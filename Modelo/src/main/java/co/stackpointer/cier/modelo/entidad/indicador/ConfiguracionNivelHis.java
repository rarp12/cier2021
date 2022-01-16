/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.entidad.indicador;

import co.stackpointer.cier.modelo.interfaceDPA.ConfNivelDPA;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.TenantDiscriminatorColumn;

/**
 *
 * @author StackPointer
 */
@Entity
@Table(name = "ind_dpa_configuracion_niveles")
@Multitenant
@TenantDiscriminatorColumn(name = "id_tnt", contextProperty = "TENANT_ID", discriminatorType = DiscriminatorType.INTEGER)
@NamedQueries({
    @NamedQuery(name = "ConfiguracionNivelHis.findAll", query = "SELECT d FROM ConfiguracionNivelHis d"),
    @NamedQuery(name = "ConfiguracionNivelHis.consultarPorPeriodo", query = "SELECT d FROM ConfiguracionNivelHis d WHERE d.pk.periodo = :periodo ORDER BY d.nivel asc"),
    @NamedQuery(name = "ConfiguracionNivelHis.consultarPorPeriodoNivelEspecifico", query = "SELECT d FROM ConfiguracionNivelHis d WHERE d.pk.periodo = :periodo AND d.nivel >= :nivel ORDER BY d.nivel asc")
})
public class ConfiguracionNivelHis implements ConfNivelDPA,Serializable {
    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    private ConfiguracionNivelHisPK pk;   
    
    @Column(name = "descripcion")
    private String descripcion;
    
    @Column(name = "estado")
    private String estado;    

    @Column(name = "nivel")
    private Long nivel;
    
    public ConfiguracionNivelHis() {
    }

    public ConfiguracionNivelHisPK getPk() {
        return pk;
    }

    public void setPk(ConfiguracionNivelHisPK pk) {
        this.pk = pk;
    }        
    
    public Long getId() {
        return pk != null ? pk.getId() : null;
    }

    @Override
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public Long getNivel() {
        return nivel;
    }

    public void setNivel(Long nivel) {
        this.nivel = nivel;
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + (this.pk != null ? this.pk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ConfiguracionNivelHis other = (ConfiguracionNivelHis) obj;
        if (this.pk != other.pk && (this.pk == null || !this.pk.equals(other.pk))) {
            return false;
        }
        return true;
    }


    
}
