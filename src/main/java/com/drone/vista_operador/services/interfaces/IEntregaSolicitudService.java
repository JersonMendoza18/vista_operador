package com.drone.vista_operador.services.interfaces;

import com.drone.vista_operador.dtos.DronDTO;
import com.drone.vista_operador.dtos.EntregaSolicitudDTO;
import com.drone.vista_operador.dtos.PilotoDTO;

import java.util.List;

public interface IEntregaSolicitudService {
    List<EntregaSolicitudDTO> listarSolicitudesSinCompletar();
    List<EntregaSolicitudDTO> listarEntregasNoEntregado();
    void actualizarEntrega(EntregaSolicitudDTO entregaSolicitudDTO, DronDTO dronDTO, PilotoDTO pilotoDTO);
    void actualizarEntrega(EntregaSolicitudDTO entregaSolicitudDTO);
    List<EntregaSolicitudDTO>listarEntregasCanceladas();
    List<EntregaSolicitudDTO>listarEntregasEntregadas();
}
