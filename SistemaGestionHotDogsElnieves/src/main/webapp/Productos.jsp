<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html> 
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Productos</title>
        <link rel="stylesheet" href="assets/css/Productos.css">
        <script src="assets/js/Productos.js"></script>
    </head>
    <body>
        <header>
            <h1>Productos</h1>
        </header>

        <nav>
            <button class="nav-button" data-categoria="todo">Todo</button>
            <button class="nav-button" data-categoria="dogo">Dogos</button>
            <button class="nav-button" data-categoria="hamburguesa">Hamburguesas</button>
            <button class="nav-button" data-categoria="bebida">Bebidas</button>
            <button class="nav-button" data-categoria="extra">Extras</button>
        </nav>

        <button class="botonRegresar" onclick="window.location.href = 'index.html';">Regresar</button>

        <section class="productos">
            <h2>Productos</h2>
            <div class="productos-container">
                <table class="tabla-productos">
                    <tr>
                        <th>Nombre</th>
                        <th>Precio</th>
                        <th>Modificar</th>
                    </tr>
                    <c:forEach var="producto" items="${productos}">                        
                        <tr class="producto" data-categoria="${producto.categoria}" data-id="${producto.id}">
                            <td>${producto.nombre}</td>
                            <td>$${producto.precio}</td>
                            <td><button class="modificar">Modificar</button></td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </section>

        <div class="botones">
            <button class="agregar" onclick="ventanaAgregar()">Agregar producto</button>
            <button class="agregar" onclick="window.location.href = 'ReporteVenta';">Reporte Venta</button>    
        </div>
    </body>
</html>
