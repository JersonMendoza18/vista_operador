package com.drone.vista_operador.modelo.entidades;

import com.drone.vista_operador.modelo.enums.EstadoSolicitudMedicamentoEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="Solicitud_de_medicamentos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class SolicitudMedicamentoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime fechaSolicitud;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoSolicitudMedicamentoEnum estado;

    @ToString.Exclude
    @OneToOne(mappedBy = "solicitud", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private EntregaSolicitudEntity entrega;

    @ManyToOne
    @JoinColumn(name = "ci_medico")
    private MedicoEntity medico;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "solicitudDeMedicamentos",
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MedicamentoSolicitudEntity> medicamentosSolicitados; // Relación con Medicamentos_Solicitud
}
