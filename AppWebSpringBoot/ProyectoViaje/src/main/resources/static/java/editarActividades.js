// editarViaje.js
import { cargarViajes } from './viajes.js';

export function editarViaje(id) {
    fetch(`/viajes/${id}`)
        .then(response => response.json())
        .then(viaje => {
            document.getElementById('viaje-destinacion').value = viaje.destinacion;
            document.getElementById('viaje-fecha').value = viaje.fecha;
        });
}

// editarViaje.js
import { cargarActividades } from './actividades.js';

export function editarActividad(id) {
    fetch(`/actividades/${id}`)
        .then(response => response.json())
        .then(actividad => {
            document.getElementById('actividad-nombre').value = actividad.nombre;
            document.getElementById('actividad-lugar').value = actividad.lugar;
			document.getElementById('actividad-precio').value = actividad.precio;
			document.getElementById('actividad-notes').value = actividad.notes;

        });
}