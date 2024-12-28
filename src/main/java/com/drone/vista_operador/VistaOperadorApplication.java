package com.drone.vista_operador;
import com.drone.vista_operador.services.interfaces.IEntregaSolicitudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VistaOperadorApplication implements CommandLineRunner {
	@Autowired
	private IEntregaSolicitudService entregaSolicitudService;
	private final Logger logger = LoggerFactory.getLogger(VistaOperadorApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(VistaOperadorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	}
}
