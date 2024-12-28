package com.drone.vista_operador.modelo.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name="Medicos")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MedicoEntity {
    @Id
    @Column(length = 8)
    private String ci;
    @Column(nullable = false, length = 20)
    private String nombre;
    @Column(nullable = false, length = 20)
    private String apellido;
    @Column(nullable = false, length = 20)
    private String especialidad;
    @Column(nullable = false, length = 10)
    private String telefonoContacto;

    @ToString.Exclude
    @OneToMany(mappedBy = "medico", fetch = FetchType.EAGER)
    private List<SolicitudMedicamentoEntity> listaSolicitudes;

    @ToString.Exclude
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name="id_centro_salud")
    private CentroSaludEntity centroSalud;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedicoEntity that = (MedicoEntity) o;
        return Objects.equals(ci, that.ci);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ci);
    }
}
