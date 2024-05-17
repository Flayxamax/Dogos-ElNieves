<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page isErrorPage="true" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Información del Producto</title>
        <link href="assets/css/DetalleProducto.css" rel="stylesheet" type="text/css" />
        <script src="assets/js/ModificarProducto.js"></script>
    </head>
    <body>
        <header>
            <h1>Información del Producto</h1>
        </header>

        <div class="infProducto">
            <div class="form">
                <label for="categoria">Categoría:</label>
                <select id="categoria" disabled></select>

                <input type="hidden" id="idProducto" value="${producto.id}">
                <p>Nombre: <input type="text" name="nombre" value="${producto.nombre}" readonly></p>
                <p>Precio: <input type="text" name="precio" value="${producto.precio}" readonly></p>
                <input type="hidden" id="categoriaProducto" value="${producto.categoria}">
                
                <button id="botonModificar" onclick="modificarProducto()">Modificar</button>
                <button id="botonEliminar" onclick="eliminarProducto()">Eliminar</button>
                <button id="botonRegresarAtras" onclick="regresarAtras()" >Regresar</button>
                <button id="botonGuardar" onclick="guardarProducto()" style="display:none;" >Guardar</button>
                <button id="botonCancelar" onclick="cancelarEdicion()" style="display:none;" >Cancelar</button>
                <button id="botonRegresar" onclick="regresar()" style="display:none;">Regresar</button>
            </div>
        </div>
    </body>
</html>
