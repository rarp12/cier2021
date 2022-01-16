/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.consulta.ambito;

import co.stackpointer.cier.modelo.filtro.ambito.FiltroAmbitoEspacioConsolidado;
import co.stackpointer.cier.modelo.tipo.ParamNivelConsul;
import co.stackpointer.cier.modelo.entidad.indicador.UnidadFuncional;
import co.stackpointer.cier.modelo.entidad.instrumento.TipologiaValorNombre;
import co.stackpointer.cier.modelo.fachada.ParametrosFacadeLocal;
import co.stackpointer.cier.modelo.tipo.Colores;
import co.stackpointer.cier.modelo.utilidad.UtilConsulta;
import co.stackpointer.cier.vista.ManejadorErrores;
import co.stackpointer.cier.vista.Utilidades;
import static co.stackpointer.cier.vista.Utilidades.mostrarMensajeError;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.omnifaces.util.Messages;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author rarp1
 */
@ManagedBean(name = "consolidadoEspacios")
@ViewScoped
public class ConsolidadoEspacios extends ConsultaAmbito {

    @ManagedProperty(value = "#{consultaAmbito}")
    private ConsultaAmbito consultaAmbito;
    private String codigoEstablecimiento;
    private boolean selectAll;
    private List<String> select;
    private List<UnidadFuncional> unidades;
    private List<UnidadFuncional> unidadesSeleccionadas;
    private List<Colores> colores;
    private FiltroAmbitoEspacioConsolidado filtro = new FiltroAmbitoEspacioConsolidado();
    private List<Registro> lista;
    public boolean isDetalladoPredio = false;
    File fileTemp;
    XSSFWorkbook wb = new XSSFWorkbook();
    FileOutputStream out;
    @EJB
    ParametrosFacadeLocal fParametros;

    @PostConstruct
    public void inicializar() {
        preparaUnidadesFuncionales();
        this.cargarCamposOpcionales();
        lista = new ArrayList<Registro>();
        this.unidadesSeleccionadas = new ArrayList<UnidadFuncional>();
        select = new ArrayList<String>();
        //resetearFiltros();
    }

    private void preparaUnidadesFuncionales() {
        unidades = fParametros.obtenerUnidadesFuncionales();
        colores = Arrays.asList(Colores.values());
        int index = 0;
        for (UnidadFuncional un : unidades) {
            un.setColor(colores.get(index).getCss());
            index++;
            if (index >= colores.size()) {
                index = 0;
            }
        }
    }

    public ConsultaAmbito getConsultaAmbito() {
        return consultaAmbito;
    }

    public void setConsultaAmbito(ConsultaAmbito consultaAmbito) {
        this.consultaAmbito = consultaAmbito;
    }

    public String getCodigoEstablecimiento() {
        return codigoEstablecimiento;
    }

    public void setCodigoEstablecimiento(String codigoEstablecimiento) {
        this.codigoEstablecimiento = codigoEstablecimiento;
    }

    public boolean isSelectAll() {
        return selectAll;
    }

    public void setSelectAll(boolean selectAll) {
        this.selectAll = selectAll;
    }

    public void selectAllItems() {
        select = new ArrayList<String>();
        if (this.selectAll) {
            for (UnidadFuncional unidad : unidades) {
                unidad.setChecked(Boolean.TRUE);
            }
        } else {
            for (UnidadFuncional unidad : unidades) {
                unidad.setChecked(Boolean.FALSE);
            }
        }
    }

     private void cargarCamposOpcionales() {
        selccionCamposOpcionales.clear();
        listaCamposOpcionales.clear();
        this.cargarCamposOpcionalesComunes();
        if (isMostrarColSector()) {
            listaCamposOpcionales.add(columnaSector);
        }
        if (isMostrarColZona()) {
            listaCamposOpcionales.add(columnaZona);
        }
    }

    public void cambioN1() {
        selccionCamposOpcionales.clear();
        listaCamposOpcionales.clear();
        listaCamposOpcionales.add(getColumnaCodNiv1());
        listaCamposOpcionales.add(listaNivelesConf.get(ParamNivelConsul.UNO.getNivel()));
        listaCamposOpcionales.add(getColumnaCodNiv2());
        listaCamposOpcionales.add(listaNivelesConf.get(ParamNivelConsul.DOS.getNivel()));
        selccionCamposOpcionales.add(getColumnaCodNiv1());
        selccionCamposOpcionales.add(listaNivelesConf.get(ParamNivelConsul.UNO.getNivel()));
        selccionCamposOpcionales.add(getColumnaCodNiv2());
        selccionCamposOpcionales.add(listaNivelesConf.get(ParamNivelConsul.DOS.getNivel()));
        this.lista.clear();
    }

    public void cambioN2() {
        selccionCamposOpcionales.clear();
        listaCamposOpcionales.clear();
        listaCamposOpcionales.add(getColumnaCodNiv1());
        listaCamposOpcionales.add(listaNivelesConf.get(ParamNivelConsul.UNO.getNivel()));
        listaCamposOpcionales.add(getColumnaCodNiv2());
        listaCamposOpcionales.add(listaNivelesConf.get(ParamNivelConsul.DOS.getNivel()));
        listaCamposOpcionales.add(getColumnaCodNiv3());
        listaCamposOpcionales.add(listaNivelesConf.get(ParamNivelConsul.TRES.getNivel()));
        selccionCamposOpcionales.add(getColumnaCodNiv1());
        selccionCamposOpcionales.add(listaNivelesConf.get(ParamNivelConsul.UNO.getNivel()));
        selccionCamposOpcionales.add(getColumnaCodNiv2());
        selccionCamposOpcionales.add(listaNivelesConf.get(ParamNivelConsul.DOS.getNivel()));
        selccionCamposOpcionales.add(getColumnaCodNiv3());
        selccionCamposOpcionales.add(listaNivelesConf.get(ParamNivelConsul.TRES.getNivel()));
        this.lista.clear();
    }

    public void cambioN3() {
        selccionCamposOpcionales.clear();
        listaCamposOpcionales.clear();
        listaCamposOpcionales.add(getColumnaCodNiv1());
        listaCamposOpcionales.add(listaNivelesConf.get(ParamNivelConsul.UNO.getNivel()));
        listaCamposOpcionales.add(getColumnaCodNiv2());
        listaCamposOpcionales.add(listaNivelesConf.get(ParamNivelConsul.DOS.getNivel()));
        listaCamposOpcionales.add(getColumnaCodNiv3());
        listaCamposOpcionales.add(listaNivelesConf.get(ParamNivelConsul.TRES.getNivel()));
        listaCamposOpcionales.add(getColumnaCodNiv4());
        listaCamposOpcionales.add(listaNivelesConf.get(ParamNivelConsul.CUATRO.getNivel()));
        selccionCamposOpcionales.add(getColumnaCodNiv1());
        selccionCamposOpcionales.add(listaNivelesConf.get(ParamNivelConsul.UNO.getNivel()));
        selccionCamposOpcionales.add(getColumnaCodNiv2());
        selccionCamposOpcionales.add(listaNivelesConf.get(ParamNivelConsul.DOS.getNivel()));
        selccionCamposOpcionales.add(getColumnaCodNiv3());
        selccionCamposOpcionales.add(listaNivelesConf.get(ParamNivelConsul.TRES.getNivel()));
        selccionCamposOpcionales.add(getColumnaCodNiv4());
        selccionCamposOpcionales.add(listaNivelesConf.get(ParamNivelConsul.CUATRO.getNivel()));
        this.lista.clear();
    }

    public void cambioN4() {
        selccionCamposOpcionales.clear();
        listaCamposOpcionales.clear();
        this.cargarCamposOpcionales();
        this.lista.clear();
    }

    @Override
    public void resetearFiltros() {
        consultaAmbito.resetearNiveles();
        this.resetearListasEstSedesPredios();
        consultaAmbito.nivelDPAOfAccesoUsuario();
        codigoEstablecimiento = "";
        this.resetearConsulta();
        this.select.clear();
        this.selectAll = false;
        unidadesSeleccionadas.clear();
        for (UnidadFuncional unidad : unidades) {
            if (unidad.isChecked()) {
                unidad.setChecked(Boolean.FALSE);
            }
        }
    }

    private void resetearConsulta() {
        this.cargarCamposOpcionales();
        // Limpiar lista de resultados
        this.lista.clear();

    }

    public void imprimirXLS() {
        try {
            fileTemp = File.createTempFile("ConsultaEspacioConsolidado", "xlsx");
            out = new FileOutputStream(fileTemp);
            unidadesSeleccionadas.clear();
            wb = new XSSFWorkbook();
            Sheet hoja1 = wb.createSheet();
            CellStyle style = wb.createCellStyle();
            XSSFFont font = wb.createFont();
            font.setColor(HSSFColor.WHITE.index);
            font.setBold(true);
            style.setFont(font);
            style.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
            style.setFillPattern(CellStyle.SOLID_FOREGROUND);
            Row rowDato;
            int i = 0;
            for (UnidadFuncional unidad : unidades) {
                if (unidad.isChecked()) {
                    unidadesSeleccionadas.add(unidad);
                }
            }
            rowDato = hoja1.createRow(0);
            for (String column : selccionCamposOpcionales) {
                Cell cell = rowDato.createCell(i);
                cell.setCellStyle(style);
                cell.setCellValue(column);
                i++;
            }
            for (UnidadFuncional unidad : unidadesSeleccionadas) {
                Cell cell = rowDato.createCell(i);
                cell.setCellStyle(style);
                cell.setCellValue(unidad.getDescripcion());
                i++;
            }
            int j = 1;
            for (Registro fila : this.lista) {
                int columna = 0;
                rowDato = hoja1.createRow(j);;
                if (selccionCamposOpcionales.contains(getColumnaCodNiv1())) {
                    rowDato.createCell(columna++).setCellValue(fila.getCodNivel1());
                    rowDato.createCell(columna++).setCellValue(fila.getNivel1());
                }
                if (selccionCamposOpcionales.contains(getColumnaCodNiv2())) {
                    rowDato.createCell(columna++).setCellValue(fila.getCodNivel2());
                    rowDato.createCell(columna++).setCellValue(fila.getNivel2());
                }
                if (selccionCamposOpcionales.contains(getColumnaCodNiv3())) {
                    rowDato.createCell(columna++).setCellValue(fila.getCodNivel3());
                    rowDato.createCell(columna++).setCellValue(fila.getNivel3());
                }
                if (selccionCamposOpcionales.contains(getColumnaCodNiv4())) {
                    rowDato.createCell(columna++).setCellValue(fila.getCodNivel4());
                    rowDato.createCell(columna++).setCellValue(fila.getNivel4());
                }
                if (selccionCamposOpcionales.contains(getColumnaCodNiv5())) {
                    rowDato.createCell(columna++).setCellValue(fila.getCodNivel5());
                    rowDato.createCell(columna++).setCellValue(fila.getNivel5());
                }
                if (selccionCamposOpcionales.contains(getColumnaCodEst())) {
                    rowDato.createCell(columna++).setCellValue(fila.getCodEstablecimiento());
                    rowDato.createCell(columna++).setCellValue(fila.getNomEstablecimiento());
                }
                if (selccionCamposOpcionales.contains(getColumnaCodSede())) {
                    rowDato.createCell(columna++).setCellValue(fila.getCodSede());
                    rowDato.createCell(columna++).setCellValue(fila.getNomSede());
                }
                if (selccionCamposOpcionales.contains(getColumnaCodPredio())) {
                    rowDato.createCell(columna++).setCellValue(fila.getCodPredio());
                    rowDato.createCell(columna++).setCellValue(fila.getNomPredio());
                }
                if (selccionCamposOpcionales.contains(getColumnaSector())) {
                     rowDato.createCell(columna++).setCellValue(fila.getSector());
                }
                 if (selccionCamposOpcionales.contains(getColumnaZona())) {
                    rowDato.createCell(columna++).setCellValue(fila.getZona());
                }
                for(UnidadFuncional unidad: this.unidadesSeleccionadas){
                    if(fila.getUnidades().containsKey(unidad.getCodigo())){
                       rowDato.createCell(columna++).setCellValue( fila.getUnidades().get(unidad.getCodigo()) != null ?  fila.getUnidades().get(unidad.getCodigo()):"0"); 
                    }
                }
                j++;
            }
            wb.write(out);
            out.close();

        } catch (Exception e) {
            mostrarMensajeError(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"), e.getMessage());
            ManejadorErrores.ingresarExcepcionEnLog(e, ConsolidadoEspacios.class);
        }

    }

    public StreamedContent getExportFile() {
        InputStream stream = null;
        try {
            stream = new FileInputStream(fileTemp);
        } catch (Exception ex) {
            ManejadorErrores.ingresarExcepcionEnLog(ex, ConsolidadoEspacios.class);
            Messages.addFlashGlobalError(Utilidades.obtenerMensaje("adm.parametros.config.carga.errorRegistro"));
        } finally {
            fileTemp.delete();
            return new DefaultStreamedContent(stream, "application/x-download", "ConsultaEspacioConsolidado.xlsx");
        }
    }

    public void consultar() {
        filtro.inicializar();
        filtro.setPeriodo(periodo);
        filtro.setIdioma(getUsuarioSesion().getUsuario().getIdioma().getId());
        filtro.setCodEstablecimiento(codigoEstablecimiento != null ? codigoEstablecimiento : null);
        filtro.setIdNivel0(consultaAmbito.getSeleccionNivel0() != null ? consultaAmbito.getSeleccionNivel0().getId() : null);
        filtro.setIdNivel1(consultaAmbito.getSeleccionNivel1() != null ? consultaAmbito.getSeleccionNivel1().getId() : null);
        filtro.setIdNivel2(consultaAmbito.getSeleccionNivel2() != null ? consultaAmbito.getSeleccionNivel2().getId() : null);
        filtro.setIdNivel3(consultaAmbito.getSeleccionNivel3() != null ? consultaAmbito.getSeleccionNivel3().getId() : null);
        filtro.setIdNivel4(consultaAmbito.getSeleccionNivel4() != null ? consultaAmbito.getSeleccionNivel4().getId() : null);
        filtro.setIdNivel5(consultaAmbito.getSeleccionNivel5() != null ? consultaAmbito.getSeleccionNivel5().getId() : null);
        isDetalladoPredio = false;
        unidadesSeleccionadas.clear();

        if (filtro.getIdNivel1() == null
                && filtro.getIdNivel2() == null
                && filtro.getIdNivel3() == null
                && filtro.getIdNivel4() == null
                && filtro.getIdNivel5() == null
                && filtro.getCodEstablecimiento().isEmpty()) {
            isDetalladoPredio = true;
        }

        if (filtro.getIdNivel1() == null
                && filtro.getIdNivel2() == null
                && filtro.getIdNivel3() == null
                && filtro.getIdNivel4() == null
                && filtro.getIdNivel5() == null
                && !filtro.getCodEstablecimiento().isEmpty()) {
            isDetalladoPredio = true;
        }

        if (filtro.getIdNivel1() != null
                && filtro.getIdNivel2() != null
                && filtro.getIdNivel3() != null
                && filtro.getIdNivel4() != null
                && filtro.getIdNivel5() == null
                && filtro.getCodEstablecimiento().isEmpty()) {
            isDetalladoPredio = true;
        }

        if (!filtro.getCodEstablecimiento().isEmpty()) {
            isDetalladoPredio = true;
        }

        this.lista.clear();

        for (UnidadFuncional unidad : unidades) {
            if (unidad.isChecked()) {
                unidadesSeleccionadas.add(unidad);
            }
        }

        List<Object[]> resultados = facAmbito.consultarEspaciosConsolidados(filtro, isDetalladoPredio, unidadesSeleccionadas,unidades);
        lista = new ArrayList<Registro>(resultados.size());
        if (lista.size() < 1) {
            mensajeTablaVacia = Utilidades.obtenerMensaje("aplicacion.general.noExistenRegistros");
        }
        for (Object[] fila : resultados) {
            Registro registro = new Registro();
            int i = 0;
            if (isDetalladoPredio) {
                registro.setCodNivel1(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                registro.setCodNivel2(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNivel2(UtilConsulta.stringValueOf(fila[i++]));
                registro.setCodNivel3(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNivel3(UtilConsulta.stringValueOf(fila[i++]));
                registro.setCodNivel4(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNivel4(UtilConsulta.stringValueOf(fila[i++]));
                registro.setCodNivel5(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNivel5(UtilConsulta.stringValueOf(fila[i++]));
                registro.setCodEstablecimiento(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNomEstablecimiento(UtilConsulta.stringValueOf(fila[i++]));
                registro.setCodSede(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNomSede(UtilConsulta.stringValueOf(fila[i++]));
                registro.setCodPredio(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNomPredio(UtilConsulta.stringValueOf(fila[i++]));
                TipologiaValorNombre tz = factParam.consultarValorTipologiaEstado("ZONA", UtilConsulta.stringValueOf(fila[i++]), getUsuarioSesion().getUsuario().getIdioma().getId());
                registro.setZona(tz != null ? tz.getNombre():"");
                TipologiaValorNombre ts = factParam.consultarValorTipologiaEstado("SEE", UtilConsulta.stringValueOf(fila[i++]), getUsuarioSesion().getUsuario().getIdioma().getId());
                registro.setSector(ts != null ? ts.getNombre():"");
            } else {
                registro.setCodNivel1(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                if (filtro.getIdNivel1() != null) {
                    registro.setCodNivel2(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel2(UtilConsulta.stringValueOf(fila[i++]));
                }
                if (filtro.getIdNivel2() != null) {
                    registro.setCodNivel3(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel3(UtilConsulta.stringValueOf(fila[i++]));
                }
                if (filtro.getIdNivel3() != null) {
                    registro.setCodNivel4(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel4(UtilConsulta.stringValueOf(fila[i++]));
                }
            }

            while (i < fila.length) {
                registro.getUnidades().put(UtilConsulta.stringValueOf(fila[i++]), UtilConsulta.stringValueOf(fila[i++]));
            }
            lista.add(registro);
        }
        this.unidadesSeleccionadas = new ArrayList<UnidadFuncional>();
        for (UnidadFuncional unidad : this.unidades) {
            if (unidad.isChecked()) {
                this.unidadesSeleccionadas.add(unidad);
            }
        }
    }

    public class Registro {

        private String codEstablecimiento;
        private String nomEstablecimiento;
        private String codSede;
        private String nomSede;
        private String codPredio;
        private String nomPredio;
        private String dirPredio;
        private String codNivel1;
        private String codNivel2;
        private String codNivel3;
        private String codNivel4;
        private String codNivel5;
        private String nivel0;
        private String nivel1;
        private String nivel2;
        private String nivel3;
        private String nivel4;
        private String nivel5;
        private String sector;
        private String zona;
        private HashMap<String, String> unidades = new HashMap<String, String>();

        public String getCodEstablecimiento() {
            return codEstablecimiento;
        }

        public void setCodEstablecimiento(String codEstablecimiento) {
            this.codEstablecimiento = codEstablecimiento;
        }

        public String getNomEstablecimiento() {
            return nomEstablecimiento;
        }

        public void setNomEstablecimiento(String nomEstablecimiento) {
            this.nomEstablecimiento = nomEstablecimiento;
        }

        public String getCodSede() {
            return codSede;
        }

        public void setCodSede(String codSede) {
            this.codSede = codSede;
        }

        public String getNomSede() {
            return nomSede;
        }

        public void setNomSede(String nomSede) {
            this.nomSede = nomSede;
        }

        public String getCodPredio() {
            return codPredio;
        }

        public void setCodPredio(String codPredio) {
            this.codPredio = codPredio;
        }

        public String getNomPredio() {
            return nomPredio;
        }

        public void setNomPredio(String nomPredio) {
            this.nomPredio = nomPredio;
        }

        public String getDirPredio() {
            return dirPredio;
        }

        public void setDirPredio(String dirPredio) {
            this.dirPredio = dirPredio;
        }

        public String getCodNivel1() {
            return codNivel1;
        }

        public void setCodNivel1(String codNivel1) {
            this.codNivel1 = codNivel1;
        }

        public String getCodNivel2() {
            return codNivel2;
        }

        public void setCodNivel2(String codNivel2) {
            this.codNivel2 = codNivel2;
        }

        public String getCodNivel3() {
            return codNivel3;
        }

        public void setCodNivel3(String codNivel3) {
            this.codNivel3 = codNivel3;
        }

        public String getCodNivel4() {
            return codNivel4;
        }

        public void setCodNivel4(String codNivel4) {
            this.codNivel4 = codNivel4;
        }

        public String getCodNivel5() {
            return codNivel5;
        }

        public void setCodNivel5(String codNivel5) {
            this.codNivel5 = codNivel5;
        }

        public String getNivel0() {
            return nivel0;
        }

        public void setNivel0(String nivel0) {
            this.nivel0 = nivel0;
        }

        public String getNivel1() {
            return nivel1;
        }

        public void setNivel1(String nivel1) {
            this.nivel1 = nivel1;
        }

        public String getNivel2() {
            return nivel2;
        }

        public void setNivel2(String nivel2) {
            this.nivel2 = nivel2;
        }

        public String getNivel3() {
            return nivel3;
        }

        public void setNivel3(String nivel3) {
            this.nivel3 = nivel3;
        }

        public String getNivel4() {
            return nivel4;
        }

        public void setNivel4(String nivel4) {
            this.nivel4 = nivel4;
        }

        public String getNivel5() {
            return nivel5;
        }

        public void setNivel5(String nivel5) {
            this.nivel5 = nivel5;
        }

        public HashMap<String, String> getUnidades() {
            return unidades;
        }

        public void setUnidades(HashMap<String, String> unidades) {
            this.unidades = unidades;
        }

        public String getSector() {
            return sector;
        }

        public void setSector(String sector) {
            this.sector = sector;
        }

        public String getZona() {
            return zona;
        }

        public void setZona(String zona) {
            this.zona = zona;
        }
    }

    public boolean isMostrarUnidadFuncional(String campo) {
        return select != null && select.contains(campo);
    }

    public List<String> getSelect() {
        return select;
    }

    public void setSelect(List<String> select) {
        this.select = select;
    }

    public List<UnidadFuncional> getUnidades() {
        return unidades;
    }

    public void setUnidades(List<UnidadFuncional> unidades) {
        this.unidades = unidades;
    }

    public List<Registro> getLista() {
        return lista;
    }

    public void setLista(List<Registro> lista) {
        this.lista = lista;
    }

    public boolean isIsDetalladoPredio() {
        return isDetalladoPredio;
    }

    public void setIsDetalladoPredio(boolean isDetalladoPredio) {
        this.isDetalladoPredio = isDetalladoPredio;
    }

    public List<UnidadFuncional> getUnidadesSeleccionadas() {
        return unidadesSeleccionadas;
    }

    public void setUnidadesSeleccionadas(List<UnidadFuncional> unidadesSeleccionadas) {
        this.unidadesSeleccionadas = unidadesSeleccionadas;
    }
}
