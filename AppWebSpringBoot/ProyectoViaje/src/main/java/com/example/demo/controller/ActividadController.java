package com.example.demo.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Actividad;
import com.example.demo.service.ActividadService;

@RestController // Indica que esta clase es un controlador REST

public class ActividadController {
	
	private final ActividadService actividadService;
	
	// Constructor para inyectar las dependencias del servicio
	public ActividadController(ActividadService actividadService) {
		this.actividadService = actividadService;
	}
	
	// Endpoint para obtener todas las actividades
	@GetMapping("/actividades")
	public List<Actividad>obtenerTodasLasActividades() {
		return actividadService.retornarActividades();
	}
	
	// Endpoint para obtener una actividad por su ID
	@GetMapping("/actividades/{id}")
	public Actividad obtenerActividadPorId (@PathVariable Long id) {
		return actividadService.obtenerViaje(id);
	}

	// Endpoint para agregar una nueva actividad // RequestBody, usado para convertir
	// el cuerpo de la colicitud a un objet java
	@PostMapping("/actividades")
	public void agregarActividad(@RequestBody Actividad actividad) {
	actividadService.agregarActividad(actividad);	
	}
	
	
	// Endpoint para eliminar una nueva actividad con su ID
	// Pathviariable en SrpingBoot permite recibir parametros desde la URL
	@DeleteMapping("/actividades/{id}")
	public void eleminarActividad(@PathVariable Long id) {
	actividadService.eleminarActividad(id);
		
	}
	
	
	// Endpoint para actualizar una actividad por su ID
	@PutMapping("/actividades/{id}")
	public void actualizarActividad(@PathVariable Long id, @RequestBody Actividad nuevaActividad) {
		actividadService.actualizarActividad(id, nuevaActividad);
		
		}
	
	// Endpoint para obtener el preciototal de actividades
	@GetMapping("/actividades/totalPrecio")
	public double obtenerPrecioTotal() throws InterruptedException, ExecutionException {
	    return actividadService.precioTotalActividades(); // para calcular preciototal
	}
	
	@GetMapping("/actividades/totalPrecioViaje")
	public double obtenerPrecioTotalViaje(@RequestParam Long viajeId) {
	    return actividadService.obtenerActividadesPorViajeId(viajeId)
	            .stream() 
	            .mapToDouble(Actividad::getTotalPrecio) // recuperar precio para cada actividad
	            .sum(); // Calcular el total
	}
	
	
	
	
}




