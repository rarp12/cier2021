/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.administracion.configuracion;

import co.stackpointer.cier.vistacontrolador.bean.importacion.Log;
import co.stackpointer.cier.modelo.entidad.instrumento.Encuestador;
import co.stackpointer.cier.modelo.entidad.instrumento.Supervisor;
import co.stackpointer.cier.modelo.fachada.EncuestadorSupervisorFacadeLocal;
import co.stackpointer.cier.modelo.fachada.InstrumentoFacadeLocal;
import co.stackpointer.cier.modelo.tipo.Estado;
import co.stackpointer.cier.modelo.utilidad.UtilCadena;
import co.stackpointer.cier.modelo.utilidad.UtilFecha;
import co.stackpointer.cier.modelo.utilidad.UtilOut;
import co.stackpointer.cier.vista.ManejadorErrores;
import co.stackpointer.cier.vista.Utilidades;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.ejb.EJB;
import javax.faces.bean.ViewScoped;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.omnifaces.util.Messages;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import static co.stackpointer.cier.vistacontrolador.bean.importacion.ImpUtil.getValorCelda;
import co.stackpointer.cier.vistacontrolador.validator.EmailValidator;
import co.stackpointer.cier.vistacontrolador.validator.PhoneValidator;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.PostConstruct;

/**
 *
 * @author StackPointer
 */
@ManagedBean(name = "adminSupervisoresEncuestadores")
@ViewScoped
public class AdminSupervisoresEncuestadoresBean implements Serializable {

    //@EJB
    //EstablecimientoFacadeLocal fachadaEst;
    //@EJB
    //DPAFacadeLocal fachadaDPA;
    @EJB
    InstrumentoFacadeLocal fachadaIns;
    @EJB
    EncuestadorSupervisorFacadeLocal fachadaEncSup;
    
    private List<Log> logs;
    private boolean hayRegErroneos;
    
    public List<Object> listaEliminar;
    public List<Object> listaCrear;
    public List<Object> listaActualizar;
    private List<Encuestador> encuestadores;
    private List<Supervisor> supervisores;
    private List<String> tiposIdentificacion;
    private boolean swEstadoEncuestador, swEstadoSupervisor;
    private Encuestador encuestadorNuevo, encuestadorSeleccionado;   
    private Supervisor supervisorNuevo, supervisorSeleccionado;
    
    
    @PostConstruct
    public void inicializar() {
        logs=null;
        hayRegErroneos=false;
        encuestadores = fachadaEncSup.getEncuestadores();
        supervisores = fachadaEncSup.getSupervisores();
        tiposIdentificacion = fachadaEncSup.getTiposIdentificacion();
        instanciarNuevoEncuestador();
    }
    
    public void instanciarNuevoEncuestador() {
        encuestadorNuevo = new Encuestador();
    }
    
    public void instanciarNuevoSupervisor() {
        supervisorNuevo = new Supervisor();
    }
    
    public void guardarEncuestador() {
        try {            
            setEncuestadorNuevo(fachadaEncSup.guardarEncuestador(encuestadorNuevo));
            encuestadores.add(encuestadorNuevo);
        } catch (Exception e) {
            //e.printStackTrace();
            Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"));
        }
    }
    
    public void guardarSupervisor() {
        try {            
            setSupervisorNuevo(fachadaEncSup.guardarSupervisor(supervisorNuevo));
            supervisores.add(supervisorNuevo);
        } catch (Exception e) {
            Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"));
        }
    }
    
    public void editarEncuestador() {
        try {
            encuestadorSeleccionado.setEstado(swEstadoEncuestador ? Estado.A : Estado.I);
            fachadaEncSup.actualizarEncuestador(encuestadorSeleccionado);
        } catch (Exception e) {
            Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion") + " " + e.getMessage());
        }
    }
    
    public void editarSupervisor() {
        try {
            supervisorSeleccionado.setEstado(swEstadoSupervisor ? Estado.A : Estado.I);
            fachadaEncSup.actualizarSupervisor(supervisorSeleccionado);
        } catch (Exception e) {
            Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion") + " " + e.getMessage());
        }
    }
    
    public void cargar(FileUploadEvent event) throws IOException {
        listaEliminar=new ArrayList<Object>();
        listaCrear=new ArrayList<Object>();
        listaActualizar=new ArrayList<Object>();
        List<String> codigosEncuestadores=new ArrayList<String>();
        List<String> codigosSupervisores=new ArrayList<String>();
        hayRegErroneos=false;
        Row rowDato;
        int rowNumber;
        UploadedFile file = event.getFile();
        logs = new ArrayList<Log>();
        try {
            Workbook wb = new XSSFWorkbook(file.getInputstream());
            Sheet hoja = wb.getSheetAt(0);
            rowNumber = 0;
            String s_e, tipoDoc, numDoc, nombre, telefono, email, direccion, celular, salud, personaContacto, telefonoContacto, fecIniVigenciaString, fecFinVigenciaString, activo, accion;
            Date fecIniVigencia=null, fecFinVigencia=null;
            while (true) {
                rowNumber++;
                rowDato = hoja.getRow(rowNumber);
                if (rowDato == null) {
                    break; //hasta que encuentre una fila vacia
                }
                s_e=getValorCelda(rowDato.getCell(0));
                tipoDoc=getValorCelda(rowDato.getCell(1));
                numDoc=getValorCelda(rowDato.getCell(2));
                nombre=getValorCelda(rowDato.getCell(3));
                telefono=getValorCelda(rowDato.getCell(4));
                email=getValorCelda(rowDato.getCell(5));
                direccion=getValorCelda(rowDato.getCell(6));
                celular=getValorCelda(rowDato.getCell(7));
                salud=getValorCelda(rowDato.getCell(8));
                personaContacto=getValorCelda(rowDato.getCell(9));
                telefonoContacto=getValorCelda(rowDato.getCell(10));
                fecIniVigenciaString=getValorCelda(rowDato.getCell(11));
                fecFinVigenciaString=getValorCelda(rowDato.getCell(12));
                activo=getValorCelda(rowDato.getCell(13));
                accion=getValorCelda(rowDato.getCell(14));
                if(UtilCadena.isNullOVacio(s_e) && UtilCadena.isNullOVacio(tipoDoc) && UtilCadena.isNullOVacio(numDoc) && UtilCadena.isNullOVacio(nombre) && UtilCadena.isNullOVacio(telefono) && 
                        UtilCadena.isNullOVacio(email) && UtilCadena.isNullOVacio(direccion) && UtilCadena.isNullOVacio(celular) && UtilCadena.isNullOVacio(salud) && UtilCadena.isNullOVacio(personaContacto) && 
                        UtilCadena.isNullOVacio(telefonoContacto) && UtilCadena.isNullOVacio(fecIniVigenciaString) && UtilCadena.isNullOVacio(fecFinVigenciaString) && UtilCadena.isNullOVacio(activo) && UtilCadena.isNullOVacio(accion)
                        ){
                    break; //hasta que encuentre una fila vacia
                }
   
                //Validaciones de formatos digitados
                if (EmailValidator.formatoErrado(email)) {
                    hayRegErroneos = true;
                    String log = Utilidades.obtenerMensaje("administracion.parametros.fila") + " [" + (rowNumber + 1) + "]: " + Utilidades.obtenerMensaje("administracion.usuario.error.validacion.correo");
                    logs.add(new Log(Log.ERROR, log));
                    continue;
                }
                if (PhoneValidator.formatoErrado(telefono)
                        || PhoneValidator.formatoErrado(celular)
                        || PhoneValidator.formatoErrado(telefonoContacto)) {
                    hayRegErroneos = true;
                    String log = Utilidades.obtenerMensaje("administracion.parametros.fila") + " [" + (rowNumber + 1) + "]: " + Utilidades.obtenerMensaje("aplicacion.general.error.validacion.telefono");
                    logs.add(new Log(Log.ERROR, log));
                    continue;
                }
                
                //Validaciones de datos
                if (UtilCadena.isNullOVacio(s_e)
                        || UtilCadena.isNullOVacio(tipoDoc)
                        || UtilCadena.isNullOVacio(numDoc)
                        || UtilCadena.isNullOVacio(nombre)
                        || UtilCadena.isNullOVacio(email)
                        || UtilCadena.isNullOVacio(direccion)
                        || UtilCadena.isNullOVacio(telefono)
                        || UtilCadena.isNullOVacio(celular)
                        || UtilCadena.isNullOVacio(personaContacto)
                        || UtilCadena.isNullOVacio(telefonoContacto)
                        || UtilCadena.isNullOVacio(activo)                        
                        || UtilCadena.isNullOVacio(accion)) {
                    hayRegErroneos=true;
                    String log = Utilidades.obtenerMensaje("administracion.parametros.fila") +" [" + (rowNumber + 1) + "]: "+Utilidades.obtenerMensaje("administracion.parametros.infoIncompleta");
                    logs.add(new Log(Log.ERROR, log));
                    continue;
                }else if(!s_e.equals("S") && !s_e.equals("E")){
                    hayRegErroneos=true;
                    String log = Utilidades.obtenerMensaje("administracion.parametros.fila") +" [" + (rowNumber + 1) + "]: "+Utilidades.obtenerMensaje("administracion.configuracion.primeraColumnaIncorrecta")+".";
                    logs.add(new Log(Log.ERROR, log));
                    continue;
                }else if(!tipoDoc.equals("CC") && !tipoDoc.equals("CE")
                        && !tipoDoc.equals("NIT") && !tipoDoc.equals("NO")
                        && !tipoDoc.equals("TI")){
                    hayRegErroneos=true;
                    String log = Utilidades.obtenerMensaje("administracion.parametros.fila") +" [" + (rowNumber + 1) + "]: "+Utilidades.obtenerMensaje("administracion.configuracion.errorTipoDoc")+".";
                    logs.add(new Log(Log.ERROR, log));
                    continue;
                }else if(!activo.equals("1") && !activo.equals("0")){
                    hayRegErroneos=true;
                    String log = Utilidades.obtenerMensaje("administracion.parametros.fila") +" [" + (rowNumber + 1) + "]: "+Utilidades.obtenerMensaje("administracion.configuracion.errorEstado")+".";
                    logs.add(new Log(Log.ERROR, log));
                    continue;
                }else if(!accion.equals("1") && !accion.equals("2")){
                    hayRegErroneos=true;
                    String log = Utilidades.obtenerMensaje("administracion.parametros.fila") +" [" + (rowNumber + 1) + "]: "+Utilidades.obtenerMensaje("administracion.configuracion.errorAccion")+".";
                    logs.add(new Log(Log.ERROR, log));
                    continue;
                }else if(!UtilCadena.isNullOVacio(fecIniVigenciaString) || !UtilCadena.isNullOVacio(fecFinVigenciaString)){
                    try{
                    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                    if(!UtilCadena.isNullOVacio(fecIniVigenciaString)){
                        fecIniVigencia=df.parse(fecIniVigenciaString);
                    }
                    if(!UtilCadena.isNullOVacio(fecFinVigenciaString)){
                        fecFinVigencia=df.parse(fecFinVigenciaString);
                    }
                    }catch(Exception e){
                        String log = Utilidades.obtenerMensaje("administracion.parametros.fila") +" [" + (rowNumber + 1) + "]: "+Utilidades.obtenerMensaje("administracion.configuracion.errorFormatoFecha")+".";
                        logs.add(new Log(Log.ERROR, log));
                        continue;
                    }
                }
                try {
                    
                    if(s_e.equals("E")){
                    //ES CARGA DE ENCUESTADORES
                        if(codigosEncuestadores.contains(numDoc)){
                            hayRegErroneos=true;
                            String log = Utilidades.obtenerMensaje("administracion.parametros.fila") +" [" + (rowNumber + 1) + "]: "+Utilidades.obtenerMensaje("administracion.configuracion.numDocRepetido")+".";
                            logs.add(new Log(Log.ERROR, log));
                            continue;
                        }
                        Encuestador encuestador=fachadaIns.obtenerEncuestadorPorCodigo(numDoc);
                        codigosEncuestadores.add(numDoc);
                        if("2".equals(accion)){//Eliminar
                            if(encuestador!=null){
                                listaEliminar.add(encuestador);
                            }else{
                                hayRegErroneos=true;
                                String log = Utilidades.obtenerMensaje("administracion.parametros.fila") +" [" + (rowNumber + 1) + "]: "+Utilidades.obtenerMensaje("administracion.configuracion.codigoNoExiste");
                                logs.add(new Log(Log.ERROR, log));
                                continue;
                            }
                        }else{//Crear ó Actualizar
                            if(encuestador!=null){
                                encuestador.setNombre(nombre);
                                encuestador.setPeriodo(UtilFecha.obtenerPeriodoActual());
                                encuestador.setEstado(Estado.A);
                                encuestador.setTelefono(telefono);
                                encuestador.setEmail(email);
                                encuestador.setDireccion(direccion);
                                encuestador.setCelular(celular);
                                encuestador.setSalud(salud);
                                encuestador.setPersonaContacto(personaContacto);
                                encuestador.setTelefonoContacto(telefonoContacto);
                                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                                encuestador.setFecIniVigencia(fecIniVigencia);
                                encuestador.setFecFinVigencia(fecFinVigencia);
                                encuestador.setInstrumento(fachadaIns.consultarInstrumentoVigente());
                                listaActualizar.add(encuestador);
                            }else{
                                encuestador=new Encuestador();
                                encuestador.setCodTipoIdentificacion(tipoDoc);
                                encuestador.setIdentificacion(numDoc);
                                encuestador.setNombre(nombre);
                                encuestador.setPeriodo(UtilFecha.obtenerPeriodoActual());
                                encuestador.setEstado(Estado.A);
                                encuestador.setTelefono(telefono);
                                encuestador.setEmail(email);
                                encuestador.setDireccion(direccion);
                                encuestador.setCelular(celular);
                                encuestador.setSalud(salud);
                                encuestador.setPersonaContacto(personaContacto);
                                encuestador.setTelefonoContacto(telefonoContacto);
                                encuestador.setFecIniVigencia(fecIniVigencia);
                                encuestador.setFecFinVigencia(fecFinVigencia);
                                encuestador.setInstrumento(fachadaIns.consultarInstrumentoVigente());
                                listaCrear.add(encuestador);
                            }
                        }
                    }else{
                    //ES CARGA DE SUPERVISOR
                        if(codigosSupervisores.contains(numDoc)){
                            hayRegErroneos=true;
                            String log = Utilidades.obtenerMensaje("administracion.parametros.fila") +" [" + (rowNumber + 1) + "]: "+Utilidades.obtenerMensaje("administracion.configuracion.codRepetido")+".";
                            logs.add(new Log(Log.ERROR, log));
                            continue;
                        }
                        Supervisor supervisor=fachadaIns.obtenerSupervisorPorCodigo(numDoc);
                        codigosSupervisores.add(numDoc);
                        
                        if("2".equals(accion)){//Eliminar
                            if(supervisor!=null){
                                listaEliminar.add(supervisor);
                            }else{
                                hayRegErroneos=true;
                                String log = Utilidades.obtenerMensaje("administracion.parametros.fila") +" [" + (rowNumber + 1) + "]: "+Utilidades.obtenerMensaje("administracion.configuracion.codigoNoExiste");
                                logs.add(new Log(Log.ERROR, log));
                                continue;
                            }
                        }else{//Crear ó Actualizar
                            if(supervisor!=null){
                                supervisor.setNombre(nombre);
                                supervisor.setPeriodo(UtilFecha.obtenerPeriodoActual());
                                supervisor.setEstado(Estado.A);
                                supervisor.setTelefono(telefono);
                                supervisor.setEmail(email);
                                supervisor.setDireccion(direccion);
                                supervisor.setCelular(celular);
                                supervisor.setSalud(salud);
                                supervisor.setPersonaContacto(personaContacto);
                                supervisor.setTelefonoContacto(telefonoContacto);
                                supervisor.setFecIniVigencia(fecIniVigencia);
                                supervisor.setFecFinVigencia(fecFinVigencia);
                                supervisor.setInstrumento(fachadaIns.consultarInstrumentoVigente());
                                listaActualizar.add(supervisor);
                            }else{
                                supervisor=new Supervisor();
                                supervisor.setCodTipoIdentificacion(tipoDoc);
                                supervisor.setIdentificacion(numDoc);
                                supervisor.setNombre(nombre);
                                supervisor.setPeriodo(UtilFecha.obtenerPeriodoActual());
                                supervisor.setEstado(Estado.A);
                                supervisor.setTelefono(telefono);
                                supervisor.setEmail(email);
                                supervisor.setDireccion(direccion);
                                supervisor.setCelular(celular);
                                supervisor.setSalud(salud);
                                supervisor.setPersonaContacto(personaContacto);
                                supervisor.setTelefonoContacto(telefonoContacto);
                                supervisor.setFecIniVigencia(fecIniVigencia);
                                supervisor.setFecFinVigencia(fecFinVigencia);
                                supervisor.setInstrumento(fachadaIns.consultarInstrumentoVigente());
                                listaCrear.add(supervisor);
                            }
                        }    
                    }
                    

                } catch (Exception e) {
                    hayRegErroneos=true;
                    String log = Utilidades.obtenerMensaje("administracion.parametros.fila") +" [" + (rowNumber + 1) + "]:"+Utilidades.obtenerMensaje("administracion.configuracion.error")+  "'" + e.getMessage() + "'";
                    logs.add(new Log(Log.ERROR, log));
                    continue;
                }
            }
            
            if (hayRegErroneos) {
                Messages.addFlashGlobalError(Utilidades.obtenerMensaje("adm.parametros.config.carga.datosNoCorrectos"));
            }
        } catch (Exception e) {
            hayRegErroneos = true;
            ManejadorErrores.ingresarExcepcionEnLog(e, AdminSupervisoresEncuestadoresBean.class);
            Messages.addFlashGlobalError(Utilidades.obtenerMensaje("adm.parametros.config.carga.errorRegistro"));
        }
    }
    
    public void guardar() throws IOException {
        try {
            for (Object o : listaCrear) {
                if (o instanceof Encuestador) {
                    fachadaIns.guardarEncuestador((Encuestador) o);
                } else {
                    fachadaIns.guardarSupervisor((Supervisor) o);
                }
            }
            for (Object o : listaActualizar) {
                if (o instanceof Encuestador) {
                    fachadaIns.actualizarEncuestador((Encuestador) o);
                } else {
                    fachadaIns.actualizarSupervisor((Supervisor) o);
                }
            }
            for (Object o : listaEliminar) {
                if (o instanceof Encuestador) {
                    fachadaIns.eliminarEncuestador((Encuestador) o);
                } else {
                    fachadaIns.eliminarSupervisor((Supervisor) o);
                }
            }
            encuestadores = fachadaEncSup.getEncuestadores();
            supervisores = fachadaEncSup.getSupervisores();
            Messages.addFlashGlobalInfo(Utilidades.obtenerMensaje("adm.parametros.config.carga.datosGuardados"));
        } catch (Exception e) {
            UtilOut.println(e);
            Messages.addFlashGlobalError(Utilidades.obtenerMensaje("adm.parametros.config.carga.errorRegistro"));
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

    public List<Encuestador> getEncuestadores() {
        return encuestadores;
    }

    public void setEncuestadores(List<Encuestador> encuestadores) {
        this.encuestadores = encuestadores;
    }

    public List<Supervisor> getSupervisores() {
        return supervisores;
    }

    public void setSupervisores(List<Supervisor> supervisores) {
        this.supervisores = supervisores;
    }

    public Encuestador getEncuestadorNuevo() {
        return encuestadorNuevo;
    }

    public void setEncuestadorNuevo(Encuestador encuestadorNuevo) {
        this.encuestadorNuevo = encuestadorNuevo;
    }

    public Encuestador getEncuestadorSeleccionado() {
        return encuestadorSeleccionado;
    }

    public void setEncuestadorSeleccionado(Encuestador encuestadorSeleccionado) {
        this.encuestadorSeleccionado = encuestadorSeleccionado;
    }

    public Supervisor getSupervisorNuevo() {
        return supervisorNuevo;
    }

    public void setSupervisorNuevo(Supervisor supervisorNuevo) {
        this.supervisorNuevo = supervisorNuevo;
    }

    public Supervisor getSupervisorSeleccionado() {
        return supervisorSeleccionado;
    }

    public void setSupervisorSeleccionado(Supervisor supervisorSeleccionado) {
        this.supervisorSeleccionado = supervisorSeleccionado;
    }
    
    public List<String> getTiposIdentificacion() {
        return tiposIdentificacion;
    }

    public boolean isSwEstadoEncuestador() {
        return swEstadoEncuestador;
    }

    public void setSwEstadoEncuestador(boolean swEstadoEncuestador) {
        this.swEstadoEncuestador = swEstadoEncuestador;
    }

    public boolean isSwEstadoSupervisor() {
        return swEstadoSupervisor;
    }

    public void setSwEstadoSupervisor(boolean swEstadoSupervisor) {
        this.swEstadoSupervisor = swEstadoSupervisor;
    }
    
}
