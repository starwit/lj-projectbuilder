package de.starwit.service.impl;

import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.starwit.persistence.entity.DomainEntity;
import de.starwit.persistence.entity.ProjectEntity;
import de.starwit.persistence.exception.NotificationException;
import de.starwit.persistence.repository.DomainRepository;

@Service
public class DomainService {
	
	@Autowired
	private ProjectService projectService;

	@Autowired
	private DomainRepository domainRepository;


	public List<DomainEntity> findAllDomainsByProject(Long projectId) {
		return this.domainRepository.findAllDomainsByProject(projectId);
	}
	
	public void setDomainSelected(Long domainId, boolean selected) {
		this.domainRepository.setDomainSelected(domainId, selected);
	}
	
	public DomainEntity update(DomainEntity entity) throws ValidationException {
		return this.domainRepository.save(entity);
	}
	
	
	public DomainEntity create(DomainEntity entity) throws ValidationException, NotificationException {
		ProjectEntity project = this.projectService.findProjectByIdOrThrowExeption(entity.getProjectId());
		if (entity != null) {
			entity.setProject(project);
		}
		return this.domainRepository.save(entity);
	}

	public List<DomainEntity> findAll() {
		return this.domainRepository.findAll();
	}

	public DomainEntity findById(Long id) {
		return this.domainRepository.findById(id).orElse(null);
	}

	public DomainEntity saveOrUpdate(DomainEntity entity) {
		return this.domainRepository.save(entity);
	}

	public DomainEntity delete(DomainEntity entity) {
    this.domainRepository.delete(entity);
    return entity;
  }

}