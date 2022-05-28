package de.starwit.service.impl;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.starwit.persistence.entity.EnumDef;
import de.starwit.persistence.repository.EnumDefRepository;

@Service
public class EnumDefService implements ServiceInterface<EnumDef, EnumDefRepository> {

    @Autowired
    private EnumDefRepository repository;

    @Override
    public EnumDefRepository getRepository() {
        return repository;
    }

    public List<EnumDef> findAllEnumsByApp(Long appId) {
        return this.getRepository().findAllEnumsByApp(appId);
    }

    public EnumDef findByAppAndEnumId(Long appId, Long enumId) {
        return this.getRepository().findByAppAndEnumId(appId, enumId)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(enumId)));
    }
}
