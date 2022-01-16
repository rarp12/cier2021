/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.tipo;

/**
 *
 * @author nruiz
 */
public enum ServiciosPublicos {
    DSA("enum.serviciosPublicos.DSA"),
    DSEE("enum.serviciosPublicos.DSEE"),
    DSG("enum.serviciosPublicos.DSG"),
    DSRB("enum.serviciosPublicos.DSRB"),
    DSRAAS("enum.serviciosPublicos.DSRAAS"),
    DSRP("enum.serviciosPublicos.DSRP"),
    DSI("enum.serviciosPublicos.DSI"),
    DST("enum.serviciosPublicos.DST"),
    SistemaReciclaje("enum.serviciosPublicos.SistemaReciclaje");
    
    public String etiqueta;

    ServiciosPublicos(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getEtiqueta() {
        return this.etiqueta;
    }
}
