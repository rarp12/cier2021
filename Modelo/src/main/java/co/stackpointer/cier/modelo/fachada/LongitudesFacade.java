/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.fachada;

import co.stackpointer.cier.modelo.entidad.sistema.Longitudes;
import co.stackpointer.cier.modelo.utilidad.UtilOut;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

/**
 *
 * @author StackPointer
 */
@Stateless
public class LongitudesFacade implements LongitudesFacadeLocal {

    @Inject
    private TenantPersistenceManager tpm;

  
    @Override
    public List<Longitudes> consultarLongitudes() {
        List<Longitudes> longitudes = new ArrayList<Longitudes>();
        try {
            longitudes = tpm.getEm().createNamedQuery("Longitudes.findAll")
                    .getResultList();
        } catch (Exception ex) {
            UtilOut.println(ex);
        }
        return longitudes;
    }

}
