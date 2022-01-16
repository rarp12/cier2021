/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.entidad.BID;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "bid_ambitos")
@NamedQueries({
    @NamedQuery(name = "AmbitosBID.findAll", query = "SELECT a FROM AmbitosBID a ORDER BY a.codigo asc")
}) 
public class AmbitosBID implements Serializable 
{    
    @Id
    @Column(name = "codigo")
    String codigo;

    @Column(name = "ponderacion") 
    Double ponderacion;

    @OneToMany(mappedBy = "ambitoBID")
    private List<VariablesBID> variablesBID;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Double getPonderacion() {
        return ponderacion;
    }

    public void setPonderacion(Double ponderacion) {
        this.ponderacion = ponderacion;
    }

    public List<VariablesBID> getVariablesBID() {
        return variablesBID;
    }

    public void setVariablesBID(List<VariablesBID> variablesBID) {
        this.variablesBID = variablesBID;
    }
   
   
}
