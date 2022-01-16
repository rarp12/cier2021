/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.consulta.informeGeneral;

import co.stackpointer.cier.vistacontrolador.consulta.informeGeneral.predio.ControlVigilancia;
import co.stackpointer.cier.vistacontrolador.consulta.informeGeneral.predio.ReporteInformeGeneralPredio;
import co.stackpointer.cier.vistacontrolador.consulta.informeGeneral.predio.EstablecimientosAdicionales;
import co.stackpointer.cier.vistacontrolador.consulta.informeGeneral.predio.SeguridadAccesibilidad;
import co.stackpointer.cier.modelo.entidad.digitado.InstrumentoDig;
import co.stackpointer.cier.modelo.entidad.dpa.Nivel;
import co.stackpointer.cier.modelo.entidad.establecimiento.Establecimiento;
import co.stackpointer.cier.modelo.entidad.establecimiento.Sede;
import co.stackpointer.cier.modelo.entidad.instrumento.ModuloIns;
import co.stackpointer.cier.modelo.entidad.instrumento.Pregunta;
import co.stackpointer.cier.modelo.entidad.instrumento.Respuesta;
import co.stackpointer.cier.modelo.entidad.instrumento.Seccion;
import co.stackpointer.cier.modelo.entidad.instrumento.TipologiaValorNombre;
import co.stackpointer.cier.modelo.fachada.CalificacionFacadeLocal;
import co.stackpointer.cier.modelo.fachada.ConsultaAmbitoFacadeLocal;
import co.stackpointer.cier.modelo.fachada.ConsultaBasicaFacadeLocal;
import co.stackpointer.cier.modelo.fachada.DPAFacadeLocal;
import co.stackpointer.cier.modelo.fachada.EstablecimientoFacadeLocal;
import co.stackpointer.cier.modelo.fachada.InformeGeneralFacadeLocal;
import co.stackpointer.cier.modelo.fachada.InstrumentoFacadeLocal;
import co.stackpointer.cier.modelo.filtro.ambito.FiltroAmbitoRedistribucion;
import co.stackpointer.cier.modelo.filtro.calificacion.FiltroCalificacion;
import co.stackpointer.cier.modelo.tipo.Ambitos;
import co.stackpointer.cier.modelo.tipo.CalificacionCondicion;
import co.stackpointer.cier.modelo.tipo.Cumplimiento;
import co.stackpointer.cier.modelo.tipo.NivelEnsenanza;
import co.stackpointer.cier.modelo.tipo.ParamEstandar;
import co.stackpointer.cier.modelo.tipo.ParamIndicador;
import co.stackpointer.cier.modelo.tipo.ParamNivelConsul;
import co.stackpointer.cier.modelo.tipo.ServiciosPublicos;
import co.stackpointer.cier.modelo.tipo.SiNo;
import co.stackpointer.cier.modelo.tipo.SiNoNA;
import co.stackpointer.cier.modelo.tipo.UnidadFuncionalAmbiente;
import co.stackpointer.cier.modelo.tipo.Vulnerabilidad;
import co.stackpointer.cier.modelo.utilidad.UtilCadena;
import co.stackpointer.cier.modelo.utilidad.UtilConsulta;
import co.stackpointer.cier.modelo.utilidad.UtilExportar;
import co.stackpointer.cier.modelo.utilidad.UtilOut;
import co.stackpointer.cier.vista.ManejadorErrores;
import co.stackpointer.cier.vista.Utilidades;
import static co.stackpointer.cier.vista.Utilidades.mostrarMensajeError;
import co.stackpointer.cier.vistacontrolador.consulta.ConsultaBase;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import net.sf.jasperreports.engine.JRParameter;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author user
 */
@ManagedBean(name = "InformeGeneralBean")
@ViewScoped
public class InformeGeneralBean extends ConsultaBase {

    @EJB
    private ConsultaBasicaFacadeLocal facBasica;
    @EJB
    InstrumentoFacadeLocal facInstrumentos;
    @EJB
    private EstablecimientoFacadeLocal facEstablecimientos;
    @EJB
    private InformeGeneralFacadeLocal facInformeGeneral;
    @EJB
    public CalificacionFacadeLocal facCalificacion;
    @EJB
    private DPAFacadeLocal facDPA;
    @EJB
    protected ConsultaAmbitoFacadeLocal facAmbito;
    private Map<String, Object> parametros;
    private ResourceBundle bundle;
    private List<String> respuestasEdificios;
    private List<String> respuestasEspaciosEscolares;
    private List<String> respuestasEstablecimientosAdicionales;
    HashMap<String, String> respuestaTipologia;

    @PostConstruct
    public void inicializar() {
        try {
            super.initActual();
            parametros = new HashMap<String, Object>();
            bundle = ResourceBundle.getBundle("etiquetas", FacesContext.getCurrentInstance().getViewRoot().getLocale());
            respuestasEdificios = new ArrayList<String>();
            respuestasEspaciosEscolares = new ArrayList<String>();
            respuestasEstablecimientosAdicionales = new ArrayList<String>();
            respuestaTipologia = new HashMap<String, String>();
        } catch (Exception e) {
            ManejadorErrores.ingresarExcepcionEnLog(e, InformeGeneralBean.class);
        }
    }

    public StreamedContent imprimirPdf(Long nivelConsulta, Long id, String cod, String tipoDoc) {
        StreamedContent resp = null;
        try {
            if (nivelConsulta.equals(ParamNivelConsul.PREDIO.getNivel())) {
                resp = imprimirPdfPredio(id);
            } else if (nivelConsulta.equals(ParamNivelConsul.SEDE.getNivel())) {
                resp = imprimirPdfSede(id, cod);
            } else if (nivelConsulta.equals(ParamNivelConsul.ESTABLECIMIENTO.getNivel())) {
                resp = imprimirPdfEstablecimiento(id, cod);
            } else if ("Totales".equals(tipoDoc)) {
                resp = imprimirPdfDPATotales(nivelConsulta, id, cod);
            } else {
                resp = imprimirPdfDPADetallado(nivelConsulta, id, cod);
            }
        } catch (Exception e) {
            ManejadorErrores.ingresarExcepcionEnLog(e, InformeGeneralBean.class);
        }
        return resp;
    }

    private StreamedContent imprimirPdfPredio(Long idDigInstrumento) {
        parametros = new HashMap<String, Object>();
        InputStream stream = null;
        try {
            InstrumentoDig insDig = facInstrumentos.consultarInstrumentoPorCodigo(idDigInstrumento);
            parametros = obtenerRespuestas(insDig);

            if (isActivoNivel1()) {
                parametros.put("nivel1", getListaNivelesConf().get(new Long(1)));
            }
            if (isActivoNivel2()) {
                parametros.put("nivel2", getListaNivelesConf().get(new Long(2)));
            }
            if (isActivoNivel3()) {
                parametros.put("nivel3", getListaNivelesConf().get(new Long(3)));
            }
            if (isActivoNivel4()) {
                parametros.put("nivel4", getListaNivelesConf().get(new Long(4)));
            }
            if (isActivoNivel5()) {
                parametros.put("nivel5", getListaNivelesConf().get(new Long(5)));
            }

            //Cambiamos los campos que corresponden a DPA
            String[] respuestasValoresDPA = {"RESP_031_02", "RESP_031_03", "RESP_031_04", "RESP_031_05", "RESP_031_06"};
            Nivel n = null;
            for (String resp : respuestasValoresDPA) {
                if (parametros.containsKey(resp)) {
                    n = new Nivel();
                    n = facDPA.getNivelPorId(new Long(parametros.get(resp).toString()));
                    parametros.put(resp, n != null ? n.getDescripcion() : "");
                }
            }

            //RESP_054_04-> Si no tiene candición de cerco perimetral se le pone 1
            if (!parametros.containsKey("RESP_054_04")) {
                parametros.put("RESP_054_04", "1");
            }

            //Cambiamos las condiciones de tipología por sus descripciones
            String[] respuestasCondiciones = {"RESP_045_02", "RESP_045_04", "RESP_045_06", "RESP_045_08", "RESP_045_10", "RESP_048_02", "RESP_048_04", "RESP_048_06", "RESP_050_02", "RESP_050_04", "RESP_050_06", "RESP_054_04", "RESP_061_05", "RESP_062_05", "RESP_063_05", "RESP_064_05", "RESP_066_05", "RESP_067_05", "RESP_068_05", "RESP_069_05"};
            for (String resp : respuestasCondiciones) {
                if (parametros.containsKey(resp)) {
                    if (!UtilCadena.isNullOVacio(parametros.get(resp))) {
                        parametros.put(resp, Utilidades.obtenerMensaje("tipologias.condicion." + facInformeGeneral.consultarCodTipologiaRespuesta(resp) + "_" + parametros.get(resp).toString()));
                    }
                }
            }
            //RESP_065_02-> Sistema de Reciclaje (No tiene tipolog'ia asociada así que usa la de Agua)
            if (parametros.containsKey("RESP_065_02")) {
                if (!UtilCadena.isNullOVacio(parametros.get("RESP_065_02"))) {
                    parametros.put("RESP_065_02", Utilidades.obtenerMensaje("tipologias.condicion." + ServiciosPublicos.DSA.toString() + "_" + parametros.get("RESP_065_02").toString()));
                }
            }

            //Cambiamos de No verificable a Verificable
            if (!parametros.containsKey("RESP_055_02")) {
                parametros.put("RESP_055_02", "1");
            } else if ("0".equals(parametros.get("RESP_055_02"))) {
                parametros.put("RESP_055_02", "1");
            } else if ("1".equals(parametros.get("RESP_055_02"))) {
                parametros.put("RESP_055_02", "0");
            }

            //Respuesta Si o No
            String[] respuestasSiNo = {"RESP_054_01", "RESP_055_01", "RESP_055_02", "RESP_058", "RESP_065_01"};
            for (String resp : respuestasSiNo) {
                if (parametros.containsKey(resp)) {
                    parametros.put(resp, transformaSiNo(parametros.get(resp) + ""));
                }
            }

            List<String> indicadores = new ArrayList<String>();
            indicadores.add(ParamIndicador.IND_AMPLIACION_AREA_CONSTRUCCION_PRIMER_PISO.name());
            indicadores.add(ParamIndicador.IND_AMPLIACION_POSIBILIDAD_CONSTRUCCION_ALTURA.name());
            indicadores.add(ParamIndicador.IND_AREA_REAL_PREDIO.name());
            indicadores.add(ParamIndicador.IND_TOTAL_AREA_CONSTRUCCION_PREDIO.name());
            indicadores.add(ParamIndicador.IND_AMPLIACION_M2_REQUERIDOS_PREDIO.name());
            indicadores.add(ParamIndicador.IND_AMPLIACION_POSIBILIDAD_CONSTRUCCION_PRIMER_PISO.name());
            indicadores.add(ParamIndicador.IND_AMPLIACION_POSIBILIDAD_CONSTRUCCION_TOTAL.name());
            indicadores.add(ParamIndicador.IND_CONTROL_VIGILANCIA_VULNERABILIDAD.name());
            indicadores.add(ParamIndicador.IND_AMPLIACION_IDONEIDAD_PREDIO.name());
            indicadores.add(ParamIndicador.IND_ACCESIBILIDAD_CONDICION_ACCESIBILIDAD.name());
            indicadores.add(ParamIndicador.IND_AMPLIACION_M2_AMPLIAR_COBERTURA.name());
            indicadores.add(ParamIndicador.IND_AMPLIACION_ALUMNOS_NUEVOS_INFRAESTRUCTURA.name());
            List<Object[]> areas = facParam.consultarIndicadores(indicadores, ParamNivelConsul.PREDIO.getNivel(), idDigInstrumento);
            if (!areas.isEmpty()) {
                parametros.put("construcPrimerPiso", new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[0]) ? "0" : (String) areas.get(0)[0]).setScale(2, RoundingMode.HALF_UP));
                parametros.put("posibilidadConstrucAltura", new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[1]) ? "0" : (String) areas.get(0)[1]).setScale(2, RoundingMode.HALF_UP));
                parametros.put("area_predio", new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[2]) ? "0" : (String) areas.get(0)[2]).setScale(2, RoundingMode.HALF_UP));
                parametros.put("indice_ocupacion", (UtilCadena.isNullOVacio((String) areas.get(0)[0]) || UtilCadena.isNullOVacio((String) areas.get(0)[2]) || new BigDecimal(areas.get(0)[2] + "").compareTo(BigDecimal.ZERO) == 0 ? new BigDecimal("0") : new BigDecimal(areas.get(0)[0] + "").divide(new BigDecimal(areas.get(0)[2] + ""), 4, RoundingMode.HALF_UP).multiply(new BigDecimal("100")).setScale(2, RoundingMode.HALF_UP)) + "%");
                parametros.put("indice_construccion", (UtilCadena.isNullOVacio((String) areas.get(0)[3]) || UtilCadena.isNullOVacio((String) areas.get(0)[2]) || new BigDecimal(areas.get(0)[2] + "").compareTo(BigDecimal.ZERO) == 0 ? new BigDecimal("0") : new BigDecimal(areas.get(0)[3] + "").divide(new BigDecimal(areas.get(0)[2] + ""), 4, RoundingMode.HALF_UP).multiply(new BigDecimal("100")).setScale(2, RoundingMode.HALF_UP)) + "%");
                parametros.put("m2RequeridosPredio", new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[4]) ? "0" : (String) areas.get(0)[4]).setScale(2, RoundingMode.HALF_UP));
                parametros.put("construible_primer_piso", new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[5]) ? "0" : (String) areas.get(0)[5]).setScale(2, RoundingMode.HALF_UP));
                parametros.put("construible_total", new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[6]) ? "0" : (String) areas.get(0)[6]).setScale(2, RoundingMode.HALF_UP));
                parametros.put("vulnerabilidad", UtilCadena.isNullOVacio((String) areas.get(0)[7]) ? null : Utilidades.obtenerMensaje((String) areas.get(0)[7]));
                parametros.put("idoneidad", Utilidades.obtenerMensaje("aplicacion.general." + (areas.get(0)[8] != null && new Double(areas.get(0)[8] + "") > new Double(100) ? "cumple" : "noCumple")));
                parametros.put("condAccesibilidad", UtilCadena.isNullOVacio((String) areas.get(0)[9]) ? null : Utilidades.obtenerMensaje((String) areas.get(0)[9]));
                parametros.put("m2AmpliarCobertura", UtilCadena.isNullOVacio((String) areas.get(0)[10]) ? null : new BigDecimal((String) areas.get(0)[10]).setScale(2, RoundingMode.HALF_UP) + "");
                parametros.put("alumnosNuevosInfraestructura", UtilCadena.isNullOVacio((String) areas.get(0)[11]) ? null : new BigDecimal((String) areas.get(0)[11]).setScale(2, RoundingMode.HALF_UP) + "");

            }

            List<String[]> indBusqUnidadesFun = new ArrayList<String[]>();
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_PISO.name(), CalificacionCondicion.CUATRO.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_PISO.name(), CalificacionCondicion.TRES.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_PISO.name(), CalificacionCondicion.DOS.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_PISO.name(), CalificacionCondicion.UNO.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_MURO.name(), CalificacionCondicion.CUATRO.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_MURO.name(), CalificacionCondicion.TRES.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_MURO.name(), CalificacionCondicion.DOS.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_MURO.name(), CalificacionCondicion.UNO.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_ACABADO_MURO.name(), CalificacionCondicion.CUATRO.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_ACABADO_MURO.name(), CalificacionCondicion.TRES.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_ACABADO_MURO.name(), CalificacionCondicion.DOS.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_ACABADO_MURO.name(), CalificacionCondicion.UNO.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_TECHO.name(), CalificacionCondicion.CUATRO.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_TECHO.name(), CalificacionCondicion.TRES.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_TECHO.name(), CalificacionCondicion.DOS.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_TECHO.name(), CalificacionCondicion.UNO.getValor() + ""});

            areas = facParam.consultarIndicadoresUnidadesFuncionales(indBusqUnidadesFun, ParamNivelConsul.PREDIO.getNivel(), idDigInstrumento);
            int i = 0;
            if (!areas.isEmpty()) {
                parametros.put("areaPisosCond4", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? new BigDecimal("0") : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                i++;
                parametros.put("areaPisosCond3", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? new BigDecimal("0") : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                i++;
                parametros.put("areaPisosCond2", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? new BigDecimal("0") : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                i++;
                parametros.put("areaPisosCond1", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? new BigDecimal("0") : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                i++;
                parametros.put("areaMurosCond4", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? new BigDecimal("0") : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                i++;
                parametros.put("areaMurosCond3", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? new BigDecimal("0") : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                i++;
                parametros.put("areaMurosCond2", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? new BigDecimal("0") : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                i++;
                parametros.put("areaMurosCond1", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? new BigDecimal("0") : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                i++;
                parametros.put("areaAcabadosMuroCond4", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? new BigDecimal("0") : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                i++;
                parametros.put("areaAcabadosMuroCond3", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? new BigDecimal("0") : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                i++;
                parametros.put("areaAcabadosMuroCond2", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? new BigDecimal("0") : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                i++;
                parametros.put("areaAcabadosMuroCond1", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? new BigDecimal("0") : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                i++;
                parametros.put("areaCieloRasosCond4", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? new BigDecimal("0") : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                i++;
                parametros.put("areaCieloRasosCond3", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? new BigDecimal("0") : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                i++;
                parametros.put("areaCieloRasosCond2", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? new BigDecimal("0") : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                i++;
                parametros.put("areaCieloRasosCond1", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? new BigDecimal("0") : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                i++;
            }

            parametros.put("areasVerdes", facInformeGeneral.obtenerAreasVerdes(idDigInstrumento));
            boolean consumoEnergiaEncimaPromedio = facInformeGeneral.consumoEnergiaEncimaPromedio(idDigInstrumento);
            boolean consumoAguaEncimaPromedio = facInformeGeneral.consumoAguaEncimaPromedio(idDigInstrumento);
            parametros.put("consumoEnergia", consumoEnergiaEncimaPromedio ? bundle.getString("informe.general.reporte.encimaPromedio") : bundle.getString("informe.general.reporte.debajoPromedio"));
            parametros.put("consumoAgua", consumoAguaEncimaPromedio ? bundle.getString("informe.general.reporte.encimaPromedio") : bundle.getString("informe.general.reporte.debajoPromedio"));
            try {
                parametros.put("areaConstrucAltura", facInformeGeneral.areaConstruccionAltura(idDigInstrumento));
            } catch (Exception e) {

            }
            this.anadirCalificaciones(idDigInstrumento, ParamNivelConsul.PREDIO.getNivel());
            this.anadirRedistribucionPredio(idDigInstrumento);

            List<EstablecimientosAdicionales> listaEstablecimientosAdicionales = obtenerEstablecimientosAdicionales(idDigInstrumento);
            List edif = obtenerObjetosEdificios(idDigInstrumento);
            List<ControlVigilancia> listaControlVigilancia = (List) edif.get(0);
            List<SeguridadAccesibilidad> listaSeguridadAccesibilidad = (List) edif.get(1);
            HashMap datosMaterial = (HashMap) edif.get(2);

            parametros.put("CondMat1", Utilidades.obtenerMensaje("tipologias.condicion." + "EMPMF_1"));
            parametros.put("CondMat2", Utilidades.obtenerMensaje("tipologias.condicion." + "EMPMF_2"));
            parametros.put("CondMat3", Utilidades.obtenerMensaje("tipologias.condicion." + "EMPMF_3"));
            parametros.put("CondMat4", Utilidades.obtenerMensaje("tipologias.condicion." + "EMPMF_4"));

            parametros.put("murosFachadasValor1", (new BigDecimal(((Integer) datosMaterial.get("murosFachadasValor1")).equals(new Integer("0")) ? 0 : new Double(100 * (Integer) datosMaterial.get("murosFachadasValor1")) / new Double((Integer) datosMaterial.get("murosFachadasValor1") + (Integer) datosMaterial.get("murosFachadasValor2") + (Integer) datosMaterial.get("murosFachadasValor3") + (Integer) datosMaterial.get("murosFachadasValor4"))).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("murosFachadasValor2", (new BigDecimal(((Integer) datosMaterial.get("murosFachadasValor2")).equals(new Integer("0")) ? 0 : new Double(100 * (Integer) datosMaterial.get("murosFachadasValor2")) / new Double((Integer) datosMaterial.get("murosFachadasValor1") + (Integer) datosMaterial.get("murosFachadasValor2") + (Integer) datosMaterial.get("murosFachadasValor3") + (Integer) datosMaterial.get("murosFachadasValor4"))).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("murosFachadasValor3", (new BigDecimal(((Integer) datosMaterial.get("murosFachadasValor3")).equals(new Integer("0")) ? 0 : new Double(100 * (Integer) datosMaterial.get("murosFachadasValor3")) / new Double((Integer) datosMaterial.get("murosFachadasValor1") + (Integer) datosMaterial.get("murosFachadasValor2") + (Integer) datosMaterial.get("murosFachadasValor3") + (Integer) datosMaterial.get("murosFachadasValor4"))).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("murosFachadasValor4", (new BigDecimal(((Integer) datosMaterial.get("murosFachadasValor4")).equals(new Integer("0")) ? 0 : new Double(100 * (Integer) datosMaterial.get("murosFachadasValor4")) / new Double((Integer) datosMaterial.get("murosFachadasValor1") + (Integer) datosMaterial.get("murosFachadasValor2") + (Integer) datosMaterial.get("murosFachadasValor3") + (Integer) datosMaterial.get("murosFachadasValor4"))).setScale(2, RoundingMode.HALF_UP)) + "%");

            parametros.put("acabadosFachadasValor1", (new BigDecimal(((Integer) datosMaterial.get("acabadosFachadasValor1")).equals(new Integer("0")) ? 0 : new Double(100 * (Integer) datosMaterial.get("acabadosFachadasValor1")) / new Double((Integer) datosMaterial.get("acabadosFachadasValor1") + (Integer) datosMaterial.get("acabadosFachadasValor2") + (Integer) datosMaterial.get("acabadosFachadasValor3") + (Integer) datosMaterial.get("acabadosFachadasValor4"))).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("acabadosFachadasValor2", (new BigDecimal(((Integer) datosMaterial.get("acabadosFachadasValor2")).equals(new Integer("0")) ? 0 : new Double(100 * (Integer) datosMaterial.get("acabadosFachadasValor2")) / new Double((Integer) datosMaterial.get("acabadosFachadasValor1") + (Integer) datosMaterial.get("acabadosFachadasValor2") + (Integer) datosMaterial.get("acabadosFachadasValor3") + (Integer) datosMaterial.get("acabadosFachadasValor4"))).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("acabadosFachadasValor3", (new BigDecimal(((Integer) datosMaterial.get("acabadosFachadasValor3")).equals(new Integer("0")) ? 0 : new Double(100 * (Integer) datosMaterial.get("acabadosFachadasValor3")) / new Double((Integer) datosMaterial.get("acabadosFachadasValor1") + (Integer) datosMaterial.get("acabadosFachadasValor2") + (Integer) datosMaterial.get("acabadosFachadasValor3") + (Integer) datosMaterial.get("acabadosFachadasValor4"))).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("acabadosFachadasValor4", (new BigDecimal(((Integer) datosMaterial.get("acabadosFachadasValor4")).equals(new Integer("0")) ? 0 : new Double(100 * (Integer) datosMaterial.get("acabadosFachadasValor4")) / new Double((Integer) datosMaterial.get("acabadosFachadasValor1") + (Integer) datosMaterial.get("acabadosFachadasValor2") + (Integer) datosMaterial.get("acabadosFachadasValor3") + (Integer) datosMaterial.get("acabadosFachadasValor4"))).setScale(2, RoundingMode.HALF_UP)) + "%");

            parametros.put("cubiertasValor1", (new BigDecimal(((Integer) datosMaterial.get("cubiertasValor1")).equals(new Integer("0")) ? 0 : new Double(100 * (Integer) datosMaterial.get("cubiertasValor1")) / new Double((Integer) datosMaterial.get("cubiertasValor1") + (Integer) datosMaterial.get("cubiertasValor2") + (Integer) datosMaterial.get("cubiertasValor3") + (Integer) datosMaterial.get("cubiertasValor4"))).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("cubiertasValor2", (new BigDecimal(((Integer) datosMaterial.get("cubiertasValor2")).equals(new Integer("0")) ? 0 : new Double(100 * (Integer) datosMaterial.get("cubiertasValor2")) / new Double((Integer) datosMaterial.get("cubiertasValor1") + (Integer) datosMaterial.get("cubiertasValor2") + (Integer) datosMaterial.get("cubiertasValor3") + (Integer) datosMaterial.get("cubiertasValor4"))).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("cubiertasValor3", (new BigDecimal(((Integer) datosMaterial.get("cubiertasValor3")).equals(new Integer("0")) ? 0 : new Double(100 * (Integer) datosMaterial.get("cubiertasValor3")) / new Double((Integer) datosMaterial.get("cubiertasValor1") + (Integer) datosMaterial.get("cubiertasValor2") + (Integer) datosMaterial.get("cubiertasValor3") + (Integer) datosMaterial.get("cubiertasValor4"))).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("cubiertasValor4", (new BigDecimal(((Integer) datosMaterial.get("cubiertasValor4")).equals(new Integer("0")) ? 0 : new Double(100 * (Integer) datosMaterial.get("cubiertasValor4")) / new Double((Integer) datosMaterial.get("cubiertasValor1") + (Integer) datosMaterial.get("cubiertasValor2") + (Integer) datosMaterial.get("cubiertasValor3") + (Integer) datosMaterial.get("cubiertasValor4"))).setScale(2, RoundingMode.HALF_UP)) + "%");

            HashMap datosEspacios = obtenerObjetosEspaciosEscolaresPredios(idDigInstrumento);

            parametros.put("pisosValor1", (((Integer) datosEspacios.get("pisosValor1") + (Integer) datosEspacios.get("pisosValor2") + (Integer) datosEspacios.get("pisosValor3") + (Integer) datosEspacios.get("pisosValor4")) == 0 ? 0 : new BigDecimal(new Double(100 * (Integer) datosEspacios.get("pisosValor1")) / new Double((Integer) datosEspacios.get("pisosValor1") + (Integer) datosEspacios.get("pisosValor2") + (Integer) datosEspacios.get("pisosValor3") + (Integer) datosEspacios.get("pisosValor4"))).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("pisosValor2", (((Integer) datosEspacios.get("pisosValor1") + (Integer) datosEspacios.get("pisosValor2") + (Integer) datosEspacios.get("pisosValor3") + (Integer) datosEspacios.get("pisosValor4")) == 0 ? 0 : new BigDecimal(new Double(100 * (Integer) datosEspacios.get("pisosValor2")) / new Double((Integer) datosEspacios.get("pisosValor1") + (Integer) datosEspacios.get("pisosValor2") + (Integer) datosEspacios.get("pisosValor3") + (Integer) datosEspacios.get("pisosValor4"))).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("pisosValor3", (((Integer) datosEspacios.get("pisosValor1") + (Integer) datosEspacios.get("pisosValor2") + (Integer) datosEspacios.get("pisosValor3") + (Integer) datosEspacios.get("pisosValor4")) == 0 ? 0 : new BigDecimal(new Double(100 * (Integer) datosEspacios.get("pisosValor3")) / new Double((Integer) datosEspacios.get("pisosValor1") + (Integer) datosEspacios.get("pisosValor2") + (Integer) datosEspacios.get("pisosValor3") + (Integer) datosEspacios.get("pisosValor4"))).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("pisosValor4", (((Integer) datosEspacios.get("pisosValor1") + (Integer) datosEspacios.get("pisosValor2") + (Integer) datosEspacios.get("pisosValor3") + (Integer) datosEspacios.get("pisosValor4")) == 0 ? 0 : new BigDecimal(new Double(100 * (Integer) datosEspacios.get("pisosValor4")) / new Double((Integer) datosEspacios.get("pisosValor1") + (Integer) datosEspacios.get("pisosValor2") + (Integer) datosEspacios.get("pisosValor3") + (Integer) datosEspacios.get("pisosValor4"))).setScale(2, RoundingMode.HALF_UP)) + "%");

            parametros.put("murosValor1", (((Integer) datosEspacios.get("murosValor1") + (Integer) datosEspacios.get("murosValor2") + (Integer) datosEspacios.get("murosValor3") + (Integer) datosEspacios.get("murosValor4")) == 0 ? 0 : new BigDecimal(new Double(100 * (Integer) datosEspacios.get("murosValor1")) / new Double((Integer) datosEspacios.get("murosValor1") + (Integer) datosEspacios.get("murosValor2") + (Integer) datosEspacios.get("murosValor3") + (Integer) datosEspacios.get("murosValor4"))).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("murosValor2", (((Integer) datosEspacios.get("murosValor1") + (Integer) datosEspacios.get("murosValor2") + (Integer) datosEspacios.get("murosValor3") + (Integer) datosEspacios.get("murosValor4")) == 0 ? 0 : new BigDecimal(new Double(100 * (Integer) datosEspacios.get("murosValor2")) / new Double((Integer) datosEspacios.get("murosValor1") + (Integer) datosEspacios.get("murosValor2") + (Integer) datosEspacios.get("murosValor3") + (Integer) datosEspacios.get("murosValor4"))).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("murosValor3", (((Integer) datosEspacios.get("murosValor1") + (Integer) datosEspacios.get("murosValor2") + (Integer) datosEspacios.get("murosValor3") + (Integer) datosEspacios.get("murosValor4")) == 0 ? 0 : new BigDecimal(new Double(100 * (Integer) datosEspacios.get("murosValor3")) / new Double((Integer) datosEspacios.get("murosValor1") + (Integer) datosEspacios.get("murosValor2") + (Integer) datosEspacios.get("murosValor3") + (Integer) datosEspacios.get("murosValor4"))).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("murosValor4", (((Integer) datosEspacios.get("murosValor1") + (Integer) datosEspacios.get("murosValor2") + (Integer) datosEspacios.get("murosValor3") + (Integer) datosEspacios.get("murosValor4")) == 0 ? 0 : new BigDecimal(new Double(100 * (Integer) datosEspacios.get("murosValor4")) / new Double((Integer) datosEspacios.get("murosValor1") + (Integer) datosEspacios.get("murosValor2") + (Integer) datosEspacios.get("murosValor3") + (Integer) datosEspacios.get("murosValor4"))).setScale(2, RoundingMode.HALF_UP)) + "%");

            parametros.put("acabadosMuroValor1", (((Integer) datosEspacios.get("acabadosMuroValor1") + (Integer) datosEspacios.get("acabadosMuroValor2") + (Integer) datosEspacios.get("acabadosMuroValor3") + (Integer) datosEspacios.get("acabadosMuroValor4")) == 0 ? 0 : new BigDecimal(new Double(100 * (Integer) datosEspacios.get("acabadosMuroValor1")) / new Double((Integer) datosEspacios.get("acabadosMuroValor1") + (Integer) datosEspacios.get("acabadosMuroValor2") + (Integer) datosEspacios.get("acabadosMuroValor3") + (Integer) datosEspacios.get("acabadosMuroValor4"))).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("acabadosMuroValor2", (((Integer) datosEspacios.get("acabadosMuroValor1") + (Integer) datosEspacios.get("acabadosMuroValor2") + (Integer) datosEspacios.get("acabadosMuroValor3") + (Integer) datosEspacios.get("acabadosMuroValor4")) == 0 ? 0 : new BigDecimal(new Double(100 * (Integer) datosEspacios.get("acabadosMuroValor2")) / new Double((Integer) datosEspacios.get("acabadosMuroValor1") + (Integer) datosEspacios.get("acabadosMuroValor2") + (Integer) datosEspacios.get("acabadosMuroValor3") + (Integer) datosEspacios.get("acabadosMuroValor4"))).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("acabadosMuroValor3", (((Integer) datosEspacios.get("acabadosMuroValor1") + (Integer) datosEspacios.get("acabadosMuroValor2") + (Integer) datosEspacios.get("acabadosMuroValor3") + (Integer) datosEspacios.get("acabadosMuroValor4")) == 0 ? 0 : new BigDecimal(new Double(100 * (Integer) datosEspacios.get("acabadosMuroValor3")) / new Double((Integer) datosEspacios.get("acabadosMuroValor1") + (Integer) datosEspacios.get("acabadosMuroValor2") + (Integer) datosEspacios.get("acabadosMuroValor3") + (Integer) datosEspacios.get("acabadosMuroValor4"))).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("acabadosMuroValor4", (((Integer) datosEspacios.get("acabadosMuroValor1") + (Integer) datosEspacios.get("acabadosMuroValor2") + (Integer) datosEspacios.get("acabadosMuroValor3") + (Integer) datosEspacios.get("acabadosMuroValor4")) == 0 ? 0 : new BigDecimal(new Double(100 * (Integer) datosEspacios.get("acabadosMuroValor4")) / new Double((Integer) datosEspacios.get("acabadosMuroValor1") + (Integer) datosEspacios.get("acabadosMuroValor2") + (Integer) datosEspacios.get("acabadosMuroValor3") + (Integer) datosEspacios.get("acabadosMuroValor4"))).setScale(2, RoundingMode.HALF_UP)) + "%");

            parametros.put("ventanasValor1", (((Integer) datosEspacios.get("ventanasValor1") + (Integer) datosEspacios.get("ventanasValor2") + (Integer) datosEspacios.get("ventanasValor3") + (Integer) datosEspacios.get("ventanasValor4")) == 0 ? 0 : new BigDecimal(new Double(100 * (Integer) datosEspacios.get("ventanasValor1")) / new Double((Integer) datosEspacios.get("ventanasValor1") + (Integer) datosEspacios.get("ventanasValor2") + (Integer) datosEspacios.get("ventanasValor3") + (Integer) datosEspacios.get("ventanasValor4"))).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("ventanasValor2", (((Integer) datosEspacios.get("ventanasValor1") + (Integer) datosEspacios.get("ventanasValor2") + (Integer) datosEspacios.get("ventanasValor3") + (Integer) datosEspacios.get("ventanasValor4")) == 0 ? 0 : new BigDecimal(new Double(100 * (Integer) datosEspacios.get("ventanasValor2")) / new Double((Integer) datosEspacios.get("ventanasValor1") + (Integer) datosEspacios.get("ventanasValor2") + (Integer) datosEspacios.get("ventanasValor3") + (Integer) datosEspacios.get("ventanasValor4"))).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("ventanasValor3", (((Integer) datosEspacios.get("ventanasValor1") + (Integer) datosEspacios.get("ventanasValor2") + (Integer) datosEspacios.get("ventanasValor3") + (Integer) datosEspacios.get("ventanasValor4")) == 0 ? 0 : new BigDecimal(new Double(100 * (Integer) datosEspacios.get("ventanasValor3")) / new Double((Integer) datosEspacios.get("ventanasValor1") + (Integer) datosEspacios.get("ventanasValor2") + (Integer) datosEspacios.get("ventanasValor3") + (Integer) datosEspacios.get("ventanasValor4"))).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("ventanasValor4", (((Integer) datosEspacios.get("ventanasValor1") + (Integer) datosEspacios.get("ventanasValor2") + (Integer) datosEspacios.get("ventanasValor3") + (Integer) datosEspacios.get("ventanasValor4")) == 0 ? 0 : new BigDecimal(new Double(100 * (Integer) datosEspacios.get("ventanasValor4")) / new Double((Integer) datosEspacios.get("ventanasValor1") + (Integer) datosEspacios.get("ventanasValor2") + (Integer) datosEspacios.get("ventanasValor3") + (Integer) datosEspacios.get("ventanasValor4"))).setScale(2, RoundingMode.HALF_UP)) + "%");

            parametros.put("puertasValor1", (((Integer) datosEspacios.get("puertasValor1") + (Integer) datosEspacios.get("puertasValor2") + (Integer) datosEspacios.get("puertasValor3") + (Integer) datosEspacios.get("puertasValor4")) == 0 ? 0 : new BigDecimal(new Double(100 * (Integer) datosEspacios.get("puertasValor1")) / new Double((Integer) datosEspacios.get("puertasValor1") + (Integer) datosEspacios.get("puertasValor2") + (Integer) datosEspacios.get("puertasValor3") + (Integer) datosEspacios.get("puertasValor4"))).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("puertasValor2", (((Integer) datosEspacios.get("puertasValor1") + (Integer) datosEspacios.get("puertasValor2") + (Integer) datosEspacios.get("puertasValor3") + (Integer) datosEspacios.get("puertasValor4")) == 0 ? 0 : new BigDecimal(new Double(100 * (Integer) datosEspacios.get("puertasValor2")) / new Double((Integer) datosEspacios.get("puertasValor1") + (Integer) datosEspacios.get("puertasValor2") + (Integer) datosEspacios.get("puertasValor3") + (Integer) datosEspacios.get("puertasValor4"))).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("puertasValor3", (((Integer) datosEspacios.get("puertasValor1") + (Integer) datosEspacios.get("puertasValor2") + (Integer) datosEspacios.get("puertasValor3") + (Integer) datosEspacios.get("puertasValor4")) == 0 ? 0 : new BigDecimal(new Double(100 * (Integer) datosEspacios.get("puertasValor3")) / new Double((Integer) datosEspacios.get("puertasValor1") + (Integer) datosEspacios.get("puertasValor2") + (Integer) datosEspacios.get("puertasValor3") + (Integer) datosEspacios.get("puertasValor4"))).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("puertasValor4", (((Integer) datosEspacios.get("puertasValor1") + (Integer) datosEspacios.get("puertasValor2") + (Integer) datosEspacios.get("puertasValor3") + (Integer) datosEspacios.get("puertasValor4")) == 0 ? 0 : new BigDecimal(new Double(100 * (Integer) datosEspacios.get("puertasValor4")) / new Double((Integer) datosEspacios.get("puertasValor1") + (Integer) datosEspacios.get("puertasValor2") + (Integer) datosEspacios.get("puertasValor3") + (Integer) datosEspacios.get("puertasValor4"))).setScale(2, RoundingMode.HALF_UP)) + "%");

            parametros.put("cielosValor1", (((Integer) datosEspacios.get("cielosValor1") + (Integer) datosEspacios.get("cielosValor2") + (Integer) datosEspacios.get("cielosValor3") + (Integer) datosEspacios.get("cielosValor4")) == 0 ? 0 : new BigDecimal(new Double(100 * (Integer) datosEspacios.get("cielosValor1")) / new Double((Integer) datosEspacios.get("cielosValor1") + (Integer) datosEspacios.get("cielosValor2") + (Integer) datosEspacios.get("cielosValor3") + (Integer) datosEspacios.get("cielosValor4"))).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("cielosValor2", (((Integer) datosEspacios.get("cielosValor1") + (Integer) datosEspacios.get("cielosValor2") + (Integer) datosEspacios.get("cielosValor3") + (Integer) datosEspacios.get("cielosValor4")) == 0 ? 0 : new BigDecimal(new Double(100 * (Integer) datosEspacios.get("cielosValor2")) / new Double((Integer) datosEspacios.get("cielosValor1") + (Integer) datosEspacios.get("cielosValor2") + (Integer) datosEspacios.get("cielosValor3") + (Integer) datosEspacios.get("cielosValor4"))).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("cielosValor3", (((Integer) datosEspacios.get("cielosValor1") + (Integer) datosEspacios.get("cielosValor2") + (Integer) datosEspacios.get("cielosValor3") + (Integer) datosEspacios.get("cielosValor4")) == 0 ? 0 : new BigDecimal(new Double(100 * (Integer) datosEspacios.get("cielosValor3")) / new Double((Integer) datosEspacios.get("cielosValor1") + (Integer) datosEspacios.get("cielosValor2") + (Integer) datosEspacios.get("cielosValor3") + (Integer) datosEspacios.get("cielosValor4"))).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("cielosValor4", (((Integer) datosEspacios.get("cielosValor1") + (Integer) datosEspacios.get("cielosValor2") + (Integer) datosEspacios.get("cielosValor3") + (Integer) datosEspacios.get("cielosValor4")) == 0 ? 0 : new BigDecimal(new Double(100 * (Integer) datosEspacios.get("cielosValor4")) / new Double((Integer) datosEspacios.get("cielosValor1") + (Integer) datosEspacios.get("cielosValor2") + (Integer) datosEspacios.get("cielosValor3") + (Integer) datosEspacios.get("cielosValor4"))).setScale(2, RoundingMode.HALF_UP)) + "%");

            parametros.put("iluminacionValor1", (((Integer) datosEspacios.get("iluminacionValor1") + (Integer) datosEspacios.get("iluminacionValor2") + (Integer) datosEspacios.get("iluminacionValor3") + (Integer) datosEspacios.get("iluminacionValor4")) == 0 ? 0 : new BigDecimal(new Double(100 * (Integer) datosEspacios.get("iluminacionValor1")) / new Double((Integer) datosEspacios.get("iluminacionValor1") + (Integer) datosEspacios.get("iluminacionValor2") + (Integer) datosEspacios.get("iluminacionValor3") + (Integer) datosEspacios.get("iluminacionValor4"))).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("iluminacionValor2", (((Integer) datosEspacios.get("iluminacionValor1") + (Integer) datosEspacios.get("iluminacionValor2") + (Integer) datosEspacios.get("iluminacionValor3") + (Integer) datosEspacios.get("iluminacionValor4")) == 0 ? 0 : new BigDecimal(new Double(100 * (Integer) datosEspacios.get("iluminacionValor2")) / new Double((Integer) datosEspacios.get("iluminacionValor1") + (Integer) datosEspacios.get("iluminacionValor2") + (Integer) datosEspacios.get("iluminacionValor3") + (Integer) datosEspacios.get("iluminacionValor4"))).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("iluminacionValor3", (((Integer) datosEspacios.get("iluminacionValor1") + (Integer) datosEspacios.get("iluminacionValor2") + (Integer) datosEspacios.get("iluminacionValor3") + (Integer) datosEspacios.get("iluminacionValor4")) == 0 ? 0 : new BigDecimal(new Double(100 * (Integer) datosEspacios.get("iluminacionValor3")) / new Double((Integer) datosEspacios.get("iluminacionValor1") + (Integer) datosEspacios.get("iluminacionValor2") + (Integer) datosEspacios.get("iluminacionValor3") + (Integer) datosEspacios.get("iluminacionValor4"))).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("iluminacionValor4", (((Integer) datosEspacios.get("iluminacionValor1") + (Integer) datosEspacios.get("iluminacionValor2") + (Integer) datosEspacios.get("iluminacionValor3") + (Integer) datosEspacios.get("iluminacionValor4")) == 0 ? 0 : new BigDecimal(new Double(100 * (Integer) datosEspacios.get("iluminacionValor4")) / new Double((Integer) datosEspacios.get("iluminacionValor1") + (Integer) datosEspacios.get("iluminacionValor2") + (Integer) datosEspacios.get("iluminacionValor3") + (Integer) datosEspacios.get("iluminacionValor4"))).setScale(2, RoundingMode.HALF_UP)) + "%");

            parametros.put("ventilacionValor1", (((Integer) datosEspacios.get("ventilacionValor1") + (Integer) datosEspacios.get("ventilacionValor2") + (Integer) datosEspacios.get("ventilacionValor3") + (Integer) datosEspacios.get("ventilacionValor4")) == 0 ? 0 : new BigDecimal(new Double(100 * (Integer) datosEspacios.get("ventilacionValor1")) / new Double((Integer) datosEspacios.get("ventilacionValor1") + (Integer) datosEspacios.get("ventilacionValor2") + (Integer) datosEspacios.get("ventilacionValor3") + (Integer) datosEspacios.get("ventilacionValor4"))).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("ventilacionValor2", (((Integer) datosEspacios.get("ventilacionValor1") + (Integer) datosEspacios.get("ventilacionValor2") + (Integer) datosEspacios.get("ventilacionValor3") + (Integer) datosEspacios.get("ventilacionValor4")) == 0 ? 0 : new BigDecimal(new Double(100 * (Integer) datosEspacios.get("ventilacionValor2")) / new Double((Integer) datosEspacios.get("ventilacionValor1") + (Integer) datosEspacios.get("ventilacionValor2") + (Integer) datosEspacios.get("ventilacionValor3") + (Integer) datosEspacios.get("ventilacionValor4"))).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("ventilacionValor3", (((Integer) datosEspacios.get("ventilacionValor1") + (Integer) datosEspacios.get("ventilacionValor2") + (Integer) datosEspacios.get("ventilacionValor3") + (Integer) datosEspacios.get("ventilacionValor4")) == 0 ? 0 : new BigDecimal(new Double(100 * (Integer) datosEspacios.get("ventilacionValor3")) / new Double((Integer) datosEspacios.get("ventilacionValor1") + (Integer) datosEspacios.get("ventilacionValor2") + (Integer) datosEspacios.get("ventilacionValor3") + (Integer) datosEspacios.get("ventilacionValor4"))).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("ventilacionValor4", (((Integer) datosEspacios.get("ventilacionValor1") + (Integer) datosEspacios.get("ventilacionValor2") + (Integer) datosEspacios.get("ventilacionValor3") + (Integer) datosEspacios.get("ventilacionValor4")) == 0 ? 0 : new BigDecimal(new Double(100 * (Integer) datosEspacios.get("ventilacionValor4")) / new Double((Integer) datosEspacios.get("ventilacionValor1") + (Integer) datosEspacios.get("ventilacionValor2") + (Integer) datosEspacios.get("ventilacionValor3") + (Integer) datosEspacios.get("ventilacionValor4"))).setScale(2, RoundingMode.HALF_UP)) + "%");

            parametros.put("acusticaValor1", (((Integer) datosEspacios.get("acusticaValor1") + (Integer) datosEspacios.get("acusticaValor2") + (Integer) datosEspacios.get("acusticaValor3") + (Integer) datosEspacios.get("acusticaValor4")) == 0 ? 0 : new BigDecimal(new Double(100 * (Integer) datosEspacios.get("acusticaValor1")) / new Double((Integer) datosEspacios.get("acusticaValor1") + (Integer) datosEspacios.get("acusticaValor2") + (Integer) datosEspacios.get("acusticaValor3") + (Integer) datosEspacios.get("acusticaValor4"))).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("acusticaValor2", (((Integer) datosEspacios.get("acusticaValor1") + (Integer) datosEspacios.get("acusticaValor2") + (Integer) datosEspacios.get("acusticaValor3") + (Integer) datosEspacios.get("acusticaValor4")) == 0 ? 0 : new BigDecimal(new Double(100 * (Integer) datosEspacios.get("acusticaValor2")) / new Double((Integer) datosEspacios.get("acusticaValor1") + (Integer) datosEspacios.get("acusticaValor2") + (Integer) datosEspacios.get("acusticaValor3") + (Integer) datosEspacios.get("acusticaValor4"))).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("acusticaValor3", (((Integer) datosEspacios.get("acusticaValor1") + (Integer) datosEspacios.get("acusticaValor2") + (Integer) datosEspacios.get("acusticaValor3") + (Integer) datosEspacios.get("acusticaValor4")) == 0 ? 0 : new BigDecimal(new Double(100 * (Integer) datosEspacios.get("acusticaValor3")) / new Double((Integer) datosEspacios.get("acusticaValor1") + (Integer) datosEspacios.get("acusticaValor2") + (Integer) datosEspacios.get("acusticaValor3") + (Integer) datosEspacios.get("acusticaValor4"))).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("acusticaValor4", (((Integer) datosEspacios.get("acusticaValor1") + (Integer) datosEspacios.get("acusticaValor2") + (Integer) datosEspacios.get("acusticaValor3") + (Integer) datosEspacios.get("acusticaValor4")) == 0 ? 0 : new BigDecimal(new Double(100 * (Integer) datosEspacios.get("acusticaValor4")) / new Double((Integer) datosEspacios.get("acusticaValor1") + (Integer) datosEspacios.get("acusticaValor2") + (Integer) datosEspacios.get("acusticaValor3") + (Integer) datosEspacios.get("acusticaValor4"))).setScale(2, RoundingMode.HALF_UP)) + "%");

            parametros.put("duchasValor1", (((Integer) datosEspacios.get("duchasValor1") + (Integer) datosEspacios.get("duchasValor2") + (Integer) datosEspacios.get("duchasValor3") + (Integer) datosEspacios.get("duchasValor4")) == 0 ? 0 : new BigDecimal(new Double(100 * (Integer) datosEspacios.get("duchasValor1")) / new Double((Integer) datosEspacios.get("duchasValor1") + (Integer) datosEspacios.get("duchasValor2") + (Integer) datosEspacios.get("duchasValor3") + (Integer) datosEspacios.get("duchasValor4"))).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("duchasValor2", (((Integer) datosEspacios.get("duchasValor1") + (Integer) datosEspacios.get("duchasValor2") + (Integer) datosEspacios.get("duchasValor3") + (Integer) datosEspacios.get("duchasValor4")) == 0 ? 0 : new BigDecimal(new Double(100 * (Integer) datosEspacios.get("duchasValor2")) / new Double((Integer) datosEspacios.get("duchasValor1") + (Integer) datosEspacios.get("duchasValor2") + (Integer) datosEspacios.get("duchasValor3") + (Integer) datosEspacios.get("duchasValor4"))).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("duchasValor3", (((Integer) datosEspacios.get("duchasValor1") + (Integer) datosEspacios.get("duchasValor2") + (Integer) datosEspacios.get("duchasValor3") + (Integer) datosEspacios.get("duchasValor4")) == 0 ? 0 : new BigDecimal(new Double(100 * (Integer) datosEspacios.get("duchasValor3")) / new Double((Integer) datosEspacios.get("duchasValor1") + (Integer) datosEspacios.get("duchasValor2") + (Integer) datosEspacios.get("duchasValor3") + (Integer) datosEspacios.get("duchasValor4"))).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("duchasValor4", (((Integer) datosEspacios.get("duchasValor1") + (Integer) datosEspacios.get("duchasValor2") + (Integer) datosEspacios.get("duchasValor3") + (Integer) datosEspacios.get("duchasValor4")) == 0 ? 0 : new BigDecimal(new Double(100 * (Integer) datosEspacios.get("duchasValor4")) / new Double((Integer) datosEspacios.get("duchasValor1") + (Integer) datosEspacios.get("duchasValor2") + (Integer) datosEspacios.get("duchasValor3") + (Integer) datosEspacios.get("duchasValor4"))).setScale(2, RoundingMode.HALF_UP)) + "%");

            parametros.put("lavamanosValor1", (((Integer) datosEspacios.get("lavamanosValor1") + (Integer) datosEspacios.get("lavamanosValor2") + (Integer) datosEspacios.get("lavamanosValor3") + (Integer) datosEspacios.get("lavamanosValor4")) == 0 ? 0 : new BigDecimal(new Double(100 * (Integer) datosEspacios.get("lavamanosValor1")) / new Double((Integer) datosEspacios.get("lavamanosValor1") + (Integer) datosEspacios.get("lavamanosValor2") + (Integer) datosEspacios.get("lavamanosValor3") + (Integer) datosEspacios.get("lavamanosValor4"))).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("lavamanosValor2", (((Integer) datosEspacios.get("lavamanosValor1") + (Integer) datosEspacios.get("lavamanosValor2") + (Integer) datosEspacios.get("lavamanosValor3") + (Integer) datosEspacios.get("lavamanosValor4")) == 0 ? 0 : new BigDecimal(new Double(100 * (Integer) datosEspacios.get("lavamanosValor2")) / new Double((Integer) datosEspacios.get("lavamanosValor1") + (Integer) datosEspacios.get("lavamanosValor2") + (Integer) datosEspacios.get("lavamanosValor3") + (Integer) datosEspacios.get("lavamanosValor4"))).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("lavamanosValor3", (((Integer) datosEspacios.get("lavamanosValor1") + (Integer) datosEspacios.get("lavamanosValor2") + (Integer) datosEspacios.get("lavamanosValor3") + (Integer) datosEspacios.get("lavamanosValor4")) == 0 ? 0 : new BigDecimal(new Double(100 * (Integer) datosEspacios.get("lavamanosValor3")) / new Double((Integer) datosEspacios.get("lavamanosValor1") + (Integer) datosEspacios.get("lavamanosValor2") + (Integer) datosEspacios.get("lavamanosValor3") + (Integer) datosEspacios.get("lavamanosValor4"))).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("lavamanosValor4", (((Integer) datosEspacios.get("lavamanosValor1") + (Integer) datosEspacios.get("lavamanosValor2") + (Integer) datosEspacios.get("lavamanosValor3") + (Integer) datosEspacios.get("lavamanosValor4")) == 0 ? 0 : new BigDecimal(new Double(100 * (Integer) datosEspacios.get("lavamanosValor4")) / new Double((Integer) datosEspacios.get("lavamanosValor1") + (Integer) datosEspacios.get("lavamanosValor2") + (Integer) datosEspacios.get("lavamanosValor3") + (Integer) datosEspacios.get("lavamanosValor4"))).setScale(2, RoundingMode.HALF_UP)) + "%");

            parametros.put("orinalesValor1", (((Integer) datosEspacios.get("orinalesValor1") + (Integer) datosEspacios.get("orinalesValor2") + (Integer) datosEspacios.get("orinalesValor3") + (Integer) datosEspacios.get("orinalesValor4")) == 0 ? 0 : new BigDecimal(new Double(100 * (Integer) datosEspacios.get("orinalesValor1")) / new Double((Integer) datosEspacios.get("orinalesValor1") + (Integer) datosEspacios.get("orinalesValor2") + (Integer) datosEspacios.get("orinalesValor3") + (Integer) datosEspacios.get("orinalesValor4"))).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("orinalesValor2", (((Integer) datosEspacios.get("orinalesValor1") + (Integer) datosEspacios.get("orinalesValor2") + (Integer) datosEspacios.get("orinalesValor3") + (Integer) datosEspacios.get("orinalesValor4")) == 0 ? 0 : new BigDecimal(new Double(100 * (Integer) datosEspacios.get("orinalesValor2")) / new Double((Integer) datosEspacios.get("orinalesValor1") + (Integer) datosEspacios.get("orinalesValor2") + (Integer) datosEspacios.get("orinalesValor3") + (Integer) datosEspacios.get("orinalesValor4"))).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("orinalesValor3", (((Integer) datosEspacios.get("orinalesValor1") + (Integer) datosEspacios.get("orinalesValor2") + (Integer) datosEspacios.get("orinalesValor3") + (Integer) datosEspacios.get("orinalesValor4")) == 0 ? 0 : new BigDecimal(new Double(100 * (Integer) datosEspacios.get("orinalesValor3")) / new Double((Integer) datosEspacios.get("orinalesValor1") + (Integer) datosEspacios.get("orinalesValor2") + (Integer) datosEspacios.get("orinalesValor3") + (Integer) datosEspacios.get("orinalesValor4"))).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("orinalesValor4", (((Integer) datosEspacios.get("orinalesValor1") + (Integer) datosEspacios.get("orinalesValor2") + (Integer) datosEspacios.get("orinalesValor3") + (Integer) datosEspacios.get("orinalesValor4")) == 0 ? 0 : new BigDecimal(new Double(100 * (Integer) datosEspacios.get("orinalesValor4")) / new Double((Integer) datosEspacios.get("orinalesValor1") + (Integer) datosEspacios.get("orinalesValor2") + (Integer) datosEspacios.get("orinalesValor3") + (Integer) datosEspacios.get("orinalesValor4"))).setScale(2, RoundingMode.HALF_UP)) + "%");

            parametros.put("inodorosValor1", (((Integer) datosEspacios.get("inodorosValor1") + (Integer) datosEspacios.get("inodorosValor2") + (Integer) datosEspacios.get("inodorosValor3") + (Integer) datosEspacios.get("inodorosValor4")) == 0 ? 0 : new BigDecimal(new Double(100 * (Integer) datosEspacios.get("inodorosValor1")) / new Double((Integer) datosEspacios.get("inodorosValor1") + (Integer) datosEspacios.get("inodorosValor2") + (Integer) datosEspacios.get("inodorosValor3") + (Integer) datosEspacios.get("inodorosValor4"))).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("inodorosValor2", (((Integer) datosEspacios.get("inodorosValor1") + (Integer) datosEspacios.get("inodorosValor2") + (Integer) datosEspacios.get("inodorosValor3") + (Integer) datosEspacios.get("inodorosValor4")) == 0 ? 0 : new BigDecimal(new Double(100 * (Integer) datosEspacios.get("inodorosValor2")) / new Double((Integer) datosEspacios.get("inodorosValor1") + (Integer) datosEspacios.get("inodorosValor2") + (Integer) datosEspacios.get("inodorosValor3") + (Integer) datosEspacios.get("inodorosValor4"))).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("inodorosValor3", (((Integer) datosEspacios.get("inodorosValor1") + (Integer) datosEspacios.get("inodorosValor2") + (Integer) datosEspacios.get("inodorosValor3") + (Integer) datosEspacios.get("inodorosValor4")) == 0 ? 0 : new BigDecimal(new Double(100 * (Integer) datosEspacios.get("inodorosValor3")) / new Double((Integer) datosEspacios.get("inodorosValor1") + (Integer) datosEspacios.get("inodorosValor2") + (Integer) datosEspacios.get("inodorosValor3") + (Integer) datosEspacios.get("inodorosValor4"))).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("inodorosValor4", (((Integer) datosEspacios.get("inodorosValor1") + (Integer) datosEspacios.get("inodorosValor2") + (Integer) datosEspacios.get("inodorosValor3") + (Integer) datosEspacios.get("inodorosValor4")) == 0 ? 0 : new BigDecimal(new Double(100 * (Integer) datosEspacios.get("inodorosValor4")) / new Double((Integer) datosEspacios.get("inodorosValor1") + (Integer) datosEspacios.get("inodorosValor2") + (Integer) datosEspacios.get("inodorosValor3") + (Integer) datosEspacios.get("inodorosValor4"))).setScale(2, RoundingMode.HALF_UP)) + "%");

            parametros.put("aislamientoTermicoPisos", datosEspacios.get("aislamientoTermicoPisos"));
            parametros.put("aislamientoTermicoMuros", datosEspacios.get("aislamientoTermicoMuros"));
            parametros.put("aislamientoTermicoVentanas", datosEspacios.get("aislamientoTermicoVentanas"));
            parametros.put("aislamientoTermicoCielos", datosEspacios.get("aislamientoTermicoCielos"));
            parametros.put("aislamientoTermicoOtros", datosEspacios.get("aislamientoTermicoOtros"));

            parametros.put("numAcustica", (Integer) datosEspacios.get("numAcustica"));
            parametros.put("numDuchas", (Integer) datosEspacios.get("numDuchas"));
            parametros.put("numIluminacion", (Integer) datosEspacios.get("numIluminacion"));
            parametros.put("numInodoros", (Integer) datosEspacios.get("numInodoros"));
            parametros.put("numLavamanos", (Integer) datosEspacios.get("numLavamanos"));
            parametros.put("numOrinales", (Integer) datosEspacios.get("numOrinales"));
            parametros.put("numVentilacion", (Integer) datosEspacios.get("numVentilacion"));

            HashMap<String, Object> datosUnidadesFuncionales = this.obtenerUnidadesFuncionales(idDigInstrumento, ParamNivelConsul.PREDIO.getNivel());
            //Añadimos todos los datos de las unidaddes funcionales a los parámetros
            String[] noStrings = {"propAreasCentrales", "propAreasApoyo", "propAreasAulas", "propAreasLaboratorios", "propAreasCentroRecursosAprendizaje", "propAreasTalleres", "propAreasExtension", "propAreasServSanitarios", "propAreasAbastProcesa", "propAreasConducAdministra"};
            List<String> noStr = new ArrayList<String>();
            Collections.addAll(noStr, noStrings);

            for (String key : datosUnidadesFuncionales.keySet()) {
                if (noStr.contains(key)) {
                    parametros.put(key, datosUnidadesFuncionales.get(key));
                } else {
                    parametros.put(key, datosUnidadesFuncionales.get(key) + "");
                }
            }

            String periodoCorte = facParam.consultarPeriodoIndicadores();
            if (periodoCorte != null) {
                DateFormat df = DateFormat.getDateInstance(DateFormat.LONG);
                Calendar calAux = Calendar.getInstance();
                calAux.set(new Integer(periodoCorte.substring(0, 4)), new Integer(periodoCorte.substring(4, 6)), 1);
                SimpleDateFormat format = new SimpleDateFormat("MMMM yyyy");
                String fecha = format.format(calAux.getTime());
                parametros.put("fechaCorte", fecha.substring(0, 1).toUpperCase() + fecha.substring(1, fecha.length()));
            }

            ReporteInformeGeneralPredio reporteInformeGeneral = new ReporteInformeGeneralPredio();
            reporteInformeGeneral.setListaEstablecimientosAdicionales(listaEstablecimientosAdicionales);
            reporteInformeGeneral.setListaControlVigilancia(listaControlVigilancia);
            reporteInformeGeneral.setListaSeguridadAccesibilidad(listaSeguridadAccesibilidad);

            List list = new ArrayList();
            list.add(reporteInformeGeneral);

            String[] ponerNoIfNull = {"RESP_061_02", "RESP_062_02", "RESP_063_02", "RESP_064_02", "RESP_066_02", "RESP_067_02", "RESP_068_02", "RESP_069_02"};
            for (String resp : ponerNoIfNull) {
                if (!parametros.containsKey(resp) || parametros.get(resp) == null || ("").equals(parametros.get(resp).toString())) {
                    parametros.put(resp, bundle.getString("aplicacion.general.no"));
                }
            }

            parametros.put(JRParameter.REPORT_RESOURCE_BUNDLE, ResourceBundle.getBundle("etiquetas", FacesContext.getCurrentInstance().getViewRoot().getLocale()));

            stream = UtilExportar.getInformeGeneral("informeGeneral/predio", "InformeGeneral", parametros, list);

        } catch (Exception e) {
           // UtilOut.println("ERROR en imprimirPdf: " + e.getMessage());
            //mostrarMensajeError(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"), e.getMessage());
            //ManejadorErrores.ingresarExcepcionEnLog(e, InformeGeneralBean.class);
            //UtilOut.println(e);
            e.printStackTrace();
        }

        return new DefaultStreamedContent(stream, "application/x-download", "InformeGeneral.pdf");
    }

    private Integer getValor(HashMap datos, String key) {
        return datos.get(key) == null ? 0 : (Integer) datos.get(key);
    }

    private StreamedContent imprimirPdfSede(Long idSede, String codSede) {
        parametros = new HashMap<String, Object>();
        InputStream stream = null;
        try {
            Sede sede = facEstablecimientos.buscarSedePorId(idSede);

            parametros.put("codigoEst", sede.getEstablecimiento().getCodigo());
            parametros.put("nombreEst", sede.getEstablecimiento().getNombre());
            parametros.put("codigoSede", sede.getCodigo());
            parametros.put("nombreSede", sede.getNombre());
            parametros.put("telefono", sede.getTelefono());
            parametros.put("email", sede.getEmailSede());
            parametros.put("rector", sede.getRector());
            parametros.put("telRector", sede.getTelRector());

            List<BigDecimal> listaInstrumentosDigitados = facInstrumentos.obtenerInstrumentosDigPorSede(idSede);
            Map<String, Object> datos = null;
            parametros.put("numPredios", listaInstrumentosDigitados.size());
            List<DatosPredio> listaDatosPredios = new ArrayList<DatosPredio>();
            BigDecimal superficie = new BigDecimal("0");
            BigDecimal consumoAgua = new BigDecimal("0");
            BigDecimal consumoEnergia = new BigDecimal("0");

            List<String> preCodes = new ArrayList<String>();
            List<String> priCodes = new ArrayList<String>();
            List<String> secCodes = new ArrayList<String>();
            List<String> basCodes = new ArrayList<String>();
            List<String> medCodes = new ArrayList<String>();
            List<String[]> nivelesEnsenanza = facInformeGeneral.getEquivalentes("nivel_educativo");
            for (Iterator<String[]> it = nivelesEnsenanza.iterator(); it.hasNext();) {
                Object[] n = it.next();
                if (n[0].equals("NE_PRE")) {
                    preCodes.add((String) n[1]);
                } else if (n[0].equals("NE_PRI")) {
                    priCodes.add((String) n[1]);
                } else if (n[0].equals("NE_SEC")) {
                    secCodes.add((String) n[1]);
                } else if (n[0].equals("NE_BAS")) {
                    basCodes.add((String) n[1]);
                } else if (n[0].equals("NE_MED")) {
                    medCodes.add((String) n[1]);
                }
            }

            int accCumplen = 0;

            int murosFachadasValor1 = 0;
            int murosFachadasValor2 = 0;
            int murosFachadasValor3 = 0;
            int murosFachadasValor4 = 0;
            int acabadosFachadasValor1 = 0;
            int acabadosFachadasValor2 = 0;
            int acabadosFachadasValor3 = 0;
            int acabadosFachadasValor4 = 0;
            int cubiertasValor1 = 0;
            int cubiertasValor2 = 0;
            int cubiertasValor3 = 0;
            int cubiertasValor4 = 0;

            int pisosValor1 = 0;
            int pisosValor2 = 0;
            int pisosValor3 = 0;
            int pisosValor4 = 0;
            int murosValor1 = 0;
            int murosValor2 = 0;
            int murosValor3 = 0;
            int murosValor4 = 0;
            int acabadosMuroValor1 = 0;
            int acabadosMuroValor2 = 0;
            int acabadosMuroValor3 = 0;
            int acabadosMuroValor4 = 0;
            int ventanasValor1 = 0;
            int ventanasValor2 = 0;
            int ventanasValor3 = 0;
            int ventanasValor4 = 0;
            int puertasValor1 = 0;
            int puertasValor2 = 0;
            int puertasValor3 = 0;
            int puertasValor4 = 0;
            int cielosValor1 = 0;
            int cielosValor2 = 0;
            int cielosValor3 = 0;
            int cielosValor4 = 0;
            int numIluminacion = 0;
            int iluminacionValor1 = 0;
            int iluminacionValor2 = 0;
            int iluminacionValor3 = 0;
            int iluminacionValor4 = 0;
            int numVentilacion = 0;
            int ventilacionValor1 = 0;
            int ventilacionValor2 = 0;
            int ventilacionValor3 = 0;
            int ventilacionValor4 = 0;
            int numAcustica = 0;
            int acusticaValor1 = 0;
            int acusticaValor2 = 0;
            int acusticaValor3 = 0;
            int acusticaValor4 = 0;
            int numDuchas = 0;
            int duchasValor1 = 0;
            int duchasValor2 = 0;
            int duchasValor3 = 0;
            int duchasValor4 = 0;
            int numLavamanos = 0;
            int lavamanosValor1 = 0;
            int lavamanosValor2 = 0;
            int lavamanosValor3 = 0;
            int lavamanosValor4 = 0;
            int numOrinales = 0;
            int orinalesValor1 = 0;
            int orinalesValor2 = 0;
            int orinalesValor3 = 0;
            int orinalesValor4 = 0;
            int numInodoros = 0;
            int inodorosValor1 = 0;
            int inodorosValor2 = 0;
            int inodorosValor3 = 0;
            int inodorosValor4 = 0;
            int aislamientoTermicoPisos = 0;
            int aislamientoTermicoMuros = 0;
            int aislamientoTermicoVentanas = 0;
            int aislamientoTermicoCielos = 0;
            int aislamientoTermicoOtros = 0;

            int poblacionTotal = 0;
            int poblacionAfectadaRiesgos = 0;
            int riesgosCumplen = 0;

            int totalCobertura = 0;

            for (BigDecimal idInstrumentoDig : listaInstrumentosDigitados) {
                InstrumentoDig instrumentoDig = facInstrumentos.consultarInstrumentoPorCodigo(idInstrumentoDig.longValue());
                datos = obtenerRespuestas(instrumentoDig);

                //INICIO DATOS PREDIO
                DatosPredio dp = new DatosPredio();
                dp.setNombrePredio(instrumentoDig.getPredio().getNombre());
                dp.setCodigoPredio(instrumentoDig.getPredio().getCodigo());
                dp.setTelefono((String) datos.get("RESP_016"));
                dp.setDireccion((String) datos.get("RESP_015"));
                dp.setEmail((String) datos.get("RESP_018"));
                dp.setRector((String) datos.get("RESP_019"));
                dp.setTelRector((String) datos.get("RESP_020"));
                //FIN DATOS PREDIO
                //INICIO COBERTURAS
                String[] respuestasNivelEnsenanza = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10"};
                int pre = 0, primaria = 0, secundaria = 0, basica = 0, media = 0;
                TipologiaValorNombre tv;
                for (String resp : respuestasNivelEnsenanza) {
                    if (preCodes.contains(datos.get("RESP_033_" + resp))) {
                        if (datos.containsKey("RESP_035_" + resp) && datos.get("RESP_035_" + resp) != null) {
                            pre += (new Integer(datos.get("RESP_035_" + resp) + ""));
                        }
                    } else if (datos.get("RESP_035_" + resp) != null && priCodes.contains(datos.get("RESP_033_" + resp))) {
                        primaria += (new Integer(datos.get("RESP_035_" + resp) + ""));
                    } else if (datos.get("RESP_035_" + resp) != null && secCodes.contains(datos.get("RESP_033_" + resp))) {
                        secundaria += (new Integer(datos.get("RESP_035_" + resp) + ""));
                    } else if (datos.get("RESP_035_" + resp) != null && basCodes.contains(datos.get("RESP_033_" + resp))) {
                        basica += (new Integer(datos.get("RESP_035_" + resp) + ""));
                    } else if (datos.get("RESP_035_" + resp) != null && medCodes.contains(datos.get("RESP_033_" + resp))) {
                        media += (new Integer(datos.get("RESP_035_" + resp) + ""));
                    }

                    if (preCodes.contains(datos.get("RESP_199_" + resp))) {
                        if (datos.containsKey("RESP_201_" + resp) && datos.get("RESP_201_" + resp) != null) {
                            pre += (new Integer(datos.get("RESP_201_" + resp) + ""));
                        }
                    } else if (datos.get("RESP_201_" + resp) != null && priCodes.contains(datos.get("RESP_199_" + resp))) {
                        primaria += (new Integer(datos.get("RESP_201_" + resp) + ""));
                    } else if (datos.get("RESP_201_" + resp) != null && secCodes.contains(datos.get("RESP_199_" + resp))) {
                        secundaria += (new Integer(datos.get("RESP_201_" + resp) + ""));
                    } else if (datos.get("RESP_201_" + resp) != null && basCodes.contains(datos.get("RESP_199_" + resp))) {
                        basica += (new Integer(datos.get("RESP_201_" + resp) + ""));
                    } else if (datos.get("RESP_201_" + resp) != null && medCodes.contains(datos.get("RESP_199_" + resp))) {
                        media += (new Integer(datos.get("RESP_201_" + resp) + ""));
                    }
                    //Se les da el valor de la tipología para que se muestre el texto en el reporte
                    if (datos.get("RESP_033_" + resp) != null) {
                        tv = facParam.consultarValorTipologia(respuestaTipologia.get("RESP_033_" + resp), datos.get("RESP_033_" + resp).toString(), getUsuarioSesion().getUsuario().getIdioma().getId());
                        datos.put(resp, tv != null ? tv.getNombre() : "");
                    } else if (datos.get("RESP_199_" + resp) != null) {
                        tv = facParam.consultarValorTipologia(respuestaTipologia.get("RESP_199_" + resp), datos.get("RESP_199_" + resp).toString(), getUsuarioSesion().getUsuario().getIdioma().getId());
                        datos.put(resp, tv != null ? tv.getNombre() : "");
                    }
                }
                dp.setGenero(datos.get("RESP_040") + "");
                dp.setPre(pre + "");
                dp.setPrimaria(primaria + "");
                dp.setSecundaria(secundaria + "");
                dp.setBasica(basica + "");
                dp.setMedia(media + "");
                dp.setTotal((pre + primaria + secundaria + basica + media) + "");
                totalCobertura += (pre + primaria + secundaria + basica + media);
                //FIN COBERTURAS

                //Superficie
                superficie = superficie.add(new BigDecimal((String) datos.get("RESP_043")));

                //INICIO ACCESIBILIDAD EXTERNA
                String[] respuestasAccExt = {"01", "03", "05", "07", "09"};
                int numAccesosExt = 0;
                for (String resp : respuestasAccExt) {
                    if (datos.containsKey("RESP_045_" + resp)) {
                        numAccesosExt++;
                    }
                }
                dp.setNumAccesos(numAccesosExt + "");
                //FIN ACCESIBILIDAD EXTERNA

                //INICIO RIESGOS
                String[] respuestasRiesgos = {"01", "03", "05"};
                int numRiesgosNaturales = 0;
                int numRiesgosAntropicos = 0;

                List<String> indicadores = new ArrayList<String>();
                indicadores.add(ParamIndicador.IND_TOTAL_POBLACION.name());
                List<Object[]> pobl = facParam.consultarIndicadores(indicadores, ParamNivelConsul.PREDIO.getNivel(), idInstrumentoDig.longValue());
                poblacionTotal += new Integer(pobl.get(0)[0] == null ? "0" : (String) pobl.get(0)[0]);

                for (String resp : respuestasRiesgos) {
                    if (datos.containsKey("RESP_048_" + resp)) {
                        numRiesgosNaturales++;
                    }
                    if (datos.containsKey("RESP_050_" + resp)) {
                        numRiesgosAntropicos++;
                    }
                }
                if (numRiesgosNaturales + numRiesgosAntropicos > 0) {
                    poblacionAfectadaRiesgos += new Integer(pobl.get(0)[0] == null ? "0" : (String) pobl.get(0)[0]);
                }

                dp.setNoNaturales(numRiesgosNaturales + "");
                dp.setNoAntropicos(numRiesgosAntropicos + "");
                //FIN RIESGOS

                //INICIO SERVICIOS
                if (datos.get("RESP_061_05") != null) {
                    dp.setAgua(Utilidades.obtenerMensaje("tipologias.condicion." + facInformeGeneral.consultarCodTipologiaRespuesta("RESP_061_05") + "_" + datos.get("RESP_061_05").toString()));
                }
                if (datos.get("RESP_062_05") != null) {
                    dp.setEnergia(Utilidades.obtenerMensaje("tipologias.condicion." + facInformeGeneral.consultarCodTipologiaRespuesta("RESP_062_05") + "_" + datos.get("RESP_062_05").toString()));
                }
                if (datos.get("RESP_066_05") != null) {
                    dp.setAlcantarillado(Utilidades.obtenerMensaje("tipologias.condicion." + facInformeGeneral.consultarCodTipologiaRespuesta("RESP_066_05") + "_" + datos.get("RESP_066_05").toString()));
                }
                if (datos.get("RESP_067_05") != null) {
                    dp.setRedPluvial(Utilidades.obtenerMensaje("tipologias.condicion." + facInformeGeneral.consultarCodTipologiaRespuesta("RESP_067_05") + "_" + datos.get("RESP_067_05").toString()));
                }
                if (datos.get("RESP_068_05") != null) {
                    dp.setInternet(Utilidades.obtenerMensaje("tipologias.condicion." + facInformeGeneral.consultarCodTipologiaRespuesta("RESP_068_05") + "_" + datos.get("RESP_068_05").toString()));
                }
                if (datos.get("RESP_069_05") != null) {
                    dp.setServTelefono(Utilidades.obtenerMensaje("tipologias.condicion." + facInformeGeneral.consultarCodTipologiaRespuesta("RESP_069_05") + "_" + datos.get("RESP_069_05").toString()));
                }
                if (datos.get("RESP_065_02") != null) {
                    dp.setReciclaje(datos.get("RESP_065_02").toString());
                } else {
                    dp.setReciclaje(Utilidades.obtenerMensaje("informe.general.noaplica"));
                }
                consumoAgua = consumoAgua.add(new BigDecimal(datos.get("RESP_061_06") != null ? UtilConsulta.doubleValueOf(datos.get("RESP_061_06")) : 0));
                consumoEnergia = consumoEnergia.add(new BigDecimal(datos.get("RESP_062_06") != null ? UtilConsulta.doubleValueOf(datos.get("RESP_062_06")) : 0));
                //FIN SERVICIOS

                //INICIO CONTROLYVIGILANCIA
                if (datos.get("RESP_054_01") != null) {
                    dp.setCerco(transformaSiNoNA(datos.get("RESP_054_01") + ""));
                }
                if (datos.get("RESP_054_04") != null) {
                    dp.setCercoCondicion(Utilidades.obtenerMensaje("tipologias.condicion." + facInformeGeneral.consultarCodTipologiaRespuesta("RESP_054_04") + "_" + datos.get("RESP_054_04").toString()));
                }
                //FIN CONTROLYVIGILANCIA

                indicadores.clear();
                indicadores.add(ParamIndicador.IND_AMPLIACION_AREA_CONSTRUCCION_PRIMER_PISO.name());
                indicadores.add(ParamIndicador.IND_ACCESIBILIDAD_CONDICION_ACCESIBILIDAD.name());
                indicadores.add(ParamIndicador.IND_PROBLEMA_LEGALIZACION.name());
                indicadores.add(ParamIndicador.IND_CONTROL_VIGILANCIA_VULNERABILIDAD.name());
                indicadores.add(ParamIndicador.IND_SEGURIDAD_ESTADO_ESTRUCTURA.name());
                indicadores.add(ParamIndicador.IND_SEGURIDAD_ANALISIS_RUTA_EVACUACION.name());
                indicadores.add(ParamIndicador.IND_SEGURIDAD_EDIF_SENALIZACION_EVACUACION.name());
                indicadores.add(ParamIndicador.IND_SEGURIDAD_REACTIVO_CONTRA_INCENDIO.name());
                indicadores.add(ParamIndicador.IND_AMPLIACION_POSIBILIDAD_CONSTRUCCION_ALTURA.name());
                indicadores.add(ParamIndicador.IND_AREA_REAL_PREDIO.name());
                indicadores.add(ParamIndicador.IND_TOTAL_AREA_CONSTRUCCION_PREDIO.name());
                indicadores.add(ParamIndicador.IND_AMPLIACION_M2_REQUERIDOS_PREDIO.name());
                indicadores.add(ParamIndicador.IND_AMPLIACION_POSIBILIDAD_CONSTRUCCION_PRIMER_PISO.name());
                indicadores.add(ParamIndicador.IND_AMPLIACION_POSIBILIDAD_CONSTRUCCION_TOTAL.name());
                indicadores.add(ParamIndicador.IND_AMPLIACION_M2_AMPLIAR_COBERTURA.name());
                indicadores.add(ParamIndicador.IND_AMPLIACION_ALUMNOS_NUEVOS_INFRAESTRUCTURA.name());

                List<Object[]> areas = facParam.consultarIndicadores(indicadores, ParamNivelConsul.PREDIO.getNivel(), idInstrumentoDig.longValue());
                if (!areas.isEmpty()) {
                    int i = 0;
                    BigDecimal construcPrimerPiso = new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP);
                    i++;
                    dp.setCumplimientoAccesos(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : Utilidades.obtenerMensaje((String) areas.get(0)[i]));
                    i++;
                    dp.setCumplimientoLegalizacion(Utilidades.obtenerMensaje("aplicacion.general." + (!UtilCadena.isNullOVacio((String) areas.get(0)[i]) && SiNoNA.No.name().equals((String) areas.get(0)[i]) ? "cumple" : "noCumple")));
                    i++;
                    dp.setVulnerabilidad(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : Utilidades.obtenerMensaje((String) areas.get(0)[i]));
                    i++;
                    dp.setCondicionEstruc(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : Utilidades.obtenerMensaje((String) areas.get(0)[i]));
                    i++;
                    dp.setRutaEvacuacion(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : Utilidades.obtenerMensaje((String) areas.get(0)[i]));
                    i++;
                    dp.setSenalizacionEvacuacion(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : Utilidades.obtenerMensaje(Utilidades.obtenerMensaje((String) areas.get(0)[i])));
                    i++;
                    dp.setEquiposContraIncendios(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]);
                    i++;
                    dp.setConstruibleAltura(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? null : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP) + "");
                    i++;
                    dp.setArea(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? null : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP) + "");
                    dp.setIndOcupacion(dp.getArea() == null ? null : construcPrimerPiso.divide(new BigDecimal(dp.getArea()), 4, RoundingMode.HALF_UP).multiply(new BigDecimal("100")).setScale(2, RoundingMode.HALF_UP) + "%");
                    i++;
                    dp.setIndConstruccion(UtilCadena.isNullOVacio((String) areas.get(0)[i]) || UtilCadena.isNullOVacio(dp.getArea()) || new BigDecimal(dp.getArea()).compareTo(BigDecimal.ZERO) == 0 ? null : new BigDecimal(areas.get(0)[i] + "").divide(new BigDecimal(dp.getArea()), 4, RoundingMode.HALF_UP).multiply(new BigDecimal("100")).setScale(2, RoundingMode.HALF_UP) + "%");
                    i++;
                    dp.setM2Requeridos(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? null : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP) + "");
                    i++;
                    dp.setConstruiblePrimerPiso(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? null : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP) + "");
                    i++;
                    dp.setConstruibleTotal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? null : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP) + "");
                    i++;
                    dp.setM2AmpliarCobertura(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? null : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP) + "");
                    i++;
                    dp.setAlumnosNuevosInfraestructura(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? null : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP) + "");
                    i++;

                }

                if (Utilidades.obtenerMensaje(Cumplimiento.SI.getEtiqueta()).toUpperCase().equals(dp.getCumplimientoAccesos())) {
                    accCumplen++;
                }
                if ("No crítico".equals(dp.getCondicion())) {
                    riesgosCumplen++;
                }

                FiltroCalificacion filtro = new FiltroCalificacion();
                filtro.setNivelAgrupamiento(ParamNivelConsul.PREDIO.getNivel());
                filtro.setIdEntidad(idInstrumentoDig.longValue());
                BigDecimal cond = facCalificacion.consultarCalificacionAmbito(filtro, Ambitos.A_RI.name());
                String condRI = (String) facCalificacion.obtenerValorEscala(Ambitos.A_RI.toString(), cond).get(0)[2];
                dp.setCondicion(Utilidades.obtenerMensaje("informe.general.reporte.cal_escala." + condRI));

                List edif = obtenerObjetosEdificios(idInstrumentoDig.longValue());
                //List<ControlVigilancia> listaControlVigilancia=(List)edif.get(0);
                //List<SeguridadAccesibilidad> listaSeguridadAccesibilidad=(List)edif.get(1);
                HashMap datosMaterial = (HashMap) edif.get(2);

                murosFachadasValor1 += (Integer) datosMaterial.get("murosFachadasValor1");
                murosFachadasValor2 += (Integer) datosMaterial.get("murosFachadasValor2");
                murosFachadasValor3 += (Integer) datosMaterial.get("murosFachadasValor3");
                murosFachadasValor4 += (Integer) datosMaterial.get("murosFachadasValor4");

                acabadosFachadasValor1 += (Integer) datosMaterial.get("acabadosFachadasValor1");
                acabadosFachadasValor2 += (Integer) datosMaterial.get("acabadosFachadasValor2");
                acabadosFachadasValor3 += (Integer) datosMaterial.get("acabadosFachadasValor3");
                acabadosFachadasValor4 += (Integer) datosMaterial.get("acabadosFachadasValor4");

                cubiertasValor1 += (Integer) datosMaterial.get("cubiertasValor1");
                cubiertasValor2 += (Integer) datosMaterial.get("cubiertasValor2");
                cubiertasValor3 += (Integer) datosMaterial.get("cubiertasValor3");
                cubiertasValor4 += (Integer) datosMaterial.get("cubiertasValor4");

                HashMap datosEsp = obtenerObjetosEspaciosEscolaresPredios(idInstrumentoDig.longValue());

                pisosValor1 += (Integer) datosEsp.get("pisosValor1");
                pisosValor2 += (Integer) datosEsp.get("pisosValor2");
                pisosValor3 += (Integer) datosEsp.get("pisosValor3");
                pisosValor4 += (Integer) datosEsp.get("pisosValor4");
                murosValor1 += (Integer) datosEsp.get("murosValor1");
                murosValor2 += (Integer) datosEsp.get("murosValor2");
                murosValor3 += (Integer) datosEsp.get("murosValor3");
                murosValor4 += (Integer) datosEsp.get("murosValor4");
                acabadosMuroValor1 += (Integer) datosEsp.get("acabadosMuroValor1");
                acabadosMuroValor2 += (Integer) datosEsp.get("acabadosMuroValor2");
                acabadosMuroValor3 += (Integer) datosEsp.get("acabadosMuroValor3");
                acabadosMuroValor4 += (Integer) datosEsp.get("acabadosMuroValor4");
                ventanasValor1 += (Integer) datosEsp.get("ventanasValor1");
                ventanasValor2 += (Integer) datosEsp.get("ventanasValor2");
                ventanasValor3 += (Integer) datosEsp.get("ventanasValor3");
                ventanasValor4 += (Integer) datosEsp.get("ventanasValor4");
                puertasValor1 += (Integer) datosEsp.get("puertasValor1");
                puertasValor2 += (Integer) datosEsp.get("puertasValor2");
                puertasValor3 += (Integer) datosEsp.get("puertasValor3");
                puertasValor4 += (Integer) datosEsp.get("puertasValor4");
                cielosValor1 += (Integer) datosEsp.get("cielosValor1");
                cielosValor2 += (Integer) datosEsp.get("cielosValor2");
                cielosValor3 += (Integer) datosEsp.get("cielosValor3");
                cielosValor4 += (Integer) datosEsp.get("cielosValor4");
                numIluminacion += (Integer) datosEsp.get("numIluminacion");
                iluminacionValor1 += (Integer) datosEsp.get("iluminacionValor1");
                iluminacionValor2 += (Integer) datosEsp.get("iluminacionValor2");
                iluminacionValor3 += (Integer) datosEsp.get("iluminacionValor3");
                iluminacionValor4 += (Integer) datosEsp.get("iluminacionValor4");
                numVentilacion += (Integer) datosEsp.get("numVentilacion");
                ventilacionValor1 += (Integer) datosEsp.get("ventilacionValor1");
                ventilacionValor2 += (Integer) datosEsp.get("ventilacionValor2");
                ventilacionValor3 += (Integer) datosEsp.get("ventilacionValor3");
                ventilacionValor4 += (Integer) datosEsp.get("ventilacionValor4");
                numAcustica += (Integer) datosEsp.get("numAcustica");
                acusticaValor1 += (Integer) datosEsp.get("acusticaValor1");
                acusticaValor2 += (Integer) datosEsp.get("acusticaValor2");
                acusticaValor3 += (Integer) datosEsp.get("acusticaValor3");
                acusticaValor4 += (Integer) datosEsp.get("acusticaValor4");
                numDuchas += (Integer) datosEsp.get("numDuchas");
                duchasValor1 += (Integer) datosEsp.get("duchasValor1");
                duchasValor2 += (Integer) datosEsp.get("duchasValor2");
                duchasValor3 += (Integer) datosEsp.get("duchasValor3");
                duchasValor4 += (Integer) datosEsp.get("duchasValor4");
                numLavamanos += (Integer) datosEsp.get("numLavamanos");
                lavamanosValor1 += (Integer) datosEsp.get("lavamanosValor1");
                lavamanosValor2 += (Integer) datosEsp.get("lavamanosValor2");
                lavamanosValor3 += (Integer) datosEsp.get("lavamanosValor3");
                lavamanosValor4 += (Integer) datosEsp.get("lavamanosValor4");
                numOrinales += (Integer) datosEsp.get("numOrinales");
                orinalesValor1 += (Integer) datosEsp.get("orinalesValor1");
                orinalesValor2 += (Integer) datosEsp.get("orinalesValor2");
                orinalesValor3 += (Integer) datosEsp.get("orinalesValor3");
                orinalesValor4 += (Integer) datosEsp.get("orinalesValor4");
                numInodoros += (Integer) datosEsp.get("numInodoros");
                inodorosValor1 += (Integer) datosEsp.get("inodorosValor1");
                inodorosValor2 += (Integer) datosEsp.get("inodorosValor2");
                inodorosValor3 += (Integer) datosEsp.get("inodorosValor3");
                inodorosValor4 += (Integer) datosEsp.get("inodorosValor4");
                aislamientoTermicoPisos += (Integer) datosEsp.get("aislamientoTermicoPisos");
                aislamientoTermicoMuros += (Integer) datosEsp.get("aislamientoTermicoMuros");
                aislamientoTermicoVentanas += (Integer) datosEsp.get("aislamientoTermicoVentanas");
                aislamientoTermicoCielos += (Integer) datosEsp.get("aislamientoTermicoCielos");
                aislamientoTermicoOtros += (Integer) datosEsp.get("aislamientoTermicoOtros");

                listaDatosPredios.add(dp);
            }//FIN for listaInstrumentosDigitados

            parametros.put("totalCobertura", totalCobertura);
            parametros.put("poblacionTotal", poblacionTotal);
            parametros.put("poblacionAfectadaRiesgos", poblacionAfectadaRiesgos);
            parametros.put("riesgosCumplen", riesgosCumplen);

            parametros.put("CondMat1", Utilidades.obtenerMensaje("tipologias.condicion." + "EMPMF_1"));
            parametros.put("CondMat2", Utilidades.obtenerMensaje("tipologias.condicion." + "EMPMF_2"));
            parametros.put("CondMat3", Utilidades.obtenerMensaje("tipologias.condicion." + "EMPMF_3"));
            parametros.put("CondMat4", Utilidades.obtenerMensaje("tipologias.condicion." + "EMPMF_4"));

            if (datos != null) {
                for (String key : datos.keySet()) {
                    parametros.put(key, datos.get(key));
                }
            }

            parametros.put("superficie", superficie.setScale(2, RoundingMode.HALF_UP) + "");

            List<String> indicadores = new ArrayList<String>();
            indicadores.add(ParamIndicador.IND_AMPLIACION_AREA_CONSTRUCCION_PRIMER_PISO.name());
            indicadores.add(ParamIndicador.IND_AMPLIACION_POSIBILIDAD_CONSTRUCCION_ALTURA.name());
            indicadores.add(ParamIndicador.IND_AREA_REAL_PREDIO.name());
            indicadores.add(ParamIndicador.IND_TOTAL_AREA_CONSTRUCCION_PREDIO.name());
            indicadores.add(ParamIndicador.IND_AMPLIACION_M2_REQUERIDOS_PREDIO.name());
            indicadores.add(ParamIndicador.IND_AMPLIACION_POSIBILIDAD_CONSTRUCCION_PRIMER_PISO.name());
            indicadores.add(ParamIndicador.IND_AMPLIACION_POSIBILIDAD_CONSTRUCCION_TOTAL.name());
            indicadores.add(ParamIndicador.IND_AMPLIACION_IDONEIDAD_PREDIO.name());
            indicadores.add(ParamIndicador.IND_ACCESIBILIDAD_POBLACION_AFECTADA.name());
            indicadores.add(ParamIndicador.IND_ACCESIBILIDAD_POBLACION_TOTAL.name());
            indicadores.add(ParamIndicador.IND_AMPLIACION_M2_AMPLIAR_COBERTURA.name());
            indicadores.add(ParamIndicador.IND_AMPLIACION_ALUMNOS_NUEVOS_INFRAESTRUCTURA.name());
            indicadores.add(ParamIndicador.IND_PROPIEDAD_TOTAL_ESTUDIANTES_PREDIO_PROBLEMA_LEGALIZACION.name());
            indicadores.add(ParamIndicador.IND_PROPIEDAD_TOTAL_PREDIO_PROBLEMA_LEGALIZACION.name());

            List<Object[]> areas = facParam.consultarIndicadores(indicadores, ParamNivelConsul.SEDE.getNivel(), idSede);
            if (!areas.isEmpty()) {
                int i = 0;
                parametros.put("construcPrimerPiso", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? null : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP) + "");
                i++;
                parametros.put("posibilidadConstrucAltura", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? null : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                i++;
                parametros.put("area_predio", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? null : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                parametros.put("indice_ocupacion", UtilCadena.isNullOVacio((String) parametros.get("construcPrimerPiso")) || UtilCadena.isNullOVacio((String) areas.get(0)[2]) || new BigDecimal(areas.get(0)[2] + "").compareTo(BigDecimal.ZERO) == 0 ? null : new BigDecimal(parametros.get("construcPrimerPiso") + "").divide(new BigDecimal(areas.get(0)[2] + ""), 4, RoundingMode.HALF_UP).multiply(new BigDecimal("100")).setScale(2, RoundingMode.HALF_UP) + "%");
                i++;
                parametros.put("indice_construccion", UtilCadena.isNullOVacio((String) areas.get(0)[i]) || UtilCadena.isNullOVacio(parametros.get("area_predio") + "") || UtilCadena.isNullOVacio(areas.get(0)[2]) || new BigDecimal(areas.get(0)[2] + "").compareTo(BigDecimal.ZERO) == 0 ? null : new BigDecimal(areas.get(0)[3] + "").divide(new BigDecimal(areas.get(0)[2] + ""), 4, RoundingMode.HALF_UP).multiply(new BigDecimal("100")).setScale(2, RoundingMode.HALF_UP) + "%");
                i++;
                parametros.put("m2RequeridosPredio", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? null : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                i++;
                parametros.put("construible_primer_piso", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? null : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                i++;
                parametros.put("construible_total", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? null : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                i++;
                parametros.put("idoneidad", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? null : Utilidades.obtenerMensaje("aplicacion.general." + (new Double(areas.get(0)[i] + "") > new Double(100) ? "cumple" : "noCumple")));
                i++;
                parametros.put("accPoblAfec", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]);
                i++;
                if (parametros.get("accPoblAfec") != null && areas.get(0)[i] != null && new BigDecimal(areas.get(0)[i] + "").compareTo(BigDecimal.ZERO) == 0) {
                    parametros.put("accPoblAfecPorcen", ((100 * new Integer((String) parametros.get("accPoblAfec"))) / new Integer((String) areas.get(0)[i])) + "");
                } else {
                    parametros.put("accPoblAfecPorcen", "0");
                }
                i++;
                parametros.put("m2AmpliarCobertura", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? null : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP) + "");
                i++;
                parametros.put("alumnosNuevosInfraestructura", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? null : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP) + "");
                i++;
                parametros.put("totalEstudiantesProblemaLegalizacion", new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalPrediosProblemaLegalizacion", new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;

            }

            List<String[]> indBusqUnidadesFun = new ArrayList<String[]>();
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_PISO.name(), CalificacionCondicion.CUATRO.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_PISO.name(), CalificacionCondicion.TRES.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_PISO.name(), CalificacionCondicion.DOS.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_PISO.name(), CalificacionCondicion.UNO.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_MURO.name(), CalificacionCondicion.CUATRO.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_MURO.name(), CalificacionCondicion.TRES.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_MURO.name(), CalificacionCondicion.DOS.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_MURO.name(), CalificacionCondicion.UNO.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_ACABADO_MURO.name(), CalificacionCondicion.CUATRO.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_ACABADO_MURO.name(), CalificacionCondicion.TRES.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_ACABADO_MURO.name(), CalificacionCondicion.DOS.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_ACABADO_MURO.name(), CalificacionCondicion.UNO.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_TECHO.name(), CalificacionCondicion.CUATRO.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_TECHO.name(), CalificacionCondicion.TRES.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_TECHO.name(), CalificacionCondicion.DOS.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_TECHO.name(), CalificacionCondicion.UNO.getValor() + ""});

            areas = facParam.consultarIndicadoresUnidadesFuncionales(indBusqUnidadesFun, ParamNivelConsul.SEDE.getNivel(), idSede);
            int i = 0;
            if (!areas.isEmpty()) {
                parametros.put("areaPisosCond4", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? new BigDecimal("0") : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                i++;
                parametros.put("areaPisosCond3", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? new BigDecimal("0") : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                i++;
                parametros.put("areaPisosCond2", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? new BigDecimal("0") : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                i++;
                parametros.put("areaPisosCond1", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? new BigDecimal("0") : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                i++;
                parametros.put("areaMurosCond4", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? new BigDecimal("0") : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                i++;
                parametros.put("areaMurosCond3", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? new BigDecimal("0") : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                i++;
                parametros.put("areaMurosCond2", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? new BigDecimal("0") : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                i++;
                parametros.put("areaMurosCond1", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? new BigDecimal("0") : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                i++;
                parametros.put("areaAcabadosMuroCond4", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? new BigDecimal("0") : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                i++;
                parametros.put("areaAcabadosMuroCond3", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? new BigDecimal("0") : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                i++;
                parametros.put("areaAcabadosMuroCond2", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? new BigDecimal("0") : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                i++;
                parametros.put("areaAcabadosMuroCond1", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? new BigDecimal("0") : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                i++;
                parametros.put("areaCieloRasosCond4", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? new BigDecimal("0") : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                i++;
                parametros.put("areaCieloRasosCond3", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? new BigDecimal("0") : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                i++;
                parametros.put("areaCieloRasosCond2", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? new BigDecimal("0") : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                i++;
                parametros.put("areaCieloRasosCond1", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? new BigDecimal("0") : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                i++;
            }

            parametros.put("accCumplimiento", ((100 * accCumplen) / listaDatosPredios.size()) + "");

            parametros.put("promedioConsumoAgua", (consumoAgua.divide(new BigDecimal(listaDatosPredios.size()))).setScale(2, RoundingMode.HALF_UP) + "");
            parametros.put("promedioConsumoEnergia", (consumoEnergia.divide(new BigDecimal(listaDatosPredios.size()))).setScale(2, RoundingMode.HALF_UP) + "");

            this.anadirCalificaciones(idSede, ParamNivelConsul.SEDE.getNivel());

            parametros.put("murosFachadasValor1", ((Integer) murosFachadasValor1 + (Integer) murosFachadasValor2 + (Integer) murosFachadasValor3 + (Integer) murosFachadasValor4) == 0 ? "0%" : new BigDecimal(new Double(100 * (Integer) murosFachadasValor1) / new Double((Integer) murosFachadasValor1 + (Integer) murosFachadasValor2 + (Integer) murosFachadasValor3 + (Integer) murosFachadasValor4)).setScale(2, RoundingMode.HALF_UP) + "%");
            parametros.put("murosFachadasValor2", ((Integer) murosFachadasValor1 + (Integer) murosFachadasValor2 + (Integer) murosFachadasValor3 + (Integer) murosFachadasValor4) == 0 ? "0%" : new BigDecimal(new Double(100 * (Integer) murosFachadasValor2) / new Double((Integer) murosFachadasValor1 + (Integer) murosFachadasValor2 + (Integer) murosFachadasValor3 + (Integer) murosFachadasValor4)).setScale(2, RoundingMode.HALF_UP) + "%");
            parametros.put("murosFachadasValor3", ((Integer) murosFachadasValor1 + (Integer) murosFachadasValor2 + (Integer) murosFachadasValor3 + (Integer) murosFachadasValor4) == 0 ? "0%" : new BigDecimal(new Double(100 * (Integer) murosFachadasValor3) / new Double((Integer) murosFachadasValor1 + (Integer) murosFachadasValor2 + (Integer) murosFachadasValor3 + (Integer) murosFachadasValor4)).setScale(2, RoundingMode.HALF_UP) + "%");
            parametros.put("murosFachadasValor4", ((Integer) murosFachadasValor1 + (Integer) murosFachadasValor2 + (Integer) murosFachadasValor3 + (Integer) murosFachadasValor4) == 0 ? "0%" : new BigDecimal(new Double(100 * (Integer) murosFachadasValor4) / new Double((Integer) murosFachadasValor1 + (Integer) murosFachadasValor2 + (Integer) murosFachadasValor3 + (Integer) murosFachadasValor4)).setScale(2, RoundingMode.HALF_UP) + "%");

            parametros.put("acabadosFachadasValor1", ((Integer) acabadosFachadasValor1 + (Integer) acabadosFachadasValor2 + (Integer) acabadosFachadasValor3 + (Integer) acabadosFachadasValor4) == 0 ? "0%" : new BigDecimal(new Double(100 * (Integer) acabadosFachadasValor1) / new Double((Integer) acabadosFachadasValor1 + (Integer) acabadosFachadasValor2 + (Integer) acabadosFachadasValor3 + (Integer) acabadosFachadasValor4)).setScale(2, RoundingMode.HALF_UP) + "%");
            parametros.put("acabadosFachadasValor2", ((Integer) acabadosFachadasValor1 + (Integer) acabadosFachadasValor2 + (Integer) acabadosFachadasValor3 + (Integer) acabadosFachadasValor4) == 0 ? "0%" : new BigDecimal(new Double(100 * (Integer) acabadosFachadasValor2) / new Double((Integer) acabadosFachadasValor1 + (Integer) acabadosFachadasValor2 + (Integer) acabadosFachadasValor3 + (Integer) acabadosFachadasValor4)).setScale(2, RoundingMode.HALF_UP) + "%");
            parametros.put("acabadosFachadasValor3", ((Integer) acabadosFachadasValor1 + (Integer) acabadosFachadasValor2 + (Integer) acabadosFachadasValor3 + (Integer) acabadosFachadasValor4) == 0 ? "0%" : new BigDecimal(new Double(100 * (Integer) acabadosFachadasValor3) / new Double((Integer) acabadosFachadasValor1 + (Integer) acabadosFachadasValor2 + (Integer) acabadosFachadasValor3 + (Integer) acabadosFachadasValor4)).setScale(2, RoundingMode.HALF_UP) + "%");
            parametros.put("acabadosFachadasValor4", ((Integer) acabadosFachadasValor1 + (Integer) acabadosFachadasValor2 + (Integer) acabadosFachadasValor3 + (Integer) acabadosFachadasValor4) == 0 ? "0%" : new BigDecimal(new Double(100 * (Integer) acabadosFachadasValor4) / new Double((Integer) acabadosFachadasValor1 + (Integer) acabadosFachadasValor2 + (Integer) acabadosFachadasValor3 + (Integer) acabadosFachadasValor4)).setScale(2, RoundingMode.HALF_UP) + "%");

            parametros.put("cubiertasValor1", ((Integer) cubiertasValor1 + (Integer) cubiertasValor2 + (Integer) cubiertasValor3 + (Integer) cubiertasValor4) == 0 ? "0%" : new BigDecimal(new Double(100 * (Integer) cubiertasValor1) / new Double((Integer) cubiertasValor1 + (Integer) cubiertasValor2 + (Integer) cubiertasValor3 + (Integer) cubiertasValor4)).setScale(2, RoundingMode.HALF_UP) + "%");
            parametros.put("cubiertasValor2", ((Integer) cubiertasValor1 + (Integer) cubiertasValor2 + (Integer) cubiertasValor3 + (Integer) cubiertasValor4) == 0 ? "0%" : new BigDecimal(new Double(100 * (Integer) cubiertasValor2) / new Double((Integer) cubiertasValor1 + (Integer) cubiertasValor2 + (Integer) cubiertasValor3 + (Integer) cubiertasValor4)).setScale(2, RoundingMode.HALF_UP) + "%");
            parametros.put("cubiertasValor3", ((Integer) cubiertasValor1 + (Integer) cubiertasValor2 + (Integer) cubiertasValor3 + (Integer) cubiertasValor4) == 0 ? "0%" : new BigDecimal(new Double(100 * (Integer) cubiertasValor3) / new Double((Integer) cubiertasValor1 + (Integer) cubiertasValor2 + (Integer) cubiertasValor3 + (Integer) cubiertasValor4)).setScale(2, RoundingMode.HALF_UP) + "%");
            parametros.put("cubiertasValor4", ((Integer) cubiertasValor1 + (Integer) cubiertasValor2 + (Integer) cubiertasValor3 + (Integer) cubiertasValor4) == 0 ? "0%" : new BigDecimal(new Double(100 * (Integer) cubiertasValor4) / new Double((Integer) cubiertasValor1 + (Integer) cubiertasValor2 + (Integer) cubiertasValor3 + (Integer) cubiertasValor4)).setScale(2, RoundingMode.HALF_UP) + "%");

            parametros.put("pisosValor1", ((pisosValor1 + pisosValor2 + pisosValor3 + pisosValor4) == 0 ? "0%" : new BigDecimal((new Double(100 * pisosValor1)) / new Double(pisosValor1 + pisosValor2 + pisosValor3 + pisosValor4)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("pisosValor2", ((pisosValor1 + pisosValor2 + pisosValor3 + pisosValor4) == 0 ? "0%" : new BigDecimal((new Double(100 * pisosValor2)) / new Double(pisosValor1 + pisosValor2 + pisosValor3 + pisosValor4)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("pisosValor3", ((pisosValor1 + pisosValor2 + pisosValor3 + pisosValor4) == 0 ? "0%" : new BigDecimal((new Double(100 * pisosValor3)) / new Double(pisosValor1 + pisosValor2 + pisosValor3 + pisosValor4)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("pisosValor4", ((pisosValor1 + pisosValor2 + pisosValor3 + pisosValor4) == 0 ? "0%" : new BigDecimal((new Double(100 * pisosValor4)) / new Double(pisosValor1 + pisosValor2 + pisosValor3 + pisosValor4)).setScale(2, RoundingMode.HALF_UP)) + "%");

            parametros.put("murosValor1", ((murosValor1 + murosValor2 + murosValor3 + murosValor4) == 0 ? "0%" : new BigDecimal((new Double(100 * murosValor1)) / new Double(murosValor1 + murosValor2 + murosValor3 + murosValor4)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("murosValor2", ((murosValor1 + murosValor2 + murosValor3 + murosValor4) == 0 ? "0%" : new BigDecimal((new Double(100 * murosValor2)) / new Double(murosValor1 + murosValor2 + murosValor3 + murosValor4)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("murosValor3", ((murosValor1 + murosValor2 + murosValor3 + murosValor4) == 0 ? "0%" : new BigDecimal((new Double(100 * murosValor3)) / new Double(murosValor1 + murosValor2 + murosValor3 + murosValor4)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("murosValor4", ((murosValor1 + murosValor2 + murosValor3 + murosValor4) == 0 ? "0%" : new BigDecimal((new Double(100 * murosValor4)) / new Double(murosValor1 + murosValor2 + murosValor3 + murosValor4)).setScale(2, RoundingMode.HALF_UP)) + "%");

            parametros.put("acabadosMuroValor1", ((acabadosMuroValor1 + acabadosMuroValor2 + acabadosMuroValor3 + acabadosMuroValor4) == 0 ? "0%" : new BigDecimal(new Double(100 * acabadosMuroValor1) / new Double(acabadosMuroValor1 + acabadosMuroValor2 + acabadosMuroValor3 + acabadosMuroValor4)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("acabadosMuroValor2", ((acabadosMuroValor1 + acabadosMuroValor2 + acabadosMuroValor3 + acabadosMuroValor4) == 0 ? "0%" : new BigDecimal(new Double(100 * acabadosMuroValor2) / new Double(acabadosMuroValor1 + acabadosMuroValor2 + acabadosMuroValor3 + acabadosMuroValor4)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("acabadosMuroValor3", ((acabadosMuroValor1 + acabadosMuroValor2 + acabadosMuroValor3 + acabadosMuroValor4) == 0 ? "0%" : new BigDecimal(new Double(100 * acabadosMuroValor3) / new Double(acabadosMuroValor1 + acabadosMuroValor2 + acabadosMuroValor3 + acabadosMuroValor4)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("acabadosMuroValor4", ((acabadosMuroValor1 + acabadosMuroValor2 + acabadosMuroValor3 + acabadosMuroValor4) == 0 ? "0%" : new BigDecimal(new Double(100 * acabadosMuroValor4) / new Double(acabadosMuroValor1 + acabadosMuroValor2 + acabadosMuroValor3 + acabadosMuroValor4)).setScale(2, RoundingMode.HALF_UP)) + "%");

            parametros.put("ventanasValor1", ((ventanasValor1 + ventanasValor2 + ventanasValor3 + ventanasValor4) == 0 ? "0%" : new BigDecimal(new Double(100 * ventanasValor1) / new Double(ventanasValor1 + ventanasValor2 + ventanasValor3 + ventanasValor4)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("ventanasValor2", ((ventanasValor1 + ventanasValor2 + ventanasValor3 + ventanasValor4) == 0 ? "0%" : new BigDecimal(new Double(100 * ventanasValor2) / new Double(ventanasValor1 + ventanasValor2 + ventanasValor3 + ventanasValor4)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("ventanasValor3", ((ventanasValor1 + ventanasValor2 + ventanasValor3 + ventanasValor4) == 0 ? "0%" : new BigDecimal(new Double(100 * ventanasValor3) / new Double(ventanasValor1 + ventanasValor2 + ventanasValor3 + ventanasValor4)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("ventanasValor4", ((ventanasValor1 + ventanasValor2 + ventanasValor3 + ventanasValor4) == 0 ? "0%" : new BigDecimal(new Double(100 * ventanasValor4) / new Double(ventanasValor1 + ventanasValor2 + ventanasValor3 + ventanasValor4)).setScale(2, RoundingMode.HALF_UP)) + "%");

            parametros.put("puertasValor1", ((puertasValor1 + puertasValor2 + puertasValor3 + puertasValor4) == 0 ? "0%" : new BigDecimal(new Double(100 * puertasValor1) / new Double(puertasValor1 + puertasValor2 + puertasValor3 + puertasValor4)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("puertasValor2", ((puertasValor1 + puertasValor2 + puertasValor3 + puertasValor4) == 0 ? "0%" : new BigDecimal(new Double(100 * puertasValor2) / new Double(puertasValor1 + puertasValor2 + puertasValor3 + puertasValor4)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("puertasValor3", ((puertasValor1 + puertasValor2 + puertasValor3 + puertasValor4) == 0 ? "0%" : new BigDecimal(new Double(100 * puertasValor3) / new Double(puertasValor1 + puertasValor2 + puertasValor3 + puertasValor4)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("puertasValor4", ((puertasValor1 + puertasValor2 + puertasValor3 + puertasValor4) == 0 ? "0%" : new BigDecimal(new Double(100 * puertasValor4) / new Double(puertasValor1 + puertasValor2 + puertasValor3 + puertasValor4)).setScale(2, RoundingMode.HALF_UP)) + "%");

            parametros.put("cielosValor1", ((cielosValor1 + cielosValor2 + cielosValor3 + cielosValor4) == 0 ? "0%" : new BigDecimal(new Double(100 * cielosValor1) / new Double(cielosValor1 + cielosValor2 + cielosValor3 + cielosValor4)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("cielosValor2", ((cielosValor1 + cielosValor2 + cielosValor3 + cielosValor4) == 0 ? "0%" : new BigDecimal(new Double(100 * cielosValor2) / new Double(cielosValor1 + cielosValor2 + cielosValor3 + cielosValor4)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("cielosValor3", ((cielosValor1 + cielosValor2 + cielosValor3 + cielosValor4) == 0 ? "0%" : new BigDecimal(new Double(100 * cielosValor3) / new Double(cielosValor1 + cielosValor2 + cielosValor3 + cielosValor4)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("cielosValor4", ((cielosValor1 + cielosValor2 + cielosValor3 + cielosValor4) == 0 ? "0%" : new BigDecimal(new Double(100 * cielosValor4) / new Double(cielosValor1 + cielosValor2 + cielosValor3 + cielosValor4)).setScale(2, RoundingMode.HALF_UP)) + "%");

            parametros.put("iluminacionValor1", (numIluminacion == 0 ? "0%" : new BigDecimal(new Double(100 * iluminacionValor1) / new Double(numIluminacion)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("iluminacionValor2", (numIluminacion == 0 ? "0%" : new BigDecimal(new Double(100 * iluminacionValor2) / new Double(numIluminacion)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("iluminacionValor3", (numIluminacion == 0 ? "0%" : new BigDecimal(new Double(100 * iluminacionValor3) / new Double(numIluminacion)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("iluminacionValor4", (numIluminacion == 0 ? "0%" : new BigDecimal(new Double(100 * iluminacionValor4) / new Double(numIluminacion)).setScale(2, RoundingMode.HALF_UP)) + "%");

            parametros.put("ventilacionValor1", (numVentilacion == 0 ? "0%" : new BigDecimal(new Double(100 * ventilacionValor1) / new Double(numVentilacion)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("ventilacionValor2", (numVentilacion == 0 ? "0%" : new BigDecimal(new Double(100 * ventilacionValor2) / new Double(numVentilacion)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("ventilacionValor3", (numVentilacion == 0 ? "0%" : new BigDecimal(new Double(100 * ventilacionValor3) / new Double(numVentilacion)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("ventilacionValor4", (numVentilacion == 0 ? "0%" : new BigDecimal(new Double(100 * ventilacionValor4) / new Double(numVentilacion)).setScale(2, RoundingMode.HALF_UP)) + "%");

            parametros.put("acusticaValor1", (numAcustica == 0 ? "0%" : new BigDecimal(new Double(100 * acusticaValor1) / new Double(numAcustica)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("acusticaValor2", (numAcustica == 0 ? "0%" : new BigDecimal(new Double(100 * acusticaValor2) / new Double(numAcustica)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("acusticaValor3", (numAcustica == 0 ? "0%" : new BigDecimal(new Double(100 * acusticaValor3) / new Double(numAcustica)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("acusticaValor4", (numAcustica == 0 ? "0%" : new BigDecimal(new Double(100 * acusticaValor4) / new Double(numAcustica)).setScale(2, RoundingMode.HALF_UP)) + "%");

            parametros.put("duchasValor1", (numDuchas == 0 ? "0%" : new BigDecimal(new Double(100 * duchasValor1) / new Double(numDuchas)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("duchasValor2", (numDuchas == 0 ? "0%" : new BigDecimal(new Double(100 * duchasValor2) / new Double(numDuchas)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("duchasValor3", (numDuchas == 0 ? "0%" : new BigDecimal(new Double(100 * duchasValor3) / new Double(numDuchas)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("duchasValor4", (numDuchas == 0 ? "0%" : new BigDecimal(new Double(100 * duchasValor4) / new Double(numDuchas)).setScale(2, RoundingMode.HALF_UP)) + "%");

            parametros.put("lavamanosValor1", (numLavamanos == 0 ? "0%" : new BigDecimal(new Double(100 * lavamanosValor1) / new Double(numLavamanos)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("lavamanosValor2", (numLavamanos == 0 ? "0%" : new BigDecimal(new Double(100 * lavamanosValor2) / new Double(numLavamanos)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("lavamanosValor3", (numLavamanos == 0 ? "0%" : new BigDecimal(new Double(100 * lavamanosValor3) / new Double(numLavamanos)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("lavamanosValor4", (numLavamanos == 0 ? "0%" : new BigDecimal(new Double(100 * lavamanosValor4) / new Double(numLavamanos)).setScale(2, RoundingMode.HALF_UP)) + "%");

            parametros.put("orinalesValor1", (numOrinales == 0 ? "0%" : new BigDecimal(new Double(100 * orinalesValor1) / new Double(numOrinales)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("orinalesValor2", (numOrinales == 0 ? "0%" : new BigDecimal(new Double(100 * orinalesValor2) / new Double(numOrinales)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("orinalesValor3", (numOrinales == 0 ? "0%" : new BigDecimal(new Double(100 * orinalesValor3) / new Double(numOrinales)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("orinalesValor4", (numOrinales == 0 ? "0%" : new BigDecimal(new Double(100 * orinalesValor4) / new Double(numOrinales)).setScale(2, RoundingMode.HALF_UP)) + "%");

            parametros.put("inodorosValor1", (numInodoros == 0 ? "0%" : new BigDecimal(new Double(100 * inodorosValor1) / new Double(numInodoros)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("inodorosValor2", (numInodoros == 0 ? "0%" : new BigDecimal(new Double(100 * inodorosValor2) / new Double(numInodoros)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("inodorosValor3", (numInodoros == 0 ? "0%" : new BigDecimal(new Double(100 * inodorosValor3) / new Double(numInodoros)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("inodorosValor4", (numInodoros == 0 ? "0%" : new BigDecimal(new Double(100 * inodorosValor4) / new Double(numInodoros)).setScale(2, RoundingMode.HALF_UP)) + "%");

            parametros.put("aislamientoTermicoPisos", aislamientoTermicoPisos);
            parametros.put("aislamientoTermicoMuros", aislamientoTermicoMuros);
            parametros.put("aislamientoTermicoVentanas", aislamientoTermicoVentanas);
            parametros.put("aislamientoTermicoCielos", aislamientoTermicoCielos);
            parametros.put("aislamientoTermicoOtros", aislamientoTermicoOtros);

            parametros.put("numAcustica", numAcustica);
            parametros.put("numDuchas", numDuchas);
            parametros.put("numIluminacion", numIluminacion);
            parametros.put("numInodoros", numInodoros);
            parametros.put("numLavamanos", numLavamanos);
            parametros.put("numOrinales", numOrinales);
            parametros.put("numVentilacion", numVentilacion);

            HashMap<String, Object> datosUnidadesFuncionales = this.obtenerUnidadesFuncionales(idSede, ParamNivelConsul.SEDE.getNivel());
            //Añadimos todos los datos de las unidaddes funcionales a los parámetros
            String[] noStrings = {"propAreasCentrales", "propAreasApoyo", "propAreasAulas", "propAreasLaboratorios", "propAreasCentroRecursosAprendizaje", "propAreasTalleres", "propAreasExtension", "propAreasServSanitarios", "propAreasAbastProcesa", "propAreasConducAdministra"};
            List<String> noStr = new ArrayList<String>();
            Collections.addAll(noStr, noStrings);

            for (String key : datosUnidadesFuncionales.keySet()) {
                if (noStr.contains(key)) {
                    parametros.put(key, datosUnidadesFuncionales.get(key));
                } else {
                    parametros.put(key, datosUnidadesFuncionales.get(key) + "");
                }
            }

            DateFormat df = DateFormat.getDateInstance(DateFormat.LONG);

            parametros.put("fechaReporte", df.format(new Date()));
            parametros.put("municipio", sede.getEstablecimiento().getNivel().getDescripcion());
            parametros.put("departamento", sede.getEstablecimiento().getNivel().getPadre().getDescripcion());
            parametros.put("region", sede.getEstablecimiento().getNivel().getPadre().getPadre().getDescripcion());

            String periodoCorte = facParam.consultarPeriodoIndicadores();
            if (periodoCorte != null) {
                Calendar calAux = Calendar.getInstance();
                calAux.set(new Integer(periodoCorte.substring(0, 4)), new Integer(periodoCorte.substring(4, 6)), 1);
                SimpleDateFormat format = new SimpleDateFormat("MMMM yyyy");
                String fecha = format.format(calAux.getTime());
                parametros.put("fechaCorte", fecha.substring(0, 1).toUpperCase() + fecha.substring(1, fecha.length()));
            }

            HashMap datosRedistribucion = this.obtenerRedistribucion(sede, ParamNivelConsul.SEDE.getNivel(), this.getIdNivel0(sede.getEstablecimiento().getNivel()).getId());
            if (datosRedistribucion.isEmpty()) {
                parametros.put("utilizacion", new BigDecimal(0));
                parametros.put("cuposTotal", new BigDecimal(0));
            } else {
                parametros.put("utilizacion", datosRedistribucion.get("utilizacion"));
                parametros.put("cuposTotal", datosRedistribucion.get("cuposTotal"));
            }

            ReporteInformeGeneral reporteInformeGeneralSede = new ReporteInformeGeneral();
            reporteInformeGeneralSede.setListaDatosPredios(listaDatosPredios);

            List list = new ArrayList();
            list.add(reporteInformeGeneralSede);

            parametros.put(JRParameter.REPORT_RESOURCE_BUNDLE, ResourceBundle.getBundle("etiquetas", FacesContext.getCurrentInstance().getViewRoot().getLocale()));

            stream = UtilExportar.getInformeGeneral("informeGeneral/sede", "InformeGeneralSede", parametros, list);

        } catch (Exception e) {
            // UtilOut.println("ERROR en imprimirPdfSede: " + e.toString());
            mostrarMensajeError(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"), e.getMessage());
            ManejadorErrores.ingresarExcepcionEnLog(e, InformeGeneralBean.class);
        }

        return new DefaultStreamedContent(stream, "application/x-download", "InformeGeneralSede.pdf");
    }

    private StreamedContent imprimirPdfEstablecimiento(Long idEstablecimiento, String codEstablecimiento) {
        parametros = new HashMap<String, Object>();
        InputStream stream = null;
        try {
            Establecimiento establecimiento = facEstablecimientos.getEstablecimientoPorCodigo(codEstablecimiento);

            parametros.put("codigoEst", establecimiento.getCodigo());
            parametros.put("nombreEst", establecimiento.getNombre());
            parametros.put("telefono", establecimiento.getTelefonoEst());
            parametros.put("email", establecimiento.getEmailEst());
            parametros.put("rector", establecimiento.getRector());
            parametros.put("telRector", establecimiento.getTelRector());

            List<BigDecimal> listaInstrumentosDigitados = facInstrumentos.obtenerInstrumentosDigPorEstablecimiento(idEstablecimiento);
            Map<String, Object> datos = null;
            parametros.put("numPredios", listaInstrumentosDigitados.size());
            parametros.put("numSedes", establecimiento.getSedes().size());
            List<DatosPredio> listaDatosPredios = new ArrayList<DatosPredio>();
            BigDecimal superficie = new BigDecimal("0");
            BigDecimal consumoAgua = new BigDecimal("0");
            BigDecimal consumoEnergia = new BigDecimal("0");

            List<String> preCodes = new ArrayList<String>();
            List<String> priCodes = new ArrayList<String>();
            List<String> secCodes = new ArrayList<String>();
            List<String> basCodes = new ArrayList<String>();
            List<String> medCodes = new ArrayList<String>();
            List<String[]> nivelesEnsenanza = facInformeGeneral.getEquivalentes("nivel_educativo");
            for (Iterator<String[]> it = nivelesEnsenanza.iterator(); it.hasNext();) {
                Object[] n = it.next();
                if (n[0].equals("NE_PRE")) {
                    preCodes.add((String) n[1]);
                } else if (n[0].equals("NE_PRI")) {
                    priCodes.add((String) n[1]);
                } else if (n[0].equals("NE_SEC")) {
                    secCodes.add((String) n[1]);
                } else if (n[0].equals("NE_BAS")) {
                    basCodes.add((String) n[1]);
                } else if (n[0].equals("NE_MED")) {
                    medCodes.add((String) n[1]);
                }
            }

            int accCumplen = 0;

            int murosFachadasValor1 = 0;
            int murosFachadasValor2 = 0;
            int murosFachadasValor3 = 0;
            int murosFachadasValor4 = 0;
            int acabadosFachadasValor1 = 0;
            int acabadosFachadasValor2 = 0;
            int acabadosFachadasValor3 = 0;
            int acabadosFachadasValor4 = 0;
            int cubiertasValor1 = 0;
            int cubiertasValor2 = 0;
            int cubiertasValor3 = 0;
            int cubiertasValor4 = 0;

            int pisosValor1 = 0;
            int pisosValor2 = 0;
            int pisosValor3 = 0;
            int pisosValor4 = 0;
            int murosValor1 = 0;
            int murosValor2 = 0;
            int murosValor3 = 0;
            int murosValor4 = 0;
            int acabadosMuroValor1 = 0;
            int acabadosMuroValor2 = 0;
            int acabadosMuroValor3 = 0;
            int acabadosMuroValor4 = 0;
            int ventanasValor1 = 0;
            int ventanasValor2 = 0;
            int ventanasValor3 = 0;
            int ventanasValor4 = 0;
            int puertasValor1 = 0;
            int puertasValor2 = 0;
            int puertasValor3 = 0;
            int puertasValor4 = 0;
            int cielosValor1 = 0;
            int cielosValor2 = 0;
            int cielosValor3 = 0;
            int cielosValor4 = 0;
            int numIluminacion = 0;
            int iluminacionValor1 = 0;
            int iluminacionValor2 = 0;
            int iluminacionValor3 = 0;
            int iluminacionValor4 = 0;
            int numVentilacion = 0;
            int ventilacionValor1 = 0;
            int ventilacionValor2 = 0;
            int ventilacionValor3 = 0;
            int ventilacionValor4 = 0;
            int numAcustica = 0;
            int acusticaValor1 = 0;
            int acusticaValor2 = 0;
            int acusticaValor3 = 0;
            int acusticaValor4 = 0;
            int numDuchas = 0;
            int duchasValor1 = 0;
            int duchasValor2 = 0;
            int duchasValor3 = 0;
            int duchasValor4 = 0;
            int numLavamanos = 0;
            int lavamanosValor1 = 0;
            int lavamanosValor2 = 0;
            int lavamanosValor3 = 0;
            int lavamanosValor4 = 0;
            int numOrinales = 0;
            int orinalesValor1 = 0;
            int orinalesValor2 = 0;
            int orinalesValor3 = 0;
            int orinalesValor4 = 0;
            int numInodoros = 0;
            int inodorosValor1 = 0;
            int inodorosValor2 = 0;
            int inodorosValor3 = 0;
            int inodorosValor4 = 0;
            int aislamientoTermicoPisos = 0;
            int aislamientoTermicoMuros = 0;
            int aislamientoTermicoVentanas = 0;
            int aislamientoTermicoCielos = 0;
            int aislamientoTermicoOtros = 0;

            int poblacionTotal = 0;
            int poblacionAfectadaRiesgos = 0;
            int riesgosCumplen = 0;

            int totalCobertura = 0;

            for (BigDecimal idInstrumentoDig : listaInstrumentosDigitados) {
                InstrumentoDig instrumentoDig = facInstrumentos.consultarInstrumentoPorCodigo(idInstrumentoDig.longValue());
                datos = obtenerRespuestas(instrumentoDig);

                //INICIO DATOS PREDIO
                DatosPredio dp = new DatosPredio();
                dp.setNombrePredio(instrumentoDig.getPredio().getNombre());
                dp.setCodigoPredio(instrumentoDig.getPredio().getCodigo());
                dp.setNombreSede(instrumentoDig.getSede().getNombre());
                dp.setCodigoSede(instrumentoDig.getSede().getCodigo());
                dp.setTelefono((String) datos.get("RESP_016"));
                dp.setDireccion((String) datos.get("RESP_015"));
                dp.setEmail((String) datos.get("RESP_018"));
                dp.setRector((String) datos.get("RESP_019"));
                dp.setTelRector((String) datos.get("RESP_020"));
                //FIN DATOS PREDIO
                //INICIO COBERTURAS
                String[] respuestasNivelEnsenanza = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10"};
                int pre = 0, primaria = 0, secundaria = 0, basica = 0, media = 0;
                TipologiaValorNombre tv;
                for (String resp : respuestasNivelEnsenanza) {
                    if (preCodes.contains(datos.get("RESP_033_" + resp))) {
                        if (datos.containsKey("RESP_035_" + resp) && datos.get("RESP_035_" + resp) != null) {
                            pre += (new Integer(datos.get("RESP_035_" + resp) + ""));
                        }
                    } else if (datos.get("RESP_035_" + resp) != null && priCodes.contains(datos.get("RESP_033_" + resp))) {
                        primaria += (new Integer(datos.get("RESP_035_" + resp) + ""));
                    } else if (datos.get("RESP_035_" + resp) != null && secCodes.contains(datos.get("RESP_033_" + resp))) {
                        secundaria += (new Integer(datos.get("RESP_035_" + resp) + ""));
                    } else if (datos.get("RESP_035_" + resp) != null && basCodes.contains(datos.get("RESP_033_" + resp))) {
                        basica += (new Integer(datos.get("RESP_035_" + resp) + ""));
                    } else if (datos.get("RESP_035_" + resp) != null && medCodes.contains(datos.get("RESP_033_" + resp))) {
                        media += (new Integer(datos.get("RESP_035_" + resp) + ""));
                    }

                    if (preCodes.contains(datos.get("RESP_199_" + resp))) {
                        if (datos.containsKey("RESP_201_" + resp) && datos.get("RESP_201_" + resp) != null) {
                            pre += (new Integer(datos.get("RESP_201_" + resp) + ""));
                        }
                    } else if (datos.get("RESP_201_" + resp) != null && priCodes.contains(datos.get("RESP_199_" + resp))) {
                        primaria += (new Integer(datos.get("RESP_201_" + resp) + ""));
                    } else if (datos.get("RESP_201_" + resp) != null && secCodes.contains(datos.get("RESP_199_" + resp))) {
                        secundaria += (new Integer(datos.get("RESP_201_" + resp) + ""));
                    } else if (datos.get("RESP_201_" + resp) != null && basCodes.contains(datos.get("RESP_199_" + resp))) {
                        basica += (new Integer(datos.get("RESP_201_" + resp) + ""));
                    } else if (datos.get("RESP_201_" + resp) != null && medCodes.contains(datos.get("RESP_199_" + resp))) {
                        media += (new Integer(datos.get("RESP_201_" + resp) + ""));
                    }
                    //Se les da el valor de la tipología para que se muestre el texto en el reporte
                    if (datos.get("RESP_033_" + resp) != null) {
                        tv = facParam.consultarValorTipologia(respuestaTipologia.get("RESP_033_" + resp), datos.get("RESP_033_" + resp).toString(), getUsuarioSesion().getUsuario().getIdioma().getId());
                        datos.put(resp, tv != null ? tv.getNombre() : "");
                    } else if (datos.get("RESP_199_" + resp) != null) {
                        tv = facParam.consultarValorTipologia(respuestaTipologia.get("RESP_199_" + resp), datos.get("RESP_199_" + resp).toString(), getUsuarioSesion().getUsuario().getIdioma().getId());
                        datos.put(resp, tv != null ? tv.getNombre() : "");
                    }
                }
                dp.setGenero(datos.get("RESP_040") + "");
                dp.setPre(pre + "");
                dp.setPrimaria(primaria + "");
                dp.setSecundaria(secundaria + "");
                dp.setBasica(basica + "");
                dp.setMedia(media + "");
                dp.setTotal((pre + primaria + secundaria + basica + media) + "");
                totalCobertura += (pre + primaria + secundaria + basica + media);
                //FIN COBERTURAS

                //Superficie
                superficie = superficie.add(new BigDecimal((String) datos.get("RESP_043")));

                //INICIO ACCESIBILIDAD EXTERNA
                String[] respuestasAccExt = {"01", "03", "05", "07", "09"};
                int numAccesosExt = 0;
                for (String resp : respuestasAccExt) {
                    if (datos.containsKey("RESP_045_" + resp)) {
                        numAccesosExt++;
                    }
                }
                dp.setNumAccesos(numAccesosExt + "");
                //FIN ACCESIBILIDAD EXTERNA

                //INICIO RIESGOS
                String[] respuestasRiesgos = {"01", "03", "05"};
                int numRiesgosNaturales = 0;
                int numRiesgosAntropicos = 0;

                List<String> indicadores = new ArrayList<String>();
                indicadores.add(ParamIndicador.IND_TOTAL_POBLACION.name());
                List<Object[]> pobl = facParam.consultarIndicadores(indicadores, ParamNivelConsul.PREDIO.getNivel(), idInstrumentoDig.longValue());
                poblacionTotal += new Integer(pobl.get(0)[0] == null ? "0" : (String) pobl.get(0)[0]);

                for (String resp : respuestasRiesgos) {
                    if (datos.containsKey("RESP_048_" + resp)) {
                        numRiesgosNaturales++;
                    }
                    if (datos.containsKey("RESP_050_" + resp)) {
                        numRiesgosAntropicos++;
                    }
                }

                if (numRiesgosNaturales + numRiesgosAntropicos > 0) {
                    poblacionAfectadaRiesgos += new Integer(pobl.get(0)[0] == null ? "0" : (String) pobl.get(0)[0]);
                }
                dp.setNoNaturales(numRiesgosNaturales + "");
                dp.setNoAntropicos(numRiesgosAntropicos + "");
                //FIN RIESGOS

                //INICIO SERVICIOS
                if (datos.get("RESP_061_05") != null) {
                    dp.setAgua(Utilidades.obtenerMensaje("tipologias.condicion." + facInformeGeneral.consultarCodTipologiaRespuesta("RESP_061_05") + "_" + datos.get("RESP_061_05").toString()));
                }
                if (datos.get("RESP_062_05") != null) {
                    dp.setEnergia(Utilidades.obtenerMensaje("tipologias.condicion." + facInformeGeneral.consultarCodTipologiaRespuesta("RESP_062_05") + "_" + datos.get("RESP_062_05").toString()));
                }
                if (datos.get("RESP_066_05") != null) {
                    dp.setAlcantarillado(Utilidades.obtenerMensaje("tipologias.condicion." + facInformeGeneral.consultarCodTipologiaRespuesta("RESP_066_05") + "_" + datos.get("RESP_066_05").toString()));
                }
                if (datos.get("RESP_067_05") != null) {
                    dp.setRedPluvial(Utilidades.obtenerMensaje("tipologias.condicion." + facInformeGeneral.consultarCodTipologiaRespuesta("RESP_067_05") + "_" + datos.get("RESP_067_05").toString()));
                }
                if (datos.get("RESP_068_05") != null) {
                    dp.setInternet(Utilidades.obtenerMensaje("tipologias.condicion." + facInformeGeneral.consultarCodTipologiaRespuesta("RESP_068_05") + "_" + datos.get("RESP_068_05").toString()));
                }
                if (datos.get("RESP_069_05") != null) {
                    dp.setServTelefono(Utilidades.obtenerMensaje("tipologias.condicion." + facInformeGeneral.consultarCodTipologiaRespuesta("RESP_069_05") + "_" + datos.get("RESP_069_05").toString()));
                }
                if (datos.get("RESP_065_02") != null) {
                    dp.setReciclaje(datos.get("RESP_065_02").toString());
                } else {
                    dp.setReciclaje(Utilidades.obtenerMensaje("informe.general.noaplica"));
                }
                consumoAgua = consumoAgua.add(new BigDecimal(datos.get("RESP_061_06") != null ? datos.get("RESP_061_06") + "" : "0"));
                consumoEnergia = consumoEnergia.add(new BigDecimal(datos.get("RESP_062_06") != null ? datos.get("RESP_062_06") + "" : "0"));
                //FIN SERVICIOS

                //INICIO CONTROLYVIGILANCIA
                if (datos.get("RESP_054_01") != null) {
                    dp.setCerco(transformaSiNoNA(datos.get("RESP_054_01") + ""));
                }
                if (datos.get("RESP_054_04") != null) {
                    dp.setCercoCondicion(Utilidades.obtenerMensaje("tipologias.condicion." + facInformeGeneral.consultarCodTipologiaRespuesta("RESP_054_04") + "_" + datos.get("RESP_054_04").toString()));
                }
                //FIN CONTROLYVIGILANCIA

                indicadores.clear();
                indicadores.add(ParamIndicador.IND_AMPLIACION_AREA_CONSTRUCCION_PRIMER_PISO.name());
                indicadores.add(ParamIndicador.IND_ACCESIBILIDAD_CONDICION_ACCESIBILIDAD.name());
                indicadores.add(ParamIndicador.IND_PROBLEMA_LEGALIZACION.name());
                indicadores.add(ParamIndicador.IND_CONTROL_VIGILANCIA_VULNERABILIDAD.name());
                indicadores.add(ParamIndicador.IND_SEGURIDAD_ESTADO_ESTRUCTURA.name());
                indicadores.add(ParamIndicador.IND_SEGURIDAD_ANALISIS_RUTA_EVACUACION.name());
                indicadores.add(ParamIndicador.IND_SEGURIDAD_EDIF_SENALIZACION_EVACUACION.name());
                indicadores.add(ParamIndicador.IND_SEGURIDAD_REACTIVO_CONTRA_INCENDIO.name());
                indicadores.add(ParamIndicador.IND_AMPLIACION_POSIBILIDAD_CONSTRUCCION_ALTURA.name());
                indicadores.add(ParamIndicador.IND_AREA_REAL_PREDIO.name());
                indicadores.add(ParamIndicador.IND_TOTAL_AREA_CONSTRUCCION_PREDIO.name());
                indicadores.add(ParamIndicador.IND_AMPLIACION_M2_REQUERIDOS_PREDIO.name());
                indicadores.add(ParamIndicador.IND_AMPLIACION_POSIBILIDAD_CONSTRUCCION_PRIMER_PISO.name());
                indicadores.add(ParamIndicador.IND_AMPLIACION_POSIBILIDAD_CONSTRUCCION_TOTAL.name());
                indicadores.add(ParamIndicador.IND_AMPLIACION_M2_AMPLIAR_COBERTURA.name());
                indicadores.add(ParamIndicador.IND_AMPLIACION_ALUMNOS_NUEVOS_INFRAESTRUCTURA.name());

                List<Object[]> areas = facParam.consultarIndicadores(indicadores, ParamNivelConsul.PREDIO.getNivel(), idInstrumentoDig.longValue());
                if (!areas.isEmpty()) {
                    int i = 0;
                    BigDecimal construcPrimerPiso = new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP);
                    i++;
                    dp.setCumplimientoAccesos(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : Utilidades.obtenerMensaje((String) areas.get(0)[i]));
                    i++;
                    dp.setCumplimientoLegalizacion(Utilidades.obtenerMensaje("aplicacion.general." + (!UtilCadena.isNullOVacio((String) areas.get(0)[i]) && SiNoNA.No.name().equals((String) areas.get(0)[i]) ? "numple" : "noCumple")));
                    i++;
                    dp.setVulnerabilidad(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : Utilidades.obtenerMensaje((String) areas.get(0)[i]));
                    i++;
                    dp.setCondicionEstruc(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : Utilidades.obtenerMensaje((String) areas.get(0)[i]));
                    i++;
                    dp.setRutaEvacuacion(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : Utilidades.obtenerMensaje((String) areas.get(0)[i]));
                    i++;
                    dp.setSenalizacionEvacuacion(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : Utilidades.obtenerMensaje((String) areas.get(0)[i]));
                    i++;
                    dp.setEquiposContraIncendios(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : Utilidades.obtenerMensaje((String) areas.get(0)[i]));
                    i++;
                    dp.setConstruibleAltura(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? null : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP) + "");
                    i++;
                    dp.setArea(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? null : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP) + "");
                    dp.setIndOcupacion(dp.getArea() == null || new BigDecimal(dp.getArea()).compareTo(BigDecimal.ZERO) == 0 ? null : construcPrimerPiso.divide(new BigDecimal(dp.getArea()), 4, RoundingMode.HALF_UP).multiply(new BigDecimal("100")).setScale(2, RoundingMode.HALF_UP) + "%");
                    i++;
                    dp.setIndConstruccion(UtilCadena.isNullOVacio((String) areas.get(0)[i]) || UtilCadena.isNullOVacio(dp.getArea()) || new BigDecimal(dp.getArea()).compareTo(BigDecimal.ZERO) == 0 ? null : new BigDecimal(areas.get(0)[i] + "").divide(new BigDecimal(dp.getArea()), 4, RoundingMode.HALF_UP).multiply(new BigDecimal("100")).setScale(2, RoundingMode.HALF_UP) + "%");
                    i++;
                    dp.setM2Requeridos(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? null : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP) + "");
                    i++;
                    dp.setConstruiblePrimerPiso(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? null : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP) + "");
                    i++;
                    dp.setConstruibleTotal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? null : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP) + "");
                    i++;
                    dp.setM2AmpliarCobertura(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? null : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP) + "");
                    i++;
                    dp.setAlumnosNuevosInfraestructura(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? null : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP) + "");
                    i++;

                }

                if (Utilidades.obtenerMensaje(Cumplimiento.SI.getEtiqueta()).toUpperCase().equals(dp.getCumplimientoAccesos())) {
                    accCumplen++;
                }
                if ("No crítico".equals(dp.getCondicion())) {
                    riesgosCumplen++;
                }

                FiltroCalificacion filtro = new FiltroCalificacion();
                filtro.setNivelAgrupamiento(ParamNivelConsul.PREDIO.getNivel());
                filtro.setIdEntidad(idInstrumentoDig.longValue());
                BigDecimal cond = facCalificacion.consultarCalificacionAmbito(filtro, Ambitos.A_RI.name());
                String condRI = (String) facCalificacion.obtenerValorEscala(Ambitos.A_RI.toString(), cond).get(0)[2];
                dp.setCondicion(Utilidades.obtenerMensaje("informe.general.reporte.cal_escala." + condRI));

                List edif = obtenerObjetosEdificios(idInstrumentoDig.longValue());
                //List<ControlVigilancia> listaControlVigilancia=(List)edif.get(0);
                //List<SeguridadAccesibilidad> listaSeguridadAccesibilidad=(List)edif.get(1);
                HashMap datosMaterial = (HashMap) edif.get(2);

                murosFachadasValor1 += (Integer) datosMaterial.get("murosFachadasValor1");
                murosFachadasValor2 += (Integer) datosMaterial.get("murosFachadasValor2");
                murosFachadasValor3 += (Integer) datosMaterial.get("murosFachadasValor3");
                murosFachadasValor4 += (Integer) datosMaterial.get("murosFachadasValor4");

                acabadosFachadasValor1 += (Integer) datosMaterial.get("acabadosFachadasValor1");
                acabadosFachadasValor2 += (Integer) datosMaterial.get("acabadosFachadasValor2");
                acabadosFachadasValor3 += (Integer) datosMaterial.get("acabadosFachadasValor3");
                acabadosFachadasValor4 += (Integer) datosMaterial.get("acabadosFachadasValor4");

                cubiertasValor1 += (Integer) datosMaterial.get("cubiertasValor1");
                cubiertasValor2 += (Integer) datosMaterial.get("cubiertasValor2");
                cubiertasValor3 += (Integer) datosMaterial.get("cubiertasValor3");
                cubiertasValor4 += (Integer) datosMaterial.get("cubiertasValor4");

                HashMap datosEsp = obtenerObjetosEspaciosEscolaresPredios(idInstrumentoDig.longValue());

                pisosValor1 += (Integer) datosEsp.get("pisosValor1");
                pisosValor2 += (Integer) datosEsp.get("pisosValor2");
                pisosValor3 += (Integer) datosEsp.get("pisosValor3");
                pisosValor4 += (Integer) datosEsp.get("pisosValor4");
                murosValor1 += (Integer) datosEsp.get("murosValor1");
                murosValor2 += (Integer) datosEsp.get("murosValor2");
                murosValor3 += (Integer) datosEsp.get("murosValor3");
                murosValor4 += (Integer) datosEsp.get("murosValor4");
                acabadosMuroValor1 += (Integer) datosEsp.get("acabadosMuroValor1");
                acabadosMuroValor2 += (Integer) datosEsp.get("acabadosMuroValor2");
                acabadosMuroValor3 += (Integer) datosEsp.get("acabadosMuroValor3");
                acabadosMuroValor4 += (Integer) datosEsp.get("acabadosMuroValor4");
                ventanasValor1 += (Integer) datosEsp.get("ventanasValor1");
                ventanasValor2 += (Integer) datosEsp.get("ventanasValor2");
                ventanasValor3 += (Integer) datosEsp.get("ventanasValor3");
                ventanasValor4 += (Integer) datosEsp.get("ventanasValor4");
                puertasValor1 += (Integer) datosEsp.get("puertasValor1");
                puertasValor2 += (Integer) datosEsp.get("puertasValor2");
                puertasValor3 += (Integer) datosEsp.get("puertasValor3");
                puertasValor4 += (Integer) datosEsp.get("puertasValor4");
                cielosValor1 += (Integer) datosEsp.get("cielosValor1");
                cielosValor2 += (Integer) datosEsp.get("cielosValor2");
                cielosValor3 += (Integer) datosEsp.get("cielosValor3");
                cielosValor4 += (Integer) datosEsp.get("cielosValor4");
                numIluminacion += (Integer) datosEsp.get("numIluminacion");
                iluminacionValor1 += (Integer) datosEsp.get("iluminacionValor1");
                iluminacionValor2 += (Integer) datosEsp.get("iluminacionValor2");
                iluminacionValor3 += (Integer) datosEsp.get("iluminacionValor3");
                iluminacionValor4 += (Integer) datosEsp.get("iluminacionValor4");
                numVentilacion += (Integer) datosEsp.get("numVentilacion");
                ventilacionValor1 += (Integer) datosEsp.get("ventilacionValor1");
                ventilacionValor2 += (Integer) datosEsp.get("ventilacionValor2");
                ventilacionValor3 += (Integer) datosEsp.get("ventilacionValor3");
                ventilacionValor4 += (Integer) datosEsp.get("ventilacionValor4");
                numAcustica += (Integer) datosEsp.get("numAcustica");
                acusticaValor1 += (Integer) datosEsp.get("acusticaValor1");
                acusticaValor2 += (Integer) datosEsp.get("acusticaValor2");
                acusticaValor3 += (Integer) datosEsp.get("acusticaValor3");
                acusticaValor4 += (Integer) datosEsp.get("acusticaValor4");
                numDuchas += (Integer) datosEsp.get("numDuchas");
                duchasValor1 += (Integer) datosEsp.get("duchasValor1");
                duchasValor2 += (Integer) datosEsp.get("duchasValor2");
                duchasValor3 += (Integer) datosEsp.get("duchasValor3");
                duchasValor4 += (Integer) datosEsp.get("duchasValor4");
                numLavamanos += (Integer) datosEsp.get("numLavamanos");
                lavamanosValor1 += (Integer) datosEsp.get("lavamanosValor1");
                lavamanosValor2 += (Integer) datosEsp.get("lavamanosValor2");
                lavamanosValor3 += (Integer) datosEsp.get("lavamanosValor3");
                lavamanosValor4 += (Integer) datosEsp.get("lavamanosValor4");
                numOrinales += (Integer) datosEsp.get("numOrinales");
                orinalesValor1 += (Integer) datosEsp.get("orinalesValor1");
                orinalesValor2 += (Integer) datosEsp.get("orinalesValor2");
                orinalesValor3 += (Integer) datosEsp.get("orinalesValor3");
                orinalesValor4 += (Integer) datosEsp.get("orinalesValor4");
                numInodoros += (Integer) datosEsp.get("numInodoros");
                inodorosValor1 += (Integer) datosEsp.get("inodorosValor1");
                inodorosValor2 += (Integer) datosEsp.get("inodorosValor2");
                inodorosValor3 += (Integer) datosEsp.get("inodorosValor3");
                inodorosValor4 += (Integer) datosEsp.get("inodorosValor4");
                aislamientoTermicoPisos += (Integer) datosEsp.get("aislamientoTermicoPisos");
                aislamientoTermicoMuros += (Integer) datosEsp.get("aislamientoTermicoMuros");
                aislamientoTermicoVentanas += (Integer) datosEsp.get("aislamientoTermicoVentanas");
                aislamientoTermicoCielos += (Integer) datosEsp.get("aislamientoTermicoCielos");
                aislamientoTermicoOtros += (Integer) datosEsp.get("aislamientoTermicoOtros");

                listaDatosPredios.add(dp);
            }//FIN for listaInstrumentosDigitados

            parametros.put("totalCobertura", totalCobertura);
            parametros.put("poblacionTotal", poblacionTotal);
            parametros.put("poblacionAfectadaRiesgos", poblacionAfectadaRiesgos);
            parametros.put("riesgosCumplen", riesgosCumplen);

            parametros.put("CondMat1", Utilidades.obtenerMensaje("tipologias.condicion." + "EMPMF_1"));
            parametros.put("CondMat2", Utilidades.obtenerMensaje("tipologias.condicion." + "EMPMF_2"));
            parametros.put("CondMat3", Utilidades.obtenerMensaje("tipologias.condicion." + "EMPMF_3"));
            parametros.put("CondMat4", Utilidades.obtenerMensaje("tipologias.condicion." + "EMPMF_4"));

            if (datos != null) {
                for (String key : datos.keySet()) {
                    parametros.put(key, datos.get(key));
                }
            }

            parametros.put("superficie", (superficie.setScale(2, RoundingMode.HALF_UP)) + "");

            List<String> indicadores = new ArrayList<String>();
            indicadores.add(ParamIndicador.IND_AMPLIACION_AREA_CONSTRUCCION_PRIMER_PISO.name());
            indicadores.add(ParamIndicador.IND_AMPLIACION_POSIBILIDAD_CONSTRUCCION_ALTURA.name());
            indicadores.add(ParamIndicador.IND_AREA_REAL_PREDIO.name());
            indicadores.add(ParamIndicador.IND_TOTAL_AREA_CONSTRUCCION_PREDIO.name());
            indicadores.add(ParamIndicador.IND_AMPLIACION_M2_REQUERIDOS_PREDIO.name());
            indicadores.add(ParamIndicador.IND_AMPLIACION_POSIBILIDAD_CONSTRUCCION_PRIMER_PISO.name());
            indicadores.add(ParamIndicador.IND_AMPLIACION_POSIBILIDAD_CONSTRUCCION_TOTAL.name());
            indicadores.add(ParamIndicador.IND_AMPLIACION_IDONEIDAD_PREDIO.name());
            indicadores.add(ParamIndicador.IND_ACCESIBILIDAD_POBLACION_AFECTADA.name());
            indicadores.add(ParamIndicador.IND_ACCESIBILIDAD_POBLACION_TOTAL.name());
            indicadores.add(ParamIndicador.IND_AMPLIACION_M2_AMPLIAR_COBERTURA.name());
            indicadores.add(ParamIndicador.IND_AMPLIACION_ALUMNOS_NUEVOS_INFRAESTRUCTURA.name());
            indicadores.add(ParamIndicador.IND_PROPIEDAD_TOTAL_ESTUDIANTES_PREDIO_PROBLEMA_LEGALIZACION.name());
            indicadores.add(ParamIndicador.IND_PROPIEDAD_TOTAL_PREDIO_PROBLEMA_LEGALIZACION.name());

            List<Object[]> areas = facParam.consultarIndicadores(indicadores, ParamNivelConsul.ESTABLECIMIENTO.getNivel(), idEstablecimiento);
            if (!areas.isEmpty()) {
                int i = 0;
                parametros.put("construcPrimerPiso", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? null : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP) + "");
                i++;
                parametros.put("posibilidadConstrucAltura", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? null : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                i++;
                parametros.put("area_predio", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? null : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                if (areas.get(0)[2] != null && new BigDecimal(areas.get(0)[2] + "").compareTo(BigDecimal.ZERO) != 0) {
                    parametros.put("indice_ocupacion", UtilCadena.isNullOVacio((String) parametros.get("construcPrimerPiso")) || UtilCadena.isNullOVacio((String) areas.get(0)[2]) || new BigDecimal(areas.get(0)[2] + "").compareTo(BigDecimal.ZERO) == 0 ? null : new BigDecimal(parametros.get("construcPrimerPiso") + "").divide(new BigDecimal(areas.get(0)[2] + ""), 4, RoundingMode.HALF_UP).multiply(new BigDecimal("100")).setScale(2, RoundingMode.HALF_UP) + "%");
                } else {
                    parametros.put("indice_ocupacion", null);
                }

                i++;
                if (areas.get(0)[2] != null && new BigDecimal(areas.get(0)[2] + "").compareTo(BigDecimal.ZERO) != 0) {
                    parametros.put("indice_construccion", UtilCadena.isNullOVacio((String) areas.get(0)[i]) || UtilCadena.isNullOVacio((String) areas.get(0)[2]) || UtilCadena.isNullOVacio(parametros.get("area_predio") + "") || UtilCadena.isNullOVacio((String) areas.get(0)[2]) || new BigDecimal(areas.get(0)[2] + "").compareTo(BigDecimal.ZERO) == 0 ? null : new BigDecimal(areas.get(0)[3] + "").divide(new BigDecimal(areas.get(0)[2] + ""), 4, RoundingMode.HALF_UP).multiply(new BigDecimal("100")).setScale(2, RoundingMode.HALF_UP) + "%");
                } else {
                    parametros.put("indice_construccion", null);
                }

                i++;
                parametros.put("m2RequeridosPredio", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? null : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                i++;
                parametros.put("construible_primer_piso", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? null : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                i++;
                parametros.put("construible_total", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? null : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                i++;
                parametros.put("idoneidad", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? null : Utilidades.obtenerMensaje("aplicacion.general." + (new Double(areas.get(0)[i] + "") > new Double(100) ? "numple" : "noCumple")));
                i++;
                parametros.put("accPoblAfec", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]);
                i++;
                if (parametros.get("accPoblAfec") != null && areas.get(0)[i] != null && (new Integer((String) areas.get(0)[i]) != 0)) {
                    parametros.put("accPoblAfecPorcen", ((100 * new Integer((String) parametros.get("accPoblAfec"))) / new Integer((String) areas.get(0)[i])) + "");
                } else {
                    parametros.put("accPoblAfecPorcen", "0");
                }
                i++;
                parametros.put("m2AmpliarCobertura", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? null : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP) + "");
                i++;
                parametros.put("alumnosNuevosInfraestructura", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? null : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP) + "");
                i++;
                parametros.put("totalEstudiantesProblemaLegalizacion", new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalPrediosProblemaLegalizacion", new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;

            }

            List<String[]> indBusqUnidadesFun = new ArrayList<String[]>();
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_PISO.name(), CalificacionCondicion.CUATRO.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_PISO.name(), CalificacionCondicion.TRES.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_PISO.name(), CalificacionCondicion.DOS.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_PISO.name(), CalificacionCondicion.UNO.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_MURO.name(), CalificacionCondicion.CUATRO.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_MURO.name(), CalificacionCondicion.TRES.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_MURO.name(), CalificacionCondicion.DOS.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_MURO.name(), CalificacionCondicion.UNO.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_ACABADO_MURO.name(), CalificacionCondicion.CUATRO.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_ACABADO_MURO.name(), CalificacionCondicion.TRES.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_ACABADO_MURO.name(), CalificacionCondicion.DOS.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_ACABADO_MURO.name(), CalificacionCondicion.UNO.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_TECHO.name(), CalificacionCondicion.CUATRO.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_TECHO.name(), CalificacionCondicion.TRES.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_TECHO.name(), CalificacionCondicion.DOS.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_TECHO.name(), CalificacionCondicion.UNO.getValor() + ""});

            areas = facParam.consultarIndicadoresUnidadesFuncionales(indBusqUnidadesFun, ParamNivelConsul.ESTABLECIMIENTO.getNivel(), idEstablecimiento);
            int i = 0;
            if (!areas.isEmpty()) {
                parametros.put("areaPisosCond4", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? new BigDecimal("0") : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                i++;
                parametros.put("areaPisosCond3", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? new BigDecimal("0") : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                i++;
                parametros.put("areaPisosCond2", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? new BigDecimal("0") : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                i++;
                parametros.put("areaPisosCond1", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? new BigDecimal("0") : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                i++;
                parametros.put("areaMurosCond4", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? new BigDecimal("0") : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                i++;
                parametros.put("areaMurosCond3", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? new BigDecimal("0") : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                i++;
                parametros.put("areaMurosCond2", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? new BigDecimal("0") : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                i++;
                parametros.put("areaMurosCond1", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? new BigDecimal("0") : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                i++;
                parametros.put("areaAcabadosMuroCond4", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? new BigDecimal("0") : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                i++;
                parametros.put("areaAcabadosMuroCond3", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? new BigDecimal("0") : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                i++;
                parametros.put("areaAcabadosMuroCond2", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? new BigDecimal("0") : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                i++;
                parametros.put("areaAcabadosMuroCond1", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? new BigDecimal("0") : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                i++;
                parametros.put("areaCieloRasosCond4", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? new BigDecimal("0") : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                i++;
                parametros.put("areaCieloRasosCond3", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? new BigDecimal("0") : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                i++;
                parametros.put("areaCieloRasosCond2", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? new BigDecimal("0") : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                i++;
                parametros.put("areaCieloRasosCond1", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? new BigDecimal("0") : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                i++;
            }

            parametros.put("accCumplimiento", ((100 * accCumplen) / listaDatosPredios.size()) + "");

            parametros.put("promedioConsumoAgua", consumoAgua.divide(new BigDecimal(listaDatosPredios.size()), 2, RoundingMode.HALF_UP) + "");
            parametros.put("promedioConsumoEnergia", (consumoEnergia.divide(new BigDecimal(listaDatosPredios.size()))).setScale(2, RoundingMode.HALF_UP) + "");

            this.anadirCalificaciones(idEstablecimiento, ParamNivelConsul.ESTABLECIMIENTO.getNivel());

            parametros.put("murosFachadasValor1", murosFachadasValor1 == -1 || (Integer) murosFachadasValor1 == 0 ? "0%" : new BigDecimal(new Double(100 * (Integer) murosFachadasValor1) / new Double((Integer) murosFachadasValor1 + (Integer) murosFachadasValor2 + (Integer) murosFachadasValor3 + (Integer) murosFachadasValor4)).setScale(2, RoundingMode.HALF_UP) + "%");
            parametros.put("murosFachadasValor2", murosFachadasValor2 == -1 || (Integer) murosFachadasValor2 == 0 ? "0%" : new BigDecimal(new Double(100 * (Integer) murosFachadasValor2) / new Double((Integer) murosFachadasValor1 + (Integer) murosFachadasValor2 + (Integer) murosFachadasValor3 + (Integer) murosFachadasValor4)).setScale(2, RoundingMode.HALF_UP) + "%");
            parametros.put("murosFachadasValor3", murosFachadasValor3 == -1 || (Integer) murosFachadasValor3 == 0 ? "0%" : new BigDecimal(new Double(100 * (Integer) murosFachadasValor3) / new Double((Integer) murosFachadasValor1 + (Integer) murosFachadasValor2 + (Integer) murosFachadasValor3 + (Integer) murosFachadasValor4)).setScale(2, RoundingMode.HALF_UP) + "%");
            parametros.put("murosFachadasValor4", murosFachadasValor4 == -1 || (Integer) murosFachadasValor4 == 0 ? "0%" : new BigDecimal(new Double(100 * (Integer) murosFachadasValor4) / new Double((Integer) murosFachadasValor1 + (Integer) murosFachadasValor2 + (Integer) murosFachadasValor3 + (Integer) murosFachadasValor4)).setScale(2, RoundingMode.HALF_UP) + "%");

            parametros.put("acabadosFachadasValor1", acabadosFachadasValor1 == -1 || (Integer) acabadosFachadasValor1 == 0 ? "0%" : new BigDecimal(new Double(100 * (Integer) acabadosFachadasValor1) / new Double((Integer) acabadosFachadasValor1 + (Integer) acabadosFachadasValor2 + (Integer) acabadosFachadasValor3 + (Integer) acabadosFachadasValor4)).setScale(2, RoundingMode.HALF_UP) + "%");
            parametros.put("acabadosFachadasValor2", acabadosFachadasValor2 == -1 || (Integer) acabadosFachadasValor2 == 0 ? "0%" : new BigDecimal(new Double(100 * (Integer) acabadosFachadasValor2) / new Double((Integer) acabadosFachadasValor1 + (Integer) acabadosFachadasValor2 + (Integer) acabadosFachadasValor3 + (Integer) acabadosFachadasValor4)).setScale(2, RoundingMode.HALF_UP) + "%");
            parametros.put("acabadosFachadasValor3", acabadosFachadasValor3 == -1 || (Integer) acabadosFachadasValor3 == 0 ? "0%" : new BigDecimal(new Double(100 * (Integer) acabadosFachadasValor3) / new Double((Integer) acabadosFachadasValor1 + (Integer) acabadosFachadasValor2 + (Integer) acabadosFachadasValor3 + (Integer) acabadosFachadasValor4)).setScale(2, RoundingMode.HALF_UP) + "%");
            parametros.put("acabadosFachadasValor4", acabadosFachadasValor4 == -1 || (Integer) acabadosFachadasValor4 == 0 ? "0%" : new BigDecimal(new Double(100 * (Integer) acabadosFachadasValor4) / new Double((Integer) acabadosFachadasValor1 + (Integer) acabadosFachadasValor2 + (Integer) acabadosFachadasValor3 + (Integer) acabadosFachadasValor4)).setScale(2, RoundingMode.HALF_UP) + "%");

            parametros.put("cubiertasValor1", cubiertasValor1 == -1 || (Integer) cubiertasValor1 == 0 ? "0%" : new BigDecimal(new Double(100 * (Integer) cubiertasValor1) / new Double((Integer) cubiertasValor1 + (Integer) cubiertasValor2 + (Integer) cubiertasValor3 + (Integer) cubiertasValor4)).setScale(2, RoundingMode.HALF_UP) + "%");
            parametros.put("cubiertasValor2", cubiertasValor2 == -1 || (Integer) cubiertasValor2 == 0 ? "0%" : new BigDecimal(new Double(100 * (Integer) cubiertasValor2) / new Double((Integer) cubiertasValor1 + (Integer) cubiertasValor2 + (Integer) cubiertasValor3 + (Integer) cubiertasValor4)).setScale(2, RoundingMode.HALF_UP) + "%");
            parametros.put("cubiertasValor3", cubiertasValor3 == -1 || (Integer) cubiertasValor3 == 0 ? "0%" : new BigDecimal(new Double(100 * (Integer) cubiertasValor3) / new Double((Integer) cubiertasValor1 + (Integer) cubiertasValor2 + (Integer) cubiertasValor3 + (Integer) cubiertasValor4)).setScale(2, RoundingMode.HALF_UP) + "%");
            parametros.put("cubiertasValor4", cubiertasValor4 == -1 || (Integer) cubiertasValor4 == 0 ? "0%" : new BigDecimal(new Double(100 * (Integer) cubiertasValor4) / new Double((Integer) cubiertasValor1 + (Integer) cubiertasValor2 + (Integer) cubiertasValor3 + (Integer) cubiertasValor4)).setScale(2, RoundingMode.HALF_UP) + "%");

            parametros.put("pisosValor1", pisosValor1 == -1 || (Integer) pisosValor1 == 0 ? "0%" : ((pisosValor1 + pisosValor2 + pisosValor3 + pisosValor4) == 0 ? "0%" : new BigDecimal((new Double(100 * pisosValor1)) / new Double(pisosValor1 + pisosValor2 + pisosValor3 + pisosValor4)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("pisosValor2", pisosValor2 == -1 || (Integer) pisosValor2 == 0 ? "0%" : ((pisosValor1 + pisosValor2 + pisosValor3 + pisosValor4) == 0 ? "0%" : new BigDecimal((new Double(100 * pisosValor2)) / new Double(pisosValor1 + pisosValor2 + pisosValor3 + pisosValor4)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("pisosValor3", pisosValor3 == -1 || (Integer) pisosValor3 == 0 ? "0%" : ((pisosValor1 + pisosValor2 + pisosValor3 + pisosValor4) == 0 ? "0%" : new BigDecimal((new Double(100 * pisosValor3)) / new Double(pisosValor1 + pisosValor2 + pisosValor3 + pisosValor4)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("pisosValor4", pisosValor4 == -1 || (Integer) pisosValor4 == 0 ? "0%" : ((pisosValor1 + pisosValor2 + pisosValor3 + pisosValor4) == 0 ? "0%" : new BigDecimal((new Double(100 * pisosValor4)) / new Double(pisosValor1 + pisosValor2 + pisosValor3 + pisosValor4)).setScale(2, RoundingMode.HALF_UP)) + "%");

            parametros.put("murosValor1", murosValor1 == -1 || (Integer) murosValor1 == 0 ? "0%" : ((murosValor1 + murosValor2 + murosValor3 + murosValor4) == 0 ? "0%" : new BigDecimal((new Double(100 * murosValor1)) / new Double(murosValor1 + murosValor2 + murosValor3 + murosValor4)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("murosValor2", murosValor2 == -1 || (Integer) murosValor2 == 0 ? "0%" : ((murosValor1 + murosValor2 + murosValor3 + murosValor4) == 0 ? "0%" : new BigDecimal((new Double(100 * murosValor2)) / new Double(murosValor1 + murosValor2 + murosValor3 + murosValor4)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("murosValor3", murosValor3 == -1 || (Integer) murosValor3 == 0 ? "0%" : ((murosValor1 + murosValor2 + murosValor3 + murosValor4) == 0 ? "0%" : new BigDecimal((new Double(100 * murosValor3)) / new Double(murosValor1 + murosValor2 + murosValor3 + murosValor4)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("murosValor4", murosValor4 == -1 || (Integer) murosValor4 == 0 ? "0%" : ((murosValor1 + murosValor2 + murosValor3 + murosValor4) == 0 ? "0%" : new BigDecimal((new Double(100 * murosValor4)) / new Double(murosValor1 + murosValor2 + murosValor3 + murosValor4)).setScale(2, RoundingMode.HALF_UP)) + "%");

            parametros.put("acabadosMuroValor1", acabadosMuroValor1 == -1 || (Integer) acabadosMuroValor1 == 0 ? "0%" : ((acabadosMuroValor1 + acabadosMuroValor2 + acabadosMuroValor3 + acabadosMuroValor4) == 0 ? "0%" : new BigDecimal(new Double(100 * acabadosMuroValor1) / new Double(acabadosMuroValor1 + acabadosMuroValor2 + acabadosMuroValor3 + acabadosMuroValor4)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("acabadosMuroValor2", acabadosMuroValor2 == -1 || (Integer) acabadosMuroValor2 == 0 ? "0%" : ((acabadosMuroValor1 + acabadosMuroValor2 + acabadosMuroValor3 + acabadosMuroValor4) == 0 ? "0%" : new BigDecimal(new Double(100 * acabadosMuroValor2) / new Double(acabadosMuroValor1 + acabadosMuroValor2 + acabadosMuroValor3 + acabadosMuroValor4)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("acabadosMuroValor3", acabadosMuroValor3 == -1 || (Integer) acabadosMuroValor3 == 0 ? "0%" : ((acabadosMuroValor1 + acabadosMuroValor2 + acabadosMuroValor3 + acabadosMuroValor4) == 0 ? "0%" : new BigDecimal(new Double(100 * acabadosMuroValor3) / new Double(acabadosMuroValor1 + acabadosMuroValor2 + acabadosMuroValor3 + acabadosMuroValor4)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("acabadosMuroValor4", acabadosMuroValor4 == -1 || (Integer) acabadosMuroValor4 == 0 ? "0%" : ((acabadosMuroValor1 + acabadosMuroValor2 + acabadosMuroValor3 + acabadosMuroValor4) == 0 ? "0%" : new BigDecimal(new Double(100 * acabadosMuroValor4) / new Double(acabadosMuroValor1 + acabadosMuroValor2 + acabadosMuroValor3 + acabadosMuroValor4)).setScale(2, RoundingMode.HALF_UP)) + "%");

            parametros.put("ventanasValor1", ventanasValor1 == -1 || (Integer) ventanasValor1 == 0 ? "0%" : ((ventanasValor1 + ventanasValor2 + ventanasValor3 + ventanasValor4) == 0 ? "0%" : new BigDecimal(new Double(100 * ventanasValor1) / new Double(ventanasValor1 + ventanasValor2 + ventanasValor3 + ventanasValor4)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("ventanasValor2", ventanasValor2 == -1 || (Integer) ventanasValor2 == 0 ? "0%" : ((ventanasValor1 + ventanasValor2 + ventanasValor3 + ventanasValor4) == 0 ? "0%" : new BigDecimal(new Double(100 * ventanasValor2) / new Double(ventanasValor1 + ventanasValor2 + ventanasValor3 + ventanasValor4)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("ventanasValor3", ventanasValor3 == -1 || (Integer) ventanasValor3 == 0 ? "0%" : ((ventanasValor1 + ventanasValor2 + ventanasValor3 + ventanasValor4) == 0 ? "0%" : new BigDecimal(new Double(100 * ventanasValor3) / new Double(ventanasValor1 + ventanasValor2 + ventanasValor3 + ventanasValor4)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("ventanasValor4", ventanasValor4 == -1 || (Integer) ventanasValor4 == 0 ? "0%" : ((ventanasValor1 + ventanasValor2 + ventanasValor3 + ventanasValor4) == 0 ? "0%" : new BigDecimal(new Double(100 * ventanasValor4) / new Double(ventanasValor1 + ventanasValor2 + ventanasValor3 + ventanasValor4)).setScale(2, RoundingMode.HALF_UP)) + "%");

            parametros.put("puertasValor1", puertasValor1 == -1 || (Integer) puertasValor1 == 0 ? "0%" : ((puertasValor1 + puertasValor2 + puertasValor3 + puertasValor4) == 0 ? "0%" : new BigDecimal(new Double(100 * puertasValor1) / new Double(puertasValor1 + puertasValor2 + puertasValor3 + puertasValor4)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("puertasValor2", puertasValor2 == -1 || (Integer) puertasValor2 == 0 ? "0%" : ((puertasValor1 + puertasValor2 + puertasValor3 + puertasValor4) == 0 ? "0%" : new BigDecimal(new Double(100 * puertasValor2) / new Double(puertasValor1 + puertasValor2 + puertasValor3 + puertasValor4)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("puertasValor3", puertasValor3 == -1 || (Integer) puertasValor3 == 0 ? "0%" : ((puertasValor1 + puertasValor2 + puertasValor3 + puertasValor4) == 0 ? "0%" : new BigDecimal(new Double(100 * puertasValor3) / new Double(puertasValor1 + puertasValor2 + puertasValor3 + puertasValor4)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("puertasValor4", puertasValor4 == -1 || (Integer) puertasValor4 == 0 ? "0%" : ((puertasValor1 + puertasValor2 + puertasValor3 + puertasValor4) == 0 ? "0%" : new BigDecimal(new Double(100 * puertasValor4) / new Double(puertasValor1 + puertasValor2 + puertasValor3 + puertasValor4)).setScale(2, RoundingMode.HALF_UP)) + "%");

            parametros.put("cielosValor1", cielosValor1 == -1 || (Integer) cielosValor1 == 0 ? "0%" : ((cielosValor1 + cielosValor2 + cielosValor3 + cielosValor4) == 0 ? "0%" : new BigDecimal(new Double(100 * cielosValor1) / new Double(cielosValor1 + cielosValor2 + cielosValor3 + cielosValor4)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("cielosValor2", cielosValor2 == -1 || (Integer) cielosValor2 == 0 ? "0%" : ((cielosValor1 + cielosValor2 + cielosValor3 + cielosValor4) == 0 ? "0%" : new BigDecimal(new Double(100 * cielosValor2) / new Double(cielosValor1 + cielosValor2 + cielosValor3 + cielosValor4)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("cielosValor3", cielosValor3 == -1 || (Integer) cielosValor3 == 0 ? "0%" : ((cielosValor1 + cielosValor2 + cielosValor3 + cielosValor4) == 0 ? "0%" : new BigDecimal(new Double(100 * cielosValor3) / new Double(cielosValor1 + cielosValor2 + cielosValor3 + cielosValor4)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("cielosValor4", cielosValor4 == -1 || (Integer) cielosValor4 == 0 ? "0%" : ((cielosValor1 + cielosValor2 + cielosValor3 + cielosValor4) == 0 ? "0%" : new BigDecimal(new Double(100 * cielosValor4) / new Double(cielosValor1 + cielosValor2 + cielosValor3 + cielosValor4)).setScale(2, RoundingMode.HALF_UP)) + "%");

            parametros.put("iluminacionValor1", (numIluminacion == 0 ? "0%" : new BigDecimal(new Double(100 * iluminacionValor1) / new Double(numIluminacion)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("iluminacionValor2", (numIluminacion == 0 ? "0%" : new BigDecimal(new Double(100 * iluminacionValor2) / new Double(numIluminacion)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("iluminacionValor3", (numIluminacion == 0 ? "0%" : new BigDecimal(new Double(100 * iluminacionValor3) / new Double(numIluminacion)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("iluminacionValor4", (numIluminacion == 0 ? "0%" : new BigDecimal(new Double(100 * iluminacionValor4) / new Double(numIluminacion)).setScale(2, RoundingMode.HALF_UP)) + "%");

            parametros.put("ventilacionValor1", (numVentilacion == 0 ? "0%" : new BigDecimal(new Double(100 * ventilacionValor1) / new Double(numVentilacion)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("ventilacionValor2", (numVentilacion == 0 ? "0%" : new BigDecimal(new Double(100 * ventilacionValor2) / new Double(numVentilacion)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("ventilacionValor3", (numVentilacion == 0 ? "0%" : new BigDecimal(new Double(100 * ventilacionValor3) / new Double(numVentilacion)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("ventilacionValor4", (numVentilacion == 0 ? "0%" : new BigDecimal(new Double(100 * ventilacionValor4) / new Double(numVentilacion)).setScale(2, RoundingMode.HALF_UP)) + "%");

            parametros.put("acusticaValor1", (numAcustica == 0 ? "0%" : new BigDecimal(new Double(100 * acusticaValor1) / new Double(numAcustica)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("acusticaValor2", (numAcustica == 0 ? "0%" : new BigDecimal(new Double(100 * acusticaValor2) / new Double(numAcustica)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("acusticaValor3", (numAcustica == 0 ? "0%" : new BigDecimal(new Double(100 * acusticaValor3) / new Double(numAcustica)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("acusticaValor4", (numAcustica == 0 ? "0%" : new BigDecimal(new Double(100 * acusticaValor4) / new Double(numAcustica)).setScale(2, RoundingMode.HALF_UP)) + "%");

            parametros.put("duchasValor1", (numDuchas == 0 ? "0%" : new BigDecimal(new Double(100 * duchasValor1) / new Double(numDuchas)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("duchasValor2", (numDuchas == 0 ? "0%" : new BigDecimal(new Double(100 * duchasValor2) / new Double(numDuchas)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("duchasValor3", (numDuchas == 0 ? "0%" : new BigDecimal(new Double(100 * duchasValor3) / new Double(numDuchas)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("duchasValor4", (numDuchas == 0 ? "0%" : new BigDecimal(new Double(100 * duchasValor4) / new Double(numDuchas)).setScale(2, RoundingMode.HALF_UP)) + "%");

            parametros.put("lavamanosValor1", (numLavamanos == 0 ? "0%" : new BigDecimal(new Double(100 * lavamanosValor1) / new Double(numLavamanos)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("lavamanosValor2", (numLavamanos == 0 ? "0%" : new BigDecimal(new Double(100 * lavamanosValor2) / new Double(numLavamanos)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("lavamanosValor3", (numLavamanos == 0 ? "0%" : new BigDecimal(new Double(100 * lavamanosValor3) / new Double(numLavamanos)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("lavamanosValor4", (numLavamanos == 0 ? "0%" : new BigDecimal(new Double(100 * lavamanosValor4) / new Double(numLavamanos)).setScale(2, RoundingMode.HALF_UP)) + "%");

            parametros.put("orinalesValor1", (numOrinales == 0 ? "0%" : new BigDecimal(new Double(100 * orinalesValor1) / new Double(numOrinales)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("orinalesValor2", (numOrinales == 0 ? "0%" : new BigDecimal(new Double(100 * orinalesValor2) / new Double(numOrinales)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("orinalesValor3", (numOrinales == 0 ? "0%" : new BigDecimal(new Double(100 * orinalesValor3) / new Double(numOrinales)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("orinalesValor4", (numOrinales == 0 ? "0%" : new BigDecimal(new Double(100 * orinalesValor4) / new Double(numOrinales)).setScale(2, RoundingMode.HALF_UP)) + "%");

            parametros.put("inodorosValor1", (numInodoros == 0 ? "0%" : new BigDecimal(new Double(100 * inodorosValor1) / new Double(numInodoros)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("inodorosValor2", (numInodoros == 0 ? "0%" : new BigDecimal(new Double(100 * inodorosValor2) / new Double(numInodoros)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("inodorosValor3", (numInodoros == 0 ? "0%" : new BigDecimal(new Double(100 * inodorosValor3) / new Double(numInodoros)).setScale(2, RoundingMode.HALF_UP)) + "%");
            parametros.put("inodorosValor4", (numInodoros == 0 ? "0%" : new BigDecimal(new Double(100 * inodorosValor4) / new Double(numInodoros)).setScale(2, RoundingMode.HALF_UP)) + "%");

            parametros.put("aislamientoTermicoPisos", aislamientoTermicoPisos);
            parametros.put("aislamientoTermicoMuros", aislamientoTermicoMuros);
            parametros.put("aislamientoTermicoVentanas", aislamientoTermicoVentanas);
            parametros.put("aislamientoTermicoCielos", aislamientoTermicoCielos);
            parametros.put("aislamientoTermicoOtros", aislamientoTermicoOtros);

            parametros.put("numAcustica", numAcustica);
            parametros.put("numDuchas", numDuchas);
            parametros.put("numIluminacion", numIluminacion);
            parametros.put("numInodoros", numInodoros);
            parametros.put("numLavamanos", numLavamanos);
            parametros.put("numOrinales", numOrinales);
            parametros.put("numVentilacion", numVentilacion);

            HashMap<String, Object> datosUnidadesFuncionales = this.obtenerUnidadesFuncionales(idEstablecimiento, ParamNivelConsul.ESTABLECIMIENTO.getNivel());
            //Añadimos todos los datos de las unidaddes funcionales a los parámetros
            String[] noStrings = {"propAreasCentrales", "propAreasApoyo", "propAreasAulas", "propAreasLaboratorios", "propAreasCentroRecursosAprendizaje", "propAreasTalleres", "propAreasExtension", "propAreasServSanitarios", "propAreasAbastProcesa", "propAreasConducAdministra"};
            List<String> noStr = new ArrayList<String>();
            Collections.addAll(noStr, noStrings);

            for (String key : datosUnidadesFuncionales.keySet()) {
                if (noStr.contains(key)) {
                    parametros.put(key, datosUnidadesFuncionales.get(key));
                } else {
                    parametros.put(key, datosUnidadesFuncionales.get(key) + "");
                }
            }

            DateFormat df = DateFormat.getDateInstance(DateFormat.LONG);

            parametros.put("fechaReporte", df.format(new Date()));
            parametros.put("municipio", establecimiento.getNivel().getDescripcion());
            parametros.put("departamento", establecimiento.getNivel().getPadre().getDescripcion());
            parametros.put("region", establecimiento.getNivel().getPadre().getPadre().getDescripcion());

            String periodoCorte = facParam.consultarPeriodoIndicadores();
            if (periodoCorte != null) {
                Calendar calAux = Calendar.getInstance();
                calAux.set(new Integer(periodoCorte.substring(0, 4)), new Integer(periodoCorte.substring(4, 6)), 1);
                SimpleDateFormat format = new SimpleDateFormat("MMMM yyyy");
                String fecha = format.format(calAux.getTime());
                parametros.put("fechaCorte", fecha.substring(0, 1).toUpperCase() + fecha.substring(1, fecha.length()));
            }

            HashMap datosRedistribucion = this.obtenerRedistribucion(establecimiento, ParamNivelConsul.ESTABLECIMIENTO.getNivel(), this.getIdNivel0(establecimiento.getNivel()).getId());
            if (datosRedistribucion.isEmpty()) {
                parametros.put("utilizacion", new BigDecimal(0));
                parametros.put("cuposTotal", new BigDecimal(0));
            } else {
                parametros.put("utilizacion", datosRedistribucion.get("utilizacion"));
                parametros.put("cuposTotal", datosRedistribucion.get("cuposTotal"));
            }

            ReporteInformeGeneral reporteInformeGeneralSede = new ReporteInformeGeneral();
            reporteInformeGeneralSede.setListaDatosPredios(listaDatosPredios);

            List list = new ArrayList();
            list.add(reporteInformeGeneralSede);

            parametros.put(JRParameter.REPORT_RESOURCE_BUNDLE, ResourceBundle.getBundle("etiquetas", FacesContext.getCurrentInstance().getViewRoot().getLocale()));

            stream = UtilExportar.getInformeGeneral("informeGeneral/establecimiento", "InformeGeneralEstablecimiento", parametros, list);

        } catch (Exception e) {
            UtilOut.println("ERROR en imprimirPdfEstablecimiento: " + e.getMessage());
            mostrarMensajeError(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"), e.getMessage());
            //UtilOut.println(e);
            ManejadorErrores.ingresarExcepcionEnLog(e, InformeGeneralBean.class);
        }

        return new DefaultStreamedContent(stream, "application/x-download", "InformeGeneralEstablecimiento.pdf");
    }

    private StreamedContent imprimirPdfDPADetallado(Long nivelConsulta, Long id, String cod) {
        parametros = new HashMap<String, Object>();
        InputStream stream = null;
        try {

            List<DatosDPA> listaDatosDPAs = new ArrayList<DatosDPA>();

            List<String> preCodes = new ArrayList<String>();
            List<String> priCodes = new ArrayList<String>();
            List<String> secCodes = new ArrayList<String>();
            List<String> basCodes = new ArrayList<String>();
            List<String> medCodes = new ArrayList<String>();
            List<String[]> nivelesEnsenanza = facInformeGeneral.getEquivalentes("nivel_educativo");
            for (Iterator<String[]> it = nivelesEnsenanza.iterator(); it.hasNext();) {
                Object[] n = it.next();
                if (n[0].equals("NE_PRE")) {
                    preCodes.add((String) n[1]);
                } else if (n[0].equals("NE_PRI")) {
                    priCodes.add((String) n[1]);
                } else if (n[0].equals("NE_SEC")) {
                    secCodes.add((String) n[1]);
                } else if (n[0].equals("NE_BAS")) {
                    basCodes.add((String) n[1]);
                } else if (n[0].equals("NE_MED")) {
                    medCodes.add((String) n[1]);
                }
            }

            BigDecimal areasCentrales = new BigDecimal("0");
            BigDecimal areasApoyo = new BigDecimal("0");
            BigDecimal areaTotal = new BigDecimal("0");
            BigDecimal areaAulas = new BigDecimal("0");
            BigDecimal areaLaboratorios = new BigDecimal("0");
            BigDecimal areaCentroRecursosAprendizaje = new BigDecimal("0");
            BigDecimal areaTalleres = new BigDecimal("0");
            BigDecimal areaExtension = new BigDecimal("0");
            BigDecimal areaServSanitariosTotal = new BigDecimal("0");
            BigDecimal areaAbastProcesa = new BigDecimal("0");
            BigDecimal areaConducAdministra = new BigDecimal("0");

            BigDecimal superficieTotal = new BigDecimal("0");
            BigDecimal porcenConstruccionTotal = new BigDecimal("0");
            BigDecimal areaConstrucPrimerPisoTotal = new BigDecimal("0");
            BigDecimal areaConstrucAlturaTotal = new BigDecimal("0");
            BigDecimal accesibilidadPoblacionAfectadaTotal = new BigDecimal("0");
            BigDecimal numPrediosAccesibilidadInadecuadaTotal = new BigDecimal("0");
            BigDecimal numPrediosTotal = new BigDecimal("0");
            BigDecimal numPrediosConRiesgosTotal = new BigDecimal("0");
            BigDecimal poblacionAfectadaRiesgosTotal = new BigDecimal("0");
            BigDecimal prediosProblemasLegalizacionTotal = new BigDecimal("0");
            BigDecimal estudiantesProblemaLegalizacionTotal = new BigDecimal("0");
            BigDecimal numPrediosConAguaTotal = new BigDecimal("0");
            BigDecimal numPrediosConEETotal = new BigDecimal("0");
            BigDecimal numPrediosConGasTotal = new BigDecimal("0");
            BigDecimal numPrediosConReciclajeTotal = new BigDecimal("0");
            BigDecimal numPrediosConAlcantarilladoTotal = new BigDecimal("0");
            BigDecimal numPrediosConPluvialTotal = new BigDecimal("0");
            BigDecimal numPrediosConInternetTotal = new BigDecimal("0");
            BigDecimal numPrediosConTelefonoTotal = new BigDecimal("0");
            BigDecimal promedioConsumoAguaTotal = new BigDecimal("0");
            BigDecimal numPrediosCumplenConsumoAguaTotal = new BigDecimal("0");
            BigDecimal promedioConsumoEETotal = new BigDecimal("0");
            BigDecimal numPrediosCumplenConsumoEETotal = new BigDecimal("0");
            BigDecimal numPrediosVulnerablesTotal = new BigDecimal("0");
            BigDecimal numPrediosEstrucCriticaTotal = new BigDecimal("0");
            BigDecimal m2PrediosEstadoEstrucCriticoTotal = new BigDecimal("0");
            BigDecimal numPrediosRutaEvacCriticaTotal = new BigDecimal("0");
            BigDecimal numPrediosSenalizaEvacCriticaTotal = new BigDecimal("0");
            BigDecimal numPrediosNoRactivoIncendioTotal = new BigDecimal("0");
            BigDecimal m2RequeridosTotal = new BigDecimal("0");
            BigDecimal construiblePrimerPisoTotal = new BigDecimal("0");
            BigDecimal construibleAlturaTotal = new BigDecimal("0");
            BigDecimal construibleTotalTotal = new BigDecimal("0");
            BigDecimal m2AmpliarCoberturaTotal = new BigDecimal("0");
            BigDecimal alumnosNuevosInfraestructuraTotal = new BigDecimal("0");
            BigDecimal cuposTotalTotal = new BigDecimal("0");
            int totalCobertura = 0;

            Nivel nivel = facEstDPA.consultarNivel(id);
            Nivel nivel0 = this.getIdNivel0(nivel);

            if (nivel.getNivelesList().isEmpty() && !nivel.getEstablecimientoList().isEmpty()) {//si es municipio
                nivel.getNivelesList().add(nivel);
            }
            for (Nivel n : nivel.getNivelesList()) {
                DatosDPA dpa = new DatosDPA();
                dpa.setCodigoDPA(n.getCodigo());
                dpa.setNombreDPA(n.getDescripcion());

                int poblacionTotal = 0;
                int poblacionAfectadaRiesgos = 0;
                int numPrediosConRiesgos = 0;

                BigDecimal m2PrediosEstadoEstrucCritico = new BigDecimal("0");

                int numSedes = 0;
                int numEstablecimientos = 0;
                int numPredios = 0;
                int pre = 0, primaria = 0, secundaria = 0, basica = 0, media = 0;

                int murosFachadasValor1 = 0, murosFachadasValor2 = 0, murosFachadasValor3 = 0, murosFachadasValor4 = 0;
                int acabadosFachadasValor1 = 0, acabadosFachadasValor2 = 0, acabadosFachadasValor3 = 0, acabadosFachadasValor4 = 0;
                int cubiertasValor1 = 0, cubiertasValor2 = 0, cubiertasValor3 = 0, cubiertasValor4 = 0;
                int numIluminacion = 0, iluminacionValor1 = 0, iluminacionValor2 = 0, iluminacionValor3 = 0, iluminacionValor4 = 0;
                int numVentilacion = 0, ventilacionValor1 = 0, ventilacionValor2 = 0, ventilacionValor3 = 0, ventilacionValor4 = 0;
                int numAcustica = 0, acusticaValor1 = 0, acusticaValor2 = 0, acusticaValor3 = 0, acusticaValor4 = 0;
                int numDuchas = 0, duchasValor1 = 0, duchasValor2 = 0, duchasValor3 = 0, duchasValor4 = 0;
                int numLavamanos = 0, lavamanosValor1 = 0, lavamanosValor2 = 0, lavamanosValor3 = 0, lavamanosValor4 = 0;
                int numOrinales = 0, orinalesValor1 = 0, orinalesValor2 = 0, orinalesValor3 = 0, orinalesValor4 = 0;
                int numInodoros = 0, inodorosValor1 = 0, inodorosValor2 = 0, inodorosValor3 = 0, inodorosValor4 = 0;
                int aislamientoTermicoPisos = 0;
                int aislamientoTermicoMuros = 0;
                int aislamientoTermicoVentanas = 0;
                int aislamientoTermicoCielos = 0;
                int aislamientoTermicoOtros = 0;

                List<Nivel> municipios = new ArrayList();
                if (n.getEstablecimientoList().isEmpty()) {
                    for (Nivel n1 : n.getNivelesList()) {
                        if (n1.getEstablecimientoList().isEmpty()) {
                            for (Nivel n2 : n1.getNivelesList()) {
                                if (n2.getEstablecimientoList().isEmpty()) {
                                    for (Nivel n3 : n2.getNivelesList()) {
                                        if (n3.getEstablecimientoList().isEmpty()) {
                                            for (Nivel n4 : n3.getNivelesList()) {
                                                if (n4.getEstablecimientoList().isEmpty()) {
                                                    for (Nivel n5 : n4.getNivelesList()) {
                                                        municipios.add(n5);
                                                        break;
                                                    }
                                                } else {
                                                    municipios.add(n4);
                                                    break;
                                                }
                                            }
                                        } else {
                                            municipios.add(n3);
                                            break;
                                        }
                                    }
                                } else {
                                    municipios.add(n2);
                                    break;
                                }
                            }
                        } else {
                            municipios.add(n1);
                            break;
                        }
                    }
                } else {
                    municipios.add(n);
                }

                for (Nivel muni : municipios) {
                    numEstablecimientos += muni.getEstablecimientoList().size();
                    for (Establecimiento est : muni.getEstablecimientoList()) {
                        numSedes += est.getSedes().size();
                        for (Sede s : est.getSedes()) {
                            numPredios += s.getPredios().size();
                        }
                        List<BigDecimal> listaInstrumentosDigitados = facInstrumentos.obtenerInstrumentosDigPorEstablecimiento(est.getId());
                        for (BigDecimal idInstrumentoDig : listaInstrumentosDigitados) {
                            InstrumentoDig instrumentoDig = facInstrumentos.consultarInstrumentoPorCodigo(idInstrumentoDig.longValue());
                            Map<String, Object> datos = null;
                            datos = obtenerRespuestas(instrumentoDig);
                            //INICIO COBERTURAS
                            String[] respuestasNivelEnsenanza = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10"};
                            TipologiaValorNombre tv;
                            for (String resp : respuestasNivelEnsenanza) {
                                if (preCodes.contains(datos.get("RESP_033_" + resp))) {
                                    if (datos.containsKey("RESP_035_" + resp) && datos.get("RESP_035_" + resp) != null) {
                                        pre += (new Integer(datos.get("RESP_035_" + resp) + ""));
                                    }
                                } else if (datos.get("RESP_035_" + resp) != null && priCodes.contains(datos.get("RESP_033_" + resp))) {
                                    primaria += (new Integer(datos.get("RESP_035_" + resp) + ""));
                                } else if (datos.get("RESP_035_" + resp) != null && secCodes.contains(datos.get("RESP_033_" + resp))) {
                                    secundaria += (new Integer(datos.get("RESP_035_" + resp) + ""));
                                } else if (datos.get("RESP_035_" + resp) != null && basCodes.contains(datos.get("RESP_033_" + resp))) {
                                    basica += (new Integer(datos.get("RESP_035_" + resp) + ""));
                                } else if (datos.get("RESP_035_" + resp) != null && medCodes.contains(datos.get("RESP_033_" + resp))) {
                                    media += (new Integer(datos.get("RESP_035_" + resp) + ""));
                                }

                                if (preCodes.contains(datos.get("RESP_199_" + resp))) {
                                    if (datos.containsKey("RESP_201_" + resp) && datos.get("RESP_201_" + resp) != null) {
                                        pre += (new Integer(datos.get("RESP_201_" + resp) + ""));
                                    }
                                } else if (datos.get("RESP_201_" + resp) != null && priCodes.contains(datos.get("RESP_199_" + resp))) {
                                    primaria += (new Integer(datos.get("RESP_201_" + resp) + ""));
                                } else if (datos.get("RESP_201_" + resp) != null && secCodes.contains(datos.get("RESP_199_" + resp))) {
                                    secundaria += (new Integer(datos.get("RESP_201_" + resp) + ""));
                                } else if (datos.get("RESP_201_" + resp) != null && basCodes.contains(datos.get("RESP_199_" + resp))) {
                                    basica += (new Integer(datos.get("RESP_201_" + resp) + ""));
                                } else if (datos.get("RESP_201_" + resp) != null && medCodes.contains(datos.get("RESP_199_" + resp))) {
                                    media += (new Integer(datos.get("RESP_201_" + resp) + ""));
                                }
                                //Se les da el valor de la tipología para que se muestre el texto en el reporte
                                if (datos.get("RESP_033_" + resp) != null) {
                                    tv = facParam.consultarValorTipologia(respuestaTipologia.get("RESP_033_" + resp), datos.get("RESP_033_" + resp).toString(), getUsuarioSesion().getUsuario().getIdioma().getId());
                                    datos.put(resp, tv != null ? tv.getNombre() : "");
                                } else if (datos.get("RESP_199_" + resp) != null) {
                                    tv = facParam.consultarValorTipologia(respuestaTipologia.get("RESP_199_" + resp), datos.get("RESP_199_" + resp).toString(), getUsuarioSesion().getUsuario().getIdioma().getId());
                                    datos.put(resp, tv != null ? tv.getNombre() : "");
                                }
                            }
                            //FIN COBERTURAS

                            //INICIO RIESGOS
                            //INICIO SEGURIDAD ESTRUCCTURAL Y ACC INTERNA
                            List<String> indicadores = new ArrayList<String>();
                            indicadores.add(ParamIndicador.IND_TOTAL_POBLACION.name());
                            indicadores.add(ParamIndicador.IND_SEGURIDAD_ESTADO_ESTRUCTURA.name());
                            indicadores.add(ParamIndicador.IND_AREA_REAL_PREDIO.name());
                            List<Object[]> ind = facParam.consultarIndicadores(indicadores, ParamNivelConsul.PREDIO.getNivel(), idInstrumentoDig.longValue());
                            poblacionTotal += ind.get(0)[0] == null ? 0 : new Integer((String) ind.get(0)[0]);
                            if (datos.containsKey("RESP_048_01") || datos.containsKey("RESP_048_03") || datos.containsKey("RESP_048_05")
                                    || datos.containsKey("RESP_050_01") || datos.containsKey("RESP_050_03") || datos.containsKey("RESP_050_05")) {
                                poblacionAfectadaRiesgos += ind.get(0)[0] == null ? 0 : new Integer((String) ind.get(0)[0]);
                                numPrediosConRiesgos++;
                                if ("CRÍTICO".equals((String) ind.get(0)[1])) {
                                    m2PrediosEstadoEstrucCritico = m2PrediosEstadoEstrucCritico.add(new BigDecimal((String) ind.get(0)[2]));
                                }
                            }
                            //FIN SEGURIDAD ESTRUCCTURAL Y ACC INTERNA
                            //FIN RIESGOS

                            //INICIO EDIFICIOS Y ESPACIOS
                            List edif = obtenerObjetosEdificios(idInstrumentoDig.longValue());
                            //List<ControlVigilancia> listaControlVigilancia=(List)edif.get(0);
                            //List<SeguridadAccesibilidad> listaSeguridadAccesibilidad=(List)edif.get(1);
                            HashMap datosMaterial = (HashMap) edif.get(2);

                            murosFachadasValor1 += (Integer) datosMaterial.get("murosFachadasValor1");
                            murosFachadasValor2 += (Integer) datosMaterial.get("murosFachadasValor2");
                            murosFachadasValor3 += (Integer) datosMaterial.get("murosFachadasValor3");
                            murosFachadasValor4 += (Integer) datosMaterial.get("murosFachadasValor4");

                            acabadosFachadasValor1 += (Integer) datosMaterial.get("acabadosFachadasValor1");
                            acabadosFachadasValor2 += (Integer) datosMaterial.get("acabadosFachadasValor2");
                            acabadosFachadasValor3 += (Integer) datosMaterial.get("acabadosFachadasValor3");
                            acabadosFachadasValor4 += (Integer) datosMaterial.get("acabadosFachadasValor4");

                            cubiertasValor1 += (Integer) datosMaterial.get("cubiertasValor1");
                            cubiertasValor2 += (Integer) datosMaterial.get("cubiertasValor2");
                            cubiertasValor3 += (Integer) datosMaterial.get("cubiertasValor3");
                            cubiertasValor4 += (Integer) datosMaterial.get("cubiertasValor4");

                            HashMap datosEsp = obtenerObjetosEspaciosEscolaresPredios(idInstrumentoDig.longValue());

                            iluminacionValor1 += (Integer) datosEsp.get("iluminacionValor1");
                            iluminacionValor2 += (Integer) datosEsp.get("iluminacionValor2");
                            iluminacionValor3 += (Integer) datosEsp.get("iluminacionValor3");
                            iluminacionValor4 += (Integer) datosEsp.get("iluminacionValor4");
                            numIluminacion += (Integer) datosEsp.get("numIluminacion");
                            ventilacionValor1 += (Integer) datosEsp.get("ventilacionValor1");
                            ventilacionValor2 += (Integer) datosEsp.get("ventilacionValor2");
                            ventilacionValor3 += (Integer) datosEsp.get("ventilacionValor3");
                            ventilacionValor4 += (Integer) datosEsp.get("ventilacionValor4");
                            numVentilacion += (Integer) datosEsp.get("numVentilacion");
                            acusticaValor1 += (Integer) datosEsp.get("acusticaValor1");
                            acusticaValor2 += (Integer) datosEsp.get("acusticaValor2");
                            acusticaValor3 += (Integer) datosEsp.get("acusticaValor3");
                            acusticaValor4 += (Integer) datosEsp.get("acusticaValor4");
                            numAcustica += (Integer) datosEsp.get("numAcustica");
                            numDuchas += (Integer) datosEsp.get("numDuchas");
                            duchasValor1 += (Integer) datosEsp.get("duchasValor1");
                            duchasValor2 += (Integer) datosEsp.get("duchasValor2");
                            duchasValor3 += (Integer) datosEsp.get("duchasValor3");
                            duchasValor4 += (Integer) datosEsp.get("duchasValor4");
                            numLavamanos += (Integer) datosEsp.get("numLavamanos");
                            lavamanosValor1 += (Integer) datosEsp.get("lavamanosValor1");
                            lavamanosValor2 += (Integer) datosEsp.get("lavamanosValor2");
                            lavamanosValor3 += (Integer) datosEsp.get("lavamanosValor3");
                            lavamanosValor4 += (Integer) datosEsp.get("lavamanosValor4");
                            numOrinales += (Integer) datosEsp.get("numOrinales");
                            orinalesValor1 += (Integer) datosEsp.get("orinalesValor1");
                            orinalesValor2 += (Integer) datosEsp.get("orinalesValor2");
                            orinalesValor3 += (Integer) datosEsp.get("orinalesValor3");
                            orinalesValor4 += (Integer) datosEsp.get("orinalesValor4");
                            numInodoros += (Integer) datosEsp.get("numInodoros");
                            inodorosValor1 += (Integer) datosEsp.get("inodorosValor1");
                            inodorosValor2 += (Integer) datosEsp.get("inodorosValor2");
                            inodorosValor3 += (Integer) datosEsp.get("inodorosValor3");
                            inodorosValor4 += (Integer) datosEsp.get("inodorosValor4");
                            aislamientoTermicoPisos += (Integer) datosEsp.get("aislamientoTermicoPisos");
                            aislamientoTermicoMuros += (Integer) datosEsp.get("aislamientoTermicoMuros");
                            aislamientoTermicoVentanas += (Integer) datosEsp.get("aislamientoTermicoVentanas");
                            aislamientoTermicoCielos += (Integer) datosEsp.get("aislamientoTermicoCielos");
                            aislamientoTermicoOtros += (Integer) datosEsp.get("aislamientoTermicoOtros");
                            //FIN EDIFICIOS Y ESPACIOS

                        }//FIN FOR listaInstrumentosDigitados

                    }//FIN FOR hijo.getEstablecimientoList()

                }//FIN FOR municipios

                dpa.setNumPrediosConRiesgos(numPrediosConRiesgos + "");
                numPrediosConRiesgosTotal = numPrediosConRiesgosTotal.add(new BigDecimal(numPrediosConRiesgos));
                dpa.setPoblacionAfectadaRiesgos(poblacionAfectadaRiesgos + "");
                poblacionAfectadaRiesgosTotal = poblacionAfectadaRiesgosTotal.add(new BigDecimal(poblacionAfectadaRiesgos));
                if (poblacionTotal > 0) {
                    dpa.setPoblacionTotal(poblacionTotal + "");
                }
                dpa.setM2PrediosEstadoEstrucCritico(m2PrediosEstadoEstrucCritico.setScale(2, RoundingMode.HALF_UP) + "");
                m2PrediosEstadoEstrucCriticoTotal = m2PrediosEstadoEstrucCriticoTotal.add(m2PrediosEstadoEstrucCritico.setScale(2, RoundingMode.HALF_UP));
                dpa.setMurosFachadasValor1(murosFachadasValor1 + "");
                dpa.setMurosFachadasValor2(murosFachadasValor2 + "");
                dpa.setMurosFachadasValor3(murosFachadasValor3 + "");
                dpa.setMurosFachadasValor4(murosFachadasValor4 + "");

                dpa.setAcabadosFachadasValor1(acabadosFachadasValor1 + "");
                dpa.setAcabadosFachadasValor2(acabadosFachadasValor2 + "");
                dpa.setAcabadosFachadasValor3(acabadosFachadasValor3 + "");
                dpa.setAcabadosFachadasValor4(acabadosFachadasValor4 + "");

                dpa.setCubiertasValor1(cubiertasValor1 + "");
                dpa.setCubiertasValor2(cubiertasValor2 + "");
                dpa.setCubiertasValor3(cubiertasValor3 + "");
                dpa.setCubiertasValor4(cubiertasValor4 + "");

                dpa.setIluminacionValor1((numIluminacion == 0 ? 0 : new BigDecimal(new Double(100 * iluminacionValor1) / new Double(numIluminacion)).setScale(2, RoundingMode.HALF_UP)) + "%");
                dpa.setIluminacionValor2((numIluminacion == 0 ? 0 : new BigDecimal(new Double(100 * iluminacionValor2) / new Double(numIluminacion)).setScale(2, RoundingMode.HALF_UP)) + "%");
                dpa.setIluminacionValor3((numIluminacion == 0 ? 0 : new BigDecimal(new Double(100 * iluminacionValor3) / new Double(numIluminacion)).setScale(2, RoundingMode.HALF_UP)) + "%");
                dpa.setIluminacionValor4((numIluminacion == 0 ? 0 : new BigDecimal(new Double(100 * iluminacionValor4) / new Double(numIluminacion)).setScale(2, RoundingMode.HALF_UP)) + "%");

                dpa.setVentilacionValor1((numVentilacion == 0 ? 0 : new BigDecimal(new Double(100 * ventilacionValor1) / new Double(numVentilacion)).setScale(2, RoundingMode.HALF_UP)) + "%");
                dpa.setVentilacionValor2((numVentilacion == 0 ? 0 : new BigDecimal(new Double(100 * ventilacionValor2) / new Double(numVentilacion)).setScale(2, RoundingMode.HALF_UP)) + "%");
                dpa.setVentilacionValor3((numVentilacion == 0 ? 0 : new BigDecimal(new Double(100 * ventilacionValor3) / new Double(numVentilacion)).setScale(2, RoundingMode.HALF_UP)) + "%");
                dpa.setVentilacionValor4((numVentilacion == 0 ? 0 : new BigDecimal(new Double(100 * ventilacionValor4) / new Double(numVentilacion)).setScale(2, RoundingMode.HALF_UP)) + "%");

                dpa.setAcusticaValor1((numAcustica == 0 ? 0 : new BigDecimal(new Double(100 * acusticaValor1) / new Double(numAcustica)).setScale(2, RoundingMode.HALF_UP)) + "%");
                dpa.setAcusticaValor2((numAcustica == 0 ? 0 : new BigDecimal(new Double(100 * acusticaValor2) / new Double(numAcustica)).setScale(2, RoundingMode.HALF_UP)) + "%");
                dpa.setAcusticaValor3((numAcustica == 0 ? 0 : new BigDecimal(new Double(100 * acusticaValor3) / new Double(numAcustica)).setScale(2, RoundingMode.HALF_UP)) + "%");
                dpa.setAcusticaValor4((numAcustica == 0 ? 0 : new BigDecimal(new Double(100 * acusticaValor4) / new Double(numAcustica)).setScale(2, RoundingMode.HALF_UP)) + "%");

                dpa.setDuchasValor1((numDuchas == 0 ? 0 : new BigDecimal(new Double(100 * duchasValor1) / new Double(numDuchas)).setScale(2, RoundingMode.HALF_UP)) + "%");
                dpa.setDuchasValor2((numDuchas == 0 ? 0 : new BigDecimal(new Double(100 * duchasValor2) / new Double(numDuchas)).setScale(2, RoundingMode.HALF_UP)) + "%");
                dpa.setDuchasValor3((numDuchas == 0 ? 0 : new BigDecimal(new Double(100 * duchasValor3) / new Double(numDuchas)).setScale(2, RoundingMode.HALF_UP)) + "%");
                dpa.setDuchasValor4((numDuchas == 0 ? 0 : new BigDecimal(new Double(100 * duchasValor4) / new Double(numDuchas)).setScale(2, RoundingMode.HALF_UP)) + "%");

                dpa.setLavamanosValor1((numLavamanos == 0 ? 0 : new BigDecimal(new Double(100 * lavamanosValor1) / new Double(numLavamanos)).setScale(2, RoundingMode.HALF_UP)) + "%");
                dpa.setLavamanosValor2((numLavamanos == 0 ? 0 : new BigDecimal(new Double(100 * lavamanosValor2) / new Double(numLavamanos)).setScale(2, RoundingMode.HALF_UP)) + "%");
                dpa.setLavamanosValor3((numLavamanos == 0 ? 0 : new BigDecimal(new Double(100 * lavamanosValor3) / new Double(numLavamanos)).setScale(2, RoundingMode.HALF_UP)) + "%");
                dpa.setLavamanosValor4((numLavamanos == 0 ? 0 : new BigDecimal(new Double(100 * lavamanosValor4) / new Double(numLavamanos)).setScale(2, RoundingMode.HALF_UP)) + "%");

                dpa.setOrinalesValor1((numOrinales == 0 ? 0 : new BigDecimal(new Double(100 * orinalesValor1) / new Double(numOrinales)).setScale(2, RoundingMode.HALF_UP)) + "%");
                dpa.setOrinalesValor2((numOrinales == 0 ? 0 : new BigDecimal(new Double(100 * orinalesValor2) / new Double(numOrinales)).setScale(2, RoundingMode.HALF_UP)) + "%");
                dpa.setOrinalesValor3((numOrinales == 0 ? 0 : new BigDecimal(new Double(100 * orinalesValor3) / new Double(numOrinales)).setScale(2, RoundingMode.HALF_UP)) + "%");
                dpa.setOrinalesValor4((numOrinales == 0 ? 0 : new BigDecimal(new Double(100 * orinalesValor4) / new Double(numOrinales)).setScale(2, RoundingMode.HALF_UP)) + "%");

                dpa.setInodorosValor1((numInodoros == 0 ? 0 : new BigDecimal(new Double(100 * inodorosValor1) / new Double(numInodoros)).setScale(2, RoundingMode.HALF_UP)) + "%");
                dpa.setInodorosValor2((numInodoros == 0 ? 0 : new BigDecimal(new Double(100 * inodorosValor2) / new Double(numInodoros)).setScale(2, RoundingMode.HALF_UP)) + "%");
                dpa.setInodorosValor3((numInodoros == 0 ? 0 : new BigDecimal(new Double(100 * inodorosValor3) / new Double(numInodoros)).setScale(2, RoundingMode.HALF_UP)) + "%");
                dpa.setInodorosValor4((numInodoros == 0 ? 0 : new BigDecimal(new Double(100 * inodorosValor4) / new Double(numInodoros)).setScale(2, RoundingMode.HALF_UP)) + "%");

                dpa.setAislamientoTermicoPisos(aislamientoTermicoPisos + "");
                dpa.setAislamientoTermicoMuros(aislamientoTermicoMuros + "");
                dpa.setAislamientoTermicoVentanas(aislamientoTermicoVentanas + "");
                dpa.setAislamientoTermicoCielos(aislamientoTermicoCielos + "");
                dpa.setAislamientoTermicoOtros(aislamientoTermicoOtros + "");

                dpa.setNumIluminacion(numIluminacion + "");
                dpa.setNumVentilacion(numVentilacion + "");
                dpa.setNumAcustica(numAcustica + "");
                dpa.setNumDuchas(numDuchas + "");
                dpa.setNumLavamanos(numLavamanos + "");
                dpa.setNumOrinales(numOrinales + "");
                dpa.setNumInodoros(numInodoros + "");

                dpa.setNumEstablecimientos(numEstablecimientos + "");
                dpa.setNumSedes(numSedes + "");
                if (numPredios > 0) {
                    dpa.setNumPredios(numPredios + "");
                }
                numPrediosTotal = numPrediosTotal.add(new BigDecimal(numPredios));
                dpa.setPre(pre + "");
                dpa.setPrimaria(primaria + "");
                dpa.setSecundaria(secundaria + "");
                dpa.setBasica(basica + "");
                dpa.setMedia(media + "");
                dpa.setTotal((pre + primaria + secundaria + basica + media) + "");
                totalCobertura += (pre + primaria + secundaria + basica + media);

                List<String> indicadores = new ArrayList<String>();
                indicadores.add(ParamIndicador.IND_AREA_REAL_PREDIO.name());
                indicadores.add(ParamIndicador.IND_TOTAL_AREA_CONSTRUCCION_PREDIO.name());
                indicadores.add(ParamIndicador.IND_AMPLIACION_AREA_CONSTRUCCION_PRIMER_PISO.name());
                indicadores.add(ParamIndicador.IND_ACCESIBILIDAD_NUMERO_PREDIOS_CONDICION_INADECUADA.name());
                indicadores.add(ParamIndicador.IND_ACCESIBILIDAD_POBLACION_AFECTADA.name());
                indicadores.add(ParamIndicador.IND_PROPIEDAD_TOTAL_PREDIO_PROBLEMA_LEGALIZACION.name());
                indicadores.add(ParamIndicador.IND_PROPIEDAD_TOTAL_ESTUDIANTES_PREDIO_PROBLEMA_LEGALIZACION.name());
                indicadores.add(ParamIndicador.IND_SERVICIO_ANALISIS_CONSUMO_PROMEDIO_AGUA.name());
                indicadores.add(ParamIndicador.IND_SERVICIO_NUM_PREDIOS_ANALISIS_CONSUMO_PROMEDIO_AGUA.name());
                indicadores.add(ParamIndicador.IND_SERVICIO_ANALISIS_CONSUMO_PROMEDIO_ENERGIA_ELECTRICA.name());
                indicadores.add(ParamIndicador.IND_SERVICIO_NUM_PREDIOS_ANALISIS_CONSUMO_PROMEDIO_ENERGIA_ELECTRICA.name());
                indicadores.add(ParamIndicador.IND_AMPLIACION_M2_REQUERIDOS_PREDIO.name());
                indicadores.add(ParamIndicador.IND_AMPLIACION_POSIBILIDAD_CONSTRUCCION_PRIMER_PISO.name());
                indicadores.add(ParamIndicador.IND_AMPLIACION_POSIBILIDAD_CONSTRUCCION_ALTURA.name());
                indicadores.add(ParamIndicador.IND_AMPLIACION_POSIBILIDAD_CONSTRUCCION_TOTAL.name());
                indicadores.add(ParamIndicador.IND_AMPLIACION_IDONEIDAD_PREDIO.name());
                indicadores.add(ParamIndicador.IND_AMPLIACION_M2_AMPLIAR_COBERTURA.name());
                indicadores.add(ParamIndicador.IND_AMPLIACION_ALUMNOS_NUEVOS_INFRAESTRUCTURA.name());
                //indicadores.add(ParamIndicador..name());
                List<Object[]> areas = facParam.consultarIndicadores(indicadores, n.getConfiguracion().getNivel().longValue(), n.getId());
                if (!areas.isEmpty()) {
                    int i = 0;
                    dpa.setSuperficie(new BigDecimal(areas.get(0)[i] == null ? "0" : (String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP) + "");
                    superficieTotal = superficieTotal.add(new BigDecimal(areas.get(0)[i] == null ? "0" : (String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                    i++;
                    BigDecimal areaConstrucTotalPredio = new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]);
                    if (dpa.getSuperficie() != null && new BigDecimal(dpa.getSuperficie()).compareTo(BigDecimal.ZERO) != 0) {
                        porcenConstruccionTotal = porcenConstruccionTotal.add(areaConstrucTotalPredio.multiply(new BigDecimal("100")).divide(new BigDecimal(dpa.getSuperficie()), 2, RoundingMode.HALF_UP));
                        dpa.setIndConstruccion(new BigDecimal("100").multiply(areaConstrucTotalPredio).divide(new BigDecimal(dpa.getSuperficie()), 2, RoundingMode.HALF_UP) + "");
                        dpa.setPorcenConstruccion(areaConstrucTotalPredio.multiply(new BigDecimal("100")).divide(new BigDecimal(dpa.getSuperficie()), 2, RoundingMode.HALF_UP) + "");
                    }
                    i++;
                    if (areas.get(0)[i] != null) {
                        dpa.setAreaConstrucPrimerPiso(new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP) + "");
                        areaConstrucPrimerPisoTotal = areaConstrucPrimerPisoTotal.add(new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));

                        if (dpa.getSuperficie() != null && new BigDecimal(dpa.getSuperficie()).compareTo(BigDecimal.ZERO) != 0) {
                            dpa.setIndOcupacion(new BigDecimal("100").multiply(new BigDecimal((String) areas.get(0)[i])).divide(new BigDecimal(dpa.getSuperficie()), 2, RoundingMode.HALF_UP) + "");
                        }
                    }
                    i++;
                    if (areas.get(0)[i] != null) {
                        BigDecimal areaConstPrimerPiso = new BigDecimal(UtilCadena.isNullOVacio(dpa.getAreaConstrucPrimerPiso()) ? "0" : dpa.getAreaConstrucPrimerPiso());
                        dpa.setAreaConstrucAltura(areaConstrucTotalPredio.subtract(areaConstPrimerPiso).setScale(2, RoundingMode.HALF_UP) + "");
                        areaConstrucAlturaTotal = areaConstrucAlturaTotal.add(areaConstrucTotalPredio.subtract(areaConstPrimerPiso).setScale(2, RoundingMode.HALF_UP));
                        dpa.setNumPrediosAccesibilidadInadecuada(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? null : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP) + "");
                        numPrediosAccesibilidadInadecuadaTotal = numPrediosAccesibilidadInadecuadaTotal.add(new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                    }
                    i++;
                    dpa.setAccesibilidadPoblacionAfectada(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP) + "");
                    accesibilidadPoblacionAfectadaTotal = accesibilidadPoblacionAfectadaTotal.add(new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                    i++;
                    dpa.setPrediosProblemasLegalizacion(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? null : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP) + "");
                    prediosProblemasLegalizacionTotal = prediosProblemasLegalizacionTotal.add(new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                    i++;
                    dpa.setEstudiantesProblemaLegalizacion(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? null : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP) + "");
                    estudiantesProblemaLegalizacionTotal = estudiantesProblemaLegalizacionTotal.add(new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                    i++;
                    dpa.setPromedioConsumoAgua(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? null : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP) + "");
                    promedioConsumoAguaTotal = promedioConsumoAguaTotal.add(new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                    i++;
                    dpa.setNumPrediosCumplenConsumoAgua(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? null : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP) + "");
                    numPrediosCumplenConsumoAguaTotal = numPrediosCumplenConsumoAguaTotal.add(new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                    i++;
                    dpa.setPromedioConsumoEE(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? null : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP) + "");
                    promedioConsumoEETotal = promedioConsumoEETotal.add(new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                    i++;
                    dpa.setNumPrediosCumplenConsumoEE(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? null : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP) + "");
                    numPrediosCumplenConsumoEETotal = numPrediosCumplenConsumoEETotal.add(new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                    i++;
                    dpa.setM2Requeridos(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? null : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP) + "");
                    m2RequeridosTotal = m2RequeridosTotal.add(new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                    i++;
                    dpa.setConstruiblePrimerPiso(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? null : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP) + "");
                    construiblePrimerPisoTotal = construiblePrimerPisoTotal.add(new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                    i++;
                    dpa.setConstruibleAltura(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? null : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP) + "");
                    construibleAlturaTotal = construibleAlturaTotal.add(new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                    i++;
                    dpa.setConstruibleTotal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? null : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP) + "");
                    construibleTotalTotal = construibleTotalTotal.add(new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                    i++;
                    dpa.setIdoneidad(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? null : Utilidades.obtenerMensaje("aplicacion.general." + (new Double(areas.get(0)[i] + "") > new Double(100) ? "cumple" : "noCumple")));
                    i++;
                    dpa.setM2AmpliarCobertura(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? null : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP) + "");
                    m2AmpliarCoberturaTotal = m2AmpliarCoberturaTotal.add(new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                    i++;
                    dpa.setAlumnosNuevosInfraestructura(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? null : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP) + "");
                    alumnosNuevosInfraestructuraTotal = alumnosNuevosInfraestructuraTotal.add(new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                    i++;
                }

                //Indicadores de unidades funcionales
                List<String[]> indBusqUnidadesFun = new ArrayList<String[]>();
                indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_CONTROL_VIGILANCIA_NUM_PREDIOS_SEGUN_CONDICION_VULNERABILIDAD.name(), Vulnerabilidad.A.getNombre()});
                indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_SEGURIDAD_NUM_PREDIOS_SEGUN_ESTADO_ESTRUCTURA.name(), SiNo.Si.name()});
                indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_SEGURIDAD_NUM_PREDIOS_SEGUN_ANALISIS_RUTA_EVALUACION.name(), SiNo.Si.name()});
                indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_SEGURIDAD_NUM_PREDIOS_SEGUN_SENALIZACION_EVACUACION.name(), SiNo.Si.name()});
                indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_SEGURIDAD_NUM_PREDIOS_SEGUN_REACTIVO_CONTRA_INCENDIO.name(), SiNo.No.name()});
                indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_NUMERO_ESPACIOS_SEGUN_CONDICION_PISO.name(), CalificacionCondicion.CUATRO.getValor() + ""});
                indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_NUMERO_ESPACIOS_SEGUN_CONDICION_PISO.name(), CalificacionCondicion.TRES.getValor() + ""});
                indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_NUMERO_ESPACIOS_SEGUN_CONDICION_PISO.name(), CalificacionCondicion.DOS.getValor() + ""});
                indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_NUMERO_ESPACIOS_SEGUN_CONDICION_PISO.name(), CalificacionCondicion.UNO.getValor() + ""});
                indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_NUMERO_ESPACIOS_SEGUN_CONDICION_MURO.name(), CalificacionCondicion.CUATRO.getValor() + ""});
                indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_NUMERO_ESPACIOS_SEGUN_CONDICION_MURO.name(), CalificacionCondicion.TRES.getValor() + ""});
                indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_NUMERO_ESPACIOS_SEGUN_CONDICION_MURO.name(), CalificacionCondicion.DOS.getValor() + ""});
                indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_NUMERO_ESPACIOS_SEGUN_CONDICION_MURO.name(), CalificacionCondicion.UNO.getValor() + ""});
                indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_NUMERO_ESPACIOS_SEGUN_CONDICION_ACABADO_MURO.name(), CalificacionCondicion.CUATRO.getValor() + ""});
                indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_NUMERO_ESPACIOS_SEGUN_CONDICION_ACABADO_MURO.name(), CalificacionCondicion.TRES.getValor() + ""});
                indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_NUMERO_ESPACIOS_SEGUN_CONDICION_ACABADO_MURO.name(), CalificacionCondicion.DOS.getValor() + ""});
                indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_NUMERO_ESPACIOS_SEGUN_CONDICION_ACABADO_MURO.name(), CalificacionCondicion.UNO.getValor() + ""});
                indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_NUMERO_ESPACIOS_SEGUN_CONDICION_VENTANA.name(), CalificacionCondicion.CUATRO.getValor() + ""});
                indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_NUMERO_ESPACIOS_SEGUN_CONDICION_VENTANA.name(), CalificacionCondicion.TRES.getValor() + ""});
                indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_NUMERO_ESPACIOS_SEGUN_CONDICION_VENTANA.name(), CalificacionCondicion.DOS.getValor() + ""});
                indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_NUMERO_ESPACIOS_SEGUN_CONDICION_VENTANA.name(), CalificacionCondicion.UNO.getValor() + ""});
                indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_NUMERO_ESPACIOS_SEGUN_CONDICION_PUERTA.name(), CalificacionCondicion.CUATRO.getValor() + ""});
                indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_NUMERO_ESPACIOS_SEGUN_CONDICION_PUERTA.name(), CalificacionCondicion.TRES.getValor() + ""});
                indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_NUMERO_ESPACIOS_SEGUN_CONDICION_PUERTA.name(), CalificacionCondicion.DOS.getValor() + ""});
                indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_NUMERO_ESPACIOS_SEGUN_CONDICION_PUERTA.name(), CalificacionCondicion.UNO.getValor() + ""});
                indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_NUMERO_ESPACIOS_SEGUN_CONDICION_TECHO.name(), CalificacionCondicion.CUATRO.getValor() + ""});
                indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_NUMERO_ESPACIOS_SEGUN_CONDICION_TECHO.name(), CalificacionCondicion.TRES.getValor() + ""});
                indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_NUMERO_ESPACIOS_SEGUN_CONDICION_TECHO.name(), CalificacionCondicion.DOS.getValor() + ""});
                indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_NUMERO_ESPACIOS_SEGUN_CONDICION_TECHO.name(), CalificacionCondicion.UNO.getValor() + ""});
                indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_PISO.name(), CalificacionCondicion.CUATRO.getValor() + ""});
                indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_PISO.name(), CalificacionCondicion.TRES.getValor() + ""});
                indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_PISO.name(), CalificacionCondicion.DOS.getValor() + ""});
                indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_PISO.name(), CalificacionCondicion.UNO.getValor() + ""});
                indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_MURO.name(), CalificacionCondicion.CUATRO.getValor() + ""});
                indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_MURO.name(), CalificacionCondicion.TRES.getValor() + ""});
                indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_MURO.name(), CalificacionCondicion.DOS.getValor() + ""});
                indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_MURO.name(), CalificacionCondicion.UNO.getValor() + ""});
                indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_ACABADO_MURO.name(), CalificacionCondicion.CUATRO.getValor() + ""});
                indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_ACABADO_MURO.name(), CalificacionCondicion.TRES.getValor() + ""});
                indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_ACABADO_MURO.name(), CalificacionCondicion.DOS.getValor() + ""});
                indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_ACABADO_MURO.name(), CalificacionCondicion.UNO.getValor() + ""});
                indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_TECHO.name(), CalificacionCondicion.CUATRO.getValor() + ""});
                indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_TECHO.name(), CalificacionCondicion.TRES.getValor() + ""});
                indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_TECHO.name(), CalificacionCondicion.DOS.getValor() + ""});
                indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_TECHO.name(), CalificacionCondicion.UNO.getValor() + ""});
                indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_SERVICIO_NUM_PREDIOS_DISPONIBILIDAD_AGUA.name(), SiNo.Si.name().toUpperCase()});
                indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_SERVICIO_NUM_PREDIOS_DISPONIBILIDAD_ENERGIA_ELECTRICA.name(), SiNo.Si.name().toUpperCase()});
                indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_SERVICIO_NUM_PREDIOS_DISPONIBILIDAD_GAS.name(), SiNo.Si.name().toUpperCase()});
                indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_SERVICIO_NUM_PREDIOS_DISPONIBILIDAD_SISTEMA_RECICLAJE.name(), SiNo.Si.name().toUpperCase()});
                indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_SERVICIO_NUM_PREDIOS_DISPONIBILIDAD_RED_ALCANTARILLADO.name(), SiNo.Si.name().toUpperCase()});
                indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_SERVICIO_NUM_PREDIOS_DISPONIBILIDAD_RED_PLUVIAL.name(), SiNo.Si.name().toUpperCase()});
                indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_SERVICIO_NUM_PREDIOS_DISPONIBILIDAD_INTERNET.name(), SiNo.Si.name().toUpperCase()});
                indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_SERVICIO_NUM_PREDIOS_DISPONIBILIDAD_TELEFONO.name(), SiNo.Si.name().toUpperCase()});

                areas = facParam.consultarIndicadoresUnidadesFuncionales(indBusqUnidadesFun, n.getConfiguracion().getNivel().longValue(), n.getId());
                int i = 0;
                if (!areas.isEmpty()) {
                    dpa.setNumPrediosVulnerables(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]);
                    numPrediosVulnerablesTotal = numPrediosVulnerablesTotal.add(new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                    i++;
                    dpa.setNumPrediosEstrucCritica(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]);
                    numPrediosEstrucCriticaTotal = numPrediosEstrucCriticaTotal.add(new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                    i++;
                    dpa.setNumPrediosRutaEvacCritica(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? null : (String) areas.get(0)[i]);
                    numPrediosRutaEvacCriticaTotal = numPrediosRutaEvacCriticaTotal.add(new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                    i++;
                    dpa.setNumPrediosSenalizaEvacCritica(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? null : (String) areas.get(0)[i]);
                    numPrediosSenalizaEvacCriticaTotal = numPrediosSenalizaEvacCriticaTotal.add(new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                    i++;
                    dpa.setNumPrediosNoRactivoIncendio(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? null : (String) areas.get(0)[i]);
                    numPrediosNoRactivoIncendioTotal = numPrediosNoRactivoIncendioTotal.add(new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                    i++;
                    dpa.setNumPisosCond4(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]);
                    i++;
                    dpa.setNumPisosCond3(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]);
                    i++;
                    dpa.setNumPisosCond2(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]);
                    i++;
                    dpa.setNumPisosCond1(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]);
                    i++;
                    dpa.setNumMurosCond4(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]);
                    i++;
                    dpa.setNumMurosCond3(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]);
                    i++;
                    dpa.setNumMurosCond2(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]);
                    i++;
                    dpa.setNumMurosCond1(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]);
                    i++;
                    dpa.setNumAcabadosMuroCond4(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]);
                    i++;
                    dpa.setNumAcabadosMuroCond3(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]);
                    i++;
                    dpa.setNumAcabadosMuroCond2(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]);
                    i++;
                    dpa.setNumAcabadosMuroCond1(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]);
                    i++;
                    dpa.setNumVentanasCond4(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]);
                    i++;
                    dpa.setNumVentanasCond3(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]);
                    i++;
                    dpa.setNumVentanasCond2(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]);
                    i++;
                    dpa.setNumVentanasCond1(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]);
                    i++;
                    dpa.setNumPuertasCond4(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]);
                    i++;
                    dpa.setNumPuertasCond3(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]);
                    i++;
                    dpa.setNumPuertasCond2(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]);
                    i++;
                    dpa.setNumPuertasCond1(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]);
                    i++;
                    dpa.setNumCieloRasosCond4(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]);
                    i++;
                    dpa.setNumCieloRasosCond3(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]);
                    i++;
                    dpa.setNumCieloRasosCond2(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]);
                    i++;
                    dpa.setNumCieloRasosCond1(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]);
                    i++;
                    dpa.setAreaPisosCond4(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP) + "");
                    i++;
                    dpa.setAreaPisosCond3(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP) + "");
                    i++;
                    dpa.setAreaPisosCond2(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP) + "");
                    i++;
                    dpa.setAreaPisosCond1(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP) + "");
                    i++;
                    dpa.setAreaMurosCond4(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP) + "");
                    i++;
                    dpa.setAreaMurosCond3(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP) + "");
                    i++;
                    dpa.setAreaMurosCond2(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP) + "");
                    i++;
                    dpa.setAreaMurosCond1(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP) + "");
                    i++;
                    dpa.setAreaAcabadosMuroCond4(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP) + "");
                    i++;
                    dpa.setAreaAcabadosMuroCond3(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP) + "");
                    i++;
                    dpa.setAreaAcabadosMuroCond2(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP) + "");
                    i++;
                    dpa.setAreaAcabadosMuroCond1(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP) + "");
                    i++;
                    dpa.setAreaCieloRasosCond4(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP) + "");
                    i++;
                    dpa.setAreaCieloRasosCond3(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP) + "");
                    i++;
                    dpa.setAreaCieloRasosCond2(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP) + "");
                    i++;
                    dpa.setAreaCieloRasosCond1(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP) + "");
                    i++;
                    dpa.setNumPrediosConAgua(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? null : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP) + "");
                    numPrediosConAguaTotal = numPrediosConAguaTotal.add(new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                    i++;
                    dpa.setNumPrediosConEE(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? null : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP) + "");
                    numPrediosConEETotal = numPrediosConEETotal.add(new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                    i++;
                    dpa.setNumPrediosConGas(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? null : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP) + "");
                    numPrediosConGasTotal = numPrediosConGasTotal.add(new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                    i++;
                    dpa.setNumPrediosConReciclaje(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? null : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP) + "");
                    numPrediosConReciclajeTotal = numPrediosConReciclajeTotal.add(new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                    i++;
                    dpa.setNumPrediosConAlcantarillado(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? null : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP) + "");
                    numPrediosConAlcantarilladoTotal = numPrediosConAlcantarilladoTotal.add(new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                    i++;
                    dpa.setNumPrediosConPluvial(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? null : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP) + "");
                    numPrediosConPluvialTotal = numPrediosConPluvialTotal.add(new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                    i++;
                    dpa.setNumPrediosConInternet(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? null : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP) + "");
                    numPrediosConInternetTotal = numPrediosConInternetTotal.add(new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                    i++;
                    dpa.setNumPrediosConTelefono(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? null : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP) + "");
                    numPrediosConTelefonoTotal = numPrediosConTelefonoTotal.add(new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                    i++;

                }

                HashMap<String, Object> datosUnidadesFuncionales = this.obtenerUnidadesFuncionales(n.getId(), n.getConfiguracion().getNivel().longValue());

                dpa.setAreaAula(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("areaAula")) ? "0" : (String) datosUnidadesFuncionales.get("areaAula"));
                dpa.setEstandarAula(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("estandarAula")) ? "0" : (String) datosUnidadesFuncionales.get("estandarAula"));
                dpa.setM2AlumnosAula(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("m2AlumnosAula")) ? "0" : (String) datosUnidadesFuncionales.get("m2AlumnosAula"));
                dpa.setCumplimientoAula((String) datosUnidadesFuncionales.get("cumplimientoAula"));
                dpa.setDeficitAula(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("deficitAula")) ? "0" : (String) datosUnidadesFuncionales.get("deficitAula"));
                dpa.setNumPrediosNoCumplenAula(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("numPrediosNoCumplenAula")) ? "0" : (String) datosUnidadesFuncionales.get("numPrediosNoCumplenAula"));
                dpa.setAreaLabCiencias(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("areaLabCiencias")) ? "0" : (String) datosUnidadesFuncionales.get("areaLabCiencias"));
                dpa.setEstandarLabCiencias(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("estandarLabCiencias")) ? "0" : (String) datosUnidadesFuncionales.get("estandarLabCiencias"));
                dpa.setM2AlumnosLabCiencias(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("m2AlumnosLabCiencias")) ? "0" : (String) datosUnidadesFuncionales.get("m2AlumnosLabCiencias"));
                dpa.setCumplimientoLabCiencias((String) datosUnidadesFuncionales.get("cumplimientoLabCiencias"));
                dpa.setDeficitLabCiencias(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("deficitLabCiencias")) ? "0" : (String) datosUnidadesFuncionales.get("deficitLabCiencias"));
                dpa.setNumPrediosNoCumplenLabCiencias(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("numPrediosNoCumplenLabCiencias")) ? "0" : (String) datosUnidadesFuncionales.get("numPrediosNoCumplenLabCiencias"));
                dpa.setAreaLabMultimedia(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("areaLabMultimedia")) ? "0" : (String) datosUnidadesFuncionales.get("areaLabMultimedia"));
                dpa.setEstandarLabMultimedia(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("estandarLabMultimedia")) ? "0" : (String) datosUnidadesFuncionales.get("estandarLabMultimedia"));
                dpa.setM2AlumnosLabMultimedia(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("m2AlumnosLabMultimedia")) ? "0" : (String) datosUnidadesFuncionales.get("m2AlumnosLabMultimedia"));
                dpa.setCumplimientoLabMultimedia((String) datosUnidadesFuncionales.get("cumplimientoLabMultimedia"));
                dpa.setDeficitLabMultimedia(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("deficitLabMultimedia")) ? "0" : (String) datosUnidadesFuncionales.get("deficitLabMultimedia"));
                dpa.setNumPrediosNoCumplenLabMultimedia(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("numPrediosNoCumplenLabMultimedia")) ? "0" : (String) datosUnidadesFuncionales.get("numPrediosNoCumplenLabMultimedia"));
                dpa.setAreaLabComputacion(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("areaLabComputacion")) ? "0" : (String) datosUnidadesFuncionales.get("areaLabComputacion"));
                dpa.setEstandarLabComputacion(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("estandarLabComputacion")) ? "0" : (String) datosUnidadesFuncionales.get("estandarLabComputacion"));
                dpa.setM2AlumnosLabComputacion(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("m2AlumnosLabComputacion")) ? "0" : (String) datosUnidadesFuncionales.get("m2AlumnosLabComputacion"));
                dpa.setCumplimientoLabComputacion((String) datosUnidadesFuncionales.get("cumplimientoLabComputacion"));
                dpa.setDeficitLabComputacion(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("deficitLabComputacion")) ? "0" : (String) datosUnidadesFuncionales.get("deficitLabComputacion"));
                dpa.setNumPrediosNoCumplenLabComputacion(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("numPrediosNoCumplenLabComputacion")) ? "0" : (String) datosUnidadesFuncionales.get("numPrediosNoCumplenLabComputacion"));
                dpa.setAreaBiblioteca(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("areaBiblioteca")) ? "0" : (String) datosUnidadesFuncionales.get("areaBiblioteca"));
                dpa.setEstandarBiblioteca(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("estandarBiblioteca")) ? "0" : (String) datosUnidadesFuncionales.get("estandarBiblioteca"));
                dpa.setM2AlumnosBiblioteca(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("m2AlumnosBiblioteca")) ? "0" : (String) datosUnidadesFuncionales.get("m2AlumnosBiblioteca"));
                dpa.setCumplimientoBiblioteca((String) datosUnidadesFuncionales.get("cumplimientoBiblioteca"));
                dpa.setDeficitBiblioteca(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("deficitBiblioteca")) ? "0" : (String) datosUnidadesFuncionales.get("deficitBiblioteca"));
                dpa.setNumPrediosNoCumplenBiblioteca(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("numPrediosNoCumplenBiblioteca")) ? "0" : (String) datosUnidadesFuncionales.get("numPrediosNoCumplenBiblioteca"));
                dpa.setAreaSalaMusica(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("areaSalaMusica")) ? "0" : (String) datosUnidadesFuncionales.get("areaSalaMusica"));
                dpa.setEstandarSalaMusica(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("estandarSalaMusica")) ? "0" : (String) datosUnidadesFuncionales.get("estandarSalaMusica"));
                dpa.setM2AlumnosSalaMusica(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("m2AlumnosSalaMusica")) ? "0" : (String) datosUnidadesFuncionales.get("m2AlumnosSalaMusica"));
                dpa.setCumplimientoSalaMusica((String) datosUnidadesFuncionales.get("cumplimientoSalaMusica"));
                dpa.setDeficitSalaMusica(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("deficitSalaMusica")) ? "0" : (String) datosUnidadesFuncionales.get("deficitSalaMusica"));
                dpa.setNumPrediosNoCumplenSalaMusica(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("numPrediosNoCumplenSalaMusica")) ? "0" : (String) datosUnidadesFuncionales.get("numPrediosNoCumplenSalaMusica"));
                dpa.setAreaSalaArte(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("areaSalaArte")) ? "0" : (String) datosUnidadesFuncionales.get("areaSalaArte"));
                dpa.setEstandarSalaArte(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("estandarSalaArte")) ? "0" : (String) datosUnidadesFuncionales.get("estandarSalaArte"));
                dpa.setM2AlumnosSalaArte(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("m2AlumnosSalaArte")) ? "0" : (String) datosUnidadesFuncionales.get("m2AlumnosSalaArte"));
                dpa.setCumplimientoSalaArte((String) datosUnidadesFuncionales.get("cumplimientoSalaArte"));
                dpa.setDeficitSalaArte(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("deficitSalaArte")) ? "0" : (String) datosUnidadesFuncionales.get("deficitSalaArte"));
                dpa.setNumPrediosNoCumplenSalaArte(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("numPrediosNoCumplenSalaArte")) ? "0" : (String) datosUnidadesFuncionales.get("numPrediosNoCumplenSalaArte"));
                dpa.setAreaPlayonDepor(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("areaPlayonDepor")) ? "0" : (String) datosUnidadesFuncionales.get("areaPlayonDepor"));
                dpa.setEstandarPlayonDepor(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("estandarPlayonDepor")) ? "0" : (String) datosUnidadesFuncionales.get("estandarPlayonDepor"));
                dpa.setM2AlumnosPlayonDepor(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("m2AlumnosPlayonDepor")) ? "0" : (String) datosUnidadesFuncionales.get("m2AlumnosPlayonDepor"));
                dpa.setCumplimientoPlayonDepor((String) datosUnidadesFuncionales.get("cumplimientoPlayonDepor"));
                dpa.setDeficitPlayonDepor(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("deficitPlayonDepor")) ? "0" : (String) datosUnidadesFuncionales.get("deficitPlayonDepor"));
                dpa.setNumPrediosNoCumplenPlayonDepor(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("numPrediosNoCumplenPlayonDepor")) ? "0" : (String) datosUnidadesFuncionales.get("numPrediosNoCumplenPlayonDepor"));
                dpa.setAreaExpRecre(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("areaExpRecre")) ? "0" : (String) datosUnidadesFuncionales.get("areaExpRecre"));
                dpa.setEstandarExpRecre(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("estandarExpRecre")) ? "0" : (String) datosUnidadesFuncionales.get("estandarExpRecre"));
                dpa.setM2AlumnosExpRecre(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("m2AlumnosExpRecre")) ? "0" : (String) datosUnidadesFuncionales.get("m2AlumnosExpRecre"));
                dpa.setCumplimientoExpRecre((String) datosUnidadesFuncionales.get("cumplimientoExpRecre"));
                dpa.setDeficitExpRecre(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("deficitExpRecre")) ? "0" : (String) datosUnidadesFuncionales.get("deficitExpRecre"));
                dpa.setNumPrediosNoCumplenExpRecre(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("numPrediosNoCumplenExpRecre")) ? "0" : (String) datosUnidadesFuncionales.get("numPrediosNoCumplenExpRecre"));
                dpa.setAreaSUM(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("areaSUM")) ? "0" : (String) datosUnidadesFuncionales.get("areaSUM"));
                dpa.setEstandarSUM(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("estandarSUM")) ? "0" : (String) datosUnidadesFuncionales.get("estandarSUM"));
                dpa.setM2AlumnosSUM(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("m2AlumnosSUM")) ? "0" : (String) datosUnidadesFuncionales.get("m2AlumnosSUM"));
                dpa.setCumplimientoSUM((String) datosUnidadesFuncionales.get("cumplimientoSUM"));
                dpa.setDeficitSUM(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("deficitSUM")) ? "0" : (String) datosUnidadesFuncionales.get("deficitSUM"));
                dpa.setNumPrediosNoCumplenSUM(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("numPrediosNoCumplenSUM")) ? "0" : (String) datosUnidadesFuncionales.get("numPrediosNoCumplenSUM"));
                dpa.setAreaPsicopeEnferm(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("areaPsicopeEnferm")) ? "0" : (String) datosUnidadesFuncionales.get("areaPsicopeEnferm"));
                dpa.setEstandarPsicopeEnferm(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("estandarPsicopeEnferm")) ? "0" : (String) datosUnidadesFuncionales.get("estandarPsicopeEnferm"));
                dpa.setM2AlumnosPsicopeEnferm(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("m2AlumnosPsicopeEnferm")) ? "0" : (String) datosUnidadesFuncionales.get("m2AlumnosPsicopeEnferm"));
                dpa.setCumplimientoPsicopeEnferm((String) datosUnidadesFuncionales.get("cumplimientoPsicopeEnferm"));
                dpa.setDeficitPsicopeEnferm(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("deficitPsicopeEnferm")) ? "0" : (String) datosUnidadesFuncionales.get("deficitPsicopeEnferm"));
                dpa.setNumPrediosNoCumplenPsicopeEnferm(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("numPrediosNoCumplenPsicopeEnferm")) ? "0" : (String) datosUnidadesFuncionales.get("numPrediosNoCumplenPsicopeEnferm"));
                dpa.setAreaServSanitarios(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("areaServSanitarios")) ? "0" : (String) datosUnidadesFuncionales.get("areaServSanitarios"));
                dpa.setEstandarServSanitarios(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("estandarServSanitarios")) ? "0" : (String) datosUnidadesFuncionales.get("estandarServSanitarios"));
                dpa.setM2AlumnosServSanitarios(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("m2AlumnosServSanitarios")) ? "0" : (String) datosUnidadesFuncionales.get("m2AlumnosServSanitarios"));
                dpa.setCumplimientoServSanitarios((String) datosUnidadesFuncionales.get("cumplimientoServSanitarios"));
                dpa.setDeficitServSanitarios(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("deficitServSanitarios")) ? "0" : (String) datosUnidadesFuncionales.get("deficitServSanitarios"));
                dpa.setNumPrediosNoCumplenServSanitarios(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("numPrediosNoCumplenServSanitarios")) ? "0" : (String) datosUnidadesFuncionales.get("numPrediosNoCumplenServSanitarios"));
                dpa.setAreaCocinaCafe(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("areaCocinaCafe")) ? "0" : (String) datosUnidadesFuncionales.get("areaCocinaCafe"));
                dpa.setEstandarCocinaCafe(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("estandarCocinaCafe")) ? "0" : (String) datosUnidadesFuncionales.get("estandarCocinaCafe"));
                dpa.setM2AlumnosCocinaCafe(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("m2AlumnosCocinaCafe")) ? "0" : (String) datosUnidadesFuncionales.get("m2AlumnosCocinaCafe"));
                dpa.setCumplimientoCocinaCafe((String) datosUnidadesFuncionales.get("cumplimientoCocinaCafe"));
                dpa.setDeficitCocinaCafe(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("deficitCocinaCafe")) ? "0" : (String) datosUnidadesFuncionales.get("deficitCocinaCafe"));
                dpa.setNumPrediosNoCumplenCocinaCafe(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("numPrediosNoCumplenCocinaCafe")) ? "0" : (String) datosUnidadesFuncionales.get("numPrediosNoCumplenCocinaCafe"));
                dpa.setAreaOfAdm(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("areaOfAdm")) ? "0" : (String) datosUnidadesFuncionales.get("areaOfAdm"));
                dpa.setEstandarOfAdm(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("estandarOfAdm")) ? "0" : (String) datosUnidadesFuncionales.get("estandarOfAdm"));
                dpa.setM2AlumnosOfAdm(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("m2AlumnosOfAdm")) ? "0" : (String) datosUnidadesFuncionales.get("m2AlumnosOfAdm"));
                dpa.setCumplimientoOfAdm((String) datosUnidadesFuncionales.get("cumplimientoOfAdm"));
                dpa.setDeficitOfAdm(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("deficitOfAdm")) ? "0" : (String) datosUnidadesFuncionales.get("deficitOfAdm"));
                dpa.setNumPrediosNoCumplenOfAdm(UtilCadena.isNullOVacio((String) datosUnidadesFuncionales.get("numPrediosNoCumplenOfAdm")) ? "0" : (String) datosUnidadesFuncionales.get("numPrediosNoCumplenOfAdm"));
                dpa.setPropAreasCentrales(!datosUnidadesFuncionales.containsKey("propAreasCentrales") ? "" : datosUnidadesFuncionales.get("propAreasCentrales") + "");
                dpa.setPropAreasApoyo(!datosUnidadesFuncionales.containsKey("propAreasApoyo") ? "" : datosUnidadesFuncionales.get("propAreasApoyo") + "");
                dpa.setPropAreasAulas(!datosUnidadesFuncionales.containsKey("propAreasAulas") ? "" : datosUnidadesFuncionales.get("propAreasAulas") + "");
                dpa.setPropAreasLaboratorios(!datosUnidadesFuncionales.containsKey("propAreasLaboratorios") ? "" : datosUnidadesFuncionales.get("propAreasLaboratorios") + "");
                dpa.setPropAreasCentroRecursosAprendizaje(!datosUnidadesFuncionales.containsKey("propAreasCentroRecursosAprendizaje") ? "" : datosUnidadesFuncionales.get("propAreasCentroRecursosAprendizaje") + "");
                dpa.setPropAreasTalleres(!datosUnidadesFuncionales.containsKey("propAreasTalleres") ? "" : datosUnidadesFuncionales.get("propAreasTalleres") + "");
                dpa.setPropAreasExtension(!datosUnidadesFuncionales.containsKey("propAreasExtension") ? "" : datosUnidadesFuncionales.get("propAreasExtension") + "");
                dpa.setPropAreasServSanitarios(!datosUnidadesFuncionales.containsKey("propAreasServSanitarios") ? "" : datosUnidadesFuncionales.get("propAreasServSanitarios") + "");
                dpa.setPropAreasAbastProcesa(!datosUnidadesFuncionales.containsKey("propAreasAbastProcesa") ? "" : datosUnidadesFuncionales.get("propAreasAbastProcesa") + "");
                dpa.setPropAreasConducAdministra(!datosUnidadesFuncionales.containsKey("propAreasConducAdministra") ? "" : datosUnidadesFuncionales.get("propAreasConducAdministra") + "");

                areasCentrales = areasCentrales.add(new BigDecimal(((Double) datosUnidadesFuncionales.get("areasCentrales")).toString()));
                areasApoyo = areasApoyo.add(new BigDecimal(((Double) datosUnidadesFuncionales.get("areasApoyo")).toString()));
                areaTotal = areaTotal.add(new BigDecimal(((Double) datosUnidadesFuncionales.get("areaTotal")).toString()));
                areaAulas = areaAulas.add(new BigDecimal(((Double) datosUnidadesFuncionales.get("areaAulas")).toString()));
                areaLaboratorios = areaLaboratorios.add(new BigDecimal(((Double) datosUnidadesFuncionales.get("areaLaboratorios")).toString()));
                areaCentroRecursosAprendizaje = areaCentroRecursosAprendizaje.add(new BigDecimal(((Double) datosUnidadesFuncionales.get("areaCentroRecursosAprendizaje")).toString()));
                areaTalleres = areaTalleres.add(new BigDecimal(((Double) datosUnidadesFuncionales.get("areaTalleres")).toString()));
                areaExtension = areaExtension.add(new BigDecimal(((Double) datosUnidadesFuncionales.get("areaExtension")).toString()));
                areaServSanitariosTotal = areaServSanitariosTotal.add(new BigDecimal(((Double) datosUnidadesFuncionales.get("areaServSanitariosTotal")).toString()));
                areaAbastProcesa = areaAbastProcesa.add(new BigDecimal(((Double) datosUnidadesFuncionales.get("areaAbastProcesa")).toString()));
                areaConducAdministra = areaConducAdministra.add(new BigDecimal(((Double) datosUnidadesFuncionales.get("areaConducAdministra")).toString()));

                HashMap datosRedistribucion = this.obtenerRedistribucion(n, n.getConfiguracion().getNivel().longValue(), nivel0.getId());
                dpa.setUtilizacion(datosRedistribucion.get("utilizacion") == null ? null : datosRedistribucion.get("utilizacion") + "");
                dpa.setCuposTotal(datosRedistribucion.get("cuposTotal") == null ? null : datosRedistribucion.get("cuposTotal") + "");
                cuposTotalTotal = cuposTotalTotal.add(datosRedistribucion.get("cuposTotal") == null ? new BigDecimal("0") : (BigDecimal) datosRedistribucion.get("cuposTotal"));
                listaDatosDPAs.add(dpa);

                parametros.put("numSedes", numSedes + (parametros.get("numSedes") != null ? (Integer) parametros.get("numSedes") : 0));
                parametros.put("numPredios", numPredios + (parametros.get("numPredios") != null ? (Integer) parametros.get("numPredios") : 0));
                parametros.put("numEstablecimientos", numEstablecimientos + (parametros.get("numEstablecimientos") != null ? (Integer) parametros.get("numEstablecimientos") : 0));
            }// FIN FOR nivel.getNivelesList()

            if (nivelConsulta > ParamNivelConsul.CERO.getNivel() && !parametros.containsKey("region")) {
                if (nivelConsulta.equals(ParamNivelConsul.UNO.getNivel())) {
                    parametros.put("region", nivel.getDescripcion());
                }
                if (nivelConsulta > ParamNivelConsul.UNO.getNivel()) {
                    parametros.put("region", nivel.getPadre().getDescripcion());
                    parametros.put("departamento", nivel.getDescripcion());
                }
            }
            parametros.put("propAreasCentrales", areaTotal.compareTo(new BigDecimal("0")) == 0 ? new BigDecimal("0") : new BigDecimal("100").multiply(areasCentrales).divide(areaTotal, 2, RoundingMode.HALF_UP));
            parametros.put("propAreasApoyo", areaTotal.compareTo(new BigDecimal("0")) == 0 ? new BigDecimal("0") : new BigDecimal("100").multiply(areasApoyo).divide(areaTotal, 2, RoundingMode.HALF_UP));
            parametros.put("propAreasAulas", areaTotal.compareTo(new BigDecimal("0")) == 0 || parametros.get("propAreasCentrales") == null || new BigDecimal(parametros.get("propAreasCentrales") + "").compareTo(BigDecimal.ZERO) == 0 ? new BigDecimal("0") : new BigDecimal("100").multiply(areaAulas).divide(areaTotal, RoundingMode.HALF_UP).multiply(new BigDecimal("100").divide((BigDecimal) parametros.get("propAreasCentrales"), RoundingMode.HALF_UP)).setScale(2, RoundingMode.HALF_UP));
            parametros.put("propAreasLaboratorios", areaTotal.compareTo(new BigDecimal("0")) == 0 && (parametros.get("propAreasCentrales") == null || new BigDecimal(parametros.get("propAreasCentrales") + "").compareTo(BigDecimal.ZERO) == 0) ? new BigDecimal("0") : new BigDecimal("100").multiply(areaLaboratorios).divide(areaTotal, RoundingMode.HALF_UP).multiply(new BigDecimal("100").divide((BigDecimal) parametros.get("propAreasCentrales"), RoundingMode.HALF_UP)).setScale(2, RoundingMode.HALF_UP));
            parametros.put("propAreasCentroRecursosAprendizaje", areaTotal.compareTo(new BigDecimal("0")) == 0 && (parametros.get("propAreasCentrales") == null || new BigDecimal(parametros.get("propAreasCentrales") + "").compareTo(BigDecimal.ZERO) == 0) ? new BigDecimal("0") : new BigDecimal("100").multiply(areaCentroRecursosAprendizaje).divide(areaTotal, RoundingMode.HALF_UP).multiply(new BigDecimal("100").divide((BigDecimal) parametros.get("propAreasCentrales"), RoundingMode.HALF_UP)).setScale(2, RoundingMode.HALF_UP));
            parametros.put("propAreasTalleres", areaTotal.compareTo(new BigDecimal("0")) == 0 && (parametros.get("propAreasCentrales") == null || new BigDecimal(parametros.get("propAreasCentrales") + "").compareTo(BigDecimal.ZERO) == 0) ? new BigDecimal("0") : new BigDecimal("100").multiply(areaTalleres).divide(areaTotal, RoundingMode.HALF_UP).multiply(new BigDecimal("100").divide((BigDecimal) parametros.get("propAreasCentrales"), RoundingMode.HALF_UP)).setScale(2, RoundingMode.HALF_UP));
            parametros.put("propAreasExtension", areaTotal.compareTo(new BigDecimal("0")) == 0 && (parametros.get("propAreasApoyo") == null || new BigDecimal(parametros.get("propAreasApoyo") + "").compareTo(BigDecimal.ZERO) == 0) ? new BigDecimal("0") : new BigDecimal("100").multiply(areaExtension).divide(areaTotal, RoundingMode.HALF_UP).multiply(new BigDecimal("100").divide((BigDecimal) parametros.get("propAreasApoyo"), RoundingMode.HALF_UP)).setScale(2, RoundingMode.HALF_UP));
            parametros.put("propAreasServSanitarios", areaTotal.compareTo(new BigDecimal("0")) == 0 && (parametros.get("propAreasApoyo") == null || new BigDecimal(parametros.get("propAreasApoyo") + "").compareTo(BigDecimal.ZERO) == 0) ? new BigDecimal("0") : new BigDecimal("100").multiply(areaServSanitariosTotal).divide(areaTotal, RoundingMode.HALF_UP).multiply(new BigDecimal("100").divide((BigDecimal) parametros.get("propAreasApoyo"), RoundingMode.HALF_UP)).setScale(2, RoundingMode.HALF_UP));
            parametros.put("propAreasAbastProcesa", areaTotal.compareTo(new BigDecimal("0")) == 0 && (parametros.get("propAreasApoyo") == null || new BigDecimal(parametros.get("propAreasApoyo") + "").compareTo(BigDecimal.ZERO) == 0) ? new BigDecimal("0") : new BigDecimal("100").multiply(areaAbastProcesa).divide(areaTotal, RoundingMode.HALF_UP).multiply(new BigDecimal("100").divide((BigDecimal) parametros.get("propAreasApoyo"), RoundingMode.HALF_UP)).setScale(2, RoundingMode.HALF_UP));
            parametros.put("propAreasConducAdministra", areaTotal.compareTo(new BigDecimal("0")) == 0 && (parametros.get("propAreasApoyo") == null || new BigDecimal(parametros.get("propAreasApoyo") + "").compareTo(BigDecimal.ZERO) == 0) ? new BigDecimal("0") : new BigDecimal("100").multiply(areaConducAdministra).divide(areaTotal, RoundingMode.HALF_UP).multiply(new BigDecimal("100").divide((BigDecimal) parametros.get("propAreasApoyo"), RoundingMode.HALF_UP)).setScale(2, RoundingMode.HALF_UP));

            parametros.put("CondMat1", Utilidades.obtenerMensaje("tipologias.condicion." + "EMPMF_1"));
            parametros.put("CondMat2", Utilidades.obtenerMensaje("tipologias.condicion." + "EMPMF_2"));
            parametros.put("CondMat3", Utilidades.obtenerMensaje("tipologias.condicion." + "EMPMF_3"));
            parametros.put("CondMat4", Utilidades.obtenerMensaje("tipologias.condicion." + "EMPMF_4"));

            this.anadirCalificaciones(id, nivelConsulta);

            DateFormat df = DateFormat.getDateInstance(DateFormat.LONG);
            parametros.put("fechaReporte", df.format(new Date()));

            String periodoCorte = facParam.consultarPeriodoIndicadores();
            if (periodoCorte != null) {
                Calendar calAux = Calendar.getInstance();
                calAux.set(new Integer(periodoCorte.substring(0, 4)), new Integer(periodoCorte.substring(4, 6)), 1);
                SimpleDateFormat format = new SimpleDateFormat("MMMM yyyy");
                String fecha = format.format(calAux.getTime());
                parametros.put("fechaCorte", fecha.substring(0, 1).toUpperCase() + fecha.substring(1, fecha.length()));
            }

            parametros.put("superficieTotal", superficieTotal);
            parametros.put("porcenConstruccionTotal", porcenConstruccionTotal);
            parametros.put("areaConstrucPrimerPisoTotal", areaConstrucPrimerPisoTotal);
            parametros.put("areaConstrucAlturaTotal", areaConstrucAlturaTotal);
            parametros.put("accesibilidadPoblacionAfectadaTotal", accesibilidadPoblacionAfectadaTotal);
            parametros.put("numPrediosAccesibilidadInadecuadaTotal", numPrediosAccesibilidadInadecuadaTotal);
            parametros.put("numPrediosTotal", numPrediosTotal);
            parametros.put("numPrediosConRiesgosTotal", numPrediosConRiesgosTotal);
            parametros.put("poblacionAfectadaRiesgosTotal", poblacionAfectadaRiesgosTotal);
            parametros.put("prediosProblemasLegalizacionTotal", prediosProblemasLegalizacionTotal);
            parametros.put("estudiantesProblemaLegalizacionTotal", estudiantesProblemaLegalizacionTotal);
            parametros.put("numPrediosConAguaTotal", numPrediosConAguaTotal);
            parametros.put("numPrediosConEETotal", numPrediosConEETotal);
            parametros.put("numPrediosConGasTotal", numPrediosConGasTotal);
            parametros.put("numPrediosConReciclajeTotal", numPrediosConReciclajeTotal);
            parametros.put("numPrediosConAlcantarilladoTotal", numPrediosConAlcantarilladoTotal);
            parametros.put("numPrediosConPluvialTotal", numPrediosConPluvialTotal);
            parametros.put("numPrediosConInternetTotal", numPrediosConInternetTotal);
            parametros.put("numPrediosConTelefonoTotal", numPrediosConTelefonoTotal);
            parametros.put("promedioConsumoAguaTotal", promedioConsumoAguaTotal);
            parametros.put("numPrediosCumplenConsumoAguaTotal", numPrediosCumplenConsumoAguaTotal);
            parametros.put("promedioConsumoEETotal", promedioConsumoEETotal);
            parametros.put("numPrediosCumplenConsumoEETotal", numPrediosCumplenConsumoEETotal);
            parametros.put("numPrediosVulnerablesTotal", numPrediosVulnerablesTotal);
            parametros.put("numPrediosEstrucCriticaTotal", numPrediosEstrucCriticaTotal);
            parametros.put("m2PrediosEstadoEstrucCriticoTotal", m2PrediosEstadoEstrucCriticoTotal);
            parametros.put("numPrediosRutaEvacCriticaTotal", numPrediosRutaEvacCriticaTotal);
            parametros.put("numPrediosSenalizaEvacCriticaTotal", numPrediosSenalizaEvacCriticaTotal);
            parametros.put("numPrediosNoRactivoIncendioTotal", numPrediosNoRactivoIncendioTotal);
            parametros.put("m2RequeridosTotal", m2RequeridosTotal);
            parametros.put("construiblePrimerPisoTotal", construiblePrimerPisoTotal);
            parametros.put("construibleAlturaTotal", construibleAlturaTotal);
            parametros.put("construibleTotalTotal", construibleTotalTotal);
            parametros.put("m2AmpliarCoberturaTotal", m2AmpliarCoberturaTotal);
            parametros.put("alumnosNuevosInfraestructuraTotal", alumnosNuevosInfraestructuraTotal);
            parametros.put("cuposTotalTotal", cuposTotalTotal);
            parametros.put("totalCobertura", totalCobertura);

            ReporteInformeGeneralDPA reporteInformeGeneralDPA = new ReporteInformeGeneralDPA();
            reporteInformeGeneralDPA.setListaDatosDPAs(listaDatosDPAs);

            List list = new ArrayList();
            list.add(reporteInformeGeneralDPA);

            parametros.put(JRParameter.REPORT_RESOURCE_BUNDLE, ResourceBundle.getBundle("etiquetas", FacesContext.getCurrentInstance().getViewRoot().getLocale()));

            stream = UtilExportar.getInformeGeneral("informeGeneral/dpa", "InformeGeneralDPA", parametros, list);

        } catch (Exception e) {
            UtilOut.println("ERROR en imprimirPdfEstablecimiento: " + e.getMessage());
            mostrarMensajeError(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"), e.getMessage());
            ManejadorErrores.ingresarExcepcionEnLog(e, InformeGeneralBean.class);
            //UtilOut.println(e);
        }

        return new DefaultStreamedContent(stream, "application/x-download", "InformeGeneralDPA.pdf");
    }

    public Map<String, Object> obtenerParametrosPdfDPATotales(Long nivelConsulta, Long id, String cod) {
        parametros = new HashMap<String, Object>();
        try {

            List<String> preCodes = new ArrayList<String>();
            List<String> priCodes = new ArrayList<String>();
            List<String> secCodes = new ArrayList<String>();
            List<String> basCodes = new ArrayList<String>();
            List<String> medCodes = new ArrayList<String>();
            List<String[]> nivelesEnsenanza = facInformeGeneral.getEquivalentes("nivel_educativo");
            for (Iterator<String[]> it = nivelesEnsenanza.iterator(); it.hasNext();) {
                Object[] n = it.next();
                if (n[0].equals("NE_PRE")) {
                    preCodes.add((String) n[1]);
                } else if (n[0].equals("NE_PRI")) {
                    priCodes.add((String) n[1]);
                } else if (n[0].equals("NE_SEC")) {
                    secCodes.add((String) n[1]);
                } else if (n[0].equals("NE_BAS")) {
                    basCodes.add((String) n[1]);
                } else if (n[0].equals("NE_MED")) {
                    medCodes.add((String) n[1]);
                }
            }

            //TOTALES
            int totalNumPrediosConRiesgos = 0;
            int totalPoblacionAfectadaRiesgos = 0;
            int totalPoblacionTotal = 0;
            BigDecimal totalM2PrediosEstadoEstrucCritico = new BigDecimal("0");
            int totalIluminacionValor1 = 0;
            int totalIluminacionValor2 = 0;
            int totalIluminacionValor3 = 0;
            int totalIluminacionValor4 = 0;
            int totalVentilacionValor1 = 0;
            int totalVentilacionValor2 = 0;
            int totalVentilacionValor3 = 0;
            int totalVentilacionValor4 = 0;
            int totalAcusticaValor1 = 0;
            int totalAcusticaValor2 = 0;
            int totalAcusticaValor3 = 0;
            int totalAcusticaValor4 = 0;
            int totalDuchasValor1 = 0;
            int totalDuchasValor2 = 0;
            int totalDuchasValor3 = 0;
            int totalDuchasValor4 = 0;
            int totalLavamanosValor1 = 0;
            int totalLavamanosValor2 = 0;
            int totalLavamanosValor3 = 0;
            int totalLavamanosValor4 = 0;
            int totalOrinalesValor1 = 0;
            int totalOrinalesValor2 = 0;
            int totalOrinalesValor3 = 0;
            int totalOrinalesValor4 = 0;
            int totalInodorosValor1 = 0;
            int totalInodorosValor2 = 0;
            int totalInodorosValor3 = 0;
            int totalInodorosValor4 = 0;
            int totalNumIluminacion = 0;
            int totalNumVentilacion = 0;
            int totalNumAcustica = 0;
            int totalNumDuchas = 0;
            int totalNumLavamanos = 0;
            int totalNumOrinales = 0;
            int totalNumInodoros = 0;
            int totalMurosFachadasValor1 = 0;
            int totalMurosFachadasValor2 = 0;
            int totalMurosFachadasValor3 = 0;
            int totalMurosFachadasValor4 = 0;
            int totalAcabadosFachadasValor1 = 0;
            int totalAcabadosFachadasValor2 = 0;
            int totalAcabadosFachadasValor3 = 0;
            int totalAcabadosFachadasValor4 = 0;
            int totalCubiertasValor1 = 0;
            int totalCubiertasValor2 = 0;
            int totalCubiertasValor3 = 0;
            int totalCubiertasValor4 = 0;
            int totalPre = 0, totalPrimaria = 0, totalSecundaria = 0, totalBasica = 0, totalMedia = 0;
            int totalAislamientoTermicoPisos = 0;
            int totalAislamientoTermicoMuros = 0;
            int totalAislamientoTermicoVentanas = 0;
            int totalAislamientoTermicoCielos = 0;
            int totalAislamientoTermicoOtros = 0;

            Nivel nivel = facEstDPA.consultarNivel(id);
            Nivel nivel0 = this.getIdNivel0(nivel);

            if (nivel.getNivelesList().isEmpty() && !nivel.getEstablecimientoList().isEmpty()) {//si es municipio
                nivel.getNivelesList().add(nivel);
            }
            for (Nivel n : nivel.getNivelesList()) {

                int numSedes = 0;
                int numEstablecimientos = 0;
                int numPredios = 0;

                List<Nivel> municipios = new ArrayList();
                if (n.getEstablecimientoList().isEmpty()) {
                    for (Nivel n1 : n.getNivelesList()) {
                        if (n1.getEstablecimientoList().isEmpty()) {
                            for (Nivel n2 : n1.getNivelesList()) {
                                if (n2.getEstablecimientoList().isEmpty()) {
                                    for (Nivel n3 : n2.getNivelesList()) {
                                        if (n3.getEstablecimientoList().isEmpty()) {
                                            for (Nivel n4 : n3.getNivelesList()) {
                                                if (n4.getEstablecimientoList().isEmpty()) {
                                                    for (Nivel n5 : n4.getNivelesList()) {
                                                        municipios.add(n5);
                                                        break;
                                                    }
                                                } else {
                                                    municipios.add(n4);
                                                    break;
                                                }
                                            }
                                        } else {
                                            municipios.add(n3);
                                            break;
                                        }
                                    }
                                } else {
                                    municipios.add(n2);
                                    break;
                                }
                            }
                        } else {
                            municipios.add(n1);
                            break;
                        }
                    }
                } else {
                    municipios.add(n);
                }

                for (Nivel muni : municipios) {
                    numEstablecimientos += muni.getEstablecimientoList().size();
                    for (Establecimiento est : muni.getEstablecimientoList()) {
                        numSedes += est.getSedes().size();
                        for (Sede s : est.getSedes()) {
                            numPredios += s.getPredios().size();
                        }
                        List<BigDecimal> listaInstrumentosDigitados = facInstrumentos.obtenerInstrumentosDigPorEstablecimiento(est.getId());
                        TipologiaValorNombre tv;
                        for (BigDecimal idInstrumentoDig : listaInstrumentosDigitados) {
                            InstrumentoDig instrumentoDig = facInstrumentos.consultarInstrumentoPorCodigo(idInstrumentoDig.longValue());
                            Map<String, Object> datos = null;
                            datos = obtenerRespuestas(instrumentoDig);
                            //INICIO COBERTURAS
                            String[] respuestasNivelEnsenanza = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10"};
                            for (String resp : respuestasNivelEnsenanza) {
                                if (preCodes.contains(datos.get("RESP_033_" + resp))) {
                                    if (datos.containsKey("RESP_035_" + resp) && datos.get("RESP_035_" + resp) != null) {
                                        totalPre += (new Integer(datos.get("RESP_035_" + resp) + ""));
                                    }
                                } else if (datos.get("RESP_035_" + resp) != null && priCodes.contains(datos.get("RESP_033_" + resp))) {
                                    totalPrimaria += (new Integer(datos.get("RESP_035_" + resp) + ""));
                                } else if (datos.get("RESP_035_" + resp) != null && secCodes.contains(datos.get("RESP_033_" + resp))) {
                                    totalSecundaria += (new Integer(datos.get("RESP_035_" + resp) + ""));
                                } else if (datos.get("RESP_035_" + resp) != null && basCodes.contains(datos.get("RESP_033_" + resp))) {
                                    totalBasica += (new Integer(datos.get("RESP_035_" + resp) + ""));
                                } else if (datos.get("RESP_035_" + resp) != null && medCodes.contains(datos.get("RESP_033_" + resp))) {
                                    totalMedia += (new Integer(datos.get("RESP_035_" + resp) + ""));
                                }

                                if (preCodes.contains(datos.get("RESP_199_" + resp))) {
                                    if (datos.containsKey("RESP_201_" + resp) && datos.get("RESP_201_" + resp) != null) {
                                        totalPre += (new Integer(datos.get("RESP_201_" + resp) + ""));
                                    }
                                } else if (datos.get("RESP_201_" + resp) != null && priCodes.contains(datos.get("RESP_199_" + resp))) {
                                    totalPrimaria += (new Integer(datos.get("RESP_201_" + resp) + ""));
                                } else if (datos.get("RESP_201_" + resp) != null && secCodes.contains(datos.get("RESP_199_" + resp))) {
                                    totalSecundaria += (new Integer(datos.get("RESP_201_" + resp) + ""));
                                } else if (datos.get("RESP_201_" + resp) != null && basCodes.contains(datos.get("RESP_199_" + resp))) {
                                    totalBasica += (new Integer(datos.get("RESP_201_" + resp) + ""));
                                } else if (datos.get("RESP_201_" + resp) != null && medCodes.contains(datos.get("RESP_199_" + resp))) {
                                    totalMedia += (new Integer(datos.get("RESP_201_" + resp) + ""));
                                }
                                //Se les da el valor de la tipología para que se muestre el texto en el reporte
                                if (datos.get("RESP_033_" + resp) != null) {
                                    tv = facParam.consultarValorTipologia(respuestaTipologia.get("RESP_033_" + resp), datos.get("RESP_033_" + resp).toString(), getUsuarioSesion().getUsuario().getIdioma().getId());
                                    datos.put(resp, tv != null ? tv.getNombre() : "");
                                } else if (datos.get("RESP_199_" + resp) != null) {
                                    tv = facParam.consultarValorTipologia(respuestaTipologia.get("RESP_199_" + resp), datos.get("RESP_199_" + resp).toString(), getUsuarioSesion().getUsuario().getIdioma().getId());
                                    datos.put(resp, tv != null ? tv.getNombre() : "");
                                }
                            }
                            //FIN COBERTURAS

                            //INICIO RIESGOS
                            //INICIO SEGURIDAD ESTRUCCTURAL Y ACC INTERNA
                            List<String> indicadores = new ArrayList<String>();
                            indicadores.add(ParamIndicador.IND_TOTAL_POBLACION.name());
                            indicadores.add(ParamIndicador.IND_SEGURIDAD_ESTADO_ESTRUCTURA.name());
                            indicadores.add(ParamIndicador.IND_AREA_REAL_PREDIO.name());
                            List<Object[]> ind = facParam.consultarIndicadores(indicadores, ParamNivelConsul.PREDIO.getNivel(), idInstrumentoDig.longValue());
                            totalPoblacionTotal += new Integer(ind.get(0)[0] == null ? "0" : (String) ind.get(0)[0]);
                            if (datos.containsKey("RESP_048_01") || datos.containsKey("RESP_048_03") || datos.containsKey("RESP_048_05")
                                    || datos.containsKey("RESP_050_01") || datos.containsKey("RESP_050_03") || datos.containsKey("RESP_050_05")) {
                                totalPoblacionAfectadaRiesgos += new Integer(ind.get(0)[0] == null ? "0" : (String) ind.get(0)[0]);
                                totalNumPrediosConRiesgos++;
                                if ("CRÍTICO".equals((String) ind.get(0)[1])) {
                                    totalM2PrediosEstadoEstrucCritico = totalM2PrediosEstadoEstrucCritico.add(new BigDecimal((String) ind.get(0)[2]));
                                }
                            }
                            //FIN SEGURIDAD ESTRUCCTURAL Y ACC INTERNA
                            //FIN RIESGOS

                            //INICIO EDIFICIOS Y ESPACIOS
                            List edif = obtenerObjetosEdificios(idInstrumentoDig.longValue());
                            //List<ControlVigilancia> listaControlVigilancia=(List)edif.get(0);
                            //List<SeguridadAccesibilidad> listaSeguridadAccesibilidad=(List)edif.get(1);
                            HashMap datosMaterial = (HashMap) edif.get(2);

                            totalMurosFachadasValor1 += (Integer) datosMaterial.get("murosFachadasValor1");
                            totalMurosFachadasValor2 += (Integer) datosMaterial.get("murosFachadasValor2");
                            totalMurosFachadasValor3 += (Integer) datosMaterial.get("murosFachadasValor3");
                            totalMurosFachadasValor4 += (Integer) datosMaterial.get("murosFachadasValor4");

                            totalAcabadosFachadasValor1 += (Integer) datosMaterial.get("acabadosFachadasValor1");
                            totalAcabadosFachadasValor2 += (Integer) datosMaterial.get("acabadosFachadasValor2");
                            totalAcabadosFachadasValor3 += (Integer) datosMaterial.get("acabadosFachadasValor3");
                            totalAcabadosFachadasValor4 += (Integer) datosMaterial.get("acabadosFachadasValor4");

                            totalCubiertasValor1 += (Integer) datosMaterial.get("cubiertasValor1");
                            totalCubiertasValor2 += (Integer) datosMaterial.get("cubiertasValor2");
                            totalCubiertasValor3 += (Integer) datosMaterial.get("cubiertasValor3");
                            totalCubiertasValor4 += (Integer) datosMaterial.get("cubiertasValor4");

                            HashMap datosEsp = obtenerObjetosEspaciosEscolaresPredios(idInstrumentoDig.longValue());

                            totalIluminacionValor1 += (Integer) datosEsp.get("iluminacionValor1");
                            totalIluminacionValor2 += (Integer) datosEsp.get("iluminacionValor2");
                            totalIluminacionValor3 += (Integer) datosEsp.get("iluminacionValor3");
                            totalIluminacionValor4 += (Integer) datosEsp.get("iluminacionValor4");
                            totalNumIluminacion += (Integer) datosEsp.get("numIluminacion");
                            totalVentilacionValor1 += (Integer) datosEsp.get("ventilacionValor1");
                            totalVentilacionValor2 += (Integer) datosEsp.get("ventilacionValor2");
                            totalVentilacionValor3 += (Integer) datosEsp.get("ventilacionValor3");
                            totalVentilacionValor4 += (Integer) datosEsp.get("ventilacionValor4");
                            totalNumVentilacion += (Integer) datosEsp.get("numVentilacion");
                            totalAcusticaValor1 += (Integer) datosEsp.get("acusticaValor1");
                            totalAcusticaValor2 += (Integer) datosEsp.get("acusticaValor2");
                            totalAcusticaValor3 += (Integer) datosEsp.get("acusticaValor3");
                            totalAcusticaValor4 += (Integer) datosEsp.get("acusticaValor4");
                            totalNumAcustica += (Integer) datosEsp.get("numAcustica");
                            totalNumDuchas += (Integer) datosEsp.get("numDuchas");
                            totalDuchasValor1 += (Integer) datosEsp.get("duchasValor1");
                            totalDuchasValor2 += (Integer) datosEsp.get("duchasValor2");
                            totalDuchasValor3 += (Integer) datosEsp.get("duchasValor3");
                            totalDuchasValor4 += (Integer) datosEsp.get("duchasValor4");
                            totalNumLavamanos += (Integer) datosEsp.get("numLavamanos");
                            totalLavamanosValor1 += (Integer) datosEsp.get("lavamanosValor1");
                            totalLavamanosValor2 += (Integer) datosEsp.get("lavamanosValor2");
                            totalLavamanosValor3 += (Integer) datosEsp.get("lavamanosValor3");
                            totalLavamanosValor4 += (Integer) datosEsp.get("lavamanosValor4");
                            totalNumOrinales += (Integer) datosEsp.get("numOrinales");
                            totalOrinalesValor1 += (Integer) datosEsp.get("orinalesValor1");
                            totalOrinalesValor2 += (Integer) datosEsp.get("orinalesValor2");
                            totalOrinalesValor3 += (Integer) datosEsp.get("orinalesValor3");
                            totalOrinalesValor4 += (Integer) datosEsp.get("orinalesValor4");
                            totalNumInodoros += (Integer) datosEsp.get("numInodoros");
                            totalInodorosValor1 += (Integer) datosEsp.get("inodorosValor1");
                            totalInodorosValor2 += (Integer) datosEsp.get("inodorosValor2");
                            totalInodorosValor3 += (Integer) datosEsp.get("inodorosValor3");
                            totalInodorosValor4 += (Integer) datosEsp.get("inodorosValor4");
                            totalAislamientoTermicoPisos += (Integer) datosEsp.get("aislamientoTermicoPisos");
                            totalAislamientoTermicoMuros += (Integer) datosEsp.get("aislamientoTermicoMuros");
                            totalAislamientoTermicoVentanas += (Integer) datosEsp.get("aislamientoTermicoVentanas");
                            totalAislamientoTermicoCielos += (Integer) datosEsp.get("aislamientoTermicoCielos");
                            totalAislamientoTermicoOtros += (Integer) datosEsp.get("aislamientoTermicoOtros");
                            //FIN EDIFICIOS Y ESPACIOS

                        }//FIN FOR listaInstrumentosDigitados

                    }//FIN FOR hijo.getEstablecimientoList()

                }//FIN FOR municipios

                parametros.put("numSedes", numSedes + (parametros.get("numSedes") != null ? (Integer) parametros.get("numSedes") : 0));
                parametros.put("numPredios", numPredios + (parametros.get("numPredios") != null ? (Integer) parametros.get("numPredios") : 0));
                parametros.put("numEstablecimientos", numEstablecimientos + (parametros.get("numEstablecimientos") != null ? (Integer) parametros.get("numEstablecimientos") : 0));
            }// FIN FOR nivel.getNivelesList()

            if (nivelConsulta > ParamNivelConsul.CERO.getNivel() && !parametros.containsKey("region")) {
                if (nivelConsulta > ParamNivelConsul.DOS.getNivel()) {
                    parametros.put("municipio", nivel.getDescripcion());
                    parametros.put("departamento", nivel.getPadre().getDescripcion());
                    parametros.put("region", nivel.getPadre().getPadre().getDescripcion());
                } else if (nivelConsulta > ParamNivelConsul.UNO.getNivel()) {
                    parametros.put("region", nivel.getPadre().getDescripcion());
                    parametros.put("departamento", nivel.getDescripcion());
                } else if (nivelConsulta.equals(ParamNivelConsul.UNO.getNivel())) {
                    parametros.put("region", nivel.getDescripcion());
                }
            }

            parametros.put("CondMat1", Utilidades.obtenerMensaje("tipologias.condicion." + "EMPMF_1"));
            parametros.put("CondMat2", Utilidades.obtenerMensaje("tipologias.condicion." + "EMPMF_2"));
            parametros.put("CondMat3", Utilidades.obtenerMensaje("tipologias.condicion." + "EMPMF_3"));
            parametros.put("CondMat4", Utilidades.obtenerMensaje("tipologias.condicion." + "EMPMF_4"));

            this.anadirCalificaciones(id, nivelConsulta);

            DateFormat df = DateFormat.getDateInstance(DateFormat.LONG);
            parametros.put("fechaReporte", df.format(new Date()));

            String periodoCorte = facParam.consultarPeriodoIndicadores();
            if (periodoCorte != null) {
                Calendar calAux = Calendar.getInstance();
                calAux.set(new Integer(periodoCorte.substring(0, 4)), new Integer(periodoCorte.substring(4, 6)), 1);
                SimpleDateFormat format = new SimpleDateFormat("MMMM yyyy");
                String fecha = format.format(calAux.getTime());
                parametros.put("fechaCorte", fecha.substring(0, 1).toUpperCase() + fecha.substring(1, fecha.length()));
            }

            List<String> indicadores = new ArrayList<String>();
            indicadores.add(ParamIndicador.IND_AREA_REAL_PREDIO.name());
            indicadores.add(ParamIndicador.IND_TOTAL_AREA_CONSTRUCCION_PREDIO.name());
            indicadores.add(ParamIndicador.IND_AMPLIACION_AREA_CONSTRUCCION_PRIMER_PISO.name());
            indicadores.add(ParamIndicador.IND_ACCESIBILIDAD_NUMERO_PREDIOS_CONDICION_INADECUADA.name());
            indicadores.add(ParamIndicador.IND_ACCESIBILIDAD_POBLACION_AFECTADA.name());
            indicadores.add(ParamIndicador.IND_PROPIEDAD_TOTAL_PREDIO_PROBLEMA_LEGALIZACION.name());
            indicadores.add(ParamIndicador.IND_PROPIEDAD_TOTAL_ESTUDIANTES_PREDIO_PROBLEMA_LEGALIZACION.name());
            indicadores.add(ParamIndicador.IND_SERVICIO_NUM_PREDIOS_ANALISIS_CONSUMO_PROMEDIO_AGUA.name());
            indicadores.add(ParamIndicador.IND_SERVICIO_NUM_PREDIOS_ANALISIS_CONSUMO_PROMEDIO_ENERGIA_ELECTRICA.name());
            indicadores.add(ParamIndicador.IND_AMPLIACION_M2_REQUERIDOS_PREDIO.name());
            indicadores.add(ParamIndicador.IND_AMPLIACION_POSIBILIDAD_CONSTRUCCION_PRIMER_PISO.name());
            indicadores.add(ParamIndicador.IND_AMPLIACION_POSIBILIDAD_CONSTRUCCION_ALTURA.name());
            indicadores.add(ParamIndicador.IND_AMPLIACION_POSIBILIDAD_CONSTRUCCION_TOTAL.name());
            indicadores.add(ParamIndicador.IND_SERVICIO_CONSUMO_PROMEDIO_AGUA.name());
            indicadores.add(ParamIndicador.IND_SERVICIO_CONSUMO_PROMEDIO_ENERGIA_ELECTRICA.name());
            indicadores.add(ParamIndicador.IND_AMPLIACION_IDONEIDAD_PREDIO.name());
            indicadores.add(ParamIndicador.IND_AMPLIACION_M2_AMPLIAR_COBERTURA.name());
            indicadores.add(ParamIndicador.IND_AMPLIACION_ALUMNOS_NUEVOS_INFRAESTRUCTURA.name());
            //indicadores.add(ParamIndicador..name());
            List<Object[]> areas = facParam.consultarIndicadores(indicadores, nivelConsulta, nivel.getId());
            if (!areas.isEmpty()) {
                int i = 0;
                parametros.put("totalSuperficie", new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalConstruccion", new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalConstrucPrimerPiso", new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalPrediosAccesibilidadInadecuada", new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalAccesibilidadPoblacionAfectada", new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalPrediosProblemasLegalizacion", new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalEstudiantesProblemaLegalizacion", new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalNumPrediosCumplenConsumoAgua", new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalNumPrediosCumplenConsumoEE", new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalM2Requeridos", new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalConstruiblePrimerPiso", new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalConstruibleAltura", new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalConstruibleTotal", new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalPromedioConsumoAgua", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? new BigDecimal(0) : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                i++;
                parametros.put("totalPromedioConsumoEE", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? new BigDecimal(0) : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP));
                i++;
                parametros.put("idoneidad", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? null : Utilidades.obtenerMensaje("aplicacion.general." + (new Double(areas.get(0)[i] + "") > new Double(100) ? "cumple" : "noCumple")));
                i++;
                parametros.put("m2AmpliarCobertura", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? null : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP) + "");
                i++;
                parametros.put("alumnosNuevosInfraestructura", UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? null : new BigDecimal((String) areas.get(0)[i]).setScale(2, RoundingMode.HALF_UP) + "");
                i++;
            }

            //Indicadores de unidades funcionales
            List<String[]> indBusqUnidadesFun = new ArrayList<String[]>();
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_CONTROL_VIGILANCIA_NUM_PREDIOS_SEGUN_CONDICION_VULNERABILIDAD.name(), Vulnerabilidad.A.getNombre()});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_SEGURIDAD_NUM_PREDIOS_SEGUN_ESTADO_ESTRUCTURA.name(), SiNo.Si.name()});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_SEGURIDAD_NUM_PREDIOS_SEGUN_ANALISIS_RUTA_EVALUACION.name(), SiNo.Si.name()});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_SEGURIDAD_NUM_PREDIOS_SEGUN_SENALIZACION_EVACUACION.name(), SiNo.Si.name()});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_SEGURIDAD_NUM_PREDIOS_SEGUN_REACTIVO_CONTRA_INCENDIO.name(), SiNo.No.name()});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_NUMERO_ESPACIOS_SEGUN_CONDICION_PISO.name(), CalificacionCondicion.CUATRO.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_NUMERO_ESPACIOS_SEGUN_CONDICION_PISO.name(), CalificacionCondicion.TRES.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_NUMERO_ESPACIOS_SEGUN_CONDICION_PISO.name(), CalificacionCondicion.DOS.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_NUMERO_ESPACIOS_SEGUN_CONDICION_PISO.name(), CalificacionCondicion.UNO.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_NUMERO_ESPACIOS_SEGUN_CONDICION_MURO.name(), CalificacionCondicion.CUATRO.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_NUMERO_ESPACIOS_SEGUN_CONDICION_MURO.name(), CalificacionCondicion.TRES.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_NUMERO_ESPACIOS_SEGUN_CONDICION_MURO.name(), CalificacionCondicion.DOS.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_NUMERO_ESPACIOS_SEGUN_CONDICION_MURO.name(), CalificacionCondicion.UNO.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_NUMERO_ESPACIOS_SEGUN_CONDICION_ACABADO_MURO.name(), CalificacionCondicion.CUATRO.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_NUMERO_ESPACIOS_SEGUN_CONDICION_ACABADO_MURO.name(), CalificacionCondicion.TRES.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_NUMERO_ESPACIOS_SEGUN_CONDICION_ACABADO_MURO.name(), CalificacionCondicion.DOS.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_NUMERO_ESPACIOS_SEGUN_CONDICION_ACABADO_MURO.name(), CalificacionCondicion.UNO.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_NUMERO_ESPACIOS_SEGUN_CONDICION_VENTANA.name(), CalificacionCondicion.CUATRO.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_NUMERO_ESPACIOS_SEGUN_CONDICION_VENTANA.name(), CalificacionCondicion.TRES.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_NUMERO_ESPACIOS_SEGUN_CONDICION_VENTANA.name(), CalificacionCondicion.DOS.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_NUMERO_ESPACIOS_SEGUN_CONDICION_VENTANA.name(), CalificacionCondicion.UNO.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_NUMERO_ESPACIOS_SEGUN_CONDICION_PUERTA.name(), CalificacionCondicion.CUATRO.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_NUMERO_ESPACIOS_SEGUN_CONDICION_PUERTA.name(), CalificacionCondicion.TRES.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_NUMERO_ESPACIOS_SEGUN_CONDICION_PUERTA.name(), CalificacionCondicion.DOS.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_NUMERO_ESPACIOS_SEGUN_CONDICION_PUERTA.name(), CalificacionCondicion.UNO.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_NUMERO_ESPACIOS_SEGUN_CONDICION_TECHO.name(), CalificacionCondicion.CUATRO.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_NUMERO_ESPACIOS_SEGUN_CONDICION_TECHO.name(), CalificacionCondicion.TRES.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_NUMERO_ESPACIOS_SEGUN_CONDICION_TECHO.name(), CalificacionCondicion.DOS.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_NUMERO_ESPACIOS_SEGUN_CONDICION_TECHO.name(), CalificacionCondicion.UNO.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_PISO.name(), CalificacionCondicion.CUATRO.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_PISO.name(), CalificacionCondicion.TRES.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_PISO.name(), CalificacionCondicion.DOS.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_PISO.name(), CalificacionCondicion.UNO.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_MURO.name(), CalificacionCondicion.CUATRO.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_MURO.name(), CalificacionCondicion.TRES.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_MURO.name(), CalificacionCondicion.DOS.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_MURO.name(), CalificacionCondicion.UNO.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_ACABADO_MURO.name(), CalificacionCondicion.CUATRO.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_ACABADO_MURO.name(), CalificacionCondicion.TRES.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_ACABADO_MURO.name(), CalificacionCondicion.DOS.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_ACABADO_MURO.name(), CalificacionCondicion.UNO.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_TECHO.name(), CalificacionCondicion.CUATRO.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_TECHO.name(), CalificacionCondicion.TRES.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_TECHO.name(), CalificacionCondicion.DOS.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_TECHO.name(), CalificacionCondicion.UNO.getValor() + ""});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_SERVICIO_NUM_PREDIOS_DISPONIBILIDAD_AGUA.name(), SiNo.Si.name().toUpperCase()});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_SERVICIO_NUM_PREDIOS_DISPONIBILIDAD_ENERGIA_ELECTRICA.name(), SiNo.Si.name().toUpperCase()});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_SERVICIO_NUM_PREDIOS_DISPONIBILIDAD_GAS.name(), SiNo.Si.name().toUpperCase()});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_SERVICIO_NUM_PREDIOS_DISPONIBILIDAD_SISTEMA_RECICLAJE.name(), SiNo.Si.name().toUpperCase()});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_SERVICIO_NUM_PREDIOS_DISPONIBILIDAD_RED_ALCANTARILLADO.name(), SiNo.Si.name().toUpperCase()});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_SERVICIO_NUM_PREDIOS_DISPONIBILIDAD_RED_PLUVIAL.name(), SiNo.Si.name().toUpperCase()});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_SERVICIO_NUM_PREDIOS_DISPONIBILIDAD_INTERNET.name(), SiNo.Si.name().toUpperCase()});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_SERVICIO_NUM_PREDIOS_DISPONIBILIDAD_TELEFONO.name(), SiNo.Si.name().toUpperCase()});

            areas = facParam.consultarIndicadoresUnidadesFuncionales(indBusqUnidadesFun, nivelConsulta, nivel.getId());
            int i = 0;
            if (!areas.isEmpty()) {
                parametros.put("totalNumPrediosVulnerables", new Integer(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalNumPrediosEstrucCritica", new Integer(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalNumPrediosRutaEvacCritica", new Integer(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalNumPrediosSenalizaEvacCritica", new Integer(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalNumPrediosNoRactivoIncendio", new Integer(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalNumPisosCond4", new Integer(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalNumPisosCond3", new Integer(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalNumPisosCond2", new Integer(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalNumPisosCond1", new Integer(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalNumMurosCond4", new Integer(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalNumMurosCond3", new Integer(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalNumMurosCond2", new Integer(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalNumMurosCond1", new Integer(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalNumAcabadosMuroCond4", new Integer(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalNumAcabadosMuroCond3", new Integer(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalNumAcabadosMuroCond2", new Integer(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalNumAcabadosMuroCond1", new Integer(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalNumVentanasCond4", new Integer(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalNumVentanasCond3", new Integer(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalNumVentanasCond2", new Integer(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalNumVentanasCond1", new Integer(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalNumPuertasCond4", new Integer(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalNumPuertasCond3", new Integer(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalNumPuertasCond2", new Integer(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalNumPuertasCond1", new Integer(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalNumCieloRasosCond4", new Integer(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalNumCieloRasosCond3", new Integer(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalNumCieloRasosCond2", new Integer(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalNumCieloRasosCond1", new Integer(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalAreaPisosCond4", new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalAreaPisosCond3", new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalAreaPisosCond2", new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalAreaPisosCond1", new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalAreaMurosCond4", new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalAreaMurosCond3", new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalAreaMurosCond2", new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalAreaMurosCond1", new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalAreaAcabadosMuroCond4", new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalAreaAcabadosMuroCond3", new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalAreaAcabadosMuroCond2", new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalAreaAcabadosMuroCond1", new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalAreaCieloRasosCond4", new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalAreaCieloRasosCond3", new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalAreaCieloRasosCond2", new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalAreaCieloRasosCond1", new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalNumPrediosConAgua", new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalNumPrediosConEE", new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalNumPrediosConGas", new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalNumPrediosConReciclaje", new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalNumPrediosConAlcantarillado", new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalNumPrediosConPluvial", new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalNumPrediosConInternet", new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;
                parametros.put("totalNumPrediosConTelefono", new BigDecimal(UtilCadena.isNullOVacio((String) areas.get(0)[i]) ? "0" : (String) areas.get(0)[i]));
                i++;

            }

            parametros.putAll(this.obtenerUnidadesFuncionales(nivel.getId(), nivelConsulta));
            parametros.put("totalNumPrediosConRiesgos", totalNumPrediosConRiesgos);
            parametros.put("totalPoblacionAfectadaRiesgos", totalPoblacionAfectadaRiesgos);
            if (totalPoblacionTotal > 0) {
                parametros.put("totalPoblacionTotal", totalPoblacionTotal);
            }
            parametros.put("totalM2PrediosEstadoEstrucCritico", totalM2PrediosEstadoEstrucCritico);
            parametros.put("totalIluminacionValor1", totalIluminacionValor1);
            parametros.put("totalIluminacionValor2", totalIluminacionValor2);
            parametros.put("totalIluminacionValor3", totalIluminacionValor3);
            parametros.put("totalIluminacionValor4", totalIluminacionValor4);
            parametros.put("totalVentilacionValor1", totalVentilacionValor1);
            parametros.put("totalVentilacionValor2", totalVentilacionValor2);
            parametros.put("totalVentilacionValor3", totalVentilacionValor3);
            parametros.put("totalVentilacionValor4", totalVentilacionValor4);
            parametros.put("totalAcusticaValor1", totalAcusticaValor1);
            parametros.put("totalAcusticaValor2", totalAcusticaValor2);
            parametros.put("totalAcusticaValor3", totalAcusticaValor3);
            parametros.put("totalAcusticaValor4", totalAcusticaValor4);
            parametros.put("totalDuchasValor1", totalDuchasValor1);
            parametros.put("totalDuchasValor2", totalDuchasValor2);
            parametros.put("totalDuchasValor3", totalDuchasValor3);
            parametros.put("totalDuchasValor4", totalDuchasValor4);
            parametros.put("totalLavamanosValor1", totalLavamanosValor1);
            parametros.put("totalLavamanosValor2", totalLavamanosValor2);
            parametros.put("totalLavamanosValor3", totalLavamanosValor3);
            parametros.put("totalLavamanosValor4", totalLavamanosValor4);
            parametros.put("totalOrinalesValor1", totalOrinalesValor1);
            parametros.put("totalOrinalesValor2", totalOrinalesValor2);
            parametros.put("totalOrinalesValor3", totalOrinalesValor3);
            parametros.put("totalOrinalesValor4", totalOrinalesValor4);
            parametros.put("totalInodorosValor1", totalInodorosValor1);
            parametros.put("totalInodorosValor2", totalInodorosValor2);
            parametros.put("totalInodorosValor3", totalInodorosValor3);
            parametros.put("totalInodorosValor4", totalInodorosValor4);
            parametros.put("totalNumIluminacion", totalNumIluminacion);
            parametros.put("totalNumVentilacion", totalNumVentilacion);
            parametros.put("totalNumAcustica", totalNumAcustica);
            parametros.put("totalNumDuchas", totalNumDuchas);
            parametros.put("totalNumLavamanos", totalNumLavamanos);
            parametros.put("totalNumOrinales", totalNumOrinales);
            parametros.put("totalNumInodoros", totalNumInodoros);
            parametros.put("totalMurosFachadasValor1", totalMurosFachadasValor1);
            parametros.put("totalMurosFachadasValor2", totalMurosFachadasValor2);
            parametros.put("totalMurosFachadasValor3", totalMurosFachadasValor3);
            parametros.put("totalMurosFachadasValor4", totalMurosFachadasValor4);
            parametros.put("totalAcabadosFachadasValor1", totalAcabadosFachadasValor1);
            parametros.put("totalAcabadosFachadasValor2", totalAcabadosFachadasValor2);
            parametros.put("totalAcabadosFachadasValor3", totalAcabadosFachadasValor3);
            parametros.put("totalAcabadosFachadasValor4", totalAcabadosFachadasValor4);
            parametros.put("totalCubiertasValor1", totalCubiertasValor1);
            parametros.put("totalCubiertasValor2", totalCubiertasValor2);
            parametros.put("totalCubiertasValor3", totalCubiertasValor3);
            parametros.put("totalCubiertasValor4", totalCubiertasValor4);
            parametros.put("totalPre", totalPre);
            parametros.put("totalPrimaria", totalPrimaria);
            parametros.put("totalSecundaria", totalSecundaria);
            parametros.put("totalBasica", totalBasica);
            parametros.put("totalMedia", totalMedia);
            parametros.put("totalAislamientoTermicoPisos", totalAislamientoTermicoPisos);
            parametros.put("totalAislamientoTermicoMuros", totalAislamientoTermicoMuros);
            parametros.put("totalAislamientoTermicoVentanas", totalAislamientoTermicoVentanas);
            parametros.put("totalAislamientoTermicoCielos", totalAislamientoTermicoCielos);
            parametros.put("totalAislamientoTermicoOtros", totalAislamientoTermicoOtros);

            HashMap datosRedistribucion = this.obtenerRedistribucion(nivel, nivelConsulta, nivel0.getId());
            parametros.put("totalUtilizacion", datosRedistribucion.get("utilizacion") == null ? null : datosRedistribucion.get("utilizacion") + "");
            parametros.put("totalCuposTotal", datosRedistribucion.get("cuposTotal") == null ? null : datosRedistribucion.get("cuposTotal") + "");

        } catch (Exception e) {
            UtilOut.println("ERROR en obtenerParametrosPdfDPATotales: " + e.getMessage());
            mostrarMensajeError(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"), e.getMessage());
            //UtilOut.println(e);
            ManejadorErrores.ingresarExcepcionEnLog(e, InformeGeneralBean.class);
        }
        return parametros;
    }

    private StreamedContent imprimirPdfDPATotales(Long nivelConsulta, Long id, String cod) {

        InputStream stream = null;
        try {
            this.obtenerParametrosPdfDPATotales(nivelConsulta, id, cod);
            ReporteInformeGeneralDPA reporteInformeGeneralDPA = new ReporteInformeGeneralDPA();
            reporteInformeGeneralDPA.setListaDatosDPAs(new ArrayList<DatosDPA>());

            List list = new ArrayList();
            list.add(reporteInformeGeneralDPA);

            parametros.put(JRParameter.REPORT_RESOURCE_BUNDLE, ResourceBundle.getBundle("etiquetas", FacesContext.getCurrentInstance().getViewRoot().getLocale()));

            stream = UtilExportar.getInformeGeneral("informeGeneral/totales", "InformeGeneralTotales", parametros, list);

        } catch (Exception e) {
            UtilOut.println("ERROR en imprimirPdfEstablecimiento: " + e.getMessage());
            mostrarMensajeError(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"), e.getMessage());
            //UtilOut.println(e);
            ManejadorErrores.ingresarExcepcionEnLog(e, InformeGeneralBean.class);
        }

        return new DefaultStreamedContent(stream, "application/x-download", "InformeGeneralTotales.pdf");
    }

    public Map<String, Object> obtenerRespuestasDigitadas(Map<String, Object> respuestasMap, Long idDigInstrumento, List<String> respuestas) {

        List<Object[]> listaRespuesta = facBasica.obtenerRespuestasInstrumentoDig(idDigInstrumento, respuestas);

        for (Object[] fila : listaRespuesta) {
            int i = 0;
            respuestasMap.put((String) fila[i++], (String) fila[i++]);
        }
        return respuestasMap;
    }

    private void anadirCalificaciones(Long id, Long nivelAgrupamiento) {
        FiltroCalificacion filtro = new FiltroCalificacion();
        filtro.setNivelAgrupamiento(nivelAgrupamiento);
        filtro.setIdEntidad(id);
        List<Object[]> ponderaciones = facCalificacion.obtenerPonderaciones();
        HashMap hmPonderaciones = new HashMap();
        for (Object[] ponderacion : ponderaciones) {
            hmPonderaciones.put(ponderacion[0], ponderacion[1]);
        }

        List<Object[]> resultados = facCalificacion.consultarCalificacionesReporte(filtro);
        if (!resultados.isEmpty()) {
            BigDecimal suma = new BigDecimal("0");
            Object[] fila = resultados.get(0);
            int i = 0;
            //HashMap<String, String> hmAux=new HashMap<String, String>();
            if (ParamNivelConsul.PREDIO.getNivel().equals(nivelAgrupamiento)) {
                i++;//registro.setIdInstrumento((String)fila[i++]);
                i++;//registro.setIdDigInstrumento(new Long(fila[i++]+""));
                //hmAux.put(bundle.getString("aplicacion.general.codEst"),UtilCadena.isNullOVacio(fila[i])?"":(String)fila[i]);
                i++;
                //hmAux.put(bundle.getString("aplicacion.general.nomEst"),UtilCadena.isNullOVacio(fila[i])?"":(String)fila[i]);
                i++;
                //hmAux.put(bundle.getString("aplicacion.general.codSede"),UtilCadena.isNullOVacio(fila[i])?"":(String)fila[i]);
                i++;
                //hmAux.put(bundle.getString("aplicacion.general.nomSede"),UtilCadena.isNullOVacio(fila[i])?"":(String)fila[i]);
                i++;
                parametros.put("codPredio", UtilCadena.isNullOVacio(fila[i]) ? "" : (String) fila[i]);
                i++;
                parametros.put("nomPredio", UtilCadena.isNullOVacio(fila[i]) ? "" : (String) fila[i]);
                i++;
            } else if (nivelAgrupamiento.equals(ParamNivelConsul.CERO.getNivel())) {
                i++;//registro.setId(new Long(fila[i++]+""));
                i++;//registro.setCod(""+fila[i++]);                    
                parametros.put("nivel0", UtilCadena.isNullOVacio(fila[i]) ? "" : "" + fila[i]);
                i++;
            } else if (nivelAgrupamiento.equals(ParamNivelConsul.UNO.getNivel())) {
                i++;//registro.setId(new Long(fila[i++]+""));
                i++;//registro.setCod(""+fila[i++]);
                parametros.put("nivel1", UtilCadena.isNullOVacio(fila[i]) ? "" : "" + fila[i]);
                i++;
            } else if (nivelAgrupamiento.equals(ParamNivelConsul.DOS.getNivel())) {
                i++;//registro.setId(new Long(fila[i++]+""));
                i++;//registro.setCod(""+fila[i++]);
                parametros.put("nivel2", UtilCadena.isNullOVacio(fila[i]) ? "" : "" + fila[i]);
                i++;
            } else if (nivelAgrupamiento.equals(ParamNivelConsul.TRES.getNivel())) {
                i++;//registro.setId(new Long(fila[i++]+""));
                i++;//registro.setCod(""+fila[i++]);
                parametros.put("nivel3", UtilCadena.isNullOVacio(fila[i]) ? "" : "" + fila[i]);
                i++;
            } else if (nivelAgrupamiento.equals(ParamNivelConsul.CUATRO.getNivel())) {
                i++;//registro.setId(new Long(fila[i++]+""));
                i++;//registro.setCod(""+fila[i++]);
                parametros.put("nivel4", UtilCadena.isNullOVacio(fila[i]) ? "" : "" + fila[i]);
                i++;
            } else if (nivelAgrupamiento.equals(ParamNivelConsul.CINCO.getNivel())) {
                i++;//registro.setId(new Long(fila[i++]+""));
                i++;//registro.setCod(""+fila[i++]);
                parametros.put("nivel5", UtilCadena.isNullOVacio(fila[i]) ? "" : "" + fila[i]);
                i++;
            } else if (nivelAgrupamiento.equals(ParamNivelConsul.ESTABLECIMIENTO.getNivel())) {
                i++;//registro.setId(new Long(fila[i++]+""));
                parametros.put("codEst", UtilCadena.isNullOVacio(fila[i]) ? "" : (String) fila[i]);
                i++;
                parametros.put("nomEst", UtilCadena.isNullOVacio(fila[i]) ? "" : (String) fila[i]);
                i++;
            } else if (nivelAgrupamiento.equals(ParamNivelConsul.SEDE.getNivel())) {
                i++;//registro.setId(new Long(fila[i++]+""));
                parametros.put("codSede", UtilCadena.isNullOVacio(fila[i]) ? "" : (String) fila[i]);
                i++;
                parametros.put("nomSede", UtilCadena.isNullOVacio(fila[i]) ? "" : (String) fila[i]);
                i++;
            }

            suma = suma.add(fila[i] != null ? (new BigDecimal(hmPonderaciones.get(Ambitos.A_ACC.toString()).toString()).divide(new BigDecimal("100"))).multiply(new BigDecimal(fila[i].toString())) : new BigDecimal("0"));
            parametros.put("calif_accesosExt", UtilCadena.isNullOVacio(fila[i]) ? "" : (BigDecimal) fila[i]);
            List<Object[]> listaAux = facCalificacion.obtenerValorEscala(Ambitos.A_ACC.toString(), (BigDecimal) fila[i]);
            if (!listaAux.isEmpty()) {
                parametros.put("desc_calif_accesosExt", "(" + listaAux.get(0)[0] + "-" + listaAux.get(0)[1] + ") " + Utilidades.obtenerMensaje("informe.general.reporte.cal_escala." + listaAux.get(0)[2]));
            }
            i++;
            suma = suma.add(fila[i] != null ? (new BigDecimal(hmPonderaciones.get(Ambitos.A_ACCD.toString()).toString()).divide(new BigDecimal("100"))).multiply(new BigDecimal(fila[i].toString())) : new BigDecimal("0"));
            parametros.put("calif_accesosInt", UtilCadena.isNullOVacio(fila[i]) ? "" : (BigDecimal) fila[i]);
            listaAux = facCalificacion.obtenerValorEscala(Ambitos.A_ACCD.toString(), (BigDecimal) fila[i]);
            if (!listaAux.isEmpty()) {
                parametros.put("desc_calif_accesosInt", "(" + listaAux.get(0)[0] + "-" + listaAux.get(0)[1] + ") " + Utilidades.obtenerMensaje("informe.general.reporte.cal_escala." + listaAux.get(0)[2]));
            }
            i++;
            suma = suma.add(fila[i] != null ? (new BigDecimal(hmPonderaciones.get(Ambitos.A_AMB.toString()).toString()).divide(new BigDecimal("100"))).multiply(new BigDecimal(fila[i].toString())) : new BigDecimal("0"));
            parametros.put("calif_ambientes", UtilCadena.isNullOVacio(fila[i]) ? "" : (BigDecimal) fila[i]);
            listaAux = facCalificacion.obtenerValorEscala(Ambitos.A_AMB.toString(), (BigDecimal) fila[i]);
            if (!listaAux.isEmpty()) {
                parametros.put("desc_calif_ambientes", "(" + listaAux.get(0)[0] + "-" + listaAux.get(0)[1] + ") " + Utilidades.obtenerMensaje("informe.general.reporte.cal_escala." + listaAux.get(0)[2]));
            }
            i++;
            suma = suma.add(fila[i] != null ? (new BigDecimal(hmPonderaciones.get(Ambitos.A_CON.toString()).toString()).divide(new BigDecimal("100"))).multiply(new BigDecimal(fila[i].toString())) : new BigDecimal("0"));
            parametros.put("calif_confort", UtilCadena.isNullOVacio(fila[i]) ? "" : (BigDecimal) fila[i]);
            listaAux = facCalificacion.obtenerValorEscala(Ambitos.A_CON.toString(), (BigDecimal) fila[i]);
            if (!listaAux.isEmpty()) {
                parametros.put("desc_calif_confort", "(" + listaAux.get(0)[0] + "-" + listaAux.get(0)[1] + ") " + Utilidades.obtenerMensaje("informe.general.reporte.cal_escala." + listaAux.get(0)[2]));
            }
            i++;
            suma = suma.add(fila[i] != null ? (new BigDecimal(hmPonderaciones.get(Ambitos.A_CV.toString()).toString()).divide(new BigDecimal("100"))).multiply(new BigDecimal(fila[i].toString())) : new BigDecimal("0"));
            parametros.put("calif_controlVigilancia", UtilCadena.isNullOVacio(fila[i]) ? "" : (BigDecimal) fila[i]);
            listaAux = facCalificacion.obtenerValorEscala(Ambitos.A_CV.toString(), (BigDecimal) fila[i]);
            if (!listaAux.isEmpty()) {
                parametros.put("desc_calif_controlVigilancia", "(" + listaAux.get(0)[0] + "-" + listaAux.get(0)[1] + ") " + Utilidades.obtenerMensaje("informe.general.reporte.cal_escala." + listaAux.get(0)[2]));
            }
            i++;
            suma = suma.add(fila[i] != null ? (new BigDecimal(hmPonderaciones.get(Ambitos.A_EEE.toString()).toString()).divide(new BigDecimal("100"))).multiply(new BigDecimal(fila[i].toString())) : new BigDecimal("0"));
            parametros.put("calif_estadoEdificiosEspacios", UtilCadena.isNullOVacio(fila[i]) ? "" : (BigDecimal) fila[i]);
            listaAux = facCalificacion.obtenerValorEscala(Ambitos.A_EEE.toString(), (BigDecimal) fila[i]);
            if (!listaAux.isEmpty()) {
                parametros.put("desc_calif_estadoEdificiosEspacios", "(" + listaAux.get(0)[0] + "-" + listaAux.get(0)[1] + ") " + Utilidades.obtenerMensaje("informe.general.reporte.cal_escala." + listaAux.get(0)[2]));
            }
            i++;
            suma = suma.add(fila[i] != null ? (new BigDecimal(hmPonderaciones.get(Ambitos.A_OFE.name()).toString()).divide(new BigDecimal("100"))).multiply(new BigDecimal(fila[i].toString())) : new BigDecimal("0"));
            parametros.put("calif_oferta", UtilCadena.isNullOVacio(fila[i]) ? "" : (BigDecimal) fila[i]);
            listaAux = facCalificacion.obtenerValorEscala(Ambitos.A_OFE.name(), (BigDecimal) fila[i]);
            if (!listaAux.isEmpty()) {
                parametros.put("desc_calif_oferta", "(" + listaAux.get(0)[0] + "-" + listaAux.get(0)[1] + ") " + Utilidades.obtenerMensaje("informe.general.reporte.cal_escala." + listaAux.get(0)[2]));
            }
            i++;
            suma = suma.add(fila[i] != null ? (new BigDecimal(hmPonderaciones.get(Ambitos.A_PROP.name()).toString()).divide(new BigDecimal("100"))).multiply(new BigDecimal(fila[i].toString())) : new BigDecimal("0"));
            parametros.put("calif_propiedad", UtilCadena.isNullOVacio(fila[i]) ? "" : (BigDecimal) fila[i]);
            listaAux = facCalificacion.obtenerValorEscala(Ambitos.A_PROP.name(), (BigDecimal) fila[i]);
            if (!listaAux.isEmpty()) {
                parametros.put("desc_calif_propiedad", "(" + listaAux.get(0)[0] + "-" + listaAux.get(0)[1] + ") " + Utilidades.obtenerMensaje("informe.general.reporte.cal_escala." + listaAux.get(0)[2]));
            }
            i++;
            suma = suma.add(fila[i] != null ? (new BigDecimal(hmPonderaciones.get(Ambitos.A_RI.name()).toString()).divide(new BigDecimal("100"))).multiply(new BigDecimal("-" + fila[i].toString())) : new BigDecimal("0"));
            parametros.put("calif_riesgos", UtilCadena.isNullOVacio(fila[i]) ? "" : (BigDecimal) fila[i]);
            listaAux = facCalificacion.obtenerValorEscala(Ambitos.A_RI.name(), (BigDecimal) fila[i]);
            if (!listaAux.isEmpty()) {
                parametros.put("desc_calif_riesgos", "(" + listaAux.get(0)[0] + "-" + listaAux.get(0)[1] + ") " + Utilidades.obtenerMensaje("informe.general.reporte.cal_escala." + listaAux.get(0)[2]));
            }
            i++;
            suma = suma.add(fila[i] != null ? (new BigDecimal(hmPonderaciones.get(Ambitos.A_SEG.name()).toString()).divide(new BigDecimal("100"))).multiply(new BigDecimal(fila[i].toString())) : new BigDecimal("0"));
            parametros.put("calif_seguridad", UtilCadena.isNullOVacio(fila[i]) ? "" : (BigDecimal) fila[i]);
            listaAux = facCalificacion.obtenerValorEscala(Ambitos.A_SEG.name(), (BigDecimal) fila[i]);
            if (!listaAux.isEmpty()) {
                parametros.put("desc_calif_seguridad", "(" + listaAux.get(0)[0] + "-" + listaAux.get(0)[1] + ") " + Utilidades.obtenerMensaje("informe.general.reporte.cal_escala." + listaAux.get(0)[2]));
            }
            i++;
            suma = suma.add(fila[i] != null ? (new BigDecimal(hmPonderaciones.get(Ambitos.A_SP.name()).toString()).divide(new BigDecimal("100"))).multiply(new BigDecimal(fila[i].toString())) : new BigDecimal("0"));
            parametros.put("calif_servicios", UtilCadena.isNullOVacio(fila[i]) ? "" : (BigDecimal) fila[i]);
            listaAux = facCalificacion.obtenerValorEscala(Ambitos.A_SP.name(), (BigDecimal) fila[i]);
            if (!listaAux.isEmpty()) {
                parametros.put("desc_calif_servicios", "(" + listaAux.get(0)[0] + "-" + listaAux.get(0)[1] + ") " + Utilidades.obtenerMensaje("informe.general.reporte.cal_escala." + listaAux.get(0)[2]));
            }
            i++;
            suma = suma.add(fila[i] != null ? (new BigDecimal(hmPonderaciones.get(Ambitos.A_SOS.name()).toString()).divide(new BigDecimal("100"))).multiply(new BigDecimal(fila[i].toString())) : new BigDecimal("0"));
            parametros.put("calif_sostenibilidad", UtilCadena.isNullOVacio(fila[i]) ? "" : (BigDecimal) fila[i]);
            listaAux = facCalificacion.obtenerValorEscala(Ambitos.A_SOS.name(), (BigDecimal) fila[i]);
            if (!listaAux.isEmpty()) {
                parametros.put("desc_calif_sostenibilidad", "(" + listaAux.get(0)[0] + "-" + listaAux.get(0)[1] + ") " + Utilidades.obtenerMensaje("informe.general.reporte.cal_escala." + listaAux.get(0)[2]));
            }
            i++;
            suma = suma.add(fila[i] != null ? (new BigDecimal(hmPonderaciones.get(Ambitos.A_SS.name()).toString()).divide(new BigDecimal("100"))).multiply(new BigDecimal(fila[i].toString())) : new BigDecimal("0"));
            parametros.put("calif_servSanitarios", UtilCadena.isNullOVacio(fila[i]) ? "" : (BigDecimal) fila[i]);
            listaAux = facCalificacion.obtenerValorEscala(Ambitos.A_SS.name(), (BigDecimal) fila[i]);
            if (!listaAux.isEmpty()) {
                parametros.put("desc_calif_servSanitarios", "(" + listaAux.get(0)[0] + "-" + listaAux.get(0)[1] + ") " + Utilidades.obtenerMensaje("informe.general.reporte.cal_escala." + listaAux.get(0)[2]));
            }

            parametros.put("calif_calificacionTotal", suma.setScale(2, RoundingMode.HALF_UP));//String.format("%.2f", suma));

        }
    }

    private void anadirRedistribucionPredio(Long idDigInstrumento) {
        FiltroAmbitoRedistribucion filtro = new FiltroAmbitoRedistribucion();
        filtro.inicializar();
        InstrumentoDig instrumentoDig = facInstrumentos.consultarInstrumentoPorCodigo(idDigInstrumento);
        filtro.setPeriodo(periodo);
        filtro.setIdioma(getUsuarioSesion().getUsuario().getIdioma().getId());
        filtro.setAgrupamiento(ParamNivelConsul.PREDIO.getNivel());
        filtro.setIdNivel0(seleccionNivel0 != null ? seleccionNivel0.getId() : null);
        filtro.setIdPredio(instrumentoDig.getPredio().getId());
        List<Object[]> resultados = facAmbito.consultarRedistribucion(filtro);
        if (!resultados.isEmpty()) {
            Object[] fila = resultados.get(0);
            int i = 0;
            //registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
            i++;
            //registro.setNivel2(UtilConsulta.stringValueOf(fila[i++]));
            i++;
            //registro.setNivel3(UtilConsulta.stringValueOf(fila[i++]));
            i++;
            //registro.setNivel4(UtilConsulta.stringValueOf(fila[i++]));
            i++;
            //registro.setNivel5(UtilConsulta.stringValueOf(fila[i++]));
            i++;
            //registro.setCodEstablecimiento(UtilConsulta.stringValueOf(fila[i++]));
            i++;
            //registro.setNomEstablecimiento(UtilConsulta.stringValueOf(fila[i++]));
            i++;
            //registro.setCodSede(UtilConsulta.stringValueOf(fila[i++]));
            i++;
            //registro.setNomSede(UtilConsulta.stringValueOf(fila[i++]));
            i++;
            //registro.setCodPredio(UtilConsulta.stringValueOf(fila[i++]));
            i++;
            //registro.setNomPredio(UtilConsulta.stringValueOf(fila[i++]));
            i++;
            //otros
            //registro.setDirPredio(UtilConsulta.stringValueOf(fila[i++]));
            i++;
            //registro.setPropiedadPredio(UtilConsulta.stringValueOf(fila[i++]));
            i++;
            //registro.setZona(UtilConsulta.stringValueOf(fila[i++]));
            i++;
            //registro.setSector(UtilConsulta.stringValueOf(fila[i++]));
            i++;
            //registro.setClima(UtilConsulta.stringValueOf(fila[i++]));
            i++;
            //registro.setEtnias(UtilConsulta.stringValueOf(fila[i++]));
            i++;

            // indicadores jornadas
            //registro.setAlternoVariaHorarioTotalEstudiantes(UtilConsulta.longValueOf(fila[i++]));
            i++;
            parametros.put("alternoCupos", fila[i] != null ? Math.round(new Double(fila[i] + "") * 100) / 100 : null);
            i++;
            parametros.put("alternoUtilizacionEstandar", fila[i] != null ? Math.round(new Double(fila[i] + "") * 100) / 100 : null);
            i++;
            //registro.setCompletaTotalEstudiantes(UtilConsulta.longValueOf(fila[i++]));
            i++;
            parametros.put("completaCupos", fila[i] != null ? Math.round(new Double(fila[i] + "") * 100) / 100 : null);
            i++;
            parametros.put("completaUtilizacionEstandar", fila[i] != null ? Math.round(new Double(fila[i] + "") * 100) / 100 : null);
            i++;
            //registro.setDobleMañanaTardeTotalEstudiantes(UtilConsulta.longValueOf(fila[i++]));
            i++;
            parametros.put("dobleCupos", fila[i] != null ? Math.round(new Double(fila[i] + "") * 100) / 100 : null);
            i++;
            parametros.put("dobleUtilizacionEstandar", fila[i] != null ? Math.round(new Double(fila[i] + "") * 100) / 100 : null);
            i++;
            //registro.setHorarioAmpliadoTotalEstudiantes(UtilConsulta.longValueOf(fila[i++]));
            i++;
            parametros.put("ampliadoCupos", fila[i] != null ? Math.round(new Double(fila[i] + "") * 100) / 100 : null);
            i++;
            parametros.put("ampliadoUtilizacionEstandar", fila[i] != null ? Math.round(new Double(fila[i] + "") * 100) / 100 : null);
            i++;
            //registro.setMañanaTotalEstudiantes(UtilConsulta.longValueOf(fila[i++]));
            i++;
            parametros.put("mananaCupos", fila[i] != null ? Math.round(new Double(fila[i] + "") * 100) / 100 : null);
            i++;
            parametros.put("mananaUtilizacionEstandar", fila[i] != null ? Math.round(new Double(fila[i] + "") * 100) / 100 : null);
            i++;
            //registro.setNocturnaTotalEstudiantes(UtilConsulta.longValueOf(fila[i++]));
            i++;
            parametros.put("nocturnaCupos", fila[i] != null ? Math.round(new Double(fila[i] + "") * 100) / 100 : null);
            i++;
            parametros.put("nocturnaUtilizacionEstandar", fila[i] != null ? Math.round(new Double(fila[i] + "") * 100) / 100 : null);
            i++;
            //registro.setTardeTotalEstudiantes(UtilConsulta.longValueOf(fila[i++]));
            i++;
            parametros.put("tardeCupos", fila[i] != null ? Math.round(new Double(fila[i] + "") * 100) / 100 : null);
            i++;
            parametros.put("tardeUtilizacionEstandar", fila[i] != null ? Math.round(new Double(fila[i] + "") * 100) / 100 : null);
            i++;
            //registro.setTripleJornadaTotalEstudiantes(UtilConsulta.longValueOf(fila[i++]));
            i++;
            parametros.put("tripleCupos", fila[i] != null ? Math.round(new Double(fila[i] + "") * 100) / 100 : null);
            i++;
            parametros.put("tripleUtilizacionEstandar", fila[i] != null ? Math.round(new Double(fila[i] + "") * 100) / 100 : null);
            i++;
            //registro.setVespertinaTotalEstudiantes(UtilConsulta.longValueOf(fila[i++]));
            i++;
            parametros.put("vespertinaCupos", fila[i] != null ? Math.round(new Double(fila[i] + "") * 100) / 100 : null);
            i++;
            parametros.put("vespertinaUtilizacionEstandar", fila[i] != null ? Math.round(new Double(fila[i] + "") * 100) / 100 : null);
            i++;
            //registro.setOtraTotalEstudiantes(UtilConsulta.longValueOf(fila[i++]));
            i++;
            parametros.put("otraCupos", fila[i] != null ? Math.round(new Double(fila[i] + "") * 100) / 100 : null);
            i++;
            parametros.put("otraUtilizacionEstandar", fila[i] != null ? Math.round(new Double(fila[i] + "") * 100) / 100 : null);
            i++;
            // otros indicadores
            parametros.put("utilEstandarTotal", fila[i] != null ? Math.round(new Double(fila[i] + "") * 100) / 100 : null);
            i++;
            parametros.put("cuposTotal", fila[i] != null ? Math.round(new Double(fila[i] + "") * 100) / 100 : null);
            i++;

        }

    }

    private HashMap obtenerRedistribucion(Object objeto, Long nivelConsulta, Long idNivel0) {
        HashMap datosRedistribucion = new HashMap();
        FiltroAmbitoRedistribucion filtro = new FiltroAmbitoRedistribucion();
        filtro.inicializar();
        if (objeto instanceof Sede) {
            filtro.setPeriodo(((Sede) objeto).getPeriodo());
            filtro.setAgrupamiento(nivelConsulta);
            filtro.setIdSede(((Sede) objeto).getId());
            filtro.setIdNivel0(idNivel0);
        } else if (objeto instanceof Establecimiento) {
            filtro.setPeriodo(((Establecimiento) objeto).getPeriodo());
            filtro.setAgrupamiento(nivelConsulta);
            filtro.setIdEstablecimiento(((Establecimiento) objeto).getId());
            filtro.setIdNivel0(idNivel0);
        } else if (objeto instanceof Nivel) {
            filtro.setPeriodo(facParam.ultimoPeriodoCalculadoIndicadores());
            filtro.setAgrupamiento(nivelConsulta);
            if (nivelConsulta.equals(ParamNivelConsul.CERO.getNivel())) {
                filtro.setIdNivel0(((Nivel) objeto).getId());
            } else if (nivelConsulta.equals(ParamNivelConsul.UNO.getNivel())) {
                filtro.setIdNivel1(((Nivel) objeto).getId());
            } else if (nivelConsulta.equals(ParamNivelConsul.DOS.getNivel())) {
                filtro.setIdNivel2(((Nivel) objeto).getId());
            } else if (nivelConsulta.equals(ParamNivelConsul.TRES.getNivel())) {
                filtro.setIdNivel3(((Nivel) objeto).getId());
            } else if (nivelConsulta.equals(ParamNivelConsul.CUATRO.getNivel())) {
                filtro.setIdNivel4(((Nivel) objeto).getId());
            } else if (nivelConsulta.equals(ParamNivelConsul.CINCO.getNivel())) {
                filtro.setIdNivel5(((Nivel) objeto).getId());
            }
            filtro.setIdNivel0(idNivel0);
        }
        filtro.setIdioma(getUsuarioSesion().getUsuario().getIdioma().getId());
        List<Object[]> resultados = facAmbito.consultarRedistribucion(filtro);
        if (!resultados.isEmpty()) {
            Object[] fila = resultados.get(0);
            int i = 0;
            //n.nombre_nivel1 o 0
            i++;
            if (nivelConsulta.equals(ParamNivelConsul.SEDE.getNivel())
                    || nivelConsulta.equals(ParamNivelConsul.ESTABLECIMIENTO.getNivel())
                    || nivelConsulta.equals(ParamNivelConsul.CINCO.getNivel())
                    || nivelConsulta.equals(ParamNivelConsul.CUATRO.getNivel())
                    || nivelConsulta.equals(ParamNivelConsul.TRES.getNivel())
                    || nivelConsulta.equals(ParamNivelConsul.DOS.getNivel())) {
                //n.nombre_nivel2
                i++;
            }
            if (nivelConsulta.equals(ParamNivelConsul.SEDE.getNivel())
                    || nivelConsulta.equals(ParamNivelConsul.ESTABLECIMIENTO.getNivel())
                    || nivelConsulta.equals(ParamNivelConsul.CINCO.getNivel())
                    || nivelConsulta.equals(ParamNivelConsul.CUATRO.getNivel())
                    || nivelConsulta.equals(ParamNivelConsul.TRES.getNivel())) {
                //n.nombre_nivel3
                i++;
            }
            if (nivelConsulta.equals(ParamNivelConsul.SEDE.getNivel())
                    || nivelConsulta.equals(ParamNivelConsul.ESTABLECIMIENTO.getNivel())
                    || nivelConsulta.equals(ParamNivelConsul.CINCO.getNivel())
                    || nivelConsulta.equals(ParamNivelConsul.CUATRO.getNivel())) {
                //n.nombre_nivel4
                i++;
            }
            if (nivelConsulta.equals(ParamNivelConsul.SEDE.getNivel())
                    || nivelConsulta.equals(ParamNivelConsul.ESTABLECIMIENTO.getNivel())
                    || nivelConsulta.equals(ParamNivelConsul.CINCO.getNivel())) {
                //n.nombre_nivel5
                i++;
            }
            if (nivelConsulta.equals(ParamNivelConsul.SEDE.getNivel())
                    || nivelConsulta.equals(ParamNivelConsul.ESTABLECIMIENTO.getNivel())) {
                //n.codigo_establecimiento
                i++;
                //n.nombre_establecimiento
                i++;
            }
            if (nivelConsulta.equals(ParamNivelConsul.SEDE.getNivel())) {
                //n.codigo_sede
                i++;
                //n.nombre_sede
                i++;
            }
            //saturado.valor
            i++;
            //sub_utilizado.valor
            i++;
            // utilizacion
            datosRedistribucion.put("utilizacion", fila[i] != null ? new BigDecimal(fila[i] + "").setScale(2, RoundingMode.HALF_UP) : null);
            i++;
            // total cupos
            datosRedistribucion.put("cuposTotal", fila[i] != null ? new BigDecimal(fila[i] + "").setScale(2, RoundingMode.HALF_UP) : null);
            i++;

        }

        return datosRedistribucion;

    }

    private List<EstablecimientosAdicionales> obtenerEstablecimientosAdicionales(Long idDigInstrumento) {
        List<EstablecimientosAdicionales> listaDatosAdic = new ArrayList<EstablecimientosAdicionales>();

        List<Object[]> listaRespuesta = facBasica.obtenerRespuestasInstrumentoDig(idDigInstrumento, respuestasEstablecimientosAdicionales);

        Map<String, Long> elemNumDatosAdic = new HashMap<String, Long>();
        List<Object[]> listaRespuesta2 = new ArrayList<Object[]>();
        String codigo = "";
        String valor = "";
        Long idElem = null;
        List<String> numsDatosAdic = new ArrayList<String>();
        for (Object[] fila : listaRespuesta) {
            int i = 0;
            codigo = (String) fila[i++];
            valor = (String) fila[i++];
            idElem = new Long(fila[i++] + "");
            if ("RESP_193".equals(codigo)) {
                //Guardamos el idElemento y el numero de los datos adicionales
                elemNumDatosAdic.put(valor, idElem);
                numsDatosAdic.add(valor);
            }
            listaRespuesta2.add(fila);
        }

        Collections.sort(numsDatosAdic);

        EstablecimientosAdicionales estAdic = new EstablecimientosAdicionales();
        for (String num : numsDatosAdic) {
            Long idSelec = elemNumDatosAdic.get(num);

            for (Object[] fila : listaRespuesta2) {
                int i = 0;
                codigo = (String) fila[i++];
                valor = (String) fila[i++];
                idElem = new Long(fila[i++] + "");
                if (idElem.equals(idSelec)) {
                    if ("RESP_193".equals(codigo)) {
                        estAdic.setCodEstablecimientoSecundario(valor);
                    }
                    if ("RESP_194".equals(codigo)) {
                        estAdic.setNombreEstablecimientoSecundario(valor);
                    }
                    if ("RESP_195".equals(codigo)) {
                        estAdic.setRESP_195(valor);
                    }
                    if ("RESP_196".equals(codigo)) {
                        estAdic.setRESP_196(valor);
                    }
                    if ("RESP_197".equals(codigo)) {
                        estAdic.setRESP_197(valor);
                    }
                    if ("RESP_206".equals(codigo)) {
                        estAdic.setTipo(valor);
                    }
                    if ("RESP_198_01".equals(codigo)) {
                        estAdic.setRESP_198_01(valor);
                    }
                    if ("RESP_198_02".equals(codigo)) {
                        estAdic.setRESP_198_02(valor);
                    }
                    if ("RESP_198_03".equals(codigo)) {
                        estAdic.setRESP_198_03(valor);
                    }
                    if ("RESP_198_04".equals(codigo)) {
                        estAdic.setRESP_198_04(valor);
                    }
                    if ("RESP_198_05".equals(codigo)) {
                        estAdic.setRESP_198_05(valor);
                    }
                    if ("RESP_198_06".equals(codigo)) {
                        estAdic.setRESP_198_06(valor);
                    }
                    if ("RESP_198_07".equals(codigo)) {
                        estAdic.setRESP_198_07(valor);
                    }
                    if ("RESP_198_08".equals(codigo)) {
                        estAdic.setRESP_198_08(valor);
                    }
                    if ("RESP_198_09".equals(codigo)) {
                        estAdic.setRESP_198_09(valor);
                    }
                    if ("RESP_198_10".equals(codigo)) {
                        estAdic.setRESP_198_10(valor);
                    }
                    if ("RESP_199_01".equals(codigo)) {
                        estAdic.setRESP_199_01(valor);
                    }
                    if ("RESP_199_02".equals(codigo)) {
                        estAdic.setRESP_199_02(valor);
                    }
                    if ("RESP_199_03".equals(codigo)) {
                        estAdic.setRESP_199_03(valor);
                    }
                    if ("RESP_199_04".equals(codigo)) {
                        estAdic.setRESP_199_04(valor);
                    }
                    if ("RESP_199_05".equals(codigo)) {
                        estAdic.setRESP_199_05(valor);
                    }
                    if ("RESP_199_06".equals(codigo)) {
                        estAdic.setRESP_199_06(valor);
                    }
                    if ("RESP_199_07".equals(codigo)) {
                        estAdic.setRESP_199_07(valor);
                    }
                    if ("RESP_199_08".equals(codigo)) {
                        estAdic.setRESP_199_08(valor);
                    }
                    if ("RESP_199_09".equals(codigo)) {
                        estAdic.setRESP_199_09(valor);
                    }
                    if ("RESP_199_10".equals(codigo)) {
                        estAdic.setRESP_199_10(valor);
                    }
                    if ("RESP_201_01".equals(codigo)) {
                        estAdic.setRESP_201_01(valor);
                    }
                    if ("RESP_201_02".equals(codigo)) {
                        estAdic.setRESP_201_02(valor);
                    }
                    if ("RESP_201_03".equals(codigo)) {
                        estAdic.setRESP_201_03(valor);
                    }
                    if ("RESP_201_04".equals(codigo)) {
                        estAdic.setRESP_201_04(valor);
                    }
                    if ("RESP_201_05".equals(codigo)) {
                        estAdic.setRESP_201_05(valor);
                    }
                    if ("RESP_201_06".equals(codigo)) {
                        estAdic.setRESP_201_06(valor);
                    }
                    if ("RESP_201_07".equals(codigo)) {
                        estAdic.setRESP_201_07(valor);
                    }
                    if ("RESP_201_08".equals(codigo)) {
                        estAdic.setRESP_201_08(valor);
                    }
                    if ("RESP_201_09".equals(codigo)) {
                        estAdic.setRESP_201_09(valor);
                    }
                    if ("RESP_201_10".equals(codigo)) {
                        estAdic.setRESP_201_10(valor);
                    }

                    String codEstablecimientoSecundario = UtilConsulta.stringValueOf(estAdic.getCodEstablecimientoSecundario());
                    Sede sede = facEstablecimientos.getSedePorCodigo(codEstablecimientoSecundario);
                    if (!UtilCadena.isNullOVacio(sede)) {
                        String codEstPrincipal = UtilConsulta.stringValueOf(sede.getEstablecimiento().getCodigo());
                        String nomEstPrincipal = UtilConsulta.stringValueOf(sede.getEstablecimiento().getNombre());
                        String telSede = UtilConsulta.stringValueOf(sede.getTelefono());
                        estAdic.setCodEstablecimientoPrincipal(codEstPrincipal);
                        estAdic.setNombreEstablecimientoPrincipal(nomEstPrincipal);
                        estAdic.setTelefono(telSede);
                    }

                }
            }
            listaDatosAdic.add(estAdic);
        }
        return listaDatosAdic;
    }

    private List<List> obtenerObjetosEdificios(Long idDigInstrumento) {
        List<Object[]> listaRespuesta = facBasica.obtenerRespuestasInstrumentoDig(idDigInstrumento, respuestasEdificios);

        Map<String, Long> elemNumEdif = new HashMap<String, Long>();
        List<Object[]> listaRespuesta2 = new ArrayList<Object[]>();
        String codigo = "";
        String valor = "";
        Long idElem = null;
        List<String> numsEd = new ArrayList<String>();
        for (Object[] fila : listaRespuesta) {
            int i = 0;
            codigo = (String) fila[i++];
            valor = (String) fila[i++];
            idElem = new Long(fila[i++] + "");
            if ("RESP_073".equals(codigo)) {
                //Guardamos el idElemento y el numero de edificio
                elemNumEdif.put(valor, idElem);
                numsEd.add(valor);
            }
            listaRespuesta2.add(fila);
        }

        Collections.sort(numsEd);

        List<ControlVigilancia> listaCVs = new ArrayList<ControlVigilancia>();
        List<SeguridadAccesibilidad> listaSAs = new ArrayList<SeguridadAccesibilidad>();
        ControlVigilancia cv = new ControlVigilancia();
        SeguridadAccesibilidad sa = new SeguridadAccesibilidad();
        int murosFachadasValor1 = 0;
        int murosFachadasValor2 = 0;
        int murosFachadasValor3 = 0;
        int murosFachadasValor4 = 0;
        int acabadosFachadasValor1 = 0;
        int acabadosFachadasValor2 = 0;
        int acabadosFachadasValor3 = 0;
        int acabadosFachadasValor4 = 0;
        int cubiertasValor1 = 0;
        int cubiertasValor2 = 0;
        int cubiertasValor3 = 0;
        int cubiertasValor4 = 0;
        for (String num : numsEd) {
            Long idSelec = elemNumEdif.get(num);
            cv = new ControlVigilancia();
            sa = new SeguridadAccesibilidad();
            for (Object[] fila : listaRespuesta2) {
                TipologiaValorNombre tv = null;
                int i = 0;
                codigo = (String) fila[i++];
                valor = (String) fila[i++];
                idElem = new Long(fila[i++] + "");
                if (idElem.equals(idSelec)) {
                    if ("RESP_073".equals(codigo)) {
                        cv.setRESP_073(valor);
                        sa.setRESP_073(valor);
                    } else if ("RESP_089_01".equals(codigo)) {
                        cv.setRESP_089_01(transformaSiNoNA(valor));
                    } else if ("RESP_089_02".equals(codigo)) {
                        cv.setRESP_089_02(Utilidades.obtenerMensaje("tipologias.condicion." + "SIC_", valor));
                    } else if ("RESP_090_01".equals(codigo)) {
                        cv.setRESP_090_01(transformaSiNoNA(valor));
                    } else if ("RESP_090_02".equals(codigo)) {
                        cv.setRESP_090_02(Utilidades.obtenerMensaje("tipologias.condicion." + "SIC_", valor));
                    } else if ("RESP_077_01".equals(codigo)) {
                        tv = facParam.consultarValorTipologia(respuestaTipologia.get("RESP_077_01"), valor, getUsuarioSesion().getUsuario().getIdioma().getId());
                        sa.setRESP_077_01(tv == null ? "" : tv.getNombre());
                    } else if ("RESP_077_03".equals(codigo)) {
                        sa.setRESP_077_03(Utilidades.obtenerMensaje("tipologias.condicion." + facInformeGeneral.consultarCodTipologiaRespuesta("RESP_077_03") + "_" + valor));
                    } else if ("RESP_087_02".equals(codigo)) {
                        sa.setRESP_087_02(Utilidades.obtenerMensaje("tipologias.condicion." + "CFRE_", valor));
                    } else if ("RESP_088".equals(codigo)) {
                        sa.setRESP_088(transformaSiNoNA(valor));
                    } else if ("RESP_086_02".equals(codigo)) {
                        tv = facParam.consultarValorTipologia(respuestaTipologia.get("RESP_086_02"), valor, getUsuarioSesion().getUsuario().getIdioma().getId());
                        sa.setRESP_086_02(tv == null ? "" : tv.getNombre());
                    } else if ("RESP_086_03".equals(codigo)) {
                        tv = facParam.consultarValorTipologia(respuestaTipologia.get("RESP_086_03"), valor, getUsuarioSesion().getUsuario().getIdioma().getId());
                        sa.setRESP_086_03(tv == null ? "" : tv.getNombre());
                    } else if ("RESP_083".equals(codigo)) {
                        sa.setRESP_083(transformaSiNoNA(valor));
                    } else if ("RESP_085".equals(codigo)) {
                        sa.setRESP_085(transformaSiNoNA(valor));
                    } else if ("RESP_080_03".equals(codigo)) {
                        if ("1".equals(valor)) {
                            murosFachadasValor1++;
                        }
                        if ("2".equals(valor)) {
                            murosFachadasValor2++;
                        }
                        if ("3".equals(valor)) {
                            murosFachadasValor3++;
                        }
                        if ("4".equals(valor)) {
                            murosFachadasValor4++;
                        }
                    } else if ("RESP_081_03".equals(codigo)) {
                        if ("1".equals(valor)) {
                            acabadosFachadasValor1++;
                        }
                        if ("2".equals(valor)) {
                            acabadosFachadasValor2++;
                        }
                        if ("3".equals(valor)) {
                            acabadosFachadasValor3++;
                        }
                        if ("4".equals(valor)) {
                            acabadosFachadasValor4++;
                        }
                    } else if ("RESP_082_03".equals(codigo)) {
                        if ("1".equals(valor)) {
                            cubiertasValor1++;
                        }
                        if ("2".equals(valor)) {
                            cubiertasValor2++;
                        }
                        if ("3".equals(valor)) {
                            cubiertasValor3++;
                        }
                        if ("4".equals(valor)) {
                            cubiertasValor4++;
                        }
                    }
                }
            }
            listaCVs.add(cv);
            listaSAs.add(sa);
        }
        List result = new ArrayList();

        //Estado de la Materialidad de Edificios
        HashMap<String, Object> datosMaterialidad = new HashMap<String, Object>();

        datosMaterialidad.put("murosFachadasValor1", murosFachadasValor1);
        datosMaterialidad.put("murosFachadasValor2", murosFachadasValor2);
        datosMaterialidad.put("murosFachadasValor3", murosFachadasValor3);
        datosMaterialidad.put("murosFachadasValor4", murosFachadasValor4);
        datosMaterialidad.put("acabadosFachadasValor1", acabadosFachadasValor1);
        datosMaterialidad.put("acabadosFachadasValor2", acabadosFachadasValor2);
        datosMaterialidad.put("acabadosFachadasValor3", acabadosFachadasValor3);
        datosMaterialidad.put("acabadosFachadasValor4", acabadosFachadasValor4);
        datosMaterialidad.put("cubiertasValor1", cubiertasValor1);
        datosMaterialidad.put("cubiertasValor2", cubiertasValor2);
        datosMaterialidad.put("cubiertasValor3", cubiertasValor3);
        datosMaterialidad.put("cubiertasValor4", cubiertasValor4);

        result.add(listaCVs);
        result.add(listaSAs);
        result.add(datosMaterialidad);
        return result;
    }

    private HashMap obtenerObjetosEspaciosEscolaresPredios(Long idDigInstrumento) {
        HashMap datosEspacios = new HashMap();
        List<Object[]> listaRespuesta = facBasica.obtenerRespuestasInstrumentoDig(idDigInstrumento, respuestasEspaciosEscolares);

        Map<String, Long> elemNumEE = new HashMap<String, Long>();
        Map<Long, String> elemNumEEInvertida = new HashMap<Long, String>();
        List<Object[]> listaRespuesta2 = new ArrayList<Object[]>();
        String codigo = "";
        String valor = "";
        Long idElem = null;
        for (Object[] fila : listaRespuesta) {
            int i = 0;
            codigo = (String) fila[i++];
            valor = (String) fila[i++];
            idElem = new Long(fila[i++] + "");
            //Guardamos el idElemento y el numero de espacio escolar
            if ("RESP_094".equals(codigo) || "RESP_095".equals(codigo)) {
                if (elemNumEEInvertida.containsKey(idElem)) {//Ponemos primero el id del espacio(094) y segundo el numero de edificio(095)
                    if ("RESP_094".equals(codigo)) {
                        elemNumEEInvertida.put(idElem, valor + elemNumEEInvertida.get(idElem));
                    } else {
                        elemNumEEInvertida.put(idElem, elemNumEEInvertida.get(idElem) + valor);
                    }
                } else {
                    elemNumEEInvertida.put(idElem, valor);
                }
                //elemNumEE.put(valor,idElem);
                //numsEE.add(valor);
            }
            listaRespuesta2.add(fila);
        }
        for (Long key : elemNumEEInvertida.keySet()) {
            elemNumEE.put(elemNumEEInvertida.get(key), key);
        }
        List<String> numsEE = new ArrayList<String>(elemNumEEInvertida.values());
        //Las ponemos en orden
        Collections.sort(numsEE);

        int pisosValor1 = 0;
        int pisosValor2 = 0;
        int pisosValor3 = 0;
        int pisosValor4 = 0;
        int murosValor1 = 0;
        int murosValor2 = 0;
        int murosValor3 = 0;
        int murosValor4 = 0;
        int acabadosMuroValor1 = 0;
        int acabadosMuroValor2 = 0;
        int acabadosMuroValor3 = 0;
        int acabadosMuroValor4 = 0;
        int ventanasValor1 = 0;
        int ventanasValor2 = 0;
        int ventanasValor3 = 0;
        int ventanasValor4 = 0;
        int puertasValor1 = 0;
        int puertasValor2 = 0;
        int puertasValor3 = 0;
        int puertasValor4 = 0;
        int cielosValor1 = 0;
        int cielosValor2 = 0;
        int cielosValor3 = 0;
        int cielosValor4 = 0;
        int numIluminacion = 0;
        int iluminacionValor1 = 0;
        int iluminacionValor2 = 0;
        int iluminacionValor3 = 0;
        int iluminacionValor4 = 0;
        int numVentilacion = 0;
        int ventilacionValor1 = 0;
        int ventilacionValor2 = 0;
        int ventilacionValor3 = 0;
        int ventilacionValor4 = 0;
        int numAcustica = 0;
        int acusticaValor1 = 0;
        int acusticaValor2 = 0;
        int acusticaValor3 = 0;
        int acusticaValor4 = 0;
        int numDuchas = 0;
        int duchasValor1 = 0;
        int duchasValor2 = 0;
        int duchasValor3 = 0;
        int duchasValor4 = 0;
        int numLavamanos = 0;
        int lavamanosValor1 = 0;
        int lavamanosValor2 = 0;
        int lavamanosValor3 = 0;
        int lavamanosValor4 = 0;
        int numOrinales = 0;
        int orinalesValor1 = 0;
        int orinalesValor2 = 0;
        int orinalesValor3 = 0;
        int orinalesValor4 = 0;
        int numInodoros = 0;
        int inodorosValor1 = 0;
        int inodorosValor2 = 0;
        int inodorosValor3 = 0;
        int inodorosValor4 = 0;
        int aislamientoTermicoPisos = 0;
        int aislamientoTermicoMuros = 0;
        int aislamientoTermicoVentanas = 0;
        int aislamientoTermicoCielos = 0;
        int aislamientoTermicoOtros = 0;

        for (String num : numsEE) {
            Long idSelec = elemNumEE.get(num);

            for (Object[] fila : listaRespuesta2) {
                int i = 0;
                codigo = (String) fila[i++];
                valor = (String) fila[i++];
                idElem = new Long(fila[i++] + "");
                if (idElem.equals(idSelec)) {

                    if ("RESP_112_04".equals(codigo)) {
                        if ("1".equals(valor)) {
                            pisosValor1++;
                        }
                        if ("2".equals(valor)) {
                            pisosValor2++;
                        }
                        if ("3".equals(valor)) {
                            pisosValor3++;
                        }
                        if ("4".equals(valor)) {
                            pisosValor4++;
                        }
                    } else if ("RESP_113_04".equals(codigo)) {
                        if ("1".equals(valor)) {
                            murosValor1++;
                        }
                        if ("2".equals(valor)) {
                            murosValor2++;
                        }
                        if ("3".equals(valor)) {
                            murosValor3++;
                        }
                        if ("4".equals(valor)) {
                            murosValor4++;
                        }
                    } else if ("RESP_114_04".equals(codigo)) {
                        if ("1".equals(valor)) {
                            acabadosMuroValor1++;
                        }
                        if ("2".equals(valor)) {
                            acabadosMuroValor2++;
                        }
                        if ("3".equals(valor)) {
                            acabadosMuroValor3++;
                        }
                        if ("4".equals(valor)) {
                            acabadosMuroValor4++;
                        }
                    } else if ("RESP_115_04".equals(codigo)) {
                        if ("1".equals(valor)) {
                            ventanasValor1++;
                        }
                        if ("2".equals(valor)) {
                            ventanasValor2++;
                        }
                        if ("3".equals(valor)) {
                            ventanasValor3++;
                        }
                        if ("4".equals(valor)) {
                            ventanasValor4++;
                        }
                    } else if ("RESP_116_04".equals(codigo)) {
                        if ("1".equals(valor)) {
                            puertasValor1++;
                        }
                        if ("2".equals(valor)) {
                            puertasValor2++;
                        }
                        if ("3".equals(valor)) {
                            puertasValor3++;
                        }
                        if ("4".equals(valor)) {
                            puertasValor4++;
                        }
                    } else if ("RESP_117_04".equals(codigo)) {
                        if ("1".equals(valor)) {
                            cielosValor1++;
                        }
                        if ("2".equals(valor)) {
                            cielosValor2++;
                        }
                        if ("3".equals(valor)) {
                            cielosValor3++;
                        }
                        if ("4".equals(valor)) {
                            cielosValor4++;
                        }
                    } else if ("RESP_107_02".equals(codigo)) {
                        numIluminacion++;
                        if ("1".equals(valor)) {
                            iluminacionValor1++;
                        }
                        if ("2".equals(valor)) {
                            iluminacionValor2++;
                        }
                        if ("3".equals(valor)) {
                            iluminacionValor3++;
                        }
                        if ("4".equals(valor)) {
                            iluminacionValor4++;
                        }
                    } else if ("RESP_108_02".equals(codigo)) {
                        numVentilacion++;
                        if ("1".equals(valor)) {
                            ventilacionValor1++;
                        }
                        if ("2".equals(valor)) {
                            ventilacionValor2++;
                        }
                        if ("3".equals(valor)) {
                            ventilacionValor3++;
                        }
                        if ("4".equals(valor)) {
                            ventilacionValor4++;
                        }
                    } else if ("RESP_109_02".equals(codigo)) {
                        numAcustica++;
                        if ("1".equals(valor)) {
                            acusticaValor1++;
                        }
                        if ("2".equals(valor)) {
                            acusticaValor2++;
                        }
                        if ("3".equals(valor)) {
                            acusticaValor3++;
                        }
                        if ("4".equals(valor)) {
                            acusticaValor4++;
                        }
                    } else if ("RESP_133_03".equals(codigo)) {
                        numDuchas++;
                        if ("1".equals(valor)) {
                            duchasValor1++;
                        }
                        if ("2".equals(valor)) {
                            duchasValor2++;
                        }
                        if ("3".equals(valor)) {
                            duchasValor3++;
                        }
                        if ("4".equals(valor)) {
                            duchasValor4++;
                        }
                    } else if ("RESP_131_03".equals(codigo) || "RESP_131_06".equals(codigo)) {
                        numLavamanos++;
                        if ("1".equals(valor)) {
                            lavamanosValor1++;
                        }
                        if ("2".equals(valor)) {
                            lavamanosValor2++;
                        }
                        if ("3".equals(valor)) {
                            lavamanosValor3++;
                        }
                        if ("4".equals(valor)) {
                            lavamanosValor4++;
                        }
                    } else if ("RESP_132_03".equals(codigo) || "RESP_132_06".equals(codigo)) {
                        numOrinales++;
                        if ("1".equals(valor)) {
                            orinalesValor1++;
                        }
                        if ("2".equals(valor)) {
                            orinalesValor2++;
                        }
                        if ("3".equals(valor)) {
                            orinalesValor3++;
                        }
                        if ("4".equals(valor)) {
                            orinalesValor4++;
                        }
                    } else if ("RESP_130_03".equals(codigo) || "RESP_130_06".equals(codigo)
                            || "RESP_130_09".equals(codigo) || "RESP_130_12".equals(codigo)) {
                        numInodoros++;
                        if ("1".equals(valor)) {
                            inodorosValor1++;
                        }
                        if ("2".equals(valor)) {
                            inodorosValor2++;
                        }
                        if ("3".equals(valor)) {
                            inodorosValor3++;
                        }
                        if ("4".equals(valor)) {
                            inodorosValor4++;
                        }
                    } else if ("RESP_110_01".equals(codigo)) {
                        aislamientoTermicoPisos += new Integer(valor);
                    } else if ("RESP_110_02".equals(codigo)) {
                        aislamientoTermicoMuros += new Integer(valor);
                    } else if ("RESP_110_03".equals(codigo)) {
                        aislamientoTermicoVentanas += new Integer(valor);
                    } else if ("RESP_110_04".equals(codigo)) {
                        aislamientoTermicoCielos += new Integer(valor);
                    } else if ("RESP_110_05".equals(codigo)) {
                        aislamientoTermicoOtros += new Integer(valor);
                    }

                }
            }
        }

        datosEspacios.put("pisosValor1", pisosValor1);
        datosEspacios.put("pisosValor2", pisosValor2);
        datosEspacios.put("pisosValor3", pisosValor3);
        datosEspacios.put("pisosValor4", pisosValor4);

        datosEspacios.put("murosValor1", murosValor1);
        datosEspacios.put("murosValor2", murosValor2);
        datosEspacios.put("murosValor3", murosValor3);
        datosEspacios.put("murosValor4", murosValor4);

        datosEspacios.put("acabadosMuroValor1", acabadosMuroValor1);
        datosEspacios.put("acabadosMuroValor2", acabadosMuroValor2);
        datosEspacios.put("acabadosMuroValor3", acabadosMuroValor3);
        datosEspacios.put("acabadosMuroValor4", acabadosMuroValor4);

        datosEspacios.put("ventanasValor1", ventanasValor1);
        datosEspacios.put("ventanasValor2", ventanasValor2);
        datosEspacios.put("ventanasValor3", ventanasValor3);
        datosEspacios.put("ventanasValor4", ventanasValor4);

        datosEspacios.put("puertasValor1", puertasValor1);
        datosEspacios.put("puertasValor2", puertasValor2);
        datosEspacios.put("puertasValor3", puertasValor3);
        datosEspacios.put("puertasValor4", puertasValor4);

        datosEspacios.put("cielosValor1", cielosValor1);
        datosEspacios.put("cielosValor2", cielosValor2);
        datosEspacios.put("cielosValor3", cielosValor3);
        datosEspacios.put("cielosValor4", cielosValor4);

        datosEspacios.put("iluminacionValor1", iluminacionValor1);
        datosEspacios.put("iluminacionValor2", iluminacionValor2);
        datosEspacios.put("iluminacionValor3", iluminacionValor3);
        datosEspacios.put("iluminacionValor4", iluminacionValor4);

        datosEspacios.put("ventilacionValor1", ventilacionValor1);
        datosEspacios.put("ventilacionValor2", ventilacionValor2);
        datosEspacios.put("ventilacionValor3", ventilacionValor3);
        datosEspacios.put("ventilacionValor4", ventilacionValor4);

        datosEspacios.put("acusticaValor1", acusticaValor1);
        datosEspacios.put("acusticaValor2", acusticaValor2);
        datosEspacios.put("acusticaValor3", acusticaValor3);
        datosEspacios.put("acusticaValor4", acusticaValor4);

        datosEspacios.put("duchasValor1", duchasValor1);
        datosEspacios.put("duchasValor2", duchasValor2);
        datosEspacios.put("duchasValor3", duchasValor3);
        datosEspacios.put("duchasValor4", duchasValor4);

        datosEspacios.put("lavamanosValor1", lavamanosValor1);
        datosEspacios.put("lavamanosValor2", lavamanosValor2);
        datosEspacios.put("lavamanosValor3", lavamanosValor3);
        datosEspacios.put("lavamanosValor4", lavamanosValor4);

        datosEspacios.put("orinalesValor1", orinalesValor1);
        datosEspacios.put("orinalesValor2", orinalesValor2);
        datosEspacios.put("orinalesValor3", orinalesValor3);
        datosEspacios.put("orinalesValor4", orinalesValor4);

        datosEspacios.put("inodorosValor1", inodorosValor1);
        datosEspacios.put("inodorosValor2", inodorosValor2);
        datosEspacios.put("inodorosValor3", inodorosValor3);
        datosEspacios.put("inodorosValor4", inodorosValor4);

        datosEspacios.put("aislamientoTermicoPisos", aislamientoTermicoPisos);
        datosEspacios.put("aislamientoTermicoMuros", aislamientoTermicoMuros);
        datosEspacios.put("aislamientoTermicoVentanas", aislamientoTermicoVentanas);
        datosEspacios.put("aislamientoTermicoCielos", aislamientoTermicoCielos);
        datosEspacios.put("aislamientoTermicoOtros", aislamientoTermicoOtros);

        datosEspacios.put("numAcustica", numAcustica);
        datosEspacios.put("numDuchas", numDuchas);
        datosEspacios.put("numIluminacion", numIluminacion);
        datosEspacios.put("numInodoros", numInodoros);
        datosEspacios.put("numLavamanos", numLavamanos);
        datosEspacios.put("numOrinales", numOrinales);
        datosEspacios.put("numVentilacion", numVentilacion);
        return datosEspacios;
    }

    private HashMap obtenerUnidadesFuncionales(Long idEntidad, Long nivelConsulta) throws Exception {

        HashMap datosUnidadesFuncionales = new HashMap();
        Double areaAulas = new Double(0);
        Double areaLaboratorios = new Double(0);
        Double areaCentroRecursosAprendizaje = new Double(0);
        Double areaTalleres = new Double(0);
        Double areaExtension = new Double(0);
        Double areaServSanitariosTotal = new Double(0);
        Double areaAbastProcesa = new Double(0);
        Double areaConducAdministra = new Double(0);
        List<String[]> indBusqUnidadesFun = new ArrayList<String[]>();
        indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_AREA_TIPO_ESPACIO.name(), UnidadFuncionalAmbiente.AULA.name()});
        indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_M2_ALUMNO.name(), UnidadFuncionalAmbiente.AULA.name()});
        indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_CUMPLIMIENTO_NORMA.name(), UnidadFuncionalAmbiente.AULA.name()});
        indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_DEFICIT_ESTANDAR.name(), UnidadFuncionalAmbiente.AULA.name()});
        indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_NUMERO_PREDIOS_NO_IDONEOS.name(), UnidadFuncionalAmbiente.AULA.name()});

        List<Object[]> indicadores = facParam.consultarIndicadoresUnidadesFuncionales(indBusqUnidadesFun, nivelConsulta, idEntidad);

        int i = 0;
        if (!indicadores.isEmpty()) {
            datosUnidadesFuncionales.put("areaAula", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "0" : (String) indicadores.get(0)[i]);
            i++;
            datosUnidadesFuncionales.put("m2AlumnosAula", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "0" : (String) indicadores.get(0)[i]);
            i++;
            datosUnidadesFuncionales.put("cumplimientoAula", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "" : (String) indicadores.get(0)[i]);
            i++;
            datosUnidadesFuncionales.put("deficitAula", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "0" : (String) indicadores.get(0)[i]);
            i++;
            datosUnidadesFuncionales.put("numPrediosNoCumplenAula", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "0" : (String) indicadores.get(0)[i]);
            if (datosUnidadesFuncionales.get("areaAula") != null) {
                areaAulas += new Double((String) datosUnidadesFuncionales.get("areaAula"));
            }
        }
        datosUnidadesFuncionales.put("estandarAula", facParam.consultarEstandar(ParamEstandar.ESTANDAR_AULA).getValor() + "");
        indicadores.clear();
        indBusqUnidadesFun.clear();

        if (ParamNivelConsul.SEDE.getNivel().equals(nivelConsulta)) {
            String valor = Utilidades.obtenerMensaje("informe.general.noaplica");
            //Lab Ciencias
            datosUnidadesFuncionales.put("areaLabCiencias", "0");
            datosUnidadesFuncionales.put("estandarLabCiencias", valor);
            datosUnidadesFuncionales.put("m2AlumnosLabCiencias", valor);
            datosUnidadesFuncionales.put("cumplimientoLabCiencias", valor);
            datosUnidadesFuncionales.put("deficitLabCiencias", valor);
            datosUnidadesFuncionales.put("numPrediosNoCumplenLabCiencias", valor);
            //Lab Multimedial
            datosUnidadesFuncionales.put("areaLabMultimedia", "0");
            datosUnidadesFuncionales.put("estandarLabMultimedia", valor);
            datosUnidadesFuncionales.put("m2AlumnosLabMultimedia", valor);
            datosUnidadesFuncionales.put("cumplimientoLabMultimedia", valor);
            datosUnidadesFuncionales.put("deficitLabMultimedia", valor);
            datosUnidadesFuncionales.put("numPrediosNoCumplenLabMultimedia", valor);
            //Lab Computacion
            datosUnidadesFuncionales.put("areaLabComputacion", "0");
            datosUnidadesFuncionales.put("estandarLabComputacion", valor);
            datosUnidadesFuncionales.put("m2AlumnosLabComputacion", valor);
            datosUnidadesFuncionales.put("cumplimientoLabComputacion", valor);
            datosUnidadesFuncionales.put("deficitLabComputacion", valor);
            datosUnidadesFuncionales.put("numPrediosNoCumplenLabComputacion", valor);
            //Biblioteca
            datosUnidadesFuncionales.put("areaBiblioteca", "0");
            datosUnidadesFuncionales.put("estandarBiblioteca", valor);
            datosUnidadesFuncionales.put("m2AlumnosBiblioteca", valor);
            datosUnidadesFuncionales.put("cumplimientoBiblioteca", valor);
            datosUnidadesFuncionales.put("deficitBiblioteca", valor);
            datosUnidadesFuncionales.put("numPrediosNoCumplenBiblioteca", valor);
            //Sala Musica
            datosUnidadesFuncionales.put("areaSalaMusica", "0");
            datosUnidadesFuncionales.put("estandarSalaMusica", valor);
            datosUnidadesFuncionales.put("m2AlumnosSalaMusica", valor);
            datosUnidadesFuncionales.put("cumplimientoSalaMusica", valor);
            datosUnidadesFuncionales.put("deficitSalaMusica", valor);
            datosUnidadesFuncionales.put("numPrediosNoCumplenSalaMusica", valor);
            //Sala Arte
            datosUnidadesFuncionales.put("areaSalaArte", "0");
            datosUnidadesFuncionales.put("estandarSalaArte", valor);
            datosUnidadesFuncionales.put("m2AlumnosSalaArte", valor);
            datosUnidadesFuncionales.put("cumplimientoSalaArte", valor);
            datosUnidadesFuncionales.put("deficitSalaArte", valor);
            datosUnidadesFuncionales.put("numPrediosNoCumplenSalaArte", valor);
            //Playon Deportivo
            datosUnidadesFuncionales.put("areaPlayonDepor", "0");
            datosUnidadesFuncionales.put("estandarPlayonDepor", valor);
            datosUnidadesFuncionales.put("m2AlumnosPlayonDepor", valor);
            datosUnidadesFuncionales.put("cumplimientoPlayonDepor", valor);
            datosUnidadesFuncionales.put("deficitPlayonDepor", valor);
            datosUnidadesFuncionales.put("numPrediosNoCumplenPlayonDepor", valor);
            //Salon Usos Multiples
            datosUnidadesFuncionales.put("areaSUM", "0");
            datosUnidadesFuncionales.put("estandarSUM", valor);
            datosUnidadesFuncionales.put("m2AlumnosSUM", valor);
            datosUnidadesFuncionales.put("cumplimientoSUM", valor);
            datosUnidadesFuncionales.put("deficitSUM", valor);
            datosUnidadesFuncionales.put("numPrediosNoCumplenSUM", valor);
            //Psicopedagogia
            datosUnidadesFuncionales.put("areaPsicopeEnferm", "0");
            datosUnidadesFuncionales.put("estandarPsicopeEnferm", valor);
            datosUnidadesFuncionales.put("m2AlumnosPsicopeEnferm", valor);
            datosUnidadesFuncionales.put("cumplimientoPsicopeEnferm", valor);
            datosUnidadesFuncionales.put("deficitPsicopeEnferm", valor);
            datosUnidadesFuncionales.put("numPrediosNoCumplenPsicopeEnferm", valor);
        } else {
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_AREA_TIPO_ESPACIO.name(), UnidadFuncionalAmbiente.LABORATORIO_CIENCIAS.name()});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_M2_ALUMNO.name(), UnidadFuncionalAmbiente.LABORATORIO_CIENCIAS.name()});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_CUMPLIMIENTO_NORMA.name(), UnidadFuncionalAmbiente.LABORATORIO_CIENCIAS.name()});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_DEFICIT_ESTANDAR.name(), UnidadFuncionalAmbiente.LABORATORIO_CIENCIAS.name()});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_NUMERO_PREDIOS_NO_IDONEOS.name(), UnidadFuncionalAmbiente.LABORATORIO_CIENCIAS.name()});

            indicadores = facParam.consultarIndicadoresUnidadesFuncionales(indBusqUnidadesFun, nivelConsulta, idEntidad);
            i = 0;
            if (!indicadores.isEmpty()) {
                datosUnidadesFuncionales.put("areaLabCiencias", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "0" : (String) indicadores.get(0)[i]);
                i++;
                datosUnidadesFuncionales.put("m2AlumnosLabCiencias", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "0" : (String) indicadores.get(0)[i]);
                i++;
                datosUnidadesFuncionales.put("cumplimientoLabCiencias", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "" : (String) indicadores.get(0)[i]);
                i++;
                datosUnidadesFuncionales.put("deficitLabCiencias", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "0" : (String) indicadores.get(0)[i]);
                i++;
                datosUnidadesFuncionales.put("numPrediosNoCumplenLabCiencias", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "0" : (String) indicadores.get(0)[i]);
                if (datosUnidadesFuncionales.get("areaLabCiencias") != null) {
                    areaLaboratorios += new Double((String) datosUnidadesFuncionales.get("areaLabCiencias"));
                }
            }
            datosUnidadesFuncionales.put("estandarLabCiencias", facParam.consultarEstandar(ParamEstandar.ESTANDAR_LABORATORIO_CIENCIAS).getValor() + "");
            indicadores.clear();
            indBusqUnidadesFun.clear();

            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_AREA_TIPO_ESPACIO.name(), UnidadFuncionalAmbiente.LABORATORIO_MULTIMEDIAL.name()});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_M2_ALUMNO.name(), UnidadFuncionalAmbiente.LABORATORIO_MULTIMEDIAL.name()});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_CUMPLIMIENTO_NORMA.name(), UnidadFuncionalAmbiente.LABORATORIO_MULTIMEDIAL.name()});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_DEFICIT_ESTANDAR.name(), UnidadFuncionalAmbiente.LABORATORIO_MULTIMEDIAL.name()});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_NUMERO_PREDIOS_NO_IDONEOS.name(), UnidadFuncionalAmbiente.LABORATORIO_MULTIMEDIAL.name()});

            indicadores = facParam.consultarIndicadoresUnidadesFuncionales(indBusqUnidadesFun, nivelConsulta, idEntidad);
            i = 0;
            if (!indicadores.isEmpty()) {
                datosUnidadesFuncionales.put("areaLabMultimedia", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "0" : (String) indicadores.get(0)[i]);
                i++;
                datosUnidadesFuncionales.put("m2AlumnosLabMultimedia", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "0" : (String) indicadores.get(0)[i]);
                i++;
                datosUnidadesFuncionales.put("cumplimientoLabMultimedia", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "" : (String) indicadores.get(0)[i]);
                i++;
                datosUnidadesFuncionales.put("deficitLabMultimedia", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "0" : (String) indicadores.get(0)[i]);
                i++;
                datosUnidadesFuncionales.put("numPrediosNoCumplenLabMultimedia", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "0" : (String) indicadores.get(0)[i]);
                if (datosUnidadesFuncionales.get("areaLabMultimedia") != null) {
                    areaLaboratorios += new Double((String) datosUnidadesFuncionales.get("areaLabMultimedia"));
                }
            }
            datosUnidadesFuncionales.put("estandarLabMultimedia", facParam.consultarEstandar(ParamEstandar.ESTANDAR_LABORATORIO_MULTIMEDIAL).getValor() + "");
            indicadores.clear();
            indBusqUnidadesFun.clear();

            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_AREA_TIPO_ESPACIO.name(), UnidadFuncionalAmbiente.LABORATORIO_COMPUTACION.name()});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_M2_ALUMNO.name(), UnidadFuncionalAmbiente.LABORATORIO_COMPUTACION.name()});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_CUMPLIMIENTO_NORMA.name(), UnidadFuncionalAmbiente.LABORATORIO_COMPUTACION.name()});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_DEFICIT_ESTANDAR.name(), UnidadFuncionalAmbiente.LABORATORIO_COMPUTACION.name()});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_NUMERO_PREDIOS_NO_IDONEOS.name(), UnidadFuncionalAmbiente.LABORATORIO_COMPUTACION.name()});

            indicadores = facParam.consultarIndicadoresUnidadesFuncionales(indBusqUnidadesFun, nivelConsulta, idEntidad);
            i = 0;
            if (!indicadores.isEmpty()) {
                datosUnidadesFuncionales.put("areaLabComputacion", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "0" : (String) indicadores.get(0)[i]);
                i++;
                datosUnidadesFuncionales.put("m2AlumnosLabComputacion", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "0" : (String) indicadores.get(0)[i]);
                i++;
                datosUnidadesFuncionales.put("cumplimientoLabComputacion", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "" : (String) indicadores.get(0)[i]);
                i++;
                datosUnidadesFuncionales.put("deficitLabComputacion", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "0" : (String) indicadores.get(0)[i]);
                i++;
                datosUnidadesFuncionales.put("numPrediosNoCumplenLabComputacion", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "0" : (String) indicadores.get(0)[i]);
                if (datosUnidadesFuncionales.get("areaLabComputacion") != null) {
                    areaLaboratorios += new Double((String) datosUnidadesFuncionales.get("areaLabComputacion"));
                }
            }
            datosUnidadesFuncionales.put("estandarLabComputacion", facParam.consultarEstandar(ParamEstandar.ESTANDAR_LABORATORIO_COMPUTACION).getValor() + "");
            indicadores.clear();
            indBusqUnidadesFun.clear();

            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_AREA_TIPO_ESPACIO.name(), UnidadFuncionalAmbiente.BIBLIOTECA.name()});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_M2_ALUMNO.name(), UnidadFuncionalAmbiente.BIBLIOTECA.name()});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_CUMPLIMIENTO_NORMA.name(), UnidadFuncionalAmbiente.BIBLIOTECA.name()});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_DEFICIT_ESTANDAR.name(), UnidadFuncionalAmbiente.BIBLIOTECA.name()});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_NUMERO_PREDIOS_NO_IDONEOS.name(), UnidadFuncionalAmbiente.BIBLIOTECA.name()});

            indicadores = facParam.consultarIndicadoresUnidadesFuncionales(indBusqUnidadesFun, nivelConsulta, idEntidad);
            i = 0;
            if (!indicadores.isEmpty()) {
                datosUnidadesFuncionales.put("areaBiblioteca", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "0" : (String) indicadores.get(0)[i]);
                i++;
                datosUnidadesFuncionales.put("m2AlumnosBiblioteca", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "0" : (String) indicadores.get(0)[i]);
                i++;
                datosUnidadesFuncionales.put("cumplimientoBiblioteca", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "" : (String) indicadores.get(0)[i]);
                i++;
                datosUnidadesFuncionales.put("deficitBiblioteca", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "0" : (String) indicadores.get(0)[i]);
                i++;
                datosUnidadesFuncionales.put("numPrediosNoCumplenBiblioteca", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "0" : (String) indicadores.get(0)[i]);
                if (datosUnidadesFuncionales.get("areaBiblioteca") != null) {
                    areaCentroRecursosAprendizaje += new Double((String) datosUnidadesFuncionales.get("areaBiblioteca"));
                }
            }
            datosUnidadesFuncionales.put("estandarBiblioteca", facParam.consultarEstandar(ParamEstandar.ESTANDAR_BIBLIOTECA).getValor() + "");
            indicadores.clear();
            indBusqUnidadesFun.clear();

            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_AREA_TIPO_ESPACIO.name(), UnidadFuncionalAmbiente.SALA_MUSICA.name()});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_M2_ALUMNO.name(), UnidadFuncionalAmbiente.SALA_MUSICA.name()});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_CUMPLIMIENTO_NORMA.name(), UnidadFuncionalAmbiente.SALA_MUSICA.name()});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_DEFICIT_ESTANDAR.name(), UnidadFuncionalAmbiente.SALA_MUSICA.name()});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_NUMERO_PREDIOS_NO_IDONEOS.name(), UnidadFuncionalAmbiente.SALA_MUSICA.name()});

            indicadores = facParam.consultarIndicadoresUnidadesFuncionales(indBusqUnidadesFun, nivelConsulta, idEntidad);
            i = 0;
            if (!indicadores.isEmpty()) {
                datosUnidadesFuncionales.put("areaSalaMusica", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "0" : (String) indicadores.get(0)[i]);
                i++;
                datosUnidadesFuncionales.put("m2AlumnosSalaMusica", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "0" : (String) indicadores.get(0)[i]);
                i++;
                datosUnidadesFuncionales.put("cumplimientoSalaMusica", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "" : (String) indicadores.get(0)[i]);
                i++;
                datosUnidadesFuncionales.put("deficitSalaMusica", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "0" : (String) indicadores.get(0)[i]);
                i++;
                datosUnidadesFuncionales.put("numPrediosNoCumplenSalaMusica", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "0" : (String) indicadores.get(0)[i]);
                if (datosUnidadesFuncionales.get("areaSalaMusica") != null) {
                    areaTalleres += new Double((String) datosUnidadesFuncionales.get("areaSalaMusica"));
                }
            }
            datosUnidadesFuncionales.put("estandarSalaMusica", facParam.consultarEstandar(ParamEstandar.ESTANDAR_SALA_MUSICA).getValor() + "");
            indicadores.clear();
            indBusqUnidadesFun.clear();

            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_AREA_TIPO_ESPACIO.name(), UnidadFuncionalAmbiente.SALA_ARTES.name()});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_M2_ALUMNO.name(), UnidadFuncionalAmbiente.SALA_ARTES.name()});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_CUMPLIMIENTO_NORMA.name(), UnidadFuncionalAmbiente.SALA_ARTES.name()});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_DEFICIT_ESTANDAR.name(), UnidadFuncionalAmbiente.SALA_ARTES.name()});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_NUMERO_PREDIOS_NO_IDONEOS.name(), UnidadFuncionalAmbiente.SALA_ARTES.name()});

            indicadores = facParam.consultarIndicadoresUnidadesFuncionales(indBusqUnidadesFun, nivelConsulta, idEntidad);
            i = 0;
            if (!indicadores.isEmpty()) {
                datosUnidadesFuncionales.put("areaSalaArte", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "0" : (String) indicadores.get(0)[i]);
                i++;
                datosUnidadesFuncionales.put("m2AlumnosSalaArte", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "0" : (String) indicadores.get(0)[i]);
                i++;
                datosUnidadesFuncionales.put("cumplimientoSalaArte", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "" : (String) indicadores.get(0)[i]);
                i++;
                datosUnidadesFuncionales.put("deficitSalaArte", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "0" : (String) indicadores.get(0)[i]);
                i++;
                datosUnidadesFuncionales.put("numPrediosNoCumplenSalaArte", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "0" : (String) indicadores.get(0)[i]);
                if (datosUnidadesFuncionales.get("areaSalaArte") != null) {
                    areaTalleres += new Double((String) datosUnidadesFuncionales.get("areaSalaArte"));
                }
            }
            datosUnidadesFuncionales.put("estandarSalaArte", facParam.consultarEstandar(ParamEstandar.ESTANDAR_SALA_ARTES).getValor() + "");
            indicadores.clear();
            indBusqUnidadesFun.clear();

            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_AREA_TIPO_ESPACIO.name(), UnidadFuncionalAmbiente.PLAYON_DEPORTIVO.name()});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_M2_ALUMNO.name(), UnidadFuncionalAmbiente.PLAYON_DEPORTIVO.name()});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_CUMPLIMIENTO_NORMA.name(), UnidadFuncionalAmbiente.PLAYON_DEPORTIVO.name()});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_DEFICIT_ESTANDAR.name(), UnidadFuncionalAmbiente.PLAYON_DEPORTIVO.name()});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_NUMERO_PREDIOS_NO_IDONEOS.name(), UnidadFuncionalAmbiente.PLAYON_DEPORTIVO.name()});

            indicadores = facParam.consultarIndicadoresUnidadesFuncionales(indBusqUnidadesFun, nivelConsulta, idEntidad);
            i = 0;
            if (!indicadores.isEmpty()) {
                datosUnidadesFuncionales.put("areaPlayonDepor", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "0" : (String) indicadores.get(0)[i]);
                i++;
                datosUnidadesFuncionales.put("m2AlumnosPlayonDepor", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "0" : (String) indicadores.get(0)[i]);
                i++;
                datosUnidadesFuncionales.put("cumplimientoPlayonDepor", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "" : (String) indicadores.get(0)[i]);
                i++;
                datosUnidadesFuncionales.put("deficitPlayonDepor", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "0" : (String) indicadores.get(0)[i]);
                i++;
                datosUnidadesFuncionales.put("numPrediosNoCumplenPlayonDepor", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "0" : (String) indicadores.get(0)[i]);
                if (datosUnidadesFuncionales.get("areaPlayonDepor") != null) {
                    areaExtension += new Double((String) datosUnidadesFuncionales.get("areaPlayonDepor"));
                }
            }
            datosUnidadesFuncionales.put("estandarPlayonDepor", facParam.consultarEstandar(ParamEstandar.ESTANDAR_PLAYON_DEPORTIVO).getValor() + "");
            indicadores.clear();
            indBusqUnidadesFun.clear();

            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_AREA_TIPO_ESPACIO.name(), UnidadFuncionalAmbiente.SALON_USOS_MULTIPLES.name()});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_M2_ALUMNO.name(), UnidadFuncionalAmbiente.SALON_USOS_MULTIPLES.name()});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_CUMPLIMIENTO_NORMA.name(), UnidadFuncionalAmbiente.SALON_USOS_MULTIPLES.name()});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_DEFICIT_ESTANDAR.name(), UnidadFuncionalAmbiente.SALON_USOS_MULTIPLES.name()});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_NUMERO_PREDIOS_NO_IDONEOS.name(), UnidadFuncionalAmbiente.SALON_USOS_MULTIPLES.name()});

            indicadores = facParam.consultarIndicadoresUnidadesFuncionales(indBusqUnidadesFun, nivelConsulta, idEntidad);
            i = 0;
            if (!indicadores.isEmpty()) {
                datosUnidadesFuncionales.put("areaSUM", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "0" : (String) indicadores.get(0)[i]);
                i++;
                datosUnidadesFuncionales.put("m2AlumnosSUM", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "0" : (String) indicadores.get(0)[i]);
                i++;
                datosUnidadesFuncionales.put("cumplimientoSUM", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "" : (String) indicadores.get(0)[i]);
                i++;
                datosUnidadesFuncionales.put("deficitSUM", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "0" : (String) indicadores.get(0)[i]);
                i++;
                datosUnidadesFuncionales.put("numPrediosNoCumplenSUM", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "0" : (String) indicadores.get(0)[i]);
                if (datosUnidadesFuncionales.get("areaSUM") != null) {
                    areaExtension += new Double((String) datosUnidadesFuncionales.get("areaSUM"));
                }
            }
            datosUnidadesFuncionales.put("estandarSUM", facParam.consultarEstandar(ParamEstandar.ESTANDAR_SALON_USOS_MULTIPLES).getValor() + "");
            indicadores.clear();
            indBusqUnidadesFun.clear();

            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_AREA_TIPO_ESPACIO.name(), UnidadFuncionalAmbiente.PSICOPEDAGOGIA_ENFERMERIA.name()});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_M2_ALUMNO.name(), UnidadFuncionalAmbiente.PSICOPEDAGOGIA_ENFERMERIA.name()});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_CUMPLIMIENTO_NORMA.name(), UnidadFuncionalAmbiente.PSICOPEDAGOGIA_ENFERMERIA.name()});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_DEFICIT_ESTANDAR.name(), UnidadFuncionalAmbiente.PSICOPEDAGOGIA_ENFERMERIA.name()});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_NUMERO_PREDIOS_NO_IDONEOS.name(), UnidadFuncionalAmbiente.PSICOPEDAGOGIA_ENFERMERIA.name()});

            indicadores = facParam.consultarIndicadoresUnidadesFuncionales(indBusqUnidadesFun, nivelConsulta, idEntidad);
            i = 0;
            if (!indicadores.isEmpty()) {
                datosUnidadesFuncionales.put("areaPsicopeEnferm", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "0" : (String) indicadores.get(0)[i]);
                i++;
                datosUnidadesFuncionales.put("m2AlumnosPsicopeEnferm", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "0" : (String) indicadores.get(0)[i]);
                i++;
                datosUnidadesFuncionales.put("cumplimientoPsicopeEnferm", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "" : (String) indicadores.get(0)[i]);
                i++;
                datosUnidadesFuncionales.put("deficitPsicopeEnferm", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "0" : (String) indicadores.get(0)[i]);
                i++;
                datosUnidadesFuncionales.put("numPrediosNoCumplenPsicopeEnferm", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "0" : (String) indicadores.get(0)[i]);
                if (datosUnidadesFuncionales.get("areaPsicopeEnferm") != null) {
                    areaExtension += new Double((String) datosUnidadesFuncionales.get("areaPsicopeEnferm"));
                }
            }
            datosUnidadesFuncionales.put("estandarPsicopeEnferm", facParam.consultarEstandar(ParamEstandar.ESTANDAR_PSICOPEDAGOGIA_ENFERMERIA).getValor() + "");
            indicadores.clear();
            indBusqUnidadesFun.clear();

        }

        indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_AREA_TIPO_ESPACIO.name(), UnidadFuncionalAmbiente.EXPANSIONES_RECREATIVAS.name()});
        indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_M2_ALUMNO.name(), UnidadFuncionalAmbiente.EXPANSIONES_RECREATIVAS.name()});
        indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_CUMPLIMIENTO_NORMA.name(), UnidadFuncionalAmbiente.EXPANSIONES_RECREATIVAS.name()});
        indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_DEFICIT_ESTANDAR.name(), UnidadFuncionalAmbiente.EXPANSIONES_RECREATIVAS.name()});
        indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_NUMERO_PREDIOS_NO_IDONEOS.name(), UnidadFuncionalAmbiente.EXPANSIONES_RECREATIVAS.name()});

        indicadores = facParam.consultarIndicadoresUnidadesFuncionales(indBusqUnidadesFun, nivelConsulta, idEntidad);
        i = 0;
        if (!indicadores.isEmpty()) {
            datosUnidadesFuncionales.put("areaExpRecre", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "0" : (String) indicadores.get(0)[i]);
            i++;
            datosUnidadesFuncionales.put("m2AlumnosExpRecre", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "0" : (String) indicadores.get(0)[i]);
            i++;
            datosUnidadesFuncionales.put("cumplimientoExpRecre", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "" : (String) indicadores.get(0)[i]);
            i++;
            datosUnidadesFuncionales.put("deficitExpRecre", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "0" : (String) indicadores.get(0)[i]);
            i++;
            datosUnidadesFuncionales.put("numPrediosNoCumplenExpRecre", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "0" : (String) indicadores.get(0)[i]);
            if (datosUnidadesFuncionales.get("areaExpRecre") != null) {
                areaExtension += new Double((String) datosUnidadesFuncionales.get("areaExpRecre"));
            }
        }
        datosUnidadesFuncionales.put("estandarExpRecre", facParam.consultarEstandar(ParamEstandar.ESTANDAR_EXPANSIONES_RECREATIVAS).getValor() + "");
        indicadores.clear();
        indBusqUnidadesFun.clear();

        indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_AREA_TIPO_ESPACIO.name(), UnidadFuncionalAmbiente.SERVICIOS_SANITARIOS.name()});
        indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_M2_ALUMNO.name(), UnidadFuncionalAmbiente.SERVICIOS_SANITARIOS.name()});
        indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_CUMPLIMIENTO_NORMA.name(), UnidadFuncionalAmbiente.SERVICIOS_SANITARIOS.name()});
        indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_DEFICIT_ESTANDAR.name(), UnidadFuncionalAmbiente.SERVICIOS_SANITARIOS.name()});
        indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_NUMERO_PREDIOS_NO_IDONEOS.name(), UnidadFuncionalAmbiente.SERVICIOS_SANITARIOS.name()});

        indicadores = facParam.consultarIndicadoresUnidadesFuncionales(indBusqUnidadesFun, nivelConsulta, idEntidad);
        i = 0;
        if (!indicadores.isEmpty()) {
            datosUnidadesFuncionales.put("areaServSanitarios", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "0" : (String) indicadores.get(0)[i]);
            i++;
            datosUnidadesFuncionales.put("m2AlumnosServSanitarios", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "0" : (String) indicadores.get(0)[i]);
            i++;
            datosUnidadesFuncionales.put("cumplimientoServSanitarios", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "" : (String) indicadores.get(0)[i]);
            i++;
            datosUnidadesFuncionales.put("deficitServSanitarios", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "0" : (String) indicadores.get(0)[i]);
            i++;
            datosUnidadesFuncionales.put("numPrediosNoCumplenServSanitarios", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "0" : (String) indicadores.get(0)[i]);
            if (datosUnidadesFuncionales.get("areaServSanitarios") != null) {
                areaServSanitariosTotal += new Double((String) datosUnidadesFuncionales.get("areaServSanitarios"));
            }
        }
        datosUnidadesFuncionales.put("estandarServSanitarios", facParam.consultarEstandar(ParamEstandar.ESTANDAR_SERVICIOS_SANITARIOS).getValor() + "");
        indicadores.clear();
        indBusqUnidadesFun.clear();

        indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_AREA_TIPO_ESPACIO.name(), UnidadFuncionalAmbiente.ALIMENTOS.name()});
        indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_M2_ALUMNO.name(), UnidadFuncionalAmbiente.ALIMENTOS.name()});
        indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_CUMPLIMIENTO_NORMA.name(), UnidadFuncionalAmbiente.ALIMENTOS.name()});
        indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_DEFICIT_ESTANDAR.name(), UnidadFuncionalAmbiente.ALIMENTOS.name()});
        indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_NUMERO_PREDIOS_NO_IDONEOS.name(), UnidadFuncionalAmbiente.ALIMENTOS.name()});

        indicadores = facParam.consultarIndicadoresUnidadesFuncionales(indBusqUnidadesFun, nivelConsulta, idEntidad);
        i = 0;
        if (!indicadores.isEmpty()) {
            datosUnidadesFuncionales.put("areaCocinaCafe", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "0" : (String) indicadores.get(0)[i]);
            i++;
            datosUnidadesFuncionales.put("m2AlumnosCocinaCafe", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "0" : (String) indicadores.get(0)[i]);
            i++;
            datosUnidadesFuncionales.put("cumplimientoCocinaCafe", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "" : (String) indicadores.get(0)[i]);
            i++;
            datosUnidadesFuncionales.put("deficitCocinaCafe", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "0" : (String) indicadores.get(0)[i]);
            i++;
            datosUnidadesFuncionales.put("numPrediosNoCumplenCocinaCafe", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "0" : (String) indicadores.get(0)[i]);
            if (datosUnidadesFuncionales.get("areaCocinaCafe") != null) {
                areaAbastProcesa += new Double((String) datosUnidadesFuncionales.get("areaCocinaCafe"));
            }
        }
        datosUnidadesFuncionales.put("estandarCocinaCafe", facParam.consultarEstandar(ParamEstandar.ESTANDAR_ALIMENTOS).getValor() + "");
        indicadores.clear();
        indBusqUnidadesFun.clear();

        indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_AREA_TIPO_ESPACIO.name(), UnidadFuncionalAmbiente.OFICINA_ADMINISTRACION.name()});
        indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_M2_ALUMNO.name(), UnidadFuncionalAmbiente.OFICINA_ADMINISTRACION.name()});
        indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_CUMPLIMIENTO_NORMA.name(), UnidadFuncionalAmbiente.OFICINA_ADMINISTRACION.name()});
        indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_DEFICIT_ESTANDAR.name(), UnidadFuncionalAmbiente.OFICINA_ADMINISTRACION.name()});
        indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_NUMERO_PREDIOS_NO_IDONEOS.name(), UnidadFuncionalAmbiente.OFICINA_ADMINISTRACION.name()});

        indicadores = facParam.consultarIndicadoresUnidadesFuncionales(indBusqUnidadesFun, nivelConsulta, idEntidad);
        i = 0;
        if (!indicadores.isEmpty()) {
            datosUnidadesFuncionales.put("areaOfAdm", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "0" : (String) indicadores.get(0)[i]);
            i++;
            datosUnidadesFuncionales.put("m2AlumnosOfAdm", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "0" : (String) indicadores.get(0)[i]);
            i++;
            datosUnidadesFuncionales.put("cumplimientoOfAdm", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "" : (String) indicadores.get(0)[i]);
            i++;
            datosUnidadesFuncionales.put("deficitOfAdm", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "0" : (String) indicadores.get(0)[i]);
            i++;
            datosUnidadesFuncionales.put("numPrediosNoCumplenOfAdm", UtilCadena.isNullOVacio((String) indicadores.get(0)[i]) ? "0" : (String) indicadores.get(0)[i]);
            if (datosUnidadesFuncionales.get("areaOfAdm") != null) {
                areaConducAdministra += new Double((String) datosUnidadesFuncionales.get("areaOfAdm"));
            }
        }
        datosUnidadesFuncionales.put("estandarOfAdm", facParam.consultarEstandar(ParamEstandar.ESTANDAR_OFICINA_ADMINISTRACION).getValor() + "");
        indicadores.clear();
        indBusqUnidadesFun.clear();

        Double areasCentrales = areaAulas + areaLaboratorios + areaCentroRecursosAprendizaje + areaTalleres;
        Double areasApoyo = areaExtension + areaServSanitariosTotal + areaAbastProcesa + areaConducAdministra;
        Double areaTotal = areasCentrales + areasApoyo;

        datosUnidadesFuncionales.put("areasCentrales", areasCentrales);
        datosUnidadesFuncionales.put("areasApoyo", areasApoyo);
        datosUnidadesFuncionales.put("areaTotal", areaTotal);
        datosUnidadesFuncionales.put("areaAulas", areaAulas);
        datosUnidadesFuncionales.put("areaLaboratorios", areaLaboratorios);
        datosUnidadesFuncionales.put("areaCentroRecursosAprendizaje", areaCentroRecursosAprendizaje);
        datosUnidadesFuncionales.put("areaTalleres", areaTalleres);
        datosUnidadesFuncionales.put("areaExtension", areaExtension);
        datosUnidadesFuncionales.put("areaServSanitariosTotal", areaServSanitariosTotal);
        datosUnidadesFuncionales.put("areaAbastProcesa", areaAbastProcesa);
        datosUnidadesFuncionales.put("areaConducAdministra", areaConducAdministra);

        if (areaTotal > 0) {
            datosUnidadesFuncionales.put("propAreasCentrales", new BigDecimal(100 * areasCentrales / areaTotal).setScale(2, RoundingMode.HALF_UP));
            datosUnidadesFuncionales.put("propAreasApoyo", new BigDecimal(100 * areasApoyo / areaTotal).setScale(2, RoundingMode.HALF_UP));
            if (areasCentrales > 0) {
                datosUnidadesFuncionales.put("propAreasAulas", new BigDecimal(100 * areaAulas / areaTotal * (100 / ((BigDecimal) datosUnidadesFuncionales.get("propAreasCentrales")).doubleValue())).setScale(2, RoundingMode.HALF_UP));
                datosUnidadesFuncionales.put("propAreasLaboratorios", new BigDecimal(100 * areaLaboratorios / areaTotal * (100 / ((BigDecimal) datosUnidadesFuncionales.get("propAreasCentrales")).doubleValue())).setScale(2, RoundingMode.HALF_UP));
                datosUnidadesFuncionales.put("propAreasCentroRecursosAprendizaje", new BigDecimal(100 * areaCentroRecursosAprendizaje / areaTotal * (100 / ((BigDecimal) datosUnidadesFuncionales.get("propAreasCentrales")).doubleValue())).setScale(2, RoundingMode.HALF_UP));
                datosUnidadesFuncionales.put("propAreasTalleres", new BigDecimal(100 * areaTalleres / areaTotal * (100 / ((BigDecimal) datosUnidadesFuncionales.get("propAreasCentrales")).doubleValue())).setScale(2, RoundingMode.HALF_UP));
            }
            if (areasApoyo > 0) {
                datosUnidadesFuncionales.put("propAreasExtension", new BigDecimal(100 * areaExtension / areaTotal * (100 / ((BigDecimal) datosUnidadesFuncionales.get("propAreasApoyo")).doubleValue())).setScale(2, RoundingMode.HALF_UP));
                datosUnidadesFuncionales.put("propAreasServSanitarios", new BigDecimal(100 * areaServSanitariosTotal / areaTotal * (100 / ((BigDecimal) datosUnidadesFuncionales.get("propAreasApoyo")).doubleValue())).setScale(2, RoundingMode.HALF_UP));
                datosUnidadesFuncionales.put("propAreasAbastProcesa", new BigDecimal(100 * areaAbastProcesa / areaTotal * (100 / ((BigDecimal) datosUnidadesFuncionales.get("propAreasApoyo")).doubleValue())).setScale(2, RoundingMode.HALF_UP));
                datosUnidadesFuncionales.put("propAreasConducAdministra", new BigDecimal(100 * areaConducAdministra / areaTotal * (100 / ((BigDecimal) datosUnidadesFuncionales.get("propAreasApoyo")).doubleValue())).setScale(2, RoundingMode.HALF_UP));
            }
        }

        return datosUnidadesFuncionales;
    }

    private String transformaSiNoNA(String resp) {
        if ((SiNoNA.NA.getValor() + "").equals(resp)) {
            resp = bundle.getString("aplicacion.general.NA");
        } else if ((SiNoNA.Si.getValor() + "").equals(resp)) {
            resp = bundle.getString("aplicacion.general.si");
        } else {
            resp = bundle.getString("aplicacion.general.no");
        }
        return resp;
    }

    private String transformaSiNo(String resp) {
        if ((SiNoNA.Si.getValor() + "").equals(resp)) {
            resp = bundle.getString("aplicacion.general.si");
        } else {
            resp = bundle.getString("aplicacion.general.no");
        }
        return resp;
    }

    private Map obtenerRespuestas(InstrumentoDig instrumentoDig) {
        Map<String, Object> datos = new HashMap<String, Object>();

        List<ModuloIns> modulos = null;
        List<String> respuestas = new ArrayList<String>();
        respuestasEdificios = new ArrayList<String>();
        respuestasEspaciosEscolares = new ArrayList<String>();
        respuestasEstablecimientosAdicionales = new ArrayList<String>();
        respuestaTipologia = new HashMap<String, String>();

        String[] respuestasValoresTipologia = {"RESP_029_01", "RESP_030_01", "RESP_032_01", "RESP_032_02", "RESP_032_03", "RESP_032_04", "RESP_032_05", "RESP_032_06", "RESP_032_07", "RESP_032_08", "RESP_032_09", "RESP_032_10", "RESP_040", "RESP_045_01", "RESP_045_03", "RESP_045_05", "RESP_045_07", "RESP_045_09", "RESP_046_01", "RESP_047_01", "RESP_047_02", "RESP_047_03", "RESP_047_04", "RESP_047_05", "RESP_047_06", "RESP_048_01", "RESP_048_03", "RESP_048_05", "RESP_050_01", "RESP_050_03", "RESP_050_05", "RESP_052_01", "RESP_052_03", "RESP_052_05", "RESP_052_07", "RESP_052_09", "RESP_056_01", "RESP_061_02", "RESP_062_02", "RESP_063_02", "RESP_064_02", "RESP_066_02", "RESP_067_02", "RESP_068_02", "RESP_069_02", "RESP_198_01", "RESP_198_02", "RESP_198_03", "RESP_198_04", "RESP_198_05", "RESP_198_06", "RESP_198_07", "RESP_198_08", "RESP_198_09", "RESP_198_10", "RESP_077_01", "RESP_086_02", "RESP_086_03"};
        List<String> respuestasValoresTipologiaList = new ArrayList<String>();
        Collections.addAll(respuestasValoresTipologiaList, respuestasValoresTipologia);

        modulos = facInstrumentos.obtenerModulosInstrumento(instrumentoDig.getInstrumento());
        for (ModuloIns modulo : modulos) {
            datos.put(modulo.getCodigo(), Utilidades.obtenerMensaje(modulo.getEtiqueta()));
            for (Seccion seccion : modulo.getSeccionList()) {
                datos.put(seccion.getCodigo(), Utilidades.obtenerMensaje(seccion.getEtiqueta()));

                for (Pregunta pregunta : seccion.getPreguntaList()) {
                    for (Respuesta respuesta : pregunta.getRespuestaList()) {
                        //La sección 8 (SEC_RIGEP) corresponde a edificios(subreport) menos las RESP_092 y RESP_093
                        if (pregunta.getSeccion().getCodigo().equals("SEC_RIGEP")
                                && !"RESP_092".equals(respuesta.getCodigo())
                                && !"RESP_093".equals(respuesta.getCodigo())) {
                            respuestasEdificios.add(respuesta.getCodigo());
                        } else if (pregunta.getSeccion().getCodigo().equals("SEC_IEE")) {
                            respuestasEspaciosEscolares.add(respuesta.getCodigo());
                        } else if (pregunta.getSeccion().getCodigo().equals("SEC_DEAFMLSLA")
                                && !"RESP_191_01".equals(respuesta.getCodigo()) && !"RESP_191_02".equals(respuesta.getCodigo())
                                && !"RESP_192".equals(pregunta.getSeccion().getCodigo())) {
                            respuestasEstablecimientosAdicionales.add(respuesta.getCodigo());
                            if (pregunta.getCodigo().equals("PREG_201") || pregunta.getCodigo().equals("PREG_199")) {
                                respuestas.add(respuesta.getCodigo());
                            }
                        } else {
                            respuestas.add(respuesta.getCodigo());
                        }

                        if (respuestasValoresTipologiaList.contains(respuesta.getCodigo())) {
                            respuestaTipologia.put(respuesta.getCodigo(), respuesta.getTipologia().getCodigo());
                        }
                    }//fin for RespuestaList
                }//fin for PreguntaList

            }//fin for getSeccionList
        }//fin for modulos

        datos = obtenerRespuestasDigitadas(datos, instrumentoDig.getId(), respuestas);

        //Cambiamos los casos excepcionales en los que hay que cambar el valor de bbdd porque es un código
        for (String resp : respuestasValoresTipologia) {
            if (datos.containsKey(resp)) {
                TipologiaValorNombre tv = null;
                if (!UtilCadena.isNullOVacio(respuestaTipologia.get(resp)) && !UtilCadena.isNullOVacio(datos.get(resp))) {
                    tv = facParam.consultarValorTipologia(respuestaTipologia.get(resp), datos.get(resp).toString(), getUsuarioSesion().getUsuario().getIdioma().getId());
                }
                datos.put(resp, tv != null ? tv.getNombre() : "");
            }
        }

        return datos;
    }

    @Override
    public void resetearFiltros() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cambioPeriodo() {
        try {
            super.initHistoricos(periodo);
            super.cargarNivel1();
        } catch (Exception e) {
            ManejadorErrores.ingresarExcepcionEnLog(e, InformeGeneralBean.class);
        }
    }

    private Nivel getIdNivel0(Nivel nivelAux) {
        Nivel idNivel0 = null;
        if (nivelAux.getPadre() == null) {
            idNivel0 = nivelAux;
        }
        while (idNivel0 == null) {
            nivelAux = nivelAux.getPadre();
            if (nivelAux.getPadre() == null) {
                idNivel0 = nivelAux;
            }
        }
        return idNivel0;
    }

    public boolean posibilidadDosInformes(Long nivel) {
        return nivel < this.maximoNivelDPA;
    }
}
