package com.drone.vista_operador.dtos;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OperadorDTO {
    private Integer id;
    private String username;
    private String email;
    private String password;
    private LocalDateTime fecha_creacion;
    private String estado;
}
