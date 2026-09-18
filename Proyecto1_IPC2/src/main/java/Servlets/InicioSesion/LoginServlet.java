/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets.InicioSesion;

import ClasesDTO.Cuentas.AdministradorSistemaDTO;
import ClasesDTO.Cuentas.AdministradorSucursalDTO;
import ClasesDTO.Cuentas.ChoferDTO;
import ClasesDTO.Cuentas.ClienteDTO;
import ClasesDTO.Cuentas.CuentaDTO;
import ConexionDB.ConnectionDB;
import Sevicios.ServicioUsuarios;
import clasesAuxiliares.Exceptions.DatoInvalidoException;
import jakarta.servlet.ServletConfig;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;

/**
 *
 * @author dar333n
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/Login"})
public class LoginServlet extends HttpServlet {
    
    private ServicioUsuarios servicioUsuarios;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config); 
        
        ConnectionDB adminCone=new  ConnectionDB();
        servicioUsuarios=new ServicioUsuarios(adminCone);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("index.jsp").forward(request, response);
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String correo = request.getParameter("correo");
        String contrasena = request.getParameter("contrasena");
        
        try{
            CuentaDTO usuario= servicioUsuarios.autenticar(correo, contrasena);
            
            HttpSession session = request.getSession();
            session.setAttribute("usuarioLogueado", usuario);
            
            if (usuario instanceof AdministradorSistemaDTO) {
                session.setAttribute("rol", "ADMINISTRADOR_SISTEMA");
                response.sendRedirect("misJsp/ClienteJSP/inicio.jsp");
            } else if (usuario instanceof AdministradorSucursalDTO) {
                session.setAttribute("rol", "ADMINISTRADOR_SUCURSAL");
                response.sendRedirect("misJsp/ClienteJSP/inicio.jsp");
            } else if (usuario instanceof ChoferDTO) {
                session.setAttribute("rol", "CHOFER");
                response.sendRedirect("misJsp/ChoferJSP/inicio.jsp");
            } else if (usuario instanceof ClienteDTO) {
                session.setAttribute("rol", "CLIENTE");
                response.sendRedirect("misJsp/ClienteJSP/inicio.jsp");
            }
            
            
        }catch(SQLException | DatoInvalidoException e){
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
