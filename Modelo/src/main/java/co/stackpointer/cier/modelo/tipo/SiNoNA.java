/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.tipo;

/**
 *
 * @author nruiz
 */
public enum SiNoNA {
    NA("aplicacion.general.NA",2),
    Si("aplicacion.general.si", 1),
    No("aplicacion.general.no", 0);
    
    private String etiqueta;
    private int valor;

    private SiNoNA(String etiqueta, int valor) {
        this.etiqueta = etiqueta;
        this.valor = valor;
    }
    
    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        // no borrar
    }

    public String getEtiqueta() {
        return etiqueta;
    }
}
