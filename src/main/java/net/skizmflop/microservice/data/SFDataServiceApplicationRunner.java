package net.skizmflop.microservice.data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import net.skizmflop.commons.jpa.entity.County;
import net.skizmflop.commons.jpa.entity.State;

/**
 * Data Service Spring Boot Application Runner
 * @author jack
 */
@SpringBootApplication
@EnableEurekaClient
@ComponentScan(basePackages="net.skizmflop.microservice.data")
@EntityScan("net.skizmflop.commons.jpa.entity")
@Configuration 
public class SFDataServiceApplicationRunner {
	
	public static void main(String[] args) {
		SpringApplication.run(SFDataServiceApplicationRunner.class, args);
	}
	
	@Bean
	public State getState() {
		return new State();
	}
	
	@Bean
	public County getCounty() {
		return new County();
	}
	
}
