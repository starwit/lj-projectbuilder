package de.starwit.service.impl;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.starwit.persistence.entity.EnumDef;
import de.starwit.persistence.exception.NotificationException;
import de.starwit.persistence.repository.AttributeRepository;
import de.starwit.persistence.repository.EnumDefRepository;

@Service
public class EnumDefService implements ServiceInterface<EnumDef, EnumDefRepository> {

    @Autowired
    private EnumDefRepository repository;

    @Autowired
    private AttributeRepository attributeRepository;

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

    @Override
    public void delete(Long id) throws NotificationException {
        int count = attributeRepository.countDomainsUsingEnum(id);
        if (count > 0) {
            throw new NotificationException("error.enum.delete.enititesexists",
                    "Deletion of enum used by entity not possible");
        }
        this.getRepository().deleteById(id);
    }
}
