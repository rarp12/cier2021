/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.fachada;

import co.stackpointer.cier.modelo.entidad.dpa.Nivel;
import co.stackpointer.cier.modelo.utilidad.UtilCadena;
import java.sql.Connection;
import java.sql.Statement;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 *
 * @author user
 */
public class GenerarIndicadorOracle extends Thread {

    private String respuesta;
    private Nivel nivel;
    private boolean sw;
    private int estadoJob;
    //private List<String> logs;
   
    private TenantPersistenceManager tpm;

    public void run() {
        try {
            Integer tenant = tpm.getCurrentTenant().intValue();
            if (UtilCadena.isNullOVacio(nivel)) {
                preprararDatos(tenant);
            } else {
                preprararDatos(tenant, nivel);
            }

            sw = true;
            while (sw) {
                Thread.sleep(5500);
                estadoJob = getEstadoJob();
                if (estadoJob != 1) {
                    sw = false;
                }
            }
            if (estadoJob == 2) {
                respuesta = "NO";
            } else if (estadoJob == 3) {
                respuesta = "ERROR";
            } else {
                respuesta = getPeriodoGenerado();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            respuesta = "ERROR";
        }
    }

    public void preprararDatos(Integer tenant) throws Exception {
        Connection conn = null;
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("jdbc/DBCierDS");
            conn = ds.getConnection();

            String sql = "DECLARE "
                    + "nuJob NUMBER; "
                    + "begin "
                    + "dbms_job.submit(job => nuJob, what => 'begin indicadores.generar_indicadores(" + tenant + ", null, null); end;'); "
                    + "commit; "
                    + "end;";
            Statement st2 = conn.createStatement();
            st2.execute(sql);
            st2.close();
        } catch (Exception e) {
            throw new Exception();
        } finally {
            conn.close();
        }
    }

    public void preprararDatos(Integer tenant, Nivel nivel) throws Exception {
        Connection conn = null;
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("jdbc/DBCierDS");
            conn = ds.getConnection();

            String sql = "DECLARE "
                    + "nuJob NUMBER; "
                    + "begin "
                    + "dbms_job.submit(job => nuJob, what => "
                    + "'begin indicadores.generar_indicadores(" + tenant + "," + nivel.getConfiguracion().getNivel() + "," + nivel.getId() + "); end;'); "
                    + "commit; "
                    + "end;";
            Statement st2 = conn.createStatement();
            st2.execute(sql);
            st2.close();
        } catch (Exception e) {
            throw new Exception();
        } finally {
            conn.close();
        }
    }

    private int getEstadoJob() {
        String sql = "select valor from ind_control_generacion where codigo='IND_ESTADO_GENERACION'";
        Integer result = new Integer(
                tpm
                .getEm().createNativeQuery(sql)
                .getSingleResult().toString());
        return result.intValue();
    }

    private String getPeriodoGenerado() {
        String sql = "select valor from ind_control_generacion where codigo='ULTIMO_PERIODO_CALCULADO'";
        String result = tpm
                .getEm().createNativeQuery(sql)
                .getSingleResult().toString();
        return result;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }    
    
    public TenantPersistenceManager getTpm() {
        return tpm;
    }

    public void setTpm(TenantPersistenceManager tpm) {
        this.tpm = tpm;
    }
    
    /*public List<String> getLogs() {
     return logs;
     }

     public void setLogs(List<String> logs) {
     this.logs = logs;
     }*/
}
