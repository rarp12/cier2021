/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.fachada;

import co.stackpointer.cier.modelo.configuracion.Ambiente;
import javax.annotation.Resource;

/**
 *
 * @author mrodriguez
 */

public abstract class Configuracion  {
    protected final static int TENANT_ID = 1;
    
    @Resource(name = "ambiente")
    protected String variable;
        
    protected Ambiente ambiente = Ambiente.DEV;

    
    void inicializar(){
        if(variable != null){
            ambiente = Ambiente.valueOf(variable);
        }
    }
    
    public Ambiente getAmbiente(){
        return ambiente;   
    }
    
}
