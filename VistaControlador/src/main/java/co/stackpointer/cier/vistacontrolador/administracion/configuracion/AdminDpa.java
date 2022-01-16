/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.administracion.configuracion;

import co.stackpointer.cier.modelo.entidad.dpa.ConfiguracionNivel;
import co.stackpointer.cier.modelo.entidad.dpa.Nivel;
import co.stackpointer.cier.modelo.entidad.establecimiento.Establecimiento;
import co.stackpointer.cier.modelo.entidad.establecimiento.Predio;
import co.stackpointer.cier.modelo.entidad.establecimiento.Sede;
import co.stackpointer.cier.modelo.entidad.instrumento.TipologiaValorNombre;
import co.stackpointer.cier.modelo.excepcion.ErrorExcel;
import co.stackpointer.cier.modelo.fachada.EstablecimientoFacadeLocal;
import co.stackpointer.cier.modelo.fachada.EstructuraDPAFacadeLocal;
import co.stackpointer.cier.modelo.fachada.InstrumentoFacadeLocal;
import co.stackpointer.cier.modelo.fachada.ParametrosFacadeLocal;
import co.stackpointer.cier.modelo.tipo.ParamNivelConsul;
import co.stackpointer.cier.modelo.tipo.TipologiaCodigo;
import co.stackpointer.cier.modelo.utilidad.UtilCadena;
import co.stackpointer.cier.modelo.utilidad.UtilExportar;
import co.stackpointer.cier.modelo.utilidad.UtilOut;
import co.stackpointer.cier.vista.ManejadorErrores;
import co.stackpointer.cier.vista.Utilidades;
import co.stackpointer.cier.vistacontrolador.bean.LoginBean;
import co.stackpointer.cier.vistacontrolador.bean.UsuarioSesionBean;
import co.stackpointer.cier.vistacontrolador.bean.importacion.ImpUtil;
import co.stackpointer.cier.vistacontrolador.consulta.generarIndicadores.NivelGeneracionBean;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import net.sf.jasperreports.engine.JRParameter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.omnifaces.util.Messages;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author rrocha
 */
@ManagedBean(name = "adminDpa")
@ViewScoped
public class AdminDpa implements Serializable {

    @EJB
    private EstructuraDPAFacadeLocal facEstDPA;
    @EJB
    private EstablecimientoFacadeLocal establecimientoFacadeLocal;
    @EJB
    protected ParametrosFacadeLocal factParam;
    @EJB
    InstrumentoFacadeLocal fInstrumentos;
    @ManagedProperty(value = "#{usuarioSesion}")
    private UsuarioSesionBean usuarioSesion;
    @ManagedProperty(value = "#{login}")
    private LoginBean currentLogin;
    private Long maximoNivelDPA;
    private Long idMaximoNivelDPA;
    private Map<Long, String> listaNivelesConf;
    private List<Nivel> listaNivel1;
    private List<Nivel> listaNivel2;
    private List<Nivel> listaNivel3;
    private List<Nivel> listaNivel4;
    private List<Nivel> listaNivel5;
    private List<Nivel> filteredListaNivel1;
    private List<Nivel> filteredListaNivel2;
    private List<Nivel> filteredListaNivel3;
    private List<Nivel> filteredListaNivel4;
    private List<Nivel> filteredListaNivel5;
    private List<Nivel> padresEstablecimiento;
    private List<Establecimiento> padresSede;
    private List<Sede> padresPredio;
    private Sede padrePredioSeleccionado;
    private List<Establecimiento> listaEstablecimientos;
    private List<Sede> listaSedes;
    private List<Predio> listaPredios;
    private List<Establecimiento> establecimientos;    
    private List<Sede> sedes;
    private List<Predio> predios;
    private List<Establecimiento> establecimientosFiltrados;
    private List<Sede> sedesFiltradas;
    private List<Predio> prediosFiltrados;
    private Establecimiento establecimientoSeleccionado;
    private Sede sedeSeleccionado;
    private Predio predioSeleccionado;
    private Establecimiento establecimientoNuevo;
    private Sede sedeNuevo;
    private Predio predioNuevo;
    private Nivel seleccionNivel0;
    private Nivel seleccionNivel1;
    private Nivel seleccionNivel2;
    private Nivel seleccionNivel3;
    private Nivel seleccionNivel4;
    private Nivel seleccionNivel5;
    private Establecimiento seleccionEst;
    private Sede seleccionSede;
    private Predio seleccionPredio;
    private Nivel dpaNuevo;
    private Nivel dpaSeleccionado;
    private Nivel padreAux;
    private String descripcionAux;
    private String codigoAux;
    private String codigoPais;
    private String msjConfirmacion;
    private boolean swConfirmacion;
    private List<TipologiaValorNombre> listaSectores;
    private TipologiaValorNombre sectorSeleccionado;
    private List<TipologiaValorNombre> listaAutoridades;
    private TipologiaValorNombre autoridadSeleccionado;
    private List<ConfiguracionNivel> configuraciones;
    private ConfiguracionNivel configuracionNivelAuxSeleccionado;
    private Integer posicionNivelConfiguracionNuevo;
    private String descripcionNivelConfiguracionNuevo;
    private List<Integer> nivelesDisponibles;
    private HashMap<Long, List<Nivel>> listaEliminar;
    private HashMap<Long, List<Nivel>> listaCrear;
    private HashMap<Long, List<Nivel>> listaActualizar;
    private List<ErroresCargaDPA> listaErrores;
    private Map parametros = new HashMap();
    private StreamedContent exportFile;
    //int lastRow;
    private boolean hayRegErroneos;
    private boolean cargaPais;

    @PostConstruct
    public void inicializar() {
        this.codigoPais = currentLogin.getPais().getCodigoPais();
        configuraciones = facEstDPA.consultarConfNivelesAuxActual();
        listaNivelesConf = new HashMap<Long, String>();
        for (ConfiguracionNivel conf : configuraciones) {
            /*String descripcion = facEstDPA.consultarDescripcionNivelDPA(conf.getNivel(), usuarioSesion.getUsuario().getIdioma().getId());
             descripcion = descripcion != null ? descripcion : conf.getDescripcion();*/
            this.listaNivelesConf.put(conf.getNivel(), conf.getDescripcion());
        }

        // maximo nivel DPA
        if (!configuraciones.isEmpty()) {
            int indice = configuraciones.size() - 1;
            this.maximoNivelDPA = configuraciones.get(indice).getNivel();
            this.idMaximoNivelDPA = configuraciones.get(indice).getId();
        } else {
            this.maximoNivelDPA = -1L;
            this.idMaximoNivelDPA = -1L;
        }

        //Para agregar/eliminar niveles de configuracion, niveles que estan disponibles del 1 al 5 como macimo
        nivelesDisponibles = null; //Si es null la lista, no se renderiza el boton agregar de nivel configuracion
        if (configuraciones.size() <= 5) {
            nivelesDisponibles = new ArrayList<Integer>();
            for (int i = configuraciones.size(); i > 0; i--) {
                nivelesDisponibles.add(i);
            }
        }

        // otros niveles
        this.listaNivelesConf.put(ParamNivelConsul.ESTABLECIMIENTO.getNivel(), Utilidades.obtenerMensaje(ParamNivelConsul.ESTABLECIMIENTO.getEtiqueta()));
        this.listaNivelesConf.put(ParamNivelConsul.SEDE.getNivel(), Utilidades.obtenerMensaje(ParamNivelConsul.SEDE.getEtiqueta()));
        this.listaNivelesConf.put(ParamNivelConsul.PREDIO.getNivel(), Utilidades.obtenerMensaje(ParamNivelConsul.PREDIO.getEtiqueta()));

        //para la administracion DPA
        listaNivel1 = facEstDPA.consultarNivelesPorConfiguracion(ParamNivelConsul.UNO);
        listaNivel2 = facEstDPA.consultarNivelesPorConfiguracion(ParamNivelConsul.DOS);
        listaNivel3 = facEstDPA.consultarNivelesPorConfiguracion(ParamNivelConsul.TRES);
        listaNivel4 = facEstDPA.consultarNivelesPorConfiguracion(ParamNivelConsul.CUATRO);
        listaNivel5 = facEstDPA.consultarNivelesPorConfiguracion(ParamNivelConsul.CINCO);
        dpaNuevo = new Nivel();
        establecimientoNuevo = new Establecimiento();
        sedeNuevo = new Sede();
        predioNuevo = new Predio();
        this.cargarNivel0();
        listaSectores = factParam.consultarValoresTipologia(TipologiaCodigo.SECTOR.getValor(), getUsuarioSesion().getUsuario().getIdioma().getId());
        listaAutoridades = factParam.consultarValoresTipologia(TipologiaCodigo.AUTORIDAD_EDUCATIVA.getValor(), getUsuarioSesion().getUsuario().getIdioma().getId());
    }
    
    public void reloadTab(){
        resetearFiltros();
        inicializar();
        listaEstablecimientos = new ArrayList<Establecimiento>();
        establecimientos = new ArrayList<Establecimiento>();
        listaSedes = new ArrayList<Sede>();
        sedes = new ArrayList<Sede>();
        listaPredios = new ArrayList<Predio>();
        predios = new ArrayList<Predio>();
    }

    public void instanciarNuevoDPA() {
        dpaNuevo = new Nivel();
    }

    public void instanciarNuevoEstablecimiento() {
        establecimientoNuevo = new Establecimiento();
        cargarPadresEstablecimiento();
    }

    public void cargarPadresEstablecimiento() {
        padresEstablecimiento = new ArrayList<Nivel>();
        Nivel ultimoSeleccionado = getUltimoNivelDpaSeleccionado();
        if (ultimoSeleccionado.getConfiguracion().getNivel().equals(maximoNivelDPA) || facEstDPA.relacionesDPAIncompleta()) {
            switch (maximoNivelDPA.intValue()) {
                case 1:
                    padresEstablecimiento = facEstDPA.consultarNivelesPorConfiguracion(ParamNivelConsul.UNO);
                    break;
                case 2:
                    padresEstablecimiento = facEstDPA.consultarNivelesPorConfiguracion(ParamNivelConsul.DOS);
                    break;
                case 3:
                    padresEstablecimiento = facEstDPA.consultarNivelesPorConfiguracion(ParamNivelConsul.TRES);
                    break;
                case 4:
                    padresEstablecimiento = facEstDPA.consultarNivelesPorConfiguracion(ParamNivelConsul.CUATRO);
                    break;
                case 5:
                    padresEstablecimiento = facEstDPA.consultarNivelesPorConfiguracion(ParamNivelConsul.CINCO);
                    break;
            }
        } else {
            padresEstablecimiento = facEstDPA.consultarNivelAux(ultimoSeleccionado.getId(), idMaximoNivelDPA);
        }
    }

    public void instanciarNuevaSede() {
        sedeNuevo = new Sede();
        cargarPadresSede();
    }

    public void cargarPadresSede() {
        padresSede = new ArrayList<Establecimiento>();
        padresSede = llenarEstablecimientos();
    }

    public void instanciarNuevoPredio() {
        predioNuevo = new Predio();
        cargarPadresPredio();
    }

    public void prepararCambioSedeToPredio(Sede sede) {
        if (establecimientoFacadeLocal.tieneHijos(sede)) {
            msjConfirmacion = Utilidades.obtenerMensaje("admistracion.admindpa.hijosAsignadoSed");
            swConfirmacion = false;
        } else {
            msjConfirmacion = Utilidades.obtenerMensaje("admistracion.admindpa.confirmarTransformacionSed");
            swConfirmacion = true;
            instanciarNuevoPredio();
            predioNuevo.setNombre(sede.getNombre());
            padresPredio = new ArrayList<Sede>();
            padresPredio.addAll(listaSedes);
            padresPredio.remove(sede);           
        }         
    }

    public void transformarSedeToPredio() {
        eliminarSed();
        guardarPredio();
    }

    public void cargarPadresPredio() {
        padresPredio = new ArrayList<Sede>();
        padresPredio = llenarSedes();
    }

    public void guardarDPA(int nivel) {
        try {
            switch (nivel) {
                case 1:
                    Nivel padre = facEstDPA.consultarNivelAux(codigoPais, ParamNivelConsul.CERO.getNivel());
                    dpaNuevo.setPadre(padre);
                    dpaNuevo.setConfiguracion(facEstDPA.getConfiguracionNivel(1L));
                    if (facEstDPA.existeDescripcionDpa(dpaNuevo)) {
                        Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("admistracion.admindpa.error.validacion.descripcion"));
                    } else {
                        dpaNuevo = facEstDPA.guardarDpa(usuarioSesion.getUsuario().getUsername(), dpaNuevo);
                        listaNivel1.add(dpaNuevo);
                    }
                    break;
                case 2:
                    dpaNuevo.setConfiguracion(facEstDPA.getConfiguracionNivel(2L));
                    if (facEstDPA.existeDescripcionDpa(dpaNuevo)) {
                        Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("admistracion.admindpa.error.validacion.descripcion"));
                    } else {
                        dpaNuevo = facEstDPA.guardarDpa(usuarioSesion.getUsuario().getUsername(), dpaNuevo);
                        listaNivel2.add(dpaNuevo);
                    }
                    break;
                case 3:
                    dpaNuevo.setConfiguracion(facEstDPA.getConfiguracionNivel(3L));
                    if (facEstDPA.existeDescripcionDpa(dpaNuevo)) {
                        Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("admistracion.admindpa.error.validacion.descripcion"));
                    } else {
                        dpaNuevo = facEstDPA.guardarDpa(usuarioSesion.getUsuario().getUsername(), dpaNuevo);
                        listaNivel3.add(dpaNuevo);
                    }
                    break;
                case 4:
                    dpaNuevo.setConfiguracion(facEstDPA.getConfiguracionNivel(4L));
                    if (facEstDPA.existeDescripcionDpa(dpaNuevo)) {
                        Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("admistracion.admindpa.error.validacion.descripcion"));
                    } else {
                        dpaNuevo = facEstDPA.guardarDpa(usuarioSesion.getUsuario().getUsername(), dpaNuevo);
                        listaNivel4.add(dpaNuevo);
                    }
                    break;
                case 5:
                    dpaNuevo.setConfiguracion(facEstDPA.getConfiguracionNivel(5L));
                    if (facEstDPA.existeDescripcionDpa(dpaNuevo)) {
                        Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("admistracion.admindpa.error.validacion.descripcion"));
                    } else {
                        dpaNuevo = facEstDPA.guardarDpa(usuarioSesion.getUsuario().getUsername(), dpaNuevo);
                        listaNivel5.add(dpaNuevo);
                    }
                    break;
            }
        } catch (Exception e) {
            ManejadorErrores.ingresarExcepcionEnLog(e, AdminDpa.class);
            Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"));
        }
    }

    public void editarDPA() {
        try {
            if (padreAux != null && !padreAux.equals(dpaSeleccionado.getPadre())) {
                if (facEstDPA.existeDescripcionDpa(dpaSeleccionado)) {
                    dpaSeleccionado.setDescripcion(descripcionAux);
                    dpaSeleccionado.setCodigo(codigoAux);
                    dpaSeleccionado.setPadre(padreAux);
                    Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("admistracion.admindpa.error.validacion.descripcion"));
                } else {
                    facEstDPA.actualizarDpa(usuarioSesion.getUsuario().getUsername(), dpaSeleccionado);
                    facEstDPA.actualizarDpaBD(dpaSeleccionado.getId());
                }
            } else {
                if (facEstDPA.existeDescripcionDpaNoId(dpaSeleccionado)) {
                    dpaSeleccionado.setDescripcion(descripcionAux);
                    dpaSeleccionado.setCodigo(codigoAux);
                    dpaSeleccionado.setPadre(dpaSeleccionado.getPadre());
                    Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("admistracion.admindpa.error.validacion.descripcion"));
                } else {
                    facEstDPA.actualizarDpa(usuarioSesion.getUsuario().getUsername(), dpaSeleccionado);
                    facEstDPA.actualizarDpaBD(dpaSeleccionado.getId());
                }
            }
        } catch (Exception e) {
            ManejadorErrores.ingresarExcepcionEnLog(e, AdminDpa.class);
            Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"));
        }
    }

    public void eliminarDPA(int nivel) {
        try {
            facEstDPA.eliminarDpa(usuarioSesion.getUsuario().getUsername(), dpaSeleccionado);
            switch (nivel) {
                case 1:
                    listaNivel1.remove(dpaSeleccionado);
                    filteredListaNivel1.remove(dpaSeleccionado);
                    break;
                case 2:
                    listaNivel2.remove(dpaSeleccionado);
                    filteredListaNivel2.remove(dpaSeleccionado);
                    break;
                case 3:
                    listaNivel3.remove(dpaSeleccionado);
                    filteredListaNivel3.remove(dpaSeleccionado);
                    break;
                case 4:
                    listaNivel4.remove(dpaSeleccionado);
                    filteredListaNivel4.remove(dpaSeleccionado);
                    break;
                case 5:
                    listaNivel5.remove(dpaSeleccionado);
                    filteredListaNivel5.remove(dpaSeleccionado);
                    break;
            }
        } catch (Exception e) {
            ManejadorErrores.ingresarExcepcionEnLog(e, AdminDpa.class);
            RequestContext.getCurrentInstance().addCallbackParam("validationFailed", true);
            Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"));
        }
    }

    public void eliminarEst() {
        try {
            establecimientoFacadeLocal.eliminarEstablecimiento(usuarioSesion.getUsuario().getUsername(), establecimientoSeleccionado);
            establecimientos.remove(establecimientoSeleccionado);
            listaEstablecimientos.remove(establecimientoSeleccionado);
            if (establecimientosFiltrados != null && !establecimientosFiltrados.isEmpty()) {
                establecimientosFiltrados.remove(establecimientoSeleccionado);
                setEstablecimientosFiltrados(establecimientosFiltrados);
            }
        } catch (Exception e) {
            ManejadorErrores.ingresarExcepcionEnLog(e, AdminDpa.class);
            RequestContext.getCurrentInstance().addCallbackParam("validationFailed", true);
            Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"));
        }
    }

    public void eliminarSed() {
        try {
            establecimientoFacadeLocal.eliminarSede(usuarioSesion.getUsuario().getUsername(), sedeSeleccionado);
            sedes.remove(sedeSeleccionado);
            listaSedes = new ArrayList<Sede>();
            if (sedesFiltradas != null && !sedesFiltradas.isEmpty()) {
                sedesFiltradas.remove(sedeSeleccionado);
                setSedesFiltradas(sedesFiltradas);
            }
        } catch (Exception e) {
            ManejadorErrores.ingresarExcepcionEnLog(e, AdminDpa.class);
            RequestContext.getCurrentInstance().addCallbackParam("validationFailed", true);
            Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"));
        }
    }

    public void eliminarPred() {
        try {
            Long predioId= predioSeleccionado.getId();
            establecimientoFacadeLocal.eliminarPredio(usuarioSesion.getUsuario().getUsername(), predioSeleccionado);
            establecimientoFacadeLocal.colocarPredioInexistenteBD(predioId);
            consultarPredios();
            if (prediosFiltrados != null && !prediosFiltrados.isEmpty()) {
                prediosFiltrados.remove(predioSeleccionado);
                setPrediosFiltrados(prediosFiltrados);
            }
        } catch (Exception e) {
            ManejadorErrores.ingresarExcepcionEnLog(e, AdminDpa.class);
            RequestContext.getCurrentInstance().addCallbackParam("validationFailed", true);
            Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"));
        }
    }

    public void validaHijosDPA(Nivel nivel) {
        if (facEstDPA.tieneSubDivisiones(nivel)) {
            msjConfirmacion = Utilidades.obtenerMensaje("admistracion.admindpa.hijosAsignadoDpa");
            swConfirmacion = false;
        } else {
            if (nivel.getConfiguracion().getNivel().equals(maximoNivelDPA)) {
                if (!facEstDPA.consultarEstablecimientosAux(nivel).isEmpty()) {
                    msjConfirmacion = Utilidades.obtenerMensaje("admistracion.admindpa.hijosAsignadoDpa");
                    swConfirmacion = false;
                } else {
                    msjConfirmacion = Utilidades.obtenerMensaje("admistracion.admindpa.confirmarEliminacionDpa");
                    swConfirmacion = true;
                }
            } else {
                msjConfirmacion = Utilidades.obtenerMensaje("admistracion.admindpa.confirmarEliminacionDpa");
                swConfirmacion = true;
            }
        }
    }

    public void validaHijosEst(Establecimiento establecimiento) {
        if (establecimientoFacadeLocal.tieneHijos(establecimiento)) {
            msjConfirmacion = Utilidades.obtenerMensaje("admistracion.admindpa.hijosAsignadoEst");
            swConfirmacion = false;
        } else {
            msjConfirmacion = Utilidades.obtenerMensaje("admistracion.admindpa.confirmarEliminacionEst");
            swConfirmacion = true;
        }
    }

    public void validaHijosSed(Sede sede) {
        if (establecimientoFacadeLocal.tieneHijos(sede)) {
            msjConfirmacion = Utilidades.obtenerMensaje("admistracion.admindpa.hijosAsignadoSed");
            swConfirmacion = false;
        } else {
            msjConfirmacion = Utilidades.obtenerMensaje("admistracion.admindpa.confirmarEliminacionSed");
            swConfirmacion = true;
        }
    }

    public void cambioNivel1() {
        this.resetearNivel2();
        this.resetearNivel3();
        this.resetearNivel4();
        this.resetearNivel5();
        this.resetearListasEstSedesPredios();
        if (seleccionNivel1 != null) {
            if (ParamNivelConsul.UNO.getNivel().equals(maximoNivelDPA)) {
                this.cargarEstablecimientos(seleccionNivel1);
            } else {
                listaNivel2 = this.cargarNivel(seleccionNivel1, ParamNivelConsul.DOS);
            }
        }
    }

    public void cambioNivel2() {
        this.resetearNivel3();
        this.resetearNivel4();
        this.resetearNivel5();
        this.resetearListasEstSedesPredios();
        if (seleccionNivel2 != null) {
            if (ParamNivelConsul.DOS.getNivel().equals(maximoNivelDPA)) {
                this.cargarEstablecimientos(seleccionNivel2);
            } else {
                listaNivel3 = this.cargarNivel(seleccionNivel2, ParamNivelConsul.TRES);
            }
        }
    }

    public void cambioNivel3() {
        this.resetearNivel4();
        this.resetearNivel5();
        this.resetearListasEstSedesPredios();
        if (seleccionNivel3 != null) {
            if (ParamNivelConsul.TRES.getNivel().equals(maximoNivelDPA)) {
                this.cargarEstablecimientos(seleccionNivel3);
            } else {
                listaNivel4 = this.cargarNivel(seleccionNivel3, ParamNivelConsul.CUATRO);
            }
        }
    }

    public void cambioNivel4() {
        this.resetearNivel5();
        this.resetearListasEstSedesPredios();
        if (seleccionNivel4 != null) {
            if (ParamNivelConsul.CUATRO.getNivel().equals(maximoNivelDPA)) {
                this.cargarEstablecimientos(seleccionNivel4);
            } else {
                listaNivel5 = this.cargarNivel(seleccionNivel4, ParamNivelConsul.CINCO);
            }
        }
    }

    public void cambioNivel5() {
        this.resetearListasEstSedesPredios();
        if (seleccionNivel5 != null && ParamNivelConsul.CINCO.getNivel().equals(maximoNivelDPA)) {
            this.cargarEstablecimientos(seleccionNivel5);
        }
    }

    public void cambioEstablecimiento() {
        this.resetearListasSedesPredios();
        if (seleccionEst != null) {
            listaSedes = seleccionEst.getSedes();
        }
    }

    public void cambioSede() {
       // listaPredios = seleccionSede != null ? seleccionSede.getPredios() : new ArrayList<Predio>();
        listaPredios =new ArrayList<Predio>();
    }

    private void resetearNivel1() {
        seleccionNivel1 = null;
        listaNivel1 = new ArrayList<Nivel>();
    }

    private void resetearNivel2() {
        seleccionNivel2 = null;
        listaNivel2 = new ArrayList<Nivel>();
    }

    private void resetearNivel3() {
        seleccionNivel3 = null;
        listaNivel3 = new ArrayList<Nivel>();
    }

    private void resetearNivel4() {
        seleccionNivel4 = null;
        listaNivel4 = new ArrayList<Nivel>();
    }

    private void resetearNivel5() {
        seleccionNivel5 = null;
        listaNivel5 = new ArrayList<Nivel>();
    }

    public void resetearListasEstSedesPredios() {
        seleccionEst = null;
        listaEstablecimientos = new ArrayList<Establecimiento>();
        this.resetearListasSedesPredios();
    }

    public void resetearListasSedesPredios() {
        seleccionSede = null;
        seleccionPredio = null;
        listaSedes = new ArrayList<Sede>();
        //sedes = new ArrayList<Sede>();
        //sedesFiltradas = new ArrayList<Sede>();
        listaPredios = new ArrayList<Predio>();
    }

    public void resetearNiveles() {
        this.resetearNivel1();
        this.resetearNivel2();
        this.resetearNivel3();
        this.resetearNivel4();
        this.resetearNivel5();
    }

    private List<Nivel> cargarNivel(Nivel nivelSeccionado, ParamNivelConsul nivelConsulta) {
        return facEstDPA.consultarNiveles(nivelConsulta, nivelSeccionado);
    }

    private void cargarEstablecimientos(Nivel nivelSeccionado) {
        listaEstablecimientos = facEstDPA.consultarEstablecimientosAux(nivelSeccionado);
    }

    public void cargarNivel0() {
        this.codigoPais = currentLogin.getPais().getCodigoPais();
        seleccionNivel0 = facEstDPA.consultarNivelAux(codigoPais, ParamNivelConsul.CERO.getNivel());
    }

    public void cargarNivel1() {
        this.resetearNivel1();
        this.resetearNivel2();
        this.resetearNivel3();
        this.resetearNivel4();
        this.resetearNivel5();
        this.resetearListasEstSedesPredios();
        if (seleccionNivel0 != null) {
            if (ParamNivelConsul.CERO.getNivel().equals(maximoNivelDPA)) {
                this.cargarEstablecimientos(seleccionNivel0);
            } else {
                listaNivel1 = this.cargarNivel(seleccionNivel0, ParamNivelConsul.UNO);
            }
        }
    }

    public void resetearFiltros() {
        resetearNiveles();
        this.resetearListasEstSedesPredios();
        cargarNivel1();
    }

    public void consultarEstablecimientos() {
        establecimientos = new ArrayList<Establecimiento>();        
        establecimientos = llenarEstablecimientos();
        Nivel ultimoSeleccionado = getUltimoNivelDpaSeleccionado();
        cargarEstablecimientos(ultimoSeleccionado);
    }

    public void consultarSedes() {
        sedes = new ArrayList<Sede>();
        listaSedes = new ArrayList<Sede>();
        sedes = llenarSedes();
        listaSedes = sedes;
    }

    public void consultarPredios() {
        predios = new ArrayList<Predio>();
        if (!UtilCadena.isNullOVacio(seleccionSede)) {
            Sede sede = facEstDPA.consultarSede(seleccionSede.getId());
            predios = sede.getPredios();
        } else {
            List<Sede> sedes_ = new ArrayList<Sede>();
            sedes_ = llenarSedes();
            for (Iterator<Sede> it = sedes_.iterator(); it.hasNext();) {
                Sede s = it.next();
                if (!UtilCadena.isNullOVacio(s.getPredios())) {
                    predios.addAll(s.getPredios());
                }
            }
        }
    }

    private List<Establecimiento> llenarEstablecimientos() {
        List<Establecimiento> es;        
        if (facEstDPA.relacionesDPAIncompleta()) {
            es = facEstDPA.consultarEstablecimientosAux();
        } else {
            Nivel ultimoSeleccionado = getUltimoNivelDpaSeleccionado();
            if (ultimoSeleccionado.getConfiguracion().getNivel().equals(maximoNivelDPA)) {
                es = facEstDPA.consultarEstablecimientosAux(ultimoSeleccionado);
            } else {
                es = facEstDPA.consultarEstablecimientosAux(ultimoSeleccionado.getId(), idMaximoNivelDPA);
            }
        }
        return es;
    }

    private List<Sede> llenarSedes() {
        List<Sede> se;
        if (facEstDPA.relacionesDPAIncompleta()) {
            se = facEstDPA.consultarSedesAux();
        } else {
            if (!UtilCadena.isNullOVacio(seleccionEst)) {
                se = facEstDPA.consultarSedesAux(seleccionEst.getId());
            } else {
                Nivel ultimoSeleccionado = getUltimoNivelDpaSeleccionado();
                if (ultimoSeleccionado.getConfiguracion().getNivel().equals(maximoNivelDPA)) {
                    se = facEstDPA.consultarSedesAux(ultimoSeleccionado);
                } else {
                    se = facEstDPA.consultarSedesAux(ultimoSeleccionado.getId(), idMaximoNivelDPA);
                }
            }
        }
        return se;
    }

    private Nivel getUltimoNivelDpaSeleccionado() {
        Nivel ultimoSeleccionado;
        if (!UtilCadena.isNullOVacio(seleccionNivel5)) {
            ultimoSeleccionado = seleccionNivel5;
        } else if (!UtilCadena.isNullOVacio(seleccionNivel4)) {
            ultimoSeleccionado = seleccionNivel4;
        } else if (!UtilCadena.isNullOVacio(seleccionNivel3)) {
            ultimoSeleccionado = seleccionNivel3;
        } else if (!UtilCadena.isNullOVacio(seleccionNivel2)) {
            ultimoSeleccionado = seleccionNivel2;
        } else if (!UtilCadena.isNullOVacio(seleccionNivel1)) {
            ultimoSeleccionado = seleccionNivel1;
        } else {
            ultimoSeleccionado = seleccionNivel0;
        }
        return ultimoSeleccionado;
    }

    private String calcularCodigoInterno() {
        Double sec = fInstrumentos.obtenerSecPredio();
        if (sec == null) {
            sec = new Double(0);
        }
        String codigoInterno = calcularMinimosCodigos();
        while ((codigoInterno + (sec.intValue() + 1)).length() < 20) {
            codigoInterno = codigoInterno + "0";
        }
        codigoInterno = codigoInterno + (sec.intValue() + 1);
        return codigoInterno;
    }

    private String calcularMinimosCodigos() {
        String codigosMinimos = "";
        if (this.getSeleccionNivel5() != null) {
            codigosMinimos = this.getSeleccionNivel4().getCodigo() + this.getSeleccionNivel5().getCodigo();
        } else if (this.getSeleccionNivel4() != null) {
            codigosMinimos = this.getSeleccionNivel3().getCodigo() + this.getSeleccionNivel4().getCodigo();
        } else if (this.getSeleccionNivel3() != null) {
            codigosMinimos = this.getSeleccionNivel2().getCodigo() + this.getSeleccionNivel3().getCodigo();
        } else if (this.getSeleccionNivel2() != null) {
            codigosMinimos = this.getSeleccionNivel1().getCodigo() + this.getSeleccionNivel2().getCodigo();
        } else if (this.getSeleccionNivel1() != null) {
            codigosMinimos = this.getSeleccionNivel0().getCodigo() + this.getSeleccionNivel1().getCodigo();
        }
        return codigosMinimos;
    }

    public void guardarEstablecimiento() {
        establecimientoFacadeLocal.guardarEstablecimiento(usuarioSesion.getUsuario().getUsername(), establecimientoNuevo);
        consultarEstablecimientos();
    }

    public void editarEstablecimiento() {
        try {
            establecimientoFacadeLocal.actualizarEstablecimiento(usuarioSesion.getUsuario().getUsername(), establecimientoSeleccionado);
            establecimientoFacadeLocal.actualizarEstablecimientoBD(establecimientoSeleccionado.getId());
        } catch (Exception e) {
            ManejadorErrores.ingresarExcepcionEnLog(e, AdminDpa.class);
            Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"));
        }
    }

    public void guardarSede() {
        sedeNuevo.setCodSector(sectorSeleccionado.getTipValor().getCodigo());
        sedeNuevo.setNomSector(sectorSeleccionado.getId_tip_valor().toString());
        sedeNuevo.setCodAutoridad(autoridadSeleccionado.getTipValor().getCodigo());
        sedeNuevo.setNomAutoridad(autoridadSeleccionado.getId_tip_valor().toString());
        establecimientoFacadeLocal.guardarSede(usuarioSesion.getUsuario().getUsername(), sedeNuevo);
        consultarSedes();
    }

    public void editarSede() {
        try {
            if (!UtilCadena.isNullOVacio(sectorSeleccionado)) {
                sedeNuevo.setCodSector(sectorSeleccionado.getTipValor().getCodigo());
                sedeNuevo.setNomSector(sectorSeleccionado.getId_tip_valor().toString());
            }
            if (!UtilCadena.isNullOVacio(autoridadSeleccionado)) {
                sedeNuevo.setCodAutoridad(autoridadSeleccionado.getTipValor().getCodigo());
                sedeNuevo.setNomAutoridad(autoridadSeleccionado.getId_tip_valor().toString());
            }
            establecimientoFacadeLocal.actualizarSede(usuarioSesion.getUsuario().getUsername(), sedeSeleccionado);
            establecimientoFacadeLocal.actualizarSedeBD(sedeSeleccionado.getId());
        } catch (Exception e) {
            ManejadorErrores.ingresarExcepcionEnLog(e, AdminDpa.class);
            Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"));
        }
    }

    public void guardarPredio() {
        predioNuevo.setCodigo(calcularCodigoInterno());
        establecimientoFacadeLocal.guardarPredio(usuarioSesion.getUsuario().getUsername(), predioNuevo, padrePredioSeleccionado);
        padrePredioSeleccionado = null;
        consultarPredios();
    }

    public void editarPredio() {
        try {
            establecimientoFacadeLocal.actualizarPredio(usuarioSesion.getUsuario().getUsername(), predioSeleccionado, padrePredioSeleccionado);
            establecimientoFacadeLocal.actualizarPredioBD(predioSeleccionado.getId());
            consultarPredios();
            predioSeleccionado = null;
        } catch (Exception e) {
            ManejadorErrores.ingresarExcepcionEnLog(e, AdminDpa.class);
            Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"));
        }
    }

    public void guardarNivelConfiguracion() {
        try {
            Integer tenant = currentLogin.getPais().getId().intValue();
            facEstDPA.guardarNivelConfiguracion(usuarioSesion.getUsuario().getUsername(), tenant, posicionNivelConfiguracionNuevo, descripcionNivelConfiguracionNuevo);
            inicializar();
        } catch (Exception e) {
            ManejadorErrores.ingresarExcepcionEnLog(e, AdminDpa.class);
            Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"));
        }
    }

    public void editarNivelConfiguracion() {
        try {
            Integer tenant = currentLogin.getPais().getId().intValue();
            Long idNivel = configuracionNivelAuxSeleccionado.getId();
            String descripcion = configuracionNivelAuxSeleccionado.getDescripcion();
            facEstDPA.actualizarNivelConfiguracion(usuarioSesion.getUsuario().getUsername(), tenant, idNivel, descripcion);
            inicializar();
        } catch (Exception e) {
            ManejadorErrores.ingresarExcepcionEnLog(e, AdminDpa.class);
            Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"));
        }
    }

    public void eliminarNivelConfiguracion() {
        try {
            Integer tenant = currentLogin.getPais().getId().intValue();
            Integer idNivelConf = configuracionNivelAuxSeleccionado.getId().intValue();
            Integer posicion = configuracionNivelAuxSeleccionado.getNivel().intValue();
            facEstDPA.eliminarNivelConfiguracion(usuarioSesion.getUsuario().getUsername(), tenant, idNivelConf, posicion);
            inicializar();
        } catch (Exception e) {
            ManejadorErrores.ingresarExcepcionEnLog(e, AdminDpa.class);
            RequestContext.getCurrentInstance().addCallbackParam("validationFailed", true);
            Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"));
        }
    }

    /*public void validaSwapDPA() {
     if (facEstDPA.puedeHacerSwapDpa()) {
     msjConfirmacion = Utilidades.obtenerMensaje("admistracion.admindpa.swapDpa.confirmar");
     swConfirmacion = true;
     } else {
     msjConfirmacion = Utilidades.obtenerMensaje("admistracion.admindpa.swapDpa.no");
     swConfirmacion = false;
     }
     }*/
    
    public boolean isActivoNivel0() {
        return listaNivelesConf.containsKey(ParamNivelConsul.CERO.getNivel());
    }

    public boolean isActivoNivel1() {
        return listaNivelesConf.containsKey(ParamNivelConsul.UNO.getNivel());
    }

    public boolean isActivoNivel2() {
        return listaNivelesConf.containsKey(ParamNivelConsul.DOS.getNivel());
    }

    public boolean isActivoNivel3() {
        return listaNivelesConf.containsKey(ParamNivelConsul.TRES.getNivel());
    }

    public boolean isActivoNivel4() {
        return listaNivelesConf.containsKey(ParamNivelConsul.CUATRO.getNivel());
    }

    public boolean isActivoNivel5() {
        return listaNivelesConf.containsKey(ParamNivelConsul.CINCO.getNivel());
    }

    public Map<Long, String> getListaNivelesConf() {
        return listaNivelesConf;
    }

    public void setListaNivelesConf(Map<Long, String> listaNivelesConf) {
        this.listaNivelesConf = listaNivelesConf;
    }

    public UsuarioSesionBean getUsuarioSesion() {
        return usuarioSesion;
    }

    public void setUsuarioSesion(UsuarioSesionBean usuarioSesion) {
        this.usuarioSesion = usuarioSesion;
    }

    public LoginBean getCurrentLogin() {
        return currentLogin;
    }

    public void setCurrentLogin(LoginBean currentLogin) {
        this.currentLogin = currentLogin;
    }

    public List<Nivel> getListaNivel1() {
        return listaNivel1;
    }

    public void setListaNivel1(List<Nivel> listaNivel1) {
        this.listaNivel1 = listaNivel1;
    }

    public List<Nivel> getListaNivel2() {
        return listaNivel2;
    }

    public void setListaNivel2(List<Nivel> listaNivel2) {
        this.listaNivel2 = listaNivel2;
    }

    public List<Nivel> getListaNivel3() {
        return listaNivel3;
    }

    public void setListaNivel3(List<Nivel> listaNivel3) {
        this.listaNivel3 = listaNivel3;
    }

    public List<Nivel> getListaNivel4() {
        return listaNivel4;
    }

    public void setListaNivel4(List<Nivel> listaNivel4) {
        this.listaNivel4 = listaNivel4;
    }

    public List<Nivel> getListaNivel5() {
        return listaNivel5;
    }

    public void setListaNivel5(List<Nivel> listaNivel5) {
        this.listaNivel5 = listaNivel5;
    }

    public List<Nivel> getFilteredListaNivel1() {
        return filteredListaNivel1;
    }

    public void setFilteredListaNivel1(List<Nivel> filteredListaNivel1) {
        this.filteredListaNivel1 = filteredListaNivel1;
    }

    public List<Nivel> getFilteredListaNivel2() {
        return filteredListaNivel2;
    }

    public void setFilteredListaNivel2(List<Nivel> filteredListaNivel2) {
        this.filteredListaNivel2 = filteredListaNivel2;
    }

    public List<Nivel> getFilteredListaNivel3() {
        return filteredListaNivel3;
    }

    public void setFilteredListaNivel3(List<Nivel> filteredListaNivel3) {
        this.filteredListaNivel3 = filteredListaNivel3;
    }

    public List<Nivel> getFilteredListaNivel4() {
        return filteredListaNivel4;
    }

    public void setFilteredListaNivel4(List<Nivel> filteredListaNivel4) {
        this.filteredListaNivel4 = filteredListaNivel4;
    }

    public List<Nivel> getFilteredListaNivel5() {
        return filteredListaNivel5;
    }

    public void setFilteredListaNivel5(List<Nivel> filteredListaNivel5) {
        this.filteredListaNivel5 = filteredListaNivel5;
    }

    public List<Nivel> getPadresEstablecimiento() {
        return padresEstablecimiento;
    }

    public void setPadresEstablecimiento(List<Nivel> padresEstablecimiento) {
        this.padresEstablecimiento = padresEstablecimiento;
    }

    public List<Establecimiento> getPadresSede() {
        return padresSede;
    }

    public void setPadresSede(List<Establecimiento> padresSede) {
        this.padresSede = padresSede;
    }

    public List<Sede> getPadresPredio() {
        return padresPredio;
    }

    public void setPadresPredio(List<Sede> padresPredio) {
        this.padresPredio = padresPredio;
    }

    public Sede getPadrePredioSeleccionado() {
        return padrePredioSeleccionado;
    }

    public void setPadrePredioSeleccionado(Sede padrePredioSeleccionado) {
        this.padrePredioSeleccionado = padrePredioSeleccionado;
    }

    public List<Establecimiento> getListaEstablecimientos() {
        return listaEstablecimientos;
    }

    public void setListaEstablecimientos(List<Establecimiento> listaEstablecimientos) {
        this.listaEstablecimientos = listaEstablecimientos;
    }

    public List<Sede> getListaSedes() {
        return listaSedes;
    }

    public void setListaSedes(List<Sede> listaSedes) {
        this.listaSedes = listaSedes;
    }

    public List<Predio> getListaPredios() {
        return listaPredios;
    }

    public void setListaPredios(List<Predio> listaPredios) {
        this.listaPredios = listaPredios;
    }

    public List<Establecimiento> getEstablecimientos() {
        return establecimientos;
    }

    public void setEstablecimientos(List<Establecimiento> establecimientos) {
        this.establecimientos = establecimientos;
    }

    public List<Sede> getSedes() {
        return sedes;
    }

    public void setSedes(List<Sede> sedes) {
        this.sedes = sedes;
    }

    public List<Predio> getPredios() {
        return predios;
    }

    public void setPredios(List<Predio> predios) {
        this.predios = predios;
    }

    public List<Establecimiento> getEstablecimientosFiltrados() {
        return establecimientosFiltrados;
    }

    public void setEstablecimientosFiltrados(List<Establecimiento> establecimientosFiltrados) {
        this.establecimientosFiltrados = establecimientosFiltrados;
    }

    public List<Sede> getSedesFiltradas() {
        return sedesFiltradas;
    }

    public void setSedesFiltradas(List<Sede> sedesFiltradas) {
        this.sedesFiltradas = sedesFiltradas;
    }

    public List<Predio> getPrediosFiltrados() {
        return prediosFiltrados;
    }

    public void setPrediosFiltrados(List<Predio> prediosFiltrados) {
        this.prediosFiltrados = prediosFiltrados;
    }

    public Establecimiento getEstablecimientoSeleccionado() {
        return establecimientoSeleccionado;
    }

    public void setEstablecimientoSeleccionado(Establecimiento establecimientoSeleccionado) {
        this.establecimientoSeleccionado = establecimientoSeleccionado;
    }

    public Sede getSedeSeleccionado() {
        return sedeSeleccionado;
    }

    public void setSedeSeleccionado(Sede sedeSeleccionado) {
        this.sedeSeleccionado = sedeSeleccionado;
    }

    public Predio getPredioSeleccionado() {
        return predioSeleccionado;
    }

    public void setPredioSeleccionado(Predio predioSeleccionado) {
        this.predioSeleccionado = predioSeleccionado;
        this.padrePredioSeleccionado = this.predioSeleccionado.getSede().get(0);
    }

    public Establecimiento getEstablecimientoNuevo() {
        return establecimientoNuevo;
    }

    public void setEstablecimientoNuevo(Establecimiento establecimientoNuevo) {
        this.establecimientoNuevo = establecimientoNuevo;
    }

    public Sede getSedeNuevo() {
        return sedeNuevo;
    }

    public void setSedeNuevo(Sede sedeNuevo) {
        this.sedeNuevo = sedeNuevo;
    }

    public Predio getPredioNuevo() {
        return predioNuevo;
    }

    public void setPredioNuevo(Predio predioNuevo) {
        this.predioNuevo = predioNuevo;
    }

    public Nivel getDpaNuevo() {
        return dpaNuevo;
    }

    public void setDpaNuevo(Nivel dpaNuevo) {
        this.dpaNuevo = dpaNuevo;
    }

    public Nivel getDpaSeleccionado() {
        return dpaSeleccionado;
    }

    public void setDpaSeleccionado(Nivel dpaSeleccionado) {
        this.dpaSeleccionado = dpaSeleccionado;
    }

    public String getDescripcionAux() {
        return descripcionAux;
    }

    public void setDescripcionAux(String descripcionAux) {
        this.descripcionAux = descripcionAux;
    }

    public Nivel getPadreAux() {
        return padreAux;
    }

    public void setPadreAux(Nivel padreAux) {
        this.padreAux = padreAux;
    }

    public String getCodigoAux() {
        return codigoAux;
    }

    public void setCodigoAux(String codigoAux) {
        this.codigoAux = codigoAux;
    }

    public String getMsjConfirmacion() {
        return msjConfirmacion;
    }

    public void setMsjConfirmacion(String msjConfirmacion) {
        this.msjConfirmacion = msjConfirmacion;
    }

    public boolean isSwConfirmacion() {
        return swConfirmacion;
    }

    public void setSwConfirmacion(boolean swConfirmacion) {
        this.swConfirmacion = swConfirmacion;
    }

    public Nivel getSeleccionNivel0() {
        return seleccionNivel0;
    }

    public void setSeleccionNivel0(Nivel seleccionNivel0) {
        this.seleccionNivel0 = seleccionNivel0;
    }

    public Nivel getSeleccionNivel1() {
        return seleccionNivel1;
    }

    public void setSeleccionNivel1(Nivel seleccionNivel1) {
        this.seleccionNivel1 = seleccionNivel1;
    }

    public Nivel getSeleccionNivel2() {
        return seleccionNivel2;
    }

    public void setSeleccionNivel2(Nivel seleccionNivel2) {
        this.seleccionNivel2 = seleccionNivel2;
    }

    public Nivel getSeleccionNivel3() {
        return seleccionNivel3;
    }

    public void setSeleccionNivel3(Nivel seleccionNivel3) {
        this.seleccionNivel3 = seleccionNivel3;
    }

    public Nivel getSeleccionNivel4() {
        return seleccionNivel4;
    }

    public void setSeleccionNivel4(Nivel seleccionNivel4) {
        this.seleccionNivel4 = seleccionNivel4;
    }

    public Nivel getSeleccionNivel5() {
        return seleccionNivel5;
    }

    public void setSeleccionNivel5(Nivel seleccionNivel5) {
        this.seleccionNivel5 = seleccionNivel5;
    }

    public Establecimiento getSeleccionEst() {
        return seleccionEst;
    }

    public void setSeleccionEst(Establecimiento seleccionEst) {
        this.seleccionEst = seleccionEst;
    }

    public Sede getSeleccionSede() {
        return seleccionSede;
    }

    public void setSeleccionSede(Sede seleccionSede) {
        this.seleccionSede = seleccionSede;
    }

    public Predio getSeleccionPredio() {
        return seleccionPredio;
    }

    public void setSeleccionPredio(Predio seleccionPredio) {
        this.seleccionPredio = seleccionPredio;
    }

    public List<TipologiaValorNombre> getListaSectores() {
        return listaSectores;
    }

    public void setListaSectores(List<TipologiaValorNombre> listaSectores) {
        this.listaSectores = listaSectores;
    }

    public TipologiaValorNombre getSectorSeleccionado() {
        return sectorSeleccionado;
    }

    public void setSectorSeleccionado(TipologiaValorNombre sectorSeleccionado) {
        this.sectorSeleccionado = sectorSeleccionado;
    }

    public List<TipologiaValorNombre> getListaAutoridades() {
        return listaAutoridades;
    }

    public void setListaAutoridades(List<TipologiaValorNombre> listaAutoridades) {
        this.listaAutoridades = listaAutoridades;
    }

    public TipologiaValorNombre getAutoridadSeleccionado() {
        return autoridadSeleccionado;
    }

    public void setAutoridadSeleccionado(TipologiaValorNombre autoridadSeleccionado) {
        this.autoridadSeleccionado = autoridadSeleccionado;
    }

    public List<ConfiguracionNivel> getConfiguraciones() {
        return configuraciones;
    }

    public void setConfiguraciones(List<ConfiguracionNivel> configuraciones) {
        this.configuraciones = configuraciones;
    }

    public List<Integer> getNivelesDisponibles() {
        return nivelesDisponibles;
    }

    public void setNivelesDisponibles(List<Integer> nivelesDisponibles) {
        this.nivelesDisponibles = nivelesDisponibles;
    }

    public ConfiguracionNivel getConfiguracionNivelAuxSeleccionado() {
        return configuracionNivelAuxSeleccionado;
    }

    public void setConfiguracionNivelAuxSeleccionado(ConfiguracionNivel configuracionNivelAuxSeleccionado) {
        this.configuracionNivelAuxSeleccionado = configuracionNivelAuxSeleccionado;
    }

    public Integer getPosicionNivelConfiguracionNuevo() {
        return posicionNivelConfiguracionNuevo;
    }

    public void setPosicionNivelConfiguracionNuevo(Integer posicionNivelConfiguracionNuevo) {
        this.posicionNivelConfiguracionNuevo = posicionNivelConfiguracionNuevo;
    }

    public String getDescripcionNivelConfiguracionNuevo() {
        return descripcionNivelConfiguracionNuevo;
    }

    public void setDescripcionNivelConfiguracionNuevo(String descripcionNivelConfiguracionNuevo) {
        this.descripcionNivelConfiguracionNuevo = descripcionNivelConfiguracionNuevo;
    }

    public Long getMaximoNivelDPA() {
        return maximoNivelDPA;
    }

    // ****************************** CARGA MASIVA ****************************** //
    public void cargaMasiva(FileUploadEvent event) {

        HashMap<Long, List<String>> codsProcesados = new HashMap<Long, List<String>>();
        codsProcesados.put(ParamNivelConsul.UNO.getNivel(), new ArrayList<String>());
        codsProcesados.put(ParamNivelConsul.DOS.getNivel(), new ArrayList<String>());
        codsProcesados.put(ParamNivelConsul.TRES.getNivel(), new ArrayList<String>());
        codsProcesados.put(ParamNivelConsul.CUATRO.getNivel(), new ArrayList<String>());
        codsProcesados.put(ParamNivelConsul.CINCO.getNivel(), new ArrayList<String>());

        listaCrear = new HashMap<Long, List<Nivel>>();
        listaEliminar = new HashMap<Long, List<Nivel>>();
        listaActualizar = new HashMap<Long, List<Nivel>>();
        listaCrear.put(ParamNivelConsul.UNO.getNivel(), new ArrayList<Nivel>());
        listaCrear.put(ParamNivelConsul.DOS.getNivel(), new ArrayList<Nivel>());
        listaCrear.put(ParamNivelConsul.TRES.getNivel(), new ArrayList<Nivel>());
        listaCrear.put(ParamNivelConsul.CUATRO.getNivel(), new ArrayList<Nivel>());
        listaCrear.put(ParamNivelConsul.CINCO.getNivel(), new ArrayList<Nivel>());
        listaEliminar.put(ParamNivelConsul.UNO.getNivel(), new ArrayList<Nivel>());
        listaEliminar.put(ParamNivelConsul.DOS.getNivel(), new ArrayList<Nivel>());
        listaEliminar.put(ParamNivelConsul.TRES.getNivel(), new ArrayList<Nivel>());
        listaEliminar.put(ParamNivelConsul.CUATRO.getNivel(), new ArrayList<Nivel>());
        listaEliminar.put(ParamNivelConsul.CINCO.getNivel(), new ArrayList<Nivel>());
        listaActualizar.put(ParamNivelConsul.UNO.getNivel(), new ArrayList<Nivel>());
        listaActualizar.put(ParamNivelConsul.DOS.getNivel(), new ArrayList<Nivel>());
        listaActualizar.put(ParamNivelConsul.TRES.getNivel(), new ArrayList<Nivel>());
        listaActualizar.put(ParamNivelConsul.CUATRO.getNivel(), new ArrayList<Nivel>());
        listaActualizar.put(ParamNivelConsul.CINCO.getNivel(), new ArrayList<Nivel>());

        listaErrores = new ArrayList<ErroresCargaDPA>();
        String codRegion, codDepartamento, codSecretaria, codMunicipio, codOtro;
        String nomRegion, nomDepartamento, nomSecretaria, nomMunicipio, nomOtro, tipo;
        Row rowDato;
        int rowNumber;
        int lastRow;
        UploadedFile file = event.getFile();
        try {
            Workbook wb = new XSSFWorkbook(file.getInputstream());
            Sheet hoja = wb.getSheetAt(0);
            rowDato = hoja.getRow(0);
                String niv1 = ImpUtil.getValorCelda(wb, rowDato.getCell(0));
                String niv2 = ImpUtil.getValorCelda(wb, rowDato.getCell(2));
                String niv3 = ImpUtil.getValorCelda(wb, rowDato.getCell(4));
                String niv4 = ImpUtil.getValorCelda(wb, rowDato.getCell(6));
                String niv5 = ImpUtil.getValorCelda(rowDato.getCell(8));
                String colTipo = ImpUtil.getValorCelda(rowDato.getCell(24));
            rowNumber = 1;
            hayRegErroneos = false;
            while (true) {
                rowNumber++;
                rowDato = hoja.getRow(rowNumber);
                if (rowDato == null) {
                    lastRow = rowNumber;
                    break;
                }
                ErroresCargaDPA er = new ErroresCargaDPA();
                codRegion = ImpUtil.getValorCelda(wb, rowDato.getCell(0));
                er.setCodRegion(codRegion);
                nomRegion = ImpUtil.getValorCelda(rowDato.getCell(1));
                er.setNomRegion(nomRegion);
                codDepartamento = ImpUtil.getValorCelda(wb, rowDato.getCell(2));
                er.setCodDepartamento(codDepartamento);
                nomDepartamento = ImpUtil.getValorCelda(rowDato.getCell(3));
                er.setNomDepartamento(nomDepartamento);
                codSecretaria = ImpUtil.getValorCelda(wb, rowDato.getCell(4));
                er.setCodSecretaria(codSecretaria);
                nomSecretaria = ImpUtil.getValorCelda(rowDato.getCell(5));
                er.setNomSecretaria(nomSecretaria);
                codMunicipio = ImpUtil.getValorCelda(wb, rowDato.getCell(6));
                er.setCodMunicipio(codMunicipio);
                nomMunicipio = ImpUtil.getValorCelda(rowDato.getCell(7));
                er.setNomMunicipio(nomMunicipio);
                codOtro = ImpUtil.getValorCelda(wb, rowDato.getCell(8));
                er.setCodOtro(codOtro);
                nomOtro = ImpUtil.getValorCelda(rowDato.getCell(9));
                er.setNomOtro(nomOtro);
                tipo = ImpUtil.getValorCelda(rowDato.getCell(24));
                er.setTipo(tipo);

                if (UtilCadena.isNullOVacio(codRegion) && UtilCadena.isNullOVacio(codDepartamento) && UtilCadena.isNullOVacio(codSecretaria) && UtilCadena.isNullOVacio(codMunicipio)
                        && UtilCadena.isNullOVacio(codOtro)) {
                    lastRow = rowNumber;
                    break;
                }
                if (seleccionNivel4 != null) {
                    if (!UtilCadena.isNullOVacio(codMunicipio) && !codMunicipio.equals(seleccionNivel4.getCodigo())) {
                        continue;
                    } else if (!UtilCadena.isNullOVacio(codOtro) && ParamNivelConsul.CINCO.getNivel().intValue() > maximoNivelDPA.intValue()) {
                        er.setError(Utilidades.obtenerMensaje("administracion.parametros.codMalRelacionados")+": "+niv5);
                        listaErrores.add(er);
                        hayRegErroneos = true;
                        continue;
                    }
                }
                if (seleccionNivel3 != null) {
                    if (!UtilCadena.isNullOVacio(codSecretaria) && !codSecretaria.equals(seleccionNivel3.getCodigo())) {
                        continue;
                    } else if (!UtilCadena.isNullOVacio(codMunicipio) && ParamNivelConsul.CUATRO.getNivel().intValue() > maximoNivelDPA.intValue()) {
                        er.setError(Utilidades.obtenerMensaje("administracion.parametros.codMalRelacionados")+": "+niv4);
                        listaErrores.add(er);
                        hayRegErroneos = true;
                        continue;
                    }
                }
                if (seleccionNivel2 != null) {
                    if (!UtilCadena.isNullOVacio(codDepartamento) && !codDepartamento.equals(seleccionNivel2.getCodigo())) {
                        continue;
                    } else if (!UtilCadena.isNullOVacio(codSecretaria) && ParamNivelConsul.TRES.getNivel().intValue() > maximoNivelDPA.intValue()) {
                        er.setError(Utilidades.obtenerMensaje("administracion.parametros.codMalRelacionados")+": "+niv3);
                        listaErrores.add(er);
                        hayRegErroneos = true;
                        continue;
                    }
                }
                if (seleccionNivel1 != null) {
                    if (!UtilCadena.isNullOVacio(codRegion) && !codRegion.equals(seleccionNivel1.getCodigo())) {
                        continue;
                    } else if (!UtilCadena.isNullOVacio(codDepartamento) && ParamNivelConsul.DOS.getNivel().intValue() > maximoNivelDPA.intValue()) {
                        er.setError(Utilidades.obtenerMensaje("administracion.parametros.codMalRelacionados")+": "+niv2);
                        listaErrores.add(er);
                        hayRegErroneos = true;
                        continue;
                    }
                }

                boolean hay = false;
                if (seleccionNivel1 == null && !UtilCadena.isNullOVacio(codRegion)) {                    
                    if (UtilCadena.isNullOVacio(codDepartamento) && UtilCadena.isNullOVacio(codSecretaria) && UtilCadena.isNullOVacio(codMunicipio)
                            && UtilCadena.isNullOVacio(codOtro)) {//Es el último nivel de la fila
                        hay = true;
                        if (codsProcesados.get(ParamNivelConsul.UNO.getNivel()).contains(codRegion)) {
                            continue;
                        }
                        if ("2".equals(tipo)) {//Eliminar
                            Nivel nivel = facEstDPA.consultarNivelAux(codRegion, ParamNivelConsul.UNO.getNivel());
                            if (nivel == null) {
                                er.setError(Utilidades.obtenerMensaje("administracion.configuracion.codigoNoExiste")+": "+niv1);
                                listaErrores.add(er);
                                hayRegErroneos = true;
                                continue;
                            } else if (!nivel.getPadre().getCodigo().equals(seleccionNivel0.getCodigo())) {
                                er.setError(Utilidades.obtenerMensaje("administracion.parametros.codMalRelacionados")+": "+niv1);
                                listaErrores.add(er);
                                hayRegErroneos = true;
                                continue;
                            } else {
                                int i = nivel.getNivelesList().size();
                                for (Nivel n : nivel.getNivelesList()) {
                                    if (listaEliminar.get(ParamNivelConsul.DOS.getNivel()).contains(n)) {
                                        i--;
                                    }
                                }
                                if (i == 0) {
                                    listaEliminar.get(ParamNivelConsul.UNO.getNivel()).add(nivel);
                                    codsProcesados.get(ParamNivelConsul.UNO.getNivel()).add(nivel.getCodigo());
                                } else {
                                    er.setError(Utilidades.obtenerMensaje("administracion.configuracion.elementosDependientes")+": "+niv1);
                                    listaErrores.add(er);
                                    hayRegErroneos = true;
                                    continue;
                                }
                            }
                        }
                    }

                    if ("1".equals(tipo) && !codsProcesados.get(ParamNivelConsul.UNO.getNivel()).contains(codRegion)) {//Crear o actualizar
                        if (UtilCadena.isNullOVacio(nomRegion)) {
                            er.setError(Utilidades.obtenerMensaje("administracion.parametros.infoIncompleta")+": "+niv1);
                            listaErrores.add(er);
                            hayRegErroneos = true;
                            continue;
                        }
                        Nivel nivel = facEstDPA.consultarNivelAux(codRegion, ParamNivelConsul.UNO.getNivel());
                        if (nivel != null) {//Actualizar
                            if (!nivel.getPadre().getCodigo().equals(seleccionNivel0.getCodigo())) {
                                er.setError(Utilidades.obtenerMensaje("administracion.parametros.codMalRelacionados")+": "+niv1);
                                listaErrores.add(er);
                                hayRegErroneos = true;
                                continue;
                            } else if (!nivel.getDescripcion().equals(nomRegion)) {
                                nivel.setDescripcion(nomRegion);
                                listaActualizar.get(ParamNivelConsul.UNO.getNivel()).add(nivel);
                                codsProcesados.get(ParamNivelConsul.UNO.getNivel()).add(nivel.getCodigo());
                            }
                        } else {//Crear
                            Nivel padre = facEstDPA.consultarNivelAux(seleccionNivel0.getCodigo(), ParamNivelConsul.CERO.getNivel());
                            nivel = new Nivel();
                            nivel.setCodigo(codRegion);
                            nivel.setDescripcion(nomRegion);
                            nivel.setPadre(padre);
                            nivel.setConfiguracion(facEstDPA.getConfiguracionNivel(ParamNivelConsul.UNO.getNivel()));
                            listaCrear.get(ParamNivelConsul.UNO.getNivel()).add(nivel);
                            codsProcesados.get(ParamNivelConsul.UNO.getNivel()).add(nivel.getCodigo());
                        }
                    }

                }
                
                if (seleccionNivel2 == null && !UtilCadena.isNullOVacio(codDepartamento)
                        && (seleccionNivel1 != null || !UtilCadena.isNullOVacio(codRegion))) {
                    if (UtilCadena.isNullOVacio(codSecretaria) && UtilCadena.isNullOVacio(codMunicipio)
                            && UtilCadena.isNullOVacio(codOtro)) {//Es el último nivel de la fila
                        hay = true;
                        if (codsProcesados.get(ParamNivelConsul.DOS.getNivel()).contains(codDepartamento)) {
                            continue;
                        }
                        if ("2".equals(tipo)) {//Eliminar
                            Nivel nivel = facEstDPA.consultarNivelAux(codDepartamento, ParamNivelConsul.DOS.getNivel());
                            if (nivel == null) {
                                er.setError(Utilidades.obtenerMensaje("administracion.configuracion.codigoNoExiste")+": "+niv2);
                                listaErrores.add(er);
                                hayRegErroneos = true;
                                continue;
                            } else if ((seleccionNivel1 == null && !nivel.getPadre().getCodigo().equals(codRegion))
                                    || (seleccionNivel1 != null && !nivel.getPadre().getCodigo().equals(seleccionNivel1.getCodigo()))) {
                                er.setError(Utilidades.obtenerMensaje("administracion.parametros.codMalRelacionados")+": "+niv1+", "+niv2);
                                listaErrores.add(er);
                                hayRegErroneos = true;
                                continue;
                            } else {
                                int i = nivel.getNivelesList().size();
                                for (Nivel n : nivel.getNivelesList()) {
                                    if (listaEliminar.get(ParamNivelConsul.TRES.getNivel()).contains(n)) {
                                        i--;
                                    }
                                }
                                if (i == 0) {
                                    listaEliminar.get(ParamNivelConsul.DOS.getNivel()).add(nivel);
                                    codsProcesados.get(ParamNivelConsul.DOS.getNivel()).add(nivel.getCodigo());
                                } else {
                                    er.setError(Utilidades.obtenerMensaje("administracion.configuracion.elementosDependientes")+": "+niv2);
                                    listaErrores.add(er);
                                    hayRegErroneos = true;
                                    continue;
                                }
                            }
                        }
                    }

                    if ("1".equals(tipo) && !codsProcesados.get(ParamNivelConsul.DOS.getNivel()).contains(codDepartamento)) {//Crear o actualizar
                        if (UtilCadena.isNullOVacio(nomDepartamento)) {
                            er.setError(Utilidades.obtenerMensaje("administracion.parametros.infoIncompleta")+": "+niv2);
                            listaErrores.add(er);
                            hayRegErroneos = true;
                            continue;
                        }
                        Nivel nivel = facEstDPA.consultarNivelAux(codDepartamento, ParamNivelConsul.DOS.getNivel());
                        if (nivel != null) {//Actualizar
                            if ((seleccionNivel1 == null && !nivel.getPadre().getCodigo().equals(codRegion))
                                    || (seleccionNivel1 != null && !nivel.getPadre().getCodigo().equals(seleccionNivel1.getCodigo()))) {
                                er.setError(Utilidades.obtenerMensaje("administracion.parametros.codMalRelacionados")+": "+niv1+", "+niv2);
                                listaErrores.add(er);
                                hayRegErroneos = true;
                                continue;
                            } else if (!nivel.getDescripcion().equals(nomDepartamento)) {
                                nivel.setDescripcion(nomDepartamento);
                                listaActualizar.get(ParamNivelConsul.DOS.getNivel()).add(nivel);
                                codsProcesados.get(ParamNivelConsul.DOS.getNivel()).add(nivel.getCodigo());
                            }
                        } else {//Crear
                            Nivel padre = facEstDPA.consultarNivelAux(codRegion, ParamNivelConsul.UNO.getNivel());
                            if (padre == null) {
                                padre = new Nivel();
                                padre.setCodigo(codRegion);
                            }
                            nivel = new Nivel();
                            nivel.setCodigo(codDepartamento);
                            nivel.setDescripcion(nomDepartamento);
                            nivel.setPadre(padre);
                            nivel.setConfiguracion(facEstDPA.getConfiguracionNivel(ParamNivelConsul.DOS.getNivel()));
                            listaCrear.get(ParamNivelConsul.DOS.getNivel()).add(nivel);
                            codsProcesados.get(ParamNivelConsul.DOS.getNivel()).add(nivel.getCodigo());
                        }
                    }
                }

                if (seleccionNivel3 == null && !UtilCadena.isNullOVacio(codSecretaria)
                        && (seleccionNivel1 != null || !UtilCadena.isNullOVacio(codRegion)) 
                        && (seleccionNivel2 != null || !UtilCadena.isNullOVacio(codDepartamento)) ) {
                    if (UtilCadena.isNullOVacio(codMunicipio)
                            && UtilCadena.isNullOVacio(codOtro)) {//Es el último nivel de la fila
                        hay = true;
                        if (codsProcesados.get(ParamNivelConsul.TRES.getNivel()).contains(codSecretaria)) {
                            continue;
                        }
                        if ("2".equals(tipo)) {//Eliminar
                            Nivel nivel = facEstDPA.consultarNivelAux(codSecretaria, ParamNivelConsul.TRES.getNivel());
                            if (nivel == null) {
                                er.setError(Utilidades.obtenerMensaje("administracion.configuracion.codigoNoExiste")+": "+niv3);
                                listaErrores.add(er);
                                hayRegErroneos = true;
                                continue;
                            } else if ((seleccionNivel2 == null && !nivel.getPadre().getCodigo().equals(codDepartamento))
                                    || (seleccionNivel2 != null && !nivel.getPadre().getCodigo().equals(seleccionNivel2.getCodigo()))) {
                                er.setError(Utilidades.obtenerMensaje("administracion.parametros.codMalRelacionados")+": "+niv2+", "+niv3);
                                listaErrores.add(er);
                                hayRegErroneos = true;
                                continue;
                            } else {
                                int i = nivel.getNivelesList().size();
                                for (Nivel n : nivel.getNivelesList()) {
                                    if (listaEliminar.get(ParamNivelConsul.CUATRO.getNivel()).contains(n)) {
                                        i--;
                                    }
                                }
                                if (i == 0) {
                                    listaEliminar.get(ParamNivelConsul.TRES.getNivel()).add(nivel);
                                    codsProcesados.get(ParamNivelConsul.TRES.getNivel()).add(nivel.getCodigo());
                                } else {
                                    er.setError(Utilidades.obtenerMensaje("administracion.configuracion.elementosDependientes")+": "+niv3);
                                    listaErrores.add(er);
                                    hayRegErroneos = true;
                                    continue;
                                }
                            }
                        }
                    }

                    if ("1".equals(tipo) && !codsProcesados.get(ParamNivelConsul.TRES.getNivel()).contains(codSecretaria)) {//Crear o actualizar
                        if (UtilCadena.isNullOVacio(nomSecretaria)) {
                            er.setError(Utilidades.obtenerMensaje("administracion.parametros.infoIncompleta")+": "+niv3);
                            listaErrores.add(er);
                            hayRegErroneos = true;
                            continue;
                        }
                        Nivel nivel = facEstDPA.consultarNivelAux(codSecretaria, ParamNivelConsul.TRES.getNivel());
                        if (nivel != null) {//Actualizar
                            if ((seleccionNivel2 == null && !nivel.getPadre().getCodigo().equals(codDepartamento))
                                    || (seleccionNivel2 != null && !nivel.getPadre().getCodigo().equals(seleccionNivel2.getCodigo()))) {
                                er.setError(Utilidades.obtenerMensaje("administracion.parametros.codMalRelacionados")+": "+niv2+", "+niv3);
                                listaErrores.add(er);
                                hayRegErroneos = true;
                                continue;
                            } else if (!nivel.getDescripcion().equals(nomSecretaria)) {
                                nivel.setDescripcion(nomSecretaria);
                                listaActualizar.get(ParamNivelConsul.TRES.getNivel()).add(nivel);
                                codsProcesados.get(ParamNivelConsul.TRES.getNivel()).add(nivel.getCodigo());
                            }
                        } else {//Crear
                            Nivel padre = facEstDPA.consultarNivelAux(codDepartamento, ParamNivelConsul.DOS.getNivel());
                            if (padre == null) {
                                padre = new Nivel();
                                padre.setCodigo(codDepartamento);
                            }
                            nivel = new Nivel();
                            nivel.setCodigo(codSecretaria);
                            nivel.setDescripcion(nomSecretaria);
                            nivel.setPadre(padre);
                            nivel.setConfiguracion(facEstDPA.getConfiguracionNivel(ParamNivelConsul.TRES.getNivel()));
                            listaCrear.get(ParamNivelConsul.TRES.getNivel()).add(nivel);
                            codsProcesados.get(ParamNivelConsul.TRES.getNivel()).add(nivel.getCodigo());
                        }
                    }
                }
                
                if (seleccionNivel4 == null && !UtilCadena.isNullOVacio(codMunicipio)
                        && (seleccionNivel1 != null || !UtilCadena.isNullOVacio(codRegion)) 
                        && (seleccionNivel2 != null || !UtilCadena.isNullOVacio(codDepartamento)) 
                        && (seleccionNivel3 != null || !UtilCadena.isNullOVacio(codSecretaria)) ) {
                    if (UtilCadena.isNullOVacio(codOtro)) {//Es el último nivel de la fila
                        hay = true;
                        if (codsProcesados.get(ParamNivelConsul.CUATRO.getNivel()).contains(codMunicipio)) {
                            continue;
                        }
                        if ("2".equals(tipo)) {//Eliminar
                            Nivel nivel = facEstDPA.consultarNivelAux(codMunicipio, ParamNivelConsul.CUATRO.getNivel());
                            if (nivel == null) {
                                er.setError(Utilidades.obtenerMensaje("administracion.configuracion.codigoNoExiste")+": "+niv4);
                                listaErrores.add(er);
                                hayRegErroneos = true;
                                continue;
                            } else if ((seleccionNivel3 == null && !nivel.getPadre().getCodigo().equals(codSecretaria))
                                    || (seleccionNivel3 != null && !nivel.getPadre().getCodigo().equals(seleccionNivel3.getCodigo()))) {
                                er.setError(Utilidades.obtenerMensaje("administracion.parametros.codMalRelacionados")+": "+niv3+", "+niv4);
                                listaErrores.add(er);
                                hayRegErroneos = true;
                                continue;
                            } else {
                                int i = nivel.getNivelesList().size();
                                for (Nivel n : nivel.getNivelesList()) {
                                    if (listaEliminar.get(ParamNivelConsul.CINCO.getNivel()).contains(n)) {
                                        i--;
                                    }
                                }
                                if (i == 0) {
                                    listaEliminar.get(ParamNivelConsul.CUATRO.getNivel()).add(nivel);
                                    codsProcesados.get(ParamNivelConsul.CUATRO.getNivel()).add(nivel.getCodigo());
                                } else {
                                    er.setError(Utilidades.obtenerMensaje("administracion.configuracion.elementosDependientes")+": "+niv4);
                                    listaErrores.add(er);
                                    hayRegErroneos = true;
                                    continue;
                                }
                            }
                        }
                    }

                    if ("1".equals(tipo) && !codsProcesados.get(ParamNivelConsul.CUATRO.getNivel()).contains(codMunicipio)) {//Crear o actualizar
                        if (UtilCadena.isNullOVacio(nomMunicipio)) {
                            er.setError(Utilidades.obtenerMensaje("administracion.parametros.infoIncompleta")+": "+niv4);
                            listaErrores.add(er);
                            hayRegErroneos = true;
                            continue;
                        }
                        Nivel nivel = facEstDPA.consultarNivelAux(codMunicipio, ParamNivelConsul.CUATRO.getNivel());
                        if (nivel != null) {//Actualizar
                            if ((seleccionNivel3 == null && !nivel.getPadre().getCodigo().equals(codSecretaria))
                                    || (seleccionNivel3 != null && !nivel.getPadre().getCodigo().equals(seleccionNivel3.getCodigo()))) {
                                er.setError(Utilidades.obtenerMensaje("administracion.parametros.codMalRelacionados")+": "+niv3+", "+niv4);
                                listaErrores.add(er);
                                hayRegErroneos = true;
                                continue;
                            } else if (!nivel.getDescripcion().equals(nomMunicipio)) {
                                nivel.setDescripcion(nomMunicipio);
                                listaActualizar.get(ParamNivelConsul.CUATRO.getNivel()).add(nivel);
                                codsProcesados.get(ParamNivelConsul.CUATRO.getNivel()).add(nivel.getCodigo());
                            }
                        } else {//Crear
                            Nivel padre = facEstDPA.consultarNivelAux(codSecretaria, ParamNivelConsul.TRES.getNivel());
                            if (padre == null) {
                                padre = new Nivel();
                                padre.setCodigo(codSecretaria);
                            }
                            nivel = new Nivel();
                            nivel.setCodigo(codMunicipio);
                            nivel.setDescripcion(nomMunicipio);
                            nivel.setPadre(padre);
                            nivel.setConfiguracion(facEstDPA.getConfiguracionNivel(ParamNivelConsul.CUATRO.getNivel()));
                            listaCrear.get(ParamNivelConsul.CUATRO.getNivel()).add(nivel);
                            codsProcesados.get(ParamNivelConsul.CUATRO.getNivel()).add(nivel.getCodigo());
                        }
                    }
                }
                if (!UtilCadena.isNullOVacio(codOtro) 
                        && (seleccionNivel1 != null || !UtilCadena.isNullOVacio(codRegion)) 
                        && (seleccionNivel2 != null || !UtilCadena.isNullOVacio(codDepartamento)) 
                        && (seleccionNivel3 != null || !UtilCadena.isNullOVacio(codSecretaria)) 
                        && (seleccionNivel4 != null || !UtilCadena.isNullOVacio(codMunicipio))) {
                    hay = true;
                    if (codsProcesados.get(ParamNivelConsul.CINCO.getNivel()).contains(codOtro)) {
                        continue;
                    }
                    if ("2".equals(tipo)) {//Eliminar
                        Nivel nivel = facEstDPA.consultarNivelAux(codOtro, ParamNivelConsul.CINCO.getNivel());
                        if (nivel == null) {
                            er.setError(Utilidades.obtenerMensaje("administracion.configuracion.codigoNoExiste")+": "+niv5);
                            listaErrores.add(er);
                            hayRegErroneos = true;
                            continue;
                        } else if ((seleccionNivel4 == null && !nivel.getPadre().getCodigo().equals(codMunicipio))
                                    || (seleccionNivel4 != null && !nivel.getPadre().getCodigo().equals(seleccionNivel4.getCodigo()))) {
                            er.setError(Utilidades.obtenerMensaje("administracion.parametros.codMalRelacionados")+": "+niv4+", "+niv5);
                            listaErrores.add(er);
                            hayRegErroneos = true;
                            continue;
                        } else {
                            if (nivel.getEstablecimientoList() == null || nivel.getEstablecimientoList().isEmpty()) {
                                listaEliminar.get(ParamNivelConsul.CINCO.getNivel()).add(nivel);
                                codsProcesados.get(ParamNivelConsul.CINCO.getNivel()).add(nivel.getCodigo());
                            } else {
                                er.setError(Utilidades.obtenerMensaje("administracion.configuracion.elementosDependientes")+": "+niv5);
                                listaErrores.add(er);
                                hayRegErroneos = true;
                                continue;
                            }
                        }
                    }

                    if ("1".equals(tipo) && !codsProcesados.get(ParamNivelConsul.CINCO.getNivel()).contains(codOtro)) {//Crear o actualizar
                        if (UtilCadena.isNullOVacio(nomOtro)) {
                            er.setError(Utilidades.obtenerMensaje("administracion.parametros.infoIncompleta")+": "+niv5);
                            listaErrores.add(er);
                            hayRegErroneos = true;
                            continue;
                        }
                        Nivel nivel = facEstDPA.consultarNivelAux(codOtro, ParamNivelConsul.CINCO.getNivel());
                        if (nivel != null) {//Actualizar
                            if ((seleccionNivel4 == null && !nivel.getPadre().getCodigo().equals(codMunicipio))
                                    || (seleccionNivel4 != null && !nivel.getPadre().getCodigo().equals(seleccionNivel4.getCodigo()))) {
                                er.setError(Utilidades.obtenerMensaje("administracion.parametros.codMalRelacionados")+": "+niv4+", "+niv5);
                                listaErrores.add(er);
                                hayRegErroneos = true;
                                continue;
                            } else if (!nivel.getDescripcion().equals(nomOtro)) {
                                nivel.setDescripcion(nomOtro);
                                listaActualizar.get(ParamNivelConsul.CINCO.getNivel()).add(nivel);
                                codsProcesados.get(ParamNivelConsul.CINCO.getNivel()).add(nivel.getCodigo());
                            }
                        } else {//Crear
                            Nivel padre = facEstDPA.consultarNivelAux(codMunicipio, ParamNivelConsul.CUATRO.getNivel());
                            if (padre == null) {
                                padre = new Nivel();
                                padre.setCodigo(codMunicipio);
                            }
                            nivel = new Nivel();
                            nivel.setCodigo(codOtro);
                            nivel.setDescripcion(nomOtro);
                            nivel.setPadre(padre);
                            nivel.setConfiguracion(facEstDPA.getConfiguracionNivel(ParamNivelConsul.CINCO.getNivel()));
                            listaCrear.get(ParamNivelConsul.CINCO.getNivel()).add(nivel);
                            codsProcesados.get(ParamNivelConsul.CINCO.getNivel()).add(nivel.getCodigo());
                        }
                    }
                }
                if (!hay) {
                    er.setError(Utilidades.obtenerMensaje("administracion.parametros.codMalRelacionados"));
                    listaErrores.add(er);
                    hayRegErroneos = true;
                    continue;
                }

                listaErrores.add(er);
            }

            if (hayRegErroneos) {
                parametros.put("col1", niv1);
                parametros.put("col2", niv2);
                parametros.put("col3", niv3);
                parametros.put("col4", niv4);
                parametros.put("col5", niv5);
                parametros.put("col16", colTipo);
                rowDato = hoja.getRow(1);
                parametros.put("colCode", ImpUtil.getValorCelda(rowDato.getCell(0)));
                parametros.put("colName", ImpUtil.getValorCelda(rowDato.getCell(1)));
                parametros.put(JRParameter.REPORT_RESOURCE_BUNDLE, ResourceBundle.getBundle("etiquetas", FacesContext.getCurrentInstance().getViewRoot().getLocale()));
            }
        } catch (ErrorExcel ex) {
            Messages.addFlashGlobalError(Utilidades.obtenerMensaje("adm.parametros.config.carga.archivoIncorrecto"));
        } catch (Exception e) {
            UtilOut.println(e);
            ManejadorErrores.ingresarExcepcionEnLog(e, AdminDpa.class);
            Messages.addFlashGlobalError(Utilidades.obtenerMensaje("adm.parametros.config.carga.errorRegistro"));
        }
    }    

    public void guardar() throws IOException {
        try {
            for (Nivel n : listaCrear.get(ParamNivelConsul.UNO.getNivel())) {
                facEstDPA.guardarDpa(usuarioSesion.getUsuario().getUsername(), n);
            }
            for (Nivel n : listaCrear.get(ParamNivelConsul.DOS.getNivel())) {
                Nivel padre = n.getPadre();
                if (padre.getId() == null) {
                    n.setPadre(facEstDPA.consultarNivelAux(padre.getCodigo(), ParamNivelConsul.UNO.getNivel()));
                }
                facEstDPA.guardarDpa(usuarioSesion.getUsuario().getUsername(), n);
            }
            for (Nivel n : listaCrear.get(ParamNivelConsul.TRES.getNivel())) {
                Nivel padre = n.getPadre();
                if (padre.getId() == null) {
                    n.setPadre(facEstDPA.consultarNivelAux(padre.getCodigo(), ParamNivelConsul.DOS.getNivel()));
                }
                facEstDPA.guardarDpa(usuarioSesion.getUsuario().getUsername(), n);
            }
            for (Nivel n : listaCrear.get(ParamNivelConsul.CUATRO.getNivel())) {
                Nivel padre = n.getPadre();
                if (padre.getId() == null) {
                    n.setPadre(facEstDPA.consultarNivelAux(padre.getCodigo(), ParamNivelConsul.TRES.getNivel()));
                }
                facEstDPA.guardarDpa(usuarioSesion.getUsuario().getUsername(), n);
            }
            for (Nivel n : listaCrear.get(ParamNivelConsul.CINCO.getNivel())) {
                Nivel padre = n.getPadre();
                if (padre.getId() == null) {
                    n.setPadre(facEstDPA.consultarNivelAux(padre.getCodigo(), ParamNivelConsul.CUATRO.getNivel()));
                }
                facEstDPA.guardarDpa(usuarioSesion.getUsuario().getUsername(), n);
            }

            for (Nivel n : listaActualizar.get(ParamNivelConsul.UNO.getNivel())) {
                facEstDPA.actualizarDpa(usuarioSesion.getUsuario().getUsername(), n);
            }
            for (Nivel n : listaActualizar.get(ParamNivelConsul.DOS.getNivel())) {
                facEstDPA.actualizarDpa(usuarioSesion.getUsuario().getUsername(), n);
            }
            for (Nivel n : listaActualizar.get(ParamNivelConsul.TRES.getNivel())) {
                facEstDPA.actualizarDpa(usuarioSesion.getUsuario().getUsername(), n);
            }
            for (Nivel n : listaActualizar.get(ParamNivelConsul.CUATRO.getNivel())) {
                facEstDPA.actualizarDpa(usuarioSesion.getUsuario().getUsername(), n);
            }
            for (Nivel n : listaActualizar.get(ParamNivelConsul.CINCO.getNivel())) {
                facEstDPA.actualizarDpa(usuarioSesion.getUsuario().getUsername(), n);
            }

            for (Nivel n : listaEliminar.get(ParamNivelConsul.CINCO.getNivel())) {
                facEstDPA.eliminarDpa(usuarioSesion.getUsuario().getUsername(), n);
            }
            for (Nivel n : listaEliminar.get(ParamNivelConsul.CUATRO.getNivel())) {
                facEstDPA.eliminarDpa(usuarioSesion.getUsuario().getUsername(), n);
            }
            for (Nivel n : listaEliminar.get(ParamNivelConsul.TRES.getNivel())) {
                facEstDPA.eliminarDpa(usuarioSesion.getUsuario().getUsername(), n);
            }
            for (Nivel n : listaEliminar.get(ParamNivelConsul.DOS.getNivel())) {
                facEstDPA.eliminarDpa(usuarioSesion.getUsuario().getUsername(), n);
            }
            for (Nivel n : listaEliminar.get(ParamNivelConsul.UNO.getNivel())) {
                facEstDPA.eliminarDpa(usuarioSesion.getUsuario().getUsername(), n);
            }
            
            resetearFiltros();
            inicializar();
            Messages.addFlashGlobalInfo(Utilidades.obtenerMensaje("adm.parametros.config.carga.datosGuardados"));

        } catch (Exception e) {
            ManejadorErrores.ingresarExcepcionEnLog(e, AdminDpa.class); //UtilOut.println(e);
            Messages.addFlashGlobalError(Utilidades.obtenerMensaje("adm.parametros.config.carga.errorRegistro"));
        }
    }

    public StreamedContent getExportFile() {
        InputStream stream = null;
        try {
            stream = UtilExportar.getDocumentoXLSX("cargaDPA", "ErroresCargaDPA", parametros, listaErrores);

        } catch (Exception ex) {
            ManejadorErrores.ingresarExcepcionEnLog(ex, AdminDpa.class); //UtilOut.println(ex);
            Messages.addFlashGlobalError(Utilidades.obtenerMensaje("adm.parametros.config.carga.errorRegistro"));
        } finally {
            try {
                stream.close();
            } catch (Exception ex) {
                ManejadorErrores.ingresarExcepcionEnLog(ex, AdminDpa.class);
                stream = null;
            }
            return new DefaultStreamedContent(stream, "application/x-download", "ErroresCargaDPA.xlsx");
        }
    }

    public boolean getHayRegErroneos() {
        return hayRegErroneos;
    }

    public boolean isCargaPais() {
        return cargaPais;
    }

    public void setCargaPais(boolean cargaPais) {
        if (cargaPais) {
            resetearFiltros();
        }
        this.cargaPais = cargaPais;
    }
}
