/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.fachada;

import co.stackpointer.cier.modelo.entidad.digitado.GrpEsp;
import co.stackpointer.cier.modelo.entidad.digitado.Grupo;
import co.stackpointer.cier.modelo.entidad.digitado.GrupoEspacio;
import co.stackpointer.cier.modelo.entidad.digitado.GrupoGeneracion;
import co.stackpointer.cier.modelo.entidad.digitado.GrupoTipologia;
import co.stackpointer.cier.modelo.excepcion.ErrorIntegridad;
import co.stackpointer.cier.modelo.filtro.digitacion.espacios.FiltroDigitacionEdificiosAreas;
import co.stackpointer.cier.modelo.filtro.digitacion.espacios.FiltroDigitacionEspacios;
import co.stackpointer.cier.modelo.utilidad.UtilProperties;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;

/**
 *
 * @author rjay1
 */
@Stateless
public class DigitacionEspacioFacade implements DigitacionEspacioFacadeLocal {

    @Inject
    private TenantPersistenceManager tpm;

    @Override
    public List<Grupo> buscarTodosGrupos() {
        tpm.getEm().clear();
        return tpm.getEm().createNamedQuery("Grupo.findAll").getResultList();
    }

    @Override
    public void crearGrupoGeneracion(List<String> grupos) {
        try {
            tpm.getEm().clear();
            List<GrupoGeneracion> list = tpm.getEm().createNamedQuery("GrupoGeneracion.findAll").getResultList();
            for (GrupoGeneracion item : list) {
                tpm.getEm().remove(item);
            }
            tpm.getEm().flush();
            tpm.getEm().clear();
            int pk = 1;
            for (String grp : grupos) {
                GrupoGeneracion item = new GrupoGeneracion(pk, Integer.parseInt(grp));
                tpm.getEm().persist(item);
                pk++;
            }
            tpm.getEm().flush();
        } catch (Exception e) {
            throw new ErrorIntegridad("No se pudo guardar la generacion: " + e);
        }
    }

    @Override
    public List<GrupoEspacio> buscarTodosGruposEspacios() {
        tpm.getEm().clear();
        return tpm.getEm().createNamedQuery("GrupoEspacio.findAll").getResultList();
    }

    @Override
    public List<GrupoTipologia> buscarTodosGruposTipologias() {
        tpm.getEm().clear();
        return tpm.getEm().createNamedQuery("GrupoTipologia.findAll").getResultList();
    }

    @Override
    public List<GrupoTipologia> buscarGruposTipologiasByCodigo(String codigo, Integer idioma) {
        tpm.getEm().clear();
        return tpm.getEm()
                .createNamedQuery("GrupoTipologia.findByTipologia")
                .setParameter("codigo", codigo)
                .setParameter("idioma", idioma)
                .getResultList();
    }

    @Override
    public List<GrupoEspacio> consultarGruposEspacios(FiltroDigitacionEspacios filtro) {

        StringBuilder sql = new StringBuilder();

        //SELECT
        sql.append("SELECT i FROM GrupoEspacio i");

        //WHERE
        sql.append(" WHERE i.idNivel0 = :n0");
        if (filtro.getIdNivel1() != null) {
            sql.append(" AND i.idNivel1 = :n1");
        }
        if (filtro.getIdNivel2() != null) {
            sql.append(" AND i.idNivel2 = :n2");
        }
        if (filtro.getIdNivel3() != null) {
            sql.append(" AND i.idNivel3 = :n3");
        }
        if (filtro.getIdNivel4() != null) {
            sql.append(" AND i.idNivel4 = :n4");
        }
        if (filtro.getIdNivel5() != null) {
            sql.append(" AND i.idNivel5 = :n5");
        }
        if (filtro.getBoletaCensal() != null && !filtro.getBoletaCensal().equals("")) {
            sql.append(" AND i.boletaCensal = :boletaCensal");
        }
        if (filtro.getCodigoEstablecimiento() != null && !filtro.getCodigoEstablecimiento().equals("")) {
            sql.append(" AND i.codigoEstablecimiento = :codigoEstablecimiento");
        }
        if (filtro.getEstadoBoleta() != null && !filtro.getEstadoBoleta().equals("")) {
            sql.append(" AND i.estadoBoleta = :estadoBoleta");
        }
        if (filtro.getTipoAmbiente() != null && !filtro.getTipoAmbiente().equals("")) {
            sql.append(" AND i.bas002 = :tipoAmbiente");
        }

        Query query = tpm.getEm().createQuery(sql.toString(), GrupoEspacio.class);

        query.setParameter("n0", filtro.getIdNivel0());
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
        if (filtro.getBoletaCensal() != null && !filtro.getBoletaCensal().equals("")) {
            query.setParameter("boletaCensal", filtro.getBoletaCensal());
        }
        if (filtro.getCodigoEstablecimiento() != null && !filtro.getCodigoEstablecimiento().equals("")) {
            query.setParameter("codigoEstablecimiento", filtro.getCodigoEstablecimiento());
        }
        if (filtro.getEstadoBoleta() != null && !filtro.getEstadoBoleta().equals("")) {
            query.setParameter("estadoBoleta", filtro.getEstadoBoleta());
        }
        if (filtro.getTipoAmbiente() != null && !filtro.getTipoAmbiente().equals("")) {
            query.setParameter("tipoAmbiente", filtro.getTipoAmbiente());
        }

        List<GrupoEspacio> result = query.getResultList();

        return result;
    }

    @Override
    public List<GrupoEspacio> consultarEdificiosAreas(FiltroDigitacionEdificiosAreas filtro) {
        StringBuilder sql = new StringBuilder();

        //SELECT
        sql.append("SELECT i FROM GrupoEspacio i");

        //WHERE
        sql.append(" WHERE i.numeroPiso = :numeroPiso AND i.idNivel0 = :n0");
        if (filtro.getIdNivel1() != null) {
            sql.append(" AND i.idNivel1 = :n1");
        }
        if (filtro.getIdNivel2() != null) {
            sql.append(" AND i.idNivel2 = :n2");
        }
        if (filtro.getIdNivel3() != null) {
            sql.append(" AND i.idNivel3 = :n3");
        }
        if (filtro.getIdNivel4() != null) {
            sql.append(" AND i.idNivel4 = :n4");
        }
        if (filtro.getIdNivel5() != null) {
            sql.append(" AND i.idNivel5 = :n5");
        }
        if (filtro.getEstablecimiento() != null && !filtro.getEstablecimiento().equals("")) {
            sql.append(" AND i.codigoEstablecimiento = :codigoEstablecimiento");
        }
        if (filtro.getSede() != null && !filtro.getSede().equals("")) {
            sql.append(" AND i.codigoSede = :codigoSede");
        }
        if (filtro.getPredio() != null && !filtro.getPredio().equals("")) {
            sql.append(" AND i.codigoPredio = :codigoPredio");
        }
        if (filtro.getEstablecimiento() == null && filtro.getCodigoEstablecimiento() != null && !filtro.getCodigoEstablecimiento().equals("")) {
            sql.append(" AND i.codigoEstablecimiento = :codigoEstablecimiento");
        }
        sql.append(" ORDER BY i.codigoEstablecimiento, i.codigoSede, i.codigoPredio, i.codigoEdificio");

        System.out.println(sql.toString());

        Query query = tpm.getEm().createQuery(sql.toString(), GrupoEspacio.class);

        query.setParameter("numeroPiso", "1");
        query.setParameter("n0", filtro.getIdNivel0());
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
        if (filtro.getEstablecimiento() != null && !filtro.getEstablecimiento().equals("")) {
            query.setParameter("codigoEstablecimiento", filtro.getEstablecimiento());
        }
        if (filtro.getSede() != null && !filtro.getSede().equals("")) {
            query.setParameter("codigoSede", filtro.getSede());
        }
        if (filtro.getPredio() != null && !filtro.getPredio().equals("")) {
            query.setParameter("codigoPredio", filtro.getPredio());
        }
        if (filtro.getEstablecimiento() == null && filtro.getCodigoEstablecimiento() != null && !filtro.getCodigoEstablecimiento().equals("")) {
            query.setParameter("codigoEstablecimiento", filtro.getCodigoEstablecimiento());
        }

        List<GrupoEspacio> result = query.getResultList();

        return result;
    }

    @Override
    public List<GrpEsp> consultarEdificiosAreasOra(FiltroDigitacionEdificiosAreas filtro, int type) {
        System.out.println(new java.util.Date());
        List<GrpEsp> result = new ArrayList<GrpEsp>();

        String sql = UtilProperties.getProperties().getProperty("ConsultaDigEspaciosArea");
        int tenant = tpm.getCurrentTenant().intValue();
        Query query = tpm.getEm().createNativeQuery(sql);

        if (filtro.getEstablecimiento() == null && filtro.getCodigoEstablecimiento() != null && !filtro.getCodigoEstablecimiento().equals("")) {
            query.setParameter(1, tenant);
            query.setParameter(2, filtro.getIdNivel0());
            query.setParameter(3, null);
            query.setParameter(4, null);
            query.setParameter(5, null);
            query.setParameter(6, null);
            query.setParameter(7, null);
            query.setParameter(8, filtro.getCodigoEstablecimiento());
            query.setParameter(9, null);
            query.setParameter(10, null);
            query.setParameter(11, type);
        } else {
            query.setParameter(1, tenant);
            query.setParameter(2, filtro.getIdNivel0());
            query.setParameter(3, filtro.getIdNivel1());
            query.setParameter(4, filtro.getIdNivel2());
            query.setParameter(5, filtro.getIdNivel3());
            query.setParameter(6, filtro.getIdNivel4());
            query.setParameter(7, filtro.getIdNivel5());
            query.setParameter(8, filtro.getEstablecimiento());
            query.setParameter(9, filtro.getSede());
            query.setParameter(10, filtro.getPredio());
            query.setParameter(11, type);
        }

        List<Object[]> rawResultList = query.getResultList();
        for (Object[] obj : rawResultList) {
            GrpEsp ge = new GrpEsp();
            ge.setCodigoEstablecimiento(obj[0] == null ? "" : obj[0].toString());
            ge.setNombreEstablecimiento(obj[1] == null ? "" : obj[1].toString());
            ge.setCodigoSede(obj[2] == null ? "" : obj[2].toString());
            ge.setNombreSede(obj[3] == null ? "" : obj[3].toString());
            ge.setCodigoPredio(obj[4] == null ? "" : obj[4].toString());
            ge.setNombrePredio(obj[5] == null ? "" : obj[5].toString());
            ge.setCodigoEdificio(obj[6] == null ? "" : obj[6].toString());
            ge.setSumArea(obj[7] == null ? 0 : Double.parseDouble(obj[7].toString()));
            ge.setSumVolumen(obj[8] == null ? 0 : Double.parseDouble(obj[8].toString()));
            ge.setSumUsuario(obj[9] == null ? 0 : Double.parseDouble(obj[9].toString()));
            result.add(ge);
        }
        System.out.println(new java.util.Date());
        return result;
    }
}