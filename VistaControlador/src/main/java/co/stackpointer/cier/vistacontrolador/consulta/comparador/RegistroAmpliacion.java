/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.consulta.comparador;

/**
 *
 * @author cier
 */
public class RegistroAmpliacion extends Registro {

    private Double areaRealPredio;
    private Double estandarPredio;
    private Double estandarLote;
    private Double cumpNormaPredio;
    private Double cuposPredio;
    private Double m2RequeridosPredio;
    private Double areaConstruccionTotal;
    private Double estandarConstruccionTotal;
    private Double estandarConst;
    private Double cumpNormaConstruccionTotal;
    private Double m2RequeridosConstruccionTotal;
    private Double areaAula;
    private Long numeroAula;
    private Double estandarAula;
    private Double cumpNormaAula;
    private Double m2RequeridosAula;
    private Double espaciosRequeridosAula;
    private Double areaConstruccionPrimerPiso;
    private Double posConstruccionPrimerPiso;
    private Double posConstruccionAltura;
    private Double posConstruccionTotal;
    private Double m2AmpliarCobertura;
    private Long alumnosNuevosInfraestructura;
    //COMPARA
    private Double comparaAreaRealPredio;
    private Double comparaEstandarPredio;
    private Double comparaEstandarLote;
    private Double comparaCumpNormaPredio;
    private Double comparaCuposPredio;
    private Double comparaM2RequeridosPredio;
    private Double comparaAreaConstruccionTotal;
    private Double comparaEstandarConstruccionTotal;
    private Double comparaEstandarConst;
    private Double comparaCumpNormaConstruccionTotal;
    private Double comparaM2RequeridosConstruccionTotal;
    private Double comparaAreaAula;
    private Long comparaNumeroAula;
    private Double comparaEstandarAula;
    private Double comparaCumpNormaAula;
    private Double comparaM2RequeridosAula;
    private Double comparaEspaciosRequeridosAula;
    private Double comparaAreaConstruccionPrimerPiso;
    private Double comparaPosConstruccionPrimerPiso;
    private Double comparaPosConstruccionAltura;
    private Double comparaPosConstruccionTotal;
    private Double comparaM2AmpliarCobertura;
    private Long comparaAlumnosNuevosInfraestructura;

    @Override
    public void inicializarTotales() {
        this.setAreaRealPredio(0D);
        this.setEstandarPredio(0D);
        this.setCumpNormaPredio(0D);
        this.setCuposPredio(0D);
        this.setM2RequeridosPredio(0D);
        this.setComparaAreaRealPredio(0D);
        this.setComparaEstandarPredio(0D);
        this.setComparaCumpNormaPredio(0D);
        this.setComparaCuposPredio(0D);
        this.setComparaM2RequeridosPredio(0D);
        //construccion
        this.setComparaAreaConstruccionTotal(0D);
        this.setComparaEstandarConstruccionTotal(0D);
        this.setComparaCumpNormaConstruccionTotal(0D);
        this.setComparaM2RequeridosConstruccionTotal(0D);
        this.setComparaAreaConstruccionTotal(0D);
        this.setComparaEstandarConstruccionTotal(0D);
        this.setComparaCumpNormaConstruccionTotal(0D);
        this.setComparaM2RequeridosConstruccionTotal(0D);
        // aula
        this.setAreaAula(0D);
        this.setNumeroAula(0L);
        this.setEstandarAula(0D);
        this.setCumpNormaAula(0D);
        this.setM2RequeridosAula(0D);
        this.setEspaciosRequeridosAula(0D);
        this.setComparaAreaAula(0D);
        this.setComparaNumeroAula(0L);
        this.setComparaEstandarAula(0D);
        this.setComparaCumpNormaAula(0D);
        this.setComparaM2RequeridosAula(0D);
        this.setComparaEspaciosRequeridosAula(0D);
        // infraestrutura construccion
        this.setAreaConstruccionPrimerPiso(0D);
        this.setPosConstruccionPrimerPiso(0D);
        this.setPosConstruccionAltura(0D);
        this.setPosConstruccionTotal(0D);
        this.setM2AmpliarCobertura(0D);
        this.setAlumnosNuevosInfraestructura(0L);
        this.setComparaAreaConstruccionPrimerPiso(0D);
        this.setComparaPosConstruccionPrimerPiso(0D);
        this.setComparaPosConstruccionAltura(0D);
        this.setComparaPosConstruccionTotal(0D);
        this.setComparaM2AmpliarCobertura(0D);
        this.setComparaAlumnosNuevosInfraestructura(0L);
    }

    public Double getAreaRealPredio() {
        return areaRealPredio;
    }

    public void setAreaRealPredio(Double areaRealPredio) {
        this.areaRealPredio = areaRealPredio;
    }

    public Double getEstandarPredio() {
        return estandarPredio;
    }

    public void setEstandarPredio(Double estandarPredio) {
        this.estandarPredio = estandarPredio;
    }

    public Double getEstandarLote() {
        return estandarLote;
    }

    public void setEstandarLote(Double estandarLote) {
        this.estandarLote = estandarLote;
    }

    public Double getCumpNormaPredio() {
        return cumpNormaPredio;
    }

    public void setCumpNormaPredio(Double cumpNormaPredio) {
        this.cumpNormaPredio = cumpNormaPredio;
    }

    public Double getCuposPredio() {
        return cuposPredio;
    }

    public void setCuposPredio(Double cuposPredio) {
        this.cuposPredio = cuposPredio;
    }

    public Double getM2RequeridosPredio() {
        return m2RequeridosPredio;
    }

    public void setM2RequeridosPredio(Double m2RequeridosPredio) {
        this.m2RequeridosPredio = m2RequeridosPredio;
    }

    public Double getAreaConstruccionTotal() {
        return areaConstruccionTotal;
    }

    public void setAreaConstruccionTotal(Double areaConstruccionTotal) {
        this.areaConstruccionTotal = areaConstruccionTotal;
    }

    public Double getEstandarConstruccionTotal() {
        return estandarConstruccionTotal;
    }

    public void setEstandarConstruccionTotal(Double estandarConstruccionTotal) {
        this.estandarConstruccionTotal = estandarConstruccionTotal;
    }

    public Double getEstandarConst() {
        return estandarConst;
    }

    public void setEstandarConst(Double estandarConst) {
        this.estandarConst = estandarConst;
    }

    public Double getCumpNormaConstruccionTotal() {
        return cumpNormaConstruccionTotal;
    }

    public void setCumpNormaConstruccionTotal(Double cumpNormaConstruccionTotal) {
        this.cumpNormaConstruccionTotal = cumpNormaConstruccionTotal;
    }

    public Double getM2RequeridosConstruccionTotal() {
        return m2RequeridosConstruccionTotal;
    }

    public void setM2RequeridosConstruccionTotal(Double m2RequeridosConstruccionTotal) {
        this.m2RequeridosConstruccionTotal = m2RequeridosConstruccionTotal;
    }

    public Double getAreaAula() {
        return areaAula;
    }

    public void setAreaAula(Double areaAula) {
        this.areaAula = areaAula;
    }

    public Long getNumeroAula() {
        return numeroAula;
    }

    public void setNumeroAula(Long numeroAula) {
        this.numeroAula = numeroAula;
    }

    public Double getEstandarAula() {
        return estandarAula;
    }

    public void setEstandarAula(Double estandarAula) {
        this.estandarAula = estandarAula;
    }

    public Double getCumpNormaAula() {
        return cumpNormaAula;
    }

    public void setCumpNormaAula(Double cumpNormaAula) {
        this.cumpNormaAula = cumpNormaAula;
    }

    public Double getM2RequeridosAula() {
        return m2RequeridosAula;
    }

    public void setM2RequeridosAula(Double m2RequeridosAula) {
        this.m2RequeridosAula = m2RequeridosAula;
    }

    public Double getEspaciosRequeridosAula() {
        return espaciosRequeridosAula;
    }

    public void setEspaciosRequeridosAula(Double espaciosRequeridosAula) {
        this.espaciosRequeridosAula = espaciosRequeridosAula;
    }

    public Double getAreaConstruccionPrimerPiso() {
        return areaConstruccionPrimerPiso;
    }

    public void setAreaConstruccionPrimerPiso(Double areaConstruccionPrimerPiso) {
        this.areaConstruccionPrimerPiso = areaConstruccionPrimerPiso;
    }

    public Double getPosConstruccionPrimerPiso() {
        return posConstruccionPrimerPiso;
    }

    public void setPosConstruccionPrimerPiso(Double posConstruccionPrimerPiso) {
        this.posConstruccionPrimerPiso = posConstruccionPrimerPiso;
    }

    public Double getPosConstruccionAltura() {
        return posConstruccionAltura;
    }

    public void setPosConstruccionAltura(Double posConstruccionAltura) {
        this.posConstruccionAltura = posConstruccionAltura;
    }

    public Double getPosConstruccionTotal() {
        return posConstruccionTotal;
    }

    public void setPosConstruccionTotal(Double posConstruccionTotal) {
        this.posConstruccionTotal = posConstruccionTotal;
    }

    public Double getM2AmpliarCobertura() {
        return m2AmpliarCobertura;
    }

    public void setM2AmpliarCobertura(Double m2AmpliarCobertura) {
        this.m2AmpliarCobertura = m2AmpliarCobertura;
    }

    public Long getAlumnosNuevosInfraestructura() {
        return alumnosNuevosInfraestructura;
    }

    public void setAlumnosNuevosInfraestructura(Long alumnosNuevosInfraestructura) {
        this.alumnosNuevosInfraestructura = alumnosNuevosInfraestructura;
    }

    public Double getComparaAreaRealPredio() {
        return comparaAreaRealPredio;
    }

    public void setComparaAreaRealPredio(Double comparaAreaRealPredio) {
        this.comparaAreaRealPredio = comparaAreaRealPredio;
    }

    public Double getComparaEstandarPredio() {
        return comparaEstandarPredio;
    }

    public void setComparaEstandarPredio(Double comparaEstandarPredio) {
        this.comparaEstandarPredio = comparaEstandarPredio;
    }

    public Double getComparaEstandarLote() {
        return comparaEstandarLote;
    }

    public void setComparaEstandarLote(Double comparaEstandarLote) {
        this.comparaEstandarLote = comparaEstandarLote;
    }

    public Double getComparaCumpNormaPredio() {
        return comparaCumpNormaPredio;
    }

    public void setComparaCumpNormaPredio(Double comparaCumpNormaPredio) {
        this.comparaCumpNormaPredio = comparaCumpNormaPredio;
    }

    public Double getComparaCuposPredio() {
        return comparaCuposPredio;
    }

    public void setComparaCuposPredio(Double comparaCuposPredio) {
        this.comparaCuposPredio = comparaCuposPredio;
    }

    public Double getComparaM2RequeridosPredio() {
        return comparaM2RequeridosPredio;
    }

    public void setComparaM2RequeridosPredio(Double comparaM2RequeridosPredio) {
        this.comparaM2RequeridosPredio = comparaM2RequeridosPredio;
    }

    public Double getComparaAreaConstruccionTotal() {
        return comparaAreaConstruccionTotal;
    }

    public void setComparaAreaConstruccionTotal(Double comparaAreaConstruccionTotal) {
        this.comparaAreaConstruccionTotal = comparaAreaConstruccionTotal;
    }

    public Double getComparaEstandarConstruccionTotal() {
        return comparaEstandarConstruccionTotal;
    }

    public void setComparaEstandarConstruccionTotal(Double comparaEstandarConstruccionTotal) {
        this.comparaEstandarConstruccionTotal = comparaEstandarConstruccionTotal;
    }

    public Double getComparaEstandarConst() {
        return comparaEstandarConst;
    }

    public void setComparaEstandarConst(Double comparaEstandarConst) {
        this.comparaEstandarConst = comparaEstandarConst;
    }

    public Double getComparaCumpNormaConstruccionTotal() {
        return comparaCumpNormaConstruccionTotal;
    }

    public void setComparaCumpNormaConstruccionTotal(Double comparaCumpNormaConstruccionTotal) {
        this.comparaCumpNormaConstruccionTotal = comparaCumpNormaConstruccionTotal;
    }

    public Double getComparaM2RequeridosConstruccionTotal() {
        return comparaM2RequeridosConstruccionTotal;
    }

    public void setComparaM2RequeridosConstruccionTotal(Double comparaM2RequeridosConstruccionTotal) {
        this.comparaM2RequeridosConstruccionTotal = comparaM2RequeridosConstruccionTotal;
    }

    public Double getComparaAreaAula() {
        return comparaAreaAula;
    }

    public void setComparaAreaAula(Double comparaAreaAula) {
        this.comparaAreaAula = comparaAreaAula;
    }

    public Long getComparaNumeroAula() {
        return comparaNumeroAula;
    }

    public void setComparaNumeroAula(Long comparaNumeroAula) {
        this.comparaNumeroAula = comparaNumeroAula;
    }

    public Double getComparaEstandarAula() {
        return comparaEstandarAula;
    }

    public void setComparaEstandarAula(Double comparaEstandarAula) {
        this.comparaEstandarAula = comparaEstandarAula;
    }

    public Double getComparaCumpNormaAula() {
        return comparaCumpNormaAula;
    }

    public void setComparaCumpNormaAula(Double comparaCumpNormaAula) {
        this.comparaCumpNormaAula = comparaCumpNormaAula;
    }

    public Double getComparaM2RequeridosAula() {
        return comparaM2RequeridosAula;
    }

    public void setComparaM2RequeridosAula(Double comparaM2RequeridosAula) {
        this.comparaM2RequeridosAula = comparaM2RequeridosAula;
    }

    public Double getComparaEspaciosRequeridosAula() {
        return comparaEspaciosRequeridosAula;
    }

    public void setComparaEspaciosRequeridosAula(Double comparaEspaciosRequeridosAula) {
        this.comparaEspaciosRequeridosAula = comparaEspaciosRequeridosAula;
    }

    public Double getComparaAreaConstruccionPrimerPiso() {
        return comparaAreaConstruccionPrimerPiso;
    }

    public void setComparaAreaConstruccionPrimerPiso(Double comparaAreaConstruccionPrimerPiso) {
        this.comparaAreaConstruccionPrimerPiso = comparaAreaConstruccionPrimerPiso;
    }

    public Double getComparaPosConstruccionPrimerPiso() {
        return comparaPosConstruccionPrimerPiso;
    }

    public void setComparaPosConstruccionPrimerPiso(Double comparaPosConstruccionPrimerPiso) {
        this.comparaPosConstruccionPrimerPiso = comparaPosConstruccionPrimerPiso;
    }

    public Double getComparaPosConstruccionAltura() {
        return comparaPosConstruccionAltura;
    }

    public void setComparaPosConstruccionAltura(Double comparaPosConstruccionAltura) {
        this.comparaPosConstruccionAltura = comparaPosConstruccionAltura;
    }

    public Double getComparaPosConstruccionTotal() {
        return comparaPosConstruccionTotal;
    }

    public void setComparaPosConstruccionTotal(Double comparaPosConstruccionTotal) {
        this.comparaPosConstruccionTotal = comparaPosConstruccionTotal;
    }

    public Double getComparaM2AmpliarCobertura() {
        return comparaM2AmpliarCobertura;
    }

    public void setComparaM2AmpliarCobertura(Double comparaM2AmpliarCobertura) {
        this.comparaM2AmpliarCobertura = comparaM2AmpliarCobertura;
    }

    public Long getComparaAlumnosNuevosInfraestructura() {
        return comparaAlumnosNuevosInfraestructura;
    }

    public void setComparaAlumnosNuevosInfraestructura(Long comparaAlumnosNuevosInfraestructura) {
        this.comparaAlumnosNuevosInfraestructura = comparaAlumnosNuevosInfraestructura;
    }
}
