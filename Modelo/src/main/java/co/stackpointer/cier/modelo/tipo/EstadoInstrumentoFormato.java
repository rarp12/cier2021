/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.tipo;

/**
 *
 * @author rrocha
 */
public enum EstadoInstrumentoFormato {
    
    N("Nuevo"),
    A("Aprobado"),
    AC("Activado"),
    I("Inactivo"),
    AN("Anulado");
    
    public String nombre;

    EstadoInstrumentoFormato(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return this.nombre;
    }
    
}
