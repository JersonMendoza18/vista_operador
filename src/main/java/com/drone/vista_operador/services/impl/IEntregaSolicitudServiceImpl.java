package com.drone.vista_operador.services.impl;

import com.drone.vista_operador.dtos.DronDTO;
import com.drone.vista_operador.dtos.EntregaSolicitudDTO;
import com.drone.vista_operador.dtos.PilotoDTO;
import com.drone.vista_operador.modelo.entidades.EntregaSolicitudEntity;
import com.drone.vista_operador.modelo.enums.EstadoDronEnum;
import com.drone.vista_operador.modelo.enums.EstadoEntregaSolicitudEnum;
import com.drone.vista_operador.modelo.enums.EstadoPilotoEnum;
import com.drone.vista_operador.modelo.enums.EstadoSolicitudMedicamentoEnum;
import com.drone.vista_operador.repositories.DronRepository;
import com.drone.vista_operador.repositories.EntregaSolicitudRepository;
import com.drone.vista_operador.repositories.PilotoRepository;
import com.drone.vista_operador.services.interfaces.IEntregaSolicitudService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class IEntregaSolicitudServiceImpl implements IEntregaSolicitudService {
    private final EntregaSolicitudRepository entregaSolicitudRepository;
    private final DronRepository dronRepository;
    private  final PilotoRepository pilotoRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<EntregaSolicitudDTO> listarSolicitudesSinCompletar() {
        var listaEntidades = this.entregaSolicitudRepository.listarSolicitudesPendientes();
        List<EntregaSolicitudDTO>listaDTO = new ArrayList<>();
        listaEntidades.forEach(l->listaDTO.add(this.mapFromEntregaSolicitudEntity(l)));
        return listaDTO;
    }

    @Override
    public List<EntregaSolicitudDTO> listarEntregasNoEntregado() {
        var listaEntidades = this.entregaSolicitudRepository.listarEntregasPendientes();
        List<EntregaSolicitudDTO>listaDTO = new ArrayList<>();
        listaEntidades.forEach(l->listaDTO.add(this.mapFromEntregaSolicitudEntity(l)));
        return listaDTO;
    }

    //Actualizar solicitud y entrega
    public void actualizarEntrega(EntregaSolicitudDTO entregaSolicitudDTO, DronDTO dronDTO, PilotoDTO pilotoDTO){
        // Recupera la entidad existente
        var entrega = this.entregaSolicitudRepository.findById(entregaSolicitudDTO.getId()).orElseThrow();
        //Actualizar estado de la solicitud
        entrega.getSolicitud().setEstado(EstadoSolicitudMedicamentoEnum.valueOf(entregaSolicitudDTO.getSolicitudMedicamento().getEstado()));

        //Si la solicitud fue cancelada se ponen en disponible
        if(entregaSolicitudDTO.getSolicitudMedicamento().getEstado().equals("CANCELADA")){
            entrega.getPiloto().setEstado(EstadoPilotoEnum.DISPONIBLE);
            entrega.getDrone().setEstado(EstadoDronEnum.DISPONIBLE);
        }

        //Actualizar fecha de entrega
        entrega.setFechaEntrega(entregaSolicitudDTO.getFechaEntrega());
        if(dronDTO.getId() !=null && pilotoDTO.getId() != null){
            // Actualizar dron
            entrega.setDrone(this.dronRepository.findById(dronDTO.getId()).orElseThrow());
            entrega.getDrone().setEstado(EstadoDronEnum.OPERANDO);
            //Actualizar piloto
            entrega.setPiloto(this.pilotoRepository.findById(pilotoDTO.getId()).orElseThrow());
            entrega.getPiloto().setEstado(EstadoPilotoEnum.OPERANDO);
        }
        // Guarda la entidad actualizada
        this.entregaSolicitudRepository.save(entrega);
        log.info("Entrega actualizada");
    }

    //Actualizar enum y fecha de entrega final
    public void actualizarEntrega(EntregaSolicitudDTO entregaSolicitudDTO){
        // Recupera la entidad existente
        var entrega = this.entregaSolicitudRepository.findById(entregaSolicitudDTO.getId()).orElseThrow();

        //Actualizar estado de la solicitud
        entrega.setEstado(EstadoEntregaSolicitudEnum.valueOf(entregaSolicitudDTO.getEstado()));

        //Actualizar fecha de entrega
        entrega.setFechaEntrega(entregaSolicitudDTO.getFechaEntrega());

        //Si la entrega fue completada o cancelada se ponen en disponible
        if(entregaSolicitudDTO.getEstado().equals("ENTREGADO") ||
                entregaSolicitudDTO.getEstado().equals("CANCELADO")){
            entrega.getPiloto().setEstado(EstadoPilotoEnum.DISPONIBLE);
            entrega.getDrone().setEstado(EstadoDronEnum.DISPONIBLE);
        }

        // Guarda la entidad actualizada
        this.entregaSolicitudRepository.save(entrega);
    }

    @Override
    public List<EntregaSolicitudDTO> listarEntregasCanceladas() {
        var listaEntidades = this.entregaSolicitudRepository.listarEntregasCanceladas();
        List<EntregaSolicitudDTO>listaDTO = new ArrayList<>();
        listaEntidades.forEach(l->listaDTO.add(this.mapFromEntregaSolicitudEntity(l)));
        return listaDTO;
    }

    @Override
    public List<EntregaSolicitudDTO> listarEntregasEntregadas() {
        var listaEntidades = this.entregaSolicitudRepository.listarEntregasEntregadas();
        List<EntregaSolicitudDTO>listaDTO = new ArrayList<>();
        listaEntidades.forEach(l->listaDTO.add(this.mapFromEntregaSolicitudEntity(l)));
        return listaDTO;
    }

    private EntregaSolicitudDTO mapFromEntregaSolicitudEntity(EntregaSolicitudEntity entregaSolicitudEntity) {
        log.info("Entidad Dron: {}", entregaSolicitudEntity.getDrone());  // Verifica si la entidad tiene un Dron asignado

        // Crear el DTO para la entidad Dron
        DronDTO dronDTO = null;
        if (entregaSolicitudEntity.getDrone() != null) {
            // Si la entidad Dron está presente, mapeamos manualmente sus propiedades
            dronDTO = modelMapper.map(entregaSolicitudEntity.getDrone(), DronDTO.class);
        }

        // Mapear el resto de la entidad a DTO
        EntregaSolicitudDTO entregaSolicitudDTO = modelMapper.map(entregaSolicitudEntity, EntregaSolicitudDTO.class);

        // Asignar el DronDTO explícitamente
        entregaSolicitudDTO.setDron(dronDTO);

        return entregaSolicitudDTO;
    }

    private EntregaSolicitudEntity mapFromEntragaSolicitudDTO(EntregaSolicitudDTO entregaSolicitudDTO){
        return this.modelMapper.map(entregaSolicitudDTO, EntregaSolicitudEntity.class);
    }

}
