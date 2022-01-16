/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.fachada;

import co.stackpointer.cier.modelo.utilidad.UtilOut;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Stateless
public class InformeGeneralFacade  implements InformeGeneralFacadeLocal{
    
    @Inject
    private TenantPersistenceManager tpm;
         
    @Override
    public String consultarCodTipologiaRespuesta(String codRespuesta){
       StringBuilder sql = new StringBuilder();
        
        sql.append("SELECT distinct c.cod_tipologia FROM INS_TIPOLOGIAS_CONDICION c, INS_RESPUESTAS padre, INS_RESPUESTAS hijo");
        sql.append(" where");
        sql.append(" hijo.cod_res_padre=padre.codigo and padre.cod_tipologia=c.cod_tipologia");
        sql.append(" and hijo.codigo=?codRespuesta");
        
        Query query = tpm.getEm().createNativeQuery(sql.toString());
        query.setParameter("codRespuesta", codRespuesta);

        
        return query.getSingleResult()!=null?query.getSingleResult().toString():"";
    } 
    
     @Override
    public String obtenerAreasVerdes(Long idDigInstrumento) throws Exception{
        String areasVerdes="";
        try{            
            StringBuilder sql = new StringBuilder();

            sql.append("with similares as (");
            sql.append(" select i.id id_dig_instrumento,i.id_predio,");
            sql.append(" r1.valor id_espacio,r2.valor id_edificio,");
            sql.append(" count(r5.valor) total_ambientes_similares");
            sql.append(" from dig_instrumentos i");
            sql.append(" inner join dig_elementos e1 on e1.id_dig_instrumento = i.id");
            sql.append(" inner join dig_respuestas r1 on r1.id_dig_elemento = e1.id and r1.cod_respuesta = 'RESP_094'"); //id espacio
            sql.append(" inner join dig_respuestas r2 on r2.id_dig_elemento = e1.id and r2.cod_respuesta = 'RESP_095'"); //id edificio
            sql.append(" left join dig_elementos e3 on e3.id_dig_instrumento = i.id");
    //espacios complementarios o similares
            sql.append(" inner join dig_respuestas r3 on r3.id_dig_elemento = e3.id");
            sql.append(" and r3.cod_respuesta = 'RESP_142' and r3.valor = r1.valor"); //id espacio
            sql.append(" inner join dig_respuestas r4 on r4.id_dig_elemento = e3.id");
            sql.append(" and r4.cod_respuesta = 'RESP_143' and r4.valor = r2.valor"); //id edificio
            sql.append(" left join dig_respuestas r5 on r5.id_dig_elemento = e3.id and r5.cod_pregunta = 'PREG_145'");//ambientes similares
            sql.append(" group by i.id,i.id_predio,r1.valor,r2.valor");
            sql.append(" order by i.id,i.id_predio,r1.valor,r2.valor"); 
            sql.append(" )");
            sql.append(" select");
            sql.append(" sum(to_number(d.valor,'9999999.999'))");
            sql.append(" from");
            sql.append(" dig_respuestas d");
            sql.append(" inner join dig_elementos m on d.id_dig_elemento=m.id");
            sql.append(" inner join dig_respuestas r10 on r10.id_dig_elemento = d.id_dig_elemento and r10.cod_respuesta = 'RESP_094'");
            sql.append(" inner join dig_respuestas r20 on r20.id_dig_elemento = d.id_dig_elemento and r20.cod_respuesta = 'RESP_095'");
            sql.append(" left join similares sim on sim.id_dig_instrumento = m.id_dig_instrumento and sim.id_espacio = r10.valor and sim.id_edificio = r20.valor");
            sql.append(" where");
            sql.append(" d.cod_respuesta='RESP_104' and d.id_dig_elemento in");
            sql.append(" (");
            sql.append(" select dd.id_dig_elemento from dig_respuestas dd");
            sql.append(" where dd.cod_respuesta='RESP_098_01' and dd.valor='79'");
            sql.append(" )");
            sql.append(" and m.id_dig_instrumento=?idDigInstrumento");

            Query query = tpm.getEm().createNativeQuery(sql.toString());
            query.setParameter("idDigInstrumento", idDigInstrumento);

            areasVerdes=query.getSingleResult()!=null?query.getSingleResult().toString():"0";
        }
        catch(Exception e){
            throw new Exception("Error en obtenerAreasVerdes",e);
        }
        return areasVerdes;
    }

     @Override
     public boolean consumoEnergiaEncimaPromedio(Long idDigInstrumento) throws Exception{
         boolean consumoEnergiaEncimaPromedio=false;
        try{            
            StringBuilder sql = new StringBuilder();

            sql.append("select");
            sql.append(" (consumo_energia.consumo/personal.num_personas),(select e.valor from ind_estandares e where codigo = 'ESTANDAR_ENERGIA' and id_tnt=?id_tnt) calif");
            sql.append(" from dig_instrumentos di");
            sql.append(" inner join (select i.id, sum(to_number(dr1.valor, '999999999') +to_number(dr2.valor, '999999999') + to_number(dr3.valor, '999999999')+ to_number(dr4.valor, '999999999')) num_personas");
            sql.append(" from dig_instrumentos i");
            sql.append(" inner join dig_elementos e1 on e1.id_dig_instrumento = i.id");
            sql.append(" left join dig_respuestas dr1 on dr1.id_dig_elemento = e1.id");
            sql.append(" left join ins_respuestas ir1 on ir1.codigo = dr1.cod_respuesta");
            sql.append(" left join dig_respuestas dr2 on dr2.id_dig_elemento = e1.id");
            sql.append(" left join ins_respuestas ir2 on ir2.codigo = dr2.cod_respuesta");
            sql.append(" left join dig_respuestas dr3 on dr3.id_dig_elemento = e1.id");
            sql.append(" left join ins_respuestas ir3 on ir3.codigo = dr3.cod_respuesta");
            sql.append(" left join dig_respuestas dr4 on dr4.id_dig_elemento = e1.id");
            sql.append(" left join ins_respuestas ir4 on ir4.codigo = dr4.cod_respuesta");
            sql.append(" where e1.cod_tipo = 'ELE_EST'");
            sql.append(" and dr1.cod_pregunta in ('PREG_035', 'PREG_201')");
            sql.append(" and dr2.cod_pregunta in ('PREG_038', 'PREG_204')");
            sql.append(" and dr3.cod_pregunta in ('PREG_039','PREG_205')");
            sql.append(" and dr4.cod_pregunta in ('PREG_037', 'PREG_203')");
            sql.append(" and ir1.fila = ir2.fila");
            sql.append(" and ir2.fila = ir3.fila");
            sql.append(" and ir3.fila = ir4.fila");
            sql.append(" group by i.id) personal on personal.id=di.id");
            sql.append(" inner join (select m.id_dig_instrumento,");
            sql.append(" char_to_double(d.valor) consumo");
            sql.append(" from dig_elementos m, dig_respuestas d");
            sql.append(" where d.id_dig_elemento=m.id and d.cod_respuesta='RESP_062_06') consumo_energia on consumo_energia.id_dig_instrumento=di.id");
            sql.append(" where di.id=?idDigInstrumento");

            Query query = tpm.getEm().createNativeQuery(sql.toString());
            query.setParameter("id_tnt", tpm.getCurrentTenant());
            query.setParameter("idDigInstrumento", idDigInstrumento);
            List<Object[]> resultado=query.getResultList();
            if(resultado!=null && !resultado.isEmpty()){
                consumoEnergiaEncimaPromedio=new Double(resultado.get(0)[0]+"")>new Double(resultado.get(0)[1]+"")?true:false;
            }
        }
        catch(Exception e){
            throw new Exception("Error en consumoEnergiaEncimaPromedio",e);
        }
        return consumoEnergiaEncimaPromedio;
     }

     @Override
     public boolean consumoAguaEncimaPromedio(Long idDigInstrumento) throws Exception{
         boolean consumoAguaEncimaPromedio=false;
        try{            
            StringBuilder sql = new StringBuilder();

            sql.append("select");
            sql.append(" (consumo_energia.consumo/personal.num_personas),(select e.valor from ind_estandares e where codigo = 'ESTANDAR_AGUA' and id_tnt=?id_tnt) calif");
            sql.append(" from dig_instrumentos di");
            sql.append(" inner join (select i.id, sum(to_number(dr1.valor, '999999999') +to_number(dr2.valor, '999999999') + to_number(dr3.valor, '999999999')+ to_number(dr4.valor, '999999999')) num_personas");
            sql.append(" from dig_instrumentos i");
            sql.append(" inner join dig_elementos e1 on e1.id_dig_instrumento = i.id");
            sql.append(" left join dig_respuestas dr1 on dr1.id_dig_elemento = e1.id");
            sql.append(" left join ins_respuestas ir1 on ir1.codigo = dr1.cod_respuesta");
            sql.append(" left join dig_respuestas dr2 on dr2.id_dig_elemento = e1.id");
            sql.append(" left join ins_respuestas ir2 on ir2.codigo = dr2.cod_respuesta");
            sql.append(" left join dig_respuestas dr3 on dr3.id_dig_elemento = e1.id");
            sql.append(" left join ins_respuestas ir3 on ir3.codigo = dr3.cod_respuesta");
            sql.append(" left join dig_respuestas dr4 on dr4.id_dig_elemento = e1.id");
            sql.append(" left join ins_respuestas ir4 on ir4.codigo = dr4.cod_respuesta");
            sql.append(" where e1.cod_tipo = 'ELE_EST'");
            sql.append(" and dr1.cod_pregunta in ('PREG_035', 'PREG_201')");
            sql.append(" and dr2.cod_pregunta in ('PREG_038', 'PREG_204')");
            sql.append(" and dr3.cod_pregunta in ('PREG_039','PREG_205')");
            sql.append(" and dr4.cod_pregunta in ('PREG_037', 'PREG_203')");
            sql.append(" and ir1.fila = ir2.fila");
            sql.append(" and ir2.fila = ir3.fila");
            sql.append(" and ir3.fila = ir4.fila");
            sql.append(" group by i.id) personal on personal.id=di.id");
            sql.append(" inner join (select m.id_dig_instrumento,");
            sql.append(" char_to_double(d.valor) consumo");
            sql.append(" from dig_elementos m, dig_respuestas d");
            sql.append(" where d.id_dig_elemento=m.id and d.cod_respuesta='RESP_062_06') consumo_energia on consumo_energia.id_dig_instrumento=di.id");
            sql.append(" where di.id=?idDigInstrumento");

            Query query = tpm.getEm().createNativeQuery(sql.toString());
            query.setParameter("id_tnt", tpm.getCurrentTenant());
            query.setParameter("idDigInstrumento", idDigInstrumento);
            List<Object[]> resultado=query.getResultList();
            if(resultado!=null && !resultado.isEmpty()){
                consumoAguaEncimaPromedio=new Double(resultado.get(0)[0]+"")>new Double(resultado.get(0)[1]+"")?true:false;
            }
        }
        catch(Exception e){
            throw new Exception("Error en consumoAguaEncimaPromedio",e);
        }
        return consumoAguaEncimaPromedio;
     }
     
     
     @Override
     public String areaConstruccionAltura(Long idDigInstrumento) throws Exception{
         String areaConstruccionAltura="";
        try{            
            StringBuilder sql = new StringBuilder();
            sql.append("with similares as (");
            sql.append(" select i.id id_dig_instrumento,i.id_predio,");
            sql.append(" r1.valor id_espacio,r2.valor id_edificio,");
            sql.append(" count(r5.valor) total_ambientes_similares");
            sql.append(" from dig_instrumentos i");
            sql.append(" inner join dig_elementos e1 on e1.id_dig_instrumento = i.id");
            sql.append(" inner join dig_respuestas r1 on r1.id_dig_elemento = e1.id and r1.cod_respuesta = 'RESP_094'"); //id espacio
            sql.append(" inner join dig_respuestas r2 on r2.id_dig_elemento = e1.id and r2.cod_respuesta = 'RESP_095'"); //id edificio
            sql.append(" left join dig_elementos e3 on e3.id_dig_instrumento = i.id");
//espacios complementarios o similares
            sql.append(" inner join dig_respuestas r3 on r3.id_dig_elemento = e3.id");
            sql.append(" and r3.cod_respuesta = 'RESP_142' and r3.valor = r1.valor"); //id espacio
            sql.append(" inner join dig_respuestas r4 on r4.id_dig_elemento = e3.id");
            sql.append(" and r4.cod_respuesta = 'RESP_143' and r4.valor = r2.valor"); //id edificio
            sql.append(" left join dig_respuestas r5 on r5.id_dig_elemento = e3.id and r5.cod_pregunta = 'PREG_145'");//ambientes similares
            sql.append(" group by i.id,i.id_predio,r1.valor,r2.valor");
            sql.append(" order by i.id,i.id_predio,r1.valor,r2.valor");
            sql.append(" )");
            sql.append(" select ");
            sql.append(" sum(to_number(d.valor,'9999999.999')*(coalesce(sim.total_ambientes_similares,0)+1))");
            sql.append(" from");
            sql.append(" dig_respuestas d");

            sql.append(" inner join dig_elementos m on d.id_dig_elemento=m.id");
            sql.append(" inner join dig_respuestas r10 on r10.id_dig_elemento = d.id_dig_elemento and r10.cod_respuesta = 'RESP_094'");
            sql.append(" inner join dig_respuestas r20 on r20.id_dig_elemento = d.id_dig_elemento and r20.cod_respuesta = 'RESP_095'");

            sql.append(" left join similares sim on sim.id_dig_instrumento = m.id_dig_instrumento and sim.id_espacio = r10.valor and sim.id_edificio = r20.valor");
            sql.append(" where");
            sql.append(" d.cod_respuesta='RESP_104' and d.id_dig_elemento in");
            sql.append(" (");
            sql.append(" select dd.id_dig_elemento from dig_respuestas dd");
            sql.append(" where dd.cod_respuesta='RESP_096' and char_to_double(dd.valor)>1");
            sql.append(" )");
            sql.append(" and m.id_dig_instrumento=?idDigInstrumento");
            
            Query query = tpm.getEm().createNativeQuery(sql.toString());
            query.setParameter("idDigInstrumento", idDigInstrumento);

            areaConstruccionAltura=query.getSingleResult()!=null?query.getSingleResult().toString():"0";
        }
        catch(Exception e){
            throw new Exception("Error en areaConstruccionAltura",e);
        }
        return areaConstruccionAltura;
    }        
            
    @Override
    public List<String[]> getEquivalentes(String grupo) {
        List<String[]> resultados=new ArrayList<String[]>();
        try {
            String sql = "select distinct e.codigo, t.codigo from ind_equivalencia e" +
" inner join ins_tipologia_valores t on t.cod_tipologia='NE' and t.codigo=e.valor_tipologia" +
" where e.grupo=?grupo";
            Query query = tpm.getEm().createNativeQuery(sql);
            query.setParameter("grupo", grupo);
            resultados = query.getResultList();
           
        } catch (Exception e) {
            UtilOut.println(e);
        }
        return resultados;
    }
}
