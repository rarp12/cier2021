/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.entidad.acceso;

import co.stackpointer.cier.modelo.tipo.Estado;
import java.io.Serializable;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.TenantDiscriminatorColumn;

/**
 *
 * @author StackPointer
 */
@Entity
@Table(name = "aya_usuarios_roles")
@Multitenant
@TenantDiscriminatorColumn(name = "id_tnt", contextProperty = "TENANT_ID", discriminatorType = DiscriminatorType.INTEGER)
@NamedQueries({
    @NamedQuery(name = "UsuarioRol.findAll", query = "SELECT u FROM UsuarioRol u"),
    @NamedQuery(name = "UsuarioRol.buscarPorRolYUsuario", query = "SELECT u FROM UsuarioRol u WHERE u.rol = :rol AND u.usuario = :usuario"),
    @NamedQuery(name = "UsuarioRol.rolesAsignados", query = "SELECT u.rol FROM UsuarioRol u WHERE u.estado=co.stackpointer.cier.modelo.tipo.Estado.A AND u.rol.estado=co.stackpointer.cier.modelo.tipo.Estado.A AND u.usuario = :usuario"),
    @NamedQuery(name = "UsuarioRol.rolesNoAsignados", query = "SELECT r FROM Rol r WHERE r.estado=co.stackpointer.cier.modelo.tipo.Estado.A AND r.id NOT IN (SELECT u.rol.id FROM UsuarioRol u WHERE u.estado=co.stackpointer.cier.modelo.tipo.Estado.A AND u.rol.estado=co.stackpointer.cier.modelo.tipo.Estado.A AND u.usuario = :usuario)"),
    @NamedQuery(name = "UsuarioRol.usuariosPorRol", query = "SELECT u.usuario FROM UsuarioRol u WHERE u.estado=co.stackpointer.cier.modelo.tipo.Estado.A AND u.rol = :rol")})
public class UsuarioRol implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    @SequenceGenerator(name = "SEC_PK_AYA_USUARIOS_ROLES", sequenceName = "SEC_PK_AYA_USUARIOS_ROLES", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEC_PK_AYA_USUARIOS_ROLES")    
    //@CampoAuditable(nombre = "id")
    private Long id;
    
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    @ManyToOne(optional = false)
    //@CampoAuditable(nombre = "usuario", auditarEnModificacion = false)
    private Usuario usuario;
    
    @JoinColumn(name = "id_rol", referencedColumnName = "id")
    @ManyToOne(optional = false)
    //@CampoAuditable(nombre = "rol", auditarEnModificacion = false)
    private Rol rol;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    //@CampoAuditable(nombre = "estado", auditarEnModificacion = true)
    private Estado estado;

    public UsuarioRol() {
        estado = Estado.A;
    }

    public UsuarioRol(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    
    protected String getIdEntidad() {
        return this.id + "";
    }
    
}
