/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.entidad.indicador;

import co.stackpointer.cier.modelo.interfaceDPA.SedeDPA;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "ind_est_sedes")
@Multitenant
@TenantDiscriminatorColumn(name = "id_tnt", contextProperty = "TENANT_ID", discriminatorType = DiscriminatorType.INTEGER)
@NamedQueries({
    @NamedQuery(name = "SedeHis.findAll", query = "SELECT e FROM SedeHis e")})
public class SedeHis implements SedeDPA,Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private SedeHisPK pk;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "codigo")
    private String codigo;
    
    @Column(name = "nombre")
    private String nombre;
    
    @Column(name = "estado")
    private String estado;
    
    @JoinTable(name = "ind_est_pre_sed", joinColumns = {
        @JoinColumn(name = "id_sede", referencedColumnName = "id"),
        @JoinColumn(name = "periodo", referencedColumnName = "periodo")},
            inverseJoinColumns = {
        @JoinColumn(name = "id_predio", referencedColumnName = "id"),
        @JoinColumn(name = "periodo", referencedColumnName = "periodo")})
    @ManyToMany
    @OrderBy(value = "nombre ASC")
    private List<PredioHis> predios;
    
    @JoinColumns({
        @JoinColumn(name = "periodo", referencedColumnName = "periodo", insertable = false, updatable = false),
        @JoinColumn(name = "id_establecimiento", referencedColumnName = "id")})
    @ManyToOne(optional = false)
    private EstablecimientoHis establecimiento;

    public SedeHis() {
    }

    public SedeHisPK getPk() {
        return pk;
    }

    public void setPk(SedeHisPK pk) {
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public List<PredioHis> getPredios() {
        return predios;
    }

    public void setPredios(List<PredioHis> predios) {
        this.predios = predios;
    }

    public EstablecimientoHis getEstablecimiento() {
        return establecimiento;
    }

    public void setEstablecimiento(EstablecimientoHis establecimiento) {
        this.establecimiento = establecimiento;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + (this.pk != null ? this.pk.hashCode() : 0);
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
        final SedeHis other = (SedeHis) obj;
        if (this.pk != other.pk && (this.pk == null || !this.pk.equals(other.pk))) {
            return false;
        }
        return true;
    }
}
