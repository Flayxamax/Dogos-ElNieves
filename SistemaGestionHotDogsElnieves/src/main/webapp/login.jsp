<%-- 
    Document   : login
    Created on : 12 may. 2024, 19:43:11
    Author     : JORGE
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <title>Inicio de sesión</title>
        <meta charset="UTF-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="assets/css/DetalleProducto.css" rel="stylesheet" type="text/css" />
        <script src="assets/js/Login.js"></script>
    </head>
    <body>
        
        <header>
            <h1>Inicio de sesión</h1>
        </header>
        
        <div class="infProducto">
            <div class="form">
                <div class="form-group">
                    <label for="usuario">Nombre de Usuario:</label>
                    <input type="text" id="usuario" name="usuario" placeholder="Ingrese el Nombre de Usuario" required>
                </div>
                <div class="form-group">
                    <label for="pass">Contraseña:</label>
                    <input type="password" id="pass" name="pass" placeholder="Ingrese su Contraseña" required>
                </div>
                <button type="submit" onclick="iniciarSesion()">Iniciar Sesión</button>
                <button class="submit" onclick="window.location.href = 'index.html';">Regresar</button>
            </div>
        </div>
    </body>
