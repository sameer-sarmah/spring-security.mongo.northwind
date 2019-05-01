package northwind.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import northwind.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, String>{
	
	@Query("SELECT u FROM User u where u.userName = :username") 
    Optional<User> findByUsername(@Param("username") String username);
}
