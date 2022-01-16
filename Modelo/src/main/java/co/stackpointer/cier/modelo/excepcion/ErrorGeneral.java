package co.stackpointer.cier.modelo.excepcion;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class ErrorGeneral extends RuntimeException {
   
    protected String titulo;

    public ErrorGeneral(String titulo, String mensaje) {
        super(mensaje);
        this.titulo = titulo;
    }

    public String getTitulo() {
        return titulo;
    }    
    
}
