package co.stackpointer.cier.modelo.excepcion;

public class ErrorAutenticacion extends ErrorGeneral {
    
    private String usuarioRed;
    
    protected  final static  String ERROR_AUTENTICACION ="Error de autenticación";

    
    public ErrorAutenticacion(String usuarioRed, String mensaje){
        super(ERROR_AUTENTICACION,mensaje);
        this.usuarioRed = usuarioRed;
    }

    public String getUsuarioRed() {
        return usuarioRed;
    }
}
