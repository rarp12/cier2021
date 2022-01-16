/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.fachada;

import co.stackpointer.cier.modelo.auditoria.InterceptorAdministracion;
import co.stackpointer.cier.modelo.entidad.indicador.UnidadFuncional;
import co.stackpointer.cier.modelo.entidad.indicador.UnidadFuncionalValor;
import co.stackpointer.cier.modelo.entidad.instrumento.Tipologia;
import co.stackpointer.cier.modelo.entidad.instrumento.TipologiaValor;
import co.stackpointer.cier.modelo.entidad.instrumento.TipologiaValorNombre;
import co.stackpointer.cier.modelo.entidad.unidad.UndFuncional;
import co.stackpointer.cier.modelo.excepcion.ErrorGeneral;
import co.stackpointer.cier.modelo.tipo.ParamBaseDatos;
import co.stackpointer.cier.modelo.utilidad.UtilOut;
import co.stackpointer.cier.modelo.utilidad.UtilProperties;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.persistence.NoResultException;

/**
 *
 * @author rjay1
 */
@Stateless
public class UndFuncionalFacade implements UndFuncionalFacadeLocal {

    @Inject
    private TenantPersistenceManager tpm;

    @Override
    public List<UndFuncional> getUnidades() {
        List<UndFuncional> unidades = new ArrayList<UndFuncional>();
        try {
            unidades = tpm.getEm().createNamedQuery("UndFuncional.getAll")
                    .getResultList();
        } catch (Exception ex) {
            UtilOut.println(ex);
        }
        return unidades;
    }

    @Override
    public UndFuncional getUnidad(Long id) {
        try {
            return (UndFuncional) tpm.getEm().createNamedQuery("UndFuncional.getById")
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } catch (Exception ex) {
            throw new ErrorGeneral("UndFuncional", "No se pudo obtener datos de sesion");
        }
    }

    @Override
    @Interceptors({InterceptorAdministracion.class})
    public UndFuncional guardarUnidad(String usernameSesion, UndFuncional unidad) {
        try {
            tpm.getEm().persist(unidad);
            tpm.getEm().flush();
            return unidad;
        } catch (Exception e) {
            throw new ErrorGeneral("UndFuncional", "Error al crear la Unidad Funcional");
        }
    }

    @Override
    @Interceptors({InterceptorAdministracion.class})
    public void actualizarUnidad(String usernameSesion, UndFuncional unidad) {
        try {
            tpm.getEm().merge(unidad);
            tpm.getEm().flush();
        } catch (Exception e) {
            throw new ErrorGeneral("UndFuncional", "Error al actualizar la Unidad Funcional");
        }
    }

    @Override
    public List<Tipologia> getTipologiasTipoEspacio() {
        List<Tipologia> tipologias = new ArrayList<Tipologia>();
        try {
            tipologias = tpm.getEm().createNamedQuery("Tipologia.findByCodigo")
                    .setParameter("codigo", "TEE")
                    .getResultList();
        } catch (Exception ex) {
            UtilOut.println(ex);
        }
        return tipologias;
    }

    @Override
    public List<TipologiaValor> getTipologiasValor(String codigo) {
        List<TipologiaValor> tipologias = new ArrayList<TipologiaValor>();
        try {
            tipologias = tpm.getEm().createNamedQuery("TipologiaValor.findByTipologia")
                    .setParameter("codigo", codigo)
                    .getResultList();
        } catch (Exception ex) {
            UtilOut.println(ex);
        }
        return tipologias;
    }

    @Override
    public void guardarTipoValor(Long unidad, String tipologia, String valor) {
        try {
            StringBuilder query = new StringBuilder();
            if (ParamBaseDatos.ORACLE.equals(UtilProperties.getDbMotor())) {
                query.append("insert into ins_unidades_funcionales_valor ");
                query.append("(id, id_tnt, periodo, id_unidad_funcional, ");
                query.append("cod_tipologia, valor_tipologia) ");
                query.append("select max(id) + 1, ?, 201312, ?, ?, ? ");
                query.append("from ins_unidades_funcionales_valor ");
            } else {
                query.append("insert into ins_unidades_funcionales_valores ");
                query.append("(id, id_tnt, periodo, id_unidad_funcional, ");
                query.append("cod_tipologia, valor_tipologia) ");
                query.append("select max(id) + 1, ?, 201312, ?, ?, ? ");
                query.append("from ins_unidades_funcionales_valores ");
            }
            int tenant = tpm.getCurrentTenant().intValue();

            tpm.getEm().createNativeQuery(query.toString())
                    .setParameter(1, tenant)
                    .setParameter(2, unidad)
                    .setParameter(3, tipologia)
                    .setParameter(4, valor)
                    .executeUpdate();
        } catch (Exception e) {
            throw new ErrorGeneral("UnidadFuncionalValor", "Error al crear el Valor de la Unidad Funcional");
        }
    }

    @Override
    public void eliminarTipoValor(Long unidad, String tipologia, String valor) {
        try {
            StringBuilder query = new StringBuilder();
            if (ParamBaseDatos.ORACLE.equals(UtilProperties.getDbMotor())) {
                query.append("DELETE FROM ins_unidades_funcionales_valor ");
                query.append("WHERE id_tnt = ? ");
                query.append("AND id_unidad_funcional = ? ");
                query.append("AND cod_tipologia = ? ");
                query.append("AND valor_tipologia = ? ");
            } else {
                query.append("DELETE FROM ins_unidades_funcionales_valores ");
                query.append("WHERE id_tnt = ? ");
                query.append("AND id_unidad_funcional = ? ");
                query.append("AND cod_tipologia = ? ");
                query.append("AND valor_tipologia = ? ");
            }
            int tenant = tpm.getCurrentTenant().intValue();

            tpm.getEm().createNativeQuery(query.toString())
                    .setParameter(1, tenant)
                    .setParameter(2, unidad)
                    .setParameter(3, tipologia)
                    .setParameter(4, valor)
                    .executeUpdate();
        } catch (Exception e) {
            throw new ErrorGeneral("UnidadFuncionalValor", "Error al eliminar el Valor de la Unidad Funcional");
        }
    }

    @Override
    public List<TipologiaValorNombre> getTipoValorByUnidad(Long unidad, Long idioma) {
        List<TipologiaValorNombre> unidades = new ArrayList<TipologiaValorNombre>();
        try {
            StringBuilder query = new StringBuilder();
            if (ParamBaseDatos.ORACLE.equals(UtilProperties.getDbMotor())) {
                query.append("select ins_tipologia_valores_nombres.* ");
                query.append("from ins_tipologia_valores ");
                query.append("inner join ins_unidades_funcionales_valor ");
                query.append("on ins_tipologia_valores.cod_tipologia = ins_unidades_funcionales_valor.cod_tipologia ");
                query.append("and ins_tipologia_valores.codigo = ins_unidades_funcionales_valor.valor_tipologia ");
                query.append("left join ins_tipologia_valores_nombres ");
                query.append("on ins_tipologia_valores.id = ins_tipologia_valores_nombres.id_tip_valor ");
                query.append("where ins_unidades_funcionales_valor.id_unidad_funcional = ?idUnidad ");
                query.append("and ins_tipologia_valores_nombres.id_idioma = ?idIdioma ");
            } else {
                query.append("select ins_tipologia_valores_nombres.* ");
                query.append("from ins_tipologia_valores ");
                query.append("inner join ins_unidades_funcionales_valores ins_unidades_funcionales_valor ");
                query.append("on ins_tipologia_valores.cod_tipologia = ins_unidades_funcionales_valor.cod_tipologia ");
                query.append("and ins_tipologia_valores.codigo = ins_unidades_funcionales_valor.valor_tipologia ");
                query.append("left join ins_tipologia_valores_nombres ");
                query.append("on ins_tipologia_valores.id = ins_tipologia_valores_nombres.id_tip_valor ");
                query.append("where ins_unidades_funcionales_valor.id_unidad_funcional = ?idUnidad ");
                query.append("and ins_tipologia_valores_nombres.id_idioma = ?idIdioma ");
            }
            unidades = tpm.getEm().createNativeQuery(query.toString(), TipologiaValorNombre.class)
                    .setParameter("idUnidad", unidad)
                    .setParameter("idIdioma", idioma)
                    .getResultList();
        } catch (Exception ex) {
            UtilOut.println(ex);
        }
        return unidades;
    }

    @Override
    public List<TipologiaValorNombre> getTipologiasValorNombre(String codigo, Long idioma) {
        List<TipologiaValorNombre> tipologias = new ArrayList<TipologiaValorNombre>();
        try {
            tipologias = tpm.getEm().createNamedQuery("TipologiaValorNombre.consultarPorCodTipologia")
                    .setParameter("estado", "A")
                    .setParameter("codTipologia", codigo)
                    .setParameter("idIdioma", idioma)
                    .getResultList();
        } catch (Exception ex) {
            UtilOut.println(ex);
        }
        return tipologias;
    }

    @Override
    public UnidadFuncional getUnidadFuncinal(Long id) {
        UnidadFuncional unidad = null;
        try {
            unidad = tpm.getEm().createNamedQuery("UnidadFuncional.getById", UnidadFuncional.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (Exception ex) {
            UtilOut.println(ex);
        }
        return unidad;
    }

    @Override
    public boolean validarNoExisteDuplicidad(Long unidad, String tipologia, String valor) {
        boolean result = true;
        try {
            StringBuilder query = new StringBuilder();
            if (ParamBaseDatos.ORACLE.equals(UtilProperties.getDbMotor())) {
                query.append("select ins_unidades_funcionales_valor.* ");
                query.append("from ins_unidades_funcionales_valor ");
                query.append("where ins_unidades_funcionales_valor.id_unidad_funcional = ?idUnidad ");
                query.append("and ins_unidades_funcionales_valor.cod_tipologia = ?idTipologia ");
                query.append("and ins_unidades_funcionales_valor.valor_tipologia = ?idValor ");
            } else {
                query.append("select ins_unidades_funcionales_valor.* ");
                query.append("from ins_unidades_funcionales_valores ins_unidades_funcionales_valor ");
                query.append("where ins_unidades_funcionales_valor.id_unidad_funcional = ?idUnidad ");
                query.append("and ins_unidades_funcionales_valor.cod_tipologia = ?idTipologia ");
                query.append("and ins_unidades_funcionales_valor.valor_tipologia = ?idValor ");
            }
            List<UnidadFuncionalValor> unidades = tpm.getEm().createNativeQuery(query.toString(), UnidadFuncionalValor.class)
                    .setParameter("idUnidad", unidad)
                    .setParameter("idTipologia", tipologia)
                    .setParameter("idValor", valor)
                    .getResultList();
            if (unidades != null && unidades.size() > 0) {
                result = false;
            }
        } catch (Exception ex) {
            UtilOut.println(ex);
        }
        return result;
    }
}