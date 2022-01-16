/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.consulta.ambito;

import co.stackpointer.cier.modelo.entidad.instrumento.TipologiaValorNombre;
import co.stackpointer.cier.modelo.fachada.ConsultaAmbitoFacadeLocal;
import co.stackpointer.cier.modelo.fachada.ParametrosFacadeLocal;
import co.stackpointer.cier.modelo.tipo.CalificacionCondicion;
import co.stackpointer.cier.modelo.tipo.ParamNivelConsul;
import co.stackpointer.cier.modelo.tipo.SiNo;
import co.stackpointer.cier.modelo.tipo.TipologiaCodigo;
import co.stackpointer.cier.modelo.tipo.Vulnerabilidad;
import co.stackpointer.cier.modelo.utilidad.UtilCadena;
import co.stackpointer.cier.modelo.utilidad.UtilConsulta;
import co.stackpointer.cier.vista.Utilidades;
import co.stackpointer.cier.vistacontrolador.consulta.ConsultaBase;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author David Paz A.
 */
@ManagedBean(name = "consultaAmbito")
@SessionScoped
public class ConsultaAmbito extends ConsultaBase {

    @EJB
    protected ParametrosFacadeLocal factParam; 
    
    @EJB
    protected ConsultaAmbitoFacadeLocal facAmbito;
    protected List<TipologiaValorNombre> listaSectores;
    protected List<TipologiaValorNombre> listaZonas;
    protected List<Integer> listaPeriodos;
    protected List<CalificacionCondicion> listaCondiciones;
    protected List<String> listaCamposOpcionales;
    protected Map<Long, String> listaAgrupamientos;
    protected Long seleccionAgrupamiento = ParamNivelConsul.PREDIO.getNivel();
    protected List<String> selccionCamposOpcionales;
    protected TipologiaValorNombre seleccionSector;
    protected TipologiaValorNombre seleccionZona;
    // columnas comunes    
    protected final String columnaCodEst = Utilidades.obtenerMensaje("consultas.ambito.comun.columna.establecimiento.codigo");
    protected final String columnaNomEst = Utilidades.obtenerMensaje("consultas.ambito.comun.columna.establecimiento.nombre");
    protected final String columnaCodSede = Utilidades.obtenerMensaje("consultas.ambito.comun.columna.sede.codigo");
    protected final String columnaNomSede = Utilidades.obtenerMensaje("consultas.ambito.comun.columna.sede.nombre");
    protected final String columnaCodPredio = Utilidades.obtenerMensaje("consultas.ambito.comun.columna.predio.codigo");
    protected final String columnaNomPredio = Utilidades.obtenerMensaje("consultas.ambito.comun.columna.predio.nombre");
    protected final String columnaSector = Utilidades.obtenerMensaje("consultas.ambito.comun.columna.sector");
    protected final String columnaZona = Utilidades.obtenerMensaje("consultas.ambito.comun.columna.zona");
    protected final String columnaClima = Utilidades.obtenerMensaje("consultas.ambito.comun.columna.clima");
    protected final String columnaEtnias = Utilidades.obtenerMensaje("consultas.ambito.comun.columna.etnias");
    protected final String columnaPropPredio = Utilidades.obtenerMensaje("consultas.ambito.comun.columna.predio.propiedad");
    protected final String columnaDirPredio = Utilidades.obtenerMensaje("consultas.ambito.comun.columna.predio.dir");

    @PostConstruct
    public void init() {
        // inicializar niveles
        this.cargarPeriodos();
        periodo = !listaPeriodos.isEmpty() ? listaPeriodos.get(0) : null;
        if (!listaPeriodos.isEmpty()) {
            if (UtilCadena.isNullOVacio(listaPeriodos.get(0)) && !listaPeriodos.get(0).equals(0L)) {
                periodo = listaPeriodos.get(0);
            } else {
                periodo = null;
            }
        } else {
            periodo = null;
        }

        if (periodo == null) {
            super.initActual();
        } else {
            super.initHistoricos(periodo);
        }        
        // niveles de agrupamiento
        this.cargarAgrupamientos();
        // campos opcionales
        listaCamposOpcionales = new ArrayList<String>();
        selccionCamposOpcionales = new ArrayList<String>();
        // tipologias por defecto        
        listaSectores = facParam.consultarValoresTipologia(TipologiaCodigo.SECTOR.getValor(),getUsuarioSesion().getUsuario().getIdioma().getId());
        listaZonas = facParam.consultarValoresTipologia(TipologiaCodigo.ZONA.getValor(),getUsuarioSesion().getUsuario().getIdioma().getId());
    }
        
    private void cargarAgrupamientos() {
        listaAgrupamientos = new HashMap<Long, String>();
        listaAgrupamientos.putAll(listaNivelesConf);
    }

    public void removerAgrupamiento(ParamNivelConsul agrupamiento) {
        listaAgrupamientos.remove(agrupamiento.getNivel());
    }
    
    public String verificarEtnias(String etnias) {
        String resul = "";
        if (!UtilCadena.isNullOVacio(etnias)) {
            if (etnias.contains(",")) {
                String[] listarEtnias = etnias.split(",");
                boolean sw = true;
                for (String e : listarEtnias) {
                    e = factParam.consultarNombreTipologia(UtilConsulta.longValueOf(e), getUsuarioSesion().getUsuario().getIdioma().getId());
                    if (sw) {
                        resul = e;
                        sw = false;
                    } else {
                        resul = resul + ", " + e;
                    }
                }
            } else {
                resul = factParam.consultarNombreTipologia(UtilConsulta.longValueOf(etnias), getUsuarioSesion().getUsuario().getIdioma().getId());
            }
        }
        return resul;
    }

    public void cargarPeriodos() {
        listaPeriodos = facParam.consultarPeriodosCalculadosIndicadores();
    }

    public List<Integer> getListaPeriodos() {
        return listaPeriodos;
    }

    public Map<Long, String> getListaAgrupamientos() {
        return listaAgrupamientos;
    }

    public List<CalificacionCondicion> getListaCondiciones() {
        if (listaCondiciones == null) {
            listaCondiciones = Arrays.asList(CalificacionCondicion.values());
        }
        return listaCondiciones;
    }

    public Long getSeleccionAgrupamiento() {
        return seleccionAgrupamiento;
    }

    public void setSeleccionAgrupamiento(Long seleccionAgrupamiento) {
        this.seleccionAgrupamiento = seleccionAgrupamiento;
    }

    public TipologiaValorNombre getSeleccionSector() {
        return seleccionSector;
    }

    public void setSeleccionSector(TipologiaValorNombre seleccionSector) {
        this.seleccionSector = seleccionSector;
    }

    public TipologiaValorNombre getSeleccionZona() {
        return seleccionZona;
    }

    public void setSeleccionZona(TipologiaValorNombre seleccionZona) {
        this.seleccionZona = seleccionZona;
    }

    public List<TipologiaValorNombre> getListaSectores() {
        return listaSectores;
    }

    public void setListaSectores(List<TipologiaValorNombre> listaSectores) {
        this.listaSectores = listaSectores;
    }

    public List<TipologiaValorNombre> getListaZonas() {
        return listaZonas;
    }

    public void setListaZonas(List<TipologiaValorNombre> listaZonas) {
        this.listaZonas = listaZonas;
    }

    public boolean isAgrupamientoNivel0() {
        return ParamNivelConsul.CERO.equals(seleccionAgrupamiento);
    }

    public boolean isAgrupamientoNivel1() {
        return ParamNivelConsul.UNO.equals(seleccionAgrupamiento);
    }

    public boolean isAgrupamientoNivel2() {
        return ParamNivelConsul.DOS.equals(seleccionAgrupamiento);
    }

    public boolean isAgrupamientoNivel3() {
        return ParamNivelConsul.TRES.equals(seleccionAgrupamiento);
    }

    public boolean isAgrupamientoNivel4() {
        return ParamNivelConsul.CUATRO.equals(seleccionAgrupamiento);
    }

    public boolean isAgrupamientoNivel5() {
        return ParamNivelConsul.CINCO.equals(seleccionAgrupamiento);
    }

    public boolean isAgrupamientoEstablecimiento() {
        return ParamNivelConsul.ESTABLECIMIENTO.equals(seleccionAgrupamiento);
    }

    public boolean isAgrupamientoSede() {
        return ParamNivelConsul.SEDE.equals(seleccionAgrupamiento);
    }

    public boolean isAgrupamientoPredio() {
        return ParamNivelConsul.PREDIO.equals(seleccionAgrupamiento);
    }

    public List<String> getListaCamposOpcionales() {
        return listaCamposOpcionales;
    }

    public boolean isMostrarCamposOpcional(String campo) {
        return selccionCamposOpcionales != null && selccionCamposOpcionales.contains(campo);
    }

    public List<String> getSelccionCamposOpcionales() {
        return selccionCamposOpcionales;
    }

    public void setSelccionCamposOpcionales(List<String> selccion) {
        this.selccionCamposOpcionales = selccion;
    }

    public void cargarCamposOpcionalesComunes() {
        if (isMostrarColNivel0()) {
            listaCamposOpcionales.add(listaNivelesConf.get(ParamNivelConsul.CERO.getNivel()));
            if (!isAgrupamientoPredio()) {
                selccionCamposOpcionales.add(listaNivelesConf.get(ParamNivelConsul.CERO.getNivel()));
            }
        }
        if (isMostrarColNivel1()) {
            listaCamposOpcionales.add(getColumnaCodNiv1());
            listaCamposOpcionales.add(listaNivelesConf.get(ParamNivelConsul.UNO.getNivel()));            
            if (!isAgrupamientoPredio()) {
                selccionCamposOpcionales.add(listaNivelesConf.get(ParamNivelConsul.UNO.getNivel()));
            }
        }
        if (isMostrarColNivel2()) {
            listaCamposOpcionales.add(getColumnaCodNiv2());
            listaCamposOpcionales.add(listaNivelesConf.get(ParamNivelConsul.DOS.getNivel()));
            if (!isAgrupamientoPredio()) {
                selccionCamposOpcionales.add(listaNivelesConf.get(ParamNivelConsul.DOS.getNivel()));
            }
        }
        if (isMostrarColNivel3()) {
            listaCamposOpcionales.add(getColumnaCodNiv3());
            listaCamposOpcionales.add(listaNivelesConf.get(ParamNivelConsul.TRES.getNivel()));
            if (!isAgrupamientoPredio()) {
                selccionCamposOpcionales.add(listaNivelesConf.get(ParamNivelConsul.TRES.getNivel()));
            }
        }
        if (isMostrarColNivel4()) {
            listaCamposOpcionales.add(getColumnaCodNiv4());
            listaCamposOpcionales.add(listaNivelesConf.get(ParamNivelConsul.CUATRO.getNivel()));
            if (!isAgrupamientoPredio()) {
                selccionCamposOpcionales.add(listaNivelesConf.get(ParamNivelConsul.CUATRO.getNivel()));
            }
        }
        if (isMostrarColNivel5()) {
            listaCamposOpcionales.add(getColumnaCodNiv5());
            listaCamposOpcionales.add(listaNivelesConf.get(ParamNivelConsul.CINCO.getNivel()));
            if (!isAgrupamientoPredio()) {
                selccionCamposOpcionales.add(listaNivelesConf.get(ParamNivelConsul.CINCO.getNivel()));
            }
        }
        if (isMostrarColCodEstablecimiento()) {
            listaCamposOpcionales.add(columnaCodEst);
            if (!isAgrupamientoPredio()) {
                selccionCamposOpcionales.add(columnaCodEst);
            }
        }
        if (isMostrarColNomEstablecimiento()) {
            listaCamposOpcionales.add(columnaNomEst);
            if (!isAgrupamientoPredio()) {
                selccionCamposOpcionales.add(columnaNomEst);
            }
        }
        if (isMostrarColCodSede()) {
            listaCamposOpcionales.add(columnaCodSede);
            if (!isAgrupamientoPredio()) {
                selccionCamposOpcionales.add(columnaCodSede);
            }
        }
        if (isMostrarColNomSede()) {
            listaCamposOpcionales.add(columnaNomSede);
            if (!isAgrupamientoPredio()) {
                selccionCamposOpcionales.add(columnaNomSede);
            }
        }
        if (isMostrarColCodPredio()) {
            listaCamposOpcionales.add(columnaCodPredio);
            selccionCamposOpcionales.add(columnaCodPredio);
        }
        if (isMostrarColNomPredio()) {
            listaCamposOpcionales.add(columnaNomPredio);
            selccionCamposOpcionales.add(columnaNomPredio);
        }
    }
    
    protected String elementosSeleccionados(List<String> lista) {
        String seleccionados = null;
        if (!UtilCadena.isNullOVacio(lista)) {
            boolean sw = true;
            for (String elemento : lista) {
                if (sw) {
                    seleccionados = "'" + elemento + "'";
                    sw = false;
                } else {
                    seleccionados += ",'" + elemento + "'";
                }
            }
        }
        return seleccionados;
    }

    public boolean isMostrarColNivel0() {
        return isActivoNivel1() && isAgrupamientoNivel0();
    }

    public boolean isMostrarColNivel1() {
        return isActivoNivel1() && (isAgrupamientoPredio() || isAgrupamientoSede() || isAgrupamientoEstablecimiento() || isAgrupamientoNivel1() || isAgrupamientoNivel2() || isAgrupamientoNivel3() || isAgrupamientoNivel4() || isAgrupamientoNivel5());
    }

    public boolean isMostrarColNivel2() {
        return isActivoNivel2() && (isAgrupamientoPredio() || isAgrupamientoSede() || isAgrupamientoEstablecimiento() || isAgrupamientoNivel2() || isAgrupamientoNivel3() || isAgrupamientoNivel4() || isAgrupamientoNivel5());
    }

    public boolean isMostrarColNivel3() {
        return isActivoNivel3() && (isAgrupamientoPredio() || isAgrupamientoSede() || isAgrupamientoEstablecimiento() || isAgrupamientoNivel3() || isAgrupamientoNivel4() || isAgrupamientoNivel5());
    }

    public boolean isMostrarColNivel4() {
        return isActivoNivel4() && (isAgrupamientoPredio() || isAgrupamientoSede() || isAgrupamientoEstablecimiento() || isAgrupamientoNivel4() || isAgrupamientoNivel5());
    }

    public boolean isMostrarColNivel5() {
        return isActivoNivel5() && (isAgrupamientoPredio() || isAgrupamientoSede() || isAgrupamientoEstablecimiento() || isAgrupamientoNivel5());
    }

    public boolean isMostrarColCodEstablecimiento() {
        return isAgrupamientoEstablecimiento() || isAgrupamientoPredio() || isAgrupamientoSede();
    }

    public boolean isMostrarColNomEstablecimiento() {
        return  isAgrupamientoEstablecimiento() || isAgrupamientoPredio()|| isAgrupamientoSede();
    }

    public boolean isMostrarColCodSede() {
        return isAgrupamientoPredio() || isAgrupamientoSede();
    }

    public boolean isMostrarColNomSede() {
        return isAgrupamientoPredio() || isAgrupamientoSede();
    }

    public boolean isMostrarColCodPredio() {
        return isAgrupamientoPredio();
    }

    public boolean isMostrarColNomPredio() {
        return isAgrupamientoPredio();
    }

    // datos opcionales
    public boolean isMostrarColZona() {
        return isAgrupamientoPredio();
    }

    public boolean isMostrarColSector() {
        return isAgrupamientoPredio();
    }

    public boolean isMostrarColClima() {
        return isAgrupamientoPredio();
    }

    public boolean isMostrarColDirPredio() {
        return isAgrupamientoPredio();
    }

    public boolean isMostrarColEtnias() {
        return isAgrupamientoPredio();
    }

    public boolean isMostrarColPropiedadPredio() {
        return isAgrupamientoPredio();
    }
    
    public String getColumnaCodNiv1(){
        if (listaNivelesConf.containsKey(ParamNivelConsul.UNO.getNivel())) {
            return Utilidades.obtenerMensaje("consultas.ambito.comun.columna.codigo.nivel", listaNivelesConf.get(ParamNivelConsul.UNO.getNivel()));
        }
        return "";
    }
    
    public String getColumnaCodNiv2(){
        if (listaNivelesConf.containsKey(ParamNivelConsul.DOS.getNivel())) {
            return Utilidades.obtenerMensaje("consultas.ambito.comun.columna.codigo.nivel", listaNivelesConf.get(ParamNivelConsul.DOS.getNivel()));
        }
        return "";
    }
    
    public String getColumnaCodNiv3(){
        if (listaNivelesConf.containsKey(ParamNivelConsul.TRES.getNivel())) {
            return Utilidades.obtenerMensaje("consultas.ambito.comun.columna.codigo.nivel", listaNivelesConf.get(ParamNivelConsul.TRES.getNivel()));
        }
        return "";
    }
    
    public String getColumnaCodNiv4(){
        if (listaNivelesConf.containsKey(ParamNivelConsul.CUATRO.getNivel())) {
            return Utilidades.obtenerMensaje("consultas.ambito.comun.columna.codigo.nivel", listaNivelesConf.get(ParamNivelConsul.CUATRO.getNivel()));
        }
        return "";
    }
    
    public String getColumnaCodNiv5() {
        if (listaNivelesConf.containsKey(ParamNivelConsul.CINCO.getNivel())) {
            return Utilidades.obtenerMensaje("consultas.ambito.comun.columna.codigo.nivel", listaNivelesConf.get(ParamNivelConsul.CINCO.getNivel()));
        }
        return "";
    }
    
    public String getColumnaCodEst() {
        return columnaCodEst;
    }

    public String getColumnaNomEst() {
        return columnaNomEst;
    }

    public String getColumnaCodSede() {
        return columnaCodSede;
    }

    public String getColumnaNomSede() {
        return columnaNomSede;
    }

    public String getColumnaCodPredio() {
        return columnaCodPredio;
    }

    public String getColumnaNomPredio() {
        return columnaNomPredio;
    }

    public String getColumnaSector() {
        return columnaSector;
    }

    public String getColumnaZona() {
        return columnaZona;
    }

    public String getColumnaClima() {
        return columnaClima;
    }

    public String getColumnaEtnias() {
        return columnaEtnias;
    }

    public String getColumnaPropPredio() {
        return columnaPropPredio;
    }

    public String getColumnaDirPredio() {
        return columnaDirPredio;
    }

    public List<SiNo> getListaSiNo() {
        return Arrays.asList(SiNo.values());
    }
    
    public List<Vulnerabilidad> getListaVulnerabilidad() {
        return Arrays.asList(Vulnerabilidad.values());
    }

    @Override
    public void resetearFiltros() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public void cambioAgrupamiento(){
        throw new UnsupportedOperationException("Not supported yet.");
    }    

    @Override
    public void cambioPeriodo() {
        super.initHistoricos(periodo);
        super.cargarNivel1();
        cargarAgrupamientos();
        seleccionAgrupamiento = ParamNivelConsul.PREDIO.getNivel();
    }
    
}
