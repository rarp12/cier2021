/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.validator;

import co.stackpointer.cier.modelo.fachada.AccesoFacadeLocal;
import co.stackpointer.cier.modelo.utilidad.UtilCadena;
import co.stackpointer.cier.vista.Utilidades;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import org.omnifaces.util.Messages;

/**
 *
 * @author stackpointer
 */
@ManagedBean(name = "username")
@ViewScoped
public class UsernameValidator implements Validator {
    @EJB
    AccesoFacadeLocal fachada;

    @Override
    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
       if (value != null) {            
            String username = UtilCadena.toUpperCase(value.toString().trim());
            if (fachada.existeUsername(username)) {
                FacesMessage msg = Messages.createError(username + " " + Utilidades.obtenerMensaje("aplicacion.general.error.validacion.yaExiste"));
                throw new ValidatorException(msg);
            }
        }
    }
}
