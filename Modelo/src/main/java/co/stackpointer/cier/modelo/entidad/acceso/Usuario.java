/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.entidad.acceso;

import co.stackpointer.cier.modelo.entidad.dpa.ConfiguracionNivel;
import co.stackpointer.cier.modelo.entidad.dpa.Nivel;
import co.stackpointer.cier.modelo.entidad.establecimiento.Establecimiento;
import co.stackpointer.cier.modelo.entidad.sistema.Idioma;
import co.stackpointer.cier.modelo.tipo.Estado;
import co.stackpointer.cier.modelo.utilidad.UtilProperties;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
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
import javax.validation.constraints.Size;
import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.TenantDiscriminatorColumn;

/**
 *
 * @author StackPointer
 */
@Entity
@Table(name = "aya_usuarios")
@Multitenant
@TenantDiscriminatorColumn(name = "id_tnt", contextProperty = "TENANT_ID", discriminatorType = DiscriminatorType.INTEGER)
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u ORDER BY u.primerNombre"),
    @NamedQuery(name = "Usuario.porUsername", query = "SELECT u FROM Usuario u WHERE u.username = :username")})
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public static String SISTEMA = "SISTEMA";
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    @SequenceGenerator(name = "SEC_PK_AYA_USUARIOS", sequenceName = "SEC_PK_AYA_USUARIOS", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEC_PK_AYA_USUARIOS")
    //@CampoAuditable(nombre = "id")
    private Long id;
    
    @Size(max = 20)
    @Column(name = "identificacion")
    //@CampoAuditable(nombre = "identificacion", auditarEnModificacion = true)
    private String identificacion;
    
    @Size(max = 100)
    @Column(name = "nombre_1")
    //@CampoAuditable(nombre = "primerNombre", auditarEnModificacion = true)
    private String primerNombre;
    
    @Size(max = 100)
    @Column(name = "nombre_2")
    //@CampoAuditable(nombre = "segundoNombre", auditarEnModificacion = true)
    private String segundoNombre;
    
    @Size(max = 100)
    @Column(name = "apellido_1")
    //@CampoAuditable(nombre = "primerApellido", auditarEnModificacion = true)
    private String primerApellido;
    
    @Size(max = 100)
    @Column(name = "apellido_2")
    //@CampoAuditable(nombre = "segundoApellido", auditarEnModificacion = true)
    private String segundoApellido;
    
    @Size(max = 1000)
    @Column(name = "correo")
    //@CampoAuditable(nombre = "correo", auditarEnModificacion = true)
    private String correo;
    
    @Size(max = 20)
    @Column(name = "usuario")
    //@CampoAuditable(nombre = "username", auditarEnModificacion = true)
    private String username;
    
    @Size(max = 500)
    @Column(name = "password")
    private String password;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    //@CampoAuditable(nombre = "estado", auditarEnModificacion = true)
    private Estado estado;
    
    @JoinColumn(name = "id_nivel_dpa", referencedColumnName = "id")
    @ManyToOne(optional = true)
    private ConfiguracionNivel nivelDpa;
    
    @JoinColumn(name = "id_nivel_especifico", referencedColumnName = "id")
    @ManyToOne(optional = true)
    private Nivel nivelEspecifico;    
    
    @JoinColumn(name = "id_establecimiento", referencedColumnName = "id")
    @ManyToOne(optional = true)
    private Establecimiento establecimiento;
 
    @JoinColumn(name = "idioma", referencedColumnName = "id")
    @ManyToOne(optional = true)
    private Idioma idioma;
    
    @Column
    private int restablecer;
    
    /*@AsociacionAuditable(nombre = "usuarioRol")
    @OneToMany(mappedBy = "usuario")
    private List<UsuarioRol> roles;*/

    public Usuario() {        
        estado = Estado.A;
        idioma = UtilProperties.getIdiomaPais();
    }

    public Usuario(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public ConfiguracionNivel getNivelDpa() {
        return nivelDpa;
    }

    public void setNivelDpa(ConfiguracionNivel nivelDpa) {
        this.nivelDpa = nivelDpa;
    }

    public Nivel getNivelEspecifico() {
        return nivelEspecifico;
    }

    public void setNivelEspecifico(Nivel nivelEspecifico) {
        this.nivelEspecifico = nivelEspecifico;
    }
    
    public Establecimiento getEstablecimiento() {
        return establecimiento;
    }

    public void setEstablecimiento(Establecimiento establecimiento) {
        this.establecimiento = establecimiento;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    public int getRestablecer() {
        return restablecer;
    }

    public void setRestablecer(int restablecer) {
        this.restablecer = restablecer;
    }
    
    /*public List<UsuarioRol> getRoles() {
        return roles;
    }

    public void setRoles(List<UsuarioRol> roles) {
        this.roles = roles;
    }*/
    
    public String getNombreSimple( ){
        return String.format("%s %s", primerNombre, primerApellido);
    }
    
    protected String getIdEntidad() {
        return this.id + "";
    }

    @Override
    public String toString() {
        return this.id + "";
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final Usuario other = (Usuario) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
}
