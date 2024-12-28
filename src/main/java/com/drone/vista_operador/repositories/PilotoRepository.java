package com.drone.vista_operador.repositories;

import com.drone.vista_operador.modelo.entidades.PilotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PilotoRepository extends JpaRepository<PilotoEntity, Integer> {
    @Query("SELECT p FROM PilotoEntity p WHERE p.estado = 'DISPONIBLE'")
    List<PilotoEntity> listarPilotosDisponibles();
}
