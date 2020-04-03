package de.spring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Domain Service class
 *
 */
@Service
public class DomainService {

    /**
     * @return
     */
    public List<String> findAll() {
        // return this.domainRepository.findAll();
        return new ArrayList<>();
    }

    /**
     * @param id
     * @return
     */
    public String findById(Long id) {
        //return this.domainRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
        return "";
    }

    /**
     * @param domain
     * @return
     */
    public String saveOrUpdate(String domain) {
        //this.domainRepository.save(domain);
        return domain;
    }

    /**
     * @param domain
     */
    public void delete(String domain) {
        //this.domainRepository.delete(domain);
    }

}
