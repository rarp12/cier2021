/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.validator;

import co.stackpointer.cier.modelo.utilidad.UtilCadena;
import co.stackpointer.cier.vista.Utilidades;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import org.omnifaces.util.Messages;

/**
 *
 * @author stackpointer
 */
@FacesValidator("coordenadaValidator")
public class CoordenadaValidator implements Validator {

    private static final String COORD_PATTERN = "^(\\-?\\d+(\\.\\d+)?),\\s*(\\-?\\d+(\\.\\d+)?)$";
    private static Pattern pattern = Pattern.compile(COORD_PATTERN);
    private static Matcher matcher;

    @Override
    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        if (value != null) {
            matcher = pattern.matcher(value.toString());
            if (!matcher.matches()) {
                FacesMessage msg = Messages.createError(Utilidades.obtenerMensaje("aplicacion.general.error.validacion.coordenada"));
                throw new ValidatorException(msg);

            }
        }

    }

    public static boolean formatoErrado(String coordenada) {
        boolean sw = false;
        matcher = pattern.matcher(coordenada);
        sw = matcher.matches() ? false : true;
        return sw;
    }
}
