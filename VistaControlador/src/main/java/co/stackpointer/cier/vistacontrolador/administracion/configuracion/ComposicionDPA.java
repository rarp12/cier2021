/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.administracion.configuracion;

/**
 *
 * @author nruiz
 */
public class ComposicionDPA {
    String codNivel1;
    String nomNivel1;    
    String codNivel2;
    String nomNivel2;   
    String codNivel3;
    String nomNivel3;   
    String codNivel4;
    String nomNivel4;   
    String codNivel5;
    String nomNivel5;   
    String codEstablecimiento;    
    String nomEstablecimiento;
    String email;
    String direccion;
    String codSede;
    String nomSede;
    String nomSector;
    String telefono;
    String codSector;
    String codAutoridad;
    String nomRector;
    String nomAutoridad;
    String emailRector;
    String telRector;    

    public ComposicionDPA() {
    }

    public ComposicionDPA(String codNivel1, String nomNivel1, String codNivel2, String nomNivel2, String codNivel3, String nomNivel3, String codNivel4, String nomNivel4, String codNivel5, String nomNivel5) {
        this.codNivel1 = codNivel1;
        this.nomNivel1 = nomNivel1;
        this.codNivel2 = codNivel2;
        this.nomNivel2 = nomNivel2;
        this.codNivel3 = codNivel3;
        this.nomNivel3 = nomNivel3;
        this.codNivel4 = codNivel4;
        this.nomNivel4 = nomNivel4;
        this.codNivel5 = codNivel5;
        this.nomNivel5 = nomNivel5;
    }    

    public String getCodNivel1() {
        return codNivel1;
    }

    public void setCodNivel1(String codNivel1) {
        this.codNivel1 = codNivel1;
    }

    public String getNomNivel1() {
        return nomNivel1;
    }

    public void setNomNivel1(String nomNivel1) {
        this.nomNivel1 = nomNivel1;
    }

    public String getCodNivel2() {
        return codNivel2;
    }

    public void setCodNivel2(String codNivel2) {
        this.codNivel2 = codNivel2;
    }

    public String getNomNivel2() {
        return nomNivel2;
    }

    public void setNomNivel2(String nomNivel2) {
        this.nomNivel2 = nomNivel2;
    }

    public String getCodNivel3() {
        return codNivel3;
    }

    public void setCodNivel3(String codNivel3) {
        this.codNivel3 = codNivel3;
    }

    public String getNomNivel3() {
        return nomNivel3;
    }

    public void setNomNivel3(String nomNivel3) {
        this.nomNivel3 = nomNivel3;
    }

    public String getCodNivel4() {
        return codNivel4;
    }

    public void setCodNivel4(String codNivel4) {
        this.codNivel4 = codNivel4;
    }

    public String getNomNivel4() {
        return nomNivel4;
    }

    public void setNomNivel4(String nomNivel4) {
        this.nomNivel4 = nomNivel4;
    }

    public String getCodNivel5() {
        return codNivel5;
    }

    public void setCodNivel5(String codNivel5) {
        this.codNivel5 = codNivel5;
    }

    public String getNomNivel5() {
        return nomNivel5;
    }

    public void setNomNivel5(String nomNivel5) {
        this.nomNivel5 = nomNivel5;
    }

    public String getCodEstablecimiento() {
        return codEstablecimiento;
    }

    public void setCodEstablecimiento(String codEstablecimiento) {
        this.codEstablecimiento = codEstablecimiento;
    }

    public String getNomEstablecimiento() {
        return nomEstablecimiento;
    }

    public void setNomEstablecimiento(String nomEstablecimiento) {
        this.nomEstablecimiento = nomEstablecimiento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCodSede() {
        return codSede;
    }

    public void setCodSede(String codSede) {
        this.codSede = codSede;
    }

    public String getNomSede() {
        return nomSede;
    }

    public void setNomSede(String nomSede) {
        this.nomSede = nomSede;
    }

    public String getNomSector() {
        return nomSector;
    }

    public void setNomSector(String nomSector) {
        this.nomSector = nomSector;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCodSector() {
        return codSector;
    }

    public void setCodSector(String codSector) {
        this.codSector = codSector;
    }

    public String getCodAutoridad() {
        return codAutoridad;
    }

    public void setCodAutoridad(String codAutoridad) {
        this.codAutoridad = codAutoridad;
    }

    public String getNomRector() {
        return nomRector;
    }

    public void setNomRector(String nomRector) {
        this.nomRector = nomRector;
    }

    public String getNomAutoridad() {
        return nomAutoridad;
    }

    public void setNomAutoridad(String nomAutoridad) {
        this.nomAutoridad = nomAutoridad;
    }

    public String getEmailRector() {
        return emailRector;
    }

    public void setEmailRector(String emailRector) {
        this.emailRector = emailRector;
    }

    public String getTelRector() {
        return telRector;
    }

    public void setTelRector(String telRector) {
        this.telRector = telRector;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + (this.codNivel1 != null ? this.codNivel1.hashCode() : 0);
        hash = 53 * hash + (this.codNivel2 != null ? this.codNivel2.hashCode() : 0);
        hash = 53 * hash + (this.codNivel3 != null ? this.codNivel3.hashCode() : 0);
        hash = 53 * hash + (this.codNivel4 != null ? this.codNivel4.hashCode() : 0);
        hash = 53 * hash + (this.codNivel5 != null ? this.codNivel5.hashCode() : 0);
        hash = 53 * hash + (this.codEstablecimiento != null ? this.codEstablecimiento.hashCode() : 0);
        hash = 53 * hash + (this.codSede != null ? this.codSede.hashCode() : 0);
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
        final ComposicionDPA other = (ComposicionDPA) obj;
        if ((this.codNivel1 == null) ? (other.codNivel1 != null) : !this.codNivel1.equals(other.codNivel1)) {
            return false;
        }
        if ((this.codNivel2 == null) ? (other.codNivel2 != null) : !this.codNivel2.equals(other.codNivel2)) {
            return false;
        }
        if ((this.codNivel3 == null) ? (other.codNivel3 != null) : !this.codNivel3.equals(other.codNivel3)) {
            return false;
        }
        if ((this.codNivel4 == null) ? (other.codNivel4 != null) : !this.codNivel4.equals(other.codNivel4)) {
            return false;
        }
        if ((this.codNivel5 == null) ? (other.codNivel5 != null) : !this.codNivel5.equals(other.codNivel5)) {
            return false;
        }
        if ((this.codEstablecimiento == null) ? (other.codEstablecimiento != null) : !this.codEstablecimiento.equals(other.codEstablecimiento)) {
            return false;
        }
        if ((this.codSede == null) ? (other.codSede != null) : !this.codSede.equals(other.codSede)) {
            return false;
        }
        return true;
    }
    
}
