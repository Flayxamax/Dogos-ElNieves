document.addEventListener('DOMContentLoaded', function () {

    cargarTodasCategorias();
});


function cargarTodasCategorias() {
    fetch('http://localhost:8080/SistemaGestionHotDogsElnieves/Categorias')
        .then(response => response.json())
        .then(data => {
            var categoriaSelect = document.getElementById('categoria');

            categoriaSelect.innerHTML = '';

            data.forEach(categoria => {
                var option = document.createElement('option');
                option.value = categoria;
                option.text = categoria;
                categoriaSelect.appendChild(option);
            });
        })
        .catch(error => console.error('Error al cargar todas las categorías:', error));
}

function agregarProducto() {
    var nombre = document.getElementById('nombre').value;
    var precio = parseFloat(document.getElementById('precio').value);
    var categoria = document.getElementById('categoria').value;

    if (!nombre || isNaN(precio) || precio <= 0 || !categoria) {
        alert('Por favor, complete todos los campos correctamente.');
        return;
    }

    var nuevoProducto = {
        nombre: nombre,
        precio: precio,
        categoria: categoria
    };

    fetch('http://localhost:8080/SistemaGestionHotDogsElnieves/DetalleProducto', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(nuevoProducto)
    })
        .then(response => {
            if (response.ok) {
                alert('Producto creado exitosamente');
                window.location.href = 'http://localhost:8080/SistemaGestionHotDogsElnieves/Productos';
            } else {
                throw new Error('Error al crear el producto');
            }
        })
        .catch(error => console.error('Error al crear el producto:', error));
}

function cancelar() {
    window.location.href = 'http://localhost:8080/SistemaGestionHotDogsElnieves/Productos';
}
