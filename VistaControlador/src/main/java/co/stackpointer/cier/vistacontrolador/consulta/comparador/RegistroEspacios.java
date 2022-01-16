/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.consulta.comparador;

/**
 *
 * @author cier
 */
public class RegistroEspacios extends Registro {

    private Long espaciosPiso;
    private Long comparaEspaciosPiso;
    private Double areaPiso;
    private Double comparaAreaPiso;
    private Double proporcionAreaPiso;
    private Double comparaProporcionAreaPiso;
    private Long espaciosMuro;
    private Long comparaEspaciosMuro;
    private Double areaMuro;
    private Double comparaAreaMuro;
    private Double proporcionAreaMuro;
    private Double comparaProporcionAreaMuro;
    private Long espaciosAcabadoMuro;
    private Long comparaEspaciosAcabadoMuro;
    private Double areaAcabadoMuro;
    private Double comparaAreaAcabadoMuro;
    private Double proporcionAreaAcabadoMuro;
    private Double comparaProporcionAreaAcabadoMuro;
    private Long espaciosVentana;
    private Long comparaEspaciosVentana;
    private Double proporcionVentana;
    private Double comparaProporcionVentana;
    private Long espaciosPuerta;
    private Long comparaEspaciosPuerta;
    private Double proporcionPuerta;
    private Double comparaProporcionPuerta;
    private Long espaciosTecho;
    private Long comparaEspaciosTecho;
    private Double areaTecho;
    private Double comparaAreaTecho;
    private Double proporcionAreaTecho;
    private Double comparaProporcionAreaTecho;
    private Double areaTotal;
    private Double comparaAreaTotal;

    @Override
    public void inicializarTotales() {
        espaciosPiso = 0L;
        comparaEspaciosPiso = 0L;
        areaPiso = 0D;
        comparaAreaPiso = 0D;
        espaciosMuro = 0L;
        comparaEspaciosMuro = 0L;
        areaMuro = 0D;
        comparaAreaMuro = 0D;
        espaciosAcabadoMuro = 0L;
        comparaEspaciosAcabadoMuro = 0L;
        areaAcabadoMuro = 0D;
        comparaAreaAcabadoMuro = 0D;
        espaciosVentana = 0L;
        comparaEspaciosVentana = 0L;
        espaciosPuerta = 0L;
        comparaEspaciosPuerta = 0L;
        espaciosTecho = 0L;
        comparaEspaciosTecho = 0L;
        areaTecho = 0D;
        comparaAreaTecho = 0D;
        areaTotal = 0D;
        comparaAreaTotal = 0D;
    }

    public Long getEspaciosPiso() {
        return espaciosPiso;
    }

    public void setEspaciosPiso(Long espaciosPiso) {
        this.espaciosPiso = espaciosPiso;
    }

    public Long getComparaEspaciosPiso() {
        return comparaEspaciosPiso;
    }

    public void setComparaEspaciosPiso(Long comparaEspaciosPiso) {
        this.comparaEspaciosPiso = comparaEspaciosPiso;
    }

    public Double getAreaPiso() {
        return areaPiso;
    }

    public void setAreaPiso(Double areaPiso) {
        this.areaPiso = areaPiso;
    }

    public Double getComparaAreaPiso() {
        return comparaAreaPiso;
    }

    public void setComparaAreaPiso(Double comparaAreaPiso) {
        this.comparaAreaPiso = comparaAreaPiso;
    }

    public Double getProporcionAreaPiso() {
        return proporcionAreaPiso;
    }

    public void setProporcionAreaPiso(Double proporcionAreaPiso) {
        this.proporcionAreaPiso = proporcionAreaPiso;
    }

    public Double getComparaProporcionAreaPiso() {
        return comparaProporcionAreaPiso;
    }

    public void setComparaProporcionAreaPiso(Double comparaProporcionAreaPiso) {
        this.comparaProporcionAreaPiso = comparaProporcionAreaPiso;
    }

    public Long getEspaciosMuro() {
        return espaciosMuro;
    }

    public void setEspaciosMuro(Long espaciosMuro) {
        this.espaciosMuro = espaciosMuro;
    }

    public Long getComparaEspaciosMuro() {
        return comparaEspaciosMuro;
    }

    public void setComparaEspaciosMuro(Long comparaEspaciosMuro) {
        this.comparaEspaciosMuro = comparaEspaciosMuro;
    }

    public Double getAreaMuro() {
        return areaMuro;
    }

    public void setAreaMuro(Double areaMuro) {
        this.areaMuro = areaMuro;
    }

    public Double getComparaAreaMuro() {
        return comparaAreaMuro;
    }

    public void setComparaAreaMuro(Double comparaAreaMuro) {
        this.comparaAreaMuro = comparaAreaMuro;
    }

    public Double getProporcionAreaMuro() {
        return proporcionAreaMuro;
    }

    public void setProporcionAreaMuro(Double proporcionAreaMuro) {
        this.proporcionAreaMuro = proporcionAreaMuro;
    }

    public Double getComparaProporcionAreaMuro() {
        return comparaProporcionAreaMuro;
    }

    public void setComparaProporcionAreaMuro(Double comparaProporcionAreaMuro) {
        this.comparaProporcionAreaMuro = comparaProporcionAreaMuro;
    }

    public Long getEspaciosAcabadoMuro() {
        return espaciosAcabadoMuro;
    }

    public void setEspaciosAcabadoMuro(Long espaciosAcabadoMuro) {
        this.espaciosAcabadoMuro = espaciosAcabadoMuro;
    }

    public Long getComparaEspaciosAcabadoMuro() {
        return comparaEspaciosAcabadoMuro;
    }

    public void setComparaEspaciosAcabadoMuro(Long comparaEspaciosAcabadoMuro) {
        this.comparaEspaciosAcabadoMuro = comparaEspaciosAcabadoMuro;
    }

    public Double getAreaAcabadoMuro() {
        return areaAcabadoMuro;
    }

    public void setAreaAcabadoMuro(Double areaAcabadoMuro) {
        this.areaAcabadoMuro = areaAcabadoMuro;
    }

    public Double getComparaAreaAcabadoMuro() {
        return comparaAreaAcabadoMuro;
    }

    public void setComparaAreaAcabadoMuro(Double comparaAreaAcabadoMuro) {
        this.comparaAreaAcabadoMuro = comparaAreaAcabadoMuro;
    }

    public Double getProporcionAreaAcabadoMuro() {
        return proporcionAreaAcabadoMuro;
    }

    public void setProporcionAreaAcabadoMuro(Double proporcionAreaAcabadoMuro) {
        this.proporcionAreaAcabadoMuro = proporcionAreaAcabadoMuro;
    }

    public Double getComparaProporcionAreaAcabadoMuro() {
        return comparaProporcionAreaAcabadoMuro;
    }

    public void setComparaProporcionAreaAcabadoMuro(Double comparaProporcionAreaAcabadoMuro) {
        this.comparaProporcionAreaAcabadoMuro = comparaProporcionAreaAcabadoMuro;
    }

    public Long getEspaciosVentana() {
        return espaciosVentana;
    }

    public void setEspaciosVentana(Long espaciosVentana) {
        this.espaciosVentana = espaciosVentana;
    }

    public Long getComparaEspaciosVentana() {
        return comparaEspaciosVentana;
    }

    public void setComparaEspaciosVentana(Long comparaEspaciosVentana) {
        this.comparaEspaciosVentana = comparaEspaciosVentana;
    }

    public Double getProporcionVentana() {
        return proporcionVentana;
    }

    public void setProporcionVentana(Double proporcionVentana) {
        this.proporcionVentana = proporcionVentana;
    }

    public Double getComparaProporcionVentana() {
        return comparaProporcionVentana;
    }

    public void setComparaProporcionVentana(Double comparaProporcionVentana) {
        this.comparaProporcionVentana = comparaProporcionVentana;
    }

    public Long getEspaciosPuerta() {
        return espaciosPuerta;
    }

    public void setEspaciosPuerta(Long espaciosPuerta) {
        this.espaciosPuerta = espaciosPuerta;
    }

    public Long getComparaEspaciosPuerta() {
        return comparaEspaciosPuerta;
    }

    public void setComparaEspaciosPuerta(Long comparaEspaciosPuerta) {
        this.comparaEspaciosPuerta = comparaEspaciosPuerta;
    }

    public Double getProporcionPuerta() {
        return proporcionPuerta;
    }

    public void setProporcionPuerta(Double proporcionPuerta) {
        this.proporcionPuerta = proporcionPuerta;
    }

    public Double getComparaProporcionPuerta() {
        return comparaProporcionPuerta;
    }

    public void setComparaProporcionPuerta(Double comparaProporcionPuerta) {
        this.comparaProporcionPuerta = comparaProporcionPuerta;
    }

    public Long getEspaciosTecho() {
        return espaciosTecho;
    }

    public void setEspaciosTecho(Long espaciosTecho) {
        this.espaciosTecho = espaciosTecho;
    }

    public Long getComparaEspaciosTecho() {
        return comparaEspaciosTecho;
    }

    public void setComparaEspaciosTecho(Long comparaEspaciosTecho) {
        this.comparaEspaciosTecho = comparaEspaciosTecho;
    }

    public Double getAreaTecho() {
        return areaTecho;
    }

    public void setAreaTecho(Double areaTecho) {
        this.areaTecho = areaTecho;
    }

    public Double getComparaAreaTecho() {
        return comparaAreaTecho;
    }

    public void setComparaAreaTecho(Double comparaAreaTecho) {
        this.comparaAreaTecho = comparaAreaTecho;
    }

    public Double getProporcionAreaTecho() {
        return proporcionAreaTecho;
    }

    public void setProporcionAreaTecho(Double proporcionAreaTecho) {
        this.proporcionAreaTecho = proporcionAreaTecho;
    }

    public Double getComparaProporcionAreaTecho() {
        return comparaProporcionAreaTecho;
    }

    public void setComparaProporcionAreaTecho(Double comparaProporcionAreaTecho) {
        this.comparaProporcionAreaTecho = comparaProporcionAreaTecho;
    }

    public Double getAreaTotal() {
        return areaTotal;
    }

    public void setAreaTotal(Double areaTotal) {
        this.areaTotal = areaTotal;
    }

    public Double getComparaAreaTotal() {
        return comparaAreaTotal;
    }

    public void setComparaAreaTotal(Double comparaAreaTotal) {
        this.comparaAreaTotal = comparaAreaTotal;
    }
}
