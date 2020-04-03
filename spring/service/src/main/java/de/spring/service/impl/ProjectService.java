package de.spring.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.spring.persistence.entity.DomainEntity;
import de.spring.persistence.entity.ProjectEntity;
import de.spring.persistence.exception.EntityNotFoundException;
import de.spring.persistence.exception.NotificationException;
import de.spring.persistence.repository.ProjectRepository;
import de.spring.persistence.response.ResponseCode;
import de.spring.persistence.response.ResponseMetadata;

@Service
public class ProjectService {

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
		return entity;
	}

	public ProjectEntity update(final ProjectEntity entity) throws EntityNotFoundException {
		if (entity != null) {
			final List<DomainEntity> domains = this.projectRepository.findById(entity.getId()).orElse(null)
					.getDomains();
			if (domains != null) {
				entity.setDomains(domains);
			}
		}
		return this.projectRepository.save(entity);
	}

	public ProjectEntity delete(final ProjectEntity entity) {
    this.projectRepository.delete(entity);
    return entity;

		/*
		 * ProjectEntity entity = getEntityManager().find(getParentClass(), id); if
		 * (entity == null) { throw new EntityNotFoundException(id,
		 * getParentClass().getName()); } List<DomainEntity> domains =
		 * findById(entity.getId()).getDomains(); for (DomainEntity domainEntity :
		 * domains) { getEntityManager().remove(domainEntity); }
		 * getEntityManager().flush(); getEntityManager().remove(entity);
		 */
	}

	public ProjectEntity saveOrUpdate(ProjectEntity entity) {
		return this.projectRepository.save(entity);
	}

	public ProjectEntity findById(Long id) {
		return this.projectRepository.findById(id).orElse(null);
	}

	public List<ProjectEntity> findAll() {
		return this.projectRepository.findAll();
	}
}