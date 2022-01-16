/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.fachada;

import co.stackpointer.cier.modelo.EntidadesConsultas.ConsultaAmbitoRiesgosNaturales;
import co.stackpointer.cier.modelo.filtro.ambito.FiltroAmbitoAccesibilidad;
import co.stackpointer.cier.modelo.filtro.ambito.FiltroAmbitoAccesibilidadInterna;
import co.stackpointer.cier.modelo.filtro.ambito.FiltroAmbitoAmbiente;
import co.stackpointer.cier.modelo.filtro.ambito.FiltroAmbitoAmpliacion;
import co.stackpointer.cier.modelo.filtro.ambito.FiltroAmbitoComplementarios;
import co.stackpointer.cier.modelo.filtro.ambito.FiltroAmbitoConfort;
import co.stackpointer.cier.modelo.filtro.ambito.FiltroAmbitoControlVigilancia;
import co.stackpointer.cier.modelo.filtro.ambito.FiltroAmbitoEdificio;
import co.stackpointer.cier.modelo.filtro.ambito.FiltroAmbitoEspacio;
import co.stackpointer.cier.modelo.filtro.ambito.FiltroAmbitoEspacioConsolidado;
import co.stackpointer.cier.modelo.filtro.ambito.FiltroAmbitoGeneralidad;
import co.stackpointer.cier.modelo.filtro.ambito.FiltroAmbitoMigracion;
import co.stackpointer.cier.modelo.filtro.ambito.FiltroAmbitoPropiedad;
import co.stackpointer.cier.modelo.filtro.ambito.FiltroAmbitoRedistribucion;
import co.stackpointer.cier.modelo.filtro.ambito.FiltroAmbitoRiesgoAntropico;
import co.stackpointer.cier.modelo.filtro.ambito.FiltroAmbitoRiesgoNatural;
import co.stackpointer.cier.modelo.filtro.ambito.FiltroAmbitoSeguridad;
import co.stackpointer.cier.modelo.filtro.ambito.FiltroAmbitoServicio;
import co.stackpointer.cier.modelo.filtro.ambito.FiltroAmbitoServicioConsolidado;
import co.stackpointer.cier.modelo.filtro.ambito.FiltroAmbitoSostenibilidad;
import co.stackpointer.cier.modelo.entidad.indicador.UnidadFuncional;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface ConsultaAmbitoFacadeLocal {

    public List<Object[]> consultarGeneralidades(FiltroAmbitoGeneralidad filtro);

    public List<Object[]> consultarRiesgosNaturales(FiltroAmbitoRiesgoNatural filtro);
    
    public List<Object[]> consultarRiesgosAntropicos(FiltroAmbitoRiesgoAntropico filtro);
    
    public List<Object[]> consultarAccesibilidad(FiltroAmbitoAccesibilidad filtro);  
    
    public List<Object[]> consultarRedistribucion(FiltroAmbitoRedistribucion filtro);
    
    public List<Object[]> consultarAmbientes(FiltroAmbitoAmbiente filtro);

    public List<Object[]> consultarAmpliacion(FiltroAmbitoAmpliacion filtro);

    public List<Object[]> consultarPropiedad(FiltroAmbitoPropiedad filtro);

    public List<Object[]> consultarAccesibilidadInterna(FiltroAmbitoAccesibilidadInterna filtro);
    
    public List<Object[]> consultarServicio(FiltroAmbitoServicio filtro);
    
    public List<Object[]> consultarServicioConsolidado(FiltroAmbitoServicioConsolidado filtro);
    
    public List<Object[]> consultarConfort(FiltroAmbitoConfort filtro);
    
    public List<Object[]> consultarControlVigilancia(FiltroAmbitoControlVigilancia filtro);

    public List<Object[]> consultarSostenibilidad(FiltroAmbitoSostenibilidad filtro);
    
    public List<Object[]> consultarSeguridad(FiltroAmbitoSeguridad filtro);

    public List<Object[]> consultarEdificios(FiltroAmbitoEdificio filtro);
    
    public List<Object[]> consultarEspacios(FiltroAmbitoEspacio filtro);
    
    public List<Object[]> consultarComplementarios(FiltroAmbitoComplementarios filtro);

    public List<ConsultaAmbitoRiesgosNaturales> generarConsultaAmbitoRiesgosNaturales(FiltroAmbitoRiesgoNatural filtro, Long idioma) throws Exception;

    public List<Object[]> consultarMigracion(FiltroAmbitoMigracion filtro);
    
    public List<Object[]> consultarCocina(FiltroAmbitoAmbiente filtro);
    
    public List<Object[]> consultarComedor(FiltroAmbitoAmbiente filtro);
    
    public List<Object[]> consultarEspaciosConsolidados(FiltroAmbitoEspacioConsolidado filtro,boolean isDetalladoPredio,List<UnidadFuncional> unidades,List<UnidadFuncional> unidadesBD);
}
