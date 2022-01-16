/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vista;

import java.io.File;
import java.util.Map;
import java.util.ResourceBundle;
import javax.faces.application.ProjectStage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author mrodriguez
 */
public class FuncionPrime {
    public static final String DIRECTORIO_IMAGENES = "/resources/imagenes/";
    
    public static ProjectStage getAmbiente() {
        return FacesContext.getCurrentInstance().getApplication().getProjectStage();
    }

    public static String recuperarParametro(String parametro) {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(parametro);
    }

    public static void mostrarDialogo(String widgetVar) {
         RequestContext.getCurrentInstance().openDialog(widgetVar);
    }
   
    public static void mostrarDialogo(String widgetVar, Map<String, Object> options) {
        RequestContext.getCurrentInstance().openDialog(widgetVar, options, null);
    }
   
    public static void ocultarDialogo(String widgetVar) {
        RequestContext.getCurrentInstance().closeDialog(widgetVar);
    }

    public static void ocultarDialogo(Object object) {
        RequestContext.getCurrentInstance().closeDialog(object);
    }

    public static ResourceBundle getBundle(String nameBundle) {
        FacesContext fc = FacesContext.getCurrentInstance();
        return fc.getApplication().getResourceBundle(fc, nameBundle);
    }

    public static String getBundle(String nameBundle, String propiedad) {
        return getBundle(nameBundle).getString(propiedad);
    }

    public static File getImageFileBundle(String propiedad) {
        FacesContext contexto = FacesContext.getCurrentInstance();
        String url = getBundle("img", propiedad);
        String realPath = ((ServletContext) contexto.getExternalContext().getContext()).getRealPath(url);
        return new File(realPath);
    }

}
