/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.fachada;

import co.stackpointer.cier.modelo.entidad.acceso.Usuario;
import co.stackpointer.cier.modelo.entidad.digitado.Adjunto;
import java.io.File;
import java.io.InputStream;
import javax.ejb.Local;

/**
 *
 * @author stackpointer
 */

@Local
public interface ArchivoFacadeLocal {
    
    public boolean subirArchivo(File file, String prefix, InputStream inputStream);
    
    public String getPathname(Long idInstrumentoDig);

    public String getPrefix(String fileName, Long idInstrumentoDig);

    public boolean eliminarArchivo(Adjunto adjunto, Usuario usuario);
    
    public void crearDirectorioPais(Long tenant);

    public String ubicacionArchivo(String ruta);
    
}
