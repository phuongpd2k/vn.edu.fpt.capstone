package vn.edu.fpt.capstone.configuration;

import java.util.HashMap;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;

@Configuration
public class InitBean {
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
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
