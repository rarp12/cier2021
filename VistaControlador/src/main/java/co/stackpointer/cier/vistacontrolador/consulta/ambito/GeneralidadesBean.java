/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.consulta.ambito;

import co.stackpointer.cier.modelo.EntidadesConsultas.ConsultaAmbitoGeneralidades;
import co.stackpointer.cier.modelo.FachadasConsultas.ConsultaAmbitoGeneralidadesFacadeLocal;
import co.stackpointer.cier.modelo.entidad.instrumento.TipologiaValorNombre;
import co.stackpointer.cier.modelo.filtro.ambito.FiltroAmbitoGeneralidad;
import co.stackpointer.cier.modelo.tipo.TipologiaCodigo;
import co.stackpointer.cier.modelo.utilidad.UtilConsulta;
import co.stackpointer.cier.vista.Utilidades;
import co.stackpointer.cier.vistacontrolador.consulta.ambito.lazyGeneralidades.LazyGeneralidadesDataModel;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author user
 */
@ManagedBean(name = "generalidadesBean")
@ViewScoped
public class GeneralidadesBean extends ConsultaAmbito {

    @ManagedProperty(value = "#{consultaAmbito}")
    private ConsultaAmbito consultaAmbito;
    private TipologiaValorNombre seleccionNivelEnseñanza;
    private TipologiaValorNombre seleccionJornada;
    private Registro totalConsulta = new Registro();
    private List<TipologiaValorNombre> listaNivelesEnseñanza;
    private List<TipologiaValorNombre> listaJornadas;
    private List<Registro> lista;
    private List<ConsultaAmbitoGeneralidades> listaGeneralidades;
    private FiltroAmbitoGeneralidad filtro = new FiltroAmbitoGeneralidad();
    final String columnaNumJorEst = Utilidades.obtenerMensaje("consultas.ambito.generalidades.columna.num.jor.est");
    final String columnaNivelEnse = Utilidades.obtenerMensaje("consultas.ambito.generalidades.columna.nivel.ense");
    final String columnaEstuNivelEnse = Utilidades.obtenerMensaje("consultas.ambito.generalidades.columna.estudiantes.nivel.ense");
    final String columnaEstudiantesTotal = Utilidades.obtenerMensaje("consultas.ambito.generalidades.columna.estudiantes.total");
    final String columnaAreaConstruccion = Utilidades.obtenerMensaje("consultas.ambito.generalidades.columna.area.construccion.total");
    final String columnaNumEstablecimientos = Utilidades.obtenerMensaje("consultas.ambito.generalidades.columna.num.establecimientos");
    final String columnaNumSedes = Utilidades.obtenerMensaje("consultas.ambito.generalidades.columna.num.sedes");
    final String columnaNumPredios = Utilidades.obtenerMensaje("consultas.ambito.generalidades.columna.num.predios");
    final String columnaAreaRealPredio = Utilidades.obtenerMensaje("consultas.ambito.generalidades.columna.area.real.predio");
    ConsultaAmbitoGeneralidades consultaGeneralidades = new ConsultaAmbitoGeneralidades();
    @EJB
    protected ConsultaAmbitoGeneralidadesFacadeLocal consultaGeneralidadesFacade;
    private LazyDataModel<Registro> lazyLista;

    @PostConstruct
    public void inicializar() {
        listaNivelesEnseñanza = facParam.consultarValoresTipologia(TipologiaCodigo.NIVEL_ENSEÑAZA.getValor(), getUsuarioSesion().getUsuario().getIdioma().getId());
        listaJornadas = facParam.consultarValoresTipologia(TipologiaCodigo.JORNADA.getValor(), getUsuarioSesion().getUsuario().getIdioma().getId());
        lista = new ArrayList<Registro>();
        this.cargarCamposOpcionales();
    }

    @Override
    public void resetearFiltros() {
        consultaAmbito.resetearNiveles();
        this.resetearListasEstSedesPredios();
        this.seleccionSector = null;
        this.seleccionJornada = null;
        this.seleccionZona = null;
        this.seleccionNivelEnseñanza = null;
        consultaAmbito.nivelDPAOfAccesoUsuario();
        this.resetearConsulta();
        this.lazyLista = null;
    }

    @Override
    public void cambioAgrupamiento() {
        //filtros
        this.seleccionNivelEnseñanza = null;
        this.resetearConsulta();
    }

    public void cambioFiltroNivelEnseñaza() {
        this.resetearConsulta();
    }

    private void resetearConsulta() {
        this.cargarCamposOpcionales();
        // resultados
        this.lista.clear();
        //listaGeneralidades.clear();
        this.inicializarTotales();
    }

    public void consultar() {
        filtro.inicializar();
        filtro.setPeriodo(periodo);
        filtro.setIdioma(getUsuarioSesion().getUsuario().getIdioma().getId());
        this.lista.clear();

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
        filtro.setCodJornada(seleccionJornada != null ? seleccionJornada.getTipValor().getCodigo() : null);
        filtro.setCodNivelEnseñaza(seleccionNivelEnseñanza != null ? seleccionNivelEnseñanza.getTipValor().getCodigo() : null);

        List<Object[]> resultados = facAmbito.consultarGeneralidades(filtro);
        lista = new ArrayList<Registro>(resultados.size());
        if (lista.size() < 1) {
            mensajeTablaVacia = Utilidades.obtenerMensaje("aplicacion.general.noExistenRegistros");
        }
        this.inicializarTotales();
        if (filtro.isAgrupamientoPredio()) {
            for (Object[] fila : resultados) {
                Registro registro = new Registro();
                int i = 0;
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
                registro.setNomPredio(UtilConsulta.stringValueOf(fila[i++]));//11 Nombre Predio
                //otros
                registro.setDirPredio(UtilConsulta.stringValueOf(fila[i++]));
                registro.setZona(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]), getUsuarioSesion().getUsuario().getIdioma().getId()));
                registro.setSector(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]), getUsuarioSesion().getUsuario().getIdioma().getId()));
                registro.setClima(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]), getUsuarioSesion().getUsuario().getIdioma().getId()));
                registro.setEtnias(verificarEtnias(UtilConsulta.stringValueOf(fila[i++])));//16 Etnias
                if (filtro.getCodNivelEnseñaza() != null) {
                    //17
                    registro.setNivelEnseñanza(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]), getUsuarioSesion().getUsuario().getIdioma().getId()));
                    //18
                    registro.setNumEstNivelEnseñanza(UtilConsulta.longValueOf(fila[i++]));
                }
                // indicadores
                registro.setNumJornadasEstudiantes(UtilConsulta.longValueOf(fila[i++]));
                registro.setTotalEstudiantes(UtilConsulta.longValueOf(fila[i++]));
                registro.setAreaConstruccion(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setAreaRealPredio(UtilConsulta.doubleValueOf(fila[i++]));
                // totales
                //this.totalizarRegistro(registro);
                lista.add(registro);
            }
        } else {
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
                }
                // indicadores
                registro.setTotalEstudiantes(UtilConsulta.longValueOf(fila[i++]));
                if (!filtro.isAgrupamientoSede()) {
                    if (!filtro.isAgrupamientoEstablecimiento()) {
                        registro.setNumEstablecimientos(UtilConsulta.longValueOf(fila[i++]));
                    }
                    registro.setNumSedes(UtilConsulta.longValueOf(fila[i++]));
                }
                registro.setNumPredios(UtilConsulta.longValueOf(fila[i++]));
                // totales
                //this.totalizarRegistro(registro);
                lista.add(registro);
            }
        }
        lazyLista = new LazyGeneralidadesDataModel(lista);
    }

    public void consultarBaseDeDatos() throws Exception {
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
        filtro.setCodJornada(seleccionJornada != null ? seleccionJornada.getTipValor().getCodigo() : null);
        filtro.setCodNivelEnseñaza(seleccionNivelEnseñanza != null ? seleccionNivelEnseñanza.getTipValor().getCodigo() : null);
        /*if (ParamBaseDatos.ORACLE.equals(UtilProperties.getDbMotor())) {
            listaGeneralidades = consultaGeneralidadesFacade.generarConsultaAmbitoGeneralidades(filtro, getUsuarioSesion().getUsuario().getIdioma().getId());
        } else {*/
            consultar();
        //}


    }

    public List<TipologiaValorNombre> getListaJornadas() {
        return listaJornadas;
    }

    public void setListaJornadas(List<TipologiaValorNombre> listaJornadas) {
        this.listaJornadas = listaJornadas;
    }

    public List<TipologiaValorNombre> getListaNivelesEnseñanza() {
        return listaNivelesEnseñanza;
    }

    public void setListaNivelesEnseñanza(List<TipologiaValorNombre> listaNivelesEnseñanza) {
        this.listaNivelesEnseñanza = listaNivelesEnseñanza;
    }

    public TipologiaValorNombre getSeleccionNivelEnseñanza() {
        return seleccionNivelEnseñanza;
    }

    public void setSeleccionNivelEnseñanza(TipologiaValorNombre seleccionNivelEnseñanza) {
        this.seleccionNivelEnseñanza = seleccionNivelEnseñanza;
    }

    public TipologiaValorNombre getSeleccionJornada() {
        return seleccionJornada;
    }

    public void setSeleccionJornada(TipologiaValorNombre seleccionJornada) {
        this.seleccionJornada = seleccionJornada;
    }

    public List<Registro> getLista() {
        return lista;
    }

    public Registro getTotalConsulta() {
        return totalConsulta;
    }

    public class Registro {

        private String codEstablecimiento;
        private String nomEstablecimiento;
        private String codSede;
        private String nomSede;
        private String codPredio;
        private String nomPredio;
        private String dirPredio;
        private String codNivel1;
        private String codNivel2;
        private String codNivel3;
        private String codNivel4;
        private String codNivel5;
        private String nivel0;
        private String nivel1;
        private String nivel2;
        private String nivel3;
        private String nivel4;
        private String nivel5;
        private String zona;
        private String sector;
        private String clima;
        private String etnias;
        private Long numJornadasEstudiantes;
        private String nivelEnseñanza;
        private Long numEstNivelEnseñanza;
        private Long totalEstudiantes;
        private Double areaConstruccion;
        private Double areaRealPredio;
        private Long numEstablecimientos;
        private Long numSedes;
        private Long numPredios;

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

        public Long getNumJornadasEstudiantes() {
            return numJornadasEstudiantes;
        }

        public void setNumJornadasEstudiantes(Long numJornadasEstudiantes) {
            this.numJornadasEstudiantes = numJornadasEstudiantes;
        }

        public String getNivelEnseñanza() {
            return nivelEnseñanza;
        }

        public void setNivelEnseñanza(String nivelEnseñanza) {
            this.nivelEnseñanza = nivelEnseñanza;
        }

        public Long getNumEstNivelEnseñanza() {
            return numEstNivelEnseñanza;
        }

        public void setNumEstNivelEnseñanza(Long numEstNivelEnseñanza) {
            this.numEstNivelEnseñanza = numEstNivelEnseñanza;
        }

        public Long getTotalEstudiantes() {
            return totalEstudiantes;
        }

        public void setTotalEstudiantes(Long totalEstudiantes) {
            this.totalEstudiantes = totalEstudiantes;
        }

        public Double getAreaConstruccion() {
            return areaConstruccion;
        }

        public void setAreaConstruccion(Double areaConstruccion) {
            this.areaConstruccion = areaConstruccion;
        }

        public Double getAreaRealPredio() {
            return areaRealPredio;
        }

        public void setAreaRealPredio(Double areaRealPredio) {
            this.areaRealPredio = areaRealPredio;
        }

        public Long getNumEstablecimientos() {
            return numEstablecimientos;
        }

        public void setNumEstablecimientos(Long numEstablecimientos) {
            this.numEstablecimientos = numEstablecimientos;
        }

        public Long getNumSedes() {
            return numSedes;
        }

        public void setNumSedes(Long numSedes) {
            this.numSedes = numSedes;
        }

        public Long getNumPredios() {
            return numPredios;
        }

        public void setNumPredios(Long numPredios) {
            this.numPredios = numPredios;
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
    }

    public void inicializarTotales() {
        totalConsulta.numJornadasEstudiantes = 0L;
        totalConsulta.numEstNivelEnseñanza = 0L;
        totalConsulta.totalEstudiantes = 0L;
        totalConsulta.areaConstruccion = 0D;
        totalConsulta.areaRealPredio = 0D;
        totalConsulta.numEstablecimientos = 0L;
        totalConsulta.numSedes = 0L;
        totalConsulta.numPredios = 0L;
    }

    private void totalizarRegistro(Registro registro) {
        if (registro.getNumJornadasEstudiantes() != null) {
            totalConsulta.setNumJornadasEstudiantes(totalConsulta.getNumJornadasEstudiantes() + registro.getNumJornadasEstudiantes());
        }
        if (registro.getNumEstNivelEnseñanza() != null) {
            totalConsulta.setNumEstNivelEnseñanza(totalConsulta.getNumEstNivelEnseñanza() + registro.getNumEstNivelEnseñanza());
        }
        if (registro.getTotalEstudiantes() != null) {
            totalConsulta.setTotalEstudiantes(totalConsulta.getTotalEstudiantes() + registro.getTotalEstudiantes());
        }
        if (registro.getAreaConstruccion() != null) {
            totalConsulta.setAreaConstruccion(totalConsulta.getAreaConstruccion() + registro.getAreaConstruccion());
        }
        if (registro.getAreaRealPredio() != null) {
            totalConsulta.setAreaRealPredio(totalConsulta.getAreaRealPredio() + registro.getAreaRealPredio());
        }
        if (registro.getNumEstablecimientos() != null) {
            totalConsulta.setNumEstablecimientos(totalConsulta.getNumEstablecimientos() + registro.getNumEstablecimientos());
        }
        if (registro.getNumSedes() != null) {
            totalConsulta.setNumSedes(totalConsulta.getNumSedes() + registro.getNumSedes());
        }
        if (registro.getNumPredios() != null) {
            totalConsulta.setNumPredios(totalConsulta.getNumPredios() + registro.getNumPredios());
        }
    }

    private void cargarCamposOpcionales() {
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
        if (isMostrarColNumJornadasEstudiante()) {
            listaCamposOpcionales.add(columnaNumJorEst);
            selccionCamposOpcionales.add(columnaNumJorEst);
        }
        if (isMostrarColNivelEnseñanza()) {
            listaCamposOpcionales.add(columnaNivelEnse);
            selccionCamposOpcionales.add(columnaNivelEnse);
        }
        if (isMostrarColNumEstNivelEnseñanza()) {
            listaCamposOpcionales.add(columnaEstuNivelEnse);
            selccionCamposOpcionales.add(columnaEstuNivelEnse);
        }
        if (isMostrarColTotalEstudiantes()) {
            listaCamposOpcionales.add(columnaEstudiantesTotal);
            selccionCamposOpcionales.add(columnaEstudiantesTotal);
        }
        if (isMostrarColAreaContruccionTotal()) {
            listaCamposOpcionales.add(columnaAreaConstruccion);
            selccionCamposOpcionales.add(columnaAreaConstruccion);
        }
        if (isMostrarColAreaRealPredio()) {
            listaCamposOpcionales.add(columnaAreaRealPredio);
            selccionCamposOpcionales.add(columnaAreaRealPredio);
        }
        if (isMostrarColNumEstablicimientos()) {
            listaCamposOpcionales.add(columnaNumEstablecimientos);
            selccionCamposOpcionales.add(columnaNumEstablecimientos);
        }
        if (isMostrarColNumSedes()) {
            listaCamposOpcionales.add(columnaNumSedes);
            selccionCamposOpcionales.add(columnaNumSedes);
        }
        if (isMostrarColNumPredios()) {
            listaCamposOpcionales.add(columnaNumPredios);
            selccionCamposOpcionales.add(columnaNumPredios);
        }
    }

    // mostrar columnas    
    // indicadores        
    public boolean isMostrarColNumJornadasEstudiante() {
        return isAgrupamientoPredio();
    }

    public boolean isMostrarColNivelEnseñanza() {
        return isAgrupamientoPredio() && seleccionNivelEnseñanza != null;
    }

    public boolean isMostrarColNumEstNivelEnseñanza() {
        return isAgrupamientoPredio() && seleccionNivelEnseñanza != null;
    }

    public boolean isMostrarColTotalEstudiantes() {
        return true;
    }

    public boolean isMostrarColAreaContruccionTotal() {
        return isAgrupamientoPredio();
    }

    public boolean isMostrarColAreaRealPredio() {
        return isAgrupamientoPredio();
    }

    public boolean isMostrarColNumEstablicimientos() {
        return !isAgrupamientoPredio() && !isAgrupamientoSede() && !isAgrupamientoEstablecimiento();
    }

    public boolean isMostrarColNumSedes() {
        return !isAgrupamientoPredio() && !isAgrupamientoSede();
    }

    public boolean isMostrarColNumPredios() {
        return !isAgrupamientoPredio();
    }
// columnas

    public String getColumnaNumJorEst() {
        return columnaNumJorEst;
    }

    public String getColumnaNivelEnse() {
        return columnaNivelEnse;
    }

    public String getColumnaEstuNivelEnse() {
        return columnaEstuNivelEnse;
    }

    public String getColumnaEstudiantesTotal() {
        return columnaEstudiantesTotal;
    }

    public String getColumnaAreaConstruccion() {
        return columnaAreaConstruccion;
    }

    public String getColumnaNumEstablecimientos() {
        return columnaNumEstablecimientos;
    }

    public String getColumnaNumSedes() {
        return columnaNumSedes;
    }

    public String getColumnaNumPredios() {
        return columnaNumPredios;
    }

    public String getColumnaAreaRealPredio() {
        return columnaAreaRealPredio;
    }

    public ConsultaAmbito getConsultaAmbito() {
        return consultaAmbito;
    }

    public void setConsultaAmbito(ConsultaAmbito consultaAmbito) {
        this.consultaAmbito = consultaAmbito;
    }

    public List<ConsultaAmbitoGeneralidades> getListaGeneralidades() {
        return listaGeneralidades;
    }

    public void setListaGeneralidades(List<ConsultaAmbitoGeneralidades> listaGeneralidades) {
        this.listaGeneralidades = listaGeneralidades;
    }

    public LazyDataModel<Registro> getLazyLista() {
        return lazyLista;
    }

    public void setLazyLista(LazyDataModel<Registro> lazyLista) {
        this.lazyLista = lazyLista;
    }
    
    
}