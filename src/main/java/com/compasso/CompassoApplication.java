package com.compasso;

import com.compasso.actores.Administrador;
import com.compasso.repositorios.RepositorioAdministrador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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

			//repositorioAdministrador.save( new Administrador("adminCompasso","Comp@ssoCompostela") );
			repositorioAdministrador.save( new Administrador("test","123") );

		};
	}

}
