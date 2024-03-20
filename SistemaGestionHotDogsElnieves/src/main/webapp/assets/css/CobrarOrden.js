document.addEventListener('DOMContentLoaded', function () {
    // Variable para almacenar los productos agregados a la orden
    let ordenProductos = [];

    // Obtener los botones de agregar y quitar productos
    const agregarBotones = document.querySelectorAll('.agregar');
    const quitarBotones = document.querySelectorAll('.quitar');

    // Función para agregar un producto a la orden
    function agregarProducto(event) {
        const nombreProducto = event.target.parentElement.querySelector('.nombre').textContent;
        const precioProducto = parseFloat(event.target.parentElement.querySelector('.precio').textContent.replace('$', ''));
        const idProducto = event.target.parentElement.querySelector('.nombre').getAttribute('value');

        // Crear objeto de producto
        const nuevoProducto = {
            id: idProducto,
            nombre: nombreProducto,
            precio: precioProducto
        };

        // Agregar el producto a la orden
        ordenProductos.push(nuevoProducto);

        // Actualizar la lista de productos en la orden
        actualizarOrden();
    }

    // Función para quitar un producto de la orden
    function quitarProducto(event) {
        const index = Array.from(event.target.parentElement.parentElement.children).indexOf(event.target.parentElement);
        ordenProductos.splice(index, 1);
        actualizarOrden();
    }

    // Función para actualizar la lista de productos en la orden y el total
    function actualizarOrden() {
        const ordenLista = document.querySelector('.orden-lista');
        const totalSpan = document.getElementById('total');

        // Limpiar la lista de productos en la orden
        ordenLista.innerHTML = '';

        // Calcular el nuevo total y mostrarlo
        let total = 0;

        ordenProductos.forEach(function (producto) {
            total += producto.precio;
            const productoHTML = `
                <div class="productoOrden">
                    <div class="info">
                        <span class="nombre">${producto.nombre}</span>
                        <div class="detalle">
                            <span class="precio">$${producto.precio.toFixed(2)}</span>
                        </div>
                    </div>
                    <button class="quitar">Quitar</button>
                </div>
            `;
            ordenLista.innerHTML += productoHTML;
        });

        // Mostrar el nuevo total
        totalSpan.textContent = `$${total.toFixed(2)}`;

        // Asignar eventos a los botones de quitar
        const quitarBotones = document.querySelectorAll('.quitar');
        quitarBotones.forEach(function (boton) {
            boton.addEventListener('click', quitarProducto);
        });
    }

    // Asignar evento a los botones de agregar
    agregarBotones.forEach(function (boton) {
        boton.addEventListener('click', agregarProducto);
    });

    // Función para manejar el evento de pagar
    function pagarOrden() {
        // Convertir los productos agregados a la orden a formato JSON
        const ordenJSON = JSON.stringify(ordenProductos);

        // Guardar el JSON en la sesión
        sessionStorage.setItem('ordenJSON', ordenJSON);

        // Redirigir a la página de Total
        window.location.href = 'OrdenTotal.html';
    }

    // Asignar evento al botón de pagar
    const pagarBoton = document.querySelector('.pagar');
    pagarBoton.addEventListener('click', pagarOrden);

});
