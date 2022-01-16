/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.entidad.calificacion;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.TenantDiscriminatorColumn;

@Entity
@Table(name = "cal_servicios_publicos")
@Multitenant
@TenantDiscriminatorColumn(name = "id_tnt", contextProperty = "TENANT_ID", discriminatorType = DiscriminatorType.INTEGER)
@NamedQuery(name = "CalServicios.findAll", query = "SELECT c FROM CalServicios c order by c.id ")
      
public class CalServicios implements Serializable 
{    
   @Id
   @Column(name = "id")
   int id;
   
   @Column(name = "servicio")
   String  servicio ;
   
   @Column(name = "tipo") 
   String tipo;
   
   @Column(name = "nombre") 
   String nombre;
   
   @Transient
   String descTipo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEtiqueta() {
        return "enum.serviciosPublicos."+servicio;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescTipo() {
        if(tipo!= null)
        {
        if(tipo.equals("O"))
        {
            descTipo="Secundario";
        }else{
            if(tipo.equals("P"))
            {
                descTipo="Principal";
            }                 
        }
        }else{
                descTipo="";
            }
                
        return descTipo;
    }

    public void setDescTipo(String descTipo) {
        this.descTipo = descTipo;
    }
    
    
  
    
}
