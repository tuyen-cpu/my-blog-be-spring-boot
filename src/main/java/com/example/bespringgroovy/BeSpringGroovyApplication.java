package com.example.bespringgroovy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
public class BeSpringGroovyApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeSpringGroovyApplication.class, args);
	}

}
