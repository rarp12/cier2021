/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.filtro.ambito;

/**
 *
 * @author user
 */
public class FiltroAmbitoGeneralidad extends FiltroAmbito{
    private String codNivelEnseñaza;     
    private String codJornada;
    
    public void inicializar() {
        super.init();
        codNivelEnseñaza = null;
        codJornada = null;
    }

    public String getCodNivelEnseñaza() {
        return codNivelEnseñaza;
    }

    public void setCodNivelEnseñaza(String codNivelEnseñaza) {
        this.codNivelEnseñaza = codNivelEnseñaza;
    }   
  
    public String getCodJornada() {
        return codJornada;
    }

    public void setCodJornada(String codJornada) {
        this.codJornada = codJornada;
    }

    @Override
    public boolean isConsultaDeTiempoReal() {
        return codZona != null || codSector != null || codJornada != null || codNivelEnseñaza != null;
    }
}
