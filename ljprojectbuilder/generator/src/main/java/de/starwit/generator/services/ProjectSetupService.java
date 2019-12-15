package de.starwit.generator.services;


import java.io.File;
import java.io.Serializable;
import java.util.Date;
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
 * Class for processing the whole project setup. A newly configured project is created an can be used.
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
		//String destDirString = project.getTargetPath();
		//projectCheckout.deleteTempProject(Constants.TMP_DIR + Constants.FILE_SEP + destDirString);
		String newProjectFolder = projectCheckout.createTempProjectDirectory(project);
		project.setTargetPath(newProjectFolder);
		project = projectService.update(project);
		Set<DomainEntity> selectedDomains = dto.getSelectedDomains();
		for (DomainEntity domain : selectedDomains) {
			domainService.setDomainSelected(domain.getId(), domain.isSelected());
		}

		GeneratorDto checkoutDto = dto;
		checkoutDto.setProject(project);
		projectCheckout.checkoutProjectTemplate(dto);
		projectRenamer.renameProjectTitle(project);
		projectRenamer.renamePackage(project);
		dto.setProject(project);
		
		generatorSerivce.generate(project.getId());
		findFilesAndDelete();
	}
	
	private void findFilesAndDelete() {
		File oldDestDir = new File(Constants.TMP_DIR);
		final File[] files = oldDestDir.listFiles((File pathname) -> pathname.getName().startsWith("LJ_"));
		for (File file : files) {
			long diff = new Date().getTime() - file.lastModified();

			if (diff > 1 * 60 * 1000) {
				projectCheckout.deleteTempProject(file.getAbsolutePath().toString());
			}
		}
	}
}