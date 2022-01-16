/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.fachada;

import co.stackpointer.cier.modelo.entidad.dpa.Nivel;
import co.stackpointer.cier.modelo.entidad.establecimiento.Establecimiento;
import co.stackpointer.cier.modelo.entidad.establecimiento.Predio;
import co.stackpointer.cier.modelo.entidad.establecimiento.Sede;
import javax.ejb.Local;

/**
 *
 * @author StackPointer
 */
@Local
public interface EstablecimientoFacadeLocal {
    
    public void guardarEstablecimiento(String usernameSesion, Establecimiento establecimiento);
    
    public void actualizarEstablecimiento(String usernameSesion, Establecimiento establecimiento);
    
    public void eliminarEstablecimiento(String usernameSesion, Establecimiento establecimiento);
    
    public Nivel getNivelPorCodigo (String codigo);
    
    public void guardarSede(String usernameSesion, Sede sede);
    
    public void actualizarSede(String usernameSesion, Sede sede);
    
    public void eliminarSede(String usernameSesion, Sede sede);
    
    public Establecimiento  getEstablecimientoPorCodigo(String codigo);

    Predio buscarPredioPorId(Long id);
    
    public Sede getSedePorCodigo(String codigo);
    
    public boolean existeSede(String codigo);
    
    public boolean existeEstablecimiento(String codigo);
    
    public boolean existeSedeEstablecimiento(String codSede, String codEstablecimiento);
    
    public boolean existePredioCodOf(String codigoOficial);
    
    public boolean sedeSinInstrumento(String codigoSede);
    
    public int ingresarCoordenada(String codSede, String coordenadaX, String coordenadaY);
    //Para administracion de niveles en tablas aux
    
    public boolean existeCodigoEst(String codigo);
    
    public boolean existeNombreEst(String nombre);
    
    public boolean existeCodigoSed(String codigo);
    
    public boolean existeNombreSed(String nombre);
    
    public boolean tieneHijos (Sede sede);
    
    public boolean tieneHijos (Establecimiento establecimiento);
    
    public void guardarPredio(String usernameSesion, Predio predio, Sede sede);
    
    public void actualizarPredio(String usernameSesion, Predio predio, Sede sede);
            
    public void eliminarPredio(String usernameSesion, Predio predio);
    
    Sede buscarSedePorId(Long id);
    
    public void asignarCoordenadaSedes(String codigoSede, String coord_x, String coord_y, String coord_grado);

    public void colocarPredioInexistenteBD(Long idPredio);

    public void actualizarPredioBD(Long idPredio);

    public void actualizarSedeBD(Long idSede);

    public void actualizarEstablecimientoBD(Long idEstablecimiento);
    
}
