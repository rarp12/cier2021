/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.tipo;

/**
 *
 * @author rarp1
 */
public enum Colores {

    check_lightgray("check_lightgray"),
    check_claro("check_claro"),
    check_powderblue("check_powderblue"),
    check_beige("check_beige"),
    check_lavender("check_lavender"),
    check_antiquewhite("check_antiquewhite"),
    check_mistyrose("check_mistyrose"),
    check_bluelavander("check_bluelavander"),
    check_amarillo("check_amarillo"),
    check_azul("check_azul"),
    check_rosa("check_rosa"),
    check_gris("check_gris"),
    check_naranja("check_naranja"),
    check_lila("check_lila"),
    check_verdeOscuro("check_verdeOscuro"),
    check_verde("check_verde");

    private Colores(String css) {
        this.css = css;
    }
    private String css;

    public String getCss() {
        return css;
    }

    public void setCss(String css) {
        this.css = css;
    }
}
