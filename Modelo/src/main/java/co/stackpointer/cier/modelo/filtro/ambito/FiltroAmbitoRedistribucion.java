/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.filtro.ambito;

/**
 *
 * @author user
 */
public class FiltroAmbitoRedistribucion extends FiltroAmbito{   

    public void inicializar() {
        super.init();
    }

    @Override
    public boolean isConsultaDeTiempoReal() {
        return codZona != null || codSector != null;
    }

}
