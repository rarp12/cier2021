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
public class FiltroAmbitoControlVigilancia extends FiltroAmbito {

    private SiNo sistemaIluminacion;
    private SiNo sistemaCamara;
    private Integer condicionOrdenPublico;
    private String vulnerabilidad;

    public void inicializar() {
        super.init();
        condicionOrdenPublico = null;
        sistemaIluminacion = null;
        sistemaCamara = null;
        vulnerabilidad = null;
    }

    public SiNo getSistemaIluminacion() {
        return sistemaIluminacion;
    }

    public void setSistemaIluminacion(SiNo sistemaIluminacion) {
        this.sistemaIluminacion = sistemaIluminacion;
    }

    public SiNo getSistemaCamara() {
        return sistemaCamara;
    }

    public void setSistemaCamara(SiNo sistemaCamara) {
        this.sistemaCamara = sistemaCamara;
    }

    public Integer getCondicionOrdenPublico() {
        return condicionOrdenPublico;
    }

    public void setCondicionOrdenPublico(Integer condicionOrdenPublico) {
        this.condicionOrdenPublico = condicionOrdenPublico;
    }

    public String getVulnerabilidad() {
        return vulnerabilidad;
    }

    public void setVulnerabilidad(String vulnerabilidad) {
        this.vulnerabilidad = vulnerabilidad;
    }
    
    @Override
    public boolean isConsultaDeTiempoReal() {
        return codZona != null || codSector != null
                || condicionOrdenPublico != null
                || vulnerabilidad != null
                || ((sistemaIluminacion != null || sistemaCamara != null) && isAgrupamientoPredio());
    }
}
