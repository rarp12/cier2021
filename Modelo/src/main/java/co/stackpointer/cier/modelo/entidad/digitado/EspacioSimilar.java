/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.entidad.digitado;

/**
 *
 * @author rarp1
 */
public class EspacioSimilar {

    private String idEspacio;
    private String idEdificio;
    private String idPiso;

    public EspacioSimilar() {
    }

    public EspacioSimilar(String idEspacio, String idEdificio, String idPiso) {
        this.idEspacio = idEspacio;
        this.idEdificio = idEdificio;
        this.idPiso = idPiso;
    }

    public String getIdEspacio() {
        return idEspacio;
    }

    public void setIdEspacio(String idEspacio) {
        this.idEspacio = idEspacio;
    }

    public String getIdEdificio() {
        return idEdificio;
    }

    public void setIdEdificio(String idEdificio) {
        this.idEdificio = idEdificio;
    }

    public String getIdPiso() {
        return idPiso;
    }

    public void setIdPiso(String idPiso) {
        this.idPiso = idPiso;
    }
}
