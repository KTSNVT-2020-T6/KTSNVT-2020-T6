package main.kts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import main.kts.model.Rate;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long>{

	@Query(value = "SELECT * FROM RATE WHERE REGISTRED_USER_ID = ?1", nativeQuery = true)
	Rate findOneByRegisteredUserId(Long id);
	
	@Query(value = "SELECT * FROM RATE WHERE CULTURAL_OFFER_ID = ?1", nativeQuery = true)
	List<Rate> findAllByCulturalOfferId(Long id);
	
}
