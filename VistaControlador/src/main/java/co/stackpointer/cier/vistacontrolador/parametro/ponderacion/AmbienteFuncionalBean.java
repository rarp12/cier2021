/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.parametro.ponderacion;

import co.stackpointer.cier.modelo.auditoria.RegistrarAuditoria;
import co.stackpointer.cier.modelo.entidad.calificacion.AmbientesFuncionales;
import co.stackpointer.cier.modelo.fachada.ParametrosFacadeLocal;
import co.stackpointer.cier.modelo.tipo.BitacoraTransaccion;
import co.stackpointer.cier.modelo.tipo.PrincipalOtro;
import co.stackpointer.cier.vista.Utilidades;
import co.stackpointer.cier.vistacontrolador.bean.UsuarioSesionBean;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.omnifaces.util.Messages;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author user
 */
@ManagedBean(name = "ambienteFuncionalBean")
@ViewScoped
public class AmbienteFuncionalBean {
    
    
    @EJB
    private ParametrosFacadeLocal facParam;
    
    @ManagedProperty(value = "#{usuarioSesion}")
    private UsuarioSesionBean usuarioSesion;
    
    private List<AmbientesFuncionales> lista;

    @PostConstruct
    public void inicializar() {
        this.consultar();
    }

    public void consultar() {
        try{
            actualizarLista();
        }finally{
            RegistrarAuditoria registrar = new RegistrarAuditoria();
            registrar.setFecha(new Date());
            registrar.setNombreMetodo(BitacoraTransaccion.CONSULTA_PRIORIZACION_UNIDADES_FUNCIONALES.name());
            registrar.setUsuario(usuarioSesion.getUsuario().getUsername());
            registrar.start();
        }
    }

    private void actualizarLista() {
        lista = facParam.consultarAmbientesFuncionales();
    }
    
    public List<AmbientesFuncionales> getLista() {
        return lista;
    }
    
    public void actualizar() {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("etiquetas", FacesContext.getCurrentInstance().getViewRoot().getLocale());
            int numPrincipales=0;
            int numSecundarios=0;
            for(AmbientesFuncionales af:lista){
                if(PrincipalOtro.P.name().equals(af.getTipo())){
                    numPrincipales++;
                }else if(PrincipalOtro.O.name().equals(af.getTipo())){
                    numSecundarios++;
                }
            }
            boolean error=false;
            if(numPrincipales!=2){
                error=true;
                Messages.addFlashGlobalError(bundle.getString("adm.parametros.servicios.principales"));
            }
            if(numSecundarios<1){
                error=true;
                Messages.addFlashGlobalError(bundle.getString("adm.parametros.servicios.secundarios"));
            }
            if(!error){
                for(AmbientesFuncionales af:lista){
                    facParam.modificarAmbientesFuncinoales(af);
                }
                this.actualizarLista();
            }
        } catch (Exception e) {
            Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("gerencial.bid.consulta.adminAmbito.upps")+" " + e.getMessage());
        }finally{
            RegistrarAuditoria registrar = new RegistrarAuditoria();
            registrar.setFecha(new Date());
            registrar.setNombreMetodo(BitacoraTransaccion.EDITAR_PRIORIZACION_UNIDADES_FUNCIONALES.name());
            registrar.setUsuario(usuarioSesion.getUsuario().getUsername());
            registrar.start();
        }
    }

     public List<PrincipalOtro> getListaPrincipalOtro() {
        return Arrays.asList(PrincipalOtro.values());
    }

    public void cancelarEdicion(RowEditEvent event) {
        this.consultar();
    }
    
    public UsuarioSesionBean getUsuarioSesion() {
        return usuarioSesion;
    }

    public void setUsuarioSesion(UsuarioSesionBean usuarioSesion) {
        this.usuarioSesion = usuarioSesion;
    }
    
}
