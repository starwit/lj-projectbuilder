package de.starwit.ljprojectbuilder.ejb;

import java.io.Serializable;

import javax.ejb.Local;

import de.starwit.ljprojectbuilder.entity.ProjectTemplateEntity;

@Local
public interface ProjectTemplateService extends Serializable, AbstractService<ProjectTemplateEntity> {
}


    
