/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.entidad.digitado;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.TenantDiscriminatorColumn;

/**
 *
 * @author rjay1
 */
@Entity
@Table(name = "dig_grp_rsp_fact")
@Multitenant
@TenantDiscriminatorColumn(name = "id_tnt", contextProperty = "TENANT_ID", discriminatorType = DiscriminatorType.INTEGER)
@NamedQueries({
    @NamedQuery(name = "GrupoEspacio.findAll", query = "SELECT i FROM GrupoEspacio i")
})
public class GrupoEspacio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "niv_000")
    private Integer idNivel0;
    @Column(name = "niv_001")
    private Integer idNivel1;
    @Column(name = "niv_002")
    private Integer idNivel2;
    @Column(name = "niv_003")
    private Integer idNivel3;
    @Column(name = "niv_004")
    private Integer idNivel4;
    @Column(name = "niv_005")
    private Integer idNivel5;
    @Column(name = "gen_001")
    private String boletaCensal;
    @Column(name = "gen_002")
    private String estadoBoleta;
    @Column(name = "gen_003")
    private String codigoEstablecimiento;
    @Column(name = "gen_004")
    private String nombreEstablecimiento;
    @Column(name = "gen_005")
    private String codigoPredio;
    @Column(name = "gen_006")
    private String codigoSede;
    @Column(name = "gen_007")
    private String nombreSede;
    @Column(name = "gen_008")
    private String version;
    @Column(name = "gen_009")
    private String numeroPiso;
    @Column(name = "gen_010")
    private String codigoEdificio;
    @Column(name = "gen_011")
    private String nombrePredio;
    @Column(name = "bas_001")
    private String bas001;
    @Column(name = "bas_002")
    private String bas002;
    @Column(name = "bas_003")
    private String bas003;
    @Column(name = "bas_004")
    private String bas004;
    @Column(name = "bas_005")
    private String bas005;
    @Column(name = "bas_006")
    private String bas006;
    @Column(name = "bas_007")
    private String bas007;
    @Column(name = "dim_001")
    private String dim001;
    @Column(name = "dim_002")
    private String dim002;
    @Column(name = "dim_003")
    private String dim003;
    @Column(name = "con_001")
    private String con001;
    @Column(name = "con_002")
    private String con002;
    @Column(name = "con_003")
    private String con003;
    @Column(name = "con_004")
    private String con004;
    @Column(name = "con_005")
    private String con005;
    @Column(name = "con_006")
    private String con006;
    @Column(name = "con_007")
    private String con007;
    @Column(name = "con_008")
    private String con008;
    @Column(name = "con_009")
    private String con009;
    @Column(name = "con_010")
    private String con010;
    @Column(name = "con_011")
    private String con011;
    @Column(name = "con_012")
    private String con012;
    @Column(name = "mat_001")
    private String mat001;
    @Column(name = "mat_002")
    private String mat002;
    @Column(name = "mat_003")
    private String mat003;
    @Column(name = "mat_004")
    private String mat004;
    @Column(name = "mat_005")
    private String mat005;
    @Column(name = "mat_006")
    private String mat006;
    @Column(name = "mat_007")
    private String mat007;
    @Column(name = "mat_008")
    private String mat008;
    @Column(name = "mat_009")
    private String mat009;
    @Column(name = "mat_010")
    private String mat010;
    @Column(name = "mat_011")
    private String mat011;
    @Column(name = "mat_012")
    private String mat012;
    @Column(name = "mat_013")
    private String mat013;
    @Column(name = "elm_001")
    private String elm001;
    @Column(name = "elm_002")
    private String elm002;
    @Column(name = "elm_003")
    private String elm003;
    @Column(name = "elm_004")
    private String elm004;
    @Column(name = "elm_005")
    private String elm005;
    @Column(name = "elm_006")
    private String elm006;
    @Column(name = "prp_001")
    private String prp001;
    @Column(name = "prp_002")
    private String prp002;
    @Column(name = "prp_003")
    private String prp003;
    @Column(name = "prp_004")
    private String prp004;
    @Column(name = "prp_005")
    private String prp005;
    @Column(name = "prp_006")
    private String prp006;
    @Column(name = "prp_007")
    private String prp007;
    @Column(name = "prp_008")
    private String prp008;
    @Column(name = "prp_009")
    private String prp009;
    @Column(name = "snt_001")
    private String snt001;
    @Column(name = "snt_002")
    private String snt002;
    @Column(name = "snt_003")
    private String snt003;
    @Column(name = "snt_004")
    private String snt004;
    @Column(name = "snt_005")
    private String snt005;
    @Column(name = "snt_006")
    private String snt006;
    @Column(name = "snt_007")
    private String snt007;
    @Column(name = "snt_008")
    private String snt008;
    @Column(name = "snt_009")
    private String snt009;
    @Column(name = "snt_010")
    private String snt010;
    @Column(name = "snt_011")
    private String snt011;
    @Column(name = "snt_012")
    private String snt012;
    @Column(name = "snt_013")
    private String snt013;
    @Column(name = "snt_014")
    private String snt014;
    @Column(name = "snt_015")
    private String snt015;
    @Column(name = "snt_016")
    private String snt016;
    @Column(name = "snt_017")
    private String snt017;
    @Column(name = "snt_018")
    private String snt018;
    @Column(name = "snt_019")
    private String snt019;
    @Column(name = "snt_020")
    private String snt020;
    @Column(name = "snt_021")
    private String snt021;
    @Column(name = "snt_022")
    private String snt022;
    @Column(name = "snt_023")
    private String snt023;
    @Column(name = "snt_024")
    private String snt024;
    @Column(name = "snt_025")
    private String snt025;
    @Column(name = "snt_026")
    private String snt026;
    @Column(name = "snt_027")
    private String snt027;
    @Column(name = "snt_028")
    private String snt028;
    @Column(name = "snt_029")
    private String snt029;
    @Column(name = "snt_030")
    private String snt030;
    @Column(name = "rec_001")
    private String rec001;
    @Column(name = "rec_002")
    private String rec002;
    @Column(name = "rec_003")
    private String rec003;
    @Column(name = "rec_004")
    private String rec004;
    @Column(name = "rec_005")
    private String rec005;
    @Column(name = "rec_006")
    private String rec006;
    @Column(name = "rec_007")
    private String rec007;
    @Column(name = "rec_008")
    private String rec008;
    @Column(name = "rec_009")
    private String rec009;
    @Column(name = "rec_010")
    private String rec010;
    @Column(name = "rec_011")
    private String rec011;
    @Column(name = "rec_012")
    private String rec012;
    @Column(name = "rec_013")
    private String rec013;
    @Column(name = "rec_014")
    private String rec014;
    @Column(name = "rec_015")
    private String rec015;
    @Transient
    private double sumArea;
    @Transient
    private double sumVolumen;
    @Transient
    private double sumUsuario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdNivel0() {
        return idNivel0;
    }

    public void setIdNivel0(Integer idNivel0) {
        this.idNivel0 = idNivel0;
    }

    public Integer getIdNivel1() {
        return idNivel1;
    }

    public void setIdNivel1(Integer idNivel1) {
        this.idNivel1 = idNivel1;
    }

    public Integer getIdNivel2() {
        return idNivel2;
    }

    public void setIdNivel2(Integer idNivel2) {
        this.idNivel2 = idNivel2;
    }

    public Integer getIdNivel3() {
        return idNivel3;
    }

    public void setIdNivel3(Integer idNivel3) {
        this.idNivel3 = idNivel3;
    }

    public Integer getIdNivel4() {
        return idNivel4;
    }

    public void setIdNivel4(Integer idNivel4) {
        this.idNivel4 = idNivel4;
    }

    public Integer getIdNivel5() {
        return idNivel5;
    }

    public void setIdNivel5(Integer idNivel5) {
        this.idNivel5 = idNivel5;
    }

    public String getBoletaCensal() {
        return boletaCensal;
    }

    public void setBoletaCensal(String boletaCensal) {
        this.boletaCensal = boletaCensal;
    }

    public String getEstadoBoleta() {
        return estadoBoleta;
    }

    public void setEstadoBoleta(String estadoBoleta) {
        this.estadoBoleta = estadoBoleta;
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

    public String getCodigoSede() {
        return codigoSede;
    }

    public void setCodigoSede(String codigoSede) {
        this.codigoSede = codigoSede;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getNumeroPiso() {
        return numeroPiso;
    }

    public void setNumeroPiso(String numeroPiso) {
        this.numeroPiso = numeroPiso;
    }

    public String getCodigoEdificio() {
        return codigoEdificio;
    }

    public void setCodigoEdificio(String codigoEdificio) {
        this.codigoEdificio = codigoEdificio;
    }

    public String getNombreSede() {
        return nombreSede;
    }

    public void setNombreSede(String nombreSede) {
        this.nombreSede = nombreSede;
    }

    public String getBas001() {
        return bas001;
    }

    public void setBas001(String bas001) {
        this.bas001 = bas001;
    }

    public String getBas002() {
        return bas002;
    }

    public void setBas002(String bas002) {
        this.bas002 = bas002;
    }

    public String getBas003() {
        return bas003;
    }

    public void setBas003(String bas003) {
        this.bas003 = bas003;
    }

    public String getBas004() {
        return bas004;
    }

    public void setBas004(String bas004) {
        this.bas004 = bas004;
    }

    public String getBas005() {
        return bas005;
    }

    public void setBas005(String bas005) {
        this.bas005 = bas005;
    }

    public String getBas006() {
        return bas006;
    }

    public void setBas006(String bas006) {
        this.bas006 = bas006;
    }

    public String getBas007() {
        return bas007;
    }

    public void setBas007(String bas007) {
        this.bas007 = bas007;
    }

    public String getDim001() {
        return dim001;
    }

    public void setDim001(String dim001) {
        this.dim001 = dim001;
    }

    public String getDim002() {
        return dim002;
    }

    public void setDim002(String dim002) {
        this.dim002 = dim002;
    }

    public String getDim003() {
        return dim003;
    }

    public void setDim003(String dim003) {
        this.dim003 = dim003;
    }

    public String getCon001() {
        return con001;
    }

    public void setCon001(String con001) {
        this.con001 = con001;
    }

    public String getCon002() {
        return con002;
    }

    public void setCon002(String con002) {
        this.con002 = con002;
    }

    public String getCon003() {
        return con003;
    }

    public void setCon003(String con003) {
        this.con003 = con003;
    }

    public String getCon004() {
        return con004;
    }

    public void setCon004(String con004) {
        this.con004 = con004;
    }

    public String getCon005() {
        return con005;
    }

    public void setCon005(String con005) {
        this.con005 = con005;
    }

    public String getCon006() {
        return con006;
    }

    public void setCon006(String con006) {
        this.con006 = con006;
    }

    public String getCon007() {
        return con007;
    }

    public void setCon007(String con007) {
        this.con007 = con007;
    }

    public String getCon008() {
        return con008;
    }

    public void setCon008(String con008) {
        this.con008 = con008;
    }

    public String getCon009() {
        return con009;
    }

    public void setCon009(String con009) {
        this.con009 = con009;
    }

    public String getCon010() {
        return con010;
    }

    public void setCon010(String con010) {
        this.con010 = con010;
    }

    public String getCon011() {
        return con011;
    }

    public void setCon011(String con011) {
        this.con011 = con011;
    }

    public String getCon012() {
        return con012;
    }

    public void setCon012(String con012) {
        this.con012 = con012;
    }

    public String getMat001() {
        return mat001;
    }

    public void setMat001(String mat001) {
        this.mat001 = mat001;
    }

    public String getMat002() {
        return mat002;
    }

    public void setMat002(String mat002) {
        this.mat002 = mat002;
    }

    public String getMat003() {
        return mat003;
    }

    public void setMat003(String mat003) {
        this.mat003 = mat003;
    }

    public String getMat004() {
        return mat004;
    }

    public void setMat004(String mat004) {
        this.mat004 = mat004;
    }

    public String getMat005() {
        return mat005;
    }

    public void setMat005(String mat005) {
        this.mat005 = mat005;
    }

    public String getMat006() {
        return mat006;
    }

    public void setMat006(String mat006) {
        this.mat006 = mat006;
    }

    public String getMat007() {
        return mat007;
    }

    public void setMat007(String mat007) {
        this.mat007 = mat007;
    }

    public String getMat008() {
        return mat008;
    }

    public void setMat008(String mat008) {
        this.mat008 = mat008;
    }

    public String getMat009() {
        return mat009;
    }

    public void setMat009(String mat009) {
        this.mat009 = mat009;
    }

    public String getMat010() {
        return mat010;
    }

    public void setMat010(String mat010) {
        this.mat010 = mat010;
    }

    public String getMat011() {
        return mat011;
    }

    public void setMat011(String mat011) {
        this.mat011 = mat011;
    }

    public String getMat012() {
        return mat012;
    }

    public void setMat012(String mat012) {
        this.mat012 = mat012;
    }

    public String getMat013() {
        return mat013;
    }

    public void setMat013(String mat013) {
        this.mat013 = mat013;
    }

    public String getElm001() {
        return elm001;
    }

    public void setElm001(String elm001) {
        this.elm001 = elm001;
    }

    public String getElm002() {
        return elm002;
    }

    public void setElm002(String elm002) {
        this.elm002 = elm002;
    }

    public String getElm003() {
        return elm003;
    }

    public void setElm003(String elm003) {
        this.elm003 = elm003;
    }

    public String getElm004() {
        return elm004;
    }

    public void setElm004(String elm004) {
        this.elm004 = elm004;
    }

    public String getElm005() {
        return elm005;
    }

    public void setElm005(String elm005) {
        this.elm005 = elm005;
    }

    public String getElm006() {
        return elm006;
    }

    public void setElm006(String elm006) {
        this.elm006 = elm006;
    }

    public String getPrp001() {
        return prp001;
    }

    public void setPrp001(String prp001) {
        this.prp001 = prp001;
    }

    public String getPrp002() {
        return prp002;
    }

    public void setPrp002(String prp002) {
        this.prp002 = prp002;
    }

    public String getPrp003() {
        return prp003;
    }

    public void setPrp003(String prp003) {
        this.prp003 = prp003;
    }

    public String getPrp004() {
        return prp004;
    }

    public void setPrp004(String prp004) {
        this.prp004 = prp004;
    }

    public String getPrp005() {
        return prp005;
    }

    public void setPrp005(String prp005) {
        this.prp005 = prp005;
    }

    public String getPrp006() {
        return prp006;
    }

    public void setPrp006(String prp006) {
        this.prp006 = prp006;
    }

    public String getPrp007() {
        return prp007;
    }

    public void setPrp007(String prp007) {
        this.prp007 = prp007;
    }

    public String getPrp008() {
        return prp008;
    }

    public void setPrp008(String prp008) {
        this.prp008 = prp008;
    }

    public String getPrp009() {
        return prp009;
    }

    public void setPrp009(String prp009) {
        this.prp009 = prp009;
    }

    public String getSnt001() {
        return snt001;
    }

    public void setSnt001(String snt001) {
        this.snt001 = snt001;
    }

    public String getSnt002() {
        return snt002;
    }

    public void setSnt002(String snt002) {
        this.snt002 = snt002;
    }

    public String getSnt003() {
        return snt003;
    }

    public void setSnt003(String snt003) {
        this.snt003 = snt003;
    }

    public String getSnt004() {
        return snt004;
    }

    public void setSnt004(String snt004) {
        this.snt004 = snt004;
    }

    public String getSnt005() {
        return snt005;
    }

    public void setSnt005(String snt005) {
        this.snt005 = snt005;
    }

    public String getSnt006() {
        return snt006;
    }

    public void setSnt006(String snt006) {
        this.snt006 = snt006;
    }

    public String getSnt007() {
        return snt007;
    }

    public void setSnt007(String snt007) {
        this.snt007 = snt007;
    }

    public String getSnt008() {
        return snt008;
    }

    public void setSnt008(String snt008) {
        this.snt008 = snt008;
    }

    public String getSnt009() {
        return snt009;
    }

    public void setSnt009(String snt009) {
        this.snt009 = snt009;
    }

    public String getSnt010() {
        return snt010;
    }

    public void setSnt010(String snt010) {
        this.snt010 = snt010;
    }

    public String getSnt011() {
        return snt011;
    }

    public void setSnt011(String snt011) {
        this.snt011 = snt011;
    }

    public String getSnt012() {
        return snt012;
    }

    public void setSnt012(String snt012) {
        this.snt012 = snt012;
    }

    public String getSnt013() {
        return snt013;
    }

    public void setSnt013(String snt013) {
        this.snt013 = snt013;
    }

    public String getSnt014() {
        return snt014;
    }

    public void setSnt014(String snt014) {
        this.snt014 = snt014;
    }

    public String getSnt015() {
        return snt015;
    }

    public void setSnt015(String snt015) {
        this.snt015 = snt015;
    }

    public String getSnt016() {
        return snt016;
    }

    public void setSnt016(String snt016) {
        this.snt016 = snt016;
    }

    public String getSnt017() {
        return snt017;
    }

    public void setSnt017(String snt017) {
        this.snt017 = snt017;
    }

    public String getSnt018() {
        return snt018;
    }

    public void setSnt018(String snt018) {
        this.snt018 = snt018;
    }

    public String getSnt019() {
        return snt019;
    }

    public void setSnt019(String snt019) {
        this.snt019 = snt019;
    }

    public String getSnt020() {
        return snt020;
    }

    public void setSnt020(String snt020) {
        this.snt020 = snt020;
    }

    public String getSnt021() {
        return snt021;
    }

    public void setSnt021(String snt021) {
        this.snt021 = snt021;
    }

    public String getSnt022() {
        return snt022;
    }

    public void setSnt022(String snt022) {
        this.snt022 = snt022;
    }

    public String getSnt023() {
        return snt023;
    }

    public void setSnt023(String snt023) {
        this.snt023 = snt023;
    }

    public String getSnt024() {
        return snt024;
    }

    public void setSnt024(String snt024) {
        this.snt024 = snt024;
    }

    public String getSnt025() {
        return snt025;
    }

    public void setSnt025(String snt025) {
        this.snt025 = snt025;
    }

    public String getSnt026() {
        return snt026;
    }

    public void setSnt026(String snt026) {
        this.snt026 = snt026;
    }

    public String getSnt027() {
        return snt027;
    }

    public void setSnt027(String snt027) {
        this.snt027 = snt027;
    }

    public String getSnt028() {
        return snt028;
    }

    public void setSnt028(String snt028) {
        this.snt028 = snt028;
    }

    public String getSnt029() {
        return snt029;
    }

    public void setSnt029(String snt029) {
        this.snt029 = snt029;
    }

    public String getSnt030() {
        return snt030;
    }

    public void setSnt030(String snt030) {
        this.snt030 = snt030;
    }

    public String getRec001() {
        return rec001;
    }

    public void setRec001(String rec001) {
        this.rec001 = rec001;
    }

    public String getRec002() {
        return rec002;
    }

    public void setRec002(String rec002) {
        this.rec002 = rec002;
    }

    public String getRec003() {
        return rec003;
    }

    public void setRec003(String rec003) {
        this.rec003 = rec003;
    }

    public String getRec004() {
        return rec004;
    }

    public void setRec004(String rec004) {
        this.rec004 = rec004;
    }

    public String getRec005() {
        return rec005;
    }

    public void setRec005(String rec005) {
        this.rec005 = rec005;
    }

    public String getRec006() {
        return rec006;
    }

    public void setRec006(String rec006) {
        this.rec006 = rec006;
    }

    public String getRec007() {
        return rec007;
    }

    public void setRec007(String rec007) {
        this.rec007 = rec007;
    }

    public String getRec008() {
        return rec008;
    }

    public void setRec008(String rec008) {
        this.rec008 = rec008;
    }

    public String getRec009() {
        return rec009;
    }

    public void setRec009(String rec009) {
        this.rec009 = rec009;
    }

    public String getRec010() {
        return rec010;
    }

    public void setRec010(String rec010) {
        this.rec010 = rec010;
    }

    public String getRec011() {
        return rec011;
    }

    public void setRec011(String rec011) {
        this.rec011 = rec011;
    }

    public String getRec012() {
        return rec012;
    }

    public void setRec012(String rec012) {
        this.rec012 = rec012;
    }

    public String getRec013() {
        return rec013;
    }

    public void setRec013(String rec013) {
        this.rec013 = rec013;
    }

    public String getRec014() {
        return rec014;
    }

    public void setRec014(String rec014) {
        this.rec014 = rec014;
    }

    public String getRec015() {
        return rec015;
    }

    public void setRec015(String rec015) {
        this.rec015 = rec015;
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