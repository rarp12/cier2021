/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.entidad.establecimiento;

import co.stackpointer.cier.modelo.entidad.dpa.Nivel;
import co.stackpointer.cier.modelo.interfaceDPA.EstablecimientoDPA;
import co.stackpointer.cier.modelo.tipo.Estado;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.TenantDiscriminatorColumn;

/**
 *
 * @author StackPointer
 */
@Entity
@Table(name = "est_establecimientos")
@Multitenant
@TenantDiscriminatorColumn(name = "id_tnt", contextProperty = "TENANT_ID", discriminatorType = DiscriminatorType.INTEGER)
@NamedQueries({
    @NamedQuery(name = "Establecimiento.porCodigo", query = "SELECT e FROM Establecimiento e WHERE e.codigo = :codigo"),
    @NamedQuery(name = "Establecimiento.consultarPorNivel", query = "SELECT e FROM Establecimiento e WHERE e.nivel.id = :idnivel ORDER BY e.nombre asc"),
    @NamedQuery(name = "Establecimiento.consultarPorNivelCompleto", query = "SELECT e FROM Establecimiento e WHERE e.nivel = :nivel ORDER BY e.nombre asc"),
    @NamedQuery(name = "Establecimiento.findAll", query = "SELECT e FROM Establecimiento e"),
    @NamedQuery(name = "Establecimiento.consultarPorListaNivel", query = "SELECT e FROM Establecimiento e WHERE e.nivel.id in :niveles ORDER BY e.nombre asc"),
    @NamedQuery(name = "Establecimiento.porNombre", query = "SELECT e FROM Establecimiento e WHERE LOWER(e.nombre) = :nombre")
})
public class Establecimiento implements EstablecimientoDPA,Serializable {
    
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    @SequenceGenerator(name = "SEC_PK_EST_ESTABLECIMIENTOS", sequenceName = "SEC_PK_EST_ESTABLECIMIENTOS", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEC_PK_EST_ESTABLECIMIENTOS")
    private Long id;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "codigo")
    private String codigo;
    
    @Column(name = "nombre")
    private String nombre;

    @Column(name = "rector")
    private String rector;
    
    @Column(name = "email_rector")
    private String emailRector;
    
    @Column(name = "tel_rector")
    private String telRector;
    
    @Column(name = "email_est")
    private String emailEst;
    
    @Column(name = "telefono_est")
    private String telefonoEst;    

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private Estado estado;
    
    @Column(name = "periodo")
    private Integer periodo;
    
    @Column(name = "cod_dane")
    private String codDane;
    
    @Column(name = "sector")
    private String sector;
    
    @JoinColumn(name = "id_nivel", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Nivel nivel;
    
    @OneToMany(mappedBy = "establecimiento")
    private List<Sede> sedes;

    public Establecimiento() {
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

    public String getEmailEst() {
        return emailEst;
    }

    public void setEmailEst(String emailEst) {
        this.emailEst = emailEst;
    }

    public String getTelefonoEst() {
        return telefonoEst;
    }

    public void setTelefonoEst(String telefonoEst) {
        this.telefonoEst = telefonoEst;
    }

    public Establecimiento(Long id) {
        this.id = id;
    }

    public Establecimiento(Long id, String codigo) {
        this.id = id;
        this.codigo = codigo;
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
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getRector() {
        return rector;
    }

    public void setRector(String rector) {
        this.rector = rector;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    
    public Integer getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Integer periodo) {
        this.periodo = periodo;
    }

    public String getCodDane() {
        return codDane;
    }
    
    public void setCodDane(String codDane) {
        this.codDane = codDane;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }    

    public Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }

    @Override
    public List<Sede> getSedes() {
        return sedes;
    }

     public void setSedes(List<Sede> sedes) {
        this.sedes = sedes;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Establecimiento)) {
            return false;
        }
        Establecimiento other = (Establecimiento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.stackpointer.cier.modelo.entidades.est.Establecimiento[ id=" + id + " ]";
    }
    
}
