document.addEventListener('DOMContentLoaded', function () {
    const modificarBotones = document.querySelectorAll('.modificar');
    const productosContainer = document.querySelector('.productos-container');

productosContainer.addEventListener('click', function (event) {
        if (event.target.classList.contains('modificar')) {
            const boton = event.target;
            const producto = boton.closest('.producto');
            const idProducto = producto.dataset.id;

            fetch(`DetalleProducto?id=${encodeURIComponent(idProducto)}`)
                .then(response =>{
                    if (!response.ok) {
                        throw new Error('Error al obtener el detalle del producto');
                    }
                    return response.text();
                })
                .then(data => {
                    window.location.href = 'DetalleProducto?id=' + encodeURIComponent(idProducto);
                })
                .catch(error => {
                    console.error('Error:', error);
                });
        }
    });

    const botonAgregar = document.getElementsByClassName('agregar')[0];

    botonAgregar.addEventListener('click', function () {
        window.location.href = 'agregarProducto.jsp';
    });

    const navButtons = document.querySelectorAll('.nav-button');
    const productosOriginales = document.querySelectorAll('.producto');

    navButtons.forEach(button => {
        button.addEventListener('click', function () {
            const categoria = button.dataset.categoria;
            const productos = [];

            productosOriginales.forEach(producto => {
                if (categoria === 'todo' || producto.dataset.categoria === categoria) {
                    productos.push(producto.outerHTML);
                }
            });

            const productosContainer = document.querySelector('.productos-container');
            productosContainer.innerHTML = `
            <table class="tabla-productos">
                <thead>
                    <tr>
                        <th>Nombre</th>
                        <th>Precio</th>
                        <th>Modificar</th>
                    </tr>
                </thead>
                <tbody>
                    ${productos.join('')}
                </tbody>
            </table>
        `;
        });
    });

});
