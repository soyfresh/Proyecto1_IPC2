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
import java.io.PrintWriter;
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
@WebServlet(name = "modificarCuentaServlet", urlPatterns = {"/modificarCuenta"})
public class modificarCuentaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session= request.getSession(); 
        
        if (session==null || session.getAttribute("usuarioLogueado")== null) {
            response.sendRedirect("/login.jsp");
            return; 
        }
        
        ConnectionDB connection= new ConnectionDB();
        ServicioUsuarios servicioUsuarios= new ServicioUsuarios(connection);
        
        CuentaDTO usuarioActual=(CuentaDTO) session.getAttribute("usuarioLogueado");//solo para el dpio
        String dpi= usuarioActual.getDpi();
        
        try {
            CuentaDTO usuarioActualizado= servicioUsuarios.buscarPorDpi(dpi);
            
            session.setAttribute("usuario", usuarioActualizado);
            request.getRequestDispatcher("misJsp/GeneralesJSP/modificarCuenta.jsp").forward(request, response);
            
        } catch (SQLException | DatoInvalidoException e) {
            request.setAttribute("errorBk", "Error al cargar la información: " + e.getMessage());
            request.getRequestDispatcher("/misJsp/GeneralesJSP/modificarCuenta.jsp").forward(request, response);
        }
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        
        if (session== null || session.getAttribute("usuarioLogueado")== null) {
            response.sendRedirect("/login.jsp");
            return; 
        }
        
        String accion=request.getParameter("accion");
        ConnectionDB connection= (ConnectionDB) new ConnectionDB();
        CuentaDTO cuenta=(CuentaDTO) session.getAttribute("usuarioLogueado");
        ServicioUsuarios servicioUsuarios= new ServicioUsuarios(connection);
        
        String dpiOriginal=cuenta.getDpi();
        String coreoOriginal= cuenta.getCorreoElectronico();

        try {
            if ("actualizarDatos".equals(accion)){
                String contrasena= request.getParameter("contrasena");
                String correo= request.getParameter("correoElectronico");
                String dpi= request.getParameter("dpi");
                String nombre =request.getParameter("nombre");
                String nit= request.getParameter("nit");
                String direccion= request.getParameter("direccion");
                String telefono= request.getParameter("telefono");
                
                String tipoUsuario= request.getParameter("tipoUsuario");

                servicioUsuarios.actualizarCuenta(dpiOriginal, coreoOriginal, contrasena, correo, dpi, nombre, nit, direccion, telefono, tipoUsuario);
                CuentaDTO usuarioModificado = servicioUsuarios.buscarPorDpi(dpi);
                session.setAttribute("usuario", usuarioModificado);
                request.setAttribute("exitoBK", "Datos actualizados correctamente.");

            } else if ("cambiarContrasena".equals(accion)){
                String dpi= request.getParameter("dpi");
                String nuevaContrasena= request.getParameter("nuevaContrasena");
                String contrasenaAntigua= request.getParameter("contrasenaAntigua");
                String verificarAntigua= request.getParameter("contraAnt");

                servicioUsuarios.cambiarContrasena(dpi, nuevaContrasena, contrasenaAntigua, verificarAntigua);
                request.setAttribute("exitoBK", "Contraseña cambiada exitosamente.");
            }
        } catch (SQLException | DatoInvalidoException e) {
            request.setAttribute("errorBK", e.getMessage());
        }

        request.getRequestDispatcher("/misJsp/GeneralesJSP/modificarCuenta.jsp").forward(request, response);
    
    }

}
