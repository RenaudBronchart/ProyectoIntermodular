document.addEventListener('DOMContentLoaded', function () {
    const viajesContainer = document.querySelector('.table tbody'); // Sélection du tableau des voyages
    const viajeForm = document.querySelector('.form'); // Sélection du formulaire des voyages
    let viajeIdToEdit = null; // Variable pour stocker l'ID du voyage à éditer

    // Fonction pour charger tous les voyages
    function cargarViajes() {
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
                        <td><a href="indexActividades.html?viajeId=${viaje.id}"  class="ver-actividades">Ver Actividades</a></td>

                    `;
                    viajesContainer.appendChild(tr);
                });

                // Ajouter des écouteurs pour les boutons "Eliminar"
                document.querySelectorAll('.eliminar').forEach(button => {
                    button.addEventListener('click', function () {
                        const viajeId = this.getAttribute('data-id');
                        eliminarViaje(viajeId);
                    });
                });

                // Ajouter des écouteurs pour les boutons "Editar"
                document.querySelectorAll('.editar').forEach(button => {
                    button.addEventListener('click', function () {
                        const viajeId = this.getAttribute('data-id');
                        editarViaje(viajeId);
                    });
                });
            });
    }

    // Fonction pour ajouter ou mettre à jour un voyage
    viajeForm.addEventListener('submit', function (event) {
        event.preventDefault();

        const destinacion = document.getElementById('viaje-destinacion').value;
        const fecha = document.getElementById('viaje-fecha').value;

        const nuevoViaje = {
            destinacion,
            fecha,
        };

        if (viajeIdToEdit !== null) {
            // Si nous sommes en mode édition, envoyer une requête PUT
            fetch(`/viajes/${viajeIdToEdit}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(nuevoViaje),
            })
                .then(() => {
                    cargarViajes(); // Recharger la liste des voyages
                    resetForm(); // Réinitialiser le formulaire
                });
        } else {
            // Sinon, envoyer une requête POST pour ajouter un nouveau voyage
            fetch('/viajes', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(nuevoViaje),
            })
                .then(() => {
                    cargarViajes(); // Recharger la liste des voyages
                    resetForm(); // Réinitialiser le formulaire
                });
        }
    });

    // Fonction pour supprimer un voyage
    function eliminarViaje(id) {
        fetch(`/viajes/${id}`, {
            method: 'DELETE',
        }).then(() => cargarViajes());
    }

    // Fonction pour éditer un voyage
    function editarViaje(id) {
        // Récupérer les informations existantes
        fetch(`/viajes/${id}`)
            .then(response => response.json())
            .then(viaje => {
                // Remplir les champs du formulaire avec les données existantes
                document.getElementById('viaje-destinacion').value = viaje.destinacion;
                document.getElementById('viaje-fecha').value = viaje.fecha;

                // Modifier le texte du bouton pour indiquer l'édition
                const submitButton = document.querySelector('.form button[type="submit"]');
                submitButton.textContent = 'Guardar Cambios';

                // Enregistrer l'ID du voyage à éditer
                viajeIdToEdit = id;
            });
    }

    // Fonction pour réinitialiser le formulaire
    function resetForm() {
        viajeForm.reset();
        const submitButton = document.querySelector('.form button[type="submit"]');
        submitButton.textContent = 'Agregar Viaje'; // Remettre le texte original du bouton
        viajeIdToEdit = null; // Réinitialiser l'ID
    }

    // Charger les voyages au démarrage
    cargarViajes();
});
