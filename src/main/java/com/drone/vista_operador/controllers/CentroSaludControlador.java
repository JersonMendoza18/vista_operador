package com.drone.vista_operador.controllers;

import com.drone.vista_operador.dtos.CentroSaludDTO;
import com.drone.vista_operador.services.interfaces.ICentroSaludService;
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
@Data
@ViewScoped
public class CentroSaludControlador {
    @Autowired
    private ICentroSaludService centroSaludService;
    private List<CentroSaludDTO> centros;
    private CentroSaludDTO centroSaludDTO;
    private static final Logger logger = LoggerFactory.getLogger(CentroSaludControlador.class);

    @PostConstruct
    public void init(){
        cargarDatos();
    }

    public void cargarDatos(){
        System.out.println("cargando datos");
        this.centros = this.centroSaludService.listarCentros();
        this.centros.forEach(centro->logger.info(centro.toString()));
    }

    public void agregarCentro(){
        this.centroSaludDTO = new CentroSaludDTO();
    }

    //guardar y actualizar
    public void guardarCentro(){
        logger.info("Centro de salud a guardar {}", this.centroSaludDTO.toString());
        //Verificar si guardar o actualizar
        if(this.centroSaludDTO.getId()==null){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Centro de salud agregado"));
        }
        //id no es nulo == actualizar
        else{
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Centro de salud actualizado"));
        }
        //agregar o actualizar dron
        this.centroSaludService.agregar(this.centroSaludDTO);

        //cargar datos
        this.cargarDatos();

        //Ocultar la ventana modal
        PrimeFaces.current().executeScript("PF('ventanaModalCentroSalud').hide()");
        //Actualizar la tabla usando AJAX
        PrimeFaces.current().ajax().update("forma-centroSalud:mensajes", "forma-centroSalud:centroSalud-tabla");
        //Limpiar el centro de salud
        this.centroSaludDTO=null;
    }

    public void eliminarCentro(){
        logger.info("Centro a eliminar {}", this.centroSaludDTO);

        this.centroSaludService.eliminar(this.centroSaludDTO.getId());

        this.centros.remove(this.centroSaludDTO);

        this.centroSaludDTO =null;

        //ocultar el modal
        PrimeFaces.current().executeScript("PF('ventanaModalCentroSalud').hide()");

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Centro de salud eliminado"));

        PrimeFaces.current().ajax().update("forma-centroSalud:mensajes", "forma-centroSalud:centroSalud-tabla");
    }
}
