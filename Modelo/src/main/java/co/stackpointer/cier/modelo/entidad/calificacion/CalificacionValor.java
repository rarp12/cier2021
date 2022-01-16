/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.entidad.calificacion;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.TenantDiscriminatorColumn;

/**
 *
 * @author StackPointer
 */
@Entity
@Table(name = "cal_valores")
@Multitenant
@TenantDiscriminatorColumn(name = "id_tnt", contextProperty = "TENANT_ID", discriminatorType = DiscriminatorType.INTEGER)
@NamedQueries({
    @NamedQuery(name = "CalificacionValor.deleteAll", query = "DELETE FROM CalificacionValor v where v.periodo=:periodo")
})
public class CalificacionValor implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    @SequenceGenerator(name = "SEC_ID_CAL_VALORES", sequenceName = "SEC_PK_CAL_VALORES", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEC_ID_CAL_VALORES")
    private Long id;
    
    @Column(name = "periodo")
    private int periodo;
    
    @Column(name = "id_entidad")
    private Long id_entidad;
    
    @Column(name = "id_var_amb")
    private int idVariableAmbito;
    
    @Column(name = "nivel_consulta")
    private int nivel_consulta;
    
    @Column(name = "valor")
    private String valor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPeriodo() {
        return periodo;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    public Long getId_entidad() {
        return id_entidad;
    }

    public void setId_entidad(Long id_entidad) {
        this.id_entidad = id_entidad;
    }

    public int getIdVariableAmbito() {
        return idVariableAmbito;
    }

    public void setIdVariableAmbito(int idVariableAmbito) {
        this.idVariableAmbito = idVariableAmbito;
    }

    public int getNivel_consulta() {
        return nivel_consulta;
    }

    public void setNivel_consulta(int nivel_consulta) {
        this.nivel_consulta = nivel_consulta;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
      
    
    
}
