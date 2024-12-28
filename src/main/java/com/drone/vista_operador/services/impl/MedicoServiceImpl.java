package com.drone.vista_operador.services.impl;


import com.drone.vista_operador.dtos.MedicoDTO;
import com.drone.vista_operador.modelo.entidades.MedicoEntity;
import com.drone.vista_operador.repositories.CentroSaludRepository;
import com.drone.vista_operador.repositories.MedicoRepository;
import com.drone.vista_operador.services.interfaces.IMedicoService;
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
public class MedicoServiceImpl implements IMedicoService {

    private final MedicoRepository medicoRepository;
    private final CentroSaludRepository centroSaludRepository;
    private final ModelMapper modelMapper;  // Inyecta ModelMapper automáticamente

    @Override
    public List<MedicoDTO> listarMedicos() {
        var medicos = new ArrayList<MedicoDTO>();
        this.medicoRepository.findAll().forEach(medicoEntity -> {
            medicos.add(this.mapFromMedicoEntity(medicoEntity));
                }
        );
        return medicos;
    }

    @Override
    public void agregar(MedicoDTO medicoDTO, String nombreCentro) {
        // Buscar el centro de salud
        var centro = this.centroSaludRepository.findByNombre(nombreCentro);

        // Verificar si el médico ya existe
        if (this.existeMedico(medicoDTO.getCi())) {
            log.info("EXISTE");
            // Cargar la instancia gestionada desde la base de datos
            var medicoEntity = this.medicoRepository.findById(medicoDTO.getCi()).orElseThrow();
            // Actualizar los campos
            medicoEntity.setNombre(medicoDTO.getNombre());
            medicoEntity.setApellido(medicoDTO.getApellido());
            medicoEntity.setEspecialidad(medicoDTO.getEspecialidad());
            medicoEntity.setTelefonoContacto(medicoDTO.getTelefonoContacto());
            medicoEntity.setCentroSalud(centro);

            // Guardar los cambios
            this.medicoRepository.save(medicoEntity);
        } else {
            log.info("NO EXISTE");
            // Crear una nueva instancia y asignar los datos
            var medicoEntity = this.mapFromMedicoDTO(medicoDTO);
            medicoEntity.setCentroSalud(centro);

            // Guardar la nueva entidad
            this.medicoRepository.save(medicoEntity);
        }
    }


    @Override
    public MedicoDTO buscar(String id) {
        return this.mapFromMedicoEntity(this.medicoRepository.findById(id).orElseThrow());
    }

    @Override
    public void eliminar(String id) {
        var medico = this.medicoRepository.findById(id).orElseThrow();
        // Desvincula al medico de su centro de salud antes de eliminarlo
        var centroSalud = this.centroSaludRepository.findById(medico.getCentroSalud().getId()).orElseThrow();
        centroSalud.getMedicos().remove(medico);
        this.centroSaludRepository.save(centroSalud);// Guarda cambios en el centro de salud
        this.medicoRepository.deleteById(id);
    }

    //Verificar si existe el medico
    @Override
    public Boolean existeMedico(String ci) {
        return this.medicoRepository.existsById(ci);
    }

    private MedicoDTO mapFromMedicoEntity(MedicoEntity medicoEntity){
        return this.modelMapper.map(medicoEntity, MedicoDTO.class);
    }

    private MedicoEntity mapFromMedicoDTO(MedicoDTO medicoDTO){
        return this.modelMapper.map(medicoDTO, MedicoEntity.class);
    }
}
