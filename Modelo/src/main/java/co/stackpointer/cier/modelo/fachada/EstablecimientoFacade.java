/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.fachada;

import co.stackpointer.cier.modelo.auditoria.InterceptorAdministracion;
import co.stackpointer.cier.modelo.entidad.dpa.Nivel;
import co.stackpointer.cier.modelo.entidad.establecimiento.Establecimiento;
import co.stackpointer.cier.modelo.entidad.establecimiento.Predio;
import co.stackpointer.cier.modelo.entidad.establecimiento.Sede;
import co.stackpointer.cier.modelo.excepcion.ErrorIntegridad;
import co.stackpointer.cier.modelo.excepcion.ErrorValidacion;
import co.stackpointer.cier.modelo.tipo.Estado;
import co.stackpointer.cier.modelo.utilidad.UtilFecha;
import co.stackpointer.cier.modelo.utilidad.UtilProperties;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.sql.DataSource;

/**
 *
 * @author user
 */
@Stateless
public class EstablecimientoFacade implements EstablecimientoFacadeLocal {

    @Inject
    private TenantPersistenceManager tpm;
    
    @Override
    @Interceptors({InterceptorAdministracion.class})
    public void guardarEstablecimiento(String usernameSesion, Establecimiento establecimiento) {
        try {
            establecimiento.setPeriodo(UtilFecha.obtenerPeriodoActual());
            establecimiento.setEstado(Estado.A);
            tpm.getEm().clear();
            tpm.getEm().persist(establecimiento);
            tpm.getEm().flush();
        } catch (Exception e) {
            throw new ErrorIntegridad("No se pudo guardar el establecimiento: " + e);
        }
    }
  
    @Override
    @Interceptors({InterceptorAdministracion.class})
    public void actualizarEstablecimiento(String usernameSesion, Establecimiento establecimiento) {
        try {
            tpm.getEm().clear();
            tpm.getEm().merge(establecimiento);
            tpm.getEm().flush();
        } catch (Exception e) {
            throw new ErrorIntegridad("No se pudo actualizar el establecimiento");
        }
    }

    @Override
    @Interceptors({InterceptorAdministracion.class})
    public void eliminarEstablecimiento(String usernameSesion, Establecimiento establecimiento) {
        try {
            tpm.getEm().clear();
            tpm.getEm().remove(tpm.getEm().contains(establecimiento) ? establecimiento : tpm.getEm().merge(establecimiento));
            tpm.getEm().flush();
        } catch (Exception e) {
            throw new ErrorIntegridad("No se pudo eliminar el establecimiento");
        }
    }

    @Override
    public Nivel getNivelPorCodigo(String codigo) {
        try {
            tpm.getEm().clear();
            return (Nivel) tpm.getEm().createNamedQuery("Nivel.porCodigo")
                    .setParameter("codigo", codigo)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        } catch (Exception e) {
            throw new ErrorValidacion("No se pudo obtener el nivel");
        }
    }

    @Override
    @Interceptors({InterceptorAdministracion.class})
    public void guardarSede(String usernameSesion, Sede sede) {
        try {
            sede.setPeriodo(UtilFecha.obtenerPeriodoActual());
            sede.setEstado(Estado.A);
            tpm.getEm().clear();
            tpm.getEm().persist(sede);
            tpm.getEm().flush();
        } catch (Exception e) {
            throw new ErrorIntegridad("No se pudo guardar la sede: " + e);
        }
    }

    @Override
    @Interceptors({InterceptorAdministracion.class})
    public void actualizarSede(String usernameSesion, Sede sede) {
        try {
            tpm.getEm().clear();
            tpm.getEm().merge(sede);
            tpm.getEm().flush();
        } catch (Exception e) {
            throw new ErrorIntegridad("No se pudo actualizar la sede");
        }
    }
    
    private void actualizarSede(Sede sede) {
        try {
            tpm.getEm().clear();
            tpm.getEm().merge(sede);
            tpm.getEm().flush();
        } catch (Exception e) {
            throw new ErrorIntegridad("No se pudo actualizar la sede");
        }
    }

    @Override
    @Interceptors({InterceptorAdministracion.class})
    public void eliminarSede(String usernameSesion, Sede sede) {
        try {
            tpm.getEm().clear();
            tpm.getEm().remove(tpm.getEm().contains(sede) ? sede : tpm.getEm().merge(sede));
            tpm.getEm().flush();
        } catch (Exception e) {
            throw new ErrorIntegridad("No se pudo eliminar la sede");
        }
    }

    @Override
    public Establecimiento getEstablecimientoPorCodigo(String codigo) {
        try {
            tpm.getEm().clear();
            return (Establecimiento) tpm.getEm().createNamedQuery("Establecimiento.porCodigo")
                    .setParameter("codigo", codigo)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        } catch (Exception e) {
            throw new ErrorValidacion("No se pudo obtener el establecimiento: " + e);
        }
    }

    @Override
    public Predio buscarPredioPorId(Long id) {
        try {
            tpm.getEm().clear();
            return (Predio) tpm.getEm().createNamedQuery("Predio.buscarPorId")
                    .setParameter("idPredio", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Sede getSedePorCodigo(String codigo) {
        try {
            tpm.getEm().clear();
            return (Sede) tpm.getEm().createNamedQuery("Sede.porCodigo")
                    .setParameter("codigo", codigo)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        } catch (Exception e) {
            throw new ErrorValidacion("No se pudo obtener la sede: " + e);
        }
    }

    @Override
    public boolean existeSede(String codigo) {
        boolean sw = false;
        try {
            Sede n = (Sede) tpm.getEm().createNamedQuery("Sede.porCodigo")
                    .setParameter("codigo", codigo)
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
    public boolean existeEstablecimiento(String codigo) {
        boolean sw = false;
        try {
            Establecimiento n = (Establecimiento) tpm.getEm().createNamedQuery("Establecimiento.porCodigo")
                    .setParameter("codigo", codigo)
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
    public boolean existeSedeEstablecimiento(String codSede, String codEstablecimiento) {
        boolean sw = false;
        try {
            Sede n = (Sede) tpm.getEm().createNamedQuery("Sede.porCodigoSedeCodigoEstablecimiento")
                    .setParameter("codSede", codSede)
                    .setParameter("codEstablecimiento", codEstablecimiento)
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
    public boolean sedeSinInstrumento(String codigoSede) {
        try {
            tpm.getEm().clear();
            List p = tpm.getEm().createNamedQuery("Instrumento.consultaPorCodigoSede")
                    .setParameter("codigoSede", codigoSede)
                    .getResultList();
            if (p != null && p.size() > 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            return true;
        }
    }

    @Override
    public int ingresarCoordenada(String codSede, String coordenadaX, String coordenadaY) {
        Connection conn = null;
        CallableStatement cs = null;
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("jdbc/DBCierDS");
            conn = ds.getConnection();
            cs = conn.prepareCall(UtilProperties.getProperties().getProperty("ingresarCoordenadas"));
            cs.registerOutParameter(1, Types.INTEGER);
            cs.setString(2, codSede);
            cs.setString(3, coordenadaX);
            cs.setString(4, coordenadaY);
            cs.execute();
            return cs.getInt(1);
        } catch (Exception exp) {
            return 0;
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (cs != null) {
                    cs.close();
                }
            } catch (SQLException ex) {
                return 0;
            }
        }
    }

    @Override
    public boolean existePredioCodOf(String codigoOficial) {
        try {
            tpm.getEm().clear();
            List p = tpm.getEm().createNamedQuery("Predio.findByCodigoOficial")
                    .setParameter("codigoOficial", codigoOficial)
                    .getResultList();
            if (p != null && p.size() > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean existeCodigoEst(String codigo) {
        boolean sw = false;
        try {
            Establecimiento n = (Establecimiento) tpm.getEm().createNamedQuery("Establecimiento.porCodigo")
                    .setParameter("codigo", codigo)
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
    public boolean existeNombreEst(String nombre) {
        boolean sw = false;
        try {
            Establecimiento est = (Establecimiento) tpm.getEm().createNamedQuery("Establecimiento.porNombre")
                    .setParameter("nombre", nombre)
                    .getSingleResult();
            if (est != null) {
                sw = true;
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return sw;
    }

    @Override
    public boolean existeCodigoSed(String codigo) {
        boolean sw = false;
        try {
            Sede n = (Sede) tpm.getEm().createNamedQuery("Sede.porCodigo")
                    .setParameter("codigo", codigo)
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
    public boolean existeNombreSed(String nombre) {
        boolean sw = false;
        try {
            Sede est = (Sede) tpm.getEm().createNamedQuery("Sede.porNombre")
                    .setParameter("nombre", nombre)
                    .getSingleResult();
            if (est != null) {
                sw = true;
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return sw;
    }

    @Override
    public boolean tieneHijos(Sede sede) {       
        try {
            Sede s = (Sede) tpm.getEm().createNamedQuery("Sede.findPorId")
                    .setParameter("idSede", sede.getId())
                    .getSingleResult();
            if (s != null) {
                List<Predio> predios = s.getPredios();
                if (!predios.isEmpty()) {
                    return true;
                }
            }
        } catch (Exception e) {
            return false;//e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean tieneHijos(Establecimiento establecimiento) {
       try {
            List<Sede> sedes = tpm.getEm().createNamedQuery("Sede.consultarPorEstablecimiento")
                    .setParameter("idEstablecimiento", establecimiento.getId())
                    .getResultList();
            if (!sedes.isEmpty()) {
                return true;
            }
        } catch (Exception e) {
            return false;//e.printStackTrace();
        }
        return false;
    }

    @Override
    @Interceptors({InterceptorAdministracion.class})
    public void guardarPredio(String usernameSesion, Predio predio, Sede sede) {
        try {
            EntityManager em = tpm.getEm();
            List<Sede> sedes = new ArrayList<Sede>();
            sedes.add(sede);
            predio.setSede(sedes);
            predio.setEstado("A");
            predio.setFechaCreacion(UtilFecha.obtenerFechaActual());
            em.persist(predio);
            em.flush();
            sede.getPredios().add(predio);
            actualizarSede(sede);
        } catch (Exception e) {
            throw new ErrorIntegridad("No se pudo guardar el predio");
        }
    }

    @Override
    @Interceptors({InterceptorAdministracion.class})
    public void actualizarPredio(String usernameSesion, Predio predio, Sede sede) {
        try {
            EntityManager em = tpm.getEm();
            em.clear();
            em.merge(predio);
            em.flush();
            Sede sedeActual = predio.getSede().get(0);
            if (!sedeActual.equals(sede)) {
                sedeActual.getPredios().remove(predio);
                sede.getPredios().add(predio);
                actualizarSede(sede);
                actualizarSede(sedeActual);
            }
        } catch (Exception e) {
            throw new ErrorIntegridad("No se pudo actualizar el predio");
        }
    }

    @Override
    @Interceptors({InterceptorAdministracion.class})
    public void eliminarPredio(String usernameSesion, Predio predio) {
        try {
            EntityManager em = tpm.getEm();
            em.clear();
            Sede sede = predio.getSede().get(0);
            em.remove(em.contains(predio) ? predio : em.merge(predio));
            em.flush();
            sede.getPredios().remove(predio);
            actualizarSede(sede); 
        } catch (Exception e) {
            throw new ErrorIntegridad("No se pudo eliminar el predio");
        }
    }

    @Override
    public Sede buscarSedePorId(Long id) {
        try {
            tpm.getEm().clear();
            return (Sede) tpm.getEm().createNamedQuery("Sede.findPorId")
                    .setParameter("idSede", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void asignarCoordenadaSedes(String codigoSede, String coord_x, String coord_y, String coord_grado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void colocarPredioInexistenteBD(Long idPredio) {
        Connection conn = null;
        CallableStatement cs = null;
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("jdbc/DBCierDS");
            conn = ds.getConnection();
            cs = conn.prepareCall(UtilProperties.getProperties().getProperty("colocarPredioInexistente"));           
            cs.setLong(1, tpm.getCurrentTenant());
            cs.setLong(2, idPredio);            
            cs.execute();            
        } catch (Exception exp) {   
             throw new ErrorIntegridad("No se pudo actualizar el instrumento a estado Inexistente");
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (cs != null) {
                    cs.close();
                }
            } catch (SQLException ex) {     
                throw new ErrorIntegridad("No se pudo actualizar el instrumento a estado Inexistente");
            }
        }
    }
    
    @Override
    public void actualizarPredioBD(Long idPredio) {
        Connection conn = null;
        CallableStatement cs = null;
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("jdbc/DBCierDS");
            conn = ds.getConnection();
            cs = conn.prepareCall(UtilProperties.getProperties().getProperty("actualizarPredio"));           
            cs.setLong(1, tpm.getCurrentTenant());
            cs.setLong(2, idPredio);            
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
    
    @Override
    public void actualizarSedeBD(Long idSede) {
        Connection conn = null;
        CallableStatement cs = null;
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("jdbc/DBCierDS");
            conn = ds.getConnection();
            cs = conn.prepareCall(UtilProperties.getProperties().getProperty("actualizarSede"));           
            cs.setLong(1, tpm.getCurrentTenant());
            cs.setLong(2, idSede);            
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
    
    /**
     *
     * @param tenant
     * @param idSede
     */
    @Override
    public void actualizarEstablecimientoBD(Long idEstablecimiento) {
        Connection conn = null;
        CallableStatement cs = null;
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("jdbc/DBCierDS");
            conn = ds.getConnection();
            cs = conn.prepareCall(UtilProperties.getProperties().getProperty("actualizarEstablecimiento"));           
            cs.setLong(1, tpm.getCurrentTenant());
            cs.setLong(2, idEstablecimiento);            
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
