/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.fachada;

import co.stackpointer.cier.modelo.entidad.establecimiento.Predio;
import co.stackpointer.cier.modelo.excepcion.ErrorGeneral;
import co.stackpointer.cier.modelo.excepcion.ErrorValidacion;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;

/**
 *
 * @author user
 */
@Stateless
public class PredioFacade  implements PredioFacadeLocal{
     
   @Inject
   private TenantPersistenceManager tpm;
   
   
   @Override
   public List<Predio> obtenerPredioPorCodigoNombre(String codigoPredio, String nombre) {
        try {
            return tpm.getEm().createNamedQuery("Predio.findByCodigoNombre").setParameter("codigo", codigoPredio).setParameter("nombre", "%"+nombre+"%").getResultList();
        } catch (NoResultException ex) {
            return null;
        } catch (Exception exp) {
            throw new ErrorGeneral("Error", exp.getMessage());
        }
    }
     
    @Override
    public List<Predio> obtenerPrediosPorSede( String idSede)
    {
          String  consutla= "select predio.* from est_sedes sede inner join est_pre_sed sp ON sede.id = sp.id_sede"+
                            " inner join est_predios predio ON predio.id = sp.id_predio where sede.id = "+ idSede;
          
      try {
            return (List<Predio>)tpm.getEm().createNativeQuery(consutla).getResultList();
        } catch (NoResultException ex) {
            ex.printStackTrace();
            return null;
        } catch (Exception exp) {
            exp.printStackTrace();
            throw new ErrorGeneral("Error", exp.getMessage());
      }
     
    }
     
    @Override
    public String obtenerSedePorPredio(Long idPredio)
    {
          String  sql= "select sede.codigo from est_sedes sede inner join est_pre_sed sp ON sede.id = sp.id_sede where sp.id_predio = "+ idPredio;
          
      try {
            return (String)tpm.getEm().createNativeQuery(sql).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } catch (Exception exp) {
            throw new ErrorGeneral("Error", exp.getMessage());
      }
     
    }

    @Override
    public Predio getPredioPorCodigo(String codigo) {
       try {
            tpm.getEm().clear();
            return (Predio) tpm.getEm().createNamedQuery("Predio.porCodigo")
                    .setParameter("codigo", codigo)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        } catch (Exception e) {
            throw new ErrorValidacion("No se pudo obtener el predio: " + e);
        }
    }

    @Override
    public Predio guardarPredio(Predio predio) {      
        try {
            tpm.getEm().persist(predio);
            tpm.getEm().flush();
        } catch (Exception ex) {           
            throw new ErrorGeneral("Error", ex.getMessage());
        }
        return predio;
    }      
     
}
