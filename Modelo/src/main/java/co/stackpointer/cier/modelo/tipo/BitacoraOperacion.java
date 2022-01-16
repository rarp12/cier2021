/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.tipo;

/**
 *
 * @author rarp1
 */
public enum BitacoraOperacion {
    
    C("enum.bitacora.operacion.C"),
    E("enum.bitacora.operacion.E"),
    M("enum.bitacora.operacion.M"),
    A("enum.bitacora.operacion.A"),
    Q("enum.bitacora.operacion.Q");
    
    private String etiqueta;

    private BitacoraOperacion(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }
    
}
