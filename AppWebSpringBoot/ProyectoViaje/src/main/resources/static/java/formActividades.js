import { cargarActividades } from './actividades.js';

export function handleFormSubmit(event, actividadForm, viajeId, actividadesContainer) {
    event.preventDefault();

    const lugar = document.getElementById('actividad-lugar').value;
    const nombre = document.getElementById('actividad-nombre').value;
    const precio = parseInt(document.getElementById('actividad-precio').value);
    const notes = document.getElementById('actividad-notes').value;

    const nuevaActividad = {
        lugar,
        nombre,
        precio,
        notes,
        viaje: { id: viajeId }
    };

    fetch('/actividades', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(nuevaActividad)
    })
    .then(() => {
        cargarActividades(actividadesContainer, viajeId); // Recharge les activités
        actividadForm.reset(); // Réinitialise le formulaire
    })
    .catch(err => console.error('Erreur lors de l\'ajout de l\'activité :', err));
}