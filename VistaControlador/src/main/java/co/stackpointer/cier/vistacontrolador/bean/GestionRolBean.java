/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.bean;

import co.stackpointer.cier.modelo.auditoria.RegistrarAuditoria;
import co.stackpointer.cier.modelo.entidad.acceso.Funcionalidad;
import co.stackpointer.cier.modelo.entidad.acceso.Modulo;
import co.stackpointer.cier.modelo.entidad.acceso.Rol;
import co.stackpointer.cier.modelo.entidad.acceso.RolPermiso;
import co.stackpointer.cier.modelo.fachada.AccesoFacadeLocal;
import co.stackpointer.cier.modelo.tipo.BitacoraTransaccion;
import co.stackpointer.cier.modelo.tipo.Estado;
import co.stackpointer.cier.modelo.tipo.GrupoModulo;
import co.stackpointer.cier.modelo.utilidad.UtilCadena;
import co.stackpointer.cier.vista.Utilidades;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
@ManagedBean(name = "gestionRol")
@ViewScoped
public class GestionRolBean implements Serializable {

    @EJB
    AccesoFacadeLocal fachada;
    @ManagedProperty(value = "#{usuarioSesion}")
    private UsuarioSesionBean usuarioSesion;
    private List<Rol> roles;
    private Rol rolSeleccionado;
    private Rol rolNuevo;
    private List<Permiso> permisos;
    private List<Permiso> filtroPermisos;
    private HashMap<Permiso, Boolean> hmEstadoPermiso;
    //private FuncionalidadDataModel funcionalidadesDataModel;
    //private Funcionalidad[] funcionalidadesSeleccionadas;
    private boolean swRolActivo;
    private String msjConfirmacion;
    private List<String> modulos;
    private String moduloSeleccionado;
    private GrupoModulo grupoSeleccionado;
    
    public GestionRolBean() {
    }

    @PostConstruct
    public void init() {
        try {
            setRoles(fachada.getRoles());
            instanciarNuevoRol();
            modulos = new ArrayList<String>();
        } finally {
            RegistrarAuditoria registrar = new RegistrarAuditoria();
            registrar.setFecha(new Date());
            registrar.setNombreMetodo(BitacoraTransaccion.CONSULTAR_ROL.name());
            registrar.setUsuario(usuarioSesion.getUsuario().getUsername());
            registrar.start();
        }
    }

    public void instanciarNuevoRol() {
        rolNuevo = new Rol();
    }

    public void guardarRol() {
        try {
            rolNuevo.setNombre(UtilCadena.toUpperCase(rolNuevo.getNombre()));
            setRolNuevo(fachada.guardarRol(usuarioSesion.getUsuario().getUsername(), rolNuevo));
            roles.add(rolNuevo);
        } catch (Exception e) {
            RequestContext.getCurrentInstance().addCallbackParam("validationFailed", true);
            Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("adm.parametros.config.carga.errorRegistro"));
            
        }
    }

    public void editarRol() {
        try {
            guardarPermisos();
            cambiarEstadoRol();
            fachada.actualizarRol(usuarioSesion.getUsuario().getUsername(), rolSeleccionado);
        } catch (Exception e) {
            RequestContext.getCurrentInstance().addCallbackParam("validationFailed", true);
            Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion")+" " + e.getMessage());            
        }
    }

    public void validaRolAsignado(Rol rol) {
        msjConfirmacion = Utilidades.obtenerMensaje("administracion.usuario.consulta.confirmarEliminacionRol");        
        if (fachada.tieneUsuarios(rol)) {
            msjConfirmacion = Utilidades.obtenerMensaje("administracion.usuario.consulta.rolAsignadoUsuario")+".\n "
                    + Utilidades.obtenerMensaje("administracion.usuario.consulta.confirmarEliminacionRol");            
        }
    }

    public void eliminarRol() {
        try {
            fachada.eliminarRol(usuarioSesion.getUsuario().getUsername(), rolSeleccionado);
            roles.remove(rolSeleccionado);
        } catch (Exception e) {
            RequestContext.getCurrentInstance().addCallbackParam("validationFailed", true);
            Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion")+" " + e.getMessage());
        }
    }

    public void cargarPanelAsignaPermiso(Rol rol) {
        try {
            filtroPermisos = null;
            moduloSeleccionado = null;
            grupoSeleccionado = null;
            modulos = new ArrayList<String>();
            List<Funcionalidad> funcionalidades = fachada.getFuncionalidades(Estado.A);
            actualizarTablePermiso(rol, funcionalidades);
        } catch (Exception e) {
            Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion")+" " + e.getMessage());
        }
    }
    
    private void actualizarTablePermiso(Rol rol, List<Funcionalidad> funcionalidades) {
        filtroPermisos = null;
        permisos = new ArrayList<Permiso>();
        hmEstadoPermiso = new HashMap<Permiso, Boolean>();
        for (Iterator<Funcionalidad> it = funcionalidades.iterator(); it.hasNext();) {
            Funcionalidad funcionalidad = it.next();
            boolean selected = tieneAcceso(funcionalidad, rol);
            Permiso permiso = new Permiso(funcionalidad, selected);
            permisos.add(permiso);
            hmEstadoPermiso.put(permiso, selected);
        }
    }

    public void onChangeGrupoModulo() {
        try {
            if (grupoSeleccionado != null) {
                modulos = fachada.descripcionModulosPorGrupo(grupoSeleccionado);
                List<Funcionalidad> funcionalidades = fachada.funcionalidadesPorGrupo(grupoSeleccionado);
                actualizarTablePermiso(rolSeleccionado, funcionalidades);
            } else {
                cargarPanelAsignaPermiso(rolSeleccionado);
            }            
        } catch (Exception e) {
            RequestContext.getCurrentInstance().addCallbackParam("validationFailed", true);
            Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion")+" " + e.getMessage());
        }
    }

    public void onChangeFormulario() {
        try {
            if (moduloSeleccionado != null) {
                List<Funcionalidad> funcionalidades = fachada.funcionalidadesPorDescripcion(moduloSeleccionado);
                actualizarTablePermiso(rolSeleccionado, funcionalidades);
            } else {
                onChangeGrupoModulo();
            }
        } catch (Exception e) {
            RequestContext.getCurrentInstance().addCallbackParam("validationFailed", true);
            Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion")+" " + e.getMessage());
        }
    }

    public void guardarPermisos() {
        try {
            for (Iterator<Permiso> it = permisos.iterator(); it.hasNext();) {
                Permiso permiso = it.next();
                if (permiso.isSelected() != hmEstadoPermiso.get(permiso)) {
                    Funcionalidad funcionalidad = permiso.getFuncionalidad();
                    Modulo modulo = permiso.getFuncionalidad().getModulo();
                    if (permiso.isSelected()) {
                        RolPermiso rolPermiso = new RolPermiso();
                        rolPermiso.setFuncionalidad(funcionalidad);
                        rolPermiso.setModulo(modulo);
                        rolPermiso.setRol(rolSeleccionado);
                        fachada.guardarRolPermiso(rolPermiso, usuarioSesion.getUsuario());
                    } else {
                        RolPermiso rolPermiso = fachada.getRolPermiso(funcionalidad, rolSeleccionado);
                        if (rolPermiso != null) {
                            fachada.eliminarRolPermiso(rolPermiso, usuarioSesion.getUsuario());
                        }
                    }
                }
            }
        } catch (Exception e) {
            RequestContext.getCurrentInstance().addCallbackParam("validationFailed", true);
            Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion")+" " + e.getMessage());
        }
    }

    public void cambiarEstadoRol() {
        if (swRolActivo) {
            rolSeleccionado.setEstado(Estado.A);
        } else {
            rolSeleccionado.setEstado(Estado.I);
        }
    }

    public boolean tieneAcceso(Funcionalidad funcionalidad, Rol rol) {
        RolPermiso rolPermiso = fachada.getRolPermiso(funcionalidad, rol);
        boolean resp = (rolPermiso == null) ? resp = false : true;
        return resp;
    }

    public List<Rol> getRoles() {
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }

    public Rol getRolSeleccionado() {
        return rolSeleccionado;
    }

    public void setRolSeleccionado(Rol rolSeleccionado) {
        this.rolSeleccionado = rolSeleccionado;
    }

    public Rol getRolNuevo() {
        return rolNuevo;
    }

    public void setRolNuevo(Rol rolNuevo) {
        this.rolNuevo = rolNuevo;
    }

    public List<Permiso> getPermisos() {
        return permisos;
    }

    public List<Permiso> getFiltroPermisos() {
        return filtroPermisos;
    }

    public void setFiltroPermisos(List<Permiso> filtroPermisos) {
        this.filtroPermisos = filtroPermisos;
    }

    public boolean isSwRolActivo() {
        return swRolActivo;
    }

    public void setSwRolActivo(boolean swRolActivo) {
        this.swRolActivo = swRolActivo;
    }

    public String getMsjConfirmacion() {
        return msjConfirmacion;
    }

    public void setMsjConfirmacion(String msjConfirmacion) {
        this.msjConfirmacion = msjConfirmacion;
    }

    public UsuarioSesionBean getUsuarioSesion() {
        return usuarioSesion;
    }

    public void setUsuarioSesion(UsuarioSesionBean usuarioSesion) {
        this.usuarioSesion = usuarioSesion;
    }

    public List<String> getModulos() {
        return modulos;
    }

    public List<GrupoModulo> getGrupoModulos() {
        return Arrays.asList(GrupoModulo.values());
    }

    public String getModuloSeleccionado() {
        return moduloSeleccionado;
    }

    public void setModuloSeleccionado(String moduloSeleccionado) {
        this.moduloSeleccionado = moduloSeleccionado;
    }

    public GrupoModulo getGrupoSeleccionado() {
        return grupoSeleccionado;
    }

    public void setGrupoSeleccionado(GrupoModulo grupoSeleccionado) {
        this.grupoSeleccionado = grupoSeleccionado;
    }
}
