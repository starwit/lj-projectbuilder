package de.spring.service.impl;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.spring.persistence.entity.DomainEntity;
import de.spring.persistence.entity.ProjectEntity;
import de.spring.persistence.exception.NotificationException;
import de.spring.persistence.repository.DomainRepository;

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
}