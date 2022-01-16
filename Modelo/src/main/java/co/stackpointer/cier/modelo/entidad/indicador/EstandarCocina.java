/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.entidad.indicador;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.TenantDiscriminatorColumn;

/**
 *
 * @author StackPointer
 */
@Entity
@Table(name = "ind_estandar_cocina")
@Multitenant
@TenantDiscriminatorColumn(name = "id_tnt", contextProperty = "TENANT_ID", discriminatorType = DiscriminatorType.INTEGER)
@NamedQueries({
    @NamedQuery(name = "EstandarCocina.findAll", query = "SELECT e FROM EstandarCocina e order by e.minAlumnos asc"),
    @NamedQuery(name = "EstandarCocina.porId", query = "SELECT e FROM EstandarCocina e WHERE e.id = :id"),
    @NamedQuery(name = "EstandarCocina.existentes", query = "SELECT e FROM EstandarCocina e"),
    @NamedQuery(name = "EstandarCocina.existentesMenosEditable", query = "SELECT e FROM EstandarCocina e WHERE e.id <> :id")})

public class EstandarCocina implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    @SequenceGenerator(name = "SEC_PK_IND_ESTANDARES", sequenceName = "SEC_PK_IND_ESTANDARES", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEC_PK_IND_ESTANDARES")
    private Long id;
  
    @Column(name = "min_alumnos")
    private Long minAlumnos;
    
    @Column(name = "max_alumnos")
    private Long maxAlumnos;  
    
    @Column(name = "num_servicios")
    private Long numServicios;  
    
    @Column(name = "area_cocina")
    private BigDecimal areaCocina; 
    
    @Column
    private String observacion;

    public EstandarCocina() {
    }

    public Long getId() {
        return id;
    }

    public Long getMinAlumnos() {
        return minAlumnos;
    }

    public void setMinAlumnos(Long minAlumnos) {
        this.minAlumnos = minAlumnos;
    }

    public Long getMaxAlumnos() {
        return maxAlumnos;
    }

    public void setMaxAlumnos(Long maxAlumnos) {
        this.maxAlumnos = maxAlumnos;
    }

    public Long getNumServicios() {
        return numServicios;
    }

    public void setNumServicios(Long numServicios) {
        this.numServicios = numServicios;
    }

    public BigDecimal getAreaCocina() {
        return areaCocina;
    }

    public void setAreaCocina(BigDecimal areaCocina) {
        this.areaCocina = areaCocina;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }


    @Override
    public int hashCode() {
        int hash = 7;
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
        final EstandarCocina other = (EstandarCocina) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }   
    

}
