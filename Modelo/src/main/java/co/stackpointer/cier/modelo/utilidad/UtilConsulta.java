/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.utilidad;

/**
 *
 * @author user
 */
public class UtilConsulta {

    public static String formatoDecimal = "#,##0.00";
    public static String formatoMoneda = "$ #,##0.00";
    public static String formatoPorcentaje = "0'%'";
    public static String formatoEntero = "#,##0";

    public static String stringValueOf(Object object) {
        return object == null ? null : String.valueOf(object);
    }

    public static Long longValueOf(Object object) {
        return object == null ? null : doubleValueOf(object).longValue();
    }

    public static Double doubleValueOf(Object object) {
        return object == null ? null : Double.valueOf(object.toString());
    }
    
    
}
