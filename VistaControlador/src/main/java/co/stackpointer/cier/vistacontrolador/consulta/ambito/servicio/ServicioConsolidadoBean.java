/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.consulta.ambito.servicio;

import co.stackpointer.cier.vistacontrolador.consulta.ambito.*;
import co.stackpointer.cier.modelo.filtro.ambito.FiltroAmbitoServicioConsolidado;
import co.stackpointer.cier.modelo.tipo.CalificacionCondicion;
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
@ManagedBean(name = "servicioConsBean")
@ViewScoped
public class ServicioConsolidadoBean extends ConsultaAmbito {

    @ManagedProperty(value = "#{consultaAmbito}")
    private ConsultaAmbito consultaAmbito;
    private Registro totalConsulta = new Registro();
    private List<Registro> lista;
    private FiltroAmbitoServicioConsolidado filtro = new FiltroAmbitoServicioConsolidado();
    private CalificacionCondicion seleccionCondicionAgua;
    private CalificacionCondicion seleccionCondicionEnergia;
    private CalificacionCondicion seleccionCondicionGas;
    private CalificacionCondicion seleccionCondicionBasuras;
    private CalificacionCondicion seleccionCondicionReciclaje;
    private CalificacionCondicion seleccionCondicionAlcantarillado;
    private CalificacionCondicion seleccionCondicionPluvial;
    private CalificacionCondicion seleccionCondicionInternet;
    private CalificacionCondicion seleccionCondicionTelefono;
    //predio
    private final String columnaDispAgua = Utilidades.obtenerMensaje("consultas.ambito.servicio.consolidado.columna.disp.agua");
    private final String columnaCondAgua = Utilidades.obtenerMensaje("consultas.ambito.servicio.consolidado.columna.cond.agua");
    private final String columnaDispEnergia = Utilidades.obtenerMensaje("consultas.ambito.servicio.consolidado.columna.disp.energia");
    private final String columnaCondEnergia = Utilidades.obtenerMensaje("consultas.ambito.servicio.consolidado.columna.cond.energia");
    private final String columnaDispGas = Utilidades.obtenerMensaje("consultas.ambito.servicio.consolidado.columna.disp.gas");
    private final String columnaCondGas = Utilidades.obtenerMensaje("consultas.ambito.servicio.consolidado.columna.cond.gas");
    private final String columnaDispBasuras = Utilidades.obtenerMensaje("consultas.ambito.servicio.consolidado.columna.disp.basuras");
    private final String columnaCondBasuras = Utilidades.obtenerMensaje("consultas.ambito.servicio.consolidado.columna.cond.basuras");
    private final String columnaDispReciclaje = Utilidades.obtenerMensaje("consultas.ambito.servicio.consolidado.columna.disp.reciclaje");
    private final String columnaCondReciclaje = Utilidades.obtenerMensaje("consultas.ambito.servicio.consolidado.columna.cond.reciclaje");
    private final String columnaDispAlcantarillado = Utilidades.obtenerMensaje("consultas.ambito.servicio.consolidado.columna.disp.alcantarillado");
    private final String columnaCondAlcantarillado = Utilidades.obtenerMensaje("consultas.ambito.servicio.consolidado.columna.cond.alcantarillado");
    private final String columnaDispPluvial = Utilidades.obtenerMensaje("consultas.ambito.servicio.consolidado.columna.disp.pluvial");
    private final String columnaCondPluvial = Utilidades.obtenerMensaje("consultas.ambito.servicio.consolidado.columna.cond.pluvial");
    private final String columnaDispInternet = Utilidades.obtenerMensaje("consultas.ambito.servicio.consolidado.columna.disp.internet");
    private final String columnaCondInternet = Utilidades.obtenerMensaje("consultas.ambito.servicio.consolidado.columna.cond.internet");
    private final String columnaDispTelefono = Utilidades.obtenerMensaje("consultas.ambito.servicio.consolidado.columna.disp.telefono");
    private final String columnaCondTelefono = Utilidades.obtenerMensaje("consultas.ambito.servicio.consolidado.columna.cond.telefono");
// otros niveles
    private final String columnaNumPredConServicioAgua = Utilidades.obtenerMensaje("consultas.ambito.servicio.consolidado.columna.num.pred.con.servicio.agua");
    private final String columnaNumPredSegunCondServicioAgua = Utilidades.obtenerMensaje("consultas.ambito.servicio.consolidado.columna.num.pred.segun.cond.servicio.agua");
    private final String columnaNumPredConServicioEnergia = Utilidades.obtenerMensaje("consultas.ambito.servicio.consolidado.columna.num.pred.con.servicio.energia");
    private final String columnaNumPredSegunCondServicioEnergia = Utilidades.obtenerMensaje("consultas.ambito.servicio.consolidado.columna.num.pred.segun.cond.servicio.energia");
    private final String columnaNumPredConServicioGas = Utilidades.obtenerMensaje("consultas.ambito.servicio.consolidado.columna.num.pred.con.servicio.gas");
    private final String columnaNumPredSegunCondServicioGas = Utilidades.obtenerMensaje("consultas.ambito.servicio.consolidado.columna.num.pred.segun.cond.servicio.gas");
    private final String columnaNumPredConServicioBasuras = Utilidades.obtenerMensaje("consultas.ambito.servicio.consolidado.columna.num.pred.con.servicio.basuras");
    private final String columnaNumPredSegunCondServicioBasuras = Utilidades.obtenerMensaje("consultas.ambito.servicio.consolidado.columna.num.pred.segun.cond.servicio.basuras");
    private final String columnaNumPredConServicioReciclaje = Utilidades.obtenerMensaje("consultas.ambito.servicio.consolidado.columna.num.pred.con.servicio.reciclaje");
    private final String columnaNumPredSegunCondServicioReciclaje = Utilidades.obtenerMensaje("consultas.ambito.servicio.consolidado.columna.num.pred.segun.cond.servicio.reciclaje");
    private final String columnaNumPredConServicioAlcantarillado = Utilidades.obtenerMensaje("consultas.ambito.servicio.consolidado.columna.num.pred.con.servicio.alcantarillado");
    private final String columnaNumPredSegunCondServicioAlcantarillado = Utilidades.obtenerMensaje("consultas.ambito.servicio.consolidado.columna.num.pred.segun.cond.servicio.alcantarillado");
    private final String columnaNumPredConServicioPluvial = Utilidades.obtenerMensaje("consultas.ambito.servicio.consolidado.columna.num.pred.con.servicio.pluvial");
    private final String columnaNumPredSegunCondServicioPluvial = Utilidades.obtenerMensaje("consultas.ambito.servicio.consolidado.columna.num.pred.segun.cond.servicio.pluvial");
    private final String columnaNumPredConServicioInternet = Utilidades.obtenerMensaje("consultas.ambito.servicio.consolidado.columna.num.pred.con.servicio.internet");
    private final String columnaNumPredSegunCondServicioInternet = Utilidades.obtenerMensaje("consultas.ambito.servicio.consolidado.columna.num.pred.segun.cond.servicio.internet");
    private final String columnaNumPredConServicioTelefono = Utilidades.obtenerMensaje("consultas.ambito.servicio.consolidado.columna.num.pred.con.servicio.telefono");
    private final String columnaNumPredSegunCondServicioTelefono = Utilidades.obtenerMensaje("consultas.ambito.servicio.consolidado.columna.num.pred.segun.cond.servicio.telefono");

    @PostConstruct
    public void inicializar() {
        //super.init();
        this.lista = new ArrayList<Registro>();
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
            // condiciones
            filtro.setCondicionAgua(seleccionCondicionAgua != null ? seleccionCondicionAgua.getValor() : null);
            filtro.setCondicionEnergia(seleccionCondicionEnergia != null ? seleccionCondicionEnergia.getValor() : null);
            filtro.setCondicionGas(seleccionCondicionGas != null ? seleccionCondicionGas.getValor() : null);
            filtro.setCondicionBasuras(seleccionCondicionBasuras != null ? seleccionCondicionBasuras.getValor() : null);
            filtro.setCondicionReciclaje(seleccionCondicionReciclaje != null ? seleccionCondicionReciclaje.getValor() : null);
            filtro.setCondicionAlcantarillado(seleccionCondicionAlcantarillado != null ? seleccionCondicionAlcantarillado.getValor() : null);
            filtro.setCondicionPluvial(seleccionCondicionPluvial != null ? seleccionCondicionPluvial.getValor() : null);
            filtro.setCondicionInternet(seleccionCondicionInternet != null ? seleccionCondicionInternet.getValor() : null);
            filtro.setCondicionTelefono(seleccionCondicionTelefono != null ? seleccionCondicionTelefono.getValor() : null);

            List<Object[]> resultados = facAmbito.consultarServicioConsolidado(filtro);
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
                    registro.setZona(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]), getUsuarioSesion().getUsuario().getIdioma().getId()));
                    registro.setSector(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]), getUsuarioSesion().getUsuario().getIdioma().getId()));
                    registro.setClima(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]), getUsuarioSesion().getUsuario().getIdioma().getId()));
                    registro.setEtnias(verificarEtnias(UtilConsulta.stringValueOf(fila[i++])));
                    //indicadores disponibilidad
                    registro.setDispAgua(Utilidades.obtenerMensaje(UtilConsulta.stringValueOf(fila[i++])));
                    registro.setDispEnergia(Utilidades.obtenerMensaje(UtilConsulta.stringValueOf(fila[i++])));
                    registro.setDispGas(Utilidades.obtenerMensaje(UtilConsulta.stringValueOf(fila[i++])));
                    registro.setDispBasuras(Utilidades.obtenerMensaje(UtilConsulta.stringValueOf(fila[i++])));
                    registro.setDispReciclaje(Utilidades.obtenerMensaje(UtilConsulta.stringValueOf(fila[i++])));
                    registro.setDispAlcantarillado(Utilidades.obtenerMensaje(UtilConsulta.stringValueOf(fila[i++])));
                    registro.setDispPluvial(Utilidades.obtenerMensaje(UtilConsulta.stringValueOf(fila[i++])));
                    registro.setDispInternet(Utilidades.obtenerMensaje(UtilConsulta.stringValueOf(fila[i++])));
                    registro.setDispTelefono(Utilidades.obtenerMensaje(UtilConsulta.stringValueOf(fila[i++])));
                    //indicadores condiccion                    
                    registro.setCondAgua(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCondEnergia(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCondGas(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCondBasuras(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCondReciclaje(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCondAlcantarillado(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCondPluvial(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCondInternet(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setCondTelefono(UtilConsulta.stringValueOf(fila[i++]));
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
                    //indicadores disponibilidad
                    registro.setNumPredConServicioAgua(UtilConsulta.longValueOf(fila[i++]));
                    registro.setNumPredConServicioEnergia(UtilConsulta.longValueOf(fila[i++]));
                    registro.setNumPredConServicioGas(UtilConsulta.longValueOf(fila[i++]));
                    registro.setNumPredConServicioBasuras(UtilConsulta.longValueOf(fila[i++]));
                    registro.setNumPredConServicioReciclaje(UtilConsulta.longValueOf(fila[i++]));
                    registro.setNumPredConServicioAlcantarillado(UtilConsulta.longValueOf(fila[i++]));
                    registro.setNumPredConServicioPluvial(UtilConsulta.longValueOf(fila[i++]));
                    registro.setNumPredConServicioInternet(UtilConsulta.longValueOf(fila[i++]));
                    registro.setNumPredConServicioTelefono(UtilConsulta.longValueOf(fila[i++]));
                    //indicadores condiccion
                    if (filtro.getCondicionAgua() != null) {
                        registro.setNumPredSegunCondServicioAgua(UtilConsulta.longValueOf(fila[i++]));
                    }
                    if (filtro.getCondicionEnergia() != null) {
                        registro.setNumPredSegunCondServicioEnergia(UtilConsulta.longValueOf(fila[i++]));
                    }
                    if (filtro.getCondicionGas() != null) {
                        registro.setNumPredSegunCondServicioGas(UtilConsulta.longValueOf(fila[i++]));
                    }
                    if (filtro.getCondicionBasuras() != null) {
                        registro.setNumPredSegunCondServicioBasuras(UtilConsulta.longValueOf(fila[i++]));
                    }
                    if (filtro.getCondicionReciclaje() != null) {
                        registro.setNumPredSegunCondServicioReciclaje(UtilConsulta.longValueOf(fila[i++]));
                    }
                    if (filtro.getCondicionAlcantarillado() != null) {
                        registro.setNumPredSegunCondServicioAlcantarillado(UtilConsulta.longValueOf(fila[i++]));
                    }
                    if (filtro.getCondicionPluvial() != null) {
                        registro.setNumPredSegunCondServicioPluvial(UtilConsulta.longValueOf(fila[i++]));
                    }
                    if (filtro.getCondicionInternet() != null) {
                        registro.setNumPredSegunCondServicioInternet(UtilConsulta.longValueOf(fila[i++]));
                    }
                    if (filtro.getCondicionTelefono() != null) {
                        registro.setNumPredSegunCondServicioTelefono(UtilConsulta.longValueOf(fila[i++]));
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

    public CalificacionCondicion getSeleccionCondicionAgua() {
        return seleccionCondicionAgua;
    }

    public void setSeleccionCondicionAgua(CalificacionCondicion seleccionCondicionAgua) {
        this.seleccionCondicionAgua = seleccionCondicionAgua;
    }

    public CalificacionCondicion getSeleccionCondicionEnergia() {
        return seleccionCondicionEnergia;
    }

    public void setSeleccionCondicionEnergia(CalificacionCondicion seleccionCondicionEnergia) {
        this.seleccionCondicionEnergia = seleccionCondicionEnergia;
    }

    public CalificacionCondicion getSeleccionCondicionGas() {
        return seleccionCondicionGas;
    }

    public void setSeleccionCondicionGas(CalificacionCondicion seleccionCondicionGas) {
        this.seleccionCondicionGas = seleccionCondicionGas;
    }

    public CalificacionCondicion getSeleccionCondicionBasuras() {
        return seleccionCondicionBasuras;
    }

    public void setSeleccionCondicionBasuras(CalificacionCondicion seleccionCondicionBasuras) {
        this.seleccionCondicionBasuras = seleccionCondicionBasuras;
    }

    public CalificacionCondicion getSeleccionCondicionReciclaje() {
        return seleccionCondicionReciclaje;
    }

    public void setSeleccionCondicionReciclaje(CalificacionCondicion seleccionCondicionReciclaje) {
        this.seleccionCondicionReciclaje = seleccionCondicionReciclaje;
    }

    public CalificacionCondicion getSeleccionCondicionAlcantarillado() {
        return seleccionCondicionAlcantarillado;
    }

    public void setSeleccionCondicionAlcantarillado(CalificacionCondicion seleccionCondicionAlcantarillado) {
        this.seleccionCondicionAlcantarillado = seleccionCondicionAlcantarillado;
    }

    public CalificacionCondicion getSeleccionCondicionPluvial() {
        return seleccionCondicionPluvial;
    }

    public void setSeleccionCondicionPluvial(CalificacionCondicion seleccionCondicionPluvial) {
        this.seleccionCondicionPluvial = seleccionCondicionPluvial;
    }

    public CalificacionCondicion getSeleccionCondicionInternet() {
        return seleccionCondicionInternet;
    }

    public void setSeleccionCondicionInternet(CalificacionCondicion seleccionCondicionInternet) {
        this.seleccionCondicionInternet = seleccionCondicionInternet;
    }

    public CalificacionCondicion getSeleccionCondicionTelefono() {
        return seleccionCondicionTelefono;
    }

    public void setSeleccionCondicionTelefono(CalificacionCondicion seleccionCondicionTelefono) {
        this.seleccionCondicionTelefono = seleccionCondicionTelefono;
    }

    private void resetearFiltrosCondicion() {
        this.seleccionCondicionAgua = null;
        this.seleccionCondicionEnergia = null;
        this.seleccionCondicionGas = null;
        this.seleccionCondicionBasuras = null;
        this.seleccionCondicionReciclaje = null;
        this.seleccionCondicionAlcantarillado = null;
        this.seleccionCondicionPluvial = null;
        this.seleccionCondicionInternet = null;
        this.seleccionCondicionTelefono = null;
    }

    @Override
    public void resetearFiltros() {
        consultaAmbito.resetearNiveles();
        this.resetearListasEstSedesPredios();
        this.seleccionSector = null;
        this.seleccionZona = null;
        this.resetearFiltrosCondicion();
        // condiciones
        consultaAmbito.cargarNivel1();
        this.resetearConsulta();
    }

    /**
     * Cuando se cambie el filtro de periodo este reseteara todos los campos
     * parametricos de la consulta a excepcion de los que sonparte de la DPA
     */
    public void resetearFiltrosPeriodo() {
        //consultaAmbito.resetearNiveles();
        this.resetearListasEstSedesPredios();
        this.seleccionSector = null;
        this.seleccionZona = null;
        this.resetearFiltrosCondicion();
        // condiciones
        consultaAmbito.cargarNivel1();
        this.resetearConsulta();
    }

    @Override
    public void cambioAgrupamiento() {
        this.resetearConsulta();
    }

    public void cambioFiltroCondicion() {
        this.resetearConsulta();
    }

    private void resetearConsulta() {
        if (isAgrupamientoPredio()) {
            this.resetearFiltrosCondicion();
        }
        // columnas
        this.cargarCamposOpcionales();
        // resultados
        this.lista.clear();
        this.inicializarTotalesConsulta();
    }

    public void cargarCamposOpcionales() {
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
        if (isMostrarColEtnias()) {
            listaCamposOpcionales.add(columnaEtnias);
        }
        if (isAgrupamientoPredio()) {
            listaCamposOpcionales.add(columnaDispAgua);
            listaCamposOpcionales.add(columnaCondAgua);
            listaCamposOpcionales.add(columnaDispEnergia);
            listaCamposOpcionales.add(columnaCondEnergia);
            listaCamposOpcionales.add(columnaDispGas);
            listaCamposOpcionales.add(columnaCondGas);
            listaCamposOpcionales.add(columnaDispBasuras);
            listaCamposOpcionales.add(columnaCondBasuras);
            listaCamposOpcionales.add(columnaDispReciclaje);
            listaCamposOpcionales.add(columnaCondReciclaje);
            listaCamposOpcionales.add(columnaDispAlcantarillado);
            listaCamposOpcionales.add(columnaCondAlcantarillado);
            listaCamposOpcionales.add(columnaDispPluvial);
            listaCamposOpcionales.add(columnaCondPluvial);
            listaCamposOpcionales.add(columnaDispInternet);
            listaCamposOpcionales.add(columnaCondInternet);
            listaCamposOpcionales.add(columnaDispTelefono);
            listaCamposOpcionales.add(columnaCondTelefono);
            // campos por defecto            
            selccionCamposOpcionales.add(columnaDispAgua);
            selccionCamposOpcionales.add(columnaCondAgua);
            selccionCamposOpcionales.add(columnaDispEnergia);
            selccionCamposOpcionales.add(columnaCondEnergia);
            selccionCamposOpcionales.add(columnaDispAlcantarillado);
            selccionCamposOpcionales.add(columnaCondAlcantarillado);
        } else {
            listaCamposOpcionales.add(columnaNumPredConServicioAgua);
            listaCamposOpcionales.add(columnaNumPredConServicioEnergia);
            listaCamposOpcionales.add(columnaNumPredConServicioGas);
            listaCamposOpcionales.add(columnaNumPredConServicioBasuras);
            listaCamposOpcionales.add(columnaNumPredConServicioReciclaje);
            listaCamposOpcionales.add(columnaNumPredConServicioAlcantarillado);
            listaCamposOpcionales.add(columnaNumPredConServicioPluvial);
            listaCamposOpcionales.add(columnaNumPredConServicioInternet);
            listaCamposOpcionales.add(columnaNumPredConServicioTelefono);
            // condicciones
            if (seleccionCondicionAgua != null) {
                listaCamposOpcionales.add(columnaNumPredSegunCondServicioAgua);
            }
            if (seleccionCondicionEnergia != null) {
                listaCamposOpcionales.add(columnaNumPredSegunCondServicioEnergia);
            }
            if (seleccionCondicionGas != null) {
                listaCamposOpcionales.add(columnaNumPredSegunCondServicioGas);
            }
            if (seleccionCondicionBasuras != null) {
                listaCamposOpcionales.add(columnaNumPredSegunCondServicioBasuras);
            }
            if (seleccionCondicionReciclaje != null) {
                listaCamposOpcionales.add(columnaNumPredSegunCondServicioReciclaje);
            }
            if (seleccionCondicionAlcantarillado != null) {
                listaCamposOpcionales.add(columnaNumPredSegunCondServicioAlcantarillado);
            }
            if (seleccionCondicionPluvial != null) {
                listaCamposOpcionales.add(columnaNumPredSegunCondServicioPluvial);
            }
            if (seleccionCondicionInternet != null) {
                listaCamposOpcionales.add(columnaNumPredSegunCondServicioInternet);
            }
            if (seleccionCondicionTelefono != null) {
                listaCamposOpcionales.add(columnaNumPredSegunCondServicioTelefono);
            }
            // campos por defecto
            selccionCamposOpcionales.add(columnaNumPredConServicioAgua);
            selccionCamposOpcionales.add(columnaNumPredConServicioEnergia);
            selccionCamposOpcionales.add(columnaNumPredConServicioAlcantarillado);
            // condicciones
            if (seleccionCondicionAgua != null) {
                selccionCamposOpcionales.add(columnaNumPredSegunCondServicioAgua);
            }
            if (seleccionCondicionEnergia != null) {
                selccionCamposOpcionales.add(columnaNumPredSegunCondServicioEnergia);
            }
            if (seleccionCondicionGas != null) {
                selccionCamposOpcionales.add(columnaNumPredSegunCondServicioGas);
            }
            if (seleccionCondicionBasuras != null) {
                selccionCamposOpcionales.add(columnaNumPredSegunCondServicioBasuras);
            }
            if (seleccionCondicionReciclaje != null) {
                selccionCamposOpcionales.add(columnaNumPredSegunCondServicioReciclaje);
            }
            if (seleccionCondicionAlcantarillado != null) {
                selccionCamposOpcionales.add(columnaNumPredSegunCondServicioAlcantarillado);
            }
            if (seleccionCondicionPluvial != null) {
                selccionCamposOpcionales.add(columnaNumPredSegunCondServicioPluvial);
            }
            if (seleccionCondicionInternet != null) {
                selccionCamposOpcionales.add(columnaNumPredSegunCondServicioInternet);
            }
            if (seleccionCondicionTelefono != null) {
                selccionCamposOpcionales.add(columnaNumPredSegunCondServicioTelefono);
            }
        }
    }

    private void inicializarTotalesConsulta() {
        totalConsulta.setNumPredConServicioAgua(0L);
        totalConsulta.setNumPredSegunCondServicioAgua(0L);
        totalConsulta.setNumPredConServicioEnergia(0L);
        totalConsulta.setNumPredSegunCondServicioEnergia(0L);
        totalConsulta.setNumPredConServicioGas(0L);
        totalConsulta.setNumPredSegunCondServicioGas(0L);
        totalConsulta.setNumPredConServicioBasuras(0L);
        totalConsulta.setNumPredSegunCondServicioBasuras(0L);
        totalConsulta.setNumPredConServicioReciclaje(0L);
        totalConsulta.setNumPredSegunCondServicioReciclaje(0L);
        totalConsulta.setNumPredConServicioAlcantarillado(0L);
        totalConsulta.setNumPredSegunCondServicioAlcantarillado(0L);
        totalConsulta.setNumPredConServicioPluvial(0L);
        totalConsulta.setNumPredSegunCondServicioPluvial(0L);
        totalConsulta.setNumPredConServicioInternet(0L);
        totalConsulta.setNumPredSegunCondServicioInternet(0L);
        totalConsulta.setNumPredConServicioTelefono(0L);
        totalConsulta.setNumPredSegunCondServicioTelefono(0L);
    }

    private void totalizarRegistro(Registro registro) {
        //agua
        if (registro.getNumPredConServicioAgua() != null) {
            totalConsulta.setNumPredConServicioAgua(totalConsulta.getNumPredConServicioAgua() + registro.getNumPredConServicioAgua());
        }
        if (registro.getNumPredSegunCondServicioAgua() != null) {
            totalConsulta.setNumPredSegunCondServicioAgua(totalConsulta.getNumPredSegunCondServicioAgua() + registro.getNumPredSegunCondServicioAgua());
        }
        //energia
        if (registro.getNumPredConServicioEnergia() != null) {
            totalConsulta.setNumPredConServicioEnergia(totalConsulta.getNumPredConServicioEnergia() + registro.getNumPredConServicioEnergia());
        }
        if (registro.getNumPredSegunCondServicioEnergia() != null) {
            totalConsulta.setNumPredSegunCondServicioEnergia(totalConsulta.getNumPredSegunCondServicioEnergia() + registro.getNumPredSegunCondServicioEnergia());
        }
        //gas
        if (registro.getNumPredConServicioGas() != null) {
            totalConsulta.setNumPredConServicioGas(totalConsulta.getNumPredConServicioGas() + registro.getNumPredConServicioGas());
        }
        if (registro.getNumPredSegunCondServicioGas() != null) {
            totalConsulta.setNumPredSegunCondServicioGas(totalConsulta.getNumPredSegunCondServicioGas() + registro.getNumPredSegunCondServicioGas());
        }
        //basura
        if (registro.getNumPredConServicioBasuras() != null) {
            totalConsulta.setNumPredConServicioBasuras(totalConsulta.getNumPredConServicioBasuras() + registro.getNumPredConServicioBasuras());
        }
        if (registro.getNumPredSegunCondServicioBasuras() != null) {
            totalConsulta.setNumPredSegunCondServicioBasuras(totalConsulta.getNumPredSegunCondServicioBasuras() + registro.getNumPredSegunCondServicioBasuras());
        }
        //reciclaje
        if (registro.getNumPredConServicioReciclaje() != null) {
            totalConsulta.setNumPredConServicioReciclaje(totalConsulta.getNumPredConServicioReciclaje() + registro.getNumPredConServicioReciclaje());
        }
        if (registro.getNumPredSegunCondServicioReciclaje() != null) {
            totalConsulta.setNumPredSegunCondServicioReciclaje(totalConsulta.getNumPredSegunCondServicioReciclaje() + registro.getNumPredSegunCondServicioReciclaje());
        }
        //alcantarillado
        if (registro.getNumPredConServicioAlcantarillado() != null) {
            totalConsulta.setNumPredConServicioAlcantarillado(totalConsulta.getNumPredConServicioAlcantarillado() + registro.getNumPredConServicioAlcantarillado());
        }
        if (registro.getNumPredSegunCondServicioAlcantarillado() != null) {
            totalConsulta.setNumPredSegunCondServicioAlcantarillado(totalConsulta.getNumPredSegunCondServicioAlcantarillado() + registro.getNumPredSegunCondServicioAlcantarillado());
        }
        // pluvial
        if (registro.getNumPredConServicioPluvial() != null) {
            totalConsulta.setNumPredConServicioPluvial(totalConsulta.getNumPredConServicioPluvial() + registro.getNumPredConServicioPluvial());
        }
        if (registro.getNumPredSegunCondServicioPluvial() != null) {
            totalConsulta.setNumPredSegunCondServicioPluvial(totalConsulta.getNumPredSegunCondServicioPluvial() + registro.getNumPredSegunCondServicioPluvial());
        }
        //internet
        if (registro.getNumPredConServicioInternet() != null) {
            totalConsulta.setNumPredConServicioInternet(totalConsulta.getNumPredConServicioInternet() + registro.getNumPredConServicioInternet());
        }
        if (registro.getNumPredSegunCondServicioInternet() != null) {
            totalConsulta.setNumPredSegunCondServicioInternet(totalConsulta.getNumPredSegunCondServicioInternet() + registro.getNumPredSegunCondServicioInternet());
        }
        //telefono
        if (registro.getNumPredConServicioTelefono() != null) {
            totalConsulta.setNumPredConServicioTelefono(totalConsulta.getNumPredConServicioTelefono() + registro.getNumPredConServicioTelefono());
        }
        if (registro.getNumPredSegunCondServicioTelefono() != null) {
            totalConsulta.setNumPredSegunCondServicioTelefono(totalConsulta.getNumPredSegunCondServicioTelefono() + registro.getNumPredSegunCondServicioTelefono());
        }
    }

    public List<Registro> getLista() {
        return lista;
    }

    public Registro getTotalConsulta() {
        return totalConsulta;
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
        private String dispAgua;
        private String condAgua;
        private String dispEnergia;
        private String condEnergia;
        private String dispGas;
        private String condGas;
        private String dispBasuras;
        private String condBasuras;
        private String dispReciclaje;
        private String condReciclaje;
        private String dispAlcantarillado;
        private String condAlcantarillado;
        private String dispPluvial;
        private String condPluvial;
        private String dispInternet;
        private String condInternet;
        private String dispTelefono;
        private String condTelefono;
        // otros
        private Long numPredConServicioAgua;
        private Long numPredSegunCondServicioAgua;
        private Long numPredConServicioEnergia;
        private Long numPredSegunCondServicioEnergia;
        private Long numPredConServicioGas;
        private Long numPredSegunCondServicioGas;
        private Long numPredConServicioBasuras;
        private Long numPredSegunCondServicioBasuras;
        private Long numPredConServicioReciclaje;
        private Long numPredSegunCondServicioReciclaje;
        private Long numPredConServicioAlcantarillado;
        private Long numPredSegunCondServicioAlcantarillado;
        private Long numPredConServicioPluvial;
        private Long numPredSegunCondServicioPluvial;
        private Long numPredConServicioInternet;
        private Long numPredSegunCondServicioInternet;
        private Long numPredConServicioTelefono;
        private Long numPredSegunCondServicioTelefono;

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

        public String getDispAgua() {
            return dispAgua;
        }

        public void setDispAgua(String dispAgua) {
            this.dispAgua = dispAgua;
        }

        public String getCondAgua() {
            return condAgua;
        }

        public void setCondAgua(String condAgua) {
            this.condAgua = condAgua;
        }

        public String getDispEnergia() {
            return dispEnergia;
        }

        public void setDispEnergia(String dispEnergia) {
            this.dispEnergia = dispEnergia;
        }

        public String getCondEnergia() {
            return condEnergia;
        }

        public void setCondEnergia(String condEnergia) {
            this.condEnergia = condEnergia;
        }

        public String getDispGas() {
            return dispGas;
        }

        public void setDispGas(String dispGas) {
            this.dispGas = dispGas;
        }

        public String getCondGas() {
            return condGas;
        }

        public void setCondGas(String condGas) {
            this.condGas = condGas;
        }

        public String getDispBasuras() {
            return dispBasuras;
        }

        public void setDispBasuras(String dispBasuras) {
            this.dispBasuras = dispBasuras;
        }

        public String getCondBasuras() {
            return condBasuras;
        }

        public void setCondBasuras(String condBasuras) {
            this.condBasuras = condBasuras;
        }

        public String getDispReciclaje() {
            return dispReciclaje;
        }

        public void setDispReciclaje(String dispReciclaje) {
            this.dispReciclaje = dispReciclaje;
        }

        public String getCondReciclaje() {
            return condReciclaje;
        }

        public void setCondReciclaje(String condReciclaje) {
            this.condReciclaje = condReciclaje;
        }

        public String getDispAlcantarillado() {
            return dispAlcantarillado;
        }

        public void setDispAlcantarillado(String dispAlcantarillado) {
            this.dispAlcantarillado = dispAlcantarillado;
        }

        public String getCondAlcantarillado() {
            return condAlcantarillado;
        }

        public void setCondAlcantarillado(String condAlcantarillado) {
            this.condAlcantarillado = condAlcantarillado;
        }

        public String getDispPluvial() {
            return dispPluvial;
        }

        public void setDispPluvial(String dispPluvial) {
            this.dispPluvial = dispPluvial;
        }

        public String getCondPluvial() {
            return condPluvial;
        }

        public void setCondPluvial(String condPluvial) {
            this.condPluvial = condPluvial;
        }

        public String getDispInternet() {
            return dispInternet;
        }

        public void setDispInternet(String dispInternet) {
            this.dispInternet = dispInternet;
        }

        public String getCondInternet() {
            return condInternet;
        }

        public void setCondInternet(String condInternet) {
            this.condInternet = condInternet;
        }

        public String getDispTelefono() {
            return dispTelefono;
        }

        public void setDispTelefono(String dispTelefono) {
            this.dispTelefono = dispTelefono;
        }

        public String getCondTelefono() {
            return condTelefono;
        }

        public void setCondTelefono(String condTelefono) {
            this.condTelefono = condTelefono;
        }

        public Long getNumPredConServicioAgua() {
            return numPredConServicioAgua;
        }

        public void setNumPredConServicioAgua(Long numPredConServicioAgua) {
            this.numPredConServicioAgua = numPredConServicioAgua;
        }

        public Long getNumPredSegunCondServicioAgua() {
            return numPredSegunCondServicioAgua;
        }

        public void setNumPredSegunCondServicioAgua(Long numPredSegunCondServicioAgua) {
            this.numPredSegunCondServicioAgua = numPredSegunCondServicioAgua;
        }

        public Long getNumPredConServicioEnergia() {
            return numPredConServicioEnergia;
        }

        public void setNumPredConServicioEnergia(Long numPredConServicioEnergia) {
            this.numPredConServicioEnergia = numPredConServicioEnergia;
        }

        public Long getNumPredSegunCondServicioEnergia() {
            return numPredSegunCondServicioEnergia;
        }

        public void setNumPredSegunCondServicioEnergia(Long numPredSegunCondServicioEnergia) {
            this.numPredSegunCondServicioEnergia = numPredSegunCondServicioEnergia;
        }

        public Long getNumPredConServicioGas() {
            return numPredConServicioGas;
        }

        public void setNumPredConServicioGas(Long numPredConServicioGas) {
            this.numPredConServicioGas = numPredConServicioGas;
        }

        public Long getNumPredSegunCondServicioGas() {
            return numPredSegunCondServicioGas;
        }

        public void setNumPredSegunCondServicioGas(Long numPredSegunCondServicioGas) {
            this.numPredSegunCondServicioGas = numPredSegunCondServicioGas;
        }

        public Long getNumPredConServicioBasuras() {
            return numPredConServicioBasuras;
        }

        public void setNumPredConServicioBasuras(Long numPredConServicioBasuras) {
            this.numPredConServicioBasuras = numPredConServicioBasuras;
        }

        public Long getNumPredSegunCondServicioBasuras() {
            return numPredSegunCondServicioBasuras;
        }

        public void setNumPredSegunCondServicioBasuras(Long numPredSegunCondServicioBasuras) {
            this.numPredSegunCondServicioBasuras = numPredSegunCondServicioBasuras;
        }

        public Long getNumPredConServicioReciclaje() {
            return numPredConServicioReciclaje;
        }

        public void setNumPredConServicioReciclaje(Long numPredConServicioReciclaje) {
            this.numPredConServicioReciclaje = numPredConServicioReciclaje;
        }

        public Long getNumPredSegunCondServicioReciclaje() {
            return numPredSegunCondServicioReciclaje;
        }

        public void setNumPredSegunCondServicioReciclaje(Long numPredSegunCondServicioReciclaje) {
            this.numPredSegunCondServicioReciclaje = numPredSegunCondServicioReciclaje;
        }

        public Long getNumPredConServicioAlcantarillado() {
            return numPredConServicioAlcantarillado;
        }

        public void setNumPredConServicioAlcantarillado(Long numPredConServicioAlcantarillado) {
            this.numPredConServicioAlcantarillado = numPredConServicioAlcantarillado;
        }

        public Long getNumPredSegunCondServicioAlcantarillado() {
            return numPredSegunCondServicioAlcantarillado;
        }

        public void setNumPredSegunCondServicioAlcantarillado(Long numPredSegunCondServicioAlcantarillado) {
            this.numPredSegunCondServicioAlcantarillado = numPredSegunCondServicioAlcantarillado;
        }

        public Long getNumPredConServicioPluvial() {
            return numPredConServicioPluvial;
        }

        public void setNumPredConServicioPluvial(Long numPredConServicioPluvial) {
            this.numPredConServicioPluvial = numPredConServicioPluvial;
        }

        public Long getNumPredSegunCondServicioPluvial() {
            return numPredSegunCondServicioPluvial;
        }

        public void setNumPredSegunCondServicioPluvial(Long numPredSegunCondServicioPluvial) {
            this.numPredSegunCondServicioPluvial = numPredSegunCondServicioPluvial;
        }

        public Long getNumPredConServicioInternet() {
            return numPredConServicioInternet;
        }

        public void setNumPredConServicioInternet(Long numPredConServicioInternet) {
            this.numPredConServicioInternet = numPredConServicioInternet;
        }

        public Long getNumPredSegunCondServicioInternet() {
            return numPredSegunCondServicioInternet;
        }

        public void setNumPredSegunCondServicioInternet(Long numPredSegunCondServicioInternet) {
            this.numPredSegunCondServicioInternet = numPredSegunCondServicioInternet;
        }

        public Long getNumPredConServicioTelefono() {
            return numPredConServicioTelefono;
        }

        public void setNumPredConServicioTelefono(Long numPredConServicioTelefono) {
            this.numPredConServicioTelefono = numPredConServicioTelefono;
        }

        public Long getNumPredSegunCondServicioTelefono() {
            return numPredSegunCondServicioTelefono;
        }

        public void setNumPredSegunCondServicioTelefono(Long numPredSegunCondServicioTelefono) {
            this.numPredSegunCondServicioTelefono = numPredSegunCondServicioTelefono;
        }

    }

    public String getColumnaDispAgua() {
        return columnaDispAgua;
    }

    public String getColumnaCondAgua() {
        return columnaCondAgua;
    }

    public String getColumnaDispEnergia() {
        return columnaDispEnergia;
    }

    public String getColumnaCondEnergia() {
        return columnaCondEnergia;
    }

    public String getColumnaDispGas() {
        return columnaDispGas;
    }

    public String getColumnaCondGas() {
        return columnaCondGas;
    }

    public String getColumnaDispBasuras() {
        return columnaDispBasuras;
    }

    public String getColumnaCondBasuras() {
        return columnaCondBasuras;
    }

    public String getColumnaDispReciclaje() {
        return columnaDispReciclaje;
    }

    public String getColumnaCondReciclaje() {
        return columnaCondReciclaje;
    }

    public String getColumnaDispAlcantarillado() {
        return columnaDispAlcantarillado;
    }

    public String getColumnaCondAlcantarillado() {
        return columnaCondAlcantarillado;
    }

    public String getColumnaDispPluvial() {
        return columnaDispPluvial;
    }

    public String getColumnaCondPluvial() {
        return columnaCondPluvial;
    }

    public String getColumnaDispInternet() {
        return columnaDispInternet;
    }

    public String getColumnaCondInternet() {
        return columnaCondInternet;
    }

    public String getColumnaDispTelefono() {
        return columnaDispTelefono;
    }

    public String getColumnaCondTelefono() {
        return columnaCondTelefono;
    }

    public String getColumnaNumPredConServicioAgua() {
        return columnaNumPredConServicioAgua;
    }

    public String getColumnaNumPredSegunCondServicioAgua() {
        return columnaNumPredSegunCondServicioAgua;
    }

    public String getColumnaNumPredConServicioEnergia() {
        return columnaNumPredConServicioEnergia;
    }

    public String getColumnaNumPredSegunCondServicioEnergia() {
        return columnaNumPredSegunCondServicioEnergia;
    }

    public String getColumnaNumPredConServicioGas() {
        return columnaNumPredConServicioGas;
    }

    public String getColumnaNumPredSegunCondServicioGas() {
        return columnaNumPredSegunCondServicioGas;
    }

    public String getColumnaNumPredConServicioBasuras() {
        return columnaNumPredConServicioBasuras;
    }

    public String getColumnaNumPredSegunCondServicioBasuras() {
        return columnaNumPredSegunCondServicioBasuras;
    }

    public String getColumnaNumPredConServicioReciclaje() {
        return columnaNumPredConServicioReciclaje;
    }

    public String getColumnaNumPredSegunCondServicioReciclaje() {
        return columnaNumPredSegunCondServicioReciclaje;
    }

    public String getColumnaNumPredConServicioAlcantarillado() {
        return columnaNumPredConServicioAlcantarillado;
    }

    public String getColumnaNumPredSegunCondServicioAlcantarillado() {
        return columnaNumPredSegunCondServicioAlcantarillado;
    }

    public String getColumnaNumPredConServicioPluvial() {
        return columnaNumPredConServicioPluvial;
    }

    public String getColumnaNumPredSegunCondServicioPluvial() {
        return columnaNumPredSegunCondServicioPluvial;
    }

    public String getColumnaNumPredConServicioInternet() {
        return columnaNumPredConServicioInternet;
    }

    public String getColumnaNumPredSegunCondServicioInternet() {
        return columnaNumPredSegunCondServicioInternet;
    }

    public String getColumnaNumPredConServicioTelefono() {
        return columnaNumPredConServicioTelefono;
    }

    public String getColumnaNumPredSegunCondServicioTelefono() {
        return columnaNumPredSegunCondServicioTelefono;
    }

    public ConsultaAmbito getConsultaAmbito() {
        return consultaAmbito;
    }

    public void setConsultaAmbito(ConsultaAmbito consultaAmbito) {
        this.consultaAmbito = consultaAmbito;
    }
}
