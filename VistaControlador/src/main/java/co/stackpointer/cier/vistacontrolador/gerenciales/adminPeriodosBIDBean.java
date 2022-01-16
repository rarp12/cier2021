/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.gerenciales;

import co.stackpointer.cier.modelo.entidad.BID.PeriodosBID;
import co.stackpointer.cier.modelo.fachada.BIDFacadeLocal;
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
@ManagedBean(name = "adminPeriodoaBID")
@ViewScoped
public class adminPeriodosBIDBean implements Serializable {

    @EJB
    private BIDFacadeLocal fachadaBID;
    private List<PeriodosBID> lista;
    
    private PeriodosBID periodoNuevo;
    private ResourceBundle bundleMensajes = ResourceBundle.getBundle("etiquetas", FacesContext.getCurrentInstance().getViewRoot().getLocale());

    @PostConstruct
    public void inicializar() {
        lista = fachadaBID.getPeriodosBID();
        periodoNuevo=new PeriodosBID();
    }
    
    public void onRowEdit(RowEditEvent event) {
        PeriodosBID periodoEdit = (PeriodosBID)event.getObject();
        fachadaBID.actualizarPeriodoBID(periodoEdit);
        periodoNuevo = new PeriodosBID();
    }
    
    public void guardarPeriodoNuevo(){
        fachadaBID.guardarPeriodoBID(periodoNuevo);
        periodoNuevo=new PeriodosBID();
        lista = fachadaBID.getPeriodosBID();
    }

    public PeriodosBID getPeriodoNuevo() {
        return periodoNuevo;
    }
    
    public void eliminar(PeriodosBID periodoElim) {
        List aux=fachadaBID.consultarIndicadoresBID(periodoElim);
        if(aux==null || aux.isEmpty()){
            aux=fachadaBID.consultarCalificacionesBID(periodoElim.getCodigo());
        }
        if(aux!=null && !aux.isEmpty()){
            Messages.addFlashGlobalError(bundleMensajes.getString("gerencial.bid.consulta.adminPeriodos.noSepuedeEliminarPeriodo"));
        }else{
            fachadaBID.eliminarPeriodoBID(periodoElim);
            lista = fachadaBID.getPeriodosBID();
        }
    }

    public void setPeriodoNuevo(PeriodosBID periodoNuevo) {
        this.periodoNuevo = periodoNuevo;
    }
    
    public List<PeriodosBID> getLista() {
        return lista;
    }
    
    
}
