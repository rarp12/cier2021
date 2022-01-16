/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.tipo;

/**
 *
 * @author nruiz
 */
public enum Vulnerabilidad {
    A("enum.vulnerabilidad.nombre.A", "sql.ind.general.alta"),
    M("enum.vulnerabilidad.nombre.M", "sql.ind.general.media"),
    B("enum.vulnerabilidad.nombre.B", "sql.ind.general.baja");    
    
    private String etiqueta;
    private String nombre;

    Vulnerabilidad(String etiqueta, String nombre) {
        this.etiqueta = etiqueta;
        this.nombre = nombre;
    }

    public String getNombre() {
        return this.nombre;
    }
    
    public String getEtiqueta() {
        return etiqueta;
    }
}
