/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.entidad.instrumento;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author StackPointer
 */
@Entity
@Table(name = "ins_tipologia_valores")
@NamedQueries({
    @NamedQuery(name = "TipologiaValor.findAll", query = "SELECT u FROM TipologiaValor u order by u.id"),
    @NamedQuery(name = "TipologiaValor.findByTipologia", query = "SELECT u FROM TipologiaValor u WHERE u.tipologia.codigo = :codigo AND u.tipologia.estado = 'A'")
})
public class TipologiaValor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    @Column(name = "codigo")
    private String codigo;
    @JoinColumn(name = "cod_tipologia", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private Tipologia tipologia;
    @NotNull
    @Column(name = "estado")
    private String estado;

    public TipologiaValor() {
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

    public Tipologia getTipologia() {
        return tipologia;
    }

    public String getNombre() {
        return "";
    }

    public void setTipologia(Tipologia tipologia) {
        this.tipologia = tipologia;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 43 * hash + (this.codigo != null ? this.codigo.hashCode() : 0);
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
        final TipologiaValor other = (TipologiaValor) obj;
        if ((this.codigo == null) ? (other.codigo != null) : !this.codigo.equals(other.codigo)) {
            return false;
        }
        return true;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}