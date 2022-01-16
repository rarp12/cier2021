/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.consulta.comparador;

import co.stackpointer.cier.modelo.filtro.ambito.FiltroComparadorInd;
import static co.stackpointer.cier.modelo.tipo.AmbitoComparado.GENERALIDADES;
import static co.stackpointer.cier.modelo.tipo.AmbitoComparado.REDISTRIBUCION;
import co.stackpointer.cier.modelo.tipo.EspacioFuncional;
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
@ManagedBean(name = "comparadorInd")
@ViewScoped
public class ComparadorIndBean extends ConsultaComparador {

    @ManagedProperty(value = "#{consultaComparador}")
    private ConsultaComparador consultaComparador;
    private RegistroGeneralidades totalConsultaGeneralidades;
    private RegistroRedistribuccion totalConsultaRedistribuccion;
    private RegistroAmpliacion totalConsultaAmpliacion;
    private RegistroEdificios totalConsultaEdificios;
    private RegistroEspacios totalConsultaEspacios;
    private RegistroAmbientes totalConsultaAmbientes;
    private List<Registro> lista;
    private FiltroComparadorInd filtro = new FiltroComparadorInd();
    //Columnas Para generalidades
    final String columnaNumJorEst = Utilidades.obtenerMensaje("consultas.ambito.generalidades.columna.num.jor.est");
    final String columnaComparaNumJorEst = Utilidades.obtenerMensaje("consultas.ambito.generalidades.columna.num.jor.est");
    final String columnaNivelEnse = Utilidades.obtenerMensaje("consultas.ambito.generalidades.columna.nivel.ense");
    final String columnaEstuNivelEnse = Utilidades.obtenerMensaje("consultas.ambito.generalidades.columna.estudiantes.nivel.ense");
    final String columnaEstudiantesTotal = Utilidades.obtenerMensaje("consultas.ambito.generalidades.columna.estudiantes.total");
    final String columnaAreaConstruccion = Utilidades.obtenerMensaje("consultas.ambito.generalidades.columna.area.construccion.total");
    final String columnaComparaAreaConstruccion = Utilidades.obtenerMensaje("consultas.ambito.generalidades.columna.area.construccion.total");
    final String columnaNumEstablecimientos = Utilidades.obtenerMensaje("consultas.ambito.generalidades.columna.num.establecimientos");
    final String columnaComparaNumEstablecimientos = Utilidades.obtenerMensaje("consultas.ambito.generalidades.columna.num.establecimientos");
    final String columnaNumSedes = Utilidades.obtenerMensaje("consultas.ambito.generalidades.columna.num.sedes");
    final String columnaComparaNumSedes = Utilidades.obtenerMensaje("consultas.ambito.generalidades.columna.num.sedes");
    final String columnaNumPredios = Utilidades.obtenerMensaje("consultas.ambito.generalidades.columna.num.predios");
    final String columnaComparaNumPredios = Utilidades.obtenerMensaje("consultas.ambito.generalidades.columna.num.predios");
    final String columnaAreaRealPredio = Utilidades.obtenerMensaje("consultas.ambito.generalidades.columna.area.real.predio");
    //Columnas Para redistribuccion
    private final String columnaNumPredioSaturados = Utilidades.obtenerMensaje("consultas.ambito.redistribucion.columna.num.predio.saturados");
    private final String columnaNumPredioSubutilizados = Utilidades.obtenerMensaje("consultas.ambito.redistribucion.columna.num.predio.subutilizados");
    private final String columnaUtilizacionTotal = Utilidades.obtenerMensaje("consultas.ambito.redistribucion.columna.utilizacion.total");
    private final String columnaCuposTotal = Utilidades.obtenerMensaje("consultas.ambito.redistribucion.columna.cupos.total");
    //Columnas para Ambientes
    final String columnaRelacionM2PorAlumno = Utilidades.obtenerMensaje("consultas.ambito.column.metroPorAlumno");
    final String columnaDeficitEstandar = Utilidades.obtenerMensaje("consultas.ambito.column.deficitEstandar");
    final String columnaAcumuladoDeficit = Utilidades.obtenerMensaje("consultas.ambito.column.acumuladoDeficit");
    final String columnaCumplimientoNorma = Utilidades.obtenerMensaje("consultas.ambito.column.cumplimientoNorma");
    final String columnaProporcionEspacios = Utilidades.obtenerMensaje("consultas.ambito.column.proporcionEspacios");
    final String columnaProporcionPredios = Utilidades.obtenerMensaje("consultas.ambito.column.proporcionPredios");
    final String columnaProporcionEstablecimientos = Utilidades.obtenerMensaje("consultas.ambito.column.proporcionEstablecimientos");
    //EDIFICIO
    private final String columnaCondMaterialidadPredio = Utilidades.obtenerMensaje("consultas.ambito.edificios.columna.cod.materialidad.predio");
    private final String columnaEdificioNumEstudiantes = Utilidades.obtenerMensaje("consultas.ambito.generalidades.columna.estudiantes.total");
    private final String columnaNumPredCondMaterialidad = Utilidades.obtenerMensaje("consultas.ambito.edificios.columna.num.pred.cond.mat");
    private final String columnaPropPredCondMaterialidad = Utilidades.obtenerMensaje("consultas.ambito.edificios.columna.prop.pred.cond.mat");
    private final String columnaNumMaterialesCondCriticaPredio = Utilidades.obtenerMensaje("consultas.ambito.edificios.columna.num.mat.cond.critica");
    private final String columnaPropMaterialesCondicionCritica = Utilidades.obtenerMensaje("consultas.ambito.edificios.columna.prop.mat.cond.critica");
    //ESPACIO
    private final String columnaEspaciosPiso = Utilidades.obtenerMensaje("consultas.ambito.espacio.columna.espaciosPiso");
    private final String columnaAreaPiso = Utilidades.obtenerMensaje("consultas.ambito.espacio.columna.areaPiso");
    private final String columnaProporcionAreaPiso = Utilidades.obtenerMensaje("consultas.ambito.espacio.columna.proporcionAreaPiso");
    private final String columnaEspaciosMuro = Utilidades.obtenerMensaje("consultas.ambito.espacio.columna.espaciosMuro");
    private final String columnaAreaMuro = Utilidades.obtenerMensaje("consultas.ambito.espacio.columna.areaMuro");
    private final String columnaProporcionAreaMuro = Utilidades.obtenerMensaje("consultas.ambito.espacio.columna.proporcionAreaMuro");
    private final String columnaEspaciosAcabadoMuro = Utilidades.obtenerMensaje("consultas.ambito.espacio.columna.espaciosAcabadoMuro");
    private final String columnaAreaAcabadoMuro = Utilidades.obtenerMensaje("consultas.ambito.espacio.columna.areaAcabadoMuro");
    private final String columnaProporcionAreaAcabadoMuro = Utilidades.obtenerMensaje("consultas.ambito.espacio.columna.proporcionAreaAcabadoMuro");
    private final String columnaEspaciosVentana = Utilidades.obtenerMensaje("consultas.ambito.espacio.columna.espaciosVentana");
    private final String columnaProporcionVentana = Utilidades.obtenerMensaje("consultas.ambito.espacio.columna.proporcionVentana");
    private final String columnaEspaciosPuerta = Utilidades.obtenerMensaje("consultas.ambito.espacio.columna.espaciosPuerta");
    private final String columnaProporcionPuerta = Utilidades.obtenerMensaje("consultas.ambito.espacio.columna.proporcionPuerta");
    private final String columnaEspaciosTecho = Utilidades.obtenerMensaje("consultas.ambito.espacio.columna.espaciosTecho");
    private final String columnaAreaTecho = Utilidades.obtenerMensaje("consultas.ambito.espacio.columna.areaTecho");
    private final String columnaProporcionAreaTecho = Utilidades.obtenerMensaje("consultas.ambito.espacio.columna.proporcionAreaTecho");
    private final String columnaAreaTotal = Utilidades.obtenerMensaje("consultas.ambito.espacio.columna.areaTotal");
    //Ampliacion
    private final String columnaDePredio = Utilidades.obtenerMensaje("compara.indicadores.columna.ampliacion.columnaDePredio");
    private final String columnaDeConstruccion = Utilidades.obtenerMensaje("compara.indicadores.columna.ampliacion.columnaDeConstruccion");
    private final String columnaDeAula = Utilidades.obtenerMensaje("compara.indicadores.columna.ampliacion.columnaDeAula");
    private final String columnaDePosibilidad = Utilidades.obtenerMensaje("compara.indicadores.columna.ampliacion.columnaDePosibilidad");
    
    @PostConstruct
    public void inicializar() {
        lista = new ArrayList<Registro>();
        this.cargarCamposOpcionales();
    }

    @Override
    public void resetearFiltros() {
        resetearNiveles();
        this.resetearListasEstSedesPredios();
        this.seleccionSector = null;
        this.seleccionZona = null;
        nivelDPAOfAccesoUsuario();
        this.resetearConsulta();
    }
    /**
     * Cuando se cambie el filtro de periodo este reseteara todos los campos parametricos de la consulta a excepcion
     * de los que sonparte de la DPA
     */
    public void resetearFiltrosPeriodo() {
        //resetearNiveles();
        this.resetearListasEstSedesPredios();
        this.seleccionSector = null;
        this.seleccionZona = null;
        //nivelDPAOfAccesoUsuario();
        this.resetearConsulta();
    }

    @Override
    public void cambioAgrupamiento() {
        // filtros
        this.resetearConsulta();
    }

    public void cambioAmbitoComparadoOEspacioFuncional() {
        cargarAgrupamientos();
        this.resetearConsulta();
    } 
    
    public void cambioMaterialidadPredio() {
        this.resetearConsulta();
    } 

    private void resetearConsulta() {
        this.cargarCamposOpcionales();
        this.seleccionCondMaterialidadPredio = null;
        // resultados
        this.lista.clear();
        //this.inicializarTotales();
    }

    public void consultar() {
        filtro.inicializar();
        filtro.setPeriodo(periodoComparado);
        filtro.setPeriodoComparado(periodoBase);
        filtro.setIdioma(getUsuarioSesion().getUsuario().getIdioma().getId());

        filtro.setAgrupamiento(seleccionAgrupamiento);
        filtro.setIdNivel0(consultaComparador.getSeleccionNivel0() != null ? consultaComparador.getSeleccionNivel0().getId() : null);
        filtro.setIdNivel1(consultaComparador.getSeleccionNivel1() != null ? consultaComparador.getSeleccionNivel1().getId() : null);
        filtro.setIdNivel2(consultaComparador.getSeleccionNivel2() != null ? consultaComparador.getSeleccionNivel2().getId() : null);
        filtro.setIdNivel3(consultaComparador.getSeleccionNivel3() != null ? consultaComparador.getSeleccionNivel3().getId() : null);
        filtro.setIdNivel4(consultaComparador.getSeleccionNivel4() != null ? consultaComparador.getSeleccionNivel4().getId() : null);
        filtro.setIdNivel5(consultaComparador.getSeleccionNivel5() != null ? consultaComparador.getSeleccionNivel5().getId() : null);


        filtro.setIdEstablecimiento(seleccionEst != null ? seleccionEst.getId() : null);
        filtro.setIdSede(seleccionSede != null ? seleccionSede.getId() : null);
        filtro.setIdPredio(seleccionPredio != null ? seleccionPredio.getId() : null);
        //otros filtros
        filtro.setCodZona(seleccionZona != null ? seleccionZona.getTipValor().getCodigo() : null);
        filtro.setCodSector(seleccionSector != null ? seleccionSector.getTipValor().getCodigo() : null);
        filtro.setEspacioFuncional(seleccionEspacioFuncional);
        filtro.setCalificacionCondicion(String.valueOf(seleccionCondicion.getValor()));
        filtro.setSwPosibilidades(isMostrarCamposOpcional(columnaDePosibilidad));
        filtro.setSwConstruccion(isMostrarCamposOpcional(columnaDeConstruccion));
        filtro.setSwAula(isMostrarCamposOpcional(columnaDeAula));
        filtro.setSwPredio(isMostrarCamposOpcional(columnaDePredio));

        List<Object[]> resultados;
        switch (ambitoComparado) {
            case GENERALIDADES:
                resultados = facComparador.consultarGeneralidades(filtro);
                resultadosGeneralidades(resultados);
                break;
            case REDISTRIBUCION:
                resultados = facComparador.consultarRedistribucion(filtro);
                resultadosRedistribuccion(resultados);
                break;
            case AMBIENTES:
                resultados = facComparador.consultarAmbientes(filtro);
                resultadosAmbientes(resultados);
                break;
            case EDIFICIOS:
                resultados = facComparador.consultarEdificios(filtro);
                resultadosEdificios(resultados);
                break;
            case ESPACIOS:
                resultados = facComparador.consultarEspacios(filtro);
                resultadosEspacios(resultados);
                break;    
            case AMPLIACION:
                resultados = facComparador.consultarAmpliacion(filtro);
                resultadosAmpliacion(resultados);
                break;
        }

        if (lista.size() < 1) {
            mensajeTablaVacia = Utilidades.obtenerMensaje("aplicacion.general.noExistenRegistros");
        }
    }

    private void resultadosGeneralidades(List<Object[]> resultados) {
        lista = new ArrayList<Registro>(resultados.size());
        totalConsultaGeneralidades = new RegistroGeneralidades();
        totalConsultaGeneralidades.inicializarTotales();

        for (Object[] fila : resultados) {
            RegistroGeneralidades registro = new RegistroGeneralidades();
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
                registro.setZona(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]), getUsuarioSesion().getUsuario().getIdioma().getId()));
                registro.setSector(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]), getUsuarioSesion().getUsuario().getIdioma().getId()));
                registro.setClima(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]), getUsuarioSesion().getUsuario().getIdioma().getId()));
                registro.setEtnias(verificarEtnias(UtilConsulta.stringValueOf(fila[i++])));
                // indicadores
                registro.setNumJornadasEstudiantes(UtilConsulta.longValueOf(fila[i++]));
                registro.setComparaNumJornadasEstudiantes(UtilConsulta.longValueOf(fila[i++]));
                registro.setTotalEstudiantes(UtilConsulta.longValueOf(fila[i++]));
                registro.setComparaTotalEstudiantes(UtilConsulta.longValueOf(fila[i++]));
                registro.setAreaConstruccion(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setComparaAreaConstruccion(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setAreaRealPredio(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setComparaAreaRealPredio(UtilConsulta.doubleValueOf(fila[i++]));
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
                registro.setTotalEstudiantes(UtilConsulta.longValueOf(fila[i++]));
                registro.setComparaTotalEstudiantes(UtilConsulta.longValueOf(fila[i++]));
                if (!filtro.isAgrupamientoSede()) {
                    if (!filtro.isAgrupamientoEstablecimiento()) {
                        registro.setNumEstablecimientos(UtilConsulta.longValueOf(fila[i++]));
                        registro.setComparaNumEstablecimientos(UtilConsulta.longValueOf(fila[i++]));
                    }
                    registro.setNumSedes(UtilConsulta.longValueOf(fila[i++]));
                    registro.setComparaNumSedes(UtilConsulta.longValueOf(fila[i++]));
                }
                registro.setNumPredios(UtilConsulta.longValueOf(fila[i++]));
                registro.setComparaNumPredios(UtilConsulta.longValueOf(fila[i++]));
            }
            // totales    
            totalizarRegistro(registro);
            lista.add(registro);
        }
    }

    private void resultadosRedistribuccion(List<Object[]> resultados) {
        lista = new ArrayList<Registro>(resultados.size());
        totalConsultaRedistribuccion = new RegistroRedistribuccion();
        totalConsultaRedistribuccion.inicializarTotales();

        for (Object[] fila : resultados) {
            RegistroRedistribuccion registro = new RegistroRedistribuccion();
            int i = 0;
            if (filtro.isAgrupamientoPredio()) {
                registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNivel2(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNivel3(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNivel4(UtilConsulta.stringValueOf(fila[i++]));
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
                registro.setZona(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]), getUsuarioSesion().getUsuario().getIdioma().getId()));
                registro.setSector(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]), getUsuarioSesion().getUsuario().getIdioma().getId()));
                registro.setClima(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]), getUsuarioSesion().getUsuario().getIdioma().getId()));
                registro.setEtnias(verificarEtnias(UtilConsulta.stringValueOf(fila[i++])));
                //indicadores
                registro.setUtilEstandarTotal(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setCuposTotal(UtilConsulta.doubleValueOf(fila[i++]));
            } else {
                if (filtro.isAgrupamientoNivel0()) {
                    registro.setNivel0(UtilConsulta.stringValueOf(fila[i++]));
                } else if (filtro.isAgrupamientoNivel1()) {
                    registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                } else if (filtro.isAgrupamientoNivel2()) {
                    registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel2(UtilConsulta.stringValueOf(fila[i++]));
                } else if (filtro.isAgrupamientoNivel3()) {
                    registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel2(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel3(UtilConsulta.stringValueOf(fila[i++]));
                } else if (filtro.isAgrupamientoNivel4()) {
                    registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel2(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel3(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel4(UtilConsulta.stringValueOf(fila[i++]));
                } else if (filtro.isAgrupamientoNivel5()) {
                    registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel2(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel3(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel4(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel5(UtilConsulta.stringValueOf(fila[i++]));
                } else if (filtro.isAgrupamientoEstablecimiento()) {
                    registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel2(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel3(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel4(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel5(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCodEstablecimiento(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNomEstablecimiento(UtilConsulta.stringValueOf(fila[i++]));
                } else if (filtro.isAgrupamientoSede()) {
                    registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel2(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel3(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel4(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel5(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCodEstablecimiento(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNomEstablecimiento(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCodSede(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNomSede(UtilConsulta.stringValueOf(fila[i++]));
                }
                // indicadores             
                registro.setNumPrediosSaturados(UtilConsulta.longValueOf(fila[i++]));
                registro.setNumPrediosSudUtilizados(UtilConsulta.longValueOf(fila[i++]));
                registro.setUtilEstandarTotal(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setCuposTotal(UtilConsulta.doubleValueOf(fila[i++]));
            }
            // totales       
            totalizarRegistro(registro);
            lista.add(registro);
        }
    }

    private void resultadosAmbientes(List<Object[]> resultados) {
        lista = new ArrayList<Registro>(resultados.size());
        //totalConsultaAmbientes = new RegistroAmbientes();
        //totalConsultaAmbientes.inicializarTotales();

        for (Object[] fila : resultados) {
            RegistroAmbientes registro = new RegistroAmbientes();
            int i = 0;
            if (filtro.isAgrupamientoPredio()) {
                registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNivel2(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNivel3(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNivel4(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNivel5(UtilConsulta.stringValueOf(fila[i++]));
                registro.setCodEstablecimiento(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNomEstablecimiento(UtilConsulta.stringValueOf(fila[i++]));
                registro.setCodSede(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNomSede(UtilConsulta.stringValueOf(fila[i++]));
                registro.setCodPredio(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNomPredio(UtilConsulta.stringValueOf(fila[i++]));
                // indicadores                
                registro.setProporcionEspacios(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setComparaProporcionEspacios(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setRelacionM2PorAlumno(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setComparaRelacionM2PorAlumno(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setCondicionM2alumno(UtilConsulta.stringValueOf(fila[i++]));
                registro.setComparaCondicionM2alumno(UtilConsulta.stringValueOf(fila[i++]));
                registro.setDeficitEstandar(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setComparaDeficitEstandar(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setCumplimientoNorma(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setComparaCumplimientoNorma(UtilConsulta.doubleValueOf(fila[i++]));
                //otros
                registro.setDirPredio(UtilConsulta.stringValueOf(fila[i++]));
                registro.setZona(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]), getUsuarioSesion().getUsuario().getIdioma().getId()));
                registro.setSector(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]), getUsuarioSesion().getUsuario().getIdioma().getId()));
                registro.setClima(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]), getUsuarioSesion().getUsuario().getIdioma().getId()));
            } else {
                if (filtro.isAgrupamientoEstablecimiento() || filtro.isAgrupamientoSede()) {
                    registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel2(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel3(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel4(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel5(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCodEstablecimiento(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNomEstablecimiento(UtilConsulta.stringValueOf(fila[i++]));
                    if (filtro.isAgrupamientoSede()) {
                        registro.setCodSede(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setNomSede(UtilConsulta.stringValueOf(fila[i++]));
                    }
                    // indicadores                    
                    registro.setProporcionEspacios(UtilConsulta.doubleValueOf(fila[i++]));
                    registro.setComparaProporcionEspacios(UtilConsulta.doubleValueOf(fila[i++]));
                    if (filtro.getEspacioFuncional().equals(EspacioFuncional.BIBLIOTECA)) {
                        registro.setRelacionM2PorAlumno(UtilConsulta.doubleValueOf(fila[i++]));
                        registro.setComparaRelacionM2PorAlumno(UtilConsulta.doubleValueOf(fila[i++]));
                        registro.setCondicionM2alumno(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setComparaCondicionM2alumno(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setDeficitEstandar(UtilConsulta.doubleValueOf(fila[i++]));
                        registro.setComparaDeficitEstandar(UtilConsulta.doubleValueOf(fila[i++]));
                    } else {
                        registro.setProporcionPredios(UtilConsulta.doubleValueOf(fila[i++]));
                        registro.setComparaProporcionPredios(UtilConsulta.doubleValueOf(fila[i++]));
                        registro.setAcumuladoDeficit(UtilConsulta.doubleValueOf(fila[i++]));
                        registro.setComparaAcumuladoDeficit(UtilConsulta.doubleValueOf(fila[i++]));
                    }
                    registro.setCumplimientoNorma(UtilConsulta.doubleValueOf(fila[i++]));
                    registro.setComparaCumplimientoNorma(UtilConsulta.doubleValueOf(fila[i++]));
                } else {
                    if (filtro.isAgrupamientoNivel0()) {
                        registro.setNivel0(UtilConsulta.stringValueOf(fila[i++]));
                    } else if (filtro.isAgrupamientoNivel1()) {
                        registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                    } else if (filtro.isAgrupamientoNivel2()) {
                        registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setNivel2(UtilConsulta.stringValueOf(fila[i++]));
                    } else if (filtro.isAgrupamientoNivel3()) {
                        registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setNivel2(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setNivel3(UtilConsulta.stringValueOf(fila[i++]));
                    } else if (filtro.isAgrupamientoNivel4()) {
                        registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setNivel2(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setNivel3(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setNivel4(UtilConsulta.stringValueOf(fila[i++]));
                    } else if (filtro.isAgrupamientoNivel5()) {
                        registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setNivel2(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setNivel3(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setNivel4(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setNivel5(UtilConsulta.stringValueOf(fila[i++]));
                    }
                    // indicadores                    
                    registro.setProporcionEstablecimientos(UtilConsulta.doubleValueOf(fila[i++]));
                    registro.setComparaProporcionEstablecimientos(UtilConsulta.doubleValueOf(fila[i++]));
                    if (!filtro.getEspacioFuncional().equals(EspacioFuncional.BIBLIOTECA)) {
                        registro.setProporcionPredios(UtilConsulta.doubleValueOf(fila[i++]));
                        registro.setComparaProporcionPredios(UtilConsulta.doubleValueOf(fila[i++]));
                    }
                    registro.setAcumuladoDeficit(UtilConsulta.doubleValueOf(fila[i++]));
                    registro.setComparaAcumuladoDeficit(UtilConsulta.doubleValueOf(fila[i++]));
                    registro.setCumplimientoNorma(UtilConsulta.doubleValueOf(fila[i++]));
                    registro.setComparaCumplimientoNorma(UtilConsulta.doubleValueOf(fila[i++]));
                }
            }
            //totalizarRegistro(registro);
            lista.add(registro);
        }
    }

    private void resultadosEdificios(List<Object[]> resultados) {
        lista = new ArrayList<Registro>(resultados.size());
        totalConsultaEdificios = new RegistroEdificios();
        totalConsultaEdificios.inicializarTotales();

        for (Object[] fila : resultados) {
            RegistroEdificios registro = new RegistroEdificios();
            int i = 0;
            if (filtro.isAgrupamientoPredio()) {
                registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNivel2(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNivel3(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNivel4(UtilConsulta.stringValueOf(fila[i++]));
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
                registro.setZona(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]), getUsuarioSesion().getUsuario().getIdioma().getId()));
                registro.setSector(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]), getUsuarioSesion().getUsuario().getIdioma().getId()));
                registro.setClima(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]), getUsuarioSesion().getUsuario().getIdioma().getId()));
                registro.setEtnias(verificarEtnias(UtilConsulta.stringValueOf(fila[i++])));
                //indicadores 
                registro.setCondMaterialidadPredio(Utilidades.obtenerMensaje(UtilConsulta.stringValueOf(fila[i++])));
                registro.setComparaCondMaterialidadPredio(Utilidades.obtenerMensaje(UtilConsulta.stringValueOf(fila[i++])));
                registro.setNumEstudiantes(UtilConsulta.longValueOf(fila[i++]));
                registro.setComparaNumEstudiantes(UtilConsulta.longValueOf(fila[i++]));
            } else {
                if (filtro.isAgrupamientoNivel0()) {
                    registro.setNivel0(UtilConsulta.stringValueOf(fila[i++]));
                } else if (filtro.isAgrupamientoNivel1()) {
                    registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                } else if (filtro.isAgrupamientoNivel2()) {
                    registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel2(UtilConsulta.stringValueOf(fila[i++]));
                } else if (filtro.isAgrupamientoNivel3()) {
                    registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel2(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel3(UtilConsulta.stringValueOf(fila[i++]));
                } else if (filtro.isAgrupamientoNivel4()) {
                    registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel2(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel3(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel4(UtilConsulta.stringValueOf(fila[i++]));
                } else if (filtro.isAgrupamientoNivel5()) {
                    registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel2(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel3(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel4(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel5(UtilConsulta.stringValueOf(fila[i++]));
                } else if (filtro.isAgrupamientoEstablecimiento()) {
                    registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel2(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel3(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel4(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel5(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCodEstablecimiento(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNomEstablecimiento(UtilConsulta.stringValueOf(fila[i++]));
                } else if (filtro.isAgrupamientoSede()) {
                    registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel2(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel3(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel4(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel5(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCodEstablecimiento(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNomEstablecimiento(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCodSede(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNomSede(UtilConsulta.stringValueOf(fila[i++]));
                }
                // indicadores     
                if (filtro.getCondMaterialidadPredio() != null) {
                    registro.setNumPredCondMaterialidad(UtilConsulta.longValueOf(fila[i++]));
                    registro.setComparaNumPredCondMaterialidad(UtilConsulta.longValueOf(fila[i++]));
                    registro.setPropPredCondMaterialidad(UtilConsulta.doubleValueOf(fila[i++]));
                    registro.setComparaPropPredCondMaterialidad(UtilConsulta.doubleValueOf(fila[i++]));
                }
                registro.setTotalPredios(UtilConsulta.longValueOf(fila[i++]));
                registro.setComparaTotalPredios(UtilConsulta.longValueOf(fila[i++]));
                registro.setNumMaterialesCondCriticaPredio(UtilConsulta.longValueOf(fila[i++]));
                registro.setComparaNumMaterialesCondCriticaPredio(UtilConsulta.longValueOf(fila[i++]));
                registro.setPropMaterialesCondicionCritica(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setComparaPropMaterialesCondicionCritica(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setNumEstudiantes(UtilConsulta.longValueOf(fila[i++]));
                registro.setComparaNumEstudiantes(UtilConsulta.longValueOf(fila[i++]));
            }
            // totales
            this.totalizarRegistro(registro);
            lista.add(registro);
        }
    }

    private void resultadosEspacios(List<Object[]> resultados) {
        lista = new ArrayList<Registro>(resultados.size());
        totalConsultaEspacios = new RegistroEspacios();
        totalConsultaEspacios.inicializarTotales();

        for (Object[] fila : resultados) {
            RegistroEspacios registro = new RegistroEspacios();
            int i = 0;
            if (filtro.isAgrupamientoNivel0()) {
                registro.setNivel0(UtilConsulta.stringValueOf(fila[i++]));
            } else if (filtro.isAgrupamientoNivel1()) {
                registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
            } else if (filtro.isAgrupamientoNivel2()) {
                registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNivel2(UtilConsulta.stringValueOf(fila[i++]));
            } else if (filtro.isAgrupamientoNivel3()) {
                registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNivel2(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNivel3(UtilConsulta.stringValueOf(fila[i++]));
            } else if (filtro.isAgrupamientoNivel4()) {
                registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNivel2(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNivel3(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNivel4(UtilConsulta.stringValueOf(fila[i++]));
            } else if (filtro.isAgrupamientoNivel5()) {
                registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNivel2(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNivel3(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNivel4(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNivel5(UtilConsulta.stringValueOf(fila[i++]));
            } else if (filtro.isAgrupamientoEstablecimiento()) {
                registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNivel2(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNivel3(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNivel4(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNivel5(UtilConsulta.stringValueOf(fila[i++]));
                registro.setCodEstablecimiento(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNomEstablecimiento(UtilConsulta.stringValueOf(fila[i++]));
            } else if (filtro.isAgrupamientoSede()) {
                registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNivel2(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNivel3(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNivel4(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNivel5(UtilConsulta.stringValueOf(fila[i++]));
                registro.setCodEstablecimiento(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNomEstablecimiento(UtilConsulta.stringValueOf(fila[i++]));
                registro.setCodSede(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNomSede(UtilConsulta.stringValueOf(fila[i++]));
            } else if (filtro.isAgrupamientoPredio()) {
                registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNivel2(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNivel3(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNivel4(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNivel5(UtilConsulta.stringValueOf(fila[i++]));
                registro.setCodEstablecimiento(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNomEstablecimiento(UtilConsulta.stringValueOf(fila[i++]));
                registro.setCodSede(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNomSede(UtilConsulta.stringValueOf(fila[i++]));
                registro.setCodPredio(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNomPredio(UtilConsulta.stringValueOf(fila[i++]));
                //otros
                registro.setDirPredio(UtilConsulta.stringValueOf(fila[i++]));
                registro.setZona(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]), getUsuarioSesion().getUsuario().getIdioma().getId()));
                registro.setSector(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]), getUsuarioSesion().getUsuario().getIdioma().getId()));
                registro.setClima(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]), getUsuarioSesion().getUsuario().getIdioma().getId()));
            }
            // indicadores
            registro.setEspaciosPiso(UtilConsulta.longValueOf(fila[i++]));
            registro.setComparaEspaciosPiso(UtilConsulta.longValueOf(fila[i++]));
            registro.setAreaPiso(UtilConsulta.doubleValueOf(fila[i++]));
            registro.setComparaAreaPiso(UtilConsulta.doubleValueOf(fila[i++]));
            registro.setProporcionAreaPiso(UtilConsulta.doubleValueOf(fila[i++]));
            registro.setComparaProporcionAreaPiso(UtilConsulta.doubleValueOf(fila[i++]));
            registro.setEspaciosMuro(UtilConsulta.longValueOf(fila[i++]));
            registro.setComparaEspaciosMuro(UtilConsulta.longValueOf(fila[i++]));
            registro.setAreaMuro(UtilConsulta.doubleValueOf(fila[i++]));
            registro.setComparaAreaMuro(UtilConsulta.doubleValueOf(fila[i++]));
            registro.setProporcionAreaMuro(UtilConsulta.doubleValueOf(fila[i++]));
            registro.setComparaProporcionAreaMuro(UtilConsulta.doubleValueOf(fila[i++]));
            registro.setEspaciosAcabadoMuro(UtilConsulta.longValueOf(fila[i++]));
            registro.setComparaEspaciosAcabadoMuro(UtilConsulta.longValueOf(fila[i++]));
            registro.setAreaAcabadoMuro(UtilConsulta.doubleValueOf(fila[i++]));
            registro.setComparaAreaAcabadoMuro(UtilConsulta.doubleValueOf(fila[i++]));
            registro.setProporcionAreaAcabadoMuro(UtilConsulta.doubleValueOf(fila[i++]));
            registro.setComparaProporcionAreaAcabadoMuro(UtilConsulta.doubleValueOf(fila[i++]));
            registro.setEspaciosVentana(UtilConsulta.longValueOf(fila[i++]));
            registro.setComparaEspaciosVentana(UtilConsulta.longValueOf(fila[i++]));
            registro.setProporcionVentana(UtilConsulta.doubleValueOf(fila[i++]));
            registro.setComparaProporcionVentana(UtilConsulta.doubleValueOf(fila[i++]));
            registro.setEspaciosPuerta(UtilConsulta.longValueOf(fila[i++]));
            registro.setComparaEspaciosPuerta(UtilConsulta.longValueOf(fila[i++]));
            registro.setProporcionPuerta(UtilConsulta.doubleValueOf(fila[i++]));
            registro.setComparaProporcionPuerta(UtilConsulta.doubleValueOf(fila[i++]));
            registro.setEspaciosTecho(UtilConsulta.longValueOf(fila[i++]));
            registro.setComparaEspaciosTecho(UtilConsulta.longValueOf(fila[i++]));
            registro.setAreaTecho(UtilConsulta.doubleValueOf(fila[i++]));
            registro.setComparaAreaTecho(UtilConsulta.doubleValueOf(fila[i++]));
            registro.setProporcionAreaTecho(UtilConsulta.doubleValueOf(fila[i++]));
            registro.setComparaProporcionAreaTecho(UtilConsulta.doubleValueOf(fila[i++]));
            registro.setAreaTotal(UtilConsulta.doubleValueOf(fila[i++]));
            registro.setComparaAreaTotal(UtilConsulta.doubleValueOf(fila[i++]));
            //totaliza registros
            totalizarRegistro(registro);
            lista.add(registro);
        }
    }
     
    private void resultadosAmpliacion(List<Object[]> resultados) {
        lista = new ArrayList<Registro>(resultados.size());
        totalConsultaAmpliacion = new RegistroAmpliacion();
        totalConsultaAmpliacion.inicializarTotales();

        for (Object[] fila : resultados) {
            RegistroAmpliacion registro = new RegistroAmpliacion();
            int i = 0;
            if (filtro.isAgrupamientoPredio()) {
                registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNivel2(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNivel3(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNivel4(UtilConsulta.stringValueOf(fila[i++]));
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
                registro.setZona(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]), getUsuarioSesion().getUsuario().getIdioma().getId()));
                registro.setSector(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]), getUsuarioSesion().getUsuario().getIdioma().getId()));
                registro.setClima(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]), getUsuarioSesion().getUsuario().getIdioma().getId()));
                registro.setEtnias(verificarEtnias(UtilConsulta.stringValueOf(fila[i++])));
            } else {
                if (filtro.isAgrupamientoNivel0()) {
                    registro.setNivel0(UtilConsulta.stringValueOf(fila[i++]));
                } else if (filtro.isAgrupamientoNivel1()) {
                    registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                } else if (filtro.isAgrupamientoNivel2()) {
                    registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel2(UtilConsulta.stringValueOf(fila[i++]));
                } else if (filtro.isAgrupamientoNivel3()) {
                    registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel2(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel3(UtilConsulta.stringValueOf(fila[i++]));
                } else if (filtro.isAgrupamientoNivel4()) {
                    registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel2(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel3(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel4(UtilConsulta.stringValueOf(fila[i++]));
                } else if (filtro.isAgrupamientoNivel5()) {
                    registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel2(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel3(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel4(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel5(UtilConsulta.stringValueOf(fila[i++]));
                } else if (filtro.isAgrupamientoEstablecimiento()) {
                    registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel2(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel3(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel4(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel5(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCodEstablecimiento(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNomEstablecimiento(UtilConsulta.stringValueOf(fila[i++]));
                } else if (filtro.isAgrupamientoSede()) {
                    registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel2(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel3(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel4(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNivel5(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCodEstablecimiento(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNomEstablecimiento(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCodSede(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNomSede(UtilConsulta.stringValueOf(fila[i++]));
                }
            }
            // indicadores  
            // predio
            if (filtro.isSwPredio()) {
                registro.setAreaRealPredio(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setComparaAreaRealPredio(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setEstandarPredio(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setComparaEstandarPredio(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setEstandarLote(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setComparaEstandarLote(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setCumpNormaPredio(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setComparaCumpNormaPredio(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setCuposPredio(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setComparaCuposPredio(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setM2RequeridosPredio(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setComparaM2RequeridosPredio(UtilConsulta.doubleValueOf(fila[i++]));
            }

            // construccion
            if (filtro.isSwConstruccion()) {
                registro.setAreaConstruccionTotal(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setComparaAreaConstruccionTotal(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setEstandarConstruccionTotal(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setComparaEstandarConstruccionTotal(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setEstandarConst(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setComparaEstandarConst(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setCumpNormaConstruccionTotal(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setComparaCumpNormaConstruccionTotal(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setM2RequeridosConstruccionTotal(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setComparaM2RequeridosConstruccionTotal(UtilConsulta.doubleValueOf(fila[i++]));
            }

            // aula
            if (filtro.isSwAula()) {
                registro.setAreaAula(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setComparaAreaAula(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setNumeroAula(UtilConsulta.longValueOf(fila[i++]));
                registro.setComparaNumeroAula(UtilConsulta.longValueOf(fila[i++]));
                registro.setEstandarAula(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setComparaEstandarAula(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setCumpNormaAula(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setComparaCumpNormaAula(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setM2RequeridosAula(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setComparaM2RequeridosAula(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setEspaciosRequeridosAula(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setComparaEspaciosRequeridosAula(UtilConsulta.doubleValueOf(fila[i++]));
            }

            // infraestructura de construccion
            if (filtro.isSwPosibilidades()) {
                registro.setAreaConstruccionPrimerPiso(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setComparaAreaConstruccionPrimerPiso(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setPosConstruccionPrimerPiso(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setComparaPosConstruccionPrimerPiso(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setPosConstruccionAltura(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setComparaPosConstruccionAltura(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setPosConstruccionTotal(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setComparaPosConstruccionTotal(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setM2AmpliarCobertura(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setComparaM2AmpliarCobertura(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setAlumnosNuevosInfraestructura(UtilConsulta.longValueOf(fila[i++]));
                registro.setComparaAlumnosNuevosInfraestructura(UtilConsulta.longValueOf(fila[i++]));
            }

            // totales
            this.totalizarRegistro(registro);
            lista.add(registro);
        }
    }

    public List<? extends Registro> getLista() {
        return lista;
    }

    public RegistroGeneralidades getTotalConsultaGeneralidades() {
        return totalConsultaGeneralidades;
    }

    public RegistroRedistribuccion getTotalConsultaRedistribuccion() {
        return totalConsultaRedistribuccion;
    }

    public RegistroAmpliacion getTotalConsultaAmpliacion() {
        return totalConsultaAmpliacion;
    }
    
    public RegistroEdificios getTotalConsultaEdificios() {
        return totalConsultaEdificios;
    }

    public RegistroEspacios getTotalConsultaEspacios() {
        return totalConsultaEspacios;
    }

    public RegistroAmbientes getTotalConsultaAmbientes() {
        return totalConsultaAmbientes;
    }
    
    private void totalizarRegistro(Registro registro) {
        //if(registro es instancia de registroGeneralidaes)
        if (registro instanceof RegistroGeneralidades) {
            RegistroGeneralidades registroGeneralidades = (RegistroGeneralidades) registro;
            if (registroGeneralidades.getNumJornadasEstudiantes() != null) {
                totalConsultaGeneralidades.setNumJornadasEstudiantes(totalConsultaGeneralidades.getNumJornadasEstudiantes() + registroGeneralidades.getNumJornadasEstudiantes());
            }            
            if (registroGeneralidades.getNumEstNivelEnseñanza() != null) {
                totalConsultaGeneralidades.setNumEstNivelEnseñanza(totalConsultaGeneralidades.getNumEstNivelEnseñanza() + registroGeneralidades.getNumEstNivelEnseñanza());
            }
            if (registroGeneralidades.getTotalEstudiantes() != null) {
                totalConsultaGeneralidades.setTotalEstudiantes(totalConsultaGeneralidades.getTotalEstudiantes() + registroGeneralidades.getTotalEstudiantes());
            }
            if (registroGeneralidades.getAreaConstruccion() != null) {
                totalConsultaGeneralidades.setAreaConstruccion(totalConsultaGeneralidades.getAreaConstruccion() + registroGeneralidades.getAreaConstruccion());
            }
            if (registroGeneralidades.getAreaRealPredio() != null) {
                totalConsultaGeneralidades.setAreaRealPredio(totalConsultaGeneralidades.getAreaRealPredio() + registroGeneralidades.getAreaRealPredio());
            }
            if (registroGeneralidades.getNumEstablecimientos() != null) {
                totalConsultaGeneralidades.setNumEstablecimientos(totalConsultaGeneralidades.getNumEstablecimientos() + registroGeneralidades.getNumEstablecimientos());
            }
            if (registroGeneralidades.getNumSedes() != null) {
                totalConsultaGeneralidades.setNumSedes(totalConsultaGeneralidades.getNumSedes() + registroGeneralidades.getNumSedes());
            }
            if (registroGeneralidades.getNumPredios() != null) {
                totalConsultaGeneralidades.setNumPredios(totalConsultaGeneralidades.getNumPredios() + registroGeneralidades.getNumPredios());
            }
            if (registroGeneralidades.getComparaNumJornadasEstudiantes() != null) {
                totalConsultaGeneralidades.setComparaNumJornadasEstudiantes(totalConsultaGeneralidades.getComparaNumJornadasEstudiantes() + registroGeneralidades.getComparaNumJornadasEstudiantes());
            }            
            if (registroGeneralidades.getComparaTotalEstudiantes() != null) {
                totalConsultaGeneralidades.setComparaTotalEstudiantes(totalConsultaGeneralidades.getComparaTotalEstudiantes() + registroGeneralidades.getComparaTotalEstudiantes());
            }
            if (registroGeneralidades.getComparaAreaConstruccion() != null) {
                totalConsultaGeneralidades.setComparaAreaConstruccion(totalConsultaGeneralidades.getComparaAreaConstruccion() + registroGeneralidades.getComparaAreaConstruccion());
            }         
            if (registroGeneralidades.getComparaAreaRealPredio() != null) {
                totalConsultaGeneralidades.setComparaAreaRealPredio(totalConsultaGeneralidades.getComparaAreaRealPredio() + registroGeneralidades.getComparaAreaRealPredio());
            }
            if (registroGeneralidades.getComparaNumEstablecimientos() != null) {
                totalConsultaGeneralidades.setComparaNumEstablecimientos(totalConsultaGeneralidades.getComparaNumEstablecimientos() + registroGeneralidades.getComparaNumEstablecimientos());
            }
            if (registroGeneralidades.getComparaNumSedes() != null) {
                totalConsultaGeneralidades.setComparaNumSedes(totalConsultaGeneralidades.getComparaNumSedes() + registroGeneralidades.getComparaNumSedes());
            }
            if (registroGeneralidades.getComparaNumPredios() != null) {
                totalConsultaGeneralidades.setComparaNumPredios(totalConsultaGeneralidades.getComparaNumPredios() + registroGeneralidades.getComparaNumPredios());
            }
        }
        if (registro instanceof RegistroEdificios) {
            RegistroEdificios registroEdificio= (RegistroEdificios) registro;
            if (registroEdificio.getNumEstudiantes()!= null) {
                totalConsultaEdificios.setNumEstudiantes(totalConsultaEdificios.getNumEstudiantes() + registroEdificio.getNumEstudiantes());
            }
            if (registroEdificio.getNumMaterialesCondCriticaPredio()!= null) {
                totalConsultaEdificios.setNumMaterialesCondCriticaPredio(totalConsultaEdificios.getNumMaterialesCondCriticaPredio() + registroEdificio.getNumMaterialesCondCriticaPredio());
            }
            if (registroEdificio.getNumPredCondMaterialidad()!= null) {
                totalConsultaEdificios.setNumPredCondMaterialidad(totalConsultaEdificios.getNumPredCondMaterialidad() + registroEdificio.getNumPredCondMaterialidad());
            }
            if (registroEdificio.getTotalPredios()!= null) {
                totalConsultaEdificios.setTotalPredios(totalConsultaEdificios.getTotalPredios() + registroEdificio.getTotalPredios());
            }
            if (registroEdificio.getComparaNumEstudiantes()!= null) {
                totalConsultaEdificios.setComparaNumEstudiantes(totalConsultaEdificios.getComparaNumEstudiantes() + registroEdificio.getComparaNumEstudiantes());
            }
            if (registroEdificio.getComparaNumMaterialesCondCriticaPredio()!= null) {
                totalConsultaEdificios.setComparaNumMaterialesCondCriticaPredio(totalConsultaEdificios.getComparaNumMaterialesCondCriticaPredio() + registroEdificio.getComparaNumMaterialesCondCriticaPredio());
            }
            if (registroEdificio.getComparaNumPredCondMaterialidad()!= null) {
                totalConsultaEdificios.setComparaNumPredCondMaterialidad(totalConsultaEdificios.getComparaNumPredCondMaterialidad() + registroEdificio.getComparaNumPredCondMaterialidad());
            }
            if (registroEdificio.getComparaTotalPredios()!= null) {
                totalConsultaEdificios.setComparaTotalPredios(totalConsultaEdificios.getComparaTotalPredios() + registroEdificio.getComparaTotalPredios());
            }
        }
        if (registro instanceof RegistroEspacios) {
            RegistroEspacios registroEspacio = (RegistroEspacios) registro;
            if (registroEspacio.getEspaciosPiso() != null) {
                totalConsultaEspacios.setEspaciosPiso(totalConsultaEspacios.getEspaciosPiso() + registroEspacio.getEspaciosPiso());
            }
            if (registroEspacio.getAreaPiso() != null) {
                totalConsultaEspacios.setAreaPiso(totalConsultaEspacios.getAreaPiso() + registroEspacio.getAreaPiso());
            }
            if (registroEspacio.getComparaEspaciosPiso() != null) {
                totalConsultaEspacios.setComparaEspaciosPiso(totalConsultaEspacios.getComparaEspaciosPiso() + registroEspacio.getComparaEspaciosPiso());
            }
            if (registroEspacio.getComparaAreaPiso() != null) {
                totalConsultaEspacios.setComparaAreaPiso(totalConsultaEspacios.getComparaAreaPiso() + registroEspacio.getComparaAreaPiso());
            }
            if (registroEspacio.getEspaciosMuro() != null) {
                totalConsultaEspacios.setEspaciosMuro(totalConsultaEspacios.getEspaciosMuro() + registroEspacio.getEspaciosMuro());
            }
            if (registroEspacio.getAreaMuro() != null) {
                totalConsultaEspacios.setAreaMuro(totalConsultaEspacios.getAreaMuro() + registroEspacio.getAreaMuro());
            }
            if (registroEspacio.getComparaEspaciosMuro() != null) {
                totalConsultaEspacios.setComparaEspaciosMuro(totalConsultaEspacios.getComparaEspaciosMuro() + registroEspacio.getComparaEspaciosMuro());
            }
            if (registroEspacio.getComparaAreaMuro() != null) {
                totalConsultaEspacios.setComparaAreaMuro(totalConsultaEspacios.getComparaAreaMuro() + registroEspacio.getComparaAreaMuro());
            }
            if (registroEspacio.getEspaciosAcabadoMuro() != null) {
                totalConsultaEspacios.setEspaciosAcabadoMuro(totalConsultaEspacios.getEspaciosAcabadoMuro() + registroEspacio.getEspaciosAcabadoMuro());
            }
            if (registroEspacio.getAreaAcabadoMuro() != null) {
                totalConsultaEspacios.setAreaAcabadoMuro(totalConsultaEspacios.getAreaAcabadoMuro() + registroEspacio.getAreaAcabadoMuro());
            }
            if (registroEspacio.getComparaEspaciosAcabadoMuro() != null) {
                totalConsultaEspacios.setComparaEspaciosAcabadoMuro(totalConsultaEspacios.getComparaEspaciosAcabadoMuro() + registroEspacio.getComparaEspaciosAcabadoMuro());
            }
            if (registroEspacio.getComparaAreaAcabadoMuro() != null) {
                totalConsultaEspacios.setComparaAreaAcabadoMuro(totalConsultaEspacios.getComparaAreaAcabadoMuro() + registroEspacio.getComparaAreaAcabadoMuro());
            }
            if (registroEspacio.getEspaciosTecho() != null) {
                totalConsultaEspacios.setEspaciosTecho(totalConsultaEspacios.getEspaciosTecho() + registroEspacio.getEspaciosTecho());
            }
            if (registroEspacio.getAreaTecho() != null) {
                totalConsultaEspacios.setAreaTecho(totalConsultaEspacios.getAreaTecho() + registroEspacio.getAreaTecho());
            }
            if (registroEspacio.getComparaEspaciosTecho() != null) {
                totalConsultaEspacios.setComparaEspaciosTecho(totalConsultaEspacios.getComparaEspaciosTecho() + registroEspacio.getComparaEspaciosTecho());
            }
            if (registroEspacio.getComparaAreaTecho() != null) {
                totalConsultaEspacios.setComparaAreaTecho(totalConsultaEspacios.getComparaAreaTecho() + registroEspacio.getComparaAreaTecho());
            }
            if (registroEspacio.getEspaciosVentana() != null) {
                totalConsultaEspacios.setEspaciosVentana(totalConsultaEspacios.getEspaciosVentana() + registroEspacio.getEspaciosVentana());
            }
            if (registroEspacio.getComparaEspaciosVentana() != null) {
                totalConsultaEspacios.setComparaEspaciosVentana(totalConsultaEspacios.getComparaEspaciosVentana() + registroEspacio.getComparaEspaciosVentana());
            }
            if (registroEspacio.getEspaciosPuerta() != null) {
                totalConsultaEspacios.setEspaciosPuerta(totalConsultaEspacios.getEspaciosPuerta() + registroEspacio.getEspaciosPuerta());
            }
            if (registroEspacio.getComparaEspaciosPuerta() != null) {
                totalConsultaEspacios.setComparaEspaciosPuerta(totalConsultaEspacios.getComparaEspaciosPuerta() + registroEspacio.getComparaEspaciosPuerta());
            }
        }
        if (registro instanceof RegistroRedistribuccion) {
            RegistroRedistribuccion registroRedistribuccion = (RegistroRedistribuccion) registro;
            if (registroRedistribuccion.getCuposTotal()!= null) {
                totalConsultaRedistribuccion.setCuposTotal(totalConsultaRedistribuccion.getCuposTotal() + registroRedistribuccion.getCuposTotal());
            }
            if (registroRedistribuccion.getNumPrediosSaturados()!= null) {
                totalConsultaRedistribuccion.setNumPrediosSaturados(totalConsultaRedistribuccion.getNumPrediosSaturados() + registroRedistribuccion.getNumPrediosSaturados());
            }
            if (registroRedistribuccion.getNumPrediosSudUtilizados()!= null) {
                totalConsultaRedistribuccion.setNumPrediosSudUtilizados(totalConsultaRedistribuccion.getNumPrediosSudUtilizados() + registroRedistribuccion.getNumPrediosSudUtilizados());
            }
            if (registroRedistribuccion.getComparaCuposTotal()!= null) {
                totalConsultaRedistribuccion.setComparaCuposTotal(totalConsultaRedistribuccion.getComparaCuposTotal() + registroRedistribuccion.getComparaCuposTotal());
            }
            if (registroRedistribuccion.getComparaNumPrediosSaturados()!= null) {
                totalConsultaRedistribuccion.setComparaNumPrediosSaturados(totalConsultaRedistribuccion.getComparaNumPrediosSaturados() + registroRedistribuccion.getComparaNumPrediosSaturados());
            }
            if (registroRedistribuccion.getComparaNumPrediosSudUtilizados()!= null) {
                totalConsultaRedistribuccion.setComparaNumPrediosSudUtilizados(totalConsultaRedistribuccion.getComparaNumPrediosSudUtilizados() + registroRedistribuccion.getComparaNumPrediosSudUtilizados());
            }
        }
        if (registro instanceof RegistroAmpliacion) {
            RegistroAmpliacion registroAmpliacion = (RegistroAmpliacion) registro;
            if (registroAmpliacion.getAreaConstruccionPrimerPiso() != null) {
                totalConsultaAmpliacion.setAreaConstruccionPrimerPiso(totalConsultaAmpliacion.getAreaConstruccionPrimerPiso() + registroAmpliacion.getAreaConstruccionPrimerPiso());
            }
            if (registroAmpliacion.getPosConstruccionPrimerPiso()!= null) {
                totalConsultaAmpliacion.setPosConstruccionPrimerPiso(totalConsultaAmpliacion.getPosConstruccionPrimerPiso() + registroAmpliacion.getPosConstruccionPrimerPiso());
            }
            //terminar los totalizadores
        }

    }

    private void cargarCamposOpcionales() {
        //estos son de generalidades hacerlo para cada ambito
        selccionCamposOpcionales.clear();
        listaCamposOpcionales.clear();
        this.cargarCamposOpcionalesComunes();
        if (isMostrarColDirPredio()) {
            listaCamposOpcionales.add(columnaDirPredio);
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
        if (isMostrarColNumJornadasEstudiante()) {
            listaCamposOpcionales.add(columnaNumJorEst);
            selccionCamposOpcionales.add(columnaNumJorEst);
            listaCamposOpcionales.add(columnaComparaNumJorEst);
            selccionCamposOpcionales.add(columnaComparaNumJorEst);
        }

        if (isMostrarEstudiantesTotal()) {
            listaCamposOpcionales.add(columnaEstudiantesTotal);
            selccionCamposOpcionales.add(columnaEstudiantesTotal);
        }
        if (isMostrarColAreaContruccionTotal()) {
            listaCamposOpcionales.add(columnaAreaConstruccion);
            selccionCamposOpcionales.add(columnaAreaConstruccion);
            listaCamposOpcionales.add(columnaComparaAreaConstruccion);
            selccionCamposOpcionales.add(columnaComparaAreaConstruccion);
        }
        if (isMostrarColAreaRealPredio()) {
            listaCamposOpcionales.add(columnaAreaRealPredio);
            selccionCamposOpcionales.add(columnaAreaRealPredio);
        }
        if (isMostrarColNumEstablicimientos()) {
            listaCamposOpcionales.add(columnaNumEstablecimientos);
            selccionCamposOpcionales.add(columnaNumEstablecimientos);
            listaCamposOpcionales.add(columnaComparaNumEstablecimientos);
            selccionCamposOpcionales.add(columnaComparaNumEstablecimientos);
        }
        if (isMostrarColNumSedes()) {
            listaCamposOpcionales.add(columnaNumSedes);
            selccionCamposOpcionales.add(columnaNumSedes);
            listaCamposOpcionales.add(columnaComparaNumSedes);
            selccionCamposOpcionales.add(columnaComparaNumSedes);
        }
        if (isMostrarColNumPredios()) {
            listaCamposOpcionales.add(columnaNumPredios);
            selccionCamposOpcionales.add(columnaNumPredios);
            listaCamposOpcionales.add(columnaComparaNumPredios);
            selccionCamposOpcionales.add(columnaComparaNumPredios);
        }
        //redistribuccion
        if (isMostrarColPropPredio()) {
            listaCamposOpcionales.add(columnaPropPredio);
        }

        if (isMostrarColNumPrediosSaturados()) {
            listaCamposOpcionales.add(columnaNumPredioSaturados);
            selccionCamposOpcionales.add(columnaNumPredioSaturados);
        }
        if (isMostrarColNumPrediosSudUtilizados()) {
            listaCamposOpcionales.add(columnaNumPredioSubutilizados);
            selccionCamposOpcionales.add(columnaNumPredioSubutilizados);
        }
        if (isMostrarColUtilEstandarTotal()) {
            listaCamposOpcionales.add(columnaUtilizacionTotal);
            selccionCamposOpcionales.add(columnaUtilizacionTotal);
        }
        if (isMostrarColCuposTotal()) {
            listaCamposOpcionales.add(columnaCuposTotal);
            selccionCamposOpcionales.add(columnaCuposTotal);
        }
        //ambientes
        if (isMostrarColRelacionM2PorAlumno()) {
            listaCamposOpcionales.add(columnaRelacionM2PorAlumno);
            selccionCamposOpcionales.add(columnaRelacionM2PorAlumno);
        }
        if (isMostrarColDeficitEstandar()) {
            listaCamposOpcionales.add(columnaDeficitEstandar);
            selccionCamposOpcionales.add(columnaDeficitEstandar);
        }
        if (isMostrarColAcumuladoDeficit()) {
            listaCamposOpcionales.add(columnaAcumuladoDeficit);
            selccionCamposOpcionales.add(columnaAcumuladoDeficit);
        }
        if (isMostrarColProporcionPredios()) {
            listaCamposOpcionales.add(columnaProporcionPredios);
            selccionCamposOpcionales.add(columnaProporcionPredios);
        }
        if (isMostrarColProporcionEstab()) {
            listaCamposOpcionales.add(columnaProporcionEstablecimientos);
            selccionCamposOpcionales.add(columnaProporcionEstablecimientos);
        }
        if (isMostrarColProporcionEspacios()) {
            listaCamposOpcionales.add(columnaProporcionEspacios);
            selccionCamposOpcionales.add(columnaProporcionEspacios);
        }
        if (isMostrarColCumplimientoNorma()) {
            listaCamposOpcionales.add(columnaCumplimientoNorma);
            selccionCamposOpcionales.add(columnaCumplimientoNorma);
        }
        //edificios
        if(isMostrarColEdificiosNumEstudiante()){
            listaCamposOpcionales.add(columnaEdificioNumEstudiantes);
            selccionCamposOpcionales.add(columnaEdificioNumEstudiantes);
        }        
        if(isAmbitoEdificios() && isAgrupamientoPredio()){
            listaCamposOpcionales.add(columnaCondMaterialidadPredio);
            selccionCamposOpcionales.add(columnaCondMaterialidadPredio);                     
        }
        if(isAmbitoEdificios() && !isAgrupamientoPredio()){
            listaCamposOpcionales.add(columnaNumMaterialesCondCriticaPredio);
            selccionCamposOpcionales.add(columnaNumMaterialesCondCriticaPredio);
            listaCamposOpcionales.add(columnaPropMaterialesCondicionCritica);
            selccionCamposOpcionales.add(columnaPropMaterialesCondicionCritica);
        }
        if(isMostrarColCondMaterialidad()){
            listaCamposOpcionales.add(columnaNumPredCondMaterialidad);
            selccionCamposOpcionales.add(columnaNumPredCondMaterialidad);
            listaCamposOpcionales.add(columnaPropPredCondMaterialidad);
            selccionCamposOpcionales.add(columnaPropPredCondMaterialidad); 
        }
        
        //espacios
        if (isAmbitoEspacios()) {
            listaCamposOpcionales.add(columnaEspaciosPiso);
            selccionCamposOpcionales.add(columnaEspaciosPiso);
            listaCamposOpcionales.add(columnaAreaPiso);
            selccionCamposOpcionales.add(columnaAreaPiso);
            listaCamposOpcionales.add(columnaProporcionAreaPiso);
            selccionCamposOpcionales.add(columnaProporcionAreaPiso);

            listaCamposOpcionales.add(columnaEspaciosMuro);
            listaCamposOpcionales.add(columnaAreaMuro);
            listaCamposOpcionales.add(columnaProporcionAreaMuro);
            listaCamposOpcionales.add(columnaEspaciosAcabadoMuro);
            listaCamposOpcionales.add(columnaAreaAcabadoMuro);
            listaCamposOpcionales.add(columnaProporcionAreaAcabadoMuro);
            listaCamposOpcionales.add(columnaEspaciosVentana);
            listaCamposOpcionales.add(columnaProporcionVentana);
            listaCamposOpcionales.add(columnaEspaciosPuerta);
            listaCamposOpcionales.add(columnaProporcionPuerta);
            listaCamposOpcionales.add(columnaEspaciosTecho);
            listaCamposOpcionales.add(columnaAreaTecho);
            listaCamposOpcionales.add(columnaEspaciosTecho);
            listaCamposOpcionales.add(columnaProporcionAreaTecho);

            listaCamposOpcionales.add(columnaAreaTotal);
            selccionCamposOpcionales.add(columnaAreaTotal);
        }
        
        //Ampliacion
        if (isAmbitoAmpliacion()) {
            listaCamposOpcionales.add(columnaDePredio);
            listaCamposOpcionales.add(columnaDeConstruccion);
            listaCamposOpcionales.add(columnaDeAula);
            listaCamposOpcionales.add(columnaDePosibilidad);
            selccionCamposOpcionales.add(columnaDePosibilidad);            
        }
    }

    // indicadores   
    // mostrar columnas Generalidades  
    public boolean isMostrarColNumJornadasEstudiante() {
        return isAgrupamientoPredio() && isAmbitoGeneralidades();
    }

    public boolean isMostrarEstudiantesTotal(){
        return isAmbitoGeneralidades();
    }
    
    public boolean isMostrarColAreaContruccionTotal() {
        return isAgrupamientoPredio() && isAmbitoGeneralidades();
    }

    public boolean isMostrarColAreaRealPredio() {
        return isAgrupamientoPredio() && isAmbitoGeneralidades();
    }

    public boolean isMostrarColNumEstablicimientos() {
        return !isAgrupamientoPredio() && isAmbitoGeneralidades()
                && !isAgrupamientoSede() && !isAgrupamientoEstablecimiento();
    }

    public boolean isMostrarColNumSedes() {
        return !isAgrupamientoPredio() && isAmbitoGeneralidades()
                && !isAgrupamientoSede();
    }

    public boolean isMostrarColNumPredios() {
        return !isAgrupamientoPredio() && (isAmbitoGeneralidades() || isAmbitoEdificios());
    }

    // redistribuccion   
    public boolean isMostrarColPropPredio() {
        return isAgrupamientoPredio() && isAmbitoRedistribuccion();
    }

    public boolean isMostrarColCuposTotal() {
        return isAmbitoRedistribuccion();
    }

    public boolean isMostrarColUtilEstandarTotal() {
        return isAmbitoRedistribuccion();
    }

    public boolean isMostrarColNumPrediosSaturados() {
        return !isAgrupamientoPredio() && isAmbitoRedistribuccion();
    }

    public boolean isMostrarColNumPrediosSudUtilizados() {
        return !isAgrupamientoPredio() && isAmbitoRedistribuccion();
    }

    //ambientes
    public boolean isMostrarColRelacionM2PorAlumno() {
        return isAmbitoAmbientes() && (isAgrupamientoPredio() && !seleccionEspacioFuncional.equals(EspacioFuncional.EXPANSIONES_RECREATIVAS)
                || isAgrupamientoEstablecimiento() && seleccionEspacioFuncional.equals(EspacioFuncional.BIBLIOTECA));
    }

    public boolean isMostrarColDeficitEstandar() {
        return isAmbitoAmbientes() && (isAgrupamientoPredio() || isAgrupamientoEstablecimiento() && seleccionEspacioFuncional.equals(EspacioFuncional.BIBLIOTECA));
    }

    public boolean isMostrarColAcumuladoDeficit() {
        return isAmbitoAmbientes() && (!isAgrupamientoPredio() && !seleccionEspacioFuncional.equals(EspacioFuncional.BIBLIOTECA)
                || !isAgrupamientoEstablecimiento() && seleccionEspacioFuncional.equals(EspacioFuncional.BIBLIOTECA));
    }

    public boolean isMostrarColProporcionPredios() {
        return isAmbitoAmbientes() && !isAgrupamientoPredio() && !seleccionEspacioFuncional.equals(EspacioFuncional.BIBLIOTECA);
    }

    public boolean isMostrarColProporcionEstab() {
        return isAmbitoAmbientes() && !isAgrupamientoPredio()
                && !isAgrupamientoSede() && !isAgrupamientoEstablecimiento();
    }

    public boolean isMostrarColProporcionEspacios() {
        return isAmbitoAmbientes()
                && (isAgrupamientoPredio() || isAgrupamientoSede() || isAgrupamientoEstablecimiento());
    }

    public boolean isMostrarColCumplimientoNorma() {
        return isAmbitoAmbientes();
    }
    
    //Edificio    
    public boolean isMostrarColEdificiosNumEstudiante() {
        return isAmbitoEdificios();
    }
    public boolean isMostrarColCondMaterialidad() {
        return isAmbitoEdificios() && seleccionCondMaterialidadPredio != null && !isAgrupamientoPredio();
    }
    
    public boolean isMostrarColTotalPredios() {
        return isAmbitoEdificios() && !isAgrupamientoPredio();
    }

    // columnas Generalidades
    public String getColumnaNumJorEst() {
        return columnaNumJorEst;
    }

    public String getColumnaComparaNumJorEst() {
        return columnaComparaNumJorEst;
    }

    public String getColumnaNivelEnse() {
        return columnaNivelEnse;
    }

    public String getColumnaEstuNivelEnse() {
        return columnaEstuNivelEnse;
    }

    public String getColumnaEstudiantesTotal() {
        return columnaEstudiantesTotal;
    }

    public String getColumnaAreaConstruccion() {
        return columnaAreaConstruccion;
    }

    public String getColumnaComparaAreaConstruccion() {
        return columnaComparaAreaConstruccion;
    }

    public String getColumnaNumEstablecimientos() {
        return columnaNumEstablecimientos;
    }

    public String getColumnaComparaNumEstablecimientos() {
        return columnaComparaNumEstablecimientos;
    }

    public String getColumnaNumSedes() {
        return columnaNumSedes;
    }

    public String getColumnaComparaNumSedes() {
        return columnaComparaNumSedes;
    }

    public String getColumnaNumPredios() {
        return columnaNumPredios;
    }

    public String getColumnaComparaNumPredios() {
        return columnaComparaNumPredios;
    }

    public String getColumnaAreaRealPredio() {
        return columnaAreaRealPredio;
    }

    public String getColumnaNumPredioSaturados() {
        return columnaNumPredioSaturados;
    }

    public String getColumnaNumPredioSubutilizados() {
        return columnaNumPredioSubutilizados;
    }

    public String getColumnaUtilizacionTotal() {
        return columnaUtilizacionTotal;
    }

    public String getColumnaCuposTotal() {
        return columnaCuposTotal;
    }

    public String getColumnaRelacionM2PorAlumno() {
        return columnaRelacionM2PorAlumno;
    }

    public String getColumnaDeficitEstandar() {
        return columnaDeficitEstandar;
    }

    public String getColumnaAcumuladoDeficit() {
        return columnaAcumuladoDeficit;
    }

    public String getColumnaCumplimientoNorma() {
        return columnaCumplimientoNorma;
    }

    //columnas EDIFICIOS
    public String getColumnaCondMaterialidadPredio() {
        return columnaCondMaterialidadPredio;
    }

    public String getColumnaEdificioNumEstudiantes() {
        return columnaEdificioNumEstudiantes;
    }
    
    public String getColumnaNumPredCondMaterialidad() {
        return columnaNumPredCondMaterialidad;
    }

    public String getColumnaPropPredCondMaterialidad() {
        return columnaPropPredCondMaterialidad;
    }

    public String getColumnaNumMaterialesCondCriticaPredio() {
        return columnaNumMaterialesCondCriticaPredio;
    }

    public String getColumnaPropMaterialesCondicionCritica() {
        return columnaPropMaterialesCondicionCritica;
    }

    //ESPACIOS
    public String getColumnaEspaciosPiso() {
        return columnaEspaciosPiso;
    }

    public String getColumnaAreaPiso() {
        return columnaAreaPiso;
    }

    public String getColumnaProporcionAreaPiso() {
        return columnaProporcionAreaPiso;
    }

    public String getColumnaEspaciosMuro() {
        return columnaEspaciosMuro;
    }

    public String getColumnaAreaMuro() {
        return columnaAreaMuro;
    }

    public String getColumnaProporcionAreaMuro() {
        return columnaProporcionAreaMuro;
    }

    public String getColumnaEspaciosAcabadoMuro() {
        return columnaEspaciosAcabadoMuro;
    }

    public String getColumnaAreaAcabadoMuro() {
        return columnaAreaAcabadoMuro;
    }

    public String getColumnaProporcionAreaAcabadoMuro() {
        return columnaProporcionAreaAcabadoMuro;
    }

    public String getColumnaEspaciosVentana() {
        return columnaEspaciosVentana;
    }

    public String getColumnaProporcionVentana() {
        return columnaProporcionVentana;
    }

    public String getColumnaEspaciosPuerta() {
        return columnaEspaciosPuerta;
    }

    public String getColumnaProporcionPuerta() {
        return columnaProporcionPuerta;
    }

    public String getColumnaEspaciosTecho() {
        return columnaEspaciosTecho;
    }

    public String getColumnaAreaTecho() {
        return columnaAreaTecho;
    }

    public String getColumnaProporcionAreaTecho() {
        return columnaProporcionAreaTecho;
    }

    public String getColumnaAreaTotal() {
        return columnaAreaTotal;
    }
    
    //Ampliacion
    
    public String getColumnaDePredio() {
        return columnaDePredio;
    }

    public String getColumnaDeConstruccion() {
        return columnaDeConstruccion;
    }

    public String getColumnaDeAula() {
        return columnaDeAula;
    }

    public String getColumnaDePosibilidad() {
        return columnaDePosibilidad;
    }
    
    public String getColumnaProporcionEspacios() {
        return columnaProporcionEspacios;
    }

    public String getColumnaProporcionPredios() {
        return columnaProporcionPredios;
    }

    public String getColumnaProporcionEstablecimientos() {
        return columnaProporcionEstablecimientos;
    }
    
    public ConsultaComparador getConsultaComparador() {
        return consultaComparador;
    }

    public void setConsultaComparador(ConsultaComparador consultaComparador) {
        this.consultaComparador = consultaComparador;
    }
}
