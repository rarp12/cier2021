/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.consulta.ambito;

import co.stackpointer.cier.modelo.auditoria.RegistrarAuditoria;
import co.stackpointer.cier.modelo.entidad.indicador.EstandarHis;
import co.stackpointer.cier.modelo.filtro.ambito.FiltroAmbitoAmbiente;
import co.stackpointer.cier.modelo.tipo.BitacoraTransaccion;
import co.stackpointer.cier.modelo.tipo.ParamNivelConsul;
import co.stackpointer.cier.modelo.utilidad.UtilConsulta;
import co.stackpointer.cier.vista.Utilidades;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author user
 */
@ManagedBean(name = "ambiente")
@ViewScoped
public class AmbienteBean extends ConsultaAmbito {

    @ManagedProperty(value = "#{consultaAmbito}")
    private ConsultaAmbito consultaAmbito;
    private List<Registro> lista;
    private FiltroAmbitoAmbiente filtro = new FiltroAmbitoAmbiente();
    private TotalConsulta totalConsulta = new TotalConsulta();
    private String unidadFuncional;
    private String nombreUnidadFuncional;
    private boolean swUnidadFuncionalSeleccionada;

    final String codEst = Utilidades.obtenerMensaje("aplicacion.general.codEst");
    final String nomEst = Utilidades.obtenerMensaje("aplicacion.general.nomEst");
    final String codSede = Utilidades.obtenerMensaje("aplicacion.general.codSede");
    final String nomSede = Utilidades.obtenerMensaje("aplicacion.general.nomSede");
    final String codPredio = Utilidades.obtenerMensaje("aplicacion.general.codPredio");
    final String nomPredio = Utilidades.obtenerMensaje("aplicacion.general.nomPredio");
    final String dirPredio = Utilidades.obtenerMensaje("consultas.ambito.column.dirPredio");
    final String jornadaPorSede = Utilidades.obtenerMensaje("consultas.ambito.column.jornadaPorSede");
    final String alumnosPorSede = Utilidades.obtenerMensaje("consultas.ambito.column.alumnosPorSede");
    final String codRiesgos = Utilidades.obtenerMensaje("consultas.ambito.column.codRiesgos");
    final String accDiscapacitados = Utilidades.obtenerMensaje("consultas.ambito.column.accDiscapacitados");
    final String numEspaciosPorTipo = Utilidades.obtenerMensaje("consultas.ambito.column.numEspaciosPorTipo");
    final String proporcionEspacios = Utilidades.obtenerMensaje("consultas.ambito.column.proporcionEspacios");
    final String numEstNoCumplenNorma = Utilidades.obtenerMensaje("consultas.ambito.column.numEstNoCumplenNorma");
    final String totalEstablecimientos = Utilidades.obtenerMensaje("consultas.ambito.column.totalEstablecimientos");
    final String proporcionEstablecimientos = Utilidades.obtenerMensaje("consultas.ambito.column.proporcionEstablecimientos");
    final String numEstNoCumplenNormaPorTipo = Utilidades.obtenerMensaje("consultas.ambito.column.numEstNoCumplenNormaPorTipo");
    final String totalPredios = Utilidades.obtenerMensaje("consultas.ambito.column.totalPredios");
    final String proporcionPredios = Utilidades.obtenerMensaje("consultas.ambito.column.proporcionPredios");
    final String acumuladoDeficit = Utilidades.obtenerMensaje("consultas.ambito.column.acumuladoDeficit");
    final String areaTipoEspacio = Utilidades.obtenerMensaje("consultas.ambito.column.areaTipoEspacio");
    final String numEstudMayorJornada = Utilidades.obtenerMensaje("consultas.ambito.column.numEstudMayorJornada");
    final String metroPorAlumno = Utilidades.obtenerMensaje("consultas.ambito.column.metroPorAlumno");
    final String deficitEstandar = Utilidades.obtenerMensaje("consultas.ambito.column.deficitEstandar");
    final String cumplimientoNorma = Utilidades.obtenerMensaje("consultas.ambito.column.cumplimientoNorma");
    public String estandar;
    public String unidad;

    @PostConstruct
    public void inicializar() {
        //super.init();
        lista = new ArrayList<Registro>();
        //cargarCamposOpcionales();        
        swUnidadFuncionalSeleccionada = false;
    }

    private void cargarCamposOpcionales() {
        selccionCamposOpcionales.clear();
        listaCamposOpcionales.clear();
        this.cargarCamposOpcionalesComunes();
        if (isMostrarColDirPredio()) {
            listaCamposOpcionales.add(dirPredio);
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
        if (isMostrarColJornadaEstudiantesPorSede()) {
            listaCamposOpcionales.add(jornadaPorSede);
        }
        if (isMostrarColNumAlumnos()) {
            listaCamposOpcionales.add(alumnosPorSede);
        }
        if (isMostrarColCondicionRiesgos()) {
            listaCamposOpcionales.add(codRiesgos);
        }
        if (isMostrarColAccesoDiscapacitados()) {
            listaCamposOpcionales.add(accDiscapacitados);
        }
        if (isMostrarColNumEspaciosPorTipo()) {
            listaCamposOpcionales.add(numEspaciosPorTipo);
            selccionCamposOpcionales.add(numEspaciosPorTipo);
        }
        if (isMostrarColProporcionEspacios()) {
            listaCamposOpcionales.add(proporcionEspacios);
            selccionCamposOpcionales.add(proporcionEspacios);
        }
        if (isMostrarColNumEstabNoCumplenNorma()) {
            listaCamposOpcionales.add(numEstNoCumplenNorma);
            selccionCamposOpcionales.add(numEstNoCumplenNorma);
        }
        if (isMostrarColTotalEstablecimientos()) {
            listaCamposOpcionales.add(totalEstablecimientos);
            selccionCamposOpcionales.add(totalEstablecimientos);
        }
        if (isMostrarColProporcionEstab()) {
            listaCamposOpcionales.add(proporcionEstablecimientos);
            selccionCamposOpcionales.add(proporcionEstablecimientos);
        }
        if (isMostrarColNumPrediosNoCumplenNormaPorTipo()) {
            listaCamposOpcionales.add(numEstNoCumplenNormaPorTipo);
            selccionCamposOpcionales.add(numEstNoCumplenNormaPorTipo);
        }
        if (isMostrarColTotalPredios()) {
            listaCamposOpcionales.add(totalPredios);
            selccionCamposOpcionales.add(totalPredios);
        }
        if (isMostrarColProporcionPredios()) {
            listaCamposOpcionales.add(proporcionPredios);
            selccionCamposOpcionales.add(proporcionPredios);
        }
        if (isMostrarColAcumuladoDeficit()) {
            listaCamposOpcionales.add(acumuladoDeficit);
            selccionCamposOpcionales.add(acumuladoDeficit);
        }
        if (isMostrarColAreaTipoEspacio()) {
            listaCamposOpcionales.add(areaTipoEspacio);
            selccionCamposOpcionales.add(areaTipoEspacio);
        }
        if (isMostrarColNumEstudiantesMayorJornada()) {
            listaCamposOpcionales.add(numEstudMayorJornada);
            //selccionCamposOpcionales.add(numEstudMayorJornada);
        }
        if (isMostrarColRelacionM2PorEstudiante()) {
            listaCamposOpcionales.add(metroPorAlumno);
            selccionCamposOpcionales.add(metroPorAlumno);
        }
        if (isMostrarColDeficitEstandar()) {
            listaCamposOpcionales.add(deficitEstandar);
            selccionCamposOpcionales.add(deficitEstandar);
        }
    }

    @Override
    public void resetearFiltros() {
        consultaAmbito.resetearNiveles();
        this.resetearListasEstSedesPredios();
        this.seleccionSector = null;
        this.seleccionZona = null;
        consultaAmbito.nivelDPAOfAccesoUsuario();
        this.lista = new ArrayList<Registro>();
        this.selccionCamposOpcionales.clear();
        this.cargarCamposOpcionales();
        this.totalConsulta.inicializar();
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

        //unidad funcional
        filtro.setUnidadFuncional(unidadFuncional != null ? unidadFuncional : null);

        // filtros basicos
        filtro.setCodZona(seleccionZona != null ? seleccionZona.getTipValor().getCodigo() : null);
        filtro.setCodSector(seleccionSector != null ? seleccionSector.getTipValor().getCodigo() : null);

        List<Object[]> resultados = facAmbito.consultarAmbientes(filtro);
        lista = new ArrayList<Registro>(resultados.size());
        if (lista.size() < 1) {
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
                // indicadores
                registro.setNumEspaciosPorTipo(UtilConsulta.longValueOf(fila[i++]));
                registro.setProporcionEspacios(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setAreaTipoEspacio(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setNumEstudiantesMayorJornada(UtilConsulta.longValueOf(fila[i++]));
                registro.setRelacionM2PorEstudiante(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setCondicionRealacionM2PorEstudiante(UtilConsulta.stringValueOf(fila[i++]));
                registro.setDeficitEstandar(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setCumplimientoNormaEspacioEstudiante(UtilConsulta.doubleValueOf(fila[i++]));
                //otros
                registro.setDirPredio(UtilConsulta.stringValueOf(fila[i++]));
                registro.setZona(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]), getUsuarioSesion().getUsuario().getIdioma().getId()));
                registro.setSector(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]), getUsuarioSesion().getUsuario().getIdioma().getId()));
                registro.setClima(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]), getUsuarioSesion().getUsuario().getIdioma().getId()));
                registro.setJornadaEstudiantesPorSede(UtilConsulta.stringValueOf(fila[i++]));
                registro.setNumAlumnos(UtilConsulta.longValueOf(fila[i++]));
                registro.setCondicionRiesgos(UtilConsulta.stringValueOf(fila[i++]));
                registro.setAccesoDiscapacitados(UtilConsulta.stringValueOf(fila[i++]));
                //totales
                totalConsulta.addNumEspaciosPorTipo(registro.getNumEspaciosPorTipo());
                totalConsulta.addNumEstudiantesMayorJornada(registro.getNumEstudiantesMayorJornada());
                totalConsulta.addAreaTotal(registro.getAreaTipoEspacio());
            } else {
                if (filtro.isAgrupamientoEstablecimiento() || filtro.isAgrupamientoSede()) {
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
                    if (filtro.isAgrupamientoSede()) {
                        registro.setCodSede(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setNomSede(UtilConsulta.stringValueOf(fila[i++]));
                    }
                    // indicadores
                    registro.setNumEspaciosPorTipo(UtilConsulta.longValueOf(fila[i++]));
                    registro.setProporcionEspacios(UtilConsulta.doubleValueOf(fila[i++]));
                    if (filtro.getUnidadFuncional().equals("BIBLIOTECA")) {
                        registro.setRelacionM2PorEstudiante(UtilConsulta.doubleValueOf(fila[i++]));
                        registro.setCondicionRealacionM2PorEstudiante(UtilConsulta.stringValueOf(fila[i++]));
                        registro.setDeficitEstandar(UtilConsulta.doubleValueOf(fila[i++]));
                    } else {
                        registro.setNumPrediosNoCumplenNormaPorTipo(UtilConsulta.longValueOf(fila[i++]));
                        registro.setTotalPredios(UtilConsulta.longValueOf(fila[i++]));
                        registro.setProporcionPredios(UtilConsulta.doubleValueOf(fila[i++]));
                        registro.setAcumuladoDeficit(UtilConsulta.doubleValueOf(fila[i++]));
                    }
                    registro.setCumplimientoNormaEspacioEstudiante(UtilConsulta.doubleValueOf(fila[i++]));
                    //totales
                    totalConsulta.addNumEspaciosPorTipo(registro.getNumEspaciosPorTipo());
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
                    }
                    // indicadores
                    registro.setNumEstabNoCumplenNorma(UtilConsulta.longValueOf(fila[i++]));
                    registro.setTotalEstablecimientos(UtilConsulta.longValueOf(fila[i++]));
                    registro.setProporcionEstab(UtilConsulta.doubleValueOf(fila[i++]));
                    if (!filtro.getUnidadFuncional().equals("BIBLIOTECA")) {
                        registro.setNumPrediosNoCumplenNormaPorTipo(UtilConsulta.longValueOf(fila[i++]));
                        registro.setTotalPredios(UtilConsulta.longValueOf(fila[i++]));
                        registro.setProporcionPredios(UtilConsulta.doubleValueOf(fila[i++]));
                    }
                    registro.setAcumuladoDeficit(UtilConsulta.doubleValueOf(fila[i++]));
                    registro.setCumplimientoNormaEspacioEstudiante(UtilConsulta.doubleValueOf(fila[i++]));
                    //totales
                    totalConsulta.addNumEstNoCumplenNormaPorTipo(registro.getNumEstabNoCumplenNorma());
                    totalConsulta.addTotalEstablecimientos(registro.getTotalEstablecimientos());
                }
                //totales
                totalConsulta.addNumPrediosNoCumplenNormaPorTipo(registro.getNumPrediosNoCumplenNormaPorTipo());
                totalConsulta.addTotalPredios(registro.getTotalPredios());
            }
            lista.add(registro);
        }
    }

    @Override
    public void cambioAgrupamiento() {
        selccionCamposOpcionales.clear();
        this.cargarCamposOpcionales();
        lista.clear();
        totalConsulta.inicializar();
    }

    public List<Registro> getLista() {
        return lista;
    }

    public boolean isSwEspacioEscolarSeleccionado() {
        return swUnidadFuncionalSeleccionada;
    }

    public void setSwUnidadFuncionalSeleccionada(boolean swUnidadFuncionalSeleccionada) {
        this.swUnidadFuncionalSeleccionada = swUnidadFuncionalSeleccionada;
    }

    public String getUnidadFuncional() {
        return unidadFuncional;
    }

    public void setUnidadFuncional(String unidadFuncional) {
        this.unidadFuncional = unidadFuncional;
        if (unidadFuncional.equals("PLAYON_DEPORTIVO") || unidadFuncional.equals("BIBLIOTECA")
                || unidadFuncional.equals("LABORATORIO_CIENCIAS") || unidadFuncional.equals("LABORATORIO_MULTIMEDIAL")
                || unidadFuncional.equals("LABORATORIO_COMPUTACION") || unidadFuncional.equals("SALA_MUSICA")
                || unidadFuncional.equals("SALA_ARTES") || unidadFuncional.equals("SALON_USOS_MULTIPLES")) {
            this.seleccionAgrupamiento = ParamNivelConsul.ESTABLECIMIENTO.getNivel();
            this.removerAgrupamiento(ParamNivelConsul.SEDE);
            this.removerAgrupamiento(ParamNivelConsul.PREDIO);
        }
        if (unidadFuncional.equals("PSICOPEDAGOGIA_ENFERMERIA") || unidadFuncional.equals("ALIMENTOS")
                || unidadFuncional.equals("OFICINA_ADMINISTRACION")) {
            this.seleccionAgrupamiento = ParamNivelConsul.SEDE.getNivel();
            this.removerAgrupamiento(ParamNivelConsul.PREDIO);
        }
        facParam.consultarEstandarPorCodigo("ESTANDAR_" + unidadFuncional).getValor().toString();
        EstandarHis estandarHis = facParam.consultarEstandarPorCodigo("ESTANDAR_" + unidadFuncional);
        estandar = estandarHis.getValor().toString();
        unidad = Utilidades.obtenerMensaje(estandarHis.getEtiquetaUnidades());
        cargarCamposOpcionales();
        auditar(unidadFuncional);
    }
    
    private void auditar(String unidadFuncional) {
        RegistrarAuditoria registrar = new RegistrarAuditoria();
        registrar.setFecha(new Date());
        if (unidadFuncional.equalsIgnoreCase("AULA")) {
            registrar.setNombreMetodo(BitacoraTransaccion.CONSULTAR_AMBIENTE_AULA.name());
        }
        if (unidadFuncional.equalsIgnoreCase("BIBLIOTECA")) {
            registrar.setNombreMetodo(BitacoraTransaccion.CONSULTAR_AMBIENTE_BIBLIOTECA.name());
        }
        if (unidadFuncional.equalsIgnoreCase("PLAYON_DEPORTIVO")) {
            registrar.setNombreMetodo(BitacoraTransaccion.CONSULTAR_AMBIENTE_PLAYON_DEPORTIVO.name());
        }
        if (unidadFuncional.equalsIgnoreCase("LABORATORIO_MULTIMEDIAL")) {
            registrar.setNombreMetodo(BitacoraTransaccion.CONSULTAR_AMBIENTE_LABORATORIO_MULTIMEDIAL.name());
        }
        if (unidadFuncional.equalsIgnoreCase("LABORATORIO_COMPUTACION")) {
            registrar.setNombreMetodo(BitacoraTransaccion.CONSULTAR_AMBIENTE_LABORATORIO_COMPUTACION.name());
        }
        if (unidadFuncional.equalsIgnoreCase("SALA_MUSICA")) {
            registrar.setNombreMetodo(BitacoraTransaccion.CONSULTAR_AMBIENTE_SALA_MUSICA.name());
        }
        if (unidadFuncional.equalsIgnoreCase("SALA_ARTES")) {
            registrar.setNombreMetodo(BitacoraTransaccion.CONSULTAR_AMBIENTE_SALA_ARTES.name());
        }
        if (unidadFuncional.equalsIgnoreCase("EXPANSIONES_RECREATIVAS")) {
            registrar.setNombreMetodo(BitacoraTransaccion.CONSULTAR_AMBIENTE_EXPANSIONES_RECREATIVAS.name());
        }
        if (unidadFuncional.equalsIgnoreCase("SALON_USOS_MULTIPLES")) {
            registrar.setNombreMetodo(BitacoraTransaccion.CONSULTAR_AMBIENTE_SALON_USOS_MULTIPLES.name());
        }
        if (unidadFuncional.equalsIgnoreCase("PSICOPEDAGOGIA_ENFERMERIA")) {
            registrar.setNombreMetodo(BitacoraTransaccion.CONSULTAR_AMBIENTE_PSICOPEDAGOGIA_ENFERMERIA.name());
        }
        if (unidadFuncional.equalsIgnoreCase("SERVICIOS_SANITARIOS")) {
            registrar.setNombreMetodo(BitacoraTransaccion.CONSULTAR_AMBIENTE_SERVICIOS_SANITARIOS.name());
        }
        if (unidadFuncional.equalsIgnoreCase("OFICINA_ADMINISTRACION")) {
            registrar.setNombreMetodo(BitacoraTransaccion.CONSULTAR_AMBIENTE_OFICINA_ADMINISTRACION.name());
        }
        registrar.setUsuario(usuarioSesion.getUsuario().getUsername());
        registrar.start();
    }    

    public String getNombreUnidadFuncional() {
        return nombreUnidadFuncional;
    }

    public void setNombreUnidadFuncional(String nombreUnidadFuncional) {
        this.nombreUnidadFuncional = nombreUnidadFuncional;
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
        private Long numEspaciosPorTipo;
        private Double proporcionEspacios;
        private Double areaTipoEspacio;
        private Long numEstudiantesMayorJornada;
        private Double relacionM2PorEstudiante;
        private String condicionRealacionM2PorEstudiante;
        private Double deficitEstandar;
        private Double cumplimientoNormaEspacioEstudiante;
        private String dirPredio;
        private String zona;
        private String sector;
        private String clima;
        private String jornadaEstudiantesPorSede;
        private Long numAlumnos;
        private String condicionRiesgos;
        private String accesoDiscapacitados;
        private Long numPrediosNoCumplenNormaPorTipo;
        private Long totalPredios;
        private Double proporcionPredios;
        private Double acumuladoDeficit;
        private Long numEstabNoCumplenNorma;
        private Long totalEstablecimientos;
        private Double proporcionEstab;

        public Double calcularCumplimientoNorma(Double m2_espacio_activo, Long num_estudiantes_may_jornada, Double estandar) {
            if (estandar.compareTo(0D) == 0 || num_estudiantes_may_jornada.compareTo(0L) == 0) {
                return 0D;
            } else {
                return (m2_espacio_activo / (num_estudiantes_may_jornada * estandar)) * 100;
            }
        }

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

        public Long getNumEspaciosPorTipo() {
            return numEspaciosPorTipo;
        }

        public void setNumEspaciosPorTipo(Long numEspaciosPorTipo) {
            this.numEspaciosPorTipo = numEspaciosPorTipo;
        }

        public Double getProporcionEspacios() {
            return proporcionEspacios;
        }

        public void setProporcionEspacios(Double proporcionEspacios) {
            this.proporcionEspacios = proporcionEspacios;
        }

        public Double getAreaTipoEspacio() {
            return areaTipoEspacio;
        }

        public void setAreaTipoEspacio(Double areaTipoEspacio) {
            this.areaTipoEspacio = areaTipoEspacio;
        }

        public Long getNumEstudiantesMayorJornada() {
            return numEstudiantesMayorJornada;
        }

        public void setNumEstudiantesMayorJornada(Long numEstudiantesMayorJornada) {
            this.numEstudiantesMayorJornada = numEstudiantesMayorJornada;
        }

        public Double getRelacionM2PorEstudiante() {
            return relacionM2PorEstudiante;
        }

        public void setRelacionM2PorEstudiante(Double relacionM2PorEstudiante) {
            this.relacionM2PorEstudiante = relacionM2PorEstudiante;
        }

        public String getCondicionRealacionM2PorEstudiante() {
            return condicionRealacionM2PorEstudiante;
        }

        public void setCondicionRealacionM2PorEstudiante(String condicionRealacionM2PorEstudiante) {
            this.condicionRealacionM2PorEstudiante = condicionRealacionM2PorEstudiante;
        }

        public Double getDeficitEstandar() {
            return deficitEstandar;
        }

        public void setDeficitEstandar(Double deficitEstandar) {
            this.deficitEstandar = deficitEstandar;
        }

        public Double getCumplimientoNormaEspacioEstudiante() {
            return cumplimientoNormaEspacioEstudiante;
        }

        public void setCumplimientoNormaEspacioEstudiante(Double cumplimientoNormaEspacioEstudiante) {
            this.cumplimientoNormaEspacioEstudiante = cumplimientoNormaEspacioEstudiante;
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

        public String getJornadaEstudiantesPorSede() {
            return jornadaEstudiantesPorSede;
        }

        public void setJornadaEstudiantesPorSede(String jornadaEstudiantesPorSede) {
            this.jornadaEstudiantesPorSede = jornadaEstudiantesPorSede;
        }

        public Long getNumAlumnos() {
            return numAlumnos;
        }

        public void setNumAlumnos(Long numAlumnos) {
            this.numAlumnos = numAlumnos;
        }

        public String getCondicionRiesgos() {
            return condicionRiesgos;
        }

        public void setCondicionRiesgos(String condicionRiesgos) {
            this.condicionRiesgos = condicionRiesgos;
        }

        public String getAccesoDiscapacitados() {
            return accesoDiscapacitados;
        }

        public void setAccesoDiscapacitados(String accesoDiscapacitados) {
            this.accesoDiscapacitados = accesoDiscapacitados;
        }

        public Long getNumPrediosNoCumplenNormaPorTipo() {
            return numPrediosNoCumplenNormaPorTipo;
        }

        public void setNumPrediosNoCumplenNormaPorTipo(Long numPrediosNoCumplenNormaPorTipo) {
            this.numPrediosNoCumplenNormaPorTipo = numPrediosNoCumplenNormaPorTipo;
        }

        public Long getTotalPredios() {
            return totalPredios;
        }

        public void setTotalPredios(Long totalPredios) {
            this.totalPredios = totalPredios;
        }

        public Double getProporcionPredios() {
            return proporcionPredios;
        }

        public void setProporcionPredios(Double proporcionPredios) {
            this.proporcionPredios = proporcionPredios;
        }

        public Double getAcumuladoDeficit() {
            return acumuladoDeficit;
        }

        public void setAcumuladoDeficit(Double acumuladoDeficit) {
            this.acumuladoDeficit = acumuladoDeficit;
        }

        public Long getNumEstabNoCumplenNorma() {
            return numEstabNoCumplenNorma;
        }

        public void setNumEstabNoCumplenNorma(Long numEstabNoCumplenNorma) {
            this.numEstabNoCumplenNorma = numEstabNoCumplenNorma;
        }

        public Long getTotalEstablecimientos() {
            return totalEstablecimientos;
        }

        public void setTotalEstablecimientos(Long totalEstablecimientos) {
            this.totalEstablecimientos = totalEstablecimientos;
        }

        public Double getProporcionEstab() {
            return proporcionEstab;
        }

        public void setProporcionEstab(Double proporcionEstab) {
            this.proporcionEstab = proporcionEstab;
        }
    }

    public class TotalConsulta {

        private Long numPrediosNoCumplenNormaPorTipo;
        private Long totalPredios;
        private Long numEspaciosPorTipo;
        private Long numEstudiantesMayorJornada;
        private Double areaTotal;
        private Long numEstNoCumplenNormaPorTipo;
        private Long totalEstablecimientos;

        public void inicializar() {
            numPrediosNoCumplenNormaPorTipo = 0L;
            totalPredios = 0L;
            numEspaciosPorTipo = 0L;
            numEstudiantesMayorJornada = 0L;
            areaTotal = 0D;
            numEstNoCumplenNormaPorTipo = 0L;
            totalEstablecimientos = 0L;
        }

        public Long getNumPrediosNoCumplenNormaPorTipo() {
            return numPrediosNoCumplenNormaPorTipo;
        }

        public Long getTotalPredios() {
            return totalPredios;
        }

        public Long getNumEspaciosPorTipo() {
            return numEspaciosPorTipo;
        }

        public Long getNumEstudiantesMayorJornada() {
            return numEstudiantesMayorJornada;
        }

        public Double getAreaTotal() {
            return areaTotal;
        }

        public Long getNumEstNoCumplenNormaPorTipo() {
            return numEstNoCumplenNormaPorTipo;
        }

        public Long getTotalEstablecimientos() {
            return totalEstablecimientos;
        }

        public void addNumPrediosNoCumplenNormaPorTipo(Long valor) {
            if (valor != null) {
                this.numPrediosNoCumplenNormaPorTipo += valor;
            }
        }

        public void addTotalPredios(Long valor) {
            if (valor != null) {
                this.totalPredios += valor;
            }
        }

        public void addNumEspaciosPorTipo(Long valor) {
            if (valor != null) {
                this.numEspaciosPorTipo += valor;
            }
        }

        public void addNumEstudiantesMayorJornada(Long valor) {
            if (valor != null) {
                this.numEstudiantesMayorJornada += valor;
            }
        }

        public void addAreaTotal(Double valor) {
            if (valor != null) {
                this.areaTotal += valor;
            }
        }

        public void addNumEstNoCumplenNormaPorTipo(Long valor) {
            if (valor != null) {
                this.numEstNoCumplenNormaPorTipo += valor;
            }
        }

        public void addTotalEstablecimientos(Long valor) {
            if (valor != null) {
                this.totalEstablecimientos += valor;
            }
        }
    }

    // mostrar columnas
    public boolean isMostrarColNumEspaciosPorTipo() {
        //return (isAgrupamientoPredio() || isAgrupamientoEstablecimiento()) && unidadFuncional.equals("AULA");
        return isAgrupamientoPredio() || isAgrupamientoSede() || isAgrupamientoEstablecimiento();
    }

    public boolean isMostrarColProporcionEspacios() {
        return isAgrupamientoPredio() || isAgrupamientoSede() || isAgrupamientoEstablecimiento();
    }

    public boolean isMostrarColAreaTipoEspacio() {
        return isAgrupamientoPredio();
    }

    public boolean isMostrarColNumEstudiantesMayorJornada() {
        return isAgrupamientoPredio();
    }

    public boolean isMostrarColRelacionM2PorEstudiante() {
        return isAgrupamientoPredio() && !unidadFuncional.equals("EXPANSIONES_RECREATIVAS")
                || isAgrupamientoEstablecimiento() && unidadFuncional.equals("BIBLIOTECA");
    }

    public boolean isMostrarColDeficitEstandar() {
        return isAgrupamientoPredio() || isAgrupamientoEstablecimiento() && unidadFuncional.equals("BIBLIOTECA");
    }

    public boolean isMostrarColJornadaEstudiantesPorSede() {
        return false;
    }

    public boolean isMostrarColNumAlumnos() {
        return false;
    }

    public boolean isMostrarColCondicionRiesgos() {
        return false;//isAgrupamientoPredio();
    }

    public boolean isMostrarColAccesoDiscapacitados() {
        return false;//isAgrupamientoPredio();
    }

    public boolean isMostrarColNumPrediosNoCumplenNormaPorTipo() {
        return !isAgrupamientoPredio() && !unidadFuncional.equals("BIBLIOTECA");
    }

    public boolean isMostrarColTotalPredios() {
        return !isAgrupamientoPredio() && !unidadFuncional.equals("BIBLIOTECA");
    }

    public boolean isMostrarColProporcionPredios() {
        return !isAgrupamientoPredio() && !unidadFuncional.equals("BIBLIOTECA");
    }

    public boolean isMostrarColAcumuladoDeficit() {
        return !isAgrupamientoPredio() && !unidadFuncional.equals("BIBLIOTECA")
                || !isAgrupamientoEstablecimiento() && unidadFuncional.equals("BIBLIOTECA");
    }

    public boolean isMostrarColNumEstabNoCumplenNorma() {
        return !isAgrupamientoPredio() && !isAgrupamientoSede() && !isAgrupamientoEstablecimiento();
    }

    public boolean isMostrarColTotalEstablecimientos() {
        return !isAgrupamientoPredio() && !isAgrupamientoSede() && !isAgrupamientoEstablecimiento();
    }

    public boolean isMostrarColProporcionEstab() {
        return !isAgrupamientoPredio() && !isAgrupamientoSede() && !isAgrupamientoEstablecimiento();
    }

    public String getCodEst() {
        return codEst;
    }

    public String getNomEst() {
        return nomEst;
    }

    public String getCodSede() {
        return codSede;
    }

    public String getNomSede() {
        return nomSede;
    }

    public String getCodPredio() {
        return codPredio;
    }

    public String getNomPredio() {
        return nomPredio;
    }

    public String getDirPredio() {
        return dirPredio;
    }

    public String getJornadaPorSede() {
        return jornadaPorSede;
    }

    public String getAlumnosPorSede() {
        return alumnosPorSede;
    }

    public String getCodRiesgos() {
        return codRiesgos;
    }

    public String getAccDiscapacitados() {
        return accDiscapacitados;
    }

    public String getNumEspaciosPorTipo() {
        return numEspaciosPorTipo;
    }

    public String getProporcionEspacios() {
        return proporcionEspacios;
    }

    public String getNumEstNoCumplenNorma() {
        return numEstNoCumplenNorma;
    }

    public String getTotalEstablecimientos() {
        return totalEstablecimientos;
    }

    public String getProporcionEstablecimientos() {
        return proporcionEstablecimientos;
    }

    public String getNumEstNoCumplenNormaPorTipo() {
        return numEstNoCumplenNormaPorTipo;
    }

    public String getTotalPredios() {
        return totalPredios;
    }

    public String getProporcionPredios() {
        return proporcionPredios;
    }

    public String getAcumuladoDeficit() {
        return acumuladoDeficit;
    }

    public String getAreaTipoEspacio() {
        return areaTipoEspacio;
    }

    public String getNumEstudMayorJornada() {
        return numEstudMayorJornada;
    }

    public String getMetroPorAlumno() {
        return metroPorAlumno;
    }

    public String getDeficitEstandar() {
        return deficitEstandar;
    }

    public String getCumplimientoNorma() {
        return cumplimientoNorma;
    }

    public String getEstandar() {
        return estandar;
    }

    public void setEstandar(String estandar) {
        this.estandar = estandar;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public boolean isCumple(Object idoneidad) {
        if (idoneidad != null) {
            Double valor = (Double) idoneidad / 100D;
            return (valor.compareTo(1D) == 0 || valor.compareTo(1D) > 0) ? true : false;
        } else {
            return false;
        }
    }

    public boolean isNoCumple(Object idoneidad) {
        if (idoneidad != null) {
            Double valor = (Double) idoneidad / 100D;
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
