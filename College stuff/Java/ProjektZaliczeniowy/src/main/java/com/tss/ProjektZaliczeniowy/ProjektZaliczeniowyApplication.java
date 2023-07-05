package com.tss.ProjektZaliczeniowy;

import com.tss.entities.Customer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import com.tss.repositories.CustomerRepository;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages={"com.tss"})
@EnableJpaRepositories(basePackages={"com.tss.repositories"})
@EnableTransactionManagement
@EntityScan(basePackages={"com.tss.entities"})
public class ProjektZaliczeniowyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjektZaliczeniowyApplication.class, args);
	}
        
    @Bean
    public CommandLineRunner databaseInitialize(CustomerRepository repository) {
        return (args) -> {
            repository.save(new Customer("Jacek", "Nowak"));
            repository.save(new Customer("Katarzyna", "Osowska"));
            repository.save(new Customer("Rafał", "Kowalski"));
            repository.save(new Customer("Dawid", "Makowiecki"));
            repository.save(new Customer("Michał", "Nowakowski"));
        };
    }
}
