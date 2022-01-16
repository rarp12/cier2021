/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.entidad.instrumento;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.TenantDiscriminatorColumn;

/**
 *
 * @author StackPointer
 */
@Entity
@Table(name = "ins_modulos")
@Multitenant
@TenantDiscriminatorColumn(name = "id_tnt", contextProperty = "TENANT_ID", discriminatorType = DiscriminatorType.INTEGER)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ModuloIns.findAll", query = "SELECT m FROM ModuloIns m order by m.codigo")
    ,@NamedQuery(name = "ModuloIns.findAllActivos", query = "SELECT m FROM ModuloIns m where m.estado = 'A' order by m.orden ASC")
    ,@NamedQuery(name = "ModuloIns.findModulosByInstrumento", query = "Select distinct(p.modulo) from Pregunta p where p.instrumento = :instrumento and p.modulo.estado = 'A' order by p.modulo.orden ASC")
    ,@NamedQuery(name = "ModuloIns.findByCodigo", query = "SELECT m FROM ModuloIns m where m.codigo = :codigo and m.estado = 'A'")
   })
public class ModuloIns implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "codigo")
    private String codigo;
    
    @Column(name = "estado")
    private String estado;
    
    @Column(name = "orden")
    private int orden;

    @Column(name = "etiqueta")
    private String etiqueta;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "modulo")
    @OrderColumn(name = "orden")
    private List<Seccion> seccionList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "modulo")
    @OrderColumn(name = "codigo")
    private List<Pregunta> preguntaList;
    
 

    public ModuloIns() {
    }

    public ModuloIns(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @XmlTransient
    public List<Seccion> getSeccionList() {
        return seccionList;
    }

    public void setSeccionList(List<Seccion> seccionList) {
        this.seccionList = seccionList;
    }

    @XmlTransient
    public List<Pregunta> getPreguntaList() {
        return preguntaList;
    }

    public void setPreguntaList(List<Pregunta> preguntaList) {
        this.preguntaList = preguntaList;
    }
    
    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public String getEtiqueta() {
        return etiqueta;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + (this.codigo != null ? this.codigo.hashCode() : 0);
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
        final ModuloIns other = (ModuloIns) obj;
        if ((this.codigo == null) ? (other.codigo != null) : !this.codigo.equals(other.codigo)) {
            return false;
        }
        return true;
    }
}
