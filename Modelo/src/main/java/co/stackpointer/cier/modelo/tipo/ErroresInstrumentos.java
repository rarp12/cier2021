/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.tipo;

/**
 *
 * @author rarp1
 */
public enum ErroresInstrumentos {
    
    DATO_REPETIDO ("enum.errores.instrumento_001","enum.errores.instrumento.tipo.01"),
    BOLETA_EXISTENTE ("enum.errores.instrumento_002","enum.errores.instrumento.tipo.02"),
    DATO_VACIO ("enum.errores.instrumento_003","enum.errores.instrumento.tipo.03"),
    BOLETA_NO_RELACIONADO ("enum.errores.instrumento_004","enum.errores.instrumento.tipo.04"),
    DATO_INEXISTENTE ("enum.errores.instrumento_005","enum.errores.instrumento.tipo.05"),
    DATO_NO_RELACIONADO("enum.errores.instrumento_001","enum.errores.instrumento.tipo.04"),
    FORMATO_INVALIDO("enum.errores.instrumento_006","enum.errores.instrumento.tipo.06");
    
    public String errorEtiqueta;
    public String tipo;
   
    ErroresInstrumentos(String errorEtiqueta, String tipo){
        this.errorEtiqueta = errorEtiqueta;
        this.tipo = tipo;
    }
    
    public String getErrorEtiqueta() {
        return errorEtiqueta;
    }

    public String getTipo() {
        return tipo;
    }


    
    
    
}
