package de.starwit.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.starwit.mapper.Mapper;
import de.starwit.persistence.entity.Domain;
import de.starwit.persistence.repository.DomainRepository;

@Service
public class DomainService implements ServiceInterface<Domain, DomainRepository> {

	@Autowired
	private DomainRepository domainRepository;
	
	final static Logger LOG = LoggerFactory.getLogger(DomainService.class);
	

	public List<Domain> findAllDomainsByApp(Long appId) {
		List<Domain> entities = this.domainRepository.findAllDomainsByApp(appId);
		return Mapper.convertList(entities, Domain.class, "app");
	}

	@Override
	public DomainRepository getRepository() {
		return domainRepository;
	}
}