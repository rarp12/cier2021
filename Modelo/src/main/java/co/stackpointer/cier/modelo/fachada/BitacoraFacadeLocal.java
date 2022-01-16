/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.fachada;

import co.stackpointer.cier.modelo.entidad.sistema.Bitacora;
import co.stackpointer.cier.modelo.filtro.bitacora.FiltroConsultaBitacora;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author rarp1
 */
@Local
public interface BitacoraFacadeLocal {
    
    public List<Bitacora> consultarBitacora( FiltroConsultaBitacora filtro);
    
    public void eliminarBitacora();
}
