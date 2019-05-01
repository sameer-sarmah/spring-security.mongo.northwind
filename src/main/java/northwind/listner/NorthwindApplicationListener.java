package northwind.listner;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import northwind.entity.Permission;
import northwind.entity.User;
import northwind.repositories.UserRepo;

@Component
public class NorthwindApplicationListener implements ApplicationListener<ApplicationEvent> {

	@Autowired
	private UserRepo userRepo;
	
	  @Override
	  public void onApplicationEvent(ApplicationEvent event) {
	     if(event instanceof ApplicationStartedEvent) {
	    	boolean isAdminPersisted = userRepo.findByUsername("admin").isPresent();
	    	if(!isAdminPersisted) {
	    		PasswordEncoder encoder = new BCryptPasswordEncoder();
	    		User sameer = new User("admin", encoder.encode("admin"), "admin", "admin");
	    		Permission read = new Permission(Permission.READ);
	    		read.setUserID(sameer);
	    		Permission write = new Permission(Permission.WRITE);
	    		write.setUserID(sameer);
	    		List<Permission> permissions=Arrays.asList(read,write);
	    		sameer.setPermissions(permissions);
	    		userRepo.save(sameer);
	    	}
	     }
	  }
	
}
