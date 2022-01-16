/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.entidad.digitado;

import co.stackpointer.cier.modelo.entidad.instrumento.Pregunta;
import co.stackpointer.cier.modelo.entidad.instrumento.Respuesta;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 *
 * @author StackPointer
 */
@Entity
@Table(name = "dig_respuestas")
@NamedQueries({
    @NamedQuery(name = "RespuestDig.findAll", query = "SELECT r FROM RespuestaDig r")
    ,@NamedQuery(name = "RespuestDig.findByElemPregResp", query = "SELECT r FROM RespuestaDig r where r.elemento = :elemento and r.pregunta = :pregunta and r.respuesta =:respuesta")
    ,@NamedQuery(name = "RespuestasDigitadas.findByElementoId", query = "SELECT r FROM RespuestaDig r where r.elemento.id = :idElemento")
    ,@NamedQuery(name = "RespuestasDigitadas.findByElementoInstrumentoDescripcion", query = "SELECT r FROM RespuestaDig r where r.elemento.instrumentoDigitado = :instrumento and r.elemento.descripcion = :elementoDescripcion")
    ,@NamedQuery(name = "RespuestaDig.findRespDigIdInstCodResp", query = "Select r from RespuestaDig r where r.respuesta.codigo = :codigoRespuesta and r.elemento.instrumentoDigitado.id = :idInstrumento")
    ,@NamedQuery(name = "RespuestaDig.findByTipoElementoCodPregunta", query="Select r from RespuestaDig r where r.elemento.tipoElemento = :tipoElemento and r.pregunta.codigo = :codigoPregunta ")
    ,@NamedQuery(name = "RespuestaDig.findByTipoElementoCodPreguntaIdInstrumento", query="Select r from RespuestaDig r where r.elemento.tipoElemento = :tipoElemento and r.pregunta.codigo = :codigoPregunta and r.elemento.instrumentoDigitado.id = :idInstrumento ")
    ,@NamedQuery(name = "RespuestaDig.findByModuloInstrumento", query="Select r from RespuestaDig r where r.elemento.instrumentoDigitado = :instrumentoDigitado and r.pregunta.modulo.codigo = :codigoModulo ")
    ,@NamedQuery(name = "RespuestDig.findById", query = "SELECT r FROM RespuestaDig r WHERE r.id = :id")
    ,@NamedQuery(name = "RespuestDig.findValorByIdInstrumentoRespuesta", query ="Select r.valor from RespuestaDig r where r.elemento.instrumentoDigitado.id = :instrumentoDigitado and r.respuesta.codigo = :codigoRespuesta")
    ,@NamedQuery(name = "RespuestaDig.findByElementoCodpreguntaCodrespuesta", query = "SELECT r FROM RespuestaDig r where r.elemento = :elemento and r.pregunta.codigo = :codigoPregunta and r.respuesta.codigo = :codigoRespuesta")
    ,@NamedQuery(name = "RespuestaDig.findByInstrumentoValor", query = "select r from RespuestaDig r where r.elemento.instrumentoDigitado.id = :instrumentoDigitado and r.respuesta.codigo = :codRespuesta and r.valor = :valor")
})
public class RespuestaDig implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    @SequenceGenerator(name = "SEC_ID_RES_DIG", sequenceName = "SEC_PK_RES_DIG", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_ID_RES_DIG")
    private Long id;
    
    @JoinColumn(name = "id_dig_elemento", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Elemento elemento;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "cod_pregunta", referencedColumnName = "codigo")
    private Pregunta pregunta;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "cod_respuesta", referencedColumnName = "codigo")
    private Respuesta respuesta;
    
    @Column(name = "valor")
    private String valor;
    
    @Column(name = "fecha_digitacion")
    @Temporal(TemporalType.DATE)
    private Date fechaDigitacion;
    
    @OneToMany(mappedBy = "respuestaDigitada")
    private List<Adjunto> adjuntosList;
    
    @Transient
    private Elemento elementoInicial;
    
    @Transient
    private Pregunta preguntaInicial;
    
    @Transient
    private Respuesta respuestaInicial;
    
    @Transient
    private String valorInicial;
    
    @Transient
    private Long idInstrumentoDigitado;

    @PostConstruct
    private void cargarDatosIniciales() {
        this.elementoInicial = this.elemento;
        this.preguntaInicial = this.pregunta;
        this.respuestaInicial= this.respuesta;
        this.valorInicial = this.valor;
    }
    
    @PrePersist
    public void asignarFecha(){
        this.setFechaDigitacion(new Date(System.currentTimeMillis()));
    }

    public RespuestaDig() {
   
    }
    
    public RespuestaDig(Elemento elemento, Pregunta pregunta, Respuesta respuesta, String valor) {
        this.elemento = elemento;
        this.pregunta = pregunta;
        this.respuesta = respuesta;
        this.valor = valor;
    }

    public RespuestaDig(Elemento elemento, Pregunta pregunta, Respuesta respuesta) {
        this.elemento = elemento;
        this.pregunta = pregunta;
        this.respuesta = respuesta;
    }
    
    public RespuestaDig(Pregunta pregunta, Respuesta respuesta, String valor) {
        this.pregunta = pregunta;
        this.respuesta = respuesta;
        this.valor = valor;
    }
    
    public RespuestaDig(Elemento elemento) {
        this.elemento = elemento;
    }

    public RespuestaDig(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pregunta getPregunta() {
        return pregunta;
    }

    public void setPregunta(Pregunta pregunta) {
        this.pregunta = pregunta;
    }

    public co.stackpointer.cier.modelo.entidad.instrumento.Respuesta getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(co.stackpointer.cier.modelo.entidad.instrumento.Respuesta respuesta) {
        this.respuesta = respuesta;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Date getFechaDigitacion() {
        return fechaDigitacion;
    }

    public void setFechaDigitacion(Date fechaDigitacion) {
        this.fechaDigitacion = fechaDigitacion;
    }

    public List<Adjunto> getAdjuntosList() {
        return adjuntosList;
    }

    public void setAdjuntosList(List<Adjunto> adjuntosList) {
        this.adjuntosList = adjuntosList;
    }

    public Elemento getElemento() {
        return elemento;
    }

    public void setElemento(Elemento elemento) {
        this.elemento = elemento;
    }

    public Elemento getElementoInicial() {
        return elementoInicial;
    }

    public void setElementoInicial(Elemento elementoInicial) {
        this.elementoInicial = elementoInicial;
    }

    public Pregunta getPreguntaInicial() {
        return preguntaInicial;
    }

    public void setPreguntaInicial(Pregunta preguntaInicial) {
        this.preguntaInicial = preguntaInicial;
    }

    public Respuesta getRespuestaInicial() {
        return respuestaInicial;
    }

    public void setRespuestaInicial(Respuesta respuestaInicial) {
        this.respuestaInicial = respuestaInicial;
    }

    public String getValorInicial() {
        return valorInicial;
    }

    public void setValorInicial(String valorInicial) {
        this.valorInicial = valorInicial;
    }
    
    public boolean isModificado(){
        return  !this.pregunta.equals(this.preguntaInicial) ||
                !this.respuesta.equals(this.respuestaInicial) ||
                !this.elemento.equals(this.elementoInicial) ||
                !this.valor.equals(this.valorInicial);
    }
    
    public boolean isValorModificado(){
         return !this.valor.equals(this.valorInicial);
    }

    /*@Override
    public int hashCode() {
    int hash = 5;
    hash = 61 * hash + (this.elemento != null ? this.elemento.hashCode() : 0);
    hash = 61 * hash + (this.pregunta != null ? this.pregunta.hashCode() : 0);
    hash = 61 * hash + (this.respuesta != null ? this.respuesta.hashCode() : 0);
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
    final RespuestaDig other = (RespuestaDig) obj;
    if (this.elemento != other.elemento && (this.elemento == null || !this.elemento.equals(other.elemento))) {
    return false;
    }
    if (this.pregunta != other.pregunta && (this.pregunta == null || !this.pregunta.equals(other.pregunta))) {
    return false;
    }
    if (this.respuesta != other.respuesta && (this.respuesta == null || !this.respuesta.equals(other.respuesta))) {
    return false;
    }
    return true;
    }*/
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final RespuestaDig other = (RespuestaDig) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    
     public Object clone() {
         RespuestaDig respuesta = new RespuestaDig();
         respuesta.setPregunta(this.getPregunta());
         respuesta.setRespuesta(this.getRespuesta());
         respuesta.setValor(this.getValor());
         return respuesta;
     }

    public Long getIdInstrumentoDigitado() {
        return idInstrumentoDigitado;
    }

    public void setIdInstrumentoDigitado(Long idInstrumentoDigitado) {
        this.idInstrumentoDigitado = idInstrumentoDigitado;
    }
    
    
    
}
