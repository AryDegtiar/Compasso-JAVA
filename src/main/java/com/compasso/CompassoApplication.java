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

@SpringBootApplication
public class CompassoApplication {

	@Autowired
	RepositorioAdministrador repositorioAdministrador;

	public static void main(String[] args) {
		SpringApplication.run(CompassoApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("*").allowedHeaders("*").allowedMethods("GET", "POST", "PATCH", "PUT", "DELETE");
			}
		};
	}


	@Bean
	public CommandLineRunner init() {
		return (args) -> {
			if (args.length > 0) {
				System.out.println(args[0]);
			}

			//repositorioAdministrador.save( new Administrador("adminCompasso","Comp@ssoCompostela") );

			//Administrador administrador =  new Administrador("test","123");
			//repositorioAdministrador.save(administrador);


		};
	}

}
