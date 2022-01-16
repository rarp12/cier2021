package co.stackpointer.cier.vistacontrolador.converter;

import co.stackpointer.cier.modelo.entidad.indicador.PredioHis;
import co.stackpointer.cier.modelo.entidad.indicador.PredioHisPK;
import co.stackpointer.cier.modelo.fachada.EstructuraDPAFacadeLocal;
import java.util.StringTokenizer;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

@ManagedBean(name="predioHisConverter")
public class PredioHisConverter implements Converter {
    
    @EJB
    private EstructuraDPAFacadeLocal facEstDPA;  

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        if (value == null || value.length() == 0) {
            return null;
        }        
        StringTokenizer st = new StringTokenizer(value,";");
        PredioHisPK pk = new PredioHisPK();
        pk.setPeriodo(Integer.valueOf(st.nextToken()));
        pk.setId(Long.valueOf(st.nextToken()));
        return facEstDPA.consultarPredioHis(pk);
    }

    
    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null
                || (object instanceof String && ((String) object).length() == 0)) {
            return null;
        }
        if (object instanceof PredioHis) {
            PredioHis value = (PredioHis) object;
            return value.getPk().getPeriodo() + ";" + value.getPk().getId();
        } else {
            return null;
        }
    }
}
