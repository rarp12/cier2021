/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.entidad.instrumento;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.TenantDiscriminatorColumn;

/**
 *
 * @author StackPointer
 */
@Entity
@Table(name = "ins_respuestas")
@Multitenant
@TenantDiscriminatorColumn(name = "id_tnt", contextProperty = "TENANT_ID", discriminatorType = DiscriminatorType.INTEGER)
@NamedQueries({
    @NamedQuery(name = "Respuesta.findAll", query = "SELECT r FROM Respuesta r"),
    @NamedQuery(name = "Respuesta.findByCodigo", query = "SELECT r FROM Respuesta r where r.codigo = :codigo"),
    @NamedQuery(name = "Respuesta.findPrincipales", query = "SELECT r FROM Respuesta r where r.codPreguntas.instrumento.codigo = :codigoInstrumento and r.codigo = r.respuestaValidar.codigo order by r.codigo"),
    @NamedQuery(name = "Respuesta.findDependientes", query = "SELECT r FROM Respuesta r where r.respuestaValidar.codigo = :codigoRespuesta and r.codigo != r.respuestaValidar.codigo and r.tipoRespuesta.codigo <> 'TIP_RESP_014'"),
    @NamedQuery(name = "Respuesta.findPrincipalesTipo", query="SELECT r FROM Respuesta r where r.codPreguntas.tipoElemento = :tipoElemento and r.codPreguntas.instrumento.codigo = :codigoInstrumento and r.codigo = r.respuestaValidar.codigo  order by r.codigo"),
    @NamedQuery(name = "Respuesta.findByFila", query="SELECT r FROM Respuesta r where r.fila = :fila and r.codigo > :codigoRespuesta  order by r.codigo"),
    @NamedQuery(name = "Respuesta.consultaDependientePorTipoElemento", query="SELECT r FROM Respuesta r WHERE r.codigo <> r.respuestaValidar.codigo and r.codPreguntas.tipoElemento.codigo = :tipoElemento"),
    @NamedQuery(name = "Respuesta.findPrincipalesPredioEstablecimiento", query = "SELECT r FROM Respuesta r where r.codPreguntas.instrumento.codigo = :codigoInstrumento and r.codigo = r.respuestaValidar.codigo and (r.codPreguntas.tipoElemento = :tipoPredio or r.codPreguntas.tipoElemento = :tipoEstablecimiento ) order by r.codigo"),
    @NamedQuery(name = "Respuesta.consultaPorTipoElementoTipoRespuesta", query = "SELECT r FROM Respuesta r where r.tipoRespuesta.codigo ='TIP_RESP_014' and r.codPreguntas.tipoElemento.codigo = :codigoTipoElemento "),
    @NamedQuery(name = "Respuesta.obtenerByRepetida", query = "SELECT r FROM Respuesta r WHERE r.repetida = :repetida"),
    @NamedQuery(name = "Respuesta.findRespuestasObligatorias", query ="SELECT r FROM Respuesta r where r.respuestaObligatoria is not null"),
    @NamedQuery(name = "Respuesta.findByPreguntas", query="SELECT r FROM Respuesta r where r.codPreguntas.codigo in :preguntas")
})
public class Respuesta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "codigo")
    private String codigo;
    
    @JoinColumn(name = "cod_tipologia", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private Tipologia tipologia;
    
    @Column(name = "cod_parametro")
    private String codParametro;
    
    @JoinColumn(name = "cod_tipo_respuesta", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private TipoRespuesta tipoRespuesta;
    
    @OneToMany(mappedBy = "codResPadre")
    private List<Respuesta> respuestaList;
    
    @JoinColumn(name = "cod_res_padre", referencedColumnName = "codigo")
    @ManyToOne
    private Respuesta codResPadre;
    
    @JoinColumn(name = "respuesta_obligatoria", referencedColumnName = "codigo")
    @ManyToOne
    private Respuesta respuestaObligatoria;
    
    @JoinColumn(name = "cod_pregunta", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private Pregunta codPreguntas;
    
    @JoinColumn(name = "respuesta_validacion", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private Respuesta respuestaValidar;
     
    @Column(name = "fila")
    private Integer fila;
    
    @Column
    private int requerida;
    
    @Column
    private int repetida;

    public int getRequerida() {
        return requerida;
    }

    public void setRequerida(int requerida) {
        this.requerida = requerida;
    }

    public Respuesta() {
    }

    public Respuesta(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodParametro() {
        return codParametro;
    }

    public void setCodParametro(String codParametro) {
        this.codParametro = codParametro;
    }

    public TipoRespuesta getTipoRespuesta() {
        return tipoRespuesta;
    }

    public void setTipoRespuesta(TipoRespuesta tipoRespuesta) {
        this.tipoRespuesta = tipoRespuesta;
    }

    @XmlTransient
    public List<Respuesta> getRespuestaList() {
        return respuestaList;
    }

    public void setRespuestaList(List<Respuesta> respuestaList) {
        this.respuestaList = respuestaList;
    }

    public Respuesta getCodResPadre() {
        return codResPadre;
    }

    public void setCodResPadre(Respuesta codResPadre) {
        this.codResPadre = codResPadre;
    }

    public Respuesta getRespuestaObligatoria() {
        return respuestaObligatoria;
    }

    public void setRespuestaObligatoria(Respuesta respuestaObligatoria) {
        this.respuestaObligatoria = respuestaObligatoria;
    }

    public Pregunta getCodPreguntas() {
        return codPreguntas;
    }

    public void setCodPreguntas(Pregunta codPreguntas) {
        this.codPreguntas = codPreguntas;
    }

    public Integer getFila() {
        return fila;
    }

    public void setFila(Integer fila) {
        this.fila = fila;
    }      

    public Tipologia getTipologia() {
        return tipologia;
    }

    public void setTipologia(Tipologia tipologia) {
        this.tipologia = tipologia;
    }

    public Respuesta getRespuestaValidar() {
        return respuestaValidar;
    }

    public void setRespuestaValidar(Respuesta respuestaValidar) {
        this.respuestaValidar = respuestaValidar;
    }

    public int getRepetida() {
        return repetida;
    }

    public void setRepetida(int repetida) {
        this.repetida = repetida;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Respuesta)) {
            return false;
        }
        Respuesta other = (Respuesta) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }
    
}
