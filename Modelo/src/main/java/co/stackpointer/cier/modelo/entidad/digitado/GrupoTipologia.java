/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.entidad.digitado;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author rjay1
 */
@Entity
@Table(name = "dig_grupos_tipologias")
@NamedQueries({
    @NamedQuery(name = "GrupoTipologia.findAll", query = "SELECT i FROM GrupoTipologia i"),
    @NamedQuery(name = "GrupoTipologia.findByTipologia", query = "SELECT i FROM GrupoTipologia i WHERE i.codTipologia = :codigo AND i.idioma = :idioma")
})
public class GrupoTipologia implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "cod_tipologia")
    private String codTipologia;
    @Column(name = "codigo")
    private String codigo;
    @Column(name = "id_idioma")
    private Integer idioma;
    @Column(name = "nombre")
    private String nombre;

    public GrupoTipologia() {
    }

    public GrupoTipologia(Long id, String codTipologia, String codigo, Integer idioma, String nombre) {
        this.id = id;
        this.codTipologia = codTipologia;
        this.codigo = codigo;
        this.idioma = idioma;
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodTipologia() {
        return codTipologia;
    }

    public void setCodTipologia(String codTipologia) {
        this.codTipologia = codTipologia;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Integer getIdioma() {
        return idioma;
    }

    public void setIdioma(Integer idioma) {
        this.idioma = idioma;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}