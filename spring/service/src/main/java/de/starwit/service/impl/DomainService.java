package de.starwit.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.starwit.dto.DomainDto;
import de.starwit.persistence.entity.DomainEntity;
import de.starwit.persistence.entity.ProjectEntity;
import de.starwit.persistence.exception.EntityNotFoundException;
import de.starwit.persistence.exception.NotificationException;
import de.starwit.persistence.repository.DomainRepository;
import de.starwit.persistence.repository.ProjectRepository;

@Service
public class DomainService implements AbstractServiceInterface<DomainDto> {

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private DomainRepository domainRepository;
	
	final static Logger LOG = LoggerFactory.getLogger(DomainService.class);
	
	private DomainDto entityToDto(DomainEntity entity) {
		if (entity != null) {
			DomainDto dto = new DomainDto();
			BeanUtils.copyProperties(entity, dto);
			dto.setId(entity.getId());
			return dto;
		}
	    return null;
	}
	
	private DomainEntity dtoToEntity(DomainDto dto) {
		if (dto != null) {
			DomainEntity entity = new DomainEntity();
			BeanUtils.copyProperties(dto, entity);
			entity.setId(dto.getId());
			return entity;
		}
	    return null;
	}
	
	private List<DomainDto> entitiesToDtos(List<DomainEntity> entities) {
		if (entities != null) {
			List<DomainDto> dtos = new ArrayList<DomainDto>();
			for (DomainEntity entity : entities) {
				dtos.add(entityToDto(entity));
			}
			return dtos;
		}
		return null;
	}

	public List<DomainDto> findAllDomainsByProject(Long projectId) {
		return entitiesToDtos(this.domainRepository.findAllDomainsByProject(projectId));
	}

	public void setDomainSelected(Long domainId, boolean selected) {
		this.domainRepository.setDomainSelected(domainId, selected);
	}

	@Override
	public List<DomainDto> findAll() {
		return entitiesToDtos(this.domainRepository.findAll());
	}

	@Override
	public DomainDto findById(Long id) {
		DomainEntity entity = this.domainRepository.findById(id).orElse(null);
		return entityToDto(entity);
	}

	public DomainDto saveOrUpdateThrowException(DomainDto dto) throws ValidationException, NotificationException {
		DomainEntity entity = dtoToEntity(dto);
		ProjectEntity project = this.projectRepository.findProjectByIdOrThrowExeption(dto.getProjectId());
		
		if (entity != null) {
			entity.setProject(project);
		}
		entity = this.domainRepository.save(entity);
		return this.entityToDto(entity);
	}

	@Override
	public void delete(Long id) throws EntityNotFoundException {
		this.domainRepository.deleteById(id);
	}

	@Override
	public DomainDto saveOrUpdate(DomainDto dto) throws ValidationException {
		try {
			return saveOrUpdateThrowException(dto);
		} catch (NotificationException e) {
			LOG.error("Error saving Domain.", e);
			return null;
		}
	}
}