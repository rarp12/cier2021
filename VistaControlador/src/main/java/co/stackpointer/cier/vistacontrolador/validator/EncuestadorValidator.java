/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.validator;

import co.stackpointer.cier.modelo.fachada.EncuestadorSupervisorFacadeLocal;
import co.stackpointer.cier.vista.Utilidades;
import co.stackpointer.cier.vistacontrolador.administracion.configuracion.AdminSupervisoresEncuestadoresBean;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
@ManagedBean(name = "encuestadorValidator")
@ViewScoped
public class EncuestadorValidator implements Validator, Serializable {

    @EJB
    EncuestadorSupervisorFacadeLocal fachadaEncuestador;
    private static final String NUMERIC_PATTERN = "^[0-9]+$";
    private Matcher matcher;

    @Override
    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        if (value != null) {
            String identificacion = value.toString().trim();
            Pattern pattern = Pattern.compile(NUMERIC_PATTERN);
            matcher = pattern.matcher(identificacion);
            if (!matcher.matches()) {
                FacesMessage msg = Messages.createError(Utilidades.obtenerMensaje("administracion.configuracion.formato.numero"));
                throw new ValidatorException(msg);
            } else {
                if (fachadaEncuestador.existeEncuestador(identificacion)) {
                    FacesMessage msg = Messages.createError(identificacion + " ya existe.");
                    throw new ValidatorException(msg);
                }
            }
        }
    }

    public void validateEdit(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        if (value != null) {
            String identificacion = value.toString().trim();
            Pattern pattern = Pattern.compile(NUMERIC_PATTERN);
            matcher = pattern.matcher(identificacion);
            if (!matcher.matches()) {
                FacesMessage msg = Messages.createError(Utilidades.obtenerMensaje("administracion.configuracion.encuestador.formato.numero"));
                throw new ValidatorException(msg);
            } else {
                AdminSupervisoresEncuestadoresBean encuestadoresBean = (AdminSupervisoresEncuestadoresBean) context.getELContext().getELResolver().getValue(context.getELContext(), null, "adminSupervisoresEncuestadores");
                String identificacionActual = encuestadoresBean.getEncuestadorSeleccionado().getIdentificacion();
                if (!identificacionActual.equalsIgnoreCase(identificacion)) {
                    if (fachadaEncuestador.existeEncuestador(identificacion)) {
                        FacesMessage msg = Messages.createError(identificacion + " ya existe.");
                        throw new ValidatorException(msg);
                    }
                }

            }
        }
    }
}
