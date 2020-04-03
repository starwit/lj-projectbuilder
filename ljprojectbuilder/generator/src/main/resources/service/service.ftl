package de.${project.packagePrefix?lower_case}.${project.title?lower_case}.ejb;

import java.io.Serializable;
import javax.ejb.Local;
import de.${project.packagePrefix?lower_case}.${project.title?lower_case}.entity.${domain.name}Entity;

@Local
public interface ${domain.name}Service extends Serializable, AbstractService<${domain.name}Entity> {

}