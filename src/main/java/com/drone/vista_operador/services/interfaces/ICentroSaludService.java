package com.drone.vista_operador.services.interfaces;

import com.drone.vista_operador.dtos.CentroSaludDTO;

import java.util.List;

public interface ICentroSaludService {
    List<CentroSaludDTO> listarCentros();
    List<String>listarNombes();
    void agregar(CentroSaludDTO c);
    CentroSaludDTO buscar(Integer id);
    CentroSaludDTO buscarPorNombre(String nombre);
    void eliminar(Integer id);
}
