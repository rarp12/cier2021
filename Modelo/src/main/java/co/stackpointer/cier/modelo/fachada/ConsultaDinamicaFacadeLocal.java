/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.fachada;

import co.stackpointer.cier.modelo.filtro.dinamica.FiltroConsultaDinamica;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author nruiz
 */
@Local
public interface ConsultaDinamicaFacadeLocal {
    
        public List<String> consultarAmbPredominantes();
        
        public List<String> consultarAmbCondicional(String ambitoPredominante);
        
        public List<String> consultarPregGerenciales(String ambitoPredominante, String ambitoCondicional);
        
        public List<Object[]> consultarPrediosPorTipologias(FiltroConsultaDinamica filtro);

}
