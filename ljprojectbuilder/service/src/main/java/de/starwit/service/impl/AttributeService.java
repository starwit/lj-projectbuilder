package de.starwit.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.starwit.persistence.entity.AttributeEntity;
import de.starwit.persistence.repository.AttributeRepository;

@Service
public class AttributeService implements ServiceInterface<AttributeEntity> {
    
    @Autowired
    private AttributeRepository attributeRepository;

    @Override
    public List<AttributeEntity> findAll() {
        return this.attributeRepository.findAll();
    }

    @Override
    public AttributeEntity findById(Long id) {
        return this.attributeRepository.findById(id).orElse(null);
    }

    @Override
    public AttributeEntity saveOrUpdate(AttributeEntity entity) {
        return this.attributeRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        this.attributeRepository.deleteById(id);
    }

}



    
