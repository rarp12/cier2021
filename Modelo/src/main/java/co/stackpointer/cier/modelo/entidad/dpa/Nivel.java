/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.entidad.dpa;

import co.stackpointer.cier.modelo.interfaceDPA.NivelDPA;
import co.stackpointer.cier.modelo.entidad.establecimiento.Establecimiento;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "dpa_niveles")
@Multitenant
@TenantDiscriminatorColumn(name = "id_tnt", contextProperty = "TENANT_ID", discriminatorType = DiscriminatorType.INTEGER)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Nivel.findAll", query = "SELECT n FROM Nivel n"),
    @NamedQuery(name = "Nivel.porCodigo", query = "SELECT n FROM Nivel n WHERE n.codigo = :codigo"),
    @NamedQuery(name = "Nivel.porId", query = "SELECT n FROM Nivel n WHERE n.id = :id"),
    @NamedQuery(name = "Nivel.porNivConf", query = "SELECT n FROM Nivel n WHERE n.configuracion= :configuracion ORDER BY n.descripcion"),
    @NamedQuery(name = "Nivel.porPadre", query = "SELECT n FROM Nivel n WHERE n.padre = :padre ORDER BY n.descripcion"),
    @NamedQuery(name = "Nivel.consultarPorConfYPadreId", query = "SELECT n FROM Nivel n WHERE n.configuracion.nivel = :idConf and n.padre.id = :idpadre ORDER BY n.descripcion asc"),
    @NamedQuery(name = "Nivel.consultarPorConfYPadre", query = "SELECT n FROM Nivel n WHERE n.configuracion.nivel = :idConf and n.padre = :padre ORDER BY n.descripcion asc"),    
    @NamedQuery(name = "Nivel.findCodigoNConfig", query = "SELECT n FROM Nivel n WHERE n.codigo = :codigo and n.configuracion.nivel = :idNivelConfiguracion"),
    @NamedQuery(name = "Nivel.porCodigoYPadre", query = "SELECT n FROM Nivel n WHERE n.codigo = :codigo and n.padre.codigo = :codPadre"),
        
    @NamedQuery(name = "Nivel.porCodigoNivel", query = "SELECT n FROM Nivel n WHERE n.codigo = :codigo and n.configuracion.nivel = :nivel"),
    @NamedQuery(name = "Nivel.porCodigoNivelPadre", query = "SELECT n FROM Nivel n WHERE n.codigo = :codigo and n.configuracion.nivel = :nivel and n.padre.codigo = :codPadre"),
    @NamedQuery(name = "Nivel.consultarPorConfiguracion", query = "SELECT n FROM Nivel n WHERE n.configuracion.nivel = :idNivelConfiguracion order by n.descripcion"),
    @NamedQuery(name = "Nivel.porDescripcionNivelPadre", query = "SELECT n FROM Nivel n WHERE UPPER(n.descripcion) = :descripcion AND n.configuracion = :configuracion AND n.padre = :padre"),
    @NamedQuery(name = "Nivel.porDescripcionNivelPadreNoId", query = "SELECT n FROM Nivel n WHERE n.descripcion = :descripcion AND n.configuracion = :configuracion AND n.padre = :padre AND n.id <> :id")

})
public class Nivel implements NivelDPA,Serializable {
        
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    @SequenceGenerator(name = "SEC_PK_DPA_NIVELES", sequenceName = "SEC_PK_DPA_NIVELES", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_PK_DPA_NIVELES")
    private Long id;
    
    @Size(max = 20)
    @Column(name = "codigo")
    private String codigo;
    
    @Size(max = 1000)
    @Column(name = "descripcion")
    private String descripcion;
    
    @OneToMany(mappedBy = "padre")
    private List<Nivel> nivelesList;
    
    @JoinColumn(name = "padre", referencedColumnName = "id")
    @ManyToOne
    private Nivel padre;
    
    @JoinColumn(name = "nivel_configuracion", referencedColumnName = "id")
    @ManyToOne(optional = true)
    private ConfiguracionNivel configuracion;
        
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "nivel")
    private List<Establecimiento> establecimientoList;

    public Nivel() {
    }

    public Nivel(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<Nivel> getNivelesList() {
        return nivelesList;
    }

    public void setNivelesList(List<Nivel> nivelesList) {
        this.nivelesList = nivelesList;
    }

    public Nivel getPadre() {
        return padre;
    }

    public void setPadre(Nivel padre) {
        this.padre = padre;
    }

    public ConfiguracionNivel getConfiguracion() {
        return configuracion;
    }

    public void setConfiguracion(ConfiguracionNivel configuracion) {
        this.configuracion = configuracion;
    }

    @XmlTransient
    public List<Establecimiento> getEstablecimientoList() {
        return establecimientoList;
    }

    public void setEstablecimientoList(List<Establecimiento> establecimientoList) {
        this.establecimientoList = establecimientoList;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final Nivel other = (Nivel) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    
    
}
