/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.consulta.ambito.migracion;

import co.stackpointer.cier.vistacontrolador.consulta.ambito.ConsultaAmbito;
import co.stackpointer.cier.modelo.entidad.instrumento.TipologiaValorNombre;
import co.stackpointer.cier.modelo.filtro.ambito.FiltroAmbitoMigracion;
import co.stackpointer.cier.modelo.tipo.ParamNivelConsul;
import co.stackpointer.cier.modelo.tipo.TipologiaCodigo;
import co.stackpointer.cier.modelo.utilidad.UtilConsulta;
import co.stackpointer.cier.vista.Utilidades;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.omnifaces.util.Messages;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author David Paz A.
 */
@ManagedBean(name = "migracionView")
@ViewScoped
public class MigracionView extends ConsultaAmbito{
    

    @ManagedProperty(value = "#{consultaAmbito}")
    private ConsultaAmbito consultaAmbito;
    private List<String> seleccionPropietarios;
    private List<TipologiaValorNombre> listaPropietarios;
    private Registro totalConsulta = new Registro();
    private List<Registro> lista;
    private LazyDataModel<Registro> lazyLista;
    private FiltroAmbitoMigracion filtro = new FiltroAmbitoMigracion();
    private static Integer periodoMigracion = 201606;
    private boolean generalidades;
    private boolean riesgos;
    private boolean ctrlVigilancia;
    private boolean seguridad;
    private boolean accesibilidad;
    private boolean confort;
    private boolean propiedad;
    private boolean oferta;
    //Generalidades
    private final String columnaNumJorEst = Utilidades.obtenerMensaje("consultas.ambito.generalidades.columna.num.jor.est");
    private final String columnaEstudiantesTotal = Utilidades.obtenerMensaje("consultas.ambito.generalidades.columna.estudiantes.total");
    private final String columnaAreaConstruccion = Utilidades.obtenerMensaje("consultas.ambito.generalidades.columna.area.construccion.total");
    private final String columnaAreaRealPredio = Utilidades.obtenerMensaje("consultas.ambito.generalidades.columna.area.real.predio");
    //Propiedad
    private final String columnaTipoPropietario = Utilidades.obtenerMensaje("consultas.ambito.propiedad.columna.tipo.propietario");
    private final String columnaVerificacionPropiedad = Utilidades.obtenerMensaje("consultas.ambito.propiedad.columna.verificacion.propiedad");
    //Riesgos
    private final String columnaTipoRN1 = Utilidades.obtenerMensaje("consulta.dinamica.column.RN1");
    private final String columnaCondRN1 = Utilidades.obtenerMensaje("consulta.dinamica.column.RN2");
    private final String columnaTipoRN2 = Utilidades.obtenerMensaje("consulta.dinamica.column.RN3");
    private final String columnaCondRN2 = Utilidades.obtenerMensaje("consulta.dinamica.column.RN4");
    private final String columnaTipoRN3 = Utilidades.obtenerMensaje("consulta.dinamica.column.RN5");
    private final String columnaCondRN3 = Utilidades.obtenerMensaje("consulta.dinamica.column.RN6");
    private final String columnaPoblacionAfectadaRN = Utilidades.obtenerMensaje("consultas.ambito.column.poblacionAfectada.RiesgoNatural");
    private final String columnaTipoRA1 = Utilidades.obtenerMensaje("consulta.dinamica.column.RA1");
    private final String columnaCondRA1 = Utilidades.obtenerMensaje("consulta.dinamica.column.RA2");
    private final String columnaTipoRA2 = Utilidades.obtenerMensaje("consulta.dinamica.column.RA3");
    private final String columnaCondRA2 = Utilidades.obtenerMensaje("consulta.dinamica.column.RA4");
    private final String columnaTipoRA3 = Utilidades.obtenerMensaje("consulta.dinamica.column.RA5");
    private final String columnaCondRA3 = Utilidades.obtenerMensaje("consulta.dinamica.column.RA6");
    private final String columnaPoblacionAfectadaRA = Utilidades.obtenerMensaje("consultas.ambito.column.poblacionAfectada.RiesgoAntropico");
    //Oferta
    private final String columnaPosConstruccionPrimerPiso = Utilidades.obtenerMensaje("consultas.ambito.ampliacion.columna.pos.cons.primer");
    private final String columnaPosConstruccionAltura = Utilidades.obtenerMensaje("consultas.ambito.ampliacion.columna.pos.cons.altura");
    private final String columnaPosConstruccionTotal = Utilidades.obtenerMensaje("consultas.ambito.ampliacion.columna.pos.cons.total");
    //Control Y Vigilancia
    private final String columnaTipoCerramiento = Utilidades.obtenerMensaje("consultas.ambito.ctrlvigi.columna.tipoCerramiento");
    private final String columnaCondicionCerramiento = Utilidades.obtenerMensaje("consultas.ambito.ctrlvigi.columna.condicionCerramiento");
    private final String columnaCondicionOrdenPublico = Utilidades.obtenerMensaje("consultas.ambito.ctrlvigi.columna.condicionOrdenPublico");
    private final String columnaVulnerabilidad = Utilidades.obtenerMensaje("consultas.ambito.ctrlvigi.columna.vulnerabilidad");
    private final String columnaTotalEdificios = Utilidades.obtenerMensaje("consultas.ambito.ctrlvigi.columna.totalEdificios");
    //Seguridad
    private final String columnaEstadoEstructura = Utilidades.obtenerMensaje("consultas.ambito.seguridad.columna.estadoEstructura");
    private final String columnaAnalisisRutaEvacuacion = Utilidades.obtenerMensaje("consultas.ambito.seguridad.columna.analisisRutaEvacuacion");
    private final String columnaReactivoIncendios = Utilidades.obtenerMensaje("consultas.ambito.seguridad.columna.reactivoIncendios");
    private final String columnaSenalizacionEvacuacion = Utilidades.obtenerMensaje("consultas.ambito.seguridad.columna.senalizacionEvacuacion");
    //Accesibilidad
    private final String columnaAcceso1 = Utilidades.obtenerMensaje("consulta.dinamica.column.ACC1");
    private final String columnaCondAcceso1 = Utilidades.obtenerMensaje("consulta.dinamica.column.ACC2");
    private final String columnaAcceso2 = Utilidades.obtenerMensaje("consulta.dinamica.column.ACC3");
    private final String columnaCondAcceso2 = Utilidades.obtenerMensaje("consulta.dinamica.column.ACC4");
    private final String columnaAcceso3 = Utilidades.obtenerMensaje("consulta.dinamica.column.ACC5");
    private final String columnaCondAcceso3 = Utilidades.obtenerMensaje("consulta.dinamica.column.ACC6");
    //Confort
    private final String columnaEspaciosAislTermPiso = Utilidades.obtenerMensaje("consultas.ambito.confort.columna.espaciosAislTermPiso");
    private final String columnaEspaciosAislTermTecho = Utilidades.obtenerMensaje("consultas.ambito.confort.columna.espaciosAislTermTecho");
    private final String columnaEspaciosAislTermPared = Utilidades.obtenerMensaje("consultas.ambito.confort.columna.espaciosAislTermPared");
    private final String columnaEspaciosAislTermVentana = Utilidades.obtenerMensaje("consultas.ambito.confort.columna.espaciosAislTermVentana");
    private final String columnaTotalInodoros = Utilidades.obtenerMensaje("consultas.ambito.confort.columna.totalInodoros");
    private final String columnaTotalDuchas = Utilidades.obtenerMensaje("consultas.ambito.confort.columna.totalDuchas");
    private final String columnaTotalLavamano = Utilidades.obtenerMensaje("consultas.ambito.confort.columna.totalLavamano");
    private final String columnaTotalOrinales = Utilidades.obtenerMensaje("consultas.ambito.confort.columna.totalOrinales");

    @PostConstruct
    public void inicializar() {
        //super.init();
        listaPropietarios = facParam.consultarValoresTipologia(TipologiaCodigo.TIPO_PROPIETARIO_PREDIO.getValor(), getUsuarioSesion().getUsuario().getIdioma().getId());
        this.lista = new ArrayList<Registro>();
        this.cargarCamposOpcionales();
    }

    public void consultar() {
        try {
            filtro.inicializar();
            filtro.setPeriodo(periodo);
            filtro.setAgrupamiento(ParamNivelConsul.PREDIO.getNivel());
            filtro.setIdioma(getUsuarioSesion().getUsuario().getIdioma().getId());

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
            filtro.setGeneralidades(generalidades);
            filtro.setPropiedad(propiedad);
            filtro.setRiesgos(riesgos);
            filtro.setOferta(oferta);
            filtro.setCtrlVigilancia(ctrlVigilancia);
            filtro.setSeguridad(seguridad);
            filtro.setAccesibilidad(accesibilidad);
            filtro.setConfort(confort);

            List<Object[]> resultados = facAmbito.consultarMigracion(filtro);
            lista = new ArrayList<Registro>(resultados.size());
            this.inicializarTotalesConsulta();
            for (Object[] fila : resultados) {
                Registro registro = new Registro();
                int i = 0;
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
                registro.setEtnias(verificarEtnias(UtilConsulta.stringValueOf(fila[i++])));
                //indicadores                     

                //indicadores 
                if (generalidades) {
                    registro.setNumJornadasEstudiantes(UtilConsulta.longValueOf(fila[i++]));
                    registro.setTotalEstudiantes(UtilConsulta.longValueOf(fila[i++]));
                    registro.setAreaConstruccion(UtilConsulta.doubleValueOf(fila[i++]));
                    registro.setAreaRealPredio(UtilConsulta.doubleValueOf(fila[i++]));
                }
                if (propiedad) {
                    //propiedad
                    registro.setPropiedadPredio(Utilidades.obtenerMensaje(UtilConsulta.stringValueOf(fila[i++])));
                    Object idtip = fila[i++];
                    if (UtilConsulta.stringValueOf(idtip).equalsIgnoreCase("sql.ind.general.noAplica")) {
                        registro.setTipoPropietario(Utilidades.obtenerMensaje(UtilConsulta.stringValueOf(idtip)));
                    } else {
                        registro.setTipoPropietario(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(idtip), getUsuarioSesion().getUsuario().getIdioma().getId()));
                    }
                    registro.setVerificacionPropiedad(Utilidades.obtenerMensaje(UtilConsulta.stringValueOf(fila[i++])));
                }
                if (riesgos) {
                    registro.setTipoRN1(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]), getUsuarioSesion().getUsuario().getIdioma().getId()));
                    registro.setCondRN1(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setTipoRN2(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]), getUsuarioSesion().getUsuario().getIdioma().getId()));
                    registro.setCondRN2(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setTipoRN3(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]), getUsuarioSesion().getUsuario().getIdioma().getId()));
                    registro.setCondRN3(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setPoblacionAfectadaRN(UtilConsulta.longValueOf(fila[i++]));
                    registro.setTipoRA1(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]), getUsuarioSesion().getUsuario().getIdioma().getId()));
                    registro.setCondRA1(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setTipoRA2(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]), getUsuarioSesion().getUsuario().getIdioma().getId()));
                    registro.setCondRA2(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setTipoRA3(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]), getUsuarioSesion().getUsuario().getIdioma().getId()));
                    registro.setCondRA3(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setPoblacionAfectadaRA(UtilConsulta.longValueOf(fila[i++]));
                }
                if (oferta) {
                    registro.setPosConstruccionPrimerPiso(UtilConsulta.doubleValueOf(fila[i++]));
                    registro.setPosConstruccionAltura(UtilConsulta.doubleValueOf(fila[i++]));
                    registro.setPosConstruccionTotal(UtilConsulta.doubleValueOf(fila[i++]));
                }
                if (ctrlVigilancia) {
                    String tipoCerramiento = UtilConsulta.stringValueOf(fila[i++]);
                    tipoCerramiento = tipoCerramiento != null ? tipoCerramiento : "sql.ind.general.noTiene";
                    if (tipoCerramiento.equalsIgnoreCase("sql.ind.general.noTiene")) {
                        registro.setTipoCerramiento(Utilidades.obtenerMensaje(tipoCerramiento));
                    } else {
                        registro.setTipoCerramiento(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(tipoCerramiento), getUsuarioSesion().getUsuario().getIdioma().getId()));
                    }
                    registro.setCondicionCerramiento(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCondicionOrdenPublico(Utilidades.obtenerMensaje(UtilConsulta.stringValueOf(fila[i++])));
                    registro.setVulnerabilidad(Utilidades.obtenerMensaje(UtilConsulta.stringValueOf(fila[i++])));
                    registro.setTotalEdificios(UtilConsulta.longValueOf(fila[i++]));
                }
                if (seguridad) {
                    registro.setEstadoEstructura(Utilidades.obtenerMensaje(UtilConsulta.stringValueOf(fila[i++])));
                    registro.setAnalisisRutaEvacuacion(Utilidades.obtenerMensaje(UtilConsulta.stringValueOf(fila[i++])));
                    registro.setReactivoIncendios(Utilidades.obtenerMensaje(UtilConsulta.stringValueOf(fila[i++])));
                    registro.setSenalizacionEvacuacion(Utilidades.obtenerMensaje(UtilConsulta.stringValueOf(fila[i++])));
                }
                if (accesibilidad) {
                    registro.setTipoAcceso1(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]), getUsuarioSesion().getUsuario().getIdioma().getId()));
                    registro.setCondiconAcceso1(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setTipoAcceso2(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]), getUsuarioSesion().getUsuario().getIdioma().getId()));
                    registro.setCondiconAcceso2(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setTipoAcceso3(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]), getUsuarioSesion().getUsuario().getIdioma().getId()));
                    registro.setCondiconAcceso3(UtilConsulta.stringValueOf(fila[i++]));
                }
                if (confort) {
                    registro.setEspaciosAislTermPiso(UtilConsulta.longValueOf(fila[i++]));
                    registro.setEspaciosAislTermTecho(UtilConsulta.longValueOf(fila[i++]));
                    registro.setEspaciosAislTermPared(UtilConsulta.longValueOf(fila[i++]));
                    registro.setEspaciosAislTermVentana(UtilConsulta.longValueOf(fila[i++]));
                    registro.setTotalInodoros(UtilConsulta.longValueOf(fila[i++]));
                    registro.setTotalDuchas(UtilConsulta.longValueOf(fila[i++]));
                    registro.setTotalLavamano(UtilConsulta.longValueOf(fila[i++]));
                    registro.setTotalOrinales(UtilConsulta.longValueOf(fila[i++]));
                }
                // totales
                this.totalizarRegistro(registro);
                lista.add(registro);
            }
            
            //se instancia la lazyList para el resultado de consulta de migracion
            lazyLista = new LazyMigracionDataModel(lista);
        } catch (Exception e) {
            Messages.addFlashGlobalError(e.getMessage());
            //UtilOut.println(e);
        }
    }

    @Override
    public void resetearFiltros() {
        consultaAmbito.resetearNiveles();
        this.resetearListasEstSedesPredios();
        this.seleccionSector = null;
        this.seleccionZona = null;
        this.seleccionPropietarios = null;
        //this.seleccionAgrupamiento = ParamNivelConsul.PREDIO.getNivel();
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
        this.seleccionPropietarios = null;
        //this.seleccionAgrupamiento = ParamNivelConsul.PREDIO.getNivel();
        //consultaAmbito.nivelDPAOfAccesoUsuario();
        this.resetearConsulta();
    }

    private void resetearConsulta() {
        // columnas
        this.cargarCamposOpcionales();
        // resultados
        this.lista.clear();
        lazyLista = new LazyMigracionDataModel(lista);
        this.inicializarTotalesConsulta();
    }

    public void cargarCamposOpcionales() {
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

        if (generalidades) {
            listaCamposOpcionales.add(columnaAreaRealPredio);
            listaCamposOpcionales.add(columnaNumJorEst);
            listaCamposOpcionales.add(columnaEstudiantesTotal);
            listaCamposOpcionales.add(columnaAreaConstruccion);
            // campos por defecto
            selccionCamposOpcionales.add(columnaAreaRealPredio);
            selccionCamposOpcionales.add(columnaNumJorEst);
            selccionCamposOpcionales.add(columnaEstudiantesTotal);
            selccionCamposOpcionales.add(columnaAreaConstruccion);
        }
        if (propiedad) {
            listaCamposOpcionales.add(columnaPropPredio);
            listaCamposOpcionales.add(columnaTipoPropietario);
            listaCamposOpcionales.add(columnaVerificacionPropiedad);
            // campos por defecto
            selccionCamposOpcionales.add(columnaPropPredio);
            selccionCamposOpcionales.add(columnaTipoPropietario);
            selccionCamposOpcionales.add(columnaVerificacionPropiedad);
        }
        if (riesgos) {
            listaCamposOpcionales.add(columnaTipoRN1);
            listaCamposOpcionales.add(columnaCondRN1);
            listaCamposOpcionales.add(columnaTipoRN2);
            listaCamposOpcionales.add(columnaCondRN2);
            listaCamposOpcionales.add(columnaTipoRN3);
            listaCamposOpcionales.add(columnaCondRN3);
            listaCamposOpcionales.add(columnaPoblacionAfectadaRN);
            listaCamposOpcionales.add(columnaTipoRA1);
            listaCamposOpcionales.add(columnaCondRA1);
            listaCamposOpcionales.add(columnaTipoRA2);
            listaCamposOpcionales.add(columnaCondRA2);
            listaCamposOpcionales.add(columnaTipoRA3);
            listaCamposOpcionales.add(columnaCondRA3);
            listaCamposOpcionales.add(columnaPoblacionAfectadaRA);
            // campos por defecto
            selccionCamposOpcionales.add(columnaTipoRN1);
            selccionCamposOpcionales.add(columnaCondRN1);
            selccionCamposOpcionales.add(columnaTipoRN2);
            selccionCamposOpcionales.add(columnaCondRN2);
            selccionCamposOpcionales.add(columnaTipoRN3);
            selccionCamposOpcionales.add(columnaCondRN3);
            selccionCamposOpcionales.add(columnaPoblacionAfectadaRN);
            selccionCamposOpcionales.add(columnaTipoRA1);
            selccionCamposOpcionales.add(columnaCondRA1);
            selccionCamposOpcionales.add(columnaTipoRA2);
            selccionCamposOpcionales.add(columnaCondRA2);
            selccionCamposOpcionales.add(columnaTipoRA3);
            selccionCamposOpcionales.add(columnaCondRA3);
            selccionCamposOpcionales.add(columnaPoblacionAfectadaRA);
        }
        if (oferta) {
            listaCamposOpcionales.add(columnaPosConstruccionAltura);
            listaCamposOpcionales.add(columnaPosConstruccionPrimerPiso);
            listaCamposOpcionales.add(columnaPosConstruccionTotal);
            // campos por defecto
            selccionCamposOpcionales.add(columnaPosConstruccionAltura);
            selccionCamposOpcionales.add(columnaPosConstruccionPrimerPiso);
            selccionCamposOpcionales.add(columnaPosConstruccionTotal);
        }
        if (ctrlVigilancia) {
            listaCamposOpcionales.add(columnaTipoCerramiento);
            listaCamposOpcionales.add(columnaCondicionCerramiento);
            listaCamposOpcionales.add(columnaCondicionOrdenPublico);
            listaCamposOpcionales.add(columnaVulnerabilidad);
            listaCamposOpcionales.add(columnaTotalEdificios);
            // campos por defecto
            selccionCamposOpcionales.add(columnaTipoCerramiento);
            selccionCamposOpcionales.add(columnaCondicionCerramiento);
            selccionCamposOpcionales.add(columnaCondicionOrdenPublico);
            selccionCamposOpcionales.add(columnaVulnerabilidad);
            selccionCamposOpcionales.add(columnaTotalEdificios);
        }
        if (seguridad) {
            listaCamposOpcionales.add(columnaEstadoEstructura);
            listaCamposOpcionales.add(columnaAnalisisRutaEvacuacion);
            listaCamposOpcionales.add(columnaReactivoIncendios);
            listaCamposOpcionales.add(columnaSenalizacionEvacuacion);
            // campos por defecto
            selccionCamposOpcionales.add(columnaEstadoEstructura);
            selccionCamposOpcionales.add(columnaAnalisisRutaEvacuacion);
            selccionCamposOpcionales.add(columnaReactivoIncendios);
            selccionCamposOpcionales.add(columnaSenalizacionEvacuacion);
        }
        if (accesibilidad) {
            listaCamposOpcionales.add(columnaAcceso1);
            listaCamposOpcionales.add(columnaCondAcceso1);
            listaCamposOpcionales.add(columnaAcceso2);
            listaCamposOpcionales.add(columnaCondAcceso2);
            listaCamposOpcionales.add(columnaAcceso3);
            listaCamposOpcionales.add(columnaCondAcceso3);
            // campos por defecto
            selccionCamposOpcionales.add(columnaAcceso1);
            selccionCamposOpcionales.add(columnaCondAcceso1);
            selccionCamposOpcionales.add(columnaAcceso2);
            selccionCamposOpcionales.add(columnaCondAcceso2);
            selccionCamposOpcionales.add(columnaAcceso3);
            selccionCamposOpcionales.add(columnaCondAcceso3);
        }
        if (confort) {
            listaCamposOpcionales.add(columnaEspaciosAislTermPiso);
            listaCamposOpcionales.add(columnaEspaciosAislTermTecho);
            listaCamposOpcionales.add(columnaEspaciosAislTermPared);
            listaCamposOpcionales.add(columnaEspaciosAislTermVentana);
            listaCamposOpcionales.add(columnaTotalInodoros);
            listaCamposOpcionales.add(columnaTotalDuchas);
            listaCamposOpcionales.add(columnaTotalLavamano);
            listaCamposOpcionales.add(columnaTotalOrinales);
            // campos por defecto
            selccionCamposOpcionales.add(columnaEspaciosAislTermPiso);
            selccionCamposOpcionales.add(columnaEspaciosAislTermTecho);
            selccionCamposOpcionales.add(columnaEspaciosAislTermPared);
            selccionCamposOpcionales.add(columnaEspaciosAislTermVentana);
            selccionCamposOpcionales.add(columnaTotalInodoros);
            selccionCamposOpcionales.add(columnaTotalDuchas);
            selccionCamposOpcionales.add(columnaTotalLavamano);
            selccionCamposOpcionales.add(columnaTotalOrinales);
        }        
    }

    private void inicializarTotalesConsulta() {
        totalConsulta.setTotalEstudiantes(0L);
        //totalConsulta.setCostoArriendo(0D);
        //totalConsulta.setTotalPredios(0L);
    }

    private void totalizarRegistro(Registro registro) {
        // predio

        if (registro.getTotalEstudiantes() != null) {
            Long valor = totalConsulta.getTotalEstudiantes() != null ? totalConsulta.getTotalEstudiantes() : 0L;
            totalConsulta.setTotalEstudiantes(valor + registro.getTotalEstudiantes());
        }
        /*if (registro.getTotalPredProbLegalizacion() != null) {
         Long valor = totalConsulta.getTotalPredProbLegalizacion() != null ? totalConsulta.getTotalPredProbLegalizacion() : 0L;
         totalConsulta.setTotalPredProbLegalizacion(valor + registro.getTotalPredProbLegalizacion());
         }
         if (registro.getTotalEstudPredProbLegalizacion() != null) {
         Long valor = totalConsulta.getTotalEstudPredProbLegalizacion() != null ? totalConsulta.getTotalEstudPredProbLegalizacion() : 0L;
         totalConsulta.setTotalEstudPredProbLegalizacion(valor + registro.getTotalEstudPredProbLegalizacion());
         }
         if (registro.getCostoArriendo() != null) {
         Double valor = totalConsulta.getCostoArriendo() != null ? totalConsulta.getCostoArriendo() : 0D;
         totalConsulta.setCostoArriendo(valor + registro.getCostoArriendo());
         }
         if (registro.getTotalPredios() != null) {
         Long valor = totalConsulta.getTotalPredios() != null ? totalConsulta.getTotalPredios() : 0L;
         totalConsulta.setTotalPredios(valor + registro.getTotalPredios());
         }*/
    }

    public List<String> getSeleccionPropietarios() {
        return seleccionPropietarios;
    }

    public void setSeleccionPropietarios(List<String> seleccionPropietarios) {
        this.seleccionPropietarios = seleccionPropietarios;
    }

    public List<TipologiaValorNombre> getListaPropietarios() {
        return listaPropietarios;
    }

    public void setListaPropietarios(List<TipologiaValorNombre> listaPropietarios) {
        this.listaPropietarios = listaPropietarios;
    }

    public List<Registro> getLista() {
        return lista;
    }

    public LazyDataModel<Registro> getLazyLista() {
        return lazyLista;
    }
    
    public Registro getTotalConsulta() {
        return totalConsulta;
    }

    public String getColumnaTipoCerramiento() {
        return columnaTipoCerramiento;
    }

    public String getColumnaCondicionCerramiento() {
        return columnaCondicionCerramiento;
    }

    public String getColumnaCondicionOrdenPublico() {
        return columnaCondicionOrdenPublico;
    }

    public String getColumnaVulnerabilidad() {
        return columnaVulnerabilidad;
    }

    public String getColumnaTotalEdificios() {
        return columnaTotalEdificios;
    }

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

    public String getColumnaAcceso1() {
        return columnaAcceso1;
    }

    public String getColumnaCondAcceso1() {
        return columnaCondAcceso1;
    }

    public String getColumnaAcceso2() {
        return columnaAcceso2;
    }

    public String getColumnaCondAcceso2() {
        return columnaCondAcceso2;
    }

    public String getColumnaAcceso3() {
        return columnaAcceso3;
    }

    public String getColumnaCondAcceso3() {
        return columnaCondAcceso3;
    }

    public String getColumnaEspaciosAislTermPiso() {
        return columnaEspaciosAislTermPiso;
    }

    public String getColumnaEspaciosAislTermTecho() {
        return columnaEspaciosAislTermTecho;
    }

    public String getColumnaEspaciosAislTermPared() {
        return columnaEspaciosAislTermPared;
    }

    public String getColumnaEspaciosAislTermVentana() {
        return columnaEspaciosAislTermVentana;
    }

    public String getColumnaTotalInodoros() {
        return columnaTotalInodoros;
    }

    public String getColumnaTotalDuchas() {
        return columnaTotalDuchas;
    }

    public String getColumnaTotalLavamano() {
        return columnaTotalLavamano;
    }

    public String getColumnaTotalOrinales() {
        return columnaTotalOrinales;
    }

    public boolean isOferta() {
        return oferta;
    }

    public void setOferta(boolean oferta) {
        this.oferta = oferta;
    }

    public String getColumnaNumJorEst() {
        return columnaNumJorEst;
    }

    public String getColumnaEstudiantesTotal() {
        return columnaEstudiantesTotal;
    }

    public String getColumnaAreaConstruccion() {
        return columnaAreaConstruccion;
    }

    public String getColumnaAreaRealPredio() {
        return columnaAreaRealPredio;
    }

    public String getColumnaTipoPropietario() {
        return columnaTipoPropietario;
    }

    public String getColumnaVerificacionPropiedad() {
        return columnaVerificacionPropiedad;
    }

    public String getColumnaTipoRN1() {
        return columnaTipoRN1;
    }

    public String getColumnaCondRN1() {
        return columnaCondRN1;
    }

    public String getColumnaTipoRN2() {
        return columnaTipoRN2;
    }

    public String getColumnaCondRN2() {
        return columnaCondRN2;
    }

    public String getColumnaTipoRN3() {
        return columnaTipoRN3;
    }

    public String getColumnaCondRN3() {
        return columnaCondRN3;
    }

    public String getColumnaPoblacionAfectadaRN() {
        return columnaPoblacionAfectadaRN;
    }

    public String getColumnaTipoRA1() {
        return columnaTipoRA1;
    }

    public String getColumnaCondRA1() {
        return columnaCondRA1;
    }

    public String getColumnaTipoRA2() {
        return columnaTipoRA2;
    }

    public String getColumnaCondRA2() {
        return columnaCondRA2;
    }

    public String getColumnaTipoRA3() {
        return columnaTipoRA3;
    }

    public String getColumnaCondRA3() {
        return columnaCondRA3;
    }

    public String getColumnaPoblacionAfectadaRA() {
        return columnaPoblacionAfectadaRA;
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

    public ConsultaAmbito getConsultaAmbito() {
        return consultaAmbito;
    }

    public void setConsultaAmbito(ConsultaAmbito consultaAmbito) {
        this.consultaAmbito = consultaAmbito;
    }

    public boolean isGeneralidades() {
        return generalidades;
    }

    public void setGeneralidades(boolean generalidades) {
        this.generalidades = generalidades;
    }

    public boolean isRiesgos() {
        return riesgos;
    }

    public void setRiesgos(boolean riesgos) {
        this.riesgos = riesgos;
    }

    public boolean isCtrlVigilancia() {
        return ctrlVigilancia;
    }

    public void setCtrlVigilancia(boolean ctrlVigilancia) {
        this.ctrlVigilancia = ctrlVigilancia;
    }

    public boolean isSeguridad() {
        return seguridad;
    }

    public void setSeguridad(boolean seguridad) {
        this.seguridad = seguridad;
    }

    public boolean isAccesibilidad() {
        return accesibilidad;
    }

    public void setAccesibilidad(boolean accesibilidad) {
        this.accesibilidad = accesibilidad;
    }

    public boolean isConfort() {
        return confort;
    }

    public void setConfort(boolean confort) {
        this.confort = confort;
    }

    public boolean isPropiedad() {
        return propiedad;
    }

    public void setPropiedad(boolean propiedad) {
        this.propiedad = propiedad;
    }
}
