<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page isErrorPage="true" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Agregar Producto</title>
        <script src="assets/js/AgregarProducto.js"></script>
    </head>
    <body>
        <h1>Información del Producto</h1>


        <label for="categoria">Categoría:</label>
        <select id="categoria"></select>

        <p>Nombre: <input type="text" id="nombre"</p>
        <p>Precio: <input type="text" id="precio"></p>

        <button id="botonGuardar" onclick="agregarProducto()">Guardar</button>
        <button id="botonCancelar" onclick="cancelar()">Cancelar</button>

    </body>
</html>
