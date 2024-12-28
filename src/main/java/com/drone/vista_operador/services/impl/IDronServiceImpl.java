package com.drone.vista_operador.services.impl;

import com.drone.vista_operador.dtos.DronDTO;
import com.drone.vista_operador.modelo.entidades.DronEntity;
import com.drone.vista_operador.repositories.DronRepository;
import com.drone.vista_operador.services.interfaces.IDronService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class IDronServiceImpl implements IDronService {
    private final DronRepository dronRepository;
    private final ModelMapper modelMapper;
    @Override
    public List<DronDTO> listarDrones() {
        var drones = new ArrayList<DronDTO>();
        this.dronRepository.findAll().forEach(dronEntity ->
                drones.add(this.mapFromDronEntity(dronEntity)));
        return drones;
    }

    @Override
    public List<DronDTO> listarDronesDisponibles() {
        var dronesDisponibles = new ArrayList<DronDTO>();
        this.dronRepository.listarDronDisponible().forEach(dronEntity ->
                dronesDisponibles.add(this.mapFromDronEntity(dronEntity)));
        return dronesDisponibles;
    }

    @Override
    public void agregarDron(DronDTO dronDTO) {
        this.dronRepository.save(mapFromDronDTO(dronDTO));
    }

    @Override
    public void eliminar(Integer id) {
        this.dronRepository.deleteById(id);
    }

    private DronDTO mapFromDronEntity(DronEntity dronEntity){
        return this.modelMapper.map(dronEntity, DronDTO.class);
    }

    private DronEntity mapFromDronDTO(DronDTO dronDTO){
        final var dronEntity = new DronEntity();
        this.modelMapper.map(dronDTO, dronEntity);
        return dronEntity;
    }
}
