/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.fachada;

import co.stackpointer.cier.modelo.excepcion.ErrorIntegridad;
import co.stackpointer.cier.modelo.filtro.calificacion.FiltroCalificacion;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author nruiz
 */
@Local
public interface CalificacionFacadeLocal {
    
        public List<Object[]> consultarCalificaciones(FiltroCalificacion filtro);
        
        public BigDecimal consultarCalificacionAmbito(FiltroCalificacion filtro, String amb);
        
        public List<Object[]> obtenerPonderaciones();
        
        public List<Object[]> obtenerValorEscala (String codAmbito, BigDecimal valor);
        
        public void crearDatosCalificacion(int periodo) throws ErrorIntegridad;
        
        public List<Object[]> obtenerVariablesPais();

    public List<Object[]> consultarCalificacionesReporte(FiltroCalificacion filtro);

}
