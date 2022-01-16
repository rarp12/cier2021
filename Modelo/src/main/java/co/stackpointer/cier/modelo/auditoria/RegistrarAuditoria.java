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
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class RegistrarAuditoria extends Thread {

    static final Logger LOGGER = Logger.getLogger(RegistrarAuditoria.class.getName());
    private String nombreMetodo;
    private String usuario;
    private Date fecha;
    private static final Map<String, BitacoraTransaccion> metodoToAccion;

    static {
        Map<String, BitacoraTransaccion> mapData = new HashMap<String, BitacoraTransaccion>();
        mapData.put("login", BitacoraTransaccion.LOGIN);
        mapData.put("logout", BitacoraTransaccion.LOGOUT);
        mapData.put("GENERAR_INDICADORES", BitacoraTransaccion.GENERAR_INDICADORES);
        mapData.put("actualizarUsuario", BitacoraTransaccion.EDITAR_USUARIO);
        mapData.put("guardarUsuarioRol", BitacoraTransaccion.EDITAR_USUARIO);
        mapData.put("CONSULTAR_USUARIO", BitacoraTransaccion.CONSULTAR_USUARIO);
        mapData.put("guardarUsuario", BitacoraTransaccion.CREAR_USUARIO);
        mapData.put("CONSULTAR_ROL", BitacoraTransaccion.CONSULTAR_ROL);
        mapData.put("guardarRol", BitacoraTransaccion.CREAR_ROL);
        mapData.put("actualizarRol", BitacoraTransaccion.EDITAR_ROL);
        mapData.put("eliminarRol", BitacoraTransaccion.ELIMINAR_ROL);
        mapData.put("crearTipologiaValor", BitacoraTransaccion.CREAR_TIPOLOGIA);
        mapData.put("EDITAR_TIPOLOGIA", BitacoraTransaccion.EDITAR_TIPOLOGIA);
        mapData.put("modificarTipologiaValor", BitacoraTransaccion.ELIMINAR_TIPOLOGIA);
        mapData.put("guardarDpa", BitacoraTransaccion.CREAR_DPA);
        mapData.put("actualizarDpa", BitacoraTransaccion.EDITAR_DPA);
        mapData.put("eliminarDpa", BitacoraTransaccion.ELIMINAR_DPA);        
        mapData.put("guardarEstablecimiento", BitacoraTransaccion.CREAR_ESTABLECIMIENTO);
        mapData.put("actualizarEstablecimiento", BitacoraTransaccion.EDITAR_ESTABLECIMIENTO);
        mapData.put("eliminarEstablecimiento", BitacoraTransaccion.ELIMINAR_ESTABLECIMIENTO);        
        mapData.put("guardarSede", BitacoraTransaccion.CREAR_SEDE);
        mapData.put("actualizarSede", BitacoraTransaccion.EDITAR_SEDE);
        mapData.put("eliminarSede", BitacoraTransaccion.ELIMINAR_SEDE);        
        mapData.put("guardarPredio", BitacoraTransaccion.CREAR_PREDIO);
        mapData.put("actualizarPredio", BitacoraTransaccion.EDITAR_PREDIO);
        mapData.put("eliminarPredio", BitacoraTransaccion.ELIMINAR_PREDIO);
        mapData.put("guardarNivelConfiguracion", BitacoraTransaccion.CREAR_NIVEL_CONFIGURACION);
        mapData.put("actualizarNivelConfiguracion", BitacoraTransaccion.EDITAR_NIVEL_CONFIGURACION);
        mapData.put("eliminarNivelConfiguracion", BitacoraTransaccion.ELIMINAR_NIVEL_CONFIGURACION);
        mapData.put("BACKUP_DPA", BitacoraTransaccion.BACKUP_DPA);
        mapData.put("CONSULTA_CALIFICACION_AMBITO", BitacoraTransaccion.CONSULTA_CALIFICACION_AMBITO);
        mapData.put("EDITAR_CALIFICACION_AMBITO", BitacoraTransaccion.EDITAR_CALIFICACION_AMBITO);
        mapData.put("CONSULTA_VARIABLE_AMBITO", BitacoraTransaccion.CONSULTA_VARIABLE_AMBITO);
        mapData.put("EDITAR_VARIABLE_AMBITO", BitacoraTransaccion.EDITAR_VARIABLE_AMBITO);
        mapData.put("CONSULTA_ESTANDARES", BitacoraTransaccion.CONSULTA_ESTANDARES);
        mapData.put("CONSULTA_PARAMETROS_CONSTRUCCION", BitacoraTransaccion.CONSULTA_PARAMETROS_CONSTRUCCION);
        mapData.put("CREAR_PARAMETROS_CONSTRUCCION", BitacoraTransaccion.CREAR_PARAMETROS_CONSTRUCCION);
        mapData.put("EDITAR_PARAMETROS_CONSTRUCCION", BitacoraTransaccion.EDITAR_PARAMETROS_CONSTRUCCION);
        mapData.put("ELIMINAR_PARAMETROS_CONSTRUCCION", BitacoraTransaccion.ELIMINAR_PARAMETROS_CONSTRUCCION);
        mapData.put("EDITAR_ESTANDARES", BitacoraTransaccion.EDITAR_ESTANDARES);
        mapData.put("/parametros/estandares/lote/consultar.xhtml", BitacoraTransaccion.CONSULTA_ESTANDAR_LOTE);
        mapData.put("guardarEstandar", BitacoraTransaccion.CREAR_ESTANDAR_LOTE);
        mapData.put("actualizarEstandar", BitacoraTransaccion.EDITAR_ESTANDAR_LOTE);
        mapData.put("eliminarEstandar", BitacoraTransaccion.ELIMINAR_ESTANDAR_LOTE);
        mapData.put("/parametros/estandares/cocina/consultar.xhtml", BitacoraTransaccion.CONSULTAR_ESTANDAR_COCINA);
        mapData.put("guardarEstandarCocina", BitacoraTransaccion.CREAR_ESTANDAR_COCINA);
        mapData.put("actualizarEstandarCocina", BitacoraTransaccion.EDITAR_ESTANDAR_COCINA);
        mapData.put("eliminarEstandarCocina", BitacoraTransaccion.ELIMINAR_ESTANDAR_COCINA);        
        mapData.put("CONSULTAR_UNIDADES_FUNCIONALES", BitacoraTransaccion.CONSULTAR_UNIDADES_FUNCIONALES);
        mapData.put("guardarUnidad", BitacoraTransaccion.CREAR_UNIDADES_FUNCIONALES);
        mapData.put("actualizarUnidad", BitacoraTransaccion.EDITAR_UNIDADES_FUNCIONALES);        
        mapData.put("CONSULTA_PRIORIZACION_UNIDADES_FUNCIONALES", BitacoraTransaccion.CONSULTA_PRIORIZACION_UNIDADES_FUNCIONALES);
        mapData.put("EDITAR_PRIORIZACION_UNIDADES_FUNCIONALES", BitacoraTransaccion.EDITAR_PRIORIZACION_UNIDADES_FUNCIONALES);
        mapData.put("CONSULTA_PRIORIZACION_SERVICIOS", BitacoraTransaccion.CONSULTA_PRIORIZACION_SERVICIOS);
        mapData.put("EDITAR_PRIORIZACION_SERVICIOS", BitacoraTransaccion.EDITAR_PRIORIZACION_SERVICIOS);
        mapData.put("CONSULTA_COSTO_CONSTRUCCION", BitacoraTransaccion.CONSULTA_COSTO_CONSTRUCCION);
        mapData.put("EDITAR_COSTO_CONSTRUCCION", BitacoraTransaccion.EDITAR_COSTO_CONSTRUCCION);
        mapData.put("/consultas/ambito/generalidades.xhtml", BitacoraTransaccion.CONSULTAR_GENERALIDADES);
        mapData.put("/consultas/ambito/riesgosNaturales.xhtml", BitacoraTransaccion.CONSULTAR_RIESGO_NATURALES);
        mapData.put("/consultas/ambito/accesibilidad.xhtml", BitacoraTransaccion.CONSULTAR_ACCESIBILIDAD);
        mapData.put("/consultas/ambito/accesibilidadInterna.xhtml", BitacoraTransaccion.CONSULTAR_ACC_INTERNA);
        mapData.put("/consultas/ambito/cocina.xhtml", BitacoraTransaccion.CONSULTAR_AMBIENTE_COCINA);
        mapData.put("/consultas/ambito/comedor.xhtml", BitacoraTransaccion.CONSULTAR_AMBIENTE_COMEDOR);
        mapData.put("CONSULTAR_AMBIENTE_AULA", BitacoraTransaccion.CONSULTAR_AMBIENTE_AULA);
        mapData.put("CONSULTAR_AMBIENTE_BIBLIOTECA", BitacoraTransaccion.CONSULTAR_AMBIENTE_BIBLIOTECA);
        mapData.put("CONSULTAR_AMBIENTE_EXPANSIONES_RECREATIVAS", BitacoraTransaccion.CONSULTAR_AMBIENTE_EXPANSIONES_RECREATIVAS);
        mapData.put("CONSULTAR_AMBIENTE_LABORATORIO_CIENCIAS", BitacoraTransaccion.CONSULTAR_AMBIENTE_LABORATORIO_CIENCIAS);
        mapData.put("CONSULTAR_AMBIENTE_LABORATORIO_COMPUTACION", BitacoraTransaccion.CONSULTAR_AMBIENTE_LABORATORIO_COMPUTACION);
        mapData.put("CONSULTAR_AMBIENTE_LABORATORIO_MULTIMEDIAL", BitacoraTransaccion.CONSULTAR_AMBIENTE_LABORATORIO_MULTIMEDIAL);
        mapData.put("CONSULTAR_AMBIENTE_OFICINA_ADMINISTRACION", BitacoraTransaccion.CONSULTAR_AMBIENTE_OFICINA_ADMINISTRACION);
        mapData.put("CONSULTAR_AMBIENTE_PLAYON_DEPORTIVO", BitacoraTransaccion.CONSULTAR_AMBIENTE_PLAYON_DEPORTIVO);
        mapData.put("CONSULTAR_AMBIENTE_PSICOPEDAGOGIA_ENFERMERIA", BitacoraTransaccion.CONSULTAR_AMBIENTE_PSICOPEDAGOGIA_ENFERMERIA);
        mapData.put("CONSULTAR_AMBIENTE_SALA_ARTES", BitacoraTransaccion.CONSULTAR_AMBIENTE_SALA_ARTES);
        mapData.put("CONSULTAR_AMBIENTE_SALA_MUSICA", BitacoraTransaccion.CONSULTAR_AMBIENTE_SALA_MUSICA);
        mapData.put("CONSULTAR_AMBIENTE_SALON_USOS_MULTIPLES", BitacoraTransaccion.CONSULTAR_AMBIENTE_SALON_USOS_MULTIPLES);
        mapData.put("CONSULTAR_AMBIENTE_SERVICIOS_SANITARIOS", BitacoraTransaccion.CONSULTAR_AMBIENTE_SERVICIOS_SANITARIOS);        
        mapData.put("CONSULTAR_SERVICIO_AGUA", BitacoraTransaccion.CONSULTAR_SERVICIO_AGUA);  
        mapData.put("CONSULTAR_SERVICIO_ENERGIA_ELECTRICA", BitacoraTransaccion.CONSULTAR_SERVICIO_ENERGIA_ELECTRICA);
        mapData.put("CONSULTAR_SERVICIO_GAS", BitacoraTransaccion.CONSULTAR_SERVICIO_GAS);
        mapData.put("CONSULTAR_SERVICIO_INTERNET", BitacoraTransaccion.CONSULTAR_SERVICIO_INTERNET);  
        mapData.put("CONSULTAR_SERVICIO_RECOLECCION_BASURAS", BitacoraTransaccion.CONSULTAR_SERVICIO_RECOLECCION_BASURAS);
        mapData.put("CONSULTAR_SERVICIO_RED_ALCANTARILLADO", BitacoraTransaccion.CONSULTAR_SERVICIO_RED_ALCANTARILLADO);
        mapData.put("CONSULTAR_SERVICIO_RED_PLUVIAL", BitacoraTransaccion.CONSULTAR_SERVICIO_RED_PLUVIAL);  
        mapData.put("CONSULTAR_SERVICIO_SISTEMA_RECICLAJE", BitacoraTransaccion.CONSULTAR_SERVICIO_SISTEMA_RECICLAJE);
        mapData.put("CONSULTAR_SERVICIO_TELEFONO", BitacoraTransaccion.CONSULTAR_SERVICIO_TELEFONO);
        mapData.put("/consultas/ambito/ampliacion.xhtml", BitacoraTransaccion.CONSULTAR_AMPLIACION);
        mapData.put("/consultas/ambito/confort.xhtml", BitacoraTransaccion.CONSULTAR_CONFORT);
        mapData.put("/consultas/ambito/controlVigilancia.xhtml", BitacoraTransaccion.CONSULTAR_CONTROL_VIGILANCIA);
        mapData.put("/consultas/ambito/edificios.xhtml", BitacoraTransaccion.CONSULTAR_EDIFICIOS);
        mapData.put("/consultas/ambito/espacios.xhtml", BitacoraTransaccion.CONSULTAR_ESPACIOS);
        mapData.put("/consultas/ambito/propiedad.xhtml", BitacoraTransaccion.CONSULTAR_PROPIEDAD);
        mapData.put("/consultas/ambito/redistribucion.xhtml", BitacoraTransaccion.CONSULTAR_REDISTRIBUCION);
        mapData.put("/consultas/ambito/riesgosAntropicos.xhtml", BitacoraTransaccion.CONSULTAR_RIESGOS_ANTROPICOS);
        mapData.put("/consultas/ambito/seguridad.xhtml", BitacoraTransaccion.CONSULTAR_SEGURIDAD);
        mapData.put("/consultas/ambito/serviciosConsolidado.xhtml", BitacoraTransaccion.CONSULTAR_SERVICIOS_CONSOLIDADOS);
        mapData.put("/consultas/ambito/sostenibilidad.xhtml", BitacoraTransaccion.CONSULTAR_SOSTENIBILIDAD);     
        mapData.put("/consultas/calificacion/consultaCalificacion.xhtml", BitacoraTransaccion.CONSULTAR_CALIFICACION);
        mapData.put("/consultas/dinamica/consultaDinamica.xhtml", BitacoraTransaccion.CONSULTAR_DINAMICA);
        mapData.put("/consultas/simulacion/diagnosticoGeneral.xhtml", BitacoraTransaccion.CONSULTAR_DIAGNOSTICO_GENERAL);
        metodoToAccion = Collections.unmodifiableMap(mapData);
    }

    public void run() {
        try {
            BitacoraTransaccion tipo_operacion = metodoToAccion.get(nombreMetodo);

            insertBitacora(tipo_operacion.getModulo().name(),
                    tipo_operacion.getOperacion().name(),
                    tipo_operacion.getOpcion().name());
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

            String sql = UtilProperties.getProperties().getProperty("insertBitacoraNotDig");

            st2 = conn.prepareStatement(sql);
            st2.setString(1, usuario);
            st2.setTimestamp(2, convertToSqlDate(fecha));
            st2.setString(3, modulo);
            st2.setString(4, tipoAccion);
            st2.setString(5, opcion);
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

    public String getNombreMetodo() {
        return nombreMetodo;
    }

    public void setNombreMetodo(String nombreMetodo) {
        this.nombreMetodo = nombreMetodo;
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
}
