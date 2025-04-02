package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.ActividadService;
import com.example.demo.service.ViajeService;
import com.example.demo.model.Actividad;
import com.example.demo.model.Viaje;

@RestController  // Permite trabajar con API REST
@RequestMapping("/viajes") // Define la URL base para todas las solicitudes relacionadas con viajes
public class ViajeController {

    private final ViajeService viajeService;
    private final ActividadService actividadService;

    // Constructor para inyectar las dependencias del servicio
    public ViajeController(ViajeService viajeService, ActividadService actividadService) {
        this.viajeService = viajeService;
        this.actividadService = actividadService;
    }

    // Obtener todos los viajes
    @GetMapping
    public List<Viaje> obtenerTodosLosViajes() {
        return viajeService.retornarViajes();
    }

    // Obtener todas las actividades asociadas a un viaje específico
    @GetMapping("/{viajeId}/actividades")
    public List<Actividad> obtenerActividadesPorViaje(@PathVariable Long viajeId) {
        return actividadService.obtenerActividadesPorViaje(viajeId);
    }

    // agregar nuevo viaje 
    @PostMapping
    public void agregarViaje(@RequestBody Viaje viaje) {
        viajeService.agregarViaje(viaje);
    }

    // borrar viaje por su ID
    @DeleteMapping("/{id}")
    public void eleminarViaje(@PathVariable long id) {
        viajeService.eliminarViaje(id);
    }

    // Actualizar los datos de un viaje existente
    @PutMapping("/{id}")
    public void actualizarViaje(@PathVariable long id, @RequestBody Viaje nuevoViaje) {
        viajeService.actualizarViaje(id, nuevoViaje);
    }

    // para obtnener viaje via ID
    @GetMapping("/{id}")
    public Viaje obtenerViajePorId(@PathVariable long id) {
        return viajeService.obtenerViaje(id);
    }
}