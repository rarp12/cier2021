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
import co.stackpointer.cier.modelo.excepcion.ErrorGeneral;
import co.stackpointer.cier.modelo.excepcion.ErrorIntegridad;
import co.stackpointer.cier.modelo.excepcion.ErrorValidacion;
import co.stackpointer.cier.modelo.auditoria.InterceptorAdministracion;
import co.stackpointer.cier.modelo.auditoria.InterceptorConsultas;
import co.stackpointer.cier.modelo.tipo.Estado;
import co.stackpointer.cier.modelo.tipo.GrupoModulo;
import co.stackpointer.cier.modelo.utilidad.UtilOut;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

/**
 *
 * @author StackPointer
 */
@Stateless
public class AccesoFacade implements AccesoFacadeLocal {  
    
    @Inject
    private TenantPersistenceManager tpm;
    
    @Override
    public Usuario getUsuarioPorUsername(String username) {
        try {        
            return (Usuario) tpm.getEm().createNamedQuery("Usuario.porUsername")
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ErrorGeneral("UsuarioSesion", "No se pudo obtener datos de sesion");
        }
    }

    @Override
    //interceptor para ambitos de consulta
    @Interceptors({InterceptorConsultas.class})
    public boolean permitirAccesoAurl(String usuario, String url) {
        boolean sw = false;
        try {
            if (url.contains("cocina.xhtml")) url=url.replace("cocina.xhtml", "ambiente.xhtml");
            if (url.contains("comedor.xhtml")) url=url.replace("comedor.xhtml", "ambiente.xhtml");
            if (usuario.equalsIgnoreCase("jguerra") && url.equalsIgnoreCase("/parametros/dpa/backup.xhtml")) {
                sw=true;
            } else {
                // @NamedQuery(name = "RolPermiso.permitORdeny", query = "SELECT rp.codFuncionalidad FROM RolPermiso rp, UsuarioRol ur WHERE rp.idRol.id = ur.idRol.id AND ur.idUsuario.usuario = :username AND rp.codModulo.url = :url")
                String activo = "co.stackpointer.cier.modelo.tipo.Estado.A";
                String hql = "SELECT rp.funcionalidad FROM RolPermiso rp, UsuarioRol ur "
                        + "WHERE rp.rol.id = ur.rol.id "
                        + "AND rp.rol.estado=" + activo + " AND rp.modulo.estado=" + activo
                        + " AND rp.funcionalidad.estado=" + activo + " AND ur.estado=" + activo
                        + " AND ur.usuario.username = :username "
                        + "AND rp.modulo.url = :url";
                List<Funcionalidad> funcionalidades = tpm.getEm().createQuery(hql)
                        .setParameter("username", usuario)
                        .setParameter("url", url)
                        .getResultList();
                sw = !funcionalidades.isEmpty();
            }
        } catch (Exception ex) {
            UtilOut.println(ex);
        }
        return sw;
    }

    @Override
    public List<Modulo> modulosPorUsuario(String usuario) {
        List<Modulo> modulos = new ArrayList<Modulo>();
        try {
            modulos = tpm.getEm().createNamedQuery("RolPermiso.modulosPorUsername")
                    .setParameter("username", usuario)
                    .getResultList();
        } catch (Exception ex) {
            UtilOut.println(ex);
        }
        return modulos;
    }
    
    @Override
    public List<String> descripcionModulosPorGrupo(GrupoModulo grupoModulo) {
        List<String> modulos = new ArrayList<String>();
        try {
            modulos = tpm.getEm().createNamedQuery("Modulo.descripcionByGrupo")
                    .setParameter("grupo", grupoModulo)
                    .getResultList();
        } catch (Exception ex) {
            UtilOut.println(ex);
        }
        return modulos;
    }

    @Override
    public List<Funcionalidad> getFuncionalidades(Estado estado) {
        List<Funcionalidad> funcionalidades = new ArrayList<Funcionalidad>();
        try {
            funcionalidades = tpm.getEm().createNamedQuery("Funcionalidad.porEstado")
                    .setParameter("estado", estado)
                    .getResultList();
        } catch (Exception ex) {
            UtilOut.println(ex);
        }
        return funcionalidades;
    }

    @Override
    public List<Funcionalidad> funcionalidadPORmoduloYusuario(String codmodulo, String usuario) {
        List<Funcionalidad> funcionalidades = new ArrayList<Funcionalidad>();
        try {
            funcionalidades = tpm.getEm().createNamedQuery("RolPermiso.funcionalidadesPorModulosYUsername")
                    .setParameter("codmodulo", codmodulo)
                    .setParameter("username", usuario)
                    .getResultList();
        } catch (Exception ex) {
            UtilOut.println(ex);
        }
        return funcionalidades;
    }
    
    @Override
    public List<Funcionalidad> funcionalidadesPorGrupo(GrupoModulo grupoModulo) {
        List<Funcionalidad> funcionalidades = new ArrayList<Funcionalidad>();
        try {
            funcionalidades = tpm.getEm().createNamedQuery("Funcionalidad.porGrupoModulo")
                    .setParameter("grupo", grupoModulo)
                    .getResultList();
        } catch (Exception ex) {
            UtilOut.println(ex);
        }
        return funcionalidades;
    }
    
    @Override
    public List<Funcionalidad> funcionalidadesPorDescripcion(String etiqueta) {
        List<Funcionalidad> funcionalidades = new ArrayList<Funcionalidad>();
        try {
            funcionalidades = tpm.getEm().createNamedQuery("Funcionalidad.porDescripcion")
                    .setParameter("etiqueta", etiqueta)
                    .getResultList();
        } catch (Exception ex) {
            UtilOut.println(ex);
        }
        return funcionalidades;
    }

    @Override
    public RolPermiso getRolPermiso(Funcionalidad funcionalidad, Rol rol) {
        try {
            return (RolPermiso) tpm.getEm().createNamedQuery("RolPermiso.porFuncionalidadYRol")
                    .setParameter("funcionalidad", funcionalidad)
                    .setParameter("rol", rol)
                    .getSingleResult();

        } catch (NoResultException nre) {
            return null;
        } catch (Exception e) {
            throw new ErrorValidacion("error al verificar el(los) permiso(s) del rol");
        }
    }

    @Override
    //@OperacionAuditable(tipo = TipoOperacion.ROL_PERMISO_CREAR)
    public RolPermiso guardarRolPermiso(RolPermiso rolPermiso, Usuario operador) {
        try {
            EntityManager em = tpm.getEm();
            em.persist(rolPermiso);
            em.flush();
            return rolPermiso;
        } catch (Exception e) {
            throw new ErrorIntegridad("No se pudo establecer el permiso");
        }
    }

    @Override
    //@OperacionAuditable(tipo = TipoOperacion.ROL_PERMISO_ELIMINACION)
    public void eliminarRolPermiso(RolPermiso rolPermiso, Usuario operador) {
        try {
            EntityManager em = tpm.getEm();
            em.remove(rolPermiso);
            em.flush();
        } catch (Exception e) {
            throw new ErrorIntegridad("No se pudo quitar el permiso");
        }
    }

    @Override
    public List<Usuario> getUsuarios() {
        List<Usuario> usuarios = new ArrayList<Usuario>();
        try {
            usuarios = tpm.getEm().createNamedQuery("Usuario.findAll").getResultList();
        } catch (Exception ex) {
            UtilOut.println(ex);
        }
        return usuarios;
    }

    @Override
    //@OperacionAuditable(tipo = TipoOperacion.USUARIO_MODIFICACION)
    @Interceptors({InterceptorAdministracion.class}) 
    public void actualizarUsuario(String usernameSesion, Usuario usuario) {
        try {
            EntityManager em = tpm.getEm();
            em.merge(usuario);
            em.flush();
        } catch (Exception e) {
            throw new ErrorIntegridad("error al actualizar el usuario");
        }
    }

    @Override
    public List<Rol> getRoles() {
        List<Rol> roles = new ArrayList<Rol>();
        try {
            roles = tpm.getEm().createNamedQuery("Rol.findAll").getResultList();
        } catch (Exception ex) {
            UtilOut.println(ex);
        }
        return roles;
    }

    @Override
    public List<Rol> rolesAsignados(Usuario usuario) {
        List<Rol> roles = new ArrayList<Rol>();
        try {
            roles = tpm.getEm().createNamedQuery("UsuarioRol.rolesAsignados")
                    .setParameter("usuario", usuario)
                    .getResultList();
        } catch (Exception ex) {
            UtilOut.println(ex);
        }
        return roles;
    }

    @Override
    public List<Rol> rolesNoAsignados(Usuario usuario) {
        List<Rol> roles = new ArrayList<Rol>();
        try {
            roles = tpm.getEm().createNamedQuery("UsuarioRol.rolesNoAsignados")
                    .setParameter("usuario", usuario)
                    .getResultList();
        } catch (Exception ex) {
            UtilOut.println(ex);
        }
        return roles;
    }

    @Override
    public boolean estaAsignadoElRol(Usuario usuario, Rol rol) {
        boolean sw = false;
        try {
            UsuarioRol ur = (UsuarioRol) tpm.getEm().createNamedQuery("UsuarioRol.buscarPorRolYUsuario")
                    .setParameter("usuario", usuario)
                    .setParameter("rol", rol)
                    .getSingleResult();
            if (ur != null) {
                sw = true;
            }

        } catch (Exception e) {
            //e.printStackTrace();
        }
        return sw;
    }

    @Override
    public UsuarioRol getUsuarioRol(Usuario usuario, Rol rol) {
        try {
            return (UsuarioRol) tpm.getEm().createNamedQuery("UsuarioRol.buscarPorRolYUsuario")
                    .setParameter("usuario", usuario)
                    .setParameter("rol", rol)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        } catch (Exception e) {
            throw new ErrorIntegridad("error al obtener el rol de usuario");
        }
    }

    @Override
    @Interceptors({InterceptorAdministracion.class}) 
    public UsuarioRol guardarUsuarioRol(String usernameSesion, UsuarioRol usuarioRol) {
        try {
            tpm.getEm().persist(usuarioRol);
            tpm.getEm().flush();
            return usuarioRol;
        } catch (Exception e) {
            throw new ErrorIntegridad("error al modificar rol(es) al usuario");
        }
    }

    @Override
    //@OperacionAuditable(tipo = TipoOperacion.USUARIO_ROL_MODIFICACION)
    public void actualizarUsuarioRol(UsuarioRol usuarioRol, Usuario operador) {
        try {
            tpm.getEm().merge(usuarioRol);
            tpm.getEm().flush();
        } catch (Exception e) {
            throw new ErrorIntegridad("error al modificar rol(es) al usuario");
        }
    }

    @Override
    public List<Nivel> getNivelesPorPadre(Nivel padre) {
        List<Nivel> nivelesEspecificos = new ArrayList<Nivel>();
        try {
            nivelesEspecificos = tpm.getEm().createNamedQuery("Nivel.porPadre")
                    .setParameter("padre", padre)
                    .getResultList();
        } catch (Exception ex) {
            UtilOut.println(ex);
        }
        return nivelesEspecificos;
    }

    @Override
    public boolean existeUsername(String username) {
        boolean sw = false;
        try {
            Usuario u = (Usuario) tpm.getEm().createNamedQuery("Usuario.porUsername")
                    .setParameter("username", username)
                    .getSingleResult();
            if (u != null) {
                sw = true;
            }

        } catch (Exception e) {
            //e.printStackTrace();
        }
        return sw;
    }

    @Override
    //@OperacionAuditable(tipo = TipoOperacion.USUARIO_CREAR)
    @Interceptors({InterceptorAdministracion.class}) 
    public Usuario guardarUsuario(String usernameSesion, Usuario usuario) {
        try {
            tpm.getEm().persist(usuario);
            tpm.getEm().flush();            
            return usuario;
        } catch (Exception e) {
            throw new ErrorIntegridad("error al guardar el usuario");
        }
    }

    @Override
    public boolean existeNombreRol(String nombre) {
        boolean sw = false;
        try {
            Rol r = (Rol) tpm.getEm().createNamedQuery("Rol.porNombre")
                    .setParameter("nombre", nombre)
                    .getSingleResult();
            if (r != null) {
                sw = true;
            }

        } catch (Exception e) {
            //e.printStackTrace();
        }
        return sw;
    }

    @Override
    //@OperacionAuditable(tipo = TipoOperacion.ROL_CREAR)
    @Interceptors({InterceptorAdministracion.class}) 
    public Rol guardarRol(String usernameSesion, Rol rol) {
        try {
            tpm.getEm().persist(rol);
            tpm.getEm().flush();
            return rol;
        } catch (Exception e) {
            throw new ErrorIntegridad("error al guardar el rol");
        }
    }

    @Override
    //@OperacionAuditable(tipo = TipoOperacion.ROL_MODIFICACION)
    @Interceptors({InterceptorAdministracion.class}) 
    public void actualizarRol(String usernameSesion, Rol rol) {
        try {
            tpm.getEm().merge(rol);
            tpm.getEm().flush();
        } catch (Exception e) {
            throw new ErrorIntegridad("error al actualizar el rol");
        }
    }

    @Override
    public boolean tieneUsuarios(Rol rol) {
        boolean sw = false;
        try {
            List<Usuario> usuarios = tpm.getEm().createNamedQuery("UsuarioRol.usuariosPorRol")
                    .setParameter("rol", rol)
                    .getResultList();
            if (!usuarios.isEmpty()) {
                sw = true;
            }

        } catch (Exception e) {
            //e.printStackTrace();
        }
        return sw;
    }

    @Override
    //@OperacionAuditable(tipo = TipoOperacion.ROL_ELIMINACION)
    @Interceptors({InterceptorAdministracion.class}) 
    public void eliminarRol(String usernameSesion, Rol rol) {
        try {
            tpm.getEm().remove(rol);
            tpm.getEm().flush();
        } catch (Exception e) {
            throw new ErrorIntegridad("error al eliminar el rol");
        }
    }
}
