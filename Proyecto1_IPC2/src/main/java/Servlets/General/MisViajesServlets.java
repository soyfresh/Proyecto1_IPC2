/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets.General;

import ClasesDTO.BoletoDTO;
import ClasesDTO.Cuentas.CuentaDTO;
import ClasesDTO.Viajes.ViajePrivadoDTO;
import ConexionDB.ConnectionDB;
import Sevicios.ServicioVenta;
import Sevicios.ServicioViajePrivado;
import clasesAuxiliares.Exceptions.DatoInvalidoException;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author dar333n
 */
@WebServlet(name = "MisViajesServlet", urlPatterns = {"/misViajes"})


public class MisViajesServlets extends HttpServlet {

    public void settarMisViajes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        
        HttpSession session= request.getSession();
        CuentaDTO cuentaUario= (CuentaDTO) session.getAttribute("usuarioLogueado");
        String dpiUsr= cuentaUario.getDpi();
        
        if (session==null || session.getAttribute("usuarioLogueado")== null) {
            response.sendRedirect("login.jsp");
            return; 
        }
        
        ConnectionDB connection=new ConnectionDB();
        ServicioViajePrivado servicioPrivado = new ServicioViajePrivado(connection);
        ServicioVenta servicioRegular = new ServicioVenta(connection);
        
        try{
            List<ViajePrivadoDTO> viajesPrivados=servicioPrivado.obtenerViajPrivNegociadosClien(dpiUsr);
            List<BoletoDTO> boletos=servicioRegular.listarBoletosComprados(dpiUsr);
            
            request.setAttribute("listaViajesPrivados", viajesPrivados);
            request.setAttribute("listaBoletos", boletos);
            
        }catch(SQLException | DatoInvalidoException e){
            request.setAttribute("mensajeError", "Error al cargar la información: " + e.getMessage());
        }
        request.getRequestDispatcher("/misJsp/GeneralesJSP/misViajes.jsp").forward(request, response);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        settarMisViajes(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session=request.getSession();
        CuentaDTO cuentaUario= (CuentaDTO) session.getAttribute("usuarioLogueado");
        String dpiCliente=cuentaUario.getDpi();
        
        if (dpiCliente==null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        String accion= request.getParameter("accion");
        
        ConnectionDB connection= new ConnectionDB();
        ServicioViajePrivado servicioPrivado= new ServicioViajePrivado(connection);

        try {
            if ("pagarViaje".equals(accion)) {
                String idViaje= request.getParameter("idViaje");
                String fechaPago= request.getParameter("fechaPago");
                
                servicioPrivado.pagarViajePrivado(idViaje, dpiCliente, fechaPago);
                session.setAttribute("mensajeExito", "Viaje pagado exitosamente.");
                
            } else if ("cancelarViaje".equals(accion)) {
                String idViaje = request.getParameter("idViaje");
                
                servicioPrivado.cancelarViaje(idViaje);
                session.setAttribute("mensajeExito", "Viaje cancelado exitosamente.");
            }
        } catch (DatoInvalidoException e) {
            session.setAttribute("mensajeError", "Error en datos: " + e.getMessage());
        } catch (SQLException e) {
            session.setAttribute("mensajeError", "Error al procesar la solicitud en la base de datos.");
        }
        
        response.sendRedirect(request.getContextPath()+"/misViajes");
    }

  
}
