package com.drone.vista_operador.services.interfaces;

import com.drone.vista_operador.dtos.PilotoDTO;

import java.util.List;

public interface IPilotoService {
    List<PilotoDTO> listarPilotos();
    List<PilotoDTO>listarPilotosDisponibles();
    void agregar(PilotoDTO pilotoDTO);
    void eliminar(Integer id);
}
