/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.fachada;

import co.stackpointer.cier.modelo.entidad.sistema.Longitudes;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author StackPointer
 */
@Local
public interface LongitudesFacadeLocal {

    List<Longitudes> consultarLongitudes();

}
