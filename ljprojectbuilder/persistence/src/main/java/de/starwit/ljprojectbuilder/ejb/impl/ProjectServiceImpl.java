package de.starwit.ljprojectbuilder.ejb.impl;


import java.util.List;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import de.starwit.ljprojectbuilder.ejb.ProjectService;
import de.starwit.ljprojectbuilder.entity.DomainEntity;
import de.starwit.ljprojectbuilder.entity.ProjectEntity;
import de.starwit.ljprojectbuilder.exception.EntityNotFoundException;
import de.starwit.ljprojectbuilder.exception.NotificationException;
import de.starwit.ljprojectbuilder.response.ResponseCode;
import de.starwit.ljprojectbuilder.response.ResponseMetadata;

@Stateless(name = "ProjectService")
public class ProjectServiceImpl extends AbstractServiceImpl<ProjectEntity> implements ProjectService {
	
	private static final long serialVersionUID = 1L;
	
	public final static String[] EXT = new String[] { "java", "js", "html", "sql","xml" };
	final static Logger LOG = Logger.getLogger(ProjectServiceImpl.class);
	
	@Override
	public ProjectEntity findProjectByIdOrThrowExeption(Long projectid) throws NotificationException {
		ProjectEntity entity = findById(projectid);
		if (entity == null) {
			LOG.error("Error setup project for generation. Project with id " + projectid + " could not be found.");
			ResponseMetadata data = new ResponseMetadata(ResponseCode.ERROR, "error.projectservice.findprojectbyid.projectnotfound");
			throw new NotificationException(data);
		}
		return entity;
	}

	@Override
	public ProjectEntity update(ProjectEntity entity) {
		if (entity != null) {
			entity.setDomains(findById(entity.getId()).getDomains());
		}
		return super.update(entity);
	}
	
	@Override
	public void delete(Long id) throws EntityNotFoundException {
		ProjectEntity entity = getEntityManager().find(getParentClass(), id);
		if (entity == null) {
			throw new EntityNotFoundException(id, getParentClass().getName());
		}
		List<DomainEntity> domains = findById(entity.getId()).getDomains();
		for (DomainEntity domainEntity : domains) {
			getEntityManager().remove(domainEntity);
		}
		getEntityManager().flush();
		getEntityManager().remove(entity);
	}
}