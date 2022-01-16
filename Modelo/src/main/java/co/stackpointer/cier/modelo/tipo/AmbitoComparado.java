/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.tipo;

/**
 *
 * @author rrocha
 */
public enum AmbitoComparado {
    GENERALIDADES("enum.AmbitoComparado.generalidades"),
    AMPLIACION("enum.AmbitoComparado.ampliacion"),
    REDISTRIBUCION("enum.AmbitoComparado.redistribucion"),
    AMBIENTES("enum.AmbitoComparado.ambientes"),
    EDIFICIOS("enum.AmbitoComparado.edificios"),
    ESPACIOS("enum.AmbitoComparado.espacios");
    
    private String etiqueta;

    private AmbitoComparado(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getEtiqueta() {
        return etiqueta;
    }
}
