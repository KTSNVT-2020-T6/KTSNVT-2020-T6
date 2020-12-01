package main.kts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import main.kts.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{
	@Query(value = "SELECT * FROM POST WHERE IMAGE_ID = ?1", nativeQuery = true)
	Post findOneByImageId(Long id);
}
