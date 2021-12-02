package de.starwit.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.starwit.persistence.entity.Attribute;
import de.starwit.persistence.repository.AttributeRepository;

@Service
public class AttributeService implements ServiceInterface<Attribute, AttributeRepository> {
    
    @Autowired
    private AttributeRepository attributeRepository;

    @Override
    public AttributeRepository getRepository() {
        return attributeRepository;
    }

}



    
