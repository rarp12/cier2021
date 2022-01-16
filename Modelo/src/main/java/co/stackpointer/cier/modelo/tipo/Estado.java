/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.tipo;

/**
 *
 * @author mrodriguez
 */
public enum Estado {
    A("enum.estado.A"),
    B("enum.estado.B"),
    I("enum.estado.I");
    
    private String etiqueta;

    private Estado(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getEtiqueta() {
        return etiqueta;
    }
}
