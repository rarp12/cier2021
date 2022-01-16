/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.instrumento;

import co.stackpointer.cier.modelo.entidad.dpa.Nivel;
import co.stackpointer.cier.modelo.entidad.establecimiento.Establecimiento;
import co.stackpointer.cier.modelo.entidad.establecimiento.Predio;
import co.stackpointer.cier.modelo.entidad.establecimiento.Sede;
import co.stackpointer.cier.modelo.fachada.EstablecimientoFacadeLocal;
import co.stackpointer.cier.modelo.fachada.EstructuraDPAFacadeLocal;
import co.stackpointer.cier.modelo.fachada.InstrumentoFacadeLocal;
import co.stackpointer.cier.modelo.fachada.PredioFacadeLocal;
import co.stackpointer.cier.modelo.interfaceDPA.EstablecimientoDPA;
import co.stackpointer.cier.modelo.interfaceDPA.PredioDPA;
import co.stackpointer.cier.modelo.interfaceDPA.SedeDPA;
import co.stackpointer.cier.modelo.tipo.Estado;
import co.stackpointer.cier.modelo.utilidad.UtilCadena;
import co.stackpointer.cier.modelo.utilidad.UtilFecha;
import co.stackpointer.cier.vista.Utilidades;
import co.stackpointer.cier.vistacontrolador.bean.UsuarioSesionBean;
import co.stackpointer.cier.vistacontrolador.consulta.ConsultaBase;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.omnifaces.util.Messages;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author user
 */
@ManagedBean(name = "busquedaPredio")
@ViewScoped
public class BusquedaPredio extends ConsultaBase implements Serializable {
    
  
    @EJB
    PredioFacadeLocal fPredios;
    
    @EJB
    InstrumentoFacadeLocal fInstrumentos;
          
    @EJB
    EstablecimientoFacadeLocal fEstablecimiento; 
    
    @EJB
    EstructuraDPAFacadeLocal festructuraDPA;
    
    
    
    private String nombre;
    private String codigo;
    Predio predioSeleccionado;
    
    //Creacion Predio
    private Predio predioNuevo;
    private String codigoOficial;
    private String codEstablecimiento;
    private List<Predio> lista;
    private List<? extends SedeDPA> sedesEncontradas;

    private String mensaje;
    
    
    @PostConstruct
    public void init()
    { 
        super.initActual();
//        listaPredios= new ArrayList<Predio>();
        predioSeleccionado=null;
      
    }
    
  public void prepararSeleccionarSede(){        
        codEstablecimiento=null;
        sedesEncontradas = new ArrayList<SedeDPA>();
        cargarNivel0();
    }
    
    public void consultarSedesPorEstablecimiento(){       
        sedesEncontradas = festructuraDPA.consultarSedes(codEstablecimiento);
    }
  
     public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Predio getPredioSeleccionado() {
        return predioSeleccionado;
    }

    public void setPredioSeleccionado(Predio predioSeleccionado) {
        this.predioSeleccionado = predioSeleccionado;
    }

    public String getCodEstablecimiento() {
        return codEstablecimiento;
    }

    public void setCodEstablecimiento(String codEstablecimiento) {
        this.codEstablecimiento = codEstablecimiento;
    }

    
    public List<Establecimiento> complete(String query) {
        
        
        List<Establecimiento> establecimeintosFiltrados= new ArrayList<Establecimiento>();
		
		for (EstablecimientoDPA esta: listaEstablecimientos) {
                if(esta.getNombre().toUpperCase().contains(query.toUpperCase()))
                    establecimeintosFiltrados.add((Establecimiento)esta);
		}
		
		return establecimeintosFiltrados;
	}
  
    public void cambioEstablecimientoVig(SelectEvent event) {
        this.resetearListasSedesPredios();
        
        if (seleccionEst != null) {
            
             Establecimiento est=facEstDPA.consultarEstablecimiento(Long.valueOf(seleccionEst.getId()));
             seleccionEst=est;
            listaSedes = est.getSedes();
        }
    }
    
    
    
    public Predio getPredioNuevo() {
        return predioNuevo;
    }

    public void setPredioNuevo(Predio predioNuevo) {
        this.predioNuevo = predioNuevo;
    }
    
    public void instanciarNuevoPredio() {
        predioNuevo = new Predio();
    }

    @Override
    public void resetearFiltros() {
        this.resetearNiveles();
        this.resetearListasEstSedesPredios();
        this.cargarNivel1();
        predioNuevo=new Predio();
        codigoOficial=null;
        codEstablecimiento=null;
        lista=null;
    }
    
    @Override
    public void cambioPeriodo() {
        super.initHistoricos(periodo);
        super.cargarNivel1();
    }
    
    public void consultarPrediosPorEstablecimiento(){
        lista=new ArrayList<Predio>();
        if(!UtilCadena.isNullOVacio(codEstablecimiento)){
            Establecimiento est=fEstablecimiento.getEstablecimientoPorCodigo(codEstablecimiento);
            for (Sede sede:est.getSedes()){
                lista.addAll(sede.getPredios());
            }
        }
        if(lista.isEmpty()){
            mensajeTablaVacia = Utilidades.obtenerMensaje("aplicacion.general.noExistenRegistros");
        }
        
    }
    
    public void cargarRutaDpa(){
        System.out.print("prueba");
    }
    
    public void guardarPredio() {
        try {
            if (UtilCadena.isNullOVacio(predioNuevo.getNombre())) {
                RequestContext.getCurrentInstance().addCallbackParam("validationFailed", true);
                Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("dig.creacion.lbl.agregarPredio.Digite.nombrePredio"));
            } else {
                if (UtilCadena.isNullOVacio(seleccionSede)) {
                    RequestContext.getCurrentInstance().addCallbackParam("validationFailed", true);
                    Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("dig.creacion.lbl.agregarPredio.SeleccionarSede"));
                } else {
                    ResourceBundle bundle = ResourceBundle.getBundle("etiquetas", FacesContext.getCurrentInstance().getViewRoot().getLocale());

                    if (predioNuevo.getCodigoOficial() != null && !"".equals(predioNuevo.getCodigoOficial().trim())
                            && (codigoOficial == null || !codigoOficial.equals(predioNuevo.getCodigoOficial().trim()))
                            && fEstablecimiento.existePredioCodOf(predioNuevo.getCodigoOficial().trim())) {
                        codigoOficial = predioNuevo.getCodigoOficial().trim();
                        mensaje = bundle.getString("dig.creacion.val.agregarPredio.existeCodOf");
                    } else {
                        Sede sede = fEstablecimiento.getSedePorCodigo(this.seleccionSede.getCodigo());
                        predioNuevo.setCodigo(calcularCodigoInterno());
                        predioNuevo.setFechaCreacion(UtilFecha.obtenerFechaActual());
                        predioNuevo.setEstado(Estado.A.toString());
                        sede.getPredios().add(predioNuevo);
                        fEstablecimiento.actualizarSede(usuarioSesion.getUsuario().getUsername(), sede);
                        //respuestasDigitadasMap.put(this.obtenerRespuestaDigitada("RESP_010"),predioNuevo.getCodigo());
                        mensaje = bundle.getString("dig.creacion.val.agregarPredio.guardado");
                        this.resetearFiltros();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
           // ManejadorErrores.ingresarExcepcionEnLog(e, BusquedaPredio.class);
            RequestContext.getCurrentInstance().addCallbackParam("validationFailed", true);
            Messages.addFlashGlobalFatal(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"));
        }
    }
    
    public void noGuardarPredio() {
        codigoOficial=null;
    }
    
    private String calcularCodigoInterno(){
        Double sec=fInstrumentos.obtenerSecPredio();
        if(sec==null) sec=new Double(0);
        String codigoInterno=calcularMinimosCodigos();
        while((codigoInterno+(sec.intValue()+1)).length()<20){
            codigoInterno=codigoInterno+"0";
        }
        codigoInterno=codigoInterno+(sec.intValue()+1);
        return codigoInterno;
    }
    
    
    private String calcularMinimosCodigos() {
        String codigosMinimos = "";
        String padre;
        if (this.getSeleccionNivel5() != null) {
            padre = padreNivel4 != null ? padreNivel4.getCodigo() : this.getSeleccionNivel4().getCodigo();
            codigosMinimos = padre + this.getSeleccionNivel5().getCodigo();
        } else if (this.getSeleccionNivel4() != null) {
            padre = padreNivel3 != null ? padreNivel3.getCodigo() : this.getSeleccionNivel3().getCodigo();
            codigosMinimos = padre + this.getSeleccionNivel4().getCodigo();
        } else if (this.getSeleccionNivel3() != null) {
            padre = padreNivel2 != null ? padreNivel2.getCodigo() : this.getSeleccionNivel2().getCodigo();
            codigosMinimos = padre + this.getSeleccionNivel3().getCodigo();
        } else if (this.getSeleccionNivel2() != null) {
            padre = padreNivel1 != null ? padreNivel1.getCodigo() : this.getSeleccionNivel1().getCodigo();
            codigosMinimos = padre + this.getSeleccionNivel2().getCodigo();
        } else if (this.getSeleccionNivel1() != null) {
            padre = padreNivel0 != null ? padreNivel0.getCodigo() : this.getSeleccionNivel0().getCodigo();
            codigosMinimos = padre + this.getSeleccionNivel1().getCodigo();
        }
        return codigosMinimos;
    }

    public String getCodigoOficial() {
        return codigoOficial;
    }

    public void setCodigoOficial(String codigoOficial) {
        this.codigoOficial = codigoOficial;
    }

    public String getMensaje() {
        return mensaje;
    }

    public List<Predio> getLista() {
        return lista;
    }

    public void setLista(List<Predio> lista) {
        this.lista = lista;
    }
    
    @Override
     public void cambioSede() {
        seleccionPredio = null;
        seleccionSede= facEstDPA.consultarSede(seleccionSede.getId());
        listaPredios = seleccionSede != null ? seleccionSede.getPredios() : new ArrayList<PredioDPA>();
    }

    public List<? extends SedeDPA> getSedesEncontradas() {
        return sedesEncontradas;
    }

    public void setSedesEncontradas(List<? extends SedeDPA> sedesEncontradas) {
        this.sedesEncontradas = sedesEncontradas;
    }
    
}
