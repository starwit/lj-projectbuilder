package de.starwit.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import de.starwit.persistence.entity.Domain;

@Repository
public interface DomainRepository extends JpaRepository<Domain, Long> {

	@Query("SELECT d FROM DomainEntity d WHERE d.app.id = ?1")
	List<Domain> findAllDomainsByApp(Long appId);

	@Modifying
	@Query("UPDATE DomainEntity d SET d.selected = ?2 WHERE d.id = ?1")
	void setDomainSelected(Long domainId, boolean selected);
}
