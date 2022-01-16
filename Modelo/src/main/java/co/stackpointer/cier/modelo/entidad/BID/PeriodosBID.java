/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.entidad.BID;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "bid_periodos")
@NamedQueries({
    @NamedQuery(name = "PeriodosBID.findAll", query = "SELECT p FROM PeriodosBID p ORDER BY p.id asc"),
    @NamedQuery(name = "PeriodosBID.delete", query = "DELETE FROM PeriodosBID p where p.id=:id"),
    @NamedQuery(name = "PeriodosBID.buscarPorCodigo", query = "SELECT p  FROM PeriodosBID p where p.codigo =:codigo"),
    @NamedQuery(name = "PeriodosBID.buscarPorId", query = "SELECT p FROM PeriodosBID p where p.id=:id")
})      
public class PeriodosBID implements Serializable 
{    
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    @SequenceGenerator(name = "SEC_PK_BID_PERIODOS", sequenceName = "SEC_PK_BID_PERIODOS", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEC_PK_BID_PERIODOS")
    private Long id;

    @Column(name = "codigo")
    String codigo;

    @Column(name = "descripcion")
    String  descripcion ;

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

    
}
