/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.entidad.indicador;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.TenantDiscriminatorColumn;

/**
 *
 * @author StackPointer
 */
@Entity
@Table(name = "ins_unidades_funcionales")
@Multitenant
@TenantDiscriminatorColumn(name = "id_tnt", contextProperty = "TENANT_ID", discriminatorType = DiscriminatorType.INTEGER)
@NamedQueries({@NamedQuery(name = "UnidadFuncional.getAll", query = "SELECT u FROM UnidadFuncional u order by u.codigo"),
@NamedQuery(name = "UnidadFuncional.getById", query = "SELECT u FROM UnidadFuncional u where u.id = :id"),
@NamedQuery(name = "UnidadFuncional.porPeriodo", query = "SELECT u FROM UnidadFuncional u WHERE u.periodo= :periodo order by u.codigo")})
public class UnidadFuncional implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    
    @Column(name = "periodo")
    private Integer periodo;
    
    @Column(name = "codigo")
    private String codigo;
    
    @Column(name = "espacio")
    private String espacio;
    
    @Column(name = "descripcion")
    private String descripcion;
    
    @OneToMany(mappedBy = "unidadFuncional")
    private List<UnidadFuncionalValor> valores;
    
    @Transient
    private boolean checked;
    
    @Transient
    private Integer cantidad;
    
    @Transient
    private String color;
        

    public UnidadFuncional() {
       this.checked = Boolean.FALSE; 
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getEspacio() {
        return espacio;
    }

    public void setEspacio(String espacio) {
        this.espacio = espacio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<UnidadFuncionalValor> getValores() {
        return valores;
    }

    public void setValores(List<UnidadFuncionalValor> valores) {
        this.valores = valores;
    }   
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final UnidadFuncional other = (UnidadFuncional) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
