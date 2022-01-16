/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.tipo;

/**
 *
 * @author user
 */
public enum EspacioFuncional {

    AULA("enum.espacioFuncional.AULA"),
LABORATORIO_CIENCIAS("enum.espacioFuncional.LABORATORIO_CIENCIAS"),
LABORATORIO_MULTIMEDIAL("enum.espacioFuncional.LABORATORIO_MULTIMEDIAL"),
LABORATORIO_COMPUTACION("enum.espacioFuncional.LABORATORIO_COMPUTACION"),
BIBLIOTECA("enum.espacioFuncional.BIBLIOTECA"),
SALA_MUSICA("enum.espacioFuncional.SALA_MUSICA"),
SALA_ARTES("enum.espacioFuncional.SALA_ARTES"),
EXPANSIONES_RECREATIVAS("enum.espacioFuncional.EXPANSIONES_RECREATIVAS"),
SALON_USOS_MULTIPLES("enum.espacioFuncional.SALON_USOS_MULTIPLES"),
PSICOPEDAGOGIA_ENFERMERIA("enum.espacioFuncional.PSICOPEDAGOGIA_ENFERMERIA"),
SERVICIOS_SANITARIOS("enum.espacioFuncional.SERVICIOS_SANITARIOS"),
OFICINA_ADMINISTRACION("enum.espacioFuncional.OFICINA_ADMINISTRACION"),
ALIMENTOS("enum.espacioFuncional.ALIMENTOS"),
PLAYON_DEPORTIVO("enum.espacioFuncional.PLAYON_DEPORTIVO");
    
    
        
    private String etiqueta;

    private EspacioFuncional(String desc) {
        this.etiqueta = desc;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }
}
