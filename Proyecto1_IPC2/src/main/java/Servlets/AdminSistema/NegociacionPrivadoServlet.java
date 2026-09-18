/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets.AdminSistema;

import ClasesDAO.SucursalDAO;
import ClasesDTO.Cuentas.CuentaDTO;
import ClasesDTO.SucursalDTO;
import ClasesDTO.Viajes.ViajePrivadoDTO;
import ConexionDB.ConnectionDB;
import Sevicios.ServicioViajePrivado;
import clasesAuxiliares.Exceptions.DatoInvalidoException;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author dar333n
 */
@WebServlet(name = "NegociacionPrivadoServlet", urlPatterns = {"/NegociacionPrivado"})
public class NegociacionPrivadoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session= request.getSession(false);
        if (session==null || session.getAttribute("usuarioLogueado")==null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }
        CuentaDTO admin = (CuentaDTO) session.getAttribute("usuarioLogueado");
        ConnectionDB connectionDB=new ConnectionDB();

        try {
            String idSucursal;
            try (Connection cone= connectionDB.getConnection()) {
                SucursalDAO sucursalDao= new SucursalDAO(cone);
                SucursalDTO sucursal = sucursalDao.obtenerConDPI(admin.getDpi());
                if (sucursal== null) {
                    throw new DatoInvalidoException("Su cuenta no tiene ninguna sucursal asignada");
                }
                idSucursal = String.valueOf(sucursal.getIdSucursal());
            }

            ServicioViajePrivado servicio=new ServicioViajePrivado(connectionDB);
            

            List<ViajePrivadoDTO> listaNegociaciones = servicio.obtenerViajPrivNegociadosAdm(idSucursal);
            request.setAttribute("listaNegociaciones", listaNegociaciones);

        } catch (SQLException | DatoInvalidoException e){
            request.setAttribute("error", "Error al cargar la lista de negociaciones: " + e.getMessage());
        }
        
        request.getRequestDispatcher("/misJsp/AdminSucursalJSP/NegociacionViajePrivado.jsp").forward(request, response);
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String accion=request.getParameter("accion");
        ConnectionDB connectionDB= new ConnectionDB();
        
        ServicioViajePrivado servicio= new ServicioViajePrivado(connectionDB);
        
        try {
            if("aceptar".equals(accion)){
                String idViaje = request.getParameter("idViaje");
                String precioFinal = request.getParameter("precioFinal");
                servicio.aceptarSolicitud(idViaje, precioFinal);
                
                request.setAttribute("mensaje", "Viaje aceptado y precio actualizado exitosamente.");

            } else if("rechazar".equals(accion)){
                String idViaje = request.getParameter("idViaje");
                servicio.rechazarSolicitud(idViaje);
                
                request.setAttribute("mensaje", "Viaje rechazado correctamente.");

            }else if("asignar".equals(accion)){
                String idViaj=request.getParameter("idViaje");
                String placaBus= request.getParameter("placaBus");
                String dpiChofer=request.getParameter("dpiChofer");
                servicio.asignarBusChofer(idViaj, placaBus, dpiChofer);
                
                request.setAttribute("mensaje", "Bus y Chofer asignados correctamente.");
            }
        } catch (DatoInvalidoException e) {
            request.setAttribute("error", "Datos inválidos");
        } catch (SQLException e) {
            request.setAttribute("error", "Error en base de datos");
        }

        doGet(request, response);
    }

}
