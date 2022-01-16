/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.digitacion.espacios;

import co.stackpointer.cier.modelo.entidad.digitado.Grupo;
import co.stackpointer.cier.modelo.fachada.DigitacionEspacioFacadeLocal;
import co.stackpointer.cier.modelo.fachada.GenerarEspaciosOracle;
import co.stackpointer.cier.modelo.fachada.GenerarEspaciosPostgres;
import co.stackpointer.cier.modelo.fachada.TenantPersistenceManager;
import co.stackpointer.cier.modelo.tipo.ParamBaseDatos;
import co.stackpointer.cier.modelo.utilidad.UtilProperties;
import co.stackpointer.cier.vista.Utilidades;
import static co.stackpointer.cier.vista.Utilidades.mostrarMensajeError;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import org.omnifaces.util.Messages;

/**
 *
 * @author rjay1
 */
@ManagedBean(name = "digEspGen")
@ApplicationScoped
public class GeneracionBean implements Serializable {

    private GenerarEspaciosOracle generarEspaciosOracle;
    private GenerarEspaciosPostgres generarEspaciosPostgres;
    private List<Grupo> grupos;
    private List<String> select;
    private boolean generando;
    private boolean selectAll;
    @EJB
    DigitacionEspacioFacadeLocal fachada;
    @EJB
    private TenantPersistenceManager tpm;

    @PostConstruct
    public void init() {
        grupos = fachada.buscarTodosGrupos();
    }

    public void generar() {
         
        if (!select.isEmpty()){
            try {
                fachada.crearGrupoGeneracion(select);
                if (ParamBaseDatos.ORACLE.equals(UtilProperties.getDbMotor())) {
                    generarParaOracle();
                } else {
                    generarParaPostgres();
                }
                Messages.addFlashGlobalInfo(Utilidades.obtenerMensaje("digitacion.espacios.generacion.inicio"));
                generando = true;
            } catch (Exception e) {
                mostrarMensajeError(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion") + " ", e.getMessage());
            }
        }else
           mostrarMensajeError(Utilidades.obtenerMensaje("digitacion.espacios.generacion.form.campos.seleccionar"),""); 
    }

    public void verificarGeneracion() {
        try {
            if (ParamBaseDatos.ORACLE.equals(UtilProperties.getDbMotor())) {
                verificarParaOracle();
            } else {
                verificarParaPostgres();
            }
        } catch (Exception e) {
            mostrarMensajeError(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion") + " ", e.getMessage());
        }
    }

    public void selectAllItems() {
        select = new ArrayList<String>();
        if (this.selectAll) {
            for (Grupo grp : grupos) {
                select.add(grp.getId() + "");
            }
        }
    }

    private void generarParaOracle() {
        generarEspaciosOracle = new GenerarEspaciosOracle();
        generarEspaciosOracle.setTpm(tpm);
        generarEspaciosOracle.start();
    }

    private void generarParaPostgres() {
        generarEspaciosPostgres = new GenerarEspaciosPostgres();
        generarEspaciosPostgres.setTpm(tpm);
        generarEspaciosPostgres.start();
    }

    private void verificarParaOracle() {
        if (!generarEspaciosOracle.isAlive()) { //resultado.isDone
            String res = generarEspaciosOracle.getRespuesta(); //resultado.get();
            select = new ArrayList<String>();
            selectAll = false;
            generando = false;
            if (res.equalsIgnoreCase("NO")) {
                Messages.addFlashGlobalInfo(Utilidades.obtenerMensaje("digitacion.espacios.generacion.datosProcesar"));
            } else {
                if (res.equalsIgnoreCase("ERROR")) {
                    Messages.addFlashGlobalError(Utilidades.obtenerMensaje("digitacion.espacios.generacion.error"));
                } else {
                    Messages.addFlashGlobalInfo(Utilidades.obtenerMensaje("digitacion.espacios.generacion.terminado"));
                }
            }
        } else {
            Messages.addFlashGlobalInfo(Utilidades.obtenerMensaje("digitacion.espacios.generacion.noFinalizado"));
        }
    }

    private void verificarParaPostgres() {
        if (!generarEspaciosPostgres.isAlive()) { //resultado.isDone
            String res = generarEspaciosPostgres.getRespuesta(); //resultado.get();
            select = new ArrayList<String>();
            selectAll = false;
            generando = false;
            if (res.equalsIgnoreCase("NO")) {
                Messages.addFlashGlobalInfo(Utilidades.obtenerMensaje("digitacion.espacios.generacion.datosProcesar"));
            } else {
                if (res.equalsIgnoreCase("ERROR")) {
                    Messages.addFlashGlobalError(Utilidades.obtenerMensaje("digitacion.espacios.generacion.error"));
                } else {
                    Messages.addFlashGlobalInfo(Utilidades.obtenerMensaje("digitacion.espacios.generacion.terminado"));
                }
            }
        } else {
            Messages.addFlashGlobalInfo(Utilidades.obtenerMensaje("digitacion.espacios.generacion.noFinalizado"));
        }
    }

    public List<Grupo> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<Grupo> grupos) {
        this.grupos = grupos;
    }

    public List<String> getSelect() {
        return select;
    }

    public void setSelect(List<String> select) {
        this.select = select;
    }

    public boolean isGenerando() {
        return generando;
    }

    public void setGenerando(boolean generando) {
        this.generando = generando;
    }

    public boolean isSelectAll() {
        return selectAll;
    }

    public void setSelectAll(boolean selectAll) {
        this.selectAll = selectAll;
    }
}