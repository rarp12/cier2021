/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.fachada;

import co.stackpointer.cier.modelo.entidad.digitado.GrpEsp;
import co.stackpointer.cier.modelo.entidad.digitado.Grupo;
import co.stackpointer.cier.modelo.entidad.digitado.GrupoEspacio;
import co.stackpointer.cier.modelo.entidad.digitado.GrupoTipologia;
import co.stackpointer.cier.modelo.filtro.digitacion.espacios.FiltroDigitacionEdificiosAreas;
import co.stackpointer.cier.modelo.filtro.digitacion.espacios.FiltroDigitacionEspacios;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author rjay1
 */
@Local
public interface DigitacionEspacioFacadeLocal {
    List<Grupo> buscarTodosGrupos();

    void crearGrupoGeneracion(List<String> grupos);

    List<GrupoEspacio> buscarTodosGruposEspacios();

    List<GrupoTipologia> buscarTodosGruposTipologias();

    List<GrupoTipologia> buscarGruposTipologiasByCodigo(String codigo, Integer idioma);
    
    List<GrupoEspacio> consultarGruposEspacios(FiltroDigitacionEspacios filtro);

    List<GrupoEspacio> consultarEdificiosAreas(FiltroDigitacionEdificiosAreas filtro);
    
    List<GrpEsp> consultarEdificiosAreasOra(FiltroDigitacionEdificiosAreas filtro, int type);
}