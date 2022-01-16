/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.consulta.ambito.riesgo;

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
public class LazyRiegosNaturalesDataModel extends LazyDataModel<RiesgosNaturalesBean.Registro> {
    
    private static final long serialVersionUID = 1L;
    private List<RiesgosNaturalesBean.Registro> datasource;
    
    public LazyRiegosNaturalesDataModel(List<RiesgosNaturalesBean.Registro> datasource) {
        this.datasource = datasource;
    }
    
    @Override
    public List<RiesgosNaturalesBean.Registro> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
        List<RiesgosNaturalesBean.Registro> data = new ArrayList<RiesgosNaturalesBean.Registro>();

        //filter
        for (RiesgosNaturalesBean.Registro reg : datasource) {
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
            Collections.sort(data, new LazySorter(sortField, sortOrder));
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
