package co.stackpointer.cier.modelo.fachada;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author user
 */
@Stateless
public class TenantPersistenceManager {

    @PersistenceUnit(unitName = "Model")
    private EntityManagerFactory emf;
    @Inject
    private Instance<Principal> principal;
    private Map<Long, EntityManager> emMap = new HashMap<Long, EntityManager>();
       
    public Long getCurrentTenant() {
        String name = principal.get().getName();
        return Long.parseLong(name.split("%")[0]);
    }

    public EntityManager getEm() {
        Long tenant = getCurrentTenant();
        if(!emMap.containsKey(tenant)){
            HashMap properties = new HashMap();
            properties.put("TENANT_ID", tenant);
            emMap.put(tenant, emf.createEntityManager(properties));
        }        
        return emMap.get(tenant);
    }    
          
    
    @PreDestroy
    public void release(){
        for(Long tenant : emMap.keySet()){
            EntityManager em = emMap.get(tenant);
            if(em.isOpen()){
                em.close();
            }
        }
    }
}
