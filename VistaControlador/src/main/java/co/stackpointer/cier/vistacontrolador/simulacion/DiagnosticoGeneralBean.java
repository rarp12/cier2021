/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.simulacion;

import co.stackpointer.cier.modelo.fachada.SimulacionCostoFacadeLocal;
import co.stackpointer.cier.modelo.filtro.simulacion.FiltroDiagnosticoGeneral;
import co.stackpointer.cier.modelo.tipo.ParamNivelConsul;
import co.stackpointer.cier.modelo.utilidad.UtilConsulta;
import co.stackpointer.cier.modelo.utilidad.UtilOut;
import co.stackpointer.cier.vista.Utilidades;
import co.stackpointer.cier.vistacontrolador.consulta.ambito.ConsultaAmbito;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.omnifaces.util.Messages;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author user
 */
@ManagedBean(name = "diagnosticoGeneralBean")
@ViewScoped
public class DiagnosticoGeneralBean extends ConsultaAmbito {

    @EJB
    private SimulacionCostoFacadeLocal facSimulacion;
    @ManagedProperty(value = "#{consultaAmbito}")
    private ConsultaAmbito consultaAmbito;
    private Registro totalConsulta = new Registro();
    private List<Registro> lista;
    private LazyDataModel<Registro> lazyLista;
    private FiltroDiagnosticoGeneral filtro = new FiltroDiagnosticoGeneral();
    // columnas
    // totales
    private final String columnaM2RequeridosTotal = Utilidades.obtenerMensaje("simulacion.diagnostico.general.columna.total.req");
    private final String columnaM2DisponiblesTotal = Utilidades.obtenerMensaje("simulacion.diagnostico.general.columna.total.disp");
    private final String columnaM2DeficitTotal = Utilidades.obtenerMensaje("simulacion.diagnostico.general.columna.total.def");
    //aula
    private final String columnaIdoneidadAula = Utilidades.obtenerMensaje("simulacion.diagnostico.general.columna.aula.cump");
    private final String columnaM2RequeridosRealesAula = Utilidades.obtenerMensaje("simulacion.diagnostico.general.columna.aula.req");
    private final String columnaM2RequeridosProyectadosAula = Utilidades.obtenerMensaje("simulacion.diagnostico.general.columna.aula.proy");
    private final String columnaUnidadesAula = Utilidades.obtenerMensaje("simulacion.diagnostico.general.columna.aula.und");
    private final String columnaM2DisponiblesAula = Utilidades.obtenerMensaje("simulacion.diagnostico.general.columna.aula.disp");
    private final String columnaM2CostosAula = Utilidades.obtenerMensaje("simulacion.diagnostico.general.columna.aula.cos");
    // sanitario
    private final String columnaIdoneidadSanitario = Utilidades.obtenerMensaje("simulacion.diagnostico.general.columna.sanitario.cump");
    private final String columnaM2RequeridosRealesSanitario = Utilidades.obtenerMensaje("simulacion.diagnostico.general.columna.sanitario.req");
    private final String columnaM2RequeridosProyectadosSanitario = Utilidades.obtenerMensaje("simulacion.diagnostico.general.columna.sanitario.proy");
    private final String columnaUnidadesSanitario = Utilidades.obtenerMensaje("simulacion.diagnostico.general.columna.sanitario.und");
    private final String columnaM2DisponiblesSanitario = Utilidades.obtenerMensaje("simulacion.diagnostico.general.columna.sanitario.disp");
    private final String columnaM2CostosSanitario = Utilidades.obtenerMensaje("simulacion.diagnostico.general.columna.sanitario.cos");
    // circulacion
    private final String columnaM2RequeridosCirculacion = Utilidades.obtenerMensaje("simulacion.diagnostico.general.columna.circulaciones.req");
    private final String columnaM2DisponiblesCirculacion = Utilidades.obtenerMensaje("simulacion.diagnostico.general.columna.circulaciones.disp");
    private final String columnaM2CostosCirculacion = Utilidades.obtenerMensaje("simulacion.diagnostico.general.columna.circulaciones.cos");
    // otros
    private final String columnaCostoM2Disponibles = Utilidades.obtenerMensaje("simulacion.diagnostico.general.columna.total.cos.disp");
    private final String columnaM2RequeridoNuevoLote = Utilidades.obtenerMensaje("simulacion.diagnostico.general.columna.total.red.nue.lote");
    private final String columnaCostoM2Lote = Utilidades.obtenerMensaje("simulacion.diagnostico.general.columna.total.cos.lote");
    
    private final String columnaFaltanteAula = Utilidades.obtenerMensaje("simulacion.diagnostico.general.columna.total.fal.aula");
    private final String columnaCostoFaltanteAula = Utilidades.obtenerMensaje("simulacion.diagnostico.general.columna.total.cos.fal.aula");
    private final String columnaFaltanteSanitario = Utilidades.obtenerMensaje("simulacion.diagnostico.general.columna.total.fal.sanitario");
    private final String columnaCostoFaltanteSanitario = Utilidades.obtenerMensaje("simulacion.diagnostico.general.columna.total.cos.fal.sanitario");
    private final String columnaFaltanteCirculacion = Utilidades.obtenerMensaje("simulacion.diagnostico.general.columna.total.fal.circulaciones");
    private final String columnaCostoFaltanteCirculacion = Utilidades.obtenerMensaje("simulacion.diagnostico.general.columna.total.cos.fal.circulaciones");
    
    private final String columnaCostoM2NuevaConstruccion = Utilidades.obtenerMensaje("simulacion.diagnostico.general.columna.total.cos.nue.contr");
    private final String columnaValorTotal = Utilidades.obtenerMensaje("simulacion.diagnostico.general.columna.total.valor");

    @PostConstruct
    public void inicializar() {
        super.init();
        this.lista = new ArrayList<Registro>();
        this.cargarCamposOpcionales();
        this.removerAgrupamiento(ParamNivelConsul.SEDE);
    }

    public void consultar() {
        try {
            filtro.inicializar();
            filtro.setPeriodo(periodo);
            filtro.setAgrupamiento(seleccionAgrupamiento);
            filtro.setIdNivel0(consultaAmbito.getSeleccionNivel0() != null ? consultaAmbito.getSeleccionNivel0().getId() : null);
            filtro.setIdNivel1(consultaAmbito.getSeleccionNivel1() != null ? consultaAmbito.getSeleccionNivel1().getId() : null);
            filtro.setIdNivel2(consultaAmbito.getSeleccionNivel2() != null ? consultaAmbito.getSeleccionNivel2().getId() : null);
            filtro.setIdNivel3(consultaAmbito.getSeleccionNivel3() != null ? consultaAmbito.getSeleccionNivel3().getId() : null);
            filtro.setIdNivel4(consultaAmbito.getSeleccionNivel4() != null ? consultaAmbito.getSeleccionNivel4().getId() : null);
            filtro.setIdNivel5(consultaAmbito.getSeleccionNivel5() != null ? consultaAmbito.getSeleccionNivel5().getId() : null);

            filtro.setIdEstablecimiento(seleccionEst != null ? seleccionEst.getId() : null);
            filtro.setIdSede(seleccionSede != null ? seleccionSede.getId() : null);
            filtro.setIdPredio(seleccionPredio != null ? seleccionPredio.getId() : null);
            filtro.setCodZona(seleccionZona != null ? seleccionZona.getTipValor().getCodigo() : null);
            filtro.setCodSector(seleccionSector != null ? seleccionSector.getTipValor().getCodigo() : null);

            List<Object[]> resultados = facSimulacion.consultarDiagnosticoGeneral(filtro);
            lista = new ArrayList<Registro>(resultados.size());
            if (resultados.isEmpty()) {
                mensajeTablaVacia = Utilidades.obtenerMensaje("aplicacion.general.noExistenRegistros");
            } else {
                this.inicializarTotalesConsulta();
//int cont = 0;
                for (Object[] fila : resultados) {
                    Registro registro = new Registro();
                    int i = 0;
                    if (filtro.isAgrupamientoNivel0()) {
                        registro.setNivel0(UtilConsulta.stringValueOf(fila[i++]));
                    } else if (filtro.isAgrupamientoNivel1()) {
                        registro.setCodNivel1(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                    } else if (filtro.isAgrupamientoNivel2()) {
                        registro.setCodNivel1(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setCodNivel2(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setNivel2(UtilConsulta.stringValueOf(fila[i++]));
                    } else if (filtro.isAgrupamientoNivel3()) {
                        registro.setCodNivel1(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setCodNivel2(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setNivel2(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setCodNivel3(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setNivel3(UtilConsulta.stringValueOf(fila[i++]));
                    } else if (filtro.isAgrupamientoNivel4()) {
                        registro.setCodNivel1(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setCodNivel2(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setNivel2(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setCodNivel3(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setNivel3(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setCodNivel4(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setNivel4(UtilConsulta.stringValueOf(fila[i++]));
                    } else if (filtro.isAgrupamientoNivel5()) {
                        registro.setCodNivel1(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setCodNivel2(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setNivel2(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setCodNivel3(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setNivel3(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setCodNivel4(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setNivel4(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setCodNivel5(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setNivel5(UtilConsulta.stringValueOf(fila[i++]));
                    } else if (filtro.isAgrupamientoEstablecimiento()) {
                        registro.setCodNivel1(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setCodNivel2(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setNivel2(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setCodNivel3(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setNivel3(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setCodNivel4(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setNivel4(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setCodNivel5(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setNivel5(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setCodEstablecimiento(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setNomEstablecimiento(UtilConsulta.stringValueOf(fila[i++]));
                    } else if (filtro.isAgrupamientoSede()) {
                        registro.setCodNivel1(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setCodNivel2(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setNivel2(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setCodNivel3(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setNivel3(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setCodNivel4(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setNivel4(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setCodNivel5(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setNivel5(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setCodEstablecimiento(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setNomEstablecimiento(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setCodSede(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setNomSede(UtilConsulta.stringValueOf(fila[i++]));
                    } else if (filtro.isAgrupamientoPredio()) {
                        registro.setCodNivel1(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setNivel1(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setCodNivel2(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setNivel2(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setCodNivel3(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setNivel3(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setCodNivel4(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setNivel4(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setCodNivel5(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setNivel5(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setCodEstablecimiento(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setNomEstablecimiento(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setCodSede(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setNomSede(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setCodPredio(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setNomPredio(UtilConsulta.stringValueOf(fila[i++]));
                        //otros
                        registro.setDirPredio(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setPropiedadPredio(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setZona(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]), getUsuarioSesion().getUsuario().getIdioma().getId()));
                        registro.setSector(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]), getUsuarioSesion().getUsuario().getIdioma().getId()));
                        registro.setClima(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]), getUsuarioSesion().getUsuario().getIdioma().getId()));
                    }
                    // calculos
                    registro.setM2RequeridosTotal(UtilConsulta.doubleValueOf(fila[i++]));
                    registro.setM2DisponiblesTotal(UtilConsulta.doubleValueOf(fila[i++]));
                    registro.setM2DeficitTotal(UtilConsulta.doubleValueOf(fila[i++]));
                    // aula
                    registro.setIdoneidadAula(UtilConsulta.doubleValueOf(fila[i++]));
                    registro.setM2RequeridosRealesAula(UtilConsulta.doubleValueOf(fila[i++]));
                    registro.setM2RequeridosProyectadosAula(UtilConsulta.doubleValueOf(fila[i++]));
                    registro.setUnidadesAula(UtilConsulta.longValueOf(fila[i++]));
                    registro.setM2DisponiblesAula(UtilConsulta.doubleValueOf(fila[i++]));
                    registro.setM2CostosAula(UtilConsulta.doubleValueOf(fila[i++]));
                    // sanitario
                    registro.setIdoneidadSanitario(UtilConsulta.doubleValueOf(fila[i++]));
                    registro.setM2RequeridosRealesSanitario(UtilConsulta.doubleValueOf(fila[i++]));
                    registro.setM2RequeridosProyectadosSanitario(UtilConsulta.doubleValueOf(fila[i++]));
                    registro.setUnidadesSanitario(UtilConsulta.longValueOf(fila[i++]));
                    registro.setM2DisponiblesSanitario(UtilConsulta.doubleValueOf(fila[i++]));
                    registro.setM2CostosSanitario(UtilConsulta.doubleValueOf(fila[i++]));
                    // circulacion
                    registro.setM2RequeridosCirculacion(UtilConsulta.doubleValueOf(fila[i++]));
                    registro.setM2DisponiblesCirculacion(UtilConsulta.doubleValueOf(fila[i++]));
                    registro.setM2CostosCirculacion(UtilConsulta.doubleValueOf(fila[i++]));
                    // otros
                    registro.setCostoM2Disponibles(UtilConsulta.doubleValueOf(fila[i++]));
                    registro.setM2RequeridoNuevoLote(UtilConsulta.doubleValueOf(fila[i++]));
                    registro.setCostoM2Lote(UtilConsulta.doubleValueOf(fila[i++]));
                    registro.setFaltanteAula(UtilConsulta.doubleValueOf(fila[i++]));
                    registro.setCostoFaltanteAula(UtilConsulta.doubleValueOf(fila[i++]));
                    registro.setFaltanteSanitario(UtilConsulta.doubleValueOf(fila[i++]));
                    registro.setCostoFaltanteSanitario(UtilConsulta.doubleValueOf(fila[i++]));
                    registro.setFaltanteCirculacion(UtilConsulta.doubleValueOf(fila[i++]));
                    registro.setCostoFaltanteCirculacion(UtilConsulta.doubleValueOf(fila[i++]));
                    registro.setCostoM2NuevaConstruccion(UtilConsulta.doubleValueOf(fila[i++]));
                    registro.setValorTotal(UtilConsulta.doubleValueOf(fila[i++]));
                    // totalizar
                    this.totalizarRegistro(registro);
                    lista.add(registro);
                }
                lazyLista = new LazyCostoDataModel(lista);
            }
        } catch (Exception e) {
            Messages.addFlashGlobalError(e.getMessage());
            UtilOut.println(e);
        }
    }

    @Override
    public void resetearFiltros() {
        consultaAmbito.resetearNiveles();
        this.resetearListasEstSedesPredios();
        this.seleccionSector = null;
        this.seleccionZona = null;
        consultaAmbito.nivelDPAOfAccesoUsuario();
        this.seleccionAgrupamiento = ParamNivelConsul.PREDIO.getNivel();
        this.resetarConsulta();
    }

    @Override
    public void cambioAgrupamiento() {
        this.resetarConsulta();
    }

    public void cambioFiltroArea() {
        this.resetarConsulta();
    }

    private void resetarConsulta() {
        // columnas
        this.cargarCamposOpcionales();
        // resultados
        this.lista.clear();
        lazyLista = new LazyCostoDataModel(lista);
        inicializarTotalesConsulta();
    }

    private void cargarCamposOpcionales() {
        selccionCamposOpcionales.clear();
        listaCamposOpcionales.clear();
       this.cargarCamposOpcionalesComunes();
        if (isMostrarColDirPredio()) {
            listaCamposOpcionales.add(columnaDirPredio);
        }
        if (isMostrarColPropiedadPredio()) {
            listaCamposOpcionales.add(columnaPropPredio);
        }
        if (isMostrarColSector()) {
            listaCamposOpcionales.add(columnaSector);
        }
        if (isMostrarColZona()) {
            listaCamposOpcionales.add(columnaZona);
        }
        if (isMostrarColClima()) {
            listaCamposOpcionales.add(columnaClima);
        }
        // agregando columnas
        // totales
        listaCamposOpcionales.add(columnaM2RequeridosTotal);
        listaCamposOpcionales.add(columnaM2DisponiblesTotal);
        listaCamposOpcionales.add(columnaM2DeficitTotal);
        //aula
        listaCamposOpcionales.add(columnaIdoneidadAula);
        listaCamposOpcionales.add(columnaM2RequeridosRealesAula);
        listaCamposOpcionales.add(columnaM2RequeridosProyectadosAula);
        listaCamposOpcionales.add(columnaUnidadesAula);
        listaCamposOpcionales.add(columnaM2DisponiblesAula);
        listaCamposOpcionales.add(columnaM2CostosAula);
        // sanitario
        listaCamposOpcionales.add(columnaIdoneidadSanitario);
        listaCamposOpcionales.add(columnaM2RequeridosRealesSanitario);
        listaCamposOpcionales.add(columnaM2RequeridosProyectadosSanitario);
        listaCamposOpcionales.add(columnaUnidadesSanitario);
        listaCamposOpcionales.add(columnaM2DisponiblesSanitario);
        listaCamposOpcionales.add(columnaM2CostosSanitario);
        // circulacion
        listaCamposOpcionales.add(columnaM2RequeridosCirculacion);
        listaCamposOpcionales.add(columnaM2DisponiblesCirculacion);
        listaCamposOpcionales.add(columnaM2CostosCirculacion);
        //otro
        listaCamposOpcionales.add(columnaCostoM2Disponibles);
        listaCamposOpcionales.add(columnaM2RequeridoNuevoLote);
        listaCamposOpcionales.add(columnaCostoM2Lote);
        listaCamposOpcionales.add(columnaFaltanteAula);
        listaCamposOpcionales.add(columnaCostoFaltanteAula);
        listaCamposOpcionales.add(columnaFaltanteSanitario);
        listaCamposOpcionales.add(columnaCostoFaltanteSanitario);
        listaCamposOpcionales.add(columnaFaltanteCirculacion);
        listaCamposOpcionales.add(columnaCostoFaltanteCirculacion);
        listaCamposOpcionales.add(columnaCostoM2NuevaConstruccion);
        listaCamposOpcionales.add(columnaValorTotal);

        // Seleccion columnas
        // totales
        selccionCamposOpcionales.add(columnaM2RequeridosTotal);
        selccionCamposOpcionales.add(columnaM2DisponiblesTotal);
        selccionCamposOpcionales.add(columnaM2DeficitTotal);
        //aula
        selccionCamposOpcionales.add(columnaIdoneidadAula);
        selccionCamposOpcionales.add(columnaM2RequeridosRealesAula);
        selccionCamposOpcionales.add(columnaM2RequeridosProyectadosAula);
        selccionCamposOpcionales.add(columnaUnidadesAula);
        selccionCamposOpcionales.add(columnaM2DisponiblesAula);
        selccionCamposOpcionales.add(columnaM2CostosAula);
        // sanitario
        selccionCamposOpcionales.add(columnaIdoneidadSanitario);
        selccionCamposOpcionales.add(columnaM2RequeridosRealesSanitario);
        selccionCamposOpcionales.add(columnaM2RequeridosProyectadosSanitario);
        selccionCamposOpcionales.add(columnaUnidadesSanitario);
        selccionCamposOpcionales.add(columnaM2DisponiblesSanitario);
        selccionCamposOpcionales.add(columnaM2CostosSanitario);
        // circulacion
        selccionCamposOpcionales.add(columnaM2RequeridosCirculacion);
        selccionCamposOpcionales.add(columnaM2DisponiblesCirculacion);
        selccionCamposOpcionales.add(columnaM2CostosCirculacion);
        //otro
        selccionCamposOpcionales.add(columnaCostoM2Disponibles);
        selccionCamposOpcionales.add(columnaM2RequeridoNuevoLote);
        selccionCamposOpcionales.add(columnaCostoM2Lote);
        selccionCamposOpcionales.add(columnaFaltanteAula);
        selccionCamposOpcionales.add(columnaCostoFaltanteAula);
        selccionCamposOpcionales.add(columnaFaltanteSanitario);
        selccionCamposOpcionales.add(columnaCostoFaltanteSanitario);
        selccionCamposOpcionales.add(columnaFaltanteCirculacion);
        selccionCamposOpcionales.add(columnaCostoFaltanteCirculacion);
        selccionCamposOpcionales.add(columnaCostoM2NuevaConstruccion);
        selccionCamposOpcionales.add(columnaValorTotal);
    }

    public List<Registro> getLista() {
        return lista;
    }   

    public LazyDataModel<Registro> getLazyLista() {
        return lazyLista;
    }
    
    public Registro getTotalConsulta() {
        return totalConsulta;
    }

    /*public class Registro {
        // niveles

        private String nivel0;
        private String nivel1;
        private String nivel2;
        private String nivel3;
        private String nivel4;
        private String nivel5;
        private String codEstablecimiento;
        private String nomEstablecimiento;
        private String codSede;
        private String nomSede;        // 
        private String codPredio;
        private String nomPredio;
        // otros
        private String dirPredio;
        private String propiedadPredio;
        private String zona;
        private String sector;
        private String clima;
        //calculos
        private Double m2RequeridosTotal;
        private Double m2DisponiblesTotal;
        private Double m2DeficitTotal;
        //aula
        private Double idoneidadAula;
        private Double m2RequeridosRealesAula;
        private Double m2RequeridosProyectadosAula;
        private Long unidadesAula;
        private Double m2DisponiblesAula;
        private Double m2CostosAula;
        // sanitario
        private Double idoneidadSanitario;
        private Double m2RequeridosRealesSanitario;
        private Double m2RequeridosProyectadosSanitario;
        private Long unidadesSanitario;
        private Double m2DisponiblesSanitario;
        private Double m2CostosSanitario;
        // circulacion
        private Double m2RequeridosCirculacion;
        private Double m2DisponiblesCirculacion;
        private Double m2CostosCirculacion;
        // otros
        private Double costoM2Disponibles;
        private Double m2RequeridoNuevoLote;
        private Double costoM2Lote;
        private Double faltanteAula;
        private Double costoFaltanteAula;
        private Double faltanteSanitario;
        private Double costoFaltanteSanitario;
        private Double faltanteCirculacion;
        private Double costoFaltanteCirculacion;
        private Double costoM2NuevaConstruccion;
        private Double valorTotal;

        public String getNivel0() {
            return nivel0;
        }

        public void setNivel0(String nivel0) {
            this.nivel0 = nivel0;
        }

        public String getNivel1() {
            return nivel1;
        }

        public void setNivel1(String nivel1) {
            this.nivel1 = nivel1;
        }

        public String getNivel2() {
            return nivel2;
        }

        public void setNivel2(String nivel2) {
            this.nivel2 = nivel2;
        }

        public String getNivel3() {
            return nivel3;
        }

        public void setNivel3(String nivel3) {
            this.nivel3 = nivel3;
        }

        public String getNivel4() {
            return nivel4;
        }

        public void setNivel4(String nivel4) {
            this.nivel4 = nivel4;
        }

        public String getNivel5() {
            return nivel5;
        }

        public void setNivel5(String nivel5) {
            this.nivel5 = nivel5;
        }

        public String getCodEstablecimiento() {
            return codEstablecimiento;
        }

        public void setCodEstablecimiento(String codEstablecimiento) {
            this.codEstablecimiento = codEstablecimiento;
        }

        public String getNomEstablecimiento() {
            return nomEstablecimiento;
        }

        public void setNomEstablecimiento(String nomEstablecimiento) {
            this.nomEstablecimiento = nomEstablecimiento;
        }

        public String getCodSede() {
            return codSede;
        }

        public void setCodSede(String codSede) {
            this.codSede = codSede;
        }

        public String getNomSede() {
            return nomSede;
        }

        public void setNomSede(String nomSede) {
            this.nomSede = nomSede;
        }

        public String getCodPredio() {
            return codPredio;
        }

        public void setCodPredio(String codPredio) {
            this.codPredio = codPredio;
        }

        public String getNomPredio() {
            return nomPredio;
        }

        public void setNomPredio(String nomPredio) {
            this.nomPredio = nomPredio;
        }

        public String getDirPredio() {
            return dirPredio;
        }

        public void setDirPredio(String dirPredio) {
            this.dirPredio = dirPredio;
        }

        public String getPropiedadPredio() {
            return propiedadPredio;
        }

        public void setPropiedadPredio(String propiedadPredio) {
            this.propiedadPredio = propiedadPredio;
        }

        public String getZona() {
            return zona;
        }

        public void setZona(String zona) {
            this.zona = zona;
        }

        public String getSector() {
            return sector;
        }

        public void setSector(String sector) {
            this.sector = sector;
        }

        public String getClima() {
            return clima;
        }

        public void setClima(String clima) {
            this.clima = clima;
        }

        public Double getM2RequeridosTotal() {
            return m2RequeridosTotal;
        }

        public void setM2RequeridosTotal(Double m2RequeridosTotal) {
            this.m2RequeridosTotal = m2RequeridosTotal;
        }

        public Double getM2DisponiblesTotal() {
            return m2DisponiblesTotal;
        }

        public void setM2DisponiblesTotal(Double m2DisponiblesTotal) {
            this.m2DisponiblesTotal = m2DisponiblesTotal;
        }

        public Double getM2DeficitTotal() {
            return m2DeficitTotal;
        }

        public void setM2DeficitTotal(Double m2DeficitTotal) {
            this.m2DeficitTotal = m2DeficitTotal;
        }

        public Double getIdoneidadAula() {
            return idoneidadAula;
        }

        public void setIdoneidadAula(Double idoneidadAula) {
            this.idoneidadAula = idoneidadAula;
        }

        public Double getM2RequeridosRealesAula() {
            return m2RequeridosRealesAula;
        }

        public void setM2RequeridosRealesAula(Double m2RequeridosRealesAula) {
            this.m2RequeridosRealesAula = m2RequeridosRealesAula;
        }

        public Double getM2RequeridosProyectadosAula() {
            return m2RequeridosProyectadosAula;
        }

        public void setM2RequeridosProyectadosAula(Double m2RequeridosProyectadosAula) {
            this.m2RequeridosProyectadosAula = m2RequeridosProyectadosAula;
        }

        public Long getUnidadesAula() {
            return unidadesAula;
        }

        public void setUnidadesAula(Long unidadesAula) {
            this.unidadesAula = unidadesAula;
        }

        public Double getM2DisponiblesAula() {
            return m2DisponiblesAula;
        }

        public void setM2DisponiblesAula(Double m2DisponiblesAula) {
            this.m2DisponiblesAula = m2DisponiblesAula;
        }

        public Double getM2CostosAula() {
            return m2CostosAula;
        }

        public void setM2CostosAula(Double m2CostosAula) {
            this.m2CostosAula = m2CostosAula;
        }

        public Double getIdoneidadSanitario() {
            return idoneidadSanitario;
        }

        public void setIdoneidadSanitario(Double idoneidadSanitario) {
            this.idoneidadSanitario = idoneidadSanitario;
        }

        public Double getM2RequeridosRealesSanitario() {
            return m2RequeridosRealesSanitario;
        }

        public void setM2RequeridosRealesSanitario(Double m2RequeridosRealesSanitario) {
            this.m2RequeridosRealesSanitario = m2RequeridosRealesSanitario;
        }

        public Double getM2RequeridosProyectadosSanitario() {
            return m2RequeridosProyectadosSanitario;
        }

        public void setM2RequeridosProyectadosSanitario(Double m2RequeridosProyectadosSanitario) {
            this.m2RequeridosProyectadosSanitario = m2RequeridosProyectadosSanitario;
        }

        public Long getUnidadesSanitario() {
            return unidadesSanitario;
        }

        public void setUnidadesSanitario(Long unidadesSanitario) {
            this.unidadesSanitario = unidadesSanitario;
        }

        public Double getM2DisponiblesSanitario() {
            return m2DisponiblesSanitario;
        }

        public void setM2DisponiblesSanitario(Double m2DisponiblesSanitario) {
            this.m2DisponiblesSanitario = m2DisponiblesSanitario;
        }

        public Double getM2CostosSanitario() {
            return m2CostosSanitario;
        }

        public void setM2CostosSanitario(Double m2CostosSanitario) {
            this.m2CostosSanitario = m2CostosSanitario;
        }

        public Double getM2RequeridosCirculacion() {
            return m2RequeridosCirculacion;
        }

        public void setM2RequeridosCirculacion(Double m2RequeridosCirculacion) {
            this.m2RequeridosCirculacion = m2RequeridosCirculacion;
        }

        public Double getM2DisponiblesCirculacion() {
            return m2DisponiblesCirculacion;
        }

        public void setM2DisponiblesCirculacion(Double m2DisponiblesCirculacion) {
            this.m2DisponiblesCirculacion = m2DisponiblesCirculacion;
        }

        public Double getM2CostosCirculacion() {
            return m2CostosCirculacion;
        }

        public void setM2CostosCirculacion(Double m2CostosCirculacion) {
            this.m2CostosCirculacion = m2CostosCirculacion;
        }

        public Double getCostoM2Disponibles() {
            return costoM2Disponibles;
        }

        public void setCostoM2Disponibles(Double costoM2Disponibles) {
            this.costoM2Disponibles = costoM2Disponibles;
        }

        public Double getM2RequeridoNuevoLote() {
            return m2RequeridoNuevoLote;
        }

        public void setM2RequeridoNuevoLote(Double m2RequeridoNuevoLote) {
            this.m2RequeridoNuevoLote = m2RequeridoNuevoLote;
        }

        public Double getCostoM2Lote() {
            return costoM2Lote;
        }

        public void setCostoM2Lote(Double costoM2Lote) {
            this.costoM2Lote = costoM2Lote;
        }

        public Double getFaltanteAula() {
            return faltanteAula;
        }

        public void setFaltanteAula(Double faltanteAula) {
            this.faltanteAula = faltanteAula;
        }

        public Double getCostoFaltanteAula() {
            return costoFaltanteAula;
        }

        public void setCostoFaltanteAula(Double costoFaltanteAula) {
            this.costoFaltanteAula = costoFaltanteAula;
        }

        public Double getFaltanteSanitario() {
            return faltanteSanitario;
        }

        public void setFaltanteSanitario(Double faltanteSanitario) {
            this.faltanteSanitario = faltanteSanitario;
        }

        public Double getCostoFaltanteSanitario() {
            return costoFaltanteSanitario;
        }

        public void setCostoFaltanteSanitario(Double costoFaltanteSanitario) {
            this.costoFaltanteSanitario = costoFaltanteSanitario;
        }

        public Double getFaltanteCirculacion() {
            return faltanteCirculacion;
        }

        public void setFaltanteCirculacion(Double faltanteCirculacion) {
            this.faltanteCirculacion = faltanteCirculacion;
        }

        public Double getCostoFaltanteCirculacion() {
            return costoFaltanteCirculacion;
        }

        public void setCostoFaltanteCirculacion(Double costoFaltanteCirculacion) {
            this.costoFaltanteCirculacion = costoFaltanteCirculacion;
        }

        public Double getCostoM2NuevaConstruccion() {
            return costoM2NuevaConstruccion;
        }

        public void setCostoM2NuevaConstruccion(Double costoM2NuevaConstruccion) {
            this.costoM2NuevaConstruccion = costoM2NuevaConstruccion;
        }

        public Double getValorTotal() {
            return valorTotal;
        }

        public void setValorTotal(Double valorTotal) {
            this.valorTotal = valorTotal;
        }
        
    }*/

    private void inicializarTotalesConsulta() {
        //calculos
        totalConsulta.setM2RequeridosTotal(0D);
        totalConsulta.setM2DisponiblesTotal(0D);
        totalConsulta.setM2DeficitTotal(0D);
        //aula
        totalConsulta.setIdoneidadAula(0D);
        totalConsulta.setM2RequeridosRealesAula(0D);
        totalConsulta.setM2RequeridosProyectadosAula(0D);
        totalConsulta.setUnidadesAula(0L);
        totalConsulta.setM2DisponiblesAula(0D);
        totalConsulta.setM2CostosAula(0D);
        // sanitario
        totalConsulta.setIdoneidadSanitario(0D);
        totalConsulta.setM2RequeridosRealesSanitario(0D);
        totalConsulta.setM2RequeridosProyectadosSanitario(0D);
        totalConsulta.setUnidadesSanitario(0L);
        totalConsulta.setM2DisponiblesSanitario(0D);
        totalConsulta.setM2CostosSanitario(0D);
        // circulacion
        totalConsulta.setM2RequeridosCirculacion(0D);
        totalConsulta.setM2DisponiblesCirculacion(0D);
        totalConsulta.setM2CostosCirculacion(0D);
        // otros
        totalConsulta.setCostoM2Disponibles(0D);
        totalConsulta.setM2RequeridoNuevoLote(0D);
        totalConsulta.setCostoM2Lote(0D);
        totalConsulta.setFaltanteAula(0D);
        totalConsulta.setCostoFaltanteAula(0D);
        totalConsulta.setFaltanteSanitario(0D);
        totalConsulta.setCostoFaltanteSanitario(0D);
        totalConsulta.setFaltanteCirculacion(0D);
        totalConsulta.setCostoFaltanteCirculacion(0D);
        totalConsulta.setCostoM2NuevaConstruccion(0D);
        totalConsulta.setValorTotal(0D);
    }

    private void totalizarRegistro(Registro registro) {
        //calculos
        if (registro.getM2RequeridosTotal() != null) {
            totalConsulta.setM2RequeridosTotal(totalConsulta.getM2RequeridosTotal() + registro.getM2RequeridosTotal());
        }
        if (registro.getM2DisponiblesTotal() != null) {
            totalConsulta.setM2DisponiblesTotal(totalConsulta.getM2DisponiblesTotal() + registro.getM2DisponiblesTotal());
        }
        if (registro.getM2DeficitTotal() != null) {
            totalConsulta.setM2DeficitTotal(totalConsulta.getM2DeficitTotal() + registro.getM2DeficitTotal());
        }
        //aula
        if (registro.getIdoneidadAula() != null) {
            totalConsulta.setIdoneidadAula(totalConsulta.getIdoneidadAula() + registro.getIdoneidadAula());
        }
        if (registro.getM2RequeridosRealesAula() != null) {
            totalConsulta.setM2RequeridosRealesAula(totalConsulta.getM2RequeridosRealesAula() + registro.getM2RequeridosRealesAula());
        }
        if (registro.getM2RequeridosProyectadosAula()!= null) {
            totalConsulta.setM2RequeridosProyectadosAula(totalConsulta.getM2RequeridosProyectadosAula() + registro.getM2RequeridosProyectadosAula());
        }
        if (registro.getUnidadesAula()!= null) {
            totalConsulta.setUnidadesAula(totalConsulta.getUnidadesAula() + registro.getUnidadesAula());
        }
        if (registro.getM2DisponiblesAula() != null) {
            totalConsulta.setM2DisponiblesAula(totalConsulta.getM2DisponiblesAula() + registro.getM2DisponiblesAula());
        }
        if (registro.getM2CostosAula() != null) {
            totalConsulta.setM2CostosAula(totalConsulta.getM2CostosAula() + registro.getM2CostosAula());
        }
        // sanitario
        if (registro.getIdoneidadSanitario() != null) {
            totalConsulta.setIdoneidadSanitario(totalConsulta.getIdoneidadSanitario() + registro.getIdoneidadSanitario());
        }
        if (registro.getM2RequeridosRealesSanitario() != null) {
            totalConsulta.setM2RequeridosRealesSanitario(totalConsulta.getM2RequeridosRealesSanitario() + registro.getM2RequeridosRealesSanitario());
        }
        if (registro.getM2RequeridosProyectadosSanitario()!= null) {
            totalConsulta.setM2RequeridosProyectadosSanitario(totalConsulta.getM2RequeridosProyectadosSanitario() + registro.getM2RequeridosProyectadosSanitario());
        }
        if (registro.getUnidadesSanitario()!= null) {
            totalConsulta.setUnidadesSanitario(totalConsulta.getUnidadesSanitario() + registro.getUnidadesSanitario());
        }
        if (registro.getM2DisponiblesSanitario() != null) {
            totalConsulta.setM2DisponiblesSanitario(totalConsulta.getM2DisponiblesSanitario() + registro.getM2DisponiblesSanitario());
        }
        if (registro.getM2CostosSanitario() != null) {
            totalConsulta.setM2CostosSanitario(totalConsulta.getM2CostosSanitario() + registro.getM2CostosSanitario());
        }
        // circulacion
        if (registro.getM2RequeridosCirculacion() != null) {
            totalConsulta.setM2RequeridosCirculacion(totalConsulta.getM2RequeridosCirculacion() + registro.getM2RequeridosCirculacion());
        }
        if (registro.getM2DisponiblesCirculacion() != null) {
            totalConsulta.setM2DisponiblesCirculacion(totalConsulta.getM2DisponiblesCirculacion() + registro.getM2DisponiblesCirculacion());
        }
        if (registro.getM2CostosCirculacion() != null) {
            totalConsulta.setM2CostosCirculacion(totalConsulta.getM2CostosCirculacion() + registro.getM2CostosCirculacion());
        }
        //otros
        if (registro.getCostoM2Disponibles() != null) {
            totalConsulta.setCostoM2Disponibles(totalConsulta.getCostoM2Disponibles() + registro.getCostoM2Disponibles());
        }
        if (registro.getM2RequeridoNuevoLote() != null) {
            totalConsulta.setM2RequeridoNuevoLote(totalConsulta.getM2RequeridoNuevoLote() + registro.getM2RequeridoNuevoLote());
        }
        if (registro.getCostoM2Lote() != null) {
            totalConsulta.setCostoM2Lote(totalConsulta.getCostoM2Lote() + registro.getCostoM2Lote());
        }
        if (registro.getFaltanteAula()!= null) {
            totalConsulta.setFaltanteAula(totalConsulta.getFaltanteAula() + registro.getFaltanteAula());
        }
        if (registro.getCostoFaltanteAula()!= null) {
            totalConsulta.setCostoFaltanteAula(totalConsulta.getCostoFaltanteAula() + registro.getCostoFaltanteAula());
        }
        if (registro.getFaltanteSanitario()!= null) {
            totalConsulta.setFaltanteSanitario(totalConsulta.getFaltanteSanitario() + registro.getFaltanteSanitario());
        }
        if (registro.getCostoFaltanteSanitario()!= null) {
            totalConsulta.setCostoFaltanteSanitario(totalConsulta.getCostoFaltanteSanitario() + registro.getCostoFaltanteSanitario());
        }
        if (registro.getFaltanteCirculacion()!= null) {
            totalConsulta.setFaltanteCirculacion(totalConsulta.getFaltanteCirculacion() + registro.getFaltanteCirculacion());
        }
        if (registro.getCostoFaltanteCirculacion()!= null) {
            totalConsulta.setCostoFaltanteCirculacion(totalConsulta.getCostoFaltanteCirculacion() + registro.getCostoFaltanteCirculacion());
        }
        if (registro.getCostoM2NuevaConstruccion() != null) {
            totalConsulta.setCostoM2NuevaConstruccion(totalConsulta.getCostoM2NuevaConstruccion() + registro.getCostoM2NuevaConstruccion());
        }
        if (registro.getValorTotal() != null) {
            totalConsulta.setValorTotal(totalConsulta.getValorTotal() + registro.getValorTotal());
        }
    }

    public String getColumnaM2RequeridosTotal() {
        return columnaM2RequeridosTotal;
    }

    public String getColumnaM2DisponiblesTotal() {
        return columnaM2DisponiblesTotal;
    }

    public String getColumnaM2DeficitTotal() {
        return columnaM2DeficitTotal;
    }

    public String getColumnaIdoneidadAula() {
        return columnaIdoneidadAula;
    }

    public String getColumnaM2RequeridosRealesAula() {
        return columnaM2RequeridosRealesAula;
    }

    public String getColumnaM2RequeridosProyectadosAula() {
        return columnaM2RequeridosProyectadosAula;
    }

    public String getColumnaUnidadesAula() {
        return columnaUnidadesAula;
    }

    public String getColumnaM2DisponiblesAula() {
        return columnaM2DisponiblesAula;
    }

    public String getColumnaM2CostosAula() {
        return columnaM2CostosAula;
    }

    public String getColumnaIdoneidadSanitario() {
        return columnaIdoneidadSanitario;
    }

    public String getColumnaM2RequeridosRealesSanitario() {
        return columnaM2RequeridosRealesSanitario;
    }

    public String getColumnaM2RequeridosProyectadosSanitario() {
        return columnaM2RequeridosProyectadosSanitario;
    }

    public String getColumnaUnidadesSanitario() {
        return columnaUnidadesSanitario;
    }

    public String getColumnaM2DisponiblesSanitario() {
        return columnaM2DisponiblesSanitario;
    }

    public String getColumnaM2CostosSanitario() {
        return columnaM2CostosSanitario;
    }

    public String getColumnaM2RequeridosCirculacion() {
        return columnaM2RequeridosCirculacion;
    }

    public String getColumnaM2DisponiblesCirculacion() {
        return columnaM2DisponiblesCirculacion;
    }

    public String getColumnaM2CostosCirculacion() {
        return columnaM2CostosCirculacion;
    }

    public String getColumnaCostoM2Disponibles() {
        return columnaCostoM2Disponibles;
    }

    public String getColumnaM2RequeridoNuevoLote() {
        return columnaM2RequeridoNuevoLote;
    }

    public String getColumnaCostoM2Lote() {
        return columnaCostoM2Lote;
    }

    public String getColumnaFaltanteAula() {
        return columnaFaltanteAula;
    }

    public String getColumnaCostoFaltanteAula() {
        return columnaCostoFaltanteAula;
    }

    public String getColumnaFaltanteSanitario() {
        return columnaFaltanteSanitario;
    }

    public String getColumnaCostoFaltanteSanitario() {
        return columnaCostoFaltanteSanitario;
    }

    public String getColumnaFaltanteCirculacion() {
        return columnaFaltanteCirculacion;
    }

    public String getColumnaCostoFaltanteCirculacion() {
        return columnaCostoFaltanteCirculacion;
    }

    public String getColumnaCostoM2NuevaConstruccion() {
        return columnaCostoM2NuevaConstruccion;
    }

    public String getColumnaValorTotal() {
        return columnaValorTotal;
    }
    
    
    public boolean isCumple(Object idoneidad) {
        if (idoneidad != null) {
            Double valor = (Double)idoneidad / 100D;
            return (valor.compareTo(1D) == 0 || valor.compareTo(1D) > 0) ? true : false;
        } else {
            return false;
        }
    }
    
    public boolean isNoCumple(Object idoneidad) {
        if (idoneidad != null) {
            Double valor = (Double)idoneidad / 100D;
            return (valor.compareTo(1D) < 0) ? true : false;
        } else {
            return false;
        }
    }
    
    public ConsultaAmbito getConsultaAmbito() {
        return consultaAmbito;
    }

    public void setConsultaAmbito(ConsultaAmbito consultaAmbito) {
        this.consultaAmbito = consultaAmbito;
    }
}
