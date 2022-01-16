/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.tipo;

/**
 *
 * @author rrocha
 */
public enum TipoEstablecimiento {
    FEMENINO("1", "enum.tipoEstablecimiento.FEMENINO"),
    MASCULINO("2", "enum.tipoEstablecimiento.MASCULINO"),
    MIXTO("3", "enum.tipoEstablecimiento.MIXTO");

    public String codigo;
    public String etiqueta;

    TipoEstablecimiento(String codigo, String etiqueta) {
        this.codigo = codigo;
        this.etiqueta = etiqueta;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public String getEtiqueta() {
        return etiqueta;
    }
}
