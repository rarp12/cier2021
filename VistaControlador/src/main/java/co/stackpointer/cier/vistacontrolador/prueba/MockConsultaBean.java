/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.prueba;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name="mockConsultaBean")
@ViewScoped
public class MockConsultaBean {
    
    private static final int NUMBER_OF_ROWS = 50;
    
    private List<Row> results;
    
    @PostConstruct 
    public void init(){
        this.results = generateData();
    }
    
    
    public List<Row> generateData(){
        
        List<Row> _results = new ArrayList<Row>(); 
        
        for (int i = 1; i <= NUMBER_OF_ROWS; i++) {
            Row row = new Row();
            row.setCodigoEst("COD_EST_" + i);          
            row.setNombreEst("Nombre Est. " + i);          
            row.setCodigoSede("COD_SED_" + i);          
            row.setCodigoPredio("COD_PRE_" + i);          
            row.setNombrePredio("Nombre del Predio " + i);          
            row.setTipoRiesgoNat1("Incendio Forestal");          
            row.setCondRiesgoNat1("3");
            row.setTipoRiesgoNat2("Erupción Volcánica");          
            row.setCondRiesgoNat2("4"); 
            row.setTipoRiesgoNat3("Tsunamis");          
            row.setCondRiesgoNat3("1");  
            row.setTipoRiesgoNat4("Otro");          
            row.setCondRiesgoNat4("2");          
            row.setCondRiesgo("5");     
            _results.add(row);
        }
        
        return _results;
    }

    public List<Row> getResults() {
        return results;
    }

    public void setResults(List<Row> results) {
        this.results = results;
    }    
}
