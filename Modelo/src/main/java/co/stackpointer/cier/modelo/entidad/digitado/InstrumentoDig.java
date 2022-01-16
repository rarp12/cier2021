/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.entidad.digitado;

import co.stackpointer.cier.modelo.entidad.dpa.Nivel;
import co.stackpointer.cier.modelo.entidad.establecimiento.Establecimiento;
import co.stackpointer.cier.modelo.entidad.establecimiento.Predio;
import co.stackpointer.cier.modelo.entidad.establecimiento.Sede;
import co.stackpointer.cier.modelo.entidad.instrumento.Instrumento;
import co.stackpointer.cier.modelo.tipo.EstadoInstrumento;
import co.stackpointer.cier.modelo.tipo.FuenteDigitacion;
import co.stackpointer.cier.modelo.utilidad.UtilFecha;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.TenantDiscriminatorColumn;

/**
 *
 * @author StackPointer
 */
@Entity
@Table(name = "dig_instrumentos")
@Multitenant
@TenantDiscriminatorColumn(name = "id_tnt", contextProperty = "TENANT_ID", discriminatorType = DiscriminatorType.INTEGER)
@NamedQueries({
    @NamedQuery(name = "InstrumentoDigitado.findAll", query = "SELECT i FROM InstrumentoDig i"),
    @NamedQuery(name = "InstrumentoDigitado.findByFechaBoletaPredio", query = "SELECT i FROM InstrumentoDig i where i.boletaCensal =:boletaCensal and i.fechaEncuesta = :fechaEncuesta and i.predio = :predio"),
    @NamedQuery(name = "InstrumentoDigitado.findByFechaBoletaPredioSedeEstablecimiento", query = "SELECT i FROM InstrumentoDig i where i.boletaCensal =:boletaCensal and i.fechaEncuesta = :fechaEncuesta and i.predio = :predio and i.sede = :sede and i.establecimiento = :establecimiento"),
    @NamedQuery(name = "InstrumentoDigitado.consultarPeriodosCreacion", query = "SELECT DISTINCT i.periodoCreacion FROM InstrumentoDig i ORDER BY i.periodoCreacion DESC"),
    @NamedQuery(name = "InstrumentoDigitado.findByCodigo", query = "SELECT i FROM InstrumentoDig i where i.id =:codigo"),
    @NamedQuery(name = "Instrumento.findRepetido", query = "SELECT i FROM InstrumentoDig i where i.predio = :predio and i.sede = :sede and i.establecimiento = :establecimiento and (i.estado = co.stackpointer.cier.modelo.tipo.EstadoInstrumento.N or i.estado = co.stackpointer.cier.modelo.tipo.EstadoInstrumento.E) "
                                                         + "and i.version = (select max(i2.version) FROM InstrumentoDig i2 where i2.predio=i.predio and i2.sede=i.sede and i2.establecimiento=i.establecimiento)"),
    @NamedQuery(name = "Instrumento.estadoInstrumentoRepetido", query = "SELECT i.estado FROM InstrumentoDig i where i.predio = :predio and i.sede = :sede and i.establecimiento = :establecimiento and (i.estado = co.stackpointer.cier.modelo.tipo.EstadoInstrumento.N or i.estado = co.stackpointer.cier.modelo.tipo.EstadoInstrumento.E) "
                                                                       + "and i.version = (select max(i2.version) FROM InstrumentoDig i2 where i2.predio=i.predio and i2.sede=i.sede and i2.establecimiento=i.establecimiento)"),
    @NamedQuery(name = "Instrumento.ultimoEmitido", query ="SELECT i FROM InstrumentoDig i where i.predio = :predio and i.estado = :estado order by i.version desc" ),
    @NamedQuery(name = "Instrumento.ultimaVersion", query = "SELECT max(i.version) FROM InstrumentoDig i where i.predio = :predio"),
    @NamedQuery(name = "InstrumentoDigitado.consultaPorPredioSedeEstablecimiento", query ="Select i from InstrumentoDig i where i.predio = :predio and i.sede = :sede and i.establecimiento = :establecimiento order by i.fechaCreacion"),
    @NamedQuery(name = "Instrumento.consultaPorCodigoSede", query ="Select i from InstrumentoDig i where i.sede.codigo = :codigoSede"),
    @NamedQuery(name = "InstrumentoDigitado.findByCodPredioBoleta", query = "SELECT i FROM InstrumentoDig i where i.boletaCensal =:boletaCensal and i.predio.codigo = :codigoPredio "
            + "and i.version = (select max(i2.version) FROM InstrumentoDig i2 where i2.boletaCensal=i.boletaCensal and i2.predio.codigo=i.predio.codigo)"),
})
public class InstrumentoDig implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    @SequenceGenerator(name = "SEC_ID_INST_DIG", sequenceName = "SEC_PK_INST_DIG", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_ID_INST_DIG")
    private Long id;
    @Column(name = "boleta_censal")
    private String boletaCensal;
    @ManyToOne
    @JoinColumn(name = "id_establecimiento", referencedColumnName = "id")
    private Establecimiento establecimiento;
    @ManyToOne
    @JoinColumn(name = "id_sede", referencedColumnName = "id")
    private Sede sede;
    @ManyToOne
    @JoinColumn(name = "id_predio", referencedColumnName = "id")
    private Predio predio;
    @Column(name = "fecha_encuesta")
    @Temporal(TemporalType.DATE)
    private Date fechaEncuesta;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;
    @Column(name = "fecha_emision")
    @Temporal(TemporalType.DATE)
    private Date fechaEmision;
    @Column(name = "fecha_modificacion")
    @Temporal(TemporalType.DATE)
    private Date fechaModificacion;
    @Column(name = "fecha_anulacion")
    @Temporal(TemporalType.DATE)
    private Date fechaAnulacion;
    @Column(name = "usuario_creacion")
    private String usuarioCreacion;
    @Column(name = "usuario_anulacion")
    private String usuarioAnulacion;
    @Column(name = "motivo_anulacion")
    private String motivoAnulacion;
    @Column(name = "usuario_emision")
    private String usuarioEmision;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "estado")
    private EstadoInstrumento estado;
    @Column(name = "periodo_creacion")
    private int periodoCreacion;
    @Column(name = "periodo_emision")
    private int periodoEmision;
    @Column(name = "version")
    private int version;
    @Column
    private int origen;
    @Column
    private int fuente;
    @Column(name = "fecha_actualizacion")
    @Temporal(TemporalType.DATE)
    private Date fechaActualizacion;
    @Column(name = "usuario_actualizacion")
    private String usuarioActualizacion;
    @ManyToOne
    @JoinColumn(name = "id_nivel0", referencedColumnName = "id")
    private Nivel nivel0;
    @ManyToOne
    @JoinColumn(name = "id_nivel1", referencedColumnName = "id")
    private Nivel nivel1;
    @ManyToOne
    @JoinColumn(name = "id_nivel2", referencedColumnName = "id")
    private Nivel nivel2;
    @ManyToOne
    @JoinColumn(name = "id_nivel3", referencedColumnName = "id")
    private Nivel nivel3;
    @ManyToOne
    @JoinColumn(name = "id_nivel4", referencedColumnName = "id")
    private Nivel nivel4;
    @ManyToOne
    @JoinColumn(name = "id_nivel5", referencedColumnName = "id")
    private Nivel nivel5;
    @JoinColumn(name = "cod_instrumento", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private Instrumento instrumento;
    @OneToMany(mappedBy = "instrumentoDigitado")
    private List<Elemento> elementosList;
    @Transient
    private Establecimiento establecimientoInicial;
    @Transient
    private Sede sedeInicial;
    @Transient
    private Predio predioInicial;
    @Transient
    private List<String> respNoEmitidas;

    public InstrumentoDig() {
        this.estado = EstadoInstrumento.N;
        this.version = 1;
        this.fechaCreacion = UtilFecha.obtenerFechaActual();
        this.periodoCreacion = UtilFecha.obtenerPeriodoActual();
        elementosList = new ArrayList<Elemento>();
        cargarDatosIniciales();
    }

    public InstrumentoDig(Instrumento instrumento) {
        this.instrumento = instrumento;
        this.estado = EstadoInstrumento.N;
        this.fechaCreacion = UtilFecha.obtenerFechaActual();
        this.periodoCreacion = UtilFecha.obtenerPeriodoActual();
        elementosList = new ArrayList<Elemento>();
        cargarDatosIniciales();
    }

    public void cargarDatosIniciales() {
        this.establecimientoInicial = this.establecimiento;
        this.predioInicial = this.predio;
        this.sedeInicial = this.sede;
    }

    public void agregarElemento(Elemento elemento) {
        elemento.setInstrumentoDigitado(this);
        this.elementosList.add(elemento);
    }

    public void removerElemento(Elemento elemento) {
        elemento.setInstrumentoDigitado(null);
        this.elementosList.remove(elemento);
    }

    public InstrumentoDig(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBoletaCensal() {
        return boletaCensal;
    }

    public void setBoletaCensal(String boletaCensal) {
        this.boletaCensal = boletaCensal;
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

    public Predio getPredio() {
        return predio;
    }

    public void setPredio(Predio predio) {
        this.predio = predio;
    }

    public Date getFechaEncuesta() {
        return fechaEncuesta;
    }

    public void setFechaEncuesta(Date fechaEncuesta) {
        this.fechaEncuesta = fechaEncuesta;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Date getFechaAnulacion() {
        return fechaAnulacion;
    }

    public void setFechaAnulacion(Date fechaAnulacion) {
        this.fechaAnulacion = fechaAnulacion;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public String getUsuarioAnulacion() {
        return usuarioAnulacion;
    }

    public void setUsuarioAnulacion(String usuarioAnulacion) {
        this.usuarioAnulacion = usuarioAnulacion;
    }

    public String getMotivoAnulacion() {
        return motivoAnulacion;
    }

    public void setMotivoAnulacion(String motivoAnulacion) {
        this.motivoAnulacion = motivoAnulacion;
    }

    public String getUsuarioEmision() {
        return usuarioEmision;
    }

    public void setUsuarioEmision(String usuarioEmision) {
        this.usuarioEmision = usuarioEmision;
    }

    public EstadoInstrumento getEstado() {
        return estado;
    }

    public void setEstado(EstadoInstrumento estado) {
        this.estado = estado;
    }

    public int getPeriodoCreacion() {
        return periodoCreacion;
    }

    public void setPeriodoCreacion(int periodoCreacion) {
        this.periodoCreacion = periodoCreacion;
    }

    public int getPeriodoEmision() {
        return periodoEmision;
    }

    public void setPeriodoEmision(int periodoEmision) {
        this.periodoEmision = periodoEmision;
    }

    public int getVersion() {
        return version;
    }
    
    public void setVersion(int version) {
        this.version = version;
    }

    public int getOrigen() {
        return origen;
    }

    public void setOrigen(int origen) {
        this.origen = origen;
    }
    
    public Nivel getNivel0() {
        return nivel0;
    }

    public void setNivel0(Nivel nivel0) {
        this.nivel0 = nivel0;
    }

    public Nivel getNivel1() {
        return nivel1;
    }

    public void setNivel1(Nivel nivel1) {
        this.nivel1 = nivel1;
    }

    public Nivel getNivel2() {
        return nivel2;
    }

    public void setNivel2(Nivel nivel2) {
        this.nivel2 = nivel2;
    }

    public Nivel getNivel3() {
        return nivel3;
    }

    public void setNivel3(Nivel nivel3) {
        this.nivel3 = nivel3;
    }

    public Nivel getNivel4() {
        return nivel4;
    }

    public void setNivel4(Nivel nivel4) {
        this.nivel4 = nivel4;
    }

    public Nivel getNivel5() {
        return nivel5;
    }

    public void setNivel5(Nivel nivel5) {
        this.nivel5 = nivel5;
    }

    public co.stackpointer.cier.modelo.entidad.instrumento.Instrumento getInstrumento() {
        return instrumento;
    }

    public void setInstrumento(co.stackpointer.cier.modelo.entidad.instrumento.Instrumento instrumento) {
        this.instrumento = instrumento;
    }

    public List<Elemento> getElementosList() {
        return elementosList;
    }

    public void setElementosList(List<Elemento> elementosList) {
        this.elementosList = elementosList;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (this.boletaCensal != null ? this.boletaCensal.hashCode() : 0);
        hash = 83 * hash + (this.predio != null ? this.predio.hashCode() : 0);
        hash = 83 * hash + (this.fechaEncuesta != null ? this.fechaEncuesta.hashCode() : 0);
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
        final InstrumentoDig other = (InstrumentoDig) obj;
        if ((this.boletaCensal == null) ? (other.boletaCensal != null) : !this.boletaCensal.equals(other.boletaCensal)) {
            return false;
        }
        if (this.predio != other.predio && (this.predio == null || !this.predio.equals(other.predio))) {
            return false;
        }
        if (this.fechaEncuesta != other.fechaEncuesta && (this.fechaEncuesta == null || !this.fechaEncuesta.equals(other.fechaEncuesta))) {
            return false;
        }
        return true;
    }

    public Establecimiento getEstablecimientoInicial() {
        return establecimientoInicial;
    }

    public void setEstablecimientoInicial(Establecimiento establecimientoInicial) {
        this.establecimientoInicial = establecimientoInicial;
    }

    public Sede getSedeInicial() {
        return sedeInicial;
    }

    public void setSedeInicial(Sede sedeInicial) {
        this.sedeInicial = sedeInicial;
    }

    public Predio getPredioInicial() {
        return predioInicial;
    }

    public void setPredioInicial(Predio predioInicial) {
        this.predioInicial = predioInicial;
    }

    private boolean isHaCambiadoSede() {
        return sedeInicial != null && !sedeInicial.equals(sede);
    }

    private boolean isHaCambiadoPredio() {
        return predioInicial != null && !predioInicial.equals(predio);
    }

    private boolean isHaCambiadoEstablecimiento() {
        return establecimientoInicial != null && !establecimientoInicial.equals(establecimiento);
    }

    public boolean isActualizableElementos() {
        return isHaCambiadoEstablecimiento() || isHaCambiadoPredio() || isHaCambiadoSede();
    }

    public List<String> getRespNoEmitidas() {
        return respNoEmitidas;
    }

    public void setRespNoEmitidas(List<String> respNoEmitidas) {
        this.respNoEmitidas = respNoEmitidas;
    }

    public int getFuente() {
        return fuente;
    }

    public void setFuente(int fuente) {
        this.fuente = fuente;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public String getUsuarioActualizacion() {
        return usuarioActualizacion;
    }

    public void setUsuarioActualizacion(String usuarioActualizacion) {
        this.usuarioActualizacion = usuarioActualizacion;
    }
    
    public Object clone() {
        InstrumentoDig instrumento = new InstrumentoDig();
        instrumento.setBoletaCensal(this.boletaCensal);
        instrumento.setInstrumento(this.getInstrumento()); 
        instrumento.setEstablecimiento(this.getEstablecimiento());
        instrumento.setSede(this.getSede());
        instrumento.setPredio(this.getPredio());
        instrumento.setVersion(this.getVersion());
        instrumento.setUsuarioCreacion(this.getUsuarioCreacion());
        instrumento.setNivel0(this.getNivel0());
        instrumento.setNivel1(this.getNivel1());
        instrumento.setNivel2(this.getNivel2());
        instrumento.setNivel3(this.getNivel3());
        instrumento.setNivel4(this.getNivel4());
        instrumento.setNivel5(this.getNivel5());
        instrumento.setFechaEncuesta(this.fechaEncuesta);
        return instrumento;
    }
    
    public boolean isPermitirObservacion(){
        if(!this.estado.equals(EstadoInstrumento.A)){
            return true;
        }else{
            return false;
        }
            
    }
}
