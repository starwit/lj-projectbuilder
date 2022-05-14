package de.starwit.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import de.starwit.persistence.entity.EnumDef;

/**
 * Domain Repository class
 */
@Repository
public interface EnumDefRepository extends JpaRepository<EnumDef, Long> {

    @Query("SELECT e FROM EnumDef e WHERE e.app.id = ?1")
    List<EnumDef> findAllEnumsByApp(Long appId);

    @Query("SELECT e FROM EnumDef e WHERE e.app.id = ?1 and e.id = ?2")
    Optional<EnumDef> findByAppAndEnumId(Long appId, Long enumId);

}
