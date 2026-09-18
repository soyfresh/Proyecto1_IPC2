/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets.General;

import ClasesDTO.Cuentas.CuentaDTO;
import ConexionDB.ConnectionDB;
import Sevicios.ServicioUsuarios;
import clasesAuxiliares.Exceptions.DatoInvalidoException;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dar333n
 */
@WebServlet(name = "recargarCartera", urlPatterns = {"/recargarCartera"})
public class recargarCartera extends HttpServlet {

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session= request.getSession(); 
        
        if (session==null || session.getAttribute("usuarioLogueado")== null) {
            response.sendRedirect("/login.jsp");
            return; 
        }
        request.getRequestDispatcher("/misJsp/GeneralesJSP/recargarCartera.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session= request.getSession(false);
        ConnectionDB adminCone=new  ConnectionDB();
        ServicioUsuarios servUs=new ServicioUsuarios(adminCone);
        
        
        if (session==null || session.getAttribute("usuarioLogueado")== null) {
            response.sendRedirect("/login.jsp");
            return; 
        }
        
        CuentaDTO usuarioActual=(CuentaDTO) session.getAttribute("usuarioLogueado");
        if (usuarioActual==null) {
            response.sendRedirect("/login.jsp");
            return;
        }
        
        String fecha=request.getParameter("fecha");
        String monto= request.getParameter("monto");
        try{
            if(monto==null || monto.trim().isEmpty()){
                throw new DatoInvalidoException("Debe ingresar la cantidad que quiere agregar");
            }
            
            double montoFn;
            try{
                montoFn=Double.parseDouble(monto);
            }catch(Exception e){
                throw new DatoInvalidoException("No se pudo hacer la recarga");
            }
            
            if(montoFn<=0){
                throw new DatoInvalidoException("El monto debe ser mayor a cero");
            }
            
            if(servUs.agregarSaldo(usuarioActual.getDpi(), monto, fecha)){
                CuentaDTO usuarioModificado = servUs.buscarPorDpi(usuarioActual.getDpi());
                session.setAttribute("usuarioLogueado", usuarioModificado);
            }
            
            request.setAttribute("exitoBK", "Recarga realizada con exito");

        } catch (DatoInvalidoException ex) {
            request.setAttribute("error", "El monto ingresado no es un número válido");
        } catch (SQLException ex) {
            request.setAttribute("error", "No se pudo realizar la recarga");
        }
        
        request.getRequestDispatcher("/misJsp/GeneralesJSP/recargarCartera.jsp").forward(request, response);
    }

}