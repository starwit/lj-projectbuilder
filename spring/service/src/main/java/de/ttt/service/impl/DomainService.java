package de.ttt.service.impl;

import de.ttt.persistence.entity.Domain;
import de.ttt.persistence.repository.DomainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;

/**
 * 
 * Domain Service class
 *
 */
@Service
public class DomainService {

    @Autowired
    private DomainRepository domainRepository;

    /**
     * @return
     */
    public List<Domain> findAll() {
        return this.domainRepository.findAll();
    }

    /**
     * @param id
     * @return
     */
    public Domain findById(Long id) {
        return this.domainRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
    }

    /**
     * @param domain
     * @return
     */
    public Domain saveOrUpdate(Domain domain) {
        this.domainRepository.save(domain);
        return domain;
    }

    /**
     * @param domain
     */
    public void delete(Domain domain) {
        this.domainRepository.delete(domain);
    }

}
