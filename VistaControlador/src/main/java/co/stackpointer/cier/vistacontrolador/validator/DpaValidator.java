/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.validator;

import co.stackpointer.cier.modelo.fachada.EstablecimientoFacadeLocal;
import co.stackpointer.cier.modelo.fachada.EstructuraDPAFacadeLocal;
import co.stackpointer.cier.modelo.tipo.ParamNivelConsul;
import co.stackpointer.cier.modelo.utilidad.UtilCadena;
import co.stackpointer.cier.vista.Utilidades;
import co.stackpointer.cier.vistacontrolador.administracion.configuracion.AdminDpa;
import java.io.Serializable;
import javax.annotation.PostConstruct;
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
@ManagedBean(name = "dpaValidator")
@ViewScoped
public class DpaValidator implements Validator,Serializable {

    /*@EJB
    PaisFacadeLocal fachada;
    private GestionPaislBean gestionPaislBean;*/
    @EJB
    private EstructuraDPAFacadeLocal facEstDPA;
    @EJB
    private EstablecimientoFacadeLocal establecimientoFacadeLocal;
    private AdminDpa adminDpa;  
    
    @PostConstruct
    public void inicializar() {
    }

    @Override
    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        if (value != null) {
            String nombre = UtilCadena.toUpperCase(value.toString().trim());
            if (true) {
                FacesMessage msg = Messages.createError(nombre + " ya existe.");
                throw new ValidatorException(msg);
            }
        }
    }
    
    public void validateCodigoNivel1(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        if (!UtilCadena.isNullOVacio(value)) {
            String codigo = value.toString().trim();
            if (facEstDPA.existeCodigoDpa(codigo, ParamNivelConsul.UNO.getNivel())) {
                FacesMessage msg = Messages.createError(codigo + " " + Utilidades.obtenerMensaje("aplicacion.general.error.validacion.yaExiste"));
                throw new ValidatorException(msg);
            }
        }
    }   
    
    public void validateEditCodigoNivel1(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        if (!UtilCadena.isNullOVacio(value)) {
            adminDpa = (AdminDpa) context.getELContext().getELResolver().getValue(context.getELContext(), null, "adminDpa");
            String codigoNuevo = value.toString().trim();
            String codigoActual = adminDpa.getDpaSeleccionado().getCodigo();
            if (!codigoActual.equalsIgnoreCase(codigoNuevo)) {
                if (facEstDPA.existeCodigoDpa(codigoNuevo, ParamNivelConsul.UNO.getNivel())) {
                    FacesMessage msg = Messages.createError(codigoNuevo + " " + Utilidades.obtenerMensaje("aplicacion.general.error.validacion.yaExiste"));
                    throw new ValidatorException(msg);
                }
            }
        }
    }
    
    public void validateCodigoNivel2(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        if (!UtilCadena.isNullOVacio(value)) {
            String codigo = value.toString().trim();
            if (facEstDPA.existeCodigoDpa(codigo, ParamNivelConsul.DOS.getNivel())) {
                FacesMessage msg = Messages.createError(codigo + " " + Utilidades.obtenerMensaje("aplicacion.general.error.validacion.yaExiste"));
                throw new ValidatorException(msg);
            }
        }
    }   
    
    public void validateEditCodigoNivel2(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        if (!UtilCadena.isNullOVacio(value)) {
            adminDpa = (AdminDpa) context.getELContext().getELResolver().getValue(context.getELContext(), null, "adminDpa");
            String codigoNuevo = value.toString().trim();
            String codigoActual = adminDpa.getDpaSeleccionado().getCodigo();
            if (!codigoActual.equalsIgnoreCase(codigoNuevo)) {
                if (facEstDPA.existeCodigoDpa(codigoNuevo, ParamNivelConsul.DOS.getNivel())) {
                    FacesMessage msg = Messages.createError(codigoNuevo + " " + Utilidades.obtenerMensaje("aplicacion.general.error.validacion.yaExiste"));
                    throw new ValidatorException(msg);
                }
            }
        }
    }
    
    public void validateCodigoNivel3(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        if (!UtilCadena.isNullOVacio(value)) {
            String codigo = value.toString().trim();
            if (facEstDPA.existeCodigoDpa(codigo, ParamNivelConsul.TRES.getNivel())) {
                FacesMessage msg = Messages.createError(codigo + " " + Utilidades.obtenerMensaje("aplicacion.general.error.validacion.yaExiste"));
                throw new ValidatorException(msg);
            }
        }
    }   
    
    public void validateEditCodigoNivel3(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        if (!UtilCadena.isNullOVacio(value)) {
            adminDpa = (AdminDpa) context.getELContext().getELResolver().getValue(context.getELContext(), null, "adminDpa");
            String codigoNuevo = value.toString().trim();
            String codigoActual = adminDpa.getDpaSeleccionado().getCodigo();
            if (!codigoActual.equalsIgnoreCase(codigoNuevo)) {
                if (facEstDPA.existeCodigoDpa(codigoNuevo, ParamNivelConsul.TRES.getNivel())) {
                    FacesMessage msg = Messages.createError(codigoNuevo + " " + Utilidades.obtenerMensaje("aplicacion.general.error.validacion.yaExiste"));
                    throw new ValidatorException(msg);
                }
            }
        }
    }
    
    public void validateCodigoNivel4(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        if (!UtilCadena.isNullOVacio(value)) {
            String codigo = value.toString().trim();
            if (facEstDPA.existeCodigoDpa(codigo, ParamNivelConsul.CUATRO.getNivel())) {
                FacesMessage msg = Messages.createError(codigo + " " + Utilidades.obtenerMensaje("aplicacion.general.error.validacion.yaExiste"));
                throw new ValidatorException(msg);
            }
        }
    }   
    
    public void validateEditCodigoNivel4(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        if (!UtilCadena.isNullOVacio(value)) {
            adminDpa = (AdminDpa) context.getELContext().getELResolver().getValue(context.getELContext(), null, "adminDpa");
            String codigoNuevo = value.toString().trim();
            String codigoActual = adminDpa.getDpaSeleccionado().getCodigo();
            if (!codigoActual.equalsIgnoreCase(codigoNuevo)) {
                if (facEstDPA.existeCodigoDpa(codigoNuevo, ParamNivelConsul.CUATRO.getNivel())) {
                    FacesMessage msg = Messages.createError(codigoNuevo + " " + Utilidades.obtenerMensaje("aplicacion.general.error.validacion.yaExiste"));
                    throw new ValidatorException(msg);
                }
            }
        }
    }
    
    public void validateCodigoNivel5(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        if (!UtilCadena.isNullOVacio(value)) {
            String codigo = value.toString().trim();
            if (facEstDPA.existeCodigoDpa(codigo, ParamNivelConsul.CINCO.getNivel())) {
                FacesMessage msg = Messages.createError(codigo + " " + Utilidades.obtenerMensaje("aplicacion.general.error.validacion.yaExiste"));
                throw new ValidatorException(msg);
            }
        }
    }   
    
    public void validateEditCodigoNivel5(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        if (!UtilCadena.isNullOVacio(value)) {
            adminDpa = (AdminDpa) context.getELContext().getELResolver().getValue(context.getELContext(), null, "adminDpa");
            String codigoNuevo = value.toString().trim();
            String codigoActual = adminDpa.getDpaSeleccionado().getCodigo();
            if (!codigoActual.equalsIgnoreCase(codigoNuevo)) {
                if (facEstDPA.existeCodigoDpa(codigoNuevo, ParamNivelConsul.CINCO.getNivel())) {
                    FacesMessage msg = Messages.createError(codigoNuevo + " " + Utilidades.obtenerMensaje("aplicacion.general.error.validacion.yaExiste"));
                    throw new ValidatorException(msg);
                }
            }
        }
    }
    
    public void validateCodigoEst(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        if (!UtilCadena.isNullOVacio(value)) {
            String codigo = value.toString().trim();
            if (establecimientoFacadeLocal.existeCodigoEst(codigo)) {
                FacesMessage msg = Messages.createError(codigo + " " + Utilidades.obtenerMensaje("aplicacion.general.error.validacion.yaExiste"));
                throw new ValidatorException(msg);
            }
        }
    }
    
    public void validateEditCodigoEst(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        if (!UtilCadena.isNullOVacio(value)) {
            adminDpa = (AdminDpa) context.getELContext().getELResolver().getValue(context.getELContext(), null, "adminDpa");
            String codigoNuevo = value.toString().trim();
            String codigoActual = adminDpa.getEstablecimientoSeleccionado().getCodigo();
            if (!codigoActual.equalsIgnoreCase(codigoNuevo)) {
                if (establecimientoFacadeLocal.existeCodigoEst(codigoNuevo)) {
                    FacesMessage msg = Messages.createError(codigoNuevo + " " + Utilidades.obtenerMensaje("aplicacion.general.error.validacion.yaExiste"));
                    throw new ValidatorException(msg);
                }
            }
        }
    }
    
    public void validateNombreEst(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        if (!UtilCadena.isNullOVacio(value)) {
            String nombre = value.toString().trim().toLowerCase();
            if (establecimientoFacadeLocal.existeNombreEst(nombre)) {
                FacesMessage msg = Messages.createError(nombre + " " + Utilidades.obtenerMensaje("aplicacion.general.error.validacion.yaExiste"));
                throw new ValidatorException(msg);
            }
        }
    }
    
    public void validateEditNombreEst(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        if (!UtilCadena.isNullOVacio(value)) {
            adminDpa = (AdminDpa) context.getELContext().getELResolver().getValue(context.getELContext(), null, "adminDpa");
            String nombreNuevo = value.toString().trim();
            String nombreActual = adminDpa.getEstablecimientoSeleccionado().getNombre();
            if (!nombreActual.equalsIgnoreCase(nombreNuevo)) {
                if (establecimientoFacadeLocal.existeNombreEst(nombreNuevo)) {
                    FacesMessage msg = Messages.createError(nombreNuevo + " " + Utilidades.obtenerMensaje("aplicacion.general.error.validacion.yaExiste"));
                    throw new ValidatorException(msg);
                }
            }
        }
    }

    public void validateCodigoSed(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        if (!UtilCadena.isNullOVacio(value)) {
            String codigo = value.toString().trim();
            if (establecimientoFacadeLocal.existeCodigoSed(codigo)) {
                FacesMessage msg = Messages.createError(codigo + " " + Utilidades.obtenerMensaje("aplicacion.general.error.validacion.yaExiste"));
                throw new ValidatorException(msg);
            }
        }
    }
    
    public void validateEditCodigoSed(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        if (!UtilCadena.isNullOVacio(value)) {
            adminDpa = (AdminDpa) context.getELContext().getELResolver().getValue(context.getELContext(), null, "adminDpa");
            String codigoNuevo = value.toString().trim();
            String codigoActual = adminDpa.getSedeSeleccionado().getCodigo();
            if (!codigoActual.equalsIgnoreCase(codigoNuevo)) {
                if (establecimientoFacadeLocal.existeCodigoSed(codigoNuevo)) {
                    FacesMessage msg = Messages.createError(codigoNuevo + " " + Utilidades.obtenerMensaje("aplicacion.general.error.validacion.yaExiste"));
                    throw new ValidatorException(msg);
                }
            }
        }
    }
    
    public void validateNombreSed(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        if (!UtilCadena.isNullOVacio(value)) {
            String nombre = value.toString().trim().toLowerCase();
            if (establecimientoFacadeLocal.existeNombreSed(nombre)) {
                FacesMessage msg = Messages.createError(nombre + " " + Utilidades.obtenerMensaje("aplicacion.general.error.validacion.yaExiste"));
                throw new ValidatorException(msg);
            }
        }
    }
    
    public void validateEditNombreSed(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        if (!UtilCadena.isNullOVacio(value)) {
            adminDpa = (AdminDpa) context.getELContext().getELResolver().getValue(context.getELContext(), null, "adminDpa");
            String nombreNuevo = value.toString().trim();
            String nombreActual = adminDpa.getSedeSeleccionado().getNombre();
            if (!nombreActual.equalsIgnoreCase(nombreNuevo)) {
                if (establecimientoFacadeLocal.existeNombreSed(nombreNuevo)) {
                    FacesMessage msg = Messages.createError(nombreNuevo + " " + Utilidades.obtenerMensaje("aplicacion.general.error.validacion.yaExiste"));
                    throw new ValidatorException(msg);
                }
            }
        }
    }

    /*public void validateEdit(FacesContext context, UIComponent component,
    Object value) throws ValidatorException {
    if (value != null) {
    gestionPaislBean = (GestionPaislBean) context.getELContext().getELResolver().getValue(context.getELContext(), null, "gestionPais");
    String nombreNuevo = UtilCadena.toUpperCase(value.toString().trim());
    String nombreActual = gestionPaislBean.getPaisSeleccionado().getPais();
    if (!nombreActual.equalsIgnoreCase(nombreNuevo)) {
    if (fachada.existePais(nombreNuevo)) {
    FacesMessage msg = Messages.createError(nombreNuevo + " ya existe.");
    throw new ValidatorException(msg);
    }
    }
    }
    }*/
    
    
    
}
