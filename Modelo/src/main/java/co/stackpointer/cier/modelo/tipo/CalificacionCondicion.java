/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.tipo;

/**
 *
 * @author mrodriguez
 */
public enum CalificacionCondicion {
   UNO(1),
   DOS(2),
   TRES(3),
   CUATRO(4);
    
    private int valor;

    private CalificacionCondicion(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        // no borrar
    }
    
    
}
