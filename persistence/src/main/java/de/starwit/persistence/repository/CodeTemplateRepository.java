package de.starwit.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import de.starwit.persistence.entity.TemplateFile;

@Repository
public interface CodeTemplateRepository extends JpaRepository<TemplateFile, Long> {

	@Query("SELECT c FROM CodeTemplateEntity c JOIN c.apps p WHERE p.id = ?1")
	List<TemplateFile> findAllCodeTemplatesByApp(Long appId);

	@Query("SELECT c FROM CodeTemplateEntity c WHERE c.appTemplate.id = ?1")
	List<TemplateFile> findAllCodeTemplatesByAppTemplate(Long appTemplateId);

	@Query("SELECT c.id FROM CodeTemplateEntity c WHERE c.appTemplate.id = ?1")
	List<Long> findAllCodeTemplateIdsByAppTemplate(Long appTemplateId);
}
