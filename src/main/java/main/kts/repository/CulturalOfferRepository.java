package main.kts.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import main.kts.model.CulturalOffer;

@Repository
public interface CulturalOfferRepository extends JpaRepository<CulturalOffer, Long>{

	List<CulturalOffer> findByActive(boolean b);

	Page<CulturalOffer> findByActive(Pageable pageable, boolean b);

	Optional<CulturalOffer> findByIdAndActive(Long id, boolean b);

}
