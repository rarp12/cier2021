/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.consulta.ambito.riesgo;

import co.stackpointer.cier.modelo.entidad.instrumento.TipologiaValorNombre;
import co.stackpointer.cier.modelo.filtro.ambito.FiltroAmbitoRiesgoAntropico;
import co.stackpointer.cier.modelo.tipo.CalificacionCondicion;
import co.stackpointer.cier.modelo.tipo.TipologiaCodigo;
import co.stackpointer.cier.modelo.utilidad.UtilCadena;
import co.stackpointer.cier.modelo.utilidad.UtilConsulta;
import co.stackpointer.cier.vista.Utilidades;
import co.stackpointer.cier.vistacontrolador.consulta.ambito.ConsultaAmbito;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author user
 */
@ManagedBean(name = "riesgoAntropico")
@ViewScoped
public class RiesgoAntropicoBean extends ConsultaAmbito {

    @ManagedProperty(value = "#{consultaAmbito}")
    private ConsultaAmbito consultaAmbito;
    private List<String> seleccionRiesgo;
    private CalificacionCondicion seleccionCondicionRiesgo;
    private TipologiaValorNombre seleccionRetiro;    
    private List<TipologiaValorNombre> listaRiesgos;
    private List<TipologiaValorNombre> listaRetiros;
    private List<Registro> lista;
    private FiltroAmbitoRiesgoAntropico filtro = new FiltroAmbitoRiesgoAntropico();
    private TotalConsulta totalConsulta = new TotalConsulta();    
  
    final String condAccesibilidad = Utilidades.obtenerMensaje("consulta.dinamica.column.ACC13");
    final String RA1 = Utilidades.obtenerMensaje("consulta.dinamica.column.RA1");
    final String RA2 = Utilidades.obtenerMensaje("consulta.dinamica.column.RA2");
    final String RA3 = Utilidades.obtenerMensaje("consulta.dinamica.column.RA3");
    final String RA4 = Utilidades.obtenerMensaje("consulta.dinamica.column.RA4");
    final String RA5 = Utilidades.obtenerMensaje("consulta.dinamica.column.RA5");
    final String RA6 = Utilidades.obtenerMensaje("consulta.dinamica.column.RA6");
    final String RA7 = Utilidades.obtenerMensaje("consulta.dinamica.column.RA7");
    final String RA8 = Utilidades.obtenerMensaje("consulta.dinamica.column.RA8");
    final String RA9 = Utilidades.obtenerMensaje("consulta.dinamica.column.RA9");
    final String RA10 = Utilidades.obtenerMensaje("consulta.dinamica.column.RA10");
    final String RA11 = Utilidades.obtenerMensaje("consulta.dinamica.column.RA11");
    final String RA12 = Utilidades.obtenerMensaje("consulta.dinamica.column.RA12");
    final String RA13 = Utilidades.obtenerMensaje("consulta.dinamica.column.RA13");
    final String RA14 = Utilidades.obtenerMensaje("consulta.dinamica.column.RA14");
    final String RA15 = Utilidades.obtenerMensaje("consulta.dinamica.column.RA15");
    final String RA16 = Utilidades.obtenerMensaje("consulta.dinamica.column.RA16");
    final String RA17 = Utilidades.obtenerMensaje("consulta.dinamica.column.RA17");
    final String numPrediosConRiesgos = Utilidades.obtenerMensaje("consultas.ambito.column.numPrediosConRiesgos");
    final String prediosTotal = Utilidades.obtenerMensaje("consultas.ambito.column.prediosTotal");
    final String proporcionCondicion = Utilidades.obtenerMensaje("consultas.ambito.column.proporcionCondicion");
    final String poblacionAfectada = Utilidades.obtenerMensaje("consultas.ambito.column.poblacionAfectada");
    final String poblacionTotal = Utilidades.obtenerMensaje("consultas.ambito.column.poblacionTotal");
    
    @PostConstruct
    public void inicializar() {
        //super.init();
        listaRiesgos = facParam.consultarValoresTipologia(TipologiaCodigo.RIESGO_ANTROPICO.getValor(),getUsuarioSesion().getUsuario().getIdioma().getId());
        listaRetiros = facParam.consultarValoresTipologia(TipologiaCodigo.AISLAMIENTO_EDIF.getValor(),getUsuarioSesion().getUsuario().getIdioma().getId());
        lista = new ArrayList<Registro>();
        cargarCamposOpcionales();
    }
    
    private void cargarCamposOpcionales() {
        listaCamposOpcionales.clear();
        this.cargarCamposOpcionalesComunes();
        if (isMostrarColDirPredio()) {
            listaCamposOpcionales.add(columnaDirPredio);
        }
        if (isMostrarColPropiedadPredio()) {
            listaCamposOpcionales.add(columnaPropPredio);
        }
        if (isMostrarColSector()) {
            listaCamposOpcionales.add(columnaSector);
        }
        if (isMostrarColZona()) {
            listaCamposOpcionales.add(columnaZona);
        }
        if (isMostrarColClima()) {
            listaCamposOpcionales.add(columnaClima);
        }
        if (isMostrarColEtnias()) {
            listaCamposOpcionales.add(columnaEtnias);
        }
        if(isMostrarColCondAccesibilidad()){
            listaCamposOpcionales.add(condAccesibilidad);
        }
        if(isMostrarColTipoRiesgo1()){
            listaCamposOpcionales.add(RA1);
            selccionCamposOpcionales.add(RA1);
        }
        if(isMostrarColCondiconRiesgo1()){
            listaCamposOpcionales.add(RA2);
            selccionCamposOpcionales.add(RA2);
        }
        if(isMostrarColTipoRiesgo2()){
            listaCamposOpcionales.add(RA3);
            selccionCamposOpcionales.add(RA3);
        }
        if(isMostrarColCondiconRiesgo2()){
            listaCamposOpcionales.add(RA4);
            selccionCamposOpcionales.add(RA4);
        }
        if(isMostrarColTipoRiesgo3()){
            listaCamposOpcionales.add(RA5);
            selccionCamposOpcionales.add(RA5);
        }
        if(isMostrarColCondiconRiesgo3()){
            listaCamposOpcionales.add(RA6);
            selccionCamposOpcionales.add(RA6);
        }
        if(isMostrarColCondiconRiesgo()){
            listaCamposOpcionales.add(RA7);
            selccionCamposOpcionales.add(RA7);
        }
        if(isMostrarColTipoRetiro1()){
            listaCamposOpcionales.add(RA8);
            selccionCamposOpcionales.add(RA8);
        }
        if(isMostrarColMtsRetiro1()){
            listaCamposOpcionales.add(RA9);
            selccionCamposOpcionales.add(RA9);
        }
        if(isMostrarColTipoRetiro2()){
            listaCamposOpcionales.add(RA10);
        }
        if(isMostrarColMtsRetiro2()){
            listaCamposOpcionales.add(RA11);
        }
        if(isMostrarColTipoRetiro3()){
            listaCamposOpcionales.add(RA12);
        }
        if(isMostrarColMtsRetiro3()){
            listaCamposOpcionales.add(RA13);
        }
        if(isMostrarColTipoRetiro4()){
            listaCamposOpcionales.add(RA14);
        }
        if(isMostrarColMtsRetiro4()){
            listaCamposOpcionales.add(RA15);
        }
        if(isMostrarColTipoRetiro5()){
            listaCamposOpcionales.add(RA16);
        }
        if(isMostrarColMtsRetiro5()){
            listaCamposOpcionales.add(RA17);
        }
        if(isMostrarColNumPrediosRiesgo()){
            listaCamposOpcionales.add(numPrediosConRiesgos);
            selccionCamposOpcionales.add(numPrediosConRiesgos);
        }
        if(isMostrarColTotalPredios()){
            listaCamposOpcionales.add(prediosTotal);
            selccionCamposOpcionales.add(prediosTotal);
        }
        if(isMostrarColPorcionCondicion()){
            listaCamposOpcionales.add(proporcionCondicion);
            selccionCamposOpcionales.add(proporcionCondicion);
        }
        if(isMostrarColPoblacionAfectada()){
            listaCamposOpcionales.add(poblacionAfectada);
            selccionCamposOpcionales.add(poblacionAfectada);
        }
        if(isMostrarColPoblacionTotal()){
            listaCamposOpcionales.add(poblacionTotal);
            selccionCamposOpcionales.add(poblacionTotal);
        }
    }
    
    @Override
    public void resetearFiltros() {
        consultaAmbito.resetearNiveles();
        this.resetearListasEstSedesPredios();
        this.seleccionSector = null;
        this.seleccionZona = null;        
        this.seleccionCondicionRiesgo = null;
        this.seleccionRiesgo = null;
        this.seleccionRetiro = null;        
        this.lista = new ArrayList<Registro>();
        this.selccionCamposOpcionales.clear();
        this.cargarCamposOpcionales();
        consultaAmbito.nivelDPAOfAccesoUsuario();
        this.totalConsulta.inicializar();
    }

    /**
     * Cuando se cambie el filtro de periodo este reseteara todos los campos parametricos de la consulta a excepcion
     * de los que sonparte de la DPA
     */
    public void resetearFiltrosPeriodo() {
        //consultaAmbito.resetearNiveles();
        this.resetearListasEstSedesPredios();
        this.seleccionSector = null;
        this.seleccionZona = null;        
        this.seleccionCondicionRiesgo = null;
        this.seleccionRiesgo = null;
        this.seleccionRetiro = null;        
        this.lista = new ArrayList<Registro>();
        this.selccionCamposOpcionales.clear();
        this.cargarCamposOpcionales();
        //consultaAmbito.nivelDPAOfAccesoUsuario();
        this.totalConsulta.inicializar();
    }
    
    public void consultar() {
        filtro.inicializar();
        filtro.setPeriodo(periodo);
        filtro.setIdioma(getUsuarioSesion().getUsuario().getIdioma().getId());
        
        filtro.setAgrupamiento(seleccionAgrupamiento);
        filtro.setIdNivel0(consultaAmbito.getSeleccionNivel0() != null ? consultaAmbito.getSeleccionNivel0().getId() : null);
        filtro.setIdNivel1(consultaAmbito.getSeleccionNivel1() != null ? consultaAmbito.getSeleccionNivel1().getId() : null);
        filtro.setIdNivel2(consultaAmbito.getSeleccionNivel2() != null ? consultaAmbito.getSeleccionNivel2().getId() : null);
        filtro.setIdNivel3(consultaAmbito.getSeleccionNivel3() != null ? consultaAmbito.getSeleccionNivel3().getId() : null);
        filtro.setIdNivel4(consultaAmbito.getSeleccionNivel4() != null ? consultaAmbito.getSeleccionNivel4().getId() : null);
        filtro.setIdNivel5(consultaAmbito.getSeleccionNivel5() != null ? consultaAmbito.getSeleccionNivel5().getId() : null);

        filtro.setIdEstablecimiento(seleccionEst != null ? seleccionEst.getId() : null);
        filtro.setIdSede(seleccionSede != null ? seleccionSede.getId() : null);
        filtro.setIdPredio(seleccionPredio != null ? seleccionPredio.getId() : null);

        // filtros basicos
        filtro.setCodZona(seleccionZona != null ? seleccionZona.getTipValor().getCodigo() : null);
        filtro.setCodSector(seleccionSector != null ? seleccionSector.getTipValor().getCodigo() : null);
        //otros filtros
        filtro.setCodRiesgo(elementosSeleccionados(seleccionRiesgo)!= null ? elementosSeleccionados(seleccionRiesgo) : null);
        filtro.setCodCondicionRiesgo(seleccionCondicionRiesgo != null ? seleccionCondicionRiesgo.getValor() : null);
        filtro.setCodRetiro(seleccionRetiro != null ? seleccionRetiro.getTipValor().getCodigo() : null);

        List<Object[]> resultados = facAmbito.consultarRiesgosAntropicos(filtro);
        lista = new ArrayList<Registro>(resultados.size());
        
           if(lista.size()<1)
            {
                mensajeTablaVacia = Utilidades.obtenerMensaje("aplicacion.general.noExistenRegistros");
            }
        totalConsulta.inicializar();
        for (Object[] fila : resultados) {
            Registro registro = new Registro();
            int i = 0;
            if (filtro.isAgrupamientoPredio()) {
                registro.setCodNivel1(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                registro.setCodNivel2(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNivel2(UtilConsulta.stringValueOf(fila[i++]));
                registro.setCodNivel3(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNivel3(UtilConsulta.stringValueOf(fila[i++]));
                registro.setCodNivel4(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNivel4(UtilConsulta.stringValueOf(fila[i++]));
                registro.setCodNivel5(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNivel5(UtilConsulta.stringValueOf(fila[i++]));
                registro.setCodEstablecimiento(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNomEstablecimiento(UtilConsulta.stringValueOf(fila[i++]));
                registro.setCodSede(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNomSede(UtilConsulta.stringValueOf(fila[i++]));
                registro.setCodPredio(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNomPredio(UtilConsulta.stringValueOf(fila[i++]));
                // indicadores
                registro.setTipoRiesgo1(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]),getUsuarioSesion().getUsuario().getIdioma().getId()));
                registro.setCondiconRiesgo1(UtilConsulta.stringValueOf(fila[i++]));
                registro.setTipoRiesgo2(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]),getUsuarioSesion().getUsuario().getIdioma().getId()));
                registro.setCondiconRiesgo2(UtilConsulta.stringValueOf(fila[i++]));
                registro.setTipoRiesgo3(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]),getUsuarioSesion().getUsuario().getIdioma().getId()));
                registro.setCondiconRiesgo3(UtilConsulta.stringValueOf(fila[i++]));
                registro.setCondiconRiesgo(Utilidades.obtenerMensaje(UtilConsulta.stringValueOf(fila[i++])));
                registro.setTipoRetiro1(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]),getUsuarioSesion().getUsuario().getIdioma().getId()));
                registro.setMtsRetiro1(UtilConsulta.longValueOf(fila[i++]));
                registro.setTipoRetiro2(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]),getUsuarioSesion().getUsuario().getIdioma().getId()));
                registro.setMtsRetiro2(UtilConsulta.longValueOf(fila[i++]));
                registro.setTipoRetiro3(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]),getUsuarioSesion().getUsuario().getIdioma().getId()));
                registro.setMtsRetiro3(UtilConsulta.longValueOf(fila[i++]));
                registro.setTipoRetiro4(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]),getUsuarioSesion().getUsuario().getIdioma().getId()));
                registro.setMtsRetiro4(UtilConsulta.longValueOf(fila[i++]));
                registro.setTipoRetiro5(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]),getUsuarioSesion().getUsuario().getIdioma().getId()));
                registro.setMtsRetiro5(UtilConsulta.longValueOf(fila[i++]));
                //otros
                registro.setDirPredio(UtilConsulta.stringValueOf(fila[i++]));
                registro.setZona(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]),getUsuarioSesion().getUsuario().getIdioma().getId()));
                registro.setSector(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]),getUsuarioSesion().getUsuario().getIdioma().getId()));
                registro.setClima(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]),getUsuarioSesion().getUsuario().getIdioma().getId()));
                registro.setEtnias(verificarEtnias(UtilConsulta.stringValueOf(fila[i++])));
                registro.setPropiedadPredio(UtilConsulta.stringValueOf(fila[i++]));
                registro.setCondAccesibilidad(UtilConsulta.stringValueOf(fila[i++]));
                registro.setPoblacionAfectada(UtilConsulta.longValueOf(fila[i++]));
                registro.setPoblacionTotal(UtilConsulta.longValueOf(fila[i++]));
                //totales
                totalConsulta.incrementNumTipRA1(registro.getTipoRiesgo1());
                totalConsulta.incrementNumTipTipRA2(registro.getTipoRiesgo2());
                totalConsulta.incrementNumTipTipRA3(registro.getTipoRiesgo3());
                totalConsulta.incrementNumTipRetiro1(registro.getTipoRetiro1());
                totalConsulta.incrementNumTipRetiro2(registro.getTipoRetiro2());
                totalConsulta.incrementNumTipRetiro3(registro.getTipoRetiro3());
                totalConsulta.incrementNumTipRetiro4(registro.getTipoRetiro4());
                totalConsulta.incrementNumTipRetiro5(registro.getTipoRetiro5());       
            } else {
                if (filtro.isAgrupamientoNivel0()) {
                    registro.setNivel0(UtilConsulta.stringValueOf(fila[i++]));
                } else if (filtro.isAgrupamientoNivel1()) {
                    registro.setCodNivel1(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                } else if (filtro.isAgrupamientoNivel2()) {
                    registro.setCodNivel1(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCodNivel2(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel2(UtilConsulta.stringValueOf(fila[i++]));
                } else if (filtro.isAgrupamientoNivel3()) {
                    registro.setCodNivel1(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCodNivel2(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel2(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCodNivel3(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel3(UtilConsulta.stringValueOf(fila[i++]));
                } else if (filtro.isAgrupamientoNivel4()) {
                    registro.setCodNivel1(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCodNivel2(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel2(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCodNivel3(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel3(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCodNivel4(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel4(UtilConsulta.stringValueOf(fila[i++]));
                } else if (filtro.isAgrupamientoNivel5()) {
                    registro.setCodNivel1(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCodNivel2(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel2(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCodNivel3(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel3(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCodNivel4(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel4(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCodNivel5(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel5(UtilConsulta.stringValueOf(fila[i++]));
                } else if (filtro.isAgrupamientoEstablecimiento()) {
                    registro.setCodNivel1(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCodNivel2(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel2(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCodNivel3(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel3(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCodNivel4(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel4(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCodNivel5(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel5(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCodEstablecimiento(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNomEstablecimiento(UtilConsulta.stringValueOf(fila[i++]));
                } else if (filtro.isAgrupamientoSede()) {
                    registro.setCodNivel1(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCodNivel2(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel2(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCodNivel3(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel3(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCodNivel4(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel4(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCodNivel5(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel5(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCodEstablecimiento(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNomEstablecimiento(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCodSede(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNomSede(UtilConsulta.stringValueOf(fila[i++]));
                }
                // indicadores
                registro.setNumPrediosRiesgo(UtilConsulta.longValueOf(fila[i++]));
                registro.setTotalPredios(UtilConsulta.longValueOf(fila[i++]));
                registro.setPorcionCondicion(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setPoblacionAfectada(UtilConsulta.longValueOf(fila[i++]));
                registro.setPoblacionTotal(UtilConsulta.longValueOf(fila[i++]));
                // totales
                totalConsulta.addNumPrediosConRA(registro.getNumPrediosRiesgo());
                totalConsulta.addNumPredios(registro.getTotalPredios());
            }
            totalConsulta.addNumPoblacionAfectada(registro.getPoblacionAfectada());
            totalConsulta.addNumPoblacionTotal(registro.getPoblacionTotal());
            lista.add(registro);
        }
    }

    @Override
    public void cambioAgrupamiento() {        
        // columnas
        selccionCamposOpcionales.clear();
        this.cargarCamposOpcionales();
        // resultados
        lista.clear();
        totalConsulta.inicializar();
    }

    public CalificacionCondicion getSeleccionCondicionRiesgo() {
        return seleccionCondicionRiesgo;
    }

    public void setSeleccionCondicionRiesgo(CalificacionCondicion seleccionCondicionRiesgo) {
        this.seleccionCondicionRiesgo = seleccionCondicionRiesgo;
    }   

    public List<String> getSeleccionRiesgo() {
        return seleccionRiesgo;
    }
    
    public void setSeleccionRiesgo(List<String> seleccionRiesgo) {
        this.seleccionRiesgo = seleccionRiesgo;
    }

    public TipologiaValorNombre getSeleccionRetiro() {
        return seleccionRetiro;
    }

    public void setSeleccionRetiro(TipologiaValorNombre seleccionRetiro) {
        this.seleccionRetiro = seleccionRetiro;
    }

    public List<TipologiaValorNombre> getListaRiesgos() {
        return listaRiesgos;
    }

    public void setListaRiesgos(List<TipologiaValorNombre> listaRiesgos) {
        this.listaRiesgos = listaRiesgos;
    }

    public List<TipologiaValorNombre> getListaRetiros() {
        return listaRetiros;
    }

    public void setListaRetiros(List<TipologiaValorNombre> listaRetiros) {
        this.listaRetiros = listaRetiros;
    }

    public List<Registro> getLista() {
        return lista;
    }

    public TotalConsulta getTotalConsulta() {
        return totalConsulta;
    }

    public class Registro {

        private String nivel0;
        private String nivel1;
        private String nivel2;
        private String nivel3;
        private String nivel4;
        private String nivel5;
        private String codNivel1;
        private String codNivel2;
        private String codNivel3;
        private String codNivel4;
        private String codNivel5;
        private String codEstablecimiento;
        private String nomEstablecimiento;
        private String codSede;
        private String nomSede;
        private String codPredio;
        private String nomPredio;
        private String tipoRiesgo1;
        private String condiconRiesgo1;
        private String tipoRiesgo2;
        private String condiconRiesgo2;
        private String tipoRiesgo3;
        private String condiconRiesgo3;
        private String condiconRiesgo;
        private String tipoRetiro1;
        private Long mtsRetiro1;
        private String tipoRetiro2;
        private Long mtsRetiro2;
        private String tipoRetiro3;
        private Long mtsRetiro3;
        private String tipoRetiro4;
        private Long mtsRetiro4;
        private String tipoRetiro5;
        private Long mtsRetiro5;
        private String dirPredio;
        private String zona;
        private String sector;
        private String clima;
        private String etnias;
        private String propiedadPredio;
        private String condAccesibilidad;
        private Long numPrediosRiesgo;
        private Long totalPredios;
        private Double porcionCondicion;
        private Long poblacionAfectada;
        private Long poblacionTotal;

        public String getNivel0() {
            return nivel0;
        }

        public void setNivel0(String nivel0) {
            this.nivel0 = nivel0;
        }

        public String getCodNivel1() {
            return codNivel1;
        }

        public void setCodNivel1(String codNivel1) {
            this.codNivel1 = codNivel1;
        }

        public String getCodNivel2() {
            return codNivel2;
        }

        public void setCodNivel2(String codNivel2) {
            this.codNivel2 = codNivel2;
        }

        public String getCodNivel3() {
            return codNivel3;
        }

        public void setCodNivel3(String codNivel3) {
            this.codNivel3 = codNivel3;
        }

        public String getCodNivel4() {
            return codNivel4;
        }

        public void setCodNivel4(String codNivel4) {
            this.codNivel4 = codNivel4;
        }

        public String getCodNivel5() {
            return codNivel5;
        }

        public void setCodNivel5(String codNivel5) {
            this.codNivel5 = codNivel5;
        }

        public String getNivel1() {
            return nivel1;
        }

        public void setNivel1(String nivel1) {
            this.nivel1 = nivel1;
        }

        public String getNivel2() {
            return nivel2;
        }

        public void setNivel2(String nivel2) {
            this.nivel2 = nivel2;
        }

        public String getNivel3() {
            return nivel3;
        }

        public void setNivel3(String nivel3) {
            this.nivel3 = nivel3;
        }

        public String getNivel4() {
            return nivel4;
        }

        public void setNivel4(String nivel4) {
            this.nivel4 = nivel4;
        }

        public String getNivel5() {
            return nivel5;
        }

        public void setNivel5(String nivel5) {
            this.nivel5 = nivel5;
        }

        public String getCodEstablecimiento() {
            return codEstablecimiento;
        }

        public void setCodEstablecimiento(String codEstablecimiento) {
            this.codEstablecimiento = codEstablecimiento;
        }

        public String getNomEstablecimiento() {
            return nomEstablecimiento;
        }

        public void setNomEstablecimiento(String nomEstablecimiento) {
            this.nomEstablecimiento = nomEstablecimiento;
        }

        public String getCodSede() {
            return codSede;
        }

        public void setCodSede(String codSede) {
            this.codSede = codSede;
        }

        public String getNomSede() {
            return nomSede;
        }

        public void setNomSede(String nomSede) {
            this.nomSede = nomSede;
        }

        public String getCodPredio() {
            return codPredio;
        }

        public void setCodPredio(String codPredio) {
            this.codPredio = codPredio;
        }

        public String getNomPredio() {
            return nomPredio;
        }

        public void setNomPredio(String nomPredio) {
            this.nomPredio = nomPredio;
        }

        public String getTipoRiesgo1() {
            return tipoRiesgo1;
        }

        public void setTipoRiesgo1(String tipoRiesgo1) {
            this.tipoRiesgo1 = tipoRiesgo1;
        }

        public String getCondiconRiesgo1() {
            return condiconRiesgo1;
        }

        public void setCondiconRiesgo1(String condiconRiesgo1) {
            this.condiconRiesgo1 = condiconRiesgo1;
        }

        public String getTipoRiesgo2() {
            return tipoRiesgo2;
        }

        public void setTipoRiesgo2(String tipoRiesgo2) {
            this.tipoRiesgo2 = tipoRiesgo2;
        }

        public String getCondiconRiesgo2() {
            return condiconRiesgo2;
        }

        public void setCondiconRiesgo2(String condiconRiesgo2) {
            this.condiconRiesgo2 = condiconRiesgo2;
        }

        public String getTipoRiesgo3() {
            return tipoRiesgo3;
        }

        public void setTipoRiesgo3(String tipoRiesgo3) {
            this.tipoRiesgo3 = tipoRiesgo3;
        }

        public String getCondiconRiesgo3() {
            return condiconRiesgo3;
        }

        public void setCondiconRiesgo3(String condiconRiesgo3) {
            this.condiconRiesgo3 = condiconRiesgo3;
        }

        public String getCondiconRiesgo() {
            return condiconRiesgo;
        }

        public void setCondiconRiesgo(String condiconRiesgo) {
            this.condiconRiesgo = condiconRiesgo;
        }

        public String getTipoRetiro1() {
            return tipoRetiro1;
        }

        public void setTipoRetiro1(String tipoRetiro1) {
            this.tipoRetiro1 = tipoRetiro1;
        }

        public Long getMtsRetiro1() {
            return mtsRetiro1;
        }

        public void setMtsRetiro1(Long mtsRetiro1) {
            this.mtsRetiro1 = mtsRetiro1;
        }

        public String getTipoRetiro2() {
            return tipoRetiro2;
        }

        public void setTipoRetiro2(String tipoRetiro2) {
            this.tipoRetiro2 = tipoRetiro2;
        }

        public Long getMtsRetiro2() {
            return mtsRetiro2;
        }

        public void setMtsRetiro2(Long mtsRetiro2) {
            this.mtsRetiro2 = mtsRetiro2;
        }

        public String getTipoRetiro3() {
            return tipoRetiro3;
        }

        public void setTipoRetiro3(String tipoRetiro3) {
            this.tipoRetiro3 = tipoRetiro3;
        }

        public Long getMtsRetiro3() {
            return mtsRetiro3;
        }

        public void setMtsRetiro3(Long mtsRetiro3) {
            this.mtsRetiro3 = mtsRetiro3;
        }

        public String getTipoRetiro4() {
            return tipoRetiro4;
        }

        public void setTipoRetiro4(String tipoRetiro4) {
            this.tipoRetiro4 = tipoRetiro4;
        }

        public Long getMtsRetiro4() {
            return mtsRetiro4;
        }

        public void setMtsRetiro4(Long mtsRetiro4) {
            this.mtsRetiro4 = mtsRetiro4;
        }

        public String getTipoRetiro5() {
            return tipoRetiro5;
        }

        public void setTipoRetiro5(String tipoRetiro5) {
            this.tipoRetiro5 = tipoRetiro5;
        }

        public Long getMtsRetiro5() {
            return mtsRetiro5;
        }

        public void setMtsRetiro5(Long mtsRetiro5) {
            this.mtsRetiro5 = mtsRetiro5;
        }

        public String getDirPredio() {
            return dirPredio;
        }

        public void setDirPredio(String dirPredio) {
            this.dirPredio = dirPredio;
        }

        public String getZona() {
            return zona;
        }

        public void setZona(String zona) {
            this.zona = zona;
        }

        public String getSector() {
            return sector;
        }

        public void setSector(String sector) {
            this.sector = sector;
        }

        public String getClima() {
            return clima;
        }

        public void setClima(String clima) {
            this.clima = clima;
        }

        public String getEtnias() {
            return etnias;
        }

        public void setEtnias(String etnias) {
            this.etnias = etnias;
        }

        public String getPropiedadPredio() {
            return propiedadPredio;
        }

        public void setPropiedadPredio(String propiedadPredio) {
            this.propiedadPredio = propiedadPredio;
            if (propiedadPredio != null) {
                if (propiedadPredio.equalsIgnoreCase("sql.ind.general.si")) {
                    this.propiedadPredio = Utilidades.obtenerMensaje(propiedadPredio);
                } else {
                    this.propiedadPredio = Utilidades.obtenerMensaje(propiedadPredio);
                }
            }
        }

        public String getCondAccesibilidad() {
            return condAccesibilidad;
        }

        public void setCondAccesibilidad(String condAccesibilidad) {
            this.condAccesibilidad = condAccesibilidad;
            if (condAccesibilidad != null) {
                if (condAccesibilidad.equalsIgnoreCase("sql.ind.general.cumple")) {
                    this.condAccesibilidad = Utilidades.obtenerMensaje(condAccesibilidad);
                } else {
                    this.condAccesibilidad = Utilidades.obtenerMensaje(condAccesibilidad);
                }
            }
        }

        public Long getNumPrediosRiesgo() {
            return numPrediosRiesgo;
        }

        public void setNumPrediosRiesgo(Long numPrediosRiesgo) {
            this.numPrediosRiesgo = numPrediosRiesgo;
        }

        public Long getTotalPredios() {
            return totalPredios;
        }

        public void setTotalPredios(Long totalPredios) {
            this.totalPredios = totalPredios;
        }

        public Double getPorcionCondicion() {
            return porcionCondicion;
        }

        public void setPorcionCondicion(Double porcionCondicion) {
            this.porcionCondicion = porcionCondicion;
        }

        public Long getPoblacionAfectada() {
            return poblacionAfectada;
        }

        public void setPoblacionAfectada(Long poblacionAfectada) {
            this.poblacionAfectada = poblacionAfectada;
        }
        
        public Long getPoblacionTotal() {
            return poblacionTotal;
        }

        public void setPoblacionTotal(Long poblacionTotal) {
            this.poblacionTotal = poblacionTotal;
        }
    }

    public class TotalConsulta {

        private Long numTipRA1 = 0L;
        private Long numTipRA2 = 0L;
        private Long numTipRA3 = 0L;
        private Long numTipRetiro1 = 0L;
        private Long numTipRetiro2 = 0L;
        private Long numTipRetiro3 = 0L;
        private Long numTipRetiro4 = 0L;
        private Long numTipRetiro5 = 0L;
        private Long numPrediosConRA = 0L;
        private Long numPredios = 0L;
        private Long numPoblacionAfectada = 0L;
        private Long numPoblacionTotal = 0L;

        public void inicializar() {
            numTipRA1 = 0L;
            numTipRA2 = 0L;
            numTipRA3 = 0L;
            numTipRetiro1 = 0L;
            numTipRetiro2 = 0L;
            numTipRetiro3 = 0L;
            numTipRetiro4 = 0L;
            numTipRetiro5 = 0L;
            numPrediosConRA = 0L;
            numPredios = 0L;
            numPoblacionAfectada = 0L;
            numPoblacionTotal = 0L;
        }

        public Long getNumTipRA1() {
            return numTipRA1;
        }

        public Long getNumTipRA2() {
            return numTipRA2;
        }

        public Long getNumTipRA3() {
            return numTipRA3;
        }

        public Long getNumTipRetiro1() {
            return numTipRetiro1;
        }

        public Long getNumTipRetiro2() {
            return numTipRetiro2;
        }

        public Long getNumTipRetiro3() {
            return numTipRetiro3;
        }

        public Long getNumTipRetiro4() {
            return numTipRetiro4;
        }

        public Long getNumTipRetiro5() {
            return numTipRetiro5;
        }

        public Long getNumPrediosConRA() {
            return numPrediosConRA;
        }

        public Long getNumPredios() {
            return numPredios;
        }

        public Long getNumPoblacionAfectada() {
            return numPoblacionAfectada;
        }
        
        public Long getNumPoblacionTotal() {
            return numPoblacionTotal;
        }

        public void incrementNumTipRA1(String tipo) {
            if (!UtilCadena.isNullOVacio(tipo)) {
                this.numTipRA1++;
            }
        }

        public void incrementNumTipTipRA2(String tipo) {
            if (!UtilCadena.isNullOVacio(tipo)) {
                this.numTipRA2++;
            }
        }

        public void incrementNumTipTipRA3(String tipo) {
            if (!UtilCadena.isNullOVacio(tipo)) {
                this.numTipRA3++;
            }
        }

        public void incrementNumTipRetiro1(String tipo) {
            if (!UtilCadena.isNullOVacio(tipo)) {
                this.numTipRetiro1++;
            }
        }

        public void incrementNumTipRetiro2(String tipo) {
            if (!UtilCadena.isNullOVacio(tipo)) {
                this.numTipRetiro2++;
            }
        }

        public void incrementNumTipRetiro3(String tipo) {
            if (!UtilCadena.isNullOVacio(tipo)) {
                this.numTipRetiro3++;
            }
        }

        public void incrementNumTipRetiro4(String tipo) {
            if (!UtilCadena.isNullOVacio(tipo)) {
                this.numTipRetiro4++;
            }
        }

        public void incrementNumTipRetiro5(String tipo) {
            if (!UtilCadena.isNullOVacio(tipo)) {
                this.numTipRetiro5++;
            }
        }

        public void addNumPrediosConRA(Long valor) {
            if (valor != null) {
                this.numPrediosConRA += valor;
            }
        }

        public void addNumPredios(Long valor) {
            if (valor != null) {
                this.numPredios += valor;
            }
        }

        public void addNumPoblacionAfectada(Long valor) {
            if (valor != null) {
                this.numPoblacionAfectada += valor;
            }
        }
        
        public void addNumPoblacionTotal(Long valor) {
            if (valor != null) {
                this.numPoblacionTotal += valor;
            }
        }
    }

    // mostrar columnas
    public boolean isMostrarColTipoRiesgo1() {
        return isAgrupamientoPredio();
    }

    public boolean isMostrarColCondiconRiesgo1() {
        return isAgrupamientoPredio();
    }

    public boolean isMostrarColTipoRiesgo2() {
        return isAgrupamientoPredio();
    }

    public boolean isMostrarColCondiconRiesgo2() {
        return isAgrupamientoPredio();
    }

    public boolean isMostrarColTipoRiesgo3() {
        return isAgrupamientoPredio();
    }

    public boolean isMostrarColCondiconRiesgo3() {
        return isAgrupamientoPredio();
    }

    public boolean isMostrarColCondiconRiesgo() {
        return isAgrupamientoPredio();
    }

    public boolean isMostrarColTipoRetiro1() {
        return isAgrupamientoPredio();
    }

    public boolean isMostrarColMtsRetiro1() {
        return isAgrupamientoPredio();
    }

    public boolean isMostrarColTipoRetiro2() {
        return isAgrupamientoPredio();
    }

    public boolean isMostrarColMtsRetiro2() {
        return isAgrupamientoPredio();
    }

    public boolean isMostrarColTipoRetiro3() {
        return isAgrupamientoPredio();
    }

    public boolean isMostrarColMtsRetiro3() {
        return isAgrupamientoPredio();
    }

    public boolean isMostrarColTipoRetiro4() {
        return isAgrupamientoPredio();
    }

    public boolean isMostrarColMtsRetiro4() {
        return isAgrupamientoPredio();
    }

    public boolean isMostrarColTipoRetiro5() {
        return isAgrupamientoPredio();
    }

    public boolean isMostrarColMtsRetiro5() {
        return isAgrupamientoPredio();
    }

    public boolean isMostrarColCondAccesibilidad() {
        return isAgrupamientoPredio();
    }

    public boolean isMostrarColNumPrediosRiesgo() {
        return !isAgrupamientoPredio();
    }

    public boolean isMostrarColTotalPredios() {
        return !isAgrupamientoPredio();
    }

    public boolean isMostrarColPorcionCondicion() {
        return !isAgrupamientoPredio();
    }

    public boolean isMostrarColPoblacionAfectada() {
        return true;
    }    
 
    public boolean isMostrarColPoblacionTotal() {
        return !isAgrupamientoPredio();
    } 
    
    public String getCondAccesibilidad() {
        return condAccesibilidad;
    }

    public String getRA1() {
        return RA1;
    }

    public String getRA2() {
        return RA2;
    }

    public String getRA3() {
        return RA3;
    }

    public String getRA4() {
        return RA4;
    }

    public String getRA5() {
        return RA5;
    }

    public String getRA6() {
        return RA6;
    }

    public String getRA7() {
        return RA7;
    }

    public String getRA8() {
        return RA8;
    }

    public String getRA9() {
        return RA9;
    }

    public String getRA10() {
        return RA10;
    }

    public String getRA11() {
        return RA11;
    }

    public String getRA12() {
        return RA12;
    }

    public String getRA13() {
        return RA13;
    }

    public String getRA14() {
        return RA14;
    }

    public String getRA15() {
        return RA15;
    }

    public String getRA16() {
        return RA16;
    }

    public String getRA17() {
        return RA17;
    }

    public String getNumPrediosConRiesgos() {
        return numPrediosConRiesgos;
    }

    public String getPrediosTotal() {
        return prediosTotal;
    }

    public String getProporcionCondicion() {
        return proporcionCondicion;
    }

    public String getPoblacionAfectada() {
        return poblacionAfectada;
    }
    
    public String getPoblacionTotal() {
        return poblacionTotal;
    }
    
      public boolean isCondicionCritica(String condicion)
    {
         if(condicion.trim().toUpperCase().equals("CRITICO")||condicion.trim().toUpperCase().equals("CRÌTICO"))
           {  
            return true;
           }else 
           {
            return false;
           }
    }  
    
    public boolean isCondicionNoCritica(String condicion)
    {
         if(condicion.trim().toUpperCase().equals("NO CRITICO")||condicion.trim().toUpperCase().equals("NO CRÌTICO"))
           {  
            return true;
           }else 
           {
            return false;
           }
    } 
    
    public ConsultaAmbito getConsultaAmbito() {
        return consultaAmbito;
    }

    public void setConsultaAmbito(ConsultaAmbito consultaAmbito) {
        this.consultaAmbito = consultaAmbito;
    }
    
}
