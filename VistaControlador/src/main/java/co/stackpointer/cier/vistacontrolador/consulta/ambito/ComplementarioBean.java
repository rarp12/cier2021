/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.consulta.ambito;

import co.stackpointer.cier.modelo.entidad.instrumento.TipologiaValorNombre;
import co.stackpointer.cier.modelo.filtro.ambito.FiltroAmbitoComplementarios;
import co.stackpointer.cier.modelo.tipo.SiNo;
import co.stackpointer.cier.modelo.tipo.TipologiaCodigo;
import co.stackpointer.cier.modelo.utilidad.UtilCadena;
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
@ManagedBean(name = "complementario")
@ViewScoped
public class ComplementarioBean extends ConsultaAmbito {

    @ManagedProperty(value = "#{consultaAmbito}")
    private ConsultaAmbito consultaAmbito;    
    private Registro totalConsulta = new Registro();
    private List<Registro> lista;
    private FiltroAmbitoComplementarios filtro = new FiltroAmbitoComplementarios();
    private List<TipologiaValorNombre> usoSueloList;
    private String codUsoSuelo;
    private List<TipologiaValorNombre> tipoDocPropiedadList;
    private String codTipoDocPropiedad;
    private List<TipologiaValorNombre> tenenciaLoteList;
    private String codTenenciaLote;
    private SiNo tieneSaneamiento;
    private List<TipologiaValorNombre> tipoSaneamientoList;
    private String codTipoSaneamiento;
    private boolean checkGeneralidades;
    private boolean checkTenencia;
    private boolean checkSaneamiento;
    private boolean checkAgua;
    private final String columnaUsoSuelo = Utilidades.obtenerMensaje("consultas.ambito.complementario.uso.normativo");
    private final String columnaTipoDocumentoProp = Utilidades.obtenerMensaje("consultas.ambito.complementario.tipo.docu.prop");
    private final String columnaRegistroCatastral = Utilidades.obtenerMensaje("consultas.ambito.complementario.reg.catastral");
    private final String columnaMatriculaInmobiliaria = Utilidades.obtenerMensaje("consultas.ambito.complementario.matricula.inmobiliaria");
    private final String columnaTenenciaLote = Utilidades.obtenerMensaje("consultas.ambito.complementario.tenencia.lote");
    private final String columnaQuienTenencia = Utilidades.obtenerMensaje("consultas.ambito.complementario.con.quien");
    private final String columnaFechaIniTenencia = Utilidades.obtenerMensaje("consultas.ambito.complementario.fec.inic.tenencia");
    private final String columnaFechaVigenciaTenencia = Utilidades.obtenerMensaje("consultas.ambito.complementario.fec.vig.tenencia");
    private final String columnaTieneSaniamiento = Utilidades.obtenerMensaje("consultas.ambito.complementario.tiene.acc.saneamiento");
    private final String columnaTipoSaniamiento = Utilidades.obtenerMensaje("consultas.ambito.complementario.tipo.saneamiento");
    private final String columnaFechaIniPleito = Utilidades.obtenerMensaje("consultas.ambito.complementario.fec.inic.pleito");
    private final String columnaQuienSaniamiento = Utilidades.obtenerMensaje("consultas.ambito.complementario.con.quien.sanea");
    private final String columnaCantidadTanque = Utilidades.obtenerMensaje("consultas.ambito.complementario.cantidad.tanque.alamcena");
    private final String columnaCapacidadTanque = Utilidades.obtenerMensaje("consultas.ambito.complementario.capacidad.tanque.almacena");
    private final String columnaPoblacionTotal = Utilidades.obtenerMensaje("consultas.ambito.complementario.poblacion.tot");
    private final String columnaTotalPredio = Utilidades.obtenerMensaje("consultas.ambito.complementario.total.predio");
    private final String columnaProporcionUsoSuelo = Utilidades.obtenerMensaje("consultas.ambito.complementario.prop.pred.uso.suelo");
    private final String columnaProporcionTipoDoc = Utilidades.obtenerMensaje("consultas.ambito.complementario.prop.pred.segun.tipo.doc.propiedad");
    private final String columnaProporcionTenencia = Utilidades.obtenerMensaje("consultas.ambito.complementario.prop.pred.segun.tenencia.lote");
    private final String columnaProporcionAccSaniamiento = Utilidades.obtenerMensaje("consultas.ambito.complementario.prop.pred.segun.acc.saniamiento");
    private final String columnaProporcioTipoSaniamiento = Utilidades.obtenerMensaje("consultas.ambito.complementario.prop.pred.segun.tipo.saniamiento");

    @PostConstruct
    public void inicializar() {
        //super.init();
        this.lista = new ArrayList<Registro>();
        this.cargarCamposOpcionales();
        usoSueloList = facParam.consultarValoresTipologia(TipologiaCodigo.USO_NORMATIVO_SUELO.getValor(), getUsuarioSesion().getUsuario().getIdioma().getId());
        tipoDocPropiedadList = facParam.consultarValoresTipologia(TipologiaCodigo.TIPO_DOCUMENTO_PROPIEDAD.getValor(), getUsuarioSesion().getUsuario().getIdioma().getId());
        tenenciaLoteList = facParam.consultarValoresTipologia(TipologiaCodigo.TENENCIA_LOTE.getValor(), getUsuarioSesion().getUsuario().getIdioma().getId());
        tipoSaneamientoList = facParam.consultarValoresTipologia(TipologiaCodigo.TIPO_SANEAMIENTO.getValor(), getUsuarioSesion().getUsuario().getIdioma().getId());
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

            // filtros basicos
            filtro.setCodZona(seleccionZona != null ? seleccionZona.getTipValor().getCodigo() : null);
            filtro.setCodSector(seleccionSector != null ? seleccionSector.getTipValor().getCodigo() : null);
            //otros filtros
            filtro.setCodUsoSuelo(codUsoSuelo);
            filtro.setCodTenenciaLote(codTenenciaLote);
            filtro.setCodTipoDocPropiedad(codTipoDocPropiedad);
            filtro.setTieneSaneamiento(tieneSaneamiento != null ? tieneSaneamiento : null);
            filtro.setCodTipoSaneamiento(codTipoSaneamiento);

            List<Object[]> resultados = facAmbito.consultarComplementarios(filtro);
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
                    registro.setZona(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]), getUsuarioSesion().getUsuario().getIdioma().getId()));
                    registro.setSector(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]), getUsuarioSesion().getUsuario().getIdioma().getId()));
                    registro.setClima(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]), getUsuarioSesion().getUsuario().getIdioma().getId()));
                    registro.setEtnias(verificarEtnias(UtilConsulta.stringValueOf(fila[i++])));
                    //indicadores 
                    registro.setUsoSuelo(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]),getUsuarioSesion().getUsuario().getIdioma().getId()));
                    registro.setTipoDocumentoProp(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]),getUsuarioSesion().getUsuario().getIdioma().getId()));
                    registro.setRegistroCatastral(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setMatriculaInmobiliaria(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setTenenciaLote(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]),getUsuarioSesion().getUsuario().getIdioma().getId()));
                    registro.setQuienTenencia(UtilConsulta.stringValueOf(fila[i++]));                    
                    registro.setFechaIniTenencia(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setFechaVigenciaTenencia(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setTieneSaniamiento(Utilidades.obtenerMensaje(UtilConsulta.stringValueOf(fila[i++])));
                    registro.setTipoSaniamiento(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]),getUsuarioSesion().getUsuario().getIdioma().getId()));
                    registro.setFechaIniPleito(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setQuienSaniamiento(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]),getUsuarioSesion().getUsuario().getIdioma().getId()));                    
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
                    //indicadores
                    registro.setTotalPredios(UtilConsulta.longValueOf(fila[i++]));  
                     if (!UtilCadena.isNullOVacio(filtro.getCodUsoSuelo())) {
                         registro.setProporcionUsoSuelo(UtilConsulta.doubleValueOf(fila[i++]));
                     }
                    if (!UtilCadena.isNullOVacio(filtro.getCodTipoDocPropiedad())) {
                        registro.setProporcionTipoDoc(UtilConsulta.doubleValueOf(fila[i++]));
                    }
                    if (!UtilCadena.isNullOVacio(filtro.getCodTenenciaLote())) {
                        registro.setProporcionTenencia(UtilConsulta.doubleValueOf(fila[i++]));
                    }
                    if (!UtilCadena.isNullOVacio(filtro.getTieneSaneamiento())) {
                        registro.setProporcionAccSaniamiento(UtilConsulta.doubleValueOf(fila[i++]));
                    }
                    if (!UtilCadena.isNullOVacio(filtro.getCodTipoSaneamiento())) {
                        registro.setProporcioTipoSaniamiento(UtilConsulta.doubleValueOf(fila[i++]));
                    }
                }
                registro.setCantidadTanque(UtilConsulta.longValueOf(fila[i++]));
                registro.setCapacidadTanque(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setPoblacionTotal(UtilConsulta.longValueOf(fila[i++]));
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
        this.codUsoSuelo = null;
        this.codTipoDocPropiedad = null;                
        this.codTenenciaLote = null;
        this.tieneSaneamiento = null;
        this.codTipoSaneamiento = null;
        //this.seleccionAgrupamiento = ParamNivelConsul.PREDIO.getNivel();
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
        this.codUsoSuelo = null;
        this.codTipoDocPropiedad = null;                
        this.codTenenciaLote = null;
        this.tieneSaneamiento = null;
        this.codTipoSaneamiento = null;
        //this.seleccionAgrupamiento = ParamNivelConsul.PREDIO.getNivel();
        //consultaAmbito.nivelDPAOfAccesoUsuario();
        this.resetearConsulta();
    }

    @Override
    public void cambioAgrupamiento() {
        selccionCamposOpcionales.clear();
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
        listaCamposOpcionales.clear();
        selccionCamposOpcionales.clear();
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
        if (isMostrarColPropiedadPredio()) {
            listaCamposOpcionales.add(columnaPropPredio);
        }
        //propias del ambito  
        if (isMostrarColUsoSuelo()) {
            listaCamposOpcionales.add(columnaUsoSuelo);
            selccionCamposOpcionales.add(columnaUsoSuelo);
        }
        if (isMostrarColTipoDocumentoProp()) {
            listaCamposOpcionales.add(columnaTipoDocumentoProp);
            selccionCamposOpcionales.add(columnaTipoDocumentoProp);
        }
        if (isMostrarColRegistroCatastral()) {
            listaCamposOpcionales.add(columnaRegistroCatastral);
            selccionCamposOpcionales.add(columnaRegistroCatastral);
        }
        if (isMostrarColMatriculaInmobiliaria()) {
            listaCamposOpcionales.add(columnaMatriculaInmobiliaria);
            selccionCamposOpcionales.add(columnaMatriculaInmobiliaria);
        }
        if (isMostrarColTenenciaLote()) {
            listaCamposOpcionales.add(columnaTenenciaLote);
            selccionCamposOpcionales.add(columnaTenenciaLote);
        }
        if (isMostrarColQuienTenencia()) {
            listaCamposOpcionales.add(columnaQuienTenencia);
            selccionCamposOpcionales.add(columnaQuienTenencia);
        }
        if (isMostrarColFechaIniTenencia()) {
            listaCamposOpcionales.add(columnaFechaIniTenencia);
            selccionCamposOpcionales.add(columnaFechaIniTenencia);
        }
        if (isMostrarColFechaVigenciaTenencia()) {
            listaCamposOpcionales.add(columnaFechaVigenciaTenencia);
            selccionCamposOpcionales.add(columnaFechaVigenciaTenencia);
        }
        if (isMostrarColTieneSaniamiento()) {
            listaCamposOpcionales.add(columnaTieneSaniamiento);
            selccionCamposOpcionales.add(columnaTieneSaniamiento);
        }
        if (isMostrarColTipoSaniamiento()) {
            listaCamposOpcionales.add(columnaTipoSaniamiento);
            selccionCamposOpcionales.add(columnaTipoSaniamiento);
        }
        if (isMostrarColFechaIniPleito()) {
            listaCamposOpcionales.add(columnaFechaIniPleito);
            selccionCamposOpcionales.add(columnaFechaIniPleito);
        }
        if (isMostrarColQuienSaniamiento()) {
            listaCamposOpcionales.add(columnaQuienSaniamiento);
            selccionCamposOpcionales.add(columnaQuienSaniamiento);
        }

        if (isMostrarColTotalPredio()) {
            listaCamposOpcionales.add(columnaTotalPredio);
            selccionCamposOpcionales.add(columnaTotalPredio);
        }
        if (isMostrarColProporcionUsoSuelo()) {
            listaCamposOpcionales.add(columnaProporcionUsoSuelo);
            selccionCamposOpcionales.add(columnaProporcionUsoSuelo);
        }
        if (isMostrarColProporcionTipoDoc()) {
            listaCamposOpcionales.add(columnaProporcionTipoDoc);
            selccionCamposOpcionales.add(columnaProporcionTipoDoc);
        }
        if (isMostrarColProporcionTenencia()) {
            listaCamposOpcionales.add(columnaProporcionTenencia);
            selccionCamposOpcionales.add(columnaProporcionTenencia);
        }
        if (isMostrarColProporcioTieneSaniamiento()) {
            listaCamposOpcionales.add(columnaProporcionAccSaniamiento);
            selccionCamposOpcionales.add(columnaProporcionAccSaniamiento);
        }
        if (isMostrarColProporcioTipoSaniamiento()) {
            listaCamposOpcionales.add(columnaProporcioTipoSaniamiento);
            selccionCamposOpcionales.add(columnaProporcioTipoSaniamiento);
        }
        if (isCheckAgua()) {
            listaCamposOpcionales.add(columnaCantidadTanque);
            selccionCamposOpcionales.add(columnaCantidadTanque);
            listaCamposOpcionales.add(columnaCapacidadTanque);
            selccionCamposOpcionales.add(columnaCapacidadTanque);
            listaCamposOpcionales.add(columnaPoblacionTotal);
            selccionCamposOpcionales.add(columnaPoblacionTotal);
        }
    }

    private void inicializarTotalesConsulta() {
        totalConsulta.setCantidadTanque(0L);
        totalConsulta.setCapacidadTanque(0D);
        totalConsulta.setPoblacionTotal(0L);
        totalConsulta.setTotalPredios(0L);
    }

    private void totalizarRegistro(Registro registro) {
        if (registro.getCantidadTanque() != null) {
            Long valor = totalConsulta.getCantidadTanque() != null ? totalConsulta.getCantidadTanque() : 0L;
            totalConsulta.setCantidadTanque(valor + registro.getCantidadTanque());
        }
        if (registro.getCapacidadTanque() != null) {
            Double valor = totalConsulta.getCapacidadTanque() != null ? totalConsulta.getCapacidadTanque() : 0D;
            totalConsulta.setCapacidadTanque(valor + registro.getCapacidadTanque());
        }
        if (registro.getPoblacionTotal() != null) {
            Long valor = totalConsulta.getPoblacionTotal() != null ? totalConsulta.getPoblacionTotal() : 0L;
            totalConsulta.setPoblacionTotal(valor + registro.getPoblacionTotal());
        }
        if (registro.getTotalPredios() != null) {
            Long valor = totalConsulta.getTotalPredios() != null ? totalConsulta.getTotalPredios() : 0L;
            totalConsulta.setTotalPredios(valor + registro.getTotalPredios());
        }
    }
    
    // mostrar columnas  
    public boolean isMostrarColUsoSuelo() {
        return isAgrupamientoPredio() && isCheckGeneralidades();
    }
    
    public boolean isMostrarColTipoDocumentoProp() {
        return isAgrupamientoPredio() && isCheckTenencia();
    }
    
    public boolean isMostrarColRegistroCatastral() {
        return isAgrupamientoPredio() && isCheckTenencia();
    }
    
    public boolean isMostrarColMatriculaInmobiliaria() {
        return isAgrupamientoPredio() && isCheckTenencia();
    }
    
    public boolean isMostrarColTenenciaLote() {
        return isAgrupamientoPredio() && isCheckTenencia();
    }
    
    public boolean isMostrarColQuienTenencia() {
         return isAgrupamientoPredio() && isCheckTenencia();
    } 
    
    public boolean isMostrarColFechaIniTenencia() {
    return isAgrupamientoPredio() && isCheckTenencia();    
    }
    
    public boolean isMostrarColFechaVigenciaTenencia() {
        return isAgrupamientoPredio() && isCheckTenencia();    
    }        
    
    public boolean isMostrarColTieneSaniamiento() {
        return isAgrupamientoPredio() && isCheckSaneamiento();  
    } 
    
    public boolean isMostrarColTipoSaniamiento() {
        return isAgrupamientoPredio() && isCheckSaneamiento();
    }
    
    public boolean isMostrarColFechaIniPleito() {
        return isAgrupamientoPredio() && isCheckSaneamiento();
    }
    
    public boolean isMostrarColQuienSaniamiento() {
        return isAgrupamientoPredio() && isCheckSaneamiento();
    }
    
    public boolean isMostrarColTotalPredio() {
        return !isAgrupamientoPredio();
    }
    
    public boolean isMostrarColProporcionUsoSuelo() {
        return !isAgrupamientoPredio() && codUsoSuelo != null && isCheckGeneralidades();
    }
    
    public boolean isMostrarColProporcionTipoDoc() {
        return !isAgrupamientoPredio() && codTipoDocPropiedad != null && isCheckTenencia();
    }
    
    public boolean isMostrarColProporcionTenencia() {
        return !isAgrupamientoPredio() && codTenenciaLote != null && isCheckTenencia();
    }
    
    public boolean isMostrarColProporcioTieneSaniamiento() {
        return !isAgrupamientoPredio() && tieneSaneamiento != null && isCheckSaneamiento();
    }
    
    public boolean isMostrarColProporcioTipoSaniamiento() {
        return !isAgrupamientoPredio() && codTipoSaneamiento != null && isCheckSaneamiento();
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
        private String zona;
        private String sector;
        private String clima;
        private String etnias;
        //predio        
        private String usoSuelo;
        private String tipoDocumentoProp;
        private String registroCatastral;
        private String matriculaInmobiliaria;
        private String tenenciaLote;
        private String quienTenencia;
        private String fechaIniTenencia;
        private String fechaVigenciaTenencia;
        private String tieneSaniamiento;
        private String tipoSaniamiento;
        private String fechaIniPleito;
        private String quienSaniamiento;
        //otros niveles
        private Long totalPredios;
        private Double proporcionUsoSuelo;
        private Double proporcionTipoDoc;
        private Double proporcionTenencia;
        private Double proporcionAccSaniamiento;
        private Double proporcioTipoSaniamiento;
        //Todos niveles
        private Long cantidadTanque;
        private Double capacidadTanque;
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

        public Long getTotalPredios() {
            return totalPredios;
        }

        public void setTotalPredios(Long totalPredios) {
            this.totalPredios = totalPredios;
        }

        public String getUsoSuelo() {
            return usoSuelo;
        }

        public void setUsoSuelo(String usoSuelo) {
            this.usoSuelo = usoSuelo;
        }

        public String getTipoDocumentoProp() {
            return tipoDocumentoProp;
        }

        public void setTipoDocumentoProp(String TipoDocumentoProp) {
            this.tipoDocumentoProp = TipoDocumentoProp;
        }

        public String getRegistroCatastral() {
            return registroCatastral;
        }

        public void setRegistroCatastral(String RegistroCatastral) {
            this.registroCatastral = RegistroCatastral;
        }

        public String getMatriculaInmobiliaria() {
            return matriculaInmobiliaria;
        }

        public void setMatriculaInmobiliaria(String MatriculaInmobiliaria) {
            this.matriculaInmobiliaria = MatriculaInmobiliaria;
        }

        public String getTenenciaLote() {
            return tenenciaLote;
        }

        public void setTenenciaLote(String TenenciaLote) {
            this.tenenciaLote = TenenciaLote;
        }

        public String getQuienTenencia() {
            return quienTenencia;
        }

        public void setQuienTenencia(String QuienTenencia) {
            this.quienTenencia = QuienTenencia;
        }

        public String getFechaIniTenencia() {
            return fechaIniTenencia;
        }

        public void setFechaIniTenencia(String FechaIniTenencia) {
            this.fechaIniTenencia = FechaIniTenencia;
        }

        public String getFechaVigenciaTenencia() {
            return fechaVigenciaTenencia;
        }

        public void setFechaVigenciaTenencia(String FechaVigenciaTenencia) {
            this.fechaVigenciaTenencia = FechaVigenciaTenencia;
        }

        public String getTieneSaniamiento() {
            return tieneSaniamiento;
        }

        public void setTieneSaniamiento(String TieneSaniamiento) {
            this.tieneSaniamiento = TieneSaniamiento;
        }

        public String getTipoSaniamiento() {
            return tipoSaniamiento;
        }

        public void setTipoSaniamiento(String TipoSaniamiento) {
            this.tipoSaniamiento = TipoSaniamiento;
        }

        public String getFechaIniPleito() {
            return fechaIniPleito;
        }

        public void setFechaIniPleito(String FechaIniPleito) {
            this.fechaIniPleito = FechaIniPleito;
        }

        public String getQuienSaniamiento() {
            return quienSaniamiento;
        }

        public void setQuienSaniamiento(String QuienSaniamiento) {
            this.quienSaniamiento = QuienSaniamiento;
        }

        public Double getProporcionUsoSuelo() {
            return proporcionUsoSuelo;
        }

        public void setProporcionUsoSuelo(Double ProporcionUsoSuelo) {
            this.proporcionUsoSuelo = ProporcionUsoSuelo;
        }

        public Double getProporcionTipoDoc() {
            return proporcionTipoDoc;
        }

        public void setProporcionTipoDoc(Double ProporcionTipoDoc) {
            this.proporcionTipoDoc = ProporcionTipoDoc;
        }

        public Double getProporcionTenencia() {
            return proporcionTenencia;
        }

        public void setProporcionTenencia(Double ProporcionTenencia) {
            this.proporcionTenencia = ProporcionTenencia;
        }

        public Double getProporcionAccSaniamiento() {
            return proporcionAccSaniamiento;
        }

        public void setProporcionAccSaniamiento(Double ProporcionAccSaniamiento) {
            this.proporcionAccSaniamiento = ProporcionAccSaniamiento;
        }

        public Double getProporcioTipoSaniamiento() {
            return proporcioTipoSaniamiento;
        }

        public void setProporcioTipoSaniamiento(Double ProporcioTipoSaniamiento) {
            this.proporcioTipoSaniamiento = ProporcioTipoSaniamiento;
        }

        public Long getCantidadTanque() {
            return cantidadTanque;
        }

        public void setCantidadTanque(Long CantidadTanque) {
            this.cantidadTanque = CantidadTanque;
        }

        public Double getCapacidadTanque() {
            return capacidadTanque;
        }

        public void setCapacidadTanque(Double CapacidadTanque) {
            this.capacidadTanque = CapacidadTanque;
        }

        public Long getPoblacionTotal() {
            return poblacionTotal;
        }

        public void setPoblacionTotal(Long PoblacionTotal) {
            this.poblacionTotal = PoblacionTotal;
        }
    }

    //Getters y Setters
    public List<Registro> getLista() {
        return lista;
    }

    public Registro getTotalConsulta() {
        return totalConsulta;
    }

    public String getColumnaUsoSuelo() {
        return columnaUsoSuelo;
    }

    public String getColumnaTipoDocumentoProp() {
        return columnaTipoDocumentoProp;
    }

    public String getColumnaRegistroCatastral() {
        return columnaRegistroCatastral;
    }

    public String getColumnaMatriculaInmobiliaria() {
        return columnaMatriculaInmobiliaria;
    }

    public String getColumnaTenenciaLote() {
        return columnaTenenciaLote;
    }

    public String getColumnaQuienTenencia() {
        return columnaQuienTenencia;
    }

    public String getColumnaFechaIniTenencia() {
        return columnaFechaIniTenencia;
    }

    public String getColumnaFechaVigenciaTenencia() {
        return columnaFechaVigenciaTenencia;
    }

    public String getColumnaTieneSaniamiento() {
        return columnaTieneSaniamiento;
    }

    public String getColumnaTipoSaniamiento() {
        return columnaTipoSaniamiento;
    }

    public String getColumnaFechaIniPleito() {
        return columnaFechaIniPleito;
    }

    public String getColumnaQuienSaniamiento() {
        return columnaQuienSaniamiento;
    }

    public String getColumnaCantidadTanque() {
        return columnaCantidadTanque;
    }

    public String getColumnaCapacidadTanque() {
        return columnaCapacidadTanque;
    }

    public String getColumnaPoblacionTotal() {
        return columnaPoblacionTotal;
    }

    public String getColumnaTotalPredio() {
        return columnaTotalPredio;
    }

    public String getColumnaProporcionUsoSuelo() {
        return columnaProporcionUsoSuelo;
    }

    public String getColumnaProporcionTipoDoc() {
        return columnaProporcionTipoDoc;
    }

    public String getColumnaProporcionTenencia() {
        return columnaProporcionTenencia;
    }

    public String getColumnaProporcionAccSaniamiento() {
        return columnaProporcionAccSaniamiento;
    }

    public String getColumnaProporcioTipoSaniamiento() {
        return columnaProporcioTipoSaniamiento;
    }

    public ConsultaAmbito getConsultaAmbito() {
        return consultaAmbito;
    }

    public void setConsultaAmbito(ConsultaAmbito consultaAmbito) {
        this.consultaAmbito = consultaAmbito;
    }

    public List<TipologiaValorNombre> getUsoSueloList() {
        return usoSueloList;
    }

    public void setUsoSueloList(List<TipologiaValorNombre> usoSueloList) {
        this.usoSueloList = usoSueloList;
    }

    public String getCodUsoSuelo() {
        return codUsoSuelo;
    }

    public void setCodUsoSuelo(String codUsoSuelo) {
        this.codUsoSuelo = codUsoSuelo;
    }

    public List<TipologiaValorNombre> getTipoDocPropiedadList() {
        return tipoDocPropiedadList;
    }

    public void setTipoDocPropiedadList(List<TipologiaValorNombre> tipoDocPropiedadList) {
        this.tipoDocPropiedadList = tipoDocPropiedadList;
    }

    public String getCodTipoDocPropiedad() {
        return codTipoDocPropiedad;
    }

    public void setCodTipoDocPropiedad(String codTipoDocPropiedad) {
        this.codTipoDocPropiedad = codTipoDocPropiedad;
    }

    public List<TipologiaValorNombre> getTenenciaLoteList() {
        return tenenciaLoteList;
    }

    public void setTenenciaLoteList(List<TipologiaValorNombre> tenenciaLoteList) {
        this.tenenciaLoteList = tenenciaLoteList;
    }

    public String getCodTenenciaLote() {
        return codTenenciaLote;
    }

    public void setCodTenenciaLote(String codTenenciaLote) {
        this.codTenenciaLote = codTenenciaLote;
    }

    public SiNo getTieneSaneamiento() {
        return tieneSaneamiento;
    }

    public void setTieneSaneamiento(SiNo tieneSaneamiento) {
        this.tieneSaneamiento = tieneSaneamiento;
    }

    public List<TipologiaValorNombre> getTipoSaneamientoList() {
        return tipoSaneamientoList;
    }

    public void setTipoSaneamientoList(List<TipologiaValorNombre> tipoSaneamientoList) {
        this.tipoSaneamientoList = tipoSaneamientoList;
    }

    public boolean isCheckGeneralidades() {
        return checkGeneralidades;
    }

    public void setCheckGeneralidades(boolean checkGeneralidades) {
        this.checkGeneralidades = checkGeneralidades;
    }

    public boolean isCheckTenencia() {
        return checkTenencia;
    }

    public void setCheckTenencia(boolean checkTenencia) {
        this.checkTenencia = checkTenencia;
    }

    public boolean isCheckSaneamiento() {
        return checkSaneamiento;
    }

    public void setCheckSaneamiento(boolean checkSaneamiento) {
        this.checkSaneamiento = checkSaneamiento;
    }

    public boolean isCheckAgua() {
        return checkAgua;
    }

    public void setCheckAgua(boolean checkAgua) {
        this.checkAgua = checkAgua;
    }
    
    public String getCodTipoSaneamiento() {
        return codTipoSaneamiento;
    }

    public void setCodTipoSaneamiento(String codTipoSaneamiento) {
        this.codTipoSaneamiento = codTipoSaneamiento;
    }
    
}
