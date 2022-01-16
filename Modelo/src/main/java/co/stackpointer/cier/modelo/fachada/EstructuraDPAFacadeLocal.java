/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.fachada;

import co.stackpointer.cier.modelo.entidad.dpa.ConfiguracionNivel;
import co.stackpointer.cier.modelo.entidad.dpa.ConfiguracionNivelAux;
import co.stackpointer.cier.modelo.entidad.dpa.Nivel;
import co.stackpointer.cier.modelo.entidad.establecimiento.Establecimiento;
import co.stackpointer.cier.modelo.entidad.establecimiento.Predio;
import co.stackpointer.cier.modelo.entidad.establecimiento.Sede;
import co.stackpointer.cier.modelo.entidad.indicador.ConfiguracionNivelHis;
import co.stackpointer.cier.modelo.entidad.indicador.EstablecimientoHis;
import co.stackpointer.cier.modelo.entidad.indicador.EstablecimientoHisPK;
import co.stackpointer.cier.modelo.entidad.indicador.NivelHis;
import co.stackpointer.cier.modelo.entidad.indicador.NivelHisPK;
import co.stackpointer.cier.modelo.entidad.indicador.PredioHis;
import co.stackpointer.cier.modelo.entidad.indicador.PredioHisPK;
import co.stackpointer.cier.modelo.entidad.indicador.SedeHis;
import co.stackpointer.cier.modelo.entidad.indicador.SedeHisPK;
import co.stackpointer.cier.modelo.tipo.ParamNivelConsul;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface EstructuraDPAFacadeLocal {

    public NivelHis consultarNivelHis(NivelHisPK pk);
    
    public NivelHis consultarNivelHis(Integer periodo, String codigoNivel);    
    
    public List<NivelHis> consultarNivelesHis(Integer periodo, Long idpadre);
    
    public String consultarDescripcionNivelDPA (Long nivel, Long idIdioma); 
    
    public String consultarDescripcionNivelDPA (Long nivel, Integer periodo, Long idIdioma); 
    
    public EstablecimientoHis consultarEstablecimientoHis(EstablecimientoHisPK pk);

    public SedeHis consultarSedeHis(SedeHisPK pk);

    public PredioHis consultarPredioHis(PredioHisPK pk);

    public List<ConfiguracionNivelHis> consultarConfNivelesHis(Integer periodo);
    
    public List<ConfiguracionNivelHis> consultarConfNivelesHis(Integer periodo, Long nivel);

    public List<EstablecimientoHis> consultarEstablecimientosHis(Integer periodo, Long idnivel);

    // actual
    public Nivel consultarNivel(Long id);
    
    public Nivel consultarNivel(String codigo);

    public Establecimiento consultarEstablecimiento(Long id);

    public Sede consultarSede(Long id);

    public Predio consultarPredio(Long id);

    public List<Nivel> consultarNiveles(ParamNivelConsul nivelConsu, Long idpadre);

    public List<ConfiguracionNivel> consultarConfNivelesActual();
    
    public List<ConfiguracionNivel> consultarConfNivelesActual(Long nivel);
    
    public List<ConfiguracionNivel> consultarConfNivelesActualInverso(Long nivel);

    public List<Establecimiento> consultarEstablecimientos(Long idnivel);
    
    public List<Sede> consultarSedes(Long idEstablecimiento);
    
    public List<Sede> consultarSedes(String idEstablecimiento);
    
    //Para administracion de niveles en tablas aux
    public ConfiguracionNivel getConfiguracionNivel(Long nivel);
            
    public List<ConfiguracionNivel> consultarConfNivelesAuxActual();
    
    public List<Nivel> consultarNivelesPorConfiguracion(ParamNivelConsul nivelConsulta);
    
    public List<Nivel> consultarNiveles(ParamNivelConsul nivelConsu, Nivel padre);
    
    public List<Nivel> consultarNivelAux(Long idDpa, Long idMaximoNivelDPA);
    
    public Nivel consultarNivelAux(Long id);
    
    public Nivel consultarNivelAux(String codigo, Long nivel);
    
    public boolean estanRelacionados(String codigoHijo, String codigoPadre, Long nivelHijo);
    
    public List<Establecimiento> consultarEstablecimientosAux();
    
    public List<Establecimiento> consultarEstablecimientosAux(Nivel nivel);
    
    public List<Establecimiento> consultarEstablecimientosAux(Long idDpa, Long maximoNivelDPA);
    
    public Sede consultarSedeAux(Long id);
    
    public List<Sede> consultarSedesAux();
    
    public List<Sede> consultarSedesAuxDiferentesSedeSeleccionada(Sede sede);
    
    public List<Sede> consultarSedesAux(Long idEstablecimiento);
    
    public List<Sede> consultarSedesAux(Long idDpa, Long maximoNivelDPA);
    
    public List<Sede> consultarSedesAux(Nivel nivel);
    
    public boolean existeCodigoDpa(String codigo, Long nivel);
    
    public boolean existeDescripcionDpa(Nivel nivel);
    
    public boolean existeDescripcionDpaNoId(Nivel nivel);
    
    public boolean tieneSubDivisiones (Nivel nivel);
    
    public boolean relacionesDPAIncompleta ();
    
    public Nivel guardarDpa(String usernameSesion, Nivel dpa);
    
    public void actualizarDpa(String usernameSesion, Nivel dpa);
    
    public void eliminarDpa (String usernameSesion, Nivel dpa);
    
    public void guardarNivelConfiguracion(String usernameSesion, Integer tenant, Integer posicion, String descripcion) throws Exception;
    
    public void actualizarNivelConfiguracion(String usernameSesion, Integer tenant, Long idNivelConf, String descripcion) throws Exception;
    
    public void eliminarNivelConfiguracion(String usernameSesion, Integer tenant, Integer idNivelConf, Integer posicion) throws Exception;
    
    public void actualizarDpaBD(Long idNivel);
}
