/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.tipo;

/**
 *
 * @author rarp1
 */
public enum UnidadFuncional {
    
    AULA("AULA","consulta.consolidado.espacios.unidad.funcional.aula","red"),
    LABORATORIO_CIENCIAS("LABORATORIO_CIENCIAS","consulta.consolidado.espacios.unidad.funcional.laboratorio_ciencias","black"),
    LABORATORIO_MULTIMEDIAL("LABORATORIO_MULTIMEDIAL","consulta.consolidado.espacios.unidad.funcional.laboratorio_multimedial","red"),
    LABORATORIO_COMPUTACION("LABORATORIO_COMPUTACION","consulta.consolidado.espacios.unidad.funcional.laboratorio_computacion","pink"),
    BIBLIOTECA("BIBLIOTECA","consulta.consolidado.espacios.unidad.funcional.biblioteca","red"),
    SALA_MUSICA("SALA_MUSICA","consulta.consolidado.espacios.unidad.funcional.sala_musica","red"),
    SALA_ARTES("SALA_ARTES","consulta.consolidado.espacios.unidad.funcional.sala_artes","red"),
    EXPANSIONES_RECREATIVAS("EXPANSIONES_RECREATIVAS","consulta.consolidado.espacios.unidad.funcional.expansiones_recreativas","red"),
    SALON_USOS_MULTIPLES("SALON_USOS_MULTIPLES","consulta.consolidado.espacios.unidad.funcional.salon_usos_multiples","red"),
    PSICOPEDAGOGIA_ENFERMERIA("PSICOPEDAGOGIA_ENFERMERIA","consulta.consolidado.espacios.unidad.funcional.psicopedagogia_enfermeria","red"),
    SERVICIOS_SANITARIOS("SERVICIOS_SANITARIOS","consulta.consolidado.espacios.unidad.funcional.servicios_sanitarios","red"),
    OFICINA_ADMINISTRACION("OFICINA_ADMINISTRACION","consulta.consolidado.espacios.unidad.funcional.oficina_administracion","red"),
    ALIMENTOS("ALIMENTOS","consulta.consolidado.espacios.unidad.funcional.alimentos","red"),
    PLAYON_DEPORTIVO("PLAYON_DEPORTIVO","consulta.consolidado.espacios.unidad.funcional.playon_deportivo","yellow"),
    COCINA("COCINA","consulta.consolidado.espacios.unidad.funcional.cocina","white"),
    COMEDOR("COMEDOR","consulta.consolidado.espacios.unidad.funcional.comedor","blue");
    
    private String codigo;
    private String etiqueta;
    private String color;

    private UnidadFuncional(String codigo,String etiqueta,String color) {
        this.codigo = codigo;
        this.etiqueta = etiqueta;
        this.color = color;
    }
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
   
    
    
}
