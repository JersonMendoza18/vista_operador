package com.drone.vista_operador.controllers;

import com.drone.vista_operador.dtos.CentroSaludDTO;
import com.drone.vista_operador.dtos.MedicoDTO;
import com.drone.vista_operador.services.interfaces.ICentroSaludService;
import com.drone.vista_operador.services.interfaces.IMedicoService;
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

import java.util.List;

@Component
@ViewScoped
@Data
public class MedicoControlador {
    @Autowired
    private IMedicoService medicoService;
    @Autowired
    private ICentroSaludService centroSaludService;
    private List<MedicoDTO> medicos;
    private MedicoDTO medicoDTO;
    private List<String> nombresCentroSalud;
    private static final Logger logger = LoggerFactory.getLogger(MedicoControlador.class);

    @PostConstruct
    public void init(){
        cargarDatos();
    }

    public void cargarDatos(){
        this.medicos = this.medicoService.listarMedicos();
        this.medicos.forEach(medico->logger.info(medico.toString()));
        this.nombresCentroSalud = this.centroSaludService.listarNombes();
        this.nombresCentroSalud.forEach(logger::info);
    }

    public void agregarMedico(){
        this.medicoDTO = new MedicoDTO();
        this.medicoDTO.setCentroSalud(new CentroSaludDTO());
    }

    public void guardarMedico(){
        logger.info("Guardar medico");
        if(this.medicoService.existeMedico(medicoDTO.getCi())){
            this.medicoService.agregar(medicoDTO,medicoDTO.getCentroSalud().getNombre());
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Medico actualizado"));
        }
        else{
            this.medicoService.agregar(medicoDTO,medicoDTO.getCentroSalud().getNombre());
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Medico agregado"));
        }
        //cargar datos
        this.cargarDatos();

        //Ocultar ventana modal
        PrimeFaces.current().executeScript("PF('ventanaModalMedico').hide()");
        //Actualizar la tabla usando AJAX
        PrimeFaces.current().ajax().update("forma-medico:mensajes", "forma-medico:medico-tabla");
        //Reset del clienteSeleccionado
        this.medicoDTO = null;
    }

    public void eliminarMedico(){
        logger.info("Medico a eliminar {}", this.medicoDTO);

        this.medicoService.eliminar(this.medicoDTO.getCi());

        this.medicos.remove(this.medicoDTO);

        this.medicoDTO =null;

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Medico eliminado"));

        PrimeFaces.current().ajax().update("forma-medico:mensajes", "forma-medico:medico-tabla");
    }
}
