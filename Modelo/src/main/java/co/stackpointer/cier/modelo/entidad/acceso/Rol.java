/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.entidad.acceso;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.TenantDiscriminatorColumn;

/**
 *
 * @author StackPointer
 */
@Entity
@Table(name = "aya_roles")
@Multitenant
@TenantDiscriminatorColumn(name = "id_tnt", contextProperty = "TENANT_ID", discriminatorType = DiscriminatorType.INTEGER)
@NamedQueries({    
    @NamedQuery(name = "Rol.findAll", query = "SELECT r FROM Rol r ORDER BY r.nombre"),
    @NamedQuery(name = "Rol.porNombre", query = "SELECT r FROM Rol r WHERE r.nombre = :nombre")})
public class Rol implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    @SequenceGenerator(name = "SEC_PK_AYA_ROLES", sequenceName = "SEC_PK_AYA_ROLES", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEC_PK_AYA_ROLES")
    //@CampoAuditable(nombre = "id")
    private Long id;
    
    @Size(max = 100)
    @Column(name = "nombre")
    //@CampoAuditable(nombre = "nombre", auditarEnModificacion = true, auditarEnEliminacion = true)
    private String nombre;
    
    @Size(max = 1000)
    @Column(name = "descripcion")
    //@CampoAuditable(nombre = "descripcion", auditarEnModificacion = true, auditarEnEliminacion = false)
    private String descripcion;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    //@CampoAuditable(nombre = "estado", auditarEnModificacion = true, auditarEnEliminacion = false)
    private Estado estado;
    
    /*@OneToMany(cascade = CascadeType.ALL, mappedBy = "rol")
    private List<UsuarioRol> usuariosRolesList;*/

    /*@OneToOne(cascade = CascadeType.ALL, mappedBy = "idRol")
     private RolPermiso rolesPermisos;*/
    public Rol() {
        estado = Estado.A;
    }

    public Rol(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    /*public List<UsuarioRol> getUsuariosRolesList() {
        return usuariosRolesList;
    }

    public void setUsuariosRolesList(List<UsuarioRol> usuariosRolesList) {
        this.usuariosRolesList = usuariosRolesList;
    }*/

    /* public RolPermiso getRolesPermisos() {
     return rolesPermisos;
     }

     public void setRolesPermisos(RolPermiso rolesPermisos) {
     this.rolesPermisos = rolesPermisos;
     }*/

    
    protected String getIdEntidad() {
        return this.id + "";
    }

    @Override
    public String toString() {
        return this.id + "";
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final Rol other = (Rol) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
}
