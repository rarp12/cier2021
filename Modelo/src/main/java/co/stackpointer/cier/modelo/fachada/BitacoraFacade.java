/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.fachada;

import co.stackpointer.cier.modelo.entidad.sistema.Bitacora;
import co.stackpointer.cier.modelo.filtro.bitacora.FiltroConsultaBitacora;
import co.stackpointer.cier.modelo.tipo.BitacoraModulo;
import co.stackpointer.cier.modelo.utilidad.UtilOut;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author rarp1
 */
@Stateless
public class BitacoraFacade implements BitacoraFacadeLocal {

    @Inject
    private TenantPersistenceManager tpm;
    @PersistenceContext(unitName = "Model")
    private EntityManager em;

    @Override
    public List<Bitacora> consultarBitacora(FiltroConsultaBitacora filtro) {
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT i FROM Bitacora i WHERE i.fecha BETWEEN :f1 AND :f2 ");
        List<Bitacora> bitacoras = new ArrayList<Bitacora>();

        if (filtro.getBoletaCensal() != null) {
            sql.append(" AND i.boletaCensal = :p1");
        }
        if (filtro.getUsuario()!= null) {
            sql.append(" AND i.usuario = :p2");
        }
        if (filtro.getModulo()!= null) {
            sql.append(" AND i.modulo = :p3");
        }
       sql.append(" ORDER BY i.fecha desc ");
        Query query = tpm.getEm().createQuery(sql.toString(), Bitacora.class);
        query.setParameter("f1", filtro.getFechaInicio());
        Calendar c = Calendar.getInstance(); 
        c.setTime(filtro.getFechaFin()); 
        c.add(Calendar.DATE, 1);
        query.setParameter("f2", c.getTime());
        if (filtro.getBoletaCensal() != null) {
            query.setParameter("p1", filtro.getBoletaCensal());
        }
        if (filtro.getUsuario() != null) {
            query.setParameter("p2", filtro.getUsuario());
        }
        if (filtro.getModulo() != null) {
            BitacoraModulo bm = BitacoraModulo.valueOf(filtro.getModulo());
            query.setParameter("p3", bm);
        }
        try {

            bitacoras = query.getResultList();
        } catch (Exception ex) {
            UtilOut.println(ex);
        }
        return bitacoras;

    }
    
    @Override
    public void eliminarBitacora(){
        try {
            tpm.getEm().clear();
            tpm.getEm().createNamedQuery("Bitacora.deleteAll").executeUpdate();
        } catch (Exception ex) {
            UtilOut.println(ex);
        }
    }
}
