/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.filtro.ambito;

/**
 *
 * @author user
 */
public class FiltroAmbitoAccesibilidad extends FiltroAmbito{
    private String codMedioTransporte;
    private Integer codCondicion;
    
    public void inicializar() {
       super.init();
       codCondicion = null;
       codMedioTransporte = null;
    }    

    public Integer getCodCondicion() {
        return codCondicion;
    }

    public void setCodCondicion(Integer codCondicion) {
        this.codCondicion = codCondicion;
    }

    public String getCodMedioTransporte() {
        return codMedioTransporte;
    }

    public void setCodMedioTransporte(String codMedioTransporte) {
        this.codMedioTransporte = codMedioTransporte;
    }
        
    @Override
    public boolean isConsultaDeTiempoReal() {
        return codZona != null || codSector != null || ((codMedioTransporte != null || codCondicion != null) && isAgrupamientoPredio());
    }
}
