/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.consulta.ambito;

import co.stackpointer.cier.modelo.entidad.instrumento.TipologiaValorNombre;
import co.stackpointer.cier.modelo.filtro.ambito.FiltroAmbitoEspacio;
import co.stackpointer.cier.modelo.tipo.CalificacionCondicion;
import co.stackpointer.cier.modelo.tipo.TipologiaCodigo;
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
@ManagedBean(name = "espacios")
@ViewScoped
public class EspaciosBean extends ConsultaAmbito {

    @ManagedProperty(value = "#{consultaAmbito}")
    private ConsultaAmbito consultaAmbito;
    private CalificacionCondicion seleccionCondicionMaterial;
    private List<TipologiaValorNombre> listaPisos;
    private List<TipologiaValorNombre> listaMuros;
    private List<TipologiaValorNombre> listaAcabadoMuros;
    private List<TipologiaValorNombre> listaVentanas;
    private List<TipologiaValorNombre> listaPuertas;
    private List<TipologiaValorNombre> listaTechos;
    private List<String> seleccionPiso;
    private List<String> seleccionMuro;
    private List<String> seleccionAcabadoMuro;
    private List<String> seleccionVentana;
    private List<String> seleccionPuerta;
    private List<String> seleccionTecho;
    private List<Registro> lista;
    private FiltroAmbitoEspacio filtro = new FiltroAmbitoEspacio();
    private TotalConsulta totalConsulta = new TotalConsulta();
    private final String columnaEspaciosPiso = Utilidades.obtenerMensaje("consultas.ambito.espacio.columna.espaciosPiso");
    private final String columnaAreaPiso = Utilidades.obtenerMensaje("consultas.ambito.espacio.columna.areaPiso");
    private final String columnaProporcionAreaPiso = Utilidades.obtenerMensaje("consultas.ambito.espacio.columna.proporcionAreaPiso");
    private final String columnaEspaciosMuro = Utilidades.obtenerMensaje("consultas.ambito.espacio.columna.espaciosMuro");
    private final String columnaAreaMuro = Utilidades.obtenerMensaje("consultas.ambito.espacio.columna.areaMuro");
    private final String columnaProporcionAreaMuro = Utilidades.obtenerMensaje("consultas.ambito.espacio.columna.proporcionAreaMuro");
    private final String columnaEspaciosAcabadoMuro = Utilidades.obtenerMensaje("consultas.ambito.espacio.columna.espaciosAcabadoMuro");
    private final String columnaAreaAcabadoMuro = Utilidades.obtenerMensaje("consultas.ambito.espacio.columna.areaAcabadoMuro");
    private final String columnaProporcionAreaAcabadoMuro = Utilidades.obtenerMensaje("consultas.ambito.espacio.columna.proporcionAreaAcabadoMuro");
    private final String columnaEspaciosVentana = Utilidades.obtenerMensaje("consultas.ambito.espacio.columna.espaciosVentana");
    private final String columnaProporcionVentana = Utilidades.obtenerMensaje("consultas.ambito.espacio.columna.proporcionVentana");
    private final String columnaEspaciosPuerta = Utilidades.obtenerMensaje("consultas.ambito.espacio.columna.espaciosPuerta");
    private final String columnaProporcionPuerta = Utilidades.obtenerMensaje("consultas.ambito.espacio.columna.proporcionPuerta");
    private final String columnaEspaciosTecho = Utilidades.obtenerMensaje("consultas.ambito.espacio.columna.espaciosTecho");
    private final String columnaAreaTecho = Utilidades.obtenerMensaje("consultas.ambito.espacio.columna.areaTecho");
    private final String columnaProporcionAreaTecho = Utilidades.obtenerMensaje("consultas.ambito.espacio.columna.proporcionAreaTecho");
    private final String columnaAreaTotal = Utilidades.obtenerMensaje("consultas.ambito.espacio.columna.areaTotal");
    private final String columnaCondicionAccesibilidad = Utilidades.obtenerMensaje("consultas.ambito.espacio.columna.condicionAccesibilidad");
    private final String columnaCondicionRA = Utilidades.obtenerMensaje("consultas.ambito.espacio.columna.condicionRA");
    private final String columnaCondicionRN = Utilidades.obtenerMensaje("consultas.ambito.espacio.columna.condicionRN");

    @PostConstruct
    public void inicializar() {
        //super.init();
        listaPisos = facParam.consultarValoresTipologia(TipologiaCodigo.ESP_ESC_MAT_PRE_PISOS.getValor(), getUsuarioSesion().getUsuario().getIdioma().getId());
        listaMuros = facParam.consultarValoresTipologia(TipologiaCodigo.ESP_ESC_MAT_PRE_MUROS.getValor(), getUsuarioSesion().getUsuario().getIdioma().getId());
        listaAcabadoMuros = facParam.consultarValoresTipologia(TipologiaCodigo.ESP_ESC_MAT_PRE_ACABADOS.getValor(), getUsuarioSesion().getUsuario().getIdioma().getId());
        listaVentanas = facParam.consultarValoresTipologia(TipologiaCodigo.ESP_ESC_MAT_PRE_VENTANAS.getValor(), getUsuarioSesion().getUsuario().getIdioma().getId());
        listaPuertas = facParam.consultarValoresTipologia(TipologiaCodigo.ESP_ESC_MAT_PRE_PUERTAS.getValor(), getUsuarioSesion().getUsuario().getIdioma().getId());
        listaTechos = facParam.consultarValoresTipologia(TipologiaCodigo.ESP_ESC_MAT_PRE_CIELO_RASO.getValor(), getUsuarioSesion().getUsuario().getIdioma().getId());
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
        if (isMostrarColCondicionAccesibilidad()) {
            listaCamposOpcionales.add(columnaCondicionAccesibilidad);
        }
        if (isMostrarColCondicionRA()) {
            listaCamposOpcionales.add(columnaCondicionRA);
        }
        if (isMostrarColCondicionRN()) {
            listaCamposOpcionales.add(columnaCondicionRN);
        }
        //propias del ambito  
        if (isMostrarColEspaciosPiso()) {
            listaCamposOpcionales.add(columnaEspaciosPiso);
            selccionCamposOpcionales.add(columnaEspaciosPiso);
        }
        if (isMostrarColAreaPiso()) {
            listaCamposOpcionales.add(columnaAreaPiso);
            selccionCamposOpcionales.add(columnaAreaPiso);
        }
        if (isMostrarColProporcionAreaPiso()) {
            listaCamposOpcionales.add(columnaProporcionAreaPiso);
            selccionCamposOpcionales.add(columnaProporcionAreaPiso);
        }
        if (isMostrarColEspaciosMuro()) {
            listaCamposOpcionales.add(columnaEspaciosMuro);
            selccionCamposOpcionales.add(columnaEspaciosMuro);
        }
        if (isMostrarColAreaMuro()) {
            listaCamposOpcionales.add(columnaAreaMuro);
            selccionCamposOpcionales.add(columnaAreaMuro);
        }
        if (isMostrarColProporcionAreaMuro()) {
            listaCamposOpcionales.add(columnaProporcionAreaMuro);
            selccionCamposOpcionales.add(columnaProporcionAreaMuro);
        }
        if (isMostrarColEspaciosAcabadoMuro()) {
            listaCamposOpcionales.add(columnaEspaciosAcabadoMuro);
            selccionCamposOpcionales.add(columnaEspaciosAcabadoMuro);
        }
        if (isMostrarColAreaAcabadoMuro()) {
            listaCamposOpcionales.add(columnaAreaAcabadoMuro);
            selccionCamposOpcionales.add(columnaAreaAcabadoMuro);
        }
        if (isMostrarColProporcionAreaAcabadoMuro()) {
            listaCamposOpcionales.add(columnaProporcionAreaAcabadoMuro);
            selccionCamposOpcionales.add(columnaProporcionAreaAcabadoMuro);
        }
        if (isMostrarColEspaciosVentana()) {
            listaCamposOpcionales.add(columnaEspaciosVentana);
            selccionCamposOpcionales.add(columnaEspaciosVentana);
        }
        if (isMostrarColProporcionVentana()) {
            listaCamposOpcionales.add(columnaProporcionVentana);
            selccionCamposOpcionales.add(columnaProporcionVentana);
        }
        if (isMostrarColEspaciosPuerta()) {
            listaCamposOpcionales.add(columnaEspaciosPuerta);
            selccionCamposOpcionales.add(columnaEspaciosPuerta);
        }
        if (isMostrarColProporcionPuerta()) {
            listaCamposOpcionales.add(columnaProporcionPuerta);
            selccionCamposOpcionales.add(columnaProporcionPuerta);
        }
        if (isMostrarColEspaciosTecho()) {
            listaCamposOpcionales.add(columnaEspaciosTecho);
            selccionCamposOpcionales.add(columnaEspaciosTecho);
        }
        if (isMostrarColAreaTecho()) {
            listaCamposOpcionales.add(columnaAreaTecho);
            selccionCamposOpcionales.add(columnaAreaTecho);
        }
        if (isMostrarColProporcionAreaTecho()) {
            listaCamposOpcionales.add(columnaProporcionAreaTecho);
            selccionCamposOpcionales.add(columnaProporcionAreaTecho);
        }
        if (isMostrarColAreaTotal()) {
            listaCamposOpcionales.add(columnaAreaTotal);
            selccionCamposOpcionales.add(columnaAreaTotal);
        }

    }

    @Override
    public void resetearFiltros() {
        consultaAmbito.resetearNiveles();
        this.resetearListasEstSedesPredios();
        this.seleccionSector = null;
        this.seleccionZona = null;
        this.seleccionCondicionMaterial = null;
        this.seleccionPiso = null;
        this.seleccionMuro = null;
        this.seleccionAcabadoMuro = null;
        this.seleccionVentana = null;
        this.seleccionPuerta = null;
        this.seleccionTecho = null;
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
        this.seleccionCondicionMaterial = null;
        this.seleccionPiso = null;
        this.seleccionMuro = null;
        this.seleccionAcabadoMuro = null;
        this.seleccionVentana = null;
        this.seleccionPuerta = null;
        this.seleccionTecho = null;
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
        filtro.setCondicionMaterial(seleccionCondicionMaterial != null ? seleccionCondicionMaterial.getValor() : null);
        filtro.setCodPiso(elementosSeleccionados(seleccionPiso));
        filtro.setCodMuro(elementosSeleccionados(seleccionMuro));
        filtro.setCodAcaboMuro(elementosSeleccionados(seleccionAcabadoMuro));
        filtro.setCodVentana(elementosSeleccionados(seleccionVentana));
        filtro.setCodPuerta(elementosSeleccionados(seleccionPuerta));
        filtro.setCodTecho(elementosSeleccionados(seleccionTecho));

        List<Object[]> resultados = facAmbito.consultarEspacios(filtro);
        lista = new ArrayList<Registro>(resultados.size());
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
                registro.setZona(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]), getUsuarioSesion().getUsuario().getIdioma().getId()));
                registro.setSector(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]), getUsuarioSesion().getUsuario().getIdioma().getId()));
                registro.setClima(factParam.consultarNombreTipologia(UtilConsulta.longValueOf(fila[i++]), getUsuarioSesion().getUsuario().getIdioma().getId()));
                registro.setCondicionAccesibilidad(UtilConsulta.stringValueOf(fila[i++]));
                registro.setCondicionRA(UtilConsulta.stringValueOf(fila[i++]));
                registro.setCondicionRN(UtilConsulta.stringValueOf(fila[i++]));
            }
            // indicadores
            registro.setEspaciosPiso(UtilConsulta.longValueOf(fila[i++]));
            registro.setAreaPiso(UtilConsulta.doubleValueOf(fila[i++]));
            registro.setProporcionAreaPiso(UtilConsulta.doubleValueOf(fila[i++]));
            registro.setEspaciosMuro(UtilConsulta.longValueOf(fila[i++]));
            registro.setAreaMuro(UtilConsulta.doubleValueOf(fila[i++]));
            registro.setProporcionAreaMuro(UtilConsulta.doubleValueOf(fila[i++]));
            registro.setEspaciosAcabadoMuro(UtilConsulta.longValueOf(fila[i++]));
            registro.setAreaAcabadoMuro(UtilConsulta.doubleValueOf(fila[i++]));
            registro.setProporcionAreaAcabadoMuro(UtilConsulta.doubleValueOf(fila[i++]));
            registro.setEspaciosVentana(UtilConsulta.longValueOf(fila[i++]));
            registro.setProporcionVentana(UtilConsulta.doubleValueOf(fila[i++]));
            registro.setEspaciosPuerta(UtilConsulta.longValueOf(fila[i++]));
            registro.setProporcionPuerta(UtilConsulta.doubleValueOf(fila[i++]));
            registro.setEspaciosTecho(UtilConsulta.longValueOf(fila[i++]));
            registro.setAreaTecho(UtilConsulta.doubleValueOf(fila[i++]));
            registro.setProporcionAreaTecho(UtilConsulta.doubleValueOf(fila[i++]));
            registro.setAreaTotal(UtilConsulta.doubleValueOf(fila[i++]));

            // totales          
            totalConsulta.addNumEspaciosPiso(registro.getEspaciosPiso());
            totalConsulta.addNumAreaPiso(registro.getAreaPiso());
            totalConsulta.addNumEspaciosMuro(registro.getEspaciosMuro());
            totalConsulta.addNumAreaMuro(registro.getAreaMuro());
            totalConsulta.addNumEspaciosAcabadoMuro(registro.getEspaciosAcabadoMuro());
            totalConsulta.addNumAreaAcabadoMuro(registro.getAreaAcabadoMuro());
            totalConsulta.addNumEspaciosVentana(registro.getEspaciosVentana());
            totalConsulta.addNumEspaciosPuerta(registro.getEspaciosPuerta());
            totalConsulta.addNumEspaciosTecho(registro.getEspaciosTecho());
            totalConsulta.addNumAreaTecho(registro.getAreaTecho());
            totalConsulta.addNumAreaTotal(registro.getAreaTotal());
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

    public CalificacionCondicion getSeleccionCondicionMaterial() {
        return seleccionCondicionMaterial;
    }

    public void setSeleccionCondicionMaterial(CalificacionCondicion seleccionCondicionMaterial) {
        this.seleccionCondicionMaterial = seleccionCondicionMaterial;
    }

    public List<TipologiaValorNombre> getListaPisos() {
        return listaPisos;
    }

    public void setListaPisos(List<TipologiaValorNombre> listaPisos) {
        this.listaPisos = listaPisos;
    }

    public List<TipologiaValorNombre> getListaMuros() {
        return listaMuros;
    }

    public void setListaMuros(List<TipologiaValorNombre> listaMuros) {
        this.listaMuros = listaMuros;
    }

    public List<TipologiaValorNombre> getListaAcabadoMuros() {
        return listaAcabadoMuros;
    }

    public void setListaAcabadoMuros(List<TipologiaValorNombre> listaAcabadoMuros) {
        this.listaAcabadoMuros = listaAcabadoMuros;
    }

    public List<TipologiaValorNombre> getListaVentanas() {
        return listaVentanas;
    }

    public void setListaVentanas(List<TipologiaValorNombre> listaVentanas) {
        this.listaVentanas = listaVentanas;
    }

    public List<TipologiaValorNombre> getListaPuertas() {
        return listaPuertas;
    }

    public void setListaPuertas(List<TipologiaValorNombre> listaPuertas) {
        this.listaPuertas = listaPuertas;
    }

    public List<TipologiaValorNombre> getListaTechos() {
        return listaTechos;
    }

    public void setListaTechos(List<TipologiaValorNombre> listaTechos) {
        this.listaTechos = listaTechos;
    }

    public List<String> getSeleccionPiso() {
        return seleccionPiso;
    }

    public void setSeleccionPiso(List<String> seleccionPiso) {
        this.seleccionPiso = seleccionPiso;
    }

    public List<String> getSeleccionMuro() {
        return seleccionMuro;
    }

    public void setSeleccionMuro(List<String> seleccionMuro) {
        this.seleccionMuro = seleccionMuro;
    }

    public List<String> getSeleccionAcabadoMuro() {
        return seleccionAcabadoMuro;
    }

    public void setSeleccionAcabadoMuro(List<String> seleccionAcabadoMuro) {
        this.seleccionAcabadoMuro = seleccionAcabadoMuro;
    }

    public List<String> getSeleccionVentana() {
        return seleccionVentana;
    }

    public void setSeleccionVentana(List<String> seleccionVentana) {
        this.seleccionVentana = seleccionVentana;
    }

    public List<String> getSeleccionPuerta() {
        return seleccionPuerta;
    }

    public void setSeleccionPuerta(List<String> seleccionPuerta) {
        this.seleccionPuerta = seleccionPuerta;
    }

    public List<String> getSeleccionTecho() {
        return seleccionTecho;
    }

    public void setSeleccionTecho(List<String> seleccionTecho) {
        this.seleccionTecho = seleccionTecho;
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
        private String condicionAccesibilidad;
        private String condicionRA;
        private String condicionRN;

        private Long espaciosPiso;
        private Double areaPiso;
        private Double proporcionAreaPiso;
        private Long espaciosMuro;
        private Double areaMuro;
        private Double proporcionAreaMuro;
        private Long espaciosAcabadoMuro;
        private Double areaAcabadoMuro;
        private Double proporcionAreaAcabadoMuro;
        private Long espaciosVentana;
        private Double proporcionVentana;
        private Long espaciosPuerta;
        private Double proporcionPuerta;
        private Long espaciosTecho;
        private Double areaTecho;
        private Double proporcionAreaTecho;
        private Double areaTotal;

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

        public Long getEspaciosPiso() {
            return espaciosPiso;
        }

        public void setEspaciosPiso(Long espaciosPiso) {
            this.espaciosPiso = espaciosPiso;
        }

        public Double getAreaPiso() {
            return areaPiso;
        }

        public void setAreaPiso(Double areaPiso) {
            this.areaPiso = areaPiso;
        }

        public Double getProporcionAreaPiso() {
            return proporcionAreaPiso;
        }

        public void setProporcionAreaPiso(Double proporcionAreaPiso) {
            this.proporcionAreaPiso = proporcionAreaPiso;
        }

        public Long getEspaciosMuro() {
            return espaciosMuro;
        }

        public void setEspaciosMuro(Long espaciosMuro) {
            this.espaciosMuro = espaciosMuro;
        }

        public Double getAreaMuro() {
            return areaMuro;
        }

        public void setAreaMuro(Double areaMuro) {
            this.areaMuro = areaMuro;
        }

        public Double getProporcionAreaMuro() {
            return proporcionAreaMuro;
        }

        public void setProporcionAreaMuro(Double proporcionAreaMuro) {
            this.proporcionAreaMuro = proporcionAreaMuro;
        }

        public Long getEspaciosAcabadoMuro() {
            return espaciosAcabadoMuro;
        }

        public void setEspaciosAcabadoMuro(Long espaciosAcabadoMuro) {
            this.espaciosAcabadoMuro = espaciosAcabadoMuro;
        }

        public Double getAreaAcabadoMuro() {
            return areaAcabadoMuro;
        }

        public void setAreaAcabadoMuro(Double areaAcabadoMuro) {
            this.areaAcabadoMuro = areaAcabadoMuro;
        }

        public Double getProporcionAreaAcabadoMuro() {
            return proporcionAreaAcabadoMuro;
        }

        public void setProporcionAreaAcabadoMuro(Double proporcionAreaAcabadoMuro) {
            this.proporcionAreaAcabadoMuro = proporcionAreaAcabadoMuro;
        }

        public Long getEspaciosVentana() {
            return espaciosVentana;
        }

        public void setEspaciosVentana(Long espaciosVentana) {
            this.espaciosVentana = espaciosVentana;
        }

        public Double getProporcionVentana() {
            return proporcionVentana;
        }

        public void setProporcionVentana(Double proporcionVentana) {
            this.proporcionVentana = proporcionVentana;
        }

        public Long getEspaciosPuerta() {
            return espaciosPuerta;
        }

        public void setEspaciosPuerta(Long espaciosPuerta) {
            this.espaciosPuerta = espaciosPuerta;
        }

        public Double getProporcionPuerta() {
            return proporcionPuerta;
        }

        public void setProporcionPuerta(Double proporcionPuerta) {
            this.proporcionPuerta = proporcionPuerta;
        }

        public Long getEspaciosTecho() {
            return espaciosTecho;
        }

        public void setEspaciosTecho(Long espaciosTecho) {
            this.espaciosTecho = espaciosTecho;
        }

        public Double getAreaTecho() {
            return areaTecho;
        }

        public void setAreaTecho(Double areaTecho) {
            this.areaTecho = areaTecho;
        }

        public Double getProporcionAreaTecho() {
            return proporcionAreaTecho;
        }

        public void setProporcionAreaTecho(Double proporcionAreaTecho) {
            this.proporcionAreaTecho = proporcionAreaTecho;
        }

        public Double getAreaTotal() {
            return areaTotal;
        }

        public void setAreaTotal(Double areaTotal) {
            this.areaTotal = areaTotal;
        }
    }

    public class TotalConsulta {

        private Long numEspaciosPiso;
        private Double numAreaPiso;
        private Long numEspaciosMuro;
        private Double numAreaMuro;
        private Long numEspaciosAcabadoMuro;
        private Double numAreaAcabadoMuro;
        private Long numEspaciosVentana;
        private Long numEspaciosPuerta;
        private Long numEspaciosTecho;
        private Double numAreaTecho;
        private Double numAreaTotal;

        public void inicializar() {
            numEspaciosPiso = 0L;
            numAreaPiso = 0D;
            numEspaciosMuro = 0L;
            numAreaMuro = 0D;
            numEspaciosAcabadoMuro = 0L;
            numAreaAcabadoMuro = 0D;
            numEspaciosVentana = 0L;
            numEspaciosPuerta = 0L;
            numEspaciosTecho = 0L;
            numAreaTecho = 0D;
            numAreaTotal = 0D;
        }

        public Long getNumEspaciosPiso() {
            return numEspaciosPiso;
        }

        public Double getNumAreaPiso() {
            return numAreaPiso;
        }

        public Long getNumEspaciosMuro() {
            return numEspaciosMuro;
        }

        public Double getNumAreaMuro() {
            return numAreaMuro;
        }

        public Long getNumEspaciosAcabadoMuro() {
            return numEspaciosAcabadoMuro;
        }

        public Double getNumAreaAcabadoMuro() {
            return numAreaAcabadoMuro;
        }

        public Long getNumEspaciosVentana() {
            return numEspaciosVentana;
        }

        public Long getNumEspaciosPuerta() {
            return numEspaciosPuerta;
        }

        public Long getNumEspaciosTecho() {
            return numEspaciosTecho;
        }

        public Double getNumAreaTecho() {
            return numAreaTecho;
        }

        public Double getNumAreaTotal() {
            return numAreaTotal;
        }

        public void addNumEspaciosPiso(Long valor) {
            if (valor != null) {
                this.numEspaciosPiso += valor;
            }
        }

        public void addNumAreaPiso(Double valor) {
            if (valor != null) {
                this.numAreaPiso += valor;
            }
        }

        public void addNumEspaciosMuro(Long valor) {
            if (valor != null) {
                this.numEspaciosMuro += valor;
            }
        }

        public void addNumAreaMuro(Double valor) {
            if (valor != null) {
                this.numAreaMuro += valor;
            }
        }

        public void addNumEspaciosAcabadoMuro(Long valor) {
            if (valor != null) {
                this.numEspaciosAcabadoMuro += valor;
            }
        }

        public void addNumAreaAcabadoMuro(Double valor) {
            if (valor != null) {
                this.numAreaAcabadoMuro += valor;
            }
        }

        public void addNumEspaciosVentana(Long valor) {
            if (valor != null) {
                this.numEspaciosVentana += valor;
            }
        }

        public void addNumEspaciosPuerta(Long valor) {
            if (valor != null) {
                this.numEspaciosPuerta += valor;
            }
        }

        public void addNumEspaciosTecho(Long valor) {
            if (valor != null) {
                this.numEspaciosTecho += valor;
            }
        }

        public void addNumAreaTecho(Double valor) {
            if (valor != null) {
                this.numAreaTecho += valor;
            }
        }

        public void addNumAreaTotal(Double valor) {
            if (valor != null) {
                this.numAreaTotal += valor;
            }
        }
    }

    // mostrar columnas  
    public boolean isMostrarColCondicionAccesibilidad() {
        return isAgrupamientoPredio();
    }

    public boolean isMostrarColCondicionRA() {
        return isAgrupamientoPredio();
    }

    public boolean isMostrarColCondicionRN() {
        return isAgrupamientoPredio();
    }

    public boolean isMostrarColEspaciosPiso() {
        return true;
    }

    public boolean isMostrarColAreaPiso() {
        return true;
    }

    public boolean isMostrarColProporcionAreaPiso() {
        return true;
    }

    public boolean isMostrarColEspaciosMuro() {
        return true;
    }

    public boolean isMostrarColAreaMuro() {
        return true;
    }

    public boolean isMostrarColProporcionAreaMuro() {
        return true;
    }

    public boolean isMostrarColEspaciosAcabadoMuro() {
        return true;
    }

    public boolean isMostrarColAreaAcabadoMuro() {
        return true;
    }

    public boolean isMostrarColProporcionAreaAcabadoMuro() {
        return true;
    }

    public boolean isMostrarColEspaciosVentana() {
        return true;
    }

    public boolean isMostrarColProporcionVentana() {
        return true;
    }

    public boolean isMostrarColEspaciosPuerta() {
        return true;
    }

    public boolean isMostrarColProporcionPuerta() {
        return true;
    }

    public boolean isMostrarColEspaciosTecho() {
        return true;
    }

    public boolean isMostrarColAreaTecho() {
        return true;
    }

    public boolean isMostrarColProporcionAreaTecho() {
        return true;
    }

    public boolean isMostrarColAreaTotal() {
        return true;
    }

    //getter de columnas    
    public String getColumnaEspaciosPiso() {
        return columnaEspaciosPiso;
    }

    public String getColumnaAreaPiso() {
        return columnaAreaPiso;
    }

    public String getColumnaProporcionAreaPiso() {
        return columnaProporcionAreaPiso;
    }

    public String getColumnaEspaciosMuro() {
        return columnaEspaciosMuro;
    }

    public String getColumnaAreaMuro() {
        return columnaAreaMuro;
    }

    public String getColumnaProporcionAreaMuro() {
        return columnaProporcionAreaMuro;
    }

    public String getColumnaEspaciosAcabadoMuro() {
        return columnaEspaciosAcabadoMuro;
    }

    public String getColumnaAreaAcabadoMuro() {
        return columnaAreaAcabadoMuro;
    }

    public String getColumnaProporcionAreaAcabadoMuro() {
        return columnaProporcionAreaAcabadoMuro;
    }

    public String getColumnaEspaciosVentana() {
        return columnaEspaciosVentana;
    }

    public String getColumnaProporcionVentana() {
        return columnaProporcionVentana;
    }

    public String getColumnaEspaciosPuerta() {
        return columnaEspaciosPuerta;
    }

    public String getColumnaProporcionPuerta() {
        return columnaProporcionPuerta;
    }

    public String getColumnaEspaciosTecho() {
        return columnaEspaciosTecho;
    }

    public String getColumnaAreaTecho() {
        return columnaAreaTecho;
    }

    public String getColumnaProporcionAreaTecho() {
        return columnaProporcionAreaTecho;
    }

    public String getColumnaAreaTotal() {
        return columnaAreaTotal;
    }

    public String getColumnaCondicionAccesibilidad() {
        return columnaCondicionAccesibilidad;
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
