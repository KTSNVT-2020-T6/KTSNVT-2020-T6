package main.kts.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import main.kts.model.Rate;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long>{

	@Query(value = "SELECT * FROM RATE WHERE REGISTRED_USER_ID = ?1 AND CULTURAL_OFFER_ID = ?2 AND ACTIVE = TRUE", nativeQuery = true)
	Rate findOneByRegisteredUserIdAndCulturalOfferId(Long id, Long id2);
	
	@Query(value = "SELECT * FROM RATE WHERE CULTURAL_OFFER_ID = ?1 AND ACTIVE = TRUE", nativeQuery = true)
	List<Rate> findAllByCulturalOfferId(Long id);

	List<Rate> findByActive(boolean b);

	Optional<Rate> findByIdAndActive(Long id, boolean b);

	Page<Rate> findByActive(Pageable pageable, boolean b);
	
}
