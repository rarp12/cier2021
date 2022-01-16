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
@Table(name = "ins_tipologia_valores_nombres")
@NamedQueries({
    @NamedQuery(name = "TipologiaValorNombre.findAll", query = "SELECT t FROM TipologiaValorNombre t"),
    @NamedQuery(name = "TipologiaValorNombre.consultarPorCodTipologia", query = "SELECT t FROM TipologiaValorNombre t WHERE t.tipValor.estado = :estado and t.tipValor.tipologia.codigo = :codTipologia and t.idioma = :idIdioma"),
    @NamedQuery(name = "TipologiaValorNombre.consultarValorPorCod", query = "SELECT t FROM TipologiaValorNombre t WHERE t.tipValor.tipologia.codigo = :codTipologia and t.tipValor.codigo = :codigo and t.idioma = :idIdioma ORDER BY t.nombre"),
    @NamedQuery(name = "TipologiaValorNombre.consultarValorPorCodEstado", query = "SELECT t FROM TipologiaValorNombre t WHERE t.tipValor.tipologia.codigo = :codTipologia and t.tipValor.codigo = :codigo and t.idioma = :idIdioma and t.tipValor.estado = 'A' "),
    @NamedQuery(name = "TipologiaValorNombre.consultarValorPorId", query = "SELECT t FROM TipologiaValorNombre t WHERE t.id_tip_valor = :idTipologia and t.idioma = :idIdioma")
})
public class TipologiaValorNombre implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_tip_valor")
    private Long id_tip_valor;
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_idioma")
    private Long idioma;
    
    @Column(name = "nombre")
    private String nombre;

    @JoinColumn(name = "id_tip_valor", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne
    private TipologiaValor tipValor;

    public Long getId_tip_valor() {
        return id_tip_valor;
    }

    public void setId_tip_valor(Long id_tip_valor) {
        this.id_tip_valor = id_tip_valor;
    }

    public Long getIdioma() {
        return idioma;
    }

    public void setIdioma(Long idioma) {
        this.idioma = idioma;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipologiaValor getTipValor() {
        return tipValor;
    }

    public void setTipValor(TipologiaValor tipValor) {
        this.tipValor = tipValor;
    }
}