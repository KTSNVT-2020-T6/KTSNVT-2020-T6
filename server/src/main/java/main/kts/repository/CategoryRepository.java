package main.kts.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import main.kts.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

	List<Category> findByActive(boolean b);

	Optional<Category> findByIdAndActive(Long id, boolean b);

	Page<Category> findByActive(Pageable pageable, boolean b);

}
