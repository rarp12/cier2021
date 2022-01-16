/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.consulta.comparador;

import co.stackpointer.cier.modelo.entidad.acceso.Usuario;
import co.stackpointer.cier.modelo.entidad.dpa.Nivel;
import co.stackpointer.cier.modelo.entidad.indicador.NivelHis;
import co.stackpointer.cier.modelo.fachada.EstructuraDPAFacadeLocal;
import co.stackpointer.cier.modelo.fachada.ParametrosFacadeLocal;
import co.stackpointer.cier.modelo.fachada.PredioFacadeLocal;
import co.stackpointer.cier.modelo.interfaceDPA.ConfNivelDPA;
import co.stackpointer.cier.modelo.interfaceDPA.EstablecimientoDPA;
import co.stackpointer.cier.modelo.interfaceDPA.NivelDPA;
import co.stackpointer.cier.modelo.interfaceDPA.PredioDPA;
import co.stackpointer.cier.modelo.interfaceDPA.SedeDPA;
import co.stackpointer.cier.modelo.tipo.AmbitoComparado;
import co.stackpointer.cier.modelo.tipo.CalificacionCondicion;
import co.stackpointer.cier.modelo.tipo.EspacioFuncional;
import co.stackpointer.cier.modelo.tipo.ParamNivelConsul;
import co.stackpointer.cier.modelo.utilidad.UtilCadena;
import co.stackpointer.cier.modelo.utilidad.UtilConsulta;
import co.stackpointer.cier.vista.Utilidades;
import co.stackpointer.cier.vistacontrolador.bean.LoginBean;
import co.stackpointer.cier.vistacontrolador.bean.UsuarioSesionBean;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.bean.ManagedProperty;

/**
 *
 * @author cier
 */
public abstract class BaseComparador {

    @EJB
    protected ParametrosFacadeLocal facParam;
    @EJB
    protected EstructuraDPAFacadeLocal facEstDPA;
    @EJB
    protected PredioFacadeLocal factPredioDPA;
    @ManagedProperty(value = "#{usuarioSesion}")
    private UsuarioSesionBean usuarioSesion;
    @ManagedProperty(value = "#{login}")
    private LoginBean currentLogin;
    protected Integer periodoBase;
    protected Integer periodoComparado;
    protected List<Integer> periodosBases;
    protected List<Integer> periodosAComparar;
    protected AmbitoComparado ambitoComparado = AmbitoComparado.GENERALIDADES;
    protected EspacioFuncional seleccionEspacioFuncional = EspacioFuncional.AULA;
    protected String seleccionCondMaterialidadPredio;
    protected CalificacionCondicion seleccionCondicion = CalificacionCondicion.UNO;
    protected String codigoPais;
    protected Long maximoNivelDPA;
    protected int minimoNivelDPA;
    protected Map<Long, String> listaNivelesConf;
    protected List<? extends NivelDPA> listaNivel1;
    protected List<? extends NivelDPA> listaNivel2;
    protected List<? extends NivelDPA> listaNivel3;
    protected List<? extends NivelDPA> listaNivel4;
    protected List<? extends NivelDPA> listaNivel5;
    protected List<? extends EstablecimientoDPA> listaEstablecimientos;
    protected List<? extends SedeDPA> listaSedes;
    protected List<? extends PredioDPA> listaPredios;
    protected List<AmbitoComparado> listaAmbitoComparado;
    protected NivelDPA seleccionNivel0;
    protected NivelDPA seleccionNivel1;
    protected NivelDPA seleccionNivel2;
    protected NivelDPA seleccionNivel3;
    protected NivelDPA seleccionNivel4;
    protected NivelDPA seleccionNivel5;
    protected boolean swNivelEspecifico;
    protected Nivel padreNivel0;
    protected Nivel padreNivel1;
    protected Nivel padreNivel2;
    protected Nivel padreNivel3;
    protected Nivel padreNivel4;
    protected Nivel padreNivel5;
    protected EstablecimientoDPA seleccionEst;
    protected SedeDPA seleccionSede;
    protected PredioDPA seleccionPredio;
    public String mensajeTablaVacia = "";
    protected String nombreNivelInicial;
    protected String nombreDPAInicial;
    protected boolean swNivel1;
    protected boolean swNivel2;
    protected boolean swNivel3;
    protected boolean swNivel4;
    protected boolean swNivel5;

    public abstract void resetearFiltros();

    public void init(Integer periodo) {
        this.periodoBase = periodo;
        this.cargarConfiguracionDPA();
        nivelDPAOfAccesoUsuario();
    }

    public void cargarConfiguracionDPA() {
        listaNivelesConf = new HashMap<Long, String>();
        // niveles DPA
        Usuario u = usuarioSesion.getUsuario();
        List<? extends ConfNivelDPA> configuraciones;

        if (UtilCadena.isNullOVacio(u.getNivelDpa())) {
            configuraciones = facEstDPA.consultarConfNivelesHis(periodoBase);
        } else {
            configuraciones = facEstDPA.consultarConfNivelesHis(periodoBase, u.getNivelDpa().getNivel());
        }

        for (ConfNivelDPA conf : configuraciones) {
            String descripcion = facEstDPA.consultarDescripcionNivelDPA(conf.getNivel(), periodoBase, usuarioSesion.getUsuario().getIdioma().getId());
            descripcion = descripcion != null ? descripcion : conf.getDescripcion();
            this.listaNivelesConf.put(conf.getNivel(), descripcion);
        }
        // maximo nivel DPA
        if (!configuraciones.isEmpty()) {
            int indice = configuraciones.size() - 1;
            this.maximoNivelDPA = configuraciones.get(indice).getNivel();
            this.minimoNivelDPA = configuraciones.get(0).getNivel().intValue();
        } else {
            this.maximoNivelDPA = -1L;
            this.minimoNivelDPA = -1;
        }
        // otros niveles
        this.listaNivelesConf.put(ParamNivelConsul.ESTABLECIMIENTO.getNivel(), Utilidades.obtenerMensaje(ParamNivelConsul.ESTABLECIMIENTO.getEtiqueta()));
        this.listaNivelesConf.put(ParamNivelConsul.SEDE.getNivel(), Utilidades.obtenerMensaje(ParamNivelConsul.SEDE.getEtiqueta()));
        this.listaNivelesConf.put(ParamNivelConsul.PREDIO.getNivel(), Utilidades.obtenerMensaje(ParamNivelConsul.PREDIO.getEtiqueta()));
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
                break;
            case 3:
                padreNivel2 = nivel.getPadre();
                padreNivel1 = padreNivel2.getPadre();
                padreNivel0 = padreNivel1.getPadre();
                break;
            case 4:
                padreNivel3 = nivel.getPadre();
                padreNivel2 = padreNivel3.getPadre();
                padreNivel1 = padreNivel2.getPadre();
                padreNivel0 = padreNivel1.getPadre();
                break;
            case 5:
                padreNivel4 = nivel.getPadre();
                padreNivel3 = padreNivel4.getPadre();
                padreNivel2 = padreNivel3.getPadre();
                padreNivel1 = padreNivel2.getPadre();
                padreNivel0 = padreNivel1.getPadre();
                break;
        }
    }

    public void nivelDPAOfAccesoUsuario() {
        Nivel n = usuarioSesion.getUsuario().getNivelEspecifico() != null
                ? usuarioSesion.getUsuario().getNivelEspecifico()
                : null;
        if (n != null) {
            this.nombreNivelInicial = n.getConfiguracion().getDescripcion();
            this.nombreDPAInicial = n.getDescripcion();
            int nivel = n.getConfiguracion().getNivel().intValue();
            switch (nivel) {
                case 0:
                    cargarNivel0();
                    break;
                case 1:
                    seleccionNivel1 = n;
                    cambioNivel1();
                    break;
                case 2:
                    seleccionNivel2 = n;
                    cambioNivel2();
                    break;
                case 3:
                    seleccionNivel3 = n;
                    cambioNivel3();
                    break;
                case 4:
                    seleccionNivel4 = n;
                    cambioNivel4();
                    break;
                case 5:
                    seleccionNivel5 = n;
                    cambioNivel5();
                    break;
            }
            cargarPadres(n);
            activarNiveles(nivel);
        } else {
            cargarNivel0();
            activarNiveles(0);
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

    public boolean isActivoNivel0() {
        return listaNivelesConf.containsKey(ParamNivelConsul.CERO.getNivel());
    }

    public boolean isActivoNivel1() {
        return swNivel1;
        //return listaNivelesConf.containsKey(ParamNivelConsul.UNO.getNivel());
    }

    public boolean isActivoNivel2() {
        return swNivel2;
        //return listaNivelesConf.containsKey(ParamNivelConsul.DOS.getNivel());
    }

    public boolean isActivoNivel3() {
        return swNivel3;
        //return listaNivelesConf.containsKey(ParamNivelConsul.TRES.getNivel());
    }

    public boolean isActivoNivel4() {
        return swNivel4;
        //return listaNivelesConf.containsKey(ParamNivelConsul.CUATRO.getNivel());
    }

    public boolean isActivoNivel5() {
        return swNivel5;
        //return listaNivelesConf.containsKey(ParamNivelConsul.CINCO.getNivel());
    }

    public Map<Long, String> getListaNivelesConf() {
        return listaNivelesConf;
    }

    private void resetearNivel1() {
        seleccionNivel1 = null;
        listaNivel1 = new ArrayList<NivelDPA>();
    }

    private void resetearNivel2() {
        seleccionNivel2 = null;
        listaNivel2 = new ArrayList<NivelDPA>();
    }

    private void resetearNivel3() {
        seleccionNivel3 = null;
        listaNivel3 = new ArrayList<NivelDPA>();
    }

    private void resetearNivel4() {
        seleccionNivel4 = null;
        listaNivel4 = new ArrayList<NivelDPA>();
    }

    private void resetearNivel5() {
        seleccionNivel5 = null;
        listaNivel5 = new ArrayList<NivelDPA>();
    }

    public void resetearListasEstSedesPredios() {
        seleccionEst = null;
        listaEstablecimientos = new ArrayList<EstablecimientoDPA>();
        this.resetearListasSedesPredios();
    }

    public void resetearListasSedesPredios() {
        seleccionSede = null;
        seleccionPredio = null;
        listaSedes = new ArrayList<SedeDPA>();
        listaPredios = new ArrayList<PredioDPA>();
    }

    public void resetearNiveles() {
        this.resetearNivel1();
        this.resetearNivel2();
        this.resetearNivel3();
        this.resetearNivel4();
        this.resetearNivel5();
    }

    private List<? extends NivelDPA> cargarNivel(NivelDPA nivelSeccionado, ParamNivelConsul nivelConsulta) {
        return facEstDPA.consultarNivelesHis(periodoBase, nivelSeccionado.getId());
    }

    private void cargarEstablecimientos(NivelDPA nivelSeccionado) {
        listaEstablecimientos = facEstDPA.consultarEstablecimientosHis(periodoBase, nivelSeccionado.getId());
    }

    public void cargarNivel0() {
        this.codigoPais = currentLogin.getPais().getCodigoPais();

        NivelHis nh = facEstDPA.consultarNivelHis(periodoBase, codigoPais);
        this.nombreNivelInicial = nh.getConfiguracion().getDescripcion();
        this.nombreDPAInicial = nh.getDescripcion();
        seleccionNivel0 = nh;

        this.cargarNivel1();
    }

    public void cargarNivel1() {
        this.resetearNivel1();
        this.resetearNivel2();
        this.resetearNivel3();
        this.resetearNivel4();
        this.resetearNivel5();
        this.resetearListasEstSedesPredios();
        if (seleccionNivel0 != null) {
            if (ParamNivelConsul.CERO.getNivel().equals(maximoNivelDPA)) {
                this.cargarEstablecimientos(seleccionNivel0);
            } else {
                listaNivel1 = this.cargarNivel(seleccionNivel0, ParamNivelConsul.UNO);
            }
        }
    }

    public abstract void cambioPeriodo();

    public void cambioNivel1() {
        this.resetearNivel2();
        this.resetearNivel3();
        this.resetearNivel4();
        this.resetearNivel5();
        this.resetearListasEstSedesPredios();
        if (seleccionNivel1 != null) {
            if (ParamNivelConsul.UNO.getNivel().equals(maximoNivelDPA)) {
                this.cargarEstablecimientos(seleccionNivel1);
            } else {
                listaNivel2 = this.cargarNivel(seleccionNivel1, ParamNivelConsul.DOS);
            }
        }
    }

    public void cambioNivel2() {
        this.resetearNivel3();
        this.resetearNivel4();
        this.resetearNivel5();
        this.resetearListasEstSedesPredios();
        if (seleccionNivel2 != null) {
            if (ParamNivelConsul.DOS.getNivel().equals(maximoNivelDPA)) {
                this.cargarEstablecimientos(seleccionNivel2);
            } else {
                listaNivel3 = this.cargarNivel(seleccionNivel2, ParamNivelConsul.TRES);
            }
        }
    }

    public void cambioNivel3() {
        this.resetearNivel4();
        this.resetearNivel5();
        this.resetearListasEstSedesPredios();
        if (seleccionNivel3 != null) {
            if (ParamNivelConsul.TRES.getNivel().equals(maximoNivelDPA)) {
                this.cargarEstablecimientos(seleccionNivel3);
            } else {
                listaNivel4 = this.cargarNivel(seleccionNivel3, ParamNivelConsul.CUATRO);
            }
        }
    }

    public void cambioNivel4() {
        this.resetearNivel5();
        this.resetearListasEstSedesPredios();
        if (seleccionNivel4 != null) {
            if (ParamNivelConsul.CUATRO.getNivel().equals(maximoNivelDPA)) {
                this.cargarEstablecimientos(seleccionNivel4);
            } else {
                listaNivel5 = this.cargarNivel(seleccionNivel4, ParamNivelConsul.CINCO);
            }
        }
    }

    public void cambioNivel5() {
        this.resetearListasEstSedesPredios();
        if (seleccionNivel5 != null && ParamNivelConsul.CINCO.getNivel().equals(maximoNivelDPA)) {
            this.cargarEstablecimientos(seleccionNivel5);
        }
    }

    public void cambioEstablecimiento() {
        this.resetearListasSedesPredios();
        if (seleccionEst != null) {
            listaSedes = seleccionEst.getSedes();
        }
    }

    public void cambioSede() {
        seleccionPredio = null;
        listaPredios = seleccionSede != null ? seleccionSede.getPredios() : new ArrayList<PredioDPA>();
    }

//    public void cambioSedeCreacionPredio() {
//       
//     listaPredios= factPredioDPA.obtenerPrediosPorSede(String.valueOf(seleccionSede.getNivel()));
//     
//    }
    public List<? extends NivelDPA> getListaNivel1() {
        return listaNivel1;
    }

    public List<? extends NivelDPA> getListaNivel2() {
        return listaNivel2;
    }

    public List<? extends NivelDPA> getListaNivel3() {
        return listaNivel3;
    }

    public List<? extends NivelDPA> getListaNivel4() {
        return listaNivel4;
    }

    public List<? extends NivelDPA> getListaNivel5() {
        return listaNivel5;
    }

    public List<? extends EstablecimientoDPA> getListaEstablecimientos() {
        return listaEstablecimientos;
    }

    public List<? extends SedeDPA> getListaSedes() {
        return listaSedes;
    }

    public List<? extends PredioDPA> getListaPredios() {
        return listaPredios;
    }

    public List<AmbitoComparado> getListaAmbitoComparado() {
        return Arrays.asList(AmbitoComparado.values());
    }
    
    public List<EspacioFuncional> getListaEspaciosFuncionales() {
        return Arrays.asList(EspacioFuncional.values());
    }
    
     public List<CalificacionCondicion> getListaCondiciones() {
         return Arrays.asList(CalificacionCondicion.values());
    }

    public NivelDPA getSeleccionNivel0() {
        return seleccionNivel0;
    }

    public void setSeleccionNivel0(NivelDPA seleccionNivel0) {
        this.seleccionNivel0 = seleccionNivel0;
    }

    public NivelDPA getSeleccionNivel1() {
        return seleccionNivel1;
    }

    public void setSeleccionNivel1(NivelDPA seleccionNivel1) {
        this.seleccionNivel1 = seleccionNivel1;
    }

    public NivelDPA getSeleccionNivel2() {
        return seleccionNivel2;
    }

    public void setSeleccionNivel2(NivelDPA seleccionNivel2) {
        this.seleccionNivel2 = seleccionNivel2;
    }

    public NivelDPA getSeleccionNivel3() {
        return seleccionNivel3;
    }

    public void setSeleccionNivel3(NivelDPA seleccionNivel3) {
        this.seleccionNivel3 = seleccionNivel3;
    }

    public NivelDPA getSeleccionNivel4() {
        return seleccionNivel4;
    }

    public void setSeleccionNivel4(NivelDPA seleccionNivel4) {
        this.seleccionNivel4 = seleccionNivel4;
    }

    public NivelDPA getSeleccionNivel5() {
        return seleccionNivel5;
    }

    public void setSeleccionNivel5(NivelDPA seleccionNivel5) {
        this.seleccionNivel5 = seleccionNivel5;
    }

    public EstablecimientoDPA getSeleccionEst() {
        return seleccionEst;
    }

    public void setSeleccionEst(EstablecimientoDPA seleccionEst) {
        this.seleccionEst = seleccionEst;
    }

    public SedeDPA getSeleccionSede() {
        return seleccionSede;
    }

    public void setSeleccionSede(SedeDPA seleccionSede) {
        this.seleccionSede = seleccionSede;
    }

    public PredioDPA getSeleccionPredio() {
        return seleccionPredio;
    }

    public void setSeleccionPredio(PredioDPA seleccionPredio) {
        this.seleccionPredio = seleccionPredio;
    }

    public Integer getPeriodoBase() {
        return periodoBase;
    }

    public void setPeriodoBase(Integer periodoBase) {
        this.periodoBase = periodoBase;
    }

    public Integer getPeriodoComparado() {
        return periodoComparado;
    }

    public void setPeriodoComparado(Integer periodoComparado) {
        this.periodoComparado = periodoComparado;
    }
    
    public void cargarPeriodosBases() {
        periodosBases = facParam.consultarPeriodosCalculadosIndicadores();        
    }
    
    public void cargarPeriodosAComparar() {
        periodosAComparar = facParam.consultarPeriodosCalculadosIndicadores(periodoBase);   
    }

    public List<Integer> getPeriodosBases() {
        return periodosBases;
    }

    public List<Integer> getPeriodosAComparar() {
        return periodosAComparar;
    }
    
    public AmbitoComparado getAmbitoComparado() {
        return ambitoComparado;
    }

    public void setAmbitoComparado(AmbitoComparado ambitoComparado) {
        this.ambitoComparado = ambitoComparado;
    }

    public EspacioFuncional getSeleccionEspacioFuncional() {
        return seleccionEspacioFuncional;
    }

    public void setSeleccionEspacioFuncional(EspacioFuncional seleccionEspacioFuncional) {
        this.seleccionEspacioFuncional = seleccionEspacioFuncional;
    }

    public String getSeleccionCondMaterialidadPredio() {
        return seleccionCondMaterialidadPredio;
    }

    public void setSeleccionCondMaterialidadPredio(String seleccionCondMaterialidadPredio) {
        this.seleccionCondMaterialidadPredio = seleccionCondMaterialidadPredio;
    }

    public CalificacionCondicion getSeleccionCondicion() {
        return seleccionCondicion;
    }

    public void setSeleccionCondicion(CalificacionCondicion seleccionCondicion) {
        this.seleccionCondicion = seleccionCondicion;
    }
    
    public NivelDPA getUltimoNivelDPA() {
        if (ParamNivelConsul.CINCO.equals(maximoNivelDPA)) {
            return seleccionNivel5;
        } else if (ParamNivelConsul.CUATRO.equals(maximoNivelDPA)) {
            return seleccionNivel4;
        } else if (ParamNivelConsul.TRES.equals(maximoNivelDPA)) {
            return seleccionNivel3;
        } else if (ParamNivelConsul.DOS.equals(maximoNivelDPA)) {
            return seleccionNivel2;
        } else if (ParamNivelConsul.UNO.equals(maximoNivelDPA)) {
            return seleccionNivel1;
        } else if (ParamNivelConsul.CERO.equals(maximoNivelDPA)) {
            return seleccionNivel0;
        } else {
            return null;
        }
    }

    public NivelDPA getUltimoNivelSeleccionado() {
        if (seleccionNivel5 != null) {
            return seleccionNivel5;
        } else if (seleccionNivel4 != null) {
            return seleccionNivel4;
        } else if (seleccionNivel3 != null) {
            return seleccionNivel3;
        } else if (seleccionNivel2 != null) {
            return seleccionNivel2;
        } else if (seleccionNivel1 != null) {
            return seleccionNivel1;
        } else if (seleccionNivel0 != null) {
            return seleccionNivel0;
        } else {
            return null;
        }
    }

    public String getFormatoDecimal() {
        return UtilConsulta.formatoDecimal;
    }

    public String getFormatoMoneda() {
        return UtilConsulta.formatoMoneda;
    }

    public String getFormatoPorcentaje() {
        return UtilConsulta.formatoPorcentaje;
    }

    public String getFormatoEntero() {
        return UtilConsulta.formatoEntero;
    }

    public String getMensajeTablaVacia() {
        return mensajeTablaVacia;
    }

    public void setMensajeTablaVacia(String mensajeTablaVacia) {
        this.mensajeTablaVacia = mensajeTablaVacia;
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

    public int getMinimoNivelDPA() {
        return minimoNivelDPA;
    }

    public String getNombreNivelInicial() {
        return nombreNivelInicial;
    }

    public String getNombreDPAInicial() {
        return nombreDPAInicial;
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
    
    public boolean isCumpleNorma(Object idoneidad) {
        if (idoneidad != null) {
            Double valor = (Double)idoneidad / 100D;
            return (valor.compareTo(1D) == 0 || valor.compareTo(1D) > 0) ? true : false;
        } else {
            return false;
        }
    }
    
    public boolean isNoCumpleNorma(Object idoneidad) {
        if (idoneidad != null) {
            Double valor = (Double)idoneidad / 100D;
            return (valor.compareTo(1D) < 0) ? true : false;
        } else {
            return false;
        }
    }
    
    public boolean isAmbitoGeneralidades() {
        return AmbitoComparado.GENERALIDADES.equals(ambitoComparado);
    }
    
    public boolean isAmbitoRedistribuccion() {
        return AmbitoComparado.REDISTRIBUCION.equals(ambitoComparado);
    }
    
    public boolean isAmbitoAmbientes() {
        return AmbitoComparado.AMBIENTES.equals(ambitoComparado);
    }
    
    public boolean isAmbitoAmpliacion() {
        return AmbitoComparado.AMPLIACION.equals(ambitoComparado);
    }
    
    public boolean isAmbitoEspacios() {
        return AmbitoComparado.ESPACIOS.equals(ambitoComparado);
    }
    
    public boolean isAmbitoEdificios() {
        return AmbitoComparado.EDIFICIOS.equals(ambitoComparado);
    }
}
