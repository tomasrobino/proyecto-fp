


//----------------------------------------------------------------
// Sé que no hace falta entregar javascript hasta el 23/4, pero si no no podía mostrar bien los botones de filtros y subfiltros
// y la parte estética de la paginación
//----------------------------------------------------------------



// Javascript para el filtrado
document.addEventListener('DOMContentLoaded', function() {
    // Botones de filtro principal
    const filtrosBtns = document.querySelectorAll('.filtro-btn');
    // Elementos de subtipo
    const subtipoTurismo = document.getElementById('subtipo-turismo');
    const subtipoFurgoneta = document.getElementById('subtipo-furgoneta');
    // Botones de subfiltro
    const filtrosSubtipoBtns = document.querySelectorAll('.filtro-subtipo-btn');
    // Tarjetas de vehículos
    const vehiculos = document.querySelectorAll('.vehiculo-card');

    // Función para ocultar todos los subtipo-filtros
    function ocultarSubtipos() {
        subtipoTurismo.style.display = 'none';
        subtipoFurgoneta.style.display = 'none';
    }

    // Función para aplicar el filtrado basado en el estado actual
    function aplicarFiltrado() {
        // Encuentra el filtro principal activo (si hay alguno)
        const filtroPrincipalActivoEl = document.querySelector('.filtro-btn.active');
        const filtroPrincipalActivo = filtroPrincipalActivoEl ? filtroPrincipalActivoEl.getAttribute('data-filter') : 'todos';

        // Encuentra el subfiltro activo (si hay alguno)
        const subfiltroActivoEl = document.querySelector('.filtro-subtipo-btn.active');
        const subfiltroActivo = subfiltroActivoEl ? subfiltroActivoEl.getAttribute('data-subfilter') : null;

        // Filtra los vehículos
        vehiculos.forEach(vehiculo => {
            const tipoVehiculo = vehiculo.getAttribute('data-tipo');
            const subtipoVehiculo = vehiculo.getAttribute('data-subtipo');

            if (filtroPrincipalActivo === 'todos') {
                // Si el filtro principal es "todos", mostrar todos los vehículos
                vehiculo.style.display = 'block';
            } else if (subfiltroActivo) {
                // Si hay un subfiltro activo, mostrar solo los vehículos que coinciden con tipo y subtipo
                if (tipoVehiculo === filtroPrincipalActivo && subtipoVehiculo === subfiltroActivo) {
                    vehiculo.style.display = 'block';
                } else {
                    vehiculo.style.display = 'none';
                }
            } else {
                // Si no hay subfiltro activo, mostrar todos los del tipo principal
                if (tipoVehiculo === filtroPrincipalActivo) {
                    vehiculo.style.display = 'block';
                } else {
                    vehiculo.style.display = 'none';
                }
            }
        });
    }

    // Manejo de filtros principales
    filtrosBtns.forEach(btn => {
        btn.addEventListener('click', () => {
            const filtroCliqueado = btn.getAttribute('data-filter');
            const yaActivo = btn.classList.contains('active');

            // Quita la clase active de todos los botones principales
            filtrosBtns.forEach(b => b.classList.remove('active'));

            // Si el botón ya estaba activo, lo desactivamos y activamos "todos"
            if (yaActivo && filtroCliqueado !== 'todos') {
                document.querySelector('.filtro-btn[data-filter="todos"]').classList.add('active');
            } else {
                // Si no estaba activo, lo activo
                btn.classList.add('active');
            }

            // Obtiene el nuevo filtro activo
            const filtroActivo = document.querySelector('.filtro-btn.active').getAttribute('data-filter');

            // Oculta todos los subtipo-filtros
            ocultarSubtipos();

            // Muestra el subtipo correspondiente si es necesario
            if (filtroActivo === 'turismo') {
                subtipoTurismo.style.display = 'block';
            } else if (filtroActivo === 'furgoneta') {
                subtipoFurgoneta.style.display = 'block';
            }

            // Restablece la selección de subfiltros - quita la clase active de todos los subfiltros
            filtrosSubtipoBtns.forEach(subBtn => subBtn.classList.remove('active'));

            // Aplica el filtrado
            aplicarFiltrado();
        });
    });

    // Manejo de subfiltros
    filtrosSubtipoBtns.forEach(subBtn => {
        subBtn.addEventListener('click', () => {
            const isAlreadyActive = subBtn.classList.contains('active');

            // Quita la clase active de TODOS los botones de subfiltro
            filtrosSubtipoBtns.forEach(sb => sb.classList.remove('active'));

            // Si no estaba activo anteriormente, lo activamos
            if (!isAlreadyActive) {
                subBtn.classList.add('active');
            }
            // Si estaba activo, ya lo hemos desactivado y no hacemos nada más

            // Aplica el filtrado
            aplicarFiltrado();
        });
    });

    // Activa el filtro "Todos" por defecto
    document.querySelector('.filtro-btn[data-filter="todos"]').click();
});