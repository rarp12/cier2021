/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.fachada;

import co.stackpointer.cier.modelo.entidad.establecimiento.Predio;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface PredioFacadeLocal {
    
    List<Predio> obtenerPredioPorCodigoNombre(String codigoPredio, String nombre);
    List<Predio> obtenerPrediosPorSede( String idSede);
    String obtenerSedePorPredio(Long idPredio);
    public Predio getPredioPorCodigo(String codigo);
    public Predio guardarPredio(Predio predio);

}
