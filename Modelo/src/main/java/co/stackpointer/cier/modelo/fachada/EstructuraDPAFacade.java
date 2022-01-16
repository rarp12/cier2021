/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.fachada;

import co.stackpointer.cier.modelo.auditoria.InterceptorAdministracion;
import co.stackpointer.cier.modelo.entidad.dpa.ConfiguracionNivel;
import co.stackpointer.cier.modelo.entidad.dpa.ConfiguracionNivelAux;
import co.stackpointer.cier.modelo.entidad.dpa.Nivel;
import co.stackpointer.cier.modelo.entidad.establecimiento.Establecimiento;
import co.stackpointer.cier.modelo.entidad.establecimiento.Predio;
import co.stackpointer.cier.modelo.entidad.establecimiento.Sede;
import co.stackpointer.cier.modelo.entidad.indicador.ConfiguracionNivelHis;
import co.stackpointer.cier.modelo.entidad.indicador.EstablecimientoHis;
import co.stackpointer.cier.modelo.entidad.indicador.EstablecimientoHisPK;
import co.stackpointer.cier.modelo.entidad.indicador.NivelHis;
import co.stackpointer.cier.modelo.entidad.indicador.NivelHisPK;
import co.stackpointer.cier.modelo.entidad.indicador.PredioHis;
import co.stackpointer.cier.modelo.entidad.indicador.PredioHisPK;
import co.stackpointer.cier.modelo.entidad.indicador.SedeHis;
import co.stackpointer.cier.modelo.entidad.indicador.SedeHisPK;
import co.stackpointer.cier.modelo.excepcion.ErrorIntegridad;
import co.stackpointer.cier.modelo.tipo.ParamNivelConsul;
import co.stackpointer.cier.modelo.utilidad.UtilProperties;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.sql.DataSource;

/**
 *
 * @author user
 */
@Stateless
public class EstructuraDPAFacade implements EstructuraDPAFacadeLocal {
      
    @Inject
    private TenantPersistenceManager tpm;
    
    @EJB
    private ParametrosFacadeLocal facParam;    
    
    @Override
    public NivelHis consultarNivelHis(NivelHisPK pk) {
        NivelHis nivel = tpm.getEm().find(NivelHis.class, pk);
        return nivel;
    }
        
    @Override
    public NivelHis consultarNivelHis(Integer periodo, String codigoNivel) {
        try {
            TypedQuery<NivelHis> query = tpm.getEm().createNamedQuery("NivelHis.consultarPorPeriodoCodigo", NivelHis.class);
            query.setParameter("periodo", periodo);
            query.setParameter("codigo", codigoNivel);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    @Override
    public String consultarDescripcionNivelDPA(Long nivel, Long idIdioma) {
        String descripcion;
        try {
            String sql = "select tb.descripcion from sys_nivel_dpa_idioma tb where tb.id_tnt = ?tenant and nivel = ?nivel and idioma = ?idIdioma";
            descripcion = (String) tpm.getEm().createNativeQuery(sql)
                    .setParameter("tenant", tpm.getCurrentTenant())
                    .setParameter("nivel", nivel)
                    .setParameter("idIdioma", idIdioma)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
        return descripcion;    
    }
    
    @Override
    public String consultarDescripcionNivelDPA (Long nivel, Integer periodo, Long idIdioma) {
        String descripcion;
        try {
            String sql = "select tb.descripcion from ind_sys_nivel_dpa_idioma tb "
                    + "where tb.id_tnt = ?tenant and tb.periodo = ?periodo and nivel = ?nivel and idioma = ?idIdioma";
            descripcion = (String) tpm.getEm().createNativeQuery(sql)
                    .setParameter("tenant", tpm.getCurrentTenant())
                    .setParameter("periodo", periodo)
                    .setParameter("nivel", nivel)
                    .setParameter("idIdioma", idIdioma)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
        return descripcion;    
    }
    
    @Override
    public List<NivelHis> consultarNivelesHis(Integer periodo, Long idpadre) {
        
        TypedQuery<NivelHis> query = tpm.getEm().createNamedQuery("NivelHis.consultarPorConfYPadre", NivelHis.class);
        query.setParameter("periodo", periodo);
        query.setParameter("idpadre", idpadre);
        return query.getResultList();
    }

    @Override
    public EstablecimientoHis consultarEstablecimientoHis(EstablecimientoHisPK pk) {
        EstablecimientoHis establecimiento = tpm.getEm().find(EstablecimientoHis.class, pk);
        return establecimiento;
    }

    @Override
    public SedeHis consultarSedeHis(SedeHisPK pk) {
        SedeHis sede = tpm.getEm().find(SedeHis.class, pk);
        return sede;
    }

    @Override
    public PredioHis consultarPredioHis(PredioHisPK pk) {
        PredioHis predio = tpm.getEm().find(PredioHis.class, pk);
        return predio;
    }    

    @Override
    public List<ConfiguracionNivelHis> consultarConfNivelesHis(Integer periodo) {
        TypedQuery<ConfiguracionNivelHis> query = tpm.getEm().createNamedQuery("ConfiguracionNivelHis.consultarPorPeriodo", ConfiguracionNivelHis.class);
        query.setParameter("periodo", periodo);
        return query.getResultList();
    }
  
    @Override
    public List<ConfiguracionNivelHis> consultarConfNivelesHis(Integer periodo, Long nivel) {
        TypedQuery<ConfiguracionNivelHis> query = tpm.getEm().createNamedQuery("ConfiguracionNivelHis.consultarPorPeriodoNivelEspecifico", ConfiguracionNivelHis.class);
        query.setParameter("periodo", periodo);
        query.setParameter("nivel", nivel);
        return query.getResultList();
    }
   
    @Override
    public List<EstablecimientoHis> consultarEstablecimientosHis(Integer periodo, Long idnivel) {
        TypedQuery<EstablecimientoHis> query = tpm.getEm().createNamedQuery("EstablecimientoHis.consultarPorPeridoYNivel", EstablecimientoHis.class);
        query.setParameter("periodo", periodo);
        query.setParameter("idnivel", idnivel);
        return query.getResultList();
    }

// actuales
     @Override
    public Nivel consultarNivel(Long id) {
        Nivel nivel = tpm.getEm().find(Nivel.class, id);
        return nivel;
    }
     
     
    @Override
    public Nivel consultarNivel(String codigo) {
        try {
            TypedQuery<Nivel> query = tpm.getEm().createNamedQuery("Nivel.porCodigo", Nivel.class);
            query.setParameter("codigo", codigo);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Establecimiento consultarEstablecimiento(Long id) {
        Establecimiento establecimiento = tpm.getEm().find(Establecimiento.class, id);
        return establecimiento;
    }

    @Override
    public Sede consultarSede(Long id) {
        Sede sede = tpm.getEm().find(Sede.class, id);
        return sede;
    }

    @Override
    public Predio consultarPredio(Long id) {
        Predio predio = tpm.getEm().find(Predio.class, id);
        return predio;
    }

    @Override
    public List<Nivel> consultarNiveles(ParamNivelConsul nivelConsu, Long idpadre) {
        TypedQuery<Nivel> query = tpm.getEm().createNamedQuery("Nivel.consultarPorConfYPadreId", Nivel.class);
        query.setParameter("idConf", nivelConsu.getNivel());
        query.setParameter("idpadre", idpadre);
        return query.getResultList();
    }

    @Override
    public List<ConfiguracionNivel> consultarConfNivelesActual() {
        TypedQuery<ConfiguracionNivel> query = tpm.getEm().createNamedQuery("ConfiguracionNivel.consultarTodos", ConfiguracionNivel.class);
        return query.getResultList();
    }
  
    @Override
    public List<ConfiguracionNivel> consultarConfNivelesActual(Long nivel) {
        TypedQuery<ConfiguracionNivel> query = tpm.getEm().createNamedQuery("ConfiguracionNivel.consultarNivelEspecifico", ConfiguracionNivel.class);
        query.setParameter("nivel", nivel);
        return query.getResultList();
    }
    
    @Override
    public List<ConfiguracionNivel> consultarConfNivelesActualInverso(Long nivel) {
        TypedQuery<ConfiguracionNivel> query = tpm.getEm().createNamedQuery("ConfiguracionNivel.consultarNivelEspecificoInverso", ConfiguracionNivel.class);
        query.setParameter("nivel", nivel);
        return query.getResultList();
    }
   
    @Override
    public List<Establecimiento> consultarEstablecimientos(Long idnivel) {
        TypedQuery<Establecimiento> query = tpm.getEm().createNamedQuery("Establecimiento.consultarPorNivel", Establecimiento.class);
        query.setParameter("idnivel", idnivel);
        return query.getResultList();
    }
    
    @Override
    public List<Sede> consultarSedes(String codEstablecimiento){
        TypedQuery<Sede> query = tpm.getEm().createNamedQuery("Sede.porCodigoEstablecimiento", Sede.class);
        query.setParameter("codEstablecimiento", codEstablecimiento);
        return query.getResultList();
    }
    

    @Override
    public List<Sede> consultarSedes(Long idEstablecimiento){
        TypedQuery<Sede> query = tpm.getEm().createNamedQuery("Sede.consultarPorEstablecimiento", Sede.class);
        query.setParameter("idEstablecimiento", idEstablecimiento);
        return query.getResultList();
    }
    
    @Override
    public ConfiguracionNivel getConfiguracionNivel(Long nivel) {
        try {
            TypedQuery<ConfiguracionNivel> query = tpm.getEm().createNamedQuery("ConfiguracionNivel.porNivel", ConfiguracionNivel.class);
            query.setParameter("nivel", nivel);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<ConfiguracionNivel> consultarConfNivelesAuxActual() {
        TypedQuery<ConfiguracionNivel> query = tpm.getEm().createNamedQuery("ConfiguracionNivel.consultarTodos", ConfiguracionNivel.class);
        return query.getResultList();
    }

    @Override
    public List<Nivel> consultarNivelesPorConfiguracion(ParamNivelConsul nivelConsulta) {
        try {
            TypedQuery<Nivel> query = tpm.getEm().createNamedQuery("Nivel.consultarPorConfiguracion", Nivel.class);
            query.setParameter("idNivelConfiguracion", nivelConsulta.getNivel());
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Nivel> consultarNiveles(ParamNivelConsul nivelConsu, Nivel padre) {
            TypedQuery<Nivel> query = tpm.getEm().createNamedQuery("Nivel.consultarPorConfYPadre", Nivel.class);
            query.setParameter("idConf", nivelConsu.getNivel());
            query.setParameter("padre", padre);
            return query.getResultList();
        }

    @Override
    public List<Nivel> consultarNivelAux(Long idDpa, Long idMaximoNivelDPA){
        String sql = UtilProperties.getProperties().getProperty("queryConsultarNivelAux");
        Query query = tpm.getEm().createNativeQuery(sql, Nivel.class);
        query.setParameter(1, idDpa);
        query.setParameter(2, idMaximoNivelDPA);
        return query.getResultList();
    }
    
    @Override
    public Nivel consultarNivelAux(Long id) {
        Nivel nivel = tpm.getEm().find(Nivel.class, id);
        return nivel;
    }    

    @Override
    public Nivel consultarNivelAux(String codigo, Long nivel) {
        try {
            TypedQuery<Nivel> query = tpm.getEm().createNamedQuery("Nivel.porCodigoNivel", Nivel.class);
            query.setParameter("codigo", codigo);
            query.setParameter("nivel", nivel);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public boolean estanRelacionados(String codigoHijo, String codigoPadre, Long nivelHijo){
        try {
            TypedQuery<Nivel> query = tpm.getEm().createNamedQuery("Nivel.porCodigoNivel", Nivel.class);
            query.setParameter("codigo", codigoHijo);
            query.setParameter("nivel", nivelHijo);
            query.setParameter("codPadre", codigoPadre);            
            query.getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }
    
    @Override
    public List<Establecimiento> consultarEstablecimientosAux() {
        TypedQuery<Establecimiento> query = tpm.getEm().createNamedQuery("Establecimiento.findAll", Establecimiento.class);
        return query.getResultList();
    }

    @Override
    public List<Establecimiento> consultarEstablecimientosAux(Nivel nivel) {
        TypedQuery<Establecimiento> query = tpm.getEm().createNamedQuery("Establecimiento.consultarPorNivelCompleto", Establecimiento.class);
        query.setParameter("nivel", nivel);
        return query.getResultList();
    }

    @Override
    public List<Establecimiento> consultarEstablecimientosAux(Long idDpa, Long maximoNivelDPA) {
        String sql = UtilProperties.getProperties().getProperty("queryConsultarEstablecimientosAux");
        Query query = tpm.getEm().createNativeQuery(sql, Establecimiento.class);
        query.setParameter(1, idDpa);
        query.setParameter(2, maximoNivelDPA);
        return query.getResultList();
    }

    @Override
    public Sede consultarSedeAux(Long id) {
        Sede sede = tpm.getEm().find(Sede.class, id);
        return sede;
    }

    @Override
    public List<Sede> consultarSedesAux() {
        TypedQuery<Sede> query = tpm.getEm().createNamedQuery("Sede.findAll", Sede.class);
        return query.getResultList();
    }

    @Override
    public List<Sede> consultarSedesAuxDiferentesSedeSeleccionada(Sede sede){
        TypedQuery<Sede> query = tpm.getEm().createNamedQuery("Sede.diferentesAsedeSeleccionada", Sede.class);
        query.setParameter("sede", sede);
        return query.getResultList();
    }
    
    @Override
    public List<Sede> consultarSedesAux(Long idEstablecimiento) {
        TypedQuery<Sede> query = tpm.getEm().createNamedQuery("Sede.consultarPorEstablecimiento", Sede.class);
        query.setParameter("idEstablecimiento", idEstablecimiento);
        return query.getResultList();
    }

    @Override
    public List<Sede> consultarSedesAux(Long idDpa, Long maximoNivelDPA) {
        String sql = UtilProperties.getProperties().getProperty("queryConsultarSedesAux");        
        Query query = tpm.getEm().createNativeQuery(sql, Sede.class);
        query.setParameter(1, idDpa);
        query.setParameter(2, maximoNivelDPA);
        return query.getResultList();
    }

    @Override
    public List<Sede> consultarSedesAux(Nivel nivel) {
        TypedQuery<Sede> query = tpm.getEm().createNamedQuery("Sede.porNivel", Sede.class);
        query.setParameter("nivel", nivel);
        return query.getResultList();
    }
    
    @Override
    public boolean existeCodigoDpa(String codigo, Long nivel) {
        boolean sw = false;
        try {
            Nivel n = consultarNivelAux(codigo, nivel);
            if (n != null) {
                sw = true;
            }

        } catch (Exception e) {
            //e.printStackTrace();
        }
        return sw;
    }

    @Override
    public boolean existeDescripcionDpa(Nivel nivel) {
        boolean sw = false;
        try {
            Nivel n = (Nivel) tpm.getEm().createNamedQuery("Nivel.porDescripcionNivelPadre")
                    .setParameter("descripcion", nivel.getDescripcion().toUpperCase())
                    .setParameter("configuracion", nivel.getConfiguracion())
                    .setParameter("padre", nivel.getPadre())
                    .getSingleResult();
            if (n != null) {                
                sw = true;
            }

        } catch (Exception e) {
            //e.printStackTrace();
        }
        return sw;
    }

    @Override
    public boolean existeDescripcionDpaNoId(Nivel nivel) {
        boolean sw = false;
        try {
            Nivel n = (Nivel) tpm.getEm().createNamedQuery("Nivel.porDescripcionNivelPadreNoId")
                    .setParameter("descripcion", nivel.getDescripcion())
                    .setParameter("configuracion", nivel.getConfiguracion())
                    .setParameter("padre", nivel.getPadre())
                    .setParameter("id", nivel.getId())
                    .getSingleResult();
            if (n != null) {
                sw = true;
            }

        } catch (Exception e) {
            //e.printStackTrace();
        }
        return sw;
    }

    @Override
    public boolean tieneSubDivisiones(Nivel nivel) {
        boolean sw = false;
        try {
            List<Nivel> niveles = tpm.getEm().createNamedQuery("Nivel.porPadre")
                    .setParameter("padre", nivel)
                    .getResultList();
            if (!niveles.isEmpty()) {
                sw = true;
            }

        } catch (Exception e) {
            //e.printStackTrace();
        }
        return sw;
    }
    
    @Override
    public boolean relacionesDPAIncompleta() {
        boolean sw = false;
        String sql;
        String result;
        int tenant;
        try {
            tenant = tpm.getCurrentTenant().intValue();
            sql = UtilProperties.getProperties().getProperty("relacionesDPAIncompleta");
            Query query = tpm.getEm().createNativeQuery(sql)
                    .setParameter("tenant", tenant);
            result= query.getSingleResult().toString();
            int sinPadres = Integer.parseInt(result);
            if (sinPadres > 0) {
                sw = true;
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return sw;
    }

    @Override
    @Interceptors({InterceptorAdministracion.class})
    public Nivel guardarDpa(String usernameSesion, Nivel dpa) {
        try {
            tpm.getEm().persist(dpa);
            tpm.getEm().flush();
            return dpa;
        } catch (Exception e) {
            throw new ErrorIntegridad("error al guardar el dpa");
        }
    }

    @Override
    @Interceptors({InterceptorAdministracion.class})
    public void actualizarDpa(String usernameSesion, Nivel dpa) {
        try {
            EntityManager em = tpm.getEm();
            em.merge(dpa);
            em.flush();
        } catch (Exception e) {
            throw new ErrorIntegridad("error al actualizar el dpa");
        }
    }

    @Override
    @Interceptors({InterceptorAdministracion.class})
    public void eliminarDpa(String usernameSesion, Nivel dpa) {
        try {            
            Nivel nivel = tpm.getEm().find(Nivel.class, dpa.getId());
            tpm.getEm().remove(nivel);
            tpm.getEm().flush();
        } catch (Exception e) {
            throw new ErrorIntegridad("error al eliminar el dpa");
        }
    }

    @Override
    @Interceptors({InterceptorAdministracion.class})
    public void guardarNivelConfiguracion(String usernameSesion, Integer tenant, Integer posicion, String descripcion) throws Exception {
        Connection conn = null;
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("jdbc/DBCierDS");
            conn = ds.getConnection();

            CallableStatement cs = null;
            //cs = conn.prepareCall("call ADMIN_DPA.agregar_nivel_conf(?,?,?)");
            cs = conn.prepareCall(UtilProperties.getProperties().getProperty("agregarNivelConf"));

            cs.setInt(1, tenant);
            cs.setInt(2, posicion);
            cs.setString(3, descripcion);

            //Ejecución del procedimiento
            cs.execute();
            cs.close();
        } catch (Exception e) {
            throw new Exception();
        } finally {
            conn.close();
        }
    }
    
     @Override
     @Interceptors({InterceptorAdministracion.class})
    public void actualizarNivelConfiguracion(String usernameSesion, Integer tenant, Long idNivelConf, String descripcion) throws Exception {
        Connection conn = null;
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("jdbc/DBCierDS");
            conn = ds.getConnection();

            CallableStatement cs = null;
            //cs = conn.prepareCall("call admin_dba_editar_nivel_conf(?,?,?)");
            cs = conn.prepareCall(UtilProperties.getProperties().getProperty("editarNivelConf"));

            cs.setInt(1, tenant);
            cs.setInt(2, idNivelConf.intValue());
            cs.setString(3, descripcion);

            //Ejecución del procedimiento
            cs.execute();
            cs.close();
        } catch (Exception e) {
            throw new Exception();
        } finally {
            conn.close();
        }
    }
       
    @Override
    @Interceptors({InterceptorAdministracion.class})
    public void eliminarNivelConfiguracion(String usernameSesion, Integer tenant, Integer idNivelConf, Integer posicion) throws Exception {
        Connection conn = null;
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("jdbc/DBCierDS");
            conn = ds.getConnection();

            CallableStatement cs = null;
            //cs = conn.prepareCall("call ADMIN_DPA.eliminar_nivel_conf(?,?,?)");
            cs = conn.prepareCall(UtilProperties.getProperties().getProperty("eliminarNivelConf"));

            cs.setInt(1, tenant);
            cs.setInt(2, idNivelConf);
            cs.setInt(3, posicion);

            //Ejecución del procedimiento
            cs.execute();
            cs.close();
        } catch (Exception e) {
            throw new Exception();
        } finally {
            conn.close();
        }
    }
    
    public void actualizarDpaBD(Long idNivel){
        Connection conn = null;
        CallableStatement cs = null;
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("jdbc/DBCierDS");
            conn = ds.getConnection();
            cs = conn.prepareCall(UtilProperties.getProperties().getProperty("actualizarDPA"));           
            cs.setLong(1, tpm.getCurrentTenant());
            cs.setLong(2, idNivel);            
            cs.execute();            
        } catch (Exception exp) {
            exp.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (cs != null) {
                    cs.close();
                }
            } catch (SQLException ex) { 
                ex.printStackTrace();
            }
        }
    }
    
}