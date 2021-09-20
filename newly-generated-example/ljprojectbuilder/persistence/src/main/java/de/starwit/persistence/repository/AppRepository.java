package de.starwit.persistence.repository;

import de.starwit.persistence.entity.App;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * App Repository class
 */
@Repository
public interface AppRepository extends JpaRepository<App, Long> {

}
