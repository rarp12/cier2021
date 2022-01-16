/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.filtro.calificacion;

import co.stackpointer.cier.modelo.filtro.dinamica.*;
import java.util.List;


/**
 *
 * @author nruiz
 */
public class FiltroCalificacion {
    protected Integer periodo;
    protected Long idNivel0;
    protected Long idNivel1;
    protected Long idNivel2;
    protected Long idNivel3;
    protected Long idNivel4;
    protected Long idNivel5;
    
    protected String codZona;
    protected String codSector;
    
    protected Long nivelAgrupamiento;
    protected Long idEntidad;
    
    public void init() {
        periodo = null;
        idNivel0 = null;
        idNivel1 = null;
        idNivel2 = null;
        idNivel3 = null;
        idNivel4 = null;
        idNivel5 = null;
        codZona = null;
        codSector = null;
        nivelAgrupamiento=null;
        idEntidad=null;
    }

    public Integer getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Integer periodo) {
        this.periodo = periodo;
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

    public Long getNivelAgrupamiento() {
        return nivelAgrupamiento;
    }

    public void setNivelAgrupamiento(Long nivelAgrupamiento) {
        this.nivelAgrupamiento = nivelAgrupamiento;
    }

    public Long getIdEntidad() {
        return idEntidad;
    }

    public void setIdEntidad(Long idEntidad) {
        this.idEntidad = idEntidad;
    }

   
}
