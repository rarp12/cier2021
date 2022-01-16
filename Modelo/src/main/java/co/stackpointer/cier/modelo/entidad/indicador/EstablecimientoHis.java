/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.entidad.indicador;

import co.stackpointer.cier.modelo.interfaceDPA.EstablecimientoDPA;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.TenantDiscriminatorColumn;

/**
 *
 * @author StackPointer
 */
@Entity
@Table(name = "ind_est_establecimientos")
@Multitenant
@TenantDiscriminatorColumn(name = "id_tnt", contextProperty = "TENANT_ID", discriminatorType = DiscriminatorType.INTEGER)
@NamedQueries({
    @NamedQuery(name = "EstablecimientoHis.findAll", query = "SELECT e FROM EstablecimientoHis e"),
    @NamedQuery(name = "EstablecimientoHis.consultarPorPeridoYNivel", query = "SELECT e FROM EstablecimientoHis e WHERE e.pk.periodo = :periodo and e.nivel.pk.id = :idnivel ORDER BY e.nombre asc")})
public class EstablecimientoHis implements EstablecimientoDPA,Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EstablecimientoHisPK pk;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "codigo")
    private String codigo;
    
    @Column(name = "nombre")
    private String nombre;
    
    @Column(name = "rector")
    private String rector;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado")
    private String estado;
    
    @OneToMany(mappedBy = "establecimiento")
    @OrderBy(value = "nombre ASC")
    private List<SedeHis> sedes;
    
    @JoinColumns({
        @JoinColumn(name = "periodo", referencedColumnName = "periodo", insertable = false, updatable = false),
        @JoinColumn(name = "id_nivel", referencedColumnName = "id")})
    @ManyToOne(optional = false)
    private NivelHis nivel;

    public EstablecimientoHis() {
    }

    public EstablecimientoHisPK getPk() {
        return pk;
    }

    public void setPk(EstablecimientoHisPK pk) {
        this.pk = pk;
    }   
    
    @Override
    public Long getId() {
        return pk != null ? pk.getId() : null;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public List<SedeHis> getSedes() {
        return sedes;
    }

    public void setSedes(List<SedeHis> sedes) {
        this.sedes = sedes;
    }

    public NivelHis getNivel() {
        return nivel;
    }

    public void setNivel(NivelHis nivel) {
        this.nivel = nivel;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + (this.pk != null ? this.pk.hashCode() : 0);
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
        final EstablecimientoHis other = (EstablecimientoHis) obj;
        if (this.pk != other.pk && (this.pk == null || !this.pk.equals(other.pk))) {
            return false;
        }
        return true;
    }

}
