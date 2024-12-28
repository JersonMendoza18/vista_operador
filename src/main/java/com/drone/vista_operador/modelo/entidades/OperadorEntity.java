package com.drone.vista_operador.modelo.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="operador")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OperadorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String email;
    private String password;
    private LocalDateTime fecha_creacion;

    @PrePersist
    public void iniciarDatos(){
        this.setFecha_creacion(LocalDateTime.now());
    }
}
