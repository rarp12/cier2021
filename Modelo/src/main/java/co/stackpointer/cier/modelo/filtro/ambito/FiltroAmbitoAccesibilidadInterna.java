/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.filtro.ambito;

/**
 *
 * @author user
 */
public class FiltroAmbitoAccesibilidadInterna extends FiltroAmbito{
    private String accesoDiscapacitado;
    private String elementoComplementario;
    
    public void inicializar() {
       super.init();
       elementoComplementario = null;
       accesoDiscapacitado = null;
    }  

    public String getAccesoDiscapacitado() {
        return accesoDiscapacitado;
    }

    public void setAccesoDiscapacitado(String accesoDiscapacitado) {
        this.accesoDiscapacitado = accesoDiscapacitado;
    }

    public String getElementoComplementario() {
        return elementoComplementario;
    }

    public void setElementoComplementario(String elementoComplementario) {
        this.elementoComplementario = elementoComplementario;
    }
        
    @Override
    public boolean isConsultaDeTiempoReal() {
        return codZona != null || codSector != null || ((accesoDiscapacitado != null || elementoComplementario != null) && isAgrupamientoPredio());
    }
}
