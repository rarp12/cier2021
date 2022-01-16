/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.entidad.calificacion;

import co.stackpointer.cier.modelo.tipo.PrincipalOtro;
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

/**
 *
 * @author user
 */
@Entity
@Table(name = "cal_ambientes")
@Multitenant
@TenantDiscriminatorColumn(name = "id_tnt", contextProperty = "TENANT_ID", discriminatorType = DiscriminatorType.INTEGER)
@NamedQuery(name = "AmbientesFuncionales.findAll", query = "SELECT c FROM AmbientesFuncionales c order by c.ambiente ")
   
public class AmbientesFuncionales implements Serializable  {
    
   @Id
    @Column(name = "ambiente")
    String ambiente;
    
    @Column(name = "tipo")   
    String tipo;
    
    @Column(name = "nombre") 
    String nombre;
    
    @Column(name = "etiqueta") 
    String etiqueta;
    
     @Transient
    String descTipo;

    public String getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(String ambiente) {
        this.ambiente = ambiente;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescTipo() {
        if(tipo!= null)
        {
        if(tipo.equals("O"))
        {
            descTipo=PrincipalOtro.O.name();
        }else{
            if(tipo.equals("P"))
            {
                descTipo=PrincipalOtro.P.name();
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

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }
    
    
     
}
