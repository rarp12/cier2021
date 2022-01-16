package co.stackpointer.cier.vistacontrolador.converter;

import co.stackpointer.cier.modelo.entidad.indicador. SedeHis;
import co.stackpointer.cier.modelo.entidad.indicador. SedeHisPK;
import co.stackpointer.cier.modelo.fachada.EstructuraDPAFacadeLocal;
import java.util.StringTokenizer;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

@ManagedBean(name="sedeHisConverter")
public class SedeHisConverter implements Converter {

    @EJB
    private EstructuraDPAFacadeLocal facEstDPA;  

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        if (value == null || value.length() == 0) {
            return null;
        }        
        StringTokenizer st = new StringTokenizer(value,";");
         SedeHisPK pk = new  SedeHisPK();
        pk.setPeriodo(Integer.valueOf(st.nextToken()));
        pk.setId(Long.valueOf(st.nextToken()));
        return facEstDPA.consultarSedeHis(pk);
    }

    
    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null
                || (object instanceof String && ((String) object).length() == 0)) {
            return null;
        }
        if (object instanceof  SedeHis) {
             SedeHis value = ( SedeHis) object;
            return value.getPk().getPeriodo() + ";" + value.getPk().getId();
        } else {
            return null;
        }
    }
}
