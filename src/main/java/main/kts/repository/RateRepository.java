package main.kts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import main.kts.model.Rate;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long>{

}
