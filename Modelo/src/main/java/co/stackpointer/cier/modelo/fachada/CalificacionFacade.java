/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.fachada;

import co.stackpointer.cier.modelo.entidad.calificacion.CalificacionValor;
import co.stackpointer.cier.modelo.excepcion.ErrorIntegridad;
import co.stackpointer.cier.modelo.filtro.calificacion.FiltroCalificacion;
import co.stackpointer.cier.modelo.tipo.Ambitos;
import co.stackpointer.cier.modelo.tipo.EspacioFuncional;
import co.stackpointer.cier.modelo.tipo.EstadoInstrumento;
import co.stackpointer.cier.modelo.tipo.ParamNivelConsul;
import co.stackpointer.cier.modelo.tipo.ServiciosPublicos;
import co.stackpointer.cier.modelo.tipo.PrincipalOtro;
import co.stackpointer.cier.modelo.utilidad.UtilOut;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;

/**
 *
 * @author nruiz
 */
@Stateless
public class CalificacionFacade implements CalificacionFacadeLocal {

    @Inject
    private TenantPersistenceManager tpm;

    @Override
    public List<Object[]> consultarCalificaciones(FiltroCalificacion filtro) {

        StringBuilder sql = new StringBuilder();
        //SELECT
        //sql.append(partesAmbientes.get(0));
        if (ParamNivelConsul.PREDIO.getNivel().equals(filtro.getNivelAgrupamiento())) {
            sql.append("select distinct aux.ID_DIG_INSTRUMENTO");
            sql.append(",n.codigo_nivel1,n.nombre_nivel1");
            sql.append(",n.codigo_nivel2,n.nombre_nivel2");
            sql.append(",n.codigo_nivel3,n.nombre_nivel3");
            sql.append(",n.codigo_nivel4,n.nombre_nivel4");
            sql.append(",n.codigo_nivel5,n.nombre_nivel5");
            sql.append(", n.codigo_establecimiento, n.nombre_establecimiento");
            sql.append(", n.codigo_sede, n.nombre_sede");
            sql.append(", n.codigo_predio, n.nombre_predio");
        } else if (ParamNivelConsul.CERO.getNivel().equals(filtro.getNivelAgrupamiento())) {
            sql.append("select distinct n.id_nivel0, n.nombre_nivel0");
        } else if (ParamNivelConsul.UNO.getNivel().equals(filtro.getNivelAgrupamiento())) {            
            sql.append("select distinct n.id_nivel1, n.codigo_nivel1, n.nombre_nivel1");
        } else if (ParamNivelConsul.DOS.getNivel().equals(filtro.getNivelAgrupamiento())) {
            sql.append("select distinct n.id_nivel2");
            sql.append(",n.codigo_nivel1,n.nombre_nivel1");
            sql.append(",n.codigo_nivel2,n.nombre_nivel2");
        } else if (ParamNivelConsul.TRES.getNivel().equals(filtro.getNivelAgrupamiento())) {
            sql.append("select distinct n.id_nivel3");
            sql.append(",n.codigo_nivel1,n.nombre_nivel1");
            sql.append(",n.codigo_nivel2,n.nombre_nivel2");
            sql.append(",n.codigo_nivel3,n.nombre_nivel3");
        } else if (ParamNivelConsul.CUATRO.getNivel().equals(filtro.getNivelAgrupamiento())) {
            sql.append("select distinct n.id_nivel4");
            sql.append(",n.codigo_nivel1,n.nombre_nivel1");
            sql.append(",n.codigo_nivel2,n.nombre_nivel2");
            sql.append(",n.codigo_nivel3,n.nombre_nivel3");
            sql.append(",n.codigo_nivel4,n.nombre_nivel4");
        } else if (ParamNivelConsul.CINCO.getNivel().equals(filtro.getNivelAgrupamiento())) {
            sql.append("select distinct n.id_nivel5");
            sql.append(",n.codigo_nivel1,n.nombre_nivel1");
            sql.append(",n.codigo_nivel2,n.nombre_nivel2");
            sql.append(",n.codigo_nivel3,n.nombre_nivel3");
            sql.append(",n.codigo_nivel4,n.nombre_nivel4");
            sql.append(",n.codigo_nivel5,n.nombre_nivel5");
        } else if (ParamNivelConsul.ESTABLECIMIENTO.getNivel().equals(filtro.getNivelAgrupamiento())) {
            sql.append("select distinct n.id_establecimiento");
            sql.append(",n.codigo_nivel1,n.nombre_nivel1");
            sql.append(",n.codigo_nivel2,n.nombre_nivel2");
            sql.append(",n.codigo_nivel3,n.nombre_nivel3");
            sql.append(",n.codigo_nivel4,n.nombre_nivel4");
            sql.append(",n.codigo_nivel5,n.nombre_nivel5");
            sql.append(",n.codigo_establecimiento, n.nombre_establecimiento");
        } else if (ParamNivelConsul.SEDE.getNivel().equals(filtro.getNivelAgrupamiento())) {
            sql.append("select distinct n.id_sede");
            sql.append(",n.codigo_nivel1,n.nombre_nivel1");
            sql.append(",n.codigo_nivel2,n.nombre_nivel2");
            sql.append(",n.codigo_nivel3,n.nombre_nivel3");
            sql.append(",n.codigo_nivel4,n.nombre_nivel4");
            sql.append(",n.codigo_nivel5,n.nombre_nivel5");
            sql.append(",n.codigo_establecimiento, n.nombre_establecimiento");
            sql.append(",n.codigo_sede, n.nombre_sede");
        }


        for (Ambitos amb : Ambitos.values()) {
            if (!amb.equals(Ambitos.A_RA) && !amb.equals(Ambitos.A_RN)) {
                sql.append(", round(coalesce(").append(amb.name()).append(".calif,0),2)");
            }
        }

        //FROM
        sql.append(" from (select * from ind_auxiliar_indicador where id_tnt= ?id_tnt and periodo= ?periodo ) aux");        
        sql.append(" inner join ind_auxiliar_consultas n on n.id_predio = aux.id_predio and n.periodo = aux.periodo and n.id_tnt = aux.id_tnt");

        //JOINS


        for (Ambitos amb : Ambitos.values()) {
            if (!amb.equals(Ambitos.A_RA) && !amb.equals(Ambitos.A_RN)) {
                sql.append(" left join(");
                //en control y vigilancia puede ser mayor de 100 por la vulnerabilidad. Por eso se controla que si es mayor se le ponga 100
                sql.append(" select val.id_tnt,val.periodo,val.id_entidad,");
                sql.append(" case when sum(char_to_double(val.valor) * v.ponderacion)/100>100 then 100 else sum(char_to_double(val.valor) * v.ponderacion)/100 end");
                sql.append(" calif");
                sql.append(" from CAL_CALIFICA_VARIABLES_AMB c, CAL_VARIABLES_AMBITO v, INS_AMBITOS a, CAL_VALORES val");
                sql.append(" where a.CODIGO=v.COD_AMBITO and v.ID=c.ID_VAR_AMB");
                sql.append(" and val.id_var_amb=c.id_var_amb and val.nivel_consulta=c.nivel_consulta");
                sql.append(" and a.ID_TNT=?id_tnt and c.NIVEL_CONSULTA=?nivelConsulta and a.codigo='").append(amb.name()).append("'");
                sql.append(" and val.periodo=?periodo");
                sql.append(" group by val.id_tnt,val.periodo,val.id_entidad,val.id_entidad");
                sql.append(" ) ").append(amb.name()).append(" on ")
                        .append(amb.name()).append(".id_tnt=n.id_tnt and ")
                        .append(amb.name()).append(".periodo=n.periodo and ")
                        .append(amb.name()).append(".id_entidad=n.");
                if (ParamNivelConsul.PREDIO.equals(filtro.getNivelAgrupamiento())) {
                    sql.append("id_predio");
                } else if (ParamNivelConsul.CERO.equals(filtro.getNivelAgrupamiento())) {
                    sql.append("id_nivel0");
                } else if (ParamNivelConsul.UNO.equals(filtro.getNivelAgrupamiento())) {
                    sql.append("id_nivel1");
                } else if (ParamNivelConsul.DOS.equals(filtro.getNivelAgrupamiento())) {
                    sql.append("id_nivel2");
                } else if (ParamNivelConsul.TRES.equals(filtro.getNivelAgrupamiento())) {
                    sql.append("id_nivel3");
                } else if (ParamNivelConsul.CUATRO.equals(filtro.getNivelAgrupamiento())) {
                    sql.append("id_nivel4");
                } else if (ParamNivelConsul.CINCO.equals(filtro.getNivelAgrupamiento())) {
                    sql.append("id_nivel5");
                } else if (ParamNivelConsul.ESTABLECIMIENTO.equals(filtro.getNivelAgrupamiento())) {
                    sql.append("id_establecimiento");
                } else if (ParamNivelConsul.SEDE.equals(filtro.getNivelAgrupamiento())) {
                    sql.append("id_sede");
                }
            }
        }

        //WHERE
        sql.append(" where");
        sql.append(" aux.id_tnt = ?id_tnt");
        //sql.append(" and x.version = (select max(xx.version) from dig_instrumentos xx where x.id_Predio=xx.id_Predio and xx.estado=?estado)");
        //sql.append(" and x.estado=?estado");


        if (filtro.getIdNivel1() != null) {
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
        // otros filtros
        if (filtro.getCodZona() != null) {
            sql.append(" and n.codigo_zona = ?cod_zona");
        }
        if (filtro.getCodSector() != null) {
            sql.append(" and n.codigo_sector = ?cod_sector");
        }

        if (filtro.getIdEntidad() != null) {
            if (ParamNivelConsul.PREDIO.getNivel().equals(filtro.getNivelAgrupamiento())) {
                sql.append(" and aux.id_predio=?idEntidad");
            } else if (ParamNivelConsul.CERO.getNivel().equals(filtro.getNivelAgrupamiento())) {
                sql.append(" and n.id_nivel0=?idEntidad");
            } else if (ParamNivelConsul.UNO.getNivel().equals(filtro.getNivelAgrupamiento())) {
                sql.append(" and n.id_nivel1=?idEntidad");
            } else if (ParamNivelConsul.DOS.getNivel().equals(filtro.getNivelAgrupamiento())) {
                sql.append(" and n.id_nivel2=?idEntidad");
            } else if (ParamNivelConsul.TRES.getNivel().equals(filtro.getNivelAgrupamiento())) {
                sql.append(" and n.id_nivel3=?idEntidad");
            } else if (ParamNivelConsul.CUATRO.getNivel().equals(filtro.getNivelAgrupamiento())) {
                sql.append(" and n.id_nivel4=?idEntidad");
            } else if (ParamNivelConsul.CINCO.getNivel().equals(filtro.getNivelAgrupamiento())) {
                sql.append(" and n.id_nivel5=?idEntidad");
            } else if (ParamNivelConsul.ESTABLECIMIENTO.getNivel().equals(filtro.getNivelAgrupamiento())) {
                sql.append(" and n.id_establecimiento=?idEntidad");
            } else if (ParamNivelConsul.SEDE.getNivel().equals(filtro.getNivelAgrupamiento())) {
                sql.append(" and n.id_sede=?idEntidad");
            }
        }


        Query query = tpm.getEm().createNativeQuery(sql.toString());
        query.setParameter("id_tnt", tpm.getCurrentTenant());
        query.setParameter("periodo", filtro.getPeriodo());
        //query.setParameter("estado", EstadoInstrumento.E.toString());
        query.setParameter("nivelConsulta", filtro.getNivelAgrupamiento());
        if (filtro.getIdEntidad() != null) {
            query.setParameter("idEntidad", filtro.getIdEntidad());
        }


        if (filtro.getIdNivel1() != null) {
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
            query.setParameter("cod_zona", filtro.getCodZona());
        }
        if (filtro.getCodSector() != null) {
            query.setParameter("cod_sector", filtro.getCodSector());
        }

        return query.getResultList();
    }

    @Override
    public List<Object[]> consultarCalificacionesReporte(FiltroCalificacion filtro) {

        StringBuilder sql = new StringBuilder();
        //SELECT
        //sql.append(partesAmbientes.get(0));
        if (ParamNivelConsul.PREDIO.getNivel().equals(filtro.getNivelAgrupamiento())) {
            sql.append("select distinct x.id, x.cod_instrumento");
            sql.append(", n.codigo_establecimiento, n.nombre_establecimiento");
            sql.append(", n.codigo_sede, n.nombre_sede");
            sql.append(", n.codigo_predio, n.nombre_predio");
        } else if (ParamNivelConsul.CERO.getNivel().equals(filtro.getNivelAgrupamiento())) {
            sql.append("select distinct n.id_nivel0, n.codigo_nivel0, n.nombre_nivel0");
        } else if (ParamNivelConsul.UNO.getNivel().equals(filtro.getNivelAgrupamiento())) {
            sql.append("select distinct n.id_nivel1, n.codigo_nivel1, n.nombre_nivel1");
        } else if (ParamNivelConsul.DOS.getNivel().equals(filtro.getNivelAgrupamiento())) {
            sql.append("select distinct n.id_nivel2, n.codigo_nivel2, n.nombre_nivel2");
        } else if (ParamNivelConsul.TRES.getNivel().equals(filtro.getNivelAgrupamiento())) {
            sql.append("select distinct n.id_nivel3, n.codigo_nivel3, n.nombre_nivel3");
        } else if (ParamNivelConsul.CUATRO.getNivel().equals(filtro.getNivelAgrupamiento())) {
            sql.append("select distinct n.id_nivel4, n.codigo_nivel4, n.nombre_nivel4");
        } else if (ParamNivelConsul.CINCO.getNivel().equals(filtro.getNivelAgrupamiento())) {
            sql.append("select distinct n.id_nivel5, n.codigo_nivel5, n.nombre_nivel5");
        } else if (ParamNivelConsul.ESTABLECIMIENTO.getNivel().equals(filtro.getNivelAgrupamiento())) {
            sql.append("select distinct n.id_establecimiento, n.codigo_establecimiento, n.nombre_establecimiento");
        } else if (ParamNivelConsul.SEDE.getNivel().equals(filtro.getNivelAgrupamiento())) {
            sql.append("select distinct n.id_sede, n.codigo_sede, n.nombre_sede");
        }


        for (Ambitos amb : Ambitos.values()) {
            if (!amb.equals(Ambitos.A_RA) && !amb.equals(Ambitos.A_RN)) {
                sql.append(", round(coalesce(").append(amb.name()).append(".calif,0),2)");
            }
        }

        //FROM
        sql.append(" from dig_instrumentos x");
        sql.append(" inner join ind_auxiliar_indicador aux on aux.id_predio=x.id_Predio");
        sql.append(" inner join ind_auxiliar_consultas n on n.id_predio = aux.id_predio and n.periodo = aux.periodo and n.id_tnt = aux.id_tnt");

        //JOINS


        for (Ambitos amb : Ambitos.values()) {
            if (!amb.equals(Ambitos.A_RA) && !amb.equals(Ambitos.A_RN)) {
                sql.append(" left join(");
                //en control y vigilancia puede ser mayor de 100 por la vulnerabilidad. Por eso se controla que si es mayor se le ponga 100
                sql.append(" select case when sum(char_to_double(val.valor) * v.ponderacion)/100>100 then 100 else sum(char_to_double(val.valor) * v.ponderacion)/100 end");
                sql.append(" calif, val.id_entidad");
                sql.append(" from CAL_CALIFICA_VARIABLES_AMB c, CAL_VARIABLES_AMBITO v, INS_AMBITOS a, CAL_VALORES val");
                sql.append(" where a.CODIGO=v.COD_AMBITO and v.ID=c.ID_VAR_AMB");
                sql.append(" and val.id_var_amb=c.id_var_amb and val.nivel_consulta=c.nivel_consulta");
                sql.append(" and a.ID_TNT=?id_tnt and c.NIVEL_CONSULTA=?nivelConsulta and a.codigo='").append(amb.name()).append("'");
                sql.append(" and val.periodo=(select max(val2.periodo) from CAL_VALORES val2)");
                sql.append(" group by val.id_entidad");
                sql.append(" ) ").append(amb.name()).append(" on ").append(amb.name()).append(".id_entidad=n.");
                if (ParamNivelConsul.PREDIO.equals(filtro.getNivelAgrupamiento())) {
                    sql.append("id_predio");
                } else if (ParamNivelConsul.CERO.equals(filtro.getNivelAgrupamiento())) {
                    sql.append("id_nivel0");
                } else if (ParamNivelConsul.UNO.equals(filtro.getNivelAgrupamiento())) {
                    sql.append("id_nivel1");
                } else if (ParamNivelConsul.DOS.equals(filtro.getNivelAgrupamiento())) {
                    sql.append("id_nivel2");
                } else if (ParamNivelConsul.TRES.equals(filtro.getNivelAgrupamiento())) {
                    sql.append("id_nivel3");
                } else if (ParamNivelConsul.CUATRO.equals(filtro.getNivelAgrupamiento())) {
                    sql.append("id_nivel4");
                } else if (ParamNivelConsul.CINCO.equals(filtro.getNivelAgrupamiento())) {
                    sql.append("id_nivel5");
                } else if (ParamNivelConsul.ESTABLECIMIENTO.equals(filtro.getNivelAgrupamiento())) {
                    sql.append("id_establecimiento");
                } else if (ParamNivelConsul.SEDE.equals(filtro.getNivelAgrupamiento())) {
                    sql.append("id_sede");
                }
            }
        }

        //WHERE
        sql.append(" where");
        sql.append(" x.id_tnt = ?id_tnt");
        sql.append(" and x.version = (select max(xx.version) from dig_instrumentos xx where x.id_Predio=xx.id_Predio and xx.estado=?estado)");
        sql.append(" and x.estado=?estado");


        if (filtro.getIdNivel1() != null) {
            sql.append(" and x.id_nivel0 = ?n0");
        }
        if (filtro.getIdNivel1() != null) {
            sql.append(" and x.id_nivel1 = ?n1");
        }
        if (filtro.getIdNivel2() != null) {
            sql.append(" and x.id_nivel2 = ?n2");
        }
        if (filtro.getIdNivel3() != null) {
            sql.append(" and x.id_nivel3 = ?n3");
        }
        if (filtro.getIdNivel4() != null) {
            sql.append(" and x.id_nivel4 = ?n4");
        }
        if (filtro.getIdNivel5() != null) {
            sql.append(" and x.id_nivel5 = ?n5");
        }
        if (filtro.getCodZona() != null) {
            sql.append(" and x.id in (");
            sql.append(" select m.id_dig_instrumento from dig_respuestas d, dig_elementos m");
            sql.append(" where d.id_dig_elemento=m.id and d.cod_respuesta='RESP_029_01'");
            sql.append(" and d.valor = ?zona)");
        }
        if (filtro.getCodSector() != null) {
            sql.append(" and x.id in (");
            sql.append(" select m.id_dig_instrumento from dig_respuestas d, dig_elementos m");
            sql.append(" where d.id_dig_elemento=m.id and d.cod_respuesta='RESP_012_01'");
            sql.append(" and d.valor = ?sector)");
        }

        if (filtro.getIdEntidad() != null) {
            if (ParamNivelConsul.PREDIO.getNivel().equals(filtro.getNivelAgrupamiento())) {
                sql.append(" and x.id=?idEntidad");
            } else if (ParamNivelConsul.CERO.getNivel().equals(filtro.getNivelAgrupamiento())) {
                sql.append(" and n.id_nivel0=?idEntidad");
            } else if (ParamNivelConsul.UNO.getNivel().equals(filtro.getNivelAgrupamiento())) {
                sql.append(" and n.id_nivel1=?idEntidad");
            } else if (ParamNivelConsul.DOS.getNivel().equals(filtro.getNivelAgrupamiento())) {
                sql.append(" and n.id_nivel2=?idEntidad");
            } else if (ParamNivelConsul.TRES.getNivel().equals(filtro.getNivelAgrupamiento())) {
                sql.append(" and n.id_nivel3=?idEntidad");
            } else if (ParamNivelConsul.CUATRO.getNivel().equals(filtro.getNivelAgrupamiento())) {
                sql.append(" and n.id_nivel4=?idEntidad");
            } else if (ParamNivelConsul.CINCO.getNivel().equals(filtro.getNivelAgrupamiento())) {
                sql.append(" and n.id_nivel5=?idEntidad");
            } else if (ParamNivelConsul.ESTABLECIMIENTO.getNivel().equals(filtro.getNivelAgrupamiento())) {
                sql.append(" and n.id_establecimiento=?idEntidad");
            } else if (ParamNivelConsul.SEDE.getNivel().equals(filtro.getNivelAgrupamiento())) {
                sql.append(" and n.id_sede=?idEntidad");
            }
        }


        Query query = tpm.getEm().createNativeQuery(sql.toString());
        query.setParameter("id_tnt", tpm.getCurrentTenant());
        query.setParameter("estado", EstadoInstrumento.E.toString());
        query.setParameter("nivelConsulta", filtro.getNivelAgrupamiento());
        if (filtro.getIdEntidad() != null) {
            query.setParameter("idEntidad", filtro.getIdEntidad());
        }


        if (filtro.getIdNivel1() != null) {
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
    
    
    @Override
    public BigDecimal consultarCalificacionAmbito(FiltroCalificacion filtro, String amb) {

        StringBuilder sql = new StringBuilder();
        //SELECT
        //sql.append(partesAmbientes.get(0));

        sql.append("select distinct round(coalesce(").append(amb).append(".calif,0),2)");


        //FROM
        sql.append(" from dig_instrumentos x");
        sql.append(" inner join ind_auxiliar_indicador aux on aux.id_predio=x.id_Predio");
        sql.append(" inner join ind_auxiliar_consultas n on n.id_predio = aux.id_predio and n.periodo = aux.periodo and n.id_tnt = aux.id_tnt");

        //JOINS
        sql.append(" left join(");
        //en control y vigilancia puede ser mayor de 100 por la vulnerabilidad. Por eso se controla que si es mayor se le ponga 100
        sql.append(" select case when sum(char_to_double(val.valor) * v.ponderacion)/100>100 then 100 else sum(char_to_double(val.valor) * v.ponderacion)/100 end");
        sql.append(" calif, val.id_entidad");
        sql.append(" from CAL_CALIFICA_VARIABLES_AMB c, CAL_VARIABLES_AMBITO v, INS_AMBITOS a, CAL_VALORES val");
        sql.append(" where a.CODIGO=v.COD_AMBITO and v.ID=c.ID_VAR_AMB");
        sql.append(" and val.id_var_amb=c.id_var_amb and val.nivel_consulta=c.nivel_consulta");
        sql.append(" and a.ID_TNT=?id_tnt and c.NIVEL_CONSULTA=?nivelConsulta and a.codigo='").append(amb).append("'");
        sql.append(" and val.periodo=(select max(val2.periodo) from CAL_VALORES val2)");
        sql.append(" group by val.id_entidad");
        sql.append(" ) ").append(amb).append(" on ").append(amb).append(".id_entidad=n.");
        if (ParamNivelConsul.PREDIO.equals(filtro.getNivelAgrupamiento())) {
            sql.append("id_predio");
        } else if (ParamNivelConsul.CERO.equals(filtro.getNivelAgrupamiento())) {
            sql.append("id_nivel0");
        } else if (ParamNivelConsul.UNO.equals(filtro.getNivelAgrupamiento())) {
            sql.append("id_nivel1");
        } else if (ParamNivelConsul.DOS.equals(filtro.getNivelAgrupamiento())) {
            sql.append("id_nivel2");
        } else if (ParamNivelConsul.TRES.equals(filtro.getNivelAgrupamiento())) {
            sql.append("id_nivel3");
        } else if (ParamNivelConsul.CUATRO.equals(filtro.getNivelAgrupamiento())) {
            sql.append("id_nivel4");
        } else if (ParamNivelConsul.CINCO.equals(filtro.getNivelAgrupamiento())) {
            sql.append("id_nivel5");
        } else if (ParamNivelConsul.ESTABLECIMIENTO.equals(filtro.getNivelAgrupamiento())) {
            sql.append("id_establecimiento");
        } else if (ParamNivelConsul.SEDE.equals(filtro.getNivelAgrupamiento())) {
            sql.append("id_sede");
        }
        //WHERE
        sql.append(" where");
        sql.append(" x.id_tnt = ?id_tnt");
        sql.append(" and x.version = (select max(xx.version) from dig_instrumentos xx where x.id_Predio=xx.id_Predio and xx.estado=?estado)");
        sql.append(" and x.estado=?estado");


        if (filtro.getIdNivel1() != null) {
            sql.append(" and x.id_nivel0 = ?n0");
        }
        if (filtro.getIdNivel1() != null) {
            sql.append(" and x.id_nivel1 = ?n1");
        }
        if (filtro.getIdNivel2() != null) {
            sql.append(" and x.id_nivel2 = ?n2");
        }
        if (filtro.getIdNivel3() != null) {
            sql.append(" and x.id_nivel3 = ?n3");
        }
        if (filtro.getIdNivel4() != null) {
            sql.append(" and x.id_nivel4 = ?n4");
        }
        if (filtro.getIdNivel5() != null) {
            sql.append(" and x.id_nivel5 = ?n5");
        }
        if (filtro.getCodZona() != null) {
            sql.append(" and x.id in (");
            sql.append(" select m.id_dig_instrumento from dig_respuestas d, dig_elementos m");
            sql.append(" where d.id_dig_elemento=m.id and d.cod_respuesta='RESP_029_01'");
            sql.append(" and d.valor = ?zona)");
        }
        if (filtro.getCodSector() != null) {
            sql.append(" and x.id in (");
            sql.append(" select m.id_dig_instrumento from dig_respuestas d, dig_elementos m");
            sql.append(" where d.id_dig_elemento=m.id and d.cod_respuesta='RESP_012_01'");
            sql.append(" and d.valor = ?sector)");
        }

        if (filtro.getIdEntidad() != null) {
            if (ParamNivelConsul.PREDIO.getNivel().equals(filtro.getNivelAgrupamiento())) {
                sql.append(" and x.id=?idEntidad");
            } else if (ParamNivelConsul.CERO.getNivel().equals(filtro.getNivelAgrupamiento())) {
                sql.append(" and n.id_nivel0=?idEntidad");
            } else if (ParamNivelConsul.UNO.getNivel().equals(filtro.getNivelAgrupamiento())) {
                sql.append(" and n.id_nivel1=?idEntidad");
            } else if (ParamNivelConsul.DOS.getNivel().equals(filtro.getNivelAgrupamiento())) {
                sql.append(" and n.id_nivel2=?idEntidad");
            } else if (ParamNivelConsul.TRES.getNivel().equals(filtro.getNivelAgrupamiento())) {
                sql.append(" and n.id_nivel3=?idEntidad");
            } else if (ParamNivelConsul.CUATRO.getNivel().equals(filtro.getNivelAgrupamiento())) {
                sql.append(" and n.id_nivel4=?idEntidad");
            } else if (ParamNivelConsul.CINCO.getNivel().equals(filtro.getNivelAgrupamiento())) {
                sql.append(" and n.id_nivel5=?idEntidad");
            } else if (ParamNivelConsul.ESTABLECIMIENTO.getNivel().equals(filtro.getNivelAgrupamiento())) {
                sql.append(" and n.id_establecimiento=?idEntidad");
            } else if (ParamNivelConsul.SEDE.getNivel().equals(filtro.getNivelAgrupamiento())) {
                sql.append(" and n.id_sede=?idEntidad");
            }
        }


        Query query = tpm.getEm().createNativeQuery(sql.toString());
        query.setParameter("id_tnt", tpm.getCurrentTenant());
        query.setParameter("estado", EstadoInstrumento.E.toString());
        query.setParameter("nivelConsulta", filtro.getNivelAgrupamiento());
        if (filtro.getIdEntidad() != null) {
            query.setParameter("idEntidad", filtro.getIdEntidad());
        }


        if (filtro.getIdNivel1() != null) {
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

        return (BigDecimal) query.getSingleResult();
    }

    private List<Object[]> obtenerVariables(Long nivelAgrupamiento) {
        StringBuilder sql = new StringBuilder();

        sql.append(" select c.formula, c.ID_VAR_AMB, a.CODIGO");
        sql.append(" from CAL_CALIFICA_VARIABLES_AMB c, CAL_VARIABLES_AMBITO v, INS_AMBITOS a");
        sql.append(" where a.CODIGO=v.COD_AMBITO and v.ID=c.ID_VAR_AMB");
        sql.append(" and a.ID_TNT=?id_tnt and c.NIVEL_CONSULTA=?nivelCons");

        Query query = tpm.getEm().createNativeQuery(sql.toString());
        query.setParameter("id_tnt", tpm.getCurrentTenant());
        query.setParameter("nivelCons", nivelAgrupamiento);

        return query.getResultList();
    }

    @Override
    public List<Object[]> obtenerPonderaciones() {
        StringBuilder sql = new StringBuilder();
        sql.append("select a.codigo, a.ponderacion pondera_general from INS_AMBITOS a");
        sql.append(" where a.ID_TNT=?id_tnt");

        Query query = tpm.getEm().createNativeQuery(sql.toString());
        query.setParameter("id_tnt", tpm.getCurrentTenant());

        return query.getResultList();
    }

    private String calcularPartesFormulaServicios(Long nivelAgregacion) {

        List<Object[]> sp_principales_otros = obtenerServiciosPublicos();
        //Es obligatorio tener 2 servicios principales. 
        //Se puede tener uno o varios servicios secundarios
        List<String> camposPrincipales = new ArrayList<String>();
        List<String> camposOtros = new ArrayList<String>();
        for (Object[] obj : sp_principales_otros) {

            if (obj[0].equals(ServiciosPublicos.DSA.toString())) {
                if (PrincipalOtro.P.toString().equals(obj[1])) {
                    camposPrincipales.add("RESP_061_05");
                } else {
                    camposOtros.add("RESP_061_05");
                }
            } else if (obj[0].equals(ServiciosPublicos.DSEE.toString())) {
                if (PrincipalOtro.P.toString().equals(obj[1])) {
                    camposPrincipales.add("RESP_062_05");
                } else {
                    camposOtros.add("RESP_062_05");
                }
            } else if (obj[0].equals(ServiciosPublicos.DSG.toString())) {
                if (PrincipalOtro.P.toString().equals(obj[1])) {
                    camposPrincipales.add("RESP_063_05");
                } else {
                    camposOtros.add("RESP_063_05");
                }
            } else if (obj[0].equals(ServiciosPublicos.DSRB.toString())) {
                if (PrincipalOtro.P.toString().equals(obj[1])) {
                    camposPrincipales.add("RESP_064_05");
                } else {
                    camposOtros.add("RESP_064_05");
                }
            } else if (obj[0].equals(ServiciosPublicos.SistemaReciclaje.toString())) {
                if (PrincipalOtro.P.toString().equals(obj[1])) {
                    camposPrincipales.add("RESP_065_02");
                } else {
                    camposOtros.add("RESP_065_02");
                }
            } else if (obj[0].equals(ServiciosPublicos.DSRAAS.toString())) {
                if (PrincipalOtro.P.toString().equals(obj[1])) {
                    camposPrincipales.add("RESP_066_05");
                } else {
                    camposOtros.add("RESP_066_05");
                }
            } else if (obj[0].equals(ServiciosPublicos.DSRP.toString())) {
                if (PrincipalOtro.P.toString().equals(obj[1])) {
                    camposPrincipales.add("RESP_067_05");
                } else {
                    camposOtros.add("RESP_067_05");
                }
            } else if (obj[0].equals(ServiciosPublicos.DSI.toString())) {
                if (PrincipalOtro.P.toString().equals(obj[1])) {
                    camposPrincipales.add("RESP_068_05");
                } else {
                    camposOtros.add("RESP_068_05");
                }
            } else if (obj[0].equals(ServiciosPublicos.DST.toString())) {
                if (PrincipalOtro.P.toString().equals(obj[1])) {
                    camposPrincipales.add("RESP_069_05");
                } else {
                    camposOtros.add("RESP_069_05");
                }
            }
        }


        StringBuilder parte1 = new StringBuilder();

        String campoSelect = "";
        if (ParamNivelConsul.CERO.getNivel().equals(nivelAgregacion)) {
            campoSelect = "id_nivel0";
        } else if (ParamNivelConsul.UNO.getNivel().equals(nivelAgregacion)) {
            campoSelect = "id_nivel1";
        } else if (ParamNivelConsul.DOS.getNivel().equals(nivelAgregacion)) {
            campoSelect = "id_nivel2";
        } else if (ParamNivelConsul.TRES.getNivel().equals(nivelAgregacion)) {
            campoSelect = "id_nivel3";
        } else if (ParamNivelConsul.CUATRO.getNivel().equals(nivelAgregacion)) {
            campoSelect = "id_nivel4";
        } else if (ParamNivelConsul.CINCO.getNivel().equals(nivelAgregacion)) {
            campoSelect = "id_nivel5";
        } else if (ParamNivelConsul.ESTABLECIMIENTO.getNivel().equals(nivelAgregacion)) {
            campoSelect = "id_establecimiento";
        } else if (ParamNivelConsul.SEDE.getNivel().equals(nivelAgregacion)) {
            campoSelect = "id_sede";
        }

        if (!ParamNivelConsul.PREDIO.getNivel().equals(nivelAgregacion)) {

            parte1.append("select n.").append(campoSelect).append(", sum(datos.calculo) calif from dig_instrumentos x");

            parte1.append(" left join(");

            parte1.append(" select xx.id, (calif.calif*area.area_predio)/areaTodos.area calculo from dig_instrumentos xx");

            parte1.append(" left join(");
        }

        //CIEN
        parte1.append("with cien as ( select distinct m.id_dig_instrumento from dig_elementos m");
        int i = 0;
        for (String campo : camposPrincipales) {
            parte1.append(" inner join dig_respuestas d").append(i).append(" on d").append(i).append(".id_dig_elemento=m.id and d").append(i).append(".cod_respuesta = '").append(campo).append("' and d").append(i).append(".valor in ('3','4')");
            i++;
        }
        for (String campo : camposOtros) {
            parte1.append(" inner join dig_respuestas d").append(i).append(" on d").append(i).append(".id_dig_elemento=m.id and d").append(i).append(".cod_respuesta = '").append(campo).append("' and d").append(i).append(".valor in ('3','4')");
            i++;
        }
        parte1.append(")");
        //OCHENTA
        parte1.append(",");
        parte1.append(" ochenta as (");
        parte1.append(" select distinct m.id_dig_instrumento");
        parte1.append(" from dig_elementos m");
        for (String campo : camposPrincipales) {
            parte1.append(" inner join dig_respuestas d").append(i).append(" on d").append(i).append(".id_dig_elemento=m.id and d").append(i).append(".cod_respuesta = '").append(campo).append("' and d").append(i).append(".valor in ('3','4')");
            i++;
        }
        parte1.append(" where m.id_dig_instrumento in (select mm.id_dig_instrumento");
        parte1.append(" from dig_elementos mm, dig_respuestas dd where dd.id_dig_elemento=mm.id and dd.cod_respuesta in ('");
        i = 0;
        for (String campo : camposOtros) {
            if (i > 0) {
                parte1.append("','");
            }
            parte1.append(campo);
            i++;
        }
        parte1.append("') and dd.valor in ('3','4'))");
        parte1.append(")");
        //SESENTA
        parte1.append(",");
        parte1.append(" sesenta as (");
        parte1.append(" select distinct m.id_dig_instrumento");
        parte1.append(" from dig_elementos m");
        for (String campo : camposPrincipales) {
            parte1.append(" inner join dig_respuestas d").append(i).append(" on d").append(i).append(".id_dig_elemento=m.id and d").append(i).append(".cod_respuesta = '").append(campo).append("' and d").append(i).append(".valor in ('3','4')");
            i++;
        }
        parte1.append(")");
        //CUARENTA_UNO
        parte1.append(",");
        parte1.append(" cuarenta_uno as (");
        parte1.append(" select distinct m.id_dig_instrumento");
        parte1.append(" from dig_elementos m");
        for (String campo : camposPrincipales) {
            parte1.append(" inner join dig_respuestas d").append(i).append(" on d").append(i).append(".id_dig_elemento=m.id and d").append(i).append(".cod_respuesta = '").append(campo).append("'");
            i++;
        }
        parte1.append(" where m.id_dig_instrumento in (select mm.id_dig_instrumento");
        parte1.append(" from dig_elementos mm, dig_respuestas dd where dd.id_dig_elemento=mm.id and dd.cod_respuesta in ('");
        i = 0;
        for (String campo : camposOtros) {
            if (i > 0) {
                parte1.append("','");
            }
            parte1.append(campo);
            i++;
        }
        parte1.append("') and dd.valor in ('3','4'))");
        parte1.append(")");
        //CUARENTA_DOS
        parte1.append(",");
        parte1.append(" cuarenta_dos as (");
        parte1.append(" select distinct m.id_dig_instrumento");
        parte1.append(" from dig_elementos m");

        parte1.append(" where m.id_dig_instrumento in (select mm.id_dig_instrumento");
        parte1.append(" from dig_elementos mm, dig_respuestas dd where dd.id_dig_elemento=mm.id and dd.cod_respuesta in ('");
        i = 0;
        for (String campo : camposPrincipales) {
            if (i > 0) {
                parte1.append("','");
            }
            parte1.append(campo);
            i++;
        }
        parte1.append("')  and dd.valor in ('3','4'))");

        parte1.append(" and m.id_dig_instrumento in (select mmm.id_dig_instrumento");
        parte1.append(" from dig_elementos mmm, dig_respuestas ddd where ddd.id_dig_elemento=mmm.id and ddd.cod_respuesta in ('");
        i = 0;
        for (String campo : camposOtros) {
            if (i > 0) {
                parte1.append("','");
            }
            parte1.append(campo);
            i++;
        }
        parte1.append("') and ddd.valor in ('3','4'))");
        parte1.append(")");
        //VEINTE_UNO
        parte1.append(",");
        parte1.append(" veinte_uno as (");
        parte1.append(" select distinct m.id_dig_instrumento");
        parte1.append(" from dig_elementos m");
        for (String campo : camposPrincipales) {
            parte1.append(" inner join dig_respuestas d").append(i).append(" on d").append(i).append(".id_dig_elemento=m.id and d").append(i).append(".cod_respuesta = '").append(campo).append("'");
            i++;
        }
        parte1.append(")");
        //VEINTE_DOS
        parte1.append(",");
        parte1.append(" veinte_dos as (");
        parte1.append(" select distinct m.id_dig_instrumento");
        parte1.append(" from dig_elementos m, dig_respuestas d where d.id_dig_elemento=m.id and d.cod_respuesta in ('");
        i = 0;
        for (String campo : camposPrincipales) {
            if (i > 0) {
                parte1.append("','");
            }
            parte1.append(campo);
            i++;
        }
        parte1.append("')  and d.valor in ('3','4')");
        parte1.append(")");
        //DIEZ
        parte1.append(",");
        parte1.append(" diez as (");
        parte1.append(" select distinct m.id_dig_instrumento");
        parte1.append(" from dig_elementos m, dig_respuestas d where d.id_dig_elemento=m.id and d.cod_respuesta in ('");
        i = 0;
        for (String campo : camposOtros) {
            if (i > 0) {
                parte1.append("','");
            }
            parte1.append(campo);
            i++;
        }
        parte1.append("') and d.valor in ('3','4')");
        parte1.append(")");



        parte1.append(" select distinct m.id_dig_instrumento");
        parte1.append(", case");
        parte1.append(" when m.id_dig_instrumento in (select id_dig_instrumento from cien) then 100");
        parte1.append(" when m.id_dig_instrumento in (select id_dig_instrumento from ochenta) then 80");
        parte1.append(" when m.id_dig_instrumento in (select id_dig_instrumento from sesenta) then 60");
        parte1.append(" when m.id_dig_instrumento in (select id_dig_instrumento from cuarenta_uno) then 40");
        parte1.append(" when m.id_dig_instrumento in (select id_dig_instrumento from cuarenta_dos) then 40");
        parte1.append(" when m.id_dig_instrumento in (select id_dig_instrumento from veinte_uno) then 20");
        parte1.append(" when m.id_dig_instrumento in (select id_dig_instrumento from veinte_dos) then 20");
        parte1.append(" when m.id_dig_instrumento in (select id_dig_instrumento from diez) then 10");
        parte1.append(" else 0 end calif");
        parte1.append(" from dig_elementos m");



        if (!ParamNivelConsul.PREDIO.getNivel().equals(nivelAgregacion)) {

            parte1.append(" ) calif on calif.id_dig_instrumento = xx.id");
            parte1.append(" inner join (select mm.id_dig_instrumento, char_to_double(dd.valor) area_predio from dig_respuestas dd, dig_elementos mm");
            parte1.append(" where dd.id_dig_elemento=mm.id and dd.valor is not null and dd.cod_respuesta = 'RESP_043') area on area.id_dig_instrumento=xx.id");

            parte1.append(" inner join ind_auxiliar_indicador aux on aux.id_predio=xx.id_Predio");
            parte1.append(" inner join ind_auxiliar_consultas n on n.id_predio = aux.id_predio and n.periodo = aux.periodo and n.id_tnt = aux.id_tnt");

            parte1.append(" inner join");
            parte1.append(" (");
            parte1.append(" select n.").append(campoSelect).append(", sum(area.area_predio) area from dig_instrumentos xxx");

            parte1.append(" inner join (select mm.id_dig_instrumento, char_to_double(dd.valor) area_predio from dig_respuestas dd, dig_elementos mm");
            parte1.append(" where dd.id_dig_elemento=mm.id and dd.valor is not null and dd.cod_respuesta = 'RESP_043') area on area.id_dig_instrumento=xxx.id");

            parte1.append(" inner join ind_auxiliar_indicador aux on aux.id_predio=xxx.id_Predio");
            parte1.append(" inner join ind_auxiliar_consultas n on n.id_predio = aux.id_predio and n.periodo = aux.periodo and n.id_tnt = aux.id_tnt");
            parte1.append(" where xxx.estado=?estado");
            parte1.append(" group by n.").append(campoSelect).append("");
            parte1.append(" ) areaTodos on areaTodos.").append(campoSelect).append("=n.").append(campoSelect);

            parte1.append(" ) datos on datos.id=x.id");



            parte1.append(" inner join ind_auxiliar_indicador aux on aux.id_predio=x.id_Predio");
            parte1.append(" inner join ind_auxiliar_consultas n on n.id_predio = aux.id_predio and n.periodo = aux.periodo and n.id_tnt = aux.id_tnt");
            parte1.append(" where x.estado=?estado");
            parte1.append(" group by n.").append(campoSelect);
        }
        return parte1.toString();
    }

    private List<Object[]> obtenerServiciosPublicos() {
        StringBuilder sql = new StringBuilder();
        sql.append("select servicio, tipo from CAL_SERVICIOS_PUBLICOS where (tipo='P' or tipo='O') and id_tnt=?id_tnt");

        Query query = tpm.getEm().createNativeQuery(sql.toString());
        query.setParameter("id_tnt", tpm.getCurrentTenant());
        return query.getResultList();
    }

    private String calcularPartesFormulaAmbientes(Long nivelAgregacion) {
        //Es obligatorio tener 2 ambientes principales. 
        //Se puede tener uno o varios ambientes secundarios
        List<String> principales = new ArrayList<String>();
        List<String> otros = new ArrayList<String>();

        //A nivel de presio siempre se mirarán aulas y servicios sanitarios como principales y expansiones recreatvas como secundario
        if (ParamNivelConsul.PREDIO.getNivel().equals(nivelAgregacion)) {
            principales.add(EspacioFuncional.AULA.name());
            principales.add(EspacioFuncional.SERVICIOS_SANITARIOS.name());
            otros.add(EspacioFuncional.EXPANSIONES_RECREATIVAS.name());
        } else {
            //Para el resto se consultan los indicados por administración
            List<Object[]> amb_principales_otros = obtenerAmbientes();
            for (Object[] obj : amb_principales_otros) {
                if (PrincipalOtro.P.toString().equals(obj[1])) {
                    principales.add(obj[0].toString());
                } else {
                    otros.add(obj[0].toString());
                }
            }
        }

        String campoSelect = "";
        if (ParamNivelConsul.PREDIO.getNivel().equals(nivelAgregacion)) {
            campoSelect = "id_dig_instrumento";
        } else if (ParamNivelConsul.CERO.getNivel().equals(nivelAgregacion)) {
            campoSelect = "id_nivel0";
        } else if (ParamNivelConsul.UNO.getNivel().equals(nivelAgregacion)) {
            campoSelect = "id_nivel1";
        } else if (ParamNivelConsul.DOS.getNivel().equals(nivelAgregacion)) {
            campoSelect = "id_nivel2";
        } else if (ParamNivelConsul.TRES.getNivel().equals(nivelAgregacion)) {
            campoSelect = "id_nivel3";
        } else if (ParamNivelConsul.CUATRO.getNivel().equals(nivelAgregacion)) {
            campoSelect = "id_nivel4";
        } else if (ParamNivelConsul.CINCO.getNivel().equals(nivelAgregacion)) {
            campoSelect = "id_nivel5";
        } else if (ParamNivelConsul.ESTABLECIMIENTO.getNivel().equals(nivelAgregacion)) {
            campoSelect = "id_establecimiento";
        } else if (ParamNivelConsul.SEDE.getNivel().equals(nivelAgregacion)) {
            campoSelect = "id_sede";
        }

        /**
         * ******************** Query sacada de IND_AMBIENTE_AREA_TIPO_ESPACIO ********************************
         */
        StringBuilder sqlPart1 = new StringBuilder();
        StringBuilder sqlPart11 = new StringBuilder();
        sqlPart1.append("select ").append(campoSelect).append(" from (");

        if (!ParamNivelConsul.PREDIO.getNivel().equals(nivelAgregacion)) {
            sqlPart1.append("select n.").append(campoSelect);
            sqlPart1.append(", sum(datos.area_tipo_espacio) area_tipo_espacio, sum(datos.num_estudiantes_max_jornada) num_estudiantes_max_jornada");
            sqlPart1.append(", datos.estandar");
            sqlPart1.append(" from dig_instrumentos x");
            sqlPart1.append(" left join(");
        }
        sqlPart1.append(" select  aux.id_dig_instrumento");
        sqlPart1.append(" ,tbl_area.area_tipo_espacio ,tbl_est_mayjornada.num_estudiantes_max_jornada");
        sqlPart1.append(" ,est.estandar");
        sqlPart1.append(" from (select e.valor estandar from ind_estandares e where codigo = 'ESTANDAR_");

        sqlPart11.append("'  and id_tnt=?id_tnt) est,"); //estandar para Aula
        sqlPart11.append(" ind_auxiliar_indicador aux");
        sqlPart11.append(" inner join ind_auxiliar_consultas n on n.id_predio = aux.id_predio and n.periodo = aux.periodo and n.id_tnt = aux.id_tnt");

        sqlPart11.append(" left join (select id_tnt, periodo,id_dig_instrumento,id_predio,sum(coalesce(area_tipo_espacio * (total_espacios_similares + 1),0)) area_tipo_espacio");
        sqlPart11.append(" from (select aux.id_tnt,aux.periodo,i.id id_dig_instrumento,i.id_predio,coalesce(sim.total_ambientes_similares,0) total_espacios_similares, sum(d.valor::float) area_tipo_espacio");
        sqlPart11.append(" from dig_instrumentos i");
        sqlPart11.append(" inner join ind_auxiliar_indicador aux on aux.id_dig_instrumento = i.id");
        sqlPart11.append(" inner join dig_elementos e on e.id_dig_instrumento = i.id and e.cod_tipo='ELE_ESP'");
        sqlPart11.append(" inner join dig_respuestas d on d.id_dig_elemento = e.id and d.cod_pregunta='PREG_104'"); //area
        sqlPart11.append(" inner join dig_respuestas d4 on d4.id_dig_elemento = e.id and d4.cod_pregunta='PREG_094'"); //espacio
        sqlPart11.append(" inner join dig_respuestas d3 on d3.id_dig_elemento = e.id and d3.cod_pregunta='PREG_095'"); //edificio
        sqlPart11.append(" inner join dig_respuestas d2 on d2.id_dig_elemento = e.id and d2.cod_pregunta='PREG_099' and d2.valor='1'"); //espacios activos
        sqlPart11.append(" inner join dig_respuestas d1 on d1.id_dig_elemento = e.id and d1.cod_pregunta='PREG_098' and d1.valor in (");

        sqlPart11.append(" select u2.valor_tipologia from ins_unidades_funcionales u1, ins_unidades_funcionales_valores u2");
        sqlPart11.append(" where u1.id=u2.id_unidad_funcional and u1.codigo in ('");

        StringBuilder sqlPart2 = new StringBuilder();
        sqlPart2.append("')");
        sqlPart2.append(")");
        sqlPart2.append(" inner join ins_respuestas r1 on r1.codigo = d.cod_respuesta");
        sqlPart2.append(" left join (");
        sqlPart2.append(" select i.id id_dig_instrumento,i.id_predio,");
        sqlPart2.append(" r1.valor id_espacio,r2.valor id_edificio,");
        sqlPart2.append(" count(r5.valor) total_ambientes_similares");
        sqlPart2.append(" from dig_instrumentos i");
        sqlPart2.append(" inner join dig_elementos e1 on e1.id_dig_instrumento = i.id");
        sqlPart2.append(" inner join dig_respuestas r1 on r1.id_dig_elemento = e1.id and r1.cod_respuesta = 'RESP_094'"); //id espacio
        sqlPart2.append(" inner join dig_respuestas r2 on r2.id_dig_elemento = e1.id and r2.cod_respuesta = 'RESP_095'"); //id edificio
        sqlPart2.append(" left join dig_elementos e3 on e3.id_dig_instrumento = i.id");
//espacios complementarios o similares
        sqlPart2.append(" inner join dig_respuestas r3 on r3.id_dig_elemento = e3.id");
        sqlPart2.append(" and r3.cod_respuesta = 'RESP_142' and r3.valor = r1.valor"); //id espacio
        sqlPart2.append(" inner join dig_respuestas r4 on r4.id_dig_elemento = e3.id");
        sqlPart2.append(" and r4.cod_respuesta = 'RESP_143' and r4.valor = r2.valor"); //id edificio
        sqlPart2.append(" left join dig_respuestas r5 on r5.id_dig_elemento = e3.id and r5.cod_pregunta = 'PREG_145'");//ambientes similares
        sqlPart2.append(" group by i.id,i.id_predio,r1.valor,r2.valor");
        sqlPart2.append(" order by i.id,i.id_predio,r1.valor,r2.valor");
        sqlPart2.append(" ) sim on sim.id_dig_instrumento = i.id and sim.id_espacio = d4.valor and sim.id_edificio = d3.valor");
        sqlPart2.append(" group by 1,2,3,4,5) tb");
        sqlPart2.append(" group by 1,2,3,4");
        sqlPart2.append(" order by 1,2,3,4) tbl_area on tbl_area.id_dig_instrumento = aux.id_dig_instrumento and tbl_area.id_predio = aux.id_predio");

        sqlPart2.append(" inner join (select jor.periodo,jor.id_dig_instrumento,jor.id_predio,max(jor.total_estudiantes_jornada) num_estudiantes_max_jornada");
        sqlPart2.append(" from(select aux.periodo,i.id id_dig_instrumento,i.id_predio,");
        sqlPart2.append(" r1.valor codigo_jornada,t.codigo||t.cod_tipologia,");
        sqlPart2.append(" coalesce(sum(to_number(r2.valor,'9999999')),0) total_estudiantes_jornada");
        sqlPart2.append(" from dig_instrumentos i inner join ind_auxiliar_indicador aux on aux.id_dig_instrumento = i.id");
        sqlPart2.append(" inner join dig_elementos e1 on e1.id_dig_instrumento = i.id and e1.cod_tipo = 'ELE_EST'"); // jornada
        sqlPart2.append(" inner join dig_respuestas r1 on r1.id_dig_elemento = e1.id and r1.cod_pregunta in ('PREG_032', 'PREG_198')"); //jornada
        sqlPart2.append(" inner join ins_respuestas d1 on d1.codigo = r1.cod_respuesta"); // total estudiantes
        sqlPart2.append(" inner join dig_respuestas r2 on r2.id_dig_elemento = e1.id and r2.cod_pregunta in ('PREG_035','PREG_201')"); //matricula
        sqlPart2.append(" inner join ins_respuestas d2 on d2.codigo = r2.cod_respuesta and d2.fila = d1.fila");
        sqlPart2.append(" inner join ins_tipologia_valores t on t.codigo = r1.valor and t.cod_tipologia ='JT'");
        sqlPart2.append(" group by aux.periodo,i.id,i.id_predio,r1.valor,t.codigo||t.cod_tipologia");
        sqlPart2.append(" order by aux.periodo,i.id,i.id_predio,r1.valor,t.codigo||t.cod_tipologia");
        sqlPart2.append(" ) jor");
        sqlPart2.append(" group by jor.periodo,jor.id_dig_instrumento,jor.id_predio ) tbl_est_mayjornada on tbl_est_mayjornada.id_dig_instrumento = aux.id_dig_instrumento and tbl_est_mayjornada.id_predio = aux.id_predio");

        sqlPart2.append(") datos");


        if (!ParamNivelConsul.PREDIO.getNivel().equals(nivelAgregacion)) {
            sqlPart2.append(" on datos.id_dig_instrumento=x.id");

            sqlPart2.append(" inner join ind_auxiliar_indicador aux on aux.id_predio=x.id_Predio");
            sqlPart2.append(" inner join ind_auxiliar_consultas n on n.id_predio = aux.id_predio and n.periodo = aux.periodo and n.id_tnt = aux.id_tnt");
            sqlPart2.append(" group by n.").append(campoSelect).append(", estandar");
            sqlPart2.append(") tbl");
        }

        //cumplimiento norma
        sqlPart2.append(" where ((area_tipo_espacio/(num_estudiantes_max_jornada*estandar))*100)>=100");


        //Empezamos a armar la query
        StringBuilder parte1 = new StringBuilder();

        int i = 0;
        //Hacemos with de cada principal y cada Otro
        for (String val : principales) {
            if (i == 0) {
                parte1.append("with principal").append(i).append(" as ( ");
            } else {
                parte1.append(", principal").append(i).append(" as ( ");
            }
            parte1.append(sqlPart1);
            parte1.append(val);
            parte1.append(sqlPart11);
            parte1.append(val);
            parte1.append(sqlPart2);
            parte1.append(")");
            i++;
        }
        i = 0;
        for (String val : otros) {
            parte1.append(", otro").append(i).append(" as ( ");
            parte1.append(sqlPart1);
            parte1.append(val);
            parte1.append(sqlPart11);
            parte1.append(val);
            parte1.append(sqlPart2);
            parte1.append(")");
            i++;
        }
        //CIEN
        parte1.append(", ambientes_cien as ( select distinct ");
        if (ParamNivelConsul.PREDIO.getNivel().equals(nivelAgregacion)) {
            campoSelect = "m." + campoSelect;
            parte1.append("m.id_dig_instrumento from dig_elementos m where m.id_dig_instrumento in (");
        } else {
            campoSelect = "n." + campoSelect;
            parte1.append(campoSelect);
            parte1.append(" from ind_auxiliar_consultas n where ").append(campoSelect).append(" in (");
        }
        i = 0;
        for (String val : principales) {
            if (i > 0) {
                parte1.append(" and ").append(campoSelect).append(" in (");
            }
            parte1.append("select ").append(campoSelect.substring(2, campoSelect.length())).append(" from principal").append(i);
            parte1.append(")");
            i++;
        }
        i = 0;
        for (String val : otros) {
            parte1.append(" and ").append(campoSelect).append(" in (");
            parte1.append("select ").append(campoSelect.substring(2, campoSelect.length())).append(" from otro").append(i);
            parte1.append(")");
            i++;
        }
        parte1.append(")");
        //OCHENTA
        parte1.append(", ambientes_ochenta as ( select distinct ").append(campoSelect);
        if (ParamNivelConsul.PREDIO.getNivel().equals(nivelAgregacion)) {
            parte1.append(" from dig_elementos m where m.id_dig_instrumento in (");
        } else {
            parte1.append(" from ind_auxiliar_consultas n where ").append(campoSelect).append(" in (");
        }
        i = 0;
        for (String val : principales) {
            if (i > 0) {
                parte1.append(" and ").append(campoSelect).append(" in (");
            }
            parte1.append("select ").append(campoSelect.substring(2, campoSelect.length())).append(" from principal").append(i);
            parte1.append(")");
            i++;
        }
        i = 0;
        for (String val : otros) {
            if (i == 0) {
                parte1.append(" and (").append(campoSelect).append(" in (");
            } else {
                parte1.append(" or ").append(campoSelect).append(" in (");
            }
            parte1.append("select ").append(campoSelect.substring(2, campoSelect.length())).append(" from otro").append(i);
            parte1.append(")");
            i++;
        }
        if (!otros.isEmpty()) {
            parte1.append(")");
        }

        parte1.append(")");
        //SESENTA
        parte1.append(", ambientes_sesenta as ( select distinct ").append(campoSelect);
        if (ParamNivelConsul.PREDIO.getNivel().equals(nivelAgregacion)) {
            parte1.append(" from dig_elementos m where m.id_dig_instrumento in (");
        } else {
            parte1.append(" from ind_auxiliar_consultas n where ").append(campoSelect).append(" in (");
        }
        i = 0;
        for (String val : principales) {
            if (i > 0) {
                parte1.append(" and ").append(campoSelect).append(" in (");
            }
            parte1.append("select ").append(campoSelect.substring(2, campoSelect.length())).append(" from principal").append(i);
            parte1.append(")");
            i++;
        }
        parte1.append(")");
        //CUARENTA
        parte1.append(", ambientes_cuarenta as ( select distinct ").append(campoSelect);
        if (ParamNivelConsul.PREDIO.getNivel().equals(nivelAgregacion)) {
            parte1.append(" from dig_elementos m where (m.id_dig_instrumento in (");
        } else {
            parte1.append(" from ind_auxiliar_consultas n where (").append(campoSelect).append(" in (");
        }

        i = 0;
        for (String val : principales) {
            if (i > 0) {
                parte1.append(" or ").append(campoSelect).append(" in (");
            }
            parte1.append("select ").append(campoSelect.substring(2, campoSelect.length())).append(" from principal").append(i);
            parte1.append(")");
            i++;
        }
        parte1.append(")");

        i = 0;
        for (String val : otros) {
            if (i == 0) {
                parte1.append(" and (").append(campoSelect).append(" in (");
            } else {
                parte1.append(" or ").append(campoSelect).append(" in (");
            }
            parte1.append("select ").append(campoSelect.substring(2, campoSelect.length())).append(" from otro").append(i);
            parte1.append(")");
            i++;
        }
        if (!otros.isEmpty()) {
            parte1.append(")");
        }
        parte1.append(")");
        //VEINTE
        parte1.append(", ambientes_veinte as ( select distinct ").append(campoSelect);
        if (ParamNivelConsul.PREDIO.getNivel().equals(nivelAgregacion)) {
            parte1.append(" from dig_elementos m where (m.id_dig_instrumento in (");
        } else {
            parte1.append(" from ind_auxiliar_consultas n where (").append(campoSelect).append(" in (");
        }

        i = 0;
        for (String val : principales) {
            if (i > 0) {
                parte1.append(" or ").append(campoSelect).append(" in (");
            }
            parte1.append("select ").append(campoSelect.substring(2, campoSelect.length())).append(" from principal").append(i);
            parte1.append(")");
            i++;
        }
        parte1.append(")");
        parte1.append(")");
        //DIEZ
        if (!otros.isEmpty()) {
            parte1.append(", ambientes_diez as ( select distinct ").append(campoSelect);
            if (ParamNivelConsul.PREDIO.getNivel().equals(nivelAgregacion)) {
                parte1.append(" from dig_elementos m where (m.id_dig_instrumento in (");
            } else {
                parte1.append(" from ind_auxiliar_consultas n where (").append(campoSelect).append(" in (");
            }
            i = 0;
            for (String val : otros) {
                if (i > 0) {
                    parte1.append(" or ").append(campoSelect).append(" in (");
                }
                parte1.append("select ").append(campoSelect.substring(2, campoSelect.length())).append(" from otro").append(i);
                parte1.append(")");
                i++;
            }
            parte1.append(")");
            parte1.append(")");
        }

        parte1.append(" select distinct ").append(campoSelect);
        parte1.append(", case");
        parte1.append(" when ").append(campoSelect).append(" in (select ").append(campoSelect.substring(2, campoSelect.length())).append(" from ambientes_cien) then 100");
        parte1.append(" when ").append(campoSelect).append(" in (select ").append(campoSelect.substring(2, campoSelect.length())).append(" from ambientes_ochenta) then 80");
        parte1.append(" when ").append(campoSelect).append(" in (select ").append(campoSelect.substring(2, campoSelect.length())).append(" from ambientes_sesenta) then 60");
        parte1.append(" when ").append(campoSelect).append(" in (select ").append(campoSelect.substring(2, campoSelect.length())).append(" from ambientes_cuarenta) then 40");
        parte1.append(" when ").append(campoSelect).append(" in (select ").append(campoSelect.substring(2, campoSelect.length())).append(" from ambientes_veinte) then 20");
        if (!otros.isEmpty()) {
            parte1.append(" when ").append(campoSelect).append(" in (select ").append(campoSelect.substring(2, campoSelect.length())).append(" from ambientes_diez) then 10");
        }
        parte1.append(" else 0 end calif");
        if (ParamNivelConsul.PREDIO.getNivel().equals(nivelAgregacion)) {
            parte1.append(" from dig_elementos m");
        } else {
            parte1.append(" from ind_auxiliar_consultas n");
        }

        return parte1.toString();
    }

    private List<Object[]> obtenerAmbientes() {
        StringBuilder sql = new StringBuilder();
        sql.append("select ambiente, tipo from CAL_AMBIENTES where (tipo='P' or tipo='O') and id_tnt=?id_tnt");

        Query query = tpm.getEm().createNativeQuery(sql.toString());
        query.setParameter("id_tnt", tpm.getCurrentTenant());
        return query.getResultList();
    }

    @Override
    public List<Object[]> obtenerValorEscala(String codAmbito, BigDecimal valor) {
        StringBuilder sql = new StringBuilder();
        sql.append("select");
        sql.append(" case when original.valor_desde is null then aumentado.valor_desde else original.valor_desde end,");
        sql.append(" case when original.valor_desde is null then aumentado.valor_hasta else original.valor_hasta end,");
        sql.append(" case when original.valor_desde is null then aumentado.codigo else original.codigo end");
        sql.append(" from");
        sql.append(" ins_ambitos a");
        sql.append(" left join (select valor_desde, valor_hasta, codigo, COD_AMBITO from CAL_ESCALA where COD_AMBITO=?codAmbito and case when ?valor +1 > 100 then 100 else ?valor +1 end between valor_desde and valor_hasta) aumentado");
        sql.append(" on aumentado.COD_AMBITO=a.codigo");
        sql.append(" left join (select valor_desde, valor_hasta, codigo, COD_AMBITO from CAL_ESCALA where COD_AMBITO=?codAmbito and ?valor between valor_desde and valor_hasta) original");
        sql.append(" on original.COD_AMBITO=a.codigo");
        sql.append(" where a.codigo=?codAmbito");

        Query query = tpm.getEm().createNativeQuery(sql.toString());
        query.setParameter("codAmbito", codAmbito);
        query.setParameter("valor", valor);

        return query.getResultList();
    }

    @Override
    public void crearDatosCalificacion(int periodo) throws ErrorIntegridad {

        try {

            //Se eliminan las calificaciones antiguas del periodo
            tpm.getEm().clear();
            tpm.getEm().createNamedQuery("CalificacionValor.deleteAll")
                    .setParameter("periodo", periodo)
                    .executeUpdate();

            for (ParamNivelConsul nivelConsulta : ParamNivelConsul.values()) {

                List<Object[]> variables = obtenerVariables(nivelConsulta.getNivel());



                //Servicios no tiene sql en BBDD sino que se calcula en calcularPartesFormulaServicios
                String parteServicios = calcularPartesFormulaServicios(nivelConsulta.getNivel());
                //Ambientes no tiene sql en BBDD sino que se calcula en calcularPartesFormulaAmbientes
                String parteAmbientes = calcularPartesFormulaAmbientes(nivelConsulta.getNivel());


                StringBuilder sql = new StringBuilder();
                //SELECT
                //sql.append(partesAmbientes.get(0));

                String campo = "";
                if (ParamNivelConsul.PREDIO.equals(nivelConsulta.getNivel())) {
                    campo = "id_predio";
                } else if (ParamNivelConsul.CERO.equals(nivelConsulta.getNivel())) {
                    campo = "id_nivel0";
                } else if (ParamNivelConsul.UNO.equals(nivelConsulta.getNivel())) {
                    campo = "id_nivel1";
                } else if (ParamNivelConsul.DOS.equals(nivelConsulta.getNivel())) {
                    campo = "id_nivel2";
                } else if (ParamNivelConsul.TRES.equals(nivelConsulta.getNivel())) {
                    campo = "id_nivel3";
                } else if (ParamNivelConsul.CUATRO.equals(nivelConsulta.getNivel())) {
                    campo = "id_nivel4";
                } else if (ParamNivelConsul.CINCO.equals(nivelConsulta.getNivel())) {
                    campo = "id_nivel5";
                } else if (ParamNivelConsul.ESTABLECIMIENTO.equals(nivelConsulta.getNivel())) {
                    campo = "id_establecimiento";
                } else if (ParamNivelConsul.SEDE.equals(nivelConsulta.getNivel())) {
                    campo = "id_sede";
                }

                sql.append("select distinct n.").append(campo);

                for (Object[] var : variables) {
                    sql.append(",ROUND(");
                    sql.append(" COALESCE(valor").append(var[1].toString()).append(".calif,0) ");
                    sql.append(",4)");
                }


                //FROM
                sql.append(" from dig_instrumentos x");
                sql.append(" inner join ind_auxiliar_indicador aux on aux.id_predio=x.id_Predio");
                sql.append(" inner join ind_auxiliar_consultas n on n.id_predio = aux.id_predio and n.periodo = aux.periodo and n.id_tnt = aux.id_tnt");

                //JOINS

                for (Object[] var : variables) {
                    sql.append(" left join (");
                    if (var[2].toString().equals(Ambitos.A_SP.toString())) {
                        sql.append(parteServicios);
                    } else if (var[2].toString().equals(Ambitos.A_AMB.toString())) {
                        sql.append(parteAmbientes);
                    } else {
                        sql.append(var[0].toString());
                    }
                    sql.append(") valor").append(var[1].toString());
                    if (ParamNivelConsul.PREDIO.getNivel().equals(nivelConsulta.getNivel())) {
                        sql.append(" on x.id=valor").append(var[1].toString()).append(".id_dig_instrumento");
                    } else {
                        sql.append(" on n.").append(campo).append("=valor").append(var[1].toString()).append(".").append(campo);
                    }
                }


                //WHERE
                sql.append(" where");
                sql.append(" x.id_tnt = ?id_tnt");
                sql.append(" and x.version = (select max(xx.version) from dig_instrumentos xx where x.id_Predio=xx.id_Predio and xx.estado=?estado)");
                sql.append(" and x.estado=?estado");



                Query query = tpm.getEm().createNativeQuery(sql.toString());
                query.setParameter("id_tnt", tpm.getCurrentTenant());
                query.setParameter("estado", EstadoInstrumento.E.name());

                List<Object[]> resultados = query.getResultList();


                int i = 0;
                boolean primero = false;
                Long idEntidad = null;
                for (Object[] resul : resultados) {
                    i = 0;
                    primero = true;
                    if (resul[0] != null) {
                        for (Object res : resul) {
                            if (primero) {
                                idEntidad = ((BigDecimal) res).longValue();
                                primero = false;
                            } else {
                                CalificacionValor calValor = new CalificacionValor();

                                calValor.setIdVariableAmbito(new Integer(variables.get(i)[1].toString()));
                                calValor.setId_entidad(idEntidad);
                                calValor.setValor(res.toString());
                                calValor.setNivel_consulta(nivelConsulta.getNivel().intValue());
                                calValor.setPeriodo(periodo);
                                tpm.getEm().clear();
                                tpm.getEm().persist(calValor);
                                tpm.getEm().flush();
                                i++;
                            }
                        }
                    }
                }



            }//END FOR nivelConsulta

        } catch (Exception e) {
            UtilOut.println(e);
            throw new ErrorIntegridad("Error en crearDatosCalificacion");
        }
    }

    @Override
    public List<Object[]> obtenerVariablesPais() {

        StringBuilder sql = new StringBuilder();
        //SELECT
        //sql.append(partesAmbientes.get(0));
        sql.append("select distinct n.id_nivel0,");
        sql.append(" variables.id, variables.valor");
        //FROM
        sql.append(" from dig_instrumentos x");
        sql.append(" inner join ind_auxiliar_indicador aux on aux.id_predio=x.id_Predio");
        sql.append(" inner join ind_auxiliar_consultas n on n.id_predio = aux.id_predio and n.periodo = aux.periodo and n.id_tnt = aux.id_tnt");

        sql.append(" inner join (");
        sql.append(" select char_to_double(val.valor) valor");
        sql.append(" , val.id_entidad, v.id");
        sql.append(" from CAL_CALIFICA_VARIABLES_AMB c, CAL_VARIABLES_AMBITO v, CAL_VALORES val");
        sql.append(" where  v.ID=c.ID_VAR_AMB");
        sql.append(" and val.id_var_amb=c.id_var_amb and val.nivel_consulta=c.nivel_consulta");
        sql.append(" and c.NIVEL_CONSULTA=0");
        sql.append(" and val.periodo=(select max(val2.periodo) from CAL_VALORES val2)");
        sql.append(" ) variables on variables.id_entidad=n.id_nivel0");

        //WHERE
        sql.append(" where");
        sql.append(" x.id_tnt = ?id_tnt");
        sql.append(" and x.version = (select max(xx.version) from dig_instrumentos xx where x.id_Predio=xx.id_Predio and xx.estado=?estado)");
        sql.append(" and x.estado=?estado");


        Query query = tpm.getEm().createNativeQuery(sql.toString());
        query.setParameter("id_tnt", tpm.getCurrentTenant());
        query.setParameter("estado", EstadoInstrumento.E.toString());


        return query.getResultList();
    }
}
