/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.bean;

import co.stackpointer.cier.modelo.auditoria.RegistrarAuditoria;
import co.stackpointer.cier.modelo.entidad.acceso.Usuario;
import co.stackpointer.cier.modelo.entidad.sistema.Idioma;
import co.stackpointer.cier.modelo.entidad.sistema.Tenant;
import co.stackpointer.cier.modelo.fachada.AccesoFacadeLocal;
import co.stackpointer.cier.modelo.fachada.EmailFacadeLocal;
import co.stackpointer.cier.modelo.fachada.PaisFacadeLocal;
import co.stackpointer.cier.modelo.fachada.ParametrosFacadeLocal;
import co.stackpointer.cier.modelo.tipo.ParamSistema;
import co.stackpointer.cier.modelo.utilidad.UtilCadena;
import co.stackpointer.cier.modelo.utilidad.UtilEncriptar;
import co.stackpointer.cier.modelo.utilidad.UtilOut;
import co.stackpointer.cier.modelo.utilidad.UtilProperties;
import co.stackpointer.cier.vista.Utilidades;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

/**
 *
 * @author stackpointer
 */
@ManagedBean(name = "login")
@SessionScoped
public class LoginBean implements Serializable {
    
    @EJB
    PaisFacadeLocal fachada;   
    
    @EJB
    ParametrosFacadeLocal facParamfacParam;
    
    @EJB
    EmailFacadeLocal emailFacade;
    
    
    private String usuario;
    private String passwd;
    private boolean swLogueado;
    private List<Tenant> paises;
    private Tenant pais;
    private Locale locale;
    private String email;   
    private boolean internetConnectionGmap;
    private String username;

    public LoginBean() {
    }

    @PostConstruct
    public void init() {
        setPaises(fachada.getPaises());
        paisLocal();        
    }

    public void paisLocal() {
        pais = paises.get(0);
        cambiarIdioma(pais);
    }  
     public long getDBMotor() {         
         return  Long.parseLong(facParamfacParam.consultarParametro(ParamSistema.BASE_DATOS.name()).getValor());
     }

    public String validar() {
        //En caso de error ir al validar
        String outcome = "login";
        //JAAS
        try {            
            setUsuario(UtilCadena.toUpperCase(usuario));
            int TENANT_ID = pais.getId().intValue();
            String userJaas = TENANT_ID + "%" + usuario;
            email = fachada.getEmailAdministrador(TENANT_ID);
            Faces.login(userJaas, passwd);
            swLogueado = true;  
            if (fachada.reestablecePassword(TENANT_ID, usuario)) {
               outcome = "/resetPassword.xhtml?faces-redirect=true";
            } else {
                RegistrarAuditoria registrar = new RegistrarAuditoria();
                registrar.setFecha(new Date());
                registrar.setNombreMetodo("login");
                registrar.setUsuario(usuario);
                registrar.start();
               outcome = "/index.xhtml?faces-redirect=true";
            }
            UtilProperties utilProperties= new UtilProperties();
            utilProperties.loadQueries(getDBMotor(),pais.getIdioma());           
        } catch (Exception e) {
            swLogueado = false;
            try {
                //System.out.println("Error logueo: "+e);
                Messages.addFlashGlobalError(Utilidades.obtenerMensaje("aplicacion.login.error.credenciales",email));
            } catch (Exception e2) {
                //System.out.println("Error logueo: "+e);
                Messages.addFlashGlobalError(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"));
                //System.out.println("Excepcion al loguear el usuario: "+e);
            }
        }
        return outcome;
    }

    public void logout() throws ServletException {    
        try {           
            Faces.invalidateSession();
            Faces.logout();
            RegistrarAuditoria registrar = new RegistrarAuditoria();
            registrar.setFecha(new Date());
            registrar.setNombreMetodo("logout");
            registrar.setUsuario(usuario);
            registrar.start();
            Faces.redirect(Faces.getRequestContextPath() + "/login/login.xhtml");
        } catch (IOException ex) {
            UtilOut.println(ex);
        }
    }

    public void goInicio() throws ServletException {       
        try {
            Faces.redirect(Faces.getRequestContextPath() + "/index.xhtml");
        } catch (IOException e) {
            UtilOut.println(e);
            logout();
        }
    }
    
    public void goResetPassword() throws ServletException {       
        try {
            Faces.redirect(Faces.getRequestContextPath() + "/resetPassword.xhtml");
        } catch (IOException e) {
            UtilOut.println(e);
            logout();
        }
    }

    private void cambiarIdioma(Tenant pais) {
        locale = new Locale(pais.getIdioma().getCodigoIso());
        Faces.setLocale(locale);
    }
    
    public void cambiarIdioma(ValueChangeEvent e) {
        Tenant paisActual = (Tenant)e.getNewValue();        
        locale = new Locale(paisActual.getIdioma().getCodigoIso());
        Faces.setLocale(locale);        
    }
    
    public void idiomaPreferencial(Usuario usuario) {
        if (usuario.getIdioma() != null) {
            locale = new Locale(usuario.getIdioma().getCodigoIso());
            Faces.setLocale(locale);             
        }else{
            Idioma idioma = pais.getIdioma();
            usuario.setIdioma(idioma);
            cambiarIdioma(pais);
        }
    }
    
    public void recordarPassword() {
        try {
            int TENANT_ID = pais.getId().intValue();
            String correo = fachada.getEmailUsuario(TENANT_ID, username.toUpperCase().trim());
            if (correo != null) {
                emailFacade.sendEmail(fachada.getEmailConfig(), correo,
                        Utilidades.obtenerMensaje("aplicacion.login.lbl.recordar.password.correo.asunto"),
                        Utilidades.obtenerMensaje("aplicacion.login.lbl.recordar.password.correo.mensaje",
                        fachada.generarPasswordTemporal(TENANT_ID, username.toUpperCase().trim())));
                Messages.addFlashGlobalInfo(Utilidades.obtenerMensaje("aplicacion.login.lbl.recordar.password.exitoso"));
            } else {
                Messages.addFlashGlobalWarn(Utilidades.obtenerMensaje("aplicacion.login.lbl.recordar.password.usuario.noExiste"));
            }
            username = "";
        } catch (Exception e) {            
            Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"));
        }

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

    public boolean isSwLogueado() {
        return swLogueado;
    }

    public void setSwLogueado(boolean swLogueado) {
        this.swLogueado = swLogueado;
    }

    public List<Tenant> getPaises() {
        return paises;
    }

    public void setPaises(List<Tenant> paises) {
        this.paises = paises;
    }

    public Tenant getPais() {
        return pais;
    }

    public void setPais(Tenant pais) {
        this.pais = pais;
        cambiarIdioma(pais);
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }    

    public boolean isInternetConnectionGmap() {
        return internetConnectionGmap;
    }

    public void setInternetConnectionGmap(boolean internetConnectionGmap) {
        this.internetConnectionGmap = internetConnectionGmap;
    }

   public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
}
