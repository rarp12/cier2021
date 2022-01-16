/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.filtro.digitacion.espacios;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author rjay1
 */
public class FiltroDigitacionEspacios implements Serializable {

    private Integer idNivel0;
    private Integer idNivel1;
    private Integer idNivel2;
    private Integer idNivel3;
    private Integer idNivel4;
    private Integer idNivel5;
    private String boletaCensal;
    private String codigoEstablecimiento;
    private String estadoBoleta;
    private String tipoAmbiente;
    private Map<String, Boolean> campos;

    public void inicializar() {
        idNivel0 = null;
        idNivel1 = null;
        idNivel2 = null;
        idNivel3 = null;
        idNivel4 = null;
        idNivel5 = null;
        boletaCensal = null;
        codigoEstablecimiento = null;
        estadoBoleta = null;
        campos = new HashMap<String, Boolean>();
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

    public String getBoletaCensal() {
        return boletaCensal;
    }

    public void setBoletaCensal(String boletaCensal) {
        this.boletaCensal = boletaCensal;
    }

    public String getCodigoEstablecimiento() {
        return codigoEstablecimiento;
    }

    public void setCodigoEstablecimiento(String codigoEstablecimiento) {
        this.codigoEstablecimiento = codigoEstablecimiento;
    }

    public String getEstadoBoleta() {
        return estadoBoleta;
    }

    public void setEstadoBoleta(String estadoBoleta) {
        this.estadoBoleta = estadoBoleta;
    }

    public String getTipoAmbiente() {
        return tipoAmbiente;
    }

    public void setTipoAmbiente(String tipoAmbiente) {
        this.tipoAmbiente = tipoAmbiente;
    }

    public Map<String, Boolean> getCampos() {
        return campos;
    }

    public void setCampos(Map<String, Boolean> campos) {
        this.campos = campos;
    }
}