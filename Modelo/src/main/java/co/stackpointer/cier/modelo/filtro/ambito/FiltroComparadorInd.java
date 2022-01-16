/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.filtro.ambito;

import co.stackpointer.cier.modelo.tipo.EspacioFuncional;

/**
 *
 * @author user
 */
public class FiltroComparadorInd extends FiltroAmbito{
    private Integer periodoComparado;     
    private EspacioFuncional espacioFuncional;
    private String condMaterialidadPredio;
    private String calificacionCondicion;
    private boolean swConstruccion;
    private boolean swPosibilidades;
    private boolean swAula;
    private boolean swPredio;
    
    public void inicializar() {
        super.init();
        periodoComparado = null;        
    }

    public Integer getPeriodoComparado() {
        return periodoComparado;
    }

    public void setPeriodoComparado(Integer periodoComparado) {
        this.periodoComparado = periodoComparado;
    }

    public EspacioFuncional getEspacioFuncional() {
        return espacioFuncional;
    }

    public void setEspacioFuncional(EspacioFuncional espacioFuncional) {
        this.espacioFuncional = espacioFuncional;
    }

    public String getCondMaterialidadPredio() {
        return condMaterialidadPredio;
    }

    public void setCondMaterialidadPredio(String condMaterialidadPredio) {
        this.condMaterialidadPredio = condMaterialidadPredio;
    }

    public String getCalificacionCondicion() {
        return calificacionCondicion;
    }

    public void setCalificacionCondicion(String calificacionCondicion) {
        this.calificacionCondicion = calificacionCondicion;
    }       

    public boolean isSwConstruccion() {
        return swConstruccion;
    }

    public void setSwConstruccion(boolean swConstruccion) {
        this.swConstruccion = swConstruccion;
    }

    public boolean isSwPosibilidades() {
        return swPosibilidades;
    }

    public void setSwPosibilidades(boolean swPosibilidades) {
        this.swPosibilidades = swPosibilidades;
    }

    public boolean isSwAula() {
        return swAula;
    }

    public void setSwAula(boolean swAula) {
        this.swAula = swAula;
    }

    public boolean isSwPredio() {
        return swPredio;
    }

    public void setSwPredio(boolean swPredio) {
        this.swPredio = swPredio;
    }
    
    @Override
    public boolean isConsultaDeTiempoReal() {
        return false;
    }
}
