package main.kts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import main.kts.model.Authority;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long>{
	Authority findByRole(String role);

}
