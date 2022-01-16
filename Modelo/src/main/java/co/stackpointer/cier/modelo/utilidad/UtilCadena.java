/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.utilidad;

/**
 *
 * @author user
 */
public class UtilCadena {

    public static String subString(String texto, int endIndex) {
        return texto != null && texto.length() > endIndex ? texto.substring(0, endIndex) : texto;
    }

    public static boolean excedeLongitud(String texto, int longuitud) {
        return texto != null && texto.length() > longuitud;
    }

    public static boolean isNullOVacio(String valor) {
        return valor == null || valor.trim().isEmpty();
    }
    
    public static boolean isNullOVacio(Object valor) {
        return valor == null || valor.toString().isEmpty();
    }

    public static String toUpperCase(String texto) {
        return texto != null ? texto.toUpperCase() : null;
    }

    public static String validarNullVacio(String texto) {
        return isNullOVacio(texto) ? null : texto;
    }
    
    public static boolean esNumerico(String valor) {
        return valor.matches("[-+]?\\d+(\\.\\d+)?");
    }

}
