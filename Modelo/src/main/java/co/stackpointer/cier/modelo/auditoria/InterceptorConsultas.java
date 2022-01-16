/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.auditoria;

import java.util.Date;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

public class InterceptorConsultas {
   
    @AroundInvoke //Define un método interceptor que se interpondrá en los métodos del EJB
    public Object metodoInterceptado(InvocationContext invocationContext) throws Exception {        
        Object[] parametersOfMethod = invocationContext.getParameters();
        String usuario = (String) parametersOfMethod[0];
        String url = (String) parametersOfMethod[1];
        boolean isConsulta = url.contains("/consultas/") | url.contains("/parametros/");
       
        Object o = invocationContext.proceed(); //hacemos ejecutar el método

        if (isConsulta && o.equals(Boolean.TRUE)) {
            RegistrarAuditoria registrar = new RegistrarAuditoria();
            registrar.setFecha(new Date());
            registrar.setNombreMetodo(url);
            registrar.setUsuario(usuario);
            registrar.start();
        }

        return o; //que continue la secuencia
    }
}