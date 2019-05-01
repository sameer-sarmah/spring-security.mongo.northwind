import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Runner {

	public static void main(String[] args) {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		String rawPassword="elnino_09";
		String encodedPassword=encoder.encode(rawPassword);
		System.out.println(encodedPassword);
		System.out.println(encoder.matches(rawPassword, encodedPassword));
	}

}
