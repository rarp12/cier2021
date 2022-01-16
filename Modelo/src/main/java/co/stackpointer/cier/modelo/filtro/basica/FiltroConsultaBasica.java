/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.filtro.basica;

/**
 *
 * @author nruiz
 */
public class FiltroConsultaBasica {
    protected String boletaCensal;
    protected String codEstablecimiento;
    protected String estado;
    protected Integer periodoCreacion;
    protected Long idNivel0;
    protected Long idNivel1;
    protected Long idNivel2;
    protected Long idNivel3;
    protected Long idNivel4;
    protected Long idNivel5;
    protected Long idEstablecimiento;
    protected Long idSede;
    protected Long idPredio;
    protected String origen;

    public void init() {
        boletaCensal = null;
        codEstablecimiento = null;
        estado = null;
        periodoCreacion = null;
        idNivel0 = null;
        idNivel1 = null;
        idNivel2 = null;
        idNivel3 = null;
        idNivel4 = null;
        idNivel5 = null;
        idEstablecimiento = null;
        idSede = null;
        idPredio = null;
        origen = null;
    }

    public String getBoletaCensal() {
        return boletaCensal;
    }

    public void setBoletaCensal(String boletaCensal) {
        this.boletaCensal = boletaCensal;
    }

    public String getCodEstablecimiento() {
        return codEstablecimiento;
    }

    public void setCodEstablecimiento(String codEstablecimiento) {
        this.codEstablecimiento = codEstablecimiento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getPeriodoCreacion() {
        return periodoCreacion;
    }

    public void setPeriodoCreacion(Integer periodoCreacion) {
        this.periodoCreacion = periodoCreacion;
    }

    public Long getIdNivel0() {
        return idNivel0;
    }

    public void setIdNivel0(Long idNivel0) {
        this.idNivel0 = idNivel0;
    }

    public Long getIdNivel1() {
        return idNivel1;
    }

    public void setIdNivel1(Long idNivel1) {
        this.idNivel1 = idNivel1;
    }

    public Long getIdNivel2() {
        return idNivel2;
    }

    public void setIdNivel2(Long idNivel2) {
        this.idNivel2 = idNivel2;
    }

    public Long getIdNivel3() {
        return idNivel3;
    }

    public void setIdNivel3(Long idNivel3) {
        this.idNivel3 = idNivel3;
    }

    public Long getIdNivel4() {
        return idNivel4;
    }

    public void setIdNivel4(Long idNivel4) {
        this.idNivel4 = idNivel4;
    }

    public Long getIdNivel5() {
        return idNivel5;
    }

    public void setIdNivel5(Long idNivel5) {
        this.idNivel5 = idNivel5;
    }

    public Long getIdEstablecimiento() {
        return idEstablecimiento;
    }

    public void setIdEstablecimiento(Long idEstablecimiento) {
        this.idEstablecimiento = idEstablecimiento;
    }

    public Long getIdSede() {
        return idSede;
    }

    public void setIdSede(Long idSede) {
        this.idSede = idSede;
    }

    public Long getIdPredio() {
        return idPredio;
    }

    public void setIdPredio(Long idPredio) {
        this.idPredio = idPredio;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    
    
}
