/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.converter;

import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author rrocha
 */
@FacesConverter("stringTablasConverter")
public class StringTablasConverter implements Converter {
    
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) { 
        return null;
    }
    
    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        String valor ="";
        if(object != null){
            valor = object.toString();
            if(valor.length()> 50){
                valor = valor.substring(0, 45)+ "...";
            }
        }
        return valor;
    }
    
}
