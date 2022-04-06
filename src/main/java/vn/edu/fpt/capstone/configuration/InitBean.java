package vn.edu.fpt.capstone.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitBean {
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
