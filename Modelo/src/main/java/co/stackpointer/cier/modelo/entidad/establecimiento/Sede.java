/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.entidad.establecimiento;

import co.stackpointer.cier.modelo.interfaceDPA.SedeDPA;
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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OrderBy;
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
@Table(name = "est_sedes")
@Multitenant
@TenantDiscriminatorColumn(name = "id_tnt", contextProperty = "TENANT_ID", discriminatorType = DiscriminatorType.INTEGER)
@NamedQueries({
    @NamedQuery(name = "Sede.findAll", query = "SELECT s FROM Sede s"),
    @NamedQuery(name = "Sede.findPorId", query = "SELECT s FROM Sede s WHERE s.id = :idSede "),
    @NamedQuery(name = "Sede.consultarPorEstablecimiento", query = "SELECT s FROM Sede s WHERE s.establecimiento.id = :idEstablecimiento ORDER BY s.nombre asc"),
    @NamedQuery(name = "Sede.porCodigo", query = "SELECT s FROM Sede s WHERE s.codigo = :codigo"),
    @NamedQuery(name = "Sede.porCodigoEstablecimiento", query = "SELECT s FROM Sede s WHERE s.establecimiento.codigo = :codEstablecimiento"),
    @NamedQuery(name = "Sede.diferentesAsedeSeleccionada", query = "SELECT s FROM Sede s WHERE s <> :sede"),
    @NamedQuery(name = "Sede.porNivel", query = "SELECT s FROM Sede s WHERE s.establecimiento.nivel = :nivel"),
    @NamedQuery(name = "Sede.consultarPorListaEstablecimiento", query = "SELECT s FROM Sede s WHERE s.establecimiento.id in :establecimientos"),
    @NamedQuery(name = "Sede.porNombre", query = "SELECT s FROM Sede s WHERE LOWER(s.nombre) = :nombre"),
    @NamedQuery(name = "Sede.porCodigoSedeCodigoEstablecimiento", query = "SELECT s FROM Sede s WHERE s.codigo = :codSede and s.establecimiento.codigo = :codEstablecimiento")
})
public class Sede implements SedeDPA,Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    @SequenceGenerator(name = "SEC_PK_EST_SEDES", sequenceName = "SEC_PK_EST_SEDES", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEC_PK_EST_SEDES")
    private Long id;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "nombre")
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private Estado estado;

    @Column(name = "periodo")
    private Integer periodo;

    @JoinTable(name = "est_pre_sed", joinColumns =
        @JoinColumn(name = "id_sede", referencedColumnName = "id"),
            inverseJoinColumns =
        @JoinColumn(name = "id_predio", referencedColumnName = "id"))
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @OrderBy(value = "nombre ASC")
    private List<Predio> predios;

    @JoinColumn(name = "id_establecimiento", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Establecimiento establecimiento;


     @Column(name = "rector")
    private String rector;

    @Column(name = "email_rector")
    private String emailRector;

    @Column(name = "tel_rector")
    private String telRector;

    @Column(name = "email_sede")
    private String emailSede;

    @Column(name = "telefono_sede")
    private String telefono;

    @Column(name = "cod_dane")
    private String codDane;

    @Column(name = "nom_sector")
    private String nomSector;

    @Column(name = "cod_sector")
    private String codSector;

    @Column(name = "direccion_sede")
    private String direccion;

    @Column(name = "cod_autoridad")
    private String codAutoridad;

    @Column(name = "nom_autoridad")
    private String nomAutoridad;

    public Sede() {
    }

    public Sede(Long id) {
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

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    @Override
    public List<Predio> getPredios() {
        return predios;
    }

    public void setPredios(List<Predio> predios) {
        this.predios = predios;
    }

    public Establecimiento getEstablecimiento() {
        return establecimiento;
    }

    public void setEstablecimiento(Establecimiento establecimiento) {
        this.establecimiento = establecimiento;
    }

    public Integer getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Integer periodo) {
        this.periodo = periodo;
    }

    public String getRector() {
        return rector;
    }

    public void setRector(String rector) {
        this.rector = rector;
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

    public String getEmailSede() {
        return emailSede;
    }

    public void setEmailSede(String emailSede) {
        this.emailSede = emailSede;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCodDane() {
        return codDane;
    }

    public void setCodDane(String codDane) {
        this.codDane = codDane;
    }

    public String getNomSector() {
        return nomSector;
    }

    public void setNomSector(String nomSector) {
        this.nomSector = nomSector;
    }

    public String getCodSector() {
        return codSector;
    }

    public void setCodSector(String codSector) {
        this.codSector = codSector;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCodAutoridad() {
        return codAutoridad;
    }

    public void setCodAutoridad(String codAutoridad) {
        this.codAutoridad = codAutoridad;
    }

    public String getNomAutoridad() {
        return nomAutoridad;
    }

    public void setNomAutoridad(String nomAutoridad) {
        this.nomAutoridad = nomAutoridad;
    }

     @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final Sede other = (Sede) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

}
