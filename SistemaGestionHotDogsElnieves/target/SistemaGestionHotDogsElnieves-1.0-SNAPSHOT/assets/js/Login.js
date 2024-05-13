document.addEventListener('DOMContentLoaded', function () {
    // Aquí puedes realizar cualquier inicialización necesaria al cargar la página
});

function iniciarSesion() {
    var usuario = document.getElementById('usuario').value;
    var contrasenia = document.getElementById('pass').value;

    if (!usuario || !contrasenia) {
        alert('Por favor, complete todos los campos.');
        return;
    }

    var datosInicioSesion = {
        nombre: usuario,
        contrasena: contrasenia
    };

    fetch('Login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(datosInicioSesion)
    })
    .then(response => {
        if (response.ok) {
            // Redirigir al usuario a la página deseada en caso de éxito
            window.location.href = 'Productos';
        } else {
            alert('Error al iniciar sesión');
            throw new Error('Error al iniciar sesión');
        }
    })
    .catch(error => console.error('Error al iniciar sesión:', error));
}

