/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vista;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author StackPointer
 */
public class Utilidades {

    public static void mostrarMensaje(String titulo, String mensaje, FacesMessage.Severity severity) {
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, new FacesMessage(severity, titulo, mensaje));
    }

    public static void mostrarMensajeError(String titulo, String mensaje) {
        mostrarMensaje(titulo, mensaje,FacesMessage.SEVERITY_ERROR);
    }

    public static void mostrarMensajeAdvertencia(String titulo, String mensaje) {
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, titulo, mensaje));
    }

    public static void mostrarMensajeFatal(String titulo, String mensaje) {
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, titulo, mensaje));
    }
    
     public static void mostrarMensajeInfo(String titulo, String mensaje) {
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, titulo, mensaje));
    }
     
    public static String obtenerMensaje(String mensaje, Object... parametros) {
        mensaje = mensaje!=null?obtenerEtiqueta(mensaje):null;
        for (int contador = 0; contador < parametros.length; contador++) {
            String parametro = "\\{" + contador + "\\}";
            try {
                mensaje = mensaje.replaceAll(parametro, parametros[contador].toString());
            } catch (IndexOutOfBoundsException ex) {
                mensaje = mensaje.replaceAll(parametro, Matcher.quoteReplacement(parametros[contador].toString()));
            }
        }
        return mensaje;
    }
    
     private static String obtenerEtiqueta(String llave) {
        try {
            ResourceBundle recursos = ResourceBundle.getBundle("etiquetas", FacesContext.getCurrentInstance().getViewRoot().getLocale());
            return recursos.getString(llave);
        } catch (MissingResourceException ex) {
            return "";
        }
    }
}
