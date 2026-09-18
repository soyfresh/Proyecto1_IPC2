<%-- 
    Document   : servicios
    Created on : 16 sept 2026, 11:36:27
    Author     : dar333n
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <jsp:include page="/misJsp/includes/header.jsp"/>
        <h2>Servicios de Transporte</h2>

        <section>
            <h3>Seleccionar Sucursal</h3>
            <form action="${pageContext.request.contextPath}/servicios" method="get">
                <label for="idSucursal">Seleccione Sucursal:</label>
                <select name="idSucursal" id="idSucursal">
                    <option value=""> </option>
                    <c:forEach var="sucursal" items="${listaSucursales}">
                        <option value="${sucursal.idSucursal}">
                            ${sucursal.departamento} - ${sucursal.direccion}
                        </option>
                    </c:forEach>
                </select>
                <button type="submit">Buscar Viajes</button>
            </form>
        </section>   
                        
                        
                        
            <hr>
            <c:if test="${not empty param.idSucursal}">
                
                
                <section>
                    <h3>Viajes Regulares Disponibles</h3>
                    
                    <c:if test="${empty listaViajesRegulares}">
                        <p>No hay viajes regulares disponibles para esta sucursal en este momento.</p>
                    </c:if>

                    <c:choose>
                        <c:when test="${not empty listaViajesRegulares}">
                            <table border="1">
                                <thead>
                                    <tr>
                                        <th>ID Viaje</th>
                                        <th>ID Ruta</th>
                                        <th>Placa Bus</th>
                                        <th>Salida Programada</th>
                                        <th>Llegada Estimada</th>
                                        <th>Acción</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="viaje" items="${listaViajesRegulares}">
                                        <tr>
                                            <td>${viaje.idViaje}</td>
                                            <td>${viaje.idRuta}</td>
                                            <td>${viaje.placaBusAsignado}</td>
                                            <td>${viaje.fechaHoraProgramadaSalida}</td>
                                            <td>${viaje.fechaHoraEstimadaLlegada}</td>
                                            <td>

                                                <form action="${pageContext.request.contextPath}/comprarBoleto" method="get">
                                                    <input type="hidden" name="idViaje" value="${viaje.idViaje}" />
                                                    <button type="submit">Elegir Asientos</button>
                                                </form>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </c:when>
                    
                        <c:otherwise>
                            <p>No hay ningun viaje regular programado</p>
                        </c:otherwise>

                    </c:choose>
                </section>
                                                
                                                
                <hr>
                <section>
                    <h3>¿Necesitas un servicio exclusivo?</h3>
                    <p>Puedes cotizar y solicitar un viaje privado</p>
                    
                    <form action="${pageContext.request.contextPath}/misJsp/GeneralesJSP/SolicitarViajePrivado.jsp" method="GET">
                        <input type="hidden" name="idSucursal" value="${param.idSucursal}"/>
                        <button type="submit">Solicitar Viaje Privado</button>
                    </form>
                </section>
                
            </c:if>
        </body>
</html>
