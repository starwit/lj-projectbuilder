package de.starwit.persistence.repository;

import de.starwit.persistence.entity.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Domain Repository class
 */
@Repository
public interface AttributeRepository extends JpaRepository<Attribute, Long> {}
