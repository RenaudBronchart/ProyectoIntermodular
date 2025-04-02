// app.js
import { cargarViajes } from './modules/viajes.js';
import { handleFormSubmit } from './modules/form.js';
import { editarViaje } from './modules/editar.js';
import { resetForm } from './modules/utils.js';

document.addEventListener('DOMContentLoaded', function () {
    const viajesContainer = document.getElementById('viaje-body');
    const viajeForm = document.getElementById('viaje-form');
    let viajeIdToEdit = null;

    // Charger les voyages au démarrage
    cargarViajes(viajesContainer);

    // Écouter la soumission du formulaire
    viajeForm.addEventListener('submit', (event) => handleFormSubmit(event, viajeForm, viajeIdToEdit));
});



