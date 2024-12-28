package com.drone.vista_operador.modelo.entidades;

import com.drone.vista_operador.modelo.ids.MedicamentoSolicitudId;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Medicamentos_Solicitud")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(MedicamentoSolicitudId.class) // Usando una clase ID compuesta para la clave primaria
public class MedicamentoSolicitudEntity {
    @Id
    @Column(name="id_solicitud", nullable = false)
    private Integer idSolicitud;

    @Id
    @Column(name="id_medicamento", nullable = false)
    private Integer idMedicamento;

    @Column(name = "cantidad_solicitada", nullable = false)
    private int cantidadSolicitada;

    @ManyToOne
    @JoinColumn(name = "id_solicitud", insertable = false, updatable = false)
    private SolicitudMedicamentoEntity solicitudDeMedicamentos; // Relación inversa


    @ManyToOne
    @JoinColumn(name = "id_medicamento", insertable = false, updatable = false)
    private MedicamentoEntity medicamento; // Relación inversa
}
