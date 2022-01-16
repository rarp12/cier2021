/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.consulta.ambito;

import co.stackpointer.cier.modelo.entidad.instrumento.TipologiaValorNombre;
import co.stackpointer.cier.modelo.filtro.ambito.FiltroAmbitoEdificio;
import co.stackpointer.cier.modelo.tipo.CalificacionCondicion;
import co.stackpointer.cier.modelo.tipo.TipologiaCodigo;
import co.stackpointer.cier.modelo.utilidad.UtilConsulta;
import co.stackpointer.cier.modelo.utilidad.UtilOut;
import co.stackpointer.cier.vista.Utilidades;
import java.util.ArrayList;
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
@ManagedBean(name = "edificiosBean")
@ViewScoped
public class EdificiosBean extends ConsultaAmbito {

    @ManagedProperty(value = "#{consultaAmbito}")
    private ConsultaAmbito consultaAmbito;
    private List<Registro> lista;
    private List<TipologiaValorNombre> listaMaterialAcabados;
    private List<TipologiaValorNombre> listaMaterialCubiertas;
    private List<TipologiaValorNombre> listaMaterialMuros;
    private FiltroAmbitoEdificio filtro = new FiltroAmbitoEdificio();
    private Registro totalConsulta = new Registro();
    private Integer seleccionAñoContruccionIni;
    private Integer seleccionAñoContruccionFin;
    private TipologiaValorNombre seleccionMaterialAcabados;
    private TipologiaValorNombre seleccionMaterialCubiertas;
    private TipologiaValorNombre seleccionMaterialMuros;
    private String seleccionCondMaterialidadPredio;
    private CalificacionCondicion seleccionCondMaterial;
    private String seleccionCondMaterialidadEdificio;

    private final String columnaCondMaterialidadPredio = Utilidades.obtenerMensaje("consultas.ambito.edificios.columna.cod.materialidad.predio");
    private final String columnaNumeroEdificio = Utilidades.obtenerMensaje("consultas.ambito.edificios.columna.num.edificio");
    private final String columnaMaterialMurosFachadas = Utilidades.obtenerMensaje("consultas.ambito.edificios.columna.mat.pred.muros.fachadas");
    private final String columnaCondMaterialMurosFachadas = Utilidades.obtenerMensaje("consultas.ambito.edificios.columna.cond.mat.pred.muros.fachadas");
    private final String columnaMaterialAcabadosFachadas = Utilidades.obtenerMensaje("consultas.ambito.edificios.columna.mat.pred.acabados.fachadas");
    private final String columnaCondMaterialAcabadosFachadas = Utilidades.obtenerMensaje("consultas.ambito.edificios.columna.cod.mat.pred.acabados.fachadas");
    private final String columnaMaterialCubiertas = Utilidades.obtenerMensaje("consultas.ambito.edificios.columna.mat.pred.cubiertas");
    private final String columnaCondMaterialCubiertas = Utilidades.obtenerMensaje("consultas.ambito.edificios.columna.cond.mat.pred.cubiertas");
    private final String columnaCondMaterialidadEdificio = Utilidades.obtenerMensaje("consultas.ambito.edificios.columna.cond.mat.edificio");
    private final String columnaTotalEstudiantes = Utilidades.obtenerMensaje("consultas.ambito.edificios.columna.total.estudiantes");
    private final String columnaNumeroPisosNiveles = Utilidades.obtenerMensaje("consultas.ambito.edificios.columna.num.pisos.niveles");
    private final String columnaAñosConstruccionEdificio = Utilidades.obtenerMensaje("consultas.ambito.edificios.columna.años.const.edificio");
    private final String columnaTipoSistemaEstructural = Utilidades.obtenerMensaje("consultas.ambito.edificios.columna.tipo.sistema.estructural");
    private final String columnaCondTipoSistemaEstructural = Utilidades.obtenerMensaje("consultas.ambito.edificios.columna.cond.tipo.sistema.estructual");
    private final String columnaEdificacionPatrimonialCultural = Utilidades.obtenerMensaje("consultas.ambito.edificios.columna.edif.interes.patr.cultural");
    private final String columnaLicenciaConstruccion = Utilidades.obtenerMensaje("consultas.ambito.edificios.columna.licencia.contruccion");
    private final String columnaTieneRutaEvacuacion = Utilidades.obtenerMensaje("consultas.ambito.edificios.columna.tiene.ruta.evac");
    private final String columnaCondFisicaRutaEvacuacion = Utilidades.obtenerMensaje("consultas.ambito.edificios.columna.cond.fisica.ruta.evac");
    private final String columnaSeñalizacionMediosEvacuacion = Utilidades.obtenerMensaje("consultas.ambito.edificios.columna.señ.medios.evac");
    private final String columnaNumPredCondMaterialidad = Utilidades.obtenerMensaje("consultas.ambito.edificios.columna.num.pred.cond.mat");
    private final String columnaTotalPredios = Utilidades.obtenerMensaje("consultas.ambito.edificios.columna.total.predios");
    private final String columnaPropPredCondMaterialidad = Utilidades.obtenerMensaje("consultas.ambito.edificios.columna.prop.pred.cond.mat");
    private final String columnaNumMaterialesCondCriticaPredio = Utilidades.obtenerMensaje("consultas.ambito.edificios.columna.num.mat.cond.critica");
    private final String columnaPropMaterialesCondicionCritica = Utilidades.obtenerMensaje("consultas.ambito.edificios.columna.prop.mat.cond.critica");

    @PostConstruct
    public void inicializar() {
        //super.init();
        this.lista = new ArrayList<Registro>();
        this.cargarCamposOpcionales();
        // tipologias
        this.listaMaterialAcabados = facParam.consultarValoresTipologia(TipologiaCodigo.EDI_MAT_PRE_ACABADOS, getUsuarioSesion().getUsuario().getIdioma().getId());
        this.listaMaterialCubiertas = facParam.consultarValoresTipologia(TipologiaCodigo.EDI_MAT_PRE_CUBIERTAS, getUsuarioSesion().getUsuario().getIdioma().getId());
        this.listaMaterialMuros = facParam.consultarValoresTipologia(TipologiaCodigo.EDI_MAT_PRE_MUROS_FACHADAS, getUsuarioSesion().getUsuario().getIdioma().getId());
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
            //filtros indicador    
            filtro.setAñoContruccionIni(seleccionAñoContruccionIni);
            filtro.setAñoContruccionFin(seleccionAñoContruccionFin);
            filtro.setCodMaterialAcabados(seleccionMaterialAcabados != null ? seleccionMaterialAcabados.getTipValor().getCodigo() : null);
            filtro.setCodMaterialCubiertas(seleccionMaterialCubiertas != null ? seleccionMaterialCubiertas.getTipValor().getCodigo() : null);
            filtro.setCodMaterialMuros(seleccionMaterialMuros != null ? seleccionMaterialMuros.getTipValor().getCodigo() : null);
            filtro.setCondMaterialidadPredio(seleccionCondMaterialidadPredio);
            filtro.setCondMaterial(seleccionCondMaterial != null ? seleccionCondMaterial.getValor() : null);
            filtro.setCondMaterialidadEdificio(seleccionCondMaterialidadEdificio);

            List<Object[]> resultados = facAmbito.consultarEdificios(filtro);
            lista = new ArrayList<Registro>(resultados.size());
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
                    registro.setZona(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]), getUsuarioSesion().getUsuario().getIdioma().getId()));
                    registro.setSector(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]), getUsuarioSesion().getUsuario().getIdioma().getId()));
                    registro.setClima(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]), getUsuarioSesion().getUsuario().getIdioma().getId()));
                    registro.setEtnias(verificarEtnias(UtilConsulta.stringValueOf(fila[i++])));
                    //indicadores 
                    registro.setCondMaterialidadPredio(Utilidades.obtenerMensaje(UtilConsulta.stringValueOf(fila[i++])));
                    registro.setNumeroEdificio(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setMaterialMurosFachadas(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]), getUsuarioSesion().getUsuario().getIdioma().getId()));
                    registro.setCondMaterialMurosFachadas(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setMaterialAcabadosFachadas(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]), getUsuarioSesion().getUsuario().getIdioma().getId()));
                    registro.setCondMaterialAcabadosFachadas(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setMaterialCubiertas(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]), getUsuarioSesion().getUsuario().getIdioma().getId()));
                    registro.setCondMaterialCubiertas(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCondMaterialidadEdificio(Utilidades.obtenerMensaje(UtilConsulta.stringValueOf(fila[i++])));
                    registro.setTotalEstudiantes(UtilConsulta.longValueOf(fila[i++]));
                    registro.setNumeroPisosNiveles(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setAñosConstruccionEdificio(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setTipoSistemaEstructural(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCondTipoSistemaEstructural(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setEdificacionPatrimonialCultural(Utilidades.obtenerMensaje(UtilConsulta.stringValueOf(fila[i++])));
                    registro.setLicenciaConstruccion(Utilidades.obtenerMensaje(UtilConsulta.stringValueOf(fila[i++])));
                    registro.setTieneRutaEvacuacion(Utilidades.obtenerMensaje(UtilConsulta.stringValueOf(fila[i++])));
                    registro.setCondFisicaRutaEvacuacion(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setSeñalizacionMediosEvacuacion(Utilidades.obtenerMensaje(UtilConsulta.stringValueOf(fila[i++])));
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
                    if (filtro.getCondMaterialidadPredio() != null) {
                        registro.setNumPredCondMaterialidad(UtilConsulta.longValueOf(fila[i++]));
                        registro.setPropPredCondMaterialidad(UtilConsulta.doubleValueOf(fila[i++]));
                    }
                    registro.setTotalPredios(UtilConsulta.longValueOf(fila[i++]));
                    registro.setNumMaterialesCondCriticaPredio(UtilConsulta.longValueOf(fila[i++]));
                    registro.setPropMaterialesCondicionCritica(UtilConsulta.doubleValueOf(fila[i++]));
                    registro.setTotalEstudiantes(UtilConsulta.longValueOf(fila[i++]));
                }
                // totales
                this.totalizarRegistro(registro);
                lista.add(registro);
            }
        } catch (Exception e) {
            Messages.addFlashGlobalError(e.getMessage());
            UtilOut.println(e);
        }
    }

    @Override
    public void resetearFiltros() {
        consultaAmbito.resetearNiveles();
        this.resetearListasEstSedesPredios();
        this.seleccionSector = null;
        this.seleccionZona = null;
        this.seleccionCondMaterial = null;
        this.seleccionCondMaterialidadPredio = null;
        this.seleccionAñoContruccionIni = null;
        this.seleccionAñoContruccionFin = null;
        this.seleccionMaterialAcabados = null;
        this.seleccionMaterialCubiertas = null;
        this.seleccionMaterialMuros = null;
        consultaAmbito.nivelDPAOfAccesoUsuario();
        this.resetearConsulta();
    }

    /**
     * Cuando se cambie el filtro de periodo este reseteara todos los campos
     * parametricos de la consulta a excepcion de los que sonparte de la DPA
     */
    public void resetearFiltrosPeriodo() {
        //consultaAmbito.resetearNiveles();
        this.resetearListasEstSedesPredios();
        this.seleccionSector = null;
        this.seleccionZona = null;
        this.seleccionCondMaterial = null;
        this.seleccionCondMaterialidadPredio = null;
        this.seleccionAñoContruccionIni = null;
        this.seleccionAñoContruccionFin = null;
        this.seleccionMaterialAcabados = null;
        this.seleccionMaterialCubiertas = null;
        this.seleccionMaterialMuros = null;
        //consultaAmbito.nivelDPAOfAccesoUsuario();
        this.resetearConsulta();
    }

    @Override
    public void cambioAgrupamiento() {
        this.resetearConsulta();
    }

    private void resetearConsulta() {
        this.seleccionAñoContruccionIni = null;
        this.seleccionAñoContruccionFin = null;
        this.seleccionMaterialAcabados = null;
        this.seleccionMaterialCubiertas = null;
        this.seleccionMaterialMuros = null;
        this.seleccionCondMaterial = null;
        this.seleccionCondMaterialidadEdificio = null;
        if (isAgrupamientoPredio()) {
            this.seleccionCondMaterialidadPredio = null;
        }
        // columnas
        this.cargarCamposOpcionales();
        // resultados
        this.lista.clear();
        this.inicializarTotalesConsulta();
    }

    public void cambioMaterialidadPredio() {
        this.resetearConsulta();
    }

    public void cargarCamposOpcionales() {
        selccionCamposOpcionales.clear();
        listaCamposOpcionales.clear();
        this.cargarCamposOpcionalesComunes();
        if (isMostrarColPropiedadPredio()) {
            listaCamposOpcionales.add(columnaPropPredio);
        }
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
        if (isAgrupamientoPredio()) {
            listaCamposOpcionales.add(columnaCondMaterialidadPredio);
            listaCamposOpcionales.add(columnaNumeroEdificio);
            listaCamposOpcionales.add(columnaMaterialMurosFachadas);
            listaCamposOpcionales.add(columnaCondMaterialMurosFachadas);
            listaCamposOpcionales.add(columnaMaterialAcabadosFachadas);
            listaCamposOpcionales.add(columnaCondMaterialAcabadosFachadas);
            listaCamposOpcionales.add(columnaMaterialCubiertas);
            listaCamposOpcionales.add(columnaCondMaterialCubiertas);
            listaCamposOpcionales.add(columnaCondMaterialidadEdificio);
            listaCamposOpcionales.add(columnaTotalEstudiantes);
            listaCamposOpcionales.add(columnaNumeroPisosNiveles);
            listaCamposOpcionales.add(columnaAñosConstruccionEdificio);
            listaCamposOpcionales.add(columnaTipoSistemaEstructural);
            listaCamposOpcionales.add(columnaCondTipoSistemaEstructural);
            listaCamposOpcionales.add(columnaEdificacionPatrimonialCultural);
            listaCamposOpcionales.add(columnaLicenciaConstruccion);
            listaCamposOpcionales.add(columnaTieneRutaEvacuacion);
            listaCamposOpcionales.add(columnaCondFisicaRutaEvacuacion);
            listaCamposOpcionales.add(columnaSeñalizacionMediosEvacuacion);
            // seleccionar
            selccionCamposOpcionales.add(columnaCondMaterialidadPredio);
            selccionCamposOpcionales.add(columnaNumeroEdificio);
            selccionCamposOpcionales.add(columnaMaterialMurosFachadas);
            selccionCamposOpcionales.add(columnaCondMaterialMurosFachadas);
            selccionCamposOpcionales.add(columnaMaterialAcabadosFachadas);
            selccionCamposOpcionales.add(columnaCondMaterialAcabadosFachadas);
            selccionCamposOpcionales.add(columnaMaterialCubiertas);
            selccionCamposOpcionales.add(columnaCondMaterialCubiertas);
            selccionCamposOpcionales.add(columnaCondMaterialidadEdificio);
            selccionCamposOpcionales.add(columnaTotalEstudiantes);
        } else {
            if (this.seleccionCondMaterialidadPredio != null) {
                listaCamposOpcionales.add(columnaNumPredCondMaterialidad);
                listaCamposOpcionales.add(columnaPropPredCondMaterialidad);
            }
            listaCamposOpcionales.add(columnaTotalPredios);
            listaCamposOpcionales.add(columnaNumMaterialesCondCriticaPredio);
            listaCamposOpcionales.add(columnaPropMaterialesCondicionCritica);
            listaCamposOpcionales.add(columnaTotalEstudiantes);
            // seleccionar
            if (this.seleccionCondMaterialidadPredio != null) {
                selccionCamposOpcionales.add(columnaNumPredCondMaterialidad);
                selccionCamposOpcionales.add(columnaPropPredCondMaterialidad);
            }
            selccionCamposOpcionales.add(columnaTotalPredios);
            selccionCamposOpcionales.add(columnaNumMaterialesCondCriticaPredio);
            selccionCamposOpcionales.add(columnaPropMaterialesCondicionCritica);
            selccionCamposOpcionales.add(columnaTotalEstudiantes);
        }
    }

    private void inicializarTotalesConsulta() {
        totalConsulta.setTotalEstudiantes(0L);
        totalConsulta.setNumPredCondMaterialidad(0L);
        totalConsulta.setTotalPredios(0L);
        totalConsulta.setNumMaterialesCondCriticaPredio(0L);
    }

    private void totalizarRegistro(Registro registro) {
        if (registro.getTotalEstudiantes() != null) {
            totalConsulta.setTotalEstudiantes(totalConsulta.getTotalEstudiantes() + registro.getTotalEstudiantes());
        }
        if (registro.getNumPredCondMaterialidad() != null) {
            totalConsulta.setNumPredCondMaterialidad(totalConsulta.getNumPredCondMaterialidad() + registro.getNumPredCondMaterialidad());
        }
        if (registro.getTotalPredios() != null) {
            totalConsulta.setTotalPredios(totalConsulta.getTotalPredios() + registro.getTotalPredios());
        }
        if (registro.getNumMaterialesCondCriticaPredio() != null) {
            totalConsulta.setNumMaterialesCondCriticaPredio(totalConsulta.getNumMaterialesCondCriticaPredio() + registro.getNumMaterialesCondCriticaPredio());
        }
    }

    public List<Registro> getLista() {
        return lista;
    }

    public Registro getTotalConsulta() {
        return totalConsulta;
    }

    public Integer getSeleccionAñoContruccionIni() {
        return seleccionAñoContruccionIni;
    }

    public void setSeleccionAñoContruccionIni(Integer seleccionAñoContruccionIni) {
        this.seleccionAñoContruccionIni = seleccionAñoContruccionIni;
    }

    public Integer getSeleccionAñoContruccionFin() {
        return seleccionAñoContruccionFin;
    }

    public void setSeleccionAñoContruccionFin(Integer seleccionAñoContruccionFin) {
        this.seleccionAñoContruccionFin = seleccionAñoContruccionFin;
    }

    public TipologiaValorNombre getSeleccionMaterialAcabados() {
        return seleccionMaterialAcabados;
    }

    public void setSeleccionMaterialAcabados(TipologiaValorNombre seleccionMaterialAcabados) {
        this.seleccionMaterialAcabados = seleccionMaterialAcabados;
    }

    public TipologiaValorNombre getSeleccionMaterialCubiertas() {
        return seleccionMaterialCubiertas;
    }

    public void setSeleccionMaterialCubiertas(TipologiaValorNombre seleccionMaterialCubiertas) {
        this.seleccionMaterialCubiertas = seleccionMaterialCubiertas;
    }

    public TipologiaValorNombre getSeleccionMaterialMuros() {
        return seleccionMaterialMuros;
    }

    public void setSeleccionMaterialMuros(TipologiaValorNombre seleccionMaterialMuros) {
        this.seleccionMaterialMuros = seleccionMaterialMuros;
    }

    public String getSeleccionCondMaterialidadPredio() {
        return seleccionCondMaterialidadPredio;
    }

    public void setSeleccionCondMaterialidadPredio(String seleccionCondMaterialidadPredio) {
        this.seleccionCondMaterialidadPredio = seleccionCondMaterialidadPredio;
    }

    public CalificacionCondicion getSeleccionCondMaterial() {
        return seleccionCondMaterial;
    }

    public void setSeleccionCondMaterial(CalificacionCondicion seleccionCondMaterial) {
        this.seleccionCondMaterial = seleccionCondMaterial;
    }

    public String getSeleccionCondMaterialidadEdificio() {
        return seleccionCondMaterialidadEdificio;
    }

    public void setSeleccionCondMaterialidadEdificio(String seleccionCondMaterialidadEdificio) {
        this.seleccionCondMaterialidadEdificio = seleccionCondMaterialidadEdificio;
    }

    public List<TipologiaValorNombre> getListaMaterialAcabados() {
        return listaMaterialAcabados;
    }

    public List<TipologiaValorNombre> getListaMaterialCubiertas() {
        return listaMaterialCubiertas;
    }

    public List<TipologiaValorNombre> getListaMaterialMuros() {
        return listaMaterialMuros;
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
        //predio        
        private String condMaterialidadPredio;
        private String numeroEdificio;
        private String materialMurosFachadas;
        private String condMaterialMurosFachadas;
        private String materialAcabadosFachadas;
        private String condMaterialAcabadosFachadas;
        private String materialCubiertas;
        private String condMaterialCubiertas;
        private String condMaterialidadEdificio;
        private Long totalEstudiantes;
        private String numeroPisosNiveles;
        private String añosConstruccionEdificio;
        private String tipoSistemaEstructural;
        private String condTipoSistemaEstructural;
        private String edificacionPatrimonialCultural;
        private String licenciaConstruccion;
        private String tieneRutaEvacuacion;
        private String condFisicaRutaEvacuacion;
        private String señalizacionMediosEvacuacion;
        //otros niveles
        private Long numPredCondMaterialidad;
        private Long totalPredios;
        private Double propPredCondMaterialidad;
        private Long numMaterialesCondCriticaPredio;
        private Double propMaterialesCondicionCritica;

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
                } else {
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

        public String getCondMaterialidadPredio() {
            return condMaterialidadPredio;
        }

        public void setCondMaterialidadPredio(String condMaterialidadPredio) {
            this.condMaterialidadPredio = condMaterialidadPredio;
        }

        public String getNumeroEdificio() {
            return numeroEdificio;
        }

        public void setNumeroEdificio(String numeroEdificio) {
            this.numeroEdificio = numeroEdificio;
        }

        public String getMaterialMurosFachadas() {
            return materialMurosFachadas;
        }

        public void setMaterialMurosFachadas(String materialMurosFachadas) {
            this.materialMurosFachadas = materialMurosFachadas;
        }

        public String getCondMaterialMurosFachadas() {
            return condMaterialMurosFachadas;
        }

        public void setCondMaterialMurosFachadas(String condMaterialMurosFachadas) {
            this.condMaterialMurosFachadas = condMaterialMurosFachadas;
        }

        public String getMaterialAcabadosFachadas() {
            return materialAcabadosFachadas;
        }

        public void setMaterialAcabadosFachadas(String materialAcabadosFachadas) {
            this.materialAcabadosFachadas = materialAcabadosFachadas;
        }

        public String getCondMaterialAcabadosFachadas() {
            return condMaterialAcabadosFachadas;
        }

        public void setCondMaterialAcabadosFachadas(String condMaterialAcabadosFachadas) {
            this.condMaterialAcabadosFachadas = condMaterialAcabadosFachadas;
        }

        public String getMaterialCubiertas() {
            return materialCubiertas;
        }

        public void setMaterialCubiertas(String materialCubiertas) {
            this.materialCubiertas = materialCubiertas;
        }

        public String getCondMaterialCubiertas() {
            return condMaterialCubiertas;
        }

        public void setCondMaterialCubiertas(String condMaterialCubiertas) {
            this.condMaterialCubiertas = condMaterialCubiertas;
        }

        public String getCondMaterialidadEdificio() {
            return condMaterialidadEdificio;
        }

        public void setCondMaterialidadEdificio(String condMaterialidadEdificio) {
            this.condMaterialidadEdificio = condMaterialidadEdificio;
        }

        public Long getTotalEstudiantes() {
            return totalEstudiantes;
        }

        public void setTotalEstudiantes(Long totalEstudiantes) {
            this.totalEstudiantes = totalEstudiantes;
        }

        public String getNumeroPisosNiveles() {
            return numeroPisosNiveles;
        }

        public void setNumeroPisosNiveles(String numeroPisosNiveles) {
            this.numeroPisosNiveles = numeroPisosNiveles;
        }

        public String getAñosConstruccionEdificio() {
            return añosConstruccionEdificio;
        }

        public void setAñosConstruccionEdificio(String añosConstruccionEdificio) {
            this.añosConstruccionEdificio = añosConstruccionEdificio;
        }

        public String getTipoSistemaEstructural() {
            return tipoSistemaEstructural;
        }

        public void setTipoSistemaEstructural(String tipoSistemaEstructural) {
            this.tipoSistemaEstructural = tipoSistemaEstructural;
        }

        public String getCondTipoSistemaEstructural() {
            return condTipoSistemaEstructural;
        }

        public void setCondTipoSistemaEstructural(String condTipoSistemaEstructural) {
            this.condTipoSistemaEstructural = condTipoSistemaEstructural;
        }

        public String getEdificacionPatrimonialCultural() {
            return edificacionPatrimonialCultural;
        }

        public void setEdificacionPatrimonialCultural(String edificacionPatrimonialCultural) {
            this.edificacionPatrimonialCultural = edificacionPatrimonialCultural;
        }

        public String getLicenciaConstruccion() {
            return licenciaConstruccion;
        }

        public void setLicenciaConstruccion(String licenciaConstruccion) {
            this.licenciaConstruccion = licenciaConstruccion;
        }

        public String getTieneRutaEvacuacion() {
            return tieneRutaEvacuacion;
        }

        public void setTieneRutaEvacuacion(String tieneRutaEvacuacion) {
            this.tieneRutaEvacuacion = tieneRutaEvacuacion;
        }

        public String getCondFisicaRutaEvacuacion() {
            return condFisicaRutaEvacuacion;
        }

        public void setCondFisicaRutaEvacuacion(String condFisicaRutaEvacuacion) {
            this.condFisicaRutaEvacuacion = condFisicaRutaEvacuacion;
        }

        public String getSeñalizacionMediosEvacuacion() {
            return señalizacionMediosEvacuacion;
        }

        public void setSeñalizacionMediosEvacuacion(String señalizacionMediosEvacuacion) {
            this.señalizacionMediosEvacuacion = señalizacionMediosEvacuacion;
        }

        public Long getNumPredCondMaterialidad() {
            return numPredCondMaterialidad;
        }

        public void setNumPredCondMaterialidad(Long numPredCondMaterialidad) {
            this.numPredCondMaterialidad = numPredCondMaterialidad;
        }

        public Long getTotalPredios() {
            return totalPredios;
        }

        public void setTotalPredios(Long totalPredios) {
            this.totalPredios = totalPredios;
        }

        public Double getPropPredCondMaterialidad() {
            return propPredCondMaterialidad;
        }

        public void setPropPredCondMaterialidad(Double propPredCondMaterialidad) {
            this.propPredCondMaterialidad = propPredCondMaterialidad;
        }

        public Long getNumMaterialesCondCriticaPredio() {
            return numMaterialesCondCriticaPredio;
        }

        public void setNumMaterialesCondCriticaPredio(Long numMaterialesCondCriticaPredio) {
            this.numMaterialesCondCriticaPredio = numMaterialesCondCriticaPredio;
        }

        public Double getPropMaterialesCondicionCritica() {
            return propMaterialesCondicionCritica;
        }

        public void setPropMaterialesCondicionCritica(Double propMaterialesCondicionCritica) {
            this.propMaterialesCondicionCritica = propMaterialesCondicionCritica;
        }
    }

    public String getColumnaCondMaterialidadPredio() {
        return columnaCondMaterialidadPredio;
    }

    public String getColumnaNumeroEdificio() {
        return columnaNumeroEdificio;
    }

    public String getColumnaMaterialMurosFachadas() {
        return columnaMaterialMurosFachadas;
    }

    public String getColumnaCondMaterialMurosFachadas() {
        return columnaCondMaterialMurosFachadas;
    }

    public String getColumnaMaterialAcabadosFachadas() {
        return columnaMaterialAcabadosFachadas;
    }

    public String getColumnaCondMaterialAcabadosFachadas() {
        return columnaCondMaterialAcabadosFachadas;
    }

    public String getColumnaMaterialCubiertas() {
        return columnaMaterialCubiertas;
    }

    public String getColumnaCondMaterialCubiertas() {
        return columnaCondMaterialCubiertas;
    }

    public String getColumnaCondMaterialidadEdificio() {
        return columnaCondMaterialidadEdificio;
    }

    public String getColumnaTotalEstudiantes() {
        return columnaTotalEstudiantes;
    }

    public String getColumnaNumeroPisosNiveles() {
        return columnaNumeroPisosNiveles;
    }

    public String getColumnaAñosConstruccionEdificio() {
        return columnaAñosConstruccionEdificio;
    }

    public String getColumnaTipoSistemaEstructural() {
        return columnaTipoSistemaEstructural;
    }

    public String getColumnaCondTipoSistemaEstructural() {
        return columnaCondTipoSistemaEstructural;
    }

    public String getColumnaEdificacionPatrimonialCultural() {
        return columnaEdificacionPatrimonialCultural;
    }

    public String getColumnaLicenciaConstruccion() {
        return columnaLicenciaConstruccion;
    }

    public String getColumnaTieneRutaEvacuacion() {
        return columnaTieneRutaEvacuacion;
    }

    public String getColumnaCondFisicaRutaEvacuacion() {
        return columnaCondFisicaRutaEvacuacion;
    }

    public String getColumnaSeñalizacionMediosEvacuacion() {
        return columnaSeñalizacionMediosEvacuacion;
    }

    public String getColumnaNumPredCondMaterialidad() {
        return columnaNumPredCondMaterialidad;
    }

    public String getColumnaTotalPredios() {
        return columnaTotalPredios;
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

    public ConsultaAmbito getConsultaAmbito() {
        return consultaAmbito;
    }

    public void setConsultaAmbito(ConsultaAmbito consultaAmbito) {
        this.consultaAmbito = consultaAmbito;
    }

}
