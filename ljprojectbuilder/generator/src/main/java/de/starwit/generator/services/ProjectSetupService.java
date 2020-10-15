package de.starwit.generator.services;


import java.io.Serializable;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.starwit.generator.dto.GeneratorDto;
import de.starwit.persistence.entity.DomainEntity;
import de.starwit.persistence.entity.ProjectEntity;
import de.starwit.persistence.exception.EntityNotFoundException;
import de.starwit.persistence.exception.NotificationException;
import de.starwit.service.impl.DomainService;
import de.starwit.service.impl.ProjectService;
/**
 * Class for processing the whole project setup. A newly configured project is created and can be used.
 * @author Anett Huebner
 *
 */
@Service
public class ProjectSetupService implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private DomainService domainService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private GeneratorService generatorSerivce;
	
	@Autowired
	private ProjectCheckout projectCheckout;
	
	@Autowired
	private ProjectRenamer projectRenamer;
	
  final static Logger LOG = LoggerFactory.getLogger(ProjectSetupService.class);
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
	@Transactional(propagation = Propagation.NEVER)
	public void setupAndGenerateProject(GeneratorDto dto) throws NotificationException, EntityNotFoundException {
		ProjectEntity project = projectService.findProjectByIdOrThrowExeption(dto.getProject().getId());
		//String destDirString = project.getTargetPath();
		//projectCheckout.deleteTempProject(Constants.TMP_DIR + Constants.FILE_SEP + destDirString);
		String newProjectFolder = projectCheckout.createTempProjectDirectory(project);
		project.setTargetPath(newProjectFolder);
		project = projectService.saveOrUpdate(project);
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
		projectCheckout.findFilesAndDelete();
	}
}