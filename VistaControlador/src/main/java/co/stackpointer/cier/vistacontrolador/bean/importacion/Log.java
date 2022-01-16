/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.bean.importacion;

/**
 *
 * @author user
 */
public class Log {

    private String tipo;
    private String valor;
    public static String DONE = "done";
    public static String WARNING = "warning";
    public static String ERROR = "error";

    public Log() {
    }

    public Log(String tipo, String valor) {
        this.tipo = tipo;
        this.valor = valor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
