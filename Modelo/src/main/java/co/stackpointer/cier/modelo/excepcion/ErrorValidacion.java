package co.stackpointer.cier.modelo.excepcion;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class ErrorValidacion extends ErrorGeneral {
   
    protected  final static  String ERROR_VALIDACION ="Error de validación de datos";

    public ErrorValidacion(String mensaje) {
        super(ERROR_VALIDACION,mensaje);    
    }
}
