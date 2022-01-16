/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.entidad.calificacion;

import co.stackpointer.cier.modelo.entidad.instrumento.Ambito;
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
@Table(name = "cal_variables_ambito")
@Multitenant
@TenantDiscriminatorColumn(name = "id_tnt", contextProperty = "TENANT_ID", discriminatorType = DiscriminatorType.INTEGER)
@NamedQueries({
@NamedQuery(name = "VariableAmbito.findAll", query = "SELECT v FROM VariableAmbito v order by v.ambito.codigo,v.nombre"),
@NamedQuery(name = "VariableAmbito.findVariablesCalificacion", query = "SELECT v FROM VariableAmbito v where v.ambito.codigo not in ('A_OFE','A_ACC','A_RI','A_SP','A_AMB','A_PROP')  and upper(v.nombre) <> 'VULNERABILIDAD' order by v.ambito.codigo,v.nombre")       
        
})
        
 public class VariableAmbito implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;    
    
    @ManyToOne
    @JoinColumn(name = "cod_ambito", referencedColumnName = "codigo")
    private Ambito ambito;
    
    @Column(name = "nombre")
    private String nombre;

    @Column(name = "ponderacion")
    private Double ponderacion;

    @Column(name = "etiqueta")
    private String etiqueta;
  
    public VariableAmbito() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ambito getAmbito() {
        return ambito;
    }

    public void setAmbito(Ambito ambito) {
        this.ambito = ambito;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPonderacion() {
        return ponderacion;
    }

    public void setPonderacion(Double ponderacion) {
        this.ponderacion = ponderacion;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    @Override
    public int hashCode() {
        int hash = 3;
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
        final VariableAmbito other = (VariableAmbito) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
}
