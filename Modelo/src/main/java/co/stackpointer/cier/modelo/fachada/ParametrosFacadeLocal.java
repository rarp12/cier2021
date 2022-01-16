/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.fachada;

import co.stackpointer.cier.modelo.entidad.calificacion.AmbientesFuncionales;
import co.stackpointer.cier.modelo.entidad.calificacion.CalServicios;
import co.stackpointer.cier.modelo.entidad.calificacion.VariableAmbito;
import co.stackpointer.cier.modelo.entidad.indicador.ControlGeneracionIndicador;
import co.stackpointer.cier.modelo.entidad.indicador.CostoConstruccion;
import co.stackpointer.cier.modelo.entidad.indicador.EstandarHis;
import co.stackpointer.cier.modelo.entidad.indicador.ParametroConstruccion;
import co.stackpointer.cier.modelo.entidad.indicador.PeriodosCostosConstruccion;
import co.stackpointer.cier.modelo.entidad.indicador.UnidadFuncional;
import co.stackpointer.cier.modelo.entidad.instrumento.Ambito;
import co.stackpointer.cier.modelo.entidad.instrumento.TipologiaValor;
import co.stackpointer.cier.modelo.entidad.instrumento.TipologiaValorNombre;
import co.stackpointer.cier.modelo.entidad.sistema.Parametro;
import co.stackpointer.cier.modelo.excepcion.ErrorIntegridad;
import co.stackpointer.cier.modelo.tipo.ControlGeneracionInd;
import co.stackpointer.cier.modelo.tipo.ParamEstandar;
import co.stackpointer.cier.modelo.tipo.TipologiaCodigo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author StackPointer
 */
@Local
public interface ParametrosFacadeLocal {

    public void crearParametro(Parametro parametro);
    
    public List<TipologiaValorNombre> consultarValoresTipologia(TipologiaCodigo codTipologia, Long idIdioma);

    public List<TipologiaValorNombre> consultarValoresTipologia(String codTipologia, Long idIdioma);
    
    public TipologiaValorNombre consultarValorTipologia(String codTipologia, String codigo, Long idIdioma);
    
    public TipologiaValorNombre consultarValorTipologiaEstado(String codTipologia, String codigo, Long idIdioma);
    
    public String consultarNombreTipologia(Long idTipologia, Long idIdioma);
    
    public TipologiaValorNombre modificarTipologiaValor(TipologiaValorNombre tipologiaValorNombre) throws ErrorIntegridad;

    public TipologiaValor modificarTipologiaValor(String usernameSesion, TipologiaValor tipologiaValor) throws ErrorIntegridad;
    
    public void crearTipologiaValor(String usernameSesion, TipologiaValorNombre tipologiaValorNombre) throws ErrorIntegridad;

    public ControlGeneracionIndicador consultarParametroIndicador(ControlGeneracionInd codigo);

    public Integer ultimoPeriodoCalculadoIndicadores();

    public List<Integer> consultarPeriodosCalculadosIndicadores();
    
    public List<Integer> consultarPeriodosCalculadosIndicadores(Integer periodo);

    public Parametro consultarParametro(String codigo);
    
    public EstandarHis consultarEstandar(ParamEstandar paramEstandar);

    public Ambito modificarAmbito(Ambito ambito);

    public VariableAmbito modificarVariableAmbito(VariableAmbito variableAmbito);

    public List<VariableAmbito> consultarVariablesAmbitos();

    public List<Ambito> consultarAmbitos();
    
    public List<CostoConstruccion> consultarCostoConstrucciones();

    public CostoConstruccion modificarCostoConstruccion(CostoConstruccion costoConstruccion);
    
    public List<Object[]> consultarIndicadores(List<String> indicadores, Long nivelAgrupamiento, Long idEntidad) throws ErrorIntegridad;
    
    public List<Object[]> consultarIndicadoresUnidadesFuncionales(List<String[]> indBusqUnidadesFun, Long nivelAgrupamiento, Long idEntidad) throws ErrorIntegridad;
    
    public List<CalServicios> consultarCalServicios();
    
    public CalServicios modificarCalServicios(CalServicios servicio);
    
    public  List<AmbientesFuncionales> consultarAmbientesFuncionales();
    
    public AmbientesFuncionales modificarAmbientesFuncinoales(AmbientesFuncionales ambiente);
    
    public EstandarHis consultarEstandarPorCodigo(String paramEstandar);

    public List<EstandarHis> consultarEstandares();

    public EstandarHis modificarEstandar(EstandarHis estandar);
    
    public List<UnidadFuncional> consultarUnidadesFuncionales(Integer periodo);
    
    public String consultarPeriodoIndicadores();
    
    public List<ParametroConstruccion> consultaParametrosConstruccion();
    
    public List<UnidadFuncional> obtenerUnidadesFuncionales();

    public void eliminarParametroPC(ParametroConstruccion parametroConstruccion);
    
    public ParametroConstruccion crearParametroPC(ParametroConstruccion nuevoParametro);
    
    public void actualizarParametro(ParametroConstruccion nuevoParametro);
    
    public UnidadFuncional obtenerUnidadFuncionalById(Long id);
    
    public boolean validarParametroCostos(ParametroConstruccion param);
    
    public List<PeriodosCostosConstruccion> obtenerPeriodosConstruccion();
    
    public PeriodosCostosConstruccion crearPeriodoCC(PeriodosCostosConstruccion nuevoPeriodo);
    
    public Long crearParametrosCostos(int idTnt, Long periodo);
     
    public List<ParametroConstruccion> consultaParametrosConstruccionPeriodo(Long periodo);
   
}
