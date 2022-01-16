/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.fachada;

import java.sql.Connection;
import java.sql.Statement;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 *
 * @author rjay1
 */
public class GenerarEspaciosOracle extends Thread {

    private String respuesta;
    private boolean sw;
    private int estadoJob;
    private TenantPersistenceManager tpm;

    @Override
    public void run() {
        try {
            Integer tenant = tpm.getCurrentTenant().intValue();
            preprararDatos(tenant);

            sw = true;
            while (sw) {
                Thread.sleep(5500);
                estadoJob = getEstadoJob(tenant);
                if (estadoJob != 1) {
                    sw = false;
                }
            }
            if (estadoJob == 2) {
                respuesta = "NO";
            } else if (estadoJob == 3) {
                respuesta = "ERROR";
            } else {
                respuesta = "OK";
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
                    + "dbms_job.submit(job => nuJob, what => 'begin digitacion_espacios.crear_resgistros_espacios(" + tenant + "); end;'); "
                    + "commit; "
                    + "end;";
            Statement st2 = conn.createStatement();
            st2.execute(sql);
            st2.close();
        } catch (Exception e) {
            throw new Exception();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    private int getEstadoJob(Integer tenant) {
        String sql = "SELECT valor FROM ind_control_generacion WHERE codigo='IND_ESTADO_GENER_ESPA' AND id_tnt = " + tenant;
        Integer result = new Integer(
                tpm
                .getEm().createNativeQuery(sql)
                .getSingleResult().toString());
        return result.intValue();
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public boolean isSw() {
        return sw;
    }

    public void setSw(boolean sw) {
        this.sw = sw;
    }

    public TenantPersistenceManager getTpm() {
        return tpm;
    }

    public void setTpm(TenantPersistenceManager tpm) {
        this.tpm = tpm;
    }
}