package com.drone.vista_operador.repositories;

import com.drone.vista_operador.modelo.entidades.OperadorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperadorRepository extends JpaRepository<OperadorEntity, Integer> {
    OperadorEntity findByUsername(String username);
}
