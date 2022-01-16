/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.fachada;

import co.stackpointer.cier.modelo.entidad.sistema.Parametro;
import co.stackpointer.cier.modelo.filtro.simulacion.FiltroDiagnosticoGeneral;
import co.stackpointer.cier.modelo.tipo.ParamBaseDatos;
import co.stackpointer.cier.modelo.tipo.ParamSistema;
import co.stackpointer.cier.modelo.utilidad.UtilProperties;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Stateless
public class SimulacionCostoFacade implements SimulacionCostoFacadeLocal {

    @Inject
    private TenantPersistenceManager tpm;
    @EJB
    private ParametrosFacadeLocal facParam;

    private String getSQLSimulacionDiagnosticoGeneral() {
        tpm.getEm().clear();
        Parametro parametro = facParam.consultarParametro(ParamSistema.SQL_SIMULACION_COSTO_DIAGNOSTICO_GENERAL.name());
        return parametro != null ? parametro.getValor() : null;
    }

    @Override
    public List<Object[]> consultarDiagnosticoGeneral(FiltroDiagnosticoGeneral filtro) {

        if (ParamBaseDatos.ORACLE.equals(UtilProperties.getDbMotor())) {
            return consultarDiagnosticoGeneralOracle(filtro);
        } else {
            return consultarDiagnosticoGeneralPostgre(filtro);
        }


    }

    private List<Object[]> consultarDiagnosticoGeneralOracle(FiltroDiagnosticoGeneral filtro) {
        StringBuilder sql = new StringBuilder();
        StringBuilder agrupamiento = new StringBuilder();
//SELECT
        sql.append(" select ");
        if (filtro.isAgrupamientoPredio()) {
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

            sql.append(agrupamiento.toString());
            // total
            sql.append(" ,simulacion.m2_req_total");
            sql.append(" ,simulacion.m2_disponibles_total");
            sql.append(" ,simulacion.m2_deficit_total");
            //aula
            sql.append(" ,simulacion.idoneidad_aula");
            sql.append(" ,simulacion.m2_req_reales_aula");
            sql.append(" ,simulacion.m2_req_proyectados_aula");
            sql.append(" ,simulacion.unidades_aula");
            sql.append(" ,simulacion.m2_disponibles_aula");
            sql.append(" ,simulacion.costo_m2_aula");
            // sanitario
            sql.append(" ,simulacion.idoneidad_sanitario");
            sql.append(" ,simulacion.m2_req_reales_sanitario");
            sql.append(" ,simulacion.m2_req_proyectados_sanitario");
            sql.append(" ,simulacion.unidades_sanitario");
            sql.append(" ,simulacion.m2_disponibles_sanitario");
            sql.append(" ,simulacion.costo_m2_sanitario");
            // circulacion
            sql.append(" ,simulacion.m2_req_circulaciones");
            sql.append(" ,simulacion.m2_disponibles_circulaciones");
            sql.append(" ,simulacion.costo_m2_circulaciones");
            // otros
            sql.append(" ,simulacion.costo_m2_disponible");
            sql.append(" ,simulacion.m2_req_lote");
            sql.append(" ,simulacion.costo_m2_lote");
            sql.append(" ,simulacion.faltante_aula");
            sql.append(" ,simulacion.costo_faltante_aula");
            sql.append(" ,simulacion.faltante_sanitario");
            sql.append(" ,simulacion.costo_faltante_sanitario");
            sql.append(" ,simulacion.faltante_circulaciones");
            sql.append(" ,simulacion.costo_faltante_circulaciones");
            sql.append(" ,simulacion.costo_faltante_total");
            sql.append(" ,simulacion.valor_total");
        } else {
            if (filtro.isAgrupamientoNivel0()) {
                agrupamiento.append(" n.nombre_nivel0");
            }
            if (filtro.isAgrupamientoNivel1()) {
                agrupamiento.append(" n.codigo_nivel1,n.nombre_nivel1");
            }
            if (filtro.isAgrupamientoNivel2()) {
                agrupamiento.append(" n.codigo_nivel1,n.nombre_nivel1");
                agrupamiento.append(",n.codigo_nivel2,n.nombre_nivel2");
            }
            if (filtro.isAgrupamientoNivel3()) {
                agrupamiento.append(" n.codigo_nivel1,n.nombre_nivel1");
                agrupamiento.append(",n.codigo_nivel2,n.nombre_nivel2");
                agrupamiento.append(",n.codigo_nivel3,n.nombre_nivel3");
            }
            if (filtro.isAgrupamientoNivel4()) {
                agrupamiento.append(" n.codigo_nivel1,n.nombre_nivel1");
                agrupamiento.append(",n.codigo_nivel2,n.nombre_nivel2");
                agrupamiento.append(",n.codigo_nivel3,n.nombre_nivel3");
                agrupamiento.append(",n.codigo_nivel4,n.nombre_nivel4");
            }
            if (filtro.isAgrupamientoNivel5()) {
                agrupamiento.append(" n.codigo_nivel1,n.nombre_nivel1");
                agrupamiento.append(",n.codigo_nivel2,n.nombre_nivel2");
                agrupamiento.append(",n.codigo_nivel3,n.nombre_nivel3");
                agrupamiento.append(",n.codigo_nivel4,n.nombre_nivel4");
                agrupamiento.append(",n.codigo_nivel5,n.nombre_nivel5");
            }
            if (filtro.isAgrupamientoEstablecimiento()) {
                agrupamiento.append(" n.codigo_nivel1,n.nombre_nivel1");
                agrupamiento.append(",n.codigo_nivel2,n.nombre_nivel2");
                agrupamiento.append(",n.codigo_nivel3,n.nombre_nivel3");
                agrupamiento.append(",n.codigo_nivel4,n.nombre_nivel4");
                agrupamiento.append(",n.codigo_nivel5,n.nombre_nivel5");
                agrupamiento.append(" ,n.codigo_establecimiento, n.nombre_establecimiento");
            }
            if (filtro.isAgrupamientoSede()) {
                agrupamiento.append(" n.codigo_nivel1,n.nombre_nivel1");
                agrupamiento.append(",n.codigo_nivel2,n.nombre_nivel2");
                agrupamiento.append(",n.codigo_nivel3,n.nombre_nivel3");
                agrupamiento.append(",n.codigo_nivel4,n.nombre_nivel4");
                agrupamiento.append(",n.codigo_nivel5,n.nombre_nivel5");
                agrupamiento.append(" ,n.codigo_establecimiento, n.nombre_establecimiento");
                agrupamiento.append(" ,n.codigo_sede, n.nombre_sede");
            }
            sql.append(agrupamiento.toString());
            // total
            sql.append(" ,sum(simulacion.m2_req_total)");
            sql.append(" ,sum(simulacion.m2_disponibles_total)");
            sql.append(" ,sum(simulacion.m2_deficit_total)");
            //aula
            sql.append(" ,sum(simulacion.idoneidad_aula)");
            sql.append(" ,sum(simulacion.m2_req_reales_aula)");
            sql.append(" ,sum(simulacion.m2_req_proyectados_aula)");
            sql.append(" ,sum(simulacion.unidades_aula)");
            sql.append(" ,sum(simulacion.m2_disponibles_aula)");
            sql.append(" ,sum(simulacion.costo_m2_aula)");
            // sanitario
            sql.append(" ,sum(simulacion.idoneidad_sanitario)");
            sql.append(" ,sum(simulacion.m2_req_reales_sanitario)");
            sql.append(" ,sum(simulacion.m2_req_proyectados_sanitario)");
            sql.append(" ,sum(simulacion.unidades_sanitario)");
            sql.append(" ,sum(simulacion.m2_disponibles_sanitario)");
            sql.append(" ,sum(simulacion.costo_m2_sanitario)");
            // circulacion
            sql.append(" ,sum(simulacion.m2_req_circulaciones)");
            sql.append(" ,sum(simulacion.m2_disponibles_circulaciones)");
            sql.append(" ,sum(simulacion.costo_m2_circulaciones)");
            // otros
            sql.append(" ,sum(simulacion.costo_m2_disponible)");
            sql.append(" ,sum(simulacion.m2_req_lote)");
            sql.append(" ,sum(simulacion.costo_m2_lote)");
            sql.append(" ,sum(simulacion.faltante_aula)");
            sql.append(" ,sum(simulacion.costo_faltante_aula)");
            sql.append(" ,sum(simulacion.faltante_sanitario)");
            sql.append(" ,sum(simulacion.costo_faltante_sanitario)");
            sql.append(" ,sum(simulacion.faltante_circulaciones)");
            sql.append(" ,sum(simulacion.costo_faltante_circulaciones)");
            sql.append(" ,sum(simulacion.costo_faltante_total)");
            sql.append(" ,sum(simulacion.valor_total)");
        }

        sql.append(" from ind_auxiliar_indicador aux");
        sql.append(" inner join ind_auxiliar_consultas n on n.id_predio = aux.id_predio and n.periodo = aux.periodo and n.id_tnt = aux.id_tnt");

        String strSQLDiagnosticoGeneral = this.getSQLSimulacionDiagnosticoGeneral()
                .replaceAll("\\?periodo", filtro.getPeriodo().toString())
                .replaceAll("\\?tenant", String.valueOf(tpm.getCurrentTenant()));
        // Sql diagnostico general
        sql.append(" inner join (");
        sql.append(strSQLDiagnosticoGeneral);
        sql.append(" ) simulacion on simulacion.id_predio = aux.id_predio and simulacion.id_tnt = aux.id_tnt and simulacion.periodo = aux.periodo ");

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
        //otros filtros
        if (filtro.getCodZona() != null) {
            sql.append(" and n.codigo_zona = ?cod_zona");
        }
        if (filtro.getCodSector() != null) {
            sql.append(" and n.codigo_sector = ?cod_sector");
        }

        if (!filtro.isAgrupamientoPredio()) {
            // GROUP BY 
            sql.append(" group by ");
            sql.append(agrupamiento.toString());
        }
        /*/ ORDER BY
         sql.append(" order by ");
         sql.append(agrupamiento.toString());*/

        Query query = tpm.getEm().createNativeQuery(sql.toString());
        query.setParameter("id_tnt", tpm.getCurrentTenant());
        query.setParameter("periodo", filtro.getPeriodo());
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

    private List<Object[]> consultarDiagnosticoGeneralPostgre(FiltroDiagnosticoGeneral filtro) {
        StringBuilder sql = new StringBuilder();
        StringBuilder agrupamiento = new StringBuilder();
//SELECT
        sql.append(" select ");
        if (filtro.isAgrupamientoPredio()) {
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

            sql.append(agrupamiento.toString());
            // total
            sql.append(" ,simulacion.m2_requeridos_total");
            sql.append(" ,simulacion.m2_disponibles_total");
            sql.append(" ,simulacion.m2_deficit_total");
            //aula
            sql.append(" ,simulacion.idoneidad_aula");
            sql.append(" ,simulacion.m2_requeridos_reales_aula");
            sql.append(" ,simulacion.m2_requeridos_proyectados_aula");
            sql.append(" ,simulacion.unidades_aula");
            sql.append(" ,simulacion.m2_disponibles_aula");
            sql.append(" ,simulacion.costo_m2_aula");
            // sanitario
            sql.append(" ,simulacion.idoneidad_sanitario");
            sql.append(" ,simulacion.m2_requeridos_reales_sanitario");
            sql.append(" ,simulacion.m2_requeridos_proyectados_sanitario");
            sql.append(" ,simulacion.unidades_sanitario");
            sql.append(" ,simulacion.m2_disponibles_sanitario");
            sql.append(" ,simulacion.costo_m2_sanitario");
            // circulacion
            sql.append(" ,simulacion.m2_requeridos_circulaciones");
            sql.append(" ,simulacion.m2_disponibles_circulaciones");
            sql.append(" ,simulacion.costo_m2_circulaciones");
            // otros
            sql.append(" ,simulacion.costo_m2_disponible");
            sql.append(" ,simulacion.m2_requeridos_lote");
            sql.append(" ,simulacion.costo_m2_lote");
            sql.append(" ,simulacion.faltante_aula");
            sql.append(" ,simulacion.costo_faltante_aula");
            sql.append(" ,simulacion.faltante_sanitario");
            sql.append(" ,simulacion.costo_faltante_sanitario");
            sql.append(" ,simulacion.faltante_circulaciones");
            sql.append(" ,simulacion.costo_faltante_circulaciones");
            sql.append(" ,simulacion.costo_faltante_total");
            sql.append(" ,simulacion.valor_total");
        } else {
            if (filtro.isAgrupamientoNivel0()) {
                agrupamiento.append(" n.nombre_nivel0");
            }
            if (filtro.isAgrupamientoNivel1()) {
                agrupamiento.append(" n.codigo_nivel1,n.nombre_nivel1");
            }
            if (filtro.isAgrupamientoNivel2()) {
                agrupamiento.append(" n.codigo_nivel1,n.nombre_nivel1");
                agrupamiento.append(",n.codigo_nivel2,n.nombre_nivel2");
            }
            if (filtro.isAgrupamientoNivel3()) {
                agrupamiento.append(" n.codigo_nivel1,n.nombre_nivel1");
                agrupamiento.append(",n.codigo_nivel2,n.nombre_nivel2");
                agrupamiento.append(",n.codigo_nivel3,n.nombre_nivel3");
            }
            if (filtro.isAgrupamientoNivel4()) {
                agrupamiento.append(" n.codigo_nivel1,n.nombre_nivel1");
                agrupamiento.append(",n.codigo_nivel2,n.nombre_nivel2");
                agrupamiento.append(",n.codigo_nivel3,n.nombre_nivel3");
                agrupamiento.append(",n.codigo_nivel4,n.nombre_nivel4");
            }
            if (filtro.isAgrupamientoNivel5()) {
                agrupamiento.append(" n.codigo_nivel1,n.nombre_nivel1");
                agrupamiento.append(",n.codigo_nivel2,n.nombre_nivel2");
                agrupamiento.append(",n.codigo_nivel3,n.nombre_nivel3");
                agrupamiento.append(",n.codigo_nivel4,n.nombre_nivel4");
                agrupamiento.append(",n.codigo_nivel5,n.nombre_nivel5");
            }
            if (filtro.isAgrupamientoEstablecimiento()) {
                agrupamiento.append(" n.codigo_nivel1,n.nombre_nivel1");
                agrupamiento.append(",n.codigo_nivel2,n.nombre_nivel2");
                agrupamiento.append(",n.codigo_nivel3,n.nombre_nivel3");
                agrupamiento.append(",n.codigo_nivel4,n.nombre_nivel4");
                agrupamiento.append(",n.codigo_nivel5,n.nombre_nivel5");
                agrupamiento.append(" ,n.codigo_establecimiento, n.nombre_establecimiento");
            }
            if (filtro.isAgrupamientoSede()) {
                agrupamiento.append(" n.codigo_nivel1,n.nombre_nivel1");
                agrupamiento.append(",n.codigo_nivel2,n.nombre_nivel2");
                agrupamiento.append(",n.codigo_nivel3,n.nombre_nivel3");
                agrupamiento.append(",n.codigo_nivel4,n.nombre_nivel4");
                agrupamiento.append(",n.codigo_nivel5,n.nombre_nivel5");
                agrupamiento.append(" ,n.codigo_establecimiento, n.nombre_establecimiento");
                agrupamiento.append(" ,n.codigo_sede, n.nombre_sede");
            }
            sql.append(agrupamiento.toString());
            // total
            sql.append(" ,sum(simulacion.m2_requeridos_total)");
            sql.append(" ,sum(simulacion.m2_disponibles_total)");
            sql.append(" ,sum(simulacion.m2_deficit_total)");
            //aula
            sql.append(" ,sum(simulacion.idoneidad_aula)");
            sql.append(" ,sum(simulacion.m2_requeridos_reales_aula)");
            sql.append(" ,sum(simulacion.m2_requeridos_proyectados_aula)");
            sql.append(" ,sum(simulacion.unidades_aula)");
            sql.append(" ,sum(simulacion.m2_disponibles_aula)");
            sql.append(" ,sum(simulacion.costo_m2_aula)");
            // sanitario
            sql.append(" ,sum(simulacion.idoneidad_sanitario)");
            sql.append(" ,sum(simulacion.m2_requeridos_reales_sanitario)");
            sql.append(" ,sum(simulacion.m2_requeridos_proyectados_sanitario)");
            sql.append(" ,sum(simulacion.unidades_sanitario)");
            sql.append(" ,sum(simulacion.m2_disponibles_sanitario)");
            sql.append(" ,sum(simulacion.costo_m2_sanitario)");
            // circulacion
            sql.append(" ,sum(simulacion.m2_requeridos_circulaciones)");
            sql.append(" ,sum(simulacion.m2_disponibles_circulaciones)");
            sql.append(" ,sum(simulacion.costo_m2_circulaciones)");
            // otros
            sql.append(" ,sum(simulacion.costo_m2_disponible)");
            sql.append(" ,sum(simulacion.m2_requeridos_lote)");
            sql.append(" ,sum(simulacion.costo_m2_lote)");
            sql.append(" ,sum(simulacion.faltante_aula)");
            sql.append(" ,sum(simulacion.costo_faltante_aula)");
            sql.append(" ,sum(simulacion.faltante_sanitario)");
            sql.append(" ,sum(simulacion.costo_faltante_sanitario)");
            sql.append(" ,sum(simulacion.faltante_circulaciones)");
            sql.append(" ,sum(simulacion.costo_faltante_circulaciones)");
            sql.append(" ,sum(simulacion.costo_faltante_total)");
            sql.append(" ,sum(simulacion.valor_total)");
        }

        sql.append(" from cier.ind_auxiliar_indicador aux");
        sql.append(" inner join cier.ind_auxiliar_consultas n on n.id_predio = aux.id_predio and n.periodo = aux.periodo and n.id_tnt = aux.id_tnt");

        String strSQLDiagnosticoGeneral = this.getSQLSimulacionDiagnosticoGeneral()
                .replaceAll("\\?periodo", filtro.getPeriodo().toString())
                .replaceAll("\\?tenant", String.valueOf(tpm.getCurrentTenant()));
        // Sql diagnostico general
        sql.append(" inner join (");
        sql.append(strSQLDiagnosticoGeneral);
        sql.append(" ) as simulacion on simulacion.id_dig_instrumento = aux.id_dig_instrumento and simulacion.id_predio = aux.id_predio");

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
        //otros filtros
        if (filtro.getCodZona() != null) {
            sql.append(" and n.codigo_zona = ?cod_zona");
        }
        if (filtro.getCodSector() != null) {
            sql.append(" and n.codigo_sector = ?cod_sector");
        }

        if (!filtro.isAgrupamientoPredio()) {
            // GROUP BY 
            sql.append(" group by ");
            sql.append(agrupamiento.toString());
        }
        // ORDER BY
        sql.append(" order by ");
        sql.append(agrupamiento.toString());

        Query query = tpm.getEm().createNativeQuery(sql.toString());
        query.setParameter("id_tnt", tpm.getCurrentTenant());
        query.setParameter("periodo", filtro.getPeriodo());
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
}
