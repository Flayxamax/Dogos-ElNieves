<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html> 
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Productos</title>
    <link rel="stylesheet" href="assets/css/CobrarOrden.css">
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

    <section class="productos">
        <h2>Productos</h2>
        <div class="scroll-wrapper" id="productos-container">
            <c:forEach var="producto" items="${productos}">
                <div class="producto" data-categoria="${producto.categoria}" data-id="${producto.id}">
                    <div class="info">
                        <span class="nombre">${producto.nombre}</span>
                        <div class="detalle">
                            <span class="precio">$${producto.precio}</span>
                        </div>
                    </div>
                    <button class="modificar">Modificar</button>
                </div>
            </c:forEach>
        </div>
    </section>
    <button class="agregar" onclick="ventanaAgregar()">Agregar producto</button>    
</body>
</html>
