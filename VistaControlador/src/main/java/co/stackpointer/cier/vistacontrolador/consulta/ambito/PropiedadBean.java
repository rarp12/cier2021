/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.consulta.ambito;

import co.stackpointer.cier.modelo.entidad.instrumento.TipologiaValorNombre;
import co.stackpointer.cier.modelo.filtro.ambito.FiltroAmbitoPropiedad;
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
@ManagedBean(name = "propiedadBean")
@ViewScoped
public class PropiedadBean extends ConsultaAmbito {

    @ManagedProperty(value = "#{consultaAmbito}")
    private ConsultaAmbito consultaAmbito;
    private List<String> seleccionPropietarios;
    private List<TipologiaValorNombre> listaPropietarios;
    private Registro totalConsulta = new Registro();
    private List<Registro> lista;
    private FiltroAmbitoPropiedad filtro = new FiltroAmbitoPropiedad();
    private String propiedadVerificable;
    private final String columnaTipoPropietario = Utilidades.obtenerMensaje("consultas.ambito.propiedad.columna.tipo.propietario");
    private final String columnaVerificacionPropiedad = Utilidades.obtenerMensaje("consultas.ambito.propiedad.columna.verificacion.propiedad");
    private final String columnaTotalEstudiantes = Utilidades.obtenerMensaje("consultas.ambito.propiedad.columna.total.estudiantes");
    private final String columnaPredioArrendado = Utilidades.obtenerMensaje("consultas.ambito.propiedad.columna.predio.arrendado");
    private final String columnaCostoArriendo = Utilidades.obtenerMensaje("consultas.ambito.propiedad.columna.costo.arriendo");
    private final String columnaNombrePropietario = Utilidades.obtenerMensaje("consultas.ambito.propiedad.columna.nombre.propietario");
    private final String columnaDisponibilidadAgua = Utilidades.obtenerMensaje("consultas.ambito.propiedad.columna.disponibilidad.agua");
    private final String columnaCondicionAgua = Utilidades.obtenerMensaje("consultas.ambito.propiedad.columna.condicion.agua");
    private final String columnaDisponibilidadEnergiaElectrica = Utilidades.obtenerMensaje("consultas.ambito.propiedad.columna.disponibilidad.energia.electrica");
    private final String columnaCondicionEnergiaElectrica = Utilidades.obtenerMensaje("consultas.ambito.propiedad.columna.condicion.energia.electrica");
    private final String columnaDisponibilidadRedAlcantarillado = Utilidades.obtenerMensaje("consultas.ambito.propiedad.columna.disponibilidad.alcantarillado");
    private final String columnaCondicionRedAlcantarillado = Utilidades.obtenerMensaje("consultas.ambito.propiedad.columna.condicion.alcantarillado");
    private final String columnaTotalPredProbLegalizacion = Utilidades.obtenerMensaje("consultas.ambito.propiedad.columna.predio.prob.legalizacion");
    private final String columnaTotalPredios = Utilidades.obtenerMensaje("consultas.ambito.propiedad.columna.total.predio");
    private final String columnaProporcionPredios = Utilidades.obtenerMensaje("consultas.ambito.propiedad.columna.proporcion.predio");
    private final String columnaTotalEstudPredProbLegalizacion = Utilidades.obtenerMensaje("consultas.ambito.propiedad.columna.est.pred.prob.legalizacion");
    private final String columnaProporcionEstudiantes = Utilidades.obtenerMensaje("consultas.ambito.propiedad.columna.nombre.proporcion.estudiantes");

    @PostConstruct
    public void inicializar() {
        //super.init();
        listaPropietarios = facParam.consultarValoresTipologia(TipologiaCodigo.TIPO_PROPIETARIO_PREDIO.getValor(), getUsuarioSesion().getUsuario().getIdioma().getId());
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
            filtro.setPropiedadVerificable(propiedadVerificable != null ? propiedadVerificable : null);
            filtro.setCodPropietario(elementosSeleccionados(seleccionPropietarios) != null ? elementosSeleccionados(seleccionPropietarios) : null);

            List<Object[]> resultados = facAmbito.consultarPropiedad(filtro);
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
                    registro.setZona(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]), getUsuarioSesion().getUsuario().getIdioma().getId()));
                    registro.setSector(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]), getUsuarioSesion().getUsuario().getIdioma().getId()));
                    registro.setClima(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]), getUsuarioSesion().getUsuario().getIdioma().getId()));
                    registro.setEtnias(verificarEtnias(UtilConsulta.stringValueOf(fila[i++])));
                    //indicadores 
                    //propiedad
                    registro.setPropiedadPredio(Utilidades.obtenerMensaje(UtilConsulta.stringValueOf(fila[i++])));
                    Object idtip = fila[i++];
                    if (UtilConsulta.stringValueOf(idtip).equalsIgnoreCase("sql.ind.general.noAplica")) {
                        registro.setTipoPropietario(Utilidades.obtenerMensaje(UtilConsulta.stringValueOf(idtip)));
                    } else {
                        registro.setTipoPropietario(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(idtip), getUsuarioSesion().getUsuario().getIdioma().getId()));
                    }
                    registro.setVerificacionPropiedad(Utilidades.obtenerMensaje(UtilConsulta.stringValueOf(fila[i++])));
                    registro.setNombrePropietario(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setPredioArrendado(Utilidades.obtenerMensaje(UtilConsulta.stringValueOf(fila[i++])));
                    registro.setCostoArriendo(UtilConsulta.doubleValueOf(fila[i++]));
                    //servicios
                    registro.setDisponibilidadAgua(Utilidades.obtenerMensaje(UtilConsulta.stringValueOf(fila[i++])));
                    registro.setCondicionAgua(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setDisponibilidadEnergiaElectrica(Utilidades.obtenerMensaje(UtilConsulta.stringValueOf(fila[i++])));
                    registro.setCondicionEnergiaElectrica(UtilConsulta.stringValueOf(fila[i++]));
                    registro.setDisponibilidadRedAlcantarillado(Utilidades.obtenerMensaje(UtilConsulta.stringValueOf(fila[i++])));
                    registro.setCondicionRedAlcantarillado(UtilConsulta.stringValueOf(fila[i++]));
                    //estudiantes                    
                    registro.setTotalEstudiantes(UtilConsulta.longValueOf(fila[i++]));
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
                    //indicadores
                    registro.setTotalPredProbLegalizacion(UtilConsulta.longValueOf(fila[i++]));
                    registro.setTotalPredios(UtilConsulta.longValueOf(fila[i++]));
                    registro.setProporcionPredios(UtilConsulta.doubleValueOf(fila[i++]));
                    registro.setTotalEstudPredProbLegalizacion(UtilConsulta.longValueOf(fila[i++]));
                    registro.setTotalEstudiantes(UtilConsulta.longValueOf(fila[i++]));
                    registro.setProporcionEstudiantes(UtilConsulta.doubleValueOf(fila[i++]));
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
        this.seleccionSector = null;
        this.seleccionZona = null;
        this.propiedadVerificable = null;
        this.seleccionPropietarios = null;
        //this.seleccionAgrupamiento = ParamNivelConsul.PREDIO.getNivel();
        consultaAmbito.nivelDPAOfAccesoUsuario();
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
        this.propiedadVerificable = null;
        this.seleccionPropietarios = null;
        //this.seleccionAgrupamiento = ParamNivelConsul.PREDIO.getNivel();
        //consultaAmbito.nivelDPAOfAccesoUsuario();
        this.resetearConsulta();
    }

    @Override
    public void cambioAgrupamiento() {
        selccionCamposOpcionales.clear();
        this.resetearConsulta();
    }

    private void resetearConsulta() {
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
            listaCamposOpcionales.add(columnaPropPredio);
            listaCamposOpcionales.add(columnaTipoPropietario);
            listaCamposOpcionales.add(columnaVerificacionPropiedad);
            listaCamposOpcionales.add(columnaTotalEstudiantes);
            listaCamposOpcionales.add(columnaPredioArrendado);
            listaCamposOpcionales.add(columnaCostoArriendo);
            listaCamposOpcionales.add(columnaNombrePropietario);
            listaCamposOpcionales.add(columnaDisponibilidadAgua);
            listaCamposOpcionales.add(columnaCondicionAgua);
            listaCamposOpcionales.add(columnaDisponibilidadEnergiaElectrica);
            listaCamposOpcionales.add(columnaCondicionEnergiaElectrica);
            listaCamposOpcionales.add(columnaDisponibilidadRedAlcantarillado);
            listaCamposOpcionales.add(columnaCondicionRedAlcantarillado);
            // campos por defecto
            selccionCamposOpcionales.add(columnaPropPredio);
            selccionCamposOpcionales.add(columnaTipoPropietario);
            selccionCamposOpcionales.add(columnaVerificacionPropiedad);
            selccionCamposOpcionales.add(columnaTotalEstudiantes);
        } else {
            listaCamposOpcionales.add(columnaTotalPredProbLegalizacion);
            listaCamposOpcionales.add(columnaTotalPredios);
            listaCamposOpcionales.add(columnaProporcionPredios);
            listaCamposOpcionales.add(columnaTotalEstudPredProbLegalizacion);
            listaCamposOpcionales.add(columnaTotalEstudiantes);
            listaCamposOpcionales.add(columnaProporcionEstudiantes);
            // campos por defecto
            selccionCamposOpcionales.add(columnaTotalPredProbLegalizacion);
            selccionCamposOpcionales.add(columnaTotalPredios);
            selccionCamposOpcionales.add(columnaProporcionPredios);
            selccionCamposOpcionales.add(columnaTotalEstudPredProbLegalizacion);
            selccionCamposOpcionales.add(columnaTotalEstudiantes);
            selccionCamposOpcionales.add(columnaProporcionEstudiantes);
        }
    }

    private void inicializarTotalesConsulta() {
        totalConsulta.setTotalEstudiantes(0L);
        totalConsulta.setCostoArriendo(0D);
        totalConsulta.setTotalPredios(0L);
    }

    private void totalizarRegistro(Registro registro) {
        // predio
        if (registro.getTotalEstudiantes() != null) {
            Long valor = totalConsulta.getTotalEstudiantes() != null ? totalConsulta.getTotalEstudiantes() : 0L;
            totalConsulta.setTotalEstudiantes(valor + registro.getTotalEstudiantes());
        }
        if (registro.getTotalPredProbLegalizacion() != null) {
            Long valor = totalConsulta.getTotalPredProbLegalizacion() != null ? totalConsulta.getTotalPredProbLegalizacion() : 0L;
            totalConsulta.setTotalPredProbLegalizacion(valor + registro.getTotalPredProbLegalizacion());
        }
        if (registro.getTotalEstudPredProbLegalizacion() != null) {
            Long valor = totalConsulta.getTotalEstudPredProbLegalizacion() != null ? totalConsulta.getTotalEstudPredProbLegalizacion() : 0L;
            totalConsulta.setTotalEstudPredProbLegalizacion(valor + registro.getTotalEstudPredProbLegalizacion());
        }
        if (registro.getCostoArriendo() != null) {
            Double valor = totalConsulta.getCostoArriendo() != null ? totalConsulta.getCostoArriendo() : 0D;
            totalConsulta.setCostoArriendo(valor + registro.getCostoArriendo());
        }
        if (registro.getTotalPredios() != null) {
            Long valor = totalConsulta.getTotalPredios() != null ? totalConsulta.getTotalPredios() : 0L;
            totalConsulta.setTotalPredios(valor + registro.getTotalPredios());
        }
    }

    public String getPropiedadVerificable() {
        return propiedadVerificable;
    }

    public void setPropiedadVerificable(String propiedadVerificable) {
        this.propiedadVerificable = propiedadVerificable;
    }

    public List<String> getSeleccionPropietarios() {
        return seleccionPropietarios;
    }

    public void setSeleccionPropietarios(List<String> seleccionPropietarios) {
        this.seleccionPropietarios = seleccionPropietarios;
    }

    public List<TipologiaValorNombre> getListaPropietarios() {
        return listaPropietarios;
    }

    public void setListaPropietarios(List<TipologiaValorNombre> listaPropietarios) {
        this.listaPropietarios = listaPropietarios;
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
        private String zona;
        private String sector;
        private String clima;
        private String etnias;
        //predio        
        private String propiedadPredio;
        private String tipoPropietario;
        private String verificacionPropiedad;
        private Long totalEstudiantes;
        private String predioArrendado;
        private Double costoArriendo;
        private String nombrePropietario;
        private String disponibilidadAgua;
        private String condicionAgua;
        private String disponibilidadEnergiaElectrica;
        private String condicionEnergiaElectrica;
        private String disponibilidadRedAlcantarillado;
        private String condicionRedAlcantarillado;
        //otros niveles
        private Long totalPredProbLegalizacion;
        private Long totalPredios;
        private Double proporcionPredios;
        private Long totalEstudPredProbLegalizacion;
        private Double proporcionEstudiantes;

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

        public String getNombrePropietario() {
            return nombrePropietario;
        }

        public void setNombrePropietario(String nombrePropietario) {
            this.nombrePropietario = nombrePropietario;
        }

        public String getTipoPropietario() {
            return tipoPropietario;
        }

        public void setTipoPropietario(String tipoPropietario) {
            this.tipoPropietario = tipoPropietario;
        }

        public String getVerificacionPropiedad() {
            return verificacionPropiedad;
        }

        public void setVerificacionPropiedad(String verificacionPropiedad) {
            this.verificacionPropiedad = verificacionPropiedad;
        }

        public Long getTotalEstudiantes() {
            return totalEstudiantes;
        }

        public void setTotalEstudiantes(Long totalEstudiantes) {
            this.totalEstudiantes = totalEstudiantes;
        }

        public String getPredioArrendado() {
            return predioArrendado;
        }

        public void setPredioArrendado(String predioArrendado) {
            this.predioArrendado = predioArrendado;
        }

        public Double getCostoArriendo() {
            return costoArriendo;
        }

        public void setCostoArriendo(Double costoArriendo) {
            this.costoArriendo = costoArriendo;
        }

        public String getDisponibilidadAgua() {
            return disponibilidadAgua;
        }

        public void setDisponibilidadAgua(String disponibilidadAgua) {
            this.disponibilidadAgua = disponibilidadAgua;
        }

        public String getCondicionAgua() {
            return condicionAgua;
        }

        public void setCondicionAgua(String condicionAgua) {
            this.condicionAgua = condicionAgua;
        }

        public String getDisponibilidadEnergiaElectrica() {
            return disponibilidadEnergiaElectrica;
        }

        public void setDisponibilidadEnergiaElectrica(String disponibilidadEnergiaElectrica) {
            this.disponibilidadEnergiaElectrica = disponibilidadEnergiaElectrica;
        }

        public String getCondicionEnergiaElectrica() {
            return condicionEnergiaElectrica;
        }

        public void setCondicionEnergiaElectrica(String condicionEnergiaElectrica) {
            this.condicionEnergiaElectrica = condicionEnergiaElectrica;
        }

        public String getDisponibilidadRedAlcantarillado() {
            return disponibilidadRedAlcantarillado;
        }

        public void setDisponibilidadRedAlcantarillado(String disponibilidadRedAlcantarillado) {
            this.disponibilidadRedAlcantarillado = disponibilidadRedAlcantarillado;
        }

        public String getCondicionRedAlcantarillado() {
            return condicionRedAlcantarillado;
        }

        public void setCondicionRedAlcantarillado(String condicionRedAlcantarillado) {
            this.condicionRedAlcantarillado = condicionRedAlcantarillado;
        }

        public Long getTotalPredProbLegalizacion() {
            return totalPredProbLegalizacion;
        }

        public void setTotalPredProbLegalizacion(Long totalPredProbLegalizacion) {
            this.totalPredProbLegalizacion = totalPredProbLegalizacion;
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

        public Long getTotalEstudPredProbLegalizacion() {
            return totalEstudPredProbLegalizacion;
        }

        public void setTotalEstudPredProbLegalizacion(Long totalEstudPredProbLegalizacion) {
            this.totalEstudPredProbLegalizacion = totalEstudPredProbLegalizacion;
        }

        public Double getProporcionEstudiantes() {
            return proporcionEstudiantes;
        }

        public void setProporcionEstudiantes(Double proporcionEstudiantes) {
            this.proporcionEstudiantes = proporcionEstudiantes;
        }
    }

    public String getColumnaNombrePropietario() {
        return columnaNombrePropietario;
    }

    public String getColumnaTipoPropietario() {
        return columnaTipoPropietario;
    }

    public String getColumnaVerificacionPropiedad() {
        return columnaVerificacionPropiedad;
    }

    public String getColumnaTotalEstudiantes() {
        return columnaTotalEstudiantes;
    }

    public String getColumnaPredioArrendado() {
        return columnaPredioArrendado;
    }

    public String getColumnaCostoArriendo() {
        return columnaCostoArriendo;
    }

    public String getColumnaDisponibilidadAgua() {
        return columnaDisponibilidadAgua;
    }

    public String getColumnaCondicionAgua() {
        return columnaCondicionAgua;
    }

    public String getColumnaDisponibilidadEnergiaElectrica() {
        return columnaDisponibilidadEnergiaElectrica;
    }

    public String getColumnaCondicionEnergiaElectrica() {
        return columnaCondicionEnergiaElectrica;
    }

    public String getColumnaDisponibilidadRedAlcantarillado() {
        return columnaDisponibilidadRedAlcantarillado;
    }

    public String getColumnaCondicionRedAlcantarillado() {
        return columnaCondicionRedAlcantarillado;
    }

    public String getColumnaTotalPredProbLegalizacion() {
        return columnaTotalPredProbLegalizacion;
    }

    public String getColumnaTotalPredios() {
        return columnaTotalPredios;
    }

    public String getColumnaProporcionPredios() {
        return columnaProporcionPredios;
    }

    public String getColumnaTotalEstudPredProbLegalizacion() {
        return columnaTotalEstudPredProbLegalizacion;
    }

    public String getColumnaProporcionEstudiantes() {
        return columnaProporcionEstudiantes;
    }

    public ConsultaAmbito getConsultaAmbito() {
        return consultaAmbito;
    }

    public void setConsultaAmbito(ConsultaAmbito consultaAmbito) {
        this.consultaAmbito = consultaAmbito;
    }
    
}
