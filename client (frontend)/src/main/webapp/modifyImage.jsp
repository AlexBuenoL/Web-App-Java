<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Domain.Image"%>
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
        <h1 class="title">MODIFICAR IMAGEN</h1>
        <div class="form-container">
            <form action="modifyImage" method="POST" enctype="multipart/form-data">
                <% String id = request.getParameter("id"); %>
                <input type="hidden" name="imageId" value="<%= id %>">

                <div class="input-group">
                    <label for="title">Título:</label>
                    <% String title = request.getParameter("title"); %>
                    <input type="text" id="title" name="titleValue" value="<%= title %>">
                </div>

                <div class="input-group">
                    <label for="description">Descripción:</label>
                    <% String desc = request.getParameter("desc"); %>
                    <input type="text" id="description" name="descriptionValue" value="<%= desc %>">
                </div>

                <div class="input-group">
                    <label for="keyWords">Palabras clave:</label>
                    <% String kws = request.getParameter("kw"); %>
                    <input type="text" id="keyWords" name="keyWordsValue" value="<%= kws %>">
                </div>

                <div class="input-group">
                    <label for="author">Autor:</label>
                    <% String author = request.getParameter("author"); %>
                    <input type="text" id="author" name="authorValue" value="<%= author %>">
                </div>
                
                <div class="input-group">
                    <label for="captureDate">Fecha de creación:</label>
                    <% String captureDate = request.getParameter("captureDate"); %>
                    <input type="date" id="captureDate" name="captureDateValue" value="<%= captureDate %>">
                </div>
                
                <div class="input-group">
                    <label for="imageName">Nombre de la imagen:</label>
                    <input type="text" id="imageName" name="fileNameValue">
                </div>
                
                <div class="input-group">
                    <label for="fileImg">Fichero de imagen:</label>
                    <input type="file" id="fileImg" name="fileImgValue" accept=".webp, .jpg, .jpeg, .png, .gif">
                </div>

                <button class="button" type="submit">Modificar</button>
            </form>
        </div>
        <div>
            <form action="menu.jsp" method="get">
                <button class="button" type="submit">Volver al menú</button>
            </form>
        </div>
    </body>
</html>
