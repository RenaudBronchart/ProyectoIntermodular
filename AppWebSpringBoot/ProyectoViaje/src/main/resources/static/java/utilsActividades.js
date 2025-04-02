export function resetForm(actividadForm) {
    actividadForm.reset();
    const submitButton = document.querySelector('#actividad-form button[type="submit"]');
    submitButton.textContent = 'Agregar Actividad';
}