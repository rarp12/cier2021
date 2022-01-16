/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.bean;

import co.stackpointer.cier.modelo.entidad.acceso.Usuario;
import co.stackpointer.cier.modelo.entidad.sistema.Idioma;
import co.stackpointer.cier.modelo.entidad.sistema.Parametro;
import co.stackpointer.cier.modelo.entidad.sistema.Tenant;
import co.stackpointer.cier.modelo.excepcion.ErrorIntegridad;
import co.stackpointer.cier.modelo.fachada.ArchivoFacadeLocal;
import co.stackpointer.cier.modelo.fachada.PaisFacadeLocal;
import co.stackpointer.cier.modelo.fachada.ParametrosFacadeLocal;
import co.stackpointer.cier.modelo.tipo.ParamSistema;
import co.stackpointer.cier.modelo.utilidad.UtilCadena;
import co.stackpointer.cier.modelo.utilidad.UtilEncriptar;
import co.stackpointer.cier.vista.ManejadorErrores;
import co.stackpointer.cier.vista.Utilidades;
import static co.stackpointer.cier.vistacontrolador.bean.importacion.ImpUtil.getValorCelda;
import co.stackpointer.cier.vistacontrolador.consulta.basica.ConsultaInstrumentoBean;
import co.stackpointer.cier.vistacontrolador.consulta.generarIndicadores.NivelGeneracionBean;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.omnifaces.util.Messages;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author StackPointer
 */
@ManagedBean(name = "gestionPais")
@ViewScoped
public class GestionPaislBean implements Serializable {

    @EJB
    PaisFacadeLocal fachada;
    @EJB
    ArchivoFacadeLocal facArchivo;
    @EJB
    ParametrosFacadeLocal facParam;
    @ManagedProperty(value = "#{usuarioSesion}")
    private UsuarioSesionBean usuarioSesion;
    private List<Tenant> paises;
    private Tenant paisSeleccionado;
    private Tenant paisNuevo;
    private LinkedHashMap<String,String> locales;
    private List<String> timeZones;
    private List<Idioma> idiomas;
    private List<String> nivelesDpa;
    private List<String> nivelesDpaTraducido;
    private Usuario superUsuario;
    private Idioma idiomaDPA;
    private boolean verTraducciones;
    private boolean verCargaDPA; 
    
    public GestionPaislBean() {
    }

    @PostConstruct
    public void init() {
        setPaises(fachada.getPaises());
        setLocales(fachada.getLocales());
        setTimeZones(fachada.getTimeZones());
        setIdiomas(fachada.getIdiomas());
        instanciarNuevoPais();
    }

    public void instanciarNuevoPais() {
        paisNuevo = new Tenant();
    }
    
    public void guardarPais() {
        try {
            paisNuevo.setPais(UtilCadena.toUpperCase(paisNuevo.getPais()));
            paisNuevo.setAbreviatura(UtilCadena.toUpperCase(paisNuevo.getAbreviatura()));
            setPaisNuevo(fachada.guardarPais(paisNuevo, usuarioSesion.getUsuario()));
            paises.add(paisNuevo);
        } catch (Exception e) {
            RequestContext.getCurrentInstance().addCallbackParam("validationFailed", true);
            if(e instanceof ErrorIntegridad) {
                Messages.addFlashGlobalError(e.getMessage());
            } else {               
                ManejadorErrores.ingresarExcepcionEnLog(e, GestionPaislBean.class);
                Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"));
            }
        }
    }

    public void editarPais() {
        try {            
            fachada.actualizarPais(paisSeleccionado, usuarioSesion.getUsuario());            
        } catch (Exception e) {
            ManejadorErrores.ingresarExcepcionEnLog(e, GestionPaislBean.class);
            RequestContext.getCurrentInstance().addCallbackParam("validationFailed", true);
            Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"));
        }
    }
    
    public void instanciarNuevoUsuario() {
        superUsuario = new Usuario();        
    }
    
    public void guardarUsuario() {
        try {
            String cifrado = UtilEncriptar.getStringMessageDigest(superUsuario.getPassword(), UtilEncriptar.SHA256);
            superUsuario.setUsername(UtilCadena.toUpperCase(superUsuario.getUsername()));
            superUsuario.setPassword(cifrado);
            fachada.guardarSuperUsuario(superUsuario, paisSeleccionado.getId());            
        } catch (Exception e) {
            ManejadorErrores.ingresarExcepcionEnLog(e, GestionPaislBean.class);
            RequestContext.getCurrentInstance().addCallbackParam("validationFailed", true);
            Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"));
        }
    }
    
    public void listaDpaActual(Long id) {
        nivelesDpa = fachada.getDPA(id);
        if (nivelesDpa != null) {
            if (nivelesDpa.size() > 1) {
                verCargaDPA = false;
            } else {
                verCargaDPA = true;
            }
        }
        verTraducciones = false;
    }

    public void crearDirectoriosPais() {
        try {
            facArchivo.crearDirectorioPais(paisSeleccionado.getId());
            RequestContext.getCurrentInstance().addCallbackParam("validationFailed", true);
            Messages.addFlashGlobalInfo(Utilidades.obtenerMensaje("admin.pais.msj.directorio.ok"));
        } catch (Exception e) {
            ManejadorErrores.ingresarExcepcionEnLog(e, GestionPaislBean.class);
            RequestContext.getCurrentInstance().addCallbackParam("validationFailed", true);
            Messages.addFlashGlobalError(Utilidades.obtenerMensaje("admin.pais.msj.directorio.error"));
        }
    }
    
    public void resetearSecuencias() {
        try {
            fachada.resetearSecuencias();
            RequestContext.getCurrentInstance().addCallbackParam("validationFailed", true);
            Messages.addFlashGlobalInfo(Utilidades.obtenerMensaje("admin.pais.msj.secuencia.ok"));
        } catch (Exception e) {
            ManejadorErrores.ingresarExcepcionEnLog(e, GestionPaislBean.class);
            RequestContext.getCurrentInstance().addCallbackParam("validationFailed", true);
            Messages.addFlashGlobalError(Utilidades.obtenerMensaje("admin.pais.msj.secuencia.error"));
        }
    }
    
    public void verTraducciones() { 
        listaDpaTraducida();
        verTraducciones=true;
    }
    
    public void listaDpaTraducida() { 
        nivelesDpaTraducido = fachada.getDPA(paisSeleccionado.getId(), idiomaDPA.getId()); 
    }
    
    public void asignarDPA(FileUploadEvent event) throws IOException {        
        Row rowDato;
        int rowNumber;
        int nivelDPA;
        UploadedFile file = event.getFile();
        try {
            Workbook wb = new XSSFWorkbook(file.getInputstream());
            Sheet hoja = wb.getSheetAt(0);
            rowNumber = 0;
            nivelDPA = 1;
            //fachada.borrarDPA(paisSeleccionado.getId());
            while (true) {
                rowDato = hoja.getRow(rowNumber);
                if (rowDato == null || rowNumber > 4) {
                    break; //hasta que encuentre una fila vacia o pase de 4 registros
                }
                String descripcion = getValorCelda(rowDato.getCell(0));
                if (!UtilCadena.isNullOVacio(descripcion)) {                    
                    fachada.guardarNivel(paisSeleccionado, descripcion, nivelDPA);                    
                    fachada.guardarTraduccion(paisSeleccionado.getId(), paisSeleccionado.getIdioma().getId(), descripcion, nivelDPA);                    
                }
                rowNumber++;
                nivelDPA++;
            }
            idiomaDPA = paisSeleccionado.getIdioma();
            nivelesDpa = fachada.getDPA(paisSeleccionado.getId());
            listaDpaTraducida();
            verCargaDPA = false;
            if (!UtilCadena.isNullOVacio(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("nivelGeneracion"))) {
                ((NivelGeneracionBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("nivelGeneracion")).cargarConfiguracionDPA();
            }
        } catch (Exception e) {
            ManejadorErrores.ingresarExcepcionEnLog(e, GestionPaislBean.class);
            RequestContext.getCurrentInstance().addCallbackParam("validationFailed", true);
            Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"));
        }
    }
    
    public void asignarTraducciones(FileUploadEvent event) throws IOException {
        
        Row rowDato;
        int rowNumber;
        UploadedFile file = event.getFile();
        try {
            Workbook wb = new XSSFWorkbook(file.getInputstream());
            Sheet hoja = wb.getSheetAt(0);
            rowNumber = 0;
            fachada.borrarTraducciones(paisSeleccionado.getId(), idiomaDPA.getId());
            while (true) {
                rowDato = hoja.getRow(rowNumber);
                if (rowDato == null || rowNumber > 4) {
                    break; //hasta que encuentre una fila vacia o pase de 4 registros
                }
                String descripcion = getValorCelda(rowDato.getCell(0));
                if (!UtilCadena.isNullOVacio(descripcion)) {                    
                    fachada.guardarTraduccion(paisSeleccionado.getId(), idiomaDPA.getId(), descripcion, rowNumber);
                    
                }
                rowNumber++;
            }
            listaDpaTraducida();
            if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("nivelGeneracion")){
                ((NivelGeneracionBean)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("nivelGeneracion")).cargarConfiguracionDPA();
            }
        } catch (Exception e) {
            ManejadorErrores.ingresarExcepcionEnLog(e, GestionPaislBean.class);
            RequestContext.getCurrentInstance().addCallbackParam("validationFailed", true);
            Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"));
        }
    }
    
    public void asignarBandera(FileUploadEvent event) {
        try {
            Parametro parametro = facParam.consultarParametro(ParamSistema.RUTA_ADJUNTOS.name());
            String rutaServidor = parametro.getValor()+"\\FLAGS\\";
            UploadedFile file = event.getFile();
            byte[] bandera = file.getContents();
            //Renombramos el archivo
            String nombreArchivo = file.getFileName();
            String prefijo = paisSeleccionado.getAbreviatura();
            String sufijo = nombreArchivo.substring(nombreArchivo.lastIndexOf("."), nombreArchivo.length());
            nombreArchivo = prefijo + sufijo;           
            String archivo = rutaServidor+nombreArchivo;
            crearArchivo(bandera, archivo);
            //se edita el pais seleccionado colocandole la bandera correspondiente y persistimo
            paisSeleccionado.setBandera(nombreArchivo);
            editarPais();
            Messages.addFlashGlobalInfo(Utilidades.obtenerMensaje("administracion.usuario.rol.banderaAsignada"));
        } catch (Exception ex) {
            ManejadorErrores.ingresarExcepcionEnLog(ex, GestionPaislBean.class);
            RequestContext.getCurrentInstance().addCallbackParam("validationFailed", true);
            Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"));
        }
    }
    
    public List<String> completeText(String query) {
        return fachada.getNombrePaisesAutocompletar(UtilCadena.toUpperCase(query),usuarioSesion.getUsuario().getIdioma().getId());                
    }

    private static void crearArchivo(byte[] bytes, String archivo) {
        FileOutputStream fos;
         ResourceBundle bundle = ResourceBundle.getBundle("etiquetas");         
        try {
            fos = new FileOutputStream(archivo);
            fos.write(bytes);
            fos.close();
        } catch (FileNotFoundException ex) {
            Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("administracion.usuario.rol.noEncontroArchivo"));
        } catch (IOException ex) {
            ManejadorErrores.ingresarExcepcionEnLog(ex, GestionPaislBean.class);
            Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"));
        }
    }

    public List<Tenant> getPaises() {
        return paises;
    }

    public void setPaises(List<Tenant> paises) {
        this.paises = paises;
    }

    public Tenant getPaisSeleccionado() {
        return paisSeleccionado;
    }

    public void setPaisSeleccionado(Tenant paisSeleccionado) {
        this.paisSeleccionado = paisSeleccionado;
    }
    
    public void ponerAbreviatura() {
        if(!UtilCadena.isNullOVacio(this.paisNuevo.getLocale())){
            Locale bLocale = new Locale(this.paisNuevo.getLocale().substring(0,2)
                    ,this.paisNuevo.getLocale().length()>2?this.paisNuevo.getLocale().substring(3,5):"");
            this.paisNuevo.setAbreviatura(bLocale.getISO3Country());
        }
    }

    public Tenant getPaisNuevo() {
        return paisNuevo;
    }

    public void setPaisNuevo(Tenant paisNuevo) {
        this.paisNuevo = paisNuevo;
    } 

    public LinkedHashMap<String,String> getLocales() {
        return locales;
    }

    public void setLocales(LinkedHashMap<String,String> locales) {
        this.locales = locales;
    }

    public List<String> getTimeZones() {
        return timeZones;
    }

    public void setTimeZones(List<String> timeZones) {
        this.timeZones = timeZones;
    }

    public List<Idioma> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<Idioma> idiomas) {
        this.idiomas = idiomas;
    }
    
    public UsuarioSesionBean getUsuarioSesion() {
        return usuarioSesion;
    }

    public void setUsuarioSesion(UsuarioSesionBean usuarioSesion) {
        this.usuarioSesion = usuarioSesion;
    }   

    public List<String> getNivelesDpa() {
        return nivelesDpa;
    }

    public void setNivelesDpa(List<String> nivelesDpa) {
        this.nivelesDpa = nivelesDpa;
    }

    public List<String> getNivelesDpaTraducido() {
        return nivelesDpaTraducido;
    }

    public void setNivelesDpaTraducido(List<String> nivelesDpaTraducido) {
        this.nivelesDpaTraducido = nivelesDpaTraducido;
    }
    
    public Idioma getIdiomaDPA() {
        return idiomaDPA;
    }

    public void setIdiomaDPA(Idioma idiomaDPA) {
        this.idiomaDPA = idiomaDPA;
    }
    
    public Usuario getSuperUsuario() {
        return superUsuario;
    }

    public void setSuperUsuario(Usuario superUsuario) {
        this.superUsuario = superUsuario;
    }

    public boolean isVerTraducciones() {
        return verTraducciones;
    }

    public boolean isVerCargaDPA() {
        return verCargaDPA;
    }
    
}
