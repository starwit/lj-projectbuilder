package de.spring.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.spring.persistence.entity.AttributeEntity;
import de.spring.persistence.repository.AttributeRepository;

@Service
public class AttributeService {
    
    @Autowired
    private AttributeRepository attributeRepository;

    public List<AttributeEntity> findAll() {
        return this.attributeRepository.findAll();
    }

    public AttributeEntity findById(Long id) {
        return this.attributeRepository.findById(id).orElse(null);
    }

    public AttributeEntity saveOrUpdate(AttributeEntity entity) {
        return this.attributeRepository.save(entity);
    }

    public AttributeEntity delete(AttributeEntity entity) {
        this.attributeRepository.delete(entity);
        return entity;
    }

}



    
