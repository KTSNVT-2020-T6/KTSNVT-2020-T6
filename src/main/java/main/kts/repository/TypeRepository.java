package main.kts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import main.kts.model.Type;

@Repository
public interface TypeRepository extends JpaRepository<Type, Long>{

}
