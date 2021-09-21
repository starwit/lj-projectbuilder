package de.starwit.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import de.starwit.persistence.entity.CodeTemplateEntity;

@Repository
public interface CodeTemplateRepository extends JpaRepository<CodeTemplateEntity, Long> {

	@Query("SELECT c FROM CodeTemplateEntity c JOIN c.apps p WHERE p.id = ?1")
	List<CodeTemplateEntity> findAllCodeTemplatesByApp(Long appId);

	@Query("SELECT c FROM CodeTemplateEntity c WHERE c.appTemplate.id = ?1")
	List<CodeTemplateEntity> findAllCodeTemplatesByAppTemplate(Long appTemplateId);

	@Query("SELECT c.id FROM CodeTemplateEntity c WHERE c.appTemplate.id = ?1")
	List<Long> findAllCodeTemplateIdsByAppTemplate(Long appTemplateId);
}
