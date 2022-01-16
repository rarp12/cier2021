/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.validator;

import co.stackpointer.cier.modelo.fachada.PaisFacadeLocal;
import co.stackpointer.cier.modelo.utilidad.UtilCadena;
import co.stackpointer.cier.vista.ManejadorErrores;
import co.stackpointer.cier.vista.Utilidades;
import static co.stackpointer.cier.vista.Utilidades.mostrarMensajeError;
import co.stackpointer.cier.vistacontrolador.bean.GestionPaislBean;
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
@ManagedBean(name = "paisValidator")
@ViewScoped
public class PaisValidator implements Validator {

    @EJB
    PaisFacadeLocal fachada;
    private GestionPaislBean gestionPaislBean;

    @Override
    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        try {
            if (value != null) {
                String nombre = UtilCadena.toUpperCase(value.toString().trim());
                if (fachada.existePais(nombre)) {
                    FacesMessage msg = Messages.createError(nombre + Utilidades.obtenerMensaje("aplicacion.general.error.validacion.yaExiste"));
                    throw new ValidatorException(msg);
                }
            }
        } catch (Exception e) {
            mostrarMensajeError(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"), e.getMessage());           
            ManejadorErrores.ingresarExcepcionEnLog(e, PaisValidator.class);
        }
    }

    public void validateEdit(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        try {
            if (value != null) {
                gestionPaislBean = (GestionPaislBean) context.getELContext().getELResolver().getValue(context.getELContext(), null, "gestionPais");
                String nombreNuevo = UtilCadena.toUpperCase(value.toString().trim());
                String nombreActual = gestionPaislBean.getPaisSeleccionado().getPais();
                if (!nombreActual.equalsIgnoreCase(nombreNuevo)) {
                    if (fachada.existePais(nombreNuevo)) {
                        FacesMessage msg = Messages.createError(nombreNuevo + Utilidades.obtenerMensaje("aplicacion.general.error.validacion.yaExiste"));
                        throw new ValidatorException(msg);
                    }
                }
            }
        } catch (Exception e) {
            mostrarMensajeError(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"), e.getMessage());
            ManejadorErrores.ingresarExcepcionEnLog(e, PaisValidator.class);
        }
    }
}
