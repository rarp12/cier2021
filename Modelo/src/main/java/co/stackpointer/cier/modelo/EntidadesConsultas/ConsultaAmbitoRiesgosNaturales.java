/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.EntidadesConsultas;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jguzmanf
 */
@Entity
@Table(name = "CON_AMB_RSGOS_NTRLES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ConsultaAmbitoRiesgosNaturales.findByConsecutivoReporte", query = "SELECT c FROM ConsultaAmbitoRiesgosNaturales c WHERE c.consecutivoReporte = :consecutivoReporte")
})
public class ConsultaAmbitoRiesgosNaturales implements Serializable{

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CONSECUTIVO")
    private Long consecutivo;
    @Column(name = "CONSECUTIVO_REPORTE")
    private Long consecutivoReporte;
    @Column(name = "NOMBRE_NIVEL0")
    private String nivel0;
    @Column(name = "NOMBRE_NIVEL1")
    private String nivel1;
    @Column(name = "NOMBRE_NIVEL2")
    private String nivel2;
    @Column(name = "NOMBRE_NIVEL3")
    private String nivel3;
    @Column(name = "NOMBRE_NIVEL4")
    private String nivel4;
    @Column(name = "NOMBRE_NIVEL5")
    private String nivel5;
    @Column(name = "CODIGO_NIVEL1")
    private String codigoNivel1;
    @Column(name = "CODIGO_NIVEL2")
    private String codigoNivel2;
    @Column(name = "CODIGO_NIVEL3")
    private String codigoNivel3;
    @Column(name = "CODIGO_NIVEL4")
    private String codigoNivel4;
    @Column(name = "CODIGO_NIVEL5")
    private String codigoNivel5;
    @Column(name = "CODIGO_ESTABLECIMIENTO")
    private String codEstablecimiento;
    @Column(name = "NOMBRE_ESTABLECIMIENTO")
    private String nomEstablecimiento;
    @Column(name = "CODIGO_SEDE")
    private String codSede;
    @Column(name = "NOMBRE_SEDE")
    private String nomSede;
    @Column(name = "CODIGO_PREDIO")
    private String codPredio;
    @Column(name = "NOMBRE_PREDIO")
    private String nomPredio;
    @Column(name = "TIPO_RIESGO1")
    private String tipoRiesgo1;
    @Column(name = "CONDICION_RIESGO1")
    private String condiconRiesgo1;
    @Column(name = "TIPO_RIESGO2")
    private String tipoRiesgo2;
    @Column(name = "CONDICION_RIESGO2")
    private String condiconRiesgo2;
    @Column(name = "TIPO_RIESGO3")
    private String tipoRiesgo3;
    @Column(name = "CONDICION_RIESGO3")
    private String condiconRiesgo3;
    @Column(name = "CONDICION_RIESGO")
    private String condiconRiesgo;
    @Column(name = "DIRECCION_PREDIO")
    private String dirPredio;
    @Column(name = "NOMBRE_ZONA")
    private String zona;
    @Column(name = "NOMBRE_SECTOR")
    private String sector;
    @Column(name = "NOMBRE_CLIMA")
    private String clima;
    @Column(name = "ETNIAS")
    private String etnias;
    @Column(name = "PROPIEDAD_PREDIO")
    private String propiedadPredio;
    @Column(name = "ACCESIBILIDAD")
    private String condAccesibilidad;
    @Column(name = "POBLACION_AFECTADA")
    private Long poblacionAfectada;
    @Column(name = "POBLACION_TOTAL")
    private Long poblacionTotal;
    @Column(name = "TOTAL_PREDIOS_RIESGO")
    private Long numPrediosRiesgo;
    @Column(name = "TOTAL_PREDIOS_VALOR")
    private Long totalPredios;
    @Column(name = "PORCION_CONDICION")
    private Double porcionCondicion;

    public ConsultaAmbitoRiesgosNaturales() {
    }

    public Long getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(Long consecutivo) {
        this.consecutivo = consecutivo;
    }

    public Long getConsecutivoReporte() {
        return consecutivoReporte;
    }

    public void setConsecutivoReporte(Long consecutivoReporte) {
        this.consecutivoReporte = consecutivoReporte;
    }

    public String getNivel0() {
        return nivel0;
    }

    public void setNivel0(String nivel0) {
        this.nivel0 = nivel0;
    }

    public String getNivel1() {
        return nivel1;
    }

    public void setNivel1(String nivel1) {
        this.nivel1 = nivel1;
    }

    public String getNivel2() {
        return nivel2;
    }

    public void setNivel2(String nivel2) {
        this.nivel2 = nivel2;
    }

    public String getNivel3() {
        return nivel3;
    }

    public void setNivel3(String nivel3) {
        this.nivel3 = nivel3;
    }

    public String getNivel4() {
        return nivel4;
    }

    public void setNivel4(String nivel4) {
        this.nivel4 = nivel4;
    }

    public String getNivel5() {
        return nivel5;
    }

    public void setNivel5(String nivel5) {
        this.nivel5 = nivel5;
    }
    
     public String getCodigoNivel1() {
        return codigoNivel1;
    }

    public void setCodigoNivel1(String codigoNivel1) {
        this.codigoNivel1 = codigoNivel1;
    }

    public String getCodigoNivel2() {
        return codigoNivel2;
    }

    public void setCodigoNivel2(String codigoNivel2) {
        this.codigoNivel2 = codigoNivel2;
    }

    public String getCodigoNivel3() {
        return codigoNivel3;
    }

    public void setCodigoNivel3(String codigoNivel3) {
        this.codigoNivel3 = codigoNivel3;
    }

    public String getCodigoNivel4() {
        return codigoNivel4;
    }

    public void setCodigoNivel4(String codigoNivel4) {
        this.codigoNivel4 = codigoNivel4;
    }

    public String getCodigoNivel5() {
        return codigoNivel5;
    }

    public void setCodigoNivel5(String codigoNivel5) {
        this.codigoNivel5 = codigoNivel5;
    }

    public String getCodEstablecimiento() {
        return codEstablecimiento;
    }

    public void setCodEstablecimiento(String codEstablecimiento) {
        this.codEstablecimiento = codEstablecimiento;
    }

    public String getNomEstablecimiento() {
        return nomEstablecimiento;
    }

    public void setNomEstablecimiento(String nomEstablecimiento) {
        this.nomEstablecimiento = nomEstablecimiento;
    }

    public String getCodSede() {
        return codSede;
    }

    public void setCodSede(String codSede) {
        this.codSede = codSede;
    }

    public String getNomSede() {
        return nomSede;
    }

    public void setNomSede(String nomSede) {
        this.nomSede = nomSede;
    }

    public String getCodPredio() {
        return codPredio;
    }

    public void setCodPredio(String codPredio) {
        this.codPredio = codPredio;
    }

    public String getNomPredio() {
        return nomPredio;
    }

    public void setNomPredio(String nomPredio) {
        this.nomPredio = nomPredio;
    }

    public String getTipoRiesgo1() {
        return tipoRiesgo1;
    }

    public void setTipoRiesgo1(String tipoRiesgo1) {
        this.tipoRiesgo1 = tipoRiesgo1;
    }

    public String getCondiconRiesgo1() {
        return condiconRiesgo1;
    }

    public void setCondiconRiesgo1(String condiconRiesgo1) {
        this.condiconRiesgo1 = condiconRiesgo1;
    }

    public String getTipoRiesgo2() {
        return tipoRiesgo2;
    }

    public void setTipoRiesgo2(String tipoRiesgo2) {
        this.tipoRiesgo2 = tipoRiesgo2;
    }

    public String getCondiconRiesgo2() {
        return condiconRiesgo2;
    }

    public void setCondiconRiesgo2(String condiconRiesgo2) {
        this.condiconRiesgo2 = condiconRiesgo2;
    }

    public String getTipoRiesgo3() {
        return tipoRiesgo3;
    }

    public void setTipoRiesgo3(String tipoRiesgo3) {
        this.tipoRiesgo3 = tipoRiesgo3;
    }

    public String getCondiconRiesgo3() {
        return condiconRiesgo3;
    }

    public void setCondiconRiesgo3(String condiconRiesgo3) {
        this.condiconRiesgo3 = condiconRiesgo3;
    }

    public String getCondiconRiesgo() {
        return condiconRiesgo;
    }

    public void setCondiconRiesgo(String condiconRiesgo) {
        this.condiconRiesgo = condiconRiesgo;
    }

    public String getDirPredio() {
        return dirPredio;
    }

    public void setDirPredio(String dirPredio) {
        this.dirPredio = dirPredio;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getClima() {
        return clima;
    }

    public void setClima(String clima) {
        this.clima = clima;
    }

    public String getEtnias() {
        return etnias;
    }

    public void setEtnias(String etnias) {
        this.etnias = etnias;
    }

    public String getPropiedadPredio() {
        return propiedadPredio;
    }

    public void setPropiedadPredio(String propiedadPredio) {
        this.propiedadPredio = propiedadPredio;
    }

    public String getCondAccesibilidad() {
        return condAccesibilidad;
    }

    public void setCondAccesibilidad(String condAccesibilidad) {
        this.condAccesibilidad = condAccesibilidad;
    }

    public Long getPoblacionAfectada() {
        return poblacionAfectada;
    }

    public void setPoblacionAfectada(Long poblacionAfectada) {
        this.poblacionAfectada = poblacionAfectada;
    }

    public Long getPoblacionTotal() {
        return poblacionTotal;
    }

    public void setPoblacionTotal(Long poblacionTotal) {
        this.poblacionTotal = poblacionTotal;
    }

    public Long getNumPrediosRiesgo() {
        return numPrediosRiesgo;
    }

    public void setNumPrediosRiesgo(Long numPrediosRiesgo) {
        this.numPrediosRiesgo = numPrediosRiesgo;
    }

    public Long getTotalPredios() {
        return totalPredios;
    }

    public void setTotalPredios(Long totalPredios) {
        this.totalPredios = totalPredios;
    }

    public Double getPorcionCondicion() {
        return porcionCondicion;
    }

    public void setPorcionCondicion(Double porcionCondicion) {
        this.porcionCondicion = porcionCondicion;
    }
    
}
