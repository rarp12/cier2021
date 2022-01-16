/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.tipo;

/**
 *
 * @author rrocha
 */
public enum FuenteInformacion {
    CROQUIS("enum.fuenteInformacion.CROQUIS","1"),
    PLANO_FISICO("enum.fuenteInformacion.PLANO_FISICO","2"),
    ARCHIVO_DWG("enum.fuenteInformacion.ARCHIVO_DWG","3"),
    AEROFOTOGRAFIA("enum.fuenteInformacion.AEROFOTOGRAFIA","4");
    
    private String etiqueta;
    private String codigo;
    
    private FuenteInformacion(String etiqueta,String codigo) {
        this.etiqueta = etiqueta;
        this.codigo = codigo;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    
}
