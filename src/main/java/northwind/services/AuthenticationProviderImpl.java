package northwind.services;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import northwind.entity.User;

@Service("authenticationProvider")
public class AuthenticationProviderImpl implements AuthenticationProvider {

	private AuthenticationService authenticationService;
	
	public AuthenticationProviderImpl(AuthenticationService authenticationService) {
		super();
		this.authenticationService = authenticationService;
	}

	public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        String username = token.getName();
        User user = authenticationService.loadUserByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("Invalid username/password");
        }
        String password = user.getPassword(); 
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        if(!encoder.matches((CharSequence) token.getCredentials(), user.getPassword())) {
            throw new BadCredentialsException("Invalid username/password");
        }
        return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }


}
