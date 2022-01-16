/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.entidad.establecimiento;

import co.stackpointer.cier.modelo.interfaceDPA.PredioDPA;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.TenantDiscriminatorColumn;

/**
 *
 * @author StackPointer
 */
@Entity
@Table(name = "est_predios")
@Multitenant
@TenantDiscriminatorColumn(name = "id_tnt", contextProperty = "TENANT_ID", discriminatorType = DiscriminatorType.INTEGER)
@NamedQueries({@NamedQuery(name = "Predio.findAll", query = "SELECT p FROM Predio p")
    ,@NamedQuery(name = "Predio.buscarPorId", query = "SELECT p FROM Predio p WHERE p.id = :idPredio")
    ,@NamedQuery(name = "Predio.findByCodigo", query = "SELECT p FROM Predio p where p.codigo = :codigo")
    ,@NamedQuery(name = "Predio.findByCodigoNombre", query = "SELECT p FROM Predio p where p.codigo = :codigo or "
        + "p.nombre like :nombre")
    ,@NamedQuery(name = "Predio.findByCodigoOficial", query = "SELECT p FROM Predio p where p.codigoOficial = :codigoOficial")
    ,@NamedQuery(name = "Predio.porCodigo", query = "SELECT p FROM Predio p where p.codigo = :codigo")})

public class Predio implements PredioDPA,Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    @SequenceGenerator(name = "SEC_PK_EST_PREDIO", sequenceName = "SEC_PK_EST_PREDIO", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEC_PK_EST_PREDIO")
    private Long id;
    
    @Column(name = "codigo")
    private String codigo;
    
    @Column(name = "nombre")
    private String nombre;
    
    @Column(name = "codigo_oficial")
    private String codigoOficial;
    
    @Column(name = "descripcion")
    private String descripcion;
    
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;
    
    @Column(name = "estado")
    private String estado;
    
    @JoinTable(name = "est_pre_sed", joinColumns = 
        @JoinColumn(name = "id_predio", referencedColumnName = "id"),
            inverseJoinColumns = 
    @JoinColumn(name = "id_sede", referencedColumnName = "id"))
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @OrderBy(value = "nombre ASC")     
    private List<Sede> sede;

    public Predio() {
    }

    public Predio(Long id) {
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
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public String getCodigoOficial() {
        return codigoOficial;
    }

    public void setCodigoOficial(String codigoOficial) {
        this.codigoOficial = codigoOficial;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    public List<Sede> getSede() {
        return sede;
    }

    public void setSede(List<Sede> sede) {
        this.sede = sede;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 73 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final Predio other = (Predio) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
}
