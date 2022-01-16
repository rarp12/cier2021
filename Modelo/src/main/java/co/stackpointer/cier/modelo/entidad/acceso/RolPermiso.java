/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.entidad.acceso;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
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
@Table(name = "aya_roles_permisos")
@Multitenant
@TenantDiscriminatorColumn(name = "id_tnt", contextProperty = "TENANT_ID", discriminatorType = DiscriminatorType.INTEGER)
@NamedQueries({
    @NamedQuery(name = "RolPermiso.getId", query = "SELECT (max(r.id)+1) FROM RolPermiso r"),
    @NamedQuery(name = "RolPermiso.findAll", query = "SELECT r FROM RolPermiso r"),
    @NamedQuery(name = "RolPermiso.modulosPorUsername", query = "SELECT DISTINCT rp.modulo FROM RolPermiso rp, UsuarioRol ur WHERE rp.rol.id = ur.rol.id AND rp.rol.estado=co.stackpointer.cier.modelo.tipo.Estado.A AND rp.rol.estado=co.stackpointer.cier.modelo.tipo.Estado.A AND rp.funcionalidad.estado=co.stackpointer.cier.modelo.tipo.Estado.A AND ur.estado=co.stackpointer.cier.modelo.tipo.Estado.A AND ur.usuario.username = :username "),
    @NamedQuery(name = "RolPermiso.funcionalidadesPorModulosYUsername", query = "SELECT DISTINCT rp.funcionalidad FROM RolPermiso rp, UsuarioRol ur WHERE rp.rol.id = ur.rol.id AND rp.rol.estado=co.stackpointer.cier.modelo.tipo.Estado.A AND rp.modulo.estado=co.stackpointer.cier.modelo.tipo.Estado.A AND rp.funcionalidad.estado=co.stackpointer.cier.modelo.tipo.Estado.A AND ur.estado=co.stackpointer.cier.modelo.tipo.Estado.A AND rp.modulo.codigo = :codmodulo AND ur.usuario.username = :username"),
    @NamedQuery(name = "RolPermiso.porFuncionalidadYRol", query = "SELECT r FROM RolPermiso r WHERE r.funcionalidad = :funcionalidad AND r.rol = :rol")})
public class RolPermiso implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    @SequenceGenerator(name = "SEC_PK_AYA_ROLES_PERMISOS", sequenceName = "SEC_PK_AYA_ROLES_PERMISOS", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEC_PK_AYA_ROLES_PERMISOS")
    //@CampoAuditable(nombre = "id")
    private Long id;
    
    @JoinColumn(name = "id_rol", referencedColumnName = "id")
    @ManyToOne(optional = false)
    //@CampoAuditable(nombre = "rol")
    private Rol rol;
    
    @JoinColumn(name = "cod_modulo", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    //@CampoAuditable(nombre = "modulo")
    private Modulo modulo;
    
    @JoinColumn(name = "cod_funcionalidad", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    //@CampoAuditable(nombre = "funcionalidad")
    private Funcionalidad funcionalidad;

    public RolPermiso() {
    }

    public RolPermiso(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Modulo getModulo() {
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }

    public Funcionalidad getFuncionalidad() {
        return funcionalidad;
    }

    public void setFuncionalidad(Funcionalidad funcionalidad) {
        this.funcionalidad = funcionalidad;
    }
    
    protected String getIdEntidad() {
        return this.id + "";
    }
}
