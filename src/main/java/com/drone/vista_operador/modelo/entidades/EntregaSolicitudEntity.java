package com.drone.vista_operador.modelo.entidades;

import com.drone.vista_operador.modelo.enums.EstadoEntregaSolicitudEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Entrega_de_solicitudes")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EntregaSolicitudEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime fechaEntrega;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoEntregaSolicitudEnum estado;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="id_solicitud", nullable = false, unique = true)
    private SolicitudMedicamentoEntity solicitud;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_piloto")
    private PilotoEntity piloto;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_dron")
    private DronEntity drone;
}
