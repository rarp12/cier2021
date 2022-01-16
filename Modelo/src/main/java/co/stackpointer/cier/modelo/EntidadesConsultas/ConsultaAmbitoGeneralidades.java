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

/**
 *
 * @author jguzmanf
 */
@Entity
@Table(name="CON_AMB_GENERALIDADES_IND")
@NamedQueries({
    @NamedQuery(name = "con_amb_generalidades_ind.consecutivoReporte", query = "SELECT C FROM ConsultaAmbitoGeneralidades C WHERE C.consecutivoReporte =:consecutivoReporte")
})
public class ConsultaAmbitoGeneralidades implements Serializable {

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
    @Column(name = "NUMERO_JORNADAS")
    private Long numJornadasEstudiantes;
    @Column(name = "NIVEL_ENSEÑANZA")
    private String nivelEnseñanza;
    @Column(name = "NUM_EST_NIVEL_ENSEÑANZA")
    private Long numEstNivelEnseñanza;
    @Column(name = "TOTAL_ESTUDIANTES")
    private Long totalEstudiantes;
    @Column(name = "TOTAL_AREA")
    private Double areaConstruccion;
    @Column(name = "AREA_PREDIO")
    private Double areaRealPredio;
    @Column(name = "TOTAL_ESTABLECIMIENTOS")
    private Long numEstablecimientos;
    @Column(name = "TOTAL_SEDES")
    private Long numSedes;
    @Column(name = "TOTAL_PREDIOS")
    private Long numPredios;

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

    public String getDirPredio() {
        return dirPredio;
    }

    public void setDirPredio(String dirPredio) {
        this.dirPredio = dirPredio;
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

    public Long getNumJornadasEstudiantes() {
        return numJornadasEstudiantes;
    }

    public void setNumJornadasEstudiantes(Long numJornadasEstudiantes) {
        this.numJornadasEstudiantes = numJornadasEstudiantes;
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

    public Double getAreaConstruccion() {
        return areaConstruccion;
    }

    public void setAreaConstruccion(Double areaConstruccion) {
        this.areaConstruccion = areaConstruccion;
    }

    public Double getAreaRealPredio() {
        return areaRealPredio;
    }

    public void setAreaRealPredio(Double areaRealPredio) {
        this.areaRealPredio = areaRealPredio;
    }

    public Long getNumEstablecimientos() {
        return numEstablecimientos;
    }

    public void setNumEstablecimientos(Long numEstablecimientos) {
        this.numEstablecimientos = numEstablecimientos;
    }

    public Long getNumSedes() {
        return numSedes;
    }

    public void setNumSedes(Long numSedes) {
        this.numSedes = numSedes;
    }

    public Long getNumPredios() {
        return numPredios;
    }

    public void setNumPredios(Long numPredios) {
        this.numPredios = numPredios;
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

}