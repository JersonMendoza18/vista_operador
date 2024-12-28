package com.drone.vista_operador.controllers;

import com.drone.vista_operador.dtos.*;
import com.drone.vista_operador.modelo.enums.EstadoDronEnum;
import com.drone.vista_operador.modelo.enums.EstadoSolicitudMedicamentoEnum;
import com.drone.vista_operador.services.interfaces.IDronService;
import com.drone.vista_operador.services.interfaces.IEntregaSolicitudService;
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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ViewScoped
@Component
@Data
public class GestionSolicitudControlador {
    @Autowired
    private IEntregaSolicitudService entregaSolicitudService;
    @Autowired
    private IDronService dronService;
    @Autowired
    private IPilotoService pilotoService;
    private EntregaSolicitudDTO entregaSolicitudDTO;
    private List<EntregaSolicitudDTO> solicitudesSinCompletar;
    private List<DronDTO>dronesDisponibles;
    private List<PilotoDTO>pilotosDisponibles;
    private List<String>estadoSolicitud = new ArrayList<>();
    private PilotoDTO pilotoEscogido;
    private DronDTO dronEscogido;
    private MedicoDTO medicoDTO;
    private LocalDateTime fechaEscogida;
    private CentroSaludDTO centroSaludDTO;
    private LocalDateTime fechaActual;
    private static final Logger logger = LoggerFactory.getLogger(GestionSolicitudControlador.class);

    @PostConstruct
    public void init(){
        cargarDatos();
    }

    public void cargarDatos(){
        this.solicitudesSinCompletar = this.entregaSolicitudService.listarSolicitudesSinCompletar();
        this.estadoSolicitud = Arrays.stream(EstadoSolicitudMedicamentoEnum.values())
                .map(Enum::name) // Extrae el nombre de cada valor del enum
                .collect(Collectors.toList()); // Colecta los nombres en una lista
        this.medicoDTO = new MedicoDTO();
        this.centroSaludDTO = new CentroSaludDTO();
        this.fechaActual = LocalDateTime.now();
    }

    public void cargarMedico(EntregaSolicitudDTO entregaSolicitudDTO){
        this.medicoDTO = entregaSolicitudDTO.getSolicitudMedicamento().getMedico();
    }

    public void cargarCentroSalud(EntregaSolicitudDTO entregaSolicitudDTO){
        this.centroSaludDTO = entregaSolicitudDTO.getSolicitudMedicamento().getMedico().getCentroSalud();
    }

    public void cargarEntregaActualizar(EntregaSolicitudDTO entregaSolicitudDTO){
        this.dronesDisponibles = this.dronService.listarDronesDisponibles();
        this.pilotosDisponibles = this.pilotoService.listarPilotosDisponibles();
        this.entregaSolicitudDTO = entregaSolicitudDTO;//Poner como dto a la entrega seleccionada
        logger.info("Dron de la entrega {}", this.entregaSolicitudDTO.toString());
        this.dronEscogido = new DronDTO();
        this.pilotoEscogido = new PilotoDTO();
    }

    public void actualizarSolicitud(){
        logger.info("Dron escogido: " + this.dronEscogido.toString());
        logger.info("Piloto escogido: " + this.pilotoEscogido.toString());
        logger.info("Fecha escogida: "+this.entregaSolicitudDTO.getFechaEntrega().toString());
        logger.info("Estado escogida: "+this.entregaSolicitudDTO.getSolicitudMedicamento().getEstado());

        this.entregaSolicitudService.actualizarEntrega(this.entregaSolicitudDTO, this.dronEscogido, this.pilotoEscogido);
        PrimeFaces.current().executeScript("PF('ventanaModalActualizar').hide()");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Solicitud actualizada"));
        cargarDatos();
    }
}
