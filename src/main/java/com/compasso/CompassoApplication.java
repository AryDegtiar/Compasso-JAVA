package com.compasso;

import com.compasso.actores.Administrador;
import com.compasso.productos.Producto;
import com.compasso.repositorios.RepositorioAdministrador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@SpringBootApplication
public class CompassoApplication {

	@Autowired
	RepositorioAdministrador repositorioAdministrador;

	public static void main(String[] args) {
		SpringApplication.run(CompassoApplication.class, args);
	}

	@Bean
	public CommandLineRunner init() {
		return (args) -> {
			if (args.length > 0) {
				System.out.println(args[0]);
			}

			new File("uploads").mkdirs();

			//repositorioAdministrador.save( new Administrador("adminCompasso","Comp@ssoCompostela") );

			Administrador administrador =  new Administrador("admin","123");
			repositorioAdministrador.save(administrador);


		};
	}

}
