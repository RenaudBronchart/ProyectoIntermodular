package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.Viaje;
import com.example.demo.repository.ViajeRepository;

@Service
public class ViajeService {
	
	// dos maneras principales de inyectar dependencias en una clase de servicio
	// inyecion por compo o inyecion por construcor como hemos hecho aqui( mas moderno y seguro )
	// 
	
	private final ViajeRepository viajerepository;
	
	public ViajeService(ViajeRepository repo ) {
		this.viajerepository = repo;
	}

	public List<Viaje> retornarViajes() {
		return viajerepository.findAll();
	}
	
	public void agregarViaje( Viaje viaje) {
		viajerepository.save(viaje);
	}
	
	
    public void eliminarViaje(Long id) {
        if (viajerepository.existsById(id)) {
            viajerepository.deleteById(id);
        } else {
            throw new RuntimeException("El viaje con id " +id + " no fue encontrado");
        }
    }
	
    public void actualizarViaje(Long id, Viaje viaje) {
        Viaje viajeModificado = viajerepository.findById(id)
            .orElseThrow(() -> new RuntimeException("El viaje con id " +id + " no fue encontrado"));

        // Mise à jour des champs
        viajeModificado.setDestinacion(viaje.getDestinacion());
        viajeModificado.setFecha(viaje.getFecha());
        viajerepository.save(viajeModificado);
    }
	
    public Viaje obtenerViaje(Long id) {
        return viajerepository.findById(id)
            .orElseThrow(() -> new RuntimeException("El viaje con id " +id + " no fue encontrado"));
    }
	
}
