function regresar() {
    // Código para regresar o cualquier acción necesaria
}

function enviarFechas() {
    const desde = document.getElementById('desde').value;
    const hasta = document.getElementById('hasta').value;

    if (desde.trim() === '' || hasta.trim() === '') {
        alert('Por favor ingrese ambas fechas.');
        return;
    }

    const fechas = {
        desde: desde,
        hasta: hasta
    };

    fetch('ReporteVenta', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(fechas)
    })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Error al enviar las fechas.');
                }
                return response.blob();
            })
            .then(blob => {
                const url = window.URL.createObjectURL(blob);
                const iframe = document.querySelector('iframe');
                iframe.src = url;
            })
            .catch(error => {
                console.error('Error:', error);
            });
}
