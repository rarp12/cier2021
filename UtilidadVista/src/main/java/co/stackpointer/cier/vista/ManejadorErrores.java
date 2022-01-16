/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vista;

import co.stackpointer.cier.modelo.excepcion.ErrorValidacion;
import javax.faces.context.FacesContext;
import javax.faces.event.FacesEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author rrocha
 */
public class ManejadorErrores {
    
    public void manejarExcepcion(Exception excepcion) {
        manejarExcepcion(excepcion, null);
    }
    
    public static void ingresarExcepcionEnLog(Exception e, Class c) {
        Logger logger = LogManager.getLogger(c);
        String message = e.toString() + System.lineSeparator();
        String stacktrace = "stacktrace: ";
        for (StackTraceElement stackTraceElement : e.getStackTrace()) {
            stacktrace = stacktrace + 
                    stackTraceElement.toString() + 
                    System.lineSeparator();
        }
        stacktrace = stacktrace +
                "--------------------------------------------------------";
        logger.error(message + stacktrace);
    }

    public static String extraerIdClienteComponente(FacesEvent evento) {
        return evento == null ? null : evento.getComponent().getClientId(FacesContext.getCurrentInstance());
    }
    
    public void manejarExcepcion(Exception excepcion, FacesEvent evento) {
        if(excepcion instanceof ErrorValidacion ){
            String[] msjs = ((ErrorValidacion) excepcion).getMessage().split(";");
            String titulo = Utilidades.obtenerMensaje("gerencial.bid.consulta.adminAmbito.errorValidacion") + ". " + msjs[0];
            Utilidades.mostrarMensajeAdvertencia(null, titulo);
            if (msjs.length > 1) {
                for (int i = 1; i < msjs.length; i++) {
                    String detalle = msjs[i];
                    Utilidades.mostrarMensajeAdvertencia(null, detalle);
                }
            }
        }else{
            Utilidades.mostrarMensajeFatal(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"),"");
        }
    }
    
}
