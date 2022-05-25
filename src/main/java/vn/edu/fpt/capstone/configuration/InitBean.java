package vn.edu.fpt.capstone.configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.edu.fpt.capstone.service.impl.SpringSecurityAuditorAware;

@Configuration
public class InitBean {
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return modelMapper;
	}

	@Bean
	public AuditorAware<String> auditorAware() {
		return new SpringSecurityAuditorAware();
	}

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
//	@Bean
//	public Cloudinary cloudinaryConfig() {
//		Cloudinary cloudinary = null;
//		java.util.Map<String, String> config = new HashMap<String, String>();
//		config.put("cloud_name", "dplsph2oc");
//		config.put("api_key", "724686778355764");
//		config.put("api_secret", "uRYWm-XyG4Po9EyDlrQ208HTjd8");
//		cloudinary = new Cloudinary(config);
//		return cloudinary;
//	}

}
