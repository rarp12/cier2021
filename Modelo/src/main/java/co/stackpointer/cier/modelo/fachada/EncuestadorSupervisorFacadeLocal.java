/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.fachada;

import co.stackpointer.cier.modelo.entidad.instrumento.Encuestador;
import co.stackpointer.cier.modelo.entidad.instrumento.Supervisor;
import java.util.List;
import javax.ejb.Local;

@Local
public interface EncuestadorSupervisorFacadeLocal {

    public List<Encuestador> getEncuestadores();

    public Encuestador guardarEncuestador(Encuestador encuestador);

    public void actualizarEncuestador(Encuestador encuestador);

    public List<Supervisor> getSupervisores();

    public Supervisor guardarSupervisor(Supervisor supervisor);

    public void actualizarSupervisor(Supervisor supervisor);

    public List<String> getTiposIdentificacion();
    
    public boolean existeEncuestador(String identificacion);
    
    public boolean existeSupervisor(String identificacion);
    
}
