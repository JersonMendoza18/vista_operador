package com.drone.vista_operador.dtos;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EntregaSolicitudDTO {
    private Integer id;
    private LocalDateTime fechaEntrega;
    private String estado;
    private DronDTO dron;
    private PilotoDTO piloto;
    private SolicitudMedicamentoDTO solicitudMedicamento;
}
