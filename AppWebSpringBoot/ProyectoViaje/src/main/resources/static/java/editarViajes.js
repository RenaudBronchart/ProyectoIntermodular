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
