/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.filtro.ambito;

/**
 *
 * @author user
 */
public class FiltroAmbitoRiesgoNatural extends FiltroAmbito{
    private String codRiesgo;
    private Integer codCondicion;
    
    public void inicializar() {
       super.init();
       codRiesgo = null;
       codCondicion = null;
    }

    public String getCodRiesgo() {
        return codRiesgo;
    }

    public void setCodRiesgo(String codRiesgo) {
        this.codRiesgo = codRiesgo;
    }   

    public Integer getCodCondicion() {
        return codCondicion;
    }

    public void setCodCondicion(Integer codCondicion) {
        this.codCondicion = codCondicion;
    }
    
    
    @Override
    public boolean isConsultaDeTiempoReal() {
        return codZona != null || codSector != null || codRiesgo != null || codCondicion != null;
    }
    
}
