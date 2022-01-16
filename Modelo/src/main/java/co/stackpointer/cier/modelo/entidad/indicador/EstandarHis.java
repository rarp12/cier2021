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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.TenantDiscriminatorColumn;

/**
 *
 * @author StackPointer
 */
@Entity
@Table(name = "ind_estandares")
@Multitenant
@TenantDiscriminatorColumn(name = "id_tnt", contextProperty = "TENANT_ID", discriminatorType = DiscriminatorType.INTEGER)
@NamedQueries({
    @NamedQuery(name = "EstandarHis.findAll", query = "SELECT e FROM EstandarHis e"),
@NamedQuery(name = "EstandarHis.consultarPorCodigo", query = "SELECT e FROM EstandarHis e WHERE e.codigo = :codigo")})
public class EstandarHis implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    
    @Size(max = 50)
    @Column(name = "codigo")
    private String codigo;
    
    @Size(max = 500)
    @Column(name = "descripcion")
    private String descripcion;
    
    @Column(name = "valor")
    private Double valor;
    
    @Size(max = 20)
    @Column(name = "unidades")
    private String unidades;    
    
    @OneToMany(mappedBy = "estandar")
    private List<RangoEstandaresHis> rangos;

    public EstandarHis() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }           

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getEtiquetaUnidades() {
        return "bd.ind_estandares.unidades."+unidades;
    }

    public List<RangoEstandaresHis> getRangos() {
        return rangos;
    }

    public void setRangos(List<RangoEstandaresHis> rangos) {
        this.rangos = rangos;
    }

    public String getEtiqueta() {
        return "bd.ind_estandares.nombre."+codigo;
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
        final EstandarHis other = (EstandarHis) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }   
    

}
