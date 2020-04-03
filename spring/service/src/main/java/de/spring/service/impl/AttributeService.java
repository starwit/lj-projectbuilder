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

}



    
