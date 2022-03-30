package de.starwit.persistence.repository;

import de.starwit.persistence.entity.TemplateFile;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplateFileRepository extends JpaRepository<TemplateFile, Long> {
    @Query("SELECT tf FROM TemplateFile tf WHERE tf.appTemplate.id = ?1")
    public Set<TemplateFile> findAllByAppTemplate(Long appTemplateId);
}
