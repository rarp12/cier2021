/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.fachada;

import co.stackpointer.cier.modelo.entidad.instrumento.Encuestador;
import co.stackpointer.cier.modelo.entidad.instrumento.Supervisor;
import co.stackpointer.cier.modelo.excepcion.ErrorGeneral;
import co.stackpointer.cier.modelo.excepcion.ErrorIntegridad;
import co.stackpointer.cier.modelo.utilidad.UtilOut;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 *
 * @author Stackpointer
 */
@Stateless
public class EncuestadorSupervisorFacade implements EncuestadorSupervisorFacadeLocal {

    @Inject
    private TenantPersistenceManager tpm;

    @Override
    public List<Encuestador> getEncuestadores() {
        List<Encuestador> encuestadores = new ArrayList<Encuestador>();
        try {
            encuestadores = tpm.getEm().createNamedQuery("Encuestador.findAll").getResultList();
        } catch (Exception ex) {
            UtilOut.println(ex);
        }
        return encuestadores;
    }

    @Override
    public Encuestador guardarEncuestador(Encuestador encuestador) {
        try {
            tpm.getEm().persist(encuestador);
            tpm.getEm().flush();
            return encuestador;
        } catch (Exception e) {
            throw new ErrorIntegridad("error al guardar el encuestador");
        }
    }

    @Override
    public void actualizarEncuestador(Encuestador encuestador) {
        try {
            EntityManager em = tpm.getEm();
            em.merge(encuestador);
            em.flush();
        } catch (Exception e) {
            throw new ErrorIntegridad("error al actualizar el encuestador");
        }
    }

    @Override
    public List<Supervisor> getSupervisores() {
        List<Supervisor> supervisores = new ArrayList<Supervisor>();
        try {
            supervisores = tpm.getEm().createNamedQuery("Supervisor.findAll").getResultList();
        } catch (Exception ex) {
            UtilOut.println(ex);
        }
        return supervisores;
    }

    @Override
    public Supervisor guardarSupervisor(Supervisor supervisor) {
        try {
            tpm.getEm().persist(supervisor);
            tpm.getEm().flush();
            return supervisor;
        } catch (Exception e) {
            throw new ErrorIntegridad("error al guardar el encuestador");
        }
    }

    @Override
    public void actualizarSupervisor(Supervisor supervisor) {
        try {
            EntityManager em = tpm.getEm();
            em.merge(supervisor);
            em.flush();
        } catch (Exception e) {
            throw new ErrorIntegridad("error al actualizar el supervisor");
        }
    }

    @Override
    public List<String> getTiposIdentificacion() {
        List<String> tipos = new ArrayList<String>();
        try {
            String sql = "select codigo from INS_TIPOS_IDEN";
            tipos = tpm.getEm().createNativeQuery(sql)
                    .getResultList();
        } catch (Exception ex) {
            String msjError = "Error EncuestadorSupervisorFacade:getTiposIdentificacion() " + ex.getMessage();
            throw new ErrorGeneral("Error en EncuestadorSupervisorFacade", msjError);
        }
        return tipos;
    }

    @Override
    public boolean existeEncuestador(String identificacion) {
        boolean sw = false;
        try {
            Encuestador e = (Encuestador) tpm.getEm().createNamedQuery("Encuestador.findByIdentificacion")
                    .setParameter("identificacion", identificacion)
                    .getSingleResult();
            if (e != null) {
                sw = true;
            }

        } catch (Exception e) {
            //e.printStackTrace();
        }
        return sw;
    }

    @Override
    public boolean existeSupervisor(String identificacion) {
        boolean sw = false;
        try {
            Supervisor s = (Supervisor) tpm.getEm().createNamedQuery("Supervisor.findByIdentificacion")
                    .setParameter("identificacion", identificacion)
                    .getSingleResult();
            if (s != null) {
                sw = true;
            }

        } catch (Exception e) {
            //e.printStackTrace();
        }
        return sw;
    }
}
