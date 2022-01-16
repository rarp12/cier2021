/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.bean;

import co.stackpointer.cier.modelo.entidad.acceso.Funcionalidad;

/**
 *
 * @author user
 */
public class Permiso {
    
    private Funcionalidad funcionalidad;
    
    private boolean selected;

    public Permiso() {
    }

    public Permiso(Funcionalidad funcionalidad, boolean selected) {
        this.funcionalidad = funcionalidad;
        this.selected = selected;
    }

    public Funcionalidad getFuncionalidad() {
        return funcionalidad;
    }

    public void setFuncionalidad(Funcionalidad funcionalidad) {
        this.funcionalidad = funcionalidad;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
        
}
