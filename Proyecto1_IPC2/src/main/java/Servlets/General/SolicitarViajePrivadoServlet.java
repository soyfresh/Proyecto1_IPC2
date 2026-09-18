/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servlets.General;

import ClasesDTO.Cuentas.CuentaDTO;
import ConexionDB.ConnectionDB;
import Sevicios.ServicioViajePrivado;
import clasesAuxiliares.Exceptions.DatoInvalidoException;
import java.io.IOException;
import java.sql.SQLException;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "SolicitarViajePrivadoServlet", urlPatterns = {"/SolicitarViajePrivado"})
public class SolicitarViajePrivadoServlet extends HttpServlet {

    private static final String VISTA = "/misJsp/GeneralesJSP/SolicitarViajePrivado.jsp";

    private ServicioViajePrivado servicioViajePrivado;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ConnectionDB adminCone = new ConnectionDB();
        servicioViajePrivado = new ServicioViajePrivado(adminCone);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!haySesion(request, response)) {
            return;
        }
        request.getRequestDispatcher(VISTA).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!haySesion(request, response)) {
            return;
        }

        String origen = request.getParameter("origen");
        String destino = request.getParameter("destino");
        String numeroPasajeros = request.getParameter("numeroPasajeros");
        String fechasalida = request.getParameter("fechasalida");
        String fechaRetorno = request.getParameter("fechaRetorno");
        String dpiCliente = request.getParameter("dpiCliente");
        String departamentoOrigen = request.getParameter("departamentoOrigen");
        String departamentoDestino = request.getParameter("departamentoDestino");
        String idSucursal = request.getParameter("idSucursal");

        try {
            servicioViajePrivado.solicitarViajePrivado(origen, destino, numeroPasajeros, fechasalida,
                    fechaRetorno, dpiCliente, departamentoOrigen, departamentoDestino, idSucursal);

            response.sendRedirect(request.getContextPath() + "/misViajes");
            return;

        } catch (DatoInvalidoException | SQLException e) {
            request.setAttribute("errorBK", e.getMessage());
        }

        request.getRequestDispatcher(VISTA).forward(request, response);
    }

    private boolean haySesion(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return false;
        }
        return true;
    }
}