/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.consulta.ambito.lazyGeneralidades;

import co.stackpointer.cier.vistacontrolador.consulta.ambito.GeneralidadesBean;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author rarp1
 */
public class LazyGeneralidadesDataModel extends LazyDataModel<GeneralidadesBean.Registro> {
    
    private static final long serialVersionUID = 1L;
    private List<GeneralidadesBean.Registro> datasource;
    
    public LazyGeneralidadesDataModel(List<GeneralidadesBean.Registro> datasource) {
        this.datasource = datasource;
    }
    
    @Override
    public List<GeneralidadesBean.Registro> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
        List<GeneralidadesBean.Registro> data = new ArrayList<GeneralidadesBean.Registro>();

        //filter
        for (GeneralidadesBean.Registro reg : datasource) {
            boolean match = true;

            if (filters != null) {
                for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
                    try {
                        String filterProperty = it.next();
                        Object filterValue = filters.get(filterProperty);
                        String fieldValue = String.valueOf(reg.getClass().getField(filterProperty).get(reg));

                        if (filterValue == null || fieldValue.startsWith(filterValue.toString())) {
                            match = true;
                        } else {
                            match = false;
                            break;
                        }
                    } catch (Exception e) {
                        match = false;
                    }
                }
            }

            if (match) {
                data.add(reg);
            }
        }

        //sort
        if (sortField != null) {
            Collections.sort(data, new LazySorterGeneralidades(sortField, sortOrder));
        }

        //rowCount
        int dataSize = data.size();
        this.setRowCount(dataSize);

        //paginate
        if (dataSize > pageSize) {
            try {
                return data.subList(first, first + pageSize);
            } catch (IndexOutOfBoundsException e) {
                return data.subList(first, first + (dataSize % pageSize));
            }
        } else {
            return data;
        }
    }
    
}
