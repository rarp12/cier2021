/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.filtro.ambito;

import co.stackpointer.cier.modelo.tipo.ParamIndicador;

/**
 *
 * @author user
 */
public class FiltroAmbitoServicio extends FiltroAmbito{   
    private Integer condicionServicio;
    private String tieneServicio;
    private String codigoTipoServicio;
    private String nombreServicio;
    private String analisisServicio;
    

    public void inicializar() {
        super.init();
        condicionServicio = null;
        tieneServicio = null;
        codigoTipoServicio = null;
        nombreServicio = null;
        analisisServicio = null;
    }

    @Override
    public boolean isConsultaDeTiempoReal() {
        return codZona != null || codSector != null;
    }

    public Integer getCondicionServicio() {
        return condicionServicio;
    }

    public void setCondicionServicio(Integer condicionServicio) {
        this.condicionServicio = condicionServicio;
    }

    public String getTieneServicio() {
        return tieneServicio;
    }

    public void setTieneServicio(String tieneServicio) {
        this.tieneServicio = tieneServicio;
    }

    public String getCodigoTipoServicio() {
        return codigoTipoServicio;
    }

    public void setCodigoTipoServicio(String codigoTipoServicio) {
        this.codigoTipoServicio = codigoTipoServicio;
    }

    public String getNombreServicio() {
        return nombreServicio;
    }

    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }

    public String getAnalisisServicio() {
        return analisisServicio != null ? analisisServicio.toLowerCase() : null;
    }

    public void setAnalisisServicio(String analisisServicio) {
        this.analisisServicio = analisisServicio;
    }
    public String getColumnaServicio() {
        return nombreServicio != null ? nombreServicio.toLowerCase() : null;
    }

    public ParamIndicador getIndicadorDisponibilidadServicio() {
        return nombreServicio != null ? ParamIndicador.valueOf("IND_SERVICIO_DISPONIBILIDAD_" + nombreServicio) : null;
    }
    
    public ParamIndicador getIndicadorCondicionServicio() {
        return nombreServicio != null ? ParamIndicador.valueOf("IND_SERVICIO_CONDICION_" + nombreServicio) : null;
    }
    
    public ParamIndicador getIndicadorTipoServicio1() {
        return nombreServicio != null ? ParamIndicador.valueOf("IND_SERVICIO_TIPO_" + nombreServicio + "_1") : null;
    }
    
    public ParamIndicador getIndicadorTipoServicio2() {
        return nombreServicio != null ? ParamIndicador.valueOf("IND_SERVICIO_TIPO_" + nombreServicio + "_2") : null;
    }

    public ParamIndicador getIndicadorNumPrediosDisponibilidadServicio() {
        return nombreServicio != null ? ParamIndicador.valueOf("IND_SERVICIO_NUM_PREDIOS_DISPONIBILIDAD_" + nombreServicio) : null;
    }

    public ParamIndicador getIndicadorNumPrediosCondicionServicio() {
        return nombreServicio != null ? ParamIndicador.valueOf("IND_SERVICIO_NUM_PREDIOS_CONDICION_" + nombreServicio) : null;
    }

    public ParamIndicador getIndicadorNumPrediosAnalisisServicio() {
        return nombreServicio != null ? ParamIndicador.valueOf("IND_SERVICIO_NUM_PREDIOS_ANALISIS_" + analisisServicio + "_" + nombreServicio) : null;
    }

    public boolean isRequiereTipoServicio() {
        return nombreServicio != null && !nombreServicio.equals("SISTEMA_RECICLAJE");
    }

    public boolean isAnalisisSuficiencia() {
        return analisisServicio != null && analisisServicio.equals("SUFICIENCIA");
    }

}
