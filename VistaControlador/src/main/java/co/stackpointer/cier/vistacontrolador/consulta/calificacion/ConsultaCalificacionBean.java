/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.consulta.calificacion;

import co.stackpointer.cier.modelo.fachada.CalificacionFacadeLocal;
import co.stackpointer.cier.modelo.filtro.calificacion.FiltroCalificacion;
import co.stackpointer.cier.modelo.tipo.Ambitos;
import co.stackpointer.cier.modelo.tipo.ParamNivelConsul;
import co.stackpointer.cier.modelo.tipo.TipologiaCodigo;
import co.stackpointer.cier.modelo.utilidad.UtilConsulta;
import co.stackpointer.cier.vista.ManejadorErrores;
import co.stackpointer.cier.vista.Utilidades;
import static co.stackpointer.cier.vista.Utilidades.mostrarMensajeError;
import co.stackpointer.cier.vistacontrolador.bean.GestionArchivos;
import co.stackpointer.cier.vistacontrolador.consulta.ambito.ConsultaAmbito;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
 * @author user
 */
@ManagedBean(name = "consultaCalificacion")
@ViewScoped
public class ConsultaCalificacionBean extends ConsultaAmbito {

    @EJB
    private CalificacionFacadeLocal facCalificacion;
    @ManagedProperty(value = "#{consultaAmbito}")
    private ConsultaAmbito consultaAmbito;
    private List<Registro> lista;
    private HashMap hmPonderaciones;
    //columnas
    private String columnaAccesosExt;
    private String columnaAccesosInt;
    private String columnaAmbientes;
    private String columnaConfort;
    private String columnaControlVigilancia;
    private String columnaEstadoEdificiosEspacios;
    private String columnaOferta;
    private String columnaPropiedad;
    private String columnaRiesgos;
    private String columnaSeguridad;
    private String columnaServicios;
    private String columnaSostenibilidad;
    private String columnaServiciosSanitarios;
    private String columnaCalificacionTotal;
    
    @PostConstruct
    public void inicializar() {
        super.initActual();
        lista = new ArrayList<Registro>();
        seleccionSector = null;
        seleccionZona = null;
        setearColumnas();
        cargarCamposOpcionales();
    }

    private void cargarCamposOpcionales() {
        selccionCamposOpcionales.clear();        
        listaCamposOpcionales.clear();        
        this.cargarCamposOpcionalesComunes();
        //this.cargarCamposOpcionalesComunes();        
        /*if (isMostrarColSector()) {
            listaCamposOpcionales.add(columnaSector);
        }
        if (isMostrarColZona()) {
            listaCamposOpcionales.add(columnaZona);
        }*/
        /*if (seleccionAgrupamiento.equals(ParamNivelConsul.PREDIO.getNivel())) {
            listaCamposOpcionales.add(columnaCodEst);
            listaCamposOpcionales.add(columnaNomEst);
            listaCamposOpcionales.add(columnaCodSede);
            listaCamposOpcionales.add(columnaNomSede);
            listaCamposOpcionales.add(columnaCodPredio);
            listaCamposOpcionales.add(columnaNomPredio);
            selccionCamposOpcionales.add(columnaCodPredio);
            selccionCamposOpcionales.add(columnaNomPredio);
        }
        if (seleccionAgrupamiento.equals(ParamNivelConsul.SEDE.getNivel())) {
            listaCamposOpcionales.add(columnaCodSede);
            listaCamposOpcionales.add(columnaNomSede);
            selccionCamposOpcionales.add(columnaCodSede);
            selccionCamposOpcionales.add(columnaNomSede);
        }
        if (seleccionAgrupamiento.equals(ParamNivelConsul.ESTABLECIMIENTO.getNivel())) {
            listaCamposOpcionales.add(columnaCodEst);
            listaCamposOpcionales.add(columnaNomEst);
            selccionCamposOpcionales.add(columnaCodEst);
            selccionCamposOpcionales.add(columnaNomEst);
        }
        if (seleccionAgrupamiento.equals(ParamNivelConsul.CERO.getNivel())) {            
            listaCamposOpcionales.add(listaNivelesConf.get(0L));
            selccionCamposOpcionales.add(listaNivelesConf.get(0L));
        }
        if (seleccionAgrupamiento.equals(ParamNivelConsul.UNO.getNivel())) {           
            listaCamposOpcionales.add(listaNivelesConf.get(1L));
            selccionCamposOpcionales.add(listaNivelesConf.get(1L));
        }
        if (seleccionAgrupamiento.equals(ParamNivelConsul.DOS.getNivel())) {           
            listaCamposOpcionales.add(listaNivelesConf.get(2L));
            selccionCamposOpcionales.add(listaNivelesConf.get(2L));
        }
        if (seleccionAgrupamiento.equals(ParamNivelConsul.TRES.getNivel())) {            
            listaCamposOpcionales.add(listaNivelesConf.get(3L));
            selccionCamposOpcionales.add(listaNivelesConf.get(3L));
        }
        if (seleccionAgrupamiento.equals(ParamNivelConsul.CUATRO.getNivel())) {           
            listaCamposOpcionales.add(listaNivelesConf.get(4L));
            selccionCamposOpcionales.add(listaNivelesConf.get(4L));
        }
        if (seleccionAgrupamiento.equals(ParamNivelConsul.CINCO.getNivel())) {            
            listaCamposOpcionales.add(listaNivelesConf.get(5L));
            selccionCamposOpcionales.add(listaNivelesConf.get(5L));
        }*/
        //propias del ambito
        listaCamposOpcionales.add(columnaAccesosExt);
        selccionCamposOpcionales.add(columnaAccesosExt);

        listaCamposOpcionales.add(columnaAccesosInt);
        selccionCamposOpcionales.add(columnaAccesosInt);

        listaCamposOpcionales.add(columnaAmbientes);
        selccionCamposOpcionales.add(columnaAmbientes);

        listaCamposOpcionales.add(columnaConfort);
        selccionCamposOpcionales.add(columnaConfort);

        listaCamposOpcionales.add(columnaControlVigilancia);
        selccionCamposOpcionales.add(columnaControlVigilancia);

        listaCamposOpcionales.add(columnaEstadoEdificiosEspacios);
        selccionCamposOpcionales.add(columnaEstadoEdificiosEspacios);

        listaCamposOpcionales.add(columnaOferta);
        selccionCamposOpcionales.add(columnaOferta);

        listaCamposOpcionales.add(columnaPropiedad);
        selccionCamposOpcionales.add(columnaPropiedad);

        listaCamposOpcionales.add(columnaRiesgos);
        selccionCamposOpcionales.add(columnaRiesgos);

        listaCamposOpcionales.add(columnaSeguridad);
        selccionCamposOpcionales.add(columnaSeguridad);

        listaCamposOpcionales.add(columnaServicios);
        selccionCamposOpcionales.add(columnaServicios);

        listaCamposOpcionales.add(columnaSostenibilidad);
        selccionCamposOpcionales.add(columnaSostenibilidad);

        listaCamposOpcionales.add(columnaServiciosSanitarios);
        selccionCamposOpcionales.add(columnaServiciosSanitarios);

        listaCamposOpcionales.add(columnaCalificacionTotal);
        selccionCamposOpcionales.add(columnaCalificacionTotal);
    }

    private void setearColumnas() {
        List<Object[]> ponderaciones = facCalificacion.obtenerPonderaciones();
        hmPonderaciones = new HashMap();

        BigDecimal suma = new BigDecimal("0");
        for (Object[] ponderacion : ponderaciones) {
            hmPonderaciones.put(ponderacion[0], ponderacion[1]);
            suma = suma.add(ponderacion[1] != null && !"".equals(ponderacion[1].toString()) ? new BigDecimal(ponderacion[1].toString()) : new BigDecimal(0));
        }

        columnaAccesosExt = hmPonderaciones.get(Ambitos.A_ACC.toString()) + "%" + Utilidades.obtenerMensaje("consultas.calificacion.accesosExt");
        columnaAccesosInt = hmPonderaciones.get(Ambitos.A_ACCD.toString()) + "%" + Utilidades.obtenerMensaje("consultas.calificacion.accesosInt");
        columnaAmbientes = hmPonderaciones.get(Ambitos.A_AMB.toString()) + "%" + Utilidades.obtenerMensaje("consultas.calificacion.ambientes");
        columnaConfort = hmPonderaciones.get(Ambitos.A_CON.toString()) + "%" + Utilidades.obtenerMensaje("consultas.calificacion.confort");
        columnaControlVigilancia = hmPonderaciones.get(Ambitos.A_CV.toString()) + "%" + Utilidades.obtenerMensaje("consultas.calificacion.controlVigilancia");
        columnaEstadoEdificiosEspacios = hmPonderaciones.get(Ambitos.A_EEE.toString()) + "%" + Utilidades.obtenerMensaje("consultas.calificacion.estadoEdificiosEspacios");
        columnaOferta = hmPonderaciones.get(Ambitos.A_OFE.toString()) + "%" + Utilidades.obtenerMensaje("consultas.calificacion.oferta");
        columnaPropiedad = hmPonderaciones.get(Ambitos.A_PROP.toString()) + "%" + Utilidades.obtenerMensaje("consultas.calificacion.propiedad");
        columnaRiesgos = hmPonderaciones.get(Ambitos.A_RI.toString()) + "%" + Utilidades.obtenerMensaje("consultas.calificacion.riesgos");
        columnaSeguridad = hmPonderaciones.get(Ambitos.A_SEG.toString()) + "%" + Utilidades.obtenerMensaje("consultas.calificacion.seguridad");
        columnaServicios = hmPonderaciones.get(Ambitos.A_SP.toString()) + "%" + Utilidades.obtenerMensaje("consultas.calificacion.servicios");
        columnaSostenibilidad = hmPonderaciones.get(Ambitos.A_SOS.toString()) + "%" + Utilidades.obtenerMensaje("consultas.calificacion.sostenibilidad");
        columnaServiciosSanitarios = hmPonderaciones.get(Ambitos.A_SS.toString()) + "%" + Utilidades.obtenerMensaje("consultas.calificacion.serviciosSanitarios");
        columnaCalificacionTotal = suma + "%" + Utilidades.obtenerMensaje("consultas.calificacion.calificacionTotal");
        
    }

    /*private void cargarAgrupamientos() {
     listaAgrupamientos = new HashMap<Long, String>();
     listaAgrupamientos.putAll(listaNivelesConf);
     }*/
    @Override
    public void resetearFiltros() {
        consultaAmbito.resetearNiveles();
        this.resetearListasEstSedesPredios();
        this.seleccionSector = null;
        this.seleccionZona = null;
        consultaAmbito.nivelDPAOfAccesoUsuario();
    }

    @Override
    public void cambioAgrupamiento() {
        this.cargarCamposOpcionales();
        // resultados
        this.lista.clear();
    }

    public void consultar() {
        try {
            FiltroCalificacion filtro = new FiltroCalificacion();
            filtro.init();
            //"periodo" en este caso se refiere al periodo de creación. 
            //En la búsqueda se verán representados todos los instrumentos vigentes en ese momento.
            filtro.setPeriodo(periodo);
            filtro.setIdNivel0(consultaAmbito.getSeleccionNivel0() != null ? consultaAmbito.getSeleccionNivel0().getId() : null);
            filtro.setIdNivel1(consultaAmbito.getSeleccionNivel1() != null ? consultaAmbito.getSeleccionNivel1().getId() : null);
            filtro.setIdNivel2(consultaAmbito.getSeleccionNivel2() != null ? consultaAmbito.getSeleccionNivel2().getId() : null);
            filtro.setIdNivel3(consultaAmbito.getSeleccionNivel3() != null ? consultaAmbito.getSeleccionNivel3().getId() : null);
            filtro.setIdNivel4(consultaAmbito.getSeleccionNivel4() != null ? consultaAmbito.getSeleccionNivel4().getId() : null);
            filtro.setIdNivel5(consultaAmbito.getSeleccionNivel5() != null ? consultaAmbito.getSeleccionNivel5().getId() : null);

            filtro.setCodZona(seleccionZona != null ? seleccionZona.getTipValor().getCodigo() : null);
            filtro.setCodSector(seleccionSector != null ? seleccionSector.getTipValor().getCodigo() : null);

            filtro.setNivelAgrupamiento(seleccionAgrupamiento);

            BigDecimal suma = new BigDecimal("0");


            List<Object[]> resultados = facCalificacion.consultarCalificaciones(filtro);
            lista = new ArrayList<Registro>(resultados.size());
            if (lista.size() < 1) {
                mensajeTablaVacia = Utilidades.obtenerMensaje("aplicacion.general.noExistenRegistros");
            }
            Registro registro = null;
            String valor = "";
            for (Object[] fila : resultados) {
                suma = BigDecimal.ZERO;
                registro = new Registro();
                registro.setNivel(seleccionAgrupamiento);
                int i = 0;
                HashMap<String, String> hmAux = new HashMap<String, String>();
                if (seleccionAgrupamiento.equals(ParamNivelConsul.PREDIO.getNivel())) {                    
                    registro.setId(UtilConsulta.longValueOf(fila[i++]));                    
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
                } else if (seleccionAgrupamiento.equals(ParamNivelConsul.CERO.getNivel())) {
                    registro.setId(UtilConsulta.longValueOf(fila[i++]));                    
                    registro.setNivel0(UtilConsulta.stringValueOf(fila[i++]));
                } else if (seleccionAgrupamiento.equals(ParamNivelConsul.UNO.getNivel())) {
                    registro.setId(UtilConsulta.longValueOf(fila[i++]));
                    registro.setCodNivel1(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCod(registro.getCodNivel1());
                } else if (seleccionAgrupamiento.equals(ParamNivelConsul.DOS.getNivel())) {
                    registro.setId(UtilConsulta.longValueOf(fila[i++]));
                    registro.setCodNivel1(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCodNivel2(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel2(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCod(registro.getCodNivel2());
                } else if (seleccionAgrupamiento.equals(ParamNivelConsul.TRES.getNivel())) {
                    registro.setId(UtilConsulta.longValueOf(fila[i++]));
                    registro.setCodNivel1(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCodNivel2(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel2(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCodNivel3(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel3(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCod(registro.getCodNivel3());
                } else if (seleccionAgrupamiento.equals(ParamNivelConsul.CUATRO.getNivel())) {
                    registro.setId(UtilConsulta.longValueOf(fila[i++]));
                    registro.setCodNivel1(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCodNivel2(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel2(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCodNivel3(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel3(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCodNivel4(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel4(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCod(registro.getCodNivel4());
                } else if (seleccionAgrupamiento.equals(ParamNivelConsul.CINCO.getNivel())) {
                    registro.setId(UtilConsulta.longValueOf(fila[i++]));
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
                    registro.setCod(registro.getCodNivel5());
                } else if (seleccionAgrupamiento.equals(ParamNivelConsul.ESTABLECIMIENTO.getNivel())) {
                    registro.setId(UtilConsulta.longValueOf(fila[i++]));
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
                    registro.setCod(registro.getCodEstablecimiento());
                } else if (seleccionAgrupamiento.equals(ParamNivelConsul.SEDE.getNivel())) {
                    registro.setId(UtilConsulta.longValueOf(fila[i++]));
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
                    registro.setCod(registro.getCodSede());
                }
                valor = fila[i] != null ? fila[i].toString() : "";
                suma = suma.add(fila[i] != null ? (new BigDecimal(hmPonderaciones.get(Ambitos.A_ACC.toString()).toString()).divide(new BigDecimal(100))).multiply(new BigDecimal(fila[i].toString())) : new BigDecimal(0));
                registro.setAccesosExt(UtilConsulta.doubleValueOf(valor));
                i++;
                valor = fila[i] != null ? fila[i].toString() : "";
                suma = suma.add(fila[i] != null ? (new BigDecimal(hmPonderaciones.get(Ambitos.A_ACCD.toString()).toString()).divide(new BigDecimal(100))).multiply(new BigDecimal(fila[i].toString())) : new BigDecimal(0));
                registro.setAccesosInt(UtilConsulta.doubleValueOf(valor));
                i++;
                valor = fila[i] != null ? fila[i].toString() : "";
                suma = suma.add(fila[i] != null ? (new BigDecimal(hmPonderaciones.get(Ambitos.A_AMB.toString()).toString()).divide(new BigDecimal(100))).multiply(new BigDecimal(fila[i].toString())) : new BigDecimal(0));
                registro.setAmbientes(UtilConsulta.doubleValueOf(valor));
                i++;
                valor = fila[i] != null ? fila[i].toString() : "";
                suma = suma.add(fila[i] != null ? (new BigDecimal(hmPonderaciones.get(Ambitos.A_CON.toString()).toString()).divide(new BigDecimal(100))).multiply(new BigDecimal(fila[i].toString())) : new BigDecimal(0));
                registro.setConfort(UtilConsulta.doubleValueOf(valor));
                i++;
                valor = fila[i] != null ? fila[i].toString() : "";
                suma = suma.add(fila[i] != null ? (new BigDecimal(hmPonderaciones.get(Ambitos.A_CV.toString()).toString()).divide(new BigDecimal(100))).multiply(new BigDecimal(fila[i].toString())) : new BigDecimal(0));
                registro.setControlVigilancia(UtilConsulta.doubleValueOf(valor));
                i++;
                valor = fila[i] != null ? fila[i].toString() : "";
                suma = suma.add(fila[i] != null ? (new BigDecimal(hmPonderaciones.get(Ambitos.A_EEE.toString()).toString()).divide(new BigDecimal(100))).multiply(new BigDecimal(fila[i].toString())) : new BigDecimal(0));
                registro.setEstadoEdificiosEspacios(UtilConsulta.doubleValueOf(valor));
                i++;
                valor = fila[i] != null ? fila[i].toString() : "";
                suma = suma.add(fila[i] != null ? (new BigDecimal(hmPonderaciones.get(Ambitos.A_OFE.toString()).toString()).divide(new BigDecimal(100))).multiply(new BigDecimal(fila[i].toString())) : new BigDecimal(0));
                registro.setOferta(UtilConsulta.doubleValueOf(valor));
                i++;
                valor = fila[i] != null ? fila[i].toString() : "";
                suma = suma.add(fila[i] != null ? (new BigDecimal(hmPonderaciones.get(Ambitos.A_PROP.toString()).toString()).divide(new BigDecimal(100))).multiply(new BigDecimal(fila[i].toString())) : new BigDecimal(0));
                registro.setPropiedad(UtilConsulta.doubleValueOf(valor));
                i++;
                valor = fila[i] != null ? "-" + fila[i].toString() : "";
                suma = suma.add(fila[i] != null ? (new BigDecimal(hmPonderaciones.get(Ambitos.A_RI.toString()).toString()).divide(new BigDecimal(100))).multiply(new BigDecimal("-" + fila[i].toString())) : new BigDecimal(0));
                registro.setRiesgos(UtilConsulta.doubleValueOf(valor));
                i++;
                valor = fila[i] != null ? fila[i].toString() : "";
                suma = suma.add(fila[i] != null ? (new BigDecimal(hmPonderaciones.get(Ambitos.A_SEG.toString()).toString()).divide(new BigDecimal(100))).multiply(new BigDecimal(fila[i].toString())) : new BigDecimal(0));
                registro.setSeguridad(UtilConsulta.doubleValueOf(valor));
                i++;
                valor = fila[i] != null ? fila[i].toString() : "";
                suma = suma.add(fila[i] != null ? (new BigDecimal(hmPonderaciones.get(Ambitos.A_SP.toString()).toString()).divide(new BigDecimal(100))).multiply(new BigDecimal(fila[i].toString())) : new BigDecimal(0));
                registro.setServicios(UtilConsulta.doubleValueOf(valor));
                i++;
                valor = fila[i] != null ? fila[i].toString() : "";
                suma = suma.add(fila[i] != null ? (new BigDecimal(hmPonderaciones.get(Ambitos.A_SOS.toString()).toString()).divide(new BigDecimal(100))).multiply(new BigDecimal(fila[i].toString())) : new BigDecimal(0));
                registro.setSostenibilidad(UtilConsulta.doubleValueOf(valor));
                i++;
                valor = fila[i] != null ? fila[i].toString() : "";
                suma = suma.add(fila[i] != null ? (new BigDecimal(hmPonderaciones.get(Ambitos.A_SS.toString()).toString()).divide(new BigDecimal(100))).multiply(new BigDecimal(fila[i].toString())) : new BigDecimal(0));
                registro.setServiciosSanitarios(UtilConsulta.doubleValueOf(valor));

                registro.setCalificacionTotal(suma.doubleValue());
                hmAux.put(columnaCalificacionTotal, suma.setScale(2, RoundingMode.HALF_UP).toString());//String.format("%.2f", suma));

                lista.add(registro);
            }

        } catch (Exception e) {
            //UtilOut.println(e);
            ManejadorErrores.ingresarExcepcionEnLog(e, ConsultaCalificacionBean.class);
            mostrarMensajeError(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"), e.getMessage());
        }
    }

    public class Registro {

        private Long nivel;
        private Long id;
        private String codEstablecimiento;
        private String nomEstablecimiento;
        private String codSede;
        private String nomSede;
        private String codPredio;
        private String nomPredio;
        private String nivel0;
        private String nivel1;
        private String nivel2;
        private String nivel3;
        private String nivel4;
        private String nivel5;
        private String codNivel1;
        private String codNivel2;
        private String codNivel3;
        private String codNivel4;
        private String codNivel5;
        private String cod;
        private String zona;
        private String sector;
        private Double accesosExt;
        private Double accesosInt;
        private Double ambientes;
        private Double confort;
        private Double controlVigilancia;
        private Double estadoEdificiosEspacios;
        private Double oferta;
        private Double propiedad;
        private Double riesgos;
        private Double seguridad;
        private Double servicios;
        private Double sostenibilidad;
        private Double serviciosSanitarios;
        private Double calificacionTotal;

        public Long getNivel() {
            return nivel;
        }

        public void setNivel(Long nivel) {
            this.nivel = nivel;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

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

        public String getZona() {
            return zona;
        }

        public void setZona(String zona) {
            this.zona = zona;
        }

        public String getSector() {
            return sector;
        }

        public void setSector(String sector) {
            this.sector = sector;
        }

        public Double getAccesosExt() {
            return accesosExt;
        }

        public void setAccesosExt(Double accesosExt) {
            this.accesosExt = accesosExt;
        }

        public Double getAccesosInt() {
            return accesosInt;
        }

        public void setAccesosInt(Double accesosInt) {
            this.accesosInt = accesosInt;
        }

        public Double getAmbientes() {
            return ambientes;
        }

        public void setAmbientes(Double ambientes) {
            this.ambientes = ambientes;
        }

        public Double getConfort() {
            return confort;
        }

        public void setConfort(Double confort) {
            this.confort = confort;
        }

        public Double getControlVigilancia() {
            return controlVigilancia;
        }

        public void setControlVigilancia(Double controlVigilancia) {
            this.controlVigilancia = controlVigilancia;
        }

        public Double getEstadoEdificiosEspacios() {
            return estadoEdificiosEspacios;
        }

        public void setEstadoEdificiosEspacios(Double estadoEdificiosEspacios) {
            this.estadoEdificiosEspacios = estadoEdificiosEspacios;
        }

        public Double getOferta() {
            return oferta;
        }

        public void setOferta(Double oferta) {
            this.oferta = oferta;
        }

        public Double getPropiedad() {
            return propiedad;
        }

        public void setPropiedad(Double propiedad) {
            this.propiedad = propiedad;
        }

        public Double getRiesgos() {
            return riesgos;
        }

        public void setRiesgos(Double riesgos) {
            this.riesgos = riesgos;
        }

        public Double getSeguridad() {
            return seguridad;
        }

        public void setSeguridad(Double seguridad) {
            this.seguridad = seguridad;
        }

        public Double getServicios() {
            return servicios;
        }

        public void setServicios(Double servicios) {
            this.servicios = servicios;
        }

        public Double getSostenibilidad() {
            return sostenibilidad;
        }

        public void setSostenibilidad(Double sostenibilidad) {
            this.sostenibilidad = sostenibilidad;
        }

        public Double getServiciosSanitarios() {
            return serviciosSanitarios;
        }

        public void setServiciosSanitarios(Double serviciosSanitarios) {
            this.serviciosSanitarios = serviciosSanitarios;
        }

        public Double getCalificacionTotal() {
            return calificacionTotal;
        }

        public void setCalificacionTotal(Double calificacionTotal) {
            this.calificacionTotal = calificacionTotal;
        }

        public String getCod() {
            return cod;
        }

        public void setCod(String cod) {
            this.cod = cod;
        }
    }

    public List<Registro> getLista() {
        return lista;
    }

    public void setLista(List<Registro> lista) {
        this.lista = lista;
    }

    public void setListaAgrupamientos(Map<Long, String> listaAgrupamientos) {
        this.listaAgrupamientos = listaAgrupamientos;
    }

    public boolean isConsultaPredio() {
        return ParamNivelConsul.PREDIO.getNivel().equals(seleccionAgrupamiento);
    }

    public String separarPonderacion(String dato) {
        if (dato.indexOf("%") >= 0) {
            return dato.substring(0, dato.indexOf("%") + 1);
        } else {
            return "";
        }

    }

    public String separarNombre(String dato) {
        if (dato.indexOf("%") >= 0) {
            return dato.substring(dato.indexOf("%") + 1, dato.length());
        } else {
            return dato;
        }

    }

    public boolean isCalificacionOK(String condicion) {
        Double cal = Double.valueOf(condicion);
        if (cal > 80.00) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isCalificacionRegular(String condicion) {
        Double cal = Double.valueOf(condicion);
        if (cal > 40.00 && cal <= 80.00) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isCalificacionNoOK(String condicion) {
        Double cal = Double.valueOf(condicion);
        if (cal <= 40.00) {
            return true;
        } else {
            return false;
        }
    }

    public ConsultaAmbito getConsultaAmbito() {
        return consultaAmbito;
    }

    public void setConsultaAmbito(ConsultaAmbito consultaAmbito) {
        this.consultaAmbito = consultaAmbito;
    }

    public void postProcessXLS(Object document) {
        try {
            HSSFWorkbook wb = (HSSFWorkbook) document;
            HSSFSheet sheet = wb.getSheetAt(0);
            HSSFRow header = sheet.getRow(0);
            for (int i = 0; i < selccionCamposOpcionales.size(); i++) {
                header.getCell(i).setCellValue(selccionCamposOpcionales.get(i));
            }
            new GestionArchivos().postProcessXLS(document);
        } catch (Exception e) {
            ManejadorErrores.ingresarExcepcionEnLog(e, ConsultaCalificacionBean.class);
        }
    }

    public String getColumnaCalificacionTotal() {
        return columnaCalificacionTotal;
    }

    public String getColumnaAccesosExt() {
        return columnaAccesosExt;
    }

    public String getColumnaAccesosInt() {
        return columnaAccesosInt;
    }

    public String getColumnaAmbientes() {
        return columnaAmbientes;
    }

    public String getColumnaConfort() {
        return columnaConfort;
    }

    public String getColumnaControlVigilancia() {
        return columnaControlVigilancia;
    }

    public String getColumnaEstadoEdificiosEspacios() {
        return columnaEstadoEdificiosEspacios;
    }

    public String getColumnaOferta() {
        return columnaOferta;
    }

    public String getColumnaPropiedad() {
        return columnaPropiedad;
    }

    public String getColumnaRiesgos() {
        return columnaRiesgos;
    }

    public String getColumnaSeguridad() {
        return columnaSeguridad;
    }

    public String getColumnaServicios() {
        return columnaServicios;
    }

    public String getColumnaSostenibilidad() {
        return columnaSostenibilidad;
    }

    public String getColumnaServiciosSanitarios() {
        return columnaServiciosSanitarios;
    }   
    
}
