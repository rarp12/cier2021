/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.filter;

import co.stackpointer.cier.modelo.fachada.AccesoFacadeLocal;
import co.stackpointer.cier.modelo.utilidad.UtilOut;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class URLfilter implements Filter {

    @EJB
    AccesoFacadeLocal fachada;

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        try {
            HttpServletRequest req = (HttpServletRequest) request;
            String user = req.getUserPrincipal().getName();
            user = user.split("%")[1];
            String urlNoContextPath = req.getServletPath();
            boolean sw = fachada.permitirAccesoAurl(user, urlNoContextPath);
     
            if (sw) {
                chain.doFilter(request, response);
            } else {
                ((HttpServletResponse) response)
                        .sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } catch (Exception e) {
            UtilOut.println(e);
            ((HttpServletResponse) response).sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Error en proceso de autorizacion");
        }

    }

    public void init(FilterConfig filterConfig) {
    }
}
