package co.stackpointer.cier.modelo.excepcion;

public class ErrorParametro extends ErrorGeneral {
       
    protected  final static  String ERROR_PRAMETRO ="Error de parámetro";
    
    public ErrorParametro(String detalles) {
        super(ERROR_PRAMETRO,detalles);
    }

}
