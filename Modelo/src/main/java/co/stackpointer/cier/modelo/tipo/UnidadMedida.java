/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.tipo;

/**
 *
 * @author rarp1
 */
public enum UnidadMedida {
    
    MT_2(1,"enum.unidadmedica.mt2"),
    KM_2(2,"enum.unidadmedida.km2"),
    MI_2(3,"enum.unidadmedida.mi2");
    
    private int valor;
    private String etiqueta;

    private UnidadMedida(int valor,String etiqueta) {
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
