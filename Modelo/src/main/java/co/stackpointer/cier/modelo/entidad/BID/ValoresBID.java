/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.entidad.BID;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "bid_valores")
@NamedQueries({
    @NamedQuery(name = "ValoresBID.existenValores", query = "SELECT v FROM ValoresBID v where v.paisBID=:paisBID and v.periodoBID=:periodoBID"),
    @NamedQuery(name = "ValoresBID.deleteValores", query = "DELETE FROM ValoresBID v where v.paisBID=:paisBID and v.periodoBID=:periodoBID")
})
public class ValoresBID implements Serializable 
{    
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    @SequenceGenerator(name = "SEC_PK_BID_VALORES", sequenceName = "SEC_PK_BID_VALORES", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEC_PK_BID_VALORES")
    private Long id;
    
    @JoinColumn(name = "id_periodo", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private PeriodosBID periodoBID;
    
    @JoinColumn(name = "id_pais", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private PaisesBID paisBID;
    
    @JoinColumn(name = "id_variable", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private VariablesBID variableBID;

    @Column(name = "valor")
    String  valor ;

    @Column(name = "periodo_info")
    int  periodoInfo ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PeriodosBID getPeriodoBID() {
        return periodoBID;
    }

    public void setPeriodoBID(PeriodosBID periodoBID) {
        this.periodoBID = periodoBID;
    }

    public PaisesBID getPaisBID() {
        return paisBID;
    }

    public void setPaisBID(PaisesBID paisBID) {
        this.paisBID = paisBID;
    }

    public VariablesBID getVariableBID() {
        return variableBID;
    }

    public void setVariableBID(VariablesBID variableBID) {
        this.variableBID = variableBID;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public int getPeriodoInfo() {
        return periodoInfo;
    }

    public void setPeriodoInfo(int periodoInfo) {
        this.periodoInfo = periodoInfo;
    }
    
}
