/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.entidad.sistema;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author StackPointer
 */
@Entity
@Table(name = "sys_longitudes")
@NamedQueries({
    @NamedQuery(name = "Longitudes.findAll", query = "SELECT l FROM Longitudes l")})
public class Longitudes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    @SequenceGenerator(name = "SEC_PK_LONGITUDES", sequenceName = "SEC_PK_SYS_LONGITUDES", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEC_PK_LONGITUDES")
    private Long id;
    
    @Size(max = 100)
    @Column(name = "descripcion")
    private String descripcion;
    
    @Size(max = 100)
    @Column(name = "elemento")
    private String elemento;
    
    @Column(name = "longitud")
    private Long longitud;
    
    @Column(name = "sobre_titulo")
    private Boolean sobreTitulo;
    
    @Column(name = "aplica_omnifaces")
    private Boolean aplicaOmnifaces;

    public Longitudes() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getElemento() {
        return elemento;
    }

    public void setElemento(String elemento) {
        this.elemento = elemento;
    }

    public Long getLongitud() {
        return longitud;
    }

    public void setLongitud(Long longitud) {
        this.longitud = longitud;
    }

    public Boolean getSobreTitulo() {
        return sobreTitulo;
    }

    public void setSobreTitulo(Boolean sobreTitulo) {
        this.sobreTitulo = sobreTitulo;
    }

    public Boolean getAplicaOmnifaces() {
        return aplicaOmnifaces;
    }

    public void setAplicaOmnifaces(Boolean aplicaOmnifaces) {
        this.aplicaOmnifaces = aplicaOmnifaces;
    }

}
