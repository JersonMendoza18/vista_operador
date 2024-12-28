package com.drone.vista_operador.modelo.ids;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MedicamentoSolicitudId {
    private Integer idSolicitud;
    private Integer idMedicamento;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedicamentoSolicitudId that = (MedicamentoSolicitudId) o;
        return Objects.equals(idSolicitud, that.idSolicitud) && Objects.equals(idMedicamento, that.idMedicamento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idSolicitud, idMedicamento);
    }
}
