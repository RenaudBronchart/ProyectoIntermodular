package com.example.demo.repository;


import com.example.demo.model.Viaje;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository  // La anotación @Repository indica a Spring que esta interfaz es un componente de acceso a datos 
//permitiendo que Spring gestione la inyección de dependencias y maneje excepciones específicas de persistencia. 
 
public interface ViajeRepository extends JpaRepository <Viaje, Long> {

}
