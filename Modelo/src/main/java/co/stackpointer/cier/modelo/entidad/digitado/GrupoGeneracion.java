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
@Table(name = "dig_grupos_generar")
@NamedQueries({
    @NamedQuery(name = "GrupoGeneracion.findAll", query = "SELECT i FROM GrupoGeneracion i")
})
public class GrupoGeneracion implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "id_dig_grupo")
    private Integer grupoId;

    public GrupoGeneracion() {
    }

    public GrupoGeneracion(Integer id, Integer grupoId) {
        this.id = id;
        this.grupoId = grupoId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(Integer grupoId) {
        this.grupoId = grupoId;
    }
}