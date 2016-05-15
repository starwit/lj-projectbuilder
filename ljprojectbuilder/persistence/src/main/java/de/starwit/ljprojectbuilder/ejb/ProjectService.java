package de.starwit.ljprojectbuilder.ejb;

import java.io.Serializable;
import javax.ejb.Local;
import de.starwit.ljprojectbuilder.entity.ProjectEntity;

@Local
public interface ProjectService extends Serializable, AbstractService<ProjectEntity> {

}


    
