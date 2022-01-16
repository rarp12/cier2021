/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.prueba;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author StackPointer
 */
@ManagedBean(name="sessionUtil")
@SessionScoped
public class SessionUtil {
    private String usuario;
    private String passwd;
    public String doLogin(){    
        return "/prueba/outsideTemplateClient.xhtml?faces-redirect=true";
    }
     public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }    
    
}
