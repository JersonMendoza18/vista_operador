package com.drone.vista_operador.repositories;

import com.drone.vista_operador.modelo.entidades.SolicitudMedicamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolicitudMedicamentoRepository extends JpaRepository<SolicitudMedicamentoEntity, Integer> {

}
