/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.filtro.ambito;

/**
 *
 * @author user
 */
public class FiltroAmbitoSostenibilidad extends FiltroAmbito {
    
    private String codClima;
    private String disponibilidadReciclaje;
    private Integer condicionReciclaje;
    private String analisisConsumoAgua;
    private String analisisConsumoEnergia;

    public void inicializar() {
        super.init();
        codClima = null;
        disponibilidadReciclaje = null;
        condicionReciclaje = null;
        analisisConsumoAgua = null;
        analisisConsumoEnergia = null;
    }

    @Override
    public boolean isConsultaDeTiempoReal() {
        return codZona != null || codSector != null || codClima != null 
                || disponibilidadReciclaje != null || condicionReciclaje != null
                || analisisConsumoAgua != null || analisisConsumoEnergia != null;
    }

    public String getCodClima() {
        return codClima;
    }

    public void setCodClima(String codClima) {
        this.codClima = codClima;
    }

    public String getDisponibilidadReciclaje() {
        return disponibilidadReciclaje;
    }

    public void setDisponibilidadReciclaje(String disponibilidadReciclaje) {
        this.disponibilidadReciclaje = disponibilidadReciclaje;
    }

    public Integer getCondicionReciclaje() {
        return condicionReciclaje;
    }

    public void setCondicionReciclaje(Integer condicionReciclaje) {
        this.condicionReciclaje = condicionReciclaje;
    }

    public String getAnalisisConsumoAgua() {
        return analisisConsumoAgua;
    }

    public void setAnalisisConsumoAgua(String analisisConsumoAgua) {
        this.analisisConsumoAgua = analisisConsumoAgua;
    }

    public String getAnalisisConsumoEnergia() {
        return analisisConsumoEnergia;
    }

    public void setAnalisisConsumoEnergia(String analisisConsumoEnergia) {
        this.analisisConsumoEnergia = analisisConsumoEnergia;
    }
    
}
