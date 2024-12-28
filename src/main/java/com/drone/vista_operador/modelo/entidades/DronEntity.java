package com.drone.vista_operador.modelo.entidades;

import com.drone.vista_operador.modelo.enums.EstadoDronEnum;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Drones")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DronEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, length = 20)
    private String modelo;
    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal capacidadCarga;
    @Column(nullable = false, precision = 6, scale = 2)
    private BigDecimal rangoVuelo;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoDronEnum estado;

    @ToString.Exclude
    @OneToMany(mappedBy = "drone", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<EntregaSolicitudEntity> entregas = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DronEntity that = (DronEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
