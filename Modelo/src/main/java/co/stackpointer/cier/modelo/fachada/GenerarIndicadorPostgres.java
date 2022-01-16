/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.fachada;

import co.stackpointer.cier.modelo.entidad.dpa.Nivel;
import co.stackpointer.cier.modelo.utilidad.UtilCadena;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.sql.DataSource;

/**
 *
 * @author user
 */
public class GenerarIndicadorPostgres extends Thread {

    private EntityManager em;
    private Long tenantActual;
    private String respuesta;
    private Nivel nivel;
    //private List<String> logs;

    public void run() {
        try {
            Integer tenant = this.tenantActual.intValue();
            //logs = new ArrayList<String>();
            //logs.add("Inicia actualizar_configuracion_niveles -> " + new Date().toGMTString().toString());

            //result = Integer.parseInt(proc.getObject(1).toString());
            Integer periodo = actualizarConfiguracionNiveles(tenant);
            //logs.add("Finaliza actualizar_configuracion_niveles -> " + new Date().toGMTString().toString());

            //Obtenemos la bandera de generacion de indicadores
            Integer bandera = getBandera(tenant);

            if (nivel != null) {
                respuesta = calculoInfraestructura(tenant, periodo,
                        nivel.getConfiguracion().getNivel().intValue(),
                        nivel.getId().intValue(),
                        bandera);
            } else {
                //logs.add("Inicia calculo_infraestructura -> " + new Date().toGMTString().toString());
                respuesta = calculoInfraestructura(tenant, periodo, bandera);
                //logs.add("Finaliza calculo_infraestructura -> " + new Date().toGMTString().toString());
            }

            if ((!respuesta.equalsIgnoreCase("NO") || bandera == 2) && !respuesta.equalsIgnoreCase("ERROR")) {

                Integer posInicial;
                Integer posFinal;
                Integer maxPosition;
                BigDecimal start;
                BigDecimal end;
                Integer nivel;
                Integer iter = getIter();
                boolean sw = true;

                nivel = 8;

                //si bandera 2: hubo cambios o movimientos en los DPAs
                if (bandera == 2) {
                    //revisamos las rutas a actualizar
                    callFunction("revisar_rutas", tenant, periodo);
                    //proceso de pendientes a aux_niveles, niveles a recalcular
                    if (this.nivel != null) {
                        revisarPendientes(tenant, periodo,
                                this.nivel.getConfiguracion().getNivel().intValue(),
                                this.nivel.getId().intValue());
                    } else {
                        revisarPendientes(tenant, periodo, null, null);
                    }
                }
                if (bandera != 1) {  //no tendra en cuenta datos de indicadores anteriroes     
                    //Revisando los datos calculados
                    //logs.add("Inicia revisar_calculados -> " + new Date().toGMTString().toString());
                    callFunction("revisar_calculados", tenant, periodo);
                    //logs.add("Finaliza revisar_calculados -> " + new Date().toGMTString().toString());
                    //proceso de poda en aux_niveles                  
                    callFunction("podar_niveles", tenant, periodo);
                }

                String query = "SELECT id FROM cier.ind_definiciones WHERE calculable is null and nivel_agrupamiento = " + nivel;
                List<BigDecimal> definiciones = em.
                        createNativeQuery(query).getResultList();

                query = "SELECT def.id_var_amb"
                        + " FROM cier.cal_califica_variables_amb def"
                        + " join cier.cal_variables_ambito amb on amb.id=def.id_var_amb"
                        + " WHERE (def.nivel_consulta in (select conf.nivel from cier.dpa_configuracion_niveles conf where id_tnt = " + tenant + ")"
                        + " or def.nivel_consulta in ('6','7','8')) and nivel_consulta = " + nivel;
                List<BigDecimal> calificaciones = em.
                        createNativeQuery(query).getResultList();


                query = "SELECT id_predio "
                        + "FROM cier.ind_auxiliar_niveles where id_predio is not null and id_tnt = " + tenant + " and estado='N' order by id_predio";
                List<BigDecimal> predios = em.
                        createNativeQuery(query).getResultList();

                if (!predios.isEmpty()) {
                    posInicial = 0;
                    posFinal = iter - 1;
                    maxPosition = predios.size() - 1;
                    while (sw) {
                        //System.out.println("start_: " + predios.get(posInicial).toString());                    
                        start = predios.get(posInicial);
                        if (posFinal < maxPosition) {
                            end = predios.get(posFinal);
                            //System.out.println("end_: " + end);
                            posInicial = posFinal + 1;
                            posFinal = posFinal + iter;
                        } else {
                            end = predios.get(maxPosition);
                            //System.out.println("end_: " + end);
                            sw = false;
                        }
                        //logs.add("Desde: " + start.intValue() + " hasta: " + end.intValue());
                        callFunction("actualizar_respuestas_digitadas", tenant, periodo, start, end);
                        callFunction("actualizar_auxiliar_consultas", tenant, periodo, start, end);
                        //variables:
                        callFunction("gen_var_estudiantes_max_jornada", tenant, periodo, start, end);
                        callFunction("gen_var_estandar_lote", tenant, periodo, start, end);
                        callFunction("gen_var_area_real_predio", tenant, periodo, start, end);
                        callFunction("gen_var_total_estudiante", tenant, periodo, start, end);
                        callFunction("gen_var_area_ambiente_tipo", tenant, periodo, start, end);
                        callFunction("gen_var_area_ambiente_total", tenant, periodo, start, end);
                        callFunction("gen_var_area_construccion_primer_piso", tenant, periodo, start, end);
                        callFunction("gen_var_area_construccion_inactiva_mayor_primer_piso", tenant, periodo, start, end);
                        callFunction("gen_var_area_construccion_cubierta", tenant, periodo, start, end);
                        callFunction("gen_var_area_construccion_total", tenant, periodo, start, end);
                        callFunction("gen_var_area_segun_cond_acabado_muro", tenant, periodo, start, end);
                        callFunction("gen_var_area_segun_cond_muro", tenant, periodo, start, end);
                        callFunction("gen_var_area_segun_cond_piso", tenant, periodo, start, end);
                        callFunction("gen_var_area_segun_cond_techo", tenant, periodo, start, end);
                        callFunction("gen_var_total_espacio", tenant, periodo, start, end);
                        callFunction("gen_var_total_poblacion", tenant, periodo, start, end);
                        callFunction("gen_var_num_jornadas_predio", tenant, periodo, start, end);
                        callFunction("gen_var_num_espacio_aislamiento_termico_novf", tenant, periodo, start, end);
                        callFunction("gen_var_num_espacio_aislamiento_termico_pared", tenant, periodo, start, end);
                        callFunction("gen_var_num_espacio_aislamiento_termico_piso", tenant, periodo, start, end);
                        callFunction("gen_var_num_espacio_aislamiento_termico_techo", tenant, periodo, start, end);
                        callFunction("gen_var_num_espacio_aislamiento_termico_ventana", tenant, periodo, start, end);
                        callFunction("gen_var_num_espacio_iluminacion_artificial", tenant, periodo, start, end);
                        callFunction("gen_var_num_espacio_iluminacion_natural", tenant, periodo, start, end);
                        callFunction("gen_var_num_espacio_segun_cond_acabado_muro", tenant, periodo, start, end);
                        callFunction("gen_var_num_espacio_segun_cond_muro", tenant, periodo, start, end);
                        callFunction("gen_var_num_espacio_segun_cond_piso", tenant, periodo, start, end);
                        callFunction("gen_var_num_espacio_segun_cond_puerta", tenant, periodo, start, end);
                        callFunction("gen_var_num_espacio_segun_cond_techo", tenant, periodo, start, end);
                        callFunction("gen_var_num_espacio_segun_cond_ventana", tenant, periodo, start, end);
                        callFunction("gen_var_num_espacio_ventilacion_natural", tenant, periodo, start, end);
                        callFunction("gen_var_num_espacio_tipo", tenant, periodo, start, end);
                        callFunction("gen_var_num_espacio_requeridos_aula", tenant, periodo, start, end);
                        callFunction("gen_var_num_estudiante_predio_problema_legalizacion", tenant, periodo, start, end);
                        callFunction("gen_var_poblacion_afectada_accesibilidad", tenant, periodo, start, end);
                        callFunction("gen_var_poblacion_afectada_riesgo_antropico", tenant, periodo, start, end);
                        callFunction("gen_var_poblacion_afectada_riesgo_natural", tenant, periodo, start, end);
                        callFunction("gen_var_posibilidad_construccion_total", tenant, periodo, start, end);
                        callFunction("gen_var_m2_ampliar_cobertura", tenant, periodo, start, end);
                        callFunction("gen_var_m2_requeridos_aula", tenant, periodo, start, end);
                        callFunction("gen_var_m2_requeridos_construccion", tenant, periodo, start, end);
                        callFunction("gen_var_m2_requeridos_predio", tenant, periodo, start, end);
                        callFunction("gen_var_num_alumnos_nuevos_infraestructura", tenant, periodo, start, end);
                        callFunction("gen_var_cupos_predio", tenant, periodo, start, end);
                        callFunction("gen_var_estudiantes_por_jornada", tenant, periodo, start, end);
                        callFunction("gen_var_cupos_total", tenant, periodo, start, end);
                        callFunction("gen_var_area_verde", tenant, periodo, start, end);
                        //indicadores
                        for (BigDecimal idDef : definiciones) {
                            generarIndicador(tenant, periodo, nivel, idDef.intValue(), start.intValue(), end.intValue());
                        }
                        //calificaciones
                        for (BigDecimal idCalif : calificaciones) {
                            generarCalificacion(tenant, periodo, nivel, idCalif.intValue(), start.intValue(), end.intValue());
                        }
                    }
                }

                //Resto de niveles superiores a predio

                String queryEntidades = "select 7 union select 6 union select nivel::numeric from cier.ind_dpa_configuracion_niveles"
                        + " where id_tnt = " + tenant + " and periodo=" + periodo + " order by 1 desc";
                List<BigDecimal> niveles = em.
                        createNativeQuery(queryEntidades).getResultList();
                for (BigDecimal niv : niveles) {
                    //logs.add("Calulo para el nivel: " + niv.intValue());
                    String tipoEntidad = null;
                    switch (niv.intValue()) {
                        case 7:
                            tipoEntidad = "id_sede";
                            break;
                        case 6:
                            tipoEntidad = "id_establecimiento";
                            break;
                        case 5:
                            tipoEntidad = "id_nivel5";
                            break;
                        case 4:
                            tipoEntidad = "id_nivel4";
                            break;
                        case 3:
                            tipoEntidad = "id_nivel3";
                            break;
                        case 2:
                            tipoEntidad = "id_nivel2";
                            break;
                        case 1:
                            tipoEntidad = "id_nivel1";
                            break;
                        case 0:
                            tipoEntidad = "id_nivel0";
                            break;
                    }

                    //definiciones.clear();
                    query = "SELECT id FROM cier.ind_definiciones WHERE calculable is null and nivel_agrupamiento = " + niv;
                    definiciones = em.
                            createNativeQuery(query).getResultList();

                    //calificaciones.clear();
                    query = "SELECT def.id_var_amb"
                            + " FROM cier.cal_califica_variables_amb def"
                            + " join cier.cal_variables_ambito amb on amb.id=def.id_var_amb"
                            + " WHERE (def.nivel_consulta in (select conf.nivel from cier.dpa_configuracion_niveles conf where id_tnt = " + tenant + ")"
                            + " or def.nivel_consulta in ('6','7','8')) and nivel_consulta = " + niv;
                    calificaciones = em.
                            createNativeQuery(query).getResultList();

                    //if (niv.intValue() == 6 || niv.intValue() == 7) {//se envia por bloques                         
                    query = "SELECT distinct " + tipoEntidad + " FROM cier.ind_auxiliar_niveles where "
                            + tipoEntidad + " is not null and id_tnt = " + tenant + " order by " + tipoEntidad + "";
                    List<BigDecimal> entidades = em.
                            createNativeQuery(query).getResultList();

                    if (!entidades.isEmpty()) {
                        posInicial = 0;
                        posFinal = iter - 1;
                        maxPosition = entidades.size() - 1;
                        sw = true;
                        while (sw) {
                            //System.out.println("start_: " + predios.get(posInicial).toString());                    
                            start = entidades.get(posInicial);
                            if (posFinal < maxPosition) {
                                end = entidades.get(posFinal);
                                //System.out.println("end_: " + end);
                                posInicial = posFinal + 1;
                                posFinal = posFinal + iter;
                            } else {
                                end = entidades.get(maxPosition);
                                //System.out.println("end_: " + end);
                                sw = false;
                            }
                            //logs.add("Desde: " + start.intValue() + " hasta: " + end.intValue());
                            //indicadores
                            for (BigDecimal idDef : definiciones) {
                                generarIndicador(tenant, periodo, niv.intValue(), idDef.intValue(), start.intValue(), end.intValue());
                            }
                            //calificaciones
                            for (BigDecimal idCalif : calificaciones) {
                                generarCalificacion(tenant, periodo, niv.intValue(), idCalif.intValue(), start.intValue(), end.intValue());
                            }

                        }
                    }

                    /* } else {
                     //indicadores
                     for (BigDecimal idDef : definiciones) {
                     generarIndicador(tenantActual, periodo, niv.intValue(), idDef.intValue(), 0, 0);// no tiene en cuenta el star ni el end
                     }
                     //calificaciones
                     for (BigDecimal idCalif : calificaciones) {
                     generarCalificacion(tenantActual, periodo, niv.intValue(), idCalif.intValue(), 0, 0);// no tiene en cuenta el star ni el end
                     }
                     }*/

                }
                //actualizamos el estado de la bandera si es necesario            
                callFunction("revisar_bandera", tenant, bandera);
                if (respuesta.equalsIgnoreCase("NO") || bandera == 2) {
                    respuesta = periodo.toString();
                }
                //Actualizamos el ultimo periodo al que se le generaron indicadores al sistema
                callFunction("actualizar_periodo_calculado", tenant, periodo);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            respuesta = "ERROR";
        }
    }

    /*private void generarVariable(Connection con, String funcion, Integer tenantActual, Integer periodo, Integer iter) {
     try {
     Integer posInicial = 0;
     boolean sw = callVarFuction(con, funcion, tenantActual, periodo, posInicial);
     while (sw) {
     posInicial = posInicial + iter;
     sw = callVarFuction(con, funcion, tenantActual, periodo, posInicial);
     }
     } catch (Exception e) {
     throw new Error("error en calculo de variables");
     }
     }*/
    private Integer getIter() {
        String sql = "select valor from sys_parametros where codigo='PAGINACION'";
        Integer result = new Integer(
                em.createNativeQuery(sql)
                .getSingleResult().toString());
        return result;
    }

    private Integer getBandera(Integer tenant) {
        String sql = "select valor from ind_control_generacion where codigo='BANDERA_GENERACION_IND'  AND id_tnt = ?tenant";
        Integer result = new Integer(
                em.createNativeQuery(sql)
                .setParameter("tenant", tenant)
                .getSingleResult().toString());
        return result;
    }

    private void revisarPendientes(Integer tenant, Integer periodo, Integer nivel_dpa, Integer id_dpa) {
        try {
            String sql;
            Object res;
            if (UtilCadena.isNullOVacio(nivel_dpa)) {
                sql = "select revisar_pendientes ( ?tenant, ?periodo , null , null )";
                res = em.createNativeQuery(sql)
                        .setParameter("tenant", tenant)
                        .setParameter("periodo", periodo)
                        .getSingleResult();
            } else {
                sql = "select revisar_pendientes ( ?tenant, ?periodo , ?nivel_dpa , ?id_dpa )";
                res = em.createNativeQuery(sql)
                        .setParameter("tenant", tenant)
                        .setParameter("periodo", periodo)
                        .setParameter("nivel_dpa", nivel_dpa)
                        .setParameter("id_dpa", id_dpa)
                        .getSingleResult();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private String calculoInfraestructura(Integer tenant, Integer periodo, Integer bandera) {
        try {
            String sql = "select calculo_infraestructura ( ?tenant, ?periodo, ?bandera )";

            Object res = em.createNativeQuery(sql)
                    .setParameter("tenant", tenant)
                    .setParameter("periodo", periodo)
                    .setParameter("bandera", bandera)
                    .getSingleResult();

            String resultado = res.toString();
            //con.commit();
            return resultado;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private Integer actualizarConfiguracionNiveles(Integer tenant) {
        try {
            String sql = "select actualizar_configuracion_niveles ( ?tenant )";

            Object res = em.createNativeQuery(sql)
                    .setParameter("tenant", tenant)
                    .getSingleResult();

            Integer resultado = new Integer(res.toString());
            //con.commit();
            return resultado;
        } catch (Exception ex) {
            //ex.printStackTrace();
            return 0;
        }
    }

    private String calculoInfraestructura(Integer tenant, Integer periodo, Integer nivel_dpa, Integer id_dpa, Integer bandera) {
        try {
            String sql = "select calculo_infraestructura_filtro_dpa ( ?tenant, ?periodo , ?nivel_dpa , ?id_dpa , ?bandera )";

            Object res = em.createNativeQuery(sql)
                    .setParameter("tenant", tenant)
                    .setParameter("periodo", periodo)
                    .setParameter("nivel_dpa", nivel_dpa)
                    .setParameter("id_dpa", id_dpa)
                    .setParameter("bandera", bandera)
                    .getSingleResult();

            String resultado = res.toString();
            //con.commit();
            return resultado;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private void generarIndicador(Integer tenant, Integer periodo, Integer nivel, Integer definicion, Integer start, Integer end) {
        try {
            String sql = "select generar_indicador ( ?tenant , ?periodo , ?nivel , ?definicion , ?start , ?end )";
            //logs.add("comienza la id_definicion: " + definicion + " -> " + new Date().toGMTString().toString());
            em.setFlushMode(FlushModeType.COMMIT);
            Object res = em.createNativeQuery(sql)
                    .setParameter("tenant", tenant)
                    .setParameter("periodo", periodo)
                    .setParameter("nivel", nivel)
                    .setParameter("definicion", definicion)
                    .setParameter("start", start)
                    .setParameter("end", end)
                    .getSingleResult();
            //con.commit();            
        } catch (Exception ex) {
            //ex.printStackTrace();
            System.out.println("id_def: " + definicion);
        }
    }

    private void generarCalificacion(Integer tenant, Integer periodo, Integer nivel, Integer calificacion, Integer start, Integer end) {
        try {
            String sql = "select generar_calificacion ( ?tenant , ?periodo , ?nivel , ?calificacion , ?start , ?end )";
            //logs.add("comienza la id_definicion: " + calificacion + " -> " + new Date().toGMTString().toString());
            em.setFlushMode(FlushModeType.COMMIT);
            Object res = em.createNativeQuery(sql)
                    .setParameter("tenant", tenant)
                    .setParameter("periodo", periodo)
                    .setParameter("nivel", nivel)
                    .setParameter("calificacion", calificacion)
                    .setParameter("start", start)
                    .setParameter("end", end)
                    .getSingleResult();
            //con.commit();            
        } catch (Exception ex) {
            //ex.printStackTrace();
            System.out.println("id_calif: " + calificacion);
        }
    }

    private boolean generarCalificaciones(Integer tenant, Integer periodo, Integer nivel, Integer posicionInicial) {
        try {
            String sql = "select generar_calificaciones ( ?tenant , ?periodo , ?nivel , ?posicionInicial )";

            em.setFlushMode(FlushModeType.COMMIT);
            Object res = em.createNativeQuery(sql)
                    .setParameter("tenant", tenant)
                    .setParameter("periodo", periodo)
                    .setParameter("nivel", nivel)
                    .setParameter("posicionInicial", posicionInicial)
                    .getSingleResult();

            boolean resultado = Boolean.valueOf(res.toString());
            return resultado;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        } finally {
            em.flush();
        }
    }

    private void callFunction(String funcion, Integer tenant, Integer periodo) {
        try {
            String sql = "select " + funcion + " ( ? , ? )";
            //em.setFlushMode(FlushModeType.COMMIT);
            em.createNativeQuery(sql)
                    .setParameter(1, tenant)
                    .setParameter(2, periodo)
                    .getSingleResult();

        } catch (Exception ex) {
            throw new Error("error en la funcion generar_indicadores");

            /*} finally {
             String sql = "commit;";
             em.createNativeQuery(sql)                                      
             .executeUpdate();
             * */
        }

    }

    private void callFunction(String funcion, Integer tenant, Integer periodo, BigDecimal start, BigDecimal end) {
        try {
            //logs.add("Inicia " + funcion + " -> " + new Date().toGMTString().toString());
            String sql = "select " + funcion + " ( ?tenant , ?periodo, ?start , ?end )";
            em.createNativeQuery(sql)
                    .setParameter("tenant", tenant)
                    .setParameter("periodo", periodo)
                    .setParameter("start", start.intValue())
                    .setParameter("end", end.intValue())
                    .getSingleResult();
            //logs.add("Finaliza " + funcion + " -> " + new Date().toGMTString().toString());

        } catch (Exception ex) {
            throw new Error("error en la funcion generar_indicadores");

        }

    }

    public void limpiar(String tabla, Integer tenant, Integer periodo) throws Exception {
        Connection conn = null;
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("jdbc/DBCierDS");
            conn = ds.getConnection();

            String sql = "DELETE FROM cier." + tabla + " WHERE id_tnt = " + tenant + " and periodo = " + periodo + "";
            Statement st2 = conn.createStatement();
            st2.executeUpdate(sql);
            st2.close();
            /*em.createQuery
             (sql)
             .setParameter("tenantActual", tenantActual)
             .setParameter("periodo", periodo)
             .executeUpdate();*/
        } catch (Exception e) {
            throw new Exception();
        } finally {
            conn.clearWarnings();
        }
    }

    private boolean callVarFuction(Connection con, String funcion, Integer tenant, Integer periodo, Integer posicionInicial) {
        PreparedStatement proc = null;
        boolean resultado = false;
        try {
            String sql = "select " + funcion + " ( ? , ?, ? )";
            proc = con.prepareStatement(sql);
            proc.setInt(1, tenant);
            proc.setInt(2, periodo);
            proc.setInt(3, posicionInicial);
            ResultSet rs = proc.executeQuery();
            if (rs.next()) {
                resultado = rs.getBoolean(1);
            }
            //con.commit();
            return resultado;
        } catch (SQLException ex) {
            respuesta = "ERROR";
            throw new Error("error en la funcion generar_indicadores");
        } finally {
            try {
                proc.close();
            } catch (SQLException ex) {
                throw new Error("error al cerrar llamada al procedimiento");
            }
        }
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }

    /*public List<String> getLogs() {
     return logs;
     }
     public void setLogs(List<String> logs) {
     this.logs = logs;
     }*/
    /*public TenantPersistenceManager getTpm() {
     return tpm;
     }
     public void setTpm(TenantPersistenceManager tpm) {
     this.tpm = tpm;
     }*/
    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public Long getTenantActual() {
        return tenantActual;
    }

    public void setTenantActual(Long tenantActual) {
        this.tenantActual = tenantActual;
    }
}
