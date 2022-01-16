/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.fachada;

import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface InformeGeneralFacadeLocal {
        
    public String consultarCodTipologiaRespuesta(String codRespuesta);
    
    public String obtenerAreasVerdes(Long idDigInstrumento) throws Exception;
    
    public boolean consumoEnergiaEncimaPromedio(Long idDigInstrumento) throws Exception;
    
    public boolean consumoAguaEncimaPromedio(Long idDigInstrumento) throws Exception;
    
    public String areaConstruccionAltura(Long idDigInstrumento) throws Exception;
    
    public List<String[]> getEquivalentes(String grupo);

}
