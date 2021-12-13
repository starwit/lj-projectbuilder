package de.starwit.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import de.starwit.persistence.entity.TemplateFile;

@Repository
public interface TemplateFileRepository extends JpaRepository<TemplateFile, Long> {

	@Query("SELECT c FROM TemplateFile c JOIN c.apps p WHERE p.id = ?1")
	List<TemplateFile> findAllTemplateFilesByApp(Long appId);

	@Query("SELECT c FROM TemplateFile c WHERE c.appTemplate.id = ?1")
	List<TemplateFile> findAllTemplateFilesByAppTemplate(Long appTemplateId);

	@Query("SELECT c.id FROM TemplateFile c WHERE c.appTemplate.id = ?1")
	List<Long> findAllTemplateFileIdsByAppTemplate(Long appTemplateId);
}
