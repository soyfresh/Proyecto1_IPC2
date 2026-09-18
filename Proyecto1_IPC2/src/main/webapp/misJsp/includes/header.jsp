<%-- 
    Document   : header
    Created on : 16 sept 2026, 02:05:50
    Author     : dar333n
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="ClasesDTO.Cuentas.CuentaDTO"%>

    <%
    String tipoUsuario = (String) session.getAttribute("rol");
    CuentaDTO usuario = (CuentaDTO) session.getAttribute("usuarioLogueado");
    
    String nombre= "";
    if(usuario != null){
        nombre = usuario.getNombre();
    }
    %>
    <header>
        <div>
            <h1>JavaBus</h1>
            <%if(usuario != null){%>
            <p>Bienvenido </p><%= nombre%> 
            <%}%>
            
            <ul>
                <% if ("CLIENTE".equals(tipoUsuario)) { %>
                <li>
                    <p>Cuenta</p>
                    <ul>
                        <li>
                            <a href="${pageContext.servletContext.contextPath}/modificarCuenta">Modificar Cuenta</a>
                        </li>
                        
                        <li>
                            <a href="${pageContext.servletContext.contextPath}/recargarCartera">Recargar Saldo</a>
                        </li>
                        
                        <li>
                            <a href="${pageContext.servletContext.contextPath}/CerrarSesion">Cerrar Sesión</a>
                        </li>
                    </ul>
                </li>
                
                
                <li>
                    <p>Comprar y Servicios</p>
                    <ul>
                        <li>
                            <a href="${pageContext.servletContext.contextPath}/servicios">Servicios</a>
                        </li>
                    </ul>
                </li>
                
                <li>
                    <a href="${pageContext.servletContext.contextPath}/misViajes">Mis Viajes</a>
                </li>
                
                <% } else if("ADMINISTRADOR_SISTEMA".equals(tipoUsuario)){ %>  
                <li>
                    <p>Cuenta</p>
                    <ul>
                        <li>
                            <a href="${pageContext.servletContext.contextPath}/modificarCuenta">Modificar Cuenta</a>
                        </li>
                        
                        <li>
                            <a href="${pageContext.servletContext.contextPath}/recargarCartera">Recargar Saldo</a>
                        </li>
                        
                        <li>
                            <a href="${pageContext.servletContext.contextPath}/CerrarSesion">Cerrar Sesión</a>
                        </li>
                    </ul>
                </li>

                
                <li>
                    <p>Comprar y Servicios</p>
                    <ul>
                        <li>
                            <a href="${pageContext.servletContext.contextPath}/servicios">Servicios</a>
                        </li>
                    </ul>
                </li>
                
                <li>
                    <a href="${pageContext.servletContext.contextPath}/misViajes">Mis Viajes</a>
                </li>
                
                <li>
                    <p>Personal y Sucursales</p>
                    <ul>
                        <li>
                            <a href="${pageContext.servletContext.contextPath}/misJsp/adminSistemaJSP/gestionSucursales.jsp">Gestionar Sucursales y Admins</a>
                        </li>
                        
                        <li>
                            <a href="${pageContext.servletContext.contextPath}/misJsp/adminSistemaJSP/gestionSistema.jsp">Gestión de Admins Sistema</a>
                        </li>
                        
                        <li>
                            <a href="${pageContext.servletContext.contextPath}/Configuracion">Configuracion</a>
                        </li>
                    </ul>
                </li>
                
                
                <% } else if("ADMINISTRADOR_SUCURSAL".equals(tipoUsuario)){ %>  
                
                <li>
                    <p>Cuenta</p>
                    <ul>
                        <li>
                            <a href="${pageContext.servletContext.contextPath}/modificarCuenta">Modificar Cuenta</a>
                        </li>
                        
                        <li>
                            <a href="${pageContext.servletContext.contextPath}/recargarCartera">Recargar Saldo</a>
                        </li>
                        
                        <li>
                            <a href="${pageContext.servletContext.contextPath}/CerrarSesion">Cerrar Sesión</a>
                        </li>
                    </ul>
                </li>
                
                
                <li>
                    <p>Comprar y Servicios</p>
                    <ul>
                        <li>
                            <a href="${pageContext.servletContext.contextPath}/servicios">Servicios</a>
                        </li>
                    </ul>
                </li>
                
                
                <li>
                    <a href="${pageContext.servletContext.contextPath}/misViajes">Mis Viajes</a>
                </li>
                
                
                <li>
                    <p>Operaciones de Viaje</p>
                    <ul>

                        <li>
                            <a href="${pageContext.servletContext.contextPath}/NegociacionPrivado">Negociación Viajes Privados</a>
                        </li>
                        <li>
                            <a href="${pageContext.servletContext.contextPath}/misJsp/adminSucursalJSP/gestionViajes.jsp">Programación de Viajes Regulares</a>
                        </li>
                        <li>
                            <a href="${pageContext.servletContext.contextPath}/misJsp/adminSucursalJSP/registroSalidaLlegada.jsp">Control de Salidas y Llegadas</a>
                        </li>
                        
                    </ul>
                </li>
                
                <li>
                    <p>Gestión de Recursos</p>
                    <ul>
                        <li>
                            <a href="${pageContext.servletContext.contextPath}/misJsp/adminSucursalJSP/crudBuses.jsp">Buses</a>
                        </li>
                        <li>
                            <a href="${pageContext.servletContext.contextPath}/misJsp/adminSucursalJSP/crudChoferes.jsp">Choferes</a>
                        </li>
                        <li>
                            <a href="${pageContext.servletContext.contextPath}/misJsp/adminSucursalJSP/crudRutas.jsp">Rutas</a>
                        </li>
                        <li>
                            <a href="${pageContext.servletContext.contextPath}/misJsp/adminSucursalJSP/gastosTaller.jsp">Gastos de Taller y Repuestos</a>
                        </li>
                    </ul>
                </li>
                
                
                
                <% } else if("CHOFER".equals(tipoUsuario)){ %>  
                <li>
                    <p>Cuenta</p>
                    <ul>
                        <li>
                            <a href="${pageContext.servletContext.contextPath}/modificarCuenta">Modificar Cuenta</a>
                        </li>
                        
                        <li>
                            <a href="${pageContext.servletContext.contextPath}/recargarCartera">Recargar Saldo</a>
                        </li>
                        
                        <li>
                            <a href="${pageContext.servletContext.contextPath}/CerrarSesion">Cerrar Sesión</a>
                        </li>
                    </ul>
                </li>
                
                
                <li>
                    <p>Comprar y Servicios</p>
                    <ul>
                        <li>
                            <a href="${pageContext.servletContext.contextPath}/servicios">Servicios</a>
                        </li>
                    </ul>
                </li>
                
                <li>
                    <a href="${pageContext.servletContext.contextPath}/misViajes">Mis Viajes</a>
                </li>
                
                <li>
                    <p>Gestion de Ruta</p>
                    <ul>
                        
                        <li>
                            <a href="${pageContext.servletContext.contextPath}/misJsp/ChoferJSP/viajesProgramados.jsp">Viajes Programados y Salidas</a>
                        </li>
                        
                        <li>
                            <a href="${pageContext.servletContext.contextPath}/misJsp/ChoferJSP/historialViajes.jsp">Historial</a>
                        </li>
                    </ul>
                </li>
                <% } %>
            </ul>
        </div> 
    </header>
 
