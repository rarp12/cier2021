/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.fachada;

import co.stackpointer.cier.modelo.entidad.dpa.ConfiguracionNivel;
import co.stackpointer.cier.modelo.entidad.dpa.Nivel;
import co.stackpointer.cier.modelo.excepcion.ErrorIntegridad;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface DPAFacadeLocal {
    
    public List<ConfiguracionNivel> getConfNiveles();
    
    public Nivel getNivelPorCodigo(String codigo);

    public Nivel getNivelPorId(Long id);

    public List<Nivel> getNivelesPorPadre(Nivel padre);

    public void guardarNivelDPA(Nivel nivel) throws ErrorIntegridad;
    
    public void actualizarNivelDPA(Nivel nivel);
    
    public void eliminarNivelDPA(Nivel nivel);
    
    public boolean estanRelacionados (String codPadre, String codHijo);

}
