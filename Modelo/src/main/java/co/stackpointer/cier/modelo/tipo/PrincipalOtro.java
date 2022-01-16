/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.tipo;

/**
 *
 * @author nruiz
 */
public enum PrincipalOtro {
    P("enum.principalOtro.P"),
    O("enum.principalOtro.O");
    
    public String etiqueta;
    
    PrincipalOtro(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getEtiqueta() {
        return this.etiqueta;
    }

    public String getName() {
        return this.toString();
    }
    
    
}
