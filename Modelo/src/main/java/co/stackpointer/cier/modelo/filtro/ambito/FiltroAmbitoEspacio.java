/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.filtro.ambito;

/**
 *
 * @author user
 */
public class FiltroAmbitoEspacio extends FiltroAmbito {

    private Integer condicionMaterial;   
    private String codPiso;
    private String codMuro;
    private String codAcaboMuro;
    private String codVentana;
    private String codPuerta;
    private String codTecho;

    public void inicializar() {
        super.init();
        condicionMaterial = null;
        codPiso = null;
        codMuro = null;
        codAcaboMuro = null;
        codVentana = null;
        codPuerta = null;
        codTecho = null;
    }

    public Integer getCondicionMaterial() {
        return condicionMaterial;
    }

    public void setCondicionMaterial(Integer condicionMaterial) {
        this.condicionMaterial = condicionMaterial;
    }

    public String getCodPiso() {
        return codPiso;
    }

    public void setCodPiso(String codPiso) {
        this.codPiso = codPiso;
    }

    public String getCodMuro() {
        return codMuro;
    }

    public void setCodMuro(String codMuro) {
        this.codMuro = codMuro;
    }

    public String getCodAcaboMuro() {
        return codAcaboMuro;
    }

    public void setCodAcaboMuro(String codAcaboMuro) {
        this.codAcaboMuro = codAcaboMuro;
    }

    public String getCodVentana() {
        return codVentana;
    }

    public void setCodVentana(String codVentana) {
        this.codVentana = codVentana;
    }

    public String getCodPuerta() {
        return codPuerta;
    }

    public void setCodPuerta(String codPuerta) {
        this.codPuerta = codPuerta;
    }

    public String getCodTecho() {
        return codTecho;
    }

    public void setCodTecho(String codTecho) {
        this.codTecho = codTecho;
    }
    
    @Override
    public boolean isConsultaDeTiempoReal() {
        /*return codZona != null || codSector != null
                || condicionMaterial != null;*/
        return true;
    }
}
