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
import co.stackpointer.cier.vista.Utilidades;
import co.stackpointer.cier.vistacontrolador.bean.UsuarioSesionBean;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
@ManagedBean(name = "variableAmbitoBean")
@ViewScoped
public class VariableAmbitoBean implements Serializable {

    @EJB
    private ParametrosFacadeLocal facParam;
    
    @ManagedProperty(value = "#{usuarioSesion}")
    private UsuarioSesionBean usuarioSesion;
    
    private List<VariableAmbito> lista;

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
            registrar.setNombreMetodo(BitacoraTransaccion.CONSULTA_VARIABLE_AMBITO.name());
            registrar.setUsuario(usuarioSesion.getUsuario().getUsername());
            registrar.start();
        }
    }
    
    private void actualizarLista() {
        lista = facParam.consultarVariablesAmbitos();
    }

    public List<VariableAmbito> getLista() {
        return lista;
    }


    public void actualizar() {
        try {
            //Se valida que de 100 cada ámbito
            HashMap<Ambito,Double> sumaPonderaAmb=new HashMap<Ambito, Double>();
            Ambito amb=null;
            for(VariableAmbito variableAmbito:lista){
                amb=variableAmbito.getAmbito();
                sumaPonderaAmb.put(amb,(sumaPonderaAmb.containsKey(amb)?sumaPonderaAmb.get(amb):0)+variableAmbito.getPonderacion());
            }
            boolean error=false;
            for (Map.Entry<Ambito, Double> e: sumaPonderaAmb.entrySet()) {
                if(!new Double(100).equals(e.getValue())){
                    error=true;
                    Messages.addFlashGlobalError(Utilidades.obtenerMensaje("gerencial.bid.consulta.adminAmbito.ponderacionVariableAmbito.variablesAmbito", Utilidades.obtenerMensaje(e.getKey().getEtiqueta())));
                }
            }
            
            //Si los datos son correctos se guardan
            if(!error){
                for(VariableAmbito variableAmbito:lista){
                    facParam.modificarVariableAmbito(variableAmbito);
                    this.actualizarLista();
                }
            }
            
        } catch (Exception e) {
            Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("gerencial.bid.consulta.adminAmbito.upps")+" " + e.getMessage());
        }finally{
            RegistrarAuditoria registrar = new RegistrarAuditoria();
            registrar.setFecha(new Date());
            registrar.setNombreMetodo(BitacoraTransaccion.EDITAR_VARIABLE_AMBITO.name());
            registrar.setUsuario(usuarioSesion.getUsuario().getUsername());
            registrar.start();
        }
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
