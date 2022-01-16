/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.tipo;

/**
 *
 * @author mrodriguez
 */
public enum TipoCostoConstruccion {
   
    PRIORITARIO("bd.ind_base_costos_construccion.tipo.PRIORITARIO"),
    COMPLEMENTARIO("bd.ind_base_costos_construccion.tipo.COMPLEMENTARIO"),
    OTRO("bd.ind_base_costos_construccion.tipo.OTRO");
    
    private String etiqueta;

    private TipoCostoConstruccion(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getEtiqueta() {
        return etiqueta;
    }
}
