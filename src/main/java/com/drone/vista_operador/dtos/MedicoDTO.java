package com.drone.vista_operador.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MedicoDTO {
    private String ci;
    private String nombre;
    private String apellido;
    private String especialidad;
    private String telefonoContacto;
    private CentroSaludDTO centroSalud;
}
