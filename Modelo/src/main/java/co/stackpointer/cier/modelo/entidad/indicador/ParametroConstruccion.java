/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.entidad.indicador;

import co.stackpointer.cier.modelo.entidad.dpa.Nivel;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorType;
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
import javax.validation.constraints.NotNull;
import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.TenantDiscriminatorColumn;

/**
 *
 * @author rarp1
 */

@Entity
@Table(name = "ind_param_costos_const")
@Multitenant
@TenantDiscriminatorColumn(name = "id_tnt", contextProperty = "TENANT_ID", discriminatorType = DiscriminatorType.INTEGER)
@NamedQueries({ @NamedQuery(name = "ParametrosConstruccion.findAll", query = "SELECT u FROM ParametroConstruccion u order by u.id"),
    @NamedQuery(name = "ParametrosConstruccion.getByMunPerUnfun", query = "SELECT u FROM ParametroConstruccion u where u.unidadFuncional = :unidadFuncional and u.nivel = :nivel and u.periodo = :periodo "),
    @NamedQuery(name = "ParametrosConstruccion.findAllbyPeriodo", query = "SELECT u FROM ParametroConstruccion u where u.periodo = :periodo order by u.id")
})
public class ParametroConstruccion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    @SequenceGenerator(name = "SEC_PK_IND_ESTANDAR_CONS", sequenceName = "SEC_PK_IND_ESTANDAR_CONS", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_PK_IND_ESTANDAR_CONS")
    private Long id;
    @Column(name = "tipo")
    @NotNull
    private String tipo;
    @ManyToOne
    @JoinColumn(name = "cod_unidad_funcional", referencedColumnName = "id")
    private UnidadFuncional unidadFuncional;
    @Column(name = "nivel")
    @NotNull
    private Long idNivel;
    @Column(name = "unidad_medida")
    @NotNull
    private String unidadMedida;
    @Column(name = "rural")
    private BigDecimal valorRural ;
    @Column(name = "rural_aislado")
    private BigDecimal valorRuralAhislado;
    @Column(name = "rural_urbe_perimetral")
    private BigDecimal valorRuralUrbePerimetral;
    @Column(name = "urbano")
    private BigDecimal valorUrbano;
    @Column(name = "urbano_marginal")
    private BigDecimal valorUrbanoMarginal;
    @Column(name = "otro")
    private BigDecimal otro;
    @Column(name = "periodo")
    private Long periodo;
    @ManyToOne
    @JoinColumn(name = "id_dpa", referencedColumnName = "id")
    private Nivel nivel;

    public ParametroConstruccion() {
    }

    public Long getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public UnidadFuncional getUnidadFuncional() {
        return unidadFuncional;
    }

    public void setUnidadFuncional(UnidadFuncional unidadFuncional) {
        this.unidadFuncional = unidadFuncional;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public BigDecimal getValorRural() {
        return valorRural;
    }

    public void setValorRural(BigDecimal valorRural) {
        this.valorRural = valorRural;
    }

    public BigDecimal getValorRuralAhislado() {
        return valorRuralAhislado;
    }

    public void setValorRuralAhislado(BigDecimal valorRuralAhislado) {
        this.valorRuralAhislado = valorRuralAhislado;
    }

    public BigDecimal getValorRuralUrbePerimetral() {
        return valorRuralUrbePerimetral;
    }

    public void setValorRuralUrbePerimetral(BigDecimal valorRuralUrbePerimetral) {
        this.valorRuralUrbePerimetral = valorRuralUrbePerimetral;
    }

    public BigDecimal getValorUrbano() {
        return valorUrbano;
    }

    public void setValorUrbano(BigDecimal urbano) {
        this.valorUrbano = urbano;
    }

    public BigDecimal getValorUrbanoMarginal() {
        return valorUrbanoMarginal;
    }

    public void setValorUrbanoMarginal(BigDecimal urbanoMarginal) {
        this.valorUrbanoMarginal = urbanoMarginal;
    }

    public BigDecimal getOtro() {
        return otro;
    }

    public void setOtro(BigDecimal otro) {
        this.otro = otro;
    }

    public Long getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Long periodo) {
        this.periodo = periodo;
    }

    @Override
    public int hashCode() {
        int hash = 3;
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
        final ParametroConstruccion other = (ParametroConstruccion) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }

    public Long getIdNivel() {
        return idNivel;
    }

    public void setIdNivel(Long idNivel) {
        this.idNivel = idNivel;
    }
}
