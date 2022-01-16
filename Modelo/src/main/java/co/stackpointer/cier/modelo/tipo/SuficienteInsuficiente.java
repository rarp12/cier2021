/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.tipo;

/**
 *
 * @author rrocha
 */
public enum SuficienteInsuficiente {
    NA(2,"enum.suficienteInsuficiente.NA"),
    SUF(1,"enum.suficienteInsuficiente.SUF"),
    INSUF(0,"enum.suficienteInsuficiente.INSUF");
    
    private int valor;
    private String etiqueta;

    private SuficienteInsuficiente(int valor,String etiqueta) {
        this.valor = valor;
        this.etiqueta = etiqueta;
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

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }
    
}
