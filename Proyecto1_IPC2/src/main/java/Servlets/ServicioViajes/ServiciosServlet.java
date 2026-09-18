/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets.ServicioViajes;

import ClasesDAO.SucursalDAO;
import ClasesDTO.SucursalDTO;
import ClasesDTO.Viajes.ViajeRegularDTO;
import ConexionDB.ConnectionDB;
import Sevicios.ServicioViajeRegular;
import clasesAuxiliares.Exceptions.DatoInvalidoException;
import jakarta.servlet.ServletConfig;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author dar333n
 */
@WebServlet(name = "ServiciosServlet", urlPatterns = {"/servicios"})
public class ServiciosServlet extends HttpServlet {

    private ServicioViajeRegular servRegular;
    private ConnectionDB admin;
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config); 
        
        this.admin=new ConnectionDB();
        this.servRegular = new ServicioViajeRegular(admin);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try (Connection cone= admin.getConnection()){
            SucursalDAO sucursalDAO=new SucursalDAO(cone);
            String idSucursalParam = request.getParameter("idSucursal");
            
            
            
            List<SucursalDTO> listaSucursales= sucursalDAO.obtenerSucursales();
            request.setAttribute("listaSucursales", listaSucursales);
            if (idSucursalParam != null && !idSucursalParam.trim().isEmpty()) {
                List<ViajeRegularDTO> listaViajes = servRegular.obtenerViajesRegProgramados(idSucursalParam);
                
               int idFN;
               try{
                    idFN= Integer.parseInt(idSucursalParam);
                }catch(Exception e){
                    throw new DatoInvalidoException();
                } 
                
                request.setAttribute("listaViajes", listaViajes);
                request.setAttribute("listaViajesRegulares", listaViajes);
                request.setAttribute("idSucursalSeleccionada", idFN);
            }
            
            
        }catch (SQLException ex){
            request.setAttribute("error", "Error de conexión con la base de datos");
        }catch(DatoInvalidoException ex){
            request.setAttribute("error", "No se pudo obtener el ID");
        }
        
        request.getRequestDispatcher("/misJsp/GeneralesJSP/servicios.jsp").forward(request, response);
    }

}
