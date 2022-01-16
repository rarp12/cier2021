/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.gerenciales;

import co.stackpointer.cier.modelo.entidad.BID.AmbitosBID;
import co.stackpointer.cier.modelo.entidad.BID.VariablesBID;
import co.stackpointer.cier.modelo.fachada.BIDFacadeLocal;
import co.stackpointer.cier.vista.Utilidades;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.omnifaces.util.Messages;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author user
 */
@ManagedBean(name = "adminVariablesBIDBean")
@ViewScoped
public class adminVariablesBIDBean implements Serializable {

    @EJB
    private BIDFacadeLocal fachadaBID;
    private List<VariablesBID> lista;
    
    @PostConstruct
    public void inicializar() {
        this.consultar();
    }

    public void consultar() {
        lista = fachadaBID.obtenerVariablesEditablesBID();
    }

    public List<VariablesBID> getLista() {
        return lista;
    }


    public void actualizar() {
        try {
            //Se valida que de 100 cada ámbito
            HashMap<AmbitosBID,Double> sumaPonderaAmb=new HashMap<AmbitosBID, Double>();
            AmbitosBID amb=null;
            for(VariablesBID v:lista){
                amb=v.getAmbitoBID();
                sumaPonderaAmb.put(amb,(sumaPonderaAmb.containsKey(amb)?sumaPonderaAmb.get(amb):0)+v.getPonderacion());
            }
            boolean error=false;
            for (Map.Entry<AmbitosBID, Double> e: sumaPonderaAmb.entrySet()) {
                if(!new Double(100).equals(e.getValue())){
                    error=true;
                    Messages.addFlashGlobalError(Utilidades.obtenerMensaje("gerencial.bid.consulta.adminAmbito.ponderacionVariableAmbito.variablesAmbito",Utilidades.obtenerMensaje("gerencial.bid.ambitos."+e.getKey().getCodigo())));
                }
            }
            
            //Si los datos son correctos se guardan 
            if(!error){
                for(VariablesBID v:lista){
                    fachadaBID.actualizarVariableBID(v);
                    this.consultar();
                }
            }
            
        } catch (Exception e) {
            Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("gerencial.bid.consulta.adminAmbito.upps")+" " + e.getMessage());
        }
    }

    public void cancelarEdicion(RowEditEvent event) {
        this.consultar();
    }
    
  
}
