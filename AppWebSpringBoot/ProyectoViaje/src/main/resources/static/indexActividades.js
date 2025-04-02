import { actualizarPrecioTotal } from '/precioTotalPorViaje.js'
document.addEventListener('DOMContentLoaded', function () {
    const actividadesContainer = document.querySelector('.table tbody'); // Sélection du tableau des activités
    const actividadForm = document.querySelector('.form'); // Sélection du formulaire des activités
    const precioTotalElement = document.getElementById('precio-total'); // Élément où afficher le prix total

    let actividadIdToEdit = null; // Variable pour stocker l'ID de l'activité à éditer

    // Fonction pour charger toutes les activités
    function cargarActividades() {
        console.log("Chargement des activités...");// Charger les activités au démarrage
        const urlParams = new URLSearchParams(window.location.search);
        const viajeId = urlParams.get('viajeId'); // Récupère l'ID du voyage dans l'URL

        if (!viajeId) {
            console.error("Erreur : Aucun ID de voyage fourni dans l'URL.");
            return;
        }

        fetch(`/viajes/${viajeId}/actividades`)
            .then(response => response.json())
            .then(data => {
                actividadesContainer.innerHTML = '';
                if (data.length > 0) {
                    data.forEach(actividad => {
                        const tr = document.createElement('tr');
                        tr.innerHTML = `
                            <td>${actividad.id}</td>
                            <td>${actividad.viaje.id}</td>
                            <td>${actividad.nombre}</td>
                            <td>${actividad.numPers}</td>
                            <td>${actividad.precio}€</td>
                            <td>${actividad.totalPrecio} € </td>
                            <td><button class="eliminar" data-id="${actividad.id}">Eliminar</button></td>
                            <td><button class="editar" data-id="${actividad.id}">Editar</button></td>
                        `;
                        actividadesContainer.appendChild(tr);
                    });

                    // Ajouter des écouteurs pour les boutons "Eliminar"
                    document.querySelectorAll('.eliminar').forEach(button => {
                        button.addEventListener('click', function () {
                            const actividadId = this.getAttribute('data-id');
                            eliminarActividad(actividadId);
                        });
                    });

                    // Ajouter des écouteurs pour les boutons "Editar"
                    document.querySelectorAll('.editar').forEach(button => {
                        button.addEventListener('click', function () {
                            const actividadId = this.getAttribute('data-id');
                            editarActividad(actividadId);
                        });
                    });
                } else {
                    actividadesContainer.innerHTML = '<tr><td colspan="8">No hay actividades disponibles para este viaje.</td></tr>';
                }

                // Appel de la fonction actualizarPrecioTotal après avoir chargé les activités
                console.log("Appel de la fonction actualizarPrecioTotal après le chargement des activités");
                actualizarPrecioTotal();
            })
            .catch(err => console.error('Erreur lors du chargement des activités :', err));
    }

    // Fonction pour ajouter ou mettre à jour une activité
    actividadForm.addEventListener('submit', function (event) {
        event.preventDefault();

        const nombre = document.getElementById('actividad-nombre').value;
        const numPers = parseInt(document.getElementById('actividad-numPers').value);
        const precio = parseFloat(document.getElementById('actividad-precio').value);
        const viajeId = new URLSearchParams(window.location.search).get('viajeId');

        const nuevaActividad = {
            nombre,
            numPers,
            precio,
            viaje: { id: viajeId },
        };

        const url = actividadIdToEdit
            ? `/actividades/${actividadIdToEdit}` // URL pour modifier une activité
            : '/actividades'; // URL pour ajouter une nouvelle activité

        const method = actividadIdToEdit ? 'PUT' : 'POST'; // Méthode HTTP en fonction du contexte

        fetch(url, {
            method: method,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(nuevaActividad),
        })
            .then(() => {
                cargarActividades(); // Recharge les activités après l'opération
                resetForm(); // Réinitialise le formulaire
                actualizarPrecioTotal(); // Met à jour le prix total après l'opération
            })
            .catch(err => console.error(`Erreur lors de la ${actividadIdToEdit ? 'modification' : 'création'} de l'activité :`, err));
    });

    // Fonction pour supprimer une activité
    function eliminarActividad(id) {
        fetch(`/actividades/${id}`, {
            method: 'DELETE',
        })
            .then(() => {
                cargarActividades(); // Recharge les activités après la suppression
                actualizarPrecioTotal(); // Met à jour le prix total après suppression
            })
            .catch(err => console.error('Erreur lors de la suppression de l\'activité :', err));
    }

    // Fonction pour éditer une activité
    function editarActividad(id) {
        fetch(`/actividades/${id}`)
            .then(response => response.json())
            .then(actividad => {
                // Remplir les champs du formulaire avec les données existantes
                document.getElementById('actividad-nombre').value = actividad.nombre;
                document.getElementById('actividad-numPers').value = actividad.numPers;
                document.getElementById('actividad-precio').value = actividad.precio;

                // Modifier le texte du bouton pour indiquer l'édition
                const submitButton = document.querySelector('.form button[type="submit"]');
                submitButton.textContent = 'Guardar Cambios';

                // Enregistrer l'ID de l'activité à éditer
                actividadIdToEdit = id;
            })
            .catch(err => console.error('Erreur lors du chargement des données de l\'activité :', err));
    }

    // Fonction pour réinitialiser le formulaire
    function resetForm() {
        actividadForm.reset();
        const submitButton = document.querySelector('.form button[type="submit"]');
        submitButton.textContent = 'Agregar Actividad'; // Remettre le texte original du bouton
        actividadIdToEdit = null; // Réinitialiser l'ID
    }
	
	
	    // Charger les activités au démarrage
	    cargarActividades();
	});