/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.entidad.instrumento;

import co.stackpointer.cier.modelo.entidad.digitado.RespuestaDig;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.TenantDiscriminatorColumn;

/**
 *
 * @author StackPointer
 */
@Entity
@Table(name = "ins_preguntas")
@Multitenant
@TenantDiscriminatorColumn(name = "id_tnt", contextProperty = "TENANT_ID", discriminatorType = DiscriminatorType.INTEGER)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pregunta.findAll", query = "SELECT p FROM Pregunta p"),
    @NamedQuery(name = "Pregunta.findPreguntaByInstrumento", query = "SELECT p FROM Pregunta p where p.instrumento = :instrumento order by p.modulo.orden, p.seccion.orden, p.orden")
    ,@NamedQuery(name = "Pregunta.obtenerPreguntaPorTipoElementoModuloSeccion", query = "SELECT p FROM Pregunta p where p.tipoElemento = :tipoElemento and p.modulo = :modulo and p.seccion = :seccion order by p.modulo.orden, p.seccion.orden, p.orden asc")
    ,@NamedQuery(name = "Pregunta.obtenerPreguntaPorTipoElementoModuloSeccionString", query = "SELECT p FROM Pregunta p where p.tipoElemento.codigo = :tipoElemento and p.modulo.codigo = :modulo and p.seccion.codigo = :seccion order by p.codigo asc")
    ,@NamedQuery(name = "Pregunta.obtenerPreguntaPorTipoElemento", query = "SELECT p FROM Pregunta p where p.tipoElemento = :tipoElemento order by p.orden")
    ,@NamedQuery(name = "Pregunta.findPreguntaByCodigo", query ="SELECT p FROM Pregunta p where p.codigo = :codigo")

})
public class Pregunta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "codigo")
    private String codigo;

    @Column(name = "orden")
    private int orden;
    
    @Column(name = "enunciado_1")
    private String enunciado1;
    
    @Column(name = "enunciado_2")
    private String enunciado2;
    
    @Column(name = "afecta_indicador")
    private int afectaIndicador;
    
    @Column(name = "estado")
    private String estado;
    
    @Column(name = "etiqueta_1")
    private String etiqueta1;
    
    @Column(name = "etiqueta_2")
    private String etiqueta2;
    
    @Column(name = "numeral")
    private String numeral;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codPreguntas")
    @OrderBy(value = "codigo")
    private List<Respuesta> respuestaList;
    
    @JoinColumn(name = "cod_seccion", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private Seccion seccion;
    
    @OneToMany(mappedBy = "preguntaPadre")
    private List<Pregunta> preguntaList;
    
    @JoinColumn(name = "cod_pre_padre", referencedColumnName = "codigo")
    @ManyToOne
    private Pregunta preguntaPadre;
    
    @JoinColumn(name = "cod_modulo", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private ModuloIns modulo;
    
    @JoinColumn(name = "cod_instrumento", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private Instrumento instrumento;
    
    @JoinColumn(name = "cod_tipo_elemento", referencedColumnName = "codigo")
    @ManyToOne()
    private TipoElemento tipoElemento;
    
    @Transient
    private List<RespuestaDig> respuestasDigitadas;

    public Pregunta() {
        respuestasDigitadas = new ArrayList<RespuestaDig>();
    }

    public Pregunta(String codigo) {
        this.codigo = codigo;
    }
    
    public void agregarRespuestaDigitada(RespuestaDig rspDg){
        this.respuestasDigitadas.add(rspDg);
    }

    public TipoElemento getTipoElemento() {
        return tipoElemento;
    }

    public void setTipoElemento(TipoElemento tipoElemento) {
        this.tipoElemento = tipoElemento;
    }
      
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public String getEnunciado1() {
        return enunciado1;
    }

    public void setEnunciado1(String enunciado1) {
        this.enunciado1 = enunciado1;
    }

    public String getEnunciado2() {
        return enunciado2;
    }

    public void setEnunciado2(String enunciado2) {
        this.enunciado2 = enunciado2;
    }

    public int getAfectaIndicador() {
        return afectaIndicador;
    }

    public void setAfectaIndicador(int afectaIndicador) {
        this.afectaIndicador = afectaIndicador;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @XmlTransient
    public List<Respuesta> getRespuestaList() {
        return respuestaList;
    }

    public void setRespuestaList(List<Respuesta> respuestaList) {
        this.respuestaList = respuestaList;
    }

    public Seccion getSeccion() {
        return seccion;
    }

    public void setSeccion(Seccion seccion) {
        this.seccion = seccion;
    }

    @XmlTransient
    public List<Pregunta> getPreguntaList() {
        return preguntaList;
    }

    public void setPreguntaList(List<Pregunta> preguntaList) {
        this.preguntaList = preguntaList;
    }

    public Pregunta getPreguntaPadre() {
        return preguntaPadre;
    }

    public void setPreguntaPadre(Pregunta preguntaPadre) {
        this.preguntaPadre = preguntaPadre;
    }

    public ModuloIns getModulo() {
        return modulo;
    }

    public void setModulo(ModuloIns modulo) {
        this.modulo = modulo;
    }

    public Instrumento getInstrumento() {
        return instrumento;
    }

    public void setInstrumento(Instrumento instrumento) {
        this.instrumento = instrumento;
    }

    public List<RespuestaDig> getRespuestasDigitadas() {
        return respuestasDigitadas;
    }

    public void setRespuestasDigitadas(List<RespuestaDig> respuestasDigitadas) {
        this.respuestasDigitadas = respuestasDigitadas;
    }

    public String getEtiqueta1() {
        return etiqueta1;
    }

    public String getEtiqueta2() {
        return etiqueta2;
    }

    public String getNumeral() {
        return numeral;
    }

    public void setNumeral(String numeral) {
        this.numeral = numeral;
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
        if (!(object instanceof Pregunta)) {
            return false;
        }
        Pregunta other = (Pregunta) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.stackpointer.cier.modelo.entidades.instumentos.Pregunta[ codigo=" + codigo + " ]";
    }
    
}
