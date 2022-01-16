/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.instrumento;

import co.stackpointer.cier.modelo.entidad.establecimiento.Establecimiento;
import co.stackpointer.cier.modelo.entidad.establecimiento.Sede;
import co.stackpointer.cier.modelo.fachada.EstablecimientoFacadeLocal;
import co.stackpointer.cier.modelo.fachada.EstructuraDPAFacadeLocal;
import co.stackpointer.cier.modelo.fachada.InstrumentoFacadeLocal;
import co.stackpointer.cier.modelo.fachada.PredioFacadeLocal;
import co.stackpointer.cier.modelo.interfaceDPA.EstablecimientoDPA;
import co.stackpointer.cier.modelo.interfaceDPA.PredioDPA;
import co.stackpointer.cier.vistacontrolador.consulta.ConsultaBase;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author user
 */
@ManagedBean(name = "busquedaSede")
@ViewScoped
public class BusquedaSedeBean extends ConsultaBase implements Serializable {
    
  
    @EJB
    PredioFacadeLocal fPredios;
    @EJB
    InstrumentoFacadeLocal fInstrumentos;
    
      
    @EJB
    EstablecimientoFacadeLocal fEstablecimiento; 
    
    @EJB
    EstructuraDPAFacadeLocal festructuraDPA;
    
    private String nombre;
    private String codigo;    
    
    //Creacion Predio
    private String codEstablecimiento;
    private List<Sede> sedesEncontradas;

    private String mensaje;
    
    
    @PostConstruct
    public void init()
    { 
        super.initActual();      
    }   
 
    
    public void consultarSedesPorEstablecimiento(){       
        sedesEncontradas = festructuraDPA.consultarSedes(codEstablecimiento);
    }
  
     public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodEstablecimiento() {
        return codEstablecimiento;
    }

    public void setCodEstablecimiento(String codEstablecimiento) {
        this.codEstablecimiento = codEstablecimiento;
    }

    
    public List<Establecimiento> complete(String query) {
        
        
        List<Establecimiento> establecimeintosFiltrados= new ArrayList<Establecimiento>();
		
		for (EstablecimientoDPA esta: listaEstablecimientos) {
                if(esta.getNombre().toUpperCase().contains(query.toUpperCase()))
                    establecimeintosFiltrados.add((Establecimiento)esta);
		}
		
		return establecimeintosFiltrados;
	}
  
    public void cambioEstablecimientoVig(SelectEvent event) {
        this.resetearListasSedesPredios();
        
        if (seleccionEst != null) {
            
             Establecimiento est=facEstDPA.consultarEstablecimiento(Long.valueOf(seleccionEst.getId()));
             seleccionEst=est;
            listaSedes = est.getSedes();
        }
    }
    

    @Override
    public void resetearFiltros() {
        this.resetearNiveles();
        this.resetearListasEstSedesPredios();
        this.cargarNivel1();        
        codEstablecimiento=null;   
        sedesEncontradas = new ArrayList<Sede>();
    }
    
    @Override
    public void cambioPeriodo() {
        super.initHistoricos(periodo);
        super.cargarNivel1();
    }  

    public String getMensaje() {
        return mensaje;
    }
    
    @Override
     public void cambioSede() {
        seleccionPredio = null;
        seleccionSede= facEstDPA.consultarSede(seleccionSede.getId());
        listaPredios = seleccionSede != null ? seleccionSede.getPredios() : new ArrayList<PredioDPA>();
    }

    public List<Sede> getSedesEncontradas() {
        return sedesEncontradas;
    }

    public void setSedesEncontradas(List<Sede> sedesEncontradas) {
        this.sedesEncontradas = sedesEncontradas;
    }
    
}
