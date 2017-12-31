package de.starwit.ljprojectbuilder.ejb;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;

import de.starwit.ljprojectbuilder.entity.CodeTemplateEntity;

@Local
public interface CodeTemplateService extends Serializable, AbstractService<CodeTemplateEntity> {
	
	List<CodeTemplateEntity> findAllCodeTemplatesByProject(Long projectId);
	
	List<CodeTemplateEntity> findAllCodeTemplatesByProjectTemplate(Long projectTemplateId);
}


    
