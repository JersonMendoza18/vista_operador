package com.drone.vista_operador.modelo.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="Centros_de_salud")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CentroSaludEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, length = 40)
    private String nombre;
    @Column(nullable = false, length = 50)
    private String ubicacion;
    @Column(nullable = false, length = 10)
    private String telefonoContacto;

    @ToString.Exclude
    // orphanRemoval asegura que los médicos se eliminen si son quitados de la lista
    @OneToMany(mappedBy = "centroSalud", fetch= FetchType.EAGER,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MedicoEntity> medicos = new ArrayList<>();//Podria dar nullpointer exception

    public void agregarMedico(MedicoEntity medicoEntity){
        this.medicos.add(medicoEntity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CentroSaludEntity that = (CentroSaludEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
