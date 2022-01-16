/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.consulta.comparador;

/**
 *
 * @author cier
 */
public class RegistroAmbientes extends Registro {

    private Double relacionM2PorAlumno;
    private Double comparaRelacionM2PorAlumno;
    private Double deficitEstandar;
    private Double comparaDeficitEstandar;
    private Double acumuladoDeficit;
    private Double comparaAcumuladoDeficit;
    private Double cumplimientoNorma;
    private Double comparaCumplimientoNorma;
    private Double proporcionEspacios;
    private Double comparaProporcionEspacios;
    private Double proporcionPredios;
    private Double comparaProporcionPredios;
    private Double proporcionEstablecimientos;
    private Double comparaProporcionEstablecimientos;
    
    private String condicionM2alumno;
    private String comparaCondicionM2alumno;

    @Override
    public void inicializarTotales() {
        throw new UnsupportedOperationException("Operacion no soportada aun");
    }

    public Double getRelacionM2PorAlumno() {
        return relacionM2PorAlumno;
    }

    public void setRelacionM2PorAlumno(Double relacionM2PorAlumno) {
        this.relacionM2PorAlumno = relacionM2PorAlumno;
    }

    public Double getComparaRelacionM2PorAlumno() {
        return comparaRelacionM2PorAlumno;
    }

    public void setComparaRelacionM2PorAlumno(Double comparaRelacionM2PorAlumno) {
        this.comparaRelacionM2PorAlumno = comparaRelacionM2PorAlumno;
    }

    public Double getDeficitEstandar() {
        return deficitEstandar;
    }

    public void setDeficitEstandar(Double deficitEstandar) {
        this.deficitEstandar = deficitEstandar;
    }

    public Double getComparaDeficitEstandar() {
        return comparaDeficitEstandar;
    }

    public void setComparaDeficitEstandar(Double comparaDeficitEstandar) {
        this.comparaDeficitEstandar = comparaDeficitEstandar;
    }

    public Double getAcumuladoDeficit() {
        return acumuladoDeficit;
    }

    public void setAcumuladoDeficit(Double acumuladoDeficit) {
        this.acumuladoDeficit = acumuladoDeficit;
    }

    public Double getComparaAcumuladoDeficit() {
        return comparaAcumuladoDeficit;
    }

    public void setComparaAcumuladoDeficit(Double comparaAcumuladoDeficit) {
        this.comparaAcumuladoDeficit = comparaAcumuladoDeficit;
    }

    public Double getCumplimientoNorma() {
        return cumplimientoNorma;
    }

    public void setCumplimientoNorma(Double cumplimientoNorma) {
        this.cumplimientoNorma = cumplimientoNorma;
    }

    public Double getComparaCumplimientoNorma() {
        return comparaCumplimientoNorma;
    }

    public void setComparaCumplimientoNorma(Double comparaCumplimientoNorma) {
        this.comparaCumplimientoNorma = comparaCumplimientoNorma;
    }

    public Double getProporcionEspacios() {
        return proporcionEspacios;
    }

    public void setProporcionEspacios(Double proporcionEspacios) {
        this.proporcionEspacios = proporcionEspacios;
    }

    public Double getComparaProporcionEspacios() {
        return comparaProporcionEspacios;
    }

    public void setComparaProporcionEspacios(Double comparaProporcionEspacios) {
        this.comparaProporcionEspacios = comparaProporcionEspacios;
    }

    public Double getProporcionPredios() {
        return proporcionPredios;
    }

    public void setProporcionPredios(Double proporcionPredios) {
        this.proporcionPredios = proporcionPredios;
    }

    public Double getComparaProporcionPredios() {
        return comparaProporcionPredios;
    }

    public void setComparaProporcionPredios(Double comparaProporcionPredios) {
        this.comparaProporcionPredios = comparaProporcionPredios;
    }

    public Double getProporcionEstablecimientos() {
        return proporcionEstablecimientos;
    }

    public void setProporcionEstablecimientos(Double proporcionEstablecimientos) {
        this.proporcionEstablecimientos = proporcionEstablecimientos;
    }

    public Double getComparaProporcionEstablecimientos() {
        return comparaProporcionEstablecimientos;
    }

    public void setComparaProporcionEstablecimientos(Double comparaProporcionEstablecimientos) {
        this.comparaProporcionEstablecimientos = comparaProporcionEstablecimientos;
    }

    public String getCondicionM2alumno() {
        return condicionM2alumno;
    }

    public void setCondicionM2alumno(String condicionM2alumno) {
        this.condicionM2alumno = condicionM2alumno;
    }

    public String getComparaCondicionM2alumno() {
        return comparaCondicionM2alumno;
    }

    public void setComparaCondicionM2alumno(String comparaCondicionM2alumno) {
        this.comparaCondicionM2alumno = comparaCondicionM2alumno;
    }
    
}
