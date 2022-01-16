/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.fachada;

import co.stackpointer.cier.modelo.entidad.instrumento.Encuestador;
import co.stackpointer.cier.modelo.entidad.instrumento.Supervisor;
import co.stackpointer.cier.modelo.entidad.instrumento.Tipologia;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;

/**
 *
 * @author StackPointer
 */
@Stateless
public class DataGenerationFacade implements DataGenerationFacadeLocal {
    
    @Inject
    private TenantPersistenceManager tpm;
    
    @Override
    public List<Encuestador> buscarTodosEncuestadores(){
        return tpm.getEm().createNamedQuery("Encuestador.findAll").getResultList();
    }
    
    @Override
    public List<Supervisor> buscarTodosSupervisores(){
        return tpm.getEm().createNamedQuery("Supervisor.findAll").getResultList();
    }    
    
    @Override
    public Tipologia buscarTipologiaPorCodigo(String codigo){
        try {
            return (Tipologia) tpm.getEm().createNamedQuery("Tipologia.findByCodigo")
                    .setParameter("codigo", codigo)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    } 

}
