package northwind.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import northwind.config.SecurityConfig;
import northwind.listner.NorthwindApplicationListener;
import northwind.repositories.UserRepo;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"northwind"})
@EntityScan(basePackages = {"northwind"})
@ComponentScan(basePackages = {"northwind"})
@EnableMongoRepositories(basePackages = { "northwind" }, mongoTemplateRef = "mongo-northwind")
@Import(SecurityConfig.class)
public class NorthwindApplication  extends SpringBootServletInitializer {

	@Autowired
	private UserRepo userRepo;
	
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    	application.listeners(new NorthwindApplicationListener());
    	return application.sources(NorthwindApplication.class);
    }
	
	public static void main(String[] args) {
		SpringApplication.run(NorthwindApplication.class, args);
		System.err.println("##########");
		
	}

}
