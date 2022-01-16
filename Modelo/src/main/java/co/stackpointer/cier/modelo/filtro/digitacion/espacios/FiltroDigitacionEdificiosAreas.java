/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.filtro.digitacion.espacios;

/**
 *
 * @author rjay1
 */
public class FiltroDigitacionEdificiosAreas {

    private Integer idNivel0;
    private Integer idNivel1;
    private Integer idNivel2;
    private Integer idNivel3;
    private Integer idNivel4;
    private Integer idNivel5;
    private String establecimiento;
    private String sede;
    private String predio;
    private String codigoEstablecimiento;

    public void inicializar() {
        idNivel0 = null;
        idNivel1 = null;
        idNivel2 = null;
        idNivel3 = null;
        idNivel4 = null;
        idNivel5 = null;
        establecimiento = null;
        sede = null;
        predio = null;
        codigoEstablecimiento = null;
    }

    public Integer getIdNivel0() {
        return idNivel0;
    }

    public void setIdNivel0(Integer idNivel0) {
        this.idNivel0 = idNivel0;
    }

    public Integer getIdNivel1() {
        return idNivel1;
    }

    public void setIdNivel1(Integer idNivel1) {
        this.idNivel1 = idNivel1;
    }

    public Integer getIdNivel2() {
        return idNivel2;
    }

    public void setIdNivel2(Integer idNivel2) {
        this.idNivel2 = idNivel2;
    }

    public Integer getIdNivel3() {
        return idNivel3;
    }

    public void setIdNivel3(Integer idNivel3) {
        this.idNivel3 = idNivel3;
    }

    public Integer getIdNivel4() {
        return idNivel4;
    }

    public void setIdNivel4(Integer idNivel4) {
        this.idNivel4 = idNivel4;
    }

    public Integer getIdNivel5() {
        return idNivel5;
    }

    public void setIdNivel5(Integer idNivel5) {
        this.idNivel5 = idNivel5;
    }

    public String getEstablecimiento() {
        return establecimiento;
    }

    public void setEstablecimiento(String establecimiento) {
        this.establecimiento = establecimiento;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public String getPredio() {
        return predio;
    }

    public void setPredio(String predio) {
        this.predio = predio;
    }

    public String getCodigoEstablecimiento() {
        return codigoEstablecimiento;
    }

    public void setCodigoEstablecimiento(String codigoEstablecimiento) {
        this.codigoEstablecimiento = codigoEstablecimiento;
    }
}