// form.js
//viajes
import { cargarViajes } from './viajes.js';

export function handleFormSubmit(event, viajeForm, viajeIdToEdit) {
    event.preventDefault();
    const destinacion = document.getElementById('viaje-destinacion').value;
    const fecha = document.getElementById('viaje-fecha').value;

    const nuevoViaje = { destinacion, fecha };

    if (viajeIdToEdit !== null) {
        fetch(`/viajes/${viajeIdToEdit}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(nuevoViaje)
        })
        .then(() => cargarViajes());
    } else {
        fetch('/viajes', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(nuevoViaje)
        })
        .then(() => cargarViajes());
    }
}

