import { cargarActividades, eliminarActividad } from './actividades.js';
import { handleFormSubmit } from './formActividades.js';

document.addEventListener('DOMContentLoaded', function () {
    const actividadesContainer = document.getElementById('actividades-body');
    const actividadForm = document.getElementById('actividad-form');
    const urlParams = new URLSearchParams(window.location.search);
    const viajeId = urlParams.get('viajeId');

    if (!viajeId) {
        console.error("viajeId manquant dans l'URL");
        return;
    }

    // Charger les activités pour le voyage
    cargarActividades(actividadesContainer, viajeId);

    // Soumission du formulaire pour ajouter une activité
    actividadForm.addEventListener('submit', (event) => {
        handleFormSubmit(event, actividadForm, viajeId, actividadesContainer);
    });

    // Suppression d'une activité
    actividadesContainer.addEventListener('click', (event) => {
        if (event.target.classList.contains('eliminar')) {
            const actividadId = event.target.getAttribute('data-id');
            eliminarActividad(actividadId, actividadesContainer, viajeId);
        }
    });
});