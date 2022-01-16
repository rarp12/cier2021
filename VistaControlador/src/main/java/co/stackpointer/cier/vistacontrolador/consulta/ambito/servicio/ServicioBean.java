/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.consulta.ambito.servicio;

import co.stackpointer.cier.modelo.auditoria.RegistrarAuditoria;
import co.stackpointer.cier.modelo.entidad.instrumento.TipologiaValorNombre;
import co.stackpointer.cier.vistacontrolador.consulta.ambito.*;
import co.stackpointer.cier.modelo.filtro.ambito.FiltroAmbitoServicio;
import co.stackpointer.cier.modelo.tipo.BitacoraTransaccion;
import co.stackpointer.cier.modelo.tipo.CalificacionCondicion;
import co.stackpointer.cier.modelo.tipo.TipologiaCodigo;
import co.stackpointer.cier.modelo.utilidad.UtilConsulta;
import co.stackpointer.cier.modelo.utilidad.UtilOut;
import co.stackpointer.cier.vista.Utilidades;
import java.util.ArrayList;
import java.util.Date;
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
@ManagedBean(name = "servicioBean")
@ViewScoped
public class ServicioBean extends ConsultaAmbito {

    @ManagedProperty(value = "#{consultaAmbito}")
    private ConsultaAmbito consultaAmbito;
    private List<Registro> lista;
    private List<TipologiaValorNombre> listaTiposServicios;
    private FiltroAmbitoServicio filtro = new FiltroAmbitoServicio();
    private Registro totalConsulta = new Registro();

    private String seleccionServicio;
    private String etiquetaServicio;
    private String seleccionTieneServicio;
    private String seleccionAnalisisServicio;
    private TipologiaValorNombre seleccionTipoServicio;
    private CalificacionCondicion seleccionCondicion;

    private final String columnaNombreServicio = Utilidades.obtenerMensaje("consultas.ambito.servicio.columna.nombre");
    private final String columnaTieneServicio = Utilidades.obtenerMensaje("consultas.ambito.servicio.columna.tiene");
    private final String columnaTipoServicio1 = Utilidades.obtenerMensaje("consultas.ambito.servicio.columna.tipo1");
    private final String columnaTipoServicio2 = Utilidades.obtenerMensaje("consultas.ambito.servicio.columna.tipo2");
    private final String columnaCondicionServicio = Utilidades.obtenerMensaje("consultas.ambito.servicio.columna.condicion");
    private final String columnaTotalEstudiantesPredio = Utilidades.obtenerMensaje("consultas.ambito.servicio.columna.total.estudiantes");
    // otros niveles
    private final String columnaNumPrediosServicios = Utilidades.obtenerMensaje("consultas.ambito.servicio.columna.num.predios.tiene");
    private final String columnaNumPrediosCondicionServicios = Utilidades.obtenerMensaje("consultas.ambito.servicio.columna.num.predios.condicion");
    private final String columnaNumPrediosAnalisisServicios = Utilidades.obtenerMensaje("consultas.ambito.servicio.columna.num.predios.analisis");
    private final String columnaTotalPredios = Utilidades.obtenerMensaje("consultas.ambito.servicio.columna.total.predios");
    //proporcion
    private final String columnaPropPrediosServicios = Utilidades.obtenerMensaje("consultas.ambito.servicio.columna.prop.predios.tiene");
    private final String columnaPropPrediosCondicionServicios = Utilidades.obtenerMensaje("consultas.ambito.servicio.columna.prop.predios.condicion");
    private final String columnaPropPrediosAnalisisServicios = Utilidades.obtenerMensaje("consultas.ambito.servicio.columna.prop.predios.analisis");

    @PostConstruct
    public void inicializar() {
        //super.init();
        this.lista = new ArrayList<Registro>();
        this.cargarCamposOpcionales();
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
            filtro.setNombreServicio(seleccionServicio);
            filtro.setTieneServicio(seleccionTieneServicio);
            filtro.setCodigoTipoServicio(seleccionTipoServicio != null ? seleccionTipoServicio.getTipValor().getCodigo() : null);
            filtro.setCondicionServicio(seleccionCondicion != null ? seleccionCondicion.getValor() : null);
            filtro.setAnalisisServicio(seleccionAnalisisServicio);
            List<Object[]> resultados = facAmbito.consultarServicio(filtro);
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
                    registro.setZona(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]), getUsuarioSesion().getUsuario().getIdioma().getId()));
                    registro.setSector(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]), getUsuarioSesion().getUsuario().getIdioma().getId()));
                    registro.setClima(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]), getUsuarioSesion().getUsuario().getIdioma().getId()));
                    registro.setEtnias(verificarEtnias(UtilConsulta.stringValueOf(fila[i++])));
                    //indicadores 
                    registro.setNombreServicio(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setTieneServicio(Utilidades.obtenerMensaje(UtilConsulta.stringValueOf(fila[i++])));
                    if (isRequiereTipoServicio()) {
                        registro.setTipoServicio1(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]), getUsuarioSesion().getUsuario().getIdioma().getId()));
                        registro.setTipoServicio2(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]), getUsuarioSesion().getUsuario().getIdioma().getId()));
                    }
                    registro.setCondicionServicio(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setTotalEstudiantesPredio(UtilConsulta.longValueOf(fila[i++]));
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
                    registro.setNombreServicio(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setNumPrediosServicios(UtilConsulta.longValueOf(fila[i++]));
                    if (filtro.getCondicionServicio() != null) {
                        registro.setNumPrediosCondicionServicios(UtilConsulta.longValueOf(fila[i++]));
                    }
                    if (filtro.getAnalisisServicio() != null) {
                        registro.setNumPrediosAnalisisServicios(UtilConsulta.longValueOf(fila[i++]));
                    }
                    registro.setTotalPredios(UtilConsulta.longValueOf(fila[i++]));
                    registro.setTotalEstudiantesPredio(UtilConsulta.longValueOf(fila[i++]));

                    // proporciones
                    registro.setPropPrediosServicios(1.0 * registro.getNumPrediosServicios() / registro.getTotalPredios());
                    if (filtro.getCondicionServicio() != null) {
                        registro.setPropPrediosCondicionServicios(1.0 * registro.getNumPrediosCondicionServicios() / registro.getTotalPredios());
                    }
                    if (filtro.getAnalisisServicio() != null) {
                        registro.setPropPrediosAnalisisServicios(1.0 * registro.getNumPrediosAnalisisServicios() / registro.getTotalPredios());
                    }
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

    public void condicionSeleccionada() {
        selccionCamposOpcionales.clear();
        this.lista = new ArrayList<Registro>();
        //this.totalConsulta.i;
        cargarCamposOpcionales();
    }

    @Override
    public void resetearFiltros() {
        consultaAmbito.resetearNiveles();
        this.resetearListasEstSedesPredios();
        this.seleccionSector = null;
        this.seleccionZona = null;
        this.seleccionTieneServicio = null;
        this.seleccionAnalisisServicio = null;
        this.seleccionTipoServicio = null;
        this.seleccionCondicion = null;
        consultaAmbito.cargarNivel1();
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
        this.seleccionTieneServicio = null;
        this.seleccionAnalisisServicio = null;
        this.seleccionTipoServicio = null;
        this.seleccionCondicion = null;
        consultaAmbito.cargarNivel1();
        this.resetearConsulta();
    }

    @Override
    public void cambioAgrupamiento() {
        if (this.isAgrupamientoPredio()) {
            this.seleccionAnalisisServicio = null;
        } else {
            this.seleccionTieneServicio = null;
        }
        this.resetearConsulta();
    }

    private void resetearConsulta() {
        // columnas
        this.cargarCamposOpcionales();
        // resultados
        this.lista.clear();
        this.inicializarTotalesConsulta();
        if (this.isAgrupamientoPredio()) {
            this.cargarTiposServicios();
        }
    }

    public void cambioSeleccionServicio(String seleccionServicio) {
        this.seleccionServicio = seleccionServicio;
        this.cargarTiposServicios();
        this.cargarCamposOpcionales();
        auditar(seleccionServicio);
    }
    
    private void auditar(String seleccionServicio) {
        RegistrarAuditoria registrar = new RegistrarAuditoria();
        registrar.setFecha(new Date());
        if (seleccionServicio.equalsIgnoreCase("AGUA")) {
            registrar.setNombreMetodo(BitacoraTransaccion.CONSULTAR_SERVICIO_AGUA.name());
        }
        if (seleccionServicio.equalsIgnoreCase("ENERGIA_ELECTRICA")) {
            registrar.setNombreMetodo(BitacoraTransaccion.CONSULTAR_SERVICIO_ENERGIA_ELECTRICA.name());
        }
        if (seleccionServicio.equalsIgnoreCase("GAS")) {
            registrar.setNombreMetodo(BitacoraTransaccion.CONSULTAR_SERVICIO_GAS.name());
        }
        if (seleccionServicio.equalsIgnoreCase("RECOLECCION_BASURAS")) {
            registrar.setNombreMetodo(BitacoraTransaccion.CONSULTAR_SERVICIO_RECOLECCION_BASURAS.name());
        }
        if (seleccionServicio.equalsIgnoreCase("SISTEMA_RECICLAJE")) {
            registrar.setNombreMetodo(BitacoraTransaccion.CONSULTAR_SERVICIO_SISTEMA_RECICLAJE.name());
        }
        if (seleccionServicio.equalsIgnoreCase("RED_ALCANTARILLADO")) {
            registrar.setNombreMetodo(BitacoraTransaccion.CONSULTAR_SERVICIO_RED_ALCANTARILLADO.name());
        }
        if (seleccionServicio.equalsIgnoreCase("RED_PLUVIAL")) {
            registrar.setNombreMetodo(BitacoraTransaccion.CONSULTAR_SERVICIO_RED_PLUVIAL.name());
        }
        if (seleccionServicio.equalsIgnoreCase("INTERNET")) {
            registrar.setNombreMetodo(BitacoraTransaccion.CONSULTAR_SERVICIO_INTERNET.name());
        }
        if (seleccionServicio.equalsIgnoreCase("TELEFONO")) {
            registrar.setNombreMetodo(BitacoraTransaccion.CONSULTAR_SERVICIO_TELEFONO.name());
        }
        registrar.setUsuario(usuarioSesion.getUsuario().getUsername());
        registrar.start();
    }    

    public void cambioAnalisisServicio() {
        this.resetearConsulta();
    }

    private void cargarTiposServicios() {
        this.seleccionTipoServicio = null;
        TipologiaCodigo codTipologia = null;
        if (seleccionServicio.equals("AGUA")) {
            codTipologia = TipologiaCodigo.DISP_SERV_AGUA;
        } else if (seleccionServicio.equals("ENERGIA_ELECTRICA")) {
            codTipologia = TipologiaCodigo.DISP_SERV_ENERGIA;
        } else if (seleccionServicio.equals("GAS")) {
            codTipologia = TipologiaCodigo.DISP_SERV_GAS;
        } else if (seleccionServicio.equals("RECOLECCION_BASURAS")) {
            codTipologia = TipologiaCodigo.DISP_SERV_BASURAS;
        } else if (seleccionServicio.equals("SISTEMA_RECICLAJE")) {
            codTipologia = null;// no existe una tipologia para reciclaje
        } else if (seleccionServicio.equals("RED_ALCANTARILLADO")) {
            codTipologia = TipologiaCodigo.DISP_SERV_ALCANTARILLADO;
        } else if (seleccionServicio.equals("RED_PLUVIAL")) {
            codTipologia = TipologiaCodigo.DISP_SERV_RED_PLUVIAL;
        } else if (seleccionServicio.equals("INTERNET")) {
            codTipologia = TipologiaCodigo.DISP_SERV_INTERNET;
        } else if (seleccionServicio.equals("TELEFONO")) {
            codTipologia = TipologiaCodigo.DISP_SERV_TELEFONICO;
        }
        if (codTipologia != null) {
            listaTiposServicios = facParam.consultarValoresTipologia(codTipologia, getUsuarioSesion().getUsuario().getIdioma().getId());
        } else {
            listaTiposServicios = new ArrayList<TipologiaValorNombre>();
        }
    }

    public boolean isRequiereTipoServicio() {
        return seleccionServicio != null && !seleccionServicio.equals("SISTEMA_RECICLAJE");
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
        listaCamposOpcionales.add(columnaNombreServicio);
        selccionCamposOpcionales.add(columnaNombreServicio);
        if (isAgrupamientoPredio()) {
            listaCamposOpcionales.add(columnaCondicionServicio);
            selccionCamposOpcionales.add(columnaCondicionServicio);
            listaCamposOpcionales.add(columnaTieneServicio);
            if (isRequiereTipoServicio()) {
                listaCamposOpcionales.add(columnaTipoServicio1);
                listaCamposOpcionales.add(columnaTipoServicio2);
            }
            listaCamposOpcionales.add(columnaTotalEstudiantesPredio);
            // campos por defecto
            selccionCamposOpcionales.add(columnaTieneServicio);
            if (isRequiereTipoServicio()) {
                selccionCamposOpcionales.add(columnaTipoServicio1);
                selccionCamposOpcionales.add(columnaTipoServicio2);
            }
            selccionCamposOpcionales.add(columnaTotalEstudiantesPredio);
        } else {
            listaCamposOpcionales.add(columnaNumPrediosServicios);
            listaCamposOpcionales.add(columnaNumPrediosCondicionServicios);
            if (this.seleccionAnalisisServicio != null) {
                listaCamposOpcionales.add(columnaNumPrediosAnalisisServicios);
                listaCamposOpcionales.add(columnaPropPrediosAnalisisServicios);
            }
            listaCamposOpcionales.add(columnaTotalPredios);
            listaCamposOpcionales.add(columnaTotalEstudiantesPredio);
            listaCamposOpcionales.add(columnaPropPrediosServicios);
            listaCamposOpcionales.add(columnaPropPrediosCondicionServicios);
            // campos por defecto
            selccionCamposOpcionales.add(columnaNumPrediosServicios);
            selccionCamposOpcionales.add(columnaNumPrediosCondicionServicios);
            if (this.seleccionAnalisisServicio != null) {
                selccionCamposOpcionales.add(columnaNumPrediosAnalisisServicios);
                selccionCamposOpcionales.add(columnaPropPrediosAnalisisServicios);
            }
            selccionCamposOpcionales.add(columnaTotalPredios);
            selccionCamposOpcionales.add(columnaTotalEstudiantesPredio);
            selccionCamposOpcionales.add(columnaPropPrediosServicios);
            selccionCamposOpcionales.add(columnaPropPrediosCondicionServicios);
        }
    }

    private void inicializarTotalesConsulta() {
        totalConsulta.setTotalEstudiantesPredio(0L);
        totalConsulta.setNumPrediosServicios(0L);
        totalConsulta.setNumPrediosCondicionServicios(0L);
        totalConsulta.setNumPrediosAnalisisServicios(0L);
        totalConsulta.setTotalPredios(0L);
    }

    private void totalizarRegistro(Registro registro) {
        // predio
        if (registro.getTotalEstudiantesPredio() != null) {
            totalConsulta.setTotalEstudiantesPredio(totalConsulta.getTotalEstudiantesPredio() + registro.getTotalEstudiantesPredio());
        }
        //otro nivel
        if (registro.getNumPrediosServicios() != null) {
            totalConsulta.setNumPrediosServicios(totalConsulta.getNumPrediosServicios() + registro.getNumPrediosServicios());
        }
        if (registro.getNumPrediosCondicionServicios() != null) {
            totalConsulta.setNumPrediosCondicionServicios(totalConsulta.getNumPrediosCondicionServicios() + registro.getNumPrediosCondicionServicios());
        }
        if (registro.getNumPrediosAnalisisServicios() != null) {
            totalConsulta.setNumPrediosAnalisisServicios(totalConsulta.getNumPrediosAnalisisServicios() + registro.getNumPrediosAnalisisServicios());
        }

        if (registro.getTotalPredios() != null) {
            totalConsulta.setTotalPredios(totalConsulta.getTotalPredios() + registro.getTotalPredios());
        }
    }

    public List<Registro> getLista() {
        return lista;
    }

    public List<TipologiaValorNombre> getListaTiposServicios() {
        return listaTiposServicios;
    }

    public Registro getTotalConsulta() {
        return totalConsulta;
    }

    public String getSeleccionServicio() {
        return seleccionServicio;
    }

    public void setSeleccionServicio(String seleccionServicio) {
        this.seleccionServicio = seleccionServicio;
    }

    public String getEtiquetaServicio() {
        return etiquetaServicio;
    }

    public void setEtiquetaServicio(String etiquetaServicio) {
        this.etiquetaServicio = etiquetaServicio;
    }

    public String getSeleccionTieneServicio() {
        return seleccionTieneServicio;
    }

    public void setSeleccionTieneServicio(String seleccionTieneServicio) {
        this.seleccionTieneServicio = seleccionTieneServicio;
    }

    public TipologiaValorNombre getSeleccionTipoServicio() {
        return seleccionTipoServicio;
    }

    public void setSeleccionTipoServicio(TipologiaValorNombre seleccionTipoServicio) {
        this.seleccionTipoServicio = seleccionTipoServicio;
    }

    public CalificacionCondicion getSeleccionCondicion() {
        return seleccionCondicion;
    }

    public void setSeleccionCondicion(CalificacionCondicion seleccionCondicion) {
        this.seleccionCondicion = seleccionCondicion;
    }

    public String getSeleccionAnalisisServicio() {
        return seleccionAnalisisServicio;
    }

    public void setSeleccionAnalisisServicio(String seleccionAnalisisServicio) {
        this.seleccionAnalisisServicio = seleccionAnalisisServicio;
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
        private String nombreServicio;
        private String tieneServicio;
        private String tipoServicio1;
        private String tipoServicio2;
        private String condicionServicio;
        private Long totalEstudiantesPredio;
        //otros niveles
        private Long numPrediosServicios;
        private Long numPrediosCondicionServicios;
        private Long numPrediosAnalisisServicios;
        private Double propPrediosServicios;
        private Double propPrediosCondicionServicios;
        private Double propPrediosAnalisisServicios;

        private Long totalPredios;

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

        public String getTieneServicio() {
            return tieneServicio;
        }

        public void setTieneServicio(String tieneServicio) {
            this.tieneServicio = tieneServicio;
        }

        public String getNombreServicio() {
            return nombreServicio;
        }

        public void setNombreServicio(String nombreServicio) {
            this.nombreServicio = nombreServicio;
        }

        public String getTipoServicio1() {
            return tipoServicio1;
        }

        public void setTipoServicio1(String tipoServicio1) {
            this.tipoServicio1 = tipoServicio1;
        }

        public String getTipoServicio2() {
            return tipoServicio2;
        }

        public void setTipoServicio2(String tipoServicio2) {
            this.tipoServicio2 = tipoServicio2;
        }

        public String getCondicionServicio() {
            return condicionServicio;
        }

        public void setCondicionServicio(String condicionServicio) {
            this.condicionServicio = condicionServicio;
        }

        public Long getTotalEstudiantesPredio() {
            return totalEstudiantesPredio;
        }

        public void setTotalEstudiantesPredio(Long totalEstudiantesPredio) {
            this.totalEstudiantesPredio = totalEstudiantesPredio;
        }

        public Long getNumPrediosServicios() {
            return numPrediosServicios;
        }

        public void setNumPrediosServicios(Long numPrediosServicios) {
            this.numPrediosServicios = numPrediosServicios;
        }

        public Long getNumPrediosCondicionServicios() {
            return numPrediosCondicionServicios;
        }

        public void setNumPrediosCondicionServicios(Long numPrediosCondicionServicios) {
            this.numPrediosCondicionServicios = numPrediosCondicionServicios;
        }

        public Long getNumPrediosAnalisisServicios() {
            return numPrediosAnalisisServicios;
        }

        public void setNumPrediosAnalisisServicios(Long numPrediosAnalisisServicios) {
            this.numPrediosAnalisisServicios = numPrediosAnalisisServicios;
        }

        public Long getTotalPredios() {
            return totalPredios;
        }

        public void setTotalPredios(Long totalPredios) {
            this.totalPredios = totalPredios;
        }

        public Double getPropPrediosCondicionServicios() {
            return propPrediosCondicionServicios;
        }

        public void setPropPrediosCondicionServicios(Double propPrediosCondicionServicios) {
            this.propPrediosCondicionServicios = propPrediosCondicionServicios;
        }

        public Double getPropPrediosAnalisisServicios() {
            return propPrediosAnalisisServicios;
        }

        public void setPropPrediosAnalisisServicios(Double propPrediosAnalisisServicios) {
            this.propPrediosAnalisisServicios = propPrediosAnalisisServicios;
        }

        public Double getPropPrediosServicios() {
            return propPrediosServicios;
        }

        public void setPropPrediosServicios(Double propPrediosServicios) {
            this.propPrediosServicios = propPrediosServicios;
        }
    }

    //mostrar Columnas
    public boolean isMostrarColNumPrediosCondicionServicios() {
        return seleccionCondicion != null;
    }

    public String getColumnaNombreServicio() {
        return columnaNombreServicio;
    }

    public String getColumnaTieneServicio() {
        return columnaTieneServicio;
    }

    public String getColumnaTipoServicio1() {
        return columnaTipoServicio1;
    }

    public String getColumnaTipoServicio2() {
        return columnaTipoServicio2;
    }

    public String getColumnaCondicionServicio() {
        return columnaCondicionServicio;
    }

    public String getColumnaTotalEstudiantesPredio() {
        return columnaTotalEstudiantesPredio;
    }

    public String getColumnaNumPrediosServicios() {
        return columnaNumPrediosServicios;
    }

    public String getColumnaNumPrediosCondicionServicios() {
        return columnaNumPrediosCondicionServicios;
    }

    public String getColumnaNumPrediosAnalisisServicios() {
        return columnaNumPrediosAnalisisServicios;
    }

    public String getColumnaTotalPredios() {
        return columnaTotalPredios;
    }

    public String getColumnaPropPrediosServicios() {
        return columnaPropPrediosServicios;
    }

    public String getColumnaPropPrediosCondicionServicios() {
        return columnaPropPrediosCondicionServicios;
    }

    public String getColumnaPropPrediosAnalisisServicios() {
        return columnaPropPrediosAnalisisServicios;
    }

    public ConsultaAmbito getConsultaAmbito() {
        return consultaAmbito;
    }

    public void setConsultaAmbito(ConsultaAmbito consultaAmbito) {
        this.consultaAmbito = consultaAmbito;
    }

}
