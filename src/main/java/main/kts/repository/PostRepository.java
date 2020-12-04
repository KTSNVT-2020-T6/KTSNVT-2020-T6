package main.kts.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import main.kts.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{
	
	@Query(value = "SELECT * FROM POST WHERE IMAGE_ID = ?1", nativeQuery = true)
	Post findOneByImageId(Long id);

	List<Post> findByActive(boolean b);

	Optional<Post> findByIdAndActive(Long id, boolean b);

	Page<Post> findByActive(Pageable pageable, boolean b);
}
