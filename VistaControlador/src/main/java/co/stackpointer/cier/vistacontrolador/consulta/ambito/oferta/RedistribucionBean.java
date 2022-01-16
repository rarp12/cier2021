/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.consulta.ambito.oferta;

import co.stackpointer.cier.modelo.filtro.ambito.FiltroAmbitoRedistribucion;
import co.stackpointer.cier.modelo.utilidad.UtilConsulta;
import co.stackpointer.cier.vista.Utilidades;
import co.stackpointer.cier.vistacontrolador.consulta.ambito.ConsultaAmbito;
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
@ManagedBean(name = "redistribucionBean")
@ViewScoped
public class RedistribucionBean extends ConsultaAmbito {

    @ManagedProperty(value = "#{consultaAmbito}")
    private ConsultaAmbito consultaAmbito;
    private TotalConsulta totalConsulta = new TotalConsulta();
    private List<Registro> lista;
    private FiltroAmbitoRedistribucion filtro = new FiltroAmbitoRedistribucion();
    
    private final String columnaNumPredioSaturados = Utilidades.obtenerMensaje("consultas.ambito.redistribucion.columna.num.predio.saturados");
    private final String columnaNumPredioSubutilizados = Utilidades.obtenerMensaje("consultas.ambito.redistribucion.columna.num.predio.subutilizados");
    private final String columnaUtilizacionTotal = Utilidades.obtenerMensaje("consultas.ambito.redistribucion.columna.utilizacion.total");
    private final String columnaCuposTotal = Utilidades.obtenerMensaje("consultas.ambito.redistribucion.columna.cupos.total");
    private final String columnaAlternoTotalEst = Utilidades.obtenerMensaje("consultas.ambito.redistribucion.columna.alterno.total.est");
    private final String columnaAlternoTotalCupos = Utilidades.obtenerMensaje("consultas.ambito.redistribucion.columna.alterno.total.cupos");
    private final String columnaAlternoUtilEstandar = Utilidades.obtenerMensaje("consultas.ambito.redistribucion.columna.alterno.util.estandar");
    private final String columnaCompletaTotalEst = Utilidades.obtenerMensaje("consultas.ambito.redistribucion.columna.completa.total.est");
    private final String columnaCompletaTotalCupos = Utilidades.obtenerMensaje("consultas.ambito.redistribucion.columna.completa.total.cupos");
    private final String columnaCompletaUtilEstandar = Utilidades.obtenerMensaje("consultas.ambito.redistribucion.columna.completa.util.estandar");
    private final String columnaDobleTotalEst = Utilidades.obtenerMensaje("consultas.ambito.redistribucion.columna.doble.total.est");
    private final String columnaDobleTotalCupos = Utilidades.obtenerMensaje("consultas.ambito.redistribucion.columna.doble.total.cupos");
    private final String columnaDobleUtilEstandar = Utilidades.obtenerMensaje("consultas.ambito.redistribucion.columna.doble.util.estandar");
    private final String columnaHorarioTotalEst = Utilidades.obtenerMensaje("consultas.ambito.redistribucion.columna.horario.total.est");
    private final String columnaHorarioTotalCupos = Utilidades.obtenerMensaje("consultas.ambito.redistribucion.columna.horario.total.cupos");
    private final String columnaHorarioUtilEstandar = Utilidades.obtenerMensaje("consultas.ambito.redistribucion.columna.horario.util.estandar");
    private final String columnaMañanaTotalEst = Utilidades.obtenerMensaje("consultas.ambito.redistribucion.columna.mañana.total.est");
    private final String columnaMañanaTotalCupos = Utilidades.obtenerMensaje("consultas.ambito.redistribucion.columna.mañana.total.cupos");
    private final String columnaMañanaUtilEstandar = Utilidades.obtenerMensaje("consultas.ambito.redistribucion.columna.mañana.util.estandar");
    private final String columnaNocturnaTotalEst = Utilidades.obtenerMensaje("consultas.ambito.redistribucion.columna.nocturna.total.est");
    private final String columnaNocturnaTotalCupos = Utilidades.obtenerMensaje("consultas.ambito.redistribucion.columna.nocturna.total.cupos");
    private final String columnaNocturnaUtilEstandar = Utilidades.obtenerMensaje("consultas.ambito.redistribucion.columna.nocturna.util.estandar");
    private final String columnaTardeTotalEst = Utilidades.obtenerMensaje("consultas.ambito.redistribucion.columna.tarde.total.est");
    private final String columnaTardeTotalCupos = Utilidades.obtenerMensaje("consultas.ambito.redistribucion.columna.tarde.total.cupos");
    private final String columnaTardeUtilEstandar = Utilidades.obtenerMensaje("consultas.ambito.redistribucion.columna.tarde.util.estandar");
    private final String columnaTripleTotalEst = Utilidades.obtenerMensaje("consultas.ambito.redistribucion.columna.triple.total.est");
    private final String columnaTripleTotalCupos = Utilidades.obtenerMensaje("consultas.ambito.redistribucion.columna.triple.total.cupos");
    private final String columnaTripleUtilEstandar = Utilidades.obtenerMensaje("consultas.ambito.redistribucion.columna.triple.util.estandar");
    private final String columnaVespertinaTotalEst = Utilidades.obtenerMensaje("consultas.ambito.redistribucion.columna.vespertina.total.est");
    private final String columnaVespertinaTotalCupos = Utilidades.obtenerMensaje("consultas.ambito.redistribucion.columna.vespertina.total.cupos");
    private final String columnaVespertinaUtilEstandar = Utilidades.obtenerMensaje("consultas.ambito.redistribucion.columna.vespertina.uti.estandar");
    private final String columnaOtraTotalEst = Utilidades.obtenerMensaje("consultas.ambito.redistribucion.columna.otra.total.est");
    private final String columnaOtraTotalCupos = Utilidades.obtenerMensaje("consultas.ambito.redistribucion.columna.otra.total.cupos");
    private final String columnaOtraUtilEstandar = Utilidades.obtenerMensaje("consultas.ambito.redistribucion.columna.otra.util.estandar");
    private final String columnaTotalPredio = Utilidades.obtenerMensaje("consultas.ambito.redistribucion.columna.total.predio");
    
    @PostConstruct
    public void inicializar() {
        //super.init();
        this.lista = new ArrayList<Registro>();
        this.cargarCamposOpcionales();
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
        if (isMostrarColEtnias()) {
            listaCamposOpcionales.add(columnaEtnias);
        }
        if (isMostrarColNumPrediosSaturados()) {
            listaCamposOpcionales.add(columnaNumPredioSaturados);
            selccionCamposOpcionales.add(columnaNumPredioSaturados);
        }
        if (isMostrarColNumPrediosSudUtilizados()) {
            listaCamposOpcionales.add(columnaNumPredioSubutilizados);
            selccionCamposOpcionales.add(columnaNumPredioSubutilizados);
        }
        if (isMostrarColUtilEstandarTotal()) {
            listaCamposOpcionales.add(columnaUtilizacionTotal);
            selccionCamposOpcionales.add(columnaUtilizacionTotal);
        }
        if (isMostrarColCuposTotal()) {
            listaCamposOpcionales.add(columnaCuposTotal);
            selccionCamposOpcionales.add(columnaCuposTotal);
        }
        if (isMostrarColTotalPredios()) {
            listaCamposOpcionales.add(columnaTotalPredio);
            selccionCamposOpcionales.add(columnaTotalPredio);
        }
        if (isMostrarColJornada()) {
            listaCamposOpcionales.add(columnaAlternoTotalEst);
            listaCamposOpcionales.add(columnaAlternoTotalCupos);
            listaCamposOpcionales.add(columnaAlternoUtilEstandar);

            listaCamposOpcionales.add(columnaCompletaTotalEst);
            listaCamposOpcionales.add(columnaCompletaTotalCupos);
            listaCamposOpcionales.add(columnaCompletaUtilEstandar);

            listaCamposOpcionales.add(columnaDobleTotalEst);
            listaCamposOpcionales.add(columnaDobleTotalCupos);
            listaCamposOpcionales.add(columnaDobleUtilEstandar);
            listaCamposOpcionales.add(columnaHorarioTotalEst);
            listaCamposOpcionales.add(columnaHorarioTotalCupos);
            listaCamposOpcionales.add(columnaHorarioUtilEstandar);

            listaCamposOpcionales.add(columnaMañanaTotalEst);
            listaCamposOpcionales.add(columnaMañanaTotalCupos);
            listaCamposOpcionales.add(columnaMañanaUtilEstandar);

            listaCamposOpcionales.add(columnaNocturnaTotalEst);
            listaCamposOpcionales.add(columnaNocturnaTotalCupos);
            listaCamposOpcionales.add(columnaNocturnaUtilEstandar);

            listaCamposOpcionales.add(columnaTardeTotalEst);
            listaCamposOpcionales.add(columnaTardeTotalCupos);
            listaCamposOpcionales.add(columnaTardeUtilEstandar);

            listaCamposOpcionales.add(columnaTripleTotalEst);
            listaCamposOpcionales.add(columnaTripleTotalCupos);
            listaCamposOpcionales.add(columnaTripleUtilEstandar);

            listaCamposOpcionales.add(columnaVespertinaTotalEst);
            listaCamposOpcionales.add(columnaVespertinaTotalCupos);
            listaCamposOpcionales.add(columnaVespertinaUtilEstandar);

            listaCamposOpcionales.add(columnaOtraTotalEst);
            listaCamposOpcionales.add(columnaOtraTotalCupos);
            listaCamposOpcionales.add(columnaOtraUtilEstandar);

            // seleccion

            selccionCamposOpcionales.add(columnaDobleTotalEst);
            selccionCamposOpcionales.add(columnaDobleTotalCupos);
            selccionCamposOpcionales.add(columnaDobleUtilEstandar);
            
            selccionCamposOpcionales.add(columnaMañanaTotalEst);
            selccionCamposOpcionales.add(columnaMañanaTotalCupos);
            selccionCamposOpcionales.add(columnaMañanaUtilEstandar);

            selccionCamposOpcionales.add(columnaTardeTotalEst);
            selccionCamposOpcionales.add(columnaTardeTotalCupos);
            selccionCamposOpcionales.add(columnaTardeUtilEstandar);            
        }
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

        //otros filtros
        filtro.setCodZona(seleccionZona != null ? seleccionZona.getTipValor().getCodigo() : null);
        filtro.setCodSector(seleccionSector != null ? seleccionSector.getTipValor().getCodigo() : null);
        List<Object[]> resultados = facAmbito.consultarRedistribucion(filtro);
        lista = new ArrayList<Registro>(resultados.size());
           if(lista.size()<1)
            {
                mensajeTablaVacia = Utilidades.obtenerMensaje("aplicacion.general.noExistenRegistros");
            }
           
        totalConsulta.inicializar();
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
                // indicadores jornadas
                registro.setAlternoVariaHorarioTotalEstudiantes(UtilConsulta.longValueOf(fila[i++]));
                registro.setAlternoVariaHorarioCupos(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setAlternoVariaHorarioUtilizacionEstandar(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setCompletaTotalEstudiantes(UtilConsulta.longValueOf(fila[i++]));
                registro.setCompletaTotalCupos(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setCompletaTotalUtilizacionEstandar(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setDobleMañanaTardeTotalEstudiantes(UtilConsulta.longValueOf(fila[i++]));
                registro.setDobleMañanaTardeCupos(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setDobleMañanaTardeUtilizacionEstandar(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setHorarioAmpliadoTotalEstudiantes(UtilConsulta.longValueOf(fila[i++]));
                registro.setHorarioAmpliadoCupos(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setHorarioAmpliadoUtilizacionEstandar(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setMañanaTotalEstudiantes(UtilConsulta.longValueOf(fila[i++]));
                registro.setMañanaCupos(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setMañanaUtilizacionEstandar(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setNocturnaTotalEstudiantes(UtilConsulta.longValueOf(fila[i++]));
                registro.setNocturnaCupos(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setNocturnaUtilizacionEstandar(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setTardeTotalEstudiantes(UtilConsulta.longValueOf(fila[i++]));
                registro.setTardeCupos(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setTardeUtilizacionEstandar(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setTripleJornadaTotalEstudiantes(UtilConsulta.longValueOf(fila[i++]));
                registro.setTripleJornadaCupos(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setTripleJornadaUtilizacionEstandar(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setVespertinaTotalEstudiantes(UtilConsulta.longValueOf(fila[i++]));
                registro.setVespertinaCupos(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setVespertinaUtilizacionEstandar(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setOtraTotalEstudiantes(UtilConsulta.longValueOf(fila[i++]));
                registro.setOtraCupos(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setOtraUtilizacionEstandar(UtilConsulta.doubleValueOf(fila[i++]));
                // otros indicadores
                registro.setUtilEstandarTotal(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setCuposTotal(UtilConsulta.doubleValueOf(fila[i++]));
                // totales
                totalConsulta.addAlternoVariaHorarioTotalEstudiantes(registro.getAlternoVariaHorarioTotalEstudiantes());
                totalConsulta.addAlternoVariaHorarioCupos(registro.getAlternoVariaHorarioCupos());
                totalConsulta.addAlternoVariaHorarioUtilizacionEstandar(registro.getAlternoVariaHorarioUtilizacionEstandar());
                totalConsulta.addCompletaTotalEstudiantes(registro.getCompletaTotalEstudiantes());
                totalConsulta.addCompletaTotalCupos(registro.getCompletaTotalCupos());
                totalConsulta.addCompletaTotalUtilizacionEstandar(registro.getCompletaTotalUtilizacionEstandar());
                totalConsulta.addDobleMañanaTardeTotalEstudiantes(registro.getDobleMañanaTardeTotalEstudiantes());
                totalConsulta.addDobleMañanaTardeCupos(registro.getDobleMañanaTardeCupos());
                totalConsulta.addDobleMañanaTardeUtilizacionEstandar(registro.getDobleMañanaTardeUtilizacionEstandar());
                totalConsulta.addHorarioAmpliadoTotalEstudiantes(registro.getHorarioAmpliadoTotalEstudiantes());
                totalConsulta.addHorarioAmpliadoCupos(registro.getHorarioAmpliadoCupos());
                totalConsulta.addHorarioAmpliadoUtilizacionEstandar(registro.getHorarioAmpliadoUtilizacionEstandar());
                totalConsulta.addMañanaTotalEstudiantes(registro.getMañanaTotalEstudiantes());
                totalConsulta.addMañanaCupos(registro.getMañanaCupos());
                totalConsulta.addMañanaUtilizacionEstandar(registro.getMañanaUtilizacionEstandar());
                totalConsulta.addNocturnaTotalEstudiantes(registro.getNocturnaTotalEstudiantes());
                totalConsulta.addNocturnaCupos(registro.getNocturnaCupos());
                totalConsulta.addNocturnaUtilizacionEstandar(registro.getNocturnaUtilizacionEstandar());
                totalConsulta.addTardeTotalEstudiantes(registro.getTardeTotalEstudiantes());
                totalConsulta.addTardeCupos(registro.getTardeCupos());
                totalConsulta.addTardeUtilizacionEstandar(registro.getTardeUtilizacionEstandar());
                totalConsulta.addTripleJornadaTotalEstudiantes(registro.getTripleJornadaTotalEstudiantes());
                totalConsulta.addTripleJornadaCupos(registro.getTripleJornadaCupos());
                totalConsulta.addTripleJornadaUtilizacionEstandar(registro.getTripleJornadaUtilizacionEstandar());
                totalConsulta.addVespertinaTotalEstudiantes(registro.getVespertinaTotalEstudiantes());
                totalConsulta.addVespertinaCupos(registro.getVespertinaCupos());
                totalConsulta.addVespertinaUtilizacionEstandar(registro.getVespertinaUtilizacionEstandar());
                totalConsulta.addOtraTotalEstudiantes(registro.getOtraTotalEstudiantes());
                totalConsulta.addOtraCupos(registro.getOtraCupos());
                totalConsulta.addOtraUtilizacionEstandar(registro.getOtraUtilizacionEstandar());
                // otros totales
                totalConsulta.addCuposTotal(registro.getCuposTotal());
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
                registro.setNumPrediosSaturados(UtilConsulta.longValueOf(fila[i++]));
                registro.setNumPrediosSudUtilizados(UtilConsulta.longValueOf(fila[i++]));
                registro.setUtilEstandarTotal(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setCuposTotal(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setNumTotalPredios(UtilConsulta.longValueOf(fila[i++]));
                // totales
                totalConsulta.addPrediosSaturados(registro.getNumPrediosSaturados());
                totalConsulta.addPrediosSubUtilizados(registro.getNumPrediosSudUtilizados());
                totalConsulta.addCuposTotal(registro.getCuposTotal());
                totalConsulta.addTotalPredios(registro.getNumTotalPredios());
            }
            lista.add(registro);
        }
    }

    @Override
    public void resetearFiltros() {
        consultaAmbito.resetearNiveles();
        this.resetearListasEstSedesPredios();
        this.seleccionSector = null;
        this.seleccionZona = null;        
        this.resetearConsulta();
        consultaAmbito.nivelDPAOfAccesoUsuario();
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
        this.resetearConsulta();
        //consultaAmbito.nivelDPAOfAccesoUsuario();
    }

    @Override
    public void cambioAgrupamiento() {
        this.resetearConsulta();
    }

    private void resetearConsulta() {
        // columnas
        this.cargarCamposOpcionales();
        // resultados
        this.lista.clear();
        this.totalConsulta.inicializar();
    }

    public boolean isEstableUtilizacionEstandar(Object valor) {
        if (valor != null) {
            return (((Double) valor).compareTo(100D) == 0) ? true : false;
        } else {
            return false;
        }
    }

    public boolean isCumpleUtilizacionEstandar(Object valor) {
        if (valor != null) {
            return (((Double) valor).compareTo(100D) < 0) ? true : false;
        } else {
            return false;
        }
    }

    public boolean isNoCumpleUtilizacionEstandar(Object valor) {
        if (valor != null) {
            return (((Double) valor).compareTo(100D) > 0) ? true : false;
        } else {
            return false;
        }
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
        private String propiedadPredio;
        private String zona;
        private String sector;
        private String clima;
        private String etnias;
        private Long alternoVariaHorarioTotalEstudiantes;
        private Double alternoVariaHorarioCupos;
        private Double alternoVariaHorarioUtilizacionEstandar;
        private Long completaTotalEstudiantes;
        private Double completaTotalCupos;
        private Double completaTotalUtilizacionEstandar;
        private Long dobleMañanaTardeTotalEstudiantes;
        private Double dobleMañanaTardeCupos;
        private Double dobleMañanaTardeUtilizacionEstandar;
        private Long horarioAmpliadoTotalEstudiantes;
        private Double horarioAmpliadoCupos;
        private Double horarioAmpliadoUtilizacionEstandar;
        private Long mañanaTotalEstudiantes;
        private Double mañanaCupos;
        private Double mañanaUtilizacionEstandar;
        private Long nocturnaTotalEstudiantes;
        private Double nocturnaCupos;
        private Double nocturnaUtilizacionEstandar;
        private Long tardeTotalEstudiantes;
        private Double tardeCupos;
        private Double tardeUtilizacionEstandar;
        private Long tripleJornadaTotalEstudiantes;
        private Double tripleJornadaCupos;
        private Double tripleJornadaUtilizacionEstandar;
        private Long vespertinaTotalEstudiantes;
        private Double vespertinaCupos;
        private Double vespertinaUtilizacionEstandar;
        private Long otraTotalEstudiantes;
        private Double otraCupos;
        private Double otraUtilizacionEstandar;
        private Double cuposTotal;
        private Double utilEstandarTotal;
        private Long numPrediosSaturados;
        private Long numPrediosSudUtilizados;
        private Long numTotalPredios;

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

        public Long getAlternoVariaHorarioTotalEstudiantes() {
            return alternoVariaHorarioTotalEstudiantes;
        }

        public void setAlternoVariaHorarioTotalEstudiantes(Long alternoVariaHorarioTotalEstudiantes) {
            this.alternoVariaHorarioTotalEstudiantes = alternoVariaHorarioTotalEstudiantes;
        }

        public Double getAlternoVariaHorarioCupos() {
            return alternoVariaHorarioCupos;
        }

        public void setAlternoVariaHorarioCupos(Double alternoVariaHorarioCupos) {
            this.alternoVariaHorarioCupos = alternoVariaHorarioCupos;
        }

        public Double getAlternoVariaHorarioUtilizacionEstandar() {
            return alternoVariaHorarioUtilizacionEstandar;
        }

        public void setAlternoVariaHorarioUtilizacionEstandar(Double alternoVariaHorarioUtilizacionEstandar) {
            this.alternoVariaHorarioUtilizacionEstandar = alternoVariaHorarioUtilizacionEstandar;
        }

        public Long getCompletaTotalEstudiantes() {
            return completaTotalEstudiantes;
        }

        public void setCompletaTotalEstudiantes(Long completaTotalEstudiantes) {
            this.completaTotalEstudiantes = completaTotalEstudiantes;
        }

        public Double getCompletaTotalCupos() {
            return completaTotalCupos;
        }

        public void setCompletaTotalCupos(Double completaTotalCupos) {
            this.completaTotalCupos = completaTotalCupos;
        }

        public Double getCompletaTotalUtilizacionEstandar() {
            return completaTotalUtilizacionEstandar;
        }

        public void setCompletaTotalUtilizacionEstandar(Double completaTotalUtilizacionEstandar) {
            this.completaTotalUtilizacionEstandar = completaTotalUtilizacionEstandar;
        }

        public Long getDobleMañanaTardeTotalEstudiantes() {
            return dobleMañanaTardeTotalEstudiantes;
        }

        public void setDobleMañanaTardeTotalEstudiantes(Long dobleMañanaTardeTotalEstudiantes) {
            this.dobleMañanaTardeTotalEstudiantes = dobleMañanaTardeTotalEstudiantes;
        }

        public Double getDobleMañanaTardeCupos() {
            return dobleMañanaTardeCupos;
        }

        public void setDobleMañanaTardeCupos(Double dobleMañanaTardeCupos) {
            this.dobleMañanaTardeCupos = dobleMañanaTardeCupos;
        }

        public Double getDobleMañanaTardeUtilizacionEstandar() {
            return dobleMañanaTardeUtilizacionEstandar;
        }

        public void setDobleMañanaTardeUtilizacionEstandar(Double dobleMañanaTardeUtilizacionEstandar) {
            this.dobleMañanaTardeUtilizacionEstandar = dobleMañanaTardeUtilizacionEstandar;
        }

        public Long getHorarioAmpliadoTotalEstudiantes() {
            return horarioAmpliadoTotalEstudiantes;
        }

        public void setHorarioAmpliadoTotalEstudiantes(Long horarioAmpliadoTotalEstudiantes) {
            this.horarioAmpliadoTotalEstudiantes = horarioAmpliadoTotalEstudiantes;
        }

        public Double getHorarioAmpliadoCupos() {
            return horarioAmpliadoCupos;
        }

        public void setHorarioAmpliadoCupos(Double horarioAmpliadoCupos) {
            this.horarioAmpliadoCupos = horarioAmpliadoCupos;
        }

        public Double getHorarioAmpliadoUtilizacionEstandar() {
            return horarioAmpliadoUtilizacionEstandar;
        }

        public void setHorarioAmpliadoUtilizacionEstandar(Double horarioAmpliadoUtilizacionEstandar) {
            this.horarioAmpliadoUtilizacionEstandar = horarioAmpliadoUtilizacionEstandar;
        }

        public Long getMañanaTotalEstudiantes() {
            return mañanaTotalEstudiantes;
        }

        public void setMañanaTotalEstudiantes(Long mañanaTotalEstudiantes) {
            this.mañanaTotalEstudiantes = mañanaTotalEstudiantes;
        }

        public Double getMañanaCupos() {
            return mañanaCupos;
        }

        public void setMañanaCupos(Double mañanaCupos) {
            this.mañanaCupos = mañanaCupos;
        }

        public Double getMañanaUtilizacionEstandar() {
            return mañanaUtilizacionEstandar;
        }

        public void setMañanaUtilizacionEstandar(Double mañanaUtilizacionEstandar) {
            this.mañanaUtilizacionEstandar = mañanaUtilizacionEstandar;
        }

        public Long getNocturnaTotalEstudiantes() {
            return nocturnaTotalEstudiantes;
        }

        public void setNocturnaTotalEstudiantes(Long nocturnaTotalEstudiantes) {
            this.nocturnaTotalEstudiantes = nocturnaTotalEstudiantes;
        }

        public Double getNocturnaCupos() {
            return nocturnaCupos;
        }

        public void setNocturnaCupos(Double nocturnaCupos) {
            this.nocturnaCupos = nocturnaCupos;
        }

        public Double getNocturnaUtilizacionEstandar() {
            return nocturnaUtilizacionEstandar;
        }

        public void setNocturnaUtilizacionEstandar(Double nocturnaUtilizacionEstandar) {
            this.nocturnaUtilizacionEstandar = nocturnaUtilizacionEstandar;
        }

        public Long getTardeTotalEstudiantes() {
            return tardeTotalEstudiantes;
        }

        public void setTardeTotalEstudiantes(Long tardeTotalEstudiantes) {
            this.tardeTotalEstudiantes = tardeTotalEstudiantes;
        }

        public Double getTardeCupos() {
            return tardeCupos;
        }

        public void setTardeCupos(Double tardeCupos) {
            this.tardeCupos = tardeCupos;
        }

        public Double getTardeUtilizacionEstandar() {
            return tardeUtilizacionEstandar;
        }

        public void setTardeUtilizacionEstandar(Double tardeUtilizacionEstandar) {
            this.tardeUtilizacionEstandar = tardeUtilizacionEstandar;
        }

        public Long getTripleJornadaTotalEstudiantes() {
            return tripleJornadaTotalEstudiantes;
        }

        public void setTripleJornadaTotalEstudiantes(Long tripleJornadaTotalEstudiantes) {
            this.tripleJornadaTotalEstudiantes = tripleJornadaTotalEstudiantes;
        }

        public Double getTripleJornadaCupos() {
            return tripleJornadaCupos;
        }

        public void setTripleJornadaCupos(Double tripleJornadaCupos) {
            this.tripleJornadaCupos = tripleJornadaCupos;
        }

        public Double getTripleJornadaUtilizacionEstandar() {
            return tripleJornadaUtilizacionEstandar;
        }

        public void setTripleJornadaUtilizacionEstandar(Double tripleJornadaUtilizacionEstandar) {
            this.tripleJornadaUtilizacionEstandar = tripleJornadaUtilizacionEstandar;
        }

        public Long getVespertinaTotalEstudiantes() {
            return vespertinaTotalEstudiantes;
        }

        public void setVespertinaTotalEstudiantes(Long vespertinaTotalEstudiantes) {
            this.vespertinaTotalEstudiantes = vespertinaTotalEstudiantes;
        }

        public Double getVespertinaCupos() {
            return vespertinaCupos;
        }

        public void setVespertinaCupos(Double vespertinaCupos) {
            this.vespertinaCupos = vespertinaCupos;
        }

        public Double getVespertinaUtilizacionEstandar() {
            return vespertinaUtilizacionEstandar;
        }

        public void setVespertinaUtilizacionEstandar(Double vespertinaUtilizacionEstandar) {
            this.vespertinaUtilizacionEstandar = vespertinaUtilizacionEstandar;
        }

        public Long getOtraTotalEstudiantes() {
            return otraTotalEstudiantes;
        }

        public void setOtraTotalEstudiantes(Long otraTotalEstudiantes) {
            this.otraTotalEstudiantes = otraTotalEstudiantes;
        }

        public Double getOtraCupos() {
            return otraCupos;
        }

        public void setOtraCupos(Double otraCupos) {
            this.otraCupos = otraCupos;
        }

        public Double getOtraUtilizacionEstandar() {
            return otraUtilizacionEstandar;
        }

        public void setOtraUtilizacionEstandar(Double otraUtilizacionEstandar) {
            this.otraUtilizacionEstandar = otraUtilizacionEstandar;
        }

        public Double getCuposTotal() {
            return cuposTotal;
        }

        public void setCuposTotal(Double cuposTotal) {
            this.cuposTotal = cuposTotal;
        }

        public Double getUtilEstandarTotal() {
            return utilEstandarTotal;
        }

        public void setUtilEstandarTotal(Double utilEstandarTotal) {
            this.utilEstandarTotal = utilEstandarTotal;
        }

        public Long getNumPrediosSaturados() {
            return numPrediosSaturados;
        }

        public void setNumPrediosSaturados(Long numPrediosSaturados) {
            this.numPrediosSaturados = numPrediosSaturados;
        }

        public Long getNumPrediosSudUtilizados() {
            return numPrediosSudUtilizados;
        }

        public void setNumPrediosSudUtilizados(Long numPrediosSudUtilizados) {
            this.numPrediosSudUtilizados = numPrediosSudUtilizados;
        }

        public Long getNumTotalPredios() {
            return numTotalPredios;
        }

        public void setNumTotalPredios(Long numTotalPredios) {
            this.numTotalPredios = numTotalPredios;
        }        
    }

    public class TotalConsulta {

        private Long alternoVariaHorarioTotalEstudiantes = 0L;
        private Double alternoVariaHorarioCupos = 0D;
        private Double alternoVariaHorarioUtilizacionEstandar = 0D;
        private Long completaTotalEstudiantes = 0L;
        private Double completaTotalCupos = 0D;
        private Double completaTotalUtilizacionEstandar = 0D;
        private Long dobleMañanaTardeTotalEstudiantes = 0L;
        private Double dobleMañanaTardeCupos = 0D;
        private Double dobleMañanaTardeUtilizacionEstandar = 0D;
        private Long horarioAmpliadoTotalEstudiantes = 0L;
        private Double horarioAmpliadoCupos = 0D;
        private Double horarioAmpliadoUtilizacionEstandar = 0D;
        private Long mañanaTotalEstudiantes = 0L;
        private Double mañanaCupos = 0D;
        private Double mañanaUtilizacionEstandar = 0D;
        private Long nocturnaTotalEstudiantes = 0L;
        private Double nocturnaCupos = 0D;
        private Double nocturnaUtilizacionEstandar = 0D;
        private Long tardeTotalEstudiantes = 0L;
        private Double tardeCupos = 0D;
        private Double tardeUtilizacionEstandar = 0D;
        private Long tripleJornadaTotalEstudiantes = 0L;
        private Double tripleJornadaCupos = 0D;
        private Double tripleJornadaUtilizacionEstandar = 0D;
        private Long vespertinaTotalEstudiantes = 0L;
        private Double vespertinaCupos = 0D;
        private Double vespertinaUtilizacionEstandar = 0D;
        private Long otraTotalEstudiantes = 0L;
        private Double otraCupos = 0D;
        private Double otraUtilizacionEstandar = 0D;
        private Long prediosSaturados = 0L;
        private Long prediosSubUtilizados = 0L;
        private Double cuposTotal = 0D;
        private Long totalPredios = 0L;

        public void inicializar() {
            alternoVariaHorarioTotalEstudiantes = 0L;
            alternoVariaHorarioCupos = 0D;
            alternoVariaHorarioUtilizacionEstandar = 0D;
            completaTotalEstudiantes = 0L;
            completaTotalCupos = 0D;
            completaTotalUtilizacionEstandar = 0D;
            dobleMañanaTardeTotalEstudiantes = 0L;
            dobleMañanaTardeCupos = 0D;
            dobleMañanaTardeUtilizacionEstandar = 0D;
            horarioAmpliadoTotalEstudiantes = 0L;
            horarioAmpliadoCupos = 0D;
            horarioAmpliadoUtilizacionEstandar = 0D;
            mañanaTotalEstudiantes = 0L;
            mañanaCupos = 0D;
            mañanaUtilizacionEstandar = 0D;
            nocturnaTotalEstudiantes = 0L;
            nocturnaCupos = 0D;
            nocturnaUtilizacionEstandar = 0D;
            tardeTotalEstudiantes = 0L;
            tardeCupos = 0D;
            tardeUtilizacionEstandar = 0D;
            tripleJornadaTotalEstudiantes = 0L;
            tripleJornadaCupos = 0D;
            tripleJornadaUtilizacionEstandar = 0D;
            vespertinaTotalEstudiantes = 0L;
            vespertinaCupos = 0D;
            vespertinaUtilizacionEstandar = 0D;
            otraTotalEstudiantes = 0L;
            otraCupos = 0D;
            otraUtilizacionEstandar = 0D;
            prediosSaturados = 0L;
            prediosSubUtilizados = 0L;
            cuposTotal = 0D;
            totalPredios = 0L;
        }

        public Long getAlternoVariaHorarioTotalEstudiantes() {
            return alternoVariaHorarioTotalEstudiantes;
        }

        public void setAlternoVariaHorarioTotalEstudiante(Long alternoVariaHorarioTotalEstudiantes) {
            this.alternoVariaHorarioTotalEstudiantes = alternoVariaHorarioTotalEstudiantes;
        }

        public Double getAlternoVariaHorarioCupos() {
            return alternoVariaHorarioCupos;
        }

        public void setAlternoVariaHorarioCupos(Double alternoVariaHorarioCupos) {
            this.alternoVariaHorarioCupos = alternoVariaHorarioCupos;
        }

        public Double getAlternoVariaHorarioUtilizacionEstandar() {
            return alternoVariaHorarioUtilizacionEstandar;
        }

        public void setAlternoVariaHorarioUtilizacionEstandar(Double alternoVariaHorarioUtilizacionEstandar) {
            this.alternoVariaHorarioUtilizacionEstandar = alternoVariaHorarioUtilizacionEstandar;
        }

        public Long getCompletaTotalEstudiantes() {
            return completaTotalEstudiantes;
        }

        public void setCompletaTotalEstudiantes(Long completaTotalEstudiantes) {
            this.completaTotalEstudiantes = completaTotalEstudiantes;
        }

        public Double getCompletaTotalCupos() {
            return completaTotalCupos;
        }

        public void setCompletaTotalCupos(Double completaTotalCupos) {
            this.completaTotalCupos = completaTotalCupos;
        }

        public Double getCompletaTotalUtilizacionEstandar() {
            return completaTotalUtilizacionEstandar;
        }

        public void setCompletaTotalUtilizacionEstandar(Double completaTotalUtilizacionEstandar) {
            this.completaTotalUtilizacionEstandar = completaTotalUtilizacionEstandar;
        }

        public Long getDobleMañanaTardeTotalEstudiantes() {
            return dobleMañanaTardeTotalEstudiantes;
        }

        public void setDobleMañanaTardeTotalEstudiantes(Long dobleMañanaTardeTotalEstudiantes) {
            this.dobleMañanaTardeTotalEstudiantes = dobleMañanaTardeTotalEstudiantes;
        }

        public Double getDobleMañanaTardeCupos() {
            return dobleMañanaTardeCupos;
        }

        public void setDobleMañanaTardeCupos(Double dobleMañanaTardeCupos) {
            this.dobleMañanaTardeCupos = dobleMañanaTardeCupos;
        }

        public Double getDobleMañanaTardeUtilizacionEstandar() {
            return dobleMañanaTardeUtilizacionEstandar;
        }

        public void setDobleMañanaTardeUtilizacionEstandar(Double dobleMañanaTardeUtilizacionEstandar) {
            this.dobleMañanaTardeUtilizacionEstandar = dobleMañanaTardeUtilizacionEstandar;
        }

        public Long getHorarioAmpliadoTotalEstudiantes() {
            return horarioAmpliadoTotalEstudiantes;
        }

        public void setHorarioAmpliadoTotalEstudiantes(Long horarioAmpliadoTotalEstudiantes) {
            this.horarioAmpliadoTotalEstudiantes = horarioAmpliadoTotalEstudiantes;
        }

        public Double getHorarioAmpliadoCupos() {
            return horarioAmpliadoCupos;
        }

        public void setHorarioAmpliadoCupos(Double horarioAmpliadoCupos) {
            this.horarioAmpliadoCupos = horarioAmpliadoCupos;
        }

        public Double getHorarioAmpliadoUtilizacionEstandar() {
            return horarioAmpliadoUtilizacionEstandar;
        }

        public void setHorarioAmpliadoUtilizacionEstandar(Double horarioAmpliadoUtilizacionEstandar) {
            this.horarioAmpliadoUtilizacionEstandar = horarioAmpliadoUtilizacionEstandar;
        }

        public Long getMañanaTotalEstudiantes() {
            return mañanaTotalEstudiantes;
        }

        public void setMañanaTotalEstudiantes(Long mañanaTotalEstudiantes) {
            this.mañanaTotalEstudiantes = mañanaTotalEstudiantes;
        }

        public Double getMañanaCupos() {
            return mañanaCupos;
        }

        public void setMañanaCupos(Double mañanaCupos) {
            this.mañanaCupos = mañanaCupos;
        }

        public Double getMañanaUtilizacionEstandar() {
            return mañanaUtilizacionEstandar;
        }

        public void setMañanaUtilizacionEstandar(Double mañanaUtilizacionEstandar) {
            this.mañanaUtilizacionEstandar = mañanaUtilizacionEstandar;
        }

        public Long getNocturnaTotalEstudiantes() {
            return nocturnaTotalEstudiantes;
        }

        public void setNocturnaTotalEstudiantes(Long nocturnaTotalEstudiantes) {
            this.nocturnaTotalEstudiantes = nocturnaTotalEstudiantes;
        }

        public Double getNocturnaCupos() {
            return nocturnaCupos;
        }

        public void setNocturnaCupos(Double nocturnaCupos) {
            this.nocturnaCupos = nocturnaCupos;
        }

        public Double getNocturnaUtilizacionEstandar() {
            return nocturnaUtilizacionEstandar;
        }

        public void setNocturnaUtilizacionEstandar(Double nocturnaUtilizacionEstandar) {
            this.nocturnaUtilizacionEstandar = nocturnaUtilizacionEstandar;
        }

        public Long getTardeTotalEstudiantes() {
            return tardeTotalEstudiantes;
        }

        public void setTardeTotalEstudiantes(Long tardeTotalEstudiantes) {
            this.tardeTotalEstudiantes = tardeTotalEstudiantes;
        }

        public Double getTardeCupos() {
            return tardeCupos;
        }

        public void setTardeCupos(Double tardeCupos) {
            this.tardeCupos = tardeCupos;
        }

        public Double getTardeUtilizacionEstandar() {
            return tardeUtilizacionEstandar;
        }

        public void setTardeUtilizacionEstandar(Double tardeUtilizacionEstandar) {
            this.tardeUtilizacionEstandar = tardeUtilizacionEstandar;
        }

        public Long getTripleJornadaTotalEstudiantes() {
            return tripleJornadaTotalEstudiantes;
        }

        public void setTripleJornadaTotalEstudiantes(Long tripleJornadaTotalEstudiantes) {
            this.tripleJornadaTotalEstudiantes = tripleJornadaTotalEstudiantes;
        }

        public Double getTripleJornadaCupos() {
            return tripleJornadaCupos;
        }

        public void setTripleJornadaCupos(Double tripleJornadaCupos) {
            this.tripleJornadaCupos = tripleJornadaCupos;
        }

        public Double getTripleJornadaUtilizacionEstandar() {
            return tripleJornadaUtilizacionEstandar;
        }

        public void setTripleJornadaUtilizacionEstandar(Double tripleJornadaUtilizacionEstandar) {
            this.tripleJornadaUtilizacionEstandar = tripleJornadaUtilizacionEstandar;
        }

        public Long getVespertinaTotalEstudiantes() {
            return vespertinaTotalEstudiantes;
        }

        public void setVespertinaTotalEstudiantes(Long vespertinaTotalEstudiantes) {
            this.vespertinaTotalEstudiantes = vespertinaTotalEstudiantes;
        }

        public Double getVespertinaCupos() {
            return vespertinaCupos;
        }

        public void setVespertinaCupos(Double vespertinaCupos) {
            this.vespertinaCupos = vespertinaCupos;
        }

        public Double getVespertinaUtilizacionEstandar() {
            return vespertinaUtilizacionEstandar;
        }

        public void setVespertinaUtilizacionEstandar(Double vespertinaUtilizacionEstandar) {
            this.vespertinaUtilizacionEstandar = vespertinaUtilizacionEstandar;
        }

        public Long getOtraTotalEstudiantes() {
            return otraTotalEstudiantes;
        }

        public void setOtraTotalEstudiantes(Long otraTotalEstudiantes) {
            this.otraTotalEstudiantes = otraTotalEstudiantes;
        }

        public Double getOtraCupos() {
            return otraCupos;
        }

        public void setOtraCupos(Double otraCupos) {
            this.otraCupos = otraCupos;
        }

        public Double getOtraUtilizacionEstandar() {
            return otraUtilizacionEstandar;
        }

        public void setOtraUtilizacionEstandar(Double otraUtilizacionEstandar) {
            this.otraUtilizacionEstandar = otraUtilizacionEstandar;
        }

        public Long getPrediosSaturados() {
            return prediosSaturados;
        }

        public void setPrediosSaturados(Long prediosSaturados) {
            this.prediosSaturados = prediosSaturados;
        }

        public Long getPrediosSubUtilizados() {
            return prediosSubUtilizados;
        }

        public void setPrediosSubUtilizados(Long prediosSubUtilizados) {
            this.prediosSubUtilizados = prediosSubUtilizados;
        }

        public Double getCuposTotal() {
            return cuposTotal;
        }

        public void setCuposTotal(Double cuposTotal) {
            this.cuposTotal = cuposTotal;
        }

        public Long getTotalPredios() {
            return totalPredios;
        }

        public void setTotalPredios(Long totalPredios) {
            this.totalPredios = totalPredios;
        }
        
        public void addPrediosSaturados(Long valor) {
            if (valor != null) {
                this.prediosSaturados += valor;
            }
        }

        public void addPrediosSubUtilizados(Long valor) {
            if (valor != null) {
                this.prediosSubUtilizados += valor;
            }
        }

        public void addCuposTotal(Double valor) {
            if (valor != null) {
                this.cuposTotal += valor;
            }
        }

        public void addAlternoVariaHorarioTotalEstudiantes(Long valor) {
            if (valor != null) {
                this.alternoVariaHorarioTotalEstudiantes += valor;
            }
        }

        public void addAlternoVariaHorarioCupos(Double valor) {
            if (valor != null) {
                this.alternoVariaHorarioCupos += valor;
            }
        }

        public void addAlternoVariaHorarioUtilizacionEstandar(Double valor) {
            if (valor != null) {
                this.alternoVariaHorarioUtilizacionEstandar += valor;
            }
        }

        public void addCompletaTotalEstudiantes(Long valor) {
            if (valor != null) {
                this.completaTotalEstudiantes += valor;
            }
        }

        public void addCompletaTotalCupos(Double valor) {
            if (valor != null) {
                this.completaTotalCupos += valor;
            }
        }

        public void addCompletaTotalUtilizacionEstandar(Double valor) {
            if (valor != null) {
                this.completaTotalUtilizacionEstandar += valor;
            }
        }

        public void addDobleMañanaTardeTotalEstudiantes(Long valor) {
            if (valor != null) {
                this.dobleMañanaTardeTotalEstudiantes += valor;
            }
        }

        public void addDobleMañanaTardeCupos(Double valor) {
            if (valor != null) {
                this.dobleMañanaTardeCupos += valor;
            }
        }

        public void addDobleMañanaTardeUtilizacionEstandar(Double valor) {
            if (valor != null) {
                this.dobleMañanaTardeUtilizacionEstandar += valor;
            }
        }

        public void addHorarioAmpliadoTotalEstudiantes(Long valor) {
            if (valor != null) {
                this.horarioAmpliadoTotalEstudiantes += valor;
            }
        }

        public void addHorarioAmpliadoCupos(Double valor) {
            if (valor != null) {
                this.horarioAmpliadoCupos += valor;
            }
        }

        public void addHorarioAmpliadoUtilizacionEstandar(Double valor) {
            if (valor != null) {
                this.horarioAmpliadoUtilizacionEstandar += valor;
            }
        }

        public void addMañanaTotalEstudiantes(Long valor) {
            if (valor != null) {
                this.mañanaTotalEstudiantes += valor;
            }
        }

        public void addMañanaCupos(Double valor) {
            if (valor != null) {
                this.mañanaCupos += valor;
            }
        }

        public void addMañanaUtilizacionEstandar(Double valor) {
            if (valor != null) {
                this.mañanaUtilizacionEstandar += valor;
            }
        }

        public void addNocturnaTotalEstudiantes(Long valor) {
            if (valor != null) {
                this.nocturnaTotalEstudiantes += valor;
            }
        }

        public void addNocturnaCupos(Double valor) {
            if (valor != null) {
                this.nocturnaCupos += valor;
            }
        }

        public void addNocturnaUtilizacionEstandar(Double valor) {
            if (valor != null) {
                this.nocturnaUtilizacionEstandar += valor;
            }
        }

        public void addTardeTotalEstudiantes(Long valor) {
            if (valor != null) {
                this.tardeTotalEstudiantes += valor;
            }
        }

        public void addTardeCupos(Double valor) {
            if (valor != null) {
                this.tardeCupos += valor;
            }
        }

        public void addTardeUtilizacionEstandar(Double valor) {
            if (valor != null) {
                this.tardeUtilizacionEstandar += valor;
            }
        }

        public void addTripleJornadaTotalEstudiantes(Long valor) {
            if (valor != null) {
                this.tripleJornadaTotalEstudiantes += valor;
            }
        }

        public void addTripleJornadaCupos(Double valor) {
            if (valor != null) {
                this.tripleJornadaCupos += valor;
            }
        }

        public void addTripleJornadaUtilizacionEstandar(Double valor) {
            if (valor != null) {
                this.tripleJornadaUtilizacionEstandar += valor;
            }
        }

        public void addVespertinaTotalEstudiantes(Long valor) {
            if (valor != null) {
                this.vespertinaTotalEstudiantes += valor;
            }
        }

        public void addVespertinaCupos(Double valor) {
            if (valor != null) {
                this.vespertinaCupos += valor;
            }
        }

        public void addVespertinaUtilizacionEstandar(Double valor) {
            if (valor != null) {
                this.vespertinaUtilizacionEstandar += valor;
            }
        }

        public void addOtraTotalEstudiantes(Long valor) {
            if (valor != null) {
                this.otraTotalEstudiantes += valor;
            }
        }

        public void addOtraCupos(Double valor) {
            if (valor != null) {
                this.otraCupos += valor;
            }
        }

        public void addOtraUtilizacionEstandar(Double valor) {
            if (valor != null) {
                this.otraUtilizacionEstandar += valor;
            }
        }
        
        public void addTotalPredios(Long valor) {
            if (valor != null) {
                this.totalPredios += valor;
            }
        }
    }
    // mostrar columnas    
    // indicadores  

    public boolean isMostrarColJornada() {
        return isAgrupamientoPredio();
    }

    public boolean isMostrarColCuposTotal() {
        return true;
    }

    public boolean isMostrarColUtilEstandarTotal() {
        return true;
    }

    public boolean isMostrarColNumPrediosSaturados() {
        return !isAgrupamientoPredio();
    }

    public boolean isMostrarColNumPrediosSudUtilizados() {
        return !isAgrupamientoPredio();
    }
    
    public boolean isMostrarColTotalPredios() {
        return !isAgrupamientoPredio();
    }

    // columnas
    public String getColumnaNumPredioSaturados() {
        return columnaNumPredioSaturados;
    }

    public String getColumnaNumPredioSubutilizados() {
        return columnaNumPredioSubutilizados;
    }

    public String getColumnaUtilizacionTotal() {
        return columnaUtilizacionTotal;
    }

    public String getColumnaCuposTotal() {
        return columnaCuposTotal;
    }

    public FiltroAmbitoRedistribucion getFiltro() {
        return filtro;
    }

    public String getColumnaAlternoTotalEst() {
        return columnaAlternoTotalEst;
    }

    public String getColumnaAlternoTotalCupos() {
        return columnaAlternoTotalCupos;
    }

    public String getColumnaAlternoUtilEstandar() {
        return columnaAlternoUtilEstandar;
    }

    public String getColumnaCompletaTotalEst() {
        return columnaCompletaTotalEst;
    }

    public String getColumnaCompletaTotalCupos() {
        return columnaCompletaTotalCupos;
    }

    public String getColumnaCompletaUtilEstandar() {
        return columnaCompletaUtilEstandar;
    }

    public String getColumnaDobleTotalEst() {
        return columnaDobleTotalEst;
    }

    public String getColumnaDobleTotalCupos() {
        return columnaDobleTotalCupos;
    }

    public String getColumnaDobleUtilEstandar() {
        return columnaDobleUtilEstandar;
    }

    public String getColumnaHorarioTotalEst() {
        return columnaHorarioTotalEst;
    }

    public String getColumnaHorarioTotalCupos() {
        return columnaHorarioTotalCupos;
    }

    public String getColumnaHorarioUtilEstandar() {
        return columnaHorarioUtilEstandar;
    }

    public String getColumnaMañanaTotalEst() {
        return columnaMañanaTotalEst;
    }

    public String getColumnaMañanaTotalCupos() {
        return columnaMañanaTotalCupos;
    }

    public String getColumnaMañanaUtilEstandar() {
        return columnaMañanaUtilEstandar;
    }

    public String getColumnaNocturnaTotalEst() {
        return columnaNocturnaTotalEst;
    }

    public String getColumnaNocturnaTotalCupos() {
        return columnaNocturnaTotalCupos;
    }

    public String getColumnaNocturnaUtilEstandar() {
        return columnaNocturnaUtilEstandar;
    }

    public String getColumnaTardeTotalEst() {
        return columnaTardeTotalEst;
    }

    public String getColumnaTardeTotalCupos() {
        return columnaTardeTotalCupos;
    }

    public String getColumnaTardeUtilEstandar() {
        return columnaTardeUtilEstandar;
    }

    public String getColumnaTripleTotalEst() {
        return columnaTripleTotalEst;
    }

    public String getColumnaTripleTotalCupos() {
        return columnaTripleTotalCupos;
    }

    public String getColumnaTripleUtilEstandar() {
        return columnaTripleUtilEstandar;
    }

    public String getColumnaVespertinaTotalEst() {
        return columnaVespertinaTotalEst;
    }

    public String getColumnaVespertinaTotalCupos() {
        return columnaVespertinaTotalCupos;
    }

    public String getColumnaVespertinaUtilEstandar() {
        return columnaVespertinaUtilEstandar;
    }

    public String getColumnaOtraTotalEst() {
        return columnaOtraTotalEst;
    }

    public String getColumnaOtraTotalCupos() {
        return columnaOtraTotalCupos;
    }

    public String getColumnaOtraUtilEstandar() {
        return columnaOtraUtilEstandar;
    }

    public String getColumnaTotalPredio() {
        return columnaTotalPredio;
    }
    
    public ConsultaAmbito getConsultaAmbito() {
        return consultaAmbito;
    }

    public void setConsultaAmbito(ConsultaAmbito consultaAmbito) {
        this.consultaAmbito = consultaAmbito;
    }
}
