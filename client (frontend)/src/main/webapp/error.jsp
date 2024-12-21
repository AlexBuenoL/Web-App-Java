<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head >
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="cssStyles/styling.css">
        <title>Error</title>
    </head>
    <body>
        <h1 class="centered serif">ERROR</h1>
        <% 
            String orig = request.getParameter("orig");
            if (orig != null && orig.equals("login")) {
        %>  
        <p> Ha habido un error al iniciar sesión, por favor compruebe sus credenciales. </p>
        <form action="login.jsp" method="get">
            <button class="button" type="submit">Volver a iniciar sesión</button>
        </form>
        <% } else if (orig != null && orig.equals("registerImg")) { %>
        <p> Ha habido un error al registrar la imagen, por favor vuelva a intentarlo. </p>
        <form action="menu.jsp" method="get">
            <button class="button" type="submit">Volver al menú</button>
        </form>
        <% } else if (orig != null && orig.equals("register_usr_exists")) { %>
        <p> El id de usuario que has introducido ya existe, por favor, ingrese un nuevo id.</p>
        <form action="register.jsp" method="get">
            <button class="button" type="submit">Volver al registro</button>
        </form>
        <% } else if (orig != null && orig.equals("register_pwds")) { %>
        <p> Las contraseñas no coinciden. Por favor, introduzca las dos contraseñas iguales.</p>
        <form action="register.jsp" method="get">
            <button class="button" type="submit">Volver al registro</button>
        </form>
        <% } else if (orig != null && orig.equals("fileExists")) { %>
        <p> Error al registrar la imagen. Ya existe una imagen con ese nombre.</p>
        <form action="menu.jsp" method="get">
            <button class="button" type="submit">Volver al menú</button>
        </form>
        <% } else if (orig != null && orig.equals("modify")) { %>
        <p> Error al modificar la imagen. Por favor, inténtelo de nuevo.</p>
        <form action="menu.jsp" method="get">
            <button class="button" type="submit">Volver al menú</button>
        </form>
        <% } else if (orig != null && orig.equals("eliminate")) { %>
        <p> Error al eliminar la imagen. Por favor, inténtelo de nuevo.</p>
        <form action="menu.jsp" method="get">
            <button class="button" type="submit">Volver al menú</button>
        </form>
        <% } else { %>
        <p> Ha ocurrido un error inesperado. </p>
        <form action="menu.jsp" method="get">
            <button class="button" type="submit">Volver al menú</button>
        </form>
        <% } %>
    </body>
</html>
