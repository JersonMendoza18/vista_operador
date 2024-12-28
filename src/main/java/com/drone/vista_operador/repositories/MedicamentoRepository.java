package com.drone.vista_operador.repositories;

import com.drone.vista_operador.modelo.entidades.MedicamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicamentoRepository extends JpaRepository<MedicamentoEntity, Integer> {
}
