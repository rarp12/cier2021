/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.tipo;

/**
 *
 * @author nruiz
 */
public enum SiNo {
    Si("aplicacion.general.si", "sql.ind.general.si",1),
    No("aplicacion.general.no", "sql.ind.general.no",0);
    
    private String etiqueta;
    private String valor;    
    private int valorEntero;

    private SiNo(String etiqueta, String valor, int valorEntero) {
        this.etiqueta = etiqueta;
        this.valor = valor;
        this.valorEntero = valorEntero;
    }
    
    public String getValor() {
        return valor;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public int getValorEntero() {
        return valorEntero;
    }

    public void setValorEntero(int valorEntero) {
        this.valorEntero = valorEntero;
    }    
    
}
