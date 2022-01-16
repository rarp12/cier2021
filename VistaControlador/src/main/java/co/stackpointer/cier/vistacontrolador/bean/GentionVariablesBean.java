/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.bean;

import co.stackpointer.cier.modelo.entidad.digitado.RespuestaDig;
import co.stackpointer.cier.modelo.entidad.establecimiento.Establecimiento;
import co.stackpointer.cier.modelo.entidad.establecimiento.Predio;
import co.stackpointer.cier.modelo.entidad.establecimiento.Sede;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author StackPointer
 */
@ManagedBean(name = "gestionVariable")
@SessionScoped
public class GentionVariablesBean {
    
    private String moduloOrigen;
    
    Predio predioSeleccionado;
    Sede sedeSeleccionada;
    Establecimiento estSeleccionado;
    private String idInstrumentoDigitado;
    private RespuestaDig respuesta;

    public String getModuloOrigen() {
        return moduloOrigen;
    }

    public void setModuloOrigen(String moduloOrigen) {
        this.moduloOrigen = moduloOrigen;
    }
    
    
    String instrumentoConsultado;

    public String getInstrumentoConsultado() {
        return instrumentoConsultado;
    }

    public void setInstrumentoConsultado(String instrumentoConsultado) {
        this.instrumentoConsultado = instrumentoConsultado;
    }

    public Predio getPredioSeleccionado() {
        return predioSeleccionado;
    }

    public void setPredioSeleccionado(Predio predioSeleccionado) {
        this.predioSeleccionado = predioSeleccionado;
    }

    public Sede getSedeSeleccionada() {
        return sedeSeleccionada;
    }

    public void setSedeSeleccionada(Sede sedeSeleccionada) {
        this.sedeSeleccionada = sedeSeleccionada;
    }

    public Establecimiento getEstSeleccionado() {
        return estSeleccionado;
    }

    public void setEstSeleccionado(Establecimiento estSeleccionado) {
        this.estSeleccionado = estSeleccionado;
    }

    public String getIdInstrumentoDigitado() {
        return idInstrumentoDigitado;
    }

    public void setIdInstrumentoDigitado(String idInstrumentoDigitado) {
        this.idInstrumentoDigitado = idInstrumentoDigitado;
    }
    
    public String prepararDigitacion(String idInstrumentoDigitado) {
        this.idInstrumentoDigitado = idInstrumentoDigitado;
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("digitarInstrumentoEstatico");
        return "/instrumento/estatico/digitar.xhtml?faces-redirect=true";
    }

    public RespuestaDig getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(RespuestaDig respuesta) {
        this.respuesta = respuesta;
    }
  
    
}
