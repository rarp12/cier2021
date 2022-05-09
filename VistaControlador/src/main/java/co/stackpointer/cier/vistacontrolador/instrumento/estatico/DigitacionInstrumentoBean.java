/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.instrumento.estatico;

import co.stackpointer.cier.modelo.auditoria.RegistrarAuditoriaDigitacion;
import co.stackpointer.cier.modelo.entidad.digitado.Adjunto;
import co.stackpointer.cier.modelo.entidad.digitado.Elemento;
import co.stackpointer.cier.modelo.entidad.digitado.EspacioSimilar;
import co.stackpointer.cier.modelo.entidad.digitado.InstrumentoDig;
import co.stackpointer.cier.modelo.entidad.digitado.RespuestaDig;
import co.stackpointer.cier.modelo.entidad.dpa.Nivel;
import co.stackpointer.cier.modelo.entidad.establecimiento.Establecimiento;
import co.stackpointer.cier.modelo.entidad.establecimiento.Predio;
import co.stackpointer.cier.modelo.entidad.establecimiento.Sede;
import co.stackpointer.cier.modelo.entidad.instrumento.Instrumento;
import co.stackpointer.cier.modelo.entidad.instrumento.ModuloIns;
import co.stackpointer.cier.modelo.entidad.instrumento.Pregunta;
import co.stackpointer.cier.modelo.entidad.instrumento.Respuesta;
import co.stackpointer.cier.modelo.entidad.instrumento.Seccion;
import co.stackpointer.cier.modelo.entidad.instrumento.TipoElemento;
import co.stackpointer.cier.modelo.entidad.instrumento.TipologiaValorNombre;
import co.stackpointer.cier.modelo.excepcion.ErrorProcedimientoBD;
import co.stackpointer.cier.modelo.excepcion.ErrorValidacion;
import co.stackpointer.cier.modelo.fachada.ArchivoFacadeLocal;
import co.stackpointer.cier.modelo.fachada.DPAFacadeLocal;
import co.stackpointer.cier.modelo.fachada.EstablecimientoFacadeLocal;
import co.stackpointer.cier.modelo.fachada.InstrumentoFacadeLocal;
import co.stackpointer.cier.modelo.fachada.ParametrosFacadeLocal;
import co.stackpointer.cier.modelo.fachada.PredioFacadeLocal;
import co.stackpointer.cier.modelo.interfaceDPA.NivelDPA;
import co.stackpointer.cier.modelo.tipo.ActivoInactivo;
import co.stackpointer.cier.modelo.tipo.BitacoraTransaccion;
import co.stackpointer.cier.modelo.tipo.EstadoInstrumento;
import co.stackpointer.cier.modelo.tipo.FuenteInformacion;
import co.stackpointer.cier.modelo.tipo.SiNo;
import co.stackpointer.cier.modelo.tipo.SiNoNA;
import co.stackpointer.cier.modelo.tipo.SuficienteInsuficiente;
import co.stackpointer.cier.modelo.tipo.TipoDato;
import co.stackpointer.cier.modelo.tipo.TipoElem;
import co.stackpointer.cier.modelo.tipo.TipoEstablecimiento;
import co.stackpointer.cier.modelo.tipo.TipologiaCodigo;
import co.stackpointer.cier.modelo.tipo.Topografias;
import co.stackpointer.cier.modelo.tipo.Verificable;
import co.stackpointer.cier.modelo.utilidad.UtilCadena;
import co.stackpointer.cier.modelo.utilidad.UtilFecha;
import co.stackpointer.cier.vista.ManejadorErrores;
import co.stackpointer.cier.vista.Utilidades;
import static co.stackpointer.cier.vista.Utilidades.mostrarMensajeError;
import static co.stackpointer.cier.vista.Utilidades.mostrarMensajeInfo;
import co.stackpointer.cier.vistacontrolador.bean.GentionVariablesBean;
import co.stackpointer.cier.vistacontrolador.bean.GestionArchivos;
import co.stackpointer.cier.vistacontrolador.bean.UsuarioSesionBean;
import co.stackpointer.cier.vistacontrolador.consulta.ConsultaBase;
import co.stackpointer.cier.vistacontrolador.consulta.basica.ConsultaInstrumentoBean;
import co.stackpointer.cier.vistacontrolador.instrumento.BusquedaSedeBean;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.omnifaces.util.Faces;
import org.primefaces.event.FlowEvent;
import org.primefaces.component.wizard.Wizard;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author rrocha
 */
@ManagedBean(name = "digitarInstrumentoEstatico")
@SessionScoped
public class DigitacionInstrumentoBean extends ConsultaBase implements Serializable {

    private List<ModuloIns> modulos = new ArrayList<ModuloIns>();
    private List<Seccion> secciones = new ArrayList<Seccion>();
    private List<Pregunta> preguntasSeccion01 = new ArrayList<Pregunta>();
    private List<Pregunta> preguntasSeccion02 = new ArrayList<Pregunta>();
    private List<Pregunta> preguntasSeccion03 = new ArrayList<Pregunta>();
    private List<Pregunta> preguntasSeccion04 = new ArrayList<Pregunta>();
    private List<Pregunta> preguntasSeccion05 = new ArrayList<Pregunta>();
    private List<Pregunta> preguntasSeccion06 = new ArrayList<Pregunta>();
    private List<Pregunta> preguntasSeccion07 = new ArrayList<Pregunta>();
    private List<Pregunta> preguntasSeccion08 = new ArrayList<Pregunta>();
    private List<Pregunta> preguntasSeccion09 = new ArrayList<Pregunta>();
    private List<Pregunta> preguntasSeccion10 = new ArrayList<Pregunta>();
    private List<Pregunta> preguntasSeccion11 = new ArrayList<Pregunta>();
    private List<Pregunta> preguntasSeccion12 = new ArrayList<Pregunta>();
    private List<Pregunta> preguntasSeccion13 = new ArrayList<Pregunta>();
    private List<Pregunta> preguntasSeccion14 = new ArrayList<Pregunta>();
    private List<Pregunta> preguntasSeccion15 = new ArrayList<Pregunta>();
    private List<Pregunta> preguntasSeccion16 = new ArrayList<Pregunta>();
    private List<Pregunta> preguntasSeccion17 = new ArrayList<Pregunta>();
    private Instrumento instrumentoVigente = new Instrumento();
    private List<Elemento> elementos = new ArrayList<Elemento>();
    private List<Elemento> establecimientos = new ArrayList<Elemento>();
    private List<Elemento> edificios = new ArrayList<Elemento>();
    private List<Elemento> espacios = new ArrayList<Elemento>();
    private List<Elemento> planosPredios = new ArrayList<Elemento>();
    private List<Elemento> planosEspacios = new ArrayList<Elemento>();
    private List<Elemento> observacionesComplementarias = new ArrayList<Elemento>();
    //InstrumentoRenderizado instrumentoRenderizado;
    private InstrumentoDig instrumentoDigitado = new InstrumentoDig();
    private Elemento edificioSeleccionado;
    private Elemento espacioSeleccionado;
    private Elemento observacionSeleccionada;
    private Elemento planoEspacioSeleccionado;
    private Elemento establecimientoSeleccionado;
    private Elemento elementoEliminado = new Elemento();
    //Mapa para el manejo de los errores, se asume que null es que el elemento no presenta error.
    private Map<Pregunta, String> erroresMap = new HashMap<Pregunta, String>();
    //Mapa con Respuestas Digitadas
    private Map<Respuesta, RespuestaDig> respuestasDigitadasMap = new HashMap<Respuesta, RespuestaDig>();
    private List<FuenteInformacion> fuentesInformacion = new ArrayList<FuenteInformacion>();
    private List<TipoEstablecimiento> tipoEstablecimiento = new ArrayList<TipoEstablecimiento>();
    private List<Topografias> topografias = new ArrayList<Topografias>();
    private List<ActivoInactivo> activoInactivo = new ArrayList<ActivoInactivo>();
    private List<Verificable> verificable = new ArrayList<Verificable>();
    private List<SuficienteInsuficiente> suficiente = new ArrayList<SuficienteInsuficiente>();
    private Predio predio;
    private Sede sede;
    private Establecimiento establecimiento;
    private ManejadorErrores errores;
    private boolean skip;
    private int indice;
    private String path;
    private String contentType;
    private boolean renderRespNoEmitidas;
    private boolean disableCodBoletaAndPredio;
    @EJB
    InstrumentoFacadeLocal fInstrumentos;
    @EJB
    DPAFacadeLocal fDpa;
    @EJB
    EstablecimientoFacadeLocal fEstablecimiento;
    @EJB
    PredioFacadeLocal fPredio;
    @EJB
    ParametrosFacadeLocal fParametros;
    @EJB
    private ArchivoFacadeLocal facArchivo;
    private int jornadaIndex = 100;
    private int nivelIndex = 101;
    private int cursoIndex = 102;
    private int matrculaIndex = 103;
    private int tandaIndex = 200;
    private int docentesIndex = 201;
    private int adminIndex = 202;
    private int servicioIndex = 203;
    private String idInstrumentoDigitado = null;
    private String mensajeEliminacion = "";
    private String tablaActualizada = "";
    @ManagedProperty(value = "#{gestionVariable}")
    private GentionVariablesBean gestionVariable;
    @ManagedProperty(value = "#{usuarioSesion}")
    private UsuarioSesionBean usuario;
    private boolean valorCheck;
    private Wizard wizard;
    private List<Pregunta> listadoPreguntasEdificios;
    private List<Pregunta> listadoPreguntasEspacios;
    private List<Pregunta> listadoPreguntasObservaciones;
    private List<Pregunta> listadoPreguntasPlanosEspacios;
    private List<Pregunta> listadoPreguntasEstablecimientos;
    private List<EspacioSimilar> espaciosDigitados;
    private String identificadorEdificioActual;
    private List<UploadedFile> archivosCargados;
    private Boolean adjuntar;
    private RespuestaDig respuestaDig;
    private List<Respuesta> respuestasObligatorias;
    private List<Respuesta> respuestasExteriores;
    private String step = "Modulo1";

    @PostConstruct
    public void inicializarInstrumento() {
        try {
            super.initActual();
            this.renderRespNoEmitidas = false;
            this.archivosCargados = new ArrayList<UploadedFile>();
            this.adjuntar = false;
            idInstrumentoDigitado = gestionVariable.getIdInstrumentoDigitado();
            fuentesInformacion = Arrays.asList(FuenteInformacion.values());
            tipoEstablecimiento = Arrays.asList(TipoEstablecimiento.values());
            topografias = Arrays.asList(Topografias.values());
            activoInactivo = Arrays.asList(ActivoInactivo.values());
            verificable = Arrays.asList(Verificable.values());
            suficiente = Arrays.asList(SuficienteInsuficiente.values());
            errores = new ManejadorErrores();
            instrumentoVigente = fInstrumentos.consultarInstrumentoVigente();
            modulos = fInstrumentos.obtenerModulosInstrumento(instrumentoVigente);
            instrumentoDigitado = new InstrumentoDig();
            instrumentoDigitado.setInstrumento(instrumentoVigente);
            espaciosDigitados = new ArrayList<EspacioSimilar>();
            for (int i = 0; i < 10; i++) {
                espaciosDigitados.add(new EspacioSimilar());
            }
            if (idInstrumentoDigitado != null && !idInstrumentoDigitado.equals("")) {
                instrumentoDigitado = fInstrumentos.consultarInstrumentoPorCodigo(idInstrumentoDigitado);
                this.sede = instrumentoDigitado.getSede();
                this.establecimiento = instrumentoDigitado.getEstablecimiento();
            }
            for (ModuloIns modulo : modulos) {
                for (Seccion seccion : modulo.getSeccionList()) {
                    this.secciones.add(seccion);
                    this.crearElementos(instrumentoDigitado, modulo, seccion);
                }
            }
            respuestasObligatorias = fInstrumentos.obtenerRespuestaObligatorias();
            this.llenarMapasPreguntasSecciones();
            this.llenarListadosElementos();

            this.prepararMapaEdificioM3S2();
            this.prepararMapaEspacioM4S1();
            this.prepararMapaPlanoEspacioM4S2();
            this.prepararMapaObservacionM5S2();
            this.prepararEstablecimientoM6S1();
            this.cargarListadoPreguntas();
            this.cargarRespuestasExteriores();

            if (idInstrumentoDigitado != null && !idInstrumentoDigitado.equals("")) {
                this.completarRespuestasDigitadas();
            }
            this.cargarDPA();

        } catch (Exception ex) {
            mostrarMensajeError(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"), ex.getMessage());
            ManejadorErrores.ingresarExcepcionEnLog(ex, DigitacionInstrumentoBean.class);
        }
    }

    private void cargarRespuestasExteriores() {
        respuestasExteriores = fInstrumentos.obtenerRespuestasPorPreguntas(Arrays.asList("PREG_135", "PREG_136", "PREG_137"));
    }

    private void cargarListadoPreguntas() {
        //Cargo preguntas de edificios
        this.listadoPreguntasEdificios = new ArrayList<Pregunta>();
        this.listadoPreguntasEdificios.addAll(fInstrumentos.obtenerPreguntaPorTipoElemento("ELE_EDI", "MODULO3", "SEC_RIGEP"));
        this.listadoPreguntasEdificios.addAll(fInstrumentos.obtenerPreguntaPorTipoElemento("ELE_EDI", "MODULOC1", "SEC_MCE"));
        this.listadoPreguntasEdificios.addAll(fInstrumentos.obtenerPreguntaPorTipoElemento("ELE_EDI", "MODULOC1", "SEC_CEEP"));

        this.listadoPreguntasEspacios = new ArrayList<Pregunta>();
        this.listadoPreguntasEspacios.addAll(fInstrumentos.obtenerPreguntaPorTipoElemento("ELE_ESP", "MODULO4", "SEC_IEE"));

        this.listadoPreguntasEstablecimientos = new ArrayList<Pregunta>();
        this.listadoPreguntasEstablecimientos.addAll(fInstrumentos.obtenerPreguntaPorTipoElemento("ELE_EST", "MODULOB1", "SEC_DEAFMLSLA"));

        this.listadoPreguntasObservaciones = new ArrayList<Pregunta>();
        this.listadoPreguntasObservaciones.addAll(fInstrumentos.obtenerPreguntaPorTipoElemento("ELE_OBS_COMP", "MODULOA1", "SEC_OCLI"));

        this.listadoPreguntasPlanosEspacios = new ArrayList<Pregunta>();
        this.listadoPreguntasPlanosEspacios.addAll(fInstrumentos.obtenerPreguntaPorTipoElemento("ELE_PLA_ESP", "MODULO4", "SEC_CEEE"));

    }

    private void cargarDPA() {
        try {
            RespuestaDig respN1 = this.respuestasDigitadasMap.get(fInstrumentos.obtenerRespuestaPorCodigo("RESP_031_02"));
            RespuestaDig respN2 = this.respuestasDigitadasMap.get(fInstrumentos.obtenerRespuestaPorCodigo("RESP_031_03"));
            RespuestaDig respN3 = this.respuestasDigitadasMap.get(fInstrumentos.obtenerRespuestaPorCodigo("RESP_031_04"));
            RespuestaDig respN4 = this.respuestasDigitadasMap.get(fInstrumentos.obtenerRespuestaPorCodigo("RESP_031_05"));
            RespuestaDig respN5 = this.respuestasDigitadasMap.get(fInstrumentos.obtenerRespuestaPorCodigo("RESP_031_06"));

            if (respN1.getValor() != null) {
                this.seleccionNivel1 = fDpa.getNivelPorId(Long.valueOf(respN1.getValor()));
                this.cambioNivel1();
            }
            if (respN2.getValor() != null) {
                this.seleccionNivel2 = fDpa.getNivelPorId(Long.valueOf(respN2.getValor()));
                this.cambioNivel2();
            }
            if (respN3.getValor() != null) {
                this.seleccionNivel3 = fDpa.getNivelPorId(Long.valueOf(respN3.getValor()));
                this.cambioNivel3();
            }
            if (respN4.getValor() != null) {
                this.seleccionNivel4 = fDpa.getNivelPorId(Long.valueOf(respN4.getValor()));
                this.cambioNivel4();
            }
            if (respN5.getValor() != null) {
                this.seleccionNivel5 = fDpa.getNivelPorId(Long.valueOf(respN5.getValor()));
                this.cambioNivel5();
            }


        } catch (Exception ex) {
            ManejadorErrores.ingresarExcepcionEnLog(ex, DigitacionInstrumentoBean.class);
            mostrarMensajeError(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"), ex.getMessage());
        }
    }

    private void completarRespuestasDigitadas() {
        for (Elemento elemento : fInstrumentos.obtenerElementoPorIdInstrumentoDigitado(idInstrumentoDigitado)) {
            for (RespuestaDig rspDig : fInstrumentos.obtenerRespuestasDigitadasPorElementoId(elemento.getId())) {
                //Busco los adjuntos para cada respuesta digitada
                rspDig.setAdjuntosList(new ArrayList<Adjunto>());
                rspDig.getAdjuntosList().addAll(fInstrumentos.obtenerAdjuntosPorRespuestaDigitada(rspDig.getId()));
                this.respuestasDigitadasMap.put(rspDig.getRespuesta(), rspDig);
            }
        }

        //Complemento Edificios
        Elemento edificioInicial = edificios.get(0);
        for (Elemento elementoAlmacenados : fInstrumentos.obtenerElementoPorIdInstrumentoDigitado(idInstrumentoDigitado)) {
            if (elementoAlmacenados.getTipoElemento().getCodigo().equals(TipoElem.EDIFIO.getCodigo())) {
                elementoAlmacenados.setPreguntas(edificioInicial.getPreguntas());
                for (RespuestaDig rspDig : edificioInicial.getRespuestasList()) {
                    elementoAlmacenados.agregarClaveMapa(rspDig.getRespuesta(), rspDig);
                }
                for (RespuestaDig rspDig : fInstrumentos.obtenerRespuestasDigitadasPorElementoId(elementoAlmacenados.getId())) {
                    rspDig.setAdjuntosList(new ArrayList<Adjunto>());
                    rspDig.getAdjuntosList().addAll(fInstrumentos.obtenerAdjuntosPorRespuestaDigitada(rspDig.getId()));
                    elementoAlmacenados.agregarClaveMapa(rspDig.getRespuesta(), rspDig);
                }
                if (!edificios.contains(elementoAlmacenados)) {
                    edificios.add(elementoAlmacenados);
                } else {
                    edificios.set(edificios.indexOf(elementoAlmacenados), elementoAlmacenados);
                }
            }
        }

        // Complemento establecimientos ;
        Elemento establecimientoInicial = establecimientos.get(0);
        for (Elemento elementoAlmacenados : fInstrumentos.obtenerElementoPorIdInstrumentoDigitado(idInstrumentoDigitado)) {
            if (elementoAlmacenados.getTipoElemento().getCodigo().equals(TipoElem.ESTABLECIMIENTO.getCodigo())) {
                elementoAlmacenados.setPreguntas(establecimientoInicial.getPreguntas());
                for (RespuestaDig rspDig : establecimientoInicial.getRespuestasList()) {
                    elementoAlmacenados.agregarClaveMapa(rspDig.getRespuesta(), rspDig);
                }
                for (RespuestaDig rspDig : fInstrumentos.obtenerRespuestasDigitadasPorElementoId(elementoAlmacenados.getId())) {
                    rspDig.setAdjuntosList(new ArrayList<Adjunto>());
                    rspDig.getAdjuntosList().addAll(fInstrumentos.obtenerAdjuntosPorRespuestaDigitada(rspDig.getId()));
                    elementoAlmacenados.agregarClaveMapa(rspDig.getRespuesta(), rspDig);
                }
                if (!establecimientos.contains(elementoAlmacenados)) {
                    establecimientos.add(elementoAlmacenados);
                } else {
                    establecimientos.set(establecimientos.indexOf(elementoAlmacenados), elementoAlmacenados);
                }
            }
        }

        //Complemento espacios
        Elemento espacioInicial = espacios.get(0);
        for (Elemento elementoAlmacenados : fInstrumentos.obtenerElementoPorIdInstrumentoDigitado(idInstrumentoDigitado)) {
            if (elementoAlmacenados.getTipoElemento().getCodigo().equals(TipoElem.ESPACIO.getCodigo())) {
                elementoAlmacenados.setPreguntas(espacioInicial.getPreguntas());
                for (RespuestaDig rspDig : espacioInicial.getRespuestasList()) {
                    elementoAlmacenados.agregarClaveMapa(rspDig.getRespuesta(), rspDig);
                }
                for (RespuestaDig rspDig : fInstrumentos.obtenerRespuestasDigitadasPorElementoId(elementoAlmacenados.getId())) {
                    rspDig.setAdjuntosList(new ArrayList<Adjunto>());
                    rspDig.getAdjuntosList().addAll(fInstrumentos.obtenerAdjuntosPorRespuestaDigitada(rspDig.getId()));
                    elementoAlmacenados.agregarClaveMapa(rspDig.getRespuesta(), rspDig);
                }
                //seteamos el nombre del tipo de espacio
                RespuestaDig codigoEspacioEscolar = fInstrumentos.obtenerRespuestaDigitada(elementoAlmacenados, "PREG_098", "RESP_098_01");
                TipologiaValorNombre nombreEspacioEscolar = fParametros.consultarValorTipologia(TipologiaCodigo.TIPO_ESPACIO_ESCOLAR.getValor(), UtilCadena.isNullOVacio(codigoEspacioEscolar) ? "" : codigoEspacioEscolar.getValor(), usuario.getUsuario().getIdioma().getId());
                elementoAlmacenados.setEspacioEscolar(UtilCadena.isNullOVacio(nombreEspacioEscolar) ? "" : nombreEspacioEscolar.getNombre());
                //adicionamos a la lista de espacios
                if (!espacios.contains(elementoAlmacenados)) {
                    espacios.add(elementoAlmacenados);
                } else {
                    espacios.set(espacios.indexOf(elementoAlmacenados), elementoAlmacenados);
                }
            }
        }
        Collections.sort(espacios);

        //Complemento planosPredios 
        Elemento planoPredioInicial = planosPredios.get(0);
        for (Elemento elementoAlmacenados : fInstrumentos.obtenerElementoPorIdInstrumentoDigitado(idInstrumentoDigitado)) {
            if (elementoAlmacenados.getTipoElemento().getCodigo().equals(TipoElem.PLANO_PREDIO.getCodigo())) {
                elementoAlmacenados.setPreguntas(planoPredioInicial.getPreguntas());
                for (RespuestaDig rspDig : planoPredioInicial.getRespuestasList()) {
                    elementoAlmacenados.agregarClaveMapa(rspDig.getRespuesta(), rspDig);
                }
                for (RespuestaDig rspDig : fInstrumentos.obtenerRespuestasDigitadasPorElementoId(elementoAlmacenados.getId())) {
                    rspDig.setAdjuntosList(new ArrayList<Adjunto>());
                    rspDig.getAdjuntosList().addAll(fInstrumentos.obtenerAdjuntosPorRespuestaDigitada(rspDig.getId()));
                    elementoAlmacenados.agregarClaveMapa(rspDig.getRespuesta(), rspDig);
                }
                if (!planosPredios.contains(elementoAlmacenados)) {
                    planosPredios.add(elementoAlmacenados);
                } else {
                    planosPredios.set(planosPredios.indexOf(elementoAlmacenados), elementoAlmacenados);
                }
            }
        }

        //Complento planosEspacios ;
        Elemento planoEspacioInicial = planosEspacios.get(0);
        for (Elemento elementoAlmacenados : fInstrumentos.obtenerElementoPorIdInstrumentoDigitado(idInstrumentoDigitado)) {
            if (elementoAlmacenados.getTipoElemento().getCodigo().equals(TipoElem.PLANO_ESPACIO.getCodigo())) {
                elementoAlmacenados.setPreguntas(planoEspacioInicial.getPreguntas());
                for (RespuestaDig rspDig : planoEspacioInicial.getRespuestasList()) {
                    elementoAlmacenados.agregarClaveMapa(rspDig.getRespuesta(), rspDig);
                }
                for (RespuestaDig rspDig : fInstrumentos.obtenerRespuestasDigitadasPorElementoId(elementoAlmacenados.getId())) {
                    rspDig.setAdjuntosList(new ArrayList<Adjunto>());
                    rspDig.getAdjuntosList().addAll(fInstrumentos.obtenerAdjuntosPorRespuestaDigitada(rspDig.getId()));
                    elementoAlmacenados.agregarClaveMapa(rspDig.getRespuesta(), rspDig);
                }
                if (!planosEspacios.contains(elementoAlmacenados)) {
                    planosEspacios.add(elementoAlmacenados);
                } else {
                    planosEspacios.set(planosEspacios.indexOf(elementoAlmacenados), elementoAlmacenados);
                }
            }
        }

        //Complemento observacionesComplementarias ;
        Elemento observacionComplementariaInicial = observacionesComplementarias.get(0);
        for (Elemento elementoAlmacenados : fInstrumentos.obtenerElementoPorIdInstrumentoDigitado(idInstrumentoDigitado)) {
            if (elementoAlmacenados.getTipoElemento().getCodigo().equals(TipoElem.OBSERVACIONES.getCodigo())) {
                elementoAlmacenados.setPreguntas(observacionComplementariaInicial.getPreguntas());
                for (RespuestaDig rspDig : observacionComplementariaInicial.getRespuestasList()) {
                    elementoAlmacenados.agregarClaveMapa(rspDig.getRespuesta(), rspDig);
                }
                for (RespuestaDig rspDig : fInstrumentos.obtenerRespuestasDigitadasPorElementoId(elementoAlmacenados.getId())) {
                    rspDig.setAdjuntosList(new ArrayList<Adjunto>());
                    rspDig.getAdjuntosList().addAll(fInstrumentos.obtenerAdjuntosPorRespuestaDigitada(rspDig.getId()));
                    elementoAlmacenados.agregarClaveMapa(rspDig.getRespuesta(), rspDig);
                }
                if (!observacionesComplementarias.contains(elementoAlmacenados)) {
                    observacionesComplementarias.add(elementoAlmacenados);
                } else {
                    observacionesComplementarias.set(observacionesComplementarias.indexOf(elementoAlmacenados), elementoAlmacenados);
                }
            }
        }

    }

    private void crearElementos(InstrumentoDig instrumento, ModuloIns modulo, Seccion seccion) {
        elementos = new ArrayList<Elemento>();
        for (TipoElemento tipo : fInstrumentos.obtenerTiposElementos()) {
            Elemento elemento = new Elemento(tipo.getDescripcion() + "_1", instrumento, tipo);
            elemento.setPreguntas(this.obtenerPreguntaRespuestasDigitadas(elemento, modulo, seccion));
            elementos.add(elemento);
        }
    }

    private List<Pregunta> obtenerPreguntaRespuestasDigitadas(Elemento elemento, ModuloIns modulo, Seccion seccion) {
        List<Pregunta> preguntas = fInstrumentos.obtenerPreguntaPorTipoElemento(elemento.getTipoElemento(), modulo, seccion);
        for (Pregunta prg : preguntas) {
            //this.erroresMap.put(prg, null);
            prg.setRespuestasDigitadas(new ArrayList<RespuestaDig>());
            for (Respuesta rsp : prg.getRespuestaList()) {
                RespuestaDig respuesta = new RespuestaDig(elemento, prg, rsp);
                prg.agregarRespuestaDigitada(respuesta);
                this.respuestasDigitadasMap.put(rsp, respuesta);
            }
        }
        return preguntas;
    }

    private void llenarMapasPreguntasSecciones() {
        for (Seccion seccion : secciones) {
            if (seccion.getCodigo().equals("SEC_DGE")) {
                this.preguntasSeccion01.addAll(seccion.getPreguntaList());
            }
            if (seccion.getCodigo().equals("SEC_DECE")) {
                this.preguntasSeccion02.addAll(seccion.getPreguntaList());
            }
            if (seccion.getCodigo().equals("SEC_UG")) {
                this.preguntasSeccion03.addAll(seccion.getPreguntaList());
            }
            if (seccion.getCodigo().equals("SEC_CU")) {
                this.preguntasSeccion04.addAll(seccion.getPreguntaList());
            }
            if (seccion.getCodigo().equals("SEC_CG")) {
                this.preguntasSeccion05.addAll(seccion.getPreguntaList());
            }
            if (seccion.getCodigo().equals("SEC_CPILGEE")) {
                this.preguntasSeccion06.addAll(seccion.getPreguntaList());
            }
            if (seccion.getCodigo().equals("SEC_CPEEPEEEE")) {
                this.preguntasSeccion07.addAll(seccion.getPreguntaList());
            }
            if (seccion.getCodigo().equals("SEC_RIGEP")) {
                this.preguntasSeccion08.addAll(seccion.getPreguntaList());
            }
            if (seccion.getCodigo().equals("SEC_IEE")) {
                this.preguntasSeccion09.addAll(seccion.getPreguntaList());
            }
            if (seccion.getCodigo().equals("SEC_CEEE")) {
                this.preguntasSeccion10.addAll(seccion.getPreguntaList());
            }
            if (seccion.getCodigo().equals("SEC_RAECTMI")) {
                this.preguntasSeccion11.addAll(seccion.getPreguntaList());
            }
            if (seccion.getCodigo().equals("SEC_OCLI")) {
                this.preguntasSeccion12.addAll(seccion.getPreguntaList());
            }
            if (seccion.getCodigo().equals("SEC_DEAFMLSLA")) {
                this.preguntasSeccion13.addAll(seccion.getPreguntaList());
            }
            if (seccion.getCodigo().equals("SEC_RSIE")) {
                this.preguntasSeccion14.addAll(seccion.getPreguntaList());
            }
            if (seccion.getCodigo().equals("SEC_MCE")) {
                this.preguntasSeccion15.addAll(seccion.getPreguntaList());
            }
            if (seccion.getCodigo().equals("SEC_CEEP")) {
                this.preguntasSeccion16.addAll(seccion.getPreguntaList());
            }
            if (seccion.getCodigo().equals("SEC_ICP")) {
                this.preguntasSeccion17.addAll(seccion.getPreguntaList());
            }

        }
    }

    public void prepararEstablecimientoM6S1() {
        ModuloIns modulo = fInstrumentos.obtenerModuloPorCodigo("MODULOB1");
        Seccion seccion = fInstrumentos.obtenerSeccionPorCodigo("SEC_DEAFMLSLA");
        Elemento establecimiento = establecimientos.get(0);
        establecimiento.setRespuestasList(new ArrayList<RespuestaDig>());
        establecimiento.setMapaRespuestas(new HashMap<Respuesta, RespuestaDig>());
        establecimiento.setPreguntas(new ArrayList<Pregunta>());
        List<Pregunta> preguntas = fInstrumentos.obtenerPreguntaPorTipoElemento(establecimiento.getTipoElemento(), modulo, seccion);
        for (Pregunta prg : preguntas) {
            establecimiento.agregarPregunta(prg);
            for (Respuesta rsp : prg.getRespuestaList()) {
                RespuestaDig respuesta = new RespuestaDig(establecimiento, prg, rsp);
                establecimiento.agregarRespuesta(respuesta);
                establecimiento.agregarClaveMapa(rsp, respuesta);
            }
        }
        establecimientos.set(0, establecimiento);
    }

    public void prepararMapaObservacionM5S2() {
        ModuloIns modulo = fInstrumentos.obtenerModuloPorCodigo("MODULOA1");
        Seccion seccion = fInstrumentos.obtenerSeccionPorCodigo("SEC_OCLI");
        Elemento observacion = observacionesComplementarias.get(0);
        observacion.setRespuestasList(new ArrayList<RespuestaDig>());
        observacion.setMapaRespuestas(new HashMap<Respuesta, RespuestaDig>());
        observacion.setPreguntas(new ArrayList<Pregunta>());
        List<Pregunta> preguntas = fInstrumentos.obtenerPreguntaPorTipoElemento(observacion.getTipoElemento(), modulo, seccion);
        for (Pregunta prg : preguntas) {
            observacion.agregarPregunta(prg);
            for (Respuesta rsp : prg.getRespuestaList()) {
                RespuestaDig respuesta = new RespuestaDig(observacion, prg, rsp);
                observacion.agregarRespuesta(respuesta);
                observacion.agregarClaveMapa(rsp, respuesta);
            }
        }
        observacionesComplementarias.set(0, observacion);
    }

    public void prepararMapaPlanoEspacioM4S2() {
        ModuloIns modulo = fInstrumentos.obtenerModuloPorCodigo("MODULO4");
        Seccion seccion = fInstrumentos.obtenerSeccionPorCodigo("SEC_CEEE");
        Elemento planoEspacio = planosEspacios.get(0);
        planoEspacio.setRespuestasList(new ArrayList<RespuestaDig>());
        planoEspacio.setMapaRespuestas(new HashMap<Respuesta, RespuestaDig>());
        planoEspacio.setPreguntas(new ArrayList<Pregunta>());
        List<Pregunta> preguntas = fInstrumentos.obtenerPreguntaPorTipoElemento(planoEspacio.getTipoElemento(), modulo, seccion);
        for (Pregunta prg : preguntas) {
            planoEspacio.agregarPregunta(prg);
            for (Respuesta rsp : prg.getRespuestaList()) {
                RespuestaDig respuesta = new RespuestaDig(planoEspacio, prg, rsp);
                planoEspacio.agregarRespuesta(respuesta);
                planoEspacio.agregarClaveMapa(rsp, respuesta);
            }
        }
        planosEspacios.set(0, planoEspacio);
    }

    public void prepararMapaEdificioM3S2() {
        ModuloIns modulo = fInstrumentos.obtenerModuloPorCodigo("MODULO3");
        Seccion seccion = fInstrumentos.obtenerSeccionPorCodigo("SEC_RIGEP");
        Elemento edificio = edificios.get(0);
        edificio.setRespuestasList(new ArrayList<RespuestaDig>());
        edificio.setMapaRespuestas(new HashMap<Respuesta, RespuestaDig>());
        edificio.setPreguntas(new ArrayList<Pregunta>());
        List<Pregunta> preguntas = fInstrumentos.obtenerPreguntaPorTipoElemento(edificio.getTipoElemento(), modulo, seccion);
        preguntas.addAll(fInstrumentos.obtenerPreguntaPorTipoElemento(edificio.getTipoElemento(), fInstrumentos.obtenerModuloPorCodigo("MODULOC1"), fInstrumentos.obtenerSeccionPorCodigo("SEC_MCE")));
        preguntas.addAll(fInstrumentos.obtenerPreguntaPorTipoElemento(edificio.getTipoElemento(), fInstrumentos.obtenerModuloPorCodigo("MODULOC1"), fInstrumentos.obtenerSeccionPorCodigo("SEC_CEEP")));
        for (Pregunta prg : preguntas) {
            edificio.agregarPregunta(prg);
            for (Respuesta rsp : prg.getRespuestaList()) {
                RespuestaDig respuesta = new RespuestaDig(edificio, prg, rsp);
                edificio.agregarRespuesta(respuesta);
                edificio.agregarClaveMapa(rsp, respuesta);
            }
        }
        edificios.set(0, edificio);
    }

    public void prepararMapaEspacioM4S1() {
        ModuloIns modulo = fInstrumentos.obtenerModuloPorCodigo("MODULO4");
        Seccion seccion = fInstrumentos.obtenerSeccionPorCodigo("SEC_IEE");
        Elemento espacio = espacios.get(0);
        espacio.setRespuestasList(new ArrayList<RespuestaDig>());
        espacio.setMapaRespuestas(new HashMap<Respuesta, RespuestaDig>());
        espacio.setPreguntas(new ArrayList<Pregunta>());
        List<Pregunta> preguntas = fInstrumentos.obtenerPreguntaPorTipoElemento(espacio.getTipoElemento(), modulo, seccion);
        for (Pregunta prg : preguntas) {
            espacio.agregarPregunta(prg);
            for (Respuesta rsp : prg.getRespuestaList()) {
                RespuestaDig respuesta = new RespuestaDig(espacio, prg, rsp);
                if (respuesta.getRespuesta().getCodigo().equals("RESP_106")) {
                    respuesta.setValor("0");
                }
                espacio.agregarRespuesta(respuesta);
                espacio.agregarClaveMapa(rsp, respuesta);
            }
        }
        espacios.set(0, espacio);
    }

    public void guardarEstablecimientoM6S1() {
        Elemento elementoAlmacenado = new Elemento(establecimientoSeleccionado, establecimiento, sede);

        try {
            if (instrumentoDigitado.getId() != null) {
                this.validarEncuestadoresSupervisoresEstablecimientos();
                this.validarMapaRespuestas(establecimientoSeleccionado.getMapaRespuestas());
                for (Map.Entry respuesta : establecimientoSeleccionado.getMapaRespuestas().entrySet()) {
                    RespuestaDig rspDg = ((RespuestaDig) respuesta.getValue());
                    if (!UtilCadena.isNullOVacio(rspDg.getValor())) {
                        if (rspDg.getRespuesta().getCodigo().equalsIgnoreCase("RESP_193")) {
                            if (UtilCadena.isNullOVacio(fEstablecimiento.getSedePorCodigo(rspDg.getValor()))) {
                                throw new ErrorValidacion(Utilidades.obtenerMensaje("dig.guardar.instrumento.codigoEstablecimiento.noexiste"));
                            }
                        }
                        elementoAlmacenado.agregarRespuestaIniciales(rspDg);
                    }
                }
                this.validarJornadasTandasEstablecimiento(establecimientoSeleccionado);
                this.validarPreguntasDependendientesElementos(establecimientoSeleccionado.getTipoElemento(), establecimientoSeleccionado);
                fInstrumentos.guardarElementoDigitado(elementoAlmacenado);
                saveBitacora(getUsuarioSesion().getUsuario().getUsername(), BitacoraTransaccion.MODIFICAR_ELEMENTO, instrumentoDigitado.getBoletaCensal(), "Modulo6");
                fInstrumentos.actualizarFechaUsuarioModificacion(instrumentoDigitado, usuario.getUsuario().getUsername());
                mostrarMensajeInfo(Utilidades.obtenerMensaje("dig.guardar.establecimiento"), Utilidades.obtenerMensaje("dig.guardar.exito.elemento"));
            } else {
                throw new ErrorValidacion(Utilidades.obtenerMensaje("dig.guardar.instrumento.noexiste"));
            }
        } catch (ErrorValidacion ev) {
            mostrarMensajeError(Utilidades.obtenerMensaje("gerencial.bid.consulta.adminAmbito.errorValidacion"), ev.getMessage());
        } catch (Exception ex) {
            mostrarMensajeError(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"), ex.getMessage());
            ManejadorErrores.ingresarExcepcionEnLog(ex, DigitacionInstrumentoBean.class);
        }
    }

    public void validarRespuestasCorrespondientes(String idEdificio, String idEspacio, String idPiso) {
        if (!fInstrumentos.tieneRespuestaCorrespondiente(this.instrumentoDigitado, idEdificio, idEspacio, idPiso)) {
            throw new ErrorValidacion(Utilidades.obtenerMensaje("aplicacion.general.error.respuestaAlmacenadas.correspondientes"));
        }
    }

    public void guardarPlanoEspacioM4S2() {
        Elemento elementoAlmacenado = new Elemento(planoEspacioSeleccionado, establecimiento, sede);
        try {
            if (instrumentoDigitado.getId() != null) {
                this.validarMapaRespuestas(planoEspacioSeleccionado.getMapaRespuestas());
                Respuesta idEspacio = fInstrumentos.obtenerRespuestaPorCodigo("RESP_138");
                Respuesta idEdificio = fInstrumentos.obtenerRespuestaPorCodigo("RESP_139");
                Respuesta idPiso = fInstrumentos.obtenerRespuestaPorCodigo("RESP_140");
                String espacio = this.planoEspacioSeleccionado.getMapaRespuestas().get(idEspacio).getValor();
                String edificio = this.planoEspacioSeleccionado.getMapaRespuestas().get(idEdificio).getValor();
                String piso = this.planoEspacioSeleccionado.getMapaRespuestas().get(idPiso).getValor();

                if (espacio == null || espacio.isEmpty()
                        || edificio == null || edificio.isEmpty()
                        || piso == null || piso.isEmpty()) {
                    throw new ErrorValidacion(Utilidades.obtenerMensaje("dig.guardar.instrumento.llenar.ambiente.incompleto"));
                }

                this.validarRespuestasCorrespondientes(edificio, espacio, piso);

                for (Map.Entry respuesta : planoEspacioSeleccionado.getMapaRespuestas().entrySet()) {
                    RespuestaDig rspDg = ((RespuestaDig) respuesta.getValue());
                    if (!UtilCadena.isNullOVacio(rspDg.getValor())) {
                        elementoAlmacenado.agregarRespuestaIniciales(rspDg);
                    }
                }

                fInstrumentos.guardarElementoDigitado(elementoAlmacenado);
                saveBitacora(getUsuarioSesion().getUsuario().getUsername(), BitacoraTransaccion.MODIFICAR_ELEMENTO, instrumentoDigitado.getBoletaCensal(), "Modulo4");
                fInstrumentos.actualizarFechaUsuarioModificacion(instrumentoDigitado, usuario.getUsuario().getUsername());
                mostrarMensajeInfo(Utilidades.obtenerMensaje("dig.guardar.croquis"), Utilidades.obtenerMensaje("dig.guardar.exito.elemento"));

            } else {
                throw new ErrorValidacion(Utilidades.obtenerMensaje("dig.guardar.instrumento.noexiste"));
            }
        } catch (Exception ex) {
            mostrarMensajeError(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"), ex.getMessage());
            ManejadorErrores.ingresarExcepcionEnLog(ex, DigitacionInstrumentoBean.class);
        }
    }

    public void guardarObservacionM5S2() {
        Elemento elementoAlmacenado = new Elemento(observacionSeleccionada, establecimiento, sede);
        try {
            if (instrumentoDigitado.getId() != null) {
                this.validarMapaRespuestas(observacionSeleccionada.getMapaRespuestas());
                for (Map.Entry respuesta : observacionSeleccionada.getMapaRespuestas().entrySet()) {
                    RespuestaDig rspDg = ((RespuestaDig) respuesta.getValue());
                    if (!UtilCadena.isNullOVacio(rspDg.getValor())) {
                        elementoAlmacenado.agregarRespuestaIniciales(rspDg);
                    }
                }
                //Valido Nombre del modulo y Observacion
                Respuesta modulo = fInstrumentos.obtenerRespuestaPorCodigo("RESP_187");
                Respuesta observacion = fInstrumentos.obtenerRespuestaPorCodigo("RESP_190");
                String valorModulo = observacionSeleccionada.getMapaRespuestas().get(modulo).getValor();
                String valorObservacion = observacionSeleccionada.getMapaRespuestas().get(observacion).getValor();
                if (!UtilCadena.isNullOVacio(valorModulo) && UtilCadena.isNullOVacio(valorObservacion)) {
                    throw new ErrorValidacion(Utilidades.obtenerMensaje("dig.guardar.requerido.observacion"));
                }

                fInstrumentos.guardarElementoDigitado(elementoAlmacenado);
                saveBitacora(getUsuarioSesion().getUsuario().getUsername(), BitacoraTransaccion.MODIFICAR_ELEMENTO, instrumentoDigitado.getBoletaCensal(), "Modulo5");
                fInstrumentos.actualizarFechaUsuarioModificacion(instrumentoDigitado, usuario.getUsuario().getUsername());
                mostrarMensajeInfo(Utilidades.obtenerMensaje("dig.guardar.observacion"), Utilidades.obtenerMensaje("dig.guardar.exito.elemento"));
            } else {
                throw new ErrorValidacion(Utilidades.obtenerMensaje("dig.guardar.instrumento.noexiste"));
            }
        } catch (Exception ex) {
            mostrarMensajeError(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"), ex.getMessage());
            ManejadorErrores.ingresarExcepcionEnLog(ex, DigitacionInstrumentoBean.class);
        }
    }

    public void guardarEdificioM3S2() {
        Elemento elementoAlmacenado = new Elemento(edificioSeleccionado, establecimiento, sede);
        try {
            if (instrumentoDigitado.getId() != null) {
                Respuesta idEdificio = fInstrumentos.obtenerRespuestaPorCodigo("RESP_073");
                Respuesta ediAltura = fInstrumentos.obtenerRespuestaPorCodigo("RESP_074_01");
                Respuesta ediAccesibilidad = fInstrumentos.obtenerRespuestaPorCodigo("RESP_085");
                int altura = Integer.parseInt(UtilCadena.isNullOVacio(this.edificioSeleccionado.getMapaRespuestas().get(ediAltura).getValor()) ? "0" : this.edificioSeleccionado.getMapaRespuestas().get(ediAltura).getValor());
                int accesibilidad = Integer.parseInt(UtilCadena.isNullOVacio(this.edificioSeleccionado.getMapaRespuestas().get(ediAccesibilidad).getValor()) ? "0" : this.edificioSeleccionado.getMapaRespuestas().get(ediAccesibilidad).getValor());
                edificioSeleccionado.getId();
                //Valido que digita el Id del Elemento
                if (UtilCadena.isNullOVacio(this.edificioSeleccionado.getMapaRespuestas().get(idEdificio).getValor())) {
                    throw new ErrorValidacion(Utilidades.obtenerMensaje("dig.guardar.requerido", idEdificio.getCodPreguntas().getNumeral(), Utilidades.obtenerMensaje("bd.ins_preguntas.etiqueta_1.PREG_073")));
                }
                this.validarRespuestasCrearEdificio("RESP_073", this.edificioSeleccionado.getMapaRespuestas().get(idEdificio).getValor(), idEdificio.getCodPreguntas().getNumeral());

                //Valido que el id digitado no estè repetido.
                TipoElemento tipo = fInstrumentos.buscarTipoElementoPorCodigo(TipoElem.EDIFIO.getCodigo());
                if (fInstrumentos.idElementoRepetido(tipo, "PREG_073", this.edificioSeleccionado.getMapaRespuestas().get(idEdificio).getValor(), edificioSeleccionado)) {
                    throw new ErrorValidacion(Utilidades.obtenerMensaje("dig.guardar.elemento.repetido"));
                }
                if (accesibilidad > altura) {
                    throw new ErrorValidacion(Utilidades.obtenerMensaje("dig.guardar.elemento.altura"));
                }
                this.validarMapaRespuestas(edificioSeleccionado.getMapaRespuestas());
                this.validarPreguntasDependendientesElementos(tipo, edificioSeleccionado);
                for (Map.Entry respuesta : edificioSeleccionado.getMapaRespuestas().entrySet()) {
                    RespuestaDig rspDg = ((RespuestaDig) respuesta.getValue());
                    if (!UtilCadena.isNullOVacio(rspDg.getValor())) {
                        elementoAlmacenado.agregarRespuestaIniciales(rspDg);
                    }
                }
                //Guardo Elementos. y sus Respuestas Digitadas.
                fInstrumentos.guardarElementoDigitado(elementoAlmacenado);
                saveBitacora(getUsuarioSesion().getUsuario().getUsername(), BitacoraTransaccion.MODIFICAR_ELEMENTO, instrumentoDigitado.getBoletaCensal(), "Modulo3");
                fInstrumentos.actualizarFechaUsuarioModificacion(instrumentoDigitado, usuario.getUsuario().getUsername());
                mostrarMensajeInfo(Utilidades.obtenerMensaje("dig.guardar.edificio"), Utilidades.obtenerMensaje("dig.guardar.exito.elemento"));

            } else {
                throw new ErrorValidacion(Utilidades.obtenerMensaje("dig.guardar.instrumento.noexiste"));
            }
        } catch (ErrorValidacion e) {
            mostrarMensajeError(Utilidades.obtenerMensaje("aplicacion.general.errorValidacion"), e.getMessage());
        } catch (Exception ex) {
            mostrarMensajeError(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"), ex.getMessage());
            ManejadorErrores.ingresarExcepcionEnLog(ex, DigitacionInstrumentoBean.class);
        }
    }

    public void guardarEspacioM4S1() {
        Elemento elementoAlmacenado = new Elemento(espacioSeleccionado, establecimiento, sede);
        try {
            if (instrumentoDigitado.getId() != null) {
                Respuesta idEdificio = fInstrumentos.obtenerRespuestaPorCodigo("RESP_095");
                Respuesta idEspacio = fInstrumentos.obtenerRespuestaPorCodigo("RESP_094");
                Respuesta alturaEspacio = fInstrumentos.obtenerRespuestaPorCodigo("RESP_096");
                boolean isExterior = false;
                if (UtilCadena.isNullOVacio(this.espacioSeleccionado.getMapaRespuestas().get(idEspacio).getValor())) {
                    throw new ErrorValidacion(Utilidades.obtenerMensaje("dig.guardar.requerido", idEdificio.getCodPreguntas().getNumeral(), Utilidades.obtenerMensaje("bd.ins_preguntas.etiqueta_1.PREG_094")));
                }
                if (UtilCadena.isNullOVacio(this.espacioSeleccionado.getMapaRespuestas().get(idEdificio).getValor())) {
                    throw new ErrorValidacion(Utilidades.obtenerMensaje("dig.guardar.requerido", idEdificio.getCodPreguntas().getNumeral(), Utilidades.obtenerMensaje("bd.ins_preguntas.etiqueta_1.PREG_095")));
                } else {
                    String nombreEdificio = this.espacioSeleccionado.getMapaRespuestas().get(idEdificio).getValor();
                    if (nombreEdificio.startsWith("EX")) {
                        isExterior = true;
                    }
                }
                this.validarRespuestasAlmacenadasEdificio("RESP_073", this.espacioSeleccionado.getMapaRespuestas().get(idEdificio).getValor(), idEdificio.getCodPreguntas().getNumeral());
                this.validarEspaciosFuncionalesSinCubierta();
                if (!isExterior) {
                    this.validarRespuestasExteriores(espacioSeleccionado);
                }
                TipoElemento tipo = fInstrumentos.buscarTipoElementoPorCodigo(TipoElem.ESPACIO.getCodigo());
                this.validarMapaRespuestas(espacioSeleccionado.getMapaRespuestas());
                this.validarPreguntasDependendientesElementos(tipo, espacioSeleccionado);
                if (!isExterior) {
                    this.validarRespuestaAlturaElemento(idEdificio, alturaEspacio);
                }
                for (Map.Entry respuesta : espacioSeleccionado.getMapaRespuestas().entrySet()) {
                    RespuestaDig rspDg = ((RespuestaDig) respuesta.getValue());
                    if (!UtilCadena.isNullOVacio(rspDg.getValor())) {
                        elementoAlmacenado.agregarRespuestaIniciales(rspDg);
                        //Para setear el nombre del tipo de espacio
                        if (rspDg.getRespuesta().getCodigo().equalsIgnoreCase("RESP_098_01")) {
                            TipologiaValorNombre nombreEspacioEscolar = fParametros.consultarValorTipologiaEstado(TipologiaCodigo.TIPO_ESPACIO_ESCOLAR.getValor(), UtilCadena.isNullOVacio(rspDg.getValor()) ? "" : rspDg.getValor(), usuario.getUsuario().getIdioma().getId());
                            espacioSeleccionado.setEspacioEscolar(UtilCadena.isNullOVacio(nombreEspacioEscolar) ? "" : nombreEspacioEscolar.getNombre());
                        }
                    }
                }
                //Llamado nueva funcion.
                this.validarTipologiaCieloRazo();
                //Guardo Elementos. y sus Respuestas Digitadas.
                fInstrumentos.guardarElementoDigitado(elementoAlmacenado);
                saveBitacora(getUsuarioSesion().getUsuario().getUsername(), BitacoraTransaccion.MODIFICAR_ELEMENTO, instrumentoDigitado.getBoletaCensal(), "Modulo4");
                fInstrumentos.actualizarFechaUsuarioModificacion(instrumentoDigitado, usuario.getUsuario().getUsername());
                mostrarMensajeInfo(Utilidades.obtenerMensaje("dig.guardar.espacio"), Utilidades.obtenerMensaje("dig.guardar.exito.elemento"));

            } else {
                throw new ErrorValidacion(Utilidades.obtenerMensaje("dig.guardar.instrumento.noexiste"));
            }
        } catch (ErrorValidacion e) {
            e.printStackTrace();
            mostrarMensajeError(Utilidades.obtenerMensaje("aplicacion.general.errorValidacion"), e.getMessage());
        } catch (Exception ex) {
            mostrarMensajeError(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"), ex.getMessage());
            ManejadorErrores.ingresarExcepcionEnLog(ex, DigitacionInstrumentoBean.class);
        }
    }

    public void validarRespuestaAlturaElemento(Respuesta RespIdEdificio, Respuesta alturaEspacio) {
        Respuesta alturaEdificio = fInstrumentos.obtenerRespuestaPorCodigo("RESP_074_01");
        String idEdificio = this.espacioSeleccionado.getMapaRespuestas().get(RespIdEdificio).getValor();
        Elemento edificio = fInstrumentos.obtenerElementoPorRespuestaValor(instrumentoDigitado.getId(), "RESP_073", idEdificio);
        if (edificio == null) {
            throw new ErrorValidacion(Utilidades.obtenerMensaje("aplicacion.general.error.altura.espacio"));
        }
        int altEdificio = Integer.parseInt(UtilCadena.isNullOVacio(edificio.getMapaRespuestas().get(alturaEdificio).getValor()) ? "0" : edificio.getMapaRespuestas().get(alturaEdificio).getValor());
        int altEspacio = Integer.parseInt(UtilCadena.isNullOVacio(espacioSeleccionado.getMapaRespuestas().get(alturaEspacio).getValor()) ? "0" : espacioSeleccionado.getMapaRespuestas().get(alturaEspacio).getValor());
        if (altEspacio > altEdificio) {
            throw new ErrorValidacion(Utilidades.obtenerMensaje("aplicacion.general.error.altura.espacio", idEdificio, altEdificio));
        }
    }

    public void validarRespuestasExteriores(Elemento espacio) {
        for (Respuesta rspTmp : this.respuestasExteriores) {
            if (!UtilCadena.isNullOVacio(espacio.getMapaRespuestas().get(rspTmp).getValor())) {
                if (!rspTmp.getCodigo().equals("RESP_137_01")) {
                    throw new ErrorValidacion(Utilidades.obtenerMensaje("aplicacion.general.error.respuestaExteriores", rspTmp.getCodPreguntas().getEnunciado1()));
                }
            }
        }
    }

    public void validarRespuestasAlmacenadas(String codigoRespuesta, String nuevoId) {
        if (!fInstrumentos.tieneRespuestaValido(this.instrumentoDigitado.getId(), codigoRespuesta, nuevoId)) {
            throw new ErrorValidacion(Utilidades.obtenerMensaje("aplicacion.general.error.respuestaAlmacenadas"));
        }
    }

    public void validarRespuestasAlmacenadasEdificio(String codigoRespuesta, String nuevoId, String numeralRespuesta) {
        if (identificadorEdificioEspacioValido(nuevoId)) {
            if (!nuevoId.toUpperCase().startsWith("EX")) {
                if (!fInstrumentos.tieneRespuestaValido(this.instrumentoDigitado.getId(), codigoRespuesta, nuevoId)) {
                    throw new ErrorValidacion(Utilidades.obtenerMensaje("aplicacion.general.error.respuestaAlmacenadas", numeralRespuesta));
                }
            }
        } else {
            throw new ErrorValidacion(Utilidades.obtenerMensaje("aplicacion.general.error.respuestaAlmacenadas", numeralRespuesta));
        }

    }

    public void validarRespuestasCrearEdificio(String codigoRespuesta, String nuevoId, String numeralRespuesta) {
        if (identificadorEdificioValido(nuevoId)) {
            if (!nuevoId.toUpperCase().startsWith("E") && !identificadorEdificioActual.equalsIgnoreCase(nuevoId)) {
                if (fInstrumentos.tieneRespuestaValido(this.instrumentoDigitado.getId(), codigoRespuesta, nuevoId)) {
                    throw new ErrorValidacion(Utilidades.obtenerMensaje("aplicacion.general.error.respuestaAlmacenadas.existe.identificador.edificio"));
                }
            }
        } else {
            throw new ErrorValidacion(Utilidades.obtenerMensaje("aplicacion.general.error.respuestaAlmacenadas", numeralRespuesta));
        }
    }

    private boolean identificadorEdificioEspacioValido(String valor) {
        //Se quito la validacion para espacios exteriores 19/07/2021 solo se pueden digitar E.
        valor = valor.toUpperCase();
        boolean sw = false;
        Pattern pattern = Pattern.compile("^EX?[0-9]+$");
        Matcher matcher;
        if (!UtilCadena.isNullOVacio(valor)) {
            matcher = pattern.matcher(valor.toString());
            sw = matcher.matches();
        }
        return sw;
    }

    private boolean identificadorEdificioValido(String valor) {
        //Se quito la validacion para espacios exteriores 19/07/2021 solo se pueden digitar E.
        valor = valor.toUpperCase();
        boolean sw = false;
        Pattern pattern = Pattern.compile("^E[0-9]+$");
        Matcher matcher;
        if (!UtilCadena.isNullOVacio(valor)) {
            matcher = pattern.matcher(valor.toString());
            sw = matcher.matches();
        }
        return sw;
    }

    public void validarEspaciosFuncionalesSinCubierta() {
        Respuesta interior = fInstrumentos.obtenerRespuestaPorCodigo("RESP_095");
        Respuesta funcional = fInstrumentos.obtenerRespuestaPorCodigo("RESP_099_01"); //Tipologia = 1
        //Respuesta cubierta = fInstrumentos.obtenerRespuestaPorCodigo("RESP_117_01"); //
        Respuesta cubierta = fInstrumentos.obtenerRespuestaPorCodigo("RESP_295"); //
        String strInterior = this.espacioSeleccionado.getMapaRespuestas().get(interior).getValor();
        String strFuncional = this.espacioSeleccionado.getMapaRespuestas().get(funcional).getValor();
        String strCubierta = this.espacioSeleccionado.getMapaRespuestas().get(cubierta).getValor();
        boolean interiorEspacio = false;
        if (strInterior != null) {
            strInterior = strInterior.toUpperCase().trim();
            if (!strInterior.startsWith("EX")) {
                interiorEspacio = true;
            }
        }
        if (interiorEspacio && strFuncional.equals("1") && strCubierta.equals("0")) {
            throw new ErrorValidacion(Utilidades.obtenerMensaje("guardar.espacios.interior.funcionales.cubierta"));
        }

    }

    public void validarTipologiaCieloRazo() {
        Respuesta tipologia = fInstrumentos.obtenerRespuestaPorCodigo("RESP_117_02");
        Respuesta condicion = fInstrumentos.obtenerRespuestaPorCodigo("RESP_117_04");
        String tipologiaValor = this.espacioSeleccionado.getMapaRespuestas().get(tipologia).getValor();
        String condicionValor = this.espacioSeleccionado.getMapaRespuestas().get(condicion).getValor();
        if (tipologiaValor.equalsIgnoreCase("8") && !condicionValor.equalsIgnoreCase("4")) {
            throw new ErrorValidacion(Utilidades.obtenerMensaje("guardar.espacios.interior.funcionales.tipologia"));
        }
    }

    public void agregarEstablecimientosM6S1() {
        try {
            ModuloIns modulo = fInstrumentos.obtenerModuloPorCodigo("MODULOB1");
            Seccion seccion = fInstrumentos.obtenerSeccionPorCodigo("SEC_DEAFMLSLA");
            TipoElemento tipo = fInstrumentos.buscarTipoElementoPorCodigo(TipoElem.ESTABLECIMIENTO.getCodigo());
            Elemento establecimiento = new Elemento(tipo.getDescripcion() + "_" + this.obtenerNumeroElemento(establecimientos), instrumentoDigitado, tipo);
            establecimiento.setRespuestasList(new ArrayList<RespuestaDig>());
            establecimiento.setMapaRespuestas(new HashMap<Respuesta, RespuestaDig>());
            establecimiento.setPreguntas(new ArrayList<Pregunta>());
            List<Pregunta> preguntas = fInstrumentos.obtenerPreguntaPorTipoElemento(tipo, modulo, seccion);
            for (Pregunta prg : preguntas) {
                establecimiento.agregarPregunta(prg);
                for (Respuesta rsp : prg.getRespuestaList()) {
                    RespuestaDig respuesta = new RespuestaDig(establecimiento, prg, rsp);
                    establecimiento.agregarRespuesta(respuesta);
                    establecimiento.agregarClaveMapa(rsp, respuesta);
                }
            }
            establecimientos.add(establecimiento);
            Collections.sort(establecimientos);
            saveBitacora(getUsuarioSesion().getUsuario().getUsername(), BitacoraTransaccion.CREAR_ELEMENTO, instrumentoDigitado.getBoletaCensal(), "Modulo6");
        } catch (Exception e) {
            ManejadorErrores.ingresarExcepcionEnLog(e, DigitacionInstrumentoBean.class);
        }
    }

    public void agregarPlanoEspacioM4S2() {
        try {
            ModuloIns modulo = fInstrumentos.obtenerModuloPorCodigo("MODULO4");
            Seccion seccion = fInstrumentos.obtenerSeccionPorCodigo("SEC_CEEE");
            TipoElemento tipo = fInstrumentos.buscarTipoElementoPorCodigo(TipoElem.PLANO_ESPACIO.getCodigo());
            Elemento planoEspacio = new Elemento(tipo.getDescripcion() + "_" + this.obtenerNumeroElemento(planosEspacios), instrumentoDigitado, tipo);
            planoEspacio.setRespuestasList(new ArrayList<RespuestaDig>());
            planoEspacio.setMapaRespuestas(new HashMap<Respuesta, RespuestaDig>());
            planoEspacio.setPreguntas(new ArrayList<Pregunta>());
            List<Pregunta> preguntas = fInstrumentos.obtenerPreguntaPorTipoElemento(tipo, modulo, seccion);
            for (Pregunta prg : preguntas) {
                planoEspacio.agregarPregunta(prg);
                for (Respuesta rsp : prg.getRespuestaList()) {
                    RespuestaDig respuesta = new RespuestaDig(planoEspacio, prg, rsp);
                    planoEspacio.agregarRespuesta(respuesta);
                    planoEspacio.agregarClaveMapa(rsp, respuesta);
                }
            }
            planosEspacios.add(planoEspacio);
            Collections.sort(planosEspacios);
            saveBitacora(getUsuarioSesion().getUsuario().getUsername(), BitacoraTransaccion.CREAR_ELEMENTO, instrumentoDigitado.getBoletaCensal(), "Modulo4");
        } catch (Exception e) {
            ManejadorErrores.ingresarExcepcionEnLog(e, DigitacionInstrumentoBean.class);
        }
    }

    public void agregarObservacionM5S2() {
        try {
            ModuloIns modulo = fInstrumentos.obtenerModuloPorCodigo("MODULOA1");
            Seccion seccion = fInstrumentos.obtenerSeccionPorCodigo("SEC_OCLI");
            TipoElemento tipo = fInstrumentos.buscarTipoElementoPorCodigo(TipoElem.OBSERVACIONES.getCodigo());
            Elemento observacion = new Elemento(tipo.getDescripcion() + "_" + this.obtenerNumeroElemento(observacionesComplementarias), instrumentoDigitado, tipo);
            observacion.setRespuestasList(new ArrayList<RespuestaDig>());
            observacion.setMapaRespuestas(new HashMap<Respuesta, RespuestaDig>());
            observacion.setPreguntas(new ArrayList<Pregunta>());
            List<Pregunta> preguntas = fInstrumentos.obtenerPreguntaPorTipoElemento(tipo, modulo, seccion);
            for (Pregunta prg : preguntas) {
                observacion.agregarPregunta(prg);
                for (Respuesta rsp : prg.getRespuestaList()) {
                    RespuestaDig respuesta = new RespuestaDig(observacion, prg, rsp);
                    observacion.agregarRespuesta(respuesta);
                    observacion.agregarClaveMapa(rsp, respuesta);
                }
            }
            observacionesComplementarias.add(observacion);
            Collections.sort(observacionesComplementarias);
            saveBitacora(getUsuarioSesion().getUsuario().getUsername(), BitacoraTransaccion.CREAR_ELEMENTO, instrumentoDigitado.getBoletaCensal(), "Modulo5");
        } catch (Exception e) {
            ManejadorErrores.ingresarExcepcionEnLog(e, DigitacionInstrumentoBean.class);
        }
    }

    public void agregarEdificioM3S2() {
        try {
            ModuloIns modulo = fInstrumentos.obtenerModuloPorCodigo("MODULO3");
            Seccion seccion = fInstrumentos.obtenerSeccionPorCodigo("SEC_RIGEP");
            TipoElemento tipo = fInstrumentos.buscarTipoElementoPorCodigo(TipoElem.EDIFIO.getCodigo());
            Elemento edificio = new Elemento(tipo.getDescripcion() + "_" + this.obtenerNumeroElemento(edificios), instrumentoDigitado, tipo);
            edificio.setRespuestasList(new ArrayList<RespuestaDig>());
            edificio.setMapaRespuestas(new HashMap<Respuesta, RespuestaDig>());
            edificio.setPreguntas(new ArrayList<Pregunta>());
            List<Pregunta> preguntas = fInstrumentos.obtenerPreguntaPorTipoElemento(tipo, modulo, seccion);
            preguntas.addAll(fInstrumentos.obtenerPreguntaPorTipoElemento(edificio.getTipoElemento(), fInstrumentos.obtenerModuloPorCodigo("MODULOC1"), fInstrumentos.obtenerSeccionPorCodigo("SEC_MCE")));
            preguntas.addAll(fInstrumentos.obtenerPreguntaPorTipoElemento(edificio.getTipoElemento(), fInstrumentos.obtenerModuloPorCodigo("MODULOC1"), fInstrumentos.obtenerSeccionPorCodigo("SEC_CEEP")));
            for (Pregunta prg : preguntas) {
                edificio.agregarPregunta(prg);
                for (Respuesta rsp : prg.getRespuestaList()) {
                    RespuestaDig respuesta = new RespuestaDig(edificio, prg, rsp);
                    edificio.agregarRespuesta(respuesta);
                    edificio.agregarClaveMapa(rsp, respuesta);
                }
            }
            edificios.add(edificio);
            Collections.sort(edificios);
            saveBitacora(getUsuarioSesion().getUsuario().getUsername(), BitacoraTransaccion.CREAR_ELEMENTO, instrumentoDigitado.getBoletaCensal(), "Modulo3");
        } catch (Exception e) {
            ManejadorErrores.ingresarExcepcionEnLog(e, DigitacionInstrumentoBean.class);
        }
    }

    public void agregarEspacioM4S1() {
        try {
            ModuloIns modulo = fInstrumentos.obtenerModuloPorCodigo("MODULO4");
            Seccion seccion = fInstrumentos.obtenerSeccionPorCodigo("SEC_IEE");
            TipoElemento tipo = fInstrumentos.buscarTipoElementoPorCodigo(TipoElem.ESPACIO.getCodigo());
            Elemento espacio = new Elemento(tipo.getDescripcion() + "_" + ( espacios != null?espacios.size()+1:1), instrumentoDigitado, tipo);
            espacio.setRespuestasList(new ArrayList<RespuestaDig>());
            espacio.setMapaRespuestas(new HashMap<Respuesta, RespuestaDig>());
            espacio.setPreguntas(new ArrayList<Pregunta>());
            List<Pregunta> preguntas = fInstrumentos.obtenerPreguntaPorTipoElemento(tipo, modulo, seccion);
            for (Pregunta prg : preguntas) {
                espacio.agregarPregunta(prg);
                for (Respuesta rsp : prg.getRespuestaList()) {
                    RespuestaDig respuesta = new RespuestaDig(espacio, prg, rsp);
                    espacio.agregarRespuesta(respuesta);
                    espacio.agregarClaveMapa(rsp, respuesta);
                }
            }
            //Setear el numeroEspacio para su posicion en la lista
            espacio.initSomeTransient();
            espacios.add(espacio);
            Collections.sort(espacios);
            saveBitacora(getUsuarioSesion().getUsuario().getUsername(), BitacoraTransaccion.CREAR_ELEMENTO, instrumentoDigitado.getBoletaCensal(), "Modulo4");
        } catch (Exception e) {
            ManejadorErrores.ingresarExcepcionEnLog(e, DigitacionInstrumentoBean.class);
        }
    }

    private int obtenerNumeroElemento(List<Elemento> elementos) {
        int numero = 1;
        int posList = 0;
        boolean sw = true;
        Integer[] numeros = new Integer[5000];
        if (elementos != null && !elementos.isEmpty()) {
            //Armo el vector con los numeros de los elementos.
            for (Elemento elemento : elementos) {
                String numeroLista = elemento.getDescripcion();
                numeroLista = numeroLista.replaceAll(elemento.getTipoElemento().getDescripcion() + "_", "");
                numeros[Integer.valueOf(numeroLista)] = Integer.valueOf(numeroLista);
            }
            while (sw) {
                if (posList != 0 && numeros[posList] == null) {
                    numero = posList;
                    sw = false;
                } else {
                    posList++;
                }
            }

        }
        return numero;
    }

    public void guardarInstrumento(boolean emitir) {
        try {
            predio = null;
            this.renderRespNoEmitidas = false;
            Date fechaEncuestaDate = null;
            //this.limpiarMapaErrores();
            this.erroresMap = new HashMap<Pregunta, String>();
            this.validarMapaRespuestas();
            RespuestaDig fechaEncuestaDig = this.obtenerRespuestaDigitada("RESP_001");
            RespuestaDig predioDig = this.obtenerRespuestaDigitada("RESP_010");
            RespuestaDig sedeDig = this.obtenerRespuestaDigitada("RESP_011");
            RespuestaDig establecimientoDig = this.obtenerRespuestaDigitada("RESP_023");
            RespuestaDig encuestador_1 = this.obtenerRespuestaDigitada("RESP_005_01");
            RespuestaDig encuestador_2 = this.obtenerRespuestaDigitada("RESP_005_02");
            RespuestaDig supervisor = this.obtenerRespuestaDigitada("RESP_006");
            RespuestaDig boletaCensal = this.obtenerRespuestaDigitada("RESP_004");

            //Valido si el instrumento tiene la fecha de la encuesta, la boleta censal y el id del lote.
            this.validacionesBasicasInstrumento(fechaEncuestaDig, predioDig, establecimientoDig, sedeDig);
            //Valido Informacion de Establecimiento Sede Predio
            this.validarEstablecimiento(predioDig, sedeDig, establecimientoDig);
            //Valido que no exista un mismo instrumento con la misma sede,predio, establecimiento, estado para el periodo.
            if (idInstrumentoDigitado == null || idInstrumentoDigitado.equals("")) {
                this.validarInstrumentoRepetido();
            }
            //Valido Encuestadores y Supervisor
            this.validarEncuestadorSupervisor(encuestador_1, encuestador_2, supervisor);

            //Valido la propiedad del lote.
            this.validarPropietario();
            //Validaciones Dependientes.
            this.validacionesDependientes();
            //Validamos Jornadas
            this.validarJornadasTandas();
            //Validar Preguntas Edificio MB1
            this.validarPreguntasEspacios();
            //Validar Respuesta Obligatorias
            this.validarRespuestaObligatorias();


            if (isDateFormat(((RespuestaDig) respuestasDigitadasMap.get(fechaEncuestaDig.getRespuesta())).getValor())) {
                fechaEncuestaDate = UtilFecha.convetirStringDate(UtilCadena.validarNullVacio(((RespuestaDig) respuestasDigitadasMap.get(fechaEncuestaDig.getRespuesta())).getValor()));
            } else {
                erroresMap.put(fechaEncuestaDig.getPregunta(), Utilidades.obtenerMensaje("dig.guardar.tipodato.fecha"));
                throw new ErrorValidacion(Utilidades.obtenerMensaje("dig.guardar.error.general"));
            }

            if (idInstrumentoDigitado != null && !idInstrumentoDigitado.equals("")) {
                this.cargarDatosIniciales();
            }

            instrumentoDigitado.setInstrumento(instrumentoVigente);
            instrumentoDigitado.setFechaEncuesta(fechaEncuestaDate);
            if (UtilCadena.isNullOVacio(instrumentoDigitado.getVersion())) {
                instrumentoDigitado.setVersion(1);
            }
            instrumentoDigitado.setUsuarioCreacion(getUsuarioSesion().getUsuario().getUsername());
            instrumentoDigitado.setEstablecimiento(establecimiento);
            instrumentoDigitado.setSede(sede);
            instrumentoDigitado.setPredio(predio);

            Nivel nivel0 = UtilCadena.isNullOVacio(seleccionNivel0) ? padreNivel0 : this.obtenerNivel(seleccionNivel0);
            Nivel nivel1 = UtilCadena.isNullOVacio(seleccionNivel1) ? padreNivel1 : this.obtenerNivel(seleccionNivel1);
            Nivel nivel2 = UtilCadena.isNullOVacio(seleccionNivel2) ? padreNivel2 : this.obtenerNivel(seleccionNivel2);
            Nivel nivel3 = UtilCadena.isNullOVacio(seleccionNivel3) ? padreNivel3 : this.obtenerNivel(seleccionNivel3);
            Nivel nivel4 = UtilCadena.isNullOVacio(seleccionNivel4) ? padreNivel4 : this.obtenerNivel(seleccionNivel4);
            Nivel nivel5 = UtilCadena.isNullOVacio(seleccionNivel5) ? padreNivel5 : this.obtenerNivel(seleccionNivel5);

            instrumentoDigitado.setNivel0(nivel0);
            instrumentoDigitado.setNivel1(nivel1);
            instrumentoDigitado.setNivel2(nivel2);
            instrumentoDigitado.setNivel3(nivel3);
            instrumentoDigitado.setNivel4(nivel4);
            instrumentoDigitado.setNivel5(nivel5);

            if (instrumentoDigitado.getId() == null || (fInstrumentos.consultarInstrumentoPorCodigo(String.valueOf(instrumentoDigitado.getId())) == null)) {
                instrumentoDigitado.setBoletaCensal(fInstrumentos.obtenerMaximaBoletaCensal());
                boletaCensal.setValor(instrumentoDigitado.getBoletaCensal());
                respuestasDigitadasMap.put(boletaCensal.getRespuesta(), boletaCensal);
                instrumentoDigitado = fInstrumentos.insertarInstrumentoDigitado(instrumentoDigitado);
                //QuitarModulo 4 y 5
                saveBitacora(getUsuarioSesion().getUsuario().getUsername(), BitacoraTransaccion.CREAR_INSTRUMENTO, instrumentoDigitado.getBoletaCensal(), "");
            } else {
                //Se debe advertir al usuario que si quiere crear uno nuevo.
                instrumentoDigitado = fInstrumentos.modificarInstrumento(instrumentoDigitado);
                saveBitacora(getUsuarioSesion().getUsuario().getUsername(), BitacoraTransaccion.MODIFICAR_INSTRUMENTO, instrumentoDigitado.getBoletaCensal(), step);
            }
            //Incluyo dentro del mapa de respuestas las correspondientes a la DPA
            this.prepararMapaRespuestasDigitadas();
            gestionVariable.setIdInstrumentoDigitado(instrumentoDigitado.getId().toString());

            //Saco los elementos de las respuesta cuyo valor sea distinto de vacio.
            Map<String, Elemento> elementosDigitados = new HashMap<String, Elemento>();
            for (Map.Entry respuesta : respuestasDigitadasMap.entrySet()) {
                if (!UtilCadena.isNullOVacio(((RespuestaDig) respuesta.getValue()).getValor())) {
                    RespuestaDig rspDg = (RespuestaDig) respuesta.getValue();
                    rspDg.getElemento().limpiarRespuesta();
                    elementosDigitados.put(rspDg.getElemento().getDescripcion(), rspDg.getElemento());
                }
            }

            //Asocio los elementos con las respuesta digitadas.
            for (Map.Entry respuesta : respuestasDigitadasMap.entrySet()) {
                if (!UtilCadena.isNullOVacio(((RespuestaDig) respuesta.getValue()).getValor())) {
                    RespuestaDig rspDg = (RespuestaDig) respuesta.getValue();
                    for (Map.Entry elemento : elementosDigitados.entrySet()) {
                        Elemento elm = (Elemento) elemento.getValue();
                        if (rspDg.getElemento().equals(elm)) {
                            elm.agregarRespuestaIniciales(rspDg);
                        }
                    }
                }
            }
            //Almaceno los Elementos Unicos.
            for (Map.Entry elemento : elementosDigitados.entrySet()) {
                Elemento elm = (Elemento) elemento.getValue();
                if (elm.getTipoElemento().getCodigo().equals(TipoElem.PREDIO.getCodigo()) || elm.getDescripcion().equals("ESTABLECIMIENTO_1")) {
                    elm.setEstablecimiento(establecimiento);
                    elm.setSede(sede);
                    fInstrumentos.guardarElementoDigitadoUnicos(elm);
                }
            }

            if (idInstrumentoDigitado == null || idInstrumentoDigitado.equals("")) {
                idInstrumentoDigitado = instrumentoDigitado.getId().toString();
            }
            if (emitir) {
                this.emitirInstrumento();
            } else {
                fInstrumentos.actualizarFechaUsuarioModificacion(instrumentoDigitado, usuario.getUsuario().getUsername());
                mostrarMensajeInfo(Utilidades.obtenerMensaje("dig.guardar.titulo"), Utilidades.obtenerMensaje("dig.guardar.exito"));
            }

        } catch (ErrorValidacion ex) {
            errores.manejarExcepcion(ex);
        } catch (Exception ex) {
            Utilidades.mostrarMensajeFatal(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"), Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"));
            ManejadorErrores.ingresarExcepcionEnLog(ex, DigitacionInstrumentoBean.class);
        }
    }

    private void saveBitacora(String usuario, BitacoraTransaccion bp, String idBoleta, String moduloDigitacion) {
        RegistrarAuditoriaDigitacion bitacora = new RegistrarAuditoriaDigitacion();
        bitacora.setBitacoraTransaccion(bp);
        bitacora.setUsuario(usuario);
        bitacora.setBoletaCensal(idBoleta);
        bitacora.setModuloDigitacion(moduloDigitacion);
        bitacora.setFecha(UtilFecha.obtenerFechaActual());
        bitacora.run();
    }

    private void validarPreguntasEspacios() {
        RespuestaDig resp_209 = this.obtenerRespuestaDigitada("RESP_209");
        RespuestaDig resp_211 = this.obtenerRespuestaDigitada("RESP_211");
        RespuestaDig resp_213 = this.obtenerRespuestaDigitada("RESP_213");
        RespuestaDig resp_215 = this.obtenerRespuestaDigitada("RESP_215");
        RespuestaDig resp_217 = this.obtenerRespuestaDigitada("RESP_217");
        RespuestaDig resp_221 = this.obtenerRespuestaDigitada("RESP_221");
        RespuestaDig resp_223 = this.obtenerRespuestaDigitada("RESP_223");
        RespuestaDig resp_228 = this.obtenerRespuestaDigitada("RESP_228");
        RespuestaDig resp_230 = this.obtenerRespuestaDigitada("RESP_230");
        RespuestaDig resp_232 = this.obtenerRespuestaDigitada("RESP_232");
        RespuestaDig resp_234 = this.obtenerRespuestaDigitada("RESP_234");
        RespuestaDig resp_236 = this.obtenerRespuestaDigitada("RESP_236");
        String valor_209 = ((RespuestaDig) respuestasDigitadasMap.get(resp_209.getRespuesta())).getValor();
        String valor_211 = ((RespuestaDig) respuestasDigitadasMap.get(resp_211.getRespuesta())).getValor();
        String valor_213 = ((RespuestaDig) respuestasDigitadasMap.get(resp_213.getRespuesta())).getValor();
        String valor_215 = ((RespuestaDig) respuestasDigitadasMap.get(resp_215.getRespuesta())).getValor();
        String valor_217 = ((RespuestaDig) respuestasDigitadasMap.get(resp_217.getRespuesta())).getValor();
        String valor_221 = ((RespuestaDig) respuestasDigitadasMap.get(resp_221.getRespuesta())).getValor();
        String valor_223 = ((RespuestaDig) respuestasDigitadasMap.get(resp_223.getRespuesta())).getValor();
        String valor_228 = ((RespuestaDig) respuestasDigitadasMap.get(resp_228.getRespuesta())).getValor();
        String valor_230 = ((RespuestaDig) respuestasDigitadasMap.get(resp_230.getRespuesta())).getValor();
        String valor_232 = ((RespuestaDig) respuestasDigitadasMap.get(resp_232.getRespuesta())).getValor();
        String valor_234 = ((RespuestaDig) respuestasDigitadasMap.get(resp_234.getRespuesta())).getValor();
        String valor_236 = ((RespuestaDig) respuestasDigitadasMap.get(resp_236.getRespuesta())).getValor();
        if (valor_209 != null && valor_209.trim().length() > 0) {
            this.validarCadenaCorrespondiente(valor_209, resp_209);
        }
        if (valor_211 != null && valor_211.trim().length() > 0) {
            this.validarCadenaCorrespondiente(valor_211, resp_211);
        }
        if (valor_213 != null && valor_213.trim().length() > 0) {
            this.validarCadenaCorrespondiente(valor_213, resp_213);
        }
        if (valor_215 != null && valor_215.trim().length() > 0) {
            this.validarCadenaCorrespondiente(valor_215, resp_215);
        }
        if (valor_217 != null && valor_217.trim().length() > 0) {
            this.validarCadenaCorrespondiente(valor_217, resp_217);
        }
        if (valor_221 != null && valor_221.trim().length() > 0) {
            this.validarCadenaCorrespondiente(valor_221, resp_221);
        }
        if (valor_223 != null && valor_223.trim().length() > 0) {
            this.validarCadenaCorrespondiente(valor_223, resp_223);
        }
        if (valor_228 != null && valor_228.trim().length() > 0) {
            this.validarCadenaCorrespondiente(valor_228, resp_228);
        }
        if (valor_230 != null && valor_230.trim().length() > 0) {
            this.validarCadenaCorrespondiente(valor_230, resp_230);
        }
        if (valor_232 != null && valor_232.trim().length() > 0) {
            this.validarCadenaCorrespondiente(valor_232, resp_232);
        }
        if (valor_234 != null && valor_234.trim().length() > 0) {
            this.validarCadenaCorrespondiente(valor_234, resp_234);
        }
        if (valor_236 != null && valor_236.trim().length() > 0) {
            this.validarCadenaCorrespondiente(valor_236, resp_236);
        }
    }

    public void validarCadenaCorrespondiente(String cadena, RespuestaDig respuesta) {
        try {
            String[] datos = cadena.split("_");
            if (datos != null && datos.length == 3) {
                String edificio = datos[0].trim();
                String espacio = datos[1].trim();
                String piso = datos[2].trim();
                if (!fInstrumentos.tieneRespuestaCorrespondiente(this.instrumentoDigitado, edificio, espacio, piso)) {
                    erroresMap.put(respuesta.getPregunta(), Utilidades.obtenerMensaje("aplicacion.general.error.respuestaAlmacenadas.correspondientes"));
                    throw new ErrorValidacion(Utilidades.obtenerMensaje("dig.guardar.error.general"));
                }
            } else {
                erroresMap.put(respuesta.getPregunta(), Utilidades.obtenerMensaje("aplicacion.general.error.respuestaAlmacenadas.vacias"));
                throw new ErrorValidacion(Utilidades.obtenerMensaje("dig.guardar.error.general"));
            }
        } catch (Exception ex) {
            if (erroresMap.get(respuesta.getPregunta()) == null) {
                erroresMap.put(respuesta.getPregunta(), Utilidades.obtenerMensaje("aplicacion.general.error.respuestaAlmacenadas.vacias"));
            }
            throw new ErrorValidacion(Utilidades.obtenerMensaje("dig.guardar.error.general"));
        }

    }

    private void cargarDatosIniciales() {
        InstrumentoDig instrumento = fInstrumentos.consultarInstrumentoPorCodigo(idInstrumentoDigitado);
        instrumentoDigitado.setEstablecimientoInicial(instrumento.getEstablecimiento());
        instrumentoDigitado.setSedeInicial(instrumento.getSede());
        instrumentoDigitado.setPredioInicial(instrumento.getPredio());
    }

    private void validarInstrumentoRepetido() {
        boolean existeRepetido = fInstrumentos.existeInstrumentoRepetido(sede, establecimiento, predio, UtilFecha.obtenerPeriodoActual());
        if (existeRepetido) {
            EstadoInstrumento estadoInstrumento = fInstrumentos.estadoInstrumentoRepetido(sede, establecimiento, predio);
            String estadoInstrumentoRepetido = Utilidades.obtenerMensaje(estadoInstrumento.getEtiqueta());
            throw new ErrorValidacion(Utilidades.obtenerMensaje("dig.guardar.instrumento.repetido", estadoInstrumentoRepetido));
        }
    }

    private Nivel obtenerNivel(NivelDPA nivelSeleccionado) {
        if (nivelSeleccionado != null) {
            return fDpa.getNivelPorCodigo(nivelSeleccionado.getCodigo());
        } else {
            return null;
        }
    }

    public void validarRespuestaObligatorias() {
        for (Respuesta rsp : respuestasObligatorias) {
            RespuestaDig respValidar = this.respuestasDigitadasMap.get(rsp.getRespuestaObligatoria());
            RespuestaDig respPrincipal = this.respuestasDigitadasMap.get(rsp);
            if (UtilCadena.isNullOVacio(respValidar.getValor()) && !UtilCadena.isNullOVacio(respPrincipal.getValor())) {
                String msj = Utilidades.obtenerMensaje("dig.guardar.error.general.inconsistencia.coma")
                        + ";"
                        + Utilidades.obtenerMensaje(rsp.getCodPreguntas().getModulo().getEtiqueta())
                        + " > "
                        + Utilidades.obtenerMensaje(rsp.getCodPreguntas().getSeccion().getEtiqueta())
                        + " > "
                        + rsp.getCodPreguntas().getNumeral();

                erroresMap.put(rsp.getCodPreguntas(), Utilidades.obtenerMensaje("dig.guardar.inconsistencia.obligatorio"));
                throw new ErrorValidacion(msj);
            }
        }
    }

    public void validacionesDependientes() {
        //Saco las respuesta que tengan la resp validacion a ellas mismas.
        List<Respuesta> respuestasPrincipales = fInstrumentos.obtenerRespuestaPrincipales(this.instrumentoVigente.getCodigo());
        for (Respuesta rsp : respuestasPrincipales) {
            RespuestaDig rspPrincial = this.obtenerRespuestaDigitada(rsp.getCodigo());
            String valorRespPrincipal = this.respuestasDigitadasMap.get(rspPrincial.getRespuesta()).getValor();
            RespuestaDig rspDigMapa = this.respuestasDigitadasMap.get(rspPrincial.getRespuesta());
            List<Respuesta> respuestasDependientes = fInstrumentos.obtenerRespuestaDependiente(rsp.getCodigo());
            boolean isFill = false;
            for (Respuesta rspDep : respuestasDependientes) {
                if (!UtilCadena.isNullOVacio(this.respuestasDigitadasMap.get(rspDep).getValor())) {
                    isFill = true;
                    break;
                }
            }
            if (!UtilCadena.isNullOVacio(valorRespPrincipal)) {
                String msj = Utilidades.obtenerMensaje("dig.guardar.error.general.inconsistencia.coma")
                        + ";"
                        + Utilidades.obtenerMensaje(rspDigMapa.getPregunta().getModulo().getEtiqueta())
                        + " > "
                        + Utilidades.obtenerMensaje(rspDigMapa.getPregunta().getSeccion().getEtiqueta())
                        + " > "
                        + rspDigMapa.getPregunta().getNumeral();
                if (rsp.getCodigo().equals("RESP_049")
                        || rsp.getCodigo().equals("RESP_051")
                        || rsp.getCodigo().equals("RESP_053")) {

                    if (valorRespPrincipal.equals("0") && !isFill) {
                        erroresMap.put(rspPrincial.getPregunta(), Utilidades.obtenerMensaje("dig.guardar.inconsistencia.riesgos"));
                        throw new ErrorValidacion(msj);
                    }
                    if (valorRespPrincipal.equals("1") && isFill) {
                        erroresMap.put(rspPrincial.getPregunta(), Utilidades.obtenerMensaje("dig.guardar.inconsistencia.riesgos.quitar.no"));
                        throw new ErrorValidacion(msj);
                    }
                } else {
                    if (valorRespPrincipal.equals("1") && !isFill) {
                        erroresMap.put(rspPrincial.getPregunta(), Utilidades.obtenerMensaje("dig.guardar.inconsistencia.riesgos"));
                        throw new ErrorValidacion(msj);
                    }
                    if (valorRespPrincipal.equals("0") && isFill) {
                        erroresMap.put(rspPrincial.getPregunta(), Utilidades.obtenerMensaje("dig.guardar.inconsistencia.riesgos.quitar"));
                        throw new ErrorValidacion(msj);
                    }
                }
            }
        }
    }

    public void validarJornadasTandas() {
        Pregunta jornadaIzq = fInstrumentos.obtenerPreguntaPorCodigo("PREG_032");
        Pregunta jornadaDer = fInstrumentos.obtenerPreguntaPorCodigo("PREG_036");
        Set<String> izq = new HashSet<String>();
        Set<String> der = new HashSet<String>();
        //Valido que las jornadas sean correspondientes.
        for (Respuesta rspDep : jornadaIzq.getRespuestaList()) {
            if (!UtilCadena.isNullOVacio(this.respuestasDigitadasMap.get(rspDep).getValor())) {
                izq.add(this.respuestasDigitadasMap.get(rspDep).getValor());
            }
        }
        for (Respuesta rspDep : jornadaDer.getRespuestaList()) {
            if (!UtilCadena.isNullOVacio(this.respuestasDigitadasMap.get(rspDep).getValor())) {
                der.add(this.respuestasDigitadasMap.get(rspDep).getValor());
            }
        }

        //Comparar si los de la Izq tienen su correspondiente en la derecha.
        boolean contiene = true;
        for (String jornada : izq) {
            if (!der.contains(jornada)) {
                contiene = false;
                break;
            }
        }
        if (!contiene) {
            String msj = Utilidades.obtenerMensaje("dig.guardar.error.general.inconsistencia.jornadas.contraria")
                    + ";"
                    + Utilidades.obtenerMensaje(jornadaIzq.getModulo().getEtiqueta())
                    + " > "
                    + Utilidades.obtenerMensaje(jornadaIzq.getSeccion().getEtiqueta())
                    + " > "
                    + jornadaIzq.getNumeral();
            throw new ErrorValidacion(msj);
        }
        //Verificar que el mapa de la derecha no tenga elementos huerfanos con respecto al de la izquierda.
        contiene = true;
        for (String jornada : der) {
            if (!izq.contains(jornada)) {
                contiene = false;
                break;
            }
        }
        if (!contiene) {
            String msj = Utilidades.obtenerMensaje("dig.guardar.error.general.inconsistencia.jornadas")
                    + ";"
                    + Utilidades.obtenerMensaje(jornadaIzq.getModulo().getEtiqueta())
                    + " > "
                    + Utilidades.obtenerMensaje(jornadaIzq.getSeccion().getEtiqueta())
                    + " > "
                    + jornadaIzq.getNumeral();
            throw new ErrorValidacion(msj);
        }

        //Verifico que el usuario haya completado los valores de la fila
        boolean isCompleteRowLeft = true;
        boolean isCompleteRowRight = true;
        //Busco las preguntas de la misma fila.
        for (Respuesta rspDep : jornadaIzq.getRespuestaList()) {
            if (!UtilCadena.isNullOVacio(this.respuestasDigitadasMap.get(rspDep).getValor())) {
                for (Respuesta rspFila : fInstrumentos.obtenerRespuestaPorFila(rspDep)) {
                    if (UtilCadena.isNullOVacio(this.respuestasDigitadasMap.get(rspFila).getValor())) {
                        isCompleteRowLeft = false;
                        break;
                    }
                }
            }
        }

        if (!isCompleteRowLeft) {
            String msj = Utilidades.obtenerMensaje("dig.guardar.error.jornadas.estudiantes.incompletas")
                    + ";"
                    + Utilidades.obtenerMensaje(jornadaIzq.getModulo().getEtiqueta())
                    + " > "
                    + Utilidades.obtenerMensaje(jornadaIzq.getSeccion().getEtiqueta())
                    + " > "
                    + jornadaIzq.getNumeral();
            throw new ErrorValidacion(msj);
        }
        //

        for (Respuesta rspDep : jornadaDer.getRespuestaList()) {
            if (!UtilCadena.isNullOVacio(this.respuestasDigitadasMap.get(rspDep).getValor())) {
                for (Respuesta rspFila : fInstrumentos.obtenerRespuestaPorFila(rspDep)) {
                    if (UtilCadena.isNullOVacio(this.respuestasDigitadasMap.get(rspFila).getValor())) {
                        isCompleteRowRight = false;
                        break;
                    }
                }
            }
        }

        if (!isCompleteRowRight) {
            String msj = Utilidades.obtenerMensaje("dig.guardar.error.jornadas.docentes.incompletas")
                    + ";"
                    + Utilidades.obtenerMensaje(jornadaDer.getModulo().getEtiqueta())
                    + " > "
                    + Utilidades.obtenerMensaje(jornadaDer.getSeccion().getEtiqueta())
                    + " > "
                    + jornadaDer.getNumeral();
            throw new ErrorValidacion(msj);
        }


    }

    public void validarEncuestadoresSupervisoresEstablecimientos() {
        Respuesta encuestador1 = fInstrumentos.obtenerRespuestaPorCodigo("RESP_191_01");
        Respuesta encuestador2 = fInstrumentos.obtenerRespuestaPorCodigo("RESP_191_02");
        Respuesta supervisor = fInstrumentos.obtenerRespuestaPorCodigo("RESP_192");
        String encuestador1Valor = establecimientoSeleccionado.getMapaRespuestas().get(encuestador1).getValor();
        String encuestador2Valor = establecimientoSeleccionado.getMapaRespuestas().get(encuestador2).getValor();
        String supervisorValor = establecimientoSeleccionado.getMapaRespuestas().get(supervisor).getValor();

        if (!UtilCadena.isNullOVacio(encuestador1Valor)) {
            if (fInstrumentos.obtenerEncuestadorPorCodigo(encuestador1Valor) == null) {
                String msj = Utilidades.obtenerMensaje("dig.guardar.error.establecimiento.inconsistencia.encuestador",
                        encuestador1.getCodPreguntas().getNumeral(),
                        encuestador1.getCodPreguntas().getEnunciado1());
                throw new ErrorValidacion(msj);
            }
        }

        if (!UtilCadena.isNullOVacio(encuestador2Valor)) {
            if (fInstrumentos.obtenerEncuestadorPorCodigo(encuestador2Valor) == null) {
                String msj = Utilidades.obtenerMensaje("dig.guardar.error.establecimiento.inconsistencia.encuestador",
                        encuestador2.getCodPreguntas().getNumeral(),
                        encuestador2.getCodPreguntas().getEnunciado1());
                throw new ErrorValidacion(msj);
            }
        }
        if (!UtilCadena.isNullOVacio(supervisorValor)) {
            if (fInstrumentos.obtenerSupervisorPorCodigo(supervisorValor) == null) {
                String msj = Utilidades.obtenerMensaje("dig.guardar.error.establecimiento.inconsistencia.supervisor",
                        supervisor.getCodPreguntas().getNumeral(),
                        supervisor.getCodPreguntas().getEnunciado1());
                throw new ErrorValidacion(msj);
            }
        }

    }

    public void validarJornadasTandasEstablecimiento(Elemento establecimiento) {
        Pregunta jornadaIzq = fInstrumentos.obtenerPreguntaPorCodigo("PREG_198");
        Pregunta jornadaDer = fInstrumentos.obtenerPreguntaPorCodigo("PREG_202");
        Set<String> izq = new HashSet<String>();
        Set<String> der = new HashSet<String>();
        //Valido que las jornadas sean correspondientes.
        for (Respuesta rspDep : jornadaIzq.getRespuestaList()) {
            if (!UtilCadena.isNullOVacio(establecimiento.getMapaRespuestas().get(rspDep).getValor())) {
                izq.add(establecimiento.getMapaRespuestas().get(rspDep).getValor());
            }
        }
        for (Respuesta rspDep : jornadaDer.getRespuestaList()) {
            if (!UtilCadena.isNullOVacio(establecimiento.getMapaRespuestas().get(rspDep).getValor())) {
                der.add(establecimiento.getMapaRespuestas().get(rspDep).getValor());
            }
        }

        //Comparar si los de la Izq tienen su correspondiente en la derecha.
        boolean contiene = true;
        for (String jornada : izq) {
            if (!der.contains(jornada)) {
                contiene = false;
                break;
            }
        }
        if (!contiene) {
            String msj = Utilidades.obtenerMensaje("dig.guardar.error.general.inconsistencia.jornadas.contraria")
                    + "  "
                    + jornadaIzq.getNumeral();
            throw new ErrorValidacion(msj);
        }
        //Verificar que el mapa de la derecha no tenga elementos huerfanos con respecto al de la izquierda.
        contiene = true;
        for (String jornada : der) {
            if (!izq.contains(jornada)) {
                contiene = false;
                break;
            }
        }
        if (!contiene) {
            String msj = Utilidades.obtenerMensaje("dig.guardar.error.general.inconsistencia.jornadas")
                    + "  "
                    + jornadaIzq.getNumeral();
            throw new ErrorValidacion(msj);
        }

        //Verifico que el usuario haya completado los valores de la fila
        boolean isCompleteRowLeft = true;
        boolean isCompleteRowRight = true;
        //Busco las preguntas de la misma fila.
        for (Respuesta rspDep : jornadaIzq.getRespuestaList()) {
            if (!UtilCadena.isNullOVacio(establecimiento.getMapaRespuestas().get(rspDep).getValor())) {
                for (Respuesta rspFila : fInstrumentos.obtenerRespuestaPorFila(rspDep)) {
                    if (UtilCadena.isNullOVacio(establecimiento.getMapaRespuestas().get(rspFila).getValor())) {
                        isCompleteRowLeft = false;
                        break;
                    }
                }
            }
        }

        if (!isCompleteRowLeft) {
            String msj = Utilidades.obtenerMensaje("dig.guardar.error.jornadas.estudiantes.incompletas");
            throw new ErrorValidacion(msj);
        }
        //

        for (Respuesta rspDep : jornadaDer.getRespuestaList()) {
            if (!UtilCadena.isNullOVacio(establecimiento.getMapaRespuestas().get(rspDep).getValor())) {
                for (Respuesta rspFila : fInstrumentos.obtenerRespuestaPorFila(rspDep)) {
                    if (UtilCadena.isNullOVacio(establecimiento.getMapaRespuestas().get(rspFila).getValor())) {
                        isCompleteRowRight = false;
                        break;
                    }
                }
            }
        }

        if (!isCompleteRowRight) {
            String msj = Utilidades.obtenerMensaje("dig.guardar.error.jornadas.docentes.incompletas");
            throw new ErrorValidacion(msj);
        }


    }

    public void validarPropietario() {
        RespuestaDig propiedadLote = this.obtenerRespuestaDigitada("RESP_055_01");
        RespuestaDig loteArriendo = this.obtenerRespuestaDigitada("RESP_058");
        String valorPropiedadLote = this.respuestasDigitadasMap.get(propiedadLote.getRespuesta()).getValor();
        String valorLoteArriendo = this.respuestasDigitadasMap.get(loteArriendo.getRespuesta()).getValor();
        if (!UtilCadena.isNullOVacio(valorPropiedadLote) && !UtilCadena.isNullOVacio(valorLoteArriendo)) {
            if (valorPropiedadLote.equals("1") && valorLoteArriendo.equals("1")) {
                erroresMap.put(loteArriendo.getPregunta(), Utilidades.obtenerMensaje("dig.guardar.inconsistencia.lote"));
                String msj = Utilidades.obtenerMensaje("dig.guardar.error.general.inconsistencia")
                        + ";"
                        + Utilidades.obtenerMensaje(loteArriendo.getPregunta().getModulo().getEtiqueta())
                        + " > "
                        + Utilidades.obtenerMensaje(loteArriendo.getPregunta().getSeccion().getEtiqueta())
                        + " > "
                        + loteArriendo.getPregunta().getNumeral();
                throw new ErrorValidacion(msj);
            }
        }

    }

    public void validarEncuestadorSupervisor(RespuestaDig encuestador_1, RespuestaDig encuestador_2, RespuestaDig supervisor) {
        String supervisorValor = ((RespuestaDig) respuestasDigitadasMap.get(supervisor.getRespuesta())).getValor();
        String encuestador_1Valor = ((RespuestaDig) respuestasDigitadasMap.get(encuestador_1.getRespuesta())).getValor();
        String encuestador_2Valor = ((RespuestaDig) respuestasDigitadasMap.get(encuestador_2.getRespuesta())).getValor();
        if (!UtilCadena.isNullOVacio(supervisorValor)) {
            if (fInstrumentos.obtenerSupervisorPorCodigo(supervisorValor) == null) {
                erroresMap.put(supervisor.getPregunta(), Utilidades.obtenerMensaje("dig.guardar.invalido.estado", Utilidades.obtenerMensaje(supervisor.getPregunta().getEtiqueta1())));
                String msj = Utilidades.obtenerMensaje("dig.guardar.error.general.inconsistencia")
                        + ";"
                        + Utilidades.obtenerMensaje(supervisor.getPregunta().getModulo().getEtiqueta())
                        + " > "
                        + Utilidades.obtenerMensaje(supervisor.getPregunta().getSeccion().getEtiqueta())
                        + " > "
                        + supervisor.getPregunta().getNumeral();
                throw new ErrorValidacion(msj);
            }
        }

        if (!UtilCadena.isNullOVacio(encuestador_1Valor)) {
            if (fInstrumentos.obtenerEncuestadorPorCodigo(encuestador_1Valor) == null) {
                erroresMap.put(encuestador_1.getPregunta(), Utilidades.obtenerMensaje("dig.guardar.invalido.estado", Utilidades.obtenerMensaje(encuestador_1.getPregunta().getEtiqueta1())));
                String msj = Utilidades.obtenerMensaje("dig.guardar.error.general.inconsistencia")
                        + ";"
                        + Utilidades.obtenerMensaje(encuestador_1.getPregunta().getModulo().getEtiqueta())
                        + " > "
                        + Utilidades.obtenerMensaje(encuestador_1.getPregunta().getSeccion().getEtiqueta())
                        + " > "
                        + encuestador_1.getPregunta().getNumeral();
                throw new ErrorValidacion(msj);
            }
        }

        if (!UtilCadena.isNullOVacio(encuestador_2Valor)) {
            if (fInstrumentos.obtenerEncuestadorPorCodigo(encuestador_2Valor) == null) {
                erroresMap.put(encuestador_2.getPregunta(), Utilidades.obtenerMensaje("dig.guardar.invalido", Utilidades.obtenerMensaje(encuestador_2.getPregunta().getEtiqueta1())));
                String msj = Utilidades.obtenerMensaje("dig.guardar.error.general.inconsistencia")
                        + ";"
                        + Utilidades.obtenerMensaje(encuestador_2.getPregunta().getModulo().getEtiqueta())
                        + " > "
                        + Utilidades.obtenerMensaje(encuestador_2.getPregunta().getSeccion().getEtiqueta())
                        + " > "
                        + encuestador_2.getPregunta().getNumeral();
                throw new ErrorValidacion(msj);
            }
        }
    }

    public void validarEstablecimiento(RespuestaDig predioDig, RespuestaDig sedeDig, RespuestaDig establecimientoDig) {
        predio = fInstrumentos.obtenerPredioPorCodigo(((RespuestaDig) respuestasDigitadasMap.get(predioDig.getRespuesta())).getValor());
        if (predio == null) {
            erroresMap.put(predioDig.getPregunta(), Utilidades.obtenerMensaje("dig.guardar.invalido", "Predio"));
            String msj = Utilidades.obtenerMensaje("dig.guardar.error.general")
                    + ";"
                    + Utilidades.obtenerMensaje(predioDig.getPregunta().getModulo().getEtiqueta())
                    + " > "
                    + Utilidades.obtenerMensaje(predioDig.getPregunta().getSeccion().getEtiqueta())
                    + " > "
                    + predioDig.getPregunta().getNumeral();
            throw new ErrorValidacion(msj);
        }

        //Pregunto si las preguntas esta en blanco o no.
        String codSede = ((RespuestaDig) respuestasDigitadasMap.get(sedeDig.getRespuesta())).getValor();
        if (!UtilCadena.isNullOVacio(codSede)) {
            //Consulto a sede en la BD
            sede = fEstablecimiento.getSedePorCodigo(codSede);
            if (sede == null) {
                erroresMap.put(sedeDig.getPregunta(), Utilidades.obtenerMensaje("dig.guardar.invalida", "Sede"));
                String msj = Utilidades.obtenerMensaje("dig.guardar.error.general")
                        + ";"
                        + Utilidades.obtenerMensaje(sedeDig.getPregunta().getModulo().getEtiqueta())
                        + " > "
                        + Utilidades.obtenerMensaje(sedeDig.getPregunta().getSeccion().getEtiqueta())
                        + " > "
                        + sedeDig.getPregunta().getNumeral();
                throw new ErrorValidacion(msj);
            }
        }
        String codEstablecimiento = ((RespuestaDig) respuestasDigitadasMap.get(establecimientoDig.getRespuesta())).getValor();
        if (!UtilCadena.isNullOVacio(codEstablecimiento)) {
            //Consulto el establecimiento en la BD
            establecimiento = fEstablecimiento.getEstablecimientoPorCodigo(codEstablecimiento);
            if (establecimiento == null) {
                erroresMap.put(establecimientoDig.getPregunta(), Utilidades.obtenerMensaje("dig.guardar.invalido", "Establecimiento"));
                String msj = Utilidades.obtenerMensaje("dig.guardar.error.general")
                        + ";"
                        + Utilidades.obtenerMensaje(establecimientoDig.getPregunta().getModulo().getEtiqueta())
                        + " > "
                        + Utilidades.obtenerMensaje(establecimientoDig.getPregunta().getSeccion().getEtiqueta())
                        + " > "
                        + establecimientoDig.getPregunta().getNumeral();
                throw new ErrorValidacion(msj);
            }
        }

    }

    public void limpiarMapaErrores() {
        for (Map.Entry error : erroresMap.entrySet()) {
            if (error.getValue() != null) {
                error.setValue(null);
            }
        }
    }

    public void validacionesBasicasInstrumento(RespuestaDig fechaEncuesta, RespuestaDig idLote, RespuestaDig establecimiento, RespuestaDig sede) {
        if (UtilCadena.isNullOVacio(((RespuestaDig) respuestasDigitadasMap.get(fechaEncuesta.getRespuesta())).getValor())) {
            erroresMap.put(fechaEncuesta.getPregunta(), Utilidades.obtenerMensaje("dig.guardar.requerido", fechaEncuesta.getPregunta().getNumeral(), Utilidades.obtenerMensaje("bd.ins_preguntas.etiqueta_1.PREG_001")));
            String msj = Utilidades.obtenerMensaje("dig.guardar.error.general")
                    + ";"
                    + Utilidades.obtenerMensaje(fechaEncuesta.getPregunta().getModulo().getEtiqueta())
                    + " > "
                    + Utilidades.obtenerMensaje(fechaEncuesta.getPregunta().getSeccion().getEtiqueta())
                    + " > "
                    + fechaEncuesta.getPregunta().getNumeral();
            throw new ErrorValidacion(msj);
        }
        if (UtilCadena.isNullOVacio(((RespuestaDig) respuestasDigitadasMap.get(idLote.getRespuesta())).getValor())) {
            erroresMap.put(idLote.getPregunta(), Utilidades.obtenerMensaje("dig.guardar.requerido", idLote.getPregunta().getNumeral(), Utilidades.obtenerMensaje(idLote.getPregunta().getEtiqueta1())));
            String msj = Utilidades.obtenerMensaje("dig.guardar.error.general")
                    + ";"
                    + Utilidades.obtenerMensaje(idLote.getPregunta().getModulo().getEtiqueta())
                    + " > "
                    + Utilidades.obtenerMensaje(idLote.getPregunta().getSeccion().getEtiqueta())
                    + " > "
                    + idLote.getPregunta().getNumeral();
            throw new ErrorValidacion(msj);
        }

        if (UtilCadena.isNullOVacio(((RespuestaDig) respuestasDigitadasMap.get(establecimiento.getRespuesta())).getValor())) {
            erroresMap.put(establecimiento.getPregunta(), Utilidades.obtenerMensaje("dig.guardar.requerido", establecimiento.getPregunta().getNumeral(), Utilidades.obtenerMensaje(establecimiento.getPregunta().getEtiqueta1())));
            String msj = Utilidades.obtenerMensaje("dig.guardar.error.general")
                    + ";"
                    + Utilidades.obtenerMensaje(establecimiento.getPregunta().getModulo().getEtiqueta())
                    + " > "
                    + Utilidades.obtenerMensaje(establecimiento.getPregunta().getSeccion().getEtiqueta())
                    + " > "
                    + establecimiento.getPregunta().getNumeral();
            throw new ErrorValidacion(msj);
        }

        if (UtilCadena.isNullOVacio(((RespuestaDig) respuestasDigitadasMap.get(sede.getRespuesta())).getValor())) {
            erroresMap.put(sede.getPregunta(), Utilidades.obtenerMensaje("dig.guardar.requerido", sede.getPregunta().getNumeral(), Utilidades.obtenerMensaje(sede.getPregunta().getEtiqueta1())));
            String msj = Utilidades.obtenerMensaje("dig.guardar.error.general")
                    + ";"
                    + Utilidades.obtenerMensaje(sede.getPregunta().getModulo().getEtiqueta())
                    + " > "
                    + Utilidades.obtenerMensaje(sede.getPregunta().getSeccion().getEtiqueta())
                    + " > "
                    + sede.getPregunta().getNumeral();
            throw new ErrorValidacion(msj);
        }
    }

    private boolean isNumeric(String cadena) {
        /*try {
         Double.parseDouble(cadena);
         return true;
         } catch (NumberFormatException ex) {
         return false;
         }*/
        if (cadena.indexOf(".") == (cadena.length() - 1)) {
            return false;
        } else {
            return cadena.matches("[-+]?\\d+(\\.\\d{0,2})?");
        }

    }

    private boolean isDateFormat(String cadena) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        if (cadena != null) {
            try {
                dateFormat.setLenient(false);
                dateFormat.parse(cadena.trim());
                return true;
            } catch (ParseException ex) {
                return false;
            }
        } else {
            return false;
        }
    }

    private boolean isHourFormat(String cadena) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        if (cadena != null) {
            try {
                dateFormat.parse(cadena.trim());
                return true;
            } catch (ParseException ex) {
                return false;
            }
        } else {
            return false;
        }
    }

    private boolean isBooleanFormat(String cadena) {
        return true;
    }

    private void validarTipoCampo(RespuestaDig respTemp) {
        String msj = Utilidades.obtenerMensaje("dig.guardar.error.general")
                + ";"
                + Utilidades.obtenerMensaje(respTemp.getPregunta().getModulo().getEtiqueta())
                + " > "
                + Utilidades.obtenerMensaje(respTemp.getPregunta().getSeccion().getEtiqueta())
                + " > "
                + respTemp.getPregunta().getNumeral();
        if (respTemp.getRespuesta().getTipoRespuesta().getCodTipCampo().getTipoDato().equals(TipoDato.FECHA.getTipo())) {
            if (!isDateFormat(respTemp.getValor())) {
                erroresMap.put(respTemp.getPregunta(), Utilidades.obtenerMensaje("dig.guardar.tipodato.fecha"));
                throw new ErrorValidacion(msj);
            }
        }

        if (respTemp.getRespuesta().getTipoRespuesta().getCodTipCampo().getTipoDato().equals(TipoDato.NUMERO.getTipo())) {
            if (!isNumeric(respTemp.getValor())) {
                erroresMap.put(respTemp.getPregunta(), Utilidades.obtenerMensaje("dig.guardar.tipodato.numeroformato"));
                throw new ErrorValidacion(msj);
            }
        }

        if (respTemp.getRespuesta().getTipoRespuesta().getCodTipCampo().getTipoDato().equals(TipoDato.HORA.getTipo())) {
            if (!isHourFormat(respTemp.getValor())) {
                erroresMap.put(respTemp.getPregunta(), Utilidades.obtenerMensaje("dig.guardar.tipodato.hora"));
                throw new ErrorValidacion(msj);
            }
        }

        if (respTemp.getRespuesta().getTipoRespuesta().getCodTipCampo().getTipoDato().equals(TipoDato.BOOLEANO.getTipo())) {
            if (!isBooleanFormat(respTemp.getValor())) {
                erroresMap.put(respTemp.getPregunta(), Utilidades.obtenerMensaje("dig.guardar.tipodato.booleano"));
                throw new ErrorValidacion(msj);
            }
        }

    }

    private void validarParametroRespuesta(RespuestaDig respTemp) {
        if (respTemp.getRespuesta().getCodParametro() != null) {
            if (fInstrumentos.obtenerCodigoParametro(respTemp.getRespuesta().getCodParametro(), respTemp.getValor()) == null) {
                String msj = Utilidades.obtenerMensaje("dig.guardar.error.general")
                        + ";"
                        + Utilidades.obtenerMensaje(respTemp.getPregunta().getModulo().getEtiqueta())
                        + " > "
                        + Utilidades.obtenerMensaje(respTemp.getPregunta().getSeccion().getEtiqueta())
                        + " > "
                        + respTemp.getPregunta().getNumeral();
                erroresMap.put(respTemp.getPregunta(), Utilidades.obtenerMensaje("dig.guardar.codigo.noexiste"));
                throw new ErrorValidacion(msj);
            }
        }
    }

    private void validarTipologiaRespuesta(RespuestaDig respTemp) {
        //System.out.println("respuesta: "+respTemp.getRespuesta()+", pregunta: "+respTemp.getPregunta()+"valor: "+respTemp.getValor());
        if (respTemp.getRespuesta().getTipologia() != null) {
            /*System.out.println("respTemp.getRespuesta().getTipologia().getCodigo(): "+respTemp.getRespuesta().getTipologia().getCodigo());
             System.out.println("respTemp.getValor(): "+respTemp.getValor());
             System.out.println("getUsuarioSesion().getUsuario().getIdioma().getId(): "+getUsuarioSesion().getUsuario().getIdioma().getId());*/
            if (fParametros.consultarValorTipologiaEstado(respTemp.getRespuesta().getTipologia().getCodigo(), respTemp.getValor(), getUsuarioSesion().getUsuario().getIdioma().getId()) == null) {
                String msj = Utilidades.obtenerMensaje("dig.guardar.error.general")
                        + ";"
                        + Utilidades.obtenerMensaje(respTemp.getPregunta().getModulo().getEtiqueta())
                        + " > "
                        + Utilidades.obtenerMensaje(respTemp.getPregunta().getSeccion().getEtiqueta())
                        + " > "
                        + respTemp.getPregunta().getNumeral();
                erroresMap.put(respTemp.getPregunta(), Utilidades.obtenerMensaje("dig.guardar.codigotipologia.noexiste"));
                throw new ErrorValidacion(msj);
            }
        }
    }

    private void validarCondiciones(RespuestaDig respTemp) {
        //System.out.println("respuesta: "+respTemp.getRespuesta().getCodigo()+", pregunta: "+respTemp.getPregunta().getCodigo()+"valor: "+respTemp.getValor()+", "+respTemp.getRespuesta().getTipoRespuesta().getCodigo()+" | "+respTemp.getValor()+", "+respTemp.getRespuesta().getTipoRespuesta().getDescripcion());
        if (respTemp.getRespuesta().getTipoRespuesta().getCodigo().equals("TIP_RESP_018")) {
            long condicion = Long.parseLong(respTemp.getValor());
            long condicion_menor = Long.parseLong((facParam.consultarParametro("CONDICION_MENOR")).getValor());
            long condicion_mayor = Long.parseLong((facParam.consultarParametro("CONDICION_MAYOR")).getValor());
            if (condicion < condicion_menor || condicion > condicion_mayor) {
                String msj = Utilidades.obtenerMensaje("dig.guardar.error.general")
                        + ";"
                        + Utilidades.obtenerMensaje(respTemp.getPregunta().getModulo().getEtiqueta())
                        + " > "
                        + Utilidades.obtenerMensaje(respTemp.getPregunta().getSeccion().getEtiqueta())
                        + " > "
                        + respTemp.getPregunta().getNumeral();
                erroresMap.put(respTemp.getPregunta(), Utilidades.obtenerMensaje("dig.guardar.condicion.invalida"));
                throw new ErrorValidacion(msj);
            }
        }
    }

    private void validarMapaRespuestas() {
        for (Map.Entry respuestaDig : respuestasDigitadasMap.entrySet()) {
            if (!UtilCadena.isNullOVacio(((RespuestaDig) respuestaDig.getValue()).getValor())) {
                RespuestaDig respTemp = (RespuestaDig) respuestaDig.getValue();
                //Valido Tipo de Respuesta
                //System.out.println("Validando tipo campo");
                this.validarTipoCampo(respTemp);

                //Valido Parametro de Respuesta
                //this.validarParametroRespuesta(respTemp);

                //Valido Tipologias
                //System.out.println("Validando tipologia respuesta");
                this.validarTipologiaRespuesta(respTemp);

                //Valido Condiciones.
                //System.out.println("Validando condiciones");
                this.validarCondiciones(respTemp);

                //Validar Repetidos
                this.validarRepetidas(respTemp);
            }
        }
    }

    private void validarMapaRespuestas(Map<Respuesta, RespuestaDig> respuestasDigitadas) {
        for (Map.Entry respuestaDig : respuestasDigitadas.entrySet()) {
            if (!UtilCadena.isNullOVacio(((RespuestaDig) respuestaDig.getValue()).getValor())) {
                RespuestaDig respTemp = (RespuestaDig) respuestaDig.getValue();
                //Valido Tipo de Respuesta
                this.validarTipoCampoMultiples(respTemp);

                //Valido Parametro de Respuesta
                this.validarParametroRespuestaMultiple(respTemp);

                //Valido Tipologias
                this.validarTipologiaRespuestaMultiple(respTemp);

                //Valido Condiciones.
                this.validarCondicionesMultiples(respTemp);

                //Validar Repetidos
                this.validarRepetidasMultiple(respTemp, respuestasDigitadas);
            }
        }
    }

    private void validarRepetidas(RespuestaDig respuesta) {
        if (respuesta.getRespuesta().getRepetida() != 0) {
            List<Respuesta> respuestasRepetidas = fInstrumentos.obtenerRespuestaRepetidas(respuesta.getRespuesta());
            Set<String> rpta = new HashSet();
            for (Respuesta rspTmp : respuestasRepetidas) {
                String valor = respuestasDigitadasMap.get(rspTmp).getValor();
                if (!UtilCadena.isNullOVacio(valor)) {
                    if (!rpta.contains(valor)) {
                        rpta.add(valor);
                    } else {
                        String msj = Utilidades.obtenerMensaje("dig.guardar.error.general")
                                + ";"
                                + Utilidades.obtenerMensaje(rspTmp.getCodPreguntas().getModulo().getEtiqueta())
                                + " > "
                                + Utilidades.obtenerMensaje(rspTmp.getCodPreguntas().getSeccion().getEtiqueta())
                                + " > "
                                + rspTmp.getCodPreguntas().getNumeral();
                        erroresMap.put(rspTmp.getCodPreguntas(), Utilidades.obtenerMensaje("aplicacion.general.error.repetido"));
                        throw new ErrorValidacion(msj);
                    }
                }
            }
        }
    }

    private void validarRepetidasMultiple(RespuestaDig respuesta, Map<Respuesta, RespuestaDig> elementoSeleccionado) {
        if (respuesta.getRespuesta().getRepetida() != 0) {
            List<Respuesta> respuestasRepetidas = fInstrumentos.obtenerRespuestaRepetidas(respuesta.getRespuesta());
            Set<String> rpta = new HashSet();
            for (Respuesta rspTmp : respuestasRepetidas) {
                String valor = elementoSeleccionado.get(rspTmp).getValor();
                if (!UtilCadena.isNullOVacio(valor)) {
                    if (!rpta.contains(valor)) {
                        rpta.add(valor);
                    } else {
                        String msj = Utilidades.obtenerMensaje("aplicacion.general.error.repetido")
                                + ";"
                                + Utilidades.obtenerMensaje(rspTmp.getCodPreguntas().getModulo().getEtiqueta())
                                + " > "
                                + Utilidades.obtenerMensaje(rspTmp.getCodPreguntas().getSeccion().getEtiqueta())
                                + " > "
                                + rspTmp.getCodPreguntas().getNumeral();
                        throw new ErrorValidacion(msj);
                    }
                }
            }
        }
    }

    private void validarPreguntasDependendientesElementos(TipoElemento tipo, Elemento elemento) {
        List<Respuesta> respuestasPrincipales = fInstrumentos.obtenerRespuestaPrincipales(tipo, this.instrumentoVigente.getCodigo());
        for (Respuesta rsp : respuestasPrincipales) {
            RespuestaDig rspPrincial = this.obtenerRespuestaDigitada(rsp.getCodigo());
            String valorRespPrincipal = elemento.getMapaRespuestas().get(rspPrincial.getRespuesta()).getValor();
            List<Respuesta> respuestasDependientes = fInstrumentos.obtenerRespuestaDependiente(rsp.getCodigo());
            boolean isFill = false;
            for (Respuesta rspDep : respuestasDependientes) {
                if (!UtilCadena.isNullOVacio(elemento.getMapaRespuestas().get(rspDep).getValor())) {
                    isFill = true;
                    break;
                }
            }
            if (valorRespPrincipal.equals("1") && !isFill) {
                if (tipo.getCodigo().equals("ELE_EDI") || tipo.getCodigo().equals("ELE_ESP")) {
                    throw new ErrorValidacion(Utilidades.obtenerMensaje("dig.guardar.error.general.inconsistencia.edificio.si", Utilidades.obtenerMensaje(rsp.getCodPreguntas().getEtiqueta1())));
                } else {
                    throw new ErrorValidacion(Utilidades.obtenerMensaje("dig.guardar.error.general.inconsistencia.elemento.si", Utilidades.obtenerMensaje(rsp.getCodPreguntas().getEtiqueta1())));
                }

            }
            if ((valorRespPrincipal.equals("0") || valorRespPrincipal.equals("2")) && isFill) {
                throw new ErrorValidacion(Utilidades.obtenerMensaje("dig.guardar.error.general.inconsistencia.elemento.no", Utilidades.obtenerMensaje(rsp.getCodPreguntas().getEtiqueta1())));
            }
        }

        for (Respuesta rsp : respuestasObligatorias) {
            if (rsp.getCodPreguntas().getTipoElemento().equals(tipo)) {
                RespuestaDig respValidar = elemento.getMapaRespuestas().get(rsp.getRespuestaObligatoria());
                RespuestaDig respPrincipal = elemento.getMapaRespuestas().get(rsp);
                if (UtilCadena.isNullOVacio(respValidar.getValor()) && !UtilCadena.isNullOVacio(respPrincipal.getValor())) {
                    throw new ErrorValidacion(Utilidades.obtenerMensaje("dig.guardar.inconsistencia.obligatorio.edificio", Utilidades.obtenerMensaje(rsp.getCodPreguntas().getEtiqueta1())));
                }
            }
        }

    }

    private void validarCondicionesMultiples(RespuestaDig respTemp) {
        if (respTemp.getRespuesta().getTipoRespuesta().getCodigo().equals("TIP_RESP_018")) {
            long condicion = Long.parseLong(respTemp.getValor());
            long condicion_menor = Long.parseLong((facParam.consultarParametro("CONDICION_MENOR")).getValor());
            long condicion_mayor = Long.parseLong((facParam.consultarParametro("CONDICION_MAYOR")).getValor());
            if (condicion < condicion_menor || condicion > condicion_mayor) {
                String msj = Utilidades.obtenerMensaje(respTemp.getPregunta().getEtiqueta1())
                        + ". "
                        + Utilidades.obtenerMensaje("dig.guardar.condicion.invalida");
                throw new ErrorValidacion(msj);
            }
        }
    }

    private void validarParametroRespuestaMultiple(RespuestaDig respTemp) {
        if (respTemp.getRespuesta().getCodParametro() != null) {
            if (fInstrumentos.obtenerCodigoParametro(respTemp.getRespuesta().getCodParametro(), respTemp.getValor()) == null) {
                throw new ErrorValidacion(Utilidades.obtenerMensaje("dig.guardar.error.parametro", respTemp.getPregunta().getNumeral(), Utilidades.obtenerMensaje(respTemp.getPregunta().getEtiqueta1())));
            }
        }
    }

    private void validarTipologiaRespuestaMultiple(RespuestaDig respTemp) {
        if (respTemp.getRespuesta().getTipologia() != null) {
            if (fParametros.consultarValorTipologiaEstado(respTemp.getRespuesta().getTipologia().getCodigo(), respTemp.getValor(), getUsuarioSesion().getUsuario().getIdioma().getId()) == null) {
                throw new ErrorValidacion(Utilidades.obtenerMensaje("dig.guardar.error.tipologia", respTemp.getPregunta().getNumeral(), Utilidades.obtenerMensaje(respTemp.getPregunta().getEtiqueta1())));
            }
        }
    }

    private void validarTipoCampoMultiples(RespuestaDig respTemp) {
        if (respTemp.getRespuesta().getTipoRespuesta().getCodTipCampo().getTipoDato().equals(TipoDato.FECHA.getTipo())) {
            if (!isDateFormat(respTemp.getValor())) {
                throw new ErrorValidacion(Utilidades.obtenerMensaje("dig.guardar.tipodato.fecha.multiple", respTemp.getPregunta().getNumeral(), Utilidades.obtenerMensaje(respTemp.getPregunta().getEtiqueta1())));
            }
        }

        if (respTemp.getRespuesta().getTipoRespuesta().getCodTipCampo().getTipoDato().equals(TipoDato.NUMERO.getTipo())) {
            if (!isNumeric(respTemp.getValor())) {
                throw new ErrorValidacion(Utilidades.obtenerMensaje("dig.guardar.tipodato.numero.multipleformato", respTemp.getPregunta().getNumeral(), Utilidades.obtenerMensaje(respTemp.getPregunta().getEtiqueta1())));
            }
        }

        if (respTemp.getRespuesta().getTipoRespuesta().getCodTipCampo().getTipoDato().equals(TipoDato.HORA.getTipo())) {
            if (!isHourFormat(respTemp.getValor())) {
                throw new ErrorValidacion(Utilidades.obtenerMensaje("dig.guardar.tipodato.hora.multiple", respTemp.getPregunta().getNumeral(), Utilidades.obtenerMensaje(respTemp.getPregunta().getEtiqueta1())));
            }
        }

        if (respTemp.getRespuesta().getTipoRespuesta().getCodTipCampo().getTipoDato().equals(TipoDato.BOOLEANO.getTipo())) {
            if (!isBooleanFormat(respTemp.getValor())) {
                throw new ErrorValidacion(Utilidades.obtenerMensaje("dig.guardar.tipodato.booleano.multiple", respTemp.getPregunta().getNumeral(), Utilidades.obtenerMensaje(respTemp.getPregunta().getEtiqueta1())));
            }
        }

    }

    private RespuestaDig obtenerRespuestaDigitada(String codigo) {
        Respuesta respuesta = fInstrumentos.obtenerRespuestaPorCodigo(codigo);
        Elemento elemento = new Elemento("PREDIO_1", instrumentoDigitado, respuesta.getCodPreguntas().getTipoElemento());
        return new RespuestaDig(elemento, respuesta.getCodPreguntas(), respuesta);
    }

    public String onFlowProcess(FlowEvent event) {
        String currentStepId = event.getOldStep();
        String stepToGo = event.getNewStep();
        if (stepToGo.equals("")) {
            this.step = currentStepId;
            return event.getOldStep();
        } else {
            this.step = stepToGo;
        }
        if (skip) {
            skip = false;   //reset in case user goes back  
            return "confirm";
        } else {
            return event.getNewStep();
        }
    }

    @Override
    public void resetearFiltros() {
        this.resetearNiveles();
    }

    @Override
    public void cambioPeriodo() {
        super.initHistoricos(periodo);
        super.cargarNivel1();
    }

    private void prepararMapaRespuestasDigitadas() {
        Nivel nivel0 = UtilCadena.isNullOVacio(seleccionNivel0) ? padreNivel0 : this.obtenerNivel(seleccionNivel0);
        Nivel nivel1 = UtilCadena.isNullOVacio(seleccionNivel1) ? padreNivel1 : this.obtenerNivel(seleccionNivel1);
        Nivel nivel2 = UtilCadena.isNullOVacio(seleccionNivel2) ? padreNivel2 : this.obtenerNivel(seleccionNivel2);
        Nivel nivel3 = UtilCadena.isNullOVacio(seleccionNivel3) ? padreNivel3 : this.obtenerNivel(seleccionNivel3);
        Nivel nivel4 = UtilCadena.isNullOVacio(seleccionNivel4) ? padreNivel4 : this.obtenerNivel(seleccionNivel4);
        Nivel nivel5 = UtilCadena.isNullOVacio(seleccionNivel5) ? padreNivel5 : this.obtenerNivel(seleccionNivel5);

        String idNivel = UtilCadena.isNullOVacio(nivel0) ? "" : nivel0.getId().toString();
        this.respuestasDigitadasMap.get(fInstrumentos.obtenerRespuestaPorCodigo("RESP_031_01")).setValor(idNivel);
        idNivel = UtilCadena.isNullOVacio(nivel1) ? "" : nivel1.getId().toString();
        this.respuestasDigitadasMap.get(fInstrumentos.obtenerRespuestaPorCodigo("RESP_031_02")).setValor(idNivel);
        idNivel = UtilCadena.isNullOVacio(nivel2) ? "" : nivel2.getId().toString();
        this.respuestasDigitadasMap.get(fInstrumentos.obtenerRespuestaPorCodigo("RESP_031_03")).setValor(idNivel);
        idNivel = UtilCadena.isNullOVacio(nivel3) ? "" : nivel3.getId().toString();
        this.respuestasDigitadasMap.get(fInstrumentos.obtenerRespuestaPorCodigo("RESP_031_04")).setValor(idNivel);
        idNivel = UtilCadena.isNullOVacio(nivel4) ? "" : nivel4.getId().toString();
        this.respuestasDigitadasMap.get(fInstrumentos.obtenerRespuestaPorCodigo("RESP_031_05")).setValor(idNivel);
        idNivel = UtilCadena.isNullOVacio(nivel5) ? "" : nivel5.getId().toString();
        this.respuestasDigitadasMap.get(fInstrumentos.obtenerRespuestaPorCodigo("RESP_031_06")).setValor(idNivel);
    }

    private String obtenerIdStringNivel(NivelDPA nivelSeleccionado) {
        if (nivelSeleccionado != null) {
            return nivelSeleccionado.getId().toString();
        } else {
            return null;
        }
    }

    public Map<Pregunta, String> getErroresMap() {
        return erroresMap;
    }

    public void setErroresMap(Map<Pregunta, String> erroresMap) {
        this.erroresMap = erroresMap;
    }

    private void llenarListadosElementos() {
        for (Elemento elemento : elementos) {

            if (elemento.getTipoElemento().getCodigo().equals(TipoElem.EDIFIO.getCodigo())) {
                this.edificios.add(elemento);
            }
            if (elemento.getTipoElemento().getCodigo().equals(TipoElem.ESTABLECIMIENTO.getCodigo())) {
                this.establecimientos.add(elemento);
            }
            if (elemento.getTipoElemento().getCodigo().equals(TipoElem.ESPACIO.getCodigo())) {
                this.espacios.add(elemento);
            }
            if (elemento.getTipoElemento().getCodigo().equals(TipoElem.PLANO_PREDIO.getCodigo())) {
                this.planosPredios.add(elemento);
            }
            if (elemento.getTipoElemento().getCodigo().equals(TipoElem.PLANO_ESPACIO.getCodigo())) {
                this.planosEspacios.add(elemento);
            }
            if (elemento.getTipoElemento().getCodigo().equals(TipoElem.OBSERVACIONES.getCodigo())) {
                this.observacionesComplementarias.add(elemento);
            }

        }
    }

    public void eliminarElemento() {
        try {
            if (elementoEliminado.getTipoElemento().getCodigo().equals(TipoElem.EDIFIO.getCodigo())) {
                if (edificios.size() > 1) {
                    this.edificios.remove(elementoEliminado);
                } else {
                    throw new ErrorValidacion(Utilidades.obtenerMensaje("dig.eliminar.mensaje.eliminacion"));
                }
            }
            if (elementoEliminado.getTipoElemento().getCodigo().equals(TipoElem.ESPACIO.getCodigo())) {
                if (espacios.size() > 1) {
                    this.espacios.remove(elementoEliminado);
                } else {
                    throw new ErrorValidacion(Utilidades.obtenerMensaje("dig.eliminar.mensaje.eliminacion"));
                }
            }
            if (elementoEliminado.getTipoElemento().getCodigo().equals(TipoElem.PLANO_ESPACIO.getCodigo())) {
                if (planosEspacios.size() > 1) {
                    this.planosEspacios.remove(elementoEliminado);
                } else {
                    throw new ErrorValidacion(Utilidades.obtenerMensaje("dig.eliminar.mensaje.eliminacion"));
                }
            }
            if (elementoEliminado.getTipoElemento().getCodigo().equals(TipoElem.OBSERVACIONES.getCodigo())) {
                if (observacionesComplementarias.size() > 1) {
                    this.observacionesComplementarias.remove(elementoEliminado);
                } else {
                    throw new ErrorValidacion(Utilidades.obtenerMensaje("dig.eliminar.mensaje.eliminacion"));
                }
            }
            if (elementoEliminado.getTipoElemento().getCodigo().equals(TipoElem.ESTABLECIMIENTO.getCodigo())) {
                if (establecimientos.size() > 1) {
                    this.establecimientos.remove(elementoEliminado);
                } else {
                    throw new ErrorValidacion(Utilidades.obtenerMensaje("dig.eliminar.mensaje.eliminacion"));
                }
            }
            fInstrumentos.eliminarElemento(elementoEliminado);
            saveBitacora(getUsuarioSesion().getUsuario().getUsername(), BitacoraTransaccion.ELIMINAR_ELEMENTO, instrumentoDigitado.getBoletaCensal(), "");
            fInstrumentos.actualizarFechaUsuarioModificacion(instrumentoDigitado, usuario.getUsuario().getUsername());
            mostrarMensajeInfo(Utilidades.obtenerMensaje("dig.eliminar.titulo"), Utilidades.obtenerMensaje("dig.eliminar.mensaje"));
        } catch (Exception ex) {
            errores.manejarExcepcion(ex);
            ManejadorErrores.ingresarExcepcionEnLog(ex, DigitacionInstrumentoBean.class);
        }
    }

    public void anularInstrumento() {
        try {
            this.renderRespNoEmitidas = false;
            if (UtilCadena.isNullOVacio(this.instrumentoDigitado.getMotivoAnulacion())) {
                throw new ErrorValidacion(Utilidades.obtenerMensaje("dig.guardar.requerido", Utilidades.obtenerMensaje("dig.anular.instrumento.mensaje")));
            }
            if (UtilCadena.isNullOVacio(this.instrumentoDigitado.getId())) {
                throw new ErrorValidacion(Utilidades.obtenerMensaje("dig.anulacion.invalida"));
            }
            this.instrumentoDigitado.setUsuarioAnulacion(getUsuarioSesion().getUsuario().getUsername());
            this.instrumentoDigitado.setEstado(EstadoInstrumento.A);
            fInstrumentos.anularInstrumento(this.instrumentoDigitado);
            saveBitacora(getUsuarioSesion().getUsuario().getUsername(), BitacoraTransaccion.ANULAR_INSTRUMENTO, instrumentoDigitado.getBoletaCensal(), "Anulacion");
            mostrarMensajeInfo(Utilidades.obtenerMensaje("dig.anular.titulo"), Utilidades.obtenerMensaje("dig.anular.mensaje"));
        } catch (Exception ex) {
            errores.manejarExcepcion(ex);
            ManejadorErrores.ingresarExcepcionEnLog(ex, DigitacionInstrumentoBean.class);
            //ex.printStackTrace();
        }
    }

    public void emitirInstrumento() {
        List<String> resultadoMsg = new ArrayList<String>();
        this.renderRespNoEmitidas = false;
        try {

            List<String> respNoEmitidas = new ArrayList<String>();

            respNoEmitidas = fInstrumentos.emitirInstrumento(instrumentoDigitado, getUsuarioSesion().getUsuario().getUsername());
            saveBitacora(getUsuarioSesion().getUsuario().getUsername(), BitacoraTransaccion.MODIFICAR_INSTRUMENTO, instrumentoDigitado.getBoletaCensal(), "Emision");
            if (respNoEmitidas.isEmpty()) {
                resultadoMsg.add(Utilidades.obtenerMensaje("dig.emitir.instrumento.msj.emitido", instrumentoDigitado.getBoletaCensal()));
            } else {
                resultadoMsg.add(Utilidades.obtenerMensaje("dig.emitir.instrumento.msj.NoEmitido", instrumentoDigitado.getBoletaCensal()));
                resultadoMsg.add(Utilidades.obtenerMensaje("dig.emitir.instrumento.msj.complete"));
                for (String s : respNoEmitidas) {
                    Respuesta respuesta = fInstrumentos.obtenerRespuestaPorCodigo(s);
                    String modulo = Utilidades.obtenerMensaje(respuesta.getCodPreguntas().getModulo().getEtiqueta());
                    String seccion = Utilidades.obtenerMensaje(respuesta.getCodPreguntas().getSeccion().getEtiqueta());
                    String enunciado = Utilidades.obtenerMensaje(respuesta.getCodPreguntas().getEtiqueta1());
                    String numeral = respuesta.getCodPreguntas().getNumeral();
                    String msj = Utilidades.obtenerMensaje("dig.emitir.instrumento.msj.respuesta", modulo, seccion, enunciado, numeral);

                    resultadoMsg.add(msj);
                }
                resultadoMsg.add("*****************************************");
                resultadoMsg.add("");
            }

            FileDownloadView(resultadoMsg);
            renderRespNoEmitidas = true;
            mostrarMensajeInfo("Proceso Ejecutado Con Exito", "Para Ver Detalles Clic En 'Ver Resultados'");
            //mostrarMensajeInfo(Utilidades.obtenerMensaje("dig.emision.titulo"), Utilidades.obtenerMensaje("dig.emision.mensaje"));
        } catch (ErrorProcedimientoBD eg) {
            Utilidades.mostrarMensajeFatal(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"), Utilidades.obtenerMensaje("dig.emitir.instrumento.msj.error.proce.bd"));
            ManejadorErrores.ingresarExcepcionEnLog(eg, ConsultaInstrumentoBean.class);
        } catch (Exception ex) {
            errores.manejarExcepcion(ex);
            resultadoMsg.add("Instrumento Boleta Censal No " + instrumentoDigitado.getBoletaCensal() + ", Resultado : NO EMITIDO.");
            ManejadorErrores.ingresarExcepcionEnLog(ex, DigitacionInstrumentoBean.class);
        }
    }

    public void FileDownloadView(List<String> msgResp) {
        BufferedWriter bw = null;
        FileWriter fw = null;
        try {
            /*ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
             URL url1 = classLoader.getResource("");
             System.err.println("-------------  "+url1.getPath());
             File files = new File(url1.getPath()+"/archivo.txt");
             ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
             System.out.println(servletContext.getRealPath("/"));
             File file = new File(servletContext.getRealPath("/file.txt"));*/
            File file = new File("RespNoEmitidas.txt");
            fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
            for (String s : msgResp) {
                bw.write(s);
                bw.newLine();
            }
            path = file.getPath();
            contentType = FacesContext.getCurrentInstance().getExternalContext().getMimeType(path);
        } catch (IOException e) {
            ManejadorErrores.ingresarExcepcionEnLog(e, DigitacionInstrumentoBean.class);
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

    private void validarModulosComplementarios(RespuestaDig compModA1, RespuestaDig compModB1, RespuestaDig compModC1) {
        String valorModA1 = ((RespuestaDig) respuestasDigitadasMap.get(compModA1.getRespuesta())).getValor();
        String valorModB1 = ((RespuestaDig) respuestasDigitadasMap.get(compModB1.getRespuesta())).getValor();
        String valorModC1 = ((RespuestaDig) respuestasDigitadasMap.get(compModC1.getRespuesta())).getValor();

        if (!UtilCadena.isNullOVacio(valorModA1) && valorModB1.equals("1")) {
            if (!fInstrumentos.tieneRepuestasDigitadasPorModulo("MODULOA1", this.instrumentoDigitado)) {
                throw new ErrorValidacion(Utilidades.obtenerMensaje("dig.guardar.error.validar.moduloA1"));
            }

        }

        if (!UtilCadena.isNullOVacio(valorModB1) && valorModB1.equals("1")) {
            if (!fInstrumentos.tieneRepuestasDigitadasPorModulo("MODULOB1", this.instrumentoDigitado)) {
                throw new ErrorValidacion(Utilidades.obtenerMensaje("dig.guardar.error.validar.moduloB1"));
            }
        }

        if (!UtilCadena.isNullOVacio(valorModC1) && valorModC1.equals("1")) {
            if (!fInstrumentos.tieneRepuestasDigitadasPorModulo("MODULOC1", this.instrumentoDigitado)) {
                throw new ErrorValidacion(Utilidades.obtenerMensaje("dig.guardar.error.validar.moduloC1"));
            }
        }

        this.instrumentoDigitado.getId();
    }

    public List<SiNo> getListaSiNo() {
        return Arrays.asList(SiNo.values());
    }

    public List<SiNoNA> getListaSiNoNA() {
        return Arrays.asList(SiNoNA.values());
    }

    public List<SiNo> getListaNoSi() {
        List<SiNo> valores = new ArrayList<SiNo>();
        valores.add(SiNo.No);
        valores.add(SiNo.Si);
        return valores;
    }

    public void limpiarMapaEspaciosSimilares() {
        espaciosDigitados = new ArrayList<EspacioSimilar>();
        for (int i = 0; i < 10; i++) {
            espaciosDigitados.add(new EspacioSimilar());
        }
    }

    public void generarEspaciosSimilares() {
        int contador = 0;
        try {
            List<EspacioSimilar> espaciosValidos = new ArrayList<EspacioSimilar>();
            //Validar que se tengas un instrumento valido y no en blanco.
            if (instrumentoDigitado.getId() != null) {
                //Validar completitud de la linea superior
                if (espaciosDigitados.get(0).getIdEspacio() == null || espaciosDigitados.get(0).getIdEspacio().isEmpty()
                        || espaciosDigitados.get(0).getIdEdificio() == null || espaciosDigitados.get(0).getIdEdificio().isEmpty()
                        || espaciosDigitados.get(0).getIdPiso() == null || espaciosDigitados.get(0).getIdPiso().isEmpty()) {
                    throw new ErrorValidacion(Utilidades.obtenerMensaje("dig.generar.ambiente.incompleto"));
                }
                for (EspacioSimilar espacio : espaciosDigitados) {
                    //Validar q exista al menos un espacio a generar.
                    if (!espacio.getIdEdificio().isEmpty()) {
                        contador++;
                    }
                    if (!espacio.getIdEspacio().isEmpty()) {
                        contador++;
                    }
                    if (!espacio.getIdPiso().isEmpty()) {
                        contador++;
                    }
                }
                if (contador == 3) {
                    //Usuario debe digitar al menos un espacio.
                    throw new ErrorValidacion(Utilidades.obtenerMensaje("dig.generar.ambiente.incompleto.tabla"));
                }
                //Valido todas las filas de la tabla que esten completas.
                espaciosValidos.add(espaciosDigitados.get(0));
                for (int i = 1; i < 10; i++) {
                    if (espaciosDigitados.get(i).getIdEspacio().isEmpty() && espaciosDigitados.get(i).getIdEdificio().isEmpty() && espaciosDigitados.get(i).getIdPiso().isEmpty()) {
                        continue;
                    }

                    if (espaciosDigitados.get(i).getIdEspacio() == null || espaciosDigitados.get(i).getIdEspacio().isEmpty()
                            || espaciosDigitados.get(i).getIdEdificio() == null || espaciosDigitados.get(i).getIdEdificio().isEmpty()
                            || espaciosDigitados.get(i).getIdPiso() == null || espaciosDigitados.get(i).getIdPiso().isEmpty()) {
                        throw new ErrorValidacion(Utilidades.obtenerMensaje("dig.generar.ambiente.incompleto.fila", i));
                    }
                    
                    if(!identificadorEdificioValido(espaciosDigitados.get(i).getIdEdificio())){
                        throw new ErrorValidacion(Utilidades.obtenerMensaje("dig.generar.ambiente.identificador.novalido.fila", i));
                    }else{
                        //Valido que exista el edificio en el modulo 3
                        if(!fInstrumentos.tieneRespuestaValido(this.instrumentoDigitado.getId(), "RESP_073", espaciosDigitados.get(i).getIdEdificio())){
                            throw new ErrorValidacion(Utilidades.obtenerMensaje("dig.generar.ambiente.edificio.novalido.fila", i));
                        }else{
                            //Es un edificio valido y debo verificar la altura
                            Elemento edificio = fInstrumentos.obtenerElementoPorRespuestaValor(instrumentoDigitado.getId(), "RESP_073", espaciosDigitados.get(i).getIdEdificio());
                            Respuesta alturaEdificio = fInstrumentos.obtenerRespuestaPorCodigo("RESP_074_01");
                            int altEdificio = Integer.parseInt(UtilCadena.isNullOVacio(edificio.getMapaRespuestas().get(alturaEdificio).getValor()) ? "0" : edificio.getMapaRespuestas().get(alturaEdificio).getValor());
                            int altEspacio = Integer.parseInt(espaciosDigitados.get(i).getIdPiso());
                            if ( altEspacio > altEdificio) {
                                throw new ErrorValidacion(Utilidades.obtenerMensaje("aplicacion.general.error.altura.espacio.fila", altEdificio,i));
                            }
                        }
                            
                    }

                    espaciosValidos.add(espaciosDigitados.get(i));
                }
                //Valido q exista el Espacio en el modulo 4
                this.validarRespuestasCorrespondientes(espaciosDigitados.get(0).getIdEdificio(), espaciosDigitados.get(0).getIdEspacio(), espaciosDigitados.get(0).getIdPiso());

                //Validar q no repitan el espacio principal.
                Set<EspacioSimilar> setEspacios = new HashSet<EspacioSimilar>(espaciosValidos);

                if (setEspacios.size() != espaciosValidos.size()) {
                    throw new ErrorValidacion(Utilidades.obtenerMensaje("dig.generar.ambiente.incompleto.fila.repetidos"));
                }

                //Validar q no repitan espacios a generar entre si.

                //Llamado al metodo de Generacion.
                fInstrumentos.crearEspaciosSimilares(instrumentoDigitado.getId(),
                        espaciosDigitados.get(0).getIdEspacio(),
                        espaciosDigitados.get(0).getIdEdificio(),
                        espaciosDigitados.get(0).getIdPiso(),
                        getEspaciosSimilares(espaciosDigitados));
                limpiarMapaEspaciosSimilares();

                //Consulto de nuevo los espacios
                Elemento espacioInicial = espacios.get(0);
                espacios.clear();
                for (Elemento elementoAlmacenados : fInstrumentos.obtenerElementoPorIdInstrumentoDigitado(idInstrumentoDigitado)) {
                    if (elementoAlmacenados.getTipoElemento().getCodigo().equals(TipoElem.ESPACIO.getCodigo())) {
                        elementoAlmacenados.setPreguntas(espacioInicial.getPreguntas());
                        for (RespuestaDig rspDig : espacioInicial.getRespuestasList()) {
                            elementoAlmacenados.agregarClaveMapa(rspDig.getRespuesta(), rspDig);
                        }
                        for (RespuestaDig rspDig : fInstrumentos.obtenerRespuestasDigitadasPorElementoId(elementoAlmacenados.getId())) {
                            rspDig.setAdjuntosList(new ArrayList<Adjunto>());
                            rspDig.getAdjuntosList().addAll(fInstrumentos.obtenerAdjuntosPorRespuestaDigitada(rspDig.getId()));
                            elementoAlmacenados.agregarClaveMapa(rspDig.getRespuesta(), rspDig);
                        }
                        //seteamos el nombre del tipo de espacio
                        RespuestaDig codigoEspacioEscolar = fInstrumentos.obtenerRespuestaDigitada(elementoAlmacenados, "PREG_098", "RESP_098_01");
                        TipologiaValorNombre nombreEspacioEscolar = fParametros.consultarValorTipologia(TipologiaCodigo.TIPO_ESPACIO_ESCOLAR.getValor(), UtilCadena.isNullOVacio(codigoEspacioEscolar) ? "" : codigoEspacioEscolar.getValor(), usuario.getUsuario().getIdioma().getId());
                        elementoAlmacenados.setEspacioEscolar(UtilCadena.isNullOVacio(nombreEspacioEscolar) ? "" : nombreEspacioEscolar.getNombre());
                        //adicionamos a la lista de espacios
                        if (!espacios.contains(elementoAlmacenados)) {
                            espacios.add(elementoAlmacenados);
                        } else {
                            espacios.set(espacios.indexOf(elementoAlmacenados), elementoAlmacenados);
                        }
                    }
                }
                Collections.sort(espacios);


            } else {
                throw new ErrorValidacion(Utilidades.obtenerMensaje("dig.guardar.instrumento.noexiste"));
            }
            mostrarMensajeInfo(Utilidades.obtenerMensaje("dig.generar.ambiente.exito.titulo"), Utilidades.obtenerMensaje("dig.generar.ambiente.exito.mensaje"));
        } catch (Exception ex) {
            errores.manejarExcepcion(ex);
            ManejadorErrores.ingresarExcepcionEnLog(ex, DigitacionInstrumentoBean.class);
        }
    }

    private String getEspaciosSimilares(List<EspacioSimilar> lista) {
        StringBuilder valor = new StringBuilder();
        boolean espacioPadre = true;
        for (EspacioSimilar espacioSimilar : lista) {
            if (espacioPadre) {
                espacioPadre = false;
                continue;
            }
            if (!UtilCadena.isNullOVacio(espacioSimilar.getIdEspacio())) {
                valor.append(espacioSimilar.getIdEspacio())
                        .append("_")
                        .append(espacioSimilar.getIdEdificio())
                        .append("_")
                        .append(espacioSimilar.getIdPiso())
                        .append(",");
            }
        }
        String str = valor.toString();
        str = str.replaceFirst(".$", "");
        return str;
    }

    public Map<Respuesta, RespuestaDig> getRespuestasDigitadasMap() {
        return respuestasDigitadasMap;
    }

    public void setRespuestasDigitadasMap(Map<Respuesta, RespuestaDig> respuestasDigitadasMap) {
        this.respuestasDigitadasMap = respuestasDigitadasMap;
    }

    public List<Seccion> getSecciones() {
        return secciones;
    }

    public void setSecciones(List<Seccion> secciones) {
        this.secciones = secciones;
    }

    public List<ModuloIns> getModulos() {
        return modulos;
    }

    public void setModulos(List<ModuloIns> modulos) {
        this.modulos = modulos;
    }

    public Instrumento getInstrumentoVigente() {
        return instrumentoVigente;
    }

    public void setInstrumentoVigente(Instrumento instrumentoVigente) {
        this.instrumentoVigente = instrumentoVigente;
    }

    public List<Pregunta> getPreguntasSeccion01() {
        return preguntasSeccion01;
    }

    public void setPreguntasSeccion01(List<Pregunta> preguntasSeccion01) {
        this.preguntasSeccion01 = preguntasSeccion01;
    }

    public List<Pregunta> getPreguntasSeccion02() {
        return preguntasSeccion02;
    }

    public void setPreguntasSeccion02(List<Pregunta> preguntasSeccion02) {
        this.preguntasSeccion02 = preguntasSeccion02;
    }

    public List<Pregunta> getPreguntasSeccion03() {
        return preguntasSeccion03;
    }

    public void setPreguntasSeccion03(List<Pregunta> preguntasSeccion03) {
        this.preguntasSeccion03 = preguntasSeccion03;
    }

    public List<Pregunta> getPreguntasSeccion04() {
        return preguntasSeccion04;
    }

    public void setPreguntasSeccion04(List<Pregunta> preguntasSeccion04) {
        this.preguntasSeccion04 = preguntasSeccion04;
    }

    public ManejadorErrores getErrores() {
        return errores;
    }

    public void setErrores(ManejadorErrores errores) {
        this.errores = errores;
    }

    public List<Pregunta> getPreguntasSeccion05() {
        return preguntasSeccion05;
    }

    public void setPreguntasSeccion05(List<Pregunta> preguntasSeccion05) {
        this.preguntasSeccion05 = preguntasSeccion05;
    }

    public List<Pregunta> getPreguntasSeccion06() {
        return preguntasSeccion06;
    }

    public void setPreguntasSeccion06(List<Pregunta> preguntasSeccion06) {
        this.preguntasSeccion06 = preguntasSeccion06;
    }

    public List<Pregunta> getPreguntasSeccion07() {
        return preguntasSeccion07;
    }

    public void setPreguntasSeccion07(List<Pregunta> preguntasSeccion07) {
        this.preguntasSeccion07 = preguntasSeccion07;
    }

    public List<Pregunta> getPreguntasSeccion08() {
        return preguntasSeccion08;
    }

    public void setPreguntasSeccion08(List<Pregunta> preguntasSeccion08) {
        this.preguntasSeccion08 = preguntasSeccion08;
    }

    public List<Pregunta> getPreguntasSeccion09() {
        return preguntasSeccion09;
    }

    public void setPreguntasSeccion09(List<Pregunta> preguntasSeccion09) {
        this.preguntasSeccion09 = preguntasSeccion09;
    }

    public List<Pregunta> getPreguntasSeccion10() {
        return preguntasSeccion10;
    }

    public void setPreguntasSeccion10(List<Pregunta> preguntasSeccion10) {
        this.preguntasSeccion10 = preguntasSeccion10;
    }

    public List<Pregunta> getPreguntasSeccion11() {
        return preguntasSeccion11;
    }

    public void setPreguntasSeccion11(List<Pregunta> preguntasSeccion11) {
        this.preguntasSeccion11 = preguntasSeccion11;
    }

    public List<Pregunta> getPreguntasSeccion12() {
        return preguntasSeccion12;
    }

    public void setPreguntasSeccion12(List<Pregunta> preguntasSeccion12) {
        this.preguntasSeccion12 = preguntasSeccion12;
    }

    public List<Pregunta> getPreguntasSeccion13() {
        return preguntasSeccion13;
    }

    public void setPreguntasSeccion13(List<Pregunta> preguntasSeccion13) {
        this.preguntasSeccion13 = preguntasSeccion13;
    }

    public List<Pregunta> getPreguntasSeccion14() {
        return preguntasSeccion14;
    }

    public void setPreguntasSeccion14(List<Pregunta> preguntasSeccion14) {
        this.preguntasSeccion14 = preguntasSeccion14;
    }

    public List<Pregunta> getPreguntasSeccion15() {
        return preguntasSeccion15;
    }

    public void setPreguntasSeccion15(List<Pregunta> preguntasSeccion15) {
        this.preguntasSeccion15 = preguntasSeccion15;
    }

    public List<Pregunta> getPreguntasSeccion16() {
        return preguntasSeccion16;
    }

    public void setPreguntasSeccion16(List<Pregunta> preguntasSeccion16) {
        this.preguntasSeccion16 = preguntasSeccion16;
    }

    public List<Pregunta> getPreguntasSeccion17() {
        return preguntasSeccion17;
    }

    public void setPreguntasSeccion17(List<Pregunta> preguntasSeccion17) {
        this.preguntasSeccion17 = preguntasSeccion17;
    }

    public List<Elemento> getElementos() {
        return elementos;
    }

    public void setElementos(List<Elemento> elementos) {
        this.elementos = elementos;
    }

    public List<Elemento> getEstablecimientos() {
        return establecimientos;
    }

    public void setEstablecimientos(List<Elemento> establecimientos) {
        this.establecimientos = establecimientos;
    }

    public List<Elemento> getEdificios() {
        return edificios;
    }

    public void setEdificios(List<Elemento> edificios) {
        this.edificios = edificios;
    }

    public List<Elemento> getEspacios() {
        return espacios;
    }

    public void setEspacios(List<Elemento> espacios) {
        this.espacios = espacios;
    }

    public List<Elemento> getPlanosPredios() {
        return planosPredios;
    }

    public void setPlanosPredios(List<Elemento> planosPredios) {
        this.planosPredios = planosPredios;
    }

    public List<Elemento> getPlanosEspacios() {
        return planosEspacios;
    }

    public void setPlanosEspacios(List<Elemento> planosEspacios) {
        this.planosEspacios = planosEspacios;
    }

    public List<Elemento> getObservacionesComplementarias() {
        return observacionesComplementarias;
    }

    public void setObservacionesComplementarias(List<Elemento> observacionesComplementarias) {
        this.observacionesComplementarias = observacionesComplementarias;
    }

    public Establecimiento getEstablecimiento() {
        return establecimiento;
    }

    public void setEstablecimiento(Establecimiento establecimiento) {
        this.establecimiento = establecimiento;
    }

    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    public Elemento getEdificioSeleccionado() {
        return this.edificioSeleccionado;
    }

    public void setEdificioSeleccionado(Elemento edificioSeleccionado) {
        this.edificioSeleccionado = edificioSeleccionado;
    }

    public void prepararEdificioSeleccionado(Elemento edificioSeleccionado) {
        this.edificioSeleccionado = edificioSeleccionado;
        for (Pregunta prg : this.listadoPreguntasEdificios) {
            for (Respuesta rsp : prg.getRespuestaList()) {
                RespuestaDig respuesta = new RespuestaDig(this.edificioSeleccionado, prg, rsp);
                this.edificioSeleccionado.agregarRespuesta(respuesta);
                this.edificioSeleccionado.agregarClaveMapa(rsp, respuesta);
            }
        }
        if (edificioSeleccionado.getId() != null) {
            for (RespuestaDig rspDig : fInstrumentos.obtenerRespuestasDigitadasPorElementoId(edificioSeleccionado.getId())) {
                rspDig.setAdjuntosList(new ArrayList<Adjunto>());
                rspDig.getAdjuntosList().addAll(fInstrumentos.obtenerAdjuntosPorRespuestaDigitada(rspDig.getId()));
                this.edificioSeleccionado.agregarClaveMapa(rspDig.getRespuesta(), rspDig);
            }
        } else {
            for (RespuestaDig rspDig : fInstrumentos.obtenerRespuestasDigitadasPorElemento(edificioSeleccionado)) {
                rspDig.setAdjuntosList(new ArrayList<Adjunto>());
                rspDig.getAdjuntosList().addAll(fInstrumentos.obtenerAdjuntosPorRespuestaDigitada(rspDig.getId()));
                this.edificioSeleccionado.agregarClaveMapa(rspDig.getRespuesta(), rspDig);
            }
        }
        Respuesta idEdificio = fInstrumentos.obtenerRespuestaPorCodigo("RESP_073");
        identificadorEdificioActual = UtilCadena.isNullOVacio(this.edificioSeleccionado.getMapaRespuestas().get(idEdificio).getValor())
                ? "" : this.edificioSeleccionado.getMapaRespuestas().get(idEdificio).getValor();

    }

    public void obtenerInfoPredio(Sede sede, Predio predio, Establecimiento est, NivelDPA n1, NivelDPA n2, NivelDPA n3, NivelDPA n4, NivelDPA n5) {
        //Informacion Geografica
        if (sede == null && predio != null) {
            String idSede = fPredio.obtenerSedePorPredio(predio.getId());
            sede = fEstablecimiento.getSedePorCodigo(idSede);
            est = sede.getEstablecimiento();
            Nivel n = est.getNivel();
            if (this.isActivoNivel5()) {
                n5 = n;
            } else if (this.isActivoNivel4()) {
                n4 = n;
            } else if (this.isActivoNivel3()) {
                n3 = n;
            } else if (this.isActivoNivel2()) {
                n2 = n;
            } else if (this.isActivoNivel1()) {
                n1 = n;
            }
            while (n1 == null) {
                n = n.getPadre();
                if (this.isActivoNivel4() && n4 == null) {
                    n4 = n;
                } else if (this.isActivoNivel3() && n3 == null) {
                    n3 = n;
                } else if (this.isActivoNivel2() && n2 == null) {
                    n2 = n;
                } else if (this.isActivoNivel1() && n1 == null) {
                    n1 = n;
                }
            }
        }

        this.cargarDPAPredio(n1, n2, n3, n4, n5);

        //info del predio 
        this.respuestasDigitadasMap.get(fInstrumentos.obtenerRespuestaPorCodigo("RESP_010")).setValor(predio.getCodigo());
        this.respuestasDigitadasMap.get(fInstrumentos.obtenerRespuestaPorCodigo("RESP_294")).setValor(predio.getNombre());

        // info sede
        this.respuestasDigitadasMap.get(fInstrumentos.obtenerRespuestaPorCodigo("RESP_011")).setValor(sede.getCodigo());
        this.respuestasDigitadasMap.get(fInstrumentos.obtenerRespuestaPorCodigo("RESP_012_01")).setValor(sede.getCodSector());
        this.respuestasDigitadasMap.get(fInstrumentos.obtenerRespuestaPorCodigo("RESP_013")).setValor(sede.getCodAutoridad());
        this.respuestasDigitadasMap.get(fInstrumentos.obtenerRespuestaPorCodigo("RESP_014")).setValor(sede.getNombre());
        this.respuestasDigitadasMap.get(fInstrumentos.obtenerRespuestaPorCodigo("RESP_016")).setValor(sede.getTelefono());
        this.respuestasDigitadasMap.get(fInstrumentos.obtenerRespuestaPorCodigo("RESP_018")).setValor(sede.getEmailSede());
        this.respuestasDigitadasMap.get(fInstrumentos.obtenerRespuestaPorCodigo("RESP_019")).setValor(sede.getRector());
        this.respuestasDigitadasMap.get(fInstrumentos.obtenerRespuestaPorCodigo("RESP_020")).setValor(sede.getTelRector());
        this.respuestasDigitadasMap.get(fInstrumentos.obtenerRespuestaPorCodigo("RESP_021")).setValor(sede.getEmailRector());

        // info Establecimiento
        est = facEstDPA.consultarEstablecimiento(Long.valueOf(est.getId()));

        this.respuestasDigitadasMap.get(fInstrumentos.obtenerRespuestaPorCodigo("RESP_023")).setValor(est.getCodigo());
        this.respuestasDigitadasMap.get(fInstrumentos.obtenerRespuestaPorCodigo("RESP_024")).setValor(est.getNombre());
    }

    public void obtenerInfoSede(Sede sede) {
        //Informacion Geografica
        if (!UtilCadena.isNullOVacio(sede)) {
            //info de la sede ("establecimiento") 
            String codigoSede = UtilCadena.isNullOVacio(sede.getCodigo()) ? "" : sede.getCodigo();
            String nombreSede = UtilCadena.isNullOVacio(sede.getNombre()) ? "" : sede.getNombre();
            String emailSede = UtilCadena.isNullOVacio(sede.getEmailSede()) ? "" : sede.getEmailSede();
            String telRectorSede = UtilCadena.isNullOVacio(sede.getTelRector()) ? "" : sede.getTelRector();
            String rectorSede = UtilCadena.isNullOVacio(sede.getRector()) ? "" : sede.getRector();
            String telefonoSede = UtilCadena.isNullOVacio(sede.getTelefono()) ? "" : sede.getTelefono();
            String codEstPrincipal = UtilCadena.isNullOVacio(sede.getEstablecimiento().getCodigo()) ? "" : sede.getEstablecimiento().getCodigo();
            String nomEstPrincipal = UtilCadena.isNullOVacio(sede.getEstablecimiento().getNombre()) ? "" : sede.getEstablecimiento().getNombre();

            this.establecimientoSeleccionado.getMapaRespuestas().get(fInstrumentos.obtenerRespuestaPorCodigo("RESP_193")).setValor(codigoSede);
            this.establecimientoSeleccionado.getMapaRespuestas().get(fInstrumentos.obtenerRespuestaPorCodigo("RESP_194")).setValor(nombreSede);
            this.establecimientoSeleccionado.getMapaRespuestas().get(fInstrumentos.obtenerRespuestaPorCodigo("RESP_195")).setValor(emailSede);
            this.establecimientoSeleccionado.getMapaRespuestas().get(fInstrumentos.obtenerRespuestaPorCodigo("RESP_196")).setValor(telRectorSede);
            this.establecimientoSeleccionado.getMapaRespuestas().get(fInstrumentos.obtenerRespuestaPorCodigo("RESP_197")).setValor(rectorSede);
            this.establecimientoSeleccionado.getMapaRespuestas().get(fInstrumentos.obtenerRespuestaPorCodigo("RESP_291")).setValor(telefonoSede);
            this.establecimientoSeleccionado.getMapaRespuestas().get(fInstrumentos.obtenerRespuestaPorCodigo("RESP_292")).setValor(codEstPrincipal);
            this.establecimientoSeleccionado.getMapaRespuestas().get(fInstrumentos.obtenerRespuestaPorCodigo("RESP_293")).setValor(nomEstPrincipal);

            FacesContext context = Faces.getContext();
            BusquedaSedeBean busquedaSedeBean = (BusquedaSedeBean) context.getELContext().getELResolver().getValue(context.getELContext(), null, "busquedaSede");
            busquedaSedeBean.resetearFiltros();
        }
    }

    private void cargarDPAPredio(NivelDPA n1, NivelDPA n2, NivelDPA n3, NivelDPA n4, NivelDPA n5) {
        try {

            if (n1 != null) {
                this.respuestasDigitadasMap.get(fInstrumentos.obtenerRespuestaPorCodigo("RESP_031_02")).setValor(n1.getId().toString());
                this.seleccionNivel1 = n1;
                this.cambioNivel1();
            }
            if (n2 != null) {
                this.respuestasDigitadasMap.get(fInstrumentos.obtenerRespuestaPorCodigo("RESP_031_03")).setValor(n2.getId().toString());
                this.seleccionNivel2 = n2;
                this.cambioNivel2();
            }
            if (n3 != null) {
                this.respuestasDigitadasMap.get(fInstrumentos.obtenerRespuestaPorCodigo("RESP_031_04")).setValor(n3.getId().toString());
                this.seleccionNivel3 = n3;
                this.cambioNivel3();
            }
            if (n4 != null) {
                this.respuestasDigitadasMap.get(fInstrumentos.obtenerRespuestaPorCodigo("RESP_031_05")).setValor(n4.getId().toString());
                this.seleccionNivel4 = n4;
                this.cambioNivel4();
            }
            if (n5 != null) {
                this.respuestasDigitadasMap.get(fInstrumentos.obtenerRespuestaPorCodigo("RESP_031_06")).setValor(n5.getId().toString());
                this.seleccionNivel5 = n5;
                this.cambioNivel5();
            }


        } catch (Exception ex) {
            mostrarMensajeError(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"), ex.getMessage());
            ManejadorErrores.ingresarExcepcionEnLog(ex, DigitacionInstrumentoBean.class);
        }
    }

    public Elemento getEspacioSeleccionado() {
        return espacioSeleccionado;
    }

    public void setEspacioSeleccionado(Elemento espacioSeleccionado) {
        this.espacioSeleccionado = espacioSeleccionado;
    }

    public void prepararEspacioSeleccionado(Elemento espacioSeleccionado) {
        this.espacioSeleccionado = espacioSeleccionado;
        for (Pregunta prg : this.listadoPreguntasEspacios) {
            for (Respuesta rsp : prg.getRespuestaList()) {
                RespuestaDig respuesta = new RespuestaDig(this.espacioSeleccionado, prg, rsp);
                if (respuesta.getRespuesta().getCodigo().equals("RESP_106")) {
                    respuesta.setValor("0");
                }
                this.espacioSeleccionado.agregarRespuesta(respuesta);
                this.espacioSeleccionado.agregarClaveMapa(rsp, respuesta);
            }
        }
        if (espacioSeleccionado.getId() != null) {
            for (RespuestaDig rspDig : fInstrumentos.obtenerRespuestasDigitadasPorElementoId(espacioSeleccionado.getId())) {
                rspDig.setAdjuntosList(new ArrayList<Adjunto>());
                rspDig.getAdjuntosList().addAll(fInstrumentos.obtenerAdjuntosPorRespuestaDigitada(rspDig.getId()));
                this.espacioSeleccionado.agregarClaveMapa(rspDig.getRespuesta(), rspDig);
            }
        } else {
            for (RespuestaDig rspDig : fInstrumentos.obtenerRespuestasDigitadasPorElemento(espacioSeleccionado)) {
                rspDig.setAdjuntosList(new ArrayList<Adjunto>());
                rspDig.getAdjuntosList().addAll(fInstrumentos.obtenerAdjuntosPorRespuestaDigitada(rspDig.getId()));
                this.espacioSeleccionado.agregarClaveMapa(rspDig.getRespuesta(), rspDig);
            }
        }
    }

    public Elemento getObservacionSeleccionada() {
        return observacionSeleccionada;
    }

    public void setObservacionSeleccionada(Elemento observacionSeleccionada) {
        this.observacionSeleccionada = observacionSeleccionada;
    }

    public void prepararObservacionSeleccionado(Elemento observacionSeleccionada) {
        this.observacionSeleccionada = observacionSeleccionada;
        for (Pregunta prg : this.listadoPreguntasObservaciones) {
            for (Respuesta rsp : prg.getRespuestaList()) {
                RespuestaDig respuesta = new RespuestaDig(this.observacionSeleccionada, prg, rsp);
                this.observacionSeleccionada.agregarRespuesta(respuesta);
                this.observacionSeleccionada.agregarClaveMapa(rsp, respuesta);
            }
        }
        if (observacionSeleccionada.getId() != null) {
            for (RespuestaDig rspDig : fInstrumentos.obtenerRespuestasDigitadasPorElementoId(observacionSeleccionada.getId())) {
                rspDig.setAdjuntosList(new ArrayList<Adjunto>());
                rspDig.getAdjuntosList().addAll(fInstrumentos.obtenerAdjuntosPorRespuestaDigitada(rspDig.getId()));
                this.observacionSeleccionada.agregarClaveMapa(rspDig.getRespuesta(), rspDig);
            }
        } else {
            for (RespuestaDig rspDig : fInstrumentos.obtenerRespuestasDigitadasPorElemento(observacionSeleccionada)) {
                rspDig.setAdjuntosList(new ArrayList<Adjunto>());
                rspDig.getAdjuntosList().addAll(fInstrumentos.obtenerAdjuntosPorRespuestaDigitada(rspDig.getId()));
                this.observacionSeleccionada.agregarClaveMapa(rspDig.getRespuesta(), rspDig);
            }
        }
    }

    public Elemento getPlanoEspacioSeleccionado() {
        return planoEspacioSeleccionado;
    }

    public void prepararEdificioPlanoEspacio(Elemento planoEspacioSeleccionado) {
        this.planoEspacioSeleccionado = planoEspacioSeleccionado;
        for (Pregunta prg : this.listadoPreguntasPlanosEspacios) {
            for (Respuesta rsp : prg.getRespuestaList()) {
                RespuestaDig respuesta = new RespuestaDig(this.planoEspacioSeleccionado, prg, rsp);
                this.planoEspacioSeleccionado.agregarRespuesta(respuesta);
                this.planoEspacioSeleccionado.agregarClaveMapa(rsp, respuesta);
            }
        }
        if (planoEspacioSeleccionado.getId() != null) {
            for (RespuestaDig rspDig : fInstrumentos.obtenerRespuestasDigitadasPorElementoId(planoEspacioSeleccionado.getId())) {
                rspDig.setAdjuntosList(new ArrayList<Adjunto>());
                rspDig.getAdjuntosList().addAll(fInstrumentos.obtenerAdjuntosPorRespuestaDigitada(rspDig.getId()));
                this.planoEspacioSeleccionado.agregarClaveMapa(rspDig.getRespuesta(), rspDig);
            }
        } else {
            for (RespuestaDig rspDig : fInstrumentos.obtenerRespuestasDigitadasPorElemento(planoEspacioSeleccionado)) {
                rspDig.setAdjuntosList(new ArrayList<Adjunto>());
                rspDig.getAdjuntosList().addAll(fInstrumentos.obtenerAdjuntosPorRespuestaDigitada(rspDig.getId()));
                this.planoEspacioSeleccionado.agregarClaveMapa(rspDig.getRespuesta(), rspDig);
            }
        }
    }

    // Adjuntos
    public void cargarArchivo(FileUploadEvent event) throws IOException {
        if (archivosCargados == null) {
            archivosCargados = new ArrayList<UploadedFile>();
        }

        archivosCargados.add(event.getFile());
    }

    public void guardarArchivo() {
        try {
            if (archivosCargados == null || archivosCargados.isEmpty()) {
                return;
            }
            for (UploadedFile uploadedFile : archivosCargados) {
                String rutaServidor = facArchivo.getPathname(instrumentoDigitado.getId());
                String prefix = facArchivo.getPrefix(uploadedFile.getFileName(), instrumentoDigitado.getId());
                //String prefix = respuestaDig.getElemento().getId() + "_" + uploadedFile.getFileName();
                String suffix = prefix.substring(prefix.lastIndexOf("."), prefix.length());
                String pathName = facArchivo.getPathname(instrumentoDigitado.getId());
                File destino = new File(pathName);
                if (facArchivo.subirArchivo(destino, prefix, uploadedFile.getInputstream())) {
                    Adjunto nuevoAdjunto = new Adjunto();
                    nuevoAdjunto.setRuta(rutaServidor + prefix);
                    nuevoAdjunto.setTipo(suffix.toUpperCase());
                    nuevoAdjunto.setContentType(uploadedFile.getContentType());
                    nuevoAdjunto.setNombre(prefix);
                    nuevoAdjunto.setUsuarioCreacion(usuario.getUsuario().getUsername());
                    nuevoAdjunto.setRespuestaDigitada(this.respuestaDig);
                    //listaAdjunto.add(nuevoAdjunto);
                    if (respuestaDig.getAdjuntosList() == null) {
                        List<Adjunto> listaAdjunto = new ArrayList<Adjunto>();
                        respuestaDig.setAdjuntosList(listaAdjunto);
                    }
                    respuestaDig.getAdjuntosList().add(nuevoAdjunto);
                    respuestaDig.setValor("1");
                }
            }
            //respuestaDig.setValor("1");
            //respuestaDig.setAdjuntosList(listaAdjunto);
            respuestaDig = fInstrumentos.guardarRespuestaDigitadaAdjunto(respuestaDig);
            archivosCargados = new ArrayList<UploadedFile>();
            respuestaDig = fInstrumentos.obtenerRespuestaDigitadaPorIdInstrumentoCodigoRespuesta(instrumentoDigitado.getId(), respuestaDig.getRespuesta().getCodigo());
            respuestasDigitadasMap.put(respuestaDig.getRespuesta(), respuestaDig);
            Utilidades.mostrarMensajeInfo(Utilidades.obtenerMensaje("aplicacion.general.operacionExitosa"), Utilidades.obtenerMensaje("aplicacion.general.registroEnServidor"));

        } catch (IOException ex) {
            Utilidades.mostrarMensajeError(Utilidades.obtenerMensaje("adm.parametros.config.carga.errorRegistro"), Utilidades.obtenerMensaje("adm.parametros.config.carga.errorRegistro"));
        } catch (Exception e) {
            Utilidades.mostrarMensajeError(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"), Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"));
            ManejadorErrores.ingresarExcepcionEnLog(e, DigitacionInstrumentoBean.class);
        }
    }

    public void adicionarAdjuntoARespuestaDigitada(Adjunto adjunto) {
        if (respuestaDig.getAdjuntosList() == null) {
            List<Adjunto> listaAdjunto = new ArrayList<Adjunto>();
            respuestaDig.setAdjuntosList(listaAdjunto);
        }
        respuestaDig.getAdjuntosList().add(adjunto);
        respuestaDig.setValor("1");
        //fInstrumento.guardarRespuestaDigitado(respuesta);
    }

    public void descargarDocumento(Adjunto adjunto) {
        ServletOutputStream out = null;
        FileInputStream fis = null;
        try {
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
                Logger.getLogger(DigitacionInstrumentoBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void eliminarAdjunto(Adjunto adjunto) {
        try {
            String nombre = adjunto.getRuta();
            if (adjunto.getId() != null) {
                if (facArchivo.eliminarArchivo(adjunto, usuario.getUsuario())) {
                    respuestaDig.getAdjuntosList().remove(adjunto);
                    if (respuestaDig.getAdjuntosList().isEmpty()) {
                        respuestaDig.setValor("0");
                        fInstrumentos.actualizarRespuestaDigitada(respuestaDig);
                    }
                    if (adjunto.getId() != null) {
                        fInstrumentos.eliminarAdjunto(adjunto);
                    }
                    Utilidades.mostrarMensajeInfo(Utilidades.obtenerMensaje("aplicacion.general.operacionExitosa"), Utilidades.obtenerMensaje("aplicacion.general.eliminacionEnServidor"));
                } else {
                    Utilidades.mostrarMensajeAdvertencia(Utilidades.obtenerMensaje("consulta.general.noExisteArchivoServidor"), Utilidades.obtenerMensaje("consulta.general.archivoSolicitado") + " " + nombre + Utilidades.obtenerMensaje("consulta.general.noExisteArchivoServidor"));
                }
            }
        } catch (Exception e) {
            Utilidades.mostrarMensajeError(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"), Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"));
            ManejadorErrores.ingresarExcepcionEnLog(e, DigitacionInstrumentoBean.class);
        }
    }

    public void resetar() {
        this.respuestaDig = null;
    }

    public void consultarRespuesta(RespuestaDig respDig) {
        try {
            adjuntar = false;
            respuestaDig = respDig;
            if (respDig.getElemento() != null
                    && respDig.getElemento().getInstrumentoDigitado() != null
                    && respDig.getElemento().getInstrumentoDigitado().getId() != null) {
                adjuntar = true;
                respuestaDig.setIdInstrumentoDigitado(respDig.getElemento().getInstrumentoDigitado().getId());
                respuestaDig.setElemento(obtenerElemento(respDig.getElemento().getInstrumentoDigitado().getId()));
                RespuestaDig respDigSearh = fInstrumentos.obtenerRespuestaDigitadaPorIdInstrumentoCodigoRespuesta(respDig.getElemento().getInstrumentoDigitado().getId(), respDig.getRespuesta().getCodigo());
                if (respDigSearh != null) {
                    respuestaDig = respDigSearh;
                }
                respuestaDig.setIdInstrumentoDigitado(respDig.getElemento().getInstrumentoDigitado().getId());
                respuestaDig.setElemento(obtenerElemento(respDig.getElemento().getInstrumentoDigitado().getId()));
                List<Adjunto> listAdjuntos = new ArrayList<Adjunto>();
                listAdjuntos = fInstrumentos.obtenerAdjuntosPorRespuestaDigitada(respuestaDig.getId());
                respuestaDig.setAdjuntosList(listAdjuntos);

            } else {
                adjuntar = false;
                Utilidades.mostrarMensajeError(Utilidades.obtenerMensaje("aplicacion.general.errorValidacion"), Utilidades.obtenerMensaje("dig.creacion.lbl.adjunto.instrumento"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            ManejadorErrores.ingresarExcepcionEnLog(ex, GestionArchivos.class);
        }
    }

    private Elemento obtenerElemento(Long idInstrumento) {
        return fInstrumentos.obtenerElementoPorIdInstrumentoDigitadoTipoElemento(idInstrumento, "ELE_PRED");
    }

    public RespuestaDig getRespuestaDig() {
        return respuestaDig;
    }

    public void setRespuestaDig(RespuestaDig respuestaDig) {
        this.respuestaDig = respuestaDig;
    }

    public void setPlanoEspacioSeleccionado(Elemento planoEspacioSeleccionado) {
        this.planoEspacioSeleccionado = planoEspacioSeleccionado;
    }

    public Elemento getEstablecimientoSeleccionado() {
        return establecimientoSeleccionado;
    }

    public void setEstablecimientoSeleccionado(Elemento establecimientoSeleccionado) {
        this.establecimientoSeleccionado = establecimientoSeleccionado;
    }

    public void prepararEstablecimientoSeleccionado(Elemento establecimientoSeleccionado) {
        this.establecimientoSeleccionado = establecimientoSeleccionado;
        for (Pregunta prg : this.listadoPreguntasEstablecimientos) {
            for (Respuesta rsp : prg.getRespuestaList()) {
                RespuestaDig respuesta = new RespuestaDig(this.establecimientoSeleccionado, prg, rsp);
                this.establecimientoSeleccionado.agregarRespuesta(respuesta);
                this.establecimientoSeleccionado.agregarClaveMapa(rsp, respuesta);
            }
        }
        if (establecimientoSeleccionado.getId() != null) {
            for (RespuestaDig rspDig : fInstrumentos.obtenerRespuestasDigitadasPorElementoId(establecimientoSeleccionado.getId())) {
                rspDig.setAdjuntosList(new ArrayList<Adjunto>());
                rspDig.getAdjuntosList().addAll(fInstrumentos.obtenerAdjuntosPorRespuestaDigitada(rspDig.getId()));
                this.establecimientoSeleccionado.agregarClaveMapa(rspDig.getRespuesta(), rspDig);
            }
        } else {
            for (RespuestaDig rspDig : fInstrumentos.obtenerRespuestasDigitadasPorElemento(establecimientoSeleccionado)) {
                rspDig.setAdjuntosList(new ArrayList<Adjunto>());
                rspDig.getAdjuntosList().addAll(fInstrumentos.obtenerAdjuntosPorRespuestaDigitada(rspDig.getId()));
                this.establecimientoSeleccionado.agregarClaveMapa(rspDig.getRespuesta(), rspDig);
            }
        }
    }

    public int getJornadaIndex() {
        jornadaIndex = jornadaIndex + 4;
        return jornadaIndex;
    }

    public void setJornadaIndex(int jornadaIndex) {
        this.jornadaIndex = jornadaIndex;
    }

    public int getNivelIndex() {
        this.nivelIndex = nivelIndex + 4;
        return nivelIndex;
    }

    public void setNivelIndex(int nivelIndex) {
        this.nivelIndex = nivelIndex;
    }

    public int getCursoIndex() {
        cursoIndex = cursoIndex + 4;
        return cursoIndex;
    }

    public void setCursoIndex(int cursoIndex) {
        this.cursoIndex = cursoIndex;
    }

    public int getMatrculaIndex() {
        matrculaIndex = matrculaIndex + 4;
        return matrculaIndex;
    }

    public void setMatrculaIndex(int matrculaIndex) {
        this.matrculaIndex = matrculaIndex;
    }

    public int getTandaIndex() {
        tandaIndex = tandaIndex + 4;
        return tandaIndex;
    }

    public void setTandaIndex(int tandaIndex) {
        this.tandaIndex = tandaIndex;
    }

    public int getDocentesIndex() {
        docentesIndex = docentesIndex + 4;
        return docentesIndex;
    }

    public void setDocentesIndex(int docentesIndex) {
        this.docentesIndex = docentesIndex;
    }

    public int getAdminIndex() {
        adminIndex = adminIndex + 4;
        return adminIndex;
    }

    public void setAdminIndex(int adminIndex) {
        this.adminIndex = adminIndex;
    }

    public int getServicioIndex() {
        servicioIndex = servicioIndex + 4;
        return servicioIndex;
    }

    public void setServicioIndex(int servicioIndex) {
        this.servicioIndex = servicioIndex;
    }

    public boolean isValorCheck(String valor) {
        return valorCheck;
    }

    public void setValorCheck(boolean valorCheck) {
        this.valorCheck = valorCheck;
    }

    public void hacer(RespuestaDig res) {
        System.out.println("Hola");

    }

    public GentionVariablesBean getGestionVariable() {
        return gestionVariable;
    }

    public void setGestionVariable(GentionVariablesBean gestionVariable) {
        this.gestionVariable = gestionVariable;
    }

    public String getMensajeEliminacion() {
        mensajeEliminacion = Utilidades.obtenerMensaje("dig.eliminar.elementos.mensaje", elementoEliminado.getDescripcion() == null ? "" : elementoEliminado.getDescripcion());
        return mensajeEliminacion;
    }

    public void setMensajeEliminacion(String mensajeEliminacion) {
        this.mensajeEliminacion = mensajeEliminacion;
    }

    public Elemento getElementoEliminado() {
        return elementoEliminado;
    }

    public void setElementoEliminado(Elemento elementoEliminado) {
        this.elementoEliminado = elementoEliminado;
    }

    public String getTablaActualizada() {
        return tablaActualizada;
    }

    public void setTablaActualizada(String tablaActualizada) {
        this.tablaActualizada = tablaActualizada;
    }

    public InstrumentoDig getInstrumentoDigitado() {
        return instrumentoDigitado;
    }

    public void setInstrumentoDigitado(InstrumentoDig instrumentoDigitado) {
        this.instrumentoDigitado = instrumentoDigitado;
    }

    public List<FuenteInformacion> getFuentesInformacion() {
        return fuentesInformacion;
    }

    public void setFuentesInformacion(List<FuenteInformacion> fuentesInformacion) {
        this.fuentesInformacion = fuentesInformacion;
    }

    public List<TipoEstablecimiento> getTipoEstablecimiento() {
        return tipoEstablecimiento;
    }

    public void setTipoEstablecimiento(List<TipoEstablecimiento> tipoEstablecimiento) {
        this.tipoEstablecimiento = tipoEstablecimiento;
    }

    public boolean isMostrarBoton() {
        return this.instrumentoDigitado.getId() != null && !this.instrumentoDigitado.getEstado().equals(EstadoInstrumento.E);
    }

    public List<Topografias> getTopografias() {
        return topografias;
    }

    public void setTopografias(List<Topografias> topografias) {
        this.topografias = topografias;
    }

    public List<ActivoInactivo> getActivoInactivo() {
        return activoInactivo;
    }

    public void setActivoInactivo(List<ActivoInactivo> activoInactivo) {
        this.activoInactivo = activoInactivo;
    }

    public List<Verificable> getVerificable() {
        return verificable;
    }

    public void setVerificable(List<Verificable> verificable) {
        this.verificable = verificable;
    }

    public List<SuficienteInsuficiente> getSuficiente() {
        return suficiente;
    }

    public void setSuficiente(List<SuficienteInsuficiente> suficiente) {
        this.suficiente = suficiente;
    }

    public Wizard getWizard() {
        return wizard;
    }

    public void setWizard(Wizard wizard) {
        this.wizard = wizard;
    }

    public boolean isMostrarSiguiente() {
        return true;
    }

    public boolean isMostrarAnterior() {
        return true;
    }

    public void calcularArea() {
        double area = 0;
        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
        simbolos.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("0.00", simbolos);
        try {
            RespuestaDig largo = this.espacioSeleccionado.getMapaRespuestas().get(fInstrumentos.obtenerRespuestaPorCodigo("RESP_102"));
            RespuestaDig ancho = this.espacioSeleccionado.getMapaRespuestas().get(fInstrumentos.obtenerRespuestaPorCodigo("RESP_103"));
            area = Double.valueOf(largo.getValor()) * Double.valueOf(ancho.getValor());
            this.espacioSeleccionado.getMapaRespuestas().get(fInstrumentos.obtenerRespuestaPorCodigo("RESP_104")).setValor(String.valueOf(df.format(area)));


        } catch (Exception ex) {
            //ex.printStackTrace();
            ManejadorErrores.ingresarExcepcionEnLog(ex, DigitacionInstrumentoBean.class);
        }

        try {
            RespuestaDig areaDig = this.espacioSeleccionado.getMapaRespuestas().get(fInstrumentos.obtenerRespuestaPorCodigo("RESP_104"));
            RespuestaDig altura = this.espacioSeleccionado.getMapaRespuestas().get(fInstrumentos.obtenerRespuestaPorCodigo("RESP_105"));

            double volumen = Double.valueOf(areaDig.getValor()) * Double.valueOf(altura.getValor());
            this.espacioSeleccionado.getMapaRespuestas().get(fInstrumentos.obtenerRespuestaPorCodigo("RESP_106")).setValor(String.valueOf(df.format(volumen)));

        } catch (Exception ex) {
            //ex.printStackTrace();
            ManejadorErrores.ingresarExcepcionEnLog(ex, DigitacionInstrumentoBean.class);
        }
    }

    public boolean motrarCoordenada(String coordenada) {
        boolean mostrar = false;
        try {
            String parametro = (facParam.consultarParametro("COORDENADA_UTM")).getValor();
            if (parametro.equals("3")) {
                mostrar = true;
            } else {
                if (parametro.equals("1") && coordenada.equals("Lon/Lat")) {
                    mostrar = true;
                } else {
                    if (parametro.equals("2") && coordenada.equals("UTM")) {
                        mostrar = true;
                    } else {
                        mostrar = false;
                    }
                }
            }
        } catch (Exception e) {
            ManejadorErrores.ingresarExcepcionEnLog(e, DigitacionInstrumentoBean.class);
        }
        return mostrar;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public boolean isRenderRespNoEmitidas() {
        return renderRespNoEmitidas;
    }

    public void setRenderRespNoEmitidas(boolean renderRespNoEmitidas) {
        this.renderRespNoEmitidas = renderRespNoEmitidas;
    }

    public boolean isDisableCodBoletaAndPredio() {
        return instrumentoDigitado.getVersion() > 1;
    }

    public void setDisableCodBoletaAndPredio(boolean disableCodBoletaAndPredio) {
        this.disableCodBoletaAndPredio = disableCodBoletaAndPredio;
    }

    public List<UploadedFile> getArchivosCargados() {
        return archivosCargados;
    }

    public void setArchivosCargados(List<UploadedFile> archivosCargados) {
        this.archivosCargados = archivosCargados;
    }

    public Boolean getAdjuntar() {
        return adjuntar;
    }

    public void setAdjuntar(Boolean adjuntar) {
        this.adjuntar = adjuntar;
    }

    public UsuarioSesionBean getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioSesionBean usuario) {
        this.usuario = usuario;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public List<EspacioSimilar> getEspaciosDigitados() {
        return espaciosDigitados;
    }

    public void setEspaciosDigitados(List<EspacioSimilar> espaciosDigitados) {
        this.espaciosDigitados = espaciosDigitados;
    }
}


