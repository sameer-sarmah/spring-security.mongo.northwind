package northwind.services;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import northwind.entity.Permission;
import northwind.entity.User;
import northwind.repositories.UserRepo;

@Service
public class AuthorizationService {

	private UserRepo userRepo;
	
	public AuthorizationService(UserRepo userRepo) {
		super();
		this.userRepo = userRepo;
	}
	
	private boolean hasReadPermission(String username) {
		User user = this.userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("user not found"));
		return this.checkPermission(user, Permission.READ);
	}
	
	private boolean hasWritePermission(String username) {
		User user = this.userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("user not found"));
        return this.checkPermission(user, Permission.WRITE);
	}
	
	private boolean checkPermission(User user,String permissionStr) {
		if(user != null) {
			List<Permission> permissions = user.getPermissions();
			for(Permission permission:permissions) {
				if(permission.getPermission().equalsIgnoreCase(permissionStr)) {
					return true;
				}
			}
		}
		return false;
	}
	
	private String getCurrentUser() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String username = authentication.getName();
    	return username;
	}
	
    public boolean canRead() {
    	return this.hasReadPermission(getCurrentUser());
    }
    
    public boolean canCreate() {
    	return this.hasWritePermission(getCurrentUser());
    }
    
    public boolean canUpdate() {
    	return this.hasWritePermission(getCurrentUser());
    }
    
    public boolean canDelete() {
    	return this.hasWritePermission(getCurrentUser());
    }
    
    
    
}