/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.validator;

import co.stackpointer.cier.modelo.fachada.AccesoFacadeLocal;
import co.stackpointer.cier.modelo.utilidad.UtilCadena;
import co.stackpointer.cier.vista.Utilidades;
import co.stackpointer.cier.vistacontrolador.bean.GestionRolBean;
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
@ManagedBean(name = "nombreRol")
@ViewScoped
public class NombreRolValidator implements Validator {

    @EJB
    AccesoFacadeLocal fachada;
    private GestionRolBean gestionRolBean;

    @Override
    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        if (value != null) {
            String nombre = UtilCadena.toUpperCase(value.toString().trim());
            if (fachada.existeNombreRol(nombre)) {
                FacesMessage msg = Messages.createError(Utilidades.obtenerMensaje("administracion.usuario.consulta.nombre") + ": " + Utilidades.obtenerMensaje("aplicacion.general.error.validacion.yaExiste"));
                throw new ValidatorException(msg);
            }
        }
    }

    public void validateEdit(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        if (value != null) {
            gestionRolBean = (GestionRolBean) context.getELContext().getELResolver().getValue(context.getELContext(), null, "gestionRol");
            String nombreNuevo = UtilCadena.toUpperCase(value.toString().trim());
            String nombreActual = gestionRolBean.getRolSeleccionado().getNombre();
            if (!nombreActual.equalsIgnoreCase(nombreNuevo)) {
                if (fachada.existeNombreRol(nombreNuevo)) {
                    FacesMessage msg = Messages.createError(Utilidades.obtenerMensaje("administracion.usuario.consulta.nombre") + ": " + Utilidades.obtenerMensaje("aplicacion.general.error.validacion.yaExiste"));
                    throw new ValidatorException(msg);
                }
            }
        }
    }
}
