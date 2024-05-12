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
            // Si la respuesta es un archivo PDF, descárgalo en lugar de interpretarlo como JSON
            response.blob().then(blob => {
                const url = window.URL.createObjectURL(blob);
                const a = document.createElement('a');
                a.href = url;
                a.download = 'ReporteVenta.pdf';
                document.body.appendChild(a);
                a.click();
                window.URL.revokeObjectURL(url);
            });
        })
        .catch(error => {
            console.error('Error:', error);
        });
}
