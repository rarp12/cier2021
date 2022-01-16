/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.FachadasConsultas;

import co.stackpointer.cier.modelo.EntidadesConsultas.ConsultaAmbitoGeneralidades;
import co.stackpointer.cier.modelo.filtro.ambito.FiltroAmbitoGeneralidad;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author jguzmanf
 */
@Local
public interface ConsultaAmbitoGeneralidadesFacadeLocal {

    void create(ConsultaAmbitoGeneralidades consultaAmbitoGeneralidades);

    void edit(ConsultaAmbitoGeneralidades consultaAmbitoGeneralidades);

    void remove(ConsultaAmbitoGeneralidades consultaAmbitoGeneralidades);

    ConsultaAmbitoGeneralidades find(Object id);

    List<ConsultaAmbitoGeneralidades> findAll();

    List<ConsultaAmbitoGeneralidades> findRange(int[] range);

    int count();
    
    public List<ConsultaAmbitoGeneralidades> ConsultaGeneralidadesReporte(Long consecutivoReporte);

    public List<ConsultaAmbitoGeneralidades> generarConsultaAmbitoGeneralidades(FiltroAmbitoGeneralidad filtro, Long idioma) throws Exception;

    
}
