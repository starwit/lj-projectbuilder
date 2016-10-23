package de.starwit.ljprojectbuilder.ejb;

import java.io.Serializable;

import javax.ejb.Local;

import de.starwit.ljprojectbuilder.dto.GeneratorDto;
import de.starwit.ljprojectbuilder.exeptions.ProjectSetupException;

/**
 * Class for processeing the whole project setup. A newly configured project is created an can be used.
 * @author Anett Huebner
 *
 */
@Local
public interface ProjectSetupService extends Serializable {

	/**
	 * Executes all functions needed to setup the new project. These are:
	 *  - checkout template-project from git-repository
	 *  - rename the project as configured in generatorDto.project
	 *  - rename the "starwit" package as configured in generatorDto.project
	 *  - generate CRUD-Operations for the several layers (Database access, REST API and frontend)
	 * @param dto - configuration for project setup
	 * @return
	 * @throws ProjectSetupException
	 */
	void setupAndGenerateProject(GeneratorDto dto) throws ProjectSetupException;
}


    
