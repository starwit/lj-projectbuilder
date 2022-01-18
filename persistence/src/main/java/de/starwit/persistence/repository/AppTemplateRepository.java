package de.starwit.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import de.starwit.persistence.entity.AppTemplate;

@Repository
public interface AppTemplateRepository extends JpaRepository<AppTemplate, Long> {

    @Query(value="SELECT * FROM APPTEMPLATE p WHERE p.groups REGEXP :groups", nativeQuery=true)
    public List<AppTemplate> findByGroupString(String groups);
}
