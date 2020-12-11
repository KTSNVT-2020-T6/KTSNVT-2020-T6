package main.kts.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import main.kts.model.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long>{

	@Query(value = "SELECT * FROM IMAGE WHERE CULTURAL_OFFER_ID = ?1", nativeQuery = true)
	Set<Image> findAllByCulturalOfferId(Long id);

	List<Image> findByActive(boolean b);

	Optional<Image> findByIdAndActive(Long id, boolean b);

	Page<Image> findByActive(Pageable pageable, boolean b);

}
