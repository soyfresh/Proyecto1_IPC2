/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets.InicioSesion;

import ConexionDB.ConnectionDB;
import Sevicios.ServicioUsuarios;
import clasesAuxiliares.Exceptions.DatoInvalidoException;
import clasesAuxiliares.Exceptions.VacioException;
import jakarta.servlet.ServletConfig;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;

/**
 *
 * @author dar333n
 */
@WebServlet(name = "NuevaCuenta", urlPatterns = {"/NuevaCuenta"})
public class NuevaCuenta extends HttpServlet {

    private ServicioUsuarios servicioUsuarios;
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        
        ConnectionDB adminCone=new  ConnectionDB();
        servicioUsuarios=new ServicioUsuarios(adminCone);
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.getRequestDispatcher("NuevoCliente.jsp").forward(request, response);
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String dpi = request.getParameter("dpi");
        String nit = request.getParameter("nit");
        String nombre = request.getParameter("nombre");
        String correoElectronico = request.getParameter("correoElectronico");
        String contrasena = request.getParameter("contrasena");
        String telefono = request.getParameter("telefono");
        String direccion = request.getParameter("direccion");
        
        try{
           
            if(servicioUsuarios.registrarUsuarioo(contrasena, correoElectronico, dpi, nombre, nit, direccion, telefono, "CLIENTE")){
                response.sendRedirect("index.jsp");
            }

        }catch(SQLException | DatoInvalidoException e){
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("NuevoCliente.jsp").forward(request, response);
        }
    }

}
