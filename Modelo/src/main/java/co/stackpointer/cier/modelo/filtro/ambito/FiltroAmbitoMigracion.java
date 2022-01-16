package co.stackpointer.cier.modelo.filtro.ambito;

public class FiltroAmbitoMigracion extends FiltroAmbito{   
    private boolean generalidades;
    private boolean riesgos;
    private boolean ctrlVigilancia;
    private boolean seguridad;
    private boolean accesibilidad;
    private boolean confort;
    private boolean propiedad;
    private boolean oferta;
    
    public void inicializar() {
        super.init();        
    }

    public boolean isGeneralidades() {
        return generalidades;
    }

    public void setGeneralidades(boolean generalidades) {
        this.generalidades = generalidades;
    }

    public boolean isRiesgos() {
        return riesgos;
    }

    public void setRiesgos(boolean riesgos) {
        this.riesgos = riesgos;
    }

    public boolean isCtrlVigilancia() {
        return ctrlVigilancia;
    }

    public void setCtrlVigilancia(boolean ctrlVigilancia) {
        this.ctrlVigilancia = ctrlVigilancia;
    }

    public boolean isSeguridad() {
        return seguridad;
    }

    public void setSeguridad(boolean seguridad) {
        this.seguridad = seguridad;
    }

    public boolean isAccesibilidad() {
        return accesibilidad;
    }

    public void setAccesibilidad(boolean accesibilidad) {
        this.accesibilidad = accesibilidad;
    }

    public boolean isConfort() {
        return confort;
    }

    public void setConfort(boolean confort) {
        this.confort = confort;
    }

    public boolean isPropiedad() {
        return propiedad;
    }

    public void setPropiedad(boolean propiedad) {
        this.propiedad = propiedad;
    }

    public boolean isOferta() {
        return oferta;
    }

    public void setOferta(boolean oferta) {
        this.oferta = oferta;
    }
    
    @Override
    public boolean isConsultaDeTiempoReal() {
        return true;
    }

}
