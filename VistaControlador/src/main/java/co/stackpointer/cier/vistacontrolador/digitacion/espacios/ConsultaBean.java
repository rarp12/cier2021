/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.digitacion.espacios;

import co.stackpointer.cier.modelo.entidad.acceso.Usuario;
import co.stackpointer.cier.modelo.entidad.digitado.Grupo;
import co.stackpointer.cier.modelo.entidad.digitado.GrupoEspacio;
import co.stackpointer.cier.modelo.entidad.digitado.GrupoTipologia;
import co.stackpointer.cier.modelo.entidad.digitado.GrupoTipologiaKey;
import co.stackpointer.cier.modelo.entidad.dpa.ConfiguracionNivel;
import co.stackpointer.cier.modelo.entidad.dpa.Nivel;
import co.stackpointer.cier.modelo.fachada.DigitacionEspacioFacadeLocal;
import co.stackpointer.cier.modelo.fachada.EstructuraDPAFacadeLocal;
import co.stackpointer.cier.modelo.filtro.digitacion.espacios.FiltroDigitacionEspacios;
import co.stackpointer.cier.modelo.interfaceDPA.ConfNivelDPA;
import co.stackpointer.cier.modelo.tipo.ParamNivelConsul;
import co.stackpointer.cier.modelo.utilidad.UtilCadena;
import co.stackpointer.cier.modelo.utilidad.UtilConsulta;
import co.stackpointer.cier.vista.Utilidades;
import co.stackpointer.cier.vistacontrolador.bean.LoginBean;
import co.stackpointer.cier.vistacontrolador.bean.UsuarioSesionBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;

/**
 *
 * @author rjay1
 */
@ManagedBean(name = "digEspCon")
@ViewScoped
public class ConsultaBean implements Serializable {

    @EJB
    DigitacionEspacioFacadeLocal fachada;
    @EJB
    private EstructuraDPAFacadeLocal facEstDPA;
    @ManagedProperty(value = "#{usuarioSesion}")
    private UsuarioSesionBean usuarioSesion;
    private Map<Long, String> listaNivelesConf;
    private int maximoNivelDPA;
    private int minimoNivelDPA;
    private List<Nivel> listaNivel1;
    private List<Nivel> listaNivel2;
    private List<Nivel> listaNivel3;
    private List<Nivel> listaNivel4;
    private List<Nivel> listaNivel5;
    private Nivel seleccionNivel0;
    private Nivel seleccionNivel1;
    private Nivel seleccionNivel2;
    private Nivel seleccionNivel3;
    private Nivel seleccionNivel4;
    private Nivel seleccionNivel5;
    private boolean swNivel1;
    private boolean swNivel2;
    private boolean swNivel3;
    private boolean swNivel4;
    private boolean swNivel5;
    private Nivel nivelSeleccionado;
    private String nombreNivelInicial;
    private String nombreDPAInicial;
    @ManagedProperty(value = "#{login}")
    private LoginBean currentLogin;
    private List<String> columnas;
    private FiltroDigitacionEspacios filtro = new FiltroDigitacionEspacios();
    private Map<String, Boolean> campos;
    private List<GrupoEspacio> registros;
    private HashMap<String, String> headers;
    private HashMap<String, String> colors;
    private HashMap<GrupoTipologiaKey, String> idiomas;
    private List<GrupoTipologia> tipoAmbientes;
    private boolean selectAll;
    private List<Grupo> grupos;
    private List<String> select;

    @PostConstruct
    public void init() {
        nivelSeleccionado = null;
        resetearNiveles();
        cargarConfiguracionDPA();
        nivelDPAOfAccesoUsuario();
        filtro.inicializar();
        grupos = fachada.buscarTodosGrupos();
        columnas = new ArrayList<String>();
        registros = new ArrayList<GrupoEspacio>();
        headers = new HashMap<String, String>();
        colors = new HashMap<String, String>();
        tipoAmbientes = fachada.buscarGruposTipologiasByCodigo("TIPO_AMBIEN", 1);
        fillIdiomas();
        resetCampos();
        for (Grupo grp : grupos) {
            colors.put(grp.getPrefijo(), grp.getColor());
        }
    }

    private void fillIdiomas() {
        idiomas = new HashMap<GrupoTipologiaKey, String>();
        List<GrupoTipologia> tipologias = fachada.buscarTodosGruposTipologias();
        for (GrupoTipologia tipologia : tipologias) {
            idiomas.put(new GrupoTipologiaKey(tipologia.getCodTipologia(),
                    tipologia.getCodigo(),
                    tipologia.getIdioma()), tipologia.getNombre());
        }
        idiomas.put(new GrupoTipologiaKey("TAUTO", "0", 1), "No");
        idiomas.put(new GrupoTipologiaKey("TAUTO", "0", 2), "No");
        idiomas.put(new GrupoTipologiaKey("TAUTO", "1", 1), "Si");
        idiomas.put(new GrupoTipologiaKey("TAUTO", "1", 2), "Yes");
        idiomas.put(new GrupoTipologiaKey("TAUTO", "2", 1), "N/A");
        idiomas.put(new GrupoTipologiaKey("TAUTO", "2", 2), "N/A");
    }

    private void resetCampos() {
        campos = new HashMap<String, Boolean>();
        for (Grupo grp : grupos) {
            campos.put(grp.getPrefijo(), false);
        }
    }

    public void selectAllItems() {
        select = new ArrayList<String>();
        if (this.selectAll) {
            for (Grupo grp : grupos) {
                select.add(grp.getPrefijo());
            }
        }
    }

    public String getTextFromTipologia(String codTipologia, String codigo) {
        String result = codigo;
        Long idioma = getUsuarioSesion().getUsuario().getIdioma().getId();
        GrupoTipologiaKey key = new GrupoTipologiaKey(codTipologia, codigo, idioma.intValue());
        if (idiomas.containsKey(key)) {
            result = idiomas.get(key);
        }
        return result;
    }

    public String getTextAislamiento(String idBusqueda, String idDato1, String idDato2, String idDato3, String idDato4) {
        if (idBusqueda.equals(idDato1.trim()) || idBusqueda.equals(idDato2.trim()) || idBusqueda.equals(idDato3.trim()) || idBusqueda.equals(idDato4.trim())) {
            return getTextFromTipologia("TAUTO", "1");
        } else {
            return getTextFromTipologia("TAUTO", "0");
        }
    }

    public void consultarBaseDeDatos() {
        columnas = new ArrayList<String>();
        headers = new HashMap<String, String>();
        resetCampos();
        for (String grp : select) {
            campos.put(grp, true);
        }
        filtro.setIdNivel0(seleccionNivel0 != null ? seleccionNivel0.getId().intValue() : null);
        filtro.setIdNivel1(seleccionNivel1 != null ? seleccionNivel1.getId().intValue() : null);
        filtro.setIdNivel2(seleccionNivel2 != null ? seleccionNivel2.getId().intValue() : null);
        filtro.setIdNivel3(seleccionNivel3 != null ? seleccionNivel3.getId().intValue() : null);
        filtro.setIdNivel4(seleccionNivel4 != null ? seleccionNivel4.getId().intValue() : null);
        filtro.setIdNivel5(seleccionNivel5 != null ? seleccionNivel5.getId().intValue() : null);
        filtro.setCampos(campos);
        registros = fachada.consultarGruposEspacios(filtro);
        String key;
        for (int j = 1; j <= 11; j++) {
            key = "gen_0" + (j < 10 ? "0" + j : "" + j);
            columnas.add(key);
            headers.put(key, Utilidades.obtenerMensaje("digitacion.espacios.consulta.tabla." + key));
        }
        if (filtro.getCampos().get("BAS")) {
            for (int j = 1; j <= 7; j++) {
                key = "bas_0" + (j < 10 ? "0" + j : "" + j);
                columnas.add(key);
                headers.put(key, Utilidades.obtenerMensaje("digitacion.espacios.consulta.tabla." + key));
            }
        }
        if (filtro.getCampos().get("DIM")) {
            for (int j = 1; j <= 3; j++) {
                key = "dim_0" + (j < 10 ? "0" + j : "" + j);
                columnas.add(key);
                headers.put(key, Utilidades.obtenerMensaje("digitacion.espacios.consulta.tabla." + key));
            }
        }
        if (filtro.getCampos().get("CON")) {
            for (int j = 1; j <= 12; j++) {
                key = "con_0" + (j < 10 ? "0" + j : "" + j);
                columnas.add(key);
                headers.put(key, Utilidades.obtenerMensaje("digitacion.espacios.consulta.tabla." + key));
            }
        }
        if (filtro.getCampos().get("MAT")) {
            for (int j = 1; j <= 13; j++) {
                key = "mat_0" + (j < 10 ? "0" + j : "" + j);
                columnas.add(key);
                headers.put(key, Utilidades.obtenerMensaje("digitacion.espacios.consulta.tabla." + key));
            }
        }
        if (filtro.getCampos().get("ELM")) {
            for (int j = 1; j <= 6; j++) {
                key = "elm_0" + (j < 10 ? "0" + j : "" + j);
                columnas.add(key);
                headers.put(key, Utilidades.obtenerMensaje("digitacion.espacios.consulta.tabla." + key));
            }
        }
        if (filtro.getCampos().get("PRP")) {
            for (int j = 1; j <= 9; j++) {
                key = "prp_0" + (j < 10 ? "0" + j : "" + j);
                columnas.add(key);
                headers.put(key, Utilidades.obtenerMensaje("digitacion.espacios.consulta.tabla." + key));
            }
        }
        if (filtro.getCampos().get("SNT")) {
            for (int j = 1; j <= 30; j++) {
                key = "snt_0" + (j < 10 ? "0" + j : "" + j);
                columnas.add(key);
                headers.put(key, Utilidades.obtenerMensaje("digitacion.espacios.consulta.tabla." + key));
            }
        }
        if (filtro.getCampos().containsKey("REC")) {
            for (int j = 1; j <= 15; j++) {
                key = "rec_0" + (j < 10 ? "0" + j : "" + j);
                columnas.add(key);
                headers.put(key, Utilidades.obtenerMensaje("digitacion.espacios.consulta.tabla." + key));
            }
        }
    }

    public String getFormatoEntero() {
        return UtilConsulta.formatoEntero;
    }

    public void postProcessXLS(Object document) {
        try {
            HSSFWorkbook wb = (HSSFWorkbook) document;
            HSSFSheet sheet = wb.getSheetAt(0);
            HSSFRow header = sheet.getRow(0);
            HSSFCellStyle cellStyle = wb.createCellStyle();
            cellStyle.setFillForegroundColor(HSSFColor.BLUE_GREY.index);
            cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            cellStyle.setFillBackgroundColor(HSSFColor.GREEN.index);
            cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
            HSSFFont font = wb.createFont();
            font.setFontName(HSSFFont.FONT_ARIAL);
            font.setFontHeightInPoints((short) 10);
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            font.setColor(HSSFColor.WHITE.index);
            cellStyle.setFont(font);

            for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
                HSSFCell cell = header.getCell(i);
                cell.setCellStyle(cellStyle);
                sheet.autoSizeColumn((short) i); //ajusta el ancho de la columna
            }
            for (int x = 1; x < sheet.getPhysicalNumberOfRows(); x++) {
                HSSFRow row = sheet.getRow(x);
                for (int y = 0; y < header.getPhysicalNumberOfCells(); y++) {
                    HSSFCell cell = row.getCell(y);
                    cell.setCellValue(cell.getStringCellValue().replaceAll("<table>", "")
                            .replaceAll("<tr>", "")
                            .replaceAll("<td class=\"noBorder\">", "")
                            .replaceAll("</td>", "")
                            .replaceAll("</tr>", "")
                            .replaceAll("</table>", "")
                            .trim());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resetearFiltros() {
        init();
    }

    public void cargarConfiguracionDPA() {
        listaNivelesConf = new HashMap<Long, String>();
        // niveles DPA
        Usuario u = usuarioSesion.getUsuario();
        List<ConfiguracionNivel> configuraciones;
        if (UtilCadena.isNullOVacio(u.getNivelDpa())) {
            configuraciones = facEstDPA.consultarConfNivelesActual();
        } else {
            configuraciones = facEstDPA.consultarConfNivelesActual(u.getNivelDpa().getNivel());
        }
        for (ConfNivelDPA conf : configuraciones) {
            String descripcion = facEstDPA.consultarDescripcionNivelDPA(conf.getNivel(), usuarioSesion.getUsuario().getIdioma().getId());
            descripcion = descripcion != null ? descripcion : conf.getDescripcion();
            this.listaNivelesConf.put(conf.getNivel(), descripcion);
        }
        // maximo nivel DPA
        if (!configuraciones.isEmpty()) {
            int indice = configuraciones.size() - 1;
            this.maximoNivelDPA = configuraciones.get(indice).getNivel().intValue();
            this.minimoNivelDPA = configuraciones.get(0).getNivel().intValue();
        } else {
            this.maximoNivelDPA = -1;
            this.minimoNivelDPA = -1;
        }
    }

    public void activarNiveles(int maximoNivelUsuario) {
        swNivel1 = false;
        swNivel2 = false;
        swNivel3 = false;
        swNivel4 = false;
        swNivel5 = false;
        maximoNivelUsuario++;
        while (maximoNivelUsuario <= maximoNivelDPA) {
            switch (maximoNivelUsuario) {
                case 1:
                    swNivel1 = true;
                    break;
                case 2:
                    swNivel2 = true;
                    break;
                case 3:
                    swNivel3 = true;
                    break;
                case 4:
                    swNivel4 = true;
                    break;
                case 5:
                    swNivel5 = true;
                    break;
            }
            maximoNivelUsuario++;
        }
    }

    public void nivelDPAOfAccesoUsuario() {
        Nivel n = usuarioSesion.getUsuario().getNivelEspecifico() != null
                ? usuarioSesion.getUsuario().getNivelEspecifico()
                : null;
        if (n != null) {
            this.nombreNivelInicial = n.getConfiguracion().getDescripcion();
            this.nombreDPAInicial = n.getDescripcion();
            int nivel = n.getConfiguracion().getNivel().intValue();
            switch (nivel) {
                case 0:
                    cargarNivel0();
                    break;
                case 1:
                    seleccionNivel1 = n;
                    cambioNivel1();
                    break;
                case 2:
                    seleccionNivel2 = n;
                    cambioNivel2();
                    break;
                case 3:
                    seleccionNivel3 = n;
                    cambioNivel3();
                    break;
                case 4:
                    seleccionNivel4 = n;
                    cambioNivel4();
                    break;
                case 5:
                    seleccionNivel5 = n;
                    cambioNivel5();
                    break;
            }
            activarNiveles(nivel);
        } else {
            cargarNivel0();
            activarNiveles(0);
        }
    }

    private List<Nivel> cargarNivel(Nivel nivelSeccionado, ParamNivelConsul nivelConsulta) {
        return facEstDPA.consultarNiveles(nivelConsulta, nivelSeccionado.getId());
    }

    private void resetearNivel1() {
        seleccionNivel1 = null;
        listaNivel1 = new ArrayList<Nivel>();
    }

    private void resetearNivel2() {
        seleccionNivel2 = null;
        listaNivel2 = new ArrayList<Nivel>();
    }

    private void resetearNivel3() {
        seleccionNivel3 = null;
        listaNivel3 = new ArrayList<Nivel>();
    }

    private void resetearNivel4() {
        seleccionNivel4 = null;
        listaNivel4 = new ArrayList<Nivel>();
    }

    private void resetearNivel5() {
        seleccionNivel5 = null;
        listaNivel5 = new ArrayList<Nivel>();
    }

    public void resetearNiveles() {
        this.resetearNivel1();
        this.resetearNivel2();
        this.resetearNivel3();
        this.resetearNivel4();
        this.resetearNivel5();
    }

    public void cargarNivel0() {
        String codigoPais = currentLogin.getPais().getCodigoPais();
        seleccionNivel0 = facEstDPA.consultarNivel(codigoPais);
        this.nombreNivelInicial = seleccionNivel0.getConfiguracion().getDescripcion();
        this.nombreDPAInicial = seleccionNivel0.getDescripcion();
        this.cargarNivel1();
    }

    public void cargarNivel1() {
        this.resetearNivel1();
        this.resetearNivel2();
        this.resetearNivel3();
        this.resetearNivel4();
        this.resetearNivel5();
        if (seleccionNivel0 != null) {
            listaNivel1 = this.cargarNivel(seleccionNivel0, ParamNivelConsul.UNO);
        }
    }

    public void cambioNivel1() {
        this.resetearNivel2();
        this.resetearNivel3();
        this.resetearNivel4();
        this.resetearNivel5();
        if (seleccionNivel1 != null) {
            nivelSeleccionado = seleccionNivel1;
            listaNivel2 = this.cargarNivel(seleccionNivel1, ParamNivelConsul.DOS);
        } else {
            nivelSeleccionado = null;
        }
    }

    public void cambioNivel2() {
        this.resetearNivel3();
        this.resetearNivel4();
        this.resetearNivel5();
        if (seleccionNivel2 != null) {
            nivelSeleccionado = seleccionNivel2;
            listaNivel3 = this.cargarNivel(seleccionNivel2, ParamNivelConsul.TRES);
        } else {
            nivelSeleccionado = seleccionNivel1;
        }
    }

    public void cambioNivel3() {
        this.resetearNivel4();
        this.resetearNivel5();
        if (seleccionNivel3 != null) {
            nivelSeleccionado = seleccionNivel3;
            listaNivel4 = this.cargarNivel(seleccionNivel3, ParamNivelConsul.CUATRO);
        } else {
            nivelSeleccionado = seleccionNivel2;
        }
    }

    public void cambioNivel4() {
        this.resetearNivel5();
        if (seleccionNivel4 != null) {
            nivelSeleccionado = seleccionNivel4;
            listaNivel5 = this.cargarNivel(seleccionNivel4, ParamNivelConsul.CINCO);
        } else {
            nivelSeleccionado = seleccionNivel3;
        }
    }

    public void cambioNivel5() {
        if (seleccionNivel5 != null) {
            nivelSeleccionado = seleccionNivel5;
        } else {
            nivelSeleccionado = seleccionNivel4;
        }
    }

    public UsuarioSesionBean getUsuarioSesion() {
        return usuarioSesion;
    }

    public void setUsuarioSesion(UsuarioSesionBean usuarioSesion) {
        this.usuarioSesion = usuarioSesion;
    }

    public Map<Long, String> getListaNivelesConf() {
        return listaNivelesConf;
    }

    public void setListaNivelesConf(Map<Long, String> listaNivelesConf) {
        this.listaNivelesConf = listaNivelesConf;
    }

    public int getMaximoNivelDPA() {
        return maximoNivelDPA;
    }

    public void setMaximoNivelDPA(int maximoNivelDPA) {
        this.maximoNivelDPA = maximoNivelDPA;
    }

    public int getMinimoNivelDPA() {
        return minimoNivelDPA;
    }

    public void setMinimoNivelDPA(int minimoNivelDPA) {
        this.minimoNivelDPA = minimoNivelDPA;
    }

    public List<Nivel> getListaNivel1() {
        return listaNivel1;
    }

    public void setListaNivel1(List<Nivel> listaNivel1) {
        this.listaNivel1 = listaNivel1;
    }

    public List<Nivel> getListaNivel2() {
        return listaNivel2;
    }

    public void setListaNivel2(List<Nivel> listaNivel2) {
        this.listaNivel2 = listaNivel2;
    }

    public List<Nivel> getListaNivel3() {
        return listaNivel3;
    }

    public void setListaNivel3(List<Nivel> listaNivel3) {
        this.listaNivel3 = listaNivel3;
    }

    public List<Nivel> getListaNivel4() {
        return listaNivel4;
    }

    public void setListaNivel4(List<Nivel> listaNivel4) {
        this.listaNivel4 = listaNivel4;
    }

    public List<Nivel> getListaNivel5() {
        return listaNivel5;
    }

    public void setListaNivel5(List<Nivel> listaNivel5) {
        this.listaNivel5 = listaNivel5;
    }

    public Nivel getSeleccionNivel0() {
        return seleccionNivel0;
    }

    public void setSeleccionNivel0(Nivel seleccionNivel0) {
        this.seleccionNivel0 = seleccionNivel0;
    }

    public Nivel getSeleccionNivel1() {
        return seleccionNivel1;
    }

    public void setSeleccionNivel1(Nivel seleccionNivel1) {
        this.seleccionNivel1 = seleccionNivel1;
    }

    public Nivel getSeleccionNivel2() {
        return seleccionNivel2;
    }

    public void setSeleccionNivel2(Nivel seleccionNivel2) {
        this.seleccionNivel2 = seleccionNivel2;
    }

    public Nivel getSeleccionNivel3() {
        return seleccionNivel3;
    }

    public void setSeleccionNivel3(Nivel seleccionNivel3) {
        this.seleccionNivel3 = seleccionNivel3;
    }

    public Nivel getSeleccionNivel4() {
        return seleccionNivel4;
    }

    public void setSeleccionNivel4(Nivel seleccionNivel4) {
        this.seleccionNivel4 = seleccionNivel4;
    }

    public Nivel getSeleccionNivel5() {
        return seleccionNivel5;
    }

    public void setSeleccionNivel5(Nivel seleccionNivel5) {
        this.seleccionNivel5 = seleccionNivel5;
    }

    public boolean isSwNivel1() {
        return swNivel1;
    }

    public void setSwNivel1(boolean swNivel1) {
        this.swNivel1 = swNivel1;
    }

    public boolean isSwNivel2() {
        return swNivel2;
    }

    public void setSwNivel2(boolean swNivel2) {
        this.swNivel2 = swNivel2;
    }

    public boolean isSwNivel3() {
        return swNivel3;
    }

    public void setSwNivel3(boolean swNivel3) {
        this.swNivel3 = swNivel3;
    }

    public boolean isSwNivel4() {
        return swNivel4;
    }

    public void setSwNivel4(boolean swNivel4) {
        this.swNivel4 = swNivel4;
    }

    public boolean isSwNivel5() {
        return swNivel5;
    }

    public void setSwNivel5(boolean swNivel5) {
        this.swNivel5 = swNivel5;
    }

    public Nivel getNivelSeleccionado() {
        return nivelSeleccionado;
    }

    public void setNivelSeleccionado(Nivel nivelSeleccionado) {
        this.nivelSeleccionado = nivelSeleccionado;
    }

    public String getNombreNivelInicial() {
        return nombreNivelInicial;
    }

    public void setNombreNivelInicial(String nombreNivelInicial) {
        this.nombreNivelInicial = nombreNivelInicial;
    }

    public String getNombreDPAInicial() {
        return nombreDPAInicial;
    }

    public void setNombreDPAInicial(String nombreDPAInicial) {
        this.nombreDPAInicial = nombreDPAInicial;
    }

    public LoginBean getCurrentLogin() {
        return currentLogin;
    }

    public void setCurrentLogin(LoginBean currentLogin) {
        this.currentLogin = currentLogin;
    }

    public List<String> getColumnas() {
        return columnas;
    }

    public void setColumnas(List<String> columnas) {
        this.columnas = columnas;
    }

    public FiltroDigitacionEspacios getFiltro() {
        return filtro;
    }

    public void setFiltro(FiltroDigitacionEspacios filtro) {
        this.filtro = filtro;
    }

    public List<GrupoEspacio> getRegistros() {
        return registros;
    }

    public void setRegistros(List<GrupoEspacio> registros) {
        this.registros = registros;
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, Boolean> getCampos() {
        return campos;
    }

    public void setCampos(Map<String, Boolean> campos) {
        this.campos = campos;
    }

    public boolean isSelectAll() {
        return selectAll;
    }

    public void setSelectAll(boolean selectAll) {
        this.selectAll = selectAll;
    }

    public List<Grupo> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<Grupo> grupos) {
        this.grupos = grupos;
    }

    public List<String> getSelect() {
        return select;
    }

    public void setSelect(List<String> select) {
        this.select = select;
    }

    public HashMap<String, String> getColors() {
        return colors;
    }

    public void setColors(HashMap<String, String> colors) {
        this.colors = colors;
    }

    public List<GrupoTipologia> getTipoAmbientes() {
        return tipoAmbientes;
    }

    public void setTipoAmbientes(List<GrupoTipologia> tipoAmbientes) {
        this.tipoAmbientes = tipoAmbientes;
    }
}