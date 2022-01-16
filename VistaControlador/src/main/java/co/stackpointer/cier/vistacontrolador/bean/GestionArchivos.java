/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.bean;

import co.stackpointer.cier.modelo.entidad.digitado.Adjunto;
import co.stackpointer.cier.modelo.entidad.digitado.Elemento;
import co.stackpointer.cier.modelo.entidad.digitado.RespuestaDig;
import co.stackpointer.cier.modelo.entidad.instrumento.Pregunta;
import co.stackpointer.cier.modelo.entidad.instrumento.Respuesta;
import co.stackpointer.cier.modelo.fachada.ArchivoFacadeLocal;
import co.stackpointer.cier.modelo.fachada.InstrumentoFacadeLocal;
import co.stackpointer.cier.modelo.fachada.ParametrosFacadeLocal;
import co.stackpointer.cier.modelo.utilidad.UtilOut;
import co.stackpointer.cier.vista.ManejadorErrores;
import co.stackpointer.cier.vista.Utilidades;
import co.stackpointer.cier.vistacontrolador.bean.importacion.Log;
import co.stackpointer.cier.vistacontrolador.consulta.basica.ConsultaInstrumentoBean;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author user
 */
@ManagedBean(name = "gestionArchivos")
@ViewScoped
public class GestionArchivos implements Serializable {

    private List<Log> logs;
    private boolean swLog;
    @ManagedProperty(value = "#{usuarioSesion}")
    private UsuarioSesionBean usuario;
    private RespuestaDig respuestaDig;
    @EJB
    private InstrumentoFacadeLocal fInstrumento;
    @EJB
    private ArchivoFacadeLocal facArchivo;
    private List<UploadedFile> archivosCargados;
    private Boolean adjuntar;

    public void cargarArchivo(FileUploadEvent event) throws IOException {
        if (archivosCargados == null) {
            archivosCargados = new ArrayList<UploadedFile>();
        }

        archivosCargados.add(event.getFile());

        /*Parametro parametro = facParam.consultarParametro(ParamSistema.RUTA_ADJUNTOS.name());
         String rutaServidor = parametro.getValor();
         cargaAdjuntoAlServidor(rutaServidor, event);*/
    }

    public void AdicionarAdjuntoARespuestaDigitada(Adjunto adjunto) {
        if (this.respuestaDig.getAdjuntosList() == null) {
            List<Adjunto> listaAdjunto = new ArrayList<Adjunto>();
            this.respuestaDig.setAdjuntosList(listaAdjunto);
        }
        this.respuestaDig.getAdjuntosList().add(adjunto);
        this.respuestaDig.setValor("1");
        //fInstrumento.guardarRespuestaDigitado(respuesta);
    }

    public void guardarArchivo() {
        try {
            if (archivosCargados == null || archivosCargados.isEmpty()) {
                return;
            }
            List<Adjunto> listaAdjunto = new ArrayList<Adjunto>();
            for (UploadedFile uploadedFile : archivosCargados) {
                String rutaServidor = facArchivo.getPathname(respuestaDig.getElemento().getInstrumentoDigitado().getId());
                String prefix = facArchivo.getPrefix(uploadedFile.getFileName(), respuestaDig.getElemento().getInstrumentoDigitado().getId());
                //String prefix = respuestaDig.getElemento().getId() + "_" + uploadedFile.getFileName();
                String suffix = prefix.substring(prefix.lastIndexOf("."), prefix.length());
                String pathName = facArchivo.getPathname(respuestaDig.getElemento().getInstrumentoDigitado().getId());
                File destino = new File(pathName);
                if (facArchivo.subirArchivo(destino, prefix, uploadedFile.getInputstream())) {
                    Adjunto nuevoAdjunto = new Adjunto();
                    nuevoAdjunto.setRuta(rutaServidor + prefix);
                    nuevoAdjunto.setTipo(suffix.toUpperCase());
                    nuevoAdjunto.setContentType(uploadedFile.getContentType());
                    nuevoAdjunto.setNombre(prefix);
                    nuevoAdjunto.setUsuarioCreacion(usuario.getUsuario().getUsername());
                    nuevoAdjunto.setRespuestaDigitada(this.respuestaDig);
                    listaAdjunto.add(nuevoAdjunto);
                    nuevoAdjunto = fInstrumento.insertarAdjunto(this.respuestaDig.getId(), nuevoAdjunto);
                    AdicionarAdjuntoARespuestaDigitada(nuevoAdjunto);
                }
            }
            //respuestaDig.setValor("1");
            //respuestaDig.setAdjuntosList(listaAdjunto);
            
            //guardarRespuestaDigitada();
            //buscarRespuesta(respuestaDig);
            archivosCargados = new ArrayList<UploadedFile>();
            Utilidades.mostrarMensajeInfo(Utilidades.obtenerMensaje("aplicacion.general.operacionExitosa"), Utilidades.obtenerMensaje("aplicacion.general.registroEnServidor"));

        } catch (IOException ex) {
            Utilidades.mostrarMensajeError(Utilidades.obtenerMensaje("adm.parametros.config.carga.errorRegistro"), Utilidades.obtenerMensaje("adm.parametros.config.carga.errorRegistro"));
            ManejadorErrores.ingresarExcepcionEnLog(ex, GestionArchivos.class);
        } catch (Exception e) {
            Utilidades.mostrarMensajeError(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"), Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"));
            ManejadorErrores.ingresarExcepcionEnLog(e, GestionArchivos.class);
        }
    }

    public void buscarRespuesta(RespuestaDig respDig) {
        try {
            adjuntar = false;
            respuestaDig = new RespuestaDig();
            if (respDig.getElemento() != null
                    && respDig.getElemento().getInstrumentoDigitado() != null
                    && respDig.getElemento().getInstrumentoDigitado().getId() != null) {
                if (respDig.getId() != null) {
                    respuestaDig = fInstrumento.obtenerRespuestaDigitadaPorId(respDig.getId());
                } else {
                    respuestaDig = respDig;
                }
                adjuntar = true;
            } else {
                adjuntar = false;
                Utilidades.mostrarMensajeError(Utilidades.obtenerMensaje("aplicacion.general.errorValidacion"), Utilidades.obtenerMensaje("dig.creacion.lbl.adjunto.instrumento"));
            }
        } catch (Exception ex) {
            ManejadorErrores.ingresarExcepcionEnLog(ex, GestionArchivos.class);
        }
    }

    public void guardarRespuestaDigitada() {
        try {
            fInstrumento.actualizarRespuestaDigitada(respuestaDig);
        } catch (Exception e) {
            ManejadorErrores.ingresarExcepcionEnLog(e, GestionArchivos.class);
        }
    }

    public List<Log> getLogs() {
        return logs;
    }

    public void setLogs(List<Log> logs) {
        this.logs = logs;
    }

    public boolean isSwLog() {
        return swLog;
    }

    public void setSwLog(boolean swLog) {
        this.swLog = swLog;
    }

    public void postProcessXLS(Object document) {
        try {
            HSSFWorkbook wb = (HSSFWorkbook) document;
            HSSFSheet sheet = wb.getSheetAt(0);
            HSSFRow header = sheet.getRow(0);
            HSSFCellStyle cellStyle = wb.createCellStyle();
            cellStyle.setFillForegroundColor(HSSFColor.BLUE_GREY.index);
            cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            cellStyle.setFillBackgroundColor(HSSFColor.GREEN.index);
            cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
            HSSFFont font = wb.createFont();
            font.setFontName(HSSFFont.FONT_ARIAL);
            font.setFontHeightInPoints((short) 10);
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            font.setColor(HSSFColor.WHITE.index);
            cellStyle.setFont(font);

            for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
                HSSFCell cell = header.getCell(i);
                cell.setCellStyle(cellStyle);
                sheet.autoSizeColumn((short) i); //ajusta el ancho de la columna
            }
            for (int x = 1; x < sheet.getPhysicalNumberOfRows(); x++) {
                HSSFRow row = sheet.getRow(x);
                for (int y = 0; y < header.getPhysicalNumberOfCells(); y++) {
                    HSSFCell cell = row.getCell(y);
                    cell.setCellValue(cell.getStringCellValue().replaceAll("<table>", "")
                            .replaceAll("<tr>", "")
                            .replaceAll("<td class=\"noBorder\">", "")
                            .replaceAll("</td>", "")
                            .replaceAll("</tr>", "")
                            .replaceAll("</table>", "")
                            .trim());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public UsuarioSesionBean getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioSesionBean usuario) {
        this.usuario = usuario;
    }

    public RespuestaDig getRespuestaDig() {
        return respuestaDig;
    }

    public void setRespuestaDig(RespuestaDig respuestaDig) {
        this.respuestaDig = respuestaDig;
    }

    public void resetar() {
        this.respuestaDig = null;
    }

    public void descargarDocumento(Adjunto adjunto) throws IOException {
        FacesContext contexto = FacesContext.getCurrentInstance();
        HttpServletResponse respuesta = (HttpServletResponse) contexto.getExternalContext().getResponse();
        respuesta.setHeader("Content-disposition", "attachment; filename=" + adjunto.getNombre());
        respuesta.setContentType(adjunto.getContentType());
        respuesta.addHeader("Content-Type", "application/x-download");
        ServletOutputStream out = respuesta.getOutputStream();
        byte[] bytes = new byte[1000];
        int read = 0;
        FileInputStream fis = new FileInputStream(adjunto.getRuta());
        while ((read = fis.read(bytes)) != -1) {
            out.write(bytes, 0, read);
        }

        out.flush();
        out.close();
        contexto.responseComplete();
    }

    public void eliminarAdjunto(Adjunto adjunto) {
        try {
            String nombre = adjunto.getRuta();
            File f = new File(nombre);
            if (f.exists() && adjunto.getId() != null) {
                if (facArchivo.eliminarArchivo(adjunto, usuario.getUsuario())) {

                    f.delete();
                    //respuestaDig = adjunto.getRespuestaDigitada();
                    respuestaDig.getAdjuntosList().remove(adjunto);
                    if (respuestaDig.getAdjuntosList().isEmpty()) {
                        respuestaDig.setValor("0");
                    }
                    if (adjunto.getId() != null) {
                        fInstrumento.eliminarAdjunto(adjunto);
                    }

                    Utilidades.mostrarMensajeInfo(Utilidades.obtenerMensaje("aplicacion.general.operacionExitosa"), Utilidades.obtenerMensaje("aplicacion.general.eliminacionEnServidor"));
                } else {
                    Utilidades.mostrarMensajeAdvertencia(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"), Utilidades.obtenerMensaje("aplicacion.general.errorEliminarEnServidor"));
                }
            } else {
                Utilidades.mostrarMensajeAdvertencia(Utilidades.obtenerMensaje("consulta.general.noExisteArchivoServidor"), Utilidades.obtenerMensaje("consulta.general.archivoSolicitado") + " " + nombre + Utilidades.obtenerMensaje("consulta.general.noExisteArchivoServidor"));
            }
        } catch (Exception e) {
            Utilidades.mostrarMensajeError(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"), "");
            ManejadorErrores.ingresarExcepcionEnLog(e, GestionArchivos.class);
        }
    }
    

    public Boolean getAdjuntar() {
        return adjuntar;
    }

    public void setAdjuntar(Boolean adjuntar) {
        this.adjuntar = adjuntar;
    }
}
