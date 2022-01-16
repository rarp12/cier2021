/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.entidad.BID;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "bid_variables_ambito")
@NamedQueries({
    @NamedQuery(name = "VariablesBID.findEditables", query = "SELECT v FROM VariablesBID v where v.ambitoBID.codigo not in ('A_OFE','A_ACC','A_RI','A_SP','A_AMB','A_PROP')  and upper(v.nombre) <> 'VULNERABILIDAD' ORDER BY v.ambitoBID.codigo asc"),    
    @NamedQuery(name = "VariablesBID.obtenerPorId", query = "SELECT v FROM VariablesBID v where v.id=:id")
}) 
public class VariablesBID implements Serializable 
{    
    @Id
    @Column(name = "id")
    int id;
    
    @JoinColumn(name = "cod_ambito", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private AmbitosBID ambitoBID;

    @Column(name = "nombre")
    String  nombre ;
   
    @Column(name = "ponderacion") 
    Double ponderacion;
    
    @Column
    String  etiqueta ;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AmbitosBID getAmbitoBID() {
        return ambitoBID;
    }

    public void setAmbitoBID(AmbitosBID ambitoBID) {
        this.ambitoBID = ambitoBID;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPonderacion() {
        return ponderacion;
    }

    public void setPonderacion(Double ponderacion) {
        this.ponderacion = ponderacion;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }
   
   
}
