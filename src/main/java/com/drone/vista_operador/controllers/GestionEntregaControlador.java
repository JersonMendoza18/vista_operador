package com.drone.vista_operador.controllers;

import com.drone.vista_operador.dtos.*;
import com.drone.vista_operador.modelo.enums.EstadoEntregaSolicitudEnum;
import com.drone.vista_operador.modelo.enums.EstadoSolicitudMedicamentoEnum;
import com.drone.vista_operador.services.interfaces.IEntregaSolicitudService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@ViewScoped
@Slf4j
@Data
public class GestionEntregaControlador {
    @Autowired
    private IEntregaSolicitudService entregaSolicitudService;
    private List<EntregaSolicitudDTO> entregasPendientes;
    private EntregaSolicitudDTO entregaSolicitudDTO;
    private MedicoDTO medicoDTO;
    private CentroSaludDTO centroSaludDTO;
    private List<String>estadoEntrega = new ArrayList<>();
    private LocalDateTime fechaActual;
    private static final Logger logger = LoggerFactory.getLogger(GestionEntregaControlador.class);


    @PostConstruct
    public void init(){
        cargarDatos();
    }

    public void cargarDatos(){
        this.entregasPendientes = this.entregaSolicitudService.listarEntregasNoEntregado();
        this.medicoDTO = new MedicoDTO();
        this.centroSaludDTO = new CentroSaludDTO();
        this.estadoEntrega = Arrays.stream(EstadoEntregaSolicitudEnum.values())
                .map(Enum::name) // Extrae el nombre de cada valor del enum
                .collect(Collectors.toList()); // Colecta los nombres en una lista
        this.fechaActual = LocalDateTime.now();
    }

    public void cargarMedico(EntregaSolicitudDTO entregaSolicitudDTO){
        this.medicoDTO = entregaSolicitudDTO.getSolicitudMedicamento().getMedico();
    }

    public void cargarCentroSalud(EntregaSolicitudDTO entregaSolicitudDTO){
        this.centroSaludDTO = entregaSolicitudDTO.getSolicitudMedicamento().getMedico().getCentroSalud();
    }

    public void cargarEntregaActualizar(EntregaSolicitudDTO entregaSolicitudDTO){
        this.entregaSolicitudDTO = entregaSolicitudDTO;//Poner como dto a la entrega seleccionada
        logger.info("Dron de la entrega {}", this.entregaSolicitudDTO.toString());
    }

    public void actualizarEntrega(){
        logger.info("Fecha escogida: "+this.entregaSolicitudDTO.getFechaEntrega().toString());
        logger.info("Estado escogida: "+this.entregaSolicitudDTO.getSolicitudMedicamento().getEstado());

        this.entregaSolicitudService.actualizarEntrega(this.entregaSolicitudDTO);
        PrimeFaces.current().executeScript("PF('ventanaModalActualizar').hide()");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Entrega actualizada"));
        cargarDatos();
    }
}
