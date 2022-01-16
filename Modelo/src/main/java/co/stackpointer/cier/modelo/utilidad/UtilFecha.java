/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.utilidad;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author user
 */
public class UtilFecha {

    private static DateFormat defaultFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    public static Date ignorarTiempo(Date fecha) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static long getTimeIgnorarTiempo(Date fecha) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime().getTime();
    }

    public static int compararIgnorandoTiempo(Date fecha1, Date fecha2) {
        return ignorarTiempo(fecha1).compareTo(ignorarTiempo(fecha2));
    }

    public static int comparargnorandoTiempo(Date fecha) {
        return ignorarTiempo(fecha).compareTo(ignorarTiempo(new Date()));
    }

    public static int compararConFinDeDia(Date fecha) {
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(fecha);
        calendario.set(Calendar.HOUR, 23);
        calendario.set(Calendar.MINUTE, 0);
        calendario.set(Calendar.SECOND, 0);
        return calendario.getTime().compareTo(new Date());
    }

    public static String representar(Date fecha) {
        return defaultFormat.format(fecha);
    }

    public static Date convertir(String textoFecha) throws ParseException {
        return defaultFormat.parse(textoFecha);
    }

    public static int diferenciaEnDias(Date fechaInicial, Date fechaFinal) {
        long inicio = getTimeIgnorarTiempo(fechaInicial);
        long fin = moverAlFinalDia(fechaFinal).getTime();
        return (int) (Math.floor((fin - inicio) / (1000 * 60 * 60 * 24)));
    }

    public static Date restarDias(Date fecha, int dias) {
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(fecha);
        calendario.add(Calendar.DATE, -dias);
        return calendario.getTime();
    }

    public static Date obtenerSinMillis(Date fecha) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date moverAlFinalDia(Date fecha) {
        if (fecha == null) {
            return null;
        }
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(fecha);
        calendario.set(Calendar.HOUR, 23);
        calendario.set(Calendar.MINUTE, 59);
        calendario.set(Calendar.SECOND, 0);
        return calendario.getTime();
    }
    
     
    public static Date obtenerFechaActual(){
        return new Date(System.currentTimeMillis());
    }
    
     public static int obtenerPeriodoActual(){
        int periodo = 0;
        periodo = Integer.parseInt(formatear(obtenerFechaActual(), "yyyyMM"));
        return periodo;
    }
    
    public static String obtenerFechaActualString(){
        return formatear(obtenerFechaActual(), "dd-MM-yyyy");
    }
    
    public static String obtenerHoraActualString(){
        return formatear(obtenerFechaActual(), "HH:mm:ss.SSS");
    }

    public static String formatearConMillis(Date date) {
        return formatear(date, "dd-MM-yyyy HH:mm:ss.SSS");
    }

    public static String formatear(Date date) {
        return formatear(date, "dd-MM-yyyy HH:mm:ss a");
    }

    public static String formatearIgnorandoTiempo(Date date) {
        return formatear(date, "dd-MM-yyyy");
    }
    
    public static String formatearExcel(Date date) {
        return formatear(date, "dd/MM/yyyy");
    }

    public static String formatear(Date date, String formato) {
        DateFormat df = new SimpleDateFormat(formato);
        return df.format(date);
    }
    
    public static Date convetirStringDate(String fechaParam) throws Exception{
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
        Date fecha = null;
        try {
            fecha = formatoDelTexto.parse(fechaParam);
        } catch (Exception ex) {
            throw ex;
        }
        return fecha;
    }
    
    public static java.sql.Timestamp convertToSqlDate(java.util.Date uDate) {
        java.sql.Timestamp sDate = new java.sql.Timestamp(uDate.getTime());
        return sDate;
    }
}
