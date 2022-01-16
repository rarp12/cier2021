/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.bean;

import co.stackpointer.cier.modelo.auditoria.RegistrarAuditoria;
import co.stackpointer.cier.modelo.entidad.indicador.UnidadFuncional;
import co.stackpointer.cier.modelo.entidad.instrumento.Tipologia;
import co.stackpointer.cier.modelo.entidad.instrumento.TipologiaValorNombre;
import co.stackpointer.cier.modelo.entidad.unidad.UndFuncional;
import co.stackpointer.cier.modelo.excepcion.ErrorIntegridad;
import co.stackpointer.cier.modelo.fachada.UndFuncionalFacadeLocal;
import co.stackpointer.cier.modelo.tipo.BitacoraTransaccion;
import co.stackpointer.cier.modelo.utilidad.UtilOut;
import co.stackpointer.cier.vista.ManejadorErrores;
import co.stackpointer.cier.vista.Utilidades;
import java.util.Date;
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
 * @author rjay1
 */
@ManagedBean(name = "gestUndFunc")
@ViewScoped
public class GestionUnidades {

    @EJB
    UndFuncionalFacadeLocal facUndFunc;
    @ManagedProperty(value = "#{usuarioSesion}")
    private UsuarioSesionBean usuarioSesion;
    private List<UndFuncional> unidades;
    private UndFuncional undSel;
    private UndFuncional undNew;
    private UnidadFuncional unidadFuncional;
    private List<Tipologia> tipologias;
    private List<TipologiaValorNombre> tipologiasValor;
    private List<TipologiaValorNombre> undFunValores;
    private Tipologia tipologiaSel;
    private TipologiaValorNombre tipologiaValSel;
    private TipologiaValorNombre tipologiaValDel;
    private Long idioma;

    @PostConstruct
    public void init() {
        try{
        idioma = getUsuarioSesion().getUsuario().getIdioma().getId();
        datosIniciales();
        }finally{
            RegistrarAuditoria registrar = new RegistrarAuditoria();
            registrar.setFecha(new Date());
            registrar.setNombreMetodo(BitacoraTransaccion.CONSULTAR_UNIDADES_FUNCIONALES.name());
            registrar.setUsuario(usuarioSesion.getUsuario().getUsername());
            registrar.start();
        }
    }

    public void datosIniciales() {
        try {
            unidades = facUndFunc.getUnidades();
            tipologias = facUndFunc.getTipologiasTipoEspacio();
        } catch (Exception err) {
            ManejadorErrores.ingresarExcepcionEnLog(err, GestionUnidades.class);
            Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"));
        }
    }

    public void buscarValores(UndFuncional unidad) {
        try {
            undFunValores = facUndFunc.getTipoValorByUnidad(unidad.getId(), idioma);
            unidadFuncional = facUndFunc.getUnidadFuncinal(unidad.getId());
        } catch (Exception err) {
            ManejadorErrores.ingresarExcepcionEnLog(err, GestionUnidades.class);
            Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"));
        }
    }

    public void onChangeTipologia() {
        try {
            if (tipologiaSel != null) {
                tipologiasValor = facUndFunc.getTipologiasValorNombre(tipologiaSel.getCodigo(), idioma);
            }
        } catch (Exception e) {
            RequestContext.getCurrentInstance().addCallbackParam("validationFailed", true);
            Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion") + " " + e.getMessage());
        }
    }

    public void instanciarNuevaUnidad() {
        undNew = new UndFuncional();
        undNew.setEstado("A");
    }

    public void guardarUnidad() {
        try {
            undNew.setCodigo(undNew.getCodigo().replace(' ', '_').toUpperCase());
            facUndFunc.guardarUnidad(usuarioSesion.getUsuario().getUsername(), undNew);
            Messages.addFlashGlobalInfo(Utilidades.obtenerMensaje("administracion.general.mensaje.guardar.exito"));
            datosIniciales();
            Messages.addFlashGlobalInfo(Utilidades.obtenerMensaje("administracion.general.mensaje.guardar.exito"));
        } catch (Exception err) {
            RequestContext.getCurrentInstance().addCallbackParam("validationFailed", true);
            if (err instanceof ErrorIntegridad) {
                Messages.addFlashGlobalError(err.getMessage());
            } else {
                ManejadorErrores.ingresarExcepcionEnLog(err, GestionUnidades.class);
                Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"));
            }
        }
    }

    public void editarUnidad() {
        try {
            facUndFunc.actualizarUnidad(usuarioSesion.getUsuario().getUsername(), undSel);
            datosIniciales();
            Messages.addFlashGlobalInfo(Utilidades.obtenerMensaje("administracion.general.mensaje.actualizar.exito"));
        } catch (Exception err) {
            ManejadorErrores.ingresarExcepcionEnLog(err, GestionUnidades.class);
            Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"));
        }
    }

    public void guardarUnidadValor() {
        try {
            if (tipologiaSel == null || tipologiaValSel == null) {
                Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("administracion.tipologias.dlg.detalles.empty.fields.message"));
            } else {
                if (facUndFunc.validarNoExisteDuplicidad(undSel.getId(), tipologiaSel.getCodigo(), tipologiaValSel.getTipValor().getCodigo())) {
                    facUndFunc.guardarTipoValor(undSel.getId(), tipologiaSel.getCodigo(), tipologiaValSel.getTipValor().getCodigo());
                    undFunValores = facUndFunc.getTipoValorByUnidad(undSel.getId(), idioma);
                    tipologiaValSel = null;
                    Messages.addFlashGlobalInfo(Utilidades.obtenerMensaje("administracion.general.mensaje.guardar.exito"));
                } else {
                    Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("administracion.tipologias.dlg.detalles.duplicate.message"));
                }
            }
        } catch (Exception err) {
            RequestContext.getCurrentInstance().addCallbackParam("validationFailed", true);
            if (err instanceof ErrorIntegridad) {
                Messages.addFlashGlobalError(err.getMessage());
            } else {
                ManejadorErrores.ingresarExcepcionEnLog(err, GestionUnidades.class);
                Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"));
            }
        }
    }

    public void eliminar() {
        try {
            facUndFunc.eliminarTipoValor(undSel.getId(), tipologiaValDel.getTipValor().getTipologia().getCodigo(), tipologiaValDel.getTipValor().getCodigo());
            undFunValores = facUndFunc.getTipoValorByUnidad(undSel.getId(), idioma);
            tipologiaValDel = null;
            Messages.addFlashGlobalInfo(Utilidades.obtenerMensaje("administracion.general.mensaje.eliminar.exito"));
        } catch (Exception e) {
            UtilOut.println(e);
            Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("gerencial.bid.consulta.adminAmbito.upps") + " " + e.getMessage());
        }
    }

    public UsuarioSesionBean getUsuarioSesion() {
        return usuarioSesion;
    }

    public void setUsuarioSesion(UsuarioSesionBean usuarioSesion) {
        this.usuarioSesion = usuarioSesion;
    }

    public List<UndFuncional> getUnidades() {
        return unidades;
    }

    public void setUnidades(List<UndFuncional> unidades) {
        this.unidades = unidades;
    }

    public UndFuncional getUndSel() {
        return undSel;
    }

    public void setUndSel(UndFuncional undSel) {
        this.undSel = undSel;
    }

    public UndFuncional getUndNew() {
        return undNew;
    }

    public void setUndNew(UndFuncional undNew) {
        this.undNew = undNew;
    }

    public List<Tipologia> getTipologias() {
        return tipologias;
    }

    public void setTipologias(List<Tipologia> tipologias) {
        this.tipologias = tipologias;
    }

    public List<TipologiaValorNombre> getTipologiasValor() {
        return tipologiasValor;
    }

    public void setTipologiasValor(List<TipologiaValorNombre> tipologiasValor) {
        this.tipologiasValor = tipologiasValor;
    }

    public List<TipologiaValorNombre> getUndFunValores() {
        return undFunValores;
    }

    public void setUndFunValores(List<TipologiaValorNombre> undFunValores) {
        this.undFunValores = undFunValores;
    }

    public Tipologia getTipologiaSel() {
        return tipologiaSel;
    }

    public void setTipologiaSel(Tipologia tipologiaSel) {
        this.tipologiaSel = tipologiaSel;
    }

    public TipologiaValorNombre getTipologiaValSel() {
        return tipologiaValSel;
    }

    public void setTipologiaValSel(TipologiaValorNombre tipologiaValSel) {
        this.tipologiaValSel = tipologiaValSel;
    }

    public TipologiaValorNombre getTipologiaValDel() {
        return tipologiaValDel;
    }

    public void setTipologiaValDel(TipologiaValorNombre tipologiaValDel) {
        this.tipologiaValDel = tipologiaValDel;
    }
}