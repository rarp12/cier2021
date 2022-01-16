/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.consulta.informeGeneral;

import co.stackpointer.cier.modelo.utilidad.UtilCadena;
import java.math.BigDecimal;

/**
 *
 * @author nruiz
 */
public class DatosDPA {
    String codigoDPA;
    String nombreDPA;
    String numEstablecimientos;
    String numSedes;
    String numPredios;
    String pre;
    String primaria;
    String secundaria;
    String basica;
    String media;
    String total;
    String superficie;
    String areaConstrucPrimerPiso;
    String areaConstrucAltura;
    String porcenConstruccion;
    String numPrediosAccesibilidadInadecuada;
    String accesibilidadPoblacionAfectada;
    String prediosProblemasLegalizacion;
    String estudiantesProblemaLegalizacion;
    String numPrediosConAgua;
    String numPrediosConEE;
    String numPrediosConGas;
    String numPrediosConReciclaje;
    String numPrediosConAlcantarillado;
    String numPrediosConPluvial;
    String numPrediosConInternet;
    String numPrediosConTelefono;
    String promedioConsumoAgua;
    String numPrediosCumplenConsumoAgua;
    String promedioConsumoEE;
    String numPrediosCumplenConsumoEE;
    String numPrediosVulnerables;
    String numPrediosEstrucCritica;
    String numPrediosRutaEvacCritica;
    String numPrediosSenalizaEvacCritica;
    String numPrediosNoRactivoIncendio;
    String numPisosCond4;
    String numPisosCond3;
    String numPisosCond2;
    String numPisosCond1;
    String numMurosCond4;
    String numMurosCond3;
    String numMurosCond2;
    String numMurosCond1;
    String numAcabadosMuroCond4;
    String numAcabadosMuroCond3;
    String numAcabadosMuroCond2;
    String numAcabadosMuroCond1;
    String numVentanasCond4;
    String numVentanasCond3;
    String numVentanasCond2;
    String numVentanasCond1;
    String numPuertasCond4;
    String numPuertasCond3;
    String numPuertasCond2;
    String numPuertasCond1;
    String numCieloRasosCond4;
    String numCieloRasosCond3;
    String numCieloRasosCond2;
    String numCieloRasosCond1;
    String areaPisosCond4;
    String areaPisosCond3;
    String areaPisosCond2;
    String areaPisosCond1;
    String areaMurosCond4;
    String areaMurosCond3;
    String areaMurosCond2;
    String areaMurosCond1;
    String areaAcabadosMuroCond4;
    String areaAcabadosMuroCond3;
    String areaAcabadosMuroCond2;
    String areaAcabadosMuroCond1;
    String areaCieloRasosCond4;
    String areaCieloRasosCond3;
    String areaCieloRasosCond2;
    String areaCieloRasosCond1;
    String murosFachadasValor1;
    String murosFachadasValor2;
    String murosFachadasValor3;
    String murosFachadasValor4;
    String acabadosFachadasValor1;
    String acabadosFachadasValor2;
    String acabadosFachadasValor3;
    String acabadosFachadasValor4;
    String cubiertasValor1;
    String cubiertasValor2;
    String cubiertasValor3;
    String cubiertasValor4;                
    String iluminacionValor1;
    String iluminacionValor2;
    String iluminacionValor3;
    String iluminacionValor4;
    String ventilacionValor1;
    String ventilacionValor2;
    String ventilacionValor3;
    String ventilacionValor4;
    String acusticaValor1;
    String acusticaValor2;
    String acusticaValor3;
    String acusticaValor4;
    String duchasValor1;
    String duchasValor2;
    String duchasValor3;
    String duchasValor4;
    String lavamanosValor1;
    String lavamanosValor2;
    String lavamanosValor3;
    String lavamanosValor4;
    String orinalesValor1;
    String orinalesValor2;
    String orinalesValor3;
    String orinalesValor4;
    String inodorosValor1;
    String inodorosValor2;
    String inodorosValor3;
    String inodorosValor4;
    String numIluminacion;
    String numVentilacion;
    String numAcustica;
    String numDuchas;
    String numLavamanos;
    String numOrinales;
    String numInodoros;
    String areaAula;
    String estandarAula;
    String m2AlumnosAula;
    String cumplimientoAula;
    String deficitAula;
    String areaLabCiencias;
    String estandarLabCiencias;
    String m2AlumnosLabCiencias;
    String cumplimientoLabCiencias;
    String deficitLabCiencias;
    String areaLabMultimedia;
    String estandarLabMultimedia;
    String m2AlumnosLabMultimedia;
    String cumplimientoLabMultimedia;
    String deficitLabMultimedia;
    String areaLabComputacion;
    String estandarLabComputacion;
    String m2AlumnosLabComputacion;
    String cumplimientoLabComputacion;
    String deficitLabComputacion;
    String areaBiblioteca;
    String estandarBiblioteca;
    String m2AlumnosBiblioteca;
    String cumplimientoBiblioteca;
    String deficitBiblioteca;
    String areaSalaMusica;
    String estandarSalaMusica;
    String m2AlumnosSalaMusica;
    String cumplimientoSalaMusica;
    String deficitSalaMusica;
    String areaSalaArte;
    String estandarSalaArte;
    String m2AlumnosSalaArte;
    String cumplimientoSalaArte;
    String deficitSalaArte;
    String areaPlayonDepor;
    String estandarPlayonDepor;
    String m2AlumnosPlayonDepor;
    String cumplimientoPlayonDepor;
    String deficitPlayonDepor;
    String areaExpRecre;
    String estandarExpRecre;
    String m2AlumnosExpRecre;
    String cumplimientoExpRecre;
    String deficitExpRecre;
    String areaSUM;
    String estandarSUM;
    String m2AlumnosSUM;
    String cumplimientoSUM;
    String deficitSUM;
    String areaPsicopeEnferm;
    String estandarPsicopeEnferm;
    String m2AlumnosPsicopeEnferm;
    String cumplimientoPsicopeEnferm;
    String deficitPsicopeEnferm;
    String areaServSanitarios;
    String estandarServSanitarios;
    String m2AlumnosServSanitarios;
    String cumplimientoServSanitarios;
    String deficitServSanitarios;
    String areaCocinaCafe;
    String estandarCocinaCafe;
    String m2AlumnosCocinaCafe;
    String cumplimientoCocinaCafe;
    String deficitCocinaCafe;
    String areaOfAdm;
    String estandarOfAdm;
    String m2AlumnosOfAdm;
    String cumplimientoOfAdm;
    String deficitOfAdm;
    String propAreasCentrales;
    String propAreasApoyo;
    String propAreasAulas;
    String propAreasLaboratorios;
    String propAreasCentroRecursosAprendizaje;
    String propAreasTalleres;
    String propAreasExtension;
    String propAreasServSanitarios;
    String propAreasAbastProcesa;
    String propAreasConducAdministra;
    String utilizacion;
    String cuposTotal;
    String m2Requeridos;
    String construiblePrimerPiso;
    String construibleAltura;
    String construibleTotal;
    String idoneidad;
    String indOcupacion;
    String indConstruccion;
    String numPrediosConRiesgos;
    String poblacionAfectadaRiesgos;
    String poblacionTotal;
    String m2PrediosEstadoEstrucCritico;
    String numPrediosNoCumplenAula;
    String numPrediosNoCumplenLabCiencias;
    String numPrediosNoCumplenLabMultimedia;
    String numPrediosNoCumplenLabComputacion;
    String numPrediosNoCumplenBiblioteca;
    String numPrediosNoCumplenSalaMusica;
    String numPrediosNoCumplenSalaArte;
    String numPrediosNoCumplenPlayonDepor;
    String numPrediosNoCumplenExpRecre;
    String numPrediosNoCumplenSUM;
    String numPrediosNoCumplenPsicopeEnferm;
    String numPrediosNoCumplenServSanitarios;
    String numPrediosNoCumplenCocinaCafe;
    String numPrediosNoCumplenOfAdm;
    String aislamientoTermicoPisos;
    String aislamientoTermicoMuros;
    String aislamientoTermicoVentanas;
    String aislamientoTermicoCielos;
    String aislamientoTermicoOtros;
    String m2AmpliarCobertura;
    String alumnosNuevosInfraestructura;
    
    public String getCodigoDPA() {
        return codigoDPA;
    }

    public void setCodigoDPA(String codigoDPA) {
        this.codigoDPA = codigoDPA;
    }

    public String getNombreDPA() {
        return nombreDPA;
    }

    public void setNombreDPA(String nombreDPA) {
        this.nombreDPA = nombreDPA;
    }

    public String getNumEstablecimientos() {
        return numEstablecimientos;
    }

    public void setNumEstablecimientos(String numEstablecimientos) {
        this.numEstablecimientos = numEstablecimientos;
    }

    public String getNumSedes() {
        return numSedes;
    }

    public void setNumSedes(String numSedes) {
        this.numSedes = numSedes;
    }

    public String getNumPredios() {
        return numPredios;
    }

    public void setNumPredios(String numPredios) {
        this.numPredios = numPredios;
    }

    public String getPre() {
        return pre;
    }

    public void setPre(String pre) {
        this.pre = pre;
    }

    public String getPrimaria() {
        return primaria;
    }

    public void setPrimaria(String primaria) {
        this.primaria = primaria;
    }

    public String getSecundaria() {
        return secundaria;
    }

    public void setSecundaria(String secundaria) {
        this.secundaria = secundaria;
    }

    public String getBasica() {
        return basica;
    }

    public void setBasica(String basica) {
        this.basica = basica;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getSuperficie() {
        return superficie;
    }

    public void setSuperficie(String superficie) {
        this.superficie = superficie;
    }

    public String getAreaConstrucPrimerPiso() {
        return areaConstrucPrimerPiso;
    }

    public void setAreaConstrucPrimerPiso(String areaConstrucPrimerPiso) {
        this.areaConstrucPrimerPiso = areaConstrucPrimerPiso;
    }

    public String getAreaConstrucAltura() {
        return areaConstrucAltura;
    }

    public void setAreaConstrucAltura(String areaConstrucAltura) {
        this.areaConstrucAltura = areaConstrucAltura;
    }

    public String getPorcenConstruccion() {
        return porcenConstruccion;
    }

    public void setPorcenConstruccion(String porcenConstruccion) {
        this.porcenConstruccion = porcenConstruccion;
    }

    public String getNumPrediosAccesibilidadInadecuada() {
        return numPrediosAccesibilidadInadecuada;
    }

    public void setNumPrediosAccesibilidadInadecuada(String numPrediosAccesibilidadInadecuada) {
        this.numPrediosAccesibilidadInadecuada = numPrediosAccesibilidadInadecuada;
    }

    public String getAccesibilidadPoblacionAfectada() {
        return accesibilidadPoblacionAfectada;
    }

    public void setAccesibilidadPoblacionAfectada(String accesibilidadPoblacionAfectada) {
        this.accesibilidadPoblacionAfectada = accesibilidadPoblacionAfectada;
    }

    public String getPrediosProblemasLegalizacion() {
        return prediosProblemasLegalizacion;
    }

    public void setPrediosProblemasLegalizacion(String prediosProblemasLegalizacion) {
        this.prediosProblemasLegalizacion = prediosProblemasLegalizacion;
    }

    public String getEstudiantesProblemaLegalizacion() {
        return estudiantesProblemaLegalizacion;
    }

    public void setEstudiantesProblemaLegalizacion(String estudiantesProblemaLegalizacion) {
        this.estudiantesProblemaLegalizacion = estudiantesProblemaLegalizacion;
    }

    public String getNumPrediosConAgua() {
        return numPrediosConAgua;
    }

    public void setNumPrediosConAgua(String numPrediosConAgua) {
        this.numPrediosConAgua = numPrediosConAgua;
    }

    public String getNumPrediosConEE() {
        return numPrediosConEE;
    }

    public void setNumPrediosConEE(String numPrediosConEE) {
        this.numPrediosConEE = numPrediosConEE;
    }

    public String getNumPrediosConGas() {
        return numPrediosConGas;
    }

    public void setNumPrediosConGas(String numPrediosConGas) {
        this.numPrediosConGas = numPrediosConGas;
    }

    public String getNumPrediosConReciclaje() {
        return numPrediosConReciclaje;
    }

    public void setNumPrediosConReciclaje(String numPrediosConReciclaje) {
        this.numPrediosConReciclaje = numPrediosConReciclaje;
    }

    public String getNumPrediosConAlcantarillado() {
        return numPrediosConAlcantarillado;
    }

    public void setNumPrediosConAlcantarillado(String numPrediosConAlcantarillado) {
        this.numPrediosConAlcantarillado = numPrediosConAlcantarillado;
    }

    public String getNumPrediosConPluvial() {
        return numPrediosConPluvial;
    }

    public void setNumPrediosConPluvial(String numPrediosConPluvial) {
        this.numPrediosConPluvial = numPrediosConPluvial;
    }

    public String getNumPrediosConInternet() {
        return numPrediosConInternet;
    }

    public void setNumPrediosConInternet(String numPrediosConInternet) {
        this.numPrediosConInternet = numPrediosConInternet;
    }

    public String getNumPrediosConTelefono() {
        return numPrediosConTelefono;
    }

    public void setNumPrediosConTelefono(String numPrediosConTelefono) {
        this.numPrediosConTelefono = numPrediosConTelefono;
    }

    public String getPromedioConsumoAgua() {
        return promedioConsumoAgua;
    }

    public void setPromedioConsumoAgua(String promedioConsumoAgua) {
        this.promedioConsumoAgua = promedioConsumoAgua;
    }

    public String getNumPrediosCumplenConsumoAgua() {
        return numPrediosCumplenConsumoAgua;
    }

    public void setNumPrediosCumplenConsumoAgua(String numPrediosCumplenConsumoAgua) {
        this.numPrediosCumplenConsumoAgua = numPrediosCumplenConsumoAgua;
    }

    public String getPromedioConsumoEE() {
        return promedioConsumoEE;
    }

    public void setPromedioConsumoEE(String promedioConsumoEE) {
        this.promedioConsumoEE = promedioConsumoEE;
    }

    public String getNumPrediosCumplenConsumoEE() {
        return numPrediosCumplenConsumoEE;
    }

    public void setNumPrediosCumplenConsumoEE(String numPrediosCumplenConsumoEE) {
        this.numPrediosCumplenConsumoEE = numPrediosCumplenConsumoEE;
    }

    public String getNumPrediosVulnerables() {
        return numPrediosVulnerables;
    }

    public void setNumPrediosVulnerables(String numPrediosVulnerables) {
        this.numPrediosVulnerables = numPrediosVulnerables;
    }

    public String getNumPrediosEstrucCritica() {
        return numPrediosEstrucCritica;
    }

    public void setNumPrediosEstrucCritica(String numPrediosEstrucCritica) {
        this.numPrediosEstrucCritica = numPrediosEstrucCritica;
    }

    public String getNumPrediosRutaEvacCritica() {
        return numPrediosRutaEvacCritica;
    }

    public void setNumPrediosRutaEvacCritica(String numPrediosRutaEvacCritica) {
        this.numPrediosRutaEvacCritica = numPrediosRutaEvacCritica;
    }

    public String getNumPrediosSenalizaEvacCritica() {
        return numPrediosSenalizaEvacCritica;
    }

    public void setNumPrediosSenalizaEvacCritica(String numPrediosSenalizaEvacCritica) {
        this.numPrediosSenalizaEvacCritica = numPrediosSenalizaEvacCritica;
    }

    public String getNumPrediosNoRactivoIncendio() {
        return numPrediosNoRactivoIncendio;
    }

    public void setNumPrediosNoRactivoIncendio(String numPrediosNoRactivoIncendio) {
        this.numPrediosNoRactivoIncendio = numPrediosNoRactivoIncendio;
    }

    public String getNumPisosCond4() {
        return numPisosCond4;
    }

    public void setNumPisosCond4(String numPisosCond4) {
        this.numPisosCond4 = numPisosCond4;
    }

    public String getNumPisosCond3() {
        return numPisosCond3;
    }

    public void setNumPisosCond3(String numPisosCond3) {
        this.numPisosCond3 = numPisosCond3;
    }

    public String getNumPisosCond2() {
        return numPisosCond2;
    }

    public void setNumPisosCond2(String numPisosCond2) {
        this.numPisosCond2 = numPisosCond2;
    }

    public String getNumPisosCond1() {
        return numPisosCond1;
    }

    public void setNumPisosCond1(String numPisosCond1) {
        this.numPisosCond1 = numPisosCond1;
    }

    public String getNumPisos() {
        return new Integer(UtilCadena.isNullOVacio(numPisosCond4)?"0":numPisosCond4)+new Integer(UtilCadena.isNullOVacio(numPisosCond3)?"0":numPisosCond3)+new Integer(UtilCadena.isNullOVacio(numPisosCond2)?"0":numPisosCond2)+new Integer(UtilCadena.isNullOVacio(numPisosCond1)?"0":numPisosCond1)+"";
    }

    public String getNumMurosCond4() {
        return numMurosCond4;
    }

    public void setNumMurosCond4(String numMurosCond4) {
        this.numMurosCond4 = numMurosCond4;
    }

    public String getNumMurosCond3() {
        return numMurosCond3;
    }

    public void setNumMurosCond3(String numMurosCond3) {
        this.numMurosCond3 = numMurosCond3;
    }

    public String getNumMurosCond2() {
        return numMurosCond2;
    }

    public void setNumMurosCond2(String numMurosCond2) {
        this.numMurosCond2 = numMurosCond2;
    }

    public String getNumMurosCond1() {
        return numMurosCond1;
    }

    public void setNumMurosCond1(String numMurosCond1) {
        this.numMurosCond1 = numMurosCond1;
    }

    public String getNumMuros() {
        return new Integer(UtilCadena.isNullOVacio(numMurosCond4)?"0":numMurosCond4)+new Integer(UtilCadena.isNullOVacio(numMurosCond3)?"0":numMurosCond3)+new Integer(UtilCadena.isNullOVacio(numMurosCond2)?"0":numMurosCond2)+new Integer(UtilCadena.isNullOVacio(numMurosCond1)?"0":numMurosCond1)+"";
    }

    public String getNumAcabadosMuroCond4() {
        return numAcabadosMuroCond4;
    }

    public void setNumAcabadosMuroCond4(String numAcabadosMuroCond4) {
        this.numAcabadosMuroCond4 = numAcabadosMuroCond4;
    }

    public String getNumAcabadosMuroCond3() {
        return numAcabadosMuroCond3;
    }

    public void setNumAcabadosMuroCond3(String numAcabadosMuroCond3) {
        this.numAcabadosMuroCond3 = numAcabadosMuroCond3;
    }

    public String getNumAcabadosMuroCond2() {
        return numAcabadosMuroCond2;
    }

    public void setNumAcabadosMuroCond2(String numAcabadosMuroCond2) {
        this.numAcabadosMuroCond2 = numAcabadosMuroCond2;
    }

    public String getNumAcabadosMuroCond1() {
        return numAcabadosMuroCond1;
    }

    public void setNumAcabadosMuroCond1(String numAcabadosMuroCond1) {
        this.numAcabadosMuroCond1 = numAcabadosMuroCond1;
    }

    public String getNumVentanasCond4() {
        return numVentanasCond4;
    }

    public void setNumVentanasCond4(String numVentanasCond4) {
        this.numVentanasCond4 = numVentanasCond4;
    }

    public String getNumVentanasCond3() {
        return numVentanasCond3;
    }

    public void setNumVentanasCond3(String numVentanasCond3) {
        this.numVentanasCond3 = numVentanasCond3;
    }

    public String getNumVentanasCond2() {
        return numVentanasCond2;
    }

    public void setNumVentanasCond2(String numVentanasCond2) {
        this.numVentanasCond2 = numVentanasCond2;
    }

    public String getNumVentanasCond1() {
        return numVentanasCond1;
    }

    public void setNumVentanasCond1(String numVentanasCond1) {
        this.numVentanasCond1 = numVentanasCond1;
    }

    public String getNumVentanas() {
        return new Integer(UtilCadena.isNullOVacio(numVentanasCond4)?"0":numVentanasCond4)+new Integer(UtilCadena.isNullOVacio(numVentanasCond3)?"0":numVentanasCond3)+new Integer(UtilCadena.isNullOVacio(numVentanasCond2)?"0":numVentanasCond2)+new Integer(UtilCadena.isNullOVacio(numVentanasCond1)?"0":numVentanasCond1)+"";
    }

    public String getNumPuertasCond4() {
        return numPuertasCond4;
    }

    public void setNumPuertasCond4(String numPuertasCond4) {
        this.numPuertasCond4 = numPuertasCond4;
    }

    public String getNumPuertasCond3() {
        return numPuertasCond3;
    }

    public void setNumPuertasCond3(String numPuertasCond3) {
        this.numPuertasCond3 = numPuertasCond3;
    }

    public String getNumPuertasCond2() {
        return numPuertasCond2;
    }

    public void setNumPuertasCond2(String numPuertasCond2) {
        this.numPuertasCond2 = numPuertasCond2;
    }

    public String getNumPuertasCond1() {
        return numPuertasCond1;
    }

    public void setNumPuertasCond1(String numPuertasCond1) {
        this.numPuertasCond1 = numPuertasCond1;
    }

    public String getNumCieloRasosCond4() {
        return numCieloRasosCond4;
    }

    public void setNumCieloRasosCond4(String numCieloRasosCond4) {
        this.numCieloRasosCond4 = numCieloRasosCond4;
    }

    public String getNumCieloRasosCond3() {
        return numCieloRasosCond3;
    }

    public void setNumCieloRasosCond3(String numCieloRasosCond3) {
        this.numCieloRasosCond3 = numCieloRasosCond3;
    }

    public String getNumCieloRasosCond2() {
        return numCieloRasosCond2;
    }

    public void setNumCieloRasosCond2(String numCieloRasosCond2) {
        this.numCieloRasosCond2 = numCieloRasosCond2;
    }

    public String getNumCieloRasosCond1() {
        return numCieloRasosCond1;
    }

    public void setNumCieloRasosCond1(String numCieloRasosCond1) {
        this.numCieloRasosCond1 = numCieloRasosCond1;
    }

    public String getNumCieloRasos() {
        return new Integer(UtilCadena.isNullOVacio(numCieloRasosCond4)?"0":numCieloRasosCond4)+new Integer(UtilCadena.isNullOVacio(numCieloRasosCond3)?"0":numCieloRasosCond3)+new Integer(UtilCadena.isNullOVacio(numCieloRasosCond2)?"0":numCieloRasosCond2)+new Integer(UtilCadena.isNullOVacio(numCieloRasosCond1)?"0":numCieloRasosCond1)+"";
    }

    public String getAreaPisosCond4() {
        return areaPisosCond4;
    }

    public void setAreaPisosCond4(String areaPisosCond4) {
        this.areaPisosCond4 = areaPisosCond4;
    }

    public String getAreaPisosCond3() {
        return areaPisosCond3;
    }

    public void setAreaPisosCond3(String areaPisosCond3) {
        this.areaPisosCond3 = areaPisosCond3;
    }

    public String getAreaPisosCond2() {
        return areaPisosCond2;
    }

    public void setAreaPisosCond2(String areaPisosCond2) {
        this.areaPisosCond2 = areaPisosCond2;
    }

    public String getAreaPisosCond1() {
        return areaPisosCond1;
    }

    public void setAreaPisosCond1(String areaPisosCond1) {
        this.areaPisosCond1 = areaPisosCond1;
    }

    public String getAreaPisos() {
        return new BigDecimal(UtilCadena.isNullOVacio(areaPisosCond4)?"0":areaPisosCond4).add(new BigDecimal(UtilCadena.isNullOVacio(areaPisosCond3)?"0":areaPisosCond3)).add(new BigDecimal(UtilCadena.isNullOVacio(areaPisosCond2)?"0":areaPisosCond2)).add(new BigDecimal(UtilCadena.isNullOVacio(areaPisosCond1)?"0":areaPisosCond1))+"";
    }

    public String getAreaMurosCond4() {
        return areaMurosCond4;
    }

    public void setAreaMurosCond4(String areaMurosCond4) {
        this.areaMurosCond4 = areaMurosCond4;
    }

    public String getAreaMurosCond3() {
        return areaMurosCond3;
    }

    public void setAreaMurosCond3(String areaMurosCond3) {
        this.areaMurosCond3 = areaMurosCond3;
    }

    public String getAreaMurosCond2() {
        return areaMurosCond2;
    }

    public void setAreaMurosCond2(String areaMurosCond2) {
        this.areaMurosCond2 = areaMurosCond2;
    }

    public String getAreaMurosCond1() {
        return areaMurosCond1;
    }

    public void setAreaMurosCond1(String areaMurosCond1) {
        this.areaMurosCond1 = areaMurosCond1;
    }

    public String getAreaMuros() {
        return new BigDecimal(UtilCadena.isNullOVacio(areaMurosCond4)?"0":areaMurosCond4).add(new BigDecimal(UtilCadena.isNullOVacio(areaMurosCond3)?"0":areaMurosCond3)).add(new BigDecimal(UtilCadena.isNullOVacio(areaMurosCond2)?"0":areaMurosCond2)).add(new BigDecimal(UtilCadena.isNullOVacio(areaMurosCond1)?"0":areaMurosCond1))+"";
    }

    public String getAreaAcabadosMuroCond4() {
        return areaAcabadosMuroCond4;
    }

    public void setAreaAcabadosMuroCond4(String areaAcabadosMuroCond4) {
        this.areaAcabadosMuroCond4 = areaAcabadosMuroCond4;
    }

    public String getAreaAcabadosMuroCond3() {
        return areaAcabadosMuroCond3;
    }

    public void setAreaAcabadosMuroCond3(String areaAcabadosMuroCond3) {
        this.areaAcabadosMuroCond3 = areaAcabadosMuroCond3;
    }

    public String getAreaAcabadosMuroCond2() {
        return areaAcabadosMuroCond2;
    }

    public void setAreaAcabadosMuroCond2(String areaAcabadosMuroCond2) {
        this.areaAcabadosMuroCond2 = areaAcabadosMuroCond2;
    }

    public String getAreaAcabadosMuroCond1() {
        return areaAcabadosMuroCond1;
    }

    public void setAreaAcabadosMuroCond1(String areaAcabadosMuroCond1) {
        this.areaAcabadosMuroCond1 = areaAcabadosMuroCond1;
    }

    public String getAreaAcabadosMuro() {
        return new BigDecimal(UtilCadena.isNullOVacio(areaAcabadosMuroCond4)?"0":areaAcabadosMuroCond4).add(new BigDecimal(UtilCadena.isNullOVacio(areaAcabadosMuroCond3)?"0":areaAcabadosMuroCond3)).add(new BigDecimal(UtilCadena.isNullOVacio(areaAcabadosMuroCond2)?"0":areaAcabadosMuroCond2)).add(new BigDecimal(UtilCadena.isNullOVacio(areaAcabadosMuroCond1)?"0":areaAcabadosMuroCond1))+"";
    }

    public String getAreaCieloRasosCond4() {
        return areaCieloRasosCond4;
    }

    public void setAreaCieloRasosCond4(String areaCieloRasosCond4) {
        this.areaCieloRasosCond4 = areaCieloRasosCond4;
    }

    public String getAreaCieloRasosCond3() {
        return areaCieloRasosCond3;
    }

    public void setAreaCieloRasosCond3(String areaCieloRasosCond3) {
        this.areaCieloRasosCond3 = areaCieloRasosCond3;
    }

    public String getAreaCieloRasosCond2() {
        return areaCieloRasosCond2;
    }

    public void setAreaCieloRasosCond2(String areaCieloRasosCond2) {
        this.areaCieloRasosCond2 = areaCieloRasosCond2;
    }

    public String getAreaCieloRasosCond1() {
        return areaCieloRasosCond1;
    }

    public void setAreaCieloRasosCond1(String areaCieloRasosCond1) {
        this.areaCieloRasosCond1 = areaCieloRasosCond1;
    }

    public String getAreaCieloRasos() {
        return new BigDecimal(UtilCadena.isNullOVacio(areaCieloRasosCond4)?"0":areaCieloRasosCond4).add(new BigDecimal(UtilCadena.isNullOVacio(areaCieloRasosCond3)?"0":areaCieloRasosCond3)).add(new BigDecimal(UtilCadena.isNullOVacio(areaCieloRasosCond2)?"0":areaCieloRasosCond2)).add(new BigDecimal(UtilCadena.isNullOVacio(areaCieloRasosCond1)?"0":areaCieloRasosCond1))+"";
    }

    public String getMurosFachadasValor1() {
        return murosFachadasValor1;
    }

    public void setMurosFachadasValor1(String murosFachadasValor1) {
        this.murosFachadasValor1 = murosFachadasValor1;
    }

    public String getMurosFachadasValor2() {
        return murosFachadasValor2;
    }

    public void setMurosFachadasValor2(String murosFachadasValor2) {
        this.murosFachadasValor2 = murosFachadasValor2;
    }

    public String getMurosFachadasValor3() {
        return murosFachadasValor3;
    }

    public void setMurosFachadasValor3(String murosFachadasValor3) {
        this.murosFachadasValor3 = murosFachadasValor3;
    }

    public String getMurosFachadasValor4() {
        return murosFachadasValor4;
    }

    public void setMurosFachadasValor4(String murosFachadasValor4) {
        this.murosFachadasValor4 = murosFachadasValor4;
    }

    public String getAcabadosFachadasValor1() {
        return acabadosFachadasValor1;
    }

    public void setAcabadosFachadasValor1(String acabadosFachadasValor1) {
        this.acabadosFachadasValor1 = acabadosFachadasValor1;
    }

    public String getAcabadosFachadasValor2() {
        return acabadosFachadasValor2;
    }

    public void setAcabadosFachadasValor2(String acabadosFachadasValor2) {
        this.acabadosFachadasValor2 = acabadosFachadasValor2;
    }

    public String getAcabadosFachadasValor3() {
        return acabadosFachadasValor3;
    }

    public void setAcabadosFachadasValor3(String acabadosFachadasValor3) {
        this.acabadosFachadasValor3 = acabadosFachadasValor3;
    }

    public String getAcabadosFachadasValor4() {
        return acabadosFachadasValor4;
    }

    public void setAcabadosFachadasValor4(String acabadosFachadasValor4) {
        this.acabadosFachadasValor4 = acabadosFachadasValor4;
    }

    public String getCubiertasValor1() {
        return cubiertasValor1;
    }

    public void setCubiertasValor1(String cubiertasValor1) {
        this.cubiertasValor1 = cubiertasValor1;
    }

    public String getCubiertasValor2() {
        return cubiertasValor2;
    }

    public void setCubiertasValor2(String cubiertasValor2) {
        this.cubiertasValor2 = cubiertasValor2;
    }

    public String getCubiertasValor3() {
        return cubiertasValor3;
    }

    public void setCubiertasValor3(String cubiertasValor3) {
        this.cubiertasValor3 = cubiertasValor3;
    }

    public String getCubiertasValor4() {
        return cubiertasValor4;
    }

    public void setCubiertasValor4(String cubiertasValor4) {
        this.cubiertasValor4 = cubiertasValor4;
    }

    public String getIluminacionValor1() {
        return iluminacionValor1;
    }

    public void setIluminacionValor1(String iluminacionValor1) {
        this.iluminacionValor1 = iluminacionValor1;
    }

    public String getIluminacionValor2() {
        return iluminacionValor2;
    }

    public void setIluminacionValor2(String iluminacionValor2) {
        this.iluminacionValor2 = iluminacionValor2;
    }

    public String getIluminacionValor3() {
        return iluminacionValor3;
    }

    public void setIluminacionValor3(String iluminacionValor3) {
        this.iluminacionValor3 = iluminacionValor3;
    }

    public String getIluminacionValor4() {
        return iluminacionValor4;
    }

    public void setIluminacionValor4(String iluminacionValor4) {
        this.iluminacionValor4 = iluminacionValor4;
    }

    public String getVentilacionValor1() {
        return ventilacionValor1;
    }

    public void setVentilacionValor1(String ventilacionValor1) {
        this.ventilacionValor1 = ventilacionValor1;
    }

    public String getVentilacionValor2() {
        return ventilacionValor2;
    }

    public void setVentilacionValor2(String ventilacionValor2) {
        this.ventilacionValor2 = ventilacionValor2;
    }

    public String getVentilacionValor3() {
        return ventilacionValor3;
    }

    public void setVentilacionValor3(String ventilacionValor3) {
        this.ventilacionValor3 = ventilacionValor3;
    }

    public String getVentilacionValor4() {
        return ventilacionValor4;
    }

    public void setVentilacionValor4(String ventilacionValor4) {
        this.ventilacionValor4 = ventilacionValor4;
    }

    public String getAcusticaValor1() {
        return acusticaValor1;
    }

    public void setAcusticaValor1(String acusticaValor1) {
        this.acusticaValor1 = acusticaValor1;
    }

    public String getAcusticaValor2() {
        return acusticaValor2;
    }

    public void setAcusticaValor2(String acusticaValor2) {
        this.acusticaValor2 = acusticaValor2;
    }

    public String getAcusticaValor3() {
        return acusticaValor3;
    }

    public void setAcusticaValor3(String acusticaValor3) {
        this.acusticaValor3 = acusticaValor3;
    }

    public String getAcusticaValor4() {
        return acusticaValor4;
    }

    public void setAcusticaValor4(String acusticaValor4) {
        this.acusticaValor4 = acusticaValor4;
    }

    public String getDuchasValor1() {
        return duchasValor1;
    }

    public void setDuchasValor1(String duchasValor1) {
        this.duchasValor1 = duchasValor1;
    }

    public String getDuchasValor2() {
        return duchasValor2;
    }

    public void setDuchasValor2(String duchasValor2) {
        this.duchasValor2 = duchasValor2;
    }

    public String getDuchasValor3() {
        return duchasValor3;
    }

    public void setDuchasValor3(String duchasValor3) {
        this.duchasValor3 = duchasValor3;
    }

    public String getDuchasValor4() {
        return duchasValor4;
    }

    public void setDuchasValor4(String duchasValor4) {
        this.duchasValor4 = duchasValor4;
    }

    public String getLavamanosValor1() {
        return lavamanosValor1;
    }

    public void setLavamanosValor1(String lavamanosValor1) {
        this.lavamanosValor1 = lavamanosValor1;
    }

    public String getLavamanosValor2() {
        return lavamanosValor2;
    }

    public void setLavamanosValor2(String lavamanosValor2) {
        this.lavamanosValor2 = lavamanosValor2;
    }

    public String getLavamanosValor3() {
        return lavamanosValor3;
    }

    public void setLavamanosValor3(String lavamanosValor3) {
        this.lavamanosValor3 = lavamanosValor3;
    }

    public String getLavamanosValor4() {
        return lavamanosValor4;
    }

    public void setLavamanosValor4(String lavamanosValor4) {
        this.lavamanosValor4 = lavamanosValor4;
    }

    public String getOrinalesValor1() {
        return orinalesValor1;
    }

    public void setOrinalesValor1(String orinalesValor1) {
        this.orinalesValor1 = orinalesValor1;
    }

    public String getOrinalesValor2() {
        return orinalesValor2;
    }

    public void setOrinalesValor2(String orinalesValor2) {
        this.orinalesValor2 = orinalesValor2;
    }

    public String getOrinalesValor3() {
        return orinalesValor3;
    }

    public void setOrinalesValor3(String orinalesValor3) {
        this.orinalesValor3 = orinalesValor3;
    }

    public String getOrinalesValor4() {
        return orinalesValor4;
    }

    public void setOrinalesValor4(String orinalesValor4) {
        this.orinalesValor4 = orinalesValor4;
    }

    public String getInodorosValor1() {
        return inodorosValor1;
    }

    public void setInodorosValor1(String inodorosValor1) {
        this.inodorosValor1 = inodorosValor1;
    }

    public String getInodorosValor2() {
        return inodorosValor2;
    }

    public void setInodorosValor2(String inodorosValor2) {
        this.inodorosValor2 = inodorosValor2;
    }

    public String getInodorosValor3() {
        return inodorosValor3;
    }

    public void setInodorosValor3(String inodorosValor3) {
        this.inodorosValor3 = inodorosValor3;
    }

    public String getInodorosValor4() {
        return inodorosValor4;
    }

    public void setInodorosValor4(String inodorosValor4) {
        this.inodorosValor4 = inodorosValor4;
    }

    public String getNumIluminacion() {
        return numIluminacion;
    }

    public void setNumIluminacion(String numIluminacion) {
        this.numIluminacion = numIluminacion;
    }

    public String getNumVentilacion() {
        return numVentilacion;
    }

    public void setNumVentilacion(String numVentilacion) {
        this.numVentilacion = numVentilacion;
    }

    public String getNumAcustica() {
        return numAcustica;
    }

    public void setNumAcustica(String numAcustica) {
        this.numAcustica = numAcustica;
    }

    public String getNumDuchas() {
        return numDuchas;
    }

    public void setNumDuchas(String numDuchas) {
        this.numDuchas = numDuchas;
    }

    public String getNumLavamanos() {
        return numLavamanos;
    }

    public void setNumLavamanos(String numLavamanos) {
        this.numLavamanos = numLavamanos;
    }

    public String getNumOrinales() {
        return numOrinales;
    }

    public void setNumOrinales(String numOrinales) {
        this.numOrinales = numOrinales;
    }

    public String getNumInodoros() {
        return numInodoros;
    }

    public void setNumInodoros(String numInodoros) {
        this.numInodoros = numInodoros;
    }

    public String getNumAparatosSanitarios() {
        return new Integer(numLavamanos)>new Integer(numInodoros)+new Integer(numOrinales)?new Integer(numInodoros)+new Integer(numOrinales)+"":new Integer(numLavamanos)+"";
    }

    public String getAreaAula() {
        return areaAula;
    }

    public void setAreaAula(String areaAula) {
        this.areaAula = areaAula;
    }

    public String getEstandarAula() {
        return estandarAula;
    }

    public void setEstandarAula(String estandarAula) {
        this.estandarAula = estandarAula;
    }

    public String getM2AlumnosAula() {
        return m2AlumnosAula;
    }

    public void setM2AlumnosAula(String m2AlumnosAula) {
        this.m2AlumnosAula = m2AlumnosAula;
    }

    public String getCumplimientoAula() {
        return cumplimientoAula;
    }

    public void setCumplimientoAula(String cumplimientoAula) {
        this.cumplimientoAula = cumplimientoAula;
    }

    public String getDeficitAula() {
        return deficitAula;
    }

    public void setDeficitAula(String deficitAula) {
        this.deficitAula = deficitAula;
    }

    public String getAreaLabCiencias() {
        return areaLabCiencias;
    }

    public void setAreaLabCiencias(String areaLabCiencias) {
        this.areaLabCiencias = areaLabCiencias;
    }

    public String getEstandarLabCiencias() {
        return estandarLabCiencias;
    }

    public void setEstandarLabCiencias(String estandarLabCiencias) {
        this.estandarLabCiencias = estandarLabCiencias;
    }

    public String getM2AlumnosLabCiencias() {
        return m2AlumnosLabCiencias;
    }

    public void setM2AlumnosLabCiencias(String m2AlumnosLabCiencias) {
        this.m2AlumnosLabCiencias = m2AlumnosLabCiencias;
    }

    public String getCumplimientoLabCiencias() {
        return cumplimientoLabCiencias;
    }

    public void setCumplimientoLabCiencias(String cumplimientoLabCiencias) {
        this.cumplimientoLabCiencias = cumplimientoLabCiencias;
    }

    public String getDeficitLabCiencias() {
        return deficitLabCiencias;
    }

    public void setDeficitLabCiencias(String deficitLabCiencias) {
        this.deficitLabCiencias = deficitLabCiencias;
    }

    public String getAreaLabMultimedia() {
        return areaLabMultimedia;
    }

    public void setAreaLabMultimedia(String areaLabMultimedia) {
        this.areaLabMultimedia = areaLabMultimedia;
    }

    public String getEstandarLabMultimedia() {
        return estandarLabMultimedia;
    }

    public void setEstandarLabMultimedia(String estandarLabMultimedia) {
        this.estandarLabMultimedia = estandarLabMultimedia;
    }

    public String getM2AlumnosLabMultimedia() {
        return m2AlumnosLabMultimedia;
    }

    public void setM2AlumnosLabMultimedia(String m2AlumnosLabMultimedia) {
        this.m2AlumnosLabMultimedia = m2AlumnosLabMultimedia;
    }

    public String getCumplimientoLabMultimedia() {
        return cumplimientoLabMultimedia;
    }

    public void setCumplimientoLabMultimedia(String cumplimientoLabMultimedia) {
        this.cumplimientoLabMultimedia = cumplimientoLabMultimedia;
    }

    public String getDeficitLabMultimedia() {
        return deficitLabMultimedia;
    }

    public void setDeficitLabMultimedia(String deficitLabMultimedia) {
        this.deficitLabMultimedia = deficitLabMultimedia;
    }

    public String getAreaLabComputacion() {
        return areaLabComputacion;
    }

    public void setAreaLabComputacion(String areaLabComputacion) {
        this.areaLabComputacion = areaLabComputacion;
    }

    public String getEstandarLabComputacion() {
        return estandarLabComputacion;
    }

    public void setEstandarLabComputacion(String estandarLabComputacion) {
        this.estandarLabComputacion = estandarLabComputacion;
    }

    public String getM2AlumnosLabComputacion() {
        return m2AlumnosLabComputacion;
    }

    public void setM2AlumnosLabComputacion(String m2AlumnosLabComputacion) {
        this.m2AlumnosLabComputacion = m2AlumnosLabComputacion;
    }

    public String getCumplimientoLabComputacion() {
        return cumplimientoLabComputacion;
    }

    public void setCumplimientoLabComputacion(String cumplimientoLabComputacion) {
        this.cumplimientoLabComputacion = cumplimientoLabComputacion;
    }

    public String getDeficitLabComputacion() {
        return deficitLabComputacion;
    }

    public void setDeficitLabComputacion(String deficitLabComputacion) {
        this.deficitLabComputacion = deficitLabComputacion;
    }

    public String getAreaBiblioteca() {
        return areaBiblioteca;
    }

    public void setAreaBiblioteca(String areaBiblioteca) {
        this.areaBiblioteca = areaBiblioteca;
    }

    public String getEstandarBiblioteca() {
        return estandarBiblioteca;
    }

    public void setEstandarBiblioteca(String estandarBiblioteca) {
        this.estandarBiblioteca = estandarBiblioteca;
    }

    public String getM2AlumnosBiblioteca() {
        return m2AlumnosBiblioteca;
    }

    public void setM2AlumnosBiblioteca(String m2AlumnosBiblioteca) {
        this.m2AlumnosBiblioteca = m2AlumnosBiblioteca;
    }

    public String getCumplimientoBiblioteca() {
        return cumplimientoBiblioteca;
    }

    public void setCumplimientoBiblioteca(String cumplimientoBiblioteca) {
        this.cumplimientoBiblioteca = cumplimientoBiblioteca;
    }

    public String getDeficitBiblioteca() {
        return deficitBiblioteca;
    }

    public void setDeficitBiblioteca(String deficitBiblioteca) {
        this.deficitBiblioteca = deficitBiblioteca;
    }

    public String getAreaSalaMusica() {
        return areaSalaMusica;
    }

    public void setAreaSalaMusica(String areaSalaMusica) {
        this.areaSalaMusica = areaSalaMusica;
    }

    public String getEstandarSalaMusica() {
        return estandarSalaMusica;
    }

    public void setEstandarSalaMusica(String estandarSalaMusica) {
        this.estandarSalaMusica = estandarSalaMusica;
    }

    public String getM2AlumnosSalaMusica() {
        return m2AlumnosSalaMusica;
    }

    public void setM2AlumnosSalaMusica(String m2AlumnosSalaMusica) {
        this.m2AlumnosSalaMusica = m2AlumnosSalaMusica;
    }

    public String getCumplimientoSalaMusica() {
        return cumplimientoSalaMusica;
    }

    public void setCumplimientoSalaMusica(String cumplimientoSalaMusica) {
        this.cumplimientoSalaMusica = cumplimientoSalaMusica;
    }

    public String getDeficitSalaMusica() {
        return deficitSalaMusica;
    }

    public void setDeficitSalaMusica(String deficitSalaMusica) {
        this.deficitSalaMusica = deficitSalaMusica;
    }

    public String getAreaSalaArte() {
        return areaSalaArte;
    }

    public void setAreaSalaArte(String areaSalaArte) {
        this.areaSalaArte = areaSalaArte;
    }

    public String getEstandarSalaArte() {
        return estandarSalaArte;
    }

    public void setEstandarSalaArte(String estandarSalaArte) {
        this.estandarSalaArte = estandarSalaArte;
    }

    public String getM2AlumnosSalaArte() {
        return m2AlumnosSalaArte;
    }

    public void setM2AlumnosSalaArte(String m2AlumnosSalaArte) {
        this.m2AlumnosSalaArte = m2AlumnosSalaArte;
    }

    public String getCumplimientoSalaArte() {
        return cumplimientoSalaArte;
    }

    public void setCumplimientoSalaArte(String cumplimientoSalaArte) {
        this.cumplimientoSalaArte = cumplimientoSalaArte;
    }

    public String getDeficitSalaArte() {
        return deficitSalaArte;
    }

    public void setDeficitSalaArte(String deficitSalaArte) {
        this.deficitSalaArte = deficitSalaArte;
    }

    public String getAreaPlayonDepor() {
        return areaPlayonDepor;
    }

    public void setAreaPlayonDepor(String areaPlayonDepor) {
        this.areaPlayonDepor = areaPlayonDepor;
    }

    public String getEstandarPlayonDepor() {
        return estandarPlayonDepor;
    }

    public void setEstandarPlayonDepor(String estandarPlayonDepor) {
        this.estandarPlayonDepor = estandarPlayonDepor;
    }

    public String getM2AlumnosPlayonDepor() {
        return m2AlumnosPlayonDepor;
    }

    public void setM2AlumnosPlayonDepor(String m2AlumnosPlayonDepor) {
        this.m2AlumnosPlayonDepor = m2AlumnosPlayonDepor;
    }

    public String getCumplimientoPlayonDepor() {
        return cumplimientoPlayonDepor;
    }

    public void setCumplimientoPlayonDepor(String cumplimientoPlayonDepor) {
        this.cumplimientoPlayonDepor = cumplimientoPlayonDepor;
    }

    public String getDeficitPlayonDepor() {
        return deficitPlayonDepor;
    }

    public void setDeficitPlayonDepor(String deficitPlayonDepor) {
        this.deficitPlayonDepor = deficitPlayonDepor;
    }

    public String getAreaExpRecre() {
        return areaExpRecre;
    }

    public void setAreaExpRecre(String areaExpRecre) {
        this.areaExpRecre = areaExpRecre;
    }

    public String getEstandarExpRecre() {
        return estandarExpRecre;
    }

    public void setEstandarExpRecre(String estandarExpRecre) {
        this.estandarExpRecre = estandarExpRecre;
    }

    public String getM2AlumnosExpRecre() {
        return m2AlumnosExpRecre;
    }

    public void setM2AlumnosExpRecre(String m2AlumnosExpRecre) {
        this.m2AlumnosExpRecre = m2AlumnosExpRecre;
    }

    public String getCumplimientoExpRecre() {
        return cumplimientoExpRecre;
    }

    public void setCumplimientoExpRecre(String cumplimientoExpRecre) {
        this.cumplimientoExpRecre = cumplimientoExpRecre;
    }

    public String getDeficitExpRecre() {
        return deficitExpRecre;
    }

    public void setDeficitExpRecre(String deficitExpRecre) {
        this.deficitExpRecre = deficitExpRecre;
    }

    public String getAreaSUM() {
        return areaSUM;
    }

    public void setAreaSUM(String areaSUM) {
        this.areaSUM = areaSUM;
    }

    public String getEstandarSUM() {
        return estandarSUM;
    }

    public void setEstandarSUM(String estandarSUM) {
        this.estandarSUM = estandarSUM;
    }

    public String getM2AlumnosSUM() {
        return m2AlumnosSUM;
    }

    public void setM2AlumnosSUM(String m2AlumnosSUM) {
        this.m2AlumnosSUM = m2AlumnosSUM;
    }

    public String getCumplimientoSUM() {
        return cumplimientoSUM;
    }

    public void setCumplimientoSUM(String cumplimientoSUM) {
        this.cumplimientoSUM = cumplimientoSUM;
    }

    public String getDeficitSUM() {
        return deficitSUM;
    }

    public void setDeficitSUM(String deficitSUM) {
        this.deficitSUM = deficitSUM;
    }

    public String getAreaPsicopeEnferm() {
        return areaPsicopeEnferm;
    }

    public void setAreaPsicopeEnferm(String areaPsicopeEnferm) {
        this.areaPsicopeEnferm = areaPsicopeEnferm;
    }

    public String getEstandarPsicopeEnferm() {
        return estandarPsicopeEnferm;
    }

    public void setEstandarPsicopeEnferm(String estandarPsicopeEnferm) {
        this.estandarPsicopeEnferm = estandarPsicopeEnferm;
    }

    public String getM2AlumnosPsicopeEnferm() {
        return m2AlumnosPsicopeEnferm;
    }

    public void setM2AlumnosPsicopeEnferm(String m2AlumnosPsicopeEnferm) {
        this.m2AlumnosPsicopeEnferm = m2AlumnosPsicopeEnferm;
    }

    public String getCumplimientoPsicopeEnferm() {
        return cumplimientoPsicopeEnferm;
    }

    public void setCumplimientoPsicopeEnferm(String cumplimientoPsicopeEnferm) {
        this.cumplimientoPsicopeEnferm = cumplimientoPsicopeEnferm;
    }

    public String getDeficitPsicopeEnferm() {
        return deficitPsicopeEnferm;
    }

    public void setDeficitPsicopeEnferm(String deficitPsicopeEnferm) {
        this.deficitPsicopeEnferm = deficitPsicopeEnferm;
    }

    public String getAreaServSanitarios() {
        return areaServSanitarios;
    }

    public void setAreaServSanitarios(String areaServSanitarios) {
        this.areaServSanitarios = areaServSanitarios;
    }

    public String getEstandarServSanitarios() {
        return estandarServSanitarios;
    }

    public void setEstandarServSanitarios(String estandarServSanitarios) {
        this.estandarServSanitarios = estandarServSanitarios;
    }

    public String getM2AlumnosServSanitarios() {
        return m2AlumnosServSanitarios;
    }

    public void setM2AlumnosServSanitarios(String m2AlumnosServSanitarios) {
        this.m2AlumnosServSanitarios = m2AlumnosServSanitarios;
    }

    public String getCumplimientoServSanitarios() {
        return cumplimientoServSanitarios;
    }

    public void setCumplimientoServSanitarios(String cumplimientoServSanitarios) {
        this.cumplimientoServSanitarios = cumplimientoServSanitarios;
    }

    public String getDeficitServSanitarios() {
        return deficitServSanitarios;
    }

    public void setDeficitServSanitarios(String deficitServSanitarios) {
        this.deficitServSanitarios = deficitServSanitarios;
    }

    public String getAreaCocinaCafe() {
        return areaCocinaCafe;
    }

    public void setAreaCocinaCafe(String areaCocinaCafe) {
        this.areaCocinaCafe = areaCocinaCafe;
    }

    public String getEstandarCocinaCafe() {
        return estandarCocinaCafe;
    }

    public void setEstandarCocinaCafe(String estandarCocinaCafe) {
        this.estandarCocinaCafe = estandarCocinaCafe;
    }

    public String getM2AlumnosCocinaCafe() {
        return m2AlumnosCocinaCafe;
    }

    public void setM2AlumnosCocinaCafe(String m2AlumnosCocinaCafe) {
        this.m2AlumnosCocinaCafe = m2AlumnosCocinaCafe;
    }

    public String getCumplimientoCocinaCafe() {
        return cumplimientoCocinaCafe;
    }

    public void setCumplimientoCocinaCafe(String cumplimientoCocinaCafe) {
        this.cumplimientoCocinaCafe = cumplimientoCocinaCafe;
    }

    public String getDeficitCocinaCafe() {
        return deficitCocinaCafe;
    }

    public void setDeficitCocinaCafe(String deficitCocinaCafe) {
        this.deficitCocinaCafe = deficitCocinaCafe;
    }

    public String getAreaOfAdm() {
        return areaOfAdm;
    }

    public void setAreaOfAdm(String areaOfAdm) {
        this.areaOfAdm = areaOfAdm;
    }

    public String getEstandarOfAdm() {
        return estandarOfAdm;
    }

    public void setEstandarOfAdm(String estandarOfAdm) {
        this.estandarOfAdm = estandarOfAdm;
    }

    public String getM2AlumnosOfAdm() {
        return m2AlumnosOfAdm;
    }

    public void setM2AlumnosOfAdm(String m2AlumnosOfAdm) {
        this.m2AlumnosOfAdm = m2AlumnosOfAdm;
    }

    public String getCumplimientoOfAdm() {
        return cumplimientoOfAdm;
    }

    public void setCumplimientoOfAdm(String cumplimientoOfAdm) {
        this.cumplimientoOfAdm = cumplimientoOfAdm;
    }

    public String getDeficitOfAdm() {
        return deficitOfAdm;
    }

    public void setDeficitOfAdm(String deficitOfAdm) {
        this.deficitOfAdm = deficitOfAdm;
    }

    public String getPropAreasCentrales() {
        return propAreasCentrales;
    }

    public void setPropAreasCentrales(String propAreasCentrales) {
        this.propAreasCentrales = propAreasCentrales;
    }

    public String getPropAreasApoyo() {
        return propAreasApoyo;
    }

    public void setPropAreasApoyo(String propAreasApoyo) {
        this.propAreasApoyo = propAreasApoyo;
    }

    public String getPropAreasAulas() {
        return propAreasAulas;
    }

    public void setPropAreasAulas(String propAreasAulas) {
        this.propAreasAulas = propAreasAulas;
    }

    public String getPropAreasLaboratorios() {
        return propAreasLaboratorios;
    }

    public void setPropAreasLaboratorios(String propAreasLaboratorios) {
        this.propAreasLaboratorios = propAreasLaboratorios;
    }

    public String getPropAreasCentroRecursosAprendizaje() {
        return propAreasCentroRecursosAprendizaje;
    }

    public void setPropAreasCentroRecursosAprendizaje(String propAreasCentroRecursosAprendizaje) {
        this.propAreasCentroRecursosAprendizaje = propAreasCentroRecursosAprendizaje;
    }

    public String getPropAreasTalleres() {
        return propAreasTalleres;
    }

    public void setPropAreasTalleres(String propAreasTalleres) {
        this.propAreasTalleres = propAreasTalleres;
    }

    public String getPropAreasExtension() {
        return propAreasExtension;
    }

    public void setPropAreasExtension(String propAreasExtension) {
        this.propAreasExtension = propAreasExtension;
    }

    public String getPropAreasServSanitarios() {
        return propAreasServSanitarios;
    }

    public void setPropAreasServSanitarios(String propAreasServSanitarios) {
        this.propAreasServSanitarios = propAreasServSanitarios;
    }

    public String getPropAreasAbastProcesa() {
        return propAreasAbastProcesa;
    }

    public void setPropAreasAbastProcesa(String propAreasAbastProcesa) {
        this.propAreasAbastProcesa = propAreasAbastProcesa;
    }

    public String getPropAreasConducAdministra() {
        return propAreasConducAdministra;
    }

    public void setPropAreasConducAdministra(String propAreasConducAdministra) {
        this.propAreasConducAdministra = propAreasConducAdministra;
    }

    public String getUtilizacion() {
        return utilizacion;
    }

    public void setUtilizacion(String utilizacion) {
        this.utilizacion = utilizacion;
    }

    public String getCuposTotal() {
        return cuposTotal;
    }

    public void setCuposTotal(String cuposTotal) {
        this.cuposTotal = cuposTotal;
    }

    public String getM2Requeridos() {
        return m2Requeridos;
    }

    public void setM2Requeridos(String m2Requeridos) {
        this.m2Requeridos = m2Requeridos;
    }

    public String getConstruiblePrimerPiso() {
        return construiblePrimerPiso;
    }

    public void setConstruiblePrimerPiso(String construiblePrimerPiso) {
        this.construiblePrimerPiso = construiblePrimerPiso;
    }

    public String getConstruibleAltura() {
        return construibleAltura;
    }

    public void setConstruibleAltura(String construibleAltura) {
        this.construibleAltura = construibleAltura;
    }

    public String getConstruibleTotal() {
        return construibleTotal;
    }

    public void setConstruibleTotal(String construibleTotal) {
        this.construibleTotal = construibleTotal;
    }

    public String getIdoneidad() {
        return idoneidad;
    }

    public void setIdoneidad(String idoneidad) {
        this.idoneidad = idoneidad;
    }

    public String getIndOcupacion() {
        return indOcupacion;
    }

    public void setIndOcupacion(String indOcupacion) {
        this.indOcupacion = indOcupacion;
    }

    public String getIndConstruccion() {
        return indConstruccion;
    }

    public void setIndConstruccion(String indConstruccion) {
        this.indConstruccion = indConstruccion;
    }

    public String getNumPrediosConRiesgos() {
        return numPrediosConRiesgos;
    }

    public void setNumPrediosConRiesgos(String numPrediosConRiesgos) {
        this.numPrediosConRiesgos = numPrediosConRiesgos;
    }

    public String getPoblacionAfectadaRiesgos() {
        return poblacionAfectadaRiesgos;
    }

    public void setPoblacionAfectadaRiesgos(String poblacionAfectadaRiesgos) {
        this.poblacionAfectadaRiesgos = poblacionAfectadaRiesgos;
    }

    public String getPoblacionTotal() {
        return poblacionTotal;
    }

    public void setPoblacionTotal(String poblacionTotal) {
        this.poblacionTotal = poblacionTotal;
    }

    public String getM2PrediosEstadoEstrucCritico() {
        return m2PrediosEstadoEstrucCritico;
    }

    public void setM2PrediosEstadoEstrucCritico(String m2PrediosEstadoEstrucCritico) {
        this.m2PrediosEstadoEstrucCritico = m2PrediosEstadoEstrucCritico;
    }

    public String getNumPrediosNoCumplenAula() {
        return numPrediosNoCumplenAula;
    }

    public void setNumPrediosNoCumplenAula(String numPrediosNoCumplenAula) {
        this.numPrediosNoCumplenAula = numPrediosNoCumplenAula;
    }

    public String getNumPrediosNoCumplenLabCiencias() {
        return numPrediosNoCumplenLabCiencias;
    }

    public void setNumPrediosNoCumplenLabCiencias(String numPrediosNoCumplenLabCiencias) {
        this.numPrediosNoCumplenLabCiencias = numPrediosNoCumplenLabCiencias;
    }

    public String getNumPrediosNoCumplenLabMultimedia() {
        return numPrediosNoCumplenLabMultimedia;
    }

    public void setNumPrediosNoCumplenLabMultimedia(String numPrediosNoCumplenLabMultimedia) {
        this.numPrediosNoCumplenLabMultimedia = numPrediosNoCumplenLabMultimedia;
    }

    public String getNumPrediosNoCumplenLabComputacion() {
        return numPrediosNoCumplenLabComputacion;
    }

    public void setNumPrediosNoCumplenLabComputacion(String numPrediosNoCumplenLabComputacion) {
        this.numPrediosNoCumplenLabComputacion = numPrediosNoCumplenLabComputacion;
    }

    public String getNumPrediosNoCumplenBiblioteca() {
        return numPrediosNoCumplenBiblioteca;
    }

    public void setNumPrediosNoCumplenBiblioteca(String numPrediosNoCumplenBiblioteca) {
        this.numPrediosNoCumplenBiblioteca = numPrediosNoCumplenBiblioteca;
    }

    public String getNumPrediosNoCumplenSalaMusica() {
        return numPrediosNoCumplenSalaMusica;
    }

    public void setNumPrediosNoCumplenSalaMusica(String numPrediosNoCumplenSalaMusica) {
        this.numPrediosNoCumplenSalaMusica = numPrediosNoCumplenSalaMusica;
    }

    public String getNumPrediosNoCumplenSalaArte() {
        return numPrediosNoCumplenSalaArte;
    }

    public void setNumPrediosNoCumplenSalaArte(String numPrediosNoCumplenSalaArte) {
        this.numPrediosNoCumplenSalaArte = numPrediosNoCumplenSalaArte;
    }

    public String getNumPrediosNoCumplenPlayonDepor() {
        return numPrediosNoCumplenPlayonDepor;
    }

    public void setNumPrediosNoCumplenPlayonDepor(String numPrediosNoCumplenPlayonDepor) {
        this.numPrediosNoCumplenPlayonDepor = numPrediosNoCumplenPlayonDepor;
    }

    public String getNumPrediosNoCumplenExpRecre() {
        return numPrediosNoCumplenExpRecre;
    }

    public void setNumPrediosNoCumplenExpRecre(String numPrediosNoCumplenExpRecre) {
        this.numPrediosNoCumplenExpRecre = numPrediosNoCumplenExpRecre;
    }

    public String getNumPrediosNoCumplenSUM() {
        return numPrediosNoCumplenSUM;
    }

    public void setNumPrediosNoCumplenSUM(String numPrediosNoCumplenSUM) {
        this.numPrediosNoCumplenSUM = numPrediosNoCumplenSUM;
    }

    public String getNumPrediosNoCumplenPsicopeEnferm() {
        return numPrediosNoCumplenPsicopeEnferm;
    }

    public void setNumPrediosNoCumplenPsicopeEnferm(String numPrediosNoCumplenPsicopeEnferm) {
        this.numPrediosNoCumplenPsicopeEnferm = numPrediosNoCumplenPsicopeEnferm;
    }

    public String getNumPrediosNoCumplenServSanitarios() {
        return numPrediosNoCumplenServSanitarios;
    }

    public void setNumPrediosNoCumplenServSanitarios(String numPrediosNoCumplenServSanitarios) {
        this.numPrediosNoCumplenServSanitarios = numPrediosNoCumplenServSanitarios;
    }

    public String getNumPrediosNoCumplenCocinaCafe() {
        return numPrediosNoCumplenCocinaCafe;
    }

    public void setNumPrediosNoCumplenCocinaCafe(String numPrediosNoCumplenCocinaCafe) {
        this.numPrediosNoCumplenCocinaCafe = numPrediosNoCumplenCocinaCafe;
    }

    public String getNumPrediosNoCumplenOfAdm() {
        return numPrediosNoCumplenOfAdm;
    }

    public void setNumPrediosNoCumplenOfAdm(String numPrediosNoCumplenOfAdm) {
        this.numPrediosNoCumplenOfAdm = numPrediosNoCumplenOfAdm;
    }

    public String getAislamientoTermicoPisos() {
        return aislamientoTermicoPisos;
    }

    public void setAislamientoTermicoPisos(String aislamientoTermicoPisos) {
        this.aislamientoTermicoPisos = aislamientoTermicoPisos;
    }

    public String getAislamientoTermicoMuros() {
        return aislamientoTermicoMuros;
    }

    public void setAislamientoTermicoMuros(String aislamientoTermicoMuros) {
        this.aislamientoTermicoMuros = aislamientoTermicoMuros;
    }

    public String getAislamientoTermicoVentanas() {
        return aislamientoTermicoVentanas;
    }

    public void setAislamientoTermicoVentanas(String aislamientoTermicoVentanas) {
        this.aislamientoTermicoVentanas = aislamientoTermicoVentanas;
    }

    public String getAislamientoTermicoCielos() {
        return aislamientoTermicoCielos;
    }

    public void setAislamientoTermicoCielos(String aislamientoTermicoCielos) {
        this.aislamientoTermicoCielos = aislamientoTermicoCielos;
    }

    public String getAislamientoTermicoOtros() {
        return aislamientoTermicoOtros;
    }

    public void setAislamientoTermicoOtros(String aislamientoTermicoOtros) {
        this.aislamientoTermicoOtros = aislamientoTermicoOtros;
    }

    public String getM2AmpliarCobertura() {
        return m2AmpliarCobertura;
    }

    public void setM2AmpliarCobertura(String m2AmpliarCobertura) {
        this.m2AmpliarCobertura = m2AmpliarCobertura;
    }

    public String getAlumnosNuevosInfraestructura() {
        return alumnosNuevosInfraestructura;
    }

    public void setAlumnosNuevosInfraestructura(String alumnosNuevosInfraestructura) {
        this.alumnosNuevosInfraestructura = alumnosNuevosInfraestructura;
    }
    
}
