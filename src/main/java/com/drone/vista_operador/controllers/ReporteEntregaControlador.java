package com.drone.vista_operador.controllers;

import com.drone.vista_operador.dtos.CentroSaludDTO;
import com.drone.vista_operador.dtos.EntregaSolicitudDTO;
import com.drone.vista_operador.dtos.MedicoDTO;
import com.drone.vista_operador.modelo.enums.EstadoEntregaSolicitudEnum;
import com.drone.vista_operador.services.interfaces.IEntregaSolicitudService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@ViewScoped
@Component
@Data
public class ReporteEntregaControlador {
    @Autowired
    private IEntregaSolicitudService entregaSolicitudService;
    private List<EntregaSolicitudDTO> entregasCanceladas;
    private List<EntregaSolicitudDTO>entregasCompletadas;
    private MedicoDTO medicoDTO;
    private CentroSaludDTO centroSaludDTO;
    private static final Logger logger = LoggerFactory.getLogger(ReporteEntregaControlador.class);


    @PostConstruct
    public void init(){
        cargarDatos();
    }

    public void cargarDatos(){
        this.entregasCanceladas = this.entregaSolicitudService.listarEntregasCanceladas();
        this.entregasCompletadas = this.entregaSolicitudService.listarEntregasEntregadas();
        this.medicoDTO = new MedicoDTO();
        this.centroSaludDTO = new CentroSaludDTO();
    }

    public void cargarMedico(EntregaSolicitudDTO entregaSolicitudDTO){
        this.medicoDTO = entregaSolicitudDTO.getSolicitudMedicamento().getMedico();
    }

    public void cargarCentroSalud(EntregaSolicitudDTO entregaSolicitudDTO){
        this.centroSaludDTO = entregaSolicitudDTO.getSolicitudMedicamento().getMedico().getCentroSalud();
    }

    public int getTotalEntregasCanceladas() {
        return entregasCanceladas != null ? entregasCanceladas.size() : 0;
    }

    public int getTotalEntregasCompletadas() {
        return entregasCompletadas != null ? entregasCompletadas.size() : 0;
    }

}
