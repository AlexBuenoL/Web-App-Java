<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    session = request.getSession(false); 
    if (session == null || session.getAttribute("nameUsr") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
    <head >
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="cssStyles/styling.css">
        <title>PhotoUploader</title>
    </head>
    <body>
        <h1 class="title">MENÚ</h1>
        <div class="container">
            <div class="item"><a href="registerImg.jsp">Registrar Imagen</a></div>
            <div class="item"><a href="searchImg.jsp">Buscar Imagen</a></div>
        </div>
        <div>
            <form action="login.jsp" method="get">
                <button class="logoutButton" type="submit">Cerrar sesión</button>
            </form>
        </div>
   </body>
</html>
