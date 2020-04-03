package de.starwit.generator.services;


import java.io.Serializable;
import java.util.Set;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Autowired;

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
		projectCheckout.findFilesAndDelete();
	}
}