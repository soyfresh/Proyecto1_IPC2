<%-- 
    Document   : NegociacionViajePrivado
    Created on : 16 sept 2026, 17:32:20
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
        <h1>Gestion de Negociaciones de Viajes Privados</h1>
        
        
        <table>
            <thead>
                <tr>
                    <th>ID Viaje</th>
                    <th>Cliente (DPI)</th>
                    <th>Origen</th>
                    <th>Destino</th>
                    <th>Pasajeros</th>
                    <th>Fecha Salida</th>
                    <th>Fecha Retorno</th>
                    <th>Precio Cotizado</th>
                    <th>Estado</th>
                    <th>Bus Asignado</th>
                    <th>Chofer Asignado</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            
            <tbody>
            <c:forEach var="viaje" items="${listaNegociaciones}">
                <tr>
                    <td>${viaje.idViaje}</td>
                    <td>${viaje.dpiCliente}</td>
                    <td>${viaje.origen} (${viaje.departamentoOrigen})</td>
                    <td>${viaje.destino} (${viaje.departamentoDestino})</td>
                    <td>${viaje.numeroPasajeros}</td>
                    <td>${viaje.fechaSalida}</td>
                    <td>${viaje.fechaRetorno != null ? viaje.fechaRetorno : 'N/A'}</td>
                    <td>Q${viaje.precioEstimado}</td>
                    <td>${viaje.estadoViaje}</td>
                    <td>${viaje.placaBusAsignado != null ? viaje.placaBusAsignado : 'Sin Asignar'}</td>
                    <td>${viaje.dpiChofer != null ? viaje.dpiChofer : 'Sin Asignar'}</td>
                    <td>
                        
                        <form action="${pageContext.request.contextPath}/NegociacionPrivado" method="GET">
                            <input type="hidden" name="idViaje" value="${viaje.idViaje}">
                            <input type="hidden" name="precioCotizado" value="${viaje.precioEstimado}">
                            <input type="hidden" name="vistaAccion" value="formAceptar">
                            <input type="submit" value="Aceptar">
                        </form>

                        
                        <form action="${pageContext.request.contextPath}/NegociacionPrivado" method="post">
                            <input type="hidden" name="accion" value="rechazar">
                            <input type="hidden" name="idViaje" value="${viaje.idViaje}">
                            <input type="submit" value="Rechazar">
                        </form>

                        
                        <form action="${pageContext.request.contextPath}/NegociacionPrivado" method="GET">
                            <input type="hidden" name="idViaje" value="${viaje.idViaje}">
                            <input type="hidden" name="vistaAccion" value="formAsignar">
                            <input type="submit" value="Asignar Bus/Chofer">
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <br>
        <br>
        <hr>
         <c:if test= "${param.vistaAccion == 'formAceptar'}">
            <h3>Aceptar Viaje Privado ID:${param.idViaje}</h3>
            <form action="${pageContext.request.contextPath}/NegociacionPrivado" method="post">
                <input type="hidden" name="accion" value="aceptar">
                <input type="hidden" name="idViaje" value="${param.idViaje}">

                <p><strong>Precio Cotizado por Sistema:</strong> Q${param.precioCotizado}</p>

                <label for="precioFinal">Precio Final Acordado (Q):</label><br>
                <input type="number" step="0.01" id="precioFinal" name="precioFinal" value="${param.precioCotizado}" required><br><br>

                <input type="submit" value="Confirmar y Aceptar Viaje">
            </form>
        </c:if>
    
        <c:if test="${param.vistaAccion == 'formAsignar'}">
            <h3>Asignar Bus y Chofer al Viaje ID:${param.idViaje}</h3>
            <form action="${pageContext.request.contextPath}/NegociacionPrivado" method="post">
                <input type="hidden" name="accion" value="asignar">
                <input type="hidden" name="idViaje" value="${param.idViaje}">

                <label for="placaBus">Numero de Placa del Bus:</label><br>
                <input type="text" id="placaBus" name="placaBus" required><br><br>

                <label for="dpiChofer">DPI del Chofer (13 digitos):</label><br>
                <input type="text" id="dpiChofer" name="dpiChofer" maxlength="13" required><br><br>

                <input type="submit" value="Guardar Asignacion">
            </form>
        </c:if>
    </body>
</html>
