/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.entidad.indicador;

import co.stackpointer.cier.modelo.entidad.instrumento.Instrumento;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "ind_definiciones")
@Multitenant
@TenantDiscriminatorColumn(name = "id_tnt", contextProperty = "TENANT_ID", discriminatorType = DiscriminatorType.INTEGER)
@NamedQueries({
    @NamedQuery(name = "DefinicionIndicador.findAll", query = "SELECT d FROM DefinicionIndicador d"),
    @NamedQuery(name = "DefinicionIndicador.consultarFormulaSQL", query = "SELECT d.formula FROM DefinicionIndicador d where d.indicador.codigo = :codIndicador and d.nivelAgrupamiento = :nivelAgrupamiento and d.instrumento.codigo = :codInstrumento")})
public class DefinicionIndicador implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    
    @Column(name = "nivel_agrupamiento")
    private Integer nivelAgrupamiento;
    
    @JoinColumn(name = "cod_ins_instrumento", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private Instrumento instrumento;
    
    @Column(name = "formula")
    private String formula;
    
    @OneToMany(mappedBy = "definicion")
    private List<IndicadorValor> valores;
    
    @JoinColumn(name = "id_indicador", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Indicador indicador;

    public DefinicionIndicador() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNivelAgrupamiento() {
        return nivelAgrupamiento;
    }

    public void setNivelAgrupamiento(Integer nivelAgrupamiento) {
        this.nivelAgrupamiento = nivelAgrupamiento;
    }

    public Instrumento getInstrumento() {
        return instrumento;
    }

    public void setInstrumento(Instrumento instrumento) {
        this.instrumento = instrumento;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public List<IndicadorValor> getValores() {
        return valores;
    }

    public void setValores(List<IndicadorValor> valores) {
        this.valores = valores;
    }

    public Indicador getIndicador() {
        return indicador;
    }

    public void setIndicador(Indicador indicador) {
        this.indicador = indicador;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final DefinicionIndicador other = (DefinicionIndicador) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
}
