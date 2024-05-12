document.addEventListener('DOMContentLoaded', function () {
    const modificarBotones = document.querySelectorAll('.modificar');

    modificarBotones.forEach(function (boton) {
        boton.addEventListener('click', function () {
            const producto = boton.closest('.producto');

            const idProducto = producto.dataset.id;

            fetch(`DetalleProducto?id=${encodeURIComponent(idProducto)}`)
                .then(response => {
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
        });
    });

    const botonAgregar = document.getElementsByClassName('agregar')[0];

    botonAgregar.addEventListener('click', function () {
        window.location.href = 'agregarProducto.jsp';
    });
});
