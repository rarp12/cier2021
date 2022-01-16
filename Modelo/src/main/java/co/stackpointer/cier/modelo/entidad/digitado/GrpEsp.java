/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.entidad.digitado;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author rjay1
 */
@XmlRootElement
public class GrpEsp implements Serializable {

    private static final long serialVersionUID = 1L;
    @XmlElement(name = "cod_est")
    private String codigoEstablecimiento;
    @XmlElement(name = "nom_est")
    private String nombreEstablecimiento;
    @XmlElement(name = "cod_pre")
    private String codigoPredio;
    @XmlElement(name = "nom_pre")
    private String nombrePredio;
    @XmlElement(name = "cod_sed")
    private String codigoSede;
    @XmlElement(name = "nom_sed")
    private String nombreSede;
    @XmlElement(name = "cod_edi")
    private String codigoEdificio;
    @XmlElement(name = "sum_are")
    private double sumArea;
    @XmlElement(name = "sum_vol")
    private double sumVolumen;
    @XmlElement(name = "sum_usu")
    private double sumUsuario;

    public GrpEsp() {
    }

    public GrpEsp(String codigoEstablecimiento, String nombreEstablecimiento, String codigoPredio, String nombrePredio, String codigoSede, String nombreSede, String codigoEdificio, double sumArea, double sumVolumen, double sumUsuario) {
        this.codigoEstablecimiento = codigoEstablecimiento;
        this.nombreEstablecimiento = nombreEstablecimiento;
        this.codigoPredio = codigoPredio;
        this.nombrePredio = nombrePredio;
        this.codigoSede = codigoSede;
        this.nombreSede = nombreSede;
        this.codigoEdificio = codigoEdificio;
        this.sumArea = sumArea;
        this.sumVolumen = sumVolumen;
        this.sumUsuario = sumUsuario;
    }

    public String getCodigoEstablecimiento() {
        return codigoEstablecimiento;
    }

    public void setCodigoEstablecimiento(String codigoEstablecimiento) {
        this.codigoEstablecimiento = codigoEstablecimiento;
    }

    public String getNombreEstablecimiento() {
        return nombreEstablecimiento;
    }

    public void setNombreEstablecimiento(String nombreEstablecimiento) {
        this.nombreEstablecimiento = nombreEstablecimiento;
    }

    public String getCodigoPredio() {
        return codigoPredio;
    }

    public void setCodigoPredio(String codigoPredio) {
        this.codigoPredio = codigoPredio;
    }

    public String getNombrePredio() {
        return nombrePredio;
    }

    public void setNombrePredio(String nombrePredio) {
        this.nombrePredio = nombrePredio;
    }

    public String getCodigoSede() {
        return codigoSede;
    }

    public void setCodigoSede(String codigoSede) {
        this.codigoSede = codigoSede;
    }

    public String getNombreSede() {
        return nombreSede;
    }

    public void setNombreSede(String nombreSede) {
        this.nombreSede = nombreSede;
    }

    public String getCodigoEdificio() {
        return codigoEdificio;
    }

    public void setCodigoEdificio(String codigoEdificio) {
        this.codigoEdificio = codigoEdificio;
    }

    public double getSumArea() {
        return sumArea;
    }

    public void setSumArea(double sumArea) {
        this.sumArea = sumArea;
    }

    public double getSumVolumen() {
        return sumVolumen;
    }

    public void setSumVolumen(double sumVolumen) {
        this.sumVolumen = sumVolumen;
    }

    public double getSumUsuario() {
        return sumUsuario;
    }

    public void setSumUsuario(double sumUsuario) {
        this.sumUsuario = sumUsuario;
    }
}