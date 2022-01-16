/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.tipo;

/**
 *
 * @author rarp1
 */
public enum FuenteDigitacion {
    DIG("aplicacion.fuente.digitacion.digitacion",0),
    MIG("aplicacion.fuente.digitacion.migracion",1);
    
    private String etiqueta;
    private Integer valor;

    private FuenteDigitacion(String etiqueta, Integer valor) {
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
