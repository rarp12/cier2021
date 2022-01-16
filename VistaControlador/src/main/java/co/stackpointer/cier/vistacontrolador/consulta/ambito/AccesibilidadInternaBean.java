/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.consulta.ambito;

import co.stackpointer.cier.modelo.entidad.instrumento.TipologiaValorNombre;
import co.stackpointer.cier.modelo.filtro.ambito.FiltroAmbitoAccesibilidadInterna;
import co.stackpointer.cier.modelo.tipo.SiNo;
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
@ManagedBean(name = "accesibilidadInterna")
@ViewScoped
public class AccesibilidadInternaBean extends ConsultaAmbito {

    @ManagedProperty(value = "#{consultaAmbito}")
    private ConsultaAmbito consultaAmbito;
    private List<String> seleccionElementoComplementario;
    private SiNo seleccionAccesoDiscapacitado; 
    private List<TipologiaValorNombre> listaElementosComplementarios;
    private List<Registro> lista;
    private FiltroAmbitoAccesibilidadInterna filtro = new FiltroAmbitoAccesibilidadInterna();
    private TotalConsulta totalConsulta = new TotalConsulta();
  
    final String columnaEspaciosSinAccd = Utilidades.obtenerMensaje("consultas.ambito.accd.columna.espaciosSinAccd");
    final String columnaTotalEspacios = Utilidades.obtenerMensaje("consultas.ambito.accd.columna.totalEspacios");
    final String columnaProporcionEspacio = Utilidades.obtenerMensaje("consultas.ambito.accd.columna.proporcionEspacio");
    final String columnaEdificio = Utilidades.obtenerMensaje("consultas.ambito.accd.columna.edificio");
    final String columnaAccesoDiscapacitado = Utilidades.obtenerMensaje("consultas.ambito.accd.columna.accesoDiscapacitado");
    final String columnaElementoComplementario = Utilidades.obtenerMensaje("consultas.ambito.accd.columna.elementoComplementario");
    final String columnaCondicionElementoComplementario = Utilidades.obtenerMensaje("consultas.ambito.accd.columna.condicionElementoComplementario");
    final String columnaNivelMaxAccesibilidad = Utilidades.obtenerMensaje("consultas.ambito.accd.columna.nivelMaxAccesibilidad");
    final String columnaCumplimiento = Utilidades.obtenerMensaje("consultas.ambito.accd.columna.cumplimiento");
    final String columnaPrediosAccesoDiscapacitado = Utilidades.obtenerMensaje("consultas.ambito.accd.columna.prediosAccesoDiscapacitado");
    final String columnaPrediosCumplenAccesibilidad = Utilidades.obtenerMensaje("consultas.ambito.accd.columna.prediosCumplenAccesibilidad");
    final String columnaTotalPredios = Utilidades.obtenerMensaje("consultas.ambito.accd.columna.totalPredios");
    final String columnaProporcionAccesoDiscapacitado = Utilidades.obtenerMensaje("consultas.ambito.accd.columna.proporcionAccesoDiscapacitado");
    final String columnaProporcionCumplenAccesibilidad = Utilidades.obtenerMensaje("consultas.ambito.accd.columna.proporcionCumplenAccesibilidad");
    final String columnaCondicionRA = Utilidades.obtenerMensaje("consultas.ambito.accd.columna.condicionRA");
    final String columnaCondicionRN = Utilidades.obtenerMensaje("consultas.ambito.accd.columna.condicionRN");
    
    @PostConstruct
    public void inicializar() {
        //super.init();        
        listaElementosComplementarios = facParam.consultarValoresTipologia(TipologiaCodigo.ELEM_COMP_ACCESIBILIDAD.getValor(),getUsuarioSesion().getUsuario().getIdioma().getId());
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
        if (isMostrarColCondicionRA()) {
            listaCamposOpcionales.add(columnaCondicionRA);
        }
        if (isMostrarColCondicionRN()) {
            listaCamposOpcionales.add(columnaCondicionRN);
        }
         //propias del ambito      
        if(isMostrarColEspaciosSinAccd()){
            listaCamposOpcionales.add(columnaEspaciosSinAccd);
            selccionCamposOpcionales.add(columnaEspaciosSinAccd);
        }
        if(isMostrarColTotalEspacios()){
            listaCamposOpcionales.add(columnaTotalEspacios);
            selccionCamposOpcionales.add(columnaTotalEspacios);
        }
        if(isMostrarColProporcionEspacio()){
            listaCamposOpcionales.add(columnaProporcionEspacio);
            selccionCamposOpcionales.add(columnaProporcionEspacio);
        }
        if(isMostrarColEdificio()){
            listaCamposOpcionales.add(columnaEdificio);
            selccionCamposOpcionales.add(columnaEdificio);
        }
        if(isMostrarColAccesoDiscapacitado()){
            listaCamposOpcionales.add(columnaAccesoDiscapacitado);
            selccionCamposOpcionales.add(columnaAccesoDiscapacitado);
        }
        if(isMostrarColElementoComplementario()){
            listaCamposOpcionales.add(columnaElementoComplementario);
            selccionCamposOpcionales.add(columnaElementoComplementario);
        }
        if(isMostrarColCondicionElementoComplementario()){
            listaCamposOpcionales.add(columnaCondicionElementoComplementario);
            selccionCamposOpcionales.add(columnaCondicionElementoComplementario);
        }
        if(isMostrarColNivelMaxAccesibilidad()){
            listaCamposOpcionales.add(columnaNivelMaxAccesibilidad);
            selccionCamposOpcionales.add(columnaNivelMaxAccesibilidad);
        }
        if(isMostrarColCumplimiento()){
            listaCamposOpcionales.add(columnaCumplimiento);
            selccionCamposOpcionales.add(columnaCumplimiento);
        }
        /*opcionales
        if(isMostrarColTipoAcceso4()){
            listaCamposOpcionales.add();
        }*/
        
        if(isMostrarColPrediosAccesoDiscapacitado()){
            listaCamposOpcionales.add(columnaPrediosAccesoDiscapacitado);
            selccionCamposOpcionales.add(columnaPrediosAccesoDiscapacitado);
        }
        if(isMostrarColPrediosCumplenAccesibilidad()){
            listaCamposOpcionales.add(columnaPrediosCumplenAccesibilidad);
            selccionCamposOpcionales.add(columnaPrediosCumplenAccesibilidad);
        }
        if(isMostrarColTotalPredios()){
            listaCamposOpcionales.add(columnaTotalPredios);
            selccionCamposOpcionales.add(columnaTotalPredios);
        }
        if(isMostrarColProporcionAccesoDiscapacitado()){
            listaCamposOpcionales.add(columnaProporcionAccesoDiscapacitado);
            selccionCamposOpcionales.add(columnaProporcionAccesoDiscapacitado);
        }
        if(isMostrarColProporcionCumplenAccesibilidad()){
            listaCamposOpcionales.add(columnaProporcionCumplenAccesibilidad);
            selccionCamposOpcionales.add(columnaProporcionCumplenAccesibilidad);
        }              
    }
    
    private String elementosSeleccionados() {
        String codElementoComplementario = null;
        if (!UtilCadena.isNullOVacio(seleccionElementoComplementario)) {
            boolean sw = true;
            for (String elemento : seleccionElementoComplementario) {
                if (sw) {
                    codElementoComplementario = "'" + elemento + "'";
                    sw = false;
                } else {
                    codElementoComplementario += ",'" + elemento + "'";
                }
            }
        }
        return codElementoComplementario;
    }
 
    @Override
    public void resetearFiltros() {
        consultaAmbito.resetearNiveles();
        this.resetearListasEstSedesPredios();
        this.seleccionSector = null;        
        this.seleccionZona = null;
        this.seleccionAccesoDiscapacitado = null;
        this.seleccionElementoComplementario = null;
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
        this.seleccionAccesoDiscapacitado = null;
        this.seleccionElementoComplementario = null;
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
        filtro.setElementoComplementario(elementosSeleccionados()!= null ? elementosSeleccionados() : null);
        filtro.setAccesoDiscapacitado(seleccionAccesoDiscapacitado != null ? seleccionAccesoDiscapacitado.getValor() : null);
        
        List<Object[]> resultados = facAmbito.consultarAccesibilidadInterna(filtro);
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
                registro.setEspaciosSinAccd(UtilConsulta.longValueOf(fila[i++]));
                registro.setTotalEspacios(UtilConsulta.longValueOf(fila[i++]));
                registro.setProporcionEspacio(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setEdificio(UtilConsulta.stringValueOf(fila[i++]));
                registro.setAccesoDiscapacitado(Utilidades.obtenerMensaje(UtilConsulta.stringValueOf(fila[i++])));
                registro.setNivelMaxAccesibilidad(UtilConsulta.longValueOf(fila[i++]));
                try{//Puede venir el código de la tipología o la etiqueta sql.ind.general.no
                    registro.setElementoComplementario(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i]),getUsuarioSesion().getUsuario().getIdioma().getId()));
                }catch(Exception e){
                    registro.setElementoComplementario(Utilidades.obtenerMensaje(UtilConsulta.stringValueOf(fila[i])));
                }
                i++;                
                registro.setCondicionElementoComplementario(Utilidades.obtenerMensaje(UtilConsulta.stringValueOf(fila[i++])));
                registro.setCumplimiento(Utilidades.obtenerMensaje(UtilConsulta.stringValueOf(fila[i++])));
                //otros
                registro.setDirPredio(UtilConsulta.stringValueOf(fila[i++]));
                registro.setZona(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]),getUsuarioSesion().getUsuario().getIdioma().getId()));
                registro.setSector(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]),getUsuarioSesion().getUsuario().getIdioma().getId()));
                registro.setClima(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]),getUsuarioSesion().getUsuario().getIdioma().getId()));
                registro.setCondicionRA(UtilConsulta.stringValueOf(fila[i++]));
                registro.setCondicionRN(UtilConsulta.stringValueOf(fila[i++]));
                /*totales
                totalConsulta.addNumEspaciosSinAccd(registro.getEspaciosSinAccd());
                totalConsulta.addNumTotalEspacios(registro.getTotalEspacios());
                totalConsulta.incrementNumElementosComplementarios(registro.getElementoComplementario());*/                
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
                registro.setPrediosAccesoDiscapacitado(UtilConsulta.longValueOf(fila[i++]));
                registro.setPrediosCumplenAccesibilidad(UtilConsulta.longValueOf(fila[i++]));
                registro.setTotalPredios(UtilConsulta.longValueOf(fila[i++]));
                registro.setProporcionAcceso(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setProporcionCumplimiento(UtilConsulta.doubleValueOf(fila[i++]));
                // totales
                totalConsulta.addNumPrediosAccesoDiscapacitado(registro.getPrediosAccesoDiscapacitado());
                totalConsulta.addNumPrediosCumplenAccesibilidad(registro.getPrediosCumplenAccesibilidad());
                totalConsulta.addNumTotalPredios(registro.getTotalPredios());
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

    public SiNo getSeleccionAccesoDiscapacitado() {
        return seleccionAccesoDiscapacitado;
    }

    public void setSeleccionAccesoDiscapacitado(SiNo seleccionAccesoDiscapacitado) {
        this.seleccionAccesoDiscapacitado = seleccionAccesoDiscapacitado;
    }       

    public List<String> getSeleccionElementoComplementario() {
        return seleccionElementoComplementario;
    }

    public void setSeleccionElementoComplementario(List<String> seleccionElementoComplementario) {
        this.seleccionElementoComplementario = seleccionElementoComplementario;
    }
    
    public List<TipologiaValorNombre> getListaElementosComplementarios() {
        return listaElementosComplementarios;
    }

    public void setListaElementosComplementarios(List<TipologiaValorNombre> listaElementosComplementarios) {
        this.listaElementosComplementarios = listaElementosComplementarios;
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
        
        private Long espaciosSinAccd;
        private Long totalEspacios;
        private Double proporcionEspacio;
        private String edificio;
        private String accesoDiscapacitado;
        private String elementoComplementario;
        private String condicionElementoComplementario;
        private Long nivelMaxAccesibilidad;
        private String cumplimiento;
        
        private String dirPredio;
        private String zona;
        private String sector;
        private String clima;
        private String condicionRA;
        private String condicionRN;
        
        private Long prediosAccesoDiscapacitado;
        private Long prediosCumplenAccesibilidad;
        private Long totalPredios;
        private Double proporcionAcceso;
        private Double proporcionCumplimiento;   

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

        public String getCondicionRA() {
            return condicionRA;
        }

        public void setCondicionRA(String condicionRA) {
            this.condicionRA = condicionRA;
            if (condicionRA != null) {
                if (condicionRA.equalsIgnoreCase("sql.ind.general.critico")) {
                    this.condicionRA = Utilidades.obtenerMensaje(condicionRA);
                } else {
                    this.condicionRA = Utilidades.obtenerMensaje(condicionRA);
                }
            }
        }

        public String getCondicionRN() {
            return condicionRN;
        }

        public void setCondicionRN(String condicionRN) {
            this.condicionRN = condicionRN;
            if (condicionRN != null) {
                if (condicionRN.equalsIgnoreCase("sql.ind.general.critico")) {
                    this.condicionRN = Utilidades.obtenerMensaje(condicionRN);
                } else {
                    this.condicionRN = Utilidades.obtenerMensaje(condicionRN);
                }
            }
        }
        
        public Long getEspaciosSinAccd() {
            return espaciosSinAccd;
        }

        public void setEspaciosSinAccd(Long espaciosSinAccd) {
            this.espaciosSinAccd = espaciosSinAccd;
        }

        public Long getTotalEspacios() {
            return totalEspacios;
        }

        public void setTotalEspacios(Long totalEspacios) {
            this.totalEspacios = totalEspacios;
        }

        public Double getProporcionEspacio() {
            return proporcionEspacio;
        }

        public void setProporcionEspacio(Double proporcionEspacio) {
            this.proporcionEspacio = proporcionEspacio;
        }

        public String getEdificio() {
            return edificio;
        }

        public void setEdificio(String edificio) {
            this.edificio = edificio;
        }

        public String getAccesoDiscapacitado() {
            return accesoDiscapacitado;
        }

        public void setAccesoDiscapacitado(String accesoDiscapacitado) {
            this.accesoDiscapacitado = accesoDiscapacitado;
        }

        public String getElementoComplementario() {
            return elementoComplementario;
        }

        public void setElementoComplementario(String elementoComplementario) {
            this.elementoComplementario = elementoComplementario;
        }

        public String getCondicionElementoComplementario() {
            return condicionElementoComplementario;
        }

        public void setCondicionElementoComplementario(String condicionElementoComplementario) {
            this.condicionElementoComplementario = condicionElementoComplementario;
        }

        public Long getNivelMaxAccesibilidad() {
            return nivelMaxAccesibilidad;
        }

        public void setNivelMaxAccesibilidad(Long nivelMaxAccesibilidad) {
            this.nivelMaxAccesibilidad = nivelMaxAccesibilidad;
        }

        public String getCumplimiento() {
            return cumplimiento;
        }

        public void setCumplimiento(String cumplimiento) {
            this.cumplimiento = cumplimiento;
        }

        public Long getPrediosAccesoDiscapacitado() {
            return prediosAccesoDiscapacitado;
        }

        public void setPrediosAccesoDiscapacitado(Long prediosAccesoDiscapacitado) {
            this.prediosAccesoDiscapacitado = prediosAccesoDiscapacitado;
        }

        public Long getPrediosCumplenAccesibilidad() {
            return prediosCumplenAccesibilidad;
        }

        public void setPrediosCumplenAccesibilidad(Long prediosCumplenAccesibilidad) {
            this.prediosCumplenAccesibilidad = prediosCumplenAccesibilidad;
        }

        public Long getTotalPredios() {
            return totalPredios;
        }

        public void setTotalPredios(Long totalPredios) {
            this.totalPredios = totalPredios;
        }

        public Double getProporcionAcceso() {
            return proporcionAcceso;
        }

        public void setProporcionAcceso(Double proporcionAcceso) {
            this.proporcionAcceso = proporcionAcceso;
        }

        public Double getProporcionCumplimiento() {
            return proporcionCumplimiento;
        }

        public void setProporcionCumplimiento(Double proporcionCumplimiento) {
            this.proporcionCumplimiento = proporcionCumplimiento;
        }
                     
    }
    
    public class TotalConsulta {

        private Long numEspaciosSinAccd;
        private Long numTotalEspacios;
        private Long numElementosComplementarios;
        private Long numPrediosAccesoDiscapacitado;
        private Long numPrediosCumplenAccesibilidad;
        private Long numTotalPredios;        

        public void inicializar() {
            numEspaciosSinAccd = 0L;
            numTotalEspacios = 0L;         
            numElementosComplementarios = 0L;
            numPrediosAccesoDiscapacitado = 0L;
            numPrediosCumplenAccesibilidad = 0L;
            numTotalPredios = 0L;            
        }

        public Long getNumEspaciosSinAccd() {
            return numEspaciosSinAccd;
        }

        public Long getNumTotalEspacios() {
            return numTotalEspacios;
        }

        public Long getNumElementosComplementarios() {
            return numElementosComplementarios;
        }
        
        public Long getNumPrediosCumplenAccesibilidad() {
            return numPrediosCumplenAccesibilidad;
        }
        
        public Long getNumPrediosAccesoDiscapacitado() {
            return numPrediosAccesoDiscapacitado;
        }

        public Long getNumTotalPredios() {
            return numTotalPredios;
        }                 

        public void addNumEspaciosSinAccd(Long valor) {
            if (valor != null) {
                this.numEspaciosSinAccd += valor;
            }
        }
        
        public void addNumTotalEspacios(Long valor) {
            if (valor != null) {
                this.numTotalEspacios += valor;
            }
        }  
        
        public void incrementNumElementosComplementarios(String tipo) {
            if (!UtilCadena.isNullOVacio(tipo)) {
                this.numElementosComplementarios++;
            }
        }      
        
        public void addNumPrediosAccesoDiscapacitado(Long valor) {
            if (valor != null) {
                this.numPrediosAccesoDiscapacitado += valor;
            }
        }

        public void addNumPrediosCumplenAccesibilidad(Long valor) {
            if (valor != null) {
                this.numPrediosCumplenAccesibilidad += valor;
            }
        }
        
        public void addNumTotalPredios(Long valor) {
            if (valor != null) {
                this.numTotalPredios += valor;
            }
        }
    }
    
     // mostrar columnas  
    public boolean isMostrarColCondicionRA() {
        return isAgrupamientoPredio();
    }

    public boolean isMostrarColCondicionRN() {
        return isAgrupamientoPredio();
    }
    
    public boolean isMostrarColEspaciosSinAccd() {
        return isAgrupamientoPredio();
    }

    public boolean isMostrarColTotalEspacios() {
        return isAgrupamientoPredio();
    }

    public boolean isMostrarColProporcionEspacio() {
        return isAgrupamientoPredio();
    }

    public boolean isMostrarColEdificio() {
        return isAgrupamientoPredio();
    }

    public boolean isMostrarColAccesoDiscapacitado() {
        return isAgrupamientoPredio();
    }

    public boolean isMostrarColElementoComplementario() {
        return isAgrupamientoPredio();
    }
    
    public boolean isMostrarColCondicionElementoComplementario() {
        return isAgrupamientoPredio();
    }

    public boolean isMostrarColNivelMaxAccesibilidad() {
        return isAgrupamientoPredio();
    }
    
    public boolean isMostrarColCumplimiento() {
        return isAgrupamientoPredio();
    }   
    
    public boolean isMostrarColPrediosAccesoDiscapacitado() {
        return !isAgrupamientoPredio();
    }

    public boolean isMostrarColPrediosCumplenAccesibilidad() {
        return !isAgrupamientoPredio();
    }

    public boolean isMostrarColTotalPredios() {
        return !isAgrupamientoPredio();
    }

    public boolean isMostrarColProporcionAccesoDiscapacitado() {
        return !isAgrupamientoPredio();
    }
    
    public boolean isMostrarColProporcionCumplenAccesibilidad() {
        return !isAgrupamientoPredio();
    }
    
    //getter de columnas
    public String getColumnaEspaciosSinAccd() {
        return columnaEspaciosSinAccd;
    }

    public String getColumnaTotalEspacios() {
        return columnaTotalEspacios;
    }

    public String getColumnaProporcionEspacio() {
        return columnaProporcionEspacio;
    }

    public String getColumnaEdificio() {
        return columnaEdificio;
    }

    public String getColumnaAccesoDiscapacitado() {
        return columnaAccesoDiscapacitado;
    }

    public String getColumnaElementoComplementario() {
        return columnaElementoComplementario;
    }

    public String getColumnaCondicionElementoComplementario() {
        return columnaCondicionElementoComplementario;
    }

    public String getColumnaNivelMaxAccesibilidad() {
        return columnaNivelMaxAccesibilidad;
    }

    public String getColumnaCumplimiento() {
        return columnaCumplimiento;
    }

    public String getColumnaPrediosAccesoDiscapacitado() {
        return columnaPrediosAccesoDiscapacitado;
    }

    public String getColumnaPrediosCumplenAccesibilidad() {
        return columnaPrediosCumplenAccesibilidad;
    }

    public String getColumnaTotalPredios() {
        return columnaTotalPredios;
    }

    public String getColumnaProporcionAccesoDiscapacitado() {
        return columnaProporcionAccesoDiscapacitado;
    } 

    public String getColumnaProporcionCumplenAccesibilidad() {
        return columnaProporcionCumplenAccesibilidad;
    }
    
    public String getColumnaCondicionRA() {
        return columnaCondicionRA;
    }

    public String getColumnaCondicionRN() {
        return columnaCondicionRN;
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
