/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.entidad.instrumento;

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
import javax.validation.constraints.Size;
import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.TenantDiscriminatorColumn;

/**
 *
 * @author StackPointer
 */
@Entity
@Table(name = "ins_tipologias")
@Multitenant
@TenantDiscriminatorColumn(name = "id_tnt", contextProperty = "TENANT_ID", discriminatorType = DiscriminatorType.INTEGER)
@NamedQueries({
    @NamedQuery(name = "Tipologia.findAll", query = "SELECT t FROM Tipologia t"),
    @NamedQuery(name = "Tipologia.findByCodigo", query = "SELECT t FROM Tipologia t where t.codigo = :codigo"),
    @NamedQuery(name = "Tipologia.findByCodigos", query = "SELECT t FROM Tipologia t where t.codigo IN (:codigos)")
})
public class Tipologia implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "codigo")
    private String codigo;
    
    @Size(max = 100)
    @Column(name = "nombre")
    private String nombre;
    
    @Size(max = 200)
    @Column(name = "clase")
    private String clase;
    
    @Size(max = 1)
    @Column(name = "estado")
    private String estado;
    
    @JoinColumn(name = "cod_instrumento", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private Instrumento instrumento;
    
    @OneToMany( mappedBy = "tipologia")
    private List<TipologiaValor> valores;

    public Tipologia() {
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Instrumento getInstrumento() {
        return instrumento;
    }

    public void setInstrumento(Instrumento instrumento) {
        this.instrumento = instrumento;
    }

    public List<TipologiaValor> getValores() {
        return valores;
    }

    public void setValores(List<TipologiaValor> valores) {
        this.valores = valores;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + (this.codigo != null ? this.codigo.hashCode() : 0);
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
        final Tipologia other = (Tipologia) obj;
        if ((this.codigo == null) ? (other.codigo != null) : !this.codigo.equals(other.codigo)) {
            return false;
        }
        return true;
    }

    
}
