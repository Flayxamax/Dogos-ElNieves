document.addEventListener('DOMContentLoaded', function () {

    cargarCategorias();
});

function cargarCategorias() {
    fetch('http://localhost:8080/SistemaGestionHotDogsElnieves/Categorias')
            .then(response => response.json())
            .then(data => {

                var categoriaSelect = document.getElementById('categoria');

                categoriaSelect.innerHTML = '';

                var categoriaProducto = document.getElementById('categoriaProducto').value;

                data.forEach(categoria => {
                    var option = document.createElement('option');
                    option.value = categoria;
                    option.text = categoria;

                    if (categoria === categoriaProducto) {
                        option.selected = true;
                    }

                    categoriaSelect.appendChild(option);
                });
            })
            .catch(error => console.error('Error al cargar las categorías:', error));
}

function modificarProducto() {
    var nombreInput = document.querySelector('input[type="text"][name="nombre"]');
    var precioInput = document.querySelector('input[type="text"][name="precio"]');
    var categoriaSelect = document.getElementById('categoria');
    var idProducto = document.getElementById('idProducto').value;

    nombreInput.removeAttribute('readonly');
    precioInput.removeAttribute('readonly');
    categoriaSelect.removeAttribute('disabled');

    document.getElementById('botonGuardar').style.display = 'block';
    document.getElementById('botonCancelar').style.display = 'block';
    document.getElementById('botonRegresar').style.display = 'block';

    document.getElementById('botonModificar').style.display = 'none';
    document.getElementById('botonEliminar').style.display = 'none';
    document.getElementById('botonRegresarAtras').style.display = 'none';
}


function guardarProducto() {
    var nuevoNombre = document.querySelector('input[type="text"][name="nombre"]').value;
    var nuevoPrecio = document.querySelector('input[type="text"][name="precio"]').value;
    var nuevaCategoria = document.getElementById('categoria').value;
    var idProducto = document.getElementById('idProducto').value;

    var data = {
        id: idProducto,
        nombre: nuevoNombre,
        precio: nuevoPrecio,
        categoria: nuevaCategoria
    };

    fetch('http://localhost:8080/SistemaGestionHotDogsElnieves/DetalleProducto', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(response => {
        if (!response.ok) {
            if (response.status === 400) {
                throw new Error('Error: El servidor no pudo procesar la solicitud correctamente.');
            } else {
                throw new Error('Error al modificar el producto');
            }
        }
        return response.json();
    })
    .then(productoModificado => {
        document.querySelector('input[name="nombre"]').value = productoModificado.nombre;
        document.querySelector('input[name="precio"]').value = productoModificado.precio;
        document.getElementById('categoria').value = productoModificado.categoria;

        alert('Producto modificado exitosamente');

        window.location.reload();
    })
    .catch(error => {
        console.error('Error al modificar el producto:', error);
        if (error.message === 'Error: El servidor no pudo procesar la solicitud correctamente.') {
            alert('Error: Los datos enviados no son válidos.');
        }
    });
}


function eliminarProducto() {
    var idProducto = document.getElementById('idProducto').value;

    fetch(`http://localhost:8080/SistemaGestionHotDogsElnieves/DetalleProducto?id=${encodeURIComponent(idProducto)}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        }
    })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Error al eliminar el producto');
                }
                return response.text();
            })
            .then(responseData => {
                alert('Producto eliminado exitosamente');

                window.location.href = 'http://localhost:8080/SistemaGestionHotDogsElnieves/Productos';
            })
            .catch(error => console.error('Error al eliminar el producto:', error));
}

function cancelarEdicion() {

    document.querySelector('input[type="text"][name="nombre"]').setAttribute('readonly', true);
    document.querySelector('input[type="text"][name="precio"]').setAttribute('readonly', true);
    document.getElementById('categoria').setAttribute('disabled', true);

    document.getElementById('botonGuardar').style.display = 'none';
    document.getElementById('botonCancelar').style.display = 'none';

    document.getElementById('botonModificar').style.display = 'block';
    document.getElementById('botonEliminar').style.display = 'block';

    window.location.reload();
}

function regresar() {
    window.location.reload();
}

function regresarAtras() {
    window.location.href = 'http://localhost:8080/SistemaGestionHotDogsElnieves/Productos';
}
