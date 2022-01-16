/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.administracion.configuracion;

import co.stackpointer.cier.modelo.entidad.digitado.Elemento;
import co.stackpointer.cier.modelo.entidad.digitado.InstrumentoDig;
import co.stackpointer.cier.modelo.entidad.digitado.RespuestaDig;
import co.stackpointer.cier.modelo.entidad.dpa.Nivel;
import co.stackpointer.cier.modelo.entidad.establecimiento.Establecimiento;
import co.stackpointer.cier.modelo.entidad.establecimiento.Predio;
import co.stackpointer.cier.modelo.entidad.establecimiento.Sede;
import co.stackpointer.cier.modelo.entidad.instrumento.Instrumento;
import co.stackpointer.cier.modelo.entidad.instrumento.Pregunta;
import co.stackpointer.cier.modelo.entidad.instrumento.Respuesta;
import co.stackpointer.cier.modelo.entidad.instrumento.TipoElemento;
import co.stackpointer.cier.modelo.excepcion.ErrorValidacion;
import co.stackpointer.cier.modelo.fachada.EstablecimientoFacadeLocal;
import co.stackpointer.cier.modelo.fachada.InstrumentoFacadeLocal;
import co.stackpointer.cier.modelo.fachada.PredioFacadeLocal;
import co.stackpointer.cier.modelo.tipo.ErroresInstrumentos;
import co.stackpointer.cier.modelo.tipo.EstadoInstrumento;
import co.stackpointer.cier.modelo.tipo.TipoDato;
import co.stackpointer.cier.modelo.tipo.TipoDatoExcel;
import co.stackpointer.cier.modelo.utilidad.UtilCadena;
import co.stackpointer.cier.modelo.utilidad.UtilFecha;
import co.stackpointer.cier.vista.ManejadorErrores;
import co.stackpointer.cier.vista.Utilidades;
import static co.stackpointer.cier.vista.Utilidades.mostrarMensajeError;
import static co.stackpointer.cier.vista.Utilidades.mostrarMensajeInfo;
import co.stackpointer.cier.vistacontrolador.bean.UsuarioSesionBean;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.omnifaces.util.Messages;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import static co.stackpointer.cier.vistacontrolador.bean.importacion.ImpUtil.getValorCelda;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Date;
import java.util.Formatter;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.bean.ManagedProperty;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;

/**
 *
 * @author rarp1
 */
@ManagedBean(name = "archivoInstrumentos")
@ViewScoped
public class ArchivoInstrumentos implements Serializable {

    @EJB
    EstablecimientoFacadeLocal fachada;
    @EJB
    InstrumentoFacadeLocal fachadaInstrumentos;
    @EJB
    EstablecimientoFacadeLocal fEstablecimiento;
    @EJB
    PredioFacadeLocal fachadaPredio;
    @ManagedProperty(value = "#{usuarioSesion}")
    private UsuarioSesionBean usuarioSesionBean;
    protected Map parametros = new HashMap();
    protected List<ResultadoCoordenada> resultados;
    public boolean hayRegErroneos, archivoValido, mostrarMsgGuardar;
    public UploadedFile file;
    Workbook wb = new XSSFWorkbook();
    FileOutputStream out;
    File fileTemp;
    public static int numeroHojas = 13;
    public static int numeroDatosVacio = 129;
    public static int maximoInstrumentos = 100;
    public static int comienzoFilas = 5;
    public static int coberturaMaxima = 10;
    public static int inicioColumnas = 2;
    public static int inicioColumnasM1 = 3;
    public HashMap<Integer, String> erroresColumnaModulo1;
    public HashMap<Integer, String> erroresColumnaCoberturaM1;
    public HashMap<Integer, String> erroresColumnaModulo2;
    public HashMap<Integer, String> erroresColumnaModulo3;
    public HashMap<Integer, String> erroresColumnaModulo4;
    public HashMap<Integer, String> erroresColumnaModuloA1;
    public HashMap<Integer, String> erroresColumnaModuloEstaB1;
    public HashMap<Integer, String> erroresColumnaCoberturaB1;
    public HashMap<Integer, String> erroresColumnaModuloB1;
    public HashMap<Integer, String> erroresColumnaModuloCondicionC1;
    public HashMap<Integer, String> erroresColumnaModuloD1;
    public int numeroInstrumentos = 0;
    public HashMap<String, Integer> itemIndice;
    public HashMap<String, Integer> itemIndiceControl;
    public HashMap<Integer, Cell> itemIndiceCoberturaM1;
    public HashMap<Integer, Cell> itemIndiceModulo2;
    public HashMap<Integer, Cell> itemIndiceModulo3;
    public HashMap<Integer, Cell> itemIndiceModulo4;
    public HashMap<Integer, Cell> itemIndiceModuloA1;
    public HashMap<Integer, Cell> itemIndiceCoberturaB1;
    public HashMap<Integer, Cell> itemIndiceModuloEstaB1;
    public HashMap<Integer, Cell> itemIndiceModuloB1;
    public HashMap<Integer, Cell> itemIndiceModuloCondicionC1;
    public HashMap<Integer, Cell> itemIndiceModuloD1;
    public HashMap<String, HashSet<String>> edificiosModulo3;
    public Map<Integer, Pregunta> mapPreguntas;
    public Map<Integer, Respuesta> mapRespuestas;
    public Map<Integer, TipoDatoExcel> mapTipologia;
    public Map<Long, Elemento> mapElementosEst;
    public Map<String, Elemento> mapElementosEstB1;
    public HashSet<String> idInstrumentos;
    public HashSet<String> sedesValidas;
    public Set<String> instrumentosDigitados;
    public Map<String, Respuesta> complementoPredio = new HashMap<String, Respuesta>();
    public Map<String, Respuesta> complementoEspacio = new HashMap<String, Respuesta>();
    public Map<String, Respuesta> complementoEdificio = new HashMap<String, Respuesta>();
    public Set<String> controlPredio;
    public Set<String> controlEspacio;
    public Set<String> controlEdificio;
    public Map<String, String> complementosEstablecimientos;

    public void guardarInformacion() throws IOException {
        if (file != null && file.getFileName() != null) {
            Row rowDato, row;
            int rowNumber = 0;
            int contadorFilas = 0;
            Cell cell;
            mapPreguntas = new HashMap<Integer, Pregunta>();
            mapRespuestas = new HashMap<Integer, Respuesta>();
            mapTipologia = new HashMap<Integer, TipoDatoExcel>();
            mapElementosEst = new HashMap<Long, Elemento>();
            mapElementosEstB1 = new HashMap<String, Elemento>();
            instrumentosDigitados = new HashSet<String>();
            for (Respuesta resp : fachadaInstrumentos.obtenerRespuestasDependientesByTipoElemento("ELE_PRED")) {
                complementoPredio.put(resp.getCodigo(), resp);
            }
            for (Respuesta resp : fachadaInstrumentos.obtenerRespuestasDependientesByTipoElemento("ELE_EDI")) {
                complementoEdificio.put(resp.getCodigo(), resp);
            }
            for (Respuesta resp : fachadaInstrumentos.obtenerRespuestasDependientesByTipoElemento("ELE_ESP")) {
                complementoEspacio.put(resp.getCodigo(), resp);
            }
            try {
                //Modulo 1
                Sheet modulo1 = wb.getSheetAt(0);
                while (true) {
                    rowDato = modulo1.getRow(rowNumber);
                    complementosEstablecimientos = new HashMap<String, String>();
                    if (rowDato == null) {
                        break;
                    }
                    if (rowNumber == 3 || rowNumber == 4) {
                        rowNumber++;
                        continue;
                    }
                    if (rowNumber == 0) {
                        for (int col = inicioColumnasM1 + 2; col <= 35; col++) {
                            Pregunta pregunta = new Pregunta(getValorCelda(rowDato.getCell(col)));
                            mapPreguntas.put(col, pregunta);
                        }
                        rowNumber++;
                        continue;
                    }
                    if (rowNumber == 1) {
                        for (int col = inicioColumnasM1 + 2; col <= 35; col++) {
                            Respuesta respuesta = new Respuesta(getValorCelda(rowDato.getCell(col)));
                            mapRespuestas.put(col, respuesta);
                        }
                        rowNumber++;
                        continue;
                    }
                    if (rowNumber == 2) {
                        for (int col = inicioColumnasM1 + 2; col <= 35; col++) {
                            String valor = getValorCelda(rowDato.getCell(col));
                            TipoDatoExcel tde;
                            if (valor.equals("1")) {
                                tde = TipoDatoExcel.TIPOLOGIA;
                            } else if (valor.equals("4")) {
                                tde = TipoDatoExcel.FECHA;
                            } else {
                                tde = TipoDatoExcel.CADENA;
                            }
                            mapTipologia.put(col, tde);
                        }
                        rowNumber++;
                        continue;
                    }
                    if (getValorCelda(rowDato.getCell(inicioColumnasM1)).equals("2")) {
                        controlPredio = new HashSet();
                        instrumentosDigitados.add(getValorCelda(rowDato.getCell(0)));
                        String boleta = getValorCelda(rowDato.getCell(inicioColumnasM1 + 2));
                        String codigoPredio = getValorCelda(rowDato.getCell(inicioColumnasM1 + 9));
                        int version = 1;
                        InstrumentoDig ins = fachadaInstrumentos.consultaInstrumentoPorBoletaCodPredio(boleta, codigoPredio);
                        if (ins == null) {
                            ins = new InstrumentoDig();
                            ins.setVersion(version);
                            ins.setBoletaCensal(fachadaInstrumentos.obtenerMaximaBoletaCensal());
                        } else {
                            ins.setId(null);
                            version = ins.getVersion() + 1;
                            ins.setBoletaCensal(ins.getBoletaCensal());
                        }
                        Instrumento i = new Instrumento();
                        i.setCodigo("RG-T2011");
                        ins.setInstrumento(i);
                        Sede sede = fachada.getSedePorCodigo(getValorCelda(rowDato.getCell(inicioColumnasM1 + 13)));
                        ins.setSede(sede);
                        ins.setEstablecimiento(fachada.getEstablecimientoPorCodigo(getValorCelda(rowDato.getCell(inicioColumnasM1 + 21))));
                        ins.setFechaEncuesta(UtilFecha.obtenerFechaActual());
                        ins.setOrigen(1);
                        Predio predio = fachadaPredio.getPredioPorCodigo(codigoPredio);
                        if (predio == null) {
                            String codigoPredioCalculado = this.calcularCodigoInterno(sede.getEstablecimiento().getNivel().getCodigo());
                            predio = new Predio();
                            predio.setCodigo(codigoPredioCalculado);
                            predio.setCodigoOficial(codigoPredioCalculado);
                            predio.setNombre(getValorCelda(rowDato.getCell(13)));
                            predio.setEstado("A");
                            predio.setFechaCreacion(new Date());
                            predio = fachadaPredio.guardarPredio(predio);
                        }
                        ins.setPredio(predio);
                        ins.setPeriodoCreacion(UtilFecha.obtenerPeriodoActual());
                        ins.setVersion(version);
                        ins.setFechaCreacion(new Date());
                        ins.setUsuarioCreacion(usuarioSesionBean.getUsuario().getUsername());
                        ins.setFechaActualizacion(new Date());
                        ins.setUsuarioActualizacion(usuarioSesionBean.getUsuario().getUsername());

                        Nivel nivEst = sede.getEstablecimiento().getNivel();
                        if (nivEst.getConfiguracion().getNivel() == 5L) {
                            ins.setNivel5(nivEst);
                            Nivel nivel4 = nivEst.getPadre();
                            ins.setNivel4(nivel4);
                            Nivel nivel3 = nivel4.getPadre();
                            ins.setNivel3(nivel3);
                            Nivel nivel2 = nivel3.getPadre();
                            ins.setNivel2(nivel2);
                            Nivel nivel1 = nivel2.getPadre();
                            ins.setNivel1(nivel1);
                            ins.setNivel0(nivel1.getPadre());
                        } else if (nivEst.getConfiguracion().getNivel() == 4L) {
                            ins.setNivel4(nivEst);
                            Nivel nivel3 = nivEst.getPadre();
                            ins.setNivel3(nivel3);
                            Nivel nivel2 = nivel3.getPadre();
                            ins.setNivel2(nivel2);
                            Nivel nivel1 = nivel2.getPadre();
                            ins.setNivel1(nivel1);
                            ins.setNivel0(nivel1.getPadre());
                        } else if (nivEst.getConfiguracion().getNivel() == 3L) {
                            ins.setNivel3(nivEst);
                            Nivel nivel2 = nivEst.getPadre();
                            ins.setNivel2(nivel2);
                            Nivel nivel1 = nivel2.getPadre();
                            ins.setNivel1(nivel1);
                            ins.setNivel0(nivel1.getPadre());
                        } else if (nivEst.getConfiguracion().getNivel() == 2L) {
                            ins.setNivel2(nivEst);
                            Nivel nivel1 = nivEst.getPadre();
                            ins.setNivel1(nivel1);
                            ins.setNivel0(nivel1.getPadre());
                        } else if (nivEst.getConfiguracion().getNivel() == 1L) {
                            ins.setNivel1(nivEst);
                            ins.setNivel0(nivEst.getPadre());
                        } else {
                            ins.setNivel0(nivEst);
                        }
                        ins.setEstado(EstadoInstrumento.N);
                        ins = fachadaInstrumentos.guardarInstrumento(ins);

                        //crear elemento establecimiento para almacenar respuestas digitadas
                        TipoElemento ELE_PRED = new TipoElemento("ELE_PRED");
                        Elemento elemento = new Elemento("PREDIO_1", ins, ELE_PRED);
                        elemento.setEstablecimiento(ins.getEstablecimiento());
                        elemento.setSede(ins.getSede());
                        List<RespuestaDig> respuestasMod1 = new ArrayList<RespuestaDig>();
                        //guardar en dig respuestas falta la respuesta de la boleta. y la del predio cuando esta vacia.
                        for (int col = 5; col <= 35; col++) {
                            String valor = getValorCelda(rowDato.getCell(col));
                            if (UtilCadena.isNullOVacio(valor)) {
                                continue;
                            }
                            Pregunta pregunta = mapPreguntas.get(col);
                            Respuesta respuesta = mapRespuestas.get(col);
                            TipoDatoExcel tde = mapTipologia.get(col);
                            if (tde.equals(TipoDatoExcel.TIPOLOGIA)) {
                                valor = valor.split("-")[0].trim();
                            }
                            if (col == 6) {
                                if (valor != null) {
                                    complementosEstablecimientos.put("PREG_191,RESP_191_01", valor);
                                }
                            }
                            if (col == 7) {
                                if (valor != null) {
                                    complementosEstablecimientos.put("PREG_191,RESP_191_02", valor);
                                }
                            }
                            if (col == 8) {
                                if (valor != null) {
                                    complementosEstablecimientos.put("PREG_192,RESP_192", valor);
                                }
                            }
                            RespuestaDig resDig = new RespuestaDig();
                            resDig.setElemento(elemento);
                            resDig.setPregunta(pregunta);
                            resDig.setRespuesta(respuesta);
                            resDig.setValor(valor);
                            respuestasMod1.add(resDig);
                        }

                        //Complementar Respuestas Digitadas. Complementar DPA

                        if (ins.getNivel0() != null) {
                            respuestasMod1.add(new RespuestaDig(elemento, new Pregunta("PREG_031"), new Respuesta("RESP_031_01"), ins.getNivel0().getId().toString()));
                        }
                        if (ins.getNivel1() != null) {
                            respuestasMod1.add(new RespuestaDig(elemento, new Pregunta("PREG_031"), new Respuesta("RESP_031_02"), ins.getNivel1().getId().toString()));
                        }
                        if (ins.getNivel2() != null) {
                            respuestasMod1.add(new RespuestaDig(elemento, new Pregunta("PREG_031"), new Respuesta("RESP_031_03"), ins.getNivel2().getId().toString()));
                        }
                        if (ins.getNivel3() != null) {
                            respuestasMod1.add(new RespuestaDig(elemento, new Pregunta("PREG_031"), new Respuesta("RESP_031_04"), ins.getNivel3().getId().toString()));
                        }
                        if (ins.getNivel4() != null) {
                            respuestasMod1.add(new RespuestaDig(elemento, new Pregunta("PREG_031"), new Respuesta("RESP_031_05"), ins.getNivel4().getId().toString()));
                        }
                        if (ins.getNivel5() != null) {
                            respuestasMod1.add(new RespuestaDig(elemento, new Pregunta("PREG_031"), new Respuesta("RESP_031_06"), ins.getNivel5().getId().toString()));
                        }

                        respuestasMod1.add(new RespuestaDig(elemento, new Pregunta("PREG_004"), new Respuesta("RESP_004"), ins.getBoletaCensal()));
                        respuestasMod1.add(new RespuestaDig(elemento, new Pregunta("PREG_010"), new Respuesta("RESP_010"), ins.getPredio().getCodigo()));
                        respuestasMod1.add(new RespuestaDig(elemento, new Pregunta("PREG_024"), new Respuesta("RESP_024"), ins.getSede().getNombre()));
                        respuestasMod1.add(new RespuestaDig(elemento, new Pregunta("PREG_014"), new Respuesta("RESP_014"), ins.getEstablecimiento().getNombre()));

                        respuestasMod1.add(new RespuestaDig(elemento, new Pregunta("PREG_001"), new Respuesta("RESP_001"), UtilFecha.obtenerFechaActualString()));
                        respuestasMod1.add(new RespuestaDig(elemento, new Pregunta("PREG_002"), new Respuesta("RESP_002"), UtilFecha.obtenerHoraActualString()));
                        respuestasMod1.add(new RespuestaDig(elemento, new Pregunta("PREG_003"), new Respuesta("RESP_003"), UtilFecha.obtenerHoraActualString()));

                        elemento.setRespuestasList(respuestasMod1);
                        elemento.getRespuestasList().addAll(complementarRespuestaDigitadas(elemento));
                        fachadaInstrumentos.guardarElementoDigitadoArchivo(elemento);

                        //Elemento Establecimiento
                        TipoElemento ele_est = new TipoElemento("ELE_EST");
                        Elemento elementoEst = new Elemento("ESTABLECIMIENTO_1", ins, ele_est);
                        elementoEst.setEstablecimiento(ins.getEstablecimiento());
                        elementoEst.setSede(ins.getSede());
                        elementoEst = fachadaInstrumentos.guardarElementoDigitadoArchivo(elementoEst);
                        mapElementosEst.put(new Long(getValorCelda(rowDato.getCell(0))), elementoEst);

                        //Guardo Modulo2
                        adjuntarElementoPredio(getValorCelda(rowDato.getCell(0)), ins, wb.getSheetAt(2), ELE_PRED, inicioColumnas + 1, 64, elemento);

                        //Guardo Elemento Edificios
                        //TODO: aca debo completar las dos repuestas digitadas del edificio en los modulos C1
                        TipoElemento edificio = new TipoElemento("ELE_EDI");
                        edificio.setDescripcion("EDIFICIO");
                        guardarElemento(getValorCelda(rowDato.getCell(0)), ins, wb.getSheetAt(3), edificio, inicioColumnas + 1, 27);

                        //Guardo Espacios
                        TipoElemento espacio = new TipoElemento("ELE_ESP");
                        espacio.setDescripcion("ESPACIO");
                        guardarElemento(getValorCelda(rowDato.getCell(0)), ins, wb.getSheetAt(4), espacio, inicioColumnas + 1, 74);

                        //Guardo Espacios
                        TipoElemento ambienteSimilar = new TipoElemento("ELE_AMB_SIM");
                        ambienteSimilar.setDescripcion("AMBIENTES SIMILARES");
                        guardarElemento(getValorCelda(rowDato.getCell(0)), ins, wb.getSheetAt(5), ambienteSimilar, inicioColumnas + 1, 35);

                        //Guardo Establecimientos
                        TipoElemento establecimiento = new TipoElemento("ELE_EST");
                        establecimiento.setDescripcion("ESTABLECIMIENTO");
                        guardarElementoEstablecimiento(getValorCelda(rowDato.getCell(0)), ins, wb.getSheetAt(7), establecimiento, inicioColumnas + 2, 16, elementoEst);

                        //Guardo B1
                        adjuntarElementoPredio(getValorCelda(rowDato.getCell(0)), ins, wb.getSheetAt(6), ELE_PRED, inicioColumnas + 1, 12, elemento);

                        //Guardo D1
                        adjuntarElementoPredio(getValorCelda(rowDato.getCell(0)), ins, wb.getSheetAt(9), ELE_PRED, inicioColumnas + 1, 16, elemento);

                    }
                    rowNumber++;
                }

                guardarCobertura();
                guardarCoberturaB1();

                mostrarMensajeInfo(Utilidades.obtenerMensaje("guardar.instrumentos.excel"), Utilidades.obtenerMensaje("guardar.instrumentos.excel.exito"));
            } catch (Exception ex) {
                mostrarMensajeError(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"), ex.getMessage());
                ManejadorErrores.ingresarExcepcionEnLog(ex, ArchivoInstrumentos.class);
            } finally {
                System.gc();
            }

        }
    }

    public void guardarElemento(String item, InstrumentoDig instrumento, Sheet modulo, TipoElemento tipoElemento, int start, int end) {
        //Tipo de dato Seteado con descripcion sino se parte.
        Map<Integer, Pregunta> mapPreguntasElementos = new HashMap<Integer, Pregunta>();
        Map<Integer, Respuesta> mapRespuestasElementos = new HashMap<Integer, Respuesta>();
        Map<Integer, TipoDatoExcel> mapTipologiaElementos = new HashMap<Integer, TipoDatoExcel>();
        int rowNumber = 0;
        int indexElemento = 1;
        String idEdificio = "E";
        while (true) {
            Row rowDato = modulo.getRow(rowNumber);
            if (rowDato == null) {
                break;
            }

            if (rowNumber == 0) {
                for (int col = start; col <= end; col++) {
                    Pregunta pregunta = new Pregunta(getValorCelda(rowDato.getCell(col)));
                    mapPreguntasElementos.put(col, pregunta);
                }
                rowNumber++;
                continue;
            }
            if (rowNumber == 1) {
                for (int col = start; col <= end; col++) {
                    Respuesta respuesta = new Respuesta(getValorCelda(rowDato.getCell(col)));
                    mapRespuestasElementos.put(col, respuesta);
                }
                rowNumber++;
                continue;
            }
            if (rowNumber == 2) {
                for (int col = start; col <= end; col++) {
                    String valor = getValorCelda(rowDato.getCell(col));
                    TipoDatoExcel tde;
                    if (valor.equals("0")) {
                        tde = TipoDatoExcel.CADENA;
                    } else if (valor.equals("1")) {
                        tde = TipoDatoExcel.TIPOLOGIA;
                    } else if (valor.equals("2")) {
                        tde = TipoDatoExcel.SINO;
                    } else if (valor.equals("3")) {
                        tde = TipoDatoExcel.SUFINSUF;
                    } else if (valor.equals("4")){
                        tde = TipoDatoExcel.FECHA;
                    }else{
                        tde = TipoDatoExcel.DECIMAL;
                    }
                    mapTipologiaElementos.put(col, tde);
                }
                rowNumber++;
                continue;
            }

            if (getValorCelda(rowDato.getCell(0)).equals(item)) {
                controlEdificio = new HashSet<String>();
                controlEspacio = new HashSet<String>();
                String nombreElemento = tipoElemento.getDescripcion() + "_" + indexElemento;
                idEdificio = getValorCelda(rowDato.getCell(inicioColumnas + 1));
                Elemento elemento = new Elemento(nombreElemento, instrumento, tipoElemento);
                elemento.setEstablecimiento(instrumento.getEstablecimiento());
                elemento.setSede(instrumento.getSede());
                List<RespuestaDig> respuestasElemento = new ArrayList<RespuestaDig>();
                //guardar en dig respuestas
                for (int col = start; col <= end; col++) {
                    String valor = getValorCelda(rowDato.getCell(col));
                    if (UtilCadena.isNullOVacio(valor)) {
                        continue;
                    }
                    Pregunta pregunta = mapPreguntasElementos.get(col);
                    Respuesta respuesta = mapRespuestasElementos.get(col);
                    TipoDatoExcel tde = mapTipologiaElementos.get(col);
                    if (!tde.equals(TipoDatoExcel.CADENA)) {
                        if (tde.equals(TipoDatoExcel.TIPOLOGIA)) {
                            valor = valor.split("-")[0].trim();
                        } else if (tde.equals(TipoDatoExcel.SINO)) {
                            valor = valor.equalsIgnoreCase("Si") ? "1" : "0";
                        } else if (tde.equals(TipoDatoExcel.SUFINSUF)) {
                            valor = valor.equalsIgnoreCase("Suficiente") ? "1" : "0";
                        } else if(tde.equals(TipoDatoExcel.DECIMAL)){
                            valor = String.format( "%.2f", Float.parseFloat(valor)).replaceAll(",",".");
                        }
                    }
                    RespuestaDig resDig = new RespuestaDig();
                    resDig.setElemento(elemento);
                    resDig.setPregunta(pregunta);
                    resDig.setRespuesta(respuesta);
                    resDig.setValor(valor);
                    respuestasElemento.add(resDig);
                    if (tipoElemento.getCodigo().equals("ELE_EDI")) {
                        if (complementoEdificio.containsKey(respuesta.getCodigo())) {
                            if (!controlEdificio.contains(complementoEdificio.get(respuesta.getCodigo()).getRespuestaValidar().getCodPreguntas().getCodigo())) {
                                Respuesta respTmp = complementoEdificio.get(respuesta.getCodigo());
                                RespuestaDig rdTmp = new RespuestaDig();
                                rdTmp.setElemento(elemento);
                                rdTmp.setPregunta(respTmp.getRespuestaValidar().getCodPreguntas());
                                rdTmp.setRespuesta(respTmp.getRespuestaValidar());
                                rdTmp.setValor(respTmp.getRespuestaValidar().getTipoRespuesta().getCodigo().equals("TIP_RESP_011") ? "0" : "1");
                                respuestasElemento.add(rdTmp);
                                controlEdificio.add(respTmp.getRespuestaValidar().getCodPreguntas().getCodigo());
                            }
                        }
                    }
                    if (tipoElemento.getCodigo().equals("ELE_ESP")) {
                        if (complementoEspacio.containsKey(respuesta.getCodigo())) {
                            if (!controlEspacio.contains(complementoEspacio.get(respuesta.getCodigo()).getRespuestaValidar().getCodPreguntas().getCodigo())) {
                                Respuesta respTmp = complementoEspacio.get(respuesta.getCodigo());
                                RespuestaDig rdTmp = new RespuestaDig();
                                rdTmp.setElemento(elemento);
                                rdTmp.setPregunta(respTmp.getRespuestaValidar().getCodPreguntas());
                                rdTmp.setRespuesta(respTmp.getRespuestaValidar());
                                rdTmp.setValor(respTmp.getRespuestaValidar().getTipoRespuesta().getCodigo().equals("TIP_RESP_011") ? "0" : "1");
                                respuestasElemento.add(rdTmp);
                                controlEspacio.add(respTmp.getRespuestaValidar().getCodPreguntas().getCodigo());
                            }
                        }
                    }
                }
                if (tipoElemento.getCodigo().equals("ELE_EDI")) {
                    RespuestaDig resDigM1 = new RespuestaDig();
                    RespuestaDig resDigM2 = new RespuestaDig();
                    resDigM1.setElemento(elemento);
                    resDigM1.setValor(idEdificio);
                    Pregunta pregunta = new Pregunta("PREG_241");
                    Respuesta respuesta = new Respuesta("RESP_241");
                    resDigM1.setPregunta(pregunta);
                    resDigM1.setRespuesta(respuesta);

                    resDigM2.setElemento(elemento);
                    resDigM2.setValor(idEdificio);
                    Pregunta preguntaM2 = new Pregunta("PREG_248");
                    Respuesta respuestaM2 = new Respuesta("RESP_248");
                    resDigM2.setPregunta(preguntaM2);
                    resDigM2.setRespuesta(respuestaM2);

                    respuestasElemento.add(resDigM1);
                    respuestasElemento.add(resDigM2);
                }

                elemento.setRespuestasList(respuestasElemento);
                elemento.getRespuestasList().addAll(complementarRespuestaDigitadas(elemento));
                fachadaInstrumentos.guardarElementoDigitadoArchivo(elemento);
                indexElemento++;
            }
            rowNumber++;
        }
    }

    public void guardarElementoEstablecimiento(String item, InstrumentoDig instrumento, Sheet modulo, TipoElemento tipoElemento, int start, int end, Elemento establecimiento) {
        //Tipo de dato Seteado con descripcion sino se parte.
        Map<Integer, Pregunta> mapPreguntasElementos = new HashMap<Integer, Pregunta>();
        Map<Integer, Respuesta> mapRespuestasElementos = new HashMap<Integer, Respuesta>();
        Map<Integer, TipoDatoExcel> mapTipologiaElementos = new HashMap<Integer, TipoDatoExcel>();
        int rowNumber = 0;
        int indexElemento = 1;
        while (true) {
            Row rowDato = modulo.getRow(rowNumber);
            if (rowDato == null) {
                break;
            }

            if (rowNumber == 0) {
                for (int col = start; col <= end; col++) {
                    Pregunta pregunta = new Pregunta(getValorCelda(rowDato.getCell(col)));
                    mapPreguntasElementos.put(col, pregunta);
                }
                rowNumber++;
                continue;
            }
            if (rowNumber == 1) {
                for (int col = start; col <= end; col++) {
                    Respuesta respuesta = new Respuesta(getValorCelda(rowDato.getCell(col)));
                    mapRespuestasElementos.put(col, respuesta);
                }
                rowNumber++;
                continue;
            }
            if (rowNumber == 2) {
                for (int col = start; col <= end; col++) {
                    String valor = getValorCelda(rowDato.getCell(col));
                    TipoDatoExcel tde;
                    if (valor.equals("1")) {
                        tde = TipoDatoExcel.TIPOLOGIA;
                    } else {
                        tde = TipoDatoExcel.CADENA;
                    }
                    mapTipologiaElementos.put(col, tde);
                }
                rowNumber++;
                continue;
            }

            if (getValorCelda(rowDato.getCell(0)).equals(item)) {
                String nombreElemento = tipoElemento.getDescripcion() + "_" + indexElemento;
                Elemento elemento = new Elemento(nombreElemento, instrumento, tipoElemento);
                elemento.setEstablecimiento(instrumento.getEstablecimiento());
                elemento.setSede(instrumento.getSede());
                List<RespuestaDig> respuestasElemento = new ArrayList<RespuestaDig>();
                //guardar en dig respuestas
                for (int col = start; col <= end; col++) {
                    String valor = getValorCelda(rowDato.getCell(col));
                    if (UtilCadena.isNullOVacio(valor)) {
                        continue;
                    }
                    if (col == 4 && valor != null) {
                        Sede sede = fEstablecimiento.getSedePorCodigo(valor);
                        if (sede != null) {
                            respuestasElemento.add(new RespuestaDig(elemento, new Pregunta("PREG_194"), new Respuesta("RESP_194"), sede.getNombre()));
                        }
                    }
                    if (col == 9 && valor != null) {
                        Establecimiento estTmp = fEstablecimiento.getEstablecimientoPorCodigo(valor);
                        if (estTmp != null) {
                            respuestasElemento.add(new RespuestaDig(elemento, new Pregunta("PREG_293"), new Respuesta("RESP_293"), estTmp.getNombre()));
                        }
                    }
                    Pregunta pregunta = mapPreguntasElementos.get(col);
                    Respuesta respuesta = mapRespuestasElementos.get(col);
                    TipoDatoExcel tde = mapTipologiaElementos.get(col);
                    if (tde.equals(TipoDatoExcel.TIPOLOGIA)) {
                        valor = valor.split("-")[0].trim();
                    }
                    RespuestaDig resDig = new RespuestaDig();
                    resDig.setElemento(elemento);
                    resDig.setPregunta(pregunta);
                    resDig.setRespuesta(respuesta);
                    resDig.setValor(valor);
                    respuestasElemento.add(resDig);
                }
                elemento.setRespuestasList(respuestasElemento);
                for (Map.Entry<String, String> entry : complementosEstablecimientos.entrySet()) {
                    elemento.getRespuestasList().add(new RespuestaDig(elemento, new Pregunta(entry.getKey().split(",")[0]), new Respuesta(entry.getKey().split(",")[1]), entry.getValue()));
                }
                elemento = fachadaInstrumentos.guardarElementoDigitadoArchivoVerificado(elemento);
                String key = getValorCelda(rowDato.getCell(0))
                        .concat("_").concat(getValorCelda(rowDato.getCell(4)));
                mapElementosEstB1.put(key, elemento);
                indexElemento++;
            }
            rowNumber++;
        }
    }

    public void adjuntarElementoPredio(String item, InstrumentoDig instrumento, Sheet modulo, TipoElemento tipoElemento, int start, int end, Elemento elementoPredio) {
        //Tipo de dato Seteado con descripcion sino se parte.
        Map<Integer, Pregunta> mapPreguntasElementos = new HashMap<Integer, Pregunta>();
        Map<Integer, Respuesta> mapRespuestasElementos = new HashMap<Integer, Respuesta>();
        Map<Integer, TipoDatoExcel> mapTipologiaElementos = new HashMap<Integer, TipoDatoExcel>();
        int rowNumber = 0;
        while (true) {
            Row rowDato = modulo.getRow(rowNumber);
            if (rowDato == null) {
                break;
            }

            if (rowNumber == 0) {
                for (int col = start; col <= end; col++) {
                    Pregunta pregunta = new Pregunta(getValorCelda(rowDato.getCell(col)));
                    mapPreguntasElementos.put(col, pregunta);
                }
                rowNumber++;
                continue;
            }
            if (rowNumber == 1) {
                for (int col = start; col <= end; col++) {
                    Respuesta respuesta = new Respuesta(getValorCelda(rowDato.getCell(col)));
                    mapRespuestasElementos.put(col, respuesta);
                }
                rowNumber++;
                continue;
            }
            if (rowNumber == 2) {
                for (int col = start; col <= end; col++) {
                    String valor = getValorCelda(rowDato.getCell(col));
                    TipoDatoExcel tde;
                    if (valor.equals("0")) {
                        tde = TipoDatoExcel.CADENA;
                    } else if (valor.equals("1")) {
                        tde = TipoDatoExcel.TIPOLOGIA;
                    } else if (valor.equals("2")) {
                        tde = TipoDatoExcel.SINO;
                    } else if (valor.equals("3")) {
                        tde = TipoDatoExcel.SUFINSUF;
                     } else if (valor.equals("4")){
                        tde = TipoDatoExcel.FECHA;
                    }else{
                        tde = TipoDatoExcel.DECIMAL;
                    }
                    mapTipologiaElementos.put(col, tde);
                }
                rowNumber++;
                continue;
            }
            if (getValorCelda(rowDato.getCell(0)).equals(item)) {
                for (int col = start; col <= end; col++) {
                    String valor = getValorCelda(rowDato.getCell(col));
                    if (UtilCadena.isNullOVacio(valor)) {
                        continue;
                    }
                    Pregunta pregunta = mapPreguntasElementos.get(col);
                    Respuesta respuesta = mapRespuestasElementos.get(col);
                    TipoDatoExcel tde = mapTipologiaElementos.get(col);
                    if (!tde.equals(TipoDatoExcel.CADENA)) {
                        if (tde.equals(TipoDatoExcel.TIPOLOGIA)) {
                            valor = valor.split("-")[0].trim();
                        } else if (tde.equals(TipoDatoExcel.SINO)) {
                            valor = valor.equalsIgnoreCase("Si") ? "1" : "0";
                        } else if (tde.equals(TipoDatoExcel.SUFINSUF)) {
                            valor = valor.equalsIgnoreCase("Suficiente") ? "1" : "0";
                        } else if (tde.equals(TipoDatoExcel.FECHA)) {
                            Date date = DateUtil.getJavaDate(Double.parseDouble(valor));
                            valor = UtilFecha.formatearExcel(date);
                        } else if(tde.equals(TipoDatoExcel.DECIMAL)){
                            valor = String.format( "%.2f", Float.parseFloat(valor)).replaceAll(",",".");
                        }
                    }
                    RespuestaDig resDig = new RespuestaDig();
                    resDig.setElemento(elementoPredio);
                    resDig.setPregunta(pregunta);
                    resDig.setRespuesta(respuesta);
                    resDig.setValor(valor);
                    fachadaInstrumentos.guardarRespuestaDigitado(resDig);
                    //Complementa las respuesta SI/NO
                    if (complementoPredio.containsKey(respuesta.getCodigo())) {
                        if (!controlPredio.contains(complementoPredio.get(respuesta.getCodigo()).getRespuestaValidar().getCodPreguntas().getCodigo())) {
                            Respuesta respTmp = complementoPredio.get(respuesta.getCodigo());
                            RespuestaDig rdTmp = new RespuestaDig();
                            rdTmp.setElemento(elementoPredio);
                            rdTmp.setPregunta(respTmp.getRespuestaValidar().getCodPreguntas());
                            rdTmp.setRespuesta(respTmp.getRespuestaValidar());
                            rdTmp.setValor(respTmp.getRespuestaValidar().getTipoRespuesta().getCodigo().equals("TIP_RESP_011") ? "0" : "1");
                            fachadaInstrumentos.guardarRespuestaDigitado(rdTmp);
                            controlPredio.add(respTmp.getRespuestaValidar().getCodPreguntas().getCodigo());
                        }
                    }
                }
            }
            rowNumber++;
        }
    }

    public void cargaMasiva(FileUploadEvent event) throws IOException {
        resultados = new ArrayList<ResultadoCoordenada>();
        erroresColumnaModulo1 = new HashMap<Integer, String>();
        erroresColumnaCoberturaM1 = new HashMap<Integer, String>();
        erroresColumnaModulo2 = new HashMap<Integer, String>();
        erroresColumnaModulo3 = new HashMap<Integer, String>();
        erroresColumnaModulo4 = new HashMap<Integer, String>();
        erroresColumnaModuloA1 = new HashMap<Integer, String>();
        erroresColumnaModuloEstaB1 = new HashMap<Integer, String>();
        erroresColumnaCoberturaB1 = new HashMap<Integer, String>();
        erroresColumnaModuloB1 = new HashMap<Integer, String>();
        erroresColumnaModuloCondicionC1 = new HashMap<Integer, String>();
        erroresColumnaModuloD1 = new HashMap<Integer, String>();

        itemIndice = new HashMap<String, Integer>();
        itemIndiceControl = new HashMap<String, Integer>();
        itemIndiceCoberturaM1 = new HashMap<Integer, Cell>();
        itemIndiceModulo2 = new HashMap<Integer, Cell>();
        itemIndiceModulo3 = new HashMap<Integer, Cell>();
        itemIndiceModulo4 = new HashMap<Integer, Cell>();
        itemIndiceModuloA1 = new HashMap<Integer, Cell>();
        itemIndiceCoberturaB1 = new HashMap<Integer, Cell>();
        itemIndiceModuloEstaB1 = new HashMap<Integer, Cell>();
        itemIndiceModuloB1 = new HashMap<Integer, Cell>();
        itemIndiceModuloCondicionC1 = new HashMap<Integer, Cell>();
        itemIndiceModuloD1 = new HashMap<Integer, Cell>();
        edificiosModulo3 = new HashMap<String, HashSet<String>>();

        idInstrumentos = new HashSet<String>();
        sedesValidas = new HashSet<String>();
        mostrarMsgGuardar = false;
        numeroInstrumentos = 0;
        Row rowDato, row;
        int rowNumberIds = comienzoFilas;
        int contadorFilas = 0;
        Cell cell;
        hayRegErroneos = false;
        archivoValido = true;
        file = event.getFile();
        try {
            wb = new XSSFWorkbook(file.getInputstream());

            //Modulo 1
            Sheet modulo1 = wb.getSheetAt(0);
            Sheet coberturaM1 = wb.getSheetAt(1);
            Sheet modulo2 = wb.getSheetAt(2);
            Sheet modulo3 = wb.getSheetAt(3);
            Sheet modulo4 = wb.getSheetAt(4);
            Sheet moduloA1 = wb.getSheetAt(5);
            Sheet moduloB1 = wb.getSheetAt(6);
            Sheet moduloEstB1 = wb.getSheetAt(7);
            Sheet coberturaB1 = wb.getSheetAt(8);
            Sheet moduloD1 = wb.getSheetAt(9);

            if (wb.getNumberOfSheets() < numeroHojas) {
                archivoValido = false;
                throw new ErrorValidacion(Utilidades.obtenerMensaje("adm.cargue.instrumentos.nohojas"));
            }

            Iterator<Row> rowIterator = modulo1.iterator();
            while (rowIterator.hasNext()) {
                row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    cell = cellIterator.next();
                    cell.removeCellComment();
                }
            }

            //Saco los ids de instrumentos.
            while (true) {
                rowDato = modulo1.getRow(rowNumberIds);
                if (rowDato == null) {
                    break;
                }
                if (getValorCelda(rowDato.getCell(inicioColumnasM1)).equals("1") || getValorCelda(rowDato.getCell(inicioColumnasM1)).equals("2")) {
                    if (!itemIndice.containsKey(getValorCelda(rowDato.getCell(0)))) {
                        itemIndice.put(getValorCelda(rowDato.getCell(0)), rowDato.getCell(inicioColumnasM1).getRowIndex());
                    } else {
                        registrarError(modulo1, rowDato.getCell(0), ErroresInstrumentos.DATO_REPETIDO, erroresColumnaModulo1);
                    }
                    if (getValorCelda(rowDato.getCell(inicioColumnasM1)).equals("2")) {
                        mostrarMsgGuardar = true;
                    }
                }
                if (!UtilCadena.isNullOVacio(getValorCelda(rowDato.getCell(4)))) {
                    rowDato.getCell(4).setCellValue("");
                }
                if (getValorCelda(rowDato.getCell(inicioColumnasM1)).equals("1")
                        || getValorCelda(rowDato.getCell(inicioColumnasM1)).equals("2")
                        || getValorCelda(rowDato.getCell(inicioColumnasM1)).equals("3")) {
                    itemIndiceControl.put(getValorCelda(rowDato.getCell(0)), rowDato.getCell(inicioColumnasM1).getRowIndex());
                }
                rowNumberIds++;
            }

            if (itemIndice.isEmpty()) {
                archivoValido = false;
                throw new ErrorValidacion(Utilidades.obtenerMensaje("adm.cargue.instrumentos.info002"));
            } else {
                if (itemIndice.size() > maximoInstrumentos) {
                    archivoValido = false;
                    throw new ErrorValidacion(Utilidades.obtenerMensaje("adm.cargue.instrumentos.info003"));
                }
            }

            //Validacion del codigo de Encuestador 1 y Encuestador 2

            for (Map.Entry<String, Integer> indice : itemIndice.entrySet()) {
                Row rowTmp = modulo1.getRow(indice.getValue());
                Cell cellEncuestrador = rowTmp.getCell(inicioColumnasM1 + 3);
                Cell cellEncuestrador2 = rowTmp.getCell(inicioColumnasM1 + 4);
                Cell cellSupervisor = rowTmp.getCell(inicioColumnasM1 + 5);
                Cell cellCodSector = rowTmp.getCell(inicioColumnasM1 + 11);//Sector
                Cell cellBoleta = rowTmp.getCell(inicioColumnasM1 + 2);
                Cell cellCodigoSede = rowTmp.getCell(inicioColumnasM1 + 13);//Corregir
                Cell cellPredio = rowTmp.getCell(inicioColumnasM1 + 9);//Corregir.
                Cell cellCodigoEstablecimiento = rowTmp.getCell(inicioColumnasM1 + 21);//Corregir

                if (UtilCadena.isNullOVacio(cellEncuestrador)) {
                    if (UtilCadena.isNullOVacio(cellEncuestrador2)) {
                        registrarError(modulo1, cellEncuestrador, ErroresInstrumentos.DATO_VACIO, erroresColumnaModulo1);
                    }
                } else {
                    if (fachadaInstrumentos.obtenerEncuestadorPorCodigo(getValorCelda(cellEncuestrador)) == null) {
                        registrarError(modulo1, cellEncuestrador, ErroresInstrumentos.DATO_INEXISTENTE, erroresColumnaModulo1);
                    }
                }

                if (!UtilCadena.isNullOVacio(cellEncuestrador2)) {
                    if (fachadaInstrumentos.obtenerEncuestadorPorCodigo(getValorCelda(cellEncuestrador2)) == null) {
                        registrarError(modulo1, cellEncuestrador2, ErroresInstrumentos.DATO_INEXISTENTE, erroresColumnaModulo1);
                    }
                }

                if (UtilCadena.isNullOVacio(cellSupervisor)) {
                    registrarError(modulo1, cellSupervisor, ErroresInstrumentos.DATO_VACIO, erroresColumnaModulo1);
                } else {
                    if (fachadaInstrumentos.obtenerSupervisorPorCodigo(getValorCelda(cellSupervisor)) == null) {
                        registrarError(modulo1, cellSupervisor, ErroresInstrumentos.DATO_INEXISTENTE, erroresColumnaModulo1);
                    }
                }

                if (UtilCadena.isNullOVacio(cellCodigoSede)) {
                    registrarError(modulo1, cellCodigoSede, ErroresInstrumentos.DATO_VACIO, erroresColumnaModulo1);
                } else {
                    if (!fEstablecimiento.existeSede(getValorCelda(cellCodigoSede))) {
                        registrarError(modulo1, cellCodigoSede, ErroresInstrumentos.DATO_INEXISTENTE, erroresColumnaModulo1);
                    } else {
                        if (!fEstablecimiento.existeSedeEstablecimiento(getValorCelda(cellCodigoSede), getValorCelda(cellCodigoEstablecimiento))) {
                            registrarError(modulo1, cellCodigoSede, ErroresInstrumentos.DATO_NO_RELACIONADO, erroresColumnaModulo1);
                        }
                    }
                }

                if (UtilCadena.isNullOVacio(cellCodigoEstablecimiento)) {
                    registrarError(modulo1, cellCodigoEstablecimiento, ErroresInstrumentos.DATO_VACIO, erroresColumnaModulo1);
                } else {
                    if (!fEstablecimiento.existeEstablecimiento(getValorCelda(cellCodigoEstablecimiento))) {
                        registrarError(modulo1, cellCodigoEstablecimiento, ErroresInstrumentos.DATO_INEXISTENTE, erroresColumnaModulo1);
                    }
                }

                if (UtilCadena.isNullOVacio(cellCodSector)) {
                    registrarError(modulo1, cellCodSector, ErroresInstrumentos.DATO_VACIO, erroresColumnaModulo1);
                }

                //Validacion de la boleta y predio.
                // 1 No me interesa validadar si boleta  y cod predio estan vacios.
                if (!UtilCadena.isNullOVacio(cellBoleta) && UtilCadena.isNullOVacio(cellPredio)) {
                    registrarError(modulo1, cellPredio, ErroresInstrumentos.DATO_VACIO, erroresColumnaModulo1);
                }

                if (UtilCadena.isNullOVacio(cellBoleta) && !UtilCadena.isNullOVacio(cellPredio)) {
                    //Valido que exista el predio
                    /*if (!fEstablecimiento.existePredioCodOf(getValorCelda(cellPredio))) {
                        registrarError(modulo1, cellPredio, ErroresInstrumentos.DATO_INEXISTENTE, erroresColumnaModulo1);
                    }*/
                    registrarError(modulo1, cellBoleta, ErroresInstrumentos.DATO_VACIO, erroresColumnaModulo1);
                }

                if (!UtilCadena.isNullOVacio(cellBoleta) && !UtilCadena.isNullOVacio(cellPredio)) {
                    //Valido Relacion con Predio.
                    if (fachadaInstrumentos.consultaInstrumentoPorBoletaCodPredio(getValorCelda(cellBoleta), getValorCelda(cellPredio)) == null) {
                        registrarError(modulo1, cellPredio, ErroresInstrumentos.DATO_INEXISTENTE, erroresColumnaModulo1);
                        registrarError(modulo1, cellBoleta, ErroresInstrumentos.DATO_INEXISTENTE, erroresColumnaModulo1);
                    }
                }

            }

            if (erroresColumnaModulo1.size() > 0) {
                for (Map.Entry<Integer, String> errores : erroresColumnaModulo1.entrySet()) {
                    Row rowError = modulo1.getRow(errores.getKey());
                    Cell cellError = rowError.getCell(inicioColumnasM1 + 1);
                    cellError.setCellValue(errores.getValue());
                }
            }

            validarIdsRelacionados(comienzoFilas, coberturaM1, itemIndiceCoberturaM1, erroresColumnaCoberturaM1, false);
            validarIdsRelacionados(comienzoFilas, modulo2, itemIndiceModulo2, erroresColumnaModulo2, true);
            validarIdsRelacionados(comienzoFilas, modulo3, itemIndiceModulo3, erroresColumnaModulo3, false);
            validarIdsRelacionados(comienzoFilas, modulo4, itemIndiceModulo4, erroresColumnaModulo4, false);
            validarIdsRelacionados(comienzoFilas, moduloA1, itemIndiceModuloA1, erroresColumnaModuloA1, false);
            validarIdsRelacionados(comienzoFilas, moduloEstB1, itemIndiceModuloEstaB1, erroresColumnaModuloEstaB1, false);
            validarIdsRelacionados(comienzoFilas, coberturaB1, itemIndiceCoberturaB1, erroresColumnaCoberturaB1, false);
            validarIdsRelacionados(comienzoFilas, moduloB1, itemIndiceModuloB1, erroresColumnaModuloB1, true);
            validarIdsRelacionados(comienzoFilas, moduloD1, itemIndiceModuloD1, erroresColumnaModuloD1, true);

            //Validacion Edificios
            rowNumberIds = comienzoFilas;
            while (true) {
                rowDato = modulo3.getRow(rowNumberIds);
                if (rowDato == null) {
                    break;
                }
                Cell cellId = rowDato.getCell(0);
                Cell cellEdificio = rowDato.getCell(inicioColumnas + 1);

                if (!UtilCadena.isNullOVacio(cellId)) {
                    if (UtilCadena.isNullOVacio(cellEdificio)) {
                        registrarError(modulo3, cellEdificio, ErroresInstrumentos.DATO_VACIO, erroresColumnaModulo3);
                    }
                }

                if (itemIndice.containsKey(getValorCelda(cellId))) {
                    if (!identificadorEdificioValido(getValorCelda(cellEdificio))) {
                        registrarError(modulo3, cellEdificio, ErroresInstrumentos.FORMATO_INVALIDO, erroresColumnaModulo3);
                    } else {
                        if (!edificiosModulo3.containsKey(getValorCelda(cellId))) {
                            HashSet tempHash = new HashSet();
                            tempHash.add(getValorCelda(cellEdificio));
                            edificiosModulo3.put(getValorCelda(cellId), tempHash);
                        } else {
                            if (!edificiosModulo3.get(getValorCelda(cellId)).contains(getValorCelda(cellEdificio))) {
                                edificiosModulo3.get(getValorCelda(cellId)).add(getValorCelda(cellEdificio));
                            } else {
                                registrarError(modulo3, cellEdificio, ErroresInstrumentos.DATO_REPETIDO, erroresColumnaModulo3);
                            }
                        }
                    }

                }
                rowNumberIds++;
            }

            if (erroresColumnaModulo3.size() > 0) {
                for (Map.Entry<Integer, String> errores : erroresColumnaModulo3.entrySet()) {
                    Row rowError = modulo3.getRow(errores.getKey());
                    Cell cellError = rowError.getCell(inicioColumnas);
                    cellError.setCellValue(errores.getValue());
                }
            }

            //Valicacion Espacios
            rowNumberIds = comienzoFilas;
            while (true) {
                rowDato = modulo4.getRow(rowNumberIds);
                if (rowDato == null) {
                    break;
                }
                Cell cellId = rowDato.getCell(0);
                Cell cellEdificio = rowDato.getCell(inicioColumnas + 2);
                Cell cellEspacio = rowDato.getCell(inicioColumnas + 1);

                if (!UtilCadena.isNullOVacio(cellId)) {
                    if (UtilCadena.isNullOVacio(cellEdificio)) {
                        registrarError(modulo4, cellEdificio, ErroresInstrumentos.DATO_VACIO, erroresColumnaModulo4);
                    }
                    if (UtilCadena.isNullOVacio(cellEspacio)) {
                        registrarError(modulo4, cellEspacio, ErroresInstrumentos.DATO_VACIO, erroresColumnaModulo4);
                    }
                }

                if (itemIndice.containsKey(getValorCelda(cellId))) {
                    HashSet edificiosValidos = edificiosModulo3.get(getValorCelda(cellId));
                    if(edificiosValidos != null){
                        if (!edificiosValidos.contains(getValorCelda(cellEdificio))) {
                            if (!identificadorEdificioEspacioValido(getValorCelda(cellEdificio))) {
                                registrarError(modulo4, cellEdificio, ErroresInstrumentos.DATO_INEXISTENTE, erroresColumnaModulo4);
                            }
                        }
                    }
                }
                rowNumberIds++;
            }


            if (erroresColumnaModulo4.size() > 0) {
                for (Map.Entry<Integer, String> errores : erroresColumnaModulo4.entrySet()) {
                    Row rowError = modulo4.getRow(errores.getKey());
                    Cell cellError = rowError.getCell(inicioColumnas);
                    cellError.setCellValue(errores.getValue());
                }
            }

            //Validacion de ESTABLECIMIENTOS

            rowNumberIds = comienzoFilas;
            while (true) {
                rowDato = moduloEstB1.getRow(rowNumberIds);
                if (rowDato == null) {
                    break;
                }
                Cell cellId = rowDato.getCell(0);
                Cell cellSede = rowDato.getCell(inicioColumnas + 2);

                if (!UtilCadena.isNullOVacio(cellId)) {
                    if (UtilCadena.isNullOVacio(cellSede)) {
                        registrarError(moduloEstB1, cellSede, ErroresInstrumentos.DATO_VACIO, erroresColumnaModuloEstaB1);
                    }

                }
                if (itemIndice.containsKey(getValorCelda(cellId))) {
                    if (sedesValidas.contains(getValorCelda(cellSede))) {
                        registrarError(moduloEstB1, cellSede, ErroresInstrumentos.DATO_REPETIDO, erroresColumnaModuloEstaB1);
                    } else {
                        sedesValidas.add(getValorCelda(cellSede));
                    }
                }
                rowNumberIds++;
            }

            if (erroresColumnaModuloEstaB1.size() > 0) {
                for (Map.Entry<Integer, String> errores : erroresColumnaModuloEstaB1.entrySet()) {
                    Row rowError = moduloEstB1.getRow(errores.getKey());
                    Cell cellError = rowError.getCell(inicioColumnas);
                    cellError.setCellValue(errores.getValue());
                }
            }

            //Validacion Cobertura Establecimientos.
            //Actualizar los indices de los arrays

            rowNumberIds = comienzoFilas;
            while (true) {
                rowDato = coberturaB1.getRow(rowNumberIds);
                if (rowDato == null) {
                    break;
                }
                Cell cellId = rowDato.getCell(0);
                Cell cellSede = rowDato.getCell(inicioColumnas + 1);

                if (!UtilCadena.isNullOVacio(cellId)) {
                    if (UtilCadena.isNullOVacio(cellSede)) {
                        registrarError(coberturaB1, cellSede, ErroresInstrumentos.DATO_VACIO, erroresColumnaCoberturaB1);
                    }
                }
                if (!UtilCadena.isNullOVacio(cellSede)) {
                    if (!sedesValidas.contains(getValorCelda(cellSede))) {
                        registrarError(coberturaB1, cellSede, ErroresInstrumentos.DATO_NO_RELACIONADO, erroresColumnaCoberturaB1);
                    }
                }

                rowNumberIds++;
            }

            if (erroresColumnaCoberturaB1.size() > 0) {
                for (Map.Entry<Integer, String> errores : erroresColumnaCoberturaB1.entrySet()) {
                    Row rowError = coberturaB1.getRow(errores.getKey());
                    Cell cellError = rowError.getCell(2);
                    cellError.setCellValue(errores.getValue());
                }
            }

            //Revisar como inserto la informacion en la columna de Errores.
            //Inserto Informacion el Columna 3
            if (hayRegErroneos) {
                fileTemp = File.createTempFile("Resultado", "xlsm");
                out = new FileOutputStream(fileTemp);
                wb.write(out);
                out.close(); //Revisar si hay q cerrar este archivo en otro lado
            }

            //hayRegErroneos = true;
        } catch (ErrorValidacion ev) {
            mostrarMensajeError(Utilidades.obtenerMensaje("adm.cargue.instrumentos.archivoIncorrecto"), ev.getMessage());
        } catch (Exception ex) {
            mostrarMensajeError(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"), ex.getMessage());
            ManejadorErrores.ingresarExcepcionEnLog(ex, ArchivoInstrumentos.class);
            resultados.clear();
        } finally {
            System.gc();
        }
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
    
     private boolean identificadorEdificioEspacioValido(String valor) {
        //Se quito la validacion para espacios exteriores 19/07/2021 solo se pueden digitar E.
        valor = valor.toUpperCase();
        boolean sw = false;
        Pattern pattern = Pattern.compile("^EX[0-9]+$");
        Matcher matcher;
        if (!UtilCadena.isNullOVacio(valor)) {
            matcher = pattern.matcher(valor.toString());
            sw = matcher.matches();
        }
        return sw;
    }

    public void validarIdsRelacionados(Integer rowNumberIds, Sheet hojaExcel, HashMap<Integer, Cell> celdasMap, HashMap<Integer, String> erroresMap, Boolean validarRepetidos) {
        Row rowDato;
        HashSet<String> idExcel = new HashSet<String>();
        while (true) {
            rowDato = hojaExcel.getRow(rowNumberIds);
            if (rowDato == null) {
                break;
            }
            if (!getValorCelda(rowDato.getCell(0)).isEmpty()) {
                celdasMap.put(rowDato.getCell(0).getRowIndex(), rowDato.getCell(0));
            }
            if (validarRepetidos) {
                if (!getValorCelda(rowDato.getCell(0)).isEmpty()) {
                    String id = getValorCelda(rowDato.getCell(0));
                    if (idExcel.contains(id)) {
                        registrarError(hojaExcel, rowDato.getCell(0), ErroresInstrumentos.DATO_REPETIDO, erroresMap);
                    } else {
                        idExcel.add(id);
                    }
                }

            }
            rowNumberIds++;
        }

        //Valido el nuevo Array con el de los Id Validos.            
        for (Map.Entry<Integer, Cell> idsCobertura : celdasMap.entrySet()) {
            if (!itemIndiceControl.containsKey(getValorCelda(idsCobertura.getValue()))) {
                registrarError(hojaExcel, idsCobertura.getValue(), ErroresInstrumentos.BOLETA_NO_RELACIONADO, erroresMap);
            }

        }

        if (erroresMap.size() > 0) {
            for (Map.Entry<Integer, String> errores : erroresMap.entrySet()) {
                Row rowError = hojaExcel.getRow(errores.getKey());
                Cell cellError = rowError.getCell(inicioColumnas);
                cellError.setCellValue(errores.getValue());
            }
        }

    }

    public void addComment(Sheet sheet, Cell cell, String commentText) {
        CreationHelper factory = wb.getCreationHelper();
        ClientAnchor anchor = factory.createClientAnchor();
        Drawing drawing = sheet.createDrawingPatriarch();
        Comment comment = drawing.createCellComment(anchor);
        comment.setString(factory.createRichTextString(commentText));
        cell.setCellComment(comment);
    }

    public void registrarError(Sheet modulo1, Cell cellTemp, ErroresInstrumentos error, HashMap<Integer, String> erroresMapa) {
        addComment(modulo1, cellTemp, Utilidades.obtenerMensaje(error.getErrorEtiqueta()));
        if (!erroresMapa.containsKey(cellTemp.getRowIndex())) {
            erroresMapa.put(cellTemp.getRowIndex(), Utilidades.obtenerMensaje(error.getTipo()));
        }
        hayRegErroneos = true;
    }

    public StreamedContent getExportFile() {
        InputStream stream = null;
        try {
            stream = new FileInputStream(fileTemp);
        } catch (Exception ex) {
            ManejadorErrores.ingresarExcepcionEnLog(ex, ArchivoInstrumentos.class);
            Messages.addFlashGlobalError(Utilidades.obtenerMensaje("adm.parametros.config.carga.errorRegistro"));
        } finally {
            System.gc();
            fileTemp.delete();
            return new DefaultStreamedContent(stream, "application/x-download", "ResultadoCargaInstrumentos.xlsm");
        }
    }

    private String calcularCodigoInterno(String codigoMinimo) {
        Double sec = fachadaInstrumentos.obtenerSecPredio();
        if (sec == null) {
            sec = new Double(0);
        }
        String codigoInterno = codigoMinimo;
        while ((codigoInterno + (sec.intValue() + 1)).length() < 20) {
            codigoInterno = codigoInterno + "0";
        }
        codigoInterno = codigoInterno + (sec.intValue() + 1);
        return codigoInterno;
    }

    public boolean isHayRegErroneos() {
        return hayRegErroneos;
    }

    public void setHayRegErroneos(boolean hayRegErroneos) {
        this.hayRegErroneos = hayRegErroneos;
    }

    public boolean isArchivoValido() {
        return archivoValido;
    }

    public void setArchivoValido(boolean archivoValido) {
        this.archivoValido = archivoValido;
    }

    public UsuarioSesionBean getUsuarioSesionBean() {
        return usuarioSesionBean;
    }

    public void setUsuarioSesionBean(UsuarioSesionBean usuarioSesionBean) {
        this.usuarioSesionBean = usuarioSesionBean;
    }

    public boolean isMostrarMsgGuardar() {
        return mostrarMsgGuardar;
    }

    public void setMostrarMsgGuardar(boolean mostrarMsgGuardar) {
        this.mostrarMsgGuardar = mostrarMsgGuardar;
    }

    private void guardarCobertura() {
        mapPreguntas = new HashMap<Integer, Pregunta>();
        mapTipologia = new HashMap<Integer, TipoDatoExcel>();
        Map<Integer, String> mapCodPreguntas = new HashMap<Integer, String>();
        Row rowDato, row;
        int rowNumber = 0;
        int contadorFilas = 0;
        Cell cell;
        Sheet cobertura = wb.getSheetAt(1);
        Long instrumento = null;
        int consecutivo = 0;
        while (true) {
            rowDato = cobertura.getRow(rowNumber);
            if (rowDato == null) {
                break;
            }
            if (rowNumber == 1 || rowNumber == 3 || rowNumber == 4) {
                rowNumber++;
                continue;
            }
            if (rowNumber == 0) {
                for (int col = 3; col <= 10; col++) {
                    mapCodPreguntas.put(col, getValorCelda(rowDato.getCell(col)).trim());
                }
                rowNumber++;
                continue;
            }
            if (rowNumber == 2) {
                for (int col = 3; col <= 10; col++) {
                    String valor = getValorCelda(rowDato.getCell(col));
                    TipoDatoExcel tde;
                    if (valor.equals("1")) {
                        tde = TipoDatoExcel.TIPOLOGIA;
                    } else {
                        tde = TipoDatoExcel.CADENA;
                    }
                    mapTipologia.put(col, tde);
                }
                rowNumber++;
                continue;
            }
            if (UtilCadena.isNullOVacio(getValorCelda(rowDato.getCell(0)))) {
                break;
            }
            if (!instrumentosDigitados.contains(getValorCelda(rowDato.getCell(0)))) {
                rowNumber++;
                continue;
            }
            Long instrumentoActual = new Long(getValorCelda(rowDato.getCell(0)));
            if (consecutivo <= 11 && instrumento != null && instrumento.equals(instrumentoActual)) {
                consecutivo++;
            } else {
                instrumento = new Long(getValorCelda(rowDato.getCell(0)));
                consecutivo = 1;
            }
            Formatter fmt = new Formatter();
            fmt.format("%02d", consecutivo);
            String numberResp = fmt.toString();
            for (int col = 3; col <= 10; col++) {
                String codPregunta = mapCodPreguntas.get(col);
                String codRespuesta = codPregunta.replaceAll("PREG", "RESP").concat("_").concat(numberResp);
                String valor = getValorCelda(rowDato.getCell(col));
                if (UtilCadena.isNullOVacio(valor)) {
                    continue;
                }
                Pregunta pregunta = new Pregunta(codPregunta);
                Respuesta respuesta = new Respuesta(codRespuesta);
                TipoDatoExcel tde = mapTipologia.get(col);
                if (tde.equals(TipoDatoExcel.TIPOLOGIA)) {
                    valor = valor.split("-")[0].trim();
                }
                RespuestaDig resDig = new RespuestaDig();
                resDig.setElemento(mapElementosEst.get(instrumentoActual));
                resDig.setPregunta(pregunta);
                resDig.setRespuesta(respuesta);
                resDig.setValor(valor);
                fachadaInstrumentos.guardarRespuestaDigitado(resDig);
            }
            rowNumber++;
        }
    }

    private void guardarCoberturaB1() {
        mapPreguntas = new HashMap<Integer, Pregunta>();
        mapTipologia = new HashMap<Integer, TipoDatoExcel>();
        Map<Integer, String> mapCodPreguntas = new HashMap<Integer, String>();
        Row rowDato, row;
        int rowNumber = 0;
        int contadorFilas = 0;
        Cell cell;
        Sheet cobertura = wb.getSheetAt(8);
        String instrumento = null;
        int consecutivo = 0;
        while (true) {
            rowDato = cobertura.getRow(rowNumber);
            if (rowDato == null) {
                break;
            }
            if (rowNumber == 1 || rowNumber == 3 || rowNumber == 4) {
                rowNumber++;
                continue;
            }
            if (rowNumber == 0) {
                for (int col = 4; col <= 11; col++) {
                    mapCodPreguntas.put(col, getValorCelda(rowDato.getCell(col)).trim());
                }
                rowNumber++;
                continue;
            }
            if (rowNumber == 2) {
                for (int col = 4; col <= 11; col++) {
                    String valor = getValorCelda(rowDato.getCell(col));
                    TipoDatoExcel tde;
                    if (valor.equals("1")) {
                        tde = TipoDatoExcel.TIPOLOGIA;
                    } else {
                        tde = TipoDatoExcel.CADENA;
                    }
                    mapTipologia.put(col, tde);
                }
                rowNumber++;
                continue;
            }
            if (UtilCadena.isNullOVacio(getValorCelda(rowDato.getCell(0)))) {
                break;
            }
            if (!instrumentosDigitados.contains(getValorCelda(rowDato.getCell(0)))) {
                rowNumber++;
                continue;
            }
            String instrumentoActual = getValorCelda(rowDato.getCell(0))
                    .concat("_").concat(getValorCelda(rowDato.getCell(3)));
            if (consecutivo <= 11 && instrumento != null && instrumento.equals(instrumentoActual)) {
                consecutivo++;
            } else {
                instrumento = getValorCelda(rowDato.getCell(0))
                        .concat("_").concat(getValorCelda(rowDato.getCell(3)));
                consecutivo = 1;
            }
            Formatter fmt = new Formatter();
            fmt.format("%02d", consecutivo);
            String numberResp = fmt.toString();
            for (int col = 4; col <= 11; col++) {
                String codPregunta = mapCodPreguntas.get(col);
                String codRespuesta = codPregunta.replaceAll("PREG", "RESP").concat("_").concat(numberResp);
                String valor = getValorCelda(rowDato.getCell(col));
                if (UtilCadena.isNullOVacio(valor)) {
                    continue;
                }
                Pregunta pregunta = new Pregunta(codPregunta);
                Respuesta respuesta = new Respuesta(codRespuesta);
                TipoDatoExcel tde = mapTipologia.get(col);
                if (tde.equals(TipoDatoExcel.TIPOLOGIA)) {
                    valor = valor.split("-")[0].trim();
                }
                RespuestaDig resDig = new RespuestaDig();
                resDig.setElemento(mapElementosEstB1.get(instrumentoActual));
                resDig.setPregunta(pregunta);
                resDig.setRespuesta(respuesta);
                resDig.setValor(valor);
                fachadaInstrumentos.guardarRespuestaDigitado(resDig);
            }
            rowNumber++;
        }
    }

    public List<RespuestaDig> complementarRespuestaDigitadas(Elemento elemento) {
        List<Respuesta> respuestas = fachadaInstrumentos.obtenerRespuestaParaCompletar(elemento.getTipoElemento().getCodigo());
        List<RespuestaDig> respDig = new ArrayList<RespuestaDig>();
        for (Respuesta resp : respuestas) {
            respDig.add(new RespuestaDig(elemento, resp.getCodPreguntas(), resp, "0"));
        }
        return respDig;
    }
}
