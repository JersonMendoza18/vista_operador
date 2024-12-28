package com.drone.vista_operador.repositories;

import com.drone.vista_operador.modelo.entidades.EntregaSolicitudEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EntregaSolicitudRepository extends JpaRepository<EntregaSolicitudEntity, Integer> {
    @Query("SELECT e FROM EntregaSolicitudEntity e WHERE e.solicitud.estado <> 'COMPLETADA'")
    List<EntregaSolicitudEntity> listarSolicitudesPendientes();
    @Query("SELECT e FROM EntregaSolicitudEntity e WHERE e.estado <> 'ENTREGADO' AND e.estado <> 'CANCELADO'")
    List<EntregaSolicitudEntity> listarEntregasPendientes();
    @Query("SELECT e FROM EntregaSolicitudEntity e " +
            "WHERE e.estado = 'CANCELADO' OR e.solicitud.estado = 'CANCELADA'")
    List<EntregaSolicitudEntity> listarEntregasCanceladas();
    @Query("SELECT e FROM EntregaSolicitudEntity e WHERE e.estado = 'ENTREGADO'")
    List<EntregaSolicitudEntity> listarEntregasEntregadas();
}
