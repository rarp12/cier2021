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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + (this.idEspacio != null ? this.idEspacio.hashCode() : 0);
        hash = 59 * hash + (this.idEdificio != null ? this.idEdificio.hashCode() : 0);
        hash = 59 * hash + (this.idPiso != null ? this.idPiso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EspacioSimilar other = (EspacioSimilar) obj;
        if ((this.idEspacio == null) ? (other.idEspacio != null) : !this.idEspacio.equals(other.idEspacio)) {
            return false;
        }
        if ((this.idEdificio == null) ? (other.idEdificio != null) : !this.idEdificio.equals(other.idEdificio)) {
            return false;
        }
        if ((this.idPiso == null) ? (other.idPiso != null) : !this.idPiso.equals(other.idPiso)) {
            return false;
        }
        return true;
    }
    
    
}
