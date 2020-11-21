package de.starwit.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.starwit.persistence.entity.AttributeEntity;

/**
 * Domain Repository class
 */
@Repository
public interface AttributeRepository extends JpaRepository<AttributeEntity, Long> {

}
