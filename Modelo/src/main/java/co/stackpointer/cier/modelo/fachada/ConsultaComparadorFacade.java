/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.fachada;

import co.stackpointer.cier.modelo.entidad.sistema.Parametro;
import co.stackpointer.cier.modelo.filtro.ambito.FiltroComparadorInd;
import co.stackpointer.cier.modelo.tipo.EspacioFuncional;
import co.stackpointer.cier.modelo.tipo.ParamBaseDatos;
import co.stackpointer.cier.modelo.tipo.ParamIndicador;
import co.stackpointer.cier.modelo.tipo.ParamNivelConsul;
import co.stackpointer.cier.modelo.tipo.ParamSistema;
import co.stackpointer.cier.modelo.utilidad.UtilProperties;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;

/**
 *
 * @author nruiz
 */
@Stateless
public class ConsultaComparadorFacade implements ConsultaComparadorFacadeLocal {

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
    public List<Object[]> consultarGeneralidades(FiltroComparadorInd filtro) {

        StringBuilder sql = new StringBuilder();

// consulta por nivel de agrupamiento superior a predio
// SELECT
        sql.append(" select distinct");
        if (filtro.isAgrupamientoPredio()) {
            //sql.append(" n.nombre_nivel1, n.nombre_nivel2, n.nombre_nivel3, n.nombre_nivel4, n.nombre_nivel5");
            sql.append(" n.codigo_nivel1,n.nombre_nivel1");
            sql.append(",n.codigo_nivel2,n.nombre_nivel2");
            sql.append(",n.codigo_nivel3,n.nombre_nivel3");
            sql.append(",n.codigo_nivel4,n.nombre_nivel4");
            sql.append(",n.codigo_nivel5,n.nombre_nivel5");
            
            sql.append(" ,n.codigo_establecimiento, n.nombre_establecimiento");
            sql.append(" ,n.codigo_sede, n.nombre_sede");
            sql.append(" ,n.codigo_predio, n.nombre_predio");
            //otros
            sql.append(" ,n.direccion_predio, n.nombre_zona");
            sql.append(" ,n.nombre_sector, n.nombre_clima");
            //etnias
            sql.append(" ,etnias.valor");
            // numero de jornadas educativas por predio
            sql.append(" ,num_jor.valor");
            sql.append(" ,percomp_num_jor.valor");
            //total de estudiantes por predio  
            sql.append(" ,tot_estud.valor");
            sql.append(" ,percomp_tot_estud.valor");
            //total area construccion por predio
            sql.append(" ,tot_area.valor");
            sql.append(" ,percomp_tot_area.valor");
            //area real del predio
            sql.append(" ,area_predio.valor");
            sql.append(" ,percomp_area_predio.valor");
        } else {
            if (filtro.isAgrupamientoNivel0()) {
                sql.append(" n.nombre_nivel0");
            }
            if (filtro.isAgrupamientoNivel1()) {
                //sql.append(" n.nombre_nivel1");
                sql.append(" n.codigo_nivel1,n.nombre_nivel1");
            }
            if (filtro.isAgrupamientoNivel2()) {
                //sql.append(" n.nombre_nivel1, n.nombre_nivel2");
                sql.append(" n.codigo_nivel1,n.nombre_nivel1");
                sql.append(",n.codigo_nivel2,n.nombre_nivel2");
            }
            if (filtro.isAgrupamientoNivel3()) {
                //sql.append(" n.nombre_nivel1, n.nombre_nivel2, n.nombre_nivel3");
                sql.append(" n.codigo_nivel1,n.nombre_nivel1");
                sql.append(",n.codigo_nivel2,n.nombre_nivel2");
                sql.append(",n.codigo_nivel3,n.nombre_nivel3");
            }
            if (filtro.isAgrupamientoNivel4()) {
                //sql.append(" n.nombre_nivel1, n.nombre_nivel2, n.nombre_nivel3, n.nombre_nivel4");
                sql.append(" n.codigo_nivel1,n.nombre_nivel1");
                sql.append(",n.codigo_nivel2,n.nombre_nivel2");
                sql.append(",n.codigo_nivel3,n.nombre_nivel3");
                sql.append(",n.codigo_nivel4,n.nombre_nivel4");
            }
            if (filtro.isAgrupamientoNivel5()) {
                //sql.append(" n.nombre_nivel1, n.nombre_nivel2, n.nombre_nivel3, n.nombre_nivel4, n.nombre_nivel5");
                sql.append(" n.codigo_nivel1,n.nombre_nivel1");
                sql.append(",n.codigo_nivel2,n.nombre_nivel2");
                sql.append(",n.codigo_nivel3,n.nombre_nivel3");
                sql.append(",n.codigo_nivel4,n.nombre_nivel4");
                sql.append(",n.codigo_nivel5,n.nombre_nivel5");
            }
            if (filtro.isAgrupamientoEstablecimiento()) {
                //sql.append(" n.nombre_nivel1, n.nombre_nivel2, n.nombre_nivel3, n.nombre_nivel4, n.nombre_nivel5");
                sql.append(" n.codigo_nivel1,n.nombre_nivel1");
                sql.append(",n.codigo_nivel2,n.nombre_nivel2");
                sql.append(",n.codigo_nivel3,n.nombre_nivel3");
                sql.append(",n.codigo_nivel4,n.nombre_nivel4");
                sql.append(",n.codigo_nivel5,n.nombre_nivel5");
                sql.append(" ,n.codigo_establecimiento, n.nombre_establecimiento");
            }
            if (filtro.isAgrupamientoSede()) {
                //sql.append(" n.nombre_nivel1, n.nombre_nivel2, n.nombre_nivel3, n.nombre_nivel4, n.nombre_nivel5");
                sql.append(" n.codigo_nivel1,n.nombre_nivel1");
                sql.append(",n.codigo_nivel2,n.nombre_nivel2");
                sql.append(",n.codigo_nivel3,n.nombre_nivel3");
                sql.append(",n.codigo_nivel4,n.nombre_nivel4");
                sql.append(",n.codigo_nivel5,n.nombre_nivel5");
                sql.append(" ,n.codigo_establecimiento, n.nombre_establecimiento");
                sql.append(" ,n.codigo_sede, n.nombre_sede");
            }
            // total de estudiantes por predio
            sql.append(" ,tot_estud.valor");
            sql.append(" ,percomp_tot_estud.valor");
            if (!filtro.isAgrupamientoSede()) {
                //numero de establecimientos   
                if (!filtro.isAgrupamientoEstablecimiento()) {
                    sql.append(" ,tot_estab.valor");
                    sql.append(" ,percomp_tot_estab.valor");
                }
                // numero de sedes
                sql.append(" ,tot_sede.valor");
                sql.append(" ,percomp_tot_sede.valor");
            }
            //numero de predios
            sql.append(" ,tot_predio.valor");
            sql.append(" ,percomp_tot_predio.valor");
        }
// FROM 
        sql.append(" from ind_auxiliar_indicador aux");
        sql.append(" inner join ind_auxiliar_consultas n on n.id_predio = aux.id_predio and n.periodo = aux.periodo and n.id_tnt = aux.id_tnt");

        String strSQLNivelAgrupamiento = this.getSQLNivelAgrupamiento(filtro);
        String strSQLConsultarIndicador = this.getSQLConsultarIndicadorAlmacenado();
        //total de estudiantes por predio  
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) tot_estud on tot_estud.id_tnt = aux.id_tnt and tot_estud.periodo = aux.periodo");
        sql.append(String.format(" and tot_estud.cod_indicador = '%s'", ParamIndicador.IND_TOTAL_ESTUDIANTES));
        sql.append(" and tot_estud.nivel_agrupamiento = ?nivel_agrupamiento");
        sql.append(" and tot_estud.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) percomp_tot_estud on percomp_tot_estud.id_tnt = aux.id_tnt");
        sql.append(String.format(" and percomp_tot_estud.cod_indicador = '%s'", ParamIndicador.IND_TOTAL_ESTUDIANTES));
        sql.append(" and percomp_tot_estud.nivel_agrupamiento = ?nivel_agrupamiento");
        sql.append(" and percomp_tot_estud.periodo = ?periodo_comparacion");
        sql.append(" and percomp_tot_estud.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
        
        if (filtro.isAgrupamientoPredio()) {
            // numero de jornadas educativas por predio
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) num_jor on num_jor.id_tnt = aux.id_tnt and num_jor.periodo = aux.periodo");
            sql.append(String.format(" and num_jor.cod_indicador = '%s'", ParamIndicador.IND_NUMERO_JORNADAS_PREDIO));
            sql.append(" and num_jor.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and num_jor.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);            
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) percomp_num_jor on percomp_num_jor.id_tnt = aux.id_tnt");
            sql.append(String.format(" and percomp_num_jor.cod_indicador = '%s'", ParamIndicador.IND_NUMERO_JORNADAS_PREDIO));
            sql.append(" and percomp_num_jor.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and percomp_num_jor.periodo = ?periodo_comparacion");
            sql.append(" and percomp_num_jor.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
            
            //total area construccion por predio            
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) tot_area on tot_area.id_tnt = aux.id_tnt and tot_area.periodo = aux.periodo");
            sql.append(String.format(" and tot_area.cod_indicador = '%s'", ParamIndicador.IND_TOTAL_AREA_CONSTRUCCION_PREDIO));
            sql.append(" and tot_area.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and tot_area.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) percomp_tot_area on percomp_tot_area.id_tnt = aux.id_tnt");
            sql.append(String.format(" and percomp_tot_area.cod_indicador = '%s'", ParamIndicador.IND_TOTAL_AREA_CONSTRUCCION_PREDIO));
            sql.append(" and percomp_tot_area.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and percomp_tot_area.periodo = ?periodo_comparacion");
            sql.append(" and percomp_tot_area.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
            
            //area real del predio          
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) area_predio on area_predio.id_tnt = aux.id_tnt and area_predio.periodo = aux.periodo");
            sql.append(String.format(" and area_predio.cod_indicador = '%s'", ParamIndicador.IND_AREA_REAL_PREDIO));
            sql.append(" and area_predio.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and area_predio.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) percomp_area_predio on percomp_area_predio.id_tnt = aux.id_tnt");
            sql.append(String.format(" and percomp_area_predio.cod_indicador = '%s'", ParamIndicador.IND_AREA_REAL_PREDIO));
            sql.append(" and percomp_area_predio.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and percomp_area_predio.periodo = ?periodo_comparacion");
            sql.append(" and percomp_area_predio.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
            
            //etnias del predio
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) etnias on etnias.id_tnt = aux.id_tnt and etnias.periodo = aux.periodo");
            sql.append(String.format(" and etnias.cod_indicador = '%s'", ParamIndicador.IND_ETNIAS_PREDIO));
            sql.append(" and etnias.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and etnias.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
        } else {
            if (!filtro.isAgrupamientoSede()) {
                if (!filtro.isAgrupamientoEstablecimiento()) {
                    //numero de establecimientos    
                    sql.append(" left join (");
                    sql.append(strSQLConsultarIndicador);
                    sql.append(" ) tot_estab on tot_estab.id_tnt = aux.id_tnt and tot_estab.periodo = aux.periodo");
                    sql.append(String.format(" and tot_estab.cod_indicador = '%s'", ParamIndicador.IND_TOTAL_ESTABLECIMIENTOS));
                    sql.append(" and tot_estab.nivel_agrupamiento = ?nivel_agrupamiento");
                    sql.append(" and tot_estab.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);                    
                    sql.append(" left join (");
                    sql.append(strSQLConsultarIndicador);
                    sql.append(" ) percomp_tot_estab on percomp_tot_estab.id_tnt = aux.id_tnt");
                    sql.append(String.format(" and percomp_tot_estab.cod_indicador = '%s'", ParamIndicador.IND_TOTAL_ESTABLECIMIENTOS));
                    sql.append(" and percomp_tot_estab.nivel_agrupamiento = ?nivel_agrupamiento");
                    sql.append(" and percomp_tot_estab.periodo = ?periodo_comparacion");
                    sql.append(" and percomp_tot_estab.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
                }
                // numero de sedes
                sql.append(" left join (");
                sql.append(strSQLConsultarIndicador);
                sql.append(" ) tot_sede on tot_sede.id_tnt = aux.id_tnt and tot_sede.periodo = aux.periodo");
                sql.append(String.format(" and tot_sede.cod_indicador = '%s'", ParamIndicador.IND_TOTAL_SEDES));
                sql.append(" and tot_sede.nivel_agrupamiento = ?nivel_agrupamiento");
                sql.append(" and tot_sede.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);                
                sql.append(" left join (");
                sql.append(strSQLConsultarIndicador);
                sql.append(" ) percomp_tot_sede on percomp_tot_sede.id_tnt = aux.id_tnt");
                sql.append(String.format(" and percomp_tot_sede.cod_indicador = '%s'", ParamIndicador.IND_TOTAL_SEDES));
                sql.append(" and percomp_tot_sede.nivel_agrupamiento = ?nivel_agrupamiento");
                sql.append(" and percomp_tot_sede.periodo = ?periodo_comparacion");
                sql.append(" and percomp_tot_sede.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
            }
            //numero de predios
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) tot_predio on tot_predio.id_tnt = aux.id_tnt and tot_predio.periodo = aux.periodo");
            sql.append(String.format(" and tot_predio.cod_indicador = '%s'", ParamIndicador.IND_TOTAL_PREDIOS));
            sql.append(" and tot_predio.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and tot_predio.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) percomp_tot_predio on tot_predio.id_tnt = aux.id_tnt");
            sql.append(String.format(" and percomp_tot_predio.cod_indicador = '%s'", ParamIndicador.IND_TOTAL_PREDIOS));
            sql.append(" and percomp_tot_predio.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and percomp_tot_predio.periodo = ?periodo_comparacion");
            sql.append(" and percomp_tot_predio.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
        }
// WHERE
        sql.append(" where aux.id_tnt = ?id_tnt and aux.periodo = ?periodo");
        if (filtro.isAgrupamientoPredio()) {
            sql.append(" and n.principal_establecimiento = 1");
        }
        if (filtro.getIdNivel0() != null) {
            sql.append(" and n.id_nivel0 = ?id_nivel0");
        }
        if (filtro.getIdNivel1() != null) {
            sql.append(" and n.id_nivel1 = ?id_nivel1");
        }
        if (filtro.getIdNivel2() != null) {
            sql.append(" and n.id_nivel2 = ?id_nivel2");
        }
        if (filtro.getIdNivel3() != null) {
            sql.append(" and n.id_nivel3 = ?id_nivel3");
        }
        if (filtro.getIdNivel4() != null) {
            sql.append(" and n.id_nivel4 = ?id_nivel4");
        }
        if (filtro.getIdNivel5() != null) {
            sql.append(" and n.id_nivel5 = ?id_nivel5");
        }
        if (filtro.getIdEstablecimiento() != null) {
            sql.append(" and n.id_establecimiento = ?id_establecimiento");
        }
        if (filtro.getIdSede() != null) {
            sql.append(" and n.id_sede = ?id_sede");
        }
        if (filtro.getIdPredio() != null) {
            sql.append(" and n.id_predio = ?id_predio");
        }

        Query query = tpm.getEm().createNativeQuery(sql.toString());
        query.setParameter("id_tnt", tpm.getCurrentTenant());
        query.setParameter("periodo", filtro.getPeriodo());
        query.setParameter("idioma", filtro.getIdioma());
        query.setParameter("id_nivel0", filtro.getIdNivel0());
        query.setParameter("nivel_agrupamiento", filtro.getAgrupamiento());
        query.setParameter("periodo_comparacion", filtro.getPeriodoComparado());
        if (filtro.getIdNivel1() != null) {
            query.setParameter("id_nivel1", filtro.getIdNivel1());
        }
        if (filtro.getIdNivel2() != null) {
            query.setParameter("id_nivel2", filtro.getIdNivel2());
        }
        if (filtro.getIdNivel3() != null) {
            query.setParameter("id_nivel3", filtro.getIdNivel3());
        }
        if (filtro.getIdNivel4() != null) {
            query.setParameter("id_nivel4", filtro.getIdNivel4());
        }
        if (filtro.getIdNivel5() != null) {
            query.setParameter("id_nivel5", filtro.getIdNivel5());
        }
        if (filtro.getIdEstablecimiento() != null) {
            query.setParameter("id_establecimiento", filtro.getIdEstablecimiento());
        }
        if (filtro.getIdSede() != null) {
            query.setParameter("id_sede", filtro.getIdSede());
        }
        if (filtro.getIdPredio() != null) {
            query.setParameter("id_predio", filtro.getIdPredio());
        }
        return query.getResultList();
    }

    @Override
    public List<Object[]> consultarRedistribucion(FiltroComparadorInd filtro) {
         if(ParamBaseDatos.ORACLE.equals(UtilProperties.getDbMotor())){
                return this.consultarRedistribucionIndicadoresOracle(filtro);
             }else{
                return this.consultarRedistribucionIndicadoresPostgre(filtro);    
             }
    }

    @Override
    public List<Object[]> consultarAmbientes(FiltroComparadorInd filtro) {
        StringBuilder sql = new StringBuilder();
        StringBuilder agrupamiento = new StringBuilder();
//SELECT
        sql.append(" select distinct");
        if (filtro.isAgrupamientoPredio()) {
            //sql.append(" n.nombre_nivel1, n.nombre_nivel2, n.nombre_nivel3, n.nombre_nivel4, n.nombre_nivel5");
            sql.append(" n.codigo_nivel1,n.nombre_nivel1");
            sql.append(",n.codigo_nivel2,n.nombre_nivel2");
            sql.append(",n.codigo_nivel3,n.nombre_nivel3");
            sql.append(",n.codigo_nivel4,n.nombre_nivel4");
            sql.append(",n.codigo_nivel5,n.nombre_nivel5");
            
            sql.append(", n.codigo_establecimiento, n.nombre_establecimiento");
            sql.append(", n.codigo_sede, n.nombre_sede");
            sql.append(", n.codigo_predio, n.nombre_predio");

            // *indicadores            
            // Proporción espacios
            sql.append(" ,to_number(coalesce(tbl_tipo_espacio.valor,'0'),'9999999')/to_number(case when tbl_total_espacio.valor = '0' then '1' else tbl_total_espacio.valor end ,'9999999') proporcion_espacios");
            sql.append(" ,to_number(coalesce(percomp_tbl_tipo_espacio.valor,'0'),'9999999')/to_number(case when percomp_tbl_total_espacio.valor = '0' then '1' else percomp_tbl_total_espacio.valor end ,'9999999') proporcion_espacios");
            // m2/alumno (relacion de m2 por estudiante)
            sql.append(" ,m2_alumno.valor"); 
            sql.append(" ,percomp_m2_alumno.valor");
            // condicion de m2/alumno (condicion relacion de m2 por estudiante)
            sql.append(" ,condicion_m2_alumno.valor");
            sql.append(" ,percomp_condicion_m2_alumno.valor");
            // deficit_estandar
            sql.append(" ,deficit_estandar.valor");
            sql.append(" ,percomp_deficit_estandar.valor");
            // cumplimiento_norma
            sql.append(" ,cumplimiento_norma.valor");
            sql.append(" ,percomp_cumplimiento_norma.valor");

            //*otros
            sql.append(" , n.direccion_predio direccion_predio, n.nombre_zona zona");
            sql.append(" , n.nombre_sector sector, n.nombre_clima clima");
            
        } else {
            if (filtro.isAgrupamientoEstablecimiento() || filtro.isAgrupamientoSede()) {
                //agrupamiento.append(" n.nombre_nivel1, n.nombre_nivel2, n.nombre_nivel3, n.nombre_nivel4, n.nombre_nivel5");
                agrupamiento.append(" n.codigo_nivel1,n.nombre_nivel1");
                agrupamiento.append(",n.codigo_nivel2,n.nombre_nivel2");
                agrupamiento.append(",n.codigo_nivel3,n.nombre_nivel3");
                agrupamiento.append(",n.codigo_nivel4,n.nombre_nivel4");
                agrupamiento.append(",n.codigo_nivel5,n.nombre_nivel5");
                
                agrupamiento.append(", n.codigo_establecimiento, n.nombre_establecimiento");
                if (filtro.isAgrupamientoSede()) {
                    agrupamiento.append(", n.codigo_sede, n.nombre_sede");
                }

                // select
                sql.append(agrupamiento.toString());                
                // Proporción espacios
                sql.append(" ,to_number(coalesce(tbl_tipo_espacio.valor,'0'),'9999999')/to_number(case when tbl_total_espacio.valor is null or tbl_total_espacio.valor = '0' then '1' else tbl_total_espacio.valor end ,'9999999') proporcion_espacios ");
                sql.append(" ,to_number(coalesce(percomp_tbl_tipo_espacio.valor,'0'),'9999999')/to_number(case when percomp_tbl_total_espacio.valor is null or percomp_tbl_total_espacio.valor = '0' then '1' else percomp_tbl_total_espacio.valor end ,'9999999') ");
                
                if (filtro.getEspacioFuncional().equals(EspacioFuncional.BIBLIOTECA)) {
                    // m2/alumno (relacion de m2 por estudiante)
                    sql.append(" ,m2_alumno.valor"); 
                    sql.append(" ,percomp_m2_alumno.valor"); 
                    // condicion de m2/alumno (condicion relacion de m2 por estudiante)
                    sql.append(" ,condicion_m2_alumno.valor");
                    sql.append(" ,percomp_condicion_m2_alumno.valor");
                    // deficit_estandar
                    sql.append(" ,deficit_estandar.valor");
                    sql.append(" ,percomp_deficit_estandar.valor");
                    // cumplimiento_norma
                    sql.append(" ,cumplimiento_norma.valor");
                    sql.append(" ,percomp_cumplimiento_norma.valor");
                } else {                    
                    // Proporcion predios
                    sql.append(" ,to_number(coalesce(predios_no_idoneos.valor,'0'),'9999999')/to_number(case when total_predio.valor = '0' then '1' else total_predio.valor end ,'9999999') proporcion_predios");
                    sql.append(" ,to_number(coalesce(percomp_predios_no_idoneos.valor,'0'),'9999999')/to_number(case when percomp_total_predio.valor = '0' then '1' else percomp_total_predio.valor end ,'9999999')");
                    
                    // Acumulado deficit
                    sql.append(" ,deficit_estandar.valor");
                    sql.append(" ,percomp_deficit_estandar.valor");
                    // Cumplimiento Norma 
                    sql.append(" ,cumplimiento_norma.valor");
                    sql.append(" ,percomp_cumplimiento_norma.valor");
                }
            } else {
                if (filtro.isAgrupamientoNivel0()) {
                    agrupamiento.append(" n.nombre_nivel0");
                }
                if (filtro.isAgrupamientoNivel1()) {
                    //agrupamiento.append(" n.nombre_nivel1");
                    agrupamiento.append(" n.codigo_nivel1,n.nombre_nivel1");
                }
                if (filtro.isAgrupamientoNivel2()) {
                    //agrupamiento.append(" n.nombre_nivel1, n.nombre_nivel2");
                    agrupamiento.append(" n.codigo_nivel1,n.nombre_nivel1");
                    agrupamiento.append(",n.codigo_nivel2,n.nombre_nivel2");
                   
                }
                if (filtro.isAgrupamientoNivel3()) {
                    //agrupamiento.append(" n.nombre_nivel1, n.nombre_nivel2, n.nombre_nivel3");
                    agrupamiento.append(" n.codigo_nivel1,n.nombre_nivel1");
                    agrupamiento.append(",n.codigo_nivel2,n.nombre_nivel2");
                    agrupamiento.append(",n.codigo_nivel3,n.nombre_nivel3");
                }
                if (filtro.isAgrupamientoNivel4()) {
                    //agrupamiento.append(" n.nombre_nivel1, n.nombre_nivel2, n.nombre_nivel3, n.nombre_nivel4");
                    agrupamiento.append(" n.codigo_nivel1,n.nombre_nivel1");
                    agrupamiento.append(",n.codigo_nivel2,n.nombre_nivel2");
                    agrupamiento.append(",n.codigo_nivel3,n.nombre_nivel3");
                    agrupamiento.append(",n.codigo_nivel4,n.nombre_nivel4");
                }
                if (filtro.isAgrupamientoNivel5()) {
                    //agrupamiento.append(" n.nombre_nivel1, n.nombre_nivel2, n.nombre_nivel3, n.nombre_nivel4, n.nombre_nivel5");
                    agrupamiento.append(" n.codigo_nivel1,n.nombre_nivel1");
                    agrupamiento.append(",n.codigo_nivel2,n.nombre_nivel2");
                    agrupamiento.append(",n.codigo_nivel3,n.nombre_nivel3");
                    agrupamiento.append(",n.codigo_nivel4,n.nombre_nivel4");
                    agrupamiento.append(",n.codigo_nivel5,n.nombre_nivel5");
                }

                // select
                sql.append(agrupamiento.toString());
                
                // Proporcion Establecimiento
                sql.append(" ,to_number(coalesce(est_no_idoneos.valor,'0'),'9999999')/to_number(case when total_establecimientos.valor = '0' then '1' else total_establecimientos.valor end ,'9999999') porcion_establecimientos");
                sql.append(" ,to_number(coalesce(percomp_est_no_idoneos.valor,'0'),'9999999')/to_number(case when percomp_total_establecimientos.valor = '0' then '1' else percomp_total_establecimientos.valor end ,'9999999') ");
                if (!filtro.getEspacioFuncional().equals(EspacioFuncional.BIBLIOTECA)) {                    
                    // Proporcion predios
                    sql.append(" ,to_number(coalesce(predios_no_idoneos.valor,'0'),'9999999')/to_number(case when total_predio.valor = '0' then '1' else total_predio.valor end ,'9999999') proporcion_predios");
                    sql.append(" ,to_number(coalesce(percomp_predios_no_idoneos.valor,'0'),'9999999')/to_number(case when percomp_total_predio.valor = '0' then '1' else percomp_total_predio.valor end ,'9999999') ");
                }
                //Acumulado Deficit
                sql.append(" ,deficit_estandar.valor");
                sql.append(" ,percomp_deficit_estandar.valor");
                //Cumplimiento de Norma
                sql.append(" ,cumplimiento_norma.valor");
                sql.append(" ,percomp_cumplimiento_norma.valor");
            }
        }
// FROM 
        sql.append(" from ind_auxiliar_indicador aux");
        sql.append(" inner join ind_auxiliar_consultas n on n.id_predio = aux.id_predio and n.periodo = aux.periodo and n.id_tnt = aux.id_tnt");

        String strSQLNivelAgrupamiento = this.getSQLNivelAgrupamiento(filtro);
        String strSQLConsultarIndicador = this.getSQLConsultarIndicadorAlmacenado();

        if (filtro.isAgrupamientoPredio()) {
            //tipo de espacios
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) tbl_tipo_espacio on tbl_tipo_espacio.id_tnt = aux.id_tnt and tbl_tipo_espacio.periodo = aux.periodo");
            sql.append(String.format(" and tbl_tipo_espacio.cod_indicador = '%s'", ParamIndicador.IND_AMBIENTE_ESPACIOS_TIPO));
            sql.append(" and tbl_tipo_espacio.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and tbl_tipo_espacio.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
            sql.append(" and tbl_tipo_espacio.cod_valor = ?unidad_funcional");
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) percomp_tbl_tipo_espacio on percomp_tbl_tipo_espacio.id_tnt = aux.id_tnt");
            sql.append(String.format(" and percomp_tbl_tipo_espacio.cod_indicador = '%s'", ParamIndicador.IND_AMBIENTE_ESPACIOS_TIPO));
            sql.append(" and percomp_tbl_tipo_espacio.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and percomp_tbl_tipo_espacio.periodo = ?periodo_comparacion");
            sql.append(" and percomp_tbl_tipo_espacio.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
            sql.append(" and percomp_tbl_tipo_espacio.cod_valor = ?unidad_funcional");

            //total de espacios
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) tbl_total_espacio on tbl_total_espacio.id_tnt = aux.id_tnt and tbl_total_espacio.periodo = aux.periodo");
            sql.append(String.format(" and tbl_total_espacio.cod_indicador = '%s'", ParamIndicador.IND_TOTAL_ESPACIOS));
            sql.append(" and tbl_total_espacio.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and tbl_total_espacio.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento); 
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) percomp_tbl_total_espacio on percomp_tbl_total_espacio.id_tnt = aux.id_tnt");
            sql.append(String.format(" and percomp_tbl_total_espacio.cod_indicador = '%s'", ParamIndicador.IND_TOTAL_ESPACIOS));
            sql.append(" and percomp_tbl_total_espacio.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and percomp_tbl_total_espacio.periodo = ?periodo_comparacion");
            sql.append(" and percomp_tbl_total_espacio.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);

            // m2/alumno (relacion de m2 por estudiante)
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) m2_alumno on m2_alumno.id_tnt = aux.id_tnt and m2_alumno.periodo = aux.periodo");
            sql.append(String.format(" and m2_alumno.cod_indicador = '%s'", ParamIndicador.IND_AMBIENTE_M2_ALUMNO));
            sql.append(" and m2_alumno.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and m2_alumno.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
            sql.append(" and m2_alumno.cod_valor = ?unidad_funcional");
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) percomp_m2_alumno on percomp_m2_alumno.id_tnt = aux.id_tnt and percomp_m2_alumno.periodo = ?periodo_comparacion");
            sql.append(String.format(" and percomp_m2_alumno.cod_indicador = '%s'", ParamIndicador.IND_AMBIENTE_M2_ALUMNO));
            sql.append(" and percomp_m2_alumno.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and percomp_m2_alumno.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
            sql.append(" and percomp_m2_alumno.cod_valor = ?unidad_funcional");

            // condicion de m2/alumno (condicion relacion de m2 por estudiante)
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) condicion_m2_alumno on condicion_m2_alumno.id_tnt = aux.id_tnt and condicion_m2_alumno.periodo = aux.periodo");
            sql.append(String.format(" and condicion_m2_alumno.cod_indicador = '%s'", ParamIndicador.IND_AMBIENTE_CONDICION_M2_ALUMNO));
            sql.append(" and condicion_m2_alumno.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and condicion_m2_alumno.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
            sql.append(" and condicion_m2_alumno.cod_valor = ?unidad_funcional");
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) percomp_condicion_m2_alumno on percomp_condicion_m2_alumno.id_tnt = aux.id_tnt and percomp_condicion_m2_alumno.periodo = ?periodo_comparacion");
            sql.append(String.format(" and percomp_condicion_m2_alumno.cod_indicador = '%s'", ParamIndicador.IND_AMBIENTE_CONDICION_M2_ALUMNO));
            sql.append(" and percomp_condicion_m2_alumno.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and percomp_condicion_m2_alumno.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
            sql.append(" and percomp_condicion_m2_alumno.cod_valor = ?unidad_funcional");

        } else {
            if (filtro.isAgrupamientoEstablecimiento() || filtro.isAgrupamientoSede()) {
                // #  espacios por tipo
                sql.append(" left join (");
                sql.append(strSQLConsultarIndicador);
                sql.append(" ) tbl_tipo_espacio on tbl_tipo_espacio.id_tnt = aux.id_tnt and tbl_tipo_espacio.periodo = aux.periodo");
                sql.append(String.format(" and tbl_tipo_espacio.cod_indicador = '%s'", ParamIndicador.IND_AMBIENTE_ESPACIOS_TIPO));
                sql.append(" and tbl_tipo_espacio.nivel_agrupamiento = ?nivel_agrupamiento");
                sql.append(" and tbl_tipo_espacio.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
                sql.append(" and tbl_tipo_espacio.cod_valor = ?unidad_funcional");
                sql.append(" left join (");
                sql.append(strSQLConsultarIndicador);
                sql.append(" ) percomp_tbl_tipo_espacio on percomp_tbl_tipo_espacio.id_tnt = aux.id_tnt and percomp_tbl_tipo_espacio.periodo = ?periodo_comparacion");
                sql.append(String.format(" and percomp_tbl_tipo_espacio.cod_indicador = '%s'", ParamIndicador.IND_AMBIENTE_ESPACIOS_TIPO));
                sql.append(" and percomp_tbl_tipo_espacio.nivel_agrupamiento = ?nivel_agrupamiento");
                sql.append(" and percomp_tbl_tipo_espacio.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
                sql.append(" and percomp_tbl_tipo_espacio.cod_valor = ?unidad_funcional");

                // total espacios
                sql.append(" left join (");
                sql.append(strSQLConsultarIndicador);
                sql.append(" ) tbl_total_espacio on tbl_total_espacio.id_tnt = aux.id_tnt and tbl_total_espacio.periodo = aux.periodo");
                sql.append(String.format(" and tbl_total_espacio.cod_indicador = '%s'", ParamIndicador.IND_TOTAL_ESPACIOS));
                sql.append(" and tbl_total_espacio.nivel_agrupamiento = ?nivel_agrupamiento");
                sql.append(" and tbl_total_espacio.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
                 sql.append(" left join (");
                sql.append(strSQLConsultarIndicador);
                sql.append(" ) percomp_tbl_total_espacio on percomp_tbl_total_espacio.id_tnt = aux.id_tnt and percomp_tbl_total_espacio.periodo = ?periodo_comparacion");
                sql.append(String.format(" and percomp_tbl_total_espacio.cod_indicador = '%s'", ParamIndicador.IND_TOTAL_ESPACIOS));
                sql.append(" and percomp_tbl_total_espacio.nivel_agrupamiento = ?nivel_agrupamiento");
                sql.append(" and percomp_tbl_total_espacio.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);

                if (filtro.getEspacioFuncional().equals(EspacioFuncional.BIBLIOTECA)) {
                    // m2/alumno (relacion de m2 por estudiante)
                    sql.append(" left join (");
                    sql.append(strSQLConsultarIndicador);
                    sql.append(" ) m2_alumno on m2_alumno.id_tnt = aux.id_tnt and m2_alumno.periodo = aux.periodo");
                    sql.append(String.format(" and m2_alumno.cod_indicador = '%s'", ParamIndicador.IND_AMBIENTE_BIBLIOTECA_M2_ALUMNO));
                    sql.append(" and m2_alumno.nivel_agrupamiento = ?nivel_agrupamiento");
                    sql.append(" and m2_alumno.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
                    sql.append(" left join (");
                    sql.append(strSQLConsultarIndicador);
                    sql.append(" ) percomp_m2_alumno on percomp_m2_alumno.id_tnt = aux.id_tnt and percomp_m2_alumno.periodo = ?periodo_comparacion");
                    sql.append(String.format(" and percomp_m2_alumno.cod_indicador = '%s'", ParamIndicador.IND_AMBIENTE_BIBLIOTECA_M2_ALUMNO));
                    sql.append(" and percomp_m2_alumno.nivel_agrupamiento = ?nivel_agrupamiento");
                    sql.append(" and percomp_m2_alumno.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);

                    // condicion de m2/alumno (condicion relacion de m2 por estudiante)
                    sql.append(" left join (");
                    sql.append(strSQLConsultarIndicador);
                    sql.append(" ) condicion_m2_alumno on condicion_m2_alumno.id_tnt = aux.id_tnt and condicion_m2_alumno.periodo = aux.periodo");
                    sql.append(String.format(" and condicion_m2_alumno.cod_indicador = '%s'", ParamIndicador.IND_AMBIENTE_BIBLIOTECA_CONDICION_M2_ALUMNO));
                    sql.append(" and condicion_m2_alumno.nivel_agrupamiento = ?nivel_agrupamiento");
                    sql.append(" and condicion_m2_alumno.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
                    sql.append(" left join (");
                    sql.append(strSQLConsultarIndicador);
                    sql.append(" ) percomp_condicion_m2_alumno on percomp_condicion_m2_alumno.id_tnt = aux.id_tnt and percomp_condicion_m2_alumno.periodo = ?periodo_comparacion");
                    sql.append(String.format(" and percomp_condicion_m2_alumno.cod_indicador = '%s'", ParamIndicador.IND_AMBIENTE_BIBLIOTECA_CONDICION_M2_ALUMNO));
                    sql.append(" and percomp_condicion_m2_alumno.nivel_agrupamiento = ?nivel_agrupamiento");
                    sql.append(" and percomp_condicion_m2_alumno.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
                } else {
                    // Numero de Predios no idoneos
                    if (filtro.getEspacioFuncional().equals(EspacioFuncional.EXPANSIONES_RECREATIVAS)) {
                        sql.append(" left join (");
                        sql.append(strSQLConsultarIndicador);
                        sql.append(" ) predios_no_idoneos on predios_no_idoneos.id_tnt = aux.id_tnt and predios_no_idoneos.periodo = aux.periodo");
                        sql.append(String.format(" and predios_no_idoneos.cod_indicador = '%s'", ParamIndicador.IND_AMBIENTE_EXPANSIONES_RECREATIVAS_NUMERO_PREDIOS_NO_IDONEOS));
                        sql.append(" and predios_no_idoneos.nivel_agrupamiento = ?nivel_agrupamiento");
                        sql.append(" and predios_no_idoneos.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
                        sql.append(" left join (");
                        sql.append(strSQLConsultarIndicador);
                        sql.append(" ) percomp_predios_no_idoneos on percomp_predios_no_idoneos.id_tnt = aux.id_tnt and percomp_predios_no_idoneos.periodo = ?periodo_comparacion");
                        sql.append(String.format(" and percomp_predios_no_idoneos.cod_indicador = '%s'", ParamIndicador.IND_AMBIENTE_EXPANSIONES_RECREATIVAS_NUMERO_PREDIOS_NO_IDONEOS));
                        sql.append(" and percomp_predios_no_idoneos.nivel_agrupamiento = ?nivel_agrupamiento");
                        sql.append(" and percomp_predios_no_idoneos.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
                        
                    } else {
                        sql.append(" left join (");
                        sql.append(strSQLConsultarIndicador);
                        sql.append(" ) predios_no_idoneos on predios_no_idoneos.id_tnt = aux.id_tnt and predios_no_idoneos.periodo = aux.periodo");
                        sql.append(String.format(" and predios_no_idoneos.cod_indicador = '%s'", ParamIndicador.IND_AMBIENTE_NUMERO_PREDIOS_NO_IDONEOS));
                        sql.append(" and predios_no_idoneos.nivel_agrupamiento = ?nivel_agrupamiento");
                        sql.append(" and predios_no_idoneos.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
                        sql.append(" and predios_no_idoneos.cod_valor = ?unidad_funcional");
                        sql.append(" left join (");
                        sql.append(strSQLConsultarIndicador);
                        sql.append(" ) percomp_predios_no_idoneos on percomp_predios_no_idoneos.id_tnt = aux.id_tnt and percomp_predios_no_idoneos.periodo = ?periodo_comparacion");
                        sql.append(String.format(" and percomp_predios_no_idoneos.cod_indicador = '%s'", ParamIndicador.IND_AMBIENTE_NUMERO_PREDIOS_NO_IDONEOS));
                        sql.append(" and percomp_predios_no_idoneos.nivel_agrupamiento = ?nivel_agrupamiento");
                        sql.append(" and percomp_predios_no_idoneos.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
                        sql.append(" and percomp_predios_no_idoneos.cod_valor = ?unidad_funcional");
                    }

                    // Total de predios
                    sql.append(" left join (");
                    sql.append(strSQLConsultarIndicador);
                    sql.append(" ) total_predio on total_predio.id_tnt = aux.id_tnt and total_predio.periodo = aux.periodo");
                    sql.append(String.format(" and total_predio.cod_indicador = '%s'", ParamIndicador.IND_TOTAL_PREDIOS));
                    sql.append(" and total_predio.nivel_agrupamiento = ?nivel_agrupamiento");
                    sql.append(" and total_predio.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
                    sql.append(" left join (");
                    sql.append(strSQLConsultarIndicador);
                    sql.append(" ) percomp_total_predio on percomp_total_predio.id_tnt = aux.id_tnt and percomp_total_predio.periodo = ?periodo_comparacion");
                    sql.append(String.format(" and percomp_total_predio.cod_indicador = '%s'", ParamIndicador.IND_TOTAL_PREDIOS));
                    sql.append(" and percomp_total_predio.nivel_agrupamiento = ?nivel_agrupamiento");
                    sql.append(" and percomp_total_predio.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
                }
            } else {
                //# de establecimientos que no cumplen la norma (est. no idoneos)
                if (filtro.getEspacioFuncional().equals(EspacioFuncional.BIBLIOTECA)) {
                    sql.append(" left join (");
                    sql.append(strSQLConsultarIndicador);
                    sql.append(" ) est_no_idoneos on est_no_idoneos.id_tnt = aux.id_tnt and est_no_idoneos.periodo = aux.periodo");
                    sql.append(String.format(" and est_no_idoneos.cod_indicador = '%s'", ParamIndicador.IND_AMBIENTE_BIBLIOTECA_NUMERO_ESTABLECIMIENTOS_NO_IDONEOS));
                    sql.append(" and est_no_idoneos.nivel_agrupamiento = ?nivel_agrupamiento");
                    sql.append(" and est_no_idoneos.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
                    sql.append(" left join (");
                    sql.append(strSQLConsultarIndicador);
                    sql.append(" ) percomp_est_no_idoneos on percomp_est_no_idoneos.id_tnt = aux.id_tnt and percomp_est_no_idoneos.periodo = ?periodo_comparacion");
                    sql.append(String.format(" and percomp_est_no_idoneos.cod_indicador = '%s'", ParamIndicador.IND_AMBIENTE_BIBLIOTECA_NUMERO_ESTABLECIMIENTOS_NO_IDONEOS));
                    sql.append(" and percomp_est_no_idoneos.nivel_agrupamiento = ?nivel_agrupamiento");
                    sql.append(" and percomp_est_no_idoneos.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
                } else {
                    if (filtro.getEspacioFuncional().equals(EspacioFuncional.EXPANSIONES_RECREATIVAS)) {
                        sql.append(" left join (");
                        sql.append(strSQLConsultarIndicador);
                        sql.append(" ) est_no_idoneos on est_no_idoneos.id_tnt = aux.id_tnt and est_no_idoneos.periodo = aux.periodo");
                        sql.append(String.format(" and est_no_idoneos.cod_indicador = '%s'", ParamIndicador.IND_AMBIENTE_EXPANSIONES_RECREATIVAS_NUMERO_ESTABLECIMIENTOS_NO_IDONEOS));
                        sql.append(" and est_no_idoneos.nivel_agrupamiento = ?nivel_agrupamiento");
                        sql.append(" and est_no_idoneos.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
                        sql.append(" left join (");
                        sql.append(strSQLConsultarIndicador);
                        sql.append(" ) percomp_est_no_idoneos on percomp_est_no_idoneos.id_tnt = aux.id_tnt and percomp_est_no_idoneos.periodo = ?periodo_comparacion");
                        sql.append(String.format(" and percomp_est_no_idoneos.cod_indicador = '%s'", ParamIndicador.IND_AMBIENTE_EXPANSIONES_RECREATIVAS_NUMERO_ESTABLECIMIENTOS_NO_IDONEOS));
                        sql.append(" and percomp_est_no_idoneos.nivel_agrupamiento = ?nivel_agrupamiento");
                        sql.append(" and percomp_est_no_idoneos.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
                    } else {
                        sql.append(" left join (");
                        sql.append(strSQLConsultarIndicador);
                        sql.append(" ) est_no_idoneos on est_no_idoneos.id_tnt = aux.id_tnt and est_no_idoneos.periodo = aux.periodo");
                        sql.append(String.format(" and est_no_idoneos.cod_indicador = '%s'", ParamIndicador.IND_AMBIENTE_NUMERO_ESTABLECIMIENTOS_NO_IDONEOS));
                        sql.append(" and est_no_idoneos.nivel_agrupamiento = ?nivel_agrupamiento");
                        sql.append(" and est_no_idoneos.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
                        sql.append(" and est_no_idoneos.cod_valor = ?unidad_funcional");
                        sql.append(" left join (");
                        sql.append(strSQLConsultarIndicador);
                        sql.append(" ) percomp_est_no_idoneos on percomp_est_no_idoneos.id_tnt = aux.id_tnt and percomp_est_no_idoneos.periodo = ?periodo_comparacion");
                        sql.append(String.format(" and percomp_est_no_idoneos.cod_indicador = '%s'", ParamIndicador.IND_AMBIENTE_NUMERO_ESTABLECIMIENTOS_NO_IDONEOS));
                        sql.append(" and percomp_est_no_idoneos.nivel_agrupamiento = ?nivel_agrupamiento");
                        sql.append(" and percomp_est_no_idoneos.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
                        sql.append(" and percomp_est_no_idoneos.cod_valor = ?unidad_funcional");
                    }
                }

                //Total Establecimientos
                sql.append(" left join (");
                sql.append(strSQLConsultarIndicador);
                sql.append(" ) total_establecimientos on total_establecimientos.id_tnt = aux.id_tnt and total_establecimientos.periodo = aux.periodo");
                sql.append(String.format(" and total_establecimientos.cod_indicador = '%s'", ParamIndicador.IND_TOTAL_ESTABLECIMIENTOS));
                sql.append(" and total_establecimientos.nivel_agrupamiento = ?nivel_agrupamiento");
                sql.append(" and total_establecimientos.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
                sql.append(" left join (");
                sql.append(strSQLConsultarIndicador);
                sql.append(" ) percomp_total_establecimientos on percomp_total_establecimientos.id_tnt = aux.id_tnt and percomp_total_establecimientos.periodo = ?periodo_comparacion");
                sql.append(String.format(" and percomp_total_establecimientos.cod_indicador = '%s'", ParamIndicador.IND_TOTAL_ESTABLECIMIENTOS));
                sql.append(" and percomp_total_establecimientos.nivel_agrupamiento = ?nivel_agrupamiento");
                sql.append(" and percomp_total_establecimientos.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
                
                if (!filtro.getEspacioFuncional().equals(EspacioFuncional.BIBLIOTECA)) {
                    // Numero de Predios no idoneos
                    if (filtro.getEspacioFuncional().equals(EspacioFuncional.EXPANSIONES_RECREATIVAS)) {
                        sql.append(" left join (");
                        sql.append(strSQLConsultarIndicador);
                        sql.append(" ) predios_no_idoneos on predios_no_idoneos.id_tnt = aux.id_tnt and predios_no_idoneos.periodo = aux.periodo");
                        sql.append(String.format(" and predios_no_idoneos.cod_indicador = '%s'", ParamIndicador.IND_AMBIENTE_EXPANSIONES_RECREATIVAS_NUMERO_PREDIOS_NO_IDONEOS));
                        sql.append(" and predios_no_idoneos.nivel_agrupamiento = ?nivel_agrupamiento");
                        sql.append(" and predios_no_idoneos.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
                        sql.append(" left join (");
                        sql.append(strSQLConsultarIndicador);
                        sql.append(" ) percomp_predios_no_idoneos on percomp_predios_no_idoneos.id_tnt = aux.id_tnt and percomp_predios_no_idoneos.periodo = ?periodo_comparacion");
                        sql.append(String.format(" and percomp_predios_no_idoneos.cod_indicador = '%s'", ParamIndicador.IND_AMBIENTE_EXPANSIONES_RECREATIVAS_NUMERO_PREDIOS_NO_IDONEOS));
                        sql.append(" and percomp_predios_no_idoneos.nivel_agrupamiento = ?nivel_agrupamiento");
                        sql.append(" and percomp_predios_no_idoneos.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
                    } else {
                        sql.append(" left join (");
                        sql.append(strSQLConsultarIndicador);
                        sql.append(" ) predios_no_idoneos on predios_no_idoneos.id_tnt = aux.id_tnt and predios_no_idoneos.periodo = aux.periodo");
                        sql.append(String.format(" and predios_no_idoneos.cod_indicador = '%s'", ParamIndicador.IND_AMBIENTE_NUMERO_PREDIOS_NO_IDONEOS));
                        sql.append(" and predios_no_idoneos.nivel_agrupamiento = ?nivel_agrupamiento");
                        sql.append(" and predios_no_idoneos.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
                        sql.append(" and predios_no_idoneos.cod_valor = ?unidad_funcional");
                        sql.append(" left join (");
                        sql.append(strSQLConsultarIndicador);
                        sql.append(" ) percomp_predios_no_idoneos on percomp_predios_no_idoneos.id_tnt = aux.id_tnt and percomp_predios_no_idoneos.periodo = ?periodo_comparacion");
                        sql.append(String.format(" and percomp_predios_no_idoneos.cod_indicador = '%s'", ParamIndicador.IND_AMBIENTE_NUMERO_PREDIOS_NO_IDONEOS));
                        sql.append(" and percomp_predios_no_idoneos.nivel_agrupamiento = ?nivel_agrupamiento");
                        sql.append(" and percomp_predios_no_idoneos.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
                        sql.append(" and percomp_predios_no_idoneos.cod_valor = ?unidad_funcional");
                    }

                    // Total de predios
                    sql.append(" left join (");
                    sql.append(strSQLConsultarIndicador);
                    sql.append(" ) total_predio on total_predio.id_tnt = aux.id_tnt and total_predio.periodo = aux.periodo");
                    sql.append(String.format(" and total_predio.cod_indicador = '%s'", ParamIndicador.IND_TOTAL_PREDIOS));
                    sql.append(" and total_predio.nivel_agrupamiento = ?nivel_agrupamiento");
                    sql.append(" and total_predio.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
                    sql.append(" left join (");
                    sql.append(strSQLConsultarIndicador);
                    sql.append(" ) percomp_total_predio on percomp_total_predio.id_tnt = aux.id_tnt and percomp_total_predio.periodo = ?periodo_comparacion");
                    sql.append(String.format(" and percomp_total_predio.cod_indicador = '%s'", ParamIndicador.IND_TOTAL_PREDIOS));
                    sql.append(" and percomp_total_predio.nivel_agrupamiento = ?nivel_agrupamiento");
                    sql.append(" and percomp_total_predio.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
                }
            }
        }
        // deficit_estandar Y Cumplimiento norma
        if (filtro.getEspacioFuncional().equals(EspacioFuncional.BIBLIOTECA)) {
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) deficit_estandar on deficit_estandar.id_tnt = aux.id_tnt and deficit_estandar.periodo = aux.periodo");
            sql.append(String.format(" and deficit_estandar.cod_indicador = '%s'", ParamIndicador.IND_AMBIENTE_BIBLIOTECA_DEFICIT_ESTANDAR));
            sql.append(" and deficit_estandar.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and deficit_estandar.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) percomp_deficit_estandar on percomp_deficit_estandar.id_tnt = aux.id_tnt and percomp_deficit_estandar.periodo = ?periodo_comparacion");
            sql.append(String.format(" and percomp_deficit_estandar.cod_indicador = '%s'", ParamIndicador.IND_AMBIENTE_BIBLIOTECA_DEFICIT_ESTANDAR));
            sql.append(" and percomp_deficit_estandar.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and percomp_deficit_estandar.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);

            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) cumplimiento_norma on cumplimiento_norma.id_tnt = aux.id_tnt and cumplimiento_norma.periodo = aux.periodo");
            sql.append(String.format(" and cumplimiento_norma.cod_indicador = '%s'", ParamIndicador.IND_AMBIENTE_BIBLIOTECA_CUMPLIMIENTO_NORMA));
            sql.append(" and cumplimiento_norma.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and cumplimiento_norma.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) percomp_cumplimiento_norma on percomp_cumplimiento_norma.id_tnt = aux.id_tnt and percomp_cumplimiento_norma.periodo = ?periodo_comparacion");
            sql.append(String.format(" and percomp_cumplimiento_norma.cod_indicador = '%s'", ParamIndicador.IND_AMBIENTE_BIBLIOTECA_CUMPLIMIENTO_NORMA));
            sql.append(" and percomp_cumplimiento_norma.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and percomp_cumplimiento_norma.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
        } else {
            if (filtro.getEspacioFuncional().equals(EspacioFuncional.EXPANSIONES_RECREATIVAS)) {
                sql.append(" left join (");
                sql.append(strSQLConsultarIndicador);
                sql.append(" ) deficit_estandar on deficit_estandar.id_tnt = aux.id_tnt and deficit_estandar.periodo = aux.periodo");
                sql.append(String.format(" and deficit_estandar.cod_indicador = '%s'", ParamIndicador.IND_AMBIENTE_EXPANSIONES_RECREATIVAS_DEFICIT_ESTANDAR));
                sql.append(" and deficit_estandar.nivel_agrupamiento = ?nivel_agrupamiento");
                sql.append(" and deficit_estandar.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
                sql.append(" left join (");
                sql.append(strSQLConsultarIndicador);
                sql.append(" ) percomp_deficit_estandar on percomp_deficit_estandar.id_tnt = aux.id_tnt and percomp_deficit_estandar.periodo = ?periodo_comparacion");
                sql.append(String.format(" and percomp_deficit_estandar.cod_indicador = '%s'", ParamIndicador.IND_AMBIENTE_EXPANSIONES_RECREATIVAS_DEFICIT_ESTANDAR));
                sql.append(" and percomp_deficit_estandar.nivel_agrupamiento = ?nivel_agrupamiento");
                sql.append(" and percomp_deficit_estandar.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);

                sql.append(" left join (");
                sql.append(strSQLConsultarIndicador);
                sql.append(" ) cumplimiento_norma on cumplimiento_norma.id_tnt = aux.id_tnt and cumplimiento_norma.periodo = aux.periodo");
                sql.append(String.format(" and cumplimiento_norma.cod_indicador = '%s'", ParamIndicador.IND_AMBIENTE_EXPANSIONES_RECREATIVAS_CUMPLIMIENTO_NORMA));
                sql.append(" and cumplimiento_norma.nivel_agrupamiento = ?nivel_agrupamiento");
                sql.append(" and cumplimiento_norma.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
                sql.append(" left join (");
                sql.append(strSQLConsultarIndicador);
                sql.append(" ) percomp_cumplimiento_norma on percomp_cumplimiento_norma.id_tnt = aux.id_tnt and percomp_cumplimiento_norma.periodo = ?periodo_comparacion");
                sql.append(String.format(" and percomp_cumplimiento_norma.cod_indicador = '%s'", ParamIndicador.IND_AMBIENTE_EXPANSIONES_RECREATIVAS_CUMPLIMIENTO_NORMA));
                sql.append(" and percomp_cumplimiento_norma.nivel_agrupamiento = ?nivel_agrupamiento");
                sql.append(" and percomp_cumplimiento_norma.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
            } else {
                sql.append(" left join (");
                sql.append(strSQLConsultarIndicador);
                sql.append(" ) deficit_estandar on deficit_estandar.id_tnt = aux.id_tnt and deficit_estandar.periodo = aux.periodo");
                sql.append(String.format(" and deficit_estandar.cod_indicador = '%s'", ParamIndicador.IND_AMBIENTE_DEFICIT_ESTANDAR));
                sql.append(" and deficit_estandar.nivel_agrupamiento = ?nivel_agrupamiento");
                sql.append(" and deficit_estandar.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
                sql.append(" and deficit_estandar.cod_valor = ?unidad_funcional");
                sql.append(" left join (");
                sql.append(strSQLConsultarIndicador);
                sql.append(" ) percomp_deficit_estandar on percomp_deficit_estandar.id_tnt = aux.id_tnt and percomp_deficit_estandar.periodo = ?periodo_comparacion");
                sql.append(String.format(" and percomp_deficit_estandar.cod_indicador = '%s'", ParamIndicador.IND_AMBIENTE_DEFICIT_ESTANDAR));
                sql.append(" and percomp_deficit_estandar.nivel_agrupamiento = ?nivel_agrupamiento");
                sql.append(" and percomp_deficit_estandar.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
                sql.append(" and percomp_deficit_estandar.cod_valor = ?unidad_funcional");

                sql.append(" left join (");
                sql.append(strSQLConsultarIndicador);
                sql.append(" ) cumplimiento_norma on cumplimiento_norma.id_tnt = aux.id_tnt and cumplimiento_norma.periodo = aux.periodo");
                sql.append(String.format(" and cumplimiento_norma.cod_indicador = '%s'", ParamIndicador.IND_AMBIENTE_CUMPLIMIENTO_NORMA));
                sql.append(" and cumplimiento_norma.nivel_agrupamiento = ?nivel_agrupamiento");
                sql.append(" and cumplimiento_norma.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
                sql.append(" and cumplimiento_norma.cod_valor = ?unidad_funcional");
                sql.append(" left join (");
                sql.append(strSQLConsultarIndicador);
                sql.append(" ) percomp_cumplimiento_norma on percomp_cumplimiento_norma.id_tnt = aux.id_tnt and percomp_cumplimiento_norma.periodo = ?periodo_comparacion");
                sql.append(String.format(" and percomp_cumplimiento_norma.cod_indicador = '%s'", ParamIndicador.IND_AMBIENTE_CUMPLIMIENTO_NORMA));
                sql.append(" and percomp_cumplimiento_norma.nivel_agrupamiento = ?nivel_agrupamiento");
                sql.append(" and percomp_cumplimiento_norma.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
                sql.append(" and percomp_cumplimiento_norma.cod_valor = ?unidad_funcional");
            }
        }

// WHERE
        sql.append(" where aux.id_tnt = ?id_tnt and aux.periodo = ?periodo");
        if (filtro.isAgrupamientoPredio()) {
            sql.append(" and n.principal_establecimiento = 1");
        }
        if (filtro.getIdNivel0() != null) {
            sql.append(" and n.id_nivel0 = ?id_nivel0");
        }
        if (filtro.getIdNivel1() != null) {
            sql.append(" and n.id_nivel1 = ?id_nivel1");
        }
        if (filtro.getIdNivel2() != null) {
            sql.append(" and n.id_nivel2 = ?id_nivel2");
        }
        if (filtro.getIdNivel3() != null) {
            sql.append(" and n.id_nivel3 = ?id_nivel3");
        }
        if (filtro.getIdNivel4() != null) {
            sql.append(" and n.id_nivel4 = ?id_nivel4");
        }
        if (filtro.getIdNivel5() != null) {
            sql.append(" and n.id_nivel5 = ?id_nivel5");
        }
        if (filtro.getIdEstablecimiento() != null) {
            sql.append(" and n.id_establecimiento = ?id_establecimiento");
        }
        if (filtro.getIdSede() != null) {
            sql.append(" and n.id_sede = ?id_sede");
        }
        if (filtro.getIdPredio() != null) {
            sql.append(" and n.id_predio = ?id_predio");
        }

        Query query = tpm.getEm().createNativeQuery(sql.toString());
        query.setParameter("unidad_funcional", filtro.getEspacioFuncional().name());
        query.setParameter("id_tnt", tpm.getCurrentTenant());
        query.setParameter("periodo", filtro.getPeriodo());
        query.setParameter("periodo_comparacion", filtro.getPeriodoComparado());
        query.setParameter("idioma", filtro.getIdioma());
        query.setParameter("id_nivel0", filtro.getIdNivel0());
        query.setParameter("nivel_agrupamiento", filtro.getAgrupamiento());
        if (filtro.getIdNivel1() != null) {
            query.setParameter("id_nivel1", filtro.getIdNivel1());
        }
        if (filtro.getIdNivel2() != null) {
            query.setParameter("id_nivel2", filtro.getIdNivel2());
        }
        if (filtro.getIdNivel3() != null) {
            query.setParameter("id_nivel3", filtro.getIdNivel3());
        }
        if (filtro.getIdNivel4() != null) {
            query.setParameter("id_nivel4", filtro.getIdNivel4());
        }
        if (filtro.getIdNivel5() != null) {
            query.setParameter("id_nivel5", filtro.getIdNivel5());
        }
        if (filtro.getIdEstablecimiento() != null) {
            query.setParameter("id_establecimiento", filtro.getIdEstablecimiento());
        }
        if (filtro.getIdSede() != null) {
            query.setParameter("id_sede", filtro.getIdSede());
        }
        if (filtro.getIdPredio() != null) {
            query.setParameter("id_predio", filtro.getIdPredio());
        }
        return query.getResultList();
    }

    @Override
    public List<Object[]> consultarAmpliacion(FiltroComparadorInd filtro) {
        StringBuilder sql = new StringBuilder();
        StringBuilder ordenar = new StringBuilder();
// SELECT
        sql.append(" select distinct");
        if (filtro.isAgrupamientoPredio()) {
            //ordenar.append(" n.nombre_nivel1, n.nombre_nivel2, n.nombre_nivel3, n.nombre_nivel4, n.nombre_nivel5");
            
            ordenar.append(" n.codigo_nivel1,n.nombre_nivel1");
            ordenar.append(",n.codigo_nivel2,n.nombre_nivel2");
            ordenar.append(",n.codigo_nivel3,n.nombre_nivel3");
            ordenar.append(",n.codigo_nivel4,n.nombre_nivel4");
            ordenar.append(",n.codigo_nivel5,n.nombre_nivel5");
            
            ordenar.append(" ,n.codigo_establecimiento, n.nombre_establecimiento");
            ordenar.append(" ,n.codigo_sede, n.nombre_sede");
            ordenar.append(" ,n.codigo_predio, n.nombre_predio");
            //otros
            ordenar.append(" ,n.direccion_predio, n.propiedad_predio");
            ordenar.append(" ,n.nombre_zona, n.nombre_sector, n.nombre_clima");
            //etnias
            ordenar.append(" ,etnias.valor");
        }
        if (filtro.isAgrupamientoNivel0()) {
            ordenar.append(" n.nombre_nivel0");
        }
        if (filtro.isAgrupamientoNivel1()) {
            //ordenar.append(" n.nombre_nivel1");
            ordenar.append(" n.codigo_nivel1,n.nombre_nivel1");
        }
        if (filtro.isAgrupamientoNivel2()) {
            //ordenar.append(" n.nombre_nivel1, n.nombre_nivel2");
            ordenar.append(" n.codigo_nivel1,n.nombre_nivel1");
            ordenar.append(",n.codigo_nivel2,n.nombre_nivel2");
        }
        if (filtro.isAgrupamientoNivel3()) {
            //ordenar.append(" n.nombre_nivel1, n.nombre_nivel2, n.nombre_nivel3");
            ordenar.append(" n.codigo_nivel1,n.nombre_nivel1");
            ordenar.append(",n.codigo_nivel2,n.nombre_nivel2");
            ordenar.append(",n.codigo_nivel3,n.nombre_nivel3");
        }
        if (filtro.isAgrupamientoNivel4()) {
            //ordenar.append(" n.nombre_nivel1, n.nombre_nivel2, n.nombre_nivel3, n.nombre_nivel4");
            ordenar.append(" n.codigo_nivel1,n.nombre_nivel1");
            ordenar.append(",n.codigo_nivel2,n.nombre_nivel2");
            ordenar.append(",n.codigo_nivel3,n.nombre_nivel3");
            ordenar.append(",n.codigo_nivel4,n.nombre_nivel4");
        }
        if (filtro.isAgrupamientoNivel5()) {
            //ordenar.append(" n.nombre_nivel1, n.nombre_nivel2, n.nombre_nivel3, n.nombre_nivel4, n.nombre_nivel5");
            ordenar.append(" n.codigo_nivel1,n.nombre_nivel1");
            ordenar.append(",n.codigo_nivel2,n.nombre_nivel2");
            ordenar.append(",n.codigo_nivel3,n.nombre_nivel3");
            ordenar.append(",n.codigo_nivel4,n.nombre_nivel4");
            ordenar.append(",n.codigo_nivel5,n.nombre_nivel5");
        }
        if (filtro.isAgrupamientoEstablecimiento()) {
            //ordenar.append(" n.nombre_nivel1, n.nombre_nivel2, n.nombre_nivel3, n.nombre_nivel4, n.nombre_nivel5");
            ordenar.append(" n.codigo_nivel1,n.nombre_nivel1");
            ordenar.append(",n.codigo_nivel2,n.nombre_nivel2");
            ordenar.append(",n.codigo_nivel3,n.nombre_nivel3");
            ordenar.append(",n.codigo_nivel4,n.nombre_nivel4");
            ordenar.append(",n.codigo_nivel5,n.nombre_nivel5");
            ordenar.append(" ,n.codigo_establecimiento, n.nombre_establecimiento");
        }
        if (filtro.isAgrupamientoSede()) {
            //ordenar.append(" n.nombre_nivel1, n.nombre_nivel2, n.nombre_nivel3, n.nombre_nivel4, n.nombre_nivel5");
            ordenar.append(" n.codigo_nivel1,n.nombre_nivel1");
            ordenar.append(",n.codigo_nivel2,n.nombre_nivel2");
            ordenar.append(",n.codigo_nivel3,n.nombre_nivel3");
            ordenar.append(",n.codigo_nivel4,n.nombre_nivel4");
            ordenar.append(",n.codigo_nivel5,n.nombre_nivel5");
            ordenar.append(" ,n.codigo_establecimiento, n.nombre_establecimiento");
            ordenar.append(" ,n.codigo_sede, n.nombre_sede");
        }
        sql.append(ordenar.toString());
        // indicadores              
        // PREDIO
        if (filtro.isSwPredio()) {
            //area_real_predio
            sql.append(" ,predio1.valor");
            sql.append(" ,percomp_predio1.valor");
            //estandar_predio
            sql.append(" ,predio2.valor");
            sql.append(" ,percomp_predio2.valor");
            //estandar_lote
            sql.append(" ,tbl_estandar_lote.valor");
            sql.append(" ,percomp_tbl_estandar_lote.valor");
            //idoneidad_predio
            sql.append(" ,predio3.valor");
            sql.append(" ,percomp_predio3.valor");
            //cupos_predio            
            sql.append(" ,round(char_to_double(predio4.valor))");
            sql.append(" ,round(char_to_double(percomp_predio4.valor))");
            //m2_requeridos_predio
            sql.append(" ,predio5.valor");
            sql.append(" ,percomp_predio5.valor");
        }

        //CONSTRUCCION
        if (filtro.isSwConstruccion()) {
            //area_cubierta_construccion
            sql.append(" ,construccion1.valor");
            sql.append(" ,percomp_construccion1.valor");
            //estandar_construccion
            sql.append(" ,construccion2.valor");
            sql.append(" ,percomp_construccion2.valor");
            //estandar_construccion de centralizado ind_estandar_lote
            sql.append(" ,tbl_estandar_construccion.valor");
            sql.append(" ,percomp_tbl_est_const.valor");
            //idoneidad_construccion
            sql.append(" ,construccion3.valor");
            sql.append(" ,percomp_construccion3.valor");
            //m2_requeridos_construccion
            sql.append(" ,construccion4.valor");
            sql.append(" ,percomp_construccion4.valor");
        }

        //AULA
        if (filtro.isSwAula()) {
            //area_aula
            sql.append(" ,ambiente_aula1.valor");
            sql.append(" ,percomp_ambiente_aula1.valor");
            //total_ambiente_aula
            sql.append(" ,ambiente_aula2.valor");
            sql.append(" ,percomp_ambiente_aula2.valor");
            //estandar_aula
            sql.append(" ,ambiente_aula3.valor");
            sql.append(" ,percomp_ambiente_aula3.valor");
            //idoneidad_aula
            sql.append(" ,ambiente_aula4.valor");
            sql.append(" ,percomp_ambiente_aula4.valor");
            //m2_requeridos_aula
            sql.append(" ,ambiente_aula5.valor");
            sql.append(" ,percomp_ambiente_aula5.valor");
            //espacios_requeridos_aula
            sql.append(" ,round(char_to_double(ambiente_aula6.valor))");
            sql.append(" ,round(char_to_double(percomp_ambiente_aula6.valor))");
        }

        // INFRAESTRUCTURA
        if (filtro.isSwPosibilidades()) {
            //area_construccion_primer_piso
            sql.append(" ,inf_constr1.valor");
            sql.append(" ,percomp_inf_constr1.valor");
            //posibilidad_construccion_primer_piso
            sql.append(" ,inf_constr2.valor");
            sql.append(" ,percomp_inf_constr2.valor");
            //posibilidad_construccion_altura
            sql.append(" ,inf_constr3.valor");
            sql.append(" ,percomp_inf_constr3.valor");
            //posibilidad_construccion_total
            sql.append(" ,inf_constr4.valor");
            sql.append(" ,percomp_inf_constr4.valor");
            // m2 ampliar cobertura
            sql.append(" ,m2_ampliar_cobertura.valor");
            sql.append(" ,percomp_m2_ampliar_cobertura.valor");
            //numero de alumnos
            sql.append(" ,numero_alumnos_nuevos.valor");
            sql.append(" ,percomp_num_alumnos_nuevos.valor");
        }
        
// FROM 
        sql.append(" from ind_auxiliar_indicador aux");
        sql.append(" inner join ind_auxiliar_consultas n on n.id_predio = aux.id_predio and n.periodo = aux.periodo and n.id_tnt = aux.id_tnt");

        String strSQLNivelAgrupamiento = this.getSQLNivelAgrupamiento(filtro);
        String strSQLConsultarIndicador = this.getSQLConsultarIndicadorAlmacenado();

        if (filtro.isAgrupamientoPredio()) {
            // etnias
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) etnias on etnias.id_tnt = aux.id_tnt and etnias.periodo = aux.periodo");
            sql.append(String.format(" and etnias.cod_indicador = '%s'", ParamIndicador.IND_ETNIAS_PREDIO));
            sql.append(" and etnias.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and etnias.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
        }

        //PREDIO
        if (filtro.isSwPredio()) {
            //area_real_predio
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) predio1 on predio1.id_tnt = aux.id_tnt and predio1.periodo = aux.periodo");
            sql.append(String.format(" and predio1.cod_indicador = '%s'", ParamIndicador.IND_AREA_REAL_PREDIO));
            sql.append(" and predio1.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and predio1.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) percomp_predio1 on percomp_predio1.id_tnt = aux.id_tnt and percomp_predio1.periodo = ?periodo_comparacion");
            sql.append(String.format(" and percomp_predio1.cod_indicador = '%s'", ParamIndicador.IND_AREA_REAL_PREDIO));
            sql.append(" and percomp_predio1.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and percomp_predio1.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
            //estandar_predio
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) predio2 on predio2.id_tnt = aux.id_tnt and predio2.periodo = aux.periodo");
            sql.append(String.format(" and predio2.cod_indicador = '%s'", ParamIndicador.IND_AMPLIACION_ESTANDAR_PREDIO));
            sql.append(" and predio2.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and predio2.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) percomp_predio2 on percomp_predio2.id_tnt = aux.id_tnt and percomp_predio2.periodo = ?periodo_comparacion");
            sql.append(String.format(" and percomp_predio2.cod_indicador = '%s'", ParamIndicador.IND_AMPLIACION_ESTANDAR_PREDIO));
            sql.append(" and percomp_predio2.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and percomp_predio2.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
            //idoneidad_predio
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) predio3 on predio3.id_tnt = aux.id_tnt and predio3.periodo = aux.periodo");
            sql.append(String.format(" and predio3.cod_indicador = '%s'", ParamIndicador.IND_AMPLIACION_IDONEIDAD_PREDIO));
            sql.append(" and predio3.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and predio3.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) percomp_predio3 on percomp_predio3.id_tnt = aux.id_tnt and percomp_predio3.periodo = ?periodo_comparacion");
            sql.append(String.format(" and percomp_predio3.cod_indicador = '%s'", ParamIndicador.IND_AMPLIACION_IDONEIDAD_PREDIO));
            sql.append(" and percomp_predio3.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and percomp_predio3.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
            //cupos_predio            
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) predio4 on predio4.id_tnt = aux.id_tnt and predio4.periodo = aux.periodo");
            sql.append(String.format(" and predio4.cod_indicador = '%s'", ParamIndicador.IND_AMPLIACION_CUPOS_PREDIO));
            sql.append(" and predio4.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and predio4.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) percomp_predio4 on percomp_predio4.id_tnt = aux.id_tnt and percomp_predio4.periodo = ?periodo_comparacion");
            sql.append(String.format(" and percomp_predio4.cod_indicador = '%s'", ParamIndicador.IND_AMPLIACION_CUPOS_PREDIO));
            sql.append(" and percomp_predio4.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and percomp_predio4.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
            //m2_requeridos_predio
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) predio5 on predio5.id_tnt = aux.id_tnt and predio5.periodo = aux.periodo");
            sql.append(String.format(" and predio5.cod_indicador = '%s'", ParamIndicador.IND_AMPLIACION_M2_REQUERIDOS_PREDIO));
            sql.append(" and predio5.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and predio5.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) percomp_predio5 on percomp_predio5.id_tnt = aux.id_tnt and percomp_predio5.periodo = ?periodo_comparacion");
            sql.append(String.format(" and percomp_predio5.cod_indicador = '%s'", ParamIndicador.IND_AMPLIACION_M2_REQUERIDOS_PREDIO));
            sql.append(" and percomp_predio5.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and percomp_predio5.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
            
            //estandar_lote
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) tbl_estandar_lote on tbl_estandar_lote.id_tnt = aux.id_tnt and tbl_estandar_lote.periodo = aux.periodo");
            sql.append(String.format(" and tbl_estandar_lote.cod_indicador = '%s'", ParamIndicador.IND_ESTANDAR_LOTE));
            sql.append(" and tbl_estandar_lote.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and tbl_estandar_lote.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) percomp_tbl_estandar_lote on percomp_tbl_estandar_lote.id_tnt = aux.id_tnt and percomp_tbl_estandar_lote.periodo = ?periodo_comparacion");
            sql.append(String.format(" and percomp_tbl_estandar_lote.cod_indicador = '%s'", ParamIndicador.IND_ESTANDAR_LOTE));
            sql.append(" and percomp_tbl_estandar_lote.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and percomp_tbl_estandar_lote.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
        }

        //CONSTRUCCION
        if (filtro.isSwConstruccion()) {
            //area_cubierta_construccion
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) construccion1 on construccion1.id_tnt = aux.id_tnt and construccion1.periodo = aux.periodo");
            sql.append(String.format(" and construccion1.cod_indicador = '%s'", ParamIndicador.IND_TOTAL_AREA_CONSTRUCCION_PREDIO));
            sql.append(" and construccion1.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and construccion1.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) percomp_construccion1 on percomp_construccion1.id_tnt = aux.id_tnt and percomp_construccion1.periodo = ?periodo_comparacion");
            sql.append(String.format(" and percomp_construccion1.cod_indicador = '%s'", ParamIndicador.IND_TOTAL_AREA_CONSTRUCCION_PREDIO));
            sql.append(" and percomp_construccion1.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and percomp_construccion1.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
            //estandar_construccion
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) construccion2 on construccion2.id_tnt = aux.id_tnt and construccion2.periodo = aux.periodo");
            sql.append(String.format(" and construccion2.cod_indicador = '%s'", ParamIndicador.IND_AMPLIACION_ESTANDAR_CONSTRUCCION));
            sql.append(" and construccion2.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and construccion2.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) percomp_construccion2 on percomp_construccion2.id_tnt = aux.id_tnt and percomp_construccion2.periodo = ?periodo_comparacion");
            sql.append(String.format(" and percomp_construccion2.cod_indicador = '%s'", ParamIndicador.IND_AMPLIACION_ESTANDAR_CONSTRUCCION));
            sql.append(" and percomp_construccion2.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and percomp_construccion2.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
            //idoneidad_construccion
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) construccion3 on construccion3.id_tnt = aux.id_tnt and construccion3.periodo = aux.periodo");
            sql.append(String.format(" and construccion3.cod_indicador = '%s'", ParamIndicador.IND_AMPLIACION_IDONEIDAD_CONSTRUCCION));
            sql.append(" and construccion3.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and construccion3.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) percomp_construccion3 on percomp_construccion3.id_tnt = aux.id_tnt and percomp_construccion3.periodo = ?periodo_comparacion");
            sql.append(String.format(" and percomp_construccion3.cod_indicador = '%s'", ParamIndicador.IND_AMPLIACION_IDONEIDAD_CONSTRUCCION));
            sql.append(" and percomp_construccion3.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and percomp_construccion3.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
            //m2_requeridos_construccion
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) construccion4 on construccion4.id_tnt = aux.id_tnt and construccion4.periodo = aux.periodo");
            sql.append(String.format(" and construccion4.cod_indicador = '%s'", ParamIndicador.IND_AMPLIACION_M2_REQUERIDOS_CONSTRUCCION));
            sql.append(" and construccion4.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and construccion4.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) percomp_construccion4 on percomp_construccion4.id_tnt = aux.id_tnt and percomp_construccion4.periodo = ?periodo_comparacion");
            sql.append(String.format(" and percomp_construccion4.cod_indicador = '%s'", ParamIndicador.IND_AMPLIACION_M2_REQUERIDOS_CONSTRUCCION));
            sql.append(" and percomp_construccion4.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and percomp_construccion4.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
            //estandar_construccion de ind_estandar_lote
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) tbl_estandar_construccion on tbl_estandar_construccion.id_tnt = aux.id_tnt and tbl_estandar_construccion.periodo = aux.periodo");
            sql.append(String.format(" and tbl_estandar_construccion.cod_indicador = '%s'", ParamIndicador.IND_ESTANDAR_CONSTRUCCION));
            sql.append(" and tbl_estandar_construccion.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and tbl_estandar_construccion.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) percomp_tbl_est_const on percomp_tbl_est_const.id_tnt = aux.id_tnt and percomp_tbl_est_const.periodo = ?periodo_comparacion");
            sql.append(String.format(" and percomp_tbl_est_const.cod_indicador = '%s'", ParamIndicador.IND_ESTANDAR_CONSTRUCCION));
            sql.append(" and percomp_tbl_est_const.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and percomp_tbl_est_const.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
        }

        //AULA 
        if (filtro.isSwAula()) {
            //area_aula
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) ambiente_aula1 on ambiente_aula1.id_tnt = aux.id_tnt and ambiente_aula1.periodo = aux.periodo");
            sql.append(String.format(" and ambiente_aula1.cod_indicador = '%s'", ParamIndicador.IND_AMPLIACION_AREA_AULA));
            sql.append(" and ambiente_aula1.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and ambiente_aula1.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) percomp_ambiente_aula1 on percomp_ambiente_aula1.id_tnt = aux.id_tnt and percomp_ambiente_aula1.periodo = ?periodo_comparacion");
            sql.append(String.format(" and percomp_ambiente_aula1.cod_indicador = '%s'", ParamIndicador.IND_AMPLIACION_AREA_AULA));
            sql.append(" and percomp_ambiente_aula1.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and percomp_ambiente_aula1.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
            //total_ambiente_aula
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) ambiente_aula2 on ambiente_aula2.id_tnt = aux.id_tnt and ambiente_aula2.periodo = aux.periodo");
            sql.append(String.format(" and ambiente_aula2.cod_indicador = '%s'", ParamIndicador.IND_AMPLIACION_NUM_TOTAL_AULA));
            sql.append(" and ambiente_aula2.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and ambiente_aula2.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) percomp_ambiente_aula2 on percomp_ambiente_aula2.id_tnt = aux.id_tnt and percomp_ambiente_aula2.periodo = ?periodo_comparacion");
            sql.append(String.format(" and percomp_ambiente_aula2.cod_indicador = '%s'", ParamIndicador.IND_AMPLIACION_NUM_TOTAL_AULA));
            sql.append(" and percomp_ambiente_aula2.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and percomp_ambiente_aula2.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
            //estandar_aula
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) ambiente_aula3 on ambiente_aula3.id_tnt = aux.id_tnt and ambiente_aula3.periodo = aux.periodo");
            sql.append(String.format(" and ambiente_aula3.cod_indicador = '%s'", ParamIndicador.IND_AMPLIACION_ESTANDAR_AULA));
            sql.append(" and ambiente_aula3.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and ambiente_aula3.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) percomp_ambiente_aula3 on percomp_ambiente_aula3.id_tnt = aux.id_tnt and percomp_ambiente_aula3.periodo = ?periodo_comparacion");
            sql.append(String.format(" and percomp_ambiente_aula3.cod_indicador = '%s'", ParamIndicador.IND_AMPLIACION_ESTANDAR_AULA));
            sql.append(" and percomp_ambiente_aula3.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and percomp_ambiente_aula3.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
            //idoneidad_aula
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) ambiente_aula4 on ambiente_aula4.id_tnt = aux.id_tnt and ambiente_aula4.periodo = aux.periodo");
            sql.append(String.format(" and ambiente_aula4.cod_indicador = '%s'", ParamIndicador.IND_AMPLIACION_IDONEIDAD_AULA));
            sql.append(" and ambiente_aula4.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and ambiente_aula4.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) percomp_ambiente_aula4 on percomp_ambiente_aula4.id_tnt = aux.id_tnt and percomp_ambiente_aula4.periodo = ?periodo_comparacion");
            sql.append(String.format(" and percomp_ambiente_aula4.cod_indicador = '%s'", ParamIndicador.IND_AMPLIACION_IDONEIDAD_AULA));
            sql.append(" and percomp_ambiente_aula4.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and percomp_ambiente_aula4.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
            //m2_requeridos_aula
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) ambiente_aula5 on ambiente_aula5.id_tnt = aux.id_tnt and ambiente_aula5.periodo = aux.periodo");
            sql.append(String.format(" and ambiente_aula5.cod_indicador = '%s'", ParamIndicador.IND_AMPLIACION_M2_REQUERIDOS_AULA));
            sql.append(" and ambiente_aula5.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and ambiente_aula5.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) percomp_ambiente_aula5 on percomp_ambiente_aula5.id_tnt = aux.id_tnt and percomp_ambiente_aula5.periodo = ?periodo_comparacion");
            sql.append(String.format(" and percomp_ambiente_aula5.cod_indicador = '%s'", ParamIndicador.IND_AMPLIACION_M2_REQUERIDOS_AULA));
            sql.append(" and percomp_ambiente_aula5.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and percomp_ambiente_aula5.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
            //espacios_requeridos_aula
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) ambiente_aula6 on ambiente_aula6.id_tnt = aux.id_tnt and ambiente_aula6.periodo = aux.periodo");
            sql.append(String.format(" and ambiente_aula6.cod_indicador = '%s'", ParamIndicador.IND_AMPLIACION_ESPACIO_REQUERIDOS_AULA));
            sql.append(" and ambiente_aula6.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and ambiente_aula6.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) percomp_ambiente_aula6 on percomp_ambiente_aula6.id_tnt = aux.id_tnt and percomp_ambiente_aula6.periodo = ?periodo_comparacion");
            sql.append(String.format(" and percomp_ambiente_aula6.cod_indicador = '%s'", ParamIndicador.IND_AMPLIACION_ESPACIO_REQUERIDOS_AULA));
            sql.append(" and percomp_ambiente_aula6.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and percomp_ambiente_aula6.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
        }

        // INFRAESTRUCTURA 
        if (filtro.isSwPosibilidades()) {
            //area_construccion_primer_piso
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) inf_constr1 on inf_constr1.id_tnt = aux.id_tnt and inf_constr1.periodo = aux.periodo");
            sql.append(String.format(" and inf_constr1.cod_indicador = '%s'", ParamIndicador.IND_AMPLIACION_AREA_CONSTRUCCION_PRIMER_PISO));
            sql.append(" and inf_constr1.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and inf_constr1.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) percomp_inf_constr1 on percomp_inf_constr1.id_tnt = aux.id_tnt and percomp_inf_constr1.periodo = ?periodo_comparacion");
            sql.append(String.format(" and percomp_inf_constr1.cod_indicador = '%s'", ParamIndicador.IND_AMPLIACION_AREA_CONSTRUCCION_PRIMER_PISO));
            sql.append(" and percomp_inf_constr1.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and percomp_inf_constr1.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
            //posibilidad_construccion_primer_piso
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) inf_constr2 on inf_constr2.id_tnt = aux.id_tnt and inf_constr2.periodo = aux.periodo");
            sql.append(String.format(" and inf_constr2.cod_indicador = '%s'", ParamIndicador.IND_AMPLIACION_POSIBILIDAD_CONSTRUCCION_PRIMER_PISO));
            sql.append(" and inf_constr2.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and inf_constr2.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) percomp_inf_constr2 on percomp_inf_constr2.id_tnt = aux.id_tnt and percomp_inf_constr2.periodo = ?periodo_comparacion");
            sql.append(String.format(" and percomp_inf_constr2.cod_indicador = '%s'", ParamIndicador.IND_AMPLIACION_POSIBILIDAD_CONSTRUCCION_PRIMER_PISO));
            sql.append(" and percomp_inf_constr2.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and percomp_inf_constr2.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
            //posibilidad_construccion_altura
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) inf_constr3 on inf_constr3.id_tnt = aux.id_tnt and inf_constr3.periodo = aux.periodo");
            sql.append(String.format(" and inf_constr3.cod_indicador = '%s'", ParamIndicador.IND_AMPLIACION_POSIBILIDAD_CONSTRUCCION_ALTURA));
            sql.append(" and inf_constr3.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and inf_constr3.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) percomp_inf_constr3 on percomp_inf_constr3.id_tnt = aux.id_tnt and percomp_inf_constr3.periodo = ?periodo_comparacion");
            sql.append(String.format(" and percomp_inf_constr3.cod_indicador = '%s'", ParamIndicador.IND_AMPLIACION_POSIBILIDAD_CONSTRUCCION_ALTURA));
            sql.append(" and percomp_inf_constr3.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and percomp_inf_constr3.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
            //posibilidad_construccion_total
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) inf_constr4 on inf_constr4.id_tnt = aux.id_tnt and inf_constr4.periodo = aux.periodo");
            sql.append(String.format(" and inf_constr4.cod_indicador = '%s'", ParamIndicador.IND_AMPLIACION_POSIBILIDAD_CONSTRUCCION_TOTAL));
            sql.append(" and inf_constr4.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and inf_constr4.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) percomp_inf_constr4 on percomp_inf_constr4.id_tnt = aux.id_tnt and percomp_inf_constr4.periodo = ?periodo_comparacion");
            sql.append(String.format(" and percomp_inf_constr4.cod_indicador = '%s'", ParamIndicador.IND_AMPLIACION_POSIBILIDAD_CONSTRUCCION_TOTAL));
            sql.append(" and percomp_inf_constr4.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and percomp_inf_constr4.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
            // m2 ampliar cobertura
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) m2_ampliar_cobertura on m2_ampliar_cobertura.id_tnt = aux.id_tnt and m2_ampliar_cobertura.periodo = aux.periodo");
            sql.append(String.format(" and m2_ampliar_cobertura.cod_indicador = '%s'", ParamIndicador.IND_AMPLIACION_M2_AMPLIAR_COBERTURA));
            sql.append(" and m2_ampliar_cobertura.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and m2_ampliar_cobertura.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) percomp_m2_ampliar_cobertura on percomp_m2_ampliar_cobertura.id_tnt = aux.id_tnt and percomp_m2_ampliar_cobertura.periodo = ?periodo_comparacion");
            sql.append(String.format(" and percomp_m2_ampliar_cobertura.cod_indicador = '%s'", ParamIndicador.IND_AMPLIACION_M2_AMPLIAR_COBERTURA));
            sql.append(" and percomp_m2_ampliar_cobertura.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and percomp_m2_ampliar_cobertura.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
            //numero de alumnos
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) numero_alumnos_nuevos on numero_alumnos_nuevos.id_tnt = aux.id_tnt and numero_alumnos_nuevos.periodo = aux.periodo");
            sql.append(String.format(" and numero_alumnos_nuevos.cod_indicador = '%s'", ParamIndicador.IND_AMPLIACION_ALUMNOS_NUEVOS_INFRAESTRUCTURA));
            sql.append(" and numero_alumnos_nuevos.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and numero_alumnos_nuevos.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) percomp_num_alumnos_nuevos on percomp_num_alumnos_nuevos.id_tnt = aux.id_tnt and percomp_num_alumnos_nuevos.periodo = ?periodo_comparacion");
            sql.append(String.format(" and percomp_num_alumnos_nuevos.cod_indicador = '%s'", ParamIndicador.IND_AMPLIACION_ALUMNOS_NUEVOS_INFRAESTRUCTURA));
            sql.append(" and percomp_num_alumnos_nuevos.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and percomp_num_alumnos_nuevos.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
        }

// WHERE
        sql.append(" where aux.id_tnt = ?id_tnt and aux.periodo = ?periodo");
        if (filtro.isAgrupamientoPredio()) {
            sql.append(" and n.principal_establecimiento = 1");
        }
        if (filtro.getIdNivel0() != null) {
            sql.append(" and n.id_nivel0 = ?id_nivel0");
        }
        if (filtro.getIdNivel1() != null) {
            sql.append(" and n.id_nivel1 = ?id_nivel1");
        }
        if (filtro.getIdNivel2() != null) {
            sql.append(" and n.id_nivel2 = ?id_nivel2");
        }
        if (filtro.getIdNivel3() != null) {
            sql.append(" and n.id_nivel3 = ?id_nivel3");
        }
        if (filtro.getIdNivel4() != null) {
            sql.append(" and n.id_nivel4 = ?id_nivel4");
        }
        if (filtro.getIdNivel5() != null) {
            sql.append(" and n.id_nivel5 = ?id_nivel5");
        }
        if (filtro.getIdEstablecimiento() != null) {
            sql.append(" and n.id_establecimiento = ?id_establecimiento");
        }
        if (filtro.getIdSede() != null) {
            sql.append(" and n.id_sede = ?id_sede");
        }
        if (filtro.getIdPredio() != null) {
            sql.append(" and n.id_predio = ?id_predio");
        }

        Query query = tpm.getEm().createNativeQuery(sql.toString());
        query.setParameter("id_tnt", tpm.getCurrentTenant());
        query.setParameter("periodo", filtro.getPeriodo());
        query.setParameter("periodo_comparacion", filtro.getPeriodoComparado());
        query.setParameter("idioma", filtro.getIdioma());
        query.setParameter("id_nivel0", filtro.getIdNivel0());
        query.setParameter("nivel_agrupamiento", filtro.getAgrupamiento());
        if (filtro.getIdNivel1() != null) {
            query.setParameter("id_nivel1", filtro.getIdNivel1());
        }
        if (filtro.getIdNivel2() != null) {
            query.setParameter("id_nivel2", filtro.getIdNivel2());
        }
        if (filtro.getIdNivel3() != null) {
            query.setParameter("id_nivel3", filtro.getIdNivel3());
        }
        if (filtro.getIdNivel4() != null) {
            query.setParameter("id_nivel4", filtro.getIdNivel4());
        }
        if (filtro.getIdNivel5() != null) {
            query.setParameter("id_nivel5", filtro.getIdNivel5());
        }
        if (filtro.getIdEstablecimiento() != null) {
            query.setParameter("id_establecimiento", filtro.getIdEstablecimiento());
        }
        if (filtro.getIdSede() != null) {
            query.setParameter("id_sede", filtro.getIdSede());
        }
        if (filtro.getIdPredio() != null) {
            query.setParameter("id_predio", filtro.getIdPredio());
        }
        return query.getResultList();
    
    }

    @Override
    public List<Object[]> consultarEdificios(FiltroComparadorInd filtro) {
         if(ParamBaseDatos.POSTGRE.equals(UtilProperties.getDbMotor())){
             return consultarEdificiosPostgre(filtro);
        }else{
            return consultarEdificiosOracle(filtro);
        }
    }
    
    @Override
    public List<Object[]> consultarEspacios(FiltroComparadorInd filtro){
        if(ParamBaseDatos.POSTGRE.equals(UtilProperties.getDbMotor())){
            return this.consultarEspaciosPostgre(filtro);
        }else{
            return this.consultarEspaciosOracle(filtro);
        }
    }
    
    private List<Object[]> consultarEspaciosOracle(FiltroComparadorInd filtro) {
        
        StringBuilder sql = new StringBuilder();
        StringBuilder agrupamiento = new StringBuilder();
//SELECT
        sql.append(" select distinct ");

        if (filtro.isAgrupamientoPredio()) {
            //sql.append(" n.nombre_nivel1, n.nombre_nivel2, n.nombre_nivel3, n.nombre_nivel4, n.nombre_nivel5");
            
            sql.append(" n.codigo_nivel1,n.nombre_nivel1");
            sql.append(",n.codigo_nivel2,n.nombre_nivel2");
            sql.append(",n.codigo_nivel3,n.nombre_nivel3");
            sql.append(",n.codigo_nivel4,n.nombre_nivel4");
            sql.append(",n.codigo_nivel5,n.nombre_nivel5");
            
            sql.append(", n.codigo_establecimiento, n.nombre_establecimiento");
            sql.append(", n.codigo_sede, n.nombre_sede");
            sql.append(", n.codigo_predio, n.nombre_predio");
            //*otros
            sql.append(" , n.direccion_predio direccion_predio, n.nombre_zona zona");
            sql.append(" , n.nombre_sector sector, n.nombre_clima clima");

        }
        if (filtro.isAgrupamientoNivel0()) {
            agrupamiento.append(" aux.nombre_nivel0");
        }
        if (filtro.isAgrupamientoNivel1()) {
            //agrupamiento.append(" aux.nombre_nivel1");
            agrupamiento.append(" n.codigo_nivel1,n.nombre_nivel1");
        }
        if (filtro.isAgrupamientoNivel2()) {
            //agrupamiento.append(" aux.nombre_nivel1, aux.nombre_nivel2");
            agrupamiento.append(" n.codigo_nivel1,n.nombre_nivel1");
            agrupamiento.append(",n.codigo_nivel2,n.nombre_nivel2");
        }
        if (filtro.isAgrupamientoNivel3()) {
            //agrupamiento.append(" aux.nombre_nivel1, aux.nombre_nivel2, aux.nombre_nivel3");
            agrupamiento.append(" n.codigo_nivel1,n.nombre_nivel1");
            agrupamiento.append(",n.codigo_nivel2,n.nombre_nivel2");
            agrupamiento.append(",n.codigo_nivel3,n.nombre_nivel3");
        }
        if (filtro.isAgrupamientoNivel4()) {
            //agrupamiento.append(" aux.nombre_nivel1, aux.nombre_nivel2, aux.nombre_nivel3, aux.nombre_nivel4");
            agrupamiento.append(" n.codigo_nivel1,n.nombre_nivel1");
            agrupamiento.append(",n.codigo_nivel2,n.nombre_nivel2");
            agrupamiento.append(",n.codigo_nivel3,n.nombre_nivel3");
            agrupamiento.append(",n.codigo_nivel4,n.nombre_nivel4");
        }
        if (filtro.isAgrupamientoNivel5()) {
            //agrupamiento.append(" aux.nombre_nivel1, aux.nombre_nivel2, aux.nombre_nivel3, aux.nombre_nivel4, aux.nombre_nivel5");
            agrupamiento.append(" n.codigo_nivel1,n.nombre_nivel1");
            agrupamiento.append(",n.codigo_nivel2,n.nombre_nivel2");
            agrupamiento.append(",n.codigo_nivel3,n.nombre_nivel3");
            agrupamiento.append(",n.codigo_nivel4,n.nombre_nivel4");
            agrupamiento.append(",n.codigo_nivel5,n.nombre_nivel5");
        }
        if (filtro.isAgrupamientoEstablecimiento()) {
            //agrupamiento.append(" aux.nombre_nivel1, aux.nombre_nivel2, aux.nombre_nivel3, aux.nombre_nivel4, aux.nombre_nivel5");
            agrupamiento.append(" n.codigo_nivel1,n.nombre_nivel1");
            agrupamiento.append(",n.codigo_nivel2,n.nombre_nivel2");
            agrupamiento.append(",n.codigo_nivel3,n.nombre_nivel3");
            agrupamiento.append(",n.codigo_nivel4,n.nombre_nivel4");
            agrupamiento.append(",n.codigo_nivel5,n.nombre_nivel5");
            agrupamiento.append(", aux.codigo_establecimiento, aux.nombre_establecimiento");
        }
        if (filtro.isAgrupamientoSede()) {
            //agrupamiento.append(" aux.nombre_nivel1, aux.nombre_nivel2, aux.nombre_nivel3, aux.nombre_nivel4, aux.nombre_nivel5");
            agrupamiento.append(" n.codigo_nivel1,n.nombre_nivel1");
            agrupamiento.append(",n.codigo_nivel2,n.nombre_nivel2");
            agrupamiento.append(",n.codigo_nivel3,n.nombre_nivel3");
            agrupamiento.append(",n.codigo_nivel4,n.nombre_nivel4");
            agrupamiento.append(",n.codigo_nivel5,n.nombre_nivel5");
            agrupamiento.append(", aux.codigo_establecimiento, aux.nombre_establecimiento");
            agrupamiento.append(", aux.codigo_sede, aux.nombre_sede");
        }

        // select
        sql.append(agrupamiento.toString());
        // *indicadores        
        //# de espacios según condición de piso
        sql.append(" ,NVL(espacios_piso.valor,0)");
        sql.append(" ,NVL(percomp_espacios_piso.valor,0)");
        // Área (m2), según condición piso
        sql.append(" ,NVL(area_piso.valor,0)");
        sql.append(" ,NVL(percomp_area_piso.valor,0)");
        //Proporción área (m2) piso
        sql.append(", DIVIDIR(NVL(CHAR_TO_DOUBLE(area_piso.valor),0),CASE WHEN area_horizontal.valor IS NULL OR area_horizontal.valor='0' THEN 1 ELSE CHAR_TO_DOUBLE(area_horizontal.valor) END) ");
        sql.append(", DIVIDIR(NVL(CHAR_TO_DOUBLE(percomp_area_piso.valor),0),CASE WHEN percomp_area_horizontal.valor IS NULL OR percomp_area_horizontal.valor='0' THEN 1 ELSE CHAR_TO_DOUBLE(percomp_area_horizontal.valor) END) ");
        //# de espacios según condición de muro
        sql.append(" ,NVL(espacios_muro.valor,0)");
        sql.append(" ,NVL(percomp_espacios_muro.valor,0)");
        // Área (m2), según condición de muro
        sql.append(" ,NVL(area_muro.valor,0)");
        sql.append(" ,NVL(percomp_area_muro.valor,0)");
        //Proporción área (m2) muro
        sql.append(" ,DIVIDIR(NVL(CHAR_TO_DOUBLE(area_muro.valor),0),CASE WHEN area_vertical.valor IS NULL OR area_vertical.valor='0' THEN 1 ELSE CHAR_TO_DOUBLE(area_vertical.valor) END) ");
        sql.append(" ,DIVIDIR(NVL(CHAR_TO_DOUBLE(percomp_area_muro.valor),0),CASE WHEN percomp_area_vertical.valor IS NULL OR percomp_area_vertical.valor='0' THEN 1 ELSE CHAR_TO_DOUBLE(percomp_area_vertical.valor) END) ");
        //# de espacios según condición de acabado muro
        sql.append(" ,NVL(espacios_acabado_muro.valor,0)");
        sql.append(" ,NVL(percomp_esp_acabado_muro.valor,0)");
        // Área (m2), según condición de acabado muro
        sql.append(" ,NVL(area_acabado_muro.valor,0)");
        sql.append(" ,NVL(percomp_area_acabado_muro.valor,0)");
        //Proporción área (m2) acabado muro
        sql.append(" ,DIVIDIR(NVL(CHAR_TO_DOUBLE(area_acabado_muro.valor),0),CASE WHEN area_vertical.valor IS NULL OR area_vertical.valor='0' THEN 1 ELSE CHAR_TO_DOUBLE(area_vertical.valor) END) ");
        sql.append(" ,DIVIDIR(NVL(CHAR_TO_DOUBLE(percomp_area_acabado_muro.valor),0),CASE WHEN percomp_area_vertical.valor IS NULL OR percomp_area_vertical.valor='0' THEN 1 ELSE CHAR_TO_DOUBLE(percomp_area_vertical.valor) END) ");
        //# de espacios según condición de ventana
        sql.append(" ,NVL(espacios_ventana.valor,0)");
        sql.append(" ,NVL(percomp_espacios_ventana.valor,0)");
        //Proporción ventana
        sql.append(" ,DIVIDIR(NVL(CHAR_TO_DOUBLE(espacios_ventana.valor),0),CASE WHEN total_espacios_con_ventana.valor IS NULL OR total_espacios_con_ventana.valor='0' THEN 1 ELSE CHAR_TO_DOUBLE(total_espacios_con_ventana.valor) END) ");
        sql.append(" ,DIVIDIR(NVL(CHAR_TO_DOUBLE(percomp_espacios_ventana.valor),0),CASE WHEN percomp_tot_esp_con_ventana.valor IS NULL OR percomp_tot_esp_con_ventana.valor='0' THEN 1 ELSE CHAR_TO_DOUBLE(percomp_tot_esp_con_ventana.valor) END) ");
        //# de espacios según condición de puerta
        sql.append(" ,NVL(espacios_puerta.valor,0)");
        sql.append(" ,NVL(percomp_espacios_puerta.valor,0)");
        //Proporción puerta
        sql.append(" ,DIVIDIR(NVL(CHAR_TO_DOUBLE(espacios_puerta.valor),0),CASE WHEN total_espacios_con_puerta.valor IS NULL OR total_espacios_con_puerta.valor='0' THEN 1 ELSE CHAR_TO_DOUBLE(total_espacios_con_puerta.valor) END) ");
        sql.append(" ,DIVIDIR(NVL(CHAR_TO_DOUBLE(percomp_espacios_puerta.valor),0),CASE WHEN percomp_tot_esp_con_puerta.valor IS NULL OR percomp_tot_esp_con_puerta.valor='0' THEN 1 ELSE CHAR_TO_DOUBLE(percomp_tot_esp_con_puerta.valor) END) ");
        //# de espacios según condición de techo
        sql.append(" ,NVL(espacios_techo.valor,0)");
        sql.append(" ,NVL(percomp_espacios_techo.valor,0)");
        // Área (m2), según condición techo
        sql.append(" ,NVL(area_techo.valor,0)");
        sql.append(" ,NVL(percomp_area_techo.valor,0)");
        //Proporción área (m2) techo
        sql.append(" ,DIVIDIR(NVL(CHAR_TO_DOUBLE(area_techo.valor),0),CASE WHEN area_total_techo.valor IS NULL OR area_total_techo.valor='0' THEN 1 ELSE CHAR_TO_DOUBLE(area_total_techo.valor) END) ");
        sql.append(" ,DIVIDIR(NVL(CHAR_TO_DOUBLE(percomp_area_techo.valor),0),CASE WHEN percomp_area_total_techo.valor IS NULL OR percomp_area_total_techo.valor='0' THEN 1 ELSE CHAR_TO_DOUBLE(percomp_area_total_techo.valor) END) ");
        //Área total
        sql.append(" ,NVL(CHAR_TO_DOUBLE(area_piso.valor)+CHAR_TO_DOUBLE(area_muro.valor)+CHAR_TO_DOUBLE(area_acabado_muro.valor)+CHAR_TO_DOUBLE(area_techo.valor),0)");
        sql.append(" ,NVL(CHAR_TO_DOUBLE(percomp_area_piso.valor)+CHAR_TO_DOUBLE(percomp_area_muro.valor)+CHAR_TO_DOUBLE(percomp_area_acabado_muro.valor)+CHAR_TO_DOUBLE(percomp_area_techo.valor),0)");

// FROM 
        String tablaAS; //para identificar con que letra se hace el cruce de info
        if (filtro.isAgrupamientoPredio()) {
            sql.append(" from ind_auxiliar_indicador aux");
            sql.append(" inner join ind_auxiliar_consultas n on n.id_predio = aux.id_predio and n.periodo = aux.periodo and n.id_tnt = aux.id_tnt");
            tablaAS="n.";
        } else {
            //(select distinct id_tnt,periodo,id_nivel0,id_nivel1,nombre_nivel1 from ind_auxiliar_consultas where id_tnt = 1 and periodo = 201606)
            sql.append(" from (");
            if (filtro.isAgrupamientoNivel0()) {
                sql.append("select distinct id_tnt,periodo,id_nivel0,");
                sql.append(agrupamiento.toString().replaceAll("aux.", ""));
            }
            if (filtro.isAgrupamientoNivel1()) {
                sql.append("select distinct id_tnt,periodo,id_nivel0,id_nivel1,");
                sql.append(agrupamiento.toString().replaceAll("aux.", ""));
            }
            if (filtro.isAgrupamientoNivel2()) {
                sql.append("select distinct id_tnt,periodo,id_nivel0,id_nivel1,id_nivel2,");
                sql.append(agrupamiento.toString().replaceAll("aux.", ""));
            }
            if (filtro.isAgrupamientoNivel3()) {
                sql.append("select distinct id_tnt,periodo,id_nivel0,id_nivel1,id_nivel2,id_nivel3,");
                sql.append(agrupamiento.toString().replaceAll("aux.", ""));
            }
             if (filtro.isAgrupamientoNivel4()) {
                sql.append("select distinct id_tnt,periodo,id_nivel0,id_nivel1,id_nivel2,id_nivel3,id_nivel4,");
                sql.append(agrupamiento.toString().replaceAll("aux.", ""));
            }
             if (filtro.isAgrupamientoNivel5()) {
                sql.append("select distinct id_tnt,periodo,id_nivel0,id_nivel1,id_nivel2,id_nivel3,id_nivel4,id_nivel5,");
                sql.append(agrupamiento.toString().replaceAll("aux.", ""));
            }
             if (filtro.isAgrupamientoEstablecimiento()) {
                sql.append("select distinct id_tnt,periodo,id_nivel0,id_nivel1,id_nivel2,id_nivel3,id_nivel4,id_nivel5,id_establecimiento,");
                sql.append(agrupamiento.toString().replaceAll("aux.", ""));
            }
              if (filtro.isAgrupamientoSede()) {
                sql.append("select distinct id_tnt,periodo,id_nivel0,id_nivel1,id_nivel2,id_nivel3,id_nivel4,id_nivel5,id_establecimiento,id_sede,");
                sql.append(agrupamiento.toString().replaceAll("aux.", ""));
            }
            sql.append(" from ind_auxiliar_consultas");
            sql.append(" where id_tnt = ?id_tnt");
            sql.append(" and periodo = ?periodo");
            sql.append(" ) aux");
            tablaAS="aux.";
        }
        
        String strSQLNivelAgrupamiento = this.getSQLNivelAgrupamiento(filtro);
        String strSQLConsultarIndicador = this.getSQLConsultarIndicadorAlmacenado();

        //# de espacios según condición de piso
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) espacios_piso on espacios_piso.id_tnt = aux.id_tnt and espacios_piso.periodo = aux.periodo");
        sql.append(String.format(" and espacios_piso.cod_indicador = '%s'", ParamIndicador.IND_ESPACIOS_NUMERO_ESPACIOS_SEGUN_CONDICION_PISO));
        sql.append(" and espacios_piso.nivel_agrupamiento = ?nivel_agrupamiento");
        sql.append(" and espacios_piso.cod_valor = ?condicion");
        sql.append(" and espacios_piso.id_entidad_nivel = ").append(tablaAS).append(strSQLNivelAgrupamiento);
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) percomp_espacios_piso on percomp_espacios_piso.id_tnt = aux.id_tnt and percomp_espacios_piso.periodo = ?periodo_comparacion");
        sql.append(String.format(" and percomp_espacios_piso.cod_indicador = '%s'", ParamIndicador.IND_ESPACIOS_NUMERO_ESPACIOS_SEGUN_CONDICION_PISO));
        sql.append(" and percomp_espacios_piso.nivel_agrupamiento = ?nivel_agrupamiento");
        sql.append(" and percomp_espacios_piso.cod_valor = ?condicion");
        sql.append(" and percomp_espacios_piso.id_entidad_nivel = ").append(tablaAS).append(strSQLNivelAgrupamiento); 

        // Área (m2), según condición piso
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) area_piso on area_piso.id_tnt = aux.id_tnt and area_piso.periodo = aux.periodo");
        sql.append(String.format(" and area_piso.cod_indicador = '%s'", ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_PISO));
        sql.append(" and area_piso.nivel_agrupamiento = ?nivel_agrupamiento");
        sql.append(" and area_piso.cod_valor = ?condicion");
        sql.append(" and area_piso.id_entidad_nivel = ").append(tablaAS).append(strSQLNivelAgrupamiento);
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) percomp_area_piso on percomp_area_piso.id_tnt = aux.id_tnt and percomp_area_piso.periodo = ?periodo_comparacion");
        sql.append(String.format(" and percomp_area_piso.cod_indicador = '%s'", ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_PISO));
        sql.append(" and percomp_area_piso.nivel_agrupamiento = ?nivel_agrupamiento");
        sql.append(" and percomp_area_piso.cod_valor = ?condicion");
        sql.append(" and percomp_area_piso.id_entidad_nivel = ").append(tablaAS).append(strSQLNivelAgrupamiento);

        //# de espacios según condición de muro
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) espacios_muro on espacios_muro.id_tnt = aux.id_tnt and espacios_muro.periodo = aux.periodo");
        sql.append(String.format(" and espacios_muro.cod_indicador = '%s'", ParamIndicador.IND_ESPACIOS_NUMERO_ESPACIOS_SEGUN_CONDICION_MURO));
        sql.append(" and espacios_muro.nivel_agrupamiento = ?nivel_agrupamiento");
        sql.append(" and espacios_muro.cod_valor = ?condicion");
        sql.append(" and espacios_muro.id_entidad_nivel = ").append(tablaAS).append(strSQLNivelAgrupamiento);
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) percomp_espacios_muro on percomp_espacios_muro.id_tnt = aux.id_tnt and percomp_espacios_muro.periodo = ?periodo_comparacion");
        sql.append(String.format(" and percomp_espacios_muro.cod_indicador = '%s'", ParamIndicador.IND_ESPACIOS_NUMERO_ESPACIOS_SEGUN_CONDICION_MURO));
        sql.append(" and percomp_espacios_muro.nivel_agrupamiento = ?nivel_agrupamiento");
        sql.append(" and percomp_espacios_muro.cod_valor = ?condicion");
        sql.append(" and percomp_espacios_muro.id_entidad_nivel = ").append(tablaAS).append(strSQLNivelAgrupamiento);
        
        // Área (m2), según condición de muro
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) area_muro on area_muro.id_tnt = aux.id_tnt and area_muro.periodo = aux.periodo");
        sql.append(String.format(" and area_muro.cod_indicador = '%s'", ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_MURO));
        sql.append(" and area_muro.nivel_agrupamiento = ?nivel_agrupamiento");
        sql.append(" and area_muro.cod_valor = ?condicion");
        sql.append(" and area_muro.id_entidad_nivel = ").append(tablaAS).append(strSQLNivelAgrupamiento);
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) percomp_area_muro on percomp_area_muro.id_tnt = aux.id_tnt and percomp_area_muro.periodo = ?periodo_comparacion");
        sql.append(String.format(" and percomp_area_muro.cod_indicador = '%s'", ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_MURO));
        sql.append(" and percomp_area_muro.nivel_agrupamiento = ?nivel_agrupamiento");
        sql.append(" and percomp_area_muro.cod_valor = ?condicion");
        sql.append(" and percomp_area_muro.id_entidad_nivel = ").append(tablaAS).append(strSQLNivelAgrupamiento);

        //# de espacios según condición de acabado muro
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) espacios_acabado_muro on espacios_acabado_muro.id_tnt = aux.id_tnt and espacios_acabado_muro.periodo = aux.periodo");
        sql.append(String.format(" and espacios_acabado_muro.cod_indicador = '%s'", ParamIndicador.IND_ESPACIOS_NUMERO_ESPACIOS_SEGUN_CONDICION_ACABADO_MURO));
        sql.append(" and espacios_acabado_muro.nivel_agrupamiento = ?nivel_agrupamiento");
        sql.append(" and espacios_acabado_muro.cod_valor = ?condicion");
        sql.append(" and espacios_acabado_muro.id_entidad_nivel = ").append(tablaAS).append(strSQLNivelAgrupamiento);
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) percomp_esp_acabado_muro on percomp_esp_acabado_muro.id_tnt = aux.id_tnt and percomp_esp_acabado_muro.periodo = ?periodo_comparacion");
        sql.append(String.format(" and percomp_esp_acabado_muro.cod_indicador = '%s'", ParamIndicador.IND_ESPACIOS_NUMERO_ESPACIOS_SEGUN_CONDICION_ACABADO_MURO));
        sql.append(" and percomp_esp_acabado_muro.nivel_agrupamiento = ?nivel_agrupamiento");
        sql.append(" and percomp_esp_acabado_muro.cod_valor = ?condicion");
        sql.append(" and percomp_esp_acabado_muro.id_entidad_nivel = ").append(tablaAS).append(strSQLNivelAgrupamiento);
        
        // Área (m2), según condición de acabado muro
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) area_acabado_muro on area_acabado_muro.id_tnt = aux.id_tnt and area_acabado_muro.periodo = aux.periodo");
        sql.append(String.format(" and area_acabado_muro.cod_indicador = '%s'", ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_ACABADO_MURO));
        sql.append(" and area_acabado_muro.nivel_agrupamiento = ?nivel_agrupamiento");
        sql.append(" and area_acabado_muro.cod_valor = ?condicion");
        sql.append(" and area_acabado_muro.id_entidad_nivel = ").append(tablaAS).append(strSQLNivelAgrupamiento);
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) percomp_area_acabado_muro on percomp_area_acabado_muro.id_tnt = aux.id_tnt and percomp_area_acabado_muro.periodo = ?periodo_comparacion");
        sql.append(String.format(" and percomp_area_acabado_muro.cod_indicador = '%s'", ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_ACABADO_MURO));
        sql.append(" and percomp_area_acabado_muro.nivel_agrupamiento = ?nivel_agrupamiento");
        sql.append(" and percomp_area_acabado_muro.cod_valor = ?condicion");
        sql.append(" and percomp_area_acabado_muro.id_entidad_nivel = ").append(tablaAS).append(strSQLNivelAgrupamiento);
        
        //# de espacios según condición de ventana
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) espacios_ventana on espacios_ventana.id_tnt = aux.id_tnt and espacios_ventana.periodo = aux.periodo");
        sql.append(String.format(" and espacios_ventana.cod_indicador = '%s'", ParamIndicador.IND_ESPACIOS_NUMERO_ESPACIOS_SEGUN_CONDICION_VENTANA));
        sql.append(" and espacios_ventana.nivel_agrupamiento = ?nivel_agrupamiento");
        sql.append(" and espacios_ventana.cod_valor = ?condicion");
        sql.append(" and espacios_ventana.id_entidad_nivel = ").append(tablaAS).append(strSQLNivelAgrupamiento);
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) percomp_espacios_ventana on percomp_espacios_ventana.id_tnt = aux.id_tnt and percomp_espacios_ventana.periodo = ?periodo_comparacion");
        sql.append(String.format(" and percomp_espacios_ventana.cod_indicador = '%s'", ParamIndicador.IND_ESPACIOS_NUMERO_ESPACIOS_SEGUN_CONDICION_VENTANA));
        sql.append(" and percomp_espacios_ventana.nivel_agrupamiento = ?nivel_agrupamiento");
        sql.append(" and percomp_espacios_ventana.cod_valor = ?condicion");
        sql.append(" and percomp_espacios_ventana.id_entidad_nivel = ").append(tablaAS).append(strSQLNivelAgrupamiento);
                
        //# de espacios según condición de puerta
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) espacios_puerta on espacios_puerta.id_tnt = aux.id_tnt and espacios_puerta.periodo = aux.periodo");
        sql.append(String.format(" and espacios_puerta.cod_indicador = '%s'", ParamIndicador.IND_ESPACIOS_NUMERO_ESPACIOS_SEGUN_CONDICION_PUERTA));
        sql.append(" and espacios_puerta.nivel_agrupamiento = ?nivel_agrupamiento");
        sql.append(" and espacios_puerta.cod_valor = ?condicion");
        sql.append(" and espacios_puerta.id_entidad_nivel = ").append(tablaAS).append(strSQLNivelAgrupamiento);
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) percomp_espacios_puerta on percomp_espacios_puerta.id_tnt = aux.id_tnt and percomp_espacios_puerta.periodo = ?periodo_comparacion");
        sql.append(String.format(" and percomp_espacios_puerta.cod_indicador = '%s'", ParamIndicador.IND_ESPACIOS_NUMERO_ESPACIOS_SEGUN_CONDICION_PUERTA));
        sql.append(" and percomp_espacios_puerta.nivel_agrupamiento = ?nivel_agrupamiento");
        sql.append(" and percomp_espacios_puerta.cod_valor = ?condicion");
        sql.append(" and percomp_espacios_puerta.id_entidad_nivel = ").append(tablaAS).append(strSQLNivelAgrupamiento);
                        
        //# de espacios según condición de techo
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) espacios_techo on espacios_techo.id_tnt = aux.id_tnt and espacios_techo.periodo = aux.periodo");
        sql.append(String.format(" and espacios_techo.cod_indicador = '%s'", ParamIndicador.IND_ESPACIOS_NUMERO_ESPACIOS_SEGUN_CONDICION_TECHO));
        sql.append(" and espacios_techo.nivel_agrupamiento = ?nivel_agrupamiento");
        sql.append(" and espacios_techo.cod_valor = ?condicion");
        sql.append(" and espacios_techo.id_entidad_nivel = ").append(tablaAS).append(strSQLNivelAgrupamiento);
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) percomp_espacios_techo on percomp_espacios_techo.id_tnt = aux.id_tnt and percomp_espacios_techo.periodo = ?periodo_comparacion");
        sql.append(String.format(" and percomp_espacios_techo.cod_indicador = '%s'", ParamIndicador.IND_ESPACIOS_NUMERO_ESPACIOS_SEGUN_CONDICION_TECHO));
        sql.append(" and percomp_espacios_techo.nivel_agrupamiento = ?nivel_agrupamiento");
        sql.append(" and percomp_espacios_techo.cod_valor = ?condicion");
        sql.append(" and percomp_espacios_techo.id_entidad_nivel = ").append(tablaAS).append(strSQLNivelAgrupamiento);
        
        // Área (m2), según condición techo
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) area_techo on area_techo.id_tnt = aux.id_tnt and area_techo.periodo = aux.periodo");
        sql.append(String.format(" and area_techo.cod_indicador = '%s'", ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_TECHO));
        sql.append(" and area_techo.nivel_agrupamiento = ?nivel_agrupamiento");
        sql.append(" and area_techo.cod_valor = ?condicion");
        sql.append(" and area_techo.id_entidad_nivel = ").append(tablaAS).append(strSQLNivelAgrupamiento);
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) percomp_area_techo on percomp_area_techo.id_tnt = aux.id_tnt and percomp_area_techo.periodo = ?periodo_comparacion");
        sql.append(String.format(" and percomp_area_techo.cod_indicador = '%s'", ParamIndicador.IND_ESPACIOS_AREA_SEGUN_CONDICION_TECHO));
        sql.append(" and percomp_area_techo.nivel_agrupamiento = ?nivel_agrupamiento");
        sql.append(" and percomp_area_techo.cod_valor = ?condicion");
        sql.append(" and percomp_area_techo.id_entidad_nivel = ").append(tablaAS).append(strSQLNivelAgrupamiento);
        
        //Area horizontal
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) area_horizontal on area_horizontal.id_tnt = aux.id_tnt and area_horizontal.periodo = aux.periodo");
        sql.append(String.format(" and area_horizontal.cod_indicador = '%s'", ParamIndicador.IND_ESPACIOS_AREA_TOTAL_HORIZONTAL));
        sql.append(" and area_horizontal.nivel_agrupamiento = ?nivel_agrupamiento");
        sql.append(" and area_horizontal.id_entidad_nivel = ").append(tablaAS).append(strSQLNivelAgrupamiento);
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) percomp_area_horizontal on percomp_area_horizontal.id_tnt = aux.id_tnt and percomp_area_horizontal.periodo = ?periodo_comparacion");
        sql.append(String.format(" and percomp_area_horizontal.cod_indicador = '%s'", ParamIndicador.IND_ESPACIOS_AREA_TOTAL_HORIZONTAL));
        sql.append(" and percomp_area_horizontal.nivel_agrupamiento = ?nivel_agrupamiento");
        sql.append(" and percomp_area_horizontal.id_entidad_nivel = ").append(tablaAS).append(strSQLNivelAgrupamiento);
        
        //Area total para techo (Cielo Rasos)
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) area_total_techo on area_total_techo.id_tnt = aux.id_tnt and area_total_techo.periodo = aux.periodo");
        sql.append(String.format(" and area_total_techo.cod_indicador = '%s'", ParamIndicador.IND_ESPACIOS_AREA_TOTAL_TECHO));
        sql.append(" and area_total_techo.nivel_agrupamiento = ?nivel_agrupamiento");
        sql.append(" and area_total_techo.id_entidad_nivel = ").append(tablaAS).append(strSQLNivelAgrupamiento);
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) percomp_area_total_techo on percomp_area_total_techo.id_tnt = aux.id_tnt and percomp_area_total_techo.periodo = ?periodo_comparacion");
        sql.append(String.format(" and percomp_area_total_techo.cod_indicador = '%s'", ParamIndicador.IND_ESPACIOS_AREA_TOTAL_TECHO));
        sql.append(" and percomp_area_total_techo.nivel_agrupamiento = ?nivel_agrupamiento");
        sql.append(" and percomp_area_total_techo.id_entidad_nivel = ").append(tablaAS).append(strSQLNivelAgrupamiento);
        
        //Area vertical
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) area_vertical on area_vertical.id_tnt = aux.id_tnt and area_vertical.periodo = aux.periodo");
        sql.append(String.format(" and area_vertical.cod_indicador = '%s'", ParamIndicador.IND_ESPACIOS_AREA_TOTAL_VERTICAL));
        sql.append(" and area_vertical.nivel_agrupamiento = ?nivel_agrupamiento");
        sql.append(" and area_vertical.cod_valor = ?condicion");
        sql.append(" and area_vertical.id_entidad_nivel = ").append(tablaAS).append(strSQLNivelAgrupamiento);
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) percomp_area_vertical on percomp_area_vertical.id_tnt = aux.id_tnt and percomp_area_vertical.periodo = ?periodo_comparacion");
        sql.append(String.format(" and percomp_area_vertical.cod_indicador = '%s'", ParamIndicador.IND_ESPACIOS_AREA_TOTAL_VERTICAL));
        sql.append(" and percomp_area_vertical.nivel_agrupamiento = ?nivel_agrupamiento");
        sql.append(" and percomp_area_vertical.cod_valor = ?condicion");
        sql.append(" and percomp_area_vertical.id_entidad_nivel = ").append(tablaAS).append(strSQLNivelAgrupamiento);
        
        // total espacios con puerta
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) total_espacios_con_puerta on total_espacios_con_puerta.id_tnt = aux.id_tnt and total_espacios_con_puerta.periodo = aux.periodo");
        sql.append(String.format(" and total_espacios_con_puerta.cod_indicador = '%s'", ParamIndicador.IND_ESPACIOS_TOTAL_ESPACIOS_CON_PUERTA));
        sql.append(" and total_espacios_con_puerta.nivel_agrupamiento = ?nivel_agrupamiento");
        sql.append(" and total_espacios_con_puerta.id_entidad_nivel = ").append(tablaAS).append(strSQLNivelAgrupamiento);
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) percomp_tot_esp_con_puerta on percomp_tot_esp_con_puerta.id_tnt = aux.id_tnt and percomp_tot_esp_con_puerta.periodo = ?periodo_comparacion");
        sql.append(String.format(" and percomp_tot_esp_con_puerta.cod_indicador = '%s'", ParamIndicador.IND_ESPACIOS_TOTAL_ESPACIOS_CON_PUERTA));
        sql.append(" and percomp_tot_esp_con_puerta.nivel_agrupamiento = ?nivel_agrupamiento");
        sql.append(" and percomp_tot_esp_con_puerta.id_entidad_nivel = ").append(tablaAS).append(strSQLNivelAgrupamiento);
        
        // total espacios con ventana
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) total_espacios_con_ventana on total_espacios_con_ventana.id_tnt = aux.id_tnt and total_espacios_con_ventana.periodo = aux.periodo");
        sql.append(String.format(" and total_espacios_con_ventana.cod_indicador = '%s'", ParamIndicador.IND_ESPACIOS_TOTAL_ESPACIOS_CON_VENTANA));
        sql.append(" and total_espacios_con_ventana.nivel_agrupamiento = ?nivel_agrupamiento");
        sql.append(" and total_espacios_con_ventana.cod_valor = ?condicion");
        sql.append(" and total_espacios_con_ventana.id_entidad_nivel = ").append(tablaAS).append(strSQLNivelAgrupamiento);
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) percomp_tot_esp_con_ventana on percomp_tot_esp_con_ventana.id_tnt = aux.id_tnt and percomp_tot_esp_con_ventana.periodo = ?periodo_comparacion");
        sql.append(String.format(" and percomp_tot_esp_con_ventana.cod_indicador = '%s'", ParamIndicador.IND_ESPACIOS_TOTAL_ESPACIOS_CON_VENTANA));
        sql.append(" and percomp_tot_esp_con_ventana.nivel_agrupamiento = ?nivel_agrupamiento");
        sql.append(" and percomp_tot_esp_con_ventana.cod_valor = ?condicion");
        sql.append(" and percomp_tot_esp_con_ventana.id_entidad_nivel = ").append(tablaAS).append(strSQLNivelAgrupamiento);
        
// WHERE
        sql.append(" where aux.id_tnt = ?id_tnt and aux.periodo = ?periodo");
        if (filtro.isAgrupamientoPredio()) {
            sql.append(" and n.principal_establecimiento = 1");
            if (filtro.getIdPredio() != null) {
                sql.append(" and ").append(tablaAS).append("id_predio = ?id_predio");
            }
        }
        if (filtro.isAgrupamientoNivel1()) {
            if (filtro.getIdNivel0() != null) {
                sql.append(" and ").append(tablaAS).append("id_nivel0 = ?id_nivel0");
            }
            if (filtro.getIdNivel1() != null) {
                sql.append(" and ").append(tablaAS).append("id_nivel1 = ?id_nivel1");
            }
        }
        if (filtro.isAgrupamientoNivel2()) {
            if (filtro.getIdNivel0() != null) {
                sql.append(" and ").append(tablaAS).append("id_nivel0 = ?id_nivel0");
            }
            if (filtro.getIdNivel1() != null) {
                sql.append(" and ").append(tablaAS).append("id_nivel1 = ?id_nivel1");
            }
            if (filtro.getIdNivel2() != null) {
                sql.append(" and ").append(tablaAS).append("id_nivel2 = ?id_nivel2");
            }
        }
        if (filtro.isAgrupamientoNivel3()) {
            if (filtro.getIdNivel0() != null) {
                sql.append(" and ").append(tablaAS).append("id_nivel0 = ?id_nivel0");
            }
            if (filtro.getIdNivel1() != null) {
                sql.append(" and ").append(tablaAS).append("id_nivel1 = ?id_nivel1");
            }
            if (filtro.getIdNivel2() != null) {
                sql.append(" and ").append(tablaAS).append("id_nivel2 = ?id_nivel2");
            }
            if (filtro.getIdNivel3() != null) {
                sql.append(" and ").append(tablaAS).append("id_nivel3 = ?id_nivel3");
            }
        }
        if (filtro.isAgrupamientoNivel4()) {
            if (filtro.getIdNivel0() != null) {
                sql.append(" and ").append(tablaAS).append("id_nivel0 = ?id_nivel0");
            }
            if (filtro.getIdNivel1() != null) {
                sql.append(" and ").append(tablaAS).append("id_nivel1 = ?id_nivel1");
            }
            if (filtro.getIdNivel2() != null) {
                sql.append(" and ").append(tablaAS).append("id_nivel2 = ?id_nivel2");
            }
            if (filtro.getIdNivel3() != null) {
                sql.append(" and ").append(tablaAS).append("id_nivel3 = ?id_nivel3");
            }
            if (filtro.getIdNivel4() != null) {
                sql.append(" and ").append(tablaAS).append("id_nivel4 = ?id_nivel4");
            }
        }
        if (filtro.isAgrupamientoNivel5() || filtro.isAgrupamientoEstablecimiento() || filtro.isAgrupamientoSede()) {
            if (filtro.getIdNivel0() != null) {
                sql.append(" and ").append(tablaAS).append("id_nivel0 = ?id_nivel0");
            }
            if (filtro.getIdNivel1() != null) {
                sql.append(" and ").append(tablaAS).append("id_nivel1 = ?id_nivel1");
            }
            if (filtro.getIdNivel2() != null) {
                sql.append(" and ").append(tablaAS).append("id_nivel2 = ?id_nivel2");
            }
            if (filtro.getIdNivel3() != null) {
                sql.append(" and ").append(tablaAS).append("id_nivel3 = ?id_nivel3");
            }
            if (filtro.getIdNivel4() != null) {
                sql.append(" and ").append(tablaAS).append("id_nivel4 = ?id_nivel4");
            }
            if (filtro.getIdNivel5() != null) {
                sql.append(" and ").append(tablaAS).append("id_nivel5 = ?id_nivel5");
            }
        }
        
        // otros filtros
        if (filtro.getCodZona() != null) {
            sql.append(" and ").append(tablaAS).append("codigo_zona = ?codigo_zona");
        }
        if (filtro.getCodSector() != null) {
            sql.append(" and ").append(tablaAS).append("codigo_sector = ?codigo_sector");
        }
        
        // ORDER BY 
        //sql.append(" ORDER by ");
        //sql.append(orden.toString());

        Query query = tpm.getEm().createNativeQuery(sql.toString());
        query.setParameter("id_tnt", tpm.getCurrentTenant());
        query.setParameter("periodo", filtro.getPeriodo());
        query.setParameter("periodo_comparacion", filtro.getPeriodoComparado());
        query.setParameter("idioma", filtro.getIdioma());
        query.setParameter("id_nivel0", filtro.getIdNivel0());
        query.setParameter("nivel_agrupamiento", filtro.getAgrupamiento());
       // query.setParameter("condicion", filtro.getCalificacionCondicion().getValor());
        query.setParameter("condicion", filtro.getCalificacionCondicion());
        if (filtro.getIdNivel1() != null) {
            query.setParameter("id_nivel1", filtro.getIdNivel1());
        }
        if (filtro.getIdNivel2() != null) {
            query.setParameter("id_nivel2", filtro.getIdNivel2());
        }
        if (filtro.getIdNivel3() != null) {
            query.setParameter("id_nivel3", filtro.getIdNivel3());
        }
        if (filtro.getIdNivel4() != null) {
            query.setParameter("id_nivel4", filtro.getIdNivel4());
        }
        if (filtro.getIdNivel5() != null) {
            query.setParameter("id_nivel5", filtro.getIdNivel5());
        }
        if (filtro.getIdEstablecimiento() != null) {
            query.setParameter("id_establecimiento", filtro.getIdEstablecimiento());
        }
        if (filtro.getIdSede() != null) {
            query.setParameter("id_sede", filtro.getIdSede());
        }
        if (filtro.getIdPredio() != null) {
            query.setParameter("id_predio", filtro.getIdPredio());
        }
        // otros filtros
        if (filtro.getCodZona() != null) {
            query.setParameter("cod_zona", filtro.getCodZona());
        }
        if (filtro.getCodSector() != null) {
            query.setParameter("cod_sector", filtro.getCodSector());
        }
        
        return query.getResultList();
    }
    
    private List<Object[]> consultarEspaciosPostgre(FiltroComparadorInd filtro) {
        throw new UnsupportedOperationException("No soportado aun");
    }
    
    private List<Object[]> consultarEdificiosOracle(FiltroComparadorInd filtro) {
        //String codInstrumento = this.consultarCodInstrumentoPorPeriodo(filtro.getPeriodo());
        StringBuilder sql = new StringBuilder();
        StringBuilder orden = new StringBuilder();
//SELECT
        sql.append(" select distinct");
        if (filtro.isAgrupamientoPredio()) {
            //orden.append(" n.nombre_nivel1, n.nombre_nivel2, n.nombre_nivel3, n.nombre_nivel4, n.nombre_nivel5");
            orden.append(" n.codigo_nivel1,n.nombre_nivel1");
            orden.append(",n.codigo_nivel2,n.nombre_nivel2");
            orden.append(",n.codigo_nivel3,n.nombre_nivel3");
            orden.append(",n.codigo_nivel4,n.nombre_nivel4");
            orden.append(",n.codigo_nivel5,n.nombre_nivel5");
            
            orden.append(" ,n.codigo_establecimiento, n.nombre_establecimiento");
            orden.append(" ,n.codigo_sede, n.nombre_sede");
            orden.append(" ,n.codigo_predio, n.nombre_predio");
            //otros
            orden.append(" ,n.direccion_predio, n.propiedad_predio");
            orden.append(" ,n.nombre_zona, n.nombre_sector, n.nombre_clima");
            //etnias
            orden.append(" ,etnias.valor");
            sql.append(orden.toString());
            //indicadores            
            sql.append(", mat_pred.valor"); 
            sql.append(", percomp_mat_pred.valor"); 
            sql.append(" ,tot_estud.valor");
            sql.append(" ,percomp_tot_estud.valor");
        } else {
            if (filtro.isAgrupamientoNivel0()) {
                orden.append(" n.nombre_nivel0");
            }
            if (filtro.isAgrupamientoNivel1()) {
                //orden.append(" n.nombre_nivel1");
                orden.append(" n.codigo_nivel1,n.nombre_nivel1");
            }
            if (filtro.isAgrupamientoNivel2()) {
                //orden.append(" n.nombre_nivel1, n.nombre_nivel2");
                orden.append(" n.codigo_nivel1,n.nombre_nivel1");
                orden.append(",n.codigo_nivel2,n.nombre_nivel2");
            }
            if (filtro.isAgrupamientoNivel3()) {
                //orden.append(" n.nombre_nivel1, n.nombre_nivel2, n.nombre_nivel3");
                orden.append(" n.codigo_nivel1,n.nombre_nivel1");
                orden.append(",n.codigo_nivel2,n.nombre_nivel2");
                orden.append(",n.codigo_nivel3,n.nombre_nivel3");
            }
            if (filtro.isAgrupamientoNivel4()) {
                //orden.append(" n.nombre_nivel1, n.nombre_nivel2, n.nombre_nivel3, n.nombre_nivel4");
                orden.append(" n.codigo_nivel1,n.nombre_nivel1");
                orden.append(",n.codigo_nivel2,n.nombre_nivel2");
                orden.append(",n.codigo_nivel3,n.nombre_nivel3");
                orden.append(",n.codigo_nivel4,n.nombre_nivel4");
            }
            if (filtro.isAgrupamientoNivel5()) {
                //orden.append(" n.nombre_nivel1, n.nombre_nivel2, n.nombre_nivel3, n.nombre_nivel4, n.nombre_nivel5");
                orden.append(" n.codigo_nivel1,n.nombre_nivel1");
                orden.append(",n.codigo_nivel2,n.nombre_nivel2");
                orden.append(",n.codigo_nivel3,n.nombre_nivel3");
                orden.append(",n.codigo_nivel4,n.nombre_nivel4");
                orden.append(",n.codigo_nivel5,n.nombre_nivel5");
            }
            if (filtro.isAgrupamientoEstablecimiento()) {
                //orden.append(" n.nombre_nivel1, n.nombre_nivel2, n.nombre_nivel3, n.nombre_nivel4, n.nombre_nivel5");
                orden.append(" n.codigo_nivel1,n.nombre_nivel1");
                orden.append(",n.codigo_nivel2,n.nombre_nivel2");
                orden.append(",n.codigo_nivel3,n.nombre_nivel3");
                orden.append(",n.codigo_nivel4,n.nombre_nivel4");
                orden.append(",n.codigo_nivel5,n.nombre_nivel5");
                orden.append(" ,n.codigo_establecimiento, n.nombre_establecimiento");
            }
            if (filtro.isAgrupamientoSede()) {
                //orden.append(" n.nombre_nivel1, n.nombre_nivel2, n.nombre_nivel3, n.nombre_nivel4, n.nombre_nivel5");
                orden.append(" n.codigo_nivel1,n.nombre_nivel1");
                orden.append(",n.codigo_nivel2,n.nombre_nivel2");
                orden.append(",n.codigo_nivel3,n.nombre_nivel3");
                orden.append(",n.codigo_nivel4,n.nombre_nivel4");
                orden.append(",n.codigo_nivel5,n.nombre_nivel5");
                orden.append(" ,n.codigo_establecimiento, n.nombre_establecimiento");
                orden.append(" ,n.codigo_sede, n.nombre_sede");
            }
            // indicadores
            sql.append(orden.toString());
            if (filtro.getCondMaterialidadPredio() != null) {
                sql.append(", mat_pred.valor total_predios_cond_mat");
                sql.append(", percomp_mat_pred.valor ");
                sql.append(", DIVIDIR(mat_pred.valor,CASE WHEN total_predio.valor IS NULL OR total_predio.valor='0' THEN 1 ELSE CHAR_TO_DOUBLE(total_predio.valor) END) * 100 ");
                sql.append(", DIVIDIR(percomp_.valor,CASE WHEN percomp_total_predio.valor IS NULL OR percomp_total_predio.valor='0' THEN 1 ELSE CHAR_TO_DOUBLE(percomp_total_predio.valor) END) * 100 ");
            }
            sql.append(", total_predio.valor total_predios");
            sql.append(", percomp_total_predio.valor ");
            sql.append(", mat_cri.valor total_material_criticos");
            sql.append(", percomp_mat_cri.valor ");
            sql.append(", DIVIDIR(mat_cri.valor,CASE WHEN total_edificios.valor IS NULL OR total_edificios.valor='0' THEN 1 ELSE CHAR_TO_DOUBLE(total_edificios.valor) END) * 100 prop_material_criticos");
            sql.append(", DIVIDIR(percomp_mat_cri.valor,CASE WHEN percomp_total_edificios.valor IS NULL OR percomp_total_edificios.valor='0' THEN 1 ELSE CHAR_TO_DOUBLE(percomp_total_edificios.valor) END) * 100 ");
            sql.append(" ,tot_estud.valor");
            sql.append(" ,percomp_tot_estud.valor");
        }
// FROM 
        sql.append(" from ind_auxiliar_indicador aux");
        sql.append(" inner join ind_auxiliar_consultas n on n.id_predio = aux.id_predio and n.periodo = aux.periodo and n.id_tnt = aux.id_tnt");
        
        String strSQLNivelAgrupamiento = this.getSQLNivelAgrupamiento(filtro);
        String strSQLConsultarIndicador = this.getSQLConsultarIndicadorAlmacenado();
        
        //total de estudiantes por predio  
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) tot_estud on tot_estud.id_tnt = aux.id_tnt and tot_estud.periodo = aux.periodo");
        sql.append(String.format(" and tot_estud.cod_indicador = '%s'", ParamIndicador.IND_TOTAL_ESTUDIANTES));
        sql.append(" and tot_estud.nivel_agrupamiento = ?nivel_agrupamiento");
        sql.append(" and tot_estud.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
         sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) percomp_tot_estud on percomp_tot_estud.id_tnt = aux.id_tnt and percomp_tot_estud.periodo = ?periodo_comparacion");
        sql.append(String.format(" and percomp_tot_estud.cod_indicador = '%s'", ParamIndicador.IND_TOTAL_ESTUDIANTES));
        sql.append(" and percomp_tot_estud.nivel_agrupamiento = ?nivel_agrupamiento");
        sql.append(" and percomp_tot_estud.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
        
        if (filtro.isAgrupamientoPredio()) {
            //etnias
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) etnias on etnias.id_tnt = aux.id_tnt and etnias.periodo = aux.periodo");
            sql.append(String.format(" and etnias.cod_indicador = '%s'", ParamIndicador.IND_ETNIAS_PREDIO));
            sql.append(" and etnias.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and etnias.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
            
            // CondMaterialidadPredio
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) mat_pred on mat_pred.id_tnt = aux.id_tnt and mat_pred.periodo = aux.periodo");
            sql.append(String.format(" and mat_pred.cod_indicador = '%s'", ParamIndicador.IND_EDIFICIO_CONDICION_MATERIALIDAD_PREDIO));
            sql.append(" and mat_pred.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and mat_pred.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento); 
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) percomp_mat_pred on percomp_mat_pred.id_tnt = aux.id_tnt and percomp_mat_pred.periodo = ?periodo_comparacion");
            sql.append(String.format(" and percomp_mat_pred.cod_indicador = '%s'", ParamIndicador.IND_EDIFICIO_CONDICION_MATERIALIDAD_PREDIO));
            sql.append(" and percomp_mat_pred.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and percomp_mat_pred.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
        } else {           
            if (filtro.getCondMaterialidadPredio() != null) {
                //NumPredCondMaterialidad
                sql.append(" left join (");
                sql.append(strSQLConsultarIndicador);
                sql.append(" ) mat_pred on mat_pred.id_tnt = aux.id_tnt and mat_pred.periodo = aux.periodo");
                sql.append(String.format(" and mat_pred.cod_indicador = '%s'", ParamIndicador.IND_EDIFICIO_NUM_PREDIOS_CONDICION_MATERIALIDAD));
                sql.append(" and mat_pred.nivel_agrupamiento = ?nivel_agrupamiento");
                sql.append(" and mat_pred.cod_valor = ?cond_mat_pred");
                sql.append(" and mat_pred.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
                sql.append(" left join (");
                sql.append(strSQLConsultarIndicador);
                sql.append(" ) percomp_mat_pred on percomp_mat_pred.id_tnt = aux.id_tnt and percomp_mat_pred.periodo = ?periodo_comparacion");
                sql.append(String.format(" and percomp_mat_pred.cod_indicador = '%s'", ParamIndicador.IND_EDIFICIO_NUM_PREDIOS_CONDICION_MATERIALIDAD));
                sql.append(" and percomp_mat_pred.nivel_agrupamiento = ?nivel_agrupamiento");
                sql.append(" and percomp_mat_pred.cod_valor = ?cond_percomp_mat_pred");
                sql.append(" and percomp_mat_pred.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
            }
            //TotalPredios
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) total_predio on total_predio.id_tnt = aux.id_tnt and total_predio.periodo = aux.periodo");
            sql.append(String.format(" and total_predio.cod_indicador = '%s'", ParamIndicador.IND_TOTAL_PREDIOS));
            sql.append(" and total_predio.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and total_predio.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) percomp_total_predio on percomp_total_predio.id_tnt = aux.id_tnt and percomp_total_predio.periodo = ?periodo_comparacion");
            sql.append(String.format(" and percomp_total_predio.cod_indicador = '%s'", ParamIndicador.IND_TOTAL_PREDIOS));
            sql.append(" and percomp_total_predio.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and percomp_total_predio.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);

            //NumMaterialesCondCriticaPredio
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) mat_cri on mat_cri.id_tnt = aux.id_tnt and mat_cri.periodo = aux.periodo");
            sql.append(String.format(" and mat_cri.cod_indicador = '%s'", ParamIndicador.IND_EDIFICIO_NUM_MATERIALES_CONDICION_CRITICA_PREDIO));
            sql.append(" and mat_cri.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and mat_cri.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento); 
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) percomp_mat_cri on percomp_mat_cri.id_tnt = aux.id_tnt and percomp_mat_cri.periodo = ?periodo_comparacion");
            sql.append(String.format(" and percomp_mat_cri.cod_indicador = '%s'", ParamIndicador.IND_EDIFICIO_NUM_MATERIALES_CONDICION_CRITICA_PREDIO));
            sql.append(" and percomp_mat_cri.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and percomp_mat_cri.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento); 
            
            //total_edificios
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) total_edificios on total_edificios.id_tnt = aux.id_tnt and total_edificios.periodo = aux.periodo");
            sql.append(String.format(" and total_edificios.cod_indicador = '%s'", ParamIndicador.IND_TOTAL_EDIFICIOS));
            sql.append(" and total_edificios.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and total_edificios.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento); 
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) percomp_total_edificios on percomp_total_edificios.id_tnt = aux.id_tnt and percomp_total_edificios.periodo = ?periodo_comparacion");
            sql.append(String.format(" and percomp_total_edificios.cod_indicador = '%s'", ParamIndicador.IND_TOTAL_EDIFICIOS));
            sql.append(" and percomp_total_edificios.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and percomp_total_edificios.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
        }
// WHERE
        sql.append(" where aux.id_tnt = ?id_tnt and aux.periodo = ?periodo");
        if (filtro.isAgrupamientoPredio()) {
            sql.append(" and n.principal_establecimiento = 1");
        }
        if (filtro.getIdNivel0() != null) {
            sql.append(" and n.id_nivel0 = ?id_nivel0");
        }
        if (filtro.getIdNivel1() != null) {
            sql.append(" and n.id_nivel1 = ?id_nivel1");
        }
        if (filtro.getIdNivel2() != null) {
            sql.append(" and n.id_nivel2 = ?id_nivel2");
        }
        if (filtro.getIdNivel3() != null) {
            sql.append(" and n.id_nivel3 = ?id_nivel3");
        }
        if (filtro.getIdNivel4() != null) {
            sql.append(" and n.id_nivel4 = ?id_nivel4");
        }
        if (filtro.getIdNivel5() != null) {
            sql.append(" and n.id_nivel5 = ?id_nivel5");
        }
        if (filtro.getIdEstablecimiento() != null) {
            sql.append(" and n.id_establecimiento = ?id_establecimiento");
        }
        if (filtro.getIdSede() != null) {
            sql.append(" and n.id_sede = ?id_sede");
        }
        if (filtro.getIdPredio() != null) {
            sql.append(" and n.id_predio = ?id_predio");
        }
        // otros filtros
        if (filtro.getCodZona() != null) {
            sql.append(" and n.codigo_zona = ?cod_zona");
        }
        if (filtro.getCodSector() != null) {
            sql.append(" and n.codigo_sector = ?cod_sector");
        }
        
        // ORDER BY 
        //sql.append(" ORDER by ");
        //sql.append(orden.toString());

        Query query = tpm.getEm().createNativeQuery(sql.toString());
        query.setParameter("id_tnt", tpm.getCurrentTenant());
        query.setParameter("periodo", filtro.getPeriodo());
        query.setParameter("periodo_comparacion", filtro.getPeriodoComparado());
        query.setParameter("idioma", filtro.getIdioma());
        query.setParameter("id_nivel0", filtro.getIdNivel0());
        query.setParameter("nivel_agrupamiento", filtro.getAgrupamiento());
        if (filtro.getIdNivel1() != null) {
            query.setParameter("id_nivel1", filtro.getIdNivel1());
        }
        if (filtro.getIdNivel2() != null) {
            query.setParameter("id_nivel2", filtro.getIdNivel2());
        }
        if (filtro.getIdNivel3() != null) {
            query.setParameter("id_nivel3", filtro.getIdNivel3());
        }
        if (filtro.getIdNivel4() != null) {
            query.setParameter("id_nivel4", filtro.getIdNivel4());
        }
        if (filtro.getIdNivel5() != null) {
            query.setParameter("id_nivel5", filtro.getIdNivel5());
        }
        if (filtro.getIdEstablecimiento() != null) {
            query.setParameter("id_establecimiento", filtro.getIdEstablecimiento());
        }
        if (filtro.getIdSede() != null) {
            query.setParameter("id_sede", filtro.getIdSede());
        }
        if (filtro.getIdPredio() != null) {
            query.setParameter("id_predio", filtro.getIdPredio());
        }
        // otros filtros
        if (filtro.getCodZona() != null) {
            query.setParameter("cod_zona", filtro.getCodZona());
        }
        if (filtro.getCodSector() != null) {
            query.setParameter("cod_sector", filtro.getCodSector());
        }
        
        return query.getResultList();
    }

    private List<Object[]> consultarEdificiosPostgre(FiltroComparadorInd filtro) {
        throw new UnsupportedOperationException("No soportado aun");
    }
    
     private List<Object[]> consultarRedistribucionIndicadoresOracle(FiltroComparadorInd filtro) {
        StringBuilder sql = new StringBuilder();
        StringBuilder agrupamiento = new StringBuilder();
        //SELECT
        sql.append(" select ");
        if (filtro.isAgrupamientoPredio()) {
            //agrupamiento.append(" n.nombre_nivel1, n.nombre_nivel2, n.nombre_nivel3, n.nombre_nivel4, n.nombre_nivel5");
            
            agrupamiento.append(" n.codigo_nivel1,n.nombre_nivel1");
            agrupamiento.append(",n.codigo_nivel2,n.nombre_nivel2");
            agrupamiento.append(",n.codigo_nivel3,n.nombre_nivel3");
            agrupamiento.append(",n.codigo_nivel4,n.nombre_nivel4");
            agrupamiento.append(",n.codigo_nivel5,n.nombre_nivel5");
            
            agrupamiento.append(" ,n.codigo_establecimiento, n.nombre_establecimiento");
            agrupamiento.append(" ,n.codigo_sede, n.nombre_sede");
            agrupamiento.append(" ,n.codigo_predio, n.nombre_predio");
            // datos informativos
            agrupamiento.append(" ,n.direccion_predio, n.propiedad_predio");
            agrupamiento.append(" ,n.nombre_zona, n.nombre_sector, n.nombre_clima");

            // indicadores            
            agrupamiento.append(" ,etnias.valor");           
            // utilizacion
            agrupamiento.append(" ,util_total.valor");
            // total cupos
            agrupamiento.append(" ,round(char_to_double(cupo_total.valor))");

            sql.append(agrupamiento.toString());
        } else {
            if (filtro.isAgrupamientoNivel0()) {
                agrupamiento.append(" n.nombre_nivel0");
            }
            if (filtro.isAgrupamientoNivel1()) {
                //agrupamiento.append(" n.nombre_nivel1");
                agrupamiento.append(" n.codigo_nivel1,n.nombre_nivel1");
            }
            if (filtro.isAgrupamientoNivel2()) {
                //agrupamiento.append(" n.nombre_nivel1, n.nombre_nivel2");
                agrupamiento.append(" n.codigo_nivel1,n.nombre_nivel1");
                agrupamiento.append(",n.codigo_nivel2,n.nombre_nivel2");
            }
            if (filtro.isAgrupamientoNivel3()) {
                //agrupamiento.append(" n.nombre_nivel1, n.nombre_nivel2, n.nombre_nivel3");
                agrupamiento.append(" n.codigo_nivel1,n.nombre_nivel1");
                agrupamiento.append(",n.codigo_nivel2,n.nombre_nivel2");
                agrupamiento.append(",n.codigo_nivel3,n.nombre_nivel3");
            }
            if (filtro.isAgrupamientoNivel4()) {
                //agrupamiento.append(" n.nombre_nivel1, n.nombre_nivel2, n.nombre_nivel3, n.nombre_nivel4");
                agrupamiento.append(" n.codigo_nivel1,n.nombre_nivel1");
                agrupamiento.append(",n.codigo_nivel2,n.nombre_nivel2");
                agrupamiento.append(",n.codigo_nivel3,n.nombre_nivel3");
                agrupamiento.append(",n.codigo_nivel4,n.nombre_nivel4");
            }
            if (filtro.isAgrupamientoNivel5()) {
                //agrupamiento.append(" n.nombre_nivel1, n.nombre_nivel2, n.nombre_nivel3, n.nombre_nivel4, n.nombre_nivel5");
                agrupamiento.append(" n.codigo_nivel1,n.nombre_nivel1");
                agrupamiento.append(",n.codigo_nivel2,n.nombre_nivel2");
                agrupamiento.append(",n.codigo_nivel3,n.nombre_nivel3");
                agrupamiento.append(",n.codigo_nivel4,n.nombre_nivel4");
                agrupamiento.append(",n.codigo_nivel5,n.nombre_nivel5");
            }
            if (filtro.isAgrupamientoEstablecimiento()) {
                //agrupamiento.append(" n.nombre_nivel1, n.nombre_nivel2, n.nombre_nivel3, n.nombre_nivel4, n.nombre_nivel5");
                agrupamiento.append(" n.codigo_nivel1,n.nombre_nivel1");
                agrupamiento.append(",n.codigo_nivel2,n.nombre_nivel2");
                agrupamiento.append(",n.codigo_nivel3,n.nombre_nivel3");
                agrupamiento.append(",n.codigo_nivel4,n.nombre_nivel4");
                agrupamiento.append(",n.codigo_nivel5,n.nombre_nivel5");
                agrupamiento.append(" ,n.codigo_establecimiento, n.nombre_establecimiento");
            }
            if (filtro.isAgrupamientoSede()) {
                //agrupamiento.append(" n.nombre_nivel1, n.nombre_nivel2, n.nombre_nivel3, n.nombre_nivel4, n.nombre_nivel5");
                agrupamiento.append(" n.codigo_nivel1,n.nombre_nivel1");
                agrupamiento.append(",n.codigo_nivel2,n.nombre_nivel2");
                agrupamiento.append(",n.codigo_nivel3,n.nombre_nivel3");
                agrupamiento.append(",n.codigo_nivel4,n.nombre_nivel4");
                agrupamiento.append(",n.codigo_nivel5,n.nombre_nivel5");
                agrupamiento.append(" ,n.codigo_establecimiento, n.nombre_establecimiento");
                agrupamiento.append(" ,n.codigo_sede, n.nombre_sede");
            }
            // indicadores
            // saturacion
            agrupamiento.append(" ,saturado.valor");
            agrupamiento.append(" ,sub_utilizado.valor");
            // utilizacion
            agrupamiento.append(" ,util_total.valor");
            // total cupos
            agrupamiento.append(" ,round(char_to_double(cupo_total.valor),2)");
           
            sql.append(agrupamiento.toString());
        }
// FROM 
        sql.append(" from ind_auxiliar_indicador aux");
        sql.append(" inner join ind_auxiliar_consultas n on n.id_predio = aux.id_predio and n.periodo = aux.periodo and n.id_tnt = aux.id_tnt");

        String strSQLNivelAgrupamiento = this.getSQLNivelAgrupamiento(filtro);
        String strSQLConsultarIndicador = this.getSQLConsultarIndicadorAlmacenado();

        //  cupos total
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) cupo_total on cupo_total.id_tnt = aux.id_tnt and cupo_total.periodo = aux.periodo");
        sql.append(String.format(" and cupo_total.cod_indicador = '%s'", ParamIndicador.IND_REDISTRUBUCION_CUPOS_TOTAL));
        sql.append(" and cupo_total.nivel_agrupamiento = ?nivel_agrupamiento");
        sql.append(" and cupo_total.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);

        // utilizacion total
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) util_total on util_total.id_tnt = aux.id_tnt and util_total.periodo = aux.periodo");
        sql.append(String.format(" and util_total.cod_indicador = '%s'", ParamIndicador.IND_REDISTRUBUCION_UTILIZACION_ESTADAR_TOTAL));
        sql.append(" and util_total.nivel_agrupamiento = ?nivel_agrupamiento");
        sql.append(" and util_total.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);

        if (filtro.isAgrupamientoPredio()) {
            // etnias
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) etnias on etnias.id_tnt = aux.id_tnt and etnias.periodo = aux.periodo");
            sql.append(String.format(" and etnias.cod_indicador = '%s'", ParamIndicador.IND_ETNIAS_PREDIO));
            sql.append(" and etnias.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and etnias.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
        } else {
            // predios saturados
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) saturado on saturado.id_tnt = aux.id_tnt and saturado.periodo = aux.periodo");
            sql.append(String.format(" and saturado.cod_indicador = '%s'", ParamIndicador.IND_REDISTRUBUCION_PREDIOS_SATURADOS));
            sql.append(" and saturado.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and saturado.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);

            // predios sub utilizado
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) sub_utilizado on sub_utilizado.id_tnt = aux.id_tnt and sub_utilizado.periodo = aux.periodo");
            sql.append(String.format(" and sub_utilizado.cod_indicador = '%s'", ParamIndicador.IND_REDISTRUBUCION_PREDIOS_SUBUTILIZADOS));
            sql.append(" and sub_utilizado.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and sub_utilizado.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
        }

// WHERE
        sql.append(" where aux.id_tnt = ?id_tnt and aux.periodo = ?periodo");
        if (filtro.getIdNivel0() != null) {
            sql.append(" and n.id_nivel0 = ?id_nivel0");
        }
        if (filtro.getIdNivel1() != null) {
            sql.append(" and n.id_nivel1 = ?id_nivel1");
        }
        if (filtro.getIdNivel2() != null) {
            sql.append(" and n.id_nivel2 = ?id_nivel2");
        }
        if (filtro.getIdNivel3() != null) {
            sql.append(" and n.id_nivel3 = ?id_nivel3");
        }
        if (filtro.getIdNivel4() != null) {
            sql.append(" and n.id_nivel4 = ?id_nivel4");
        }
        if (filtro.getIdNivel5() != null) {
            sql.append(" and n.id_nivel5 = ?id_nivel5");
        }
        if (filtro.getIdEstablecimiento() != null) {
            sql.append(" and n.id_establecimiento = ?id_establecimiento");
        }
        if (filtro.getIdSede() != null) {
            sql.append(" and n.id_sede = ?id_sede");
        }
        if (filtro.getIdPredio() != null) {
            sql.append(" and n.id_predio = ?id_predio");
        }
        // GROUP BY 
        sql.append(" group by ");
        sql.append(agrupamiento.toString());
        // ORDER BY
        sql.append(" order by ");
        sql.append(agrupamiento.toString());

        Query query = tpm.getEm().createNativeQuery(sql.toString());
        query.setParameter("id_tnt", tpm.getCurrentTenant());
        query.setParameter("periodo", filtro.getPeriodo());
        query.setParameter("periodo_comparacion", filtro.getPeriodoComparado());
        query.setParameter("idioma", filtro.getIdioma());
        query.setParameter("id_nivel0", filtro.getIdNivel0());
        query.setParameter("nivel_agrupamiento", filtro.getAgrupamiento());

        if (filtro.getIdNivel1() != null) {
            query.setParameter("id_nivel1", filtro.getIdNivel1());
        }
        if (filtro.getIdNivel2() != null) {
            query.setParameter("id_nivel2", filtro.getIdNivel2());
        }
        if (filtro.getIdNivel3() != null) {
            query.setParameter("id_nivel3", filtro.getIdNivel3());
        }
        if (filtro.getIdNivel4() != null) {
            query.setParameter("id_nivel4", filtro.getIdNivel4());
        }
        if (filtro.getIdNivel5() != null) {
            query.setParameter("id_nivel5", filtro.getIdNivel5());
        }
        if (filtro.getIdEstablecimiento() != null) {
            query.setParameter("id_establecimiento", filtro.getIdEstablecimiento());
        }
        if (filtro.getIdSede() != null) {
            query.setParameter("id_sede", filtro.getIdSede());
        }
        if (filtro.getIdPredio() != null) {
            query.setParameter("id_predio", filtro.getIdPredio());
        }

        return query.getResultList();
    }

      private List<Object[]> consultarRedistribucionIndicadoresPostgre(FiltroComparadorInd filtro) {
        StringBuilder sql = new StringBuilder();
        StringBuilder agrupamiento = new StringBuilder();
//SELECT
        sql.append(" select ");
        if (filtro.isAgrupamientoPredio()) {
            agrupamiento.append(" n.nombre_nivel1, n.nombre_nivel2, n.nombre_nivel3, n.nombre_nivel4, n.nombre_nivel5");
            agrupamiento.append(" ,n.codigo_establecimiento, n.nombre_establecimiento");
            agrupamiento.append(" ,n.codigo_sede, n.nombre_sede");
            agrupamiento.append(" ,n.codigo_predio, n.nombre_predio");
            // datos informativos
            agrupamiento.append(" ,n.direccion_predio, n.propiedad_predio");
            agrupamiento.append(" ,n.nombre_zona, n.nombre_sector, n.nombre_clima");

            // indicadores            
            agrupamiento.append(" ,etnias.valor");            
            // utilizacion
            agrupamiento.append(" ,util_total.valor");
            // total cupos
            agrupamiento.append(" ,case when to_number(cupo_total.valor,'9999999.999') >= 0 then ceil(to_number(cupo_total.valor,'9999999.999')) else floor(to_number(cupo_total.valor,'9999999.999')) end");
            
            sql.append(agrupamiento.toString());
        } else {
            if (filtro.isAgrupamientoNivel0()) {
                agrupamiento.append(" n.nombre_nivel0");
            }
            if (filtro.isAgrupamientoNivel1()) {
                agrupamiento.append(" n.nombre_nivel1");
            }
            if (filtro.isAgrupamientoNivel2()) {
                agrupamiento.append(" n.nombre_nivel1, n.nombre_nivel2");
            }
            if (filtro.isAgrupamientoNivel3()) {
                agrupamiento.append(" n.nombre_nivel1, n.nombre_nivel2, n.nombre_nivel3");
            }
            if (filtro.isAgrupamientoNivel4()) {
                agrupamiento.append(" n.nombre_nivel1, n.nombre_nivel2, n.nombre_nivel3, n.nombre_nivel4");
            }
            if (filtro.isAgrupamientoNivel5()) {
                agrupamiento.append(" n.nombre_nivel1, n.nombre_nivel2, n.nombre_nivel3, n.nombre_nivel4, n.nombre_nivel5");
            }
            if (filtro.isAgrupamientoEstablecimiento()) {
                agrupamiento.append(" n.nombre_nivel1, n.nombre_nivel2, n.nombre_nivel3, n.nombre_nivel4, n.nombre_nivel5");
                agrupamiento.append(" ,n.codigo_establecimiento, n.nombre_establecimiento");
            }
            if (filtro.isAgrupamientoSede()) {
                agrupamiento.append(" n.nombre_nivel1, n.nombre_nivel2, n.nombre_nivel3, n.nombre_nivel4, n.nombre_nivel5");
                agrupamiento.append(" ,n.codigo_establecimiento, n.nombre_establecimiento");
                agrupamiento.append(" ,n.codigo_sede, n.nombre_sede");
            }
            // indicadores
            // saturacion
            agrupamiento.append(" ,saturado.valor");
            agrupamiento.append(" ,sub_utilizado.valor");
            // utilizacion
            agrupamiento.append(" ,util_total.valor");
            // total cupos
            agrupamiento.append(" ,case when to_number(cupo_total.valor,'9999999.999') >= 0 then ceil(to_number(cupo_total.valor,'9999999.999')) else floor(to_number(cupo_total.valor,'9999999.999')) end");
            sql.append(agrupamiento.toString());           
        }
// FROM 
        sql.append(" from cier.ind_auxiliar_indicador aux");
        sql.append(" inner join cier.ind_auxiliar_consultas n on n.id_predio = aux.id_predio and n.periodo = aux.periodo and n.id_tnt = aux.id_tnt");

        String strSQLNivelAgrupamiento = this.getSQLNivelAgrupamiento(filtro);
        String strSQLConsultarIndicador = this.getSQLConsultarIndicadorAlmacenado();

        //  cupos total
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) as cupo_total on cupo_total.id_tnt = aux.id_tnt and cupo_total.periodo = aux.periodo");
        sql.append(String.format(" and cupo_total.cod_indicador = '%s'", ParamIndicador.IND_REDISTRUBUCION_CUPOS_TOTAL));
        sql.append(" and cupo_total.nivel_agrupamiento = ?nivel_agrupamiento");
        sql.append(" and cupo_total.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);

        // utilizacion total
        sql.append(" left join (");
        sql.append(strSQLConsultarIndicador);
        sql.append(" ) as util_total on util_total.id_tnt = aux.id_tnt and util_total.periodo = aux.periodo");
        sql.append(String.format(" and util_total.cod_indicador = '%s'", ParamIndicador.IND_REDISTRUBUCION_UTILIZACION_ESTADAR_TOTAL));
        sql.append(" and util_total.nivel_agrupamiento = ?nivel_agrupamiento");
        sql.append(" and util_total.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);

        if (filtro.isAgrupamientoPredio()) {           
            // etnias
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) as etnias on etnias.id_tnt = aux.id_tnt and etnias.periodo = aux.periodo");
            sql.append(String.format(" and etnias.cod_indicador = '%s'", ParamIndicador.IND_ETNIAS_PREDIO));
            sql.append(" and etnias.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and etnias.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
        } else {
            // predios saturados
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) as saturado on saturado.id_tnt = aux.id_tnt and saturado.periodo = aux.periodo");
            sql.append(String.format(" and saturado.cod_indicador = '%s'", ParamIndicador.IND_REDISTRUBUCION_PREDIOS_SATURADOS));
            sql.append(" and saturado.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and saturado.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);

            // predios sub utilizado
            sql.append(" left join (");
            sql.append(strSQLConsultarIndicador);
            sql.append(" ) as sub_utilizado on sub_utilizado.id_tnt = aux.id_tnt and sub_utilizado.periodo = aux.periodo");
            sql.append(String.format(" and sub_utilizado.cod_indicador = '%s'", ParamIndicador.IND_REDISTRUBUCION_PREDIOS_SUBUTILIZADOS));
            sql.append(" and sub_utilizado.nivel_agrupamiento = ?nivel_agrupamiento");
            sql.append(" and sub_utilizado.id_entidad_nivel = n.").append(strSQLNivelAgrupamiento);
        }

// WHERE
        sql.append(" where aux.id_tnt = ?id_tnt and aux.periodo = ?periodo");
        if (filtro.getIdNivel0() != null) {
            sql.append(" and n.id_nivel0 = ?id_nivel0");
        }
        if (filtro.getIdNivel1() != null) {
            sql.append(" and n.id_nivel1 = ?id_nivel1");
        }
        if (filtro.getIdNivel2() != null) {
            sql.append(" and n.id_nivel2 = ?id_nivel2");
        }
        if (filtro.getIdNivel3() != null) {
            sql.append(" and n.id_nivel3 = ?id_nivel3");
        }
        if (filtro.getIdNivel4() != null) {
            sql.append(" and n.id_nivel4 = ?id_nivel4");
        }
        if (filtro.getIdNivel5() != null) {
            sql.append(" and n.id_nivel5 = ?id_nivel5");
        }
        if (filtro.getIdEstablecimiento() != null) {
            sql.append(" and n.id_establecimiento = ?id_establecimiento");
        }
        if (filtro.getIdSede() != null) {
            sql.append(" and n.id_sede = ?id_sede");
        }
        if (filtro.getIdPredio() != null) {
            sql.append(" and n.id_predio = ?id_predio");
        }
        // GROUP BY 
        sql.append(" group by ");
        sql.append(agrupamiento.toString());
        // ORDER BY
        sql.append(" order by ");
        sql.append(agrupamiento.toString());

        Query query = tpm.getEm().createNativeQuery(sql.toString());
        query.setParameter("id_tnt", tpm.getCurrentTenant());
        query.setParameter("periodo", filtro.getPeriodo());
        query.setParameter("periodo_comparacion", filtro.getPeriodoComparado());
        query.setParameter("idioma", filtro.getIdioma());
        query.setParameter("id_nivel0", filtro.getIdNivel0());
        query.setParameter("nivel_agrupamiento", filtro.getAgrupamiento());

        if (filtro.getIdNivel1() != null) {
            query.setParameter("id_nivel1", filtro.getIdNivel1());
        }
        if (filtro.getIdNivel2() != null) {
            query.setParameter("id_nivel2", filtro.getIdNivel2());
        }
        if (filtro.getIdNivel3() != null) {
            query.setParameter("id_nivel3", filtro.getIdNivel3());
        }
        if (filtro.getIdNivel4() != null) {
            query.setParameter("id_nivel4", filtro.getIdNivel4());
        }
        if (filtro.getIdNivel5() != null) {
            query.setParameter("id_nivel5", filtro.getIdNivel5());
        }
        if (filtro.getIdEstablecimiento() != null) {
            query.setParameter("id_establecimiento", filtro.getIdEstablecimiento());
        }
        if (filtro.getIdSede() != null) {
            query.setParameter("id_sede", filtro.getIdSede());
        }
        if (filtro.getIdPredio() != null) {
            query.setParameter("id_predio", filtro.getIdPredio());
        }

        return query.getResultList();
    }


    private String getSQLNivelAgrupamiento(FiltroComparadorInd filtro) {
        if (filtro.getAgrupamiento() <= ParamNivelConsul.CINCO.getNivel()) {
            return "id_nivel" + filtro.getAgrupamiento();
        } else if (filtro.isAgrupamientoEstablecimiento()) {
            return "id_establecimiento";
        } else if (filtro.isAgrupamientoSede()) {
            return "id_sede";
        } else if (filtro.isAgrupamientoPredio()) {
            return "id_predio";
        } else {
            return null;
        }
    }

    private String getSQLConsultarIndicadorAlmacenado() {
        tpm.getEm().clear();
        Parametro parametro = facParam.consultarParametro(ParamSistema.SQL_CONSULTAR_INDICADOR_ALMACENADO.name());
        return parametro != null ? parametro.getValor() : null;
    }
}
