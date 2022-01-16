/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.filtro.ambito;

/**
 *
 * @author user
 */
public class FiltroAmbitoEdificio extends FiltroAmbito {
    
    private Integer añoContruccionIni;
    private Integer añoContruccionFin;
    private String codMaterialAcabados;
    private String codMaterialCubiertas;
    private String codMaterialMuros;    
    private String condMaterialidadPredio;
    private Integer condMaterial;
    private String condMaterialidadEdificio;

    public void inicializar() {
        super.init();
        añoContruccionIni = null;
        añoContruccionFin = null;
        codMaterialAcabados = null;
        codMaterialCubiertas = null;
        codMaterialMuros = null;
        condMaterialidadPredio = null;
        condMaterial = null;
        condMaterialidadEdificio = null;
    }

    @Override
    public boolean isConsultaDeTiempoReal() {
        return codZona != null || codSector != null;
    }

    public Integer getAñoContruccionIni() {
        return añoContruccionIni;
    }

    public void setAñoContruccionIni(Integer añoContruccionIni) {
        this.añoContruccionIni = añoContruccionIni;
    }

    public Integer getAñoContruccionFin() {
        return añoContruccionFin;
    }

    public void setAñoContruccionFin(Integer añoContruccionFin) {
        this.añoContruccionFin = añoContruccionFin;
    }

    public String getCodMaterialAcabados() {
        return codMaterialAcabados;
    }

    public void setCodMaterialAcabados(String codMaterialAcabados) {
        this.codMaterialAcabados = codMaterialAcabados;
    }

    
    public String getCodMaterialCubiertas() {
        return codMaterialCubiertas;
    }

    public void setCodMaterialCubiertas(String codMaterialCubiertas) {
        this.codMaterialCubiertas = codMaterialCubiertas;
    }

    public String getCodMaterialMuros() {
        return codMaterialMuros;
    }

    public void setCodMaterialMuros(String codMaterialMuros) {
        this.codMaterialMuros = codMaterialMuros;
    }

    public String getCondMaterialidadPredio() {
        return condMaterialidadPredio;
    }

    public void setCondMaterialidadPredio(String condMaterialidadPredio) {
        this.condMaterialidadPredio = condMaterialidadPredio;
    }

    public Integer getCondMaterial() {
        return condMaterial;
    }

    public void setCondMaterial(Integer condMaterial) {
        this.condMaterial = condMaterial;
    }

    public String getCondMaterialidadEdificio() {
        return condMaterialidadEdificio;
    }

    public void setCondMaterialidadEdificio(String condMaterialidadEdificio) {
        this.condMaterialidadEdificio = condMaterialidadEdificio;
    }
    

}
