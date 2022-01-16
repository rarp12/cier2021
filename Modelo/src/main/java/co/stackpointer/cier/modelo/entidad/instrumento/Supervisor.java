/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.entidad.instrumento;

import co.stackpointer.cier.modelo.tipo.Estado;
import co.stackpointer.cier.modelo.utilidad.UtilFecha;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;
import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.TenantDiscriminatorColumn;

/**
 *
 * @author StackPointer
 */
@Entity
@Table(name = "ins_supervisores")
@Multitenant
@TenantDiscriminatorColumn(name = "id_tnt", contextProperty = "TENANT_ID", discriminatorType = DiscriminatorType.INTEGER)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Supervisor.findAll", query = "SELECT s FROM Supervisor s"),
    @NamedQuery(name = "Supervisor.findByCodigo", query = "SELECT s FROM Supervisor s where s.identificacion = :codigo and s.estado = co.stackpointer.cier.modelo.tipo.Estado.A"),
    @NamedQuery(name = "Supervisor.findByIdentificacion", query = "SELECT e FROM Supervisor e where e.identificacion = :identificacion")})
public class Supervisor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "identification")
    private String identificacion;
    @Column(name = "nombre")
    private String nombre;
    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private Estado estado;
    @Column(name = "periodo")
    private int periodo;
    @Column(name = "cod_tipo_id")
    private String codTipoIdentificacion;
    @JoinColumn(name = "cod_instrumento", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private Instrumento instrumento;
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "email")
    private String email;
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "celular")
    private String celular;
    @Column(name = "salud")
    private String salud;
    @Column(name = "persona_contacto")
    private String personaContacto;
    @Column(name = "tel_persona_contacto")
    private String telefonoContacto;
    @Column(name = "fec_inic_vigencia")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fecIniVigencia;
    @Column(name = "fec_fin_vigencia")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fecFinVigencia;

    public Supervisor() {
        estado = Estado.A;
        periodo = UtilFecha.obtenerPeriodoActual();
        fecIniVigencia = UtilFecha.obtenerFechaActual();
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public int getPeriodo() {
        return periodo;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    public String getCodTipoIdentificacion() {
        return codTipoIdentificacion;
    }

    public void setCodTipoIdentificacion(String codTipoIdentificacion) {
        this.codTipoIdentificacion = codTipoIdentificacion;
    }

    public Instrumento getInstrumento() {
        return instrumento;
    }

    public void setInstrumento(Instrumento instrumento) {
        this.instrumento = instrumento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getSalud() {
        return salud;
    }

    public void setSalud(String salud) {
        this.salud = salud;
    }

    public String getPersonaContacto() {
        return personaContacto;
    }

    public void setPersonaContacto(String personaContacto) {
        this.personaContacto = personaContacto;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public Date getFecIniVigencia() {
        return fecIniVigencia;
    }

    public void setFecIniVigencia(Date fecIniVigencia) {
        this.fecIniVigencia = fecIniVigencia;
    }

    public Date getFecFinVigencia() {
        return fecFinVigencia;
    }

    public void setFecFinVigencia(Date fecFinVigencia) {
        this.fecFinVigencia = fecFinVigencia;
    }
}
