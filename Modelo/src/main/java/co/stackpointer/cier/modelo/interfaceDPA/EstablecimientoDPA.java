/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.interfaceDPA;

import java.util.List;

/**
 *
 * @author user
 */
public interface EstablecimientoDPA {
     
    public Long getId();
    
    public String getCodigo();
    
    public String getNombre();
    
    public List<? extends SedeDPA> getSedes();
}
