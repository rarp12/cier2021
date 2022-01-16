/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.tipo;

/**
 *
 * @author rrocha
 */
public enum EstadoInstrumento {
    
    M("enum.estadoInstrumento.M"),
    N("enum.estadoInstrumento.N"),
    E("enum.estadoInstrumento.E"),
    A("enum.estadoInstrumento.A"),
    I("enum.estadoInstrumento.I");
         
    public String etiqueta;

    EstadoInstrumento(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getEtiqueta() {
        return this.etiqueta;
    }
}
