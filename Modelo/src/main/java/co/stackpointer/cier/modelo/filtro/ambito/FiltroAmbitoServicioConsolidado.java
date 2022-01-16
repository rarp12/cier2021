/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.filtro.ambito;

/**
 *
 * @author user
 */
public class FiltroAmbitoServicioConsolidado extends FiltroAmbito{   

   
    private Integer condicionAgua;
    private Integer condicionEnergia;
    private Integer condicionGas;
    private Integer condicionBasuras;
    private Integer condicionReciclaje;
    private Integer condicionAlcantarillado;
    private Integer condicionPluvial;
    private Integer condicionInternet;
    private Integer condicionTelefono;
    
     public void inicializar() {
        super.init();
        condicionAgua = null;
        condicionEnergia = null;
        condicionGas = null;
        condicionBasuras = null;
        condicionReciclaje = null;
        condicionAlcantarillado = null;
        condicionPluvial = null;
        condicionInternet = null;
        condicionTelefono = null;
    }

    @Override
    public boolean isConsultaDeTiempoReal() {
        return codZona != null || codSector != null;
    }

    public Integer getCondicionAgua() {
        return condicionAgua;
    }

    public void setCondicionAgua(Integer condicionAgua) {
        this.condicionAgua = condicionAgua;
    }

    public Integer getCondicionEnergia() {
        return condicionEnergia;
    }

    public void setCondicionEnergia(Integer condicionEnergia) {
        this.condicionEnergia = condicionEnergia;
    }

    public Integer getCondicionGas() {
        return condicionGas;
    }

    public void setCondicionGas(Integer condicionGas) {
        this.condicionGas = condicionGas;
    }

    public Integer getCondicionBasuras() {
        return condicionBasuras;
    }

    public void setCondicionBasuras(Integer condicionBasuras) {
        this.condicionBasuras = condicionBasuras;
    }

    public Integer getCondicionReciclaje() {
        return condicionReciclaje;
    }

    public void setCondicionReciclaje(Integer condicionReciclaje) {
        this.condicionReciclaje = condicionReciclaje;
    }

    public Integer getCondicionAlcantarillado() {
        return condicionAlcantarillado;
    }

    public void setCondicionAlcantarillado(Integer condicionAlcantarillado) {
        this.condicionAlcantarillado = condicionAlcantarillado;
    }

    public Integer getCondicionPluvial() {
        return condicionPluvial;
    }

    public void setCondicionPluvial(Integer condicionPluvial) {
        this.condicionPluvial = condicionPluvial;
    }

    public Integer getCondicionInternet() {
        return condicionInternet;
    }

    public void setCondicionInternet(Integer condicionInternet) {
        this.condicionInternet = condicionInternet;
    }

    public Integer getCondicionTelefono() {
        return condicionTelefono;
    }

    public void setCondicionTelefono(Integer condicionTelefono) {
        this.condicionTelefono = condicionTelefono;
    }
}
