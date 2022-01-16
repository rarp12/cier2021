/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.tipo;

/**
 *
 * @author rrocha
 */
public enum Topografias {
        CERO("1","enum.topografias.CERO"),
        MENORQUINCE("2","enum.topografias.MENORQUINCE"),
        QUINCETREINTA("3","enum.topografias.QUINCETREINTA"),
        MASTREINTA("4","enum.topografias.MASTREINTA");
        
        public String codigo;
        public String etiqueta;

        Topografias(String codigo, String etiqueta) {
            this.codigo = codigo;
            this.etiqueta = etiqueta;
        }

        public String getCodigo() {
            return this.codigo;
        }
        
        public String getEtiqueta() {
            return this.etiqueta;
        }
}
