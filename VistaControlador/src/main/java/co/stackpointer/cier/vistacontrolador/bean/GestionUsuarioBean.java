/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.bean;

import co.stackpointer.cier.modelo.auditoria.RegistrarAuditoria;
import co.stackpointer.cier.modelo.entidad.acceso.Rol;
import co.stackpointer.cier.modelo.entidad.acceso.Usuario;
import co.stackpointer.cier.modelo.entidad.acceso.UsuarioRol;
import co.stackpointer.cier.modelo.entidad.dpa.Nivel;
import co.stackpointer.cier.modelo.fachada.AccesoFacadeLocal;
import co.stackpointer.cier.modelo.tipo.BitacoraTransaccion;
import co.stackpointer.cier.modelo.tipo.Estado;
import co.stackpointer.cier.modelo.utilidad.UtilCadena;
import co.stackpointer.cier.modelo.utilidad.UtilEncriptar;
import co.stackpointer.cier.vista.Utilidades;
import static co.stackpointer.cier.vista.Utilidades.mostrarMensajeError;
import co.stackpointer.cier.vistacontrolador.consulta.ConsultaBase;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ViewScoped;
import org.omnifaces.util.Messages;
import org.primefaces.model.DualListModel;

/**
 *
 * @author StackPointer
 */
@ManagedBean(name = "gestionUsuario")
@ViewScoped
public class GestionUsuarioBean extends ConsultaBase {

    @EJB
    AccesoFacadeLocal fachada;
    private List<Usuario> usuarios;
    private Usuario usuarioSeleccionado;
    private DualListModel<Rol> rolesDualListModel;
    private HashMap<Rol, Boolean> hmEstadoRol;
    private boolean swAsignaRol;
    private boolean swAsignaNivelConsulta;
    private Usuario usuarioNuevo;
    private boolean swReestablecerPassword;
    private String newPasswd;
    private boolean swCuentaActiva;

    public GestionUsuarioBean() {
    }

    @PostConstruct
    public void init() {
        try {
            setUsuarios(fachada.getUsuarios());
            usuarioNuevo = new Usuario();
        } finally {
            RegistrarAuditoria registrar = new RegistrarAuditoria();
            registrar.setFecha(new Date());
            registrar.setNombreMetodo(BitacoraTransaccion.CONSULTAR_USUARIO.name());
            registrar.setUsuario(usuarioSesion.getUsuario().getUsername());
            registrar.start();
        }
    }

    public void cargarPanelAsignaRol() {
        swAsignaRol = true;
        hmEstadoRol = new HashMap<Rol, Boolean>();
        List<Rol> rolesDisponibles = fachada.rolesNoAsignados(usuarioSeleccionado);
        List<Rol> rolesAsignados = fachada.rolesAsignados(usuarioSeleccionado);
        for (Rol rol : rolesAsignados) {
            hmEstadoRol.put(rol, true);
        }
        for (Rol rol : rolesDisponibles) {
            hmEstadoRol.put(rol, false);
        }
        rolesDualListModel = new DualListModel<Rol>(rolesDisponibles, rolesAsignados);
    }

    public void cargarPanelAsignaNivConsulta() {
        swAsignaNivelConsulta = true;
        super.initActual();
    }

    public void asignaRoles() {
        try {
            for (Rol rol : rolesDualListModel.getSource()) {
                if (hmEstadoRol.get(rol)) {
                    UsuarioRol ur = fachada.getUsuarioRol(usuarioSeleccionado, rol);
                    if (ur != null) {
                        ur.setEstado(Estado.I);
                        fachada.actualizarUsuarioRol(ur, getUsuarioSesion().getUsuario());
                    }
                }
            }
            for (Rol rol : rolesDualListModel.getTarget()) {
                if (!hmEstadoRol.get(rol)) {
                    UsuarioRol ur = fachada.getUsuarioRol(usuarioSeleccionado, rol);
                    if (ur != null) {
                        ur.setEstado(Estado.A);
                        fachada.actualizarUsuarioRol(ur, getUsuarioSesion().getUsuario());
                    } else {
                        UsuarioRol nuevo_ur = new UsuarioRol();
                        nuevo_ur.setRol(rol);
                        nuevo_ur.setUsuario(usuarioSeleccionado);
                        fachada.guardarUsuarioRol(getUsuarioSesion().getUsuario().getUsername(), nuevo_ur);
                    }
                }
            }
        } catch (Exception e) {
            mostrarMensajeError(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion") + " ", e.getMessage());
        } finally {
            swAsignaRol = false;
        }
    }    

    public void actualizarNivelConsulta() {
        Nivel nivelConsulta;
        nivelConsulta = (Nivel) this.getUltimoNivelSeleccionado();
        if (nivelConsulta != null) {
            usuarioSeleccionado.setNivelDpa(nivelConsulta.getConfiguracion());
            usuarioSeleccionado.setNivelEspecifico(nivelConsulta);
            editarUsuario();
        }
        swAsignaNivelConsulta = false;
    }

    public void swPaneles(boolean value) {
        setSwAsignaRol(value);
        setSwAsignaNivelConsulta(value);
    }

    public void instanciarNuevoUsuario() {
        usuarioNuevo = new Usuario();
    }

    public void guardarUsuario() {
        try {
            String cifrado = UtilEncriptar.getStringMessageDigest(usuarioNuevo.getUsername(), UtilEncriptar.SHA256);
            usuarioNuevo.setUsername(UtilCadena.toUpperCase(usuarioNuevo.getUsername()));
            usuarioNuevo.setPassword(cifrado);
            usuarioNuevo.setRestablecer(1);
            setUsuarioNuevo(fachada.guardarUsuario(getUsuarioSesion().getUsuario().getUsername(), usuarioNuevo));
            usuarios.add(usuarioNuevo);
            //Envio de correo.
        } catch (Exception e) {
            e.printStackTrace();
            Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"));   
        }
    }

    public void editarUsuario() {
        try {
            cambiarEstadoUsuario();
            fachada.actualizarUsuario(getUsuarioSesion().getUsuario().getUsername(), usuarioSeleccionado);
        } catch (Exception e) {
            Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion") + " " + e.getMessage());
        }
    }

    public void reestablecerPassword() {
        if (!UtilCadena.isNullOVacio(newPasswd)) {
            newPasswd = UtilEncriptar.getStringMessageDigest(newPasswd, UtilEncriptar.SHA256);
            usuarioSeleccionado.setPassword(newPasswd);
            editarUsuario();
        }
        swReestablecerPassword = false;
        
    }

    public void cambiarEstadoUsuario() {
        if (swCuentaActiva) {
            usuarioSeleccionado.setEstado(Estado.A);
        } else {
            usuarioSeleccionado.setEstado(Estado.I);
        }       
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public Usuario getUsuarioSeleccionado() {
        return usuarioSeleccionado;
    }

    public void setUsuarioSeleccionado(Usuario usuarioSeleccionado) {
        this.usuarioSeleccionado = usuarioSeleccionado;
    }

    public DualListModel<Rol> getRolesDualListModel() {
        return rolesDualListModel;
    }

    public void setRolesDualListModel(DualListModel<Rol> rolesDualListModel) {
        this.rolesDualListModel = rolesDualListModel;
    }

    public boolean isSwAsignaRol() {
        return swAsignaRol;
    }

    public void setSwAsignaRol(boolean swAsignaRol) {
        this.swAsignaRol = swAsignaRol;
    }

    public boolean isSwAsignaNivelConsulta() {
        return swAsignaNivelConsulta;
    }

    public void setSwAsignaNivelConsulta(boolean swAsignaNivelConsulta) {
        this.swAsignaNivelConsulta = swAsignaNivelConsulta;
    }

    public Usuario getUsuarioNuevo() {
        return usuarioNuevo;
    }

    public void setUsuarioNuevo(Usuario usuarioNuevo) {
        this.usuarioNuevo = usuarioNuevo;
    }

    public String getNewPasswd() {
        return newPasswd;
    }

    public void setNewPasswd(String newPasswd) {
        this.newPasswd = newPasswd;
    }

    public boolean isSwReestablecerPassword() {
        return swReestablecerPassword;
    }

    public void setSwReestablecerPassword(boolean swReestablecerPassword) {
        this.swReestablecerPassword = swReestablecerPassword;
    }

    public boolean isSwCuentaActiva() {
        return swCuentaActiva;
    }

    public void setSwCuentaActiva(boolean swCuentaActiva) {
        this.swCuentaActiva = swCuentaActiva;
    }

    @Override
    public void resetearFiltros() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void cambioPeriodo() {
        super.initHistoricos(periodo);
        super.cargarNivel1();
    }
}
