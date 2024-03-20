<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Orden</title>
        <link rel="stylesheet" href="assets/css/CobrarOrden.css">
        <script src="assets/css/CobrarOrden.js"></script>
    </head>
    <body>
        <header>
            <h1>Cobrar orden</h1>
        </header>

        <nav>
            <button class="nav-button" onclick="filtrarProductos('dogos')">Dogos</button>
            <button class="nav-button" onclick="filtrarProductos('hamburguesas')">Hamburguesas</button>
            <button class="nav-button" onclick="filtrarProductos('bebidas')">Bebidas</button>
            <button class="nav-button" onclick="filtrarProductos('extra')">Extras</button>
        </nav>

        <section class="productos">
            <h2>Productos</h2>
            <div class="scroll-wrapper" id="prodcutos-container">
                <c:forEach var="producto" items="${productos}">
                    <div class="producto">
                        <div class="info">
                            <span class="nombre" value="${producto.id}">${producto.nombre}</span>
                            <div class="detalle">
                                <span class="precio">${producto.precio}</span>
                            </div>
                        </div>
                        <button class="agregar">Agregar</button>
                    </div>
                </c:forEach>
            </div>
        </section>

        <section class="orden">
            <h2>Orden</h2>
            <div class="orden-lista">

                <div class="productoOrden">
                    <div class="info">
                        <span class="nombre"></span>
                        <div class="detalle">
                            <span class="precio"></span>
                        </div>
                    </div>
                    <button class="quitar">Quitar</button>
                </div>

            </div>
            <button class="pagar">Pagar</button>
            <span id="total">$TOTAL</span>
        </section>
    </section>
</body>
</html>
