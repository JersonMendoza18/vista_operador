package com.drone.vista_operador.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CentroSaludDTO {
    private Integer id;
    private String nombre;
    private String ubicacion;
    private String telefonoContacto;
}
