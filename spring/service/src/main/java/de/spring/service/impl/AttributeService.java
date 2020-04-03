package de.spring.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class AttributeService {
    
    @Autowired
    private AttributeRepository attributeRepository;

    public List<AttributeEntity> findAll() {
        return this.attributeRepository.findAll();
    }

    public AttributeEntity findById(Long id) {
        return this.attributeRepository.findById(id);
    }

    public AttributeEntity saveOrUpdate(AttributeEntity entity) {
        return this.attributeRepository.saveOrUpdate(entity);
    }

    public void delete(AttributeEntity entity) {
        this.attributeRepository.delete(entity);
    }

}



    
