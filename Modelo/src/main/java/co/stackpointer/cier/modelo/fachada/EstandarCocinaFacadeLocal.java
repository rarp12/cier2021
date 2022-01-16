/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.fachada;

import co.stackpointer.cier.modelo.entidad.indicador.EstandarCocina;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author rarp1
 */
@Local
public interface EstandarCocinaFacadeLocal {
    
    public List<EstandarCocina> getEstandares();
    
    public EstandarCocina getEstandar(Long id);    

    public EstandarCocina guardarEstandarCocina(String usernameSesion, EstandarCocina estandar);

    public void actualizarEstandarCocina(String usernameSesion, EstandarCocina estandar);    

    public void eliminarEstandarCocina(String usernameSesion, EstandarCocina estandar);
    
}
