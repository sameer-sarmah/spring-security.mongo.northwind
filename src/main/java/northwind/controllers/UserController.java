package northwind.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import northwind.entity.Permission;
import northwind.entity.User;
import northwind.repositories.UserRepo;
import northwind.services.AuthorizationService;

@RestController
public class UserController {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private AuthorizationService authorizationService;
	
	@PreAuthorize("@authorizationService.canRead()")
	@ResponseBody
	@GetMapping("api/user")
	public User getCurrentUser() {
		String username = getCurrentUserName();
		User currentUser= this.userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("user not found"));
		currentUser.setPassword(null);
		return currentUser;
	}
	
	private String getCurrentUserName() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String username = authentication.getName();
    	return username;
	}

	@PreAuthorize("@authorizationService.canCreate()")
	@ResponseBody
	@PostMapping("api/user")
	public User createUser(@RequestBody User user) {
		String encodedPassword = encoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		User createdUser=  userRepo.save(user);
		createdUser.setPassword(null);
		return createdUser;
	}
	
	@PreAuthorize("@authorizationService.canCreate()")
	@ResponseBody
	@PostMapping("api/user/{username}/{permission}")
	public User addPermission(@PathVariable("username") String username, @PathVariable("permission") String permission) {
		User user = userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("user not found"));
		if(user != null) {
			Permission perm = null;
			if(permission.equalsIgnoreCase(Permission.READ)) {
				 perm = new Permission(Permission.READ);
			}
			else if(permission.equalsIgnoreCase(Permission.WRITE)) {
				 perm = new Permission(Permission.WRITE);
			}
			perm.setUserID(user);
			user.addGrantedAuthority(perm);
			User updatedUser=  userRepo.save(user);
			updatedUser.setPassword(null);
			return updatedUser;
		}
		return user;
	}

}
