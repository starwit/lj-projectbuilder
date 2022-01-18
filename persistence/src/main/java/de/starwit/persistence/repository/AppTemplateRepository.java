package de.starwit.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.starwit.persistence.entity.AppTemplate;

@Repository
public interface AppTemplateRepository extends JpaRepository<AppTemplate, Long> {
    List<AppTemplate> findByGroups(List<String> groups);
}
