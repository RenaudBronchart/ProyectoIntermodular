package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.example.demo.model.Actividad;
import com.example.demo.repository.ActividadRepository;

@Service // Servicio para gestionar la lógica de las actividades
public class ActividadService {

    private final ActividadRepository actividadRepository;

    // Constructor de la clase, que recibe el repositorio para interactuar con la base de datos
    public ActividadService(ActividadRepository actividadRepository) {
        this.actividadRepository = actividadRepository;
    }

    // Método para retornar todas las actividades
    public List<Actividad> retornarActividades() {
        return actividadRepository.findAll(); // Devuelve todas las actividades almacenadas
    }

    // Método para agregar una nueva actividad
    public void agregarActividad(Actividad actividad) {
        actividad.recalculerTotalPrecio(); // Recalcula el total antes de guardar la actividad
        actividadRepository.save(actividad); // Guarda la actividad en la base de datos
    }

    // Método para eliminar una actividad por su ID
    public void eleminarActividad(long id) {
        // Verifica si existe una actividad con el ID proporcionado
        if (actividadRepository.existsById(id)) {
            actividadRepository.deleteById(id); // Elimina la actividad si existe
        } else {
            throw new RuntimeException("Actividad con id " + id + " no fue encontrada"); // Lanza una excepción si no se encuentra
        }
    }

    // Método para actualizar una actividad existente
    public void actualizarActividad(Long id, Actividad actividad) {
        // Busca la actividad por su ID, o lanza una excepción si no la encuentra
        Actividad actividadModificada = actividadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Actividad con id " + id + " no fue encontrada"));

        // Actualiza los campos de la actividad con los nuevos valores
        actividadModificada.setNumPers(actividad.getNumPers());
        actividadModificada.setNombre(actividad.getNombre());
        actividadModificada.setPrecio(actividad.getPrecio());

        // Recalcula automáticamente el precio total de la actividad
        actividadModificada.recalculerTotalPrecio();

        // Guarda la actividad actualizada en la base de datos
        actividadRepository.save(actividadModificada);
    }

    // Método para obtener una actividad por su ID
    public Actividad obtenerViaje(Long id) {
        return actividadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Actividad con id " + id + " no fue encontrada")); // Lanza una excepción si no la encuentra
    }
    
    public List<Actividad> obtenerActividadesPorViajeId(Long viajeId) {
        return actividadRepository.findByViajeId(viajeId); // devuelve unq lista de actividades con su ID
    }

    // Método para obtener todas las actividades asociadas a un viaje específico
    public List<Actividad> obtenerActividadesPorViaje(Long viajeId) {
        return actividadRepository.findByViajeId(viajeId); // Busca las actividades relacionadas con el ID del viaje
    }

    // Método para calcular el precio total de todas las actividades
    public double precioTotalActividades() throws InterruptedException, ExecutionException {

        // Recupera todas las actividades desde la base de datos
        List<Actividad> actividades = actividadRepository.findAll();

        // Crea un ExecutorService con un número de threads igual al tamaño de la lista de actividades
        ExecutorService executorService = Executors.newFixedThreadPool(actividades.size());

        // Lista para almacenar los resultados de las tareas futuras (Future)
        List<Future<Double>> futures = new ArrayList<>();

        // Recorre todas las actividades y envía una tarea para calcular su precio total
        for (Actividad actividad : actividades) {
            // Utiliza Callable para cada tarea, que devuelve el totalPrecio de la actividad
        	// Callable en vez de Runnable, callable puede devolver en valor ( total precio aqui)
        	//
            Callable<Double> task = () -> actividad.getTotalPrecio(); 
            futures.add(executorService.submit(task)); // Envía la tarea al pool de threads
        }

        // Espera a que todas las tareas terminen y calcula el total general
        double totalGeneral = 0;
        for (Future<Double> future : futures) {
            totalGeneral += future.get(); // Recupera el resultado de cada tarea
        }
        
        System.out.println("Prix total des activités : " + totalGeneral);     

        // Cierra el ExecutorService para liberar los recursos del sistema
        executorService.shutdown();

        // Devuelve el total general acumulado de todas las actividades
        return totalGeneral;
    }
}