/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.tipo;

/**
 *
 * @author DPAZ
 */
public enum TipoDatoExcel {
    CADENA(0),
    TIPOLOGIA(1),
    SINO(2),
    SUFINSUF(3),
    FECHA(4),
    DECIMAL(5);
    
    private int valor;

    private TipoDatoExcel(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }    
    
}
