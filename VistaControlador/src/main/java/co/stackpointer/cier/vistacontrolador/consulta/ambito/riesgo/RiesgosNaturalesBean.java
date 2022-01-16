/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.consulta.ambito.riesgo;

import co.stackpointer.cier.modelo.EntidadesConsultas.ConsultaAmbitoRiesgosNaturales;
import co.stackpointer.cier.modelo.entidad.instrumento.TipologiaValorNombre;
import co.stackpointer.cier.modelo.filtro.ambito.FiltroAmbitoRiesgoNatural;
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
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author user
 */
@ManagedBean(name = "riesgosNaturales")
@ViewScoped
public class RiesgosNaturalesBean extends ConsultaAmbito {
    
    @ManagedProperty(value = "#{consultaAmbito}")
    private ConsultaAmbito consultaAmbito;
    private List<String> seleccionRiesgo;
    private CalificacionCondicion seleccionCondicion;
    private List<TipologiaValorNombre> listaRiesgos;
    private List<Registro> lista;
    private FiltroAmbitoRiesgoNatural filtro = new FiltroAmbitoRiesgoNatural();
    private TotalConsulta totalConsulta = new TotalConsulta();
    private LazyDataModel<Registro> lazyLista;
    
    final String condAccesibilidad = Utilidades.obtenerMensaje("consulta.dinamica.column.ACC13");
    final String RN1 = Utilidades.obtenerMensaje("consulta.dinamica.column.RN1");
    final String RN2 = Utilidades.obtenerMensaje("consulta.dinamica.column.RN2");
    final String RN3 = Utilidades.obtenerMensaje("consulta.dinamica.column.RN3");
    final String RN4 = Utilidades.obtenerMensaje("consulta.dinamica.column.RN4");
    final String RN5 = Utilidades.obtenerMensaje("consulta.dinamica.column.RN5");
    final String RN6 = Utilidades.obtenerMensaje("consulta.dinamica.column.RN6");
    final String RN7 = Utilidades.obtenerMensaje("consulta.dinamica.column.RN7");
    final String numPrediosConRiesgos = Utilidades.obtenerMensaje("consultas.ambito.column.numPrediosConRiesgos");
    final String prediosTotal = Utilidades.obtenerMensaje("consultas.ambito.column.prediosTotal");
    final String proporcionCondicion = Utilidades.obtenerMensaje("consultas.ambito.column.proporcionCondicion");
    final String poblacionAfectada = Utilidades.obtenerMensaje("consultas.ambito.column.poblacionAfectada");
    final String poblacionTotal = Utilidades.obtenerMensaje("consultas.ambito.column.poblacionTotal");

    @PostConstruct
    public void inicializar() {
        //super.init();
        listaRiesgos = facParam.consultarValoresTipologia(TipologiaCodigo.RIESGO_NATURAL.getValor(),getUsuarioSesion().getUsuario().getIdioma().getId());
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
               
        
        if (isMostrarColCondAccesibilidad()) {
            listaCamposOpcionales.add(condAccesibilidad);
        }
        if (isMostrarColTipoRiesgo1()) {
            listaCamposOpcionales.add(RN1);
            selccionCamposOpcionales.add(RN1);
        }
        if (isMostrarColCondiconRiesgo1()) {
            listaCamposOpcionales.add(RN2);
            selccionCamposOpcionales.add(RN2);
        }
        if (isMostrarColTipoRiesgo2()) {
            listaCamposOpcionales.add(RN3);
            selccionCamposOpcionales.add(RN3);
        }
        if (isMostrarColCondiconRiesgo2()) {
            listaCamposOpcionales.add(RN4);
            selccionCamposOpcionales.add(RN4);
        }
        if (isMostrarColTipoRiesgo3()) {
            listaCamposOpcionales.add(RN5);
            selccionCamposOpcionales.add(RN5);
        }
        if (isMostrarColCondiconRiesgo3()) {
            listaCamposOpcionales.add(RN6);
            selccionCamposOpcionales.add(RN6);
        }
        if (isMostrarColCondiconRiesgo()) {
            listaCamposOpcionales.add(RN7);
            selccionCamposOpcionales.add(RN7);
        }
        if (isMostrarColNumPrediosRiesgo()) {
            listaCamposOpcionales.add(numPrediosConRiesgos);
            selccionCamposOpcionales.add(numPrediosConRiesgos);
        }
        if (isMostrarColTotalPredios()) {
            listaCamposOpcionales.add(prediosTotal);
            selccionCamposOpcionales.add(prediosTotal);
        }
        if (isMostrarColPorcionCondicion()) {
            listaCamposOpcionales.add(proporcionCondicion);
            selccionCamposOpcionales.add(proporcionCondicion);
        }
        if (isMostrarColPoblacionAfectada()) {
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
        this.seleccionCondicion = null;
        this.seleccionRiesgo = null;        
        this.lista = new ArrayList<Registro>();
        this.selccionCamposOpcionales.clear();
        this.cargarCamposOpcionales();
        consultaAmbito.nivelDPAOfAccesoUsuario();
        this.totalConsulta.inicializar();
        this.lazyLista = null;
    }

    public void consultar() {
        filtro.inicializar();
        filtro.setPeriodo(periodo);
        filtro.setIdioma(getUsuarioSesion().getUsuario().getIdioma().getId());
        this.lista = new ArrayList<Registro>();

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
        filtro.setCodRiesgo(elementosSeleccionados(seleccionRiesgo) != null ? elementosSeleccionados(seleccionRiesgo) : null);
        filtro.setCodCondicion(seleccionCondicion != null ? seleccionCondicion.getValor() : null);

        List<Object[]> resultados = facAmbito.consultarRiesgosNaturales(filtro);
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
                registro.setCodigoNivel1(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                registro.setCodigoNivel2(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNivel2(UtilConsulta.stringValueOf(fila[i++]));
                registro.setCodigoNivel3(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNivel3(UtilConsulta.stringValueOf(fila[i++]));
                registro.setCodigoNivel4(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNivel4(UtilConsulta.stringValueOf(fila[i++]));
                registro.setCodigoNivel5(UtilConsulta.stringValueOf(fila[i++]));
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
                totalConsulta.incrementNumTipRN1(registro.getTipoRiesgo1());
                totalConsulta.incrementNumTipTipRN2(registro.getTipoRiesgo2());
                totalConsulta.incrementNumTipTipRN3(registro.getTipoRiesgo3());                
            } else {
                if (filtro.isAgrupamientoNivel0()) {
                    registro.setNivel0(UtilConsulta.stringValueOf(fila[i++]));
                } else if (filtro.isAgrupamientoNivel1()) {
                    registro.setCodigoNivel1(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                } else if (filtro.isAgrupamientoNivel2()) {
                    registro.setCodigoNivel1(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCodigoNivel2(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel2(UtilConsulta.stringValueOf(fila[i++]));
                } else if (filtro.isAgrupamientoNivel3()) {
                    registro.setCodigoNivel1(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCodigoNivel2(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel2(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCodigoNivel3(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel3(UtilConsulta.stringValueOf(fila[i++]));
                } else if (filtro.isAgrupamientoNivel4()) {
                    registro.setCodigoNivel1(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCodigoNivel2(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel2(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCodigoNivel3(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel3(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCodigoNivel4(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel4(UtilConsulta.stringValueOf(fila[i++]));
                } else if (filtro.isAgrupamientoNivel5()) {
                    registro.setCodigoNivel1(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCodigoNivel2(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel2(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCodigoNivel3(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel3(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCodigoNivel4(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel4(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCodigoNivel5(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel5(UtilConsulta.stringValueOf(fila[i++]));
                } else if (filtro.isAgrupamientoEstablecimiento()) {
                    registro.setCodigoNivel1(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCodigoNivel2(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel2(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCodigoNivel3(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel3(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCodigoNivel4(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel4(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCodigoNivel5(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel5(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCodEstablecimiento(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNomEstablecimiento(UtilConsulta.stringValueOf(fila[i++]));
                } else if (filtro.isAgrupamientoSede()) {
                    registro.setCodigoNivel1(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCodigoNivel2(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel2(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCodigoNivel3(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel3(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCodigoNivel4(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel4(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCodigoNivel5(UtilConsulta.stringValueOf(fila[i++]));
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
                totalConsulta.addNumPrediosConRN(registro.getNumPrediosRiesgo());
                totalConsulta.addNumPredios(registro.getTotalPredios());                
            }
            totalConsulta.addNumPoblacionAfectada(registro.getPoblacionAfectada());
            totalConsulta.addNumPoblacionTotal(registro.getPoblacionTotal());
            lista.add(registro);
        }
        lazyLista = new LazyRiegosNaturalesDataModel(lista);
    }

    @Override
    public void cambioAgrupamiento() {
        selccionCamposOpcionales.clear();
        this.cargarCamposOpcionales();
        lista.clear();
        totalConsulta.inicializar();
    }

    public CalificacionCondicion getSeleccionCondicion() {
        return seleccionCondicion;
    }

    public void setSeleccionCondicion(CalificacionCondicion seleccionCondicion) {
        this.seleccionCondicion = seleccionCondicion;
    }

    public List<String> getSeleccionRiesgo() {
        return seleccionRiesgo;
    }

    public void setSeleccionRiesgo(List<String> seleccionRiesgo) {
        this.seleccionRiesgo = seleccionRiesgo;
    }

    public List<TipologiaValorNombre> getListaRiesgos() {
        return listaRiesgos;
    }

    public void setListaRiesgos(List<TipologiaValorNombre> listaRiesgos) {
        this.listaRiesgos = listaRiesgos;
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
        private String codigoNivel1;
        private String codigoNivel2;
        private String codigoNivel3;
        private String codigoNivel4;
        private String codigoNivel5;
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

        public String getCodigoNivel1() {
            return codigoNivel1;
        }

        public void setCodigoNivel1(String codigoNivel1) {
            this.codigoNivel1 = codigoNivel1;
        }

        public String getCodigoNivel2() {
            return codigoNivel2;
        }

        public void setCodigoNivel2(String codigoNivel2) {
            this.codigoNivel2 = codigoNivel2;
        }

        public String getCodigoNivel3() {
            return codigoNivel3;
        }

        public void setCodigoNivel3(String codigoNivel3) {
            this.codigoNivel3 = codigoNivel3;
        }

        public String getCodigoNivel4() {
            return codigoNivel4;
        }

        public void setCodigoNivel4(String codigoNivel4) {
            this.codigoNivel4 = codigoNivel4;
        }

        public String getCodigoNivel5() {
            return codigoNivel5;
        }

        public void setCodigoNivel5(String codigoNivel5) {
            this.codigoNivel5 = codigoNivel5;
        }
    }

    public class TotalConsulta {

        private Long numTipRN1 = 0L;
        private Long numTipRN2 = 0L;
        private Long numTipRN3 = 0L;
        private Long numPrediosConRN = 0L;
        private Long numPredios = 0L;
        private Long numPoblacionAfectada = 0L;
        private Long numPoblacionTotal = 0L;

        public void inicializar() {
            numTipRN1 = 0L;
            numTipRN2 = 0L;
            numTipRN3 = 0L;
            numPrediosConRN = 0L;
            numPredios = 0L;
            numPoblacionAfectada = 0L;
            numPoblacionTotal = 0L;
        }

        public Long getNumTipRN1() {
            return numTipRN1;
        }

        public Long getNumTipRN2() {
            return numTipRN2;
        }

        public Long getNumTipRN3() {
            return numTipRN3;
        }

        public Long getNumPrediosConRN() {
            return numPrediosConRN;
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
        
        public void incrementNumTipRN1(String tipo) {
            if (!UtilCadena.isNullOVacio(tipo)) {
                this.numTipRN1++;
            }
        }

        public void incrementNumTipTipRN2(String tipo) {
            if (!UtilCadena.isNullOVacio(tipo)) {
                this.numTipRN2++;
            }
        }

        public void incrementNumTipTipRN3(String tipo) {
            if (!UtilCadena.isNullOVacio(tipo)) {
                this.numTipRN3++;
            }
        }

        public void addNumPrediosConRN(Long valor) {
            if (valor != null) {
                this.numPrediosConRN += valor;
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

    public String getRN1() {
        return RN1;
    }

    public String getRN2() {
        return RN2;
    }

    public String getRN3() {
        return RN3;
    }

    public String getRN4() {
        return RN4;
    }

    public String getRN5() {
        return RN5;
    }

    public String getRN6() {
        return RN6;
    }

    public String getRN7() {
        return RN7;
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
    
    public boolean isCondicionCritica(String condicion) {
        if (condicion.trim().toUpperCase().equals("CRITICO") || condicion.trim().toUpperCase().equals("CRÌTICO")) {
            return true;
        } else {
            return false;
        }
    }
    
    public String condicionRiesgo(String condicion) {
        return (Utilidades.obtenerMensaje(UtilConsulta.stringValueOf(condicion)));
    }
    
    public boolean isCondicionNoCritica(String condicion) {
        if (condicion.trim().toUpperCase().equals("NO CRITICO") || condicion.trim().toUpperCase().equals("NO CRÌTICO")) {
            return true;
        } else {
            return false;
        }
    }

    public ConsultaAmbito getConsultaAmbito() {
        return consultaAmbito;
    }

    public void setConsultaAmbito(ConsultaAmbito consultaAmbito) {
        this.consultaAmbito = consultaAmbito;
    }

    public LazyDataModel<Registro> getLazyLista() {
        return lazyLista;
    }

    public void setLazyLista(LazyDataModel<Registro> lazyLista) {
        this.lazyLista = lazyLista;
    }
    
}