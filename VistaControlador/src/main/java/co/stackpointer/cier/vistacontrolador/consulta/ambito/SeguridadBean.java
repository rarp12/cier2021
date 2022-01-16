/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.consulta.ambito;

import co.stackpointer.cier.modelo.entidad.instrumento.TipologiaValorNombre;
import co.stackpointer.cier.modelo.filtro.ambito.FiltroAmbitoSeguridad;
import co.stackpointer.cier.modelo.tipo.CalificacionCondicion;
import co.stackpointer.cier.modelo.tipo.TipologiaCodigo;
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
@ManagedBean(name = "seguridad")
@ViewScoped
public class SeguridadBean extends ConsultaAmbito {

    @ManagedProperty(value = "#{consultaAmbito}")
    private ConsultaAmbito consultaAmbito;
    private List<String> seleccionEstructura;
    private CalificacionCondicion seleccionCondicionEstructura;
    private String seleccionSenalizacionEvacuacion;
    private String seleccionSistemaContraIncendio;
    private String seleccionAnalisisRutaEvacuacion;
    private String seleccionEstadoEstructura;
    private List<TipologiaValorNombre> listaSistemaEstructural;
    private List<Registro> lista;
    private FiltroAmbitoSeguridad filtro = new FiltroAmbitoSeguridad();
    private TotalConsulta totalConsulta = new TotalConsulta();
    final String columnaEstadoEstructura = Utilidades.obtenerMensaje("consultas.ambito.seguridad.columna.estadoEstructura");
    final String columnaAnalisisRutaEvacuacion = Utilidades.obtenerMensaje("consultas.ambito.seguridad.columna.analisisRutaEvacuacion");
    final String columnaReactivoIncendios = Utilidades.obtenerMensaje("consultas.ambito.seguridad.columna.reactivoIncendios");
    final String columnaSenalizacionEvacuacion = Utilidades.obtenerMensaje("consultas.ambito.seguridad.columna.senalizacionEvacuacion");
    final String columnaEdificio = Utilidades.obtenerMensaje("consultas.ambito.seguridad.columna.edificio");
    final String columnaEstructura = Utilidades.obtenerMensaje("consultas.ambito.seguridad.columna.estructura");
    final String columnaCondicionEstructura = Utilidades.obtenerMensaje("consultas.ambito.seguridad.columna.condicionEstructura");
    final String columnaCondicionRutaEvacuacion = Utilidades.obtenerMensaje("consultas.ambito.seguridad.columna.condicionRutaEvacuacion");
    final String columnaSenalizacionEvacuacionEdificio = Utilidades.obtenerMensaje("consultas.ambito.seguridad.columna.senalizacionEvacuacionEdificio");
    final String columnaSistemaContraIncendio = Utilidades.obtenerMensaje("consultas.ambito.seguridad.columna.sistemaContraIncendio");
    final String columnaTotalEstudiantes = Utilidades.obtenerMensaje("consultas.ambito.seguridad.columna.totalEstudiantes");
    final String columnaTotalPredios = Utilidades.obtenerMensaje("consultas.ambito.seguridad.columna.totalPredios");
    final String columnaPrediosSegunEstadoEstructura = Utilidades.obtenerMensaje("consultas.ambito.seguridad.columna.prediosSegunEstadoEstructura");
    final String columnaPrediosSegunRutaEvacuacion = Utilidades.obtenerMensaje("consultas.ambito.seguridad.columna.prediosSegunRutaEvacuacion");
    final String columnaPrediosSegunReactivoIncendios = Utilidades.obtenerMensaje("consultas.ambito.seguridad.columna.prediosSegunReactivoIncendios");
    final String columnaPrediosSegunSenalizacionEvacuacion = Utilidades.obtenerMensaje("consultas.ambito.seguridad.columna.prediosSegunSenalizacionEvacuacion");
    final String columnaCondicionAccesibilidad = Utilidades.obtenerMensaje("consultas.ambito.seguridad.columna.condicionAccesibilidad");
    final String columnaCondicionRA = Utilidades.obtenerMensaje("consultas.ambito.seguridad.columna.condicionRA");
    final String columnaCondicionRN = Utilidades.obtenerMensaje("consultas.ambito.seguridad.columna.condicionRN");

    @PostConstruct
    public void inicializar() {
        //super.init();
        listaSistemaEstructural = facParam.consultarValoresTipologia(TipologiaCodigo.TIPO_SIS_ESTRUTURAL.getValor(),getUsuarioSesion().getUsuario().getIdioma().getId());
        lista = new ArrayList<Registro>();
        cargarCamposOpcionales();       
    }

    public void condicionSeleccionada() {
        selccionCamposOpcionales.clear();
      /*  this.lista = new ArrayList<Registro>();
        this.totalConsulta.inicializar();*/
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
        if (isMostrarColCondicionAccesibilidad()) {
            listaCamposOpcionales.add(columnaCondicionAccesibilidad);
        }
        if (isMostrarColCondicionRA()) {
            listaCamposOpcionales.add(columnaCondicionRA);
        }
        if (isMostrarColCondicionRN()) {
            listaCamposOpcionales.add(columnaCondicionRN);
        }
        //propias del ambito      
        if (isMostrarColEstadoEstructura()) {
            listaCamposOpcionales.add(columnaEstadoEstructura);
            selccionCamposOpcionales.add(columnaEstadoEstructura);
        }
        if (isMostrarColAnalisisRutaEvacuacion()) {
            listaCamposOpcionales.add(columnaAnalisisRutaEvacuacion);
            selccionCamposOpcionales.add(columnaAnalisisRutaEvacuacion);
        }
        if (isMostrarColReactivoIncendios()) {
            listaCamposOpcionales.add(columnaReactivoIncendios);
            selccionCamposOpcionales.add(columnaReactivoIncendios);
        }
        if (isMostrarColSenalizacionEvacuacion()) {
            listaCamposOpcionales.add(columnaSenalizacionEvacuacion);
            selccionCamposOpcionales.add(columnaSenalizacionEvacuacion);
        }
        if (isMostrarColEdificio()) {
            listaCamposOpcionales.add(columnaEdificio);
            selccionCamposOpcionales.add(columnaEdificio);
        }
        if (isMostrarColEstructura()) {
            listaCamposOpcionales.add(columnaEstructura);
            selccionCamposOpcionales.add(columnaEstructura);
        }
        if (isMostrarColCondicionEstructura()) {
            listaCamposOpcionales.add(columnaCondicionEstructura);
            selccionCamposOpcionales.add(columnaCondicionEstructura);
        }
        if (isMostrarColCondicionRutaEvacuacion()) {
            listaCamposOpcionales.add(columnaCondicionRutaEvacuacion);
            selccionCamposOpcionales.add(columnaCondicionRutaEvacuacion);
        }
        if (isMostrarColSenalizacionEvacuacionEdificio()) {
            listaCamposOpcionales.add(columnaSenalizacionEvacuacionEdificio);
            selccionCamposOpcionales.add(columnaSenalizacionEvacuacionEdificio);
        }
        if (isMostrarColSistemaContraIncendio()) {
            listaCamposOpcionales.add(columnaSistemaContraIncendio);
            selccionCamposOpcionales.add(columnaSistemaContraIncendio);
        }
        if (isMostrarColTotalEstudiantes()) {
            listaCamposOpcionales.add(columnaTotalEstudiantes);
            selccionCamposOpcionales.add(columnaTotalEstudiantes);
        }
        if (isMostrarColTotalPredios()) {
            listaCamposOpcionales.add(columnaTotalPredios);
            selccionCamposOpcionales.add(columnaTotalPredios);
        }
        if (isMostrarColPrediosSegunEstadoEstructura()) {
            listaCamposOpcionales.add(columnaPrediosSegunEstadoEstructura);
            selccionCamposOpcionales.add(columnaPrediosSegunEstadoEstructura);
        }
        if (isMostrarColPrediosSegunRutaEvacuacion()) {
            listaCamposOpcionales.add(columnaPrediosSegunRutaEvacuacion);
            selccionCamposOpcionales.add(columnaPrediosSegunRutaEvacuacion);
        }
        if (isMostrarColPrediosSegunReactivoIncendios()) {
            listaCamposOpcionales.add(columnaPrediosSegunReactivoIncendios);
            selccionCamposOpcionales.add(columnaPrediosSegunReactivoIncendios);
        }
        if (isMostrarColPrediosSegunSenalizacionEvacuacion()) {
            listaCamposOpcionales.add(columnaPrediosSegunSenalizacionEvacuacion);
            selccionCamposOpcionales.add(columnaPrediosSegunSenalizacionEvacuacion);
        }
    }

    @Override
    public void resetearFiltros() {
        consultaAmbito.resetearNiveles();
        this.resetearListasEstSedesPredios();
        this.seleccionSector = null;
        this.seleccionZona = null;
        this.seleccionSenalizacionEvacuacion = null;
        this.seleccionEstructura = null;
        this.seleccionSistemaContraIncendio = null;  
        this.seleccionCondicionEstructura = null;
        this.seleccionAnalisisRutaEvacuacion = null;
        this.seleccionEstadoEstructura = null;
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
        this.seleccionSenalizacionEvacuacion = null;
        this.seleccionEstructura = null;
        this.seleccionSistemaContraIncendio = null;  
        this.seleccionCondicionEstructura = null;
        this.seleccionAnalisisRutaEvacuacion = null;
        this.seleccionEstadoEstructura = null;
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
        filtro.setEstructura(elementosSeleccionados(seleccionEstructura));
        filtro.setEstadoEstructura(seleccionEstadoEstructura != null ? seleccionEstadoEstructura : null);
        filtro.setSenalizacionEvacuacion(seleccionSenalizacionEvacuacion != null ? seleccionSenalizacionEvacuacion : null);
        filtro.setSistemaContraIncendio(seleccionSistemaContraIncendio != null ? seleccionSistemaContraIncendio : null);
        filtro.setAnalisisRutaEvacuacion(seleccionAnalisisRutaEvacuacion != null ? seleccionAnalisisRutaEvacuacion : null);
        filtro.setCondicionTipoEstructura(seleccionCondicionEstructura != null ? seleccionCondicionEstructura.getValor() : null);

        List<Object[]> resultados = facAmbito.consultarSeguridad(filtro);
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
                registro.setEstadoEstructura(Utilidades.obtenerMensaje(UtilConsulta.stringValueOf(fila[i++])));
                registro.setAnalisisRutaEvacuacion(Utilidades.obtenerMensaje(UtilConsulta.stringValueOf(fila[i++])));
                registro.setReactivoIncendios(Utilidades.obtenerMensaje(UtilConsulta.stringValueOf(fila[i++])));
                registro.setSenalizacionEvacuacion(Utilidades.obtenerMensaje(UtilConsulta.stringValueOf(fila[i++])));
                registro.setEdificio(UtilConsulta.stringValueOf(fila[i++]));
                registro.setEstructura(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]),getUsuarioSesion().getUsuario().getIdioma().getId()));
                registro.setCondicionEstructura(UtilConsulta.stringValueOf(fila[i++]));
                registro.setCondicionRutaEvacuacion(UtilConsulta.stringValueOf(fila[i++]));
                registro.setSenalizacionEvacuacionEdificio(Utilidades.obtenerMensaje(UtilConsulta.stringValueOf(fila[i++])));
                registro.setSistemaContraIncendio(Utilidades.obtenerMensaje(UtilConsulta.stringValueOf(fila[i++])));
                registro.setTotalEstudiantes(UtilConsulta.longValueOf(fila[i++]));
                //otros
                registro.setDirPredio(UtilConsulta.stringValueOf(fila[i++]));
                registro.setZona(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]),getUsuarioSesion().getUsuario().getIdioma().getId()));
                registro.setSector(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]),getUsuarioSesion().getUsuario().getIdioma().getId()));
                registro.setClima(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]),getUsuarioSesion().getUsuario().getIdioma().getId()));
                registro.setPropiedadPredio(UtilConsulta.stringValueOf(fila[i++]));
                registro.setCondicionAccesibilidad(Utilidades.obtenerMensaje(UtilConsulta.stringValueOf(fila[i++])));
                registro.setCondicionRA(Utilidades.obtenerMensaje(UtilConsulta.stringValueOf(fila[i++])));
                registro.setCondicionRN(Utilidades.obtenerMensaje(UtilConsulta.stringValueOf(fila[i++])));
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
                if (filtro.getEstadoEstructura() != null) {
                    registro.setPrediosSegunEstadoEstructura(UtilConsulta.longValueOf(fila[i++]));
                }
                if (filtro.getAnalisisRutaEvacuacion() != null) {
                    registro.setPrediosSegunRutaEvacuacion(UtilConsulta.longValueOf(fila[i++]));
                }
                if (filtro.getSistemaContraIncendio() != null) {
                    registro.setPrediosSegunReactivoIncendios(UtilConsulta.longValueOf(fila[i++]));
                }
                if (filtro.getSenalizacionEvacuacion() != null) {
                    registro.setPrediosSegunSenalizacionEvacuacion(UtilConsulta.longValueOf(fila[i++]));
                }
                registro.setTotalEstudiantes(UtilConsulta.longValueOf(fila[i++]));
                registro.setTotalPredios(UtilConsulta.longValueOf(fila[i++]));
                // totales
                totalConsulta.addNumPrediosSegunEstadoEstructura(registro.getPrediosSegunEstadoEstructura());
                totalConsulta.addNumPrediosSegunReactivoIncendios(registro.getPrediosSegunReactivoIncendios());
                totalConsulta.addNumPrediosSegunRutaEvacuacion(registro.getPrediosSegunRutaEvacuacion());
                totalConsulta.addNumPrediosSegunSenalizacionEvacuacion(registro.getPrediosSegunSenalizacionEvacuacion());
                totalConsulta.addNumTotalEstudiantes(registro.getTotalEstudiantes());
                totalConsulta.addNumTotalPredios(registro.getTotalPredios());
            }            
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

    public List<String> getSeleccionEstructura() {
        return seleccionEstructura;
    }

    public void setSeleccionEstructura(List<String> seleccionEstructura) {
        this.seleccionEstructura = seleccionEstructura;
    }

    public List<TipologiaValorNombre> getListaSistemaEstructural() {
        return listaSistemaEstructural;
    }

    public CalificacionCondicion getSeleccionCondicionEstructura() {
        return seleccionCondicionEstructura;
    }

    public void setSeleccionCondicionEstructura(CalificacionCondicion seleccionCondicionEstructura) {
        this.seleccionCondicionEstructura = seleccionCondicionEstructura;
    }

    public void setListaSistemaEstructural(List<TipologiaValorNombre> listaSistemaEstructural) {
        this.listaSistemaEstructural = listaSistemaEstructural;
    }

    public String getSeleccionSenalizacionEvacuacion() {
        return seleccionSenalizacionEvacuacion;
    }

    public void setSeleccionSenalizacionEvacuacion(String seleccionSenalizacionEvacuacion) {
        this.seleccionSenalizacionEvacuacion = seleccionSenalizacionEvacuacion;
    }

    public String getSeleccionSistemaContraIncendio() {
        return seleccionSistemaContraIncendio;
    }

    public void setSeleccionSistemaContraIncendio(String seleccionSistemaContraIncendio) {
        this.seleccionSistemaContraIncendio = seleccionSistemaContraIncendio;
    }

    public String getSeleccionAnalisisRutaEvacuacion() {
        return seleccionAnalisisRutaEvacuacion;
    }

    public void setSeleccionAnalisisRutaEvacuacion(String seleccionAnalisisRutaEvacuacion) {
        this.seleccionAnalisisRutaEvacuacion = seleccionAnalisisRutaEvacuacion;
    }

    public String getSeleccionEstadoEstructura() {
        return seleccionEstadoEstructura;
    }

    public void setSeleccionEstadoEstructura(String seleccionEstadoEstructura) {
        this.seleccionEstadoEstructura = seleccionEstadoEstructura;
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
        private String estadoEstructura;
        private String analisisRutaEvacuacion;
        private String reactivoIncendios;
        private String senalizacionEvacuacion;
        private String edificio;
        private String estructura;
        private String condicionEstructura;
        private String condicionRutaEvacuacion;
        private String senalizacionEvacuacionEdificio;
        private String sistemaContraIncendio;
        private Long totalEstudiantes;
        private Long totalPredios;
        private String dirPredio;
        private String zona;
        private String sector;
        private String clima;
        private String propiedadPredio;
        private String condicionAccesibilidad;
        private String condicionRA;
        private String condicionRN;
        private Long PrediosSegunEstadoEstructura;
        private Long PrediosSegunRutaEvacuacion;
        private Long PrediosSegunReactivoIncendios;
        private Long PrediosSegunSenalizacionEvacuacion;

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

        public String getEstadoEstructura() {
            return estadoEstructura;
        }

        public void setEstadoEstructura(String estadoEstructura) {
            this.estadoEstructura = estadoEstructura;
        }

        public String getAnalisisRutaEvacuacion() {
            return analisisRutaEvacuacion;
        }

        public void setAnalisisRutaEvacuacion(String analisisRutaEvacuacion) {
            this.analisisRutaEvacuacion = analisisRutaEvacuacion;
        }

        public String getReactivoIncendios() {
            return reactivoIncendios;
        }

        public void setReactivoIncendios(String reactivoIncendios) {
            this.reactivoIncendios = reactivoIncendios;
        }

        public String getSenalizacionEvacuacion() {
            return senalizacionEvacuacion;
        }

        public void setSenalizacionEvacuacion(String senalizacionEvacuacion) {
            this.senalizacionEvacuacion = senalizacionEvacuacion;
        }

        public String getEdificio() {
            return edificio;
        }

        public void setEdificio(String edificio) {
            this.edificio = edificio;
        }

        public String getEstructura() {
            return estructura;
        }

        public void setEstructura(String estructura) {
            this.estructura = estructura;
        }

        public String getCondicionEstructura() {
            return condicionEstructura;
        }

        public void setCondicionEstructura(String condicionEstructura) {
            this.condicionEstructura = condicionEstructura;
        }

        public String getCondicionRutaEvacuacion() {
            return condicionRutaEvacuacion;
        }

        public void setCondicionRutaEvacuacion(String condicionRutaEvacuacion) {
            this.condicionRutaEvacuacion = condicionRutaEvacuacion;
        }

        public String getSenalizacionEvacuacionEdificio() {
            return senalizacionEvacuacionEdificio;
        }

        public void setSenalizacionEvacuacionEdificio(String senalizacionEvacuacionEdificio) {
            this.senalizacionEvacuacionEdificio = senalizacionEvacuacionEdificio;
        }

        public String getSistemaContraIncendio() {
            return sistemaContraIncendio;
        }

        public void setSistemaContraIncendio(String sistemaContraIncendio) {
            this.sistemaContraIncendio = sistemaContraIncendio;
        }

        public Long getTotalEstudiantes() {
            return totalEstudiantes;
        }

        public void setTotalEstudiantes(Long totalEstudiantes) {
            this.totalEstudiantes = totalEstudiantes;
        }

        public Long getTotalPredios() {
            return totalPredios;
        }

        public void setTotalPredios(Long totalPredios) {
            this.totalPredios = totalPredios;
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

        public String getCondicionAccesibilidad() {
            return condicionAccesibilidad;
        }

        public void setCondicionAccesibilidad(String condicionAccesibilidad) {
            this.condicionAccesibilidad = condicionAccesibilidad;
        }

        public String getCondicionRA() {
            return condicionRA;
        }

        public void setCondicionRA(String condicionRA) {
            this.condicionRA = condicionRA;
        }

        public String getCondicionRN() {
            return condicionRN;
        }

        public void setCondicionRN(String condicionRN) {
            this.condicionRN = condicionRN;
        }
        
        public Long getPrediosSegunEstadoEstructura() {
            return PrediosSegunEstadoEstructura;
        }

        public void setPrediosSegunEstadoEstructura(Long PrediosSegunEstadoEstructura) {
            this.PrediosSegunEstadoEstructura = PrediosSegunEstadoEstructura;
        }

        public Long getPrediosSegunRutaEvacuacion() {
            return PrediosSegunRutaEvacuacion;
        }

        public void setPrediosSegunRutaEvacuacion(Long PrediosSegunRutaEvacuacion) {
            this.PrediosSegunRutaEvacuacion = PrediosSegunRutaEvacuacion;
        }

        public Long getPrediosSegunReactivoIncendios() {
            return PrediosSegunReactivoIncendios;
        }

        public void setPrediosSegunReactivoIncendios(Long PrediosSegunReactivoIncendios) {
            this.PrediosSegunReactivoIncendios = PrediosSegunReactivoIncendios;
        }

        public Long getPrediosSegunSenalizacionEvacuacion() {
            return PrediosSegunSenalizacionEvacuacion;
        }

        public void setPrediosSegunSenalizacionEvacuacion(Long PrediosSegunSenalizacionEvacuacion) {
            this.PrediosSegunSenalizacionEvacuacion = PrediosSegunSenalizacionEvacuacion;
        }
    }

    public class TotalConsulta {

        private Long numPrediosSegunSenalizacionEvacuacion;
        private Long numPrediosSegunReactivoIncendios;
        private Long numPrediosSegunRutaEvacuacion;
        private Long numPrediosSegunEstadoEstructura;
        private Long numTotalEstudiantes;
        private Long numTotalPredios;

        public void inicializar() {
            numPrediosSegunSenalizacionEvacuacion = 0L;
            numPrediosSegunReactivoIncendios = 0L;
            numPrediosSegunRutaEvacuacion = 0L;
            numPrediosSegunEstadoEstructura = 0L;
            numTotalEstudiantes = 0L;
            numTotalPredios = 0L;
        }

        public Long getNumPrediosSegunSenalizacionEvacuacion() {
            return numPrediosSegunSenalizacionEvacuacion;
        }

        public Long getNumPrediosSegunReactivoIncendios() {
            return numPrediosSegunReactivoIncendios;
        }

        public Long getNumPrediosSegunEstadoEstructura() {
            return numPrediosSegunEstadoEstructura;
        }

        public Long getNumPrediosSegunRutaEvacuacion() {
            return numPrediosSegunRutaEvacuacion;
        }

        public Long getNumTotalEstudiantes() {
            return numTotalEstudiantes;
        }

        public Long getNumTotalPredios() {
            return numTotalPredios;
        }
        
        public void addNumPrediosSegunSenalizacionEvacuacion(Long valor) {
            if (valor != null) {
                this.numPrediosSegunSenalizacionEvacuacion += valor;
            }
        }

        public void addNumPrediosSegunReactivoIncendios(Long valor) {
            if (valor != null) {
                this.numPrediosSegunReactivoIncendios += valor;
            }
        }

        public void addNumPrediosSegunRutaEvacuacion(Long valor) {
            if (valor != null) {
                this.numPrediosSegunRutaEvacuacion += valor;
            }
        }

        public void addNumPrediosSegunEstadoEstructura(Long valor) {
            if (valor != null) {
                this.numPrediosSegunEstadoEstructura += valor;
            }
        }

        public void addNumTotalEstudiantes(Long valor) {
            if (valor != null) {
                this.numTotalEstudiantes += valor;
            }
        }
        
        public void addNumTotalPredios(Long valor) {
            if (valor != null) {
                this.numTotalPredios += valor;
            }
        }
    }

    // mostrar columnas  
    public boolean isMostrarColCondicionAccesibilidad() {
        return isAgrupamientoPredio();
    }
    
    public boolean isMostrarColCondicionRA() {
        return isAgrupamientoPredio();
    }
    
    public boolean isMostrarColCondicionRN() {
        return isAgrupamientoPredio();
    }
    
    public boolean isMostrarColEstadoEstructura() {
        return isAgrupamientoPredio();
    }

    public boolean isMostrarColAnalisisRutaEvacuacion() {
        return isAgrupamientoPredio();
    }

    public boolean isMostrarColReactivoIncendios() {
        return isAgrupamientoPredio();
    }

    public boolean isMostrarColSenalizacionEvacuacion() {
        return isAgrupamientoPredio();
    }

    public boolean isMostrarColEdificio() {
        return isAgrupamientoPredio();
    }

    public boolean isMostrarColEstructura() {
        return isAgrupamientoPredio();
    }

    public boolean isMostrarColCondicionEstructura() {
        return isAgrupamientoPredio();
    }

    public boolean isMostrarColCondicionRutaEvacuacion() {
        return isAgrupamientoPredio();
    }

    public boolean isMostrarColSenalizacionEvacuacionEdificio() {
        return isAgrupamientoPredio();
    }

    public boolean isMostrarColSistemaContraIncendio() {
        return isAgrupamientoPredio();
    }

    public boolean isMostrarColTotalEstudiantes() {
        return true;
    }
    
    public boolean isMostrarColTotalPredios() {
        return !isAgrupamientoPredio();
    }

    public boolean isMostrarColPrediosSegunEstadoEstructura() {
        return !isAgrupamientoPredio() && seleccionEstadoEstructura != null;
    }

    public boolean isMostrarColPrediosSegunRutaEvacuacion() {
        return !isAgrupamientoPredio() && seleccionAnalisisRutaEvacuacion != null;
    }

    public boolean isMostrarColPrediosSegunReactivoIncendios() {
        return !isAgrupamientoPredio() && seleccionSistemaContraIncendio != null;
    }

    public boolean isMostrarColPrediosSegunSenalizacionEvacuacion() {
        return !isAgrupamientoPredio() && seleccionSenalizacionEvacuacion != null;
    }

    //getter de columnas
    public String getColumnaEstadoEstructura() {
        return columnaEstadoEstructura;
    }

    public String getColumnaAnalisisRutaEvacuacion() {
        return columnaAnalisisRutaEvacuacion;
    }

    public String getColumnaReactivoIncendios() {
        return columnaReactivoIncendios;
    }

    public String getColumnaSenalizacionEvacuacion() {
        return columnaSenalizacionEvacuacion;
    }

    public String getColumnaEdificio() {
        return columnaEdificio;
    }

    public String getColumnaEstructura() {
        return columnaEstructura;
    }

    public String getColumnaCondicionEstructura() {
        return columnaCondicionEstructura;
    }

    public String getColumnaCondicionRutaEvacuacion() {
        return columnaCondicionRutaEvacuacion;
    }

    public String getColumnaSenalizacionEvacuacionEdificio() {
        return columnaSenalizacionEvacuacionEdificio;
    }

    public String getColumnaSistemaContraIncendio() {
        return columnaSistemaContraIncendio;
    }

    public String getColumnaTotalEstudiantes() {
        return columnaTotalEstudiantes;
    }

    public String getColumnaTotalPredios() {
        return columnaTotalPredios;
    }
    
    public String getColumnaPrediosSegunEstadoEstructura() {
        return columnaPrediosSegunEstadoEstructura;
    }

    public String getColumnaPrediosSegunRutaEvacuacion() {
        return columnaPrediosSegunRutaEvacuacion;
    }

    public String getColumnaPrediosSegunReactivoIncendios() {
        return columnaPrediosSegunReactivoIncendios;
    }

    public String getColumnaPrediosSegunSenalizacionEvacuacion() {
        return columnaPrediosSegunSenalizacionEvacuacion;
    }

    public String getColumnaCondicionAccesibilidad() {
        return columnaCondicionAccesibilidad;
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
