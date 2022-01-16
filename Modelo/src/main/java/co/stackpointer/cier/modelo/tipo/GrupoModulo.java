/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.tipo;

/**
 *
 * @author rrocha
 */
public enum GrupoModulo {
    ADM("enum.grupoModulo.ADM"),
    DIG("enum.grupoModulo.DIG"),
    GER("enum.grupoModulo.GER"),
    CON("enum.grupoModulo.CON");
    
    private String etiqueta;

    private GrupoModulo(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getEtiqueta() {
        return etiqueta;
    }
}
