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
@Table(name = "bid_indicadores")
@NamedQueries({
    @NamedQuery(name = "IndicadoresBID.existenValores", query = "SELECT i FROM IndicadoresBID i where i.paisBID=:paisBID and i.periodoBID=:periodoBID"),
    @NamedQuery(name = "IndicadoresBID.deleteValores", query = "DELETE FROM IndicadoresBID i where i.paisBID=:paisBID and i.periodoBID=:periodoBID"),
    @NamedQuery(name = "IndicadoresBID.obtenerPorPeriodoBID", query = "SELECT i FROM IndicadoresBID i where i.periodoBID =:periodoBID order by i.tipoIndicadorBID.id"),
    @NamedQuery(name = "IndicadoresBID.obtenerPorPeriodoBIDporId", query = "SELECT i FROM IndicadoresBID i where i.periodoBID.id=:idPeriodo order by i.tipoIndicadorBID.id"),
    @NamedQuery(name = "IndicadoresBID.obtenerPorPeriodoBIDPais", query = "SELECT i FROM IndicadoresBID i where i.periodoBID=:periodoBID and i.paisBID=:paisBID order by i.tipoIndicadorBID.id")

})
public class IndicadoresBID implements Serializable 
{    
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    @SequenceGenerator(name = "SEC_PK_BID_INDICADORES", sequenceName = "SEC_PK_BID_INDICADORES", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEC_PK_BID_INDICADORES")
    private Long id;
    
    @JoinColumn(name = "id_periodo", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private PeriodosBID periodoBID;
    
    @JoinColumn(name = "id_pais", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private PaisesBID paisBID;
    
    @JoinColumn(name = "id_tipo_indicador", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TiposIndicadoresBID tipoIndicadorBID;

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

    public TiposIndicadoresBID getTipoIndicadorBID() {
        return tipoIndicadorBID;
    }

    public void setTipoIndicadorBID(TiposIndicadoresBID tipoIndicadorBID) {
        this.tipoIndicadorBID = tipoIndicadorBID;
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
