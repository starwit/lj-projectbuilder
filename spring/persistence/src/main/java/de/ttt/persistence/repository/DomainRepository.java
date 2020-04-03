package de.ttt.persistence.repository;

import de.ttt.persistence.entity.Domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Domain Repository class
 */
@Repository
public interface DomainRepository extends JpaRepository<Domain, Long> {

}
