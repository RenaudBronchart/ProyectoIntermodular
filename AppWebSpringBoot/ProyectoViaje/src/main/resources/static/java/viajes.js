// viajes.js
export function cargarViajes(viajesContainer) {
    fetch('/viajes')
        .then(response => response.json())
        .then(data => {
            viajesContainer.innerHTML = '';
            data.forEach(viaje => {
                const tr = document.createElement('tr');
                tr.innerHTML = `
                    <td>${viaje.id}</td>
                    <td>${viaje.destinacion}</td>
                    <td>${viaje.fecha}</td>
                    <td><button class="eliminar" data-id="${viaje.id}">Eliminar</button></td>
                    <td><button class="editar" data-id="${viaje.id}">Editar</button></td>
                `;
                viajesContainer.appendChild(tr);
            });
        });
}

export function eliminarViaje(id) {
    fetch(`/viajes/${id}`, { method: 'DELETE' })
        .then(() => cargarViajes());
}