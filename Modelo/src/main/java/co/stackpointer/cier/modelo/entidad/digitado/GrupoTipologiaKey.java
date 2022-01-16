/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.entidad.digitado;

/**
 *
 * @author rjay1
 */
public class GrupoTipologiaKey {

    private String codTipologia;
    private String codigo;
    private Integer idioma;

    public GrupoTipologiaKey(String codTipologia, String codigo, Integer idioma) {
        this.codTipologia = codTipologia;
        this.codigo = codigo;
        this.idioma = idioma;
    }

    public String getCodTipologia() {
        return codTipologia;
    }

    public void setCodTipologia(String codTipologia) {
        this.codTipologia = codTipologia;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Integer getIdioma() {
        return idioma;
    }

    public void setIdioma(Integer idioma) {
        this.idioma = idioma;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (this.codTipologia != null ? this.codTipologia.hashCode() : 0);
        hash = 79 * hash + (this.codigo != null ? this.codigo.hashCode() : 0);
        hash = 79 * hash + (this.idioma != null ? this.idioma.hashCode() : 0);
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
        final GrupoTipologiaKey other = (GrupoTipologiaKey) obj;
        if ((this.codTipologia == null) ? (other.codTipologia != null) : !this.codTipologia.equals(other.codTipologia)) {
            return false;
        }
        if ((this.codigo == null) ? (other.codigo != null) : !this.codigo.equals(other.codigo)) {
            return false;
        }
        if (this.idioma != other.idioma && (this.idioma == null || !this.idioma.equals(other.idioma))) {
            return false;
        }
        return true;
    }
}