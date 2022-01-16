/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.consulta.generarIndicadores;

import co.stackpointer.cier.modelo.entidad.dpa.Nivel;
import co.stackpointer.cier.modelo.fachada.GenerarIndicadorOracle;
import co.stackpointer.cier.modelo.fachada.GenerarIndicadorPostgres;
import co.stackpointer.cier.modelo.fachada.TenantPersistenceManager;
import co.stackpointer.cier.modelo.tipo.ParamBaseDatos;
import co.stackpointer.cier.modelo.utilidad.UtilProperties;
import co.stackpointer.cier.vista.Utilidades;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ApplicationScoped;
import org.omnifaces.util.Messages;

/**
 *
 * @author StackPointer
 */
@ManagedBean(name = "indicador")
@ApplicationScoped
public class IndicadorBean implements Serializable {

    private boolean generando;
    private GenerarIndicadorOracle generarIndicadorOracle;
    private GenerarIndicadorPostgres generarIndicadorPostgres;
    private List<String> logs;
    @EJB
    private TenantPersistenceManager tpm;

    public IndicadorBean() {
    }

    public void generarIndicadores(Nivel nivel) {
        try {
            logs = new ArrayList<String>();
            if (ParamBaseDatos.ORACLE.equals(UtilProperties.getDbMotor())) {
                generarParaOracle(nivel);
            } else {
                generarParaPostgres(nivel);
            }
            generando = true;
            Messages.addFlashGlobalInfo(Utilidades.obtenerMensaje("administracion.generar.indicadores.inicio"));
        } catch (Exception e) {
            Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"));
        }
    }

    public void verificarGeneracion() {
        try {
            if (ParamBaseDatos.ORACLE.equals(UtilProperties.getDbMotor())) {
                verificarParaOracle();
            } else {
                verificarParaPostgres();
            }
            //this.logs = generarIndicadorOracle.getLogs();
        } catch (Exception e) {
            generando = false;
            Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"));
        }
    }

    private void generarParaOracle(Nivel nivel) {
        generarIndicadorOracle = new GenerarIndicadorOracle();
        generarIndicadorOracle.setTpm(tpm);
        if (nivel != null) {
            generarIndicadorOracle.setNivel(nivel);
        }
        generarIndicadorOracle.start();
    }

    private void generarParaPostgres(Nivel nivel) {
        generarIndicadorPostgres = new GenerarIndicadorPostgres();
        generarIndicadorPostgres.setTenantActual(tpm.getCurrentTenant());
        generarIndicadorPostgres.setEm(tpm.getEm());
        if (nivel != null) {
            generarIndicadorPostgres.setNivel(nivel);
        }
        generarIndicadorPostgres.start();
    }

    private void verificarParaOracle() {
        if (!generarIndicadorOracle.isAlive()) { //resultado.isDone
            String res = generarIndicadorOracle.getRespuesta(); //resultado.get();
            generando = false;
            if (res.equalsIgnoreCase("NO")) {
                Messages.addFlashGlobalInfo(Utilidades.obtenerMensaje("administracion.generar.indicadores.datosProcesar"));
            } else {
                if (res.equalsIgnoreCase("ERROR")) {
                    Messages.addFlashGlobalError(Utilidades.obtenerMensaje("administracion.generar.indicadores.error"));
                } else {
                    Messages.addFlashGlobalInfo(Utilidades.obtenerMensaje("administracion.generar.indicadores.terminado", res));
                }
            }
        } else {
            Messages.addFlashGlobalInfo(Utilidades.obtenerMensaje("administracion.generar.indicadores.noFinalizado"));
        }
    }

    private void verificarParaPostgres() {
        if (!generarIndicadorPostgres.isAlive()) { //resultado.isDone
            String res = generarIndicadorPostgres.getRespuesta(); //resultado.get();
            generando = false;
            if (res.equalsIgnoreCase("NO")) {
                Messages.addFlashGlobalInfo(Utilidades.obtenerMensaje("administracion.generar.indicadores.datosProcesar"));
            } else {
                if (res.equalsIgnoreCase("ERROR")) {
                    Messages.addFlashGlobalError(Utilidades.obtenerMensaje("administracion.generar.indicadores.error"));
                } else {
                    Messages.addFlashGlobalInfo(Utilidades.obtenerMensaje("administracion.generar.indicadores.terminado", res));
                }
            }
        } else {
            Messages.addFlashGlobalInfo(Utilidades.obtenerMensaje("administracion.generar.indicadores.noFinalizado"));
        }
    }

    public boolean isGenerando() {
        return generando;
    }

    public List<String> getLogs() {
        return logs;
    }

    public void setLogs(List<String> logs) {
        this.logs = logs;
    }
}
