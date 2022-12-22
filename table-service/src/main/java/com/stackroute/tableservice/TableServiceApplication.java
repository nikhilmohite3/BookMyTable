package com.stackroute.tableservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@SpringBootApplication
@EnableEurekaClient
@OpenAPIDefinition(info = @Info(title = "Table Service", description = "Below are the APIs for the TABLE-SERVICE"))
public class TableServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TableServiceApplication.class, args);
	}

}
