package co.stackpointer.cier.modelo.utilidad;



import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.faces.context.FacesContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JRVirtualizer;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.fill.JRSwapFileVirtualizer;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRSwapFile;


/**
 * Clase auxiliar de utilidad que facilita la exportaci�n de un reporte creado
 * mediante <i>JasperReports</i>, o mediante <i>DynamicJasper</i>.
 * @author  Fabio Turizo.
 * @version 1.0
 */
public class UtilExportar {

    private static final String DIRECTORIO_REPORTES = "/resources/reports/";
    private static final String EXTENSION_JASPER = ".jasper";
    private static final String EXTENSION_JRXML = ".jrxml";

    private static Locale obtenerLocaleAplicacion() {
        return FacesContext.getCurrentInstance().getApplication().getDefaultLocale();
    }

    /**
     * Genera un nuevo reporte estructurado en JasperReports y lo exporta a la salida
     * del servlet que atiende el contexto JSF. Este reporte es generado en formato PDF,
     * y es generado a partir de los parametros suministrados.
     * @param   reporte     El nombre del archivo .jasper que contiene la estructura del reporte a exportar (sin la extensi�n).
     * @param   nombre      El nombre del archivo en el que se exportar� el reporte (sin la extensi�n).
     * @param   parametros  Un mapa que contiene los parametros a pasar directamente a la estructura del reporte.
     * @param   datos       La lista de datos que se utilizar�n para llenar el reporte.
     * @throws  JRException En caso de que ocurra un error al momento de construir y generar el reporte v�a JasperReports.
     * @throws  IOException En caso de que ocurran fallas en la lectura/escritura de datos.
     */
    @SuppressWarnings("unchecked")
    public static void exportarDocumentoPDF(String carpeta, String reporte, String nombre, Map parametros, List datos) throws JRException, IOException {
        exportarDocumento(carpeta, reporte, nombre, parametros, datos, new JRPdfExporter());
    }
    
   public static InputStream getInformeGeneral(String carpeta, String reporte, Map parametros, List datos) throws JRException, IOException {
        return getInformeGeneral(carpeta, reporte, parametros, datos, new JRPdfExporter());
    }
    
    public static void exportarInformeGeneral(String carpeta, String reporte, String nombre, Map parametros, List datos) throws JRException, IOException {
        exportarInformeGeneral(carpeta, reporte, nombre, parametros, datos, new JRPdfExporter());
    }
    
    public static void exportarDocumentoXLSX(String carpeta, String reporte, String nombre, Map parametros, List datos) throws JRException, IOException {
        exportarDocumento(carpeta, reporte, nombre, parametros, datos, new JRXlsxExporter());
    }

    public static InputStream getDocumentoXLSX(String carpeta, String reporte, Map parametros, List datos) throws JRException, IOException {
        return getDocumento(carpeta, reporte, parametros, datos, new JRXlsxExporter());
    }

    /*@SuppressWarnings("unchecked")
    public static void exportarDocumentoXLS(String nombre, ConfiguracionReporte conf, Collection datos) throws JRException,
                                                                                                                IOException {
        exportarDocumento(nombre, conf, datos, new JRXlsxExporter());
    }
    
    public static void exportarDocumentoPDF(String nombre, ConfiguracionReporte conf, Collection datos) throws JRException,
                                                                                                                IOException {
        exportarDocumento(nombre, conf, datos, new JRPdfExporter());
    }*/
    
    private static void exportarDocumento(FacesContext contexto, String nombre, JasperPrint impresora, JRExporter exportador) throws IOException, JRException {
        HttpServletResponse respuesta = (HttpServletResponse)contexto.getExternalContext().getResponse();

        if(exportador instanceof JRPdfExporter){
            nombre += ".pdf";
        }else if(exportador instanceof JRXlsxExporter){
            nombre += ".xlsx";
        }
        
        respuesta.setHeader("Content-disposition", "attachment; filename=" + nombre);
        respuesta.setContentType("application/x-download");
        respuesta.addHeader("Content-Type", "application/x-download");

        exportador.setParameter(JRExporterParameter.JASPER_PRINT, impresora);
        exportador.setParameter(JRExporterParameter.OUTPUT_STREAM, respuesta.getOutputStream());
        if(exportador instanceof JRXlsxExporter){
            exportador.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE,Boolean.TRUE);
            exportador.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
            exportador.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
            exportador.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
        }
        

        
        exportador.exportReport();
        contexto.responseComplete();
    }

    @SuppressWarnings("unchecked")
    private static void exportarDocumento(String carpeta, String reporte, String nombre, Map parametros, List datos, JRExporter exportador) throws JRException, IOException {
        FacesContext contexto = FacesContext.getCurrentInstance();
        String directorioBase = ((ServletContext)contexto.getExternalContext().getContext()).getRealPath(DIRECTORIO_REPORTES+carpeta+"/");
        parametros.put("SUBREPORT_DIR", directorioBase);
        parametros.put(JRParameter.REPORT_LOCALE, obtenerLocaleAplicacion());

        String archivo = directorioBase + "/" + reporte + EXTENSION_JASPER;
        JasperPrint impresora = JasperFillManager.fillReport(archivo, parametros, new JRBeanCollectionDataSource(datos));
        exportarDocumento(contexto, nombre, impresora, exportador);
    }
    
    
    @SuppressWarnings("unchecked")
    private static InputStream getDocumento(String carpeta, String reporte, Map parametros, List datos, JRExporter exportador) throws JRException, IOException {
        FacesContext contexto = FacesContext.getCurrentInstance();
        String directorioBase = ((ServletContext)contexto.getExternalContext().getContext()).getRealPath(DIRECTORIO_REPORTES+carpeta+"/");
        parametros.put("SUBREPORT_DIR", directorioBase);
        parametros.put(JRParameter.REPORT_LOCALE, obtenerLocaleAplicacion());

        String archivo = directorioBase + "/" + reporte + EXTENSION_JASPER;
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(new File(archivo));
        jasperReport.setProperty("net.sf.jasperreports.awt.ignore.missing.font", "true");        
        
        JasperPrint impresora = JasperFillManager.fillReport(jasperReport, parametros, new JRBeanCollectionDataSource(datos));
        
        HttpServletResponse respuesta = (HttpServletResponse)contexto.getExternalContext().getResponse();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        
        
        respuesta.setHeader("Content-disposition", "attachment; filename=xxx.xlsx" );
        respuesta.setContentType("application/x-download");
        respuesta.addHeader("Content-Type", "application/x-download");

        exportador.setParameter(JRExporterParameter.JASPER_PRINT, impresora);
        exportador.setParameter(JRExporterParameter.OUTPUT_STREAM, os);
        if(exportador instanceof JRXlsxExporter){
            exportador.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE,Boolean.TRUE);
            exportador.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
            exportador.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
            exportador.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
        }
        
        exportador.exportReport();
        
        return new ByteArrayInputStream(os.toByteArray());

    }
    
    @SuppressWarnings("unchecked")
    private static void exportarInformeGeneral(String carpeta, String reporte, String nombre, Map parametros, List datos, JRExporter exportador) throws JRException, IOException {
        FacesContext contexto = FacesContext.getCurrentInstance();
        String directorioBase = ((ServletContext)contexto.getExternalContext().getContext()).getRealPath(DIRECTORIO_REPORTES+carpeta+"/");
        parametros.put("SUBREPORT_DIR", directorioBase);
        parametros.put(JRParameter.REPORT_LOCALE, obtenerLocaleAplicacion());
        
        
        String archivo = directorioBase + "/" + reporte + EXTENSION_JASPER;
        
        JRSwapFile swapFile = new JRSwapFile(".", 1024, 1024);
        JRVirtualizer virtualizer = new JRSwapFileVirtualizer(100, swapFile, true);
        parametros.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);
        
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(new File(archivo));
        jasperReport.setProperty("net.sf.jasperreports.awt.ignore.missing.font", "true");        
        JasperPrint impresora = JasperFillManager.fillReport(jasperReport, parametros, new JRBeanCollectionDataSource(datos));
                
        exportarDocumento(contexto, nombre, impresora, exportador);
    }
    
    
         
     @SuppressWarnings("unchecked")
    private static InputStream getInformeGeneral(String carpeta, String reporte, Map parametros, List datos, JRExporter exportador) throws JRException, IOException {
        FacesContext contexto = FacesContext.getCurrentInstance();
        String directorioBase = ((ServletContext)contexto.getExternalContext().getContext()).getRealPath(DIRECTORIO_REPORTES+carpeta+"/");
        parametros.put("SUBREPORT_DIR", directorioBase);
        parametros.put(JRParameter.REPORT_LOCALE, obtenerLocaleAplicacion());
        
        
        String archivo = directorioBase + "/" + reporte + EXTENSION_JASPER;
        try{
        JRSwapFile swapFile = new JRSwapFile(".", 1024, 1024);
        JRVirtualizer virtualizer = new JRSwapFileVirtualizer(100, swapFile, true);
        parametros.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);
        }catch(Exception ei){
            ei.printStackTrace();
        }
        
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(new File(archivo));
        jasperReport.setProperty("net.sf.jasperreports.awt.ignore.missing.font", "true");        
        JasperPrint impresora = JasperFillManager.fillReport(jasperReport, parametros, new JRBeanCollectionDataSource(datos));
                
        return new ByteArrayInputStream(JasperExportManager.exportReportToPdf(impresora));
    }
    
/*    private static class ExpresionVacia implements CustomExpression{
        
        public Object evaluate(Map fields, Map variables, Map parameters) {
            return " ";
        }

        public String getClassName() {
            return String.class.getName();
        }
    }
    
    private static void exportarDocumento(String nombre, ConfiguracionReporte conf, Collection datos, JRExporter exportador) throws ColumnBuilderException, JRException,
                                                                                                                                       IOException {
        
        FacesContext contexto = FacesContext.getCurrentInstance();
        String directorioBase = ((ServletContext)contexto.getExternalContext().getContext()).getRealPath(DIRECTORIO_REPORTES);
        
        String archivo = directorioBase + "/" + conf.plantilla + EXTENSION_JRXML;
        
        DynamicReportBuilder builder = new DynamicReportBuilder();
        builder.setTemplateFile(archivo,true, false, false, false);
        if(conf.altoDetalles != null){
            builder.setDetailHeight(conf.altoDetalles);
        }
        for(InfoColumna columna : conf.columnas){
            ColumnBuilder cBuilder = ColumnBuilder.getNew();
            if(columna.campo == null){
                cBuilder.setCustomExpression(new ExpresionVacia());
            }else{
                cBuilder.setColumnProperty(columna.campo, columna.clase.getName());
            }
            AbstractColumn aColumna = cBuilder.build();
            aColumna.setTitle(columna.nombre);
            aColumna.setWidth(columna.ancho);
            aColumna.setBlankWhenNull(false);
            aColumna.setHeaderStyle(columna.estiloCabecera());
            if(columna.estilosCondicionales != null){
                aColumna.setConditionalStyles(columna.estilosCondicionales);
            } else{
                aColumna.setStyle(columna.estilo());
            }
            if (columna.totalizar) {
                builder.addGlobalFooterVariable(aColumna, DJCalculation.SUM);
            }
            if (columna.monetario){
                aColumna.setPattern("$###,##0.00");
            }else if(columna.porcentaje){
                aColumna.setPattern("###,##0%");
            }else if(columna.clase != null && (columna.clase == Double.class || columna.clase == Long.class)){
                aColumna.setPattern("###,##0.00");
            }
            builder.addColumn(aColumna);
        }
        builder.setPrintColumnNames(true);
        if(conf.textoTotales != null){
            builder.setGrandTotalLegend(conf.textoTotales);
            builder.setGrandTotalLegendStyle(conf.estiloTotal());
        }
        builder.setUseFullPageWidth(conf.usarAnchoPaginaCompleto);
        builder.setIgnorePagination(conf.ignorarPaginacion);
        builder.setWhenNoDataAllSectionNoDetail();
        
        if(conf.superColumnas != null){
            for(SuperColumna columna : conf.superColumnas){
                builder.setColspan(columna.posicion, columna.columnas, columna.nombre, columna.estilo());
            }
        }
        
        Map parametros = new HashMap();
        parametros.put("SUBREPORT_DIR", directorioBase);
        parametros.put(JRParameter.REPORT_LOCALE, obtenerLocaleAplicacion());
        if(conf.parametros != null){
            parametros.putAll(conf.parametros);
        }
        
        JasperPrint impresora = DynamicJasperHelper.generateJasperPrint(builder.build(), new ClassicLayoutManager(), new JRBeanCollectionDataSource(datos), parametros);
        exportarDocumento(contexto, nombre, impresora, exportador);
    }
    
    public static class InfoColumna{
        
        private String campo;
        private String nombre;
        private Class clase;
        
        private boolean estirar;
        private boolean totalizar;
        private int ancho;
        private String fuente = "Tahoma";
        private int tama�o = 8;
        private int tama�oCabecera = 8;
        
        private HorizontalAlign alHorizontal;
        private VerticalAlign alVertical;
        
        private boolean monetario;
        private boolean porcentaje;
        
        private List<ConditionalStyle> estilosCondicionales;

        public InfoColumna(String nombre) {
            this.nombre = nombre;
        }

        public InfoColumna(String campo, String nombre, Class clase) {
            this(nombre);
            this.campo = campo;
            this.clase = clase;
        }
        
        public InfoColumna ancho(int ancho){
            this.ancho = ancho;
            return this;
        }
        
        public InfoColumna fuente(String fuente){
            this.fuente = fuente;
            return this;
        }
        
        public InfoColumna tama�o(int tama�o){
            this.tama�o = tama�o;
            return this;
        }
        
        public InfoColumna tama�oCabecera(int tama�o){
            this.tama�oCabecera = tama�o;
            return this;
        }
        
        public InfoColumna alHorizontal(HorizontalAlign al){
            this.alHorizontal = al;
            return this;
        }

        public InfoColumna alVertical(VerticalAlign al) {
            this.alVertical = al;
            return this;
        }
        
        public InfoColumna estirar(){
            this.estirar = true;
            return this;
        }
        
        private Style estilo(){
            Style estilo = new Style();
            estilo.setFont(new Font(tama�o, fuente, false, true, false));
            estilo.setBorder(Border.THIN);
            estilo.setStreching(Stretching.RELATIVE_TO_TALLEST_OBJECT);
            estilo.getFont().setItalic(false);
            estilo.setStretchWithOverflow(true);
            if (alHorizontal != null) {
                estilo.setHorizontalAlign(alHorizontal);
            }
            if (alVertical != null) {
                estilo.setVerticalAlign(alVertical);
            }
            return estilo;
        }
        
        private Style estiloCabecera() {
            Style estilo = new Style();
            estilo.setFont(new Font(tama�oCabecera, fuente, false, true, false));
            estilo.setBorder(Border.THIN);
            estilo.getFont().setBold(true);
            estilo.setVerticalAlign(alVertical != null ? alVertical : VerticalAlign.MIDDLE);
            estilo.getFont().setItalic(false);
            estilo.setStreching(Stretching.RELATIVE_TO_TALLEST_OBJECT);
            estilo.setStretchWithOverflow(true);
            if(alHorizontal != null){
                estilo.setHorizontalAlign(alHorizontal);
            }
            return estilo;
        }
        
        public InfoColumna totalizar(){
            this.totalizar = true;
            return this;
        }
        
        public InfoColumna patronMonetario(){
            this.monetario = true;
            return this;
        }
        
        public InfoColumna patronPorcentual(){
            this.porcentaje = true;
            return this;
        }
        
        public InfoColumna estiloCondicionalColor(double inicio, double fin, Color color){
            if(estilosCondicionales == null){
                estilosCondicionales = new ArrayList<ConditionalStyle>();
            }
            Style estilo = this.estilo();
            //estilo.setTextColor(color);
            estilo.setBackgroundColor(color);
            estilo.setTransparent(false);
            this.estilosCondicionales.add(new ConditionalStyle(new StatusLightCondition(inicio, fin), estilo));
            return this;
        }
    }
    
    private static class SuperColumna{
        private String nombre;
        private int posicion;
        private int columnas;
        private Color colorFondo;
        private Color colorTexto;

        public SuperColumna(String nombre, int posicion, int columnas, Color colorFondo, Color colorTexto) {
            this.nombre = nombre;
            this.posicion = posicion;
            this.columnas = columnas;
            this.colorFondo = colorFondo;
            this.colorTexto = colorTexto;
        }
        
        private Style estilo() {
            Style estilo = new Style();
            estilo.setBorder(Border.THIN);
            estilo.getFont().setBold(true);
            estilo.setVerticalAlign(VerticalAlign.MIDDLE);
            estilo.setHorizontalAlign(HorizontalAlign.CENTER);
            if(colorFondo != null){
                estilo.setBackgroundColor(colorFondo);
                estilo.setTransparent(false);
            }
            if(colorTexto != null){
                estilo.setTextColor(colorTexto);
            }
            return estilo;
        }
    }
    
    public static class ConfiguracionReporte{
        
        private String plantilla;
        private String textoTotales;
        private String fuenteTotales;
        private int    tama�oTotales;
        private Integer altoDetalles;
        
        private boolean usarAnchoPaginaCompleto;
        private boolean ignorarPaginacion;
        
        private List<InfoColumna> columnas;
        private List<SuperColumna> superColumnas;
        private Map<String, Object> parametros;
        
        
        private ConfiguracionReporte(){
            columnas = new ArrayList<InfoColumna>();
        }
        
        public static ConfiguracionReporte generar(String plantilla){
            ConfiguracionReporte conf = new ConfiguracionReporte();
            conf.plantilla = plantilla;
            return conf;
        }
        
        public ConfiguracionReporte altoDetalles(int alto){
            this.altoDetalles = alto;
            return this;
        }
        
        public ConfiguracionReporte textoTotal(String texto){
            this.textoTotales = texto;
            return this;
        }

        public ConfiguracionReporte tama�oTotal(int tama�o) {
            this.tama�oTotales = tama�o;
            return this;
        }

        public ConfiguracionReporte fuenteTotal(String fuente) {
            this.fuenteTotales = fuente;
            return this;
        }
        
        public ConfiguracionReporte usarAnchoPaginaCompleto(){
            this.usarAnchoPaginaCompleto = true;
            return this;
        }
        
        public ConfiguracionReporte ignorarPaginacion(){
            this.ignorarPaginacion = true;
            return this;
        }
        
        public ConfiguracionReporte superColumna(String nombre, int columna, int tama�o, Color colorFondo, Color colorTexto){
            if(superColumnas == null){
                superColumnas = new ArrayList<SuperColumna>();
            }
            superColumnas.add(new SuperColumna(nombre, columna, tama�o, colorFondo, colorTexto));
            return this;
        }
        
        public ConfiguracionReporte parametro(String parametro, Object valor){
            if(this.parametros == null){
                parametros = new HashMap<String, Object>();
            }
            this.parametros.put(parametro, valor);
            return this;
        }
        
        public ConfiguracionReporte agregar(InfoColumna columna){            
            this.columnas.add(columna);
            return this;
        }

        private Style estiloTotal() {
            Style estilo = new Style();
            estilo.setFont(new Font(tama�oTotales, fuenteTotales, false, true, false));
            estilo.setBorder(Border.THIN);
            estilo.setStreching(Stretching.RELATIVE_TO_TALLEST_OBJECT);
            estilo.getFont().setBold(true);
            estilo.getFont().setItalic(false);
            estilo.setStretchWithOverflow(true);            
            estilo.setHorizontalAlign(HorizontalAlign.CENTER);
            estilo.setVerticalAlign(VerticalAlign.MIDDLE);
            return estilo;
        }
    }*/
}
