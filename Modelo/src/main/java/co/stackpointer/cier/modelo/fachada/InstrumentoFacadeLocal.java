/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.fachada;

import co.stackpointer.cier.modelo.entidad.digitado.Adjunto;
import co.stackpointer.cier.modelo.entidad.digitado.Elemento;
import co.stackpointer.cier.modelo.entidad.digitado.EspacioSimilar;
import co.stackpointer.cier.modelo.entidad.digitado.InstrumentoDig;
import co.stackpointer.cier.modelo.entidad.instrumento.Encuestador;
import co.stackpointer.cier.modelo.entidad.digitado.RespuestaDig;
import co.stackpointer.cier.modelo.entidad.establecimiento.Establecimiento;
import co.stackpointer.cier.modelo.entidad.establecimiento.Predio;
import co.stackpointer.cier.modelo.entidad.establecimiento.Sede;
import co.stackpointer.cier.modelo.entidad.instrumento.Instrumento;
import co.stackpointer.cier.modelo.entidad.instrumento.ModuloIns;
import co.stackpointer.cier.modelo.entidad.instrumento.Pregunta;
import co.stackpointer.cier.modelo.entidad.instrumento.Respuesta;
import co.stackpointer.cier.modelo.entidad.instrumento.Seccion;
import co.stackpointer.cier.modelo.entidad.instrumento.Supervisor;
import co.stackpointer.cier.modelo.entidad.instrumento.TipoElemento;
import co.stackpointer.cier.modelo.entidad.instrumento.Tipologia;
import co.stackpointer.cier.modelo.entidad.instrumento.TipologiaValorNombre;
import co.stackpointer.cier.modelo.tipo.EstadoInstrumento;
import co.stackpointer.cier.modelo.tipo.EstadoInstrumentoFormato;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author StackPointer
 */
@Local
public interface InstrumentoFacadeLocal {
    
    public List<ModuloIns> obtenerModulosActivos();
    
    public List<ModuloIns> obtenerModulosInstrumento(Instrumento instrumento);
    
    public InstrumentoDig guardarInstrumento(InstrumentoDig insDig);
    
    public InstrumentoDig modificarInstrumento(InstrumentoDig insDig);
    
    public Instrumento consultarInstrumentoVigente();
    
    public List<Pregunta> obtenerPreguntasOrdenadas(Instrumento instrumento);
    
    public Respuesta obtenerRespuestaPorCodigo(String codigo);
    
    public Pregunta obtenerPreguntaPorCodigo(String codigo);
    
    public Tipologia obtenerTipologiaPorCodigo(String codigo);
    
    public TipologiaValorNombre obtenerTipologiaValorPorTipologiaCodigo(Tipologia tipologia,String codigoValorTipologia);
    
    public void guardarElementoDigitadoUnico(Elemento elemento);
    
    public void guardarElementoDigitado(Elemento elemento);
    
    public void guardarElementoDigitadoUnicos(Elemento elemento);
    
    public Elemento guardarElementoDigitadoElemento(Elemento elemento);
    
     public void guardarElementoObservacion(Elemento elemento);
    
    public void guardarRespuestaDigitado(RespuestaDig respuesta);
    
    public void modificarRespuestaDigitado(RespuestaDig respuesta);
    
    public Elemento obtenerElementoPorCodigoInstrumento(InstrumentoDig instrumento, String codigoElemento);
    
    public Elemento obtenerElementoPorIdInstrumentoDigitadoDescripcionElemento(Long idInstrumento, String descripcionElemento);
    
    public InstrumentoDig obtenerInstrumentoDigitadoPorBoletaFechaPredio(Date fecha, String boletaCensal, Predio predio);
    
    public InstrumentoDig obtenerInstrumentoDigitadoPorBoletaFechaPredioSedeEstablecimiento(Date fecha, String boletaCensal, Predio predio, Sede sede, Establecimiento establecimiento);
    
    public Predio obtenerPredioPorCodigo(String codigoPredio);
    
    public RespuestaDig obtenerRespuestaDigitadaPorElementoPreguntaRespuesta(Elemento elemento, Pregunta pregunta , Respuesta respuesta);
    
    public Encuestador obtenerEncuestadorPorCodigo(String codigoEncuestador);
    
    public Supervisor obtenerSupervisorPorCodigo(String codigoSupervisor);
    
    public void guardarEncuestador(Encuestador e);
    
    public void actualizarEncuestador(Encuestador e);
    
    public void eliminarEncuestador(Encuestador e);
    
    public void guardarSupervisor(Supervisor s);
    
    public void actualizarSupervisor(Supervisor s);
    
    public void eliminarSupervisor(Supervisor s);
    
    public Object obtenerCodigoParametro(String entidad, String codigo);
    
    public List<TipoElemento> obtenerTiposElementos();
    
    public List<Pregunta> obtenerPreguntaPorTipoElemento(TipoElemento tipoElemento,ModuloIns modulo,Seccion seccion);
    
    public List<Pregunta> obtenerPreguntaPorTipoElemento(String tipoElemento,String modulo,String seccion);
    
    public List<Pregunta> obtenerPreguntaPorTipoElemento(TipoElemento tipoElemento);
    
    public Double obtenerSecPredio();
    
    TipoElemento buscarTipoElementoPorCodigo(String codigoTipoElem);
    
    InstrumentoDig consultarInstrumentoPorCodigo(String codigo);
    
    InstrumentoDig consultarInstrumentoPorCodigo(Long codigo);
    
    ModuloIns obtenerModuloPorCodigo(String codigo);
    
    Seccion obtenerSeccionPorCodigo(String codigo);
    
    public void insertarRespuestaDigitada(Long idRespuestaDigitada,Long idElemento, String codPregunta, String codRespuesta, String valor);
    
    public List<Elemento> obtenerElementoPorIdInstrumentoDigitado(String idInstrumentoDigitado);
    
    public List<Elemento> obtenerElementoPorIdInstrumentoDigitado(Long idInstrumentoDigitado);
    
    public List<RespuestaDig> obtenerRespuestasDigitadasPorElementoId(Long idElemento);
    
    public List<RespuestaDig> obtenerRespuestasDigitadasPorElemento(Elemento elemento);
    
    public List<Adjunto> obtenerAdjuntosPorRespuestaDigitada(Long idRespuestaDigitada);
    
    public InstrumentoDig insertarInstrumentoDigitado(InstrumentoDig instrumento);
    
    public void eliminarElemento(Elemento elemento);
    
    public void anularInstrumento(InstrumentoDig instrumento);
    
    public void actualizarFechaUsuarioModificacion(InstrumentoDig instrumento,String usuario);
    
    public void insertarRespuestaDigitadaAjunto(Long idRespuestaDigitada, Adjunto adjunto);
    
    public Adjunto insertarAdjunto(Long idRespuestaDigitada, Adjunto adjunto);
    
    public boolean existeInstrumentoRepetido(Sede sede,Establecimiento establecimiento,Predio predio, int periodo);
    
    public EstadoInstrumento estadoInstrumentoRepetido(Sede sede,Establecimiento establecimiento,Predio predio);
    
    public List<String> emitirInstrumento(InstrumentoDig instrumento, String userName);
    
    public List<Adjunto> obtenerAdjuntosPorIdInstrumentoRespuesta(Long idInstrumento, String codPregunta);
    
    public Long crearNovedad(Long idInstrumento, String username);
    
    public void cambiarEstadoNuevo(Long idInstrumento);
    
    public RespuestaDig obtenerRespuestaDigitadaPorId(Long idRespuestaDigitada );
    
    public RespuestaDig obtenerRespuestaDigitadaPorIdInstrumentoCodigoRespuesta(Long idInstrumento,String codigoRespuesta);
    
    public RespuestaDig obtenerRespuestaDigitada(Elemento elemento, String codigoPregunta, String codigoRespuesta);
    
    public RespuestaDig crearRespuestaDigitadaPorIdInstrumentoCodigoRespuesta(Long idInstrumento, String codigoRespuesta, String descripcionElemento);
    
    public RespuestaDig guardarRespuestaDigitadaAdjunto(RespuestaDig respuesta);
            
    public void actualizarRespuestaDigitada(RespuestaDig respuesta);
    
    public List<RespuestaDig> obtenerRespuestasDigitadasPorTipoElementoCodigoPregunta(TipoElemento tipoElemento, String codigoPregunta, InstrumentoDig instrumento);
    
    public boolean idElementoRepetido(TipoElemento tipoElemento, String codigoPregunta, String idEdificio, Elemento elementoSeleccionado);
   
    public List<BigDecimal> obtenerInstrumentosDigPorSede(Long idSede);
   
    public List<BigDecimal> obtenerInstrumentosDigPorEstablecimiento(Long idEstablecimiento);
    
    public List<Respuesta> obtenerRespuestaPrincipales(String codigoInstrumentoVigente);
    
    public List<Respuesta> obtenerRespuestaObligatorias();
    
    public List<Respuesta> obtenerRespuestaDependiente(String codigoRespuesta);
    
    public List<Respuesta> obtenerRespuestaPrincipales(TipoElemento tipo,String codigoInstrumento);
    
    public List<Respuesta> obtenerRespuestaPorFila(Respuesta respuesta);
    
    public boolean tieneRepuestasDigitadasPorModulo(String codModulo,InstrumentoDig instrumento);
    
    public List<Instrumento> consultarInstrumentosConfiguracion(String codigo,EstadoInstrumentoFormato estado);
    
    public Elemento obtenerElementoPorId(Elemento elemento);
    
    public void eliminarRespuestaDigitadaId(Long idRespuesta);
    
    public void eliminarElementoDigitadoId(Long idElemento);
    
    public void actualizarRespuestaDigitadaId(Long idRespuesta,String valor);
   
    public List<InstrumentoDig> consultaInstrumentoPredioSedeEstablecimiento(InstrumentoDig instrumento);

    public void eliminarAdjunto(Adjunto adjunto);
    
    public String obtenerDescripcionElemento(Long idInstrumento, String tipoElemento, String descripcion);
    
    public boolean tieneRespuestaValido(Long idInstrumento, String codigoRespuesta,String idNuevoElemento);
    
    public boolean tieneRespuestaCorrespondiente(InstrumentoDig instrumento,String idEdificio,String idEspacio,String idPiso);

    public Elemento obtenerElementoPorIdInstrumentoDigitadoTipoElemento(Long idInstrumento, String tipoElemento);
    
    public InstrumentoDig consultaInstrumentoPorBoletaCodPredio(String boleta, String codigoPredio);
   
    public Elemento guardarElementoDigitadoArchivo(Elemento elemento);
    
    public Elemento guardarElementoDigitadoArchivoVerificado(Elemento elemento);
    
    public String obtenerMaximaBoletaCensal();
    
    public List<Respuesta> obtenerRespuestaParaCompletar(String codigoTipoElemento);
    
    public List<Respuesta> obtenerRespuestasDependientesByTipoElemento(String elemento);
    
    public List<Respuesta> obtenerRespuestasPorPreguntas(List<String> preguntas);
    
    public Elemento obtenerElementoPorRespuestaValor(Long idInstrumento,String codRespuesta, String valor);
    
    public List<Respuesta> obtenerRespuestaRepetidas(Respuesta respuesta);

    public void crearEspaciosSimilares(long idInstrumento, String elemento, String espaciosDigitados);

}