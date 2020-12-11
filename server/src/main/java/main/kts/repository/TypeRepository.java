package main.kts.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import main.kts.model.Type;

@Repository
public interface TypeRepository extends JpaRepository<Type, Long>{

	List<Type> findByActive(boolean b);

	Optional<Type> findByIdAndActive(Long id, boolean b);

	Page<Type> findByActive(Pageable pageable, boolean b);

	@Query(value = "SELECT * FROM TYPE WHERE CATEGORY_ID = ?1", nativeQuery = true)
	List<Type> findTypesOfCategory(Long id);

}
