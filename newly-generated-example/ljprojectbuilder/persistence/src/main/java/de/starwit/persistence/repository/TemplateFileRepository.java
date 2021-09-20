package de.starwit.persistence.repository;

import de.starwit.persistence.entity.TemplateFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * TemplateFile Repository class
 */
@Repository
public interface TemplateFileRepository extends JpaRepository<TemplateFile, Long> {

}
