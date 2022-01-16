/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.fachada;

import co.stackpointer.cier.modelo.entidad.BID.AmbitosBID;
import co.stackpointer.cier.modelo.entidad.BID.DatosInformeBID;
import co.stackpointer.cier.modelo.entidad.BID.IndicadoresBID;
import co.stackpointer.cier.modelo.entidad.BID.PaisesBID;
import co.stackpointer.cier.modelo.entidad.BID.PeriodosBID;
import co.stackpointer.cier.modelo.entidad.BID.TiposIndicadoresBID;
import co.stackpointer.cier.modelo.entidad.BID.ValoresBID;
import co.stackpointer.cier.modelo.entidad.BID.VariablesBID;
import co.stackpointer.cier.modelo.excepcion.ErrorIntegridad;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface BIDFacadeLocal {
    
    public List<PeriodosBID> getPeriodosBID();
 
    public PaisesBID obtenerPaisBIDPorId(int idPaisBID);
    
    public void guardarValorBID(ValoresBID valorBID) throws ErrorIntegridad;
    
    public void guardarIndicadorBID(IndicadoresBID indicadorBID) throws ErrorIntegridad;
    
    public void guardarDatosInformeBID(DatosInformeBID datoInformeBID) throws ErrorIntegridad;
    
    public void guardarPeriodoBID(PeriodosBID periodoBID) throws ErrorIntegridad;
    
    public boolean existenValoresPaisPeriodo(PaisesBID paisBID, PeriodosBID periodoBID) throws ErrorIntegridad;
    
    public boolean existenIndicadoresPaisPeriodo(PaisesBID paisBID, PeriodosBID periodoBID) throws ErrorIntegridad;

    public boolean existenDatosInformePaisPeriodo(PaisesBID paisBID, PeriodosBID periodoBID) throws ErrorIntegridad;

    public void eliminaraValoresPaisPeriodo(PaisesBID paisBID, PeriodosBID periodoBID) throws ErrorIntegridad;
    
    public void eliminaraIndicadoresPaisPeriodo(PaisesBID paisBID, PeriodosBID periodoBID) throws ErrorIntegridad;
    
    public void eliminaraDatosInformePaisPeriodo(PaisesBID paisBID, PeriodosBID periodoBID) throws ErrorIntegridad;
    
    public List<AmbitosBID> obtenerAmbitosBID();
    
    public List<VariablesBID> obtenerVariablesEditablesBID();
    
    public void actualizarVariableBID(VariablesBID variableBID) throws ErrorIntegridad;
    
    public void actualizarAmbitoBID(AmbitosBID ambitoBID) throws ErrorIntegridad;
    
    public void actualizarPeriodoBID(PeriodosBID periodoBID) throws ErrorIntegridad;
    
    public List<Object[]> consultarCalificacionesBID(String codPeriodo);
    
    public VariablesBID obtenerVariablePorId(int id) throws ErrorIntegridad;
    
    public TiposIndicadoresBID obtenerTipoIndicadorPorCodigo(String codigo) throws ErrorIntegridad;
    
    public List<IndicadoresBID> consultarIndicadoresBID(PeriodosBID periodoBID) throws ErrorIntegridad;
    
    public List<IndicadoresBID> consultarIndicadoresBIDPais(PeriodosBID periodoBID, PaisesBID paisBID) throws ErrorIntegridad;
    
    public List<TiposIndicadoresBID> consultarTiposIndicadoresBID() throws ErrorIntegridad;

    public List<DatosInformeBID> consultarDatosInformeBID(PeriodosBID periodoBID, Long idPais) throws ErrorIntegridad;
    
    public void eliminarPeriodoBID(PeriodosBID periodoBID) throws ErrorIntegridad;
   
    public List<IndicadoresBID> consultarIndicadoresBIDporID(PeriodosBID periodoBID)throws ErrorIntegridad;
    
    public boolean consultarPeriodoCodigo(String periodoBID)throws ErrorIntegridad;
}
