/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.entidad.acceso;

import co.stackpointer.cier.modelo.tipo.Estado;
import co.stackpointer.cier.modelo.tipo.GrupoModulo;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author StackPointer
 */
@Entity
@Table(name = "aya_modulos")
@NamedQueries({
    @NamedQuery(name = "Modulo.findAll", query = "SELECT m FROM Modulo m"),
    @NamedQuery(name = "Modulo.descripcionByGrupo", query = "SELECT DISTINCT m.etiqueta FROM Modulo m WHERE m.grupo = :grupo AND m.estado = co.stackpointer.cier.modelo.tipo.Estado.A")})
public class Modulo implements Serializable {
    
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
    
    @Column(name = "url")
    private String url;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "grupo")
    private GrupoModulo grupo;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "modulo")
    private List<RolPermiso> rolPermisoList;

    public Modulo() {
    }

    public Modulo(String codigo) {
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
    
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }    

    public GrupoModulo getGrupo() {
        return grupo;
    }

    public void setGrupo(GrupoModulo grupo) {
        this.grupo = grupo;
    }
    
    public List<RolPermiso> getRolPermisoList() {
        return rolPermisoList;
    }

    public void setRolPermisoList(List<RolPermiso> rolPermisoList) {
        this.rolPermisoList = rolPermisoList;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (this.codigo != null ? this.codigo.hashCode() : 0);
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
        final Modulo other = (Modulo) obj;
        if ((this.codigo == null) ? (other.codigo != null) : !this.codigo.equals(other.codigo)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return this.codigo + "";
    }

}
