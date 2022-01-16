/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.tipo;

/**
 *
 * @author rrocha
 */
public enum Verificable {
    
    VER(1,"enum.verificable.VER"),
    NO_VER(0,"enum.verificable.NO_VER");
    
    private int valor;
    private String etiqueta;

    private Verificable(int valor,String etiqueta) {
        this.valor = valor;
        this.etiqueta = etiqueta;
    }
    
    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    } 

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }
    
    
}
