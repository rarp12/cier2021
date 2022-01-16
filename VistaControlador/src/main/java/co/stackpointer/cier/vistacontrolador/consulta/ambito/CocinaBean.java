/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.consulta.ambito;

import co.stackpointer.cier.modelo.entidad.indicador.EstandarHis;
import co.stackpointer.cier.modelo.filtro.ambito.FiltroAmbitoAmbiente;
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
@ManagedBean(name = "cocina")
@ViewScoped
public class CocinaBean extends ConsultaAmbito {

    @ManagedProperty(value = "#{consultaAmbito}")
    private ConsultaAmbito consultaAmbito;
    private List<Registro> lista;
    private FiltroAmbitoAmbiente filtro = new FiltroAmbitoAmbiente();
    private TotalConsulta totalConsulta = new TotalConsulta();
    private String unidadFuncional;
    private String nombreUnidadFuncional;

    final String columnaNumEspaciosPorTipo = Utilidades.obtenerMensaje("consultas.ambito.cocina.column.numEspacios");
    final String columnaProporcionEspacios = Utilidades.obtenerMensaje("consultas.ambito.cocina.column.propEspacios");
    final String columnaPoblacionAtendida = Utilidades.obtenerMensaje("consultas.ambito.cocina.column.pobAtendida");
    final String columnaNumSedesIncumplen = Utilidades.obtenerMensaje("consultas.ambito.cocina.column.numSedIncumplen");
    final String columnaAreaIdonea = Utilidades.obtenerMensaje("consultas.ambito.cocina.column.areaIdonea");
    final String columnaDeficitEstandar = Utilidades.obtenerMensaje("consultas.ambito.cocina.column.deficit");
    final String columnaProporcionSedesIncumplen = Utilidades.obtenerMensaje("consultas.ambito.cocina.column.propSedIncumplen");
    final String columnaAreaEspacio = Utilidades.obtenerMensaje("consultas.ambito.cocina.column.areaEspacios");
    final String columnaCumplimientoNorma = Utilidades.obtenerMensaje("consultas.ambito.cocina.column.cumpNorma");
    public String estandar;
    public String unidad;

    @PostConstruct
    public void inicializar() {
        //super.init();
        lista = new ArrayList<Registro>();
        removerAgrupamiento(ParamNivelConsul.PREDIO);
        seleccionAgrupamiento = ParamNivelConsul.SEDE.getNivel();
        cargarCamposOpcionales();
    }

    private void cargarCamposOpcionales() {
        selccionCamposOpcionales.clear();
        listaCamposOpcionales.clear();
        this.cargarCamposOpcionalesComunes();

        listaCamposOpcionales.add(columnaNumEspaciosPorTipo);
        selccionCamposOpcionales.add(columnaNumEspaciosPorTipo);
        listaCamposOpcionales.add(columnaProporcionEspacios);
        selccionCamposOpcionales.add(columnaProporcionEspacios);
        listaCamposOpcionales.add(columnaAreaEspacio);
        selccionCamposOpcionales.add(columnaAreaEspacio);
        if (isAgrupamientoSede()) {
            listaCamposOpcionales.add(columnaPoblacionAtendida);
            selccionCamposOpcionales.add(columnaPoblacionAtendida);
            listaCamposOpcionales.add(columnaAreaIdonea);
            selccionCamposOpcionales.add(columnaAreaIdonea);
            listaCamposOpcionales.add(columnaDeficitEstandar);
            selccionCamposOpcionales.add(columnaDeficitEstandar);
            listaCamposOpcionales.add(columnaCumplimientoNorma);
            selccionCamposOpcionales.add(columnaCumplimientoNorma);
        } else {
            listaCamposOpcionales.add(columnaNumSedesIncumplen);
            selccionCamposOpcionales.add(columnaNumSedesIncumplen);
            listaCamposOpcionales.add(columnaProporcionSedesIncumplen);
            selccionCamposOpcionales.add(columnaProporcionSedesIncumplen);
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

        // filtros basicos
        filtro.setCodZona(seleccionZona != null ? seleccionZona.getTipValor().getCodigo() : null);
        filtro.setCodSector(seleccionSector != null ? seleccionSector.getTipValor().getCodigo() : null);
        filtro.setUnidadFuncional("COCINA");

        List<Object[]> resultados = facAmbito.consultarCocina(filtro);
        lista = new ArrayList<Registro>(resultados.size());
        if (lista.size() < 1) {
            mensajeTablaVacia = Utilidades.obtenerMensaje("aplicacion.general.noExistenRegistros");
        }

        totalConsulta.inicializar();
        for (Object[] fila : resultados) {
            Registro registro = new Registro();

            int i = 0;
            if (filtro.isAgrupamientoSede()) {
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
                registro.setPobAtendida(UtilConsulta.longValueOf(fila[i++]));
                registro.setAreaIdonea(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setDeficit(UtilConsulta.doubleValueOf(fila[i++]));
                registro.setCumplimientoNorma(Utilidades.obtenerMensaje(UtilConsulta.stringValueOf(fila[i++])));
                totalConsulta.addTotalPoblacion(registro.getPobAtendida());
            } else {
                if (filtro.isAgrupamientoEstablecimiento()) {
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
                }
                registro.setNumSedIncumplen(UtilConsulta.longValueOf(fila[i++]));
                registro.setPropSedIncumplen(UtilConsulta.doubleValueOf(fila[i++]));
            }
            registro.setNumEspaciosPorTipo(UtilConsulta.longValueOf(fila[i++]));
            registro.setProporcionEspacios(UtilConsulta.doubleValueOf(fila[i++]));
            registro.setAreaEspacio(UtilConsulta.doubleValueOf(fila[i++]));
            //totales                
            totalConsulta.addNumEspaciosPorTipo(registro.getNumEspaciosPorTipo());
            totalConsulta.addAreaTotal(registro.getAreaEspacio());
            totalConsulta.addAreaIdoneaTotal(registro.getAreaIdonea());
            totalConsulta.addTotalSedesIncumplen(registro.getNumSedIncumplen());
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

    public String getUnidadFuncional() {
        return unidadFuncional;
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
        private Long numEspaciosPorTipo;
        private Double proporcionEspacios;
        private Long pobAtendida;
        private Long numSedIncumplen;
        private Double areaIdonea;
        private Double deficit;
        private Double propSedIncumplen;
        private Double areaEspacio;
        private String cumplimientoNorma;

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

        public Long getPobAtendida() {
            return pobAtendida;
        }

        public void setPobAtendida(Long pobAtendida) {
            this.pobAtendida = pobAtendida;
        }

        public Long getNumSedIncumplen() {
            return numSedIncumplen;
        }

        public void setNumSedIncumplen(Long numSedIncumplen) {
            this.numSedIncumplen = numSedIncumplen;
        }

        public Double getAreaIdonea() {
            return areaIdonea;
        }

        public void setAreaIdonea(Double areaIdonea) {
            this.areaIdonea = areaIdonea;
        }

        public Double getDeficit() {
            return deficit;
        }

        public void setDeficit(Double deficit) {
            this.deficit = deficit;
        }

        public Double getPropSedIncumplen() {
            return propSedIncumplen;
        }

        public void setPropSedIncumplen(Double propSedIncumplen) {
            this.propSedIncumplen = propSedIncumplen;
        }

        public Double getAreaEspacio() {
            return areaEspacio;
        }

        public void setAreaEspacio(Double areaEspacio) {
            this.areaEspacio = areaEspacio;
        }

        public String getCumplimientoNorma() {
            return cumplimientoNorma;
        }

        public void setCumplimientoNorma(String cumplimientoNorma) {
            this.cumplimientoNorma = cumplimientoNorma;
        }
    }

    public class TotalConsulta {

        private Long totalSedesIncumplen;
        private Long numEspaciosPorTipo;
        private Long totalPoblacion;
        private Double areaEspacios;
        private Double totalAreaIdonea;

        public void inicializar() {
            totalSedesIncumplen = 0L;
            numEspaciosPorTipo = 0L;
            totalPoblacion = 0L;
            areaEspacios = 0D;
            totalAreaIdonea = 0D;
        }

        public Long getTotalSedesIncumplen() {
            return totalSedesIncumplen;
        }

        public Long getNumEspaciosPorTipo() {
            return numEspaciosPorTipo;
        }

        public Long getTotalPoblacion() {
            return totalPoblacion;
        }

        public Double getAreaEspacios() {
            return areaEspacios;
        }

        public Double getTotalAreaIdonea() {
            return totalAreaIdonea;
        }

        public void addTotalSedesIncumplen(Long valor) {
            if (valor != null) {
                this.totalSedesIncumplen += valor;
            }
        }

        public void addNumEspaciosPorTipo(Long valor) {
            if (valor != null) {
                this.numEspaciosPorTipo += valor;
            }
        }

        public void addTotalPoblacion(Long valor) {
            if (valor != null) {
                this.totalPoblacion += valor;
            }
        }

        public void addAreaTotal(Double valor) {
            if (valor != null) {
                this.areaEspacios += valor;
            }
        }

        public void addAreaIdoneaTotal(Double valor) {
            if (valor != null) {
                this.totalAreaIdonea += valor;
            }
        }
    }

    public String getColumnaNumEspaciosPorTipo() {
        return columnaNumEspaciosPorTipo;
    }

    public String getColumnaProporcionEspacios() {
        return columnaProporcionEspacios;
    }

    public String getColumnaPoblacionAtendida() {
        return columnaPoblacionAtendida;
    }

    public String getColumnaNumSedesIncumplen() {
        return columnaNumSedesIncumplen;
    }

    public String getColumnaAreaIdonea() {
        return columnaAreaIdonea;
    }

    public String getColumnaDeficitEstandar() {
        return columnaDeficitEstandar;
    }

    public String getColumnaProporcionSedesIncumplen() {
        return columnaProporcionSedesIncumplen;
    }

    public String getColumnaAreaEspacio() {
        return columnaAreaEspacio;
    }

    public String getColumnaCumplimientoNorma() {
        return columnaCumplimientoNorma;
    }

    public String getEstandar() {
        return estandar;
    }

    public String getUnidad() {
        return unidad;
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
