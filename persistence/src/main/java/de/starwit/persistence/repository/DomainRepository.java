package de.starwit.persistence.repository;

import de.starwit.persistence.entity.Domain;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DomainRepository extends JpaRepository<Domain, Long> {
    @Query("SELECT d FROM Domain d WHERE d.app.id = ?1")
    List<Domain> findAllDomainsByApp(Long appId);

    @Query("SELECT d FROM Domain d WHERE d.app.id = ?1 and d.name = ?2")
    List<Domain> findByAppAndDomainName(Long appId, String domainName);

    @Query("SELECT d FROM Domain d WHERE d.app.id = ?1 and d.id = ?2")
    Optional<Domain> findByAppAndDomainId(Long appId, Long domainId);
}
