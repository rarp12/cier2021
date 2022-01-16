/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.administracion.configuracion;

import co.stackpointer.cier.modelo.entidad.dpa.Nivel;
import co.stackpointer.cier.modelo.entidad.establecimiento.Establecimiento;
import co.stackpointer.cier.modelo.entidad.establecimiento.Sede;
import co.stackpointer.cier.modelo.fachada.DPAFacadeLocal;
import co.stackpointer.cier.modelo.fachada.EstablecimientoFacadeLocal;
import co.stackpointer.cier.modelo.tipo.Estado;
import co.stackpointer.cier.modelo.tipo.ParamNivelConsul;
import co.stackpointer.cier.modelo.utilidad.UtilCadena;
import co.stackpointer.cier.modelo.utilidad.UtilExportar;
import co.stackpointer.cier.modelo.utilidad.UtilFecha;
import co.stackpointer.cier.vista.ManejadorErrores;
import co.stackpointer.cier.vista.Utilidades;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.ejb.EJB;
import javax.faces.bean.ViewScoped;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.omnifaces.util.Messages;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import static co.stackpointer.cier.vistacontrolador.bean.importacion.ImpUtil.getValorCelda;
import co.stackpointer.cier.vistacontrolador.consulta.ConsultaBase;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import net.sf.jasperreports.engine.JRParameter;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author StackPointer
 */
@ManagedBean(name = "archivoImpEst")
@ViewScoped
public class ArchivoImpEstBean extends ConsultaBase implements Serializable {

    @EJB
    EstablecimientoFacadeLocal fachadaEst;
    
    @EJB
    DPAFacadeLocal fachadaDPA;
    
    public List<Object> listaEliminar;
    public List<Object> listaCrear;
    public List<Object> listaActualizar;
    public List<ErroresCargaSedesEst> listaErrores;
    Map parametros = new HashMap();
    StreamedContent exportFile;
    int lastRow;
    boolean hayRegErroneos;
    boolean cargaPais;

    @PostConstruct
    public void inicializar() {
        super.initActual();
        hayRegErroneos = false;
    }

    @Override
    public void resetearFiltros() {
        this.seleccionNivel1 = null;
        this.seleccionNivel2 = null;
        this.seleccionNivel3 = null;
        this.seleccionNivel4 = null;
        this.seleccionNivel5 = null;
        this.seleccionEst = null;
    }

    @Override
    public void cambioPeriodo() {
        super.initHistoricos(periodo);
        super.cargarNivel1();
    }

    public void cargaMasiva(FileUploadEvent event) throws IOException {
        String codRegion, codDepartamento, codSecretaria, codMunicipio, codOtro, codEstablecimiento, codSede;
        String nomRegion, nomDepartamento, nomSecretaria, nomMunicipio, nomOtro, nomEstablecimiento, nomSede;
        String direccion, email, telefono, codSector, nomSector, codAutoridad, nomAutoridad, nomRector, emailRector, telRector, tipo;
        String codPadre;
        listaEliminar = new ArrayList<Object>();
        listaCrear = new ArrayList<Object>();
        listaActualizar = new ArrayList<Object>();
        listaErrores = new ArrayList<ErroresCargaSedesEst>();
        List<String> codigosEstablecimientos = new ArrayList<String>();
        List<String> codigosSedes = new ArrayList<String>();
        Row rowDato;
        int rowNumber;
        UploadedFile file = event.getFile();
        try {
            Workbook wb = new XSSFWorkbook(file.getInputstream());
            Sheet hoja = wb.getSheetAt(0);
            rowNumber = 1;
            hayRegErroneos = false;
            while (true) {
                rowNumber++;
                rowDato = hoja.getRow(rowNumber);
                if (rowDato == null) {
                    lastRow = rowNumber;
                    break;
                }
                //Recuperamos todos los datos del registro del excel
                ErroresCargaSedesEst er = new ErroresCargaSedesEst();
                codRegion = getValorCelda(wb, rowDato.getCell(0));
                er.setCodRegion(codRegion);
                nomRegion = getValorCelda(rowDato.getCell(1));
                er.setNomRegion(nomRegion);
                codDepartamento = getValorCelda(wb, rowDato.getCell(2));
                er.setCodDepartamento(codDepartamento);
                nomDepartamento = getValorCelda(rowDato.getCell(3));
                er.setNomDepartamento(nomDepartamento);
                codSecretaria = getValorCelda(wb, rowDato.getCell(4));
                er.setCodSecretaria(codSecretaria);
                nomSecretaria = getValorCelda(rowDato.getCell(5));
                er.setNomSecretaria(nomSecretaria);
                codMunicipio = getValorCelda(wb, rowDato.getCell(6));
                er.setCodMunicipio(codMunicipio);
                nomMunicipio = getValorCelda(rowDato.getCell(7));
                er.setNomMunicipio(nomMunicipio);
                codOtro = getValorCelda(wb, rowDato.getCell(8));
                er.setCodOtro(codOtro);
                nomOtro = getValorCelda(rowDato.getCell(9));
                er.setNomOtro(nomOtro);
                codEstablecimiento = getValorCelda(rowDato.getCell(10));
                er.setCodEstablecimiento(codEstablecimiento);
                nomEstablecimiento = getValorCelda(rowDato.getCell(11));
                er.setNomEstablecimiento(nomEstablecimiento);
                codSede = getValorCelda(rowDato.getCell(12));
                er.setCodSede(codSede);
                nomSede = getValorCelda(rowDato.getCell(13));
                er.setNomSede(nomSede);
                direccion = getValorCelda(rowDato.getCell(14));
                er.setDireccion(direccion);
                email = getValorCelda(rowDato.getCell(15));
                er.setEmail(email);
                telefono = getValorCelda(rowDato.getCell(16));
                er.setTelefono(telefono);
                codSector = getValorCelda(wb, rowDato.getCell(17));
                er.setCodSector(codSector);
                nomSector = getValorCelda(rowDato.getCell(18));
                er.setNomSector(nomSector);
                codAutoridad = getValorCelda(wb, rowDato.getCell(19));
                er.setCodAutoridad(codAutoridad);
                nomAutoridad = getValorCelda(rowDato.getCell(20));
                er.setNomAutoridad(nomAutoridad);
                nomRector = getValorCelda(rowDato.getCell(21));
                er.setNomRector(nomRector);
                emailRector = getValorCelda(rowDato.getCell(22));
                er.setEmailRector(emailRector);
                telRector = getValorCelda(rowDato.getCell(23));
                er.setTelRector(telRector);
                tipo = getValorCelda(rowDato.getCell(24));
                er.setTipo(tipo);

                if (UtilCadena.isNullOVacio(codRegion) && UtilCadena.isNullOVacio(codDepartamento) && UtilCadena.isNullOVacio(codSecretaria) && UtilCadena.isNullOVacio(codMunicipio)
                        && UtilCadena.isNullOVacio(codOtro) && UtilCadena.isNullOVacio(codEstablecimiento) && UtilCadena.isNullOVacio(codSede)) {
                    lastRow = rowNumber;
                    break;
                }

                rowDato = hoja.getRow(0);
                String descNivel1 = getValorCelda(wb, rowDato.getCell(0));
                String descNivel2 = getValorCelda(wb, rowDato.getCell(2));
                String descNivel3 = getValorCelda(wb, rowDato.getCell(4));
                String descNivel4 = getValorCelda(wb, rowDato.getCell(6));
                String descNivel5 = getValorCelda(rowDato.getCell(8));
                String descNivelEst = getValorCelda(rowDato.getCell(10));
                String descNivelSed = getValorCelda(rowDato.getCell(12));

                codEstablecimiento = UtilCadena.isNullOVacio(codEstablecimiento) && seleccionEst != null ? seleccionEst.getCodigo() : codEstablecimiento;
                codRegion = UtilCadena.isNullOVacio(codRegion) && seleccionNivel1 != null ? seleccionNivel1.getCodigo() : codRegion;
                codDepartamento = UtilCadena.isNullOVacio(codDepartamento) && seleccionNivel2 != null ? seleccionNivel2.getCodigo() : codDepartamento;
                codSecretaria = UtilCadena.isNullOVacio(codSecretaria) && seleccionNivel3 != null ? seleccionNivel3.getCodigo() : codSecretaria;
                codMunicipio = UtilCadena.isNullOVacio(codMunicipio) && seleccionNivel4 != null ? seleccionNivel4.getCodigo() : codMunicipio;
                codOtro = UtilCadena.isNullOVacio(codOtro) && seleccionNivel5 != null ? seleccionNivel5.getCodigo() : codOtro;

                if ((ParamNivelConsul.CERO.getNivel().intValue() < maximoNivelDPA && UtilCadena.isNullOVacio(codRegion))
                        || (ParamNivelConsul.UNO.getNivel().intValue() < maximoNivelDPA && UtilCadena.isNullOVacio(codDepartamento))
                        || (ParamNivelConsul.DOS.getNivel().intValue() < maximoNivelDPA && UtilCadena.isNullOVacio(codSecretaria))
                        || (ParamNivelConsul.TRES.getNivel().intValue() < maximoNivelDPA && UtilCadena.isNullOVacio(codMunicipio))
                        || (ParamNivelConsul.CUATRO.getNivel().intValue() < maximoNivelDPA && UtilCadena.isNullOVacio(codOtro))
                        || UtilCadena.isNullOVacio(codEstablecimiento)) {
                    er.setError(Utilidades.obtenerMensaje("administracion.parametros.infoIncompleta"));
                    listaErrores.add(er);
                    hayRegErroneos = true;
                    continue;
                }
                if (ParamNivelConsul.CINCO.equals(maximoNivelDPA)) {
                    codPadre = codOtro;
                } else if (ParamNivelConsul.CUATRO.equals(maximoNivelDPA)) {
                    codPadre = codMunicipio;
                } else if (ParamNivelConsul.TRES.equals(maximoNivelDPA)) {
                    codPadre = codSecretaria;
                } else if (ParamNivelConsul.DOS.equals(maximoNivelDPA)) {
                    codPadre = codDepartamento;
                } else if (ParamNivelConsul.UNO.equals(maximoNivelDPA)) {
                    codPadre = codRegion;
                } else {
                    codPadre = seleccionNivel0.getCodigo();
                }
                Nivel padre = fachadaDPA.getNivelPorCodigo(codPadre);
                Establecimiento establecimiento = fachadaEst.getEstablecimientoPorCodigo(codEstablecimiento);
                boolean aplica = false;
                if (seleccionEst != null) {
                    if (codEstablecimiento.equals(seleccionEst.getCodigo())) {
                        if (!codOtro.equals(seleccionNivel5.getCodigo()) || !codMunicipio.equals(seleccionNivel4.getCodigo()) || !codSecretaria.equals(seleccionNivel3.getCodigo())
                                || !codDepartamento.equals(seleccionNivel2.getCodigo()) || !codRegion.equals(seleccionNivel1.getCodigo())) {
                            continue;
                        }
                        aplica = true;
                    }
                } else if (seleccionNivel5 != null) {
                    if (codOtro.equals(seleccionNivel5.getCodigo())) {
                        if (!codMunicipio.equals(seleccionNivel4.getCodigo()) || !codSecretaria.equals(seleccionNivel3.getCodigo())
                                || !codDepartamento.equals(seleccionNivel2.getCodigo()) || !codRegion.equals(seleccionNivel1.getCodigo())) {
                            continue;
                        }
                        if (establecimiento != null && !codPadre.equals(establecimiento.getNivel().getCodigo())) {
                            continue;
                        }
                        aplica = true;
                    }
                } else if (seleccionNivel4 != null) {
                    if (codMunicipio.equals(seleccionNivel4.getCodigo())) {
                        if (ParamNivelConsul.CERO.getNivel().intValue() < maximoNivelDPA && !fachadaDPA.estanRelacionados(seleccionNivel0.getCodigo(), codRegion)) {
                            er.setError(Utilidades.obtenerMensaje("administracion.parametros.codMalRelacionados") + ": " + descNivel1);
                            listaErrores.add(er);
                            hayRegErroneos = true;
                            continue;
                        }
                        if (ParamNivelConsul.UNO.getNivel().intValue() < maximoNivelDPA && !fachadaDPA.estanRelacionados(codRegion, codDepartamento)) {
                            er.setError(Utilidades.obtenerMensaje("administracion.parametros.codMalRelacionados") + ": " + descNivel1 + " y " + descNivel2);
                            listaErrores.add(er);
                            hayRegErroneos = true;
                            continue;
                        }
                        if (ParamNivelConsul.DOS.getNivel().intValue() < maximoNivelDPA && !fachadaDPA.estanRelacionados(codDepartamento, codSecretaria)) {
                            er.setError(Utilidades.obtenerMensaje("administracion.parametros.codMalRelacionados") + ": " + descNivel2 + " y " + descNivel3);
                            listaErrores.add(er);
                            hayRegErroneos = true;
                            continue;
                        }
                        if (ParamNivelConsul.TRES.getNivel().intValue() < maximoNivelDPA && !fachadaDPA.estanRelacionados(codSecretaria, codMunicipio)) {
                            er.setError(Utilidades.obtenerMensaje("administracion.parametros.codMalRelacionados") + ": " + descNivel3 + " y " + descNivel4);
                            listaErrores.add(er);
                            hayRegErroneos = true;
                            continue;
                        }
                        if ((ParamNivelConsul.CUATRO.getNivel().intValue() < maximoNivelDPA && !fachadaDPA.estanRelacionados(codMunicipio, codOtro))) {
                            er.setError(Utilidades.obtenerMensaje("administracion.parametros.codMalRelacionados") + ": " + descNivel4 + " y " + descNivel5);
                            listaErrores.add(er);
                            hayRegErroneos = true;
                            continue;
                        }
                        if (establecimiento != null && !codPadre.equals(establecimiento.getNivel().getCodigo())) {
                            er.setError(Utilidades.obtenerMensaje("administracion.parametros.codMalRelacionados") + ": " + descNivel5 + " y " + descNivelEst);
                            listaErrores.add(er);
                            hayRegErroneos = true;
                            continue;
                        }
                        aplica = true;
                    }
                } else if (seleccionNivel3 != null) {
                    if (codSecretaria.equals(seleccionNivel3.getCodigo())) {
                        if (!codDepartamento.equals(seleccionNivel2.getCodigo()) || !codRegion.equals(seleccionNivel1.getCodigo())) {
                            continue;
                        }
                        if (ParamNivelConsul.TRES.getNivel().intValue() < maximoNivelDPA && !fachadaDPA.estanRelacionados(codSecretaria, codMunicipio)) {
                            er.setError(Utilidades.obtenerMensaje("administracion.parametros.codMalRelacionados") + ": " + descNivel3 + " y " + descNivel4);
                            listaErrores.add(er);
                            hayRegErroneos = true;
                            continue;
                        }
                        if (ParamNivelConsul.CUATRO.getNivel().intValue() < maximoNivelDPA && !fachadaDPA.estanRelacionados(codMunicipio, codOtro)) {
                            er.setError(Utilidades.obtenerMensaje("administracion.parametros.codMalRelacionados") + ": " + descNivel4 + " y " + descNivel5);
                            listaErrores.add(er);
                            hayRegErroneos = true;
                            continue;
                        }
                        if (establecimiento != null && !codPadre.equals(establecimiento.getNivel().getCodigo())) {
                            er.setError(Utilidades.obtenerMensaje("administracion.parametros.codMalRelacionados") + ": " + descNivel5 + " y " + descNivelEst);
                            listaErrores.add(er);
                            hayRegErroneos = true;
                            continue;
                        }
                        aplica = true;
                    }
                } else if (seleccionNivel2 != null) {
                    if (codDepartamento.equals(seleccionNivel2.getCodigo())) {
                        if (!codRegion.equals(seleccionNivel1.getCodigo())) {
                            continue;
                        }
                        if (ParamNivelConsul.DOS.getNivel().intValue() < maximoNivelDPA && !fachadaDPA.estanRelacionados(codDepartamento, codSecretaria)) {
                            er.setError(Utilidades.obtenerMensaje("administracion.parametros.codMalRelacionados") + ": " + descNivel2 + " y " + descNivel3);
                            listaErrores.add(er);
                            hayRegErroneos = true;
                            continue;
                        }
                        if (ParamNivelConsul.TRES.getNivel().intValue() < maximoNivelDPA && !fachadaDPA.estanRelacionados(codSecretaria, codMunicipio)) {
                            er.setError(Utilidades.obtenerMensaje("administracion.parametros.codMalRelacionados") + ": " + descNivel3 + " y " + descNivel4);
                            listaErrores.add(er);
                            hayRegErroneos = true;
                            continue;
                        }
                        if (ParamNivelConsul.CUATRO.getNivel().intValue() < maximoNivelDPA && !fachadaDPA.estanRelacionados(codMunicipio, codOtro)) {
                            er.setError(Utilidades.obtenerMensaje("administracion.parametros.codMalRelacionados") + ": " + descNivel4 + " y " + descNivel5);
                            listaErrores.add(er);
                            hayRegErroneos = true;
                            continue;
                        }
                        if (establecimiento != null && !codPadre.equals(establecimiento.getNivel().getCodigo())) {
                            er.setError(Utilidades.obtenerMensaje("administracion.parametros.codMalRelacionados") + ": " + descNivel5 + " y " + descNivelEst);
                            listaErrores.add(er);
                            hayRegErroneos = true;
                            continue;
                        }
                        aplica = true;
                    }
                } else if (seleccionNivel1 != null) {
                    if (codRegion.equals(seleccionNivel1.getCodigo())) {
                        if (ParamNivelConsul.UNO.getNivel().intValue() < maximoNivelDPA && !fachadaDPA.estanRelacionados(codRegion, codDepartamento)) {
                            er.setError(Utilidades.obtenerMensaje("administracion.parametros.codMalRelacionados") + ": " + descNivel1 + " y " + descNivel2);
                            listaErrores.add(er);
                            hayRegErroneos = true;
                            continue;
                        }
                        if (ParamNivelConsul.DOS.getNivel().intValue() < maximoNivelDPA && !fachadaDPA.estanRelacionados(codDepartamento, codSecretaria)) {
                            er.setError(Utilidades.obtenerMensaje("administracion.parametros.codMalRelacionados") + ": " + descNivel2 + " y " + descNivel3);
                            listaErrores.add(er);
                            hayRegErroneos = true;
                            continue;
                        }
                        if (ParamNivelConsul.TRES.getNivel().intValue() < maximoNivelDPA && !fachadaDPA.estanRelacionados(codSecretaria, codMunicipio)) {
                            er.setError(Utilidades.obtenerMensaje("administracion.parametros.codMalRelacionados") + ": " + descNivel3 + " y " + descNivel4);
                            listaErrores.add(er);
                            hayRegErroneos = true;
                            continue;
                        }
                        if (ParamNivelConsul.CUATRO.getNivel().intValue() < maximoNivelDPA && !fachadaDPA.estanRelacionados(codMunicipio, codOtro)) {
                            er.setError(Utilidades.obtenerMensaje("administracion.parametros.codMalRelacionados") + ": " + descNivel4 + " y " + descNivel5);
                            listaErrores.add(er);
                            hayRegErroneos = true;
                            continue;
                        }
                        if (establecimiento != null && !codPadre.equals(establecimiento.getNivel().getCodigo())) {
                            er.setError(Utilidades.obtenerMensaje("administracion.parametros.codMalRelacionados") + ": " + descNivel5 + " y " + descNivelEst);
                            listaErrores.add(er);
                            hayRegErroneos = true;
                            continue;
                        }
                        aplica = true;
                    }
                } else {
                    if (ParamNivelConsul.CERO.getNivel().intValue() < maximoNivelDPA && !fachadaDPA.estanRelacionados(seleccionNivel0.getCodigo(), codRegion)) {
                        er.setError(Utilidades.obtenerMensaje("administracion.parametros.codMalRelacionados") + ": " + descNivel1);
                        listaErrores.add(er);
                        hayRegErroneos = true;
                        continue;
                    }
                    if (ParamNivelConsul.UNO.getNivel().intValue() < maximoNivelDPA && !fachadaDPA.estanRelacionados(codRegion, codDepartamento)) {
                        er.setError(Utilidades.obtenerMensaje("administracion.parametros.codMalRelacionados") + ": " + descNivel1 + " y " + descNivel2);
                        listaErrores.add(er);
                        hayRegErroneos = true;
                        continue;
                    }
                    if (ParamNivelConsul.DOS.getNivel().intValue() < maximoNivelDPA && !fachadaDPA.estanRelacionados(codDepartamento, codSecretaria)) {
                        er.setError(Utilidades.obtenerMensaje("administracion.parametros.codMalRelacionados") + ": " + descNivel2 + " y " + descNivel3);
                        listaErrores.add(er);
                        hayRegErroneos = true;
                        continue;
                    }
                    if (ParamNivelConsul.TRES.getNivel().intValue() < maximoNivelDPA && !fachadaDPA.estanRelacionados(codSecretaria, codMunicipio)) {
                        er.setError(Utilidades.obtenerMensaje("administracion.parametros.codMalRelacionados") + ": " + descNivel3 + " y " + descNivel4);
                        listaErrores.add(er);
                        hayRegErroneos = true;
                        continue;
                    }
                    if (ParamNivelConsul.CUATRO.getNivel().intValue() < maximoNivelDPA && !fachadaDPA.estanRelacionados(codMunicipio, codOtro)) {
                        er.setError(Utilidades.obtenerMensaje("administracion.parametros.codMalRelacionados") + ": " + descNivel4 + " y " + descNivel5);
                        listaErrores.add(er);
                        hayRegErroneos = true;
                        continue;
                    }
                    if (establecimiento != null && !codPadre.equals(establecimiento.getNivel().getCodigo())) {
                        er.setError(Utilidades.obtenerMensaje("administracion.parametros.codMalRelacionados") + ": " + descNivel5 + " y " + descNivelEst);
                        listaErrores.add(er);
                        hayRegErroneos = true;
                        continue;
                    }
                    aplica = true;
                }

                if (aplica) {
                    if (codigosSedes.contains(codSede)) {
                        continue;
                    }
                    Sede sede = null;
                    if (!UtilCadena.isNullOVacio(codSede)) {
                        sede = fachadaEst.getSedePorCodigo(codSede);
                    }

                    if ("2".equals(tipo)) {//Eliminar
                        if (UtilCadena.isNullOVacio(codSede)) {//Es establecimiento
                            if (codigosEstablecimientos.contains(codEstablecimiento)) {
                                continue;
                            }
                            if (establecimiento == null) {
                                er.setError(Utilidades.obtenerMensaje("administracion.configuracion.codigoNoExiste") + ": " + descNivelEst);
                                listaErrores.add(er);
                                hayRegErroneos = true;
                                continue;
                            }
                            int i = establecimiento.getSedes().size();
                            for (Sede sedeAux : establecimiento.getSedes()) {
                                if (listaEliminar.contains(sedeAux)) {
                                    i--;
                                }
                            }
                            if (i == 0) {
                                listaEliminar.add(establecimiento);
                                codigosEstablecimientos.add(establecimiento.getCodigo());
                            } else {
                                er.setError(Utilidades.obtenerMensaje("administracion.configuracion.sedesDependientes"));
                                listaErrores.add(er);
                                hayRegErroneos = true;
                                continue;
                            }
                        } else {//Es sede
                            if (establecimiento == null) {
                                er.setError(Utilidades.obtenerMensaje("administracion.configuracion.codigoNoExiste") + ": " + descNivelEst);
                                listaErrores.add(er);
                                hayRegErroneos = true;
                                continue;
                            }
                            if (sede == null) {
                                er.setError(Utilidades.obtenerMensaje("administracion.configuracion.codigoNoExiste") + ": " + descNivelSed);
                                listaErrores.add(er);
                                hayRegErroneos = true;
                                continue;
                            }
                            if (!sede.getEstablecimiento().getCodigo().equals(establecimiento.getCodigo())) {
                                er.setError(Utilidades.obtenerMensaje("administracion.parametros.codMalRelacionados") + ": " + descNivelEst + " y " + descNivelSed);
                                listaErrores.add(er);
                                hayRegErroneos = true;
                                continue;
                            }
                            if (sede.getPredios().isEmpty()) {
                                listaEliminar.add(sede);
                                codigosSedes.add(sede.getCodigo());
                            } else {
                                er.setError(Utilidades.obtenerMensaje("administracion.configuracion.prediosPendientes"));
                                listaErrores.add(er);
                                hayRegErroneos = true;
                                continue;
                            }
                        }

                    } else if ("1".equals(tipo)) {
                        if (!UtilCadena.isNullOVacio(codEstablecimiento)) {//Es establecimiento
                            /*if (codigosEstablecimientos.contains(codEstablecimiento)) {
                             continue;
                             }*/
                            if (UtilCadena.isNullOVacio(nomEstablecimiento)) {
                                er.setError(Utilidades.obtenerMensaje("administracion.parametros.infoIncompleta") + ": " + descNivelEst);
                                listaErrores.add(er);
                                hayRegErroneos = true;
                                continue;
                            }
                            if (establecimiento != null) {
                                establecimiento.setNombre(nomEstablecimiento);
                                establecimiento.setPeriodo(UtilFecha.obtenerPeriodoActual());
                                establecimiento.setEstado(Estado.A);
                                listaActualizar.add(establecimiento);
                                codigosEstablecimientos.add(establecimiento.getCodigo());
                            } else if (establecimiento == null) {
                                establecimiento = new Establecimiento();
                                establecimiento.setCodigo(codEstablecimiento);
                                establecimiento.setEstado(Estado.A);
                                establecimiento.setNivel(padre);
                                establecimiento.setNombre(nomEstablecimiento);
                                establecimiento.setPeriodo(UtilFecha.obtenerPeriodoActual());
                                if (!existeEstablecimientoCreado(listaCrear, codEstablecimiento)) {
                                    listaCrear.add(establecimiento);
                                    codigosEstablecimientos.add(establecimiento.getCodigo());
                                }
                            }
                        }
                        if (!UtilCadena.isNullOVacio(codSede)) {//Es sede
                            //Si es sede y el establecimiento no existe, se crea el establecimiento también
                            if (establecimiento == null && !codigosEstablecimientos.contains(codEstablecimiento)) {
                                if (UtilCadena.isNullOVacio(nomEstablecimiento)) {
                                    er.setError(Utilidades.obtenerMensaje("administracion.parametros.infoIncompleta") + ": " + descNivelEst);
                                    listaErrores.add(er);
                                    hayRegErroneos = true;
                                    continue;
                                }
                                establecimiento = new Establecimiento();
                                establecimiento.setCodigo(codEstablecimiento);
                                establecimiento.setEstado(Estado.A);
                                establecimiento.setNivel(padre);
                                establecimiento.setNombre(nomEstablecimiento);
                                establecimiento.setPeriodo(UtilFecha.obtenerPeriodoActual());
                                listaCrear.add(establecimiento);
                                codigosEstablecimientos.add(establecimiento.getCodigo());
                            }
                            if (UtilCadena.isNullOVacio(nomSede)) {
                                er.setError(Utilidades.obtenerMensaje("administracion.parametros.infoIncompleta") + ": " + descNivelSed);
                                listaErrores.add(er);
                                hayRegErroneos = true;
                                continue;
                            }
                            if (sede != null) {
                                if (establecimiento == null) {
                                    er.setError(Utilidades.obtenerMensaje("administracion.configuracion.codigoNoExiste") + ": " + descNivelEst);
                                    listaErrores.add(er);
                                    hayRegErroneos = true;
                                    continue;
                                }
                                if (!sede.getEstablecimiento().getCodigo().equals(establecimiento.getCodigo())) {
                                    er.setError(Utilidades.obtenerMensaje("administracion.parametros.codMalRelacionados") + ": " + descNivelEst + " y " + descNivelSed);
                                    listaErrores.add(er);
                                    hayRegErroneos = true;
                                    continue;
                                }
                                sede.setNombre(nomSede);
                                sede.setPeriodo(UtilFecha.obtenerPeriodoActual());
                                sede.setEstado(Estado.A);
                                sede.setDireccion(direccion);
                                sede.setEmailSede(email);
                                sede.setTelefono(telefono);
                                sede.setCodSector(codSector);
                                sede.setNomSector(nomSector);
                                sede.setCodAutoridad(codAutoridad);
                                sede.setNomAutoridad(nomAutoridad);
                                sede.setRector(nomRector);
                                sede.setEmailRector(emailRector);
                                sede.setTelRector(telRector);
                                listaActualizar.add(sede);
                                codigosSedes.add(sede.getCodigo());
                            } else {
                                sede = new Sede();
                                sede.setCodigo(codSede);
                                if (establecimiento == null) {
                                    for (Object est : listaCrear) {
                                        if (est instanceof Establecimiento && ((Establecimiento) est).getCodigo().equals(codEstablecimiento)) {
                                            establecimiento = (Establecimiento) est;
                                            break;
                                        }
                                    }
                                }
                                sede.setEstablecimiento(establecimiento);
                                sede.setNombre(nomSede);
                                sede.setEstado(Estado.A);
                                sede.setDireccion(direccion);
                                sede.setEmailSede(email);
                                sede.setTelefono(telefono);
                                sede.setCodSector(codSector);
                                sede.setNomSector(nomSector);
                                sede.setCodAutoridad(codAutoridad);
                                sede.setNomAutoridad(nomAutoridad);
                                sede.setRector(nomRector);
                                sede.setEmailRector(emailRector);
                                sede.setTelRector(telRector);
                                sede.setPeriodo(UtilFecha.obtenerPeriodoActual());
                                listaCrear.add(sede);
                                codigosSedes.add(sede.getCodigo());
                            }
                        }
                    }
                }
                listaErrores.add(er);
            }

            if (hayRegErroneos) {
                rowDato = hoja.getRow(0);
                parametros.put("col1", getValorCelda(wb, rowDato.getCell(0)));
                parametros.put("col2", getValorCelda(wb, rowDato.getCell(2)));
                parametros.put("col3", getValorCelda(wb, rowDato.getCell(4)));
                parametros.put("col4", getValorCelda(wb, rowDato.getCell(6)));
                parametros.put("col5", getValorCelda(rowDato.getCell(8)));
                parametros.put("col6", getValorCelda(rowDato.getCell(10)));
                parametros.put("col7", getValorCelda(rowDato.getCell(12)));
                parametros.put("col8", getValorCelda(rowDato.getCell(14)));
                parametros.put("col9", getValorCelda(rowDato.getCell(15)));
                parametros.put("col10", getValorCelda(rowDato.getCell(16)));
                parametros.put("col11", getValorCelda(rowDato.getCell(17)));
                parametros.put("col12", getValorCelda(rowDato.getCell(19)));
                parametros.put("col13", getValorCelda(rowDato.getCell(21)));
                parametros.put("col14", getValorCelda(rowDato.getCell(22)));
                parametros.put("col15", getValorCelda(rowDato.getCell(23)));
                parametros.put("col16", getValorCelda(rowDato.getCell(24)));
                rowDato = hoja.getRow(1);
                parametros.put("colCode", getValorCelda(rowDato.getCell(0)));
                parametros.put("colName", getValorCelda(rowDato.getCell(1)));
                parametros.put(JRParameter.REPORT_RESOURCE_BUNDLE, ResourceBundle.getBundle("etiquetas", FacesContext.getCurrentInstance().getViewRoot().getLocale()));
            }
        } catch (Exception e) {
            ManejadorErrores.ingresarExcepcionEnLog(e, ArchivoImpEstBean.class);
            System.out.println("Error: " + e);
            Messages.addFlashGlobalError(Utilidades.obtenerMensaje("adm.parametros.config.carga.errorRegistro"));
            listaErrores.clear();
            hayRegErroneos = true;
        }
    }

    /**
     * Este procedimiento recibira la lista de establecimientos a crear y el
     * codigo del establecimiento actual si el codigo de establecimiento actual
     * ya existe en el listado de los establecimientos ya creados el metodo
     * devolvera true para avisar que no se cree de nuevo esto se usara en el
     * metodo cargaMasiva que es cuando cargan los establecimientos y sedes
     * desde el excel.
     *
     * @param establecimientos
     * @param codigoEstablecimiento
     * @return
     */
    public boolean existeEstablecimientoCreado(List<Object> establecimientos, String codigoEstablecimiento) {
        boolean establecimientoExistente = false;
        for (Object registro : establecimientos) {
            if (registro instanceof Establecimiento && ((Establecimiento) registro).getCodigo().equals(codigoEstablecimiento)) {
                establecimientoExistente = true;
                return establecimientoExistente;
            }
        }
        return establecimientoExistente;
    }

    public void guardar() throws IOException {
        try {
            for (Object n : listaCrear) {
                if (n instanceof Sede) {
                    Establecimiento establecimientoConId = fachadaEst.getEstablecimientoPorCodigo(((Sede) n).getEstablecimiento().getCodigo());
                    ((Sede) n).setEstablecimiento(establecimientoConId);
                    fachadaEst.guardarSede(usuarioSesion.getUsuario().getUsername(), (Sede) n);
                    //System.out.println("CreandoSede: " + ((Sede) n).getCodigo());
                } else {
                    fachadaEst.guardarEstablecimiento(usuarioSesion.getUsuario().getUsername(), (Establecimiento) n);
                    //System.out.println("CreandoEstablecimiento: " + ((Sede) n).getCodigo());
                }
            }
            for (Object n : listaActualizar) {
                if (n instanceof Sede) {
                    fachadaEst.actualizarSede(usuarioSesion.getUsuario().getUsername(), (Sede) n);
                    //System.out.println("ActualizandoSede: " + ((Sede) n).getCodigo());
                } else {
                    fachadaEst.actualizarEstablecimiento(usuarioSesion.getUsuario().getUsername(), (Establecimiento) n);
                    //System.out.println("ActualizandoEstablecimiento: " + ((Sede) n).getCodigo());
                }
            }
            for (Object n : listaEliminar) {
                if (n instanceof Sede) {
                    fachadaEst.eliminarSede(usuarioSesion.getUsuario().getUsername(), (Sede) n);
                    //System.out.println("EliminandoSede: " + ((Sede) n).getCodigo());
                } else {
                    fachadaEst.eliminarEstablecimiento(usuarioSesion.getUsuario().getUsername(), (Establecimiento) n);
                    //System.out.println("eliminandoEstablecimiento: " + ((Sede) n).getCodigo());
                }
            }

            Messages.addFlashGlobalInfo(Utilidades.obtenerMensaje("adm.parametros.config.carga.datosGuardados"));
            super.initActual();

        } catch (Exception e) {
            ManejadorErrores.ingresarExcepcionEnLog(e, ArchivoImpEstBean.class); //UtilOut.println(e);
            Messages.addFlashGlobalError(Utilidades.obtenerMensaje("adm.parametros.config.carga.errorRegistro"));
        }
    }

    public StreamedContent getExportFile() {
        InputStream stream = null;
        try {
            stream = UtilExportar.getDocumentoXLSX("cargaSedesEst", "ErroresCargaSedesEst", parametros, listaErrores);

        } catch (Exception ex) {
            ManejadorErrores.ingresarExcepcionEnLog(ex, ArchivoImpEstBean.class); //UtilOut.println(ex);
            Messages.addFlashGlobalError(Utilidades.obtenerMensaje("adm.parametros.config.carga.errorRegistro"));
        } finally {
            try {
                stream.close();
            } catch (Exception ex) {
                stream = null;
            }
            return new DefaultStreamedContent(stream, "application/x-download", "ErroresCargaSedesEst.xlsx");
        }
    }

    public boolean getHayRegErroneos() {
        return hayRegErroneos;
    }

    public boolean isCargaPais() {
        return cargaPais;
    }

    public void setCargaPais(boolean cargaPais) {
        if (cargaPais) {
            resetearFiltros();
        }
        this.cargaPais = cargaPais;
    }
}
