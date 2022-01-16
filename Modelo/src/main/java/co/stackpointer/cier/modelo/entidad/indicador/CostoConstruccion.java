/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.entidad.indicador;

import co.stackpointer.cier.modelo.tipo.TipoCostoConstruccion;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
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
@Table(name = "ind_base_costos_construccion")
@Multitenant
@TenantDiscriminatorColumn(name = "id_tnt", contextProperty = "TENANT_ID", discriminatorType = DiscriminatorType.INTEGER)
@NamedQuery(name = "CostoConstruccion.findAll", query = "SELECT c FROM CostoConstruccion c order by c.unidadMedida ASC")
public class CostoConstruccion implements Serializable {    
    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;   
        
    @Column(name = "tipo")
    @Enumerated(EnumType.STRING)
    private TipoCostoConstruccion tipo;
        
    @Column(name = "cod_unidad_funcional")
    private String unidadFuncional;
    
    @Column(name = "unidad_medida")
    private String unidadMedida;
    
    @Column(name = "valor_dotacion")
    private BigDecimal valorDotacion;
    
    @Column(name = "valor_obra_civil")
    private BigDecimal valorObraCivil;
    
    @Column(name = "valor_total")
    private BigDecimal valorTotal;

    public CostoConstruccion() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoCostoConstruccion getTipo() {
        return tipo;
    }

    public void setTipo(TipoCostoConstruccion tipo) {
        this.tipo = tipo;
    }

    public String getUnidadFuncional() {
        return unidadFuncional;
    }

    public String getEtiqueta() {
        return "bd.ind_base_costos_construccion.unidadFuncional."+unidadFuncional;
    }

    public void setUnidadFuncional(String unidadFuncional) {
        this.unidadFuncional = unidadFuncional;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public BigDecimal getValorDotacion() {
        return valorDotacion;
    }

    public void setValorDotacion(BigDecimal valorDotacion) {
        this.valorDotacion = valorDotacion;
    }

    public BigDecimal getValorObraCivil() {
        return valorObraCivil;
    }

    public void setValorObraCivil(BigDecimal valorObraCivil) {
        this.valorObraCivil = valorObraCivil;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

   
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 43 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final CostoConstruccion other = (CostoConstruccion) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }        
    
}
