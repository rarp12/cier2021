/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.filtro.bitacora;

import co.stackpointer.cier.modelo.tipo.BitacoraModulo;
import java.util.Date;

/**
 *
 * @author rarp1
 */
public class FiltroConsultaBitacora {

    protected String boletaCensal;
    protected String usuario;
    protected Date fechaInicio;
    protected Date fechaFin;
    protected String modulo;

    public void init() {
        boletaCensal = null;
        usuario = null;
        fechaInicio = null;
        fechaFin = null;
        modulo = null;
    }

    public String getBoletaCensal() {
        return boletaCensal;
    }

    public void setBoletaCensal(String boletaCensal) {
        this.boletaCensal = boletaCensal;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }
}
