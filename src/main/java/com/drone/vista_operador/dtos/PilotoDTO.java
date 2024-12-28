package com.drone.vista_operador.dtos;

import com.drone.vista_operador.modelo.enums.UasPilotEnum;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PilotoDTO {
    private Integer id;
    private String nombre;
    private String apellido;
    private String telefonoContacto;
    private UasPilotEnum uasPilot;
    private String estado;
}
