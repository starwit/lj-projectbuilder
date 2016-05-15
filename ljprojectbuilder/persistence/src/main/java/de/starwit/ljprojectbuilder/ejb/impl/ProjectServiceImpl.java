package de.starwit.ljprojectbuilder.ejb.impl;


import javax.ejb.Stateless;
import de.starwit.ljprojectbuilder.ejb.ProjectService;
import de.starwit.ljprojectbuilder.entity.ProjectEntity;

@Stateless(name = "ProjectService")
public class ProjectServiceImpl extends AbstractServiceImpl<ProjectEntity> implements ProjectService {
	
	private static final long serialVersionUID = 1L;

}



    
