/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.fachada;

import co.stackpointer.cier.modelo.entidad.indicador.UnidadFuncional;
import co.stackpointer.cier.modelo.entidad.instrumento.Tipologia;
import co.stackpointer.cier.modelo.entidad.instrumento.TipologiaValor;
import co.stackpointer.cier.modelo.entidad.instrumento.TipologiaValorNombre;
import co.stackpointer.cier.modelo.entidad.unidad.UndFuncional;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author rjay1
 */
@Local
public interface UndFuncionalFacadeLocal {

    public List<UndFuncional> getUnidades();

    public UndFuncional getUnidad(Long id);

    public UnidadFuncional getUnidadFuncinal(Long id);

    public UndFuncional guardarUnidad(String usernameSesion, UndFuncional unidad);

    public void actualizarUnidad(String usernameSesion, UndFuncional unidad);

    public List<Tipologia> getTipologiasTipoEspacio();

    public List<TipologiaValor> getTipologiasValor(String codigo);

    public List<TipologiaValorNombre> getTipologiasValorNombre(String codigo, Long idioma);

    public void guardarTipoValor(Long unidad, String tipologia, String valor);

    public void eliminarTipoValor(Long unidad, String tipologia, String valor);

    public List<TipologiaValorNombre> getTipoValorByUnidad(Long unidad, Long idioma);
    
    public boolean validarNoExisteDuplicidad(Long unidad, String tipologia, String valor);
}
