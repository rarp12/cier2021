/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.consulta.comparador;

/**
 *
 * @author cier
 */
public class RegistroGeneralidades extends Registro {

    private Long numJornadasEstudiantes;
    private Long comparaNumJornadasEstudiantes;
    private String nivelEnseñanza;
    private Long numEstNivelEnseñanza;
    private Long totalEstudiantes;
    private Long comparaTotalEstudiantes;
    private Double areaConstruccion;
    private Double comparaAreaConstruccion;
    private Double areaRealPredio;
    private Double comparaAreaRealPredio;
    private Long numEstablecimientos;
    private Long comparaNumEstablecimientos;
    private Long numSedes;
    private Long comparaNumSedes;
    private Long numPredios;
    private Long comparaNumPredios;

    @Override
    public void inicializarTotales() {
        numJornadasEstudiantes = 0L;
        comparaNumJornadasEstudiantes = 0L;
        numEstNivelEnseñanza = 0L;
        totalEstudiantes = 0L;
        comparaTotalEstudiantes = 0L;
        areaConstruccion = 0D;
        comparaAreaConstruccion = 0D;
        areaRealPredio = 0D;
        comparaAreaRealPredio = 0D;
        numEstablecimientos = 0L;
        comparaNumEstablecimientos = 0L;
        numSedes = 0L;
        comparaNumSedes = 0L;
        numPredios = 0L;
        comparaNumPredios = 0L;
    }

    public Long getNumJornadasEstudiantes() {
        return numJornadasEstudiantes;
    }

    public void setNumJornadasEstudiantes(Long numJornadasEstudiantes) {
        this.numJornadasEstudiantes = numJornadasEstudiantes;
    }

    public Long getComparaNumJornadasEstudiantes() {
        return comparaNumJornadasEstudiantes;
    }

    public void setComparaNumJornadasEstudiantes(Long comparaNumJornadasEstudiantes) {
        this.comparaNumJornadasEstudiantes = comparaNumJornadasEstudiantes;
    }

    public String getNivelEnseñanza() {
        return nivelEnseñanza;
    }

    public void setNivelEnseñanza(String nivelEnseñanza) {
        this.nivelEnseñanza = nivelEnseñanza;
    }

    public Long getNumEstNivelEnseñanza() {
        return numEstNivelEnseñanza;
    }

    public void setNumEstNivelEnseñanza(Long numEstNivelEnseñanza) {
        this.numEstNivelEnseñanza = numEstNivelEnseñanza;
    }

    public Long getTotalEstudiantes() {
        return totalEstudiantes;
    }

    public void setTotalEstudiantes(Long totalEstudiantes) {
        this.totalEstudiantes = totalEstudiantes;
    }

    public Long getComparaTotalEstudiantes() {
        return comparaTotalEstudiantes;
    }

    public void setComparaTotalEstudiantes(Long comparaTotalEstudiantes) {
        this.comparaTotalEstudiantes = comparaTotalEstudiantes;
    }

    public Double getAreaConstruccion() {
        return areaConstruccion;
    }

    public void setAreaConstruccion(Double areaConstruccion) {
        this.areaConstruccion = areaConstruccion;
    }

    public Double getComparaAreaConstruccion() {
        return comparaAreaConstruccion;
    }

    public void setComparaAreaConstruccion(Double comparaAreaConstruccion) {
        this.comparaAreaConstruccion = comparaAreaConstruccion;
    }

    public Double getAreaRealPredio() {
        return areaRealPredio;
    }

    public void setAreaRealPredio(Double areaRealPredio) {
        this.areaRealPredio = areaRealPredio;
    }

    public Double getComparaAreaRealPredio() {
        return comparaAreaRealPredio;
    }

    public void setComparaAreaRealPredio(Double comparaAreaRealPredio) {
        this.comparaAreaRealPredio = comparaAreaRealPredio;
    }
    
    public Long getNumEstablecimientos() {
        return numEstablecimientos;
    }

    public void setNumEstablecimientos(Long numEstablecimientos) {
        this.numEstablecimientos = numEstablecimientos;
    }

    public Long getComparaNumEstablecimientos() {
        return comparaNumEstablecimientos;
    }

    public void setComparaNumEstablecimientos(Long comparaNumEstablecimientos) {
        this.comparaNumEstablecimientos = comparaNumEstablecimientos;
    }

    public Long getNumSedes() {
        return numSedes;
    }

    public void setNumSedes(Long numSedes) {
        this.numSedes = numSedes;
    }

    public Long getComparaNumSedes() {
        return comparaNumSedes;
    }

    public void setComparaNumSedes(Long comparaNumSedes) {
        this.comparaNumSedes = comparaNumSedes;
    }

    public Long getNumPredios() {
        return numPredios;
    }

    public void setNumPredios(Long numPredios) {
        this.numPredios = numPredios;
    }

    public Long getComparaNumPredios() {
        return comparaNumPredios;
    }

    public void setComparaNumPredios(Long comparaNumPredios) {
        this.comparaNumPredios = comparaNumPredios;
    }
}
