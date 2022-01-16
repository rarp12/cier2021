/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.utilidad;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author mrodriguez
 */
public class UtilOut {

    private static final boolean IMPRIMIR = true;
    private static DateFormat defaultDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    private UtilOut() {
    }

    public static void println(Object o) {
        if (IMPRIMIR) {
            System.out.printf("%s : %s\n", defaultDateFormat.format(new Date()), o);
        }
    }

    public static void println(Exception ex) {
        if (IMPRIMIR) {
            ex.printStackTrace();
        }
    }

    public static void println(String format, Object... args) {
        if (IMPRIMIR) {
            System.out.printf("%s : %s\n", defaultDateFormat.format(new Date()), String.format(format, args));
        }
    }
}
