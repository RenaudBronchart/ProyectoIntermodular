package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Actividad;




//La anotación @Repository indica a Spring que esta interfaz es un componente de acceso a datos 
//permitiendo que Spring gestione la inyección de dependencias y maneje excepciones específicas de persistencia. 
@Repository
public interface ActividadRepository extends JpaRepository<Actividad, Long> {
	List<Actividad> findByViajeId(Long viajeId);
	
	
}
