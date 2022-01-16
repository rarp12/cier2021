package co.stackpointer.cier.vistacontrolador.converter;

import co.stackpointer.cier.modelo.entidad.establecimiento.Establecimiento;
import co.stackpointer.cier.modelo.fachada.EstructuraDPAFacadeLocal;
import co.stackpointer.cier.modelo.interfaceDPA.EstablecimientoDPA;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

@ManagedBean(name="establecimientoVigConverter")
public class EstablecimientoVigConverter implements Converter {

    @EJB
    private EstructuraDPAFacadeLocal facEstDPA;  

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        if (value == null || value.length() == 0) {
            return null;
        }        
       
      // 
      
        Establecimiento est = new Establecimiento();
        est.setId(Long.valueOf(value));
        
        return est;
    }

    
    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null
                || (object instanceof String && ((String) object).length() == 0)) {
            return null;
        }
        if (object instanceof  Establecimiento) {
             Establecimiento value = ( Establecimiento) object;
            return String.valueOf(value.getId());
        } else {
            return null;
        }
    }
}
