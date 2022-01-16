/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.administracion.configuracion;

import co.stackpointer.cier.modelo.auditoria.RegistrarAuditoria;
import co.stackpointer.cier.modelo.entidad.dpa.ConfiguracionNivel;
import co.stackpointer.cier.modelo.entidad.dpa.Nivel;
import co.stackpointer.cier.modelo.entidad.establecimiento.Establecimiento;
import co.stackpointer.cier.modelo.entidad.establecimiento.Sede;
import co.stackpointer.cier.modelo.fachada.EstructuraDPAFacadeLocal;
import co.stackpointer.cier.modelo.tipo.BitacoraTransaccion;
import co.stackpointer.cier.modelo.tipo.ParamNivelConsul;
import co.stackpointer.cier.modelo.utilidad.UtilCadena;
import co.stackpointer.cier.modelo.utilidad.UtilExportar;
import co.stackpointer.cier.modelo.utilidad.UtilOut;
import co.stackpointer.cier.vista.Utilidades;
import co.stackpointer.cier.vistacontrolador.bean.LoginBean;
import co.stackpointer.cier.vistacontrolador.bean.UsuarioSesionBean;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import net.sf.jasperreports.engine.JRParameter;
import org.omnifaces.util.Messages;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean(name = "backupDpa")
@ViewScoped
public class BackupDPA implements Serializable {

    @EJB
    private EstructuraDPAFacadeLocal facEstDPA;
    
    @ManagedProperty(value = "#{login}")
    private LoginBean currentLogin;
    
    @ManagedProperty(value = "#{usuarioSesion}")
    private UsuarioSesionBean usuarioSesion;
    
    private Long maximoNivelDPA;
    private List<ComposicionDPA> registrosDPA;
    //private Long idMaximoNivelDPA;   
    
    private StreamedContent dpaConfigurationFile;
    
    public void createDPAConfigurationFile() {
        InputStream stream = null;
        try {
            Map parametros = llenarParametros();
            llenarRegistrosDPA();
            stream = UtilExportar.getDocumentoXLSX("cargaDPA", "BackupDPA", parametros, registrosDPA);
        } catch (Exception ex) {
            UtilOut.println(ex);
            Messages.addFlashGlobalError(Utilidades.obtenerMensaje("admistracion.backupdpa.error.exportar"));
        } finally {
            try {
                stream.close();
            } catch (Exception ex) {
                stream = null;
            }
            StreamedContent file = new DefaultStreamedContent(stream, "application/x-download", "backupDPA.xlsx");
            setDpaConfigurationFile(file);
        }
    }

    public StreamedContent getDpaConfigurationFile() {
        try {
            return dpaConfigurationFile;
        } finally {
            RegistrarAuditoria registrar = new RegistrarAuditoria();
            registrar.setFecha(new Date());
            registrar.setNombreMetodo(BitacoraTransaccion.BACKUP_DPA.name());
            registrar.setUsuario(usuarioSesion.getUsuario().getUsername());
            registrar.start();
        }
    }

    public void setDpaConfigurationFile(StreamedContent dpaConfigurationFile) {
        this.dpaConfigurationFile = dpaConfigurationFile;
    }    
    
    private Map llenarParametros() {
        //niveles DPA        
        List<ConfiguracionNivel> configuraciones = facEstDPA.consultarConfNivelesActual();
        Map<Long, String> listaNivelesConf = new HashMap<Long, String>();
        for (ConfiguracionNivel conf : configuraciones) {
            listaNivelesConf.put(conf.getNivel(), conf.getDescripcion());
        }
        // maximo nivel DPA
        if (!configuraciones.isEmpty()) {
            int indice = configuraciones.size() - 1;
            this.maximoNivelDPA = configuraciones.get(indice).getNivel();
            //this.idMaximoNivelDPA = configuraciones.get(indice).getId();
        } else {
            this.maximoNivelDPA = -1L;
            //this.idMaximoNivelDPA = -1L;
        }

        //Encabezados para el archivo
        Map parametros = new HashMap();
        parametros.put("colCode", Utilidades.obtenerMensaje("admistracion.backupdpa.codigo"));
        parametros.put("colName", Utilidades.obtenerMensaje("admistracion.backupdpa.nombre"));
        parametros.put("col1", listaNivelesConf.get(ParamNivelConsul.UNO.getNivel()));
        parametros.put("col2", listaNivelesConf.get(ParamNivelConsul.DOS.getNivel()));
        parametros.put("col3", listaNivelesConf.get(ParamNivelConsul.TRES.getNivel()));
        parametros.put("col4", listaNivelesConf.get(ParamNivelConsul.CUATRO.getNivel()));
        parametros.put("col5", listaNivelesConf.get(ParamNivelConsul.CINCO.getNivel()));
        parametros.put("col6", Utilidades.obtenerMensaje(ParamNivelConsul.ESTABLECIMIENTO.getEtiqueta()));
        parametros.put("col7", Utilidades.obtenerMensaje(ParamNivelConsul.SEDE.getEtiqueta()));
        parametros.put("col8", Utilidades.obtenerMensaje("admistracion.backupdpa.direccion"));
        parametros.put("col9", Utilidades.obtenerMensaje("admistracion.backupdpa.email"));
        parametros.put("col10", Utilidades.obtenerMensaje("admistracion.backupdpa.telefono"));
        parametros.put("col11", Utilidades.obtenerMensaje("admistracion.backupdpa.sector"));
        parametros.put("col12", Utilidades.obtenerMensaje("admistracion.backupdpa.autoridad"));
        parametros.put("col13", Utilidades.obtenerMensaje("admistracion.backupdpa.rector.nombre"));
        parametros.put("col14", Utilidades.obtenerMensaje("admistracion.backupdpa.rector.email"));
        parametros.put("col15", Utilidades.obtenerMensaje("admistracion.backupdpa.rector.telefono"));
        parametros.put("col16", Utilidades.obtenerMensaje("admistracion.backupdpa.tipo"));
        parametros.put(JRParameter.REPORT_RESOURCE_BUNDLE, ResourceBundle.getBundle("etiquetas", FacesContext.getCurrentInstance().getViewRoot().getLocale()));
        return parametros;
    }

    private void llenarRegistrosDPA() {
        registrosDPA = new ArrayList<ComposicionDPA>();
        String codigoPais = currentLogin.getPais().getCodigoPais();
        Nivel nivel0 = facEstDPA.consultarNivel(codigoPais);
        if (ParamNivelConsul.UNO.getNivel() <= maximoNivelDPA) {
            List<Nivel> listaNivel1 = cargarNivel(nivel0, ParamNivelConsul.UNO);
            for (Nivel nivel1 : listaNivel1) {
                ComposicionDPA reg1 = new ComposicionDPA();
                reg1.setCodNivel1(nivel1.getCodigo());
                reg1.setNomNivel1(nivel1.getDescripcion());
                if (ParamNivelConsul.DOS.getNivel() <= maximoNivelDPA) {
                    List<Nivel> listaNivel2 = cargarNivel(nivel1, ParamNivelConsul.DOS);
                    for (Nivel nivel2 : listaNivel2) {
                        ComposicionDPA reg2 = new ComposicionDPA();
                        reg2.setCodNivel1(nivel1.getCodigo());
                        reg2.setNomNivel1(nivel1.getDescripcion());
                        reg2.setCodNivel2(nivel2.getCodigo());
                        reg2.setNomNivel2(nivel2.getDescripcion());
                        if (ParamNivelConsul.TRES.getNivel() <= maximoNivelDPA) {
                            List<Nivel> listaNivel3 = cargarNivel(nivel2, ParamNivelConsul.TRES);
                            for (Nivel nivel3 : listaNivel3) {
                                ComposicionDPA reg3 = new ComposicionDPA();
                                reg3.setCodNivel1(nivel1.getCodigo());
                                reg3.setNomNivel1(nivel1.getDescripcion());
                                reg3.setCodNivel2(nivel2.getCodigo());
                                reg3.setNomNivel2(nivel2.getDescripcion());
                                reg3.setCodNivel3(nivel3.getCodigo());
                                reg3.setNomNivel3(nivel3.getDescripcion());
                                if (ParamNivelConsul.CUATRO.getNivel() <= maximoNivelDPA) {
                                    List<Nivel> listaNivel4 = cargarNivel(nivel3, ParamNivelConsul.CUATRO);
                                    for (Nivel nivel4 : listaNivel4) {
                                        ComposicionDPA reg4 = new ComposicionDPA();
                                        reg4.setCodNivel1(nivel1.getCodigo());
                                        reg4.setNomNivel1(nivel1.getDescripcion());
                                        reg4.setCodNivel2(nivel2.getCodigo());
                                        reg4.setNomNivel2(nivel2.getDescripcion());
                                        reg4.setCodNivel3(nivel3.getCodigo());
                                        reg4.setNomNivel3(nivel3.getDescripcion());
                                        reg4.setCodNivel4(nivel4.getCodigo());
                                        reg4.setNomNivel4(nivel4.getDescripcion());
                                        if (ParamNivelConsul.CINCO.getNivel() <= maximoNivelDPA) {
                                            List<Nivel> listaNivel5 = cargarNivel(nivel4, ParamNivelConsul.CINCO);
                                            for (Nivel nivel5 : listaNivel5) {
                                                ComposicionDPA reg5 = new ComposicionDPA();
                                                reg5.setCodNivel1(nivel1.getCodigo());
                                                reg5.setNomNivel1(nivel1.getDescripcion());
                                                reg5.setCodNivel2(nivel2.getCodigo());
                                                reg5.setNomNivel2(nivel2.getDescripcion());
                                                reg5.setCodNivel3(nivel3.getCodigo());
                                                reg5.setNomNivel3(nivel3.getDescripcion());
                                                reg5.setCodNivel4(nivel4.getCodigo());
                                                reg5.setNomNivel4(nivel4.getDescripcion());
                                                reg5.setCodNivel5(nivel5.getCodigo());
                                                reg5.setNomNivel5(nivel5.getDescripcion());
                                                todoRegistro(reg5, nivel5);
                                            }
                                        } else {
                                            todoRegistro(reg4, nivel4);
                                        }
                                    }
                                } else {
                                    todoRegistro(reg3, nivel3);
                                }
                            }
                        } else {
                            todoRegistro(reg2, nivel2);
                        }
                    }
                } else {
                    todoRegistro(reg1, nivel1);
                }
            }
        }
    }
    
    private void todoRegistro(ComposicionDPA reg, Nivel nivel) { 
        String codNivel1 = UtilCadena.isNullOVacio(reg.getCodNivel1()) ? null : reg.getCodNivel1();
        String codNivel2 = UtilCadena.isNullOVacio(reg.getCodNivel2()) ? null : reg.getCodNivel2();
        String codNivel3 = UtilCadena.isNullOVacio(reg.getCodNivel3()) ? null : reg.getCodNivel3();
        String codNivel4 = UtilCadena.isNullOVacio(reg.getCodNivel4()) ? null : reg.getCodNivel4();
        String codNivel5 = UtilCadena.isNullOVacio(reg.getCodNivel5()) ? null : reg.getCodNivel5();
        String nomNivel1 = UtilCadena.isNullOVacio(reg.getNomNivel1()) ? null : reg.getNomNivel1();
        String nomNivel2 = UtilCadena.isNullOVacio(reg.getNomNivel2()) ? null : reg.getNomNivel2();
        String nomNivel3 = UtilCadena.isNullOVacio(reg.getNomNivel3()) ? null : reg.getNomNivel3();
        String nomNivel4 = UtilCadena.isNullOVacio(reg.getNomNivel4()) ? null : reg.getNomNivel4();
        String nomNivel5 = UtilCadena.isNullOVacio(reg.getNomNivel5()) ? null : reg.getNomNivel5();        
        
        List<Establecimiento> establecimientos = facEstDPA.consultarEstablecimientos(nivel.getId());
        if (!establecimientos.isEmpty()) {
            for (Establecimiento establecimiento : establecimientos) {    
                ComposicionDPA regAux1 = new ComposicionDPA(codNivel1, nomNivel1, codNivel2, nomNivel2, codNivel3, nomNivel3, codNivel4, nomNivel4, codNivel5, nomNivel5);
                regAux1.setCodEstablecimiento(establecimiento.getCodigo());
                regAux1.setNomEstablecimiento(establecimiento.getNombre());
                List<Sede> sedes = facEstDPA.consultarSedes(establecimiento.getId());                
                if (!sedes.isEmpty()) {
                    for (Sede sede : sedes) {
                        ComposicionDPA regAux2 = new ComposicionDPA(codNivel1, nomNivel1, codNivel2, nomNivel2, codNivel3, nomNivel3, codNivel4, nomNivel4, codNivel5, nomNivel5);
                        String direccion = UtilCadena.isNullOVacio(sede.getDireccion()) ? null : sede.getDireccion();
                        String email = UtilCadena.isNullOVacio(sede.getEmailSede()) ? null : sede.getEmailSede();
                        String telefono = UtilCadena.isNullOVacio(sede.getTelefono()) ? null : sede.getTelefono();
                        String codSector = UtilCadena.isNullOVacio(sede.getCodSector()) ? null : sede.getCodSector();
                        String nomSector = UtilCadena.isNullOVacio(sede.getNomSector()) ? null : sede.getNomSector();
                        String codAutoridad = UtilCadena.isNullOVacio(sede.getCodAutoridad()) ? null : sede.getCodAutoridad();
                        String nomAutoridad = UtilCadena.isNullOVacio(sede.getNomAutoridad()) ? null : sede.getNomAutoridad();
                        String rector = UtilCadena.isNullOVacio(sede.getRector()) ? null : sede.getRector();
                        String emailRector = UtilCadena.isNullOVacio(sede.getEmailRector()) ? null : sede.getEmailRector();
                        String telRector = UtilCadena.isNullOVacio(sede.getTelRector()) ? null : sede.getTelRector();
                        regAux2.setCodEstablecimiento(establecimiento.getCodigo());
                        regAux2.setNomEstablecimiento(establecimiento.getNombre());
                        regAux2.setDireccion(direccion);
                        regAux2.setEmail(email);
                        regAux2.setTelefono(telefono);
                        regAux2.setCodSector(codSector);
                        regAux2.setNomSector(nomSector);
                        regAux2.setCodAutoridad(codAutoridad);
                        regAux2.setNomAutoridad(nomAutoridad);
                        regAux2.setCodSede(sede.getCodigo());
                        regAux2.setNomSede(sede.getNombre());
                        regAux2.setNomRector(rector);
                        regAux2.setEmailRector(emailRector);
                        regAux2.setTelRector(telRector);
                        registrosDPA.add(regAux2);
                    }
                } else {
                    registrosDPA.add(regAux1);
                }
            }
        } else {
            registrosDPA.add(reg);
        }        
    }
    
    private List<Nivel> cargarNivel(Nivel nivelSeccionado, ParamNivelConsul nivelConsulta) {
        return facEstDPA.consultarNiveles(nivelConsulta, nivelSeccionado.getId());
    }
    
    public LoginBean getCurrentLogin() {
        return currentLogin;
    }

    public void setCurrentLogin(LoginBean currentLogin) {
        this.currentLogin = currentLogin;
    }
    
    public UsuarioSesionBean getUsuarioSesion() {
        return usuarioSesion;
    }

    public void setUsuarioSesion(UsuarioSesionBean usuarioSesion) {
        this.usuarioSesion = usuarioSesion;
    }
    
}
