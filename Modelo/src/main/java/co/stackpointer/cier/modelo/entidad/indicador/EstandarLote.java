/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.entidad.indicador;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.TenantDiscriminatorColumn;

/**
 *
 * @author StackPointer
 */
@Entity
@Table(name = "ind_referencia_estandar_lote")
@Multitenant
@TenantDiscriminatorColumn(name = "id_tnt", contextProperty = "TENANT_ID", discriminatorType = DiscriminatorType.INTEGER)
@NamedQueries({
    @NamedQuery(name = "EstandarLote.findAll", query = "SELECT e FROM EstandarLote e order by e.numPisos asc, e.minAlumnos asc"),
    @NamedQuery(name = "EstandarLote.porId", query = "SELECT e FROM EstandarLote e WHERE e.id = :id"),
    @NamedQuery(name = "EstandarLote.existentes", query = "SELECT e FROM EstandarLote e WHERE e.numPisos = :pisos"),
    @NamedQuery(name = "EstandarLote.existentesMenosEditable", query = "SELECT e FROM EstandarLote e WHERE e.numPisos = :pisos and e.id <> :id")})

public class EstandarLote implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    @SequenceGenerator(name = "SEC_PK_IND_ESTANDAR_LOTE", sequenceName = "SEC_PK_IND_ESTANDAR_LOTE", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEC_PK_IND_ESTANDAR_LOTE")
    private Long id;
  
    
    @Column(name = "min_alumnos")
    private Long minAlumnos;
    
    @Column(name = "max_alumnos")
    private Long maxAlumnos;  
    
    @Column(name = "num_pisos")
    private Long numPisos;  
    
    @Column(name = "area_min_lote_urb")
    private BigDecimal areaMinimaLoteUrbano; 
    
    @Column(name = "area_min_lote_urb_mrg")
    private BigDecimal areaMinimaLoteUrbanoMarginal; 
    
    @Column(name = "area_min_lote_rrl")
    private BigDecimal areaMinimaLoteRural; 
    
    @Column(name = "indice_ocupacion")
    private BigDecimal indiceOcupacion; 
    
    @Column(name = "indice_construccion")
    private BigDecimal indiceConstruccion; 
    
    @Column(name = "area_cubierta")
    private BigDecimal areaCubierta;
    
    
    public EstandarLote() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMinAlumnos() {
        return minAlumnos;
    }

    public void setMinAlumnos(Long minAlumnos) {
        this.minAlumnos = minAlumnos;
    }

    public Long getMaxAlumnos() {
        return maxAlumnos;
    }

    public void setMaxAlumnos(Long maxAlumnos) {
        this.maxAlumnos = maxAlumnos;
    }

    public Long getNumPisos() {
        return numPisos;
    }

    public void setNumPisos(Long numPisos) {
        this.numPisos = numPisos;
    }

    public BigDecimal getAreaMinimaLoteUrbano() {
        return areaMinimaLoteUrbano;
    }

    public void setAreaMinimaLoteUrbano(BigDecimal areaMinimaLoteUrbano) {
        this.areaMinimaLoteUrbano = areaMinimaLoteUrbano;
    }

    public BigDecimal getAreaMinimaLoteUrbanoMarginal() {
        return areaMinimaLoteUrbanoMarginal;
    }

    public void setAreaMinimaLoteUrbanoMarginal(BigDecimal areaMinimaLoteUrbanoMarginal) {
        this.areaMinimaLoteUrbanoMarginal = areaMinimaLoteUrbanoMarginal;
    }

    public BigDecimal getAreaMinimaLoteRural() {
        return areaMinimaLoteRural;
    }

    public void setAreaMinimaLoteRural(BigDecimal areaMinimaLoteRural) {
        this.areaMinimaLoteRural = areaMinimaLoteRural;
    }


    public BigDecimal getIndiceOcupacion() {
        return indiceOcupacion;
    }

    public void setIndiceOcupacion(BigDecimal indiceOcupacion) {
        this.indiceOcupacion = indiceOcupacion;
    }

    public BigDecimal getIndiceConstruccion() {
        return indiceConstruccion;
    }

    public void setIndiceConstruccion(BigDecimal indiceConstruccion) {
        this.indiceConstruccion = indiceConstruccion;
    }

    public BigDecimal getAreaCubierta() {
        return areaCubierta;
    }

    public void setAreaCubierta(BigDecimal areaCubierta) {
        this.areaCubierta = areaCubierta;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final EstandarLote other = (EstandarLote) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }   
    

}
