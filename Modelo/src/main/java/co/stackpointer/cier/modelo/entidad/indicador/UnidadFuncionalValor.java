/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.entidad.indicador;

import co.stackpointer.cier.modelo.entidad.instrumento.TipologiaValor;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.TenantDiscriminatorColumn;

/**
 *
 * @author StackPointer
 */
@Entity
@Table(name = "ins_unidades_funcionales_valor")
@Multitenant
@TenantDiscriminatorColumn(name = "id_tnt", contextProperty = "TENANT_ID", discriminatorType = DiscriminatorType.INTEGER)
@NamedQueries({
    @NamedQuery(name = "UnidadFuncionalValor.findAll", query = "SELECT u FROM UnidadFuncionalValor u order by u.id"),
    @NamedQuery(name = "UnidadFuncionalValor.findByUnidad", query = "SELECT u FROM UnidadFuncionalValor u WHERE u.unidadFuncional.id = :id"),
    @NamedQuery(name = "UnidadFuncionalValor.findByTipologia", query = "SELECT u FROM UnidadFuncionalValor u WHERE u.valorTipologia.tipologia.codigo = :codigo order by u.id")
})
public class UnidadFuncionalValor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    @Column(name = "periodo")
    private Integer periodo;
    @JoinColumn(name = "id_unidad_funcional", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private UnidadFuncional unidadFuncional;
    @JoinColumns({
        @JoinColumn(name = "cod_tipologia", referencedColumnName = "cod_tipologia"),
        @JoinColumn(name = "valor_tipologia", referencedColumnName = "codigo")})
    @OneToOne(optional = false)
    private TipologiaValor valorTipologia;

    public UnidadFuncionalValor() {
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

    public UnidadFuncional getUnidadFuncional() {
        return unidadFuncional;
    }

    public void setUnidadFuncional(UnidadFuncional unidadFuncional) {
        this.unidadFuncional = unidadFuncional;
    }

    public TipologiaValor getValorTipologia() {
        return valorTipologia;
    }

    public void setValorTipologia(TipologiaValor valorTipologia) {
        this.valorTipologia = valorTipologia;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final UnidadFuncionalValor other = (UnidadFuncionalValor) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
}
