package com.drone.vista_operador.repositories;

import com.drone.vista_operador.modelo.entidades.DronEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DronRepository extends JpaRepository<DronEntity, Integer> {
    @Query("SELECT d FROM DronEntity d WHERE d.estado = 'DISPONIBLE'")
    List<DronEntity> listarDronDisponible();
}
