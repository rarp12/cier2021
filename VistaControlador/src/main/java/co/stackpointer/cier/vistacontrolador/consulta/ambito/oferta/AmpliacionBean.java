/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.consulta.ambito.oferta;

import co.stackpointer.cier.modelo.filtro.ambito.FiltroAmbitoAmpliacion;
import co.stackpointer.cier.modelo.tipo.Cumplimiento;
import co.stackpointer.cier.modelo.tipo.ParamEstandar;
import co.stackpointer.cier.modelo.utilidad.UtilConsulta;
import co.stackpointer.cier.modelo.utilidad.UtilOut;
import co.stackpointer.cier.vista.Utilidades;
import co.stackpointer.cier.vistacontrolador.consulta.ambito.ConsultaAmbito;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.omnifaces.util.Messages;

/**
 *
 * @author user
 */
@ManagedBean(name = "ampliacionBean")
@ViewScoped
public class AmpliacionBean extends ConsultaAmbito {

    @ManagedProperty(value = "#{consultaAmbito}")
    private ConsultaAmbito consultaAmbito;
    private Registro totalConsulta = new Registro();
    private List<Registro> lista;
    private List<Cumplimiento> listaCumplimientos;
    private Cumplimiento seleccionCumpAreaPredio;
    private Cumplimiento seleccionCumpAreaConstruccion;
    private Cumplimiento seleccionCumpAreaAmbienteAula;
    private FiltroAmbitoAmpliacion filtro = new FiltroAmbitoAmpliacion();
    /*private Double estandarConstruccion;
    private Double estandarPredio;*/
    private Double estandarAula;
    
    private final String columnaAreaRealPredio = Utilidades.obtenerMensaje("consultas.ambito.ampliacion.columna.predio.area.real");
    private final String columnaEstandarPredio = Utilidades.obtenerMensaje("consultas.ambito.ampliacion.columna.predio.estandar");
    private final String columnaEstandarLote = Utilidades.obtenerMensaje("consultas.ambito.ampliacion.columna.predio.estandar.lote");    
    private final String columnaCumpNormaPredio = Utilidades.obtenerMensaje("consultas.ambito.ampliacion.columna.predio.cumplimiento");		
    private final String columnaCuposPredio = Utilidades.obtenerMensaje("consultas.ambito.ampliacion.columna.predio.cupos");
    private final String columnaM2RequeridosPredio = Utilidades.obtenerMensaje("consultas.ambito.ampliacion.columna.predio.m2");
 
    private final String columnaAreaConstruccionTotal = Utilidades.obtenerMensaje("consultas.ambito.ampliacion.columna.construccion.area");
    private final String columnaEstandarConstruccionTotal = Utilidades.obtenerMensaje("consultas.ambito.ampliacion.columna.construccion.estandar");
    private final String columnaEstandarConst = Utilidades.obtenerMensaje("consultas.ambito.ampliacion.columna.construccion.estandar.const");
    private final String columnaCumpNormaConstruccionTotal = Utilidades.obtenerMensaje("consultas.ambito.ampliacion.columna.construccion.cumplimiento");		
    private final String columnaM2RequeridosConstruccionTotal= Utilidades.obtenerMensaje("consultas.ambito.ampliacion.columna.construccion.m2");
        
    private final String columnaAreaAula = Utilidades.obtenerMensaje("consultas.ambito.ampliacion.columna.aula.area");
    private final String columnaNumeroAula = Utilidades.obtenerMensaje("consultas.ambito.ampliacion.columna.aula.numero");
    private final String columnaEstandarAula = Utilidades.obtenerMensaje("consultas.ambito.ampliacion.columna.aula.estabdar");
    private final String columnaCumpNormaAula = Utilidades.obtenerMensaje("consultas.ambito.ampliacion.columna.aula.cunplimiento");
    private final String columnaM2RequeridosAula = Utilidades.obtenerMensaje("consultas.ambito.ampliacion.columna.aula.m2");
    private final String columnaEspaciosRequeridosAula = Utilidades.obtenerMensaje("consultas.ambito.ampliacion.columna.aula.espacio");
            
    private final String columnaPosConstruccionPrimerPiso = Utilidades.obtenerMensaje("consultas.ambito.ampliacion.columna.pos.cons.primer");
    private final String columnaPosConstruccionAltura = Utilidades.obtenerMensaje("consultas.ambito.ampliacion.columna.pos.cons.altura");
    private final String columnaPosConstruccionTotal = Utilidades.obtenerMensaje("consultas.ambito.ampliacion.columna.pos.cons.total");
    private final String columnaM2AmpliarCobertura = Utilidades.obtenerMensaje("consultas.ambito.ampliacion.columna.m2.ampliar.cobertura");
    private final String columnaAlumnosNuevosInfraestructura = Utilidades.obtenerMensaje("consultas.ambito.ampliacion.columna.alumnos.nuevos");
    

    private boolean checkCumpAreaPredio;
    private boolean checkCumpAreaConstruccion;
    private boolean chackCumpAreaAmbienteAula;
    @PostConstruct
    public void inicializar() {
        //super.init();
        this.lista = new ArrayList<Registro>();
        this.cargarCamposOpcionales();
        this.listaCumplimientos = Arrays.asList(Cumplimiento.values());
        /*this.estandarConstruccion = facParam.consultarEstandar(ParamEstandar.ESTANDAR_CONSTRUCCION).getValor();
        this.estandarPredio = facParam.consultarEstandar(ParamEstandar.ESTANDAR_LOTE).getValor();*/
        this.estandarAula = facParam.consultarEstandar(ParamEstandar.ESTANDAR_AULA).getValor();
    }
   
    public void consultar() {
        try {
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

            //otros filtros
            filtro.setCodZona(seleccionZona != null ? seleccionZona.getTipValor().getCodigo() : null);
            filtro.setCodSector(seleccionSector != null ? seleccionSector.getTipValor().getCodigo() : null);
            // filtros de area
            filtro.setCumplimientoAreaPredio(seleccionCumpAreaPredio);
            filtro.setCumplimientoAreaConstruccion(seleccionCumpAreaConstruccion);
            filtro.setCumplimientoAreaAmbienteAula(seleccionCumpAreaAmbienteAula);

            List<Object[]> resultados = facAmbito.consultarAmpliacion(filtro);
            lista = new ArrayList<Registro>(resultados.size());
            if (lista.size() < 1) {
                mensajeTablaVacia = Utilidades.obtenerMensaje("aplicacion.general.noExistenRegistros");
            }

            this.inicializarTotalesConsulta();

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
                    //otros
                    registro.setDirPredio(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setPropiedadPredio(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setZona(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]),getUsuarioSesion().getUsuario().getIdioma().getId()));
                    registro.setSector(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]),getUsuarioSesion().getUsuario().getIdioma().getId()));
                    registro.setClima(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]),getUsuarioSesion().getUsuario().getIdioma().getId()));
                    registro.setEtnias(verificarEtnias(UtilConsulta.stringValueOf(fila[i++])));
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
                }
                // indicadores  
                // predio
                registro.setAreaRealPredio(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setEstandarPredio(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setEstandarLote(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setCumpNormaPredio(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setCuposPredio(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setM2RequeridosPredio(UtilConsulta.doubleValueOf(fila[i++]));
                // construccion
                registro.setAreaConstruccionTotal(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setEstandarConstruccionTotal(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setEstandarConst(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setCumpNormaConstruccionTotal(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setM2RequeridosConstruccionTotal(UtilConsulta.doubleValueOf(fila[i++]));
                // aula
                registro.setAreaAula(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setNumeroAula(UtilConsulta.longValueOf(fila[i++]));
                registro.setEstandarAula(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setCumpNormaAula(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setM2RequeridosAula(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setEspaciosRequeridosAula(UtilConsulta.doubleValueOf(fila[i++]));
                // infraestructura de construccion
                registro.setAreaConstruccionPrimerPiso(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setPosConstruccionPrimerPiso(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setPosConstruccionAltura(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setPosConstruccionTotal(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setM2AmpliarCobertura(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setAlumnosNuevosInfraestructura(UtilConsulta.longValueOf(fila[i++]));
                // totales
                this.totalizarRegistro(registro);
                lista.add(registro);
            }
        } catch (Exception e) {
            e.printStackTrace();
            /*Messages.addFlashGlobalError(e.getMessage());
            UtilOut.println(e);*/
        }
    }

    @Override
    public void resetearFiltros() {
        consultaAmbito.resetearNiveles();
        this.resetearListasEstSedesPredios();
        this.seleccionSector = null;
        this.seleccionZona = null;
        this.seleccionCumpAreaAmbienteAula = null;
        this.seleccionCumpAreaConstruccion = null;
        this.seleccionCumpAreaPredio = null;
        consultaAmbito.nivelDPAOfAccesoUsuario();
        this.resetearConsulta();
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
        this.seleccionCumpAreaAmbienteAula = null;
        this.seleccionCumpAreaConstruccion = null;
        this.seleccionCumpAreaPredio = null;
        //consultaAmbito.nivelDPAOfAccesoUsuario();
        this.resetearConsulta();
    }

    @Override
    public void cambioAgrupamiento() {
        this.resetearConsulta();
    }

    public void cambioFiltroArea() {
        this.resetearConsulta();
    }

    private void resetearConsulta() {
        // columnas
        this.cargarCamposOpcionales();
        // resultados
        this.lista.clear();
        this.inicializarTotalesConsulta();
    }
    
     
     public void cargarCamposOpcionales() {        
        selccionCamposOpcionales.clear();
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
        
        // indciadores predio
        listaCamposOpcionales.add(columnaAreaRealPredio);
        listaCamposOpcionales.add(columnaEstandarPredio);
        listaCamposOpcionales.add(columnaEstandarLote);
        listaCamposOpcionales.add(columnaCumpNormaPredio);
        listaCamposOpcionales.add(columnaCuposPredio);
        listaCamposOpcionales.add(columnaM2RequeridosPredio);
        // indciadores predio seleccion
        if (checkCumpAreaPredio) {
            selccionCamposOpcionales.add(columnaAreaRealPredio);
            selccionCamposOpcionales.add(columnaEstandarPredio);
            selccionCamposOpcionales.add(columnaEstandarLote);
            selccionCamposOpcionales.add(columnaCumpNormaPredio);
            selccionCamposOpcionales.add(columnaCuposPredio);
            selccionCamposOpcionales.add(columnaM2RequeridosPredio);
        }
        // indciadores construccion
        listaCamposOpcionales.add(columnaAreaConstruccionTotal);
        listaCamposOpcionales.add(columnaEstandarConstruccionTotal);
        listaCamposOpcionales.add(columnaEstandarConst);
        listaCamposOpcionales.add(columnaCumpNormaConstruccionTotal);
        listaCamposOpcionales.add(columnaM2RequeridosConstruccionTotal);
        // indciadores construccion seleccion
        
        if (checkCumpAreaConstruccion) {
            selccionCamposOpcionales.add(columnaAreaConstruccionTotal);
            selccionCamposOpcionales.add(columnaEstandarConstruccionTotal);
            selccionCamposOpcionales.add(columnaEstandarConst);
            selccionCamposOpcionales.add(columnaCumpNormaConstruccionTotal);
            selccionCamposOpcionales.add(columnaM2RequeridosConstruccionTotal);
        }

        // indciadores aula
        listaCamposOpcionales.add(columnaAreaAula);
        listaCamposOpcionales.add(columnaNumeroAula);
        listaCamposOpcionales.add(columnaEstandarAula);
        listaCamposOpcionales.add(columnaCumpNormaAula);
        listaCamposOpcionales.add(columnaM2RequeridosAula);
        listaCamposOpcionales.add(columnaEspaciosRequeridosAula);
        // indciadores aula seleccion
        if (chackCumpAreaAmbienteAula) {
            selccionCamposOpcionales.add(columnaAreaAula);
            selccionCamposOpcionales.add(columnaNumeroAula);
            selccionCamposOpcionales.add(columnaEstandarAula);
            selccionCamposOpcionales.add(columnaCumpNormaAula);
            selccionCamposOpcionales.add(columnaM2RequeridosAula);
            selccionCamposOpcionales.add(columnaEspaciosRequeridosAula);
        }
        
        // indciadores infraestructura construccion 
        listaCamposOpcionales.add(columnaPosConstruccionPrimerPiso);
        listaCamposOpcionales.add(columnaPosConstruccionAltura);
        listaCamposOpcionales.add(columnaPosConstruccionTotal);
        //ind m2_ampliar_cobertura y alumnos_nuevos
        listaCamposOpcionales.add(columnaM2AmpliarCobertura);
        listaCamposOpcionales.add(columnaAlumnosNuevosInfraestructura);
        // indciadores infraestructura construccion seleccion
        selccionCamposOpcionales.add(columnaPosConstruccionPrimerPiso);
        selccionCamposOpcionales.add(columnaPosConstruccionAltura);
        selccionCamposOpcionales.add(columnaPosConstruccionTotal);
        
    }
     
    public boolean isCumpleNorma(Object idoneidad) {
        if (idoneidad != null) {
            Double valor = (Double)idoneidad / 100D;
            return (valor.compareTo(1D) == 0 || valor.compareTo(1D) > 0) ? true : false;
        } else {
            return false;
        }
    }
    
    public boolean isNoCumpleNorma(Object idoneidad) {
        if (idoneidad != null) {
            Double valor = (Double)idoneidad / 100D;
            return (valor.compareTo(1D) < 0) ? true : false;
        } else {
            return false;
        }
    }

    public List<Registro> getLista() {
        return lista;
    }

    public Registro getTotalConsulta() {
        return totalConsulta;
    }

    public Cumplimiento getSeleccionCumpAreaPredio() {
        return seleccionCumpAreaPredio;
    }

    public void setSeleccionCumpAreaPredio(Cumplimiento seleccionCumpAreaPredio) {
        this.seleccionCumpAreaPredio = seleccionCumpAreaPredio;
    }

    public Cumplimiento getSeleccionCumpAreaConstruccion() {
        return seleccionCumpAreaConstruccion;
    }

    public void setSeleccionCumpAreaConstruccion(Cumplimiento seleccionCumpAreaConstruccion) {
        this.seleccionCumpAreaConstruccion = seleccionCumpAreaConstruccion;
    }

    public Cumplimiento getSeleccionCumpAreaAmbienteAula() {
        return seleccionCumpAreaAmbienteAula;
    }

    public void setSeleccionCumpAreaAmbienteAula(Cumplimiento seleccionCumpAreaAmbienteAula) {
        this.seleccionCumpAreaAmbienteAula = seleccionCumpAreaAmbienteAula;
    }   

    public List<Cumplimiento> getListaCumplimientos() {
        return listaCumplimientos;
    }       

    /*public Double getEstandarConstruccion() {
        return estandarConstruccion;
    }

    public Double getEstandarPredio() {
        return estandarPredio;
    }*/

    public Double getEstandarAula() {
        return estandarAula;
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
        private String dirPredio;        
        private String propiedadPredio;      
                
        private String zona;
        private String sector;
        private String clima;
        private String etnias;   
                
        private Double areaRealPredio;
        private Double estandarPredio;
        private Double estandarLote;
        private Double cumpNormaPredio;        
        private Double cuposPredio;        
        private Double m2RequeridosPredio;
        
        private Double areaConstruccionTotal;
        private Double estandarConstruccionTotal;
        private Double estandarConst;
        private Double cumpNormaConstruccionTotal;
        private Double m2RequeridosConstruccionTotal;
        
        private Double areaAula;
        private Long numeroAula;
        private Double estandarAula;
        private Double cumpNormaAula;
        private Double m2RequeridosAula;
        private Double espaciosRequeridosAula;
        
        private Double areaConstruccionPrimerPiso;
        private Double posConstruccionPrimerPiso;
        private Double posConstruccionAltura ;
        private Double posConstruccionTotal;        
        private Double m2AmpliarCobertura;
        private Long alumnosNuevosInfraestructura;

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

        public String getPropiedadPredio() {
            return propiedadPredio;
        }

        public void setPropiedadPredio(String propiedadPredio) {
            this.propiedadPredio = propiedadPredio;
            if (propiedadPredio != null) {
                if (propiedadPredio.equalsIgnoreCase("sql.ind.general.si")) {
                    this.propiedadPredio = Utilidades.obtenerMensaje(propiedadPredio);
                } else if (propiedadPredio.equalsIgnoreCase("sql.ind.general.no")){
                this.propiedadPredio = Utilidades.obtenerMensaje(propiedadPredio);
                } else{
                    this.propiedadPredio = Utilidades.obtenerMensaje(propiedadPredio);
                }
            }
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

        public Double getAreaRealPredio() {
            return areaRealPredio;
        }

        public void setAreaRealPredio(Double areaRealPredio) {
            this.areaRealPredio = areaRealPredio;
        }

        public Double getEstandarPredio() {
            return estandarPredio;
        }

        public void setEstandarPredio(Double estandarPredio) {
            this.estandarPredio = estandarPredio;
        }

        public Double getEstandarLote() {
            return estandarLote;
        }

        public void setEstandarLote(Double estandarLote) {
            this.estandarLote = estandarLote;
        }
        
        public Double getCumpNormaPredio() {
            return cumpNormaPredio;
        }

        public void setCumpNormaPredio(Double cumpNormaPredio) {
            this.cumpNormaPredio = cumpNormaPredio;
        }

        public Double getCuposPredio() {
            return cuposPredio;
        }

        public void setCuposPredio(Double cuposPredio) {
            this.cuposPredio = cuposPredio;
        }

        public Double getM2RequeridosPredio() {
            return m2RequeridosPredio;
        }

        public void setM2RequeridosPredio(Double m2RequeridosPredio) {
            this.m2RequeridosPredio = m2RequeridosPredio;
        }

        public Double getAreaConstruccionTotal() {
            return areaConstruccionTotal;
        }

        public void setAreaConstruccionTotal(Double areaConstruccionTotal) {
            this.areaConstruccionTotal = areaConstruccionTotal;
        }

        public Double getEstandarConstruccionTotal() {
            return estandarConstruccionTotal;
        }

        public void setEstandarConstruccionTotal(Double estandarConstruccionTotal) {
            this.estandarConstruccionTotal = estandarConstruccionTotal;
        }

        public Double getEstandarConst() {
            return estandarConst;
        }

        public void setEstandarConst(Double estandarConst) {
            this.estandarConst = estandarConst;
        }
        
        public Double getCumpNormaConstruccionTotal() {
            return cumpNormaConstruccionTotal;
        }

        public void setCumpNormaConstruccionTotal(Double cumpNormaConstruccionTotal) {
            this.cumpNormaConstruccionTotal = cumpNormaConstruccionTotal;
        }

        public Double getM2RequeridosConstruccionTotal() {
            return m2RequeridosConstruccionTotal;
        }

        public void setM2RequeridosConstruccionTotal(Double m2RequeridosConstruccionTotal) {
            this.m2RequeridosConstruccionTotal = m2RequeridosConstruccionTotal;
        }

        public Double getAreaAula() {
            return areaAula;
        }

        public void setAreaAula(Double areaAula) {
            this.areaAula = areaAula;
        }

        public Long getNumeroAula() {
            return numeroAula;
        }

        public void setNumeroAula(Long numeroAula) {
            this.numeroAula = numeroAula;
        }

        public Double getEstandarAula() {
            return estandarAula;
        }

        public void setEstandarAula(Double estandarAula) {
            this.estandarAula = estandarAula;
        }

        public Double getCumpNormaAula() {
            return cumpNormaAula;
        }

        public void setCumpNormaAula(Double cumpNormaAula) {
            this.cumpNormaAula = cumpNormaAula;
        }

        public Double getM2RequeridosAula() {
            return m2RequeridosAula;
        }

        public void setM2RequeridosAula(Double m2RequeridosAula) {
            this.m2RequeridosAula = m2RequeridosAula;
        }

        public Double getEspaciosRequeridosAula() {
            return espaciosRequeridosAula;
        }

        public void setEspaciosRequeridosAula(Double espaciosRequeridosAula) {
            this.espaciosRequeridosAula = espaciosRequeridosAula;
        }

        public Double getAreaConstruccionPrimerPiso() {
            return areaConstruccionPrimerPiso;
        }

        public void setAreaConstruccionPrimerPiso(Double areaConstruccionPrimerPiso) {
            this.areaConstruccionPrimerPiso = areaConstruccionPrimerPiso;
        }

        public Double getPosConstruccionPrimerPiso() {
            return posConstruccionPrimerPiso;
        }

        public void setPosConstruccionPrimerPiso(Double posConstruccionPrimerPiso) {
            this.posConstruccionPrimerPiso = posConstruccionPrimerPiso;
        }

        public Double getPosConstruccionAltura() {
            return posConstruccionAltura;
        }

        public void setPosConstruccionAltura(Double posConstruccionAltura) {
            this.posConstruccionAltura = posConstruccionAltura;
        }

        public Double getPosConstruccionTotal() {
            return posConstruccionTotal;
        }

        public void setPosConstruccionTotal(Double posConstruccionTotal) {
            this.posConstruccionTotal = posConstruccionTotal;
        }

        public Double getM2AmpliarCobertura() {
            return m2AmpliarCobertura;
        }

        public void setM2AmpliarCobertura(Double m2AmpliarCobertura) {
            this.m2AmpliarCobertura = m2AmpliarCobertura;
        }

        public Long getAlumnosNuevosInfraestructura() {
            return alumnosNuevosInfraestructura;
        }

        public void setAlumnosNuevosInfraestructura(Long alumnosNuevosInfraestructura) {
            this.alumnosNuevosInfraestructura = alumnosNuevosInfraestructura;
        }       
    }

    private void inicializarTotalesConsulta() {
        totalConsulta.setAreaRealPredio(0D);
        totalConsulta.setEstandarPredio(0D);
        totalConsulta.setCumpNormaPredio(0D);
        totalConsulta.setCuposPredio(0D);
        totalConsulta.setM2RequeridosPredio(0D);
        //construccion
        totalConsulta.setAreaConstruccionTotal(0D);
        totalConsulta.setEstandarConstruccionTotal(0D);
        totalConsulta.setCumpNormaConstruccionTotal(0D);
        totalConsulta.setM2RequeridosConstruccionTotal(0D);
        // aula
        totalConsulta.setAreaAula(0D);
        totalConsulta.setNumeroAula(0L);
        totalConsulta.setEstandarAula(0D);
        totalConsulta.setCumpNormaAula(0D);
        totalConsulta.setM2RequeridosAula(0D);
        totalConsulta.setEspaciosRequeridosAula(0D);
        // infraestrutura construccion
        totalConsulta.setAreaConstruccionPrimerPiso(0D);
        totalConsulta.setPosConstruccionPrimerPiso(0D);
        totalConsulta.setPosConstruccionAltura(0D);
        totalConsulta.setPosConstruccionTotal(0D);
        totalConsulta.setM2AmpliarCobertura(0D);
        totalConsulta.setAlumnosNuevosInfraestructura(0L);
    }
    
    private void totalizarRegistro(Registro registro){
        // predio
        if (registro.getAreaRealPredio() != null) {
            totalConsulta.setAreaRealPredio(totalConsulta.getAreaRealPredio() + registro.getAreaRealPredio());
        }        
        if (registro.getEstandarPredio() != null) {
            totalConsulta.setEstandarPredio(totalConsulta.getEstandarPredio() + registro.getEstandarPredio());
        }
        if (registro.getCumpNormaPredio() != null) {
            totalConsulta.setCumpNormaPredio(totalConsulta.getCumpNormaPredio() + registro.getCumpNormaPredio());
        }        
        if (registro.getCuposPredio() != null) {
            totalConsulta.setCuposPredio(totalConsulta.getCuposPredio() + registro.getCuposPredio());
        }
        if (registro.getM2RequeridosPredio() != null) {
            totalConsulta.setM2RequeridosPredio(totalConsulta.getM2RequeridosPredio() + registro.getM2RequeridosPredio());
        }        
        //construccion
        if (registro.getAreaConstruccionTotal() != null) {
            totalConsulta.setAreaConstruccionTotal(totalConsulta.getAreaConstruccionTotal() + registro.getAreaConstruccionTotal());
        }
        if (registro.getEstandarConstruccionTotal() != null) {
            totalConsulta.setEstandarConstruccionTotal(totalConsulta.getEstandarConstruccionTotal() + registro.getEstandarConstruccionTotal());
        }
        if (registro.getCumpNormaConstruccionTotal() != null) {
            totalConsulta.setCumpNormaConstruccionTotal(totalConsulta.getCumpNormaConstruccionTotal() + registro.getCumpNormaConstruccionTotal());
        }
        if (registro.getM2RequeridosConstruccionTotal() != null) {
            totalConsulta.setM2RequeridosConstruccionTotal(totalConsulta.getM2RequeridosConstruccionTotal() + registro.getM2RequeridosConstruccionTotal());
        }
        // aula
        if (registro.getAreaAula() != null) {
            totalConsulta.setAreaAula(totalConsulta.getAreaAula() + registro.getAreaAula());
        }
        if (registro.getNumeroAula() != null) {
            totalConsulta.setNumeroAula(totalConsulta.getNumeroAula() + registro.getNumeroAula());
        }
        if (registro.getEstandarAula() != null) {
            totalConsulta.setEstandarAula(totalConsulta.getEstandarAula() + registro.getEstandarAula());
        }
        if (registro.getCumpNormaAula() != null) {
            totalConsulta.setCumpNormaAula(totalConsulta.getCumpNormaAula() + registro.getCumpNormaAula());
        }
        if (registro.getM2RequeridosAula() != null) {
            totalConsulta.setM2RequeridosAula(totalConsulta.getM2RequeridosAula() + registro.getM2RequeridosAula());
        }
        if (registro.getEspaciosRequeridosAula() != null) {
            totalConsulta.setEspaciosRequeridosAula(totalConsulta.getEspaciosRequeridosAula() + registro.getEspaciosRequeridosAula());
        }
        // infraestrutura construccion
        if (registro.getAreaConstruccionPrimerPiso() != null) {
            totalConsulta.setAreaConstruccionPrimerPiso(totalConsulta.getAreaConstruccionPrimerPiso() + registro.getAreaConstruccionPrimerPiso());
        }
        if (registro.getPosConstruccionPrimerPiso() != null) {
            totalConsulta.setPosConstruccionPrimerPiso(totalConsulta.getPosConstruccionPrimerPiso() + registro.getPosConstruccionPrimerPiso());
        }
        if (registro.getPosConstruccionAltura() != null) {
            totalConsulta.setPosConstruccionAltura(totalConsulta.getPosConstruccionAltura() + registro.getPosConstruccionAltura());
        }
        if (registro.getPosConstruccionTotal() != null) {
            totalConsulta.setPosConstruccionTotal(totalConsulta.getPosConstruccionTotal() + registro.getPosConstruccionTotal());
        }
        if (registro.getM2AmpliarCobertura() != null) {
            totalConsulta.setM2AmpliarCobertura(totalConsulta.getM2AmpliarCobertura() + registro.getM2AmpliarCobertura());
        }
        if (registro.getAlumnosNuevosInfraestructura() != null) {
            totalConsulta.setAlumnosNuevosInfraestructura(totalConsulta.getAlumnosNuevosInfraestructura() + registro.getAlumnosNuevosInfraestructura());
        }
    }

    // indicadores  
    // predio
    public boolean isMostrarColPredio() {
        return true;
    }

    // construccion
    public boolean isMostrarColConstruccion() {
        return true;
    }

    // aula
    public boolean isMostrarColAula() {
        return true;
    }

    // Infraestructura Construccion
    public boolean isMostrarColInfraestructuraConstruccion() {
        return true;
    }

    // columnas
    public String getColumnaAreaRealPredio() {
        return columnaAreaRealPredio;
    }

    public String getColumnaEstandarPredio() {
        return columnaEstandarPredio;
    }

    public String getColumnaEstandarLote() {
        return columnaEstandarLote;
    }
    
    public String getColumnaCumpNormaPredio() {
        return columnaCumpNormaPredio;
    }

    public String getColumnaCuposPredio() {
        return columnaCuposPredio;
    }

    public String getColumnaM2RequeridosPredio() {
        return columnaM2RequeridosPredio;
    }

    public String getColumnaAreaConstruccionTotal() {
        return columnaAreaConstruccionTotal;
    }

    public String getColumnaEstandarConstruccionTotal() {
        return columnaEstandarConstruccionTotal;
    }

    public String getColumnaEstandarConst() {
        return columnaEstandarConst;
    }
    
    public String getColumnaCumpNormaConstruccionTotal() {
        return columnaCumpNormaConstruccionTotal;
    }

    public String getColumnaM2RequeridosConstruccionTotal() {
        return columnaM2RequeridosConstruccionTotal;
    }

    public String getColumnaAreaAula() {
        return columnaAreaAula;
    }

    public String getColumnaNumeroAula() {
        return columnaNumeroAula;
    }

    public String getColumnaEstandarAula() {
        return columnaEstandarAula;
    }

    public String getColumnaCumpNormaAula() {
        return columnaCumpNormaAula;
    }

    public String getColumnaM2RequeridosAula() {
        return columnaM2RequeridosAula;
    }

    public String getColumnaEspaciosRequeridosAula() {
        return columnaEspaciosRequeridosAula;
    }

    public String getColumnaPosConstruccionPrimerPiso() {
        return columnaPosConstruccionPrimerPiso;
    }

    public String getColumnaPosConstruccionAltura() {
        return columnaPosConstruccionAltura;
    }

    public String getColumnaPosConstruccionTotal() {
        return columnaPosConstruccionTotal;
    }

    public String getColumnaM2AmpliarCobertura() {
        return columnaM2AmpliarCobertura;
    }

    public String getColumnaAlumnosNuevosInfraestructura() {
        return columnaAlumnosNuevosInfraestructura;
    }

    public FiltroAmbitoAmpliacion getFiltro() {
        return filtro;
    }

    public void setFiltro(FiltroAmbitoAmpliacion filtro) {
        this.filtro = filtro;
    }

    public boolean isCheckCumpAreaPredio() {
        return checkCumpAreaPredio;
    }

    public void setCheckCumpAreaPredio(boolean checkCumpAreaPredio) {
        this.checkCumpAreaPredio = checkCumpAreaPredio;
    }  

    public boolean isCheckCumpAreaConstruccion() {
        return checkCumpAreaConstruccion;
    }

    public void setCheckCumpAreaConstruccion(boolean checkCumpAreaConstruccion) {
        this.checkCumpAreaConstruccion = checkCumpAreaConstruccion;
    }

    public boolean isChackCumpAreaAmbienteAula() {
        return chackCumpAreaAmbienteAula;
    }

    public void setChackCumpAreaAmbienteAula(boolean chackCumpAreaAmbienteAula) {
        this.chackCumpAreaAmbienteAula = chackCumpAreaAmbienteAula;
    }
    
    public ConsultaAmbito getConsultaAmbito() {
        return consultaAmbito;
    }

    public void setConsultaAmbito(ConsultaAmbito consultaAmbito) {
        this.consultaAmbito = consultaAmbito;
    }
}
