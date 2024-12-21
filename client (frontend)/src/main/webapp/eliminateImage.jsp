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
        <title>Photo uploader</title>
    </head>
    <body>
        <h1 class="centered serif">ELIMINAR IMAGEN</h1>
        <div class="container">
            <form class="centered serif" action="eliminateImage" method="POST">
                <% String id = request.getParameter("id"); %>
                <input class="input-onlyRead" type="hidden" name="imageId" value="<%= id %>">

                <label class="serif" for="title">Título:</label>
                <% String title = request.getParameter("title"); %>
                <input class="input-onlyRead" type="text" id="title" name="titleValue" value="<%= title %>" readonly>

                <label class="serif" for="description">Descripción:</label>
                <% String desc = request.getParameter("desc"); %>
                <input class="input-onlyRead" type="text" id="description" name="descriptionValue" value="<%= desc %>" readonly>

                <label class="serif" for="keyWords">Palabras clave:</label>
                <% String kws = request.getParameter("kw"); %>
                <input class="input-onlyRead" type="text" id="keyWords" name="keyWordsValue" value="<%= kws %>" readonly>

                <label class="serif" for="author">Autor:</label>
                <% String author = request.getParameter("author"); %>
                <input class="input-onlyRead" type="text" id="author" name="authorValue" value="<%= author %>" readonly>

                <label class="serif" for="captureDate">Fecha de captura:</label>
                <% String captureDate = request.getParameter("captureDate"); %>
                <input class="input-onlyRead" type="text" id="captureDate" name="captureDateValue" value="<%= captureDate %>" readonly>

                <% String filename = request.getParameter("filename"); %>
                <input type="hidden" name="filename" value="<%= filename %>">

                <button class="logoutButton" type="submit">Eliminar</button>
            </form>
        </div>
        <div>
            <form action="menu.jsp" method="get">
                <button class="button" type="submit">Volver al menú</button>
            </form>
        </div>
    </body>
</html>
