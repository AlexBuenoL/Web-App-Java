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
        <title>PhotoUploader</title>
        <link rel="stylesheet" href="cssStyles/styling.css">
    </head>
    <body>
        <h1 class="title">REGISTRO DE IMAGEN</h1>
        <form class="form-container" action="registerImg" method="POST" enctype="multipart/form-data">
            <div class="input-group">
                <label for="title">Título:</label>
                <input type="text" id="title" name="titleValue">
            </div>
            <div class="input-group">
                <label for="description">Descripción:</label>
                <input type="text" id="description" name="descriptionValue">
            </div>
            <div class="input-group">
                <label for="keyWords">Palabras clave:</label>
                <input type="text" id="keyWords" name="keyWordsValue">
            </div>
            <div class="input-group">
                <label for="author">Autor:</label>
                <input type="text" id="author" name="authorValue">
            </div>
            <div class="input-group">
                <label for="captureDate">Fecha de creación:</label>
                <input type="date" id="captureDate" name="captureDateValue">
            </div>
            <div class="input-group">
                <label for="fileName">Nombre de la imagen:</label>
                <input type="text" id="fileName" name="fileNameValue">
            </div>
            <div class="input-group">
                <label for="fileImg">Seleccione la imagen:</label>
                <input type="file" id="fileImg" name="fileImgValue" accept=".webp, .jpg, .jpeg, .png, .gif">
            </div>
            <button type="submit" class="button">Enviar</button>
        </form>
        <div>
            <form action="menu.jsp" method="get">
                <button class="button" type="submit">Volver al menú</button>
            </form>
        </div>
    </body>
</html>
