/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;
import co.stackpointer.cier.modelo.entidad.acceso.Rol;

/**
 *
 * @author user
 */
@FacesConverter(value = "pickListConverter")
public class PickListConverter implements Converter {
        @Override
        public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
            Object ret = null;
            if (arg1 instanceof PickList) {
                Object dualList = ((PickList) arg1).getValue();
                DualListModel dl = (DualListModel) dualList;
                for (Object o : dl.getSource()) {
                    String id = "";
                    if (o instanceof Rol) {
                        id += ((Rol) o).getId();
                    }
                    /*if (o instanceof Grupo) {
                        id += ((Grupo) o).getId();
                    }*/
                    if (arg2.equals(id)) {
                        ret = o;
                        break;
                    }
                }
                if (ret == null)
                    for (Object o : dl.getTarget()) {
                            String id = "";
                            if (o instanceof Rol) {
                                id += ((Rol) o).getId();
                            }
                           /* if (o instanceof Grupo) {
                                id += ((Grupo) o).getId();
                            }*/
                        if (arg2.equals(id)) {
                            ret = o;
                            break;
                        }
                    }
            }
            return ret;
        }

        @Override
        public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
            String str = "";
            if (arg2 instanceof Rol) {
                str = "" + ((Rol) arg2).getId();
            }
            /*if (arg2 instanceof Grupo) {
                str = "" + ((Grupo) arg2).getId();
            }*/
            return str;
        }
}