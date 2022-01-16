/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.vistacontrolador.bean;

import java.io.IOException;
import java.io.PrintWriter;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Usuario
 */
@WebServlet("/checkGmap")
public class CheckGmap extends HttpServlet {
   
    private static final long serialVersionUID = 1L;    
       
    public CheckGmap() {
        super();        
    }
 
    //Setea la variable internetConnectionGmap de LoginBean
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LoginBean bean = (LoginBean) request.getSession().getAttribute("login");
        boolean data = Boolean.parseBoolean(request.getParameter("data"));
        bean.setInternetConnectionGmap(data); 
    }
    
}
