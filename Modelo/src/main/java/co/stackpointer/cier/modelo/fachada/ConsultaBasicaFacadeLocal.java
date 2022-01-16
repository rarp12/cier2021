/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.fachada;

import co.stackpointer.cier.modelo.entidad.digitado.Adjunto;
import co.stackpointer.cier.modelo.entidad.instrumento.Documento;
import co.stackpointer.cier.modelo.entidad.instrumento.Observacion;
import co.stackpointer.cier.modelo.filtro.basica.FiltroConsultaBasica;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface ConsultaBasicaFacadeLocal {
    
    public List<Integer> consultaPeriodosCreacion();
    
    public Integer consultaPeriodoHis(Integer periodoCreacion);
    
    public List<Object[]> consultarInstrumentos(FiltroConsultaBasica filtro,int nivel);
    
    public List<Object[]> obtenerRespuestasInstrumentoDig(Long idDigInstrumento, List<String> respuestas);
    
    public List<String> consultaRutaAdjunto(Long idDigInstrumento, String respAdj);
    
    public String consultaRutaAdjuntoCroquis(Long idDigInstrumento, String respAdj, Long idElem);
    
    public List<Observacion> consultarObservacionesPorInstrumento(Long idDigInstrumento);
    
    public List<Observacion> consultarObservacionesPorInstrumentos(String idsInstrumento);
    
    public List<Adjunto> consultarAdjuntosPorInstrumentos(String idsInstrumento);
    
    public List<Documento> consultarDocumentosPorInstrumento(Long idInstrumento);
    
}
