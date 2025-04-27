document.addEventListener("DOMContentLoaded", function () {
    // Sección de vehículos donde se agregarán las tarjetas
    const vehiculosLista = document.querySelector('.grupo-tarjetas.vehiculos-lista');

    // Carga los datos del archivo JSON utilizando fetch
    //La URL debería ser reemplazada por la del servidor
    fetch('../../../programacion/json/vehiculos.json', {
        //Headers para CORS. En un navegador moderno no hay realmente manera de evitar usar un servidor local
        //porque mode: no-cors no permite cargar el contenido
        headers: {
            'Access-Control-Allow-Origin': '*',
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            console.log(response.body)
            return response.json()
        })     // Convertir la respuesta a JSON
        .then(data => {
            // Limpiar la sección de vehículos antes de agregar nuevas tarjetas
            vehiculosLista.innerHTML = '';

            // Recorrer cada vehículo y crear una tarjeta para cada uno
            data.forEach(vehiculo => {
                if (vehiculo.alquilado===true) {
                    // Crear el elemento tarjeta
                    const tarjeta = document.createElement('div');
                    tarjeta.classList.add('tarjeta', 'vehiculo-card');
                    tarjeta.setAttribute('data-tipo', vehiculo.tipo);

                    // Si tiene subtipo (por ejemplo, turismo - pequeño)
                    if (vehiculo.subtipo) {
                        tarjeta.setAttribute('data-subtipo', vehiculo.subtipo.toLowerCase());
                    }

                    // Crear el contenido de la tarjeta
                    tarjeta.innerHTML = `
                    <h3>${vehiculo.marca} ${vehiculo.modelo}</h3>
                    <p class="vehiculo-tipo">${vehiculo.tipo} ${vehiculo.subtipo ? '- ' + vehiculo.subtipo : ''}</p>
                    <div class="vehiculo-detalles">
                        <p><strong>Precio:</strong> Desde ${vehiculo.precio}€/día</p>
                        <p><strong>Alquilado por:</strong>{vehiculo.cliente}</p>
                    </div
                    `;

                    // Añadir la tarjeta a la lista de vehículos
                    vehiculosLista.appendChild(tarjeta);
                }
            });
        })
        .catch(error => {
            console.error('Error al cargar el archivo JSON:', error);
        });
});