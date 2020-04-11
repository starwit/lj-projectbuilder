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
import de.starwit.persistence.response.ResponseCode;
import de.starwit.persistence.response.ResponseMetadata;

@Service
public class ProjectService implements AbstractServiceInterface<ProjectEntity> {

	public final static String[] EXT = new String[] { "java", "js", "html", "sql", "xml" };
	final static Logger LOG = LoggerFactory.getLogger(ProjectService.class);

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private DomainRepository domainRepository;

	public ProjectEntity findProjectByIdOrThrowExeption(final Long projectid) throws NotificationException {
		final ProjectEntity entity = this.projectRepository.findById(projectid).orElse(null);
		if (entity == null) {
			LOG.error("Error setup project for generation. Project with id " + projectid + " could not be found.");
			final ResponseMetadata data = new ResponseMetadata(ResponseCode.ERROR,
					"error.projectservice.findprojectbyid.projectnotfound");
			throw new NotificationException(data);
		}
		return entity;
	}

	@Override
	public ProjectEntity saveOrUpdate(final ProjectEntity entity) throws ValidationException {
		if (entity != null && entity.getId() != null) {
			final List<DomainEntity> domains = this.projectRepository.findById(entity.getId()).orElse(null)
					.getDomains();
			if (domains != null) {
				entity.setDomains(domains);
			}
		}
		return this.projectRepository.save(entity);
	}

	@Override
	public void delete(Long id) throws EntityNotFoundException {
		this.projectRepository.deleteById(id);
		ProjectEntity entity = projectRepository.findById(id).get();
		if (entity == null) {
			throw new EntityNotFoundException(id, "ProjectEntity");
		}
		List<DomainEntity> domains = entity.getDomains();
		for (DomainEntity domainEntity : domains) {
			domainRepository.delete(domainEntity);
		}
	}

	@Override
	public ProjectEntity findById(Long id) {
		return this.projectRepository.findById(id).orElse(null);
	}

	@Override
	public List<ProjectEntity> findAll() {
		return this.projectRepository.findAll();
	}
}