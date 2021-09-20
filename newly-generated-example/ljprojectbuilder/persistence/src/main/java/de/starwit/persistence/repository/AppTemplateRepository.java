package de.starwit.persistence.repository;

import de.starwit.persistence.entity.AppTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * AppTemplate Repository class
 */
@Repository
public interface AppTemplateRepository extends JpaRepository<AppTemplate, Long> {

}
