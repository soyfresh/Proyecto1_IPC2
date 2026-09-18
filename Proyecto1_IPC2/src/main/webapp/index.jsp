<%-- 
    Document   : index
    Created on : 14 sept 2026, 15:19:46
    Author     : dar333n
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <main>
            <section>
                <%--
                seccion para la imagen
                --%>
            </section>
            
            <section>
                <h3>iniciar sesion en JavaBus</h3>
                <br>
                
                <%
                    String error = (String) request.getAttribute("error");
                    if (error != null) {
                %>
                    <p style="color: red;"><%= error %></p>
                <%
                    }
                %>
                <form action="Login" method="POST">
                    <div> 
                        <label for="correo">Correo electrónico</label>
                        <br>
                        <input type="email" id="correo" name="correo" placeholder="Correo electrónico"> 
                    </div>
                    <br>
                     <div> 
                        <label for="contrasena">Contraseña</label>
                        <br>
                        <input type="password" id="constrasena" name="contrasena" placeholder="Constraseña"> 
                    </div>
                    <br>
                    <div>
                        <button type="submit">Iniciar Sesión</button>
                    </div>
                    <br>
                </form>
                
                <p>¿No tienes cuenta?</p>
                <a href="NuevoCliente.jsp">Crear cuenta</a>
            </section>
        </main>
    </body>
</html>
