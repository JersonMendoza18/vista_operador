package com.drone.vista_operador.controllers;

import com.drone.vista_operador.dtos.PilotoDTO;
import com.drone.vista_operador.modelo.enums.EstadoPilotoEnum;
import com.drone.vista_operador.modelo.enums.UasPilotEnum;
import com.drone.vista_operador.services.interfaces.IPilotoService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import lombok.Data;
import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@ViewScoped
@Component
@Data
public class PilotoControlador {
    @Autowired
    private final IPilotoService pilotoService;

    private PilotoDTO pilotoDTO;
    private List<PilotoDTO> pilotos = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(PilotoControlador.class);
    private final List<String>tiposDeUas = Stream.of(UasPilotEnum.values()).map(Enum::name).toList();
    private final List<String>estados =Stream.of(EstadoPilotoEnum.values()).map(Enum::name).toList();

    @PostConstruct
    public void init(){
        this.cargarDatos();
    }

    public void cargarDatos(){
        System.out.println("Cargando datos");
        this.pilotos = this.pilotoService.listarPilotos();
    }

    public void agregarPiloto(){
        this.pilotoDTO = new PilotoDTO();
    }

    public void guardarPiloto(){
        if(this.pilotoDTO.getId() == null){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Piloto guardado"));
        }
        else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Piloto actualizado"));
        }

        this.pilotoService.agregar(this.pilotoDTO);

        this.cargarDatos();

        PrimeFaces.current().executeScript("PF('ventanaModalPiloto').hide()");

        PrimeFaces.current().ajax().update("forma-piloto:mensajes", "forma-piloto:piloto-tabla");

        this.pilotoDTO = null;
    }

    public void eliminarPiloto(){
        this.pilotoService.eliminar(this.pilotoDTO.getId());

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Piloto eliminado"));

        this.cargarDatos();

        PrimeFaces.current().ajax().update("forma-piloto:mensajes", "forma-piloto:piloto-tabla");

        this.pilotoDTO = null;
    }
}
