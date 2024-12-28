package com.drone.vista_operador.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MedicamentoDTO {
    private Integer idMedicamento;
    private String nombreMedicamento;
    private String descripcionMedicamento;
    private Integer cantidad;
}
