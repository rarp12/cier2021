package co.stackpointer.cier.modelo.excepcion;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class ErrorProcedimientoBD extends ErrorGeneral {
    
    protected  final static  String ERROR_PROCEDIMIENTO_BD ="Error de procedimiento almacenado de base de datos";
  
    public ErrorProcedimientoBD(String mensaje) {
        super(ERROR_PROCEDIMIENTO_BD,mensaje);
    }
    
    public ErrorProcedimientoBD(String mensaje, Object... valores) {
        super(ERROR_PROCEDIMIENTO_BD,String.format(mensaje, valores));
    }
}
