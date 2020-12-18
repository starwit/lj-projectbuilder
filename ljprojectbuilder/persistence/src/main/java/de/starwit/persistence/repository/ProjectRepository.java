package de.starwit.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import de.starwit.persistence.entity.ProjectEntity;
import de.starwit.persistence.exception.NotificationException;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {

	@Query("SELECT p FROM ProjectEntity p WHERE p.id = ?1")
	public ProjectEntity findProjectByIdOrThrowExeption(Long projectid) throws NotificationException;
	


}
