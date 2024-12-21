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
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="cssStyles/styling.css">
        <title>PhotoUploader</title>
    </head>
    <body>
        <h1 class="centered serif">Eliminación de imagen realizada correctamente!</h1>
        <div class="centered serif">
            <form action="searchImg.jsp" method="get">
                <button class="button" type="submit">Buscar imagen</button>
            </form>
        </div>
        <div class="centered serif">
            <form action="menu.jsp" method="get">
                <button class="button" type="submit">Volver al menú</button>
            </form>
        </div>
    </body>
</html>
