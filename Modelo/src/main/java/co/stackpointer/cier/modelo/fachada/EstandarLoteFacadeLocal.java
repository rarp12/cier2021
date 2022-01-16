/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.fachada;

import co.stackpointer.cier.modelo.entidad.indicador.EstandarLote;
import java.util.LinkedHashMap;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface EstandarLoteFacadeLocal {

    public List<EstandarLote> getEstandares();
    
    public EstandarLote getEstandar(Long id);    
    
    public List<String> getListaTipo(String grupo);
    
    public List<String> getListaTipo();

    public EstandarLote guardarEstandar(String usernameSesion, EstandarLote estandar);

    public void actualizarEstandar(String usernameSesion, EstandarLote estandar);    

    public void eliminarEstandar(String usernameSesion, EstandarLote estandar);
  
}
