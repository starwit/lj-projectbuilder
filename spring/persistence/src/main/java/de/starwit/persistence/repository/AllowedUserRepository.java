package de.starwit.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.starwit.persistence.entity.AllowedUserEntity;

@Repository
public interface AllowedUserRepository extends JpaRepository<AllowedUserEntity, Long> {
    AllowedUserEntity findByUserAlias(String alias);
}
