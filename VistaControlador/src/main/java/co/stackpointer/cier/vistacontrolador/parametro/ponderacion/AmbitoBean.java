/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.parametro.ponderacion;

import co.stackpointer.cier.modelo.auditoria.RegistrarAuditoria;
import co.stackpointer.cier.modelo.entidad.calificacion.VariableAmbito;
import co.stackpointer.cier.modelo.entidad.instrumento.Ambito;
import co.stackpointer.cier.modelo.fachada.ParametrosFacadeLocal;
import co.stackpointer.cier.modelo.tipo.BitacoraTransaccion;
import co.stackpointer.cier.modelo.utilidad.UtilOut;
import co.stackpointer.cier.vista.Utilidades;
import co.stackpointer.cier.vistacontrolador.bean.UsuarioSesionBean;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.omnifaces.util.Messages;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author user
 */
@ManagedBean(name = "ambitoBean")
@ViewScoped
public class AmbitoBean implements Serializable {

    @EJB
    private ParametrosFacadeLocal facParam;
    
    @ManagedProperty(value = "#{usuarioSesion}")
    private UsuarioSesionBean usuarioSesion;
    
    private List<Ambito> lista;
    Double totalPonderacion =0.0;

    @PostConstruct
    public void inicializar() {
        this.consultar();
    }

    public void consultar() {
        try {
            actualizarLista();
        } finally {
            RegistrarAuditoria registrar = new RegistrarAuditoria();
            registrar.setFecha(new Date());
            registrar.setNombreMetodo(BitacoraTransaccion.CONSULTA_CALIFICACION_AMBITO.name());
            registrar.setUsuario(usuarioSesion.getUsuario().getUsername());
            registrar.start();
        }
    }
    
    private void actualizarLista(){
        lista = facParam.consultarAmbitos();
    }

    public List<Ambito> getLista() {
        return lista;
    }

    public void guardar() {
        try {
            if(validarPonderacion()){
                for(Ambito ambito:lista){
                    facParam.modificarAmbito(ambito);
                    this.actualizarLista();
                }
            }            
        } catch (Exception e) {
            UtilOut.println(e);
            Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("gerencial.bid.consulta.adminAmbito.upps")+" " + e.getMessage());
        }finally{
            RegistrarAuditoria registrar = new RegistrarAuditoria();
            registrar.setFecha(new Date());
            registrar.setNombreMetodo(BitacoraTransaccion.EDITAR_CALIFICACION_AMBITO.name());
            registrar.setUsuario(usuarioSesion.getUsuario().getUsername());
            registrar.start();
        }
    }

    public void cancelarEdicion(RowEditEvent event) {
        this.consultar();
    }
    
    public boolean validarPonderacion()
    {          
        Double total=this.getTotalPonderacion();
        if(total<100.0)
        {           
            Messages.addWarn( Utilidades.obtenerMensaje("gerencial.bid.consulta.adminAmbito.errorValidacion"),Utilidades.obtenerMensaje("gerencial.bid.consulta.adminAmbito.validacionPonderacion")+": "+ total);
            return false;
        }else{
            if(total>100.0)
            {
                Messages.addWarn( Utilidades.obtenerMensaje("gerencial.bid.consulta.adminAmbito.errorValidacion"),Utilidades.obtenerMensaje("gerencial.bid.consulta.adminAmbito.validacionPonderacion2")+": "+total);
                return false;
            }
        }
        return true;
    }

    public Double getTotalPonderacion() {
          totalPonderacion =0.0;
          for(Ambito ambito: lista)
          {             
               totalPonderacion=totalPonderacion+ambito.getPonderacion();
          }  
        return totalPonderacion;
    }

    public void setTotalPonderacion(Double totalPonderacion) {
        this.totalPonderacion = totalPonderacion;
    }
    
    public UsuarioSesionBean getUsuarioSesion() {
        return usuarioSesion;
    }

    public void setUsuarioSesion(UsuarioSesionBean usuarioSesion) {
        this.usuarioSesion = usuarioSesion;
    }
    
}
