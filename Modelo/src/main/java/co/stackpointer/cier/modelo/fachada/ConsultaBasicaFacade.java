/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.fachada;

import co.stackpointer.cier.modelo.entidad.digitado.Adjunto;
import co.stackpointer.cier.modelo.entidad.digitado.Elemento;
import co.stackpointer.cier.modelo.entidad.digitado.RespuestaDig;
import co.stackpointer.cier.modelo.entidad.instrumento.Documento;
import co.stackpointer.cier.modelo.entidad.instrumento.Observacion;
import co.stackpointer.cier.modelo.excepcion.ErrorGeneral;
import co.stackpointer.cier.modelo.filtro.basica.FiltroConsultaBasica;
import co.stackpointer.cier.modelo.tipo.SiNo;
import co.stackpointer.cier.modelo.utilidad.UtilCadena;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.eclipse.persistence.config.CacheUsage;
import org.eclipse.persistence.config.QueryHints;

/**
 *
 * @author user
 */
@Stateless
public class ConsultaBasicaFacade implements ConsultaBasicaFacadeLocal {

    @Inject
    private TenantPersistenceManager tpm;

    @Override
    public List<Integer> consultaPeriodosCreacion() {
        TypedQuery<Integer> query = tpm.getEm().createNamedQuery("InstrumentoDigitado.consultarPeriodosCreacion", Integer.class);
        return query.getResultList();
    }

    @Override
    public Integer consultaPeriodoHis(Integer periodoCreacion) {
        StringBuilder sql = new StringBuilder();
        sql.append("select max(c.periodo) from ind_dpa_configuracion_niveles c");
        sql.append(" where c.id_tnt=?id_tnt1 and c.periodo<=?pc1");
        sql.append(" and (select max(cc.periodo) from ind_dpa_configuracion_niveles cc");
        sql.append(" where cc.id_tnt=?id_tnt2)>=?pc2");

        Query query = tpm.getEm().createNativeQuery(sql.toString());
        query.setParameter("id_tnt1", tpm.getCurrentTenant());
        query.setParameter("pc1", periodoCreacion);
        query.setParameter("pc2", periodoCreacion);
        query.setParameter("id_tnt2", tpm.getCurrentTenant());

        return query.getSingleResult() != null ? new Integer(query.getSingleResult() + "") : null;
    }

    @Override
    public List<Object[]> consultarInstrumentos(FiltroConsultaBasica filtro, int nivel) {
        StringBuilder sql = new StringBuilder();

//SELECT

        sql.append("select");
        sql.append(" i.cod_instrumento ,i.id,");
        sql.append(" e.codigo codigoEstablecimiento, e.nombre nombreEstablecimiento,");
        sql.append(" s.codigo codigoSede, s.nombre nombreSede,");
        sql.append(" p.codigo codigoPredio, p.nombre nombrePredio,");
        sql.append(" i.estado estado,");
         sql.append("i.usuario_actualizacion , i.fecha_actualizacion ,");
        sql.append(" gradosLat.valor gradosLat,");
        sql.append(" minutosLat.valor minutosLat,");
        sql.append(" segundosLat.valor segundosLat,");
        sql.append(" yLat.valor yLat,");
        sql.append(" gradosLon.valor gradosLon,");
        sql.append(" minutosLon.valor minutosLon,");
        sql.append(" segundosLon.valor segundosLon,");
        sql.append(" xLat.valor xLat,");
        sql.append(" coorX.valor coorX,");
        sql.append(" coorY.valor coorY,");
        sql.append(" zona.valor zona,");
        sql.append(" zonaLat.valor zonaLat,");
        sql.append(" i.boleta_censal boleta, i.version, ");
        sql.append(" n.descripcion nivel, i.fuente");
   
        // FROM 
        sql.append(" from dig_instrumentos i");
        sql.append(" inner join est_establecimientos e on i.id_Establecimiento=e.id");
        sql.append(" inner join est_sedes s on i.id_Sede=s.id");
        sql.append(" inner join est_predios p on i.id_Predio=p.id");
        
        switch (nivel) {
            case 0:
                sql.append(" inner join dpa_niveles n on i.id_nivel0 = n.id ");
                break;
            case 1:
                sql.append(" inner join dpa_niveles n on i.id_nivel1 = n.id");
                break;
            case 2:
                sql.append(" inner join dpa_niveles n on i.id_nivel2 = n.id");
                break;
            case 3:
                sql.append(" inner join dpa_niveles n on i.id_nivel3 = n.id");
                break;
            case 4:
                sql.append(" inner join dpa_niveles n on i.id_nivel4 = n.id");
                break;
            default :
                sql.append(" inner join dpa_niveles n on i.id_nivel5 = n.id");
                break;
        }
        
        sql.append(" left join (select l.id_dig_instrumento,r.valor from dig_respuestas r, dig_elementos l where r.id_dig_elemento=l.id and r.cod_respuesta='RESP_025_01') gradosLat on i.id=gradosLat.id_dig_instrumento");
        sql.append(" left join (select l.id_dig_instrumento,r.valor from dig_respuestas r, dig_elementos l where r.id_dig_elemento=l.id and r.cod_respuesta='RESP_025_02') minutosLat on i.id=minutosLat.id_dig_instrumento");
        sql.append(" left join (select l.id_dig_instrumento,r.valor from dig_respuestas r, dig_elementos l where r.id_dig_elemento=l.id and r.cod_respuesta='RESP_025_03') segundosLat on i.id=segundosLat.id_dig_instrumento");
        sql.append(" left join (select l.id_dig_instrumento,r.valor from dig_respuestas r, dig_elementos l where r.id_dig_elemento=l.id and r.cod_respuesta='RESP_025_04') yLat on i.id=yLat.id_dig_instrumento");
        sql.append(" left join (select l.id_dig_instrumento,r.valor from dig_respuestas r, dig_elementos l where r.id_dig_elemento=l.id and r.cod_respuesta='RESP_026_01') gradosLon on i.id=gradosLon.id_dig_instrumento");
        sql.append(" left join (select l.id_dig_instrumento,r.valor from dig_respuestas r, dig_elementos l where r.id_dig_elemento=l.id and r.cod_respuesta='RESP_026_02') minutosLon on i.id=minutosLon.id_dig_instrumento");
        sql.append(" left join (select l.id_dig_instrumento,r.valor from dig_respuestas r, dig_elementos l where r.id_dig_elemento=l.id and r.cod_respuesta='RESP_026_03') segundosLon on i.id=segundosLon.id_dig_instrumento");
        sql.append(" left join (select l.id_dig_instrumento,r.valor from dig_respuestas r, dig_elementos l where r.id_dig_elemento=l.id and r.cod_respuesta='RESP_026_04') xLat on i.id=xLat.id_dig_instrumento");
        sql.append(" left join (select l.id_dig_instrumento,r.valor from dig_respuestas r, dig_elementos l where r.id_dig_elemento=l.id and r.cod_respuesta='RESP_028_01') coorX on i.id=coorX.id_dig_instrumento");
        sql.append(" left join (select l.id_dig_instrumento,r.valor from dig_respuestas r, dig_elementos l where r.id_dig_elemento=l.id and r.cod_respuesta='RESP_028_02') coorY on i.id=coorY.id_dig_instrumento");
        sql.append(" left join (select l.id_dig_instrumento,r.valor from dig_respuestas r, dig_elementos l where r.id_dig_elemento=l.id and r.cod_respuesta='RESP_028_03') zona on i.id=zona.id_dig_instrumento");
        sql.append(" left join (select l.id_dig_instrumento,r.valor from dig_respuestas r, dig_elementos l where r.id_dig_elemento=l.id and r.cod_respuesta='RESP_028_04') zonaLat on i.id=zonaLat.id_dig_instrumento");
        // WHERE
        sql.append(" where");
        sql.append(" i.id_tnt = ?id_tnt and i.periodo_creacion <= ?pc");
        sql.append(" and i.version = (select max(ii.version) from dig_instrumentos ii where ii.id_tnt = ?id_tnt and ii.periodo_creacion <= ?pc");
        sql.append(" and i.id_predio=ii.id_predio");
        sql.append(" group by ii.id_predio)");


        if (!UtilCadena.isNullOVacio(filtro.getCodEstablecimiento())) {
            sql.append(" and e.codigo = ?codEst");
        }
        if (!UtilCadena.isNullOVacio(filtro.getBoletaCensal())) {
            sql.append(" and i.boleta_censal = ?bc");
        }
        if (filtro.getEstado() != null && !"".equals(filtro.getEstado())) {
            sql.append(" and i.estado = ?estado");
        }
        if (filtro.getIdNivel0() != null) {
            sql.append(" and i.id_nivel0 = ?n0");
        }
        if (filtro.getIdNivel1() != null) {
            sql.append(" and i.id_nivel1 = ?n1");
        }
        if (filtro.getIdNivel2() != null) {
            sql.append(" and i.id_nivel2 = ?n2");
        }
        if (filtro.getIdNivel3() != null) {
            sql.append(" and i.id_nivel3 = ?n3");
        }
        if (filtro.getIdNivel4() != null) {
            sql.append(" and i.id_nivel4 = ?n4");
        }
        if (filtro.getIdNivel5() != null) {
            sql.append(" and i.id_nivel5 = ?n5");
        }
        if (filtro.getIdEstablecimiento() != null) {
            sql.append(" and i.id_establecimiento = ?est");
        }
        if (filtro.getIdSede() != null) {
            sql.append(" and i.id_sede = ?sede");
        }
        if (filtro.getIdPredio() != null) {
            sql.append(" and i.id_predio = ?pred");
        }
        if (filtro.getOrigen()!= null) {
            sql.append(" and i.fuente = ?fuente");
        }

        Query query = tpm.getEm().createNativeQuery(sql.toString());
        query.setParameter("id_tnt", tpm.getCurrentTenant());
        query.setParameter("pc", filtro.getPeriodoCreacion());
        if (filtro.getCodEstablecimiento() != null) {
            query.setParameter("codEst", filtro.getCodEstablecimiento());
        }
        if (filtro.getBoletaCensal() != null && !"".equals(filtro.getBoletaCensal())) {
            query.setParameter("bc", filtro.getBoletaCensal());
        }
        if (filtro.getEstado() != null && !"".equals(filtro.getEstado())) {
            query.setParameter("estado", filtro.getEstado());
        }
        if (filtro.getOrigen()!= null && !"".equals(filtro.getOrigen())) {
            query.setParameter("fuente", filtro.getOrigen());
        }
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
        if (filtro.getIdEstablecimiento() != null) {
            query.setParameter("est", filtro.getIdEstablecimiento());
        }
        if (filtro.getIdSede() != null) {
            query.setParameter("sede", filtro.getIdSede());
        }
        if (filtro.getIdPredio() != null) {
            query.setParameter("pred", filtro.getIdPredio());
        }


        return query.getResultList();
    }

    @Override
    public List<Object[]> obtenerRespuestasInstrumentoDig(Long idDigInstrumento, List<String> respuestas) {
        Map<String, String> respuestasMap = new HashMap<String, String>();
        StringBuilder sql = new StringBuilder();
        sql.append("select r.cod_respuesta, r.valor, r.id_dig_elemento");
        sql.append(" from dig_respuestas r, dig_elementos l where");
        sql.append(" l.id_dig_instrumento=?id and r.id_dig_elemento=l.id");
        boolean primer = true;
        for (String respuesta : respuestas) {
            if (primer) {
                sql.append(" and (");
                primer = false;
            } else {
                sql.append(" or");
            }
            sql.append(" r.cod_respuesta='");
            sql.append(respuesta);
            sql.append("'");
        }
        if (!primer) {
            sql.append(")");
        }
        Query query = tpm.getEm().createNativeQuery(sql.toString());
        query.setParameter("id", idDigInstrumento);


        return query.getResultList();
    }

    @Override
    public List<String> consultaRutaAdjunto(Long idDigInstrumento, String respAdj) {
        StringBuilder sql = new StringBuilder();
        sql.append("select j.ruta");
        sql.append(" from dig_respuestas r, dig_elementos l, dig_adjuntos j where");
        sql.append(" l.id_dig_instrumento=?id and r.id_dig_elemento=l.id");
        sql.append(" and r.cod_respuesta=?resp");
        sql.append(" and r.id=j.id_respuesta_dig");


        Query query = tpm.getEm().createNativeQuery(sql.toString());
        query.setParameter("id", idDigInstrumento);
        query.setParameter("resp", respAdj);

        return query.getResultList();
    }

    @Override
    public String consultaRutaAdjuntoCroquis(Long idDigInstrumento, String respAdj, Long idElem) {

        StringBuilder sql = new StringBuilder();
        sql.append("select a.ruta");
        sql.append(" from dig_respuestas r, dig_elementos l, dig_adjuntos a where");
        sql.append(" l.id_dig_instrumento=?id and r.cod_respuesta=?resp");
        sql.append(" and l.id=?idElem and r.id_dig_elemento=l.id");
        sql.append(" and r.id=a.id_respuesta_dig ");

        Query query = tpm.getEm().createNativeQuery(sql.toString());
        query.setParameter("id", idDigInstrumento);
        query.setParameter("resp", respAdj);
        query.setParameter("idElem", idElem);

        return (String) query.getSingleResult();
    }

    private List<RespuestaDig> obtenerRespuestasDigitadasPorElemento(Elemento elemento) {
        List<RespuestaDig> respuestasDigitadas = new ArrayList<RespuestaDig>();
        try {
            //EntityManager em = tpm.getEm();
            respuestasDigitadas = tpm.getEm().createNamedQuery("RespuestasDigitadas.findByElementoId")
                    .setParameter("idElemento", elemento.getId())
                    .setHint(QueryHints.CACHE_USAGE, CacheUsage.NoCache)
                    .getResultList();
        } catch (Exception ex) {
            throw new ErrorGeneral("Error", ex.getMessage());
        }
        return respuestasDigitadas;
    }

    @Override
    public List<Observacion> consultarObservacionesPorInstrumento(Long idInstrumento) {
        List<Elemento> elementos = new ArrayList<Elemento>();
        List<Observacion> observaciones = new ArrayList<Observacion>();
        try {
            //EntityManager em = tpm.getEm();
            elementos = tpm.getEm().createNamedQuery("Elemento.findByIdInstrumentoTipoElemento")
                    .setParameter("idInstrumento", idInstrumento)
                    .setParameter("codigoTipoElemento", "ELE_OBS_COMP")
                    .setHint(QueryHints.CACHE_USAGE, CacheUsage.NoCache)
                    .getResultList();
        } catch (NoResultException ex) {
            return null;
        } catch (Exception exp) {
            throw new ErrorGeneral("Error", exp.getMessage());
        }
        if (!elementos.isEmpty()) {
            Observacion obs;
            for (Elemento elemento : elementos) {
                obs = new Observacion();
                obs.setElemento(elemento);
                int control = 0;
                for (RespuestaDig resp : this.obtenerRespuestasDigitadasPorElemento(elemento)) {
                    if (resp.getRespuesta().getCodigo().equals("RESP_187")) {
                        control++;
                        obs.setModulo(resp.getValor());
                        obs.setIdModulo(resp.getId());
                    }
                    if (resp.getRespuesta().getCodigo().equals("RESP_190")) {
                        control++;
                        obs.setObservacion(resp.getValor());
                        obs.setIdObservacion(resp.getId());
                    }

                }
                if (control > 0) {
                    observaciones.add(obs);
                }
            }

        }
        return observaciones;
    }

    @Override
    public List<Observacion> consultarObservacionesPorInstrumentos(String idsInstrumento) {
        List<Elemento> elementos = new ArrayList<Elemento>();
        List<Observacion> observaciones = new ArrayList<Observacion>();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT e FROM Elemento e ");
            sql.append("where e.instrumentoDigitado.id in (");
            sql.append(idsInstrumento);
            sql.append(") and ");
            sql.append("e.tipoElemento.codigo = :codigoTipoElemento ");
            sql.append("order by e.id");

            //EntityManager em = tpm.getEm();
            elementos = tpm.getEm().createQuery(sql.toString())
                    .setParameter("codigoTipoElemento", "ELE_OBS_COMP")
                    .setHint(QueryHints.CACHE_USAGE, CacheUsage.NoCache)
                    .getResultList();
        } catch (NoResultException ex) {
            return null;
        } catch (Exception exp) {
            throw new ErrorGeneral("Error", exp.getMessage());
        }
        if (!elementos.isEmpty()) {
            Observacion obs;
            for (Elemento elemento : elementos) {
                obs = new Observacion();
                obs.setElemento(elemento);
                int control = 0;
                for (RespuestaDig resp : this.obtenerRespuestasDigitadasPorElemento(elemento)) {
                    if (resp.getRespuesta().getCodigo().equals("RESP_187")) {
                        control++;
                        obs.setModulo(resp.getValor());
                        obs.setIdModulo(resp.getId());
                    }
                    if (resp.getRespuesta().getCodigo().equals("RESP_190")) {
                        control++;
                        obs.setObservacion(resp.getValor());
                        obs.setIdObservacion(resp.getId());
                    }

                }
                if (control > 0) {
                    observaciones.add(obs);
                }
            }

        }
        return observaciones;
    }

    @Override
    public List<Adjunto> consultarAdjuntosPorInstrumentos(String idsInstrumento) {
        List<Adjunto> adjuntos = new ArrayList<Adjunto>();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT a FROM Adjunto a ");
            sql.append("where a.respuestaDigitada.elemento.instrumentoDigitado.id ");
            sql.append(" in (");
            sql.append(idsInstrumento);
            sql.append(")");

            //EntityManager em = tpm.getEm();
            adjuntos = tpm.getEm().createQuery(sql.toString())
                    .setHint(QueryHints.CACHE_USAGE, CacheUsage.NoCache)
                    .getResultList();

        } catch (NoResultException ex) {
            return null;
        } catch (Exception exp) {
            throw new ErrorGeneral("Error", exp.getMessage());
        }

        return adjuntos;
    }

    @Override
    public List<Documento> consultarDocumentosPorInstrumento(Long idInstrumento) {
        List<Documento> documentos = new ArrayList<Documento>();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("select a.etiqueta_1,a.cod_respuesta,b.descripcion,b.valor,a.descripcion,b.id"
                    + " from "
                    + " (select p.etiqueta_1, r.codigo cod_respuesta, te.descripcion"
                    + " from ins_preguntas p"
                    + " join ins_respuestas r on p.codigo=r.cod_pregunta and r.cod_tipo_respuesta='TIP_RESP_013'"
                    + " join ins_tipo_elementos te on te.codigo = p.cod_tipo_elemento) a"
                    + " left join "
                    + " (select el.descripcion,r.codigo,dig.valor,dig.id"
                    + " from dig_respuestas dig"
                    + " join dig_elementos el on el.id = dig.id_dig_elemento"
                    + " join ins_respuestas r on r.codigo=dig.cod_respuesta and r.cod_tipo_respuesta='TIP_RESP_013'"
                    + " join ins_preguntas p on p.codigo = r.cod_pregunta"
                    + " where "
                    + " el.id_dig_instrumento = ?idInstrumento)b on a.cod_respuesta = b.codigo "
                    + " where a.descripcion = 'PREDIO' or (a.descripcion != 'PREDIO' and valor = '1')"
                    + " order by 4 ,3 desc,2");
            //Query query = tpm.getEm().createNativeQuery(sql.toString());
            Query query = tpm.getEm().createNativeQuery(sql.toString());
            query.setParameter("idInstrumento", idInstrumento);

            List<Object[]> listDatos = query.getResultList();
            for (Object[] datos : listDatos) {
                Documento doc = new Documento();
                doc.setEnunciado(validarObjeto(datos[0]));
                doc.setCodRespuesta(validarObjeto(datos[1]));
                doc.setElemento(validarObjeto(datos[2]));
                doc.setTieneAdjunto(tieneAdjunto(datos[3]));
                doc.setTipoElemento(validarObjeto(datos[4]));
                doc.setIdRespuestaDigitada(datos[5] != null ? Long.valueOf(datos[5].toString()) : null);
                documentos.add(doc);
            }

        } catch (NoResultException ex) {
            return null;
        } catch (Exception exp) {
            throw new ErrorGeneral("Error", exp.getMessage());
        }

        return documentos;
    }

    private SiNo tieneAdjunto(Object valor) {
        SiNo adjunto = SiNo.No;
        if (valor != null) {
            if (valor.toString().equalsIgnoreCase("1")) {
                adjunto = SiNo.Si;
            }
        }
        return adjunto;
    }

    private String validarObjeto(Object objeto) {
        String valor = "";
        if (objeto != null) {
            valor = objeto.toString();
        }
        return valor;
    }
}
