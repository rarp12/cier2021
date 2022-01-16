/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.parametro.estandares;

import co.stackpointer.cier.modelo.auditoria.RegistrarAuditoria;
import co.stackpointer.cier.modelo.entidad.indicador.EstandarHis;
import co.stackpointer.cier.modelo.fachada.ParametrosFacadeLocal;
import co.stackpointer.cier.modelo.tipo.BitacoraTransaccion;
import co.stackpointer.cier.modelo.utilidad.UtilOut;
import co.stackpointer.cier.vista.Utilidades;
import co.stackpointer.cier.vistacontrolador.bean.UsuarioSesionBean;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.omnifaces.util.Messages;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author user
 */
@ManagedBean(name = "estandaresBean")
@ViewScoped
public class EstandaresBean implements Serializable {

    @EJB
    private ParametrosFacadeLocal facParam;
    
    @ManagedProperty(value = "#{usuarioSesion}")
    private UsuarioSesionBean usuarioSesion;
    
    private List<EstandarHis> lista;

    @PostConstruct
    public void inicializar() {
        this.consultar();
    }

    public void consultar() {
        try {
            actualizarLista();
        } finally {
            RegistrarAuditoria registrar = new RegistrarAuditoria();
            registrar.setFecha(new Date());
            registrar.setNombreMetodo(BitacoraTransaccion.CONSULTA_ESTANDARES.name());
            registrar.setUsuario(usuarioSesion.getUsuario().getUsername());
            registrar.start();
        }
    }
    
    private void actualizarLista() {
        lista = facParam.consultarEstandares();
    }

    public List<EstandarHis> getLista() {
        return lista;
    }

    public void guardar() {
        try {
            for(EstandarHis estandar:lista){
                facParam.modificarEstandar(estandar);
            }
            this.actualizarLista();
            
        } catch (Exception e) {
            UtilOut.println(e);
            Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("gerencial.bid.consulta.adminAmbito.upps")+" " + e.getMessage());
        }finally{
            RegistrarAuditoria registrar = new RegistrarAuditoria();
            registrar.setFecha(new Date());
            registrar.setNombreMetodo(BitacoraTransaccion.EDITAR_ESTANDARES.name());
            registrar.setUsuario(usuarioSesion.getUsuario().getUsername());
            registrar.start();
        }
    }

    public void cancelarEdicion(RowEditEvent event) {
        this.consultar();
    }
    
    public UsuarioSesionBean getUsuarioSesion() {
        return usuarioSesion;
    }

    public void setUsuarioSesion(UsuarioSesionBean usuarioSesion) {
        this.usuarioSesion = usuarioSesion;
    }
}
