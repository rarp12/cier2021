/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.filtro.ambito;

/**
 *
 * @author rarp1
 */
public class FiltroAmbitoEspacioConsolidado extends FiltroAmbito {
    
    private String codEstablecimiento; 
    
    public void inicializar() {
        super.init();
        codEstablecimiento = null;
    }

    public String getCodEstablecimiento() {
        return codEstablecimiento;
    }

    public void setCodEstablecimiento(String codEstablecimiento) {
        this.codEstablecimiento = codEstablecimiento;
    }
    
    @Override
    public boolean isConsultaDeTiempoReal() {
        return false;
    }
}
