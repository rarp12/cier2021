/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.bean;

import co.stackpointer.cier.modelo.entidad.acceso.Usuario;
import co.stackpointer.cier.modelo.fachada.AccesoFacadeLocal;
import co.stackpointer.cier.modelo.utilidad.UtilCadena;
import co.stackpointer.cier.modelo.utilidad.UtilEncriptar;
import co.stackpointer.cier.vista.Utilidades;
import javax.faces.bean.ManagedBean;
import javax.ejb.EJB;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.omnifaces.util.Messages;

/**
 *
 * @author david paz
 */
@ManagedBean(name = "gestionPassword")
@RequestScoped
public class GestionPasswordBean {

    @EJB
    AccesoFacadeLocal fachada;
    private Usuario usuario;
    private String newPasswd;
    
    public GestionPasswordBean() {
    }
   
    private void editarUsuario() {
        try {
            fachada.actualizarUsuario(usuario.getUsername(), usuario);
        } catch (Exception e) {
            Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion") + " " + e.getMessage());
        }
    }

    public void cambiarPassword(Usuario usuario) {
        //initUsuario();
        this.usuario=usuario;
        if (!UtilCadena.isNullOVacio(newPasswd)) {
            newPasswd = UtilEncriptar.getStringMessageDigest(newPasswd, UtilEncriptar.SHA256);
            this.usuario.setPassword(newPasswd);
            editarUsuario();
        }
        Messages.addFlashGlobalInfo(Utilidades.obtenerMensaje("aplicacion.plantilla.msg.cambioCorrecto"));
    }
    
    public String resetPassword(Usuario usuario) {
        //initUsuario();
        this.usuario=usuario;
        if (!UtilCadena.isNullOVacio(newPasswd)) {
            newPasswd = UtilEncriptar.getStringMessageDigest(newPasswd, UtilEncriptar.SHA256);
            this.usuario.setPassword(newPasswd);
            this.usuario.setRestablecer(0);
            editarUsuario();
        }
        return "/index.xhtml?faces-redirect=true";
    }
    
    public String getNewPasswd() {
        return newPasswd;
    }

    public void setNewPasswd(String newPasswd) {
        this.newPasswd = newPasswd;
    }
    
    }
