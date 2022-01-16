/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.parametro.tipologias;

import co.stackpointer.cier.modelo.auditoria.InterceptorAdministracion;
import co.stackpointer.cier.modelo.auditoria.RegistrarAuditoria;
import co.stackpointer.cier.modelo.entidad.instrumento.Tipologia;
import co.stackpointer.cier.modelo.entidad.instrumento.TipologiaValor;
import co.stackpointer.cier.modelo.entidad.instrumento.TipologiaValorNombre;
import co.stackpointer.cier.modelo.excepcion.ErrorIntegridad;
import co.stackpointer.cier.modelo.fachada.InstrumentoFacadeLocal;
import co.stackpointer.cier.modelo.fachada.ParametrosFacadeLocal;
import co.stackpointer.cier.modelo.tipo.BitacoraTransaccion;
import co.stackpointer.cier.modelo.utilidad.UtilOut;
import co.stackpointer.cier.vista.ManejadorErrores;
import co.stackpointer.cier.vista.Utilidades;
import co.stackpointer.cier.vistacontrolador.bean.UsuarioSesionBean;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.interceptor.Interceptors;
import org.omnifaces.util.Messages;
import org.primefaces.context.RequestContext;

/**
 *
 * @author user
 */
@ManagedBean(name = "tipologias")
@ViewScoped
public class TipologiasBean {

    @EJB
    private ParametrosFacadeLocal facParam;
    @EJB
    private InstrumentoFacadeLocal facTipos;
    private List<TipologiaValorNombre> lista;
    private TipologiaValorNombre tipologiaSel;
    private TipologiaValor tipologiaNew;
    private Tipologia tipologiaObj;
    private String tipologia;
    private String nombreTipologia;
    private boolean swTipologiaSeleccionada;
    @ManagedProperty(value = "#{usuarioSesion}")
    private UsuarioSesionBean usuarioSesion;
    private String nombreNuevo;

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
        this.consultar();
        tipologiaObj = facTipos.obtenerTipologiaPorCodigo(tipologia);
    }

    public void consultar() {
        lista = facParam.consultarValoresTipologia(tipologia, usuarioSesion.getUsuario().getIdioma().getId());
    }

    public void guardar() {
        try {
            for (TipologiaValorNombre TipologiaValorNombreNombre : lista) {
                facParam.modificarTipologiaValor(TipologiaValorNombreNombre);
            }
            Messages.addFlashGlobalInfo(Utilidades.obtenerMensaje("administracion.general.mensaje.actualizar.exito"));
            this.consultar();
        } catch (Exception e) {
            UtilOut.println(e);
            Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("gerencial.bid.consulta.adminAmbito.upps") + " " + e.getMessage());
        }finally{
            RegistrarAuditoria registrar = new RegistrarAuditoria();
            registrar.setFecha(new Date());
            registrar.setNombreMetodo(BitacoraTransaccion.EDITAR_TIPOLOGIA.name());
            registrar.setUsuario(getUsuarioSesion().getUsuario().getUsername());
            registrar.start();
        }            
    }

    public void eliminar() {
        try {
            tipologiaSel.getTipValor().setEstado("I");
            facParam.modificarTipologiaValor(getUsuarioSesion().getUsuario().getUsername(),tipologiaSel.getTipValor());
            this.consultar();
            Messages.addFlashGlobalInfo(Utilidades.obtenerMensaje("administracion.general.mensaje.eliminar.exito"));
        } catch (Exception e) {
            UtilOut.println(e);
            Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("gerencial.bid.consulta.adminAmbito.upps") + " " + e.getMessage());
        }
    }

    public void instanciarNuevo() {
        nombreNuevo = null;
        tipologiaNew = new TipologiaValor();
        tipologiaNew.setTipologia(tipologiaObj);
        tipologiaNew.setEstado("A");
    }

    public void guardarNuevo() {
        try {
            Long idioma = getUsuarioSesion().getUsuario().getIdioma().getId();
            TipologiaValorNombre tn = new TipologiaValorNombre();
            tn.setIdioma(idioma);
            tn.setTipValor(tipologiaNew);
            tn.setNombre(nombreNuevo);
            facParam.crearTipologiaValor(getUsuarioSesion().getUsuario().getUsername(),tn);
            Messages.addFlashGlobalInfo(Utilidades.obtenerMensaje("administracion.general.mensaje.guardar.exito"));
            this.consultar();
        } catch (Exception err) {
            RequestContext.getCurrentInstance().addCallbackParam("validationFailed", true);
            if (err instanceof ErrorIntegridad) {
                Messages.addFlashGlobalError(Utilidades.obtenerMensaje("administracion.tipologias.dlg.agregar.duplicate.message"));
            } else {
                ManejadorErrores.ingresarExcepcionEnLog(err, TipologiasBean.class);
                Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"));
            }
        }
    }

    public String getNombreTipologia() {
        return nombreTipologia;
    }

    public void setNombreTipologia(String nombreTipologia) {
        this.nombreTipologia = nombreTipologia;
    }

    public boolean isSwTipologiaSeleccionada() {
        return swTipologiaSeleccionada;
    }

    public void setSwTipologiaSeleccionada(boolean swTipologiaSeleccionada) {
        this.swTipologiaSeleccionada = swTipologiaSeleccionada;
    }

    public List<TipologiaValorNombre> getLista() {
        return lista;
    }

    public void setLista(List<TipologiaValorNombre> lista) {
        this.lista = lista;
    }

    public ParametrosFacadeLocal getFacParam() {
        return facParam;
    }

    public void setFacParam(ParametrosFacadeLocal facParam) {
        this.facParam = facParam;
    }

    public UsuarioSesionBean getUsuarioSesion() {
        return usuarioSesion;
    }

    public void setUsuarioSesion(UsuarioSesionBean usuarioSesion) {
        this.usuarioSesion = usuarioSesion;
    }

    public TipologiaValorNombre getTipologiaSel() {
        return tipologiaSel;
    }

    public void setTipologiaSel(TipologiaValorNombre tipologiaSel) {
        this.tipologiaSel = tipologiaSel;
    }

    public TipologiaValor getTipologiaNew() {
        return tipologiaNew;
    }

    public void setTipologiaNew(TipologiaValor tipologiaNew) {
        this.tipologiaNew = tipologiaNew;
    }

    public String getNombreNuevo() {
        return nombreNuevo;
    }

    public void setNombreNuevo(String nombreNuevo) {
        this.nombreNuevo = nombreNuevo;
    }
}