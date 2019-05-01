package northwind.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;

@Entity
public class Permission implements GrantedAuthority{

	public static final String READ = "READ";
	public static final String WRITE = "WRITE";
	public static final String NONE = "NONE";
	private static final long serialVersionUID = 1L;
	@Id 
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	@Column(name="ID")
	private String permissionID;
	
	@JoinColumn(name="USER_ID")
	@ManyToOne()
	private User userID;
	
	@Column(name="PERMISSION")
	private String permission;
		
	public Permission(String permission) {
		super();
		this.permission = permission;
	}
	

	public Permission() {
		super();
	}



	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	@Override
	public String getAuthority() {
		return permission;
	}

	public User getUserID() {
		return userID;
	}

	public void setUserID(User userID) {
		this.userID = userID;
	}


	@Override
	public String toString() {
		return "Permission [permission=" + permission + "]";
	}

	
	
}
