package main.kts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import main.kts.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{

}
