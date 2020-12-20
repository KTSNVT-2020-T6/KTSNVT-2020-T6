package main.kts.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import main.kts.model.RegisteredUser;

@Repository
public interface RegisteredUserRepository extends JpaRepository<RegisteredUser, Long>{
	
	RegisteredUser findByEmail(String email);
	
	@Query(value = "SELECT * FROM USERS WHERE TYPE = 'registered_user' AND ACTIVE = TRUE", nativeQuery = true)
	List<RegisteredUser> findAllRegisteredUser();

	@Query(value = "SELECT * FROM USERS_FAVORITE_CULTURAL_OFFERS WHERE"
			+ " FAVORITE_CULTURAL_OFFERS_ID = ?1 ", nativeQuery = true)
	List<Long> findByIdCO(Long id);

	@Query(value = "SELECT * FROM USERS WHERE TYPE = 'registered_user' AND id = ?1", nativeQuery = true)
	RegisteredUser findByIdRU(Long l);

	Optional<RegisteredUser> findByIdAndActive(Long id, boolean b);

	RegisteredUser findByEmailAndActive(String email, boolean b);
}
