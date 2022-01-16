/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.entidad.digitado;

import co.stackpointer.cier.modelo.entidad.establecimiento.Establecimiento;
import co.stackpointer.cier.modelo.entidad.establecimiento.Sede;
import co.stackpointer.cier.modelo.entidad.instrumento.Pregunta;
import co.stackpointer.cier.modelo.entidad.instrumento.Respuesta;
import co.stackpointer.cier.modelo.entidad.instrumento.TipoElemento;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author StackPointer
 */
@Entity
@Table(name = "dig_elementos")
@NamedQueries({
    @NamedQuery(name = "Elemento.findAll", query = "SELECT a FROM Elemento a"),
    @NamedQuery(name = "Elemento.findByTipoInstrumento", query = "SELECT a FROM Elemento a where a.tipoElemento.codigo = :tipoElemento and a.instrumentoDigitado = :instrumento"),
    @NamedQuery(name = "Elemento.findByDescripcionInstrumento", query = "SELECT a FROM Elemento a where a.descripcion = :descripcion and a.instrumentoDigitado = :instrumento"),
    @NamedQuery(name = "Elemento.findByInstrumento", query = "SELECT a FROM Elemento a where a.instrumentoDigitado = :instrumento"),
    @NamedQuery(name = "Elemento.findByInstrumentoId", query = "SELECT a FROM Elemento a where a.instrumentoDigitado.id = :idInstrumentoDigitado"),
    @NamedQuery(name = "Elemento.findById", query = "SELECT a FROM Elemento a where a.id = :idElemento"),
    @NamedQuery(name = "Elemento.findByInstrumentoIdDescripcionElemento", query = "SELECT a FROM Elemento a where a.instrumentoDigitado.id = :idInstrumento and a.descripcion = :descripcion"),
    @NamedQuery(name = "Elemento.findByIdInstrumentoTipoElemento", query = "SELECT e FROM Elemento e where e.instrumentoDigitado.id = :idInstrumento and e.tipoElemento.codigo = :codigoTipoElemento order by e.id"),
    @NamedQuery(name = "Elemento.findByIdInstrumentoTipoElementoListado", query = "Select e from Elemento e where e.instrumentoDigitado.id = :idInstrumento and e.tipoElemento.codigo = :codigoTipoElemento")
})
public class Elemento implements Serializable, Comparable<Elemento>, Cloneable {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "SEC_ID_ELE_DIG", sequenceName = "SEC_PK_ELE_DIG", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_ID_ELE_DIG")
    private Long id;
    @Column(name = "descripcion")
    private String descripcion;
    @JoinColumn(name = "id_dig_elem_padre", referencedColumnName = "id")
    @ManyToOne(optional = true)
    private Elemento elemento;
    @JoinColumn(name = "id_dig_instrumento", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private InstrumentoDig instrumentoDigitado;
    @JoinColumn(name = "cod_tipo", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private TipoElemento tipoElemento;
    @JoinColumn(name = "id_establecimiento", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne
    private Establecimiento establecimiento;
    @JoinColumn(name = "id_sede", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne
    private Sede sede;
    @OneToMany(mappedBy = "elemento", cascade = CascadeType.MERGE)
    private List<RespuestaDig> respuestasList;
    @Transient
    private List<Pregunta> preguntas;
    @Transient
    private String descripcionInicial;
    @Transient
    private Elemento elementoInicial;
    @Transient
    private InstrumentoDig instrumentoDigitadoInicial;
    @Transient
    private TipoElemento tipoElementoInicial;
    @Transient
    private Map<Respuesta, RespuestaDig> mapaRespuestas;
    @Transient
    private List<RespuestaDig> respuestasDigitadasIniciales;
    @Transient
    private int numeroEspacio;
    @Transient
    private String espacioEscolar;

    public Elemento() {
        respuestasList = new ArrayList<RespuestaDig>();
        mapaRespuestas = new HashMap<Respuesta, RespuestaDig>();
        respuestasDigitadasIniciales = new ArrayList<RespuestaDig>();
    }

    public Elemento(Elemento elemento, Establecimiento establecimiento, Sede sede) {
        descripcion = elemento.getDescripcion();
        this.elemento = elemento.getElemento();
        tipoElemento = elemento.getTipoElemento();
        instrumentoDigitado = elemento.getInstrumentoDigitado();
        this.establecimiento = establecimiento;
        this.sede = sede;
        respuestasList = new ArrayList<RespuestaDig>();
        respuestasDigitadasIniciales = new ArrayList<RespuestaDig>();
    }

    public Elemento(String descripcion, InstrumentoDig instrumentoDigitado, TipoElemento tipoElemento) {
        preguntas = new ArrayList<Pregunta>();
        respuestasList = new ArrayList<RespuestaDig>();
        respuestasDigitadasIniciales = new ArrayList<RespuestaDig>();
        this.descripcion = descripcion;
        this.instrumentoDigitado = instrumentoDigitado;
        this.tipoElemento = tipoElemento;
    }

    @PostConstruct
    public void cargarDatosIniciales() {
        this.descripcionInicial = this.descripcion;
        this.elementoInicial = this.elemento;
        this.instrumentoDigitadoInicial = this.instrumentoDigitado;
        this.tipoElementoInicial = this.tipoElemento;        
    }

    @PostLoad
    public void initSomeTransient() {
        this.numeroEspacio = extraerNumeroEspacio(this.descripcion.trim());
    }

    protected int extraerNumeroEspacio(String cadena) {
        String REGEX = "([A-Za-z0-9]+)_([0-9]+)";
        int resultado = -1;
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(cadena);
        if (matcher.matches()) {
            resultado = matcher.groupCount() > 1 ? Integer.parseInt(matcher.group(2)) : 0;
        }
        return resultado;
    }

    public void agregarRespuesta(RespuestaDig respuesta) {
        respuesta.setElemento(this);
        this.respuestasList.add(respuesta);
    }

    public void agregarRespuestaIniciales(RespuestaDig respuesta) {
        respuesta.setElemento(this);
        this.respuestasDigitadasIniciales.add(respuesta);
    }

    public void limpiarRespuesta() {
        this.respuestasDigitadasIniciales.clear();
    }

    public void agregarPregunta(Pregunta pregunta) {
        this.preguntas.add(pregunta);
    }

    public void removerRespuesta(RespuestaDig respuesta) {
        respuesta.setElemento(null);
        this.respuestasList.remove(respuesta);
    }

    public Long getId() {
        return id;
    }

    public List<Pregunta> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(List<Pregunta> preguntas) {
        this.preguntas = preguntas;
    }

    public List<RespuestaDig> getRespuestasList() {
        return respuestasList;
    }

    public void setRespuestasList(List<RespuestaDig> respuestasList) {
        this.respuestasList = respuestasList;
    }

    public TipoElemento getTipoElemento() {
        return tipoElemento;
    }

    public void setTipoElemento(TipoElemento tipoElemento) {
        this.tipoElemento = tipoElemento;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;       
    }

    public Establecimiento getEstablecimiento() {
        return establecimiento;
    }

    public void setEstablecimiento(Establecimiento establecimiento) {
        this.establecimiento = establecimiento;
    }

    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }

    public Elemento getElemento() {
        return elemento;
    }

    public void setElemento(Elemento elemento) {
        this.elemento = elemento;
    }

    public InstrumentoDig getInstrumentoDigitado() {
        return instrumentoDigitado;
    }

    public String getDescripcionInicial() {
        return descripcionInicial;
    }

    public void setDescripcionInicial(String descripcionInicial) {
        this.descripcionInicial = descripcionInicial;
    }

    public Elemento getElementoInicial() {
        return elementoInicial;
    }

    public void setElementoInicial(Elemento elementoInicial) {
        this.elementoInicial = elementoInicial;
    }

    public InstrumentoDig getInstrumentoDigitadoInicial() {
        return instrumentoDigitadoInicial;
    }

    public void setInstrumentoDigitadoInicial(InstrumentoDig instrumentoDigitadoInicial) {
        this.instrumentoDigitadoInicial = instrumentoDigitadoInicial;
    }

    public TipoElemento getTipoElementoInicial() {
        return tipoElementoInicial;
    }

    public void setTipoElementoInicial(TipoElemento tipoElementoInicial) {
        this.tipoElementoInicial = tipoElementoInicial;
    }

    public void setInstrumentoDigitado(InstrumentoDig InstrumentoDigitado) {
        this.instrumentoDigitado = InstrumentoDigitado;
    }

    public boolean isModificado() {
        return !this.descripcion.equals(this.descripcionInicial)
                || !this.elemento.equals(this.elementoInicial)
                || !this.instrumentoDigitado.equals(this.instrumentoDigitadoInicial)
                || !this.tipoElemento.equals(this.tipoElementoInicial);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + (this.descripcion != null ? this.descripcion.hashCode() : 0);
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
        final Elemento other = (Elemento) obj;
        if ((this.descripcion == null) ? (other.descripcion != null) : !this.descripcion.equals(other.descripcion)) {
            return false;
        }
        return true;
    }

    public Map<Respuesta, RespuestaDig> getMapaRespuestas() {
        return mapaRespuestas;
    }

    public void setMapaRespuestas(Map<Respuesta, RespuestaDig> mapaRespuestas) {
        this.mapaRespuestas = mapaRespuestas;
    }

    public void agregarClaveMapa(Respuesta respuesta, RespuestaDig respuestaDigitada) {
        this.mapaRespuestas.put(respuesta, respuestaDigitada);
    }

    public List<RespuestaDig> getRespuestasDigitadasIniciales() {
        return respuestasDigitadasIniciales;
    }

    public void setRespuestasDigitadasIniciales(List<RespuestaDig> respuestasDigitadasIniciales) {
        this.respuestasDigitadasIniciales = respuestasDigitadasIniciales;
    }

    public int getNumeroEspacio() {
        return numeroEspacio;
    }

    public void setNumeroEspacio(int numeroEspacio) {
        this.numeroEspacio = numeroEspacio;
    }

    public String getEspacioEscolar() {
        return espacioEscolar;
    }

    public void setEspacioEscolar(String espacioEscolar) {
        this.espacioEscolar = espacioEscolar;
    }
    
    @Override
    public int compareTo(Elemento o) {
        if (this.getNumeroEspacio() < (o.getNumeroEspacio())) {
            return -1;
        } else {
            return 1;
        }
    }

    public String getIdEdificio() {
        String idEdificio = "N/A";
        Respuesta respuestaIdEdificio = null;
        try {
            if (this.preguntas != null && !this.preguntas.isEmpty()) {
                if (this.preguntas.get(0).getRespuestaList() != null && !this.preguntas.get(0).getRespuestaList().isEmpty()) {
                    respuestaIdEdificio = preguntas.get(0).getRespuestaList().get(0);
                }
            }
            if (this.mapaRespuestas != null && !this.mapaRespuestas.isEmpty()) {
                idEdificio = this.mapaRespuestas.get(respuestaIdEdificio).getValor();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return idEdificio;
    }

    public String getIdEspacio() {
        String idEspacio = "N/A";
        Respuesta respuestaIdEdificio = null;
        try {
            if (this.preguntas != null && !this.preguntas.isEmpty()) {
                if (this.preguntas.get(1).getRespuestaList() != null && !this.preguntas.get(0).getRespuestaList().isEmpty()) {
                    respuestaIdEdificio = preguntas.get(1).getRespuestaList().get(0);
                }
            }
            if (this.mapaRespuestas != null && !this.mapaRespuestas.isEmpty()) {
                idEspacio = this.mapaRespuestas.get(respuestaIdEdificio).getValor();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return idEspacio;
    }

    public Object clone() {
        Elemento elemento = new Elemento();
        elemento.setDescripcion(this.getDescripcion());
        elemento.setTipoElemento(this.getTipoElemento());
        elemento.setElemento(this.getElemento());
        elemento.setEstablecimiento(this.getEstablecimiento());
        elemento.setSede(this.getSede());
        return elemento;
    }
}
