package com.drone.vista_operador.controllers;

import com.drone.vista_operador.dtos.OperadorDTO;
import com.drone.vista_operador.services.interfaces.IOperadorService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import lombok.Data;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Data
@SessionScoped
public class LoginControlador {
    @Autowired
    private IOperadorService operadorService;
    private OperadorDTO operadorDTO;

    @PostConstruct
    public void init(){
        this.operadorDTO = new OperadorDTO();
    }
    public String login() {
        if (this.operadorService.validarDatos(this.operadorDTO)){
            return "/menuPrincipalOperador?faces-redirect=true";  // Redirige a la página de venta.xhtml
        } else {
            // Si las credenciales son incorrectas, mostrar un mensaje de error
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Usuario o contraseña incorrecta"));
            return null;  // Permanece en la misma página de login
        }
    }

    public void verificarSesion() {
        if (!isLoggedIn()) {
            try {
                FacesContext.getCurrentInstance()
                        .getExternalContext()
                        .redirect("index.xhtml");
            } catch (IOException e) {
                LoggerFactory.getLogger(LoginControlador.class)
                        .error("Error al redirigir a la página de login", e);
            }
        }
    }

    public boolean isLoggedIn() {
        return operadorDTO != null && operadorDTO.getUsername() != null;
    }
}
