package de.starwit.ljprojectbuilder.ejb;

import java.io.Serializable;

import javax.ejb.Local;

import de.starwit.ljprojectbuilder.entity.ProjectEntity;
import de.starwit.ljprojectbuilder.exception.NotificationException;

@Local
public interface ProjectService extends Serializable, AbstractService<ProjectEntity> {
	
	public ProjectEntity findProjectByIdOrThrowExeption(Long projectid) throws NotificationException;
}


    
