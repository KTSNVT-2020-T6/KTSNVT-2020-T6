package main.kts.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import main.kts.model.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long>{

	@Query(value = "SELECT * FROM IMAGE WHERE CULTURAL_OFFER_ID = ?1", nativeQuery = true)
	Set<Image> findAllByCulturalOfferId(Long id);

}
