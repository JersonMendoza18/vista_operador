package com.drone.vista_operador.repositories;

import com.drone.vista_operador.modelo.entidades.MedicoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicoRepository extends JpaRepository<MedicoEntity, String> {
}
