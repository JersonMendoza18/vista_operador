package com.drone.vista_operador.repositories;

import com.drone.vista_operador.modelo.entidades.CentroSaludEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CentroSaludRepository extends JpaRepository<CentroSaludEntity, Integer>{
    CentroSaludEntity findByNombre(String nombre);
    @Query("SELECT c.nombre FROM CentroSaludEntity c WHERE c.nombre IS NOT NULL")
    List<String> findAllNombres();
}
