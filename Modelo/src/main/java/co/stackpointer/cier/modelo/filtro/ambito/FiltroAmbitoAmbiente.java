/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.filtro.ambito;

/**
 *
 * @author user
 */
public class FiltroAmbitoAmbiente extends FiltroAmbito{  
    
    private String unidadFuncional;
    
    public void inicializar() {
       super.init();  
       unidadFuncional = null;
    }

    public String getUnidadFuncional() {
        return unidadFuncional;
    }

    public void setUnidadFuncional(String unidadFuncional) {
        this.unidadFuncional = unidadFuncional;
    }
    
    @Override
    public boolean isConsultaDeTiempoReal() {
        return codZona != null || codSector != null;
    }
    
}
