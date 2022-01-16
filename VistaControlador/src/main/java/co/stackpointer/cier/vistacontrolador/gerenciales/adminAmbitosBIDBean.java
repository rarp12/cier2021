/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.gerenciales;

import co.stackpointer.cier.modelo.entidad.BID.AmbitosBID;
import co.stackpointer.cier.modelo.fachada.BIDFacadeLocal;
import co.stackpointer.cier.modelo.utilidad.UtilOut;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.omnifaces.util.Messages;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author user
 */
@ManagedBean(name = "adminAmbitosBIDBean")
@ViewScoped
public class adminAmbitosBIDBean implements Serializable {

    @EJB
    private BIDFacadeLocal fachadaBID;
    private List<AmbitosBID> lista;
    Double totalPonderacion =0.0;
    private ResourceBundle bundleMensajes = ResourceBundle.getBundle("etiquetas", FacesContext.getCurrentInstance().getViewRoot().getLocale());

    @PostConstruct
    public void inicializar() {
        this.consultar();
    }

    public void consultar() {
        lista = fachadaBID.obtenerAmbitosBID();
    }
    
    

    public List<AmbitosBID> getLista() {
        return lista;
    }

    public void guardar() {
        try {
            if(validarPonderacion()){
                for(AmbitosBID ambito:lista){
                    fachadaBID.actualizarAmbitoBID(ambito);
                    this.consultar();
                }
            }
            
        } catch (Exception e) {
            UtilOut.println(e);
            Messages.addFlashGlobalFatal(bundleMensajes.getString("gerencial.bid.consulta.adminAmbito.upps")+" " + e.getMessage());
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
            Messages.addWarn( bundleMensajes.getString("gerencial.bid.consulta.adminAmbito.errorValidacion"),bundleMensajes.getString("gerencial.bid.consulta.adminAmbito.validacionPonderacion")+": "+ total);
            return false;
        }else{
            if(total>100.0)
            {
                Messages.addWarn( bundleMensajes.getString("gerencial.bid.consulta.adminAmbito.errorValidacion"),bundleMensajes.getString("gerencial.bid.consulta.adminAmbito.validacionPonderacion")+": "+total);
                return false;
            }
        }
        return true;
    }

    public Double getTotalPonderacion() {
          totalPonderacion =0.0;
          for(AmbitosBID a: lista)
          {             
               totalPonderacion=totalPonderacion+a.getPonderacion();
          }  
        return totalPonderacion;
    }

    public void setTotalPonderacion(Double totalPonderacion) {
        this.totalPonderacion = totalPonderacion;
    }
    
    
}
