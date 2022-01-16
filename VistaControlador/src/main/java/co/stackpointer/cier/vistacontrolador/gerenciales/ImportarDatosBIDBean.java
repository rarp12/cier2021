/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.gerenciales;

import co.stackpointer.cier.modelo.entidad.BID.DatosInformeBID;
import co.stackpointer.cier.modelo.entidad.BID.IndicadoresBID;
import co.stackpointer.cier.modelo.entidad.BID.PaisesBID;
import co.stackpointer.cier.modelo.entidad.BID.PeriodosBID;
import co.stackpointer.cier.modelo.entidad.BID.TiposIndicadoresBID;
import co.stackpointer.cier.modelo.entidad.BID.ValoresBID;
import co.stackpointer.cier.modelo.entidad.BID.VariablesBID;
import co.stackpointer.cier.modelo.fachada.BIDFacadeLocal;
import co.stackpointer.cier.modelo.utilidad.UtilCadena;
import co.stackpointer.cier.modelo.utilidad.UtilOut;
import co.stackpointer.cier.vista.Utilidades;
import static co.stackpointer.cier.vistacontrolador.bean.importacion.ImpUtil.getValorCelda;
import co.stackpointer.cier.vistacontrolador.bean.importacion.Log;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.omnifaces.util.Messages;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author nruiz
 */
@ManagedBean(name = "importarDatosBID")
@ViewScoped
public class ImportarDatosBIDBean {
    
    
    @EJB
    private BIDFacadeLocal fachadaBID;
    
    public PeriodosBID periodoBID;
    public List<PeriodosBID> listaPeriodos;
    
    private PaisesBID pais;
    private List<Log> logs;
    private boolean hayRegErroneos;
    
    public List<ValoresBID> listaCalificaciones;
    public List<IndicadoresBID> listaIndicadores;
    public List<DatosInformeBID> listaDatosInforme;
    
    public String mensaje;
    
    private ResourceBundle bundleMensajes = ResourceBundle.getBundle("etiquetas", FacesContext.getCurrentInstance().getViewRoot().getLocale());

    
    @PostConstruct
    public void inicializar() {
        logs=null;
        hayRegErroneos=false;
        periodoBID=null;
        pais=null;
        listaPeriodos=fachadaBID.getPeriodosBID();
        mensaje=null;
    }

    public void cargar(FileUploadEvent event) throws IOException {
        listaCalificaciones=new ArrayList<ValoresBID>();
        listaIndicadores=new ArrayList<IndicadoresBID>();
        listaDatosInforme=new ArrayList<DatosInformeBID>();
        hayRegErroneos=false;
        Row row1;
        Row row2;
        UploadedFile file = event.getFile();
        logs = new ArrayList<Log>();
        try {
            Workbook wb = new XSSFWorkbook(file.getInputstream());
            Sheet hoja = wb.getSheetAt(0);
            row1 = hoja.getRow(0);
            row2 = hoja.getRow(1);
            if (row1==null || row2==null) {
                hayRegErroneos=true;
                Messages.addFlashGlobalError(bundleMensajes.getString("adm.parametros.config.carga.archivoIncorrecto"));
            }else{
                
                if(periodoBID==null){
                    hayRegErroneos=true;
                    Messages.addFlashGlobalError(bundleMensajes.getString("adm.parametros.config.carga.errorSeleccionarPeriodo"));
                }
                if (row2.getCell(0) == null
                        || UtilCadena.isNullOVacio(getValorCelda(row2.getCell(0)))) {
                    hayRegErroneos=true;
                    Messages.addFlashGlobalError(bundleMensajes.getString("adm.parametros.config.carga.idPaisErroneo"));
                }else{
                    pais=fachadaBID.obtenerPaisBIDPorId(new Integer(getValorCelda(row2.getCell(0))));
                    if(pais==null){
                        hayRegErroneos=true;
                        Messages.addFlashGlobalError(bundleMensajes.getString("adm.parametros.config.carga.idPaisErroneo"));
                    }
                }
                int periodoInfo=0;
                try{
                    periodoInfo=new Integer(getValorCelda(row2.getCell(1))).intValue();
                }catch(Exception e){
                    hayRegErroneos=true;
                    Messages.addFlashGlobalError(bundleMensajes.getString("adm.parametros.config.carga.periodoArchivoErroneo"));
                }
                if(!hayRegErroneos){
                     //Se valida si el país tiene valores para ese periodo
                    if(fachadaBID.existenValoresPaisPeriodo(pais,periodoBID)
                            || fachadaBID.existenIndicadoresPaisPeriodo(pais,periodoBID)
                            || fachadaBID.existenDatosInformePaisPeriodo(pais,periodoBID)){
                        mensaje=Utilidades.obtenerMensaje("adm.parametros.config.carga.paisTieneValoresPeriodo",pais.getNombre(),periodoBID.getCodigo());
                    }

                    int i=2;
                    
                    while((row1.getCell(i)!=null && !UtilCadena.isNullOVacio(getValorCelda(row1.getCell(i))))
                            || (row2.getCell(i)!=null && !UtilCadena.isNullOVacio(getValorCelda(row2.getCell(i))))) {
                        
                        try {
                            //Para saber si es una calificación consultamos el código
                            String codigo=getValorCelda(row1.getCell(i));
                            String valor=getValorCelda(row2.getCell(i));
                            if("null".equals(valor)) valor="";
                            //expresión regular para saber si es numerico
                            VariablesBID var=UtilCadena.esNumerico(codigo)?fachadaBID.obtenerVariablePorId(new Integer(codigo)):null;
                            if(var!=null){//es calificacion
                                ValoresBID v=new ValoresBID();
                                v.setVariableBID(var);
                                v.setValor(valor);
                                v.setPaisBID(pais);
                                v.setPeriodoBID(periodoBID);
                                v.setPeriodoInfo(periodoInfo);
                                listaCalificaciones.add(v);
                                
                            }else{
                                TiposIndicadoresBID tip=fachadaBID.obtenerTipoIndicadorPorCodigo(codigo);
                                //Para saber si es un indicador consultamos el código
                                if(tip!=null){//es indicador
                                    IndicadoresBID ind=new IndicadoresBID();
                                    ind.setTipoIndicadorBID(tip);
                                    ind.setValor(valor);
                                    ind.setPaisBID(pais);
                                    ind.setPeriodoBID(periodoBID);
                                    ind.setPeriodoInfo(periodoInfo);
                                    listaIndicadores.add(ind);
                                    
                                }else if(!"null".equals(codigo)){
                                    //Es un dato del informe
                                    DatosInformeBID di=new DatosInformeBID();
                                    di.setCodigo(codigo);
                                    di.setValor(valor);
                                    di.setPaisBID(pais);
                                    di.setPeriodoBID(periodoBID);
                                    di.setPeriodoInfo(periodoInfo);
                                    listaDatosInforme.add(di);
                                }
                            }

                        } catch (Exception e) {
                            hayRegErroneos=true;
                            String log = " Columna[" + (i + 1) + "]: Error '" + e.getMessage() + "'";
                            logs.add(new Log(Log.ERROR, log));
                        }finally{
                            i++;
                        }
                    }//WHILE
                    
                    if (hayRegErroneos) {
                        Messages.addFlashGlobalError(bundleMensajes.getString("adm.parametros.config.carga.datosNoCorrectos"));
                    }
                }
                
                if(UtilCadena.isNullOVacio(mensaje)){
                    mensaje=Utilidades.obtenerMensaje("adm.parametros.config.carga.datosCorrectosBID",pais.getNombre(),periodoBID.getCodigo());
                }
            }
            
            
        } catch (Exception e) {
            hayRegErroneos = true;
            UtilOut.println(e);            
            Messages.addFlashGlobalError(bundleMensajes.getString("adm.parametros.config.carga.errorRegistro"));
        }
    }
    
    public void guardar() throws IOException {
        try{
            if(Utilidades.obtenerMensaje("adm.parametros.config.carga.paisTieneValoresPeriodo",pais.getNombre(),periodoBID.getCodigo()).equals(mensaje)){
                //HAY QUE ELIMINAR LOS VALORES ANTIGUOS
                fachadaBID.eliminaraValoresPaisPeriodo(pais,periodoBID);
                fachadaBID.eliminaraIndicadoresPaisPeriodo(pais,periodoBID);
                fachadaBID.eliminaraDatosInformePaisPeriodo(pais,periodoBID);
            }
            
            for(ValoresBID var:listaCalificaciones){
                fachadaBID.guardarValorBID(var);
            }
            
            for(IndicadoresBID ind:listaIndicadores){
                fachadaBID.guardarIndicadorBID(ind);
            }
            
            for(DatosInformeBID di:listaDatosInforme){
                fachadaBID.guardarDatosInformeBID(di);
            }

            
            
            Messages.addFlashGlobalInfo(bundleMensajes.getString("adm.parametros.config.carga.datosGuardados"));
            inicializar() ;           
        }catch (Exception e) {
            UtilOut.println(e);
            Messages.addFlashGlobalError(bundleMensajes.getString("adm.parametros.config.carga.errorRegistro"));
        }
    }

    public List<Log> getLogs() {
        return logs;
    }

    public void setLogs(List<Log> logs) {
        this.logs = logs;
    }

    public boolean isHayRegErroneos() {
        return hayRegErroneos;
    }

    public void setHayRegErroneos(boolean hayRegErroneos) {
        this.hayRegErroneos = hayRegErroneos;
    }    

    public PeriodosBID getPeriodoBID() {
        return periodoBID;
    }

    public void setPeriodoBID(PeriodosBID periodoBID) {
        this.periodoBID = periodoBID;
    }

    public void cambioPeriodoBID(AjaxBehaviorEvent event) {
        //Solo para que cargue el periodo
    }

    public List<PeriodosBID> getListaPeriodos() {
        return listaPeriodos;
    }

    public void setListaPeriodos(List<PeriodosBID> listaPeriodos) {
        this.listaPeriodos = listaPeriodos;
    }

    public List<ValoresBID> getListaCalificaciones() {
        return listaCalificaciones;
    }

    public void setListaCalificaciones(List<ValoresBID> listaCalificaciones) {
        this.listaCalificaciones = listaCalificaciones;
    }

    public List<IndicadoresBID> getListaIndicadores() {
        return listaIndicadores;
    }

    public void setListaIndicadores(List<IndicadoresBID> listaIndicadores) {
        this.listaIndicadores = listaIndicadores;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
}
