package de.starwit.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.starwit.persistence.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	
	Category findByName(String name);
}


    
