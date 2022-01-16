/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.auditoria;

import java.lang.reflect.Method;
import java.util.Date;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
 
public class InterceptorAdministracion {            
    
    @AroundInvoke //Define un método interceptor que se interpondrá en los métodos del EJB
    public Object metodoInterceptado(InvocationContext invocationContext) throws Exception{        
        Method metodoInterceptado=invocationContext.getMethod();
        Object[] parametersOfMethod = invocationContext.getParameters();
        String usuario = (String) parametersOfMethod[0];
        // para mostrar el metodo que se va a ejecutar
                 
        Object o=invocationContext.proceed(); //hacemos ejecutar el método, o si hay otro interceptor, le damos la posta
        // la variable devuelta es el resultado del método, o NULL si es un método VOID
                 
        RegistrarAuditoria registrar = new RegistrarAuditoria();
        registrar.setFecha(new Date());
        registrar.setNombreMetodo(metodoInterceptado.getName());
        registrar.setUsuario(usuario);
        registrar.start();
         
        return o; //que continue la secuencia
    }    
   
}