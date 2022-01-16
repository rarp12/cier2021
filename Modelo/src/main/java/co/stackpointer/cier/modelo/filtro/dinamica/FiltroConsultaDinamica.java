/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.filtro.dinamica;

import co.stackpointer.cier.modelo.entidad.indicador.UnidadFuncional;
import co.stackpointer.cier.modelo.tipo.CalificacionCondicion;
import co.stackpointer.cier.modelo.tipo.ServiciosPublicos;
import co.stackpointer.cier.modelo.tipo.SiNo;
import co.stackpointer.cier.modelo.tipo.Vulnerabilidad;
import java.util.List;


/**
 *
 * @author nruiz
 */
public class FiltroConsultaDinamica {
    protected List<String[]> listaTipValores;
    protected Integer periodo;
    protected Long idNivel0;
    protected Long idNivel1;
    protected Long idNivel2;
    protected Long idNivel3;
    protected Long idNivel4;
    protected Long idNivel5;
    protected SiNo campoSino1;
    protected SiNo campoSino2;
    protected SiNo campoSino3;
    protected SiNo campoSinoPred1;
    protected SiNo campoSinoPred2;
    protected SiNo campoSinoPred3;
    protected String preguntaGerencial;
    protected String ambitoPredominante;
    protected String ambitoCondicional;
    protected String codZona;
    protected String codSector;
    protected Vulnerabilidad vulnerabilidad;
    protected ServiciosPublicos servicioPublico;
    protected String unidadFuncional;
    protected CalificacionCondicion condicionDuchas;
    protected CalificacionCondicion condicionLavamanos;
    protected CalificacionCondicion condicionOrinales;
    protected CalificacionCondicion condicionInodoros;
    protected String analisisConsumoAgua;
    protected CalificacionCondicion condicionServivio;
    
    public void init() {
        listaTipValores = null;
        periodo = null;
        idNivel0 = null;
        idNivel1 = null;
        idNivel2 = null;
        idNivel3 = null;
        idNivel4 = null;
        idNivel5 = null;
        campoSino1 = null;
        campoSino2 = null;
        campoSino3 = null;
        campoSinoPred1 = null;
        campoSinoPred2 = null;
        campoSinoPred3 = null;        
        codZona = null;
        codSector = null;
        vulnerabilidad = null;
        servicioPublico = null;
        unidadFuncional = null;
        condicionDuchas = null;
        condicionLavamanos = null;
        condicionOrinales = null;
        condicionInodoros = null;
        analisisConsumoAgua = null;
        condicionServivio = null;
    }

    public List<String[]> getListaTipValores() {
        return listaTipValores;
    }

    public void setListaTipValores(List<String[]> listaTipValores) {
        this.listaTipValores = listaTipValores;
    }

    public Integer getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Integer periodo) {
        this.periodo = periodo;
    }
    
    public Long getIdNivel0() {
        return idNivel0;
    }

    public void setIdNivel0(Long idNivel0) {
        this.idNivel0 = idNivel0;
    }

    public Long getIdNivel1() {
        return idNivel1;
    }

    public void setIdNivel1(Long idNivel1) {
        this.idNivel1 = idNivel1;
    }

    public Long getIdNivel2() {
        return idNivel2;
    }

    public void setIdNivel2(Long idNivel2) {
        this.idNivel2 = idNivel2;
    }

    public Long getIdNivel3() {
        return idNivel3;
    }

    public void setIdNivel3(Long idNivel3) {
        this.idNivel3 = idNivel3;
    }

    public Long getIdNivel4() {
        return idNivel4;
    }

    public void setIdNivel4(Long idNivel4) {
        this.idNivel4 = idNivel4;
    }

    public Long getIdNivel5() {
        return idNivel5;
    }

    public void setIdNivel5(Long idNivel5) {
        this.idNivel5 = idNivel5;
    }

    public SiNo getCampoSino1() {
        return campoSino1;
    }

    public void setCampoSino1(SiNo campoSino1) {
        this.campoSino1 = campoSino1;
    }

    public SiNo getCampoSino2() {
        return campoSino2;
    }

    public void setCampoSino2(SiNo campoSino2) {
        this.campoSino2 = campoSino2;
    }

    public SiNo getCampoSino3() {
        return campoSino3;
    }

    public void setCampoSino3(SiNo campoSino3) {
        this.campoSino3 = campoSino3;
    }

    public SiNo getCampoSinoPred3() {
        return campoSinoPred3;
    }

    public void setCampoSinoPred3(SiNo campoSinoPred3) {
        this.campoSinoPred3 = campoSinoPred3;
    }
    
    public SiNo getCampoSinoPred1() {
        return campoSinoPred1;
    }

    public void setCampoSinoPred1(SiNo campoSinoPred1) {
        this.campoSinoPred1 = campoSinoPred1;
    }

    public SiNo getCampoSinoPred2() {
        return campoSinoPred2;
    }

    public void setCampoSinoPred2(SiNo campoSinoPred2) {
        this.campoSinoPred2 = campoSinoPred2;
    }
    
    public String getPreguntaGerencial() {
        return preguntaGerencial;
    }

    public void setPreguntaGerencial(String preguntaGerencial) {
        this.preguntaGerencial = preguntaGerencial;
    }

    public String getAmbitoPredominante() {
        return ambitoPredominante;
    }

    public void setAmbitoPredominante(String ambitoPredominante) {
        this.ambitoPredominante = ambitoPredominante;
    }

    public String getAmbitoCondicional() {
        return ambitoCondicional;
    }

    public void setAmbitoCondicional(String ambitoCondicional) {
        this.ambitoCondicional = ambitoCondicional;
    }

    public String getCodZona() {
        return codZona;
    }

    public void setCodZona(String codZona) {
        this.codZona = codZona;
    }

    public String getCodSector() {
        return codSector;
    }

    public void setCodSector(String codSector) {
        this.codSector = codSector;
    }

    public Vulnerabilidad getVulnerabilidad() {
        return vulnerabilidad;
    }

    public void setVulnerabilidad(Vulnerabilidad vulnerabilidad) {
        this.vulnerabilidad = vulnerabilidad;
    }

    public ServiciosPublicos getServicioPublico() {
        return servicioPublico;
    }

    public void setServicioPublico(ServiciosPublicos servicioPublico) {
        this.servicioPublico = servicioPublico;
    }

    public String getUnidadFuncional() {
        return unidadFuncional;
    }

    public void setUnidadFuncional(String unidadFuncional) {
        this.unidadFuncional = unidadFuncional;
    }

    public CalificacionCondicion getCondicionDuchas() {
        return condicionDuchas;
    }

    public void setCondicionDuchas(CalificacionCondicion condicionDuchas) {
        this.condicionDuchas = condicionDuchas;
    }

    public CalificacionCondicion getCondicionLavamanos() {
        return condicionLavamanos;
    }

    public void setCondicionLavamanos(CalificacionCondicion condicionLavamanos) {
        this.condicionLavamanos = condicionLavamanos;
    }

    public CalificacionCondicion getCondicionOrinales() {
        return condicionOrinales;
    }

    public void setCondicionOrinales(CalificacionCondicion condicionOrinales) {
        this.condicionOrinales = condicionOrinales;
    }

    public CalificacionCondicion getCondicionInodoros() {
        return condicionInodoros;
    }

    public void setCondicionInodoros(CalificacionCondicion condicionInodoros) {
        this.condicionInodoros = condicionInodoros;
    }

    public String getAnalisisConsumoAgua() {
        return analisisConsumoAgua;
    }

    public void setAnalisisConsumoAgua(String analisisConsumoAgua) {
        this.analisisConsumoAgua = analisisConsumoAgua;
    }

    public CalificacionCondicion getCondicionServivio() {
        return condicionServivio;
    }

    public void setCondicionServivio(CalificacionCondicion condicionServivio) {
        this.condicionServivio = condicionServivio;
    }
        
}
