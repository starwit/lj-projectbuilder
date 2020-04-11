package de.starwit.service.impl;

import java.util.List;

import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.starwit.persistence.entity.DomainEntity;
import de.starwit.persistence.entity.ProjectEntity;
import de.starwit.persistence.exception.EntityNotFoundException;
import de.starwit.persistence.exception.NotificationException;
import de.starwit.persistence.repository.DomainRepository;
import de.starwit.persistence.repository.ProjectRepository;

@Service
public class DomainService implements AbstractServiceInterface<DomainEntity> {

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private DomainRepository domainRepository;
	
	final static Logger LOG = LoggerFactory.getLogger(DomainService.class);

	public List<DomainEntity> findAllDomainsByProject(Long projectId) {
		return this.domainRepository.findAllDomainsByProject(projectId);
	}

	public void setDomainSelected(Long domainId, boolean selected) {
		this.domainRepository.setDomainSelected(domainId, selected);
	}

	@Override
	public List<DomainEntity> findAll() {
		return this.domainRepository.findAll();
	}

	@Override
	public DomainEntity findById(Long id) {
		return this.domainRepository.findById(id).orElse(null);
	}

	public DomainEntity saveOrUpdateThrowException(DomainEntity entity) throws ValidationException, NotificationException {
		ProjectEntity project = this.projectRepository.findProjectByIdOrThrowExeption(entity.getProjectId());
		if (entity != null) {
			entity.setProject(project);
		}
		return this.domainRepository.save(entity);
	}

	@Override
	public void delete(Long id) throws EntityNotFoundException {
		this.domainRepository.deleteById(id);
	}

	@Override
	public DomainEntity saveOrUpdate(DomainEntity entity) throws ValidationException {
		try {
			return saveOrUpdateThrowException(entity);
		} catch (NotificationException e) {
			LOG.error("Error saving Domain.", e);
			return null;
		}
	}
}