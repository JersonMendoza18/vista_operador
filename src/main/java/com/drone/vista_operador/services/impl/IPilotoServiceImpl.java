package com.drone.vista_operador.services.impl;

import com.drone.vista_operador.dtos.PilotoDTO;
import com.drone.vista_operador.modelo.entidades.PilotoEntity;
import com.drone.vista_operador.repositories.PilotoRepository;
import com.drone.vista_operador.services.interfaces.IPilotoService;
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
public class IPilotoServiceImpl implements IPilotoService {
    private final PilotoRepository pilotoRepository;
    private final ModelMapper modelMapper; // Inyecta ModelMapper automáticamente

    @Override
    public List<PilotoDTO> listarPilotos() {
        final var pilotos = new ArrayList<PilotoDTO>();
        this.pilotoRepository.findAll().forEach(pilotoEntity -> pilotos.add(mapFromPilotoEnity(pilotoEntity)));
        return pilotos;
    }

    @Override
    public List<PilotoDTO> listarPilotosDisponibles() {
        final var pilotosDisponibles = new ArrayList<PilotoDTO>();
        this.pilotoRepository.listarPilotosDisponibles().forEach(pilotoEntity -> pilotosDisponibles.add(mapFromPilotoEnity(pilotoEntity)));
        return pilotosDisponibles;
    }

    @Override
    public void agregar(PilotoDTO pilotoDTO) {
        this.pilotoRepository.save(this.mapFromPilotoDTO(pilotoDTO));
    }

    @Override
    public void eliminar(Integer id) {
        this.pilotoRepository.deleteById(id);
    }

    private PilotoEntity mapFromPilotoDTO(PilotoDTO pilotoDTO){
        final var pilotoEntity = new PilotoEntity();
        this.modelMapper.map(pilotoDTO, pilotoEntity);
        return pilotoEntity;
    }

    private PilotoDTO mapFromPilotoEnity(PilotoEntity pilotoEntity){
        return this.modelMapper.map(pilotoEntity, PilotoDTO.class);
    }
}
