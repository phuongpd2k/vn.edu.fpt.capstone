package vn.edu.fpt.capstone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import vn.edu.fpt.capstone.service.impl.SpringSecurityAuditorAware;



@SpringBootApplication
@ComponentScan(basePackages = "vn.edu.fpt.capstone")
@EnableAutoConfiguration()
@EnableJpaAuditing(auditorAwareRef="auditorAware")
public class CapstoneApplication {
	
	@Bean
	public AuditorAware<String> auditorAware() {
		return new SpringSecurityAuditorAware();
	}

	public static void main(String[] args) {
		SpringApplication.run(CapstoneApplication.class, args);
	}

}
