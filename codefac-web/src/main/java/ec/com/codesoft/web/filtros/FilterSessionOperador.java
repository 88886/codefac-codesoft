/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.web.filtros;

import ec.com.codesoft.model.Perfil;
import ec.com.codesoft.model.Usuario;
import java.io.IOException;
import javax.faces.application.ResourceHandler;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author carlo
 */
//@WebFilter(urlPatterns = { "/operador/*"})
public class FilterSessionOperador implements Filter {

    private FilterConfig filterConfig;
    
    private static final String AJAX_REDIRECT_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
        + "<partial-response><redirect url=\"%s\"></redirect></partial-response>";


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
         this.filterConfig=filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req= (HttpServletRequest) request;
        HttpServletResponse res= (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        String loginURL = req.getContextPath() + "/login.xhtml";

        boolean loggedIn = (session != null) && (session.getAttribute("usuario") != null);
        boolean loginRequest = req.getRequestURI().equals(loginURL);
        boolean resourceRequest = req.getRequestURI().startsWith(req.getContextPath() + ResourceHandler.RESOURCE_IDENTIFIER + "/");
        boolean ajaxRequest = "partial/ajax".equals(req.getHeader("Faces-Request"));

        if (loggedIn || loginRequest || resourceRequest) {
            if (!resourceRequest) { // Prevent browser from caching restricted resources. See also http://stackoverflow.com/q/4194207/157882
                res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
                res.setHeader("Pragma", "no-cache"); // HTTP 1.0.
                res.setDateHeader("Expires", 0); // Proxies.
            }
            
            //verificar si el usuario pertenece a la seccion correspondiente
            Usuario usuario=(Usuario) session.getAttribute("usuario");
            Perfil perfil=(Perfil) session.getAttribute("perfil");
            if(perfil.getTipo().equals("operador") && usuario!=null)
            {
                chain.doFilter(req, res); // So, just continue request.
            }
            else
            {
                //cuando no tenga permiso para acceder
                if (ajaxRequest) {
                    res.setContentType("text/xml");
                    res.setCharacterEncoding("UTF-8");
                    res.getWriter().printf(AJAX_REDIRECT_XML, loginURL); // So, return special XML response instructing JSF ajax to send a redirect.
                } else {
                    res.sendRedirect(loginURL); // So, just perform standard synchronous redirect.
                }
            }           
        }
        else
        {
            if (ajaxRequest) {
                res.setContentType("text/xml");
                res.setCharacterEncoding("UTF-8");
                res.getWriter().printf(AJAX_REDIRECT_XML, loginURL); // So, return special XML response instructing JSF ajax to send a redirect.
            }
            else    
            {
                res.sendRedirect(loginURL); // So, just perform standard synchronous redirect.
            }
    }   }

    @Override
    public void destroy() {
        this.filterConfig=null;
    }
}
