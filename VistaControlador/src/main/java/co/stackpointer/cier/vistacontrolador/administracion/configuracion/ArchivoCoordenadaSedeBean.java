/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.administracion.configuracion;

import co.stackpointer.cier.modelo.fachada.EstablecimientoFacadeLocal;
import co.stackpointer.cier.modelo.utilidad.UtilCadena;
import co.stackpointer.cier.modelo.utilidad.UtilExportar;
import co.stackpointer.cier.vista.ManejadorErrores;
import co.stackpointer.cier.vista.Utilidades;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.omnifaces.util.Messages;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import static co.stackpointer.cier.vistacontrolador.bean.importacion.ImpUtil.getValorCelda;
import co.stackpointer.cier.vistacontrolador.validator.CoordenadaValidator;
import java.io.InputStream;
import java.util.ResourceBundle;
import javax.faces.context.FacesContext;
import net.sf.jasperreports.engine.JRParameter;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Usuario
 */
@ManagedBean(name = "archivoCoordenadaSede")
@ViewScoped
public class ArchivoCoordenadaSedeBean implements Serializable{
    
    @EJB
    EstablecimientoFacadeLocal fachada;
    protected Map parametros = new HashMap();
    protected List<ResultadoCoordenada> resultados;
    public boolean hayRegErroneos;
    
    public void cargaMasiva(FileUploadEvent event) throws IOException {
        String codigo, coordX, coordY;
        resultados = new ArrayList<ResultadoCoordenada>();        
        Row rowDato;
        int rowNumber;
        UploadedFile file = event.getFile();
        try {
            Workbook wb = new XSSFWorkbook(file.getInputstream());
            Sheet hoja = wb.getSheetAt(0);
            rowNumber = 0;
            hayRegErroneos = false;
            while (true) {
                rowNumber++;
                rowDato = hoja.getRow(rowNumber);
                if (rowDato == null) {
                    //lastRow = rowNumber;
                    break;
                }
                ResultadoCoordenada er = new ResultadoCoordenada();
                codigo = getValorCelda(wb, rowDato.getCell(0));
                er.setCodigo(codigo);
                coordX = getValorCelda(rowDato.getCell(1)).replace(".", ",");
                er.setCoordX(coordX);
                coordY = getValorCelda(rowDato.getCell(2)).replace(".", ",");
                er.setCoordY(coordY);

                //Validaciones de datos ingresados por fila
                if (UtilCadena.isNullOVacio(codigo)) {
                    er.setError(Utilidades.obtenerMensaje("administracion.parametros.archivoCoordenadaSede.sede.vacio"));
                    hayRegErroneos = true;
                    resultados.add(er);
                    continue;
                }
                if (UtilCadena.isNullOVacio(coordX)) {
                    er.setError(Utilidades.obtenerMensaje("administracion.parametros.archivoCoordenadaSede.coordX.vacio"));
                    hayRegErroneos = true;
                    resultados.add(er);
                    continue;
                }
                if (UtilCadena.isNullOVacio(coordY)) {
                    er.setError(Utilidades.obtenerMensaje("administracion.parametros.archivoCoordenadaSede.coordY.vacio"));
                    hayRegErroneos = true;
                    resultados.add(er);
                    continue;
                }
                if (!fachada.existeSede(codigo)) {
                    er.setError(Utilidades.obtenerMensaje("administracion.parametros.archivoCoordenadaSede.sede.noExiste"));
                    hayRegErroneos = true;
                    resultados.add(er);
                    continue;
                }
                if (CoordenadaValidator.formatoErrado(coordX)) {
                    er.setError(Utilidades.obtenerMensaje("administracion.parametros.archivoCoordenadaSede.coordX.formatoErrado"));
                    hayRegErroneos = true;
                    resultados.add(er);
                    continue;
                }
                if (CoordenadaValidator.formatoErrado(coordY)) {
                    er.setError(Utilidades.obtenerMensaje("administracion.parametros.archivoCoordenadaSede.coordY.formatoErrado"));
                    hayRegErroneos = true;
                    resultados.add(er);
                    continue;
                }
                if (fachada.sedeSinInstrumento(codigo)) {
                    er.setError(Utilidades.obtenerMensaje("administracion.parametros.archivoCoordenadaSede.sede.sinInstrumento"));
                    hayRegErroneos = true;
                    resultados.add(er);
                    continue;
                }

                int resultado = fachada.ingresarCoordenada(codigo, coordX.replace(",", "."), coordY.replace(",", "."));
                //se actualiza los datos
                if (resultado==0) {
                    er.setError(Utilidades.obtenerMensaje("administracion.parametros.archivoCoordenadaSede.error.transaccion"));
                    hayRegErroneos = true;
                    resultados.add(er);
                    continue;
                }else if (resultado==2){
                    er.setError(Utilidades.obtenerMensaje("administracion.parametros.archivoCoordenadaSede.sede.coordenada.digitada"));
                    hayRegErroneos = true;
                    resultados.add(er);
                    continue;
                }
                resultados.add(er);
            }

            if (hayRegErroneos) {
                rowDato = hoja.getRow(0);
                parametros.put("col1", getValorCelda(wb, rowDato.getCell(0)));
                parametros.put("col2", getValorCelda(wb, rowDato.getCell(1)));
                parametros.put("col3", getValorCelda(wb, rowDato.getCell(2)));
                parametros.put(JRParameter.REPORT_RESOURCE_BUNDLE, ResourceBundle.getBundle("etiquetas", FacesContext.getCurrentInstance().getViewRoot().getLocale()));
            }
        } catch (Exception e) {
            ManejadorErrores.ingresarExcepcionEnLog(e, ArchivoCoordenadaSedeBean.class);
            //UtilOut.println(e);
            Messages.addFlashGlobalError(Utilidades.obtenerMensaje("adm.parametros.config.carga.errorRegistro"));
            resultados.clear();
            hayRegErroneos = true;
        }
    }
    
    public StreamedContent getExportFile() {
        InputStream stream = null;
        try {
            stream = UtilExportar.getDocumentoXLSX("cargaCoordenadas", "ResultadoCargaCoordenadas", parametros, resultados);
        } catch (Exception ex) {
            ManejadorErrores.ingresarExcepcionEnLog(ex, ArchivoCoordenadaSedeBean.class); //UtilOut.println(ex);
            Messages.addFlashGlobalError(Utilidades.obtenerMensaje("adm.parametros.config.carga.errorRegistro"));
        } finally {
            try {
                stream.close();
            } catch (Exception ex) {
                stream = null;
            }
            return new DefaultStreamedContent(stream, "application/x-download", "ResultadoCargaCoordenadas.xlsx");
        }
    }

    public boolean isHayRegErroneos() {
        return hayRegErroneos;
    }

    public void setHayRegErroneos(boolean hayRegErroneos) {
        this.hayRegErroneos = hayRegErroneos;
    }
  
}
