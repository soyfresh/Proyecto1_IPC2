/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets.AdminSistema;

import ClasesDAO.ConfiguracionDAO;
import ClasesDTO.ConfiguracionDTO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dar333n
 */
@WebServlet(name = "ConfiguracionServlet", urlPatterns = {"/Configuracion"})
public class ConfiguracionServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
        request.getRequestDispatcher("/misJsp/AdminSistemaJSP/gestionConfiguracion.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String monto= request.getParameter("montoPorKm");
        
        try (Connection cone=(Connection) request.getAttribute("conexionBD")){
            ConfiguracionDAO dao=new ConfiguracionDAO(cone);

            if (monto != null && !monto.trim().isEmpty()){
                
                int nuevoMonto;
                try{
                nuevoMonto = Integer.parseInt(monto);
                }catch(Exception e){
                    throw new NumberFormatException();
                }
                
                if(nuevoMonto<=0){
                    throw new NumberFormatException();
                }
                
                ConfiguracionDTO nuevaConfig = new ConfiguracionDTO(nuevoMonto);
                
                if (dao.cambiarConfiguracion(nuevaConfig)){
                    request.setAttribute("mensaje", "Configuracionn actualizada correctamente.");
                } else {
                    request.setAttribute("error", "No se pudo actualizar la configuracion");
                }
            } else {
                request.setAttribute("error", "El campo del monto no puede estar vacio.");
            }

        } catch (NumberFormatException e) {
            request.setAttribute("error", "El monto ingresado debe ser un numero entero valido.");
        } catch (SQLException ex) {
            request.setAttribute("error", "Error en la base de datos al guardar la configuracion.");
        }

        request.getRequestDispatcher("/misJsp/AdminSistemaJSP/gestionConfiguracion.jsp").forward(request, response);
    }

 
}
