package de.starwit.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.starwit.persistence.entity.ProjectTemplateEntity;

@Repository
public interface ProjectTemplateRepository extends JpaRepository<ProjectTemplateEntity, Long> {
    
}
