package co.stackpointer.cier.modelo.excepcion;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class ErrorIntegridad extends ErrorGeneral {
    
    protected  final static  String ERROR_INTEGRIDAD ="Error de integridad de datos";
  
    public ErrorIntegridad(String mensaje) {
        super(ERROR_INTEGRIDAD,mensaje);
    }
    
    public ErrorIntegridad(String mensaje, Object... valores) {
        super(ERROR_INTEGRIDAD,String.format(mensaje, valores));
    }
}
