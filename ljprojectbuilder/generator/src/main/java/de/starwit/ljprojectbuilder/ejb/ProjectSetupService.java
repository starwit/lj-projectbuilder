package de.starwit.ljprojectbuilder.ejb;

import java.io.Serializable;

import javax.ejb.Local;

import de.starwit.ljprojectbuilder.dto.GeneratorDto;
import de.starwit.ljprojectbuilder.exeptions.ProjectSetupException;

@Local
public interface ProjectSetupService extends Serializable {

	void setupAndGenerateProject(GeneratorDto dto) throws ProjectSetupException;
}


    
