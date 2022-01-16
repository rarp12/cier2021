/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.consulta.comparador;

/**
 *
 * @author cier
 */
public class RegistroRedistribuccion extends Registro {

    private Double cuposTotal;
    private Double comparaCuposTotal;
    private Double utilEstandarTotal;
    private Double comparaUtilEstandarTotal;
    private Long numPrediosSaturados;
    private Long comparaNumPrediosSaturados;
    private Long numPrediosSudUtilizados;
    private Long comparaNumPrediosSudUtilizados;

    @Override
    public void inicializarTotales() {
        cuposTotal = 0D;
        comparaCuposTotal = 0D;
        numPrediosSaturados = 0L;
        comparaNumPrediosSaturados = 0L;
        numPrediosSudUtilizados = 0L;
        comparaNumPrediosSudUtilizados = 0L;
    }

    public Double getCuposTotal() {
        return cuposTotal;
    }

    public void setCuposTotal(Double cuposTotal) {
        this.cuposTotal = cuposTotal;
    }

    public Double getComparaCuposTotal() {
        return comparaCuposTotal;
    }

    public void setComparaCuposTotal(Double comparaCuposTotal) {
        this.comparaCuposTotal = comparaCuposTotal;
    }

    public Double getUtilEstandarTotal() {
        return utilEstandarTotal;
    }

    public void setUtilEstandarTotal(Double utilEstandarTotal) {
        this.utilEstandarTotal = utilEstandarTotal;
    }

    public Double getComparaUtilEstandarTotal() {
        return comparaUtilEstandarTotal;
    }

    public void setComparaUtilEstandarTotal(Double comparaUtilEstandarTotal) {
        this.comparaUtilEstandarTotal = comparaUtilEstandarTotal;
    }

    public Long getNumPrediosSaturados() {
        return numPrediosSaturados;
    }

    public void setNumPrediosSaturados(Long numPrediosSaturados) {
        this.numPrediosSaturados = numPrediosSaturados;
    }

    public Long getComparaNumPrediosSaturados() {
        return comparaNumPrediosSaturados;
    }

    public void setComparaNumPrediosSaturados(Long comparaNumPrediosSaturados) {
        this.comparaNumPrediosSaturados = comparaNumPrediosSaturados;
    }

    public Long getNumPrediosSudUtilizados() {
        return numPrediosSudUtilizados;
    }

    public void setNumPrediosSudUtilizados(Long numPrediosSudUtilizados) {
        this.numPrediosSudUtilizados = numPrediosSudUtilizados;
    }

    public Long getComparaNumPrediosSudUtilizados() {
        return comparaNumPrediosSudUtilizados;
    }

    public void setComparaNumPrediosSudUtilizados(Long comparaNumPrediosSudUtilizados) {
        this.comparaNumPrediosSudUtilizados = comparaNumPrediosSudUtilizados;
    }    
    
}
