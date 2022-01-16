/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.entidad.dpa;

import co.stackpointer.cier.modelo.tipo.Estado;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.TenantDiscriminatorColumn;

/**
 *
 * @author StackPointer
 */
@Entity
@Table(name = "aux_dpa_configuracion_niveles")
@Multitenant
@TenantDiscriminatorColumn(name = "id_tnt", contextProperty = "TENANT_ID", discriminatorType = DiscriminatorType.INTEGER)
@XmlRootElement
@NamedQueries({    
    @NamedQuery(name = "ConfiguracionNivelAux.consultarTodos", query = "SELECT c FROM ConfiguracionNivelAux c WHERE c.estado=co.stackpointer.cier.modelo.tipo.Estado.A ORDER BY c.nivel asc"),
    @NamedQuery(name = "ConfiguracionNivelAux.porNivel", query = "SELECT c FROM ConfiguracionNivelAux c WHERE c.estado=co.stackpointer.cier.modelo.tipo.Estado.A and c.nivel = :nivel")
})
public class ConfiguracionNivelAux implements Serializable {
    
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    @SequenceGenerator(name = "SEC_PK_DPA_CONF_NIVELES", sequenceName = "SEC_PK_DPA_CONF_NIVELES", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_PK_DPA_CONF_NIVELES")
    private Long id;
    
    @Size(max = 1000)
    @Column(name = "descripcion")
    private String descripcion;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private Estado estado;
    
    @Size(max = 1)
    @Column(name = "nivel")
    private Long nivel;
    
    /*@OneToMany(cascade = CascadeType.ALL, mappedBy = "configuracion")
    private List<NivelAux> niveles;*/

    public ConfiguracionNivelAux() {
    }

    public ConfiguracionNivelAux(Long id) {
        this.id = id;
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

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Long getNivel() {
        return nivel;
    }

    public void setNivel(Long nivel) {
        this.nivel = nivel;
    }   
    
    /*@XmlTransient
    public List<NivelAux> getNiveles() {
        return niveles;
    }

    public void setNiveles(List<NivelAux> niveles) {
        this.niveles = niveles;
    }*/
    
}
