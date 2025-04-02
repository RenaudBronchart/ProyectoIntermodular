export function cargarActividades(container, viajeId) {
    fetch(`/viajes/${viajeId}/actividades`)
        .then(response => response.json())
        .then(data => {
            container.innerHTML = '';
            if (data.length > 0) {
                data.forEach(actividad => {
                    const tr = document.createElement('tr');
                    tr.innerHTML = `
                        <td>${actividad.id}</td>
                        <td>${actividad.viaje.id}</td>
                        <td>${actividad.nombre}</td>
                        <td>${actividad.lugar}</td>
                        <td>${actividad.precio}</td>
                        <td>${actividad.notes ? actividad.notes : 'No hay notas'}</td>
                        <td><button class="eliminar" data-id="${actividad.id}">Eliminar</button></td>
                        <td><button class="editar" data-id="${actividad.id}">Editar</button></td>
                    `;
                    container.appendChild(tr);
                });
            } else {
                container.innerHTML = '<tr><td colspan="6">No hay actividades para este viaje.</td></tr>';
            }
        })
        .catch(err => console.error('Erreur lors du chargement des activités :', err));
}

export function eliminarActividad(id, container, viajeId) {
    fetch(`/actividades/${id}`, {
        method: 'DELETE'
    })
    .then(() => cargarActividades(container, viajeId))
    .catch(err => console.error('Erreur lors de la suppression de l\'activité :', err));
}



