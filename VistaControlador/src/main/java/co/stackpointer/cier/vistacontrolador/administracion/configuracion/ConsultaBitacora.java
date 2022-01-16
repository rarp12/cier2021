/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.administracion.configuracion;

import co.stackpointer.cier.modelo.entidad.acceso.Usuario;
import co.stackpointer.cier.modelo.entidad.sistema.Bitacora;
import co.stackpointer.cier.modelo.fachada.AccesoFacadeLocal;
import co.stackpointer.cier.modelo.fachada.BitacoraFacadeLocal;
import co.stackpointer.cier.modelo.filtro.bitacora.FiltroConsultaBitacora;
import co.stackpointer.cier.modelo.tipo.BitacoraModulo;
import co.stackpointer.cier.modelo.utilidad.UtilCadena;
import co.stackpointer.cier.vista.ManejadorErrores;
import static co.stackpointer.cier.vista.Utilidades.mostrarMensajeInfo;
import co.stackpointer.cier.vistacontrolador.consulta.basica.ConsultaInstrumentoBean;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author devTeam
 */
@ManagedBean(name = "consultaBitacoraBean")
@ViewScoped
public class ConsultaBitacora {

    @EJB
    protected BitacoraFacadeLocal consultaBitacoraFacade;
    @EJB
    protected AccesoFacadeLocal consultaAccesoFacade;
    private String boletaCensal;
    private String usuario;
    private Date fechaInicio;
    private Date fechaFinal;
    private String modulo;
    private List<Bitacora> lista;
    private FiltroConsultaBitacora filtro;
    private List<BitacoraModulo> modulos;
    private List<Usuario> usuarios;

    @PostConstruct
    public void inicializar() {
        this.lista = new ArrayList<Bitacora>();
        filtro = new FiltroConsultaBitacora();
        boletaCensal = null;
        usuario = null;
        modulo = null;
        modulos = Arrays.asList(BitacoraModulo.values());
        usuarios = consultaAccesoFacade.getUsuarios();
        inicializarFecha();
    }

    private void inicializarFecha() {
        Calendar c1 = Calendar.getInstance();
        c1.add(Calendar.MONTH, -1);
        Calendar c2 = Calendar.getInstance();
        fechaInicio = c1.getTime();
        fechaFinal = c2.getTime();
    }

    public void consultar() {
        filtro.setBoletaCensal(!UtilCadena.isNullOVacio(boletaCensal) ? boletaCensal : null);
        filtro.setUsuario(!UtilCadena.isNullOVacio(usuario) ? usuario : null);
        filtro.setModulo(!UtilCadena.isNullOVacio(modulo) ? modulo : null);
        filtro.setFechaInicio(fechaInicio != null ? fechaInicio : null);
        filtro.setFechaFin(fechaFinal != null ? fechaFinal : null);
        this.lista = consultaBitacoraFacade.consultarBitacora(filtro);
    }
    
    public void eliminarBitacora() {
        try {
            consultaBitacoraFacade.eliminarBitacora();
            mostrarMensajeInfo("Eliminar Bitacora", "Bitacora eliminada correctamente !");
        } catch (Exception ex) {
            ManejadorErrores.ingresarExcepcionEnLog(ex, ConsultaBitacora.class);
        }
        
    }

    public void resetearFiltros() {
        this.lista = new ArrayList<Bitacora>();
        this.filtro = new FiltroConsultaBitacora();
        inicializarFecha();
        boletaCensal = null;
        usuario = null;
        modulo = null;
    }

    public BitacoraFacadeLocal getConsultaBitacoraFacade() {
        return consultaBitacoraFacade;
    }

    public void setConsultaBitacoraFacade(BitacoraFacadeLocal consultaBitacoraFacade) {
        this.consultaBitacoraFacade = consultaBitacoraFacade;
    }

    public String getBoletaCensal() {
        return boletaCensal;
    }

    public void setBoletaCensal(String boletaCensal) {
        this.boletaCensal = boletaCensal;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public List<Bitacora> getLista() {
        return lista;
    }

    public void setLista(List<Bitacora> lista) {
        this.lista = lista;
    }

    public List<BitacoraModulo> getModulos() {
        return modulos;
    }

    public void setModulos(List<BitacoraModulo> modulos) {
        this.modulos = modulos;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}
