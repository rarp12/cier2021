/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.tipo;

/**
 *
 * @author user
 */
public enum ParamBaseDatos {
    
    ORACLE(1L),
    POSTGRE(2L);
    
    private Long nivel;
    

    private ParamBaseDatos(Long nivel) {
        this.nivel = nivel;
      }

    public Long getNivel() {
        return nivel;
    }

    public void setNivel(Long nivel) {
        this.nivel = nivel;
    }

    
    public boolean equals(Long obj) {
        if (obj == null) {
            return false;
        }
        if (this.nivel != obj && (this.nivel == null || !this.nivel.equals(obj))) {
            return false;
        }
        return true;
    }
     
        
}
