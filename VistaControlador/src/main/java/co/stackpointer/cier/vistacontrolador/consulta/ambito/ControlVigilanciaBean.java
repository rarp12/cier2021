/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.consulta.ambito;

import co.stackpointer.cier.modelo.filtro.ambito.FiltroAmbitoControlVigilancia;
import co.stackpointer.cier.modelo.tipo.CalificacionCondicion;
import co.stackpointer.cier.modelo.tipo.SiNo;
import co.stackpointer.cier.modelo.tipo.Vulnerabilidad;
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
@ManagedBean(name = "controlVigilancia")
@ViewScoped
public class ControlVigilanciaBean extends ConsultaAmbito {

    @ManagedProperty(value = "#{consultaAmbito}")
    private ConsultaAmbito consultaAmbito;
    private CalificacionCondicion seleccionCondicionOrdenPublico;
    private SiNo seleccionSistemaIluminacion;
    private SiNo seleccionSistemaCamara;
    private Vulnerabilidad seleccionVulnerabilidad;
    private List<Registro> lista;
    private FiltroAmbitoControlVigilancia filtro = new FiltroAmbitoControlVigilancia();
    private TotalConsulta totalConsulta = new TotalConsulta();
    final String columnaTipoCerramiento = Utilidades.obtenerMensaje("consultas.ambito.ctrlvigi.columna.tipoCerramiento");
    final String columnaCondicionCerramiento = Utilidades.obtenerMensaje("consultas.ambito.ctrlvigi.columna.condicionCerramiento");
    final String columnaCondicionOrdenPublico = Utilidades.obtenerMensaje("consultas.ambito.ctrlvigi.columna.condicionOrdenPublico");
    final String columnaVulnerabilidad = Utilidades.obtenerMensaje("consultas.ambito.ctrlvigi.columna.vulnerabilidad");
    final String columnaEdificiosSistemaIluminacion = Utilidades.obtenerMensaje("consultas.ambito.ctrlvigi.columna.edificiosSistemaIluminacion");
    final String columnaProporcionSistemaIluminacion = Utilidades.obtenerMensaje("consultas.ambito.ctrlvigi.columna.proporcionSistemaIluminacion");
    final String columnaEdificiosSistemaCamara = Utilidades.obtenerMensaje("consultas.ambito.ctrlvigi.columna.edificiosSistemaCamara");
    final String columnaProporcionSistemaCamara = Utilidades.obtenerMensaje("consultas.ambito.ctrlvigi.columna.proporcionSistemaCamara");
    final String columnaTotalEdificios = Utilidades.obtenerMensaje("consultas.ambito.ctrlvigi.columna.totalEdificios");
    final String columnaPrediosOrdenPublico = Utilidades.obtenerMensaje("consultas.ambito.ctrlvigi.columna.prediosOrdenPublico");
    final String columnaProporcionPrediosOrdenPublico = Utilidades.obtenerMensaje("consultas.ambito.ctrlvigi.columna.proporcionPrediosOrdenPublico");
    final String columnaPrediosVulnerabilidad = Utilidades.obtenerMensaje("consultas.ambito.ctrlvigi.columna.prediosVulnerabilidad");
    final String columnaProporcionVulnerabilidad = Utilidades.obtenerMensaje("consultas.ambito.ctrlvigi.columna.proporcionVulnerabilidad");
    final String columnaTotalPredios = Utilidades.obtenerMensaje("consultas.ambito.ctrlvigi.columna.totalPredios");
    final String columnaCondicionAccesibilidad = Utilidades.obtenerMensaje("consultas.ambito.ctrlvigi.columna.condicionAccesibilidad");

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
        if (isMostrarColPropiedadPredio()) {
            listaCamposOpcionales.add(columnaPropPredio);
        }
        if (isMostrarColCondicionAccesibilidad()) {
            listaCamposOpcionales.add(columnaCondicionAccesibilidad);
        }
        //propias del ambito  
        if (isMostrarColTipoCerramiento()) {
            listaCamposOpcionales.add(columnaTipoCerramiento);
            selccionCamposOpcionales.add(columnaTipoCerramiento);
        }
        if (isMostrarColCondicionCerramiento()) {
            listaCamposOpcionales.add(columnaCondicionCerramiento);
            selccionCamposOpcionales.add(columnaCondicionCerramiento);
        }
        if (isMostrarColCondicionOrdenPublico()) {
            listaCamposOpcionales.add(columnaCondicionOrdenPublico);
            selccionCamposOpcionales.add(columnaCondicionOrdenPublico);
        }
        if (isMostrarColVulnerabilidad()) {
            listaCamposOpcionales.add(columnaVulnerabilidad);
            selccionCamposOpcionales.add(columnaVulnerabilidad);
        }
        if (isMostrarColEdificiosSistemaIluminacion()) {
            listaCamposOpcionales.add(columnaEdificiosSistemaIluminacion);
            selccionCamposOpcionales.add(columnaEdificiosSistemaIluminacion);
        }
        if (isMostrarColProporcionSistemaIluminacion()) {
            listaCamposOpcionales.add(columnaProporcionSistemaIluminacion);
            selccionCamposOpcionales.add(columnaProporcionSistemaIluminacion);
        }
        if (isMostrarColEdificiosSistemaCamara()) {
            listaCamposOpcionales.add(columnaEdificiosSistemaCamara);
            selccionCamposOpcionales.add(columnaEdificiosSistemaCamara);
        }
        if (isMostrarColProporcionSistemaCamara()) {
            listaCamposOpcionales.add(columnaProporcionSistemaCamara);
            selccionCamposOpcionales.add(columnaProporcionSistemaCamara);
        }
        if (isMostrarColTotalEdificios()) {
            listaCamposOpcionales.add(columnaTotalEdificios);
            selccionCamposOpcionales.add(columnaTotalEdificios);
        }
        if (isMostrarColPrediosOrdenPublico()) {
            listaCamposOpcionales.add(columnaPrediosOrdenPublico);
            selccionCamposOpcionales.add(columnaPrediosOrdenPublico);
        }
        if (isMostrarColProporcionPrediosOrdenPublico()) {
            listaCamposOpcionales.add(columnaProporcionPrediosOrdenPublico);
            selccionCamposOpcionales.add(columnaProporcionPrediosOrdenPublico);
        }
        if (isMostrarColPrediosVulnerabilidad()) {
            listaCamposOpcionales.add(columnaPrediosVulnerabilidad);
            selccionCamposOpcionales.add(columnaPrediosVulnerabilidad);
        }
        if (isMostrarColProporcionVulnerabilidad()) {
            listaCamposOpcionales.add(columnaProporcionVulnerabilidad);
            selccionCamposOpcionales.add(columnaProporcionVulnerabilidad);
        }
        if (isMostrarColTotalPredios()) {
            listaCamposOpcionales.add(columnaTotalPredios);
            selccionCamposOpcionales.add(columnaTotalPredios);
        }
    }

    public void condicionSeleccionada() {
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
        this.seleccionCondicionOrdenPublico = null;
        this.seleccionSistemaIluminacion = null;
        this.seleccionSistemaCamara = null;
        this.seleccionVulnerabilidad = null;
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
        this.seleccionCondicionOrdenPublico = null;
        this.seleccionSistemaIluminacion = null;
        this.seleccionSistemaCamara = null;
        this.seleccionVulnerabilidad = null;
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
        filtro.setCondicionOrdenPublico(seleccionCondicionOrdenPublico != null ? seleccionCondicionOrdenPublico.getValor() : null);
        filtro.setSistemaIluminacion(seleccionSistemaIluminacion != null ? seleccionSistemaIluminacion : null);
        filtro.setSistemaCamara(seleccionSistemaCamara != null ? seleccionSistemaCamara : null);
        filtro.setVulnerabilidad(seleccionVulnerabilidad != null ? seleccionVulnerabilidad.getNombre() : null);

        List<Object[]> resultados = facAmbito.consultarControlVigilancia(filtro);
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
                String tipoCerramiento = UtilConsulta.stringValueOf(fila[i++]);
                tipoCerramiento = tipoCerramiento != null?tipoCerramiento:"sql.ind.general.noTiene";
                if (tipoCerramiento.equalsIgnoreCase("sql.ind.general.noTiene")) {
                    registro.setTipoCerramiento(Utilidades.obtenerMensaje(tipoCerramiento));
                } else {
                    registro.setTipoCerramiento(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(tipoCerramiento), getUsuarioSesion().getUsuario().getIdioma().getId()));
                }
                registro.setCondicionCerramiento(UtilConsulta.stringValueOf(fila[i++]));
                registro.setCondicionOrdenPublico(UtilConsulta.stringValueOf(fila[i++]));
                registro.setVulnerabilidad(Utilidades.obtenerMensaje(UtilConsulta.stringValueOf(fila[i++])));
                if (isMostrarColEdificiosSistemaIluminacion()) {
                    registro.setEdificiosSistemaIluminacion(UtilConsulta.longValueOf(fila[i++]));
                }
                if (isMostrarColProporcionSistemaIluminacion()) {
                    registro.setProporcionSistemaIluminacion(UtilConsulta.doubleValueOf(fila[i++]));
                }
                if (isMostrarColEdificiosSistemaCamara()) {
                    registro.setEdificiosSistemaCamara(UtilConsulta.longValueOf(fila[i++]));
                }
                if (isMostrarColProporcionSistemaCamara()) {
                    registro.setProporcionSistemaCamara(UtilConsulta.doubleValueOf(fila[i++]));
                }
                registro.setTotalEdificios(UtilConsulta.longValueOf(fila[i++]));
                //otros
                registro.setDirPredio(UtilConsulta.stringValueOf(fila[i++]));
                registro.setZona(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]),getUsuarioSesion().getUsuario().getIdioma().getId()));
                registro.setSector(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]),getUsuarioSesion().getUsuario().getIdioma().getId()));
                registro.setClima(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]),getUsuarioSesion().getUsuario().getIdioma().getId()));
                registro.setEtnias(verificarEtnias(UtilConsulta.stringValueOf(fila[i++])));
                registro.setPropiedadPredio(UtilConsulta.stringValueOf(fila[i++]));
                registro.setCondicionAccesibilidad(UtilConsulta.stringValueOf(fila[i++]));
                //totales
                totalConsulta.addNumEdificios(registro.getTotalEdificios());
                totalConsulta.addNumEdificiosSistemaIluminacion(registro.getEdificiosSistemaIluminacion());
                totalConsulta.addNumEdificiosSistemaCamara(registro.getEdificiosSistemaCamara());
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
                if (isMostrarColPrediosOrdenPublico()) {
                    registro.setPrediosOrdenPublico(UtilConsulta.longValueOf(fila[i++]));
                }
                if (isMostrarColProporcionPrediosOrdenPublico()) {
                    registro.setProporcionPrediosOrdenPublico(UtilConsulta.doubleValueOf(fila[i++]));
                }
                if (isMostrarColPrediosVulnerabilidad()) {
                    registro.setPrediosVulnerabilidad(UtilConsulta.longValueOf(fila[i++]));
                }
                if (isMostrarColProporcionVulnerabilidad()) {
                    registro.setProporcionVulnerabilidad(UtilConsulta.doubleValueOf(fila[i++]));
                }
                registro.setTotalPredios(UtilConsulta.longValueOf(fila[i++]));
                // totales
                totalConsulta.addNumTotalPredios(registro.getTotalPredios());
                totalConsulta.addNumPrediosOrdenPublico(registro.getPrediosOrdenPublico());
                totalConsulta.addNumPrediosVulnerabilidad(registro.getPrediosVulnerabilidad());
            }
            
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

    public CalificacionCondicion getSeleccionCondicionOrdenPublico() {
        return seleccionCondicionOrdenPublico;
    }

    public void setSeleccionCondicionOrdenPublico(CalificacionCondicion seleccionCondicionOrdenPublico) {
        this.seleccionCondicionOrdenPublico = seleccionCondicionOrdenPublico;
    }

    public SiNo getSeleccionSistemaIluminacion() {
        return seleccionSistemaIluminacion;
    }

    public void setSeleccionSistemaIluminacion(SiNo seleccionSistemaIluminacion) {
        this.seleccionSistemaIluminacion = seleccionSistemaIluminacion;
    }

    public SiNo getSeleccionSistemaCamara() {
        return seleccionSistemaCamara;
    }

    public void setSeleccionSistemaCamara(SiNo seleccionSistemaCamara) {
        this.seleccionSistemaCamara = seleccionSistemaCamara;
    }

    public Vulnerabilidad getSeleccionVulnerabilidad() {
        return seleccionVulnerabilidad;
    }

    public void setSeleccionVulnerabilidad(Vulnerabilidad seleccionVulnerabilidad) {
        this.seleccionVulnerabilidad = seleccionVulnerabilidad;
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
        private String tipoCerramiento;
        private String condicionCerramiento;
        private String condicionOrdenPublico;
        private String vulnerabilidad;
        private Long edificiosSistemaIluminacion;
        private Double proporcionSistemaIluminacion;
        private Long edificiosSistemaCamara;
        private Double proporcionSistemaCamara;
        private Long totalEdificios;
        private String dirPredio;
        private String zona;
        private String sector;
        private String clima;
        private String etnias;
        private String propiedadPredio;
        private String condicionAccesibilidad;
        private Long prediosOrdenPublico;
        private Double proporcionPrediosOrdenPublico;
        private Long prediosVulnerabilidad;
        private Double proporcionVulnerabilidad;
        private Long totalPredios;

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

        public String getTipoCerramiento() {
            return tipoCerramiento;
        }

        public void setTipoCerramiento(String tipoCerramiento) {
            this.tipoCerramiento = tipoCerramiento;
        }

        public String getCondicionCerramiento() {
            return condicionCerramiento;
        }

        public void setCondicionCerramiento(String condicionCerramiento) {
            this.condicionCerramiento = condicionCerramiento;
        }

        public String getCondicionOrdenPublico() {
            return condicionOrdenPublico;
        }

        public void setCondicionOrdenPublico(String condicionOrdenPublico) {
            if (condicionOrdenPublico != null) {
                if (condicionOrdenPublico.equalsIgnoreCase("sql.ind.general.noTiene")) {
                    this.condicionOrdenPublico = Utilidades.obtenerMensaje(condicionOrdenPublico);
                } else {
                    this.condicionOrdenPublico = condicionOrdenPublico;
                }
            }
        }

        public String getVulnerabilidad() {
            return vulnerabilidad;
        }

        public void setVulnerabilidad(String vulnerabilidad) {
            this.vulnerabilidad = vulnerabilidad;
        }

        public Long getEdificiosSistemaIluminacion() {
            return edificiosSistemaIluminacion;
        }

        public void setEdificiosSistemaIluminacion(Long edificiosSistemaIluminacion) {
            this.edificiosSistemaIluminacion = edificiosSistemaIluminacion;
        }

        public Double getProporcionSistemaIluminacion() {
            return proporcionSistemaIluminacion;
        }

        public void setProporcionSistemaIluminacion(Double proporcionSistemaIluminacion) {
            this.proporcionSistemaIluminacion = proporcionSistemaIluminacion;
        }

        public Long getEdificiosSistemaCamara() {
            return edificiosSistemaCamara;
        }

        public void setEdificiosSistemaCamara(Long edificiosSistemaCamara) {
            this.edificiosSistemaCamara = edificiosSistemaCamara;
        }

        public Double getProporcionSistemaCamara() {
            return proporcionSistemaCamara;
        }

        public void setProporcionSistemaCamara(Double proporcionSistemaCamara) {
            this.proporcionSistemaCamara = proporcionSistemaCamara;
        }

        public Long getTotalEdificios() {
            return totalEdificios;
        }

        public void setTotalEdificios(Long totalEdificios) {
            this.totalEdificios = totalEdificios;
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

        public String getEtnias() {
            return etnias;
        }

        public void setEtnias(String etnias) {
            this.etnias = etnias;
        }

        public String getPropiedadPredio() {
            return propiedadPredio;
        }

        public void setPropiedadPredio(String propiedadPredio) {
             this.propiedadPredio = propiedadPredio;
             if (propiedadPredio != null) {
                if (propiedadPredio.equalsIgnoreCase("sql.ind.general.si")) {
                    this.propiedadPredio = Utilidades.obtenerMensaje(propiedadPredio);
                } else {
                    this.propiedadPredio = Utilidades.obtenerMensaje(propiedadPredio);
                    //this.propiedadPredio = propiedadPredio;
                }
            }
        }

        public String getCondicionAccesibilidad() {
            return condicionAccesibilidad;
        }

        public void setCondicionAccesibilidad(String condicionAccesibilidad) {
            this.condicionAccesibilidad = condicionAccesibilidad;
             if (condicionAccesibilidad != null) {
                if (condicionAccesibilidad.equalsIgnoreCase("sql.ind.general.cumple")) {
                    this.condicionAccesibilidad = Utilidades.obtenerMensaje(condicionAccesibilidad);
                } else {
                    this.condicionAccesibilidad = Utilidades.obtenerMensaje(condicionAccesibilidad);
                }
            }
        }
        
        public Long getPrediosOrdenPublico() {
            return prediosOrdenPublico;
        }

        public void setPrediosOrdenPublico(Long prediosOrdenPublico) {
            this.prediosOrdenPublico = prediosOrdenPublico;
        }

        public Double getProporcionPrediosOrdenPublico() {
            return proporcionPrediosOrdenPublico;
        }

        public void setProporcionPrediosOrdenPublico(Double proporcionPrediosOrdenPublico) {
            this.proporcionPrediosOrdenPublico = proporcionPrediosOrdenPublico;
        }

        public Long getPrediosVulnerabilidad() {
            return prediosVulnerabilidad;
        }

        public void setPrediosVulnerabilidad(Long prediosVulnerabilidad) {
            this.prediosVulnerabilidad = prediosVulnerabilidad;
        }

        public Double getProporcionVulnerabilidad() {
            return proporcionVulnerabilidad;
        }

        public void setProporcionVulnerabilidad(Double proporcionVulnerabilidad) {
            this.proporcionVulnerabilidad = proporcionVulnerabilidad;
        }

        public Long getTotalPredios() {
            return totalPredios;
        }

        public void setTotalPredios(Long totalPredios) {
            this.totalPredios = totalPredios;
        }
    }

    public class TotalConsulta {

        private Long numEdificios;
        private Long numEdificiosSistemaIluminacion;
        private Long numEdificiosSistemaCamara;
        private Long numTotalPredios;
        private Long numPrediosOrdenPublico;
        private Long numPrediosVulnerabilidad;

        public void inicializar() {
            numEdificios = 0L;
            numEdificiosSistemaIluminacion = 0L;
            numEdificiosSistemaCamara = 0L;
            numTotalPredios = 0L;
            numPrediosOrdenPublico = 0L;
            numPrediosVulnerabilidad = 0L;
        }

        public Long getNumEdificios() {
            return numEdificios;
        }

        public Long getNumEdificiosSistemaIluminacion() {
            return numEdificiosSistemaIluminacion;
        }

        public Long getNumEdificiosSistemaCamara() {
            return numEdificiosSistemaCamara;
        }

        public Long getNumTotalPredios() {
            return numTotalPredios;
        }

        public Long getNumPrediosOrdenPublico() {
            return numPrediosOrdenPublico;
        }

        public Long getNumPrediosVulnerabilidad() {
            return numPrediosVulnerabilidad;
        }

        public void addNumEdificios(Long valor) {
            if (valor != null) {
                this.numEdificios += valor;
            }
        }

        public void addNumEdificiosSistemaIluminacion(Long valor) {
            if (valor != null) {
                this.numEdificiosSistemaIluminacion += valor;
            }
        }

        public void addNumEdificiosSistemaCamara(Long valor) {
            if (valor != null) {
                this.numEdificiosSistemaCamara += valor;
            }
        }

        public void addNumTotalPredios(Long valor) {
            if (valor != null) {
                this.numTotalPredios += valor;
            }
        }

        public void addNumPrediosOrdenPublico(Long valor) {
            if (valor != null) {
                this.numPrediosOrdenPublico += valor;
            }
        }

        public void addNumPrediosVulnerabilidad(Long valor) {
            if (valor != null) {
                this.numPrediosVulnerabilidad += valor;
            }
        }
    }

    // mostrar columnas  
    public boolean isMostrarColCondicionAccesibilidad() {
        return isAgrupamientoPredio();
    }
    
    public boolean isMostrarColTipoCerramiento() {
        return isAgrupamientoPredio();
    }

    public boolean isMostrarColCondicionCerramiento() {
        return isAgrupamientoPredio();
    }

    public boolean isMostrarColCondicionOrdenPublico() {
        return isAgrupamientoPredio();
    }

    public boolean isMostrarColVulnerabilidad() {
        return isAgrupamientoPredio();
    }

    public boolean isMostrarColEdificiosSistemaIluminacion() {
        return isAgrupamientoPredio() && seleccionSistemaIluminacion != null;
    }

    public boolean isMostrarColProporcionSistemaIluminacion() {
        return isAgrupamientoPredio() && seleccionSistemaIluminacion != null;
    }

    public boolean isMostrarColEdificiosSistemaCamara() {
        return isAgrupamientoPredio() && seleccionSistemaCamara != null;
    }

    public boolean isMostrarColProporcionSistemaCamara() {
        return isAgrupamientoPredio() && seleccionSistemaCamara != null;
    }

    public boolean isMostrarColTotalEdificios() {
        return isAgrupamientoPredio();
    }

    public boolean isMostrarColPrediosOrdenPublico() {
        return !isAgrupamientoPredio() && seleccionCondicionOrdenPublico != null;
    }

    public boolean isMostrarColProporcionPrediosOrdenPublico() {
        return !isAgrupamientoPredio() && seleccionCondicionOrdenPublico != null;
    }

    public boolean isMostrarColPrediosVulnerabilidad() {
        return !isAgrupamientoPredio() && seleccionVulnerabilidad != null;
    }

    public boolean isMostrarColProporcionVulnerabilidad() {
        return !isAgrupamientoPredio() && seleccionVulnerabilidad != null;
    }

    public boolean isMostrarColTotalPredios() {
        return !isAgrupamientoPredio();
    }

    //getter de columnas
    public String getColumnaTipoCerramiento() {
        return columnaTipoCerramiento;
    }

    public String getColumnaCondicionCerramiento() {
        return columnaCondicionCerramiento;
    }

    public String getColumnaCondicionOrdenPublico() {
        return columnaCondicionOrdenPublico;
    }

    public String getColumnaVulnerabilidad() {
        return columnaVulnerabilidad;
    }

    public String getColumnaEdificiosSistemaIluminacion() {
        return columnaEdificiosSistemaIluminacion;
    }

    public String getColumnaEdificiosSistemaCamara() {
        return columnaEdificiosSistemaCamara;
    }

    public String getColumnaProporcionSistemaIluminacion() {
        return columnaProporcionSistemaIluminacion;
    }

    public String getColumnaProporcionSistemaCamara() {
        return columnaProporcionSistemaCamara;
    }

    public String getColumnaTotalEdificios() {
        return columnaTotalEdificios;
    }

    public String getColumnaPrediosOrdenPublico() {
        return columnaPrediosOrdenPublico;
    }

    public String getColumnaPrediosVulnerabilidad() {
        return columnaPrediosVulnerabilidad;
    }

    public String getColumnaProporcionPrediosOrdenPublico() {
        return columnaProporcionPrediosOrdenPublico;
    }

    public String getColumnaProporcionVulnerabilidad() {
        return columnaProporcionVulnerabilidad;
    }

    public String getColumnaTotalPredios() {
        return columnaTotalPredios;
    }

    public String getColumnaCondicionAccesibilidad() {
        return columnaCondicionAccesibilidad;
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
