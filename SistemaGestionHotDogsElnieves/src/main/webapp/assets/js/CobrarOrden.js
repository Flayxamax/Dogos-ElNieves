document.addEventListener('DOMContentLoaded', function () {
    let ordenProductos = [];

    const agregarBotones = document.querySelectorAll('.agregar');
    const quitarBotones = document.querySelectorAll('.quitar');

    function agregarProducto(event) {
        const idProducto = event.target.parentElement.querySelector('.nombre').getAttribute('value');
        const nombreProducto = event.target.parentElement.querySelector('.nombre').textContent;
        const precioProducto = parseFloat(event.target.parentElement.querySelector('.precio').textContent.replace('$', ''));

        const nuevoProducto = {
            id: idProducto,
            nombre: nombreProducto,
            precio: precioProducto
        };

        ordenProductos.push(nuevoProducto);
        actualizarOrden();
    }

    function quitarProducto(event) {
        let confirmar = confirm("¿Estás seguro de que deseas quitar este producto?");
        if (confirmar) {
            const index = Array.from(event.target.parentElement.parentElement.children).indexOf(event.target.parentElement);
            ordenProductos.splice(index, 1);
            actualizarOrden();
        } else {
            return;
        }

    }

    function actualizarOrden() {
        const ordenLista = document.querySelector('.orden-lista');
        const totalSpan = document.getElementById('total');

        ordenLista.innerHTML = '';

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

        totalSpan.textContent = `$${total.toFixed(2)}`;

        const quitarBotones = document.querySelectorAll('.quitar');
        quitarBotones.forEach(function (boton) {
            boton.addEventListener('click', quitarProducto);
        });
    }

    agregarBotones.forEach(function (boton) {
        boton.addEventListener('click', agregarProducto);
    });

    function pagarOrden() {

        if (ordenProductos.length === 0) {
            alert("No hay productos en la orden para pagar.");
            return; 
        }
        const ordenJSON = JSON.stringify({
            productos: ordenProductos
        });

        sessionStorage.setItem('ordenJSON', ordenJSON);
        window.location.href = 'OrdenTotal.html';
    }

    const pagarBoton = document.querySelector('.pagar');
    pagarBoton.addEventListener('click', pagarOrden);

    const navButtons = document.querySelectorAll('.nav-button');

    navButtons.forEach(button => {
        button.addEventListener('click', function () {
            const categoria = button.dataset.categoria;
            const productos = document.querySelectorAll('.producto');

            productos.forEach(producto => {
                if (categoria === 'todo' || producto.dataset.categoria === categoria) {
                    producto.style.display = 'block';
                } else {
                    producto.style.display = 'none';
                }
            });
        });
    });


});
