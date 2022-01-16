/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.fachada;

import co.stackpointer.cier.modelo.entidad.dpa.ConfiguracionNivel;
import co.stackpointer.cier.modelo.entidad.dpa.Nivel;
import co.stackpointer.cier.modelo.excepcion.ErrorIntegridad;
import co.stackpointer.cier.modelo.utilidad.UtilOut;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;

/**
 *
 * @author StackPointer
 */
@Stateless
public class DPAFacade implements DPAFacadeLocal {
    
    @Inject
    private TenantPersistenceManager tpm;
    
    @Override
    public List<ConfiguracionNivel> getConfNiveles() {
        List<ConfiguracionNivel> ConfNiveles = new ArrayList<ConfiguracionNivel>();
        try {
            ConfNiveles = tpm.getEm().createNamedQuery("ConfiguracionNivel.findAll")
                    .getResultList();
        } catch (Exception ex) {
            UtilOut.println(ex);
        }
        return ConfNiveles;
    }   

    @Override
    public Nivel getNivelPorCodigo(String codigo) {
        try {
            return (Nivel) tpm.getEm().createNamedQuery("Nivel.porCodigo")
                    .setParameter("codigo", codigo)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        } 
    }

    @Override
    public Nivel getNivelPorId(Long id) {
        try {
            return (Nivel) tpm.getEm().createNamedQuery("Nivel.porId")
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        } 
    }
     
    @Override
    public List<Nivel> getNivelesPorPadre(Nivel padre) {
        List<Nivel> nivelesEspecificos = new ArrayList<Nivel>();
        try {
            nivelesEspecificos = tpm.getEm().createNamedQuery("Nivel.porPadre")
                    .setParameter("padre", padre)
                    .getResultList();
        } catch (Exception ex) {
            UtilOut.println(ex);
        }
        return nivelesEspecificos;
    }
     
    @Override
    public void guardarNivelDPA(Nivel nivel) {
        try {            
            tpm.getEm().persist(nivel);
            tpm.getEm().flush();
        } catch (Exception e) {
            UtilOut.println(e);
            throw new ErrorIntegridad("No se pudo guardar el nivel");
        }
    }

    @Override
    public void actualizarNivelDPA(Nivel nivel) {
        try {
            tpm.getEm().merge(nivel);
            tpm.getEm().flush();            
        } catch (Exception e) {
            throw new ErrorIntegridad("No se pudo actualizar el nivel");
        }        
    }

    @Override
    public void eliminarNivelDPA(Nivel nivel) {
        try {
            tpm.getEm().clear();
            tpm.getEm().remove(tpm.getEm().contains(nivel) ? nivel : tpm.getEm().merge(nivel));
            tpm.getEm().flush();            
        } catch (Exception e) {
            throw new ErrorIntegridad("No se pudo eliminar el nivel");
        }        
    }

    @Override
    public boolean estanRelacionados(String codPadre, String codHijo) {
        try {
            Nivel n = (Nivel) tpm.getEm().createNamedQuery("Nivel.porCodigoYPadre")
                    .setParameter("codigo", codHijo)
                    .setParameter("codPadre", codPadre)
                    .getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
        } catch (Exception er) {
            throw new ErrorIntegridad("No se pudo verifircar relacion DPA", er.getMessage());
        }
    }
    
}
