package main.kts.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import main.kts.model.CulturalOffer;

@Repository
public interface CulturalOfferRepository extends JpaRepository<CulturalOffer, Long>{

	List<CulturalOffer> findByActive(boolean b);

	Page<CulturalOffer> findByActive(Pageable pageable, boolean b);

	Optional<CulturalOffer> findByIdAndActive(Long id, boolean b);
	
	/*
	@Query(value ="SELECT * FROM CULTURAL_OFFER WHERE upper(CITY) LIKE %?1%", nativeQuery = true)
	List<CulturalOffer> findByCity(String city);

	@Query(value ="SELECT * FROM CULTURAL_OFFER WHERE upper(NAME) LIKE %?1% OR upper(DESCRIPTION) LIKE %?1% OR upper(CITY) LIKE %?1%", nativeQuery = true)
	List<CulturalOffer> findByContent(String content);
	*/

	@Query(value ="SELECT * FROM CULTURAL_OFFER WHERE (upper(NAME) LIKE %?1% OR upper(DESCRIPTION) LIKE %?1%) AND (upper(CITY) like %?2%)", nativeQuery = true)
	Page<CulturalOffer> findByCombinedSearch(Pageable pageable, String upperCase, String upperCase2);

	List<CulturalOffer> findByTypeId(Long id);
	
	

}
