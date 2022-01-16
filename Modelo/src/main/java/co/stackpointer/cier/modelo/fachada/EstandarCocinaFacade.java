/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.fachada;

import co.stackpointer.cier.modelo.auditoria.InterceptorAdministracion;
import co.stackpointer.cier.modelo.entidad.indicador.EstandarCocina;
import co.stackpointer.cier.modelo.excepcion.ErrorGeneral;
import co.stackpointer.cier.modelo.excepcion.ErrorIntegridad;
import co.stackpointer.cier.modelo.excepcion.ErrorValidacion;
import co.stackpointer.cier.modelo.utilidad.UtilOut;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.persistence.NoResultException;

/**
 *
 * @author rarp1
 */
@Stateless
public class EstandarCocinaFacade implements EstandarCocinaFacadeLocal {
    
    @Inject
    private TenantPersistenceManager tpm;

    @Override
    public List<EstandarCocina> getEstandares() {
        List<EstandarCocina> estandares = new ArrayList<EstandarCocina>();
        try {
            estandares = tpm.getEm().createNamedQuery("EstandarCocina.findAll")                    
                    .getResultList();
        } catch (Exception ex) {
            UtilOut.println(ex);
        }
        return estandares;
    }
    
    @Override
    public EstandarCocina getEstandar(Long id) {
        try {
            return (EstandarCocina) tpm.getEm().createNamedQuery("EstandarCocina.porId")
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } catch (Exception ex) {            
            throw new ErrorGeneral("EstandarCocina", "No se pudo obtener datos de sesion");
        }
    }            
            

    @Override
    @Interceptors({InterceptorAdministracion.class})
    public EstandarCocina guardarEstandarCocina(String usernameSesion, EstandarCocina estandar) {
        boolean sw = true;
        try {
            if (estandar.getMaxAlumnos().compareTo(estandar.getMinAlumnos()) > 0) {
                List<EstandarCocina> estandaresExistentes = getEstandaresExistentes();
                if (estandar.getMaxAlumnos() == null) {
                    estandar.setMaxAlumnos(99999999L);
                }
                if (!estandaresExistentes.isEmpty()) {
                    sw = aceptarRango(estandar.getMinAlumnos(),
                            estandar.getMaxAlumnos(),
                            estandaresExistentes);
                }
                if (sw) {
                    tpm.getEm().persist(estandar);
                    tpm.getEm().flush();
                } else {
                    throw new ErrorValidacion("Conflicto con el nuevo rango");
                }
            } else {
                throw new ErrorValidacion("El mínimo de alumnos supera o es igual al máximo");
            }
            return estandar;
        } catch (Exception e) {
            if (e instanceof ErrorValidacion) {
                throw new ErrorValidacion(e.getMessage());
            } else {
                throw new ErrorIntegridad("Error al guardar el estandar Cocina");
            }
        }
    }

    @Override
    @Interceptors({InterceptorAdministracion.class})
    public void actualizarEstandarCocina(String usernameSesion, EstandarCocina estandar) {
        boolean sw = true;
        try {
            if (estandar.getMaxAlumnos().compareTo(estandar.getMinAlumnos()) > 0) {
                List<EstandarCocina> estandaresExistentes = getEstandaresExistentesMenosEditable(
                        estandar.getId());
                if (estandar.getMaxAlumnos() == null) {
                    estandar.setMaxAlumnos(99999999L);
                }
                if (!estandaresExistentes.isEmpty()) {
                    sw = aceptarRango(estandar.getMinAlumnos(),
                            estandar.getMaxAlumnos(),
                            estandaresExistentes);
                }
                if (sw) {
                    tpm.getEm().merge(estandar);
                    tpm.getEm().flush();
                } else {
                    throw new ErrorValidacion("Conflicto con el nuevo rango");
                }
            } else {
                throw new ErrorValidacion("El mínimo de alumnos supera al máximo");
            }
        } catch (Exception e) {
            if (e instanceof ErrorValidacion) {
                throw new ErrorValidacion(e.getMessage());
            } else {
                throw new ErrorIntegridad("Error al actualizar el estandar Cocina");

            }
        }
    }
    
    @Override
    @Interceptors({InterceptorAdministracion.class})
    public void eliminarEstandarCocina(String usernameSesion, EstandarCocina estandar) {
        try {
            tpm.getEm().remove(estandar);
            tpm.getEm().flush();
        } catch (Exception e) {
            throw new ErrorIntegridad("Error al eliminar el estandar Cocina");
        }
    }    
    
    private boolean aceptarRango(Long x, Long y, List<EstandarCocina> estandares) {
        boolean sw = true;
        for (EstandarCocina estandarLote : estandares) {
            Long rx = estandarLote.getMinAlumnos();
            Long ry = estandarLote.getMaxAlumnos();
            if ((x >= rx && x <= ry) || (y >= rx && y <= ry)) {
                sw = false;
                break;
            }
        }
        return sw;
    }
    
    private List<EstandarCocina> getEstandaresExistentes() {
        List<EstandarCocina> estandares = new ArrayList<EstandarCocina>();
        try {
            estandares = tpm.getEm().createNamedQuery("EstandarCocina.existentes")
                    .getResultList();
        } catch (Exception ex) {
            UtilOut.println(ex);
        }
        return estandares;
    }
    
    private List<EstandarCocina> getEstandaresExistentesMenosEditable(Long id) {
        List<EstandarCocina> estandares = new ArrayList<EstandarCocina>();
        try {
            estandares = tpm.getEm().createNamedQuery("EstandarCocina.existentesMenosEditable")
                    .setParameter("id", id)
                    .getResultList();
        } catch (Exception ex) {
            UtilOut.println(ex);
        }
        return estandares;
    }
    
}
