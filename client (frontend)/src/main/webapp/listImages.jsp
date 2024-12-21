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
        <h1>LISTADO DE IMÁGENES</h1>
         <% try { 
                Integer numImages = (Integer) request.getAttribute("numImages");
                if (numImages == 0) { %>
                    <p>No images found with these features</p>
             <% } else { %>
                    <ul class="image-list"> 
                 <% for (int i = 0; i < numImages; ++i) {
                        Image img = (Image) request.getAttribute("img" + Integer.toString(i)); 
                        String base64Image = img.getImageBase64();
                        String filename = img.getFilename();
                        String imageExtension = filename.substring(filename.lastIndexOf('.') + 1);
                        String imageSrc = "data:image/" + imageExtension + ";base64," + base64Image; %>
                        
                        <li>
                            <img src="<%= imageSrc %>" alt="Image"/>
                            <div class="image-details">
                                <p><strong>Título:</strong> <%= img.getTitle() %> </p>
                                <p><strong>Descripción:</strong> <%= img.getDescription() %> </p>
                                <p><strong>Palabras clave:</strong> <%= img.getKeyWords() %> </p>
                                <p><strong>Autor:</strong> <%= img.getAuthor() %> </p>
                                <p><strong>Creador:</strong> <%= img.getCreator() %> </p>
                                <p><strong>Fecha de captura:</strong> <%= img.getCaptureDate() %> </p>
                                <p><strong>Fecha de guardado:</strong> <%= img.getStorageDate() %> </p>
                            </div>
                        
                     <% if (img.getCreator().equals(session.getAttribute("nameUsr"))) { %>
                            <div class="button-container">
                                <form action="transferDataModifyImage" method="POST">
                                    <input type="hidden" name="imageId" value="<%= img.getId() %>">
                                    <input type="hidden" name="imageTitle" value="<%= img.getTitle() %>">
                                    <input type="hidden" name="imageDescription" value="<%= img.getDescription() %>">
                                    <input type="hidden" name="imageKeyWords" value="<%= img.getKeyWords() %>">
                                    <input type="hidden" name="imageAuthor" value="<%= img.getAuthor() %>">
                                    <input type="hidden" name="imageCaptureDate" value="<%= img.getCaptureDate() %>">
                                    <button class="button" type="submit">Modificar</button>
                                </form>

                                <form action="transferDataEliminateImage" method="POST">
                                    <input type="hidden" name="imageId" value="<%= img.getId() %>">
                                    <input type="hidden" name="imageTitle" value="<%= img.getTitle() %>">
                                    <input type="hidden" name="imageDescription" value="<%= img.getDescription() %>">
                                    <input type="hidden" name="imageKeyWords" value="<%= img.getKeyWords() %>">
                                    <input type="hidden" name="imageAuthor" value="<%= img.getAuthor() %>">
                                    <input type="hidden" name="imageCaptureDate" value="<%= img.getCaptureDate() %>">
                                    <input type="hidden" name="filename" value="<%= img.getFilename() %>">
                                    <button class="logoutButton" type="submit">Eliminar</button>
                                </form>
                            </div>
                     <% } %>
                        </li>
                 <% } %>
                    </ul>
             <% }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            } %>
        <div>
            <form action="menu.jsp" method="get">
                <button class="button" type="submit">Volver al menú</button>
            </form>
        </div>
    </body>
</html>
