/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.filtro.ambito;

/**
 *
 * @author user
 */
public class FiltroAmbitoRiesgoAntropico extends FiltroAmbito{
    private String codRiesgo;
    private Integer codCondicionRiesgo;
    private String codRetiro;
    
    public void inicializar() {
       super.init();
       codRiesgo = null;
       codCondicionRiesgo = null;
       codRetiro = null;
    }

    public String getCodRiesgo() {
        return codRiesgo;
    }

    public void setCodRiesgo(String codRiesgo) {
        this.codRiesgo = codRiesgo;
    }   

    public Integer getCodCondicionRiesgo() {
        return codCondicionRiesgo;
    }

    public void setCodCondicionRiesgo(Integer codCondicionRiesgo) {
        this.codCondicionRiesgo = codCondicionRiesgo;
    }

    public String getCodRetiro() {
        return codRetiro;
    }

    public void setCodRetiro(String codRetiro) {
        this.codRetiro = codRetiro;
    }
        
    @Override
    public boolean isConsultaDeTiempoReal() {
        return codZona != null || codSector != null || codRiesgo != null || codCondicionRiesgo != null || codRetiro != null;
    }
}
