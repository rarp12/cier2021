/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.gerenciales;

import co.stackpointer.cier.modelo.entidad.BID.AmbitosBID;
import co.stackpointer.cier.modelo.entidad.BID.DatosInformeBID;
import co.stackpointer.cier.modelo.entidad.BID.IndicadoresBID;
import co.stackpointer.cier.modelo.entidad.BID.PaisesBID;
import co.stackpointer.cier.modelo.entidad.BID.PeriodosBID;
import co.stackpointer.cier.modelo.fachada.BIDFacadeLocal;
import co.stackpointer.cier.modelo.tipo.Ambitos;
import co.stackpointer.cier.modelo.tipo.TipoIndicadorBID;
import co.stackpointer.cier.modelo.utilidad.UtilCadena;
import co.stackpointer.cier.modelo.utilidad.UtilExportar;
import co.stackpointer.cier.modelo.utilidad.UtilOut;
import static co.stackpointer.cier.vista.Utilidades.mostrarMensajeError;
import co.stackpointer.cier.vistacontrolador.bean.GestionArchivos;
import co.stackpointer.cier.vistacontrolador.consulta.BID.DatosPais;
import co.stackpointer.cier.vistacontrolador.consulta.BID.ReporteInformeGeneralDetallado;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import net.sf.jasperreports.engine.JRParameter;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

    
/**
 *
 * @author user
 */
@ManagedBean(name = "consultaCalificacionBID")
@ViewScoped
public class ConsultaCalificacionBIDBean {
    
    @EJB
    private BIDFacadeLocal fachadaBID; 
    
    private List<Registro> lista;
    List<String> colums;
    List<String> columsTotal;
    
    String mensajeTablaVacia;    
    
    public PeriodosBID periodoBID;
    public List<PeriodosBID> listaPeriodos;
    ResourceBundle bundle = ResourceBundle.getBundle("etiquetas", FacesContext.getCurrentInstance().getViewRoot().getLocale());
    String errorAplicacion = bundle.getString("aplicacion.general.errorAplicacion");        
    
    @PostConstruct
    public void inicializar() {
        lista=new ArrayList<Registro>();
        colums=new ArrayList<String>();
        columsTotal=new ArrayList<String>();
        periodoBID=null;
        listaPeriodos=fachadaBID.getPeriodosBID();
        for(PeriodosBID p:listaPeriodos){
            if(periodoBID==null
                    || p.getId()>periodoBID.getId()){
                periodoBID=p;
            }
        }
    }

       
    public void consultar() {
        try{
            ResourceBundle bundle = ResourceBundle.getBundle("etiquetas", FacesContext.getCurrentInstance().getViewRoot().getLocale());
            List<AmbitosBID> ambitosBID=fachadaBID.obtenerAmbitosBID();
            HashMap hmPonderaciones=new HashMap();
            
            BigDecimal suma=new BigDecimal("0");
            for(AmbitosBID amb:ambitosBID){
                hmPonderaciones.put(amb.getCodigo(), amb.getPonderacion());
                suma=suma.add(amb.getPonderacion()!=-1?new BigDecimal(amb.getPonderacion()).setScale(1, RoundingMode.HALF_UP):new BigDecimal(0));
            }
            
            String accesosExt=hmPonderaciones.get(Ambitos.A_ACC.toString())+"%"+ bundle.getString("consultas.calificacion.accesosExt");
            String accesosInt=hmPonderaciones.get(Ambitos.A_ACCD.toString())+"%"+ bundle.getString("consultas.calificacion.accesosInt");
            String ambientes=hmPonderaciones.get(Ambitos.A_AMB.toString())+"%"+bundle.getString("consultas.calificacion.ambientes");
            String confort=hmPonderaciones.get(Ambitos.A_CON.toString())+"%"+bundle.getString("consultas.calificacion.confort");
            String controlVigilancia=hmPonderaciones.get(Ambitos.A_CV.toString())+"%"+bundle.getString("consultas.calificacion.controlVigilancia");
            String estadoEdificiosEspacios=hmPonderaciones.get(Ambitos.A_EEE.toString())+"%"+bundle.getString("consultas.calificacion.estadoEdificiosEspacios");
            String oferta=hmPonderaciones.get(Ambitos.A_OFE.toString())+"%"+ bundle.getString("consultas.calificacion.oferta");
            String propiedad=hmPonderaciones.get(Ambitos.A_PROP.toString())+"%"+ bundle.getString("consultas.calificacion.propiedad");
            String riesgos=hmPonderaciones.get(Ambitos.A_RI.toString())+"%"+ bundle.getString("consultas.calificacion.riesgos");
            String seguridad=hmPonderaciones.get(Ambitos.A_SEG.toString())+"%"+bundle.getString("consultas.calificacion.seguridad");
            String servicios=hmPonderaciones.get(Ambitos.A_SP.toString())+"%"+bundle.getString("consultas.calificacion.servicios");
            String sostenibilidad=hmPonderaciones.get(Ambitos.A_SOS.toString())+"%"+bundle.getString("consultas.calificacion.sostenibilidad");
            //SSOLANO
            String serviciosSanitarios=hmPonderaciones.get(Ambitos.A_SS.toString())+"%"+bundle.getString("consultas.calificacion.serviciosSanitarios");

            String calificacionTotal=suma+"%"+bundle.getString("consultas.calificacion.calificacionTotal");
            List<Object[]> resultados=new ArrayList<Object[]>();
            if(periodoBID!=null){
                resultados = fachadaBID.consultarCalificacionesBID(periodoBID.getCodigo());
            }
            lista = new ArrayList<Registro>(resultados.size());
            if(lista.size()<1)
            {
                mensajeTablaVacia = bundle.getString("aplicacion.general.noExistenRegistros");
            }
            Registro registro=null;
            String valor="";
            for (Object[] fila : resultados) {
                suma=new BigDecimal("0");
                registro = new Registro();
                int i = 0;
                HashMap<String, String> hmAux=new HashMap<String, String>();
                    registro.setId(new Long(fila[i++]+""));
                    registro.setCod(""+fila[i++]);
                    
                    hmAux.put(bundle.getString("gerencial.bid.consulta.calificacionesBID.pais"),UtilCadena.isNullOVacio(fila[i])?"":""+fila[i]);
                    i++;
                    hmAux.put(bundle.getString("gerencial.bid.consulta.calificacionesBID.periodoInfo"),UtilCadena.isNullOVacio(fila[i])?"":""+fila[i]);
                    i++;
                
                valor=fila[i]!=null?fila[i].toString():"";
                suma=suma.add(fila[i]!=null?(new BigDecimal(hmPonderaciones.get(Ambitos.A_ACC.toString()).toString()).divide(new BigDecimal(100))).multiply(new BigDecimal(fila[i].toString())):new BigDecimal(0));
                hmAux.put(accesosExt,valor);
                i++;
                valor=fila[i]!=null?fila[i].toString():"";
                suma=suma.add(fila[i]!=null?(new BigDecimal(hmPonderaciones.get(Ambitos.A_ACCD.toString()).toString()).divide(new BigDecimal(100))).multiply(new BigDecimal(fila[i].toString())):new BigDecimal(0));
                hmAux.put(accesosInt,valor);
                i++;
                valor=fila[i]!=null?fila[i].toString():"";
                suma=suma.add(fila[i]!=null?(new BigDecimal(hmPonderaciones.get(Ambitos.A_AMB.toString()).toString()).divide(new BigDecimal(100))).multiply(new BigDecimal(fila[i].toString())):new BigDecimal(0));
                hmAux.put(ambientes,valor);
                i++;
                valor=fila[i]!=null?fila[i].toString():"";
                suma=suma.add(fila[i]!=null?(new BigDecimal(hmPonderaciones.get(Ambitos.A_CON.toString()).toString()).divide(new BigDecimal(100))).multiply(new BigDecimal(fila[i].toString())):new BigDecimal(0));
                hmAux.put(confort,valor);
                i++;
                valor=fila[i]!=null?fila[i].toString():"";
                suma=suma.add(fila[i]!=null?(new BigDecimal(hmPonderaciones.get(Ambitos.A_CV.toString()).toString()).divide(new BigDecimal(100))).multiply(new BigDecimal(fila[i].toString())):new BigDecimal(0));
                hmAux.put(controlVigilancia,valor);
                i++;
                valor=fila[i]!=null?fila[i].toString():"";
                suma=suma.add(fila[i]!=null?(new BigDecimal(hmPonderaciones.get(Ambitos.A_EEE.toString()).toString()).divide(new BigDecimal(100))).multiply(new BigDecimal(fila[i].toString())):new BigDecimal(0));
                hmAux.put(estadoEdificiosEspacios,valor);
                i++;
                valor=fila[i]!=null?fila[i].toString():"";
                suma=suma.add(fila[i]!=null?(new BigDecimal(hmPonderaciones.get(Ambitos.A_OFE.toString()).toString()).divide(new BigDecimal(100))).multiply(new BigDecimal(fila[i].toString())):new BigDecimal(0));
                hmAux.put(oferta,valor);
                i++;
                valor=fila[i]!=null?fila[i].toString():"";
                suma=suma.add(fila[i]!=null?(new BigDecimal(hmPonderaciones.get(Ambitos.A_PROP.toString()).toString()).divide(new BigDecimal(100))).multiply(new BigDecimal(fila[i].toString())):new BigDecimal(0));
                hmAux.put(propiedad,valor);
                i++;
                valor=fila[i]!=null?"-"+fila[i].toString():"";
                suma=suma.add(fila[i]!=null?(new BigDecimal(hmPonderaciones.get(Ambitos.A_RI.toString()).toString()).divide(new BigDecimal(100))).multiply(new BigDecimal("-"+fila[i].toString())):new BigDecimal(0));
                hmAux.put(riesgos,valor);
                i++;
                valor=fila[i]!=null?fila[i].toString():"";
                suma=suma.add(fila[i]!=null?(new BigDecimal(hmPonderaciones.get(Ambitos.A_SEG.toString()).toString()).divide(new BigDecimal(100))).multiply(new BigDecimal(fila[i].toString())):new BigDecimal(0));
                hmAux.put(seguridad,valor);
                i++;
                valor=fila[i]!=null?fila[i].toString():"";
                suma=suma.add(fila[i]!=null?(new BigDecimal(hmPonderaciones.get(Ambitos.A_SP.toString()).toString()).divide(new BigDecimal(100))).multiply(new BigDecimal(fila[i].toString())):new BigDecimal(0));
                hmAux.put(servicios,valor);
                i++;
                valor=fila[i]!=null?fila[i].toString():"";
                suma=suma.add(fila[i]!=null?(new BigDecimal(hmPonderaciones.get(Ambitos.A_SOS.toString()).toString()).divide(new BigDecimal(100))).multiply(new BigDecimal(fila[i].toString())):new BigDecimal(0));
                hmAux.put(sostenibilidad,valor);                
                i++;
                valor=fila[i]!=null?fila[i].toString():"";
                suma=suma.add(fila[i]!=null?(new BigDecimal(hmPonderaciones.get(Ambitos.A_SS.toString()).toString()).divide(new BigDecimal(100))).multiply(new BigDecimal(fila[i].toString())):new BigDecimal(0));
                hmAux.put(serviciosSanitarios,valor);
                
                hmAux.put(calificacionTotal,suma.setScale(2, RoundingMode.HALF_UP).toString());//String.format("%.2f", suma));
                
                registro.setInd_nombre(hmAux);
                lista.add(registro);
            }
            
            
            columsTotal=new ArrayList<String>();
            columsTotal.add(bundle.getString("gerencial.bid.consulta.calificacionesBID.pais"));
            columsTotal.add(bundle.getString("gerencial.bid.consulta.calificacionesBID.periodoInfo"));
            
            columsTotal.add(accesosExt);
            columsTotal.add(accesosInt);
            columsTotal.add(ambientes);
            columsTotal.add(confort);
            columsTotal.add(controlVigilancia);
            columsTotal.add(estadoEdificiosEspacios);
            columsTotal.add(oferta);
            columsTotal.add(propiedad);
            columsTotal.add(riesgos);
            columsTotal.add(seguridad);
            columsTotal.add(servicios);
            columsTotal.add(sostenibilidad);
            columsTotal.add(serviciosSanitarios);
            columsTotal.add(calificacionTotal);
            
            colums=new ArrayList<String>();
            colums.add(bundle.getString("gerencial.bid.consulta.calificacionesBID.pais"));
            colums.add(bundle.getString("gerencial.bid.consulta.calificacionesBID.periodoInfo"));
            colums.add(accesosExt);
            colums.add(accesosInt);
            colums.add(ambientes);
            colums.add(confort);
            colums.add(controlVigilancia);
            colums.add(estadoEdificiosEspacios);
            colums.add(oferta);
            colums.add(propiedad);
            colums.add(riesgos);
            colums.add(seguridad);
            colums.add(servicios);
            colums.add(sostenibilidad);            
            colums.add(serviciosSanitarios);
            colums.add(calificacionTotal);
            
            
        }catch(Exception e){
            UtilOut.println(e);
            mostrarMensajeError(errorAplicacion, e.getMessage());
        }
    }
    
    public String separarPonderacion(String dato)
    {
        if (dato.indexOf("%")>=0)
         {
             return dato.substring(0, dato.indexOf("%")+1);
         }else
         {
         return ""; 
         }

    }
    
    public String separarNombre(String dato)
    {
        if (dato.indexOf("%")>=0)
         {
             return dato.substring(dato.indexOf("%")+1, dato.length());
         }else
         {
         return dato; 
         }

    }
    
    public void imprimirPdf(Long id, String cod)
    {
        try{
            Map<String, Object> parametros= new HashMap<String, Object>();
            List list=new ArrayList();
            list.add("");

            List<DatosInformeBID> listaDatosInforme=fachadaBID.consultarDatosInformeBID(periodoBID, id);

            for(DatosInformeBID di:listaDatosInforme){
                parametros.put(di.getCodigo(), di.getValor());
            }
            
            PaisesBID pais=fachadaBID.obtenerPaisBIDPorId(id.intValue());
            parametros.put("pais",pais.getNombre());
            
            parametros.put(JRParameter.REPORT_RESOURCE_BUNDLE, ResourceBundle.getBundle("etiquetas", FacesContext.getCurrentInstance().getViewRoot().getLocale()));


            UtilExportar.exportarInformeGeneral("BID", "InformeGeneralBID", "InformeGeneralBID", parametros, list);
        
        }catch(Exception e){
            UtilOut.println(bundle.getString("aplicacion.general.errorImprimirPDF")+": "+e.getMessage());
            mostrarMensajeError(errorAplicacion, e.getMessage());
            UtilOut.println(e);
        }

    }
    
    
    public void imprimirPdfDetallado(){
        List<DatosPais> listaDatosPaises=new ArrayList<DatosPais>();
        Map<String, Object> parametros=new HashMap<String, Object>();
        try{
            if(!lista.isEmpty()){
                int numEstablecimientosTotal=0;
                int numSedesTotal=0;
                int numPrediosTotal=0;
                BigDecimal superficieTotal=new BigDecimal(0);
                BigDecimal areaConstrucPrimerPisoTotal=new BigDecimal(0);
                BigDecimal areaConstruccionTotal=new BigDecimal(0);
                int accesibilidadPoblacionAfectadaTotal=0;
                int numPrediosAccesibilidadInadecuadaTotal=0;
                int numPrediosConRiesgosTotal=0;
                int poblacionAfectadaRiesgosTotal=0;
                int estudiantesProblemaLegalizacionTotal=0;
                int prediosProblemasLegalizacionTotal=0;
                int numPrediosConAguaTotal=0;
                int numPrediosConEETotal=0;
                BigDecimal promedioConsumoAguaTotal=new BigDecimal(0);
                int numPrediosCumplenConsumoAguaTotal=0;
                BigDecimal promedioConsumoEETotal=new BigDecimal(0);
                int numPrediosCumplenConsumoEETotal=0;
                int numPrediosVulnerablesTotal=0;
                int numPrediosEstrucCriticaTotal=0;
                BigDecimal m2PrediosEstadoEstrucCriticoTotal=new BigDecimal(0);
                int numPrediosRutaEvacCriticaTotal=0;
                int numPrediosSenalizaEvacCriticaTotal=0;
                int numPrediosNoRactivoIncendioTotal=0;
                BigDecimal m2RequeridosTotal=new BigDecimal(0);
                BigDecimal construiblePrimerPisoTotal=new BigDecimal(0);
                BigDecimal construibleAlturaTotal=new BigDecimal(0);
                BigDecimal construibleTotalTotal=new BigDecimal(0);
                BigDecimal m2AmpliarCoberturaTotal=new BigDecimal(0);
                BigDecimal alumnosNuevosInfraestructuraTotal=new BigDecimal(0);
                BigDecimal cuposTotalTotal=new BigDecimal(0);
                BigDecimal propPrediosAguaInsuficienteTotal=new BigDecimal(0);
                BigDecimal propPrediosEEInsuficienteTotal=new BigDecimal(0);

                for(Registro reg:lista){
                    List<DatosInformeBID> listaDatosInforme=fachadaBID.consultarDatosInformeBID(periodoBID, reg.getId());

                    DatosPais dp=new DatosPais();
                    PaisesBID pais=fachadaBID.obtenerPaisBIDPorId(reg.getId().intValue());
                    dp.setPais(pais.getNombre());
                    dp.setCodigoPais(pais.getCodigo());
                    for(DatosInformeBID di:listaDatosInforme){
                        if(di.getCodigo().equals("numEstablecimientos")){
                            dp.setNumEstablecimientos(di.getValor());
                            numEstablecimientosTotal+=di.getValor().isEmpty()?0:new Integer(di.getValor());
                        }else if(di.getCodigo().equals("numSedes")){
                            dp.setNumSedes(di.getValor());
                            numSedesTotal+=di.getValor().isEmpty()?0:new Integer(di.getValor());
                        }else if(di.getCodigo().equals("numPredios")){
                            dp.setNumPredios(di.getValor());
                            numPrediosTotal+=di.getValor().isEmpty()?0:new Integer(di.getValor());
                        }else if(di.getCodigo().equals("areaAula")){
                            dp.setAreaAula(di.getValor());
                        }else if(di.getCodigo().equals("estandarAula")){
                            dp.setEstandarAula(di.getValor());
                        }else if(di.getCodigo().equals("m2AlumnosAula")){
                            dp.setM2AlumnosAula(di.getValor());
                        }else if(di.getCodigo().equals("cumplimientoAula")){
                            dp.setCumplimientoAula(di.getValor());
                        }else if(di.getCodigo().equals("deficitAula")){
                            dp.setDeficitAula(di.getValor());
                        }else if(di.getCodigo().equals("areaLabCiencias")){
                            dp.setAreaLabCiencias(di.getValor());
                        }else if(di.getCodigo().equals("estandarLabCiencias")){
                            dp.setEstandarLabCiencias(di.getValor());
                        }else if(di.getCodigo().equals("m2AlumnosLabCiencias")){
                            dp.setM2AlumnosLabCiencias(di.getValor());
                        }else if(di.getCodigo().equals("cumplimientoLabCiencias")){
                            dp.setCumplimientoLabCiencias(di.getValor());
                        }else if(di.getCodigo().equals("deficitLabCiencias")){
                            dp.setDeficitLabCiencias(di.getValor());
                        }else if(di.getCodigo().equals("areaLabMultimedia")){
                            dp.setAreaLabMultimedia(di.getValor());
                        }else if(di.getCodigo().equals("estandarLabMultimedia")){
                            dp.setEstandarLabMultimedia(di.getValor());
                        }else if(di.getCodigo().equals("m2AlumnosLabMultimedia")){
                            dp.setM2AlumnosLabMultimedia(di.getValor());
                        }else if(di.getCodigo().equals("cumplimientoLabMultimedia")){
                            dp.setCumplimientoLabMultimedia(di.getValor());
                        }else if(di.getCodigo().equals("deficitLabMultimedia")){
                            dp.setDeficitLabMultimedia(di.getValor());
                        }else if(di.getCodigo().equals("areaLabComputacion")){
                            dp.setAreaLabComputacion(di.getValor());
                        }else if(di.getCodigo().equals("estandarLabComputacion")){
                            dp.setEstandarLabComputacion(di.getValor());
                        }else if(di.getCodigo().equals("m2AlumnosLabComputacion")){
                            dp.setM2AlumnosLabComputacion(di.getValor());
                        }else if(di.getCodigo().equals("cumplimientoLabComputacion")){
                            dp.setCumplimientoLabComputacion(di.getValor());
                        }else if(di.getCodigo().equals("deficitLabComputacion")){
                            dp.setDeficitLabComputacion(di.getValor());
                        }else if(di.getCodigo().equals("areaBiblioteca")){
                            dp.setAreaBiblioteca(di.getValor());
                        }else if(di.getCodigo().equals("estandarBiblioteca")){
                            dp.setEstandarBiblioteca(di.getValor());
                        }else if(di.getCodigo().equals("m2AlumnosBiblioteca")){
                            dp.setM2AlumnosBiblioteca(di.getValor());
                        }else if(di.getCodigo().equals("cumplimientoBiblioteca")){
                            dp.setCumplimientoBiblioteca(di.getValor());
                        }else if(di.getCodigo().equals("deficitBiblioteca")){
                            dp.setDeficitBiblioteca(di.getValor());
                        }else if(di.getCodigo().equals("areaSalaMusica")){
                            dp.setAreaSalaMusica(di.getValor());
                        }else if(di.getCodigo().equals("estandarSalaMusica")){
                            dp.setEstandarSalaMusica(di.getValor());
                        }else if(di.getCodigo().equals("m2AlumnosSalaMusica")){
                            dp.setM2AlumnosSalaMusica(di.getValor());
                        }else if(di.getCodigo().equals("cumplimientoSalaMusica")){
                            dp.setCumplimientoSalaMusica(di.getValor());
                        }else if(di.getCodigo().equals("deficitSalaMusica")){
                            dp.setDeficitSalaMusica(di.getValor());
                        }else if(di.getCodigo().equals("areaSalaArte")){
                            dp.setAreaSalaArte(di.getValor());
                        }else if(di.getCodigo().equals("estandarSalaArte")){
                            dp.setEstandarSalaArte(di.getValor());
                        }else if(di.getCodigo().equals("m2AlumnosSalaArte")){
                            dp.setM2AlumnosSalaArte(di.getValor());
                        }else if(di.getCodigo().equals("cumplimientoSalaArte")){
                            dp.setCumplimientoSalaArte(di.getValor());
                        }else if(di.getCodigo().equals("deficitSalaArte")){
                            dp.setDeficitSalaArte(di.getValor());
                        }else if(di.getCodigo().equals("areaPlayonDepor")){
                            dp.setAreaPlayonDepor(di.getValor());
                        }else if(di.getCodigo().equals("estandarPlayonDepor")){
                            dp.setEstandarPlayonDepor(di.getValor());
                        }else if(di.getCodigo().equals("m2AlumnosPlayonDepor")){
                            dp.setM2AlumnosPlayonDepor(di.getValor());
                        }else if(di.getCodigo().equals("cumplimientoPlayonDepor")){
                            dp.setCumplimientoPlayonDepor(di.getValor());
                        }else if(di.getCodigo().equals("deficitPlayonDepor")){
                            dp.setDeficitPlayonDepor(di.getValor());
                        }else if(di.getCodigo().equals("areaExpRecre")){
                            dp.setAreaExpRecre(di.getValor());
                        }else if(di.getCodigo().equals("estandarExpRecre")){
                            dp.setEstandarExpRecre(di.getValor());
                        }else if(di.getCodigo().equals("m2AlumnosExpRecre")){
                            dp.setM2AlumnosExpRecre(di.getValor());
                        }else if(di.getCodigo().equals("cumplimientoExpRecre")){
                            dp.setCumplimientoExpRecre(di.getValor());
                        }else if(di.getCodigo().equals("deficitExpRecre")){
                            dp.setDeficitExpRecre(di.getValor());
                        }else if(di.getCodigo().equals("areaSUM")){
                            dp.setAreaSUM(di.getValor());
                        }else if(di.getCodigo().equals("estandarSUM")){
                            dp.setEstandarSUM(di.getValor());
                        }else if(di.getCodigo().equals("m2AlumnosSUM")){
                            dp.setM2AlumnosSUM(di.getValor());
                        }else if(di.getCodigo().equals("cumplimientoSUM")){
                            dp.setCumplimientoSUM(di.getValor());
                        }else if(di.getCodigo().equals("deficitSUM")){
                            dp.setDeficitSUM(di.getValor());
                        }else if(di.getCodigo().equals("areaPsicopeEnferm")){
                            dp.setAreaPsicopeEnferm(di.getValor());
                        }else if(di.getCodigo().equals("estandarPsicopeEnferm")){
                            dp.setEstandarPsicopeEnferm(di.getValor());
                        }else if(di.getCodigo().equals("m2AlumnosPsicopeEnferm")){
                            dp.setM2AlumnosPsicopeEnferm(di.getValor());
                        }else if(di.getCodigo().equals("cumplimientoPsicopeEnferm")){
                            dp.setCumplimientoPsicopeEnferm(di.getValor());
                        }else if(di.getCodigo().equals("deficitPsicopeEnferm")){
                            dp.setDeficitPsicopeEnferm(di.getValor());
                        }else if(di.getCodigo().equals("areaServSanitarios")){
                            dp.setAreaServSanitarios(di.getValor());
                        }else if(di.getCodigo().equals("estandarServSanitarios")){
                            dp.setEstandarServSanitarios(di.getValor());
                        }else if(di.getCodigo().equals("m2AlumnosServSanitarios")){
                            dp.setM2AlumnosServSanitarios(di.getValor());
                        }else if(di.getCodigo().equals("cumplimientoServSanitarios")){
                            dp.setCumplimientoServSanitarios(di.getValor());
                        }else if(di.getCodigo().equals("deficitServSanitarios")){
                            dp.setDeficitServSanitarios(di.getValor());
                        }else if(di.getCodigo().equals("areaCocinaCafe")){
                            dp.setAreaCocinaCafe(di.getValor());
                        }else if(di.getCodigo().equals("estandarCocinaCafe")){
                            dp.setEstandarCocinaCafe(di.getValor());
                        }else if(di.getCodigo().equals("m2AlumnosCocinaCafe")){
                            dp.setM2AlumnosCocinaCafe(di.getValor());
                        }else if(di.getCodigo().equals("cumplimientoCocinaCafe")){
                            dp.setCumplimientoCocinaCafe(di.getValor());
                        }else if(di.getCodigo().equals("deficitCocinaCafe")){
                            dp.setDeficitCocinaCafe(di.getValor());
                        }else if(di.getCodigo().equals("areaOfAdm")){
                            dp.setAreaOfAdm(di.getValor());
                        }else if(di.getCodigo().equals("estandarOfAdm")){
                            dp.setEstandarOfAdm(di.getValor());
                        }else if(di.getCodigo().equals("m2AlumnosOfAdm")){
                            dp.setM2AlumnosOfAdm(di.getValor());
                        }else if(di.getCodigo().equals("cumplimientoOfAdm")){
                            dp.setCumplimientoOfAdm(di.getValor());
                        }else if(di.getCodigo().equals("deficitOfAdm")){
                            dp.setDeficitOfAdm(di.getValor());
                        }else if(di.getCodigo().equals("calif_accesosExt")){
                            dp.setCalif_accesosExt(di.getValor());
                        }else if(di.getCodigo().equals("desc_calif_accesosExt")){
                            dp.setDesc_calif_accesosExt(di.getValor());
                        }else if(di.getCodigo().equals("calif_riesgos")){
                            dp.setCalif_riesgos(di.getValor());
                        }else if(di.getCodigo().equals("calif_servicios")){
                            dp.setCalif_servicios(di.getValor());
                        }else if(di.getCodigo().equals("calif_ambientes")){
                            dp.setCalif_ambientes(di.getValor());
                        }else if(di.getCodigo().equals("calif_accesosInt")){
                            dp.setCalif_accesosInt(di.getValor());
                        }else if(di.getCodigo().equals("calif_propiedad")){
                            dp.setCalif_propiedad(di.getValor());
                        }else if(di.getCodigo().equals("calif_estadoEdificiosEspacios")){
                            dp.setCalif_estadoEdificiosEspacios(di.getValor());
                        }else if(di.getCodigo().equals("calif_confort")){
                            dp.setCalif_confort(di.getValor());
                        }else if(di.getCodigo().equals("calif_controlVigilancia")){
                            dp.setCalif_controlVigilancia(di.getValor());
                        }else if(di.getCodigo().equals("calif_oferta")){
                            dp.setCalif_oferta(di.getValor());
                        }else if(di.getCodigo().equals("calif_seguridad")){
                            dp.setCalif_seguridad(di.getValor());
                        }else if(di.getCodigo().equals("calif_sostenibilidad")){
                            dp.setCalif_sostenibilidad(di.getValor());
                        }else if(di.getCodigo().equals("calif_calificacionTotal")){
                            dp.setCalif_calificacionTotal(di.getValor());
                        }else if(di.getCodigo().equals("desc_calif_riesgos")){
                            dp.setDesc_calif_riesgos(di.getValor());
                        }else if(di.getCodigo().equals("desc_calif_servicios")){
                            dp.setDesc_calif_servicios(di.getValor());
                        }else if(di.getCodigo().equals("desc_calif_ambientes")){
                            dp.setDesc_calif_ambientes(di.getValor());
                        }else if(di.getCodigo().equals("desc_calif_accesosInt")){
                            dp.setDesc_calif_accesosInt(di.getValor());
                        }else if(di.getCodigo().equals("desc_calif_propiedad")){
                            dp.setDesc_calif_propiedad(di.getValor());
                        }else if(di.getCodigo().equals("desc_calif_estadoEdificiosEspacios")){
                            dp.setDesc_calif_estadoEdificiosEspacios(di.getValor());
                        }else if(di.getCodigo().equals("desc_calif_confort")){
                            dp.setDesc_calif_confort(di.getValor());
                        }else if(di.getCodigo().equals("desc_calif_controlVigilancia")){
                            dp.setDesc_calif_controlVigilancia(di.getValor());
                        }else if(di.getCodigo().equals("desc_calif_oferta")){
                            dp.setDesc_calif_oferta(di.getValor());
                        }else if(di.getCodigo().equals("desc_calif_seguridad")){
                            dp.setDesc_calif_seguridad(di.getValor());
                        }else if(di.getCodigo().equals("desc_calif_sostenibilidad")){
                            dp.setDesc_calif_sostenibilidad(di.getValor());
                        }else if(di.getCodigo().equals("calif_servSanitarios")){
                            dp.setCalif_servSanitarios(di.getValor());
                        }else if(di.getCodigo().equals("desc_calif_servSanitarios")){
                            dp.setDesc_calif_servSanitarios(di.getValor());
                        }else if(di.getCodigo().equals("CondMat1") && !parametros.containsKey("CondMat1")){
                            parametros.put("CondMat1",di.getValor());
                        }else if(di.getCodigo().equals("CondMat2") && !parametros.containsKey("CondMat2")){
                            parametros.put("CondMat2",di.getValor());
                        }else if(di.getCodigo().equals("CondMat3") && !parametros.containsKey("CondMat3")){
                            parametros.put("CondMat3",di.getValor());
                        }else if(di.getCodigo().equals("CondMat4") && !parametros.containsKey("CondMat4")){
                            parametros.put("CondMat4",di.getValor());
                        }else if(di.getCodigo().equals("propAreasCentrales")){
                            dp.setPropAreasCentrales(di.getValor());
                        }else if(di.getCodigo().equals("propAreasApoyo")){
                            dp.setPropAreasApoyo(di.getValor());
                        }else if(di.getCodigo().equals("propAreasAulas")){
                            dp.setPropAreasAulas(di.getValor());
                        }else if(di.getCodigo().equals("propAreasLaboratorios")){
                            dp.setPropAreasLaboratorios(di.getValor());
                        }else if(di.getCodigo().equals("propAreasCentroRecursosAprendizaje")){
                            dp.setPropAreasCentroRecursosAprendizaje(di.getValor());
                        }else if(di.getCodigo().equals("propAreasTalleres")){
                            dp.setPropAreasTalleres(di.getValor());
                        }else if(di.getCodigo().equals("propAreasExtension")){
                            dp.setPropAreasExtension(di.getValor());
                        }else if(di.getCodigo().equals("propAreasServSanitarios")){
                            dp.setPropAreasServSanitarios(di.getValor());
                        }else if(di.getCodigo().equals("propAreasAbastProcesa")){
                            dp.setPropAreasAbastProcesa(di.getValor());
                        }else if(di.getCodigo().equals("propAreasConducAdministra")){
                            dp.setPropAreasConducAdministra(di.getValor());
                        }else if(di.getCodigo().equals("fechaCorte") && !parametros.containsKey("fechaCorte")){
                            parametros.put("fechaCorte",di.getValor());
                        }else if(di.getCodigo().equals("totalSuperficie")){
                            dp.setSuperficie(di.getValor());
                            superficieTotal=superficieTotal.add(di.getValor().isEmpty()?new BigDecimal(0):new BigDecimal(di.getValor()));
                        }else if(di.getCodigo().equals("totalConstruccion")){
                            dp.setAreaConstruccionTotal(new BigDecimal(di.getValor()).setScale(2, RoundingMode.HALF_UP)+"");
                            areaConstruccionTotal=areaConstruccionTotal.add(di.getValor().isEmpty()?new BigDecimal(0):new BigDecimal(di.getValor()));
                        }else if(di.getCodigo().equals("totalConstrucPrimerPiso")){
                            dp.setAreaConstrucPrimerPiso(new BigDecimal(di.getValor()).setScale(2, RoundingMode.HALF_UP)+"");
                            areaConstrucPrimerPisoTotal=areaConstrucPrimerPisoTotal.add(di.getValor().isEmpty()?new BigDecimal(0):new BigDecimal(di.getValor()));
                        }else if(di.getCodigo().equals("totalConstruibleAltura")){
                            dp.setConstruibleAltura(di.getValor());
                            construibleAlturaTotal=construibleAlturaTotal.add(di.getValor().isEmpty()?new BigDecimal(0):new BigDecimal(di.getValor()));
                        }else if(di.getCodigo().equals("totalConstruibleTotal")){
                            dp.setConstruibleTotal(di.getValor());
                            construibleTotalTotal=construibleTotalTotal.add(di.getValor().isEmpty()?new BigDecimal(0):new BigDecimal(di.getValor()));
                        }else if(di.getCodigo().equals("totalPrediosAccesibilidadInadecuada")){
                            dp.setNumPrediosAccesibilidadInadecuada(di.getValor());
                            numPrediosAccesibilidadInadecuadaTotal+=di.getValor().isEmpty()?0:new Integer(di.getValor());
                        }else if(di.getCodigo().equals("totalAccesibilidadPoblacionAfectada")){
                            dp.setAccesibilidadPoblacionAfectada(di.getValor());
                            accesibilidadPoblacionAfectadaTotal+=di.getValor().isEmpty()?0:new Integer(di.getValor());
                        }else if(di.getCodigo().equals("totalNumPrediosConRiesgos")){
                            dp.setNumPrediosConRiesgos(di.getValor());
                            numPrediosConRiesgosTotal+=di.getValor().isEmpty()?0:new Integer(di.getValor());
                        }else if(di.getCodigo().equals("totalPoblacionAfectadaRiesgos")){
                            dp.setPoblacionAfectadaRiesgos(di.getValor());
                            poblacionAfectadaRiesgosTotal+=di.getValor().isEmpty()?0:new Integer(di.getValor());
                        }else if(di.getCodigo().equals("totalPoblacionTotal")){
                            dp.setPoblacionTotal(di.getValor());
                        }else if(di.getCodigo().equals("totalPrediosProblemasLegalizacion")){
                            dp.setPrediosProblemasLegalizacion(di.getValor());
                            prediosProblemasLegalizacionTotal+=di.getValor().isEmpty()?0:new Integer(di.getValor());
                        }else if(di.getCodigo().equals("totalEstudiantesProblemaLegalizacion")){
                            dp.setEstudiantesProblemaLegalizacion(di.getValor());
                            estudiantesProblemaLegalizacionTotal+=di.getValor().isEmpty()?0:new Integer(di.getValor());
                        }else if(di.getCodigo().equals("totalNumPrediosConAgua")){
                            dp.setNumPrediosConAgua(di.getValor());
                            numPrediosConAguaTotal+=di.getValor().isEmpty()?0:new Integer(di.getValor());
                        }else if(di.getCodigo().equals("totalNumPrediosConEE")){
                            dp.setNumPrediosConEE(di.getValor());
                            numPrediosConEETotal+=di.getValor().isEmpty()?0:new Integer(di.getValor());
                        }else if(di.getCodigo().equals("totalNumPrediosCumplenConsumoAgua")){
                            dp.setNumPrediosCumplenConsumoAgua(di.getValor());
                            numPrediosCumplenConsumoAguaTotal+=di.getValor().isEmpty()?0:new Integer(di.getValor());
                        }else if(di.getCodigo().equals("totalNumPrediosCumplenConsumoEE")){
                            dp.setNumPrediosCumplenConsumoEE(di.getValor());
                            numPrediosCumplenConsumoEETotal+=di.getValor().isEmpty()?0:new Integer(di.getValor());
                        }else if(di.getCodigo().equals("totalM2Requeridos")){
                            dp.setM2Requeridos(di.getValor());
                            m2RequeridosTotal=m2RequeridosTotal.add(di.getValor().isEmpty()?new BigDecimal(0):new BigDecimal(di.getValor()));
                        }else if(di.getCodigo().equals("totalConstruiblePrimerPiso")){
                            dp.setConstruiblePrimerPiso(di.getValor());
                            construiblePrimerPisoTotal=construiblePrimerPisoTotal.add(di.getValor().isEmpty()?new BigDecimal(0):new BigDecimal(di.getValor()));
                        }else if(di.getCodigo().equals("totalNumPrediosVulnerables")){
                            dp.setNumPrediosVulnerables(di.getValor());
                            numPrediosVulnerablesTotal+=di.getValor().isEmpty()?0:new Integer(di.getValor());
                        }else if(di.getCodigo().equals("totalNumPrediosEstrucCritica")){
                            dp.setNumPrediosEstrucCritica(di.getValor());
                            numPrediosEstrucCriticaTotal+=di.getValor().isEmpty()?0:new Integer(di.getValor());
                        }else if(di.getCodigo().equals("totalNumPrediosRutaEvacCritica")){
                            dp.setNumPrediosRutaEvacCritica(di.getValor());
                            numPrediosRutaEvacCriticaTotal+=di.getValor().isEmpty()?0:new Integer(di.getValor());
                        }else if(di.getCodigo().equals("totalNumPrediosSenalizaEvacCritica")){
                            dp.setNumPrediosSenalizaEvacCritica(di.getValor());
                            numPrediosSenalizaEvacCriticaTotal+=di.getValor().isEmpty()?0:new Integer(di.getValor());
                        }else if(di.getCodigo().equals("totalNumPrediosNoRactivoIncendio")){
                            dp.setNumPrediosNoRactivoIncendio(di.getValor());
                            numPrediosNoRactivoIncendioTotal+=di.getValor().isEmpty()?0:new Integer(di.getValor());
                        }else if(di.getCodigo().equals("totalAreaPisosCond4")){
                            dp.setAreaPisosCond4(di.getValor());
                        }else if(di.getCodigo().equals("totalAreaPisosCond3")){
                            dp.setAreaPisosCond3(di.getValor());
                        }else if(di.getCodigo().equals("totalAreaPisosCond2")){
                            dp.setAreaPisosCond2(di.getValor());
                        }else if(di.getCodigo().equals("totalAreaPisosCond1")){
                            dp.setAreaPisosCond1(di.getValor());
                        }else if(di.getCodigo().equals("totalAreaMurosCond4")){
                            dp.setAreaMurosCond4(di.getValor());
                        }else if(di.getCodigo().equals("totalAreaMurosCond3")){
                            dp.setAreaMurosCond3(di.getValor());
                        }else if(di.getCodigo().equals("totalAreaMurosCond2")){
                            dp.setAreaMurosCond2(di.getValor());
                        }else if(di.getCodigo().equals("totalAreaMurosCond1")){
                            dp.setAreaMurosCond1(di.getValor());
                        }else if(di.getCodigo().equals("totalAreaAcabadosMuroCond4")){
                            dp.setAreaAcabadosMuroCond4(di.getValor());
                        }else if(di.getCodigo().equals("totalAreaAcabadosMuroCond3")){
                            dp.setAreaAcabadosMuroCond3(di.getValor());
                        }else if(di.getCodigo().equals("totalAreaAcabadosMuroCond2")){
                            dp.setAreaAcabadosMuroCond2(di.getValor());
                        }else if(di.getCodigo().equals("totalAreaAcabadosMuroCond1")){
                            dp.setAreaAcabadosMuroCond1(di.getValor());
                        }else if(di.getCodigo().equals("totalAreaCieloRasosCond4")){
                            dp.setAreaCieloRasosCond4(di.getValor());
                        }else if(di.getCodigo().equals("totalAreaCieloRasosCond3")){
                            dp.setAreaCieloRasosCond3(di.getValor());
                        }else if(di.getCodigo().equals("totalAreaCieloRasosCond2")){
                            dp.setAreaCieloRasosCond2(di.getValor());
                        }else if(di.getCodigo().equals("totalAreaCieloRasosCond1")){
                            dp.setAreaCieloRasosCond1(di.getValor());
                        }else if(di.getCodigo().equals("totalM2PrediosEstadoEstrucCritico")){
                            dp.setM2PrediosEstadoEstrucCritico(di.getValor());
                            m2PrediosEstadoEstrucCriticoTotal=m2PrediosEstadoEstrucCriticoTotal.add(new BigDecimal(di.getValor()));
                        }else if(di.getCodigo().equals("totalIluminacionValor1")){
                            dp.setIluminacionValor1(di.getValor());
                        }else if(di.getCodigo().equals("totalIluminacionValor2")){
                            dp.setIluminacionValor2(di.getValor());
                        }else if(di.getCodigo().equals("totalIluminacionValor3")){
                            dp.setIluminacionValor3(di.getValor());
                        }else if(di.getCodigo().equals("totalIluminacionValor4")){
                            dp.setIluminacionValor4(di.getValor());
                        }else if(di.getCodigo().equals("totalVentilacionValor1")){
                            dp.setVentilacionValor1(di.getValor());
                        }else if(di.getCodigo().equals("totalVentilacionValor2")){
                            dp.setVentilacionValor2(di.getValor());
                        }else if(di.getCodigo().equals("totalVentilacionValor3")){
                            dp.setVentilacionValor3(di.getValor());
                        }else if(di.getCodigo().equals("totalVentilacionValor4")){
                            dp.setVentilacionValor4(di.getValor());
                        }else if(di.getCodigo().equals("totalAcusticaValor1")){
                            dp.setAcusticaValor1(di.getValor());
                        }else if(di.getCodigo().equals("totalAcusticaValor2")){
                            dp.setAcusticaValor2(di.getValor());
                        }else if(di.getCodigo().equals("totalAcusticaValor3")){
                            dp.setAcusticaValor3(di.getValor());
                        }else if(di.getCodigo().equals("totalAcusticaValor4")){
                            dp.setAcusticaValor4(di.getValor());
                        }else if(di.getCodigo().equals("totalDuchasValor1")){
                            dp.setDuchasValor1(di.getValor());
                        }else if(di.getCodigo().equals("totalDuchasValor2")){
                            dp.setDuchasValor2(di.getValor());
                        }else if(di.getCodigo().equals("totalDuchasValor3")){
                            dp.setDuchasValor3(di.getValor());
                        }else if(di.getCodigo().equals("totalDuchasValor4")){
                            dp.setDuchasValor4(di.getValor());
                        }else if(di.getCodigo().equals("totalLavamanosValor1")){
                            dp.setLavamanosValor1(di.getValor());
                        }else if(di.getCodigo().equals("totalLavamanosValor2")){
                            dp.setLavamanosValor2(di.getValor());
                        }else if(di.getCodigo().equals("totalLavamanosValor3")){
                            dp.setLavamanosValor3(di.getValor());
                        }else if(di.getCodigo().equals("totalLavamanosValor4")){
                            dp.setLavamanosValor4(di.getValor());
                        }else if(di.getCodigo().equals("totalOrinalesValor1")){
                            dp.setOrinalesValor1(di.getValor());
                        }else if(di.getCodigo().equals("totalOrinalesValor2")){
                            dp.setOrinalesValor2(di.getValor());
                        }else if(di.getCodigo().equals("totalOrinalesValor3")){
                            dp.setOrinalesValor3(di.getValor());
                        }else if(di.getCodigo().equals("totalOrinalesValor4")){
                            dp.setOrinalesValor4(di.getValor());
                        }else if(di.getCodigo().equals("totalInodorosValor1")){
                            dp.setInodorosValor1(di.getValor());
                        }else if(di.getCodigo().equals("totalInodorosValor2")){
                            dp.setInodorosValor2(di.getValor());
                        }else if(di.getCodigo().equals("totalInodorosValor3")){
                            dp.setInodorosValor3(di.getValor());
                        }else if(di.getCodigo().equals("totalInodorosValor4")){
                            dp.setInodorosValor4(di.getValor());
                        }else if(di.getCodigo().equals("totalNumIluminacion")){
                            dp.setNumIluminacion(di.getValor());
                        }else if(di.getCodigo().equals("totalNumVentilacion")){
                            dp.setNumVentilacion(di.getValor());
                        }else if(di.getCodigo().equals("totalNumAcustica")){
                            dp.setNumAcustica(di.getValor());
                        }else if(di.getCodigo().equals("totalNumDuchas")){
                            dp.setNumDuchas(di.getValor());
                        }else if(di.getCodigo().equals("totalNumLavamanos")){
                            dp.setNumLavamanos(di.getValor());
                        }else if(di.getCodigo().equals("totalNumOrinales")){
                            dp.setNumOrinales(di.getValor());
                        }else if(di.getCodigo().equals("totalNumInodoros")){
                            dp.setNumInodoros(di.getValor());
                        }else if(di.getCodigo().equals("totalPromedioConsumoAgua")){
                            dp.setPromedioConsumoAgua(di.getValor());
                            promedioConsumoAguaTotal=promedioConsumoAguaTotal.add(di.getValor().isEmpty()?new BigDecimal(0):new BigDecimal(di.getValor()));
                        }else if(di.getCodigo().equals("totalPromedioConsumoEE")){
                            dp.setPromedioConsumoEE(di.getValor());
                            promedioConsumoEETotal=promedioConsumoEETotal.add(di.getValor().isEmpty()?new BigDecimal(0):new BigDecimal(di.getValor()));
                        }else if(di.getCodigo().equals("totalMurosFachadasValor1")){
                            dp.setMurosFachadasValor1(di.getValor());
                        }else if(di.getCodigo().equals("totalMurosFachadasValor2")){
                            dp.setMurosFachadasValor2(di.getValor());
                        }else if(di.getCodigo().equals("totalMurosFachadasValor3")){
                            dp.setMurosFachadasValor3(di.getValor());
                        }else if(di.getCodigo().equals("totalMurosFachadasValor4")){
                            dp.setMurosFachadasValor4(di.getValor());
                        }else if(di.getCodigo().equals("totalAcabadosFachadasValor1")){
                            dp.setAcabadosFachadasValor1(di.getValor());
                        }else if(di.getCodigo().equals("totalAcabadosFachadasValor2")){
                            dp.setAcabadosFachadasValor2(di.getValor());
                        }else if(di.getCodigo().equals("totalAcabadosFachadasValor3")){
                            dp.setAcabadosFachadasValor3(di.getValor());
                        }else if(di.getCodigo().equals("totalAcabadosFachadasValor4")){
                            dp.setAcabadosFachadasValor4(di.getValor());
                        }else if(di.getCodigo().equals("totalCubiertasValor1")){
                            dp.setCubiertasValor1(di.getValor());
                        }else if(di.getCodigo().equals("totalCubiertasValor2")){
                            dp.setCubiertasValor2(di.getValor());
                        }else if(di.getCodigo().equals("totalCubiertasValor3")){
                            dp.setCubiertasValor3(di.getValor());
                        }else if(di.getCodigo().equals("totalCubiertasValor4")){
                            dp.setCubiertasValor4(di.getValor());
                        }else if(di.getCodigo().equals("totalUtilizacion")){
                            dp.setUtilizacion(di.getValor());
                        }else if(di.getCodigo().equals("totalCuposTotal")){
                            dp.setCuposTotal(di.getValor());
                            cuposTotalTotal=cuposTotalTotal.add(di.getValor().isEmpty()?new BigDecimal(0):new BigDecimal(di.getValor()));
                        }else if(di.getCodigo().equals("idoneidad")){
                            dp.setIdoneidad(di.getValor());
                        }else if(di.getCodigo().equals("m2AmpliarCobertura")){
                            dp.setM2AmpliarCobertura(di.getValor());
                            m2AmpliarCoberturaTotal=m2AmpliarCoberturaTotal.add(di.getValor().isEmpty()?new BigDecimal(0):new BigDecimal(di.getValor()));
                        }else if(di.getCodigo().equals("alumnosNuevosInfraestructura")){
                            dp.setAlumnosNuevosInfraestructura(di.getValor());
                            alumnosNuevosInfraestructuraTotal=alumnosNuevosInfraestructuraTotal.add(di.getValor().isEmpty()?new BigDecimal(0):new BigDecimal(di.getValor()));
                        }else if(di.getCodigo().equals("totalPre")){
                            dp.setPre(di.getValor());
                        }else if(di.getCodigo().equals("totalPrimaria")){
                            dp.setPrimaria(di.getValor());
                        }else if(di.getCodigo().equals("totalSecundaria")){
                            dp.setSecundaria(di.getValor());
                        }else if(di.getCodigo().equals("totalBasica")){
                            dp.setBasica(di.getValor());
                        }else if(di.getCodigo().equals("totalMedia")){
                            dp.setMedia(di.getValor());
                        }else if(di.getCodigo().equals("totalAislamientoTermicoPisos")){
                            dp.setAislamientoTermicoPisos(di.getValor());
                        }else if(di.getCodigo().equals("totalAislamientoTermicoMuros")){
                            dp.setAislamientoTermicoMuros(di.getValor());
                        }else if(di.getCodigo().equals("totalAislamientoTermicoVentanas")){
                            dp.setAislamientoTermicoVentanas(di.getValor());
                        }else if(di.getCodigo().equals("totalAislamientoTermicoCielos")){
                            dp.setAislamientoTermicoCielos(di.getValor());
                        }else if(di.getCodigo().equals("totalAislamientoTermicoOtros")){
                            dp.setAislamientoTermicoOtros(di.getValor());
                        }else if(di.getCodigo().equals("numPrediosNoCumplenAula")){
                            dp.setNumPrediosNoCumplenAula(di.getValor());
                        }else if(di.getCodigo().equals("numPrediosNoCumplenLabCiencias")){
                            dp.setNumPrediosNoCumplenLabCiencias(di.getValor());
                        }else if(di.getCodigo().equals("numPrediosNoCumplenLabMultimedia")){
                            dp.setNumPrediosNoCumplenLabMultimedia(di.getValor());
                        }else if(di.getCodigo().equals("numPrediosNoCumplenLabComputacion")){
                            dp.setNumPrediosNoCumplenLabComputacion(di.getValor());
                        }else if(di.getCodigo().equals("numPrediosNoCumplenBiblioteca")){
                            dp.setNumPrediosNoCumplenBiblioteca(di.getValor());
                        }else if(di.getCodigo().equals("numPrediosNoCumplenSalaMusica")){
                            dp.setNumPrediosNoCumplenSalaMusica(di.getValor());
                        }else if(di.getCodigo().equals("numPrediosNoCumplenSalaArte")){
                            dp.setNumPrediosNoCumplenSalaArte(di.getValor());
                        }else if(di.getCodigo().equals("numPrediosNoCumplenPlayonDepor")){
                            dp.setNumPrediosNoCumplenPlayonDepor(di.getValor());
                        }else if(di.getCodigo().equals("numPrediosNoCumplenExpRecre")){
                            dp.setNumPrediosNoCumplenExpRecre(di.getValor());
                        }else if(di.getCodigo().equals("numPrediosNoCumplenSUM")){
                            dp.setNumPrediosNoCumplenSUM(di.getValor());
                        }else if(di.getCodigo().equals("numPrediosNoCumplenPsicopeEnferm")){
                            dp.setNumPrediosNoCumplenPsicopeEnferm(di.getValor());
                        }else if(di.getCodigo().equals("numPrediosNoCumplenServSanitarios")){
                            dp.setNumPrediosNoCumplenServSanitarios(di.getValor());
                        }else if(di.getCodigo().equals("numPrediosNoCumplenCocinaCafe")){
                            dp.setNumPrediosNoCumplenCocinaCafe(di.getValor());
                        }else if(di.getCodigo().equals("numPrediosNoCumplenOfAdm")){
                            dp.setNumPrediosNoCumplenOfAdm(di.getValor());
                        }                    
                    }
                    
                    List<IndicadoresBID> resultados = fachadaBID.consultarIndicadoresBIDPais(periodoBID, pais);
                    for (IndicadoresBID indicadorBID : resultados) {
                        if(indicadorBID.getTipoIndicadorBID().getId().equals(TipoIndicadorBID.PROPORCION_PREDIOS_AGUA_CONDICION_INSUFICIENTE.getId())){
                            dp.setPropPrediosAguaInsuficiente(new BigDecimal(indicadorBID.getValor()).setScale(0,RoundingMode.HALF_UP));
                            propPrediosAguaInsuficienteTotal=propPrediosAguaInsuficienteTotal.add(new BigDecimal(indicadorBID.getValor()).setScale(0,RoundingMode.HALF_UP));
                        }else if(indicadorBID.getTipoIndicadorBID().getId().equals(TipoIndicadorBID.PROPORCION_PREDIOS_ENERGIA_ELECTRICA_CONDICION_INSUFICIENTE.getId())){
                            dp.setPropPrediosEEInsuficiente(new BigDecimal(indicadorBID.getValor()).setScale(0,RoundingMode.HALF_UP));
                            propPrediosEEInsuficienteTotal=propPrediosEEInsuficienteTotal.add(new BigDecimal(indicadorBID.getValor()).setScale(0,RoundingMode.HALF_UP));
                        }else if(indicadorBID.getTipoIndicadorBID().getId().equals(TipoIndicadorBID.PROPORCION_PREDIOS_CONDICION_MATERIALIDAD_CRITICA.getId())){
                            dp.setPropPrediosMaterialidadCritica(new BigDecimal(indicadorBID.getValor()).setScale(0,RoundingMode.HALF_UP));
                        }
                    }
                    
                    listaDatosPaises.add(dp);
                }

                


                parametros.put("numEstablecimientosTotal", numEstablecimientosTotal);
                parametros.put("numSedesTotal", numSedesTotal);
                parametros.put("numPrediosTotal", numPrediosTotal);
                parametros.put("superficieTotal", superficieTotal.equals(BigDecimal.ZERO)?BigDecimal.ZERO:superficieTotal.setScale(2, RoundingMode.HALF_UP));
                parametros.put("areaConstrucPrimerPisoTotal", areaConstrucPrimerPisoTotal.setScale(2, RoundingMode.HALF_UP));
                parametros.put("areaConstruccionTotal", areaConstruccionTotal.setScale(2, RoundingMode.HALF_UP));
                parametros.put("accesibilidadPoblacionAfectadaTotal",accesibilidadPoblacionAfectadaTotal);
                parametros.put("numPrediosAccesibilidadInadecuadaTotal",numPrediosAccesibilidadInadecuadaTotal);
                parametros.put("numPrediosConRiesgosTotal",numPrediosConRiesgosTotal);
                parametros.put("poblacionAfectadaRiesgosTotal",poblacionAfectadaRiesgosTotal);
                parametros.put("estudiantesProblemaLegalizacionTotal",estudiantesProblemaLegalizacionTotal);
                parametros.put("numPrediosConAguaTotal",numPrediosConAguaTotal);
                parametros.put("prediosProblemasLegalizacionTotal",prediosProblemasLegalizacionTotal);
                parametros.put("numPrediosConEETotal",numPrediosConEETotal);
                parametros.put("promedioConsumoAguaTotal",promedioConsumoAguaTotal);
                parametros.put("numPrediosCumplenConsumoAguaTotal",numPrediosCumplenConsumoAguaTotal);
                parametros.put("promedioConsumoEETotal",promedioConsumoEETotal);
                parametros.put("numPrediosCumplenConsumoEETotal",numPrediosCumplenConsumoEETotal);
                parametros.put("numPrediosVulnerablesTotal",numPrediosVulnerablesTotal);
                parametros.put("numPrediosEstrucCriticaTotal",numPrediosEstrucCriticaTotal);
                parametros.put("m2PrediosEstadoEstrucCriticoTotal",m2PrediosEstadoEstrucCriticoTotal);
                parametros.put("numPrediosRutaEvacCriticaTotal",numPrediosRutaEvacCriticaTotal);
                parametros.put("numPrediosSenalizaEvacCriticaTotal",numPrediosSenalizaEvacCriticaTotal);
                parametros.put("numPrediosNoRactivoIncendioTotal",numPrediosNoRactivoIncendioTotal);
                parametros.put("m2RequeridosTotal",m2RequeridosTotal);
                parametros.put("construiblePrimerPisoTotal",construiblePrimerPisoTotal);
                parametros.put("construibleAlturaTotal",construibleAlturaTotal);
                parametros.put("construibleTotalTotal",construibleTotalTotal);
                parametros.put("m2AmpliarCoberturaTotal",m2AmpliarCoberturaTotal);
                parametros.put("alumnosNuevosInfraestructuraTotal",alumnosNuevosInfraestructuraTotal);
                parametros.put("cuposTotalTotal",cuposTotalTotal);
                parametros.put("propPrediosAguaInsuficienteTotal",propPrediosAguaInsuficienteTotal);
                parametros.put("propPrediosEEInsuficienteTotal",propPrediosEEInsuficienteTotal);
                
                ReporteInformeGeneralDetallado reporteInformeGeneralDetallado= new ReporteInformeGeneralDetallado();
                reporteInformeGeneralDetallado.setListaDatosPaises(listaDatosPaises);

                List list=new ArrayList();
                list.add(reporteInformeGeneralDetallado);


                parametros.put(JRParameter.REPORT_RESOURCE_BUNDLE, ResourceBundle.getBundle("etiquetas", FacesContext.getCurrentInstance().getViewRoot().getLocale()));

                UtilExportar.exportarInformeGeneral("BID/informeGeneralDetallado", "InformeGeneralDetallado", "InformeGeneralDetallado", parametros, list);
            }
        }catch(Exception e){
            UtilOut.println(bundle.getString("aplicacion.general.errorImprimirPDFDetallado")+": "+e.getMessage());
            mostrarMensajeError(errorAplicacion, e.getMessage());
            UtilOut.println(e);
        }
    }
    
    public void postProcessXLS(Object document) {
        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow header = sheet.getRow(0);
        for (int i = 0; i < colums.size(); i++) {
            header.getCell(i).setCellValue(colums.get(i));
        }
        new GestionArchivos().postProcessXLS(document);
    }
    
    public class Registro {

        private String cod;
        private Long id;
        private HashMap<String,String> ind_nombre;

        public String getCod() {
            return cod;
        }

        public void setCod(String cod) {
            this.cod = cod;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public HashMap<String, String> getInd_nombre() {
            return ind_nombre;
        }

        public void setInd_nombre(HashMap<String, String> ind_nombre) {
            this.ind_nombre = ind_nombre;
        }
                
    }

    public List<Registro> getLista() {
        return lista;
    }

    public void setLista(List<Registro> lista) {
        this.lista = lista;
    }

    public List<String> getColums() {
        return colums;
    }

    public void setColums(List<String> colums) {
        this.colums = colums;
    }

    public List<String> getColumsTotal() {
        return columsTotal;
    }

    public void setColumsTotal(List<String> columsTotal) {
        this.columsTotal = columsTotal;
    }

    public String getMensajeTablaVacia() {
        return mensajeTablaVacia;
    }

    public void setMensajeTablaVacia(String mensajeTablaVacia) {
        this.mensajeTablaVacia = mensajeTablaVacia;
    }

    public PeriodosBID getPeriodoBID() {
        return periodoBID;
    }

    public void setPeriodoBID(PeriodosBID periodoBID) {
        this.periodoBID = periodoBID;
    }

    public List<PeriodosBID> getListaPeriodos() {
        return listaPeriodos;
    }

    public void setListaPeriodos(List<PeriodosBID> listaPeriodos) {
        this.listaPeriodos = listaPeriodos;
    }

}
