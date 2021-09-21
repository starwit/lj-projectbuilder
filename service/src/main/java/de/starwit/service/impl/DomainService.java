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
import de.starwit.persistence.entity.DomainEntity;
import de.starwit.persistence.entity.App;
import de.starwit.persistence.exception.EntityNotFoundException;
import de.starwit.persistence.exception.NotificationException;
import de.starwit.persistence.repository.DomainRepository;
import de.starwit.persistence.repository.AppRepository;

@Service
public class DomainService implements ServiceInterface<DomainEntity> {

	@Autowired
	private AppRepository AppRepository;

	@Autowired
	private DomainRepository domainRepository;
	
	final static Logger LOG = LoggerFactory.getLogger(DomainService.class);
	

	public List<DomainEntity> findAllDomainsByApp(Long appId) {
		List<DomainEntity> entities = this.domainRepository.findAllDomainsByApp(appId);
		return Mapper.convertList(entities, DomainEntity.class, "app");
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void setDomainSelected(Long domainId, boolean selected) {
		this.domainRepository.setDomainSelected(domainId, selected);
	}

	@Override
	public List<DomainEntity> findAll() {
		List<DomainEntity> entities = this.domainRepository.findAll();
		return Mapper.convertList(entities, DomainEntity.class, "app");
	}

	@Override
	public DomainEntity findById(Long id) {
		DomainEntity entity = this.domainRepository.findById(id).orElse(null);
		return Mapper.convert(entity, DomainEntity.class, "app");
	}

	public DomainEntity saveOrUpdateThrowException(DomainEntity dto) throws ValidationException, NotificationException {
		App app = this.AppRepository.findAppByIdOrThrowExeption(dto.getAppId());
		
		if (dto != null) {
			dto.setApp(app);
		}
		dto = this.domainRepository.save(dto);
		return Mapper.convert(dto, DomainEntity.class, "app");
	}

	@Override
	public void delete(Long id) throws EntityNotFoundException {
		this.domainRepository.deleteById(id);
	}

	@Override
	public DomainEntity saveOrUpdate(DomainEntity dto) throws ValidationException {
		try {
			return saveOrUpdateThrowException(dto);
		} catch (NotificationException e) {
			LOG.error("Error saving Domain.", e);
			return null;
		}
	}
}