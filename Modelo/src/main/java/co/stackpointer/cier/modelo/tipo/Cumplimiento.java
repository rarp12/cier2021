/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.tipo;

/**
 *
 * @author user
 */
public enum Cumplimiento {
    SI("aplicacion.general.cumple",1),
    NO("aplicacion.general.noCumple",0);
    
    private String etiqueta;
    private Integer valor;

    private Cumplimiento(String etiqueta, Integer valor) {
        this.etiqueta = etiqueta;
        this.valor = valor;
    }  
    
    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }
    
   
}
