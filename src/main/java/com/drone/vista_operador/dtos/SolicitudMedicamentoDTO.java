package com.drone.vista_operador.dtos;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SolicitudMedicamentoDTO {
    private Integer idSolicitudMedicamento;
    private LocalDateTime fechaSolicitud;
    private String estado;
    private MedicoDTO medico;
    List<MedicamentoDTO> medicamentos;
}
