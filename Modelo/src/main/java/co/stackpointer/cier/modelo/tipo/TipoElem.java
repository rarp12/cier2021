/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.tipo;

/**
 *
 * @author rrocha
 */
public enum TipoElem {

        PREDIO("ELE_PRED"),
        ESTABLECIMIENTO("ELE_EST"),
        EDIFIO("ELE_EDI"),
        ESPACIO("ELE_ESP"),
        PLANO_PREDIO("ELE_PLA_PRED"),
        PLANO_ESPACIO("ELE_PLA_ESP"),
        OBSERVACIONES("ELE_OBS_COMP"),
        AMBIENTES_SIMILARES("ELE_AMB_SIM");
        public String codigo;

        TipoElem(String codigo) {
            this.codigo = codigo;
        }

        public String getCodigo() {
            return this.codigo;
        }
}
