/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.fachada;

import co.stackpointer.cier.modelo.auditoria.InterceptorAdministracion;
import co.stackpointer.cier.modelo.entidad.calificacion.AmbientesFuncionales;
import co.stackpointer.cier.modelo.entidad.calificacion.CalServicios;
import co.stackpointer.cier.modelo.entidad.calificacion.VariableAmbito;
import co.stackpointer.cier.modelo.entidad.indicador.ControlGeneracionIndicador;
import co.stackpointer.cier.modelo.entidad.indicador.CostoConstruccion;
import co.stackpointer.cier.modelo.entidad.indicador.EstandarHis;
import co.stackpointer.cier.modelo.entidad.indicador.ParametroConstruccion;
import co.stackpointer.cier.modelo.entidad.indicador.PeriodosCostosConstruccion;
import co.stackpointer.cier.modelo.entidad.indicador.UnidadFuncional;
import co.stackpointer.cier.modelo.entidad.instrumento.Ambito;
import co.stackpointer.cier.modelo.entidad.instrumento.TipologiaValor;
import co.stackpointer.cier.modelo.entidad.instrumento.TipologiaValorNombre;
import co.stackpointer.cier.modelo.entidad.sistema.Parametro;
import co.stackpointer.cier.modelo.excepcion.ErrorGeneral;
import co.stackpointer.cier.modelo.excepcion.ErrorIntegridad;
import co.stackpointer.cier.modelo.excepcion.ErrorValidacion;
import co.stackpointer.cier.modelo.tipo.ControlGeneracionInd;
import co.stackpointer.cier.modelo.tipo.ParamBaseDatos;
import co.stackpointer.cier.modelo.tipo.ParamEstandar;
import co.stackpointer.cier.modelo.tipo.ParamNivelConsul;
import co.stackpointer.cier.modelo.tipo.ParamSistema;
import co.stackpointer.cier.modelo.tipo.TipologiaCodigo;
import co.stackpointer.cier.modelo.utilidad.UtilCadena;
import co.stackpointer.cier.modelo.utilidad.UtilOut;
import co.stackpointer.cier.modelo.utilidad.UtilProperties;
import java.math.BigDecimal;
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
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.sql.DataSource;

/**
 *
 * @author StackPointer
 */
@Stateless
public class ParametrosFacade implements ParametrosFacadeLocal {

    @Inject
    private TenantPersistenceManager tpm;

    @Override
    public void crearParametro(Parametro parametro) {
        tpm.getEm().persist(parametro);
        tpm.getEm().flush();
    }

    @Override
    public List<TipologiaValorNombre> consultarValoresTipologia(TipologiaCodigo codTipologia, Long idIdioma) {
        return consultarValoresTipologia(codTipologia.getValor(), idIdioma);
    }

    @Override
    public List<TipologiaValorNombre> consultarValoresTipologia(String codTipologia, Long idIdioma) {
        tpm.getEm().clear();
        TypedQuery<TipologiaValorNombre> query = tpm.getEm().createNamedQuery("TipologiaValorNombre.consultarPorCodTipologia", TipologiaValorNombre.class);
        query.setParameter("estado", "A");
        query.setParameter("codTipologia", codTipologia);
        query.setParameter("idIdioma", idIdioma);
        List<TipologiaValorNombre> lista = query.getResultList();
        return lista;
    }

    @Override
    public TipologiaValorNombre consultarValorTipologia(String codTipologia, String codigo, Long idIdioma) {
        try {
            tpm.getEm().clear();
            TypedQuery<TipologiaValorNombre> query = tpm.getEm().createNamedQuery("TipologiaValorNombre.consultarValorPorCod", TipologiaValorNombre.class);
            query.setParameter("codTipologia", codTipologia);
            query.setParameter("codigo", codigo);
            query.setParameter("idIdioma", idIdioma);
            TipologiaValorNombre tipValor = query.getSingleResult();
            return tipValor;
        } catch (NoResultException e) {
            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public TipologiaValorNombre consultarValorTipologiaEstado(String codTipologia, String codigo, Long idIdioma) {
        try {
            tpm.getEm().clear();
            TypedQuery<TipologiaValorNombre> query = tpm.getEm().createNamedQuery("TipologiaValorNombre.consultarValorPorCodEstado", TipologiaValorNombre.class);
            query.setParameter("codTipologia", codTipologia);
            query.setParameter("codigo", codigo);
            query.setParameter("idIdioma", idIdioma);
            TipologiaValorNombre tipValor = query.getSingleResult();
            return tipValor;
        } catch (NoResultException e) {
            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public String consultarNombreTipologia(Long idTipologia, Long idIdioma) {
        try {
            tpm.getEm().clear();
            TypedQuery<TipologiaValorNombre> query = tpm.getEm().createNamedQuery("TipologiaValorNombre.consultarValorPorId", TipologiaValorNombre.class);
            query.setParameter("idTipologia", idTipologia);
            query.setParameter("idIdioma", idIdioma);
            TipologiaValorNombre tipValor = query.getSingleResult();
            return tipValor.getNombre();
        } catch (NoResultException e) {
            return null;
        }
    }

   @Override
    public TipologiaValorNombre modificarTipologiaValor(TipologiaValorNombre tipologiaValorNombre) throws ErrorIntegridad {
        try {
            tpm.getEm().createNativeQuery("UPDATE ins_tipologia_valores_nombres SET nombre = ? WHERE id_tip_valor=(select id from ins_tipologia_valores where codigo = ? and cod_tipologia = ?)")
                    .setParameter(1, tipologiaValorNombre.getNombre())
                    .setParameter(2, tipologiaValorNombre.getTipValor().getCodigo())
                    .setParameter(3, tipologiaValorNombre.getTipValor().getTipologia().getCodigo())
                    .executeUpdate();
            return tipologiaValorNombre;
        } catch (Exception ex) {
            throw new ErrorIntegridad("Error", ex.getMessage());
        }
    }

    @Override
    public ControlGeneracionIndicador consultarParametroIndicador(ControlGeneracionInd codigo) {
        try {
            tpm.getEm().clear();
            TypedQuery<ControlGeneracionIndicador> query = tpm.getEm().createNamedQuery("ControlGeneracionIndicador.consultarPorCodigo", ControlGeneracionIndicador.class);
            query.setParameter("codigo", codigo.name());
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Integer ultimoPeriodoCalculadoIndicadores() {
        ControlGeneracionIndicador parametro = consultarParametroIndicador(ControlGeneracionInd.ULTIMO_PERIODO_CALCULADO);
        return !UtilCadena.isNullOVacio(parametro.getValor()) ? Integer.valueOf(parametro.getValor()) : null;
    }

    @Override
    public List<Integer> consultarPeriodosCalculadosIndicadores() {
        tpm.getEm().clear();
        TypedQuery<Integer> query = tpm.getEm().createNamedQuery("AuxiliarIndicador.consultarPeriodos", Integer.class);
        return query.getResultList();
    }

    @Override
    public List<Integer> consultarPeriodosCalculadosIndicadores(Integer periodo) {
        tpm.getEm().clear();
        TypedQuery<Integer> query = tpm.getEm().createNamedQuery("AuxiliarIndicador.consultarPeriodosDesdeCiertoPeriodo", Integer.class)
                .setParameter("periodo", periodo);
        return query.getResultList();
    }

    @Override
    public Parametro consultarParametro(String codigo) {
        try {
            tpm.getEm().clear();
            TypedQuery<Parametro> query = tpm.getEm().createNamedQuery("Parametro.consultarPorCodigo", Parametro.class);
            query.setParameter("codigo", codigo);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public EstandarHis consultarEstandar(ParamEstandar paramEstandar) {
        try {
            tpm.getEm().clear();
            TypedQuery<EstandarHis> query = tpm.getEm().createNamedQuery("EstandarHis.consultarPorCodigo", EstandarHis.class);
            query.setParameter("codigo", paramEstandar.name());
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public EstandarHis consultarEstandarPorCodigo(String paramEstandar) {
        try {
            tpm.getEm().clear();
            TypedQuery<EstandarHis> query = tpm.getEm().createNamedQuery("EstandarHis.consultarPorCodigo", EstandarHis.class);
            query.setParameter("codigo", paramEstandar);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Ambito> consultarAmbitos() {
        tpm.getEm().clear();
        TypedQuery<Ambito> query = tpm.getEm().createNamedQuery("Ambito.findAmbitosCalificacion", Ambito.class);
        return query.getResultList();
    }

    @Override
    public Ambito modificarAmbito(Ambito ambito) {
        tpm.getEm().clear();
        ambito = tpm.getEm().merge(ambito);
        tpm.getEm().flush();
        return ambito;
    }

    @Override
    public List<VariableAmbito> consultarVariablesAmbitos() {
        tpm.getEm().clear();
        TypedQuery<VariableAmbito> query = tpm.getEm().createNamedQuery("VariableAmbito.findVariablesCalificacion", VariableAmbito.class);
        return query.getResultList();
    }

    @Override
    public VariableAmbito modificarVariableAmbito(VariableAmbito variableAmbito) {
        tpm.getEm().clear();
        variableAmbito = tpm.getEm().merge(variableAmbito);
        tpm.getEm().flush();
        return variableAmbito;
    }

    @Override
    public List<CostoConstruccion> consultarCostoConstrucciones() {
        tpm.getEm().clear();
        TypedQuery<CostoConstruccion> query = tpm.getEm().createNamedQuery("CostoConstruccion.findAll", CostoConstruccion.class);
        return query.getResultList();
    }

    @Override
    public CostoConstruccion modificarCostoConstruccion(CostoConstruccion costoConstruccion) {
        tpm.getEm().clear();
        costoConstruccion = tpm.getEm().merge(costoConstruccion);
        tpm.getEm().flush();
        return costoConstruccion;
    }

    @Override
    public List<Object[]> consultarIndicadores(List<String> indicadores, Long nivelAgrupamiento, Long idEntidad) throws ErrorIntegridad {
        List<Object[]> respuesta = new ArrayList<Object[]>();
        List<Object> aux = new ArrayList<Object>();
        try {
            tpm.getEm().clear();
            String campo = "";
            if (nivelAgrupamiento <= ParamNivelConsul.CINCO.getNivel()) {
                campo = "id_nivel" + nivelAgrupamiento;
            } else if (ParamNivelConsul.ESTABLECIMIENTO.getNivel().equals(nivelAgrupamiento)) {
                campo = "id_establecimiento";
            } else if (ParamNivelConsul.SEDE.getNivel().equals(nivelAgrupamiento)) {
                campo = "id_sede";
            } else if (ParamNivelConsul.PREDIO.getNivel().equals(nivelAgrupamiento)) {
                campo = "id_predio";
            }

            Parametro parametro = this.consultarParametro(ParamSistema.SQL_CONSULTAR_INDICADOR_ALMACENADO.name());
            boolean hay = false;
            for (String indicador : indicadores) {


                StringBuilder sql = new StringBuilder();
                sql.append("select distinct");

                sql.append(" tbl_tipo.valor");
                sql.append(" from dig_instrumentos x");
                sql.append(" inner join ind_auxiliar_indicador aux on aux.id_predio=x.id_Predio");
                sql.append(" inner join ind_auxiliar_consultas n on n.id_predio = aux.id_predio and n.periodo = aux.periodo and n.id_tnt = aux.id_tnt");

                sql.append(" left join (");
                sql.append(parametro.getValor());
                sql.append(" ) tbl_tipo on tbl_tipo.id_tnt = aux.id_tnt and tbl_tipo.periodo = aux.periodo");
                sql.append(" and tbl_tipo");
                sql.append(String.format(".cod_indicador = '%s'", indicador));
                sql.append(" and tbl_tipo.nivel_agrupamiento = ?nivel_agrupamiento");
                sql.append(" and tbl_tipo.id_entidad_nivel = n.").append(campo);

                if (idEntidad != null) {
                    if (ParamNivelConsul.PREDIO.getNivel().equals(nivelAgrupamiento)) {
                        sql.append(" where x.id=").append(idEntidad);//En el nivel de predio se consulta por id de instrumento
                    } else {
                        sql.append(" where n.").append(campo).append("=").append(idEntidad);
                    }
                }
                sql.append(" and x.id_tnt=?id_tnt");

                Query query = tpm.getEm().createNativeQuery(sql.toString());
                query.setParameter("id_tnt", tpm.getCurrentTenant());
                query.setParameter("nivel_agrupamiento", nivelAgrupamiento);

                if (!query.getResultList().isEmpty()) {
                    hay = true;
                }
                aux.add(query.getResultList().isEmpty() ? null : query.getResultList().get(0));

            }
            if (hay) {
                Object[] obj = new Object[aux.size()];
                aux.toArray(obj);
                respuesta.add(obj);
            }
            return respuesta;
        } catch (ErrorIntegridad e) {
            UtilOut.println(e);
            throw new ErrorIntegridad("Error en consultarIndicador");
        }
    }

    @Override
    public List<Object[]> consultarIndicadoresUnidadesFuncionales(List<String[]> indBusqUnidadesFun, Long nivelAgrupamiento, Long idEntidad) throws ErrorIntegridad {
        List<Object[]> respuesta = new ArrayList<Object[]>();
        List<Object> listAux = new ArrayList<Object>();
        try {
            tpm.getEm().clear();
            String campo = "";
            if (nivelAgrupamiento <= ParamNivelConsul.CINCO.getNivel()) {
                campo = "id_nivel" + nivelAgrupamiento;
            } else if (ParamNivelConsul.ESTABLECIMIENTO.getNivel().equals(nivelAgrupamiento)) {
                campo = "id_establecimiento";
            } else if (ParamNivelConsul.SEDE.getNivel().equals(nivelAgrupamiento)) {
                campo = "id_sede";
            } else if (ParamNivelConsul.PREDIO.getNivel().equals(nivelAgrupamiento)) {
                campo = "id_predio";
            }

            Parametro parametro = this.consultarParametro(ParamSistema.SQL_CONSULTAR_INDICADOR_ALMACENADO.name());
            boolean hay = false;
            for (String[] aux : indBusqUnidadesFun) {

                StringBuilder sql = new StringBuilder();
                sql.append("select distinct");

                sql.append(" tbl_tipo.valor");
                sql.append(" from dig_instrumentos x");
                sql.append(" inner join ind_auxiliar_indicador aux on aux.id_predio=x.id_Predio");
                sql.append(" inner join ind_auxiliar_consultas n on n.id_predio = aux.id_predio and n.periodo = aux.periodo and n.id_tnt = aux.id_tnt");

                sql.append(" left join (");
                sql.append(parametro.getValor());
                sql.append(" ) tbl_tipo on tbl_tipo.id_tnt = aux.id_tnt and tbl_tipo.periodo = aux.periodo");
                sql.append(" and tbl_tipo");
                sql.append(String.format(".cod_indicador = '%s'", aux[0]));
                sql.append(" and tbl_tipo.nivel_agrupamiento = ?nivel_agrupamiento");
                sql.append(" and tbl_tipo.id_entidad_nivel = n.").append(campo);
                sql.append(" and tbl_tipo.cod_valor = '").append(aux[1]).append("'");
                if (idEntidad != null) {
                    if (ParamNivelConsul.PREDIO.getNivel().equals(nivelAgrupamiento)) {
                        sql.append(" where x.id=").append(idEntidad);//En el nivel de predio se consulta por id de instrumento
                    } else {
                        sql.append(" where n.").append(campo).append("=").append(idEntidad);
                    }
                    sql.append(" and x.id_tnt=?id_tnt");
                } else {
                    sql.append(" where x.id_tnt=?id_tnt");
                }

                Query query = tpm.getEm().createNativeQuery(sql.toString());
                query.setParameter("id_tnt", tpm.getCurrentTenant());
                query.setParameter("nivel_agrupamiento", nivelAgrupamiento);

                if (!query.getResultList().isEmpty()) {
                    hay = true;
                }
                listAux.add(query.getResultList().isEmpty() ? null : query.getResultList().get(0));
            }
            if (hay) {
                Object[] obj = new Object[listAux.size()];
                listAux.toArray(obj);
                respuesta.add(obj);
            }
            return respuesta;
        } catch (ErrorIntegridad e) {
            UtilOut.println(e);
            throw new ErrorIntegridad("Error en consultarIndicador");
        }
    }

    @Override
    public List<CalServicios> consultarCalServicios() {
        tpm.getEm().clear();
        TypedQuery<CalServicios> query = tpm.getEm().createNamedQuery("CalServicios.findAll", CalServicios.class);
        return query.getResultList();
    }

    @Override
    public CalServicios modificarCalServicios(CalServicios servicio) {
        tpm.getEm().clear();
        servicio = tpm.getEm().merge(servicio);
        tpm.getEm().flush();
        return servicio;
    }

    @Override
    public List<AmbientesFuncionales> consultarAmbientesFuncionales() {
        tpm.getEm().clear();
        TypedQuery<AmbientesFuncionales> query = tpm.getEm().createNamedQuery("AmbientesFuncionales.findAll", AmbientesFuncionales.class);
        return query.getResultList();
    }

    @Override
    public AmbientesFuncionales modificarAmbientesFuncinoales(AmbientesFuncionales ambiente) {
        tpm.getEm().clear();
        ambiente = tpm.getEm().merge(ambiente);
        tpm.getEm().flush();
        return ambiente;
    }

    @Override
    public List<EstandarHis> consultarEstandares() {
        tpm.getEm().clear();
        TypedQuery<EstandarHis> query = tpm.getEm().createNamedQuery("EstandarHis.findAll", EstandarHis.class);
        return query.getResultList();
    }

    @Override
    public EstandarHis modificarEstandar(EstandarHis estandar) {
        tpm.getEm().clear();
        estandar = tpm.getEm().merge(estandar);
        tpm.getEm().flush();
        return estandar;
    }

    @Override
    public List<UnidadFuncional> consultarUnidadesFuncionales(Integer periodo) {
        tpm.getEm().clear();
        TypedQuery<UnidadFuncional> query = tpm.getEm().createNamedQuery("UnidadFuncional.porPeriodo", UnidadFuncional.class);
        query.setParameter("periodo", periodo);
        return query.getResultList();
    }

    @Override
    public String consultarPeriodoIndicadores() {
        try {
            String sql = "select max(periodo) from ind_valor";
            Query query = tpm.getEm().createNativeQuery(sql);
            query.setParameter("id_tnt", tpm.getCurrentTenant());
            return query.getSingleResult() != null ? query.getSingleResult() + "" : null;
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<ParametroConstruccion> consultaParametrosConstruccion() {
        tpm.getEm().clear();
        TypedQuery<ParametroConstruccion> query = tpm.getEm().createNamedQuery("ParametrosConstruccion.findAll", ParametroConstruccion.class);
        return query.getResultList();
    }

    @Override
    public List<ParametroConstruccion> consultaParametrosConstruccionPeriodo(Long periodo) {
        tpm.getEm().clear();
        TypedQuery<ParametroConstruccion> query = tpm.getEm().createNamedQuery("ParametrosConstruccion.findAllbyPeriodo", ParametroConstruccion.class).setParameter("periodo", periodo);
        return query.getResultList();
    }

    @Override
    public void eliminarParametroPC(ParametroConstruccion parametroConstruccion) {
        try {
            ParametroConstruccion paramEliminado = tpm.getEm().find(ParametroConstruccion.class, parametroConstruccion.getId());
            tpm.getEm().remove(paramEliminado);
            tpm.getEm().flush();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorIntegridad("adm.parametros.estandar.pc.error.eliminar");
        }
    }

    @Override
    public List<PeriodosCostosConstruccion> obtenerPeriodosConstruccion() {
        tpm.getEm().clear();
        TypedQuery<PeriodosCostosConstruccion> query = tpm.getEm().createNamedQuery("PeriodosCostosConstruccion.findAll", PeriodosCostosConstruccion.class);
        return query.getResultList();
    }

    @Override
    public PeriodosCostosConstruccion crearPeriodoCC(PeriodosCostosConstruccion nuevoPeriodo) {
        boolean sw = true;
        try {
            tpm.getEm().persist(nuevoPeriodo);
            tpm.getEm().flush();

        } catch (Exception e) {
            if (e instanceof ErrorValidacion) {
                throw new ErrorValidacion(e.getMessage());
            } else {
                throw new ErrorIntegridad("adm.parametros.estandar.pc.error.repetido");
            }
        }
        return nuevoPeriodo;
    }

    @Override
    public Long crearParametrosCostos(int idTnt, Long periodo) {
        Connection conn = null;
        CallableStatement cs = null;
        Long result = -1L;
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("jdbc/DBCierDS");
            conn = ds.getConnection();
            cs = conn.prepareCall(UtilProperties.getProperties().getProperty("insertarParametrosCostos").trim());
            cs.registerOutParameter(1, Types.INTEGER);
            cs.setLong(2, idTnt);
            cs.setLong(3, periodo);
            cs.execute();
            int valor = cs.getInt(1);
            result = new Long(valor);
            cs.close();
        } catch (Exception exp) {
            throw new ErrorGeneral("Error Insertando Parametros", exp.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (cs != null) {
                    cs.close();
                }
            } catch (SQLException ex) {
                throw new ErrorGeneral("Error Cerrando conn ", ex.getMessage());
            }
        }
        return result;
    }

    @Override
    public List<UnidadFuncional> obtenerUnidadesFuncionales() {
        tpm.getEm().clear();
        TypedQuery<UnidadFuncional> query = tpm.getEm().createNamedQuery("UnidadFuncional.getAll", UnidadFuncional.class);
        return query.getResultList();
    }

    @Override
    public ParametroConstruccion crearParametroPC(ParametroConstruccion nuevoParametro) {
        boolean sw = true;
        try {
            tpm.getEm().persist(nuevoParametro);
            tpm.getEm().flush();

        } catch (Exception e) {
            if (e instanceof ErrorValidacion) {
                throw new ErrorValidacion(e.getMessage());
            } else {
                throw new ErrorIntegridad("adm.parametros.estandar.pc.error.editar");
            }
        }
        return nuevoParametro;
    }

    @Override
    public void actualizarParametro(ParametroConstruccion nuevoParametro) {
        boolean sw = true;
        try {
            tpm.getEm().merge(nuevoParametro);
            tpm.getEm().flush();
        } catch (Exception e) {
            if (e instanceof ErrorValidacion) {
                throw new ErrorValidacion(e.getMessage());
            } else {
                throw new ErrorIntegridad("adm.parametros.estandar.pc.error.editar");

            }
        }
    }

    @Override
    public UnidadFuncional obtenerUnidadFuncionalById(Long id) {
        try {
            tpm.getEm().clear();
            TypedQuery<UnidadFuncional> query = tpm.getEm().createNamedQuery("UnidadFuncional.getById", UnidadFuncional.class);
            query.setParameter("id", id);
            UnidadFuncional unidad = query.getSingleResult();
            return unidad;
        } catch (NoResultException e) {
            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public boolean validarParametroCostos(ParametroConstruccion param) {
        boolean sw = true;
        try {
            tpm.getEm().createNamedQuery("ParametrosConstruccion.getByMunPerUnfun")
                    .setParameter("unidadFuncional", param.getUnidadFuncional())
                    .setParameter("nivel", param.getNivel())
                    .setParameter("periodo", param.getPeriodo())
                    .getSingleResult();
        } catch (NoResultException e) {
            sw = false;
        } catch (NonUniqueResultException e) {
            sw = true;
        }
        return sw;
    }

    @Override
    @Interceptors({InterceptorAdministracion.class})
    public TipologiaValor modificarTipologiaValor(String usernameSesion, TipologiaValor tipologiaValor) throws ErrorIntegridad {
        try {
            tpm.getEm().merge(tipologiaValor);
            tpm.getEm().flush();
            return tipologiaValor;
        } catch (Exception e) {
            if (e instanceof ErrorValidacion) {
                throw new ErrorValidacion(e.getMessage());
            } else {
                throw new ErrorIntegridad("adm.parametros.estandar.pc.error.editar");
            }
        }
    }

    private boolean existeTipologiaValor(TipologiaValorNombre tipologiaValorNombre, boolean inactivo) {
        boolean result = false;
        try {
            StringBuilder query = new StringBuilder();
            query.append("select * ");
            query.append("from ins_tipologia_valores ");
            query.append("where cod_tipologia = ?tipologia ");
            query.append("and codigo = ?codigo ");
            if (inactivo) {
                query.append("and estado <> 'A' ");
            } else {
                query.append("and estado = 'A' ");
            }
            List<TipologiaValor> tipologias = tpm.getEm().createNativeQuery(query.toString(), TipologiaValor.class)
                    .setParameter("tipologia", tipologiaValorNombre.getTipValor().getTipologia().getCodigo())
                    .setParameter("codigo", tipologiaValorNombre.getTipValor().getCodigo())
                    .getResultList();
            if (tipologias != null && tipologias.size() > 0) {
                result = true;
            }
        } catch (Exception ex) {
            UtilOut.println(ex);
        }
        return result;
    }

    private Long getIdByKey(TipologiaValorNombre tipologiaValorNombre) {
        Object result = null;
        try {
            StringBuilder query = new StringBuilder();
            query.append("select id ");
            query.append("from ins_tipologia_valores ");
            query.append("where cod_tipologia = ?tipologia ");
            query.append("and codigo = ?codigo ");
            result = tpm.getEm().createNativeQuery(query.toString())
                    .setParameter("tipologia", tipologiaValorNombre.getTipValor().getTipologia().getCodigo())
                    .setParameter("codigo", tipologiaValorNombre.getTipValor().getCodigo())
                    .getSingleResult();
        } catch (Exception ex) {
            UtilOut.println(ex);
        }
        return ((BigDecimal) result).longValue();
    }

    @Override
    @Interceptors({InterceptorAdministracion.class}) 
    public void crearTipologiaValor(String usernameSesion, TipologiaValorNombre tipologiaValorNombre) throws ErrorIntegridad {
        try {
            StringBuilder query = new StringBuilder();
            if (existeTipologiaValor(tipologiaValorNombre, false)) {
                throw new ErrorIntegridad("administracion.tipologias.dlg.agregar.duplicate.message");
            } else {
                if (existeTipologiaValor(tipologiaValorNombre, true)) {
                    query.append("UPDATE ins_tipologia_valores ");
                    query.append("SET estado = 'A' ");
                    query.append("WHERE cod_tipologia = ?tipologia ");
                    query.append("AND codigo = ?codigo ");
                    tpm.getEm().createNativeQuery(query.toString())
                            .setParameter("tipologia", tipologiaValorNombre.getTipValor().getTipologia().getCodigo())
                            .setParameter("codigo", tipologiaValorNombre.getTipValor().getCodigo())
                            .executeUpdate();

                    query = new StringBuilder();
                    query.append("UPDATE ins_tipologia_valores_nombres ");
                    query.append("SET nombre = ?nombre ");
                    query.append("WHERE id_tip_valor IN ");
                    query.append("(SELECT id ");
                    query.append("FROM ins_tipologia_valores ");
                    query.append("WHERE cod_tipologia = ?tipologia ");
                    query.append("AND codigo = ?codigo) ");
                    query.append("AND id_idioma = ?idioma ");
                    tpm.getEm().createNativeQuery(query.toString())
                            .setParameter("tipologia", tipologiaValorNombre.getTipValor().getTipologia().getCodigo())
                            .setParameter("codigo", tipologiaValorNombre.getTipValor().getCodigo())
                            .setParameter("nombre", tipologiaValorNombre.getNombre())
                            .setParameter("idioma", tipologiaValorNombre.getIdioma())
                            .executeUpdate();
                } else {
                    query.append("INSERT INTO ins_tipologia_valores (codigo, cod_tipologia, nombre, id, estado) ");
                    query.append("SELECT ?codigo, ?tipologia, ?nombre, MAX(id) + 1, 'A' ");
                    query.append("FROM ins_tipologia_valores ");
                    tpm.getEm().createNativeQuery(query.toString())
                            .setParameter("tipologia", tipologiaValorNombre.getTipValor().getTipologia().getCodigo())
                            .setParameter("codigo", tipologiaValorNombre.getTipValor().getCodigo())
                            .setParameter("nombre", tipologiaValorNombre.getNombre())
                            .executeUpdate();

                    Long key = getIdByKey(tipologiaValorNombre);

                    query = new StringBuilder();
                    query.append("INSERT INTO ins_tipologia_valores_nombres (id_tip_valor, id_idioma, nombre) ");
                    query.append("VALUES (?id, ?idioma, ?nombre) ");
                    tpm.getEm().createNativeQuery(query.toString())
                            .setParameter("id", key)
                            .setParameter("nombre", tipologiaValorNombre.getNombre())
                            .setParameter("idioma", tipologiaValorNombre.getIdioma())
                            .executeUpdate();
                }
            }
        } catch (Exception e) {
            if (e instanceof ErrorValidacion) {
                throw new ErrorValidacion(e.getMessage());
            } else {
                throw new ErrorIntegridad("Error");
            }
        }
    }
}