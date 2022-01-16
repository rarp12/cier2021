/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.fachada;

import co.stackpointer.cier.modelo.auditoria.InterceptorAdministracion;
import co.stackpointer.cier.modelo.entidad.indicador.EstandarLote;
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
import javax.persistence.Query;

/**
 *
 * @author StackPointer
 */
@Stateless
public class EstandarLoteFacade implements EstandarLoteFacadeLocal {
    
    @Inject
    private TenantPersistenceManager tpm;

    @Override
    public List<EstandarLote> getEstandares() {
        List<EstandarLote> estandares = new ArrayList<EstandarLote>();
        try {
            estandares = tpm.getEm().createNamedQuery("EstandarLote.findAll")                    
                    .getResultList();
        } catch (Exception ex) {
            UtilOut.println(ex);
        }
        return estandares;
    }
    
    @Override
    public EstandarLote getEstandar(Long id) {
        try {
            return (EstandarLote) tpm.getEm().createNamedQuery("EstandarLote.porId")
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } catch (Exception ex) {            
            throw new ErrorGeneral("EstandarLote", "No se pudo obtener datos de sesion");
        }
    }            
            
    @Override
    public List<String> getListaTipo(String grupo) {
        List<String> resultados=null;
        try {
            String sql = "select distinct codigo from ind_equivalencia where grupo = ?grupo";
            Query query = tpm.getEm().createNativeQuery(sql);
            query.setParameter("grupo", grupo);
            resultados = query.getResultList();
           
        } catch (Exception e) {
            UtilOut.println(e);
        }
        return resultados;
    }
    
    @Override
    public List<String> getListaTipo() {
        List<String> resultados=null;
        try {
            String sql = "select distinct codigo from ind_equivalencia where grupo = 'nivel_educativo' or grupo='zona'";
            Query query = tpm.getEm().createNativeQuery(sql);
            resultados = query.getResultList();
            
        } catch (Exception e) {
            UtilOut.println(e);
        }
        return resultados;
    }

    @Override
    @Interceptors({InterceptorAdministracion.class})
    public EstandarLote guardarEstandar(String usernameSesion, EstandarLote estandar) {
        boolean sw = true;
        try {
            if (estandar.getMaxAlumnos().compareTo(estandar.getMinAlumnos()) > 0) {
                List<EstandarLote> estandaresExistentes = getEstandaresExistentes(
                        estandar.getNumPisos());
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
                throw new ErrorIntegridad("Error al guardar el estandar");
            }
        }
    }

    @Override
    @Interceptors({InterceptorAdministracion.class})
    public void actualizarEstandar(String usernameSesion, EstandarLote estandar) {
        boolean sw = true;
        try {
            if (estandar.getMaxAlumnos().compareTo(estandar.getMinAlumnos()) > 0) {
                List<EstandarLote> estandaresExistentes = getEstandaresExistentesMenosEditable(
                        estandar.getNumPisos(),
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
                throw new ErrorIntegridad("Error al actualizar el estandar");

            }
        }
    }
    
    @Override
    @Interceptors({InterceptorAdministracion.class})
    public void eliminarEstandar(String usernameSesion, EstandarLote estandar) {
        try {
            EstandarLote estandarElimando = tpm.getEm().find(EstandarLote.class, estandar.getId());
            tpm.getEm().remove(estandarElimando);
            tpm.getEm().flush();
        } catch (Exception e) {
            throw new ErrorIntegridad("Error al eliminar el estandar");
        }
    }    
    
    private boolean aceptarRango(Long x, Long y, List<EstandarLote> estandares) {
        boolean sw = true;
        for (EstandarLote estandarLote : estandares) {
            Long rx = estandarLote.getMinAlumnos();
            Long ry = estandarLote.getMaxAlumnos();
            if ((x >= rx && x <= ry) || (y >= rx && y <= ry)) {
                sw = false;
                break;
            }
        }
        return sw;
    }
    
    private List<EstandarLote> getEstandaresExistentes(Long pisos) {
        List<EstandarLote> estandares = new ArrayList<EstandarLote>();
        try {
            estandares = tpm.getEm().createNamedQuery("EstandarLote.existentes")
                    .setParameter("pisos", pisos)
                    .getResultList();
        } catch (Exception ex) {
            UtilOut.println(ex);
        }
        return estandares;
    }
    
    private List<EstandarLote> getEstandaresExistentesMenosEditable(Long pisos, Long id) {
        List<EstandarLote> estandares = new ArrayList<EstandarLote>();
        try {
            estandares = tpm.getEm().createNamedQuery("EstandarLote.existentesMenosEditable")
                    .setParameter("pisos", pisos)
                    .setParameter("id", id)
                    .getResultList();
        } catch (Exception ex) {
            UtilOut.println(ex);
        }
        return estandares;
    }
    
}
