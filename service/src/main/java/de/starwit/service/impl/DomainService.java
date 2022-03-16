package de.starwit.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import de.starwit.mapper.Mapper;
import de.starwit.persistence.entity.Domain;
import de.starwit.persistence.entity.Relationship;
import de.starwit.persistence.repository.DomainRepository;

@Service
public class DomainService implements ServiceInterface<Domain, DomainRepository> {

    @Autowired
    private DomainRepository domainRepository;

    static final Logger LOG = LoggerFactory.getLogger(DomainService.class);

    public List<Domain> findAllDomainsByApp(Long appId) {
        List<Domain> entities = this.domainRepository.findAllDomainsByApp(appId);
        return Mapper.convertList(entities, Domain.class, "app");
    }

    public Domain findByAppAndDomainId(Long appId, Long domainId) {
        return this.getRepository().findByAppAndDomainId(appId, domainId).orElseThrow(() -> new EntityNotFoundException(String.valueOf(domainId)));
    }

    public List<Domain> findByAppAndDomainName(Long appId, String domainName) {
        return this.getRepository().findByAppAndDomainName(appId, domainName);
    }

    @Override
    public DomainRepository getRepository() {
        return domainRepository;
    }

    public void createRelationsForAllTargetDomains(Long appId, String sourceDomainName, List<Relationship> relationsSource) {
        List<Domain> targetDomains = null;
        if (CollectionUtils.isEmpty(relationsSource)) {
            return;
        }

        HashMap<String, List<Relationship>> sourceRelationshipMap = new HashMap<>();
        for (Relationship relationshipSource : relationsSource) {
            if (sourceRelationshipMap.get(relationshipSource.getOtherEntityName()) == null) {
                sourceRelationshipMap.put(relationshipSource.getOtherEntityName(), new ArrayList<>());
            }
            sourceRelationshipMap.get(relationshipSource.getOtherEntityName()).add(relationshipSource);
        }

        for (String otherEntityName : sourceRelationshipMap.keySet()) {
            targetDomains = this.getRepository().findByAppAndDomainName(appId, otherEntityName);
            if (!CollectionUtils.isEmpty(targetDomains)) {
                for (Domain targetDomain : targetDomains) {
                    createTargetDomainRelations(sourceDomainName, sourceRelationshipMap.get(otherEntityName), targetDomain);
                }
            }
        }
    }

    private void createTargetDomainRelations(String sourceDomainName, List<Relationship> relationshipsSource, Domain targetDomain) {
        if (CollectionUtils.isEmpty(relationshipsSource)) {
            return;
        }

        for (Relationship relationshipSource : relationshipsSource) {
            Relationship targetRelationship = createTargetRelationship(sourceDomainName, relationshipSource);
            targetDomain.getRelationships().add(targetRelationship);
        }
        this.getRepository().save(targetDomain);
    }

    private Relationship createTargetRelationship(String sourceDomainName, Relationship relationshipSource) {
        Relationship targetRelationship = new Relationship();
        targetRelationship.setOtherEntityName(sourceDomainName);
        targetRelationship.setOtherEntityRelationshipName(relationshipSource.getRelationshipName());
        targetRelationship.setRelationshipName(relationshipSource.getOtherEntityRelationshipName());
        targetRelationship.setRelationshipType(relationshipSource.getRelationshipType().getOpposite());
        return targetRelationship;
    }

    private void removeExistingRelationsFromTargetDomain(String sourceDomainName, Domain targetDomain) {
        List<Relationship> relationsTarget = targetDomain.getRelationships();
        List<Relationship> newRelationsTarget = new ArrayList<>();
        if (!CollectionUtils.isEmpty(relationsTarget)) {
            for (Relationship relationshipTarget : relationsTarget) {
                if (relationshipTarget.getOtherEntityName().equals(sourceDomainName)) {
                    newRelationsTarget.add(relationshipTarget);
                }
            }
        }
        relationsTarget.removeAll(newRelationsTarget);
    }

    public void deleteRelationsForAllTargetDomains(Long sourceDomainId) {
        Domain domain = this.getRepository().findById(sourceDomainId).orElseThrow(() -> new EntityNotFoundException(String.valueOf(sourceDomainId)));
        Long appId = domain.getApp().getId();
        String sourceDomainName = domain.getName();
        List<Relationship> relationsSource = domain.getRelationships();
        if (CollectionUtils.isEmpty(relationsSource)) {
            return;
        }

        HashMap<String, List<Relationship>> sourceRelationshipMap = new HashMap<>();
        for (Relationship relationshipSource : relationsSource) {
            if (sourceRelationshipMap.get(relationshipSource.getOtherEntityName()) == null) {
                sourceRelationshipMap.put(relationshipSource.getOtherEntityName(), new ArrayList<>());
            }
            sourceRelationshipMap.get(relationshipSource.getOtherEntityName()).add(relationshipSource);
        }

        for (String otherEntityName : sourceRelationshipMap.keySet()) {
            List<Domain> targetDomains = null;
            targetDomains = this.getRepository().findByAppAndDomainName(appId, otherEntityName);
            if (!CollectionUtils.isEmpty(targetDomains)) {
                for (Domain targetDomain : targetDomains) {
                    removeExistingRelationsFromTargetDomain(sourceDomainName, targetDomain);
                    this.getRepository().save(targetDomain);
                }
            }
        }
    }
}
