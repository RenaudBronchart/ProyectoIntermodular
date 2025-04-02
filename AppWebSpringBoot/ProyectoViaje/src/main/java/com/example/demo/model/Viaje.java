package com.example.demo.model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name="viaje")
public class Viaje {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String destinacion;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate fecha;
	
	@JsonIgnore
	@OneToMany(mappedBy="viaje", cascade = CascadeType.ALL)
	private List<Actividad> actividades;
	
	
	public Viaje() {}


	public Viaje(Long id, String destinacion, LocalDate fecha, List<Actividad> actividades) {
		super();
		this.id = id;
		this.destinacion = destinacion;
		this.fecha = fecha;
		this.actividades = actividades;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDestinacion() {
		return destinacion;
	}

	public void setDestinacion(String destinacion) {
		this.destinacion = destinacion;
	}


	public List<Actividad> getActividades() {
		return actividades;
	}


	public void setActividades(List<Actividad> actividades) {
		this.actividades = actividades;
	}


    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
	
	

}
