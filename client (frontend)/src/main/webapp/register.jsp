<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head >
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="cssStyles/styling.css">
        <title>PhotoUploader</title>
    </head>
    <body>
        <div class="form-container">
            <h1 class="title">REGISTRO</h1>
            <form class="form" action="register" method="POST">
                <div class="input-group">
                    <label class="serif" for="username">Nombre de usuario:</label>
                    <input type="text" id="username" name="valueUsername" required>
                </div>
                <div class="input-group">
                    <label class="serif" for="pwd">Contraseña:</label>
                    <input type="password" id="pwd" name="valuePassword" required>
                </div>
                <div class="input-group">
                    <label class="serif" for="pwd2">Repita la contraseña:</label>
                    <input type="password" id="pwd2" name="valuePassword2" required>
                </div>
                <button type="submit" class="sign">Enviar</button>
            </form>
            <div class="forgot">
                <p>¿Ya tiene una cuenta? <a href="login.jsp">Inicie sesión</a></p>
            </div>
        </div>
    </body>
</html>
