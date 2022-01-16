/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.filtro.ambito;

import co.stackpointer.cier.modelo.tipo.SiNo;

/**
 *
 * @author user
 */
public class FiltroAmbitoComplementarios extends FiltroAmbito{
    private String codUsoSuelo;
    private String codTenenciaLote;
    private String codTipoDocPropiedad;
    private String codTipoSaneamiento;
    private SiNo tieneSaneamiento;
    
    public void inicializar() {
       super.init();
       codUsoSuelo = null;
       codTenenciaLote = null;
       codTipoDocPropiedad = null;
       codTipoSaneamiento = null;
       tieneSaneamiento = null;
    }

    public String getCodUsoSuelo() {
        return codUsoSuelo;
    }

    public void setCodUsoSuelo(String codUsoSuelo) {
        this.codUsoSuelo = codUsoSuelo;
    }

    public String getCodTenenciaLote() {
        return codTenenciaLote;
    }

    public void setCodTenenciaLote(String codTenenciaLote) {
        this.codTenenciaLote = codTenenciaLote;
    }

    public String getCodTipoDocPropiedad() {
        return codTipoDocPropiedad;
    }

    public void setCodTipoDocPropiedad(String codTipoDocPropiedad) {
        this.codTipoDocPropiedad = codTipoDocPropiedad;
    }

    public String getCodTipoSaneamiento() {
        return codTipoSaneamiento;
    }

    public void setCodTipoSaneamiento(String codTipoSaneamiento) {
        this.codTipoSaneamiento = codTipoSaneamiento;
    }

    public SiNo getTieneSaneamiento() {
        return tieneSaneamiento;
    }

    public void setTieneSaneamiento(SiNo tieneSaneamiento) {
        this.tieneSaneamiento = tieneSaneamiento;
    }
    
    @Override
    public boolean isConsultaDeTiempoReal() {
        return codZona != null || codSector != null || codUsoSuelo != null || codTenenciaLote != null || codTipoDocPropiedad != null || codTipoSaneamiento != null || tieneSaneamiento != null;
    }
    
}
