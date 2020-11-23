package main.kts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import main.kts.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

}
