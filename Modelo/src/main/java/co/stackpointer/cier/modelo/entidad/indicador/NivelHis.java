/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.entidad.indicador;


import co.stackpointer.cier.modelo.interfaceDPA.NivelDPA;
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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.TenantDiscriminatorColumn;

/**
 *
 * @author StackPointer
 */
@Entity
@Table(name = "ind_dpa_niveles")
@Multitenant
@TenantDiscriminatorColumn(name = "id_tnt", contextProperty = "TENANT_ID", discriminatorType = DiscriminatorType.INTEGER)
@NamedQueries({
    @NamedQuery(name = "NivelHis.findAll", query = "SELECT n FROM NivelHis n"),
    @NamedQuery(name = "NivelHis.consultarPorPeriodoCodigo", query = "SELECT n FROM NivelHis n WHERE n.pk.periodo = :periodo AND n.codigo = :codigo"),
    @NamedQuery(name = "NivelHis.consultarPorConfYPadre", query = "SELECT n FROM NivelHis n WHERE n.pk.periodo=:periodo and n.padre.pk.id = :idpadre ORDER BY n.descripcion asc")})
public class NivelHis implements NivelDPA,Serializable {

    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    private NivelHisPK pk;

    @Basic(optional = false)
    @NotNull
    @Column(name = "codigo")
    private String codigo;
    
    @Column(name = "descripcion")
    private String descripcion;
    
    @OneToMany( mappedBy = "nivel")
    private List<EstablecimientoHis> establecimientos;    

    @JoinColumns({
        @JoinColumn(name = "periodo", referencedColumnName = "periodo", insertable = false, updatable = false),
        @JoinColumn(name = "id_padre", referencedColumnName = "id")})
    @ManyToOne(optional = false)
    private NivelHis padre;
    
    @JoinColumns({
        @JoinColumn(name = "id_conf_nivel", referencedColumnName = "id"),
        @JoinColumn(name = "periodo", referencedColumnName = "periodo", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private ConfiguracionNivelHis configuracion;

    public NivelHis() {
    }

    public NivelHisPK getPk() {
        return pk;
    }

    public void setPk(NivelHisPK pk) {
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
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<EstablecimientoHis> getEstablecimientos() {
        return establecimientos;
    }

    public void setEstablecimientos(List<EstablecimientoHis> establecimientos) {
        this.establecimientos = establecimientos;
    }

    public NivelHis getPadre() {
        return padre;
    }

    public void setPadre(NivelHis padre) {
        this.padre = padre;
    }

    public ConfiguracionNivelHis getConfiguracion() {
        return configuracion;
    }

    public void setConfiguracion(ConfiguracionNivelHis configuracion) {
        this.configuracion = configuracion;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + (this.pk != null ? this.pk.hashCode() : 0);
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
        final NivelHis other = (NivelHis) obj;
        if (this.pk != other.pk && (this.pk == null || !this.pk.equals(other.pk))) {
            return false;
        }
        return true;
    }

   

   
}
