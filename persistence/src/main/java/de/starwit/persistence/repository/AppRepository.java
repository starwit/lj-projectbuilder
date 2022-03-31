package de.starwit.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import de.starwit.persistence.entity.App;
import de.starwit.persistence.exception.NotificationException;

@Repository
public interface AppRepository extends JpaRepository<App, Long> {

	@Query("SELECT p FROM App p WHERE p.id = ?1")
	public App findAppByIdOrThrowExeption(Long appid) throws NotificationException;

	@Query(value="SELECT * FROM APP p WHERE p.groups REGEXP :groups", nativeQuery=true)
	List<App> findByGroupString(String groups);

    @Query(value = "SELECT count(p) FROM App p WHERE p.template.id = ?1")
    int countAppUsingTemplate(Long templateId);

}
