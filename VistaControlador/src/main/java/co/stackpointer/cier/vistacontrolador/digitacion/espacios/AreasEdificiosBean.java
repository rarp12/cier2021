/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.digitacion.espacios;

import co.stackpointer.cier.modelo.entidad.acceso.Usuario;
import co.stackpointer.cier.modelo.entidad.digitado.GrpEsp;
import co.stackpointer.cier.modelo.entidad.digitado.GrupoEspacio;
import co.stackpointer.cier.modelo.entidad.dpa.ConfiguracionNivel;
import co.stackpointer.cier.modelo.entidad.dpa.Nivel;
import co.stackpointer.cier.modelo.entidad.establecimiento.Establecimiento;
import co.stackpointer.cier.modelo.entidad.establecimiento.Sede;
import co.stackpointer.cier.modelo.fachada.DigitacionEspacioFacadeLocal;
import co.stackpointer.cier.modelo.fachada.EstructuraDPAFacadeLocal;
import co.stackpointer.cier.modelo.filtro.digitacion.espacios.FiltroDigitacionEdificiosAreas;
import co.stackpointer.cier.modelo.interfaceDPA.ConfNivelDPA;
import co.stackpointer.cier.modelo.tipo.ParamBaseDatos;
import co.stackpointer.cier.modelo.tipo.ParamNivelConsul;
import co.stackpointer.cier.modelo.utilidad.UtilCadena;
import co.stackpointer.cier.modelo.utilidad.UtilConsulta;
import co.stackpointer.cier.modelo.utilidad.UtilProperties;
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
@ManagedBean(name = "digAreaEdif")
@ViewScoped
public class AreasEdificiosBean implements Serializable {

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
    private List<Establecimiento> listaEstablecimientos;
    private List<Sede> listaSedes;
    private Nivel seleccionNivel0;
    private Nivel seleccionNivel1;
    private Nivel seleccionNivel2;
    private Nivel seleccionNivel3;
    private Nivel seleccionNivel4;
    private Nivel seleccionNivel5;
    private Establecimiento seleccionNivel6;
    private Sede seleccionNivel7;
    private boolean swNivel1;
    private boolean swNivel2;
    private boolean swNivel3;
    private boolean swNivel4;
    private boolean swNivel5;
    private boolean swNivel6;
    private boolean swNivel7;
    private Nivel nivelSeleccionado;
    private String nombreNivelInicial;
    private String nombreDPAInicial;
    @ManagedProperty(value = "#{login}")
    private LoginBean currentLogin;
    private List<String> columnas;
    private FiltroDigitacionEdificiosAreas filtro = new FiltroDigitacionEdificiosAreas();
    private List<GrpEsp> registros;
    private List<GrupoEspacio> registrosLocal;
    private HashMap<String, String> headers;
    private int grouping;
    private boolean showPredio;
    private boolean showSede;
    private boolean showEst;

    @PostConstruct
    public void init() {
        nivelSeleccionado = null;
        resetearNiveles();
        cargarConfiguracionDPA();
        nivelDPAOfAccesoUsuario();
        filtro.inicializar();
        columnas = new ArrayList<String>();
        registros = new ArrayList<GrpEsp>();
        headers = new HashMap<String, String>();
        grouping = 0;
        columnas = new ArrayList<String>();
        headers = new HashMap<String, String>();
        String key;
        for (int j = 1; j <= 11; j++) {
            key = "gen_0" + (j < 10 ? "0" + j : "" + j);
            columnas.add(key);
            headers.put(key, Utilidades.obtenerMensaje("digitacion.espacios.consulta.tabla." + key));
        }
        for (int j = 1; j <= 7; j++) {
            key = "bas_00" + j;
            columnas.add(key);
            headers.put(key, Utilidades.obtenerMensaje("digitacion.espacios.consulta.tabla." + key));
        }
        for (int j = 1; j <= 3; j++) {
            key = "dim_00" + j;
            columnas.add(key);
            headers.put(key, Utilidades.obtenerMensaje("digitacion.espacios.consulta.tabla." + key));
        }
    }

    private static double getDouble(String data) {
        double result;
        try {
            result = Double.parseDouble(data);
        } catch (Exception e) {
            result = 0D;
        }
        return result;
    }

    private List<GrpEsp> getSumatoriaByGrouping(List<GrupoEspacio> listado, int type) {
        List<GrpEsp> result = new ArrayList<GrpEsp>();
        GrupoEspacio grpAnt = null;
        double sumArea = 0D;
        double sumVolumen = 0D;
        double sumUsuario = 0D;
        switch (type) {
            case 1:
                showEst = true;
                showSede = true;
                showPredio = false;
                for (GrupoEspacio grp : listado) {
                    if (grpAnt != null && !(grpAnt.getCodigoEstablecimiento().equals(grp.getCodigoEstablecimiento()) && grpAnt.getCodigoSede().equals(grp.getCodigoSede()))) {
                        result.add(new GrpEsp(grpAnt.getCodigoEstablecimiento(), grpAnt.getNombreEstablecimiento(), grpAnt.getCodigoPredio(), grpAnt.getNombrePredio(), grpAnt.getCodigoSede(), grpAnt.getNombreSede(), grpAnt.getCodigoEdificio(), sumArea, sumVolumen, sumUsuario));
                        sumArea = 0D;
                        sumVolumen = 0D;
                        sumUsuario = 0D;
                    }
                    sumArea += getDouble(grp.getDim001());
                    sumVolumen += getDouble(grp.getDim003());
                    sumUsuario += getDouble(grp.getBas007());
                    grpAnt = grp;
                }
                if (grpAnt != null) {
                    result.add(new GrpEsp(grpAnt.getCodigoEstablecimiento(), grpAnt.getNombreEstablecimiento(), grpAnt.getCodigoPredio(), grpAnt.getNombrePredio(), grpAnt.getCodigoSede(), grpAnt.getNombreSede(), grpAnt.getCodigoEdificio(), sumArea, sumVolumen, sumUsuario));
                }
                break;
            case 2:
                showEst = true;
                showSede = false;
                showPredio = false;
                for (GrupoEspacio grp : listado) {
                    if (grpAnt != null && !(grpAnt.getCodigoEstablecimiento().equals(grp.getCodigoEstablecimiento()))) {
                        result.add(new GrpEsp(grpAnt.getCodigoEstablecimiento(), grpAnt.getNombreEstablecimiento(), grpAnt.getCodigoPredio(), grpAnt.getNombrePredio(), grpAnt.getCodigoSede(), grpAnt.getNombreSede(), grpAnt.getCodigoEdificio(), sumArea, sumVolumen, sumUsuario));
                        sumArea = 0D;
                        sumVolumen = 0D;
                        sumUsuario = 0D;
                    }
                    sumArea += getDouble(grp.getDim001());
                    sumVolumen += getDouble(grp.getDim003());
                    sumUsuario += getDouble(grp.getBas007());
                    grpAnt = grp;
                }
                if (grpAnt != null) {
                    result.add(new GrpEsp(grpAnt.getCodigoEstablecimiento(), grpAnt.getNombreEstablecimiento(), grpAnt.getCodigoPredio(), grpAnt.getNombrePredio(), grpAnt.getCodigoSede(), grpAnt.getNombreSede(), grpAnt.getCodigoEdificio(), sumArea, sumVolumen, sumUsuario));
                }
                break;
            default:
                showEst = true;
                showSede = true;
                showPredio = true;
                for (GrupoEspacio grp : listado) {
                    if (grpAnt != null && !(grpAnt.getCodigoEstablecimiento().equals(grp.getCodigoEstablecimiento()) && grpAnt.getCodigoSede().equals(grp.getCodigoSede()) && grpAnt.getCodigoEdificio().equals(grp.getCodigoEdificio()))) {
                        result.add(new GrpEsp(grpAnt.getCodigoEstablecimiento(), grpAnt.getNombreEstablecimiento(), grpAnt.getCodigoPredio(), grpAnt.getNombrePredio(), grpAnt.getCodigoSede(), grpAnt.getNombreSede(), grpAnt.getCodigoEdificio(), sumArea, sumVolumen, sumUsuario));
                        sumArea = 0D;
                        sumVolumen = 0D;
                        sumUsuario = 0D;
                    }
                    sumArea += getDouble(grp.getDim001());
                    sumVolumen += getDouble(grp.getDim003());
                    sumUsuario += getDouble(grp.getBas007());
                    grpAnt = grp;
                }
                if (grpAnt != null) {
                    result.add(new GrpEsp(grpAnt.getCodigoEstablecimiento(), grpAnt.getNombreEstablecimiento(), grpAnt.getCodigoPredio(), grpAnt.getNombrePredio(), grpAnt.getCodigoSede(), grpAnt.getNombreSede(), grpAnt.getCodigoEdificio(), sumArea, sumVolumen, sumUsuario));
                }
                break;
        }
        return result;
    }

    public void consultarBaseDeDatos() {
        filtro.setIdNivel0(seleccionNivel0 != null ? seleccionNivel0.getId().intValue() : null);
        filtro.setIdNivel1(seleccionNivel1 != null ? seleccionNivel1.getId().intValue() : null);
        filtro.setIdNivel2(seleccionNivel2 != null ? seleccionNivel2.getId().intValue() : null);
        filtro.setIdNivel3(seleccionNivel3 != null ? seleccionNivel3.getId().intValue() : null);
        filtro.setIdNivel4(seleccionNivel4 != null ? seleccionNivel4.getId().intValue() : null);
        filtro.setIdNivel5(seleccionNivel5 != null ? seleccionNivel5.getId().intValue() : null);
        filtro.setEstablecimiento(seleccionNivel6 != null ? seleccionNivel6.getCodigo() : null);
        filtro.setSede(seleccionNivel7 != null ? seleccionNivel7.getCodigo() : null);

        if (ParamBaseDatos.ORACLE.equals(UtilProperties.getDbMotor())) {
            registros = fachada.consultarEdificiosAreasOra(filtro, grouping);
            switch (grouping) {
                case 1:
                    showEst = true;
                    showSede = true;
                    showPredio = false;
                    break;
                case 2:
                    showEst = true;
                    showSede = false;
                    showPredio = false;
                    break;
                default:
                    showEst = true;
                    showSede = true;
                    showPredio = true;
                    break;
            }
        } else {
            registrosLocal = fachada.consultarEdificiosAreas(filtro);
            registros = getSumatoriaByGrouping(registrosLocal, grouping);
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

    public void cambioAgrupamiento() {
        if (registrosLocal != null && registrosLocal.size() > 0) {
            registros = getSumatoriaByGrouping(registrosLocal, grouping);
        }
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
        // otros niveles
        this.listaNivelesConf.put(ParamNivelConsul.ESTABLECIMIENTO.getNivel(), Utilidades.obtenerMensaje(ParamNivelConsul.ESTABLECIMIENTO.getEtiqueta()));
        this.listaNivelesConf.put(ParamNivelConsul.SEDE.getNivel(), Utilidades.obtenerMensaje(ParamNivelConsul.SEDE.getEtiqueta()));

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
        swNivel6 = true;
        swNivel7 = true;
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

    private void cargarEstablecimientos(Nivel nivelSeccionado) {
        listaEstablecimientos = facEstDPA.consultarEstablecimientos(nivelSeccionado.getId());
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

    public void resetearListasEstSedesPredios() {
        seleccionNivel6 = null;
        listaEstablecimientos = new ArrayList<Establecimiento>();
        this.resetearListasSedesPredios();
    }

    public void resetearListasSedesPredios() {
        seleccionNivel7 = null;
        listaSedes = new ArrayList<Sede>();
    }

    public void resetearNiveles() {
        this.resetearNivel1();
        this.resetearNivel2();
        this.resetearNivel3();
        this.resetearNivel4();
        this.resetearNivel5();
        this.resetearListasEstSedesPredios();
        this.resetearListasSedesPredios();
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
        this.resetearListasEstSedesPredios();
        this.resetearListasSedesPredios();
        if (seleccionNivel0 != null) {
            listaNivel1 = this.cargarNivel(seleccionNivel0, ParamNivelConsul.UNO);
        }
    }

    public void cambioNivel1() {
        this.resetearNivel2();
        this.resetearNivel3();
        this.resetearNivel4();
        this.resetearNivel5();
        this.resetearListasEstSedesPredios();
        this.resetearListasSedesPredios();
        if (seleccionNivel1 != null) {
            nivelSeleccionado = seleccionNivel1;
            if (maximoNivelDPA == 1) {
                this.cargarEstablecimientos(nivelSeleccionado);
            } else {
                listaNivel2 = this.cargarNivel(seleccionNivel1, ParamNivelConsul.DOS);
            }
        } else {
            nivelSeleccionado = null;
        }
    }

    public void cambioNivel2() {
        this.resetearNivel3();
        this.resetearNivel4();
        this.resetearNivel5();
        this.resetearListasEstSedesPredios();
        this.resetearListasSedesPredios();
        if (seleccionNivel2 != null) {
            nivelSeleccionado = seleccionNivel2;
            if (maximoNivelDPA == 2) {
                this.cargarEstablecimientos(nivelSeleccionado);
            } else {
                listaNivel3 = this.cargarNivel(seleccionNivel2, ParamNivelConsul.TRES);
            }
        } else {
            nivelSeleccionado = seleccionNivel1;
        }
    }

    public void cambioNivel3() {
        this.resetearNivel4();
        this.resetearNivel5();
        this.resetearListasEstSedesPredios();
        this.resetearListasSedesPredios();
        if (seleccionNivel3 != null) {
            nivelSeleccionado = seleccionNivel3;
            if (maximoNivelDPA == 3) {
                this.cargarEstablecimientos(nivelSeleccionado);
            } else {
                listaNivel4 = this.cargarNivel(seleccionNivel3, ParamNivelConsul.CUATRO);
            }
        } else {
            nivelSeleccionado = seleccionNivel2;
        }
    }

    public void cambioNivel4() {
        this.resetearNivel5();
        this.resetearListasEstSedesPredios();
        this.resetearListasSedesPredios();
        if (seleccionNivel4 != null) {
            nivelSeleccionado = seleccionNivel4;
            if (maximoNivelDPA == 4) {
                this.cargarEstablecimientos(nivelSeleccionado);
            } else {
                listaNivel5 = this.cargarNivel(seleccionNivel4, ParamNivelConsul.CINCO);
            }
        } else {
            nivelSeleccionado = seleccionNivel3;
        }
    }

    public void cambioNivel5() {
        if (seleccionNivel5 != null) {
            nivelSeleccionado = seleccionNivel5;
            if (maximoNivelDPA == 5) {
                this.cargarEstablecimientos(nivelSeleccionado);
            }
        } else {
            nivelSeleccionado = seleccionNivel4;
        }
    }

    public void cambioEstablecimiento() {
        this.resetearListasSedesPredios();
        if (seleccionNivel6 != null) {
            listaSedes = seleccionNivel6.getSedes();
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

    public FiltroDigitacionEdificiosAreas getFiltro() {
        return filtro;
    }

    public void setFiltro(FiltroDigitacionEdificiosAreas filtro) {
        this.filtro = filtro;
    }

    public List<GrpEsp> getRegistros() {
        return registros;
    }

    public void setRegistros(List<GrpEsp> registros) {
        this.registros = registros;
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }

    public int getGrouping() {
        return grouping;
    }

    public void setGrouping(int grouping) {
        this.grouping = grouping;
    }

    public boolean isShowPredio() {
        return showPredio;
    }

    public void setShowPredio(boolean showPredio) {
        this.showPredio = showPredio;
    }

    public boolean isShowSede() {
        return showSede;
    }

    public void setShowSede(boolean showSede) {
        this.showSede = showSede;
    }

    public boolean isShowEst() {
        return showEst;
    }

    public void setShowEst(boolean showEst) {
        this.showEst = showEst;
    }

    public List<Establecimiento> getListaEstablecimientos() {
        return listaEstablecimientos;
    }

    public void setListaEstablecimientos(List<Establecimiento> listaEstablecimientos) {
        this.listaEstablecimientos = listaEstablecimientos;
    }

    public List<Sede> getListaSedes() {
        return listaSedes;
    }

    public void setListaSedes(List<Sede> listaSedes) {
        this.listaSedes = listaSedes;
    }

    public Establecimiento getSeleccionNivel6() {
        return seleccionNivel6;
    }

    public void setSeleccionNivel6(Establecimiento seleccionNivel6) {
        this.seleccionNivel6 = seleccionNivel6;
    }

    public Sede getSeleccionNivel7() {
        return seleccionNivel7;
    }

    public void setSeleccionNivel7(Sede seleccionNivel7) {
        this.seleccionNivel7 = seleccionNivel7;
    }

    public boolean isSwNivel6() {
        return swNivel6;
    }

    public void setSwNivel6(boolean swNivel6) {
        this.swNivel6 = swNivel6;
    }

    public boolean isSwNivel7() {
        return swNivel7;
    }

    public void setSwNivel7(boolean swNivel7) {
        this.swNivel7 = swNivel7;
    }
}