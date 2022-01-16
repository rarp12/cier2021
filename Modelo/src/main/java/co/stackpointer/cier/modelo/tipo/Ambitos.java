/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.tipo;

/**
 *
 * @author nruiz
 */
public enum Ambitos {
    A_ACC("Accesibilidad al predio"),
    A_ACCD("Accesibilidad dentro del predio"),
    A_AMB("Ambiente"),
    A_CON("Confort"),
    A_CV("Control y vigilancia"),
    A_EEE("Estad Edif y Esp"),
    A_OFE("Oferta"),
    A_PROP("Propiedad"),
    A_RA("Riesgos Antropicos"),
    A_RN("Riesgos Naturales"),
    A_RI("Riesgos"),
    A_SEG("Seguridad"),
    A_SP("Servicios"),
    A_SOS("Sostenibilidad"),
    A_SS("Servicios Sanitarios");

    public String nombre;

    Ambitos(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return this.nombre;
    }
}
