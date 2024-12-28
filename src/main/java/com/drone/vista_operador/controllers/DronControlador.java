package com.drone.vista_operador.controllers;

import com.drone.vista_operador.dtos.DronDTO;
import com.drone.vista_operador.modelo.enums.EstadoDronEnum;
import com.drone.vista_operador.services.interfaces.IDronService;
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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ViewScoped
@Component
@Data
public class DronControlador {
    @Autowired
    private IDronService dronService;

    private DronDTO dronDTO;
    private List<DronDTO> drones=new ArrayList<>();
    private List<String> estados = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(DronControlador.class);

    @PostConstruct
    public void init(){
        this.cargarDatos();
    }

    public void cargarDatos(){
        System.out.println("Cargando datos");
        this.drones = this.dronService.listarDrones();
        // Convertir cada valor del enum en una lista de Strings
        this.estados = Arrays.stream(EstadoDronEnum.values())
                .map(Enum::name) // Extrae el nombre de cada valor del enum
                .collect(Collectors.toList()); // Colecta los nombres en una lista
    }

    public void agregarDron(){
        this.dronDTO = new DronDTO();
    }

    public void guardarDron(){
        if(this.dronDTO.getId() == null){
            logger.info("agregando dron {}", this.dronDTO.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Dron agregado"));
        }
        else{
            logger.info("actualizando dron {}", this.dronDTO.toString());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Dron actualizado"));
        }

        this.dronService.agregarDron(this.dronDTO);
        this.cargarDatos();

        //ocultar el modal
        PrimeFaces.current().executeScript("PF('ventanaModalDron').hide()");

        //actualizar la tabla usando ajax
        PrimeFaces.current().ajax().update("forma-dron:mensajes", "forma-dron:dron-tabla");

        //Limpiar el objeto dron
        this.dronDTO=null;
    }

    public void eliminarDron(){
        logger.info("Dron a eliminar {}", this.dronDTO);

        this.dronService.eliminar(dronDTO.getId());

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Dron eliminado"));

        PrimeFaces.current().ajax().update("forma-dron:mensajes", "forma-dron:mensajes", "forma-dron:dron-tabla");

        this.cargarDatos();

        //Limpiar el objeto dron
        this.dronDTO=null;
    }
}
