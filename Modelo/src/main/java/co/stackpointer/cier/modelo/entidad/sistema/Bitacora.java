/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.entidad.sistema;

import co.stackpointer.cier.modelo.tipo.BitacoraModulo;
import co.stackpointer.cier.modelo.tipo.BitacoraOpcion;
import co.stackpointer.cier.modelo.tipo.BitacoraOperacion;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author DevTeam
 */
@Entity
@Table(name = "sys_bitacora")
@NamedQueries({
    @NamedQuery(name = "Bitacora.findAll", query = "SELECT i FROM Bitacora i"),
    @NamedQuery(name = "Bitacora.deleteAll", query = "DELETE FROM Bitacora i")
})
public class Bitacora implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    @SequenceGenerator(name = "SEC_PK_SYS_BITACORA", sequenceName = "SEC_PK_SYS_BITACORA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_PK_SYS_BITACORA")
    private Long id;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    
    private String usuario;
    
    @Enumerated(EnumType.STRING)
    private BitacoraModulo modulo;
   
    @Column(name = "boleta_censal")
    private String boletaCensal;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_accion")
    private BitacoraOperacion tipoAccion;
    
    @Enumerated(EnumType.STRING)
    private BitacoraOpcion opcion;
    
    @Column(name = "modulo_digitacion")
    private String moduloDigitacion;

    public Bitacora() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public BitacoraModulo getModulo() {
        return modulo;
    }

    public void setModulo(BitacoraModulo modulo) {
        this.modulo = modulo;
    }

    public String getBoletaCensal() {
        return boletaCensal;
    }

    public void setBoletaCensal(String boletaCensal) {
        this.boletaCensal = boletaCensal;
    }

    public BitacoraOperacion getTipoAccion() {
        return tipoAccion;
    }

    public void setTipoAccion(BitacoraOperacion tipoAccion) {
        this.tipoAccion = tipoAccion;
    }

    public BitacoraOpcion getOpcion() {
        return opcion;
    }

    public void setOpcion(BitacoraOpcion opcion) {
        this.opcion = opcion;
    }

    public String getModuloDigitacion() {
        return moduloDigitacion;
    }

    public void setModuloDigitacion(String moduloDigitacion) {
        this.moduloDigitacion = moduloDigitacion;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final Bitacora other = (Bitacora) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
}
