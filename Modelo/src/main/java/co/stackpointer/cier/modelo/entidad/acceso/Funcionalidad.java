/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.entidad.acceso;

import co.stackpointer.cier.modelo.tipo.Estado;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author StackPointer
 */
@Entity
@Table(name = "aya_funcionalidades")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Funcionalidad.findAll", query = "SELECT f FROM Funcionalidad f"),
    @NamedQuery(name = "Funcionalidad.porEstado", query = "SELECT f FROM Funcionalidad f WHERE f.estado = :estado ORDER BY f.modulo.codigo"),
    @NamedQuery(name = "Funcionalidad.porGrupoModulo", query = "SELECT f FROM Funcionalidad f WHERE f.modulo.grupo = :grupo AND f.estado = co.stackpointer.cier.modelo.tipo.Estado.A ORDER BY f.modulo.codigo"),
    @NamedQuery(name = "Funcionalidad.porDescripcion", query = "SELECT f FROM Funcionalidad f WHERE f.modulo.etiqueta = :etiqueta AND f.estado = co.stackpointer.cier.modelo.tipo.Estado.A ORDER BY f.modulo.codigo")})
public class Funcionalidad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "codigo")
    private String codigo;
    
    @Column(name = "etiqueta")
    private String etiqueta;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private Estado estado;
    
    @JoinColumn(name = "cod_modulo", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private Modulo modulo;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "funcionalidad")
    private List<RolPermiso> rolPermisoList;

    public Funcionalidad() {
    }

    public Funcionalidad(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Modulo getModulo() {
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }

    @XmlTransient
    public List<RolPermiso> getRolPermisoList() {
        return rolPermisoList;
    }

    public void setRolPermisoList(List<RolPermiso> rolPermisoList) {
        this.rolPermisoList = rolPermisoList;
    }

    @Override
    public String toString() {
        return this.codigo + "";
    }
}
