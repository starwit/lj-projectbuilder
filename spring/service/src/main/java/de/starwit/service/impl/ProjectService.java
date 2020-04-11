package de.starwit.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.starwit.dto.ProjectDto;
import de.starwit.persistence.entity.DomainEntity;
import de.starwit.persistence.entity.ProjectEntity;
import de.starwit.persistence.exception.EntityNotFoundException;
import de.starwit.persistence.exception.NotificationException;
import de.starwit.persistence.repository.DomainRepository;
import de.starwit.persistence.repository.ProjectRepository;
import de.starwit.persistence.response.ResponseCode;
import de.starwit.persistence.response.ResponseMetadata;

@Service
public class ProjectService implements AbstractServiceInterface<ProjectDto> {

	public final static String[] EXT = new String[] { "java", "js", "html", "sql", "xml" };
	final static Logger LOG = LoggerFactory.getLogger(ProjectService.class);

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private DomainRepository domainRepository;
	
	private ProjectDto entityToDto(ProjectEntity entity) {
		if (entity != null) {
			ProjectDto dto = new ProjectDto();
			BeanUtils.copyProperties(entity, dto);
			dto.setId(entity.getId());
			return dto;
		}
	    return null;
	}
	
	private ProjectEntity dtoToEntity(ProjectDto dto) {
		if (dto != null) {
			ProjectEntity entity = new ProjectEntity();
			BeanUtils.copyProperties(dto, entity);
			entity.setId(dto.getId());
			return entity;
		}
	    return null;
	}
	
	private List<ProjectDto> entitiesToDtos(List<ProjectEntity> entities) {
		if (entities != null) {
			List<ProjectDto> dtos = new ArrayList<ProjectDto>();
			for (ProjectEntity entity : entities) {
				dtos.add(entityToDto(entity));
			}
			return dtos;
		}
		return null;
	}

	public ProjectDto findProjectByIdOrThrowExeption(final Long projectid) throws NotificationException {
		final ProjectEntity entity = this.projectRepository.findById(projectid).orElse(null);
		if (entity == null) {
			LOG.error("Error setup project for generation. Project with id " + projectid + " could not be found.");
			final ResponseMetadata data = new ResponseMetadata(ResponseCode.ERROR,
					"error.projectservice.findprojectbyid.projectnotfound");
			throw new NotificationException(data);
		}
		return entityToDto(entity);
	}

	@Override
	public ProjectDto saveOrUpdate(final ProjectDto dto) throws ValidationException {
		ProjectEntity entity = dtoToEntity(dto);
		
		if (entity != null && entity.getId() != null) {
			final List<DomainEntity> domains = this.projectRepository.findById(entity.getId()).orElse(null)
					.getDomains();
			if (domains != null) {
				entity.setDomains(domains);
			}
		}
		
		entity = this.projectRepository.save(entity);
		return this.entityToDto(entity);
	}

	@Override
	public void delete(Long id) throws EntityNotFoundException {
		ProjectEntity entity = projectRepository.findById(id).get();
		if (entity == null) {
			throw new EntityNotFoundException(id, "ProjectEntity");
		}
		List<DomainEntity> domains = entity.getDomains();
		for (DomainEntity domainEntity : domains) {
			domainRepository.delete(domainEntity);
		}
		this.projectRepository.deleteById(id);
	}

	@Override
	public ProjectDto findById(Long id) {
		ProjectEntity entity = this.projectRepository.findById(id).orElse(null);
		return entityToDto(entity);
	}

	@Override
	public List<ProjectDto> findAll() {
		return entitiesToDtos(this.projectRepository.findAll());
	}
}