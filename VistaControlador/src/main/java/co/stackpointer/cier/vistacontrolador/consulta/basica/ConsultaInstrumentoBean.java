/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.consulta.basica;

import co.stackpointer.cier.modelo.auditoria.RegistrarAuditoriaDigitacion;
import co.stackpointer.cier.modelo.entidad.acceso.Usuario;
import co.stackpointer.cier.modelo.entidad.digitado.Adjunto;
import co.stackpointer.cier.modelo.entidad.digitado.Elemento;
import co.stackpointer.cier.modelo.entidad.digitado.InstrumentoDig;
import co.stackpointer.cier.modelo.entidad.digitado.RespuestaDig;
import co.stackpointer.cier.modelo.entidad.dpa.ConfiguracionNivel;
import co.stackpointer.cier.modelo.entidad.dpa.Nivel;
import co.stackpointer.cier.modelo.entidad.instrumento.Documento;
import co.stackpointer.cier.modelo.report.instrumento.Croquis;
import co.stackpointer.cier.modelo.report.instrumento.Mantenimiento;
import co.stackpointer.cier.modelo.report.instrumento.CondEstruc;
import co.stackpointer.cier.modelo.report.instrumento.Observaciones;
import co.stackpointer.cier.modelo.report.instrumento.AmbMismaTipo;
import co.stackpointer.cier.modelo.report.instrumento.GrupoEdificios;
import co.stackpointer.cier.modelo.report.instrumento.DatosAdic;
import co.stackpointer.cier.modelo.report.instrumento.EspaciosEscolares;
import co.stackpointer.cier.modelo.entidad.instrumento.Instrumento;
import co.stackpointer.cier.modelo.entidad.instrumento.ModuloIns;
import co.stackpointer.cier.modelo.entidad.instrumento.Observacion;
import co.stackpointer.cier.modelo.entidad.instrumento.Pregunta;
import co.stackpointer.cier.modelo.entidad.instrumento.Respuesta;
import co.stackpointer.cier.modelo.entidad.instrumento.Seccion;
import co.stackpointer.cier.modelo.entidad.instrumento.TipoElemento;
import co.stackpointer.cier.modelo.entidad.instrumento.TipologiaValorNombre;
import co.stackpointer.cier.modelo.excepcion.ErrorProcedimientoBD;
import co.stackpointer.cier.modelo.fachada.ArchivoFacadeLocal;
import co.stackpointer.cier.modelo.fachada.ConsultaBasicaFacadeLocal;
import co.stackpointer.cier.modelo.fachada.DPAFacadeLocal;
import co.stackpointer.cier.modelo.fachada.EstructuraDPAFacadeLocal;
import co.stackpointer.cier.modelo.fachada.InstrumentoFacadeLocal;
import co.stackpointer.cier.modelo.fachada.ParametrosFacadeLocal;
import co.stackpointer.cier.modelo.filtro.basica.FiltroConsultaBasica;
import co.stackpointer.cier.modelo.interfaceDPA.ConfNivelDPA;
import co.stackpointer.cier.modelo.tipo.BitacoraTransaccion;
import co.stackpointer.cier.modelo.tipo.EstadoInstrumento;
import co.stackpointer.cier.modelo.tipo.FuenteDigitacion;
import co.stackpointer.cier.modelo.tipo.ParamNivelConsul;
import co.stackpointer.cier.modelo.tipo.SiNoNA;
import co.stackpointer.cier.modelo.utilidad.UtilExportar;
import co.stackpointer.cier.modelo.utilidad.UtilFecha;
import co.stackpointer.cier.modelo.utilidad.UtilMapas;
import co.stackpointer.cier.vista.ManejadorErrores;
import co.stackpointer.cier.vista.Utilidades;
import static co.stackpointer.cier.vista.Utilidades.mostrarMensajeError;
import static co.stackpointer.cier.vista.Utilidades.mostrarMensajeInfo;
import co.stackpointer.cier.vistacontrolador.bean.GentionVariablesBean;
import co.stackpointer.cier.vistacontrolador.bean.UsuarioSesionBean;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import java.util.Collections;
import javax.faces.bean.ManagedProperty;
import net.sf.jasperreports.engine.JRParameter;
import org.primefaces.event.map.OverlaySelectEvent;
import co.stackpointer.cier.modelo.tipo.SuficienteInsuficiente;
import co.stackpointer.cier.modelo.tipo.TipoElem;
import co.stackpointer.cier.modelo.utilidad.UtilCadena;
import co.stackpointer.cier.vistacontrolador.bean.LoginBean;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.context.RequestContext;
import org.primefaces.model.UploadedFile;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author user
 */
@ManagedBean(name = "consultaInstrumentoBean")
@ViewScoped
public class ConsultaInstrumentoBean implements Serializable {

    @ManagedProperty(value = "#{login}")
    private LoginBean currentLogin;
    @EJB
    private ConsultaBasicaFacadeLocal facBasica;
    @EJB
    InstrumentoFacadeLocal facInstrumentos;
    @EJB
    private ParametrosFacadeLocal facParam;
    @EJB
    private EstructuraDPAFacadeLocal facEstDPA;
    @EJB
    private ArchivoFacadeLocal facArchivo;
    @EJB
    private DPAFacadeLocal  facDpa;
    private FiltroConsultaBasica filtro = new FiltroConsultaBasica();
    private List<Registro> lista;
    private List<Integer> anos;
    private List<String> meses;
    private Integer ano;
    private String mes;
    private String boletaCensal;
    private String codEstablecimiento;
    private String estado;
    private String origen;
    protected Integer periodoCreacion;
    private MapModel modelTodos;
    private String centroTodos;
    private Marker marker;
    private EstadoInstrumento[] estados;
    private FuenteDigitacion[] origenes;
    private List<Adjunto> listAdjuntos;
    private Long idInstrumentoSeleccionado;
    private InstrumentoDig instrumentoSeleccionado;
    private String mensajeNovedad;
    private String mensajeNuevo;
    private RespuestaDig respuestaSeleccionada;
    private boolean seleccionarTodos;
    private String strObservacion;
    private String strModulo;
    private List<Observacion> observaciones = new ArrayList<Observacion>();
    private List<Documento> documentos;
    private ManejadorErrores errores;
    @ManagedProperty(value = "#{gestionVariable}")
    private GentionVariablesBean gestionVariable;
    @ManagedProperty(value = "#{usuarioSesion}")
    private UsuarioSesionBean usuario;
    private RespuestaDig respuestaDig;
    private Adjunto adjuntoSeleccionado;
    private Long idModulo;
    private Long idObservacion;
    private Elemento elementoSeleccionado;
    private List<InstrumentoDig> instrumentosVersiones = new ArrayList<InstrumentoDig>();
    private List<InstrumentoDig> instrumentosSeleccionados;
    private Long consultaSeleccionado;
    private List<Adjunto> adjuntos;
    private String mensajeTablaVacia = "";
    private Map<Long, String> listaNivelesConf;
    private int maximoNivelDPA;
    private int minimoNivelDPA;
    private List<Nivel> listaNivel1;
    private List<Nivel> listaNivel2;
    private List<Nivel> listaNivel3;
    private List<Nivel> listaNivel4;
    private List<Nivel> listaNivel5;
    private Nivel seleccionNivel0;
    private Nivel seleccionNivel1;
    private Nivel seleccionNivel2;
    private Nivel seleccionNivel3;
    private Nivel seleccionNivel4;
    private Nivel seleccionNivel5;
    private boolean swNivel1;
    private boolean swNivel2;
    private boolean swNivel3;
    private boolean swNivel4;
    private boolean swNivel5;
    private Nivel nivelSeleccionado;
    private String nombreNivelInicial;
    private String nombreDPAInicial;
    private String nombreUltimoDPA;
    private String path;
    private String contentType;
    private boolean renderRespNoEmitidas;
    private List<String> archivosCargados;
    private List<UploadedFile> adjuntosCargados;
    private String boletaCensalAuditoria;

    @PostConstruct
    public void inicializar() {
        try {
            this.cargarAnosMeses();
            errores = new ManejadorErrores();
            ano = new Integer((UtilFecha.obtenerPeriodoActual() + "").substring(0, 4));
            mes = (UtilFecha.obtenerPeriodoActual() + "").substring(4, 6);
            periodoCreacion = UtilFecha.obtenerPeriodoActual();
            this.inicializaBase();
            lista = new ArrayList<Registro>();
            estados = EstadoInstrumento.values();
            origenes = FuenteDigitacion.values();
            listAdjuntos = new ArrayList<Adjunto>();
            documentos = new ArrayList<Documento>();
            instrumentosSeleccionados = new ArrayList<InstrumentoDig>();
            adjuntos = new ArrayList<Adjunto>();
            adjuntosCargados = new ArrayList<UploadedFile>();
        } catch (Exception e) {
            ManejadorErrores.ingresarExcepcionEnLog(e, ConsultaInstrumentoBean.class);
        }
    }

    public void cargarConfiguracionDPA() {
        listaNivelesConf = new HashMap<Long, String>();
        // niveles DPA
        Usuario u = usuario.getUsuario();
        List<ConfiguracionNivel> configuraciones;
        if (UtilCadena.isNullOVacio(u.getNivelDpa())) {
            configuraciones = facEstDPA.consultarConfNivelesActual();
        } else {
            configuraciones = facEstDPA.consultarConfNivelesActual(u.getNivelDpa().getNivel());
        }

        //System.out.println(usuarioSesion.getUsuario().getIdioma().getId());
        for (ConfNivelDPA conf : configuraciones) {
            String descripcion = facEstDPA.consultarDescripcionNivelDPA(conf.getNivel(), usuario.getUsuario().getIdioma().getId());
            descripcion = descripcion != null ? descripcion : conf.getDescripcion();
            this.listaNivelesConf.put(conf.getNivel(), descripcion);
        }
        // maximo nivel DPA
        if (!configuraciones.isEmpty()) {
            int indice = configuraciones.size() - 1;
            this.maximoNivelDPA = configuraciones.get(indice).getNivel().intValue();
            this.minimoNivelDPA = configuraciones.get(0).getNivel().intValue();
            this.nombreUltimoDPA = configuraciones.get(indice).getDescripcion();
        } else {
            this.maximoNivelDPA = -1;
            this.minimoNivelDPA = -1;
        }
    }

    public void activarNiveles(int maximoNivelUsuario) {
        swNivel1 = false;
        swNivel2 = false;
        swNivel3 = false;
        swNivel4 = false;
        swNivel5 = false;
        maximoNivelUsuario++;
        while (maximoNivelUsuario <= maximoNivelDPA) {
            switch (maximoNivelUsuario) {
                case 1:
                    swNivel1 = true;
                    break;
                case 2:
                    swNivel2 = true;
                    break;
                case 3:
                    swNivel3 = true;
                    break;
                case 4:
                    swNivel4 = true;
                    break;
                case 5:
                    swNivel5 = true;
                    break;
            }
            maximoNivelUsuario++;
        }

    }

    public void nivelDPAOfAccesoUsuario() {
        Nivel n = usuario.getUsuario().getNivelEspecifico() != null
                ? usuario.getUsuario().getNivelEspecifico()
                : null;
        if (n != null) {
            this.nombreNivelInicial = n.getConfiguracion().getDescripcion();
            this.nombreDPAInicial = n.getDescripcion();
            int nivel = n.getConfiguracion().getNivel().intValue();
            switch (nivel) {
                case 0:
                    cargarNivel0();
                    break;
                case 1:
                    seleccionNivel1 = n;
                    cambioNivel1();
                    break;
                case 2:
                    seleccionNivel2 = n;
                    cambioNivel2();
                    break;
                case 3:
                    seleccionNivel3 = n;
                    cambioNivel3();
                    break;
                case 4:
                    seleccionNivel4 = n;
                    cambioNivel4();
                    break;
                case 5:
                    seleccionNivel5 = n;
                    cambioNivel5();
                    break;
            }
            activarNiveles(nivel);
        } else {
            cargarNivel0();
            activarNiveles(0);
        }
    }

    public void viewMap() {
        try {
            Map<String, Object> options = new HashMap<String, Object>();
            options.put("modal", true);
            options.put("width", 640);
            options.put("height", 340);
            options.put("contentWidth", "100%");
            options.put("contentHeight", "100%");
            options.put("headerElement", "customheader");
            RequestContext.getCurrentInstance().openDialog("/consultas/basico/mapas/mapView", options, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Nivel> cargarNivel(Nivel nivelSeccionado, ParamNivelConsul nivelConsulta) {
        return facEstDPA.consultarNiveles(nivelConsulta, nivelSeccionado.getId());
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

    public void resetearNiveles() {
        this.resetearNivel1();
        this.resetearNivel2();
        this.resetearNivel3();
        this.resetearNivel4();
        this.resetearNivel5();
    }

    public void cargarNivel0() {
        String codigoPais = currentLogin.getPais().getCodigoPais();
        seleccionNivel0 = facEstDPA.consultarNivel(codigoPais);
        this.nombreNivelInicial = seleccionNivel0.getConfiguracion().getDescripcion();
        this.nombreDPAInicial = seleccionNivel0.getDescripcion();
        this.cargarNivel1();
    }

    public void cargarNivel1() {
        this.resetearNivel1();
        this.resetearNivel2();
        this.resetearNivel3();
        this.resetearNivel4();
        this.resetearNivel5();
        if (seleccionNivel0 != null) {
            listaNivel1 = this.cargarNivel(seleccionNivel0, ParamNivelConsul.UNO);
        }
    }

    public void cambioNivel1() {
        this.resetearNivel2();
        this.resetearNivel3();
        this.resetearNivel4();
        this.resetearNivel5();
        if (seleccionNivel1 != null) {
            nivelSeleccionado = seleccionNivel1;
            listaNivel2 = this.cargarNivel(seleccionNivel1, ParamNivelConsul.DOS);
        } else {
            nivelSeleccionado = null;
        }
    }

    public void cambioNivel2() {
        this.resetearNivel3();
        this.resetearNivel4();
        this.resetearNivel5();
        if (seleccionNivel2 != null) {
            nivelSeleccionado = seleccionNivel2;
            listaNivel3 = this.cargarNivel(seleccionNivel2, ParamNivelConsul.TRES);
        } else {
            nivelSeleccionado = seleccionNivel1;
        }
    }

    public void cambioNivel3() {
        this.resetearNivel4();
        this.resetearNivel5();
        if (seleccionNivel3 != null) {
            nivelSeleccionado = seleccionNivel3;
            listaNivel4 = this.cargarNivel(seleccionNivel3, ParamNivelConsul.CUATRO);
        } else {
            nivelSeleccionado = seleccionNivel2;
        }
    }

    public void cambioNivel4() {
        this.resetearNivel5();
        if (seleccionNivel4 != null) {
            nivelSeleccionado = seleccionNivel4;
            listaNivel5 = this.cargarNivel(seleccionNivel4, ParamNivelConsul.CINCO);
        } else {
            nivelSeleccionado = seleccionNivel3;
        }
    }

    public void cambioNivel5() {
        if (seleccionNivel5 != null) {
            nivelSeleccionado = seleccionNivel5;
        } else {
            nivelSeleccionado = seleccionNivel4;
        }
    }

    public List<Nivel> getListaNivel1() {
        return listaNivel1;
    }

    public List<Nivel> getListaNivel2() {
        return listaNivel2;
    }

    public List<Nivel> getListaNivel3() {
        return listaNivel3;
    }

    public List<Nivel> getListaNivel4() {
        return listaNivel4;
    }

    public List<Nivel> getListaNivel5() {
        return listaNivel5;
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

    public boolean isSwNivel1() {
        return swNivel1;
    }

    public boolean isSwNivel2() {
        return swNivel2;
    }

    public boolean isSwNivel3() {
        return swNivel3;
    }

    public boolean isSwNivel4() {
        return swNivel4;
    }

    public boolean isSwNivel5() {
        return swNivel5;
    }

    public String getMensajeTablaVacia() {
        return mensajeTablaVacia;
    }

    public void setMensajeTablaVacia(String mensajeTablaVacia) {
        this.mensajeTablaVacia = mensajeTablaVacia;
    }

    public Map<Long, String> getListaNivelesConf() {
        return listaNivelesConf;
    }

    public Nivel getNivelSeleccionado() {
        return nivelSeleccionado;
    }

    public String getNombreNivelInicial() {
        return nombreNivelInicial;
    }

    public String getNombreDPAInicial() {
        return nombreDPAInicial;
    }

    public LoginBean getCurrentLogin() {
        return currentLogin;
    }

    public void setCurrentLogin(LoginBean currentLogin) {
        this.currentLogin = currentLogin;
    }

    public int getMinimoNivelDPA() {
        return minimoNivelDPA;
    }

    public void cargarAnosMeses() {
        anos = new ArrayList<Integer>();
        List<Integer> listaPeriodosCreacion = facBasica.consultaPeriodosCreacion();
        for (Integer periodoCrea : listaPeriodosCreacion) {
            if (!anos.contains(new Integer((periodoCrea + "").substring(0, 4)))) {
                anos.add(new Integer((periodoCrea + "").substring(0, 4)));
            }
        }
        meses = new ArrayList<String>();
        int i = 0;
        while (i < 12) {
            i++;
            if (i < 10) {
                meses.add("0" + i);
            } else {
                meses.add("" + i);
            }
        }
    }

    public void inicializaBase() {
        try {
            resetearNiveles();
            cargarConfiguracionDPA();
            nivelDPAOfAccesoUsuario();
        } catch (Exception e) {
            ManejadorErrores.ingresarExcepcionEnLog(e, ConsultaInstrumentoBean.class);
        }
        //Si el periodoCreacion es posterior a los históricos se cargan las DPA actuales
        //Sino se cargan las del periodo igual o anterior al periodoCreacion
        /*periodo = facBasica.consultaPeriodoHis(periodoCreacion);
         if (periodo == null) {
         super.initActual();
         } else {
         super.initHistoricos(periodo);
         }*/
        //super.initActual();
    }

    public void cambioPeriodoCreacion() {
        ano = ano != null ? ano : anos.get(0);
        mes = mes != null ? mes : meses.get(0);
        periodoCreacion = new Integer(ano + "" + mes);
        this.inicializaBase();
    }

    public void resetearFiltros() {
        try {
            this.renderRespNoEmitidas = false;
            this.resetearNiveles();
            this.cargarNivel1();
            this.lista = new ArrayList<Registro>();
            periodoCreacion = new Integer(anos.get(0) + "" + meses.get(0));
            ano = new Integer((UtilFecha.obtenerPeriodoActual() + "").substring(0, 4));
            mes = (UtilFecha.obtenerPeriodoActual() + "").substring(4, 6);
            boletaCensal = null;
            codEstablecimiento = null;
            modelTodos = null;
        } catch (Exception e) {
            ManejadorErrores.ingresarExcepcionEnLog(e, ConsultaInstrumentoBean.class);
        }
    }

    public List<Registro> getLista() {
        return lista;
    }

    public void consultar() {
        try {
            filtro.init();
            DecimalFormat df = new DecimalFormat("#.##");
            //"periodo" en este caso se refiere al periodo de creación.
            //En la búsqueda se verán representados todos los instrumentos vigentes en ese momento.

            filtro.setPeriodoCreacion(new Integer(ano + "" + mes));
            filtro.setBoletaCensal(boletaCensal != null ? boletaCensal : null);
            filtro.setEstado(estado != null ? estado : null);
            filtro.setOrigen(origen != null ? origen: null);
            filtro.setIdNivel0(seleccionNivel0 != null ? seleccionNivel0.getId() : null);
            filtro.setIdNivel1(seleccionNivel1 != null ? seleccionNivel1.getId() : null);
            filtro.setIdNivel2(seleccionNivel2 != null ? seleccionNivel2.getId() : null);
            filtro.setIdNivel3(seleccionNivel3 != null ? seleccionNivel3.getId() : null);
            filtro.setIdNivel4(seleccionNivel4 != null ? seleccionNivel4.getId() : null);
            filtro.setIdNivel5(seleccionNivel5 != null ? seleccionNivel5.getId() : null);

            filtro.setCodEstablecimiento(codEstablecimiento != null ? codEstablecimiento : null);

            List<Object[]> resultados = facBasica.consultarInstrumentos(filtro,this.maximoNivelDPA);
            if (resultados.size() < 1) {
                mensajeTablaVacia = Utilidades.obtenerMensaje("aplicacion.general.noExistenRegistros");
            }

            lista = new ArrayList<Registro>(resultados.size());
            modelTodos = null;
            Registro registro = null;
            for (Object[] fila : resultados) {
                registro = new Registro();
                int i = 0;
                registro.setIdInstrumento((String) fila[i++]);
                registro.setIdDigInstrumento(new Long(fila[i++] + ""));
                registro.setCodigoEstablecimiento((String) fila[i++]);
                registro.setNombreEstablecimiento((String) fila[i++]);
                registro.setCodigoSede((String) fila[i++]);
                registro.setNombreSede((String) fila[i++]);
                registro.setCodigoPredio((String) fila[i++]);
                registro.setNombrePredio((String) fila[i++]);
                registro.setEstado((String) fila[i++]);
                registro.setUsuarioActualizacion((String) fila[i++]);
                registro.setFechaActualizacion((Date) fila[i++]);
                String gradosLat = (String) fila[i++];
                String minutosLat = (String) fila[i++];
                String segundosLat = (String) fila[i++];
                String yLat = (String) fila[i++];
                String gradosLon = (String) fila[i++];
                String minutosLon = (String) fila[i++];
                String segundosLon = (String) fila[i++];
                String xLon = (String) fila[i++];
                String coorX = (String) fila[i++];
                String coorY = (String) fila[i++];
                String zona = (String) fila[i++];
                String zonaLat = (String) fila[i++];
                registro.setBoleta((String) fila[i++]);
                registro.setVersion(new Long(fila[i++] + ""));
                registro.setNivelDPA((String) fila[i++]);
                registro.setFuente( new Long(fila[i++] + "") == 0?"aplicacion.fuente.digitacion.digitacion":"aplicacion.fuente.digitacion.migracion");

                //POR GRADOS, MINUTOS Y SEGUNDOS
                if (gradosLat != null && !("").equals(gradosLat) && minutosLat != null && !("").equals(minutosLat)
                        && segundosLat != null && !("").equals(segundosLat) && yLat != null && !("").equals(yLat)
                        && gradosLon != null && !("").equals(gradosLon) && minutosLon != null && !("").equals(minutosLon)
                        && segundosLon != null && !("").equals(segundosLon) && xLon != null && !("").equals(xLon)) {

                    try{
                        registro.setLatitud(UtilMapas.convertirGraMinSecADecimal(new Double(gradosLat), new Double(minutosLat), new Double(segundosLat), yLat));
                        registro.setLongitud(UtilMapas.convertirGraMinSecADecimal(new Double(gradosLon), new Double(minutosLon), new Double(segundosLon), xLon));
                    }catch(Exception e){
                        registro.setLatitud(0D);
                        registro.setLongitud(0D);
                    }

                    //Se contruye la latitud y longitud que van a mostrar en las columnas.
                    try {
                        gradosLat = df.format(Double.valueOf(gradosLat));
                        minutosLat = df.format(Double.valueOf(minutosLat));
                        segundosLat = df.format(Double.valueOf(segundosLat));
                        gradosLon = df.format(Double.valueOf(gradosLon));
                        minutosLon = df.format(Double.valueOf(minutosLon));
                        segundosLon = df.format(Double.valueOf(segundosLon));

                        registro.setLatitudColumnas(gradosLat+"-"+minutosLat+"-"+segundosLat+"-"+yLat);
                        registro.setLongitudColumnas(gradosLon+"-"+minutosLon+"-"+segundosLon+"-"+xLon);

                    }catch(Exception e){
                        gradosLat = "";
                        minutosLat = "";
                        segundosLat = "";
                        gradosLon = "";
                        minutosLon = "";
                        segundosLon = "";
                    }



                } else if (coorX != null && !("").equals(coorX) && coorY != null && !("").equals(coorY)
                        && zona != null && !("").equals(zona) && zonaLat != null && !("").equals(zonaLat)) {
                    //POR UTM
                    //double[] latlon = UtilMapas.convertUTMToLatLong(new Integer(18), "P", new Double(523063.582), new Double(1214636.110));
                    double[] latlon = UtilMapas.convertUTMToLatLong(new Integer(zona), zonaLat, new Double(coorX), new Double(coorY));
                    registro.setLatitud(latlon[0]);
                    registro.setLongitud(latlon[1]);

                    registro.setLatitudColumnas("-");
                    registro.setLongitudColumnas("-");
                }

                if (registro.getLatitud() != null) {
                    if (modelTodos == null) {
                        modelTodos = new DefaultMapModel();
                    }
                    LatLng coord1 = new LatLng(registro.getLatitud(), registro.getLongitud());
                    modelTodos.addOverlay(new Marker(coord1, registro.getNombrePredio()));
                }

                lista.add(registro);
            }
        } catch (Exception e) {
            ManejadorErrores.ingresarExcepcionEnLog(e, ConsultaInstrumentoBean.class);
        }
    }

    public void imprimirPdf(Long idDigInstrumento, String codInstrumento) {
        List<ModuloIns> modulos = null;
        List<String> respuestas = new ArrayList<String>();
        Map<String, Object> parametros = new HashMap<String, Object>();
        HashMap<String, String> respuestaTipologia = new HashMap<String, String>();
        String[] respuestasValoresTipologia = {"RESP_040", "RESP_046_01", "RESP_276_01", "RESP_278_01", "RESP_281_01", "RESP_286_01", "RESP_288_01"};
        List<String> respuestasValoresTipologiaList = new ArrayList<String>();
        Collections.addAll(respuestasValoresTipologiaList, respuestasValoresTipologia);

        List<String> respuestasEdificios = new ArrayList<String>();
        List<String> respuestasEspaciosEscolares = new ArrayList<String>();
        List<String> respuestasCroquis = new ArrayList<String>();
        List<String> respuestasAmbMismaTipo = new ArrayList<String>();
        List<String> respuestasObservaciones = new ArrayList<String>();
        List<String> respuestasDatosAdic = new ArrayList<String>();
        List<String> respuestasMantenimiento = new ArrayList<String>();
        List<String> respuestasCondEstruc = new ArrayList<String>();
        try {
            Instrumento instrumento = new Instrumento(codInstrumento);
            modulos = facInstrumentos.obtenerModulosInstrumento(instrumento);
            for (ModuloIns modulo : modulos) {
                parametros.put(modulo.getCodigo(), Utilidades.obtenerMensaje(modulo.getEtiqueta()));
                for (Seccion seccion : modulo.getSeccionList()) {
                    parametros.put(seccion.getCodigo(), Utilidades.obtenerMensaje(seccion.getEtiqueta()));

                    for (Pregunta pregunta : seccion.getPreguntaList()) {

                        for (Respuesta respuesta : pregunta.getRespuestaList()) {
                            //La sección 8 (SEC_RIGEP) corresponde a edificios(subreport) menos las RESP_092 y RESP_093
                            if (pregunta.getSeccion().getCodigo().equals("SEC_RIGEP")
                                    && !"RESP_092".equals(respuesta.getCodigo())
                                    && !"RESP_093".equals(respuesta.getCodigo())) {
                                respuestasEdificios.add(respuesta.getCodigo());
                            } else if (pregunta.getSeccion().getCodigo().equals("SEC_IEE")) {
                                respuestasEspaciosEscolares.add(respuesta.getCodigo());
                            } else if (pregunta.getSeccion().getCodigo().equals("SEC_CEEE")) {
                                respuestasCroquis.add(respuesta.getCodigo());
                            } else if (pregunta.getSeccion().getCodigo().equals("SEC_RAECTMI")) {
                                respuestasAmbMismaTipo.add(respuesta.getCodigo());
                            } else if (pregunta.getSeccion().getCodigo().equals("SEC_OCLI")) {
                                respuestasObservaciones.add(respuesta.getCodigo());
                            } else if (pregunta.getSeccion().getCodigo().equals("SEC_DEAFMLSLA")
                                    && !"RESP_191_01".equals(respuesta.getCodigo()) && !"RESP_191_02".equals(respuesta.getCodigo())
                                    && !"RESP_192".equals(respuesta.getCodigo())
                                    && !"RESP_207_01".equals(respuesta.getCodigo()) && !"RESP_207_02".equals(respuesta.getCodigo()) && !"RESP_207_03".equals(respuesta.getCodigo())
                                    && !"RESP_207_04".equals(respuesta.getCodigo()) && !"RESP_207_05".equals(respuesta.getCodigo()) && !"RESP_207_06".equals(respuesta.getCodigo())
                                    && !"RESP_207_07".equals(respuesta.getCodigo()) && !"RESP_207_08".equals(respuesta.getCodigo()) && !"RESP_207_09".equals(respuesta.getCodigo())
                                    && !"RESP_207_10".equals(respuesta.getCodigo()) && !"RESP_207_11".equals(respuesta.getCodigo()) && !"RESP_207_10".equals(respuesta.getCodigo())) {
                                respuestasDatosAdic.add(respuesta.getCodigo());
                            } else if (pregunta.getSeccion().getCodigo().equals("SEC_MCE")
                                    && !"RESP_238_01".equals(respuesta.getCodigo()) && !"RESP_238_02".equals(respuesta.getCodigo())
                                    && !"RESP_239_01".equals(respuesta.getCodigo()) && !"RESP_239_02".equals(respuesta.getCodigo())
                                    && !"RESP_240_01".equals(respuesta.getCodigo()) && !"RESP_240_02".equals(respuesta.getCodigo())) {
                                respuestasMantenimiento.add(respuesta.getCodigo());
                            } else if (pregunta.getSeccion().getCodigo().equals("SEC_CEEP")) {
                                respuestasCondEstruc.add(respuesta.getCodigo());
                            } else {
                                respuestas.add(respuesta.getCodigo());
                            }
                            //Esta respuesta es una escepción, porque es una tipología y guarda el código y el valor no
                            if (respuestasValoresTipologiaList.contains(respuesta.getCodigo()) && respuesta.getTipologia() != null) {
                                respuestaTipologia.put(respuesta.getCodigo(), respuesta.getTipologia().getCodigo());
                            }
                        }
                    }

                }
            }



            parametros = obtenerRespuestasDigitadas(parametros, idDigInstrumento, respuestas);

            //Cambiamos de No verificable a Verificable
            if (!parametros.containsKey("RESP_055_02")) {
                parametros.put("RESP_055_02", "1");
            } else if ("0".equals(parametros.get("RESP_055_02"))) {
                parametros.put("RESP_055_02", "1");
            } else if ("1".equals(parametros.get("RESP_055_02"))) {
                parametros.put("RESP_055_02", "0");
            }
            //Cambiamos los casos excepcionales en los que hay que cambar el valor de bbdd porque es un código
            for (String resp : respuestasValoresTipologia) {
                if (parametros.containsKey(resp)) {
                    if (parametros.get(resp) != null) {
                        TipologiaValorNombre tv = facParam.consultarValorTipologia(respuestaTipologia.get(resp), parametros.get(resp).toString(), getUsuario().getUsuario().getIdioma().getId());
                        parametros.put(resp, tv != null ? tv.getNombre() : "");
                    }
                }
            }
            //Los casos Si o No
            String[] respuestasSiNo = {"RESP_022", "RESP_041", "RESP_042", "RESP_044_03", "RESP_045_12", "RESP_046_02", "RESP_047_08", "RESP_048_08", "RESP_050_08", "RESP_049", "RESP_051",
                "RESP_052_13", "RESP_054_01", "RESP_054_06", "RESP_055_01", "RESP_055_02", "RESP_056_03", "RESP_058", "RESP_060", "RESP_070_02", "RESP_072_02", "RESP_061_01", "RESP_062_01",
                "RESP_063_01", "RESP_064_01", "RESP_065_01", "RESP_066_01", "RESP_067_01", "RESP_068_01", "RESP_069_01", "RESP_208", "RESP_219", "RESP_220", "RESP_225", "RESP_226", "RESP_227",
                "RESP_238_01", "RESP_239_01", "RESP_240_01", "RESP_276_03", "RESP_279_02", "RESP_280_02", "RESP_285_01", "RESP_286_02", "RESP_099_01"};
            for (String resp : respuestasSiNo) {
                parametros.put(resp, transformaSiNoNA(parametros.get(resp) + ""));
            }
            //Los casos No verificable
            String[] respuestasNoVerificable = {"RESP_238_02", "RESP_239_02", "RESP_240_02"};
            for (String resp : respuestasNoVerificable) {
                parametros.put(resp, transformaNoVerificable(parametros.get(resp) + ""));
            }

            //Los adjuntos
            String[] respAdjuntos = {"RESP_070_03", "RESP_072_03"};
            for (String respAdj : respAdjuntos) {
                if (parametros.containsKey(respAdj)) {
                    List<String> resultados = facBasica.consultaRutaAdjunto(idDigInstrumento, respAdj);
                    String ruta = resultados != null && !resultados.isEmpty() ? resultados.get(0) : "";
                    parametros.put(respAdj, ruta);
                }
            }
            //Edificios
            List<GrupoEdificios> listaEdificios = obtenerEdificios(idDigInstrumento, respuestasEdificios);

            //Espacios Escolares
            List<EspaciosEscolares> listaEspaciosEscolares = obtenerEspaciosEscolares(idDigInstrumento, respuestasEspaciosEscolares);

            //Croquis
            List<Croquis> listaCroquis = obtenerCroquis(idDigInstrumento, respuestasCroquis);

            //Ambientes misma tipografia
            List<AmbMismaTipo> listaAmbMismaTipo = obtenerAmbMismaTipo(idDigInstrumento, respuestasAmbMismaTipo);

            //Observaciones
            List<Observaciones> listaObservaciones = obtenerObservaciones(idDigInstrumento, respuestasObservaciones);

            //Datos adicionales
            List<DatosAdic> listaDatosAdic = obtenerDatosAdic(idDigInstrumento, respuestasDatosAdic);

            //Mantenimiento
            List<Mantenimiento> listaMantenimiento = obtenerMantenimiento(idDigInstrumento, respuestasMantenimiento);

            //Condiciones Estructurales
            List<CondEstruc> listaCondEstruc = obtenerCondEstruc(idDigInstrumento, respuestasCondEstruc);

            ReporteInstrumento reporteInstrumento = new ReporteInstrumento();
            reporteInstrumento.setListaEdificios(listaEdificios);
            reporteInstrumento.setListaEspaciosEscolares(listaEspaciosEscolares);
            reporteInstrumento.setListaCroquis(listaCroquis);
            reporteInstrumento.setListaAmbMismaTipo(listaAmbMismaTipo);
            reporteInstrumento.setListaObservaciones(listaObservaciones);
            reporteInstrumento.setListaDatosAdic(listaDatosAdic);
            reporteInstrumento.setListaMantenimiento(listaMantenimiento);
            reporteInstrumento.setListaCondEstruc(listaCondEstruc);

            List list = new ArrayList();
            list.add(reporteInstrumento);

            parametros.put(JRParameter.REPORT_RESOURCE_BUNDLE, ResourceBundle.getBundle("etiquetas", FacesContext.getCurrentInstance().getViewRoot().getLocale()));

            UtilExportar.exportarDocumentoPDF("instrumento", "Instrumento", "Instrumento", parametros, list);
        } catch (Exception e) {
            //UtilOut.println("ERROR en imprimirPdf: " + e.getMessage());
            mostrarMensajeError(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"), e.getMessage());
            ManejadorErrores.ingresarExcepcionEnLog(e, ConsultaInstrumentoBean.class);
            //UtilOut.println(e);
        }
    }

     public void imprimirPdfTipologias(Long idDigInstrumento, String codInstrumento) {
        List<ModuloIns> modulos = null;
        List<String> respuestas = new ArrayList<String>();
        Map<String, Object> parametros = new HashMap<String, Object>();
        HashMap<String, String> respuestaTipologia = new HashMap<String, String>();
        String[] respuestasValoresTipologia = {"RESP_040", "RESP_046_01"};
        String[] respuestasValoresTipologiaDescripcion  = {"RESP_012_01","RESP_013","RESP_029_01","RESP_030_01"
                ,"RESP_032_01","RESP_032_02","RESP_032_03","RESP_032_04","RESP_032_05","RESP_032_06","RESP_032_07","RESP_032_08","RESP_032_09","RESP_032_10"
                ,"RESP_033_01","RESP_033_02","RESP_033_03","RESP_033_04","RESP_033_05","RESP_033_06","RESP_033_07","RESP_033_08","RESP_033_09","RESP_033_10"
                ,"RESP_036_01","RESP_036_02","RESP_036_03","RESP_036_04","RESP_036_05","RESP_036_06","RESP_036_07","RESP_036_08","RESP_036_09","RESP_036_10"
                ,"RESP_044_01","RESP_045_01","RESP_045_03","RESP_045_05","RESP_045_07","RESP_045_09"
                ,"RESP_047_01","RESP_047_02","RESP_047_03","RESP_047_04","RESP_047_05","RESP_047_06","RESP_048_01","RESP_048_03","RESP_048_05"
                ,"RESP_050_01","RESP_050_03","RESP_050_05"
                ,"RESP_052_01","RESP_052_03","RESP_052_05","RESP_052_07","RESP_052_09","RESP_054_02","RESP_056_01"
                ,"RESP_061_02","RESP_062_02","RESP_063_02","RESP_064_02","RESP_066_02","RESP_067_02","RESP_068_02","RESP_069_02"
                ,"RESP_061_03","RESP_062_03","RESP_063_03","RESP_064_03","RESP_066_03","RESP_067_03","RESP_068_03","RESP_069_03"
                ,"RESP_276_01","RESP_278_01","RESP_281_01","RESP_286_01","RESP_288_01"

        };
        String[] respuestasTiposdeEspacio = {
            "RESP_210","RESP_212","RESP_214","RESP_216","RESP_218","RESP_210",
            "RESP_222","RESP_224","RESP_229","RESP_231","RESP_233","RESP_235","RESP_237"
        };
        String[] respuestasValoresDPA = {"RESP_031_01","RESP_031_02","RESP_031_03","RESP_031_04","RESP_031_05","RESP_031_06"};
        List<String> respuestasValoresTipologiaList = new ArrayList<String>();
        Collections.addAll(respuestasValoresTipologiaList, respuestasValoresTipologia);
        Collections.addAll(respuestasValoresTipologiaList, respuestasValoresTipologiaDescripcion);

        List<String> respuestasEdificios = new ArrayList<String>();
        List<String> respuestasEspaciosEscolares = new ArrayList<String>();
        List<String> respuestasCroquis = new ArrayList<String>();
        List<String> respuestasAmbMismaTipo = new ArrayList<String>();
        List<String> respuestasObservaciones = new ArrayList<String>();
        List<String> respuestasDatosAdic = new ArrayList<String>();
        List<String> respuestasMantenimiento = new ArrayList<String>();
        List<String> respuestasCondEstruc = new ArrayList<String>();
        try {
            Instrumento instrumento = new Instrumento(codInstrumento);
            modulos = facInstrumentos.obtenerModulosInstrumento(instrumento);
            for (ModuloIns modulo : modulos) {
                parametros.put(modulo.getCodigo(), Utilidades.obtenerMensaje(modulo.getEtiqueta()));
                for (Seccion seccion : modulo.getSeccionList()) {
                    parametros.put(seccion.getCodigo(), Utilidades.obtenerMensaje(seccion.getEtiqueta()));

                    for (Pregunta pregunta : seccion.getPreguntaList()) {

                        for (Respuesta respuesta : pregunta.getRespuestaList()) {
                            //La sección 8 (SEC_RIGEP) corresponde a edificios(subreport) menos las RESP_092 y RESP_093
                            if (pregunta.getSeccion().getCodigo().equals("SEC_RIGEP")
                                    && !"RESP_092".equals(respuesta.getCodigo())
                                    && !"RESP_093".equals(respuesta.getCodigo())) {
                                respuestasEdificios.add(respuesta.getCodigo());
                            } else if (pregunta.getSeccion().getCodigo().equals("SEC_IEE")) {
                                respuestasEspaciosEscolares.add(respuesta.getCodigo());
                            } else if (pregunta.getSeccion().getCodigo().equals("SEC_CEEE")) {
                                respuestasCroquis.add(respuesta.getCodigo());
                            } else if (pregunta.getSeccion().getCodigo().equals("SEC_RAECTMI")) {
                                respuestasAmbMismaTipo.add(respuesta.getCodigo());
                            } else if (pregunta.getSeccion().getCodigo().equals("SEC_OCLI")) {
                                respuestasObservaciones.add(respuesta.getCodigo());
                            } else if (pregunta.getSeccion().getCodigo().equals("SEC_DEAFMLSLA")
                                    && !"RESP_191_01".equals(respuesta.getCodigo()) && !"RESP_191_02".equals(respuesta.getCodigo())
                                    && !"RESP_192".equals(respuesta.getCodigo())
                                    && !"RESP_207_01".equals(respuesta.getCodigo()) && !"RESP_207_02".equals(respuesta.getCodigo()) && !"RESP_207_03".equals(respuesta.getCodigo())
                                    && !"RESP_207_04".equals(respuesta.getCodigo()) && !"RESP_207_05".equals(respuesta.getCodigo()) && !"RESP_207_06".equals(respuesta.getCodigo())
                                    && !"RESP_207_07".equals(respuesta.getCodigo()) && !"RESP_207_08".equals(respuesta.getCodigo()) && !"RESP_207_09".equals(respuesta.getCodigo())
                                    && !"RESP_207_10".equals(respuesta.getCodigo()) && !"RESP_207_11".equals(respuesta.getCodigo()) && !"RESP_207_10".equals(respuesta.getCodigo())) {
                                respuestasDatosAdic.add(respuesta.getCodigo());
                            } else if (pregunta.getSeccion().getCodigo().equals("SEC_MCE")
                                    && !"RESP_238_01".equals(respuesta.getCodigo()) && !"RESP_238_02".equals(respuesta.getCodigo())
                                    && !"RESP_239_01".equals(respuesta.getCodigo()) && !"RESP_239_02".equals(respuesta.getCodigo())
                                    && !"RESP_240_01".equals(respuesta.getCodigo()) && !"RESP_240_02".equals(respuesta.getCodigo())) {
                                respuestasMantenimiento.add(respuesta.getCodigo());
                            } else if (pregunta.getSeccion().getCodigo().equals("SEC_CEEP")) {
                                respuestasCondEstruc.add(respuesta.getCodigo());
                            } else {
                                respuestas.add(respuesta.getCodigo());
                            }
                            //Esta respuesta es una escepción, porque es una tipología y guarda el código y el valor no
                            if (respuestasValoresTipologiaList.contains(respuesta.getCodigo()) && respuesta.getTipologia() != null) {
                                respuestaTipologia.put(respuesta.getCodigo(), respuesta.getTipologia().getCodigo());
                            }
                        }
                    }

                }
            }

            parametros = obtenerRespuestasDigitadas(parametros, idDigInstrumento, respuestas);

            //Cambiamos de No verificable a Verificable
            if (!parametros.containsKey("RESP_055_02")) {
                parametros.put("RESP_055_02", "1");
            } else if ("0".equals(parametros.get("RESP_055_02"))) {
                parametros.put("RESP_055_02", "1");
            } else if ("1".equals(parametros.get("RESP_055_02"))) {
                parametros.put("RESP_055_02", "0");
            }
            //Cambiamos los casos excepcionales en los que hay que cambar el valor de bbdd porque es un código
            for (String resp : respuestasValoresTipologia) {
                if (parametros.containsKey(resp)) {
                    if (parametros.get(resp) != null) {
                        TipologiaValorNombre tv = facParam.consultarValorTipologia(respuestaTipologia.get(resp), parametros.get(resp).toString(), getUsuario().getUsuario().getIdioma().getId());
                        parametros.put(resp, tv != null ? tv.getNombre() : "");
                    }
                }
            }

            for (String resp : respuestasValoresTipologiaDescripcion) {
                if (parametros.containsKey(resp)) {
                    if (parametros.get(resp) != null) {
                        TipologiaValorNombre tv = facParam.consultarValorTipologia(respuestaTipologia.get(resp), parametros.get(resp).toString(), getUsuario().getUsuario().getIdioma().getId());
                        parametros.put(resp, tv != null ?  tv.getTipValor().getCodigo()+" - "+tv.getNombre(): "");
                    }
                }
            }


           for (String resp : respuestasTiposdeEspacio){
                if (parametros.containsKey(resp)) {
                    if (parametros.get(resp) != null) {
                        //Averiguo si es un numero en caso que sea lo busco en las tipologias de tipos de espacio.
                        if (parametros.get(resp).toString().matches("-?\\d+(\\.\\d+)?")){
                             TipologiaValorNombre tv = facParam.consultarValorTipologia("TEE", parametros.get(resp).toString(), getUsuario().getUsuario().getIdioma().getId());
                             parametros.put(resp, tv != null ? tv.getTipValor().getCodigo()+" - "+tv.getNombre(): parametros.get(resp).toString());
                        }
                    }
                }
            }

             for (String resp : respuestasValoresDPA) {
                if (parametros.containsKey(resp)) {
                    if (parametros.get(resp) != null) {
                        //Mejorar manejo de excepciones
                        Nivel  nv = facDpa.getNivelPorId( Long.parseLong(parametros.get(resp).toString()));
                        System.out.print(nv.getDescripcion());
                        parametros.put(resp, nv != null ?  nv.getId() +" - "+nv.getDescripcion():"");
                    }
                }
            }

            //Los casos Si o No
            String[] respuestasSiNo = {"RESP_022", "RESP_041", "RESP_042", "RESP_044_03", "RESP_045_12", "RESP_046_02", "RESP_047_08", "RESP_048_08", "RESP_050_08", "RESP_049", "RESP_051",
                "RESP_052_13" ,"RESP_053","RESP_054_01", "RESP_054_06", "RESP_055_01", "RESP_055_02", "RESP_056_03", "RESP_058", "RESP_060", "RESP_070_02", "RESP_072_02", "RESP_061_01", "RESP_062_01",
                "RESP_063_01", "RESP_064_01", "RESP_065_01", "RESP_066_01", "RESP_067_01", "RESP_068_01", "RESP_069_01", "RESP_208", "RESP_219", "RESP_220", "RESP_225", "RESP_226", "RESP_227",
                "RESP_238_01", "RESP_239_01", "RESP_240_01", "RESP_276_03", "RESP_279_02", "RESP_280_02", "RESP_285_01", "RESP_286_02", "RESP_099_01"};
            for (String resp : respuestasSiNo) {
                parametros.put(resp, transformaSiNoNA(parametros.get(resp) + ""));
            }
            //Los casos No verificable
            String[] respuestasNoVerificable = {"RESP_238_02", "RESP_239_02", "RESP_240_02"};
            for (String resp : respuestasNoVerificable) {
                parametros.put(resp, transformaNoVerificable(parametros.get(resp) + ""));
            }

            //Los adjuntos
            String[] respAdjuntos = {"RESP_070_03", "RESP_072_03"};
            for (String respAdj : respAdjuntos) {
                if (parametros.containsKey(respAdj)) {
                    List<String> resultados = facBasica.consultaRutaAdjunto(idDigInstrumento, respAdj);
                    String ruta = resultados != null && !resultados.isEmpty() ? resultados.get(0) : "";
                    parametros.put(respAdj, ruta);
                }
            }
            //Edificios
            List<GrupoEdificios> listaEdificios = obtenerEdificiosTipologias(idDigInstrumento, respuestasEdificios);

            //Espacios Escolares
            List<EspaciosEscolares> listaEspaciosEscolares = obtenerEspaciosEscolaresTipologias(idDigInstrumento, respuestasEspaciosEscolares);

            //Croquis
            List<Croquis> listaCroquis = obtenerCroquis(idDigInstrumento, respuestasCroquis);

            //Ambientes misma tipografia
            List<AmbMismaTipo> listaAmbMismaTipo = obtenerAmbMismaTipo(idDigInstrumento, respuestasAmbMismaTipo);

            //Observaciones
            List<Observaciones> listaObservaciones = obtenerObservaciones(idDigInstrumento, respuestasObservaciones);

            //Datos adicionales
            List<DatosAdic> listaDatosAdic = obtenerDatosAdicTipologias(idDigInstrumento, respuestasDatosAdic);

            //Mantenimiento
            List<Mantenimiento> listaMantenimiento = obtenerMantenimientoTipologias(idDigInstrumento, respuestasMantenimiento);

            //Condiciones Estructurales
            List<CondEstruc> listaCondEstruc = obtenerCondEstrucTipologias(idDigInstrumento, respuestasCondEstruc);

            ReporteInstrumento reporteInstrumento = new ReporteInstrumento();
            reporteInstrumento.setListaEdificios(listaEdificios);
            reporteInstrumento.setListaEspaciosEscolares(listaEspaciosEscolares);
            reporteInstrumento.setListaCroquis(listaCroquis);
            reporteInstrumento.setListaAmbMismaTipo(listaAmbMismaTipo);
            reporteInstrumento.setListaObservaciones(listaObservaciones);
            reporteInstrumento.setListaDatosAdic(listaDatosAdic);
            reporteInstrumento.setListaMantenimiento(listaMantenimiento);
            reporteInstrumento.setListaCondEstruc(listaCondEstruc);

            List list = new ArrayList();
            list.add(reporteInstrumento);

            parametros.put(JRParameter.REPORT_RESOURCE_BUNDLE, ResourceBundle.getBundle("etiquetas", FacesContext.getCurrentInstance().getViewRoot().getLocale()));

            UtilExportar.exportarDocumentoPDF("instrumentoTipologias", "Instrumento", "InstrumentoDescripciones", parametros, list);
        } catch (Exception e) {
            //UtilOut.println("ERROR en imprimirPdf: " + e.getMessage());
            mostrarMensajeError(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"), e.getMessage());
            ManejadorErrores.ingresarExcepcionEnLog(e, ConsultaInstrumentoBean.class);
            //UtilOut.println(e);
        }
    }

    private void mostrarMensajeAdvertencia(String string, String obtenerMensaje) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public class ReporteInstrumento {

        List<GrupoEdificios> listaEdificios;
        List<EspaciosEscolares> listaEspaciosEscolares;
        List<Croquis> listaCroquis;
        List<AmbMismaTipo> listaAmbMismaTipo;
        List<Observaciones> listaObservaciones;
        List<DatosAdic> listaDatosAdic;
        List<Mantenimiento> listaMantenimiento;
        List<CondEstruc> listaCondEstruc;

        public List<CondEstruc> getListaCondEstruc() {
            return listaCondEstruc;
        }

        public void setListaCondEstruc(List<CondEstruc> listaCondEstruc) {
            this.listaCondEstruc = listaCondEstruc;
        }

        public List<Mantenimiento> getListaMantenimiento() {
            return listaMantenimiento;
        }

        public void setListaMantenimiento(List<Mantenimiento> listaMantenimiento) {
            this.listaMantenimiento = listaMantenimiento;
        }

        public List<DatosAdic> getListaDatosAdic() {
            return listaDatosAdic;
        }

        public void setListaDatosAdic(List<DatosAdic> listaDatosAdic) {
            this.listaDatosAdic = listaDatosAdic;
        }

        public List<Observaciones> getListaObservaciones() {
            return listaObservaciones;
        }

        public void setListaObservaciones(List<Observaciones> listaObservaciones) {
            this.listaObservaciones = listaObservaciones;
        }

        public List<GrupoEdificios> getListaEdificios() {
            return listaEdificios;
        }

        public void setListaEdificios(List<GrupoEdificios> listaEdificios) {
            this.listaEdificios = listaEdificios;
        }

        public List<EspaciosEscolares> getListaEspaciosEscolares() {
            return listaEspaciosEscolares;
        }

        public void setListaEspaciosEscolares(List<EspaciosEscolares> listaEspaciosEscolares) {
            this.listaEspaciosEscolares = listaEspaciosEscolares;
        }

        public List<Croquis> getListaCroquis() {
            return listaCroquis;
        }

        public void setListaCroquis(List<Croquis> listaCroquis) {
            this.listaCroquis = listaCroquis;
        }

        public List<AmbMismaTipo> getListaAmbMismaTipo() {
            return listaAmbMismaTipo;
        }

        public void setListaAmbMismaTipo(List<AmbMismaTipo> listaAmbMismaTipo) {
            this.listaAmbMismaTipo = listaAmbMismaTipo;
        }
    }

    public String transformaSiNoNA(String resp) {
        if ((SiNoNA.NA.getValor() + "").equals(resp)) {
            resp = Utilidades.obtenerMensaje("aplicacion.general.NA");
        } else if ((SiNoNA.Si.getValor() + "").equals(resp)) {
            resp = Utilidades.obtenerMensaje("aplicacion.general.si");
        } else {
            resp = Utilidades.obtenerMensaje("aplicacion.general.no");
        }
        return resp;
    }

    public String transformaNoVerificable(String resp) {
        if ((SiNoNA.Si.getValor() + "").equals(resp)) {
            resp = Utilidades.obtenerMensaje("aplicacion.general.noVerificable");
        } else {
            resp = "";
        }
        return resp;
    }

    public String tranformacionSuficiente(String resp) {
        if ((SuficienteInsuficiente.INSUF.getValor() + "").equals(resp)) {
            resp = Utilidades.obtenerMensaje("enum.suficienteInsuficiente.INSUF");
        } else {
            if ((SuficienteInsuficiente.SUF.getValor() + "").equals(resp)) {
                resp = Utilidades.obtenerMensaje("enum.suficienteInsuficiente.SUF");
            } else {
                resp = Utilidades.obtenerMensaje("enum.suficienteInsuficiente.NA");
            }
        }
        return resp;
    }

    public Map<String, Object> obtenerRespuestasDigitadas(Map<String, Object> respuestasMap, Long idDigInstrumento, List<String> respuestas) {
        List<Object[]> listaRespuesta;
        try {
            listaRespuesta = facBasica.obtenerRespuestasInstrumentoDig(idDigInstrumento, respuestas);

            for (Object[] fila : listaRespuesta) {
                int i = 0;
                respuestasMap.put((String) fila[i++], (String) fila[i++]);
            }

        } catch (Exception e) {
            ManejadorErrores.ingresarExcepcionEnLog(e, ConsultaInstrumentoBean.class);
        }
        return respuestasMap;
    }

    public List<GrupoEdificios> obtenerEdificios(Long idDigInstrumento, List<String> respuestasEdificios) {
        List<GrupoEdificios> lista5Ed = new ArrayList<GrupoEdificios>();
        try {
            List<Object[]> listaRespuesta = facBasica.obtenerRespuestasInstrumentoDig(idDigInstrumento, respuestasEdificios);

            Map<String, Long> elemNumEdif = new HashMap<String, Long>();
            List<Object[]> listaRespuesta2 = new ArrayList<Object[]>();
            String codigo = "";
            String valor = "";
            Long idElem = null;
            List<String> numsEd = new ArrayList<String>();
            for (Object[] fila : listaRespuesta) {
                int i = 0;
                codigo = (String) fila[i++];
                valor = (String) fila[i++];
                idElem = new Long(fila[i++] + "");
                if ("RESP_073".equals(codigo)) {
                    //Guardamos el idElemento y el numero de edificio
                    elemNumEdif.put(valor, idElem);
                    numsEd.add(valor);
                }
                listaRespuesta2.add(fila);
            }

            Collections.sort(numsEd);

            //lista5Ed = new ArrayList<GrupoEdificios>();
            int aux = 0;
            GrupoEdificios edificio = new GrupoEdificios();
            for (String num : numsEd) {
                aux++;
                Long idSelec = elemNumEdif.get(num);

                for (Object[] fila : listaRespuesta2) {
                    int i = 0;
                    codigo = (String) fila[i++];
                    valor = (String) fila[i++];
                    idElem = new Long(fila[i++] + "");
                    if (idElem.equals(idSelec)) {
                        if ("RESP_073".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_073(valor);
                            } else if (aux == 2) {
                                edificio.setDOS_RESP_073(valor);
                            } else if (aux == 3) {
                                edificio.setTRES_RESP_073(valor);
                            } else if (aux == 4) {
                                edificio.setCUATRO_RESP_073(valor);
                            } else if (aux == 5) {
                                edificio.setCINCO_RESP_073(valor);
                            }
                        } else if ("RESP_074_01".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_074_01(valor);
                            } else if (aux == 2) {
                                edificio.setDOS_RESP_074_01(valor);
                            } else if (aux == 3) {
                                edificio.setTRES_RESP_074_01(valor);
                            } else if (aux == 4) {
                                edificio.setCUATRO_RESP_074_01(valor);
                            } else if (aux == 5) {
                                edificio.setCINCO_RESP_074_01(valor);
                            }
                        } else if ("RESP_074_02".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_074_02(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                edificio.setDOS_RESP_074_02(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                edificio.setTRES_RESP_074_02(transformaSiNoNA(valor));
                            } else if (aux == 4) {
                                edificio.setCUATRO_RESP_074_02(transformaSiNoNA(valor));
                            } else if (aux == 5) {
                                edificio.setCINCO_RESP_074_02(transformaSiNoNA(valor));
                            }
                        } else if ("RESP_075_01".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_075_01(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                edificio.setDOS_RESP_075_01(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                edificio.setTRES_RESP_075_01(transformaSiNoNA(valor));
                            } else if (aux == 4) {
                                edificio.setCUATRO_RESP_075_01(transformaSiNoNA(valor));
                            } else if (aux == 5) {
                                edificio.setCINCO_RESP_075_01(transformaSiNoNA(valor));
                            }
                        } else if ("RESP_075_02".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_075_02(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                edificio.setDOS_RESP_075_02(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                edificio.setTRES_RESP_075_02(transformaSiNoNA(valor));
                            } else if (aux == 4) {
                                edificio.setCUATRO_RESP_075_02(transformaSiNoNA(valor));
                            } else if (aux == 5) {
                                edificio.setCINCO_RESP_075_02(transformaSiNoNA(valor));
                            }
                        } else if ("RESP_075_03".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_075_03(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                edificio.setDOS_RESP_075_03(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                edificio.setTRES_RESP_075_03(transformaSiNoNA(valor));
                            } else if (aux == 4) {
                                edificio.setCUATRO_RESP_075_03(transformaSiNoNA(valor));
                            } else if (aux == 5) {
                                edificio.setCINCO_RESP_075_03(transformaSiNoNA(valor));
                            }
                        } else if ("RESP_076_01".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_076_01(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                edificio.setDOS_RESP_076_01(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                edificio.setTRES_RESP_076_01(transformaSiNoNA(valor));
                            } else if (aux == 4) {
                                edificio.setCUATRO_RESP_076_01(transformaSiNoNA(valor));
                            } else if (aux == 5) {
                                edificio.setCINCO_RESP_076_01(transformaSiNoNA(valor));
                            }
                        } else if ("RESP_076_02".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_076_02(valor);
                            } else if (aux == 2) {
                                edificio.setDOS_RESP_076_02(valor);
                            } else if (aux == 3) {
                                edificio.setTRES_RESP_076_02(valor);
                            } else if (aux == 4) {
                                edificio.setCUATRO_RESP_076_02(valor);
                            } else if (aux == 5) {
                                edificio.setCINCO_RESP_076_02(valor);
                            }
                        } else if ("RESP_076_03".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_076_03(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                edificio.setDOS_RESP_076_03(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                edificio.setTRES_RESP_076_03(transformaSiNoNA(valor));
                            } else if (aux == 4) {
                                edificio.setCUATRO_RESP_076_03(transformaSiNoNA(valor));
                            } else if (aux == 5) {
                                edificio.setCINCO_RESP_076_03(transformaSiNoNA(valor));
                            }
                        } else if ("RESP_077_01".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_077_01(valor);
                            } else if (aux == 2) {
                                edificio.setDOS_RESP_077_01(valor);
                            } else if (aux == 3) {
                                edificio.setTRES_RESP_077_01(valor);
                            } else if (aux == 4) {
                                edificio.setCUATRO_RESP_077_01(valor);
                            } else if (aux == 5) {
                                edificio.setCINCO_RESP_077_01(valor);
                            }
                        } else if ("RESP_077_02".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_077_02(valor);
                            } else if (aux == 2) {
                                edificio.setDOS_RESP_077_02(valor);
                            } else if (aux == 3) {
                                edificio.setTRES_RESP_077_02(valor);
                            } else if (aux == 4) {
                                edificio.setCUATRO_RESP_077_02(valor);
                            } else if (aux == 5) {
                                edificio.setCINCO_RESP_077_02(valor);
                            }
                        } else if ("RESP_077_03".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_077_03(valor);
                            } else if (aux == 2) {
                                edificio.setDOS_RESP_077_03(valor);
                            } else if (aux == 3) {
                                edificio.setTRES_RESP_077_03(valor);
                            } else if (aux == 4) {
                                edificio.setCUATRO_RESP_077_03(valor);
                            } else if (aux == 5) {
                                edificio.setCINCO_RESP_077_03(valor);
                            }
                        } else if ("RESP_078_01".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_078_01(valor);
                            } else if (aux == 2) {
                                edificio.setDOS_RESP_078_01(valor);
                            } else if (aux == 3) {
                                edificio.setTRES_RESP_078_01(valor);
                            } else if (aux == 4) {
                                edificio.setCUATRO_RESP_078_01(valor);
                            } else if (aux == 5) {
                                edificio.setCINCO_RESP_078_01(valor);
                            }
                        } else if ("RESP_078_02".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_078_02(valor);
                            } else if (aux == 2) {
                                edificio.setDOS_RESP_078_02(valor);
                            } else if (aux == 3) {
                                edificio.setTRES_RESP_078_02(valor);
                            } else if (aux == 4) {
                                edificio.setCUATRO_RESP_078_02(valor);
                            } else if (aux == 5) {
                                edificio.setCINCO_RESP_078_02(valor);
                            }
                        } else if ("RESP_079_01".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_079_01(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                edificio.setDOS_RESP_079_01(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                edificio.setTRES_RESP_079_01(transformaSiNoNA(valor));
                            } else if (aux == 4) {
                                edificio.setCUATRO_RESP_079_01(transformaSiNoNA(valor));
                            } else if (aux == 5) {
                                edificio.setCINCO_RESP_079_01(transformaSiNoNA(valor));
                            }
                        } else if ("RESP_079_02".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_079_02(valor);
                            } else if (aux == 2) {
                                edificio.setDOS_RESP_079_02(valor);
                            } else if (aux == 3) {
                                edificio.setTRES_RESP_079_02(valor);
                            } else if (aux == 4) {
                                edificio.setCUATRO_RESP_079_02(valor);
                            } else if (aux == 5) {
                                edificio.setCINCO_RESP_079_02(valor);
                            }
                        } else if ("RESP_079_03".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_079_03(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                edificio.setDOS_RESP_079_03(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                edificio.setTRES_RESP_079_03(transformaSiNoNA(valor));
                            } else if (aux == 4) {
                                edificio.setCUATRO_RESP_079_03(transformaSiNoNA(valor));
                            } else if (aux == 5) {
                                edificio.setCINCO_RESP_079_03(transformaSiNoNA(valor));
                            }
                        } else if ("RESP_080_01".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_080_01(valor);
                            } else if (aux == 2) {
                                edificio.setDOS_RESP_080_01(valor);
                            } else if (aux == 3) {
                                edificio.setTRES_RESP_080_01(valor);
                            } else if (aux == 4) {
                                edificio.setCUATRO_RESP_080_01(valor);
                            } else if (aux == 5) {
                                edificio.setCINCO_RESP_080_01(valor);
                            }
                        } else if ("RESP_080_02".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_080_02(valor);
                            } else if (aux == 2) {
                                edificio.setDOS_RESP_080_02(valor);
                            } else if (aux == 3) {
                                edificio.setTRES_RESP_080_02(valor);
                            } else if (aux == 4) {
                                edificio.setCUATRO_RESP_080_02(valor);
                            } else if (aux == 5) {
                                edificio.setCINCO_RESP_080_02(valor);
                            }
                        } else if ("RESP_080_03".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_080_03(valor);
                            } else if (aux == 2) {
                                edificio.setDOS_RESP_080_03(valor);
                            } else if (aux == 3) {
                                edificio.setTRES_RESP_080_03(valor);
                            } else if (aux == 4) {
                                edificio.setCUATRO_RESP_080_03(valor);
                            } else if (aux == 5) {
                                edificio.setCINCO_RESP_080_03(valor);
                            }
                        } else if ("RESP_081_01".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_081_01(valor);
                            } else if (aux == 2) {
                                edificio.setDOS_RESP_081_01(valor);
                            } else if (aux == 3) {
                                edificio.setTRES_RESP_081_01(valor);
                            } else if (aux == 4) {
                                edificio.setCUATRO_RESP_081_01(valor);
                            } else if (aux == 5) {
                                edificio.setCINCO_RESP_081_01(valor);
                            }
                        } else if ("RESP_081_02".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_081_02(valor);
                            } else if (aux == 2) {
                                edificio.setDOS_RESP_081_02(valor);
                            } else if (aux == 3) {
                                edificio.setTRES_RESP_081_02(valor);
                            } else if (aux == 4) {
                                edificio.setCUATRO_RESP_081_02(valor);
                            } else if (aux == 5) {
                                edificio.setCINCO_RESP_081_02(valor);
                            }
                        } else if ("RESP_081_03".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_081_03(valor);
                            } else if (aux == 2) {
                                edificio.setDOS_RESP_081_03(valor);
                            } else if (aux == 3) {
                                edificio.setTRES_RESP_081_03(valor);
                            } else if (aux == 4) {
                                edificio.setCUATRO_RESP_081_03(valor);
                            } else if (aux == 5) {
                                edificio.setCINCO_RESP_081_03(valor);
                            }
                        } else if ("RESP_082_01".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_082_01(valor);
                            } else if (aux == 2) {
                                edificio.setDOS_RESP_082_01(valor);
                            } else if (aux == 3) {
                                edificio.setTRES_RESP_082_01(valor);
                            } else if (aux == 4) {
                                edificio.setCUATRO_RESP_082_01(valor);
                            } else if (aux == 5) {
                                edificio.setCINCO_RESP_082_01(valor);
                            }
                        } else if ("RESP_082_02".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_082_02(valor);
                            } else if (aux == 2) {
                                edificio.setDOS_RESP_082_02(valor);
                            } else if (aux == 3) {
                                edificio.setTRES_RESP_082_02(valor);
                            } else if (aux == 4) {
                                edificio.setCUATRO_RESP_082_02(valor);
                            } else if (aux == 5) {
                                edificio.setCINCO_RESP_082_02(valor);
                            }
                        } else if ("RESP_082_03".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_082_03(valor);
                            } else if (aux == 2) {
                                edificio.setDOS_RESP_082_03(valor);
                            } else if (aux == 3) {
                                edificio.setTRES_RESP_082_03(valor);
                            } else if (aux == 4) {
                                edificio.setCUATRO_RESP_082_03(valor);
                            } else if (aux == 5) {
                                edificio.setCINCO_RESP_082_03(valor);
                            }
                        } else if ("RESP_083".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_083(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                edificio.setDOS_RESP_083(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                edificio.setTRES_RESP_083(transformaSiNoNA(valor));
                            } else if (aux == 4) {
                                edificio.setCUATRO_RESP_083(transformaSiNoNA(valor));
                            } else if (aux == 5) {
                                edificio.setCINCO_RESP_083(transformaSiNoNA(valor));
                            }
                        } else if ("RESP_084_01".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_084_01(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                edificio.setDOS_RESP_084_01(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                edificio.setTRES_RESP_084_01(transformaSiNoNA(valor));
                            } else if (aux == 4) {
                                edificio.setCUATRO_RESP_084_01(transformaSiNoNA(valor));
                            } else if (aux == 5) {
                                edificio.setCINCO_RESP_084_01(transformaSiNoNA(valor));
                            }
                        } else if ("RESP_084_02".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_084_02(valor);
                            } else if (aux == 2) {
                                edificio.setDOS_RESP_084_02(valor);
                            } else if (aux == 3) {
                                edificio.setTRES_RESP_084_02(valor);
                            } else if (aux == 4) {
                                edificio.setCUATRO_RESP_084_02(valor);
                            } else if (aux == 5) {
                                edificio.setCINCO_RESP_084_02(valor);
                            }
                        } else if ("RESP_084_03".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_084_03(valor);
                            } else if (aux == 2) {
                                edificio.setDOS_RESP_084_03(valor);
                            } else if (aux == 3) {
                                edificio.setTRES_RESP_084_03(valor);
                            } else if (aux == 4) {
                                edificio.setCUATRO_RESP_084_03(valor);
                            } else if (aux == 5) {
                                edificio.setCINCO_RESP_084_03(valor);
                            }
                        } else if ("RESP_085".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_085(valor);
                            } else if (aux == 2) {
                                edificio.setDOS_RESP_085(valor);
                            } else if (aux == 3) {
                                edificio.setTRES_RESP_085(valor);
                            } else if (aux == 4) {
                                edificio.setCUATRO_RESP_085(valor);
                            } else if (aux == 5) {
                                edificio.setCINCO_RESP_085(valor);
                            }
                        } else if ("RESP_086_01".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_086_01(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                edificio.setDOS_RESP_086_01(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                edificio.setTRES_RESP_086_01(transformaSiNoNA(valor));
                            } else if (aux == 4) {
                                edificio.setCUATRO_RESP_086_01(transformaSiNoNA(valor));
                            } else if (aux == 5) {
                                edificio.setCINCO_RESP_086_01(transformaSiNoNA(valor));
                            }
                        } else if ("RESP_086_02".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_086_02(valor);
                            } else if (aux == 2) {
                                edificio.setDOS_RESP_086_02(valor);
                            } else if (aux == 3) {
                                edificio.setTRES_RESP_086_02(valor);
                            } else if (aux == 4) {
                                edificio.setCUATRO_RESP_086_02(valor);
                            } else if (aux == 5) {
                                edificio.setCINCO_RESP_086_02(valor);
                            }
                        } else if ("RESP_086_03".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_086_03(valor);
                            } else if (aux == 2) {
                                edificio.setDOS_RESP_086_03(valor);
                            } else if (aux == 3) {
                                edificio.setTRES_RESP_086_03(valor);
                            } else if (aux == 4) {
                                edificio.setCUATRO_RESP_086_03(valor);
                            } else if (aux == 5) {
                                edificio.setCINCO_RESP_086_03(valor);
                            }
                        } else if ("RESP_087_01".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_087_01(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                edificio.setDOS_RESP_087_01(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                edificio.setTRES_RESP_087_01(transformaSiNoNA(valor));
                            } else if (aux == 4) {
                                edificio.setCUATRO_RESP_087_01(transformaSiNoNA(valor));
                            } else if (aux == 5) {
                                edificio.setCINCO_RESP_087_01(transformaSiNoNA(valor));
                            }
                        } else if ("RESP_087_02".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_087_02(valor);
                            } else if (aux == 2) {
                                edificio.setDOS_RESP_087_02(valor);
                            } else if (aux == 3) {
                                edificio.setTRES_RESP_087_02(valor);
                            } else if (aux == 4) {
                                edificio.setCUATRO_RESP_087_02(valor);
                            } else if (aux == 5) {
                                edificio.setCINCO_RESP_087_02(valor);
                            }
                        } else if ("RESP_088".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_088(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                edificio.setDOS_RESP_088(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                edificio.setTRES_RESP_088(transformaSiNoNA(valor));
                            } else if (aux == 4) {
                                edificio.setCUATRO_RESP_088(transformaSiNoNA(valor));
                            } else if (aux == 5) {
                                edificio.setCINCO_RESP_088(transformaSiNoNA(valor));
                            }
                        } else if ("RESP_089_01".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_089_01(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                edificio.setDOS_RESP_089_01(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                edificio.setTRES_RESP_089_01(transformaSiNoNA(valor));
                            } else if (aux == 4) {
                                edificio.setCUATRO_RESP_089_01(transformaSiNoNA(valor));
                            } else if (aux == 5) {
                                edificio.setCINCO_RESP_089_01(transformaSiNoNA(valor));
                            }
                        } else if ("RESP_089_02".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_089_02(valor);
                            } else if (aux == 2) {
                                edificio.setDOS_RESP_089_02(valor);
                            } else if (aux == 3) {
                                edificio.setTRES_RESP_089_02(valor);
                            } else if (aux == 4) {
                                edificio.setCUATRO_RESP_089_02(valor);
                            } else if (aux == 5) {
                                edificio.setCINCO_RESP_089_02(valor);
                            }
                        } else if ("RESP_090_01".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_090_01(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                edificio.setDOS_RESP_090_01(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                edificio.setTRES_RESP_090_01(transformaSiNoNA(valor));
                            } else if (aux == 4) {
                                edificio.setCUATRO_RESP_090_01(transformaSiNoNA(valor));
                            } else if (aux == 5) {
                                edificio.setCINCO_RESP_090_01(transformaSiNoNA(valor));
                            }
                        } else if ("RESP_090_02".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_090_02(valor);
                            } else if (aux == 2) {
                                edificio.setDOS_RESP_090_02(valor);
                            } else if (aux == 3) {
                                edificio.setTRES_RESP_090_02(valor);
                            } else if (aux == 4) {
                                edificio.setCUATRO_RESP_090_02(valor);
                            } else if (aux == 5) {
                                edificio.setCINCO_RESP_090_02(valor);
                            }
                        } else if ("RESP_091".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_091(valor);
                            } else if (aux == 2) {
                                edificio.setDOS_RESP_091(valor);
                            } else if (aux == 3) {
                                edificio.setTRES_RESP_091(valor);
                            } else if (aux == 4) {
                                edificio.setCUATRO_RESP_091(valor);
                            } else if (aux == 5) {
                                edificio.setCINCO_RESP_091(valor);
                            }
                        }
                    }
                }
                if (aux == 5) {
                    lista5Ed.add(edificio);
                    edificio = new GrupoEdificios();
                    aux = 0;
                }
            }
            if (edificio.getUNO_RESP_073() != null && !"".equals(edificio.getUNO_RESP_073())) {
                lista5Ed.add(edificio);
            }
        } catch (Exception e) {
            ManejadorErrores.ingresarExcepcionEnLog(e, ConsultaInstrumentoBean.class);
        }

        return lista5Ed;
    }

    public List<GrupoEdificios> obtenerEdificiosTipologias(Long idDigInstrumento, List<String> respuestasEdificios) {
        List<GrupoEdificios> lista2Ed = new ArrayList<GrupoEdificios>();
        try {
            List<Object[]> listaRespuesta = facBasica.obtenerRespuestasInstrumentoDig(idDigInstrumento, respuestasEdificios);
            Map<String, Long> elemNumEdif = new HashMap<String, Long>();
            List<Object[]> listaRespuesta2 = new ArrayList<Object[]>();
            String codigo = "";
            String valor = "";
            Long idElem = null;
            List<String> numsEd = new ArrayList<String>();
            for (Object[] fila : listaRespuesta) {
                int i = 0;
                codigo = (String) fila[i++];
                valor = (String) fila[i++];
                idElem = new Long(fila[i++] + "");
                if ("RESP_073".equals(codigo)) {
                    //Guardamos el idElemento y el numero de edificio
                    elemNumEdif.put(valor, idElem);
                    numsEd.add(valor);
                }
                listaRespuesta2.add(fila);
            }
            Collections.sort(numsEd);
            int aux = 0;
            GrupoEdificios edificio = new GrupoEdificios();
            for (String num : numsEd) {
                aux++;
                Long idSelec = elemNumEdif.get(num);

                for (Object[] fila : listaRespuesta2) {
                    int i = 0;
                    codigo = (String) fila[i++];
                    valor = (String) fila[i++];
                    idElem = new Long(fila[i++] + "");
                    if (idElem.equals(idSelec) && valor != null ) {
                        if ("RESP_073".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_073(valor);
                            }
                        } else if ("RESP_074_01".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_074_01(valor);
                            }
                        }else if ("RESP_074_02".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_074_02(transformaSiNoNA(valor));
                            }
                        } else if ("RESP_075_01".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_075_01(transformaSiNoNA(valor));
                            }
                        } else if ("RESP_075_02".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_075_02(transformaSiNoNA(valor));
                            }
                        } else if ("RESP_075_03".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_075_03(transformaSiNoNA(valor));
                            }
                        } else if ("RESP_076_01".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_076_01(transformaSiNoNA(valor));
                            }
                        } else if ("RESP_076_02".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_076_02(valor);
                            }
                        } else if ("RESP_076_03".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_076_03(transformaSiNoNA(valor));
                            }
                        } else if ("RESP_077_01".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("TSE", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            if (aux == 1) {
                                edificio.setUNO_RESP_077_01( valor);
                            }
                        } else if ("RESP_077_02".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_077_02(valor);
                            }
                        } else if ("RESP_077_03".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_077_03(valor);
                            }
                        } else if ("RESP_078_01".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_078_01(valor);
                            }
                        } else if ("RESP_078_02".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_078_02(valor);
                            }
                        } else if ("RESP_079_01".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_079_01(transformaSiNoNA(valor));
                            }
                        } else if ("RESP_079_02".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_079_02(valor);
                            }
                        } else if ("RESP_079_03".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_079_03(transformaSiNoNA(valor));
                            }
                        } else if ("RESP_080_01".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("EMPMF", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            if (aux == 1) {
                                edificio.setUNO_RESP_080_01(valor);
                            }
                        } else if ("RESP_080_02".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_080_02(valor);
                            }
                        } else if ("RESP_080_03".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_080_03(valor);
                            }
                        } else if ("RESP_081_01".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("EMPAMF", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            if (aux == 1) {
                                edificio.setUNO_RESP_081_01(valor);
                            }
                        } else if ("RESP_081_02".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_081_02(valor);
                            }
                        } else if ("RESP_081_03".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_081_03(valor);
                            }
                        } else if ("RESP_082_01".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("EMPC", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            if (aux == 1) {
                                edificio.setUNO_RESP_082_01(valor);
                            }
                        } else if ("RESP_082_02".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_082_02(valor);
                            }
                        } else if ("RESP_082_03".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_082_03(valor);
                            }
                        } else if ("RESP_083".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_083(transformaSiNoNA(valor));
                            }
                        } else if ("RESP_084_01".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_084_01(transformaSiNoNA(valor));
                            }
                        } else if ("RESP_084_02".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("ECA", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            if (aux == 1) {
                                edificio.setUNO_RESP_084_02(valor);
                            }
                        } else if ("RESP_084_03".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_084_03(valor);
                            }
                        } else if ("RESP_085".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_085(valor);
                            }
                        } else if ("RESP_086_01".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_086_01(transformaSiNoNA(valor));
                            }
                        } else if ("RESP_086_02".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("SECI", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            if (aux == 1) {
                                edificio.setUNO_RESP_086_02(valor);
                            }
                        } else if ("RESP_086_03".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("SECI", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            if (aux == 1) {
                                edificio.setUNO_RESP_086_03(valor);
                            }
                        } else if ("RESP_087_01".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_087_01(transformaSiNoNA(valor));
                            }
                        } else if ("RESP_087_02".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_087_02(valor);
                            }
                        } else if ("RESP_088".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_088(transformaSiNoNA(valor));
                            }
                        } else if ("RESP_089_01".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_089_01(transformaSiNoNA(valor));
                            }
                        } else if ("RESP_089_02".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_089_02(valor);
                            }
                        } else if ("RESP_090_01".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_090_01(transformaSiNoNA(valor));
                            }
                        } else if ("RESP_090_02".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_090_02(valor);
                            }
                        } else if ("RESP_091".equals(codigo)) {
                            if (aux == 1) {
                                edificio.setUNO_RESP_091(valor);
                            }
                        }
                    }
                }
                if (aux == 1) {
                    lista2Ed.add(edificio);
                    edificio = new GrupoEdificios();
                    aux = 0;
                }
            }
            if (edificio.getUNO_RESP_073() != null && !"".equals(edificio.getUNO_RESP_073())) {
                lista2Ed.add(edificio);
            }
        } catch (Exception e) {
            ManejadorErrores.ingresarExcepcionEnLog(e, ConsultaInstrumentoBean.class);
        }

        return lista2Ed;
    }

    public List<EspaciosEscolares> obtenerEspaciosEscolares(Long idDigInstrumento, List<String> respuestasEspaciosEscolares) {
        List<EspaciosEscolares> listaEE = new ArrayList<EspaciosEscolares>();
        try {
            List<Object[]> listaRespuesta = facBasica.obtenerRespuestasInstrumentoDig(idDigInstrumento, respuestasEspaciosEscolares);


            Map<String, Long> elemNumEE = new HashMap<String, Long>();
            Map<Long, String> elemNumEEInvertida = new HashMap<Long, String>();
            List<Object[]> listaRespuesta2 = new ArrayList<Object[]>();
            String codigo = "";
            String valor = "";
            Long idElem = null;
            for (Object[] fila : listaRespuesta) {
                int i = 0;
                codigo = (String) fila[i++];
                valor = (String) fila[i++];
                idElem = new Long(fila[i++] + "");
                //Guardamos el idElemento y el numero de espacio escolar
                if ("RESP_094".equals(codigo) || "RESP_095".equals(codigo)) {
                    if (elemNumEEInvertida.containsKey(idElem)) {//Ponemos primero el id del espacio(094) y segundo el numero de edificio(095)
                        if ("RESP_094".equals(codigo)) {
                            elemNumEEInvertida.put(idElem, valor + elemNumEEInvertida.get(idElem));
                        } else {
                            elemNumEEInvertida.put(idElem, elemNumEEInvertida.get(idElem) + valor);
                        }
                    } else {
                        elemNumEEInvertida.put(idElem, valor);
                    }
                    //elemNumEE.put(valor,idElem);
                    //numsEE.add(valor);
                }
                listaRespuesta2.add(fila);
            }
            for (Long key : elemNumEEInvertida.keySet()) {
                elemNumEE.put(elemNumEEInvertida.get(key), key);
            }
            List<String> numsEE = new ArrayList<String>(elemNumEEInvertida.values());
            //Las ponemos en orden
            Collections.sort(numsEE);

            EspaciosEscolares ee = null;
            for (String num : numsEE) {
                ee = new EspaciosEscolares();
                Long idSelec = elemNumEE.get(num);

                for (Object[] fila : listaRespuesta2) {
                    int i = 0;
                    codigo = (String) fila[i++];
                    valor = (String) fila[i++];
                    idElem = new Long(fila[i++] + "");
                    if (idElem.equals(idSelec)) {
                        if ("RESP_094".equals(codigo)) {
                            ee.setRESP_094(valor);
                        }
                        if ("RESP_095".equals(codigo)) {
                            ee.setRESP_095(valor);
                        }
                        if ("RESP_096".equals(codigo)) {
                            ee.setRESP_096(valor);
                        }
                        if ("RESP_097".equals(codigo)) {
                            ee.setRESP_097(valor);
                        }
                        if ("RESP_098_01".equals(codigo)) {
                            ee.setRESP_098_01(valor);
                        }
                        if ("RESP_098_02".equals(codigo)) {
                            ee.setRESP_098_02(valor);
                        }
                        if ("RESP_099_01".equals(codigo)) {
                            ee.setRESP_099_01(transformaSiNoNA(valor));
                        }
                        if ("RESP_099_02".equals(codigo)) {
                            ee.setRESP_099_02(valor);
                        }
                        if ("RESP_100".equals(codigo)) {
                            ee.setRESP_100(transformaSiNoNA(valor));
                        }
                        if ("RESP_101".equals(codigo)) {
                            ee.setRESP_101(valor);
                        }
                        if ("RESP_102".equals(codigo)) {
                            ee.setRESP_102(valor);
                        }
                        if ("RESP_103".equals(codigo)) {
                            ee.setRESP_103(valor);
                        }
                        if ("RESP_104".equals(codigo)) {
                            ee.setRESP_104(valor);
                        }
                        if ("RESP_105".equals(codigo)) {
                            ee.setRESP_105(valor);
                        }
                        if ("RESP_106".equals(codigo)) {
                            ee.setRESP_106(valor);
                        }
                        if ("RESP_107_01".equals(codigo)) {
                            ee.setRESP_107_01(transformaSiNoNA(valor));
                        }
                        if ("RESP_107_02".equals(codigo)) {
                            ee.setRESP_107_02(valor);
                        }
                        if ("RESP_107_03".equals(codigo)) {
                            ee.setRESP_107_03(valor);
                        }
                        if ("RESP_108_01".equals(codigo)) {
                            ee.setRESP_108_01(transformaSiNoNA(valor));
                        }
                        if ("RESP_108_02".equals(codigo)) {
                            ee.setRESP_108_02(valor);
                        }
                        if ("RESP_108_03".equals(codigo)) {
                            ee.setRESP_108_03(valor);
                        }
                        if ("RESP_109_01".equals(codigo)) {
                            ee.setRESP_109_01(transformaSiNoNA(valor));
                        }
                        if ("RESP_109_02".equals(codigo)) {
                            ee.setRESP_109_02(valor);
                        }
                        if ("RESP_109_03".equals(codigo)) {
                            ee.setRESP_109_03(valor);
                        }
                        if ("RESP_110_01".equals(codigo)) {
                            ee.setRESP_110_01(valor);
                        }
                        if ("RESP_110_02".equals(codigo)) {
                            ee.setRESP_110_02(valor);
                        }
                        if ("RESP_110_03".equals(codigo)) {
                            ee.setRESP_110_03(valor);
                        }
                        if ("RESP_110_04".equals(codigo)) {
                            ee.setRESP_110_04(valor);
                        }
                        if ("RESP_110_05".equals(codigo)) {
                            ee.setRESP_110_05(valor);
                        }
                        if ("RESP_110_06".equals(codigo)) {
                            ee.setRESP_110_06(valor);
                        }
                        if ("RESP_111_01".equals(codigo)) {
                            ee.setRESP_111_01(transformaSiNoNA(valor));
                        }
                        if ("RESP_111_02".equals(codigo)) {
                            ee.setRESP_111_02(valor);
                        }
                        if ("RESP_111_03".equals(codigo)) {
                            ee.setRESP_111_03(valor);
                        }
                        if ("RESP_112_01".equals(codigo)) {
                            ee.setRESP_112_01(transformaSiNoNA(valor));
                        }
                        if ("RESP_112_02".equals(codigo)) {
                            ee.setRESP_112_02(valor);
                        }
                        if ("RESP_112_03".equals(codigo)) {
                            ee.setRESP_112_03(valor);
                        }
                        if ("RESP_112_04".equals(codigo)) {
                            ee.setRESP_112_04(valor);
                        }
                        if ("RESP_113_01".equals(codigo)) {
                            ee.setRESP_113_01(transformaSiNoNA(valor));
                        }
                        if ("RESP_113_02".equals(codigo)) {
                            ee.setRESP_113_02(valor);
                        }
                        if ("RESP_113_03".equals(codigo)) {
                            ee.setRESP_113_03(valor);
                        }
                        if ("RESP_113_04".equals(codigo)) {
                            ee.setRESP_113_04(valor);
                        }
                        if ("RESP_114_01".equals(codigo)) {
                            ee.setRESP_114_01(transformaSiNoNA(valor));
                        }
                        if ("RESP_114_02".equals(codigo)) {
                            ee.setRESP_114_02(valor);
                        }
                        if ("RESP_114_03".equals(codigo)) {
                            ee.setRESP_114_03(valor);
                        }
                        if ("RESP_114_04".equals(codigo)) {
                            ee.setRESP_114_04(valor);
                        }
                        if ("RESP_115_01".equals(codigo)) {
                            ee.setRESP_115_01(transformaSiNoNA(valor));
                        }
                        if ("RESP_115_02".equals(codigo)) {
                            ee.setRESP_115_02(valor);
                        }
                        if ("RESP_115_03".equals(codigo)) {
                            ee.setRESP_115_03(valor);
                        }
                        if ("RESP_115_04".equals(codigo)) {
                            ee.setRESP_115_04(valor);
                        }
                        if ("RESP_116_01".equals(codigo)) {
                            ee.setRESP_116_01(transformaSiNoNA(valor));
                        }
                        if ("RESP_116_02".equals(codigo)) {
                            ee.setRESP_116_02(valor);
                        }
                        if ("RESP_116_03".equals(codigo)) {
                            ee.setRESP_116_03(valor);
                        }
                        if ("RESP_116_04".equals(codigo)) {
                            ee.setRESP_116_04(valor);
                        }
                        if ("RESP_117_01".equals(codigo)) {
                            ee.setRESP_117_01(transformaSiNoNA(valor));
                        }
                        if ("RESP_117_02".equals(codigo)) {
                            ee.setRESP_117_02(valor);
                        }
                        if ("RESP_117_03".equals(codigo)) {
                            ee.setRESP_117_03(valor);
                        }
                        if ("RESP_117_04".equals(codigo)) {
                            ee.setRESP_117_04(valor);
                        }
                        if ("RESP_295".equals(codigo)) {
                            ee.setRESP_295(transformaSiNoNA(valor));
                        }
                        if ("RESP_118".equals(codigo)) {
                            ee.setRESP_118(transformaSiNoNA(valor));
                        }
                        if ("RESP_119_01".equals(codigo)) {
                            ee.setRESP_119_01(tranformacionSuficiente(valor));
                        }
                        if ("RESP_119_02".equals(codigo)) {
                            ee.setRESP_119_02(valor);
                        }
                        if ("RESP_119_03".equals(codigo)) {
                            ee.setRESP_119_03(valor);
                        }
                        if ("RESP_120_01".equals(codigo)) {
                            ee.setRESP_120_01(transformaSiNoNA(valor));
                        }
                        if ("RESP_120_02".equals(codigo)) {
                            ee.setRESP_120_02(valor);
                        }
                        if ("RESP_120_03".equals(codigo)) {
                            ee.setRESP_120_03(valor);
                        }
                        if ("RESP_120_04".equals(codigo)) {
                            ee.setRESP_120_04(valor);
                        }
                        if ("RESP_120_05".equals(codigo)) {
                            ee.setRESP_120_05(valor);
                        }
                        if ("RESP_120_06".equals(codigo)) {
                            ee.setRESP_120_06(valor);
                        }
                        if ("RESP_121_01".equals(codigo)) {
                            ee.setRESP_121_01(transformaSiNoNA(valor));
                        }
                        if ("RESP_121_02".equals(codigo)) {
                            ee.setRESP_121_02(valor);
                        }
                        if ("RESP_121_03".equals(codigo)) {
                            ee.setRESP_121_03(valor);
                        }
                        if ("RESP_122_01".equals(codigo)) {
                            ee.setRESP_122_01(transformaSiNoNA(valor));
                        }
                        if ("RESP_122_02".equals(codigo)) {
                            ee.setRESP_122_02(valor);
                        }
                        if ("RESP_122_03".equals(codigo)) {
                            ee.setRESP_122_03(valor);
                        }
                        if ("RESP_123_01".equals(codigo)) {
                            ee.setRESP_123_01(transformaSiNoNA(valor));
                        }
                        if ("RESP_123_02".equals(codigo)) {
                            ee.setRESP_123_02(valor);
                        }
                        if ("RESP_123_03".equals(codigo)) {
                            ee.setRESP_123_03(valor);
                        }
                        if ("RESP_124_01".equals(codigo)) {
                            ee.setRESP_124_01(transformaSiNoNA(valor));
                        }
                        if ("RESP_124_02".equals(codigo)) {
                            ee.setRESP_124_02(valor);
                        }
                        if ("RESP_124_03".equals(codigo)) {
                            ee.setRESP_124_03(valor);
                        }
                        if ("RESP_125_01".equals(codigo)) {
                            ee.setRESP_125_01(transformaSiNoNA(valor));
                        }
                        if ("RESP_125_02".equals(codigo)) {
                            ee.setRESP_125_02(valor);
                        }
                        if ("RESP_125_03".equals(codigo)) {
                            ee.setRESP_125_03(valor);
                        }
                        if ("RESP_126_01".equals(codigo)) {
                            ee.setRESP_126_01(transformaSiNoNA(valor));
                        }
                        if ("RESP_126_02".equals(codigo)) {
                            ee.setRESP_126_02(valor);
                        }
                        if ("RESP_126_03".equals(codigo)) {
                            ee.setRESP_126_03(valor);
                        }
                        if ("RESP_127_01".equals(codigo)) {
                            ee.setRESP_127_01(transformaSiNoNA(valor));
                        }
                        if ("RESP_127_02".equals(codigo)) {
                            ee.setRESP_127_02(valor);
                        }
                        if ("RESP_127_03".equals(codigo)) {
                            ee.setRESP_127_03(valor);
                        }
                        if ("RESP_128_01".equals(codigo)) {
                            ee.setRESP_128_01(transformaSiNoNA(valor));
                        }
                        if ("RESP_128_02".equals(codigo)) {
                            ee.setRESP_128_02(valor);
                        }
                        if ("RESP_128_03".equals(codigo)) {
                            ee.setRESP_128_03(valor);
                        }
                        if ("RESP_129_01".equals(codigo)) {
                            ee.setRESP_129_01(transformaSiNoNA(valor));
                        }
                        if ("RESP_129_02".equals(codigo)) {
                            ee.setRESP_129_02(valor);
                        }
                        if ("RESP_129_03".equals(codigo)) {
                            ee.setRESP_129_03(valor);
                        }
                        if ("RESP_130_01".equals(codigo)) {
                            ee.setRESP_130_01(valor);
                        }
                        if ("RESP_130_02".equals(codigo)) {
                            ee.setRESP_130_02(valor);
                        }
                        if ("RESP_130_03".equals(codigo)) {
                            ee.setRESP_130_03(valor);
                        }
                        if ("RESP_130_04".equals(codigo)) {
                            ee.setRESP_130_04(valor);
                        }
                        if ("RESP_130_05".equals(codigo)) {
                            ee.setRESP_130_05(valor);
                        }
                        if ("RESP_130_06".equals(codigo)) {
                            ee.setRESP_130_06(valor);
                        }
                        if ("RESP_130_07".equals(codigo)) {
                            ee.setRESP_130_07(valor);
                        }
                        if ("RESP_130_08".equals(codigo)) {
                            ee.setRESP_130_08(valor);
                        }
                        if ("RESP_130_09".equals(codigo)) {
                            ee.setRESP_130_09(valor);
                        }
                        if ("RESP_130_10".equals(codigo)) {
                            ee.setRESP_130_10(valor);
                        }
                        if ("RESP_130_11".equals(codigo)) {
                            ee.setRESP_130_11(valor);
                        }
                        if ("RESP_130_12".equals(codigo)) {
                            ee.setRESP_130_12(valor);
                        }
                        if ("RESP_131_01".equals(codigo)) {
                            ee.setRESP_131_01(valor);
                        }
                        if ("RESP_131_02".equals(codigo)) {
                            ee.setRESP_131_02(valor);
                        }
                        if ("RESP_131_03".equals(codigo)) {
                            ee.setRESP_131_03(valor);
                        }
                        if ("RESP_131_04".equals(codigo)) {
                            ee.setRESP_131_04(valor);
                        }
                        if ("RESP_131_05".equals(codigo)) {
                            ee.setRESP_131_05(valor);
                        }
                        if ("RESP_131_06".equals(codigo)) {
                            ee.setRESP_131_06(valor);
                        }
                        if ("RESP_132_01".equals(codigo)) {
                            ee.setRESP_132_01(valor);
                        }
                        if ("RESP_132_02".equals(codigo)) {
                            ee.setRESP_132_02(valor);
                        }
                        if ("RESP_132_03".equals(codigo)) {
                            ee.setRESP_132_03(valor);
                        }
                        if ("RESP_132_04".equals(codigo)) {
                            ee.setRESP_132_04(valor);
                        }
                        if ("RESP_132_05".equals(codigo)) {
                            ee.setRESP_132_05(valor);
                        }
                        if ("RESP_132_06".equals(codigo)) {
                            ee.setRESP_132_06(valor);
                        }
                        if ("RESP_133_01".equals(codigo)) {
                            ee.setRESP_133_01(valor);
                        }
                        if ("RESP_133_02".equals(codigo)) {
                            ee.setRESP_133_02(valor);
                        }
                        if ("RESP_133_03".equals(codigo)) {
                            ee.setRESP_133_03(valor);
                        }
                        if ("RESP_134_01".equals(codigo)) {
                            ee.setRESP_134_01(valor);
                        }
                        if ("RESP_134_02".equals(codigo)) {
                            ee.setRESP_134_02(valor);
                        }
                        if ("RESP_134_03".equals(codigo)) {
                            ee.setRESP_134_03(valor);
                        }
                        if ("RESP_135_01".equals(codigo)) {
                            ee.setRESP_135_01(valor);
                        }
                        if ("RESP_135_02".equals(codigo)) {
                            ee.setRESP_135_02(valor);
                        }
                        if ("RESP_135_03".equals(codigo)) {
                            ee.setRESP_135_03(valor);
                        }
                        if ("RESP_135_04".equals(codigo)) {
                            ee.setRESP_135_04(valor);
                        }
                        if ("RESP_135_05".equals(codigo)) {
                            ee.setRESP_135_05(valor);
                        }
                        if ("RESP_135_06".equals(codigo)) {
                            ee.setRESP_135_06(valor);
                        }
                        if ("RESP_136_01".equals(codigo)) {
                            ee.setRESP_136_01(valor);
                        }
                        if ("RESP_136_02".equals(codigo)) {
                            ee.setRESP_136_02(valor);
                        }
                        if ("RESP_136_03".equals(codigo)) {
                            ee.setRESP_136_03(valor);
                        }
                        if ("RESP_136_04".equals(codigo)) {
                            ee.setRESP_136_04(valor);
                        }
                        if ("RESP_136_05".equals(codigo)) {
                            ee.setRESP_136_05(valor);
                        }
                        if ("RESP_136_06".equals(codigo)) {
                            ee.setRESP_136_06(valor);
                        }
                        if ("RESP_137_01".equals(codigo)) {
                            ee.setRESP_137_01(transformaSiNoNA(valor));
                        }
                        if ("RESP_137_02".equals(codigo)) {
                            ee.setRESP_137_02(valor);
                        }
                    }
                }
                listaEE.add(ee);
            }
        } catch (Exception e) {
            ManejadorErrores.ingresarExcepcionEnLog(e, ConsultaInstrumentoBean.class);
        }
        return listaEE;
    }

     public List<EspaciosEscolares> obtenerEspaciosEscolaresTipologias(Long idDigInstrumento, List<String> respuestasEspaciosEscolares) {
        List<EspaciosEscolares> listaEE = new ArrayList<EspaciosEscolares>();
        try {
            List<Object[]> listaRespuesta = facBasica.obtenerRespuestasInstrumentoDig(idDigInstrumento, respuestasEspaciosEscolares);


            Map<String, Long> elemNumEE = new HashMap<String, Long>();
            Map<Long, String> elemNumEEInvertida = new HashMap<Long, String>();
            List<Object[]> listaRespuesta2 = new ArrayList<Object[]>();
            String codigo = "";
            String valor = "";
            Long idElem = null;
            for (Object[] fila : listaRespuesta) {
                int i = 0;
                codigo = (String) fila[i++];
                valor = (String) fila[i++];
                idElem = new Long(fila[i++] + "");
                //Guardamos el idElemento y el numero de espacio escolar
                if ("RESP_094".equals(codigo) || "RESP_095".equals(codigo)) {
                    if (elemNumEEInvertida.containsKey(idElem)) {//Ponemos primero el id del espacio(094) y segundo el numero de edificio(095)
                        if ("RESP_094".equals(codigo)) {
                            elemNumEEInvertida.put(idElem, valor + elemNumEEInvertida.get(idElem));
                        } else {
                            elemNumEEInvertida.put(idElem, elemNumEEInvertida.get(idElem) + valor);
                        }
                    } else {
                        elemNumEEInvertida.put(idElem, valor);
                    }
                    //elemNumEE.put(valor,idElem);
                    //numsEE.add(valor);
                }
                listaRespuesta2.add(fila);
            }
            for (Long key : elemNumEEInvertida.keySet()) {
                elemNumEE.put(elemNumEEInvertida.get(key), key);
            }
            List<String> numsEE = new ArrayList<String>(elemNumEEInvertida.values());
            //Las ponemos en orden
            Collections.sort(numsEE);

            EspaciosEscolares ee = null;
            for (String num : numsEE) {
                ee = new EspaciosEscolares();
                Long idSelec = elemNumEE.get(num);

                for (Object[] fila : listaRespuesta2) {
                    int i = 0;
                    codigo = (String) fila[i++];
                    valor = (String) fila[i++];
                    idElem = new Long(fila[i++] + "");
                    if (idElem.equals(idSelec) && valor != null) {
                        if ("RESP_094".equals(codigo)) {
                            ee.setRESP_094(valor);
                        }
                        if ("RESP_095".equals(codigo)) {
                            ee.setRESP_095(valor);
                        }
                        if ("RESP_096".equals(codigo)) {
                            ee.setRESP_096(valor);
                        }
                        if ("RESP_097".equals(codigo)) {
                            ee.setRESP_097(valor);
                        }
                        if ("RESP_098_01".equals(codigo)) {
                            TipologiaValorNombre tv = facParam.consultarValorTipologia("TEE", valor, getUsuario().getUsuario().getIdioma().getId());
                            valor =  (tv != null ? tv.getTipValor().getCodigo()+" - "+tv.getNombre(): valor);
                           ee.setRESP_098_01(valor);
                        }
                        if ("RESP_098_02".equals(codigo)) {
                            ee.setRESP_098_02(valor);
                        }
                        if ("RESP_099_01".equals(codigo)) {
                            ee.setRESP_099_01(transformaSiNoNA(valor));
                        }
                        if ("RESP_099_02".equals(codigo)) {
                            ee.setRESP_099_02(valor);
                        }
                        if ("RESP_100".equals(codigo)) {
                            ee.setRESP_100(transformaSiNoNA(valor));
                        }
                        if ("RESP_101".equals(codigo)) {
                            ee.setRESP_101(valor);
                        }
                        if ("RESP_102".equals(codigo)) {
                            ee.setRESP_102(valor);
                        }
                        if ("RESP_103".equals(codigo)) {
                            ee.setRESP_103(valor);
                        }
                        if ("RESP_104".equals(codigo)) {
                            ee.setRESP_104(valor);
                        }
                        if ("RESP_105".equals(codigo)) {
                            ee.setRESP_105(valor);
                        }
                        if ("RESP_106".equals(codigo)) {
                            ee.setRESP_106(valor);
                        }
                        if ("RESP_107_01".equals(codigo)) {
                            ee.setRESP_107_01(transformaSiNoNA(valor));
                        }
                        if ("RESP_107_02".equals(codigo)) {
                            ee.setRESP_107_02(valor);
                        }
                        if ("RESP_107_03".equals(codigo)) {
                            ee.setRESP_107_03(valor);
                        }
                        if ("RESP_108_01".equals(codigo)) {
                            ee.setRESP_108_01(transformaSiNoNA(valor));
                        }
                        if ("RESP_108_02".equals(codigo)) {
                            ee.setRESP_108_02(valor);
                        }
                        if ("RESP_108_03".equals(codigo)) {
                            ee.setRESP_108_03(valor);
                        }
                        if ("RESP_109_01".equals(codigo)) {
                            ee.setRESP_109_01(transformaSiNoNA(valor));
                        }
                        if ("RESP_109_02".equals(codigo)) {
                            ee.setRESP_109_02(valor);
                        }
                        if ("RESP_109_03".equals(codigo)) {
                            ee.setRESP_109_03(valor);
                        }
                        if ("RESP_110_01".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("TAHTEM", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            ee.setRESP_110_01(valor);
                        }
                        if ("RESP_110_02".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("TAHTEM", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            ee.setRESP_110_02(valor);
                        }
                        if ("RESP_110_03".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("TAHTEM", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            ee.setRESP_110_03(valor);
                        }
                        if ("RESP_110_04".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("TAHTEM", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            ee.setRESP_110_04(valor);
                        }
                        if ("RESP_110_05".equals(codigo)) {
                            ee.setRESP_110_05(valor);
                        }
                        if ("RESP_110_06".equals(codigo)) {
                            ee.setRESP_110_06(valor);
                        }
                        if ("RESP_111_01".equals(codigo)) {
                            ee.setRESP_111_01(transformaSiNoNA(valor));
                        }
                        if ("RESP_111_02".equals(codigo)) {
                            ee.setRESP_111_02(valor);
                        }
                        if ("RESP_111_03".equals(codigo)) {
                            ee.setRESP_111_03(valor);
                        }
                        if ("RESP_112_01".equals(codigo)) {
                            ee.setRESP_112_01(transformaSiNoNA(valor));
                        }
                        if ("RESP_112_02".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("EEMPP", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            ee.setRESP_112_02(valor);
                        }
                        if ("RESP_112_03".equals(codigo)) {
                            ee.setRESP_112_03(valor);
                        }
                        if ("RESP_112_04".equals(codigo)) {
                            ee.setRESP_112_04(valor);
                        }
                        if ("RESP_113_01".equals(codigo)) {
                            ee.setRESP_113_01(transformaSiNoNA(valor));
                        }
                        if ("RESP_113_02".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("EEMPM", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            ee.setRESP_113_02(valor);
                        }
                        if ("RESP_113_03".equals(codigo)) {
                            ee.setRESP_113_03(valor);
                        }
                        if ("RESP_113_04".equals(codigo)) {
                            ee.setRESP_113_04(valor);
                        }
                        if ("RESP_114_01".equals(codigo)) {
                            ee.setRESP_114_01(transformaSiNoNA(valor));
                        }
                        if ("RESP_114_02".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("EEMPAMI", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            ee.setRESP_114_02(valor);
                        }
                        if ("RESP_114_03".equals(codigo)) {
                            ee.setRESP_114_03(valor);
                        }
                        if ("RESP_114_04".equals(codigo)) {
                            ee.setRESP_114_04(valor);
                        }
                        if ("RESP_115_01".equals(codigo)) {
                            ee.setRESP_115_01(transformaSiNoNA(valor));
                        }
                        if ("RESP_115_02".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("EEMPV", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            ee.setRESP_115_02(valor);
                        }
                        if ("RESP_115_03".equals(codigo)) {
                            ee.setRESP_115_03(valor);
                        }
                        if ("RESP_115_04".equals(codigo)) {
                            ee.setRESP_115_04(valor);
                        }
                        if ("RESP_116_01".equals(codigo)) {
                            ee.setRESP_116_01(transformaSiNoNA(valor));
                        }
                        if ("RESP_116_02".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("EEMPPU", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            ee.setRESP_116_02(valor);
                        }
                        if ("RESP_116_03".equals(codigo)) {
                            ee.setRESP_116_03(valor);
                        }
                        if ("RESP_116_04".equals(codigo)) {
                            ee.setRESP_116_04(valor);
                        }
                        if ("RESP_117_01".equals(codigo)) {
                            ee.setRESP_117_01(transformaSiNoNA(valor));
                        }
                        if ("RESP_117_02".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("EEMPCR", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            ee.setRESP_117_02(valor);
                        }
                        if ("RESP_117_03".equals(codigo)) {
                            ee.setRESP_117_03(valor);
                        }
                        if ("RESP_117_04".equals(codigo)) {
                            ee.setRESP_117_04(valor);
                        }
                        if ("RESP_295".equals(codigo)) {
                            ee.setRESP_295(transformaSiNoNA(valor));
                        }
                        if ("RESP_118".equals(codigo)) {
                            ee.setRESP_118(transformaSiNoNA(valor));
                        }
                        if ("RESP_119_01".equals(codigo)) {
                            ee.setRESP_119_01(tranformacionSuficiente(valor));
                        }
                        if ("RESP_119_02".equals(codigo)) {
                            ee.setRESP_119_02(valor);
                        }
                        if ("RESP_119_03".equals(codigo)) {
                            ee.setRESP_119_03(valor);
                        }
                        if ("RESP_120_01".equals(codigo)) {
                            ee.setRESP_120_01(transformaSiNoNA(valor));
                        }
                        if ("RESP_120_02".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("TC", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            ee.setRESP_120_02(valor);
                        }
                        if ("RESP_120_03".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("TC", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            ee.setRESP_120_03(valor);
                        }
                        if ("RESP_120_04".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("TC", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            ee.setRESP_120_04(valor);
                        }
                        if ("RESP_120_05".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("TC", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            ee.setRESP_120_05(valor);
                        }
                        if ("RESP_120_06".equals(codigo)) {
                            ee.setRESP_120_06(valor);
                        }
                        if ("RESP_121_01".equals(codigo)) {
                            ee.setRESP_121_01(transformaSiNoNA(valor));
                        }
                        if ("RESP_121_02".equals(codigo)) {
                            ee.setRESP_121_02(valor);
                        }
                        if ("RESP_121_03".equals(codigo)) {
                            ee.setRESP_121_03(valor);
                        }
                        if ("RESP_122_01".equals(codigo)) {
                            ee.setRESP_122_01(transformaSiNoNA(valor));
                        }
                        if ("RESP_122_02".equals(codigo)) {
                            ee.setRESP_122_02(valor);
                        }
                        if ("RESP_122_03".equals(codigo)) {
                            ee.setRESP_122_03(valor);
                        }
                        if ("RESP_123_01".equals(codigo)) {
                            ee.setRESP_123_01(transformaSiNoNA(valor));
                        }
                        if ("RESP_123_02".equals(codigo)) {
                            ee.setRESP_123_02(valor);
                        }
                        if ("RESP_123_03".equals(codigo)) {
                            ee.setRESP_123_03(valor);
                        }
                        if ("RESP_124_01".equals(codigo)) {
                            ee.setRESP_124_01(transformaSiNoNA(valor));
                        }
                        if ("RESP_124_02".equals(codigo)) {
                            ee.setRESP_124_02(valor);
                        }
                        if ("RESP_124_03".equals(codigo)) {
                            ee.setRESP_124_03(valor);
                        }
                        if ("RESP_125_01".equals(codigo)) {
                            ee.setRESP_125_01(transformaSiNoNA(valor));
                        }
                        if ("RESP_125_02".equals(codigo)) {
                            ee.setRESP_125_02(valor);
                        }
                        if ("RESP_125_03".equals(codigo)) {
                            ee.setRESP_125_03(valor);
                        }
                        if ("RESP_126_01".equals(codigo)) {
                            ee.setRESP_126_01(transformaSiNoNA(valor));
                        }
                        if ("RESP_126_02".equals(codigo)) {
                            ee.setRESP_126_02(valor);
                        }
                        if ("RESP_126_03".equals(codigo)) {
                            ee.setRESP_126_03(valor);
                        }
                        if ("RESP_127_01".equals(codigo)) {
                            ee.setRESP_127_01(transformaSiNoNA(valor));
                        }
                        if ("RESP_127_02".equals(codigo)) {
                            ee.setRESP_127_02(valor);
                        }
                        if ("RESP_127_03".equals(codigo)) {
                            ee.setRESP_127_03(valor);
                        }
                        if ("RESP_128_01".equals(codigo)) {
                            ee.setRESP_128_01(transformaSiNoNA(valor));
                        }
                        if ("RESP_128_02".equals(codigo)) {
                            ee.setRESP_128_02(valor);
                        }
                        if ("RESP_128_03".equals(codigo)) {
                            ee.setRESP_128_03(valor);
                        }
                        if ("RESP_129_01".equals(codigo)) {
                            ee.setRESP_129_01(transformaSiNoNA(valor));
                        }
                        if ("RESP_129_02".equals(codigo)) {
                            ee.setRESP_129_02(valor);
                        }
                        if ("RESP_129_03".equals(codigo)) {
                            ee.setRESP_129_03(valor);
                        }
                        if ("RESP_130_01".equals(codigo)) {
                            ee.setRESP_130_01(valor);
                        }
                        if ("RESP_130_02".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("DASASLC", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            ee.setRESP_130_02(valor);
                        }
                        if ("RESP_130_03".equals(codigo)) {
                            ee.setRESP_130_03(valor);
                        }
                        if ("RESP_130_04".equals(codigo)) {
                            ee.setRESP_130_04(valor);
                        }
                        if ("RESP_130_05".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("DASASLC", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            ee.setRESP_130_05(valor);
                        }
                        if ("RESP_130_06".equals(codigo)) {
                            ee.setRESP_130_06(valor);
                        }
                        if ("RESP_130_07".equals(codigo)) {
                            ee.setRESP_130_07(valor);
                        }
                        if ("RESP_130_08".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("DASASLC", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            ee.setRESP_130_08(valor);
                        }
                        if ("RESP_130_09".equals(codigo)) {
                            ee.setRESP_130_09(valor);
                        }
                        if ("RESP_130_10".equals(codigo)) {
                            ee.setRESP_130_10(valor);
                        }
                        if ("RESP_130_11".equals(codigo)) {
                            ee.setRESP_130_11(valor);
                        }
                        if ("RESP_130_12".equals(codigo)) {
                            ee.setRESP_130_12(valor);
                        }
                        if ("RESP_131_01".equals(codigo)) {
                            ee.setRESP_131_01(valor);
                        }
                        if ("RESP_131_02".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("DASASLC", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            ee.setRESP_131_02(valor);
                        }
                        if ("RESP_131_03".equals(codigo)) {
                            ee.setRESP_131_03(valor);
                        }
                        if ("RESP_131_04".equals(codigo)) {
                            ee.setRESP_131_04(valor);
                        }
                        if ("RESP_131_05".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("DASASLC", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            ee.setRESP_131_05(valor);
                        }
                        if ("RESP_131_06".equals(codigo)) {
                            ee.setRESP_131_06(valor);
                        }
                        if ("RESP_132_01".equals(codigo)) {
                            ee.setRESP_132_01(valor);
                        }
                        if ("RESP_132_02".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("DASASLC", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            ee.setRESP_132_02(valor);
                        }
                        if ("RESP_132_03".equals(codigo)) {
                            ee.setRESP_132_03(valor);
                        }
                        if ("RESP_132_04".equals(codigo)) {
                            ee.setRESP_132_04(valor);
                        }
                        if ("RESP_132_05".equals(codigo)) {
                            ee.setRESP_132_05(valor);
                        }
                        if ("RESP_132_06".equals(codigo)) {
                            ee.setRESP_132_06(valor);
                        }
                        if ("RESP_133_01".equals(codigo)) {
                            ee.setRESP_133_01(valor);
                        }
                        if ("RESP_133_02".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("DASASLC", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            ee.setRESP_133_02(valor);
                        }
                        if ("RESP_133_03".equals(codigo)) {
                            ee.setRESP_133_03(valor);
                        }
                        if ("RESP_134_01".equals(codigo)) {
                            ee.setRESP_134_01(valor);
                        }
                        if ("RESP_134_02".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("DASASLC", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            ee.setRESP_134_02(valor);
                        }
                        if ("RESP_134_03".equals(codigo)) {
                            ee.setRESP_134_03(valor);
                        }
                        if ("RESP_135_01".equals(codigo)) {
                            ee.setRESP_135_01(valor);
                        }
                        if ("RESP_135_02".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("ME", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            ee.setRESP_135_02(valor);
                        }
                        if ("RESP_135_03".equals(codigo)) {
                            ee.setRESP_135_03(valor);
                        }
                        if ("RESP_135_04".equals(codigo)) {
                            ee.setRESP_135_04(valor);
                        }
                        if ("RESP_135_05".equals(codigo)) {
                            ee.setRESP_135_05(valor);
                        }
                        if ("RESP_135_06".equals(codigo)) {
                            ee.setRESP_135_06(valor);
                        }
                        if ("RESP_136_01".equals(codigo)) {
                            ee.setRESP_136_01(valor);
                        }
                        if ("RESP_136_02".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("ED", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            ee.setRESP_136_02(valor);
                        }
                        if ("RESP_136_03".equals(codigo)) {
                            ee.setRESP_136_03(valor);
                        }
                        if ("RESP_136_04".equals(codigo)) {
                            ee.setRESP_136_04(valor);
                        }
                        if ("RESP_136_05".equals(codigo)) {
                            ee.setRESP_136_05(valor);
                        }
                        if ("RESP_136_06".equals(codigo)) {
                            ee.setRESP_136_06(valor);
                        }
                        if ("RESP_137_01".equals(codigo)) {
                            ee.setRESP_137_01(transformaSiNoNA(valor));
                        }
                        if ("RESP_137_02".equals(codigo)) {
                            ee.setRESP_137_02(valor);
                        }
                    }
                }
                listaEE.add(ee);
            }
        } catch (Exception e) {
            ManejadorErrores.ingresarExcepcionEnLog(e, ConsultaInstrumentoBean.class);
        }
        return listaEE;
    }

    public List<Croquis> obtenerCroquis(Long idDigInstrumento, List<String> respuestasCroquis) {
        List<Croquis> listaCroquis = new ArrayList<Croquis>();
        try {
            List<Object[]> listaRespuesta = facBasica.obtenerRespuestasInstrumentoDig(idDigInstrumento, respuestasCroquis);

            Map<String, Long> elemNumCroquis = new HashMap<String, Long>();
            List<Object[]> listaRespuesta2 = new ArrayList<Object[]>();
            String codigo = "";
            String valor = "";
            Long idElem = null;
            List<Long> numsCroquis = new ArrayList<Long>();
            for (Object[] fila : listaRespuesta) {
                int i = 0;
                codigo = (String) fila[i++];
                valor = (String) fila[i++];
                idElem = new Long(fila[i++] + "");
                if (!numsCroquis.contains(idElem)) {
                    //Guardamos el idElemento
                    numsCroquis.add(idElem);
                }
                listaRespuesta2.add(fila);
            }

            Collections.sort(numsCroquis);

            Croquis croquis = new Croquis();
            for (Long idSelec : numsCroquis) {
                for (Object[] fila : listaRespuesta2) {
                    int i = 0;
                    codigo = (String) fila[i++];
                    valor = (String) fila[i++];
                    idElem = new Long(fila[i++] + "");
                    if (idElem.equals(idSelec)) {
                        if ("RESP_139".equals(codigo)) {
                            croquis.setRESP_139(valor);
                        }
                        if ("RESP_138".equals(codigo)) {
                            croquis.setRESP_138(valor);
                        }
                        if ("RESP_140".equals(codigo)) {
                            croquis.setRESP_140(valor);
                        }
                        if ("RESP_141_01".equals(codigo)) {
                            croquis.setRESP_141(facBasica.consultaRutaAdjuntoCroquis(idDigInstrumento, codigo, idElem));
                        }
                    }
                }
                listaCroquis.add(croquis);
            }
        } catch (Exception e) {
            ManejadorErrores.ingresarExcepcionEnLog(e, ConsultaInstrumentoBean.class);
        }
        return listaCroquis;
    }

    public List<AmbMismaTipo> obtenerAmbMismaTipo(Long idDigInstrumento, List<String> respuestasAmbMismaTipo) {
        List<AmbMismaTipo> listaAmbMismaTipo = new ArrayList<AmbMismaTipo>();
        try {
            List<Object[]> listaRespuesta = facBasica.obtenerRespuestasInstrumentoDig(idDigInstrumento, respuestasAmbMismaTipo);

            Map<String, Long> elemNumAmbMismaTipo = new HashMap<String, Long>();
            List<Object[]> listaRespuesta2 = new ArrayList<Object[]>();
            String codigo = "";
            String valor = "";
            Long idElem = null;
            List<Long> numsAmbMismaTipo = new ArrayList<Long>();
            for (Object[] fila : listaRespuesta) {
                int i = 0;
                codigo = (String) fila[i++];
                valor = (String) fila[i++];
                idElem = new Long(fila[i++] + "");
                if (!numsAmbMismaTipo.contains(idElem)) {
                    //Guardamos el idElemento
                    numsAmbMismaTipo.add(idElem);
                }
                listaRespuesta2.add(fila);
            }

            Collections.sort(numsAmbMismaTipo);

            AmbMismaTipo ambMismaTipo = new AmbMismaTipo();
            int aux = 0;
            for (Long idSelec : numsAmbMismaTipo) {
                aux++;
                for (Object[] fila : listaRespuesta2) {
                    int i = 0;
                    codigo = (String) fila[i++];
                    valor = (String) fila[i++];
                    idElem = new Long(fila[i++] + "");
                    if (idElem.equals(idSelec)) {
                        if ("RESP_142".equals(codigo)) {
                            if (aux == 1) {
                                ambMismaTipo.setUNO_RESP_142(valor);
                            } else if (aux == 2) {
                                ambMismaTipo.setDOS_RESP_142(valor);
                            }
                        }
                        if ("RESP_143".equals(codigo)) {
                            if (aux == 1) {
                                ambMismaTipo.setUNO_RESP_143(valor);
                            } else if (aux == 2) {
                                ambMismaTipo.setDOS_RESP_143(valor);
                            }
                        }
                        if ("RESP_144".equals(codigo)) {
                            if (aux == 1) {
                                ambMismaTipo.setUNO_RESP_144(valor);
                            } else if (aux == 2) {
                                ambMismaTipo.setDOS_RESP_144(valor);
                            }
                        }
                        if ("RESP_145_01".equals(codigo)) {
                            if (aux == 1) {
                                ambMismaTipo.setUNO_RESP_145_01(valor);
                            } else if (aux == 2) {
                                ambMismaTipo.setDOS_RESP_145_01(valor);
                            }
                        }
                        if ("RESP_145_02".equals(codigo)) {
                            if (aux == 1) {
                                ambMismaTipo.setUNO_RESP_145_02(valor);
                            } else if (aux == 2) {
                                ambMismaTipo.setDOS_RESP_145_02(valor);
                            }
                        }
                        if ("RESP_145_03".equals(codigo)) {
                            if (aux == 1) {
                                ambMismaTipo.setUNO_RESP_145_03(valor);
                            } else if (aux == 2) {
                                ambMismaTipo.setDOS_RESP_145_03(valor);
                            }
                        }
                        if ("RESP_145_04".equals(codigo)) {
                            if (aux == 1) {
                                ambMismaTipo.setUNO_RESP_145_04(valor);
                            } else if (aux == 2) {
                                ambMismaTipo.setDOS_RESP_145_04(valor);
                            }
                        }
                        if ("RESP_145_05".equals(codigo)) {
                            if (aux == 1) {
                                ambMismaTipo.setUNO_RESP_145_05(valor);
                            } else if (aux == 2) {
                                ambMismaTipo.setDOS_RESP_145_05(valor);
                            }
                        }
                        if ("RESP_145_06".equals(codigo)) {
                            if (aux == 1) {
                                ambMismaTipo.setUNO_RESP_145_06(valor);
                            } else if (aux == 2) {
                                ambMismaTipo.setDOS_RESP_145_06(valor);
                            }
                        }
                        if ("RESP_145_07".equals(codigo)) {
                            if (aux == 1) {
                                ambMismaTipo.setUNO_RESP_145_07(valor);
                            } else if (aux == 2) {
                                ambMismaTipo.setDOS_RESP_145_07(valor);
                            }
                        }
                        if ("RESP_145_08".equals(codigo)) {
                            if (aux == 1) {
                                ambMismaTipo.setUNO_RESP_145_08(valor);
                            } else if (aux == 2) {
                                ambMismaTipo.setDOS_RESP_145_08(valor);
                            }
                        }
                        if ("RESP_145_09".equals(codigo)) {
                            if (aux == 1) {
                                ambMismaTipo.setUNO_RESP_145_09(valor);
                            } else if (aux == 2) {
                                ambMismaTipo.setDOS_RESP_145_09(valor);
                            }
                        }
                        if ("RESP_145_10".equals(codigo)) {
                            if (aux == 1) {
                                ambMismaTipo.setUNO_RESP_145_10(valor);
                            } else if (aux == 2) {
                                ambMismaTipo.setDOS_RESP_145_10(valor);
                            }
                        }
                        if ("RESP_145_11".equals(codigo)) {
                            if (aux == 1) {
                                ambMismaTipo.setUNO_RESP_145_11(valor);
                            } else if (aux == 2) {
                                ambMismaTipo.setDOS_RESP_145_11(valor);
                            }
                        }
                        if ("RESP_145_12".equals(codigo)) {
                            if (aux == 1) {
                                ambMismaTipo.setUNO_RESP_145_12(valor);
                            } else if (aux == 2) {
                                ambMismaTipo.setDOS_RESP_145_12(valor);
                            }
                        }
                        if ("RESP_145_13".equals(codigo)) {
                            if (aux == 1) {
                                ambMismaTipo.setUNO_RESP_145_13(valor);
                            } else if (aux == 2) {
                                ambMismaTipo.setDOS_RESP_145_13(valor);
                            }
                        }
                        if ("RESP_145_14".equals(codigo)) {
                            if (aux == 1) {
                                ambMismaTipo.setUNO_RESP_145_14(valor);
                            } else if (aux == 2) {
                                ambMismaTipo.setDOS_RESP_145_14(valor);
                            }
                        }
                        if ("RESP_146_01".equals(codigo)) {
                            if (aux == 1) {
                                ambMismaTipo.setUNO_RESP_146_01(valor);
                            } else if (aux == 2) {
                                ambMismaTipo.setDOS_RESP_146_01(valor);
                            }
                        }
                        if ("RESP_146_02".equals(codigo)) {
                            if (aux == 1) {
                                ambMismaTipo.setUNO_RESP_146_02(valor);
                            } else if (aux == 2) {
                                ambMismaTipo.setDOS_RESP_146_02(valor);
                            }
                        }
                        if ("RESP_146_03".equals(codigo)) {
                            if (aux == 1) {
                                ambMismaTipo.setUNO_RESP_146_03(valor);
                            } else if (aux == 2) {
                                ambMismaTipo.setDOS_RESP_146_03(valor);
                            }
                        }
                        if ("RESP_146_04".equals(codigo)) {
                            if (aux == 1) {
                                ambMismaTipo.setUNO_RESP_146_04(valor);
                            } else if (aux == 2) {
                                ambMismaTipo.setDOS_RESP_146_04(valor);
                            }
                        }
                        if ("RESP_146_05".equals(codigo)) {
                            if (aux == 1) {
                                ambMismaTipo.setUNO_RESP_146_05(valor);
                            } else if (aux == 2) {
                                ambMismaTipo.setDOS_RESP_146_05(valor);
                            }
                        }
                        if ("RESP_146_06".equals(codigo)) {
                            if (aux == 1) {
                                ambMismaTipo.setUNO_RESP_146_06(valor);
                            } else if (aux == 2) {
                                ambMismaTipo.setDOS_RESP_146_06(valor);
                            }
                        }
                        if ("RESP_146_07".equals(codigo)) {
                            if (aux == 1) {
                                ambMismaTipo.setUNO_RESP_146_07(valor);
                            } else if (aux == 2) {
                                ambMismaTipo.setDOS_RESP_146_07(valor);
                            }
                        }
                        if ("RESP_146_08".equals(codigo)) {
                            if (aux == 1) {
                                ambMismaTipo.setUNO_RESP_146_08(valor);
                            } else if (aux == 2) {
                                ambMismaTipo.setDOS_RESP_146_08(valor);
                            }
                        }
                        if ("RESP_146_09".equals(codigo)) {
                            if (aux == 1) {
                                ambMismaTipo.setUNO_RESP_146_09(valor);
                            } else if (aux == 2) {
                                ambMismaTipo.setDOS_RESP_146_09(valor);
                            }
                        }
                        if ("RESP_146_10".equals(codigo)) {
                            if (aux == 1) {
                                ambMismaTipo.setUNO_RESP_146_10(valor);
                            } else if (aux == 2) {
                                ambMismaTipo.setDOS_RESP_146_10(valor);
                            }
                        }
                        if ("RESP_146_11".equals(codigo)) {
                            if (aux == 1) {
                                ambMismaTipo.setUNO_RESP_146_11(valor);
                            } else if (aux == 2) {
                                ambMismaTipo.setDOS_RESP_146_11(valor);
                            }
                        }
                        if ("RESP_146_12".equals(codigo)) {
                            if (aux == 1) {
                                ambMismaTipo.setUNO_RESP_146_12(valor);
                            } else if (aux == 2) {
                                ambMismaTipo.setDOS_RESP_146_12(valor);
                            }
                        }
                        if ("RESP_146_13".equals(codigo)) {
                            if (aux == 1) {
                                ambMismaTipo.setUNO_RESP_146_13(valor);
                            } else if (aux == 2) {
                                ambMismaTipo.setDOS_RESP_146_13(valor);
                            }
                        }
                        if ("RESP_146_14".equals(codigo)) {
                            if (aux == 1) {
                                ambMismaTipo.setUNO_RESP_146_14(valor);
                            } else if (aux == 2) {
                                ambMismaTipo.setDOS_RESP_146_14(valor);
                            }
                        }
                        if ("RESP_147_01".equals(codigo)) {
                            if (aux == 1) {
                                ambMismaTipo.setUNO_RESP_147_01(valor);
                            } else if (aux == 2) {
                                ambMismaTipo.setDOS_RESP_147_01(valor);
                            }
                        }
                        if ("RESP_147_02".equals(codigo)) {
                            if (aux == 1) {
                                ambMismaTipo.setUNO_RESP_147_02(valor);
                            } else if (aux == 2) {
                                ambMismaTipo.setDOS_RESP_147_02(valor);
                            }
                        }
                        if ("RESP_147_03".equals(codigo)) {
                            if (aux == 1) {
                                ambMismaTipo.setUNO_RESP_147_03(valor);
                            } else if (aux == 2) {
                                ambMismaTipo.setDOS_RESP_147_03(valor);
                            }
                        }
                        if ("RESP_147_04".equals(codigo)) {
                            if (aux == 1) {
                                ambMismaTipo.setUNO_RESP_147_04(valor);
                            } else if (aux == 2) {
                                ambMismaTipo.setDOS_RESP_147_04(valor);
                            }
                        }
                        if ("RESP_147_05".equals(codigo)) {
                            if (aux == 1) {
                                ambMismaTipo.setUNO_RESP_147_05(valor);
                            } else if (aux == 2) {
                                ambMismaTipo.setDOS_RESP_147_05(valor);
                            }
                        }
                        if ("RESP_147_06".equals(codigo)) {
                            if (aux == 1) {
                                ambMismaTipo.setUNO_RESP_147_06(valor);
                            } else if (aux == 2) {
                                ambMismaTipo.setDOS_RESP_147_06(valor);
                            }
                        }
                        if ("RESP_147_07".equals(codigo)) {
                            if (aux == 1) {
                                ambMismaTipo.setUNO_RESP_147_07(valor);
                            } else if (aux == 2) {
                                ambMismaTipo.setDOS_RESP_147_07(valor);
                            }
                        }
                        if ("RESP_147_08".equals(codigo)) {
                            if (aux == 1) {
                                ambMismaTipo.setUNO_RESP_147_08(valor);
                            } else if (aux == 2) {
                                ambMismaTipo.setDOS_RESP_147_08(valor);
                            }
                        }
                        if ("RESP_147_09".equals(codigo)) {
                            if (aux == 1) {
                                ambMismaTipo.setUNO_RESP_147_09(valor);
                            } else if (aux == 2) {
                                ambMismaTipo.setDOS_RESP_147_09(valor);
                            }
                        }
                        if ("RESP_147_10".equals(codigo)) {
                            if (aux == 1) {
                                ambMismaTipo.setUNO_RESP_147_10(valor);
                            } else if (aux == 2) {
                                ambMismaTipo.setDOS_RESP_147_10(valor);
                            }
                        }
                        if ("RESP_147_11".equals(codigo)) {
                            if (aux == 1) {
                                ambMismaTipo.setUNO_RESP_147_11(valor);
                            } else if (aux == 2) {
                                ambMismaTipo.setDOS_RESP_147_11(valor);
                            }
                        }
                        if ("RESP_147_12".equals(codigo)) {
                            if (aux == 1) {
                                ambMismaTipo.setUNO_RESP_147_12(valor);
                            } else if (aux == 2) {
                                ambMismaTipo.setDOS_RESP_147_12(valor);
                            }
                        }
                        if ("RESP_147_13".equals(codigo)) {
                            if (aux == 1) {
                                ambMismaTipo.setUNO_RESP_147_13(valor);
                            } else if (aux == 2) {
                                ambMismaTipo.setDOS_RESP_147_13(valor);
                            }
                        }
                        if ("RESP_147_14".equals(codigo)) {
                            if (aux == 1) {
                                ambMismaTipo.setUNO_RESP_147_14(valor);
                            } else if (aux == 2) {
                                ambMismaTipo.setDOS_RESP_147_14(valor);
                            }
                        }
                    }
                }
                if (aux == 2) {
                    listaAmbMismaTipo.add(ambMismaTipo);
                    ambMismaTipo = new AmbMismaTipo();
                    aux = 0;
                }
            }
            if (ambMismaTipo.getUNO_RESP_142() != null && !"".equals(ambMismaTipo.getUNO_RESP_142())) {
                listaAmbMismaTipo.add(ambMismaTipo);
            }
        } catch (Exception e) {
            ManejadorErrores.ingresarExcepcionEnLog(e, ConsultaInstrumentoBean.class);
        }
        return listaAmbMismaTipo;
    }

    public List<Observaciones> obtenerObservaciones(Long idDigInstrumento, List<String> respuestasObservaciones) {
        List<Observaciones> listaObservaciones = new ArrayList<Observaciones>();
        try {
            List<Object[]> listaRespuesta = facBasica.obtenerRespuestasInstrumentoDig(idDigInstrumento, respuestasObservaciones);

            Map<Long, Long> elemNumObservaciones = new HashMap<Long, Long>();
            String codigo = "";
            String valor = "";
            Long idElem = null;
            List<Long> numsObservaciones = new ArrayList<Long>();
            for (Object[] fila : listaRespuesta) {
                int i = 0;
                codigo = (String) fila[i++];
                valor = (String) fila[i++];
                idElem = new Long(fila[i++] + "");
                if ("RESP_187".equals(codigo)) {
                    //Guardamos el idElemento y el numero de la observaciones
                    elemNumObservaciones.put(idElem, idElem);
                    numsObservaciones.add(idElem);
                }
            }
            Collections.sort(numsObservaciones);
            for (Long num : numsObservaciones) {
                Long idSelec = elemNumObservaciones.get(num);
                Observaciones observaciones = new Observaciones();
                for (Object[] fila : listaRespuesta) {
                    int i = 0;
                    codigo = (String) fila[i++];
                    valor = (String) fila[i++];
                    idElem = new Long(fila[i++] + "");
                    if (idElem.equals(idSelec)) {
                        if ("RESP_187".equals(codigo)) {
                            observaciones.setRESP_187(valor);
                        }
                        if ("RESP_188".equals(codigo)) {
                            observaciones.setRESP_188(valor);
                        }
                        if ("RESP_189".equals(codigo)) {
                            observaciones.setRESP_189(valor);
                        }
                        if ("RESP_190".equals(codigo)) {
                            observaciones.setRESP_190(valor);
                        }
                    }
                }
                listaObservaciones.add(observaciones);
            }
        } catch (Exception e) {
            ManejadorErrores.ingresarExcepcionEnLog(e, ConsultaInstrumentoBean.class);
        }
        return listaObservaciones;
    }

    public List<DatosAdic> obtenerDatosAdic(Long idDigInstrumento, List<String> respuestasDatosAdic) {
        List<DatosAdic> listaDatosAdic = new ArrayList<DatosAdic>();
        try {
            List<Object[]> listaRespuesta = facBasica.obtenerRespuestasInstrumentoDig(idDigInstrumento, respuestasDatosAdic);

            Map<String, Long> elemNumDatosAdic = new HashMap<String, Long>();
            List<Object[]> listaRespuesta2 = new ArrayList<Object[]>();
            String codigo = "";
            String valor = "";
            Long idElem = null;
            List<Long> numsDatosAdic = new ArrayList<Long>();
            for (Object[] fila : listaRespuesta) {
                int i = 0;
                codigo = (String) fila[i++];
                valor = (String) fila[i++];
                idElem = new Long(fila[i++] + "");
                if (!numsDatosAdic.contains(idElem)) {
                    //Guardamos el idElemento
                    numsDatosAdic.add(idElem);
                }
                listaRespuesta2.add(fila);
            }

            Collections.sort(numsDatosAdic);

            for (Long idSelec : numsDatosAdic) {
                DatosAdic datosAdic = new DatosAdic();
                for (Object[] fila : listaRespuesta2) {
                    int i = 0;
                    codigo = (String) fila[i++];
                    valor = (String) fila[i++];
                    idElem = new Long(fila[i++] + "");
                    if (idElem.equals(idSelec)) {
                        if ("RESP_193".equals(codigo)) {
                            datosAdic.setRESP_193(valor);
                        }
                        if ("RESP_194".equals(codigo)) {
                            datosAdic.setRESP_194(valor);
                        }
                        if ("RESP_195".equals(codigo)) {
                            datosAdic.setRESP_195(valor);
                        }
                        if ("RESP_291".equals(codigo)) {
                            datosAdic.setRESP_291(valor);
                        }
                        if ("RESP_196".equals(codigo)) {
                            datosAdic.setRESP_196(valor);
                        }
                        if ("RESP_197".equals(codigo)) {
                            datosAdic.setRESP_197(valor);
                        }
                        if ("RESP_292".equals(codigo)) {
                            datosAdic.setRESP_292(valor);
                        }
                        if ("RESP_293".equals(codigo)) {
                            datosAdic.setRESP_293(valor);
                        }
                        if ("RESP_198_01".equals(codigo)) {
                            datosAdic.setRESP_198_01(valor);
                        }
                        if ("RESP_198_02".equals(codigo)) {
                            datosAdic.setRESP_198_02(valor);
                        }
                        if ("RESP_198_03".equals(codigo)) {
                            datosAdic.setRESP_198_03(valor);
                        }
                        if ("RESP_198_04".equals(codigo)) {
                            datosAdic.setRESP_198_04(valor);
                        }
                        if ("RESP_198_05".equals(codigo)) {
                            datosAdic.setRESP_198_05(valor);
                        }
                        if ("RESP_198_06".equals(codigo)) {
                            datosAdic.setRESP_198_06(valor);
                        }
                        if ("RESP_198_07".equals(codigo)) {
                            datosAdic.setRESP_198_07(valor);
                        }
                        if ("RESP_198_08".equals(codigo)) {
                            datosAdic.setRESP_198_08(valor);
                        }
                        if ("RESP_198_09".equals(codigo)) {
                            datosAdic.setRESP_198_09(valor);
                        }
                        if ("RESP_198_10".equals(codigo)) {
                            datosAdic.setRESP_198_10(valor);
                        }
                        if ("RESP_199_01".equals(codigo)) {
                            datosAdic.setRESP_199_01(valor);
                        }
                        if ("RESP_199_02".equals(codigo)) {
                            datosAdic.setRESP_199_02(valor);
                        }
                        if ("RESP_199_03".equals(codigo)) {
                            datosAdic.setRESP_199_03(valor);
                        }
                        if ("RESP_199_04".equals(codigo)) {
                            datosAdic.setRESP_199_04(valor);
                        }
                        if ("RESP_199_05".equals(codigo)) {
                            datosAdic.setRESP_199_05(valor);
                        }
                        if ("RESP_199_06".equals(codigo)) {
                            datosAdic.setRESP_199_06(valor);
                        }
                        if ("RESP_199_07".equals(codigo)) {
                            datosAdic.setRESP_199_07(valor);
                        }
                        if ("RESP_199_08".equals(codigo)) {
                            datosAdic.setRESP_199_08(valor);
                        }
                        if ("RESP_199_09".equals(codigo)) {
                            datosAdic.setRESP_199_09(valor);
                        }
                        if ("RESP_199_10".equals(codigo)) {
                            datosAdic.setRESP_199_10(valor);
                        }
                        if ("RESP_200_01".equals(codigo)) {
                            datosAdic.setRESP_200_01(valor);
                        }
                        if ("RESP_200_02".equals(codigo)) {
                            datosAdic.setRESP_200_02(valor);
                        }
                        if ("RESP_200_03".equals(codigo)) {
                            datosAdic.setRESP_200_03(valor);
                        }
                        if ("RESP_200_04".equals(codigo)) {
                            datosAdic.setRESP_200_04(valor);
                        }
                        if ("RESP_200_05".equals(codigo)) {
                            datosAdic.setRESP_200_05(valor);
                        }
                        if ("RESP_200_06".equals(codigo)) {
                            datosAdic.setRESP_200_06(valor);
                        }
                        if ("RESP_200_07".equals(codigo)) {
                            datosAdic.setRESP_200_07(valor);
                        }
                        if ("RESP_200_08".equals(codigo)) {
                            datosAdic.setRESP_200_08(valor);
                        }
                        if ("RESP_200_09".equals(codigo)) {
                            datosAdic.setRESP_200_09(valor);
                        }
                        if ("RESP_200_10".equals(codigo)) {
                            datosAdic.setRESP_200_10(valor);
                        }
                        if ("RESP_201_01".equals(codigo)) {
                            datosAdic.setRESP_201_01(valor);
                        }
                        if ("RESP_201_02".equals(codigo)) {
                            datosAdic.setRESP_201_02(valor);
                        }
                        if ("RESP_201_03".equals(codigo)) {
                            datosAdic.setRESP_201_03(valor);
                        }
                        if ("RESP_201_04".equals(codigo)) {
                            datosAdic.setRESP_201_04(valor);
                        }
                        if ("RESP_201_05".equals(codigo)) {
                            datosAdic.setRESP_201_05(valor);
                        }
                        if ("RESP_201_06".equals(codigo)) {
                            datosAdic.setRESP_201_06(valor);
                        }
                        if ("RESP_201_07".equals(codigo)) {
                            datosAdic.setRESP_201_07(valor);
                        }
                        if ("RESP_201_08".equals(codigo)) {
                            datosAdic.setRESP_201_08(valor);
                        }
                        if ("RESP_201_09".equals(codigo)) {
                            datosAdic.setRESP_201_09(valor);
                        }
                        if ("RESP_201_10".equals(codigo)) {
                            datosAdic.setRESP_201_10(valor);
                        }
                        if ("RESP_202_01".equals(codigo)) {
                            datosAdic.setRESP_202_01(valor);
                        }
                        if ("RESP_202_02".equals(codigo)) {
                            datosAdic.setRESP_202_02(valor);
                        }
                        if ("RESP_202_03".equals(codigo)) {
                            datosAdic.setRESP_202_03(valor);
                        }
                        if ("RESP_202_04".equals(codigo)) {
                            datosAdic.setRESP_202_04(valor);
                        }
                        if ("RESP_202_05".equals(codigo)) {
                            datosAdic.setRESP_202_05(valor);
                        }
                        if ("RESP_202_06".equals(codigo)) {
                            datosAdic.setRESP_202_06(valor);
                        }
                        if ("RESP_202_07".equals(codigo)) {
                            datosAdic.setRESP_202_07(valor);
                        }
                        if ("RESP_202_08".equals(codigo)) {
                            datosAdic.setRESP_202_08(valor);
                        }
                        if ("RESP_202_09".equals(codigo)) {
                            datosAdic.setRESP_202_09(valor);
                        }
                        if ("RESP_202_10".equals(codigo)) {
                            datosAdic.setRESP_202_10(valor);
                        }
                        if ("RESP_203_01".equals(codigo)) {
                            datosAdic.setRESP_203_01(valor);
                        }
                        if ("RESP_203_02".equals(codigo)) {
                            datosAdic.setRESP_203_02(valor);
                        }
                        if ("RESP_203_03".equals(codigo)) {
                            datosAdic.setRESP_203_03(valor);
                        }
                        if ("RESP_203_04".equals(codigo)) {
                            datosAdic.setRESP_203_04(valor);
                        }
                        if ("RESP_203_05".equals(codigo)) {
                            datosAdic.setRESP_203_05(valor);
                        }
                        if ("RESP_203_06".equals(codigo)) {
                            datosAdic.setRESP_203_06(valor);
                        }
                        if ("RESP_203_07".equals(codigo)) {
                            datosAdic.setRESP_203_07(valor);
                        }
                        if ("RESP_203_08".equals(codigo)) {
                            datosAdic.setRESP_203_08(valor);
                        }
                        if ("RESP_203_09".equals(codigo)) {
                            datosAdic.setRESP_203_09(valor);
                        }
                        if ("RESP_203_10".equals(codigo)) {
                            datosAdic.setRESP_203_10(valor);
                        }
                        if ("RESP_204_01".equals(codigo)) {
                            datosAdic.setRESP_204_01(valor);
                        }
                        if ("RESP_204_02".equals(codigo)) {
                            datosAdic.setRESP_204_02(valor);
                        }
                        if ("RESP_204_03".equals(codigo)) {
                            datosAdic.setRESP_204_03(valor);
                        }
                        if ("RESP_204_04".equals(codigo)) {
                            datosAdic.setRESP_204_04(valor);
                        }
                        if ("RESP_204_05".equals(codigo)) {
                            datosAdic.setRESP_204_05(valor);
                        }
                        if ("RESP_204_06".equals(codigo)) {
                            datosAdic.setRESP_204_06(valor);
                        }
                        if ("RESP_204_07".equals(codigo)) {
                            datosAdic.setRESP_204_07(valor);
                        }
                        if ("RESP_204_08".equals(codigo)) {
                            datosAdic.setRESP_204_08(valor);
                        }
                        if ("RESP_204_09".equals(codigo)) {
                            datosAdic.setRESP_204_09(valor);
                        }
                        if ("RESP_204_10".equals(codigo)) {
                            datosAdic.setRESP_204_10(valor);
                        }
                        if ("RESP_205_01".equals(codigo)) {
                            datosAdic.setRESP_205_01(valor);
                        }
                        if ("RESP_205_02".equals(codigo)) {
                            datosAdic.setRESP_205_02(valor);
                        }
                        if ("RESP_205_03".equals(codigo)) {
                            datosAdic.setRESP_205_03(valor);
                        }
                        if ("RESP_205_04".equals(codigo)) {
                            datosAdic.setRESP_205_04(valor);
                        }
                        if ("RESP_205_05".equals(codigo)) {
                            datosAdic.setRESP_205_05(valor);
                        }
                        if ("RESP_205_06".equals(codigo)) {
                            datosAdic.setRESP_205_06(valor);
                        }
                        if ("RESP_205_07".equals(codigo)) {
                            datosAdic.setRESP_205_07(valor);
                        }
                        if ("RESP_205_08".equals(codigo)) {
                            datosAdic.setRESP_205_08(valor);
                        }
                        if ("RESP_205_09".equals(codigo)) {
                            datosAdic.setRESP_205_09(valor);
                        }
                        if ("RESP_205_10".equals(codigo)) {
                            datosAdic.setRESP_205_10(valor);
                        }
                        if ("RESP_206".equals(codigo)) {
                            datosAdic.setRESP_206(valor);
                        }
                        if ("RESP_207_01".equals(codigo)) {
                            datosAdic.setRESP_207_01(valor);
                        }
                        if ("RESP_207_02".equals(codigo)) {
                            datosAdic.setRESP_207_02(valor);
                        }
                        if ("RESP_207_03".equals(codigo)) {
                            datosAdic.setRESP_207_03(valor);
                        }
                        if ("RESP_207_04".equals(codigo)) {
                            datosAdic.setRESP_207_04(valor);
                        }
                        if ("RESP_207_05".equals(codigo)) {
                            datosAdic.setRESP_207_05(valor);
                        }
                        if ("RESP_207_06".equals(codigo)) {
                            datosAdic.setRESP_207_06(valor);
                        }
                        if ("RESP_207_07".equals(codigo)) {
                            datosAdic.setRESP_207_07(valor);
                        }
                        if ("RESP_207_08".equals(codigo)) {
                            datosAdic.setRESP_207_08(valor);
                        }
                        if ("RESP_207_09".equals(codigo)) {
                            datosAdic.setRESP_207_09(valor);
                        }
                        if ("RESP_207_10".equals(codigo)) {
                            datosAdic.setRESP_207_10(valor);
                        }
                        if ("RESP_207_11".equals(codigo)) {
                            datosAdic.setRESP_207_11(valor);
                        }
                        if ("RESP_207_12".equals(codigo)) {
                            datosAdic.setRESP_207_12(valor);
                        } else if ("RESP_207_01_01".equals(codigo)) {
                            datosAdic.setRESP_207_01_01(valor);
                        } else if ("RESP_207_01_02".equals(codigo)) {
                            datosAdic.setRESP_207_01_02(valor);
                        } else if ("RESP_207_01_03".equals(codigo)) {
                            datosAdic.setRESP_207_01_03(valor);
                        } else if ("RESP_207_01_04".equals(codigo)) {
                            datosAdic.setRESP_207_01_04(valor);
                        } else if ("RESP_207_01_05".equals(codigo)) {
                            datosAdic.setRESP_207_01_05(valor);
                        } else if ("RESP_207_01_06".equals(codigo)) {
                            datosAdic.setRESP_207_01_06(valor);
                        } else if ("RESP_207_01_07".equals(codigo)) {
                            datosAdic.setRESP_207_01_07(valor);
                        } else if ("RESP_207_01_08".equals(codigo)) {
                            datosAdic.setRESP_207_01_08(valor);
                        } else if ("RESP_207_01_09".equals(codigo)) {
                            datosAdic.setRESP_207_01_09(valor);
                        } else if ("RESP_207_01_10".equals(codigo)) {
                            datosAdic.setRESP_207_01_10(valor);
                        } else if ("RESP_207_01_11".equals(codigo)) {
                            datosAdic.setRESP_207_01_11(valor);
                        } else if ("RESP_207_01_12".equals(codigo)) {
                            datosAdic.setRESP_207_01_12(valor);
                        }
                    }
                }
                listaDatosAdic.add(datosAdic);
            }
        } catch (Exception e) {
            ManejadorErrores.ingresarExcepcionEnLog(e, ConsultaInstrumentoBean.class);
        }
        return listaDatosAdic;
    }

    public List<DatosAdic> obtenerDatosAdicTipologias(Long idDigInstrumento, List<String> respuestasDatosAdic) {
        List<DatosAdic> listaDatosAdic = new ArrayList<DatosAdic>();
        try {
            List<Object[]> listaRespuesta = facBasica.obtenerRespuestasInstrumentoDig(idDigInstrumento, respuestasDatosAdic);

            Map<String, Long> elemNumDatosAdic = new HashMap<String, Long>();
            List<Object[]> listaRespuesta2 = new ArrayList<Object[]>();
            String codigo = "";
            String valor = "";
            Long idElem = null;
            List<Long> numsDatosAdic = new ArrayList<Long>();
            for (Object[] fila : listaRespuesta) {
                int i = 0;
                codigo = (String) fila[i++];
                valor = (String) fila[i++];
                idElem = new Long(fila[i++] + "");
                if (!numsDatosAdic.contains(idElem)) {
                    //Guardamos el idElemento
                    numsDatosAdic.add(idElem);
                }
                listaRespuesta2.add(fila);
            }

            Collections.sort(numsDatosAdic);

            for (Long idSelec : numsDatosAdic) {
                DatosAdic datosAdic = new DatosAdic();
                for (Object[] fila : listaRespuesta2) {
                    int i = 0;
                    codigo = (String) fila[i++];
                    valor = (String) fila[i++];
                    idElem = new Long(fila[i++] + "");
                    if (idElem.equals(idSelec) && valor != null) {
                        if ("RESP_193".equals(codigo)) {
                            datosAdic.setRESP_193(valor);
                        }
                        if ("RESP_194".equals(codigo)) {
                            datosAdic.setRESP_194(valor);
                        }
                        if ("RESP_195".equals(codigo)) {
                            datosAdic.setRESP_195(valor);
                        }
                        if ("RESP_291".equals(codigo)) {
                            datosAdic.setRESP_291(valor);
                        }
                        if ("RESP_196".equals(codigo)) {
                            datosAdic.setRESP_196(valor);
                        }
                        if ("RESP_197".equals(codigo)) {
                            datosAdic.setRESP_197(valor);
                        }
                        if ("RESP_292".equals(codigo)) {
                            datosAdic.setRESP_292(valor);
                        }
                        if ("RESP_293".equals(codigo)) {
                            datosAdic.setRESP_293(valor);
                        }
                        if ("RESP_198_01".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("JT", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            datosAdic.setRESP_198_01(valor);
                        }
                        if ("RESP_198_02".equals(codigo)) {
                             valor = valor +"-"+ facParam.consultarValorTipologia("JT", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            datosAdic.setRESP_198_02(valor);
                        }
                        if ("RESP_198_03".equals(codigo)) {
                             valor = valor +"-"+ facParam.consultarValorTipologia("JT", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            datosAdic.setRESP_198_03(valor);
                        }
                        if ("RESP_198_04".equals(codigo)) {
                             valor = valor +"-"+ facParam.consultarValorTipologia("JT", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            datosAdic.setRESP_198_04(valor);
                        }
                        if ("RESP_198_05".equals(codigo)) {
                             valor = valor +"-"+ facParam.consultarValorTipologia("JT", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            datosAdic.setRESP_198_05(valor);
                        }
                        if ("RESP_198_06".equals(codigo)) {
                             valor = valor +"-"+ facParam.consultarValorTipologia("JT", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            datosAdic.setRESP_198_06(valor);
                        }
                        if ("RESP_198_07".equals(codigo)) {
                             valor = valor +"-"+ facParam.consultarValorTipologia("JT", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            datosAdic.setRESP_198_07(valor);
                        }
                        if ("RESP_198_08".equals(codigo)) {
                             valor = valor +"-"+ facParam.consultarValorTipologia("JT", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            datosAdic.setRESP_198_08(valor);
                        }
                        if ("RESP_198_09".equals(codigo)) {
                             valor = valor +"-"+ facParam.consultarValorTipologia("JT", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            datosAdic.setRESP_198_09(valor);
                        }
                        if ("RESP_198_10".equals(codigo)) {
                             valor = valor +"-"+ facParam.consultarValorTipologia("JT", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            datosAdic.setRESP_198_10(valor);
                        }
                        if ("RESP_199_01".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("NE", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            datosAdic.setRESP_199_01(valor);
                        }
                        if ("RESP_199_02".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("NE", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            datosAdic.setRESP_199_02(valor);
                        }
                        if ("RESP_199_03".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("NE", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            datosAdic.setRESP_199_03(valor);
                        }
                        if ("RESP_199_04".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("NE", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            datosAdic.setRESP_199_04(valor);
                        }
                        if ("RESP_199_05".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("NE", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            datosAdic.setRESP_199_05(valor);
                        }
                        if ("RESP_199_06".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("NE", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            datosAdic.setRESP_199_06(valor);
                        }
                        if ("RESP_199_07".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("NE", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            datosAdic.setRESP_199_07(valor);
                        }
                        if ("RESP_199_08".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("NE", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            datosAdic.setRESP_199_08(valor);
                        }
                        if ("RESP_199_09".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("NE", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            datosAdic.setRESP_199_09(valor);
                        }
                        if ("RESP_199_10".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("NE", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            datosAdic.setRESP_199_10(valor);
                        }
                        if ("RESP_200_01".equals(codigo)) {
                            datosAdic.setRESP_200_01(valor);
                        }
                        if ("RESP_200_02".equals(codigo)) {
                            datosAdic.setRESP_200_02(valor);
                        }
                        if ("RESP_200_03".equals(codigo)) {
                            datosAdic.setRESP_200_03(valor);
                        }
                        if ("RESP_200_04".equals(codigo)) {
                            datosAdic.setRESP_200_04(valor);
                        }
                        if ("RESP_200_05".equals(codigo)) {
                            datosAdic.setRESP_200_05(valor);
                        }
                        if ("RESP_200_06".equals(codigo)) {
                            datosAdic.setRESP_200_06(valor);
                        }
                        if ("RESP_200_07".equals(codigo)) {
                            datosAdic.setRESP_200_07(valor);
                        }
                        if ("RESP_200_08".equals(codigo)) {
                            datosAdic.setRESP_200_08(valor);
                        }
                        if ("RESP_200_09".equals(codigo)) {
                            datosAdic.setRESP_200_09(valor);
                        }
                        if ("RESP_200_10".equals(codigo)) {
                            datosAdic.setRESP_200_10(valor);
                        }
                        if ("RESP_201_01".equals(codigo)) {
                            datosAdic.setRESP_201_01(valor);
                        }
                        if ("RESP_201_02".equals(codigo)) {
                            datosAdic.setRESP_201_02(valor);
                        }
                        if ("RESP_201_03".equals(codigo)) {
                            datosAdic.setRESP_201_03(valor);
                        }
                        if ("RESP_201_04".equals(codigo)) {
                            datosAdic.setRESP_201_04(valor);
                        }
                        if ("RESP_201_05".equals(codigo)) {
                            datosAdic.setRESP_201_05(valor);
                        }
                        if ("RESP_201_06".equals(codigo)) {
                            datosAdic.setRESP_201_06(valor);
                        }
                        if ("RESP_201_07".equals(codigo)) {
                            datosAdic.setRESP_201_07(valor);
                        }
                        if ("RESP_201_08".equals(codigo)) {
                            datosAdic.setRESP_201_08(valor);
                        }
                        if ("RESP_201_09".equals(codigo)) {
                            datosAdic.setRESP_201_09(valor);
                        }
                        if ("RESP_201_10".equals(codigo)) {
                            datosAdic.setRESP_201_10(valor);
                        }
                        if ("RESP_202_01".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("JT", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            datosAdic.setRESP_202_01(valor);
                        }
                        if ("RESP_202_02".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("JT", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            datosAdic.setRESP_202_02(valor);
                        }
                        if ("RESP_202_03".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("JT", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            datosAdic.setRESP_202_03(valor);
                        }
                        if ("RESP_202_04".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("JT", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            datosAdic.setRESP_202_04(valor);
                        }
                        if ("RESP_202_05".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("JT", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            datosAdic.setRESP_202_05(valor);
                        }
                        if ("RESP_202_06".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("JT", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            datosAdic.setRESP_202_06(valor);
                        }
                        if ("RESP_202_07".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("JT", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            datosAdic.setRESP_202_07(valor);
                        }
                        if ("RESP_202_08".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("JT", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            datosAdic.setRESP_202_08(valor);
                        }
                        if ("RESP_202_09".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("JT", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            datosAdic.setRESP_202_09(valor);
                        }
                        if ("RESP_202_10".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("JT", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            datosAdic.setRESP_202_10(valor);
                        }
                        if ("RESP_203_01".equals(codigo)) {
                            datosAdic.setRESP_203_01(valor);
                        }
                        if ("RESP_203_02".equals(codigo)) {
                            datosAdic.setRESP_203_02(valor);
                        }
                        if ("RESP_203_03".equals(codigo)) {
                            datosAdic.setRESP_203_03(valor);
                        }
                        if ("RESP_203_04".equals(codigo)) {
                            datosAdic.setRESP_203_04(valor);
                        }
                        if ("RESP_203_05".equals(codigo)) {
                            datosAdic.setRESP_203_05(valor);
                        }
                        if ("RESP_203_06".equals(codigo)) {
                            datosAdic.setRESP_203_06(valor);
                        }
                        if ("RESP_203_07".equals(codigo)) {
                            datosAdic.setRESP_203_07(valor);
                        }
                        if ("RESP_203_08".equals(codigo)) {
                            datosAdic.setRESP_203_08(valor);
                        }
                        if ("RESP_203_09".equals(codigo)) {
                            datosAdic.setRESP_203_09(valor);
                        }
                        if ("RESP_203_10".equals(codigo)) {
                            datosAdic.setRESP_203_10(valor);
                        }
                        if ("RESP_204_01".equals(codigo)) {
                            datosAdic.setRESP_204_01(valor);
                        }
                        if ("RESP_204_02".equals(codigo)) {
                            datosAdic.setRESP_204_02(valor);
                        }
                        if ("RESP_204_03".equals(codigo)) {
                            datosAdic.setRESP_204_03(valor);
                        }
                        if ("RESP_204_04".equals(codigo)) {
                            datosAdic.setRESP_204_04(valor);
                        }
                        if ("RESP_204_05".equals(codigo)) {
                            datosAdic.setRESP_204_05(valor);
                        }
                        if ("RESP_204_06".equals(codigo)) {
                            datosAdic.setRESP_204_06(valor);
                        }
                        if ("RESP_204_07".equals(codigo)) {
                            datosAdic.setRESP_204_07(valor);
                        }
                        if ("RESP_204_08".equals(codigo)) {
                            datosAdic.setRESP_204_08(valor);
                        }
                        if ("RESP_204_09".equals(codigo)) {
                            datosAdic.setRESP_204_09(valor);
                        }
                        if ("RESP_204_10".equals(codigo)) {
                            datosAdic.setRESP_204_10(valor);
                        }
                        if ("RESP_205_01".equals(codigo)) {
                            datosAdic.setRESP_205_01(valor);
                        }
                        if ("RESP_205_02".equals(codigo)) {
                            datosAdic.setRESP_205_02(valor);
                        }
                        if ("RESP_205_03".equals(codigo)) {
                            datosAdic.setRESP_205_03(valor);
                        }
                        if ("RESP_205_04".equals(codigo)) {
                            datosAdic.setRESP_205_04(valor);
                        }
                        if ("RESP_205_05".equals(codigo)) {
                            datosAdic.setRESP_205_05(valor);
                        }
                        if ("RESP_205_06".equals(codigo)) {
                            datosAdic.setRESP_205_06(valor);
                        }
                        if ("RESP_205_07".equals(codigo)) {
                            datosAdic.setRESP_205_07(valor);
                        }
                        if ("RESP_205_08".equals(codigo)) {
                            datosAdic.setRESP_205_08(valor);
                        }
                        if ("RESP_205_09".equals(codigo)) {
                            datosAdic.setRESP_205_09(valor);
                        }
                        if ("RESP_205_10".equals(codigo)) {
                            datosAdic.setRESP_205_10(valor);
                        }
                        if ("RESP_206".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("GE", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            datosAdic.setRESP_206(valor);
                        }
                        if ("RESP_207_01".equals(codigo)) {
                            datosAdic.setRESP_207_01(valor);
                        }
                        if ("RESP_207_02".equals(codigo)) {
                            datosAdic.setRESP_207_02(valor);
                        }
                        if ("RESP_207_03".equals(codigo)) {
                            datosAdic.setRESP_207_03(valor);
                        }
                        if ("RESP_207_04".equals(codigo)) {
                            datosAdic.setRESP_207_04(valor);
                        }
                        if ("RESP_207_05".equals(codigo)) {
                            datosAdic.setRESP_207_05(valor);
                        }
                        if ("RESP_207_06".equals(codigo)) {
                            datosAdic.setRESP_207_06(valor);
                        }
                        if ("RESP_207_07".equals(codigo)) {
                            datosAdic.setRESP_207_07(valor);
                        }
                        if ("RESP_207_08".equals(codigo)) {
                            datosAdic.setRESP_207_08(valor);
                        }
                        if ("RESP_207_09".equals(codigo)) {
                            datosAdic.setRESP_207_09(valor);
                        }
                        if ("RESP_207_10".equals(codigo)) {
                            datosAdic.setRESP_207_10(valor);
                        }
                        if ("RESP_207_11".equals(codigo)) {
                            datosAdic.setRESP_207_11(valor);
                        }
                        if ("RESP_207_12".equals(codigo)) {
                            datosAdic.setRESP_207_12(valor);
                        } else if ("RESP_207_01_01".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("PA", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            datosAdic.setRESP_207_01_01(valor);
                        } else if ("RESP_207_01_02".equals(codigo)) {
                            datosAdic.setRESP_207_01_02(valor);
                        } else if ("RESP_207_01_03".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("PA", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            datosAdic.setRESP_207_01_03(valor);
                        } else if ("RESP_207_01_04".equals(codigo)) {
                            datosAdic.setRESP_207_01_04(valor);
                        } else if ("RESP_207_01_05".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("PA", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            datosAdic.setRESP_207_01_05(valor);
                        } else if ("RESP_207_01_06".equals(codigo)) {
                            datosAdic.setRESP_207_01_06(valor);
                        } else if ("RESP_207_01_07".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("PA", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            datosAdic.setRESP_207_01_07(valor);
                        } else if ("RESP_207_01_08".equals(codigo)) {
                            datosAdic.setRESP_207_01_08(valor);
                        } else if ("RESP_207_01_09".equals(codigo)) {
                            datosAdic.setRESP_207_01_09(valor);
                        } else if ("RESP_207_01_10".equals(codigo)) {
                            datosAdic.setRESP_207_01_10(valor);
                        } else if ("RESP_207_01_11".equals(codigo)) {
                            datosAdic.setRESP_207_01_11(valor);
                        } else if ("RESP_207_01_12".equals(codigo)) {
                            datosAdic.setRESP_207_01_12(valor);
                        }
                    }
                }
                listaDatosAdic.add(datosAdic);
            }
        } catch (Exception e) {
            ManejadorErrores.ingresarExcepcionEnLog(e, ConsultaInstrumentoBean.class);
        }
        return listaDatosAdic;
    }

    public List<Mantenimiento> obtenerMantenimiento(Long idDigInstrumento, List<String> respuestasMantenimiento) {
        List<Mantenimiento> listaMantenimiento = new ArrayList<Mantenimiento>();
        try {
            List<Object[]> listaRespuesta = facBasica.obtenerRespuestasInstrumentoDig(idDigInstrumento, respuestasMantenimiento);

            Map<String, Long> elemNumMantenimiento = new HashMap<String, Long>();
            List<Object[]> listaRespuesta2 = new ArrayList<Object[]>();
            String codigo = "";
            String valor = "";
            Long idElem = null;
            List<Long> numsMantenimiento = new ArrayList<Long>();
            for (Object[] fila : listaRespuesta) {
                int i = 0;
                codigo = (String) fila[i++];
                valor = (String) fila[i++];
                idElem = new Long(fila[i++] + "");
                if (!numsMantenimiento.contains(idElem)) {
                    //Guardamos el idElemento y el numero de los datos adicionales
                    numsMantenimiento.add(idElem);
                }
                listaRespuesta2.add(fila);
            }

            Collections.sort(numsMantenimiento);

            Mantenimiento mantenimiento = new Mantenimiento();
            int aux = 0;
            for (Long idSelec : numsMantenimiento) {
                aux++;
                for (Object[] fila : listaRespuesta2) {
                    int i = 0;
                    codigo = (String) fila[i++];
                    valor = (String) fila[i++];
                    idElem = new Long(fila[i++] + "");
                    if (idElem.equals(idSelec)) {
                        if ("RESP_241".equals(codigo)) {
                            if (aux == 1) {
                                mantenimiento.setUNO_RESP_241(valor);
                            } else if (aux == 2) {
                                mantenimiento.setDOS_RESP_241(valor);
                            } else if (aux == 3) {
                                mantenimiento.setTRES_RESP_241(valor);
                            } else if (aux == 4) {
                                mantenimiento.setCUATRO_RESP_241(valor);
                            } else if (aux == 5) {
                                mantenimiento.setCINCO_RESP_241(valor);
                            }
                        }
                        if ("RESP_242".equals(codigo)) {
                            if (aux == 1) {
                                mantenimiento.setUNO_RESP_242(valor);
                            } else if (aux == 2) {
                                mantenimiento.setDOS_RESP_242(valor);
                            } else if (aux == 3) {
                                mantenimiento.setTRES_RESP_242(valor);
                            } else if (aux == 4) {
                                mantenimiento.setCUATRO_RESP_242(valor);
                            } else if (aux == 5) {
                                mantenimiento.setCINCO_RESP_242(valor);
                            }
                        }
                        if ("RESP_243".equals(codigo)) {
                            if (aux == 1) {
                                mantenimiento.setUNO_RESP_243(valor);
                            } else if (aux == 2) {
                                mantenimiento.setDOS_RESP_243(valor);
                            } else if (aux == 3) {
                                mantenimiento.setTRES_RESP_243(valor);
                            } else if (aux == 4) {
                                mantenimiento.setCUATRO_RESP_243(valor);
                            } else if (aux == 5) {
                                mantenimiento.setCINCO_RESP_243(valor);
                            }
                        }
                        if ("RESP_244".equals(codigo)) {
                            if (aux == 1) {
                                mantenimiento.setUNO_RESP_244(valor);
                            } else if (aux == 2) {
                                mantenimiento.setDOS_RESP_244(valor);
                            } else if (aux == 3) {
                                mantenimiento.setTRES_RESP_244(valor);
                            } else if (aux == 4) {
                                mantenimiento.setCUATRO_RESP_244(valor);
                            } else if (aux == 5) {
                                mantenimiento.setCINCO_RESP_244(valor);
                            }
                        }
                        if ("RESP_245".equals(codigo)) {
                            if (aux == 1) {
                                mantenimiento.setUNO_RESP_245(valor);
                            } else if (aux == 2) {
                                mantenimiento.setDOS_RESP_245(valor);
                            } else if (aux == 3) {
                                mantenimiento.setTRES_RESP_245(valor);
                            } else if (aux == 4) {
                                mantenimiento.setCUATRO_RESP_245(valor);
                            } else if (aux == 5) {
                                mantenimiento.setCINCO_RESP_245(valor);
                            }
                        }
                        if ("RESP_246_01".equals(codigo)) {
                            if (aux == 1) {
                                mantenimiento.setUNO_RESP_246_01(valor);
                            } else if (aux == 2) {
                                mantenimiento.setDOS_RESP_246_01(valor);
                            } else if (aux == 3) {
                                mantenimiento.setTRES_RESP_246_01(valor);
                            } else if (aux == 4) {
                                mantenimiento.setCUATRO_RESP_246_01(valor);
                            } else if (aux == 5) {
                                mantenimiento.setCINCO_RESP_246_01(valor);
                            }
                        }
                        if ("RESP_246_02".equals(codigo)) {
                            if (aux == 1) {
                                mantenimiento.setUNO_RESP_246_02(valor);
                            } else if (aux == 2) {
                                mantenimiento.setDOS_RESP_246_02(valor);
                            } else if (aux == 3) {
                                mantenimiento.setTRES_RESP_246_02(valor);
                            } else if (aux == 4) {
                                mantenimiento.setCUATRO_RESP_246_02(valor);
                            } else if (aux == 5) {
                                mantenimiento.setCINCO_RESP_246_02(valor);
                            }
                        }
                        if ("RESP_246_03".equals(codigo)) {
                            if (aux == 1) {
                                mantenimiento.setUNO_RESP_246_03(valor);
                            } else if (aux == 2) {
                                mantenimiento.setDOS_RESP_246_03(valor);
                            } else if (aux == 3) {
                                mantenimiento.setTRES_RESP_246_03(valor);
                            } else if (aux == 4) {
                                mantenimiento.setCUATRO_RESP_246_03(valor);
                            } else if (aux == 5) {
                                mantenimiento.setCINCO_RESP_246_03(valor);
                            }
                        }
                        if ("RESP_246_04".equals(codigo)) {
                            if (aux == 1) {
                                mantenimiento.setUNO_RESP_246_04(valor);
                            } else if (aux == 2) {
                                mantenimiento.setDOS_RESP_246_04(valor);
                            } else if (aux == 3) {
                                mantenimiento.setTRES_RESP_246_04(valor);
                            } else if (aux == 4) {
                                mantenimiento.setCUATRO_RESP_246_04(valor);
                            } else if (aux == 5) {
                                mantenimiento.setCINCO_RESP_246_04(valor);
                            }
                        }
                        if ("RESP_246_05".equals(codigo)) {
                            if (aux == 1) {
                                mantenimiento.setUNO_RESP_246_05(valor);
                            } else if (aux == 2) {
                                mantenimiento.setDOS_RESP_246_05(valor);
                            } else if (aux == 3) {
                                mantenimiento.setTRES_RESP_246_05(valor);
                            } else if (aux == 4) {
                                mantenimiento.setCUATRO_RESP_246_05(valor);
                            } else if (aux == 5) {
                                mantenimiento.setCINCO_RESP_246_05(valor);
                            }
                        }
                        if ("RESP_246_06".equals(codigo)) {
                            if (aux == 1) {
                                mantenimiento.setUNO_RESP_246_06(valor);
                            } else if (aux == 2) {
                                mantenimiento.setDOS_RESP_246_06(valor);
                            } else if (aux == 3) {
                                mantenimiento.setTRES_RESP_246_06(valor);
                            } else if (aux == 4) {
                                mantenimiento.setCUATRO_RESP_246_06(valor);
                            } else if (aux == 5) {
                                mantenimiento.setCINCO_RESP_246_06(valor);
                            }
                        }
                        if ("RESP_246_07".equals(codigo)) {
                            if (aux == 1) {
                                mantenimiento.setUNO_RESP_246_07(valor);
                            } else if (aux == 2) {
                                mantenimiento.setDOS_RESP_246_07(valor);
                            } else if (aux == 3) {
                                mantenimiento.setTRES_RESP_246_07(valor);
                            } else if (aux == 4) {
                                mantenimiento.setCUATRO_RESP_246_07(valor);
                            } else if (aux == 5) {
                                mantenimiento.setCINCO_RESP_246_07(valor);
                            }
                        }
                        if ("RESP_246_08".equals(codigo)) {
                            if (aux == 1) {
                                mantenimiento.setUNO_RESP_246_08(valor);
                            } else if (aux == 2) {
                                mantenimiento.setDOS_RESP_246_08(valor);
                            } else if (aux == 3) {
                                mantenimiento.setTRES_RESP_246_08(valor);
                            } else if (aux == 4) {
                                mantenimiento.setCUATRO_RESP_246_08(valor);
                            } else if (aux == 5) {
                                mantenimiento.setCINCO_RESP_246_08(valor);
                            }
                        }
                        if ("RESP_247_01".equals(codigo)) {
                            if (aux == 1) {
                                mantenimiento.setUNO_RESP_247_01(valor);
                            } else if (aux == 2) {
                                mantenimiento.setDOS_RESP_247_01(valor);
                            } else if (aux == 3) {
                                mantenimiento.setTRES_RESP_247_01(valor);
                            } else if (aux == 4) {
                                mantenimiento.setCUATRO_RESP_247_01(valor);
                            } else if (aux == 5) {
                                mantenimiento.setCINCO_RESP_247_01(valor);
                            }
                        }
                        if ("RESP_247_02".equals(codigo)) {
                            if (aux == 1) {
                                mantenimiento.setUNO_RESP_247_02(valor);
                            } else if (aux == 2) {
                                mantenimiento.setDOS_RESP_247_02(valor);
                            } else if (aux == 3) {
                                mantenimiento.setTRES_RESP_247_02(valor);
                            } else if (aux == 4) {
                                mantenimiento.setCUATRO_RESP_247_02(valor);
                            } else if (aux == 5) {
                                mantenimiento.setCINCO_RESP_247_02(valor);
                            }
                        }
                    }
                }
                if (aux == 5) {
                    listaMantenimiento.add(mantenimiento);
                    mantenimiento = new Mantenimiento();
                    aux = 0;
                }
            }
            if (aux > 0) {
                listaMantenimiento.add(mantenimiento);
            }
        } catch (Exception e) {
            ManejadorErrores.ingresarExcepcionEnLog(e, ConsultaInstrumentoBean.class);
        }
        return listaMantenimiento;
    }

    public List<Mantenimiento> obtenerMantenimientoTipologias(Long idDigInstrumento, List<String> respuestasMantenimiento) {
        List<Mantenimiento> listaMantenimiento = new ArrayList<Mantenimiento>();
        try {
            List<Object[]> listaRespuesta = facBasica.obtenerRespuestasInstrumentoDig(idDigInstrumento, respuestasMantenimiento);

            Map<String, Long> elemNumMantenimiento = new HashMap<String, Long>();
            List<Object[]> listaRespuesta2 = new ArrayList<Object[]>();
            String codigo = "";
            String valor = "";
            Long idElem = null;
            List<Long> numsMantenimiento = new ArrayList<Long>();
            for (Object[] fila : listaRespuesta) {
                int i = 0;
                codigo = (String) fila[i++];
                valor = (String) fila[i++];
                idElem = new Long(fila[i++] + "");
                if (!numsMantenimiento.contains(idElem)) {
                    //Guardamos el idElemento y el numero de los datos adicionales
                    numsMantenimiento.add(idElem);
                }
                listaRespuesta2.add(fila);
            }

            Collections.sort(numsMantenimiento);

            Mantenimiento mantenimiento = new Mantenimiento();
            int aux = 0;
            for (Long idSelec : numsMantenimiento) {
                aux++;
                for (Object[] fila : listaRespuesta2) {
                    int i = 0;
                    codigo = (String) fila[i++];
                    valor = (String) fila[i++];
                    idElem = new Long(fila[i++] + "");
                    if (idElem.equals(idSelec) && valor != null) {
                        if ("RESP_241".equals(codigo)) {
                            if (aux == 1) {
                                mantenimiento.setUNO_RESP_241(valor);
                            } else if (aux == 2) {
                                mantenimiento.setDOS_RESP_241(valor);
                            } else if (aux == 3) {
                                mantenimiento.setTRES_RESP_241(valor);
                            }
                        }
                        if ("RESP_242".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("PM", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            if (aux == 1) {
                                mantenimiento.setUNO_RESP_242(valor);
                            } else if (aux == 2) {
                                mantenimiento.setDOS_RESP_242(valor);
                            } else if (aux == 3) {
                                mantenimiento.setTRES_RESP_242(valor);
                            }
                        }
                        if ("RESP_243".equals(codigo)) {
                            if (aux == 1) {
                                mantenimiento.setUNO_RESP_243(valor);
                            } else if (aux == 2) {
                                mantenimiento.setDOS_RESP_243(valor);
                            } else if (aux == 3) {
                                mantenimiento.setTRES_RESP_243(valor);
                            }
                        }
                        if ("RESP_244".equals(codigo)) {
                            if (aux == 1) {
                                mantenimiento.setUNO_RESP_244(valor);
                            } else if (aux == 2) {
                                mantenimiento.setDOS_RESP_244(valor);
                            } else if (aux == 3) {
                                mantenimiento.setTRES_RESP_244(valor);
                            }
                        }
                        if ("RESP_245".equals(codigo)) {
                            if (aux == 1) {
                                mantenimiento.setUNO_RESP_245(valor);
                            } else if (aux == 2) {
                                mantenimiento.setDOS_RESP_245(valor);
                            } else if (aux == 3) {
                                mantenimiento.setTRES_RESP_245(valor);
                            }
                        }
                        if ("RESP_246_01".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("TM", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            if (aux == 1) {
                                mantenimiento.setUNO_RESP_246_01(valor);
                            } else if (aux == 2) {
                                mantenimiento.setDOS_RESP_246_01(valor);
                            } else if (aux == 3) {
                                mantenimiento.setTRES_RESP_246_01(valor);
                            }
                        }
                        if ("RESP_246_02".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("TM", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            if (aux == 1) {
                                mantenimiento.setUNO_RESP_246_02(valor);
                            } else if (aux == 2) {
                                mantenimiento.setDOS_RESP_246_02(valor);
                            } else if (aux == 3) {
                                mantenimiento.setTRES_RESP_246_02(valor);
                            }
                        }
                        if ("RESP_246_03".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("TM", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            if (aux == 1) {
                                mantenimiento.setUNO_RESP_246_03(valor);
                            } else if (aux == 2) {
                                mantenimiento.setDOS_RESP_246_03(valor);
                            } else if (aux == 3) {
                                mantenimiento.setTRES_RESP_246_03(valor);
                            }
                        }
                        if ("RESP_246_04".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("TM", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            if (aux == 1) {
                                mantenimiento.setUNO_RESP_246_04(valor);
                            } else if (aux == 2) {
                                mantenimiento.setDOS_RESP_246_04(valor);
                            } else if (aux == 3) {
                                mantenimiento.setTRES_RESP_246_04(valor);
                            }
                        }
                        if ("RESP_246_05".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("TM", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            if (aux == 1) {
                                mantenimiento.setUNO_RESP_246_05(valor);
                            } else if (aux == 2) {
                                mantenimiento.setDOS_RESP_246_05(valor);
                            } else if (aux == 3) {
                                mantenimiento.setTRES_RESP_246_05(valor);
                            }
                        }
                        if ("RESP_246_06".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("TM", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            if (aux == 1) {
                                mantenimiento.setUNO_RESP_246_06(valor);
                            } else if (aux == 2) {
                                mantenimiento.setDOS_RESP_246_06(valor);
                            } else if (aux == 3) {
                                mantenimiento.setTRES_RESP_246_06(valor);
                            }
                        }
                        if ("RESP_246_07".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("TM", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            if (aux == 1) {
                                mantenimiento.setUNO_RESP_246_07(valor);
                            } else if (aux == 2) {
                                mantenimiento.setDOS_RESP_246_07(valor);
                            } else if (aux == 3) {
                                mantenimiento.setTRES_RESP_246_07(valor);
                            }
                        }
                        if ("RESP_246_08".equals(codigo)) {
                            if (aux == 1) {
                                mantenimiento.setUNO_RESP_246_08(valor);
                            } else if (aux == 2) {
                                mantenimiento.setDOS_RESP_246_08(valor);
                            } else if (aux == 3) {
                                mantenimiento.setTRES_RESP_246_08(valor);
                            }
                        }
                        if ("RESP_247_01".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("CPD", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            if (aux == 1) {
                                mantenimiento.setUNO_RESP_247_01(valor);
                            } else if (aux == 2) {
                                mantenimiento.setDOS_RESP_247_01(valor);
                            } else if (aux == 3) {
                                mantenimiento.setTRES_RESP_247_01(valor);
                            }
                        }
                        if ("RESP_247_02".equals(codigo)) {
                            if (aux == 1) {
                                mantenimiento.setUNO_RESP_247_02(valor);
                            } else if (aux == 2) {
                                mantenimiento.setDOS_RESP_247_02(valor);
                            } else if (aux == 3) {
                                mantenimiento.setTRES_RESP_247_02(valor);
                            }
                        }
                    }
                }
                if (aux == 3) {
                    listaMantenimiento.add(mantenimiento);
                    mantenimiento = new Mantenimiento();
                    aux = 0;
                }
            }
            if (aux > 0) {
                listaMantenimiento.add(mantenimiento);
            }
        } catch (Exception e) {
            ManejadorErrores.ingresarExcepcionEnLog(e, ConsultaInstrumentoBean.class);
        }
        return listaMantenimiento;
    }

    public List<CondEstruc> obtenerCondEstruc(Long idDigInstrumento, List<String> respuestasCondEstruc) {
        List<CondEstruc> listaCondEstruc = new ArrayList<CondEstruc>();
        try {
            List<Object[]> listaRespuesta = facBasica.obtenerRespuestasInstrumentoDig(idDigInstrumento, respuestasCondEstruc);

            Map<String, Long> elemNumCondEstruc = new HashMap<String, Long>();
            List<Object[]> listaRespuesta2 = new ArrayList<Object[]>();
            String codigo = "";
            String valor = "";
            Long idElem = null;
            List<Long> numsCondEstruc = new ArrayList<Long>();
            for (Object[] fila : listaRespuesta) {
                int i = 0;
                codigo = (String) fila[i++];
                valor = (String) fila[i++];
                idElem = new Long(fila[i++] + "");
                if (!numsCondEstruc.contains(idElem)) {
                    //Guardamos el idElemento
                    numsCondEstruc.add(idElem);
                }
                listaRespuesta2.add(fila);
            }

            Collections.sort(numsCondEstruc);

            CondEstruc condEstruc = new CondEstruc();
            int aux = 0;
            for (Long idSelec : numsCondEstruc) {
                aux++;
                for (Object[] fila : listaRespuesta2) {
                    int i = 0;
                    codigo = (String) fila[i++];
                    valor = (String) fila[i++];
                    idElem = new Long(fila[i++] + "");
                    if (idElem.equals(idSelec)) {
                        if ("RESP_248".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_248(valor);
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_248(valor);
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_248(valor);
                            }
                        }
                        if ("RESP_250_01".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_250_01(valor);
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_250_01(valor);
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_250_01(valor);
                            }
                        }
                        if ("RESP_250_02".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_250_02(valor);
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_250_02(valor);
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_250_02(valor);
                            }
                        }
                        if ("RESP_250_03".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_250_03(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_250_03(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_250_03(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_251_01".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_251_01(valor);
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_251_01(valor);
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_251_01(valor);
                            }
                        }
                        if ("RESP_251_02".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_251_02(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_251_02(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_251_02(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_251_03".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_251_03(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_251_03(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_251_03(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_252_01".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_252_01(valor);
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_252_01(valor);
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_252_01(valor);
                            }
                        }
                        if ("RESP_254_01".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_254_01(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_254_01(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_254_01(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_254_02".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_254_02(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_254_02(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_254_02(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_254_03".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_254_03(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_254_03(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_254_03(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_255_01".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_255_01(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_255_01(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_255_01(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_255_02".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_255_02(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_255_02(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_255_02(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_255_03".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_255_03(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_255_03(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_255_03(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_257_01".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_257_01(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_257_01(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_257_01(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_257_02".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_257_02(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_257_02(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_257_02(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_257_03".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_257_03(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_257_03(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_257_03(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_258_01".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_258_01(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_258_01(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_262_01(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_258_02".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_258_02(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_258_02(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_262_02(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_258_03".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_258_03(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_258_03(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_262_03(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_260_01".equals(codigo)) {
                            if (aux == 1) {
                            }
                            if ("RESP_255_03".equals(codigo)) {
                                condEstruc.setUNO_RESP_260_01(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_260_01(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_263_01(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_260_02".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_260_02(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_260_02(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_263_02(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_260_03".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_260_03(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_260_03(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_263_03(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_261_01".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_261_01(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_261_01(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_264_01(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_261_02".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_261_02(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_261_02(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_264_02(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_261_03".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_261_03(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_261_03(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_264_03(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_262_01".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_262_01(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_262_01(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_266_01(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_262_02".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_262_02(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_262_02(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_266_02(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_262_03".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_262_03(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_262_03(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_266_03(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_263_01".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_263_01(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_263_01(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_267_01(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_263_02".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_263_02(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_263_02(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_267_02(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_263_03".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_263_03(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_263_03(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_267_03(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_264_01".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_264_01(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_264_01(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_268_01(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_264_02".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_264_02(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_264_02(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_268_02(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_264_03".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_264_03(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_264_03(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_268_03(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_266_01".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_266_01(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_266_01(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_269_01(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_266_02".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_266_02(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_266_02(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_269_02(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_266_03".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_266_03(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_266_03(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_269_03(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_267_01".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_267_01(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_267_01(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_270_01(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_267_02".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_267_02(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_267_02(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_270_02(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_267_03".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_267_03(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_267_03(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_270_03(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_268_01".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_268_01(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_268_01(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_272_01(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_268_02".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_268_02(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_268_02(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_272_02(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_268_03".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_268_03(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_268_03(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_272_03(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_269_01".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_269_01(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_269_01(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_274_01(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_269_02".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_269_02(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_269_02(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_274_02(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_269_03".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_269_03(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_269_03(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_274_03(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_270_01".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_270_01(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_270_01(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_274_03(valor);
                            }
                        }
                        if ("RESP_270_02".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_270_02(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_270_02(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_270_02(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_270_03".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_270_03(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_270_03(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_270_03(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_272_01".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_272_01(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_272_01(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_272_01(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_272_02".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_272_02(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_272_02(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_272_02(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_272_03".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_272_03(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_272_03(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_272_03(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_274_01".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_274_01(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_274_01(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_274_01(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_274_02".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_274_02(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_274_02(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_274_02(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_274_03".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_274_03(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_274_03(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_274_03(transformaSiNoNA(valor));
                            }
                        }
                    }
                }
                if (aux == 3) {
                    listaCondEstruc.add(condEstruc);
                    condEstruc = new CondEstruc();
                    aux = 0;
                }
            }
            if (aux > 0) {
                listaCondEstruc.add(condEstruc);
            }
        } catch (Exception e) {
            ManejadorErrores.ingresarExcepcionEnLog(e, ConsultaInstrumentoBean.class);
        }
        return listaCondEstruc;
    }

    public List<CondEstruc> obtenerCondEstrucTipologias(Long idDigInstrumento, List<String> respuestasCondEstruc) {
        List<CondEstruc> listaCondEstruc = new ArrayList<CondEstruc>();
        try {
            List<Object[]> listaRespuesta = facBasica.obtenerRespuestasInstrumentoDig(idDigInstrumento, respuestasCondEstruc);

            Map<String, Long> elemNumCondEstruc = new HashMap<String, Long>();
            List<Object[]> listaRespuesta2 = new ArrayList<Object[]>();
            String codigo = "";
            String valor = "";
            Long idElem = null;
            List<Long> numsCondEstruc = new ArrayList<Long>();
            for (Object[] fila : listaRespuesta) {
                int i = 0;
                codigo = (String) fila[i++];
                valor = (String) fila[i++];
                idElem = new Long(fila[i++] + "");
                if (!numsCondEstruc.contains(idElem)) {
                    //Guardamos el idElemento
                    numsCondEstruc.add(idElem);
                }
                listaRespuesta2.add(fila);
            }

            Collections.sort(numsCondEstruc);

            CondEstruc condEstruc = new CondEstruc();
            int aux = 0;
            for (Long idSelec : numsCondEstruc) {
                aux++;
                for (Object[] fila : listaRespuesta2) {
                    int i = 0;
                    codigo = (String) fila[i++];
                    valor = (String) fila[i++];
                    idElem = new Long(fila[i++] + "");
                    if (idElem.equals(idSelec) && valor != null) {
                        if ("RESP_248".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_248(valor);
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_248(valor);
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_248(valor);
                            }
                        }
                        if ("RESP_250_01".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("NSH", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_250_01(valor);
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_250_01(valor);
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_250_01(valor);
                            }
                        }
                        if ("RESP_250_02".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_250_02(valor);
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_250_02(valor);
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_250_02(valor);
                            }
                        }
                        if ("RESP_250_03".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_250_03(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_250_03(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_250_03(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_251_01".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("NSH", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_251_01(valor);
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_251_01(valor);
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_251_01(valor);
                            }
                        }
                        if ("RESP_251_02".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_251_02(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_251_02(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_251_02(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_251_03".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_251_03(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_251_03(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_251_03(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_252_01".equals(codigo)) {
                            valor = valor +"-"+ facParam.consultarValorTipologia("TEENS", valor, getUsuario().getUsuario().getIdioma().getId()).getNombre();
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_252_01(valor);
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_252_01(valor);
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_252_01(valor);
                            }
                        }
                        if ("RESP_254_01".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_254_01(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_254_01(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_254_01(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_254_02".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_254_02(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_254_02(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_254_02(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_254_03".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_254_03(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_254_03(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_254_03(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_255_01".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_255_01(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_255_01(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_255_01(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_255_02".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_255_02(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_255_02(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_255_02(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_255_03".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_255_03(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_255_03(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_255_03(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_257_01".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_257_01(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_257_01(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_257_01(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_257_02".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_257_02(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_257_02(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_257_02(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_257_03".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_257_03(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_257_03(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_257_03(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_258_01".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_258_01(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_258_01(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_262_01(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_258_02".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_258_02(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_258_02(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_262_02(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_258_03".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_258_03(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_258_03(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_262_03(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_260_01".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_260_01(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_260_01(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_263_01(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_260_02".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_260_02(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_260_02(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_263_02(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_260_03".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_260_03(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_260_03(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_263_03(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_261_01".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_261_01(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_261_01(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_264_01(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_261_02".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_261_02(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_261_02(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_264_02(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_261_03".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_261_03(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_261_03(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_264_03(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_262_01".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_262_01(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_262_01(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_266_01(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_262_02".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_262_02(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_262_02(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_266_02(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_262_03".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_262_03(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_262_03(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_266_03(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_263_01".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_263_01(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_263_01(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_267_01(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_263_02".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_263_02(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_263_02(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_267_02(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_263_03".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_263_03(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_263_03(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_267_03(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_264_01".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_264_01(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_264_01(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_268_01(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_264_02".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_264_02(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_264_02(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_268_02(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_264_03".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_264_03(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_264_03(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_268_03(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_266_01".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_266_01(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_266_01(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_269_01(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_266_02".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_266_02(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_266_02(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_269_02(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_266_03".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_266_03(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_266_03(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_269_03(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_267_01".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_267_01(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_267_01(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_270_01(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_267_02".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_267_02(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_267_02(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_270_02(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_267_03".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_267_03(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_267_03(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_270_03(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_268_01".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_268_01(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_268_01(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_272_01(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_268_02".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_268_02(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_268_02(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_272_02(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_268_03".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_268_03(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_268_03(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_272_03(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_269_01".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_269_01(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_269_01(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_274_01(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_269_02".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_269_02(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_269_02(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_274_02(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_269_03".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_269_03(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_269_03(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_274_03(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_270_01".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_270_01(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_270_01(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_274_03(valor);
                            }
                        }
                        if ("RESP_270_02".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_270_02(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_270_02(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_270_02(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_270_03".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_270_03(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_270_03(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_270_03(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_272_01".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_272_01(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_272_01(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_272_01(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_272_02".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_272_02(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_272_02(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_272_02(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_272_03".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_272_03(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_272_03(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_272_03(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_274_01".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_274_01(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_274_01(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_274_01(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_274_02".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_274_02(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_274_02(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_274_02(transformaSiNoNA(valor));
                            }
                        }
                        if ("RESP_274_03".equals(codigo)) {
                            if (aux == 1) {
                                condEstruc.setUNO_RESP_274_03(transformaSiNoNA(valor));
                            } else if (aux == 2) {
                                condEstruc.setDOS_RESP_274_03(transformaSiNoNA(valor));
                            } else if (aux == 3) {
                                condEstruc.setTRES_RESP_274_03(transformaSiNoNA(valor));
                            }
                        }
                    }
                }
                if (aux == 3) {
                    listaCondEstruc.add(condEstruc);
                    condEstruc = new CondEstruc();
                    aux = 0;
                }
            }
            if (aux > 0) {
                listaCondEstruc.add(condEstruc);
            }
        } catch (Exception e) {
            ManejadorErrores.ingresarExcepcionEnLog(e, ConsultaInstrumentoBean.class);
        }
        return listaCondEstruc;
    }

    public class Registro {

        private String idInstrumento;
        private Long idDigInstrumento;
        private String codigoEstablecimiento;
        private String nombreEstablecimiento;
        private String codigoSede;
        private String nombreSede;
        private String codigoPredio;
        private String nombrePredio;
        private String estado;
        private Double latitud;
        private Double longitud;
        private MapModel model;
        private String centro;
        private String Boleta;
        private Long version;
        private Date fechaActualizacion;
        private String usuarioActualizacion;
        private boolean seleccionado;
        private String latitudColumnas;
        private String longitudColumnas;
        private String nivelDPA;
        private String fuente;

        public Long getVersion() {
            return version;
        }

        public void setVersion(Long version) {
            this.version = version;
        }

        public MapModel getModel() {
            model = new DefaultMapModel();
            LatLng coord1 = new LatLng(this.getLatitud(), this.getLongitud());
            model.addOverlay(new Marker(coord1, nombrePredio));
            return model;
        }

        public String getCentro() {
            centro = this.getLatitud() + "," + this.getLongitud();
            return centro;
        }

        public String getIdInstrumento() {
            return idInstrumento;
        }

        public void setIdInstrumento(String idInstrumento) {
            this.idInstrumento = idInstrumento;
        }

        public Long getIdDigInstrumento() {
            return idDigInstrumento;
        }

        public void setIdDigInstrumento(Long idDigInstrumento) {
            this.idDigInstrumento = idDigInstrumento;
        }

        public String getCodigoEstablecimiento() {
            return codigoEstablecimiento;
        }

        public void setCodigoEstablecimiento(String codigoEstablecimiento) {
            this.codigoEstablecimiento = codigoEstablecimiento;
        }

        public String getNombreEstablecimiento() {
            return nombreEstablecimiento;
        }

        public void setNombreEstablecimiento(String nombreEstablecimiento) {
            this.nombreEstablecimiento = nombreEstablecimiento;
        }

        public String getCodigoSede() {
            return codigoSede;
        }

        public void setCodigoSede(String codigoSede) {
            this.codigoSede = codigoSede;
        }

        public String getNombreSede() {
            return nombreSede;
        }

        public void setNombreSede(String nombreSede) {
            this.nombreSede = nombreSede;
        }

        public String getCodigoPredio() {
            return codigoPredio;
        }

        public void setCodigoPredio(String codigoPredio) {
            this.codigoPredio = codigoPredio;
        }

        public String getNombrePredio() {
            return nombrePredio;
        }

        public void setNombrePredio(String nombrePredio) {
            this.nombrePredio = nombrePredio;
        }

        public boolean isEstadoEmitido() {
            return estado != null && estado.equals(EstadoInstrumento.E.toString());
        }

        public boolean isEstadoNuevo() {
            return estado != null && estado.equals(EstadoInstrumento.N.toString());
        }

        public boolean isEstadoAnulado() {
            return estado != null && estado.equals(EstadoInstrumento.A.toString());
        }

        public String getEstado() {
            return estado;
        }

        public void setEstado(String estado) {
            this.estado = estado;
        }

        public Double getLatitud() {
            return latitud;
        }

        public void setLatitud(Double latitud) {
            this.latitud = latitud;
        }

        public Double getLongitud() {
            return longitud;
        }

        public void setLongitud(Double longitud) {
            this.longitud = longitud;
        }

        public String getBoleta() {
            return Boleta;
        }

        public void setBoleta(String Boleta) {
            this.Boleta = Boleta;
        }

        public boolean isSeleccionado() {
            return seleccionado;
        }

        public void setSeleccionado(boolean seleccionado) {
            this.seleccionado = seleccionado;
        }

          public String getLatitudColumnas() {
            return latitudColumnas;
        }

        public void setLatitudColumnas(String latitud) {
            this.latitudColumnas = latitud;
        }

        public String getLongitudColumnas() {
            return longitudColumnas;
        }

        public void setLongitudColumnas(String longitud) {
            this.longitudColumnas = longitud;
        }


        public String getNivelDPA() {
            return nivelDPA;
        }

        public void setNivelDPA(String nivelDPA) {
            this.nivelDPA = nivelDPA;
        }

        public String getFuente() {
            return fuente;
        }

        public void setFuente(String fuente) {
            this.fuente = fuente;
        }

        public Date getFechaActualizacion() {
            return fechaActualizacion;
        }

        public void setFechaActualizacion(Date fechaActualizacion) {
            this.fechaActualizacion = fechaActualizacion;
        }

        public String getUsuarioActualizacion() {
            return usuarioActualizacion;
        }

        public void setUsuarioActualizacion(String usuarioActualizacion) {
            this.usuarioActualizacion = usuarioActualizacion;
        }

    }

    public ConsultaBasicaFacadeLocal getFacBasica() {
        return facBasica;
    }

    public void setFacBasica(ConsultaBasicaFacadeLocal facBasica) {
        this.facBasica = facBasica;
    }

    public List<Integer> getAnos() {
        return anos;
    }

    public void setAnos(List<Integer> anos) {
        this.anos = anos;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public List<String> getMeses() {
        return meses;
    }

    public void setMeses(List<String> meses) {
        this.meses = meses;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getBoletaCensal() {
        return boletaCensal;
    }

    public void setBoletaCensal(String boletaCensal) {
        this.boletaCensal = boletaCensal;
    }

    public String getCodEstablecimiento() {
        return codEstablecimiento;
    }

    public void setCodEstablecimiento(String codEstablecimiento) {
        this.codEstablecimiento = codEstablecimiento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public EstadoInstrumento[] getEstados() {
        return estados;
    }

    public void setEstados(EstadoInstrumento[] estados) {
        this.estados = estados;
    }

    public MapModel getModelTodos() {
        return modelTodos;
    }

    public void setModelTodos(MapModel modelTodos) {
        this.modelTodos = modelTodos;
    }
    
    public String getCentroTodos() {
        double sumLon = 0;
        double sumLat = 0;
        int i = 0;
        for (Registro reg : this.getLista()) {
            if (reg.getLatitud() != null && reg.getLongitud() != null) {
                i++;
                sumLat = sumLat + reg.getLatitud();
                sumLon = sumLon + reg.getLongitud();
            }
        }
        centroTodos = sumLat / i + "," + sumLon / i;
        return centroTodos;
    }

    public void setCentroTodos(String centroTodos) {
        this.centroTodos = centroTodos;
    }

    public void onMarkerSelect(OverlaySelectEvent event) {
        marker = (Marker) event.getOverlay();
    }

    public Marker getMarker() {
        return marker;
    }

    public String consultarInstrumento(Long idDigInstrumento) {
        gestionVariable.setModuloOrigen("consulta");
        gestionVariable.setInstrumentoConsultado(String.valueOf(idDigInstrumento));
        return "modificarInstrumento";
    }

    public GentionVariablesBean getGestionVariable() {
        return gestionVariable;
    }

    public void setGestionVariable(GentionVariablesBean gestionVariable) {
        this.gestionVariable = gestionVariable;
    }

    public List<Adjunto> getListAdjuntos() {
        return listAdjuntos;
    }

    public void setListAdjuntos(List<Adjunto> listAdjuntos) {
        this.listAdjuntos = listAdjuntos;
    }

    public String rutaRelativa(Adjunto adjuntoSeleccionado) {
        String ruta = adjuntoSeleccionado.getRuta();
        if (ruta.contains("ADJUNTOS_CIER")) {
            String[] cadena = adjuntoSeleccionado.getRuta().split("ADJUNTOS_CIER");
            ruta = cadena[1];
        }
        return ruta;
    }

    public List<String> getGaleria() {

        List<String> galerias = new ArrayList<String>();
        if (listAdjuntos != null) {
            for (Adjunto adj : listAdjuntos) {
                String[] cadena = adj.getRuta().split("ADJUNTOS_CIER");
                galerias.add(cadena[1]);
            }
        }
        return galerias;
    }

    public void listaDetalle() {
        try {
            observaciones = new ArrayList<Observacion>();
            adjuntos = new ArrayList<Adjunto>();
            if (!UtilCadena.isNullOVacio(consultaSeleccionado) && !instrumentosSeleccionados.isEmpty()) {
                StringBuilder idsInstrumento = new StringBuilder();
                boolean sw = false;
                for (InstrumentoDig instrumento : instrumentosSeleccionados) {
                    if (sw) {
                        idsInstrumento.append(",");
                    }
                    idsInstrumento.append(instrumento.getId());
                    sw = true;
                }
                if (consultaSeleccionado.equals(1L)) {
                    observaciones = facBasica.consultarObservacionesPorInstrumentos(idsInstrumento.toString());
                } else {
                    adjuntos = facBasica.consultarAdjuntosPorInstrumentos(idsInstrumento.toString());
                }
            }
        } catch (Exception e) {
            ManejadorErrores.ingresarExcepcionEnLog(e, ConsultaInstrumentoBean.class);
        }
    }

    public void verImagenesPredio(Long idDigInstrumento) {
        try {

            listAdjuntos = new ArrayList<Adjunto>();
            respuestaDig = null;
            respuestaDig = facInstrumentos.obtenerRespuestaDigitadaPorIdInstrumentoCodigoRespuesta(idDigInstrumento, "RESP_275");

            if (respuestaDig == null) {
                respuestaDig = new RespuestaDig();
                respuestaDig.setPregunta(facInstrumentos.obtenerPreguntaPorCodigo("PREG_275"));
                respuestaDig.setRespuesta(facInstrumentos.obtenerRespuestaPorCodigo("RESP_275"));
            }
            respuestaDig.setIdInstrumentoDigitado(idDigInstrumento);
            respuestaDig.setElemento(obtenerElemento(idDigInstrumento));

            listAdjuntos = facInstrumentos.obtenerAdjuntosPorIdInstrumentoRespuesta(idDigInstrumento, "PREG_275");

        } catch (Exception ex) {
            ManejadorErrores.ingresarExcepcionEnLog(ex, ConsultaInstrumentoBean.class);
        }
    }

    private Elemento obtenerElemento(Long idInstrumento) {
        return facInstrumentos.obtenerElementoPorIdInstrumentoDigitadoTipoElemento(idInstrumento, "ELE_PRED");
    }

    public void verPlanosPredio(Long idDigInstrumento) {
        try {
            archivosCargados = new ArrayList<String>();
            listAdjuntos = new ArrayList<Adjunto>();
            respuestaDig = null;
            respuestaDig = facInstrumentos.obtenerRespuestaDigitadaPorIdInstrumentoCodigoRespuesta(idDigInstrumento, "RESP_070_03");
            if (respuestaDig == null) {
                respuestaDig = new RespuestaDig();
                respuestaDig.setPregunta(facInstrumentos.obtenerPreguntaPorCodigo("PREG_070"));
                respuestaDig.setRespuesta(facInstrumentos.obtenerRespuestaPorCodigo("RESP_070_03"));
            }
            respuestaDig.setIdInstrumentoDigitado(idDigInstrumento);
            respuestaDig.setElemento(obtenerElemento(idDigInstrumento));
            listAdjuntos = facInstrumentos.obtenerAdjuntosPorIdInstrumentoRespuesta(idDigInstrumento, "PREG_070");
        } catch (Exception ex) {
            //UtilOut.println(ex);
            ManejadorErrores.ingresarExcepcionEnLog(ex, ConsultaInstrumentoBean.class);
        }
    }

    public void cargarAdjuntos(FileUploadEvent event) {
        adjuntosCargados.add(event.getFile());
    }

    public void guardarPlanos() {
        try {
            guardarAdjuntos();
            //verPlanosPredio(respuestaDig.getIdInstrumentoDigitado());
            Utilidades.mostrarMensajeInfo(Utilidades.obtenerMensaje("aplicacion.general.operacionExitosa"), Utilidades.obtenerMensaje("aplicacion.general.registroEnServidor"));
        } catch (Exception e) {
            Utilidades.mostrarMensajeError(Utilidades.obtenerMensaje("aplicacion.general.errorEnServidor"), Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"));
            e.printStackTrace();
        }
    }

    public void guardarFotos() {
        try {
            guardarAdjuntos();
            //verImagenesPredio(respuestaDig.getIdInstrumentoDigitado());
            Utilidades.mostrarMensajeInfo(Utilidades.obtenerMensaje("aplicacion.general.operacionExitosa"), Utilidades.obtenerMensaje("aplicacion.general.registroEnServidor"));
        } catch (Exception e) {
            Utilidades.mostrarMensajeError(Utilidades.obtenerMensaje("aplicacion.general.errorEnServidor"), Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"));
            e.printStackTrace();
        }
    }

    private void guardarAdjuntos() throws Exception {
        try {

            if (adjuntosCargados == null || adjuntosCargados.isEmpty()) {
                return;
            }

            List<Adjunto> listaAdjunto = new ArrayList<Adjunto>();
            for (UploadedFile uploadedFile : adjuntosCargados) {
                String rutaServidor = facArchivo.getPathname(respuestaDig.getIdInstrumentoDigitado());
                String prefix = facArchivo.getPrefix(uploadedFile.getFileName(), respuestaDig.getIdInstrumentoDigitado());
                //String prefix = respuestaDig.getElemento().getId() + "_" + uploadedFile.getFileName();
                String suffix = prefix.substring(prefix.lastIndexOf("."), prefix.length());
                String pathName = facArchivo.getPathname(respuestaDig.getIdInstrumentoDigitado());
                File destino = new File(pathName);
                if (facArchivo.subirArchivo(destino, prefix, uploadedFile.getInputstream())) {
                    Adjunto nuevoAdjunto = new Adjunto();
                    nuevoAdjunto.setRuta(rutaServidor + prefix);
                    nuevoAdjunto.setTipo(suffix.toUpperCase());
                    nuevoAdjunto.setContentType(uploadedFile.getContentType());
                    nuevoAdjunto.setNombre(prefix);
                    nuevoAdjunto.setUsuarioCreacion(usuario.getUsuario().getUsername());
                    nuevoAdjunto.setRespuestaDigitada(this.respuestaDig);
                    listaAdjunto.add(nuevoAdjunto);
                    //System.out.println("sube: " + rutaServidor + prefix);
                    //System.out.println("respuestaDig: " + respuestaDig.getId());
                }
            }
            respuestaDig.setValor("1");
            respuestaDig.setAdjuntosList(listaAdjunto);
            respuestaDig = facInstrumentos.guardarRespuestaDigitadaAdjunto(respuestaDig);
            adjuntosCargados = new ArrayList<UploadedFile>();
            respuestaDig = facInstrumentos.obtenerRespuestaDigitadaPorIdInstrumentoCodigoRespuesta(respuestaDig.getIdInstrumentoDigitado(), respuestaDig.getRespuesta().getCodigo());
            respuestaDig.setIdInstrumentoDigitado(respuestaDig.getElemento().getInstrumentoDigitado().getId());
            listAdjuntos = respuestaDig.getAdjuntosList();
            saveBitacora(usuario.getUsuario().getUsername(), BitacoraTransaccion.MODIFICAR_INSTRUMENTO, this.instrumentoSeleccionado.getBoletaCensal(), "Modulo2");
            facInstrumentos.actualizarFechaUsuarioModificacion(instrumentoSeleccionado, usuario.getUsuario().getUsername());
        } catch (Exception e) {
            throw e;
        }
    }

    public void eliminarAdjunto() {
        String nombre;
        try {
            nombre = adjuntoSeleccionado.getRuta();
            //File f = new File(nombre);
            if (adjuntoSeleccionado.getId() != null) {
                if (facArchivo.eliminarArchivo(adjuntoSeleccionado, usuario.getUsuario())) {
                    //f.delete();
                    respuestaDig.getAdjuntosList().remove(adjuntoSeleccionado);
                    listAdjuntos.remove(adjuntoSeleccionado);
                    if (respuestaDig.getAdjuntosList().isEmpty()) {
                        respuestaDig.setValor("0");
                        facInstrumentos.actualizarRespuestaDigitada(respuestaDig);
                    }
                    if (adjuntoSeleccionado.getId() != null) {
                        facInstrumentos.eliminarAdjunto(adjuntoSeleccionado);
                    }
                    respuestaDig = facInstrumentos.obtenerRespuestaDigitadaPorIdInstrumentoCodigoRespuesta(respuestaDig.getIdInstrumentoDigitado(), respuestaDig.getRespuesta().getCodigo());
                    respuestaDig.setIdInstrumentoDigitado(respuestaDig.getElemento().getInstrumentoDigitado().getId());
                    listAdjuntos = respuestaDig.getAdjuntosList();
                     saveBitacora(usuario.getUsuario().getUsername(), BitacoraTransaccion.MODIFICAR_INSTRUMENTO, this.instrumentoSeleccionado.getBoletaCensal(), "Modulo2_Adj");
                    facInstrumentos.actualizarFechaUsuarioModificacion(instrumentoSeleccionado, usuario.getUsuario().getUsername());
                    Utilidades.mostrarMensajeInfo(Utilidades.obtenerMensaje("aplicacion.general.operacionExitosa"), Utilidades.obtenerMensaje("aplicacion.general.eliminacionEnServidor"));
                } else {
                    Utilidades.mostrarMensajeAdvertencia(Utilidades.obtenerMensaje("consulta.general.noExisteArchivoServidor"), Utilidades.obtenerMensaje("consulta.general.archivoSolicitado") + " " + nombre + Utilidades.obtenerMensaje("consulta.general.noExisteArchivoServidor"));
                }
            }
        } catch (Exception e) {
            Utilidades.mostrarMensajeAdvertencia(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"), Utilidades.obtenerMensaje("aplicacion.general.errorEliminarEnServidor"));
            ManejadorErrores.ingresarExcepcionEnLog(e, ConsultaInstrumentoBean.class);
        }
    }

    public void prepararCreacionNuevo(Long idInstrumento) {
        try {
            InstrumentoDig instrumento = facInstrumentos.consultarInstrumentoPorCodigo(idInstrumento);
            this.mensajeNuevo = Utilidades.obtenerMensaje("dig.crear.nuevo", instrumento.getBoletaCensal() == null ? "" : instrumento.getBoletaCensal(), instrumento.getPredio() == null ? "" : instrumento.getPredio().getDescripcion());
            this.idInstrumentoSeleccionado = idInstrumento;
        } catch (Exception e) {
            ManejadorErrores.ingresarExcepcionEnLog(e, ConsultaInstrumentoBean.class);
        }
    }

    public void prepararCreacionNovedad(Long idInstrumento) {
        try {
            InstrumentoDig instrumento = facInstrumentos.consultarInstrumentoPorCodigo(idInstrumento);
            this.boletaCensalAuditoria = instrumento.getBoletaCensal();
            this.mensajeNovedad = Utilidades.obtenerMensaje("dig.crear.novedad", instrumento.getBoletaCensal() == null ? "" : instrumento.getBoletaCensal(), instrumento.getPredio() == null ? "" : instrumento.getPredio().getNombre());
            this.idInstrumentoSeleccionado = idInstrumento;
        } catch (Exception e) {
            ManejadorErrores.ingresarExcepcionEnLog(e, ConsultaInstrumentoBean.class);
        }
    }

    public void prepararAdministracionObservaciones(Long idInstrumento) {
        try {
            InstrumentoDig instrumento = facInstrumentos.consultarInstrumentoPorCodigo(idInstrumento);
            this.instrumentoSeleccionado = instrumento;
            if (instrumento != null) {
                observaciones = facBasica.consultarObservacionesPorInstrumento(instrumento.getId());
            }
        } catch (Exception e) {
            ManejadorErrores.ingresarExcepcionEnLog(e, ConsultaInstrumentoBean.class);
        }
    }

    public void prepararAdministracionDocumentos(Long idInstrumento) {
        try {
            InstrumentoDig instrumento = facInstrumentos.consultarInstrumentoPorCodigo(idInstrumento);
            this.instrumentoSeleccionado = instrumento;
            if (instrumento != null) {
                documentos = facBasica.consultarDocumentosPorInstrumento(instrumento.getId());
            }
        } catch (Exception e) {
            ManejadorErrores.ingresarExcepcionEnLog(e, ConsultaInstrumentoBean.class);
        }
    }

    public void prepararAdministracionElementos(Long idInstrumento) {
        try {
            InstrumentoDig instrumento = facInstrumentos.consultarInstrumentoPorCodigo(idInstrumento);
            this.consultaSeleccionado = 1L;
            this.instrumentoSeleccionado = instrumento;
            if (instrumento != null) {
                this.instrumentosVersiones = facInstrumentos.consultaInstrumentoPredioSedeEstablecimiento(instrumento);
            }
        } catch (Exception e) {
            ManejadorErrores.ingresarExcepcionEnLog(e, ConsultaInstrumentoBean.class);
        }
    }

    public void getRespuestaDigitada(String codRespuesta, Long idRespuestaDigitada) {
        try {
            respuestaDig = facInstrumentos.obtenerRespuestaDigitadaPorId(idRespuestaDigitada);
            listAdjuntos = new ArrayList<Adjunto>();
            if (respuestaDig == null) {
                respuestaDig = facInstrumentos.crearRespuestaDigitadaPorIdInstrumentoCodigoRespuesta(this.instrumentoSeleccionado.getId(), codRespuesta, "ELE_PRED");
            }
            respuestaDig.setIdInstrumentoDigitado(this.instrumentoSeleccionado.getId());
            listAdjuntos = respuestaDig.getAdjuntosList();
        } catch (Exception e) {
            ManejadorErrores.ingresarExcepcionEnLog(e, ConsultaInstrumentoBean.class);
        }
    }

    public void cargarArchivo() {
        try {
            guardarAdjuntos();
            documentos = new ArrayList<Documento>();
            documentos = facBasica.consultarDocumentosPorInstrumento(instrumentoSeleccionado.getId());
            respuestaDig = facInstrumentos.obtenerRespuestaDigitadaPorId(respuestaDig.getId());
            listAdjuntos = new ArrayList<Adjunto>();
            listAdjuntos = respuestaDig.getAdjuntosList();
            Utilidades.mostrarMensajeInfo(Utilidades.obtenerMensaje("aplicacion.general.operacionExitosa"), Utilidades.obtenerMensaje("aplicacion.general.registroEnServidor"));
        } catch (Exception e) {
            Utilidades.mostrarMensajeError(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"), Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"));
            ManejadorErrores.ingresarExcepcionEnLog(e, ConsultaInstrumentoBean.class);
        }
    }

    public void adicionarAdjuntoARespuestaDigitada(Adjunto adjunto) {
        try {
            List<Adjunto> listaAdjunto = new ArrayList<Adjunto>();
            listaAdjunto = facInstrumentos.obtenerAdjuntosPorRespuestaDigitada(respuestaDig.getId());

            if (listaAdjunto.size() > 0) {
                respuestaDig.setAdjuntosList(listaAdjunto);
            }
            respuestaDig.getAdjuntosList().add(adjunto);
            respuestaDig.setValor("1");
        } catch (Exception e) {
            ManejadorErrores.ingresarExcepcionEnLog(e, ConsultaInstrumentoBean.class);
        }
    }

    public void descargarDocumento(Adjunto adjunto) {
        ServletOutputStream out = null;
        FileInputStream fis = null;
        try {
            /*Utilidades.mostrarMensajeAdvertencia(Utilidades.obtenerMensaje("consulta.general.archivoSolicitado"), Utilidades.obtenerMensaje("consulta.general.noExisteArchivoServidor"));
            File buscandoArc = new File(adjunto.getRuta());*/

            FacesContext contexto = FacesContext.getCurrentInstance();
            HttpServletResponse respuesta = (HttpServletResponse) contexto.getExternalContext().getResponse();
            respuesta.setHeader("Content-disposition", "attachment; filename=" + adjunto.getNombre());
            respuesta.setContentType(adjunto.getContentType());
            respuesta.addHeader("Content-Type", "application/x-download");
            out = respuesta.getOutputStream();
            byte[] bytes = new byte[1000];
            int read = 0;
            String ruta = facArchivo.ubicacionArchivo(adjunto.getRuta());
            fis = new FileInputStream(ruta);
            while ((read = fis.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            contexto.responseComplete();
        } catch (FileNotFoundException e) {
            Utilidades.mostrarMensajeAdvertencia(Utilidades.obtenerMensaje("consulta.general.archivoSolicitado"), Utilidades.obtenerMensaje("consulta.general.noExisteArchivoServidor"));
        } catch (Exception e) {
            ManejadorErrores.ingresarExcepcionEnLog(e, ConsultaInstrumentoBean.class);
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException ex) {
                ManejadorErrores.ingresarExcepcionEnLog(ex, ConsultaInstrumentoBean.class);
            }
        }
    }

    /*public void guardarRespuestaDigitada() {
     try {
     facInstrumentos.actualizarRespuestaDigitada(respuestaDig);
     } catch (Exception e) {
     ManejadorErrores.ingresarExcepcionEnLog(e, ConsultaInstrumentoBean.class);
     }
     }*/
    public void crearNovedad() {
        try {
            Long idNuevoInstrumento;
            idNuevoInstrumento = facInstrumentos.crearNovedad(this.idInstrumentoSeleccionado, currentLogin.getUsuario());
            saveBitacora(currentLogin.getUsuario(), BitacoraTransaccion.MODIFICAR_INSTRUMENTO,this.boletaCensalAuditoria , "Novedad");
            if (idNuevoInstrumento.equals(-1L)) {
                mostrarMensajeAdvertencia("", Utilidades.obtenerMensaje("dig.no.crear.novedad"));
            } else {
                mostrarMensajeInfo("", Utilidades.obtenerMensaje("consulta.crear.novedad.exito"));
                //this.consultar();
                this.gestionVariable.prepararDigitacion(idNuevoInstrumento.toString());
            }
        } catch (Exception ex) {
            mostrarMensajeError("", Utilidades.obtenerMensaje("dig.no.crear.novedad"));
            ManejadorErrores.ingresarExcepcionEnLog(ex, ConsultaInstrumentoBean.class);
        }
    }

    public void crearNuevo() {
        try {
            facInstrumentos.cambiarEstadoNuevo(this.idInstrumentoSeleccionado);
            mostrarMensajeInfo(Utilidades.obtenerMensaje("aplicacion.general.cambiar.estado"), Utilidades.obtenerMensaje("consulta.crear.nuevo.exito"));
            this.consultar();
        } catch (Exception ex) {
            //ex.printStackTrace();
            ManejadorErrores.ingresarExcepcionEnLog(ex, ConsultaInstrumentoBean.class);
        }
    }
    
    private void saveBitacora(String usuario,BitacoraTransaccion bp,String idBoleta,String moduloDigitacion){
        RegistrarAuditoriaDigitacion bitacora = new RegistrarAuditoriaDigitacion();
        bitacora.setBitacoraTransaccion(bp);
        bitacora.setUsuario(usuario);
        bitacora.setBoletaCensal(idBoleta);
        bitacora.setModuloDigitacion(moduloDigitacion);
        bitacora.setFecha(UtilFecha.obtenerFechaActual());
        bitacora.run();
    }

    public String getMensajeNovedad() {
        return mensajeNovedad;
    }

    public void setMensajeNovedad(String mensajeNovedad) {
        this.mensajeNovedad = mensajeNovedad;
    }

    public RespuestaDig getRespuestaSeleccionada() {
        return respuestaSeleccionada;
    }

    public void setRespuestaSeleccionada(RespuestaDig respuestaSeleccionada) {
        this.respuestaSeleccionada = respuestaSeleccionada;
    }

    public boolean isSeleccionarTodos() {
        return seleccionarTodos;
    }

    public void setSeleccionarTodos(boolean seleccionarTodos) {
        this.seleccionarTodos = seleccionarTodos;
    }

    public void selectAllChecks() {
        for (Registro fila : this.lista) {
            if(fila.isEstadoNuevo())
            fila.setSeleccionado(this.seleccionarTodos);
        }
    }

    public UsuarioSesionBean getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioSesionBean usuario) {
        this.usuario = usuario;
    }

    public void emitirInstrumentos() {
        List<String> resultadoMsg = new ArrayList<String>();
        this.renderRespNoEmitidas = false;
        StringBuffer msgResp = new StringBuffer();
        List<String> respNoEmitidas = new ArrayList<String>();
        String resultadoError = "";
        boolean regSeleccionado = false;
        try {
            for (Registro registro : this.lista) {
                if (registro.isSeleccionado()) {
                    regSeleccionado = true;
                    break;
                }
            }
            if (!regSeleccionado) {
                mostrarMensajeInfo("No se puede realizar la emisión", "Para emitir debe seleccionar al menos una boleta");
                return;
            }

            for (Registro registro : this.lista) {
                if (registro.isSeleccionado()) {
                    //Consulto el instrumento Digitado
                    if (!registro.isEstadoEmitido()) {
                        InstrumentoDig instrumento = facInstrumentos.consultarInstrumentoPorCodigo(registro.getIdDigInstrumento());
                        try {
                            instrumento.setUsuarioEmision(usuario.getUsuario().getUsername());
                            instrumento.setPeriodoEmision(UtilFecha.obtenerPeriodoActual());
                            instrumento.setEstado(EstadoInstrumento.E);
                            respNoEmitidas = new ArrayList<String>();
                            respNoEmitidas = facInstrumentos.emitirInstrumento(instrumento, usuario.getUsuario().getUsername());
                            saveBitacora(usuario.getUsuario().getUsername(), BitacoraTransaccion.MODIFICAR_INSTRUMENTO, registro.Boleta, "Emitir");
                            if (respNoEmitidas.isEmpty()) {
                                resultadoMsg.add(Utilidades.obtenerMensaje("dig.emitir.instrumento.msj.emitido", registro.getBoleta()));
                            } else {
                                resultadoMsg.add(Utilidades.obtenerMensaje("dig.emitir.instrumento.msj.NoEmitido", registro.getBoleta()));
                                resultadoMsg.add(Utilidades.obtenerMensaje("dig.emitir.instrumento.msj.complete"));
                                for (String s : respNoEmitidas) {
                                    Respuesta respuesta = facInstrumentos.obtenerRespuestaPorCodigo(s);
                                    String modulo = Utilidades.obtenerMensaje(respuesta.getCodPreguntas().getModulo().getEtiqueta());
                                    String seccion = Utilidades.obtenerMensaje(respuesta.getCodPreguntas().getSeccion().getEtiqueta());
                                    String enunciado = Utilidades.obtenerMensaje(respuesta.getCodPreguntas().getEtiqueta1());
                                    String numeral = respuesta.getCodPreguntas().getNumeral();

                                    String msj = Utilidades.obtenerMensaje("dig.emitir.instrumento.msj.respuesta", modulo, seccion, enunciado, numeral);

                                    resultadoMsg.add(msj);
                                }
                                //resultadoMsg.add("*****************************************");
                                //resultadoMsg.add("");
                            }

                        } catch (ErrorProcedimientoBD eg) {
                            resultadoMsg.add("Instrumento Boleta Censal No " + registro.getBoleta() + ", Resultado : NO EMITIDO.");
                            ManejadorErrores.ingresarExcepcionEnLog(eg, ConsultaInstrumentoBean.class);
                        } catch (Exception ex) {
                            resultadoMsg.add("Instrumento Boleta Censal No " + registro.getBoleta() + ", Resultado : NO EMITIDO.");
                            //resultadoMsg.add(ex.getMessage());
                        }

                    } else {
                        resultadoError = Utilidades.obtenerMensaje("dig.consulta.emitir.actual");
                        resultadoMsg.add("Instrumento Boleta Censal No " + registro.getBoleta() + ", Resultado : " + resultadoError);
                    }
                    resultadoMsg.add("*****************************************");
                    resultadoMsg.add("\n");
                }
            }
            this.consultar();
            FileDownloadView(resultadoMsg);
            mostrarMensajeInfo("Proceso Ejecutado Con Exito", "Para Ver Detalles Clic En 'Ver Resultados'");
            this.renderRespNoEmitidas = true;
        } catch (Exception ex) {
            //errores.manejarExcepcion(ex);
            //ex.printStackTrace();
            ManejadorErrores.ingresarExcepcionEnLog(ex, ConsultaInstrumentoBean.class);
        }

    }

    public void FileDownloadView(List<String> msgResp) {
        BufferedWriter bw = null;
        FileWriter fw = null;
        try {
            File file = new File("RespNoEmitidas.txt");
            fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
            for (String s : msgResp) {
                if (!UtilCadena.isNullOVacio(s)) {
                    bw.write(s);
                    bw.newLine();
                }
            }
            path = file.getPath();
            contentType = FacesContext.getCurrentInstance().getExternalContext().getMimeType(path);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
                if (fw != null) {
                    fw.close();
                }
            } catch (IOException ex) {
            }
        }
    }

    public StreamedContent getFile() throws IOException {
        return new DefaultStreamedContent(new FileInputStream(path), contentType, path);
    }

    public void guardarObservacion() {
        try {
            if (!strModulo.isEmpty() && !strObservacion.isEmpty()) {
                if (strModulo.length() < 100 && strObservacion.length() < 1500) {
                    Pregunta pregObservacion = facInstrumentos.obtenerPreguntaPorCodigo("PREG_190");
                    Pregunta pregModulo = facInstrumentos.obtenerPreguntaPorCodigo("PREG_187");
                    Respuesta respObservacion = facInstrumentos.obtenerRespuestaPorCodigo("RESP_190");
                    Respuesta respModulo = facInstrumentos.obtenerRespuestaPorCodigo("RESP_187");
                    TipoElemento tObservacion = facInstrumentos.buscarTipoElementoPorCodigo("ELE_OBS_COMP");
                    String descripcion = facInstrumentos.obtenerDescripcionElemento(this.instrumentoSeleccionado.getId(), TipoElem.OBSERVACIONES.getCodigo(), Utilidades.obtenerMensaje("elemento.tipo.obscomplementaria"));
                    Elemento elementoObservacion = new Elemento(descripcion, this.instrumentoSeleccionado, tObservacion);
                    elementoObservacion.setEstablecimiento(this.instrumentoSeleccionado.getEstablecimiento());
                    elementoObservacion.setSede(this.instrumentoSeleccionado.getSede());
                    //Creo repuesta digitada.
                    RespuestaDig rdModulo = new RespuestaDig(pregModulo, respModulo, strModulo);
                    RespuestaDig rdObservacion = new RespuestaDig(pregObservacion, respObservacion, strObservacion);
                    elementoObservacion.agregarRespuestaIniciales(rdModulo);
                    elementoObservacion.agregarRespuestaIniciales(rdObservacion);
                    facInstrumentos.guardarElementoObservacion(elementoObservacion);
                    facInstrumentos.actualizarFechaUsuarioModificacion(instrumentoSeleccionado, usuario.getUsuario().getUsername());
                    mostrarMensajeInfo(Utilidades.obtenerMensaje("dig.guardar.observacion"), Utilidades.obtenerMensaje("dig.guardar.observacion.exito"));
                    observaciones = facBasica.consultarObservacionesPorInstrumento(this.instrumentoSeleccionado.getId());
                } else {
                    mostrarMensajeError(Utilidades.obtenerMensaje("dig.error.observaciones"), Utilidades.obtenerMensaje("dig.error.completar.observacion.longitud"));

                }
            } else {
                mostrarMensajeError(Utilidades.obtenerMensaje("dig.error.observaciones"), Utilidades.obtenerMensaje("dig.error.completar.observacion"));

            }
        } catch (Exception ex) {
            errores.manejarExcepcion(ex);
            //ex.printStackTrace();
            ManejadorErrores.ingresarExcepcionEnLog(ex, ConsultaInstrumentoBean.class);
        }
    }

    public void prepararCreacion() {
        strModulo = "";
        strObservacion = "";
    }

    public void prepararEliminacion(Long idModulo, Long idObservacion, Elemento elemento) {
        this.idModulo = idModulo;
        this.idObservacion = idObservacion;
        this.elementoSeleccionado = elemento;

    }

    public void prepararModificacion(Observacion observacion) {
        idModulo = observacion.getIdModulo();
        idObservacion = observacion.getIdObservacion();
        strModulo = observacion.getModulo();
        strObservacion = observacion.getObservacion();
        this.elementoSeleccionado = observacion.getElemento();
    }

    public void eliminarObservacion() {
        try {
            facInstrumentos.eliminarRespuestaDigitadaId(idModulo);
            facInstrumentos.eliminarRespuestaDigitadaId(idObservacion);
            facInstrumentos.eliminarElementoDigitadoId(elementoSeleccionado.getId());
            facInstrumentos.actualizarFechaUsuarioModificacion(instrumentoSeleccionado, usuario.getUsuario().getUsername());
            mostrarMensajeInfo(Utilidades.obtenerMensaje("dig.eliminar.observacion"), Utilidades.obtenerMensaje("dig.eliminar.observacion.exito"));
            observaciones = facBasica.consultarObservacionesPorInstrumento(this.instrumentoSeleccionado.getId());
        } catch (Exception ex) {
            errores.manejarExcepcion(ex);
            //ex.printStackTrace();
            ManejadorErrores.ingresarExcepcionEnLog(ex, ConsultaInstrumentoBean.class);
        }
    }

    public void modificarObservacion() {
        try {
            facInstrumentos.actualizarRespuestaDigitadaId(idModulo, strModulo);
            facInstrumentos.actualizarRespuestaDigitadaId(idObservacion, strObservacion);
            mostrarMensajeInfo(Utilidades.obtenerMensaje("dig.modificar.observacion"), Utilidades.obtenerMensaje("dig.guardar.modificar.exito"));
            observaciones = facBasica.consultarObservacionesPorInstrumento(this.instrumentoSeleccionado.getId());
        } catch (Exception ex) {
            errores.manejarExcepcion(ex);
            //ex.printStackTrace();
            ManejadorErrores.ingresarExcepcionEnLog(ex, ConsultaInstrumentoBean.class);
        }
    }

    public void limpiarFormulario() {
        strModulo = "";
        strObservacion = "";
    }

    public String getMensajeNuevo() {
        return mensajeNuevo;
    }

    public void setMensajeNuevo(String mensajeNuevo) {
        this.mensajeNuevo = mensajeNuevo;
    }

    public List<Observacion> getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(List<Observacion> observaciones) {
        this.observaciones = observaciones;
    }

    public List<Documento> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<Documento> documentos) {
        this.documentos = documentos;
    }

    public InstrumentoDig getInstrumentoSeleccionado() {
        return instrumentoSeleccionado;
    }

    public void setInstrumentoSeleccionado(InstrumentoDig instrumentoSeleccionado) {
        this.instrumentoSeleccionado = instrumentoSeleccionado;
    }

    public String getStrObservacion() {
        return strObservacion;
    }

    public void setStrObservacion(String strObservacion) {
        this.strObservacion = strObservacion;
    }

    public String getStrModulo() {
        return strModulo;
    }

    public void setStrModulo(String strModulo) {
        this.strModulo = strModulo;
    }

    public RespuestaDig getRespuestaDig() {
        return respuestaDig;
    }

    public void setRespuestaDig(RespuestaDig respuestaDig) {
        this.respuestaDig = respuestaDig;
    }

    public List<InstrumentoDig> getInstrumentosVersiones() {
        return instrumentosVersiones;
    }

    public void setInstrumentosVersiones(List<InstrumentoDig> instrumentosVersiones) {
        this.instrumentosVersiones = instrumentosVersiones;
    }

    public Adjunto getAdjuntoSeleccionado() {
        return adjuntoSeleccionado;
    }

    public void setAdjuntoSeleccionado(Adjunto adjuntoSeleccionado) {
        this.adjuntoSeleccionado = adjuntoSeleccionado;
    }

    public List<InstrumentoDig> getInstrumentosSeleccionados() {
        return instrumentosSeleccionados;
    }

    public void setInstrumentosSeleccionados(List<InstrumentoDig> instrumentosSeleccionados) {
        this.instrumentosSeleccionados = instrumentosSeleccionados;
    }

    public Long getConsultaSeleccionado() {
        return consultaSeleccionado;
    }

    public void setConsultaSeleccionado(Long consultaSeleccionado) {
        this.consultaSeleccionado = consultaSeleccionado;
    }

    public List<Adjunto> getAdjuntos() {
        return adjuntos;
    }

    public void setAdjuntos(List<Adjunto> adjuntos) {
        this.adjuntos = adjuntos;
    }

    public boolean isRenderRespNoEmitidas() {
        return renderRespNoEmitidas;
    }

    public void setRenderRespNoEmitidas(boolean renderRespNoEmitidas) {
        this.renderRespNoEmitidas = renderRespNoEmitidas;
    }

    public String getNombreUltimoDPA() {
        return nombreUltimoDPA;
    }

    public void setNombreUltimoDPA(String nombreUltimoDPA) {
        this.nombreUltimoDPA = nombreUltimoDPA;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public FuenteDigitacion[] getOrigenes() {
        return origenes;
    }

    public void setOrigenes(FuenteDigitacion[] origenes) {
        this.origenes = origenes;
    }

}
