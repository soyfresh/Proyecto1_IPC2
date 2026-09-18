<%-- 
    Document   : misViajes
    Created on : 16 sept 2026, 11:37:19
    Author     : dar333n
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Mis Viajes</title>
    </head>
    <body>
        <jsp:include page="/misJsp/includes/header.jsp"/>
        <h1>Mis Viajes y Boletos</h1>
        <br>
        <h2>Boletos Comprados</h2>

        <c:choose>
            <c:when test="${not empty listaBoletos}">
                <table>
                    <thead>
                        <tr>
                            <th>ID Boleto</th>
                            <th>No. Asiento</th>
                            <th>Precio</th>
                            <th>Fecha Compra</th>
                            <th>ID Viaje</th>
                        </tr>
                    </thead>
                    <tbody>
                        
                        <c:forEach items="${listaBoletos}" var="boleto">
                            <tr>
                                <td>${boleto.idBoleto}</td>
                                <td>
                                    <c:forEach items="${boleto.detalle}" var="det" varStatus="st">
                                        ${det.idAsiento}<c:if test="${!st.last}">, </c:if>
                                    </c:forEach>
                                </td>
                                <td>${boleto.total}</td>
                                <td>${boleto.fechaCompra}</td>
                                <td>${boleto.idViaje}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:when>
            
            <c:otherwise>
                <p>No tienes boletos registrados.</p>
            </c:otherwise>
        </c:choose>
                
                <br>
                <br>
        <h2>Solicitudes de Viajes Privados</h2>
        
        <c:choose>
            <c:when test="${not empty listaViajesPrivados}">
                <table>
                    
                    <thead>
                        <tr>
                            <th>ID Viaje</th>
                            <th>Estado</th>
                            <th>Origen</th>
                            <th>Destino</th>
                            <th>Pasajeros</th>
                            <th>Fecha Salida</th>
                            <th>Fecha Retorno</th>
                            <th>Precio Final</th>
                            <th>Accion: Pagar</th>
                            <th>Accion: Cancelar</th>
                        </tr>
                    </thead>
                    
                    <tbody>
                        <c:forEach items="${listaViajesPrivados}" var="privado">
                            <tr>
                                <td>${privado.idViaje}</td>
                                <td>${privado.estadoViaje}</td>
                                <td>${privado.origen}</td>
                                <td>${privado.destino}</td>
                                <td>${privado.numeroPasajeros}</td>
                                <td>${privado.fechaSalida}</td>
                                <td>${privado.fechaRetorno}</td>
                                <td>${privado.precioFinal}</td>

                                
                                <td>
                                    <form action="${pageContext.request.contextPath}/misViajes" method="POST">
                                        <input type="hidden" name="accion" value="pagarViaje">
                                        <input type="hidden" name="idViaje" value="${privado.idViaje}">
                                        <input type="date" name="fechaPago" required>
                                        <input type="submit" value="Pagar">
                                    </form>
                                </td>

                                <td>
                                    <form action="${pageContext.request.contextPath}/misViajes" method="POST">
                                        <input type="hidden" name="accion" value="cancelarViaje">
                                        <input type="hidden" name="idViaje" value="${privado.idViaje}">
                                        <input type="submit" value="Cancelar">
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:when>
            
            <c:otherwise>
                <p>No tienes solicitudes de viajes privados.</p>
            </c:otherwise>
                
        </c:choose>
    </body>
</html>
