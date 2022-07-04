package vn.edu.fpt.capstone.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import vn.edu.fpt.capstone.model.UserModel;
import vn.edu.fpt.capstone.model.UserPrincipal;
import vn.edu.fpt.capstone.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService{
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		//UserModel user = userRepository.findByUsername(username).get();
		UserModel user = userRepository.findByEmail(username).get();
		if(user == null) {
			throw new UsernameNotFoundException("Username not found");
		}
		
		UserPrincipal userPrincipal = new UserPrincipal(user);
		return userPrincipal;
	}
}
