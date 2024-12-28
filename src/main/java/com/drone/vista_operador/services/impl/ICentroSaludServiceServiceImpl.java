package com.drone.vista_operador.services.impl;

import com.drone.vista_operador.dtos.CentroSaludDTO;
import com.drone.vista_operador.modelo.entidades.CentroSaludEntity;
import com.drone.vista_operador.repositories.CentroSaludRepository;
import com.drone.vista_operador.services.interfaces.ICentroSaludService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
//Injeccion de dependencia cuando las variables son final
//No se necesita autowired para el repositorio
@Transactional
@RequiredArgsConstructor
public class ICentroSaludServiceServiceImpl implements ICentroSaludService {
    private final CentroSaludRepository centroSaludRepository;
    private final ModelMapper modelMapper;  // Inyecta ModelMapper automáticamente
    @Override
    public List<CentroSaludDTO> listarCentros() {
        final var centros = new ArrayList<CentroSaludDTO>();
        this.centroSaludRepository.findAll().forEach(centroSaludEntity -> {
            centros.add(this.mapFromCentroEntity(centroSaludEntity));
        });
        return centros;
    }

    @Override
    public List<String> listarNombes() {
        return this.centroSaludRepository.findAllNombres();
    }

    @Override
    public void agregar(CentroSaludDTO c) {
        this.centroSaludRepository.save(this.mapFromCentroDTO(c));
    }

    @Override
    public CentroSaludDTO buscar(Integer id) {
        return this.mapFromCentroEntity(this.centroSaludRepository.findById(id).orElseThrow());
    }

    @Override
    public CentroSaludDTO buscarPorNombre(String nombre){
        var centro = this.centroSaludRepository.findByNombre(nombre);
        if(centro == null){
            throw new IllegalStateException("El centro buscado no existe");
        }
        return this.mapFromCentroEntity(centro);
    }

    @Override
    public void eliminar(Integer id) {
        this.centroSaludRepository.deleteById(id);
    }

    private CentroSaludDTO mapFromCentroEntity(CentroSaludEntity centroSaludEntity){
        return this.modelMapper.map(centroSaludEntity, CentroSaludDTO.class);
    }

    private CentroSaludEntity mapFromCentroDTO(CentroSaludDTO centroSaludDTO){
        final var centroSaludEntity = new CentroSaludEntity();
        this.modelMapper.map(centroSaludDTO, centroSaludEntity);
        return centroSaludEntity;
    }
}
