document.addEventListener('DOMContentLoaded', function () {
    // Función para obtener el tipo de pago seleccionado desde el HTML
    function obtenerTipoPagoSeleccionado() {
        const botonPagoEfectivo = document.getElementById('pagoEfectivo');
        const botonPagoTarjeta = document.getElementById('pagoTarjeta');

        if (botonPagoEfectivo.checked) {
            return "efectivo";
        } else if (botonPagoTarjeta.checked) {
            return "tarjeta";
        } else {
            return null;
        }
    }

    // Función para obtener la orden de la sesión
    function obtenerOrdenDesdeSesion() {
        // Obtener el JSON de la sesión
        const ordenJSON = sessionStorage.getItem('ordenJSON');
        if (!ordenJSON) {
            console.error("No se encontró ninguna orden en la sesión.");
            return [];
        }
        // Convertir el JSON a objeto JavaScript
        return JSON.parse(ordenJSON);
    }

    // Función para mostrar la orden en la página
    function mostrarOrden(orden) {
        const productosOrden = document.querySelector('.productos-orden');
        const totalElement = document.querySelector('.total');

        // Limpiar la lista de productos en la orden
        productosOrden.innerHTML = '';

        // Calcular el nuevo total y mostrarlo
        let total = 0;
        productos = orden.productos;
        productos.forEach(function (producto) {
            total += producto.precio;
            const productoHTML = `
                <div class="producto-resumen">
                    <span class="nombre">${producto.nombre}</span>
                </div>
            `;
            productosOrden.innerHTML += productoHTML;
        });

        // Mostrar el nuevo total
        totalElement.textContent = `Total: $${total.toFixed(2)}`;

        // Devolver el total calculado
        return total;
    }

    // Función para calcular el cambio y actualizarlo en tiempo real
    function calcularCambio(total) {
        const montoPagadoInput = document.getElementById('monto');
        const cambioSpan = document.getElementById('cambio');
        montoPagadoInput.addEventListener('input', function () {
            const montoPagado = parseFloat(montoPagadoInput.value);
            const cambio = montoPagado - total;
            cambioSpan.textContent = `$${cambio.toFixed(2)}`;
        });
    }
    
    function validarNumero(evento) {
    evento.value = evento.value.replace(/[^0-9]/g,"");
}

    // Función para enviar la orden al servidor
    function enviarOrdenAlServidor(orden) {
        fetch('Venta', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(orden)
        })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Error al enviar la orden al servidor.');
                    }
                    // Aquí puedes manejar la respuesta del servidor si es necesario
                    return response.json();
                })
                .then(data => {
                    // Aquí puedes manejar la respuesta del servidor si es necesario
                    console.log('Orden enviada exitosamente:', data);
                })
                .catch(error => {
                    console.error('Error:', error);
                });
    }

    // Función para finalizar la orden
    function finalizarOrden(total) {
        const montoPagadoInput = document.getElementById('monto');
        const montoPagado = parseFloat(montoPagadoInput.value);
        const cambio = montoPagado - total;
        const cambioSpan = document.getElementById('cambio');
        const orden = obtenerOrdenDesdeSesion();
        const tipoPago = "efectivo";
        
        const cambioNumerico = parseFloat(cambioSpan.textContent.replace('$', ''));

        // Verificar si el cambio es menor que cero
        if (montoPagado < total || isNaN(cambio)) {
            alert("El monto pagado es menor que el total de la orden. Por favor, ingrese un monto válido.");
            return; // Detener la ejecución si el cambio es negativo
        }

        // Agregar el tipo de pago al objeto de la orden
        orden.tipoPago = tipoPago;

        // Guardar la orden en el servidor
        enviarOrdenAlServidor(orden);

        // Restaurar la sesión
        sessionStorage.removeItem('ordenJSON');

        // Redirigir o mostrar un mensaje de éxito
        alert(`Orden finalizada. Tipo de Pago: ${tipoPago}. Cambio: $${cambio.toFixed(2)}`);

        window.location.href = 'Venta';
    }

    function finalizarOrdenTarjeta(total) {
        const montoPagadoInput = document.getElementById('monto');
        const montoPagado = parseFloat(montoPagadoInput.value);
        const cambio = montoPagado - total;
        const orden = obtenerOrdenDesdeSesion();
        const tipoPago = "tarjeta";
        const productosOrden = document.querySelector('.productos-orden');
        
        if(productosOrden.lenght===0){
            alert("No hay productos en la orden para pagar.");
            return;
        }

        // Agregar el tipo de pago al objeto de la orden
        orden.tipoPago = tipoPago;

        // Guardar la orden en el servidor
        enviarOrdenAlServidor(orden);

        // Restaurar la sesión
        sessionStorage.removeItem('ordenJSON');

        // Redirigir o mostrar un mensaje de éxito
        alert(`Orden finalizada. Tipo de Pago: ${tipoPago}.`);
        window.location.href = 'Venta';
    }

    // Obtener la orden de la sesión
    const orden = obtenerOrdenDesdeSesion();

    // Mostrar la orden en la página y obtener el total
    const total = mostrarOrden(orden);

    // Calcular y mostrar el cambio en tiempo real
    calcularCambio(total);

    // Asignar evento al botón de finalizar orden
    const finalizarOrdenBoton = document.querySelector('.finalizarOrden');
    finalizarOrdenBoton.addEventListener('click', function () {
        finalizarOrden(total);
    });

    const finalizarOrdenTBoton = document.querySelector('.finalizarOrdenTarjeta');
    finalizarOrdenTBoton.addEventListener('click', function () {
        finalizarOrdenTarjeta(total);
    });
});
