package com.drone.vista_operador.services.interfaces;

import com.drone.vista_operador.dtos.OperadorDTO;

public interface IOperadorService {
    void save(OperadorDTO operadorDTO);
    void delete(OperadorDTO operadorDTO);
    boolean validarDatos(OperadorDTO operadorDTO);
}
