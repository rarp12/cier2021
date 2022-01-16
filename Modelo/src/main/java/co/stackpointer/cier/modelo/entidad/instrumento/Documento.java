/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.entidad.instrumento;

import co.stackpointer.cier.modelo.tipo.SiNo;
import java.io.Serializable;

/**
 *
 * @author user
 */
public class Documento implements Serializable {
    
    private String enunciado;
    private String tipoElemento;
    private SiNo tieneAdjunto;
    private String codRespuesta;
    private String elemento;
    private Long idRespuestaDigitada;

    public Documento() {
    }

    public Documento(String enunciado, String tipoElemento, SiNo tieneAdjunto, String codRespuesta) {
        this.enunciado = enunciado;
        this.tipoElemento = tipoElemento;
        this.tieneAdjunto = tieneAdjunto;
        this.codRespuesta = codRespuesta;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public String getTipoElemento() {
        return tipoElemento;
    }

    public void setTipoElemento(String tipoElemento) {
        this.tipoElemento = tipoElemento;
    }

    public SiNo getTieneAdjunto() {
        return tieneAdjunto;
    }

    public void setTieneAdjunto(SiNo tieneAdjunto) {
        this.tieneAdjunto = tieneAdjunto;
    }

    public String getCodRespuesta() {
        return codRespuesta;
    }

    public void setCodRespuesta(String codRespuesta) {
        this.codRespuesta = codRespuesta;
    }

    public String getElemento() {
        return elemento;
    }

    public void setElemento(String elemento) {
        this.elemento = elemento;
    }

    public Long getIdRespuestaDigitada() {
        return idRespuestaDigitada;
    }

    public void setIdRespuestaDigitada(Long idRespuestaDigitada) {
        this.idRespuestaDigitada = idRespuestaDigitada;
    }
}
