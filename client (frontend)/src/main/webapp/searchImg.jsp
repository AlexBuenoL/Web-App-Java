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
        <h1 class="title">BUSCAR IMAGEN</h1>
        <div class="form-container">
            <form action="searchImg" method="GET">
                <div class="input-group">
                    <label for="id">Id:</label>
                    <input type="text" id="id" name="idValue">
                </div>
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
                    <label for="creator">Creador:</label>
                    <input type="text" id="creator" name="creatorValue">
                </div>
                <div class="input-group">
                    <label for="captureDate">Fecha de creación:</label>
                    <input type="date" id="captureDate" name="captureDateValue">
                </div>
                <div class="input-group">
                    <label for="storageDate">Fecha de guardado:</label>
                    <input type="date" id="storageDate" name="storageDateValue">
                </div>
                <button class="button" type="submit">Buscar</button>
            </form>
        </div>
        <div>
            <form action="menu.jsp" method="get">
                <button class="button" type="submit">Volver al menú</button>
            </form>
        </div>
    </body>
</html>
