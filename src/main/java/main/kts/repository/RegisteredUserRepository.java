package main.kts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import main.kts.model.RegisteredUser;

@Repository
public interface RegisteredUserRepository extends JpaRepository<RegisteredUser, Long>{
	RegisteredUser findByEmail(String email);
	

	@Query(value = "SELECT * FROM USER WHERE TYPE == 'registered_user'", nativeQuery = true)
	List<RegisteredUser> findAllRegisteredUser();
}
