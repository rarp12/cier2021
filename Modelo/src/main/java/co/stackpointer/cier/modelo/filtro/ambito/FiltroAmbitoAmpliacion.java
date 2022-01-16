/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.filtro.ambito;

import co.stackpointer.cier.modelo.tipo.Cumplimiento;

/**
 *
 * @author user
 */
public class FiltroAmbitoAmpliacion extends FiltroAmbito {

    private Cumplimiento cumplimientoAreaPredio;
    private Cumplimiento cumplimientoAreaConstruccion;
    private Cumplimiento cumplimientoAreaAmbienteAula;

    public void inicializar() {
        super.init();
        cumplimientoAreaPredio = null;
        cumplimientoAreaConstruccion = null;
        cumplimientoAreaAmbienteAula = null;
    }

    @Override
    public boolean isConsultaDeTiempoReal() {
        return codZona != null || codSector != null;
    }

    public Cumplimiento getCumplimientoAreaPredio() {
        return cumplimientoAreaPredio;
    }

    public void setCumplimientoAreaPredio(Cumplimiento cumplimiento) {
        this.cumplimientoAreaPredio = cumplimiento;
    }

    public Cumplimiento getCumplimientoAreaConstruccion() {
        return cumplimientoAreaConstruccion;
    }

    public void setCumplimientoAreaConstruccion(Cumplimiento cumplimiento) {
        this.cumplimientoAreaConstruccion = cumplimiento;
    }

    public Cumplimiento getCumplimientoAreaAmbienteAula() {
        return cumplimientoAreaAmbienteAula;
    }

    public void setCumplimientoAreaAmbienteAula(Cumplimiento cumplimiento) {
        this.cumplimientoAreaAmbienteAula = cumplimiento;
    }

    public boolean isTieneCumplimientos() {
        return cumplimientoAreaPredio != null || cumplimientoAreaConstruccion != null || cumplimientoAreaAmbienteAula != null;
    }
}
