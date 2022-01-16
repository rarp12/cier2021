/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.consulta.ambito.migracion;

/**
 *
 * @author DEPAZAN
 */
public class Registro {

    private String nivel0;
    private String nivel1;
    private String nivel2;
    private String nivel3;
    private String nivel4;
    private String nivel5;
    private String codEstablecimiento;
    private String nomEstablecimiento;
    private String codSede;
    private String nomSede;
    private String codPredio;
    private String nomPredio;
    private String dirPredio;
    private String zona;
    private String sector;
    private String clima;
    private String etnias;
    //generalidades        
    private Long numJornadasEstudiantes;
    private Long totalEstudiantes;
    private Double areaConstruccion;
    private Double areaRealPredio;
    //propiedad
    private String propiedadPredio;
    private String tipoPropietario;
    private String verificacionPropiedad;
    //Riesgos
    private String tipoRN1;
    private String condRN1;
    private String tipoRN2;
    private String condRN2;
    private String tipoRN3;
    private String condRN3;
    private Long poblacionAfectadaRN;
    private String tipoRA1;
    private String condRA1;
    private String tipoRA2;
    private String condRA2;
    private String tipoRA3;
    private String condRA3;
    private Long poblacionAfectadaRA;
    //oferta
    private Double posConstruccionPrimerPiso;
    private Double posConstruccionAltura;
    private Double posConstruccionTotal;
    //Control y vigilancia
    private String tipoCerramiento;
    private String condicionCerramiento;
    private String condicionOrdenPublico;
    private String vulnerabilidad;
    private Long totalEdificios;
    //seguridad
    private String estadoEstructura;
    private String analisisRutaEvacuacion;
    private String reactivoIncendios;
    private String senalizacionEvacuacion;
    //Accesibilidad
    private String tipoAcceso1;
    private String condiconAcceso1;
    private String tipoAcceso2;
    private String condiconAcceso2;
    private String tipoAcceso3;
    private String condiconAcceso3;
    //Confort
    private Long espaciosAislTermPiso;
    private Long espaciosAislTermTecho;
    private Long espaciosAislTermPared;
    private Long espaciosAislTermVentana;
    private Long totalInodoros;
    private Long totalDuchas;
    private Long totalLavamano;
    private Long totalOrinales;
    
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

    public String getEtnias() {
        return etnias;
    }

    public void setEtnias(String etnias) {
        this.etnias = etnias;
    }

    public Long getTotalEstudiantes() {
        return totalEstudiantes;
    }

    public void setTotalEstudiantes(Long totalEstudiantes) {
        this.totalEstudiantes = totalEstudiantes;
    }

    public Long getNumJornadasEstudiantes() {
        return numJornadasEstudiantes;
    }

    public void setNumJornadasEstudiantes(Long numJornadasEstudiantes) {
        this.numJornadasEstudiantes = numJornadasEstudiantes;
    }

    public Double getAreaConstruccion() {
        return areaConstruccion;
    }

    public void setAreaConstruccion(Double areaConstruccion) {
        this.areaConstruccion = areaConstruccion;
    }

    public Double getAreaRealPredio() {
        return areaRealPredio;
    }

    public void setAreaRealPredio(Double areaRealPredio) {
        this.areaRealPredio = areaRealPredio;
    }

    public String getPropiedadPredio() {
        return propiedadPredio;
    }

    public void setPropiedadPredio(String propiedadPredio) {
        this.propiedadPredio = propiedadPredio;
    }

    public String getTipoPropietario() {
        return tipoPropietario;
    }

    public void setTipoPropietario(String tipoPropietario) {
        this.tipoPropietario = tipoPropietario;
    }

    public String getVerificacionPropiedad() {
        return verificacionPropiedad;
    }

    public void setVerificacionPropiedad(String verificacionPropiedad) {
        this.verificacionPropiedad = verificacionPropiedad;
    }

    public String getTipoRN1() {
        return tipoRN1;
    }

    public void setTipoRN1(String tipoRN1) {
        this.tipoRN1 = tipoRN1;
    }

    public String getCondRN1() {
        return condRN1;
    }

    public void setCondRN1(String condRN1) {
        this.condRN1 = condRN1;
    }

    public String getTipoRN2() {
        return tipoRN2;
    }

    public void setTipoRN2(String tipoRN2) {
        this.tipoRN2 = tipoRN2;
    }

    public String getCondRN2() {
        return condRN2;
    }

    public void setCondRN2(String condRN2) {
        this.condRN2 = condRN2;
    }

    public String getTipoRN3() {
        return tipoRN3;
    }

    public void setTipoRN3(String tipoRN3) {
        this.tipoRN3 = tipoRN3;
    }

    public String getCondRN3() {
        return condRN3;
    }

    public void setCondRN3(String condRN3) {
        this.condRN3 = condRN3;
    }

    public Long getPoblacionAfectadaRN() {
        return poblacionAfectadaRN;
    }

    public void setPoblacionAfectadaRN(Long poblacionAfectada) {
        this.poblacionAfectadaRN = poblacionAfectada;
    }

    public String getTipoRA1() {
        return tipoRA1;
    }

    public void setTipoRA1(String tipoRA1) {
        this.tipoRA1 = tipoRA1;
    }

    public String getCondRA1() {
        return condRA1;
    }

    public void setCondRA1(String condRA1) {
        this.condRA1 = condRA1;
    }

    public String getTipoRA2() {
        return tipoRA2;
    }

    public void setTipoRA2(String tipoRA2) {
        this.tipoRA2 = tipoRA2;
    }

    public String getCondRA2() {
        return condRA2;
    }

    public void setCondRA2(String condRA2) {
        this.condRA2 = condRA2;
    }

    public String getTipoRA3() {
        return tipoRA3;
    }

    public void setTipoRA3(String tipoRA3) {
        this.tipoRA3 = tipoRA3;
    }

    public String getCondRA3() {
        return condRA3;
    }

    public void setCondRA3(String condRA3) {
        this.condRA3 = condRA3;
    }

    public Long getPoblacionAfectadaRA() {
        return poblacionAfectadaRA;
    }

    public void setPoblacionAfectadaRA(Long poblacionAfectadaRA) {
        this.poblacionAfectadaRA = poblacionAfectadaRA;
    }

    public Double getPosConstruccionPrimerPiso() {
        return posConstruccionPrimerPiso;
    }

    public void setPosConstruccionPrimerPiso(Double posConstruccionPrimerPiso) {
        this.posConstruccionPrimerPiso = posConstruccionPrimerPiso;
    }

    public Double getPosConstruccionAltura() {
        return posConstruccionAltura;
    }

    public void setPosConstruccionAltura(Double posConstruccionAltura) {
        this.posConstruccionAltura = posConstruccionAltura;
    }

    public Double getPosConstruccionTotal() {
        return posConstruccionTotal;
    }

    public void setPosConstruccionTotal(Double posConstruccionTotal) {
        this.posConstruccionTotal = posConstruccionTotal;
    }

    public String getTipoCerramiento() {
        return tipoCerramiento;
    }

    public void setTipoCerramiento(String tipoCerramiento) {
        this.tipoCerramiento = tipoCerramiento;
    }

    public String getCondicionCerramiento() {
        return condicionCerramiento;
    }

    public void setCondicionCerramiento(String condicionCerramiento) {
        this.condicionCerramiento = condicionCerramiento;
    }

    public String getCondicionOrdenPublico() {
        return condicionOrdenPublico;
    }

    public void setCondicionOrdenPublico(String condicionOrdenPublico) {
        this.condicionOrdenPublico = condicionOrdenPublico;
    }

    public String getVulnerabilidad() {
        return vulnerabilidad;
    }

    public void setVulnerabilidad(String vulnerabilidad) {
        this.vulnerabilidad = vulnerabilidad;
    }

    public Long getTotalEdificios() {
        return totalEdificios;
    }

    public void setTotalEdificios(Long totalEdificios) {
        this.totalEdificios = totalEdificios;
    }

    public String getEstadoEstructura() {
        return estadoEstructura;
    }

    public void setEstadoEstructura(String estadoEstructura) {
        this.estadoEstructura = estadoEstructura;
    }

    public String getAnalisisRutaEvacuacion() {
        return analisisRutaEvacuacion;
    }

    public void setAnalisisRutaEvacuacion(String analisisRutaEvacuacion) {
        this.analisisRutaEvacuacion = analisisRutaEvacuacion;
    }

    public String getReactivoIncendios() {
        return reactivoIncendios;
    }

    public void setReactivoIncendios(String reactivoIncendios) {
        this.reactivoIncendios = reactivoIncendios;
    }

    public String getSenalizacionEvacuacion() {
        return senalizacionEvacuacion;
    }

    public void setSenalizacionEvacuacion(String senalizacionEvacuacion) {
        this.senalizacionEvacuacion = senalizacionEvacuacion;
    }

    public String getTipoAcceso1() {
        return tipoAcceso1;
    }

    public void setTipoAcceso1(String tipoAcceso1) {
        this.tipoAcceso1 = tipoAcceso1;
    }

    public String getCondiconAcceso1() {
        return condiconAcceso1;
    }

    public void setCondiconAcceso1(String condiconAcceso1) {
        this.condiconAcceso1 = condiconAcceso1;
    }

    public String getTipoAcceso2() {
        return tipoAcceso2;
    }

    public void setTipoAcceso2(String tipoAcceso2) {
        this.tipoAcceso2 = tipoAcceso2;
    }

    public String getCondiconAcceso2() {
        return condiconAcceso2;
    }

    public void setCondiconAcceso2(String condiconAcceso2) {
        this.condiconAcceso2 = condiconAcceso2;
    }

    public String getTipoAcceso3() {
        return tipoAcceso3;
    }

    public void setTipoAcceso3(String tipoAcceso3) {
        this.tipoAcceso3 = tipoAcceso3;
    }

    public String getCondiconAcceso3() {
        return condiconAcceso3;
    }

    public void setCondiconAcceso3(String condiconAcceso3) {
        this.condiconAcceso3 = condiconAcceso3;
    }

    public Long getEspaciosAislTermPiso() {
        return espaciosAislTermPiso;
    }

    public void setEspaciosAislTermPiso(Long espaciosAislTermPiso) {
        this.espaciosAislTermPiso = espaciosAislTermPiso;
    }

    public Long getEspaciosAislTermTecho() {
        return espaciosAislTermTecho;
    }

    public void setEspaciosAislTermTecho(Long espaciosAislTermTecho) {
        this.espaciosAislTermTecho = espaciosAislTermTecho;
    }

    public Long getEspaciosAislTermPared() {
        return espaciosAislTermPared;
    }

    public void setEspaciosAislTermPared(Long espaciosAislTermPared) {
        this.espaciosAislTermPared = espaciosAislTermPared;
    }

    public Long getEspaciosAislTermVentana() {
        return espaciosAislTermVentana;
    }

    public void setEspaciosAislTermVentana(Long espaciosAislTermVentana) {
        this.espaciosAislTermVentana = espaciosAislTermVentana;
    }

    public Long getTotalInodoros() {
        return totalInodoros;
    }

    public void setTotalInodoros(Long totalInodoros) {
        this.totalInodoros = totalInodoros;
    }

    public Long getTotalDuchas() {
        return totalDuchas;
    }

    public void setTotalDuchas(Long totalDuchas) {
        this.totalDuchas = totalDuchas;
    }

    public Long getTotalLavamano() {
        return totalLavamano;
    }

    public void setTotalLavamano(Long totalLavamano) {
        this.totalLavamano = totalLavamano;
    }

    public Long getTotalOrinales() {
        return totalOrinales;
    }

    public void setTotalOrinales(Long totalOrinales) {
        this.totalOrinales = totalOrinales;
    }
}
