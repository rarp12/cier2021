/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.filtro.ambito;

/**
 *
 * @author user
 */
public class FiltroAmbitoSeguridad extends FiltroAmbito{
    private String estructura;
    private Integer condicionTipoEstructura;
    private String senalizacionEvacuacion;
    private String sistemaContraIncendio;
    private String analisisRutaEvacuacion;
    private String estadoEstructura;
    
    public void inicializar() {
        super.init();
        estructura = null;
        condicionTipoEstructura = null;
        senalizacionEvacuacion = null;
        sistemaContraIncendio = null;
        analisisRutaEvacuacion = null;
        estadoEstructura = null;
    }

    public String getEstructura() {
        return estructura;
    }

    public void setEstructura(String estructura) {
        this.estructura = estructura;
    }

    public Integer getCondicionTipoEstructura() {
        return condicionTipoEstructura;
    }

    public void setCondicionTipoEstructura(Integer condicionTipoEstructura) {
        this.condicionTipoEstructura = condicionTipoEstructura;
    }    

    public String getSenalizacionEvacuacion() {
        return senalizacionEvacuacion;
    }

    public void setSenalizacionEvacuacion(String senalizacionEvacuacion) {
        this.senalizacionEvacuacion = senalizacionEvacuacion;
    }

    public String getSistemaContraIncendio() {
        return sistemaContraIncendio;
    }

    public void setSistemaContraIncendio(String sistemaContraIncendio) {
        this.sistemaContraIncendio = sistemaContraIncendio;
    }

    public String getAnalisisRutaEvacuacion() {
        return analisisRutaEvacuacion;
    }

    public void setAnalisisRutaEvacuacion(String analisisRutaEvacuacion) {
        this.analisisRutaEvacuacion = analisisRutaEvacuacion;
    }

    public String getEstadoEstructura() {
        return estadoEstructura;
    }

    public void setEstadoEstructura(String estadoEstructura) {
        this.estadoEstructura = estadoEstructura;
    }
     
    @Override
    public boolean isConsultaDeTiempoReal() {
        return codZona != null || codSector != null
                || senalizacionEvacuacion != null || sistemaContraIncendio != null
                || analisisRutaEvacuacion != null || estadoEstructura != null
                || ((estructura != null || condicionTipoEstructura != null) && isAgrupamientoPredio());
    }
}
