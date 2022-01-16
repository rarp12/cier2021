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
@Table(name = "IND_PERIODO_COSTOS_CONST")
@Multitenant
@TenantDiscriminatorColumn(name = "id_tnt", contextProperty = "TENANT_ID", discriminatorType = DiscriminatorType.INTEGER)
@NamedQueries({ @NamedQuery(name = "PeriodosCostosConstruccion.findAll", query = "SELECT u FROM PeriodosCostosConstruccion u where u.estado = 'A' order by u.periodo")})
public class PeriodosCostosConstruccion implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @NotNull
    private Long periodo;
    
    @Column
    private String estado;

    public PeriodosCostosConstruccion() {
    }

    public Long getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Long periodo) {
        this.periodo = periodo;
    }
    
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + (this.periodo != null ? this.periodo.hashCode() : 0);
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
        final PeriodosCostosConstruccion other = (PeriodosCostosConstruccion) obj;
        if (this.periodo != other.periodo && (this.periodo == null || !this.periodo.equals(other.periodo))) {
            return false;
        }
        return true;
    }

    
}
