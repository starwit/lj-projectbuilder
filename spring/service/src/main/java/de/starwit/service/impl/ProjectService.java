package de.starwit.service.impl;

import java.util.List;

import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.starwit.mapper.Mapper;
import de.starwit.persistence.entity.DomainEntity;
import de.starwit.persistence.entity.ProjectEntity;
import de.starwit.persistence.exception.EntityNotFoundException;
import de.starwit.persistence.exception.NotificationException;
import de.starwit.persistence.repository.ProjectRepository;
import de.starwit.persistence.response.ResponseCode;
import de.starwit.persistence.response.ResponseMetadata;

@Service
public class ProjectService implements ServiceInterface<ProjectEntity> {

	public final static String[] EXT = new String[] { "java", "js", "html", "sql", "xml" };
	final static Logger LOG = LoggerFactory.getLogger(ProjectService.class);

	@Autowired
	private ProjectRepository projectRepository;


	public ProjectEntity findProjectByIdOrThrowExeption(final Long projectid) throws NotificationException {
		final ProjectEntity entity = this.projectRepository.findById(projectid).orElse(null);
		if (entity == null) {
			LOG.error("Error setup project for generation. Project with id " + projectid + " could not be found.");
			final ResponseMetadata data = new ResponseMetadata(ResponseCode.ERROR,
					"error.projectservice.findprojectbyid.projectnotfound");
			throw new NotificationException(data);
		}
		return Mapper.convert(entity, ProjectEntity.class, "domains", "selectedDomains");
	}

	@Override
	public ProjectEntity saveOrUpdate(ProjectEntity dto) throws ValidationException {
		if (dto != null && dto.getId() != null) {
			final List<DomainEntity> domains = this.projectRepository.findById(dto.getId()).orElse(null)
					.getDomains();
			if (domains != null) {
				dto.setDomains(domains);
			}
		}
		
		dto = this.projectRepository.save(dto);
		return Mapper.convert(dto, ProjectEntity.class, "domains", "selectedDomains");
	}

	@Override
	public void delete(Long id) throws EntityNotFoundException {
		ProjectEntity entity = projectRepository.findById(id).get();
		if (entity == null) {
			throw new EntityNotFoundException(id, "ProjectEntity");
		}
		this.projectRepository.deleteById(id);
	}

	@Override
	public ProjectEntity findById(Long id) {
		ProjectEntity entity = this.projectRepository.findById(id).orElse(null);
		return Mapper.convert(entity, ProjectEntity.class, "domains", "selectedDomains");
	}

	@Override
	public List<ProjectEntity> findAll() {
		return Mapper.convertList(this.projectRepository.findAll(), ProjectEntity.class, "domains", "selectedDomains");
	}
}