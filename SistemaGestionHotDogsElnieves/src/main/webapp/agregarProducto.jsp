<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page isErrorPage="true" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Agregar Producto</title>
        <link href="assets/css/DetalleProducto.css" rel="stylesheet" type="text/css" />
        <script src="assets/js/AgregarProducto.js"></script>
    </head>
    <body>
        <header>
            <h1>Información del Producto</h1>
        </header>

        <section class="infProducto">
            <div class="form">
                <label for="categoria">Categoría:</label>
                <select id="categoria"></select>

                <p>Nombre: <input type="text" id="nombre"</p>
                <p>Precio: <input type="text" id="precio"></p>

                <button id="botonGuardar" onclick="agregarProducto()">Guardar</button>
                <button id="botonCancelar" onclick="cancelar()">Cancelar</button>
            </div>
        </section>
    </body>
</html>
