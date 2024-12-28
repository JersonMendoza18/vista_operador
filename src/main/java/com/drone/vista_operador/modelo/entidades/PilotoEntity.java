package com.drone.vista_operador.modelo.entidades;

import com.drone.vista_operador.modelo.enums.EstadoPilotoEnum;
import com.drone.vista_operador.modelo.enums.UasPilotEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Pilotos")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PilotoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, length = 20)
    private String nombre;
    @Column(nullable = false, length = 20)
    private String apellido;
    @Column(nullable = false, length = 10)
    private String telefonoContacto;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UasPilotEnum uasPilot;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPilotoEnum estado;

    @ToString.Exclude
    @OneToMany(mappedBy = "piloto", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<EntregaSolicitudEntity> entregas = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PilotoEntity that = (PilotoEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
