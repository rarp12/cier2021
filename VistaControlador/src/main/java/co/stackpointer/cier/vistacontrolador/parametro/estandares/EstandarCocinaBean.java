/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.parametro.estandares;

import co.stackpointer.cier.modelo.entidad.indicador.EstandarCocina;
import co.stackpointer.cier.modelo.entidad.indicador.EstandarLote;
import co.stackpointer.cier.modelo.excepcion.ErrorValidacion;
import co.stackpointer.cier.modelo.fachada.EstandarCocinaFacadeLocal;
import co.stackpointer.cier.modelo.fachada.ParametrosFacadeLocal;
import co.stackpointer.cier.vista.Utilidades;
import co.stackpointer.cier.vistacontrolador.bean.UsuarioSesionBean;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.omnifaces.util.Messages;
import org.primefaces.context.RequestContext;

/**
 *
 * @author rarp1
 */
@ManagedBean(name = "estandarCocina")
@ViewScoped
public class EstandarCocinaBean implements Serializable {

    @EJB
    EstandarCocinaFacadeLocal fachada;
    
    @EJB
    ParametrosFacadeLocal fachadaParametros;
    
    @ManagedProperty(value = "#{usuarioSesion}")
    private UsuarioSesionBean usuarioSesion;
    
    private List<EstandarCocina> estandares;
    private EstandarCocina estandarNuevo;
    private EstandarCocina estandarSeleccionado;
    private static Long tempMinAlumnos;
    private static Long tempMaxAlumnos;
    private static Long tempNumServicios;
    private static BigDecimal tempAreaCocina;
    private static String tempObservacion;

    public EstandarCocinaBean() {
    }

    @PostConstruct
    public void init() {
        this.setEstandares(fachada.getEstandares());
        instanciarNuevoEstandar();
    }

    public void guardarEstandar() {
        try {
            setEstandarNuevo(fachada.guardarEstandarCocina(usuarioSesion.getUsuario().getUsername(), estandarNuevo));
            setEstandares(fachada.getEstandares());
        } catch (Exception ex) {
            if (ex instanceof ErrorValidacion) {
                RequestContext.getCurrentInstance().addCallbackParam("validationFailed", true);
                Messages.addFlashGlobalError(ex.getMessage());
            } else {
                Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("adm.parametros.config.carga.errorRegistro"));
            }
        }
    }

    public void preEditar(EstandarCocina estandar) {
        limpiarVariablesTemporales();
        if (estandar != null) {
            estandarSeleccionado = estandar;
            tempMinAlumnos = estandarSeleccionado.getMinAlumnos();
            tempMaxAlumnos = estandarSeleccionado.getMaxAlumnos();
            tempNumServicios = estandarSeleccionado.getNumServicios();
            tempAreaCocina = estandarSeleccionado.getAreaCocina();
            tempObservacion = estandarSeleccionado.getObservacion();
        }
    }

    public void editarEstandar() {
        try {
            fachada.actualizarEstandarCocina(usuarioSesion.getUsuario().getUsername(), estandarSeleccionado);
            limpiarVariablesTemporales();
        } catch (Exception ex) {
            reestablecerEstandarSeleccionado();
            if (ex instanceof ErrorValidacion) {
                RequestContext.getCurrentInstance().addCallbackParam("validationFailed", true);
                Messages.addFlashGlobalError(ex.getMessage());
            } else {
                Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("adm.parametros.config.carga.errorRegistro"));
            }
        }
    }

    public void eliminarEstandar() {
        try {
            fachada.eliminarEstandarCocina(usuarioSesion.getUsuario().getUsername(), estandarSeleccionado);
            estandares.remove(estandarSeleccionado);
        } catch (Exception e) {
            Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("adm.parametros.config.carga.errorRegistro"));
        }
    }

    private void reestablecerEstandarSeleccionado() {
        estandarSeleccionado.setMinAlumnos(tempMinAlumnos);
        estandarSeleccionado.setMaxAlumnos(tempMaxAlumnos);
        estandarSeleccionado.setNumServicios(tempNumServicios);
        estandarSeleccionado.setAreaCocina(tempAreaCocina);
        estandarSeleccionado.setObservacion(tempObservacion);
    }

    private void limpiarVariablesTemporales() {

        tempMinAlumnos = null;
        tempMaxAlumnos = null;
        tempNumServicios = null;
        tempAreaCocina = null;
        tempObservacion = null;

    }

    private void validarRegistros(EstandarLote estandar) {

        if (estandar.getMinAlumnos() != null) {
            if (!isNumericInteger(estandar.getMinAlumnos().toString())) {
                throw new ErrorValidacion(Utilidades.obtenerMensaje("adm.parametros.estandar.pc.error.crear.valor.numerico", "Minimo Alumunos"));
            }
        }

        if (estandar.getMaxAlumnos() != null) {
            if (!isNumericInteger(estandar.getMaxAlumnos().toString())) {
                throw new ErrorValidacion(Utilidades.obtenerMensaje("adm.parametros.estandar.pc.error.crear.valor.numerico", "Minimo Alumunos"));
            }
        }
    }

    private boolean isNumericInteger(String cadena) {
        return cadena.matches("\\d?");
    }

    public void instanciarNuevoEstandar() {
        estandarNuevo = new EstandarCocina();
    }

    public List<EstandarCocina> getEstandares() {
        return estandares;
    }

    public void setEstandares(List<EstandarCocina> estandares) {
        this.estandares = estandares;
    }

    public EstandarCocina getEstandarNuevo() {
        return estandarNuevo;
    }

    public void setEstandarNuevo(EstandarCocina estandarNuevo) {
        this.estandarNuevo = estandarNuevo;
    }

    public EstandarCocina getEstandarSeleccionado() {
        return estandarSeleccionado;
    }

    public void setEstandarSeleccionado(EstandarCocina estandarSeleccionado) {
        this.estandarSeleccionado = estandarSeleccionado;
    }

    public static Long getTempMinAlumnos() {
        return tempMinAlumnos;
    }

    public static void setTempMinAlumnos(Long tempMinAlumnos) {
        EstandarCocinaBean.tempMinAlumnos = tempMinAlumnos;
    }

    public static Long getTempMaxAlumnos() {
        return tempMaxAlumnos;
    }

    public static void setTempMaxAlumnos(Long tempMaxAlumnos) {
        EstandarCocinaBean.tempMaxAlumnos = tempMaxAlumnos;
    }

    public static Long getTempNumServicios() {
        return tempNumServicios;
    }

    public static void setTempNumServicios(Long tempNumServicios) {
        EstandarCocinaBean.tempNumServicios = tempNumServicios;
    }

    public static BigDecimal getTempAreaCocina() {
        return tempAreaCocina;
    }

    public static void setTempAreaCocina(BigDecimal tempAreaCocina) {
        EstandarCocinaBean.tempAreaCocina = tempAreaCocina;
    }

    public static String getTempObservacion() {
        return tempObservacion;
    }

    public static void setTempObservacion(String tempObservacion) {
        EstandarCocinaBean.tempObservacion = tempObservacion;
    }
    
    public UsuarioSesionBean getUsuarioSesion() {
        return usuarioSesion;
    }

    public void setUsuarioSesion(UsuarioSesionBean usuarioSesion) {
        this.usuarioSesion = usuarioSesion;
    }

}
