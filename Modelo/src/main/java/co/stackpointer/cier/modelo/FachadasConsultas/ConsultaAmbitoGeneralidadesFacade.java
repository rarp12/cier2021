/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.FachadasConsultas;

import co.stackpointer.cier.modelo.EntidadesConsultas.ConsultaAmbitoGeneralidades;
import co.stackpointer.cier.modelo.fachada.TenantPersistenceManager;
import co.stackpointer.cier.modelo.filtro.ambito.FiltroAmbitoGeneralidad;
import co.stackpointer.cier.modelo.tipo.ParamIndicador;
import co.stackpointer.cier.modelo.utilidad.UtilProperties;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

/**
 *
 * @author jguzmanf
 */
@Stateless
public class ConsultaAmbitoGeneralidadesFacade extends AbstractFacade<ConsultaAmbitoGeneralidades> implements ConsultaAmbitoGeneralidadesFacadeLocal{

    @PersistenceContext(unitName = "Model")
    private EntityManager em;
    @Inject
    private TenantPersistenceManager tpm;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ConsultaAmbitoGeneralidadesFacade() {
        super(ConsultaAmbitoGeneralidades.class);
    }

    public List<Object[]> consultarGeneralidades(FiltroAmbitoGeneralidad filtro) {
        if (filtro.isConsultaDeTiempoReal()) {
            return null;//this.consultarGeneralidadesTiempoReal(filtro);
        } else {
            return null;//consultarGeneralidadesIndicadores(filtro);
        }
    }
    
    /**
     * 
     * @param filtro
     * @return
     * @throws Exception 
     * Este metodo se encargara de hacer el llamado del procedimiento CIE_PR_CONS_GNRLDDES
     * el cual se encargara de generar la consulta de generalidades por indicadores
     */
    
    @Override
    public List<ConsultaAmbitoGeneralidades> generarConsultaAmbitoGeneralidades(FiltroAmbitoGeneralidad filtro, Long idioma) throws Exception {
        Connection conn = null;
        CallableStatement cs = null;
        List<ConsultaAmbitoGeneralidades> resultados;
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("jdbc/DBCierDS");
            conn = ds.getConnection();
            //Si la consulta no es por tiempo real llamamos el siguiente procedimiento almacenado
            if (!filtro.isConsultaDeTiempoReal()) {
                cs = conn.prepareCall(UtilProperties.getProperties().getProperty("ConsultaGeneralidadesIndicador"));
                cs.setLong(1, filtro.getAgrupamiento());
                cs.setLong(2, tpm.getCurrentTenant());
                cs.setInt(3, filtro.getPeriodo());
                if(filtro.getIdNivel0() != null){ cs.setLong(4, filtro.getIdNivel0());} else{ cs.setString(4, "");}
                if(filtro.getIdNivel1() != null){ cs.setLong(5, filtro.getIdNivel1());} else{ cs.setString(5, "");}
                if(filtro.getIdNivel2() != null){ cs.setLong(6, filtro.getIdNivel2());}else{ cs.setString(6, "");}
                if(filtro.getIdNivel3() != null){ cs.setLong(7, filtro.getIdNivel3());}else{ cs.setString(7, "");}
                if(filtro.getIdNivel4() != null){ cs.setLong(8, filtro.getIdNivel4());}else{ cs.setString(8, "");}
                if(filtro.getIdNivel5() != null){ cs.setLong(9, filtro.getIdNivel5());}else{ cs.setString(9, "");}
                if(filtro.getIdEstablecimiento() != null){ cs.setLong(10, filtro.getIdEstablecimiento());}else{ cs.setString(10, "");}
                if(filtro.getIdSede() != null){ cs.setLong(11, filtro.getIdSede());}else{ cs.setString(11, "");}
                if(filtro.getIdPredio() != null){ cs.setLong(12, filtro.getIdPredio());}else{ cs.setString(12, "");}
                cs.setString(13, "SQL_CONSULTAR_INDICADOR_ALMACENADO");
                cs.setString(14, (ParamIndicador.IND_TOTAL_ESTUDIANTES).toString());
                cs.setString(15, (ParamIndicador.IND_NUMERO_JORNADAS_PREDIO).toString());
                cs.setString(16, (ParamIndicador.IND_TOTAL_AREA_CONSTRUCCION_PREDIO).toString());
                cs.setString(17, (ParamIndicador.IND_AREA_REAL_PREDIO).toString());
                cs.setString(18, (ParamIndicador.IND_ETNIAS_PREDIO).toString());
                cs.setString(19, (ParamIndicador.IND_TOTAL_ESTABLECIMIENTOS).toString());
                cs.setString(20, (ParamIndicador.IND_TOTAL_SEDES).toString());
                cs.setString(21, (ParamIndicador.IND_TOTAL_PREDIOS).toString());
                if(idioma != null){ cs.setLong(22, idioma);}else{ cs.setString(22, "");}
                //registramos el parametro de salida
                cs.registerOutParameter(23, Types.NUMERIC, "P_CONSECUTIVO_REPORTE");
                //ejecutamos el reporte
                cs.execute();
                //recuperamos los parametros de salida
                Long consecutivoReporte = cs.getLong(23);
                //ejecutamos la consulta de los datos generados por el reporte
                resultados = ConsultaGeneralidadesReporte(consecutivoReporte);
            }
            else{
                cs = conn.prepareCall(UtilProperties.getProperties().getProperty("ConsultaGeneralidadesIndicadorTr"));
                cs.setLong(1, filtro.getAgrupamiento());
                cs.setLong(2, tpm.getCurrentTenant());
                cs.setInt(3, filtro.getPeriodo());
                if(filtro.getIdNivel0() != null){ cs.setLong(4, filtro.getIdNivel0());} else{ cs.setString(4, "");}
                if(filtro.getIdNivel1() != null){ cs.setLong(5, filtro.getIdNivel1());} else{ cs.setString(5, "");}
                if(filtro.getIdNivel2() != null){ cs.setLong(6, filtro.getIdNivel2());}else{ cs.setString(6, "");}
                if(filtro.getIdNivel3() != null){ cs.setLong(7, filtro.getIdNivel3());}else{ cs.setString(7, "");}
                if(filtro.getIdNivel4() != null){ cs.setLong(8, filtro.getIdNivel4());}else{ cs.setString(8, "");}
                if(filtro.getIdNivel5() != null){ cs.setLong(9, filtro.getIdNivel5());}else{ cs.setString(9, "");}
                if(filtro.getIdEstablecimiento() != null){ cs.setLong(10, filtro.getIdEstablecimiento());}else{ cs.setString(10, "");}
                if(filtro.getIdSede() != null){ cs.setLong(11, filtro.getIdSede());}else{ cs.setString(11, "");}
                if(filtro.getIdPredio() != null){ cs.setLong(12, filtro.getIdPredio());}else{ cs.setString(12, "");}
                if(filtro.getCodNivelEnseñaza() != null){ cs.setString(13, filtro.getCodNivelEnseñaza());}else{ cs.setString(13, "");}
                if(filtro.getCodJornada()!= null){ cs.setString(14, filtro.getCodJornada());}else{ cs.setString(14, "");}
                if(filtro.getCodSector()!= null){ cs.setString(15, filtro.getCodSector());}else{ cs.setString(15, "");}
                if(filtro.getCodZona()!= null){ cs.setString(16, filtro.getCodZona());}else{ cs.setString(16, "");}
                cs.setString(17, (ParamIndicador.IND_JORNADAS_PREDIO).toString());
                cs.setString(18, (ParamIndicador.IND_TOTAL_ESTUDIANTES).toString());
                cs.setString(19, (ParamIndicador.IND_NUMERO_JORNADAS_PREDIO).toString());
                cs.setString(20, (ParamIndicador.IND_TOTAL_AREA_CONSTRUCCION_PREDIO).toString());
                cs.setString(21, (ParamIndicador.IND_AREA_REAL_PREDIO).toString());
                cs.setString(22, (ParamIndicador.IND_ETNIAS_PREDIO).toString());
                cs.setString(23, (ParamIndicador.IND_TOTAL_ESTUDIANTE_NIVEL_ENSENAZA_PREDIO).toString());
                cs.setString(24, (ParamIndicador.IND_TOTAL_ESTABLECIMIENTOS).toString());
                cs.setString(25, (ParamIndicador.IND_TOTAL_SEDES).toString());
                cs.setString(26, (ParamIndicador.IND_TOTAL_PREDIOS).toString());
                if(idioma != null){ cs.setLong(27, idioma);}else{ cs.setString(27, "");}
                //registramos el parametro de salida
                cs.registerOutParameter(28, Types.NUMERIC, "P_CONSECUTIVO_REPORTE");
                //ejecutamos el reporte
                cs.execute();
                //recuperamos los parametros de salida
                Long consecutivoReporte = cs.getLong(28);
                //ejecutamos la consulta de los datos generados por el reporte
                resultados = ConsultaGeneralidadesReporte(consecutivoReporte);
            }    
                
                
                return  resultados;
        } catch (Exception e) {
            System.out.println("Error: "+e);
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (cs != null) {
                cs.close();
            }
        }
    }
    
    /**
     * 
     * @param consecutivoReporte
     * @return 
     * Este metodo se encarga de recibir el consecutivo que retorno el procedimiento encargado de generar la consulta de generalidades
     * por indicadores y retornar los datos que este haya depositado en la tabla CON_AMB_GENERALIDADES_IND
     */
    @Override
    public List<ConsultaAmbitoGeneralidades> ConsultaGeneralidadesReporte (Long consecutivoReporte) {
        if (consecutivoReporte == null) {
            return null;
        } else {
            List<ConsultaAmbitoGeneralidades> registros = em.createNamedQuery("con_amb_generalidades_ind.consecutivoReporte")
                    .setParameter("consecutivoReporte", consecutivoReporte)
                    .getResultList();
            return registros;
        }
    }
}
               
    
   
    

