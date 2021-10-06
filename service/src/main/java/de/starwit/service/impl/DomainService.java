package de.starwit.service.impl;

import java.util.List;

import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.starwit.mapper.Mapper;
import de.starwit.persistence.entity.Domain;
import de.starwit.persistence.entity.App;
import de.starwit.persistence.exception.EntityNotFoundException;
import de.starwit.persistence.exception.NotificationException;
import de.starwit.persistence.repository.DomainRepository;
import de.starwit.persistence.repository.AppRepository;

@Service
public class DomainService implements ServiceInterface<Domain> {

	@Autowired
	private AppRepository appRepository;

	@Autowired
	private DomainRepository domainRepository;
	
	final static Logger LOG = LoggerFactory.getLogger(DomainService.class);
	

	public List<Domain> findAllDomainsByApp(Long appId) {
		List<Domain> entities = this.domainRepository.findAllDomainsByApp(appId);
		return Mapper.convertList(entities, Domain.class, "app");
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void setDomainSelected(Long domainId, boolean selected) {
		this.domainRepository.setDomainSelected(domainId, selected);
	}

	@Override
	public List<Domain> findAll() {
		List<Domain> entities = this.domainRepository.findAll();
		return Mapper.convertList(entities, Domain.class, "app");
	}

	@Override
	public Domain findById(Long id) {
		Domain entity = this.domainRepository.findById(id).orElse(null);
		return Mapper.convert(entity, Domain.class, "app");
	}

	public Domain saveOrUpdateThrowException(Domain dto) throws ValidationException, NotificationException {
		App app = this.appRepository.findAppByIdOrThrowExeption(dto.getAppId());
		
		if (dto != null) {
			dto.setApp(app);
		}
		dto = this.domainRepository.save(dto);
		return Mapper.convert(dto, Domain.class, "app");
	}

	@Override
	public void delete(Long id) throws EntityNotFoundException {
		this.domainRepository.deleteById(id);
	}

	@Override
	public Domain saveOrUpdate(Domain dto) throws ValidationException {
		try {
			return saveOrUpdateThrowException(dto);
		} catch (NotificationException e) {
			LOG.error("Error saving Domain.", e);
			return null;
		}
	}
}