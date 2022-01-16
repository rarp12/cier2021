/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.fachada;

import co.stackpointer.cier.modelo.entidad.acceso.Funcionalidad;
import co.stackpointer.cier.modelo.entidad.acceso.Modulo;
import co.stackpointer.cier.modelo.entidad.acceso.Rol;
import co.stackpointer.cier.modelo.entidad.acceso.RolPermiso;
import co.stackpointer.cier.modelo.entidad.acceso.Usuario;
import co.stackpointer.cier.modelo.entidad.acceso.UsuarioRol;
import co.stackpointer.cier.modelo.entidad.dpa.Nivel;
import co.stackpointer.cier.modelo.tipo.Estado;
import co.stackpointer.cier.modelo.tipo.GrupoModulo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author StackPointer
 */
@Local
public interface AccesoFacadeLocal {
    
    public Usuario getUsuarioPorUsername(String username);

    public boolean permitirAccesoAurl(String usuario, String url);    
    
    public List<Modulo> modulosPorUsuario(String usuario);
    
    public List<String> descripcionModulosPorGrupo(GrupoModulo grupoModulo);
    
    public List<Funcionalidad> getFuncionalidades(Estado estado);
    
    public List<Funcionalidad> funcionalidadPORmoduloYusuario(String codmodulo, String usuario);
    
    public List<Funcionalidad> funcionalidadesPorGrupo(GrupoModulo grupoModulo);

    public List<Funcionalidad> funcionalidadesPorDescripcion(String descripcion);
    
    public RolPermiso getRolPermiso(Funcionalidad funcionalidad, Rol rol);
    
    public RolPermiso guardarRolPermiso(RolPermiso rolPermiso, Usuario operador);
    
    public void eliminarRolPermiso(RolPermiso rolPermiso, Usuario operador);
    
    public List<Usuario> getUsuarios();
    
    public void actualizarUsuario(String usernameSesion, Usuario usuario);
    
    public List<Rol> getRoles();
    
    public List<Rol> rolesAsignados(Usuario usuario);
    
    public List<Rol> rolesNoAsignados(Usuario usuario);
    
    public boolean estaAsignadoElRol(Usuario usuario, Rol rol);
    
    public UsuarioRol getUsuarioRol(Usuario usuario, Rol rol); 
            
    public UsuarioRol guardarUsuarioRol(String usernameSesion, UsuarioRol usuarioRol);
    
    public void actualizarUsuarioRol(UsuarioRol usuarioRol, Usuario operador);    
    
    public List<Nivel> getNivelesPorPadre(Nivel padre);
    
    public boolean existeUsername(String username);
    
    public Usuario guardarUsuario(String usernameSesion, Usuario usuario);
    
    public boolean existeNombreRol(String nombre);
    
    public Rol guardarRol (String usernameSesion, Rol rol);
    
    public void actualizarRol (String usernameSesion, Rol rol);
    
    public boolean tieneUsuarios (Rol rol);
     
    public void eliminarRol (String usernameSesion, Rol rol);
}
