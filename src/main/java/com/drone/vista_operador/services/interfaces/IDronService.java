package com.drone.vista_operador.services.interfaces;

import com.drone.vista_operador.dtos.DronDTO;

import java.util.List;

public interface IDronService {
    List<DronDTO> listarDrones();
    List<DronDTO>listarDronesDisponibles();
    void agregarDron(DronDTO dronDTO);
    void eliminar(Integer id);
}
