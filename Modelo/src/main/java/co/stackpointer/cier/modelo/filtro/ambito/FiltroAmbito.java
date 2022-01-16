/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.filtro.ambito;

import co.stackpointer.cier.modelo.tipo.ParamNivelConsul;
import java.io.Serializable;

/**
 *
 * @author user
 */
public abstract class FiltroAmbito implements Serializable{
    
    protected Integer periodo;
    protected Long idioma;
    protected Long idNivel0;
    protected Long idNivel1;
    protected Long idNivel2;
    protected Long idNivel3;
    protected Long idNivel4;
    protected Long idNivel5;
    protected Long idEstablecimiento;
    protected Long idSede;
    protected Long idPredio; 
    
    protected String codZona;
    protected String codSector;   
    protected Long agrupamiento;
   
    
    protected void init() {
        periodo = null;
        idioma = null;
        idNivel0 = null;
        idNivel1 = null;
        idNivel2 = null;
        idNivel3 = null;
        idNivel4 = null;
        idNivel5 = null;
        idEstablecimiento = null;
        idSede = null;
        idPredio = null;
        codZona = null;
        codSector = null;       
        agrupamiento = null;
    }
    
    public abstract boolean isConsultaDeTiempoReal();

    public Integer getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Integer periodo) {
        this.periodo = periodo;
    }

    public Long getIdioma() {
        return idioma;
    }

    public void setIdioma(Long idioma) {
        this.idioma = idioma;
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

    public String getCodZona() {
        return codZona;
    }

    public void setCodZona(String codZona) {
        this.codZona = codZona;
    }

    public String getCodSector() {
        return codSector;
    }

    public void setCodSector(String codSector) {
        this.codSector = codSector;
    }          

    public Long getAgrupamiento() {
        return agrupamiento;
    }

    public void setAgrupamiento(Long agrupamiento) {
        this.agrupamiento = agrupamiento;
    }

    public boolean isAgrupamientoPredio() {
        return ParamNivelConsul.PREDIO.equals(agrupamiento);
    }

    public boolean isAgrupamientoSede() {
        return ParamNivelConsul.SEDE.equals(agrupamiento);
    }

    public boolean isAgrupamientoEstablecimiento() {
        return ParamNivelConsul.ESTABLECIMIENTO.equals(agrupamiento);
    }
    
    public boolean isAgrupamientoNivel0() {
        return ParamNivelConsul.CERO.equals(agrupamiento);
    }

    public boolean isAgrupamientoNivel1() {
        return ParamNivelConsul.UNO.equals(agrupamiento);
    }

    public boolean isAgrupamientoNivel2() {
        return ParamNivelConsul.DOS.equals(agrupamiento);
    }

    public boolean isAgrupamientoNivel3() {
        return ParamNivelConsul.TRES.equals(agrupamiento);
    }

    public boolean isAgrupamientoNivel4() {
        return ParamNivelConsul.CUATRO.equals(agrupamiento);
    }

    public boolean isAgrupamientoNivel5() {
        return ParamNivelConsul.CINCO.equals(agrupamiento);
    }
   
}
