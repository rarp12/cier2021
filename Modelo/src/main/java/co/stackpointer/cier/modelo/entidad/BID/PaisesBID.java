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
@Table(name = "bid_paises")
@NamedQueries({
    @NamedQuery(name = "PaisesBID.buscarPorId", query = "SELECT p FROM PaisesBID p WHERE p.id = :idPais")
    })
public class PaisesBID implements Serializable 
{    
    @Id
    @Column(name = "id")
    int id;

    @Column(name = "codigo")
    String  codigo ;

    @Column(name = "nombre")
    String  nombre ;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

   
}
