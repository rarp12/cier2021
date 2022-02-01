/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.fachada;

import co.stackpointer.cier.modelo.entidad.digitado.Adjunto;
import co.stackpointer.cier.modelo.entidad.digitado.Elemento;
import co.stackpointer.cier.modelo.entidad.digitado.EspacioSimilar;
import co.stackpointer.cier.modelo.entidad.digitado.InstrumentoDig;
import co.stackpointer.cier.modelo.entidad.digitado.RespuestaDig;
import co.stackpointer.cier.modelo.entidad.dpa.Nivel;
import co.stackpointer.cier.modelo.entidad.establecimiento.Establecimiento;
import co.stackpointer.cier.modelo.entidad.establecimiento.Predio;
import co.stackpointer.cier.modelo.entidad.establecimiento.Sede;
import co.stackpointer.cier.modelo.entidad.instrumento.Encuestador;
import co.stackpointer.cier.modelo.entidad.instrumento.Instrumento;
import co.stackpointer.cier.modelo.entidad.instrumento.ModuloIns;
import co.stackpointer.cier.modelo.entidad.instrumento.Pregunta;
import co.stackpointer.cier.modelo.entidad.instrumento.Respuesta;
import co.stackpointer.cier.modelo.entidad.instrumento.Seccion;
import co.stackpointer.cier.modelo.entidad.instrumento.Supervisor;
import co.stackpointer.cier.modelo.entidad.instrumento.TipoElemento;
import co.stackpointer.cier.modelo.entidad.instrumento.Tipologia;
import co.stackpointer.cier.modelo.entidad.instrumento.TipologiaValorNombre;
import co.stackpointer.cier.modelo.excepcion.ErrorGeneral;
import co.stackpointer.cier.modelo.excepcion.ErrorIntegridad;
import co.stackpointer.cier.modelo.excepcion.ErrorProcedimientoBD;
import co.stackpointer.cier.modelo.tipo.EstadoInstrumento;
import co.stackpointer.cier.modelo.tipo.EstadoInstrumentoFormato;
import co.stackpointer.cier.modelo.tipo.ParamBaseDatos;
import co.stackpointer.cier.modelo.tipo.SiNo;
import co.stackpointer.cier.modelo.tipo.Verificable;
import co.stackpointer.cier.modelo.utilidad.UtilCadena;
import co.stackpointer.cier.modelo.utilidad.UtilConsulta;
import co.stackpointer.cier.modelo.utilidad.UtilOut;
import co.stackpointer.cier.modelo.utilidad.UtilProperties;
import java.math.BigDecimal;
import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import javax.sql.DataSource;
import org.eclipse.persistence.config.CacheUsage;
import org.eclipse.persistence.config.QueryHints;

/**
 * @author StackPointer
 */
@Stateless
public class InstrumentoFacade implements InstrumentoFacadeLocal {

    @Inject
    private TenantPersistenceManager tpm;

    @Override
    public List<ModuloIns> obtenerModulosActivos() {
        List<ModuloIns> modulos = new ArrayList<ModuloIns>();
        try {
            modulos = tpm.getEm().createNamedQuery("ModuloIns.findAllActivos").getResultList();
        } catch (Exception ex) {
            UtilOut.println(ex);
        }
        return modulos;
    }

    @Override
    public InstrumentoDig guardarInstrumento(InstrumentoDig insDig) {
        try {
            tpm.getEm().persist(insDig);
            tpm.getEm().flush();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ErrorGeneral("Error", ex.getMessage());
        }
        return insDig;
    }

    @Override
    public InstrumentoDig modificarInstrumento(InstrumentoDig instrumento) {
        try {
            //Actualizo el encabezado.
            tpm.getEm().createNativeQuery("UPDATE dig_instrumentos "
                    + "       SET boleta_censal=?,id_establecimiento=?, "
                    + "       id_sede = ?, id_predio = ?, fecha_modificacion = CURRENT_DATE, "
                    + "       id_nivel0=?, id_nivel1=?, id_nivel2=?, id_nivel3=?, "
                    + "       id_nivel4=?, id_nivel5=?,fecha_encuesta=? "
                    + "       WHERE id = ?")
                    .setParameter(1, instrumento.getBoletaCensal())
                    .setParameter(2, instrumento.getEstablecimiento().getId())
                    .setParameter(3, instrumento.getSede().getId())
                    .setParameter(4, instrumento.getPredio().getId())
                    .setParameter(5, instrumento.getNivel0() != null ? instrumento.getNivel0().getId() : 0L)
                    .setParameter(6, instrumento.getNivel1() != null ? instrumento.getNivel1().getId() : 0L)
                    .setParameter(7, instrumento.getNivel2() != null ? instrumento.getNivel2().getId() : 0L)
                    .setParameter(8, instrumento.getNivel3() != null ? instrumento.getNivel3().getId() : 0L)
                    .setParameter(9, instrumento.getNivel4() != null ? instrumento.getNivel4().getId() : 0L)
                    .setParameter(10, instrumento.getNivel5() != null ? instrumento.getNivel5().getId() : 0L)
                    .setParameter(11, instrumento.getFechaEncuesta())
                    .setParameter(12, instrumento.getId())
                    .executeUpdate();
            //Actualizo los elementos del instrumento.
            if (instrumento.isActualizableElementos()) {
                for (Elemento elemento : this.obtenerElementoPorIdInstrumentoDigitado(instrumento.getId())) {
                    tpm.getEm().createNativeQuery("UPDATE dig_elementos "
                            + "   SET id_establecimiento=?, id_sede= ?"
                            + "   WHERE id = ?")
                            .setParameter(1, instrumento.getEstablecimiento().getId())
                            .setParameter(2, instrumento.getSede().getId())
                            .setParameter(3, elemento.getId())
                            .executeUpdate();
                }
            }
            return instrumento;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ErrorGeneral("Error", ex.getMessage());
        }
    }

    @Override
    public Instrumento consultarInstrumentoVigente() {
        try {
            Object obj = tpm.getEm().createNamedQuery("Instrumento.findVigente").getSingleResult();
            return (Instrumento) obj;
        } catch (NoResultException ex) {
            return null;
        } catch (Exception exp) {
            throw new ErrorGeneral("Error", exp.getMessage());
        }
    }

    @Override
    public InstrumentoDig consultarInstrumentoPorCodigo(String codigo) {
        try {
            Object obj = (InstrumentoDig) tpm.getEm().createNamedQuery("InstrumentoDigitado.findByCodigo").setHint(QueryHints.CACHE_USAGE, CacheUsage.DoNotCheckCache).setParameter("codigo", Long.valueOf(codigo)).getSingleResult();
            return (InstrumentoDig) obj;
        } catch (NoResultException ex) {
            return null;
        } catch (Exception exp) {
            throw new ErrorGeneral("Error", exp.getMessage());
        }
    }

    @Override
    public InstrumentoDig consultarInstrumentoPorCodigo(Long codigo) {
        try {
            Object obj = (InstrumentoDig) tpm.getEm().createNamedQuery("InstrumentoDigitado.findByCodigo").setHint(QueryHints.CACHE_USAGE, CacheUsage.DoNotCheckCache).setParameter("codigo", codigo).getSingleResult();
            return (InstrumentoDig) obj;
        } catch (NoResultException ex) {
            return null;
        } catch (Exception exp) {
            throw new ErrorGeneral("Error", exp.getMessage());
        }
    }

    public InstrumentoDig consultaUltimoEmitido(Long codigo) {
        try {
            InstrumentoDig intrumentoOriginal = consultarInstrumentoPorCodigo(codigo);
            InstrumentoDig instrumentoUltimoEmitido = (InstrumentoDig) tpm.getEm().createNamedQuery("Instrumento.ultimoEmitido")
                    .setParameter("predio", intrumentoOriginal.getPredio())
                    .setParameter("estado", EstadoInstrumento.E)
                    .setMaxResults(1).getSingleResult();
            /*if (instrumentoUltimoEmitido != null) {
             Systtpm.getEm().out.println("-->> " + instrumentoUltimoEmitido.getId());
             }*/
            return instrumentoUltimoEmitido;
        } catch (NoResultException ex) {
            return null;
        } catch (Exception exp) {
            throw new ErrorGeneral("Error", exp.getMessage());
        }
    }

    @Override
    public List<Pregunta> obtenerPreguntasOrdenadas(Instrumento instrumento) {
        List<Pregunta> preguntas = null;
        try {
            preguntas = tpm.getEm().createNamedQuery("Pregunta.findPreguntaByInstrumento").setParameter("instrumento", instrumento).getResultList();
        } catch (Exception ex) {
            throw new ErrorGeneral("Error", ex.getMessage());
        }
        return preguntas;
    }

    @Override
    public Respuesta obtenerRespuestaPorCodigo(String codigo) {
        try {
            return (Respuesta) tpm.getEm().createNamedQuery("Respuesta.findByCodigo").setParameter("codigo", codigo).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } catch (Exception exp) {
            throw new ErrorGeneral("Error", exp.getMessage());
        }
    }

    @Override
    public Pregunta obtenerPreguntaPorCodigo(String codigo) {
        try {
            return (Pregunta) tpm.getEm().createNamedQuery("Pregunta.findPreguntaByCodigo").setParameter("codigo", codigo).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } catch (Exception exp) {
            throw new ErrorGeneral("Error", exp.getMessage());
        }
    }

    @Override
    public Tipologia obtenerTipologiaPorCodigo(String codigo) {
        try {
            return (Tipologia) tpm.getEm().createNamedQuery("Tipologia.findByCodigo").setParameter("codigo", codigo).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } catch (Exception exp) {
            throw new ErrorGeneral("Error", exp.getMessage());
        }
    }

    @Override
    public TipologiaValorNombre obtenerTipologiaValorPorTipologiaCodigo(Tipologia tipologia, String codigoValorTipologia) {

        try {
            return (TipologiaValorNombre) tpm.getEm().createNamedQuery("TipologiaValorNombre.consultarPorTipologiaCodigo").setParameter("tipologia", tipologia).setParameter("codigo", codigoValorTipologia).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } catch (Exception exp) {
            throw new ErrorGeneral("Error", exp.getMessage());
        }
    }

    @Override
    public void guardarElementoDigitado(Elemento elemento) {
        try {
            if (elemento.getRespuestasDigitadasIniciales().size() > 0) {
                Elemento elementoConsultado = this.obtenerElementoPorInstrumentoDescripcion(elemento);
                if (elementoConsultado == null) {
                    try {
                        //  
                        Long id = obtenerIdElementos();
                        this.insertarElemento(id, elemento.getDescripcion(), elemento.getTipoElemento().getCodigo(), elemento.getInstrumentoDigitado().getId(), elemento.getSede().getId(), elemento.getEstablecimiento().getId());
                        elemento.setId(id);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        throw new ErrorGeneral("Error Insertando", ex.getMessage());
                    }
                }
                //Guardo Respuestas Digitadas.
                if (elementoConsultado == null) {
                    //Persisto todas las respuestas digitadas del elemento.
                    for (RespuestaDig rspDig : elemento.getRespuestasDigitadasIniciales()) {
                        Long idRespDig = this.obtenerIdRespuestasDigitadas();
                        this.insertarRespuestaDigitada(idRespDig, elemento.getId(), rspDig.getPregunta().getCodigo(), rspDig.getRespuesta().getCodigo(), rspDig.getValor());
                        //Guardo Adjuntos
                        if (rspDig.getAdjuntosList() != null && !rspDig.getAdjuntosList().isEmpty()) {
                            for (Adjunto adjunto : rspDig.getAdjuntosList()) {
                                this.insertarRespuestaDigitadaAjunto(idRespDig, adjunto);
                            }
                        }
                    }

                } else {

                    //Elimino Adjuntos
                    this.limpiarAdjuntosPorElementoId(elementoConsultado.getId());
                    //Elimino las Preguntas.
                    this.limpiarRespuestasDig(elementoConsultado.getId());

                    //Guardo Nuevas Respuestas.
                    for (RespuestaDig rspDig : elemento.getRespuestasDigitadasIniciales()) {
                        //Elimino los adjuntos asociados al elemento.
                        Long idRespDig = this.obtenerIdRespuestasDigitadas();
                        this.insertarRespuestaDigitada(idRespDig, elementoConsultado.getId(), rspDig.getPregunta().getCodigo(), rspDig.getRespuesta().getCodigo(), rspDig.getValor());
                        //Guardo Adjuntos
                        if (rspDig.getAdjuntosList() != null && !rspDig.getAdjuntosList().isEmpty()) {
                            for (Adjunto adjunto : rspDig.getAdjuntosList()) {
                                this.insertarRespuestaDigitadaAjunto(idRespDig, adjunto);
                            }
                        }

                    }

                }
            }
        } catch (Exception exp) {
            exp.printStackTrace();
            throw new ErrorGeneral("Error", exp.getMessage());
        }
    }

    @Override
    public void guardarElementoDigitadoUnicos(Elemento elemento) {
        try {
            if (elemento.getRespuestasDigitadasIniciales().size() > 0) {
                Elemento elementoConsultado = this.obtenerElementoPorInstrumentoDescripcion(elemento);
                if (elementoConsultado == null) {
                    try {
                        //  
                        Long id = obtenerIdElementos();
                        this.insertarElemento(id, elemento.getDescripcion(), elemento.getTipoElemento().getCodigo(), elemento.getInstrumentoDigitado().getId(), elemento.getSede().getId(), elemento.getEstablecimiento().getId());
                        elemento.setId(id);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        throw new ErrorGeneral("Error Insertando", ex.getMessage());
                    }
                }
                //Guardo Respuestas Digitadas.
                if (elementoConsultado == null) {
                    //Persisto todas las respuestas digitadas del elemento.
                    for (RespuestaDig rspDig : elemento.getRespuestasDigitadasIniciales()) {
                        Long idRespDig = this.obtenerIdRespuestasDigitadas();
                        this.insertarRespuestaDigitada(idRespDig, elemento.getId(), rspDig.getPregunta().getCodigo(), rspDig.getRespuesta().getCodigo(), rspDig.getValor());
                        //Guardo Adjuntos
                        if (rspDig.getAdjuntosList() != null && !rspDig.getAdjuntosList().isEmpty()) {
                            for (Adjunto adjunto : rspDig.getAdjuntosList()) {
                                this.insertarRespuestaDigitadaAjunto(idRespDig, adjunto);
                            }
                        }
                    }

                } else {
                    List<RespuestaDig> respAlmacenadas = obtenerRespuestasDigitadasPorElementoId(elementoConsultado.getId());
                    //Elimino Adjuntos
                    this.limpiarAdjuntosPorElementoId(elementoConsultado.getId());
                    //Elimino las Preguntas.
                    this.limpiarRespuestasDig(elementoConsultado.getId());

                    List<RespuestaDig> respuestasSeleccionadas = elemento.getRespuestasDigitadasIniciales();

                    if (elemento.getDescripcion().equals("ESTABLECIMIENTO_1")) {
                        respuestasSeleccionadas = new ArrayList<RespuestaDig>();
                        for (RespuestaDig rsp : elemento.getRespuestasDigitadasIniciales()) {
                            if (rsp.getPregunta().getModulo().getCodigo().equals("MODULO1")) {
                                respuestasSeleccionadas.add(rsp);
                            }
                        }
                        
                        for (RespuestaDig rspDig : respAlmacenadas) {
                            if (!rspDig.getPregunta().getModulo().getCodigo().equals("MODULO1")) {
                                respuestasSeleccionadas.add(rspDig);
                            }
                        }
                        System.out.println("Size " + respuestasSeleccionadas.size());

                    }

                    //Guardo Nuevas Respuestas.
                    for (RespuestaDig rspDig : respuestasSeleccionadas) {
                        //Elimino los adjuntos asociados al elemento.
                        Long idRespDig = this.obtenerIdRespuestasDigitadas();
                        this.insertarRespuestaDigitada(idRespDig, elementoConsultado.getId(), rspDig.getPregunta().getCodigo(), rspDig.getRespuesta().getCodigo(), rspDig.getValor());
                        //Guardo Adjuntos
                        if (rspDig.getAdjuntosList() != null && !rspDig.getAdjuntosList().isEmpty()) {
                            for (Adjunto adjunto : rspDig.getAdjuntosList()) {
                                this.insertarRespuestaDigitadaAjunto(idRespDig, adjunto);
                            }
                        }

                    }

                }
            }
        } catch (Exception exp) {
            exp.printStackTrace();
            throw new ErrorGeneral("Error", exp.getMessage());
        }
    }

    @Override
    public Elemento guardarElementoDigitadoElemento(Elemento elemento) {
        try {
            if (elemento.getRespuestasDigitadasIniciales().size() > 0) {
                Elemento elementoConsultado = this.obtenerElementoPorInstrumentoDescripcion(elemento);
                if (elementoConsultado == null) {
                    try {
                        //  
                        Long id = obtenerIdElementos();
                        this.insertarElemento(id, elemento.getDescripcion(), elemento.getTipoElemento().getCodigo(), elemento.getInstrumentoDigitado().getId(), elemento.getSede().getId(), elemento.getEstablecimiento().getId());
                        elemento.setId(id);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        throw new ErrorGeneral("Error Insertando", ex.getMessage());
                    }
                }
                //Guardo Respuestas Digitadas.
                if (elementoConsultado == null) {
                    //Persisto todas las respuestas digitadas del elemento.
                    for (RespuestaDig rspDig : elemento.getRespuestasDigitadasIniciales()) {
                        Long idRespDig = this.obtenerIdRespuestasDigitadas();
                        this.insertarRespuestaDigitada(idRespDig, elemento.getId(), rspDig.getPregunta().getCodigo(), rspDig.getRespuesta().getCodigo(), rspDig.getValor());
                        //Guardo Adjuntos
                        if (rspDig.getAdjuntosList() != null && !rspDig.getAdjuntosList().isEmpty()) {
                            for (Adjunto adjunto : rspDig.getAdjuntosList()) {
                                this.insertarRespuestaDigitadaAjunto(idRespDig, adjunto);
                            }
                        }
                    }

                } else {

                    //Elimino Adjuntos
                    this.limpiarAdjuntosPorElementoId(elementoConsultado.getId());
                    //Elimino las Preguntas.
                    this.limpiarRespuestasDig(elementoConsultado.getId());

                    //Guardo Nuevas Respuestas.
                    for (RespuestaDig rspDig : elemento.getRespuestasDigitadasIniciales()) {
                        //Elimino los adjuntos asociados al elemento.
                        Long idRespDig = this.obtenerIdRespuestasDigitadas();
                        this.insertarRespuestaDigitada(idRespDig, elementoConsultado.getId(), rspDig.getPregunta().getCodigo(), rspDig.getRespuesta().getCodigo(), rspDig.getValor());
                        //Guardo Adjuntos
                        if (rspDig.getAdjuntosList() != null && !rspDig.getAdjuntosList().isEmpty()) {
                            for (Adjunto adjunto : rspDig.getAdjuntosList()) {
                                this.insertarRespuestaDigitadaAjunto(idRespDig, adjunto);
                            }
                        }

                    }

                }
            }
            return elemento;
        } catch (Exception exp) {
            exp.printStackTrace();
            throw new ErrorGeneral("Error", exp.getMessage());
        }
    }

    @Override
    public void guardarElementoDigitadoUnico(Elemento elemento) {
        if (elemento.getRespuestasList().size() > 0) {
            try {
                tpm.getEm().persist(elemento);
                tpm.getEm().flush();
            } catch (Exception ex) {
                throw new ErrorGeneral("Error Insertando", ex.getMessage());
            }
        }
    }

    public void modificarElementoDigitado(Elemento elemento) {
        try {
            tpm.getEm().merge(elemento);
            tpm.getEm().flush();
        } catch (Exception ex) {
            throw new ErrorGeneral("Error", ex.getMessage());
        }
    }

    @Override
    public void guardarRespuestaDigitado(RespuestaDig respuesta) {
        try {
            tpm.getEm().persist(respuesta);
            tpm.getEm().flush();
        } catch (Exception ex) {
            throw new ErrorGeneral("Error", ex.getMessage());
        }
    }

    @Override
    public Elemento obtenerElementoPorCodigoInstrumento(InstrumentoDig instrumento, String codigoElemento) {
        try {
            return (Elemento) tpm.getEm().createNamedQuery("Elemento.findByTipoInstrumento")
                    .setParameter("tipoElemento", codigoElemento)
                    .setParameter("instrumento", instrumento)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } catch (Exception exp) {
            throw new ErrorGeneral("Error", exp.getMessage());
        }
    }

    private List<Elemento> obtenerElementosPorCodigoInstrumento(InstrumentoDig instrumento, String codigoElemento) {
        try {
            return tpm.getEm().createNamedQuery("Elemento.findByTipoInstrumento")
                    .setParameter("tipoElemento", codigoElemento)
                    .setParameter("instrumento", instrumento)
                    .getResultList();
        } catch (NoResultException ex) {
            return null;
        } catch (Exception exp) {
            throw new ErrorGeneral("Error", exp.getMessage());
        }
    }

    @Override
    public Elemento obtenerElementoPorIdInstrumentoDigitadoDescripcionElemento(Long idInstrumento, String descripcionElemento) {
        try {
            return (Elemento) tpm.getEm().createNamedQuery("Elemento.findByInstrumentoIdDescripcionElemento")
                    .setParameter("idInstrumento", idInstrumento)
                    .setParameter("descripcion", descripcionElemento)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } catch (Exception exp) {
            throw new ErrorGeneral("Error", exp.getMessage());
        }
    }

    public Elemento obtenerElementoPorInstrumentoDescripcion(Elemento elemento) {
        try {
            return (Elemento) tpm.getEm().createNamedQuery("Elemento.findByDescripcionInstrumento")
                    .setParameter("descripcion", elemento.getDescripcion())
                    .setParameter("instrumento", elemento.getInstrumentoDigitado())
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } catch (Exception exp) {
            throw new ErrorGeneral("Error", exp.getMessage());
        }
    }

    @Override
    public InstrumentoDig obtenerInstrumentoDigitadoPorBoletaFechaPredio(Date fecha, String boletaCensal, Predio predio) {
        try {
            return (InstrumentoDig) tpm.getEm().createNamedQuery("InstrumentoDigitado.findByFechaBoletaPredio")
                    .setParameter("fechaEncuesta", fecha)
                    .setParameter("boletaCensal", boletaCensal)
                    .setParameter("predio", predio)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } catch (Exception exp) {
            throw new ErrorGeneral("Error", exp.getMessage());
        }
    }

    @Override
    public InstrumentoDig obtenerInstrumentoDigitadoPorBoletaFechaPredioSedeEstablecimiento(Date fecha, String boletaCensal, Predio predio, Sede sede, Establecimiento establecimietno) {
        try {
            return (InstrumentoDig) tpm.getEm().createNamedQuery("InstrumentoDigitado.findByFechaBoletaPredioSedeEstablecimiento")
                    .setParameter("fechaEncuesta", fecha)
                    .setParameter("boletaCensal", boletaCensal)
                    .setParameter("predio", predio)
                    .setParameter("sede", sede)
                    .setParameter("establecimiento", establecimietno)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } catch (Exception exp) {
            throw new ErrorGeneral("Error", exp.getMessage());
        }
    }

    @Override
    public Predio obtenerPredioPorCodigo(String codigoPredio) {
        try {
            return (Predio) tpm.getEm().createNamedQuery("Predio.findByCodigo").setParameter("codigo", codigoPredio).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } catch (NonUniqueResultException ex) {
            throw new ErrorGeneral("Error", "Existe mas de un predio con el código digitado");
        } catch (Exception exp) {
            throw new ErrorGeneral("Error", exp.getMessage());
        }
    }

    @Override
    public RespuestaDig obtenerRespuestaDigitadaPorElementoPreguntaRespuesta(Elemento elemento, Pregunta pregunta, Respuesta respuesta) {
        try {
            return (RespuestaDig) tpm.getEm().createNamedQuery("RespuestDig.findByElemPregResp")
                    .setParameter("elemento", elemento)
                    .setParameter("pregunta", pregunta)
                    .setParameter("respuesta", respuesta).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } catch (Exception exp) {
            throw new ErrorGeneral("Error", exp.getMessage());
        }
    }

    @Override
    public void modificarRespuestaDigitado(RespuestaDig respuesta) {
        try {
            tpm.getEm().merge(respuesta);
            tpm.getEm().flush();
        } catch (Exception ex) {
            throw new ErrorGeneral("Error", ex.getMessage());
        }
    }

    public Nivel obtenerNivelPorConfiguracionCodigo(Long idConfiguracion, String codigo) {
        try {
            return (Nivel) tpm.getEm().createNamedQuery("RespuestDig.findByElemPregResp")
                    .setParameter("idNivelConfiguracion", idConfiguracion)
                    .setParameter("codigo", codigo).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } catch (Exception exp) {
            throw new ErrorGeneral("Error", exp.getMessage());
        }
    }

    @Override
    public TipoElemento buscarTipoElementoPorCodigo(String codigoTipoElem) {
        try {
            return (TipoElemento) tpm.getEm().createNamedQuery("TipoElemento.buscarPorCodigo")
                    .setParameter("codigoTipoElem", codigoTipoElem)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public Encuestador obtenerEncuestadorPorCodigo(String codigoEncuestador) {
        try {
            return (Encuestador) tpm.getEm().createNamedQuery("Encuestador.findByCodigo").setParameter("codigo", codigoEncuestador).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } catch (Exception exp) {
            throw new ErrorGeneral("Error", exp.getMessage());
        }
    }

    @Override
    public Supervisor obtenerSupervisorPorCodigo(String codigoSupervisor) {
        try {
            return (Supervisor) tpm.getEm().createNamedQuery("Supervisor.findByCodigo").setParameter("codigo", codigoSupervisor).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } catch (Exception exp) {
            throw new ErrorGeneral("Error", exp.getMessage());
        }
    }

    @Override
    public void guardarEncuestador(Encuestador encuestador) {
        try {
            tpm.getEm().clear();
            tpm.getEm().persist(encuestador);
            tpm.getEm().flush();
        } catch (Exception e) {
            throw new ErrorIntegridad("No se pudo guardar el encuestador");
        }
    }

    @Override
    public void actualizarEncuestador(Encuestador encuestador) {
        try {
            tpm.getEm().clear();
            tpm.getEm().merge(encuestador);
            tpm.getEm().flush();
        } catch (Exception e) {
            throw new ErrorIntegridad("No se pudo actualizar el encuestador");
        }
    }

    @Override
    public void eliminarEncuestador(Encuestador encuestador) {
        try {
            tpm.getEm().clear();
            tpm.getEm().remove(tpm.getEm().contains(encuestador) ? encuestador : tpm.getEm().merge(encuestador));
            tpm.getEm().flush();
        } catch (Exception e) {
            throw new ErrorIntegridad("No se pudo eliminar el encuestador");
        }
    }

    @Override
    public void guardarSupervisor(Supervisor supervisor) {
        try {
            tpm.getEm().clear();
            tpm.getEm().persist(supervisor);
            tpm.getEm().flush();
        } catch (Exception e) {
            throw new ErrorIntegridad("No se pudo guardar el supervisor");
        }
    }

    @Override
    public void actualizarSupervisor(Supervisor supervisor) {
        try {
            tpm.getEm().clear();
            tpm.getEm().merge(supervisor);
            tpm.getEm().flush();
        } catch (Exception e) {
            throw new ErrorIntegridad("No se pudo actualizar el supervisor");
        }
    }

    @Override
    public void eliminarSupervisor(Supervisor supervisor) {
        try {
            tpm.getEm().clear();
            tpm.getEm().remove(tpm.getEm().contains(supervisor) ? supervisor : tpm.getEm().merge(supervisor));
            tpm.getEm().flush();
        } catch (Exception e) {
            throw new ErrorIntegridad("No se pudo eliminar el supervisor");
        }
    }

    @Override
    public Object obtenerCodigoParametro(String entidad, String codigo) {
        try {
            String sql = "Select a from " + entidad + " a where a.identificacion = :codigo";
            return (Object) tpm.getEm().createQuery(sql).setParameter("codigo", codigo).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } catch (Exception exp) {
            throw new ErrorGeneral("Error", exp.getMessage());
        }
    }

    @Override
    public List<ModuloIns> obtenerModulosInstrumento(Instrumento instrumento) {
        List<ModuloIns> modulos = null;
        try {
            modulos = tpm.getEm().createNamedQuery("ModuloIns.findModulosByInstrumento").setParameter("instrumento", instrumento).getResultList();
        } catch (Exception ex) {
            throw new ErrorGeneral("Error", ex.getMessage());
        }
        return modulos;
    }

    @Override
    public List<TipoElemento> obtenerTiposElementos() {
        List<TipoElemento> tiposElementos = null;
        try {
            tiposElementos = tpm.getEm().createNamedQuery("TipoElemento.findAll").getResultList();
        } catch (Exception ex) {
            throw new ErrorGeneral("Error", ex.getMessage());
        }
        return tiposElementos;
    }

    @Override
    public List<Pregunta> obtenerPreguntaPorTipoElemento(TipoElemento tipoElemento, ModuloIns modulo, Seccion seccion) {
        List<Pregunta> preguntas = null;
        try {
            preguntas = tpm.getEm().createNamedQuery("Pregunta.obtenerPreguntaPorTipoElementoModuloSeccion")
                    .setParameter("tipoElemento", tipoElemento)
                    .setParameter("modulo", modulo)
                    .setParameter("seccion", seccion).getResultList();
        } catch (Exception ex) {
            throw new ErrorGeneral("Error", ex.getMessage());
        }
        return preguntas;
    }

    @Override
    public List<Pregunta> obtenerPreguntaPorTipoElemento(String tipoElemento, String modulo, String seccion) {
        List<Pregunta> preguntas = null;
        try {
            preguntas = tpm.getEm().createNamedQuery("Pregunta.obtenerPreguntaPorTipoElementoModuloSeccionString")
                    .setParameter("tipoElemento", tipoElemento)
                    .setParameter("modulo", modulo)
                    .setParameter("seccion", seccion).getResultList();
        } catch (Exception ex) {
            throw new ErrorGeneral("Error", ex.getMessage());
        }
        return preguntas;
    }

    @Override
    public List<Pregunta> obtenerPreguntaPorTipoElemento(TipoElemento tipoElemento) {
        List<Pregunta> pregunta = null;
        try {
            pregunta = tpm.getEm().createNamedQuery("Pregunta.obtenerPreguntaPorTipoElemento")
                    .setParameter("tipoElemento", tipoElemento).getResultList();
        } catch (Exception ex) {
            throw new ErrorGeneral("Error", ex.getMessage());
        }
        return pregunta;
    }

    @Override
    public Double obtenerSecPredio() {
        Query query = tpm.getEm().createNativeQuery("Select max(id) from est_predios");
        return query.getSingleResult() != null ? new Double(query.getSingleResult() + "") : null;
    }

    @Override
    public ModuloIns obtenerModuloPorCodigo(String codigo) {
        try {
            return (ModuloIns) tpm.getEm().createNamedQuery("ModuloIns.findByCodigo").setParameter("codigo", codigo).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } catch (Exception exp) {
            throw new ErrorGeneral("Error", exp.getMessage());
        }
    }

    @Override
    public Seccion obtenerSeccionPorCodigo(String codigo) {
        try {
            return (Seccion) tpm.getEm().createNamedQuery("Seccion.findByCodigo").setParameter("codigo", codigo).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } catch (Exception exp) {
            throw new ErrorGeneral("Error", exp.getMessage());
        }
    }

    public TipoElemento obtenerTipoElementoPorCodigo(String codigo) {
        try {
            return (TipoElemento) tpm.getEm().createNamedQuery("Seccion.findByCodigo").setParameter("codigo", codigo).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } catch (Exception exp) {
            throw new ErrorGeneral("Error", exp.getMessage());
        }
    }

    private void limpiarRespuestasDig(Long idElemento) {
        try {
            tpm.getEm().createNativeQuery("delete from dig_respuestas where id_dig_elemento = ?")
                    .setParameter(1, idElemento).executeUpdate();
        } catch (Exception exp) {
            throw new ErrorGeneral("Error", exp.getMessage());
        }
    }

    private void limpiarAdjuntos(Long idAdjunto) {
        try {
            tpm.getEm().createNativeQuery("delete from dig_adjuntos where id = ?")
                    .setParameter(1, idAdjunto).executeUpdate();
        } catch (Exception exp) {
            throw new ErrorGeneral("Error", exp.getMessage());
        }
    }

    private void limpiarAdjuntosPorElementoId(Long idElemento) {
        try {
            tpm.getEm().createNativeQuery("delete from dig_adjuntos where id_respuesta_dig in (select id from dig_respuestas where id_dig_elemento = ? )")
                    .setParameter(1, idElemento).executeUpdate();
        } catch (Exception exp) {
            throw new ErrorGeneral("Error", exp.getMessage());
        }
    }

    private void limpiarElemento(Long idElemento) {
        try {
            tpm.getEm().createNativeQuery("delete from dig_elementos where id = ?")
                    .setParameter(1, idElemento).executeUpdate();
        } catch (Exception exp) {
            throw new ErrorGeneral("Error", exp.getMessage());
        }
    }

    @Override
    public InstrumentoDig insertarInstrumentoDigitado(InstrumentoDig instrumento) {
        try {
            tpm.getEm().persist(instrumento);
            tpm.getEm().flush();
            return instrumento;
        } catch (Exception e) {
            throw new ErrorIntegridad("error al guardar el Instrumento");
        }
    }

    @Override
    public void insertarRespuestaDigitada(Long idRespuestaDigitada, Long idElemento, String codPregunta, String codRespuesta, String valor) {
        try {
            tpm.getEm().createNativeQuery("INSERT INTO dig_respuestas("
                    + "  id, id_dig_elemento, cod_pregunta, cod_respuesta, valor, fecha_digitacion)"
                    + "    VALUES (?, ?, ?, ?, ?, CURRENT_DATE )")
                    .setParameter(1, idRespuestaDigitada)
                    .setParameter(2, idElemento)
                    .setParameter(3, codPregunta)
                    .setParameter(4, codRespuesta)
                    .setParameter(5, valor)
                    .executeUpdate();
        } catch (Exception exp) {
            exp.printStackTrace();
            throw new ErrorGeneral("Error", exp.getMessage());
        }
    }

    private void actualizarRespuestaDigitada(Long idRespuestaDigitada, String valor) {
        try {
            tpm.getEm().createNativeQuery("UPDATE  dig_respuestas set valor = ? where id = ?")
                    .setParameter(1, valor)
                    .setParameter(2, idRespuestaDigitada)
                    .executeUpdate();
        } catch (Exception exp) {
            exp.printStackTrace();
            throw new ErrorGeneral("Error", exp.getMessage());
        }
    }

    @Override
    public void insertarRespuestaDigitadaAjunto(Long idRespuestaDigitada, Adjunto adjunto) {
        try {
            tpm.getEm().createNativeQuery("INSERT INTO dig_adjuntos("
                    + "  id, id_tnt, ruta, usuario_creacion, fecha_creacion, tipo, content_type,nombre, id_respuesta_dig)"
                    + "    VALUES (" + UtilProperties.getProperties().getProperty("seqIdAdjuntos") + ",?, ?, ?, CURRENT_DATE, ?, ?, ?, ?)")
                    .setParameter(1, tpm.getCurrentTenant())
                    .setParameter(2, adjunto.getRuta())
                    .setParameter(3, adjunto.getUsuarioCreacion())
                    .setParameter(4, adjunto.getTipo())
                    .setParameter(5, adjunto.getContentType())
                    .setParameter(6, adjunto.getNombre())
                    .setParameter(7, idRespuestaDigitada)
                    .executeUpdate();
        } catch (Exception exp) {
            exp.printStackTrace();
            throw new ErrorGeneral("Error", exp.getMessage());
        }
    }

    public void insertarElemento(Long id, String descripcion, String codTipo, Long idInstrumentoDig, Long idSede, Long idEstablecimiento) {
        try {
            tpm.getEm().createNativeQuery("INSERT INTO dig_elementos("
                    + " id, descripcion, cod_tipo, id_dig_instrumento, "
                    + " id_establecimiento, id_sede)"
                    + "  VALUES (?, ?, ?, ?, ?, ?)")
                    .setParameter(1, id)
                    .setParameter(2, descripcion)
                    .setParameter(3, codTipo)
                    .setParameter(4, idInstrumentoDig)
                    .setParameter(5, idSede)
                    .setParameter(6, idEstablecimiento)
                    .executeUpdate();
        } catch (Exception exp) {
            exp.printStackTrace();
            throw new ErrorGeneral("Error Insertando Elementos", exp.getMessage());
        }
    }

    @Override
    public Adjunto insertarAdjunto(Long idRespuestaDigitada, Adjunto adjunto) {
        try {
            Long idAdj = obtenerIdAdjuntos();
            adjunto.setId(idAdj);
            tpm.getEm().createNativeQuery("INSERT INTO dig_adjuntos("
                    + "  id, id_tnt, ruta, usuario_creacion, fecha_creacion, tipo, content_type,nombre, id_respuesta_dig)"
                    + "    VALUES (?,?, ?, ?, CURRENT_DATE, ?, ?, ?, ?)")
                    .setParameter(1, adjunto.getId())
                    .setParameter(2, tpm.getCurrentTenant())
                    .setParameter(3, adjunto.getRuta())
                    .setParameter(4, adjunto.getUsuarioCreacion())
                    .setParameter(5, adjunto.getTipo())
                    .setParameter(6, adjunto.getContentType())
                    .setParameter(7, adjunto.getNombre())
                    .setParameter(8, idRespuestaDigitada)
                    .executeUpdate();
            return adjunto;
        } catch (Exception exp) {
            exp.printStackTrace();
            throw new ErrorGeneral("Error", exp.getMessage());
        }
    }

    private Long obtenerIdAdjuntos() {
        try {
            Object valor = tpm.getEm().createNativeQuery(UtilProperties.getProperties().getProperty("obtenerIdAdjuntos")).getSingleResult();
            return UtilConsulta.longValueOf(valor);
        } catch (Exception exp) {
            throw new ErrorGeneral("Error Insertando Elementos", exp.getMessage());
        }
    }

    private Long obtenerIdRespuestasDigitadas() {
        try {
            Object valor = tpm.getEm().createNativeQuery(UtilProperties.getProperties().getProperty("obtenerIdRespuestasDigitadas")).getSingleResult();
            return UtilConsulta.longValueOf(valor);
        } catch (Exception exp) {
            throw new ErrorGeneral("Error Insertando Elementos", exp.getMessage());
        }
    }

    private Long obtenerIdElementos() {
        try {
            Object valor = tpm.getEm().createNativeQuery(UtilProperties.getProperties().getProperty("obtenerIdElementos")).getSingleResult();
            return UtilConsulta.longValueOf(valor);
        } catch (Exception exp) {
            throw new ErrorGeneral("Error Insertando Elementos", exp.getMessage());
        }
    }

    private Long obtenerIdInstrumentoDig() {
        try {
            Object valor = tpm.getEm().createNativeQuery(UtilProperties.getProperties().getProperty("obtenerIdInstrumentoDig")).getSingleResult();
            return UtilConsulta.longValueOf(valor);
        } catch (Exception exp) {
            throw new ErrorGeneral("Error Insertando Elementos", exp.getMessage());
        }
    }

    @Override
    public List<Elemento> obtenerElementoPorIdInstrumentoDigitado(String idInstrumentoDigitado) {
        List<Elemento> elemento = new ArrayList<Elemento>();
        try {
            elemento = tpm.getEm().createNamedQuery("Elemento.findByInstrumentoId")
                    .setParameter("idInstrumentoDigitado", Long.valueOf(idInstrumentoDigitado))
                    .setHint(QueryHints.CACHE_USAGE, CacheUsage.DoNotCheckCache)
                    .getResultList();
        } catch (Exception ex) {
            throw new ErrorGeneral("Error", ex.getMessage());
        }
        return elemento;
    }

    @Override
    public List<Elemento> obtenerElementoPorIdInstrumentoDigitado(Long idInstrumentoDigitado) {
        List<Elemento> elemento = new ArrayList<Elemento>();
        try {
            elemento = tpm.getEm().createNamedQuery("Elemento.findByInstrumentoId")
                    .setParameter("idInstrumentoDigitado", idInstrumentoDigitado)
                    .setHint(QueryHints.CACHE_USAGE, CacheUsage.DoNotCheckCache)
                    .getResultList();
        } catch (Exception ex) {
            throw new ErrorGeneral("Error", ex.getMessage());
        }
        return elemento;
    }

    @Override
    public List<RespuestaDig> obtenerRespuestasDigitadasPorElementoId(Long idElemento) {
        List<RespuestaDig> respuestasDigitadas = new ArrayList<RespuestaDig>();
        try {
            respuestasDigitadas = tpm.getEm().createNamedQuery("RespuestasDigitadas.findByElementoId")
                    .setParameter("idElemento", Long.valueOf(idElemento))
                    .setHint(QueryHints.CACHE_USAGE, CacheUsage.DoNotCheckCache)
                    .getResultList();
        } catch (Exception ex) {
            throw new ErrorGeneral("Error", ex.getMessage());
        }
        return respuestasDigitadas;
    }

    @Override
    public List<RespuestaDig> obtenerRespuestasDigitadasPorElemento(Elemento elemento) {
        List<RespuestaDig> respuestasDigitadas = new ArrayList<RespuestaDig>();
        try {
            respuestasDigitadas = tpm.getEm().createNamedQuery("RespuestasDigitadas.findByElementoInstrumentoDescripcion")
                    .setParameter("instrumento", elemento.getInstrumentoDigitado())
                    .setParameter("elementoDescripcion", elemento.getDescripcion())
                    .setHint(QueryHints.CACHE_USAGE, CacheUsage.DoNotCheckCache)
                    .getResultList();
        } catch (Exception ex) {
            throw new ErrorGeneral("Error", ex.getMessage());
        }
        return respuestasDigitadas;
    }

    @Override
    public List<Adjunto> obtenerAdjuntosPorRespuestaDigitada(Long idRespuestaDigitada) {
        List<Adjunto> adjuntos = new ArrayList<Adjunto>();
        try {
            if (!UtilCadena.isNullOVacio(idRespuestaDigitada)) {
                adjuntos = tpm.getEm().createNamedQuery("Adjunto.findByIdRespuestaDigitada")
                        .setParameter("idRespuestaDigitada", Long.valueOf(idRespuestaDigitada))
                        .setHint(QueryHints.CACHE_USAGE, CacheUsage.DoNotCheckCache)
                        .getResultList();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            //throw new ErrorGeneral("Error", ex.getMessage());
        }
        return adjuntos;
    }

    @Override
    public List<Adjunto> obtenerAdjuntosPorIdInstrumentoRespuesta(Long idInstrumento, String codPregunta) {
        List<Adjunto> adjuntos = new ArrayList<Adjunto>();
        try {
            adjuntos = tpm.getEm().createNamedQuery("Adjunto.findByIdInstrumentoCodigoRespuesta")
                    .setParameter("idInstrumento", idInstrumento)
                    .setParameter("codPregunta", codPregunta)
                    .setHint(QueryHints.CACHE_USAGE, CacheUsage.DoNotCheckCache)
                    .getResultList();
        } catch (Exception ex) {
            throw new ErrorGeneral("Error", ex.getMessage());
        }
        return adjuntos;
    }

    @Override
    public List<BigDecimal> obtenerInstrumentosDigPorSede(Long idSede) {
        StringBuilder sql = new StringBuilder();

        sql.append("select x.id from dig_instrumentos x");
        sql.append(" inner join est_sedes sede on sede.id=?idSede");
        sql.append(" inner join est_pre_sed sp ON sede.id = sp.id_sede");

        sql.append(" inner join ind_auxiliar_indicador aux on aux.id_predio=x.id_Predio");
        sql.append(" inner join ind_auxiliar_consultas n on n.id_predio = aux.id_predio and n.periodo = aux.periodo and n.id_tnt = aux.id_tnt");

        sql.append(" where ");

        sql.append(" x.id_Predio=sp.id_predio and x.id_tnt = ?id_tnt");
        sql.append(" and x.version = (select max(xx.version) from dig_instrumentos xx where x.id_Predio=xx.id_Predio and xx.estado=?estado)");
        sql.append(" and x.estado=?estado");


        Query query = tpm.getEm().createNativeQuery(sql.toString());
        query.setParameter("id_tnt", tpm.getCurrentTenant());
        query.setParameter("estado", EstadoInstrumento.E.toString());
        query.setParameter("idSede", idSede);

        return query.getResultList();
    }

    @Override
    public List<BigDecimal> obtenerInstrumentosDigPorEstablecimiento(Long idEstablecimiento) {
        StringBuilder sql = new StringBuilder();

        sql.append("select x.id from dig_instrumentos x");
        sql.append(" inner join est_sedes sede on sede.id_establecimiento=?idEstablecimiento");
        sql.append(" inner join est_pre_sed sp ON sede.id = sp.id_sede");

        sql.append(" inner join ind_auxiliar_indicador aux on aux.id_predio=x.id_Predio");
        sql.append(" inner join ind_auxiliar_consultas n on n.id_predio = aux.id_predio and n.periodo = aux.periodo and n.id_tnt = aux.id_tnt");

        sql.append(" where ");

        sql.append(" x.id_Predio=sp.id_predio and x.id_tnt = ?id_tnt");
        sql.append(" and x.version = (select max(xx.version) from dig_instrumentos xx where x.id_Predio=xx.id_Predio and xx.estado=?estado)");
        sql.append(" and x.estado=?estado");


        Query query = tpm.getEm().createNativeQuery(sql.toString());
        query.setParameter("id_tnt", tpm.getCurrentTenant());
        query.setParameter("estado", EstadoInstrumento.E.toString());
        query.setParameter("idEstablecimiento", idEstablecimiento);

        return query.getResultList();
    }

    @Override
    public void eliminarElemento(Elemento elemento) {
        try {
            Elemento elementoEliminado = this.obtenerElementoPorId(elemento);
            if (elementoEliminado != null) {
                this.limpiarAdjuntos(elementoEliminado.getId());
                this.limpiarRespuestasDig(elementoEliminado.getId());
                this.limpiarElemento(elementoEliminado.getId());
            }
        } catch (Exception ex) {
            throw new ErrorGeneral("Error", ex.getMessage());
        }
    }

    @Override
    public Elemento obtenerElementoPorId(Elemento elemento) {
        try {
            return (Elemento) tpm.getEm().createNamedQuery("Elemento.findById")
                    .setParameter("idElemento", elemento.getId())
                    .setHint(QueryHints.CACHE_USAGE, CacheUsage.DoNotCheckCache)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } catch (Exception exp) {
            throw new ErrorGeneral("Error", exp.getMessage());
        }
    }

    @Override
    public void anularInstrumento(InstrumentoDig instrumento) {
        try {
            tpm.getEm().createNativeQuery("UPDATE dig_instrumentos"
                    + "   SET  "
                    + "       fecha_anulacion = CURRENT_DATE,"
                    + "       usuario_anulacion = ?, "
                    + "       motivo_anulacion = ?, "
                    + "       estado = ?"
                    + " WHERE id = ? ")
                    .setParameter(1, instrumento.getUsuarioAnulacion())
                    .setParameter(2, instrumento.getMotivoAnulacion())
                    .setParameter(3, instrumento.getEstado().name())
                    .setParameter(4, instrumento.getId())
                    .executeUpdate();
        } catch (Exception exp) {
            exp.printStackTrace();
            throw new ErrorGeneral("Error Anulando Instrumentos", exp.getMessage());
        }
    }
    
    @Override
    public void actualizarFechaUsuarioModificacion(InstrumentoDig instrumento,String usuario) {
        try {
            tpm.getEm().createNativeQuery("UPDATE dig_instrumentos"
                    + "   SET  "
                    + "       fecha_actualizacion = CURRENT_DATE,"
                    + "       usuario_actualizacion = ? "
                    + " WHERE id = ? ")
                    .setParameter(1, usuario)
                    .setParameter(2, instrumento.getId())
                    .executeUpdate();
        } catch (Exception exp) {
            exp.printStackTrace();
            throw new ErrorGeneral("Error Anulando Instrumentos", exp.getMessage());
        }
    }

    @Override
    public void cambiarEstadoNuevo(Long idInstrumento) {
        try {
            tpm.getEm().createNativeQuery("UPDATE dig_instrumentos"
                    + "   SET  "
                    + "       estado = ?"
                    + " WHERE id = ? ")
                    .setParameter(1, EstadoInstrumento.N.name())
                    .setParameter(2, idInstrumento)
                    .executeUpdate();
        } catch (Exception exp) {
            exp.printStackTrace();
            throw new ErrorGeneral("Error Abriendo Instrumentos", exp.getMessage());
        }
    }

    @Override
    public boolean existeInstrumentoRepetido(Sede sede, Establecimiento establecimiento, Predio predio, int periodo) {
        boolean sw = false;
        try {
            Object o = (InstrumentoDig) tpm.getEm().createNamedQuery("Instrumento.findRepetido")
                    .setParameter("sede", sede)
                    .setParameter("establecimiento", establecimiento)
                    .setParameter("predio", predio)
                    .getSingleResult();
            if (!UtilCadena.isNullOVacio(o)) {
                sw = true;
            }
        } catch (NoResultException ex) {
            sw = false;
        } catch (Exception exp) {
            throw new ErrorGeneral("Error", exp.getMessage());
        }
        return sw;
    }

    @Override
    public EstadoInstrumento estadoInstrumentoRepetido(Sede sede, Establecimiento establecimiento, Predio predio) {
        try {
            EstadoInstrumento estadoInstrumento = (EstadoInstrumento) tpm.getEm().createNamedQuery("Instrumento.estadoInstrumentoRepetido")
                    .setParameter("sede", sede)
                    .setParameter("establecimiento", establecimiento)
                    .setParameter("predio", predio)
                    .getSingleResult();
            return estadoInstrumento;
        } catch (NoResultException ex) {
            return null;
        } catch (Exception exp) {
            throw new ErrorGeneral("Error", exp.getMessage());
        }
    }

    @Override
    public List<String> emitirInstrumento(InstrumentoDig ins, String userName) {
        try {
            List<String> list = new ArrayList<String>();
            if (validarRespEmitidas(ins.getId().intValue())) {
                emitirRespuestas(ins, userName);
            } else {
                list = getRespNoEmitidas(ins.getId().intValue());
            }
            return list;
        } catch (Exception exp) {
            //exp.printStackTrace();
            throw new ErrorProcedimientoBD("Error Proc. Emite Instrumento", exp.getMessage());
        }
    }

    private Boolean validarRespEmitidas(Integer instrumento) throws Exception {
        Connection conn = null;
        CallableStatement cs = null;
        int result = 0;
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("jdbc/DBCierDS");
            conn = ds.getConnection();
            cs = conn.prepareCall(UtilProperties.getProperties().getProperty("validarRespEmitidas"));
            cs.registerOutParameter(1, Types.INTEGER);
            cs.setInt(2, instrumento);
            //System.out.println("Ejecución del procedimiento");
            cs.execute();
            result = cs.getInt(1);
            //System.out.println("resp: " + result);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (cs != null) {
                cs.close();
            }
        }
        return result == 0 ? false : true;
    }

    private List<String> getRespNoEmitidas(Integer inst) throws Exception {
        Connection conn = null;
        CallableStatement cs = null;
        List<String> result = new ArrayList<String>();
        try {
            String proc = UtilProperties.getProperties().getProperty("getRespNoEmitidas");
            if (ParamBaseDatos.ORACLE.equals(UtilProperties.getDbMotor())) {
                Context ctx = new InitialContext();
                DataSource ds = (DataSource) ctx.lookup("jdbc/DBCierDS");
                conn = ds.getConnection();
                cs = conn.prepareCall(proc);
                cs.setInt(1, inst);
                cs.registerOutParameter(2, Types.ARRAY, "EMISIONLIST");
                cs.execute();
                Object[] data = (Object[]) ((Array) cs.getObject(2)).getArray();
                for (Object tmp : data) {
                    String row = (String) tmp;
                    result.add(row);
                }
            } else {
                result = tpm.getEm().createNativeQuery(proc)
                        .setParameter("instrumento", inst)
                        .getResultList();
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (cs != null) {
                cs.close();
            }
        }
        return result;
    }

    private void emitirRespuestas(InstrumentoDig ins, String userName) throws Exception {
        Connection conn = null;
        CallableStatement cs = null;
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("jdbc/DBCierDS");
            conn = ds.getConnection();

            cs = conn.prepareCall(UtilProperties.getProperties().getProperty("emitirResp"));
            if (ParamBaseDatos.ORACLE.equals(UtilProperties.getDbMotor())) {
                cs.registerOutParameter(1, Types.VARCHAR);
                cs.setInt(2, ins.getId().intValue());
                cs.setString(3, userName);
                cs.setInt(4, ins.getPeriodoEmision());
            } else {
                cs.setInt(1, ins.getId().intValue());
                cs.setString(2, userName);
            }
            //Ejecución del procedimiento
            cs.execute();
        } catch (Exception e) {
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (cs != null) {
                cs.close();
            }
        }
    }

    @Override
    public Long crearNovedad(Long idInstrumento, String username) {
        Connection conn = null;
        CallableStatement cs = null;
        Long result = -1L;
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("jdbc/DBCierDS");
            conn = ds.getConnection();

            cs = conn.prepareCall(UtilProperties.getProperties().getProperty("crearNovedad").trim());
            cs.registerOutParameter(1, Types.INTEGER);
            cs.setLong(2, idInstrumento);
            cs.setString(3, username);
            cs.execute();
            int valor = cs.getInt(1);
            result = new Long(valor);
            cs.close();
        } catch (Exception exp) {
            throw new ErrorGeneral("Error Creando Novedad", exp.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (cs != null) {
                    cs.close();
                }
            } catch (SQLException ex) {
                throw new ErrorGeneral("Error Cerrando conn ", ex.getMessage());
            }
        }
        return result;
    }

    @Override
    public RespuestaDig obtenerRespuestaDigitadaPorIdInstrumentoCodigoRespuesta(Long idInstrumento, String codigoRespuesta) {
        try {
            return (RespuestaDig) tpm.getEm().createNamedQuery("RespuestaDig.findRespDigIdInstCodResp")
                    .setParameter("codigoRespuesta", codigoRespuesta)
                    .setParameter("idInstrumento", idInstrumento)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } catch (Exception exp) {
            throw new ErrorGeneral("Error", exp.getMessage());
        }
    }

    @Override
    public RespuestaDig obtenerRespuestaDigitada(Elemento elemento, String codigoPregunta, String codigoRespuesta) {
        try {
            return (RespuestaDig) tpm.getEm().createNamedQuery("RespuestaDig.findByElementoCodpreguntaCodrespuesta")
                    .setParameter("elemento", elemento)
                    .setParameter("codigoPregunta", codigoPregunta)
                    .setParameter("codigoRespuesta", codigoRespuesta)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } catch (Exception exp) {
            throw new ErrorGeneral("Error", exp.getMessage());
        }
    }

    @Override
    public RespuestaDig guardarRespuestaDigitadaAdjunto(RespuestaDig respuesta) {
        Long idRespDig = respuesta.getId();
        if (respuesta.getId() == null) {
            idRespDig = this.obtenerIdRespuestasDigitadas();
            respuesta.setId(idRespDig);
            this.insertarRespuestaDigitada(idRespDig, respuesta.getElemento().getId(), respuesta.getPregunta().getCodigo(), respuesta.getRespuesta().getCodigo(), "1");
        } else {
            this.actualizarRespuestaDigitada(respuesta.getId(), respuesta.getValor());
        }
        //Guardo Adjuntos
        if (respuesta.getAdjuntosList() != null && !respuesta.getAdjuntosList().isEmpty()) {
            for (Adjunto adjunto : respuesta.getAdjuntosList()) {
                if (adjunto.getId() == null) {
                    this.insertarRespuestaDigitadaAjunto(idRespDig, adjunto);
                }
            }
        }
        return respuesta;
    }

    @Override
    public List<RespuestaDig> obtenerRespuestasDigitadasPorTipoElementoCodigoPregunta(TipoElemento tipoElemento, String codigoPregunta, InstrumentoDig instrumento) {
        List<RespuestaDig> respuestasDigitadas = new ArrayList<RespuestaDig>();
        try {
            respuestasDigitadas = tpm.getEm().createNamedQuery("RespuestaDig.findByTipoElementoCodPreguntaIdInstrumento")
                    .setParameter("tipoElemento", tipoElemento)
                    .setParameter("codigoPregunta", codigoPregunta)
                    .setParameter("idInstrumento", instrumento.getId())
                    .getResultList();
        } catch (Exception ex) {
            throw new ErrorGeneral("Error", ex.getMessage());
        }
        return respuestasDigitadas;
    }

    @Override
    public boolean idElementoRepetido(TipoElemento tipoElemento, String codigoPregunta, String idEdificio, Elemento elementoSeleccionado) {
        for (RespuestaDig respuesta : this.obtenerRespuestasDigitadasPorTipoElementoCodigoPregunta(tipoElemento, codigoPregunta, elementoSeleccionado.getInstrumentoDigitado())) {
            if (idEdificio.equals(respuesta.getValor())) {
                if (!elementoSeleccionado.equals(respuesta.getElemento())) {
                    return Boolean.TRUE;
                }
            }
        }
        return Boolean.FALSE;
    }

    @Override
    public List<Respuesta> obtenerRespuestaPrincipales(String codigoInstrumentoVigente) {
        List<Respuesta> respuestasPrincipales = new ArrayList<Respuesta>();
        TipoElemento predio = buscarTipoElementoPorCodigo("ELE_PRED");
        TipoElemento establecimiento = buscarTipoElementoPorCodigo("ELE_EST");
        try {
            respuestasPrincipales = tpm.getEm().createNamedQuery("Respuesta.findPrincipalesPredioEstablecimiento")
                    .setParameter("codigoInstrumento", codigoInstrumentoVigente)
                    .setParameter("tipoPredio", predio)
                    .setParameter("tipoEstablecimiento", establecimiento).getResultList();
        } catch (Exception ex) {
            throw new ErrorGeneral("Error", ex.getMessage());
        }
        return respuestasPrincipales;
    }
    
    @Override
    public List<Respuesta> obtenerRespuestaObligatorias() {
        List<Respuesta> respuestasPrincipales = new ArrayList<Respuesta>();
        try {
            respuestasPrincipales = tpm.getEm().createNamedQuery("Respuesta.findRespuestasObligatorias").getResultList();
        } catch (Exception ex) {
            throw new ErrorGeneral("Error", ex.getMessage());
        }
        return respuestasPrincipales;
    }

    @Override
    public List<Respuesta> obtenerRespuestaDependiente(String codigoRespuesta) {
        List<Respuesta> respuestasDependientes = new ArrayList<Respuesta>();
        try {
            respuestasDependientes = tpm.getEm().createNamedQuery("Respuesta.findDependientes").setParameter("codigoRespuesta", codigoRespuesta).getResultList();
        } catch (Exception ex) {
            throw new ErrorGeneral("Error", ex.getMessage());
        }
        return respuestasDependientes;
    }

    @Override
    public List<Respuesta> obtenerRespuestaPrincipales(TipoElemento tipo, String codigoInstrumento) {
        List<Respuesta> respuestasDependientes = new ArrayList<Respuesta>();
        try {
            respuestasDependientes = tpm.getEm().createNamedQuery("Respuesta.findPrincipalesTipo")
                    .setParameter("codigoInstrumento", codigoInstrumento)
                    .setParameter("tipoElemento", tipo).getResultList();
        } catch (Exception ex) {
            throw new ErrorGeneral("Error", ex.getMessage());
        }
        return respuestasDependientes;
    }

    @Override
    public List<Respuesta> obtenerRespuestaPorFila(Respuesta respuesta) {
        List<Respuesta> respuestas = new ArrayList<Respuesta>();
        try {
            respuestas = tpm.getEm().createNamedQuery("Respuesta.findByFila")
                    .setParameter("fila", respuesta.getFila())
                    .setParameter("codigoRespuesta", respuesta.getCodigo())
                    .setMaxResults(3).getResultList();
        } catch (Exception ex) {
            throw new ErrorGeneral("Error", ex.getMessage());
        }
        return respuestas;
    }

    @Override
    public boolean tieneRepuestasDigitadasPorModulo(String codModulo, InstrumentoDig instrumento) {
        boolean tieneRespuestas = true;
        int count = 0;
        try {
            try {
                List<RespuestaDig> respuestas = tpm.getEm().createNamedQuery("RespuestaDig.findByModuloInstrumento")
                        .setParameter("instrumentoDigitado", instrumento)
                        .setParameter("codigoModulo", codModulo)
                        .getResultList();
                count = respuestas.size();
                if (codModulo.equals("MODULOB1")) {
                    for (RespuestaDig r : respuestas) {
                        if (r.getValor().equals(SiNo.No.getValorEntero() + "")
                                && (r.getRespuesta().getCodigo().equals("RESP_208")
                                || r.getRespuesta().getCodigo().equals("RESP_219")
                                || r.getRespuesta().getCodigo().equals("RESP_220")
                                || r.getRespuesta().getCodigo().equals("RESP_225")
                                || r.getRespuesta().getCodigo().equals("RESP_226")
                                || r.getRespuesta().getCodigo().equals("RESP_227"))) {
                            count--;
                        }
                    }
                } else if (codModulo.equals("MODULOC1")) {
                    for (RespuestaDig r : respuestas) {
                        if (r.getValor().equals(SiNo.No.getValorEntero() + "")
                                && (r.getRespuesta().getCodigo().equals("RESP_238_01")
                                || r.getRespuesta().getCodigo().equals("RESP_239_01"))) {
                            count--;
                        } else if (r.getValor().equals(Verificable.VER.getValor() + "")
                                && (r.getRespuesta().getCodigo().equals("RESP_238_02")
                                || r.getRespuesta().getCodigo().equals("RESP_239_02"))) {
                            count--;
                        }
                    }
                }
                if (count < 1) {
                    tieneRespuestas = false;
                }
            } catch (NoResultException ex) {
                ex.printStackTrace();
                tieneRespuestas = false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return tieneRespuestas;
    }

    @Override
    public List<Instrumento> consultarInstrumentosConfiguracion(String codigo, EstadoInstrumentoFormato estado) {
        List<Instrumento> resultado = new ArrayList<Instrumento>();
        try {
            StringBuilder consulta = new StringBuilder();
            consulta.append("Select i from Instrumento i where i.estado = :estado");
            if (!UtilCadena.isNullOVacio(codigo)) {
                consulta.append(" and i.codigo = :codigo");
            }
            Query query = tpm.getEm().createQuery(consulta.toString());
            query.setParameter("estado", estado);
            if (!UtilCadena.isNullOVacio(codigo)) {
                query.setParameter("codigo", codigo);
            }
            resultado = query.getResultList();
        } catch (Exception ex) {
            throw new ErrorGeneral("Error", ex.getMessage());
        }
        return resultado;
    }

    public int obtenerUltimaVersionInstrumento(InstrumentoDig instrumento) {
        int version = 0;
        try {
            version = ((Number) tpm.getEm().createNamedQuery("Instrumento.ultimaVersion")
                    .setParameter("predio", instrumento.getPredio())
                    .getSingleResult())
                    .intValue();
        } catch (NoResultException ex) {
            version = 1;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return version;
    }

    /*public void eliminarAdjunto(Adjunto adjunto){
     try{
     tpm.getEm().createNativeQuery("delete from dig_adjuntos where id = ?")
     .setParameter(1, adjunto.getId()).executeUpdate();
     } catch (Exception ex) {
     ex.printStackTrace();
     }
     }*/
    @Override
    public void guardarElementoObservacion(Elemento elemento) {
        try {
            if (elemento.getRespuestasDigitadasIniciales().size() > 0) {
                try {
                    Long id = obtenerIdElementos();
                    this.insertarElemento(id, elemento.getDescripcion(), elemento.getTipoElemento().getCodigo(), elemento.getInstrumentoDigitado().getId(), elemento.getEstablecimiento().getId(), elemento.getSede().getId());
                    elemento.setId(id);

                } catch (Exception ex) {
                    ex.printStackTrace();
                    throw new ErrorGeneral("Error Insertando Observacion", ex.getMessage());
                }
                //Guardo Respuestas Digitadas.
                //Persisto todas las respuestas digitadas del elemento.
                for (RespuestaDig rspDig : elemento.getRespuestasDigitadasIniciales()) {
                    Long idRespDig = this.obtenerIdRespuestasDigitadas();
                    this.insertarRespuestaDigitada(idRespDig, elemento.getId(), rspDig.getPregunta().getCodigo(), rspDig.getRespuesta().getCodigo(), rspDig.getValor());
                }
            }
        } catch (Exception exp) {
            exp.printStackTrace();
            throw new ErrorGeneral("Error", exp.getMessage());
        }
    }

    @Override
    public RespuestaDig obtenerRespuestaDigitadaPorId(Long idRespuestaDigitada) {
        try {
            //EntityManager em = tpm.getEm();
            return (RespuestaDig) tpm.getEm().createNamedQuery("RespuestDig.findById")
                    .setParameter("id", idRespuestaDigitada)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } catch (Exception exp) {
            exp.printStackTrace();
            throw new ErrorGeneral("Error", exp.getMessage());
        }
    }

    @Override
    public void eliminarRespuestaDigitadaId(Long idRespuesta) {
        try {
            //EntityManager em = tpm.getEm();
            tpm.getEm().createNativeQuery("delete from dig_respuestas where id = ?")
                    .setParameter(1, idRespuesta).executeUpdate();
        } catch (Exception exp) {
            throw new ErrorGeneral("Error", exp.getMessage());
        }
    }

    @Override
    public void eliminarElementoDigitadoId(Long idElemento) {
        try {
            //EntityManager em = tpm.getEm();
            tpm.getEm().createNativeQuery("delete from dig_elementos where id = ?")
                    .setParameter(1, idElemento).executeUpdate();
        } catch (Exception exp) {
            throw new ErrorGeneral("Error", exp.getMessage());
        }
    }

    @Override
    public void actualizarRespuestaDigitadaId(Long idRespuesta, String valor) {
        try {
            //EntityManager em = tpm.getEm();
            RespuestaDig respuesta = tpm.getEm().find(RespuestaDig.class, idRespuesta);
            respuesta.setValor(valor);
            tpm.getEm().merge(respuesta);
            tpm.getEm().flush();
        } catch (Exception exp) {
            throw new ErrorGeneral("Error", exp.getMessage());
        }
    }

    @Override
    public List<InstrumentoDig> consultaInstrumentoPredioSedeEstablecimiento(InstrumentoDig instrumento) {
        List<InstrumentoDig> resultado = new ArrayList<InstrumentoDig>();
        try {
            //EntityManager em = tpm.getEm();
            resultado = tpm.getEm().createNamedQuery("InstrumentoDigitado.consultaPorPredioSedeEstablecimiento")
                    .setParameter("predio", instrumento.getPredio())
                    .setParameter("sede", instrumento.getSede())
                    .setParameter("establecimiento", instrumento.getEstablecimiento()).getResultList();
        } catch (Exception ex) {
            throw new ErrorGeneral("Error", ex.getMessage());
        }
        return resultado;
    }

    @Override
    public void eliminarAdjunto(Adjunto adjunto) {
        try {
            //EntityManager em = tpm.getEm();
            if (adjunto.getId() != null) {
                Adjunto eliminado = tpm.getEm().find(Adjunto.class, adjunto.getId());
                //Systtpm.getEm().out.print("----->"+eliminado);
                if (eliminado != null) {
                    tpm.getEm().remove(eliminado);
                    tpm.getEm().flush();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorIntegridad("No se pudo quitar el permiso");
        }
    }

    @Override
    public void actualizarRespuestaDigitada(RespuestaDig respuesta) {
        try {
            //EntityManager em = tpm.getEm();
            tpm.getEm().merge(respuesta);
            tpm.getEm().flush();
        } catch (Exception e) {
            throw new ErrorIntegridad("error al modificar Respuesta Digitada");
        }
    }

    @Override
    public RespuestaDig crearRespuestaDigitadaPorIdInstrumentoCodigoRespuesta(Long idInstrumento, String codigoRespuesta, String descripcionElemento) {
        try {
            RespuestaDig rd = new RespuestaDig();
            rd.setIdInstrumentoDigitado(idInstrumento);
            Respuesta respuesta = obtenerRespuestaPorCodigo(codigoRespuesta);
            rd.setRespuesta(respuesta);
            rd.setPregunta(respuesta.getCodPreguntas());
            Elemento elemento = obtenerElementoPorIdInstrumentoDigitadoTipoElemento(idInstrumento, descripcionElemento);
            rd.setElemento(elemento);
            rd.setValor("0");
            //EntityManager em = tpm.getEm();            
            tpm.getEm().persist(rd);
            tpm.getEm().flush();
            return rd;
        } catch (NoResultException ex) {
            return null;
        } catch (Exception exp) {
            exp.printStackTrace();
            throw new ErrorGeneral("Error", exp.getMessage());
        }
    }

    @Override
    public Elemento obtenerElementoPorIdInstrumentoDigitadoTipoElemento(Long idInstrumento, String tipoElemento) {
        try {
            //EntityManager em = tpm.getEm();
            return (Elemento) tpm.getEm().createNamedQuery("Elemento.findByIdInstrumentoTipoElemento")
                    .setParameter("idInstrumento", idInstrumento)
                    .setParameter("codigoTipoElemento", tipoElemento)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } catch (Exception exp) {
            throw new ErrorGeneral("Error", exp.getMessage());
        }
    }

    @Override
    public String obtenerDescripcionElemento(Long idInstrumento, String tipoElemento, String descripcion) {
        List<Elemento> elementos = new ArrayList<Elemento>();
        try {
            elementos = tpm.getEm().createNamedQuery("Elemento.findByIdInstrumentoTipoElementoListado")
                    .setParameter("idInstrumento", idInstrumento)
                    .setParameter("codigoTipoElemento", tipoElemento)
                    .getResultList();
        } catch (NoResultException ex) {
            return null;
        } catch (Exception exp) {
            throw new ErrorGeneral("Error", exp.getMessage());
        }
        int numero = 1;
        int posList = 0;
        boolean sw = true;
        Integer[] numeros = new Integer[1000];
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
        return descripcion + "_" + numero;
    }

    @Override
    public boolean tieneRespuestaValido(Long idInstrumento, String codigoRespuesta, String idNuevoElemento) {
        List<String> idsElementos = new ArrayList<String>();
        try {
            //EntityManager em = tpm.getEm();
            idsElementos = tpm.getEm().createNamedQuery("RespuestDig.findValorByIdInstrumentoRespuesta")
                    .setParameter("instrumentoDigitado", idInstrumento)
                    .setParameter("codigoRespuesta", codigoRespuesta)
                    .getResultList();
        } catch (NoResultException ex) {
            ex.printStackTrace();
        } catch (Exception exp) {
            throw new ErrorGeneral("Error", exp.getMessage());
        }
        if (idsElementos != null && !idsElementos.isEmpty()) {
            for (String idElemento : idsElementos) {
                if (idElemento.equalsIgnoreCase(idNuevoElemento)) {
                    return true;
                }

            }
        }
        return false;
    }
    
    @Override
    public boolean tieneRespuestaCorrespondiente(InstrumentoDig instrumento, String idEdificio, String idEspacio, String idPiso) {
        try {
            List<Elemento> elementos = this.obtenerElementosPorCodigoInstrumento(instrumento, "ELE_ESP");
            List<Object> respuestas = new ArrayList<Object>();
            for (Elemento espacio : elementos) {
                try {
                    respuestas = tpm.getEm().createNativeQuery("select id from dig_respuestas where"
                            + "(( cod_respuesta = 'RESP_094' AND valor = ? ) "
                            + "or(cod_respuesta = 'RESP_095' AND valor = ? ) "
                            + "or(cod_respuesta = 'RESP_096' AND valor = ?)) "
                            + "AND id_dig_elemento = ?")
                            .setParameter(1, idEspacio)
                            .setParameter(2, idEdificio)
                            .setParameter(3, idPiso)
                            .setParameter(4, espacio.getId()).getResultList();
                    if (!respuestas.isEmpty() && respuestas.size() == 3) {
                        return true;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } catch (Exception exp) {
            exp.getMessage();
        }
        return false;
    }
    
    @Override
    public InstrumentoDig consultaInstrumentoPorBoletaCodPredio(String boleta, String codigoPredio){
          try {
            return (InstrumentoDig) tpm.getEm().createNamedQuery("InstrumentoDigitado.findByCodPredioBoleta")
                    .setParameter("boletaCensal", boleta)
                    .setParameter("codigoPredio", codigoPredio)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } catch (Exception exp) {
            throw new ErrorGeneral("Error", exp.getMessage());
        }
     }
    
    /**
     *
     * @param elemento
     * @return
     */
    @Override
    public Elemento guardarElementoDigitadoArchivo(Elemento elemento) {
        try {
            Long id = obtenerIdElementos();
            this.insertarElemento(id, elemento.getDescripcion(), elemento.getTipoElemento().getCodigo(), elemento.getInstrumentoDigitado().getId(), elemento.getSede().getId(), elemento.getEstablecimiento().getId());
            elemento.setId(id);
            for (RespuestaDig rspDig : elemento.getRespuestasList()) {
                Long idRespDig = this.obtenerIdRespuestasDigitadas();
                this.insertarRespuestaDigitada(idRespDig, elemento.getId(), rspDig.getPregunta().getCodigo(), rspDig.getRespuesta().getCodigo(), rspDig.getValor());
            }
            return elemento;
        } catch (Exception exp) {
            exp.printStackTrace();
            throw new ErrorGeneral("Error", exp.getMessage());
        }
    }
    
    
    /**
     *
     * @param elemento
     * @return
     */
    @Override
    public Elemento guardarElementoDigitadoArchivoVerificado(Elemento elemento) {
        try {
            Elemento elementoQuery = obtenerElementoPorInstrumentoDescripcion(elemento);
            if(elementoQuery == null){
                Long id = obtenerIdElementos();
                this.insertarElemento(id, elemento.getDescripcion(), elemento.getTipoElemento().getCodigo(), elemento.getInstrumentoDigitado().getId(), elemento.getSede().getId(), elemento.getEstablecimiento().getId());
                elemento.setId(id);
                for (RespuestaDig rspDig : elemento.getRespuestasList()) {
                    Long idRespDig = this.obtenerIdRespuestasDigitadas();
                    this.insertarRespuestaDigitada(idRespDig, elemento.getId(), rspDig.getPregunta().getCodigo(), rspDig.getRespuesta().getCodigo(), rspDig.getValor());
                }                
            }else{
                Long idElemento = elementoQuery.getId();
                elemento.setId(idElemento);
                for (RespuestaDig rspDig : elemento.getRespuestasList()) {
                    Long idRespDig = this.obtenerIdRespuestasDigitadas();
                    this.insertarRespuestaDigitada(idRespDig, idElemento, rspDig.getPregunta().getCodigo(), rspDig.getRespuesta().getCodigo(), rspDig.getValor());
                }
            }
            return elemento;    
        } catch (Exception exp) {
            //exp.printStackTrace();
            throw new ErrorGeneral("Error", exp.getMessage());
        }
    }
    
    /**
     * 
     * @return 
     */
    public String obtenerMaximaBoletaCensal(){
        String boleta="";
        try{
           Object valor = tpm.getEm().createNativeQuery(UtilProperties.getProperties().getProperty("obtenerBoletaCensal")).getSingleResult();
           boleta =valor.toString();
        } catch (NoResultException ex) {
            return null;
        } catch (Exception exp) {
            throw new ErrorGeneral("Error", exp.getMessage());
        }
        return boleta;
         
    }
    
    @Override
    public List<Respuesta> obtenerRespuestaParaCompletar(String codigoTipoElemento) {
        List<Respuesta> respuestas = new ArrayList<Respuesta>();
        try {
            respuestas = tpm.getEm().createNamedQuery("Respuesta.consultaPorTipoElementoTipoRespuesta").setParameter("codigoTipoElemento", codigoTipoElemento).getResultList();
        } catch (Exception ex) {
            throw new ErrorGeneral("Error", ex.getMessage());
        }
        return respuestas;
    }
    
    @Override
    public List<Respuesta> obtenerRespuestasDependientesByTipoElemento(String elemento) {
        List<Respuesta> respuestas = new ArrayList<Respuesta>();
        try {
            respuestas = tpm.getEm().createNamedQuery("Respuesta.consultaDependientePorTipoElemento").setParameter("tipoElemento", elemento).getResultList();
        } catch (Exception ex) {
            throw new ErrorGeneral("Error", ex.getMessage());
        }
        return respuestas;
    }

    @Override
    public List<Respuesta> obtenerRespuestasPorPreguntas(List<String> preguntas) {
        List<Respuesta> respuestas = new ArrayList<Respuesta>();
        try {
            respuestas = tpm.getEm().createNamedQuery("Respuesta.findByPreguntas").setParameter("preguntas", preguntas).getResultList();
        } catch (Exception ex) {
            throw new ErrorGeneral("Error", ex.getMessage());
        }
        return respuestas;
    }
    
    @Override
    public Elemento obtenerElementoPorRespuestaValor(Long idInstrumento,String codRespuesta, String valor){
        Elemento elemento = new Elemento();
        try {
            RespuestaDig respuesta = (RespuestaDig) tpm.getEm().createNamedQuery("RespuestaDig.findByInstrumentoValor")
                    .setParameter("instrumentoDigitado", idInstrumento)
                    .setParameter("codRespuesta", codRespuesta)
                    .setParameter("valor", valor).getSingleResult();
            if(respuesta!= null){
                elemento = respuesta.getElemento();
                for(RespuestaDig res:elemento.getRespuestasList()){
                    elemento.getMapaRespuestas().put(res.getRespuesta(), res);
                }
            }
        } catch (Exception ex) {
            return null;
        }
        return elemento;
        
    }
    
    @Override
    public List<Respuesta> obtenerRespuestaRepetidas(Respuesta respuesta){
        List<Respuesta> respuestas = new ArrayList<Respuesta>();
        try {
            respuestas = tpm.getEm().createNamedQuery("Respuesta.obtenerByRepetida").setParameter("repetida", respuesta.getRepetida()).getResultList();
        } catch (Exception ex) {
            throw new ErrorGeneral("Error", ex.getMessage());
        }
        return respuestas;
    }

    @Override
    public void crearEspaciosSimilares(long idInstrumento, String elemento, String espaciosDigitados) {
        Connection conn = null;
        CallableStatement cs = null;
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("jdbc/DBCierDS");
            conn = ds.getConnection();           

            cs = conn.prepareCall(UtilProperties.getProperties().getProperty("CrearEspaciosSimilares"));

            cs.registerOutParameter(1, Types.VARCHAR);
            cs.setLong(2, idInstrumento);
            cs.setString(3, elemento);
            cs.setString(4, espaciosDigitados);

            cs.execute();
            String valor = cs.getString(1);
            cs.close();
            if (!valor.equals("done")) {
                throw new Exception();
            }
        } catch (Exception ex) {
            throw new ErrorGeneral("Error", ex.getMessage());
        } finally {
            
            try {
                if (conn != null) {
                    conn.close();
                }
                if (cs != null) {
                    cs.close();
                }
            } catch (SQLException ex) {
                throw new ErrorGeneral("Error Cerrando conn ", ex.getMessage());
            }
        }
    }

}
