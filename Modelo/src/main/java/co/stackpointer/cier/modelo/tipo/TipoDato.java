/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.tipo;

/**
 *
 * @author rrocha
 */
public enum TipoDato {
     
        BOOLEANO("Booleano"),
        FECHA("DATE"),
        HORA("TIME"),
        NUMERO("LONG"),
        TEXTO("STRING");
        
        public String tipo;

        TipoDato(String tipo) {
            this.tipo = tipo;
        }

        public String getTipo() {
            return this.tipo;
        }
    
}
