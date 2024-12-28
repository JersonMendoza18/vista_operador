package com.drone.vista_operador.services.impl;

import com.drone.vista_operador.dtos.OperadorDTO;
import com.drone.vista_operador.modelo.entidades.OperadorEntity;
import com.drone.vista_operador.repositories.OperadorRepository;
import com.drone.vista_operador.services.interfaces.IOperadorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class IOperadorServiceImpl implements IOperadorService {
    private final OperadorRepository operadorRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void save(OperadorDTO operadorDTO) {
        operadorDTO.setPassword(this.encryptPassword(operadorDTO.getPassword()));
        log.info("Operador hasheado {}", operadorDTO.toString());
        this.operadorRepository.save(this.mapFromOperadorDTO(operadorDTO));
    }

    @Override
    public void delete(OperadorDTO operadorDTO) {
        this.operadorRepository.delete(this.mapFromOperadorDTO(operadorDTO));
    }

    @Override
    public boolean validarDatos(OperadorDTO operadorDTO) {
        final var operadorEntity = this.operadorRepository.findByUsername(operadorDTO.getUsername());
        if (operadorEntity == null) {
            log.warn("Operador no encontrado: {}", operadorDTO.getUsername());
            return false;
        }
        return this.verificarPassword(operadorDTO.getPassword(), operadorEntity.getPassword());
    }


    private String encryptPassword(String password){
        return this.passwordEncoder.encode(password);
    }

    private boolean verificarPassword(String plainPassword, String hashedPassword){
        return passwordEncoder.matches(plainPassword, hashedPassword);
    }

    private OperadorDTO mapFromOperadorEntity(OperadorEntity operadorEntity){
        return this.modelMapper.map(operadorEntity, OperadorDTO.class);
    }

    private OperadorEntity mapFromOperadorDTO(OperadorDTO operadorDTO){
        return this.modelMapper.map(operadorDTO, OperadorEntity.class);
    }
}
