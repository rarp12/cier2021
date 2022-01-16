/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.fachada;

import co.stackpointer.cier.modelo.entidad.instrumento.Encuestador;
import co.stackpointer.cier.modelo.entidad.instrumento.Supervisor;
import co.stackpointer.cier.modelo.entidad.instrumento.Tipologia;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author StackPointer
 */
@Local
public interface DataGenerationFacadeLocal {

    public List<Encuestador> buscarTodosEncuestadores();

    public List<Supervisor> buscarTodosSupervisores();

    public Tipologia buscarTipologiaPorCodigo(String codigo);
    
}
