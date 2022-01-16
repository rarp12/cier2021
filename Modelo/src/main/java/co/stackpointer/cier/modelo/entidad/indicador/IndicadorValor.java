/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.entidad.indicador;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "ind_valor")
@Multitenant
@TenantDiscriminatorColumn(name = "id_tnt", contextProperty = "TENANT_ID", discriminatorType = DiscriminatorType.INTEGER)
@NamedQueries({
    @NamedQuery(name = "IndicadorValor.findAll", query = "SELECT v FROM IndicadorValor v")})
public class IndicadorValor implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "periodo")
    private Integer periodo;
    
    @Column(name = "valor")
    private String valor;
    
    @Column(name = "cod_valor")
    private String codigoValor;
    
    @Column(name = "id_entidad_nivel")
    private Long idEntidadNivel;
    
    @JoinColumn(name = "id_ind_definicion", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private DefinicionIndicador definicion;

    public IndicadorValor() {
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

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getCodigoValor() {
        return codigoValor;
    }

    public void setCodigoValor(String codigoValor) {
        this.codigoValor = codigoValor;
    }

   
    public Long getIdEntidadNivel() {
        return idEntidadNivel;
    }

    public void setIdEntidadNivel(Long idEntidadNivel) {
        this.idEntidadNivel = idEntidadNivel;
    }

    public DefinicionIndicador getDefinicion() {
        return definicion;
    }

    public void setDefinicion(DefinicionIndicador definicion) {
        this.definicion = definicion;
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final IndicadorValor other = (IndicadorValor) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    
}
