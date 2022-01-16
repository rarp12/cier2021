/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.entidad.BID;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "bid_tipos_indicadores")
@NamedQueries({
    @NamedQuery(name = "TiposIndicadoresBID.obtenerPorCodigo", query = "SELECT t FROM TiposIndicadoresBID t where t.codigo=:codigo"),
    @NamedQuery(name = "TiposIndicadoresBID.obtenerTodos", query = "SELECT t FROM TiposIndicadoresBID t")
}) 
public class TiposIndicadoresBID implements Serializable 
{    
    @Id
    @Column(name = "id")
    Long id;
    
    @Column(name = "codigo")
    String codigo;

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
    
}
