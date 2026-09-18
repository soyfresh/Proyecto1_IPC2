/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Filter.java to edit this template
 */
package FiltrosJSP;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author dar333n
 */
@WebFilter(filterName = "FiltroDeInicioSesion", urlPatterns = {"/misJsp/AdminSistemaJSP/*", "/misJsp/AdminSucursalJSP/*",
"/misJsp/ChoferJSP/*", "/misJsp/ClienteJSP/*", "/misJsp/GeneralesJSP/*", "/misViajes", "/servicios", "/SolicitarViajePrivado"})

public class FiltroDeInicioSesion implements Filter {
    
  
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest= (HttpServletRequest) request;
        HttpServletResponse httpResponse= (HttpServletResponse) response;
        HttpSession session= httpRequest.getSession(false);
        
        if(session==null || session.getAttribute("usuarioLogueado")==null){
            httpResponse.sendRedirect(httpRequest.getContextPath()+"/index.jsp");
            return;
        }
        
        chain.doFilter(request, response);
    }
}
