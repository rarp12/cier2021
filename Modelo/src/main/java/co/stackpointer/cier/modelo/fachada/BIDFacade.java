/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.fachada;

import co.stackpointer.cier.modelo.entidad.BID.AmbitosBID;
import co.stackpointer.cier.modelo.entidad.BID.DatosInformeBID;
import co.stackpointer.cier.modelo.entidad.BID.IndicadoresBID;
import co.stackpointer.cier.modelo.entidad.BID.PaisesBID;
import co.stackpointer.cier.modelo.entidad.BID.PeriodosBID;
import co.stackpointer.cier.modelo.entidad.BID.TiposIndicadoresBID;
import co.stackpointer.cier.modelo.entidad.BID.ValoresBID;
import co.stackpointer.cier.modelo.entidad.BID.VariablesBID;
import co.stackpointer.cier.modelo.excepcion.ErrorIntegridad;
import co.stackpointer.cier.modelo.tipo.Ambitos;
import co.stackpointer.cier.modelo.utilidad.UtilOut;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author StackPointer
 */
@Stateless
public class BIDFacade implements BIDFacadeLocal {

    @Inject
    private TenantPersistenceManager tpm;

    @Override
    public List<PeriodosBID> getPeriodosBID() {
        List<PeriodosBID> periodos = new ArrayList<PeriodosBID>();
        try {
            tpm.getEm().clear(); 
            periodos = tpm.getEm().createNamedQuery("PeriodosBID.findAll")
                    .getResultList();
        } catch (Exception ex) {
            UtilOut.println(ex);
        }
        return periodos;
    }

    @Override
    public PaisesBID obtenerPaisBIDPorId(int idPaisBID) {
        PaisesBID p = null;
        try {
            tpm.getEm().clear();
            p = (PaisesBID) tpm.getEm().createNamedQuery("PaisesBID.buscarPorId")
                    .setParameter("idPais", idPaisBID)
                    .getSingleResult();
        } catch (Exception ex) {
            UtilOut.println(ex);
        }
        return p;
    }

    @Override
    public void guardarValorBID(ValoresBID valorBID) throws ErrorIntegridad {
        try {
            tpm.getEm().clear();
            tpm.getEm().persist(valorBID);
            tpm.getEm().flush();
        } catch (Exception e) {
            throw new ErrorIntegridad("No se pudo guardar el valor");
        }
    }

    @Override
    public void guardarIndicadorBID(IndicadoresBID indicadorBID) throws ErrorIntegridad {
        try {
            tpm.getEm().clear();
            tpm.getEm().persist(indicadorBID);
            tpm.getEm().flush();
        } catch (Exception e) {
            throw new ErrorIntegridad("No se pudo guardar el indicador");
        }
    }

    @Override
    public void guardarDatosInformeBID(DatosInformeBID datoInformeBID) throws ErrorIntegridad {
        try {
            tpm.getEm().clear();
            tpm.getEm().persist(datoInformeBID);
            tpm.getEm().flush();
        } catch (Exception e) {
            throw new ErrorIntegridad("No se pudo guardar el datoInformeBID");
        }
    }

    @Override
    public void guardarPeriodoBID(PeriodosBID periodoBID) throws ErrorIntegridad {
        try {
            tpm.getEm().clear();
            tpm.getEm().persist(periodoBID);
            tpm.getEm().flush();
        } catch (Exception e) {
            throw new ErrorIntegridad("No se pudo guardar el periodo");
        }
    }

    @Override
    public boolean existenValoresPaisPeriodo(PaisesBID paisBID, PeriodosBID periodoBID) {
        try {
            tpm.getEm().clear();
            List<ValoresBID> list = tpm.getEm().createNamedQuery("ValoresBID.existenValores")
                    .setParameter("paisBID", paisBID)
                    .setParameter("periodoBID", periodoBID)
                    .getResultList();
            if (list == null || list.isEmpty()) {
                return false;
            } else {
                return true;
            }
        } catch (NoResultException nre) {
            return false;
        } catch (Exception e) {
            throw new ErrorIntegridad("No se pudo verificar si existen valores");
        }
    }

    @Override
    public boolean existenIndicadoresPaisPeriodo(PaisesBID paisBID, PeriodosBID periodoBID) throws ErrorIntegridad {
        try {
            tpm.getEm().clear();
            List<ValoresBID> list = tpm.getEm().createNamedQuery("IndicadoresBID.existenValores")
                    .setParameter("paisBID", paisBID)
                    .setParameter("periodoBID", periodoBID)
                    .getResultList();
            if (list == null || list.isEmpty()) {
                return false;
            } else {
                return true;
            }
        } catch (NoResultException nre) {
            return false;
        } catch (Exception e) {
            throw new ErrorIntegridad("No se pudo verificar si existen indicadores");
        }
    }

    @Override
    public boolean existenDatosInformePaisPeriodo(PaisesBID paisBID, PeriodosBID periodoBID) throws ErrorIntegridad {
        try {
            tpm.getEm().clear();
            List<ValoresBID> list = tpm.getEm().createNamedQuery("DatosInformeBID.existenValores")
                    .setParameter("paisBID", paisBID)
                    .setParameter("periodoBID", periodoBID)
                    .getResultList();
            if (list == null || list.isEmpty()) {
                return false;
            } else {
                return true;
            }
        } catch (NoResultException nre) {
            return false;
        } catch (Exception e) {
            throw new ErrorIntegridad("No se pudo verificar si existen indicadores");
        }
    }

    @Override
    public void eliminaraValoresPaisPeriodo(PaisesBID paisBID, PeriodosBID periodoBID) {
        try {
            tpm.getEm().clear();
            tpm.getEm().createNamedQuery("ValoresBID.deleteValores")
                    .setParameter("paisBID", paisBID)
                    .setParameter("periodoBID", periodoBID)
                    .executeUpdate();
        } catch (Exception e) {
            throw new ErrorIntegridad("No se pudieron eliminar valores");
        }
    }

    @Override
    public void eliminaraIndicadoresPaisPeriodo(PaisesBID paisBID, PeriodosBID periodoBID) {
        try {
            tpm.getEm().clear();
            tpm.getEm().createNamedQuery("IndicadoresBID.deleteValores")
                    .setParameter("paisBID", paisBID)
                    .setParameter("periodoBID", periodoBID)
                    .executeUpdate();
        } catch (Exception e) {
            throw new ErrorIntegridad("No se pudieron eliminar indicadores");
        }
    }

    @Override
    public void eliminaraDatosInformePaisPeriodo(PaisesBID paisBID, PeriodosBID periodoBID) {
        try {
            tpm.getEm().clear();
            tpm.getEm().createNamedQuery("DatosInformeBID.deleteValores")
                    .setParameter("paisBID", paisBID)
                    .setParameter("periodoBID", periodoBID)
                    .executeUpdate();
        } catch (Exception e) {
            throw new ErrorIntegridad("No se pudieron eliminar indicadores");
        }
    }

    @Override
    public List<AmbitosBID> obtenerAmbitosBID() {
        List<AmbitosBID> ambitosBID = new ArrayList<AmbitosBID>();
        try {
            tpm.getEm().clear();
            TypedQuery<AmbitosBID> query = tpm.getEm().createNamedQuery("AmbitosBID.findAll", AmbitosBID.class);
            ambitosBID = query.getResultList();
        } catch (Exception ex) {
            UtilOut.println(ex);
        }
        return ambitosBID;
    }

    @Override
    public List<VariablesBID> obtenerVariablesEditablesBID() {
        List<VariablesBID> variablesBID = new ArrayList<VariablesBID>();
        try {
            tpm.getEm().clear();
            TypedQuery<VariablesBID> query = tpm.getEm().createNamedQuery("VariablesBID.findEditables", VariablesBID.class);
            variablesBID = query.getResultList();
        } catch (Exception ex) {
            UtilOut.println(ex);
        }
        return variablesBID;
    }

    @Override
    public void actualizarVariableBID(VariablesBID variableBID) throws ErrorIntegridad {
        try {
            tpm.getEm().clear();
            tpm.getEm().merge(variableBID);
            tpm.getEm().flush();
        } catch (Exception e) {
            throw new ErrorIntegridad("No se pudo actualizar la variable");
        }
    }

    @Override
    public void actualizarAmbitoBID(AmbitosBID ambitoBID) throws ErrorIntegridad {
        try {
            tpm.getEm().clear();
            tpm.getEm().merge(ambitoBID);
            tpm.getEm().flush();
        } catch (Exception e) {
            throw new ErrorIntegridad("No se pudo actualizar el ambito");
        }
    }

    @Override
    public void actualizarPeriodoBID(PeriodosBID periodoBID) throws ErrorIntegridad {
        try {
            tpm.getEm().clear();
            tpm.getEm().merge(periodoBID);
            tpm.getEm().flush();
        } catch (Exception e) {
            throw new ErrorIntegridad("No se pudo actualizar el periodo");
        }
    }

    @Override
    public List<Object[]> consultarCalificacionesBID(String codPeriodo) {

        StringBuilder sql = new StringBuilder();
        //SELECT
        sql.append("select pais.id, pais.codigo, pais.nombre, max(valores.periodo_info)");

        for (Ambitos amb : Ambitos.values()) {
            if (!amb.equals(Ambitos.A_RA) && !amb.equals(Ambitos.A_RN)) {
                sql.append(", round(coalesce(").append(amb.name()).append(".calif,0),2)");
            }
        }

        //FROM
        sql.append(" from BID_PAISES pais");
        sql.append(" inner join BID_PERIODOS periodos on periodos.codigo=?codPeriodo");
        sql.append(" inner join BID_VALORES valores on pais.id=valores.id_pais and valores.id_periodo=periodos.id");

        //JOINS
        for (Ambitos amb : Ambitos.values()) {
            if (!amb.equals(Ambitos.A_RA) && !amb.equals(Ambitos.A_RN)) {
                sql.append(" left join(");
                //en control y vigilancia puede ser mayor de 100 por la vulnerabilidad. Por eso se controla que si es mayor se le ponga 100
                sql.append(" select case when sum(to_number(val.valor,'99999.99999') * var.ponderacion)/100>100 then 100 else sum(to_number(val.valor,'99999.99999') * var.ponderacion)/100 end");
                sql.append(" calif, val.id_pais, max(val.periodo_info)");
                sql.append(" from BID_VALORES val, BID_PERIODOS p, BID_VARIABLES_AMBITO var");
                sql.append(" where ");
                sql.append(" p.codigo=?codPeriodo and var.cod_ambito='").append(amb.name()).append("'");
                sql.append(" and val.id_periodo=p.id and val.id_variable=var.id");
                sql.append(" group by val.id_pais");
                sql.append(" ) ").append(amb.name()).append(" on ").append(amb.name()).append(".id_pais=pais.id");

            }
        }
        sql.append(" group by pais.id, pais.codigo, pais.nombre");

        for (Ambitos amb : Ambitos.values()) {
            if (!amb.equals(Ambitos.A_RA) && !amb.equals(Ambitos.A_RN)) {
                sql.append(", ").append(amb.name()).append(".calif");
            }
        }

        tpm.getEm().clear();
        Query query = tpm.getEm().createNativeQuery(sql.toString());
        query.setParameter("codPeriodo", codPeriodo);

        return query.getResultList();
    }

    @Override
    public VariablesBID obtenerVariablePorId(int id) throws ErrorIntegridad {
        try {
            tpm.getEm().clear();
            List<VariablesBID> list = tpm.getEm().createNamedQuery("VariablesBID.obtenerPorId")
                    .setParameter("id", id)
                    .getResultList();
            if (list == null || list.isEmpty()) {
                return null;
            } else {
                return list.get(0);
            }
        } catch (NoResultException nre) {
            return null;
        } catch (Exception e) {
            throw new ErrorIntegridad("No se pudo obtener la variable");
        }
    }

    @Override
    public TiposIndicadoresBID obtenerTipoIndicadorPorCodigo(String codigo) throws ErrorIntegridad {
        try {
            tpm.getEm().clear();
            List<TiposIndicadoresBID> list = tpm.getEm().createNamedQuery("TiposIndicadoresBID.obtenerPorCodigo")
                    .setParameter("codigo", codigo)
                    .getResultList();
            if (list == null || list.isEmpty()) {
                return null;
            } else {
                return list.get(0);
            }
        } catch (NoResultException nre) {
            return null;
        } catch (Exception e) {
            throw new ErrorIntegridad("No se pudo obtener el tipo de indicador");
        }
    }

    @Override
    public List<IndicadoresBID> consultarIndicadoresBID(PeriodosBID periodoBID) throws ErrorIntegridad {
        try {
            tpm.getEm().clear();
            List<IndicadoresBID> list = tpm.getEm().createNamedQuery("IndicadoresBID.obtenerPorPeriodoBID")
                    .setParameter("periodoBID", periodoBID)
                    .getResultList();
            return list;
        } catch (NoResultException nre) {
            return null;
        } catch (Exception e) {
            throw new ErrorIntegridad("No se pudo obtener los indicadores");
        }
    }

    @Override
    public List<IndicadoresBID> consultarIndicadoresBIDPais(PeriodosBID periodoBID, PaisesBID paisBID) throws ErrorIntegridad {
        try {
            tpm.getEm().clear();
            List<IndicadoresBID> list = tpm.getEm().createNamedQuery("IndicadoresBID.obtenerPorPeriodoBIDPais")
                    .setParameter("periodoBID", periodoBID)
                    .setParameter("paisBID", paisBID)
                    .getResultList();
            return list;
        } catch (NoResultException nre) {
            return null;
        } catch (Exception e) {
            throw new ErrorIntegridad("No se pudo obtener los indicadores");
        }
    }
    
     @Override
    public List<IndicadoresBID> consultarIndicadoresBIDporID(PeriodosBID periodoBID) throws ErrorIntegridad {
        try {
            tpm.getEm().clear();
            List<IndicadoresBID> list = tpm.getEm().createNamedQuery("IndicadoresBID.obtenerPorPeriodoBIDporId")
                    .setParameter("idPeriodo", periodoBID.getId())
                    .getResultList();
            return list;
        } catch (NoResultException nre) {
            return null;
        } catch (Exception e) {
            throw new ErrorIntegridad("No se pudo obtener los indicadores");
        }
    }
     
     @Override
     public boolean consultarPeriodoCodigo(String periodoBID)throws ErrorIntegridad{
        try { 
            tpm.getEm().createNamedQuery("PeriodosBID.buscarPorCodigo").setParameter("codigo", periodoBID).getSingleResult();
            return true;
        } catch (NoResultException nre) {
            return false;
        } catch (Exception e) {
            throw new ErrorIntegridad("No se pudo obtener el periodo");
        }
     }


    @Override
    public List<TiposIndicadoresBID> consultarTiposIndicadoresBID() throws ErrorIntegridad {
        try {
            tpm.getEm().clear();
            List<TiposIndicadoresBID> list = tpm.getEm().createNamedQuery("TiposIndicadoresBID.obtenerTodos")
                    .getResultList();
            return list;
        } catch (NoResultException nre) {
            return null;
        } catch (Exception e) {
            throw new ErrorIntegridad("No se pudo obtener los tipos de indicador");
        }
    }

    @Override
    public List<DatosInformeBID> consultarDatosInformeBID(PeriodosBID periodoBID, Long idPais) throws ErrorIntegridad {
        try {
            tpm.getEm().clear();
            List<DatosInformeBID> list = tpm.getEm().createNamedQuery("DatosInformeBID.obtenerPorPeriodoPaisBID")
                    .setParameter("periodoBID", periodoBID)
                    .setParameter("idPais", idPais)
                    .getResultList();
            return list;
        } catch (NoResultException nre) {
            return null;
        } catch (Exception e) {
            throw new ErrorIntegridad("No se pudo obtener los datosInforme");
        }
    }

    @Override
    public void eliminarPeriodoBID(PeriodosBID periodoBID) throws ErrorIntegridad {
        try {
            tpm.getEm().clear();
            tpm.getEm().createNamedQuery("PeriodosBID.delete")
                    .setParameter("id", periodoBID.getId())
                    .executeUpdate();
        } catch (Exception e) {
            throw new ErrorIntegridad("No se pudo eliminar el periodo");
        }
    }
}
