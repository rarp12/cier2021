/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.bean;

import co.stackpointer.cier.modelo.entidad.acceso.Usuario;
import co.stackpointer.cier.modelo.entidad.dpa.ConfiguracionNivel;
import co.stackpointer.cier.modelo.entidad.dpa.Nivel;
import co.stackpointer.cier.modelo.fachada.EmailFacadeLocal;
import co.stackpointer.cier.modelo.fachada.EstructuraDPAFacadeLocal;
import co.stackpointer.cier.modelo.interfaceDPA.ConfNivelDPA;
import co.stackpointer.cier.modelo.tipo.ParamNivelConsul;
import co.stackpointer.cier.modelo.utilidad.UtilCadena;
import co.stackpointer.cier.modelo.utilidad.UtilFileItem;
import co.stackpointer.cier.vista.ManejadorErrores;
import co.stackpointer.cier.vista.Utilidades;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.ejb.EJB;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author rjay1
 */
@ManagedBean(name = "mensajesBean")
@ViewScoped
public class GestionMensajesBean implements Serializable {

    @EJB
    private EstructuraDPAFacadeLocal facEstDPA;
    @ManagedProperty(value = "#{usuarioSesion}")
    private UsuarioSesionBean usuarioSesion;
    private Map<Long, String> listaNivelesConf;
    private int maximoNivelDPA;
    private int minimoNivelDPA;
    private List<Nivel> listaNivel1;
    private List<Nivel> listaNivel2;
    private List<Nivel> listaNivel3;
    private List<Nivel> listaNivel4;
    private List<Nivel> listaNivel5;
    private Nivel seleccionNivel0;
    private Nivel seleccionNivel1;
    private Nivel seleccionNivel2;
    private Nivel seleccionNivel3;
    private Nivel seleccionNivel4;
    private Nivel seleccionNivel5;
    private boolean swNivel1;
    private boolean swNivel2;
    private boolean swNivel3;
    private boolean swNivel4;
    private boolean swNivel5;
    private Nivel nivelSeleccionado;
    private String nombreNivelInicial;
    private String nombreDPAInicial;
    private List<UploadedFile> archivosCargados;
    @ManagedProperty(value = "#{login}")
    private LoginBean currentLogin;
    @EJB
    protected EmailFacadeLocal emailFacade;
    private String correoBasico;
    private String correoAsunto;
    private String correoMensaje;
    public boolean hayRegErroneos;

    @PostConstruct
    public void init() {
        nivelSeleccionado = null;
        correoBasico = null;
        correoAsunto = null;
        correoMensaje = null;
        archivosCargados = new ArrayList<UploadedFile>();
        resetearNiveles();
        cargarConfiguracionDPA();
        nivelDPAOfAccesoUsuario();
    }

    public void cargarConfiguracionDPA() {
        listaNivelesConf = new HashMap<Long, String>();
        // niveles DPA
        Usuario u = usuarioSesion.getUsuario();
        List<ConfiguracionNivel> configuraciones;
        if (UtilCadena.isNullOVacio(u.getNivelDpa())) {
            configuraciones = facEstDPA.consultarConfNivelesActual();
        } else {
            configuraciones = facEstDPA.consultarConfNivelesActual(u.getNivelDpa().getNivel());
        }

        //System.out.println(usuarioSesion.getUsuario().getIdioma().getId());
        for (ConfNivelDPA conf : configuraciones) {
            String descripcion = facEstDPA.consultarDescripcionNivelDPA(conf.getNivel(), usuarioSesion.getUsuario().getIdioma().getId());
            descripcion = descripcion != null ? descripcion : conf.getDescripcion();
            this.listaNivelesConf.put(conf.getNivel(), descripcion);
        }
        // maximo nivel DPA
        if (!configuraciones.isEmpty()) {
            int indice = configuraciones.size() - 1;
            this.maximoNivelDPA = configuraciones.get(indice).getNivel().intValue();
            this.minimoNivelDPA = configuraciones.get(0).getNivel().intValue();
        } else {
            this.maximoNivelDPA = -1;
            this.minimoNivelDPA = -1;
        }
    }

    public void activarNiveles(int maximoNivelUsuario) {
        swNivel1 = false;
        swNivel2 = false;
        swNivel3 = false;
        swNivel4 = false;
        swNivel5 = false;
        maximoNivelUsuario++;
        while (maximoNivelUsuario <= maximoNivelDPA) {
            switch (maximoNivelUsuario) {
                case 1:
                    swNivel1 = true;
                    break;
                case 2:
                    swNivel2 = true;
                    break;
                case 3:
                    swNivel3 = true;
                    break;
                case 4:
                    swNivel4 = true;
                    break;
                case 5:
                    swNivel5 = true;
                    break;
            }
            maximoNivelUsuario++;
        }

    }

    public void nivelDPAOfAccesoUsuario() {
        Nivel n = usuarioSesion.getUsuario().getNivelEspecifico() != null
                ? usuarioSesion.getUsuario().getNivelEspecifico()
                : null;
        if (n != null) {
            this.nombreNivelInicial = n.getConfiguracion().getDescripcion();
            this.nombreDPAInicial = n.getDescripcion();
            int nivel = n.getConfiguracion().getNivel().intValue();
            switch (nivel) {
                case 0:
                    cargarNivel0();
                    break;
                case 1:
                    seleccionNivel1 = n;
                    cambioNivel1();
                    break;
                case 2:
                    seleccionNivel2 = n;
                    cambioNivel2();
                    break;
                case 3:
                    seleccionNivel3 = n;
                    cambioNivel3();
                    break;
                case 4:
                    seleccionNivel4 = n;
                    cambioNivel4();
                    break;
                case 5:
                    seleccionNivel5 = n;
                    cambioNivel5();
                    break;
            }
            activarNiveles(nivel);
        } else {
            cargarNivel0();
            activarNiveles(0);
        }
    }

    public void enviarCorreo() {
        try {
            List<UtilFileItem> atts = new ArrayList<UtilFileItem>();
            for(UploadedFile u : archivosCargados){
                atts.add(new UtilFileItem(u.getFileName(), u.getInputstream(), u.getContentType()));
            }
            if (correoBasico != null && !correoBasico.trim().equals("")) {
                if (atts.size() > 0) {
                    emailFacade.sendEmail(correoBasico, correoAsunto, correoMensaje, atts);
                } else {
                    emailFacade.sendEmail(correoBasico, correoAsunto, correoMensaje);
                }
            } else {
                String hierarchy = seleccionNivel0.getId().toString();
                if (seleccionNivel1 != null && seleccionNivel1.getId() != null) {
                    hierarchy += seleccionNivel1.getId().toString();
                }
                if (seleccionNivel2 != null && seleccionNivel2.getId() != null) {
                    hierarchy += seleccionNivel2.getId().toString();
                }
                if (seleccionNivel3 != null && seleccionNivel3.getId() != null) {
                    hierarchy += seleccionNivel3.getId().toString();
                }
                if (seleccionNivel4 != null && seleccionNivel4.getId() != null) {
                    hierarchy += seleccionNivel4.getId().toString();
                }
                if (atts.size() > 0) {
                    emailFacade.sendEmail(hierarchy, 0, correoAsunto, correoMensaje, atts);
                } else {
                    emailFacade.sendEmail(hierarchy, 0, correoAsunto, correoMensaje, null);
                }
            }
            Utilidades.mostrarMensajeInfo(Utilidades.obtenerMensaje("administracion.mensajes.enviar.correo.exito.titulo"), Utilidades.obtenerMensaje("administracion.mensajes.enviar.correo.exito.mensaje"));
            init();
        } catch (Exception ex) {
            Utilidades.mostrarMensajeInfo(Utilidades.obtenerMensaje("administracion.mensajes.enviar.correo.error.titulo"), Utilidades.obtenerMensaje("administracion.mensajes.enviar.correo.error.mensaje"));
            Logger.getLogger(GestionMensajesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cargarAdjuntos(FileUploadEvent event) throws IOException {
        if (archivosCargados == null) {
            archivosCargados = new ArrayList<UploadedFile>();
        }
        hayRegErroneos = false;
        archivosCargados.add(event.getFile());
    }

    public void guardarArchivo() {
        try {
            if (archivosCargados == null || archivosCargados.isEmpty()) {
                return;
            }
            for (UploadedFile uploadedFile : archivosCargados) {
                System.out.println(uploadedFile.getFileName());
            }
            Utilidades.mostrarMensajeInfo(Utilidades.obtenerMensaje("aplicacion.general.operacionExitosa"), Utilidades.obtenerMensaje("aplicacion.general.registroEnServidor"));
        } catch (Exception e) {
            Utilidades.mostrarMensajeError(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"), Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"));
            ManejadorErrores.ingresarExcepcionEnLog(e, GestionMensajesBean.class);
        }
    }

    private List<Nivel> cargarNivel(Nivel nivelSeccionado, ParamNivelConsul nivelConsulta) {
        return facEstDPA.consultarNiveles(nivelConsulta, nivelSeccionado.getId());
    }

    private void resetearNivel1() {
        seleccionNivel1 = null;
        listaNivel1 = new ArrayList<Nivel>();
    }

    private void resetearNivel2() {
        seleccionNivel2 = null;
        listaNivel2 = new ArrayList<Nivel>();
    }

    private void resetearNivel3() {
        seleccionNivel3 = null;
        listaNivel3 = new ArrayList<Nivel>();
    }

    private void resetearNivel4() {
        seleccionNivel4 = null;
        listaNivel4 = new ArrayList<Nivel>();
    }

    private void resetearNivel5() {
        seleccionNivel5 = null;
        listaNivel5 = new ArrayList<Nivel>();
    }

    public void resetearNiveles() {
        this.resetearNivel1();
        this.resetearNivel2();
        this.resetearNivel3();
        this.resetearNivel4();
        this.resetearNivel5();
    }

    public void cargarNivel0() {
        String codigoPais = currentLogin.getPais().getCodigoPais();
        seleccionNivel0 = facEstDPA.consultarNivel(codigoPais);
        this.nombreNivelInicial = seleccionNivel0.getConfiguracion().getDescripcion();
        this.nombreDPAInicial = seleccionNivel0.getDescripcion();
        this.cargarNivel1();
    }

    public void cargarNivel1() {
        this.resetearNivel1();
        this.resetearNivel2();
        this.resetearNivel3();
        this.resetearNivel4();
        this.resetearNivel5();
        if (seleccionNivel0 != null) {
            listaNivel1 = this.cargarNivel(seleccionNivel0, ParamNivelConsul.UNO);
        }
    }

    public void cambioNivel1() {
        this.resetearNivel2();
        this.resetearNivel3();
        this.resetearNivel4();
        this.resetearNivel5();
        if (seleccionNivel1 != null) {
            nivelSeleccionado = seleccionNivel1;
            listaNivel2 = this.cargarNivel(seleccionNivel1, ParamNivelConsul.DOS);
        } else {
            nivelSeleccionado = null;
        }
    }

    public void cambioNivel2() {
        this.resetearNivel3();
        this.resetearNivel4();
        this.resetearNivel5();
        if (seleccionNivel2 != null) {
            nivelSeleccionado = seleccionNivel2;
            listaNivel3 = this.cargarNivel(seleccionNivel2, ParamNivelConsul.TRES);
        } else {
            nivelSeleccionado = seleccionNivel1;
        }
    }

    public void cambioNivel3() {
        this.resetearNivel4();
        this.resetearNivel5();
        if (seleccionNivel3 != null) {
            nivelSeleccionado = seleccionNivel3;
            listaNivel4 = this.cargarNivel(seleccionNivel3, ParamNivelConsul.CUATRO);
        } else {
            nivelSeleccionado = seleccionNivel2;
        }
    }

    public void cambioNivel4() {
        this.resetearNivel5();
        if (seleccionNivel4 != null) {
            nivelSeleccionado = seleccionNivel4;
            listaNivel5 = this.cargarNivel(seleccionNivel4, ParamNivelConsul.CINCO);
        } else {
            nivelSeleccionado = seleccionNivel3;
        }
    }

    public void cambioNivel5() {
        if (seleccionNivel5 != null) {
            nivelSeleccionado = seleccionNivel5;
        } else {
            nivelSeleccionado = seleccionNivel4;
        }
    }

    public List<Nivel> getListaNivel1() {
        return listaNivel1;
    }

    public List<Nivel> getListaNivel2() {
        return listaNivel2;
    }

    public List<Nivel> getListaNivel3() {
        return listaNivel3;
    }

    public List<Nivel> getListaNivel4() {
        return listaNivel4;
    }

    public List<Nivel> getListaNivel5() {
        return listaNivel5;
    }

    public Nivel getSeleccionNivel0() {
        return seleccionNivel0;
    }

    public void setSeleccionNivel0(Nivel seleccionNivel0) {
        this.seleccionNivel0 = seleccionNivel0;
    }

    public Nivel getSeleccionNivel1() {
        return seleccionNivel1;
    }

    public void setSeleccionNivel1(Nivel seleccionNivel1) {
        this.seleccionNivel1 = seleccionNivel1;
    }

    public Nivel getSeleccionNivel2() {
        return seleccionNivel2;
    }

    public void setSeleccionNivel2(Nivel seleccionNivel2) {
        this.seleccionNivel2 = seleccionNivel2;
    }

    public Nivel getSeleccionNivel3() {
        return seleccionNivel3;
    }

    public void setSeleccionNivel3(Nivel seleccionNivel3) {
        this.seleccionNivel3 = seleccionNivel3;
    }

    public Nivel getSeleccionNivel4() {
        return seleccionNivel4;
    }

    public void setSeleccionNivel4(Nivel seleccionNivel4) {
        this.seleccionNivel4 = seleccionNivel4;
    }

    public Nivel getSeleccionNivel5() {
        return seleccionNivel5;
    }

    public void setSeleccionNivel5(Nivel seleccionNivel5) {
        this.seleccionNivel5 = seleccionNivel5;
    }

    public boolean isSwNivel1() {
        return swNivel1;
    }

    public boolean isSwNivel2() {
        return swNivel2;
    }

    public boolean isSwNivel3() {
        return swNivel3;
    }

    public boolean isSwNivel4() {
        return swNivel4;
    }

    public boolean isSwNivel5() {
        return swNivel5;
    }

    public Map<Long, String> getListaNivelesConf() {
        return listaNivelesConf;
    }

    public Nivel getNivelSeleccionado() {
        return nivelSeleccionado;
    }

    public String getNombreNivelInicial() {
        return nombreNivelInicial;
    }

    public String getNombreDPAInicial() {
        return nombreDPAInicial;
    }

    public UsuarioSesionBean getUsuarioSesion() {
        return usuarioSesion;
    }

    public void setUsuarioSesion(UsuarioSesionBean usuarioSesion) {
        this.usuarioSesion = usuarioSesion;
    }

    public LoginBean getCurrentLogin() {
        return currentLogin;
    }

    public void setCurrentLogin(LoginBean currentLogin) {
        this.currentLogin = currentLogin;
    }

    public int getMinimoNivelDPA() {
        return minimoNivelDPA;
    }

    public String getCorreoAsunto() {
        return correoAsunto;
    }

    public void setCorreoAsunto(String correoAsunto) {
        this.correoAsunto = correoAsunto;
    }

    public String getCorreoMensaje() {
        return correoMensaje;
    }

    public void setCorreoMensaje(String correoMensaje) {
        this.correoMensaje = correoMensaje;
    }

    public String getCorreoBasico() {
        return correoBasico;
    }

    public void setCorreoBasico(String correoBasico) {
        this.correoBasico = correoBasico;
    }

    public List<UploadedFile> getArchivosCargados() {
        return archivosCargados;
    }

    public void setArchivosCargados(List<UploadedFile> archivosCargados) {
        this.archivosCargados = archivosCargados;
    }

    public boolean isHayRegErroneos() {
        return hayRegErroneos;
    }

    public void setHayRegErroneos(boolean hayRegErroneos) {
        this.hayRegErroneos = hayRegErroneos;
    }
}