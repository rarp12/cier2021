/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.gerenciales;

import co.stackpointer.cier.modelo.entidad.BID.IndicadoresBID;
import co.stackpointer.cier.modelo.entidad.BID.PeriodosBID;
import co.stackpointer.cier.modelo.entidad.BID.TiposIndicadoresBID;
import co.stackpointer.cier.modelo.fachada.BIDFacadeLocal;
import co.stackpointer.cier.modelo.tipo.TipoIndicadorBID;
import co.stackpointer.cier.modelo.utilidad.UtilCadena;
import co.stackpointer.cier.modelo.utilidad.UtilOut;
import static co.stackpointer.cier.vista.Utilidades.mostrarMensajeError;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

    
/**
 *
 * @author user
 */
@ManagedBean(name = "consultaIndicadoresBID")
@ViewScoped
public class ConsultaIndicadoresBIDBean {
    
    @EJB
    private BIDFacadeLocal fachadaBID; 
    
    private List<Registro> lista;
    List<String> colums;
    List<String> columsTotal;
    
    String mensajeTablaVacia;
    
    public PeriodosBID periodoBID;
    public List<PeriodosBID> listaPeriodos;
    
    HashMap<String, String> hmEstilos;
    HashMap<Long, String> hmColumnas;
    
    private boolean checkRiesgos;
    private boolean checkAccesibilidadExt;
    private boolean checkSeguridad;
    private boolean checkPropiedad;
    private boolean checkEstadoEdifEsp;
    private boolean checkServicios;
    private boolean checkAmbientes;
    private boolean checkOferta;
    
    ResourceBundle bundle;
    
    @PostConstruct
    public void inicializar() {
        lista=new ArrayList<Registro>();
        colums=new ArrayList<String>();
        columsTotal=new ArrayList<String>();
        periodoBID=null;
        listaPeriodos=fachadaBID.getPeriodosBID();
        for(PeriodosBID p:listaPeriodos){
            if(periodoBID==null
                    || p.getId()>periodoBID.getId()){
                periodoBID=p;
            }
        }
        hmEstilos=new HashMap<String, String>();
        hmColumnas=new HashMap<Long, String>();
        checkRiesgos=true;
        checkAccesibilidadExt=true;
        checkSeguridad=true;
        checkPropiedad=true;
        checkEstadoEdifEsp=true;
        checkServicios=true;
        checkAmbientes=true;
        checkOferta=true;
        bundle = ResourceBundle.getBundle("etiquetas", FacesContext.getCurrentInstance().getViewRoot().getLocale());
            
    }

       
    public void consultar() {
        try{            
            List<IndicadoresBID> resultados = fachadaBID.consultarIndicadoresBID(periodoBID);
            lista = new ArrayList<Registro>(resultados.size());
            if(lista.size()<1)
            {
                mensajeTablaVacia = bundle.getString("aplicacion.general.noExistenRegistros");
            }
            Registro registro=null;
            HashMap<Integer, Registro> hmRegistros=new HashMap<Integer, Registro>();
            
            //Las proporciones se muestran sin decimales y con %
            List<Long> valoresPorcentaje=new ArrayList<Long>();
            valoresPorcentaje.add(TipoIndicadorBID.PROPORCION_PREDIOS_PRESENTAN_RIESGOS_NATURALES.getId());
            valoresPorcentaje.add(TipoIndicadorBID.PROPORCION_PREDIOS_PRESENTAN_RIESGOS_ANTROPICOS.getId());
            valoresPorcentaje.add(TipoIndicadorBID.PROPORCION_PREDIOS_SIN_ACCESO_BUEN_ESTADO.getId());
            valoresPorcentaje.add(TipoIndicadorBID.PROPORCION_PREDIOS_RUTA_EVALUACION_CRITICA.getId());
            valoresPorcentaje.add(TipoIndicadorBID.PROPORCION_PREDIOS_CONDICION_ESTRUCTURA_CRITICA.getId());
            valoresPorcentaje.add(TipoIndicadorBID.PROPORCION_PREDIOS_CON_PROBLEMAS_LEGALIZACION.getId());
            valoresPorcentaje.add(TipoIndicadorBID.PROPORCION_PREDIOS_CONDICION_MATERIALIDAD_CRITICA.getId());
            valoresPorcentaje.add(TipoIndicadorBID.PROPORCION_PREDIOS_DISPONIBILIDAD_AGUA.getId());
            valoresPorcentaje.add(TipoIndicadorBID.PROPORCION_PREDIOS_AGUA_CONDICION_INSUFICIENTE.getId());
            valoresPorcentaje.add(TipoIndicadorBID.PROPORCION_PREDIOS_DISPONIBILIDAD_ENERGIA_ELECTRICA.getId());
            valoresPorcentaje.add(TipoIndicadorBID.PROPORCION_PREDIOS_ENERGIA_ELECTRICA_CONDICION_INSUFICIENTE.getId());
            valoresPorcentaje.add(TipoIndicadorBID.PROPORCION_PREDIOS_SATURADOS.getId());
            valoresPorcentaje.add(TipoIndicadorBID.PROPORCION_PREDIOS_SUBUTILIZADOS.getId());
            hmEstilos.clear();
            for (IndicadoresBID indicadorBID : resultados) {
                HashMap<String, String> hmAux=new HashMap<String, String>();
                if(hmRegistros.get(indicadorBID.getPaisBID().getId())!=null){
                    registro=hmRegistros.get(indicadorBID.getPaisBID().getId());
                    hmAux=registro.getInd_nombre();
                }else{
                    registro = new Registro();
                    registro.setId(indicadorBID.getPaisBID().getId());
                    registro.setCod(indicadorBID.getPaisBID().getCodigo());
                    hmAux.put(bundle.getString("gerencial.bid.consulta.calificacionesBID.pais"),indicadorBID.getPaisBID().getNombre());
                    hmAux.put(bundle.getString("gerencial.bid.consulta.calificacionesBID.periodoInfo"),indicadorBID.getPeriodoInfo()+"");
                    hmEstilos.put(bundle.getString("gerencial.bid.consulta.calificacionesBID.periodoInfo"), "campoNumero");

                }
                
                if(!hmColumnas.containsKey(indicadorBID.getTipoIndicadorBID().getId())){
                    hmColumnas.put(indicadorBID.getTipoIndicadorBID().getId(), bundle.getString("gerencial.bid.consulta.tipos.indicadores."+indicadorBID.getTipoIndicadorBID().getCodigo()));
                }
                
                //completamos el mapa de estilos
                if(!hmEstilos.containsKey(indicadorBID.getTipoIndicadorBID().getCodigo())){
                    if(UtilCadena.esNumerico(indicadorBID.getValor())){
                        hmEstilos.put(bundle.getString("gerencial.bid.consulta.tipos.indicadores."+indicadorBID.getTipoIndicadorBID().getCodigo()), "campoNumero");
                    }
                    this.darEstilo(indicadorBID.getTipoIndicadorBID().getId(), bundle.getString("gerencial.bid.consulta.tipos.indicadores."+indicadorBID.getTipoIndicadorBID().getCodigo()));
                }
                
                String valor = "";
                if(valoresPorcentaje.contains(indicadorBID.getTipoIndicadorBID().getId())){
                    valor=new BigDecimal(indicadorBID.getValor()).setScale(0,RoundingMode.HALF_UP)+"%";
                }else{
                    valor=indicadorBID.getValor();
                }
                hmAux.put(bundle.getString("gerencial.bid.consulta.tipos.indicadores."+indicadorBID.getTipoIndicadorBID().getCodigo()),valor);                
                registro.setInd_nombre(hmAux);
                hmRegistros.put(registro.getId(), registro);
            }
            
            for(Registro reg:hmRegistros.values()){
                lista.add(reg);
            }
            
            this.actualizarColumnas();
            this.cargarColumsTotal();
            
        }catch(Exception e){
            UtilOut.println(e);
            mostrarMensajeError(bundle.getString("aplicacion.general.errorAplicacion"), e.getMessage());
        }
    }
    
    private void darEstilo(Long id, String nombre){
        List<Long> rosa=new ArrayList<Long>();
        rosa.add(TipoIndicadorBID.PROPORCION_PREDIOS_PRESENTAN_RIESGOS_NATURALES.getId());
        rosa.add(TipoIndicadorBID.POBLACION_AFECTADA_RIESGO_NATURAL.getId());
        rosa.add(TipoIndicadorBID.PROPORCION_PREDIOS_PRESENTAN_RIESGOS_ANTROPICOS.getId());
        rosa.add(TipoIndicadorBID.POBLACION_AFECTADA_RIESGO_ANTROPICO.getId());
        List<Long> gris=new ArrayList<Long>();
        gris.add(TipoIndicadorBID.PROPORCION_PREDIOS_SIN_ACCESO_BUEN_ESTADO.getId());
        List<Long> lila=new ArrayList<Long>();
        lila.add(TipoIndicadorBID.PROPORCION_PREDIOS_RUTA_EVALUACION_CRITICA.getId());
        lila.add(TipoIndicadorBID.PROPORCION_PREDIOS_CONDICION_ESTRUCTURA_CRITICA.getId());
        List<Long> azul=new ArrayList<Long>();
        azul.add(TipoIndicadorBID.PROPORCION_PREDIOS_CON_PROBLEMAS_LEGALIZACION.getId());
        azul.add(TipoIndicadorBID.NUM_ESTUDIANTES_PREDIOS_PROBLEMAS_LEGALIZACION.getId());
        List<Long> verdeOscuro=new ArrayList<Long>();
        verdeOscuro.add(TipoIndicadorBID.PROPORCION_PREDIOS_CONDICION_MATERIALIDAD_CRITICA.getId());
        List<Long> naranja=new ArrayList<Long>();
        naranja.add(TipoIndicadorBID.PROPORCION_PREDIOS_DISPONIBILIDAD_AGUA.getId());
        naranja.add(TipoIndicadorBID.PROPORCION_PREDIOS_AGUA_CONDICION_INSUFICIENTE.getId());
        naranja.add(TipoIndicadorBID.PROPORCION_PREDIOS_DISPONIBILIDAD_ENERGIA_ELECTRICA.getId());
        naranja.add(TipoIndicadorBID.PROPORCION_PREDIOS_ENERGIA_ELECTRICA_CONDICION_INSUFICIENTE.getId());
        List<Long> amarillo=new ArrayList<Long>();
        amarillo.add(TipoIndicadorBID.M2_ALUMNO_AULA.getId());
        amarillo.add(TipoIndicadorBID.M2_ALUMNO_SERVICIOS_SANITARIOS.getId());
        amarillo.add(TipoIndicadorBID.ESTANDAR_AULA.getId());
        amarillo.add(TipoIndicadorBID.ESTANDAR_SERVICIOS_SANITARIOS.getId());
        List<Long> verde=new ArrayList<Long>();
        verde.add(TipoIndicadorBID.UTILIZACION_ESTADAR_TOTAL.getId());
        verde.add(TipoIndicadorBID.CUPOS_TOTAL.getId());
        verde.add(TipoIndicadorBID.PROPORCION_PREDIOS_SATURADOS.getId());
        verde.add(TipoIndicadorBID.PROPORCION_PREDIOS_SUBUTILIZADOS.getId());
        verde.add(TipoIndicadorBID.AREA_PREDIO.getId());
        verde.add(TipoIndicadorBID.AREA_CONSTRUCCION_TOTAL.getId());
        verde.add(TipoIndicadorBID.INDICE_OCUPACION.getId());
        verde.add(TipoIndicadorBID.ESTANDAR_CONSTRUCCION.getId());
        verde.add(TipoIndicadorBID.INDICE_CONSTRUCCION.getId());
        verde.add(TipoIndicadorBID.ESTANDAR_INDICE_OCUPACION.getId());
        verde.add(TipoIndicadorBID.CUMPLIMIENTO_NORMA_CONSTRUC_TOTAL.getId());
        
        if(rosa.contains(id)){
            hmEstilos.put(nombre, hmEstilos.get(nombre)+" rosa");
        }else if(gris.contains(id)){
            hmEstilos.put(nombre, hmEstilos.get(nombre)+" gris");
        }else if(lila.contains(id)){
            hmEstilos.put(nombre, hmEstilos.get(nombre)+" lila");
        }else if(azul.contains(id)){
            hmEstilos.put(nombre, hmEstilos.get(nombre)+" azul");
        }else if(verdeOscuro.contains(id)){
            hmEstilos.put(nombre, hmEstilos.get(nombre)+" verdeOscuro");
        }else if(naranja.contains(id)){
            hmEstilos.put(nombre, hmEstilos.get(nombre)+" naranja");
        }else if(amarillo.contains(id)){
            hmEstilos.put(nombre, hmEstilos.get(nombre)+" amarillo");
        }else if(verde.contains(id)){
            hmEstilos.put(nombre, hmEstilos.get(nombre)+" verde");
        }

    }
    
    public String obtenerEstilo(String col){
        return hmEstilos.containsKey(col)?hmEstilos.get(col):"";
    }
    
    public void actualizarColumnas(){
        if(hmColumnas.isEmpty()){
            List<TiposIndicadoresBID> listaTiposInd = fachadaBID.consultarTiposIndicadoresBID();
            for(TiposIndicadoresBID tip:listaTiposInd){
                hmColumnas.put(tip.getId(), bundle.getString("gerencial.bid.consulta.tipos.indicadores."+tip.getCodigo()));
                this.darEstilo(tip.getId(), bundle.getString("gerencial.bid.consulta.tipos.indicadores."+tip.getCodigo()));
            }
        }
        colums=new ArrayList<String>();
        colums.add(bundle.getString("gerencial.bid.consulta.calificacionesBID.pais"));
        colums.add(bundle.getString("gerencial.bid.consulta.calificacionesBID.periodoInfo"));
        colums.add(hmColumnas.get(TipoIndicadorBID.TOTAL_ESTUDIANTES.getId()));
        colums.add(hmColumnas.get(TipoIndicadorBID.NUM_ESTABLECIMIENTOS.getId()));
        colums.add(hmColumnas.get(TipoIndicadorBID.NUM_PREDIOS.getId()));
            
        if(this.isCheckRiesgos()){
            colums.add(hmColumnas.get(TipoIndicadorBID.PROPORCION_PREDIOS_PRESENTAN_RIESGOS_NATURALES.getId()));
            colums.add(hmColumnas.get(TipoIndicadorBID.POBLACION_AFECTADA_RIESGO_NATURAL.getId()));
            colums.add(hmColumnas.get(TipoIndicadorBID.PROPORCION_PREDIOS_PRESENTAN_RIESGOS_ANTROPICOS.getId()));
            colums.add(hmColumnas.get(TipoIndicadorBID.POBLACION_AFECTADA_RIESGO_ANTROPICO.getId()));
        }
        if(this.isCheckAccesibilidadExt()){
            colums.add(hmColumnas.get(TipoIndicadorBID.PROPORCION_PREDIOS_SIN_ACCESO_BUEN_ESTADO.getId()));            
        }
        if(this.isCheckSeguridad()){
            colums.add(hmColumnas.get(TipoIndicadorBID.PROPORCION_PREDIOS_RUTA_EVALUACION_CRITICA.getId()));
            colums.add(hmColumnas.get(TipoIndicadorBID.PROPORCION_PREDIOS_CONDICION_ESTRUCTURA_CRITICA.getId()));
        }
        if(this.isCheckPropiedad()){
            colums.add(hmColumnas.get(TipoIndicadorBID.PROPORCION_PREDIOS_CON_PROBLEMAS_LEGALIZACION.getId()));
            colums.add(hmColumnas.get(TipoIndicadorBID.NUM_ESTUDIANTES_PREDIOS_PROBLEMAS_LEGALIZACION.getId()));
        }
        if(this.isCheckEstadoEdifEsp()){
            colums.add(hmColumnas.get(TipoIndicadorBID.PROPORCION_PREDIOS_CONDICION_MATERIALIDAD_CRITICA.getId()));
        }
        if(this.isCheckServicios()){
            colums.add(hmColumnas.get(TipoIndicadorBID.PROPORCION_PREDIOS_DISPONIBILIDAD_AGUA.getId()));
            colums.add(hmColumnas.get(TipoIndicadorBID.PROPORCION_PREDIOS_AGUA_CONDICION_INSUFICIENTE.getId()));
            colums.add(hmColumnas.get(TipoIndicadorBID.PROPORCION_PREDIOS_DISPONIBILIDAD_ENERGIA_ELECTRICA.getId()));
            colums.add(hmColumnas.get(TipoIndicadorBID.PROPORCION_PREDIOS_ENERGIA_ELECTRICA_CONDICION_INSUFICIENTE.getId()));
        }
        if(this.isCheckAmbientes()){
            colums.add(hmColumnas.get(TipoIndicadorBID.ESTANDAR_AULA.getId()));
            colums.add(hmColumnas.get(TipoIndicadorBID.M2_ALUMNO_AULA.getId()));
            colums.add(hmColumnas.get(TipoIndicadorBID.ESTANDAR_SERVICIOS_SANITARIOS.getId()));
            colums.add(hmColumnas.get(TipoIndicadorBID.M2_ALUMNO_SERVICIOS_SANITARIOS.getId()));
        }
        if(this.isCheckOferta()){
            colums.add(hmColumnas.get(TipoIndicadorBID.UTILIZACION_ESTADAR_TOTAL.getId()));
            colums.add(hmColumnas.get(TipoIndicadorBID.CUPOS_TOTAL.getId()));
            colums.add(hmColumnas.get(TipoIndicadorBID.PROPORCION_PREDIOS_SATURADOS.getId()));
            colums.add(hmColumnas.get(TipoIndicadorBID.PROPORCION_PREDIOS_SUBUTILIZADOS.getId()));
            colums.add(hmColumnas.get(TipoIndicadorBID.AREA_PREDIO.getId()));
            colums.add(hmColumnas.get(TipoIndicadorBID.AREA_CONSTRUCCION_TOTAL.getId()));
            colums.add(hmColumnas.get(TipoIndicadorBID.CUMPLIMIENTO_NORMA_CONSTRUC_TOTAL.getId()));
            colums.add(hmColumnas.get(TipoIndicadorBID.ESTANDAR_INDICE_OCUPACION.getId()));
            colums.add(hmColumnas.get(TipoIndicadorBID.INDICE_OCUPACION.getId()));
            colums.add(hmColumnas.get(TipoIndicadorBID.ESTANDAR_CONSTRUCCION.getId()));
            colums.add(hmColumnas.get(TipoIndicadorBID.INDICE_CONSTRUCCION.getId()));
        }
    }
    
    
    public void cargarColumsTotal(){
        columsTotal=new ArrayList<String>();
        columsTotal.add(bundle.getString("gerencial.bid.consulta.calificacionesBID.pais"));
        columsTotal.add(bundle.getString("gerencial.bid.consulta.calificacionesBID.periodoInfo"));
        columsTotal.add(hmColumnas.get(TipoIndicadorBID.TOTAL_ESTUDIANTES.getId()));
        columsTotal.add(hmColumnas.get(TipoIndicadorBID.NUM_ESTABLECIMIENTOS.getId()));
        columsTotal.add(hmColumnas.get(TipoIndicadorBID.NUM_PREDIOS.getId()));
        columsTotal.add(hmColumnas.get(TipoIndicadorBID.PROPORCION_PREDIOS_PRESENTAN_RIESGOS_NATURALES.getId()));
        columsTotal.add(hmColumnas.get(TipoIndicadorBID.POBLACION_AFECTADA_RIESGO_NATURAL.getId()));
        columsTotal.add(hmColumnas.get(TipoIndicadorBID.PROPORCION_PREDIOS_PRESENTAN_RIESGOS_ANTROPICOS.getId()));
        columsTotal.add(hmColumnas.get(TipoIndicadorBID.POBLACION_AFECTADA_RIESGO_ANTROPICO.getId()));
        columsTotal.add(hmColumnas.get(TipoIndicadorBID.PROPORCION_PREDIOS_SIN_ACCESO_BUEN_ESTADO.getId()));            
        columsTotal.add(hmColumnas.get(TipoIndicadorBID.PROPORCION_PREDIOS_RUTA_EVALUACION_CRITICA.getId()));
        columsTotal.add(hmColumnas.get(TipoIndicadorBID.PROPORCION_PREDIOS_CONDICION_ESTRUCTURA_CRITICA.getId()));
        columsTotal.add(hmColumnas.get(TipoIndicadorBID.PROPORCION_PREDIOS_CON_PROBLEMAS_LEGALIZACION.getId()));
        columsTotal.add(hmColumnas.get(TipoIndicadorBID.NUM_ESTUDIANTES_PREDIOS_PROBLEMAS_LEGALIZACION.getId()));
        columsTotal.add(hmColumnas.get(TipoIndicadorBID.PROPORCION_PREDIOS_CONDICION_MATERIALIDAD_CRITICA.getId()));
        columsTotal.add(hmColumnas.get(TipoIndicadorBID.PROPORCION_PREDIOS_DISPONIBILIDAD_AGUA.getId()));
        columsTotal.add(hmColumnas.get(TipoIndicadorBID.PROPORCION_PREDIOS_AGUA_CONDICION_INSUFICIENTE.getId()));
        columsTotal.add(hmColumnas.get(TipoIndicadorBID.PROPORCION_PREDIOS_DISPONIBILIDAD_ENERGIA_ELECTRICA.getId()));
        columsTotal.add(hmColumnas.get(TipoIndicadorBID.PROPORCION_PREDIOS_ENERGIA_ELECTRICA_CONDICION_INSUFICIENTE.getId()));
        columsTotal.add(hmColumnas.get(TipoIndicadorBID.ESTANDAR_AULA.getId()));
        columsTotal.add(hmColumnas.get(TipoIndicadorBID.M2_ALUMNO_AULA.getId()));
        columsTotal.add(hmColumnas.get(TipoIndicadorBID.ESTANDAR_SERVICIOS_SANITARIOS.getId()));
        columsTotal.add(hmColumnas.get(TipoIndicadorBID.M2_ALUMNO_SERVICIOS_SANITARIOS.getId()));
        columsTotal.add(hmColumnas.get(TipoIndicadorBID.UTILIZACION_ESTADAR_TOTAL.getId()));
        columsTotal.add(hmColumnas.get(TipoIndicadorBID.CUPOS_TOTAL.getId()));
        columsTotal.add(hmColumnas.get(TipoIndicadorBID.PROPORCION_PREDIOS_SATURADOS.getId()));
        columsTotal.add(hmColumnas.get(TipoIndicadorBID.PROPORCION_PREDIOS_SUBUTILIZADOS.getId()));
        columsTotal.add(hmColumnas.get(TipoIndicadorBID.AREA_PREDIO.getId()));
        columsTotal.add(hmColumnas.get(TipoIndicadorBID.AREA_CONSTRUCCION_TOTAL.getId()));
        columsTotal.add(hmColumnas.get(TipoIndicadorBID.CUMPLIMIENTO_NORMA_CONSTRUC_TOTAL.getId()));
        columsTotal.add(hmColumnas.get(TipoIndicadorBID.ESTANDAR_INDICE_OCUPACION.getId()));
        columsTotal.add(hmColumnas.get(TipoIndicadorBID.INDICE_OCUPACION.getId()));
        columsTotal.add(hmColumnas.get(TipoIndicadorBID.ESTANDAR_CONSTRUCCION.getId()));
        columsTotal.add(hmColumnas.get(TipoIndicadorBID.INDICE_CONSTRUCCION.getId()));
    }
    
    public class Registro {

        private String cod;
        private int id;
        private HashMap<String,String> ind_nombre;

        public String getCod() {
            return cod;
        }

        public void setCod(String cod) {
            this.cod = cod;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public HashMap<String, String> getInd_nombre() {
            return ind_nombre;
        }

        public void setInd_nombre(HashMap<String, String> ind_nombre) {
            this.ind_nombre = ind_nombre;
        }
                
    }

    public List<Registro> getLista() {
        return lista;
    }

    public void setLista(List<Registro> lista) {
        this.lista = lista;
    }

    public List<String> getColums() {
        return colums;
    }

    public void setColums(List<String> colums) {
        this.colums = colums;
        if(colums.contains(hmColumnas.get(TipoIndicadorBID.PROPORCION_PREDIOS_PRESENTAN_RIESGOS_NATURALES.getId()))
                || colums.contains(hmColumnas.get(TipoIndicadorBID.POBLACION_AFECTADA_RIESGO_NATURAL.getId()))
                || colums.contains(hmColumnas.get(TipoIndicadorBID.PROPORCION_PREDIOS_PRESENTAN_RIESGOS_ANTROPICOS.getId()))
                || colums.contains(hmColumnas.get(TipoIndicadorBID.POBLACION_AFECTADA_RIESGO_ANTROPICO.getId()))){
            this.setCheckRiesgos(true);
        }else{
            this.setCheckRiesgos(false);
        }
        if(colums.contains(hmColumnas.get(TipoIndicadorBID.PROPORCION_PREDIOS_SIN_ACCESO_BUEN_ESTADO.getId())) ){
            this.setCheckAccesibilidadExt(true);
        }else{
            this.setCheckAccesibilidadExt(false);
        }
        if(colums.contains(hmColumnas.get(TipoIndicadorBID.PROPORCION_PREDIOS_RUTA_EVALUACION_CRITICA.getId()))
                || colums.contains(hmColumnas.get(TipoIndicadorBID.PROPORCION_PREDIOS_CONDICION_ESTRUCTURA_CRITICA.getId()))){
            this.setCheckSeguridad(true);
        }else{
            this.setCheckSeguridad(false);
        }
        if(colums.contains(hmColumnas.get(TipoIndicadorBID.PROPORCION_PREDIOS_CON_PROBLEMAS_LEGALIZACION.getId()))
                || colums.contains(hmColumnas.get(TipoIndicadorBID.NUM_ESTUDIANTES_PREDIOS_PROBLEMAS_LEGALIZACION.getId()))){
            this.setCheckPropiedad(true);
        }else{
            this.setCheckPropiedad(false);
        }
        if(colums.contains(hmColumnas.get(TipoIndicadorBID.PROPORCION_PREDIOS_CONDICION_MATERIALIDAD_CRITICA.getId()))){
            this.setCheckEstadoEdifEsp(true);
        }else{
            this.setCheckEstadoEdifEsp(false);
        }
        if(colums.contains(hmColumnas.get(TipoIndicadorBID.PROPORCION_PREDIOS_DISPONIBILIDAD_AGUA.getId()))
                || colums.contains(hmColumnas.get(TipoIndicadorBID.PROPORCION_PREDIOS_AGUA_CONDICION_INSUFICIENTE.getId()))
                || colums.contains(hmColumnas.get(TipoIndicadorBID.PROPORCION_PREDIOS_DISPONIBILIDAD_ENERGIA_ELECTRICA.getId()))
                || colums.contains(hmColumnas.get(TipoIndicadorBID.PROPORCION_PREDIOS_ENERGIA_ELECTRICA_CONDICION_INSUFICIENTE.getId()))){
            this.setCheckServicios(true);
        }else{
            this.setCheckServicios(false);
        }
        if(colums.contains(hmColumnas.get(TipoIndicadorBID.ESTANDAR_AULA.getId()))
                || colums.contains(hmColumnas.get(TipoIndicadorBID.M2_ALUMNO_AULA.getId()))
                || colums.contains(hmColumnas.get(TipoIndicadorBID.ESTANDAR_SERVICIOS_SANITARIOS.getId()))
                || colums.contains(hmColumnas.get(TipoIndicadorBID.M2_ALUMNO_SERVICIOS_SANITARIOS.getId()))){
            this.setCheckAmbientes(true);
        }else{
            this.setCheckAmbientes(false);
        }
        if(colums.contains(hmColumnas.get(TipoIndicadorBID.UTILIZACION_ESTADAR_TOTAL.getId()))
                || colums.contains(hmColumnas.get(TipoIndicadorBID.CUPOS_TOTAL.getId()))
                || colums.contains(hmColumnas.get(TipoIndicadorBID.PROPORCION_PREDIOS_SATURADOS.getId()))
                || colums.contains(hmColumnas.get(TipoIndicadorBID.PROPORCION_PREDIOS_SUBUTILIZADOS.getId()))
                || colums.contains(hmColumnas.get(TipoIndicadorBID.AREA_PREDIO.getId()))
                || colums.contains(hmColumnas.get(TipoIndicadorBID.AREA_CONSTRUCCION_TOTAL.getId()))
                || colums.contains(hmColumnas.get(TipoIndicadorBID.CUMPLIMIENTO_NORMA_CONSTRUC_TOTAL.getId()))
                || colums.contains(hmColumnas.get(TipoIndicadorBID.ESTANDAR_INDICE_OCUPACION.getId()))
                || colums.contains(hmColumnas.get(TipoIndicadorBID.INDICE_OCUPACION.getId()))
                || colums.contains(hmColumnas.get(TipoIndicadorBID.ESTANDAR_CONSTRUCCION.getId()))
                || colums.contains(hmColumnas.get(TipoIndicadorBID.INDICE_CONSTRUCCION.getId()))){
            this.setCheckOferta(true);
        }else{
            this.setCheckOferta(false);
        }
    }

    public List<String> getColumsTotal() {
        return columsTotal;
    }

    public void setColumsTotal(List<String> columsTotal) {
        this.columsTotal = columsTotal;
    }

    public String getMensajeTablaVacia() {
        return mensajeTablaVacia;
    }

    public void setMensajeTablaVacia(String mensajeTablaVacia) {
        this.mensajeTablaVacia = mensajeTablaVacia;
    }

    public PeriodosBID getPeriodoBID() {
        return periodoBID;
    }

    public void setPeriodoBID(PeriodosBID periodoBID) {
        this.periodoBID = periodoBID;
    }

    public List<PeriodosBID> getListaPeriodos() {
        return listaPeriodos;
    }

    public void setListaPeriodos(List<PeriodosBID> listaPeriodos) {
        this.listaPeriodos = listaPeriodos;
    }

    public boolean isCheckRiesgos() {
        return checkRiesgos;
    }

    public void setCheckRiesgos(boolean checkRiesgos) {
        this.checkRiesgos = checkRiesgos;
    }

    public boolean isCheckAccesibilidadExt() {
        return checkAccesibilidadExt;
    }

    public void setCheckAccesibilidadExt(boolean checkAccesibilidadExt) {
        this.checkAccesibilidadExt = checkAccesibilidadExt;
    }

    public boolean isCheckSeguridad() {
        return checkSeguridad;
    }

    public void setCheckSeguridad(boolean checkSeguridad) {
        this.checkSeguridad = checkSeguridad;
    }

    public boolean isCheckPropiedad() {
        return checkPropiedad;
    }

    public void setCheckPropiedad(boolean checkPropiedad) {
        this.checkPropiedad = checkPropiedad;
    }

    public boolean isCheckEstadoEdifEsp() {
        return checkEstadoEdifEsp;
    }

    public void setCheckEstadoEdifEsp(boolean checkEstadoEdifEsp) {
        this.checkEstadoEdifEsp = checkEstadoEdifEsp;
    }

    public boolean isCheckServicios() {
        return checkServicios;
    }

    public void setCheckServicios(boolean checkServicios) {
        this.checkServicios = checkServicios;
    }

    public boolean isCheckAmbientes() {
        return checkAmbientes;
    }

    public void setCheckAmbientes(boolean checkAmbientes) {
        this.checkAmbientes = checkAmbientes;
    }

    public boolean isCheckOferta() {
        return checkOferta;
    }

    public void setCheckOferta(boolean checkOferta) {
        this.checkOferta = checkOferta;
    }

}
