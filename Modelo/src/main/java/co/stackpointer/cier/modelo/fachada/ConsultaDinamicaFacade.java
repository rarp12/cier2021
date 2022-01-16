/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.fachada;

import co.stackpointer.cier.modelo.entidad.sistema.Parametro;
import co.stackpointer.cier.modelo.filtro.dinamica.FiltroConsultaDinamica;
import co.stackpointer.cier.modelo.tipo.Ambitos;
import co.stackpointer.cier.modelo.tipo.CalificacionCondicion;
import co.stackpointer.cier.modelo.tipo.CodPregGerenciales;
import co.stackpointer.cier.modelo.tipo.ParamBaseDatos;
import co.stackpointer.cier.modelo.tipo.ParamIndicador;
import co.stackpointer.cier.modelo.tipo.ParamNivelConsul;
import co.stackpointer.cier.modelo.tipo.ParamSistema;
import co.stackpointer.cier.modelo.tipo.ServiciosPublicos;
import co.stackpointer.cier.modelo.tipo.SiNo;
import co.stackpointer.cier.modelo.tipo.Vulnerabilidad;
import co.stackpointer.cier.modelo.utilidad.UtilCadena;
import co.stackpointer.cier.modelo.utilidad.UtilProperties;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author nruiz
 */
@Stateless
public class ConsultaDinamicaFacade implements ConsultaDinamicaFacadeLocal {

    private static int i;   

    @PostConstruct
    private void inicializarParametros() {
        i = 0;
    }
    
    @Inject
    private TenantPersistenceManager tpm;
    
    @EJB
    private ParametrosFacadeLocal facParam;

    @Override
    public List<String> consultarAmbPredominantes() {
        StringBuilder sql = new StringBuilder();
        sql.append("Select a.codigo");
        sql.append(" from ins_ambitos a where a.codigo in");
        sql.append("(select distinct cod_amb_predominante from ins_preg_gerenciales)");
        sql.append(" order by a.nombre");
        Query query = tpm.getEm().createNativeQuery(sql.toString());

        return query.getResultList();
    }

    @Override
    public List<String> consultarAmbCondicional(String ambitoPredominante) {
        StringBuilder sql = new StringBuilder();
        sql.append("Select a.codigo");
        sql.append(" from ins_ambitos a where a.codigo in");
        sql.append(" (select distinct cod_amb_condicional from ins_preg_gerenciales where");
        sql.append(" cod_amb_predominante=?ambPred)");
        sql.append(" order by a.nombre");

        Query query = tpm.getEm().createNativeQuery(sql.toString());
        query.setParameter("ambPred", ambitoPredominante);

        return query.getResultList();
    }

    @Override
    public List<String> consultarPregGerenciales(String ambitoPredominante, String ambitoCondicional) {
        StringBuilder sql = new StringBuilder();
        sql.append("Select codigo from ins_preg_gerenciales where");
        sql.append(" cod_amb_predominante=?ambPred");
        sql.append(" and cod_amb_condicional=?ambCond");

        Query query = tpm.getEm().createNativeQuery(sql.toString());
        query.setParameter("ambPred", ambitoPredominante);
        query.setParameter("ambCond", ambitoCondicional);

        return query.getResultList();
    }

    private String getSQLConsultarIndicadorAlmacenado() {
        tpm.getEm().clear();
        Parametro parametro = facParam.consultarParametro(ParamSistema.SQL_CONSULTAR_INDICADOR_ALMACENADO.name());
        return parametro != null ? parametro.getValor() : null;
    }
    
    private String consultarFormulaSQLIndicador(ParamIndicador indicador, ParamNivelConsul nivel) {
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("select d.formula from ind_definiciones d");
            sql.append(" inner join ind_indicadores i on i.id_tnt=d.id_tnt and i.id=d.id_indicador");
            sql.append(" and i.codigo = ?codIndicador");
            sql.append(" and d.nivel_agrupamiento = ?nivelAgrupamiento");
            
            Query query = tpm.getEm().createNativeQuery(sql.toString());
            query.setParameter("codIndicador", indicador.name());
            query.setParameter("nivelAgrupamiento", nivel.getNivel());
            return (String) query.getSingleResult();
            
        } catch (NoResultException e) {
            return null;
        }
    }

    private String agregarCondicionConfort(String subquery, CalificacionCondicion condicion) {        
        if (condicion != null) {  
             if(ParamBaseDatos.POSTGRE.equals(UtilProperties.getDbMotor())){
                  subquery = subquery.replace("--filtro_simular", "");   
             }else{
                subquery = subquery.replace("--filtro", "");
             }
           
            subquery = subquery.replace("cond_", String.valueOf(condicion.getValor()));
        }
        return subquery;
    }
    
    @Override
     public List<Object[]> consultarPrediosPorTipologias(FiltroConsultaDinamica filtro) {
           
        i = 0;
        StringBuilder sql = new StringBuilder();
        //SELECT
        sql.append("select distinct aux.id_dig_instrumento");
        sql.append(", n.codigo_nivel1,n.nombre_nivel1");
        sql.append(",n.codigo_nivel2,n.nombre_nivel2");
        sql.append(",n.codigo_nivel3,n.nombre_nivel3");
        sql.append(",n.codigo_nivel4,n.nombre_nivel4");
        sql.append(",n.codigo_nivel5,n.nombre_nivel5");
        sql.append(", n.codigo_establecimiento, n.nombre_establecimiento");
        sql.append(", n.codigo_sede, n.nombre_sede");
        sql.append(", n.codigo_predio, n.nombre_predio");

        int x = 1;
        int cx = 0;
        if (Ambitos.A_RN.toString().equals(filtro.getAmbitoPredominante())
                || Ambitos.A_RN.toString().equals(filtro.getAmbitoCondicional())) {
            if (Ambitos.A_RN.toString().equals(filtro.getAmbitoPredominante())) {
                cx = x;
            }
            sql.append(", tbl_tipo").append(x).append(".valor tip1, tbl_cond").append(x++).append(".valor cond1, tbl_tipo").append(x).append(".valor tip2, tbl_cond").append(x++).append(".valor cond2, tbl_tipo").append(x).append(".valor tip3, tbl_cond").append(x++).append(".valor cond3, condicion_riesgo").append(x++).append(".valor cond_rn");
        }
        if (Ambitos.A_RA.toString().equals(filtro.getAmbitoPredominante())
                || Ambitos.A_RA.toString().equals(filtro.getAmbitoCondicional())) {
            if (Ambitos.A_RA.toString().equals(filtro.getAmbitoPredominante())) {
                cx = x;
            }
            sql.append(", tbl_tipo").append(x).append(".valor tip1, tbl_cond").append(x++).append(".valor cond1, tbl_tipo").append(x).append(".valor tip2, tbl_cond").append(x++).append(".valor cond2, tbl_tipo").append(x).append(".valor tip3, tbl_cond").append(x++).append(".valor cond3, condicion_riesgo").append(x++).append(".valor cond_riesgo");
            sql.append(", tbl_tip_ret").append(x).append(".valor ret1, tbl_mts_ret").append(x++).append(".valor mts1, tbl_tip_ret").append(x).append(".valor ret2, tbl_mts_ret").append(x++).append(".valor mts2, tbl_tip_ret").append(x).append(".valor ret3, tbl_mts_ret").append(x++).append(".valor mts3");
            sql.append(" ,tbl_tip_ret").append(x).append(".valor ret4, tbl_mts_ret").append(x++).append(".valor mts4, tbl_tip_ret").append(x).append(".valor ret5, tbl_mts_ret").append(x++).append(".valor mts5");
        }
        if (Ambitos.A_ACC.toString().equals(filtro.getAmbitoPredominante())
                || Ambitos.A_ACC.toString().equals(filtro.getAmbitoCondicional())) {
            sql.append(", tbl_tipo").append(x).append(".valor tip1, tbl_cond").append(x++).append(".valor cond1, tbl_tipo").append(x).append(".valor tip2, tbl_cond").append(x++).append(".valor cond2, tbl_tipo").append(x).append(".valor tip3, tbl_cond").append(x++).append(".valor cond3, tbl_tipo").append(x).append(".valor tip4");
            sql.append(", tbl_cond").append(x++).append(".valor cond4, tbl_tipo").append(x).append(".valor tip5, tbl_cond").append(x++).append(".valor cond5");
            sql.append(", tbl_num_medios_transportes.valor medios_transp, coalesce(tbl_cond_1o2.valor,'0') cond_1o2, tbl_accesibilidad.valor accesibili");
        }
        if (Ambitos.A_OFE.toString().equals(filtro.getAmbitoPredominante())
                || Ambitos.A_OFE.toString().equals(filtro.getAmbitoCondicional())) {
            sql.append(", tbl_area_real_predio.valor area_real_predio");
            sql.append(", tbl_estandar_predio.valor v1");
            sql.append(", tbl_idoneidad_predio.valor v2");
            sql.append(", tbl_cupos_total.valor v3");
            sql.append(", tbl_m2_requeridos_predio.valor v4");
            sql.append(", tbl_area_cubierta_const.valor area_construccion_total");
            sql.append(", tbl_estandar_construccion.valor v5");
            sql.append(", tbl_idoneidad_construccion.valor v6");
            sql.append(", tbl_m2_requeridos_const.valor v7");
            sql.append(", tbl_area_ambiente_aula.valor v8");
            sql.append(", tbl_total_ambiente_aula.valor v9");
            sql.append(", tbl_estandar_aula.valor v10");
            sql.append(", tbl_idoneidad_aula.valor v11");
            sql.append(", tbl_m2_requeridos_aula.valor v12");
            sql.append(", tbl_espacios_requeridos_aula.valor v13");
            sql.append(", tbl_area_const_primer_piso.valor area_construccion_primer_piso");
            if (ParamBaseDatos.ORACLE.equals(UtilProperties.getDbMotor())) {
                sql = posiblidadConstruccionOracle(sql, "tbl_posib_const_primer_piso.valor", "posib_const_primer_piso");
                sql = posiblidadConstruccionOracle(sql, "tbl_posib_const_altura.valor", "posib_const_altura");
                sql = posiblidadConstruccionOracle(sql, "tbl_posib_const_total.valor", "posib_const_total");
            } else {
                sql = posiblidadConstruccionPostgre(sql, "tbl_posib_const_primer_piso.valor", "posib_const_primer_piso");
                sql = posiblidadConstruccionPostgre(sql, "tbl_posib_const_altura.valor", "posib_const_altura");
                sql = posiblidadConstruccionPostgre(sql, "tbl_posib_const_total.valor", "posib_const_total");

            }
        }
        if (Ambitos.A_PROP.toString().equals(filtro.getAmbitoPredominante())
                || Ambitos.A_PROP.toString().equals(filtro.getAmbitoCondicional())) {
            sql.append(", tbl_tipo_propietario.valor tip_propietario");
            sql.append(", tbl_problema_legalizacion.valor problema_legalizacion");
        }
        if (Ambitos.A_CV.toString().equals(filtro.getAmbitoPredominante())
                || Ambitos.A_CV.toString().equals(filtro.getAmbitoCondicional())) {
            sql.append(", tbl_vulnerabilidad.valor vulnerabilidad");
        }
        if (Ambitos.A_ACCD.toString().equals(filtro.getAmbitoPredominante())
                || Ambitos.A_ACCD.toString().equals(filtro.getAmbitoCondicional())) {
            sql.append(", tbl_acceso_discapacitado.valor acceso_discapacitado");
        }
        if (Ambitos.A_AMB.toString().equals(filtro.getAmbitoPredominante())
                || Ambitos.A_AMB.toString().equals(filtro.getAmbitoCondicional())) {
            sql.append(" ,coalesce(tbl_tipo_espacio.valor,'0') tip_espacio");
            sql.append(" ,coalesce(tbl_total_espacio.valor,'0') tot_espacio");
            if(ParamBaseDatos.ORACLE.equals(UtilProperties.getDbMotor()))
               sql.append(" ,to_char(dividir(tbl_tipo_espacio.valor,tbl_total_espacio.valor)) proporcion_espacio");
            else
               sql.append(",dividir(char_to_double(tbl_tipo_espacio.valor),char_to_double(tbl_total_espacio.valor)) proporcion_espacio");

            sql.append(" ,area_tipo_espacio.valor m2_tipo_espacio");
            sql.append(" ,num_estudiantes_max_jornada.valor num_est_max_jornada");
            sql.append(" ,m2_alumno.valor m2_alumno");
            sql.append(" ,condicion_m2_alumno.valor condicion_m2_alumno");
            sql.append(" ,deficit_estandar.valor deficit_estandar");
            sql.append(" ,cumplimiento_norma.valor cumplimiento_norma");
        }
        if (Ambitos.A_CON.toString().equals(filtro.getAmbitoPredominante())
                || Ambitos.A_CON.toString().equals(filtro.getAmbitoCondicional())) {
            sql.append(" ,coalesce(tb_duchas_condicion.valor,0) cond_duchas");
            sql.append(" ,coalesce(tb_lavamanos_condicion.valor,0) cond_lavamanos");
            sql.append(" ,coalesce(tb_orinales_condicion.valor,0) cond_orinales");
            sql.append(" ,coalesce(tb_inodoros_condicion.valor,0) cond_inodoros");
        }
        if (Ambitos.A_SOS.toString().equals(filtro.getAmbitoPredominante())
                || Ambitos.A_SOS.toString().equals(filtro.getAmbitoCondicional())) {
            sql.append(" ,tbl_analisis_consumo_agua.valor analisis_cons_agua");
        }
        if (Ambitos.A_SEG.toString().equals(filtro.getAmbitoPredominante())
                || Ambitos.A_SEG.toString().equals(filtro.getAmbitoCondicional())) {
            sql.append(" , tb_estado_estructura.valor tb_estado_estructura");
            sql.append(" , tb_senalizacion_evacuacion.valor tb_senalizacion_evacuacion");
            sql.append(" , condicion_ruta_evacuacion.valor condicion_ruta_evacuacion");
        }
        if (Ambitos.A_EEE.toString().equals(filtro.getAmbitoPredominante())
                || Ambitos.A_EEE.toString().equals(filtro.getAmbitoCondicional())) {
            sql.append(" , condicion_materialidad.valor materialidad");
        }
        if (Ambitos.A_SP.toString().equals(filtro.getAmbitoPredominante())
                || Ambitos.A_SP.toString().equals(filtro.getAmbitoCondicional())) {
            sql.append(" , tiene_servicio_pub.valor tiene_servicio_pub");
            sql.append(" , cond_serv.valor cond_serv");
        }

        //FROM
        
        sql.append(" from ind_auxiliar_indicador aux");
        sql.append(" inner join ind_auxiliar_consultas n on n.id_predio = aux.id_predio and n.periodo = aux.periodo and n.id_tnt = aux.id_tnt");

        String strSQLConsultarIndicador = this.getSQLConsultarIndicadorAlmacenado();

        if (Ambitos.A_SP.toString().equals(filtro.getAmbitoPredominante())
                || Ambitos.A_SP.toString().equals(filtro.getAmbitoCondicional())) {
            if (CodPregGerenciales.SP_CON.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.CON_SP.toString().equals(filtro.getPreguntaGerencial())) {
                filtro.setServicioPublico(ServiciosPublicos.DSA);
            }
            sql = joinsSP(sql, strSQLConsultarIndicador, filtro.getServicioPublico(), filtro.getCondicionServivio());
        }
        if (Ambitos.A_CON.toString().equals(filtro.getAmbitoPredominante())
                || Ambitos.A_CON.toString().equals(filtro.getAmbitoCondicional())) {
            sql = joinsConfort(sql, filtro);
        }
        if (Ambitos.A_RN.toString().equals(filtro.getAmbitoPredominante())
                || Ambitos.A_RN.toString().equals(filtro.getAmbitoCondicional())) {
            sql = joinsRiesgosNaturales(sql, strSQLConsultarIndicador);
        }
        if (Ambitos.A_RA.toString().equals(filtro.getAmbitoPredominante())
                || Ambitos.A_RA.toString().equals(filtro.getAmbitoCondicional())) {
            sql = joinsRiesgosAntropicos(sql, strSQLConsultarIndicador);
        }
        if (Ambitos.A_ACC.toString().equals(filtro.getAmbitoPredominante())
                || Ambitos.A_ACC.toString().equals(filtro.getAmbitoCondicional())) {
            sql = joinsAccesibilidad(sql, strSQLConsultarIndicador);
        }

        if (Ambitos.A_OFE.toString().equals(filtro.getAmbitoPredominante())
                || Ambitos.A_OFE.toString().equals(filtro.getAmbitoCondicional())) {
            sql = joinsOferta(sql, strSQLConsultarIndicador);
        }
        if (Ambitos.A_PROP.toString().equals(filtro.getAmbitoPredominante())
                || Ambitos.A_PROP.toString().equals(filtro.getAmbitoCondicional())) {
            sql = joinsPropiedadLote(sql, strSQLConsultarIndicador);
        }
        if (Ambitos.A_CV.toString().equals(filtro.getAmbitoPredominante())
                || Ambitos.A_CV.toString().equals(filtro.getAmbitoCondicional())) {
            sql = joinsVulnerabilidad(sql, strSQLConsultarIndicador);
        }
        if (Ambitos.A_ACCD.toString().equals(filtro.getAmbitoPredominante())
                || Ambitos.A_ACCD.toString().equals(filtro.getAmbitoCondicional())) {
            sql = joinsAccesibilidadInterna(sql, strSQLConsultarIndicador);
        }
        if (Ambitos.A_AMB.toString().equals(filtro.getAmbitoPredominante())
                || Ambitos.A_AMB.toString().equals(filtro.getAmbitoCondicional())) {
            sql = joinsAmbiente(sql, strSQLConsultarIndicador, filtro.getUnidadFuncional());
        }        
        if (Ambitos.A_SOS.toString().equals(filtro.getAmbitoPredominante())
                || Ambitos.A_SOS.toString().equals(filtro.getAmbitoCondicional())) {
            sql = joinsSostenibilidad(sql, strSQLConsultarIndicador);
        }
        if (Ambitos.A_SEG.toString().equals(filtro.getAmbitoPredominante())
                || Ambitos.A_SEG.toString().equals(filtro.getAmbitoCondicional())) {
            sql = joinsSeguridad(sql, strSQLConsultarIndicador);
        }
        if (Ambitos.A_EEE.toString().equals(filtro.getAmbitoPredominante())
                || Ambitos.A_EEE.toString().equals(filtro.getAmbitoCondicional())) {
            sql = joinsEEE(sql, strSQLConsultarIndicador);
        }        

        //WHERE
        sql.append(" where");
        sql.append(" aux.id_tnt = ?tenant and aux.periodo = ?periodo");                

        //excluye resultados que no cumplan con el ambito predominante RN
        if (Ambitos.A_RN.toString().equals(filtro.getAmbitoPredominante())) {
            sql.append(" and (tbl_tipo").append(cx++).append(".valor IS NOT NULL or");
            sql.append(" tbl_tipo").append(cx++).append(".valor IS NOT NULL or");
            sql.append(" tbl_tipo").append(cx++).append(".valor IS NOT NULL)");
        }
        //excluye resultados que no cumplan con el ambito predominante RA
        if (Ambitos.A_RA.toString().equals(filtro.getAmbitoPredominante())) {
            sql.append(" and (tbl_tipo").append(cx++).append(".valor IS NOT NULL or");
            sql.append(" tbl_tipo").append(cx++).append(".valor IS NOT NULL or");
            sql.append(" tbl_tipo").append(cx++).append(".valor IS NOT NULL)");
        }

        //Si escoge algun tipo de Propietario y/o (RA|RN con o sin condicion)        
        for (String[] tipValor : filtro.getListaTipValores()) {
            sql.append(" and aux.id_dig_instrumento in (");
            if (!UtilCadena.isNullOVacio(tipValor[2])) {
                sql.append(" select m.id_dig_instrumento from dig_respuestas d, ins_respuestas i, dig_elementos m");
                sql.append(" where d.id_dig_elemento=m.id and d.cod_respuesta=i.codigo and d.id_dig_elemento||i.cod_res_padre");
                sql.append(" in (");
                sql.append(" select dd.id_dig_elemento||dd.cod_respuesta from dig_respuestas dd, ins_respuestas ii");
                sql.append(" where dd.cod_respuesta=ii.codigo and ii.cod_tipologia=").append(tipValor[0]);
                if (!UtilCadena.isNullOVacio(tipValor[1])) {
                    sql.append(" and dd.valor in (").append(tipValor[1]).append(")");
                }
                sql.append(" )");
                sql.append(" and d.valor='").append(tipValor[2]).append("'");
            } else {
                sql.append(" select m.id_dig_instrumento from dig_respuestas d, ins_respuestas i, dig_elementos m");
                sql.append(" where d.id_dig_elemento=m.id and d.cod_respuesta=i.codigo and i.cod_tipologia=").append(tipValor[0]);
                sql.append(" and d.valor in (").append(tipValor[1]).append(")");
            }
            sql.append(")");
        }

        /*int n=0;
         for(String[] tipValor:filtro.getListaTipValores()){
         sql.append(" and x.id in (");
         if(!UtilCadena.isNullOVacio(tipValor[1])){
         sql.append(" select m.id_dig_instrumento from dig_respuestas d, ins_respuestas i, dig_elementos m");
         sql.append(" where d.id_dig_elemento=m.id and d.cod_respuesta=i.codigo and d.id_dig_elemento||i.cod_res_padre");
         sql.append(" in (");
         sql.append(" select dd.id_dig_elemento||dd.cod_respuesta from dig_respuestas dd, ins_respuestas ii");
         sql.append(" where dd.cod_respuesta=ii.codigo and ii.cod_tipologia=?codTip");
         sql.append(n);
         sql.append(" and dd.valor=?codTipVal");
         sql.append(n);
         sql.append(" )");
         sql.append(" and d.valor=?val");
         sql.append(n);
         }else{
         sql.append(" select m.id_dig_instrumento from dig_respuestas d, ins_respuestas i, dig_elementos m");
         sql.append(" where d.id_dig_elemento=m.id and d.cod_respuesta=i.codigo and i.cod_tipologia=?codTip");
         sql.append(n);
         sql.append(" and d.valor=?val");
         sql.append(n);
         }
         sql.append(")");
         n++;
         }*/

        if (filtro.getIdNivel0() != null) {
            sql.append(" and n.id_nivel0 = ?n0");
        }
        if (filtro.getIdNivel1() != null) {
            sql.append(" and n.id_nivel1 = ?n1");
        }
        if (filtro.getIdNivel2() != null) {
            sql.append(" and n.id_nivel2 = ?n2");
        }
        if (filtro.getIdNivel3() != null) {
            sql.append(" and n.id_nivel3 = ?n3");
        }
        if (filtro.getIdNivel4() != null) {
            sql.append(" and n.id_nivel4 = ?n4");
        }
        if (filtro.getIdNivel5() != null) {
            sql.append(" and n.id_nivel5 = ?n5");
        }
        if (filtro.getCodZona() != null) {           
            sql.append(" and n.codigo_zona = ?zona");
        }
        if (filtro.getCodSector() != null) {            
            sql.append(" and n.codigo_sector = ?sector");
        }
        //CASOS ESPECIALES

        /**
         * *******************Ambito predominante***********************
         */
        if (CodPregGerenciales.ACC_PROP.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.ACC_CV.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.ACC_OFE.toString().equals(filtro.getPreguntaGerencial())) {

            sql = anadirAccesibilidad(sql, filtro.getCampoSinoPred1());

        } else if (CodPregGerenciales.CV_RA.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.CV_ACC.toString().equals(filtro.getPreguntaGerencial())) {

            sql = anadirControlVigilancia(sql, filtro.getVulnerabilidad());

        } else if (CodPregGerenciales.PROP_RN.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.PROP_RA.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.PROP_SEG.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.PROP_SP.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.PROP_EEE.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.PROP_CON.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.PROP_ACCD.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.PROP_OFE.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.PROP_AMB.toString().equals(filtro.getPreguntaGerencial())) {

            sql = anadirVerifPropiedad(sql, filtro.getCampoSinoPred1());

        } else if (CodPregGerenciales.SEG_RN.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.SEG_RA.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.SEG_PROP1.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.SEG_EEE.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.SEG_OFE.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.SEG_AMB.toString().equals(filtro.getPreguntaGerencial())) {

            sql = anadirCondicionEstructura(sql, filtro.getCampoSinoPred1());

        } else if (CodPregGerenciales.SEG_PROP2.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.SEG_ACCD.toString().equals(filtro.getPreguntaGerencial())) {

            sql = anadirRutaSenalEvacuacion(sql, filtro.getCampoSinoPred1(), filtro.getCampoSinoPred2());

        } else if (CodPregGerenciales.SP_EEE.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.SP_OFE.toString().equals(filtro.getPreguntaGerencial())) {

            sql = anadirServicioPublico(sql, filtro.getCampoSinoPred3());

        } else if (CodPregGerenciales.SP_PROP2.toString().equals(filtro.getPreguntaGerencial())) {

            sql = anadirServicioPublico(sql, filtro.getCampoSinoPred2());

        } else if (CodPregGerenciales.ACCD_EEE.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.ACCD_PROP.toString().equals(filtro.getPreguntaGerencial())) {

            sql = anadirAccesibilidadDentroPredio(sql, filtro.getCampoSinoPred1());

        } else if (CodPregGerenciales.EEE_RN.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.EEE_RA.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.EEE_PROP.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.EEE_CON.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.EEE_SP.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.EEE_ACCD.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.EEE_SEG.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.EEE_AMB.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.EEE_OFE.toString().equals(filtro.getPreguntaGerencial())) {

            sql = anadirEstadEdifEsp(sql, filtro.getCampoSinoPred1());
        } else if (CodPregGerenciales.AMB_PROP.toString().equals(filtro.getPreguntaGerencial())) {
            sql = anadirCumpleAmbiente(sql, filtro.getCampoSinoPred1());
        } else if (CodPregGerenciales.SOS_CON.toString().equals(filtro.getPreguntaGerencial())) {
            sql = anadirSostenibilidad(sql, filtro.getAnalisisConsumoAgua());
        }

        /**
         * *******************Ambito condicional***********************
         */
        if (CodPregGerenciales.RN_ACC.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.CV_ACC.toString().equals(filtro.getPreguntaGerencial())) {

            sql = anadirAccesibilidad(sql, filtro.getCampoSino1());

        } else if (CodPregGerenciales.RN_PROP1.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.RA_PROP1.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.SEG_PROP1.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.SEG_PROP2.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.SP_PROP1.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.SP_PROP2.toString().equals(filtro.getPreguntaGerencial())) {

            sql = anadirVerifPropiedad(sql, filtro.getCampoSino1());

        } else if (CodPregGerenciales.ACCD_PROP.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.ACC_PROP.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.EEE_PROP.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.OFE_PROP.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.AMB_PROP.toString().equals(filtro.getPreguntaGerencial())) {

            sql = anadirVerifPropiedad(sql, filtro.getCampoSino1());

        } else if (CodPregGerenciales.RN_SEG1.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.RA_SEG1.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.PROP_SEG.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.EEE_SEG.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.OFE_SEG.toString().equals(filtro.getPreguntaGerencial())) {

            sql = anadirCondicionEstructura(sql, filtro.getCampoSino1());

        } else if (CodPregGerenciales.RN_SEG2.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.RA_SEG2.toString().equals(filtro.getPreguntaGerencial())) {

            sql = anadirRutaSenalEvacuacion(sql, filtro.getCampoSino1(), filtro.getCampoSino2());

        } else if (CodPregGerenciales.RN_EEE.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.PROP_EEE.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.SEG_EEE.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.SP_EEE.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.ACCD_EEE.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.CON_EEE.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.OFE_EEE.toString().equals(filtro.getPreguntaGerencial())) {

            sql = anadirEstadEdifEsp(sql, filtro.getCampoSino1());

        } else if (CodPregGerenciales.RN_CV.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.RA_CV.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.ACC_CV.toString().equals(filtro.getPreguntaGerencial())) {

            sql = anadirControlVigilancia(sql, filtro.getVulnerabilidad());

        } else if (CodPregGerenciales.RN_ACCD.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.RA_ACCD.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.PROP_ACCD.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.SEG_ACCD.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.EEE_ACCD.toString().equals(filtro.getPreguntaGerencial())) {

            sql = anadirAccesibilidadDentroPredio(sql, filtro.getCampoSino1());

        } else if (CodPregGerenciales.EEE_SP.toString().equals(filtro.getPreguntaGerencial())) {

            sql = anadirServicioPublico(sql, filtro.getCampoSino3());

        } else if (CodPregGerenciales.PROP_AMB.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.EEE_AMB.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.SEG_AMB.toString().equals(filtro.getPreguntaGerencial())) {

            sql = anadirCumpleAmbiente(sql, filtro.getCampoSino1());

        } else if (CodPregGerenciales.CON_SOS.toString().equals(filtro.getPreguntaGerencial())) {

            sql = anadirSostenibilidad(sql, filtro.getAnalisisConsumoAgua());
        }

        if ((Ambitos.A_OFE.toString().equals(filtro.getAmbitoPredominante()) && (!UtilCadena.isNullOVacio(filtro.getCampoSinoPred1()) || !UtilCadena.isNullOVacio(filtro.getCampoSinoPred2()) || !UtilCadena.isNullOVacio(filtro.getCampoSinoPred3())))
                || (Ambitos.A_OFE.toString().equals(filtro.getAmbitoCondicional()) && (!UtilCadena.isNullOVacio(filtro.getCampoSino1()) || !UtilCadena.isNullOVacio(filtro.getCampoSino2()) || !UtilCadena.isNullOVacio(filtro.getCampoSino3())))) {
            sql = anadirCondicionOferta(sql, filtro);
        }

        //String sqlaux = sql.toString().replaceAll("\\?tenant", "").replaceAll("\\?periodo", "");
        String auxSql = sql.toString();
        auxSql = auxSql.replaceAll("\\?tenant", String.valueOf(tpm.getCurrentTenant()));
        auxSql = auxSql.replaceAll("\\?periodo", filtro.getPeriodo().toString());  
        
        
        if(ParamBaseDatos.POSTGRE.equals(UtilProperties.getDbMotor())){
            auxSql = auxSql.replaceAll("\\?start", "");
            auxSql = auxSql.replaceAll("\\?end", "");
        }
        
        
        Query query = tpm.getEm().createNativeQuery(auxSql);       
        
        /*n=0;
         for(String[] tipValor:filtro.getListaTipValores()){
         query.setParameter("codTip"+n, tipValor[0]);            
         if(!UtilCadena.isNullOVacio(tipValor[1])){
         query.setParameter("codTipVal"+n, tipValor[1]);
         }
         query.setParameter("val"+n, tipValor[2]);
         n++;
         }*/

        if (filtro.getIdNivel0() != null) {
            query.setParameter("n0", filtro.getIdNivel0());
        }
        if (filtro.getIdNivel1() != null) {
            query.setParameter("n1", filtro.getIdNivel1());
        }
        if (filtro.getIdNivel2() != null) {
            query.setParameter("n2", filtro.getIdNivel2());
        }
        if (filtro.getIdNivel3() != null) {
            query.setParameter("n3", filtro.getIdNivel3());
        }
        if (filtro.getIdNivel4() != null) {
            query.setParameter("n4", filtro.getIdNivel4());
        }
        if (filtro.getIdNivel5() != null) {
            query.setParameter("n5", filtro.getIdNivel5());
        }
        if (filtro.getCodZona() != null) {
            query.setParameter("zona", filtro.getCodZona());
        }
        if (filtro.getCodSector() != null) {
            query.setParameter("sector", filtro.getCodSector());
        }

        return query.getResultList();
    }

    private StringBuilder posiblidadConstruccionOracle(StringBuilder sql, String campo, String rename) {
        sql.append(", CASE WHEN dividir(tbl_idoneidad_predio.valor,100) >= 1 ");
        sql.append(" AND dividir(tbl_area_const_primer_piso.valor,tbl_area_real_predio.valor)");
        sql.append(" < (SELECT IND_OCUPACION FROM IND_ESTANDAR_LOTE WHERE ID_TNT = aux.ID_TNT AND PERIODO = aux.PERIODO AND ID_PREDIO = aux.ID_PREDIO)");
        sql.append(" AND dividir(tbl_area_cubierta_const.valor,tbl_area_real_predio.valor)");
        sql.append(" < (SELECT IND_CONSTRUCCION FROM IND_ESTANDAR_LOTE WHERE ID_TNT = aux.ID_TNT AND PERIODO = aux.PERIODO AND ID_PREDIO = aux.ID_PREDIO)");
        sql.append(" THEN ").append(campo);
        sql.append(" ELSE '0'");
        sql.append(" END ").append(rename);
        return sql;
    }
    
    private StringBuilder posiblidadConstruccionPostgre(StringBuilder sql, String campo, String rename) {
        sql.append(", CASE WHEN (dividir(char_to_double(tbl_idoneidad_predio.valor),100)) >= 1 ");
        sql.append(" AND (dividir(char_to_double(coalesce(tbl_area_const_primer_piso.valor,'0')), char_to_double(coalesce(tbl_area_real_predio.valor ,'1'))))");
        sql.append(" < (SELECT IND_OCUPACION FROM IND_ESTANDAR_LOTE WHERE ID_TNT = aux.ID_TNT AND PERIODO = aux.PERIODO AND ID_PREDIO = aux.ID_PREDIO)");
        sql.append(" AND (dividir(char_to_double(coalesce(tbl_area_cubierta_const.valor,'0')),char_to_double(coalesce(tbl_area_real_predio.valor,'1')))) ");
        sql.append(" < (SELECT IND_CONSTRUCCION FROM IND_ESTANDAR_LOTE WHERE ID_TNT = aux.ID_TNT AND PERIODO = aux.PERIODO AND ID_PREDIO = aux.ID_PREDIO)");
        sql.append(" THEN ").append(campo);
        sql.append(" ELSE '0'");
        sql.append(" END ").append(rename);
        return sql;
    }

    private StringBuilder joinsRiesgosNaturales(StringBuilder sql, String strSQLConsultarIndicador) {
        i++;
        // tipo de riesgo natural 1
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) tbl_tipo").append(i).append(" on tbl_tipo").append(i).append(".id_tnt = aux.id_tnt and tbl_tipo").append(i).append(".periodo = aux.periodo");
        sql.append(String.format(" and tbl_tipo" + i + ".cod_indicador = '%s'", ParamIndicador.IND_RIESGO_NATURAL_TIPO_RIESGO_1));
        sql.append(" and tbl_tipo").append(i).append(".nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and tbl_tipo").append(i).append(".id_entidad_nivel = n.id_predio");
        // condicion del riesgo natural 1
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) tbl_cond").append(i).append(" on tbl_cond").append(i).append(".id_tnt = aux.id_tnt and tbl_cond").append(i).append(".periodo = aux.periodo");
        sql.append(String.format(" and tbl_cond" + i + ".cod_indicador = '%s'", ParamIndicador.IND_RIESGO_NATURAL_CONDICION_RIESGO_1));
        sql.append(" and tbl_cond").append(i).append(".nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and tbl_cond").append(i).append(".id_entidad_nivel = n.id_predio");
        i++;
        // tipo de riesgo natural 2
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) tbl_tipo").append(i).append(" on tbl_tipo").append(i).append(".id_tnt = aux.id_tnt and tbl_tipo").append(i).append(".periodo = aux.periodo");
        sql.append(String.format(" and tbl_tipo" + i + ".cod_indicador = '%s'", ParamIndicador.IND_RIESGO_NATURAL_TIPO_RIESGO_2));
        sql.append(" and tbl_tipo").append(i).append(".nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and tbl_tipo").append(i).append(".id_entidad_nivel = n.id_predio");
        // condicion del riesgo natural 2
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) tbl_cond").append(i).append(" on tbl_cond").append(i).append(".id_tnt = aux.id_tnt and tbl_cond").append(i).append(".periodo = aux.periodo");
        sql.append(String.format(" and tbl_cond" + i + ".cod_indicador = '%s'", ParamIndicador.IND_RIESGO_NATURAL_CONDICION_RIESGO_2));
        sql.append(" and tbl_cond").append(i).append(".nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and tbl_cond").append(i).append(".id_entidad_nivel = n.id_predio");
        i++;
        // tipo de riesgo natural 3
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) tbl_tipo").append(i).append(" on tbl_tipo").append(i).append(".id_tnt = aux.id_tnt and tbl_tipo").append(i).append(".periodo = aux.periodo");
        sql.append(String.format(" and tbl_tipo" + i + ".cod_indicador = '%s'", ParamIndicador.IND_RIESGO_NATURAL_TIPO_RIESGO_3));
        sql.append(" and tbl_tipo").append(i).append(".nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and tbl_tipo").append(i).append(".id_entidad_nivel = n.id_predio");
        // condicion del riesgo natural 3
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) tbl_cond").append(i).append(" on tbl_cond").append(i).append(".id_tnt = aux.id_tnt and tbl_cond").append(i).append(".periodo = aux.periodo");
        sql.append(String.format(" and tbl_cond" + i + ".cod_indicador = '%s'", ParamIndicador.IND_RIESGO_NATURAL_CONDICION_RIESGO_3));
        sql.append(" and tbl_cond").append(i).append(".nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and tbl_cond").append(i).append(".id_entidad_nivel = n.id_predio");
        i++;
        // Condicion de Riesgo
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) condicion_riesgo").append(i).append(" on condicion_riesgo").append(i).append(".id_tnt = aux.id_tnt and condicion_riesgo").append(i).append(".periodo = aux.periodo");
        sql.append(String.format(" and condicion_riesgo" + i + ".cod_indicador = '%s'", ParamIndicador.IND_RIESGO_NATURAL_CONDICION_RIESGO));
        sql.append(" and condicion_riesgo").append(i).append(".nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and condicion_riesgo").append(i).append(".id_entidad_nivel = n.id_predio");

        return sql;
    }

    private StringBuilder joinsRiesgosAntropicos(StringBuilder sql, String strSQLConsultarIndicador) {
        i++;
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) tbl_tipo").append(i).append(" on tbl_tipo").append(i).append(".id_tnt = aux.id_tnt and tbl_tipo").append(i).append(".periodo = aux.periodo");
        sql.append(String.format(" and tbl_tipo" + i + ".cod_indicador = '%s'", ParamIndicador.IND_RIESGO_ANTROPICO_TIPO_RIESGO_1));
        sql.append(" and tbl_tipo").append(i).append(".nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and tbl_tipo").append(i).append(".id_entidad_nivel = n.id_predio");
        // condicion del riesgo antropico 1
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) tbl_cond").append(i).append(" on tbl_cond").append(i).append(".id_tnt = aux.id_tnt and tbl_cond").append(i).append(".periodo = aux.periodo");
        sql.append(String.format(" and tbl_cond" + i + ".cod_indicador = '%s'", ParamIndicador.IND_RIESGO_ANTROPICO_CONDICION_RIESGO_1));
        sql.append(" and tbl_cond").append(i).append(".nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and tbl_cond").append(i).append(".id_entidad_nivel = n.id_predio");
        i++;
        // tipo de riesgo antropico 2
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) tbl_tipo").append(i).append(" on tbl_tipo").append(i).append(".id_tnt = aux.id_tnt and tbl_tipo").append(i).append(".periodo = aux.periodo");
        sql.append(String.format(" and tbl_tipo" + i + ".cod_indicador = '%s'", ParamIndicador.IND_RIESGO_ANTROPICO_TIPO_RIESGO_2));
        sql.append(" and tbl_tipo").append(i).append(".nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and tbl_tipo").append(i).append(".id_entidad_nivel = n.id_predio");
        // condicion del riesgo antropico 2
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) tbl_cond").append(i).append(" on tbl_cond").append(i).append(".id_tnt = aux.id_tnt and tbl_cond").append(i).append(".periodo = aux.periodo");
        sql.append(String.format(" and tbl_cond" + i + ".cod_indicador = '%s'", ParamIndicador.IND_RIESGO_ANTROPICO_CONDICION_RIESGO_2));
        sql.append(" and tbl_cond").append(i).append(".nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and tbl_cond").append(i).append(".id_entidad_nivel = n.id_predio");
        i++;
        // tipo de riesgo antropico 3
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) tbl_tipo").append(i).append(" on tbl_tipo").append(i).append(".id_tnt = aux.id_tnt and tbl_tipo").append(i).append(".periodo = aux.periodo");
        sql.append(String.format(" and tbl_tipo" + i + ".cod_indicador = '%s'", ParamIndicador.IND_RIESGO_ANTROPICO_TIPO_RIESGO_3));
        sql.append(" and tbl_tipo").append(i).append(".nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and tbl_tipo").append(i).append(".id_entidad_nivel = n.id_predio");
        // condicion del riesgo antropico 3
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) tbl_cond").append(i).append(" on tbl_cond").append(i).append(".id_tnt = aux.id_tnt and tbl_cond").append(i).append(".periodo = aux.periodo");
        sql.append(String.format(" and tbl_cond" + i + ".cod_indicador = '%s'", ParamIndicador.IND_RIESGO_ANTROPICO_CONDICION_RIESGO_3));
        sql.append(" and tbl_cond").append(i).append(".nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and tbl_cond").append(i).append(".id_entidad_nivel = n.id_predio");
        i++;
        // Condicion de Riesgo
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) condicion_riesgo").append(i).append(" on condicion_riesgo").append(i).append(".id_tnt = aux.id_tnt and condicion_riesgo").append(i).append(".periodo = aux.periodo");
        sql.append(String.format(" and condicion_riesgo" + i + ".cod_indicador = '%s'", ParamIndicador.IND_RIESGO_ANTROPICO_CONDICION_RIESGO));
        sql.append(" and condicion_riesgo").append(i).append(".nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and condicion_riesgo").append(i).append(".id_entidad_nivel = n.id_predio");
        i++;
        // tipo de retiro 1
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) tbl_tip_ret").append(i).append(" on tbl_tip_ret").append(i).append(".id_tnt = aux.id_tnt and tbl_tip_ret").append(i).append(".periodo = aux.periodo");
        sql.append(String.format(" and tbl_tip_ret" + i + ".cod_indicador = '%s'", ParamIndicador.IND_RIESGO_ANTROPICO_TIPO_RETIRO_1));
        sql.append(" and tbl_tip_ret").append(i).append(".nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and tbl_tip_ret").append(i).append(".id_entidad_nivel = n.id_predio");
        // metros de retiro 1
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) tbl_mts_ret").append(i).append(" on tbl_mts_ret").append(i).append(".id_tnt = aux.id_tnt and tbl_mts_ret").append(i).append(".periodo = aux.periodo");
        sql.append(String.format(" and tbl_mts_ret" + i + ".cod_indicador = '%s'", ParamIndicador.IND_RIESGO_ANTROPICO_METROS_RETIRO_1));
        sql.append(" and tbl_mts_ret").append(i).append(".nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and tbl_mts_ret").append(i).append(".id_entidad_nivel = n.id_predio");
        i++;
        // tipo de retiro 2
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) tbl_tip_ret").append(i).append(" on tbl_tip_ret").append(i).append(".id_tnt = aux.id_tnt and tbl_tip_ret").append(i).append(".periodo = aux.periodo");
        sql.append(String.format(" and tbl_tip_ret" + i + ".cod_indicador = '%s'", ParamIndicador.IND_RIESGO_ANTROPICO_TIPO_RETIRO_2));
        sql.append(" and tbl_tip_ret").append(i).append(".nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and tbl_tip_ret").append(i).append(".id_entidad_nivel = n.id_predio");
        // metros de retiro 2
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) tbl_mts_ret").append(i).append(" on tbl_mts_ret").append(i).append(".id_tnt = aux.id_tnt and tbl_mts_ret").append(i).append(".periodo = aux.periodo");
        sql.append(String.format(" and tbl_mts_ret" + i + ".cod_indicador = '%s'", ParamIndicador.IND_RIESGO_ANTROPICO_METROS_RETIRO_2));
        sql.append(" and tbl_mts_ret").append(i).append(".nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and tbl_mts_ret").append(i).append(".id_entidad_nivel = n.id_predio");
        i++;
        // tipo de retiro 3
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) tbl_tip_ret").append(i).append(" on tbl_tip_ret").append(i).append(".id_tnt = aux.id_tnt and tbl_tip_ret").append(i).append(".periodo = aux.periodo");
        sql.append(String.format(" and tbl_tip_ret" + i + ".cod_indicador = '%s'", ParamIndicador.IND_RIESGO_ANTROPICO_TIPO_RETIRO_3));
        sql.append(" and tbl_tip_ret").append(i).append(".nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and tbl_tip_ret").append(i).append(".id_entidad_nivel = n.id_predio");
        // metros de retiro 3
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) tbl_mts_ret").append(i).append(" on tbl_mts_ret").append(i).append(".id_tnt = aux.id_tnt and tbl_mts_ret").append(i).append(".periodo = aux.periodo");
        sql.append(String.format(" and tbl_mts_ret" + i + ".cod_indicador = '%s'", ParamIndicador.IND_RIESGO_ANTROPICO_METROS_RETIRO_3));
        sql.append(" and tbl_mts_ret").append(i).append(".nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and tbl_mts_ret").append(i).append(".id_entidad_nivel = n.id_predio");
        i++;
        // tipo de retiro 4
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) tbl_tip_ret").append(i).append(" on tbl_tip_ret").append(i).append(".id_tnt = aux.id_tnt and tbl_tip_ret").append(i).append(".periodo = aux.periodo");
        sql.append(String.format(" and tbl_tip_ret" + i + ".cod_indicador = '%s'", ParamIndicador.IND_RIESGO_ANTROPICO_TIPO_RETIRO_4));
        sql.append(" and tbl_tip_ret").append(i).append(".nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and tbl_tip_ret").append(i).append(".id_entidad_nivel = n.id_predio");
        // metros de retiro 4
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) tbl_mts_ret").append(i).append(" on tbl_mts_ret").append(i).append(".id_tnt = aux.id_tnt and tbl_mts_ret").append(i).append(".periodo = aux.periodo");
        sql.append(String.format(" and tbl_mts_ret" + i + ".cod_indicador = '%s'", ParamIndicador.IND_RIESGO_ANTROPICO_METROS_RETIRO_4));
        sql.append(" and tbl_mts_ret").append(i).append(".nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and tbl_mts_ret").append(i).append(".id_entidad_nivel = n.id_predio");
        i++;
        // tipo de retiro 5
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) tbl_tip_ret").append(i).append(" on tbl_tip_ret").append(i).append(".id_tnt = aux.id_tnt and tbl_tip_ret").append(i).append(".periodo = aux.periodo");
        sql.append(String.format(" and tbl_tip_ret" + i + ".cod_indicador = '%s'", ParamIndicador.IND_RIESGO_ANTROPICO_TIPO_RETIRO_5));
        sql.append(" and tbl_tip_ret").append(i).append(".nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and tbl_tip_ret").append(i).append(".id_entidad_nivel = n.id_predio");
        // metros de retiro 5
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) tbl_mts_ret").append(i).append(" on tbl_mts_ret").append(i).append(".id_tnt = aux.id_tnt and tbl_mts_ret").append(i).append(".periodo = aux.periodo");
        sql.append(String.format(" and tbl_mts_ret" + i + ".cod_indicador = '%s'", ParamIndicador.IND_RIESGO_ANTROPICO_METROS_RETIRO_5));
        sql.append(" and tbl_mts_ret").append(i).append(".nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and tbl_mts_ret").append(i).append(".id_entidad_nivel = n.id_predio");

        return sql;
    }

    private StringBuilder joinsAccesibilidad(StringBuilder sql, String strSQLConsultarIndicador) {
        i++;
        // tipo de Acceso 1
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) tbl_tipo").append(i).append(" on tbl_tipo").append(i).append(".id_tnt = aux.id_tnt and tbl_tipo").append(i).append(".periodo = aux.periodo");
        sql.append(String.format(" and tbl_tipo" + i + ".cod_indicador = '%s'", ParamIndicador.IND_ACCESIBILIDAD_TIPO_ACCESO_1));
        sql.append(" and tbl_tipo").append(i).append(".nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and tbl_tipo").append(i).append(".id_entidad_nivel = n.id_predio");
        // condicion de Acceso 1
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) tbl_cond").append(i).append(" on tbl_cond").append(i).append(".id_tnt = aux.id_tnt and tbl_cond").append(i).append(".periodo = aux.periodo");
        sql.append(String.format(" and tbl_cond" + i + ".cod_indicador = '%s'", ParamIndicador.IND_ACCESIBILIDAD_CONDICION_ACCESO_1));
        sql.append(" and tbl_cond").append(i).append(".nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and tbl_cond").append(i).append(".id_entidad_nivel = n.id_predio");
        i++;
        // tipo de Acceso 2
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) tbl_tipo").append(i).append(" on tbl_tipo").append(i).append(".id_tnt = aux.id_tnt and tbl_tipo").append(i).append(".periodo = aux.periodo");
        sql.append(String.format(" and tbl_tipo" + i + ".cod_indicador = '%s'", ParamIndicador.IND_ACCESIBILIDAD_TIPO_ACCESO_2));
        sql.append(" and tbl_tipo").append(i).append(".nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and tbl_tipo").append(i).append(".id_entidad_nivel = n.id_predio");
        // condicion de Acceso 2
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) tbl_cond").append(i).append(" on tbl_cond").append(i).append(".id_tnt = aux.id_tnt and tbl_cond").append(i).append(".periodo = aux.periodo");
        sql.append(String.format(" and tbl_cond" + i + ".cod_indicador = '%s'", ParamIndicador.IND_ACCESIBILIDAD_CONDICION_ACCESO_2));
        sql.append(" and tbl_cond").append(i).append(".nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and tbl_cond").append(i).append(".id_entidad_nivel = n.id_predio");
        i++;
        // tipo de Acceso 3
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) tbl_tipo").append(i).append(" on tbl_tipo").append(i).append(".id_tnt = aux.id_tnt and tbl_tipo").append(i).append(".periodo = aux.periodo");
        sql.append(String.format(" and tbl_tipo" + i + ".cod_indicador = '%s'", ParamIndicador.IND_ACCESIBILIDAD_TIPO_ACCESO_3));
        sql.append(" and tbl_tipo").append(i).append(".nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and tbl_tipo").append(i).append(".id_entidad_nivel = n.id_predio");
        // condicion de Acceso 3
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) tbl_cond").append(i).append(" on tbl_cond").append(i).append(".id_tnt = aux.id_tnt and tbl_cond").append(i).append(".periodo = aux.periodo");
        sql.append(String.format(" and tbl_cond" + i + ".cod_indicador = '%s'", ParamIndicador.IND_ACCESIBILIDAD_CONDICION_ACCESO_3));
        sql.append(" and tbl_cond").append(i).append(".nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and tbl_cond").append(i).append(".id_entidad_nivel = n.id_predio");
        i++;
        // tipo de Acceso 4
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) tbl_tipo").append(i).append(" on tbl_tipo").append(i).append(".id_tnt = aux.id_tnt and tbl_tipo").append(i).append(".periodo = aux.periodo");
        sql.append(String.format(" and tbl_tipo" + i + ".cod_indicador = '%s'", ParamIndicador.IND_ACCESIBILIDAD_TIPO_ACCESO_4));
        sql.append(" and tbl_tipo").append(i).append(".nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and tbl_tipo").append(i).append(".id_entidad_nivel = n.id_predio");
        // condicion de Acceso 4
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) tbl_cond").append(i).append(" on tbl_cond").append(i).append(".id_tnt = aux.id_tnt and tbl_cond").append(i).append(".periodo = aux.periodo");
        sql.append(String.format(" and tbl_cond" + i + ".cod_indicador = '%s'", ParamIndicador.IND_ACCESIBILIDAD_CONDICION_ACCESO_4));
        sql.append(" and tbl_cond").append(i).append(".nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and tbl_cond").append(i).append(".id_entidad_nivel = n.id_predio");
        i++;
        // tipo de Acceso 5
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) tbl_tipo").append(i).append(" on tbl_tipo").append(i).append(".id_tnt = aux.id_tnt and tbl_tipo").append(i).append(".periodo = aux.periodo");
        sql.append(String.format(" and tbl_tipo" + i + ".cod_indicador = '%s'", ParamIndicador.IND_ACCESIBILIDAD_TIPO_ACCESO_5));
        sql.append(" and tbl_tipo").append(i).append(".nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and tbl_tipo").append(i).append(".id_entidad_nivel = n.id_predio");
        // condicion de Acceso 5
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) tbl_cond").append(i).append(" on tbl_cond").append(i).append(".id_tnt = aux.id_tnt and tbl_cond").append(i).append(".periodo = aux.periodo");
        sql.append(String.format(" and tbl_cond" + i + ".cod_indicador = '%s'", ParamIndicador.IND_ACCESIBILIDAD_CONDICION_ACCESO_5));
        sql.append(" and tbl_cond").append(i).append(".nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and tbl_cond").append(i).append(".id_entidad_nivel = n.id_predio");

        //Numero de Medios de Trasporte para llegar al predio
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) tbl_num_medios_transportes on tbl_num_medios_transportes.id_tnt = aux.id_tnt and tbl_num_medios_transportes.periodo = aux.periodo");
        sql.append(String.format(" and tbl_num_medios_transportes.cod_indicador = '%s'", ParamIndicador.IND_ACCESIBILIDAD_NUMERO_MEDIOS_TRANSPORTES));
        sql.append(" and tbl_num_medios_transportes.nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and tbl_num_medios_transportes.id_entidad_nivel = n.id_predio");

        //Nro de acceso en condiciones 1 y 2
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) tbl_cond_1o2 on tbl_cond_1o2.id_tnt = aux.id_tnt and tbl_cond_1o2.periodo = aux.periodo");
        sql.append(String.format(" and tbl_cond_1o2.cod_indicador = '%s'", ParamIndicador.IND_ACCESIBILIDAD_NUMERO_ACCESOS_CONDICION_1_2));
        sql.append(" and tbl_cond_1o2.nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and tbl_cond_1o2.id_entidad_nivel = n.id_predio");

        //Condición de accesibilidad
        sql.append(" inner join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) tbl_accesibilidad on tbl_accesibilidad.id_tnt = aux.id_tnt and tbl_accesibilidad.periodo = aux.periodo");
        sql.append(String.format(" and tbl_accesibilidad.cod_indicador = '%s'", ParamIndicador.IND_ACCESIBILIDAD_CONDICION_ACCESIBILIDAD));
        sql.append(" and tbl_accesibilidad.nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and tbl_accesibilidad.id_entidad_nivel = n.id_predio");

        return sql;
    }

    private StringBuilder joinsOferta(StringBuilder sql, String strSQLConsultarIndicador) {
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) tbl_area_real_predio on tbl_area_real_predio.id_tnt = aux.id_tnt and tbl_area_real_predio.periodo = aux.periodo");
        sql.append(String.format(" and tbl_area_real_predio.cod_indicador = '%s'", ParamIndicador.IND_AREA_REAL_PREDIO));
        sql.append(" and tbl_area_real_predio.nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and tbl_area_real_predio.id_entidad_nivel = n.id_predio");

        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) tbl_estandar_predio on tbl_estandar_predio.id_tnt = aux.id_tnt and tbl_estandar_predio.periodo = aux.periodo");
        sql.append(String.format(" and tbl_estandar_predio.cod_indicador = '%s'", ParamIndicador.IND_AMPLIACION_ESTANDAR_PREDIO));
        sql.append(" and tbl_estandar_predio.nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and tbl_estandar_predio.id_entidad_nivel = n.id_predio");

        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) tbl_idoneidad_predio on tbl_idoneidad_predio.id_tnt = aux.id_tnt and tbl_idoneidad_predio.periodo = aux.periodo");
        sql.append(String.format(" and tbl_idoneidad_predio.cod_indicador = '%s'", ParamIndicador.IND_AMPLIACION_IDONEIDAD_PREDIO));
        sql.append(" and tbl_idoneidad_predio.nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and tbl_idoneidad_predio.id_entidad_nivel = n.id_predio");

        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) tbl_cupos_total on tbl_cupos_total.id_tnt = aux.id_tnt and tbl_cupos_total.periodo = aux.periodo");
        sql.append(String.format(" and tbl_cupos_total.cod_indicador = '%s'", ParamIndicador.IND_AMPLIACION_CUPOS_PREDIO));
        sql.append(" and tbl_cupos_total.nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and tbl_cupos_total.id_entidad_nivel = n.id_predio");

        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) tbl_m2_requeridos_predio on tbl_m2_requeridos_predio.id_tnt = aux.id_tnt and tbl_m2_requeridos_predio.periodo = aux.periodo");
        sql.append(String.format(" and tbl_m2_requeridos_predio.cod_indicador = '%s'", ParamIndicador.IND_AMPLIACION_M2_REQUERIDOS_PREDIO));
        sql.append(" and tbl_m2_requeridos_predio.nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and tbl_m2_requeridos_predio.id_entidad_nivel = n.id_predio");

        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) tbl_area_cubierta_const on tbl_area_cubierta_const.id_tnt = aux.id_tnt and tbl_area_cubierta_const.periodo = aux.periodo");
        sql.append(String.format(" and tbl_area_cubierta_const.cod_indicador = '%s'", ParamIndicador.IND_TOTAL_AREA_CONSTRUCCION_PREDIO));
        sql.append(" and tbl_area_cubierta_const.nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and tbl_area_cubierta_const.id_entidad_nivel = n.id_predio");

        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) tbl_estandar_construccion on tbl_estandar_construccion.id_tnt = aux.id_tnt and tbl_estandar_construccion.periodo = aux.periodo");
        sql.append(String.format(" and tbl_estandar_construccion.cod_indicador = '%s'", ParamIndicador.IND_AMPLIACION_ESTANDAR_CONSTRUCCION));
        sql.append(" and tbl_estandar_construccion.nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and tbl_estandar_construccion.id_entidad_nivel = n.id_predio");

        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) tbl_idoneidad_construccion on tbl_idoneidad_construccion.id_tnt = aux.id_tnt and tbl_idoneidad_construccion.periodo = aux.periodo");
        sql.append(String.format(" and tbl_idoneidad_construccion.cod_indicador = '%s'", ParamIndicador.IND_AMPLIACION_IDONEIDAD_CONSTRUCCION));
        sql.append(" and tbl_idoneidad_construccion.nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and tbl_idoneidad_construccion.id_entidad_nivel = n.id_predio");

        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) tbl_m2_requeridos_const on tbl_m2_requeridos_const.id_tnt = aux.id_tnt and tbl_m2_requeridos_const.periodo = aux.periodo");
        sql.append(String.format(" and tbl_m2_requeridos_const.cod_indicador = '%s'", ParamIndicador.IND_AMPLIACION_M2_REQUERIDOS_CONSTRUCCION));
        sql.append(" and tbl_m2_requeridos_const.nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and tbl_m2_requeridos_const.id_entidad_nivel = n.id_predio");

        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) tbl_area_ambiente_aula on tbl_area_ambiente_aula.id_tnt = aux.id_tnt and tbl_area_ambiente_aula.periodo = aux.periodo");
        sql.append(String.format(" and tbl_area_ambiente_aula.cod_indicador = '%s'", ParamIndicador.IND_AMPLIACION_AREA_AULA));
        sql.append(" and tbl_area_ambiente_aula.nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and tbl_area_ambiente_aula.id_entidad_nivel = n.id_predio");

        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) tbl_total_ambiente_aula on tbl_total_ambiente_aula.id_tnt = aux.id_tnt and tbl_total_ambiente_aula.periodo = aux.periodo");
        sql.append(String.format(" and tbl_total_ambiente_aula.cod_indicador = '%s'", ParamIndicador.IND_AMPLIACION_NUM_TOTAL_AULA));
        sql.append(" and tbl_total_ambiente_aula.nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and tbl_total_ambiente_aula.id_entidad_nivel = n.id_predio");

        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) tbl_estandar_aula on tbl_estandar_aula.id_tnt = aux.id_tnt and tbl_estandar_aula.periodo = aux.periodo");
        sql.append(String.format(" and tbl_estandar_aula.cod_indicador = '%s'", ParamIndicador.IND_AMPLIACION_ESTANDAR_AULA));
        sql.append(" and tbl_estandar_aula.nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and tbl_estandar_aula.id_entidad_nivel = n.id_predio");

        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) tbl_idoneidad_aula on tbl_idoneidad_aula.id_tnt = aux.id_tnt and tbl_idoneidad_aula.periodo = aux.periodo");
        sql.append(String.format(" and tbl_idoneidad_aula.cod_indicador = '%s'", ParamIndicador.IND_AMPLIACION_IDONEIDAD_AULA));
        sql.append(" and tbl_idoneidad_aula.nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and tbl_idoneidad_aula.id_entidad_nivel = n.id_predio");

        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) tbl_m2_requeridos_aula on tbl_m2_requeridos_aula.id_tnt = aux.id_tnt and tbl_m2_requeridos_aula.periodo = aux.periodo");
        sql.append(String.format(" and tbl_m2_requeridos_aula.cod_indicador = '%s'", ParamIndicador.IND_AMPLIACION_M2_REQUERIDOS_AULA));
        sql.append(" and tbl_m2_requeridos_aula.nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and tbl_m2_requeridos_aula.id_entidad_nivel = n.id_predio");

        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) tbl_espacios_requeridos_aula on tbl_espacios_requeridos_aula.id_tnt = aux.id_tnt and tbl_espacios_requeridos_aula.periodo = aux.periodo");
        sql.append(String.format(" and tbl_espacios_requeridos_aula.cod_indicador = '%s'", ParamIndicador.IND_AMPLIACION_ESPACIO_REQUERIDOS_AULA));
        sql.append(" and tbl_espacios_requeridos_aula.nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and tbl_espacios_requeridos_aula.id_entidad_nivel = n.id_predio");

        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) tbl_area_const_primer_piso on tbl_area_const_primer_piso.id_tnt = aux.id_tnt and tbl_area_const_primer_piso.periodo = aux.periodo");
        sql.append(String.format(" and tbl_area_const_primer_piso.cod_indicador = '%s'", ParamIndicador.IND_AMPLIACION_AREA_CONSTRUCCION_PRIMER_PISO));
        sql.append(" and tbl_area_const_primer_piso.nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and tbl_area_const_primer_piso.id_entidad_nivel = n.id_predio");

        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) tbl_posib_const_primer_piso on tbl_posib_const_primer_piso.id_tnt = aux.id_tnt and tbl_posib_const_primer_piso.periodo = aux.periodo");
        sql.append(String.format(" and tbl_posib_const_primer_piso.cod_indicador = '%s'", ParamIndicador.IND_AMPLIACION_POSIBILIDAD_CONSTRUCCION_PRIMER_PISO));
        sql.append(" and tbl_posib_const_primer_piso.nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and tbl_posib_const_primer_piso.id_entidad_nivel = n.id_predio");

        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) tbl_posib_const_altura on tbl_posib_const_altura.id_tnt = aux.id_tnt and tbl_posib_const_altura.periodo = aux.periodo");
        sql.append(String.format(" and tbl_posib_const_altura.cod_indicador = '%s'", ParamIndicador.IND_AMPLIACION_POSIBILIDAD_CONSTRUCCION_ALTURA));
        sql.append(" and tbl_posib_const_altura.nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and tbl_posib_const_altura.id_entidad_nivel = n.id_predio");

        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) tbl_posib_const_total on tbl_posib_const_total.id_tnt = aux.id_tnt and tbl_posib_const_total.periodo = aux.periodo");
        sql.append(String.format(" and tbl_posib_const_total.cod_indicador = '%s'", ParamIndicador.IND_AMPLIACION_POSIBILIDAD_CONSTRUCCION_TOTAL));
        sql.append(" and tbl_posib_const_total.nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and tbl_posib_const_total.id_entidad_nivel = n.id_predio");

        return sql;
    }

    private StringBuilder joinsPropiedadLote(StringBuilder sql, String strSQLConsultarIndicador) {
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) tbl_tipo_propietario on tbl_tipo_propietario.id_tnt = aux.id_tnt and tbl_tipo_propietario.periodo = aux.periodo");
        sql.append(String.format(" and tbl_tipo_propietario.cod_indicador = '%s'", ParamIndicador.IND_TIPO_PROPIETARIO));
        sql.append(" and tbl_tipo_propietario.nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and tbl_tipo_propietario.id_entidad_nivel = n.id_predio");

        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) tbl_problema_legalizacion on tbl_problema_legalizacion.id_tnt = aux.id_tnt and tbl_problema_legalizacion.periodo = aux.periodo");
        sql.append(String.format(" and tbl_problema_legalizacion.cod_indicador = '%s'", ParamIndicador.IND_PROBLEMA_LEGALIZACION));
        sql.append(" and tbl_problema_legalizacion.nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and tbl_problema_legalizacion.id_entidad_nivel = n.id_predio");

        return sql;
    }

    private StringBuilder joinsVulnerabilidad(StringBuilder sql, String strSQLConsultarIndicador) {
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) tbl_vulnerabilidad on tbl_vulnerabilidad.id_tnt = aux.id_tnt and tbl_vulnerabilidad.periodo = aux.periodo");
        sql.append(String.format(" and tbl_vulnerabilidad.cod_indicador = '%s'", ParamIndicador.IND_CONTROL_VIGILANCIA_VULNERABILIDAD));
        sql.append(" and tbl_vulnerabilidad.nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and tbl_vulnerabilidad.id_entidad_nivel = n.id_predio");

        return sql;
    }
    
    private StringBuilder joinsAccesibilidadInterna(StringBuilder sql, String strSQLConsultarIndicador) {
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) tbl_acceso_discapacitado on tbl_acceso_discapacitado.id_tnt = aux.id_tnt and tbl_acceso_discapacitado.periodo = aux.periodo");
        sql.append(String.format(" and tbl_acceso_discapacitado.cod_indicador = '%s'", ParamIndicador.IND_ACCESO_DISCAPACITADOS));
        sql.append(" and tbl_acceso_discapacitado.nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and tbl_acceso_discapacitado.id_entidad_nivel = n.id_predio");

        return sql;
    }
    
    private StringBuilder joinsAmbiente(StringBuilder sql, String strSQLConsultarIndicador, String unidadFuncional) {
        if (!UtilCadena.isNullOVacio(unidadFuncional)) {
            //tipo de espacios
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) tbl_tipo_espacio on tbl_tipo_espacio.id_tnt = aux.id_tnt and tbl_tipo_espacio.periodo = aux.periodo");
            sql.append(String.format(" and tbl_tipo_espacio.cod_indicador = '%s'", ParamIndicador.IND_AMBIENTE_ESPACIOS_TIPO));
            sql.append(" and tbl_tipo_espacio.nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
            sql.append(" and tbl_tipo_espacio.id_entidad_nivel = n.id_predio");
            sql.append(" and tbl_tipo_espacio.cod_valor ='");
            sql.append(unidadFuncional).append("'");

            //total de espacios
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) tbl_total_espacio on tbl_total_espacio.id_tnt = aux.id_tnt and tbl_total_espacio.periodo = aux.periodo");
            sql.append(String.format(" and tbl_total_espacio.cod_indicador = '%s'", ParamIndicador.IND_TOTAL_ESPACIOS));
            sql.append(" and tbl_total_espacio.nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
            sql.append(" and tbl_total_espacio.id_entidad_nivel = n.id_predio");

            //area tipo de espacio
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) area_tipo_espacio on area_tipo_espacio.id_tnt = aux.id_tnt and area_tipo_espacio.periodo = aux.periodo");
            sql.append(String.format(" and area_tipo_espacio.cod_indicador = '%s'", ParamIndicador.IND_AMBIENTE_AREA_TIPO_ESPACIO));
            sql.append(" and area_tipo_espacio.nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
            sql.append(" and area_tipo_espacio.id_entidad_nivel = n.id_predio");
            sql.append(" and area_tipo_espacio.cod_valor ='");
            sql.append(unidadFuncional).append("'");

            // Número de estudiantes de la mayor jornada
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) num_estudiantes_max_jornada on num_estudiantes_max_jornada.id_tnt = aux.id_tnt and num_estudiantes_max_jornada.periodo = aux.periodo");
            sql.append(String.format(" and num_estudiantes_max_jornada.cod_indicador = '%s'", ParamIndicador.IND_TOTAL_ESTUDIANTES_MAXIMA_JORNADA));
            sql.append(" and num_estudiantes_max_jornada.nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
            sql.append(" and num_estudiantes_max_jornada.id_entidad_nivel = n.id_predio");

            // m2/alumno (relacion de m2 por estudiante)
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) m2_alumno on m2_alumno.id_tnt = aux.id_tnt and m2_alumno.periodo = aux.periodo");
            sql.append(String.format(" and m2_alumno.cod_indicador = '%s'", ParamIndicador.IND_AMBIENTE_M2_ALUMNO));
            sql.append(" and m2_alumno.nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
            sql.append(" and m2_alumno.id_entidad_nivel = n.id_predio");
            sql.append(" and m2_alumno.cod_valor ='");
            sql.append(unidadFuncional).append("'");

            // condicion de m2/alumno (condicion relacion de m2 por estudiante)
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) condicion_m2_alumno on condicion_m2_alumno.id_tnt = aux.id_tnt and condicion_m2_alumno.periodo = aux.periodo");
            sql.append(String.format(" and condicion_m2_alumno.cod_indicador = '%s'", ParamIndicador.IND_AMBIENTE_CONDICION_M2_ALUMNO));
            sql.append(" and condicion_m2_alumno.nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
            sql.append(" and condicion_m2_alumno.id_entidad_nivel = n.id_predio");
            sql.append(" and condicion_m2_alumno.cod_valor ='");
            sql.append(unidadFuncional).append("'");            
                    
            if (unidadFuncional.equals("EXPANSIONES_RECREATIVAS")) {
                // deficit_estandar
                sql.append(" left join (");
                sql.append(strSQLConsultarIndicador);
                sql.append(" ) deficit_estandar on deficit_estandar.id_tnt = aux.id_tnt and deficit_estandar.periodo = aux.periodo");
                sql.append(String.format(" and deficit_estandar.cod_indicador = '%s'", ParamIndicador.IND_AMBIENTE_EXPANSIONES_RECREATIVAS_DEFICIT_ESTANDAR));
                sql.append(" and deficit_estandar.nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
                sql.append(" and deficit_estandar.id_entidad_nivel = n.id_predio");

                // cumplimiento_norma
                sql.append(" left join (");
                sql.append(strSQLConsultarIndicador);
                sql.append(" ) cumplimiento_norma on cumplimiento_norma.id_tnt = aux.id_tnt and cumplimiento_norma.periodo = aux.periodo");
                sql.append(String.format(" and cumplimiento_norma.cod_indicador = '%s'", ParamIndicador.IND_AMBIENTE_EXPANSIONES_RECREATIVAS_CUMPLIMIENTO_NORMA));
                sql.append(" and cumplimiento_norma.nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
                sql.append(" and cumplimiento_norma.id_entidad_nivel = n.id_predio");
            } else {
                // deficit_estandar
                sql.append(" left join (");
                sql.append(strSQLConsultarIndicador);
                sql.append(" ) deficit_estandar on deficit_estandar.id_tnt = aux.id_tnt and deficit_estandar.periodo = aux.periodo");
                sql.append(String.format(" and deficit_estandar.cod_indicador = '%s'", ParamIndicador.IND_AMBIENTE_DEFICIT_ESTANDAR));
                sql.append(" and deficit_estandar.nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
                sql.append(" and deficit_estandar.id_entidad_nivel = n.id_predio");
                sql.append(" and deficit_estandar.cod_valor ='");
                sql.append(unidadFuncional).append("'");

                // cumplimiento_norma
                sql.append(" left join (");
                sql.append(strSQLConsultarIndicador);
                sql.append(" ) cumplimiento_norma on cumplimiento_norma.id_tnt = aux.id_tnt and cumplimiento_norma.periodo = aux.periodo");
                sql.append(String.format(" and cumplimiento_norma.cod_indicador = '%s'", ParamIndicador.IND_AMBIENTE_CUMPLIMIENTO_NORMA));
                sql.append(" and cumplimiento_norma.nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
                sql.append(" and cumplimiento_norma.id_entidad_nivel = n.id_predio");
                sql.append(" and cumplimiento_norma.cod_valor ='");
                sql.append(unidadFuncional).append("'");
            }
  
        }
        return sql;
    }

    private StringBuilder joinsConfort(StringBuilder sql, FiltroConsultaDinamica filtro) {
        // # de duchas según condición 
        sql.append(filtro.getCondicionDuchas()!=null?" inner":" left");
        sql.append(" join (");
        sql.append(agregarCondicionConfort(this.consultarFormulaSQLIndicador(ParamIndicador.IND_CONFORT_NUMERO_DUCHAS, ParamNivelConsul.PREDIO), filtro.getCondicionDuchas()));
        sql.append(" ) tb_duchas_condicion on tb_duchas_condicion.id_dig_instrumento = aux.id_dig_instrumento and tb_duchas_condicion.id_predio = aux.id_predio");

        // # de lavamanos según condición 
        sql.append(filtro.getCondicionLavamanos()!=null?" inner":" left");
        sql.append(" join (");
        sql.append(agregarCondicionConfort(this.consultarFormulaSQLIndicador(ParamIndicador.IND_CONFORT_NUMERO_LAVAMANOS, ParamNivelConsul.PREDIO), filtro.getCondicionLavamanos()));
        sql.append(" ) tb_lavamanos_condicion on tb_lavamanos_condicion.id_dig_instrumento = aux.id_dig_instrumento and tb_lavamanos_condicion.id_predio = aux.id_predio");

        // # de orinales según condición 
        sql.append(filtro.getCondicionOrinales()!=null?" inner":" left");
        sql.append(" join (");
        sql.append(agregarCondicionConfort(this.consultarFormulaSQLIndicador(ParamIndicador.IND_CONFORT_NUMERO_ORINALES, ParamNivelConsul.PREDIO), filtro.getCondicionOrinales()));
        sql.append(" ) tb_orinales_condicion on tb_orinales_condicion.id_dig_instrumento = aux.id_dig_instrumento and tb_orinales_condicion.id_predio = aux.id_predio");

        // # de inodoros según condición 
        sql.append(filtro.getCondicionInodoros()!=null?" inner":" left");
        sql.append(" join (");
        sql.append(agregarCondicionConfort(this.consultarFormulaSQLIndicador(ParamIndicador.IND_CONFORT_NUMERO_INODOROS, ParamNivelConsul.PREDIO), filtro.getCondicionInodoros()));
        sql.append(" ) tb_inodoros_condicion on tb_inodoros_condicion.id_dig_instrumento = aux.id_dig_instrumento and tb_inodoros_condicion.id_predio = aux.id_predio");

        return sql;
    }
    
    private StringBuilder joinsSostenibilidad(StringBuilder sql, String strSQLConsultarIndicador) {
       sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) tbl_analisis_consumo_agua on tbl_analisis_consumo_agua.id_tnt = aux.id_tnt and tbl_analisis_consumo_agua.periodo = aux.periodo");
        sql.append(String.format(" and tbl_analisis_consumo_agua.cod_indicador = '%s'", ParamIndicador.IND_SERVICIO_ANALISIS_CONSUMO_PROMEDIO_AGUA));
        sql.append(" and tbl_analisis_consumo_agua.nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and tbl_analisis_consumo_agua.id_entidad_nivel = n.id_predio");

        return sql;
    }
    
    private StringBuilder joinsSeguridad(StringBuilder sql, String strSQLConsultarIndicador) {
        //condicion de estructura
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) tb_estado_estructura on tb_estado_estructura.id_tnt = aux.id_tnt and tb_estado_estructura.periodo = aux.periodo");
        sql.append(String.format(" and tb_estado_estructura.cod_indicador = '%s'", ParamIndicador.IND_SEGURIDAD_ESTADO_ESTRUCTURA));
        sql.append(" and tb_estado_estructura.nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and tb_estado_estructura.id_entidad_nivel = n.id_predio");
        
        //SenalizacionEvacuacion
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) tb_senalizacion_evacuacion on tb_senalizacion_evacuacion.id_tnt = aux.id_tnt and tb_senalizacion_evacuacion.periodo = aux.periodo");
        sql.append(String.format(" and tb_senalizacion_evacuacion.cod_indicador = '%s'", ParamIndicador.IND_SEGURIDAD_SENALIZACION_EVACUACION));
        sql.append(" and tb_senalizacion_evacuacion.nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and tb_senalizacion_evacuacion.id_entidad_nivel = n.id_predio");
        
        //AnalisisRutaEvacuacion
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) condicion_ruta_evacuacion on condicion_ruta_evacuacion.id_tnt = aux.id_tnt and condicion_ruta_evacuacion.periodo = aux.periodo");
        sql.append(String.format(" and condicion_ruta_evacuacion.cod_indicador = '%s'", ParamIndicador.IND_SEGURIDAD_ANALISIS_RUTA_EVACUACION));
        sql.append(" and condicion_ruta_evacuacion.nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and condicion_ruta_evacuacion.id_entidad_nivel = n.id_predio");
        
        return sql;
    }
    
    private StringBuilder joinsEEE(StringBuilder sql, String strSQLConsultarIndicador) {
        //condicion de materialidad
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) condicion_materialidad on condicion_materialidad.id_tnt = aux.id_tnt and condicion_materialidad.periodo = aux.periodo");
        sql.append(String.format(" and condicion_materialidad.cod_indicador = '%s'", ParamIndicador.IND_EDIFICIO_CONDICION_MATERIALIDAD_PREDIO));
        sql.append(" and condicion_materialidad.nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and condicion_materialidad.id_entidad_nivel = n.id_predio");
        
        return sql;
    }
    
    private StringBuilder joinsSP(StringBuilder sql, String strSQLConsultarIndicador,ServiciosPublicos sp, CalificacionCondicion condicionSP) {
        ParamIndicador indCondicionSP, indDispSP;
        if (sp.equals(ServiciosPublicos.SistemaReciclaje)) {
            indCondicionSP = ParamIndicador.IND_SERVICIO_CONDICION_SISTEMA_RECICLAJE;
            indDispSP = ParamIndicador.IND_SERVICIO_DISPONIBILIDAD_SISTEMA_RECICLAJE;
        } else if (sp.equals(ServiciosPublicos.DSEE)) {
            indCondicionSP = ParamIndicador.IND_SERVICIO_CONDICION_ENERGIA_ELECTRICA;
            indDispSP = ParamIndicador.IND_SERVICIO_DISPONIBILIDAD_ENERGIA_ELECTRICA;
        } else if (sp.equals(ServiciosPublicos.DSG)) {
            indCondicionSP = ParamIndicador.IND_SERVICIO_CONDICION_GAS;
            indDispSP = ParamIndicador.IND_SERVICIO_DISPONIBILIDAD_GAS;
        } else if (sp.equals(ServiciosPublicos.DSRB)) {
            indCondicionSP = ParamIndicador.IND_SERVICIO_CONDICION_RECOLECCION_BASURAS;
            indDispSP = ParamIndicador.IND_SERVICIO_DISPONIBILIDAD_RECOLECCION_BASURAS;
        } else if (sp.equals(ServiciosPublicos.DSRAAS)) {
            indCondicionSP = ParamIndicador.IND_SERVICIO_CONDICION_RED_ALCANTARILLADO;
            indDispSP = ParamIndicador.IND_SERVICIO_DISPONIBILIDAD_RED_ALCANTARILLADO;
        } else if (sp.equals(ServiciosPublicos.DSRP)) {
            indCondicionSP = ParamIndicador.IND_SERVICIO_CONDICION_RED_PLUVIAL;
            indDispSP = ParamIndicador.IND_SERVICIO_DISPONIBILIDAD_RED_PLUVIAL;
        } else if (sp.equals(ServiciosPublicos.DSI)) {
            indCondicionSP = ParamIndicador.IND_SERVICIO_CONDICION_INTERNET;
            indDispSP = ParamIndicador.IND_SERVICIO_DISPONIBILIDAD_INTERNET;
        } else if (sp.equals(ServiciosPublicos.DST)) {
            indCondicionSP = ParamIndicador.IND_SERVICIO_CONDICION_TELEFONO;
            indDispSP = ParamIndicador.IND_SERVICIO_DISPONIBILIDAD_TELEFONO;
        } else {
            indCondicionSP = ParamIndicador.IND_SERVICIO_CONDICION_AGUA;
            indDispSP = ParamIndicador.IND_SERVICIO_DISPONIBILIDAD_AGUA;
        }

        //condicion servicio publico
        sql.append(condicionSP != null ? " inner" : " left");
        sql.append(" join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) cond_serv on cond_serv.id_tnt = aux.id_tnt and cond_serv.periodo = aux.periodo");
        sql.append(String.format(" and cond_serv.cod_indicador = '%s'", indCondicionSP));
        sql.append(" and cond_serv.nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and cond_serv.id_entidad_nivel = n.id_predio");
        sql.append(condicionSP != null ? " and cond_serv.valor='" + condicionSP.getValor() + "'" : "");

        //tiene o no tiene servicio publico
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) tiene_servicio_pub on tiene_servicio_pub.id_tnt = aux.id_tnt and tiene_servicio_pub.periodo = aux.periodo");
        sql.append(String.format(" and tiene_servicio_pub.cod_indicador = '%s'", indDispSP));
        sql.append(" and tiene_servicio_pub.nivel_agrupamiento = ").append(ParamNivelConsul.PREDIO.getNivel());
        sql.append(" and tiene_servicio_pub.id_entidad_nivel = n.id_predio");

        return sql;
    }

    private StringBuilder anadirAccesibilidad(StringBuilder sql, SiNo campo) {
        if (campo != null) {
            if (campo.equals(SiNo.No)) {
                sql.append(" and tbl_accesibilidad.valor = 'sql.ind.general.cumple'");
            } else {
                sql.append(" and tbl_accesibilidad.valor != 'sql.ind.general.cumple'");
            }
        }
        return sql;
    }

    private StringBuilder anadirVerifPropiedad(StringBuilder sql, SiNo campo) {
        if (campo != null) {
            sql.append(" and tbl_problema_legalizacion.valor='")
                    .append(campo.getValor())
                    .append("'");
        }
        return sql;
    }

    private StringBuilder anadirCondicionEstructura(StringBuilder sql, SiNo campo) {
        if (campo != null) {
            sql.append(" and tb_estado_estructura.valor='")
                    .append(campo.equals(SiNo.Si) ? "sql.ind.general.critico" : "sql.ind.general.noCritico")
                    .append("'");
        }
        return sql;
    }

    private StringBuilder anadirRutaSenalEvacuacion(StringBuilder sql, SiNo campo1, SiNo campo2) {

        if (campo1 != null) {
            sql.append(" and tb_senalizacion_evacuacion.valor='")
                    .append(campo1.getValor())                    
                    .append("'");
        }
        if (campo2 != null) {
            sql.append(" and condicion_ruta_evacuacion.valor='")
                    .append(campo2.equals(SiNo.Si) ? "sql.ind.general.critico" : "sql.ind.general.noCritico")
                    .append("'");
        }
        return sql;
    }

    private StringBuilder anadirServicioPublico(StringBuilder sql, SiNo tieneServicio) {
        if (tieneServicio != null) {
            sql.append(" and tiene_servicio_pub.valor='")
                    .append(tieneServicio.getValor())                    
                    .append("'");
        }
        return sql;
    }

    private StringBuilder anadirCondicionOferta(StringBuilder sql, FiltroConsultaDinamica filtro) {
        StringBuilder sql_aux = new StringBuilder();
       
        String condicion = "3";
        sql_aux.append(" select * from (");
        sql_aux.append(sql);
        sql_aux.append(" ) tbl where");

        //Si filtran por Posibilidad de construccion primer piso
        if ((Ambitos.A_OFE.toString().equals(filtro.getAmbitoPredominante()) && !UtilCadena.isNullOVacio(filtro.getCampoSinoPred1()))
                || (Ambitos.A_OFE.toString().equals(filtro.getAmbitoCondicional()) && !UtilCadena.isNullOVacio(filtro.getCampoSino1()))) {
            condicion = Ambitos.A_OFE.toString().equals(filtro.getAmbitoPredominante())
                    ? filtro.getCampoSinoPred1().getValor()
                    : filtro.getCampoSino1().getValor();
            //isPredioIdoneo
            sql_aux.append(" (posib_const_primer_piso is not null");
            if (ParamBaseDatos.ORACLE.equals(UtilProperties.getDbMotor())) {
                sql_aux.append(" and nvl(posib_const_primer_piso,0)");
            } else {
                sql_aux.append(" and char_to_double(coalesce(posib_const_primer_piso,'0'))");
            }
           
            sql_aux.append(condicion == SiNo.No.getValor() ? "<=" : ">");
            sql_aux.append("0)");
        }

        //Si filtran por Posibilidad de altura
        if ((Ambitos.A_OFE.toString().equals(filtro.getAmbitoPredominante()) && !UtilCadena.isNullOVacio(filtro.getCampoSinoPred2()))
                || (Ambitos.A_OFE.toString().equals(filtro.getAmbitoCondicional()) && !UtilCadena.isNullOVacio(filtro.getCampoSino2()))) {
            if (!condicion.equals("3")) {
                sql_aux.append(" and");
            }
            condicion = Ambitos.A_OFE.toString().equals(filtro.getAmbitoPredominante())
                    ? filtro.getCampoSinoPred2().getValor()
                    : filtro.getCampoSino2().getValor();
            sql_aux.append(" (posib_const_altura is not null");
            if (ParamBaseDatos.ORACLE.equals(UtilProperties.getDbMotor())) {
                sql_aux.append(" and nvl(posib_const_altura,0)");
            } else {
                sql_aux.append(" and char_to_double(coalesce(posib_const_altura,'0'))");
            }
           
            sql_aux.append(condicion.equals(SiNo.No.getValor()) ? "<=" : ">");
            sql_aux.append("0)");
        }

        //Si filtran por Posibilidad de construccion total
        if ((Ambitos.A_OFE.toString().equals(filtro.getAmbitoPredominante()) && !UtilCadena.isNullOVacio(filtro.getCampoSinoPred3()))
                || (Ambitos.A_OFE.toString().equals(filtro.getAmbitoCondicional()) && !UtilCadena.isNullOVacio(filtro.getCampoSino3()))) {
            if (!condicion.equals("3")) {
                sql_aux.append(" and");
            }
            condicion = Ambitos.A_OFE.toString().equals(filtro.getAmbitoPredominante())
                    ? filtro.getCampoSinoPred3().getValor()
                    : filtro.getCampoSino3().getValor();
            sql_aux.append(" (posib_const_total is not null");
             if (ParamBaseDatos.ORACLE.equals(UtilProperties.getDbMotor())) {
                 sql_aux.append(" and nvl(posib_const_total,0)");
            } else {
                sql_aux.append(" and char_to_double(coalesce(posib_const_total,'0'))");
            }
          
            sql_aux.append(condicion.equals(SiNo.No.getValor()) ? "<=" : ">");
            sql_aux.append("0)");
        }
        return sql_aux;
    }

    private StringBuilder anadirAccesibilidadDentroPredio(StringBuilder sql, SiNo campo) {
        if (campo != null) {
            sql.append(" and tbl_acceso_discapacitado.valor='")
                    .append(campo.getValor())
                    .append("'");
        }
        return sql;
    }

    private StringBuilder anadirEstadEdifEsp(StringBuilder sql, SiNo campo) {
        //CONDICIÓN DE MATERIALIDAD
        //Un edificio en el cual cuando exista el  50% o más de los materiales en condición  uno(1) o dos (2),  se determina como estado CRITICO para los demás casos el estado es NO CRÍTICO
        //Un predio cuando los m2 de los edificios en estado critico/ M2 totales del predio ≥ 50%, se determina como predio CRITICO para los demás casos el predio es NO CRÍTICO
        if (campo != null) {
            sql.append(" and condicion_materialidad.valor='")
                    .append(campo.equals(SiNo.Si) ? "sql.ind.general.critico" : "sql.ind.general.noCritico")
                    .append("'");
        }
        return sql;
    }

    private StringBuilder anadirControlVigilancia(StringBuilder sql, Vulnerabilidad vulnerabilidad) {
        if (vulnerabilidad != null) {
            sql.append(" and tbl_vulnerabilidad.valor='")
                    .append(vulnerabilidad.getNombre())
                    .append("'");
        }
        return sql;
    }
    
    private StringBuilder anadirCumpleAmbiente(StringBuilder sql, SiNo campo) {
        if (campo != null) {            
            sql.append(" and condicion_m2_alumno.valor='")
                    .append(campo.equals(SiNo.Si)?"sql.ind.general.cumple":"sql.ind.general.noCumple")
                    .append("'");
        }
        return sql;
    }
    
    private StringBuilder anadirSostenibilidad(StringBuilder sql, String campo) {
        if (campo != null) {
               sql.append(" and tbl_analisis_consumo_agua.valor = '");
               sql.append(campo).append("'");            
        }
        return sql;
    }
}
