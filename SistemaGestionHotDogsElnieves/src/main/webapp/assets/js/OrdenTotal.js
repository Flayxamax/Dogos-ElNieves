document.addEventListener('DOMContentLoaded', function () {
    const regresarBtn = document.getElementById('regresarOrdenesBtn');
    
    regresarBtn.addEventListener('click', function () {
        window.location.href = 'Venta'; 
    });

    const ordenJSON = sessionStorage.getItem('ordenJSON');
    if (ordenJSON) {
        const orden = JSON.parse(ordenJSON);
        const productosOrdenContainer = document.querySelector('.productos-orden');
        let total = 0;
        orden.productos.forEach(producto => {
            const productoDiv = document.createElement('div');
            productoDiv.classList.add('producto-resumen');
            productoDiv.innerHTML = `<span class="nombre">${producto.nombre}</span>`;
            productosOrdenContainer.appendChild(productoDiv);
            total += producto.precio;
        });
        document.getElementById('totalOrden').textContent = `$${total.toFixed(2)}`;
    }

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

    function obtenerOrdenDesdeSesion() {
        const ordenJSON = sessionStorage.getItem('ordenJSON');
        if (!ordenJSON) {
            console.error("No se encontró ninguna orden en la sesión.");
            return [];
        }
        return JSON.parse(ordenJSON);
    }

    function mostrarOrden(orden) {
        const productosOrden = document.querySelector('.productos-orden');
        const totalElement = document.querySelector('.total');
        productosOrden.innerHTML = '';
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
        document.getElementById('totalOrden').textContent = `$${total.toFixed(2)}`;
        return total;
    }

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
        evento.value = evento.value.replace(/[^0-9]/g, "");
    }

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
                    return response.json();
                })
                .then(data => {
                    console.log('Orden enviada exitosamente:', data);
                })
                .catch(error => {
                    console.error('Error:', error);
                });
    }

    function finalizarOrden(total) {
        const montoPagadoInput = document.getElementById('monto');
        const montoPagado = parseFloat(montoPagadoInput.value);
        const cambio = montoPagado - total;
        const cambioSpan = document.getElementById('cambio');
        const orden = obtenerOrdenDesdeSesion();
        const tipoPago = "efectivo";
        const cambioNumerico = parseFloat(cambioSpan.textContent.replace('$', ''));
        if (montoPagado < total || isNaN(cambio)) {
            alert("El monto pagado es menor que el total de la orden. Por favor, ingrese un monto válido.");
            return;
        }


        orden.tipoPago = tipoPago;
        enviarOrdenAlServidor(orden);
        sessionStorage.removeItem('ordenJSON');
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
        if (productosOrden.lenght === 0) {
            alert("No hay productos en la orden para pagar.");
            return;
        }

        orden.tipoPago = tipoPago;
        enviarOrdenAlServidor(orden);
        sessionStorage.removeItem('ordenJSON');
        alert(`Orden finalizada. Tipo de Pago: ${tipoPago}.`);
        window.location.href = 'Venta';
    }

    const orden = obtenerOrdenDesdeSesion();
    const total = mostrarOrden(orden);
    calcularCambio(total);
    const finalizarOrdenBoton = document.querySelector('.finalizarOrden');
    finalizarOrdenBoton.addEventListener('click', function () {
        finalizarOrden(total);
    });
    const finalizarOrdenTBoton = document.querySelector('.finalizarOrdenTarjeta');
    finalizarOrdenTBoton.addEventListener('click', function () {
        finalizarOrdenTarjeta(total);
    });
}
);
