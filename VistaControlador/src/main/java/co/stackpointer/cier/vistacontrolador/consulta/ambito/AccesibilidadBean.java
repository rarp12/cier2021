/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.consulta.ambito;

import co.stackpointer.cier.modelo.entidad.instrumento.TipologiaValorNombre;
import co.stackpointer.cier.modelo.filtro.ambito.FiltroAmbitoAccesibilidad;
import co.stackpointer.cier.modelo.tipo.CalificacionCondicion;
import co.stackpointer.cier.modelo.tipo.TipologiaCodigo;
import co.stackpointer.cier.modelo.utilidad.UtilCadena;
import co.stackpointer.cier.modelo.utilidad.UtilConsulta;
import co.stackpointer.cier.vista.Utilidades;
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
@ManagedBean(name = "accesibilidad")
@ViewScoped
public class AccesibilidadBean extends ConsultaAmbito {

    @ManagedProperty(value = "#{consultaAmbito}")
    private ConsultaAmbito consultaAmbito;
    private TipologiaValorNombre seleccionMedioTransporte;
    private CalificacionCondicion seleccionCondicion; 
    private List<TipologiaValorNombre> listaMediosTransportes;
    private List<Registro> lista;
    private FiltroAmbitoAccesibilidad filtro = new FiltroAmbitoAccesibilidad();
    private TotalConsulta totalConsulta = new TotalConsulta();

  
    final String numAccesos = Utilidades.obtenerMensaje("consultas.ambito.column.numAccesos");
    final String vulnerabilidad = Utilidades.obtenerMensaje("consultas.ambito.column.controlVigilancia");
    final String ACC1 = Utilidades.obtenerMensaje("consulta.dinamica.column.ACC1");
    final String ACC2 = Utilidades.obtenerMensaje("consulta.dinamica.column.ACC2");
    final String ACC3 = Utilidades.obtenerMensaje("consulta.dinamica.column.ACC3");
    final String ACC4 = Utilidades.obtenerMensaje("consulta.dinamica.column.ACC4");
    final String ACC5 = Utilidades.obtenerMensaje("consulta.dinamica.column.ACC5");
    final String ACC6 = Utilidades.obtenerMensaje("consulta.dinamica.column.ACC6");
    final String ACC7 = Utilidades.obtenerMensaje("consulta.dinamica.column.ACC7");
    final String ACC8 = Utilidades.obtenerMensaje("consulta.dinamica.column.ACC8");
    final String ACC9 = Utilidades.obtenerMensaje("consulta.dinamica.column.ACC9");
    final String ACC10 = Utilidades.obtenerMensaje("consulta.dinamica.column.ACC10");
    final String ACC11 = Utilidades.obtenerMensaje("consulta.dinamica.column.ACC11");
    final String ACC12 = Utilidades.obtenerMensaje("consulta.dinamica.column.ACC12");
    final String ACC13 = Utilidades.obtenerMensaje("consulta.dinamica.column.ACC13");
    final String predSinBuenAcc = Utilidades.obtenerMensaje("consultas.ambito.column.predSinBuenAcc");
    final String prediosTotal = Utilidades.obtenerMensaje("consultas.ambito.column.prediosTotal");
    final String proporcionPredios = Utilidades.obtenerMensaje("consultas.ambito.column.proporcionPredios");
    final String poblacionAfectada = Utilidades.obtenerMensaje("consultas.ambito.column.poblacionAfectada");
    final String poblacionTotal = Utilidades.obtenerMensaje("consultas.ambito.column.poblacionTotal");

    
   
    @PostConstruct
    public void inicializar() {
        //super.init();        
        listaMediosTransportes = facParam.consultarValoresTipologia(TipologiaCodigo.MEDIO_TRANSPORTE.getValor(),getUsuarioSesion().getUsuario().getIdioma().getId());
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
       
        if(isMostrarColNumAcessos()){
            listaCamposOpcionales.add(numAccesos);            
        }        
        if(isMostrarColVulnerabilidad()){
            listaCamposOpcionales.add(vulnerabilidad);           
        }
        if(isMostrarColTipoAcceso1()){
            listaCamposOpcionales.add(ACC1);
            selccionCamposOpcionales.add(ACC1);
        }
        if(isMostrarColCondicionAcceso1()){
            listaCamposOpcionales.add(ACC2);
            selccionCamposOpcionales.add(ACC2);
        }
        if(isMostrarColTipoAcceso2()){
            listaCamposOpcionales.add(ACC3);
            selccionCamposOpcionales.add(ACC3);
        }
        if(isMostrarColCondicionAcceso2()){
            listaCamposOpcionales.add(ACC4);
            selccionCamposOpcionales.add(ACC4);
        }
        if(isMostrarColTipoAcceso3()){
            listaCamposOpcionales.add(ACC5);
            selccionCamposOpcionales.add(ACC5);
        }
        if(isMostrarColCondicionAcceso3()){
            listaCamposOpcionales.add(ACC6);
            selccionCamposOpcionales.add(ACC6);
        }
        if(isMostrarColTipoAcceso4()){
            listaCamposOpcionales.add(ACC7);
        }
        if(isMostrarColCondicionAcceso4()){
            listaCamposOpcionales.add(ACC8);
        }
        if(isMostrarColCondicionAcceso5()){
            listaCamposOpcionales.add(ACC9);
        }
        if(isMostrarColTipoAcceso5()){
            listaCamposOpcionales.add(ACC10);
        }
        if(isMostrarColNumMediosTransporte()){
            listaCamposOpcionales.add(ACC11);
            selccionCamposOpcionales.add(ACC11);
        }
        if(isMostrarColNumAccesosEn1o2()){
            listaCamposOpcionales.add(ACC12);
            selccionCamposOpcionales.add(ACC12);
        }
        if(isMostrarColCondicionAccesibilidad()){
            listaCamposOpcionales.add(ACC13);
            selccionCamposOpcionales.add(ACC13);
        }
        if(isMostrarColNumPrediosNingunAccesoBueno()){
            listaCamposOpcionales.add(predSinBuenAcc);
            selccionCamposOpcionales.add(predSinBuenAcc);
        }
        if(isMostrarColTotalPredios()){
            listaCamposOpcionales.add(prediosTotal);
            selccionCamposOpcionales.add(prediosTotal);
        }
        if(isMostrarColProporcion()){
            listaCamposOpcionales.add(proporcionPredios);
            selccionCamposOpcionales.add(proporcionPredios);
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
        this.seleccionCondicion = null;
        this.seleccionMedioTransporte = null;       
        consultaAmbito.nivelDPAOfAccesoUsuario();
        this.lista = new ArrayList<Registro>();
        this.selccionCamposOpcionales.clear();
        this.cargarCamposOpcionales();
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
        this.seleccionCondicion = null;
        this.seleccionMedioTransporte = null;       
        //consultaAmbito.nivelDPAOfAccesoUsuario();
        this.lista = new ArrayList<Registro>();
        this.selccionCamposOpcionales.clear();
        this.cargarCamposOpcionales();
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
        filtro.setCodMedioTransporte(seleccionMedioTransporte != null ? seleccionMedioTransporte.getTipValor().getCodigo() : null);
        filtro.setCodCondicion(seleccionCondicion != null ? seleccionCondicion.getValor() : null);
        
        List<Object[]> resultados = facAmbito.consultarAccesibilidad(filtro);
        lista = new ArrayList<Registro>(resultados.size());
         if(lista.size()<1)
        { 
            mensajeTablaVacia=Utilidades.obtenerMensaje("aplicacion.general.noExistenRegistros");
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
                registro.setTipoAcceso1(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]),getUsuarioSesion().getUsuario().getIdioma().getId()));
                registro.setCondiconAcceso1(UtilConsulta.stringValueOf(fila[i++]));
                registro.setTipoAcceso2(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]),getUsuarioSesion().getUsuario().getIdioma().getId()));
                registro.setCondiconAcceso2(UtilConsulta.stringValueOf(fila[i++]));
                registro.setTipoAcceso3(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]),getUsuarioSesion().getUsuario().getIdioma().getId()));
                registro.setCondiconAcceso3(UtilConsulta.stringValueOf(fila[i++]));
                registro.setTipoAcceso4(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]),getUsuarioSesion().getUsuario().getIdioma().getId()));
                registro.setCondiconAcceso4(UtilConsulta.stringValueOf(fila[i++]));
                registro.setTipoAcceso5(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]),getUsuarioSesion().getUsuario().getIdioma().getId()));
                registro.setCondiconAcceso5(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNumMediosTransporte(UtilConsulta.longValueOf(fila[i++]));
                registro.setNumAccesosEn1o2(UtilConsulta.longValueOf(fila[i++]));
                registro.setCondicionAccesibilidad(Utilidades.obtenerMensaje(UtilConsulta.stringValueOf(fila[i++])));
                //otros
                registro.setDirPredio(UtilConsulta.stringValueOf(fila[i++]));
                registro.setZona(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]),getUsuarioSesion().getUsuario().getIdioma().getId()));
                registro.setSector(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]),getUsuarioSesion().getUsuario().getIdioma().getId()));
                registro.setClima(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]),getUsuarioSesion().getUsuario().getIdioma().getId()));
                registro.setEtnias(verificarEtnias(UtilConsulta.stringValueOf(fila[i++])));
                registro.setPropiedadPredio(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNumAccesos(UtilConsulta.stringValueOf(fila[i++]));
                registro.setVulnerabilidad(UtilConsulta.stringValueOf(fila[i++]));   
                //totales
                totalConsulta.incrementNumTipAcc1(registro.getTipoAcceso1());
                totalConsulta.incrementNumTipAcc2(registro.getTipoAcceso2());
                totalConsulta.incrementNumTipAcc3(registro.getTipoAcceso3());
                totalConsulta.incrementNumTipAcc4(registro.getTipoAcceso4());
                totalConsulta.incrementNumTipAcc5(registro.getTipoAcceso5());
                totalConsulta.addNumMediosTransportes(registro.getNumMediosTransporte());
                totalConsulta.addNumAccesosEn1o2(registro.getNumAccesosEn1o2());
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
                registro.setNumPrediosNingunAccesoBueno(UtilConsulta.longValueOf(fila[i++]));
                registro.setTotalPredios(UtilConsulta.longValueOf(fila[i++]));
                registro.setProporcion(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setPoblacionAfectada(UtilConsulta.longValueOf(fila[i++]));
                registro.setPoblacionTotal(UtilConsulta.longValueOf(fila[i++]));
                // totales
                totalConsulta.addNumPrediosAccesosInadecuados(registro.getNumPrediosNingunAccesoBueno());
                totalConsulta.addNumPredios(registro.getTotalPredios());
                totalConsulta.addNumPoblacionAfectada(registro.getPoblacionAfectada());
                totalConsulta.addNumPoblacionTotal(registro.getPoblacionTotal());
            }
            lista.add(registro);
        }
    }   
    
    @Override
    public void cambioAgrupamiento(){
        // columnas
        selccionCamposOpcionales.clear();
        this.cargarCamposOpcionales();
        // resultados
        lista.clear();
        totalConsulta.inicializar();
    }  

    public CalificacionCondicion getSeleccionCondicion() {
        return seleccionCondicion;
    }

    public void setSeleccionCondicion(CalificacionCondicion seleccionCondicion) {
        this.seleccionCondicion = seleccionCondicion;
    }       

    public TipologiaValorNombre getSeleccionMedioTransporte() {
        return seleccionMedioTransporte;
    }

    public void setSeleccionMedioTransporte(TipologiaValorNombre seleccionMedioTransporte) {
        this.seleccionMedioTransporte = seleccionMedioTransporte;
    }
    
    public List<TipologiaValorNombre> getListaMediosTransportes() {
        return listaMediosTransportes;
    }

    public void setListaMediosTransportes(List<TipologiaValorNombre> listaMediosTransportes) {
        this.listaMediosTransportes = listaMediosTransportes;
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
        
        private String tipoAcceso1;
        private String condiconAcceso1;
        private String tipoAcceso2;
        private String condiconAcceso2;
        private String tipoAcceso3;
        private String condiconAcceso3;
        private String tipoAcceso4;
        private String condiconAcceso4; 
        private String tipoAcceso5;
        private String condiconAcceso5; 
        private Long numMediosTransporte;
        private Long numAccesosEn1o2; 
        private String condicionAccesibilidad; 
        
        private String dirPredio;
        private String zona;
        private String sector;
        private String clima;
        private String etnias;
        private String propiedadPredio;
        private String numAccesos;
        private String vulnerabilidad;        
        
        private Long numPrediosNingunAccesoBueno;
        private Long totalPredios;
        private Double proporcion;
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

        public String getTipoAcceso1() {
            return tipoAcceso1;
        }

        public void setTipoAcceso1(String tipoAcceso1) {
            this.tipoAcceso1 = tipoAcceso1;
        }

        public String getCondiconAcceso1() {
            return condiconAcceso1;
        }

        public void setCondiconAcceso1(String condiconAcceso1) {
            this.condiconAcceso1 = condiconAcceso1;
        }

        public String getTipoAcceso2() {
            return tipoAcceso2;
        }

        public void setTipoAcceso2(String tipoAcceso2) {
            this.tipoAcceso2 = tipoAcceso2;
        }

        public String getCondiconAcceso2() {
            return condiconAcceso2;
        }

        public void setCondiconAcceso2(String condiconAcceso2) {
            this.condiconAcceso2 = condiconAcceso2;
        }

        public String getTipoAcceso3() {
            return tipoAcceso3;
        }

        public void setTipoAcceso3(String tipoAcceso3) {
            this.tipoAcceso3 = tipoAcceso3;
        }

        public String getCondiconAcceso3() {
            return condiconAcceso3;
        }

        public String getTipoAcceso4() {
            return tipoAcceso4;
        }

        public void setTipoAcceso4(String tipoAcceso4) {
            this.tipoAcceso4 = tipoAcceso4;
        }

        public String getCondiconAcceso4() {
            return condiconAcceso4;
        }

        public void setCondiconAcceso4(String condiconAcceso4) {
            this.condiconAcceso4 = condiconAcceso4;
        }

        public String getTipoAcceso5() {
            return tipoAcceso5;
        }

        public void setTipoAcceso5(String tipoAcceso5) {
            this.tipoAcceso5 = tipoAcceso5;
        }

        public String getCondiconAcceso5() {
            return condiconAcceso5;
        }

        public void setCondiconAcceso5(String condiconAcceso5) {
            this.condiconAcceso5 = condiconAcceso5;
        }
        
        public void setCondiconAcceso3(String condiconAcceso3) {
            this.condiconAcceso3 = condiconAcceso3;
        }        

        public Long getNumMediosTransporte() {
            return numMediosTransporte;
        }

        public void setNumMediosTransporte(Long numMediosTransporte) {
            this.numMediosTransporte = numMediosTransporte;
        }

        public Long getNumAccesosEn1o2() {
            return numAccesosEn1o2;
        }

        public void setNumAccesosEn1o2(Long numAccesosEn1o2) {
            this.numAccesosEn1o2 = numAccesosEn1o2;
        }
        
        public String getCondicionAccesibilidad() {
            return condicionAccesibilidad;
        }

        public void setCondicionAccesibilidad(String condicionAccesibilidad) {
            this.condicionAccesibilidad = condicionAccesibilidad;
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

        public String getNumAccesos() {
            return numAccesos;
        }

        public void setNumAccesos(String numAccesos) {
            this.numAccesos = numAccesos;
        }

        public String getVulnerabilidad() {
            return vulnerabilidad;
        }

        public void setVulnerabilidad(String vulnerabilidad) {
            this.vulnerabilidad = vulnerabilidad;
            if (vulnerabilidad != null) {
                if (vulnerabilidad.equalsIgnoreCase("sql.ind.general.baja")) {
                    this.vulnerabilidad = Utilidades.obtenerMensaje(vulnerabilidad);
                } else {
                    this.vulnerabilidad = Utilidades.obtenerMensaje(vulnerabilidad);
                }
            }
        }

        public Long getNumPrediosNingunAccesoBueno() {
            return numPrediosNingunAccesoBueno;
        }

        public void setNumPrediosNingunAccesoBueno(Long numPrediosNingunAccesoBueno) {
            this.numPrediosNingunAccesoBueno = numPrediosNingunAccesoBueno;
        }

        public Long getTotalPredios() {
            return totalPredios;
        }

        public void setTotalPredios(Long totalPredios) {
            this.totalPredios = totalPredios;
        }

        public Double getProporcion() {
            return proporcion;
        }

        public void setProporcion(Double proporcion) {
            this.proporcion = proporcion;
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

        private Long numTipAcc1 = 0L;
        private Long numTipAcc2 = 0L;
        private Long numTipAcc3 = 0L;
        private Long numTipAcc4 = 0L;
        private Long numTipAcc5 = 0L;
        private Long numMediosTransportes = 0L;
        private Long numAccesosEn1o2 = 0L;        
        private Long numPrediosAccesosInadecuados = 0L;
        private Long numPredios = 0L;
        private Long numPoblacionAfectada = 0L;
        private Long numPoblacionTotal = 0L;        

        public void inicializar() {
            numTipAcc1 = 0L;
            numTipAcc2 = 0L;
            numTipAcc3 = 0L;
            numTipAcc4 = 0L;
            numTipAcc5 = 0L;
            numMediosTransportes = 0L;
            numAccesosEn1o2 = 0L; 
            numPrediosAccesosInadecuados = 0L;
            numPredios = 0L;
            numPoblacionAfectada = 0L;
            numPoblacionTotal = 0L;
        }

        public Long getNumTipAcc1() {
            return numTipAcc1;
        }

        public Long getNumTipAcc2() {
            return numTipAcc2;
        }

        public Long getNumTipAcc3() {
            return numTipAcc3;
        }

        public Long getNumTipAcc4() {
            return numTipAcc4;
        }

        public Long getNumTipAcc5() {
            return numTipAcc5;
        }

        public Long getNumMediosTransportes() {
            return numMediosTransportes;
        }

        public Long getNumAccesosEn1o2() {
            return numAccesosEn1o2;
        }
        
        public Long getNumPrediosAccesosInadecuados() {
            return numPrediosAccesosInadecuados;
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
        
        public void incrementNumTipAcc1(String tipo) {
            if (!UtilCadena.isNullOVacio(tipo)) {
                this.numTipAcc1++;
            }
        }

        public void incrementNumTipAcc2(String tipo) {
            if (!UtilCadena.isNullOVacio(tipo)) {
                this.numTipAcc2++;
            }
        }
        
        public void incrementNumTipAcc3(String tipo) {
            if (!UtilCadena.isNullOVacio(tipo)) {
                this.numTipAcc3++;
            }
        }
        
        public void incrementNumTipAcc4(String tipo) {
            if (!UtilCadena.isNullOVacio(tipo)) {
                this.numTipAcc4++;
            }
        }
        
        public void incrementNumTipAcc5(String tipo) {
            if (!UtilCadena.isNullOVacio(tipo)) {
                this.numTipAcc5++;
            }
        }        

        public void addNumPrediosAccesosInadecuados(Long valor) {
            if (valor != null) {
                this.numPrediosAccesosInadecuados += valor;
            }
        }
        
        public void addNumMediosTransportes(Long valor) {
            if (valor != null) {
                this.numMediosTransportes += valor;
            }
        }
        
        public void addNumAccesosEn1o2(Long valor) {
            if (valor != null) {
                this.numAccesosEn1o2 += valor;
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

    public boolean isMostrarColTipoAcceso1() {
        return isAgrupamientoPredio();
    }

    public boolean isMostrarColCondicionAcceso1() {
        return isAgrupamientoPredio();
    }

    public boolean isMostrarColTipoAcceso2() {
        return isAgrupamientoPredio();
    }

    public boolean isMostrarColCondicionAcceso2() {
        return isAgrupamientoPredio();
    }

    public boolean isMostrarColTipoAcceso3() {
        return isAgrupamientoPredio();
    }

    public boolean isMostrarColCondicionAcceso3() {
        return isAgrupamientoPredio();
    }
    
    public boolean isMostrarColTipoAcceso4() {
        return isAgrupamientoPredio();
    }

    public boolean isMostrarColCondicionAcceso4() {
        return isAgrupamientoPredio();
    }
    
    public boolean isMostrarColTipoAcceso5() {
        return isAgrupamientoPredio();
    }

    public boolean isMostrarColCondicionAcceso5() {
        return isAgrupamientoPredio();
    }
    
    public boolean isMostrarColNumMediosTransporte() {
        return isAgrupamientoPredio();
    }
    
    public boolean isMostrarColNumAccesosEn1o2() {
        return isAgrupamientoPredio();
    }
    
    public boolean isMostrarColCondicionAccesibilidad() {
        return isAgrupamientoPredio();
    }
    
    public boolean isMostrarColNumAcessos() {
        return isAgrupamientoPredio();
    }
    
    public boolean isMostrarColVulnerabilidad() {
        return isAgrupamientoPredio();
    }
    
    public boolean isMostrarColNumPrediosNingunAccesoBueno() {
        return !isAgrupamientoPredio();
    }

    public boolean isMostrarColTotalPredios() {
        return !isAgrupamientoPredio();
    }

    public boolean isMostrarColProporcion() {
        return !isAgrupamientoPredio();
    }

    public boolean isMostrarColTotalEstudiantes() {
        return !isAgrupamientoPredio();
    } 
    
    public boolean isMostrarColPoblacionAfectada() {
        return !isAgrupamientoPredio();
    } 
    
    public boolean isMostrarColPoblacionTotal() {
        return !isAgrupamientoPredio();
    } 
   
    public String getNumAccesos() {
        return numAccesos;
    }

    public String getVulnerabilidad() {
        return vulnerabilidad;
    }

    public String getACC1() {
        return ACC1;
    }

    public String getACC2() {
        return ACC2;
    }

    public String getACC3() {
        return ACC3;
    }

    public String getACC4() {
        return ACC4;
    }

    public String getACC5() {
        return ACC5;
    }

    public String getACC6() {
        return ACC6;
    }

    public String getACC7() {
        return ACC7;
    }

    public String getACC8() {
        return ACC8;
    }

    public String getACC9() {
        return ACC9;
    }

    public String getACC10() {
        return ACC10;
    }

    public String getACC11() {
        return ACC11;
    }

    public String getACC12() {
        return ACC12;
    }

    public String getACC13() {
        return ACC13;
    }

    public String getPredSinBuenAcc() {
        return predSinBuenAcc;
    }

    public String getPrediosTotal() {
        return prediosTotal;
    }

    public String getProporcionPredios() {
        return proporcionPredios;
    }

    public String getPoblacionAfectada() {
        return poblacionAfectada;
    }
    
    public String getPoblacionTotal() {
        return poblacionTotal;
    }

    public boolean isCumple(String condicion) {
        if (condicion.trim().toUpperCase().equals("CUMPLE")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isNoCumple(String condicion) {
        if (condicion.trim().toUpperCase().equals("NO CUMPLE")) {
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
    
}
