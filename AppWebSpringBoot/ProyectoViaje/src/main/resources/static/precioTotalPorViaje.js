export function actualizarPrecioTotal() {
    const precioTotalElement = document.getElementById('precio-total');
    console.log("Appel à l'API pour le prix total");

    // Récupérer l'ID du voyage à partir de l'URL
    const urlParams = new URLSearchParams(window.location.search);
    const viajeId = urlParams.get('viajeId'); 

    if (!viajeId) {
        console.error("Erreur : Aucun ID de voyage fourni dans l'URL.");
        return; // Arrêter l'exécution si aucun ID de voyage n'est trouvé
    }

    // Envoi de la requête avec le paramètre viajeId
    fetch(`/actividades/totalPrecioViaje?viajeId=${viajeId}`)
        .then(response => response.json())
        .then(data => {
            console.log("Prix total récupéré :", data); // Vérifie la réponse du serveur

            // Vérifiez que la donnée est bien un nombre
            if (typeof data === 'number') {
                console.log("Prix total valide : ", data);
                precioTotalElement.textContent = data.toFixed(2); // Met à jour le contenu HTML
            } else {
                console.error("La donnée récupérée n'est pas un nombre valide.");
            }
        })
        .catch(err => console.error('Erreur lors du calcul du prix total :', err));
}

// Assurez-vous que cette fonction est bien appelée et que l'élément est chargé
document.addEventListener('DOMContentLoaded', function () {
    console.log("Chargement de la page terminé, appel de actualizarPrecioTotal.");
    actualizarPrecioTotal();
});


