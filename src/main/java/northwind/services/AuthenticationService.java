package northwind.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import northwind.entity.User;
import northwind.repositories.UserRepo;

@Service
public class AuthenticationService implements UserDetailsService {

	private UserRepo userRepo;

	public AuthenticationService(UserRepo userRepo) {
		super();
		this.userRepo = userRepo;
	}

	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepo.findByUsername(username).get();
	}

}
