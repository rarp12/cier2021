/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.bean;

import co.stackpointer.cier.modelo.entidad.acceso.Funcionalidad;
import co.stackpointer.cier.modelo.entidad.acceso.Modulo;
import co.stackpointer.cier.modelo.entidad.acceso.Usuario;
import co.stackpointer.cier.modelo.fachada.AccesoFacadeLocal;
import co.stackpointer.cier.modelo.entidad.sistema.Idioma;
import co.stackpointer.cier.modelo.entidad.sistema.Longitudes;
import co.stackpointer.cier.modelo.fachada.LongitudesFacadeLocal;
import co.stackpointer.cier.modelo.fachada.PaisFacadeLocal;
import co.stackpointer.cier.modelo.utilidad.UtilOut;
import co.stackpointer.cier.vista.Utilidades;
import co.stackpointer.cier.vistacontrolador.consulta.generarIndicadores.NivelGeneracionBean;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import org.omnifaces.util.Messages;

/**
 *
 * @author user
 */
@ManagedBean(name = "usuarioSesion")
@SessionScoped
public class UsuarioSesionBean implements Serializable {

    @EJB
    AccesoFacadeLocal fachada;
    @EJB
    PaisFacadeLocal fachadaPais;
    private LoginBean login;
    private HashMap<String, Boolean> hmPermisos; //permisos para Modulos
    private HashMap<String, HashMap<String, Boolean>> hmSubPermisos; //permisos para Funcionalidades de cada Modulo
    private Usuario usuario;
    private String menuActual = "";
    private String estiloDigitacion = "display: none!important";
    private String estiloConsultas = "display: none!important";
    private String estiloGerencialBID = "display: none!important";
    private String estiloAdminsitracion = "display: none!important";
    private List<Idioma> idiomas;
    private List<Longitudes> longitudes;
    @EJB
    LongitudesFacadeLocal fachadaLongitudes;
    private final Map<String, Longitudes> longitudesPorOmnifaces = new HashMap<String, Longitudes>();
    private final String subfijoTruncado = "...";

    public UsuarioSesionBean() {
    }

    @PostConstruct
    public void init() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            login = (LoginBean) context.getELContext().getELResolver().getValue(context.getELContext(), null, "login");
            usuario = fachada.getUsuarioPorUsername(login.getUsuario());
            construirMapaDePermisos(login.getUsuario());
            login.idiomaPreferencial(usuario);            
            setIdiomas(fachadaPais.getIdiomas());           
            this.consultarLongitudes();
            this.procesarLongitudesPorOmnifaces();
        } catch (Exception e) {
            UtilOut.println(e);
        }
    }

    private void construirMapaDePermisos(String usuario) {
        hmPermisos = new HashMap<String, Boolean>();
        hmSubPermisos = new HashMap<String, HashMap<String, Boolean>>();

        hmPermisos.put("home", Boolean.TRUE);
        hmPermisos.put("salir", Boolean.TRUE);

        List<Modulo> modulos = fachada.modulosPorUsuario(usuario);
        for (Iterator<Modulo> it = modulos.iterator(); it.hasNext();) {
            Modulo modulo = it.next();
            hmPermisos.put(modulo.getCodigo(), Boolean.TRUE);
            HashMap<String, Boolean> hmFuncionalidades = new HashMap<String, Boolean>();
            List<Funcionalidad> funcionalidades = fachada.funcionalidadPORmoduloYusuario(modulo.getCodigo(), usuario);
            for (Iterator<Funcionalidad> it1 = funcionalidades.iterator(); it1.hasNext();) {
                Funcionalidad funcionalidad = it1.next();
                hmFuncionalidades.put(funcionalidad.getCodigo(), Boolean.TRUE);
            }
            hmSubPermisos.put(modulo.getCodigo(), hmFuncionalidades);
        }

    }
    
    public void cambiarIdiomaPreferencial(ValueChangeEvent event) {
        try {
            Idioma i = (Idioma) event.getNewValue();
            if (i!=null && !i.getId().equals(usuario.getIdioma().getId())) {
                usuario.setIdioma(i);
                login.idiomaPreferencial(usuario);
                fachada.actualizarUsuario(usuario.getUsername(), usuario);
                if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("nivelGeneracion")){
                    ((NivelGeneracionBean)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("nivelGeneracion")).cargarConfiguracionDPA();
                }
                //Reload page
                FacesContext ctx = FacesContext.getCurrentInstance();
                ExternalContext extContext = ctx.getExternalContext();
                String url = extContext.encodeActionURL(ctx.getApplication().getViewHandler().getResourceURL(ctx, FacesContext.getCurrentInstance().getViewRoot().getViewId()));
                extContext.redirect(url);
            }            
        } catch (Exception ex) {
            UtilOut.println(ex);
            Messages.addFlashGlobalError(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"));
        }
    }
    
    public HashMap<String, HashMap<String, Boolean>> getHmSubPermisos() {
        return hmSubPermisos;
    }

    public HashMap<String, Boolean> getHmPermisos() {
        return hmPermisos;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getMenuActual() {
        return this.menuActual;
    }

    public void setMenuActual(String menuActual) {
        this.menuActual = menuActual;
    }
    
    public List<Idioma> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<Idioma> idiomas) {
        this.idiomas = idiomas;
    }
    public String cambiarMenuActual() {

        Map<String, String> params;
        params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String action = params.get("menuActual");
        this.setMenuActual(action);
        System.out.println("Menu Actual2" + action);
        return "";
    }

    public String inicio() {

        Map<String, String> params;
        params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String action = params.get("menuActual");
        this.setMenuActual(action);
        System.out.println("Menu Actual2" + action);
        return "inicio";
    }

    public String getEstiloDigitacion() {

        if (this.menuActual.equals("DIGITACION")) {
            return "";
        } else {
            return "display: none!important";
        }

    }

    public String cambiarInicio() {
        Map<String, String> params;
        params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String action = params.get("menuActual");
        this.setMenuActual(action);
        System.out.println("Menu Actual2" + action);
        return "inicio";
    }
    
    private void consultarLongitudes() {
        this.longitudes = this.fachadaLongitudes.consultarLongitudes();
    }

    public void setEstiloDigitacion(String estilo) {
        this.estiloDigitacion = estilo;

    }

    public String getEstiloConsulta() {

        if (this.menuActual.equals("CONSULTA")) {
            return "";
        } else {
            return "display: none!important";
        }

    }

    public void setEstiloConsulta(String estilo) {
        this.estiloConsultas = estilo;

    }

    public String getEstiloGerencialBID() {

        if (this.menuActual.equals("GERENCIAL")) {
            return "";
        } else {
            return "display: none!important";
        }

    }

    public void setEstiloGerencialBID(String estilo) {
        this.estiloGerencialBID = estilo;

    }

    public String getEstiloAdminsitracion() {

        if (this.menuActual.equals("ADMINISTRACION")) {
            return "";
        } else {
            return "display: none!important";
        }

    }

    public void setEstiloAdminsitracion(String estilo) {
        this.estiloAdminsitracion = estilo;

    }

    public String navDigitacion() {
        return "digitacionMenuPrincipal";
    }

    public List<Longitudes> getLongitudes() {
        return longitudes;
    }

    public void setLongitudes(List<Longitudes> longitudes) {
        this.longitudes = longitudes;
    }
    
    private void procesarLongitudesPorOmnifaces() {
        if (this.longitudes != null) {
            for (Longitudes longitud : this.longitudes) {
                if (longitud.getAplicaOmnifaces() != null 
                        && longitud.getAplicaOmnifaces()) {
                    this.longitudesPorOmnifaces.put(longitud.getDescripcion(), longitud);
                }
            }
        }
    }

    public Map<String, Longitudes> getLongitudesPorOmnifaces() {
        return longitudesPorOmnifaces;
    }
    
    public String labelItemSinOmnifaces(Object item, String key) {
        return this.processLabelTruncate(item.toString(), key);
    }
    
    public String labelItemSinOmnifaces(String item, String key) {
        return this.processLabelTruncate(item, key);
    }
    
    public String processLabelTruncate(String label, String key) {
        if (this.longitudesPorOmnifaces.containsKey(key)) {
            if (label.length() > this.longitudesPorOmnifaces.get(key).getLongitud().intValue()) {
                return label.substring(0, this.longitudesPorOmnifaces.get(key).getLongitud().intValue()) + this.subfijoTruncado;
            }
        }
        return label;
    }

    public String getSubfijoTruncado() {
        return subfijoTruncado;
    }
    
    public int longitudItemOmnifaces(String key) {
        if (this.longitudesPorOmnifaces.containsKey(key)) {
            return this.longitudesPorOmnifaces.get(key).getLongitud().intValue();
        }
        return 500;
    }
}
