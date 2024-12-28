package com.drone.vista_operador.repositories;

import com.drone.vista_operador.modelo.entidades.MedicamentoSolicitudEntity;
import com.drone.vista_operador.modelo.ids.MedicamentoSolicitudId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicamentoSolicitudRepository extends JpaRepository<MedicamentoSolicitudEntity, MedicamentoSolicitudId> {
}
