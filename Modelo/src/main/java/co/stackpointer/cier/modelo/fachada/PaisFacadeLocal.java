/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.fachada;

import co.stackpointer.cier.modelo.entidad.acceso.Usuario;
import co.stackpointer.cier.modelo.entidad.dpa.Nivel;
import co.stackpointer.cier.modelo.entidad.sistema.Idioma;
import co.stackpointer.cier.modelo.entidad.sistema.Tenant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;
import javax.ejb.Local;

/**
 *
 * @author StackPointer
 */
@Local
public interface PaisFacadeLocal {

    public List<String> getTimeZones();

    public LinkedHashMap<String, String> getLocales();

    public List<Idioma> getIdiomas();

    public List<Tenant> getPaises();

    public Tenant guardarPais(Tenant pais, Usuario operador);

    public void actualizarPais(Tenant pais, Usuario operador);

    public boolean existePais(String pais);

    public boolean existeAbreviatura(String abreviatura);

    public List<String> getNombrePaisesAutocompletar(String texto, Long idioma);

    public List<String> getDPA(Long tenant);

    public List<String> getDPA(Long tenant, Long idIdioma);

    public void guardarNivel(Tenant tenant, String descripcion, Integer nivel) throws Exception;

    public void guardarTraduccion(Long tenant, Long idIdioma, String descripcion, Integer nivel) throws Exception;

    public void borrarTraducciones(Long tenant, Long idIdioma) throws Exception;

    public void borrarDPA(Long tenant) throws Exception;

    public void guardarSuperUsuario(Usuario usuario, Long tenant);

    public String getEmailAdministrador(Integer tenant) throws Exception;

    public String getEmailUsuario(Integer tenant, String usuario) throws Exception;

    public void resetearSecuencias() throws Exception;

    public boolean reestablecePassword(Integer tenant, String username) throws Exception;

    public String generarPasswordTemporal(Integer tenant, String username);

    public Properties getEmailConfig() throws Exception;
}
