package main.kts.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import main.kts.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{

	@Query(value = "SELECT * FROM COMMENT WHERE CULTURAL_OFFER_ID = ?1", nativeQuery = true)
	List<Comment> findAllByCulturalOfferId(Long id);

	List<Comment> findByActive(boolean b);

	Optional<Comment> findByIdAndActive(Long id, boolean b);

	Page<Comment> findByActive(Pageable pageable, boolean b);

}
