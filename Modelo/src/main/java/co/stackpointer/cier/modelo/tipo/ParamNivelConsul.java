/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.tipo;

/**
 *
 * @author user
 */
public enum ParamNivelConsul {
    
    CERO(0L,"enum.paramNivelConsul.CERO"),
    UNO(1L,"enum.paramNivelConsul.UNO"),
    DOS(2L,"enum.paramNivelConsul.DOS"),
    TRES(3L,"enum.paramNivelConsul.TRES"),
    CUATRO(4L,"enum.paramNivelConsul.CUATRO"),
    CINCO(5L,"enum.paramNivelConsul.CINCO"),
    ESTABLECIMIENTO(6L,"enum.paramNivelConsul.ESTABLECIMIENTO"),
    SEDE(7L,"enum.paramNivelConsul.SEDE"),
    PREDIO(8L,"enum.paramNivelConsul.PREDIO");
    
    private Long nivel;
    private String etiqueta; 

    private ParamNivelConsul(Long nivel, String etiqueta) {
        this.nivel = nivel;
        this.etiqueta = etiqueta;
    }

    public Long getNivel() {
        return nivel;
    }

    public void setNivel(Long nivel) {
        this.nivel = nivel;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
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
