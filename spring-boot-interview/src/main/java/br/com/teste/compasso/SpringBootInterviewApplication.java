package br.com.teste.compasso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ComponentScan(basePackages = "br.com.teste.compasso.endpoint.controller")
@EntityScan("br.com.teste.compasso.model")
@EnableJpaRepositories("br.com.teste.compasso.repositorio")
@EnableSwagger2
@ComponentScan("br.com.teste.compasso")
public class SpringBootInterviewApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootInterviewApplication.class, args);
	}

}
