package main.kts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import main.kts.model.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long>{

}
