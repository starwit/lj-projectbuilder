package de.starwit.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.starwit.persistence.entity.AppTemplateEntity;

@Repository
public interface AppTemplateRepository extends JpaRepository<AppTemplateEntity, Long> {
    
}
