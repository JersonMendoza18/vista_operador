package com.drone.vista_operador.dtos;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DronDTO{
    private Integer id;
    private String modelo;
    private BigDecimal capacidadCarga;
    private BigDecimal rangoVuelo;
    private String estado;
}
