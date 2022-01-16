/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.consulta.ambito;

import co.stackpointer.cier.modelo.entidad.instrumento.TipologiaValorNombre;
import co.stackpointer.cier.modelo.filtro.ambito.FiltroAmbitoSostenibilidad;
import co.stackpointer.cier.modelo.tipo.CalificacionCondicion;
import co.stackpointer.cier.modelo.tipo.TipologiaCodigo;
import co.stackpointer.cier.modelo.utilidad.UtilConsulta;
import co.stackpointer.cier.modelo.utilidad.UtilOut;
import co.stackpointer.cier.vista.Utilidades;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.omnifaces.util.Messages;

/**
 *
 * @author user
 */
@ManagedBean(name = "sostenibilidadBean")
@ViewScoped
public class SostenibilidadBean extends ConsultaAmbito {

    @ManagedProperty(value = "#{consultaAmbito}")
    private ConsultaAmbito consultaAmbito;
    private List<Registro> lista;
    private List<TipologiaValorNombre> listaClimas;
    private FiltroAmbitoSostenibilidad filtro = new FiltroAmbitoSostenibilidad();
    private Registro totalConsulta = new Registro();
    private TipologiaValorNombre seleccionClima;
    private String seleccionDisponibilidadReciclaje;
    private CalificacionCondicion seleccionCondicionReciclaje;
    private String seleccionAnalisisConsumoAgua;
    private String seleccionAnalisisConsumoEnergia;
    private final String columnaDisponibilidadReciclaje = Utilidades.obtenerMensaje("consultas.ambito.sostenibilidad.columna.disp.reciclaje");
    private final String columnaCondicionReciclaje = Utilidades.obtenerMensaje("consultas.ambito.sostenibilidad.columna.cond.reciclaje");
    private final String columnaConsumoAgua = Utilidades.obtenerMensaje("consultas.ambito.sostenibilidad.columna.consumo.agua");
    private final String columnaConsumoEnergia = Utilidades.obtenerMensaje("consultas.ambito.sostenibilidad.columna.consumo.energia");
    private final String columnaAnalisisConsumoAgua = Utilidades.obtenerMensaje("consultas.ambito.sostenibilidad.columna.analisis.consumo.agua");
    private final String columnaAnalisisConsumoEnergia = Utilidades.obtenerMensaje("consultas.ambito.sostenibilidad.columna.analisis.consumo.energia");
    private final String columnaDisponibilidadAreasVerdes = Utilidades.obtenerMensaje("consultas.ambito.sostenibilidad.columna.disp.area.verde");
    //otros niveles
    private final String columnaNumPrediosDispReciclaje = Utilidades.obtenerMensaje("consultas.ambito.sostenibilidad.columna.num.pred.disp.reciclaje");
    private final String columnaNumPrediosCondReciclaje = Utilidades.obtenerMensaje("consultas.ambito.sostenibilidad.columna.num.pred.cond.reciclaje");
    private final String columnaNumPrediosAnalisisAgua = Utilidades.obtenerMensaje("consultas.ambito.sostenibilidad.columna.num.pred.analisis.agua");
    private final String columnaNumPrediosAnalisisEnergia = Utilidades.obtenerMensaje("consultas.ambito.sostenibilidad.columna.num.pred.analisis.energia");
    private final String columnaNumTotalPredios = Utilidades.obtenerMensaje("consultas.ambito.sostenibilidad.columna.total.predios");
    private final String columnaPropPrediosDispReciclaje = Utilidades.obtenerMensaje("consultas.ambito.sostenibilidad.columna.prop.disp.reciclaje");
    private final String columnaPropPrediosCondReciclaje = Utilidades.obtenerMensaje("consultas.ambito.sostenibilidad.columna.prop.cond.reciclaje");
    private final String columnaPropPrediosAnalisisAgua = Utilidades.obtenerMensaje("consultas.ambito.sostenibilidad.columna.prop.pred.analisis.agua");
    private final String columnaPropPrediosAnalisisEnergia = Utilidades.obtenerMensaje("consultas.ambito.sostenibilidad.columna.prop.pred.analisis.energia");
    private final String columnaCumpAreaVerdeNormaLEED = Utilidades.obtenerMensaje("consultas.ambito.sostenibilidad.columna.cump.area.verde.norma.leed");
    private final String columnaNumEstabCumpAreaVerdeNormaLEED = Utilidades.obtenerMensaje("consultas.ambito.sostenibilidad.columna.num.estab.cump.area.verde.norma.leed");
    private final String columnaNumEstablecimientos = Utilidades.obtenerMensaje("consultas.ambito.sostenibilidad.columna.num.establecimientos");

    @PostConstruct
    public void inicializar() {
        //super.init();
        this.lista = new ArrayList<Registro>();
        this.listaClimas = facParam.consultarValoresTipologia(TipologiaCodigo.CLIMA.getValor(),getUsuarioSesion().getUsuario().getIdioma().getId());
        this.cargarCamposOpcionales();
    }

    public void consultar() {
        try {
            filtro.inicializar();
            filtro.setPeriodo(periodo);
            filtro.setIdioma(getUsuarioSesion().getUsuario().getIdioma().getId());
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
            //otros filtros
            filtro.setCodZona(seleccionZona != null ? seleccionZona.getTipValor().getCodigo() : null);
            filtro.setCodSector(seleccionSector != null ? seleccionSector.getTipValor().getCodigo() : null);
            filtro.setCodClima(seleccionClima != null ? seleccionClima.getTipValor().getCodigo() : null);
            //filtros indicador
            filtro.setDisponibilidadReciclaje(seleccionDisponibilidadReciclaje);
            filtro.setCondicionReciclaje(seleccionCondicionReciclaje != null ? seleccionCondicionReciclaje.getValor() : null);
            filtro.setAnalisisConsumoAgua(seleccionAnalisisConsumoAgua);
            filtro.setAnalisisConsumoEnergia(seleccionAnalisisConsumoEnergia);
            List<Object[]> resultados = facAmbito.consultarSostenibilidad(filtro);
            lista = new ArrayList<Registro>(resultados.size());
            this.inicializarTotalesConsulta();
            for (Object[] fila : resultados) {
                Registro registro = new Registro();
                int i = 0;
                if (filtro.isAgrupamientoPredio()) {
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
                    registro.setZona(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]),getUsuarioSesion().getUsuario().getIdioma().getId()));
                    registro.setSector(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]),getUsuarioSesion().getUsuario().getIdioma().getId()));
                    registro.setClima(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]),getUsuarioSesion().getUsuario().getIdioma().getId()));
                    registro.setEtnias(verificarEtnias(UtilConsulta.stringValueOf(fila[i++])));
                    //indicadores 
                    registro.setDisponibilidadReciclaje(Utilidades.obtenerMensaje(UtilConsulta.stringValueOf(fila[i++])));
                    registro.setCondicionReciclaje(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setConsumoAgua(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setConsumoEnergia(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setAnalisisConsumoAgua(Utilidades.obtenerMensaje(UtilConsulta.stringValueOf(fila[i++])));
                    registro.setAnalisisConsumoEnergia(Utilidades.obtenerMensaje(UtilConsulta.stringValueOf(fila[i++])));
                    registro.setDisponibilidadAreasVerdes(UtilConsulta.doubleValueOf(fila[i++]));
                } else {
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
                    }
                    // indicadores             
                    registro.setNumPrediosDispReciclaje(UtilConsulta.longValueOf(fila[i++]));                   
                    registro.setNumPrediosCondReciclaje(UtilConsulta.longValueOf(fila[i++]));
                    if (filtro.getAnalisisConsumoAgua() != null) {
                        registro.setNumPrediosAnalisisAgua(UtilConsulta.longValueOf(fila[i++]));
                    }
                    if (filtro.getAnalisisConsumoEnergia() != null) {
                        registro.setNumPrediosAnalisisEnergia(UtilConsulta.longValueOf(fila[i++]));
                    }
                    registro.setNumTotalPredios(UtilConsulta.longValueOf(fila[i++]));
                    if (isAgrupamientoEstablecimiento() || isAgrupamientoSede()) {
                        registro.setCumpAreaVerdeNormaLEED(UtilConsulta.doubleValueOf(fila[i++]));
                    } else {
                        registro.setNumEstabCumpAreaVerdeNormaLEED(UtilConsulta.longValueOf(fila[i++]));
                        registro.setNumTotalEstablecimientos(UtilConsulta.longValueOf(fila[i++]));
                    }
                    // proporciones
                    registro.setPropPrediosDispReciclaje(1.0 * registro.getNumPrediosDispReciclaje() / registro.getNumTotalPredios());
                    registro.setPropPrediosCondReciclaje(1.0 * registro.getNumPrediosCondReciclaje() / registro.getNumTotalPredios());
                    if (filtro.getAnalisisConsumoAgua() != null) {
                        registro.setPropPrediosAnalisisAgua(1.0 * registro.getNumPrediosAnalisisAgua() / registro.getNumTotalPredios());
                    }
                    if (filtro.getAnalisisConsumoEnergia() != null) {
                        registro.setPropPrediosAnalisisEnergia(1.0 * registro.getNumPrediosAnalisisEnergia() / registro.getNumTotalPredios());
                    }
                }
                // totales
                this.totalizarRegistro(registro);
                lista.add(registro);
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
        this.seleccionClima = null;
        this.seleccionSector = null;
        this.seleccionZona = null;
        this.seleccionDisponibilidadReciclaje = null;
        this.seleccionCondicionReciclaje = null;
        this.seleccionAnalisisConsumoAgua = null;
        this.seleccionAnalisisConsumoEnergia = null;        
        consultaAmbito.nivelDPAOfAccesoUsuario();
        this.resetearConsulta();
    }
    /**
     * Cuando se cambie el filtro de periodo este reseteara todos los campos parametricos de la consulta a excepcion
     * de los que sonparte de la DPA
     */
    public void resetearFiltrosPeriodo() {
        //consultaAmbito.resetearNiveles();
        this.resetearListasEstSedesPredios();
        this.seleccionClima = null;
        this.seleccionSector = null;
        this.seleccionZona = null;
        this.seleccionDisponibilidadReciclaje = null;
        this.seleccionCondicionReciclaje = null;
        this.seleccionAnalisisConsumoAgua = null;
        this.seleccionAnalisisConsumoEnergia = null;        
        //consultaAmbito.nivelDPAOfAccesoUsuario();
        this.resetearConsulta();
    }

    @Override
    public void cambioAgrupamiento() {
        if (!isAgrupamientoPredio()) {
            this.seleccionDisponibilidadReciclaje = null;
        }
        this.resetearConsulta();
    }

    private void resetearConsulta() {
        // columnas
        this.cargarCamposOpcionales();
        // resultados
        this.lista.clear();
        this.inicializarTotalesConsulta();
    }

    public void cambioAnalisisServicio() {
        this.resetearConsulta();
    }

    public TipologiaValorNombre getSeleccionClima() {
        return seleccionClima;
    }

    public void setSeleccionClima(TipologiaValorNombre seleccionClima) {
        this.seleccionClima = seleccionClima;
    }

    public List<TipologiaValorNombre> getListaClimas() {
        return listaClimas;
    }

    public void setListaClimas(List<TipologiaValorNombre> listaClimas) {
        this.listaClimas = listaClimas;
    }

    public void cargarCamposOpcionales() {
        selccionCamposOpcionales.clear();
        listaCamposOpcionales.clear();
        this.cargarCamposOpcionalesComunes();
        if (isMostrarColPropiedadPredio()) {
            listaCamposOpcionales.add(columnaPropPredio);
        }
        if (isMostrarColDirPredio()) {
            listaCamposOpcionales.add(columnaDirPredio);
        }
        if (isMostrarColSector()) {
            listaCamposOpcionales.add(columnaSector);
        }
        if (isMostrarColZona()) {
            listaCamposOpcionales.add(columnaZona);
        }
        if (isMostrarColEtnias()) {
            listaCamposOpcionales.add(columnaEtnias);
        }
        if (isAgrupamientoPredio()) {
            listaCamposOpcionales.add(columnaDisponibilidadReciclaje);
            listaCamposOpcionales.add(columnaCondicionReciclaje);
            listaCamposOpcionales.add(columnaConsumoAgua);
            listaCamposOpcionales.add(columnaConsumoEnergia);
            listaCamposOpcionales.add(columnaAnalisisConsumoAgua);
            listaCamposOpcionales.add(columnaAnalisisConsumoEnergia);
            listaCamposOpcionales.add(columnaClima);
            listaCamposOpcionales.add(columnaDisponibilidadAreasVerdes);
            // seleccionar
            selccionCamposOpcionales.add(columnaDisponibilidadReciclaje);
            selccionCamposOpcionales.add(columnaCondicionReciclaje);
            selccionCamposOpcionales.add(columnaConsumoAgua);
            selccionCamposOpcionales.add(columnaConsumoEnergia);
            selccionCamposOpcionales.add(columnaAnalisisConsumoAgua);
            selccionCamposOpcionales.add(columnaAnalisisConsumoEnergia);
            selccionCamposOpcionales.add(columnaClima);
            selccionCamposOpcionales.add(columnaDisponibilidadAreasVerdes);
        } else {
            //agregagabdo columnas
            listaCamposOpcionales.add(columnaNumPrediosDispReciclaje);            
            listaCamposOpcionales.add(columnaNumPrediosCondReciclaje);
            if (seleccionAnalisisConsumoAgua != null) {
                listaCamposOpcionales.add(columnaNumPrediosAnalisisAgua);
                listaCamposOpcionales.add(columnaPropPrediosAnalisisAgua);
            }
            if (seleccionAnalisisConsumoEnergia != null) {
                listaCamposOpcionales.add(columnaNumPrediosAnalisisEnergia);
                listaCamposOpcionales.add(columnaPropPrediosAnalisisEnergia);
            }
            listaCamposOpcionales.add(columnaNumTotalPredios);
            listaCamposOpcionales.add(columnaPropPrediosDispReciclaje);
            listaCamposOpcionales.add(columnaPropPrediosCondReciclaje);
            if (isAgrupamientoEstablecimiento() || isAgrupamientoSede()) {
                listaCamposOpcionales.add(columnaCumpAreaVerdeNormaLEED);
            } else {
                listaCamposOpcionales.add(columnaNumEstabCumpAreaVerdeNormaLEED);
                listaCamposOpcionales.add(columnaNumEstablecimientos);
            }
            // seleccionado columnas
            selccionCamposOpcionales.add(columnaNumPrediosDispReciclaje);            
            selccionCamposOpcionales.add(columnaNumPrediosCondReciclaje);
            if (seleccionAnalisisConsumoAgua != null) {
                selccionCamposOpcionales.add(columnaNumPrediosAnalisisAgua);
                selccionCamposOpcionales.add(columnaPropPrediosAnalisisAgua);
            }
            if (seleccionAnalisisConsumoEnergia != null) {
                selccionCamposOpcionales.add(columnaNumPrediosAnalisisEnergia);
                selccionCamposOpcionales.add(columnaPropPrediosAnalisisEnergia);
            }
            selccionCamposOpcionales.add(columnaNumTotalPredios);
            selccionCamposOpcionales.add(columnaPropPrediosDispReciclaje);
            selccionCamposOpcionales.add(columnaPropPrediosCondReciclaje);
            if (isAgrupamientoEstablecimiento() || isAgrupamientoSede()) {
                selccionCamposOpcionales.add(columnaCumpAreaVerdeNormaLEED);
            } else {
                selccionCamposOpcionales.add(columnaNumEstabCumpAreaVerdeNormaLEED);
                selccionCamposOpcionales.add(columnaNumEstablecimientos);
            }
        }
    }

    private void inicializarTotalesConsulta() {
        //predio
        totalConsulta.setConsumoAgua(null);
        totalConsulta.setConsumoEnergia(null);
        totalConsulta.setDisponibilidadAreasVerdes(0D);
        // otros niveles
        totalConsulta.setNumPrediosDispReciclaje(0L);
        totalConsulta.setNumPrediosCondReciclaje(0L);
        totalConsulta.setNumPrediosAnalisisAgua(0L);
        totalConsulta.setNumPrediosAnalisisEnergia(0L);
        totalConsulta.setNumTotalPredios(0L);
        totalConsulta.setNumEstabCumpAreaVerdeNormaLEED(0L);
        totalConsulta.setNumTotalEstablecimientos(0L);
    }

    private void totalizarRegistro(Registro registro) {
        //predio
        
        if (registro.getConsumoAgua() != null) {
            if (registro.getConsumoAgua().equalsIgnoreCase("sql.ind.general.noTiene")) {
                registro.setConsumoAgua(Utilidades.obtenerMensaje(registro.getConsumoAgua()));
            } else {
                Double consumoAgua = Double.parseDouble(totalConsulta.getConsumoAgua() != null ? totalConsulta.getConsumoAgua() : "0")
                        + Double.parseDouble(registro.getConsumoAgua());
                totalConsulta.setConsumoAgua(consumoAgua.toString());
            }
        }
        if (registro.getConsumoEnergia() != null) {
            if (registro.getConsumoEnergia().equalsIgnoreCase("sql.ind.general.noTiene")) {
                registro.setConsumoEnergia(Utilidades.obtenerMensaje(registro.getConsumoEnergia()));
            } else {
                Double consumoEnergia = Double.parseDouble(totalConsulta.getConsumoEnergia() != null ? totalConsulta.getConsumoEnergia() : "0")
                        + Double.parseDouble(registro.getConsumoEnergia());
                totalConsulta.setConsumoEnergia(consumoEnergia.toString());
            }
        }
        if (registro.getDisponibilidadAreasVerdes() != null) {
            totalConsulta.setDisponibilidadAreasVerdes(totalConsulta.getDisponibilidadAreasVerdes() + registro.getDisponibilidadAreasVerdes());
        }
        // otros niveles
        if (registro.getNumPrediosDispReciclaje() != null) {
            totalConsulta.setNumPrediosDispReciclaje(totalConsulta.getNumPrediosDispReciclaje() + registro.getNumPrediosDispReciclaje());
        }
        if (registro.getNumPrediosCondReciclaje() != null) {
            totalConsulta.setNumPrediosCondReciclaje(totalConsulta.getNumPrediosCondReciclaje() + registro.getNumPrediosCondReciclaje());
        }
        if (registro.getNumPrediosAnalisisAgua() != null) {
            totalConsulta.setNumPrediosAnalisisAgua(totalConsulta.getNumPrediosAnalisisAgua() + registro.getNumPrediosAnalisisAgua());
        }
        if (registro.getNumPrediosAnalisisEnergia() != null) {
            totalConsulta.setNumPrediosAnalisisEnergia(totalConsulta.getNumPrediosAnalisisEnergia() + registro.getNumPrediosAnalisisEnergia());
        }
        if (registro.getNumTotalPredios() != null) {
            totalConsulta.setNumTotalPredios(totalConsulta.getNumTotalPredios() + registro.getNumTotalPredios());
        }
        if (registro.getNumEstabCumpAreaVerdeNormaLEED() != null) {
            totalConsulta.setNumEstabCumpAreaVerdeNormaLEED(totalConsulta.getNumEstabCumpAreaVerdeNormaLEED() + registro.getNumEstabCumpAreaVerdeNormaLEED());
        }
        if (registro.getNumTotalEstablecimientos() != null) {
            totalConsulta.setNumTotalEstablecimientos(totalConsulta.getNumTotalEstablecimientos() + registro.getNumTotalEstablecimientos());
        }
    }

    public List<Registro> getLista() {
        return lista;
    }

    public Registro getTotalConsulta() {
        return totalConsulta;
    }

    public String getSeleccionDisponibilidadReciclaje() {
        return seleccionDisponibilidadReciclaje;
    }

    public void setSeleccionDisponibilidadReciclaje(String seleccionDisponibilidadReciclaje) {
        this.seleccionDisponibilidadReciclaje = seleccionDisponibilidadReciclaje;
    }

    public CalificacionCondicion getSeleccionCondicionReciclaje() {
        return seleccionCondicionReciclaje;
    }

    public void setSeleccionCondicionReciclaje(CalificacionCondicion seleccionCondicionReciclaje) {
        this.seleccionCondicionReciclaje = seleccionCondicionReciclaje;
    }

    public String getSeleccionAnalisisConsumoAgua() {
        return seleccionAnalisisConsumoAgua;
    }

    public void setSeleccionAnalisisConsumoAgua(String seleccionAnalisisConsumoAgua) {
        this.seleccionAnalisisConsumoAgua = seleccionAnalisisConsumoAgua;
    }

    public String getSeleccionAnalisisConsumoEnergia() {
        return seleccionAnalisisConsumoEnergia;
    }

    public void setSeleccionAnalisisConsumoEnergia(String seleccionAnalisisConsumoEnergia) {
        this.seleccionAnalisisConsumoEnergia = seleccionAnalisisConsumoEnergia;
    }

    public class Registro {

        private String nivel0;
        private String nivel1;
        private String nivel2;
        private String nivel3;
        private String nivel4;
        private String nivel5;
        private String codNivel1;
        private String codNivel2;
        private String codNivel3;
        private String codNivel4;
        private String codNivel5;
        private String codEstablecimiento;
        private String nomEstablecimiento;
        private String codSede;
        private String nomSede;
        private String codPredio;
        private String nomPredio;
        private String dirPredio;
        private String propiedadPredio;
        private String zona;
        private String sector;
        private String clima;
        private String etnias;
        //predio        
        private String disponibilidadReciclaje;
        private String condicionReciclaje;
        private String consumoAgua;
        private String consumoEnergia;
        private String analisisConsumoAgua;
        private String analisisConsumoEnergia;
        private Double disponibilidadAreasVerdes;
        //otros niveles
        private Long numPrediosDispReciclaje;
        private Long numPrediosCondReciclaje;
        private Long numPrediosAnalisisAgua;
        private Long numPrediosAnalisisEnergia;
        private Long numTotalPredios;
        private Double propPrediosDispReciclaje;
        private Double propPrediosCondReciclaje;
        private Double propPrediosAnalisisAgua;
        private Double propPrediosAnalisisEnergia;
        private Double cumpAreaVerdeNormaLEED;
        private Long numEstabCumpAreaVerdeNormaLEED;
        private Long numTotalEstablecimientos;

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
        
         public String getCodNivel1() {
            return codNivel1;
        }

        public void setCodNivel1(String codNivel1) {
            this.codNivel1 = codNivel1;
        }

        public String getCodNivel2() {
            return codNivel2;
        }

        public void setCodNivel2(String codNivel2) {
            this.codNivel2 = codNivel2;
        }

        public String getCodNivel3() {
            return codNivel3;
        }

        public void setCodNivel3(String codNivel3) {
            this.codNivel3 = codNivel3;
        }

        public String getCodNivel4() {
            return codNivel4;
        }

        public void setCodNivel4(String codNivel4) {
            this.codNivel4 = codNivel4;
        }

        public String getCodNivel5() {
            return codNivel5;
        }

        public void setCodNivel5(String codNivel5) {
            this.codNivel5 = codNivel5;
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
            if (propiedadPredio != null) {
                if (propiedadPredio.equalsIgnoreCase("sql.ind.general.si")) {
                    this.propiedadPredio = Utilidades.obtenerMensaje(propiedadPredio);
                } else if (propiedadPredio.equalsIgnoreCase("sql.ind.general.no")){
                this.propiedadPredio = Utilidades.obtenerMensaje(propiedadPredio);
                } else{
                    this.propiedadPredio = Utilidades.obtenerMensaje(propiedadPredio);
                }
            }
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

        public String getEtnias() {
            return etnias;
        }

        public void setEtnias(String etnias) {
            this.etnias = etnias;
        }

        public String getDisponibilidadReciclaje() {
            return disponibilidadReciclaje;
        }

        public void setDisponibilidadReciclaje(String disponibilidadReciclaje) {
            this.disponibilidadReciclaje = disponibilidadReciclaje;
        }

        public String getCondicionReciclaje() {
            return condicionReciclaje;
        }

        public void setCondicionReciclaje(String condicionReciclaje) {
            this.condicionReciclaje = condicionReciclaje;
        }

        public String getConsumoAgua() {
            return consumoAgua;
        }

        public void setConsumoAgua(String consumoAgua) {
            this.consumoAgua = consumoAgua;
        }

        public String getConsumoEnergia() {
            return consumoEnergia;
        }

        public void setConsumoEnergia(String consumoEnergia) {
            this.consumoEnergia = consumoEnergia;
        }

        public String getAnalisisConsumoAgua() {
            return analisisConsumoAgua;
        }

        public void setAnalisisConsumoAgua(String analisisConsumoAgua) {
            this.analisisConsumoAgua = analisisConsumoAgua;
        }

        public String getAnalisisConsumoEnergia() {
            return analisisConsumoEnergia;
        }

        public void setAnalisisConsumoEnergia(String analisisConsumoEnergia) {
            this.analisisConsumoEnergia = analisisConsumoEnergia;
        }

        public Double getDisponibilidadAreasVerdes() {
            return disponibilidadAreasVerdes;
        }

        public void setDisponibilidadAreasVerdes(Double disponibilidadAreasVerdes) {
            this.disponibilidadAreasVerdes = disponibilidadAreasVerdes;
        }

        public Long getNumPrediosDispReciclaje() {
            return numPrediosDispReciclaje;
        }

        public void setNumPrediosDispReciclaje(Long numPrediosDispReciclaje) {
            this.numPrediosDispReciclaje = numPrediosDispReciclaje;
        }

        public Long getNumPrediosCondReciclaje() {
            return numPrediosCondReciclaje;
        }

        public void setNumPrediosCondReciclaje(Long numPrediosCondReciclaje) {
            this.numPrediosCondReciclaje = numPrediosCondReciclaje;
        }

        public Long getNumPrediosAnalisisAgua() {
            return numPrediosAnalisisAgua;
        }

        public void setNumPrediosAnalisisAgua(Long numPrediosAnalisisAgua) {
            this.numPrediosAnalisisAgua = numPrediosAnalisisAgua;
        }

        public Long getNumPrediosAnalisisEnergia() {
            return numPrediosAnalisisEnergia;
        }

        public void setNumPrediosAnalisisEnergia(Long numPrediosAnalisisEnergia) {
            this.numPrediosAnalisisEnergia = numPrediosAnalisisEnergia;
        }

        public Long getNumTotalPredios() {
            return numTotalPredios;
        }

        public void setNumTotalPredios(Long numTotalPredios) {
            this.numTotalPredios = numTotalPredios;
        }

        public Double getPropPrediosDispReciclaje() {
            return propPrediosDispReciclaje;
        }

        public void setPropPrediosDispReciclaje(Double propPrediosDispReciclaje) {
            this.propPrediosDispReciclaje = propPrediosDispReciclaje;
        }

        public Double getPropPrediosCondReciclaje() {
            return propPrediosCondReciclaje;
        }

        public void setPropPrediosCondReciclaje(Double propPrediosCondReciclaje) {
            this.propPrediosCondReciclaje = propPrediosCondReciclaje;
        }

        public Double getPropPrediosAnalisisAgua() {
            return propPrediosAnalisisAgua;
        }

        public void setPropPrediosAnalisisAgua(Double propPrediosAnalisisAgua) {
            this.propPrediosAnalisisAgua = propPrediosAnalisisAgua;
        }

        public Double getPropPrediosAnalisisEnergia() {
            return propPrediosAnalisisEnergia;
        }

        public void setPropPrediosAnalisisEnergia(Double propPrediosAnalisisEnergia) {
            this.propPrediosAnalisisEnergia = propPrediosAnalisisEnergia;
        }

        public Double getCumpAreaVerdeNormaLEED() {
            return cumpAreaVerdeNormaLEED;
        }

        public void setCumpAreaVerdeNormaLEED(Double cumpAreaVerdeNormaLEED) {
            this.cumpAreaVerdeNormaLEED = cumpAreaVerdeNormaLEED;
        }

        public Long getNumTotalEstablecimientos() {
            return numTotalEstablecimientos;
        }

        public void setNumTotalEstablecimientos(Long numTotalEstablecimientos) {
            this.numTotalEstablecimientos = numTotalEstablecimientos;
        }

        public Long getNumEstabCumpAreaVerdeNormaLEED() {
            return numEstabCumpAreaVerdeNormaLEED;
        }

        public void setNumEstabCumpAreaVerdeNormaLEED(Long numEstabCumpAreaVerdeNormaLEED) {
            this.numEstabCumpAreaVerdeNormaLEED = numEstabCumpAreaVerdeNormaLEED;
        }
    }

    public String getColumnaDisponibilidadReciclaje() {
        return columnaDisponibilidadReciclaje;
    }

    public String getColumnaCondicionReciclaje() {
        return columnaCondicionReciclaje;
    }

    public String getColumnaConsumoAgua() {
        return columnaConsumoAgua;
    }

    public String getColumnaConsumoEnergia() {
        return columnaConsumoEnergia;
    }

    public String getColumnaAnalisisConsumoAgua() {
        return columnaAnalisisConsumoAgua;
    }

    public String getColumnaAnalisisConsumoEnergia() {
        return columnaAnalisisConsumoEnergia;
    }

    public String getColumnaDisponibilidadAreasVerdes() {
        return columnaDisponibilidadAreasVerdes;
    }

    public String getColumnaNumPrediosDispReciclaje() {
        return columnaNumPrediosDispReciclaje;
    }

    public String getColumnaNumPrediosCondReciclaje() {
        return columnaNumPrediosCondReciclaje;
    }

    public String getColumnaNumPrediosAnalisisAgua() {
        return columnaNumPrediosAnalisisAgua;
    }

    public String getColumnaNumPrediosAnalisisEnergia() {
        return columnaNumPrediosAnalisisEnergia;
    }

    public String getColumnaNumTotalPredios() {
        return columnaNumTotalPredios;
    }

    public String getColumnaPropPrediosDispReciclaje() {
        return columnaPropPrediosDispReciclaje;
    }

    public String getColumnaPropPrediosCondReciclaje() {
        return columnaPropPrediosCondReciclaje;
    }

    public String getColumnaPropPrediosAnalisisAgua() {
        return columnaPropPrediosAnalisisAgua;
    }

    public String getColumnaPropPrediosAnalisisEnergia() {
        return columnaPropPrediosAnalisisEnergia;
    }

    public String getColumnaCumpAreaVerdeNormaLEED() {
        return columnaCumpAreaVerdeNormaLEED;
    }

    public String getColumnaNumEstabCumpAreaVerdeNormaLEED() {
        return columnaNumEstabCumpAreaVerdeNormaLEED;
    }

    public String getColumnaNumEstablecimientos() {
        return columnaNumEstablecimientos;
    }

    public ConsultaAmbito getConsultaAmbito() {
        return consultaAmbito;
    }

    public void setConsultaAmbito(ConsultaAmbito consultaAmbito) {
        this.consultaAmbito = consultaAmbito;
    }
}
