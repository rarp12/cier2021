/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.tipo;

/**
 *
 * @author rarp1
 */
public enum BitacoraModulo {
    
    DG("DIGITACION","enum.bitacora.modulo.DG"),
    CO("CONSULTA","enum.bitacora.modulo.CO"),
    AM("ADMINISTRACION","enum.bitacora.modulo.AM");
    
    private String valor;
    private String etiqueta;

    private BitacoraModulo(String valor, String etiqueta) {
        this.valor = valor;
        this.etiqueta = etiqueta;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }
    
}
