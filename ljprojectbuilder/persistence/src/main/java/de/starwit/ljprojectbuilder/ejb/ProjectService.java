package de.starwit.ljprojectbuilder.ejb;

import java.io.Serializable;
import javax.ejb.Local;
import de.starwit.ljprojectbuilder.entity.ProjectEntity;
import de.starwit.ljprojectbuilder.response.ResponseMetadata;

@Local
public interface ProjectService extends Serializable, AbstractService<ProjectEntity> {
	
	public ResponseMetadata copyProjectTemplate(ProjectEntity entity);
	public ResponseMetadata renameAll(ProjectEntity entity);

}


    
