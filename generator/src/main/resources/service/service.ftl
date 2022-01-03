package de.${app.packagePrefix?lower_case}.${app.title?lower_case}.ejb;

import java.io.Serializable;
import javax.ejb.Local;
import de.${app.packagePrefix?lower_case}.${app.title?lower_case}.entity.${domain.name}Entity;

@Local
public interface ${domain.name}Service extends Serializable, AbstractService<${domain.name}Entity> {

}