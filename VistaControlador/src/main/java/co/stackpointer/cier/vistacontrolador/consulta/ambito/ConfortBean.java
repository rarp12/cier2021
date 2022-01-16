/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.consulta.ambito;

import co.stackpointer.cier.modelo.filtro.ambito.FiltroAmbitoConfort;
import co.stackpointer.cier.modelo.tipo.CalificacionCondicion;
import co.stackpointer.cier.modelo.tipo.ParamNivelConsul;
import co.stackpointer.cier.modelo.utilidad.UtilConsulta;
import co.stackpointer.cier.vista.Utilidades;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author user
 */
@ManagedBean(name = "confort")
@ViewScoped
public class ConfortBean extends ConsultaAmbito {

    @ManagedProperty(value = "#{consultaAmbito}")
    private ConsultaAmbito consultaAmbito;
    private CalificacionCondicion seleccionCondicionIluminacionNatural;
    private CalificacionCondicion seleccionCondicionVentilacionNatural;
    private CalificacionCondicion seleccionCondicionAcustica;
    private CalificacionCondicion seleccionCondicionIluminacionArtificial;
    private CalificacionCondicion seleccionCondicionDucha;
    private CalificacionCondicion seleccionCondicionLavamano;
    private CalificacionCondicion seleccionCondicionInodoro;
    private CalificacionCondicion seleccionCondicionOrinal;
    private List<Registro> lista;
    private FiltroAmbitoConfort filtro = new FiltroAmbitoConfort();
    private TotalConsulta totalConsulta = new TotalConsulta();
    final String columnaTotalEspacios = Utilidades.obtenerMensaje("consultas.ambito.confort.columna.totalEspacios");
    final String columnaEspaciosIluminacionNatural = Utilidades.obtenerMensaje("consultas.ambito.confort.columna.espaciosIluminacionNatural");
    final String columnaProporcionIluminacionNatural = Utilidades.obtenerMensaje("consultas.ambito.confort.columna.proporcionIluminacionNatural");
    final String columnaEspaciosVentilacionNatural = Utilidades.obtenerMensaje("consultas.ambito.confort.columna.espaciosVentilacionNatural");
    final String columnaProporcionVentilacionNatural = Utilidades.obtenerMensaje("consultas.ambito.confort.columna.proporcionVentilacionNatural");
    final String columnaEspaciosAcustica = Utilidades.obtenerMensaje("consultas.ambito.confort.columna.espaciosAcustica");
    final String columnaProporcionAcustica = Utilidades.obtenerMensaje("consultas.ambito.confort.columna.proporcionAcustica");
    final String columnaEspaciosIluminacionArtificial = Utilidades.obtenerMensaje("consultas.ambito.confort.columna.espaciosIluminacionArtificial");
    final String columnaProporcionIluminacionArtificial = Utilidades.obtenerMensaje("consultas.ambito.confort.columna.proporcionIluminacionArtificial");
    final String columnaEspaciosAislTermPiso = Utilidades.obtenerMensaje("consultas.ambito.confort.columna.espaciosAislTermPiso");
    final String columnaEspaciosAislTermTecho = Utilidades.obtenerMensaje("consultas.ambito.confort.columna.espaciosAislTermTecho");
    final String columnaEspaciosAislTermPared = Utilidades.obtenerMensaje("consultas.ambito.confort.columna.espaciosAislTermPared");
    final String columnaEspaciosAislTermVentana = Utilidades.obtenerMensaje("consultas.ambito.confort.columna.espaciosAislTermVentana");
    final String columnaEspaciosAislTermNovf = Utilidades.obtenerMensaje("consultas.ambito.confort.columna.espaciosAislTermNovf");
    final String columnaInodorosCondicion = Utilidades.obtenerMensaje("consultas.ambito.confort.columna.inodorosCondicion");
    final String columnaTotalInodoros = Utilidades.obtenerMensaje("consultas.ambito.confort.columna.totalInodoros");
    final String columnaProporcionInodoros = Utilidades.obtenerMensaje("consultas.ambito.confort.columna.proporcionInodoros");
    final String columnaDuchasCondicion = Utilidades.obtenerMensaje("consultas.ambito.confort.columna.duchasCondicion");
    final String columnaTotalDuchas = Utilidades.obtenerMensaje("consultas.ambito.confort.columna.totalDuchas");
    final String columnaProporcionDuchas = Utilidades.obtenerMensaje("consultas.ambito.confort.columna.proporcionDuchas");
    final String columnaLavamanoCondicion = Utilidades.obtenerMensaje("consultas.ambito.confort.columna.lavamanoCondicion");
    final String columnaTotalLavamano = Utilidades.obtenerMensaje("consultas.ambito.confort.columna.totalLavamano");
    final String columnaProporcionLavamano = Utilidades.obtenerMensaje("consultas.ambito.confort.columna.proporcionLavamano");
    final String columnaOrinalesCondicion = Utilidades.obtenerMensaje("consultas.ambito.confort.columna.orinalesCondicion");
    final String columnaTotalOrinales = Utilidades.obtenerMensaje("consultas.ambito.confort.columna.totalOrinales");
    final String columnaProporcionOrinales = Utilidades.obtenerMensaje("consultas.ambito.confort.columna.proporcionOrinales");
    final String columnaCondicionRA = Utilidades.obtenerMensaje("consultas.ambito.confort.columna.condicionRA");
    final String columnaCondicionRN = Utilidades.obtenerMensaje("consultas.ambito.confort.columna.condicionRN");

    @PostConstruct
    public void inicializar() {
        //super.init();
        lista = new ArrayList<Registro>();
        cargarCamposOpcionales();
    }

    private void cargarCamposOpcionales() {
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
        if (isMostrarColCondicionRA()) {
            listaCamposOpcionales.add(columnaCondicionRA);
        }
        if (isMostrarColCondicionRN()) {
            listaCamposOpcionales.add(columnaCondicionRN);
        }
        //propias del ambito  
        if (isMostrarColTotalEspacios()) {
            listaCamposOpcionales.add(columnaTotalEspacios);
            selccionCamposOpcionales.add(columnaTotalEspacios);
        }
        if (isMostrarColEspaciosIluminacionNatural()) {
            listaCamposOpcionales.add(columnaEspaciosIluminacionNatural);
            selccionCamposOpcionales.add(columnaEspaciosIluminacionNatural);
        }
        if (isMostrarColProporcionIluminacionNatural()) {
            listaCamposOpcionales.add(columnaProporcionIluminacionNatural);
            selccionCamposOpcionales.add(columnaProporcionIluminacionNatural);

        }
        if (isMostrarColEspaciosVentilacionNatural()) {
            listaCamposOpcionales.add(columnaEspaciosVentilacionNatural);
            selccionCamposOpcionales.add(columnaEspaciosVentilacionNatural);
        }
        if (isMostrarColProporcionVentilacionNatural()) {
            listaCamposOpcionales.add(columnaProporcionVentilacionNatural);
            selccionCamposOpcionales.add(columnaProporcionVentilacionNatural);
        }
        if (isMostrarColEspaciosAcustica()) {
            listaCamposOpcionales.add(columnaEspaciosAcustica);
            selccionCamposOpcionales.add(columnaEspaciosAcustica);
        }
        if (isMostrarColProporcionAcustica()) {
            listaCamposOpcionales.add(columnaProporcionAcustica);
            selccionCamposOpcionales.add(columnaProporcionAcustica);
        }
        if (isMostrarColEspaciosIluminacionArtificial()) {
            listaCamposOpcionales.add(columnaEspaciosIluminacionArtificial);
            selccionCamposOpcionales.add(columnaEspaciosIluminacionArtificial);
        }
        if (isMostrarColProporcionIluminacionArtificial()) {
            listaCamposOpcionales.add(columnaProporcionIluminacionArtificial);
            selccionCamposOpcionales.add(columnaProporcionIluminacionArtificial);
        }
        if (isMostrarColEspaciosAislTermPiso()) {
            listaCamposOpcionales.add(columnaEspaciosAislTermPiso);
            selccionCamposOpcionales.add(columnaEspaciosAislTermPiso);
        }
        if (isMostrarColEspaciosAislTermTecho()) {
            listaCamposOpcionales.add(columnaEspaciosAislTermTecho);
            selccionCamposOpcionales.add(columnaEspaciosAislTermTecho);
        }
        if (isMostrarColEspaciosAislTermPared()) {
            listaCamposOpcionales.add(columnaEspaciosAislTermPared);
            selccionCamposOpcionales.add(columnaEspaciosAislTermPared);
        }
        if (isMostrarColEspaciosAislTermVentana()) {
            listaCamposOpcionales.add(columnaEspaciosAislTermVentana);
            selccionCamposOpcionales.add(columnaEspaciosAislTermVentana);
        }
        if (isMostrarColEspaciosAislTermNovf()) {
            listaCamposOpcionales.add(columnaEspaciosAislTermNovf);
            selccionCamposOpcionales.add(columnaEspaciosAislTermNovf);
        }
        if (isMostrarColInodorosCondicion()) {
            listaCamposOpcionales.add(columnaInodorosCondicion);
            selccionCamposOpcionales.add(columnaInodorosCondicion);
        }
        if (isMostrarColTotalInodoros()) {
            listaCamposOpcionales.add(columnaTotalInodoros);
            selccionCamposOpcionales.add(columnaTotalInodoros);
        }
        if (isMostrarColProporcionInodoros()) {
            listaCamposOpcionales.add(columnaProporcionInodoros);
            selccionCamposOpcionales.add(columnaProporcionInodoros);
        }
        if (isMostrarColDuchasCondicion()) {
            listaCamposOpcionales.add(columnaDuchasCondicion);
            selccionCamposOpcionales.add(columnaDuchasCondicion);
        }
        if (isMostrarColTotalDuchas()) {
            listaCamposOpcionales.add(columnaTotalDuchas);
            selccionCamposOpcionales.add(columnaTotalDuchas);
        }
        if (isMostrarColProporcionDuchas()) {
            listaCamposOpcionales.add(columnaProporcionDuchas);
            selccionCamposOpcionales.add(columnaProporcionDuchas);
        }
        if (isMostrarColLavamanoCondicion()) {
            listaCamposOpcionales.add(columnaLavamanoCondicion);
            selccionCamposOpcionales.add(columnaLavamanoCondicion);
        }
        if (isMostrarColTotalLavamano()) {
            listaCamposOpcionales.add(columnaTotalLavamano);
            selccionCamposOpcionales.add(columnaTotalLavamano);
        }
        if (isMostrarColProporcionLavamano()) {
            listaCamposOpcionales.add(columnaProporcionLavamano);
            selccionCamposOpcionales.add(columnaProporcionLavamano);
        }
        if (isMostrarColOrinalesCondicion()) {
            listaCamposOpcionales.add(columnaOrinalesCondicion);
            selccionCamposOpcionales.add(columnaOrinalesCondicion);
        }
        if (isMostrarColTotalOrinales()) {
            listaCamposOpcionales.add(columnaTotalOrinales);
            selccionCamposOpcionales.add(columnaTotalOrinales);
        }
        if (isMostrarColProporcionOrinales()) {
            listaCamposOpcionales.add(columnaProporcionOrinales);
            selccionCamposOpcionales.add(columnaProporcionOrinales);
        }

    }
    
    public void condicionSeleccionada(){
        selccionCamposOpcionales.clear();
        this.lista = new ArrayList<Registro>();
        this.totalConsulta.inicializar();
        cargarCamposOpcionales();
    }
            
    @Override
    public void resetearFiltros() {
        consultaAmbito.resetearNiveles();
        this.resetearListasEstSedesPredios();
        this.seleccionSector = null;
        this.seleccionZona = null;
        this.seleccionCondicionIluminacionNatural = null;
        this.seleccionCondicionVentilacionNatural = null;
        this.seleccionCondicionAcustica = null;
        this.seleccionCondicionIluminacionArtificial = null;
        this.seleccionCondicionDucha = null;
        this.seleccionCondicionLavamano = null;
        this.seleccionCondicionInodoro = null;
        this.seleccionCondicionOrinal = null;
        consultaAmbito.nivelDPAOfAccesoUsuario();
        this.lista = new ArrayList<Registro>();
        this.selccionCamposOpcionales.clear();
        this.cargarCamposOpcionales();
        this.totalConsulta.inicializar();
    }
    /**
     * Cuando se cambie el filtro de periodo este reseteara todos los campos parametricos de la consulta a excepcion
     * de los que sonparte de la DPA
     */
    public void resetearFiltrosPeriodo() {
        //consultaAmbito.resetearNiveles();
        this.resetearListasEstSedesPredios();
        this.seleccionSector = null;
        this.seleccionZona = null;
        this.seleccionCondicionIluminacionNatural = null;
        this.seleccionCondicionVentilacionNatural = null;
        this.seleccionCondicionAcustica = null;
        this.seleccionCondicionIluminacionArtificial = null;
        this.seleccionCondicionDucha = null;
        this.seleccionCondicionLavamano = null;
        this.seleccionCondicionInodoro = null;
        this.seleccionCondicionOrinal = null;
        //consultaAmbito.nivelDPAOfAccesoUsuario();
        this.lista = new ArrayList<Registro>();
        this.selccionCamposOpcionales.clear();
        this.cargarCamposOpcionales();
        this.totalConsulta.inicializar();
    }

    public void consultar() {
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

        // filtros basicos
        filtro.setCodZona(seleccionZona != null ? seleccionZona.getTipValor().getCodigo() : null);
        filtro.setCodSector(seleccionSector != null ? seleccionSector.getTipValor().getCodigo() : null);
        //otros filtros
        filtro.setCondicionIluminacionNatural(seleccionCondicionIluminacionNatural != null ? seleccionCondicionIluminacionNatural.getValor() : null);
        filtro.setCondicionVentilacionNatural(seleccionCondicionVentilacionNatural != null ? seleccionCondicionVentilacionNatural.getValor() : null);
        filtro.setCondicionAcustica(seleccionCondicionAcustica != null ? seleccionCondicionAcustica.getValor() : null);
        filtro.setCondicionIluminacionArtificial(seleccionCondicionIluminacionArtificial != null ? seleccionCondicionIluminacionArtificial.getValor() : null);
        filtro.setCondicionDucha(seleccionCondicionDucha != null ? seleccionCondicionDucha.getValor() : null);
        filtro.setCondicionLavamano(seleccionCondicionLavamano != null ? seleccionCondicionLavamano.getValor() : null);
        filtro.setCondicionInodoro(seleccionCondicionInodoro != null ? seleccionCondicionInodoro.getValor() : null);
        filtro.setCondicionOrinal(seleccionCondicionOrinal != null ? seleccionCondicionOrinal.getValor() : null);

        List<Object[]> resultados = facAmbito.consultarConfort(filtro);
        lista = new ArrayList<Registro>(resultados.size());
         if(lista.size()<1)
            {
                mensajeTablaVacia = Utilidades.obtenerMensaje("aplicacion.general.noExistenRegistros");
            }
        totalConsulta.inicializar();
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
                registro.setZona(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]),getUsuarioSesion().getUsuario().getIdioma().getId()));
                registro.setSector(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]),getUsuarioSesion().getUsuario().getIdioma().getId()));
                registro.setClima(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]),getUsuarioSesion().getUsuario().getIdioma().getId()));
                registro.setCondicionRA(UtilConsulta.stringValueOf(fila[i++]));
                registro.setCondicionRN(UtilConsulta.stringValueOf(fila[i++]));
            }
            // indicadores
            registro.setTotalEspacios(UtilConsulta.longValueOf(fila[i++]));
            if (filtro.isConsultaDeTiempoReal()) {
                registro.setEspaciosIluminacionNatural(UtilConsulta.longValueOf(fila[i++]));
                registro.setProporcionIluminacionNatural(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setEspaciosVentilacionNatural(UtilConsulta.longValueOf(fila[i++]));
                registro.setProporcionVentilacionNatural(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setEspaciosAcustica(UtilConsulta.longValueOf(fila[i++]));
                registro.setProporcionAcustica(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setEspaciosIluminacionArtificial(UtilConsulta.longValueOf(fila[i++]));
                registro.setProporcionIluminacionArtificial(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setEspaciosAislTermPiso(UtilConsulta.longValueOf(fila[i++]));
                registro.setEspaciosAislTermTecho(UtilConsulta.longValueOf(fila[i++]));
                registro.setEspaciosAislTermPared(UtilConsulta.longValueOf(fila[i++]));
                registro.setEspaciosAislTermVentana(UtilConsulta.longValueOf(fila[i++]));
                registro.setEspaciosAislTermNovf(UtilConsulta.longValueOf(fila[i++]));
                registro.setInodorosCondicion(UtilConsulta.longValueOf(fila[i++]));
                registro.setTotalInodoros(UtilConsulta.longValueOf(fila[i++]));
                registro.setProporcionInodoros(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setDuchasCondicion(UtilConsulta.longValueOf(fila[i++]));
                registro.setTotalDuchas(UtilConsulta.longValueOf(fila[i++]));
                registro.setProporcionDuchas(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setLavamanoCondicion(UtilConsulta.longValueOf(fila[i++]));
                registro.setTotalLavamano(UtilConsulta.longValueOf(fila[i++]));
                registro.setProporcionLavamano(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setOrinalesCondicion(UtilConsulta.longValueOf(fila[i++]));
                registro.setTotalOrinales(UtilConsulta.longValueOf(fila[i++]));
                registro.setProporcionOrinales(UtilConsulta.doubleValueOf(fila[i++]));
                //totales
                totalConsulta.addNumEspaciosIluminacionNatural(registro.getEspaciosIluminacionNatural());
                totalConsulta.addNumEspaciosVentilacionNatural(registro.getEspaciosVentilacionNatural());
                totalConsulta.addNumEspaciosAcustica(registro.getEspaciosAcustica());
                totalConsulta.addNumEspaciosIluminacionArtificial(registro.getEspaciosIluminacionArtificial());
                totalConsulta.addNumInodorosCondicion(registro.getInodorosCondicion());
                totalConsulta.addNumDuchasCondicion(registro.getDuchasCondicion());
                totalConsulta.addNumLavamanoCondicion(registro.getLavamanoCondicion());
                totalConsulta.addNumOrinalesCondicion(registro.getOrinalesCondicion());
            } else {
                registro.setEspaciosAislTermPiso(UtilConsulta.longValueOf(fila[i++]));
                registro.setEspaciosAislTermTecho(UtilConsulta.longValueOf(fila[i++]));
                registro.setEspaciosAislTermPared(UtilConsulta.longValueOf(fila[i++]));
                registro.setEspaciosAislTermVentana(UtilConsulta.longValueOf(fila[i++]));
                registro.setEspaciosAislTermNovf(UtilConsulta.longValueOf(fila[i++]));
                registro.setTotalInodoros(UtilConsulta.longValueOf(fila[i++]));
                registro.setTotalDuchas(UtilConsulta.longValueOf(fila[i++]));
                registro.setTotalLavamano(UtilConsulta.longValueOf(fila[i++]));
                registro.setTotalOrinales(UtilConsulta.longValueOf(fila[i++]));
            }
            // totales
            totalConsulta.addNumTotalEspacios(registro.getTotalEspacios());
            totalConsulta.addNumEspaciosAislTermPiso(registro.getEspaciosAislTermPiso());
            totalConsulta.addNumEspaciosAislTermPared(registro.getEspaciosAislTermPared());
            totalConsulta.addNumEspaciosAislTermVentana(registro.getEspaciosAislTermVentana());
            totalConsulta.addNumEspaciosAislTermNovf(registro.getEspaciosAislTermNovf());
            totalConsulta.addNumTotalInodoros(registro.getTotalInodoros());
            totalConsulta.addNumTotalDuchas(registro.getTotalDuchas());
            totalConsulta.addNumTotalLavamano(registro.getTotalLavamano());
            totalConsulta.addNumTotalOrinales(registro.getTotalOrinales());
            lista.add(registro);
        }
    }

    @Override
    public void cambioAgrupamiento() {
        // columnas
        selccionCamposOpcionales.clear();
        this.cargarCamposOpcionales();
        // resultados
        lista.clear();
        totalConsulta.inicializar();
    }

    public CalificacionCondicion getSeleccionCondicionIluminacionNatural() {
        return seleccionCondicionIluminacionNatural;
    }

    public void setSeleccionCondicionIluminacionNatural(CalificacionCondicion seleccionCondicionIluminacionNatural) {
        this.seleccionCondicionIluminacionNatural = seleccionCondicionIluminacionNatural;
    }

    public CalificacionCondicion getSeleccionCondicionVentilacionNatural() {
        return seleccionCondicionVentilacionNatural;
    }

    public void setSeleccionCondicionVentilacionNatural(CalificacionCondicion seleccionCondicionVentilacionNatural) {
        this.seleccionCondicionVentilacionNatural = seleccionCondicionVentilacionNatural;
    }

    public CalificacionCondicion getSeleccionCondicionAcustica() {
        return seleccionCondicionAcustica;
    }

    public void setSeleccionCondicionAcustica(CalificacionCondicion seleccionCondicionAcustica) {
        this.seleccionCondicionAcustica = seleccionCondicionAcustica;
    }

    public CalificacionCondicion getSeleccionCondicionIluminacionArtificial() {
        return seleccionCondicionIluminacionArtificial;
    }

    public void setSeleccionCondicionIluminacionArtificial(CalificacionCondicion seleccionCondicionIluminacionArtificial) {
        this.seleccionCondicionIluminacionArtificial = seleccionCondicionIluminacionArtificial;
    }

    public CalificacionCondicion getSeleccionCondicionDucha() {
        return seleccionCondicionDucha;
    }

    public void setSeleccionCondicionDucha(CalificacionCondicion seleccionCondicionDucha) {
        this.seleccionCondicionDucha = seleccionCondicionDucha;
    }

    public CalificacionCondicion getSeleccionCondicionLavamano() {
        return seleccionCondicionLavamano;
    }

    public void setSeleccionCondicionLavamano(CalificacionCondicion seleccionCondicionLavamano) {
        this.seleccionCondicionLavamano = seleccionCondicionLavamano;
    }

    public CalificacionCondicion getSeleccionCondicionInodoro() {
        return seleccionCondicionInodoro;
    }

    public void setSeleccionCondicionInodoro(CalificacionCondicion seleccionCondicionInodoro) {
        this.seleccionCondicionInodoro = seleccionCondicionInodoro;
    }

    public CalificacionCondicion getSeleccionCondicionOrinal() {
        return seleccionCondicionOrinal;
    }

    public void setSeleccionCondicionOrinal(CalificacionCondicion seleccionCondicionOrinal) {
        this.seleccionCondicionOrinal = seleccionCondicionOrinal;
    }

    public List<Registro> getLista() {
        return lista;
    }

    public TotalConsulta getTotalConsulta() {
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
        private String zona;
        private String sector;
        private String clima;
        private String condicionRA;
        private String condicionRN;
        private Long totalEspacios;
        private Long espaciosIluminacionNatural;
        private Double proporcionIluminacionNatural;
        private Long espaciosVentilacionNatural;
        private Double proporcionVentilacionNatural;
        private Long espaciosAcustica;
        private Double proporcionAcustica;
        private Long espaciosIluminacionArtificial;
        private Double proporcionIluminacionArtificial;
        private Long espaciosAislTermPiso;
        private Long espaciosAislTermTecho;
        private Long espaciosAislTermPared;
        private Long espaciosAislTermVentana;
        private Long espaciosAislTermNovf;
        private Long inodorosCondicion;
        private Long totalInodoros;
        private Double proporcionInodoros;
        private Long duchasCondicion;
        private Long totalDuchas;
        private Double proporcionDuchas;
        private Long lavamanoCondicion;
        private Long totalLavamano;
        private Double proporcionLavamano;
        private Long orinalesCondicion;
        private Long totalOrinales;
        private Double proporcionOrinales;

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

        public String getCondicionRA() {
            return condicionRA;
        }

        public void setCondicionRA(String condicionRA) {
            this.condicionRA = condicionRA;
            if (condicionRA != null) {
                if (condicionRA.equalsIgnoreCase("sql.ind.general.critico")) {
                    this.condicionRA = Utilidades.obtenerMensaje(condicionRA);
                } else {
                    this.condicionRA = Utilidades.obtenerMensaje(condicionRA);
                }
            }
        }

        public String getCondicionRN() {
            return condicionRN;
        }

        public void setCondicionRN(String condicionRN) {
            this.condicionRN = condicionRN;
            if (condicionRN != null) {
                if (condicionRN.equalsIgnoreCase("sql.ind.general.critico")) {
                    this.condicionRN = Utilidades.obtenerMensaje(condicionRN);
                } else {
                    this.condicionRN = Utilidades.obtenerMensaje(condicionRN);
                }
            }
        }
        
        public Long getEspaciosIluminacionNatural() {
            return espaciosIluminacionNatural;
        }

        public void setEspaciosIluminacionNatural(Long espaciosIluminacionNatural) {
            this.espaciosIluminacionNatural = espaciosIluminacionNatural;
        }

        public Long getTotalEspacios() {
            return totalEspacios;
        }

        public void setTotalEspacios(Long totalEspacios) {
            this.totalEspacios = totalEspacios;
        }

        public Double getProporcionIluminacionNatural() {
            return proporcionIluminacionNatural;
        }

        public void setProporcionIluminacionNatural(Double proporcionIluminacionNatural) {
            this.proporcionIluminacionNatural = proporcionIluminacionNatural;
        }

        public Long getEspaciosVentilacionNatural() {
            return espaciosVentilacionNatural;
        }

        public void setEspaciosVentilacionNatural(Long espaciosVentilacionNatural) {
            this.espaciosVentilacionNatural = espaciosVentilacionNatural;
        }

        public Double getProporcionVentilacionNatural() {
            return proporcionVentilacionNatural;
        }

        public void setProporcionVentilacionNatural(Double proporcionVentilacionNatural) {
            this.proporcionVentilacionNatural = proporcionVentilacionNatural;
        }

        public Long getEspaciosAcustica() {
            return espaciosAcustica;
        }

        public void setEspaciosAcustica(Long espaciosAcustica) {
            this.espaciosAcustica = espaciosAcustica;
        }

        public Double getProporcionAcustica() {
            return proporcionAcustica;
        }

        public void setProporcionAcustica(Double proporcionAcustica) {
            this.proporcionAcustica = proporcionAcustica;
        }

        public Long getEspaciosIluminacionArtificial() {
            return espaciosIluminacionArtificial;
        }

        public void setEspaciosIluminacionArtificial(Long espaciosIluminacionArtificial) {
            this.espaciosIluminacionArtificial = espaciosIluminacionArtificial;
        }

        public Double getProporcionIluminacionArtificial() {
            return proporcionIluminacionArtificial;
        }

        public void setProporcionIluminacionArtificial(Double proporcionIluminacionArtificial) {
            this.proporcionIluminacionArtificial = proporcionIluminacionArtificial;
        }

        public Long getEspaciosAislTermPiso() {
            return espaciosAislTermPiso;
        }

        public void setEspaciosAislTermPiso(Long espaciosAislTermPiso) {
            this.espaciosAislTermPiso = espaciosAislTermPiso;
        }

        public Long getEspaciosAislTermTecho() {
            return espaciosAislTermTecho;
        }

        public void setEspaciosAislTermTecho(Long espaciosAislTermTecho) {
            this.espaciosAislTermTecho = espaciosAislTermTecho;
        }

        public Long getEspaciosAislTermPared() {
            return espaciosAislTermPared;
        }

        public void setEspaciosAislTermPared(Long espaciosAislTermPared) {
            this.espaciosAislTermPared = espaciosAislTermPared;
        }

        public Long getEspaciosAislTermVentana() {
            return espaciosAislTermVentana;
        }

        public void setEspaciosAislTermVentana(Long espaciosAislTermVentana) {
            this.espaciosAislTermVentana = espaciosAislTermVentana;
        }

        public Long getEspaciosAislTermNovf() {
            return espaciosAislTermNovf;
        }

        public void setEspaciosAislTermNovf(Long espaciosAislTermNovf) {
            this.espaciosAislTermNovf = espaciosAislTermNovf;
        }

        public Long getInodorosCondicion() {
            return inodorosCondicion;
        }

        public void setInodorosCondicion(Long inodorosCondicion) {
            this.inodorosCondicion = inodorosCondicion;
        }

        public Long getTotalInodoros() {
            return totalInodoros;
        }

        public void setTotalInodoros(Long totalInodoros) {
            this.totalInodoros = totalInodoros;
        }

        public Double getProporcionInodoros() {
            return proporcionInodoros;
        }

        public void setProporcionInodoros(Double proporcionInodoros) {
            this.proporcionInodoros = proporcionInodoros;
        }

        public Long getDuchasCondicion() {
            return duchasCondicion;
        }

        public void setDuchasCondicion(Long duchasCondicion) {
            this.duchasCondicion = duchasCondicion;
        }

        public Long getTotalDuchas() {
            return totalDuchas;
        }

        public void setTotalDuchas(Long totalDuchas) {
            this.totalDuchas = totalDuchas;
        }

        public Double getProporcionDuchas() {
            return proporcionDuchas;
        }

        public void setProporcionDuchas(Double proporcionDuchas) {
            this.proporcionDuchas = proporcionDuchas;
        }

        public Long getLavamanoCondicion() {
            return lavamanoCondicion;
        }

        public void setLavamanoCondicion(Long lavamanoCondicion) {
            this.lavamanoCondicion = lavamanoCondicion;
        }

        public Long getTotalLavamano() {
            return totalLavamano;
        }

        public void setTotalLavamano(Long totalLavamano) {
            this.totalLavamano = totalLavamano;
        }

        public Double getProporcionLavamano() {
            return proporcionLavamano;
        }

        public void setProporcionLavamano(Double proporcionLavamano) {
            this.proporcionLavamano = proporcionLavamano;
        }

        public Long getOrinalesCondicion() {
            return orinalesCondicion;
        }

        public void setOrinalesCondicion(Long orinalesCondicion) {
            this.orinalesCondicion = orinalesCondicion;
        }

        public Long getTotalOrinales() {
            return totalOrinales;
        }

        public void setTotalOrinales(Long totalOrinales) {
            this.totalOrinales = totalOrinales;
        }

        public Double getProporcionOrinales() {
            return proporcionOrinales;
        }

        public void setProporcionOrinales(Double proporcionOrinales) {
            this.proporcionOrinales = proporcionOrinales;
        }
    }

    public class TotalConsulta {

        private Long numTotalEspacios;
        private Long numEspaciosIluminacionNatural;
        private Long numEspaciosVentilacionNatural;
        private Long numEspaciosAcustica;
        private Long numEspaciosIluminacionArtificial;
        private Long numEspaciosAislTermPiso;
        private Long numEspaciosAislTermTecho;
        private Long numEspaciosAislTermPared;
        private Long numEspaciosAislTermVentana;
        private Long numEspaciosAislTermNovf;
        private Long numInodorosCondicion;
        private Long numTotalInodoros;
        private Long numDuchasCondicion;
        private Long numTotalDuchas;
        private Long numLavamanoCondicion;
        private Long numTotalLavamano;
        private Long numOrinalesCondicion;
        private Long numTotalOrinales;

        public void inicializar() {
            numTotalEspacios = 0L;
            numEspaciosIluminacionNatural = 0L;
            numEspaciosVentilacionNatural = 0L;
            numEspaciosAcustica = 0L;
            numEspaciosIluminacionArtificial = 0L;
            numEspaciosAislTermPiso = 0L;
            numEspaciosAislTermTecho = 0L;
            numEspaciosAislTermPared = 0L;
            numEspaciosAislTermVentana = 0L;
            numEspaciosAislTermNovf = 0L;
            numInodorosCondicion = 0L;
            numTotalInodoros = 0L;
            numDuchasCondicion = 0L;
            numTotalDuchas = 0L;
            numLavamanoCondicion = 0L;
            numTotalLavamano = 0L;
            numOrinalesCondicion = 0L;
            numTotalOrinales = 0L;
        }

        public Long getNumTotalEspacios() {
            return numTotalEspacios;
        }

        public Long getNumEspaciosIluminacionNatural() {
            return numEspaciosIluminacionNatural;
        }

        public Long getNumEspaciosVentilacionNatural() {
            return numEspaciosVentilacionNatural;
        }

        public Long getNumEspaciosAcustica() {
            return numEspaciosAcustica;
        }

        public Long getNumEspaciosIluminacionArtificial() {
            return numEspaciosIluminacionArtificial;
        }

        public Long getNumEspaciosAislTermPiso() {
            return numEspaciosAislTermPiso;
        }

        public Long getNumEspaciosAislTermTecho() {
            return numEspaciosAislTermTecho;
        }

        public Long getNumEspaciosAislTermPared() {
            return numEspaciosAislTermPared;
        }

        public Long getNumEspaciosAislTermVentana() {
            return numEspaciosAislTermVentana;
        }

        public Long getNumEspaciosAislTermNovf() {
            return numEspaciosAislTermNovf;
        }

        public Long getNumInodorosCondicion() {
            return numInodorosCondicion;
        }

        public Long getNumTotalInodoros() {
            return numTotalInodoros;
        }

        public Long getNumDuchasCondicion() {
            return numDuchasCondicion;
        }

        public Long getNumTotalDuchas() {
            return numTotalDuchas;
        }

        public Long getNumLavamanoCondicion() {
            return numLavamanoCondicion;
        }

        public Long getNumTotalLavamano() {
            return numTotalLavamano;
        }

        public Long getNumOrinalesCondicion() {
            return numOrinalesCondicion;
        }

        public Long getNumTotalOrinales() {
            return numTotalOrinales;
        }

        public void addNumTotalEspacios(Long valor) {
            if (valor != null) {
                this.numTotalEspacios += valor;
            }
        }

        public void addNumEspaciosIluminacionNatural(Long valor) {
            if (valor != null) {
                this.numEspaciosIluminacionNatural += valor;
            }
        }

        public void addNumEspaciosVentilacionNatural(Long valor) {
            if (valor != null) {
                this.numEspaciosVentilacionNatural += valor;
            }
        }

        public void addNumEspaciosAcustica(Long valor) {
            if (valor != null) {
                this.numEspaciosAcustica += valor;
            }
        }

        public void addNumEspaciosIluminacionArtificial(Long valor) {
            if (valor != null) {
                this.numEspaciosIluminacionArtificial += valor;
            }
        }

        public void addNumEspaciosAislTermPiso(Long valor) {
            if (valor != null) {
                this.numEspaciosAislTermPiso += valor;
            }
        }

        public void addNumEspaciosAislTermPared(Long valor) {
            if (valor != null) {
                this.numEspaciosAislTermPared += valor;
            }
        }

        public void addNumEspaciosAislTermVentana(Long valor) {
            if (valor != null) {
                this.numEspaciosAislTermVentana += valor;
            }
        }

        public void addNumEspaciosAislTermNovf(Long valor) {
            if (valor != null) {
                this.numEspaciosAislTermNovf += valor;
            }
        }

        public void addNumInodorosCondicion(Long valor) {
            if (valor != null) {
                this.numInodorosCondicion += valor;
            }
        }

        public void addNumTotalInodoros(Long valor) {
            if (valor != null) {
                this.numTotalInodoros += valor;
            }
        }

        public void addNumDuchasCondicion(Long valor) {
            if (valor != null) {
                this.numDuchasCondicion += valor;
            }
        }

        public void addNumTotalDuchas(Long valor) {
            if (valor != null) {
                this.numTotalDuchas += valor;
            }
        }

        public void addNumLavamanoCondicion(Long valor) {
            if (valor != null) {
                this.numLavamanoCondicion += valor;
            }
        }

        public void addNumTotalLavamano(Long valor) {
            if (valor != null) {
                this.numTotalLavamano += valor;
            }
        }

        public void addNumOrinalesCondicion(Long valor) {
            if (valor != null) {
                this.numOrinalesCondicion += valor;
            }
        }

        public void addNumTotalOrinales(Long valor) {
            if (valor != null) {
                this.numTotalOrinales += valor;
            }
        }
    }

    // mostrar columnas  
    public boolean isMostrarColCondicionRA() {
        return isAgrupamientoPredio();
    }
    
    public boolean isMostrarColCondicionRN() {
        return isAgrupamientoPredio();
    }
    
    public boolean isMostrarColTotalEspacios() {
        return true;
    }

    public boolean isMostrarColEspaciosIluminacionNatural() {
        return seleccionCondicionIluminacionNatural != null;
    }

    public boolean isMostrarColProporcionIluminacionNatural() {
        return seleccionCondicionIluminacionNatural != null;
    }

    public boolean isMostrarColEspaciosVentilacionNatural() {
        return seleccionCondicionVentilacionNatural != null;
    }

    public boolean isMostrarColProporcionVentilacionNatural() {
        return seleccionCondicionVentilacionNatural != null;
    }

    public boolean isMostrarColEspaciosAcustica() {
        return seleccionCondicionAcustica != null;
    }

    public boolean isMostrarColProporcionAcustica() {
        return seleccionCondicionAcustica != null;
    }

    public boolean isMostrarColEspaciosIluminacionArtificial() {
        return seleccionCondicionIluminacionArtificial != null;
    }

    public boolean isMostrarColProporcionIluminacionArtificial() {
        return seleccionCondicionIluminacionArtificial != null;
    }

    public boolean isMostrarColEspaciosAislTermPiso() {
        return true;
    }

    public boolean isMostrarColEspaciosAislTermTecho() {
        return true;
    }

    public boolean isMostrarColEspaciosAislTermPared() {
        return true;
    }

    public boolean isMostrarColEspaciosAislTermVentana() {
        return true;
    }

    public boolean isMostrarColEspaciosAislTermNovf() {
        return true;
    }

    public boolean isMostrarColInodorosCondicion() {
        return seleccionCondicionInodoro != null;
    }

    public boolean isMostrarColTotalInodoros() {
        return true;
    }

    public boolean isMostrarColProporcionInodoros() {
        return seleccionCondicionInodoro != null;
    }

    public boolean isMostrarColDuchasCondicion() {
        return seleccionCondicionDucha != null;
    }

    public boolean isMostrarColTotalDuchas() {
        return true;
    }

    public boolean isMostrarColProporcionDuchas() {
        return seleccionCondicionDucha != null;
    }

    public boolean isMostrarColLavamanoCondicion() {
        return seleccionCondicionLavamano != null;
    }

    public boolean isMostrarColTotalLavamano() {
        return true;
    }

    public boolean isMostrarColProporcionLavamano() {
        return seleccionCondicionLavamano != null;
    }

    public boolean isMostrarColOrinalesCondicion() {
        return seleccionCondicionOrinal != null;
    }

    public boolean isMostrarColTotalOrinales() {
        return true;
    }

    public boolean isMostrarColProporcionOrinales() {
        return seleccionCondicionOrinal != null;
    }

    //getter de columnas
    public String getColumnaTotalEspacios() {
        return columnaTotalEspacios;
    }

    public String getColumnaEspaciosIluminacionNatural() {
        return columnaEspaciosIluminacionNatural;
    }

    public String getColumnaProporcionIluminacionNatural() {
        return columnaProporcionIluminacionNatural;
    }

    public String getColumnaEspaciosVentilacionNatural() {
        return columnaEspaciosVentilacionNatural;
    }

    public String getColumnaProporcionVentilacionNatural() {
        return columnaProporcionVentilacionNatural;
    }

    public String getColumnaEspaciosAcustica() {
        return columnaEspaciosAcustica;
    }

    public String getColumnaProporcionAcustica() {
        return columnaProporcionAcustica;
    }

    public String getColumnaEspaciosIluminacionArtificial() {
        return columnaEspaciosIluminacionArtificial;
    }

    public String getColumnaProporcionIluminacionArtificial() {
        return columnaProporcionIluminacionArtificial;
    }

    public String getColumnaEspaciosAislTermPiso() {
        return columnaEspaciosAislTermPiso;
    }

    public String getColumnaEspaciosAislTermTecho() {
        return columnaEspaciosAislTermTecho;
    }

    public String getColumnaEspaciosAislTermPared() {
        return columnaEspaciosAislTermPared;
    }

    public String getColumnaEspaciosAislTermVentana() {
        return columnaEspaciosAislTermVentana;
    }

    public String getColumnaEspaciosAislTermNovf() {
        return columnaEspaciosAislTermNovf;
    }

    public String getColumnaInodorosCondicion() {
        return columnaInodorosCondicion;
    }

    public String getColumnaTotalInodoros() {
        return columnaTotalInodoros;
    }

    public String getColumnaProporcionInodoros() {
        return columnaProporcionInodoros;
    }

    public String getColumnaDuchasCondicion() {
        return columnaDuchasCondicion;
    }

    public String getColumnaTotalDuchas() {
        return columnaTotalDuchas;
    }

    public String getColumnaProporcionDuchas() {
        return columnaProporcionDuchas;
    }

    public String getColumnaLavamanoCondicion() {
        return columnaLavamanoCondicion;
    }

    public String getColumnaTotalLavamano() {
        return columnaTotalLavamano;
    }

    public String getColumnaProporcionLavamano() {
        return columnaProporcionLavamano;
    }

    public String getColumnaOrinalesCondicion() {
        return columnaOrinalesCondicion;
    }

    public String getColumnaTotalOrinales() {
        return columnaTotalOrinales;
    }

    public String getColumnaProporcionOrinales() {
        return columnaProporcionOrinales;
    }

    public String getColumnaCondicionRA() {
        return columnaCondicionRA;
    }

    public String getColumnaCondicionRN() {
        return columnaCondicionRN;
    }
    
    public boolean isCumple(String condicion) {
        if (condicion.trim().toUpperCase().equals("CUMPLE")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isNoCumple(String condicion) {
        if (condicion.trim().toUpperCase().equals("NO CUMPLE")) {
            return true;
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
