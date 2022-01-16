/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.entidad.digitado;

import co.stackpointer.cier.modelo.utilidad.UtilFecha;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.TenantDiscriminatorColumn;

/**
 *
 * @author StackPointer
 */
@Entity
@Table(name = "dig_adjuntos")
@Multitenant
@TenantDiscriminatorColumn(name = "id_tnt", contextProperty = "TENANT_ID", discriminatorType = DiscriminatorType.INTEGER)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Adjunto.findAll", query = "SELECT a FROM Adjunto a"),
    @NamedQuery(name = "Adjunto.findByIdRespuestaDigitada", query = "SELECT a FROM Adjunto a where a.respuestaDigitada.id = :idRespuestaDigitada"),
    @NamedQuery(name = "Adjunto.findByIdInstrumentoCodigoRespuesta", query = "SELECT a FROM Adjunto a where a.respuestaDigitada.elemento.instrumentoDigitado.id = :idInstrumento and  a.respuestaDigitada.pregunta.codigo = :codPregunta" )
})

public class Adjunto implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    @SequenceGenerator(name = "SEC_ID_ADJ", sequenceName = "SEC_PK_ELE_DIG", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEC_ID_ADJ")
    private Long id;
    
    @Size(max = 4000)
    @Column(name = "ruta")
    private String ruta;
    
    @Size(max = 20)
    @Column(name = "usuario_creacion")
    private String usuarioCreacion;
    
    @Size(max = 20)
    @Column(name = "tipo")
    private String tipo;
    
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;
    

    @Column(name = "content_type")
    private String contentType;
    
    
    @Column(name = "nombre")
    private String nombre;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_respuesta_dig", referencedColumnName = "id")
    private RespuestaDig respuestaDigitada;
    
    public Adjunto() {
        this.fechaCreacion = UtilFecha.obtenerFechaActual();
    }

    public Adjunto(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Adjunto)) {
            return false;
        }
        Adjunto other = (Adjunto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.stackpointer.cier.modelo.entidades.dig.Adjuntos[ id=" + id + " ]";
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public RespuestaDig getRespuestaDigitada() {
        return respuestaDigitada;
    }

    public void setRespuestaDigitada(RespuestaDig respuestaDigitada) {
        this.respuestaDigitada = respuestaDigitada;
    }
    
    public Object clone() {
        Adjunto adjunto = new Adjunto();
        adjunto.setNombre(this.getNombre());
        adjunto.setRuta(this.getRuta());
        adjunto.setTipo(this.getTipo());
        adjunto.setUsuarioCreacion(this.getUsuarioCreacion());
        return adjunto;
    }    
    
}
