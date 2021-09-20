package de.starwit.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import de.starwit.persistence.entity.App;
import de.starwit.persistence.exception.NotificationException;

@Repository
public interface AppRepository extends JpaRepository<App, Long> {

	@Query("SELECT p FROM App p WHERE p.id = ?1")
	public App findAppByIdOrThrowExeption(Long appid) throws NotificationException;
	


}
