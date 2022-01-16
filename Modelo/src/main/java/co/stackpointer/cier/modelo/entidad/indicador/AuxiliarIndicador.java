package co.stackpointer.cier.modelo.entidad.indicador;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.TenantDiscriminatorColumn;

/**
 *
 * @author StackPointer
 */
@Entity
@Table(name = "ind_auxiliar_indicador")
@Multitenant
@TenantDiscriminatorColumn(name = "id_tnt", contextProperty = "TENANT_ID", discriminatorType = DiscriminatorType.INTEGER)
@NamedQueries({
    @NamedQuery(name = "AuxiliarIndicador.findAll", query = "SELECT a FROM AuxiliarIndicador a"),
    @NamedQuery(name = "AuxiliarIndicador.consultarPeriodos", query = "SELECT DISTINCT a.periodo FROM AuxiliarIndicador a ORDER BY a.periodo ASC"),
    @NamedQuery(name = "AuxiliarIndicador.consultarPeriodosDesdeCiertoPeriodo", query = "SELECT DISTINCT a.periodo FROM AuxiliarIndicador a WHERE a.periodo > :periodo ORDER BY a.periodo ASC")})
public class AuxiliarIndicador implements Serializable {
    private static final long serialVersionUID = 1L;
    //@Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "periodo")
    private Integer periodo;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_instrumento")
    private Long idInstrumento;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_predio")
    private Long idPredio;

    public AuxiliarIndicador() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Integer periodo) {
        this.periodo = periodo;
    }

    public Long getIdInstrumento() {
        return idInstrumento;
    }

    public void setIdInstrumento(Long idInstrumento) {
        this.idInstrumento = idInstrumento;
    }

    public Long getIdPredio() {
        return idPredio;
    }

    public void setIdPredio(Long idPredio) {
        this.idPredio = idPredio;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final AuxiliarIndicador other = (AuxiliarIndicador) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    
    
}
