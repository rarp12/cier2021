/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.fachada;

import co.stackpointer.cier.modelo.filtro.simulacion.FiltroDiagnosticoGeneral;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface SimulacionCostoFacadeLocal {

    public List<Object[]> consultarDiagnosticoGeneral(FiltroDiagnosticoGeneral filtro);
    
}
