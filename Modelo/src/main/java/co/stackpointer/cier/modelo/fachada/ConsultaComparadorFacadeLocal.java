/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.fachada;

import co.stackpointer.cier.modelo.filtro.ambito.FiltroComparadorInd;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface ConsultaComparadorFacadeLocal {

    public List<Object[]> consultarGeneralidades(FiltroComparadorInd filtro);
    
    public List<Object[]> consultarRedistribucion(FiltroComparadorInd filtro);
    
    public List<Object[]> consultarAmbientes(FiltroComparadorInd filtro);

    public List<Object[]> consultarAmpliacion(FiltroComparadorInd filtro);

    public List<Object[]> consultarEdificios(FiltroComparadorInd filtro);
    
    public List<Object[]> consultarEspacios(FiltroComparadorInd filtro);

}
