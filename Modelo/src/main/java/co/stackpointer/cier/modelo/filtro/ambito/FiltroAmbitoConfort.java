/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.filtro.ambito;

/**
 *
 * @author user
 */
public class FiltroAmbitoConfort extends FiltroAmbito {

    private Integer condicionIluminacionNatural;
    private Integer condicionVentilacionNatural;
    private Integer condicionAcustica;
    private Integer condicionIluminacionArtificial;
    private Integer condicionDucha;
    private Integer condicionLavamano;
    private Integer condicionInodoro;
    private Integer condicionOrinal;

    public void inicializar() {
        super.init();
        condicionIluminacionNatural = null;
        condicionVentilacionNatural = null;
        condicionAcustica = null;
        condicionIluminacionArtificial = null;
        condicionDucha = null;
        condicionLavamano = null;
        condicionInodoro = null;
        condicionOrinal = null;
    }

    public Integer getCondicionIluminacionNatural() {
        return condicionIluminacionNatural;
    }

    public void setCondicionIluminacionNatural(Integer condicionIluminacionNatural) {
        this.condicionIluminacionNatural = condicionIluminacionNatural;
    }

    public Integer getCondicionVentilacionNatural() {
        return condicionVentilacionNatural;
    }

    public void setCondicionVentilacionNatural(Integer condicionVentilacionNatural) {
        this.condicionVentilacionNatural = condicionVentilacionNatural;
    }

    public Integer getCondicionAcustica() {
        return condicionAcustica;
    }

    public void setCondicionAcustica(Integer condicionAcustica) {
        this.condicionAcustica = condicionAcustica;
    }

    public Integer getCondicionIluminacionArtificial() {
        return condicionIluminacionArtificial;
    }

    public void setCondicionIluminacionArtificial(Integer condicionIluminacionArtificial) {
        this.condicionIluminacionArtificial = condicionIluminacionArtificial;
    }

    public Integer getCondicionDucha() {
        return condicionDucha;
    }

    public void setCondicionDucha(Integer condicionDucha) {
        this.condicionDucha = condicionDucha;
    }

    public Integer getCondicionLavamano() {
        return condicionLavamano;
    }

    public void setCondicionLavamano(Integer condicionLavamano) {
        this.condicionLavamano = condicionLavamano;
    }

    public Integer getCondicionInodoro() {
        return condicionInodoro;
    }

    public void setCondicionInodoro(Integer condicionInodoro) {
        this.condicionInodoro = condicionInodoro;
    }

    public Integer getCondicionOrinal() {
        return condicionOrinal;
    }

    public void setCondicionOrinal(Integer condicionOrinal) {
        this.condicionOrinal = condicionOrinal;
    }

    @Override
    public boolean isConsultaDeTiempoReal() {
        return codZona != null || codSector != null
                || condicionIluminacionNatural != null
                || condicionVentilacionNatural != null
                || condicionAcustica != null
                || condicionIluminacionArtificial != null
                || condicionDucha != null
                || condicionLavamano != null
                || condicionInodoro != null
                || condicionOrinal != null;
    }
}
