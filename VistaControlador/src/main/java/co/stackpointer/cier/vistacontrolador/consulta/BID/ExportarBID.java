/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.consulta.BID;

import co.stackpointer.cier.modelo.entidad.indicador.EstandarHis;
import co.stackpointer.cier.modelo.tipo.ParamIndicador;
import co.stackpointer.cier.modelo.tipo.ParamNivelConsul;
import co.stackpointer.cier.modelo.tipo.SiNo;
import co.stackpointer.cier.modelo.tipo.TipoIndicadorBID;
import co.stackpointer.cier.modelo.tipo.UnidadFuncionalAmbiente;
import co.stackpointer.cier.modelo.utilidad.UtilExportar;
import co.stackpointer.cier.modelo.utilidad.UtilFecha;
import co.stackpointer.cier.modelo.utilidad.UtilOut;
import co.stackpointer.cier.vista.Utilidades;
import static co.stackpointer.cier.vista.Utilidades.mostrarMensajeError;
import co.stackpointer.cier.vistacontrolador.consulta.informeGeneral.InformeGeneralBean;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author nruiz
 */
@ManagedBean(name = "exportarBID")
@ViewScoped
public class ExportarBID extends InformeGeneralBean{
    
    
    public StreamedContent exportarCalificaVariables(){
        InputStream stream=null;
        try {
            
            Map<String, Object> parametros=new HashMap<String, Object>();
    
            
            List<Object[]> resultados = facCalificacion.obtenerVariablesPais();
            
            int col=3;
            for(Object[] registro:resultados){
                if(col==3){
                    parametros.put("col1","ID PAÍS");
                    parametros.put("valCol1",((BigDecimal)registro[0]).setScale(0, RoundingMode.HALF_UP));
                    parametros.put("col2","PERIODO");
                    parametros.put("valCol2", UtilFecha.obtenerPeriodoActual());
                }
                if(registro[1] != null){
                    parametros.put("col"+col,(BigDecimal)registro[1]);
                }
                BigDecimal variable = new BigDecimal(0);
                if(registro[2] != null){
                    variable= (BigDecimal)registro[2];
                }
                parametros.put("valCol"+col,(variable).setScale(2, RoundingMode.HALF_UP));
                
                col++;
                
            }
            
            //INDICADORES
            List<String> indicadoresBusq=new ArrayList<String>();
            indicadoresBusq.add(ParamIndicador.IND_TOTAL_ESTUDIANTES.name());
            indicadoresBusq.add(ParamIndicador.IND_TOTAL_ESTABLECIMIENTOS.name());
            indicadoresBusq.add(ParamIndicador.IND_TOTAL_PREDIOS.name());
            indicadoresBusq.add(ParamIndicador.IND_RIESGO_NATURAL_NUMERO_PREDIOS_PRESENTAN_RIESGO.name());
            indicadoresBusq.add(ParamIndicador.IND_RIESGO_NATURAL_POBLACION_AFECTADA.name());
            indicadoresBusq.add(ParamIndicador.IND_RIESGO_ANTROPICO_NUMERO_PREDIOS_PRESENTAN_RIESGO.name());
            indicadoresBusq.add(ParamIndicador.IND_RIESGO_ANTROPICO_POBLACION_AFECTADA.name());
            indicadoresBusq.add(ParamIndicador.IND_ACCESIBILIDAD_NUMERO_PREDIOS_CONDICION_INADECUADA.name());
            indicadoresBusq.add(ParamIndicador.IND_PROPIEDAD_TOTAL_PREDIO_PROBLEMA_LEGALIZACION.name());
            indicadoresBusq.add(ParamIndicador.IND_PROPIEDAD_TOTAL_ESTUDIANTES_PREDIO_PROBLEMA_LEGALIZACION.name());
            indicadoresBusq.add(ParamIndicador.IND_SERVICIO_NUM_PREDIOS_DISPONIBILIDAD_AGUA.name());
            indicadoresBusq.add(ParamIndicador.IND_SERVICIO_NUM_PREDIOS_ANALISIS_INSUFICIENCIA_AGUA.name());
            indicadoresBusq.add(ParamIndicador.IND_SERVICIO_NUM_PREDIOS_DISPONIBILIDAD_ENERGIA_ELECTRICA.name());
            indicadoresBusq.add(ParamIndicador.IND_SERVICIO_NUM_PREDIOS_ANALISIS_INSUFICIENCIA_ENERGIA_ELECTRICA.name());
            indicadoresBusq.add(ParamIndicador.IND_REDISTRUBUCION_UTILIZACION_ESTADAR_TOTAL.name());
            indicadoresBusq.add(ParamIndicador.IND_REDISTRUBUCION_CUPOS_TOTAL.name());
            indicadoresBusq.add(ParamIndicador.IND_REDISTRUBUCION_PREDIOS_SATURADOS.name());
            indicadoresBusq.add(ParamIndicador.IND_REDISTRUBUCION_PREDIOS_SUBUTILIZADOS.name());
            indicadoresBusq.add(ParamIndicador.IND_AREA_REAL_PREDIO.name());
            indicadoresBusq.add(ParamIndicador.IND_TOTAL_AREA_CONSTRUCCION_PREDIO.name());
            indicadoresBusq.add(ParamIndicador.IND_AMPLIACION_AREA_CONSTRUCCION_PRIMER_PISO.name());
            indicadoresBusq.add(ParamIndicador.IND_AMPLIACION_IDONEIDAD_CONSTRUCCION.name());
            indicadoresBusq.add(ParamIndicador.IND_AMPLIACION_ESTANDAR_CONSTRUCCION.name());
            
            List<Object[]> indicadores=facParam.consultarIndicadores(indicadoresBusq, ParamNivelConsul.CERO.getNivel(), null);
            if(!indicadores.isEmpty()){
                int i=0;
                parametros.put("col"+ TipoIndicadorBID.TOTAL_ESTUDIANTES.name(),TipoIndicadorBID.TOTAL_ESTUDIANTES.name());
                parametros.put(TipoIndicadorBID.TOTAL_ESTUDIANTES.name(), indicadores.get(0)[i]!=null?new BigDecimal((String)indicadores.get(0)[i]).setScale(2, RoundingMode.HALF_UP):BigDecimal.ZERO);
                i++;
                parametros.put("col"+TipoIndicadorBID.NUM_ESTABLECIMIENTOS.name(),TipoIndicadorBID.NUM_ESTABLECIMIENTOS.name());
                parametros.put(TipoIndicadorBID.NUM_ESTABLECIMIENTOS.name(), indicadores.get(0)[i]!=null?new BigDecimal((String)indicadores.get(0)[i]).setScale(2, RoundingMode.HALF_UP):BigDecimal.ZERO);
                i++;
                parametros.put("col"+TipoIndicadorBID.NUM_PREDIOS.name(),TipoIndicadorBID.NUM_PREDIOS.name());
                parametros.put(TipoIndicadorBID.NUM_PREDIOS.name(), indicadores.get(0)[i]!=null?new BigDecimal((String)indicadores.get(0)[i]).setScale(2, RoundingMode.HALF_UP):BigDecimal.ZERO);
                i++;
                parametros.put("col"+TipoIndicadorBID.PROPORCION_PREDIOS_PRESENTAN_RIESGOS_NATURALES.name(),TipoIndicadorBID.PROPORCION_PREDIOS_PRESENTAN_RIESGOS_NATURALES.name());
                parametros.put(TipoIndicadorBID.PROPORCION_PREDIOS_PRESENTAN_RIESGOS_NATURALES.name(), indicadores.get(0)[i]!=null?
                        new BigDecimal(new Double((String)indicadores.get(0)[i])
                        *(new Double(100))
                        /((BigDecimal)parametros.get("NUM_PREDIOS")).doubleValue()).setScale(2, RoundingMode.HALF_UP)
                        :BigDecimal.ZERO);
                i++;
                parametros.put("col"+TipoIndicadorBID.POBLACION_AFECTADA_RIESGO_NATURAL.name(),TipoIndicadorBID.POBLACION_AFECTADA_RIESGO_NATURAL.name());
                parametros.put(TipoIndicadorBID.POBLACION_AFECTADA_RIESGO_NATURAL.name(), indicadores.get(0)[i]!=null?new BigDecimal((String)indicadores.get(0)[i]).setScale(2, RoundingMode.HALF_UP):BigDecimal.ZERO);
                i++;
                parametros.put("col"+TipoIndicadorBID.PROPORCION_PREDIOS_PRESENTAN_RIESGOS_ANTROPICOS.name(),TipoIndicadorBID.PROPORCION_PREDIOS_PRESENTAN_RIESGOS_ANTROPICOS.name());
                parametros.put(TipoIndicadorBID.PROPORCION_PREDIOS_PRESENTAN_RIESGOS_ANTROPICOS.name(), indicadores.get(0)[i]!=null && !((BigDecimal)parametros.get("NUM_PREDIOS")).equals(BigDecimal.ZERO)?
                        new BigDecimal(new Double((String)indicadores.get(0)[i])
                        *(new Double(100))
                        /((BigDecimal)parametros.get("NUM_PREDIOS")).doubleValue()).setScale(2, RoundingMode.HALF_UP)
                        :BigDecimal.ZERO);
                i++;
                parametros.put("col"+TipoIndicadorBID.POBLACION_AFECTADA_RIESGO_ANTROPICO.name(),TipoIndicadorBID.POBLACION_AFECTADA_RIESGO_ANTROPICO.name());
                parametros.put(TipoIndicadorBID.POBLACION_AFECTADA_RIESGO_ANTROPICO.name(), indicadores.get(0)[i]!=null?new BigDecimal((String)indicadores.get(0)[i]).setScale(2, RoundingMode.HALF_UP):BigDecimal.ZERO);
                i++;
                parametros.put("col"+TipoIndicadorBID.PROPORCION_PREDIOS_SIN_ACCESO_BUEN_ESTADO.name(),TipoIndicadorBID.PROPORCION_PREDIOS_SIN_ACCESO_BUEN_ESTADO.name());
                parametros.put(TipoIndicadorBID.PROPORCION_PREDIOS_SIN_ACCESO_BUEN_ESTADO.name(), indicadores.get(0)[i]!=null?
                        new BigDecimal(new Double((String)indicadores.get(0)[i])
                        *(new Double(100))
                        /((BigDecimal)parametros.get("NUM_PREDIOS")).doubleValue()).setScale(2, RoundingMode.HALF_UP)
                        :BigDecimal.ZERO);
                i++;
                parametros.put("col"+TipoIndicadorBID.PROPORCION_PREDIOS_CON_PROBLEMAS_LEGALIZACION.name(),TipoIndicadorBID.PROPORCION_PREDIOS_CON_PROBLEMAS_LEGALIZACION.name());
                parametros.put(TipoIndicadorBID.PROPORCION_PREDIOS_CON_PROBLEMAS_LEGALIZACION.name(), indicadores.get(0)[i]!=null?
                        new BigDecimal(new Double((String)indicadores.get(0)[i])
                        *(new Double(100))
                        /((BigDecimal)parametros.get("NUM_PREDIOS")).doubleValue()).setScale(2, RoundingMode.HALF_UP)
                        :BigDecimal.ZERO);
                i++;
                parametros.put("col"+TipoIndicadorBID.NUM_ESTUDIANTES_PREDIOS_PROBLEMAS_LEGALIZACION.name(),TipoIndicadorBID.NUM_ESTUDIANTES_PREDIOS_PROBLEMAS_LEGALIZACION.name());
                parametros.put(TipoIndicadorBID.NUM_ESTUDIANTES_PREDIOS_PROBLEMAS_LEGALIZACION.name(), indicadores.get(0)[i]!=null?new BigDecimal((String)indicadores.get(0)[i]).setScale(2, RoundingMode.HALF_UP):BigDecimal.ZERO);
                i++;
                parametros.put("col"+TipoIndicadorBID.PROPORCION_PREDIOS_DISPONIBILIDAD_AGUA.name(),TipoIndicadorBID.PROPORCION_PREDIOS_DISPONIBILIDAD_AGUA.name());
                parametros.put(TipoIndicadorBID.PROPORCION_PREDIOS_DISPONIBILIDAD_AGUA.name(), indicadores.get(0)[i]!=null?
                        new BigDecimal(new Double((String)indicadores.get(0)[i])
                        *(new Double(100))
                        /((BigDecimal)parametros.get("NUM_PREDIOS")).doubleValue()).setScale(2, RoundingMode.HALF_UP)
                        :BigDecimal.ZERO);
                i++;
                parametros.put("col"+TipoIndicadorBID.PROPORCION_PREDIOS_AGUA_CONDICION_INSUFICIENTE.name(),TipoIndicadorBID.PROPORCION_PREDIOS_AGUA_CONDICION_INSUFICIENTE.name());
                parametros.put(TipoIndicadorBID.PROPORCION_PREDIOS_AGUA_CONDICION_INSUFICIENTE.name(), indicadores.get(0)[i]!=null?
                        new BigDecimal(new Double((String)indicadores.get(0)[i])
                        *(new Double(100))
                        /((BigDecimal)parametros.get("NUM_PREDIOS")).doubleValue()).setScale(2, RoundingMode.HALF_UP)
                        :BigDecimal.ZERO);
                i++;
                parametros.put("col"+TipoIndicadorBID.PROPORCION_PREDIOS_DISPONIBILIDAD_ENERGIA_ELECTRICA.name(),TipoIndicadorBID.PROPORCION_PREDIOS_DISPONIBILIDAD_ENERGIA_ELECTRICA.name());
                parametros.put(TipoIndicadorBID.PROPORCION_PREDIOS_DISPONIBILIDAD_ENERGIA_ELECTRICA.name(), indicadores.get(0)[i]!=null?
                        new BigDecimal(new Double((String)indicadores.get(0)[i])
                        *(new Double(100))
                        /((BigDecimal)parametros.get("NUM_PREDIOS")).doubleValue()).setScale(2, RoundingMode.HALF_UP)
                        :BigDecimal.ZERO);
                i++;
                parametros.put("col"+TipoIndicadorBID.PROPORCION_PREDIOS_ENERGIA_ELECTRICA_CONDICION_INSUFICIENTE.name(),TipoIndicadorBID.PROPORCION_PREDIOS_ENERGIA_ELECTRICA_CONDICION_INSUFICIENTE.name());
                parametros.put(TipoIndicadorBID.PROPORCION_PREDIOS_ENERGIA_ELECTRICA_CONDICION_INSUFICIENTE.name(), indicadores.get(0)[i]!=null?
                        new BigDecimal(new Double((String)indicadores.get(0)[i])
                        *(new Double(100))
                        /((BigDecimal)parametros.get("NUM_PREDIOS")).doubleValue()).setScale(2, RoundingMode.HALF_UP)
                        :BigDecimal.ZERO);
                i++;
                parametros.put("col"+TipoIndicadorBID.UTILIZACION_ESTADAR_TOTAL.name(),TipoIndicadorBID.UTILIZACION_ESTADAR_TOTAL.name());
                parametros.put(TipoIndicadorBID.UTILIZACION_ESTADAR_TOTAL.name(), indicadores.get(0)[i]!=null?new BigDecimal((String)indicadores.get(0)[i]).setScale(2, RoundingMode.HALF_UP):BigDecimal.ZERO);
                i++;
                parametros.put("col"+TipoIndicadorBID.CUPOS_TOTAL.name(),TipoIndicadorBID.CUPOS_TOTAL.name());
                parametros.put(TipoIndicadorBID.CUPOS_TOTAL.name(), indicadores.get(0)[i]!=null?new BigDecimal((String)indicadores.get(0)[i]).setScale(2, RoundingMode.HALF_UP):BigDecimal.ZERO);
                i++;
                parametros.put("col"+TipoIndicadorBID.PROPORCION_PREDIOS_SATURADOS.name(),TipoIndicadorBID.PROPORCION_PREDIOS_SATURADOS.name());
                parametros.put(TipoIndicadorBID.PROPORCION_PREDIOS_SATURADOS.name(), indicadores.get(0)[i]!=null?
                        new BigDecimal(new Double((String)indicadores.get(0)[i])
                        *(new Double(100))
                        /((BigDecimal)parametros.get("NUM_PREDIOS")).doubleValue()).setScale(2, RoundingMode.HALF_UP)
                        :BigDecimal.ZERO);
                i++;
                parametros.put("col"+TipoIndicadorBID.PROPORCION_PREDIOS_SUBUTILIZADOS.name(),TipoIndicadorBID.PROPORCION_PREDIOS_SUBUTILIZADOS.name());
                parametros.put(TipoIndicadorBID.PROPORCION_PREDIOS_SUBUTILIZADOS.name(), indicadores.get(0)[i]!=null?
                        new BigDecimal(new Double((String)indicadores.get(0)[i])
                        *(new Double(100))
                        /((BigDecimal)parametros.get("NUM_PREDIOS")).doubleValue()).setScale(2, RoundingMode.HALF_UP)
                        :BigDecimal.ZERO);
                i++;
                parametros.put("col"+TipoIndicadorBID.AREA_PREDIO.name(),TipoIndicadorBID.AREA_PREDIO.name());
                parametros.put(TipoIndicadorBID.AREA_PREDIO.name(), indicadores.get(0)[i]!=null?new BigDecimal((String)indicadores.get(0)[i]).setScale(2, RoundingMode.HALF_UP):BigDecimal.ZERO);
                i++;
                parametros.put("col"+TipoIndicadorBID.AREA_CONSTRUCCION_TOTAL.name(),TipoIndicadorBID.AREA_CONSTRUCCION_TOTAL.name());
                parametros.put(TipoIndicadorBID.AREA_CONSTRUCCION_TOTAL.name(), indicadores.get(0)[i]!=null?new BigDecimal((String)indicadores.get(0)[i]).setScale(2, RoundingMode.HALF_UP):BigDecimal.ZERO);
                i++;
                parametros.put("col"+TipoIndicadorBID.INDICE_OCUPACION.name(),TipoIndicadorBID.INDICE_OCUPACION.name());
                parametros.put(TipoIndicadorBID.INDICE_OCUPACION.name(), indicadores.get(0)[i]!=null?
                        new BigDecimal(new Double((String)indicadores.get(0)[i])
                        *(new Double(100))
                        /((BigDecimal)parametros.get("AREA_PREDIO")).doubleValue()).setScale(2, RoundingMode.HALF_UP)
                        :BigDecimal.ZERO);
                i++;
                parametros.put("col"+TipoIndicadorBID.CUMPLIMIENTO_NORMA_CONSTRUC_TOTAL.name(),TipoIndicadorBID.CUMPLIMIENTO_NORMA_CONSTRUC_TOTAL.name());
                parametros.put(TipoIndicadorBID.CUMPLIMIENTO_NORMA_CONSTRUC_TOTAL.name(), indicadores.get(0)[i]!=null && new Double((String)indicadores.get(0)[i])>new Double(100)?"Cumple":"No cumple");
                i++;
                parametros.put("col"+TipoIndicadorBID.ESTANDAR_CONSTRUCCION.name(),TipoIndicadorBID.ESTANDAR_CONSTRUCCION.name());
                parametros.put(TipoIndicadorBID.ESTANDAR_CONSTRUCCION.name(), indicadores.get(0)[i]!=null?new BigDecimal((String)indicadores.get(0)[i]).setScale(2, RoundingMode.HALF_UP):BigDecimal.ZERO);
                i++;
                
                parametros.put("col"+TipoIndicadorBID.INDICE_CONSTRUCCION.name(),TipoIndicadorBID.INDICE_CONSTRUCCION.name());
                parametros.put(TipoIndicadorBID.INDICE_CONSTRUCCION.name(), ((BigDecimal)parametros.get("AREA_PREDIO")).equals(BigDecimal.ZERO)?BigDecimal.ZERO:new BigDecimal(((BigDecimal)parametros.get("AREA_CONSTRUCCION_TOTAL")).doubleValue()
                        *(new Double(100))
                        /((BigDecimal)parametros.get("AREA_PREDIO")).doubleValue()).setScale(2, RoundingMode.HALF_UP));
                
            }

            
            //Indicadores de unidades funcionales
            List<String[]> indBusqUnidadesFun=new ArrayList<String[]>();
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_M2_ALUMNO.name(), UnidadFuncionalAmbiente.AULA.name()});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_AMBIENTE_M2_ALUMNO.name(), UnidadFuncionalAmbiente.SERVICIOS_SANITARIOS.name()});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_SEGURIDAD_NUM_PREDIOS_SEGUN_ANALISIS_RUTA_EVALUACION.name(), SiNo.Si.name()});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_SEGURIDAD_NUM_PREDIOS_SEGUN_ESTADO_ESTRUCTURA.name(), SiNo.Si.name()});
            indBusqUnidadesFun.add(new String[]{ParamIndicador.IND_EDIFICIO_NUM_PREDIOS_CONDICION_MATERIALIDAD.name(), "CRITICO"});
            indicadores=facParam.consultarIndicadoresUnidadesFuncionales(indBusqUnidadesFun, ParamNivelConsul.CERO.getNivel(), null);

            if(!indicadores.isEmpty()){
                int i=0;
                parametros.put("col"+TipoIndicadorBID.M2_ALUMNO_AULA.name(),TipoIndicadorBID.M2_ALUMNO_AULA.name());
                parametros.put(TipoIndicadorBID.M2_ALUMNO_AULA.name(), indicadores.get(0)[i]!=null?new BigDecimal((String)indicadores.get(0)[i]).setScale(2, RoundingMode.HALF_UP):BigDecimal.ZERO);
                i++;
                parametros.put("col"+TipoIndicadorBID.M2_ALUMNO_SERVICIOS_SANITARIOS.name(),TipoIndicadorBID.M2_ALUMNO_SERVICIOS_SANITARIOS.name());
                parametros.put(TipoIndicadorBID.M2_ALUMNO_SERVICIOS_SANITARIOS.name(), indicadores.get(0)[i]!=null?new BigDecimal((String)indicadores.get(0)[i]).setScale(2, RoundingMode.HALF_UP):BigDecimal.ZERO);
                i++;
                parametros.put("col"+TipoIndicadorBID.PROPORCION_PREDIOS_RUTA_EVALUACION_CRITICA.name(),TipoIndicadorBID.PROPORCION_PREDIOS_RUTA_EVALUACION_CRITICA.name());
                parametros.put(TipoIndicadorBID.PROPORCION_PREDIOS_RUTA_EVALUACION_CRITICA.name(), indicadores.get(0)[i]!=null?
                        new BigDecimal(new Double((String)indicadores.get(0)[i])
                        *(new Double(100))
                        /((BigDecimal)parametros.get("NUM_PREDIOS")).doubleValue()).setScale(2, RoundingMode.HALF_UP)
                        :BigDecimal.ZERO);
                i++;
                parametros.put("col"+TipoIndicadorBID.PROPORCION_PREDIOS_CONDICION_ESTRUCTURA_CRITICA.name(),TipoIndicadorBID.PROPORCION_PREDIOS_CONDICION_ESTRUCTURA_CRITICA.name());
                parametros.put(TipoIndicadorBID.PROPORCION_PREDIOS_CONDICION_ESTRUCTURA_CRITICA.name(), indicadores.get(0)[i]!=null?
                        new BigDecimal(new Double((String)indicadores.get(0)[i])
                        *(new Double(100))
                        /((BigDecimal)parametros.get("NUM_PREDIOS")).doubleValue()).setScale(2, RoundingMode.HALF_UP)
                        :BigDecimal.ZERO);
                i++;
                parametros.put("col"+TipoIndicadorBID.PROPORCION_PREDIOS_CONDICION_MATERIALIDAD_CRITICA.name(),TipoIndicadorBID.PROPORCION_PREDIOS_CONDICION_MATERIALIDAD_CRITICA.name());
                parametros.put(TipoIndicadorBID.PROPORCION_PREDIOS_CONDICION_MATERIALIDAD_CRITICA.name(), indicadores.get(0)[i]!=null?
                        new BigDecimal(new Double((String)indicadores.get(0)[i])
                        *(new Double(100))
                        /((BigDecimal)parametros.get("NUM_PREDIOS")).doubleValue()).setScale(2, RoundingMode.HALF_UP)
                        :BigDecimal.ZERO);
                i++;
            }
            
            //Estandars
            EstandarHis estandarHis = facParam.consultarEstandarPorCodigo("ESTANDAR_" + UnidadFuncionalAmbiente.AULA.name());
            parametros.put("col"+TipoIndicadorBID.ESTANDAR_AULA.name(),TipoIndicadorBID.ESTANDAR_AULA.name());
            parametros.put(TipoIndicadorBID.ESTANDAR_AULA.name(), new BigDecimal(estandarHis.getValor()).setScale(2, RoundingMode.HALF_UP));
            
            estandarHis = facParam.consultarEstandarPorCodigo("ESTANDAR_" + UnidadFuncionalAmbiente.SERVICIOS_SANITARIOS.name());
            parametros.put("col"+TipoIndicadorBID.ESTANDAR_SERVICIOS_SANITARIOS.name(),TipoIndicadorBID.ESTANDAR_SERVICIOS_SANITARIOS.name());
            parametros.put(TipoIndicadorBID.ESTANDAR_SERVICIOS_SANITARIOS.name(), new BigDecimal(estandarHis.getValor()).setScale(2, RoundingMode.HALF_UP));
            
            estandarHis = facParam.consultarEstandarPorCodigo("ESTANDAR_INDICE_OCUPACION");
            parametros.put("col"+TipoIndicadorBID.ESTANDAR_INDICE_OCUPACION.name(),TipoIndicadorBID.ESTANDAR_INDICE_OCUPACION.name());
            parametros.put(TipoIndicadorBID.ESTANDAR_INDICE_OCUPACION.name(), new BigDecimal(estandarHis.getValor()).setScale(2, RoundingMode.HALF_UP));
            
            Map<String, Object> paramsPdfDPATotales= super.obtenerParametrosPdfDPATotales(ParamNivelConsul.CERO.getNivel(),((BigDecimal)parametros.get("valCol1")).longValue(),"");
            for(String key:paramsPdfDPATotales.keySet()){
                parametros.put("col"+key,key);
            }
            parametros.putAll(paramsPdfDPATotales);
            
            
            List list=new ArrayList();
            list.add(new Object());
            stream=UtilExportar.getDocumentoXLSX("BID", "VariablesPais", parametros, list);
           
        } catch (Exception e) {
            UtilOut.println("ERROR en exportarCalificaVariables: "+e.getMessage());
            mostrarMensajeError(Utilidades.obtenerMensaje("aplicacion.general.errorAplicacion"), e.getMessage());
            e.printStackTrace();
            return null;
        }
        
        return new DefaultStreamedContent(stream,"application/x-download", "VariablesPais.xlsx");
    }
    
    
}
