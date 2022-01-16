/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.validator;

import co.stackpointer.cier.modelo.fachada.BIDFacadeLocal;
import co.stackpointer.cier.vista.Utilidades;
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
 * @author rrocha
 */
@ManagedBean(name = "codigoPerido")
@ViewScoped
public class PeriodoBidValidator implements Validator {

    @EJB
    private BIDFacadeLocal fachadaBID;
    private static final String PERIODO_PATTERN = "^[0-9]{4}((0[1-9])|(1[012]))$";
    private Pattern pattern = Pattern.compile(PERIODO_PATTERN);    
    private Matcher matcher;

    @Override
    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        if (value != null) {
            matcher = pattern.matcher(value.toString());
            if (!matcher.matches()) {
                FacesMessage msg = Messages.createError(Utilidades.obtenerMensaje("gerencial.bid.error.validacion.periodo.formato"));
                throw new ValidatorException(msg);
            } else {
                boolean aux = fachadaBID.consultarPeriodoCodigo(value.toString());
                if (aux) {
                    FacesMessage msg = Messages.createError(Utilidades.obtenerMensaje("gerencial.bid.error.validacion.periodo.existe"));
                    throw new ValidatorException(msg);
                }
            }
        }
    }
}
