package de.starwit.generator.services;


import java.io.Serializable;
import java.util.Set;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import de.starwit.generator.config.Constants;
import de.starwit.generator.dto.GeneratorDto;
import de.starwit.ljprojectbuilder.ejb.DomainService;
import de.starwit.ljprojectbuilder.ejb.ProjectService;
import de.starwit.ljprojectbuilder.entity.DomainEntity;
import de.starwit.ljprojectbuilder.entity.ProjectEntity;
import de.starwit.ljprojectbuilder.exception.NotificationException;
/**
 * Class for processeing the whole project setup. A newly configured project is created an can be used.
 * @author Anett Huebner
 *
 */
@Local
@Stateless(name = "ProjectSetupService")
public class ProjectSetupService implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private DomainService domainService;
	
	@Inject
	private ProjectService projectService;
	
	@Inject
	private GeneratorService generatorSerivce;
	
	@Inject
	private ProjectCheckout projectCheckout;
	
	@Inject
	private ProjectRenamer projectRenamer;
	
	final static Logger LOG = Logger.getLogger(ProjectSetupService.class);
	
	/**
	 * Executes all functions needed to setup the new project. These are:
	 *  - checkout template-project from git-repository
	 *  - rename the project as configured in generatorDto.project
	 *  - rename the "starwit" package as configured in generatorDto.project
	 *  - generate CRUD-Operations for the several layers (Database access, REST API and frontend)
	 * @param dto - configuration for project setup
	 * @return
	 * @throws NotificationException
	 */
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public void setupAndGenerateProject(GeneratorDto dto) throws NotificationException {
		ProjectEntity project = projectService.findProjectByIdOrThrowExeption(dto.getProject().getId());

		projectCheckout.deleteOldProject(Constants.TMP_DIR + Constants.FILE_SEP + project.getTargetPath());
		String newProjectFolder = projectCheckout.createNewProjectDirectory(project);
		project.setTargetPath(newProjectFolder);
		project = projectService.update(project);
		Set<DomainEntity> selectedDomains = dto.getSelectedDomains();
		for (DomainEntity domain : selectedDomains) {
			domainService.setDomainSelected(domain.getId(), domain.isSelected());
		}
		
		projectCheckout.checkoutProjectTemplate(project);
		
		projectRenamer.renameProjectTitle(project);
		projectRenamer.renamePackage(project);
		dto.setProject(project);
		
		generatorSerivce.generate(project.getId());
	}
}