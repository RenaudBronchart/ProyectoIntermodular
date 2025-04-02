package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;

@Entity // Mapear clases Java a tablas de la base de datos
public class Actividad {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificador único de la actividad
    private String nombre; // Nombre de la actividad
    @Column(name = "num_pers") // Dar el nombre de la comunna en la BDD mysql
    private int numPers;
    private double precio; // Precio por persona
    
    @Column(name = "total_precio") 
    private double totalPrecio; // Precio calculado segun numPers * precio

    @ManyToOne
    @JoinColumn(name = "viaje_id", nullable = false)
    private Viaje viaje; // Relación con el viaje asociado a la actividad

    // Constructor por defecto
    public Actividad() {
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumPers() {
        return numPers;
    }

    public void setNumPers(int numPers) {
        this.numPers = numPers;
        recalculerTotalPrecio(); // Actualiza el total cada vez que se modifica el número de personnes
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
        recalculerTotalPrecio(); // Actualiza el total cada vez que se modifica el precio
    }

    //
    public void recalculerTotalPrecio() {
        this.totalPrecio = this.numPers * this.precio;
    }

    public double getTotalPrecio() {
		return totalPrecio;
	}

	public void setTotalPrecio(double totalPrecio) {
		this.totalPrecio = totalPrecio;
	}

	// Relacion con viaje
    public Viaje getViaje() {
        return viaje;
    }

    public void setViaje(Viaje viaje) {
        this.viaje = viaje;
    }
}
