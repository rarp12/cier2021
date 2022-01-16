/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.parametro.estandares;

import co.stackpointer.cier.modelo.entidad.indicador.EstandarLote;
import co.stackpointer.cier.modelo.excepcion.ErrorValidacion;
import co.stackpointer.cier.modelo.fachada.EstandarLoteFacadeLocal;
import co.stackpointer.cier.modelo.fachada.ParametrosFacadeLocal;
import co.stackpointer.cier.vista.Utilidades;
import co.stackpointer.cier.vistacontrolador.bean.UsuarioSesionBean;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.omnifaces.util.Messages;
import org.primefaces.context.RequestContext;

/**
 *
 * @author StackPointer
 */
@ManagedBean(name = "estandarLote")
@ViewScoped
public class EstandarLoteBean implements Serializable {

    @EJB
    EstandarLoteFacadeLocal fachada;
    
    @EJB
    ParametrosFacadeLocal fachadaParametros;
    
    @ManagedProperty(value = "#{usuarioSesion}")
    private UsuarioSesionBean usuarioSesion;
    
    private List<EstandarLote> estandares;
    private EstandarLote estandarNuevo;
    private EstandarLote estandarSeleccionado;
    private static Long tempMinAlumnos;
    private static Long tempMaxAlumnos;
    private static Long tempNumPisos;
    private static BigDecimal tempAreaLoteUrbano;
    private static BigDecimal tempAreaLoteUrbanoMarginal;
    private static BigDecimal tempAreaLoteRural;
    private static BigDecimal tempIndOcupacion;
    private static BigDecimal tempIndConstruccion;
    private static BigDecimal tempAreaCubierta;
    private Double numeroMaximoPisos;

    public EstandarLoteBean() {
    }

    @PostConstruct
    public void init() {
        setEstandares(fachada.getEstandares());
        instanciarNuevoEstandar();
        numeroMaximoPisos = fachadaParametros.consultarEstandarPorCodigo("ESTANDAR_NUMERO_MAX_PISOS").getValor();
    }

    public void instanciarNuevoEstandar() {
        estandarNuevo = new EstandarLote();
    }

    public void guardarEstandar() {
        try {
            setEstandarNuevo(fachada.guardarEstandar(usuarioSesion.getUsuario().getUsername(), estandarNuevo));
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
   
    public void preEditar(EstandarLote estandar) {
        limpiarVariablesTemporales();
        if (estandar != null) {
            estandarSeleccionado = estandar;
            tempMinAlumnos = estandarSeleccionado.getMinAlumnos();
            tempMaxAlumnos = estandarSeleccionado.getMaxAlumnos();
            tempNumPisos = estandarSeleccionado.getNumPisos();
            tempAreaLoteUrbano = estandarSeleccionado.getAreaMinimaLoteUrbano();
            tempAreaLoteUrbanoMarginal = estandarSeleccionado.getAreaMinimaLoteUrbanoMarginal();
            tempAreaLoteRural = estandarSeleccionado.getAreaMinimaLoteRural();
            tempIndOcupacion = estandarSeleccionado.getIndiceOcupacion();
            tempIndConstruccion = estandarSeleccionado.getIndiceConstruccion();
            tempAreaCubierta = estandarSeleccionado.getAreaCubierta();
        }
    }

    public void editarEstandar() {
        try {
            fachada.actualizarEstandar(usuarioSesion.getUsuario().getUsername(), estandarSeleccionado);
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
            fachada.eliminarEstandar(usuarioSesion.getUsuario().getUsername(), estandarSeleccionado);
            estandares.remove(estandarSeleccionado);            
        } catch (Exception e) {
            Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("adm.parametros.config.carga.errorRegistro"));
        }
    }

    private void reestablecerEstandarSeleccionado() {
        estandarSeleccionado.setMinAlumnos(tempMinAlumnos);
        estandarSeleccionado.setMaxAlumnos(tempMaxAlumnos);
        estandarSeleccionado.setNumPisos(tempNumPisos);
        estandarSeleccionado.setAreaMinimaLoteUrbano(tempAreaLoteUrbano);
        estandarSeleccionado.setAreaMinimaLoteUrbanoMarginal(tempAreaLoteUrbanoMarginal);
        estandarSeleccionado.setAreaMinimaLoteRural(tempAreaLoteRural);
        estandarSeleccionado.setIndiceOcupacion(tempIndOcupacion);
        estandarSeleccionado.setIndiceConstruccion(tempIndConstruccion);
        estandarSeleccionado.setAreaCubierta(tempAreaCubierta);
    }

    private void limpiarVariablesTemporales() {
        tempAreaCubierta = null;
        tempAreaLoteUrbano = null;
        tempAreaLoteUrbanoMarginal = null;
        tempAreaLoteRural = null;
        tempMinAlumnos = null;
        tempMaxAlumnos = null;
        tempNumPisos = null;
        tempIndConstruccion = null;
        tempIndOcupacion = null;
    }
    
    private void validarRegistros(EstandarLote estandar){
        
        if( estandar.getMinAlumnos()!= null ){
            if(!isNumericInteger( estandar.getMinAlumnos().toString())){
                throw new ErrorValidacion(Utilidades.obtenerMensaje("adm.parametros.estandar.pc.error.crear.valor.numerico","Minimo Alumunos"));
            }
        }
        
         if( estandar.getMaxAlumnos()!= null ){
            if(!isNumericInteger( estandar.getMaxAlumnos().toString())){
                throw new ErrorValidacion(Utilidades.obtenerMensaje("adm.parametros.estandar.pc.error.crear.valor.numerico","Minimo Alumunos"));
            }
        }
    }
    
    private boolean isNumericInteger(String cadena) {
          return cadena.matches("\\d?");
    }

    public List<EstandarLote> getEstandares() {
        return estandares;
    }

    public void setEstandares(List<EstandarLote> estandares) {
        this.estandares = estandares;
    }

    public EstandarLoteFacadeLocal getFachada() {
        return fachada;
    }

    public void setFachada(EstandarLoteFacadeLocal fachada) {
        this.fachada = fachada;
    }
    
    public EstandarLote getEstandarSeleccionado() {
        return estandarSeleccionado;
    }

    public void setEstandarSeleccionado(EstandarLote estandarSeleccionado) {
        this.estandarSeleccionado = estandarSeleccionado;
    }

    public EstandarLote getEstandarNuevo() {
        return estandarNuevo;
    }

    public void setEstandarNuevo(EstandarLote estandarNuevo) {
        this.estandarNuevo = estandarNuevo;
    }

    public Double getNumeroMaximoPisos() {
        return numeroMaximoPisos;
    }

    public void setNumeroMaximoPisos(Double numeroMaximoPisos) {
        this.numeroMaximoPisos = numeroMaximoPisos;
    }
    
    public UsuarioSesionBean getUsuarioSesion() {
        return usuarioSesion;
    }

    public void setUsuarioSesion(UsuarioSesionBean usuarioSesion) {
        this.usuarioSesion = usuarioSesion;
    }
    
}
