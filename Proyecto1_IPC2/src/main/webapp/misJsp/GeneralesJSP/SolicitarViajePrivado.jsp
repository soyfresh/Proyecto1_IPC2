<%-- 
    Document   : SolicitarViajePrivado
    Created on : 17 sept 2026, 14:40:26
    Author     : dar333n
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="ClasesDTO.Cuentas.CuentaDTO"%>

    <%
        CuentaDTO usuario= (CuentaDTO) session.getAttribute("usuarioLogueado");
        if (usuario==null) {
            response.sendRedirect(request.getContextPath()+"/index.jsp");
            return;
        }
        String error= (String) request.getAttribute("errorBK");
    %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <jsp:include page="/misJsp/includes/header.jsp"/>
        <h2>Solicitud de Viaje Privado</h2>
        <br><!-- comment -->
        <% if (error != null) { %>
        <p>Error: <%= error %></p>
        <% } %>
        
        <section>
            <form action="${pageContext.request.contextPath}/SolicitarViajePrivado" method="POST">

                <input type="hidden" name="idSucursal" value="${param.idSucursal}"/>

                <input type="hidden" name="dpiCliente" value="<%= usuario.getDpi() %>"/>

                <p>
                    <label for="departamentoOrigen">Departamento de Origen (Punto de Encuentro):</label>
                    <br>
                    <select id="departamentoOrigen" name="departamentoOrigen" required>
                        <option value=""> </option>
                        <option value="ALTA_VERAPAZ">Alta Verapaz</option>
                        <option value="BAJA_VERAPAZ">Baja Verapaz</option>
                        <option value="CHIMALTENANGO">Chimaltenango</option>
                        <option value="CHIQUIMULA">Chiquimula</option>
                        <option value="EL_PROGRESO">El Progreso</option>
                        <option value="ESCUINTLA">Escuintla</option>
                        <option value="GUATEMALA">Guatemala</option>
                        <option value="HUEHUETENANGO">Huehuetenango</option>
                        <option value="IZABAL">Izabal</option>
                        <option value="JALAPA">Jalapa</option>
                        <option value="JUTIAPA">Jutiapa</option>
                        <option value="PETEN">Petén</option>
                        <option value="QUETZALTENANGO">Quetzaltenango</option>
                        <option value="QUICHE">Quiché</option>
                        <option value="RETALHULEU">Retalhuleu</option>
                        <option value="SACATEPEQUEZ">Sacatepéquez</option>
                        <option value="SAN_MARCOS">San Marcos</option>
                        <option value="SANTA_ROSA">Santa Rosa</option>
                        <option value="SOLOLA">Sololá</option>
                        <option value="SUCHITEPEQUEZ">Suchitepéquez</option>
                        <option value="TOTONICAPAN">Totonicapán</option>
                        <option value="ZACAPA">Zacapa</option>
                    </select>
                </p>

                <p>
                    <label for="origen">Dirección o Punto Exacto de Origen:</label>
                    <br>
                    <input type="text" id="origen" name="origen" required value="${param.origen}"/>
                </p>

                <p>
                    <label for="departamentoDestino">Departamento de Destino:</label>
                    <br>
                    <select id="departamentoDestino" name="departamentoDestino" required>
                        <option value=""> </option>
                        <option value="ALTA_VERAPAZ">Alta Verapaz</option>
                        <option value="BAJA_VERAPAZ">Baja Verapaz</option>
                        <option value="CHIMALTENANGO">Chimaltenango</option>
                        <option value="CHIQUIMULA">Chiquimula</option>
                        <option value="EL_PROGRESO">El Progreso</option>
                        <option value="ESCUINTLA">Escuintla</option>
                        <option value="GUATEMALA">Guatemala</option>
                        <option value="HUEHUETENANGO">Huehuetenango</option>
                        <option value="IZABAL">Izabal</option>
                        <option value="JALAPA">Jalapa</option>
                        <option value="JUTIAPA">Jutiapa</option>
                        <option value="PETEN">Petén</option>
                        <option value="QUETZALTENANGO">Quetzaltenango</option>
                        <option value="QUICHE">Quiché</option>
                        <option value="RETALHULEU">Retalhuleu</option>
                        <option value="SACATEPEQUEZ">Sacatepéquez</option>
                        <option value="SAN_MARCOS">San Marcos</option>
                        <option value="SANTA_ROSA">Santa Rosa</option>
                        <option value="SOLOLA">Sololá</option>
                        <option value="SUCHITEPEQUEZ">Suchitepéquez</option>
                        <option value="TOTONICAPAN">Totonicapán</option>
                        <option value="ZACAPA">Zacapa</option>
                    </select>
                </p>

                <p>
                    <label for="destino">Dirección o Punto Exacto de Destino:</label>
                    <br>
                    <input type="text" id="destino" name="destino" required value="${param.destino}"/>
                </p>

                <p>
                    <label for="numeroPasajeros">Número de Pasajeros:</label>
                    <br>
                    <input type="number" id="numeroPasajeros" name="numeroPasajeros" min="1" required value="${param.numeroPasajeros}"/>
                </p>

                <p>
                    <label for="fechasalida">Fecha de Salida:</label>
                    <br>
                    <input type="date" id="fechasalida" name="fechasalida" required value="${param.fechasalida}"/>
                </p>

                <p>
                    <label for="fechaRetorno">Fecha de Retorno (Opcional):</label>
                    <br>
                    <input type="date" id="fechaRetorno" name="fechaRetorno" value="${param.fechaRetorno}"/>
                </p>

                <p>
                    <button type="submit">Enviar Solicitud</button>
                </p>
            </form>
        </section>

                
        <hr>
        
        <section>
            <form action="${pageContext.request.contextPath}/servicios" method="GET">
                <input type="hidden" name="idSucursal" value="${param.idSucursal}"/>
                <button type="submit">Cancelar y Volver</button>
            </form>
        </section>
    </body>
</html>
