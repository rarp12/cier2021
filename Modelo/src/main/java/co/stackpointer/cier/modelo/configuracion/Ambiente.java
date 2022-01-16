/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.configuracion;

/**
 *
 * @author mrodriguez
 */
public enum Ambiente {

    DEV("Desarrollo"),
    TEST("Prueba"),
    PROD("Productivo");
    private String desc;

    private Ambiente(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
