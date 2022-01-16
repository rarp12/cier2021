/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.fachada;

import co.stackpointer.cier.modelo.entidad.acceso.Usuario;
import co.stackpointer.cier.modelo.entidad.sistema.Idioma;
import co.stackpointer.cier.modelo.entidad.sistema.Parametro;
import co.stackpointer.cier.modelo.entidad.sistema.Tenant;
import co.stackpointer.cier.modelo.excepcion.ErrorGeneral;
import co.stackpointer.cier.modelo.excepcion.ErrorIntegridad;
import co.stackpointer.cier.modelo.auditoria.InterceptorAdministracion;
import co.stackpointer.cier.modelo.utilidad.PasswordGenerator;
import co.stackpointer.cier.modelo.utilidad.UtilEncriptar;
import co.stackpointer.cier.modelo.utilidad.UtilOut;
import co.stackpointer.cier.modelo.utilidad.UtilProperties;
import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeSet;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.sql.DataSource;

/**
 *
 * @author StackPointer
 */
@Stateless
public class PaisFacade implements PaisFacadeLocal {

    public static String EMAIL_USER = "";
    public static String EMAIL_PSWD = "";
    @PersistenceContext(unitName = "Model")
    private EntityManager em;
    private Object codigo;

    @PostConstruct
    public void inicializarParametros() {
        try {
            em = Persistence.createEntityManagerFactory("Model").createEntityManager();
        } catch (Exception ex) {
            String msjError = "Error PaisFacade:inicializarParametros. " + ex.getMessage();
            throw new ErrorGeneral("Error en PaisFacade", msjError);
        }
    }

    @Override
    public List<String> getTimeZones() {
        List<String> timeZones = new ArrayList<String>();
        String[] timeZonesDisponibles = TimeZone.getAvailableIDs();
        timeZones.addAll(Arrays.asList(timeZonesDisponibles));
        return timeZones;
    }

    @Override
    public LinkedHashMap<String, String> getLocales() {
        LinkedHashMap<String, String> locales = new LinkedHashMap<String, String>();
        Locale list[] = SimpleDateFormat.getAvailableLocales();
        Set set = new TreeSet();
        for (int i = 0; i < list.length; i++) {
            set.add(list[i].getDisplayName()
                    + ";" + list[i].toString());
        }
        Iterator it = set.iterator();
        while (it.hasNext()) {
            String[] l = it.next().toString().split(";");
            locales.put(l[0], l[1]);
        }
        return locales;
    }

    @Override
    public List<Idioma> getIdiomas() {
        List<Idioma> idiomas = new ArrayList<Idioma>();
        try {
            idiomas = em.createNamedQuery("Idioma.findAll")
                    .getResultList();
        } catch (Exception ex) {
            UtilOut.println(ex);
        }
        return idiomas;
    }

    @Override
    public List<Tenant> getPaises() {
        List<Tenant> paises = new ArrayList<Tenant>();
        try {
            paises = em.createNamedQuery("Tenant.findAll")
                    .getResultList();
        } catch (Exception ex) {
            String msjError = "Error PaisFacade:getPaises. " + ex.getMessage();
            throw new ErrorGeneral("Error en PaisFacade", msjError);
        }
        return paises;
    }

    @Override
    //@OperacionAuditable(tipo = TipoOperacion.PAIS_CREAR)
    public Tenant guardarPais(Tenant pais, Usuario operador) {
        try {
            pais.setCodigoPais(getCodigoPais(pais.getPais()));
            em.persist(pais);
            em.flush();
            setTenantNivel(pais.getId(), pais.getCodigoPais());
            //Guardamos el nivel pais (0)
            Integer nivelPais = Integer.valueOf(0);
            guardarNivel(pais, pais.getPais(), nivelPais);
            guardarTraduccion(pais.getId(), pais.getIdioma().getId(), pais.getPais(), nivelPais);
            return pais;
        } catch (Exception e) {
            throw new ErrorIntegridad("error al guardar el pais");
        }
    }

    @Override
    //@OperacionAuditable(tipo = TipoOperacion.PAIS_MODIFICACION)
    public void actualizarPais(Tenant pais, Usuario operador) {
        try {
            em.merge(pais);
            em.flush();
        } catch (Exception e) {
            throw new ErrorIntegridad("error al actualizar el pais");
        }
    }

    @Override
    public boolean existePais(String pais) {
        boolean sw = false;
        try {
            Tenant t = (Tenant) em.createNamedQuery("Tenant.porPais")
                    .setParameter("pais", pais)
                    .getSingleResult();
            if (t != null) {
                sw = true;
            }
        } catch (NoResultException nre) {
            return sw;
        } catch (Exception e) {
            String msjError = "Error PaisFacade:existePais. " + e.getMessage();
            throw new ErrorGeneral("Error en PaisFacade", msjError);
        }
        return sw;
    }

    @Override
    public boolean existeAbreviatura(String abreviatura) {
        boolean sw = false;
        try {
            Tenant t = (Tenant) em.createNamedQuery("Tenant.porAbreviatura")
                    .setParameter("abreviatura", abreviatura)
                    .getSingleResult();
            if (t != null) {
                sw = true;
            }
        } catch (NoResultException nre) {
            return sw;
        } catch (Exception e) {
            String msjError = "Error PaisFacade:existeAbreviatura. " + e.getMessage();
            throw new ErrorGeneral("Error en PaisFacade", msjError);
        }
        return sw;
    }

    @Override
    public List<String> getNombrePaisesAutocompletar(String texto, Long idioma) {
        List<String> nombrePaises = new ArrayList<String>();
        try {
            String sql = "select dpa.descripcion from "
                    + "dpa_niveles dpa "
                    + "where dpa.id_tnt is null "
                    + "and dpa.nivel_configuracion is null "
                    + "and dpa.padre is null "
                    + "and dpa.descripcion like '" + texto + "%'";
            nombrePaises = em.createNativeQuery(sql)
                    .setParameter("idioma", idioma)
                    .getResultList();
        } catch (Exception ex) {
            String msjError = "Error PaisFacade:getNombrePaisesAutocompletar. " + ex.getMessage();
            throw new ErrorGeneral("Error en PaisFacade", msjError);
        }
        return nombrePaises;
    }

    @Override
    public List<String> getDPA(Long tenant) {
        List<String> dpa = new ArrayList<String>();
        try {
            String sql = "select dpa.descripcion from dpa_configuracion_niveles dpa "
                    + "where dpa.id_tnt = ?tenant and estado = 'A' order by dpa.nivel asc";
            dpa = em.createNativeQuery(sql)
                    .setParameter("tenant", tenant)
                    .getResultList();
        } catch (Exception ex) {
            String msjError = "Error PaisFacade:getDPA(Long tenant). " + ex.getMessage();
            throw new ErrorGeneral("Error en PaisFacade", msjError);
        }
        return dpa;
    }

    @Override
    public List<String> getDPA(Long tenant, Long idIdioma) {
        List<String> dpa = new ArrayList<String>();
        try {
            String sql = "select coalesce(tb.descripcion,'-') "
                    + "from (select * from dpa_configuracion_niveles where id_tnt = ?tenant and estado = 'A') dpa "
                    + "left join sys_nivel_dpa_idioma tb on dpa.id_tnt = tb.id_tnt and dpa.nivel = tb.nivel "
                    + "and  tb.idioma = ?idIdioma "
                    + "order by dpa.nivel asc";
            dpa = em.createNativeQuery(sql)
                    .setParameter("tenant", tenant)
                    .setParameter("idIdioma", idIdioma)
                    .getResultList();
        } catch (Exception ex) {
            String msjError = "Error PaisFacade:getDPA(Long tenant, Long idIdioma). " + ex.getMessage();
            throw new ErrorGeneral("Error en PaisFacade", msjError);
        }
        return dpa;
    }

    @Override
    public void borrarDPA(Long tenant) throws Exception {
        try {
            String sql = "DELETE FROM dpa_configuracion_niveles dpa WHERE dpa.id_tnt = ?tenant";
            em.createNativeQuery(sql)
                    .setParameter("tenant", tenant)
                    .executeUpdate();

            sql = "DELETE FROM sys_nivel_dpa_idioma tb WHERE tb.id_tnt = ?tenant";
            em.createNativeQuery(sql)
                    .setParameter("tenant", tenant)
                    .executeUpdate();

        } catch (Exception e) {
            String msjError = "Error PaisFacade:borrarDPA. " + e.getMessage();
            throw new ErrorGeneral("Error en PaisFacade", msjError);
        }
    }

    @Override
    public void guardarNivel(Tenant tenant, String descripcion, Integer nivel) throws Exception {
        try {
            Object valor = em.createNativeQuery(UtilProperties.getProperties().getProperty("obtenerIdDpaConfiguracionNiveles")).getSingleResult();
            BigInteger idDpaConfiguracionNiveles = new BigInteger(valor.toString());

            String sql = "INSERT INTO dpa_configuracion_niveles(id, id_tnt, descripcion, estado, nivel) "
                    + "VALUES (" + idDpaConfiguracionNiveles + ", ?tenant, ?descripcion, 'A', ?nivel)";
            em.createNativeQuery(sql)
                    .setParameter("tenant", tenant.getId())
                    .setParameter("descripcion", descripcion)
                    .setParameter("nivel", nivel)
                    .executeUpdate();

            /*if (nivel.equals(0)) {
             sql = "UPDATE dpa_niveles "
             + "SET nivel_configuracion = (select id from dpa_configuracion_niveles where nivel=?nivel and id_tnt = ?tenant) "
             + "WHERE codigo = ?codigo";
             em.createNativeQuery(sql)
             .setParameter("nivel", nivel)
             .setParameter("tenant", tenant.getId())
             .setParameter("codigo", tenant.getCodigoPais())
             .executeUpdate();
             }else{*/
            sql = "UPDATE dpa_niveles "
                    + "SET nivel_configuracion = ?idDpaConfiguracionNiveles "
                    + "WHERE padre in "
                    + "(select n.id from dpa_niveles n"
                    + " inner join dpa_configuracion_niveles c on c.id=n.nivel_configuracion"
                    + " and c.nivel = ?nivelPadre and c.id_tnt = ?tenant)";
            em.createNativeQuery(sql)
                    .setParameter("idDpaConfiguracionNiveles", idDpaConfiguracionNiveles)
                    .setParameter("nivel", nivel)
                    .setParameter("nivelPadre", nivel - 1)
                    .setParameter("tenant", tenant.getId())
                    .setParameter("codigo", tenant.getCodigoPais())
                    .executeUpdate();
            //}
        } catch (Exception e) {
            String msjError = "Error PaisFacade:guardarNivel. " + e.getMessage();
            throw new ErrorIntegridad("Error al guardar la configuracion del nuevo país");
        }
    }

    @Override
    public void guardarTraduccion(Long tenant, Long idIdioma, String descripcion, Integer nivel) throws Exception {
        try {
            String sql = "INSERT INTO sys_nivel_dpa_idioma(id_tnt, nivel, idioma, descripcion) "
                    + "VALUES (?tenant, ?nivel, ?idIdioma, ?descripcion)";
            em.createNativeQuery(sql)
                    .setParameter("tenant", tenant)
                    .setParameter("nivel", nivel)
                    .setParameter("idIdioma", idIdioma)
                    .setParameter("descripcion", descripcion)
                    .executeUpdate();
        } catch (Exception e) {
            String msjError = "Error PaisFacade:guardarTraduccion. " + e.getMessage();
            throw new ErrorGeneral("Error en PaisFacade", msjError);
        }
    }

    @Override
    public void borrarTraducciones(Long tenant, Long idIdioma) throws Exception {
        try {
            String sql = "DELETE FROM sys_nivel_dpa_idioma tb WHERE tb.id_tnt = ?tenant"
                    + " and idioma=?idioma";
            em.createNativeQuery(sql)
                    .setParameter("tenant", tenant)
                    .setParameter("idioma", idIdioma)
                    .executeUpdate();

        } catch (Exception e) {
            String msjError = "Error PaisFacade:borrarTraducciones. " + e.getMessage();
            throw new ErrorGeneral("Error en PaisFacade", msjError);
        }
    }

    @Override
    public void guardarSuperUsuario(Usuario usuario, Long tenant) {
        String sql;
        try {
            //(Se elimina si existen el Super usuario y su Rol Administrador predeterminado)
            sql = "DELETE FROM aya_usuarios WHERE id_tnt = ?tenant";
            em.createNativeQuery(sql)
                    .setParameter("tenant", tenant)
                    .executeUpdate();
            sql = "DELETE FROM aya_roles WHERE nombre='ADMINISTRADOR' and id_tnt = ?tenant";
            em.createNativeQuery(sql)
                    .setParameter("tenant", tenant)
                    .executeUpdate();

            //Se crea el Super Usuario
            sql = "INSERT INTO aya_usuarios( "
                    + "id, id_tnt, identificacion, nombre_1, nombre_2, apellido_1, apellido_2, "
                    + "correo, usuario, password, estado, idioma) "
                    + "VALUES (" + UtilProperties.getProperties().getProperty("obtenerIdUsuario") + ", ?tenant, ?identificacion, ?nombre_1, ?nombre_2, ?apellido_1, ?apellido_2, "
                    + "?correo, ?usuario, ?password, 'A', ?idioma)";
            em.createNativeQuery(sql)
                    .setParameter("tenant", tenant)
                    .setParameter("identificacion", usuario.getIdentificacion())
                    .setParameter("nombre_1", usuario.getPrimerNombre())
                    .setParameter("nombre_2", usuario.getSegundoNombre())
                    .setParameter("apellido_1", usuario.getPrimerApellido())
                    .setParameter("apellido_2", usuario.getSegundoApellido())
                    .setParameter("correo", usuario.getCorreo())
                    .setParameter("usuario", usuario.getUsername())
                    .setParameter("password", usuario.getPassword())
                    .setParameter("idioma", usuario.getIdioma().getId())
                    .executeUpdate();

            //Se crea el rol Administrador con sus Funcionalidades
            sql = "INSERT INTO aya_roles(id, id_tnt, descripcion, nombre, estado) "
                    + "VALUES (" + UtilProperties.getProperties().getProperty("obtenerIdRol") + ", ?tenant, 'ROL ADMINISTRADOR DEL SISTEMA', 'ADMINISTRADOR', 'A')";
            em.createNativeQuery(sql)
                    .setParameter("tenant", tenant)
                    .executeUpdate();

            //obtenemos el id del SuperUsuario y del Rol
            sql = "SELECT id FROM aya_usuarios WHERE id_tnt = ?tenant";
            Long idUsuario = new Long(em.createNativeQuery(sql)
                    .setParameter("tenant", tenant)
                    .getSingleResult().toString());
            sql = "SELECT id FROM aya_roles WHERE nombre='ADMINISTRADOR' and id_tnt = ?tenant";
            Long idRol = new Long(em.createNativeQuery(sql)
                    .setParameter("tenant", tenant)
                    .getSingleResult().toString());

            //Se crean funcionalidades basicas del rol Administrador
            addFuncionalidad(tenant, idRol, "ADM_USUARIO", "ADM_USUARIO_CONSULTAR");
            addFuncionalidad(tenant, idRol, "ADM_USUARIO", "ADM_USUARIO_EDITAR");
            addFuncionalidad(tenant, idRol, "ADM_USUARIO", "ADM_USUARIO_AGREGAR");
            addFuncionalidad(tenant, idRol, "ADM_USUARIO", "ADM_USUARIO_ACTIVAR/DESACTIVAR");
            addFuncionalidad(tenant, idRol, "ADM_USUARIO", "ADM_USUARIO_ASIGNA_ROL");
            addFuncionalidad(tenant, idRol, "ADM_USUARIO", "ADM_ASIGNA_NIVEL_DPA");
            addFuncionalidad(tenant, idRol, "ADM_ROL", "ADM_ROL_CONSULTAR");
            addFuncionalidad(tenant, idRol, "ADM_ROL", "ADM_ROL_AGREGAR");
            addFuncionalidad(tenant, idRol, "ADM_ROL", "ADM_ROL_EDITAR");
            addFuncionalidad(tenant, idRol, "ADM_ROL", "ADM_ROL_ELIMINAR");

            //relacionamos el Super Usuario con el Rol Administrador            
            sql = "INSERT INTO aya_usuarios_roles(id, id_tnt, id_usuario, id_rol, estado) "
                    + "VALUES (" + UtilProperties.getProperties().getProperty("obtenerIdRol") + ", ?tenant, ?idUsuario, ?idRol, 'A')";
            em.createNativeQuery(sql)
                    .setParameter("tenant", tenant)
                    .setParameter("idUsuario", idUsuario)
                    .setParameter("idRol", idRol)
                    .executeUpdate();

        } catch (Exception e) {
            String msjError = "Error PaisFacade:guardarSuperUsuario " + e.getMessage();
            throw new ErrorIntegridad("Error en PaisFacade", msjError);
        }
    }

    @Override
    public String getEmailAdministrador(Integer tenant) throws Exception {
        Connection conn = null;
        Statement st2 = null;
        ResultSet rs = null;
        String email;
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("jdbc/DBCierDS");
            conn = ds.getConnection();
            String sql = "select valor from sys_parametros where codigo = 'EMAIL_SOPORTE'";
            st2 = conn.createStatement();
            rs = st2.executeQuery(sql);
            rs.next();
            email = rs.getString(1);
            return email;
        } catch (Exception e) {
            throw new Exception();
        } finally {
            rs.close();
            st2.close();
            conn.close();
        }
    }

    @Override
    public String getEmailUsuario(Integer tenant, String usuario) throws Exception {
        Connection conn = null;
        Statement st2 = null;
        ResultSet rs = null;
        String email;
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("jdbc/DBCierDS");
            conn = ds.getConnection();
            String sql = "select correo from aya_usuarios where usuario = '" + usuario + "' and id_tnt = '" + tenant + "'";
            st2 = conn.createStatement();
            rs = st2.executeQuery(sql);
            rs.next();
            email = rs.getString(1);
            return email;
        } catch (Exception e) {
            return null;
        } finally {
            rs.close();
            st2.close();
            conn.close();
        }
    }

    @Override
    public void resetearSecuencias() throws Exception {
        Connection conn = null;
        CallableStatement cs = null;
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("jdbc/DBCierDS");
            conn = ds.getConnection();
            cs = conn.prepareCall(UtilProperties.getProperties().getProperty("resetearSecuencias"));
            //Ejecución del procedimiento
            cs.execute();
        } catch (Exception e) {
            String msjError = "Error PaisFacade:resetearSecuencias " + e.getMessage();
            throw new ErrorGeneral("Error en PaisFacade", msjError);
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (cs != null) {
                cs.close();
            }
        }
    }

    private void addFuncionalidad(Long tenant, Long idRol, String modulo, String funcionalidad) throws Exception {
        try {
            String sql = "INSERT INTO aya_roles_permisos(id, id_rol, id_tnt, cod_modulo, cod_funcionalidad) "
                    + "VALUES (" + UtilProperties.getProperties().getProperty("obtenerIdFuncionalidad") + ", ?idRol, ?tenant, ?modulo, ?funcionalidad)";
            em.createNativeQuery(sql)
                    .setParameter("tenant", tenant)
                    .setParameter("idRol", idRol)
                    .setParameter("modulo", modulo)
                    .setParameter("funcionalidad", funcionalidad)
                    .executeUpdate();
        } catch (Exception e) {
            throw new Exception();
        }
    }

    private String getCodigoPais(String descripcion, Long idioma) throws Exception {
        String codigo = null;
        try {
            String sql = "select dpa.codigo  "
                    + "from dpa_niveles dpa  "
                    + "inner join sys_dpa_paises_idioma p  "
                    + "on p.id_dpa_nivel=dpa.id  "
                    + "and id_idioma=?idioma  "
                    + "and p.descripcion = ?descripcion  "
                    + "where dpa.padre is null";
            codigo = (String) em.createNativeQuery(sql)
                    .setParameter("idioma", idioma)
                    .setParameter("descripcion", descripcion)
                    .getSingleResult();
        } catch (Exception e) {
            throw new Exception();
        }
        return codigo;
    }

    private String getCodigoPais(String descripcion) throws Exception {
        String codigo = null;
        try {
            String sql = "select dpa.codigo  "
                    + "from dpa_niveles dpa  "
                    + " where  dpa.descripcion = ?descripcion  "
                    + "and dpa.padre is null";
            codigo = (String) em.createNativeQuery(sql)
                    .setParameter("descripcion", descripcion)
                    .getSingleResult();
        } catch (Exception e) {
            throw new Exception();
        }
        return codigo;
    }

    private void setTenantNivel(Long tenant, String codigo) throws Exception {
        try {
            String sql = "UPDATE dpa_niveles SET id_tnt=?tenant where codigo = ?codigo";
            em.createNativeQuery(sql)
                    .setParameter("tenant", tenant)
                    .setParameter("codigo", codigo)
                    .executeUpdate();
        } catch (Exception e) {
            throw new Exception();
        }
    }

    @Override
    public boolean reestablecePassword(Integer tenant, String username) throws Exception {
        boolean value = false;
        try {
            String sql = "select restablecer from aya_usuarios where id_tnt = ?tenant and usuario=?usuario";
            Object resultado = em.createNativeQuery(sql)
                    .setParameter("tenant", tenant)
                    .setParameter("usuario", username)
                    .getSingleResult();
            //value = Integer.parseInt(resultado.toString());
            value = resultado.toString().equals("1") ? true : false;
        } catch (Exception e) {
            throw new Exception();
        }
        return value;
    }

    @Override
    public String generarPasswordTemporal(Integer tenant, String username) {
        String password = PasswordGenerator.getPassword(
                PasswordGenerator.MINUSCULAS
                + PasswordGenerator.MAYUSCULAS
                + PasswordGenerator.ESPECIALES, 10);
        String pswd = password;        
        password = UtilEncriptar.getStringMessageDigest(password, UtilEncriptar.SHA256);
        String sql = "update aya_usuarios SET restablecer = 1,password = ?password  where id_tnt = ?tenant and usuario=?usuario";
        em.createNativeQuery(sql)
                .setParameter("password", password)
                .setParameter("tenant", tenant)
                .setParameter("usuario", username)
                .executeUpdate();
        return pswd;
    }

    @Override
    public Properties getEmailConfig() throws Exception {
        Properties prop = new Properties();
        prop.put("mail.from", em.createNamedQuery("Parametro.consultarPorCodigo", Parametro.class)
                .setParameter("codigo", "EMAIL_CONFIG_USER").getSingleResult().getValor());
        prop.put("mail.user", em.createNamedQuery("Parametro.consultarPorCodigo", Parametro.class)
                .setParameter("codigo", "EMAIL_CONFIG_USER").getSingleResult().getValor());
        prop.put("mail.password", em.createNamedQuery("Parametro.consultarPorCodigo", Parametro.class)
                .setParameter("codigo", "EMAIL_CONFIG_PSWD").getSingleResult().getValor());
        prop.put("mail.smtp.host", em.createNamedQuery("Parametro.consultarPorCodigo", Parametro.class)
                .setParameter("codigo", "EMAIL_SMTP_HOST").getSingleResult().getValor());
        prop.put("mail.smtp.port", em.createNamedQuery("Parametro.consultarPorCodigo", Parametro.class)
                .setParameter("codigo", "EMAIL_SMTP_PORT").getSingleResult().getValor());
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS
        return prop;
    }
}
