/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.simulacion;

import java.io.Serializable;

/**
 *
 * @author Usuario
 */
public class Registro implements Serializable {

    // niveles
    private String nivel0;
    private String nivel1;
    private String nivel2;
    private String nivel3;
    private String nivel4;
    private String nivel5;
    
    private String codNivel1;
    private String codNivel2;
    private String codNivel3;
    private String codNivel4;
    private String codNivel5;
    
    private String codEstablecimiento;
    private String nomEstablecimiento;
    private String codSede;
    private String nomSede;        // 
    private String codPredio;
    private String nomPredio;
    // otros
    private String dirPredio;
    private String propiedadPredio;
    private String zona;
    private String sector;
    private String clima;
    //calculos
    private Double m2RequeridosTotal;
    private Double m2DisponiblesTotal;
    private Double m2DeficitTotal;
    //aula
    private Double idoneidadAula;
    private Double m2RequeridosRealesAula;
    private Double m2RequeridosProyectadosAula;
    private Long unidadesAula;
    private Double m2DisponiblesAula;
    private Double m2CostosAula;
    // sanitario
    private Double idoneidadSanitario;
    private Double m2RequeridosRealesSanitario;
    private Double m2RequeridosProyectadosSanitario;
    private Long unidadesSanitario;
    private Double m2DisponiblesSanitario;
    private Double m2CostosSanitario;
    // circulacion
    private Double m2RequeridosCirculacion;
    private Double m2DisponiblesCirculacion;
    private Double m2CostosCirculacion;
    // otros
    private Double costoM2Disponibles;
    private Double m2RequeridoNuevoLote;
    private Double costoM2Lote;
    private Double faltanteAula;
    private Double costoFaltanteAula;
    private Double faltanteSanitario;
    private Double costoFaltanteSanitario;
    private Double faltanteCirculacion;
    private Double costoFaltanteCirculacion;
    private Double costoM2NuevaConstruccion;
    private Double valorTotal;

    public Registro() {
    }

    public String getNivel0() {
        return nivel0;
    }

    public void setNivel0(String nivel0) {
        this.nivel0 = nivel0;
    }

    public String getNivel1() {
        return nivel1;
    }

    public void setNivel1(String nivel1) {
        this.nivel1 = nivel1;
    }

    public String getNivel2() {
        return nivel2;
    }

    public void setNivel2(String nivel2) {
        this.nivel2 = nivel2;
    }

    public String getNivel3() {
        return nivel3;
    }

    public void setNivel3(String nivel3) {
        this.nivel3 = nivel3;
    }

    public String getNivel4() {
        return nivel4;
    }

    public void setNivel4(String nivel4) {
        this.nivel4 = nivel4;
    }

    public String getNivel5() {
        return nivel5;
    }

    public void setNivel5(String nivel5) {
        this.nivel5 = nivel5;
    }

    public String getCodNivel1() {
        return codNivel1;
    }

    public void setCodNivel1(String codNivel1) {
        this.codNivel1 = codNivel1;
    }

    public String getCodNivel2() {
        return codNivel2;
    }

    public void setCodNivel2(String codNivel2) {
        this.codNivel2 = codNivel2;
    }

    public String getCodNivel3() {
        return codNivel3;
    }

    public void setCodNivel3(String codNivel3) {
        this.codNivel3 = codNivel3;
    }

    public String getCodNivel4() {
        return codNivel4;
    }

    public void setCodNivel4(String codNivel4) {
        this.codNivel4 = codNivel4;
    }

    public String getCodNivel5() {
        return codNivel5;
    }

    public void setCodNivel5(String codNivel5) {
        this.codNivel5 = codNivel5;
    }


    public String getCodEstablecimiento() {
        return codEstablecimiento;
    }

    public void setCodEstablecimiento(String codEstablecimiento) {
        this.codEstablecimiento = codEstablecimiento;
    }

    public String getNomEstablecimiento() {
        return nomEstablecimiento;
    }

    public void setNomEstablecimiento(String nomEstablecimiento) {
        this.nomEstablecimiento = nomEstablecimiento;
    }

    public String getCodSede() {
        return codSede;
    }

    public void setCodSede(String codSede) {
        this.codSede = codSede;
    }

    public String getNomSede() {
        return nomSede;
    }

    public void setNomSede(String nomSede) {
        this.nomSede = nomSede;
    }

    public String getCodPredio() {
        return codPredio;
    }

    public void setCodPredio(String codPredio) {
        this.codPredio = codPredio;
    }

    public String getNomPredio() {
        return nomPredio;
    }

    public void setNomPredio(String nomPredio) {
        this.nomPredio = nomPredio;
    }

    public String getDirPredio() {
        return dirPredio;
    }

    public void setDirPredio(String dirPredio) {
        this.dirPredio = dirPredio;
    }

    public String getPropiedadPredio() {
        return propiedadPredio;
    }

    public void setPropiedadPredio(String propiedadPredio) {
        this.propiedadPredio = propiedadPredio;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getClima() {
        return clima;
    }

    public void setClima(String clima) {
        this.clima = clima;
    }

    public Double getM2RequeridosTotal() {
        return m2RequeridosTotal;
    }

    public void setM2RequeridosTotal(Double m2RequeridosTotal) {
        this.m2RequeridosTotal = m2RequeridosTotal;
    }

    public Double getM2DisponiblesTotal() {
        return m2DisponiblesTotal;
    }

    public void setM2DisponiblesTotal(Double m2DisponiblesTotal) {
        this.m2DisponiblesTotal = m2DisponiblesTotal;
    }

    public Double getM2DeficitTotal() {
        return m2DeficitTotal;
    }

    public void setM2DeficitTotal(Double m2DeficitTotal) {
        this.m2DeficitTotal = m2DeficitTotal;
    }

    public Double getIdoneidadAula() {
        return idoneidadAula;
    }

    public void setIdoneidadAula(Double idoneidadAula) {
        this.idoneidadAula = idoneidadAula;
    }

    public Double getM2RequeridosRealesAula() {
        return m2RequeridosRealesAula;
    }

    public void setM2RequeridosRealesAula(Double m2RequeridosRealesAula) {
        this.m2RequeridosRealesAula = m2RequeridosRealesAula;
    }

    public Double getM2RequeridosProyectadosAula() {
        return m2RequeridosProyectadosAula;
    }

    public void setM2RequeridosProyectadosAula(Double m2RequeridosProyectadosAula) {
        this.m2RequeridosProyectadosAula = m2RequeridosProyectadosAula;
    }

    public Long getUnidadesAula() {
        return unidadesAula;
    }

    public void setUnidadesAula(Long unidadesAula) {
        this.unidadesAula = unidadesAula;
    }

    public Double getM2DisponiblesAula() {
        return m2DisponiblesAula;
    }

    public void setM2DisponiblesAula(Double m2DisponiblesAula) {
        this.m2DisponiblesAula = m2DisponiblesAula;
    }

    public Double getM2CostosAula() {
        return m2CostosAula;
    }

    public void setM2CostosAula(Double m2CostosAula) {
        this.m2CostosAula = m2CostosAula;
    }

    public Double getIdoneidadSanitario() {
        return idoneidadSanitario;
    }

    public void setIdoneidadSanitario(Double idoneidadSanitario) {
        this.idoneidadSanitario = idoneidadSanitario;
    }

    public Double getM2RequeridosRealesSanitario() {
        return m2RequeridosRealesSanitario;
    }

    public void setM2RequeridosRealesSanitario(Double m2RequeridosRealesSanitario) {
        this.m2RequeridosRealesSanitario = m2RequeridosRealesSanitario;
    }

    public Double getM2RequeridosProyectadosSanitario() {
        return m2RequeridosProyectadosSanitario;
    }

    public void setM2RequeridosProyectadosSanitario(Double m2RequeridosProyectadosSanitario) {
        this.m2RequeridosProyectadosSanitario = m2RequeridosProyectadosSanitario;
    }

    public Long getUnidadesSanitario() {
        return unidadesSanitario;
    }

    public void setUnidadesSanitario(Long unidadesSanitario) {
        this.unidadesSanitario = unidadesSanitario;
    }

    public Double getM2DisponiblesSanitario() {
        return m2DisponiblesSanitario;
    }

    public void setM2DisponiblesSanitario(Double m2DisponiblesSanitario) {
        this.m2DisponiblesSanitario = m2DisponiblesSanitario;
    }

    public Double getM2CostosSanitario() {
        return m2CostosSanitario;
    }

    public void setM2CostosSanitario(Double m2CostosSanitario) {
        this.m2CostosSanitario = m2CostosSanitario;
    }

    public Double getM2RequeridosCirculacion() {
        return m2RequeridosCirculacion;
    }

    public void setM2RequeridosCirculacion(Double m2RequeridosCirculacion) {
        this.m2RequeridosCirculacion = m2RequeridosCirculacion;
    }

    public Double getM2DisponiblesCirculacion() {
        return m2DisponiblesCirculacion;
    }

    public void setM2DisponiblesCirculacion(Double m2DisponiblesCirculacion) {
        this.m2DisponiblesCirculacion = m2DisponiblesCirculacion;
    }

    public Double getM2CostosCirculacion() {
        return m2CostosCirculacion;
    }

    public void setM2CostosCirculacion(Double m2CostosCirculacion) {
        this.m2CostosCirculacion = m2CostosCirculacion;
    }

    public Double getCostoM2Disponibles() {
        return costoM2Disponibles;
    }

    public void setCostoM2Disponibles(Double costoM2Disponibles) {
        this.costoM2Disponibles = costoM2Disponibles;
    }

    public Double getM2RequeridoNuevoLote() {
        return m2RequeridoNuevoLote;
    }

    public void setM2RequeridoNuevoLote(Double m2RequeridoNuevoLote) {
        this.m2RequeridoNuevoLote = m2RequeridoNuevoLote;
    }

    public Double getCostoM2Lote() {
        return costoM2Lote;
    }

    public void setCostoM2Lote(Double costoM2Lote) {
        this.costoM2Lote = costoM2Lote;
    }

    public Double getFaltanteAula() {
        return faltanteAula;
    }

    public void setFaltanteAula(Double faltanteAula) {
        this.faltanteAula = faltanteAula;
    }

    public Double getCostoFaltanteAula() {
        return costoFaltanteAula;
    }

    public void setCostoFaltanteAula(Double costoFaltanteAula) {
        this.costoFaltanteAula = costoFaltanteAula;
    }

    public Double getFaltanteSanitario() {
        return faltanteSanitario;
    }

    public void setFaltanteSanitario(Double faltanteSanitario) {
        this.faltanteSanitario = faltanteSanitario;
    }

    public Double getCostoFaltanteSanitario() {
        return costoFaltanteSanitario;
    }

    public void setCostoFaltanteSanitario(Double costoFaltanteSanitario) {
        this.costoFaltanteSanitario = costoFaltanteSanitario;
    }

    public Double getFaltanteCirculacion() {
        return faltanteCirculacion;
    }

    public void setFaltanteCirculacion(Double faltanteCirculacion) {
        this.faltanteCirculacion = faltanteCirculacion;
    }

    public Double getCostoFaltanteCirculacion() {
        return costoFaltanteCirculacion;
    }

    public void setCostoFaltanteCirculacion(Double costoFaltanteCirculacion) {
        this.costoFaltanteCirculacion = costoFaltanteCirculacion;
    }

    public Double getCostoM2NuevaConstruccion() {
        return costoM2NuevaConstruccion;
    }

    public void setCostoM2NuevaConstruccion(Double costoM2NuevaConstruccion) {
        this.costoM2NuevaConstruccion = costoM2NuevaConstruccion;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }
}
