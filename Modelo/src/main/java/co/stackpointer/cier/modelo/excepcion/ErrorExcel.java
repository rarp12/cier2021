package co.stackpointer.cier.modelo.excepcion;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class ErrorExcel extends ErrorGeneral {
    
    protected  final static  String ERROR_EXCEL ="Error en archivo Excel";
  
    public ErrorExcel(String mensaje) {
        super(ERROR_EXCEL,mensaje);
    }
    
    public ErrorExcel(String mensaje, Object... valores) {
        super(ERROR_EXCEL,String.format(mensaje, valores));
    }
}
