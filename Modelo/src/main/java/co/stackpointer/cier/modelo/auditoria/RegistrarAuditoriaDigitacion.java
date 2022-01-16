/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.auditoria;

import co.stackpointer.cier.modelo.tipo.BitacoraTransaccion;
import static co.stackpointer.cier.modelo.utilidad.UtilFecha.convertToSqlDate;
import co.stackpointer.cier.modelo.utilidad.UtilProperties;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 *
 * @author rarp1
 */
public class RegistrarAuditoriaDigitacion extends Thread {

    static final Logger LOGGER = Logger.getLogger(RegistrarAuditoriaDigitacion.class.getName());
    private BitacoraTransaccion bitacoraTransaccion;
    private String moduloDigitacion;
    private String boletaCensal;
    private String usuario;
    private Date fecha;

    public void run() {
        try {
            insertBitacora(bitacoraTransaccion.getModulo().name(),
                    bitacoraTransaccion.getOperacion().name(),
                    bitacoraTransaccion.getOpcion().name());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "EL registro de bitacora devuelve el error: {0}", ex.getMessage());
        }
    }

    private void insertBitacora(String modulo, String tipoAccion, String opcion) {
        Connection conn = null;
        PreparedStatement st2 = null;
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("jdbc/DBCierDS");
            conn = ds.getConnection();

            String sql = UtilProperties.getProperties().getProperty("insertBitacoraDig");

            st2 = conn.prepareStatement(sql);
            st2.setString(1, usuario);
            st2.setTimestamp(2,convertToSqlDate(fecha));
            st2.setString(3, modulo);
            st2.setString(4, tipoAccion);
            st2.setString(5, opcion);
            st2.setString(6, boletaCensal);
            st2.setString(7, moduloDigitacion);
            st2.executeUpdate();

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "EL registro de bitacora devuelve el error: {0}", e.getMessage());
        } finally {
            try {
                conn.clearWarnings();
                conn.close();
            } catch (Exception e2) {
                LOGGER.log(Level.SEVERE, "EL registro de bitacora devuelve el error: {0}", e2.getMessage());
            }
        }
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BitacoraTransaccion getBitacoraTransaccion() {
        return bitacoraTransaccion;
    }

    public void setBitacoraTransaccion(BitacoraTransaccion bitacoraTransaccion) {
        this.bitacoraTransaccion = bitacoraTransaccion;
    }

    public String getModuloDigitacion() {
        return moduloDigitacion;
    }

    public void setModuloDigitacion(String moduloDigitacion) {
        this.moduloDigitacion = moduloDigitacion;
    }

    public String getBoletaCensal() {
        return boletaCensal;
    }

    public void setBoletaCensal(String boletaCensal) {
        this.boletaCensal = boletaCensal;
    }
}
