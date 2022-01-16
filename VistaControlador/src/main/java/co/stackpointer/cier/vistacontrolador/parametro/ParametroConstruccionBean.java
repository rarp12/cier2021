/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.parametro;

import co.stackpointer.cier.modelo.auditoria.RegistrarAuditoria;
import co.stackpointer.cier.modelo.entidad.dpa.ConfiguracionNivel;
import co.stackpointer.cier.modelo.entidad.dpa.Nivel;
import co.stackpointer.cier.modelo.entidad.indicador.ParametroConstruccion;
import co.stackpointer.cier.modelo.entidad.indicador.PeriodosCostosConstruccion;
import co.stackpointer.cier.modelo.entidad.indicador.UnidadFuncional;
import co.stackpointer.cier.modelo.excepcion.ErrorIntegridad;
import co.stackpointer.cier.modelo.excepcion.ErrorValidacion;
import co.stackpointer.cier.modelo.fachada.EstructuraDPAFacadeLocal;
import co.stackpointer.cier.modelo.fachada.ParametrosFacadeLocal;
import co.stackpointer.cier.modelo.interfaceDPA.ConfNivelDPA;
import co.stackpointer.cier.modelo.tipo.BitacoraTransaccion;
import co.stackpointer.cier.modelo.tipo.ParamNivelConsul;
import co.stackpointer.cier.modelo.tipo.TipoCostoConstruccion;
import co.stackpointer.cier.modelo.tipo.UnidadMedida;
import co.stackpointer.cier.modelo.utilidad.UtilCadena;
import co.stackpointer.cier.modelo.utilidad.UtilFecha;
import co.stackpointer.cier.vista.Utilidades;
import static co.stackpointer.cier.vista.Utilidades.mostrarMensajeInfo;
import co.stackpointer.cier.vistacontrolador.bean.LoginBean;
import co.stackpointer.cier.vistacontrolador.bean.UsuarioSesionBean;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.omnifaces.util.Messages;
import org.primefaces.context.RequestContext;

/**
 *
 * @author rarp1
 */
@ManagedBean(name = "parametroConstruccionBean")
@ViewScoped
public class ParametroConstruccionBean {

    @EJB
    private ParametrosFacadeLocal facParam;
    @EJB
    private EstructuraDPAFacadeLocal facEstDPA;
    @ManagedProperty(value = "#{usuarioSesion}")
    private UsuarioSesionBean usuarioSesion;
    @ManagedProperty(value = "#{login}")
    private LoginBean currentLogin;
    private List<ParametroConstruccion> listado = new ArrayList<ParametroConstruccion>();
    private List<UnidadFuncional> unidadesFuncionales = new ArrayList<UnidadFuncional>();
    private List<PeriodosCostosConstruccion> periodos = new ArrayList<PeriodosCostosConstruccion>();
    private ParametroConstruccion parametroSeleccionado;
    private ParametroConstruccion nuevoParametro;
    private PeriodosCostosConstruccion nuevoPeriodo;
    private PeriodosCostosConstruccion periodoSeleccionado;
    private Long idUnidad;
    private LinkedHashMap<String, String> listaTiposCostos;
    private LinkedHashMap<String, String> listaUnidades;
    private LinkedHashMap<Long, Long> listaPeriodos;
    //DPA
    private Map<Long, String> listaNivelesConf;
    private int maximoNivelDPA;
    private int minimoNivelDPA;
    private List<Nivel> listaNivel1;
    private List<Nivel> listaNivel2;
    private List<Nivel> listaNivel3;
    private List<Nivel> listaNivel4;
    private List<Nivel> listaNivel5;
    private Nivel seleccion;
    private Nivel seleccionNivel0;
    private Nivel seleccionNivel1;
    private Nivel seleccionNivel2;
    private Nivel seleccionNivel3;
    private Nivel seleccionNivel4;
    private Nivel seleccionNivel5;
    private boolean swNivel1;
    private boolean swNivel2;
    private boolean swNivel3;
    private boolean swNivel4;
    private boolean swNivel5;
    protected boolean swNivelEspecifico;
    protected Nivel padreNivel0;
    protected Nivel padreNivel1;
    protected Nivel padreNivel2;
    protected Nivel padreNivel3;
    protected Nivel padreNivel4;
    protected Nivel padreNivel5;
    private Nivel nivelSeleccionado;
    private String nombreNivelInicial;
    private String nombreDPAInicial;
    private String nombreUltimoDPA;
    private Long parametroDpaNivel;
    private int periodoActual;
    private List<ParametroConstruccion> parametrosFiltrados;

    @PostConstruct
    public void inicializar() {
        periodoActual = UtilFecha.obtenerPeriodoActual();
        this.nuevoParametro = new ParametroConstruccion();
        this.parametroSeleccionado = new ParametroConstruccion();
        this.nuevoPeriodo = new PeriodosCostosConstruccion();
        this.periodoSeleccionado = new PeriodosCostosConstruccion();
        listaTiposCostos = new LinkedHashMap<String, String>();
        unidadesFuncionales = facParam.obtenerUnidadesFuncionales();
        List<TipoCostoConstruccion> auxList = Arrays.asList(TipoCostoConstruccion.values());
        for (TipoCostoConstruccion cod : auxList) {
            listaTiposCostos.put(cod.name(), Utilidades.obtenerMensaje(cod.getEtiqueta()));
        }
        List<UnidadMedida> auxList1 = Arrays.asList(UnidadMedida.values());
        listaUnidades = new LinkedHashMap<String, String>();
        for (UnidadMedida cod : auxList1) {
            listaUnidades.put(cod.name(), Utilidades.obtenerMensaje(cod.getEtiqueta()));
        }
        this.consultaPeriodos();
        //resetearNiveles();
        cargarConfiguracionDPA();
        cargarNivel0();
        activarNiveles(0);
        this.consultarParametrosPorPeriodosInicial();
    }

    public void consultaPeriodos() {
        try {
            actualizarPeriodos();
        } finally {
            RegistrarAuditoria registrar = new RegistrarAuditoria();
            registrar.setFecha(new Date());
            registrar.setNombreMetodo(BitacoraTransaccion.CONSULTA_PARAMETROS_CONSTRUCCION.name());
            registrar.setUsuario(usuarioSesion.getUsuario().getUsername());
            registrar.start();
        }
    }
    
    private void actualizarPeriodos() {
        periodos = facParam.obtenerPeriodosConstruccion();
        listaPeriodos = new LinkedHashMap<Long, Long>();
        for (PeriodosCostosConstruccion periodo : periodos) {
            listaPeriodos.put(periodo.getPeriodo(), periodo.getPeriodo());
        }
    }   

    public void consultarParametrosPorPeriodos(){
        this.parametrosFiltrados = facParam.consultaParametrosConstruccionPeriodo(this.periodoSeleccionado.getPeriodo());
        this.listado = facParam.consultaParametrosConstruccionPeriodo(this.periodoSeleccionado.getPeriodo());
    }

    public void consultarParametrosPorPeriodoIn( PeriodosCostosConstruccion periodoIn ){
        this.parametrosFiltrados = facParam.consultaParametrosConstruccionPeriodo(periodoIn.getPeriodo());
        this.listado = facParam.consultaParametrosConstruccionPeriodo(periodoIn.getPeriodo());
    }

    public void consultarParametrosPorPeriodosInicial(){
        if (!listaPeriodos.isEmpty()){
        Long primerPeriodo = listaPeriodos.entrySet().iterator().next().getValue();
        if ( primerPeriodo != null)
            this.listado = facParam.consultaParametrosConstruccionPeriodo(primerPeriodo);
        }
    }

    public List<UnidadFuncional> getUnidadesFuncionales() {
        return unidadesFuncionales;
    }

    public Long getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(Long idUnidad) {
        this.idUnidad = idUnidad;
    }

    public void setUnidadesFuncionales(List<UnidadFuncional> unidadesFuncionales) {
        this.unidadesFuncionales = unidadesFuncionales;
    }

    public LinkedHashMap<String, String> getListaTiposCostos() {
        return listaTiposCostos;
    }

    public void setListaTiposCostos(LinkedHashMap<String, String> listaTiposCostos) {
        this.listaTiposCostos = listaTiposCostos;
    }

    public LinkedHashMap<String, String> getListaUnidades() {
        return listaUnidades;
    }

    public void setListaUnidades(LinkedHashMap<String, String> listaUnidades) {
        this.listaUnidades = listaUnidades;
    }

    public void consulta() {
        this.listado = facParam.consultaParametrosConstruccion();
    }

    public void eliminarParametroCosto() {
        try {
            facParam.eliminarParametroPC(this.parametroSeleccionado);
            this.listado.remove(parametroSeleccionado);
        } catch (Exception e) {
            Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("adm.parametros.config.carga.errorRegistro"));
        } finally {
            RegistrarAuditoria registrar = new RegistrarAuditoria();
            registrar.setFecha(new Date());
            registrar.setNombreMetodo(BitacoraTransaccion.ELIMINAR_PARAMETROS_CONSTRUCCION.name());
            registrar.setUsuario(usuarioSesion.getUsuario().getUsername());
            registrar.start();
        }
    }

    public void instanciarNuevoParametro() {
        this.nuevoParametro = new ParametroConstruccion();
        this.seleccionNivel5 = null;
        this.seleccionNivel4 = null;
        this.seleccionNivel3 = null;
        this.seleccionNivel2 = null;
        this.seleccionNivel1 = null;
        this.cargarConfiguracionDPA();
        cargarNivel0();
        activarNiveles(0);
    }

     public void instanciarNuevoPeriodo() {
        this.nuevoPeriodo = new PeriodosCostosConstruccion();
    }

    private void escogerNivel() {
        switch (parametroDpaNivel.intValue()) {
            case 0:
                seleccion = seleccionNivel0;
                break;
            case 1:
                seleccion = seleccionNivel1;
                break;
            case 2:
                seleccion = seleccionNivel2;
                break;
            case 3:
                seleccion = seleccionNivel3;
                break;
            case 4:
                seleccion = seleccionNivel4;
                break;
            case 5:
                seleccion = seleccionNivel5;
                break;
        }
    }

    private void validarPeriodos(PeriodosCostosConstruccion parametro){

        if (parametro.getPeriodo() == null || parametro.getPeriodo() < 100000 || parametro.getPeriodo() > 999999) {
            throw new ErrorValidacion(Utilidades.obtenerMensaje("adm.parametros.estandar.pc.error.periodo.invalido"));
        } else {
            String yr = (parametro.getPeriodo()).toString().substring(0, 4);
            String mn = (parametro.getPeriodo()).toString().substring(4, 6);
            String yrAct = Integer.toString(periodoActual).substring(0, 4);
            int yrInt = Integer.parseInt(yrAct) + 10;
            if (parametro.getPeriodo() < periodoActual) {
                throw new ErrorValidacion(Utilidades.obtenerMensaje("adm.parametros.estandar.pc.error.crear.periodo.invalido"));
            }

            if (Integer.valueOf(mn) == 0 || Integer.valueOf(mn) > 12) {
                throw new ErrorValidacion(Utilidades.obtenerMensaje("adm.parametros.estandar.pc.error.crear.mes.invalido"));
            }

            if (Integer.valueOf(yr) > yrInt) {
                throw new ErrorValidacion(Utilidades.obtenerMensaje("adm.parametros.estandar.pc.error.crear.ano.invalido", yrInt));
            }
        }

    }

    private void validarRegistros(ParametroConstruccion parametro) {
        if (parametro.getValorRural() == null
                && parametro.getValorRuralAhislado() == null
                && parametro.getValorRuralUrbePerimetral() == null
                && parametro.getValorUrbano() == null
                && parametro.getValorUrbanoMarginal() == null
                && parametro.getOtro() == null) {
            throw new ErrorValidacion(Utilidades.obtenerMensaje("adm.parametros.guardar.faltan.valores"));
        }



        if( parametro.getValorRural() != null ){
            if(!isNumeric(parametro.getValorRural().toString())){
                throw new ErrorValidacion(Utilidades.obtenerMensaje("adm.parametros.estandar.pc.error.crear.valor.numerico","Valor Rural"));
            }
        }

        if (parametro.getValorRuralAhislado() != null) {
            if (!isNumeric(parametro.getValorRuralAhislado().toString())) {
                throw new ErrorValidacion(Utilidades.obtenerMensaje("adm.parametros.estandar.pc.error.crear.valor.numerico", "Valor Rural Ahislado"));
            }
        }

        if (parametro.getValorRuralUrbePerimetral() != null) {
            if (!isNumeric(parametro.getValorRuralUrbePerimetral().toString())) {
                throw new ErrorValidacion(Utilidades.obtenerMensaje("adm.parametros.estandar.pc.error.crear.valor.numerico", "Valor Rural Urbe Perimetral"));
            }
        }

        if (parametro.getValorUrbano() != null) {
            if (!isNumeric(parametro.getValorUrbano().toString())) {
                throw new ErrorValidacion(Utilidades.obtenerMensaje("adm.parametros.estandar.pc.error.crear.valor.numerico", "Valor Urbano"));
            }
        }

        if (parametro.getValorUrbanoMarginal() != null) {
            if (!isNumeric(parametro.getValorUrbanoMarginal().toString())) {
                throw new ErrorValidacion(Utilidades.obtenerMensaje("adm.parametros.estandar.pc.error.crear.valor.numerico", "Valor Urbano Marginal"));
            }
        }

        if (parametro.getOtro() != null) {
            if (!isNumeric(parametro.getOtro().toString())) {
                throw new ErrorValidacion(Utilidades.obtenerMensaje("adm.parametros.estandar.pc.error.crear.valor.numerico", "Valor Otro"));
            }
        }
    }

    public void guardarParametro() {
        try {
            this.escogerNivel();
            this.nuevoParametro.setNivel(this.seleccion);
            this.nuevoParametro.setUnidadFuncional(facParam.obtenerUnidadFuncionalById(idUnidad));
            this.nuevoParametro.setIdNivel(nuevoParametro.getNivel().getConfiguracion().getNivel());

            if (facParam.validarParametroCostos(nuevoParametro) == true) {
                throw new ErrorValidacion(Utilidades.obtenerMensaje("adm.parametros.estandar.pc.error.crear.varios"));
            }
            this.validarRegistros(nuevoParametro);
            listado.add(facParam.crearParametroPC(this.nuevoParametro));
            mostrarMensajeInfo(Utilidades.obtenerMensaje("dig.guardar.establecimiento"), Utilidades.obtenerMensaje("dig.guardar.exito.elemento"));
        } catch (Exception ex) {
            if (ex instanceof ErrorValidacion) {
                RequestContext.getCurrentInstance().addCallbackParam("validationFailed", true);
                Messages.addFlashGlobalError(ex.getMessage());
            } else {
                Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("adm.parametros.config.carga.errorRegistro"));
            }
        } 
    }

    public void guardarPeriodo() {
         try {
            this.nuevoPeriodo.setEstado("A");
            this.validarPeriodos(this.nuevoPeriodo);
            this.nuevoPeriodo = facParam.crearPeriodoCC(this.nuevoPeriodo);
            facParam.crearParametrosCostos(1, nuevoPeriodo.getPeriodo());
            this.actualizarPeriodos();
            this.consultarParametrosPorPeriodoIn(nuevoPeriodo);
            this.periodoSeleccionado = this.nuevoPeriodo;
            mostrarMensajeInfo(Utilidades.obtenerMensaje("adm.parametros.config.carga.datosGuardados"), Utilidades.obtenerMensaje("adm.parametros.config.carga.datosGuardados"));
         } catch (Exception ex) {
            if (ex instanceof ErrorValidacion) {
                RequestContext.getCurrentInstance().addCallbackParam("validationFailed", true);
                Messages.addFlashGlobalError(ex.getMessage());
            } else {
                if (ex instanceof ErrorIntegridad) {
                    RequestContext.getCurrentInstance().addCallbackParam("validationFailed", true);
                    Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje(ex.getMessage()));
                } else{
                    RequestContext.getCurrentInstance().addCallbackParam("validationFailed", true);
                    Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("adm.parametros.config.carga.errorRegistro"));
                }
            }
        } finally {
            RegistrarAuditoria registrar = new RegistrarAuditoria();
            registrar.setFecha(new Date());
            registrar.setNombreMetodo(BitacoraTransaccion.CREAR_PARAMETROS_CONSTRUCCION.name());
            registrar.setUsuario(usuarioSesion.getUsuario().getUsername());
            registrar.start();
        }
    }



    public void preEditar(ParametroConstruccion parametro) {
        if (parametro != null) {
            this.parametroSeleccionado = parametro;
            this.idUnidad = parametro.getUnidadFuncional().getId();
            this.cargarConfiguracionDPA(parametro.getNivel().getConfiguracion().getNivel() != null ? parametro.getNivel().getConfiguracion().getNivel() : parametroDpaNivel);
            this.nivelDPAOfAccesoUsuario(parametro.getNivel());
            this.cargarConfiguracionDPA(parametro.getIdNivel());
        }

    }

    public void editarParametro() {
        try {
            //this.parametroSeleccionado.setNivel(seleccionNivel4 == null ? seleccionNivel3 : seleccionNivel4);
            this.parametroSeleccionado.setPeriodo(this.periodoSeleccionado.getPeriodo());
            this.validarRegistros(parametroSeleccionado);
            facParam.actualizarParametro(this.parametroSeleccionado);
            mostrarMensajeInfo(Utilidades.obtenerMensaje("dig.guardar.establecimiento"), Utilidades.obtenerMensaje("dig.guardar.exito.elemento"));
            this.consulta();
        } catch (Exception ex) {
            if (ex instanceof ErrorValidacion) {
                RequestContext.getCurrentInstance().addCallbackParam("validationFailed", true);
                Messages.addFlashGlobalError(ex.getMessage());
            } else {
                Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("adm.parametros.config.carga.errorRegistro"));
            }
        } finally {
            RegistrarAuditoria registrar = new RegistrarAuditoria();
            registrar.setFecha(new Date());
            registrar.setNombreMetodo(BitacoraTransaccion.EDITAR_PARAMETROS_CONSTRUCCION.name());
            registrar.setUsuario(usuarioSesion.getUsuario().getUsername());
            registrar.start();
        }
    }
    //DPA
    public void cargarConfiguracionDPA() {
        listaNivelesConf = new HashMap<Long, String>();
        String tmp = facParam.consultarParametro("NIVEL_COSTOS_CONSTRUCCION").getValor();
        parametroDpaNivel = (tmp != null ? Long.valueOf(tmp) : 0L);
        // niveles DPA
        List<ConfiguracionNivel> configuraciones;
        configuraciones = facEstDPA.consultarConfNivelesActualInverso(parametroDpaNivel);
        for (ConfNivelDPA conf : configuraciones) {
            String descripcion = facEstDPA.consultarDescripcionNivelDPA(conf.getNivel(), usuarioSesion.getUsuario().getIdioma().getId());
            descripcion = descripcion != null ? descripcion : conf.getDescripcion();
            this.listaNivelesConf.put(conf.getNivel(), descripcion);
        }
        // maximo nivel DPA
        if (!configuraciones.isEmpty()) {
            int indice = configuraciones.size() - 1;
            this.maximoNivelDPA = configuraciones.get(indice).getNivel().intValue();
            this.minimoNivelDPA = configuraciones.get(0).getNivel().intValue();
            this.nombreUltimoDPA = configuraciones.get(indice).getDescripcion();
        } else {
            this.maximoNivelDPA = -1;
            this.minimoNivelDPA = -1;
        }
    }

    //DPAEditar
    public void cargarConfiguracionDPA(Long nivel) {
        listaNivelesConf = new HashMap<Long, String>();
        // niveles DPA
        List<ConfiguracionNivel> configuraciones;
        configuraciones = facEstDPA.consultarConfNivelesActualInverso(nivel);
        for (ConfNivelDPA conf : configuraciones) {
            String descripcion = facEstDPA.consultarDescripcionNivelDPA(conf.getNivel(), usuarioSesion.getUsuario().getIdioma().getId());
            descripcion = descripcion != null ? descripcion : conf.getDescripcion();
            this.listaNivelesConf.put(conf.getNivel(), descripcion);
        }
        // maximo nivel DPA
        if (!configuraciones.isEmpty()) {
            int indice = configuraciones.size() - 1;
            this.maximoNivelDPA = configuraciones.get(indice).getNivel().intValue();
            this.minimoNivelDPA = configuraciones.get(0).getNivel().intValue();
            this.nombreUltimoDPA = configuraciones.get(indice).getDescripcion();
        } else {
            this.maximoNivelDPA = -1;
            this.minimoNivelDPA = -1;
        }
    }

    public void activarNiveles(int maximoNivelUsuario) {
        swNivel1 = false;
        swNivel2 = false;
        swNivel3 = false;
        swNivel4 = false;
        swNivel5 = false;
        maximoNivelUsuario++;
        while (maximoNivelUsuario <= maximoNivelDPA) {
            switch (maximoNivelUsuario) {
                case 1:
                    swNivel1 = true;
                    break;
                case 2:
                    swNivel2 = true;
                    break;
                case 3:
                    swNivel3 = true;
                    break;
                case 4:
                    swNivel4 = true;
                    break;
                case 5:
                    swNivel5 = true;
                    break;
            }
            maximoNivelUsuario++;
        }
    }

    public void cargarPadres(Nivel nivel) {
        int level = UtilCadena.isNullOVacio(nivel.getConfiguracion().getNivel()) ? 0 : nivel.getConfiguracion().getNivel().intValue();
        switch (level) {
            case 1:
                padreNivel0 = nivel.getPadre();
                break;
            case 2:
                padreNivel1 = nivel.getPadre();
                padreNivel0 = padreNivel1.getPadre();

                seleccionNivel1 = padreNivel1;
                cambioNivel1();
                seleccionNivel2 = nivel;
                cambioNivel2();

                break;
            case 3:
                padreNivel2 = nivel.getPadre();
                padreNivel1 = padreNivel2.getPadre();
                padreNivel0 = padreNivel1.getPadre();

                seleccionNivel1 = padreNivel1;
                cambioNivel1();
                seleccionNivel2 = padreNivel2;
                cambioNivel2();
                seleccionNivel3 = nivel;
                cambioNivel3();
                break;
            case 4:
                padreNivel3 = nivel.getPadre();
                padreNivel2 = padreNivel3.getPadre();
                padreNivel1 = padreNivel2.getPadre();
                padreNivel0 = padreNivel1.getPadre();

                seleccionNivel1 = padreNivel1;
                cambioNivel1();
                seleccionNivel2 = padreNivel2;
                cambioNivel2();
                seleccionNivel3 = padreNivel3;
                cambioNivel3();
                seleccionNivel4 = nivel;
                cambioNivel4();

                break;
            case 5:
                padreNivel4 = nivel.getPadre();
                padreNivel3 = padreNivel4.getPadre();
                padreNivel2 = padreNivel3.getPadre();
                padreNivel1 = padreNivel2.getPadre();
                padreNivel0 = padreNivel1.getPadre();

                seleccionNivel1 = padreNivel1;
                cambioNivel1();
                seleccionNivel2 = padreNivel2;
                cambioNivel2();
                seleccionNivel3 = padreNivel3;
                cambioNivel3();
                seleccionNivel4 = padreNivel4;
                cambioNivel4();
                seleccionNivel5 = nivel;
                cambioNivel5();

                break;
        }
    }

    public void nivelDPAOfAccesoUsuario(Nivel n) {
        if (n != null) {
            int nivel = n.getConfiguracion().getNivel().intValue();
            switch (nivel) {
                case 0:
                    cargarNivel0();
                    break;
                case 1:
                    seleccionNivel1 = n;
                    break;
                case 2:
                    seleccionNivel2 = n;
                    break;
                case 3:
                    seleccionNivel3 = n;
                    break;
                case 4:
                    seleccionNivel4 = n;
                    break;
                case 5:
                    seleccionNivel5 = n;
                    break;
            }
            cargarPadres(n);
            activarNiveles(0);
        } else {
            cargarNivel0();
            activarNiveles(0);
        }
    }

    private List<Nivel> cargarNivel(Nivel nivelSeccionado, ParamNivelConsul nivelConsulta) {
        return facEstDPA.consultarNiveles(nivelConsulta, nivelSeccionado.getId());
    }

    private void resetearNivel1() {
        seleccionNivel1 = null;
        listaNivel1 = new ArrayList<Nivel>();
    }

    private void resetearNivel2() {
        seleccionNivel2 = null;
        listaNivel2 = new ArrayList<Nivel>();
    }

    private void resetearNivel3() {
        seleccionNivel3 = null;
        listaNivel3 = new ArrayList<Nivel>();
    }

    private void resetearNivel4() {
        seleccionNivel4 = null;
        listaNivel4 = new ArrayList<Nivel>();
    }

    private void resetearNivel5() {
        seleccionNivel5 = null;
        listaNivel5 = new ArrayList<Nivel>();
    }

    public void resetearNiveles() {
        this.resetearNivel1();
        this.resetearNivel2();
        this.resetearNivel3();
        this.resetearNivel4();
        this.resetearNivel5();
    }

    public void cargarNivel0() {
        String codigoPais = currentLogin.getPais().getCodigoPais();
        seleccionNivel0 = facEstDPA.consultarNivel(codigoPais);
        if (seleccionNivel0 != null) {
            this.nombreNivelInicial = seleccionNivel0.getConfiguracion().getDescripcion();
            this.nombreDPAInicial = seleccionNivel0.getDescripcion();
            this.cargarNivel1();
        }
    }

    public void cargarNivel1() {
        this.resetearNivel1();
        this.resetearNivel2();
        this.resetearNivel3();
        this.resetearNivel4();
        this.resetearNivel5();
        if (seleccionNivel0 != null) {
            listaNivel1 = this.cargarNivel(seleccionNivel0, ParamNivelConsul.UNO);
        }
    }

    public void cambioNivel1() {
        this.resetearNivel2();
        this.resetearNivel3();
        this.resetearNivel4();
        this.resetearNivel5();
        if (seleccionNivel1 != null) {
            nivelSeleccionado = seleccionNivel1;
            listaNivel2 = this.cargarNivel(seleccionNivel1, ParamNivelConsul.DOS);
        } else {
            nivelSeleccionado = null;
        }
    }

    public void cambioNivel2() {
        this.resetearNivel3();
        this.resetearNivel4();
        this.resetearNivel5();
        if (seleccionNivel2 != null) {
            nivelSeleccionado = seleccionNivel2;
            listaNivel3 = this.cargarNivel(seleccionNivel2, ParamNivelConsul.TRES);
        } else {
            nivelSeleccionado = seleccionNivel1;
        }
    }

    public void cambioNivel3() {
        this.resetearNivel4();
        this.resetearNivel5();
        if (seleccionNivel3 != null) {
            nivelSeleccionado = seleccionNivel3;
            listaNivel4 = this.cargarNivel(seleccionNivel3, ParamNivelConsul.CUATRO);
        } else {
            nivelSeleccionado = seleccionNivel2;
        }
    }

    public void cambioNivel4() {
        this.resetearNivel5();
        if (seleccionNivel4 != null) {
            nivelSeleccionado = seleccionNivel4;
            listaNivel5 = this.cargarNivel(seleccionNivel4, ParamNivelConsul.CINCO);
        } else {
            nivelSeleccionado = seleccionNivel3;
        }
    }

    public void cambioNivel5() {
        if (seleccionNivel5 != null) {
            nivelSeleccionado = seleccionNivel5;
        } else {
            nivelSeleccionado = seleccionNivel4;
        }
    }
    //DPA

    private boolean isNumeric(String cadena) {
        if (cadena.indexOf(".") == (cadena.length() - 1)) {
            return false;
        } else {
            return cadena.matches("\\d+(\\.\\d{0,2})?");
        }

    }

    public ParametroConstruccion getNuevoParametro() {
        return nuevoParametro;
    }

    public void setNuevoParametro(ParametroConstruccion nuevoParametro) {
        this.nuevoParametro = nuevoParametro;
    }

    public List<ParametroConstruccion> getListado() {
        return listado;
    }

    public void setListado(List<ParametroConstruccion> listado) {
        this.listado = listado;
    }

    public ParametroConstruccion getParametroSeleccionado() {
        return parametroSeleccionado;
    }

    public void setParametroSeleccionado(ParametroConstruccion parametroSeleccionado) {
        this.parametroSeleccionado = parametroSeleccionado;
    }

    public List<Nivel> getListaNivel1() {
        return listaNivel1;
    }

    public void setListaNivel1(List<Nivel> listaNivel1) {
        this.listaNivel1 = listaNivel1;
    }

    public List<Nivel> getListaNivel2() {
        return listaNivel2;
    }

    public void setListaNivel2(List<Nivel> listaNivel2) {
        this.listaNivel2 = listaNivel2;
    }

    public List<Nivel> getListaNivel3() {
        return listaNivel3;
    }

    public void setListaNivel3(List<Nivel> listaNivel3) {
        this.listaNivel3 = listaNivel3;
    }

    public List<Nivel> getListaNivel4() {
        return listaNivel4;
    }

    public void setListaNivel4(List<Nivel> listaNivel4) {
        this.listaNivel4 = listaNivel4;
    }

    public List<Nivel> getListaNivel5() {
        return listaNivel5;
    }

    public void setListaNivel5(List<Nivel> listaNivel5) {
        this.listaNivel5 = listaNivel5;
    }

    public Nivel getSeleccionNivel0() {
        return seleccionNivel0;
    }

    public void setSeleccionNivel0(Nivel seleccionNivel0) {
        this.seleccionNivel0 = seleccionNivel0;
    }

    public Nivel getSeleccionNivel1() {
        return seleccionNivel1;
    }

    public void setSeleccionNivel1(Nivel seleccionNivel1) {
        this.seleccionNivel1 = seleccionNivel1;
    }

    public Nivel getSeleccionNivel2() {
        return seleccionNivel2;
    }

    public void setSeleccionNivel2(Nivel seleccionNivel2) {
        this.seleccionNivel2 = seleccionNivel2;
    }

    public Nivel getSeleccionNivel3() {
        return seleccionNivel3;
    }

    public void setSeleccionNivel3(Nivel seleccionNivel3) {
        this.seleccionNivel3 = seleccionNivel3;
    }

    public Nivel getSeleccionNivel4() {
        return seleccionNivel4;
    }

    public void setSeleccionNivel4(Nivel seleccionNivel4) {
        this.seleccionNivel4 = seleccionNivel4;
    }

    public Nivel getSeleccionNivel5() {
        return seleccionNivel5;
    }

    public void setSeleccionNivel5(Nivel seleccionNivel5) {
        this.seleccionNivel5 = seleccionNivel5;
    }

    public boolean isSwNivel1() {
        return swNivel1;
    }

    public void setSwNivel1(boolean swNivel1) {
        this.swNivel1 = swNivel1;
    }

    public boolean isSwNivel2() {
        return swNivel2;
    }

    public void setSwNivel2(boolean swNivel2) {
        this.swNivel2 = swNivel2;
    }

    public boolean isSwNivel3() {
        return swNivel3;
    }

    public void setSwNivel3(boolean swNivel3) {
        this.swNivel3 = swNivel3;
    }

    public boolean isSwNivel4() {
        return swNivel4;
    }

    public void setSwNivel4(boolean swNivel4) {
        this.swNivel4 = swNivel4;
    }

    public boolean isSwNivel5() {
        return swNivel5;
    }

    public void setSwNivel5(boolean swNivel5) {
        this.swNivel5 = swNivel5;
    }

    public Map<Long, String> getListaNivelesConf() {
        return listaNivelesConf;
    }

    public void setListaNivelesConf(Map<Long, String> listaNivelesConf) {
        this.listaNivelesConf = listaNivelesConf;
    }

    public UsuarioSesionBean getUsuarioSesion() {
        return usuarioSesion;
    }

    public void setUsuarioSesion(UsuarioSesionBean usuarioSesion) {
        this.usuarioSesion = usuarioSesion;
    }

    public LoginBean getCurrentLogin() {
        return currentLogin;
    }

    public void setCurrentLogin(LoginBean currentLogin) {
        this.currentLogin = currentLogin;
    }

    public String getNombreUltimoDPA() {
        return nombreUltimoDPA;
    }

    public LinkedHashMap<Long, Long> getListaPeriodos() {
        return listaPeriodos;
    }

    public void setListaPeriodos(LinkedHashMap<Long, Long> listaPeriodos) {
        this.listaPeriodos = listaPeriodos;
    }

    public void setNombreUltimoDPA(String nombreUltimoDPA) {
        this.nombreUltimoDPA = nombreUltimoDPA;
    }

    public PeriodosCostosConstruccion getNuevoPeriodo() {
        return nuevoPeriodo;
    }

    public void setNuevoPeriodo(PeriodosCostosConstruccion nuevoPeriodo) {
        this.nuevoPeriodo = nuevoPeriodo;
    }

    public PeriodosCostosConstruccion getPeriodoSeleccionado() {
        return periodoSeleccionado;
    }

    public void setPeriodoSeleccionado(PeriodosCostosConstruccion periodoSeleccionado) {
        this.periodoSeleccionado = periodoSeleccionado;
    }

    public List<ParametroConstruccion> getParametrosFiltrados() {
        return parametrosFiltrados;
    }

    public void setParametrosFiltrados(List<ParametroConstruccion> parametrosFiltrados) {
        this.parametrosFiltrados = parametrosFiltrados;
    }

}
