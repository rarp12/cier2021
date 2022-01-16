/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.entidad.sistema;

import co.stackpointer.cier.modelo.utilidad.UtilFecha;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author StackPointer
 */
@Entity
@Table(name = "sys_tenants")
@NamedQueries({
    @NamedQuery(name = "Tenant.findAll", query = "SELECT t FROM Tenant t"),
    @NamedQuery(name = "Tenant.porPais", query = "SELECT t FROM Tenant t WHERE t.pais = :pais"),
    @NamedQuery(name = "Tenant.porAbreviatura", query = "SELECT t FROM Tenant t WHERE t.abreviatura = :abreviatura")})
public class Tenant implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    @SequenceGenerator(name = "SEC_PK_TENANT", sequenceName = "SEC_PK_SYS_TENANTS", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEC_PK_TENANT")
    //@CampoAuditable(nombre = "id")
    private Long id;
    
    @Size(max = 100)
    @NotNull
    @Column(name = "pais")
    //@CampoAuditable(nombre = "pais", auditarEnModificacion = true)
    private String pais;
    
    @Size(max = 50)
    @NotNull
    @Column(name = "abreviatura")
    //@CampoAuditable(nombre = "abreviatura", auditarEnModificacion = true)
    private String abreviatura;
    
    @JoinColumn(name = "idioma", referencedColumnName = "id")
    @ManyToOne(optional = false)
    //@CampoAuditable(nombre = "idioma", auditarEnModificacion = false)
    private Idioma idioma;
    
    @Size(max = 500)
    @NotNull
    @Column(name = "locale")
    //@CampoAuditable(nombre = "locale", auditarEnModificacion = true)
    private String locale;
    
    @Size(max = 500)
    @NotNull
    @Column(name = "timezone")
    //@CampoAuditable(nombre = "timezone", auditarEnModificacion = true)
    private String timezone;
    
    @NotNull
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.DATE)
    //@CampoAuditable(nombre = "fechaCreacion")
    private Date fechaCreacion;
    
    @Size(max = 50)    
    @Column(name = "bandera")
    private String bandera;
    
    @Size(max = 20)    
    @Column(name = "codigo_dpa_niveles")
    private String codigoPais;

    public Tenant() {
        fechaCreacion = UtilFecha.obtenerFechaActual();
    }

    public Tenant(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getBandera() {
        return bandera;
    }

    public void setBandera(String bandera) {
        this.bandera = bandera;
    }

    public String getCodigoPais() {
        return codigoPais;
    }

    public void setCodigoPais(String codigoPais) {
        this.codigoPais = codigoPais;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final Tenant other = (Tenant) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    protected String getIdEntidad() {
        return this.id + "";
    }

    @Override
    public String toString() {
        return this.id + "";
    }
    
}
