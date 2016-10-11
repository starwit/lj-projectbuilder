package de.starwit.ljprojectbuilder.ejb.impl;


import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import de.starwit.ljprojectbuilder.ejb.ProjectService;
import de.starwit.ljprojectbuilder.entity.ProjectEntity;

@Stateless(name = "ProjectService")
public class ProjectServiceImpl extends AbstractServiceImpl<ProjectEntity> implements ProjectService {
	
	private static final long serialVersionUID = 1L;
	
	public final static String[] EXT = new String[] { "java", "js", "html", "sql","xml" };
	final static Logger LOG = Logger.getLogger(ProjectServiceImpl.class);
	
}