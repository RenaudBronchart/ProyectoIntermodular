export function resetForm(viajeForm) {
    viajeForm.reset();
    const submitButton = document.querySelector('#viaje-form button[type="submit"]');
    submitButton.textContent = 'Agregar Viaje';
}

