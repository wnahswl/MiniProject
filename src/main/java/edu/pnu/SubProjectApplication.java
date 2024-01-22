package edu.pnu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;

@SpringBootApplication
public class SubProjectApplication {

	public static void main(String[] args) {
        SpringApplication.run(SubProjectApplication.class, args);
    }
	
	@Bean
	 PageableHandlerMethodArgumentResolverCustomizer customize() {
		return p->p.setOneIndexedParameters(true);
	}

  
 
}