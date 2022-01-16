/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.filtro.ambito;

/**
 *
 * @author user
 */
public class FiltroAmbitoPropiedad extends FiltroAmbito{   
    private String propiedadVerificable;
    private String codPropietario;
    
    public void inicializar() {
        super.init();
        propiedadVerificable = null;
        codPropietario = null;
    }

    public String getPropiedadVerificable() {
        return propiedadVerificable;
    }

    public void setPropiedadVerificable(String propiedadVerificable) {
        this.propiedadVerificable = propiedadVerificable;
    }

    public String getCodPropietario() {
        return codPropietario;
    }

    public void setCodPropietario(String codPropietario) {
        this.codPropietario = codPropietario;
    }
    
    @Override
    public boolean isConsultaDeTiempoReal() {
        return codZona != null || codSector != null || ((propiedadVerificable != null || codPropietario != null) && isAgrupamientoPredio());
    }

}
