package de.spring.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import de.spring.persistence.entity.ProjectEntity;
import de.spring.persistence.exception.NotificationException;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {

	@Query("SELECT p FROM ProjectEntity p WHERE p.id = ?1")
	public ProjectEntity findProjectByIdOrThrowExeption(Long projectid) throws NotificationException;
}
