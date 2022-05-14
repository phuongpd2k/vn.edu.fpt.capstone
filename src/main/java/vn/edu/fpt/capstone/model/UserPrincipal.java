package vn.edu.fpt.capstone.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


public class UserPrincipal implements UserDetails{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private UserModel user;
    
    public UserPrincipal(final UserModel user) {
        this.user = user;
    }
    
    public UserModel getUser() {
		return user;
	}


	public void setUser(UserModel user) {
		this.user = user;
	}


	public Collection<? extends GrantedAuthority> getAuthorities() {
        final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (final RoleModel role : this.user.getRoles()) {
            authorities.add((GrantedAuthority)new SimpleGrantedAuthority(role.getRole()));
        }
        return authorities;
    }
    
    public String getPassword() {
        return this.user.getPassword();
    }
    
    public String getUsername() {
        return this.user.getUsername();
    }
    
    public boolean isAccountNonExpired() {
        return true;
    }
    
    public boolean isAccountNonLocked() {
        return true;
    }
    
    public boolean isCredentialsNonExpired() {
        return true;
    }
    
    public boolean isEnabled() {
        return true;
    }
}
