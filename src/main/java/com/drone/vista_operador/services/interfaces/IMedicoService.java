package com.drone.vista_operador.services.interfaces;

import com.drone.vista_operador.dtos.MedicoDTO;
import java.util.List;

public interface IMedicoService {
    List<MedicoDTO> listarMedicos();
    void agregar(MedicoDTO medicoDTO, String nombreCentro);
    MedicoDTO buscar(String id);
    void eliminar(String id);
    Boolean existeMedico(String ci);
}
