/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.consulta.dinamica;

import co.stackpointer.cier.modelo.entidad.instrumento.TipologiaValorNombre;
import co.stackpointer.cier.modelo.fachada.ConsultaDinamicaFacadeLocal;
import co.stackpointer.cier.modelo.filtro.dinamica.FiltroConsultaDinamica;
import co.stackpointer.cier.modelo.tipo.Ambitos;
import co.stackpointer.cier.modelo.tipo.CalificacionCondicion;
import co.stackpointer.cier.modelo.tipo.CodPregGerenciales;
import co.stackpointer.cier.modelo.tipo.ParamNivelConsul;
import co.stackpointer.cier.modelo.tipo.ServiciosPublicos;
import co.stackpointer.cier.modelo.tipo.SiNo;
import co.stackpointer.cier.modelo.tipo.TipologiaCodigo;
import co.stackpointer.cier.modelo.tipo.Vulnerabilidad;
import co.stackpointer.cier.modelo.utilidad.UtilCadena;
import co.stackpointer.cier.modelo.utilidad.UtilConsulta;
import co.stackpointer.cier.vista.Utilidades;
import co.stackpointer.cier.vistacontrolador.consulta.ambito.ConsultaAmbito;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.ToggleSelectEvent;

/**
 *
 * @author nruiz
 */
@ManagedBean(name = "consultaDinamica")
@ViewScoped
public class ConsultaDinamicaBean extends ConsultaAmbito {

    @EJB
    private ConsultaDinamicaFacadeLocal facDinamica;
    @ManagedProperty(value = "#{consultaAmbito}")
    private ConsultaAmbito consultaAmbito;
    private List<Registro> lista;
    List<String> colums;
    List<String> columsTotal;
    private List<String> listaAmbPredominantes;
    private List<String> listaAmbCondicionales;
    private List<String> listaPregGerenciales;
    private FiltroConsultaDinamica filtro = new FiltroConsultaDinamica();
    private String tipPredLabel1;
    private boolean mostrarTipPred1;
    private String tipPredLabel2;
    private boolean mostrarTipPred2;
    private String tipPredLabel3;
    private boolean mostrarTipPred3;
    private String tipPredLabel4;
    private boolean mostrarTipPred4;
    private String tipologiaPred5;
    private String tipPredLabel5;
    private String tipologiaCond1;
    private String tipCondLabel1;
    private boolean mostrarTipCond1;
    private String tipCondLabel2;
    private boolean mostrarTipCond2;
    private String tipCondLabel3;
    private boolean mostrarTipCond3;
    private String tipCondLabel4;
    private boolean mostrarTipCond4;
    private boolean mostrarCampoSino1;
    private boolean mostrarCampoSino2;
    private boolean mostrarCampoSino3;
    private boolean mostrarCampoSPPred1;
    private boolean mostrarCampoSPPred2;
    private boolean mostrarCampoSPCond;
    private boolean mostrarCampoVulnera;
    private boolean mostrarCampoVulneraPred;
    private boolean mostrarCampoSinoPred1;
    private boolean mostrarCampoSinoPred2;
    private boolean mostrarCampoSinoPred3;
    private List<TipologiaValorNombre> listaRiesgosNaturales;
    private List<String> seleccionRiesgoNatural;
    private CalificacionCondicion seleccionCondicionRiesgoNatural;
    private boolean mostrarTipoRNPred;
    private boolean mostrarTipoRNCond;
    private List<TipologiaValorNombre> listaRiesgosAntropicos;
    private List<String> seleccionRiesgoAntropico;
    private CalificacionCondicion seleccionCondicionRiesgoAntropico;
    private boolean mostrarTipoRAPred;
    private boolean mostrarTipoRACond;
    private Map<String, Boolean> allSelectTipoRiesgo;
    private List<TipologiaValorNombre> listaTiposPropietarios;
    private List<String> seleccionTipoPropietario;
    private boolean mostrarTipoPropPred;
    private boolean mostrarTipoPropCond;
    private boolean mostrarUnidadFuncionalPred;
    private boolean mostrarUnidadFuncionalCond;
    private boolean mostrarPredConfort;
    private boolean mostrarCondConfort;
    //private boolean mostrarPredConfort;
    private boolean mostrarCondAnalisisConsumoAgua;
    private boolean mostrarPredAnalisisConsumoAgua;
    private boolean mostrarCondicionSPPred;
    private boolean mostrarCondicionSPCond;
    private int cantRegistros = 0;

    @PostConstruct
    public void inicializar() {
        super.initActual();
        this.cargarAmbPredominantes();
        listaSectores = facParam.consultarValoresTipologia(TipologiaCodigo.SECTOR.getValor(),getUsuarioSesion().getUsuario().getIdioma().getId());
        listaZonas = facParam.consultarValoresTipologia(TipologiaCodigo.ZONA.getValor(),getUsuarioSesion().getUsuario().getIdioma().getId());
        seleccionSector = null;
        seleccionZona = null;
        
    }

    public void cargarAmbPredominantes() {
        filtro.setAmbitoCondicional(null);
        resetearTipValores();
        listaAmbPredominantes = facDinamica.consultarAmbPredominantes();        
    }

    public void cargarAmbCondicionales() {
        listaPregGerenciales = new ArrayList<String>();
        filtro.setAmbitoCondicional(null);
        resetearTipValores();
        listaAmbCondicionales = facDinamica.consultarAmbCondicional(filtro.getAmbitoPredominante());
    }
    
    public String obtenerAmbito(String codigo){
        return Utilidades.obtenerMensaje("bd.ins_ambitos."+codigo);
    }

    public void cargarPregGerenciales() {
        listaPregGerenciales = new ArrayList<String>();

        resetearTipValores();
        List<String> listaAux = facDinamica.consultarPregGerenciales(filtro.getAmbitoPredominante(), filtro.getAmbitoCondicional());
        for (String aux : listaAux) {
            listaPregGerenciales.add(aux);
        }
    }

    public void cargarValTipologiasAmb() {
        resetearTipValores();
        if (!UtilCadena.isNullOVacio(filtro.getAmbitoPredominante()) && filtro.getAmbitoPredominante().contains(TipologiaCodigo.RIESGO_NATURAL.getValor())) {
            mostrarTipoRNPred = true;
            listaRiesgosNaturales = facParam.consultarValoresTipologia(TipologiaCodigo.RIESGO_NATURAL.getValor(),getUsuarioSesion().getUsuario().getIdioma().getId());
        } else if (!UtilCadena.isNullOVacio(filtro.getAmbitoCondicional()) && filtro.getAmbitoCondicional().contains(TipologiaCodigo.RIESGO_NATURAL.getValor())) {
            mostrarTipoRNCond = true;
            listaRiesgosNaturales = facParam.consultarValoresTipologia(TipologiaCodigo.RIESGO_NATURAL.getValor(),getUsuarioSesion().getUsuario().getIdioma().getId());
        }

        if (!UtilCadena.isNullOVacio(filtro.getAmbitoPredominante()) && filtro.getAmbitoPredominante().contains(TipologiaCodigo.RIESGO_ANTROPICO.getValor())) {
            mostrarTipoRAPred = true;
            listaRiesgosAntropicos = facParam.consultarValoresTipologia(TipologiaCodigo.RIESGO_ANTROPICO.getValor(),getUsuarioSesion().getUsuario().getIdioma().getId());
        } else if (!UtilCadena.isNullOVacio(filtro.getAmbitoCondicional()) && filtro.getAmbitoCondicional().contains(TipologiaCodigo.RIESGO_ANTROPICO.getValor())) {
            mostrarTipoRACond = true;
            listaRiesgosAntropicos = facParam.consultarValoresTipologia(TipologiaCodigo.RIESGO_ANTROPICO.getValor(),getUsuarioSesion().getUsuario().getIdioma().getId());
        }

        //CASOS ESPECIALES
        
        /**
         * *******************Ambito predominante***********************
         */
        if (CodPregGerenciales.ACC_PROP.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.ACC_CV.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.ACC_OFE.toString().equals(filtro.getPreguntaGerencial())) {
            mostrarTipPred1 = true;
            mostrarCampoSinoPred1 = true;
            tipPredLabel1 = Utilidades.obtenerMensaje("consulta.dinamica.filtro.accesibilidad");
        } else if (CodPregGerenciales.CV_RA.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.CV_ACC.toString().equals(filtro.getPreguntaGerencial())) {
            mostrarTipPred1 = true;
            mostrarCampoVulneraPred = true;
            tipPredLabel1 = Utilidades.obtenerMensaje("consulta.dinamica.filtro.vulnerabilidad");
        } else if (CodPregGerenciales.PROP_RN.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.PROP_RA.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.PROP_SEG.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.PROP_SP.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.PROP_EEE.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.PROP_CON.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.PROP_ACCD.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.PROP_OFE.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.PROP_AMB.toString().equals(filtro.getPreguntaGerencial())) {
            mostrarTipPred1 = true;
            mostrarCampoSinoPred1 = true;
            tipPredLabel1 = Utilidades.obtenerMensaje("consulta.dinamica.filtro.legalizacion");
            mostrarTipoPropPred = true;
            listaTiposPropietarios = facParam.consultarValoresTipologia(TipologiaCodigo.TIPO_PROPIETARIO_PREDIO.getValor(),getUsuarioSesion().getUsuario().getIdioma().getId());
            tipPredLabel2 = Utilidades.obtenerMensaje("consulta.dinamica.filtro.tipoPropietario");
        } else if (CodPregGerenciales.SEG_RN.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.SEG_RA.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.SEG_PROP1.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.SEG_EEE.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.SEG_OFE.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.SEG_AMB.toString().equals(filtro.getPreguntaGerencial())) {
            mostrarTipPred1 = true;
            mostrarCampoSinoPred1 = true;
            tipPredLabel1 = Utilidades.obtenerMensaje("consulta.dinamica.filtro.estructural");
        } else if (CodPregGerenciales.SEG_PROP2.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.SEG_ACCD.toString().equals(filtro.getPreguntaGerencial())) {
            mostrarTipPred1 = true;
            mostrarCampoSinoPred1 = true;
            tipPredLabel1 = Utilidades.obtenerMensaje("consulta.dinamica.filtro.señalizacion");
            mostrarTipPred2 = true;
            mostrarCampoSinoPred2 = true;
            tipPredLabel2 = Utilidades.obtenerMensaje("consulta.dinamica.filtro.evacuacion");

        } else if (CodPregGerenciales.SP_PROP1.toString().equals(filtro.getPreguntaGerencial())) {
            mostrarCampoSPPred1 = true;
            mostrarTipPred1 = true;
            tipPredLabel1 = Utilidades.obtenerMensaje("consulta.dinamica.filtro.servicio");
            mostrarCondicionSPPred = true;
            mostrarTipPred2 = true;
            tipPredLabel2 = Utilidades.obtenerMensaje("consulta.dinamica.filtro.condicion");
        } else if (CodPregGerenciales.SP_EEE.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.SP_OFE.toString().equals(filtro.getPreguntaGerencial())) {
            mostrarCampoSPPred1 = true;
            mostrarTipPred1 = true;
            tipPredLabel1 = Utilidades.obtenerMensaje("consulta.dinamica.filtro.servicio");
            mostrarCondicionSPPred = true;
            mostrarTipPred2 = true;
            tipPredLabel2 = Utilidades.obtenerMensaje("consulta.dinamica.filtro.condicion");
            mostrarTipPred3 = true;
            mostrarCampoSinoPred3 = true;
            tipPredLabel3 = Utilidades.obtenerMensaje("consulta.dinamica.filtro.tieneServ");
        } else if (CodPregGerenciales.SP_PROP2.toString().equals(filtro.getPreguntaGerencial())) {
            mostrarCampoSPPred1 = true;
            mostrarTipPred1 = true;
            tipPredLabel1 = Utilidades.obtenerMensaje("consulta.dinamica.filtro.servicio");
            mostrarTipPred2 = true;
            mostrarCampoSinoPred2 = true;
            tipPredLabel2 = Utilidades.obtenerMensaje("consulta.dinamica.filtro.tieneServ");
        } else if (CodPregGerenciales.SP_CON.toString().equals(filtro.getPreguntaGerencial())) {
            mostrarCondicionSPPred = true;
            mostrarTipPred2 = true;
            tipPredLabel2 = Utilidades.obtenerMensaje("consulta.dinamica.filtro.condicionServAgua");
        } else if (CodPregGerenciales.ACCD_EEE.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.ACCD_PROP.toString().equals(filtro.getPreguntaGerencial())) {
            mostrarTipPred1 = true;
            mostrarCampoSinoPred1 = true;
            tipPredLabel1 = Utilidades.obtenerMensaje("consulta.dinamica.filtro.discapacitados");
        } else if (CodPregGerenciales.EEE_RN.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.EEE_RA.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.EEE_PROP.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.EEE_CON.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.EEE_SP.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.EEE_ACCD.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.EEE_SEG.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.EEE_OFE.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.EEE_AMB.toString().equals(filtro.getPreguntaGerencial())) {
            mostrarTipPred1 = true;
            mostrarCampoSinoPred1 = true;
            tipPredLabel1 = Utilidades.obtenerMensaje("consulta.dinamica.filtro.materialidad");
        } else if (CodPregGerenciales.OFE_EEE.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.OFE_PROP.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.OFE_RA.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.OFE_RN.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.OFE_SEG.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.OFE_SP.toString().equals(filtro.getPreguntaGerencial())) {
            mostrarTipPred1 = true;
            mostrarCampoSinoPred1 = true;
            tipPredLabel1 = Utilidades.obtenerMensaje("consulta.dinamica.column.OFE17");
            mostrarTipPred2 = true;
            mostrarCampoSinoPred2 = true;
            tipPredLabel2 = Utilidades.obtenerMensaje("consulta.dinamica.column.OFE18");
            mostrarTipPred3 = true;
            mostrarCampoSinoPred3 = true;
            tipPredLabel3 = Utilidades.obtenerMensaje("consulta.dinamica.column.OFE19");
        } else if (CodPregGerenciales.AMB_PROP.toString().equals(filtro.getPreguntaGerencial())) {
            mostrarUnidadFuncionalPred = true;
            mostrarTipPred1 = true;
            mostrarCampoSinoPred1 = true;
            tipPredLabel1 = Utilidades.obtenerMensaje("consulta.dinamica.filtro.cumplimientoAmbiente");
        } else if (CodPregGerenciales.CON_SP.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.CON_EEE.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.CON_SOS.toString().equals(filtro.getPreguntaGerencial())) {
            mostrarPredConfort = true;
            mostrarTipPred1 = true;
            tipPredLabel1 = Utilidades.obtenerMensaje("consulta.dinamica.filtro.CondDuchas");
            mostrarTipPred2 = true;
            tipPredLabel2 = Utilidades.obtenerMensaje("consulta.dinamica.filtro.CondLavamanos");
            mostrarTipPred3 = true;
            tipPredLabel3 = Utilidades.obtenerMensaje("consulta.dinamica.filtro.CondOrinales");
            mostrarTipPred4 = true;
            tipPredLabel4 = Utilidades.obtenerMensaje("consulta.dinamica.filtro.CondInodoros");
        } else if (CodPregGerenciales.SOS_CON.toString().equals(filtro.getPreguntaGerencial())) {
            mostrarPredAnalisisConsumoAgua = true;
            mostrarTipPred1 = true;
            tipPredLabel1 = Utilidades.obtenerMensaje("consulta.dinamica.filtro.analisisConsumoAgua");
        }

        /**
         * *******************Ambito condicional***********************
         */
        if (CodPregGerenciales.RN_ACC.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.CV_ACC.toString().equals(filtro.getPreguntaGerencial())) {
            mostrarTipCond1 = true;
            mostrarCampoSino1 = true;
            tipCondLabel1 = Utilidades.obtenerMensaje("consulta.dinamica.filtro.accesibilidad");
        } else if (CodPregGerenciales.RN_PROP1.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.RA_PROP1.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.SEG_PROP1.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.SEG_PROP2.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.SP_PROP1.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.SP_PROP2.toString().equals(filtro.getPreguntaGerencial())) {
            mostrarTipCond1 = true;
            mostrarCampoSino1 = true;
            tipCondLabel1 = Utilidades.obtenerMensaje("consulta.dinamica.filtro.legalizacion");
        } else if (CodPregGerenciales.RN_PROP2.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.RA_PROP2.toString().equals(filtro.getPreguntaGerencial())) {
            mostrarTipoPropCond = true;
            listaTiposPropietarios = facParam.consultarValoresTipologia(TipologiaCodigo.TIPO_PROPIETARIO_PREDIO.getValor(),getUsuarioSesion().getUsuario().getIdioma().getId());
            tipCondLabel2 = Utilidades.obtenerMensaje("consulta.dinamica.filtro.tipoPropietario");
        } else if (CodPregGerenciales.ACCD_PROP.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.ACC_PROP.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.EEE_PROP.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.OFE_PROP.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.AMB_PROP.toString().equals(filtro.getPreguntaGerencial())) {
            mostrarTipCond1 = true;
            mostrarCampoSino1 = true;
            tipCondLabel1 = Utilidades.obtenerMensaje("consulta.dinamica.filtro.legalizacion");
            mostrarTipoPropCond = true;
            listaTiposPropietarios = facParam.consultarValoresTipologia(TipologiaCodigo.TIPO_PROPIETARIO_PREDIO.getValor(),getUsuarioSesion().getUsuario().getIdioma().getId());
            tipCondLabel2 = Utilidades.obtenerMensaje("consulta.dinamica.filtro.tipoPropietario");
        } else if (CodPregGerenciales.RN_SEG1.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.RA_SEG1.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.PROP_SEG.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.EEE_SEG.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.OFE_SEG.toString().equals(filtro.getPreguntaGerencial())) {
            mostrarTipCond1 = true;
            mostrarCampoSino1 = true;
            tipCondLabel1 = Utilidades.obtenerMensaje("consulta.dinamica.filtro.estructural");
        } else if (CodPregGerenciales.RN_SEG2.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.RA_SEG2.toString().equals(filtro.getPreguntaGerencial())) {
            mostrarTipCond1 = true;
            mostrarCampoSino1 = true;
            tipCondLabel1 = Utilidades.obtenerMensaje("consulta.dinamica.filtro.señalizacion");
            mostrarTipCond2 = true;
            mostrarCampoSino2 = true;
            tipCondLabel2 = Utilidades.obtenerMensaje("consulta.dinamica.filtro.evacuacion");
        } else if (CodPregGerenciales.RN_EEE.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.PROP_EEE.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.SEG_EEE.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.SP_EEE.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.ACCD_EEE.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.CON_EEE.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.OFE_EEE.toString().equals(filtro.getPreguntaGerencial())) {
            mostrarTipCond1 = true;
            mostrarCampoSino1 = true;
            tipCondLabel1 = Utilidades.obtenerMensaje("consulta.dinamica.filtro.materialidad");
        } else if (CodPregGerenciales.RN_CV.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.RA_CV.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.ACC_CV.toString().equals(filtro.getPreguntaGerencial())) {
            mostrarTipCond1 = true;
            mostrarCampoVulnera = true;
            tipCondLabel1 = Utilidades.obtenerMensaje("consulta.dinamica.filtro.vulnerabilidad");
        } else if (CodPregGerenciales.RN_ACCD.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.RA_ACCD.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.PROP_ACCD.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.SEG_ACCD.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.EEE_ACCD.toString().equals(filtro.getPreguntaGerencial())) {
            mostrarTipCond1 = true;
            mostrarCampoSino1 = true;
            tipCondLabel1 = Utilidades.obtenerMensaje("consulta.dinamica.filtro.discapacitados");
        } else if (CodPregGerenciales.PROP_SP.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.OFE_SP.toString().equals(filtro.getPreguntaGerencial())) {
            mostrarTipCond1 = true;
            mostrarCampoSPCond = true;
            tipCondLabel1 = Utilidades.obtenerMensaje("consulta.dinamica.filtro.servicio");
            mostrarCondicionSPCond = true;
            mostrarTipCond2 = true;
            tipCondLabel2 = Utilidades.obtenerMensaje("consulta.dinamica.filtro.condicion");
        } else if (CodPregGerenciales.EEE_SP.toString().equals(filtro.getPreguntaGerencial())) {
            mostrarTipCond1 = true;
            mostrarCampoSPCond = true;
            tipCondLabel1 = Utilidades.obtenerMensaje("consulta.dinamica.filtro.servicio");
            mostrarCondicionSPCond = true;
            mostrarTipCond2 = true;
            tipCondLabel2 = Utilidades.obtenerMensaje("consulta.dinamica.filtro.condicion");
            mostrarTipCond3 = true;
            mostrarCampoSino3 = true;
            tipCondLabel3 = Utilidades.obtenerMensaje("consulta.dinamica.filtro.tieneServ");
        } else if (CodPregGerenciales.ACC_OFE.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.EEE_OFE.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.PROP_OFE.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.SEG_OFE.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.SP_OFE.toString().equals(filtro.getPreguntaGerencial())) {
            mostrarTipCond1 = true;
            mostrarCampoSino1 = true;
            tipCondLabel1 = Utilidades.obtenerMensaje("consulta.dinamica.column.OFE17");
            mostrarTipCond2 = true;
            mostrarCampoSino2 = true;
            tipCondLabel2 = Utilidades.obtenerMensaje("consulta.dinamica.column.OFE18");
            mostrarTipCond3 = true;
            mostrarCampoSino3 = true;
            tipCondLabel3 = Utilidades.obtenerMensaje("consulta.dinamica.column.OFE19");
        } else if (CodPregGerenciales.PROP_AMB.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.EEE_AMB.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.SEG_AMB.toString().equals(filtro.getPreguntaGerencial())) {
            mostrarUnidadFuncionalCond = true;
            mostrarTipCond1 = true;
            mostrarCampoSino1 = true;
            tipCondLabel1 = Utilidades.obtenerMensaje("consulta.dinamica.filtro.cumplimientoAmbiente");
        } else if (CodPregGerenciales.PROP_CON.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.SP_CON.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.EEE_CON.toString().equals(filtro.getPreguntaGerencial())
                || CodPregGerenciales.SOS_CON.toString().equals(filtro.getPreguntaGerencial())) {
            mostrarCondConfort = true;
            mostrarTipCond1 = true;
            tipCondLabel1 = Utilidades.obtenerMensaje("consulta.dinamica.filtro.CondDuchas");
            mostrarTipCond2 = true;
            tipCondLabel2 = Utilidades.obtenerMensaje("consulta.dinamica.filtro.CondLavamanos");
            mostrarTipCond3 = true;
            tipCondLabel3 = Utilidades.obtenerMensaje("consulta.dinamica.filtro.CondOrinales");
            mostrarTipCond4 = true;
            tipCondLabel4 = Utilidades.obtenerMensaje("consulta.dinamica.filtro.CondInodoros");
        } else if (CodPregGerenciales.CON_SOS.toString().equals(filtro.getPreguntaGerencial())) {
            mostrarCondAnalisisConsumoAgua = true;
            mostrarTipCond1 = true;
            tipCondLabel1 = Utilidades.obtenerMensaje("consulta.dinamica.filtro.analisisConsumoAgua");
        } else if (CodPregGerenciales.CON_SP.toString().equals(filtro.getPreguntaGerencial())) {
            mostrarCondicionSPCond = true;
            mostrarTipCond2 = true;
            tipCondLabel2 = Utilidades.obtenerMensaje("consulta.dinamica.filtro.condicionServAgua");
        }

        resetearColumnas();
    }

    @Override
    public void resetearFiltros() {
        consultaAmbito.resetearNiveles();
        this.resetearListasEstSedesPredios();
        this.seleccionSector = null;
        this.seleccionZona = null;
        consultaAmbito.nivelDPAOfAccesoUsuario();
        seleccionRiesgoAntropico = new ArrayList<String>();
        seleccionRiesgoNatural = new ArrayList<String>();
        allSelectTipoRiesgo = new HashMap<String, Boolean>();
        seleccionCondicionRiesgoNatural = null;
        seleccionCondicionRiesgoAntropico = null;
        seleccionTipoPropietario = new ArrayList<String>();
        filtro.init();
        resetearColumnas();
    }

    public void resetearTipValores() {
        lista = new ArrayList<Registro>();
        columsTotal = new ArrayList<String>();
        colums = new ArrayList<String>();
        mostrarTipoRNPred = false;
        mostrarTipoRNCond = false;
        seleccionCondicionRiesgoNatural = null;
        seleccionCondicionRiesgoAntropico = null;
        seleccionRiesgoNatural = new ArrayList<String>();
        listaRiesgosNaturales = new ArrayList<TipologiaValorNombre>();
        mostrarTipoRAPred = false;
        mostrarTipoRACond = false;
        seleccionRiesgoAntropico = new ArrayList<String>();
        listaRiesgosAntropicos = new ArrayList<TipologiaValorNombre>();
        allSelectTipoRiesgo = new HashMap<String, Boolean>();
        mostrarTipoPropPred = false;
        mostrarTipoPropCond = false;
        seleccionTipoPropietario = new ArrayList<String>();
        listaTiposPropietarios = new ArrayList<TipologiaValorNombre>();
        mostrarUnidadFuncionalPred = false;
        mostrarUnidadFuncionalCond = false;
        mostrarPredConfort = false;
        mostrarCondConfort = false;
        mostrarCondAnalisisConsumoAgua = false;
        mostrarPredAnalisisConsumoAgua = false;
        mostrarCondicionSPPred = false;
        mostrarCondicionSPCond = false;
        filtro.init();
        tipPredLabel1 = null;
        mostrarTipPred1 = false;
        tipPredLabel2 = null;
        mostrarTipPred2 = false;
        tipPredLabel3 = null;
        mostrarTipPred3 = false;
        tipPredLabel4 = null;
        mostrarTipPred4 = false;
        tipologiaPred5 = null;
        tipPredLabel5 = null;
        tipologiaCond1 = null;
        tipCondLabel1 = null;
        mostrarTipCond1 = false;
        tipCondLabel2 = null;
        mostrarTipCond2 = false;
        tipCondLabel3 = null;
        mostrarTipCond3 = false;
        tipCondLabel4 = null;
        mostrarTipCond4 = false;
        mostrarCampoSino1 = false;
        mostrarCampoSino2 = false;
        mostrarCampoSino3 = false;
        mostrarCampoSinoPred1 = false;
        mostrarCampoSinoPred2 = false;
        mostrarCampoSinoPred3 = false;
        mostrarCampoSPPred1 = false;
        mostrarCampoSPPred2 = false;
        mostrarCampoSPCond = false;
        mostrarCampoVulnera = false;
        mostrarCampoVulneraPred = false;
    }

    private String codigosSeleccionados(List<String> seleccionLista) {
        String codigos = null;
        if (!seleccionLista.isEmpty()) {
            boolean sw = true;
            for (String riesgo : seleccionLista) {
                if (sw) {
                    codigos = "'" + riesgo + "'";
                    sw = false;
                } else {
                    codigos += ",'" + riesgo + "'";
                }
            }
        }
        return codigos;
    }
    
    public String getColumnaCodNiv(ParamNivelConsul parametro ){
        if (listaNivelesConf.containsKey(parametro.getNivel())) {
            return Utilidades.obtenerMensaje("consultas.ambito.comun.columna.codigo.nivel", listaNivelesConf.get(parametro.getNivel()));
        }
        return "";
    }

    public void consultar() {
        //filtro.init();
        filtro.setListaTipValores(new ArrayList<String[]>());
        filtro.setPeriodo(periodo);

        if (seleccionRiesgoNatural.isEmpty() && allSelectTipoRiesgo.containsKey("chkmRNCond") && allSelectTipoRiesgo.get("chkmRNCond")) {
            this.seleccionRiesgoNatural = seleccionarTodoTipo(listaRiesgosNaturales);
        }
        if (seleccionRiesgoAntropico.isEmpty() && allSelectTipoRiesgo.containsKey("chkmRACond") && allSelectTipoRiesgo.get("chkmRACond")) {
            this.seleccionRiesgoAntropico = seleccionarTodoTipo(listaRiesgosAntropicos);
        }

        if (!seleccionRiesgoNatural.isEmpty() || !UtilCadena.isNullOVacio(seleccionCondicionRiesgoNatural)) {
            String tipo = "'" + TipologiaCodigo.RIESGO_NATURAL.getValor() + "'";
            String codTipos = !seleccionRiesgoNatural.isEmpty()
                    ? codigosSeleccionados(seleccionRiesgoNatural) : null;
            String condicion = !UtilCadena.isNullOVacio(seleccionCondicionRiesgoNatural)
                    ? String.valueOf(seleccionCondicionRiesgoNatural.getValor()) : null;
            filtro.getListaTipValores().add(new String[]{tipo, codTipos, condicion});
        }
        if (!seleccionRiesgoAntropico.isEmpty() || !UtilCadena.isNullOVacio(seleccionCondicionRiesgoAntropico)) {
            String tipo = "'" + TipologiaCodigo.RIESGO_ANTROPICO.getValor() + "'";
            String codTipos = !seleccionRiesgoAntropico.isEmpty()
                    ? codigosSeleccionados(seleccionRiesgoAntropico) : null;
            String condicion = !UtilCadena.isNullOVacio(seleccionCondicionRiesgoAntropico)
                    ? String.valueOf(seleccionCondicionRiesgoAntropico.getValor()) : null;
            filtro.getListaTipValores().add(new String[]{tipo, codTipos, condicion});
        }
        if (!seleccionTipoPropietario.isEmpty()) {
            String tipo = "'" + TipologiaCodigo.TIPO_PROPIETARIO_PREDIO.getValor() + "'";
            String codTipos = codigosSeleccionados(seleccionTipoPropietario);
            filtro.getListaTipValores().add(new String[]{tipo, codTipos, null});
        }

        filtro.setIdNivel0(consultaAmbito.getSeleccionNivel0() != null ? consultaAmbito.getSeleccionNivel0().getId() : null);
        filtro.setIdNivel1(consultaAmbito.getSeleccionNivel1() != null ? consultaAmbito.getSeleccionNivel1().getId() : null);
        filtro.setIdNivel2(consultaAmbito.getSeleccionNivel2() != null ? consultaAmbito.getSeleccionNivel2().getId() : null);
        filtro.setIdNivel3(consultaAmbito.getSeleccionNivel3() != null ? consultaAmbito.getSeleccionNivel3().getId() : null);
        filtro.setIdNivel4(consultaAmbito.getSeleccionNivel4() != null ? consultaAmbito.getSeleccionNivel4().getId() : null);
        filtro.setIdNivel5(consultaAmbito.getSeleccionNivel5() != null ? consultaAmbito.getSeleccionNivel5().getId() : null);
  
        filtro.setCodZona(seleccionZona != null ? seleccionZona.getTipValor().getCodigo() : null);
        filtro.setCodSector(seleccionSector != null ? seleccionSector.getTipValor().getCodigo() : null);

        List<Object[]> resultados = facDinamica.consultarPrediosPorTipologias(filtro);
        cantRegistros = resultados.size();
        if (resultados.size() < 1) {
            mensajeTablaVacia = Utilidades.obtenerMensaje("aplicacion.general.noExistenRegistros");
        }

        lista = new ArrayList<Registro>();
        Registro registro = null;
        for (Object[] fila : resultados) {
            registro = new Registro();
            int i = 0;
            //registro.setIdInstrumento((String) fila[i++]);
            registro.setIdDigInstrumento(new Long(fila[i++] + ""));
            HashMap<String, Object> hmAux = new HashMap<String, Object>();
            
            //
            
            hmAux.put(getColumnaCodNiv(ParamNivelConsul.UNO), UtilCadena.isNullOVacio(fila[i]) ? "" : (String) fila[i]);
            i++;
            hmAux.put(listaNivelesConf.get(ParamNivelConsul.UNO.getNivel()), UtilCadena.isNullOVacio(fila[i]) ? "" : (String) fila[i]);
            i++;
            
            hmAux.put(getColumnaCodNiv(ParamNivelConsul.DOS), UtilCadena.isNullOVacio(fila[i]) ? "" : (String) fila[i]);
            i++;
            hmAux.put(listaNivelesConf.get(ParamNivelConsul.DOS.getNivel()), UtilCadena.isNullOVacio(fila[i]) ? "" : (String) fila[i]);
            i++;
            
            hmAux.put(getColumnaCodNiv(ParamNivelConsul.TRES), UtilCadena.isNullOVacio(fila[i]) ? "" : (String) fila[i]);
            i++;
            hmAux.put(listaNivelesConf.get(ParamNivelConsul.TRES.getNivel()), UtilCadena.isNullOVacio(fila[i]) ? "" : (String) fila[i]);
            i++;
            
            hmAux.put(getColumnaCodNiv(ParamNivelConsul.CUATRO), UtilCadena.isNullOVacio(fila[i]) ? "" : (String) fila[i]);
            i++;
            hmAux.put(listaNivelesConf.get(ParamNivelConsul.CUATRO.getNivel()), UtilCadena.isNullOVacio(fila[i]) ? "" : (String) fila[i]);
            i++;
            
            hmAux.put(getColumnaCodNiv(ParamNivelConsul.CINCO), UtilCadena.isNullOVacio(fila[i]) ? "" : (String) fila[i]);
            i++;
            hmAux.put(listaNivelesConf.get(ParamNivelConsul.CINCO.getNivel()), UtilCadena.isNullOVacio(fila[i]) ? "" : (String) fila[i]);
            i++;

            //
 
            hmAux.put(Utilidades.obtenerMensaje("aplicacion.general.codEst"), UtilCadena.isNullOVacio(fila[i]) ? "" : (String) fila[i]);
            i++;
            hmAux.put(Utilidades.obtenerMensaje("aplicacion.general.nomEst"), UtilCadena.isNullOVacio(fila[i]) ? "" : (String) fila[i]);
            i++;
            hmAux.put(Utilidades.obtenerMensaje("aplicacion.general.codSede"), UtilCadena.isNullOVacio(fila[i]) ? "" : (String) fila[i]);
            i++;
            hmAux.put(Utilidades.obtenerMensaje("aplicacion.general.nomSede"), UtilCadena.isNullOVacio(fila[i]) ? "" : (String) fila[i]);
            i++;
            hmAux.put(Utilidades.obtenerMensaje("aplicacion.general.codPredio"), UtilCadena.isNullOVacio(fila[i]) ? "" : (String) fila[i]);
            i++;
            hmAux.put(Utilidades.obtenerMensaje("aplicacion.general.nomPredio"), UtilCadena.isNullOVacio(fila[i]) ? "" : (String) fila[i]);
            i++;

            if (Ambitos.A_RN.toString().equals(filtro.getAmbitoPredominante())
                    || Ambitos.A_RN.toString().equals(filtro.getAmbitoCondicional())) {
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.RN1"), UtilCadena.isNullOVacio(fila[i]) ? "" : factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i]),getUsuarioSesion().getUsuario().getIdioma().getId()));
                i++;
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.RN2"), UtilCadena.isNullOVacio(fila[i]) ? "" : formatear((String) fila[i]));
                i++;
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.RN3"), UtilCadena.isNullOVacio(fila[i]) ? "" : factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i]),getUsuarioSesion().getUsuario().getIdioma().getId()));
                i++;
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.RN4"), UtilCadena.isNullOVacio(fila[i]) ? "" : formatear((String) fila[i]));
                i++;
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.RN5"), UtilCadena.isNullOVacio(fila[i]) ? "" : factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i]),getUsuarioSesion().getUsuario().getIdioma().getId()));
                i++;
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.RN6"), UtilCadena.isNullOVacio(fila[i]) ? "" : formatear((String) fila[i]));
                i++;
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.RN7"), UtilCadena.isNullOVacio(fila[i]) ? "" : Utilidades.obtenerMensaje((String) fila[i]));
                i++;
            }
            if (Ambitos.A_RA.toString().equals(filtro.getAmbitoPredominante())
                    || Ambitos.A_RA.toString().equals(filtro.getAmbitoCondicional())) {
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.RA1"), UtilCadena.isNullOVacio(fila[i]) ? "" : factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i]),getUsuarioSesion().getUsuario().getIdioma().getId()));
                i++;
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.RA2"), UtilCadena.isNullOVacio(fila[i]) ? "" : formatear((String) fila[i]));
                i++;
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.RA3"), UtilCadena.isNullOVacio(fila[i]) ? "" : factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i]),getUsuarioSesion().getUsuario().getIdioma().getId()));
                i++;
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.RA4"), UtilCadena.isNullOVacio(fila[i]) ? "" : formatear((String) fila[i]));
                i++;
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.RA5"), UtilCadena.isNullOVacio(fila[i]) ? "" : factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i]),getUsuarioSesion().getUsuario().getIdioma().getId()));
                i++;
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.RA6"), UtilCadena.isNullOVacio(fila[i]) ? "" : formatear((String) fila[i]));
                i++;
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.RA7"), UtilCadena.isNullOVacio(fila[i]) ? "" : Utilidades.obtenerMensaje((String) fila[i]));
                i++;
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.RA8"), UtilCadena.isNullOVacio(fila[i]) ? "" : factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i]),getUsuarioSesion().getUsuario().getIdioma().getId()));
                i++;
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.RA9"), UtilCadena.isNullOVacio(fila[i]) ? "" : formatear((String) fila[i]));
                i++;
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.RA10"), factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i]),getUsuarioSesion().getUsuario().getIdioma().getId()));
                i++;
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.RA11"), UtilCadena.isNullOVacio(fila[i]) ? "" : formatear((String) fila[i]));
                i++;
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.RA12"), factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i]),getUsuarioSesion().getUsuario().getIdioma().getId()));
                i++;
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.RA13"), UtilCadena.isNullOVacio(fila[i]) ? "" : formatear((String) fila[i]));
                i++;
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.RA14"), UtilCadena.isNullOVacio(fila[i]) ? "" : factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i]),getUsuarioSesion().getUsuario().getIdioma().getId()));
                i++;
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.RA15"), UtilCadena.isNullOVacio(fila[i]) ? "" : formatear((String) fila[i]));
                i++;
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.RA16"), UtilCadena.isNullOVacio(fila[i]) ? "" : factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i]),getUsuarioSesion().getUsuario().getIdioma().getId()));
                i++;
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.RA17"), UtilCadena.isNullOVacio(fila[i]) ? "" : formatear((String) fila[i]));
                i++;
            }
            if (Ambitos.A_ACC.toString().equals(filtro.getAmbitoPredominante())
                    || Ambitos.A_ACC.toString().equals(filtro.getAmbitoCondicional())) {
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.ACC1"), UtilCadena.isNullOVacio(fila[i]) ? "" : factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i]),getUsuarioSesion().getUsuario().getIdioma().getId()));
                i++;
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.ACC2"), UtilCadena.isNullOVacio(fila[i]) ? "" : formatear((String) fila[i]));
                i++;
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.ACC3"), UtilCadena.isNullOVacio(fila[i]) ? "" : factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i]),getUsuarioSesion().getUsuario().getIdioma().getId()));
                i++;
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.ACC4"), UtilCadena.isNullOVacio(fila[i]) ? "" : formatear((String) fila[i]));
                i++;
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.ACC5"), UtilCadena.isNullOVacio(fila[i]) ? "" : factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i]),getUsuarioSesion().getUsuario().getIdioma().getId()));
                i++;
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.ACC6"), UtilCadena.isNullOVacio(fila[i]) ? "" : formatear((String) fila[i]));
                i++;
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.ACC7"), UtilCadena.isNullOVacio(fila[i]) ? "" : factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i]),getUsuarioSesion().getUsuario().getIdioma().getId()));
                i++;
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.ACC8"), UtilCadena.isNullOVacio(fila[i]) ? "" : formatear((String) fila[i]));
                i++;
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.ACC9"), UtilCadena.isNullOVacio(fila[i]) ? "" : factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i]),getUsuarioSesion().getUsuario().getIdioma().getId()));
                i++;
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.ACC10"), UtilCadena.isNullOVacio(fila[i]) ? "" : formatear((String) fila[i]));
                i++;
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.ACC11"), UtilCadena.isNullOVacio(fila[i]) ? "" : formatear((String) fila[i]));
                i++;
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.ACC12"), UtilCadena.isNullOVacio(fila[i]) ? "" : formatear((String) fila[i]));
                i++;
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.ACC13"), UtilCadena.isNullOVacio(fila[i]) ? "" : Utilidades.obtenerMensaje((String) fila[i]));
                i++;
            }
            if (Ambitos.A_OFE.toString().equals(filtro.getAmbitoPredominante())
                    || Ambitos.A_OFE.toString().equals(filtro.getAmbitoCondicional())) {
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.OFE1"), UtilCadena.isNullOVacio(fila[i]) ? "" : formatear((String) fila[i]));
                i++;
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.OFE2"), UtilCadena.isNullOVacio(fila[i]) ? "" : formatear((String) fila[i]));
                i++;
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.OFE3"), UtilCadena.isNullOVacio(fila[i]) ? "" : formatear((String) fila[i]));
                i++;
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.OFE4"), UtilCadena.isNullOVacio(fila[i]) ? "" : formatear((String) fila[i]));
                i++;
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.OFE5"), UtilCadena.isNullOVacio(fila[i]) ? "" : formatear((String) fila[i]));
                i++;
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.OFE6"), UtilCadena.isNullOVacio(fila[i]) ? "" : formatear((String) fila[i]));
                i++;
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.OFE7"), UtilCadena.isNullOVacio(fila[i]) ? "" : formatear((String) fila[i]));
                i++;
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.OFE8"), UtilCadena.isNullOVacio(fila[i]) ? "" : formatear((String) fila[i]));
                i++;
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.OFE9"), UtilCadena.isNullOVacio(fila[i]) ? "" : formatear((String) fila[i]));
                i++;
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.OFE10"), UtilCadena.isNullOVacio(fila[i]) ? "" : formatear((String) fila[i]));
                i++;
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.OFE11"), UtilCadena.isNullOVacio(fila[i]) ? "" : formatear((String) fila[i]));
                i++;
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.OFE12"), UtilCadena.isNullOVacio(fila[i]) ? "" : formatear((String) fila[i]));
                i++;
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.OFE13"), UtilCadena.isNullOVacio(fila[i]) ? "" : formatear((String) fila[i]));
                i++;
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.OFE14"), UtilCadena.isNullOVacio(fila[i]) ? "" : formatear((String) fila[i]));
                i++;
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.OFE15"), UtilCadena.isNullOVacio(fila[i]) ? "" : formatear((String) fila[i]));
                i++;
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.OFE16"), UtilCadena.isNullOVacio(fila[i]) ? "" : formatear((String) fila[i]));
                i++;
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.OFE17"), UtilCadena.isNullOVacio(fila[i]) ? "" : formatear((String) fila[i]));
                i++;
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.OFE18"), UtilCadena.isNullOVacio(fila[i]) ? "" : formatear((String) fila[i]));
                i++;
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.OFE19"), UtilCadena.isNullOVacio(fila[i]) ? "" : formatear((String) fila[i]));
                i++;
            }
            if (Ambitos.A_PROP.toString().equals(filtro.getAmbitoPredominante())
                    || Ambitos.A_PROP.toString().equals(filtro.getAmbitoCondicional())) {
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.PROP1"), UtilCadena.isNullOVacio(fila[i]) ? "" : factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i]),getUsuarioSesion().getUsuario().getIdioma().getId()));
                i++;
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.PROP2"), UtilCadena.isNullOVacio(fila[i]) ? "" : Utilidades.obtenerMensaje((String) fila[i]));
                i++;
            }
            if (Ambitos.A_CV.toString().equals(filtro.getAmbitoPredominante())
                    || Ambitos.A_CV.toString().equals(filtro.getAmbitoCondicional())) {
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.VUL1"), UtilCadena.isNullOVacio(fila[i]) ? "" : Utilidades.obtenerMensaje((String) fila[i]));
                i++;
            }
            if (Ambitos.A_ACCD.toString().equals(filtro.getAmbitoPredominante())
                    || Ambitos.A_ACCD.toString().equals(filtro.getAmbitoCondicional())) {
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.ACCD1"), UtilCadena.isNullOVacio(fila[i]) ? "" : Utilidades.obtenerMensaje((String) fila[i]));
                i++;
            }
            if (Ambitos.A_AMB.toString().equals(filtro.getAmbitoPredominante())
                    || Ambitos.A_AMB.toString().equals(filtro.getAmbitoCondicional())) {
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.AMB1"), UtilCadena.isNullOVacio(fila[i]) ? "" : formatear((String) fila[i]));
                i++;
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.AMB2"), UtilCadena.isNullOVacio(fila[i]) ? "" : formatear(fila[i].toString()));
                i++;
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.AMB3"), UtilCadena.isNullOVacio(fila[i]) ? "" : formatear(fila[i].toString()));
                i++;
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.AMB4"), UtilCadena.isNullOVacio(fila[i]) ? "" : formatear((String) fila[i]));
                i++;
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.AMB5"), UtilCadena.isNullOVacio(fila[i]) ? "" : formatear((String) fila[i]));
                i++;
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.AMB6"), UtilCadena.isNullOVacio(fila[i]) ? "" : formatear((String) fila[i]));
                i += 2;
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.AMB7"), UtilCadena.isNullOVacio(fila[i]) ? "" : formatear((String) fila[i]));
                i++;
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.AMB8"), UtilCadena.isNullOVacio(fila[i]) ? "" : formatear((String) fila[i]));
                i++;
            }
            if (Ambitos.A_CON.toString().equals(filtro.getAmbitoPredominante())
                    || Ambitos.A_CON.toString().equals(filtro.getAmbitoCondicional())) {
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.CON1"), UtilCadena.isNullOVacio(fila[i]) ? "" : fila[i]);
                i++;
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.CON2"), UtilCadena.isNullOVacio(fila[i]) ? "" : fila[i]);
                i++;
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.CON3"), UtilCadena.isNullOVacio(fila[i]) ? "" : fila[i]);
                i++;
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.CON4"), UtilCadena.isNullOVacio(fila[i]) ? "" : fila[i]);
                i++;
            }
            if (Ambitos.A_SOS.toString().equals(filtro.getAmbitoPredominante())
                    || Ambitos.A_SOS.toString().equals(filtro.getAmbitoCondicional())) {
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.SOS1"), UtilCadena.isNullOVacio(fila[i]) ? "" : Utilidades.obtenerMensaje((String)fila[i]));
                i++;
            }
            if (Ambitos.A_SEG.toString().equals(filtro.getAmbitoPredominante())
                    || Ambitos.A_SEG.toString().equals(filtro.getAmbitoCondicional())) {
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.SEG1"), UtilCadena.isNullOVacio(fila[i]) ? "" : Utilidades.obtenerMensaje((String)fila[i]));
                i++;
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.SEG2"), UtilCadena.isNullOVacio(fila[i]) ? "" : Utilidades.obtenerMensaje((String)fila[i]));
                i++;
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.SEG3"), UtilCadena.isNullOVacio(fila[i]) ? "" : Utilidades.obtenerMensaje((String)fila[i]));
                i++;
            }
            if (Ambitos.A_EEE.toString().equals(filtro.getAmbitoPredominante())
                    || Ambitos.A_EEE.toString().equals(filtro.getAmbitoCondicional())) {
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.EEE1"), UtilCadena.isNullOVacio(fila[i]) ? "" : Utilidades.obtenerMensaje((String)fila[i]));
                i++;
            }
            if (Ambitos.A_SP.toString().equals(filtro.getAmbitoPredominante())
                    || Ambitos.A_SP.toString().equals(filtro.getAmbitoCondicional())) {
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.SP1"), UtilCadena.isNullOVacio(fila[i]) ? "" : Utilidades.obtenerMensaje((String)fila[i]));
                i++;
                hmAux.put(Utilidades.obtenerMensaje("consulta.dinamica.column.SP2"), UtilCadena.isNullOVacio(fila[i]) ? "" : fila[i]);
                i++;
            }
            registro.setInd_nombre(hmAux);
            lista.add(registro);
        }
    }
    
      public String getColumnaCodNiv1(){
        if (listaNivelesConf.containsKey(ParamNivelConsul.UNO.getNivel())) {
            return Utilidades.obtenerMensaje("consultas.ambito.comun.columna.codigo.nivel", listaNivelesConf.get(ParamNivelConsul.UNO.getNivel()));
        }
        return "";
    }
    
    public String getColumnaCodNiv2(){
        if (listaNivelesConf.containsKey(ParamNivelConsul.DOS.getNivel())) {
            return Utilidades.obtenerMensaje("consultas.ambito.comun.columna.codigo.nivel", listaNivelesConf.get(ParamNivelConsul.DOS.getNivel()));
        }
        return "";
    }
    
    public String getColumnaCodNiv3(){
        if (listaNivelesConf.containsKey(ParamNivelConsul.TRES.getNivel())) {
            return Utilidades.obtenerMensaje("consultas.ambito.comun.columna.codigo.nivel", listaNivelesConf.get(ParamNivelConsul.TRES.getNivel()));
        }
        return "";
    }
    
    public String getColumnaCodNiv4(){
        if (listaNivelesConf.containsKey(ParamNivelConsul.CUATRO.getNivel())) {
            return Utilidades.obtenerMensaje("consultas.ambito.comun.columna.codigo.nivel", listaNivelesConf.get(ParamNivelConsul.CUATRO.getNivel()));
        }
        return "";
    }
    
    public String getColumnaCodNiv5() {
        if (listaNivelesConf.containsKey(ParamNivelConsul.CINCO.getNivel())) {
            return Utilidades.obtenerMensaje("consultas.ambito.comun.columna.codigo.nivel", listaNivelesConf.get(ParamNivelConsul.CINCO.getNivel()));
        }
        return "";
    }
    

    public void resetearColumnas() {
        columsTotal = new ArrayList<String>();
        
        //
        if (listaNivelesConf.containsKey(ParamNivelConsul.UNO.getNivel())) {
            columsTotal.add(getColumnaCodNiv1());
            columsTotal.add(listaNivelesConf.get(ParamNivelConsul.UNO.getNivel()));
        }
        
        
        if (listaNivelesConf.containsKey(ParamNivelConsul.DOS.getNivel())) {
            columsTotal.add(getColumnaCodNiv2());
            columsTotal.add(listaNivelesConf.get(ParamNivelConsul.DOS.getNivel()));
        }
        
        if (listaNivelesConf.containsKey(ParamNivelConsul.TRES.getNivel())) {
            columsTotal.add(getColumnaCodNiv3());
            columsTotal.add(listaNivelesConf.get(ParamNivelConsul.TRES.getNivel()));
        }
        
        if (listaNivelesConf.containsKey(ParamNivelConsul.CUATRO.getNivel())) {
            columsTotal.add(getColumnaCodNiv4());
            columsTotal.add(listaNivelesConf.get(ParamNivelConsul.CUATRO.getNivel()));
        }
        
        if (listaNivelesConf.containsKey(ParamNivelConsul.CINCO.getNivel())) {
            columsTotal.add(getColumnaCodNiv5());
            columsTotal.add(listaNivelesConf.get(ParamNivelConsul.CINCO.getNivel()));
        }
 
        //
        
        columsTotal.add(Utilidades.obtenerMensaje("aplicacion.general.codEst"));
        columsTotal.add(Utilidades.obtenerMensaje("aplicacion.general.nomEst"));
        columsTotal.add(Utilidades.obtenerMensaje("aplicacion.general.codSede"));
        columsTotal.add(Utilidades.obtenerMensaje("aplicacion.general.nomSede"));
        columsTotal.add(Utilidades.obtenerMensaje("aplicacion.general.codPredio"));
        columsTotal.add(Utilidades.obtenerMensaje("aplicacion.general.nomPredio"));
        colums = new ArrayList<String>();
        colums.add(Utilidades.obtenerMensaje("aplicacion.general.codPredio"));
        colums.add(Utilidades.obtenerMensaje("aplicacion.general.nomPredio"));


        if (Ambitos.A_RN.toString().equals(filtro.getAmbitoPredominante())
                || Ambitos.A_RN.toString().equals(filtro.getAmbitoCondicional())) {
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.RN1"));
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.RN2"));
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.RN3"));
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.RN4"));
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.RN5"));
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.RN6"));
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.RN7"));
            colums.add(Utilidades.obtenerMensaje("consulta.dinamica.column.RN7"));
        }
        if (Ambitos.A_RA.toString().equals(filtro.getAmbitoPredominante())
                || Ambitos.A_RA.toString().equals(filtro.getAmbitoCondicional())) {
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.RA1"));
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.RA2"));
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.RA3"));
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.RA4"));
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.RA5"));
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.RA6"));
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.RA7"));
            colums.add(Utilidades.obtenerMensaje("consulta.dinamica.column.RA7"));
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.RA8"));
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.RA9"));
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.RA10"));
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.RA11"));
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.RA12"));
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.RA13"));
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.RA14"));
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.RA15"));
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.RA16"));
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.RA17"));
        }
        if (Ambitos.A_ACC.toString().equals(filtro.getAmbitoPredominante())
                || Ambitos.A_ACC.toString().equals(filtro.getAmbitoCondicional())) {
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.ACC1"));
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.ACC2"));
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.ACC3"));
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.ACC4"));
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.ACC5"));
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.ACC6"));
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.ACC7"));
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.ACC8"));
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.ACC9"));
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.ACC10"));
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.ACC11"));
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.ACC12"));
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.ACC13"));
            colums.add(Utilidades.obtenerMensaje("consulta.dinamica.column.ACC13"));
        }
        if (Ambitos.A_OFE.toString().equals(filtro.getAmbitoPredominante())
                || Ambitos.A_OFE.toString().equals(filtro.getAmbitoCondicional())) {
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.OFE1"));
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.OFE2"));
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.OFE3"));
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.OFE4"));
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.OFE5"));
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.OFE6"));
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.OFE7"));
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.OFE8"));
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.OFE9"));
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.OFE10"));
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.OFE11"));
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.OFE12"));
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.OFE13"));
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.OFE14"));
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.OFE15"));
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.OFE16"));
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.OFE17"));
            colums.add(Utilidades.obtenerMensaje("consulta.dinamica.column.OFE17"));
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.OFE18"));
            colums.add(Utilidades.obtenerMensaje("consulta.dinamica.column.OFE18"));
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.OFE19"));
            colums.add(Utilidades.obtenerMensaje("consulta.dinamica.column.OFE19"));
        }
        if (Ambitos.A_PROP.toString().equals(filtro.getAmbitoPredominante())
                || Ambitos.A_PROP.toString().equals(filtro.getAmbitoCondicional())) {
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.PROP1"));
            colums.add(Utilidades.obtenerMensaje("consulta.dinamica.column.PROP1"));
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.PROP2"));
            colums.add(Utilidades.obtenerMensaje("consulta.dinamica.column.PROP2"));
        }
        if (Ambitos.A_CV.toString().equals(filtro.getAmbitoPredominante())
                || Ambitos.A_CV.toString().equals(filtro.getAmbitoCondicional())) {
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.VUL1"));
            colums.add(Utilidades.obtenerMensaje("consulta.dinamica.column.VUL1"));
        }
        if (Ambitos.A_ACCD.toString().equals(filtro.getAmbitoPredominante())
                || Ambitos.A_ACCD.toString().equals(filtro.getAmbitoCondicional())) {
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.ACCD1"));
            colums.add(Utilidades.obtenerMensaje("consulta.dinamica.column.ACCD1"));
        }
        if (Ambitos.A_AMB.toString().equals(filtro.getAmbitoPredominante())
                || Ambitos.A_AMB.toString().equals(filtro.getAmbitoCondicional())) {
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.AMB1"));
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.AMB2"));
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.AMB3"));
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.AMB4"));
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.AMB5"));
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.AMB6"));
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.AMB7"));
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.AMB8"));
            colums.add(Utilidades.obtenerMensaje("consulta.dinamica.column.AMB8"));
        }
        if (Ambitos.A_CON.toString().equals(filtro.getAmbitoPredominante())
                || Ambitos.A_CON.toString().equals(filtro.getAmbitoCondicional())) {
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.CON1"));
            colums.add(Utilidades.obtenerMensaje("consulta.dinamica.column.CON1"));
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.CON2"));
            colums.add(Utilidades.obtenerMensaje("consulta.dinamica.column.CON2"));
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.CON3"));
            colums.add(Utilidades.obtenerMensaje("consulta.dinamica.column.CON3"));
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.CON4"));
            colums.add(Utilidades.obtenerMensaje("consulta.dinamica.column.CON4"));
        }
        if (Ambitos.A_SOS.toString().equals(filtro.getAmbitoPredominante())
                || Ambitos.A_SOS.toString().equals(filtro.getAmbitoCondicional())) {
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.SOS1"));
            colums.add(Utilidades.obtenerMensaje("consulta.dinamica.column.SOS1"));
        }
        if (Ambitos.A_SEG.toString().equals(filtro.getAmbitoPredominante())
                || Ambitos.A_SEG.toString().equals(filtro.getAmbitoCondicional())) {
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.SEG1"));
            colums.add(Utilidades.obtenerMensaje("consulta.dinamica.column.SEG1"));
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.SEG2"));
            colums.add(Utilidades.obtenerMensaje("consulta.dinamica.column.SEG2"));
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.SEG3"));
            colums.add(Utilidades.obtenerMensaje("consulta.dinamica.column.SEG3"));
        }
        if (Ambitos.A_EEE.toString().equals(filtro.getAmbitoPredominante())
                || Ambitos.A_EEE.toString().equals(filtro.getAmbitoCondicional())) {
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.EEE1"));
            colums.add(Utilidades.obtenerMensaje("consulta.dinamica.column.EEE1"));
        }
        if (Ambitos.A_SP.toString().equals(filtro.getAmbitoPredominante())
                || Ambitos.A_SP.toString().equals(filtro.getAmbitoCondicional())) {
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.SP1"));
            colums.add(Utilidades.obtenerMensaje("consulta.dinamica.column.SP1"));
            columsTotal.add(Utilidades.obtenerMensaje("consulta.dinamica.column.SP2"));
            colums.add(Utilidades.obtenerMensaje("consulta.dinamica.column.SP2"));
        }
    }

    public boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public String formatear(String str) {
        if (isNumeric(str)) {

            if (str.lastIndexOf(".") + 3 < str.length()) {
                return str.substring(0, str.lastIndexOf(".") + 3);
            } else {
                return str;
            }

        } else {
            return str;
        }
    }

    public class Registro {

        private String idInstrumento;
        private Long idDigInstrumento;
        private HashMap<String, Object> ind_nombre;

        public String getIdInstrumento() {
            return idInstrumento;
        }

        public void setIdInstrumento(String idInstrumento) {
            this.idInstrumento = idInstrumento;
        }

        public Long getIdDigInstrumento() {
            return idDigInstrumento;
        }

        public void setIdDigInstrumento(Long idDigInstrumento) {
            this.idDigInstrumento = idDigInstrumento;
        }

        public HashMap<String, Object> getInd_nombre() {
            return ind_nombre;
        }

        public void setInd_nombre(HashMap<String, Object> ind_nombre) {
            this.ind_nombre = ind_nombre;
        }
    }

    public List<String> getColums() {
        return colums;
    }

    public void setColums(List<String> colums) {
        this.colums = colums;
    }

    public List<String> getColumsTotal() {
        return columsTotal;
    }

    public void setColumsTotal(List<String> columsTotal) {
        this.columsTotal = columsTotal;
    }

    public List<Registro> getLista() {
        return lista;
    }

    public void setLista(List<Registro> lista) {
        this.lista = lista;
    }

    public List<String> getListaPregGerenciales() {
        return listaPregGerenciales;
    }

    public void setListaPregGerenciales(List<String> listaPregGerenciales) {
        this.listaPregGerenciales = listaPregGerenciales;
    }

    public String getTipPredLabel1() {
        return tipPredLabel1;
    }

    public boolean isMostrarTipPred1() {
        return mostrarTipPred1;
    }

    public String getTipPredLabel2() {
        return tipPredLabel2;
    }

    public boolean isMostrarTipPred2() {
        return mostrarTipPred2;
    }

    public String getTipPredLabel3() {
        return tipPredLabel3;
    }

    public boolean isMostrarTipPred3() {
        return mostrarTipPred3;
    }

    public String getTipPredLabel4() {
        return tipPredLabel4;
    }

    public boolean isMostrarTipPred4() {
        return mostrarTipPred4;
    }

    public String getTipPredLabel5() {
        return tipPredLabel5;
    }

    public String getTipCondLabel1() {
        return tipCondLabel1;
    }

    public boolean isMostrarTipCond1() {
        return mostrarTipCond1;
    }

    public String getTipCondLabel2() {
        return tipCondLabel2;
    }

    public boolean isMostrarTipCond2() {
        return mostrarTipCond2;
    }

    public String getTipCondLabel3() {
        return tipCondLabel3;
    }

    public boolean isMostrarTipCond3() {
        return mostrarTipCond3;
    }

    public String getTipCondLabel4() {
        return tipCondLabel4;
    }

    public boolean isMostrarTipCond4() {
        return mostrarTipCond4;
    }

    public String getTipologiaPred5() {
        return tipologiaPred5;
    }

    public void setTipologiaPred5(String tipologiaPred5) {
        this.tipologiaPred5 = tipologiaPred5;
    }

    public String getTipologiaCond1() {
        return tipologiaCond1;
    }

    public void setTipologiaCond1(String tipologiaCond1) {
        this.tipologiaCond1 = tipologiaCond1;
    }

    public boolean isMostrarCampoSino1() {
        return mostrarCampoSino1;
    }

    public boolean isMostrarCampoSinoPred1() {
        return mostrarCampoSinoPred1;
    }

    public boolean isMostrarCampoSinoPred2() {
        return mostrarCampoSinoPred2;
    }

    public boolean isMostrarCampoSinoPred3() {
        return mostrarCampoSinoPred3;
    }

    public boolean isMostrarCampoSino2() {
        return mostrarCampoSino2;
    }

    public boolean isMostrarCampoSino3() {
        return mostrarCampoSino3;
    }

    public boolean isMostrarCampoSPPred1() {
        return mostrarCampoSPPred1;
    }

    public boolean isMostrarCampoSPPred2() {
        return mostrarCampoSPPred2;
    }

    public boolean isMostrarCampoSPCond() {
        return mostrarCampoSPCond;
    }

    public List<SiNo> getListaSiNoPred() {
        return Arrays.asList(SiNo.values());
    }

    public List<Vulnerabilidad> getListaVulnera() {
        return Arrays.asList(Vulnerabilidad.values());
    }

    public List<String> getListaAmbientes() {
        List<String> ambientes = new ArrayList<String>();
        ambientes.add(Utilidades.obtenerMensaje("consulta.dinamica.listaAmbiente.aula"));
        ambientes.add(Utilidades.obtenerMensaje("consulta.dinamica.listaAmbiente.expansionesRecreativas"));
        ambientes.add(Utilidades.obtenerMensaje("consulta.dinamica.listaAmbiente.serviciosSanitarios"));
        return ambientes;
    }

    public List<ServiciosPublicos> getListaSerciciosPublicos() {
        List<ServiciosPublicos> list = Arrays.asList(ServiciosPublicos.values());
        //list.add(ServiciosPublicos.DSA);
        return list;
    }

    public String getAmbitoPredominanteTexto() {
        String texto = "";
        for (String amb : listaAmbPredominantes) {
            if (amb.equals(filtro.getAmbitoPredominante())) {
                texto = Utilidades.obtenerMensaje("bd.ins_ambitos."+amb);
                break;
            }
        }
        return texto;
    }

    public String getAmbitoCondicionalTexto() {
        String texto = "";
        for (String amb : listaAmbCondicionales) {
            if (amb.equals(filtro.getAmbitoCondicional())) {
                texto = Utilidades.obtenerMensaje("bd.ins_ambitos."+amb);
                break;
            }
        }
        return texto;
    }

    protected List<String> seleccionarTodoTipo(List<TipologiaValorNombre> listaTipos) {
        List<String> l = new ArrayList<String>();
        for (TipologiaValorNombre tipologia : listaTipos) {
            l.add(tipologia.getTipValor().getCodigo());
        }
        return l;
    }

    public List<TipologiaValorNombre> getListaRiesgosNaturales() {
        return listaRiesgosNaturales;
    }

    public void setListaRiesgosNaturales(List<TipologiaValorNombre> listaRiesgosNaturales) {
        this.listaRiesgosNaturales = listaRiesgosNaturales;
    }

    public List<String> getSeleccionRiesgoNatural() {
        return seleccionRiesgoNatural;
    }

    public void setSeleccionRiesgoNatural(List<String> seleccionRiesgoNatural) {
        this.seleccionRiesgoNatural = seleccionRiesgoNatural;
    }

    public FiltroConsultaDinamica getFiltro() {
        return filtro;
    }

    public void setFiltro(FiltroConsultaDinamica filtro) {
        this.filtro = filtro;
    }

    public List<CalificacionCondicion> getListaCondiciones() {
        if (listaCondiciones == null) {
            listaCondiciones = Arrays.asList(CalificacionCondicion.values());
        }
        return listaCondiciones;
    }

    public CalificacionCondicion getSeleccionCondicionRiesgoNatural() {
        return seleccionCondicionRiesgoNatural;
    }

    public void setSeleccionCondicionRiesgoNatural(CalificacionCondicion seleccionCondicionRiesgoNatural) {
        this.seleccionCondicionRiesgoNatural = seleccionCondicionRiesgoNatural;
    }

    public void allSelectRiesgo(ToggleSelectEvent event) {
        String id = event.getComponent().getId();
        Boolean sw = event.isSelected();
        allSelectTipoRiesgo.put(id, sw);
    }

    public List<TipologiaValorNombre> getListaRiesgosAntropicos() {
        return listaRiesgosAntropicos;
    }

    public void setListaRiesgosAntropicos(List<TipologiaValorNombre> listaRiesgosAntropicos) {
        this.listaRiesgosAntropicos = listaRiesgosAntropicos;
    }

    public List<String> getSeleccionRiesgoAntropico() {
        return seleccionRiesgoAntropico;
    }

    public void setSeleccionRiesgoAntropico(List<String> seleccionRiesgoAntropico) {
        this.seleccionRiesgoAntropico = seleccionRiesgoAntropico;
    }

    public CalificacionCondicion getSeleccionCondicionRiesgoAntropico() {
        return seleccionCondicionRiesgoAntropico;
    }

    public void setSeleccionCondicionRiesgoAntropico(CalificacionCondicion seleccionCondicionRiesgoAntropico) {
        this.seleccionCondicionRiesgoAntropico = seleccionCondicionRiesgoAntropico;
    }

    public List<TipologiaValorNombre> getListaTiposPropietarios() {
        return listaTiposPropietarios;
    }

    public void setListaTiposPropietarios(List<TipologiaValorNombre> listaTiposPropietarios) {
        this.listaTiposPropietarios = listaTiposPropietarios;
    }

    public List<String> getSeleccionTipoPropietario() {
        return seleccionTipoPropietario;
    }

    public void setSeleccionTipoPropietario(List<String> seleccionTipoPropietario) {
        this.seleccionTipoPropietario = seleccionTipoPropietario;
    }

    public boolean isMostrarTipoRNPred() {
        return mostrarTipoRNPred;
    }

    public boolean isMostrarTipoRNCond() {
        return mostrarTipoRNCond;
    }

    public boolean isMostrarTipoRAPred() {
        return mostrarTipoRAPred;
    }

    public boolean isMostrarTipoRACond() {
        return mostrarTipoRACond;
    }

    public boolean isMostrarTipoPropPred() {
        return mostrarTipoPropPred;
    }

    public boolean isMostrarTipoPropCond() {
        return mostrarTipoPropCond;
    }

    public boolean isMostrarUnidadFuncionalPred() {
        return mostrarUnidadFuncionalPred;
    }

    public boolean isMostrarUnidadFuncionalCond() {
        return mostrarUnidadFuncionalCond;
    }

    public boolean isMostrarPredConfort() {
        return mostrarPredConfort;
    }

    public boolean isMostrarCondConfort() {
        return mostrarCondConfort;
    }

    public boolean isMostrarCondAnalisisConsumoAgua() {
        return mostrarCondAnalisisConsumoAgua;
    }

    public boolean isMostrarPredAnalisisConsumoAgua() {
        return mostrarPredAnalisisConsumoAgua;
    }

    public boolean isMostrarCondicionSPPred() {
        return mostrarCondicionSPPred;
    }

    public boolean isMostrarCondicionSPCond() {
        return mostrarCondicionSPCond;
    }

    public boolean isMostrarCampoVulnera() {
        return mostrarCampoVulnera;
    }

    public boolean isMostrarCampoVulneraPred() {
        return mostrarCampoVulneraPred;
    }

    public int getCantRegistros() {
        return cantRegistros;
    }

    public void setCantRegistros(int cantRegistros) {
        this.cantRegistros = cantRegistros;
    }
    
    public ConsultaAmbito getConsultaAmbito() {
        return consultaAmbito;
    }

    public void setConsultaAmbito(ConsultaAmbito consultaAmbito) {
        this.consultaAmbito = consultaAmbito;
    }

    public List<String> getListaAmbPredominantes() {
        return listaAmbPredominantes;
    }

    public void setListaAmbPredominantes(List<String> listaAmbPredominantes) {
        this.listaAmbPredominantes = listaAmbPredominantes;
    }

    public List<String> getListaAmbCondicionales() {
        return listaAmbCondicionales;
    }

    public void setListaAmbCondicionales(List<String> listaAmbCondicionales) {
        this.listaAmbCondicionales = listaAmbCondicionales;
    }
}
