/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.consulta.comparador;

/**
 *
 * @author cier
 */
public class RegistroEdificios extends Registro {

    //predio        
    private String condMaterialidadPredio;
    private String comparaCondMaterialidadPredio;
    private Long numEstudiantes;
    private Long comparaNumEstudiantes;
    //otros niveles
    private Long numPredCondMaterialidad;
    private Long comparaNumPredCondMaterialidad;
    private Long totalPredios;
    private Long comparaTotalPredios;
    private Double propPredCondMaterialidad;
    private Double comparaPropPredCondMaterialidad;
    private Long numMaterialesCondCriticaPredio;
    private Long comparaNumMaterialesCondCriticaPredio;
    private Double propMaterialesCondicionCritica;
    private Double comparaPropMaterialesCondicionCritica;

    @Override
    public void inicializarTotales() {
        numEstudiantes = 0L;
        comparaNumEstudiantes = 0L;
        totalPredios = 0L;
        comparaTotalPredios = 0L;
        numPredCondMaterialidad = 0L;
        comparaNumPredCondMaterialidad = 0L;
        numMaterialesCondCriticaPredio = 0L;
        comparaNumMaterialesCondCriticaPredio = 0L;
    }

    public String getCondMaterialidadPredio() {
        return condMaterialidadPredio;
    }

    public void setCondMaterialidadPredio(String condMaterialidadPredio) {
        this.condMaterialidadPredio = condMaterialidadPredio;
    }

    public String getComparaCondMaterialidadPredio() {
        return comparaCondMaterialidadPredio;
    }

    public void setComparaCondMaterialidadPredio(String comparaCondMaterialidadPredio) {
        this.comparaCondMaterialidadPredio = comparaCondMaterialidadPredio;
    }

    public Long getNumEstudiantes() {
        return numEstudiantes;
    }

    public void setNumEstudiantes(Long numEstudiantes) {
        this.numEstudiantes = numEstudiantes;
    }

    public Long getComparaNumEstudiantes() {
        return comparaNumEstudiantes;
    }

    public void setComparaNumEstudiantes(Long comparaNumEstudiantes) {
        this.comparaNumEstudiantes = comparaNumEstudiantes;
    }

    public Long getNumPredCondMaterialidad() {
        return numPredCondMaterialidad;
    }

    public void setNumPredCondMaterialidad(Long numPredCondMaterialidad) {
        this.numPredCondMaterialidad = numPredCondMaterialidad;
    }

    public Long getComparaNumPredCondMaterialidad() {
        return comparaNumPredCondMaterialidad;
    }

    public void setComparaNumPredCondMaterialidad(Long comparaNumPredCondMaterialidad) {
        this.comparaNumPredCondMaterialidad = comparaNumPredCondMaterialidad;
    }

    public Long getTotalPredios() {
        return totalPredios;
    }

    public void setTotalPredios(Long totalPredios) {
        this.totalPredios = totalPredios;
    }

    public Long getComparaTotalPredios() {
        return comparaTotalPredios;
    }

    public void setComparaTotalPredios(Long comparaTotalPredios) {
        this.comparaTotalPredios = comparaTotalPredios;
    }

    public Double getPropPredCondMaterialidad() {
        return propPredCondMaterialidad;
    }

    public void setPropPredCondMaterialidad(Double propPredCondMaterialidad) {
        this.propPredCondMaterialidad = propPredCondMaterialidad;
    }

    public Double getComparaPropPredCondMaterialidad() {
        return comparaPropPredCondMaterialidad;
    }

    public void setComparaPropPredCondMaterialidad(Double comparaPropPredCondMaterialidad) {
        this.comparaPropPredCondMaterialidad = comparaPropPredCondMaterialidad;
    }

    public Long getNumMaterialesCondCriticaPredio() {
        return numMaterialesCondCriticaPredio;
    }

    public void setNumMaterialesCondCriticaPredio(Long numMaterialesCondCriticaPredio) {
        this.numMaterialesCondCriticaPredio = numMaterialesCondCriticaPredio;
    }

    public Long getComparaNumMaterialesCondCriticaPredio() {
        return comparaNumMaterialesCondCriticaPredio;
    }

    public void setComparaNumMaterialesCondCriticaPredio(Long comparaNumMaterialesCondCriticaPredio) {
        this.comparaNumMaterialesCondCriticaPredio = comparaNumMaterialesCondCriticaPredio;
    }

    public Double getPropMaterialesCondicionCritica() {
        return propMaterialesCondicionCritica;
    }

    public void setPropMaterialesCondicionCritica(Double propMaterialesCondicionCritica) {
        this.propMaterialesCondicionCritica = propMaterialesCondicionCritica;
    }

    public Double getComparaPropMaterialesCondicionCritica() {
        return comparaPropMaterialesCondicionCritica;
    }

    public void setComparaPropMaterialesCondicionCritica(Double comparaPropMaterialesCondicionCritica) {
        this.comparaPropMaterialesCondicionCritica = comparaPropMaterialesCondicionCritica;
    }
}
